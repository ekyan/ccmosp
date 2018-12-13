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
package jp.mosp.platform.base;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.human.ConcurrentDaoInterface;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.platform.utils.PlatformNamingUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * プラットフォームにおけるBeanの基本機能を提供する。<br>
 * <br>
 */
public abstract class PlatformBean extends BaseBean {
	
	/**
	 * 区切文字(データ)。<br>
	 * 入力の際の区切文字として用いる。<br>
	 */
	protected static final String SEPARATOR_DATA = ",";
	
	
	/**
	 * {@link PlatformBean}を生成する。<br>
	 */
	public PlatformBean() {
		// 処理無し
	}
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected PlatformBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	/**
	 * レコード識別IDによる排他確認を行う。<br>
	 * 対象レコード識別IDの情報が既に削除されていた場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dao 対象DAOオブジェクト
	 * @param id  レコード識別ID
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkExclusive(BaseDaoInterface dao, long id) throws MospException {
		// レコード識別IDによる情報取得及び確認
		checkExclusive(findForKey(dao, id, false));
	}
	
	/**
	 * 排他確認を行う。<br>
	 * 対象DTOの情報が既に削除されていた場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dto 対象DTOオブジェクト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkExclusive(BaseDtoInterface dto) throws MospException {
		// 対象レコード削除確認
		if (dto == null || dto.getDeleteFlag() == MospConst.DELETE_FLAG_ON) {
			// 対象レコード識別IDが存在しない、或いは削除されている場合
			addExclusiveErrorMessage();
		}
	}
	
	/**
	 * 重複確認(新規登録用)を行う。<br>
	 * 対象DTOリストのサイズが0でない場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param list 対象となるDTOの履歴リスト
	 */
	protected void checkDuplicateInsert(List<?> list) {
		// 対象DTO存在確認
		if (!list.isEmpty()) {
			// 対象DTOが存在する場合
			PlatformMessageUtility.addErrorDuplicate(mospParams);
		}
	}
	
	/**
	 * 重複確認(新規登録用)を行う。<br>
	 * 対象DTOがnullでない場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dto 対象となるDTO
	 */
	protected void checkDuplicateInsert(BaseDtoInterface dto) {
		// 対象DTO存在確認
		if (dto != null) {
			// 対象DTOが存在する場合
			PlatformMessageUtility.addErrorDuplicate(mospParams);
		}
	}
	
	/**
	 * 重複確認(履歴追加用)を行う。<br>
	 * 対象DTOがnullでない場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dto 対象DTO
	 */
	protected void checkDuplicateAdd(BaseDtoInterface dto) {
		// 対象DTO存在確認
		if (dto != null) {
			// 対象DTOが存在する場合
			mospParams.addErrorMessage(PlatformMessageConst.MSG_HIST_ALREADY_EXISTED);
		}
	}
	
	/**
	 * プルダウンに設定する対象データがない場合のメッセージを取得する。<br>
	 * @return 有効日編集中プルダウン
	 */
	protected String[][] getNoObjectDataPulldown() {
		String[][] aryPulldown = { { "", mospParams.getProperties().getName("NoObjectData") } };
		return aryPulldown;
	}
	
