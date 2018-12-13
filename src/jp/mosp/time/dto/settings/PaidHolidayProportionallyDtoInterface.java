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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 有給休暇比例付与DTOインターフェース
 */
public interface PaidHolidayProportionallyDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmPaidHolidayProportionallyId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 週所定労働日数。
	 */
	int getPrescribedWeeklyWorkingDays();
	
	/**
	 * @return 雇入れの日から起算した継続勤務期間。
	 */
	int getContinuousServiceTermsCountingFromTheEmploymentDay();
	
	/**
	 * @return 日数。
	 */
	int getDays();
	
//	/**
//	 * @return 一日/六箇月。
//	 */
//	int getOneDayAndSixMonths();
//	
//	/**
//	 * @return 一日/一年六箇月。
//	 */
//	int getOneDayAndOneYearAndSixMonths();
//	
//	/**
//	 * @return 一日/二年六箇月。
//	 */
//	int getOneDayAndTwoYearsAndSixMonths();
//	
//	/**
//	 * @return 一日/三年六箇月。
//	 */
//	int getOneDayAndThreeYearsAndSixMonths();
//	
//	/**
//	 * @return 一日/四年六箇月。
//	 */
//	int getOneDayAndFourYearsAndSixMonths();
//	
//	/**
//	 * @return 一日/五年六箇月。
//	 */
//	int getOneDayAndFiveYearsAndSixMonths();
//	
//	/**
//	 * @return 一日/六年六箇月以上。
//	 */
//	int getOneDayAndSixYearsAndSixMonthsOrMore();
//	
//	/**
//	 * @return 二日/六箇月。
//	 */
//	int getTwoDaysAndSixMonths();
//	
//	/**
//	 * @return 二日/一年六箇月。
//	 */
//	int getTwoDaysAndOneYearAndSixMonths();
//	
//	/**
//	 * @return 二日/二年六箇月。
//	 */
//	int getTwoDaysAndTwoYearsAndSixMonths();
//	
//	/**
//	 * @return 二日/三年六箇月。
//	 */
//	int getTwoDaysAndThreeYearsAndSixMonths();
//	
//	/**
//	 * @return 二日/四年六箇月。
//	 */
//	int getTwoDaysAndFourYearsAndSixMonths();
//	
//	/**
//	 * @return 二日/五年六箇月。
//	 */
//	int getTwoDaysAndFiveYearsAndSixMonths();
//	
//	/**
//	 * @return 二日/六年六箇月以上。
//	 */
//	int getTwoDaysAndSixYearsAndSixMonthsOrMore();
//	
//	/**
//	 * @return 三日/六箇月。
//	 */
//	int getThreeDaysAndSixMonths();
//	
//	/**
//	 * @return 三日/一年六箇月。
//	 */
//	int getThreeDaysAndOneYearAndSixMonths();
//	
//	/**
//	 * @return 三日/二年六箇月。
//	 */
//	int getThreeDaysAndTwoYearsAndSixMonths();
//	
//	/**
//	 * @return 三日/三年六箇月。
//	 */
//	int getThreeDaysAndThreeYearsAndSixMonths();
//	
//	/**
//	 * @return 三日/四年六箇月。
//	 */
//	int getThreeDaysAndFourYearsAndSixMonths();
//	
//	/**
//	 * @return 三日/五年六箇月。
//	 */
//	int getThreeDaysAndFiveYearsAndSixMonths();
//	
//	/**
//	 * @return 三日/六年六箇月以上。
//	 */
//	int getThreeDaysAndSixYearsAndSixMonthsOrMore();
//	
//	/**
//	 * @return 四日/六箇月。
//	 */
//	int getFourDaysAndSixMonths();
//	
//	/**
//	 * @return 四日/一年六箇月。
//	 */
//	int getFourDaysAndOneYearAndSixMonths();
//	
//	/**
//	 * @return 四日/二年六箇月。
//	 */
//	int getFourDaysAndTwoYearsAndSixMonths();
//	
//	/**
//	 * @return 四日/三年六箇月。
//	 */
//	int getFourDaysAndThreeYearsAndSixMonths();
//	
//	/**
//	 * @return 四日/四年六箇月。
//	 */
//	int getFourDaysAndFourYearsAndSixMonths();
//	
//	/**
//	 * @return 四日/五年六箇月。
//	 */
//	int getFourDaysAndFiveYearsAndSixMonths();
//	
//	/**
//	 * @return 四日/六年六箇月以上。
//	 */
//	int getFourDaysAndSixYearsAndSixMonthsOrMore();
//	
//	/**
//	 * @return 五日以上/六箇月。
//	 */
//	int getFiveDaysOrMoreAndSixMonths();
//	
//	/**
//	 * @return 五日以上/一年六箇月。
//	 */
//	int getFiveDaysOrMoreAndOneYearAndSixMonths();
//	
//	/**
//	 * @return 五日以上/二年六箇月。
//	 */
//	int getFiveDaysOrMoreAndTwoYearsAndSixMonths();
//	
//	/**
//	 * @return 五日以上/三年六箇月。
//	 */
//	int getFiveDaysOrMoreAndThreeYearsAndSixMonths();
//	
//	/**
//	 * @return 五日以上/四年六箇月。
//	 */
//	int getFiveDaysOrMoreAndFourYearsAndSixMonths();
//	
//	/**
//	 * @return 五日以上/五年六箇月。
//	 */
//	int getFiveDaysOrMoreAndFiveYearsAndSixMonths();
//	
//	/**
//	 * @return 五日以上/六年六箇月以上。
//	 */
//	int getFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore();
	
	/**
	 * @param tmmPaidHolidayProportionallyId セットする レコード識別ID。
	 */
	void setTmmPaidHolidayProportionallyId(long tmmPaidHolidayProportionallyId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param prescribedWeeklyWorkingDays セットする 週所定労働日数。
	 */
	void setPrescribedWeeklyWorkingDays(int prescribedWeeklyWorkingDays);
	
	/**
	 * @param continuousServiceTermsCountingFromTheEmploymentDay セットする 雇入れの日から起算した継続勤務期間。
	 */
	void setContinuousServiceTermsCountingFromTheEmploymentDay(int continuousServiceTermsCountingFromTheEmploymentDay);
	
	/**
	 * @param days セットする 日数。
	 */
	void setDays(int days);
	
//	/**
//	 * @param oneDayAndSixMonths セットする 一日/六箇月。
//	 */
//	void setOneDayAndSixMonths(int oneDayAndSixMonths);
//	
//	/**
//	 * @param oneDayAndOneYearAndSixMonths セットする 一日/一年六箇月。
//	 */
//	void setOneDayAndOneYearAndSixMonths(int oneDayAndOneYearAndSixMonths);
//	
//	/**
//	 * @param oneDayAndTwoYearsAndSixMonths セットする 一日/二年六箇月。
//	 */
//	void setOneDayAndTwoYearsAndSixMonths(int oneDayAndTwoYearsAndSixMonths);
//	
//	/**
//	 * @param oneDayAndThreeYearsAndSixMonths セットする 一日/三年六箇月。
//	 */
//	void setOneDayAndThreeYearsAndSixMonths(int oneDayAndThreeYearsAndSixMonths);
//	
//	/**
//	 * @param oneDayAndFourYearsAndSixMonths セットする 一日/四年六箇月。
//	 */
//	void setOneDayAndFourYearsAndSixMonths(int oneDayAndFourYearsAndSixMonths);
//	
//	/**
//	 * @param oneDayAndFiveYearsAndSixMonths セットする 一日/五年六箇月。
//	 */
//	void setOneDayAndFiveYearsAndSixMonths(int oneDayAndFiveYearsAndSixMonths);
//	
//	/**
//	 * @param oneDayAndSixYearsAndSixMonthsOrMore セットする 一日/六年六箇月以上。
//	 */
//	void setOneDayAndSixYearsAndSixMonthsOrMore(int oneDayAndSixYearsAndSixMonthsOrMore);
//	
//	/**
//	 * @param twoDaysAndSixMonths セットする 二日/六箇月。
//	 */
//	void setTwoDaysAndSixMonths(int twoDaysAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndOneYearAndSixMonths セットする 二日/一年六箇月。
//	 */
//	void setTwoDaysAndOneYearAndSixMonths(int twoDaysAndOneYearAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndTwoYearsAndSixMonths セットする 二日/二年六箇月。
//	 */
//	void setTwoDaysAndTwoYearsAndSixMonths(int twoDaysAndTwoYearsAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndThreeYearsAndSixMonths セットする 二日/三年六箇月。
//	 */
//	void setTwoDaysAndThreeYearsAndSixMonths(int twoDaysAndThreeYearsAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndFourYearsAndSixMonths セットする 二日/四年六箇月。
//	 */
//	void setTwoDaysAndFourYearsAndSixMonths(int twoDaysAndFourYearsAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndFiveYearsAndSixMonths セットする 二日/五年六箇月。
//	 */
//	void setTwoDaysAndFiveYearsAndSixMonths(int twoDaysAndFiveYearsAndSixMonths);
//	
//	/**
//	 * @param twoDaysAndSixYearsAndSixMonthsOrMore セットする 二日/六年六箇月以上。
//	 */
//	void setTwoDaysAndSixYearsAndSixMonthsOrMore(int twoDaysAndSixYearsAndSixMonthsOrMore);
//	
//	/**
//	 * @param threeDaysAndSixMonths セットする 三日/六箇月。
//	 */
//	void setThreeDaysAndSixMonths(int threeDaysAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndOneYearAndSixMonths セットする 三日/一年六箇月。
//	 */
//	void setThreeDaysAndOneYearAndSixMonths(int threeDaysAndOneYearAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndTwoYearsAndSixMonths セットする 三日/二年六箇月。
//	 */
//	void setThreeDaysAndTwoYearsAndSixMonths(int threeDaysAndTwoYearsAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndThreeYearsAndSixMonths セットする 三日/三年六箇月。
//	 */
//	void setThreeDaysAndThreeYearsAndSixMonths(int threeDaysAndThreeYearsAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndFourYearsAndSixMonths セットする 三日/四年六箇月。
//	 */
//	void setThreeDaysAndFourYearsAndSixMonths(int threeDaysAndFourYearsAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndFiveYearsAndSixMonths セットする 三日/五年六箇月。
//	 */
//	void setThreeDaysAndFiveYearsAndSixMonths(int threeDaysAndFiveYearsAndSixMonths);
//	
//	/**
//	 * @param threeDaysAndSixYearsAndSixMonthsOrMore セットする 三日/六年六箇月以上。
//	 */
//	void setThreeDaysAndSixYearsAndSixMonthsOrMore(int threeDaysAndSixYearsAndSixMonthsOrMore);
//	
//	/**
//	 * @param fourDaysAndSixMonths セットする 四日/六箇月。
//	 */
//	void setFourDaysAndSixMonths(int fourDaysAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndOneYearAndSixMonths セットする 四日/一年六箇月。
//	 */
//	void setFourDaysAndOneYearAndSixMonths(int fourDaysAndOneYearAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndTwoYearsAndSixMonths セットする 四日/二年六箇月。
//	 */
//	void setFourDaysAndTwoYearsAndSixMonths(int fourDaysAndTwoYearsAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndThreeYearsAndSixMonths セットする 四日/三年六箇月。
//	 */
//	void setFourDaysAndThreeYearsAndSixMonths(int fourDaysAndThreeYearsAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndFourYearsAndSixMonths セットする 四日/四年六箇月。
//	 */
//	void setFourDaysAndFourYearsAndSixMonths(int fourDaysAndFourYearsAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndFiveYearsAndSixMonths セットする 四日/五年六箇月。
//	 */
//	void setFourDaysAndFiveYearsAndSixMonths(int fourDaysAndFiveYearsAndSixMonths);
//	
//	/**
//	 * @param fourDaysAndSixYearsAndSixMonthsOrMore セットする 四日/六年六箇月以上。
//	 */
//	void setFourDaysAndSixYearsAndSixMonthsOrMore(int fourDaysAndSixYearsAndSixMonthsOrMore);
//	
//	/**
//	 * @param fiveDaysOrMoreAndSixMonths セットする 五日以上/六箇月。
//	 */
//	void setFiveDaysOrMoreAndSixMonths(int fiveDaysOrMoreAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndOneYearAndSixMonths セットする 五日以上/一年六箇月。
//	 */
//	void setFiveDaysOrMoreAndOneYearAndSixMonths(int fiveDaysOrMoreAndOneYearAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndTwoYearsAndSixMonths セットする 五日以上/二年六箇月。
//	 */
//	void setFiveDaysOrMoreAndTwoYearsAndSixMonths(int fiveDaysOrMoreAndTwoYearsAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndThreeYearsAndSixMonths セットする 五日以上/三年六箇月。
//	 */
//	void setFiveDaysOrMoreAndThreeYearsAndSixMonths(int fiveDaysOrMoreAndThreeYearsAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndFourYearsAndSixMonths セットする 五日以上/四年六箇月。
//	 */
//	void setFiveDaysOrMoreAndFourYearsAndSixMonths(int fiveDaysOrMoreAndFourYearsAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndFiveYearsAndSixMonths セットする 五日以上/五年六箇月。
//	 */
//	void setFiveDaysOrMoreAndFiveYearsAndSixMonths(int fiveDaysOrMoreAndFiveYearsAndSixMonths);
//	
//	/**
//	 * @param fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore セットする 五日以上/六年六箇月以上。
//	 */
//	void setFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(int fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore);

}
