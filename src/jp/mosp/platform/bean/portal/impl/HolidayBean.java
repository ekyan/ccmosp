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
package jp.mosp.platform.bean.portal.impl;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.HolidayBeanInterface;

/**
 * 祝日クラス。<br>
 */
public class HolidayBean extends PlatformBean implements HolidayBeanInterface {
	
	/**
	 * 文言コード(成人の日)。
	 */
	protected static final String	NAM_COMING_OF_AGE_DAY			= "ComingOfAgeDay";
	
	/**
	 * 文言コード(海の日)。
	 */
	protected static final String	NAM_MARINE_DAY					= "MarineDay";
	
	/**
	 * 文言コード(敬老の日)。
	 */
	protected static final String	NAM_RESPECT_FOR_THE_AGED_DAY	= "RespectForTheAgedDay";
	
	/**
	 * 文言コード(体育の日)。
	 */
	protected static final String	NAM_SPORTS_DAY					= "SportsDay";
	
	/**
	 * 文言コード(春分の日)。
	 */
	protected static final String	NAM_VERNAL_EQUINOX_DAY			= "VernalEquinoxDay";
	
	/**
	 * 文言コード(秋分の日)。
	 */
	protected static final String	NAM_AUTUMNAL_EQUINOX_DAY		= "AutumnalEquinoxDay";
	
	/**
	 * 文言コード(元日)。
	 */
	protected static final String	NAM_NEW_YEARS_DAY				= "NewYearsDay";
	
	/**
	 * 文言コード(建国記念の日)。
	 */
	protected static final String	NAM_NATIONAL_FOUNDATION_DAY		= "NationalFoundationDay";
	
	/**
	 * 文言コード(昭和の日)。
	 */
	protected static final String	NAM_SHOWA_DAY					= "ShowaDay";
	
	/**
	 * 文言コード(憲法記念日)。
	 */
	protected static final String	NAM_CONSTITUTION_DAY			= "ConstitutionDay";
	
	/**
	 * 文言コード(みどりの日)。
	 */
	protected static final String	NAM_GREENERY_DAY				= "GreeneryDay";
	
	/**
	 * 文言コード(こどもの日)。
	 */
	protected static final String	NAM_CHILDRENS_DAY				= "ChildrensDay";
	
	/**
	 * 文言コード(文化の日)。
	 */
	protected static final String	NAM_CULTURE_DAY					= "CultureDay";
	
	/**
	 * 文言コード(勤労感謝の日)。
	 */
	protected static final String	NAM_LABOR_THANKSGIVING_DAY		= "LaborThanksgivingDay";
	
	/**
	 * 文言コード(天皇誕生日)。
	 */
	protected static final String	NAM_EMPERORS_BIRTHDAY			= "EmperorsBirthday";
	
	/**
	 * 文言コード(国民の休日)。
	 */
	protected static final String	NAM_PEOPLES_DAY					= "PeoplesDay";
	
	/**
	 * 文言コード(振替休日)。
	 */
	protected static final String	NAM_OBSERVED_HOLIDAY			= "ObservedHoliday";
	
	/**
	 * 祝日マップ。
	 */
	protected Map<Date, String>		holidayMap;
	
