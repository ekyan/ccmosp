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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalTimeListDtoInterface;

/**
 * 勤怠集計結果一覧DTO。
 */
public class TotalTimeListDto extends BaseDto implements TotalTimeListDtoInterface {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	/**
	 * 社員氏名(姓)。
	 */
	private String				firstName;
	/**
	 * 社員氏名(名)。
	 */
	private String				lastName;
	/**
	 * 所属。
	 */
	private String				sectionCode;
	/**
	 * 出勤日数。
	 */
	private String				workDate;
	/**
	 * 勤務時間。
	 */
	private String				workTime;
	/**
	 * 休憩時間。
	 */
	private String				restTime;
	/**
	 * 遅刻時間。
	 */
	private String				lateTime;
	/**
	 * 早退時間。
	 */
	private String				leaveEarlyTime;
	/**
	 * 残業時間。
	 */
	private String				overTimeIn;
	/**
	 * 法定外残業時間(外残)。
	 */
	private String				overTimeOut;
	/**
	 * 休出時間。
	 */
	private String				workOnHolidayTime;
	/**
	 * 深夜時間。
	 */
	private String				lateNightTime;
	/**
	 * 有休日数。
	 */
	private String				paidHoliday;
	/**
	 * 休暇日数。
	 */
	private String				allHoliday;
	/**
	 * 欠勤日数。
	 */
	private String				absence;
	/**
	 * 未承認有無。
	 */
	private String				apploval;
	/**
	 * 未集計有無。
	 */
	private String				calc;
	/**
	 * 修正履歴。
	 */
	private String				correction;
	
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public String getWorkDate() {
		return workDate;
	}
	
	@Override
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	
	@Override
	public String getWorkTime() {
		return workTime;
	}
	
	@Override
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	@Override
	public String getRestTime() {
		return restTime;
	}
	
	@Override
	public void setRestTime(String restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public String getLateTime() {
		return lateTime;
	}
	
	@Override
	public void setLateTime(String lateTime) {
		this.lateTime = lateTime;
	}
	
	@Override
	public String getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	@Override
	public void setLeaveEarlyTime(String leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	@Override
	public String getOverTimeIn() {
		return overTimeIn;
	}
	
	@Override
	public void setOverTimeIn(String overTimeIn) {
		this.overTimeIn = overTimeIn;
	}
	
	@Override
	public String getOverTimeOut() {
		return overTimeOut;
	}
	
	@Override
	public void setOverTimeOut(String overTimeOut) {
		this.overTimeOut = overTimeOut;
	}
	
	@Override
	public String getWorkOnHolidayTime() {
		return workOnHolidayTime;
	}
	
	@Override
	public void setWorkOnHolidayTime(String workOnHolidayTime) {
		this.workOnHolidayTime = workOnHolidayTime;
	}
	
	@Override
	public String getLateNightTime() {
		return lateNightTime;
	}
	
	@Override
	public void setLateNightTime(String lateNightTime) {
		this.lateNightTime = lateNightTime;
	}
	
	@Override
	public String getPaidHoliday() {
		return paidHoliday;
	}
	
	@Override
	public void setPaidHoliday(String paidHoliday) {
		this.paidHoliday = paidHoliday;
	}
	
	@Override
	public String getAllHoliday() {
		return allHoliday;
	}
	
	@Override
	public void setAllHoliday(String allHoliday) {
		this.allHoliday = allHoliday;
	}
	
	@Override
	public String getAbsence() {
		return absence;
	}
	
	@Override
	public void setAbsence(String absence) {
		this.absence = absence;
	}
	
	@Override
	public String getApploval() {
		return apploval;
	}
	
	@Override
	public void setApploval(String apploval) {
		this.apploval = apploval;
	}
	
	@Override
	public String getCalc() {
		return calc;
	}
	
	@Override
	public void setCalc(String calc) {
		this.calc = calc;
	}
	
	@Override
	public String getCorrection() {
		return correction;
	}
	
	@Override
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	
}
