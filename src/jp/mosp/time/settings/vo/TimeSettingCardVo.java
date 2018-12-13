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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤怠設定情報の情報を格納する。
 */
public class TimeSettingCardVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -300140290571146843L;
	
	private String				txtSettingCode;							// 勤怠設定コード
	private String				txtSettingName;							// 勤怠設定名称
	private String				txtSettingAbbr;							// 勤怠設定略称
	private String				pltCutoffDate;								// 締日
																			
	private String				pltTimeManagement;							// 勤怠管理対象
	private String				pltDailyApproval;							// 日々申請対象
	private String				pltBeforeOverTime;							// 勤務前残業
	private String				pltSpecificHoliday;						// 所定休日取扱
	private String				pltStartWeek;								// 週の起算曜日
	private String				pltStartMonth;								// 月の起算日
	private String				pltStartYear;								// 年の起算月
	private String				txtGeneralWorkTimeHour;					// 所定労働時間（時間）
	private String				txtGeneralWorkTimeMinute;					// 所定労働時間（分）
	private String				txtStartDayHour;							// 一日の起算時（時間）
	private String				txtStartDayMinute;							// 一日の起算時（分）
	private String				txtLateEarlyFullHour;						// 遅刻早退限度時間（全日）
	private String				txtLateEarlyFullMinute;					// 遅刻早退限度分（全日）
	private String				txtLateEarlyHalfHour;						// 遅刻早退限度時間（半日）
	private String				txtLateEarlyHalfMinute;					// 遅刻早退限度分（半日）
	private String				txtTransferAheadLimitMonth;				// 振休取得期限月(休出前)
	private String				txtTransferAheadLimitDate;					// 振休取得期限日(休出前)
	private String				txtTransferLaterLimitMonth;				// 振休取得期限月(休出後)
	private String				txtTransferLaterLimitDate;					// 振休取得期限日(休出後)
	private String				txtSubHolidayLimitMonth;					// 代休取得期限
	private String				txtSubHolidayLimitDate;					// 代休取得期限
	private String				pltTransferExchange;						// 半休入替取得（振休）
	private String				pltSubHolidayExchange;						// 半休入替取得（代休）
	private String				txtSubHolidayAllNormHour;					// 代休基準時間(全休)
	private String				txtSubHolidayAllNormMinute;				// 代休基準分(全休)
	private String				txtSubHolidayHalfNormHour;					// 代休基準時間(半休)
	private String				txtSubHolidayHalfNormMinute;				// 代休基準分(半休)
																			
	/**
	 * ポータル出退勤ボタン表示。
	 */
	private String				pltPortalTimeButtons;
	
	/**
	 * ポータル休憩ボタン表示。
	 */
	private String				pltPortalRestButtons;
	
	/**
	 * 勤務予定時間表示。
	 */
	private String				pltUseScheduledTime;
	
	private String				txtRoundDailyStart;						// 日出勤丸め単位
	private String				pltRoundDailyStart;						// 日出勤丸め
	private String				txtRoundDailyEnd;							// 日退勤丸め単位
	private String				pltRoundDailyEnd;							// 日退勤丸め
	private String				txtRoundDailyWork;							// 日勤務時間丸め単位
	private String				pltRoundDailyWork;							// 日勤務時間丸め
	private String				txtRoundDailyRestStart;					// 日休憩入丸め単位
	private String				pltRoundDailyRestStart;					// 日休憩入丸め
	private String				txtRoundDailyRestEnd;						// 日休憩戻丸め単位
	private String				pltRoundDailyRestEnd;						// 日休憩戻丸め
	private String				txtRoundDailyRestTime;						// 日休憩時間丸め単位
	private String				pltRoundDailyRestTime;						// 日休憩時間丸め
	private String				txtRoundDailyLate;							// 日遅刻丸め単位
	private String				pltRoundDailyLate;							// 日遅刻丸め
	private String				txtRoundDailyLeaveEarly;					// 日早退丸め単位
	private String				pltRoundDailyLeaveEaly;					// 日早退丸め
	private String				txtRoundDailyPrivateIn;					// 日私用外出入丸め単位
	private String				pltRoundDailyPrivateIn;					// 日私用外出入り丸め
	private String				txtRoundDailyPrivateOut;					// 日私用外出戻丸め単位
	private String				pltRoundDailyPrivateOut;					// 日私用外出戻り丸め
	private String				txtRoundDailyPublicIn;						// 日公用外出入丸め単位
	private String				pltRoundDailyPublicIn;						// 日公用外出入り丸め
	private String				txtRoundDailyPublicOut;					// 日公用外出戻丸め単位
	private String				pltRoundDailyPublicOut;					// 日公用外出戻り丸め
	private String				txtRoundDailyDecreaseTime;					// 日減額対象丸め単位
	private String				pltRoundDailyDecreaseTime;					// 日減額対象時間丸め
	private String				txtRoundDailyShortUnpaid;					// 日無給時短時間丸め単位
	private String				pltRoundDailyShortUnpaid;					// 日無給時短時間丸め
																			
	private String				txtRoundMonthlyWork;						// 月勤務時間丸め単位
	private String				pltRoundMonthlyWork;						// 月勤務時間丸め
	private String				txtRoundMonthlyRest;						// 月休憩時間丸め単位
	private String				pltRoundMonthlyRest;						// 月休憩時間丸め
	private String				txtRoundMonthlyLate;						// 月遅刻丸め単位
	private String				pltRoundMonthlyLate;						// 月遅刻時間丸め
	private String				txtRoundMonthlyLeaveEarly;					// 月早退丸め単位
	private String				pltRoundMonthlyLeaveEarly;					// 月早退時間丸め
	private String				txtRoundMonthlyPrivate;					// 月私用外出丸め単位
	private String				pltRoundMonthlyPrivate;					// 月私用外出時間丸め
	private String				txtRoundMonthlyPublic;						// 月公用外出丸め単位
	private String				pltRoundMonthlyPublic;						// 月公用外出時間丸め
	private String				txtRoundMonthlyDecreaseTime;				// 月減額対象丸め単位
	private String				pltRoundMonthlyDecreaseTime;				// 月減額対象時間丸め
	private String				txtRoundMonthlyShortUnpaid;				// 月無給時短時間丸め単位
	private String				pltRoundMonthlyShortUnpaid;				// 月無給時短時間丸め
																			
	private String				txtLimit1WeekHour;							// 1週間限度時間（時間）
	private String				txtLimit1WeekMinute;						// 1週間限度時間（分）
	private String				txtLimit2WeekHour;							// 2週間限度時間（時間）
	private String				txtLimit2WeekMinute;						// 2週間限度時間（分）
	private String				txtLimit4WeekHour;							// 4週間限度時間（時間）
	private String				txtLimit4WeekMinute;						// 4週間限度時間（分）
	private String				txtLimit1MonthHour;						// 1ヶ月限度時間（時間）
	private String				txtLimit1MonthMinute;						// 1ヶ月限度時間（分）
	private String				txtLimit2MonthHour;						// 2ヶ月限度時間（時間）
	private String				txtLimit2MonthMinute;						// 2ヶ月限度時間（分）
	private String				txtLimit3MonthHour;						// 3ヶ月限度時間（時間）
	private String				txtLimit3MonthMinute;						// 3ヶ月限度時間（分）
	private String				txtLimit1YearHour;							// 1年間限度時間（時間）
	private String				txtLimit1YearMinute;						// 1年間限度時間（分）
	private String				txtAttention1WeekHour;						// 1週間注意時間（時間）
	private String				txtAttention1WeekMinute;					// 1週間注意時間（分）
	private String				txtAttention2WeekHour;						// 2週間注意時間（時間）
	private String				txtAttention2WeekMinute;					// 2週間注意時間（分）
	private String				txtAttention4WeekHour;						// 4週間注意時間（時間）
	private String				txtAttention4WeekMinute;					// 4週間注意時間（分）
	private String				txtAttention1MonthHour;					// 1ヶ月注意時間（時間）
	private String				txtAttention1MonthMinute;					// 1ヶ月注意時間（分）
	private String				txtAttention2MonthHour;					// 2ヶ月注意時間（時間）
	private String				txtAttention2MonthMinute;					// 2ヶ月注意時間（分）
	private String				txtAttention3MonthHour;					// 3ヶ月注意時間（時間）
	private String				txtAttention3MonthMinute;					// 3ヶ月注意時間（分）
	private String				txtAttention1YearHour;						// 1年間注意時間（時間）
	private String				txtAttention1YearMinute;					// 1年間注意時間（分）
	private String				txtWarning1WeekHour;						// 1週間警告時間（時間）
	private String				txtWarning1WeekMinute;						// 1週間警告時間（分）
	private String				txtWarning2WeekHour;						// 2週間警告時間（時間）
	private String				txtWarning2WeekMinute;						// 2週間警告時間（分）
	private String				txtWarning4WeekHour;						// 4週間警告時間（時間）
	private String				txtWarning4WeekMinute;						// 4週間警告時間（分）
	private String				txtWarning1MonthHour;						// 1ヶ月警告時間（時間）
	private String				txtWarning1MonthMinute;					// 1ヶ月警告時間（分）
	private String				txtWarning2MonthHour;						// 2ヶ月警告時間（時間）
	private String				txtWarning2MonthMinute;					// 2ヶ月警告時間（分）
	private String				txtWarning3MonthHour;						// 3ヶ月警告時間（時間）
	private String				txtWarning3MonthMinute;					// 3ヶ月警告時間（分）
	private String				txtWarning1YearHour;						// 1年間警告時間（時間）
	private String				txtWarning1YearMinute;						// 1年間警告時間（分）
																			
	private String				plt60HourFunction;							// 60時間超割増機能
	private String				plt60HourAlternative;						// 60時間超代替休暇
	private String				txtMonth60HourSurcharge;					// 月60時間超割増
	private String				txtWeekdayOver;							// 平日残業割増
	private String				txtWeekdayAlternative;						// 代替休暇平日
	private String				txtAltnativeCancel;						// 代替休暇放棄
	private String				txtAltnativeSpecific;						// 代替休暇所定休日
	private String				txtAltnativeLegal;							// 代替休暇法定休日
	private String				txtSpecificHoliday;						// 所定休日割増率
	private String				txtLegalHoliday;							// 法定休日割増率
																			
	private String[][]			aryPltCutoffDate;
	private String[][]			aryPltTimelyPaidHolidayTime;
	private String[][]			aryPltStartYear;
	private String[][]			aryPltRoundingItems;
	
	
	/**
	 * @return txtSettingCode
	 */
	public String getTxtSettingCode() {
		return txtSettingCode;
	}
	
	/**
	 * @param txtSettingCode セットする txtSettingCode
	 */
	public void setTxtSettingCode(String txtSettingCode) {
		this.txtSettingCode = txtSettingCode;
	}
	
	/**
	 * @return txtSettingName
	 */
	public String getTxtSettingName() {
		return txtSettingName;
	}
	
	/**
	 * @param txtSettingName セットする txtSettingName
	 */
	public void setTxtSettingName(String txtSettingName) {
		this.txtSettingName = txtSettingName;
	}
	
	/**
	 * @return txtSettingAbbr
	 */
	public String getTxtSettingAbbr() {
		return txtSettingAbbr;
	}
	
	/**
	 * @param txtSettingAbbr セットする txtSettingAbbr
	 */
	public void setTxtSettingAbbr(String txtSettingAbbr) {
		this.txtSettingAbbr = txtSettingAbbr;
	}
	
	/**
	 * @return pltCutoffDate
	 */
	public String getPltCutoffDate() {
		return pltCutoffDate;
	}
	
	/**
	 * @param pltCutoffDate セットする pltCutoffDate
	 */
	public void setPltCutoffDate(String pltCutoffDate) {
		this.pltCutoffDate = pltCutoffDate;
	}
	
	/**
	 * @return pltTimeManagement
	 */
	public String getPltTimeManagement() {
		return pltTimeManagement;
	}
	
	/**
	 * @param pltTimeManagement セットする pltTimeManagement
	 */
	public void setPltTimeManagement(String pltTimeManagement) {
		this.pltTimeManagement = pltTimeManagement;
	}
	
	/**
	 * @return pltDailyApproval
	 */
	public String getPltDailyApproval() {
		return pltDailyApproval;
	}
	
	/**
	 * @param pltDailyApproval セットする pltDailyApproval
	 */
	public void setPltDailyApproval(String pltDailyApproval) {
		this.pltDailyApproval = pltDailyApproval;
	}
	
	/**
	 * @return txtGeneralWorkTimeHour
	 */
	public String getTxtGeneralWorkTimeHour() {
		return txtGeneralWorkTimeHour;
	}
	
	/**
	 * @param txtGeneralWorkTimeHour セットする txtGeneralWorkTimeHour
	 */
	public void setTxtGeneralWorkTimeHour(String txtGeneralWorkTimeHour) {
		this.txtGeneralWorkTimeHour = txtGeneralWorkTimeHour;
	}
	
	/**
	 * @return txtGeneralWorkTimeMinute
	 */
	public String getTxtGeneralWorkTimeMinute() {
		return txtGeneralWorkTimeMinute;
	}
	
	/**
	 * @param txtGeneralWorkTimeMinute セットする txtGeneralWorkTimeMinute
	 */
	public void setTxtGeneralWorkTimeMinute(String txtGeneralWorkTimeMinute) {
		this.txtGeneralWorkTimeMinute = txtGeneralWorkTimeMinute;
	}
	
	/**
	 * @return txtStartDayHour
	 */
	public String getTxtStartDayHour() {
		return txtStartDayHour;
	}
	
	/**
	 * @param txtStartDayHour セットする txtStartDayHour
	 */
	public void setTxtStartDayHour(String txtStartDayHour) {
		this.txtStartDayHour = txtStartDayHour;
	}
	
	/**
	 * @return txtStartDayMinute
	 */
	public String getTxtStartDayMinute() {
		return txtStartDayMinute;
	}
	
	/**
	 * @param txtStartDayMinute セットする txtStartDayMinute
	 */
	public void setTxtStartDayMinute(String txtStartDayMinute) {
		this.txtStartDayMinute = txtStartDayMinute;
	}
	
	/**
	 * @return txtLateEarlyFullHour
	 */
	public String getTxtLateEarlyFullHour() {
		return txtLateEarlyFullHour;
	}
	
	/**
	 * @param txtLateEarlyFullHour セットする txtLateEarlyFullHour
	 */
	public void setTxtLateEarlyFullHour(String txtLateEarlyFullHour) {
		this.txtLateEarlyFullHour = txtLateEarlyFullHour;
	}
	
	/**
	 * @return txtLateEarlyFullMinute
	 */
	public String getTxtLateEarlyFullMinute() {
		return txtLateEarlyFullMinute;
	}
	
	/**
	 * @param txtLateEarlyFullMinute セットする txtLateEarlyFullMinute
	 */
	public void setTxtLateEarlyFullMinute(String txtLateEarlyFullMinute) {
		this.txtLateEarlyFullMinute = txtLateEarlyFullMinute;
	}
	
	/**
	 * @return txtLateEarlyHalfHour
	 */
	public String getTxtLateEarlyHalfHour() {
		return txtLateEarlyHalfHour;
	}
	
	/**
	 * @param txtLateEarlyHalfHour セットする txtLateEarlyHalfHour
	 */
	public void setTxtLateEarlyHalfHour(String txtLateEarlyHalfHour) {
		this.txtLateEarlyHalfHour = txtLateEarlyHalfHour;
	}
	
	/**
	 * @return txtLateEarlyHalfMinute
	 */
	public String getTxtLateEarlyHalfMinute() {
		return txtLateEarlyHalfMinute;
	}
	
	/**
	 * @param txtLateEarlyHalfMinute セットする txtLateEarlyHalfMinute
	 */
	public void setTxtLateEarlyHalfMinute(String txtLateEarlyHalfMinute) {
		this.txtLateEarlyHalfMinute = txtLateEarlyHalfMinute;
	}
	
	/**
	 * @return pltBeforeOverTime
	 */
	public String getPltBeforeOverTime() {
		return pltBeforeOverTime;
	}
	
	/**
	 * @param pltBeforeOverTime セットする pltBeforeOverTime
	 */
	public void setPltBeforeOverTime(String pltBeforeOverTime) {
		this.pltBeforeOverTime = pltBeforeOverTime;
	}
	
	/**
	 * @return pltSpecificHoliday
	 */
	public String getPltSpecificHoliday() {
		return pltSpecificHoliday;
	}
	
	/**
	 * @param pltSpecificHoliday セットする pltSpecificHoliday
	 */
	public void setPltSpecificHoliday(String pltSpecificHoliday) {
		this.pltSpecificHoliday = pltSpecificHoliday;
	}
	
	/**
	 * @return txtRoundDailyDecreaseTime
	 */
	public String getTxtRoundDailyDecreaseTime() {
		return txtRoundDailyDecreaseTime;
	}
	
	/**
	 * @param txtRoundDailyDecreaseTime セットする txtRoundDailyDecreaseTime
	 */
	public void setTxtRoundDailyDecreaseTime(String txtRoundDailyDecreaseTime) {
		this.txtRoundDailyDecreaseTime = txtRoundDailyDecreaseTime;
	}
	
	/**
	 * @return txtRoundDailyWork
	 */
	public String getTxtRoundDailyWork() {
		return txtRoundDailyWork;
	}
	
	/**
	 * @param txtRoundDailyWork セットする txtRoundDailyWork
	 */
	public void setTxtRoundDailyWork(String txtRoundDailyWork) {
		this.txtRoundDailyWork = txtRoundDailyWork;
	}
	
	/**
	 * @return txtRoundMonthlyWork
	 */
	public String getTxtRoundMonthlyWork() {
		return txtRoundMonthlyWork;
	}
	
	/**
	 * @param txtRoundMonthlyWork セットする txtRoundMonthlyWork
	 */
	public void setTxtRoundMonthlyWork(String txtRoundMonthlyWork) {
		this.txtRoundMonthlyWork = txtRoundMonthlyWork;
	}
	
	/**
	 * @return txtRoundMonthlyPrivate
	 */
	public String getTxtRoundMonthlyPrivate() {
		return txtRoundMonthlyPrivate;
	}
	
	/**
	 * @param txtRoundMonthlyPrivate セットする txtRoundMonthlyPrivate
	 */
	public void setTxtRoundMonthlyPrivate(String txtRoundMonthlyPrivate) {
		this.txtRoundMonthlyPrivate = txtRoundMonthlyPrivate;
	}
	
	/**
	 * @return txtRoundMonthlyPublic
	 */
	public String getTxtRoundMonthlyPublic() {
		return txtRoundMonthlyPublic;
	}
	
	/**
	 * @param txtRoundMonthlyPublic セットする txtRoundMonthlyPublic
	 */
	public void setTxtRoundMonthlyPublic(String txtRoundMonthlyPublic) {
		this.txtRoundMonthlyPublic = txtRoundMonthlyPublic;
	}
	
	/**
	 * @return pltRoundDailyStart
	 */
	public String getPltRoundDailyStart() {
		return pltRoundDailyStart;
	}
	
	/**
	 * @param pltRoundDailyStart セットする pltRoundDailyStart
	 */
	public void setPltRoundDailyStart(String pltRoundDailyStart) {
		this.pltRoundDailyStart = pltRoundDailyStart;
	}
	
	/**
	 * @return pltRoundDailyEnd
	 */
	public String getPltRoundDailyEnd() {
		return pltRoundDailyEnd;
	}
	
	/**
	 * @param pltRoundDailyEnd セットする pltRoundDailyEnd
	 */
	public void setPltRoundDailyEnd(String pltRoundDailyEnd) {
		this.pltRoundDailyEnd = pltRoundDailyEnd;
	}
	
	/**
	 * @return pltRoundDailyLate
	 */
	public String getPltRoundDailyLate() {
		return pltRoundDailyLate;
	}
	
	/**
	 * @param pltRoundDailyLate セットする pltRoundDailyLate
	 */
	public void setPltRoundDailyLate(String pltRoundDailyLate) {
		this.pltRoundDailyLate = pltRoundDailyLate;
	}
	
	/**
	 * @return pltRoundDailyPrivateIn
	 */
	public String getPltRoundDailyPrivateIn() {
		return pltRoundDailyPrivateIn;
	}
	
	/**
	 * @param pltRoundDailyPrivateIn セットする pltRoundDailyPrivateIn
	 */
	public void setPltRoundDailyPrivateIn(String pltRoundDailyPrivateIn) {
		this.pltRoundDailyPrivateIn = pltRoundDailyPrivateIn;
	}
	
	/**
	 * @return pltRoundDailyPrivateOut
	 */
	public String getPltRoundDailyPrivateOut() {
		return pltRoundDailyPrivateOut;
	}
	
	/**
	 * @param pltRoundDailyPrivateOut セットする pltRoundDailyPrivateOut
	 */
	public void setPltRoundDailyPrivateOut(String pltRoundDailyPrivateOut) {
		this.pltRoundDailyPrivateOut = pltRoundDailyPrivateOut;
	}
	
	/**
	 * @return pltRoundDailyPublicIn
	 */
	public String getPltRoundDailyPublicIn() {
		return pltRoundDailyPublicIn;
	}
	
	/**
	 * @param pltRoundDailyPublicIn セットする pltRoundDailyPublicIn
	 */
	public void setPltRoundDailyPublicIn(String pltRoundDailyPublicIn) {
		this.pltRoundDailyPublicIn = pltRoundDailyPublicIn;
	}
	
	/**
	 * @return pltRoundDailyPublicOut
	 */
	public String getPltRoundDailyPublicOut() {
		return pltRoundDailyPublicOut;
	}
	
	/**
	 * @param pltRoundDailyPublicOut セットする pltRoundDailyPublicOut
	 */
	public void setPltRoundDailyPublicOut(String pltRoundDailyPublicOut) {
		this.pltRoundDailyPublicOut = pltRoundDailyPublicOut;
	}
	
	/**
	 * @return pltRoundMonthlyWork
	 */
	public String getPltRoundMonthlyWork() {
		return pltRoundMonthlyWork;
	}
	
	/**
	 * @param pltRoundMonthlyWork セットする pltRoundMonthlyWork
	 */
	public void setPltRoundMonthlyWork(String pltRoundMonthlyWork) {
		this.pltRoundMonthlyWork = pltRoundMonthlyWork;
	}
	
	/**
	 * @return pltRoundMonthlyPrivate
	 */
	public String getPltRoundMonthlyPrivate() {
		return pltRoundMonthlyPrivate;
	}
	
	/**
	 * @param pltRoundMonthlyPrivate セットする pltRoundMonthlyPrivate
	 */
	public void setPltRoundMonthlyPrivate(String pltRoundMonthlyPrivate) {
		this.pltRoundMonthlyPrivate = pltRoundMonthlyPrivate;
	}
	
	/**
	 * @return pltRoundMonthlyPublic
	 */
	public String getPltRoundMonthlyPublic() {
		return pltRoundMonthlyPublic;
	}
	
	/**
	 * @param pltRoundMonthlyPublic セットする pltRoundMonthlyPublic
	 */
	public void setPltRoundMonthlyPublic(String pltRoundMonthlyPublic) {
		this.pltRoundMonthlyPublic = pltRoundMonthlyPublic;
	}
	
	/**
	 * @return txtLimit1WeekHour
	 */
	public String getTxtLimit1WeekHour() {
		return txtLimit1WeekHour;
	}
	
	/**
	 * @param txtLimit1WeekHour セットする txtLimit1WeekHour
	 */
	public void setTxtLimit1WeekHour(String txtLimit1WeekHour) {
		this.txtLimit1WeekHour = txtLimit1WeekHour;
	}
	
	/**
	 * @return txtLimit1WeekMinute
	 */
	public String getTxtLimit1WeekMinute() {
		return txtLimit1WeekMinute;
	}
	
	/**
	 * @param txtLimit1WeekMinute セットする txtLimit1WeekMinute
	 */
	public void setTxtLimit1WeekMinute(String txtLimit1WeekMinute) {
		this.txtLimit1WeekMinute = txtLimit1WeekMinute;
	}
	
	/**
	 * @return txtLimit2WeekHour
	 */
	public String getTxtLimit2WeekHour() {
		return txtLimit2WeekHour;
	}
	
	/**
	 * @param txtLimit2WeekHour セットする txtLimit2WeekHour
	 */
	public void setTxtLimit2WeekHour(String txtLimit2WeekHour) {
		this.txtLimit2WeekHour = txtLimit2WeekHour;
	}
	
	/**
	 * @return txtLimit2WeekMinute
	 */
	public String getTxtLimit2WeekMinute() {
		return txtLimit2WeekMinute;
	}
	
	/**
	 * @param txtLimit2WeekMinute セットする txtLimit2WeekMinute
	 */
	public void setTxtLimit2WeekMinute(String txtLimit2WeekMinute) {
		this.txtLimit2WeekMinute = txtLimit2WeekMinute;
	}
	
	/**
	 * @return txtLimit4WeekHour
	 */
	public String getTxtLimit4WeekHour() {
		return txtLimit4WeekHour;
	}
	
	/**
	 * @param txtLimit4WeekHour セットする txtLimit4WeekHour
	 */
	public void setTxtLimit4WeekHour(String txtLimit4WeekHour) {
		this.txtLimit4WeekHour = txtLimit4WeekHour;
	}
	
	/**
	 * @return txtLimit4WeekMinute
	 */
	public String getTxtLimit4WeekMinute() {
		return txtLimit4WeekMinute;
	}
	
	/**
	 * @param txtLimit4WeekMinute セットする txtLimit4WeekMinute
	 */
	public void setTxtLimit4WeekMinute(String txtLimit4WeekMinute) {
		this.txtLimit4WeekMinute = txtLimit4WeekMinute;
	}
	
	/**
	 * @return txtLimit1MonthHour
	 */
	public String getTxtLimit1MonthHour() {
		return txtLimit1MonthHour;
	}
	
	/**
	 * @param txtLimit1MonthHour セットする txtLimit1MonthHour
	 */
	public void setTxtLimit1MonthHour(String txtLimit1MonthHour) {
		this.txtLimit1MonthHour = txtLimit1MonthHour;
	}
	
	/**
	 * @return txtLimit1MonthMinute
	 */
	public String getTxtLimit1MonthMinute() {
		return txtLimit1MonthMinute;
	}
	
	/**
	 * @param txtLimit1MonthMinute セットする txtLimit1MonthMinute
	 */
	public void setTxtLimit1MonthMinute(String txtLimit1MonthMinute) {
		this.txtLimit1MonthMinute = txtLimit1MonthMinute;
	}
	
	/**
	 * @return txtLimit2MonthHour
	 */
	public String getTxtLimit2MonthHour() {
		return txtLimit2MonthHour;
	}
	
	/**
	 * @param txtLimit2MonthHour セットする txtLimit2MonthHour
	 */
	public void setTxtLimit2MonthHour(String txtLimit2MonthHour) {
		this.txtLimit2MonthHour = txtLimit2MonthHour;
	}
	
	/**
	 * @return txtLimit2MonthMinute
	 */
	public String getTxtLimit2MonthMinute() {
		return txtLimit2MonthMinute;
	}
	
	/**
	 * @param txtLimit2MonthMinute セットする txtLimit2MonthMinute
	 */
	public void setTxtLimit2MonthMinute(String txtLimit2MonthMinute) {
		this.txtLimit2MonthMinute = txtLimit2MonthMinute;
	}
	
	/**
	 * @return txtLimit3MonthHour
	 */
	public String getTxtLimit3MonthHour() {
		return txtLimit3MonthHour;
	}
	
	/**
	 * @param txtLimit3MonthHour セットする txtLimit3MonthHour
	 */
	public void setTxtLimit3MonthHour(String txtLimit3MonthHour) {
		this.txtLimit3MonthHour = txtLimit3MonthHour;
	}
	
	/**
	 * @return txtLimit3MonthMinute
	 */
	public String getTxtLimit3MonthMinute() {
		return txtLimit3MonthMinute;
	}
	
	/**
	 * @param txtLimit3MonthMinute セットする txtLimit3MonthMinute
	 */
	public void setTxtLimit3MonthMinute(String txtLimit3MonthMinute) {
		this.txtLimit3MonthMinute = txtLimit3MonthMinute;
	}
	
	/**
	 * @return txtLimit1YearHour
	 */
	public String getTxtLimit1YearHour() {
		return txtLimit1YearHour;
	}
	
	/**
	 * @param txtLimit1YearHour セットする txtLimit1YearHour
	 */
	public void setTxtLimit1YearHour(String txtLimit1YearHour) {
		this.txtLimit1YearHour = txtLimit1YearHour;
	}
	
	/**
	 * @return txtLimit1YearMinute
	 */
	public String getTxtLimit1YearMinute() {
		return txtLimit1YearMinute;
	}
	
	/**
	 * @param txtLimit1YearMinute セットする txtLimit1YearMinute
	 */
	public void setTxtLimit1YearMinute(String txtLimit1YearMinute) {
		this.txtLimit1YearMinute = txtLimit1YearMinute;
	}
	
	/**
	 * @return txtAttention1WeekHour
	 */
	public String getTxtAttention1WeekHour() {
		return txtAttention1WeekHour;
	}
	
	/**
	 * @param txtAttention1WeekHour セットする txtAttention1WeekHour
	 */
	public void setTxtAttention1WeekHour(String txtAttention1WeekHour) {
		this.txtAttention1WeekHour = txtAttention1WeekHour;
	}
	
	/**
	 * @return txtAttention1WeekMinute
	 */
	public String getTxtAttention1WeekMinute() {
		return txtAttention1WeekMinute;
	}
	
	/**
	 * @param txtAttention1WeekMinute セットする txtAttention1WeekMinute
	 */
	public void setTxtAttention1WeekMinute(String txtAttention1WeekMinute) {
		this.txtAttention1WeekMinute = txtAttention1WeekMinute;
	}
	
	/**
	 * @return txtAttention2WeekHour
	 */
	public String getTxtAttention2WeekHour() {
		return txtAttention2WeekHour;
	}
	
	/**
	 * @param txtAttention2WeekHour セットする txtAttention2WeekHour
	 */
	public void setTxtAttention2WeekHour(String txtAttention2WeekHour) {
		this.txtAttention2WeekHour = txtAttention2WeekHour;
	}
	
	/**
	 * @return txtAttention2WeekMinute
	 */
	public String getTxtAttention2WeekMinute() {
		return txtAttention2WeekMinute;
	}
	
	/**
	 * @param txtAttention2WeekMinute セットする txtAttention2WeekMinute
	 */
	public void setTxtAttention2WeekMinute(String txtAttention2WeekMinute) {
		this.txtAttention2WeekMinute = txtAttention2WeekMinute;
	}
	
	/**
	 * @return txtAttention4WeekHour
	 */
	public String getTxtAttention4WeekHour() {
		return txtAttention4WeekHour;
	}
	
	/**
	 * @param txtAttention4WeekHour セットする txtAttention4WeekHour
	 */
	public void setTxtAttention4WeekHour(String txtAttention4WeekHour) {
		this.txtAttention4WeekHour = txtAttention4WeekHour;
	}
	
	/**
	 * @return txtAttention4WeekMinute
	 */
	public String getTxtAttention4WeekMinute() {
		return txtAttention4WeekMinute;
	}
	
	/**
	 * @param txtAttention4WeekMinute セットする txtAttention4WeekMinute
	 */
	public void setTxtAttention4WeekMinute(String txtAttention4WeekMinute) {
		this.txtAttention4WeekMinute = txtAttention4WeekMinute;
	}
	
	/**
	 * @return txtAttention1MonthHour
	 */
	public String getTxtAttention1MonthHour() {
		return txtAttention1MonthHour;
	}
	
	/**
	 * @param txtAttention1MonthHour セットする txtAttention1MonthHour
	 */
	public void setTxtAttention1MonthHour(String txtAttention1MonthHour) {
		this.txtAttention1MonthHour = txtAttention1MonthHour;
	}
	
	/**
	 * @return txtAttention1MonthMinute
	 */
	public String getTxtAttention1MonthMinute() {
		return txtAttention1MonthMinute;
	}
	
	/**
	 * @param txtAttention1MonthMinute セットする txtAttention1MonthMinute
	 */
	public void setTxtAttention1MonthMinute(String txtAttention1MonthMinute) {
		this.txtAttention1MonthMinute = txtAttention1MonthMinute;
	}
	
	/**
	 * @return txtAttention2MonthHour
	 */
	public String getTxtAttention2MonthHour() {
		return txtAttention2MonthHour;
	}
	
	/**
	 * @param txtAttention2MonthHour セットする txtAttention2MonthHour
	 */
	public void setTxtAttention2MonthHour(String txtAttention2MonthHour) {
		this.txtAttention2MonthHour = txtAttention2MonthHour;
	}
	
	/**
	 * @return txtAttention2MonthMinute
	 */
	public String getTxtAttention2MonthMinute() {
		return txtAttention2MonthMinute;
	}
	
	/**
	 * @param txtAttention2MonthMinute セットする txtAttention2MonthMinute
	 */
	public void setTxtAttention2MonthMinute(String txtAttention2MonthMinute) {
		this.txtAttention2MonthMinute = txtAttention2MonthMinute;
	}
	
	/**
	 * @return txtAttention3MonthHour
	 */
	public String getTxtAttention3MonthHour() {
		return txtAttention3MonthHour;
	}
	
	/**
	 * @param txtAttention3MonthHour セットする txtAttention3MonthHour
	 */
	public void setTxtAttention3MonthHour(String txtAttention3MonthHour) {
		this.txtAttention3MonthHour = txtAttention3MonthHour;
	}
	
	/**
	 * @return txtAttention3MonthMinute
	 */
	public String getTxtAttention3MonthMinute() {
		return txtAttention3MonthMinute;
	}
	
	/**
	 * @param txtAttention3MonthMinute セットする txtAttention3MonthMinute
	 */
	public void setTxtAttention3MonthMinute(String txtAttention3MonthMinute) {
		this.txtAttention3MonthMinute = txtAttention3MonthMinute;
	}
	
	/**
	 * @return txtAttention1YearHour
	 */
	public String getTxtAttention1YearHour() {
		return txtAttention1YearHour;
	}
	
	/**
	 * @param txtAttention1YearHour セットする txtAttention1YearHour
	 */
	public void setTxtAttention1YearHour(String txtAttention1YearHour) {
		this.txtAttention1YearHour = txtAttention1YearHour;
	}
	
	/**
	 * @return txtAttention1YearMinute
	 */
	public String getTxtAttention1YearMinute() {
		return txtAttention1YearMinute;
	}
	
	/**
	 * @param txtAttention1YearMinute セットする txtAttention1YearMinute
	 */
	public void setTxtAttention1YearMinute(String txtAttention1YearMinute) {
		this.txtAttention1YearMinute = txtAttention1YearMinute;
	}
	
	/**
	 * @return txtWarning1WeekHour
	 */
	public String getTxtWarning1WeekHour() {
		return txtWarning1WeekHour;
	}
	
	/**
	 * @param txtWarning1WeekHour セットする txtWarning1WeekHour
	 */
	public void setTxtWarning1WeekHour(String txtWarning1WeekHour) {
		this.txtWarning1WeekHour = txtWarning1WeekHour;
	}
	
	/**
	 * @return txtWarning1WeekMinute
	 */
	public String getTxtWarning1WeekMinute() {
		return txtWarning1WeekMinute;
	}
	
	/**
	 * @param txtWarning1WeekMinute セットする txtWarning1WeekMinute
	 */
	public void setTxtWarning1WeekMinute(String txtWarning1WeekMinute) {
		this.txtWarning1WeekMinute = txtWarning1WeekMinute;
	}
	
	/**
	 * @return txtWarning2WeekHour
	 */
	public String getTxtWarning2WeekHour() {
		return txtWarning2WeekHour;
	}
	
	/**
	 * @param txtWarning2WeekHour セットする txtWarning2WeekHour
	 */
	public void setTxtWarning2WeekHour(String txtWarning2WeekHour) {
		this.txtWarning2WeekHour = txtWarning2WeekHour;
	}
	
	/**
	 * @return txtWarning2WeekMinute
	 */
	public String getTxtWarning2WeekMinute() {
		return txtWarning2WeekMinute;
	}
	
	/**
	 * @param txtWarning2WeekMinute セットする txtWarning2WeekMinute
	 */
	public void setTxtWarning2WeekMinute(String txtWarning2WeekMinute) {
		this.txtWarning2WeekMinute = txtWarning2WeekMinute;
	}
	
	/**
	 * @return txtWarning4WeekHour
	 */
	public String getTxtWarning4WeekHour() {
		return txtWarning4WeekHour;
	}
	
	/**
	 * @param txtWarning4WeekHour セットする txtWarning4WeekHour
	 */
	public void setTxtWarning4WeekHour(String txtWarning4WeekHour) {
		this.txtWarning4WeekHour = txtWarning4WeekHour;
	}
	
	/**
	 * @return txtWarning4WeekMinute
	 */
	public String getTxtWarning4WeekMinute() {
		return txtWarning4WeekMinute;
	}
	
	/**
	 * @param txtWarning4WeekMinute セットする txtWarning4WeekMinute
	 */
	public void setTxtWarning4WeekMinute(String txtWarning4WeekMinute) {
		this.txtWarning4WeekMinute = txtWarning4WeekMinute;
	}
	
	/**
	 * @return txtWarning1MonthHour
	 */
	public String getTxtWarning1MonthHour() {
		return txtWarning1MonthHour;
	}
	
	/**
	 * @param txtWarning1MonthHour セットする txtWarning1MonthHour
	 */
	public void setTxtWarning1MonthHour(String txtWarning1MonthHour) {
		this.txtWarning1MonthHour = txtWarning1MonthHour;
	}
	
	/**
	 * @return txtWarning1MonthMinute
	 */
	public String getTxtWarning1MonthMinute() {
		return txtWarning1MonthMinute;
	}
	
	/**
	 * @param txtWarning1MonthMinute セットする txtWarning1MonthMinute
	 */
	public void setTxtWarning1MonthMinute(String txtWarning1MonthMinute) {
		this.txtWarning1MonthMinute = txtWarning1MonthMinute;
	}
	
	/**
	 * @return txtWarning2MonthHour
	 */
	public String getTxtWarning2MonthHour() {
		return txtWarning2MonthHour;
	}
	
	/**
	 * @param txtWarning2MonthHour セットする txtWarning2MonthHour
	 */
	public void setTxtWarning2MonthHour(String txtWarning2MonthHour) {
		this.txtWarning2MonthHour = txtWarning2MonthHour;
	}
	
	/**
	 * @return txtWarning2MonthMinute
	 */
	public String getTxtWarning2MonthMinute() {
		return txtWarning2MonthMinute;
	}
	
	/**
	 * @param txtWarning2MonthMinute セットする txtWarning2MonthMinute
	 */
	public void setTxtWarning2MonthMinute(String txtWarning2MonthMinute) {
		this.txtWarning2MonthMinute = txtWarning2MonthMinute;
	}
	
	/**
	 * @return txtWarning3MonthHour
	 */
	public String getTxtWarning3MonthHour() {
		return txtWarning3MonthHour;
	}
	
	/**
	 * @param txtWarning3MonthHour セットする txtWarning3MonthHour
	 */
	public void setTxtWarning3MonthHour(String txtWarning3MonthHour) {
		this.txtWarning3MonthHour = txtWarning3MonthHour;
	}
	
	/**
	 * @return txtWarning3MonthMinute
	 */
	public String getTxtWarning3MonthMinute() {
		return txtWarning3MonthMinute;
	}
	
	/**
	 * @param txtWarning3MonthMinute セットする txtWarning3MonthMinute
	 */
	public void setTxtWarning3MonthMinute(String txtWarning3MonthMinute) {
		this.txtWarning3MonthMinute = txtWarning3MonthMinute;
	}
	
	/**
	 * @return txtWarning1YearHour
	 */
	public String getTxtWarning1YearHour() {
		return txtWarning1YearHour;
	}
	
	/**
	 * @param txtWarning1YearHour セットする txtWarning1YearHour
	 */
	public void setTxtWarning1YearHour(String txtWarning1YearHour) {
		this.txtWarning1YearHour = txtWarning1YearHour;
	}
	
	/**
	 * @return txtWarning1YearMinute
	 */
	public String getTxtWarning1YearMinute() {
		return txtWarning1YearMinute;
	}
	
	/**
	 * @param txtWarning1YearMinute セットする txtWarning1YearMinute
	 */
	public void setTxtWarning1YearMinute(String txtWarning1YearMinute) {
		this.txtWarning1YearMinute = txtWarning1YearMinute;
	}
	
	/**
	 * @return pltStartWeek
	 */
	public String getPltStartWeek() {
		return pltStartWeek;
	}
	
	/**
	 * @param pltStartWeek セットする pltStartWeek
	 */
	public void setPltStartWeek(String pltStartWeek) {
		this.pltStartWeek = pltStartWeek;
	}
	
	/**
	 * @return pltRoundDailyWork
	 */
	public String getPltRoundDailyWork() {
		return pltRoundDailyWork;
	}
	
	/**
	 * @param pltRoundDailyWork セットする pltRoundDailyWork
	 */
	public void setPltRoundDailyWork(String pltRoundDailyWork) {
		this.pltRoundDailyWork = pltRoundDailyWork;
	}
	
	/**
	 * @return pltRoundDailyRestStart
	 */
	public String getPltRoundDailyRestStart() {
		return pltRoundDailyRestStart;
	}
	
	/**
	 * @param pltRoundDailyRestStart セットする pltRoundDailyRestStart
	 */
	public void setPltRoundDailyRestStart(String pltRoundDailyRestStart) {
		this.pltRoundDailyRestStart = pltRoundDailyRestStart;
	}
	
	/**
	 * @return pltRoundDailyRestEnd
	 */
	public String getPltRoundDailyRestEnd() {
		return pltRoundDailyRestEnd;
	}
	
	/**
	 * @param pltRoundDailyRestEnd セットする pltRoundDailyRestEnd
	 */
	public void setPltRoundDailyRestEnd(String pltRoundDailyRestEnd) {
		this.pltRoundDailyRestEnd = pltRoundDailyRestEnd;
	}
	
	/**
	 * @return txtRoundDailyRestTime
	 */
	public String getTxtRoundDailyRestTime() {
		return txtRoundDailyRestTime;
	}
	
	/**
	 * @param txtRoundDailyRestTime セットする txtRoundDailyRestTime
	 */
	public void setTxtRoundDailyRestTime(String txtRoundDailyRestTime) {
		this.txtRoundDailyRestTime = txtRoundDailyRestTime;
	}
	
	/**
	 * @return pltRoundDailyRestTime
	 */
	public String getPltRoundDailyRestTime() {
		return pltRoundDailyRestTime;
	}
	
	/**
	 * @param pltRoundDailyRestTime セットする pltRoundDailyRestTime
	 */
	public void setPltRoundDailyRestTime(String pltRoundDailyRestTime) {
		this.pltRoundDailyRestTime = pltRoundDailyRestTime;
	}
	
	/**
	 * @return pltRoundDailyDecreaseTime
	 */
	public String getPltRoundDailyDecreaseTime() {
		return pltRoundDailyDecreaseTime;
	}
	
	/**
	 * @param pltRoundDailyDecreaseTime セットする pltRoundDailyDecreaseTime
	 */
	public void setPltRoundDailyDecreaseTime(String pltRoundDailyDecreaseTime) {
		this.pltRoundDailyDecreaseTime = pltRoundDailyDecreaseTime;
	}
	
	/**
	 * @return txtRoundDailyShortUnpaid
	 */
	public String getTxtRoundDailyShortUnpaid() {
		return txtRoundDailyShortUnpaid;
	}
	
	/**
	 * @param txtRoundDailyShortUnpaid セットする txtRoundDailyShortUnpaid
	 */
	public void setTxtRoundDailyShortUnpaid(String txtRoundDailyShortUnpaid) {
		this.txtRoundDailyShortUnpaid = txtRoundDailyShortUnpaid;
	}
	
	/**
	 * @return pltRoundDailyShortUnpaid
	 */
	public String getPltRoundDailyShortUnpaid() {
		return pltRoundDailyShortUnpaid;
	}
	
	/**
	 * @param pltRoundDailyShortUnpaid セットする pltRoundDailyShortUnpaid
	 */
	public void setPltRoundDailyShortUnpaid(String pltRoundDailyShortUnpaid) {
		this.pltRoundDailyShortUnpaid = pltRoundDailyShortUnpaid;
	}
	
	/**
	 * @return txtRoundMonthlyRest
	 */
	public String getTxtRoundMonthlyRest() {
		return txtRoundMonthlyRest;
	}
	
	/**
	 * @param txtRoundMonthlyRest セットする txtRoundMonthlyRest
	 */
	public void setTxtRoundMonthlyRest(String txtRoundMonthlyRest) {
		this.txtRoundMonthlyRest = txtRoundMonthlyRest;
	}
	
	/**
	 * @return pltRoundMonthlyRest
	 */
	public String getPltRoundMonthlyRest() {
		return pltRoundMonthlyRest;
	}
	
	/**
	 * @param pltRoundMonthlyRest セットする pltRoundMonthlyRest
	 */
	public void setPltRoundMonthlyRest(String pltRoundMonthlyRest) {
		this.pltRoundMonthlyRest = pltRoundMonthlyRest;
	}
	
	/**
	 * @return txtRoundMonthlyDecreaseTime
	 */
	public String getTxtRoundMonthlyDecreaseTime() {
		return txtRoundMonthlyDecreaseTime;
	}
	
	/**
	 * @param txtRoundMonthlyDecreaseTime セットする txtRoundMonthlyDecreaseTime
	 */
	public void setTxtRoundMonthlyDecreaseTime(String txtRoundMonthlyDecreaseTime) {
		this.txtRoundMonthlyDecreaseTime = txtRoundMonthlyDecreaseTime;
	}
	
	/**
	 * @return pltRoundMonthlyDecreaseTime
	 */
	public String getPltRoundMonthlyDecreaseTime() {
		return pltRoundMonthlyDecreaseTime;
	}
	
	/**
	 * @param pltRoundMonthlyDecreaseTime セットする pltRoundMonthlyDecreaseTime
	 */
	public void setPltRoundMonthlyDecreaseTime(String pltRoundMonthlyDecreaseTime) {
		this.pltRoundMonthlyDecreaseTime = pltRoundMonthlyDecreaseTime;
	}
	
	/**
	 * @return txtRoundMonthlyShortUnpaid
	 */
	public String getTxtRoundMonthlyShortUnpaid() {
		return txtRoundMonthlyShortUnpaid;
	}
	
	/**
	 * @param txtRoundMonthlyShortUnpaid セットする txtRoundMonthlyShortUnpaid
	 */
	public void setTxtRoundMonthlyShortUnpaid(String txtRoundMonthlyShortUnpaid) {
		this.txtRoundMonthlyShortUnpaid = txtRoundMonthlyShortUnpaid;
	}
	
	/**
	 * @return pltRoundMonthlyShortUnpaid
	 */
	public String getPltRoundMonthlyShortUnpaid() {
		return pltRoundMonthlyShortUnpaid;
	}
	
	/**
	 * @param pltRoundMonthlyShortUnpaid セットする pltRoundMonthlyShortUnpaid
	 */
	public void setPltRoundMonthlyShortUnpaid(String pltRoundMonthlyShortUnpaid) {
		this.pltRoundMonthlyShortUnpaid = pltRoundMonthlyShortUnpaid;
	}
	
	/**
	 * @return pltStartMonth
	 */
	public String getPltStartMonth() {
		return pltStartMonth;
	}
	
	/**
	 * @param pltStartMonth セットする pltStartMonth
	 */
	public void setPltStartMonth(String pltStartMonth) {
		this.pltStartMonth = pltStartMonth;
	}
	
	/**
	 * @return pltStartYear
	 */
	public String getPltStartYear() {
		return pltStartYear;
	}
	
	/**
	 * @param pltStartYear セットする pltStartYear
	 */
	public void setPltStartYear(String pltStartYear) {
		this.pltStartYear = pltStartYear;
	}
	
	/**
	 * @return plt60HourFunction
	 */
	public String getPlt60HourFunction() {
		return plt60HourFunction;
	}
	
	/**
	 * @param plt60HourFunction セットする plt60HourFunction
	 */
	public void setPlt60HourFunction(String plt60HourFunction) {
		this.plt60HourFunction = plt60HourFunction;
	}
	
	/**
	 * @return plt60HourAlternative
	 */
	public String getPlt60HourAlternative() {
		return plt60HourAlternative;
	}
	
	/**
	 * @param plt60HourAlternative セットする plt60HourAlternative
	 */
	public void setPlt60HourAlternative(String plt60HourAlternative) {
		this.plt60HourAlternative = plt60HourAlternative;
	}
	
	/**
	 * @return txtMonth60HourSurcharge
	 */
	public String getTxtMonth60HourSurcharge() {
		return txtMonth60HourSurcharge;
	}
	
	/**
	 * @param txtMonth60HourSurcharge セットする txtMonth60HourSurcharge
	 */
	public void setTxtMonth60HourSurcharge(String txtMonth60HourSurcharge) {
		this.txtMonth60HourSurcharge = txtMonth60HourSurcharge;
	}
	
	/**
	 * @return txtWeekdayOver
	 */
	public String getTxtWeekdayOver() {
		return txtWeekdayOver;
	}
	
	/**
	 * @param txtWeekdayOver セットする txtWeekdayOver
	 */
	public void setTxtWeekdayOver(String txtWeekdayOver) {
		this.txtWeekdayOver = txtWeekdayOver;
	}
	
	/**
	 * @return txtWeekdayAlternative
	 */
	public String getTxtWeekdayAlternative() {
		return txtWeekdayAlternative;
	}
	
	/**
	 * @param txtWeekdayAlternative セットする txtWeekdayAlternative
	 */
	public void setTxtWeekdayAlternative(String txtWeekdayAlternative) {
		this.txtWeekdayAlternative = txtWeekdayAlternative;
	}
	
	/**
	 * @return txtAltnativeCancel
	 */
	public String getTxtAltnativeCancel() {
		return txtAltnativeCancel;
	}
	
	/**
	 * @param txtAltnativeCancel セットする txtAltnativeCancel
	 */
	public void setTxtAltnativeCancel(String txtAltnativeCancel) {
		this.txtAltnativeCancel = txtAltnativeCancel;
	}
	
	/**
	 * @return txtAltnativeSpecific
	 */
	public String getTxtAltnativeSpecific() {
		return txtAltnativeSpecific;
	}
	
	/**
	 * @param txtAltnativeSpecific セットする txtAltnativeSpecific
	 */
	public void setTxtAltnativeSpecific(String txtAltnativeSpecific) {
		this.txtAltnativeSpecific = txtAltnativeSpecific;
	}
	
	/**
	 * @return txtAltnativeLegal
	 */
	public String getTxtAltnativeLegal() {
		return txtAltnativeLegal;
	}
	
	/**
	 * @param txtAltnativeLegal セットする txtAltnativeLegal
	 */
	public void setTxtAltnativeLegal(String txtAltnativeLegal) {
		this.txtAltnativeLegal = txtAltnativeLegal;
	}
	
	/**
	 * @return txtSpecificHoliday
	 */
	public String getTxtSpecificHoliday() {
		return txtSpecificHoliday;
	}
	
	/**
	 * @param txtSpecificHoliday セットする txtSpecificHoliday
	 */
	public void setTxtSpecificHoliday(String txtSpecificHoliday) {
		this.txtSpecificHoliday = txtSpecificHoliday;
	}
	
	/**
	 * @return txtLegalHoliday
	 */
	public String getTxtLegalHoliday() {
		return txtLegalHoliday;
	}
	
	/**
	 * @param txtLegalHoliday セットする txtLegalHoliday
	 */
	public void setTxtLegalHoliday(String txtLegalHoliday) {
		this.txtLegalHoliday = txtLegalHoliday;
	}
	
	/**
	 * @return aryPltCutoffDate
	 */
	public String[][] getAryPltCutoffDate() {
		return getStringArrayClone(aryPltCutoffDate);
	}
	
	/**
	 * @param aryPltCutoffDate セットする aryPltCutoffDate
	 */
	public void setAryPltCutoffDate(String[][] aryPltCutoffDate) {
		this.aryPltCutoffDate = getStringArrayClone(aryPltCutoffDate);
	}
	
	/**
	 * @return aryPltTimelyPaidHolidayTime
	 */
	public String[][] getAryPltTimelyPaidHolidayTime() {
		return getStringArrayClone(aryPltTimelyPaidHolidayTime);
	}
	
	/**
	 * @param aryPltTimelyPaidHolidayTime セットする aryPltTimelyPaidHolidayTime
	 */
	public void setAryPltTimelyPaidHolidayTime(String[][] aryPltTimelyPaidHolidayTime) {
		this.aryPltTimelyPaidHolidayTime = getStringArrayClone(aryPltTimelyPaidHolidayTime);
	}
	
	/**
	 * @return aryPltStartYear
	 */
	public String[][] getAryPltStartYear() {
		return getStringArrayClone(aryPltStartYear);
	}
	
	/**
	 * @param aryPltStartYear セットする aryPltStartYear
	 */
	public void setAryPltStartYear(String[][] aryPltStartYear) {
		this.aryPltStartYear = getStringArrayClone(aryPltStartYear);
	}
	
	/**
	 * @return aryPltRoundingItems
	 */
	public String[][] getAryPltRoundingItems() {
		return getStringArrayClone(aryPltRoundingItems);
	}
	
	/**
	 * @param aryPltRoundingItems セットする aryPltRoundingItems
	 */
	public void setAryPltRoundingItems(String[][] aryPltRoundingItems) {
		this.aryPltRoundingItems = getStringArrayClone(aryPltRoundingItems);
	}
	
	/**
	 * @return txtTransferAheadLimitMonth
	 */
	public String getTxtTransferAheadLimitMonth() {
		return txtTransferAheadLimitMonth;
	}
	
	/**
	 * @param txtTransferAheadLimitMonth セットする txtTransferAheadLimitMonth
	 */
	public void setTxtTransferAheadLimitMonth(String txtTransferAheadLimitMonth) {
		this.txtTransferAheadLimitMonth = txtTransferAheadLimitMonth;
	}
	
	/**
	 * @param txtTransferAheadLimitDate セットする txtTransferAheadLimitDate
	 */
	public void setTxtTransferAheadLimitDate(String txtTransferAheadLimitDate) {
		this.txtTransferAheadLimitDate = txtTransferAheadLimitDate;
	}
	
	/**
	 * @return txtTransferAheadLimitDate
	 */
	public String getTxtTransferAheadLimitDate() {
		return txtTransferAheadLimitDate;
	}
	
	/**
	 * @param txtTransferLaterLimitMonth セットする txtTransferLaterLimitMonth
	 */
	public void setTxtTransferLaterLimitMonth(String txtTransferLaterLimitMonth) {
		this.txtTransferLaterLimitMonth = txtTransferLaterLimitMonth;
	}
	
	/**
	 * @return txtTransferLaterLimitMonth
	 */
	public String getTxtTransferLaterLimitMonth() {
		return txtTransferLaterLimitMonth;
	}
	
	/**
	 * @param txtTransferLaterLimitDate セットする txtTransferLaterLimitDate
	 */
	public void setTxtTransferLaterLimitDate(String txtTransferLaterLimitDate) {
		this.txtTransferLaterLimitDate = txtTransferLaterLimitDate;
	}
	
	/**
	 * @return txtTransferLaterLimitDate
	 */
	public String getTxtTransferLaterLimitDate() {
		return txtTransferLaterLimitDate;
	}
	
	/**
	 * @param txtSubHolidayLimitMonth セットする txtSubHolidayLimitMonth
	 */
	public void setTxtSubHolidayLimitMonth(String txtSubHolidayLimitMonth) {
		this.txtSubHolidayLimitMonth = txtSubHolidayLimitMonth;
	}
	
	/**
	 * @return txtSubHolidayLimitMonth
	 */
	public String getTxtSubHolidayLimitMonth() {
		return txtSubHolidayLimitMonth;
	}
	
	/**
	 * @param txtSubHolidayLimitDate セットする txtSubHolidayLimitDate
	 */
	public void setTxtSubHolidayLimitDate(String txtSubHolidayLimitDate) {
		this.txtSubHolidayLimitDate = txtSubHolidayLimitDate;
	}
	
	/**
	 * @return txtSubHolidayLimitDate
	 */
	public String getTxtSubHolidayLimitDate() {
		return txtSubHolidayLimitDate;
	}
	
	/**
	 * @param pltTransferExchange セットする pltTransferExchange
	 */
	public void setPltTransferExchange(String pltTransferExchange) {
		this.pltTransferExchange = pltTransferExchange;
	}
	
	/**
	 * @return pltTransferExchange
	 */
	public String getPltTransferExchange() {
		return pltTransferExchange;
	}
	
	/**
	 * @param pltSubHolidayExchange セットする pltSubHolidayExchange
	 */
	public void setPltSubHolidayExchange(String pltSubHolidayExchange) {
		this.pltSubHolidayExchange = pltSubHolidayExchange;
	}
	
	/**
	 * @return pltSubHolidayExchange
	 */
	public String getPltSubHolidayExchange() {
		return pltSubHolidayExchange;
	}
	
	/**
	 * @param txtSubHolidayAllNormHour セットする txtSubHolidayAllNormHour
	 */
	public void setTxtSubHolidayAllNormHour(String txtSubHolidayAllNormHour) {
		this.txtSubHolidayAllNormHour = txtSubHolidayAllNormHour;
	}
	
	/**
	 * @return txtSubHolidayAllNormHour
	 */
	public String getTxtSubHolidayAllNormHour() {
		return txtSubHolidayAllNormHour;
	}
	
	/**
	 * @param txtSubHolidayAllNormMinute セットする txtSubHolidayAllNormMinute
	 */
	public void setTxtSubHolidayAllNormMinute(String txtSubHolidayAllNormMinute) {
		this.txtSubHolidayAllNormMinute = txtSubHolidayAllNormMinute;
	}
	
	/**
	 * @return txtSubHolidayAllNormMinute
	 */
	public String getTxtSubHolidayAllNormMinute() {
		return txtSubHolidayAllNormMinute;
	}
	
	/**
	 * @param txtSubHolidayHalfNormHour セットする txtSubHolidayHalfNormHour
	 */
	public void setTxtSubHolidayHalfNormHour(String txtSubHolidayHalfNormHour) {
		this.txtSubHolidayHalfNormHour = txtSubHolidayHalfNormHour;
	}
	
	/**
	 * @return txtSubHolidayHalfNormHour
	 */
	public String getTxtSubHolidayHalfNormHour() {
		return txtSubHolidayHalfNormHour;
	}
	
	/**
	 * @param txtSubHolidayHalfNormMinute セットする txtSubHolidayHalfNormMinute
	 */
	public void setTxtSubHolidayHalfNormMinute(String txtSubHolidayHalfNormMinute) {
		this.txtSubHolidayHalfNormMinute = txtSubHolidayHalfNormMinute;
	}
	
	/**
	 * @return txtSubHolidayHalfNormMinute
	 */
	public String getTxtSubHolidayHalfNormMinute() {
		return txtSubHolidayHalfNormMinute;
	}
	
	/**
	 * @return pltPortalTimeButtons
	 */
	public String getPltPortalTimeButtons() {
		return pltPortalTimeButtons;
	}
	
	/**
	 * @param pltPortalTimeButtons セットする pltPortalTimeButtons
	 */
	public void setPltPortalTimeButtons(String pltPortalTimeButtons) {
		this.pltPortalTimeButtons = pltPortalTimeButtons;
	}
	
	/**
	 * @return pltPortalRestButtons
	 */
	public String getPltPortalRestButtons() {
		return pltPortalRestButtons;
	}
	
	/**
	 * @param pltPortalRestButtons セットする pltPortalRestButtons
	 */
	public void setPltPortalRestButtons(String pltPortalRestButtons) {
		this.pltPortalRestButtons = pltPortalRestButtons;
	}
	
	/**
	 * @return pltUseScheduledTime
	 */
	public String getPltUseScheduledTime() {
		return pltUseScheduledTime;
	}
	
	/**
	 * @param pltUseScheduledTime セットする pltUseScheduledTime
	 */
	public void setPltUseScheduledTime(String pltUseScheduledTime) {
		this.pltUseScheduledTime = pltUseScheduledTime;
	}
	
	/**
	 * @param txtRoundDailyStart セットする txtRoundDailyStart
	 */
	public void setTxtRoundDailyStart(String txtRoundDailyStart) {
		this.txtRoundDailyStart = txtRoundDailyStart;
	}
	
	/**
	 * @return txtRoundDailyStart
	 */
	public String getTxtRoundDailyStart() {
		return txtRoundDailyStart;
	}
	
	/**
	 * @param txtRoundDailyEnd セットする txtRoundDailyEnd
	 */
	public void setTxtRoundDailyEnd(String txtRoundDailyEnd) {
		this.txtRoundDailyEnd = txtRoundDailyEnd;
	}
	
	/**
	 * @return txtRoundDailyEnd
	 */
	public String getTxtRoundDailyEnd() {
		return txtRoundDailyEnd;
	}
	
	/**
	 * @param txtRoundDailyRestStart セットする txtRoundDailyRestStart
	 */
	public void setTxtRoundDailyRestStart(String txtRoundDailyRestStart) {
		this.txtRoundDailyRestStart = txtRoundDailyRestStart;
	}
	
	/**
	 * @return txtRoundDailyRestStart
	 */
	public String getTxtRoundDailyRestStart() {
		return txtRoundDailyRestStart;
	}
	
	/**
	 * @param txtRoundDailyRestEnd セットする txtRoundDailyRestEnd
	 */
	public void setTxtRoundDailyRestEnd(String txtRoundDailyRestEnd) {
		this.txtRoundDailyRestEnd = txtRoundDailyRestEnd;
	}
	
	/**
	 * @return txtRoundDailyRestEnd
	 */
	public String getTxtRoundDailyRestEnd() {
		return txtRoundDailyRestEnd;
	}
	
	/**
	 * @param txtRoundDailyLate セットする txtRoundDailyLate
	 */
	public void setTxtRoundDailyLate(String txtRoundDailyLate) {
		this.txtRoundDailyLate = txtRoundDailyLate;
	}
	
	/**
	 * @return txtRoundDailyLate
	 */
	public String getTxtRoundDailyLate() {
		return txtRoundDailyLate;
	}
	
	/**
	 * @param txtRoundDailyLeaveEarly セットする txtRoundDailyLeaveEarly
	 */
	public void setTxtRoundDailyLeaveEarly(String txtRoundDailyLeaveEarly) {
		this.txtRoundDailyLeaveEarly = txtRoundDailyLeaveEarly;
	}
	
	/**
	 * @return txtRoundDailyLeaveEarly
	 */
	public String getTxtRoundDailyLeaveEarly() {
		return txtRoundDailyLeaveEarly;
	}
	
	/**
	 * @param pltRoundDailyLeaveEaly セットする pltRoundDailyLeaveEaly
	 */
	public void setPltRoundDailyLeaveEaly(String pltRoundDailyLeaveEaly) {
		this.pltRoundDailyLeaveEaly = pltRoundDailyLeaveEaly;
	}
	
	/**
	 * @return pltRoundDailyLeaveEaly
	 */
	public String getPltRoundDailyLeaveEaly() {
		return pltRoundDailyLeaveEaly;
	}
	
	/**
	 * @param txtRoundDailyPrivateIn セットする txtRoundDailyPrivateIn
	 */
	public void setTxtRoundDailyPrivateIn(String txtRoundDailyPrivateIn) {
		this.txtRoundDailyPrivateIn = txtRoundDailyPrivateIn;
	}
	
	/**
	 * @return txtRoundDailyPrivateIn
	 */
	public String getTxtRoundDailyPrivateIn() {
		return txtRoundDailyPrivateIn;
	}
	
	/**
	 * @param txtRoundDailyPrivateOut セットする txtRoundDailyPrivateOut
	 */
	public void setTxtRoundDailyPrivateOut(String txtRoundDailyPrivateOut) {
		this.txtRoundDailyPrivateOut = txtRoundDailyPrivateOut;
	}
	
	/**
	 * @return txtRoundDailyPrivateOut
	 */
	public String getTxtRoundDailyPrivateOut() {
		return txtRoundDailyPrivateOut;
	}
	
	/**
	 * @param txtRoundDailyPublicIn セットする txtRoundDailyPublicIn
	 */
	public void setTxtRoundDailyPublicIn(String txtRoundDailyPublicIn) {
		this.txtRoundDailyPublicIn = txtRoundDailyPublicIn;
	}
	
	/**
	 * @return txtRoundDailyPublicIn
	 */
	public String getTxtRoundDailyPublicIn() {
		return txtRoundDailyPublicIn;
	}
	
	/**
	 * @param txtRoundDailyPublicOut セットする txtRoundDailyPublicOut
	 */
	public void setTxtRoundDailyPublicOut(String txtRoundDailyPublicOut) {
		this.txtRoundDailyPublicOut = txtRoundDailyPublicOut;
	}
	
	/**
	 * @return txtRoundDailyPublicOut
	 */
	public String getTxtRoundDailyPublicOut() {
		return txtRoundDailyPublicOut;
	}
	
	/**
	 * @param txtRoundMonthlyLate セットする txtRoundMonthlyLate
	 */
	public void setTxtRoundMonthlyLate(String txtRoundMonthlyLate) {
		this.txtRoundMonthlyLate = txtRoundMonthlyLate;
	}
	
	/**
	 * @return txtRoundMonthlyLate
	 */
	public String getTxtRoundMonthlyLate() {
		return txtRoundMonthlyLate;
	}
	
	/**
	 * @param pltRoundMonthlyLate セットする pltRoundMonthlyLate
	 */
	public void setPltRoundMonthlyLate(String pltRoundMonthlyLate) {
		this.pltRoundMonthlyLate = pltRoundMonthlyLate;
	}
	
	/**
	 * @return pltRoundMonthlyLate
	 */
	public String getPltRoundMonthlyLate() {
		return pltRoundMonthlyLate;
	}
	
	/**
	 * @param txtRoundMonthlyLeaveEarly セットする txtRoundMonthlyLeaveEarly
	 */
	public void setTxtRoundMonthlyLeaveEarly(String txtRoundMonthlyLeaveEarly) {
		this.txtRoundMonthlyLeaveEarly = txtRoundMonthlyLeaveEarly;
	}
	
	/**
	 * @return txtRoundMonthlyLeaveEarly
	 */
	public String getTxtRoundMonthlyLeaveEarly() {
		return txtRoundMonthlyLeaveEarly;
	}
	
	/**
	 * @param pltRoundMonthlyLeaveEarly セットする pltRoundMonthlyLeaveEarly
	 */
	public void setPltRoundMonthlyLeaveEarly(String pltRoundMonthlyLeaveEarly) {
		this.pltRoundMonthlyLeaveEarly = pltRoundMonthlyLeaveEarly;
	}
	
	/**
	 * @return pltRoundMonthlyLeaveEarly
	 */
	public String getPltRoundMonthlyLeaveEarly() {
		return pltRoundMonthlyLeaveEarly;
	}
	
}
