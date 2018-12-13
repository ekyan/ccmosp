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

/**
 * 勤怠一覧DTO。<br>
 * 勤怠一覧画面の他、予定確認画面、出勤簿、予定簿にも用いられる。<br>
 */
public class AttendanceListDto extends BaseDto {
	
	private static final long	serialVersionUID	= 8802206806270430291L;
	
	/**
	 * 個人ID(頁単位項目)。
	 */
	private String				personalId;
	
	/**
	 * 帳票タイトル(ヘッダ項目)。<br>
	 * 出勤簿、予定簿等のタイトルを保持する。<br>
	 */
	private String				title;
	
	/**
	 * 対象日(ヘッダ項目)。
	 */
	private Date				targetDate;
	
	/**
	 * 社員コード(ヘッダ項目)。
	 */
	private String				employeeCode;
	
	/**
	 * 社員氏名(ヘッダ項目)。
	 */
	private String				employeeName;
	
	/**
	 * 所属名称(ヘッダ項目)。
	 */
	private String				sectionName;
	
	/**
	 * 帳票アラート(ヘッダ項目)。<br>
	 * 出勤簿、予定簿等のアラートを保持する。<br>
	 */
	private String				alert;
	
	/**
	 * 帳票残業項目タイトル(ヘッダ項目)。<br>
	 */
	private String				overtimeTitle;
	
	/**
	 * 帳票承認印欄表示設定(ヘッダ項目)。<br>
	 */
	private boolean				isSealBoxAvailable;
	
	/**
	 * 帳票代休申請有効設定(フッタ項目)。<br>
	 */
	private boolean				isSubHolidayRequestValid;
	
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	
	/**
	 * 勤務回数。
	 */
	private int					goingWork;
	
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	
	/**
	 * 勤務形態略称。
	 */
	private String				workTypeAbbr;
	
	/**
	 * 午前勤務形態略称。
	 */
	private String				workTypeAnteAbbr;
	
	/**
	 * 午後勤務形態略称。
	 */
	private String				workTypePostAbbr;
	
	/**
	 * 出勤時刻。
	 */
	private Date				startTime;
	
	/**
	 * 退勤時刻。
	 */
	private Date				endTime;
	
	/**
	 * 勤務時間(分)。
	 */
	private Integer				workTime;
	
	/**
	 * 遅刻時間(分)。
	 */
	private Integer				lateTime;
	
	/**
	 * 早退時間(分)。
	 */
	private Integer				leaveEarlyTime;
	
	/**
	 * 遅刻早退時間(分)。
	 */
	private Integer				lateLeaveEarlyTime;
	
	/**
	 * 休憩時間(分)。<br>
	 */
	private Integer				restTime;
	
	/**
	 * 私用外出時間(分)。<br>
	 */
	private Integer				privateTime;
	
	/**
	 * 公用外出回数(回)。<br>
	 */
	private Integer				publicTime;
	
	/**
	 * 分単位休暇A時間タイトル(ヘッダ項目)。<br>
	 */
	private String				minutelyHolidayATitle;
	
	/**
	 * 分単位休暇A時間(分)。
	 */
	private Integer				minutelyHolidayATime;
	
	/**
	 * 分単位休暇B時間タイトル(ヘッダ項目)。<br>
	 */
	private String				minutelyHolidayBTitle;
	
	/**
	 * 分単位休暇B時間(分)。
	 */
	private Integer				minutelyHolidayBTime;
	
	/**
	 * 分単位休暇A全休。
	 */
	private int					minutelyHolidayA;
	
	/**
	 * 分単位休暇B全休。
	 */
	private int					minutelyHolidayB;
	
	/**
	 * 残業時間(分)。<br>
	 */
	private Integer				overtime;
	
	/**
	 * 法定内残業時間(分)。
	 */
	private Integer				overtimeIn;
	
	/**
	 * 法定外残業時間(分)。
	 */
	private Integer				overtimeOut;
	
	/**
	 * 深夜勤務時間(分)。
	 */
	private Integer				lateNightTime;
	
	/**
	 * 休日勤務時間(分)。
	 */
	private Integer				holidayWorkTime;
	
	/**
	 * 申請情報。
	 */
	private String				applicationInfo;
	
	/**
	 * 勤怠コメント。
	 */
	private String				timeComment;
	
	/**
	 * 備考。
	 */
	private String				remark;
	
	/**
	 * 修正情報。<br>
	 * null(修正無し)、本(本人)、他(他人)。
	 */
	private String				correctionInfo;
	
	/**
	 * 状態リンク要否(勤怠一覧用)。
	 */
	private boolean				needStatusLink;
	
	/**
	 * レコード識別ID(勤怠一覧状態リンク用)。
	 */
	private long				attendanceRecordId;
	
	/**
	 * ワークフローレコード識別ID(勤怠承認一覧リンク用)。
	 */
	private long				workflowRecordId;
	
	/**
	 * ワークフロー番号(勤怠承認一覧リンク用)。
	 */
	private long				workflow;
	
	/**
	 * ワークフロー状況(勤怠承認一覧リンク用)。
	 */
	private String				workflowStatus;
	
	/**
	 * ワークフロー段階(勤怠承認一覧リンク用)。
	 */
	private int					workflowStage;
	
	/**
	 * チェックボックス要否(勤怠一覧用)。
	 */
	private boolean				needCheckbox;
	
	/**
	 * 勤務時間合計(分)。
	 */
	private Integer				workTimeTotal;
	
	/**
	 * 休憩時間合計(分)。
	 */
	private Integer				restTimeTotal;
	
	/**
	 * 私用外出時間合計(分)。
	 */
	private Integer				privateTimeTotal;
	
	/**
	 * 公用外出回数合計(回)。
	 */
	private Integer				publicTimeTotal;
	
	/**
	 * 遅刻時間合計(分)。
	 */
	private Integer				lateTimeTotal;
	
	/**
	 * 早退時間合計(分)。
	 */
	private Integer				leaveEarlyTimeTotal;
	
	/**
	 * 遅刻早退時間合計(分)。
	 */
	private Integer				lateLeaveEarlyTimeTotal;
	
	/**
	 * 残業時間合計(分)。
	 */
	private Integer				overtimeTotal;
	
	/**
	 * 法定内残業時間合計(分)。
	 */
	private Integer				overtimeInTotal;
	
	/**
	 * 法定外残業時間合計(分)。
	 */
	private Integer				overtimeOutTotal;
	
	/**
	 * 深夜勤務時間合計(分)。
	 */
	private Integer				lateNightTimeTotal;
	
	/**
	 * 休日勤務時間合計(分)。
	 */
	private Integer				holidayWorkTimeTotal;
	
	/**
	 * 出勤回数。
	 */
	private Integer				workDays;
	
	/**
	 * 遅刻時間回数。
	 */
	private Integer				lateDays;
	
	/**
	 * 早退時間回数。
	 */
	private Integer				leaveEarlyDays;
	
	/**
	 * 残業時間回数。
	 */
	private Integer				overtimeDays;
	
	/**
	 * 深夜勤務時間回数。
	 */
	private Integer				holidayWorkDays;
	
