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
package jp.mosp.time.bean;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.entity.HolidayRequestEntityInterface;

/**
 * 休暇申請参照処理インターフェース。<br>
 */
public interface HolidayRequestReferenceBeanInterface {
	
	/**
	 * 休暇申請リスト取得。
	 * <p>
	 * 個人IDと申請日から休暇申請リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休暇申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 休暇申請リスト取得。<br>
	 * 個人IDと申請日から休暇申請リストを取得。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休暇申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date requestDate)
			throws MospException;
	
	/**
	 * 休暇申請からレコードを取得する。<br>
	 * 個人ID、申請日、休暇種別1、休暇種別2、休暇範囲、時休開始時刻で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestStartDate 申請開始日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @param startTime 時休開始時刻
	 * @return 休暇申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime) throws MospException;
	
	/**
	 * 休暇申請からレコードを取得する。<br>
	 * 個人ID、申請日、休暇種別1、休暇種別2、休暇範囲、時休終了時刻で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestStartDate 申請開始日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @param endTime 時休終了時刻
	 * @return 休暇申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForEndTimeKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date endTime) throws MospException;
	
	/**
	 * 休暇申請からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 休暇申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 休暇申請データ取得。
	 * <p>
	 * レコード識別IDから休暇申請データを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 休暇申請データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 承認済申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate 申請終了日
	 * @return 承認済申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getApprovedDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException;
	
	/**
	 * 承認済有給休暇申請数を取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate 申請終了日
	 * @return 承認済有給休暇申請数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getApprovedPaidHolidayReqeust(String personalId, Date acquisitionDate, Date requestStartDate,
			Date requestEndDate) throws MospException;
	
	/**
	 * 個人IDと取得日から有給休暇時間承認状態別休数回数をマップで取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayRequestDto 休暇申請DTO
	 * @return 休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<String, Integer> getTimeHolidayStatusTimesMap(String personalId, Date acquisitionDate,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException;
	
	/**
	 * 申請リストを取得する。<br>
	 * 承認状態が下書、取下の申請を除く。
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate 申請終了日
	 * @return 申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getRequestDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException;
	
	/**
	 * 休暇の利用日数及び利用時間数を取得する。<br>
	 * 取下以外の休暇申請(下書を含む)を対象とする。<br>
	 * <br>
	 * @param personalId       個人ID
	 * @param firstDate        期間初日
	 * @param lastDate         期間最終日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param acquisitionDates 休暇取得日群
	 * @return 休暇の利用日数(キー)及び利用時間数(値)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SimpleEntry<Double, Integer> getHolidayUses(String personalId, Date firstDate, Date lastDate, int holidayType1,
			String holidayType2, Collection<Date> acquisitionDates) throws MospException;
	
	/**
	 * 有給休暇申請理由必須設定を確認する。<br>
	 * @return 確認結果(true:必須、false:任意)
	 */
	boolean isPaidHolidayReasonRequired();
	
	/**
	 * 基本情報チェック。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 差戻・下書・取下以外の対象有給情報で作成している休暇申請一覧を取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 有給休暇取得日
	 * @return 差戻・下書・取下以外の対象有給情報で作成している休暇申請一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getUsePaidHolidayDataList(String personalId, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 休暇申請エンティティを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestEntityInterface getHolidayRequestEntity(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
}
