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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.WorkTypeEntity;

/**
 * 勤怠データ自動計算クラス。
 */
public class AttendanceCalcBean extends TimeBean implements AttendanceCalcBeanInterface {
	
	/**
	 * 勤務日
	 */
	protected Date										workDate;
	
	/**
	 * 出勤時刻
	 */
	protected Date										startTime;
	
	/**
	 * 退勤時刻
	 */
	protected Date										endTime;
	
	/**
	 * 直行
	 */
	protected boolean									directStart;
	
	/**
	 * 直帰
	 */
	protected boolean									directEnd;
	
	/**
	 * 遅刻理由
	 */
	protected String									lateReason;
	
	/**
	 * 早退理由
	 */
	protected String									leaveEarlyReason;
	
	/**
	 * 勤務時間
	 */
	protected int										workTime;
	
	/**
	 * 所定労働時間
	 */
	protected int										prescribedWorkTime;
	
	/**
	 * 休暇時間
	 */
	protected int										totalRest;
	
	/**
	 * 法定休日休憩
	 */
	protected int										legalHolidayRest;
	
	/**
	 * 所定休日休憩
	 */
	protected int										prescribedHolidayRest;
	
	/**
	 * 公用外出
	 */
	protected int										totalPublic;
	
	/**
	 * 私用外出
	 */
	protected int										totalPrivate;
	
	/**
	 * 分単位休暇A全休
	 */
	protected boolean									allMinutelyHolidayA;
	
	/**
	 * 分単位休暇B全休
	 */
	protected boolean									allMinutelyHolidayB;
	
	/**
	 * 分単位休暇A
	 */
	protected int										totalMinutelyHolidayA;
	
	/**
	 * 分単位休暇B
	 */
	protected int										totalMinutelyHolidayB;
	
	/**
	 * 遅刻
	 */
	protected int										lateTime;
	
	/**
	 * 早退
	 */
	protected int										leaveEarlyTime;
	
	/**
	 * 休憩リスト
	 */
	protected List<RestDtoInterface>					restDtoList;
	
	/**
	 * 公用外出リスト
	 */
	protected List<GoOutDtoInterface>					publicGoOutDtoList;
	
	/**
	 * 私用外出リスト
	 */
	protected List<GoOutDtoInterface>					privateGoOutDtoList;
	
	/**
	 * 分単位休暇Aリスト
	 */
	protected List<GoOutDtoInterface>					minutelyHolidayADtoList;
	
	/**
	 * 分単位休暇Bリスト
	 */
	protected List<GoOutDtoInterface>					minutelyHolidayBDtoList;
	
	/**
	 * 分単位休暇A(分休)
	 */
	protected Map<Date, Date>							minutelyHolidayAMap;
	
	/**
	 * 分単位休暇B(分休)
	 */
	protected Map<Date, Date>							minutelyHolidayBMap;
	
	/**
	 * 減額対象時間
	 */
	protected int										decreaseTime;
	
	/**
	 * 勤怠設定管理DTOインターフェース
	 */
	protected TimeSettingDtoInterface					timeSettingDto;
	
	/**
	 * 勤務形態項目管理DTOインターフェース
	 */
	protected WorkTypeItemDtoInterface					workTypeItemDto;
	
	/**
	 * 休日出勤申請DTOインターフェース
	 */
	protected WorkOnHolidayRequestDtoInterface			workOnHolidayDto;
	
	/**
	 * 休暇申請DTOインターフェースリスト
	 */
	protected List<HolidayRequestDtoInterface>			holidayRequestDtoList;
	
	/**
	 * 代休申請DTOインターフェースリスト
	 */
	protected List<SubHolidayRequestDtoInterface>		subHolidayRequestDtoList;
	
	/**
	 * カレンダマスタDTOインターフェース
	 */
	protected ScheduleDtoInterface						scheduleDto;
	
	/**
	 * 時差出勤申請DTOインターフェース
	 */
	protected DifferenceRequestDtoInterface				differenceDto;
	
	/**
	 * カレンダ日マスタDTOインターフェース
	 */
	protected ScheduleDateDtoInterface					scheduleDateDto;
	
	/**
	 * 残業申請DTOインターフェース(始業前)
	 */
	protected OvertimeRequestDtoInterface				beforeOvertimeDto;
	
	/**
	 * 残業申請DTOインターフェース(終業後)
	 */
	protected OvertimeRequestDtoInterface				afterOvertimeDto;
	
	/**
	 * 勤務形態エンティティ。
	 */
	protected WorkTypeEntity							workTypeEntity;
	
	/**
	 * 公用外出時間
	 */
	protected int										totalPublicTime;
	
	/**
	 * 残業時間
	 */
	protected int										overtimeTime;
	
	/**
	 * 規定始業時刻
	 */
	protected int										regWorkStart;
	
	/**
	 * 規定終業時刻
	 */
	protected int										regWorkEnd;
	
	/**
	 * 規定労働時間
	 */
	protected int										regWorkTime;
	
	/**
	 * 規定フル始業時刻
	 */
	protected int										regFullWorkStart;
	
	/**
	 * 規定フル労働時間
	 */
	protected int										regFullWorkTime;
	
	/**
	 * 規定休憩
	 */
	protected Map<Date, Date>							regRestMap;
	
	/**
	 * 前半休と後半休の間の時間
	 */
	protected int										betweenHalfHolidayTime;
	
	/**
	 * 残前休憩
	 */
	protected int										overbefore;
	
	/**
	 * 残業休憩時間(毎)
	 */
	protected int										overper;
	
	/**
	 * 残業休憩時間
	 */
	protected int										overrest;
	
	/**
	 * 法定外残業時間
	 */
	protected int										overtimeOut;
	
	/**
	 * 平日法定労働時間内労働時間
	 */
	protected int										workdayOvertimeIn;
	
	/**
	 * 平日法定労働時間外労働時間
	 */
	protected int										workdayOvertimeOut;
	
	/**
	 * 所定休日法定労働時間内労働時間
	 */
	protected int										prescribedHolidayOvertimeIn;
	
	/**
	 * 所定休日法定労働時間外労働時間
	 */
	protected int										prescribedHolidayOvertimeOut;
	
	/**
	 * 法定外休憩時間
	 */
	protected int										overRestTime;
	
	/**
	 * 深夜勤務時間
	 */
	protected int										nightWork;
	
	/**
	 * 深夜休憩時間
	 */
	protected int										nightRest;
	
	/**
	 * 深夜労働時間配列
	 */
	protected int[]										nightWorkArray;
	
	/**
	 * 深夜休憩時間配列
	 */
	protected int[]										nightRestArray;
	
	/**
	 * 法定休日労働
	 */
	protected int										legalHolidayWork;
	
	/**
	 * 所定休日労働
	 */
	protected int										prescribedHolidayWork;
	
	/**
	 * 法内残業
	 */
	protected int										withinStatutoryOvertime;
	
	/**
	 * 法定代休発生日数
	 */
	protected double									grantedLegalCompensationDays;
	
	/**
	 * 所定代休発生日数
	 */
	protected double									grantedPrescribedCompensationDays;
	
	/**
	 * 深夜代休発生日数
	 */
	protected double									grantedNightCompensationDays;
	
	/**
	 * 所定労働時間内法定休日労働時間
	 */
	protected int										statutoryHolidayWorkIn;
	
	/**
	 * 所定労働時間外法定休日労働時間
	 */
	protected int										statutoryHolidayWorkOut;
	
	/**
	 * 所定労働時間内所定休日労働時間
	 */
	protected int										prescribedHolidayWorkIn;
	
	/**
	 * 所定労働時間外所定休日労働時間
	 */
	protected int										prescribedHolidayWorkOut;
	
	/**
	 * 規定始業時刻前時間
	 */
	protected int										workBeforeTime;
	
	/**
	 * 規定終業時刻後時間
	 */
	protected int										workAfterTime;
	
	/**
	 * 勤怠計算上の始業時刻
	 */
	protected int										calculatedStart;
	
	/**
	 * 勤怠計算上の終業時刻
	 */
	protected int										calculatedEnd;
	
	/**
	 * 所定労働終了時刻
	 */
	protected int										prescribedWorkEnd;
	
	/**
	 * 自動休憩計算開始時刻
	 */
	protected int										autoRestCalcStart;
	
	/**
	 * 遅刻休憩
	 */
	protected Map<Date, Date>							tardinessRestMap;
	
	/**
	 * 早退休憩
	 */
	protected Map<Date, Date>							leaveEarlyRestMap;
	
	/**
	 * 規定終業時刻後時間外前休憩
	 */
	protected Map<Date, Date>							overtimeBeforeRestMap;
	
	/**
	 * 規定終業時刻後時間外休憩
	 */
	protected Map<Date, Date>							overtimeRestMap;
	
	/**
	 * 24時以前の有給休暇時間
	 */
	protected int										totalBefore24HourPaidLeave;
	
	/**
	 * 24時以前の合計手動休憩時間
	 */
	protected int										totalBefore24HourManualRest;
	
	/**
	 * 24時以前の合計残前休憩時間
	 */
	protected int										totalBefore24HourOvertimeBeforeRest;
	
	/**
	 * 24時以前の合計残業休憩時間
	 */
	protected int										totalBefore24HourOvertimeRest;
	
	/**
	 * 24時以前の合計公用外出時間
	 */
	protected int										totalBefore24HourPublicGoOut;
	
	/**
	 * 24時以前の合計私用外出時間
	 */
	protected int										totalBefore24HourPrivateGoOut;
	
	/**
	 * 24時以前の合計分単位休暇A時間
	 */
	protected int										totalBefore24HourMinutelyHolidayA;
	
	/**
	 * 24時以前の合計分単位休暇B時間
	 */
	protected int										totalBefore24HourMinutelyHolidayB;
	
	/**
	 * 24時以後の有給休暇時間
	 */
	protected int										totalAfter24HourPaidLeave;
	
	/**
	 * 24時以後の合計手動休憩時間
	 */
	protected int										totalAfter24HourManualRest;
	
	/**
	 * 24時以後の合計残前休憩時間
	 */
	protected int										totalAfter24HourOvertimeBeforeRest;
	
	/**
	 * 24時以後の合計残業休憩時間
	 */
	protected int										totalAfter24HourOvertimeRest;
	
	/**
	 * 24時以後の合計公用外出時間
	 */
	protected int										totalAfter24HourPublicGoOut;
	
	/**
	 * 24時以後の合計私用外出時間
	 */
	protected int										totalAfter24HourPrivateGoOut;
	
	/**
	 * 24時以後の合計分単位休暇A時間
	 */
	protected int										totalAfter24HourMinutelyHolidayA;
	
	/**
	 * 24時以後の合計分単位休暇B時間
	 */
	protected int										totalAfter24HourMinutelyHolidayB;
	
	/**
	 * 有給休暇(前半休)
	 */
	protected boolean									isPaidLeaveAm;
	
	/**
	 * 有給休暇(後半休)
	 */
	protected boolean									isPaidLeavePm;
	
	/**
	 * 有給休暇(時間休)
	 */
	protected Map<Date, Date>							paidLeaveHourMap;
	
	/**
	 * 有給休暇時間数
	 */
	protected int										paidLeaveHour;
	
	/**
	 * 始業時刻前の有給休暇分数
	 */
	protected int										beforePaidLeaveMinute;
	
	/**
	 * 終業時刻後の有給休暇分数
	 */
	protected int										afterPaidLeaveMinute;
	
	/**
	 * 始業時刻前の分単位休暇分数
	 */
	protected int										beforeMinutelyHolidayMinute;
	
	/**
	 * 終業時刻後の分単位休暇分数
	 */
	protected int										afterMinutelyHolidayMinute;
	
	/**
	 * ストック休暇(前半休)
	 */
	protected boolean									isStockLeaveAm;
	
	/**
	 * ストック休暇(後半休)
	 */
	protected boolean									isStockLeavePm;
	
	/**
	 * 特別休暇(前半休)
	 */
	protected boolean									isSpecialLeaveAm;
	
	/**
	 * 特別休暇(後半休)
	 */
	protected boolean									isSpecialLeavePm;
	
	/**
	 * その他休暇(前半休)
	 */
	protected boolean									isOtherLeaveAm;
	
	/**
	 * その他休暇(後半休)
	 */
	protected boolean									isOtherLeavePm;
	
	/**
	 * 欠勤(前半休)
	 */
	protected boolean									isAbsenceAm;
	
	/**
	 * 欠勤(後半休)
	 */
	protected boolean									isAbsencePm;
	
	/**
	 * 法定代休(前半休)
	 */
	protected boolean									isLegalCompensationDayAm;
	
	/**
	 * 法定代休(後半休)
	 */
	protected boolean									isLegalCompensationDayPm;
	
	/**
	 * 所定代休(前半休)
	 */
	protected boolean									isPrescribedCompensationDayAm;
	
	/**
	 * 所定代休(後半休)
	 */
	protected boolean									isPrescribedCompensationDayPm;
	
	/**
	 * 深夜代休(前半休)
	 */
	protected boolean									isNightCompensationDaysAm;
	
	/**
	 * 深夜代休(前半休)
	 */
	protected boolean									isNightCompensationDaysPm;
	
	/**
	 * 勤務形態コード
	 */
	protected String									workTypeCode;
	
	/**
	 * 翌日の勤務形態コード
	 */
	protected String									nextDayWorkTypeCode;
	
	// 勤務形態エンティティ
	/**
	 * 時短時間1可否
	 */
	protected boolean									useShort1;
	
	/**
	 * 時短時間1給与区分(true：時短時間1給与区分が有給、false：無給)
	 */
	protected boolean									isShort1StartTypePay;
	
	/**
	 * 時短時間1開始時刻
	 */
	protected int										short1Start;
	
	/**
	 * 時短時間1終了時刻
	 */
	protected int										short1End;
	
	/**
	 * 時短時間2可否
	 */
	protected boolean									useShort2;
	
	/**
	 * 時短時間2給与区分(true：時短時間2給与区分が有給、false：無給)
	 */
	protected boolean									isShort2StartTypePay;
	
	/**
	 * 時短時間2開始時刻
	 */
	protected int										short2Start;
	
	/**
	 * 時短時間2終了時刻
	 */
	protected int										short2End;
	
