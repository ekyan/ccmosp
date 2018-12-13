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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.AttendanceListBaseVo;

/**
 * 勤怠情報の一覧表示の情報を格納する。
 */
public class AttendanceListVo extends AttendanceListBaseVo {
	
	private static final long	serialVersionUID	= 5197932165843778696L;
	
	/**
	 * 日付(YYYY/MM/DD)。
	 */
	private String[]			aryDate;
	
	/**
	 * 始業打刻時間。
	 */
	private String[]			aryLblStartRecordTime;
	
	/**
	 * 終業打刻時間
	 */
	private String[]			aryLblEndRecordTime;
	
	/**
	 * 私用外出時間。
	 */
	private String[]			aryLblPrivateTime;
	
	/**
	 * 遅刻時間。
	 */
	private String[]			aryLblLateTime;
	
	/**
	 * 早退時間。
	 */
	private String[]			aryLblLeaveEarlyTime;
	
	/**
	 * 遅刻早退時間。
	 */
	private String[]			aryLblLateLeaveEarlyTime;
	
	/**
	 * 法定内残業時間。
	 */
	private String[]			aryLblOverTimeIn;
	
	/**
	 * 残業時間スタイル。
	 */
	private String[]			aryOvertimeStyle;
	
	/**
	 * 法定外残業時間。
	 */
	private String[]			aryLblOverTimeOut;
	
	/**
	 * 休日出勤時間。
	 */
	private String[]			aryLblWorkOnHoliday;
	
	/**
	 * 深夜時間。
	 */
	private String[]			aryLblLateNight;
	
	/**
	 * 無休時短時間。
	 */
	private String[]			aryLblShortUnpaid;
	
	/**
	 * カット([外出]+[遅早]+[無給時短時間])
	 */
	private String[]			aryLblCat;
	
	/**
	 * 状態。
	 */
	private String[]			aryLblState;
	
	/**
	 * 状態スタイル。
	 */
	private String[]			aryStateStyle;
	
	/**
	 * 状態リンクコマンド。
	 */
	private boolean[]			aryLinkState;
	
	/**
	 * 勤怠データレコード識別ID。
	 */
	private long[]				aryAttendanceRecordId;
	
	/**
	 * ワークフローレコード識別ID。
	 */
	private long[]				aryWorkflowRecordId;
	
	/**
	 * ワークフロー番号。
	 */
	private long[]				aryWorkflow;
	
	/**
	 * ワークフロー状況。
	 */
	private String[]			aryWorkflowStatus;
	
	/**
	 * ワークフロー段階。
	 */
	private int[]				aryWorkflowStage;
	
	/**
	 * チェックボックス要否情報。
	 */
	private boolean[]			aryCheckState;
	
	/**
	 * 修正情報。
	 */
	private String[]			aryLblCorrection;
	
	/**
	 * 合計私用外出時間。
	 */
	private String				lblTotalPrivate;
	
	/**
	 * 合計遅刻時間。
	 */
	private String				lblTotalLate;
	
	/**
	 * 合計早退時間。
	 */
	private String				lblTotalLeaveEarly;
	
	/**
	 * 合計遅刻早退時間。
	 */
	private String				lblTotalLateLeaveEarly;
	
	/**
	 * 合計残業時間。
	 */
	private String				lblTotalOverTimeIn;
	
	/**
	 * 合計外残時間。
	 */
	private String				lblTotalOverTimeOut;
	
	/**
	 * 合計休日出勤時間。
	 */
	private String				lblTotalWorkOnHoliday;
	
	/**
	 * 合計深夜勤務時間。
	 */
	private String				lblTotalLateNight;
	
	/**
	 * 合計遅刻回数。
	 */
	private String				lblTimesLate;
	
	/**
	 * 合計早退回数。
	 */
	private String				lblTimesLeaveEarly;
	
	/**
	 * 合計残業回数。
	 */
	private String				lblTimesOverTimeWork;
	
	/**
	 * 合計休出回数。
	 */
	private String				lblTimesWorkOnHoliday;
	
	/**
	 * 合計所定代休発生回数。
	 */
	private String				lblTimesBirthPrescribedSubHolidayday;
	
	/**
	 * 合計法定代休発生回数。
	 */
	private String				lblTimesBirthLegalSubHolidayday;
	
	/**
	 * 合計深夜代休発生回数。
	 */
	private String				lblTimesBirthMidnightSubHolidayday;
	
