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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.constant.TimeConst;

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
	 * デフォルト日付における時刻を取得する。<br>
	 * <br>
	 * @param hour   時(24時間制)
	 * @param minute 分
	 * @return デフォルト日付における時刻を取得する。
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(int hour, int minute) throws MospException {
		return getDateTime(DateUtility.getDefaultTime(), hour, minute);
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
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
		// 対象日を確認
		if (date == null) {
			return null;
		}
		// 対象日のカレンダーオブジェクトを取得
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 秒及びミリ秒の初期化
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// 時分の設定
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		// 対象日における時刻を取得
		return cal.getTime();
	}
	
	/**
	 * デフォルト日時からの差を分で取得する。<br>
	 * @param targetTime 対象時刻
	 * @return デフォルト日時からの差(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static int getMinutes(Date targetTime) throws MospException {
		// 対象時刻確認
		if (targetTime == null) {
			return 0;
		}
		// ミリ秒で差を取得
		long defference = targetTime.getTime() - DateUtility.getDefaultTime().getTime();
		// 時間に変換
		return (int)(defference / (TimeBean.MILLI_SEC_PER_HOUR / TimeConst.CODE_DEFINITION_HOUR));
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
	 * 残業申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請が有効、false：無効)
	 */
	public static boolean isOvertimeRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 残業申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請利用可、false：不可)
	 */
	public static boolean isOvertimeRequestAvailable(MospParams mospParams) {
		return PlatformUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 休暇申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請が有効、false：無効)
	 */
	public static boolean isHolidayRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 休暇申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請利用可、false：不可)
	 */
	public static boolean isHolidayRequestAvailable(MospParams mospParams) {
		return PlatformUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請が有効、false：無効)
	 */
	public static boolean isWorkOnHolidayRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請利用可、false：不可)
	 */
	public static boolean isWorkOnHolidayRequestAvailable(MospParams mospParams) {
		return PlatformUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請が有効、false：無効)
	 */
	public static boolean isSubHolidayRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請利用可、false：不可)
	 */
	public static boolean isSubHolidayRequestAvailable(MospParams mospParams) {
		return PlatformUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請が有効、false：無効)
	 */
	public static boolean isWorkTypeChangeRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請利用可、false：不可)
	 */
	public static boolean isWorkTypeChangeRequestAvailable(MospParams mospParams) {
		return PlatformUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 時差出勤申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：時差出勤申請が有効、false：無効)
	 */
	public static boolean isDifferenceRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_DIFFERENCE_REQUEST);
	}
	
	/**
	 * 承認解除申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：承認解除申請が有効、false：無効)
	 */
	public static boolean isCancellationRequestValid(MospParams mospParams) {
		return PlatformUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_CANCELLATION_REQUEST);
	}
	
}