	/**
	 * 設定適用管理参照インターフェース。<br>
	 */
	private ApplicationReferenceBeanInterface			applicationReference;
	/**
	 * 勤怠設定参照インターフェース。<br>
	 */
	private TimeSettingReferenceBeanInterface			timeSettingReference;
	/**
	 * カレンダ管理参照インターフェース。<br>
	 */
	private ScheduleReferenceBeanInterface				scheduleReference;
	/**
	 * カレンダ日参照インターフェース。<br>
	 */
	private ScheduleDateReferenceBeanInterface			scheduleDateReference;
	/**
	 * 勤怠データ参照インターフェース。<br>
	 */
	private AttendanceReferenceBeanInterface			attendanceReference;
	/**
	 * 残業申請参照インターフェース。<br>
	 */
	private OvertimeRequestReferenceBeanInterface		overtimeReference;
	/**
	 * 休暇申請参照インターフェース。<br>
	 */
	private HolidayRequestReferenceBeanInterface		holidayReference;
	/**
	 * 代休申請参照インターフェース。<br>
	 */
	private SubHolidayRequestReferenceBeanInterface		subHolidayReference;
	/**
	 * 休日出勤申請参照インターフェース。<br>
	 */
	private WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayReference;
	/**
	 * 振替休日データ参照インターフェース。<br>
	 */
	private SubstituteReferenceBeanInterface			substituteReference;
	/**
	 * ワークフロー参照インターフェース。<br>
	 */
	private WorkflowReferenceBeanInterface				workflowReference;
	/**
	 * ワークフロー統括インターフェース。<br>
	 */
	private WorkflowIntegrateBeanInterface				workflowIntegrate;
	/**
	 * 勤務形態項目参照インターフェース。<br>
	 */
	protected WorkTypeItemReferenceBeanInterface		workTypeItemReference;
	/**
	 * 勤務形態マスタ参照インターフェース。<br>
	 */
	protected WorkTypeReferenceBeanInterface			workTypeReference;
	/**
	 * 勤怠データ休憩情報参照インターフェース。<br>
	 */
	private RestReferenceBeanInterface					restReference;
	/**
	 * 勤怠データ外出情報参照インターフェース。<br>
	 */
	private GoOutReferenceBeanInterface					goOutReference;
	
	
	@Override
	public void initBean() throws MospException {
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		overtimeReference = (OvertimeRequestReferenceBeanInterface)createBean(OvertimeRequestReferenceBeanInterface.class);
		holidayReference = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		subHolidayReference = (SubHolidayRequestReferenceBeanInterface)createBean(SubHolidayRequestReferenceBeanInterface.class);
		workOnHolidayReference = (WorkOnHolidayRequestReferenceBeanInterface)createBean(WorkOnHolidayRequestReferenceBeanInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		workflowReference = (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workTypeItemReference = (WorkTypeItemReferenceBeanInterface)createBean(WorkTypeItemReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		restReference = (RestReferenceBeanInterface)createBean(RestReferenceBeanInterface.class);
		goOutReference = (GoOutReferenceBeanInterface)createBean(GoOutReferenceBeanInterface.class);
	}
	
	/**
	 * @param personalId 個人ID
	 * @param workTypeCode 勤務形態コード
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public void initAttendanceTotal(String personalId, String workTypeCode, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 設定適用マスタDTOインターフェース
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, workDate);
		applicationReference.chkExistApplication(applicationDto, workDate);
		if (mospParams.hasErrorMessage()) {
			// 該当する設定適用が存在しない
			return;
		}
		// カレンダマスタDTOインターフェース
		ScheduleDtoInterface scheduleDto = scheduleReference
			.getScheduleInfo(applicationDto.getScheduleCode(), workDate);
		scheduleReference.chkExistSchedule(scheduleDto, workDate);
		if (mospParams.hasErrorMessage()) {
			// 該当するカレンダマスタが存在しない
			return;
		}
		// 勤怠設定
		timeSettingDto = timeSettingReference.getTimeSettingInfo(applicationDto.getWorkSettingCode(), workDate);
		timeSettingReference.chkExistTimeSetting(timeSettingDto, workDate);
		if (mospParams.hasErrorMessage()) {
			// 該当する勤怠設定が存在しない
			return;
		}
		// カレンダ日マスタDTOインターフェース
		scheduleDateDto = scheduleDateReference.getScheduleDateInfo(scheduleDto.getScheduleCode(), workDate, workDate);
		scheduleDateReference.chkExistScheduleDate(scheduleDateDto, workDate);
		if (mospParams.hasErrorMessage()) {
			// 該当するカレンダマスタが存在しない
			return;
		}
		// 残業申請
		OvertimeRequestDtoInterface overtimeRequestDto = overtimeReference
			.findForKeyOnWorkflow(personalId, workDate, 2);
		if (overtimeRequestDto != null) {
			WorkflowDtoInterface workflowDto = workflowReference
				.getLatestWorkflowInfo(overtimeRequestDto.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				afterOvertimeDto = overtimeRequestDto;
			}
		}
		if (timeSettingDto.getBeforeOvertimeFlag() == TimeConst.CODE_BEFORE_OVERTIME_VALID) {
			// 始業前の時間外労働が有効である場合
			OvertimeRequestDtoInterface beforeDto = overtimeReference.findForKeyOnWorkflow(personalId, workDate, 1);
			if (beforeDto != null) {
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(beforeDto.getWorkflow());
				if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認完了の場合
					beforeOvertimeDto = beforeDto;
				}
			}
		}
		// 休暇申請
		holidayRequestDtoList = new ArrayList<HolidayRequestDtoInterface>();
		List<HolidayRequestDtoInterface> holidayRequestList = holidayReference.getHolidayRequestList(personalId,
				workDate);
		for (HolidayRequestDtoInterface requestDto : holidayRequestList) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				holidayRequestDtoList.add(requestDto);
			}
		}
		// 代休申請
		subHolidayRequestDtoList = new ArrayList<SubHolidayRequestDtoInterface>();
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayReference.getSubHolidayRequestList(
				personalId, workDate);
		for (SubHolidayRequestDtoInterface requestDto : subHolidayRequestList) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				subHolidayRequestDtoList.add(requestDto);
			}
		}
		// 時差出勤
		DifferenceRequestReferenceBeanInterface differenceReference = (DifferenceRequestReferenceBeanInterface)createBean(
				DifferenceRequestReferenceBeanInterface.class, workDate);
		DifferenceRequestDtoInterface differenceRequestDto = differenceReference.findForKeyOnWorkflow(personalId,
				workDate);
		if (differenceRequestDto != null) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceRequestDto
				.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				differenceDto = differenceRequestDto;
			}
		}
		// 休日出勤
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayReference.findForKeyOnWorkflow(
				personalId, workDate);
		if (workOnHolidayRequestDto != null) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayRequestDto
				.getWorkflow());
			if (workflowDto != null
					&& (workflowIntegrate.isApprovable(workflowDto) || workflowIntegrate.isCompleted(workflowDto))) {
				// 承認可能又は承認済の場合
				workOnHolidayDto = workOnHolidayRequestDto;
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
					// 振替休日を申請する場合
					List<SubstituteDtoInterface> list = substituteReference.getSubstituteList(workOnHolidayDto
						.getWorkflow());
					for (SubstituteDtoInterface dto : list) {
						ScheduleDateDtoInterface substituteScheduleDateDto = scheduleDateReference.getScheduleDateInfo(
								scheduleDto.getScheduleCode(), workDate, dto.getSubstituteDate());
						if (substituteScheduleDateDto == null) {
							continue;
						}
						scheduleDateDto = substituteScheduleDateDto;
						break;
					}
				}
			}
		}
		// 勤務形態コードを取得
		nextDayWorkTypeCode = getWorkTypeCode(personalId, DateUtility.addDay(workDate, 1), TimeBean.TIMES_WORK_DEFAULT);
		if (differenceDto != null
				&& (TimeConst.CODE_DIFFERENCE_TYPE_A.equals(workTypeCode)
						|| TimeConst.CODE_DIFFERENCE_TYPE_B.equals(workTypeCode)
						|| TimeConst.CODE_DIFFERENCE_TYPE_C.equals(workTypeCode)
						|| TimeConst.CODE_DIFFERENCE_TYPE_D.equals(workTypeCode) || TimeConst.CODE_DIFFERENCE_TYPE_S
					.equals(workTypeCode))) {
			// 時差出勤の場合
			setFields(scheduleDateDto.getWorkTypeCode(), requestUtil);
		} else {
			setFields(workTypeCode, requestUtil);
		}
	}
	
	/**
	 * @param workTypeCode 勤務形態コード
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void setFields(String workTypeCode, RequestUtilBeanInterface requestUtil) throws MospException {
		// カレンダ日マスタの所定休日、法定休日、空欄の場合
		this.workTypeCode = workTypeCode;
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			return;
		}
		// 勤務形態エンティティ取得
		workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, workDate);
		// TODO 以下、workTypeEntityの値を使って値を設定する。
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 法定休日労働又は所定休日労働の場合
			// 始業時刻
			int daysOffWorkStart = getDefferenceMinutes(workOnHolidayDto.getRequestDate(),
					workOnHolidayDto.getStartTime());
			regWorkStart = daysOffWorkStart;
			regFullWorkStart = daysOffWorkStart;
			// 終業時刻
			regWorkEnd = getDefferenceMinutes(workOnHolidayDto.getRequestDate(), workOnHolidayDto.getEndTime());
			return;
		}
		// カレンダ日マスタに勤怠コードが設定されている場合
		WorkTypeDtoInterface workTypeDto = workTypeReference.getWorkTypeInfo(workTypeCode, workDate);
		if (workTypeDto == null) {
			addWorkTypeNotExistErrorMessage(workDate);
			return;
		}
		String localWorkTypeCode = workTypeDto.getWorkTypeCode();
		// 出勤時刻,退勤時刻の取得
		// 勤務形態から取得
		// 出勤時刻
		WorkTypeItemDtoInterface workStartDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_WORKSTART);
		if (workStartDto != null) {
			int time = getDefferenceMinutes(getDefaultStandardDate(), workStartDto.getWorkTypeItemValue());
			regWorkStart = time;
			regFullWorkStart = time;
		}
		// 退勤時刻
		WorkTypeItemDtoInterface workEndDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_WORKEND);
		if (workEndDto != null) {
			regWorkEnd = getDefferenceMinutes(getDefaultStandardDate(), workEndDto.getWorkTypeItemValue());
		}
		// 規定労働時間
		WorkTypeItemDtoInterface workTimeDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_WORKTIME);
		if (workTimeDto != null) {
			int time = getDefferenceMinutes(getDefaultStandardDate(), workTimeDto.getWorkTypeItemValue());
			regWorkTime = time;
			regFullWorkTime = time;
		}
		// 残前休憩
		WorkTypeItemDtoInterface overBeforeDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_OVERBEFORE);
		if (overBeforeDto != null) {
			overbefore = getDefferenceMinutes(getDefaultStandardDate(), overBeforeDto.getWorkTypeItemValue());
		}
		// 残業休憩時間(毎)
		WorkTypeItemDtoInterface overPerDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_OVERPER);
		if (overPerDto != null) {
			overper = getDefferenceMinutes(getDefaultStandardDate(), overPerDto.getWorkTypeItemValue());
		}
		// 残業休憩時間
		WorkTypeItemDtoInterface overRestDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_OVERREST);
		if (overRestDto != null) {
			overrest = getDefferenceMinutes(getDefaultStandardDate(), overRestDto.getWorkTypeItemValue());
		}
		// 半休
		WorkTypeItemDtoInterface frontStartDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_FRONTSTART);
		WorkTypeItemDtoInterface frontEndDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_FRONTEND);
		WorkTypeItemDtoInterface backStartDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_BACKSTART);
		WorkTypeItemDtoInterface backEndDto = workTypeItemReference.getWorkTypeItemInfo(localWorkTypeCode, workDate,
				TimeConst.CODE_BACKEND);
		int frontStart = 0;
		if (frontStartDto != null) {
			frontStart = getDefferenceMinutes(getDefaultStandardDate(), frontStartDto.getWorkTypeItemValue());
		}
		int frontEnd = 0;
		if (frontEndDto != null) {
			frontEnd = getDefferenceMinutes(getDefaultStandardDate(), frontEndDto.getWorkTypeItemValue());
		}
		int backStart = 0;
		if (backStartDto != null) {
			backStart = getDefferenceMinutes(getDefaultStandardDate(), backStartDto.getWorkTypeItemValue());
		}
		int backEnd = 0;
		if (backEndDto != null) {
			backEnd = getDefferenceMinutes(getDefaultStandardDate(), backEndDto.getWorkTypeItemValue());
		}
		if (isPmHalfDayOff(requestUtil)) {
			// 午後休の場合
			regWorkStart = frontStart;
			regWorkEnd = frontEnd;
			regWorkTime = frontEnd - frontStart;
			betweenHalfHolidayTime = backStart - frontEnd;
		}
		if (isAmHalfDayOff(requestUtil)) {
			// 午前休の場合
			regWorkStart = backStart;
			regWorkEnd = backEnd;
			regWorkTime = backEnd - backStart;
			betweenHalfHolidayTime = backStart - frontEnd;
		}
	}
	
	/**
	 * 勤務時間の出力
	 * @return 勤務時間
	 */
	public int getWorkTime() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め単位
		return getRoundMinute(workTime, timeSettingDto.getRoundDailyWork(), timeSettingDto.getRoundDailyWorkUnit());
	}
	
	/**
	 * 規定休憩を計算する。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void calcRegRest(RequestUtilBeanInterface requestUtil) throws MospException {
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 法定休日労働又は所定休日労働の場合
			return;
		}
		if (isAmHalfDayOff(requestUtil)) {
			// 午前休の場合
			WorkTypeItemDtoInterface halfRestDto = workTypeItemReference.getWorkTypeItemInfo(workTypeCode, workDate,
					TimeConst.CODE_HALFREST);
			if (halfRestDto == null) {
				return;
			}
			int halfRest = getDefferenceMinutes(getDefaultStandardDate(), halfRestDto.getWorkTypeItemValue());
			if (halfRest <= calculatedStart || calculatedEnd < halfRest) {
				return;
			}
			WorkTypeItemDtoInterface halfRestStartDto = workTypeItemReference.getWorkTypeItemInfo(workTypeCode,
					workDate, TimeConst.CODE_HALFRESTSTART);
			if (halfRestStartDto == null) {
				return;
			}
			WorkTypeItemDtoInterface halfRestEndDto = workTypeItemReference.getWorkTypeItemInfo(workTypeCode, workDate,
					TimeConst.CODE_HALFRESTEND);
			if (halfRestEndDto == null) {
				return;
			}
			int restStart = getDefferenceMinutes(getDefaultStandardDate(), halfRestStartDto.getWorkTypeItemValue());
			int restEnd = getDefferenceMinutes(getDefaultStandardDate(), halfRestEndDto.getWorkTypeItemValue());
			if (restStart == restEnd) {
				return;
			}
			regRestMap.put(getAttendanceTime(workDate, restStart), getAttendanceTime(workDate, restEnd));
			return;
		} else if (isPmHalfDayOff(requestUtil)) {
			// 午後休の場合
			return;
		}
		// 半休でない場合
		String[] restStartArray = new String[]{ TimeConst.CODE_RESTSTART1, TimeConst.CODE_RESTSTART2,
			TimeConst.CODE_RESTSTART3, TimeConst.CODE_RESTSTART4 };
		String[] restEndArray = new String[]{ TimeConst.CODE_RESTEND1, TimeConst.CODE_RESTEND2,
			TimeConst.CODE_RESTEND3, TimeConst.CODE_RESTEND4 };
		for (int i = 0; i < restStartArray.length; i++) {
			WorkTypeItemDtoInterface restStartDto = workTypeItemReference.getWorkTypeItemInfo(workTypeCode, workDate,
					restStartArray[i]);
			if (restStartDto == null) {
				continue;
			}
			WorkTypeItemDtoInterface restEndDto = workTypeItemReference.getWorkTypeItemInfo(workTypeCode, workDate,
					restEndArray[i]);
			if (restEndDto == null) {
				continue;
			}
			int restStart = getDefferenceMinutes(getDefaultStandardDate(), restStartDto.getWorkTypeItemValue());
			int restEnd = getDefferenceMinutes(getDefaultStandardDate(), restEndDto.getWorkTypeItemValue());
			if (restStart == restEnd) {
				continue;
			}
			regRestMap.put(getAttendanceTime(workDate, restStart), getAttendanceTime(workDate, restEnd));
		}
	}
	
	/* 自動計算 */
	/**
	 * 労働時間を計算する。<br>
	 * 合わせて休憩時間及び規定終業時刻後時間を計算する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void calcWorkTime(RequestUtilBeanInterface requestUtil) throws MospException {
		// 初期化
//		overtimeBeforeRestMap = new TreeMap<Date, Date>();
//		overtimeRestMap = new TreeMap<Date, Date>();
		int totalManualRest = 0;
		for (RestDtoInterface dto : restDtoList) {
			totalManualRest += dto.getRestTime();
		}
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		// 遅刻休憩時間
		for (Entry<Date, Date> entry : tardinessRestMap.entrySet()) {
			map.put(getDefferenceMinutes(workDate, entry.getKey()), getDefferenceMinutes(workDate, entry.getValue()));
		}
		// 早退休憩時間
		for (Entry<Date, Date> entry : leaveEarlyRestMap.entrySet()) {
			map.put(getDefferenceMinutes(workDate, entry.getKey()), getDefferenceMinutes(workDate, entry.getValue()));
		}
		// 休憩時間
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (endTimeInt > regWorkStart) {
				if (startTimeInt < regWorkStart) {
					startTimeInt = regWorkStart;
				}
				if (map.containsKey(startTimeInt)) {
					if (map.get(startTimeInt).intValue() < endTimeInt) {
						map.put(startTimeInt, endTimeInt);
					}
				} else {
					map.put(startTimeInt, endTimeInt);
				}
			}
		}
		// 公用外出時間
//		for (GoOutDtoInterface dto : publicGoOutDtoList) {
//			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
//					timeSettingDto.getRoundDailyPublicStartUnit());
//			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(), timeSettingDto
//				.getRoundDailyPublicEndUnit());
//			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
//					+ DateUtility.getMinute(startTime);
//			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
//					+ DateUtility.getMinute(endTime);
//			if (endTimeInt > regWorkStart) {
//				if (startTimeInt < regWorkStart) {
//					startTimeInt = regWorkStart;
//				}
//				if (map.containsKey(startTimeInt)) {
//					if (map.get(startTimeInt).intValue() < endTimeInt) {
//						map.put(startTimeInt, endTimeInt);
//					}
//				} else {
//					map.put(startTimeInt, endTimeInt);
//				}
//			}
//		}
		// 私用外出時間
//		for (GoOutDtoInterface dto : privateGoOutDtoList) {
//			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPrivateStart(),
//					timeSettingDto.getRoundDailyPrivateStartUnit());
//			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPrivateEnd(), timeSettingDto
//				.getRoundDailyPrivateEndUnit());
//			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
//					+ DateUtility.getMinute(startTime);
//			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
//					+ DateUtility.getMinute(endTime);
//			if (endTimeInt > regWorkStart) {
//				if (startTimeInt < regWorkStart) {
//					startTimeInt = regWorkStart;
//				}
//				if (map.containsKey(startTimeInt)) {
//					if (map.get(startTimeInt).intValue() < endTimeInt) {
//						map.put(startTimeInt, endTimeInt);
//					}
//				} else {
//					map.put(startTimeInt, endTimeInt);
//				}
//			}
//		}
		// 分単位休暇A時間
		for (GoOutDtoInterface dto : minutelyHolidayADtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (endTimeInt > regWorkStart) {
				if (startTimeInt < regWorkStart) {
					startTimeInt = regWorkStart;
				}
				if (map.containsKey(startTimeInt)) {
					if (map.get(startTimeInt).intValue() < endTimeInt) {
						map.put(startTimeInt, endTimeInt);
					}
				} else {
					map.put(startTimeInt, endTimeInt);
				}
			}
		}
		// 分単位休暇B時間
		for (GoOutDtoInterface dto : minutelyHolidayBDtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (endTimeInt > regWorkStart) {
				if (startTimeInt < regWorkStart) {
					startTimeInt = regWorkStart;
				}
				if (map.containsKey(startTimeInt)) {
					if (map.get(startTimeInt).intValue() < endTimeInt) {
						map.put(startTimeInt, endTimeInt);
					}
				} else {
					map.put(startTimeInt, endTimeInt);
				}
			}
		}
		// 規定始業時刻準備
		int time = regWorkStart;
		// 労働時間
		int tmpWorkTime = 0;
		int scheduledWorkEndTime = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int mapStartTimeInt = entry.getKey().intValue();
			int mapEndTimeInt = entry.getValue().intValue();
			if (time <= mapStartTimeInt) {
				int addWorkTime = mapStartTimeInt - time;
				if (tmpWorkTime + addWorkTime >= autoRestCalcStart) {
					// 勤務時間に追加勤務時間を加えたものが自動休憩計算開始時刻以上の場合は
					// 時間に自動休憩計算開始時刻から勤務時間を引いたものとする
					scheduledWorkEndTime = time + (autoRestCalcStart - tmpWorkTime);
					break;
				}
				tmpWorkTime += addWorkTime;
				time = mapEndTimeInt;
			}
		}
		if (scheduledWorkEndTime == 0) {
			int remainWorkTime = calculatedEnd - time;
			if (tmpWorkTime + remainWorkTime <= autoRestCalcStart) {
				totalRest = totalManualRest;
				workTime = totalWorkTime(requestUtil);
				return;
			} else {
				scheduledWorkEndTime = time + (autoRestCalcStart - tmpWorkTime);
			}
		}
		if (scheduledWorkEndTime + overbefore >= calculatedEnd) {
			// 終業時刻が残前休憩終了時刻以前の場合
			int overtimeBeforeTime = 0;
			if (scheduledWorkEndTime < calculatedStart) {
				// 勤怠計算上の始業時刻より前の場合
				if (calculatedEnd - calculatedStart > 0) {
					overtimeBeforeTime = calculatedEnd - calculatedStart;
					overtimeBeforeRestMap.put(getAttendanceTime(workDate, calculatedStart),
							getAttendanceTime(workDate, calculatedEnd));
				}
			} else {
				if (calculatedEnd - scheduledWorkEndTime > 0) {
					overtimeBeforeTime = calculatedEnd - scheduledWorkEndTime;
					overtimeBeforeRestMap.put(getAttendanceTime(workDate, scheduledWorkEndTime),
							getAttendanceTime(workDate, calculatedEnd));
				}
			}
			totalRest = totalManualRest + overtimeBeforeTime;
			workTime = totalWorkTime(requestUtil);
			return;
		}
		// 規定終業時刻後時間外労働開始時刻
		int afterOvertimeWorkStart = scheduledWorkEndTime + overbefore;
		// 規定終業時刻後時間外前休憩
		int totalOvertimeBeforeRest = 0;
		if (scheduledWorkEndTime >= calculatedStart) {
			// 勤怠計算上の始業時刻以後の場合
			if (afterOvertimeWorkStart - scheduledWorkEndTime > 0) {
				totalOvertimeBeforeRest += afterOvertimeWorkStart - scheduledWorkEndTime;
				overtimeBeforeRestMap.put(getAttendanceTime(workDate, scheduledWorkEndTime),
						getAttendanceTime(workDate, afterOvertimeWorkStart));
			}
		} else if (afterOvertimeWorkStart >= calculatedStart) {
			if (afterOvertimeWorkStart - calculatedStart > 0) {
				totalOvertimeBeforeRest += afterOvertimeWorkStart - calculatedStart;
				overtimeBeforeRestMap.put(getAttendanceTime(workDate, calculatedStart),
						getAttendanceTime(workDate, afterOvertimeWorkStart));
			}
		}
		// 残業休憩時間
		int totalOvertimeRest = 0;
		if (!isWorkOnLegalDaysOff() && !isWorkOnPrescribedDaysOff()) {
			// 法定休日労働でなく且つ所定休日労働でない場合
			// 残業休憩回数の設定
			int overtimeRestTimes = 0;
			if (overper > 1) {
				overtimeRestTimes = (calculatedEnd - afterOvertimeWorkStart) / overper;
			}
			int previousOvertimeRestEnd = afterOvertimeWorkStart;
			for (int i = 0; i < overtimeRestTimes; i++) {
				int overtimeRestStart = previousOvertimeRestEnd + overper - overrest;
				int overtimeRestEnd = previousOvertimeRestEnd + overper;
				if (overtimeRestStart >= calculatedStart) {
					overtimeRestMap.put(getAttendanceTime(workDate, overtimeRestStart),
							getAttendanceTime(workDate, overtimeRestEnd));
					totalOvertimeRest += overtimeRestEnd - overtimeRestStart;
				}
				previousOvertimeRestEnd = overtimeRestEnd;
			}
		}
		totalRest = totalManualRest + totalOvertimeBeforeRest + totalOvertimeRest;
		workTime = totalWorkTime(requestUtil);
	}
	
	/**
	 * 勤務時間を計算する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤務時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int totalWorkTime(RequestUtilBeanInterface requestUtil) throws MospException {
		// 有給休暇
		int paidLeave = paidLeaveHour * TimeConst.CODE_DEFINITION_HOUR - beforePaidLeaveMinute - afterPaidLeaveMinute;
		// 分単位休暇
		int minutelyHoliday = (totalMinutelyHolidayA + totalMinutelyHolidayB) - beforeMinutelyHolidayMinute
				- afterMinutelyHolidayMinute;
		int result = calculatedEnd - calculatedStart - totalRest - totalPublic - totalPrivate - paidLeave
				- minutelyHoliday;
		if (!isAmHalfDayOff(requestUtil) && useShort1 && isShort1StartTypePay && calculatedStart > short1End) {
			// 前半休でなく且つ時短有給且つ勤怠計算上の始業時刻が時短時間1終了時刻より後の場合は、
			// 時短時間1時間を加算する
			result += short1End - short1Start;
		}
		if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
			// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合は、
			// 時短時間2時間を加算する
			result += short2End - short2Start;
		}
		return result;
	}
	
	/**
	 * 法定外時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void calcLegalOutTime() throws MospException {
		overtimeOut = overtimeTime - withinStatutoryOvertime;
		// 法定外休憩時間設定
		overRestTime = 0;
//		if (overtimeOut == 0) {
//			// 法定時間外労働時間が0の場合
//			return;
//		}
		// 所定休日取扱(true：暦日扱い)
		boolean isSpecificHolidayCalendarDay = timeSettingDto.getSpecificHolidayHandling() == 2;
		if (isWorkOnLegalDaysOff()) {
			// 法定休日労働の場合
			if (isNextDayLegalDaysOff() || isNextDayWorkOnLegalDaysOff()) {
				// 翌日が法定休日又は法定休日労働の場合
				return;
			} else if (isNextDayPrescribedDaysOff() || isNextDayWorkOnPrescribedDaysOff()) {
				// 翌日が所定休日又は所定休日労働の場合
				prescribedHolidayOvertimeIn = withinStatutoryOvertime;
				prescribedHolidayOvertimeOut = overtimeOut;
				workdayOvertimeIn = 0;
				workdayOvertimeOut = 0;
				return;
			}
			// 翌日が平日の場合
			prescribedHolidayOvertimeIn = 0;
			prescribedHolidayOvertimeOut = 0;
			workdayOvertimeIn = withinStatutoryOvertime;
			workdayOvertimeOut = overtimeOut;
			return;
		} else if (isWorkOnPrescribedDaysOff()) {
			// 所定休日労働の場合
			if (isNextDayLegalDaysOff() || isNextDayWorkOnLegalDaysOff() || isNextDayPrescribedDaysOff()
					|| isNextDayWorkOnPrescribedDaysOff()) {
				// 翌日が法定休日、法定休日労働、所定休日又は所定休日労働の場合
				prescribedHolidayOvertimeIn = withinStatutoryOvertime;
				prescribedHolidayOvertimeOut = overtimeOut;
				workdayOvertimeIn = 0;
				workdayOvertimeOut = 0;
				return;
			}
			// 翌日が平日の場合
			if (!isSpecificHolidayCalendarDay) {
				// 所定休日取扱が通常の場合
				prescribedHolidayOvertimeIn = withinStatutoryOvertime;
				prescribedHolidayOvertimeOut = overtimeOut;
				workdayOvertimeIn = 0;
				workdayOvertimeOut = 0;
				return;
			}
			// 所定休日取扱が暦日の場合
			int difference = prescribedHolidayWork - withinStatutoryOvertime;
			if (difference >= 0) {
				// 所定休日労働時間と法定労働時間内時間外労働時間の差が0以上の場合
				prescribedHolidayOvertimeIn = withinStatutoryOvertime;
				prescribedHolidayOvertimeOut = difference;
				workdayOvertimeIn = 0;
				workdayOvertimeOut = overtimeOut - difference;
				return;
			}
			// 所定休日労働時間と法定労働時間内時間外労働時間の差がマイナスの場合
			prescribedHolidayOvertimeIn = prescribedHolidayWork;
			prescribedHolidayOvertimeOut = 0;
			workdayOvertimeIn = withinStatutoryOvertime - prescribedHolidayWork;
			workdayOvertimeOut = overtimeOut;
			return;
		}
		// 平日の場合
		if (isNextDayLegalDaysOff() || isNextDayWorkOnLegalDaysOff()) {
			// 翌日が法定休日又は法定休日労働の場合
			prescribedHolidayOvertimeIn = 0;
			prescribedHolidayOvertimeOut = 0;
			workdayOvertimeIn = withinStatutoryOvertime;
			workdayOvertimeOut = overtimeOut;
			return;
		} else if (isNextDayPrescribedDaysOff() || isNextDayWorkOnPrescribedDaysOff()) {
			// 翌日が所定休日又は所定休日労働の場合
			if (!isSpecificHolidayCalendarDay) {
				// 所定休日取扱が通常の場合
				prescribedHolidayOvertimeIn = 0;
				prescribedHolidayOvertimeOut = 0;
				workdayOvertimeIn = withinStatutoryOvertime;
				workdayOvertimeOut = overtimeOut;
				return;
			}
			// 所定休日取扱が暦日の場合
			if (withinStatutoryOvertime >= workBeforeTime) {
				// 法定労働時間内所定労働時間外時間が規定始業時刻前時間以上の場合
				int difference = overtimeOut - prescribedHolidayWork;
				if (difference >= 0) {
					// 法定労働時間外時間と所定休日労働時間の差が0以上の場合
					prescribedHolidayOvertimeIn = 0;
					prescribedHolidayOvertimeOut = prescribedHolidayWork;
					workdayOvertimeIn = withinStatutoryOvertime;
					workdayOvertimeOut = difference;
					return;
				}
				// 法定労働時間外時間と所定休日労働時間の差がマイナスの場合
				prescribedHolidayOvertimeOut = overtimeOut;
				workdayOvertimeOut = 0;
				if (prescribedHolidayWork - overtimeOut >= withinStatutoryOvertime - workBeforeTime) {
					// 所定休日労働時間から法定労働時間外時間を引いたものが
					// 法定労働時間内時間外時間から規定始業時刻前時間を引いたもの以上の場合
					prescribedHolidayOvertimeIn = withinStatutoryOvertime - workBeforeTime;
					workdayOvertimeIn = workBeforeTime;
					return;
				}
				prescribedHolidayOvertimeIn = prescribedHolidayWork - overtimeOut;
				workdayOvertimeIn = withinStatutoryOvertime - prescribedHolidayOvertimeIn;
				return;
			}
			// 法定労働時間内所定労働時間外時間が規定始業時刻前時間未満の場合
			prescribedHolidayOvertimeIn = 0;
			workdayOvertimeIn = withinStatutoryOvertime;
			int difference = overtimeOut - (workBeforeTime - withinStatutoryOvertime) - prescribedHolidayWork;
			if (difference >= 0) {
				// 差が0以上の場合
				prescribedHolidayOvertimeOut = prescribedHolidayWork;
				workdayOvertimeOut = overtimeOut - prescribedHolidayWork;
				return;
			}
			// 差がマイナスの場合
			prescribedHolidayOvertimeOut = overtimeOut - (workBeforeTime - withinStatutoryOvertime);
			workdayOvertimeOut = workBeforeTime - withinStatutoryOvertime;
			return;
		}
		// 翌日が平日の場合
		prescribedHolidayOvertimeIn = 0;
		prescribedHolidayOvertimeOut = 0;
		workdayOvertimeIn = withinStatutoryOvertime;
		workdayOvertimeOut = overtimeOut;
	}
	
	/**
	 * 公用外出時間の計算
	 */
	private void calcPublicGoOutTime() {
		int total = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			total += dto.getGoOutTime();
		}
		totalPublic = total;
	}
	
	/**
	 * 私用外出時間の計算
	 */
	private void calcPrivateGoOutTime() {
		int total = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			total += dto.getGoOutTime();
		}
		totalPrivate = total;
	}
	
	/**
	 * 分単位休暇A時間の計算
	 */
	private void calcMinutelyHolidayATime() {
		int total = 0;
		for (GoOutDtoInterface dto : minutelyHolidayADtoList) {
			minutelyHolidayAMap.put(dto.getGoOutStart(), dto.getGoOutEnd());
			total += dto.getGoOutTime();
		}
		totalMinutelyHolidayA = total;
	}
	
