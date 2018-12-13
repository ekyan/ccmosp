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
package jp.mosp.framework.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;

/**
 * 日付操作に有用なメソッドを提供する。<br><br>
 */
public final class DateUtility {
	
	/**
	 * 1時間を秒に直した数値(1000 * 60 * 60)。<br>
	 */
	public static final int	TIME_HOUR_MILLI_SEC	= 3600000;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private DateUtility() {
		// 処理無し
	}
	
	/**
	 * システム日付を取得する。<br>
	 * @return システム日付
	 */
	public static Date getSystemDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * システム日時を取得する。<br>
	 * @return システム日時
	 */
	public static Date getSystemTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * システム日時(秒含む)を取得する。<br>
	 * @return システム日時
	 */
	public static Date getSystemTimeAndSecond() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	/**
	 * 
	 * @return システム日時 ミリ秒
	 */
	public static String getSystemTimeAndMillsSecond() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		
		return dFormat.format(new Date());
		
	}
	
	/**
	 * デフォルト日時を取得する。<br>
	 * @return デフォルト日時
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDefaultTime() throws MospException {
		return getTime(0, 0);
	}
	
	/**
	 * カレンダーによるデータ取得。<br>
	 * @param date  日付
	 * @param field フィールド
	 * @return date
	 */
	public static int getCalendarValue(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}
	
	/**
	 * 年取得。<br>
	 * @param date 日付
	 * @return 年
	 */
	public static int getYear(Date date) {
		return getCalendarValue(date, Calendar.YEAR);
	}
	
	/**
	 * 月取得。<br>
	 * @param date 日付
	 * @return 月
	 */
	public static int getMonth(Date date) {
		return getCalendarValue(date, Calendar.MONTH) + 1;
	}
	
	/**
	 * 日取得。<br>
	 * @param date 日付
	 * @return 日
	 */
	public static int getDay(Date date) {
		return getCalendarValue(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 時間取得。<br>
	 * @param date 日付
	 * @return 時間
	 */
	public static int getHour(Date date) {
		return getCalendarValue(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 時間取得。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static int getHour(Date date, Date standardDate) {
		if (date == null || standardDate == null) {
			// FIXME TODO nullの場合 0を返しても大丈夫かどうか。
			return getHour(date);
		}
		return (int)((date.getTime() - standardDate.getTime()) / TIME_HOUR_MILLI_SEC);
	}
	
	/**
	 * 分取得。<br>
	 * @param date 日付
	 * @return 分
	 */
	public static int getMinute(Date date) {
		return getCalendarValue(date, Calendar.MINUTE);
	}
	
	/**
	 * 曜日取得。<br>
	 * @param date 日付
	 * @return 曜日を示すフィールド値
	 */
	public static int getDayOfWeek(Date date) {
		return getCalendarValue(date, Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 年を取得する。<br>
	 * @param date 日付
	 * @return 年
	 */
	public static String getStringYear(Date date) {
		return getStringDate(date, "yyyy");
	}
	
	/**
	 * 月を取得する。<br>
	 * @param date 日付
	 * @return 月
	 */
	public static String getStringMonth(Date date) {
		return getStringDate(date, "MM");
	}
	
	/**
	 * 月(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 月(前ゼロ無し)
	 */
	public static String getStringMonthM(Date date) {
		return getStringDate(date, "M");
	}
	
	/**
	 * 日を取得する。<br>
	 * @param date 日付
	 * @return 日
	 */
	public static String getStringDay(Date date) {
		return getStringDate(date, "dd");
	}
	
	/**
	 * 日(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 日(前ゼロ無し)
	 */
	public static String getStringDayD(Date date) {
		return getStringDate(date, "d");
	}
	
	/**
	 * 時間を取得する。<br>
	 * @param date 日付
	 * @return 時間(0～23)
	 */
	public static String getStringHour(Date date) {
		return getStringDate(date, "HH");
	}
	
	/**
	 * 時間(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 時間(0～23)(前ゼロ無し)
	 */
	public static String getStringHourH(Date date) {
		return getStringDate(date, "H");
	}
	
	/**
	 * 基準日付と日付の差を時間で取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static String getStringHour(Date date, Date standardDate) {
		return getStringHour(date, standardDate, "#00");
	}
	
	/**
	 * 基準日付と日付の差を時間(前ゼロ無し)で取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static String getStringHourH(Date date, Date standardDate) {
		return getStringHour(date, standardDate, "#0");
	}
	
	/**
	 * 時間を取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @param pattern パターン
	 * @return 時間
	 */
	private static String getStringHour(Date date, Date standardDate, String pattern) {
		if (date == null || standardDate == null) {
			return getStringHour(date);
		}
		Format format = new DecimalFormat(pattern);
		return format.format((date.getTime() - standardDate.getTime()) / TIME_HOUR_MILLI_SEC);
	}
	
	/**
	 * 分を取得する。<br>
	 * @param date 日付
	 * @return 分
	 */
	public static String getStringMinute(Date date) {
		return getStringDate(date, "mm");
	}
	
	/**
	 * 分(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 分(前ゼロ無し)
	 */
	public static String getStringMinuteM(Date date) {
		return getStringDate(date, "m");
	}
	
	/**
	 * 曜日を取得する。<br>
	 * @param date 日付
	 * @return 曜日
	 */
	public static String getStringDayOfWeek(Date date) {
		return getStringDate(date, "E");
	}
	
	/**
	 * 日付オブジェクト(日付)を取得する。<br>
	 * 対象日付オブジェクトから、時間以下の情報を除いた日付を取得する。<br>
	 * @param date 対象日付オブジェクト
	 * @return 日付オブジェクト(日付)
	 */
	public static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 日付変換(int→Date)
	 * @param year		対象年
	 * @param month		対象月
	 * @param day		対象日
	 * @return Date	対象年月日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDate(int year, int month, int day) throws MospException {
		return getDateTime(year, month, day, 0, 0);
	}
	
	/**
	 * 日付オブジェクトを取得する。<br>
	 * 年、月、日がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param year	対象年
	 * @param month	対象月
	 * @param day	対象日
	 * @return 日付オブジェクト
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDate(String year, String month, String day) throws MospException {
		try {
			if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
				return null;
			}
			return getDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		} catch (NullPointerException e) {
			throw new MospException(e);
		} catch (NumberFormatException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * @param hourOfDay 対象時間
	 * @param minute 対象分
	 * @return 日付オブジェクト(時間)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(int hourOfDay, int minute) throws MospException {
		return getDateTime(MospConst.DEFAULT_YEAR, 1, 1, hourOfDay, minute);
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * 時、分がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param hour   時間
	 * @param minute 分
	 * @return 日付オブジェクト(時間)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(String hour, String minute) throws MospException {
		try {
			if (hour.isEmpty() && minute.isEmpty()) {
				return null;
			}
			return getTime(Integer.parseInt(hour), Integer.parseInt(minute));
		} catch (NullPointerException e) {
			throw new MospException(e);
		} catch (NumberFormatException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 日付オブジェクト(日時)を取得する。<br>
	 * @param date 日付文字列(yyyyMMddHHmmss)
	 * @return 日付オブジェクト(日時)
	 */
	public static Date getTime(String date) {
		return getDate(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。
	 * @param date 日付文字列(yyyy/MM/dd)
	 * @return 日付
	 */
	public static Date getDate(String date) {
		return getDate(date, "yyyy/MM/dd");
	}
	
	/**
	 * 日付変換（String→Date）
	 * @since	0.0.2
	 * @param date		対象年月日
	 * @param format	日付フォーマット
	 * @return			Date型対象年月日
	 */
	public static Date getDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd)
	 */
	public static String getStringDate(Date date) {
		return getStringDate(date, "yyyy/MM/dd");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd(E))
	 */
	public static String getStringDateAndDay(Date date) {
		return getStringDate(date, "yyyy/MM/dd(E)");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(MM/dd(E))
	 */
	public static String getStringMonthAndDay(Date date) {
		return getStringDate(date, "MM/dd(E)");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd HH:mm)
	 */
	public static String getStringDateAndTime(Date date) {
		return getStringDate(date, "yyyy/MM/dd HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd(E) HH:mm)
	 */
	public static String getStringDateAndDayAndTime(Date date) {
		return getStringDate(date, "yyyy/MM/dd(E) HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM)
	 */
	public static String getStringYearMonth(Date date) {
		return getStringDate(date, "yyyy/MM");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(HH:mm)
	 */
	public static String getStringTime(Date date) {
		return getStringDate(date, "HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm)
	 */
	public static String getStringTime(Date date, Date standardDate) {
		if (date == null || standardDate == null) {
			return getStringTime(date);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getStringHour(date, standardDate));
		sb.append(":");
		sb.append(getStringMinute(date));
		return sb.toString();
	}
	
	/**
	 * 日付文字列取得。<br>
	 * @param date 日付
	 * @param format 時間
	 * @return 日付文字列
	 */
	public static String getStringDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 年操作。<br>
	 * @param date   操作日付
	 * @param amount 増減年数
	 * @return 操作後日付
	 */
	public static Date addYear(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}
	
	/**
	 * 月操作。<br>
	 * @param date   操作日付
	 * @param amount 増減月数
	 * @return 操作後日付
	 */
	public static Date addMonth(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 日操作。<br>
	 * @param date   操作日付
	 * @param amount 増減日数
	 * @return 操作後日付
	 */
	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 時間操作。<br>
	 * @param date 操作日付
	 * @param amount 増減時間数
	 * @return 操作後日付
	 */
	public static Date addHour(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, amount);
		return cal.getTime();
	}
	
	/**
	 * 分操作。<br>
	 * @param date 操作日付
	 * @param amount 増減分数
	 * @return 操作後日付
	 */
	public static Date addMinute(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MINUTE, amount);
		return cal.getTime();
	}
	
	/**
	 * 日付変換(int→Date)
	 * @param year		対象年
	 * @param month		対象月
	 * @param day		対象日
	 * @param hour		対象時
	 * @param minute	対象分
	 * @return Date	対象年月日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(int year, int month, int day, int hour, int minute) throws MospException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.set(year, month - 1, day, hour, minute, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} catch (IllegalArgumentException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 日付オブジェクトを取得する。<br>
	 * 年、月、日がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param year		対象年
	 * @param month		対象月
	 * @param day		対象日
	 * @param hour		対象時
	 * @param minute	対象分
	 * @return 日付オブジェクト
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(String year, String month, String day, String hour, String minute)
			throws MospException {
		try {
			if (year.isEmpty() && month.isEmpty() && day.isEmpty() && hour.isEmpty() && minute.isEmpty()) {
				return null;
			}
			return getDateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
					Integer.valueOf(hour), Integer.valueOf(minute));
		} catch (NullPointerException e) {
			throw new MospException(e);
		} catch (NumberFormatException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 指定曜日可否を確認する。
	 * @param date 対象年月日
	 * @param dayOfWeek 対象曜日
	 * @return 対象の曜日の場合true、そうでない場合false
	 */
	public static boolean isDayOfWeek(Date date, int dayOfWeek) {
		return getDayOfWeek(date) == dayOfWeek;
	}
	
	/**
	 * 日曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 日曜日の場合true、そうでない場合false
	 */
	public static boolean isSunday(Date date) {
		return isDayOfWeek(date, Calendar.SUNDAY);
	}
	
	/**
	 * 月曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 月曜日の場合true、そうでない場合false
	 */
	public static boolean isMonday(Date date) {
		return isDayOfWeek(date, Calendar.MONDAY);
	}
	
	/**
	 * 火曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 火曜日の場合true、そうでない場合false
	 */
	public static boolean isTuesday(Date date) {
		return isDayOfWeek(date, Calendar.TUESDAY);
	}
	
	/**
	 * 水曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 水曜日の場合true、そうでない場合false
	 */
	public static boolean isWednesday(Date date) {
		return isDayOfWeek(date, Calendar.WEDNESDAY);
	}
	
	/**
	 * 木曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 木曜日の場合true、そうでない場合false
	 */
	public static boolean isThursday(Date date) {
		return isDayOfWeek(date, Calendar.THURSDAY);
	}
	
	/**
	 * 金曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 金曜日の場合true、そうでない場合false
	 */
	public static boolean isFriday(Date date) {
		return isDayOfWeek(date, Calendar.FRIDAY);
	}
	
	/**
	 * 土曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 土曜日の場合true、そうでない場合false
	 */
	public static boolean isSaturday(Date date) {
		return isDayOfWeek(date, Calendar.SATURDAY);
	}
	
	/**
	 * 開始日から対象日までの日数を取得する。
	 * @param startDate 開始日
	 * @param targetDate 対象日
	 * @return 日数
	 */
	public static String countDays(Date startDate, Date targetDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(getYear(targetDate), getMonth(targetDate), getDay(targetDate), 0, 0, 0);
		cal2.set(getYear(startDate), getMonth(startDate), getDay(startDate), 0, 0, 0);
		long day = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		return String.valueOf(day);
	}
	
	/**
	 * 開始日から対象日まで経過月を取得する。
	 * @param startDate 開始日
	 * @param targetDate 対象日
	 * @return 経過月数
	 */
	public static int getMonthDifference(Date startDate, Date targetDate) {
		// 年、月を取得
		int startYear = getYear(startDate);
		int startMonth = getMonth(startDate);
		int targetYear = getYear(targetDate);
		int targetMonth = getMonth(targetDate);
		// 月数を取得
		int startMonthMin = startYear * 12 + startMonth;
		int targetMonthMin = targetYear * 12 + targetMonth;
		// 経過月
		return targetMonthMin - startMonthMin;
	}
	
	/**
	 * 開始日から対象日まで経過年数を取得する。
	 * @param startDate 開始日
	 * @param targetDate 対象日
	 * @return 経過年数
	 */
	public int countMonth(Date startDate, Date targetDate) {
		int startYear = getMonth(startDate);
		int targetYear = getMonth(targetDate);
		return targetYear - startYear;
	}
	
	/**
	 * 期間に対象日が含まれているか確認する。<br>
	 * 開始日nullの場合：対象日が終了日以前であるかを確認。<br>
	 * 終了日nullの場合：対象日が開始日以後であるかを確認。<br>
	 * @param targetDate 対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @return 確認結果(true：含まれている、false：含んでない)
	 */
	public static boolean isTermContain(Date targetDate, Date startDate, Date endDate) {
		// 開始日がnullの場合
		if (startDate == null) {
			return targetDate.compareTo(endDate) <= 0;
		}
		// 終了日がnullの場合
		if (endDate == null) {
			return targetDate.compareTo(startDate) >= 0;
		}
		// 対象日が開始日以降であり、対象日が終了日以前であるかを確認
		return targetDate.compareTo(startDate) >= 0 && targetDate.compareTo(endDate) <= 0;
	}
	
}
