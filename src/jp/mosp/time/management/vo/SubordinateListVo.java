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
package jp.mosp.time.management.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 部下一覧の情報を格納する。
 */
public class SubordinateListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -2092258650689243780L;
	
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				txtSearchEmployeeCode;
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	private String				pltSearchApproval;
	private String				pltSearchCalc;
	
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblWorkDate;
	private String[]			aryLblWorkTime;
	private String[]			aryLblRestTime;
	private String[]			aryLblPrivateTime;
	private String[]			aryLblLateTime;
	private String[]			aryLblLeaveEarlyTime;
	private String[]			aryLblLateLeaveEarlyTime;
	private String[]			aryLblOverTimeIn;
	private String[]			aryLblOverTimeOut;
	private String[]			aryLblWorkOnHolidayTime;
	private String[]			aryLblLateNightTime;
	private String[]			aryLblPaidHoliday;
	private String[]			aryLblAllHoliday;
	private String[]			aryLblAbsence;
	private String[]			aryLblApploval;
	private String[]			aryLblCalc;
	private String[]			aryLblCorrection;
	private String[]			aryOvertimeOutStyle;
	
	private String				lblTotalWork;
	private String				lblTotalRest;
	private String				lblTotalPrivate;
	private String				lblTotalLate;
	private String				lblTotalLeaveEarly;
	private String				lblTotalLateLeaveEarly;
	private String				lblTotalOverTimeIn;
	private String				lblTotalOverTimeOut;
	private String				lblTotalWorkOnHoliday;
	private String				lblTotalLateNight;
	private String				lblTimesWork;
	private String				lblTimesLate;
	private String				lblTimesLeaveEarly;
	private String				lblTimesOverTimeWork;
	private String				lblTimesWorkOnHoliday;
	private String				lblTimesHoliday;
	private String				lblTimesLegalHoliday;
	private String				lblTimesSpecificHoliday;
	private String				lblTimesPaidHoliday;
	private String				lblTimesPaidHoloidayTime;
	private String				lblTimesSpecialHoloiday;
	private String				lblTimesOtherHoloiday;
	private String				lblTimesSubstitute;
	private String				lblTimesSubHoliday;
	private String				lblTimesAbsence;
	private String				lblTimesAllowance1;
	private String				lblTimesAllowance2;
	private String				lblTimesAllowance3;
	private String				lblTimesAllowance4;
	private String				lblTimesAllowance5;
	private String				lblTimesAllowance6;
	private String				lblTimesAllowance7;
	private String				lblTimesAllowance8;
	private String				lblTimesAllowance9;
	private String				lblTimesAllowance10;
	
	private String[][]			aryPltRequestYear;
	private String[][]			aryPltRequestMonth;
	private String[][]			aryPltWorkPlace;
	private String[][]			aryPltEmployment;
	private String[][]			aryPltSection;
	private String[][]			aryPltPosition;
	private String[][]			aryPltApproval;
	private String[][]			aryPltCalc;
	
	private String[]			claApploval;
	private String[]			claCalc;
	private String[]			aryPersonalId;
	
	private boolean				jsSearchConditionRequired;
	
	/**
	 * 表示コマンド。<br>
	 * 上司モード(部下一覧)か承認者モード(社員別勤怠承認)かを
	 * 判別するのに用いる。<br>
	 */
	private String				showCommand;
	
	
	/**
	 * @return pltSearchRequestYear
	 */
	public String getPltSearchRequestYear() {
		return pltSearchRequestYear;
	}
	
	/**
	 * @return pltSearchRequestMonth
	 */
	public String getPltSearchRequestMonth() {
		return pltSearchRequestMonth;
	}
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	@Override
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @return txtSearchEmployeeName
	 */
	public String getTxtSearchEmployeeName() {
		return txtSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchWorkPlace
	 */
	public String getPltSearchWorkPlace() {
		return pltSearchWorkPlace;
	}
	
	/**
	 * @return pltSearchEmployment
	 */
	public String getPltSearchEmployment() {
		return pltSearchEmployment;
	}
	
	/**
	 * @return pltSearchSection
	 */
	public String getPltSearchSection() {
		return pltSearchSection;
	}
	
	/**
	 * @return pltSearchPosition
	 */
	public String getPltSearchPosition() {
		return pltSearchPosition;
	}
	
	/**
	 * @return pltSearchApproval
	 */
	public String getPltSearchApproval() {
		return pltSearchApproval;
	}
	
	/**
	 * @return pltSearchCalc
	 */
	public String getPltSearchCalc() {
		return pltSearchCalc;
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeCode
	 */
	public String getAryLblEmployeeCode(int idx) {
		return aryLblEmployeeCode[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSection
	 */
	public String getAryLblSection(int idx) {
		return aryLblSection[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkDate
	 */
	public String getAryLblWorkDate(int idx) {
		return aryLblWorkDate[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkTime
	 */
	public String getAryLblWorkTime(int idx) {
		return aryLblWorkTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblRestTime(int idx) {
		return aryLblRestTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPrivateTime
	 */
	public String getAryLblPrivateTime(int idx) {
		return aryLblPrivateTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateTime
	 */
	public String getAryLblLateTime(int idx) {
		return aryLblLateTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLeaveEarlyTime
	 */
	public String getAryLblLeaveEarlyTime(int idx) {
		return aryLblLeaveEarlyTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateLeaveEarlyTime
	 */
	public String getAryLblLateLeaveEarlyTime(int idx) {
		return aryLblLateLeaveEarlyTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeIn
	 */
	public String getAryLblOverTimeIn(int idx) {
		return aryLblOverTimeIn[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeOut
	 */
	public String getAryLblOverTimeOut(int idx) {
		return aryLblOverTimeOut[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkOnHolidayTime
	 */
	public String getAryLblWorkOnHolidayTime(int idx) {
		return aryLblWorkOnHolidayTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateNightTime
	 */
	public String getAryLblLateNightTime(int idx) {
		return aryLblLateNightTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPaidHoliday
	 */
	public String getAryLblPaidHoliday(int idx) {
		return aryLblPaidHoliday[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAllHoliday
	 */
	public String getAryLblAllHoliday(int idx) {
		return aryLblAllHoliday[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAbsence
	 */
	public String getAryLblAbsence(int idx) {
		return aryLblAbsence[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApploval
	 */
	public String getAryLblApploval(int idx) {
		return aryLblApploval[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCalc
	 */
	public String getAryLblCalc(int idx) {
		return aryLblCalc[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCorrection
	 */
	public String getAryLblCorrection(int idx) {
		return aryLblCorrection[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryOvertimeOutStyle
	 */
	public String getAryOvertimeOutStyle(int idx) {
		return aryOvertimeOutStyle[idx];
	}
	
	/**
	 * @return lblTotalWork
	 */
	public String getLblTotalWork() {
		return lblTotalWork;
	}
	
	/**
	 * @return lblTotalRest
	 */
	public String getLblTotalRest() {
		return lblTotalRest;
	}
	
	/**
	 * @return lblTotalPrivate
	 */
	public String getLblTotalPrivate() {
		return lblTotalPrivate;
	}
	
	/**
	 * @return lblTotalLate
	 */
	public String getLblTotalLate() {
		return lblTotalLate;
	}
	
	/**
	 * @return lblTotalLeaveEarly
	 */
	public String getLblTotalLeaveEarly() {
		return lblTotalLeaveEarly;
	}
	
	/**
	 * @return lblTotalLateLeaveEarly
	 */
	public String getLblTotalLateLeaveEarly() {
		return lblTotalLateLeaveEarly;
	}
	
	/**
	 * @return lblTotalOverTimeIn
	 */
	public String getLblTotalOverTimeIn() {
		return lblTotalOverTimeIn;
	}
	
	/**
	 * @return lblTotalOverTimeOut
	 */
	public String getLblTotalOverTimeOut() {
		return lblTotalOverTimeOut;
	}
	
	/**
	 * @return lblTotalWorkOnHoliday
	 */
	public String getLblTotalWorkOnHoliday() {
		return lblTotalWorkOnHoliday;
	}
	
	/**
	 * @return lblTotalLateNight
	 */
	public String getLblTotalLateNight() {
		return lblTotalLateNight;
	}
	
	/**
	 * @return lblTimesWork
	 */
	public String getLblTimesWork() {
		return lblTimesWork;
	}
	
	/**
	 * @return lblTimesLate
	 */
	public String getLblTimesLate() {
		return lblTimesLate;
	}
	
	/**
	 * @return lblTimesLeaveEarly
	 */
	public String getLblTimesLeaveEarly() {
		return lblTimesLeaveEarly;
	}
	
	/**
	 * @return lblTimesOverTimeWork
	 */
	public String getLblTimesOverTimeWork() {
		return lblTimesOverTimeWork;
	}
	
	/**
	 * @return lblTimesWorkOnHoliday
	 */
	public String getLblTimesWorkOnHoliday() {
		return lblTimesWorkOnHoliday;
	}
	
	/**
	 * @return lblTimesHoliday
	 */
	public String getLblTimesHoliday() {
		return lblTimesHoliday;
	}
	
	/**
	 * @return lblTimesLegalHoliday
	 */
	public String getLblTimesLegalHoliday() {
		return lblTimesLegalHoliday;
	}
	
	/**
	 * @return lblTimesSpecificHoliday
	 */
	public String getLblTimesSpecificHoliday() {
		return lblTimesSpecificHoliday;
	}
	
	/**
	 * @return lblTimesPaidHoliday
	 */
	public String getLblTimesPaidHoliday() {
		return lblTimesPaidHoliday;
	}
	
	/**
	 * @return lblTimesPaidHoloidayTime
	 */
	public String getLblTimesPaidHoloidayTime() {
		return lblTimesPaidHoloidayTime;
	}
	
	/**
	 * @return lblTimesSpecialHoloiday
	 */
	public String getLblTimesSpecialHoloiday() {
		return lblTimesSpecialHoloiday;
	}
	
	/**
	 * @return lblTimesOtherHoloiday
	 */
	public String getLblTimesOtherHoloiday() {
		return lblTimesOtherHoloiday;
	}
	
	/**
	 * @return lblTimesSubstitute
	 */
	public String getLblTimesSubstitute() {
		return lblTimesSubstitute;
	}
	
	/**
	 * @return lblTimesSubHoliday
	 */
	public String getLblTimesSubHoliday() {
		return lblTimesSubHoliday;
	}
	
	/**
	 * @return lblTimesAbsence
	 */
	public String getLblTimesAbsence() {
		return lblTimesAbsence;
	}
	
	/**
	 * @return lblTimesAllowance1
	 */
	public String getLblTimesAllowance1() {
		return lblTimesAllowance1;
	}
	
	/**
	 * @return lblTimesAllowance2
	 */
	public String getLblTimesAllowance2() {
		return lblTimesAllowance2;
	}
	
	/**
	 * @return lblTimesAllowance3
	 */
	public String getLblTimesAllowance3() {
		return lblTimesAllowance3;
	}
	
	/**
	 * @return lblTimesAllowance4
	 */
	public String getLblTimesAllowance4() {
		return lblTimesAllowance4;
	}
	
	/**
	 * @return lblTimesAllowance5
	 */
	public String getLblTimesAllowance5() {
		return lblTimesAllowance5;
	}
	
	/**
	 * @return lblTimesAllowance6
	 */
	public String getLblTimesAllowance6() {
		return lblTimesAllowance6;
	}
	
	/**
	 * @return lblTimesAllowance7
	 */
	public String getLblTimesAllowance7() {
		return lblTimesAllowance7;
	}
	
	/**
	 * @return lblTimesAllowance8
	 */
	public String getLblTimesAllowance8() {
		return lblTimesAllowance8;
	}
	
	/**
	 * @return lblTimesAllowance9
	 */
	public String getLblTimesAllowance9() {
		return lblTimesAllowance9;
	}
	
	/**
	 * @return lblTimesAllowance10
	 */
	public String getLblTimesAllowance10() {
		return lblTimesAllowance10;
	}
	
	/**
	 * @return aryPltRequestYear
	 */
	public String[][] getAryPltRequestYear() {
		return getStringArrayClone(aryPltRequestYear);
	}
	
	/**
	 * @return aryPltRequestMonth
	 */
	public String[][] getAryPltRequestMonth() {
		return getStringArrayClone(aryPltRequestMonth);
	}
	
	/**
	 * @return aryPltWorkPlace
	 */
	public String[][] getAryPltWorkPlace() {
		return getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @return aryPltApproval
	 */
	public String[][] getAryPltApproval() {
		return getStringArrayClone(aryPltApproval);
	}
	
	/**
	 * @return aryPltCalc
	 */
	public String[][] getAryPltCalc() {
		return getStringArrayClone(aryPltCalc);
	}
	
	/**
	 * @param pltSearchRequestYear セットする pltSearchRequestYear
	 */
	public void setPltSearchRequestYear(String pltSearchRequestYear) {
		this.pltSearchRequestYear = pltSearchRequestYear;
	}
	
	/**
	 * @param pltSearchRequestMonth セットする pltSearchRequestMonth
	 */
	public void setPltSearchRequestMonth(String pltSearchRequestMonth) {
		this.pltSearchRequestMonth = pltSearchRequestMonth;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	@Override
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeName セットする txtSearchEmployeeName
	 */
	public void setTxtSearchEmployeeName(String txtSearchEmployeeName) {
		this.txtSearchEmployeeName = txtSearchEmployeeName;
	}
	
	/**
	 * @param pltSearchWorkPlace セットする pltSearchWorkPlace
	 */
	public void setPltSearchWorkPlace(String pltSearchWorkPlace) {
		this.pltSearchWorkPlace = pltSearchWorkPlace;
	}
	
	/**
	 * @param pltSearchEmployment セットする pltSearchEmployment
	 */
	public void setPltSearchEmployment(String pltSearchEmployment) {
		this.pltSearchEmployment = pltSearchEmployment;
	}
	
	/**
	 * @param pltSearchSection セットする pltSearchSection
	 */
	public void setPltSearchSection(String pltSearchSection) {
		this.pltSearchSection = pltSearchSection;
	}
	
	/**
	 * @param pltSearchPosition セットする pltSearchPosition
	 */
	public void setPltSearchPosition(String pltSearchPosition) {
		this.pltSearchPosition = pltSearchPosition;
	}
	
	/**
	 * @param pltSearchApproval セットする pltSearchApproval
	 */
	public void setPltSearchApproval(String pltSearchApproval) {
		this.pltSearchApproval = pltSearchApproval;
	}
	
	/**
	 * @param pltSearchCalc セットする pltSearchCalc
	 */
	public void setPltSearchCalc(String pltSearchCalc) {
		this.pltSearchCalc = pltSearchCalc;
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param aryLblWorkDate セットする aryLblWorkDate
	 */
	public void setAryLblWorkDate(String[] aryLblWorkDate) {
		this.aryLblWorkDate = getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblRestTime セットする aryLblRestTime
	 */
	public void setAryLblRestTime(String[] aryLblRestTime) {
		this.aryLblRestTime = getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param aryLblPrivateTime セットする aryLblPrivateTime
	 */
	public void setAryLblPrivateTime(String[] aryLblPrivateTime) {
		this.aryLblPrivateTime = getStringArrayClone(aryLblPrivateTime);
	}
	
	/**
	 * @param aryLblLateTime セットする aryLblLateTime
	 */
	public void setAryLblLateTime(String[] aryLblLateTime) {
		this.aryLblLateTime = getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param aryLblLeaveEarlyTime セットする aryLblLeaveEarlyTime
	 */
	public void setAryLblLeaveEarlyTime(String[] aryLblLeaveEarlyTime) {
		this.aryLblLeaveEarlyTime = getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param aryLblLateLeaveEarlyTime セットする aryLblLateLeaveEarlyTime
	 */
	public void setAryLblLateLeaveEarlyTime(String[] aryLblLateLeaveEarlyTime) {
		this.aryLblLateLeaveEarlyTime = getStringArrayClone(aryLblLateLeaveEarlyTime);
	}
	
	/**
	 * @param aryLblOverTimeIn セットする aryLblOverTimeIn
	 */
	public void setAryLblOverTimeIn(String[] aryLblOverTimeIn) {
		this.aryLblOverTimeIn = getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param aryLblOverTimeOut セットする aryLblOverTimeOut
	 */
	public void setAryLblOverTimeOut(String[] aryLblOverTimeOut) {
		this.aryLblOverTimeOut = getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param aryLblWorkOnHolidayTime セットする aryLblWorkOnHolidayTime
	 */
	public void setAryLblWorkOnHolidayTime(String[] aryLblWorkOnHolidayTime) {
		this.aryLblWorkOnHolidayTime = getStringArrayClone(aryLblWorkOnHolidayTime);
	}
	
	/**
	 * @param aryLblLateNightTime セットする aryLblLateNightTime
	 */
	public void setAryLblLateNightTime(String[] aryLblLateNightTime) {
		this.aryLblLateNightTime = getStringArrayClone(aryLblLateNightTime);
	}
	
	/**
	 * @param aryLblPaidHoliday セットする aryLblPaidHoliday
	 */
	public void setAryLblPaidHoliday(String[] aryLblPaidHoliday) {
		this.aryLblPaidHoliday = getStringArrayClone(aryLblPaidHoliday);
	}
	
	/**
	 * @param aryLblAllHoliday セットする aryLblAllHoliday
	 */
	public void setAryLblAllHoliday(String[] aryLblAllHoliday) {
		this.aryLblAllHoliday = getStringArrayClone(aryLblAllHoliday);
	}
	
	/**
	 * @param aryLblAbsence セットする aryLblAbsence
	 */
	public void setAryLblAbsence(String[] aryLblAbsence) {
		this.aryLblAbsence = getStringArrayClone(aryLblAbsence);
	}
	
	/**
	 * @param aryLblApploval セットする aryLblApploval
	 */
	public void setAryLblApploval(String[] aryLblApploval) {
		this.aryLblApploval = getStringArrayClone(aryLblApploval);
	}
	
	/**
	 * @param aryLblCalc セットする aryLblCalc
	 */
	public void setAryLblCalc(String[] aryLblCalc) {
		this.aryLblCalc = getStringArrayClone(aryLblCalc);
	}
	
	/**
	 * @param aryLblCorrection セットする aryLblCorrection
	 */
	public void setAryLblCorrection(String[] aryLblCorrection) {
		this.aryLblCorrection = getStringArrayClone(aryLblCorrection);
	}
	
	/**
	 * @param aryOvertimeOutStyle セットする aryOvertimeOutStyle
	 */
	public void setAryOvertimeOutStyle(String[] aryOvertimeOutStyle) {
		this.aryOvertimeOutStyle = aryOvertimeOutStyle;
	}
	
	/**
	 * @param lblTotalWork セットする lblTotalWork
	 */
	public void setLblTotalWork(String lblTotalWork) {
		this.lblTotalWork = lblTotalWork;
	}
	
	/**
	 * @param lblTotalRest セットする lblTotalRest
	 */
	public void setLblTotalRest(String lblTotalRest) {
		this.lblTotalRest = lblTotalRest;
	}
	
	/**
	 * @param lblTotalPrivate セットする lblTotalPrivate
	 */
	public void setLblTotalPrivate(String lblTotalPrivate) {
		this.lblTotalPrivate = lblTotalPrivate;
	}
	
	/**
	 * @param lblTotalLate セットする lblTotalLate
	 */
	public void setLblTotalLate(String lblTotalLate) {
		this.lblTotalLate = lblTotalLate;
	}
	
	/**
	 * @param lblTotalLeaveEarly セットする lblTotalLeaveEarly
	 */
	public void setLblTotalLeaveEarly(String lblTotalLeaveEarly) {
		this.lblTotalLeaveEarly = lblTotalLeaveEarly;
	}
	
	/**
	 * @param lblTotalLateLeaveEarly セットする lblTotalLateLeaveEarly
	 */
	public void setLblTotalLateLeaveEarly(String lblTotalLateLeaveEarly) {
		this.lblTotalLateLeaveEarly = lblTotalLateLeaveEarly;
	}
	
	/**
	 * @param lblTotalOverTimeIn セットする lblTotalOverTimeIn
	 */
	public void setLblTotalOverTimeIn(String lblTotalOverTimeIn) {
		this.lblTotalOverTimeIn = lblTotalOverTimeIn;
	}
	
	/**
	 * @param lblTotalOverTimeOut セットする lblTotalOverTimeOut
	 */
	public void setLblTotalOverTimeOut(String lblTotalOverTimeOut) {
		this.lblTotalOverTimeOut = lblTotalOverTimeOut;
	}
	
	/**
	 * @param lblTotalWorkOnHoliday セットする lblTotalWorkOnHoliday
	 */
	public void setLblTotalWorkOnHoliday(String lblTotalWorkOnHoliday) {
		this.lblTotalWorkOnHoliday = lblTotalWorkOnHoliday;
	}
	
	/**
	 * @param lblTotalLateNight セットする lblTotalLateNight
	 */
	public void setLblTotalLateNight(String lblTotalLateNight) {
		this.lblTotalLateNight = lblTotalLateNight;
	}
	
	/**
	 * @param lblTimesWork セットする lblTimesWork
	 */
	public void setLblTimesWork(String lblTimesWork) {
		this.lblTimesWork = lblTimesWork;
	}
	
	/**
	 * @param lblTimesLate セットする lblTimesLate
	 */
	public void setLblTimesLate(String lblTimesLate) {
		this.lblTimesLate = lblTimesLate;
	}
	
	/**
	 * @param lblTimesLeaveEarly セットする lblTimesLeaveEarly
	 */
	public void setLblTimesLeaveEarly(String lblTimesLeaveEarly) {
		this.lblTimesLeaveEarly = lblTimesLeaveEarly;
	}
	
	/**
	 * @param lblTimesOverTimeWork セットする lblTimesOverTimeWork
	 */
	public void setLblTimesOverTimeWork(String lblTimesOverTimeWork) {
		this.lblTimesOverTimeWork = lblTimesOverTimeWork;
	}
	
	/**
	 * @param lblTimesWorkOnHoliday セットする lblTimesWorkOnHoliday
	 */
	public void setLblTimesWorkOnHoliday(String lblTimesWorkOnHoliday) {
		this.lblTimesWorkOnHoliday = lblTimesWorkOnHoliday;
	}
	
	/**
	 * @param lblTimesHoliday セットする lblTimesHoliday
	 */
	public void setLblTimesHoliday(String lblTimesHoliday) {
		this.lblTimesHoliday = lblTimesHoliday;
	}
	
	/**
	 * @param lblTimesLegalHoliday セットする lblTimesLegalHoliday
	 */
	public void setLblTimesLegalHoliday(String lblTimesLegalHoliday) {
		this.lblTimesLegalHoliday = lblTimesLegalHoliday;
	}
	
	/**
	 * @param lblTimesSpecificHoliday セットする lblTimesSpecificHoliday
	 */
	public void setLblTimesSpecificHoliday(String lblTimesSpecificHoliday) {
		this.lblTimesSpecificHoliday = lblTimesSpecificHoliday;
	}
	
	/**
	 * @param lblTimesPaidHoliday セットする lblTimesPaidHoliday
	 */
	public void setLblTimesPaidHoliday(String lblTimesPaidHoliday) {
		this.lblTimesPaidHoliday = lblTimesPaidHoliday;
	}
	
	/**
	 * @param lblTimesPaidHoloidayTime セットする lblTimesPaidHoloidayTime
	 */
	public void setLblTimesPaidHoloidayTime(String lblTimesPaidHoloidayTime) {
		this.lblTimesPaidHoloidayTime = lblTimesPaidHoloidayTime;
	}
	
	/**
	 * @param lblTimesSpecialHoloiday セットする lblTimesSpecialHoloiday
	 */
	public void setLblTimesSpecialHoloiday(String lblTimesSpecialHoloiday) {
		this.lblTimesSpecialHoloiday = lblTimesSpecialHoloiday;
	}
	
	/**
	 * @param lblTimesOtherHoloiday セットする lblTimesOtherHoloiday
	 */
	public void setLblTimesOtherHoloiday(String lblTimesOtherHoloiday) {
		this.lblTimesOtherHoloiday = lblTimesOtherHoloiday;
	}
	
	/**
	 * @param lblTimesSubstitute セットする lblTimesSubstitute
	 */
	public void setLblTimesSubstitute(String lblTimesSubstitute) {
		this.lblTimesSubstitute = lblTimesSubstitute;
	}
	
	/**
	 * @param lblTimesSubHoliday セットする lblTimesSubHoliday
	 */
	public void setLblTimesSubHoliday(String lblTimesSubHoliday) {
		this.lblTimesSubHoliday = lblTimesSubHoliday;
	}
	
	/**
	 * @param lblTimesAbsence セットする lblTimesAbsence
	 */
	public void setLblTimesAbsence(String lblTimesAbsence) {
		this.lblTimesAbsence = lblTimesAbsence;
	}
	
	/**
	 * @param lblTimesAllowance1 セットする lblTimesAllowance1
	 */
	public void setLblTimesAllowance1(String lblTimesAllowance1) {
		this.lblTimesAllowance1 = lblTimesAllowance1;
	}
	
	/**
	 * @param lblTimesAllowance2 セットする lblTimesAllowance2
	 */
	public void setLblTimesAllowance2(String lblTimesAllowance2) {
		this.lblTimesAllowance2 = lblTimesAllowance2;
	}
	
	/**
	 * @param lblTimesAllowance3 セットする lblTimesAllowance3
	 */
	public void setLblTimesAllowance3(String lblTimesAllowance3) {
		this.lblTimesAllowance3 = lblTimesAllowance3;
	}
	
	/**
	 * @param lblTimesAllowance4 セットする lblTimesAllowance4
	 */
	public void setLblTimesAllowance4(String lblTimesAllowance4) {
		this.lblTimesAllowance4 = lblTimesAllowance4;
	}
	
	/**
	 * @param lblTimesAllowance5 セットする lblTimesAllowance5
	 */
	public void setLblTimesAllowance5(String lblTimesAllowance5) {
		this.lblTimesAllowance5 = lblTimesAllowance5;
	}
	
	/**
	 * @param lblTimesAllowance6 セットする lblTimesAllowance6
	 */
	public void setLblTimesAllowance6(String lblTimesAllowance6) {
		this.lblTimesAllowance6 = lblTimesAllowance6;
	}
	
	/**
	 * @param lblTimesAllowance7 セットする lblTimesAllowance7
	 */
	public void setLblTimesAllowance7(String lblTimesAllowance7) {
		this.lblTimesAllowance7 = lblTimesAllowance7;
	}
	
	/**
	 * @param lblTimesAllowance8 セットする lblTimesAllowance8
	 */
	public void setLblTimesAllowance8(String lblTimesAllowance8) {
		this.lblTimesAllowance8 = lblTimesAllowance8;
	}
	
	/**
	 * @param lblTimesAllowance9 セットする lblTimesAllowance9
	 */
	public void setLblTimesAllowance9(String lblTimesAllowance9) {
		this.lblTimesAllowance9 = lblTimesAllowance9;
	}
	
	/**
	 * @param lblTimesAllowance10 セットする lblTimesAllowance10
	 */
	public void setLblTimesAllowance10(String lblTimesAllowance10) {
		this.lblTimesAllowance10 = lblTimesAllowance10;
	}
	
	/**
	 * @param aryPltRequestYear セットする aryPltRequestYear
	 */
	public void setAryPltRequestYear(String[][] aryPltRequestYear) {
		this.aryPltRequestYear = getStringArrayClone(aryPltRequestYear);
	}
	
	/**
	 * @param aryPltRequestMonth セットする aryPltRequestMonth
	 */
	public void setAryPltRequestMonth(String[][] aryPltRequestMonth) {
		this.aryPltRequestMonth = getStringArrayClone(aryPltRequestMonth);
	}
	
	/**
	 * @param aryPltWorkPlace セットする aryPltWorkPlace
	 */
	public void setAryPltWorkPlace(String[][] aryPltWorkPlace) {
		this.aryPltWorkPlace = getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltApproval セットする aryPltApproval
	 */
	public void setAryPltApproval(String[][] aryPltApproval) {
		this.aryPltApproval = getStringArrayClone(aryPltApproval);
	}
	
	/**
	 * @param aryPltCalc セットする aryPltCalc
	 */
	public void setAryPltCalc(String[][] aryPltCalc) {
		this.aryPltCalc = getStringArrayClone(aryPltCalc);
	}
	
	/**
	 * @param idx インデックス
	 * @return claApploval
	 */
	public String getClaApploval(int idx) {
		return claApploval[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return claCalc
	 */
	public String getClaCalc(int idx) {
		return claCalc[idx];
	}
	
	/**
	 * @param claApploval セットする claApploval
	 */
	public void setClaApploval(String[] claApploval) {
		this.claApploval = getStringArrayClone(claApploval);
	}
	
	/**
	 * @param claCalc セットする claCalc
	 */
	public void setClaCalc(String[] claCalc) {
		this.claCalc = getStringArrayClone(claCalc);
	}
	
	/**
	 * @param idx インデックス
	 * @return personalId
	 */
	public String getAryPersonalId(int idx) {
		return aryPersonalId[idx];
	}
	
	/**
	 * @param aryPersonalId セットする aryPersonalId
	 */
	public void setAryPersonalId(String[] aryPersonalId) {
		this.aryPersonalId = getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @return jsSearchConditionRequired
	 */
	public boolean isJsSearchConditionRequired() {
		return jsSearchConditionRequired;
	}
	
	/**
	 * @param jsSearchConditionRequired セットする jsSearchConditionRequired
	 */
	public void setJsSearchConditionRequired(boolean jsSearchConditionRequired) {
		this.jsSearchConditionRequired = jsSearchConditionRequired;
	}
	
	/**
	 * @return showCommand
	 */
	public String getShowCommand() {
		return showCommand;
	}
	
	/**
	 * @param showCommand セットする showCommand
	 */
	public void setShowCommand(String showCommand) {
		this.showCommand = showCommand;
	}
	
}