	/**
	 * 日付オブジェクトの複製を取得する。<br>
	 * @param date 対象日付オブジェクト
	 * @return 複製日付オブジェクト
	 */
	protected Date getDateClone(Date date) {
		return CapsuleUtility.getDateClone(date);
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	protected String[] getStringArrayClone(String[] array) {
		return CapsuleUtility.getStringArrayClone(array);
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	protected String[][] getStringArrayClone(String[][] array) {
		return CapsuleUtility.getStringArrayClone(array);
	}
	
	/**
	 * 一括処理用レコード識別ID配列の妥当性確認を行う。<br>
	 * @param aryId 対象レコード識別ID配列
	 */
	protected void validateAryId(long[] aryId) {
		// 存在確認
		if (aryId == null || aryId.length == 0) {
			PlatformMessageUtility.addErrorRequireCheck(mospParams);
		}
	}
	
	/**
	 * 確認すべき人事マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新の人事マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * 人事マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto  対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 人事マスタDAO取得
		HumanDaoInterface humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		// 削除対象の有効日以前で最新の人事情報を取得
		List<HumanDtoInterface> humanList = humanDao.findForActivateDate(dto.getActivateDate());
		// 無効期間で人事履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		humanList.addAll(humanDao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return humanList;
	}
	
	/**
	 * 確認すべき兼務情報リストを取得する。<br>
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる兼務情報リストを取得する。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto 対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 兼務情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ConcurrentDtoInterface> getConcurrentListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 人事マスタDAO取得
		ConcurrentDaoInterface concurrentDao = (ConcurrentDaoInterface)createDao(ConcurrentDaoInterface.class);
		// 削除対象の有効日以前で最新の兼務情報を取得
		List<ConcurrentDtoInterface> concurrentList = concurrentDao.findForTerm(dto.getActivateDate(),
				getNextActivateDate(dto.getActivateDate(), list));
		return concurrentList;
	}
	
	/**
	 * 対象日の情報を追加することにより生じる無効期間から、期間確認要否を取得する。<br>
	 * 対象DTOの無効フラグは、無効であるとする。<br>
	 * リストは、有効日の昇順で並んでいるものとする。<br>
	 * @param dto  対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 期間確認要否(true：要、false：不要)
	 */
	protected boolean needCheckTermForAdd(PlatformDtoInterface dto, List<? extends PlatformDtoInterface> list) {
		// 対象DTOの直前の履歴が存在し無効であるかを確認
		int idx = 0;
		for (; idx < list.size(); idx++) {
			// DTO取得
			PlatformDtoInterface historyDto = list.get(idx);
			// 有効日確認
			if (dto.getActivateDate().compareTo(historyDto.getActivateDate()) > 0) {
				continue;
			}
			// 対象DTOの有効日を越えた時点でbreak
			break;
		}
		// インデックス確認
		if (idx == 0) {
			// 直前の履歴が存在しなければ無効期間が生じる
			return true;
		}
		// 直前の履歴の無効フラグを確認
		if (isDtoActivate(list.get(idx - 1)) == false) {
			// 直前の情報が存在し無効であれば無効期間は発生しない
			return false;
		}
		// 対象DTOの直前の履歴が存在し無効でなければ無効期間が生じる
		return true;
	}
	
	/**
	 * 対象日の情報を削除することにより生じる無効期間から、期間確認要否を取得する。<br>
	 * 対象DTOの無効フラグは、有効であるとする。<br>
	 * リストは、有効日の昇順で並んでいるものとする。<br>
	 * @param dto  削除対象DTO
	 * @param list 削除対象コード履歴リスト
	 * @return 期間確認要否(true：要、false：不要)
	 */
	protected boolean needCheckTermForDelete(PlatformDtoInterface dto, List<? extends PlatformDtoInterface> list) {
		// 対象DTOの直前の履歴が存在し有効であるかを確認
		int idx = 0;
		for (; idx < list.size(); idx++) {
			// DTO取得
			PlatformDtoInterface historyDto = list.get(idx);
			// 有効日確認
			if (dto.getActivateDate().compareTo(historyDto.getActivateDate()) != 0) {
				continue;
			}
			// インデックス確認
			if (idx == 0) {
				// 直前の履歴は存在しない
				break;
			}
			// 直前の履歴の無効フラグを確認
			if (isDtoActivate(list.get(idx - 1))) {
				// 直前の情報が存在し有効であれば無効期間は発生しない
				return false;
			}
			break;
		}
		// 対象DTOの直前の履歴が存在し有効でなければ無効期間が生じる
		return true;
	}
	
	/**
	 * 情報が影響を及ぼす期間の最終日を取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 対象日から見て次の履歴の有効日の前日を取得する。<br>
	 * 次の履歴が存在しない場合は、nullを返す。<br>
	 * 人事情報登録時マスタ整合性確認等に、用いる。<br>
	 * @param targetDate 対象日
	 * @param list 履歴一覧
	 * @return 情報が影響を及ぼす期間の最終日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getEffectiveLastDate(Date targetDate, List<? extends PlatformDtoInterface> list)
			throws MospException {
		// 次の履歴の有効日を取得
		Date effectiveLastDate = getNextActivateDate(targetDate, list);
		// 次の履歴の有効日が取得した場合
		if (effectiveLastDate != null) {
			// 次の履歴の有効日前日を取得
			effectiveLastDate = DateUtility.addDay(effectiveLastDate, -1);
		}
		return effectiveLastDate;
	}
	
	/**
	 * 対象日から見て次の履歴の有効日を取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 情報を操作することにより生じる無効期間の最終日を取得する際等に、用いる。<br>
	 * @param targetDate 対象日
	 * @param list       対象コード履歴リスト
	 * @return 無効期間の最終日
	 */
	protected Date getNextActivateDate(Date targetDate, List<? extends PlatformDtoInterface> list) {
		// 最終日宣言
		Date lastDate = null;
		// 履歴リスト確認
		for (PlatformDtoInterface dto : list) {
			// 有効日確認
			if (targetDate.compareTo(dto.getActivateDate()) >= 0) {
				// 対象日以前であればcontinue
				continue;
			}
			// 対象日より後で直後の履歴の有効日
			lastDate = dto.getActivateDate();
			break;
		}
		return lastDate;
	}
	
	/**
	 * 個人ID及び対象日時点から、入社しているか確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：入社している、false：入社していない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isEntered(String personalId, Date targetDate) throws MospException {
		// 人事入社情報DAO準備
		EntranceDaoInterface entranceDao = (EntranceDaoInterface)createDao(EntranceDaoInterface.class);
		EntranceDtoInterface dto = entranceDao.findForInfo(personalId);
		// 情報がないまたは入社日がない場合
		if (dto == null || dto.getEntranceDate() == null) {
			return false;
		}
		// 入社日より対象日が後の場合true
		return targetDate.compareTo(dto.getEntranceDate()) >= 0;
	}
	
	/**
	 * 個人ID及び対象日から、対象日以前で最新の人事マスタ情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 人事マスタ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface getHumanInfo(String personalId, Date targetDate) throws MospException {
		// 人事マスタDAO準備
		HumanDaoInterface dao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		// 人事マスタ情報取得
		return dao.findForInfo(personalId, targetDate);
	}
	
	/**
	 * ログインユーザの対象日以前で最新の人事マスタ情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 人事マスタ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface getUserHumanInfo(Date targetDate) throws MospException {
		// 対象日が存在しない場合
		if (targetDate == null) {
			// 対象日としてシステム日付を設定
			targetDate = getSystemDate();
		}
		// ログインユーザ情報が存在しない場合
		if (mospParams.getUser() == null) {
			// nullを取得
			return null;
		}
		// ユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日における人事情報を取得
		return getHumanInfo(personalId, targetDate);
	}
	
	/**
	 * 操作範囲(勤務地)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * @param operationType 操作区分
	 * @param targetDate 対象日
	 * @return 操作範囲(勤務地)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getRangeWorkPlace(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0];
		}
		// 操作範囲(勤務地)取得及び確認
		String rangeWorkPlace = range.getWorkPlace();
		if (rangeWorkPlace == null || rangeWorkPlace.isEmpty()) {
			return new String[0];
		}
		// 配列に変換
		String[] array = split(rangeWorkPlace, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(MospConst.RANGE_MYSELF)) {
				// ログインユーザの人事マスタ情報を取得
				HumanDtoInterface dto = getUserHumanInfo(targetDate);
				if (dto != null && dto.getWorkPlaceCode().isEmpty() == false) {
					// 人事情報に設定された勤務地を設定
					array[i] = dto.getWorkPlaceCode();
				}
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(雇用契約)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * @param operationType 操作区分
	 * @param targetDate 対象日
	 * @return 操作範囲(雇用契約)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getRangeEmploymentContract(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0];
		}
		// 操作範囲(雇用契約)取得及び確認
		String rangeEmployment = range.getEmploymentContract();
		if (rangeEmployment == null || rangeEmployment.isEmpty()) {
			return new String[0];
		}
		// 配列に変換
		String[] array = split(rangeEmployment, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(MospConst.RANGE_MYSELF)) {
				// ログインユーザの人事マスタ情報を取得
				HumanDtoInterface dto = getUserHumanInfo(targetDate);
				if (dto != null && dto.getEmploymentContractCode().isEmpty() == false) {
					// 人事情報に設定された勤務地を設定
					array[i] = dto.getEmploymentContractCode();
				}
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(所属)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * @param operationType 操作区分
	 * @param targetDate 対象日
	 * @return 操作範囲(所属)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getRangeSection(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0];
		}
		// 操作範囲(所属)取得及び確認
		String rangeSection = range.getSection();
		if (rangeSection == null || rangeSection.isEmpty()) {
			return new String[0];
		}
		// 配列に変換
		String[] array = split(rangeSection, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(MospConst.RANGE_MYSELF)) {
				// ログインユーザの人事マスタ情報を取得
				HumanDtoInterface dto = getUserHumanInfo(targetDate);
				if (dto != null && dto.getSectionCode().isEmpty() == false) {
					// 人事情報に設定された所属を設定
					array[i] = dto.getSectionCode();
				}
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(職位)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * @param operationType 操作区分
	 * @param targetDate 対象日
	 * @return 操作範囲(職位)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getRangePosition(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0];
		}
		// 操作範囲(職位)取得及び確認
		String rangePosition = range.getPosition();
		if (rangePosition == null || rangePosition.isEmpty()) {
			return new String[0];
		}
		// 配列に変換
		String[] array = split(rangePosition, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(MospConst.RANGE_MYSELF)) {
				// ログインユーザの人事マスタ情報を取得
				HumanDtoInterface dto = getUserHumanInfo(targetDate);
				if (dto != null && dto.getPositionCode().isEmpty() == false) {
					// 人事情報に設定された職位を設定
					array[i] = dto.getPositionCode();
				}
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(社員)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * @param operationType 操作区分
	 * @param targetDate 対象日
	 * @return 操作範囲(社員)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getRangeEmployee(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0];
		}
		// 操作範囲(社員)取得及び確認
		String rangeEmployee = range.getEmployee();
		if (rangeEmployee == null || rangeEmployee.isEmpty()) {
			return new String[0];
		}
		// 配列に変換
		String[] array = split(rangeEmployee, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(MospConst.RANGE_MYSELF)) {
				// ログインユーザの人事マスタ情報を取得
				HumanDtoInterface dto = getUserHumanInfo(targetDate);
				if (dto != null && dto.getPersonalId().isEmpty() == false) {
					// 人事情報に設定された職位を設定
					array[i] = dto.getPersonalId();
				}
			}
		}
		return array;
	}
	
	/**
	 * DTOの無効フラグが有効に設定されているかの確認をする。<br>
	 * @param dto 確認対象DTO
	 * @return 確認結果(true：有効、false：無効)
	 */
	protected boolean isDtoActivate(BaseDtoInterface dto) {
		return PlatformUtility.isDtoActivate((PlatformDtoInterface)dto);
	}
	
	/**
	 * DTOの削除フラグがONに設定されているかの確認をする。<br>
	 * @param dto 確認対象DTO
	 * @return 確認結果(true：削除フラグON、false：削除フラグONでない)
	 */
	protected boolean isDtoDeleted(BaseDtoInterface dto) {
		return dto.getDeleteFlag() == MospConst.DELETE_FLAG_ON;
	}
	
	/**
	 * プルダウン用配列から、コードに対応する名称を取得する。<br>
	 * {@link MospUtility#getCodeName(String, String[][])}参照。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コード名称
	 */
	protected String getCodeName(String code, String[][] array) {
		return MospUtility.getCodeName(code, array);
	}
	
	/**
	 * コード名称を取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード名称
	 */
	protected String getCodeName(String code, String codeKey) {
		// コード配列を取得
		String[][] array = mospParams.getProperties().getCodeArray(codeKey, false);
		// コード名称を取得
		return MospUtility.getCodeName(code, array);
	}
	
	/**
	 * コード名称を取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード名称
	 */
	protected String getCodeName(int code, String codeKey) {
		// コード名称を取得
		return getCodeName(String.valueOf(code), codeKey);
	}
	
	/**
	 * 日付オブジェクトが、同一日を指しているかを比較する。<br>
	 * @param date1 日付1
	 * @param date2 日付2
	 * @return 比較結果(true：同一日、false：同一日でない)
	 */
	protected boolean isSameDate(Date date1, Date date2) {
		// null確認
		if (date1 == null && date2 == null) {
			return true;
		}
		if (date1 == null) {
			return false;
		}
		// 比較
		if (date1.equals(date2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * システム日付を取得する。<br>
	 * @return システム日付
	 */
	protected Date getSystemDate() {
		return DateUtility.getSystemDate();
	}
	
	/**
	 * システム日時を取得する。<br>
	 * @return システム日時
	 */
	protected Date getSystemTime() {
		return DateUtility.getSystemTime();
	}
	
	/**
	 * システム日時(秒含む)を取得する。<br>
	 * @return システム日時
	 */
	protected Date getSystemTimeAndSecond() {
		return DateUtility.getSystemTimeAndSecond();
	}
	
	/**
	 * 日操作を行う。<br>
	 * @param date   操作日付
	 * @param amount 増減日数
	 * @return 操作後日付
	 */
	protected Date addDay(Date date, int amount) {
		return DateUtility.addDay(date, amount);
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date	対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd)
	 */
	protected String getStringDate(Date date) {
		return DateUtility.getStringDate(date);
	}
	
	/**
	 * 日付の順序を確認する。<br>
	 * @param before         前にあるべき日
	 * @param after          後にあるべき日
	 * @param acceptSameDate 同一日容認フラグ(true：容認、false：否認)
	 * @return 確認結果(true：正しい順序、false：正しくない順序)
	 */
	protected boolean checkDateOrder(Date before, Date after, boolean acceptSameDate) {
		// null確認
		if (before == null || after == null) {
			return true;
		}
		// 同一日確認
		if (acceptSameDate && before.equals(after)) {
			return true;
		}
		// 比較
		return before.before(after);
	}
	
	/**
	 * 期間の重複を確認する。<br>
	 * @param start1 開始日1
	 * @param end1   終了日1(nullの可能性有り)
	 * @param start2 開始日2
	 * @param end2   終了日2(nullの可能性有り)
	 * @return 確認結果(true：重複無し、false：重複有り)
	 */
	protected boolean checkTermDuplicate(Date start1, Date end1, Date start2, Date end2) {
		// 終了日が共に無い場合
		if (end1 == null && end2 == null) {
			if (start1.equals(start2)) {
				return false;
			}
			return true;
		}
		// 期間1の終了日が無い場合
		if (end1 == null) {
			if (start1.before(start2) || start1.after(end2)) {
				return true;
			}
			return false;
		}
		// 期間2の終了日が無い場合
		if (end2 == null) {
			if (start2.before(start1) || start2.after(end1)) {
				return true;
			}
			return false;
		}
		// 期間1の開始前に期間2が終了しているか期間1の終了後に期間2が開始している場合
		if (start1.before(end2) || end1.after(start2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 有効日以前の情報が存在しない場合の警告メッセージを追加する。<br>
	 * マスタメンテナンス画面における一括更新時の確認等で用いる。<br>
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 */
	protected void addNoCodeBeforeActivateDateMessage(String code) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_CODE_BEFORE_DATE, code);
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  人事マスタDTO
	 */
	protected void addCodeIsUsedMessage(String code, HumanDtoInterface dto) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_CODE_IS_USED, code, dto.getEmployeeCode());
	}
	
	/**
	 * 順序が異なる場合のメッセージを設定する。<br>
	 * @param before 前にあるべきもの
	 * @param after  後ろにあるべきもの
	 */
	protected void addInvalidOrderMessage(String before, String after) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INVALID_ORDER, after, before);
	}
	
	/**
	 * 検索（前方一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：前方一致、false：前方一致でない)
	 */
	protected boolean isForwardMatch(String condition, String value) {
		return PlatformUtility.isForwardMatch(condition, value);
	}
	
	/**
	 * 検索（部分一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：部分一致、false：部分一致でない)
	 */
	protected boolean isBroadMatch(String condition, String value) {
		return PlatformUtility.isBroadMatch(condition, value);
	}
	
	/**
	 * 検索（完全一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：完全一致、false：完全一致でない)
	 */
	protected boolean isExactMatch(String condition, String value) {
		return PlatformUtility.isExactMatch(condition, value);
	}
	
	/**
	 * 社員名が検索条件に合致しているか確認。
	 * @param condition 条件
	 * @param firstName 名
	 * @param lastName 姓
	 * @return 確認結果(true:検索条件合致、false:検索条件合致していない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isHumanNameMatch(String condition, String firstName, String lastName) throws MospException {
		// 名で確認
		if (isBroadMatch(condition, firstName)) {
			return true;
		}
		// 姓で確認
		if (isBroadMatch(condition, lastName)) {
			return true;
		}
		// 姓＋名で確認
		if (isBroadMatch(condition, lastName + firstName)) {
			return true;
		}
		// 氏名で確認
		if (isBroadMatch(condition, MospUtility.getHumansName(firstName, lastName))) {
			return true;
		}
		return false;
	}
	
	/**
	 * リストをソートする。<br>
	 * @param list      対象リスト
	 * @param cls       比較クラス
	 * @param isReverse 逆順序フラグ(true：逆順序、false：自然順序)
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	public void sortList(List<?> list, Class<?> cls, boolean isReverse) throws MospException {
		// 比較クラスインスタンスを取得しソート
		sortList(list, cls.getName(), isReverse);
	}
	
	/**
	 * リストをソートする。<br>
	 * @param list      対象リスト
	 * @param className 比較クラス名
	 * @param isReverse 逆順序フラグ(true：逆順序、false：自然順序)
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	public void sortList(List<?> list, String className, boolean isReverse) throws MospException {
		// 比較クラス取得
		Comparator<Object> comp = InstanceFactory.loadComparator(className);
		// 逆順序フラグ確認
		if (isReverse) {
			comp = Collections.reverseOrder(comp);
		}
		// 比較クラスインスタンスを取得しソート
		Collections.sort(list, comp);
	}
	
	/**
	 * プルダウン用配列を準備する。<br>
	 * @param length    リスト長
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列(空白)
	 */
	protected String[][] prepareSelectArray(int length, boolean needBlank) {
		// 空白行要否確認
		if (needBlank == false) {
			// リスト長分の配列を返す
			return new String[length][2];
		}
		
		// 配列宣言(リスト長+空白行)
		String[][] array = new String[length + 1][2];
		// 空白行設定
		array[0][0] = "";
		array[0][1] = "";
		return array;
	}
	
	/**
	 * コード等を付加した名称を取得する。<br>
	 * コード等部の文字列長は、lengthに依る。<br>
	 * @param code   コード等
	 * @param name   名称
	 * @param length コード等長
	 * @return コード等を付加した名称
	 */
	protected String getCodedName(String code, String name, int length) {
		// 文字列準備
		StringBuffer sb = new StringBuffer();
		// 必要な空白数を取得(コードと名称の間の空白分1を加算)
		int doubleBytes = (length - code.length() + 1) / 2;
		int singleBytes = (length - code.length() + 1) % 2;
		// コード付加
		sb.append(code);
		// 全角空白設定
		for (int i = 0; i < doubleBytes; i++) {
			sb.append(MospConst.STR_DB_SPACE);
		}
		// 半角空白設定
		for (int i = 0; i < singleBytes; i++) {
			sb.append(MospConst.STR_SB_SPACE);
		}
		// 名称付加
		sb.append(name);
		return sb.toString();
	}
	
	/**
	 * 対象文字列を正規表現の区切りで分割し、リスト(String)にする。<br>
	 * 前後に空白が存在した場合は、その空白を除く。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return リスト(String)
	 */
	protected List<String> asList(String target, String regex) {
		return MospUtility.asList(split(target, regex));
	}
	
	/**
	 * 対象文字列を正規表現の区切りで分割し、配列(String)にする。<br>
	 * 前後に空白が存在した場合は、その空白を除く。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return 配列(String)
	 */
	protected String[] split(String target, String regex) {
		return MospUtility.split(target, regex);
	}
	
	/**
	 * リスト(String)を区切文字で区切った文字列にする。<br>
	 * @param list      対象リスト(String)
	 * @param separator 区切文字
	 * @return リスト(String)
	 */
	protected String toSeparatedString(List<String> list, String separator) {
		// 字列準備
		StringBuffer sb = new StringBuffer();
		// 個人ID毎に処理
		for (String str : list) {
			// 文字列追加
			sb.append(str);
			sb.append(separator);
		}
		// 最終区切文字除去
		if (sb.length() > 0) {
			sb.delete(sb.lastIndexOf(separator), sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * 配列(String)を区切文字で区切った文字列にする。<br>
	 * @param array     対象配列(String)
	 * @param separator 区切文字
	 * @return リスト(String)
	 */
	protected String toSeparatedString(String[] array, String separator) {
		// 字列準備
		StringBuffer sb = new StringBuffer();
		// 個人ID毎に処理
		for (String str : array) {
			// 文字列追加
			sb.append(str);
			sb.append(separator);
		}
		// 最終区切文字除去
		if (sb.length() > 0) {
			sb.delete(sb.lastIndexOf(separator), sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * 重複がない区切り文字データに変更する。<br>
	 * 区切り文字データ内で値が重複している場合、
	 * 重複がない区切り文字データに変更する。<br>
	 * @param values 重複している値
	 * @param separator 区切文字
	 * @return 重複がない区切り文字データ
	 */
	protected String overlapValue(String values, String separator) {
		List<String> idList = new ArrayList<String>();
		// 個人ID毎に確認
		for (String value : asList(values, separator)) {
			// 値がない場合
			if (idList.contains(value)) {
				continue;
			}
			// 値追加
			idList.add(value);
		}
		return toSeparatedString(idList, separator);
	}
	
	/**
	 * 連番(文字列)をフォーマットの形式で発行する。<br>
	 * @param sequenceNo 連番値
	 * @param format    連番フォーマット
	 * @return 連番(文字列)
	 */
	protected String issueSequenceNo(long sequenceNo, String format) {
		// フォーマット定義
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(sequenceNo);
	}
	
	/**
	 * 数値を取得する(String→Integer)。<br>
	 * 数値の取得に失敗した場合は、nullを返す。<br>
	 * @param value 値(String)
	 * @return 値(Integer)
	 */
	protected Integer getInteger(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 数値を取得する(String→Double)。<br>
	 * 数値の取得に失敗した場合は、nullを返す。<br>
	 * @param value 値(String)
	 * @return 値(Double)
	 */
	protected Double getDouble(String value) {
		try {
			return new Double(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 必須確認を行う。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkRequired(Object value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRequired(value) == false) {
			// エラーメッセージ追加
			addRequiredErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 文字列長(入力文字数)を確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param length 文字数
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkInputLength(String value, int length, String fieldName, Integer row) {
		// 文字列長(入力文字数)確認
		if (ValidateUtility.chkInputLength(value, length) == false) {
			// エラーメッセージ追加
			String[] rep = { fieldName, String.valueOf(length) };
			// 桁数エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, rep);
		}
	}
	
	/**
	 * 文字列長(最大文字数)を確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkLength(String value, int maxLength, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkLength(value, maxLength) == false) {
			// エラーメッセージ追加
			addMaxLengthErrorMessage(fieldName, maxLength, row);
		}
	}
	
	/**
	 * バイト数(表示上)を確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大バイト数(表示上)
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkByteLength(String value, int maxLength, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkByteLength(value, maxLength) == false) {
			// エラーメッセージ追加
			addByteMaxLengthErrorMessage(fieldName, maxLength, row);
		}
	}
	
	/**
	 * 対象文字列が半角英数字又は記号(-_.@)であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkMailAddress(String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[!#-9A-~]+@+[A-Za-z0-9]+.+[^.]$", value) == false) {
			// エラーメッセージ追加
			addUserIdErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 対象文字列が半角英数字又は記号(-_.@)であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkUserId(String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[._@A-Za-z0-9-]*", value) == false) {
			// エラーメッセージ追加
			addUserIdErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 対象文字列が半角英数字であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkTypeCode(String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[A-Za-z0-9]*", value) == false) {
			// エラーメッセージ追加
			addTypeCodeErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 対象文字列が半角数字であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkTypeNumber(String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[0-9]*", value) == false) {
			// エラーメッセージ追加
			addTypeNumberErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 対象文字列が半角英数字であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkTypeKana(String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[｡-ﾟ -~]*", value) == false) {
			// エラーメッセージ追加
			addTypeKanaErrorMessage(fieldName, row);
		}
	}
	
	/**
	 * 対象文字列が利用可能文字列であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value      確認対象文字列
	 * @param availables 利用可能文字列リスト
	 * @param fieldName  確認対象フィールド名
	 * @param row        行インデックス
	 */
	protected void checkAvailableChars(String value, List<String> availables, String fieldName, Integer row) {
		// 利用可能文字列リストに確認対象文字列が含まれない場合
		if (availables.contains(value) == false) {
			// エラーメッセージ追加
			PlatformMessageUtility.addErrorAvailableChars(mospParams, fieldName, availables, row);
		}
	}
	
	/**
	 * 対象文字列が小数であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value        確認対象文字列
	 * @param integerDigit 整数部桁数
	 * @param decimalDigit 小数部桁数
	 * @param fieldName    確認対象フィールド名
	 * @param row          行インデックス
	 */
	protected void checDecimal(String value, int integerDigit, int decimalDigit, String fieldName, Integer row) {
		// 小数を確認
		if (ValidateUtility.chkDecimal(value, integerDigit, decimalDigit) == false) {
			// エラーメッセージ追加
			PlatformMessageUtility.addErrorCheckDecimal(mospParams, fieldName, integerDigit, decimalDigit, row);
		}
	}
	
	/**
	 * 無効フラグが妥当であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value 確認対象
	 * @param row   行インデックス
	 */
	protected void checkInactivateFlag(int value, Integer row) {
		// 有効でも無効でもない場合
		if (value != MospConst.INACTIVATE_FLAG_OFF && value != MospConst.INACTIVATE_FLAG_ON) {
			// エラーメッセージ追加
			MessageUtility.addErrorMessageActivateOrInactivateInvalid(mospParams, row);
		}
	}
	
	/**
	 * 削除フラグが妥当であるかを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象値
	 * @param row       行インデックス
	 */
	protected void checkDeleteFlag(int value, Integer row) {
		// フラグがOFFでもONでもない場合
		if (value != MospConst.DELETE_FLAG_OFF && value != MospConst.DELETE_FLAG_ON) {
			// エラーメッセージ追加
			MessageUtility.addErrorMessageDeleteFlagInvalid(mospParams, row);
		}
	}
	
	/**
	 * フラグがOFF(0)又はON(1)のいずれかであるかを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象値
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	protected void checkFlag(int value, String fieldName, Integer row) {
		// フラグがOFFでもONでもない場合
		if (PlatformUtility.isFlagOff(value) == false && PlatformUtility.isFlagOn(value) == false) {
			// エラーメッセージ追加
			MessageUtility.addErrorMessageFlagInvalid(mospParams, fieldName, row);
		}
	}
	
	/**
	 * 限界値を超えているかを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象値
	 * @param fieldName 確認対象フィールド名
	 * @param limit     確認対象限界値
	 * @param row       行インデックス
	 */
	protected void checkUnderLimit(double value, String fieldName, int limit, Integer row) {
		// 対象値と限界値を比較
		if (value < limit) {
			// エラーメッセージ追加
			MessageUtility.addErrorMessageUnderLimit(mospParams, fieldName, limit, row);
		}
	}
	
	/**
	 * 限界値を超えているかを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param value     確認対象値
	 * @param fieldName 確認対象フィールド名
	 * @param limit     確認対象限界値
	 * @param row       行インデックス
	 */
	protected void checkOverLimit(double value, String fieldName, int limit, Integer row) {
		// 対象値と限界値を比較
		if (value > limit) {
			// エラーメッセージ追加
			MessageUtility.addErrorMessageOverLimit(mospParams, fieldName, limit, row);
		}
	}
	
	/**
	 * 入力内容の妥当性が確認できない場合のメッセージを設定する。<br>
	 */
	protected void addInputDataInvalidErrorMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT);
	}
	
	/**
	 * 登録情報から必須項目が取得できなかった場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 */
	protected void addRequiredErrorMessage(String fieldName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED, getRowedFieldName(fieldName, row));
	}
	
	/**
	 * 登録情報の文字列が最大長を超えている場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param maxLength 対象フィールド文字列最大長
	 * @param row       対象行インデックス
	 */
	protected void addMaxLengthErrorMessage(String fieldName, int maxLength, Integer row) {
		String[] rep = { getRowedFieldName(fieldName, row), String.valueOf(maxLength) };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_MAX_LENGTH_ERR, rep);
	}
	
	/**
	 * 登録情報の文字列がバイト数(表示上)最大長を超えている場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param maxLength 対象フィールドバイト数(表示上)最大長
	 * @param row       対象行インデックス
	 */
	protected void addByteMaxLengthErrorMessage(String fieldName, int maxLength, Integer row) {
		// 最大長の半分(切捨)を取得
		int halfLength = maxLength / 2;
		String[] rep = { getRowedFieldName(fieldName, row), String.valueOf(halfLength), String.valueOf(maxLength) };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_BYTE_LENGTH_ERR, rep);
	}
	
	/**
	 * 登録情報の文字列が半角英数字又は記号("_"、"."、"-"、"@")でない場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 */
	protected void addUserIdErrorMessage(String fieldName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_ALP_SIGN_NUM_CHECK_AMP, getRowedFieldName(fieldName, row));
	}
	
	/**
	 * 登録情報の文字列が半角英数字でない場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 */
	protected void addTypeCodeErrorMessage(String fieldName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_ALP_NUM_CHECK_AMP, getRowedFieldName(fieldName, row));
	}
	
	/**
	 * 登録情報の文字列が半角数字でない場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 */
	protected void addTypeNumberErrorMessage(String fieldName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NUMBER_CHECK_AMP, getRowedFieldName(fieldName, row));
	}
	
	/**
	 * 登録情報の文字列がカナでない場合のエラーメッセージを追加する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 */
	protected void addTypeKanaErrorMessage(String fieldName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, getRowedFieldName(fieldName, row));
	}
	
	/**
	 * 該当データが既に更新されていた場合(排他)のエラーメッセージを追加する。<br>
	 */
	protected void addExclusiveErrorMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_UPDATE_OTHER_USER);
	}
	
	/**
	 * 社員が退職している場合のメッセージを設定する。<br>
	 */
	protected void addEmployeeRetiredMessage() {
		PlatformMessageUtility.addErrorEmployeeRetired(mospParams, null);
	}
	
	/**
	 * 社員が休職している場合のメッセージを設定する。<br>
	 */
	protected void addEmployeeSuspendedMessage() {
		PlatformMessageUtility.addErrorEmployeeSuspended(mospParams, null);
	}
	
	/**
	 * 行番号が付加されたフィールド名を取得する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	protected String getRowedFieldName(String fieldName, Integer row) {
		// 対象行インデックス確認
		if (row == null) {
			return fieldName;
		}
		// メッセージ置換文字列追加
		return getRowColonName(row.intValue()) + fieldName;
	}
	
	/**
	 * 行：名称を取得する。
	 * @param row 対象行インデックス
	 * @return 行：名称
	 */
	protected String getRowColonName(int row) {
		return mospParams.getName("Row") + String.valueOf(row + 1) + mospParams.getName("Colon");
	}
	
	/**
	 * 社員コード名称を取得する。<br>
	 * @return 社員コード名称
	 */
	protected String getNameEmployeeCode() {
		return PlatformNamingUtility.employeeCode(mospParams);
	}
	
	/**
	 * 有効日名称を取得する。<br>
	 * @return 有効日名称
	 */
	protected String getNameActivateDate() {
		return PlatformNamingUtility.activateDate(mospParams);
	}
	
	/**
	 * 所属名称を取得する。<br>
	 * @return 所属名称
	 */
	protected String getNameSection() {
		return mospParams.getName("Section");
	}
	
	/**
	 * 職位名称を取得する。<br>
	 * @return 職位名称
	 */
	protected String getNamePosition() {
		return mospParams.getName("Position");
	}
	
	/**
	 * 一度に取得する個人情報の最大数の取得を取得する。<br>
	 * @return 一度に取得する個人情報の最大数
	 */
	protected int getPersonalIdsMaxIndex() {
		return mospParams.getApplicationProperty(PlatformConst.APP_PERSONAL_IDS_MAX_INDEX,
				PlatformConst.DEFAULT_PERSONAL_IDS_MAX_INDEX_VALUE);
	}
	
	/**
	 * 個人ID配列を取得する。<br>
	 * @param srcArray  ソース個人ID配列
	 * @param fromIndex 配列の開始位置
	 * @param length    配列の長さ
	 * @return 個人ID配列
	 */
	protected String[] getPersonalIds(String[] srcArray, int fromIndex, int length) {
		int toIndex = fromIndex + length;
		if (toIndex > srcArray.length) {
			toIndex = srcArray.length;
		}
		String[] personalIds = new String[toIndex - fromIndex];
		for (int cnt = 0; cnt < personalIds.length; cnt++) {
			personalIds[cnt] = srcArray[fromIndex + cnt];
		}
		return personalIds;
	}
	
	/**
	 * 個人ID配列を取得する。<br>
	 * @param personalIdList ソース個人IDリスト
	 * @param fromIndex      配列の開始位置
	 * @param length         配列の長さ
	 * @return 個人ID配列
	 */
	protected String[] getPersonalIdsSubList(List<String> personalIdList, int fromIndex, int length) {
		return getPersonalIds(personalIdList.toArray(new String[personalIdList.size()]), fromIndex, length);
	}
	
	/**
	 * 個人ID配列を取得する。<br>
	 * @param list 対象リスト(個人IDを有するDTOのリスト)
	 * @return 個人ID配列
	 */
	protected String[] getPersonalIds(List<? extends PersonalIdDtoInterface> list) {
		// 個人IDリストを準備
		List<String> personalIds = new ArrayList<String>();
		// 情報毎に処理
		for (PersonalIdDtoInterface dto : list) {
			personalIds.add(dto.getPersonalId());
		}
		// 個人ID配列を取得
		return MospUtility.toArray(personalIds);
	}
	
	/**
	 * 個人ID配列を取得する。<br>
	 * @param list      対象リスト(個人IDを有するDTOのリスト)
	 * @param fromIndex 配列の開始位置
	 * @param length    配列の長さ
	 * @return 個人ID配列
	 */
	protected String[] getPersonalIds(List<? extends PersonalIdDtoInterface> list, int fromIndex, int length) {
		int toIndex = fromIndex + length;
		if (toIndex > list.size()) {
			toIndex = list.size();
		}
		// 個人IDリストを準備
		List<String> personalIds = new ArrayList<String>();
		// 情報毎に処理
		for (PersonalIdDtoInterface dto : list.subList(fromIndex, toIndex)) {
			personalIds.add(dto.getPersonalId());
		}
		// 個人ID配列を取得
		return MospUtility.toArray(personalIds);
	}
	
	/**
	 * 部分リストを取得する。<br>
	 * @param list      ソースリスト
	 * @param fromIndex 開始位置
	 * @param length    長さ
	 * @return 部分リスト
	 */
	protected List<?> getSubList(List<?> list, int fromIndex, int length) {
		int toIndex = fromIndex + length;
		if (toIndex > list.size()) {
			toIndex = list.size();
		}
		return list.subList(fromIndex, toIndex);
	}
}