	/**
	 * 休日勤務時間回数。
	 */
	private Integer				lateNightDays;
	
	/**
	 * 所定休日回数。
	 */
	private Integer				prescribedHolidays;
	
	/**
	 * 法定休日回数。
	 */
	private Integer				legalHolidays;
	
	/**
	 * 振替休日回数。
	 */
	private Float				substituteHolidays;
	
	/**
	 * 有給休暇回数。
	 */
	private Float				paidHolidays;
	
	/**
	 * 有休時間項目有無
	 */
	private boolean				isHourlyPaidHolidayValid;
	
	/**
	 * 有給時間。
	 */
	private Float				paidHolidayTime;
	
	/**
	 * 特別休暇回数タイトル(ヘッダ項目)。<br>
	 */
	private String				specialHolidaysTitle;
	
	/**
	 * 特別休暇回数。
	 */
	private Float				specialHolidays;
	
	/**
	 * その他休暇回数。
	 */
	private Float				otherHolidays;
	
	/**
	 * 代休回数。
	 */
	private Float				subHolidays;
	
	/**
	 * 欠勤回数。
	 */
	private Float				absenceDays;
	
	/**
	 * 分単位休暇A時間合計(分)。
	 */
	private Integer				minutelyHolidayATimeTotal;
	
	/**
	 * 分単位休暇B時間合計(分)。
	 */
	private Integer				minutelyHolidayBTimeTotal;
	
	/**
	 * 勤務日(表示用)。
	 */
	private String				workDateString;
	
	/**
	 * 勤務曜日(表示用)。
	 */
	private String				workDayOfWeek;
	
	/**
	 * 勤務曜日スタイル(表示用)。
	 */
	private String				workDayOfWeekStyle;
	
	/**
	 * 出勤時刻(表示用)。
	 */
	private String				startTimeString;
	
	/**
	 * 退勤時刻(表示用)。
	 */
	private String				endTimeString;
	
	/**
	 * 勤務時間(表示用)。
	 */
	private String				workTimeString;
	
	/**
	 * 遅刻時間(表示用)。
	 */
	private String				lateTimeString;
	
	/**
	 * 早退時間(表示用)。
	 */
	private String				leaveEarlyTimeString;
	
	/**
	 * 遅刻早退時間(表示用)。
	 */
	private String				lateLeaveEarlyTimeString;
	
	/**
	 * 休憩時間(表示用)。<br>
	 */
	private String				restTimeString;
	
	/**
	 * 私用外出時間(表示用)。<br>
	 */
	private String				privateTimeString;
	
	/**
	 * 公用外出時間(表示用)。<br>
	 */
	private String				publicTimeString;
	
	/**
	 * 残業時間(表示用)。<br>
	 */
	private String				overtimeString;
	
	/**
	 * 残業時間スタイル(表示用)。
	 */
	private String				overtimeStyle;
	
	/**
	 * 法定内残業時間(表示用)。
	 */
	private String				overtimeInString;
	
	/**
	 * 法定外残業時間(表示用)。
	 */
	private String				overtimeOutString;
	
	/**
	 * 深夜勤務時間(表示用)。
	 */
	private String				lateNightTimeString;
	
	/**
	 * 休日勤務時間(表示用)。
	 */
	private String				holidayWorkTimeString;
	
	/**
	 * 勤務時間合計(表示用)。
	 */
	private String				workTimeTotalString;
	
	/**
	 * 休憩時間合計(表示用)。
	 */
	private String				restTimeTotalString;
	
	/**
	 * 私用外出時間合計(表示用)。
	 */
	private String				privateTimeTotalString;
	
	/**
	 * 公用外出回数合計(表示用)。
	 */
	private String				publicTimeTotalString;
	
	/**
	 * 遅刻時間合計(表示用)。
	 */
	private String				lateTimeTotalString;
	
	/**
	 * 早退時間合計(表示用)。
	 */
	private String				leaveEarlyTimeTotalString;
	
	/**
	 * 遅刻早退時間合計(表示用)。
	 */
	private String				lateLeaveEarlyTimeTotalString;
	
	/**
	 * 残業時間合計(表示用)。
	 */
	private String				overtimeTotalString;
	
	/**
	 * 法定内残業時間合計(表示用)。
	 */
	private String				overtimeInTotalString;
	
	/**
	 * 法定外残業時間合計(表示用)。
	 */
	private String				overtimeOutTotalString;
	
	/**
	 * 深夜勤務時間合計(表示用)。
	 */
	private String				lateNightTimeTotalString;
	
	/**
	 * 休日勤務時間合計(表示用)。
	 */
	private String				holidayWorkTimeTotalString;
	
	/**
	 * 出勤回数(表示用)。
	 */
	private String				workDaysString;
	
	/**
	 * 遅刻時間回数(表示用)。
	 */
	private String				lateDaysString;
	
	/**
	 * 早退時間回数(表示用)。
	 */
	private String				leaveEarlyDaysString;
	
	/**
	 * 残業時間回数(表示用)。
	 */
	private String				overtimeDaysString;
	
	/**
	 * 深夜勤務時間回数(表示用)。
	 */
	private String				holidayWorkDaysString;
	
	/**
	 * 休日勤務時間回数(表示用)。
	 */
	private String				lateNightDaysString;
	
	/**
	 * 所定代休発生日数。
	 */
	private Float				birthPrescribedSubHoliday;
	
	/**
	 * 法定代休発生日数。
	 */
	private Float				birthLegalSubHoliday;
	
	/**
	 * 深夜代休発生日数。
	 */
	private Float				birthMidnightSubHoliday;
	
	/**
	 * 所定代休発生日数(表示用)。
	 */
	private String				birthPrescribedSubHolidayString;
	
	/**
	 * 法定代休発生日数(表示用)。
	 */
	private String				birthLegalSubHolidayString;
	
	/**
	 * 深夜代休発生日数(表示用)。
	 */
	private String				birthMidnightSubHolidayString;
	
	/**
	 * 所定休日回数(表示用)。
	 */
	private String				prescribedHolidaysString;
	
	/**
	 * 法定休日回数(表示用)。
	 */
	private String				legalHolidaysString;
	
	/**
	 * 振替休日回数(表示用)。
	 */
	private String				substituteHolidaysString;
	
	/**
	 * 有給休暇回数(表示用)。
	 */
	private String				paidHolidaysString;
	
	/**
	 * 有給時間(表示用)。
	 */
	private String				paidHolidayTimeString;
	
	/**
	 * 特別休暇回数(表示用)。
	 */
	private String				specialHolidaysString;
	
	/**
	 * その他休暇回数(表示用)。
	 */
	private String				otherHolidaysString;
	
	/**
	 * 代休回数(表示用)。
	 */
	private String				subHolidaysString;
	
	/**
	 * 欠勤回数(表示用)。
	 */
	private String				absenceDaysString;
	
	/**
	 * 分単位休暇A時間(表示用)。
	 */
	private String				minutelyHolidayATimeString;
	
	/**
	 * 分単位休暇B時間(表示用)。
	 */
	private String				minutelyHolidayBTimeString;
	
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return targetDate
	 */
	public Date getTargetDate() {
		return getDateClone(targetDate);
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = getDateClone(targetDate);
	}
	
