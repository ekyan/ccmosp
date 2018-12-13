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
package jp.mosp.time.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MenuUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformNamingUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 勤怠管理における有用なメソッドを提供する。<br><br>
 */
public class TimeUtility {
	
	/**
	 * 日付休暇設定における取得期間(無期限)の年。<br>
	 * Date型の最大年。<br>
	 */
	public static final int		DATE_UNLIMITED_YEAR				= 5874897;
	
	/**
	 * 日付休暇設定における取得期間(無期限)の日。<br>
	 */
	public static final int		DATE_YEAR_LAST_MONTH			= 31;
	
	/**
	 * メインメニューキー(勤怠報告)。<br>
	 */
	public static final String	MAIN_MENU_TIME_INMPUT			= "menuTimeInput";
	
	/**
	 * メニューキー(勤怠一覧)。<br>
	 */
	public static final String	MENU_ATTENDANCE_LIST			= "AttendanceList";
	
	/**
	 * メニューキー(残業申請)。<br>
	 */
	public static final String	MENU_OVERTIME_REQUEST			= "OvertimeRequest";
	
	/**
	 * メニューキー(休暇申請)。<br>
	 */
	public static final String	MENU_HOLIDAY_REQUEST			= "HolidayRequest";
	
	/**
	 * メニューキー(振出・休出申請)。<br>
	 */
	public static final String	MENU_WORK_ON_HOLIDAY_REQUEST	= "WorkOnHolidayRequest";
	
	/**
	 * メニューキー(代休申請)。<br>
	 */
	public static final String	MENU_SUB_HOLIDAY_REQUEST		= "SubHolidayRequest";
	
	/**
	 * メニューキー(勤務形態変更申請)。<br>
	 */
	public static final String	MENU_WORK_TYPE_CHANGE_REQUEST	= "WorkTypeChangeRequest";
	
	/**
	 * メニューキー(時差出勤申請)。<br>
	 */
	public static final String	MENU_DIFFERENCE_REQUEST			= "DifferenceRequest";
	
	/**
	 * メニューキー(承認解除申請)。<br>
	 */
	public static final String	MENU_CANCELLATION_REQUEST		= "CancellationRequest";
	