	/**
	 * 対象年。
	 */
	protected int					year;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param actionInfo 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	public HolidayBean(MospParams actionInfo, Connection connection) {
		super(actionInfo, connection);
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public String getHolidayName(Date targetDate) {
		// 祝日マップ取得
		getHolidayMap(targetDate);
		return holidayMap.get(targetDate);
	}
	
	@Override
	public boolean isHoliday(Date targetDate) {
		// 祝日マップ取得
		getHolidayMap(targetDate);
		// 祝日名取得
		return holidayMap.get(targetDate) != null;
	}
	
	@Override
	public Map<Date, String> getHolidayMap(Date targetDate) {
		// 対象日の年を取得
		int targetYear = DateUtility.getYear(targetDate);
		// 対象年と比較
		if (year == targetYear) {
			// 何もしない
			return holidayMap;
		}
		// 対象年を設定
		year = targetYear;
		// 祝日マップ作成
		createHolidayMap();
		// 祝日マップ取得
		return holidayMap;
	}
	
	/**
	 * Calendarインスタンスを取得する。
	 * @return	Calendarインスタンス
	 */
	protected Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	/**
	 * 祝日Mapを生成する。
	 */
	protected void createHolidayMap() {
		// 成人の日
		addHolidayMap(getComingOfAgeDay(), mospParams.getName(NAM_COMING_OF_AGE_DAY));
		// 海の日
		addHolidayMap(getMarineDay(), mospParams.getName(NAM_MARINE_DAY));
		// 敬老の日
		addHolidayMap(getRespectForTheAgedDay(), mospParams.getName(NAM_RESPECT_FOR_THE_AGED_DAY));
		// 体育の日
		addHolidayMap(getSportsDay(), mospParams.getName(NAM_SPORTS_DAY));
		// 春分の日
		addHolidayMap(getVernalEquinoxDay(), mospParams.getName(NAM_VERNAL_EQUINOX_DAY));
		// 秋分の日
		addHolidayMap(getAutumnalEquinoxDay(), mospParams.getName(NAM_AUTUMNAL_EQUINOX_DAY));
		// 元日
		addHolidayMap(getNewYearsDay(), mospParams.getName(NAM_NEW_YEARS_DAY));
		// 建国記念の日
		addHolidayMap(getNationalFoundationDay(), mospParams.getName(NAM_NATIONAL_FOUNDATION_DAY));
		// 憲法記念日
		addHolidayMap(getConstitutionDay(), mospParams.getName(NAM_CONSTITUTION_DAY));
		// こどもの日
		addHolidayMap(getChildrensDay(), mospParams.getName(NAM_CHILDRENS_DAY));
		// 文化の日
		addHolidayMap(getCultureDay(), mospParams.getName(NAM_CULTURE_DAY));
		// 勤労感謝の日
		addHolidayMap(getLaborThanksgivingDay(), mospParams.getName(NAM_LABOR_THANKSGIVING_DAY));
		// 天皇誕生日
		addHolidayMap(getEmperorsBirthday(), mospParams.getName(NAM_EMPERORS_BIRTHDAY));
		// 昭和の日導入年
		final int showaSwitchYear = 2007;
		// みどりの日は2006年まで4月29日、昭和の日は2007年から導入
		// 国民の休日は2006年まで5月4日、みどりの日は2007年から導入
		String midoriName = mospParams.getName(NAM_GREENERY_DAY);
		String syouwaName = mospParams.getName(NAM_SHOWA_DAY);
		// みどりの日
		// 国民の休日
		if (year < showaSwitchYear) {
			midoriName = mospParams.getName(NAM_PEOPLES_DAY);
			syouwaName = mospParams.getName(NAM_GREENERY_DAY);
		}
		addHolidayMap(getShowaDay(), syouwaName);
		addHolidayMap(getGreeneryDay(), midoriName);
		// 振替休日を追加
		addSubstituteDate();
	}
	
	/**
	 * Mapに祝日を追加する。
	 * @param date 対象年月日
	 * @param name 対象祝祭日名
	 */
	protected void addHolidayMap(Date date, String name) {
		if (holidayMap == null) {
			holidayMap = new TreeMap<Date, String>();
		}
		holidayMap.put(date, name);
	}
	
	/**
	 * 振替休日を追加する。
	 */
	protected void addSubstituteDate() {
		final int switchYear = 2006;
		Calendar cal = getCalendar();
		Set<Date> keySet = holidayMap.keySet();
		TreeMap<Date, String> addMap = new TreeMap<Date, String>();
		Date formerHoliday = null;
		for (Date date : keySet) {
			// 第三条第二項
			// 「国民の祝日」が日曜日に当たるときは、その日後においてその日に最も近い「国民の祝日」でない日を休日とする。
			// 日曜だった場合、最も近い「国民の祝日」でない日に「振替休日」を追加する。
			if (DateUtility.isSunday(date)) {
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (year > switchYear) {
					// 既に登録されている休日と重複した場合、一日ずらす。
					while (holidayMap.containsKey(cal.getTime())) {
						cal.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
				if (!holidayMap.containsKey(cal.getTime())) {
					addMap.put(cal.getTime(), mospParams.getName(NAM_OBSERVED_HOLIDAY));
				}
			}
			// 第三条第三項
			// その前日及び翌日が「国民の祝日」である日（「国民の祝日」でない日に限る。）は、休日とする。
			if (formerHoliday != null) {
				cal.setTime(formerHoliday);
				cal.add(Calendar.DAY_OF_MONTH, 2);
				if (cal.getTime().compareTo(date) == 0) {
					cal.add(Calendar.DAY_OF_MONTH, -1);
					addMap.put(cal.getTime(), mospParams.getName(NAM_PEOPLES_DAY));
				}
			}
			formerHoliday = date;
		}
		holidayMap.putAll(addMap);
	}
	
	/**
	 * 成人の日を取得する。<br>
	 * @return	成人の日(1月第2月曜日)
	 * <p>
	 * 1999年以前は(1月15日)
	 * </p>
	 */
	protected Date getComingOfAgeDay() {
		final int switchYear = 2000;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		if (year < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 15);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		}
		return cal.getTime();
	}
	
	/**
	 * 海の日を取得する。<br>
	 * @return	海の日(7月第3月曜日)
	 * <p>
	 * 2002年以前は(7月20日)
	 * </p>
	 */
	protected Date getMarineDay() {
		final int switchYear = 2003;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JULY);
		if (year < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 20);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
		}
		return cal.getTime();
	}
	