	/**
	 * 始業時刻前分単位休暇AB分数を設定
	 */
	protected void calcBeforeMinutelyHolidayMinute() {
		// 分単位休暇A
		int minutelyHolidayA = 0;
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayStart >= calculatedStart) {
				// 分単位休暇A開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 分単位休暇A開始時刻が勤怠計算上の始業時刻より前の場合
			if (minutelyHolidayEnd > calculatedStart) {
				// 分単位休暇A終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を分単位休暇A終了時刻とする
				minutelyHolidayEnd = calculatedStart;
			}
			minutelyHolidayA += minutelyHolidayEnd - minutelyHolidayStart;
		}
		// 分単位休暇B
		int minutelyHolidayB = 0;
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayStart >= calculatedStart) {
				// 分単位休暇B開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 分単位休暇B開始時刻が勤怠計算上の始業時刻より前の場合
			if (minutelyHolidayEnd > calculatedStart) {
				// 分単位休暇B終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を分単位休暇終了時刻とする
				minutelyHolidayEnd = calculatedStart;
			}
			minutelyHolidayB += minutelyHolidayEnd - minutelyHolidayStart;
		}
		// 始業時刻前分単位休暇AB分数を設定
		beforeMinutelyHolidayMinute = minutelyHolidayA + minutelyHolidayB;
	}
	
	/**
	 * 始業時刻後分単位休暇分数を設定。
	 */
	protected void calcAfterMinutelyHolidayMinute() {
		// 分単位休暇
		int minutelyHolidayA = 0;
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayEnd <= calculatedEnd) {
				// 分単位休暇終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 分単位休暇終了時刻が勤怠計算上の始業時刻より後の場合
			if (minutelyHolidayStart < calculatedEnd) {
				// 分単位休暇開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を分単位休暇開始時刻とする
				minutelyHolidayStart = calculatedEnd;
			}
			minutelyHolidayA += minutelyHolidayEnd - minutelyHolidayStart;
		}
		
		// 分単位休暇
		int minutelyHolidayB = 0;
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayEnd <= calculatedEnd) {
				// 分単位休暇終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 分単位休暇終了時刻が勤怠計算上の始業時刻より後の場合
			if (minutelyHolidayStart < calculatedEnd) {
				// 分単位休暇開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を分単位休暇開始時刻とする
				minutelyHolidayStart = calculatedEnd;
			}
			minutelyHolidayB += minutelyHolidayEnd - minutelyHolidayStart;
		}
		
		// 始業時刻後分単位休暇分数を設定
		afterMinutelyHolidayMinute = minutelyHolidayA + minutelyHolidayB;
	}
	
	/**
	 * 分単位休暇B時間の計算
	 */
	private void calcMinutelyHolidayBTime() {
		int total = 0;
		for (GoOutDtoInterface dto : minutelyHolidayBDtoList) {
			minutelyHolidayBMap.put(dto.getGoOutStart(), dto.getGoOutEnd());
			total += dto.getGoOutTime();
		}
		totalMinutelyHolidayB = total;
	}
	
	/**
	 * 遅刻時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void calcTardinessTime(RequestUtilBeanInterface requestUtil) throws MospException {
		// 法定休日労動又は所定休日労動の場合
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			return;
		}
		// 分単位休暇全休の場合
		if (allMinutelyHolidayA || allMinutelyHolidayB) {
			return;
		}
		int exclusion = 0;
		// 休憩
		for (Entry<Date, Date> entry : regRestMap.entrySet()) {
			int restStart = getDefferenceMinutes(workDate, entry.getKey());
			int restEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (restStart >= calculatedStart) {
				// 休憩開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 休憩開始時刻が勤怠計算上の始業時刻より前の場合
			if (restEnd > calculatedStart) {
				// 休憩終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を休憩終了時刻とする
				restEnd = calculatedStart;
			}
			exclusion += restEnd - restStart;
			// 遅刻休憩時間マップ設定
			tardinessRestMap.put(getAttendanceTime(workDate, restStart), getAttendanceTime(workDate, restEnd));
		}
		// 分単位休暇A
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayStart >= calculatedStart) {
				// 分単位休暇A開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 分単位休暇A開始時刻が勤怠計算上の始業時刻より前の場合
			if (minutelyHolidayEnd > calculatedStart) {
				// 分単位休暇A終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を分単位休暇A終了時刻とする
				minutelyHolidayEnd = calculatedStart;
			}
			for (Entry<Date, Date> restEntry : tardinessRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (minutelyHolidayStart >= restStart && restEnd >= minutelyHolidayEnd) {
					// 分単位休暇A開始時刻が休憩開始時刻以後且つ分単位休暇A終了時刻が休憩終了時刻以前の場合
					minutelyHolidayStart = 0;
					minutelyHolidayEnd = 0;
					break;
				}
				if (minutelyHolidayStart >= restEnd) {
					// 分単位休暇A開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (minutelyHolidayEnd <= restStart) {
					// 分単位休暇A終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (minutelyHolidayStart < restStart && restEnd < minutelyHolidayEnd) {
					// 分単位休暇A開始時刻が休憩開始時刻より前且つ分単位休暇A終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から分単位休暇A開始時刻を引いたものを加算
					exclusion += restStart - minutelyHolidayStart;
					// 休憩終了時刻を分単位休暇A開始時刻とする
					minutelyHolidayStart = restEnd;
					continue;
				}
				if (minutelyHolidayStart >= restStart && minutelyHolidayEnd > restEnd) {
					// 分単位休暇A開始時刻が休憩開始時刻以後且つ分単位休暇A終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を分単位休暇A開始時刻とする
					minutelyHolidayStart = restEnd;
				}
				if (minutelyHolidayStart < restStart && minutelyHolidayEnd <= restEnd) {
					// 分単位休暇A開始時刻が休憩開始時刻より前且つ分単位休暇A終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を分単位休暇A終了時刻とする
					minutelyHolidayEnd = restStart;
				}
			}
			exclusion += minutelyHolidayEnd - minutelyHolidayStart;
		}
		// 分単位休暇B
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayStart >= calculatedStart) {
				// 分単位休暇B開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 分単位休暇B開始時刻が勤怠計算上の始業時刻より前の場合
			if (minutelyHolidayEnd > calculatedStart) {
				// 分単位休暇B終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を分単位休暇終了時刻とする
				minutelyHolidayEnd = calculatedStart;
			}
			for (Entry<Date, Date> restEntry : tardinessRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (minutelyHolidayStart >= restStart && restEnd >= minutelyHolidayEnd) {
					// 分単位休暇B開始時刻が休憩開始時刻以後且つ分単位休暇B終了時刻が休憩終了時刻以前の場合
					minutelyHolidayStart = 0;
					minutelyHolidayEnd = 0;
					break;
				}
				if (minutelyHolidayStart >= restEnd) {
					// 分単位休暇B開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (minutelyHolidayEnd <= restStart) {
					// 分単位休暇B終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (minutelyHolidayStart < restStart && restEnd < minutelyHolidayEnd) {
					// 分単位休暇B開始時刻が休憩開始時刻より前且つ分単位休暇B終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から分単位休暇B開始時刻を引いたものを加算
					exclusion += restStart - minutelyHolidayStart;
					// 休憩終了時刻を分単位休暇B開始時刻とする
					minutelyHolidayStart = restEnd;
					continue;
				}
				if (minutelyHolidayStart >= restStart && minutelyHolidayEnd > restEnd) {
					// 分単位休暇B開始時刻が休憩開始時刻以後且つ分単位休暇B終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を有給休暇開始時刻とする
					minutelyHolidayStart = restEnd;
				}
				if (minutelyHolidayStart < restStart && minutelyHolidayEnd <= restEnd) {
					// 分単位休暇B開始時刻が休憩開始時刻より前且つ分単位休暇B終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を分単位休暇B終了時刻とする
					minutelyHolidayEnd = restStart;
				}
			}
			exclusion += minutelyHolidayEnd - minutelyHolidayStart;
		}
		// 始業時刻前分単位休暇AB分数を設定
		calcBeforeMinutelyHolidayMinute();
		
		// 有給休暇
		int paidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int paidLeaveStart = getDefferenceMinutes(workDate, entry.getKey());
			int paidLeaveEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (paidLeaveStart >= calculatedStart) {
				// 有給休暇開始時刻が勤怠計算上の始業時刻以後の場合
				continue;
			}
			// 有給休暇開始時刻が勤怠計算上の始業時刻より前の場合
			if (paidLeaveEnd > calculatedStart) {
				// 有給休暇終了時刻が勤怠計算上の始業時刻より後の場合は
				// 勤怠計算上の始業時刻を有給休暇終了時刻とする
				paidLeaveEnd = calculatedStart;
			}
			paidLeave += paidLeaveEnd - paidLeaveStart;
			for (Entry<Date, Date> restEntry : tardinessRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (paidLeaveStart >= restStart && restEnd >= paidLeaveEnd) {
					// 有給休暇開始時刻が休憩開始時刻以後且つ有給休暇終了時刻が休憩終了時刻以前の場合
					paidLeaveStart = 0;
					paidLeaveEnd = 0;
					break;
				}
				if (paidLeaveStart >= restEnd) {
					// 有給休暇開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (paidLeaveEnd <= restStart) {
					// 有給休暇終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (paidLeaveStart < restStart && restEnd < paidLeaveEnd) {
					// 有給休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から有給休暇開始時刻を引いたものを加算
					exclusion += restStart - paidLeaveStart;
					// 休憩終了時刻を有給休暇開始時刻とする
					paidLeaveStart = restEnd;
					continue;
				}
				if (paidLeaveStart >= restStart && paidLeaveEnd > restEnd) {
					// 有給休暇開始時刻が休憩開始時刻以後且つ有給休暇終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を有給休暇開始時刻とする
					paidLeaveStart = restEnd;
				}
				if (paidLeaveStart < restStart && paidLeaveEnd <= restEnd) {
					// 有給休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を有給休暇終了時刻とする
					paidLeaveEnd = restStart;
				}
			}
			exclusion += paidLeaveEnd - paidLeaveStart;
		}
		// 始業時刻前有給休暇分数を設定
		beforePaidLeaveMinute = paidLeave;
		int tardinessWorkStart = calculatedStart;
		int tardinessRegWorkStart = regWorkStart;
		if (!isAmHalfDayOff(requestUtil) && useShort1) {
			// 前半休でなく且つ時短の場合は時短時間1終了時刻とする
			tardinessRegWorkStart = short1End;
		}
		if (!isPmHalfDayOff(requestUtil) && useShort2 && calculatedStart > short2Start) {
			// 後半休でなく且つ時短且つ勤怠計算上の始業時刻が時短2開始時刻より後の場合は、
			// 時短時間2開始時刻とする
			tardinessWorkStart = short2Start;
		}
		int result = tardinessWorkStart - tardinessRegWorkStart - exclusion;
		if (result < 0) {
			// 0未満の場合は0とする
			result = 0;
		}
		if (result > prescribedWorkTime) {
			// 所定労働時間より大きい場合は所定労働時間とする
			result = prescribedWorkTime;
		}
		lateTime = getRoundMinute(result, timeSettingDto.getRoundDailyLate(), timeSettingDto.getRoundDailyLateUnit());
	}
	
	/**
	 * 早退時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void calcLeaveEarlyTime(RequestUtilBeanInterface requestUtil) throws MospException {
		// 法定休日労動又は所定休日労動の場合
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			return;
		}
		// 分単位休暇全休の場合
		if (allMinutelyHolidayA || allMinutelyHolidayB) {
			return;
		}
		int exclusion = 0;
		// 休憩
		for (Entry<Date, Date> entry : regRestMap.entrySet()) {
			int restStart = getDefferenceMinutes(workDate, entry.getKey());
			int restEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (restEnd <= calculatedEnd) {
				// 休憩終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 休憩終了時刻が勤怠計算上の終業時刻より後の場合
			if (restStart < calculatedEnd) {
				// 休憩開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を休憩開始時刻とする
				restStart = calculatedEnd;
			}
			exclusion += restEnd - restStart;
			leaveEarlyRestMap.put(getAttendanceTime(workDate, restStart), getAttendanceTime(workDate, restEnd));
		}
		// 分単位休暇
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayEnd <= calculatedEnd) {
				// 分単位休暇終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 分単位休暇終了時刻が勤怠計算上の始業時刻より後の場合
			if (minutelyHolidayStart < calculatedEnd) {
				// 分単位休暇開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を分単位休暇開始時刻とする
				minutelyHolidayStart = calculatedEnd;
			}
			for (Entry<Date, Date> restEntry : leaveEarlyRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (minutelyHolidayStart >= restStart && restEnd >= minutelyHolidayEnd) {
					// 分単位休暇開始時刻が休憩開始時刻以後且つ分単位休暇終了時刻が休憩終了時刻以前の場合
					minutelyHolidayStart = 0;
					minutelyHolidayEnd = 0;
					break;
				}
				if (minutelyHolidayStart >= restEnd) {
					// 分単位休暇開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (minutelyHolidayEnd <= restStart) {
					// 分単位休暇終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (minutelyHolidayStart < restStart && restEnd < minutelyHolidayEnd) {
					// 分単位休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から分単位休暇開始時刻を引いたものを加算
					exclusion += restStart - minutelyHolidayStart;
					// 休憩終了時刻を分単位休暇開始時刻とする
					minutelyHolidayStart = restEnd;
					continue;
				}
				if (minutelyHolidayStart >= restStart && minutelyHolidayEnd > restEnd) {
					// 分単位休暇開始時刻が休憩開始時刻以後且つ分単位休暇終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を分単位休暇開始時刻とする
					minutelyHolidayStart = restEnd;
				}
				if (minutelyHolidayStart < restStart && minutelyHolidayEnd <= restEnd) {
					// 分単位休暇開始時刻が休憩開始時刻より前且つ分単位休暇終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を分単位休暇終了時刻とする
					minutelyHolidayEnd = restStart;
				}
			}
			exclusion += minutelyHolidayEnd - minutelyHolidayStart;
		}
		
		// 分単位休暇
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
			int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (minutelyHolidayEnd <= calculatedEnd) {
				// 分単位休暇終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 分単位休暇終了時刻が勤怠計算上の始業時刻より後の場合
			if (minutelyHolidayStart < calculatedEnd) {
				// 分単位休暇開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を分単位休暇開始時刻とする
				minutelyHolidayStart = calculatedEnd;
			}
			for (Entry<Date, Date> restEntry : leaveEarlyRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (minutelyHolidayStart >= restStart && restEnd >= minutelyHolidayEnd) {
					// 分単位休暇開始時刻が休憩開始時刻以後且つ分単位休暇終了時刻が休憩終了時刻以前の場合
					minutelyHolidayStart = 0;
					minutelyHolidayEnd = 0;
					break;
				}
				if (minutelyHolidayStart >= restEnd) {
					// 分単位休暇開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (minutelyHolidayEnd <= restStart) {
					// 分単位休暇終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (minutelyHolidayStart < restStart && restEnd < minutelyHolidayEnd) {
					// 分単位休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から分単位休暇開始時刻を引いたものを加算
					exclusion += restStart - minutelyHolidayStart;
					// 休憩終了時刻を分単位休暇開始時刻とする
					minutelyHolidayStart = restEnd;
					continue;
				}
				if (minutelyHolidayStart >= restStart && minutelyHolidayEnd > restEnd) {
					// 分単位休暇開始時刻が休憩開始時刻以後且つ分単位休暇終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を分単位休暇開始時刻とする
					minutelyHolidayStart = restEnd;
				}
				if (minutelyHolidayStart < restStart && minutelyHolidayEnd <= restEnd) {
					// 分単位休暇開始時刻が休憩開始時刻より前且つ分単位休暇終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を分単位休暇終了時刻とする
					minutelyHolidayEnd = restStart;
				}
			}
			exclusion += minutelyHolidayEnd - minutelyHolidayStart;
		}
		// 始業時刻後分単位休暇分数を設定
		calcAfterMinutelyHolidayMinute();
		
		// 有休休暇
		int paidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int paidLeaveStart = getDefferenceMinutes(workDate, entry.getKey());
			int paidLeaveEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (paidLeaveEnd <= calculatedEnd) {
				// 有給休暇終了時刻が勤怠計算上の終業時刻以前の場合
				continue;
			}
			// 有給休暇終了時刻が勤怠計算上の始業時刻より後の場合
			if (paidLeaveStart < calculatedEnd) {
				// 有給休暇開始時刻が勤怠計算上の終業時刻より前の場合は
				// 勤怠計算上の終業時刻を有給休暇開始時刻とする
				paidLeaveStart = calculatedEnd;
			}
			paidLeave += paidLeaveEnd - paidLeaveStart;
			for (Entry<Date, Date> restEntry : leaveEarlyRestMap.entrySet()) {
				int restStart = getDefferenceMinutes(workDate, restEntry.getKey());
				int restEnd = getDefferenceMinutes(workDate, restEntry.getValue());
				if (paidLeaveStart >= restStart && restEnd >= paidLeaveEnd) {
					// 有給休暇開始時刻が休憩開始時刻以後且つ有給休暇終了時刻が休憩終了時刻以前の場合
					paidLeaveStart = 0;
					paidLeaveEnd = 0;
					break;
				}
				if (paidLeaveStart >= restEnd) {
					// 有給休暇開始時刻が休憩終了時刻以後の場合
					continue;
				}
				if (paidLeaveEnd <= restStart) {
					// 有給休暇終了時刻が休憩開始時刻以前の場合
					continue;
				}
				if (paidLeaveStart < restStart && restEnd < paidLeaveEnd) {
					// 有給休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻より後の場合
					// 休憩開始時刻から有給休暇開始時刻を引いたものを加算
					exclusion += restStart - paidLeaveStart;
					// 休憩終了時刻を有給休暇開始時刻とする
					paidLeaveStart = restEnd;
					continue;
				}
				if (paidLeaveStart >= restStart && paidLeaveEnd > restEnd) {
					// 有給休暇開始時刻が休憩開始時刻以後且つ有給休暇終了時刻が休憩終了時刻より後の場合は
					// 休憩終了時刻を有給休暇開始時刻とする
					paidLeaveStart = restEnd;
				}
				if (paidLeaveStart < restStart && paidLeaveEnd <= restEnd) {
					// 有給休暇開始時刻が休憩開始時刻より前且つ有給休暇終了時刻が休憩終了時刻以前の場合は
					// 休憩開始時刻を有給休暇終了時刻とする
					paidLeaveEnd = restStart;
				}
			}
			exclusion += paidLeaveEnd - paidLeaveStart;
		}
		// 始業時刻後有給休暇分数を設定
		afterPaidLeaveMinute = paidLeave;
		int leaveEarlyWorkEnd = calculatedEnd;
		int leaveEarlyRegWorkEnd = regWorkEnd;
		if (!isAmHalfDayOff(requestUtil) && useShort1 && calculatedEnd < short1End) {
			// 前半休でなく且つ時短且つ勤怠計算上の終業時刻が時短1終了時刻より前の場合は、
			// 時短時間1終了時刻とする
			leaveEarlyWorkEnd = short1End;
		}
		if (!isPmHalfDayOff(requestUtil) && useShort2) {
			// 後半休でなく且つ時短の場合は時短時間2開始時刻とする
			leaveEarlyRegWorkEnd = short2Start;
		}
		int result = leaveEarlyRegWorkEnd - leaveEarlyWorkEnd - exclusion;
		if (result < 0) {
			// 0未満の場合は0とする
			result = 0;
		}
		if (result > prescribedWorkTime) {
			// 所定労働時間より大きい場合は所定労働時間とする
			result = prescribedWorkTime;
		}
		leaveEarlyTime = getRoundMinute(result, timeSettingDto.getRoundDailyLeaveEarly(),
				timeSettingDto.getRoundDailyLeaveEarlyUnit());
	}
	
	/**
	 * 残業時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void calcOvertimeWork(RequestUtilBeanInterface requestUtil) throws MospException {
		// 翌日の勤務形態コードが法定休日労働又は法定休日
		boolean nextDayWorkOnLegalDaysOff = isNextDayWorkOnLegalDaysOff() || isNextDayLegalDaysOff();
		if (isWorkOnLegalDaysOff() && nextDayWorkOnLegalDaysOff) {
			// 法定休日労働且つ翌日が法定休日又は法定休日労働の場合
			return;
		}
		int twentyFourHours = TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR;
		// 24時以前の合計有給休暇時間
		int before24HourPaidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= twentyFourHours) {
				// 有給休暇開始時刻が24時以後の場合
				continue;
			}
			if (end <= calculatedStart) {
				// 有給休暇終了時刻が勤怠計算上の始業時刻以前の場合
				continue;
			}
			if (start < calculatedStart) {
				// 有給休暇開始時刻が勤怠計算上の始業時刻より前の場合は
				// 勤怠計算上の始業時刻を有給休暇開始時刻とする
				start = calculatedStart;
			}
			if (end > twentyFourHours) {
				// 有給休暇終了時刻が24時より後の場合は
				// 24時を有給休暇終了時刻とする
				end = twentyFourHours;
			}
			before24HourPaidLeave += end - start;
		}
		totalBefore24HourPaidLeave = before24HourPaidLeave;
		// 24時以前の合計手動休憩時間
		int before24HourManualRest = 0;
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < twentyFourHours) {
				if (end > twentyFourHours) {
					end = twentyFourHours;
				}
				before24HourManualRest += getRoundMinute(end - start, timeSettingDto.getRoundDailyRestTime(),
						timeSettingDto.getRoundDailyRestTimeUnit());
			}
		}
		totalBefore24HourManualRest = before24HourManualRest;
		// 24時以前の規定終業時刻後時間外前休憩時間
		int before24HourOvertimeBeforeRest = 0;
		for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start < twentyFourHours) {
				if (end > twentyFourHours) {
					end = twentyFourHours;
				}
				before24HourOvertimeBeforeRest += end - start;
			}
		}
		totalBefore24HourOvertimeBeforeRest = before24HourOvertimeBeforeRest;
		// 24時以前の合計残業休憩時間
		int before24HourOvertimeRest = 0;
		for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start < twentyFourHours) {
				if (end > twentyFourHours) {
					end = twentyFourHours;
				}
				before24HourOvertimeRest += end - start;
			}
		}
		totalBefore24HourOvertimeRest = before24HourOvertimeRest;
		// 24時以前の合計公用外出時間
		int before24HourPublicGoOut = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < twentyFourHours) {
				if (end > twentyFourHours) {
					end = twentyFourHours;
				}
				before24HourPublicGoOut += end - start;
			}
		}
		totalBefore24HourPublicGoOut = before24HourPublicGoOut;
		// 24時以前の合計私用外出時間
		int before24HourPrivateGoOut = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPrivateStart(),
					timeSettingDto.getRoundDailyPrivateStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPrivateEnd(),
					timeSettingDto.getRoundDailyPrivateEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < twentyFourHours) {
				if (end > twentyFourHours) {
					end = twentyFourHours;
				}
				before24HourPrivateGoOut += end - start;
			}
		}
		totalBefore24HourPrivateGoOut = before24HourPrivateGoOut;
		// 始業時刻前分単位休暇AB分数を設定
		calcBeforeMinutelyHolidayMinute();
		// 始業時刻後分単位休暇分数を設定
		calcAfterMinutelyHolidayMinute();
		// 24時以前の合計分単位休暇A時間
		int before24HourMinutelyHolidayA = 0;
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= twentyFourHours) {
				// 分単位休暇A開始時刻が24時以後の場合
				continue;
			}
			if (end <= calculatedStart) {
				// 分単位休暇A終了時刻が勤怠計算上の始業時刻以前の場合
				continue;
			}
			if (start < calculatedStart) {
				// 分単位休暇A開始時刻が勤怠計算上の始業時刻より前の場合は
				// 勤怠計算上の始業時刻を分単位休暇A開始時刻とする
				start = calculatedStart;
			}
			if (end > twentyFourHours) {
				// 分単位休暇A終了時刻が24時より後の場合は
				// 24時を分単位休暇A終了時刻とする
				end = twentyFourHours;
			}
			before24HourMinutelyHolidayA += end - start;
		}
		totalBefore24HourMinutelyHolidayA = before24HourMinutelyHolidayA;
		// 24時以前の合計分単位休暇B時間
		int before24HourMinutelyHolidayB = 0;
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= twentyFourHours) {
				// 分単位休暇B開始時刻が24時以後の場合
				continue;
			}
			if (end <= calculatedStart) {
				// 分単位休暇B終了時刻が勤怠計算上の始業時刻以前の場合
				continue;
			}
			if (start < calculatedStart) {
				// 分単位休暇B開始時刻が勤怠計算上の始業時刻より前の場合は
				// 勤怠計算上の始業時刻を分単位休暇A開始時刻とする
				start = calculatedStart;
			}
			if (end > twentyFourHours) {
				// 分単位休暇B終了時刻が24時より後の場合は
				// 24時を分単位休暇B終了時刻とする
				end = twentyFourHours;
			}
			before24HourMinutelyHolidayB += end - start;
		}
		totalBefore24HourMinutelyHolidayB = before24HourMinutelyHolidayB;
		// 24時以後の合計有給休暇時間
		int after24HourPaidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= calculatedEnd) {
				// 有給休暇開始時刻が勤怠計算上の終業時刻以後の場合
				continue;
			}
			if (end <= twentyFourHours) {
				// 有給休暇終了時刻が24時以前の場合
				continue;
			}
			if (start < twentyFourHours) {
				// 有給休暇開始時刻が24時より前の場合は
				// 24時を有給休暇開始時刻とする
				start = twentyFourHours;
			}
			if (end > calculatedEnd) {
				// 有給休暇終了時刻が勤怠計算上の終業時刻より後の場合は
				// 勤怠計算上の終業時刻を有給休暇終了時刻とする
			}
			after24HourPaidLeave += end - start;
		}
		totalAfter24HourPaidLeave = after24HourPaidLeave;
		// 24時以後の合計手動休憩時間
		int after24HourManualRest = 0;
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (end > twentyFourHours) {
				if (start < twentyFourHours) {
					start = twentyFourHours;
				}
				after24HourManualRest += getRoundMinute(end - start, timeSettingDto.getRoundDailyRestTime(),
						timeSettingDto.getRoundDailyRestTimeUnit());
			}
		}
		totalAfter24HourManualRest = after24HourManualRest;
		// 24時以後の規定終業時刻後時間外前休憩時間
		int after24HourOvertimeBeforeRest = 0;
		for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (end > twentyFourHours) {
				if (start < twentyFourHours) {
					start = twentyFourHours;
				}
				after24HourOvertimeBeforeRest += end - start;
			}
		}
		totalAfter24HourOvertimeBeforeRest = after24HourOvertimeBeforeRest;
		// 24時以後の合計残業休憩時間
		int after24HourOvertimeRest = 0;
		for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (end > twentyFourHours) {
				if (start < twentyFourHours) {
					start = twentyFourHours;
				}
				after24HourOvertimeRest += end - start;
			}
		}
		totalAfter24HourOvertimeRest = after24HourOvertimeRest;
		// 24時以後の合計公用外出時間
		int after24HourPublicGoOut = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (end > twentyFourHours) {
				if (start < twentyFourHours) {
					start = twentyFourHours;
				}
				after24HourPublicGoOut += end - start;
			}
		}
		totalAfter24HourPublicGoOut = after24HourPublicGoOut;
		// 24時以後の合計私用外出時間
		int after24HourPrivateGoOut = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPrivateStart(),
					timeSettingDto.getRoundDailyPrivateStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPrivateEnd(),
					timeSettingDto.getRoundDailyPrivateEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (end > twentyFourHours) {
				if (start < twentyFourHours) {
					start = twentyFourHours;
				}
				after24HourPrivateGoOut += end - start;
			}
		}
		totalAfter24HourPrivateGoOut = after24HourPrivateGoOut;
		// 24時以後の合計分単位休暇A時間
		int after24HourMinutelyHolidayA = 0;
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= calculatedEnd) {
				// 分単位休暇A開始時刻が勤怠計算上の終業時刻以後の場合
				continue;
			}
			if (end <= twentyFourHours) {
				// 分単位休暇A終了時刻が24時以前の場合
				continue;
			}
			if (start < twentyFourHours) {
				// 分単位休暇A開始時刻が24時より前の場合は
				// 24時を分単位休暇A開始時刻とする
				start = twentyFourHours;
			}
			if (end > calculatedEnd) {
				// 分単位休暇A終了時刻が勤怠計算上の終業時刻より後の場合は
				// 勤怠計算上の終業時刻を分単位休暇A終了時刻とする
			}
			after24HourMinutelyHolidayA += end - start;
		}
		totalAfter24HourMinutelyHolidayA = after24HourMinutelyHolidayA;
		// 24時以後の合計分単位休暇B時間
		int after24HourMinutelyHolidayB = 0;
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start >= calculatedEnd) {
				// 分単位休暇B開始時刻が勤怠計算上の終業時刻以後の場合
				continue;
			}
			if (end <= twentyFourHours) {
				// 分単位休暇B終了時刻が24時以前の場合
				continue;
			}
			if (start < twentyFourHours) {
				// 分単位休暇B開始時刻が24時より前の場合は
				// 24時を分単位休暇B開始時刻とする
				start = twentyFourHours;
			}
			if (end > calculatedEnd) {
				// 分単位休暇B終了時刻が勤怠計算上の終業時刻より後の場合は
				// 勤怠計算上の終業時刻を分単位休暇B終了時刻とする
			}
			after24HourMinutelyHolidayB += end - start;
		}
		totalAfter24HourMinutelyHolidayB = after24HourMinutelyHolidayB;
		
		if ((!isWorkOnLegalDaysOff() && calculatedEnd <= twentyFourHours)
				|| (!isWorkOnLegalDaysOff() && !nextDayWorkOnLegalDaysOff)) {
			// 法定休日労働でなく且つ勤怠計算上の終業時刻が24時以前の場合、
			// 又は法定休日労働でなく且つ翌日が法定休日又は法定休日労働でない場合
			// 合計手動休憩時間
			int totalManualRest = 0;
			for (RestDtoInterface dto : restDtoList) {
				totalManualRest += dto.getRestTime();
			}
			// 規定終業時刻後時間外前休憩時間
			int totalOvertimeBeforeRest = 0;
			for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
				int start = getDefferenceMinutes(workDate, entry.getKey());
				int end = getDefferenceMinutes(workDate, entry.getValue());
				totalOvertimeBeforeRest += end - start;
			}
			// 合計残業休憩時間
			int totalOvertimeRest = 0;
			for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
				int start = getDefferenceMinutes(workDate, entry.getKey());
				int end = getDefferenceMinutes(workDate, entry.getValue());
				totalOvertimeRest += end - start;
			}
			// 残業時間設定
			int overtime = calcOverTime(totalManualRest, totalOvertimeRest, totalOvertimeBeforeRest, totalPublic,
					totalPrivate, beforeMinutelyHolidayMinute, afterMinutelyHolidayMinute, requestUtil);
			// 残業時間の設定
			overtimeTime = getRoundMinute(overtime, timeSettingDto.getRoundDailyWork(),
					timeSettingDto.getRoundDailyWorkUnit());
		} else if (!isWorkOnLegalDaysOff() && nextDayWorkOnLegalDaysOff && calculatedEnd > twentyFourHours) {
			// 法定休日労働でなく且つ翌日が法定休日又は法定休日労働且つ勤怠計算上の終業時刻が24時より後の場合
			int overtime = getcalcOvertimeWorkCaseWorkDay(calculatedStart, totalBefore24HourManualRest,
					totalBefore24HourOvertimeBeforeRest, totalBefore24HourOvertimeRest, totalBefore24HourPublicGoOut,
					totalBefore24HourPrivateGoOut, totalBefore24HourMinutelyHolidayA,
					totalBefore24HourMinutelyHolidayB, prescribedWorkTime, lateTime, leaveEarlyTime, requestUtil);
			if (overtime < 0) {
				// 0未満の場合は0とする
				overtime = 0;
			}
			overtimeTime = getRoundMinute(overtime, timeSettingDto.getRoundDailyWork(),
					timeSettingDto.getRoundDailyWorkUnit());
		} else if (isWorkOnLegalDaysOff() && !nextDayWorkOnLegalDaysOff && calculatedEnd > twentyFourHours) {
			// 法定休日労働且つ翌日が法定休日又は法定休日労働でなく且つ勤怠計算上の終業時刻が24時より後の場合
			int nextDayWork = getNextDayTime(calculatedEnd, totalAfter24HourManualRest,
					totalAfter24HourOvertimeBeforeRest, totalAfter24HourOvertimeRest, totalAfter24HourPublicGoOut,
					totalAfter24HourPrivateGoOut);
			if (nextDayWork < 0) {
				// 0未満の場合は0とする
				nextDayWork = 0;
			}
			overtimeTime = getRoundMinute(nextDayWork, timeSettingDto.getRoundDailyWork(),
					timeSettingDto.getRoundDailyWorkUnit());
		}
		int afterTime = overtimeTime - workBeforeTime;
		if (afterTime < 0) {
			afterTime = 0;
		}
		workAfterTime = afterTime;
	}
	
	/**
	 * 当日が法定休日労働の場合
	 * @param calculatedStart 勤怠計算上の始業時刻
	 * @param before24HourManualRest 24時以前の合計手動休憩時間
	 * @param before24HourOvertimeBeforeRest 24時以前の規定終業時刻後時間外前休憩時間
	 * @param before24HourOvertimeRest 24時以前の合計残業休憩時間
	 * @param before24HourPublicGoOut 24時以前の合計公用外出時間
	 * @param before24HourPrivateGoOut 24時以前の合計私用外出時間
	 * @param before24HourMinutelyHolidayA 24時以前の合計分単位休暇A時間
	 * @param before24HourMinutelyHolidayB 24時以前の合計分単位休暇B時間
	 * @return 法定休日労動残業時間
	 */
	protected int getOvertimeWorkCaseLegalDay(int calculatedStart, int before24HourManualRest,
			int before24HourOvertimeBeforeRest, int before24HourOvertimeRest, int before24HourPublicGoOut,
			int before24HourPrivateGoOut, int before24HourMinutelyHolidayA, int before24HourMinutelyHolidayB) {
		return TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - calculatedStart - before24HourManualRest
				- before24HourOvertimeBeforeRest - before24HourOvertimeRest - before24HourPublicGoOut
				- before24HourOvertimeRest - before24HourMinutelyHolidayB
//				- before24HourPrivateGoOut
		;
	}
	
	/**
	 * 残業時間を計算する。
	 * 暦日計算：24時以前の合計残業時間を計算する際の当日が所定休日労働の場合。
	 * @param calculatedStart 勤怠計算上の始業時刻
	 * @param before24HourManualRest 24時以前の合計手動休憩時間
	 * @param before24HourOvertimeBeforeRest 24時以前の規定終業時刻後時間外前休憩時間
	 * @param before24HourOvertimeRest 24時以前の合計残業休憩時間
	 * @param before24HourPublicGoOut 24時以前の合計公用外出時間
	 * @param before24HourPrivateGoOut 24時以前の合計私用外出時間
	 * @param before24HourMinutelyHolidayA 24時以前の合計分単位休暇A時間
	 * @param before24HourMinutelyHolidayB 24時以前の合計分単位休暇B時間
	 * @return 所定休日労動残業時間
	 */
	protected int getcalcOvertimeWorkCasePrescribedDay(int calculatedStart, int before24HourManualRest,
			int before24HourOvertimeBeforeRest, int before24HourOvertimeRest, int before24HourPublicGoOut,
			int before24HourPrivateGoOut, int before24HourMinutelyHolidayA, int before24HourMinutelyHolidayB) {
		return TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - calculatedStart - before24HourManualRest
				- before24HourOvertimeBeforeRest - before24HourOvertimeRest - before24HourPublicGoOut
				- before24HourMinutelyHolidayA - before24HourMinutelyHolidayB
//				- before24HourPrivateGoOut
		;
	}
	
	/**
	 * 残業時間を計算する。
	 * 暦日計算：24時以前の残業時間を計算する際の当日が平日労働の場合。
	 * @param calculatedStart 勤怠計算上の始業時刻
	 * @param before24HourManualRest 24時以前の合計手動休憩時間
	 * @param before24HourOvertimeBeforeRest 24時以前の規定終業時刻後時間外前休憩時間
	 * @param before24HourOvertimeRest 24時以前の合計残業休憩時間
	 * @param before24HourPublicGoOut 24時以前の合計公用外出時間
	 * @param before24HourPrivateGoOut 24時以前の合計私用外出時間
	 * @param before24HourMinutelyHolidayA 24時以前の合計分単位休暇A時間
	 * @param before24HourMinutelyHolidayB 24時以前の合計分単位休暇B時間
	 * @param prescribedWorkTime 所定労働時間
	 * @param lateTime 遅刻時間
	 * @param leaveEarlyTime 早退時間
	 * @param requestUtil 申請ユーティリティ
	 * @return 平日労働残業時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getcalcOvertimeWorkCaseWorkDay(int calculatedStart, int before24HourManualRest,
			int before24HourOvertimeBeforeRest, int before24HourOvertimeRest, int before24HourPublicGoOut,
			int before24HourPrivateGoOut, int before24HourMinutelyHolidayA, int before24HourMinutelyHolidayB,
			int prescribedWorkTime, int lateTime, int leaveEarlyTime, RequestUtilBeanInterface requestUtil)
			throws MospException {
		if (calculatedStart >= regWorkStart) {
			// 勤怠計算上の始業時刻が規定始業時刻以後の場合
			int overtime = TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - calculatedStart
					- before24HourManualRest - before24HourOvertimeBeforeRest - before24HourOvertimeRest
					- before24HourPublicGoOut - before24HourMinutelyHolidayA - before24HourMinutelyHolidayB
//			- before24HourPrivateGoOut
					- prescribedWorkTime + lateTime + leaveEarlyTime + beforePaidLeaveMinute
					+ beforeMinutelyHolidayMinute
			
//			+ afterPaidLeaveMinute+ afterMinutelyHolidayMinute
			;
			if (useShort1) {
				// 時短の場合
				if (isAmHalfDayOff(requestUtil)) {
					// 前半休である場合
					if (!isShort1StartTypePay) {
						// 無給の場合
						if (short1Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間1開始時刻が24時より前の場合
							if (short1End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
								// 時短時間1終了時刻が24時より前の場合は、
								// 時短時間1終了時刻から時短時間1開始時刻を引いたものを減算する
								overtime -= short1End - short1Start;
							} else {
								// 時短時間1終了時刻が24時以後の場合は、
								// 24時から時短時間1開始時刻を引いたものを減算する
								overtime -= TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short1Start;
							}
						}
					}
				} else {
					// 前半休でない場合
					if (isShort1StartTypePay && calculatedStart > short1End) {
						// 有給且つ勤怠計算上の始業時刻が時短時間1終了時刻より後の場合
						if (short1Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間1開始時刻が24時より前の場合
							if (short1End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
								// 時短時間1終了時刻が24時より前の場合は、
								// 時短時間1終了時刻から時短時間1開始時刻を引いたものを加算する
								overtime += short1End - short1Start;
							} else {
								// 時短時間1終了時刻が24時以後の場合は、
								// 24時から時短時間1開始時刻を引いたものを加算する
								overtime += TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short1Start;
							}
						}
					}
				}
			}
			if (useShort2) {
				// 時短の場合
				if (isPmHalfDayOff(requestUtil)) {
					// 後半休である場合
					if (!isShort2StartTypePay) {
						// 無給の場合
						if (short2Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間2開始時刻が24時より前の場合
							if (short2End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
								// 時短時間2終了時刻が24時より前の場合は、
								// 時短時間2終了時刻から時短時間2開始時刻を引いたものを減算する
								overtime -= short2End - short2Start;
							} else {
								// 時短時間2終了時刻が24時以後の場合は、
								// 24時から時短時間2開始時刻を引いたものを減算する
								overtime -= TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short2Start;
							}
						}
					}
				} else {
					// 後半休でない場合
					if (isShort2StartTypePay && calculatedEnd < short2Start) {
						// 有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合
						if (short2Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間2開始時刻が24時より前の場合
							if (short2End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
								// 時短時間2終了時刻が24時より前の場合は、
								// 時短時間2終了時刻から時短時間2開始時刻を引いたものを加算する
								overtime += short2End - short2Start;
							} else {
								// 時短時間2終了時刻が24時以後の場合は、
								// 24時から時短時間2開始時刻を引いたものを加算する
								overtime += TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short2Start;
							}
						}
					}
				}
			}
			return overtime;
		}
		int overtime = TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - regWorkStart
				- before24HourManualRest - before24HourOvertimeBeforeRest - before24HourOvertimeRest
				- before24HourPublicGoOut - before24HourMinutelyHolidayA - before24HourMinutelyHolidayB
//				- before24HourPrivateGoOut
				- prescribedWorkTime + lateTime + leaveEarlyTime + beforePaidLeaveMinute + beforeMinutelyHolidayMinute
//				+ afterPaidLeaveMinute + afterMinutelyHolidayMinute
		;
		if (useShort1) {
			// 時短の場合
			if (isAmHalfDayOff(requestUtil)) {
				// 前半休である場合
				if (!isShort1StartTypePay) {
					// 無給の場合
					if (short1Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
						// 時短時間1開始時刻が24時より前の場合
						if (short1End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間1終了時刻が24時より前の場合は、
							// 時短時間1終了時刻から時短時間1開始時刻を引いたものを減算する
							overtime -= short1End - short1Start;
						} else {
							// 時短時間1終了時刻が24時以後の場合は、
							// 24時から時短時間1開始時刻を引いたものを減算する
							overtime -= TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short1Start;
						}
					}
				}
			} else {
				// 前半休でない場合
				if (isShort1StartTypePay && calculatedStart > short1End) {
					// 有給且つ勤怠計算上の始業時刻が時短時間1終了時刻より後の場合
					if (short1Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
						// 時短時間1開始時刻が24時より前の場合
						if (short1End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間1終了時刻が24時より前の場合は、
							// 時短時間1終了時刻から時短時間1開始時刻を引いたものを加算する
							overtime += short1End - short1Start;
						} else {
							// 時短時間1終了時刻が24時以後の場合は、
							// 24時から時短時間1開始時刻を引いたものを加算する
							overtime += TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short1Start;
						}
					}
				}
			}
		}
		if (useShort2) {
			// 時短の場合
			if (isPmHalfDayOff(requestUtil)) {
				// 後半休である場合
				if (!isShort2StartTypePay) {
					// 無給の場合
					if (short2Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
						// 時短時間2開始時刻が24時より前の場合
						if (short2End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間2終了時刻が24時より前の場合は、
							// 時短時間2終了時刻から時短時間2開始時刻を引いたものを減算する
							overtime -= short2End - short2Start;
						} else {
							// 時短時間2終了時刻が24時以後の場合は、
							// 24時から時短時間2開始時刻を引いたものを減算する
							overtime -= TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short2Start;
						}
					}
				}
			} else {
				// 後半休でない場合
				if (isShort2StartTypePay && calculatedEnd < short2Start) {
					// 有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合
					if (short2Start < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
						// 時短時間2開始時刻が24時より前の場合
						if (short2End < TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
							// 時短時間2終了時刻が24時より前の場合は、
							// 時短時間2終了時刻から時短時間2開始時刻を引いたものを加算する
							overtime += short2End - short2Start;
						} else {
							// 時短時間2終了時刻が24時以後の場合は、
							// 24時から時短時間2開始時刻を引いたものを加算する
							overtime += TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR - short2Start;
						}
					}
				}
			}
		}
		if (overtime > 0) {
			return overtime + workBeforeTime;
		}
		return workBeforeTime;
	}
	
	/**
	 * 残業時間を計算する。
	 * 暦日計算：翌日時間を計算する際
	 * @param calculatedEnd 勤怠計算上の終業時刻
	 * @param after24HourManualRest 24時以後の合計手動休憩時間
	 * @param after24HourOvertimeBeforeRest 24時以後の規定終業時刻後時間外前休憩時間
	 * @param after24HourOvertimeRest 24時以後の合計残業休憩時間
	 * @param after24HourPublicGoOut 24時以後の合計公用外出時間
	 * @param after24HourPrivateGoOut 24時以後の合計私用外出時間
	 * @return 翌日残業時間
	 */
	protected int getNextDayTime(int calculatedEnd, int after24HourManualRest, int after24HourOvertimeBeforeRest,
			int after24HourOvertimeRest, int after24HourPublicGoOut, int after24HourPrivateGoOut) {
		return calculatedEnd - (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR) - after24HourManualRest
				- after24HourOvertimeBeforeRest - after24HourOvertimeRest - after24HourPublicGoOut
//				- after24HourPrivateGoOut
		;
	}
	
	/**
	 * 翌日の休日労働時間を計算する。
	 * @return 翌日の休日労働時間
	 */
	protected int getNextDayHolidayWork() {
		return calculatedEnd - (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR)
				- totalAfter24HourManualRest - totalAfter24HourOvertimeBeforeRest - totalAfter24HourOvertimeRest
				- totalAfter24HourPublicGoOut - totalAfter24HourPrivateGoOut - totalAfter24HourPaidLeave;
	}
	
	/**
	 * 労働時間を取得する。
	 * @param start 開始
	 * @param end 終了
	 * @return 労働時間
	 */
	protected int getWorkTime(int start, int end) {
		//  有休時間計算
		int paidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int paidLeaveStart = getDefferenceMinutes(workDate, entry.getKey());
			int paidLeaveEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (paidLeaveStart >= end || paidLeaveEnd <= start) {
				// 有給休暇開始時刻が終了時刻以後の場合
				// 又は有給休暇終了時刻が開始時刻以前の場合
				continue;
			}
			if (paidLeaveStart < start) {
				// 有給休暇開始時刻が開始時刻より前の場合は
				// 開始時刻を有給休暇開始時刻とする
				paidLeaveStart = start;
			}
			if (paidLeaveEnd > end) {
				// 有給休暇終了時刻が終了時刻より後の場合は
				// 終了時刻を有給休暇終了時刻とする
				paidLeaveEnd = end;
			}
			paidLeave += paidLeaveEnd - paidLeaveStart;
		}
		int rest = 0;
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int startTimeInt = getDefferenceMinutes(workDate, startTime);
			int endTimeInt = getDefferenceMinutes(workDate, endTime);
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			rest += getRoundMinute(endTimeInt - startTimeInt, timeSettingDto.getRoundDailyRestTime(),
					timeSettingDto.getRoundDailyRestTimeUnit());
		}
		int overtimeBeforeRest = 0;
		for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
			int startTimeInt = getDefferenceMinutes(workDate, entry.getKey());
			int endTimeInt = getDefferenceMinutes(workDate, entry.getValue());
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			overtimeBeforeRest += endTimeInt - startTimeInt;
		}
		int overtimeRest = 0;
		for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
			int startTimeInt = getDefferenceMinutes(workDate, entry.getKey());
			int endTimeInt = getDefferenceMinutes(workDate, entry.getValue());
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			overtimeRest += endTimeInt - startTimeInt;
		}
		int publicGoOut = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int startTimeInt = getDefferenceMinutes(workDate, startTime);
			int endTimeInt = getDefferenceMinutes(workDate, endTime);
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			publicGoOut += endTimeInt - startTimeInt;
		}
		int privateGoOut = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int startTimeInt = getDefferenceMinutes(workDate, startTime);
			int endTimeInt = getDefferenceMinutes(workDate, endTime);
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			privateGoOut += endTimeInt - startTimeInt;
		}
		// 分単位休暇A
		int minutelyHolidayA = 0;
		for (GoOutDtoInterface dto : minutelyHolidayADtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int startTimeInt = getDefferenceMinutes(workDate, startTime);
			int endTimeInt = getDefferenceMinutes(workDate, endTime);
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			minutelyHolidayA += endTimeInt - startTimeInt;
		}
		// 分単位休暇B
		int minutelyHolidayB = 0;
		for (GoOutDtoInterface dto : minutelyHolidayBDtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int startTimeInt = getDefferenceMinutes(workDate, startTime);
			int endTimeInt = getDefferenceMinutes(workDate, endTime);
			if (startTimeInt >= end || endTimeInt <= start) {
				continue;
			}
			if (startTimeInt < start) {
				startTimeInt = start;
			}
			if (endTimeInt > end) {
				endTimeInt = end;
			}
			minutelyHolidayB += endTimeInt - startTimeInt;
		}
		
		return end - start - paidLeave - rest - overtimeBeforeRest - overtimeRest - publicGoOut - privateGoOut
				- minutelyHolidayA - minutelyHolidayB;
	}
	
	/**
	 * 残業時間の計算をする。
	 * @param totalManualRest 合計手動休憩時間
	 * @param totalOvertimeRest 合計残業休憩時間
	 * @param totalOvertimeBeforeRest 規定終業時刻後時間外前休憩時間
	 * @param totalPublicGoOut 合計公用外出時間
	 * @param totalPrivateGoOut 合計私用外出時間
	 * @param beforeMinutelyHolidayMinute 始業前分単位休暇時間
	 * @param afterMinutelyHolidayMinute 終業後分単位休暇時間
	 * @param requestUtil 申請ユーティリティ
	 * @return 残業時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int calcOverTime(int totalManualRest, int totalOvertimeRest, int totalOvertimeBeforeRest,
			int totalPublicGoOut, int totalPrivateGoOut, int beforeMinutelyHolidayMinute,
			int afterMinutelyHolidayMinute, RequestUtilBeanInterface requestUtil) throws MospException {
		// 残業時間設定
		int overtime = calculatedEnd - calculatedStart - workBeforeTime + lateTime - prescribedWorkTime
				+ beforePaidLeaveMinute + afterPaidLeaveMinute - totalManualRest - totalOvertimeRest
				+ beforeMinutelyHolidayMinute + afterMinutelyHolidayMinute - totalOvertimeBeforeRest;
		if (useShort1) {
			// 時短の場合
			if (isAmHalfDayOff(requestUtil)) {
				// 前半休である場合
				if (!isShort1StartTypePay) {
					// 無給の場合は無給時短時間1時間を減算する
					overtime -= getShortUnpaid1(requestUtil);
				}
			} else {
				// 前半休でない場合
				if (isShort1StartTypePay && calculatedStart > short1End) {
					// 有給且つ勤怠計算上の始業時刻が時短時間1終了時刻より後の場合は、
					// 時短時間1時間を加算する
					overtime += short1End - short1Start;
				}
			}
		}
		if (useShort2) {
			// 時短の場合
			if (isPmHalfDayOff(requestUtil)) {
				// 後半休である場合
				if (!isShort2StartTypePay) {
					// 無給の場合は無給時短時間2時間を減算する
					overtime -= getShortUnpaid2(requestUtil);
				}
			} else {
				// 後半休でない場合
				if (isShort2StartTypePay && calculatedEnd < short2Start) {
					// 有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合は、
					// 時短時間2時間を加算する
					overtime += short2End - short2Start;
				}
			}
		}
		if (overtime < 0) {
			// 0未満の場合は0とする
			return workBeforeTime;
		}
		return overtime + workBeforeTime;
	}
	
	/**
	 * 法定休日労働時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calcLegalHolidayWork(RequestUtilBeanInterface requestUtil) throws MospException {
		// 翌日の勤務形態コードが法定休日又は法定休日労働
		boolean nextDayLegalDayOff = isNextDayWorkOnLegalDaysOff() || isNextDayLegalDaysOff();
		if (!isWorkOnLegalDaysOff() && !nextDayLegalDayOff) {
			// 法定休日労働でなく且つ翌日が法定休日又は法定休日労働でない場合
			return;
		}
		int twentyFourHours = TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR;
		if ((isWorkOnLegalDaysOff() && nextDayLegalDayOff)
				|| (isWorkOnLegalDaysOff() && calculatedEnd <= twentyFourHours)) {
			// 法定休日労働且つ翌日が法定休日又は法定休日労働の場合
			// 又は法定休日労働且つ勤怠計算上の終業時刻が24時以前の場合
			legalHolidayWork = getWorkTime();
			legalHolidayRest = totalRest;
			statutoryHolidayWorkIn = 0;
			statutoryHolidayWorkOut = getWorkTime();
		} else if (isWorkOnLegalDaysOff() && !nextDayLegalDayOff && calculatedEnd > twentyFourHours) {
			// 法定休日労働且つ翌日が法定休日又は法定休日労働でなく且つ勤怠計算上の終業時刻が24時より後の場合
			int todayStatutoryHolidayWork = getOvertimeWorkCaseLegalDay(calculatedStart, totalBefore24HourManualRest,
					totalBefore24HourOvertimeBeforeRest, totalBefore24HourOvertimeRest, totalBefore24HourPublicGoOut,
					totalBefore24HourPrivateGoOut, totalBefore24HourMinutelyHolidayA, totalBefore24HourMinutelyHolidayB);
			if (todayStatutoryHolidayWork < 0) {
				// 0未満の場合は0とする
				todayStatutoryHolidayWork = 0;
			}
			int roundedTodayStatutoryHolidayWork = getRoundMinute(todayStatutoryHolidayWork,
					timeSettingDto.getRoundDailyWork(), timeSettingDto.getRoundDailyWorkUnit());
			legalHolidayWork = roundedTodayStatutoryHolidayWork;
			legalHolidayRest = totalBefore24HourManualRest + totalBefore24HourOvertimeBeforeRest
					+ totalBefore24HourOvertimeRest;
			statutoryHolidayWorkIn = 0;
			statutoryHolidayWorkOut = roundedTodayStatutoryHolidayWork;
		} else if (!isWorkOnLegalDaysOff() && nextDayLegalDayOff && calculatedEnd > twentyFourHours) {
			// 法定休日労働でなく且つ翌日が法定休日又は法定休日労働且つ勤怠計算上の終業時刻が24時より後の場合
			int nextDayLegalHolidayWork = getNextDayHolidayWork();
			if (nextDayLegalHolidayWork < 0) {
				// 0未満の場合は0とする
				nextDayLegalHolidayWork = 0;
			}
			if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
				// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合
				nextDayLegalHolidayWork += short2End - short2Start;
			}
			legalHolidayWork = getRoundMinute(nextDayLegalHolidayWork, timeSettingDto.getRoundDailyWork(),
					timeSettingDto.getRoundDailyWorkUnit());
			legalHolidayRest = totalAfter24HourManualRest + totalAfter24HourOvertimeBeforeRest
					+ totalAfter24HourOvertimeRest;
			if (prescribedWorkEnd > twentyFourHours) {
				// 所定労働終了時刻が24時より後の場合
				statutoryHolidayWorkIn = getWorkTime(twentyFourHours, prescribedWorkEnd);
				if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
					// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合は、
					// 時短時間2終了時刻から時短時間2開始時刻を引いたものを加算する
					statutoryHolidayWorkIn += short2End - short2Start;
				}
				statutoryHolidayWorkOut = getWorkTime(prescribedWorkEnd, calculatedEnd);
				return;
			}
			// 所定労働終了時刻が24時以前の場合
			statutoryHolidayWorkIn = 0;
			statutoryHolidayWorkOut = getWorkTime(twentyFourHours, calculatedEnd);
		} else if (!isWorkOnLegalDaysOff() && nextDayLegalDayOff && calculatedEnd <= twentyFourHours) {
			// 法定休日労働でなく且つ翌日が法定休日又は法定休日労働且つ勤怠計算上の終業時刻が24時以前の場合
			legalHolidayWork = 0;
			legalHolidayRest = 0;
			statutoryHolidayWorkOut = 0;
			if (prescribedWorkEnd > twentyFourHours) {
				// 所定労働終了時刻が24時より後の場合
				if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay) {
					// 後半休でなく且つ時短有給の場合
					if (short2End <= twentyFourHours) {
						// 時短時間2終了時刻が24時以前の場合
						return;
					}
					// 時短時間2終了時刻が24時より後の場合
					if (short2Start >= twentyFourHours) {
						// 時短時間2開始時刻が24時以後の場合は、
						// 時短時間2終了時刻から時短時間2開始時刻を引いたものとする
						statutoryHolidayWorkIn = short2End - short2Start;
						return;
					}
					// 時短時間2開始時刻が24時より前の場合は、
					// 24時から時短時間2開始時刻を引いたものとする
					statutoryHolidayWorkIn = short2End - twentyFourHours;
				}
			}
		}
	}
	
	/**
	 * 所定休日労働の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calcPrescribedHolidayWork(RequestUtilBeanInterface requestUtil) throws MospException {
		// 翌日の勤務形態コードが所定休日又は所定休日労働
		boolean nextDayPrescribedDayOff = isNextDayWorkOnPrescribedDaysOff() || isNextDayPrescribedDaysOff();
		if (!isWorkOnPrescribedDaysOff() && !nextDayPrescribedDayOff) {
			// 所定休日労働でなく且つ翌日が所定休日又は所定休日労働でない場合
			return;
		}
		// 翌日の勤務形態コードが法定休日又は法定休日労働
		boolean nextDayLegalDayOff = isNextDayWorkOnLegalDaysOff() || isNextDayLegalDaysOff();
		// 所定休日取扱
		boolean isCalendarDay = timeSettingDto.getSpecificHolidayHandling() == 2;
		int twentyFourHours = TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR;
		if ((isWorkOnPrescribedDaysOff() && nextDayPrescribedDayOff)
				|| (isWorkOnPrescribedDaysOff() && !nextDayPrescribedDayOff && !nextDayLegalDayOff && !isCalendarDay)
				|| (isWorkOnPrescribedDaysOff() && calculatedEnd <= twentyFourHours)) {
			// 所定休日労働且つ翌日が所定休日又は所定休日労働の場合、
			// 又は所定休日労働且つ翌日が所定休日又は所定休日労働でなく
			// 且つ翌日が法定休日又は法定休日労働でなく且つ通常の場合、
			// 又は所定休日労働且つ勤怠計算上の終業時刻が24時以前の場合
			prescribedHolidayWork = getWorkTime();
			prescribedHolidayRest = totalRest;
			prescribedHolidayWorkIn = 0;
			prescribedHolidayWorkOut = getWorkTime();
		} else if ((isWorkOnPrescribedDaysOff() && nextDayLegalDayOff && calculatedEnd > twentyFourHours)
				|| (isWorkOnPrescribedDaysOff() && !nextDayPrescribedDayOff && !nextDayLegalDayOff && isCalendarDay && calculatedEnd > twentyFourHours)) {
			// 所定休日労働且つ翌日が法定休日又は法定休日労働且つ勤怠計算上の終業時刻が24時より後の場合、
			// 又は所定休日労働且つ翌日が所定休日又は所定休日労働でなく
			// 且つ翌日が法定休日又は法定休日労働でなく且つ暦日且つ終業時刻が24時より後の場合
			int todayPrescribedHolidayWork = getcalcOvertimeWorkCasePrescribedDay(calculatedStart,
					totalBefore24HourManualRest, totalBefore24HourOvertimeBeforeRest, totalBefore24HourOvertimeRest,
					totalBefore24HourPublicGoOut, totalBefore24HourPrivateGoOut, totalBefore24HourMinutelyHolidayA,
					totalBefore24HourMinutelyHolidayB);
			if (todayPrescribedHolidayWork < 0) {
				// 0未満の場合は0とする
				todayPrescribedHolidayWork = 0;
			}
			int roundedTodayPrescribedHolidayWork = getRoundMinute(todayPrescribedHolidayWork,
					timeSettingDto.getRoundDailyWork(), timeSettingDto.getRoundDailyWorkUnit());
			prescribedHolidayWork = roundedTodayPrescribedHolidayWork;
			prescribedHolidayRest = totalBefore24HourManualRest + totalBefore24HourOvertimeBeforeRest
					+ totalBefore24HourOvertimeRest;
			prescribedHolidayWorkIn = 0;
			prescribedHolidayWorkOut = roundedTodayPrescribedHolidayWork;
		} else if ((isWorkOnLegalDaysOff() && nextDayPrescribedDayOff && calculatedEnd > twentyFourHours)
				|| (!isWorkOnPrescribedDaysOff() && !isWorkOnLegalDaysOff() && nextDayPrescribedDayOff && isCalendarDay && calculatedEnd > twentyFourHours)) {
			// 法定休日労働且つ翌日が所定休日又は所定休日労働且つ勤怠計算上の終業時刻が24時より後の場合、
			// 又は所定休日労働でなく且つ法定休日労働でなく且つ翌日が所定休日又は所定休日労働
			// 且つ暦日且つ終業時刻が24時より後の場合
			int nextDayPrescribedHolidayWork = getNextDayHolidayWork();
			if (nextDayPrescribedHolidayWork < 0) {
				// 0未満の場合は0とする
				nextDayPrescribedHolidayWork = 0;
			}
			if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
				// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合
				nextDayPrescribedHolidayWork += short2End - short2Start;
			}
			prescribedHolidayWork = getRoundMinute(nextDayPrescribedHolidayWork, timeSettingDto.getRoundDailyWork(),
					timeSettingDto.getRoundDailyWorkUnit());
			prescribedHolidayRest = totalAfter24HourManualRest + totalAfter24HourOvertimeBeforeRest
					+ totalAfter24HourOvertimeRest;
			if (prescribedWorkEnd > twentyFourHours) {
				// 所定労働終了時刻が24時より後の場合
				prescribedHolidayWorkIn = getWorkTime(twentyFourHours, prescribedWorkEnd);
				if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
					// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合は、
					// 時短時間2終了時刻から時短時間2開始時刻を引いたものを加算する
					prescribedHolidayWorkIn += short2End - short2Start;
				}
				prescribedHolidayWorkOut = getWorkTime(prescribedWorkEnd, calculatedEnd);
				return;
			}
			// 所定労働終了時刻が24時以前の場合
			prescribedHolidayWorkIn = 0;
			prescribedHolidayWorkOut = getWorkTime(twentyFourHours, calculatedEnd);
		} else if (!isWorkOnPrescribedDaysOff() && !isWorkOnLegalDaysOff() && nextDayPrescribedDayOff && isCalendarDay
				&& calculatedEnd <= twentyFourHours) {
			// 所定休日労働でなく且つ法定休日労働でなく且つ翌日が所定休日又は所定休日労働且つ暦日
			// 且つ終業時刻が24時以前の場合
			prescribedHolidayWork = 0;
			prescribedHolidayRest = 0;
			prescribedHolidayWorkOut = 0;
			if (prescribedWorkEnd > twentyFourHours) {
				// 所定労働終了時刻が24時より後の場合
				if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay) {
					// 後半休でなく且つ時短有給の場合
					if (short2End <= twentyFourHours) {
						// 時短時間2終了時刻が24時以前の場合
						return;
					}
					// 時短時間2終了時刻が24時より後の場合
					if (short2Start >= twentyFourHours) {
						// 時短時間2開始時刻が24時以後の場合は、
						// 時短時間2終了時刻から時短時間2開始時刻を引いたものとする
						prescribedHolidayWorkIn = short2End - short2Start;
						return;
					}
					// 時短時間2開始時刻が24時より前の場合は、
					// 24時から時短時間2開始時刻を引いたものとする
					prescribedHolidayWorkIn = short2End - twentyFourHours;
				}
			}
		}
	}
	
	/**
	 * 所定労働の計算
	 */
	protected void calcPrescribedWork() {
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		// 遅刻休憩時間
		for (Entry<Date, Date> entry : tardinessRestMap.entrySet()) {
			map.put(getDefferenceMinutes(workDate, entry.getKey()), getDefferenceMinutes(workDate, entry.getValue()));
		}
		// 早退休憩時間
		for (Entry<Date, Date> entry : leaveEarlyRestMap.entrySet()) {
			map.put(getDefferenceMinutes(workDate, entry.getKey()), getDefferenceMinutes(workDate, entry.getValue()));
		}
		// 休憩時間
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int startTimeInt = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int endTimeInt = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (startTimeInt >= regWorkEnd || endTimeInt <= regWorkStart) {
				continue;
			}
			if (startTimeInt < regWorkStart) {
				startTimeInt = regWorkStart;
			}
			if (endTimeInt > regWorkEnd) {
				endTimeInt = regWorkEnd;
			}
			if (map.containsKey(startTimeInt)) {
				if (map.get(startTimeInt).intValue() >= endTimeInt) {
					continue;
				}
			}
			map.put(startTimeInt, endTimeInt);
		}
		int time = regWorkStart;
		// 労働時間
		int totalWork = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int mapStart = entry.getKey().intValue();
			int mapEnd = entry.getValue().intValue();
			if (time <= mapStart) {
				int addWork = mapStart - time;
				if (totalWork + addWork >= prescribedWorkTime) {
					// 所定労働時間以上の場合
					prescribedWorkEnd = time + prescribedWorkTime - totalWork;
					return;
				}
				totalWork += addWork;
				time = mapEnd;
			}
		}
		if (totalWork + calculatedEnd - time < prescribedWorkTime) {
			return;
		}
		prescribedWorkEnd = time + prescribedWorkTime - totalWork;
	}
	
	/**
	 * 深夜労働時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void calcNightWorkTime(RequestUtilBeanInterface requestUtil) throws MospException {
		// 初期化
		nightWork = 0;
		nightRest = 0;
		nightWorkArray = new int[3];
		nightRestArray = new int[3];
		// 0:00から5:00までの深夜労働時間・深夜休憩時間の計算
		calcNightWorkTime(0, 5 * TimeConst.CODE_DEFINITION_HOUR, 0);
		// 22:00から29:00までの深夜労働時間・深夜休憩時間の計算
		calcNightWorkTime(TimeConst.TIME_NIGHT_WORK_START * TimeConst.CODE_DEFINITION_HOUR,
				TimeConst.TIME_NIGHT_WORK_END * TimeConst.CODE_DEFINITION_HOUR, 1);
		// 46:00から48:00までの深夜労働時間・深夜休憩時間の計算
		calcNightWorkTime((TimeConst.TIME_NIGHT_WORK_START + TimeConst.TIME_DAY_ALL_HOUR)
				* TimeConst.CODE_DEFINITION_HOUR, 48 * TimeConst.CODE_DEFINITION_HOUR, 2);
		// 合計深夜労働時間
		int totalNightWorkTime = 0;
		for (int time : nightWorkArray) {
			// 深夜労働時間を加算
			totalNightWorkTime += time;
		}
		// 深夜有給時短時間を加算
		totalNightWorkTime += getNightShortPaid(requestUtil);
		nightWork = totalNightWorkTime;
		// 合計深夜休憩時間
		int totalNightRestTime = 0;
		for (int time : nightRestArray) {
			// 深夜休憩時間を加算
			totalNightRestTime += time;
		}
		nightRest = totalNightRestTime;
		// 割増休憩除外(勤務形態設定)の場合
		if (workTypeEntity.isNightRestExclude()) {
			// 深夜勤務時間 = 深夜労働時間 + 深夜休憩時間(休憩を取っても減算されない)
			nightWork = totalNightWorkTime + totalNightRestTime;
			// 深夜休憩時間はゼロ
			nightRest = 0;
		}
	}
	
	/**
	 * 深夜労働時間の計算
	 * @param regNightWorkStart 規定深夜労働開始時刻
	 * @param regNightWorkEnd 規定深夜労働終了時刻
	 * @param i i
	 */
	private void calcNightWorkTime(int regNightWorkStart, int regNightWorkEnd, int i) {
		// 始業時刻が規定深夜労働終了時刻以後又は終業時刻が規定深夜労働開始時刻以前の場合
		if (calculatedStart >= regNightWorkEnd || calculatedEnd <= regNightWorkStart) {
			nightWorkArray[i] = 0;
			nightRestArray[i] = 0;
			return;
		}
		// 深夜労働開始時刻
		int nightWorkStart = regNightWorkStart;
		if (calculatedStart >= regNightWorkStart) {
			// 始業時刻が規定深夜労働開始時刻以後の場合は
			// 始業時刻を深夜労働開始時刻とする
			nightWorkStart = calculatedStart;
		}
		// 深夜労働終了時刻
		int nightWorkEnd = regNightWorkEnd;
		if (calculatedEnd <= regNightWorkEnd) {
			// 終業時刻が規定深夜労働終了時刻以前の場合は
			// 終業時刻を深夜労働終了時刻とする
			nightWorkEnd = calculatedEnd;
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計有給休暇時間
		int totalPaidLeave = 0;
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			int paidLeaveStart = getDefferenceMinutes(workDate, entry.getKey());
			int paidLeaveEnd = getDefferenceMinutes(workDate, entry.getValue());
			if (paidLeaveStart >= nightWorkEnd || paidLeaveEnd <= nightWorkStart) {
				// 有給休暇開始時刻が深夜労働終了時刻以後の場合
				// 又は有給休暇終了時刻が深夜労働開始時刻以前の場合
				continue;
			}
			if (paidLeaveStart < nightWorkStart) {
				// 有給休暇開始時刻が深夜労働開始時刻より前の場合は
				// 深夜労働開始時刻を有給休暇開始時刻とする
				paidLeaveStart = nightWorkStart;
			}
			if (paidLeaveEnd > nightWorkEnd) {
				// 有給休暇終了時刻が深夜労働終了時刻より後の場合は
				// 深夜労働終了時刻を有給休暇終了時刻とする
				paidLeaveEnd = nightWorkEnd;
			}
			totalPaidLeave += paidLeaveEnd - paidLeaveStart;
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計規定終業時刻後時間外前休憩時間
		int totalOvertimeBeforeRest = 0;
		for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalOvertimeBeforeRest += end - start;
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計残業休憩時間
		int totalOvertimeRest = 0;
		for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
			int start = getDefferenceMinutes(workDate, entry.getKey());
			int end = getDefferenceMinutes(workDate, entry.getValue());
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalOvertimeRest += end - start;
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計手動休憩時間
		int totalManualRest = 0;
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalManualRest += getRoundMinute(end - start, timeSettingDto.getRoundDailyRestTime(),
						timeSettingDto.getRoundDailyRestTimeUnit());
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計公用外出時間
		int totalPublicGoOut = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalPublicGoOut += end - start;
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計私用外出時間
		int totalPrivateGoOut = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPrivateStart(),
					timeSettingDto.getRoundDailyPrivateStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPrivateEnd(),
					timeSettingDto.getRoundDailyPrivateEndUnit());
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalPrivateGoOut += end - start;
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計分単位休暇A時間
		int totalMinutelyHolidayA = 0;
		for (GoOutDtoInterface dto : minutelyHolidayADtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalMinutelyHolidayA += end - start;
			}
		}
		// 深夜労働開始時刻から深夜労働終了時刻までの合計分単位休暇B時間
		int totalMinutelyHolidayB = 0;
		for (GoOutDtoInterface dto : minutelyHolidayBDtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int start = getDefferenceMinutes(workDate, startTime);
			int end = getDefferenceMinutes(workDate, endTime);
			if (start < nightWorkEnd && end > nightWorkStart) {
				if (start < nightWorkStart) {
					start = nightWorkStart;
				}
				if (end > nightWorkEnd) {
					end = nightWorkEnd;
				}
				totalMinutelyHolidayB += end - start;
			}
		}
		
		// 深夜労働時間
		int night = nightWorkTime(nightWorkEnd, nightWorkStart, totalManualRest, totalOvertimeBeforeRest,
				totalOvertimeRest, totalPublicGoOut, totalPrivateGoOut, totalMinutelyHolidayA, totalMinutelyHolidayB);
		// 有給休暇時間数を減算する
		night -= totalPaidLeave;
		if (night < 0) {
			// 深夜労働時間が0未満の場合は0とする
			night = 0;
		}
		nightWorkArray[i] = getRoundMinute(night, timeSettingDto.getRoundDailyWork(),
				timeSettingDto.getRoundDailyWorkUnit());
		nightRestArray[i] = totalManualRest + totalOvertimeBeforeRest + totalOvertimeRest;
	}
	
	/**
	 * 深夜労働時間を計算する。
	 * @param nightWorkEnd 深夜労働終了時刻
	 * @param nightWorkStart 深夜労働始業時刻
	 * @param totalManualRest 深夜時間を含む合計手動休憩時間
	 * @param totalOvertimeBeforeRest 深夜時間を含む残業前休憩
	 * @param totalOvertimeRest 深夜時間を含む残業休憩時間
	 * @param totalPublicGoOut 深夜時間を含む公用外出時間
	 * @param totalPrivateGoOut 深夜時間を含む私用外出時間
	 * @param totalMinutelyHolidayAGoOut 分単位休暇A時間
	 * @param totalMinutely2HolidayGoOut 分単位休暇2時間
	 * @return 深夜労働時間
	 */
	protected int nightWorkTime(int nightWorkEnd, int nightWorkStart, int totalManualRest, int totalOvertimeBeforeRest,
			int totalOvertimeRest, int totalPublicGoOut, int totalPrivateGoOut, int totalMinutelyHolidayAGoOut,
			int totalMinutely2HolidayGoOut) {
		// 深夜労働時間
		return nightWorkEnd - nightWorkStart - totalManualRest - totalOvertimeBeforeRest - totalOvertimeRest
				- totalPublicGoOut - totalPrivateGoOut - totalMinutelyHolidayAGoOut - totalMinutely2HolidayGoOut;
	}
	
	/**
	 * 深夜有給時短時間を取得する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 深夜有給時短時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getNightShortPaid(RequestUtilBeanInterface requestUtil) throws MospException {
		int shortPaid = 0;
		int[] startArray = { 0, TimeConst.TIME_NIGHT_WORK_START * TimeConst.CODE_DEFINITION_HOUR,
			(TimeConst.TIME_NIGHT_WORK_START + TimeConst.TIME_DAY_ALL_HOUR) * TimeConst.CODE_DEFINITION_HOUR };
		int[] endArray = { 5 * TimeConst.CODE_DEFINITION_HOUR,
			TimeConst.TIME_NIGHT_WORK_END * TimeConst.CODE_DEFINITION_HOUR, 48 * TimeConst.CODE_DEFINITION_HOUR };
		for (int i = 0; i < startArray.length; i++) {
			int nightStart = startArray[i];
			int nightEnd = endArray[i];
			if (!isAmHalfDayOff(requestUtil) && useShort1 && isShort1StartTypePay && calculatedStart > short1End) {
				// 前半休でなく且つ時短有給且つ勤怠計算上の始業時刻が時短時間1終了時刻より後の場合
				if (short1Start <= nightStart) {
					// 時短時間1開始時刻が深夜開始時刻以前の場合
					if (short1End >= nightEnd) {
						// 時短時間1終了時刻が深夜終了時刻以後の場合は、
						// 深夜終了時刻から深夜開始時刻を引いたものを加算する
						shortPaid += nightEnd - nightStart;
					} else if (short1End > nightStart) {
						// 時短時間1終了時刻が深夜開始時刻より後の場合は、
						// 時短時間1終了時刻から深夜開始時刻を引いたものを加算する
						shortPaid += short1End - nightStart;
					}
				} else if (short1Start < nightEnd) {
					// 時短時間1開始時刻が深夜終了時刻より前の場合
					if (short1End >= nightEnd) {
						// 時短時間1終了時刻が深夜終了時刻以後の場合は、
						// 深夜終了時刻から時短時間1開始時刻を引いたものを加算する
						shortPaid += nightEnd - short1Start;
					} else if (short1End > nightStart) {
						// 時短時間1終了時刻が深夜開始時刻より後の場合は、
						// 時短時間1終了時刻から時短時間1開始時刻を引いたものを加算する
						shortPaid += short1End - short1Start;
					}
				}
			}
			if (!isPmHalfDayOff(requestUtil) && useShort2 && isShort2StartTypePay && calculatedEnd < short2Start) {
				// 後半休でなく且つ時短有給且つ勤怠計算上の終業時刻が時短時間2開始時刻より前の場合
				if (short2Start <= nightStart) {
					// 時短時間2開始時刻が深夜開始時刻以前の場合
					if (short2End >= nightEnd) {
						// 時短時間2終了時刻が深夜終了時刻以後の場合は、
						// 深夜終了時刻から深夜開始時刻を引いたものを加算する
						shortPaid += nightEnd - nightStart;
					} else if (short2End > nightStart) {
						// 時短時間2終了時刻が深夜開始時刻より後の場合は、
						// 時短時間2終了時刻から深夜開始時刻を引いたものを加算する
						shortPaid += short2End - nightStart;
					}
				} else if (short2Start < nightEnd) {
					// 時短時間2開始時刻が深夜終了時刻より前の場合
					if (short2End >= nightEnd) {
						// 時短時間2終了時刻が深夜終了時刻以後の場合は、
						// 深夜終了時刻から時短時間2開始時刻を引いたものを加算する
						shortPaid += nightEnd - short2Start;
					} else if (short2End > nightStart) {
						// 時短時間2終了時刻が深夜開始時刻より後の場合は、
						// 時短時間2終了時刻から時短時間2開始時刻を引いたものを加算する
						shortPaid += short2End - short2Start;
					}
				}
			}
		}
		return shortPaid;
	}
	
	/**
	 * 減額対象時間の計算
	 */
	private void calcReducedTargetTime() {
		// 私用外出時間
		int result = totalPrivate;
		if (!isLateReasonTrain() && !isLateReasonCompany()) {
			// 遅刻理由が電車遅延でなく且つ会社指示でない場合は遅刻時間を加算
			result += lateTime;
		}
		if (!isLeaveEarlyReasonCompany()) {
			// 早退理由が会社指示でない場合は早退時間を加算
			result += leaveEarlyTime;
		}
		if (result < 0) {
			// 0未満の場合は0とする
			result = 0;
		}
		decreaseTime = getRoundMinute(result, timeSettingDto.getRoundDailyDecreaseTime(),
				timeSettingDto.getRoundDailyDecreaseTimeUnit());
	}
	
	/**
	 * 所定労働時間の計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calcPrescribedWorkTime(RequestUtilBeanInterface requestUtil) throws MospException {
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 法定休日労働又は所定休日労働の場合は0とする
			prescribedWorkTime = 0;
			// 法定休日労働又は所定休日労働の場合は勤怠設定の所定労働時間とする
			autoRestCalcStart = DateUtility.getHour(timeSettingDto.getGeneralWorkTime())
					* TimeConst.CODE_DEFINITION_HOUR + DateUtility.getMinute(timeSettingDto.getGeneralWorkTime());
			return;
		}
		// 規定労働時間が法定労働時間(8時間)を超える場合
		if (regWorkTime > TimeConst.TIME_WORKING_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
			// 規定労働時間が法定労働時間を超える場合は法定労働時間(8時間)を所定労働時間とする
			prescribedWorkTime = TimeConst.TIME_WORKING_HOUR * TimeConst.CODE_DEFINITION_HOUR;
			autoRestCalcStart = TimeConst.TIME_WORKING_HOUR * TimeConst.CODE_DEFINITION_HOUR;
			if (isAmHalfDayOff(requestUtil)) {
				// 午前半休の場合
				autoRestCalcStart = regWorkTime;
			}
			return;
		}
		// 規定労働時間を所定労働時間とする
		prescribedWorkTime = regWorkTime;
		autoRestCalcStart = regFullWorkTime;
		if (isAmHalfDayOff(requestUtil)) {
			// 午前半休の場合
			autoRestCalcStart = regWorkTime;
		}
	}
	
	/**
	 * 法内残業の計算
	 */
	protected void calcWithinStatutoryOvertime() {
		// 有給休暇
		int paidLeave = paidLeaveHour * TimeConst.CODE_DEFINITION_HOUR;
		// 法定労働時間と所定労働時間の差に遅刻時間、早退時間及び私用外出時間を加算する
		int differenceTime = TimeConst.TIME_WORKING_HOUR * TimeConst.CODE_DEFINITION_HOUR - prescribedWorkTime
				+ lateTime + leaveEarlyTime + totalPrivate + paidLeave + totalMinutelyHolidayA + totalMinutelyHolidayB;
		if (differenceTime < 0) {
			// 差が0未満の場合
			return;
		} else if (differenceTime <= overtimeTime) {
			// 差が時間外労働時間以下の場合
			withinStatutoryOvertime = differenceTime;
			return;
		}
		// 法定労働時間から所定労働時間を引いたものが時間外労働時間超の場合
		withinStatutoryOvertime = overtimeTime;
	}
	
	/**
	 * 代休を計算する。
	 */
	protected void calcCompensationDay() {
		int all = DateUtility.getHour(timeSettingDto.getSubHolidayAllNorm()) * TimeConst.CODE_DEFINITION_HOUR
				+ DateUtility.getMinute(timeSettingDto.getSubHolidayAllNorm());
		int half = DateUtility.getHour(timeSettingDto.getSubHolidayHalfNorm()) * TimeConst.CODE_DEFINITION_HOUR
				+ DateUtility.getMinute(timeSettingDto.getSubHolidayHalfNorm());
		int work = getWorkTime();
		if (isWorkOnLegalDaysOff()) {
			// 法定休日労働の場合
			if (work >= all) {
				// 労働時間が代休基準時間(全休)以上の場合
				grantedLegalCompensationDays = 1;
			} else if (work >= half) {
				// 法定休日労働時間が代休基準時間(半休)以上の場合
				grantedLegalCompensationDays = TimeConst.HOLIDAY_TIMES_HALF;
			}
		} else if (isWorkOnPrescribedDaysOff()) {
			// 所定休日労働の場合
			if (work >= all) {
				// 労働時間が代休基準時間(全休)以上の場合
				grantedPrescribedCompensationDays = 1;
			} else if (work >= half) {
				// 法定休日労働時間が代休基準時間(半休)以上の場合
				grantedPrescribedCompensationDays = TimeConst.HOLIDAY_TIMES_HALF;
			}
		}
	}
	
	/**
	 * 無給時短時間を取得する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 無給時短時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getShortUnpaid(RequestUtilBeanInterface requestUtil) throws MospException {
		int shortUnpaid1 = getShortUnpaid1(requestUtil);
		int shortUnpaid2 = getShortUnpaid2(requestUtil);
		int shortUnpaid = shortUnpaid1 + shortUnpaid2;
		int add = 0;
		if (isAmHalfDayOff(requestUtil)) {
			// 前半休の場合
			add = shortUnpaid1;
		}
		if (isPmHalfDayOff(requestUtil)) {
			// 後半休の場合
			add = shortUnpaid2;
		}
		if (getWorkTime() + lateTime + totalPublic + totalPrivate >= prescribedWorkTime + add) {
			// 勤務時間 + 遅刻時間 + 公用外出時間 + 私用外出時間が所定労働時間 + 加算時間以上の場合
			shortUnpaid = 0;
		} else if (getWorkTime() + lateTime + totalPublic + totalPrivate + shortUnpaid > prescribedWorkTime + add) {
			// 勤務時間 + 遅刻時間 + 公用外出時間 + 私用外出時間 + 無給時短時間が
			// 所定労働時間 + 加算時間より大きい場合
			shortUnpaid = prescribedWorkTime + add - getWorkTime() - lateTime - totalPublic - totalPrivate;
		}
		if (shortUnpaid < 0) {
			// 0より小さい場合は0とする
			return 0;
		}
		return getRoundMinute(shortUnpaid, timeSettingDto.getRoundDailyShortUnpaid(),
				timeSettingDto.getRoundDailyShortUnpaidUnit());
	}
	
	/**
	 * 無給時短時間1時間を取得する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 無給時短時間1時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getShortUnpaid1(RequestUtilBeanInterface requestUtil) throws MospException {
		if (!useShort1) {
			// 時短でない場合
			return 0;
		}
		// 時短である場合
		if (isShort1StartTypePay) {
			// 有給の場合
			return 0;
		}
		// 無給の場合
		if (isAmHalfDayOff(requestUtil)) {
			// 前半休である場合
			return short1End - short1Start;
		}
		// 前半休でない場合
		if (calculatedStart >= short1End) {
			// 勤怠計算上の始業時刻が時短時間1終了時刻以後の場合は、
			// 時短時間1終了時刻から時短時間1開始時刻を引いたものとする
			return short1End - short1Start;
		} else if (calculatedStart >= short1Start) {
			// 勤怠計算上の始業時刻が時短時間1開始時刻以後の場合は、
			// 勤怠計算上の始業時刻から時短時間1開始時刻を引いたものとする
			return calculatedStart - short1Start;
		}
		if (calculatedEnd != 0 && calculatedEnd <= short1Start) {
			// 勤怠計算上の終業時刻が時短時間1開始時刻以前の場合は、
			// 時短時間1終了時刻から時短時間1開始時刻を引いたものとする
			return short1End - short1Start;
		} else if (calculatedEnd != 0 && calculatedEnd <= short1End) {
			// 勤怠計算上の終業時刻が時短時間1終了時刻以前の場合は、
			// 時短時間1終了時刻から勤怠計算上の終業時刻を引いたものとする
			return short1End - calculatedEnd;
		}
		return 0;
	}
	
	/**
	 * 無給時短時間2時間を取得する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 無給時短時間2時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getShortUnpaid2(RequestUtilBeanInterface requestUtil) throws MospException {
		if (!useShort2) {
			// 時短でない場合
			return 0;
		}
		// 時短である場合
		if (isShort2StartTypePay) {
			// 有給の場合
			return 0;
		}
		// 無給の場合
		if (isPmHalfDayOff(requestUtil)) {
			// 後半休である場合
			return short2End - short2Start;
		}
		// 後半休でない場合
		if (calculatedStart >= short2End) {
			// 勤怠計算上の始業時刻が時短時間2終了時刻以後の場合は、
			// 時短時間2終了時刻から時短時間2開始時刻を引いたものとする
			return short2End - short2Start;
		} else if (calculatedStart >= short2Start) {
			// 勤怠計算上の始業時刻が時短時間2開始時刻以後の場合は、
			// 勤怠計算上の始業時刻から時短時間2開始時刻を引いたものとする
			return calculatedStart - short2Start;
		}
		if (calculatedEnd != 0 && calculatedEnd <= short2Start) {
			// 勤怠計算上の終業時刻が時短時間2開始時刻以前の場合は、
			// 時短時間2終了時刻から時短時間2開始時刻を引いたものとする
			return short2End - short2Start;
		} else if (calculatedEnd != 0 && calculatedEnd <= short2End) {
			// 勤怠計算上の終業時刻が時短時間2終了時刻以前の場合は、
			// 時短時間2終了時刻から勤怠計算上の終業時刻を引いたものとする
			return short2End - calculatedEnd;
		}
		return 0;
	}
	
	/**
	 * 自動計算
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void setAutoCalc(RequestUtilBeanInterface requestUtil) throws MospException {
		if (startTime != null) {
			// 勤怠計算上の始業時刻・時間外計算上の始業時刻・規定始業時刻前時間の計算
			calcCalculatedStart(true, requestUtil);
		}
		if (endTime != null) {
			// 勤怠計算上の終業時刻の計算
			calcCalculatedEnd(true, requestUtil);
		}
		if (startTime == null || endTime == null) {
			return;
		}
		// 規定休憩の計算
		calcRegRest(requestUtil);
		// 所定労働時間の計算
		calcPrescribedWorkTime(requestUtil);
		// 遅刻時間の計算
		calcTardinessTime(requestUtil);
		// 早退時間の計算
		calcLeaveEarlyTime(requestUtil);
		// 公用外出時間の計算
		calcPublicGoOutTime();
		// 私用外出時間の計算
		calcPrivateGoOutTime();
		// 分単位休暇A時間の計算
		calcMinutelyHolidayATime();
		// 分単位休暇B時間の計算
		calcMinutelyHolidayBTime();
		// 労働時間・休憩時間・規定終業時刻後時間の計算
		calcWorkTime(requestUtil);
		// 時間外労働時間の計算
		calcOvertimeWork(requestUtil);
		// 所定労働の計算
		calcPrescribedWork();
		// 法定休日労働時間の計算
		calcLegalHolidayWork(requestUtil);
		// 所定休日労働時間の計算
		calcPrescribedHolidayWork(requestUtil);
		// 深夜労働時間の計算
		calcNightWorkTime(requestUtil);
		// 減額対象時間の計算
		calcReducedTargetTime();
		// 法内残業の計算
		calcWithinStatutoryOvertime();
		// 法定外残業時間、法定外休憩時間の計算
		calcLegalOutTime();
		// 代休の計算
		calcCompensationDay();
	}
	
	/**
	 * 計算基準値設定
	 * @param attendanceDto 勤怠データ
	 * @param restDtoList 休憩データ
	 * @param publicGoOutDtoList 公用データ
	 * @param privateGoOutDtoList 私用データ
	 * @param minutelyHolidayAList 分単位休暇Aデータ
	 * @param minutelyHolidayBList 分単位休暇Bデータ
	 */
	public void setCalcInfo(AttendanceDtoInterface attendanceDto, List<RestDtoInterface> restDtoList,
			List<GoOutDtoInterface> publicGoOutDtoList, List<GoOutDtoInterface> privateGoOutDtoList,
			List<GoOutDtoInterface> minutelyHolidayAList, List<GoOutDtoInterface> minutelyHolidayBList) {
		// 初期化
		workTime = 0;
		prescribedWorkTime = 0;
		totalRest = 0;
		legalHolidayRest = 0;
		prescribedHolidayRest = 0;
		totalPublic = 0;
		lateTime = 0;
		leaveEarlyTime = 0;
		totalPrivate = 0;
		totalMinutelyHolidayA = 0;
		totalMinutelyHolidayB = 0;
		decreaseTime = 0;
		timeSettingDto = null;
		workTypeItemDto = null;
		workOnHolidayDto = null;
		holidayRequestDtoList = new ArrayList<HolidayRequestDtoInterface>();
		subHolidayRequestDtoList = new ArrayList<SubHolidayRequestDtoInterface>();
		scheduleDto = null;
		differenceDto = null;
		scheduleDateDto = null;
		beforeOvertimeDto = null;
		afterOvertimeDto = null;
		totalPublicTime = 0;
		overtimeTime = 0;
		regWorkStart = 0;
		regWorkEnd = 0;
		regWorkTime = 0;
		regFullWorkStart = 0;
		regFullWorkTime = 0;
		betweenHalfHolidayTime = 0;
		overbefore = 0;
		overper = 0;
		overrest = 0;
		overtimeOut = 0;
		workdayOvertimeIn = 0;
		workdayOvertimeOut = 0;
		prescribedHolidayOvertimeIn = 0;
		prescribedHolidayOvertimeOut = 0;
		overRestTime = 0;
		nightWork = 0;
		nightRest = 0;
		nightWorkArray = new int[0];
		nightRestArray = new int[0];
		legalHolidayWork = 0;
		prescribedHolidayWork = 0;
		withinStatutoryOvertime = 0;
		grantedLegalCompensationDays = 0;
		grantedPrescribedCompensationDays = 0;
		grantedNightCompensationDays = 0;
		statutoryHolidayWorkIn = 0;
		statutoryHolidayWorkOut = 0;
		prescribedHolidayWorkIn = 0;
		prescribedHolidayWorkOut = 0;
		workBeforeTime = 0;
		workAfterTime = 0;
		calculatedStart = 0;
		calculatedEnd = 0;
		prescribedWorkEnd = 0;
		totalBefore24HourPaidLeave = 0;
		totalBefore24HourManualRest = 0;
		totalBefore24HourOvertimeBeforeRest = 0;
		totalBefore24HourOvertimeRest = 0;
		totalBefore24HourPublicGoOut = 0;
		totalBefore24HourPrivateGoOut = 0;
		totalBefore24HourMinutelyHolidayA = 0;
		totalBefore24HourMinutelyHolidayB = 0;
		totalAfter24HourPaidLeave = 0;
		totalAfter24HourManualRest = 0;
		totalAfter24HourOvertimeBeforeRest = 0;
		totalAfter24HourOvertimeRest = 0;
		totalAfter24HourPublicGoOut = 0;
		totalAfter24HourPrivateGoOut = 0;
		totalAfter24HourMinutelyHolidayA = 0;
		totalAfter24HourMinutelyHolidayB = 0;
		regRestMap = new TreeMap<Date, Date>();
		tardinessRestMap = new TreeMap<Date, Date>();
		leaveEarlyRestMap = new TreeMap<Date, Date>();
		overtimeBeforeRestMap = new TreeMap<Date, Date>();
		overtimeRestMap = new TreeMap<Date, Date>();
		isPaidLeaveAm = false;
		isPaidLeavePm = false;
		paidLeaveHourMap = new TreeMap<Date, Date>();
		minutelyHolidayAMap = new TreeMap<Date, Date>();
		minutelyHolidayBMap = new TreeMap<Date, Date>();
		paidLeaveHour = 0;
		beforePaidLeaveMinute = 0;
		beforeMinutelyHolidayMinute = 0;
		afterPaidLeaveMinute = 0;
		afterMinutelyHolidayMinute = 0;
		isStockLeaveAm = false;
		isStockLeavePm = false;
		isSpecialLeaveAm = false;
		isSpecialLeavePm = false;
		isOtherLeaveAm = false;
		isOtherLeavePm = false;
		isAbsenceAm = false;
		isAbsencePm = false;
		isLegalCompensationDayAm = false;
		isLegalCompensationDayPm = false;
		isPrescribedCompensationDayAm = false;
		isPrescribedCompensationDayPm = false;
		isNightCompensationDaysAm = false;
		isNightCompensationDaysPm = false;
		workTypeCode = "";
		// 勤務日
		workDate = attendanceDto.getWorkDate();
		// 出勤時刻
		startTime = attendanceDto.getStartTime();
		// 退勤時刻 
		endTime = attendanceDto.getEndTime();
		// 直行
		directStart = attendanceDto.getDirectStart() == 1;
		// 直帰
		directEnd = attendanceDto.getDirectEnd() == 1;
		// 遅刻理由
		lateReason = attendanceDto.getLateReason();
		// 早退理由
		leaveEarlyReason = attendanceDto.getLeaveEarlyReason();
		// 分単位休暇A全休
		allMinutelyHolidayA = attendanceDto.getMinutelyHolidayA() == 1;
		// 分単位休暇2全休
		allMinutelyHolidayB = attendanceDto.getMinutelyHolidayB() == 1;
		// 休憩リスト
		this.restDtoList = restDtoList;
		// 公用外出リスト
		this.publicGoOutDtoList = publicGoOutDtoList;
		// 私用外出リスト
		this.privateGoOutDtoList = privateGoOutDtoList;
		// 分単位休暇リスト
		minutelyHolidayADtoList = minutelyHolidayAList;
		minutelyHolidayBDtoList = minutelyHolidayBList;
	}
	
	/**
	 * 計算結果設定
	 * @param attendanceDto 勤怠データ
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void getCalcInfo(AttendanceDtoInterface attendanceDto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 法定休日労働
		boolean isWorkOnLegalDaysOff = isWorkOnLegalDaysOff();
		// 所定休日労働
		boolean isWorkOnPrescribedDaysOff = isWorkOnPrescribedDaysOff();
		// 勤怠計算上の始業時刻をセットする
		attendanceDto.setStartTime(startTime == null ? startTime : getAttendanceTime(workDate, calculatedStart));
		// 勤怠計算上の終業時刻をセットする
		attendanceDto.setEndTime(endTime == null ? endTime : getAttendanceTime(workDate, calculatedEnd));
		attendanceDto.setDecreaseTime(decreaseTime);
		attendanceDto.setGeneralWorkTime(prescribedWorkTime);
		attendanceDto.setLateNightTime(nightWork);
		attendanceDto.setLegalWorkTime(legalHolidayWork);
		attendanceDto.setNightRestTime(nightRest);
		attendanceDto.setOverRestTime(overRestTime);
		attendanceDto.setTimesOvertime(0);
		if (overtimeTime > 0) {
			attendanceDto.setTimesOvertime(1);
		}
		attendanceDto.setOvertime(overtimeTime);
		attendanceDto.setOvertimeIn(withinStatutoryOvertime);
		attendanceDto.setOvertimeOut(overtimeOut);
		attendanceDto.setWorkdayOvertimeIn(workdayOvertimeIn);
		attendanceDto.setWorkdayOvertimeOut(workdayOvertimeOut);
		attendanceDto.setPrescribedHolidayOvertimeIn(prescribedHolidayOvertimeIn);
		attendanceDto.setPrescribedHolidayOvertimeOut(prescribedHolidayOvertimeOut);
		attendanceDto.setOvertimeAfter(workAfterTime);
		attendanceDto.setOvertimeBefore(workBeforeTime);
		attendanceDto.setPrivateTime(totalPrivate);
		attendanceDto.setPublicTime(totalPublic);
		attendanceDto.setMinutelyHolidayATime(totalMinutelyHolidayA);
		attendanceDto.setMinutelyHolidayBTime(totalMinutelyHolidayB);
		attendanceDto.setRestTime(totalRest);
		attendanceDto.setLegalHolidayRestTime(legalHolidayRest);
		attendanceDto.setPrescribedHolidayRestTime(prescribedHolidayRest);
		attendanceDto.setSpecificWorkTime(prescribedHolidayWork);
		attendanceDto.setWorkTime(getWorkTime());
		attendanceDto
			.setWorkTimeWithinPrescribedWorkTime(attendanceDto.getWorkTime() - overtimeTime - legalHolidayWork);
		attendanceDto.setContractWorkTime(getContractWorkTime());
		attendanceDto.setShortUnpaid(getShortUnpaid(requestUtil));
		attendanceDto.setWorkDays(1);
		if (isAmHalfDayOff(requestUtil) || isPmHalfDayOff(requestUtil)) {
			// 半休の場合
			attendanceDto.setWorkDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setWorkDaysForPaidLeave(1);
		attendanceDto.setTotalWorkDaysForPaidLeave(1);
		attendanceDto.setTimesHolidayWork(0);
		attendanceDto.setTimesLegalHolidayWork(0);
		attendanceDto.setTimesPrescribedHolidayWork(0);
		if (isWorkOnLegalDaysOff || isWorkOnPrescribedDaysOff) {
			// 法定休日労働又は所定休日労働の場合
			attendanceDto.setWorkDaysForPaidLeave(0);
			attendanceDto.setTotalWorkDaysForPaidLeave(0);
			attendanceDto.setTimesHolidayWork(1);
			if (isWorkOnLegalDaysOff) {
				// 休日出勤労働の場合
				attendanceDto.setTimesLegalHolidayWork(1);
			}
			if (isWorkOnPrescribedDaysOff) {
				// 所定労働時間の場合
				attendanceDto.setTimesPrescribedHolidayWork(1);
			}
		}
		attendanceDto.setPaidLeaveDays(0);
		if (isPaidLeaveAm || isPaidLeavePm) {
			// 有給休暇(前半休)又は有給休暇(後半休)の場合
			attendanceDto.setPaidLeaveDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setPaidLeaveHours(paidLeaveHour);
		attendanceDto.setStockLeaveDays(0);
		if (isStockLeaveAm || isStockLeavePm) {
			// ストック休暇(前半休)又はストック休暇(後半休)の場合
			attendanceDto.setStockLeaveDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setCompensationDays(0);
		attendanceDto.setLegalCompensationDays(0);
		attendanceDto.setPrescribedCompensationDays(0);
		attendanceDto.setNightCompensationDays(0);
		if (isLegalCompensationDayAm || isLegalCompensationDayPm || isPrescribedCompensationDayAm
				|| isPrescribedCompensationDayPm || isNightCompensationDaysAm || isNightCompensationDaysPm) {
			// 代休の場合
			attendanceDto.setCompensationDays(TimeConst.HOLIDAY_TIMES_HALF);
			if (isLegalCompensationDayAm || isLegalCompensationDayPm) {
				// 法定代休の場合
				attendanceDto.setLegalCompensationDays(TimeConst.HOLIDAY_TIMES_HALF);
			} else if (isPrescribedCompensationDayAm || isPrescribedCompensationDayPm) {
				// 所定代休の場合
				attendanceDto.setPrescribedCompensationDays(TimeConst.HOLIDAY_TIMES_HALF);
			} else if (isNightCompensationDaysAm || isNightCompensationDaysPm) {
				// 深夜代休の場合
				attendanceDto.setNightCompensationDays(TimeConst.HOLIDAY_TIMES_HALF);
			}
		}
		attendanceDto.setSpecialLeaveDays(0);
		if (isSpecialLeaveAm || isSpecialLeavePm) {
			// 特別休暇の場合
			attendanceDto.setSpecialLeaveDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setOtherLeaveDays(0);
		if (isOtherLeaveAm || isOtherLeavePm) {
			// その他休暇の場合
			attendanceDto.setOtherLeaveDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setAbsenceDays(0);
		if (isAbsenceAm || isAbsencePm) {
			// 欠勤の場合
			attendanceDto.setAbsenceDays(TimeConst.HOLIDAY_TIMES_HALF);
		}
		attendanceDto.setGrantedLegalCompensationDays(grantedLegalCompensationDays);
		attendanceDto.setGrantedPrescribedCompensationDays(grantedPrescribedCompensationDays);
		attendanceDto.setGrantedNightCompensationDays(grantedNightCompensationDays);
		attendanceDto.setLegalHolidayWorkTimeWithCompensationDay(0);
		attendanceDto.setLegalHolidayWorkTimeWithoutCompensationDay(0);
		attendanceDto.setPrescribedHolidayWorkTimeWithCompensationDay(0);
		attendanceDto.setPrescribedHolidayWorkTimeWithoutCompensationDay(0);
		attendanceDto.setOvertimeInWithCompensationDay(0);
		attendanceDto.setOvertimeInWithoutCompensationDay(0);
		attendanceDto.setOvertimeOutWithCompensationDay(0);
		attendanceDto.setOvertimeOutWithoutCompensationDay(0);
		attendanceDto.setStatutoryHolidayWorkTimeIn(statutoryHolidayWorkIn);
		attendanceDto.setStatutoryHolidayWorkTimeOut(statutoryHolidayWorkOut);
		attendanceDto.setPrescribedHolidayWorkTimeIn(prescribedHolidayWorkIn);
		attendanceDto.setPrescribedHolidayWorkTimeOut(prescribedHolidayWorkOut);
	}
	
	/**
	 * 遅刻設定。
	 * @param dto 対象DTO
	 */
	protected void setLate(AttendanceDtoInterface dto) {
		// 遅刻理由が電車遅延又は会社指示の場合は遅刻時間を0とする
		dto.setLateTime(isLateReasonTrain() || isLateReasonCompany() ? 0 : lateTime);
		dto.setActualLateTime(lateTime);
		dto.setLateDays(0);
		dto.setLateThirtyMinutesOrMore(0);
		dto.setLateLessThanThirtyMinutes(0);
		dto.setLateThirtyMinutesOrMoreTime(0);
		dto.setLateLessThanThirtyMinutesTime(0);
		if (dto.getLateTime() > 0) {
			// 遅刻時間が0より大きい場合
			dto.setLateDays(1);
			if (dto.getLateTime() >= 30) {
				// 遅刻時間が30分以上の場合
				dto.setLateThirtyMinutesOrMore(1);
				dto.setLateThirtyMinutesOrMoreTime(lateTime);
			} else {
				// 遅刻時間が30分より短い場合
				dto.setLateLessThanThirtyMinutes(1);
				dto.setLateLessThanThirtyMinutesTime(lateTime);
			}
			if (dto.getLateReason().isEmpty()) {
				// 遅刻理由がない場合は個人都合とする
				dto.setLateReason(TimeConst.CODE_TARDINESS_WHY_INDIVIDU);
			}
		}
	}
	
	/**
	 * 早退設定。
	 * @param dto 対象DTO
	 */
	protected void setLeaveEarly(AttendanceDtoInterface dto) {
		// 早退理由が会社指示の場合は早退時間を0とする
		dto.setLeaveEarlyTime(isLeaveEarlyReasonCompany() ? 0 : leaveEarlyTime);
		dto.setActualLeaveEarlyTime(leaveEarlyTime);
		dto.setLeaveEarlyDays(0);
		dto.setLeaveEarlyThirtyMinutesOrMore(0);
		dto.setLeaveEarlyLessThanThirtyMinutes(0);
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(0);
		dto.setLeaveEarlyLessThanThirtyMinutesTime(0);
		if (dto.getLeaveEarlyTime() > 0) {
			// 早退時間が0より大きい場合
			dto.setLeaveEarlyDays(1);
			if (dto.getLeaveEarlyTime() >= 30) {
				// 早退時間が30分以上の場合
				dto.setLeaveEarlyThirtyMinutesOrMore(1);
				dto.setLeaveEarlyThirtyMinutesOrMoreTime(leaveEarlyTime);
			} else {
				// 早退時間が30分より短い場合
				dto.setLeaveEarlyLessThanThirtyMinutes(1);
				dto.setLeaveEarlyLessThanThirtyMinutesTime(leaveEarlyTime);
			}
			if (dto.getLeaveEarlyReason().isEmpty()) {
				// 早退理由がない場合は個人都合とする
				dto.setLeaveEarlyReason(TimeConst.CODE_LEAVEEARLY_WHY_INDIVIDU);
			}
		}
	}
	
	/**
	 * 法定休日労働かどうか確認。<br>
	 * @return 法定休日労働であればtrue、法定休日労働でなければfalse
	 */
	protected boolean isWorkOnLegalDaysOff() {
		return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 所定休日労働かどうか確認。<br>
	 * @return 所定休日労働であればtrue、所定休日労働でなければfalse
	 */
	protected boolean isWorkOnPrescribedDaysOff() {
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 翌日が法定休日労働かどうか確認。<br>
	 * @return 翌日が法定休日労働であればtrue、翌日が法定休日労働でなければfalse
	 */
	protected boolean isNextDayWorkOnLegalDaysOff() {
		return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(nextDayWorkTypeCode);
	}
	
	/**
	 * 翌日が所定休日労働かどうか確認。<br>
	 * @return 翌日が所定休日労働であればtrue、翌日が所定休日労働でなければfalse
	 */
	protected boolean isNextDayWorkOnPrescribedDaysOff() {
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(nextDayWorkTypeCode);
	}
	
	/**
	 * 翌日が法定休日かどうか確認。
	 * @return 翌日が法定休日であればtrue、翌日が法定休日でなければfalse
	 */
	protected boolean isNextDayLegalDaysOff() {
		return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(nextDayWorkTypeCode);
	}
	
	/**
	 * 翌日が所定休日かどうか確認。<br>
	 * @return 翌日が所定休日であればtrue、翌日が所定休日でなければfalse
	 */
	protected boolean isNextDayPrescribedDaysOff() {
		return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(nextDayWorkTypeCode);
	}
	
	/**
	 * 午前休かどうか確認。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @return 午前休であればtrue、午前休でなければfalse
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean isAmHalfDayOff(RequestUtilBeanInterface requestUtil) throws MospException {
		// 休暇申請
		if (requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(true)) == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 午前休の場合
			return true;
		}
		// 代休申請
		if (requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true)) == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 午前休の場合
			return true;
		}
		// 振替休日
		if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true)) == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 午前休の場合
			return true;
		}
		// 振出・休出申請
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		return workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
	}
	
	/**
	 * 午後休かどうか確認。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @return 午後休であればtrue、午後休でなければfalse
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean isPmHalfDayOff(RequestUtilBeanInterface requestUtil) throws MospException {
		// 休暇申請
		if (requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(true)) == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休の場合
			return true;
		}
		// 代休申請
		if (requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true)) == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休の場合
			return true;
		}
		// 振替休日
		if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true)) == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休の場合
			return true;
		}
		// 振出・休出申請
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		return workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
	}
	
	/**
	 * 休暇申請日数を計算する。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public void calcHolidayRequest(RequestUtilBeanInterface requestUtil) throws MospException {
		List<HolidayRequestDtoInterface> list = requestUtil.getHolidayList(true);
		for (HolidayRequestDtoInterface dto : list) {
			int holidayType1 = dto.getHolidayType1();
			boolean is1PaidLeave = holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY;
			boolean isSpecialLeave = holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL;
			boolean isOtherLeave = holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER;
			boolean isAbsence = holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE;
			boolean is2PaidLeave = Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2());
			boolean isStockLeave = Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(dto.getHolidayType2());
			int holidayRange = dto.getHolidayRange();
			// 前半休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				if (is1PaidLeave) {
					// 休暇種別1が有給休暇の場合
					if (is2PaidLeave) {
						// 休暇種別2が有給休暇の場合
						isPaidLeaveAm = true;
					} else if (isStockLeave) {
						// 休暇種別2がストック休暇の場合
						isStockLeaveAm = true;
					}
				} else if (isSpecialLeave) {
					// 休暇種別1が特別休暇の場合
					isSpecialLeaveAm = true;
				} else if (isOtherLeave) {
					// 休暇種別1がその他休暇の場合
					isOtherLeaveAm = true;
				} else if (isAbsence) {
					// 休暇種別1が欠勤の場合
					isAbsenceAm = true;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (is1PaidLeave) {
					// 休暇種別1が有給休暇の場合
					if (is2PaidLeave) {
						// 休暇種別2が有給休暇の場合
						isPaidLeavePm = true;
					} else if (isStockLeave) {
						// 休暇種別2がストック休暇の場合
						isStockLeavePm = true;
					}
				} else if (isSpecialLeave) {
					// 休暇種別1が特別休暇の場合
					isSpecialLeavePm = true;
				} else if (isOtherLeave) {
					// 休暇種別1がその他休暇の場合
					isOtherLeavePm = true;
				} else if (isAbsence) {
					// 休暇種別1が欠勤の場合
					isAbsencePm = true;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (is1PaidLeave && is2PaidLeave) {
					// 有給休暇の場合は使用時間数を加算する
					paidLeaveHour += dto.getUseHour();
					paidLeaveHourMap.put(dto.getStartTime(), dto.getEndTime());
				}
			}
		}
	}
	
	/**
	 * 代休申請日数を計算する。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public void calcSubHolidayRequestDays(RequestUtilBeanInterface requestUtil) throws MospException {
		List<SubHolidayRequestDtoInterface> list = requestUtil.getSubHolidayList(true);
		for (SubHolidayRequestDtoInterface dto : list) {
			boolean isLegal = dto.getWorkDateSubHolidayType() == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE;
			boolean isPrescribed = dto.getWorkDateSubHolidayType() == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE;
			boolean isNight = dto.getWorkDateSubHolidayType() == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE;
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (isLegal) {
					// 法定代休の場合
					isLegalCompensationDayAm = true;
				} else if (isPrescribed) {
					// 所定代休の場合
					isPrescribedCompensationDayAm = true;
				} else if (isNight) {
					// 深夜代休の場合
					isNightCompensationDaysAm = true;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (isLegal) {
					// 法定代休の場合
					isLegalCompensationDayPm = true;
				} else if (isPrescribed) {
					// 所定代休の場合
					isPrescribedCompensationDayPm = true;
				} else if (isNight) {
					// 深夜代休の場合
					isNightCompensationDaysPm = true;
				}
			}
		}
	}
	
	@Override
	public void attendanceCalc(AttendanceDtoInterface attendanceDto) throws MospException {
		// 休憩リストの取得
		List<RestDtoInterface> restList = restReference.getRestList(attendanceDto.getPersonalId(),
				attendanceDto.getWorkDate(), attendanceDto.getTimesWork());
		// 公用外出リストの取得
		List<GoOutDtoInterface> publicGoOutList = goOutReference.getPublicGoOutList(attendanceDto.getPersonalId(),
				attendanceDto.getWorkDate());
		// 私用外出リストの取得
		List<GoOutDtoInterface> privateGoOutList = goOutReference.getPrivateGoOutList(attendanceDto.getPersonalId(),
				attendanceDto.getWorkDate());
		// 分単位休暇Aリストの取得
		List<GoOutDtoInterface> minutelyHolidayAList = goOutReference.getMinutelyHolidayAList(
				attendanceDto.getPersonalId(), attendanceDto.getWorkDate());
		// 分単位休暇Bリストの取得
		List<GoOutDtoInterface> minutelyHolidayBList = goOutReference.getMinutelyHolidayBList(
				attendanceDto.getPersonalId(), attendanceDto.getWorkDate());
		// 日々の自動計算処理
		attendanceCalc(attendanceDto, restList, publicGoOutList, privateGoOutList, minutelyHolidayAList,
				minutelyHolidayBList);
	}
	
	@Override
	public void attendanceCalc(AttendanceDtoInterface attendanceDto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> publicGoOutList, List<GoOutDtoInterface> privateGoOutList,
			List<GoOutDtoInterface> minutelyHolidayAList, List<GoOutDtoInterface> minutelyHolidayBList)
			throws MospException {
		// 個人ID・勤務日取得
		String personalId = attendanceDto.getPersonalId();
		Date targetDate = attendanceDto.getWorkDate();
		// 計算基準値設定
		setCalcInfo(attendanceDto, restList, publicGoOutList, privateGoOutList, minutelyHolidayAList,
				minutelyHolidayBList);
		// 申請設定
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(attendanceDto.getPersonalId(), attendanceDto.getWorkDate());
		// 基本情報取得
		initAttendanceTotal(attendanceDto.getPersonalId(), attendanceDto.getWorkTypeCode(), requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時短時間設定
		setShort();
		// 休暇申請日数計算
		calcHolidayRequest(requestUtil);
		// 代休申請日数計算
		calcSubHolidayRequestDays(requestUtil);
		// 分単位休暇時間数計算
		minutelyHolidayADtoList = goOutReference.getMinutelyHolidayAList(personalId, targetDate);
		minutelyHolidayBDtoList = goOutReference.getMinutelyHolidayBList(personalId, targetDate);
		calcMinutelyHolidayATime();
		calcMinutelyHolidayBTime();
		// 自動計算
		setAutoCalc(requestUtil);
		// 計算結果設定
		getCalcInfo(attendanceDto, requestUtil);
		// 遅刻設定
		setLate(attendanceDto);
		// 早退設定
		setLeaveEarly(attendanceDto);
//		// 休憩時間の重複チェック
//		checkRest();
		// 残業休憩時間の重複チェック
		checkOvertimeRest();
	}
	
	@Override
	public void calcStartEndTime(AttendanceDtoInterface attendanceDto, boolean useBetweenTime) throws MospException {
		// 計算基準値設定
		setCalcInfo(attendanceDto, new ArrayList<RestDtoInterface>(), new ArrayList<GoOutDtoInterface>(),
				new ArrayList<GoOutDtoInterface>(), new ArrayList<GoOutDtoInterface>(),
				new ArrayList<GoOutDtoInterface>());
		// 個人ID・勤務日取得
		String personalId = attendanceDto.getPersonalId();
		Date workDate = attendanceDto.getWorkDate();
		// 申請設定
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		// 基本情報取得
		initAttendanceTotal(personalId, attendanceDto.getWorkTypeCode(), requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時短時間設定
		setShort();
		// 休暇申請日数計算
		calcHolidayRequest(requestUtil);
		// 分単位休暇時間数計算
		minutelyHolidayADtoList = goOutReference.getMinutelyHolidayAList(personalId, workDate);
		minutelyHolidayBDtoList = goOutReference.getMinutelyHolidayBList(personalId, workDate);
		calcMinutelyHolidayATime();
		calcMinutelyHolidayBTime();
		// 自動計算
		if (startTime != null) {
			// 勤怠計算上の始業時刻・時間外計算上の始業時刻・規定始業時刻前時間の計算
			calcCalculatedStart(useBetweenTime, requestUtil);
		}
		if (endTime != null) {
			// 勤怠計算上の終業時刻の計算
			calcCalculatedEnd(useBetweenTime, requestUtil);
		}
		// 計算結果設定
		// 勤怠計算上の始業時刻をセットする
		attendanceDto.setStartTime(startTime == null ? startTime : getAttendanceTime(workDate, calculatedStart));
		// 勤怠計算上の終業時刻をセットする
		attendanceDto.setEndTime(endTime == null ? endTime : getAttendanceTime(workDate, calculatedEnd));
		// 実始業時刻及び実終業時刻を再設定
		setActualStartTime(attendanceDto);
		setActualEndTime(attendanceDto);
	}
	
	/**
	 * 実始業時刻を再設定する。<br>
	 * <br>
	 * 実始業時刻が設定されていて、
	 * 勤務予定表示設定(勤怠設定情報)が有効である場合のみ、
	 * 始業時刻を実始業時刻に設定する。<br>
	 * <br>
	 * 但し、設定されている実始業時刻(丸め)が時短時間1(有給)の範囲内の場合は、
	 * 実始業時刻を日出勤丸め(勤怠設定情報)で丸めた時刻を、実始業時刻に設定する。<br>
	 * <br>
	 * 同様に、設定されている実始業時刻(丸め)が時間単位有給休暇の範囲内の場合は、
	 * 実始業時刻を日出勤丸め(勤怠設定情報)で丸めた時刻を、実始業時刻に設定する。<br>
	 * <br>
	 * @param attendanceDto 勤怠データDTO
	 */
	protected void setActualStartTime(AttendanceDtoInterface attendanceDto) {
		// 実始業時刻確認
		if (attendanceDto.getActualStartTime() == null) {
			return;
		}
		// 勤務予定表示設定(勤怠設定情報)が無効である場合
		if (timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_ON) {
			return;
		}
		// 実始業時刻(丸め)(分)を取得
		int actualStartMinute = getRoundMinute(getDefferenceMinutes(workDate, attendanceDto.getActualStartTime()),
				timeSettingDto.getRoundDailyStart(), timeSettingDto.getRoundDailyStartUnit());
		// 実始業時刻(丸め)(勤務日時刻に調整)
		Date actualStartTime = getAttendanceTime(workDate, actualStartMinute);
		// 実始業時刻(丸め)が時短時間1(有給)の範囲内の場合
		if (useShort1 && isShort1StartTypePay && short1Start <= actualStartMinute && actualStartMinute <= short1End) {
			// 実始業時刻に実始業時刻(丸め)を再設定
			attendanceDto.setActualStartTime(actualStartTime);
			return;
		}
		// 実始業時刻(丸め)が時間単位有給休暇の範囲内の場合
		if (isIncludedInHourlyPaidHoliday(actualStartTime)) {
			// 実始業時刻に実始業時刻(丸め)を再設定
			attendanceDto.setActualStartTime(actualStartTime);
			return;
		}
		// 実始業時刻に勤怠計算上の始業時刻を再設定
		attendanceDto.setActualStartTime(attendanceDto.getStartTime());
	}
	
	/**
	 * 実終業時刻を再設定する。<br>
	 * <br>
	 * 実終業時刻が設定されていて、
	 * 勤務予定表示設定(勤怠設定情報)が有効である場合のみ、
	 * 終業時刻を実終業時刻に設定する。<br>
	 * <br>
	 * 但し、設定されている実終業時刻(丸め)が時短時間2(有給)の範囲内の場合は、
	 * 実終業時刻を日退勤丸め(勤怠設定情報)で丸めた時刻を、実終業時刻に設定する。<br>
	 * <br>
	 * 同様に、設定されている実終業時刻(丸め)が時間単位有給休暇の範囲内の場合は、
	 * 実終業時刻を日退勤丸め(勤怠設定情報)で丸めた時刻を、実終業時刻に設定する。<br>
	 * <br>
	 * @param attendanceDto 勤怠データDTO
	 */
	protected void setActualEndTime(AttendanceDtoInterface attendanceDto) {
		// 実終業時刻確認
		if (attendanceDto.getActualEndTime() == null) {
			return;
		}
		// 勤務予定表示設定(勤怠設定情報)が無効である場合
		if (timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_ON) {
			return;
		}
		// 実終業時刻(丸め)を取得
		int actualEndMinute = getRoundMinute(getDefferenceMinutes(workDate, attendanceDto.getActualEndTime()),
				timeSettingDto.getRoundDailyEnd(), timeSettingDto.getRoundDailyEndUnit());
		// 実終業時刻(丸め)(勤務日時刻に調整)
		Date actualEndTime = getAttendanceTime(workDate, actualEndMinute);
		// 実終業時刻(丸め)が時短時間2(有給)の範囲内の場合
		if (useShort2 && isShort2StartTypePay && short2Start <= actualEndMinute && actualEndMinute <= short2End) {
			// 実終業時刻に実終業時刻(丸め)を再設定
			attendanceDto.setActualEndTime(actualEndTime);
			return;
		}
		// 実終業時刻(丸め)が時間単位有給休暇の範囲内の場合
		if (isIncludedInHourlyPaidHoliday(actualEndTime)) {
			// 実終業時刻に実終業時刻(丸め)を再設定
			attendanceDto.setActualEndTime(actualEndTime);
			return;
		}
		// 実終業時刻に勤怠計算上の終業時刻を再設定
		attendanceDto.setActualEndTime(attendanceDto.getEndTime());
		
	}
	
	/**
	 * 対象時刻が時間単位有給休暇に含まれるかを確認する。<br>
	 * @param targetTime 対象時刻
	 * @return 確認結果(true：時間単位有給休暇に含まれる、false：含まれない)
	 */
	protected boolean isIncludedInHourlyPaidHoliday(Date targetTime) {
		// 時間単位有給休暇情報毎に処理
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			// 対象時刻が時間単位有給休暇に含まれるかを確認
			if (targetTime.before(entry.getKey()) == false && targetTime.after(entry.getValue()) == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 時短時間を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setShort() throws MospException {
		// 初期化
		useShort1 = false;
		useShort2 = false;
		isShort1StartTypePay = false;
		isShort2StartTypePay = false;
		short1Start = 0;
		short1End = 0;
		short2Start = 0;
		short2End = 0;
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 法定休日労働又は所定休日労働の場合
			return;
		}
		// 平日の場合
		if (differenceDto != null) {
			// 時差出勤申請が承認されている場合
			return;
		}
		// 時差出勤でない場合
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, workDate);
		if (workTypeEntity == null) {
			return;
		}
		isShort1StartTypePay = workTypeEntity.isShort1TypePay();
		isShort2StartTypePay = workTypeEntity.isShort2TypePay();
		if (workTypeEntity.getShort1StartTime() != null) {
			short1Start = getDefferenceMinutes(getDefaultStandardDate(), workTypeEntity.getShort1StartTime());
		}
		if (workTypeEntity.getShort1EndTime() != null) {
			short1End = getDefferenceMinutes(getDefaultStandardDate(), workTypeEntity.getShort1EndTime());
		}
		useShort1 = workTypeEntity.isShort1TimeSet();
		if (workTypeEntity.getShort2StartTime() != null) {
			short2Start = getDefferenceMinutes(getDefaultStandardDate(), workTypeEntity.getShort2StartTime());
		}
		if (workTypeEntity.getShort2EndTime() != null) {
			short2End = getDefferenceMinutes(getDefaultStandardDate(), workTypeEntity.getShort2EndTime());
		}
		useShort2 = workTypeEntity.isShort2TimeSet();
	}
	
	/**
	 * 勤怠計算上の始業時刻を計算する。<br>
	 * 合わせて時間外計算上の始業時刻及び規定始業時刻前時間を計算する。<br>
	 * @param useBetweenTime 前半休と後半休の間の時間を使う場合true、そうでない場合false
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calcCalculatedStart(boolean useBetweenTime, RequestUtilBeanInterface requestUtil)
			throws MospException {
		int betweenTime = 0;
		// 午前休間の前残業の場合
		if (useBetweenTime && beforeOvertimeDto != null && isAmHalfDayOff(requestUtil)) {
			// 残業時間が前半休と後半休の間の時間より少ない場合
			if (beforeOvertimeDto.getRequestTime() < betweenHalfHolidayTime) {
				betweenTime = beforeOvertimeDto.getRequestTime();
			} else {
				betweenTime = betweenHalfHolidayTime;
			}
		}
		// 午後休間の後残業の場合
		if (useBetweenTime && afterOvertimeDto != null && isPmHalfDayOff(requestUtil)) {
			// 残業時間が前半休と後半休の間の時間より少ない場合
			if (afterOvertimeDto.getRequestTime() < betweenHalfHolidayTime) {
				betweenTime = afterOvertimeDto.getRequestTime();
			} else {
				betweenTime = betweenHalfHolidayTime;
			}
		}
		// 実際の始業時刻
		int actualWorkBegin = getRoundMinute(getDefferenceMinutes(workDate, startTime),
				timeSettingDto.getRoundDailyStart(), timeSettingDto.getRoundDailyStartUnit());
		// 分単位全休の場合
		if (allMinutelyHolidayA || allMinutelyHolidayB) {
			// 実際の始業時刻を0とする
			calculatedStart = regWorkStart;
			//actualWorkBegin = regWorkStart;
			return;
		}
		// 法定休日労働又は所定休日労働の場合
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 実際の始業時刻を規定始業時刻とする
			regWorkStart = actualWorkBegin;
			// 実際の始業時刻を勤怠計算上の始業時刻とする
			calculatedStart = actualWorkBegin;
			return;
		}
		// 平日の場合
		if (!isAmHalfDayOff(requestUtil)) {
			// 前半休でない場合
			// 時短且つ実際の始業時刻が時短時間1開始時刻より後且つ時短時刻1終了時刻以前の場合
			if (useShort1 && actualWorkBegin > short1Start && actualWorkBegin <= short1End) {
				// 時短時間1給与区分確認
				if (isShort1StartTypePay) {
					// 実際の始業時刻に時短時間1始業時刻を設定(有休の場合)
					actualWorkBegin = short1Start;
				} else {
					// 実際の始業時刻に時短時間1終業時刻を設定(無給の場合)
					actualWorkBegin = short1End;
				}
			}
		}
		if (isPmHalfDayOff(requestUtil)) {
			// 後半休である場合
			if (actualWorkBegin > regWorkEnd + betweenTime) {
				// 実際の始業時刻が規定終業時刻より後の場合は、
				// 規定終業時刻を実際の始業時刻とする
				actualWorkBegin = regWorkEnd + betweenTime;
			}
		} else {
			// 後半休でない場合
			if (useShort2 && isShort2StartTypePay && actualWorkBegin > short2Start && actualWorkBegin <= short2End) {
				// 時短有給且つ実際の始業時刻が時短時間2開始時刻より後且つ時短時間2終了時刻以前の場合は、
				// 時短時間2開始時刻を実際の始業時刻とする
				actualWorkBegin = short2Start;
			}
		}
		// 実際の始業時刻が規定始業時刻以後の場合
		if (actualWorkBegin >= regWorkStart) {
			// 直行の場合
			if (directStart) {
				if (isAmHalfDayOff(requestUtil)) {
					// 前半休である場合は規定始業時刻を勤怠計算上の始業時刻とする
					calculatedStart = regWorkStart;
					return;
				}
				// 前半休でない場合
				if (useShort1) {
					// 時短である場合
					if (isShort1StartTypePay) {
						// 有給の場合は時短時間1開始時刻を勤怠計算上の始業時刻とする
						calculatedStart = short1Start;
						return;
					}
					// 無給の場合は時短時間1終了時刻を勤怠計算上の始業時刻とする
					calculatedStart = short1End;
					return;
				}
				// 時短でない場合は規定始業時刻を勤怠計算上の始業時刻とする
				calculatedStart = regWorkStart;
				return;
			}
			// 直行でない場合
			if (!isAmHalfDayOff(requestUtil)) {
				// 前半休でない場合
				// 時短且つ実際の始業時刻が時短時間1開始時刻以降且つ時短時刻1終了時刻以前の場合
				if (useShort1 && actualWorkBegin >= short1Start && actualWorkBegin <= short1End) {
					// 時短時間1給与区分確認
					if (isShort1StartTypePay) {
						// 勤怠計算上の始業時刻に時短時間1始業時刻を設定(有休の場合)
						calculatedStart = short1Start;
					} else {
						// 勤怠計算上の始業時刻に時短時間1終業時刻を設定(無給の場合)
						calculatedStart = short1End;
					}
					return;
				}
			}
			if (!isPmHalfDayOff(requestUtil)) {
				// 後半休でない場合
				if (useShort2 && isShort2StartTypePay && actualWorkBegin > short2Start && actualWorkBegin <= short2End) {
					// 時短有給且つ実際の始業時刻が時短時間2開始時刻より後且つ時短時間2終了時刻以前の場合は、
					// 時短時間2開始時刻を勤怠計算上の始業時刻とする
					calculatedStart = short2Start;
					return;
				}
			}
			// 有給休暇終了時刻に実際の始業時刻設定
			actualWorkBegin = getRoundStartTime(actualWorkBegin, betweenTime);
			return;
		}
		// 始業前労働予定時間
		int planBeforeOvertime = 0;
		if (beforeOvertimeDto != null) {
			planBeforeOvertime = beforeOvertimeDto.getRequestTime();
		}
		if (!isAmHalfDayOff(requestUtil) && useShort1 && !isShort1StartTypePay) {
			// 前半休でなく且つ時短無給の場合
			planBeforeOvertime = 0;
		}
		// 始業前労働予定時間が0以下の場合
		if (planBeforeOvertime <= 0) {
			if (!isAmHalfDayOff(requestUtil) && useShort1 && !isShort1StartTypePay) {
				// 前半休でなく且つ時短無給の場合は時短時間1終了時刻を勤怠計算上の始業時刻とする
				calculatedStart = short1End;
				return;
			}
			calculatedStart = regWorkStart;
			return;
		}
		if (isAmHalfDayOff(requestUtil)) {
			// 前半休の場合
			// 始業前労働可能時間
			int possibleBeforeTime = planBeforeOvertime;
			if (planBeforeOvertime >= betweenTime) {
				// 始業前労働予定時間が前半休と後半休の間の時間以上の場合
				possibleBeforeTime = betweenTime;
			}
			if (actualWorkBegin >= regWorkStart - possibleBeforeTime) {
				// 実際の始業時刻が規定始業時刻から始業前労働可能時間を引いたもの以後の場合
				calculatedStart = actualWorkBegin;
			} else {
				// 実際の始業時刻が規定始業時刻から始業前労働可能時間を引いたものより前の場合
				calculatedStart = regWorkStart - betweenTime;
			}
			// 規定始業時刻前時間の計算
			calcWorkBeforeTime();
			return;
		}
		// 前半休でない場合
		if (actualWorkBegin >= regWorkStart - planBeforeOvertime) {
			// 実際の始業時刻が規定始業時刻から始業前労働予定時間を引いたもの以後の場合
			calculatedStart = actualWorkBegin;
		} else {
			calculatedStart = regWorkStart - planBeforeOvertime;
		}
		// 規定始業時刻前時間の計算
		calcWorkBeforeTime();
	}
	
	/**
	 * 規定始業時刻前時間を計算する。<br>
	 */
	protected void calcWorkBeforeTime() {
		// 休憩時間
		int rest = 0;
		for (RestDtoInterface dto : restDtoList) {
			Date startTime = getRoundMinute(dto.getRestStart(), timeSettingDto.getRoundDailyRestStart(),
					timeSettingDto.getRoundDailyRestStartUnit());
			Date endTime = getRoundMinute(dto.getRestEnd(), timeSettingDto.getRoundDailyRestEnd(),
					timeSettingDto.getRoundDailyRestEndUnit());
			int start = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int end = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (start < regWorkStart && end > calculatedStart) {
				// 休憩開始時刻が規定始業時刻より前で且つ
				// 休憩終了時刻が勤怠計算上の始業時刻より後の場合
				if (start < calculatedStart) {
					// 休憩開始時刻が勤怠計算上の始業時刻より前の場合は、
					// 勤怠計算上の始業時刻を休憩開始時刻とする
					start = calculatedStart;
				}
				if (end > regWorkStart) {
					// 休憩終了時刻が規定始業時刻より後の場合は、
					// 規定始業時刻を休憩終了時刻とする
					end = regWorkStart;
				}
				rest += end - start;
			}
		}
		// 公用外出時間
		int publicGoOut = 0;
		for (GoOutDtoInterface dto : publicGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPublicStart(),
					timeSettingDto.getRoundDailyPublicStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPublicEnd(),
					timeSettingDto.getRoundDailyPublicEndUnit());
			int start = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int end = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (start < regWorkStart && end > calculatedStart) {
				// 外出開始時刻が規定始業時刻より前で且つ
				// 外出終了時刻が勤怠計算上の始業時刻より後の場合
				if (start < calculatedStart) {
					// 外出開始時刻が勤怠計算上の始業時刻より前の場合は、
					// 勤怠計算上の始業時刻を外出開始時刻とする
					start = calculatedStart;
				}
				if (end > regWorkStart) {
					// 外出終了時刻が規定始業時刻より後の場合は、
					// 規定始業時刻を外出終了時刻とする
					end = regWorkStart;
				}
				publicGoOut += end - start;
			}
		}
		// 私用外出時間
		int privateGoOut = 0;
		for (GoOutDtoInterface dto : privateGoOutDtoList) {
			Date startTime = getRoundMinute(dto.getGoOutStart(), timeSettingDto.getRoundDailyPrivateStart(),
					timeSettingDto.getRoundDailyPrivateStartUnit());
			Date endTime = getRoundMinute(dto.getGoOutEnd(), timeSettingDto.getRoundDailyPrivateEnd(),
					timeSettingDto.getRoundDailyPrivateEndUnit());
			int start = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int end = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (start < regWorkStart && end > calculatedStart) {
				// 外出開始時刻が規定始業時刻より前で且つ
				// 外出終了時刻が勤怠計算上の始業時刻より後の場合
				if (start < calculatedStart) {
					// 外出開始時刻が勤怠計算上の始業時刻より前の場合は、
					// 勤怠計算上の始業時刻を外出開始時刻とする
					start = calculatedStart;
				}
				if (end > regWorkStart) {
					// 外出終了時刻が規定始業時刻より後の場合は、
					// 規定始業時刻を外出終了時刻とする
					end = regWorkStart;
				}
				privateGoOut += end - start;
			}
		}
		// 分単位A休暇時間
		int minutelyHolidayA = 0;
		for (GoOutDtoInterface dto : minutelyHolidayADtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int start = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int end = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (start < regWorkStart && end > calculatedStart) {
				// 外出開始時刻が規定始業時刻より前で且つ
				// 外出終了時刻が勤怠計算上の始業時刻より後の場合
				if (start < calculatedStart) {
					// 外出開始時刻が勤怠計算上の始業時刻より前の場合は、
					// 勤怠計算上の始業時刻を外出開始時刻とする
					start = calculatedStart;
				}
				if (end > regWorkStart) {
					// 外出終了時刻が規定始業時刻より後の場合は、
					// 規定始業時刻を外出終了時刻とする
					end = regWorkStart;
				}
				minutelyHolidayA += end - start;
			}
		}
		// 分単位休暇B時間
		int minutelyHolidayB = 0;
		for (GoOutDtoInterface dto : minutelyHolidayBDtoList) {
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			int start = DateUtility.getHour(startTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(startTime);
			int end = DateUtility.getHour(endTime, workDate) * TimeConst.CODE_DEFINITION_HOUR
					+ DateUtility.getMinute(endTime);
			if (start < regWorkStart && end > calculatedStart) {
				// 外出開始時刻が規定始業時刻より前で且つ
				// 外出終了時刻が勤怠計算上の始業時刻より後の場合
				if (start < calculatedStart) {
					// 外出開始時刻が勤怠計算上の始業時刻より前の場合は、
					// 勤怠計算上の始業時刻を外出開始時刻とする
					start = calculatedStart;
				}
				if (end > regWorkStart) {
					// 外出終了時刻が規定始業時刻より後の場合は、
					// 規定始業時刻を外出終了時刻とする
					end = regWorkStart;
				}
				minutelyHolidayB += end - start;
			}
		}
		int beforeTime = getbeforeTime(regWorkStart, calculatedStart, rest, publicGoOut, privateGoOut,
				minutelyHolidayA, minutelyHolidayB);
		if (beforeTime < 0) {
			beforeTime = 0;
		}
		workBeforeTime = beforeTime;
	}
	
	/**
	 * 時間休・分単位A・分単位Bにより丸められる始業時刻を取得する。
	 * @param actualWorkBegin 実始業開始時刻
	 * @param betweenTime 有休間時間
	 * @return 始業時刻
	 */
	protected int getRoundStartTime(int actualWorkBegin, int betweenTime) {
		// 全丸めマップ準備
		Map<Date, Date> roundStartTimeMap = new TreeMap<Date, Date>();
		// 時間休・分単位A・分単位Bを全丸めマップに設定
		for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
			roundStartTimeMap.put(entry.getKey(), entry.getValue());
		}
		for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
			roundStartTimeMap.put(entry.getKey(), entry.getValue());
		}
		for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
			roundStartTimeMap.put(entry.getKey(), entry.getValue());
		}
		// 
		int roundLeaveEnd = actualWorkBegin;
		// 全丸めマップ毎に処理
		for (Entry<Date, Date> entry : roundStartTimeMap.entrySet()) {
			// 開始時刻・終了時刻取得
			int leaveHourStart = getDefferenceMinutes(workDate, entry.getKey());
			int leaveHourEnd = getDefferenceMinutes(workDate, entry.getValue());
			// 開始時刻以後且つ終了時刻より前の場合
			if (roundLeaveEnd >= leaveHourStart && roundLeaveEnd < leaveHourEnd) {
				// 有給休暇終了時刻を有休終了時間に設定
				roundLeaveEnd = leaveHourEnd;
			}
		}
		// 実際の始業時刻が有給休暇終了時刻より前の場合
		if (actualWorkBegin < roundLeaveEnd) {
			// 有給休暇終了時刻が規定終業時刻より後の場合
			if (roundLeaveEnd > regWorkEnd + betweenTime) {
				// 規定終業時刻を勤怠計算上の始業時刻とする
				calculatedStart = regWorkEnd + betweenTime;
			} else {
				// 有給休暇終了時刻を勤怠計算上の始業時刻とする
				calculatedStart = roundLeaveEnd;
			}
			return roundLeaveEnd;
		}
		// 実際の始業時刻が有給休暇終了時刻以後の場合
		calculatedStart = actualWorkBegin;
		return roundLeaveEnd;
		
	}
	
	/**
	 * 規定始業時刻前時間を計算する。
	 * @param regWorkStart 規定始業時刻前時間を計算
	 * @param calculatedStart 勤怠計算上の始業時刻
	 * @param rest 勤怠計算上の休憩時間
	 * @param publicGoOut 勤怠計算上の公用外出時間
	 * @param privateGoOut 勤怠計算上の私用外出時間
	 * @param minutelyHolidayAGoOut 分単位休暇A時間
	 * @param minutelyHolidayBGoOut 分単位休暇B時間
	 * @return 規定始業時刻前時間
	 */
	protected int getbeforeTime(int regWorkStart, int calculatedStart, int rest, int publicGoOut, int privateGoOut,
			int minutelyHolidayAGoOut, int minutelyHolidayBGoOut) {
		return regWorkStart - calculatedStart - rest - publicGoOut - privateGoOut - minutelyHolidayAGoOut
				- minutelyHolidayBGoOut;
	}
	
	/**
	 * 勤怠計算上の終業時刻を計算する。<br>
	 * @param useBetweenTime 前半休と後半休の間の時間を使う場合true、そうでない場合false
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calcCalculatedEnd(boolean useBetweenTime, RequestUtilBeanInterface requestUtil) throws MospException {
		int betweenTime = 0;
		// 午前休間の前残業の場合
		if (useBetweenTime && beforeOvertimeDto != null && isAmHalfDayOff(requestUtil)) {
			// 残業時間が前半休と後半休の間の時間より少ない場合
			if (beforeOvertimeDto.getRequestTime() < betweenHalfHolidayTime) {
				// 残業時間設定
				betweenTime = beforeOvertimeDto.getRequestTime();
			} else {
				betweenTime = betweenHalfHolidayTime;
			}
		}
		// 午後休間の後残業の場合
		if (useBetweenTime && afterOvertimeDto != null && isPmHalfDayOff(requestUtil)) {
			// 残業時間が前半休と後半休の間の時間より少ない場合
			if (afterOvertimeDto.getRequestTime() < betweenHalfHolidayTime) {
				// 残業時間設定
				betweenTime = afterOvertimeDto.getRequestTime();
			} else {
				betweenTime = betweenHalfHolidayTime;
			}
		}
		// 実際の終業時刻
		int actualWorkEnd = getRoundMinute(getDefferenceMinutes(workDate, endTime), timeSettingDto.getRoundDailyEnd(),
				timeSettingDto.getRoundDailyEndUnit());
		// 分単位全休の場合
		if (allMinutelyHolidayA || allMinutelyHolidayB) {
			// 実際の終業時刻を0とする
			calculatedEnd = regWorkEnd;
			//actualWorkEnd = regWorkEnd;
			return;
		}
		// 法定休日労働又は所定休日労働の場合
		if (isWorkOnLegalDaysOff() || isWorkOnPrescribedDaysOff()) {
			// 実際の終業時刻を勤怠計算上の終業時刻とする
			calculatedEnd = actualWorkEnd;
			return;
		}
		// 平日の場合
		if (isAmHalfDayOff(requestUtil)) {
			// 前半休である場合
			if (actualWorkEnd < regWorkStart - betweenTime) {
				// 実際の終業時刻が規定始業時刻より前の場合は、
				// 規定始業時刻を実際の終業時刻とする
				actualWorkEnd = regWorkStart - betweenTime;
			}
		} else {
			// 前半休でない場合
			if (useShort1 && isShort1StartTypePay && actualWorkEnd >= short1Start && actualWorkEnd < short1End) {
				// 時短有給且つ実際の終業時刻が時短時間1開始時刻以後且つ時短時間1終了時刻より前の場合は、
				// 時短時間1終了時刻を実際の終業時刻とする
				actualWorkEnd = short1End;
			}
		}
		if (!isPmHalfDayOff(requestUtil)) {
			// 後半休でない場合
			if (useShort2 && isShort2StartTypePay && actualWorkEnd >= short2Start && actualWorkEnd < short2End) {
				// 時短有給且つ実際の終業時刻が時短時間2開始時刻以後時短時刻2終了時刻より前の場合は、
				// 時短時間2終了時刻を勤怠計算上の終業時刻とする
				actualWorkEnd = short2End;
			}
		}
		if (actualWorkEnd <= regWorkEnd) {
			// 実際の終業時刻が規定終業時刻以前の場合
			if (directEnd) {
				// 直帰である場合
				if (isPmHalfDayOff(requestUtil)) {
					// 後半休である場合は規定終業時刻を勤怠計算上の終業時刻とする
					calculatedEnd = regWorkEnd;
					return;
				}
				// 後半休でない場合
				if (useShort2) {
					// 時短である場合
					if (isShort2StartTypePay) {
						// 有給の場合は時短時間2終了時刻を勤怠計算上の終業時刻とする
						calculatedEnd = short2End;
						return;
					}
					// 無給の場合は時短時間2始業時刻を勤怠計算上の終業時刻とする
					calculatedEnd = short2Start;
					return;
				}
				// 時短でない場合は規定終業時刻を勤怠計算上の終業時刻とする
				calculatedEnd = regWorkEnd;
				return;
			}
			// 直帰でない場合
			if (!isAmHalfDayOff(requestUtil)) {
				// 前半休でない場合
				if (useShort1 && isShort1StartTypePay && actualWorkEnd >= short1Start && actualWorkEnd < short1End) {
					// 時短有給且つ実際の終業時刻が時短時間1開始時刻以後且つ時短時間1終了時刻より前の場合は、
					// 時短時間1終了時刻を実際の終業時刻とする
					calculatedEnd = short1End;
					return;
				}
			}
			if (!isPmHalfDayOff(requestUtil)) {
				// 後半休でない場合
				if (useShort2 && isShort2StartTypePay && actualWorkEnd >= short2Start && actualWorkEnd < short2End) {
					// 時短有給且つ実際の終業時刻が時短時間2開始時刻以後且つ時短時刻2終了時刻より前の場合は、
					// 時短時間2終了時刻を勤怠計算上の終業時刻とする
					calculatedEnd = short2End;
					return;
				}
			}
			int paidLeaveStart = actualWorkEnd;
			boolean whileFlag = true;
			// 分単位休暇毎に処理
			while (whileFlag) {
				whileFlag = false;
				for (Entry<Date, Date> entry : minutelyHolidayAMap.entrySet()) {
					int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
					int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
					if (paidLeaveStart > minutelyHolidayStart && paidLeaveStart <= minutelyHolidayEnd) {
						// 分単位休暇開始時刻以後且つ分単位休暇終了時刻より前の場合
						paidLeaveStart = minutelyHolidayStart;
						whileFlag = true;
					}
				}
				for (Entry<Date, Date> entry : minutelyHolidayBMap.entrySet()) {
					int minutelyHolidayStart = getDefferenceMinutes(workDate, entry.getKey());
					int minutelyHolidayEnd = getDefferenceMinutes(workDate, entry.getValue());
					if (paidLeaveStart > minutelyHolidayStart && paidLeaveStart <= minutelyHolidayEnd) {
						// 分単位休暇開始時刻以後且つ分単位休暇終了時刻より前の場合
						paidLeaveStart = minutelyHolidayStart;
						whileFlag = true;
					}
				}
				for (Entry<Date, Date> entry : paidLeaveHourMap.entrySet()) {
					int paidLeaveHourStart = getDefferenceMinutes(workDate, entry.getKey());
					int paidLeaveHourEnd = getDefferenceMinutes(workDate, entry.getValue());
					if (paidLeaveStart > paidLeaveHourStart && paidLeaveStart <= paidLeaveHourEnd) {
						// 時間休開始時刻以後且つ時間休終了時刻より前の場合
						paidLeaveStart = paidLeaveHourStart;
						whileFlag = true;
					}
				}
			}
			if (actualWorkEnd > paidLeaveStart) {
				// 実際の終業時刻が有給休暇開始時刻より後の場合
				if (paidLeaveStart < regWorkStart - betweenTime) {
					// 有給休暇開始時刻が規定始業時刻より前の場合は、
					// 規定始業時刻を勤怠計算上の終業時刻とする
					calculatedEnd = regWorkStart - betweenTime;
				}
				// 有給休暇開始時刻を勤怠計算上の終業時刻とする
				calculatedEnd = paidLeaveStart;
				return;
			}
			// 実際の終業時刻が有給休暇開始時刻以前の場合
			calculatedEnd = actualWorkEnd;
			return;
		}
		if (isPmHalfDayOff(requestUtil)) {
			// 午後休の場合
			// 終業後労働予定時間
			int planAfterOvertime = 0;
			if (afterOvertimeDto != null) {
				planAfterOvertime = afterOvertimeDto.getRequestTime();
			}
			if (planAfterOvertime <= 0) {
				// 終業後労働予定時間が0以下の場合
				calculatedEnd = regWorkEnd;
				return;
			}
			// 終業後労働可能時間
			int possibleAfterTime = planAfterOvertime;
			if (planAfterOvertime >= betweenTime) {
				// 終業後労働予定時間が前半休と後半休の間の時間以上の場合
				possibleAfterTime = betweenTime;
			}
			if (actualWorkEnd <= regWorkEnd + possibleAfterTime) {
				// 実際の終業時刻が規定終業時刻に終業後労働可能時間を足したもの以前の場合
				calculatedEnd = actualWorkEnd;
				return;
			}
			// 実際の終業時刻が規定終業時刻に終業後労働可能時間を足したものより後の場合
			calculatedEnd = regWorkEnd + betweenTime;
			return;
		}
		calculatedEnd = actualWorkEnd;
	}
	
	/**
	 * 契約勤務時間を取得する。
	 * @return 契約勤務時間
	 */
	protected int getContractWorkTime() {
		int contractWorkTime = getWorkTime() - overtimeTime - legalHolidayWork;
		if (isLateReasonTrain() || isLateReasonCompany()) {
			// 遅刻理由が電車遅延又は会社指示の場合は遅刻時間を加算
			contractWorkTime += lateTime;
		}
		if (isLeaveEarlyReasonCompany()) {
			// 早退理由が会社指示の場合は早退時間を加算
			contractWorkTime += leaveEarlyTime;
		}
		return contractWorkTime;
	}
	
	/**
	 * 遅刻理由が電車遅延かどうか確認する。
	 * @return 電車遅延の場合true、そうでない場合false
	 */
	protected boolean isLateReasonTrain() {
		return TimeConst.CODE_TARDINESS_WHY_TRAIN.equals(lateReason);
	}
	
	/**
	 * 遅刻理由が会社指示かどうか確認する。
	 * @return 会社指示の場合true、そうでない場合false
	 */
	protected boolean isLateReasonCompany() {
		return TimeConst.CODE_TARDINESS_WHY_COMPANY.equals(lateReason);
	}
	
	/**
	 * 早退理由が会社指示かどうか確認する。
	 * @return 会社指示の場合true、そうでない場合false
	 */
	protected boolean isLeaveEarlyReasonCompany() {
		return TimeConst.CODE_LEAVEEARLY_WHY_COMPANY.equals(leaveEarlyReason);
	}
	
	/**
	 * 休憩との重複チェック。<br>
	 * 公用外出、私用外出、分単位休暇A、分単位休暇Bを確認。
	 */
	protected void checkRest() {
		String[] restNameArray = new String[]{ mospParams.getName("Rest1"), mospParams.getName("Rest2"),
			mospParams.getName("Rest3"), mospParams.getName("Rest4"), mospParams.getName("Rest5"),
			mospParams.getName("Rest6") };
		for (int i = 0; i < restDtoList.size(); i++) {
			RestDtoInterface restDto = restDtoList.get(i);
			// 公用外出
			String[] publicGoOutNameArray = new String[]{ mospParams.getName("Official", "GoingOut", "No1"),
				mospParams.getName("Official", "GoingOut", "No2") };
			for (int j = 0; j < publicGoOutDtoList.size(); j++) {
				GoOutDtoInterface goOutDto = publicGoOutDtoList.get(j);
				if (isOverlap(restDto.getRestStart(), restDto.getRestEnd(), goOutDto.getGoOutStart(),
						goOutDto.getGoOutEnd())) {
					// 重複している場合
					addOverlapErrorMessage(restNameArray[i], publicGoOutNameArray[j]);
				}
			}
			// 私用外出
			String[] privateGoOutNameArray = new String[]{ mospParams.getName("PrivateGoingOut1"),
				mospParams.getName("PrivateGoingOut2") };
			for (int j = 0; j < privateGoOutDtoList.size(); j++) {
				GoOutDtoInterface goOutDto = privateGoOutDtoList.get(j);
				if (isOverlap(restDto.getRestStart(), restDto.getRestEnd(), goOutDto.getGoOutStart(),
						goOutDto.getGoOutEnd())) {
					// 重複している場合
					addOverlapErrorMessage(restNameArray[i], privateGoOutNameArray[j]);
				}
			}
			// 分単位休暇A
			String[] minutelyHolidayANameArray = new String[]{ mospParams.getName("MinutelyHolidayA", "No1"),
				mospParams.getName("MinutelyHolidayA", "No2"), mospParams.getName("MinutelyHolidayA", "No3"),
				mospParams.getName("MinutelyHolidayA", "No4") };
			for (int j = 0; j < minutelyHolidayADtoList.size(); j++) {
				GoOutDtoInterface goOutDto = minutelyHolidayADtoList.get(j);
				if (isOverlap(restDto.getRestStart(), restDto.getRestEnd(), goOutDto.getGoOutStart(),
						goOutDto.getGoOutEnd())) {
					// 重複している場合
					addOverlapErrorMessage(restNameArray[i], minutelyHolidayANameArray[j]);
				}
			}
			// 分単位休暇B
			String[] minutelyHolidayBNameArray = new String[]{ mospParams.getName("MinutelyHolidayB", "No1"),
				mospParams.getName("MinutelyHolidayB", "No2"), mospParams.getName("MinutelyHolidayB", "No3"),
				mospParams.getName("MinutelyHolidayB", "No4") };
			for (int j = 0; j < minutelyHolidayBDtoList.size(); j++) {
				GoOutDtoInterface goOutDto = minutelyHolidayBDtoList.get(j);
				if (isOverlap(restDto.getRestStart(), restDto.getRestEnd(), goOutDto.getGoOutStart(),
						goOutDto.getGoOutEnd())) {
					// 重複している場合
					addOverlapErrorMessage(restNameArray[i], minutelyHolidayBNameArray[j]);
				}
			}
			
		}
	}
	
	/**
	 * 残前休憩・残業休憩との重複チェック。<br>
	 * 休憩時間、公用外出、私用外出、分単位休暇A、分単位休暇Bを確認。
	 */
	protected void checkOvertimeRest() {
		// 休憩時間
		String[] restNameArray = new String[]{ mospParams.getName("Rest1"), mospParams.getName("Rest2"),
			mospParams.getName("Rest3"), mospParams.getName("Rest4"), mospParams.getName("Rest5"),
			mospParams.getName("Rest6") };
		for (int i = 0; i < restDtoList.size(); i++) {
			RestDtoInterface dto = restDtoList.get(i);
			Date[] overtimeBeforeArray = getOverlapOvertimeBeforeRestArray(dto.getRestStart(), dto.getRestEnd());
			boolean isOverlapOvertimeBefore = overtimeBeforeArray.length >= 2;
			Date[] overtimeArray = getOverlapOvertimeRestArray(dto.getRestStart(), dto.getRestEnd());
			boolean isOverlapOvertime = overtimeArray.length >= 2;
			if (isOverlapOvertimeBefore || isOverlapOvertime) {
				// 重複している場合
				if (isOverlapOvertimeBefore) {
					// 残前休憩と重複している場合
					addOverlapOvertimeBeforeRestErrorMessage(restNameArray[i], overtimeBeforeArray[0],
							overtimeBeforeArray[1]);
				}
				if (isOverlapOvertime) {
					// 残業休憩と重複している場合
					addOverlapOvertimeRestErrorMessage(restNameArray[i], overtimeArray[0], overtimeArray[1]);
				}
				break;
			}
		}
		// 公用外出時間
		StringBuffer publicGoOut1 = new StringBuffer();
		publicGoOut1.append(mospParams.getName("Official"));
		publicGoOut1.append(mospParams.getName("GoingOut"));
		publicGoOut1.append(mospParams.getName("No1"));
		StringBuffer publicGoOut2 = new StringBuffer();
		publicGoOut2.append(mospParams.getName("Official"));
		publicGoOut2.append(mospParams.getName("GoingOut"));
		publicGoOut2.append(mospParams.getName("No2"));
		String[] publicGoOutNameArray = new String[]{ publicGoOut1.toString(), publicGoOut2.toString() };
		for (int i = 0; i < publicGoOutDtoList.size(); i++) {
			GoOutDtoInterface dto = publicGoOutDtoList.get(i);
			Date[] overtimeBeforeArray = getOverlapOvertimeBeforeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertimeBefore = overtimeBeforeArray.length >= 2;
			Date[] overtimeArray = getOverlapOvertimeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertime = overtimeArray.length >= 2;
			if (isOverlapOvertimeBefore || isOverlapOvertime) {
				// 重複している場合
				if (isOverlapOvertimeBefore) {
					// 残前休憩と重複している場合
					addOverlapOvertimeBeforeRestErrorMessage(publicGoOutNameArray[i], overtimeBeforeArray[0],
							overtimeBeforeArray[1]);
				}
				if (isOverlapOvertime) {
					// 残業休憩と重複している場合
					addOverlapOvertimeRestErrorMessage(publicGoOutNameArray[i], overtimeArray[0], overtimeArray[1]);
				}
				break;
			}
		}
		// 私用外出時間
		String[] privateGoOutNameArray = new String[]{ mospParams.getName("PrivateGoingOut1"),
			mospParams.getName("PrivateGoingOut2") };
		for (int i = 0; i < privateGoOutDtoList.size(); i++) {
			GoOutDtoInterface dto = privateGoOutDtoList.get(i);
			Date[] overtimeBeforeArray = getOverlapOvertimeBeforeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertimeBefore = overtimeBeforeArray.length >= 2;
			Date[] overtimeArray = getOverlapOvertimeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertime = overtimeArray.length >= 2;
			if (isOverlapOvertimeBefore || isOverlapOvertime) {
				// 重複している場合
				if (isOverlapOvertimeBefore) {
					// 残前休憩と重複している場合
					addOverlapOvertimeBeforeRestErrorMessage(privateGoOutNameArray[i], overtimeBeforeArray[0],
							overtimeBeforeArray[1]);
				}
				if (isOverlapOvertime) {
					// 残業休憩と重複している場合
					addOverlapOvertimeRestErrorMessage(privateGoOutNameArray[i], overtimeArray[0], overtimeArray[1]);
				}
				break;
			}
		}
		// 分単位休暇A
		String[] minutelyHolidayANameArray = new String[]{ mospParams.getName("MinutelyHolidayA", "No1"),
			mospParams.getName("MinutelyHolidayA", "No2"), mospParams.getName("MinutelyHolidayA", "No3"),
			mospParams.getName("MinutelyHolidayA", "No4") };
		for (int i = 0; i < minutelyHolidayADtoList.size(); i++) {
			GoOutDtoInterface dto = minutelyHolidayADtoList.get(i);
			Date[] overtimeBeforeArray = getOverlapOvertimeBeforeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertimeBefore = overtimeBeforeArray.length >= 2;
			Date[] overtimeArray = getOverlapOvertimeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertime = overtimeArray.length >= 2;
			if (isOverlapOvertimeBefore || isOverlapOvertime) {
				// 重複している場合
				if (isOverlapOvertimeBefore) {
					// 残前休憩と重複している場合
					addOverlapOvertimeBeforeRestErrorMessage(minutelyHolidayANameArray[i], overtimeBeforeArray[0],
							overtimeBeforeArray[1]);
				}
				if (isOverlapOvertime) {
					// 残業休憩と重複している場合
					addOverlapOvertimeRestErrorMessage(minutelyHolidayANameArray[i], overtimeArray[0], overtimeArray[1]);
				}
				break;
			}
		}
		// 分単位休暇B
		String[] minutelyHolidayBNameArray = new String[]{ mospParams.getName("MinutelyHolidayB", "No1"),
			mospParams.getName("MinutelyHolidayB", "No2"), mospParams.getName("MinutelyHolidayB", "No3"),
			mospParams.getName("MinutelyHolidayB", "No4") };
		for (int i = 0; i < minutelyHolidayBDtoList.size(); i++) {
			GoOutDtoInterface dto = minutelyHolidayBDtoList.get(i);
			Date[] overtimeBeforeArray = getOverlapOvertimeBeforeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertimeBefore = overtimeBeforeArray.length >= 2;
			Date[] overtimeArray = getOverlapOvertimeRestArray(dto.getGoOutStart(), dto.getGoOutEnd());
			boolean isOverlapOvertime = overtimeArray.length >= 2;
			if (isOverlapOvertimeBefore || isOverlapOvertime) {
				// 重複している場合
				if (isOverlapOvertimeBefore) {
					// 残前休憩と重複している場合
					addOverlapOvertimeBeforeRestErrorMessage(minutelyHolidayBNameArray[i], overtimeBeforeArray[0],
							overtimeBeforeArray[1]);
				}
				if (isOverlapOvertime) {
					// 残業休憩と重複している場合
					addOverlapOvertimeRestErrorMessage(minutelyHolidayBNameArray[i], overtimeArray[0], overtimeArray[1]);
				}
				break;
			}
		}
	}
	
	/**
	 * 残前休憩時間との重複時間を取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 重複時間配列
	 */
	protected Date[] getOverlapOvertimeBeforeRestArray(Date startTime, Date endTime) {
		for (Entry<Date, Date> entry : overtimeBeforeRestMap.entrySet()) {
			Date[] array = getOverlapArray(startTime, endTime, entry.getKey(), entry.getValue());
			if (array.length == 0) {
				continue;
			}
			return array;
		}
		return new Date[0];
	}
	
	/**
	 * 残業休憩時間との重複時間を取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 重複時間配列
	 */
	protected Date[] getOverlapOvertimeRestArray(Date startTime, Date endTime) {
		for (Entry<Date, Date> entry : overtimeRestMap.entrySet()) {
			Date[] array = getOverlapArray(startTime, endTime, entry.getKey(), entry.getValue());
			if (array.length == 0) {
				continue;
			}
			return array;
		}
		return new Date[0];
	}
	
	/**
	 * 重複時間を取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param targetStartTime 対象開始時刻
	 * @param targetEndTime 対象終了時刻
	 * @return 重複時間配列
	 */
	protected Date[] getOverlapArray(Date startTime, Date endTime, Date targetStartTime, Date targetEndTime) {
		if (isOverlap(startTime, endTime, targetStartTime, targetEndTime)) {
			Date[] array = new Date[2];
			array[0] = targetStartTime;
			if (startTime.after(targetStartTime)) {
				array[0] = startTime;
			}
			array[1] = targetEndTime;
			if (endTime.before(targetEndTime)) {
				array[1] = endTime;
			}
			return array;
		}
		return new Date[0];
	}
	
	/**
	 * 時間帯の重複チェック。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param targetStartTime 対象開始時刻
	 * @param targetEndTime 対象終了時刻
	 * @return 重複している場合true、そうでない場合false
	 */
	protected boolean isOverlap(Date startTime, Date endTime, Date targetStartTime, Date targetEndTime) {
		if (startTime == null || endTime == null || targetStartTime == null || targetEndTime == null) {
			return false;
		}
		return startTime.before(targetEndTime) && endTime.after(targetStartTime);
	}
	
	/**
	 * 残前休憩時間と時間帯が重複している場合のエラーメッセージ設定。<br>
	 * @param nameOfTime 時間の名称
	 * @param overlapStartTime 重複開始時刻
	 * @param overlapEndTime 重複終了時刻
	 */
	protected void addOverlapOvertimeBeforeRestErrorMessage(String nameOfTime, Date overlapStartTime,
			Date overlapEndTime) {
		addOverlapErrorMessage(nameOfTime, mospParams.getName("RestBeforeOvertimeWork"), overlapStartTime,
				overlapEndTime);
	}
	
	/**
	 * 残業休憩時間と時間帯が重複している場合のエラーメッセージ設定。<br>
	 * @param nameOfTime 時間の名称
	 * @param overlapStartTime 重複開始時刻
	 * @param overlapEndTime 重複終了時刻
	 */
	protected void addOverlapOvertimeRestErrorMessage(String nameOfTime, Date overlapStartTime, Date overlapEndTime) {
		addOverlapErrorMessage(nameOfTime, mospParams.getName("RestInOvertime"), overlapStartTime, overlapEndTime);
	}
	
	/**
	 * 時間帯が重複している場合のエラーメッセージ設定。<br>
	 * @param nameOfTime 時間の名称
	 * @param nameOfTargetTime 対象時間の名称
	 * @param overlapStartTime 重複開始時刻
	 * @param overlapEndTime 重複終了時刻
	 */
	protected void addOverlapErrorMessage(String nameOfTime, String nameOfTargetTime, Date overlapStartTime,
			Date overlapEndTime) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_2, nameOfTime, nameOfTargetTime,
				DateUtility.getStringTime(overlapStartTime, workDate),
				DateUtility.getStringTime(overlapEndTime, workDate));
	}
	
	/**
	 * 時間帯が重複している場合のエラーメッセージ設定。<br>
	 * @param nameOfTime 時間の名称
	 * @param nameOfTargetTime 対象時間の名称
	 */
	protected void addOverlapErrorMessage(String nameOfTime, String nameOfTargetTime) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK,
				DateUtility.getStringDateAndDay(workDate), nameOfTime, nameOfTargetTime);
	}
	
	/**
	 * 勤務形態コードを取得する
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @return 勤務形態コード
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkTypeCode(String personalId, Date workDate, int timesWork) throws MospException {
		AttendanceDtoInterface dto = attendanceReference.findForKey(personalId, workDate, timesWork);
		WorkflowDtoInterface workflowDto = null;
		if (dto != null) {
			workflowDto = workflowReference.getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				return dto.getWorkTypeCode();
			}
		}
		List<SubstituteDtoInterface> substituteList = substituteReference.getSubstituteList(personalId, workDate);
		for (SubstituteDtoInterface substituteDto : substituteList) {
			WorkflowDtoInterface substituteWorkflowDto = workflowReference.getLatestWorkflowInfo(substituteDto
				.getWorkflow());
			if (substituteWorkflowDto == null
					|| !PlatformConst.CODE_STATUS_COMPLETE.equals(substituteWorkflowDto.getWorkflowStatus())) {
				continue;
			}
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				return substituteDto.getSubstituteType();
			}
		}
		DifferenceRequestReferenceBeanInterface differenceReference = (DifferenceRequestReferenceBeanInterface)createBean(
				DifferenceRequestReferenceBeanInterface.class, workDate);
		DifferenceRequestDtoInterface differenceRequestDto = differenceReference.findForKeyOnWorkflow(personalId,
				workDate);
		if (differenceRequestDto != null) {
			WorkflowDtoInterface differenceRequestWorkflowDto = workflowReference
				.getLatestWorkflowInfo(differenceRequestDto.getWorkflow());
			if (differenceRequestWorkflowDto != null
					&& PlatformConst.CODE_STATUS_COMPLETE.equals(differenceRequestWorkflowDto.getWorkflowStatus())) {
				return differenceRequestDto.getDifferenceType();
			}
		}
		Date scheduleWorkDate = workDate;
//		int scheduleTimesWork = timesWork;
		// 休日出勤申請からレコードを取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayReference.findForKeyOnWorkflow(
				personalId, workDate);
		if (workOnHolidayRequestDto == null) {
			if (workflowDto != null) {
				return dto.getWorkTypeCode();
			}
		} else {
			// ワークフロー番号取得
			long workflow = workOnHolidayRequestDto.getWorkflow();
			WorkflowDtoInterface workOnHolidayRequestWorkflowDto = workflowReference.getLatestWorkflowInfo(workflow);
			// 最新のワークフローを取得でき承認済状態の場合
			if (workOnHolidayRequestWorkflowDto != null
					&& PlatformConst.CODE_STATUS_COMPLETE.equals(workOnHolidayRequestWorkflowDto.getWorkflowStatus())) {
				// 振替申請種類取得
				int substitute = workOnHolidayRequestDto.getSubstitute();
				if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
					// 振り替える場合
					// 振替休日データリストを取得
					List<SubstituteDtoInterface> list = substituteReference.getSubstituteList(workflow);
					// 振替休日データリスト毎に処理
					for (SubstituteDtoInterface substituteDto : list) {
						// 振替日取得
						scheduleWorkDate = substituteDto.getSubstituteDate();
//						scheduleTimesWork = substituteDto.getTimesWork();
						break;
					}
				} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 振り替えない場合
					if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
						// 法定休日労働の場合
						return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
					} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workOnHolidayRequestDto
						.getWorkOnHolidayType())) {
						// 所定休日労働の場合
						return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
					}
				}
			}
		}
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, scheduleWorkDate);
		if (applicationDto == null) {
			return null;
		}
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
				scheduleWorkDate);
		if (scheduleDto == null) {
			return null;
		}
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
				scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), scheduleWorkDate);
		if (scheduleDateDto == null) {
			return null;
		}
		return scheduleDateDto.getWorkTypeCode();
	}
	
	@Override
	public int getDefferenceMinutes(Date startTime, Date endTime) {
		return super.getDefferenceMinutes(startTime, endTime);
	}
	
	/**
	 * 時刻を日付オブジェクトに変換し取得する。<br>
	 * 時刻は分単位とする。<br>
	 * 基準日は、変換の際に基準として用いる。<br>
	 * @param date 基準日
	 * @param time 時刻
	 * @return 日付オブジェクト
	 */
	protected Date getAttendanceTime(Date date, int time) {
		return getAttendanceTime(date, Integer.toString(time / TimeConst.CODE_DEFINITION_HOUR),
				Integer.toString(time % TimeConst.CODE_DEFINITION_HOUR));
	}
	
	@Override
	public Date getAttendanceTime(Date date, String hour, String minute) {
		StringBuffer sb = new StringBuffer();
		if (hour.length() == 1) {
			sb.append(0);
		}
		sb.append(hour);
		if (minute.length() == 1) {
			sb.append(0);
		}
		sb.append(minute);
		return getAttendanceTime(date, sb.toString());
	}
	
	@Override
	public Date getRoundMinute(Date time, int type, int unit) {
		if (time == null) {
			return null;
		}
		long milliseconds = time.getTime();
		if (milliseconds == 0 || type == 0 || unit <= 0) {
			return time;
		}
		if (type == 1 || type == 2) {
			// 丸め単位を分単位からミリ秒単位に変換
			int millisecondsUnit = unit * TimeConst.CODE_DEFINITION_HOUR * 1000;
			// 余り
			long remainder = milliseconds % millisecondsUnit;
			if (remainder == 0) {
				// 余りが0の場合はそのまま返す
				return time;
			}
			long rounded = milliseconds - remainder;
			if (type == 1) {
				// 切捨て
				return new Date(rounded);
			} else if (type == 2) {
				// 切上げ
				return new Date(rounded + millisecondsUnit);
			}
		}
		return time;
	}
	
	@Override
	public int getRoundMinute(int time, int type, int unit) {
		return super.getRoundMinute(time, type, unit);
	}
	
}
