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

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇情報参照インターフェース。
 */
public interface PaidHolidayInfoReferenceBeanInterface extends PaidHolidayRequestReferenceBeanInterface {
	
	/**
	 * 有給休暇情報を取得する。<br><br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇情報を取得する。<br>
	 * 支給日数/時間、廃棄日数/時間、利用日数/時間を累計で取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @return 有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate, boolean containNotApproved)
			throws MospException;
	
	/**
	 * 承認済の有給休暇情報を取得する。<br>
	 * 個別有給休暇確認画面で利用する。<br>
	 * 支給日数/時間、廃棄日数/時間、利用日数/時間を
	 * 累計ではなく月毎に取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 対象締期間初日
	 * @param lastDate 対象締期間最終日
	 * @return 有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayReferenceInfo(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 申請用有給休暇申請可能数リストを取得する。<br><br>
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は残日数から減算する。
	 * 但し、1次戻は減算しない。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇申請可能数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayPossibleRequestForRequestList(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 前年度残、今年度付与日数、対象期間利用(休暇申請)日数を取得する。<br>
	 * 統計情報一覧で使用する。<br>
	 * @param humanDto    人事基本情報
	 * @param displayYear 表示年度
	 * @param targetDate  対象日
	 * @return 前年度残、 今年度付与日数、対象期間利用(休暇申請)日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getSubordinateFiscalPaidHolidayInfo(HumanDtoInterface humanDto, int displayYear,
			Date targetDate) throws MospException;
	
	/**
	 * 表示用有給休暇リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 表示用有給休暇リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<Map<String, Object>> getPaidHolidayDataListForView(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇次回付与予定を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与予定
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getNextGivingInfo(String personalId) throws MospException;
	
	/**
	 * 有給休暇次回付与予定を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇次回付与予定
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getNextGivingInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇次回付与予定日を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getNextManualGivingDate(String personalId) throws MospException;
	
	/**
	 * 有給休暇次回付与予定日数を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与予定日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNextManualGivingDaysAndHours(String personalId) throws MospException;
	
	/**
	 * 有給休暇付与回数を取得する。<br>
	 * 入社初年度を1、次年度を2、次々年度を3とする。
	 * @param personalId 個人ID
	 * @param cutoffDate 締日
	 * @return 有給休暇付与回数(集計月における有給休暇付与がない場合はnull)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Integer getNumberOfYearsFromEntranceForPaidHolidayGrant(String personalId, Date cutoffDate) throws MospException;
	
	/**
	 * 出勤率を取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param grant 付与回数
	 * @return 出勤率
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	double getAttendanceRatio(String personalId, Date activateDate, int calculationYear, int calculationMonth,
			int grant) throws MospException;
	
	/**
	 * 有給休暇時間休の時間単位限度を取得する。
	 * 時間休申請が承認済みの場合計算する。
	 * @param personalId        個人ID
	 * @param targetDate        対象日
	 * @param isCompleted       承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param holidayRequestDto 休暇申請DTO
	 * @return 時間単位残限度配列：○日、○時間、時間限度
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int[] getHolidayTimeUnitLimit(String personalId, Date targetDate, boolean isCompleted,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException;
	
	/**
	 * 対象日時点で利用可能な最も古い有給休暇付与日を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param requestDay 申請日数
	 * @param requestHour 申請時間数
	 * @return 対象日時点で利用可能な最も古い有給休暇付与日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Date getOldestGrantDate(String personalId, Date targetDate, double requestDay, int requestHour)
			throws MospException;
	
	/**
	 * 有給休暇申請可能かどうか確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param requestDay 申請日数
	 * @param requestHour 申請時間数
	 * @return 有給休暇申請可能の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean canPaidHolidayRequest(String personalId, Date targetDate, double requestDay, int requestHour)
			throws MospException;
	
	/**
	 * テーブルロック。
	 * @throws MospException SQL例外が発生した場合
	 */
	void lockTables() throws MospException;
	
	/**
	 * テーブルロック解除。
	 * @throws MospException SQL例外が発生した場合
	 */
	void unlockTable() throws MospException;
	
	/**
	 * 勤怠関連マスタ参照クラスを設定する。<br>
	 * <br>
	 * 勤怠関連マスタ参照クラスはinitBeanでは生成せず、
	 * 別のBeanから設定される。<br>
	 * より多くのBeanで勤怠関連マスタ参照クラスを共有することで、
	 * パフォーマンスの向上を図る。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照クラス
	 */
	void setTimeMasterBean(TimeMasterBeanInterface timeMaster);
	
}