	/**
	 * 1時間あたりのミリ秒数。<br>
	 */
	public static final int		MILLI_SEC_PER_HOUR				= 3600000;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeUtility() {
		// 処理無し
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * 時刻は、デフォルト時刻(1970/01/01 00:00:00)を基準として取得する。<br>
	 * @param date 対象日
	 * @param time 時刻
	 * @return 対象日における時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(Date date, Date time) throws MospException {
		// 時刻を確認
		if (time == null) {
			return null;
		}
		// 時分を取得
		int hour = DateUtility.getHour(time, DateUtility.getDefaultTime());
		int minute = DateUtility.getMinute(time);
		// 対象日における時刻を取得
		return getDateTime(date, hour, minute);
	}
	
	/**
	 * デフォルト日時における時刻を取得する。<br>
	 * <br>
	 * @param hour   時間
	 * @param minute 分
	 * @return デフォルト日時の時刻
	 * @throws MospException MospUtility.getInt(minute)
	 */
	public static Date getDefaultTime(String hour, String minute) throws MospException {
		// デフォルト日時の時刻を取得
		return getDateTime(DateUtility.getDefaultTime(), hour, minute);
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * <br>
	 * @param date   対象日
	 * @param hour   時(24時間制)
	 * @param minute 分
	 * @return 対象日における時刻
	 */
	public static Date getDateTime(Date date, String hour, String minute) {
		// 対象日における時刻を取得
		return getDateTime(date, MospUtility.getInt(hour), MospUtility.getInt(minute));
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * <br>
	 * {@link DateUtility#getTime(String, String)}と異なり、
	 * 24時を超えた場合は翌日の時刻として返す。<br>
	 * <br>
	 * 例：<br>
	 * 対象日が2013/04/01で時刻が9:00だった場合、2013/04/01 09:00を返す。
	 * 対象日が2013/04/01で時刻が25:00だった場合、2013/04/02 01:00を返す。
	 * <br>
	 * @param date   対象日
	 * @param hour   時(24時間制)
	 * @param minute 分
	 * @return 対象日における時刻
	 */
	public static Date getDateTime(Date date, int hour, int minute) {
		// 対象日がnullである場合
		if (date == null) {
			// nullを取得
			return null;
		}
		// 対象日から時間以下の情報を除いた日付を取得
		Date dateTime = DateUtility.getDate(date);
		// 時間を加算
		dateTime = DateUtility.addHour(dateTime, hour);
		// 分を加算
		dateTime = DateUtility.addMinute(dateTime, minute);
		// 対象日における時刻を取得
		return dateTime;
	}
	
	/**
	 * デフォルト日時からの差を分で取得する。<br>
	 * @param targetTime 対象時刻
	 * @return デフォルト日時からの差(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static int getMinutes(Date targetTime) throws MospException {
		// 対象日(デフォルト日時)からの差を分で取得
		return getMinutes(targetTime, DateUtility.getDefaultTime());
	}
	
	/**
	 * 対象日からの差を分で取得する。<br>
	 * @param targetTime 対象時刻
	 * @param targetDate 対象日
	 * @return 対象日からの差(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static int getMinutes(Date targetTime, Date targetDate) throws MospException {
		// 対象時刻確認
		if (targetTime == null || targetDate == null) {
			return 0;
		}
		// ミリ秒で差を取得
		long defference = targetTime.getTime() - targetDate.getTime();
		// 時間に変換
		return (int)(defference / (TimeBean.MILLI_SEC_PER_HOUR / TimeConst.CODE_DEFINITION_HOUR));
	}
	
	/**
	 * 時刻の差を分で取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 時刻の差(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static int getDifferenceMinutes(Date startTime, Date endTime) throws MospException {
		// 対象時刻確認
		long start = startTime.getTime();
		long end = endTime.getTime();
		int difference = (int)(end - start) / TimeConst.CODE_DEFINITION_MINUTE_MILLI_SEC;
		
		return difference;
	}
	
	/**
	 * 対象年月及び締日から締期間初日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffFirstDate(int cutoffDate, int targetYear, int targetMonth) throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate);
		// 締期間初日設定
		return MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, cutoffDay);
	}
	
	/**
	 * 対象年月及び締日から締期間最終日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffLastDate(int cutoffDate, int targetYear, int targetMonth) throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate);
		// 締期間最終日設定
		return MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, cutoffDay);
	}
	
	/**
	 * 調整した締日を取得する。
	 * @param cutoffDay 締日
	 * @return 調整した締日
	 */
	protected static int adjustCutoffDay(int cutoffDay) {
		// 月末締の場合
		if (cutoffDay == TimeConst.CUTOFF_DATE_LAST_DAY) {
			// 締期間初日設定(対象年月の初日)
			return cutoffDay;
		}
		// 当月締日判断
		if (cutoffDay > TimeConst.CUTOFF_DATE_THIS_MONTH_MAX) {
			// 締期間初日設定
			return cutoffDay;
		} else {
			// 締期間初日設定
			return cutoffDay + MonthUtility.TARGET_DATE_NEXT_MONTH;
		}
	}
	
	/**
	 * 対象年月における締期間の基準日を取得する。<br>
	 * @param cutoffDate 締日
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締期間基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffTermTargetDate(int cutoffDate, int targetYear, int targetMonth) throws MospException {
		return getCutoffLastDate(cutoffDate, targetYear, targetMonth);
	}
	
	/**
	 * 対象年月における締期間の集計日を取得する。<br>
	 * @param cutoffDate 締日
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締期間集計日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffCalculationDate(int cutoffDate, int targetYear, int targetMonth) throws MospException {
		return getCutoffLastDate(cutoffDate, targetYear, targetMonth);
	}
	
	/**
	 * 対象日付及び締日から対象年月日が含まれる締月を取得する。<br>
	 * @param cutoffDate 締日
	 * @param targetDate 対象日付
	 * @return 締月(締月初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffMonth(int cutoffDate, Date targetDate) throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate);
		// 対象年月日が含まれる締月を取得
		return MonthUtility.getTargetYearMonth(targetDate, cutoffDay);
	}
	
	/**
	 * 勤怠管理用機能コードセットを取得する。<br>
	 * @return 勤怠管理用機能コードセット
	 */
	public static Set<String> getTimeFunctionSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(TimeConst.CODE_FUNCTION_WORK_MANGE);
		set.add(TimeConst.CODE_FUNCTION_OVER_WORK);
		set.add(TimeConst.CODE_FUNCTION_VACATION);
		set.add(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_DIFFERENCE);
		set.add(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE);
		return set;
	}
	
	/**
	 * 休暇設定における取得期間(無期限)を取得する。
	 * @return 無期限
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getUnlimitedDate() throws MospException {
		return DateUtility.getDate(DATE_UNLIMITED_YEAR, TimeConst.CODE_DEFINITION_YEAR, DATE_YEAR_LAST_MONTH);
	}
	
	/**
	 * 対象休暇付与情報が無期限(無制限)であるかを確認する。<br>
	 * @param dto 休暇付与情報
	 * @return 確認結果(true：対象休暇付与情報が無期限(無制限)である、false：そうでない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isUnlimited(HolidayDataDtoInterface dto) throws MospException {
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 無制限でないと判断
			return false;
		}
		// 取得期限が無期限と同じであるかを確認
		return isUnlimited(dto.getHolidayLimitDate());
	}
	
	/**
	 * 取得期限が無期限(無制限)であるかを確認する。<br>
	 * @param limitDate 取得期限
	 * @return 確認結果(true：対象休暇付与情報が無期限(無制限)である、false：そうでない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isUnlimited(Date limitDate) throws MospException {
		// 取得期限が無期限と同じであるかを確認
		return DateUtility.isSame(getUnlimitedDate(), limitDate);
	}
	
	/**
	 * 初日から最終日の日リストを取得する。<br>
	 * @param firstDate 初日
	 * @param lastDate  最終日
	 * @return 日リスト
	 */
	public static List<Date> getDateList(Date firstDate, Date lastDate) {
		// 日リスト準備
		List<Date> list = new ArrayList<Date>();
		// 日の確認
		if (firstDate == null || lastDate == null) {
			// 空リスト
			return list;
		}
		// 初日を基に対象日を準備
		Date date = (Date)firstDate.clone();
		// 対象日毎に処理
		while (!date.after(lastDate)) {
			list.add(date);
			date = DateUtility.addDay(date, 1);
		}
		return list;
	}
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	public static int getRoundMinute(int time, int type, int unit) {
		if (time <= 0 || type == 0 || unit <= 0) {
			return time;
		}
		if (type == 1 || type == 2) {
			// 余り
			int remainder = time % unit;
			if (remainder == 0) {
				// 余りが0の場合はそのまま返す
				return time;
			}
			int rounded = time - remainder;
			if (type == 1) {
				// 切捨て
				return rounded;
			} else if (type == 2) {
				// 切上げ
				return rounded + unit;
			}
		}
		return time;
	}
	
	/**
	 * 時間をデリミタ区切で取得する。
	 * @param minute 分
	 * @param delimiter デリミタ
	 * @return 時間(デリミタ区切)
	 */
	public static String getTimeDelimiterSeparated(int minute, String delimiter) {
		// 時間
		int h = minute / TimeConst.CODE_DEFINITION_HOUR;
		// 分
		int m = minute % TimeConst.CODE_DEFINITION_HOUR;
		StringBuffer sb = new StringBuffer();
		sb.append(h);
		sb.append(delimiter);
		if (m < 10) {
			sb.append(0);
		}
		sb.append(m);
		return sb.toString();
	}
	
	/**
	 * 勤務形態コードが所定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日である、false：所定休日でない)
	 */
	public static boolean isPrescribedHoliday(String workTypeCode) {
		// 所定休日が勤務形態コードと一致するかを確認
		return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが法定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日である、false：法定休日でない)
	 */
	public static boolean isLegalHoliday(String workTypeCode) {
		// 法定休日が勤務形態コードと一致するかを確認
		return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが所定休日又は法定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日又は法定休日である、false：そうでない)
	 */
	public static boolean isHoliday(String workTypeCode) {
		// 所定休日又は法定休日であるかを確認
		return isPrescribedHoliday(workTypeCode) || isLegalHoliday(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが所定休日出勤であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日出勤である、false：所定休日出勤でない)
	 */
	public static boolean isWorkOnPrescribedHoliday(String workTypeCode) {
		// 所定休日出勤が勤務形態コードと一致するかを確認
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが法定休日出勤であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日出勤である、false：法定休日出勤でない)
	 */
	public static boolean isWorkOnLegalHoliday(String workTypeCode) {
		// 所定休日出勤が勤務形態コードと一致するかを確認
		return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤怠一覧が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤怠一覧利用可、false：不可)
	 */
	public static boolean isAttendanceListAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_ATTENDANCE_LIST);
	}
	
	/**
	 * 残業申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請が有効、false：無効)
	 */
	public static boolean isOvertimeRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 残業申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請利用可、false：不可)
	 */
	public static boolean isOvertimeRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 休暇申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請が有効、false：無効)
	 */
	public static boolean isHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 休暇申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請利用可、false：不可)
	 */
	public static boolean isHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請が有効、false：無効)
	 */
	public static boolean isWorkOnHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請利用可、false：不可)
	 */
	public static boolean isWorkOnHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請が有効、false：無効)
	 */
	public static boolean isSubHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請利用可、false：不可)
	 */
	public static boolean isSubHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請が有効、false：無効)
	 */
	public static boolean isWorkTypeChangeRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請利用可、false：不可)
	 */
	public static boolean isWorkTypeChangeRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 時差出勤申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：時差出勤申請が有効、false：無効)
	 */
	public static boolean isDifferenceRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_DIFFERENCE_REQUEST);
	}
	
	/**
	 * 承認解除申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：承認解除申請が有効、false：無効)
	 */
	public static boolean isCancellationRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_CANCELLATION_REQUEST);
	}
	
	/**
	 * double型の値を少数点第三位で四捨五入する。
	 * @param value 値
	 * @return 少数点第三位で四捨五入した値
	 */
	public static double getRoundHalfUp2(double value) {
		BigDecimal bi = new BigDecimal(String.valueOf(value));
		// 小数点第三位で四捨五入
		return bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 時間を分単位から一時間単位に表記変更し、少数点第三位で四捨五入する。
	 * @param minutes 分
	 * @return 十進法に表記かつ少数点第三位で四捨五入した値
	 */
	public static double getMinuteToHour(double minutes) {
		double dotMinutes = minutes / 60;
		// 小数点第三位で四捨五入
		return getRoundHalfUp2(dotMinutes);
	}
	
	/**
	 * 休暇申請が有給休暇申請であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：有給休暇申請である、false：そうでない)
	 */
	public static boolean isPaidHolidayRequest(HolidayRequestDtoInterface dto) {
		// 休暇申請が有給休暇申請であるかを確認
		return isTheHoliday(dto, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY);
	}
	
	/**
	 * 休暇申請がストック休暇申請であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：ストック休暇申請である、false：そうでない)
	 */
	public static boolean isStockHolidayRequest(HolidayRequestDtoInterface dto) {
		// 休暇申請がストック休暇申請であるかを確認
		return isTheHoliday(dto, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE_STOCK);
	}
	
	/**
	 * 休暇申請情報がその休暇種別であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @return 確認結果(true：その休暇種別である、false：そうでない)
	 */
	public static boolean isTheHoliday(HolidayRequestDtoInterface dto, int holidayType1, int holidayType2) {
		// 休暇申請情報がその休暇種別であるかを確認
		return isTheHoliday(dto, holidayType1, String.valueOf(holidayType2));
	}
	
	/**
	 * 休暇申請情報がその休暇種別であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @return 確認結果(true：その休暇種別である、false：そうでない)
	 */
	public static boolean isTheHoliday(HolidayRequestDtoInterface dto, int holidayType1, String holidayType2) {
		// 休暇申請情報が存在しない場合
		if (dto == null) {
			// その休暇種別でないと判断
			return false;
		}
		// 休暇種別1が同じでない場合
		if (dto.getHolidayType1() != holidayType1) {
			// その休暇種別でないと判断
			return false;
		}
		// 休暇種別2が同じでない場合
		if (MospUtility.isEqual(dto.getHolidayType2(), holidayType2) == false) {
			// その休暇種別でないと判断
			return false;
		}
		// その休暇種別であると判断
		return true;
	}
	
	/**
	 * 休暇申請情報が時間休であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：時間単位休暇である、false：そうでない)
	 */
	public static boolean isHourlyHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報が時間休であるかを確認
		return isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_TIME);
	}
	
	/**
	 * 休暇申請情報がその休暇範囲であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayRange 休暇範囲
	 * @return 確認結果(true：その休暇範囲である、false：そうでない)
	 */
	public static boolean isTheHolidayRange(HolidayRequestDtoInterface dto, int holidayRange) {
		// 休暇申請情報がその休暇範囲であるかを確認
		return dto != null && dto.getHolidayRange() == holidayRange;
	}
	
	/**
	 * 休暇回数が半日であるかを確認する。
	 * @param holidayTimes 休暇回数
	 * @return 確認結果(true：休暇回数が半日である、false：そうでない)
	 */
	public static boolean isHolidayTimesHalf(double holidayTimes) {
		return holidayTimes == TimeConst.HOLIDAY_TIMES_HALF;
	}
	
	/**
	 * 休暇が残っているかを確認する。<br>
	 * @param dto         休暇付与情報
	 * @param useDays     利用日数
	 * @param useHours    利用時間数
	 * @param hoursPerDay 1日の時間数
	 * @return 確認結果(true：休暇が残っている、false：休暇が残っていない)
	 */
	public static boolean isHolidayRemain(HolidayDataDtoInterface dto, double useDays, int useHours, int hoursPerDay) {
		// 休暇の残日数及び残時間数を取得
		SimpleEntry<Double, Integer> remains = getHolidayRemains(dto, useDays, useHours, hoursPerDay);
		// 休暇が残っているかを確認(日数及び時間数が共に0以上である場合に残っていると判断)
		return remains.getKey() >= 0 && remains.getValue() >= 0;
	}
	
	/**
	 * 休暇の残日数及び残時間数を取得する。<br>
	 * @param dto         休暇付与情報
	 * @param useDays     利用日数
	 * @param useHours    利用時間数
	 * @param hoursPerDay 1日の時間数
	 * @return 休暇の残日数(キー)及び残時間数(値)
	 */
	public static SimpleEntry<Double, Integer> getHolidayRemains(HolidayDataDtoInterface dto, double useDays,
			int useHours, int hoursPerDay) {
		// 休暇の残日数及び残時間数を取得
		return getHolidayRemains(getCurrentDays(dto), getCurrentHours(dto), useDays, useHours, hoursPerDay);
	}
	
	/**
	 * 休暇の残日数及び残時間数を取得する。<br>
	 * <br>
	 * 残時間数が1日の時間数より大きい場合は日数に繰り上げ、
	 * 残時間数がマイナスである場合は日数から取り崩す。<br>
	 * <br>
	 * そのため、残数が足りない場合は残日数がマイナスとなり、
	 * 残時間数は0から1日の時間数の間の数値となる。<br>
	 * <br>
	 * 但し、1日の時間数が0(取り崩し不能)である場合は、この限りではない。
	 * <br>
	 * @param currentDays  利用前の残日数
	 * @param currentHours 利用前の残時間数
	 * @param useDays      利用日数
	 * @param useHours     利用時間数
	 * @param hoursPerDay 1日の時間数
	 * @return 休暇の残日数(キー)及び残時間数(値)
	 */
	public static SimpleEntry<Double, Integer> getHolidayRemains(double currentDays, int currentHours, double useDays,
			int useHours, int hoursPerDay) {
		// 残日数及び残時間数を準備
		double remainDays = currentDays;
		int remainHours = currentHours;
		// 利用数を減算
		remainDays -= useDays;
		remainHours -= useHours;
		// 1日の時間数が0(取り崩し不能)である場合
		if (hoursPerDay == 0) {
			// 休暇の残日数及び残時間数を取得
			return new SimpleEntry<Double, Integer>(remainDays, remainHours);
		}
		// 残時間数を確認(0の場合は取り崩しも繰り上げも無し)
		if (remainHours > 0) {
			// 残時間数が正である場合(繰り上げ)
			// 繰り上げ日数を残日数に加算
			remainDays += Math.abs(remainHours / hoursPerDay);
			// 残時間数(繰り上げた残り)を設定
			remainHours = remainHours % hoursPerDay;
		} else if (remainHours < 0) {
			// 残時間数が負である場合(取り崩し)
			// 取り崩し日数を取得
			int breakDays = Math.abs(remainHours / hoursPerDay);
			// 取り崩し日数を加算(1日の時間数以下の時間)
			breakDays += remainHours % hoursPerDay == 0 ? 0 : 1;
			// 残日数から取り崩し日数を減算
			remainDays -= breakDays;
			// 残時間数に取り崩し分を加算
			remainHours += breakDays * hoursPerDay;
		}
		// 休暇の残日数及び残時間数を取得
		return new SimpleEntry<Double, Integer>(remainDays, remainHours);
	}
	
	/**
	 * 現在の残日数を取得する。<br>
	 * @param dto 休暇付与情報
	 * @return 現在の残日数
	 */
	public static double getCurrentDays(HolidayDataDtoInterface dto) {
		// 現在の残日数を準備
		double currentDays = 0D;
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 現在の残日数を取得
			return currentDays;
		}
		// 付与数を加算し破棄数を減算
		currentDays += dto.getGivingDay();
		currentDays -= dto.getCancelDay();
		// 現在の残日数を取得
		return currentDays;
	}
	
	/**
	 * 現在の残時間数を取得する。<br>
	 * @param dto 休暇付与情報
	 * @return 現在の残時間数
	 */
	public static int getCurrentHours(HolidayDataDtoInterface dto) {
		// 現在の残時間数を準備
		int currentHours = 0;
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 現在の残時間数を取得
			return currentHours;
		}
		// 付与数を加算し破棄数を減算
		currentHours += dto.getGivingHour();
		currentHours -= dto.getCancelHour();
		// 現在の残時間数を取得
		return currentHours;
	}
	
	/**
	 * 時間文字列(時separator分suffix)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @param separator  区切り文字
	 * @param suffix     接尾文字
	 * @return 時間文字列(時separator分suffix)
	 */
	public static String getStringTime(MospParams mospParams, int time, String separator, String suffix) {
		// 時間文字列を準備
		StringBuilder sb = new StringBuilder();
		// 時及び分を取得
		int hours = getHours(time);
		int minutes = getMinutes(time);
		// 時間(分)が負であり時が0である場合
		if (time < 0 && hours == 0) {
			// マイナスを付加
			sb.append(PlatformNamingUtility.hyphen(mospParams));
		}
		// 時(時間(分)/60の商)を追加
		sb.append(hours);
		// 区切り文字を追加
		sb.append(separator);
		// 数値フォーマットを取得
		NumberFormat format = NumberFormat.getNumberInstance();
		// 整数部分最小桁数を設定
		format.setMinimumIntegerDigits(2);
		// 分(時間(分)/60の余り)を追加
		sb.append(format.format(minutes));
		// 接尾文字を追加
		sb.append(suffix);
		// 時間文字列(時separator分suffix)を取得
		return sb.toString();
	}
	
	/**
	 * 時間文字列(時separator分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time      時間(分)
	 * @param separator 区切り文字
	 * @return 時間文字列(時separator分)
	 */
	public static String getStringTime(MospParams mospParams, int time, String separator) {
		// 時間文字列(時separator分)を取得
		return getStringTime(mospParams, time, separator, MospConst.STR_EMPTY);
	}
	
	/**
	 * 時間文字列(時.分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時.分)
	 */
	public static String getStringPeriodTime(MospParams mospParams, int time) {
		//  時間文字列(時.分)を取得
		return getStringTime(mospParams, time, TimeNamingUtility.halfPeriod(mospParams));
	}
	
	/**
	 * 時間文字列(時時間分分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時時間分分)
	 */
	public static String getStringJpTime(MospParams mospParams, int time) {
		// 時間文字列(時時間分分)を取得
		return getStringTime(mospParams, time, PlatformNamingUtility.time(mospParams),
				PlatformNamingUtility.minutes(mospParams));
	}
	
	/**
	 * 時間(分)から時(時間(分)/60の商)を取得する。<br>
	 * @param time 時間(分)
	 * @return 時
	 */
	public static int getHours(int time) {
		// 時間(分)を60で割った値を取得(余りは切り捨て)
		return time / TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 時間(分)から分(時間(分)/60の余り)を取得する。<br>
	 * @param time 時間(分)
	 * @return 分
	 */
	public static int getMinutes(int time) {
		// 時間(分)を60で割った値を取得(余りは切り捨て)
		return Math.abs(time) % TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @param minutes 分(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(int hours, int minutes) {
		// 分を取得
		return hours * TimeConst.CODE_DEFINITION_HOUR + minutes;
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @param minutes 分(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(String hours, String minutes) {
		// 時(文字列)及び分(文字列)を数値に変換
		int intHours = MospUtility.getInt(hours);
		int intMinutes = MospUtility.getInt(minutes);
		// 分を取得
		return getTime(intHours, intMinutes);
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(String hours) {
		// 分を取得
		return getTime(hours, MospConst.STR_EMPTY);
	}
	
}