	/**
	 * 敬老の日を取得する。<br>
	 * @return	敬老の日(9月第3月曜日)
	 * <p>
	 * 2002年以前は(9月15日)
	 * </p>
	 */
	protected Date getRespectForTheAgedDay() {
		final int switchYear = 2003;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		if (year < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 15);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
		}
		return cal.getTime();
	}
	
	/**
	 * 体育の日を取得する。<br>
	 * @return	体育の日(10月第2月曜日)
	 * <p>
	 * 1999年以前は(10月10日)
	 * </p>
	 */
	protected Date getSportsDay() {
		final int switchYear = 2000;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		if (year < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 10);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		}
		return cal.getTime();
	}
	
	/**
	 * 春分の日を取得する。<br>
	 * @return	春分の日(3月xx日)
	 */
	protected Date getVernalEquinoxDay() {
		final double param1 = 21.4471d;
		final double param2 = 0.242377d;
		final double param3 = 1900d;
		final double param4 = 4.0d;
		Calendar cal = getCalendar();
		double date = param1 + (param2 * (year - param3)) - Math.floor((year - param3) / param4);
		int dd = (int)Math.floor(date);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		return cal.getTime();
	}
	
	/**
	 *  秋分の日を取得する。<br>
	 *  @return 秋分の日(9月xx日)
	 */
	protected Date getAutumnalEquinoxDay() {
		final double param1 = 23.8896d;
		final double param2 = 0.242032d;
		final double param3 = 1900d;
		final double param4 = 4.0d;
		Calendar cal = getCalendar();
		final double date = param1 + (param2 * (year - param3)) - Math.floor((year - param3) / param4);
		int dd = (int)Math.floor(date);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		return cal.getTime();
	}
	
	/**
	 * 元日を取得する。<br>
	 * @return	元日(1月1日)
	 */
	protected Date getNewYearsDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 建国記念の日を取得する。<br>
	 * @return	建国記念の日(2月11日)
	 */
	protected Date getNationalFoundationDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 11);
		return cal.getTime();
	}
	
	/**
	 * 憲法記念日を取得する。<br>
	 * @return	憲法記念日(5月3日)
	 */
	protected Date getConstitutionDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		return cal.getTime();
	}
	
	/**
	 * こどもの日を取得する。<br>
	 * @return	こどもの日(5月5日)
	 */
	protected Date getChildrensDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		return cal.getTime();
	}
	
	/**
	 * 文化の日を取得する。<br>
	 * @return	文化の日(11月3日)
	 */
	protected Date getCultureDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		return cal.getTime();
	}
	
	/**
	 * 勤労感謝の日を取得する。<br>
	 * @return	勤労感謝の日(11月23日)
	 */
	protected Date getLaborThanksgivingDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		return cal.getTime();
	}
	
	/**
	 * 天皇誕生日を取得する。<br>
	 * @return	天皇誕生日(12月23日)
	 */
	protected Date getEmperorsBirthday() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		return cal.getTime();
	}
	
	/**
	 * 昭和の日を取得する。<br>
	 * @return	昭和の日(4月29日)
	 */
	protected Date getShowaDay() {
		final int dayOfMonth = 29;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return cal.getTime();
	}
	
	/**
	 * みどりの日を取得する。<br>
	 * @return	みどりの日(5月4日)
	 */
	protected Date getGreeneryDay() {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 4);
		return cal.getTime();
	}
	
}