	/**
	 * @return employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	/**
	 * @return employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	
	/**
	 * @param employeeName セットする employeeName
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	/**
	 * @return sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	/**
	 * @param sectionName セットする sectionName
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	/**
	 * @return alert
	 */
	public String getAlert() {
		return alert;
	}
	
	/**
	 * @param alert セットする alert
	 */
	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	/**
	 * @return overtimeTitle
	 */
	public String getOvertimeTitle() {
		return overtimeTitle;
	}
	
	/**
	 * @param overtimeTitle セットする overtimeTitle
	 */
	public void setOvertimeTitle(String overtimeTitle) {
		this.overtimeTitle = overtimeTitle;
	}
	
	/**
	 * @return isSubHolidayRequestValid
	 */
	public boolean isSubHolidayRequestValid() {
		return isSubHolidayRequestValid;
	}
	
	/**
	 * @param isSubHolidayRequestValid セットする isSubHolidayRequestValid
	 */
	public void setSubHolidayRequestValid(boolean isSubHolidayRequestValid) {
		this.isSubHolidayRequestValid = isSubHolidayRequestValid;
	}
	
	/**
	 * @return isSealBoxAvailable
	 */
	public boolean isSealBoxAvailable() {
		return isSealBoxAvailable;
	}
	
	/**
	 * @param isSealBoxAvailable セットする isSealBoxAvailable
	 */
	public void setSealBoxAvailable(boolean isSealBoxAvailable) {
		this.isSealBoxAvailable = isSealBoxAvailable;
	}
	
	/**
	 * @return workDate
	 */
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	/**
	 * @param workDate セットする workDate
	 */
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	/**
	 * @return workTypeCode
	 */
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	/**
	 * @param workTypeCode セットする workTypeCode
	 */
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	/**
	 * @return workTypeAbbr
	 */
	public String getWorkTypeAbbr() {
		return workTypeAbbr;
	}
	
	/**
	 * @param workTypeAbbr セットする workTypeAbbr
	 */
	public void setWorkTypeAbbr(String workTypeAbbr) {
		this.workTypeAbbr = workTypeAbbr;
	}
	
	/**
	 * @return workTypeAnteAbbr
	 */
	public String getWorkTypeAnteAbbr() {
		return workTypeAnteAbbr;
	}
	
	/**
	 * @param workTypeAnteAbbr セットする workTypeAnteAbbr
	 */
	public void setWorkTypeAnteAbbr(String workTypeAnteAbbr) {
		this.workTypeAnteAbbr = workTypeAnteAbbr;
	}
	
	/**
	 * @return workTypePostAbbr
	 */
	public String getWorkTypePostAbbr() {
		return workTypePostAbbr;
	}
	
	/**
	 * @param workTypePostAbbr セットする workTypePostAbbr
	 */
	public void setWorkTypePostAbbr(String workTypePostAbbr) {
		this.workTypePostAbbr = workTypePostAbbr;
	}
	
	/**
	 * @return startTime
	 */
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	/**
	 * @param startTime セットする startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return getDateClone(endTime);
	}
	
	/**
	 * @param endTime セットする endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
	}
	
	/**
	 * @return workTime
	 */
	public Integer getWorkTime() {
		return workTime;
	}
	
