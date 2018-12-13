/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.platform.human.utils;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.BinaryUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事情報における有用なメソッドを提供する。<br>
 */
public class HumanUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HumanUtility() {
		// 処理無し
	}
	
	/**
	 * 人事情報履歴一覧の中での対象人事履歴DTOのインデックスを取得する。
	 * @param dto 対象DTO
	 * @param list 履歴一覧
	 * @return index 対象DTOのインデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static int getHumanTargetIndex(HumanDtoInterface dto, List<HumanDtoInterface> list) throws MospException {
		int index = 0;
		//iでまわす
		for (int i = 0; i < list.size(); i++) {
			if (dto.getPfmHumanId() == (list.get(i).getPfmHumanId())) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * 経過月から経過年月を取得する。
	 * 入社勤続年数に表示する。
	 * @param amountMonth 経過月
	 * @param mospParams MosP処理情報
	 * @return ○年○ヶ月
	 */
	public static String getDuration(int amountMonth, MospParams mospParams) {
		// 経過月取得
		int countYear = amountMonth / 12;
		int countMonth = amountMonth % 12;
		// マイナスの場合0に変換
		if (countYear < 0) {
			countYear = 0;
		}
		if (countMonth < 0) {
			countMonth = 0;
		}
		// 文字列取得
		String stringCountYear = String.valueOf(countYear);
		String stringCountMonth = String.valueOf(countMonth);
		
		return stringCountYear + mospParams.getName("Year") + stringCountMonth + mospParams.getName("AmountMonth");
	}
	
	/**
	 * 誕生日から今日まで満何歳かを取得する。
	 * @param birthDate 誕生日
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @return 満○歳
	 */
	public static String getHumanOlder(Date birthDate, Date targetDate, MospParams mospParams) {
		if (birthDate == null || targetDate == null) {
			return "";
		}
		// 経過月を取得
		int amountMonth = DateUtility.getMonthDifference(birthDate, targetDate);
		// 経過年数取得
		String countYear = String.valueOf(amountMonth / 12);
		return mospParams.getName("FrontParentheses") + countYear + mospParams.getName("YearsOld")
				+ mospParams.getName("BackParentheses");
	}
	
	/**
	 * 人事情報履歴と対象勤務地情報から勤務地名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param workPlaceDto 勤務地情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 勤務地名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getWorkPlaceStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, WorkPlaceDtoInterface workPlaceDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 勤務地情報かつ勤務地コード確認
		if (workPlaceDto == null || dto.getWorkPlaceCode().isEmpty()) {
			return "";
		}
		// 勤務地コード取得
		String workPlaceCode = dto.getWorkPlaceCode();
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index - 1; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 勤務地コード比較
			if (firstDto.getWorkPlaceCode().equals(workPlaceCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(workPlaceDto.getWorkPlaceName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象所属情報から所属名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param sectionDto 対象所属情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 所属名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getSectionStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList, Date targetDate,
			SectionDtoInterface sectionDto, RetirementDtoInterface retirementDto, MospParams mospParams)
			throws MospException {
		// 所属情報かつ所属コード確認
		if (sectionDto == null || dto.getSectionCode().isEmpty()) {
			return "";
		}
		// 所属コード取得
		String sectionCode = dto.getSectionCode();
		// 所属コード確認
		if (sectionCode.isEmpty()) {
			return "";
		}
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 所属コード比較
			if (firstDto.getSectionCode().equals(sectionCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		
		return getDurationName(sectionDto.getSectionName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象職位情報から職位名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @param positionDto 職位情報
	 * @param retirementDto 退職情報
	 * @return 職位名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getPositionStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, PositionDtoInterface positionDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 職位情報かつ職位コード確認
		if (positionDto == null || dto.getPositionCode().isEmpty()) {
			return "";
		}
		// 職位コード取得
		String positionCode = dto.getPositionCode();
		// 職位コード確認
		if (positionCode.isEmpty()) {
			return "";
		}
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 職位コード比較
			if (firstDto.getPositionCode().equals(positionCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(positionDto.getPositionName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象雇用契約情報から雇用契約名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param employmentContractDto 雇用契約情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 雇用契約名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getEmploymentStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, EmploymentContractDtoInterface employmentContractDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 雇用契約情報かつ雇用契約コード確認
		if (employmentContractDto == null || dto.getEmploymentContractCode().isEmpty()) {
			return "";
		}
		// 雇用契約コード取得
		String employmentCode = dto.getEmploymentContractCode();
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 雇用契約コード比較
			if (firstDto.getEmploymentContractCode().equals(employmentCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(employmentContractDto.getEmploymentContractName(), targetDate, firstDate, retirementDto,
				mospParams);
	}
	
	/**
	 * 人事情報履歴と対象役職情報から役職名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param postList 役職情報リスト
	 * @param postDto 対象役職情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報リスト
	 * @param name 役職名称
	 * @return 役職名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getPostStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList, Date targetDate,
			List<HumanHistoryDtoInterface> postList, HumanHistoryDtoInterface postDto,
			RetirementDtoInterface retirementDto, MospParams mospParams, String name) throws MospException {
		// 役職情報かつ役職情報リスト確認
		if (postDto == null || postList.isEmpty()) {
			return "";
		}
		// 役職コード取得
		String postCode = postDto.getHumanItemValue();
		// 対象人事履歴DTOのインデックスを取得
		int index = HumanUtility.getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index - 1; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 対象役職情報インデックス取得
			int postIndex = getTargetIndexPost(postList, firstDto.getActivateDate());
			if (postIndex == -1) {
				break;
			}
			// 対象役職情報取得
			HumanHistoryDtoInterface targetPostDto = postList.get(postIndex);
			// 役職情報が違う場合
			if (targetPostDto.getHumanItemValue().equals(postCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(name, targetDate, firstDate, retirementDto, mospParams);
		
	}
	
	/**
	 * 役職情報リストの中で同じ有効日情報が存在する場合、<br>
	 * 対象インデックスを取得する。<br>
	 * 有効日情報が存在しない場合は、-1を返す。
	 * @param postList 役職情報リスト
	 * @param activeDate 対象有効日
	 * @return 役職情報対象インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static int getTargetIndexPost(List<HumanHistoryDtoInterface> postList, Date activeDate)
			throws MospException {
		// 役職インデックス取得
		int postIndex = -1;
		// 役職情報リスト毎に処理
		for (int i = 0; i < postList.size(); i++) {
			// 対象役職情報取得
			HumanHistoryDtoInterface dto = postList.get(i);
			// 有効日が同じ場合
			if (dto.getActivateDate().equals(activeDate)) {
				return i;
			}
		}
		return postIndex;
	}
	
	/**
	 * 継続月数を取得する。<br>
	 * 退職日を考慮し、表示日付が退職日より先の場合
	 * 退職日までの継続月数を取得する。<br>
	 * @param showDate 表示日付
	 * @param targetDate 対象情報有効日
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return ○年○ヶ月
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getContinuationMonthName(Date showDate, Date targetDate, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 経過月終了日
		Date lastDate = showDate;
		// 退職情報がある場合
		if (retirementDto != null) {
			// システム日付が退職日より先の場合
			if (lastDate.compareTo(retirementDto.getRetirementDate()) > 0) {
				// 退職日設定
				lastDate = retirementDto.getRetirementDate();
			}
		}
		// 経過月取得
		int amountMonth = DateUtility.getMonthDifference(targetDate, lastDate);
		return getDuration(amountMonth, mospParams);
	}
	
	/**
	 * 名称・継続月数から名称(○年○ヶ月)を取得する。
	 * @param name 名称
	 * @param showDate 表示日付
	 * @param targetDate 対象情報有効日
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 名称(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getDurationName(String name, Date showDate, Date targetDate,
			RetirementDtoInterface retirementDto, MospParams mospParams) throws MospException {
		return name + mospParams.getName("FrontParentheses")
				+ getContinuationMonthName(showDate, targetDate, retirementDto, mospParams)
				+ mospParams.getName("BackParentheses");
	}
	
	/**
	 * 人事汎用バイナリファイル拡張子コードを取得する。<br>
	 * ファイル名の拡張子から、判断する。<br>
	 * @param fileName ファイル名
	 * @return 人事汎用バイナリファイル拡張子コード
	 */
	public static String getBinaryFileType(String fileName) {
		// 拡張子がgifの場合
		if (BinaryUtility.isExtensionGif(fileName)) {
			return PlatformHumanConst.CODE_HUMAN_BINARY_FILE_TYPE_GIF;
		}
		// 拡張子がpngの場合
		if (BinaryUtility.isExtensionPng(fileName)) {
			return PlatformHumanConst.CODE_HUMAN_BINARY_FILE_TYPE_PNG;
		}
		// 拡張子がjpg/jpegの場合
		if (BinaryUtility.isExtensionJpg(fileName)) {
			return PlatformHumanConst.CODE_HUMAN_BINARY_FILE_TYPE_JPEG;
		}
		// それ以外の場合
		return PlatformHumanConst.CODE_HUMAN_BINARY_FILE_TYPE_FILE;
	}
	
	/**
	 * 対象日が休職中であるかを確認する。<br>
	 * <br>
	 * @param suspensionList 休職情報リスト
	 * @param targetDate     対象日
	 * @return 確認結果(true：休職中である、false：休職中でない)
	 */
	public static boolean isSuspension(List<SuspensionDtoInterface> suspensionList, Date targetDate) {
		// 休職リストがない場合
		if (suspensionList == null || suspensionList.isEmpty()) {
			return false;
		}
		// 休職情報毎に処理
		for (SuspensionDtoInterface suspensionDto : suspensionList) {
			// 期間開始日終了日取得
			Date startDate = suspensionDto.getStartDate();
			Date endDate = suspensionDto.getEndDate();
			// 期間終了日がない場合
			if (endDate == null) {
				// 休職予定終了日設定
				endDate = suspensionDto.getScheduleEndDate();
			}
			// 休職期間に含まれている場合
			if (DateUtility.isTermContain(targetDate, startDate, endDate)) {
				return true;
			}
		}
		return false;
	}
	
}