	/**
	 * 合計休暇日数(所定休日+法定休日)
	 */
	private String				lblTimesHoliday;
	
	/**
	 * 合計有休日数。
	 */
	private String				lblTimesPaidHoliday;
	
	/**
	 * 合計有休時間。
	 */
	private String				lblTimesPaidHolidayTime;
	
	/**
	 * 合計特別休暇日数。
	 */
	private String				lblTimesSpecialHoloiday;
	
	/**
	 * 合計その他休暇日数。
	 */
	private String				lblTimesOtherHoloiday;
	
	/**
	 * 合計振替休暇日数。
	 */
	private String				lblTimesSubstitute;
	
	/**
	 * 合計代休日数。
	 */
	private String				lblTimesSubHoliday;
	
	/**
	 * 合計欠勤日数。
	 */
	private String				lblTimesAbsence;
	
	/**
	 * 合計無休時短時間。
	 */
	private String				lblShortUnpaidTotal;
	
	/**
	 * 合計分単位休暇A時間。
	 */
	private String				lblTimesMinutelyHolidayA;
	
	/**
	 * 合計分単位休暇B時間。
	 */
	private String				lblTimesMinutelyHolidayB;
	
	/**
	 * 始業時間(入力値取得用)。
	 */
	private String[]			txtStartTime;
	
	/**
	 * 終業時間(入力値取得用)。
	 */
	private String[]			txtEndTime;
	
	/**
	 * 集計ボタン要否。
	 */
	private boolean				isTotalButtonVisible;
	
	/**
	 * 合計分単位休暇A時間表示要否。
	 */
	private boolean				isLblTimesMinutelyHolidayA;
	
	/**
	 * 合計分単位休暇B時間表示要否。
	 */
	private boolean				isLblTimesMinutelyHolidayB;
	
	/**
	 * 始業打刻時間表示要否。
	 */
	private boolean				isLblStartRecordTime;
	
	/**
	 * 終業打刻時間表示要否。
	 */
	private boolean				isLblEndRecordTime;
	
	/**
	 * カット([外出]+[遅早]+[無給時短時間])表示要否。
	 */
	private boolean				isLblTimesCat;
	
	/**
	 * 追加フィールド。<br>
	 * アドオン機能で利用する。<br>
	 */
	private String				lblExtraFiled;
	
	
	/**
	 * @return txtStartTime
	 */
	public String[] getTxtStartTime() {
		return getStringArrayClone(txtStartTime);
	}
	
