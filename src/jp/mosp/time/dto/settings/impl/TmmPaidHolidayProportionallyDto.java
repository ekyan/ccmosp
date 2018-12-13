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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇比例付与DTO
 */
public class TmmPaidHolidayProportionallyDto extends BaseDto implements PaidHolidayProportionallyDtoInterface {
	
	private static final long	serialVersionUID	= 6371812231643052610L;
	
	private long				tmmPaidHolidayProportionallyId;
	private String				paidHolidayCode;
	private Date				activateDate;
	private int					prescribedWeeklyWorkingDays;
	private int					continuousServiceTermsCountingFromTheEmploymentDay;
	private int					days;
//	private int					oneDayAndSixMonths;
//	private int					oneDayAndOneYearAndSixMonths;
//	private int					oneDayAndTwoYearsAndSixMonths;
//	private int					oneDayAndThreeYearsAndSixMonths;
//	private int					oneDayAndFourYearsAndSixMonths;
//	private int					oneDayAndFiveYearsAndSixMonths;
//	private int					oneDayAndSixYearsAndSixMonthsOrMore;
//	private int					twoDaysAndSixMonths;
//	private int					twoDaysAndOneYearAndSixMonths;
//	private int					twoDaysAndTwoYearsAndSixMonths;
//	private int					twoDaysAndThreeYearsAndSixMonths;
//	private int					twoDaysAndFourYearsAndSixMonths;
//	private int					twoDaysAndFiveYearsAndSixMonths;
//	private int					twoDaysAndSixYearsAndSixMonthsOrMore;
//	private int					threeDaysAndSixMonths;
//	private int					threeDaysAndOneYearAndSixMonths;
//	private int					threeDaysAndTwoYearsAndSixMonths;
//	private int					threeDaysAndThreeYearsAndSixMonths;
//	private int					threeDaysAndFourYearsAndSixMonths;
//	private int					threeDaysAndFiveYearsAndSixMonths;
//	private int					threeDaysAndSixYearsAndSixMonthsOrMore;
//	private int					fourDaysAndSixMonths;
//	private int					fourDaysAndOneYearAndSixMonths;
//	private int					fourDaysAndTwoYearsAndSixMonths;
//	private int					fourDaysAndThreeYearsAndSixMonths;
//	private int					fourDaysAndFourYearsAndSixMonths;
//	private int					fourDaysAndFiveYearsAndSixMonths;
//	private int					fourDaysAndSixYearsAndSixMonthsOrMore;
//	private int					fiveDaysOrMoreAndSixMonths;
//	private int					fiveDaysOrMoreAndOneYearAndSixMonths;
//	private int					fiveDaysOrMoreAndTwoYearsAndSixMonths;
//	private int					fiveDaysOrMoreAndThreeYearsAndSixMonths;
//	private int					fiveDaysOrMoreAndFourYearsAndSixMonths;
//	private int					fiveDaysOrMoreAndFiveYearsAndSixMonths;
//	private int					fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
	private int					inactivateFlag;
	
	
	@Override
	public long getTmmPaidHolidayProportionallyId() {
		return tmmPaidHolidayProportionallyId;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getPrescribedWeeklyWorkingDays() {
		return prescribedWeeklyWorkingDays;
	}
	
	@Override
	public int getContinuousServiceTermsCountingFromTheEmploymentDay() {
		return continuousServiceTermsCountingFromTheEmploymentDay;
	}
	
	@Override
	public int getDays() {
		return days;
	}
	
//	@Override
//	public int getOneDayAndSixMonths() {
//		return oneDayAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndOneYearAndSixMonths() {
//		return oneDayAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndTwoYearsAndSixMonths() {
//		return oneDayAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndThreeYearsAndSixMonths() {
//		return oneDayAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndFourYearsAndSixMonths() {
//		return oneDayAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndFiveYearsAndSixMonths() {
//		return oneDayAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getOneDayAndSixYearsAndSixMonthsOrMore() {
//		return oneDayAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public int getTwoDaysAndSixMonths() {
//		return twoDaysAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndOneYearAndSixMonths() {
//		return twoDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndTwoYearsAndSixMonths() {
//		return twoDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndThreeYearsAndSixMonths() {
//		return twoDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndFourYearsAndSixMonths() {
//		return twoDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndFiveYearsAndSixMonths() {
//		return twoDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getTwoDaysAndSixYearsAndSixMonthsOrMore() {
//		return twoDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public int getThreeDaysAndSixMonths() {
//		return threeDaysAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndOneYearAndSixMonths() {
//		return threeDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndTwoYearsAndSixMonths() {
//		return threeDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndThreeYearsAndSixMonths() {
//		return threeDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndFourYearsAndSixMonths() {
//		return threeDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndFiveYearsAndSixMonths() {
//		return threeDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getThreeDaysAndSixYearsAndSixMonthsOrMore() {
//		return threeDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public int getFourDaysAndSixMonths() {
//		return fourDaysAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndOneYearAndSixMonths() {
//		return fourDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndTwoYearsAndSixMonths() {
//		return fourDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndThreeYearsAndSixMonths() {
//		return fourDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndFourYearsAndSixMonths() {
//		return fourDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndFiveYearsAndSixMonths() {
//		return fourDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFourDaysAndSixYearsAndSixMonthsOrMore() {
//		return fourDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndSixMonths() {
//		return fiveDaysOrMoreAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndOneYearAndSixMonths() {
//		return fiveDaysOrMoreAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndTwoYearsAndSixMonths() {
//		return fiveDaysOrMoreAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndThreeYearsAndSixMonths() {
//		return fiveDaysOrMoreAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndFourYearsAndSixMonths() {
//		return fiveDaysOrMoreAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndFiveYearsAndSixMonths() {
//		return fiveDaysOrMoreAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public int getFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore() {
//		return fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
//	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setTmmPaidHolidayProportionallyId(long tmmPaidHolidayProportionallyId) {
		this.tmmPaidHolidayProportionallyId = tmmPaidHolidayProportionallyId;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setPrescribedWeeklyWorkingDays(int prescribedWeeklyWorkingDays) {
		this.prescribedWeeklyWorkingDays = prescribedWeeklyWorkingDays;
	}
	
	@Override
	public void setContinuousServiceTermsCountingFromTheEmploymentDay(
			int continuousServiceTermsCountingFromTheEmploymentDay) {
		this.continuousServiceTermsCountingFromTheEmploymentDay = continuousServiceTermsCountingFromTheEmploymentDay;
	}
	
	@Override
	public void setDays(int days) {
		this.days = days;
	}
	
//	@Override
//	public void setOneDayAndSixMonths(int oneDayAndSixMonths) {
//		this.oneDayAndSixMonths = oneDayAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndOneYearAndSixMonths(int oneDayAndOneYearAndSixMonths) {
//		this.oneDayAndOneYearAndSixMonths = oneDayAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndTwoYearsAndSixMonths(int oneDayAndTwoYearsAndSixMonths) {
//		this.oneDayAndTwoYearsAndSixMonths = oneDayAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndThreeYearsAndSixMonths(int oneDayAndThreeYearsAndSixMonths) {
//		this.oneDayAndThreeYearsAndSixMonths = oneDayAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndFourYearsAndSixMonths(int oneDayAndFourYearsAndSixMonths) {
//		this.oneDayAndFourYearsAndSixMonths = oneDayAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndFiveYearsAndSixMonths(int oneDayAndFiveYearsAndSixMonths) {
//		this.oneDayAndFiveYearsAndSixMonths = oneDayAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setOneDayAndSixYearsAndSixMonthsOrMore(int oneDayAndSixYearsAndSixMonthsOrMore) {
//		this.oneDayAndSixYearsAndSixMonthsOrMore = oneDayAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public void setTwoDaysAndSixMonths(int twoDaysAndSixMonths) {
//		this.twoDaysAndSixMonths = twoDaysAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndOneYearAndSixMonths(int twoDaysAndOneYearAndSixMonths) {
//		this.twoDaysAndOneYearAndSixMonths = twoDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndTwoYearsAndSixMonths(int twoDaysAndTwoYearsAndSixMonths) {
//		this.twoDaysAndTwoYearsAndSixMonths = twoDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndThreeYearsAndSixMonths(int twoDaysAndThreeYearsAndSixMonths) {
//		this.twoDaysAndThreeYearsAndSixMonths = twoDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndFourYearsAndSixMonths(int twoDaysAndFourYearsAndSixMonths) {
//		this.twoDaysAndFourYearsAndSixMonths = twoDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndFiveYearsAndSixMonths(int twoDaysAndFiveYearsAndSixMonths) {
//		this.twoDaysAndFiveYearsAndSixMonths = twoDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setTwoDaysAndSixYearsAndSixMonthsOrMore(int twoDaysAndSixYearsAndSixMonthsOrMore) {
//		this.twoDaysAndSixYearsAndSixMonthsOrMore = twoDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public void setThreeDaysAndSixMonths(int threeDaysAndSixMonths) {
//		this.threeDaysAndSixMonths = threeDaysAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndOneYearAndSixMonths(int threeDaysAndOneYearAndSixMonths) {
//		this.threeDaysAndOneYearAndSixMonths = threeDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndTwoYearsAndSixMonths(int threeDaysAndTwoYearsAndSixMonths) {
//		this.threeDaysAndTwoYearsAndSixMonths = threeDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndThreeYearsAndSixMonths(int threeDaysAndThreeYearsAndSixMonths) {
//		this.threeDaysAndThreeYearsAndSixMonths = threeDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndFourYearsAndSixMonths(int threeDaysAndFourYearsAndSixMonths) {
//		this.threeDaysAndFourYearsAndSixMonths = threeDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndFiveYearsAndSixMonths(int threeDaysAndFiveYearsAndSixMonths) {
//		this.threeDaysAndFiveYearsAndSixMonths = threeDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setThreeDaysAndSixYearsAndSixMonthsOrMore(int threeDaysAndSixYearsAndSixMonthsOrMore) {
//		this.threeDaysAndSixYearsAndSixMonthsOrMore = threeDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public void setFourDaysAndSixMonths(int fourDaysAndSixMonths) {
//		this.fourDaysAndSixMonths = fourDaysAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndOneYearAndSixMonths(int fourDaysAndOneYearAndSixMonths) {
//		this.fourDaysAndOneYearAndSixMonths = fourDaysAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndTwoYearsAndSixMonths(int fourDaysAndTwoYearsAndSixMonths) {
//		this.fourDaysAndTwoYearsAndSixMonths = fourDaysAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndThreeYearsAndSixMonths(int fourDaysAndThreeYearsAndSixMonths) {
//		this.fourDaysAndThreeYearsAndSixMonths = fourDaysAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndFourYearsAndSixMonths(int fourDaysAndFourYearsAndSixMonths) {
//		this.fourDaysAndFourYearsAndSixMonths = fourDaysAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndFiveYearsAndSixMonths(int fourDaysAndFiveYearsAndSixMonths) {
//		this.fourDaysAndFiveYearsAndSixMonths = fourDaysAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFourDaysAndSixYearsAndSixMonthsOrMore(int fourDaysAndSixYearsAndSixMonthsOrMore) {
//		this.fourDaysAndSixYearsAndSixMonthsOrMore = fourDaysAndSixYearsAndSixMonthsOrMore;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndSixMonths(int fiveDaysOrMoreAndSixMonths) {
//		this.fiveDaysOrMoreAndSixMonths = fiveDaysOrMoreAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndOneYearAndSixMonths(int fiveDaysOrMoreAndOneYearAndSixMonths) {
//		this.fiveDaysOrMoreAndOneYearAndSixMonths = fiveDaysOrMoreAndOneYearAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndTwoYearsAndSixMonths(int fiveDaysOrMoreAndTwoYearsAndSixMonths) {
//		this.fiveDaysOrMoreAndTwoYearsAndSixMonths = fiveDaysOrMoreAndTwoYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndThreeYearsAndSixMonths(int fiveDaysOrMoreAndThreeYearsAndSixMonths) {
//		this.fiveDaysOrMoreAndThreeYearsAndSixMonths = fiveDaysOrMoreAndThreeYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndFourYearsAndSixMonths(int fiveDaysOrMoreAndFourYearsAndSixMonths) {
//		this.fiveDaysOrMoreAndFourYearsAndSixMonths = fiveDaysOrMoreAndFourYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndFiveYearsAndSixMonths(int fiveDaysOrMoreAndFiveYearsAndSixMonths) {
//		this.fiveDaysOrMoreAndFiveYearsAndSixMonths = fiveDaysOrMoreAndFiveYearsAndSixMonths;
//	}
//	
//	@Override
//	public void setFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(int fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore) {
//		this.fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore = fiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
//	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