	/**
	 * @param workTime セットする workTime
	 */
	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}
	
	/**
	 * @return lateTime
	 */
	public Integer getLateTime() {
		return lateTime;
	}
	
	/**
	 * @param lateTime セットする lateTime
	 */
	public void setLateTime(Integer lateTime) {
		this.lateTime = lateTime;
	}
	
	/**
	 * @return leaveEarlyTime
	 */
	public Integer getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	/**
	 * @param leaveEarlyTime セットする leaveEarlyTime
	 */
	public void setLeaveEarlyTime(Integer leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	/**
	 * @return lateLeaveEarlyTime
	 */
	public Integer getLateLeaveEarlyTime() {
		return lateLeaveEarlyTime;
	}
	
	/**
	 * @param lateLeaveEarlyTime セットする lateLeaveEarlyTime
	 */
	public void setLateLeaveEarlyTime(Integer lateLeaveEarlyTime) {
		this.lateLeaveEarlyTime = lateLeaveEarlyTime;
	}
	
	/**
	 * @return restTime
	 */
	public Integer getRestTime() {
		return restTime;
	}
	
	/**
	 * @param restTime セットする restTime
	 */
	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}
	
	/**
	 * @return privateTime
	 */
	public Integer getPrivateTime() {
		return privateTime;
	}
	
	/**
	 * @param privateTime セットする privateTime
	 */
	public void setPrivateTime(Integer privateTime) {
		this.privateTime = privateTime;
	}
	
	/**
	 * @return publicTime
	 */
	public Integer getPublicTime() {
		return publicTime;
	}
	
	/**
	 * @param publicTime セットする publicTime
	 */
	public void setPublicTime(Integer publicTime) {
		this.publicTime = publicTime;
	}
	
	/**
	 * @return minutelyHolidayATime
	 */
	public Integer getMinutelyHolidayATime() {
		return minutelyHolidayATime;
	}
	
	/**
	 * @return minutelyHolidayATitle
	 */
	public String getMinutelyHolidayATitle() {
		return minutelyHolidayATitle;
	}
	
	/**
	 * @param minutelyHolidayATitle セットする minutelyHolidayATitle
	 */
	public void setMinutelyHolidayATitle(String minutelyHolidayATitle) {
		this.minutelyHolidayATitle = minutelyHolidayATitle;
	}
	
	/**
	 * @param minutelyHolidayATime セットする minutelyHolidayATime
	 */
	public void setMinutelyHolidayATime(Integer minutelyHolidayATime) {
		this.minutelyHolidayATime = minutelyHolidayATime;
	}
	
	/**
	 * @return minutelyHolidayBTitle
	 */
	public String getMinutelyHolidayBTitle() {
		return minutelyHolidayBTitle;
	}
	
	/**
	 * @param minutelyHolidayBTitle セットする minutelyHolidayBTitle
	 */
	public void setMinutelyHolidayBTitle(String minutelyHolidayBTitle) {
		this.minutelyHolidayBTitle = minutelyHolidayBTitle;
	}
	
	/**
	 * @return minutelyHolidayBTime
	 */
	public Integer getMinutelyHolidayBTime() {
		return minutelyHolidayBTime;
	}
	
	/**
	 * @param minutelyHolidayBTime セットする minutelyHolidayBTime
	 */
	public void setMinutelyHolidayBTime(Integer minutelyHolidayBTime) {
		this.minutelyHolidayBTime = minutelyHolidayBTime;
	}
	
	/**
	 * @return overtime
	 */
	public Integer getOvertime() {
		return overtime;
	}
	
	/**
	 * @param overtime セットする overtime
	 */
	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}
	
	/**
	 * @return overtimeIn
	 */
	public Integer getOvertimeIn() {
		return overtimeIn;
	}
	
	/**
	 * @param overtimeIn セットする overtimeIn
	 */
	public void setOvertimeIn(Integer overtimeIn) {
		this.overtimeIn = overtimeIn;
	}
	
	/**
	 * @return overtimeOut
	 */
	public Integer getOvertimeOut() {
		return overtimeOut;
	}
	
	/**
	 * @param overtimeOut セットする overtimeOut
	 */
	public void setOvertimeOut(Integer overtimeOut) {
		this.overtimeOut = overtimeOut;
	}
	
	/**
	 * @return lateNightTime
	 */
	public Integer getLateNightTime() {
		return lateNightTime;
	}
	
	/**
	 * @param lateNightTime セットする lateNightTime
	 */
	public void setLateNightTime(Integer lateNightTime) {
		this.lateNightTime = lateNightTime;
	}
	
	/**
	 * @return holidayWorkTime
	 */
	public Integer getHolidayWorkTime() {
		return holidayWorkTime;
	}
	
	/**
	 * @param holidayWorkTime セットする holidayWorkTime
	 */
	public void setHolidayWorkTime(Integer holidayWorkTime) {
		this.holidayWorkTime = holidayWorkTime;
	}
	
	/**
	 * @return applicationInfo
	 */
	public String getApplicationInfo() {
		return applicationInfo;
	}
	
	/**
	 * @param applicationInfo セットする applicationInfo
	 */
	public void setApplicationInfo(String applicationInfo) {
		this.applicationInfo = applicationInfo;
	}
	
	/**
	 * @return timeComment
	 */
	public String getTimeComment() {
		return timeComment;
	}
	
	/**
	 * @param timeComment セットする timeComment
	 */
	public void setTimeComment(String timeComment) {
		this.timeComment = timeComment;
	}
	
	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @param remark セットする remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return correctionInfo
	 */
	public String getCorrectionInfo() {
		return correctionInfo;
	}
	
	/**
	 * @param correctionInfo セットする correctionInfo
	 */
	public void setCorrectionInfo(String correctionInfo) {
		this.correctionInfo = correctionInfo;
	}
	
	/**
	 * @return needStatusLink
	 */
	public boolean isNeedStatusLink() {
		return needStatusLink;
	}
	
	/**
	 * @param needStatusLink セットする needStatusLink
	 */
	public void setNeedStatusLink(boolean needStatusLink) {
		this.needStatusLink = needStatusLink;
	}
	
	/**
	 * @return attendanceRecordId
	 */
	public long getAttendanceRecordId() {
		return attendanceRecordId;
	}
	
	/**
	 * @param attendanceRecordId セットする attendanceRecordId
	 */
	public void setAttendanceRecordId(long attendanceRecordId) {
		this.attendanceRecordId = attendanceRecordId;
	}
	
	/**
	 * @return workflowRecordId
	 */
	public long getWorkflowRecordId() {
		return workflowRecordId;
	}
	
	/**
	 * @param workflowRecordId セットする workflowRecordId
	 */
	public void setWorkflowRecordId(long workflowRecordId) {
		this.workflowRecordId = workflowRecordId;
	}
	
	/**
	 * @return workflow
	 */
	public long getWorkflow() {
		return workflow;
	}
	
	/**
	 * @param workflow セットする workflow
	 */
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	/**
	 * @return workflowStatus
	 */
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	
	/**
	 * @param workflowStatus セットする workflowStatus
	 */
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	/**
	 * @return workflowStage
	 */
	public int getWorkflowStage() {
		return workflowStage;
	}
	
	/**
	 * @param workflowStage セットする workflowStage
	 */
	public void setWorkflowStage(int workflowStage) {
		this.workflowStage = workflowStage;
	}
	
	/**
	 * @return needCheckbox
	 */
	public boolean isNeedCheckbox() {
		return needCheckbox;
	}
	
	/**
	 * @param needCheckbox セットする needCheckbox
	 */
	public void setNeedCheckbox(boolean needCheckbox) {
		this.needCheckbox = needCheckbox;
	}
	
	/**
	 * @return workTimeTotal
	 */
	public Integer getWorkTimeTotal() {
		return workTimeTotal;
	}
	
	/**
	 * @param workTimeTotal セットする workTimeTotal
	 */
	public void setWorkTimeTotal(Integer workTimeTotal) {
		this.workTimeTotal = workTimeTotal;
	}
	
	/**
	 * @return restTimeTotal
	 */
	public Integer getRestTimeTotal() {
		return restTimeTotal;
	}
	
	/**
	 * @param restTimeTotal セットする restTimeTotal
	 */
	public void setRestTimeTotal(Integer restTimeTotal) {
		this.restTimeTotal = restTimeTotal;
	}
	
	/**
	 * @return privateTimeTotal
	 */
	public Integer getPrivateTimeTotal() {
		return privateTimeTotal;
	}
	
	/**
	 * @param privateTimeTotal セットする privateTimeTotal
	 */
	public void setPrivateTimeTotal(Integer privateTimeTotal) {
		this.privateTimeTotal = privateTimeTotal;
	}
	
	/**
	 * @return publicTimeTotal
	 */
	public Integer getPublicTimeTotal() {
		return publicTimeTotal;
	}
	
	/**
	 * @param publicTimeTotal セットする publicTimeTotal
	 */
	public void setPublicTimeTotal(Integer publicTimeTotal) {
		this.publicTimeTotal = publicTimeTotal;
	}
	
	/**
	 * @return lateTimeTotal
	 */
	public Integer getLateTimeTotal() {
		return lateTimeTotal;
	}
	
	/**
	 * @param lateTimeTotal セットする lateTimeTotal
	 */
	public void setLateTimeTotal(Integer lateTimeTotal) {
		this.lateTimeTotal = lateTimeTotal;
	}
	
	/**
	 * @return leaveEarlyTimeTotal
	 */
	public Integer getLeaveEarlyTimeTotal() {
		return leaveEarlyTimeTotal;
	}
	
	/**
	 * @param leaveEarlyTimeTotal セットする leaveEarlyTimeTotal
	 */
	public void setLeaveEarlyTimeTotal(Integer leaveEarlyTimeTotal) {
		this.leaveEarlyTimeTotal = leaveEarlyTimeTotal;
	}
	
	/**
	 * @return lateLeaveEarlyTimeTotal
	 */
	public Integer getLateLeaveEarlyTimeTotal() {
		return lateLeaveEarlyTimeTotal;
	}
	
	/**
	 * @param lateLeaveEarlyTimeTotal セットする lateLeaveEarlyTimeTotal
	 */
	public void setLateLeaveEarlyTimeTotal(Integer lateLeaveEarlyTimeTotal) {
		this.lateLeaveEarlyTimeTotal = lateLeaveEarlyTimeTotal;
	}
	
	/**
	 * @return overtimeTotal
	 */
	public Integer getOvertimeTotal() {
		return overtimeTotal;
	}
	
	/**
	 * @param overtimeTotal セットする overtimeTotal
	 */
	public void setOvertimeTotal(Integer overtimeTotal) {
		this.overtimeTotal = overtimeTotal;
	}
	
	/**
	 * @return overtimeInTotal
	 */
	public Integer getOvertimeInTotal() {
		return overtimeInTotal;
	}
	
	/**
	 * @param overtimeInTotal セットする overtimeInTotal
	 */
	public void setOvertimeInTotal(Integer overtimeInTotal) {
		this.overtimeInTotal = overtimeInTotal;
	}
	
	/**
	 * @return overtimeOutTotal
	 */
	public Integer getOvertimeOutTotal() {
		return overtimeOutTotal;
	}
	
	/**
	 * @param overtimeOutTotal セットする overtimeOutTotal
	 */
	public void setOvertimeOutTotal(Integer overtimeOutTotal) {
		this.overtimeOutTotal = overtimeOutTotal;
	}
	
	/**
	 * @return lateNightTimeTotal
	 */
	public Integer getLateNightTimeTotal() {
		return lateNightTimeTotal;
	}
	
	/**
	 * @param lateNightTimeTotal セットする lateNightTimeTotal
	 */
	public void setLateNightTimeTotal(Integer lateNightTimeTotal) {
		this.lateNightTimeTotal = lateNightTimeTotal;
	}
	
	/**
	 * @return holidayWorkTimeTotal
	 */
	public Integer getHolidayWorkTimeTotal() {
		return holidayWorkTimeTotal;
	}
	
	/**
	 * @param holidayWorkTimeTotal セットする holidayWorkTimeTotal
	 */
	public void setHolidayWorkTimeTotal(Integer holidayWorkTimeTotal) {
		this.holidayWorkTimeTotal = holidayWorkTimeTotal;
	}
	
	/**
	 * @return workDays
	 */
	public Integer getWorkDays() {
		return workDays;
	}
	
	/**
	 * @param workDays セットする workDays
	 */
	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	
	/**
	 * @return lateDays
	 */
	public Integer getLateDays() {
		return lateDays;
	}
	
	/**
	 * @param lateDays セットする lateDays
	 */
	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}
	
	/**
	 * @return leaveEarlyDays
	 */
	public Integer getLeaveEarlyDays() {
		return leaveEarlyDays;
	}
	
	/**
	 * @param leaveEarlyDays セットする leaveEarlyDays
	 */
	public void setLeaveEarlyDays(Integer leaveEarlyDays) {
		this.leaveEarlyDays = leaveEarlyDays;
	}
	
	/**
	 * @return overtimeDays
	 */
	public Integer getOvertimeDays() {
		return overtimeDays;
	}
	
	/**
	 * @param overtimeDays セットする overtimeDays
	 */
	public void setOvertimeDays(Integer overtimeDays) {
		this.overtimeDays = overtimeDays;
	}
	
	/**
	 * @return holidayWorkDays
	 */
	public Integer getHolidayWorkDays() {
		return holidayWorkDays;
	}
	
	/**
	 * @param holidayWorkDays セットする holidayWorkDays
	 */
	public void setHolidayWorkDays(Integer holidayWorkDays) {
		this.holidayWorkDays = holidayWorkDays;
	}
	
	/**
	 * @return lateNightDays
	 */
	public Integer getLateNightDays() {
		return lateNightDays;
	}
	
	/**
	 * @param lateNightDays セットする lateNightDays
	 */
	public void setLateNightDays(Integer lateNightDays) {
		this.lateNightDays = lateNightDays;
	}
	
	/**
	 * @return prescribedHolidays
	 */
	public Integer getPrescribedHolidays() {
		return prescribedHolidays;
	}
	
	/**
	 * @param prescribedHolidays セットする prescribedHolidays
	 */
	public void setPrescribedHolidays(Integer prescribedHolidays) {
		this.prescribedHolidays = prescribedHolidays;
	}
	
	/**
	 * @return legalHolidays
	 */
	public Integer getLegalHolidays() {
		return legalHolidays;
	}
	
	/**
	 * @param legalHolidays セットする legalHolidays
	 */
	public void setLegalHolidays(Integer legalHolidays) {
		this.legalHolidays = legalHolidays;
	}
	
	/**
	 * @return substituteHolidays
	 */
	public Float getSubstituteHolidays() {
		return substituteHolidays;
	}
	
	/**
	 * @param substituteHolidays セットする substituteHolidays
	 */
	public void setSubstituteHolidays(Float substituteHolidays) {
		this.substituteHolidays = substituteHolidays;
	}
	
	/**
	 * @return paidHolidays
	 */
	public Float getPaidHolidays() {
		return paidHolidays;
	}
	
	/**
	 * @param paidHolidays セットする paidHolidays
	 */
	public void setPaidHolidays(Float paidHolidays) {
		this.paidHolidays = paidHolidays;
	}
	
	/**
	 * @return isHourlyPaidHolidayValid
	 */
	public boolean isHourlyPaidHolidayValid() {
		return isHourlyPaidHolidayValid;
	}
	
	/**
	 * @param isHourlyPaidHolidayValid セットする isHourlyPaidHolidayValid
	 */
	public void setHourlyPaidHolidayValid(boolean isHourlyPaidHolidayValid) {
		this.isHourlyPaidHolidayValid = isHourlyPaidHolidayValid;
	}
	
	/**
	 * @return paidHolidayTime
	 */
	public Float getPaidHolidayTime() {
		return paidHolidayTime;
	}
	
	/**
	 * @param paidHolidayTime セットする paidHolidayTime
	 */
	public void setPaidHolidayTime(Float paidHolidayTime) {
		this.paidHolidayTime = paidHolidayTime;
	}
	
	/**
	 * @return specialHolidaysTitle
	 */
	public String getSpecialHolidaysTitle() {
		return specialHolidaysTitle;
	}
	
	/**
	 * @param specialHolidaysTitle セットする specialHolidaysTitle
	 */
	public void setSpecialHolidaysTitle(String specialHolidaysTitle) {
		this.specialHolidaysTitle = specialHolidaysTitle;
	}
	
	/**
	 * @return specialHolidays
	 */
	public Float getSpecialHolidays() {
		return specialHolidays;
	}
	
	/**
	 * @param specialHolidays セットする specialHolidays
	 */
	public void setSpecialHolidays(Float specialHolidays) {
		this.specialHolidays = specialHolidays;
	}
	
	/**
	 * @return otherHolidays
	 */
	public Float getOtherHolidays() {
		return otherHolidays;
	}
	
	/**
	 * @param otherHolidays セットする otherHolidays
	 */
	public void setOtherHolidays(Float otherHolidays) {
		this.otherHolidays = otherHolidays;
	}
	
	/**
	 * @return subHolidays
	 */
	public Float getSubHolidays() {
		return subHolidays;
	}
	
	/**
	 * @param subHolidays セットする subHolidays
	 */
	public void setSubHolidays(Float subHolidays) {
		this.subHolidays = subHolidays;
	}
	
	/**
	 * @return absenceDays
	 */
	public Float getAbsenceDays() {
		return absenceDays;
	}
	
	/**
	 * @param absenceDays セットする absenceDays
	 */
	public void setAbsenceDays(Float absenceDays) {
		this.absenceDays = absenceDays;
	}
	
	/**
	 * @return workDateString
	 */
	public String getWorkDateString() {
		return workDateString;
	}
	
	/**
	 * @param workDateString セットする workDateString
	 */
	public void setWorkDateString(String workDateString) {
		this.workDateString = workDateString;
	}
	
	/**
	 * @return workDayOfWeek
	 */
	public String getWorkDayOfWeek() {
		return workDayOfWeek;
	}
	
	/**
	 * @param workDayOfWeek セットする workDayOfWeek
	 */
	public void setWorkDayOfWeek(String workDayOfWeek) {
		this.workDayOfWeek = workDayOfWeek;
	}
	
	/**
	 * @return workDayOfWeekStyle
	 */
	public String getWorkDayOfWeekStyle() {
		return workDayOfWeekStyle;
	}
	
	/**
	 * @param workDayOfWeekStyle セットする workDayOfWeekStyle
	 */
	public void setWorkDayOfWeekStyle(String workDayOfWeekStyle) {
		this.workDayOfWeekStyle = workDayOfWeekStyle;
	}
	
	/**
	 * @return startTimeString
	 */
	public String getStartTimeString() {
		return startTimeString;
	}
	
	/**
	 * @param startTimeString セットする startTimeString
	 */
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	
	/**
	 * @return endTimeString
	 */
	public String getEndTimeString() {
		return endTimeString;
	}
	
	/**
	 * @param endTimeString セットする endTimeString
	 */
	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}
	
	/**
	 * @return workTimeString
	 */
	public String getWorkTimeString() {
		return workTimeString;
	}
	
	/**
	 * @param workTimeString セットする workTimeString
	 */
	public void setWorkTimeString(String workTimeString) {
		this.workTimeString = workTimeString;
	}
	
	/**
	 * @return lateTimeString
	 */
	public String getLateTimeString() {
		return lateTimeString;
	}
	
	/**
	 * @param lateTimeString セットする lateTimeString
	 */
	public void setLateTimeString(String lateTimeString) {
		this.lateTimeString = lateTimeString;
	}
	
	/**
	 * @return leaveEarlyTimeString
	 */
	public String getLeaveEarlyTimeString() {
		return leaveEarlyTimeString;
	}
	
	/**
	 * @param leaveEarlyTimeString セットする leaveEarlyTimeString
	 */
	public void setLeaveEarlyTimeString(String leaveEarlyTimeString) {
		this.leaveEarlyTimeString = leaveEarlyTimeString;
	}
	
	/**
	 * @return lateLeaveEarlyTimeString
	 */
	public String getLateLeaveEarlyTimeString() {
		return lateLeaveEarlyTimeString;
	}
	
	/**
	 * @param lateLeaveEarlyTimeString セットする lateLeaveEarlyTimeString
	 */
	public void setLateLeaveEarlyTimeString(String lateLeaveEarlyTimeString) {
		this.lateLeaveEarlyTimeString = lateLeaveEarlyTimeString;
	}
	
	/**
	 * @return restTimeString
	 */
	public String getRestTimeString() {
		return restTimeString;
	}
	
	/**
	 * @param restTimeString セットする restTimeString
	 */
	public void setRestTimeString(String restTimeString) {
		this.restTimeString = restTimeString;
	}
	
	/**
	 * @return privateTimeString
	 */
	public String getPrivateTimeString() {
		return privateTimeString;
	}
	
	/**
	 * @param privateTimeString セットする privateTimeString
	 */
	public void setPrivateTimeString(String privateTimeString) {
		this.privateTimeString = privateTimeString;
	}
	
	/**
	 * @return publicTimeString
	 */
	public String getPublicTimeString() {
		return publicTimeString;
	}
	
	/**
	 * @param publicTimeString セットする publicTimeString
	 */
	public void setPublicTimeString(String publicTimeString) {
		this.publicTimeString = publicTimeString;
	}
	
	/**
	 * @return overtimeString
	 */
	public String getOvertimeString() {
		return overtimeString;
	}
	
	/**
	 * @param overtimeString セットする overtimeString
	 */
	public void setOvertimeString(String overtimeString) {
		this.overtimeString = overtimeString;
	}
	
	/**
	 * @return overtimeStyle
	 */
	public String getOvertimeStyle() {
		return overtimeStyle;
	}
	
	/**
	 * @param overtimeStyle セットする overtimeStyle
	 */
	public void setOvertimeStyle(String overtimeStyle) {
		this.overtimeStyle = overtimeStyle;
	}
	
	/**
	 * @return overtimeInString
	 */
	public String getOvertimeInString() {
		return overtimeInString;
	}
	
	/**
	 * @param overtimeInString セットする overtimeInString
	 */
	public void setOvertimeInString(String overtimeInString) {
		this.overtimeInString = overtimeInString;
	}
	
	/**
	 * @return overtimeOutString
	 */
	public String getOvertimeOutString() {
		return overtimeOutString;
	}
	
	/**
	 * @param overtimeOutString セットする overtimeOutString
	 */
	public void setOvertimeOutString(String overtimeOutString) {
		this.overtimeOutString = overtimeOutString;
	}
	
	/**
	 * @return lateNightTimeString
	 */
	public String getLateNightTimeString() {
		return lateNightTimeString;
	}
	
	/**
	 * @param lateNightTimeString セットする lateNightTimeString
	 */
	public void setLateNightTimeString(String lateNightTimeString) {
		this.lateNightTimeString = lateNightTimeString;
	}
	
	/**
	 * @return holidayWorkTimeString
	 */
	public String getHolidayWorkTimeString() {
		return holidayWorkTimeString;
	}
	
	/**
	 * @param holidayWorkTimeString セットする holidayWorkTimeString
	 */
	public void setHolidayWorkTimeString(String holidayWorkTimeString) {
		this.holidayWorkTimeString = holidayWorkTimeString;
	}
	
	/**
	 * @return workTimeTotalString
	 */
	public String getWorkTimeTotalString() {
		return workTimeTotalString;
	}
	
	/**
	 * @param workTimeTotalString セットする workTimeTotalString
	 */
	public void setWorkTimeTotalString(String workTimeTotalString) {
		this.workTimeTotalString = workTimeTotalString;
	}
	
	/**
	 * @return restTimeTotalString
	 */
	public String getRestTimeTotalString() {
		return restTimeTotalString;
	}
	
	/**
	 * @param restTimeTotalString セットする restTimeTotalString
	 */
	public void setRestTimeTotalString(String restTimeTotalString) {
		this.restTimeTotalString = restTimeTotalString;
	}
	
	/**
	 * @return privateTimeTotalString
	 */
	public String getPrivateTimeTotalString() {
		return privateTimeTotalString;
	}
	
	/**
	 * @param privateTimeTotalString セットする privateTimeTotalString
	 */
	public void setPrivateTimeTotalString(String privateTimeTotalString) {
		this.privateTimeTotalString = privateTimeTotalString;
	}
	
	/**
	 * @return publicTimeTotalString
	 */
	public String getPublicTimeTotalString() {
		return publicTimeTotalString;
	}
	
	/**
	 * @param publicTimeTotalString セットする publicTimeTotalString
	 */
	public void setPublicTimeTotalString(String publicTimeTotalString) {
		this.publicTimeTotalString = publicTimeTotalString;
	}
	
	/**
	 * @return lateTimeTotalString
	 */
	public String getLateTimeTotalString() {
		return lateTimeTotalString;
	}
	
	/**
	 * @param lateTimeTotalString セットする lateTimeTotalString
	 */
	public void setLateTimeTotalString(String lateTimeTotalString) {
		this.lateTimeTotalString = lateTimeTotalString;
	}
	
	/**
	 * @return leaveEarlyTimeTotalString
	 */
	public String getLeaveEarlyTimeTotalString() {
		return leaveEarlyTimeTotalString;
	}
	
	/**
	 * @param leaveEarlyTimeTotalString セットする leaveEarlyTimeTotalString
	 */
	public void setLeaveEarlyTimeTotalString(String leaveEarlyTimeTotalString) {
		this.leaveEarlyTimeTotalString = leaveEarlyTimeTotalString;
	}
	
	/**
	 * @return lateLeaveEarlyTimeTotalString
	 */
	public String getLateLeaveEarlyTimeTotalString() {
		return lateLeaveEarlyTimeTotalString;
	}
	
	/**
	 * @param lateLeaveEarlyTimeTotalString セットする lateLeaveEarlyTimeTotalString
	 */
	public void setLateLeaveEarlyTimeTotalString(String lateLeaveEarlyTimeTotalString) {
		this.lateLeaveEarlyTimeTotalString = lateLeaveEarlyTimeTotalString;
	}
	
	/**
	 * @return overtimeTotalString
	 */
	public String getOvertimeTotalString() {
		return overtimeTotalString;
	}
	
	/**
	 * @param overtimeTotalString セットする overtimeTotalString
	 */
	public void setOvertimeTotalString(String overtimeTotalString) {
		this.overtimeTotalString = overtimeTotalString;
	}
	
	/**
	 * @return overtimeInTotalString
	 */
	public String getOvertimeInTotalString() {
		return overtimeInTotalString;
	}
	
	/**
	 * @param overtimeInTotalString セットする overtimeInTotalString
	 */
	public void setOvertimeInTotalString(String overtimeInTotalString) {
		this.overtimeInTotalString = overtimeInTotalString;
	}
	
	/**
	 * @return overtimeOutTotalString
	 */
	public String getOvertimeOutTotalString() {
		return overtimeOutTotalString;
	}
	
	/**
	 * @param overtimeOutTotalString セットする overtimeOutTotalString
	 */
	public void setOvertimeOutTotalString(String overtimeOutTotalString) {
		this.overtimeOutTotalString = overtimeOutTotalString;
	}
	
	/**
	 * @return lateNightTimeTotalString
	 */
	public String getLateNightTimeTotalString() {
		return lateNightTimeTotalString;
	}
	
	/**
	 * @param lateNightTimeTotalString セットする lateNightTimeTotalString
	 */
	public void setLateNightTimeTotalString(String lateNightTimeTotalString) {
		this.lateNightTimeTotalString = lateNightTimeTotalString;
	}
	
	/**
	 * @return holidayWorkTimeTotalString
	 */
	public String getHolidayWorkTimeTotalString() {
		return holidayWorkTimeTotalString;
	}
	
	/**
	 * @param holidayWorkTimeTotalString セットする holidayWorkTimeTotalString
	 */
	public void setHolidayWorkTimeTotalString(String holidayWorkTimeTotalString) {
		this.holidayWorkTimeTotalString = holidayWorkTimeTotalString;
	}
	
	/**
	 * @return workDaysString
	 */
	public String getWorkDaysString() {
		return workDaysString;
	}
	
	/**
	 * @param workDaysString セットする workDaysString
	 */
	public void setWorkDaysString(String workDaysString) {
		this.workDaysString = workDaysString;
	}
	
	/**
	 * @return lateDaysString
	 */
	public String getLateDaysString() {
		return lateDaysString;
	}
	
	/**
	 * @param lateDaysString セットする lateDaysString
	 */
	public void setLateDaysString(String lateDaysString) {
		this.lateDaysString = lateDaysString;
	}
	
	/**
	 * @return leaveEarlyDaysString
	 */
	public String getLeaveEarlyDaysString() {
		return leaveEarlyDaysString;
	}
	
	/**
	 * @param leaveEarlyDaysString セットする leaveEarlyDaysString
	 */
	public void setLeaveEarlyDaysString(String leaveEarlyDaysString) {
		this.leaveEarlyDaysString = leaveEarlyDaysString;
	}
	
	/**
	 * @return overtimeDaysString
	 */
	public String getOvertimeDaysString() {
		return overtimeDaysString;
	}
	
	/**
	 * @param overtimeDaysString セットする overtimeDaysString
	 */
	public void setOvertimeDaysString(String overtimeDaysString) {
		this.overtimeDaysString = overtimeDaysString;
	}
	
	/**
	 * @return holidayWorkDaysString
	 */
	public String getHolidayWorkDaysString() {
		return holidayWorkDaysString;
	}
	
	/**
	 * @param holidayWorkDaysString セットする holidayWorkDaysString
	 */
	public void setHolidayWorkDaysString(String holidayWorkDaysString) {
		this.holidayWorkDaysString = holidayWorkDaysString;
	}
	
	/**
	 * @return birthPrescribedSubHoliday
	 */
	public Float getBirthPrescribedSubHoliday() {
		return birthPrescribedSubHoliday;
	}
	
	/**
	 * @param birthPrescribedSubHoliday セットする birthPrescribedSubHoliday
	 */
	public void setBirthPrescribedSubHoliday(Float birthPrescribedSubHoliday) {
		this.birthPrescribedSubHoliday = birthPrescribedSubHoliday;
	}
	
	/**
	 * @return birthLegalSubHolidaydaysString
	 */
	public Float getBirthLegalSubHoliday() {
		return birthLegalSubHoliday;
	}
	
	/**
	 * @param birthLegalSubHoliday セットする birthLegalSubHoliday
	 */
	public void setBirthLegalSubHoliday(Float birthLegalSubHoliday) {
		this.birthLegalSubHoliday = birthLegalSubHoliday;
	}
	
	/**
	 * @return birthMidnightSubHoliday
	 */
	public Float getBirthMidnightSubHoliday() {
		return birthMidnightSubHoliday;
	}
	
	/**
	 * @param birthMidnightSubHoliday セットする birthMidnightSubHoliday
	 */
	public void setBirthMidnightSubHolidaydays(Float birthMidnightSubHoliday) {
		this.birthMidnightSubHoliday = birthMidnightSubHoliday;
	}
	
	/**
	 * @return birthPrescribedSubHolidayString
	 */
	public String getBirthPrescribedSubHolidayString() {
		return birthPrescribedSubHolidayString;
	}
	
	/**
	 * @param birthPrescribedSubHolidayString セットする birthPrescribedSubHolidayString
	 */
	public void setBirthPrescribedSubHolidayString(String birthPrescribedSubHolidayString) {
		this.birthPrescribedSubHolidayString = birthPrescribedSubHolidayString;
	}
	
	/**
	 * @return birthLegalSubHolidaydaysStringString
	 */
	public String getBirthLegalSubHolidayString() {
		return birthLegalSubHolidayString;
	}
	
	/**
	 * @param birthLegalSubHolidayString セットする birthLegalSubHolidayString
	 */
	public void setBirthLegalSubHolidayString(String birthLegalSubHolidayString) {
		this.birthLegalSubHolidayString = birthLegalSubHolidayString;
	}
	
	/**
	 * @return birthMidnightSubHolidayString
	 */
	public String getBirthMidnightSubHolidayString() {
		return birthMidnightSubHolidayString;
	}
	
	/**
	 * @param birthMidnightSubHolidayString セットする birthMidnightSubHolidayString
	 */
	public void setBirthMidnightSubHolidayString(String birthMidnightSubHolidayString) {
		this.birthMidnightSubHolidayString = birthMidnightSubHolidayString;
	}
	
	/**
	 * @return lateNightDaysString
	 */
	public String getLateNightDaysString() {
		return lateNightDaysString;
	}
	
	/**
	 * @param lateNightDaysString セットする lateNightDaysString
	 */
	public void setLateNightDaysString(String lateNightDaysString) {
		this.lateNightDaysString = lateNightDaysString;
	}
	
	/**
	 * @return prescribedHolidaysString
	 */
	public String getPrescribedHolidaysString() {
		return prescribedHolidaysString;
	}
	
	/**
	 * @param prescribedHolidaysString セットする prescribedHolidaysString
	 */
	public void setPrescribedHolidaysString(String prescribedHolidaysString) {
		this.prescribedHolidaysString = prescribedHolidaysString;
	}
	
	/**
	 * @return legalHolidaysString
	 */
	public String getLegalHolidaysString() {
		return legalHolidaysString;
	}
	
	/**
	 * @param legalHolidaysString セットする legalHolidaysString
	 */
	public void setLegalHolidaysString(String legalHolidaysString) {
		this.legalHolidaysString = legalHolidaysString;
	}
	
	/**
	 * @return substituteHolidaysString
	 */
	public String getSubstituteHolidaysString() {
		return substituteHolidaysString;
	}
	
	/**
	 * @param substituteHolidaysString セットする substituteHolidaysString
	 */
	public void setSubstituteHolidaysString(String substituteHolidaysString) {
		this.substituteHolidaysString = substituteHolidaysString;
	}
	
	/**
	 * @return paidHolidaysString
	 */
	public String getPaidHolidaysString() {
		return paidHolidaysString;
	}
	
	/**
	 * @param paidHolidaysString セットする paidHolidaysString
	 */
	public void setPaidHolidaysString(String paidHolidaysString) {
		this.paidHolidaysString = paidHolidaysString;
	}
	
	/**
	 * @return paidHolidayTimeString
	 */
	public String getPaidHolidayTimeString() {
		return paidHolidayTimeString;
	}
	
	/**
	 * @param paidHolidayTimeString セットする paidHolidayTimeString
	 */
	public void setPaidHolidayTimeString(String paidHolidayTimeString) {
		this.paidHolidayTimeString = paidHolidayTimeString;
	}
	
	/**
	 * @return specialHolidaysString
	 */
	public String getSpecialHolidaysString() {
		return specialHolidaysString;
	}
	
	/**
	 * @param specialHolidaysString セットする specialHolidaysString
	 */
	public void setSpecialHolidaysString(String specialHolidaysString) {
		this.specialHolidaysString = specialHolidaysString;
	}
	
	/**
	 * @return otherHolidaysString
	 */
	public String getOtherHolidaysString() {
		return otherHolidaysString;
	}
	
	/**
	 * @param otherHolidaysString セットする otherHolidaysString
	 */
	public void setOtherHolidaysString(String otherHolidaysString) {
		this.otherHolidaysString = otherHolidaysString;
	}
	
	/**
	 * @return subHolidaysString
	 */
	public String getSubHolidaysString() {
		return subHolidaysString;
	}
	
	/**
	 * @param subHolidaysString セットする subHolidaysString
	 */
	public void setSubHolidaysString(String subHolidaysString) {
		this.subHolidaysString = subHolidaysString;
	}
	
	/**
	 * @return absenceDaysString
	 */
	public String getAbsenceDaysString() {
		return absenceDaysString;
	}
	
	/**
	 * @param absenceDaysString セットする absenceDaysString
	 */
	public void setAbsenceDaysString(String absenceDaysString) {
		this.absenceDaysString = absenceDaysString;
	}
	
	/**
	 * @return minutelyHolidayATimeString
	 */
	public String getMinutelyHolidayATimeString() {
		return minutelyHolidayATimeString;
	}
	
	/**
	 * @param minutelyHolidayATimeString セットする minutelyHolidayATimeString
	 */
	public void setMinutelyHolidayATimeString(String minutelyHolidayATimeString) {
		this.minutelyHolidayATimeString = minutelyHolidayATimeString;
	}
	
	/**
	 * @return minutelyHolidayBTimeString
	 */
	public String getMinutelyHolidayBTimeString() {
		return minutelyHolidayBTimeString;
	}
	
	/**
	 * @param minutelyHolidayBTimeString セットする minutelyHolidayBTimeString
	 */
	public void setMinutelyHolidayBTimeString(String minutelyHolidayBTimeString) {
		this.minutelyHolidayBTimeString = minutelyHolidayBTimeString;
	}
	
	/**
	 * @return minutelyHolidayATimeTotal
	 */
	public Integer getMinutelyHolidayATimeTotal() {
		return minutelyHolidayATimeTotal;
	}
	
	/**
	 * @param minutelyHolidayATimeTotal セットする minutelyHolidayATimeTotal
	 */
	public void setMinutelyHolidayATimeTotal(Integer minutelyHolidayATimeTotal) {
		this.minutelyHolidayATimeTotal = minutelyHolidayATimeTotal;
	}
	
	/**
	 * @return minutelyHolidayBTimeTotal
	 */
	public Integer getMinutelyHolidayBTimeTotal() {
		return minutelyHolidayBTimeTotal;
	}
	
	/**
	 * @param minutelyHolidayBTimeTotal セットする minutelyHolidayBTimeTotal
	 */
	public void setMinutelyHolidayBTimeTotal(Integer minutelyHolidayBTimeTotal) {
		this.minutelyHolidayBTimeTotal = minutelyHolidayBTimeTotal;
	}
	
	/**
	 * @return minutelyHolidayA
	 */
	public int getMinutelyHolidayA() {
		return minutelyHolidayA;
	}
	
	/**
	 * @param minutelyHolidayA セットする minutelyHolidayA
	 */
	public void setMinutelyHolidayA(int minutelyHolidayA) {
		this.minutelyHolidayA = minutelyHolidayA;
	}
	
	/**
	 * @return minutelyHolidayB
	 */
	public int getMinutelyHolidayB() {
		return minutelyHolidayB;
	}
	
	/**
	 * @param minutelyHolidayB セットする minutelyHolidayB
	 */
	public void setMinutelyHolidayB(int minutelyHolidayB) {
		this.minutelyHolidayB = minutelyHolidayB;
	}
	
	/**
	 * @return goingWork
	 */
	public int getGoingWork() {
		return goingWork;
	}
	
	/**
	 * @param goingWork セットする goingWork
	 */
	public void setGoingWork(int goingWork) {
		this.goingWork = goingWork;
	}
	
}