	/**
	 * @param txtStartTime セットする txtStartTime
	 */
	public void setTxtStartTime(String[] txtStartTime) {
		this.txtStartTime = getStringArrayClone(txtStartTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStartRecordTime
	 */
	public String getAryLblStartRecordTime(int idx) {
		return aryLblStartRecordTime[idx];
	}
	
	/**
	 * @param aryLblStartRecordTime セットする aryLblStartRecordTime
	 */
	public void setAryLblStartRecordTime(String[] aryLblStartRecordTime) {
		this.aryLblStartRecordTime = getStringArrayClone(aryLblStartRecordTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStartRecordTime
	 */
	public String getAryLblEndRecordTime(int idx) {
		return aryLblEndRecordTime[idx];
	}
	
	/**
	 * @param aryLblEndRecordTime セットする aryLblStartRecordTime
	 */
	public void setAryLblEndRecordTime(String[] aryLblEndRecordTime) {
		this.aryLblEndRecordTime = getStringArrayClone(aryLblEndRecordTime);
	}
	
	/**
	 * @return txtEndTime
	 */
	public String[] getTxtEndTime() {
		return getStringArrayClone(txtEndTime);
	}
	
	/**
	 * @param txtEndTime セットする txtEndTime
	 */
	public void setTxtEndTime(String[] txtEndTime) {
		this.txtEndTime = getStringArrayClone(txtEndTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPrivateTime
	 */
	public String getAryLblPrivateTime(int idx) {
		return aryLblPrivateTime[idx];
	}
	
	/**
	 * @param aryLblPrivateTime セットする aryLblPrivateTime
	 */
	public void setAryLblPrivateTime(String[] aryLblPrivateTime) {
		this.aryLblPrivateTime = getStringArrayClone(aryLblPrivateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateTime
	 */
	public String getAryLblLateTime(int idx) {
		return aryLblLateTime[idx];
	}
	
	/**
	 * @param aryLblLateTime セットする aryLblLateTime
	 */
	public void setAryLblLateTime(String[] aryLblLateTime) {
		this.aryLblLateTime = getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLeaveEarlyTime
	 */
	public String getAryLblLeaveEarlyTime(int idx) {
		return aryLblLeaveEarlyTime[idx];
	}
	
	/**
	 * @param aryLblLeaveEarlyTime セットする aryLblLeaveEarlyTime
	 */
	public void setAryLblLeaveEarlyTime(String[] aryLblLeaveEarlyTime) {
		this.aryLblLeaveEarlyTime = getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateLeaveEarlyTime
	 */
	public String getAryLblLateLeaveEarlyTime(int idx) {
		return aryLblLateLeaveEarlyTime[idx];
	}
	
	/**
	 * @param aryLblLateLeaveEarlyTime セットする aryLblLateLeaveEarlyTime
	 */
	public void setAryLblLateLeaveEarlyTime(String[] aryLblLateLeaveEarlyTime) {
		this.aryLblLateLeaveEarlyTime = getStringArrayClone(aryLblLateLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeIn
	 */
	public String getAryLblOverTimeIn(int idx) {
		return aryLblOverTimeIn[idx];
	}
	
	/**
	 * @param aryLblOverTimeIn セットする aryLblOverTimeIn
	 */
	public void setAryLblOverTimeIn(String[] aryLblOverTimeIn) {
		this.aryLblOverTimeIn = getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryOvertimeStyle
	 */
	public String getAryOvertimeStyle(int idx) {
		return aryOvertimeStyle[idx];
	}
	
	/**
	 * @param aryOvertimeStyle セットする aryOvertimeStyle
	 */
	public void setAryOvertimeStyle(String[] aryOvertimeStyle) {
		this.aryOvertimeStyle = getStringArrayClone(aryOvertimeStyle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeOut
	 */
	public String getAryLblOverTimeOut(int idx) {
		return aryLblOverTimeOut[idx];
	}
	
	/**
	 * @param aryLblOverTimeOut セットする aryLblOverTimeOut
	 */
	public void setAryLblOverTimeOut(String[] aryLblOverTimeOut) {
		this.aryLblOverTimeOut = getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkOnHoliday
	 */
	public String getAryLblWorkOnHoliday(int idx) {
		return aryLblWorkOnHoliday[idx];
	}
	
	/**
	 * @param aryLblWorkOnHoliday セットする aryLblWorkOnHoliday
	 */
	public void setAryLblWorkOnHoliday(String[] aryLblWorkOnHoliday) {
		this.aryLblWorkOnHoliday = getStringArrayClone(aryLblWorkOnHoliday);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateNight
	 */
	public String getAryLblLateNight(int idx) {
		return aryLblLateNight[idx];
	}
	
	/**
	 * @param aryLblLateNight セットする aryLblLateNight
	 */
	public void setAryLblLateNight(String[] aryLblLateNight) {
		this.aryLblLateNight = getStringArrayClone(aryLblLateNight);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblState
	 */
	public String getAryLblState(int idx) {
		return aryLblState[idx];
	}
	
	/**
	 * 
	 * @param idx インデックス 
	 * @return aryLblShortUnpaid。
	 */
	public String getAryLblShortUnpaid(int idx) {
		return aryLblShortUnpaid[idx];
	}
	
	/**
	 * @param aryLblShortUnpaid セットする aryLblShortUnpaid。
	 */
	public void setAryLblShortUnpaid(String[] aryLblShortUnpaid) {
		this.aryLblShortUnpaid = getStringArrayClone(aryLblShortUnpaid);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCat
	 */
	public String getAryLblCat(int idx) {
		return aryLblCat[idx];
	}
	
	/**
	 * @param aryLblCat セットする aryLblCat
	 */
	public void setAryLblCat(String[] aryLblCat) {
		this.aryLblCat = getStringArrayClone(aryLblCat);
	}
	
	/**
	 * @param aryLblState セットする aryLblState
	 */
	public void setAryLblState(String[] aryLblState) {
		this.aryLblState = getStringArrayClone(aryLblState);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryStateStyle
	 */
	public String getAryStateStyle(int idx) {
		return aryStateStyle[idx];
	}
	
	/**
	 * @param aryStateStyle セットする aryStateStyle
	 */
	public void setAryStateStyle(String[] aryStateStyle) {
		this.aryStateStyle = getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLinkState
	 */
	public boolean getAryLinkState(int idx) {
		return aryLinkState[idx];
	}
	
	/**
	 * @param aryLinkState セットする aryLinkState
	 */
	public void setAryLinkState(boolean[] aryLinkState) {
		this.aryLinkState = getBooleanArrayClone(aryLinkState);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryAttendanceRecordId
	 */
	public long getAryAttendanceRecordId(int idx) {
		return aryAttendanceRecordId[idx];
	}
	
	/**
	 * @param aryAttendanceRecordId セットする aryAttendanceRecordId
	 */
	public void setAryAttendanceRecordId(long[] aryAttendanceRecordId) {
		this.aryAttendanceRecordId = getLongArrayClone(aryAttendanceRecordId);
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
	 * @return aryWorkflowRecordId
	 */
	public long getAryWorkflowRecordId(int idx) {
		return aryWorkflowRecordId[idx];
	}
	
	/**
	 * @param aryWorkflowRecordId セットする aryWorkflowRecordId
	 */
	public void setAryWorkflowRecordId(long[] aryWorkflowRecordId) {
		this.aryWorkflowRecordId = getLongArrayClone(aryWorkflowRecordId);
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(long[] aryWorkflow) {
		this.aryWorkflow = getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflow
	 */
	public long getAryWorkflow(int idx) {
		return aryWorkflow[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflowStatus
	 */
	public String getAryWorkflowStatus(int idx) {
		return aryWorkflowStatus[idx];
	}
	
	/**
	 * @param aryWorkflowStatus セットする aryWorkflowStatus
	 */
	public void setAryWorkflowStatus(String[] aryWorkflowStatus) {
		this.aryWorkflowStatus = getStringArrayClone(aryWorkflowStatus);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflowStage
	 */
	public int getAryWorkflowStage(int idx) {
		return aryWorkflowStage[idx];
	}
	
	/**
	 * @param aryWorkflowStage セットする aryWorkflowStage
	 */
	public void setAryWorkflowStage(int[] aryWorkflowStage) {
		this.aryWorkflowStage = getIntArrayClone(aryWorkflowStage);
	}
	
	/**
	 * @param aryLblCorrection セットする aryLblCorrection
	 */
	public void setAryLblCorrection(String[] aryLblCorrection) {
		this.aryLblCorrection = getStringArrayClone(aryLblCorrection);
	}
	
	/**
	 * @return lblTotalPrivate
	 */
	public String getLblTotalPrivate() {
		return lblTotalPrivate;
	}
	
	/**
	 * @param lblTotalPrivate セットする lblTotalPrivate
	 */
	public void setLblTotalPrivate(String lblTotalPrivate) {
		this.lblTotalPrivate = lblTotalPrivate;
	}
	
	/**
	 * @return lblTotalLate
	 */
	public String getLblTotalLate() {
		return lblTotalLate;
	}
	
	/**
	 * @param lblTotalLate セットする lblTotalLate
	 */
	public void setLblTotalLate(String lblTotalLate) {
		this.lblTotalLate = lblTotalLate;
	}
	
	/**
	 * @return lblTotalLeaveEarly
	 */
	public String getLblTotalLeaveEarly() {
		return lblTotalLeaveEarly;
	}
	
	/**
	 * @param lblTotalLeaveEarly セットする lblTotalLeaveEarly
	 */
	public void setLblTotalLeaveEarly(String lblTotalLeaveEarly) {
		this.lblTotalLeaveEarly = lblTotalLeaveEarly;
	}
	
	/**
	 * @return lblTotalLateLeaveEarly
	 */
	public String getLblTotalLateLeaveEarly() {
		return lblTotalLateLeaveEarly;
	}
	
	/**
	 * @param lblTotalLateLeaveEarly セットする lblTotalLateLeaveEarly
	 */
	public void setLblTotalLateLeaveEarly(String lblTotalLateLeaveEarly) {
		this.lblTotalLateLeaveEarly = lblTotalLateLeaveEarly;
	}
	
	/**
	 * @return lblTotalOverTimeIn
	 */
	public String getLblTotalOverTimeIn() {
		return lblTotalOverTimeIn;
	}
	
	/**
	 * @param lblTotalOverTimeIn セットする lblTotalOverTimeIn
	 */
	public void setLblTotalOverTimeIn(String lblTotalOverTimeIn) {
		this.lblTotalOverTimeIn = lblTotalOverTimeIn;
	}
	
	/**
	 * @return lblTotalOverTimeOut
	 */
	public String getLblTotalOverTimeOut() {
		return lblTotalOverTimeOut;
	}
	
	/**
	 * @param lblTotalOverTimeOut セットする lblTotalOverTimeOut
	 */
	public void setLblTotalOverTimeOut(String lblTotalOverTimeOut) {
		this.lblTotalOverTimeOut = lblTotalOverTimeOut;
	}
	
	/**
	 * @return lblTotalWorkOnHoliday
	 */
	public String getLblTotalWorkOnHoliday() {
		return lblTotalWorkOnHoliday;
	}
	
	/**
	 * @param lblTotalWorkOnHoliday セットする lblTotalWorkOnHoliday
	 */
	public void setLblTotalWorkOnHoliday(String lblTotalWorkOnHoliday) {
		this.lblTotalWorkOnHoliday = lblTotalWorkOnHoliday;
	}
	
	/**
	 * @return lblTotalLateNight
	 */
	public String getLblTotalLateNight() {
		return lblTotalLateNight;
	}
	
	/**
	 * @param lblTotalLateNight セットする lblTotalLateNight
	 */
	public void setLblTotalLateNight(String lblTotalLateNight) {
		this.lblTotalLateNight = lblTotalLateNight;
	}
	
	/**
	 * @return lblTimesLate
	 */
	public String getLblTimesLate() {
		return lblTimesLate;
	}
	
	/**
	 * @param lblTimesLate セットする lblTimesLate
	 */
	public void setLblTimesLate(String lblTimesLate) {
		this.lblTimesLate = lblTimesLate;
	}
	
	/**
	 * @return lblTimesLeaveEarly
	 */
	public String getLblTimesLeaveEarly() {
		return lblTimesLeaveEarly;
	}
	
	/**
	 * @param lblTimesLeaveEarly セットする lblTimesLeaveEarly
	 */
	public void setLblTimesLeaveEarly(String lblTimesLeaveEarly) {
		this.lblTimesLeaveEarly = lblTimesLeaveEarly;
	}
	
	/**
	 * @return lblTimesWorkOnHoliday
	 */
	public String getLblTimesWorkOnHoliday() {
		return lblTimesWorkOnHoliday;
	}
	
	/**
	 * @param lblTimesWorkOnHoliday セットする lblTimesWorkOnHoliday
	 */
	public void setLblTimesWorkOnHoliday(String lblTimesWorkOnHoliday) {
		this.lblTimesWorkOnHoliday = lblTimesWorkOnHoliday;
	}
	
	/**
	 * @return lblTimesBirthPrescribedSubHolidayday
	 */
	public String getLblTimesBirthPrescribedSubHolidayday() {
		return lblTimesBirthPrescribedSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthPrescribedSubHolidayday セットする lblTimesBirthPrescribedSubHolidayday
	 */
	public void setLblTimesBirthPrescribedSubHolidayday(String lblTimesBirthPrescribedSubHolidayday) {
		this.lblTimesBirthPrescribedSubHolidayday = lblTimesBirthPrescribedSubHolidayday;
	}
	
	/**
	 * @return lblTimesBirthLegalSubHolidayday
	 */
	public String getLblTimesBirthLegalSubHolidayday() {
		return lblTimesBirthLegalSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthLegalSubHolidayday セットする lblTimesBirthLegalSubHolidayday
	 */
	public void setLblTimesBirthLegalSubHolidayday(String lblTimesBirthLegalSubHolidayday) {
		this.lblTimesBirthLegalSubHolidayday = lblTimesBirthLegalSubHolidayday;
	}
	
	/**
	 * @return lblTimesBirthMidnightSubHolidayday
	 */
	public String getLblTimesBirthMidnightSubHolidayday() {
		return lblTimesBirthMidnightSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthMidnightSubHolidayday セットする lblTimesBirthMidnightSubHolidayday
	 */
	public void setLblTimesBirthMidnightSubHolidayday(String lblTimesBirthMidnightSubHolidayday) {
		this.lblTimesBirthMidnightSubHolidayday = lblTimesBirthMidnightSubHolidayday;
	}
	
	/**
	 * @return lblTimesHoliday
	 */
	@Override
	public String getLblTimesHoliday() {
		return lblTimesHoliday;
	}
	
	/**
	 * @param lblTimesHoliday セットする lblTimesHoliday
	 */
	@Override
	public void setLblTimesHoliday(String lblTimesHoliday) {
		this.lblTimesHoliday = lblTimesHoliday;
	}
	
	/**
	 * @return lblTimesPaidHoliday
	 */
	public String getLblTimesPaidHoliday() {
		return lblTimesPaidHoliday;
	}
	
	/**
	 * @param lblTimesPaidHoliday セットする lblTimesPaidHoliday
	 */
	public void setLblTimesPaidHoliday(String lblTimesPaidHoliday) {
		this.lblTimesPaidHoliday = lblTimesPaidHoliday;
	}
	
	/**
	 * @return lblTimesSpecialHoloiday
	 */
	public String getLblTimesSpecialHoloiday() {
		return lblTimesSpecialHoloiday;
	}
	
	/**
	 * @param lblTimesSpecialHoloiday セットする lblTimesSpecialHoloiday
	 */
	public void setLblTimesSpecialHoloiday(String lblTimesSpecialHoloiday) {
		this.lblTimesSpecialHoloiday = lblTimesSpecialHoloiday;
	}
	
	/**
	 * @return lblTimesSubstitute
	 */
	public String getLblTimesSubstitute() {
		return lblTimesSubstitute;
	}
	
	/**
	 * @param lblTimesSubstitute セットする lblTimesSubstitute
	 */
	public void setLblTimesSubstitute(String lblTimesSubstitute) {
		this.lblTimesSubstitute = lblTimesSubstitute;
	}
	
	/**
	 * @return lblTimesSubHoliday
	 */
	public String getLblTimesSubHoliday() {
		return lblTimesSubHoliday;
	}
	
	/**
	 * @param lblTimesSubHoliday セットする lblTimesSubHoliday
	 */
	public void setLblTimesSubHoliday(String lblTimesSubHoliday) {
		this.lblTimesSubHoliday = lblTimesSubHoliday;
	}
	
	/**
	 * @return lblTimesAbsence
	 */
	public String getLblTimesAbsence() {
		return lblTimesAbsence;
	}
	
	/**
	 * @param lblTimesAbsence セットする lblTimesAbsence
	 */
	public void setLblTimesAbsence(String lblTimesAbsence) {
		this.lblTimesAbsence = lblTimesAbsence;
	}
	
	/**
	 * @return lblShortUnpaidTotal
	 */
	public String getLblShortUnpaidTotal() {
		return lblShortUnpaidTotal;
	}
	
	/**
	 * @param lblShortUnpaidTotal セットする lblShortUnpaidTotal
	 */
	public void setLblShortUnpaidTotal(String lblShortUnpaidTotal) {
		this.lblShortUnpaidTotal = lblShortUnpaidTotal;
	}
	
	/**
	 * @return lblTimesMinutelyHolidayA
	 */
	public String getLblTimesMinutelyHolidayA() {
		return lblTimesMinutelyHolidayA;
	}
	
	/**
	 * @param lblTimesMinutelyHolidayA セットする lblTimesMinutelyHolidayA
	 */
	public void setLblTimesMinutelyHolidayA(String lblTimesMinutelyHolidayA) {
		this.lblTimesMinutelyHolidayA = lblTimesMinutelyHolidayA;
	}
	
	/**
	 * @return lblTimesMinutelyHolidayB
	 */
	public String getLblTimesMinutelyHolidayB() {
		return lblTimesMinutelyHolidayB;
	}
	
	/**
	 * @param lblTimesMinutelyHolidayB セットする lblTimesMinutelyHolidayB
	 */
	public void setLblTimesMinutelyHolidayB(String lblTimesMinutelyHolidayB) {
		this.lblTimesMinutelyHolidayB = lblTimesMinutelyHolidayB;
	}
	
	/**
	 * @return lblTimesOverTimeWork
	 */
	public String getLblTimesOverTimeWork() {
		return lblTimesOverTimeWork;
	}
	
	/**
	 * @return lblTimesPaidHolidayTime
	 */
	public String getLblTimesPaidHolidayTime() {
		return lblTimesPaidHolidayTime;
	}
	
	/**
	 * @return lblTimesOtherHoloiday
	 */
	public String getLblTimesOtherHoloiday() {
		return lblTimesOtherHoloiday;
	}
	
	/**
	 * @param lblTimesOverTimeWork セットする lblTimesOverTimeWork
	 */
	public void setLblTimesOverTimeWork(String lblTimesOverTimeWork) {
		this.lblTimesOverTimeWork = lblTimesOverTimeWork;
	}
	
	/**
	 * @param lblTimesPaidHolidayTime セットする lblTimesPaidHolidayTime
	 */
	public void setLblTimesPaidHolidayTime(String lblTimesPaidHolidayTime) {
		this.lblTimesPaidHolidayTime = lblTimesPaidHolidayTime;
	}
	
	/**
	 * @param lblTimesOtherHoloiday セットする lblTimesOtherHoloiday
	 */
	public void setLblTimesOtherHoloiday(String lblTimesOtherHoloiday) {
		this.lblTimesOtherHoloiday = lblTimesOtherHoloiday;
	}
	
	/**
	 * @return aryDate
	 */
	public String[] getAryDate() {
		return getStringArrayClone(aryDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryDate
	 */
	public String getAryDate(int idx) {
		return aryDate[idx];
	}
	
	/**
	 * @param aryDate セットする aryDate
	 */
	public void setAryDate(String[] aryDate) {
		this.aryDate = getStringArrayClone(aryDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryState
	 */
	public boolean getAryCheckState(int idx) {
		return aryCheckState[idx];
	}
	
	/**
	 * @param aryCheckState セットする aryCheckState
	 */
	public void setAryCheckState(boolean[] aryCheckState) {
		this.aryCheckState = getBooleanArrayClone(aryCheckState);
	}
	
	/**
	 * @return isTotalButtonVisible
	 */
	public boolean isTotalButtonVisible() {
		return isTotalButtonVisible;
	}
	
	/**
	 * @param isTotalButtonVisible セットする isTotalButtonVisible
	 */
	public void setTotalButtonVisible(boolean isTotalButtonVisible) {
		this.isTotalButtonVisible = isTotalButtonVisible;
	}
	
	/**
	 * @return isLblTimesMinutelyHolidayA
	 */
	public boolean isLblTimesMinutelyHolidayA() {
		return isLblTimesMinutelyHolidayA;
	}
	
	/**
	 * @param isLblTimesMinutelyHolidayA セットする isLblTimesMinutelyHolidayA
	 */
	public void setLblTimesMinutelyHolidayA(boolean isLblTimesMinutelyHolidayA) {
		this.isLblTimesMinutelyHolidayA = isLblTimesMinutelyHolidayA;
	}
	
	/**
	 * @return isLblTimesMinutelyHolidayB
	 */
	public boolean isLblTimesMinutelyHolidayB() {
		return isLblTimesMinutelyHolidayB;
	}
	
	/**
	 * @param isLblTimesMinutelyHolidayB セットする isLblTimesMinutelyHolidayB
	 */
	public void setLblTimesMinutelyHolidayB(boolean isLblTimesMinutelyHolidayB) {
		this.isLblTimesMinutelyHolidayB = isLblTimesMinutelyHolidayB;
	}
	
	/**
	 * @return isLblStartRecordTime
	 */
	public boolean isLblStartRecordTime() {
		return isLblStartRecordTime;
	}
	
	/**
	 * @param isLblStartRecordTime セットする isLblStartRecordTime
	 */
	public void setLblStartRecordTime(boolean isLblStartRecordTime) {
		this.isLblStartRecordTime = isLblStartRecordTime;
	}
	
	/**
	 * @return isLblEndRecordTime
	 */
	public boolean isLblEndRecordTime() {
		return isLblEndRecordTime;
	}
	
	/**
	 * @param isLblEndRecordTime セットする isLblEndRecordTime
	 */
	public void setLblEndRecordTime(boolean isLblEndRecordTime) {
		this.isLblEndRecordTime = isLblEndRecordTime;
	}
	
	/**
	 * @return isLblTimesCat
	 */
	public boolean isLblTimesCat() {
		return isLblTimesCat;
	}
	
	/**
	 * @param isLblTimesCat セットする isLblTimesCat
	 */
	public void setLblTimesCat(boolean isLblTimesCat) {
		this.isLblTimesCat = isLblTimesCat;
	}
	
	/**
	 * @return lblExtraFiled
	 */
	public String getLblExtraFiled() {
		return lblExtraFiled;
	}
	
	/**
	 * @param lblExtraFiled セットする lblExtraFiled
	 */
	public void setLblExtraFiled(String lblExtraFiled) {
		this.lblExtraFiled = lblExtraFiled;
	}
	
}
