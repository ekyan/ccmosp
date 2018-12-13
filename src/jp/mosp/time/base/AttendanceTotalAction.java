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
package jp.mosp.time.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.comparator.settings.AttendanceTotalComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * MosP勤怠管理におけるActionの勤怠等の自動計算を提供する。<br>
 */
public abstract class AttendanceTotalAction extends TimeAction {
	
	/**
	 * 出勤時刻
	 */
	protected Date								startTime;
	
	/**
	 * 退勤時刻
	 */
	protected Date								endTime;
	
	/**
	 * 勤務時間
	 */
	protected int								workTime;
	
	/**
	 * 休暇時間
	 */
	protected int								totalRest;
	
	/**
	 * 休暇入り1
	 */
	protected Date								restStart1;
	
	/**
	 * 休暇戻り1
	 */
	protected Date								restEnd1;
	
	/**
	 * 休暇時間1
	 */
	protected int								restTime1;
	
	/**
	 * 休暇入り2
	 */
	protected Date								restStart2;
	
	/**
	 * 休暇戻り2
	 */
	protected Date								restEnd2;
	
	/**
	 * 休暇時間2
	 */
	protected int								restTime2;
	
	/**
	 * 休暇入り3
	 */
	protected Date								restStart3;
	
	/**
	 * 休暇戻り3
	 */
	protected Date								restEnd3;
	
	/**
	 * 休暇時間3
	 */
	protected int								restTime3;
	
	/**
	 * 休暇入り4
	 */
	protected Date								restStart4;
	
	/**
	 * 休暇戻り4
	 */
	protected Date								restEnd4;
	
	/**
	 * 休暇時間4
	 */
	protected int								restTime4;
	
	/**
	 * 休暇入り5
	 */
	protected Date								restStart5;
	
	/**
	 * 休暇戻り5
	 */
	protected Date								restEnd5;
	
	/**
	 * 休暇時間5
	 */
	protected int								restTime5;
	
	/**
	 * 休暇入り6
	 */
	protected Date								restStart6;
	
	/**
	 * 休暇戻り6
	 */
	protected Date								restEnd6;
	
	/**
	 * 休暇時間6
	 */
	protected int								restTime6;
	
	/**
	 * 公用外出
	 */
	protected int								totalPublic;
	
	/**
	 * 公用外出入り1
	 */
	protected Date								publicStart1;
	
	/**
	 * 公用外出戻り1
	 */
	protected Date								publicEnd1;
	
	/**
	 * 公用外出入り2
	 */
	protected Date								publicStart2;
	
	/**
	 * 公用外出戻り2
	 */
	protected Date								publicEnd2;
	
	/**
	 * 遅刻
	 */
	protected int								lateTime;
	
	/**
	 * 早退
	 */
	protected int								leaveEarlyTime;
	
	/**
	 * 私用外出
	 */
	protected int								totalPrivate;
	
	/**
	 * 私用外出入り1
	 */
	protected Date								privateStart1;
	
	/**
	 * 私用外出戻り1
	 */
	protected Date								privateEnd1;
	
	/**
	 * 私用外出入り2
	 */
	protected Date								privateStart2;
	
	/**
	 * 私用外出戻り2
	 */
	protected Date								privateEnd2;
	
	/**
	 * 減額対象時間
	 */
	protected int								decreaseTime;
	
	/**
	 * 勤怠設定管理DTOインターフェース
	 */
	protected TimeSettingDtoInterface			timeSettingDto;
	
	/**
	 * 勤務形態項目管理DTOインターフェース
	 */
	protected WorkTypeItemDtoInterface			workTypeItemDto;
	
	/**
	 * 休日出勤申請DTOインターフェース
	 */
	protected WorkOnHolidayRequestDtoInterface	workOnHolidayDto;
	
	/**
	 * 休暇申請DTOインターフェース
	 */
	protected List<HolidayRequestDtoInterface>	holidayRequestListDto;
	
	/**
	 * カレンダマスタDTOインターフェース
	 */
	protected ScheduleDtoInterface				scheduleDto;
	
	/**
	 * 時差出勤申請DTOインターフェース
	 */
	protected DifferenceRequestDtoInterface		differenceDto;
	
	/**
	 * カレンダ日マスタDTOインターフェース
	 */
	protected ScheduleDateDtoInterface			scheduleDateDto;
	
	/**
	 * 残業申請DTOインターフェース
	 */
	protected OvertimeRequestDtoInterface		beforeOvertimeDto;
	/**
	 * 公用外出時間
	 */
	protected int								totalPublicTime;
	
	/**
	 * 残業時間
	 */
	protected int								overtimeTime;
	
	/**
	 * 始業時刻
	 */
	protected int								workstart;
	
	/**
	 * 終業時刻
	 */
	protected int								workend;
	
	/**
	 * 所定労働時間内労働
	 */
	protected int								generalWorkTime;
	
	/**
	 * 残前休憩
	 */
	protected int								overbefore;
	
	/**
	 * 残業休憩時間(毎)
	 */
	protected int								overper;
	
	/**
	 * 残業休憩時間
	 */
	protected int								overrest;
	
	/**
	 * 前半休(開始)
	 */
	protected int								frontStart;
	
	/**
	 * 前半休(終了)
	 */
	protected int								frontEnd;
	
	/**
	 * 後半休(開始)
	 */
	protected int								backStart;
	
	/**
	 * 後半休(終了)
	 */
	protected int								backEnd;
	
	/**
	 * 法定外残業時間
	 */
	protected int								overTimeOut;
	
	/**
	 * 法定外休憩時間
	 */
	protected int								overRestTime;
	
	/**
	 * 深夜勤務時間
	 */
	protected int								lateNightTime;
	
	/**
	 * 深夜休憩時間
	 */
	protected int								lateBreakTime;
	
	/**
	 * 所定休日勤務時間
	 */
	protected int								predeterminedHolidayWorkTime;
	
	/**
	 * 法定休日勤務時間
	 */
	protected int								legalHolidayWorkTime;
	
	/**
	 * 法内残業
	 */
	protected int								withinStatutoryOvertime;
	
	/**
	 * 対象日
	 */
	protected Date								targetDate;
	
	/**
	 * 暦日の所定余り時間
	 */
	protected int								prescribedCalendarDayTime;
	
	/**
	 * 暦日の法定余り時間
	 */
	protected int								legalCalendarDayTime;
	
	/**
	 * 前残業時間
	 */
	protected int								overtimeBefore;
	
	/**
	 * 後残業時間
	 */
	protected int								overtimeAfter;
	/**
	 * 
	 */
	protected String							code;
	
	
	/**
	 * 
	 */
	public AttendanceTotalAction() {
		super();
	}
	
	/**
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param workTypeCode 勤務形態コード
	 * @throws MospException 例外処理が発生した場合
	 */
	public void initAttendanceTotal(String personalId, Date targetDate, final String workTypeCode) throws MospException {
		// 設定適用マスタDTOインターフェース
		ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(personalId, targetDate);
		timeReference().application().chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 該当する設定適用が存在しない
			return;
		}
		// カレンダマスタDTOインターフェース
		ScheduleDtoInterface scheduleDto = timeReference().schedule().getScheduleInfo(applicationDto.getScheduleCode(),
				targetDate);
		timeReference().schedule().chkExistSchedule(scheduleDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 該当するカレンダマスタが存在しない
			return;
		}
		// 勤怠設定
		timeSettingDto = timeReference().timeSetting().getTimeSettingInfo(applicationDto.getWorkSettingCode(),
				targetDate);
		timeReference().timeSetting().chkExistTimeSetting(timeSettingDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 該当する勤怠設定が存在しない
			return;
		}
		// カレンダ日マスタDTOインターフェース
		scheduleDateDto = timeReference().scheduleDate().getScheduleDateInfo(scheduleDto.getScheduleCode(), targetDate,
				targetDate);
		timeReference().scheduleDate().chkExistScheduleDate(scheduleDateDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 該当するカレンダマスタが存在しない
			return;
		}
		// 残業申請
		beforeOvertimeDto = timeReference().overtimeRequest().findForKeyOnWorkflow(personalId, targetDate,
				TimeConst.CODE_OVERTIME_WORK_BEFORE);
		// 休暇申請
		holidayRequestListDto = timeReference().holidayRequest().getHolidayRequestList(personalId, targetDate);
		// 時差出勤
		differenceDto = timeReference().differenceRequest().findForKeyOnWorkflow(personalId, targetDate);
		// 休日出勤
		workOnHolidayDto = timeReference().workOnHolidayRequest().findForKeyOnWorkflow(personalId, targetDate);
		if (workOnHolidayDto != null) {
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
					workOnHolidayDto.getWorkflow());
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				if (workOnHolidayDto.getSubstitute() == 1) {
					// 振替休日を申請する場合
					List<SubstituteDtoInterface> list = timeReference().substitute().getSubstituteList(
							workOnHolidayDto.getWorkflow());
					scheduleDateDto = timeReference().scheduleDate().getScheduleDateInfo(scheduleDto.getScheduleCode(),
							targetDate, list.get(0).getSubstituteDate());
				}
			}
		}
		// 対象日の設定
		this.targetDate = targetDate;
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			setFields(scheduleDateDto.getWorkTypeCode());
		} else {
			setFields(workTypeCode);
		}
	}
	
	/**
	 * 初期化
	 */
	private void setDefaultValues() {
		workstart = 0;
		workend = 0;
		overbefore = 0;
		overper = 0;
		overrest = 0;
		frontStart = 0;
		frontEnd = 0;
		backStart = 0;
		backEnd = 0;
	}
	
	/**
	 * 
	 * @param workTypeCode 勤務形態コード
	 * @throws MospException 例外処理が発生した場合
	 */
	private void setFields(String workTypeCode) throws MospException {
		// カレンダ日マスタの所定休日、法定休日、空欄の場合
		setDefaultValues();
		code = workTypeCode;
		if (workTypeCode != null && !workTypeCode.isEmpty() && !workTypeCode.equals("prescribed_holiday")
				&& !workTypeCode.equals("legal_holiday")) {
			// カレンダ日マスタに勤怠コードが設定されている場合
			WorkTypeDtoInterface workTypeDto = timeReference().workType().getWorkTypeInfo(workTypeCode, targetDate);
			if (workTypeDto == null) {
				String errorMes = mospParams.getName("Calendar", "Day");
				mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, getStringDate(targetDate),
						errorMes);
				return;
			}
			// 初期化
			setDefaultValues();
			WorkTypeItemReferenceBeanInterface workTypeItem = timeReference().workTypeItem();
			String localWorkTypeCode = workTypeDto.getWorkTypeCode();
			// 出勤時刻,退勤時刻の取得
			if (differenceDto != null
					&& reference().workflow().getLatestWorkflowInfo(differenceDto.getWorkflow()).getWorkflowStatus()
						.equals(PlatformConst.CODE_STATUS_COMPLETE)) {
				// 時差出勤から取得する場合
				// 区分がA
				if (TimeConst.CODE_DIFFERENCE_TYPE_A.equals(differenceDto.getDifferenceType())) {
					// A：始業時刻8:00～終業時刻16:00
					workstart = 8 * 60;
					workend = 16 * 60;
					/*
						if (holidayRequestListDto != null) {
					// 休暇申請
					for (int i = 0; i < holidayRequestListDto.size(); i++) {
					WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						holidayRequestListDto.get(i).getWorkflow());
					if (workflowDto == null) {
					continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					if (holidayRequestListDto.get(i).getHolidayRange() == 2) {
						if (timeReference().workTypeItem().getWorkTypeItemInfo(code, targetDate,
								TimeConst.CODE_BACKSTART) != null) {
							workstart = setHourMinute(timeReference().workTypeItem().getWorkTypeItemInfo(code,
									targetDate, TimeConst.CODE_BACKSTART).getWorkTypeItemValue());
							lateTimeCale = getHourMinuteInt(getStartTime()) - workstart;
						}
					}
					}
					}
					} else {
					 */
				}
				// 区分がB
				if (TimeConst.CODE_DIFFERENCE_TYPE_B.equals(differenceDto.getDifferenceType())) {
					// B：始業時刻8:30～終業時刻16:30
					workstart = (8 * TimeConst.CODE_DEFINITION_HOUR) + TimeConst.TIME_HURF_HOUR_MINUTES;
					workend = (16 * TimeConst.CODE_DEFINITION_HOUR) + TimeConst.TIME_HURF_HOUR_MINUTES;
				}
				// 区分がC
				if (TimeConst.CODE_DIFFERENCE_TYPE_C.equals(differenceDto.getDifferenceType())) {
					// C：始業時刻9:30～終業時刻17:30
					workstart = (9 * TimeConst.CODE_DEFINITION_HOUR) + TimeConst.TIME_HURF_HOUR_MINUTES;
					workend = (17 * TimeConst.CODE_DEFINITION_HOUR) + TimeConst.TIME_HURF_HOUR_MINUTES;
				}
				// 区分がD
				if (TimeConst.CODE_DIFFERENCE_TYPE_D.equals(differenceDto.getDifferenceType())) {
					// D：始業時刻10:00～終業時刻18:00
					workstart = 10 * TimeConst.CODE_DEFINITION_HOUR;
					workend = 18 * TimeConst.CODE_DEFINITION_HOUR;
				}
				// 区分がS
				if (TimeConst.CODE_DIFFERENCE_TYPE_S.equals(differenceDto.getDifferenceType())) {
					workstart = setHourMinute(differenceDto.getRequestStart());
					workend = setHourMinute(differenceDto.getRequestEnd());
				}
			} else {
				// 勤務形態から取得
				// 出勤時刻
				if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_WORKSTART) != null) {
					workstart = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
							TimeConst.CODE_WORKSTART).getWorkTypeItemValue());
				}
				// 退勤時刻
				if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_WORKEND) != null) {
					workend = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
							TimeConst.CODE_WORKEND).getWorkTypeItemValue());
				}
			}
			// 残前休憩
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_OVERBEFORE) != null) {
				overbefore = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_OVERBEFORE).getWorkTypeItemValue());
			}
			// 残業休憩時間(毎)
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_OVERPER) != null) {
				overper = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_OVERPER).getWorkTypeItemValue());
			}
			// 残業休憩時間
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_OVERREST) != null) {
				overrest = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_OVERREST).getWorkTypeItemValue());
			}
			// 前半休(開始)
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_FRONTSTART) != null) {
				frontStart = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_FRONTSTART).getWorkTypeItemValue());
			}
			// 前半休(終了)
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_FRONTEND) != null) {
				frontEnd = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_FRONTEND).getWorkTypeItemValue());
			}
			// 後半休(開始)
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_BACKSTART) != null) {
				backStart = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_BACKSTART).getWorkTypeItemValue());
			}
			// 後半休(終了)
			if (workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate, TimeConst.CODE_BACKEND) != null) {
				backEnd = setHourMinute(workTypeItem.getWorkTypeItemInfo(localWorkTypeCode, targetDate,
						TimeConst.CODE_BACKEND).getWorkTypeItemValue());
			}
		}
	}
	
	/**
	 * 出勤時刻の出力
	 * @return 出勤時刻
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected Date getStartTime() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(startTime, timeSettingDto.getRoundDailyStartUnit(), timeSettingDto.getRoundDailyStart());
	}
	
	/**
	 * 退勤時刻の出力
	 * @return 退勤時刻
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getEndTime() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(endTime, timeSettingDto.getRoundDailyEndUnit(), timeSettingDto.getRoundDailyEnd());
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
		return getCheckCalcResults(roundingTreatmentCalc(workTime, timeSettingDto.getRoundDailyWorkUnit(),
				timeSettingDto.getRoundDailyWork()));
	}
	
	/**
	 * 休憩時間の出力
	 * @return 休憩時間
	 */
	public int getTotalRest() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(totalRest, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻1の出力
	 * @return 休憩入り時刻1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(restStart1, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻1の出力
	 * @return 休憩戻り時刻1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(restEnd1, timeSettingDto.getRoundDailyEndUnit(), timeSettingDto.getRoundDailyRestEnd());
	}
	
	/**
	 * 休憩時間1の出力
	 * @return 休憩時間1
	 */
	public int getRestTime1() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime1, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻2の出力
	 * @return 休憩入り時刻2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(restStart2, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻2の出力
	 * @return 休憩戻り時刻2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め単位
		return getItmeDateTime(restEnd2, timeSettingDto.getRoundDailyRestEndUnit(),
				timeSettingDto.getRoundDailyRestEnd());
	}
	
	/**
	 * 休憩時間2の出力
	 * @return 休憩時間1
	 */
	public int getRestTime2() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime2, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻3の出力
	 * @return 休憩入り時刻3
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart3() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restStart3, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻3の出力
	 * @return 休憩戻り時刻3
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd3() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restEnd3, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩時間3の出力
	 * @return 休憩時間3
	 */
	public int getRestTime3() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime3, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻4の出力
	 * @return 休憩入り時刻4
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart4() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restStart4, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻4の出力
	 * @return 休憩戻り時刻4
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd4() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restEnd4, timeSettingDto.getRoundDailyRestEndUnit(),
				timeSettingDto.getRoundDailyRestEnd());
	}
	
	/**
	 * 休憩時間4の出力
	 * @return 休憩時間4
	 */
	public int getRestTime4() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime4, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻5の出力
	 * @return 休憩入り時刻5
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart5() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restStart5, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻5の出力
	 * @return 休憩戻り時刻5
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd5() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restEnd5, timeSettingDto.getRoundDailyRestEnd(), timeSettingDto.getRoundDailyEndUnit());
	}
	
	/**
	 * 休憩時間5の出力
	 * @return 休憩時間5
	 */
	public int getRestTime5() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime5, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 休憩入り時刻6の出力
	 * @return 休憩入り時刻6
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestStart6() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restStart6, timeSettingDto.getRoundDailyRestStartUnit(),
				timeSettingDto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻り時刻6の出力
	 * @return 休憩戻り時刻6
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getRestEnd6() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(restEnd6, timeSettingDto.getRoundDailyRestEndUnit(),
				timeSettingDto.getRoundDailyRestEnd());
	}
	
	/**
	 * 休憩時間6の出力
	 * @return 休憩時間6
	 */
	public int getRestTime6() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(restTime6, timeSettingDto.getRoundDailyRestTimeUnit(),
				timeSettingDto.getRoundDailyRestTime()));
	}
	
	/**
	 * 公用外出
	 * @return 公用外出の合計値
	 */
	public int getTotalPublic() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(totalPublic);
	}
	
	/**
	 * 公用外出入り1の出力
	 * @return 公用外出入り1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getPublicStart1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(publicStart1, timeSettingDto.getRoundDailyPublicStartUnit(),
				timeSettingDto.getRoundDailyPublicStart());
	}
	
	/**
	 * 公用外出戻り1の出力
	 * @return 公用外出戻り1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public Date getPublicEnd1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(publicEnd1, timeSettingDto.getRoundDailyPublicEndUnit(),
				timeSettingDto.getRoundDailyPublicEnd());
	}
	
	/**
	 * 公用外出入り2の出力
	 * @return 公用外出入り2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPublicStart2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(publicStart2, timeSettingDto.getRoundDailyPublicStartUnit(),
				timeSettingDto.getRoundDailyPublicStart());
	}
	
	/**
	 * 公用外出戻り2の出力
	 * @return 公用外出戻り2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPublicEnd2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(publicEnd2, timeSettingDto.getRoundDailyPublicEndUnit(),
				timeSettingDto.getRoundDailyPublicEnd());
	}
	
	/**
	 * 遅刻の出力
	 * @return 遅刻
	 */
	public int getLateTime() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(lateTime, timeSettingDto.getRoundDailyLateUnit(),
				timeSettingDto.getRoundDailyLate()));
	}
	
	/**
	 * 早退の出力
	 * @return 早退
	 */
	public int getLeaveEarlyTime() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(leaveEarlyTime, timeSettingDto.getRoundDailyLeaveEarlyUnit(),
				timeSettingDto.getRoundDailyLeaveEarly()));
	}
	
	/**
	 * 私用外出の出力
	 * @return 私用外出
	 */
	public int getTotalPrivate() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(totalPrivate);
	}
	
	/**
	 * 私用外出入り1の出力
	 * @return 私用外出入り1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPrivateStart1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		
		// 丸め処理
		return getItmeDateTime(privateStart1, timeSettingDto.getRoundDailyStartUnit(),
				timeSettingDto.getRoundDailyStart());
	}
	
	/**
	 * 私用外出戻り1の出力
	 * @return 私用外出戻り1
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPrivateEnd1() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(privateEnd1, timeSettingDto.getRoundDailyEndUnit(), timeSettingDto.getRoundDailyEnd());
	}
	
	/**
	 * 私用外出入り2の出力
	 * @return 私用外出入り2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPrivateStart2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(privateStart2, timeSettingDto.getRoundDailyStartUnit(),
				timeSettingDto.getRoundDailyStart());
	}
	
	/**
	 * 私用外出戻り2の出力
	 * @return 私用外出戻り2
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getPrivateEnd2() throws MospException {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return null;
		}
		// 丸め処理
		return getItmeDateTime(privateEnd2, timeSettingDto.getRoundDailyStartUnit(),
				timeSettingDto.getRoundDailyStart());
	}
	
	/**
	 * @param time 対象時間
	 * @param unit 対象丸め単位
	 * @param roundType 対象丸め単位
	 * @return 日付オブジェクト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合 
	 */
	public Date getItmeDateTime(Date time, int unit, int roundType) throws MospException {
		Date dateHourMinute = getHourMinuteDate(roundingTreatmentCalc(getHourMinuteInt(time), unit, roundType));
		int year = DateUtility.getYear(time);
		int month = DateUtility.getMonth(time);
		int day = DateUtility.getDay(time);
		int hour = DateUtility.getHour(dateHourMinute);
		int minute = DateUtility.getMinute(dateHourMinute);
		return DateUtility.getDateTime(year, month, day, hour, minute);
	}
	
	/**
	 * 減額対象時間の出力
	 * @return 減額対象時間
	 */
	public int getDecreaseTime() {
		// 勤怠設定管理DTOが存在しない
		if (timeSettingDto == null) {
			return 0;
		}
		// 丸め処理
		return getCheckCalcResults(roundingTreatmentCalc(decreaseTime, timeSettingDto.getRoundDailyDecreaseTimeUnit(),
				timeSettingDto.getRoundDailyDecreaseTime()));
	}
	
	/**
	 * @return overtimeTime
	 */
	public int getOvertimeTime() {
		return overtimeTime;
	}
	
	/**
	 * @return overTimeOut
	 */
	public int getOverTimeOut() {
		return getCheckCalcResults(overTimeOut);
	}
	
	/**
	 * @return generalWorkTime
	 */
	public int getGeneralWorkTime() {
		return generalWorkTime;
	}
	
	/**
	 * @return predeterminedHolidayWorkTime
	 */
	public int getPredeterminedHolidayWorkTime() {
		return predeterminedHolidayWorkTime;
	}
	
	/**
	 * @return withinStatutoryOvertime
	 */
	public int getWithinStatutoryOvertime() {
		return withinStatutoryOvertime;
	}
	
	/**
	 * @return legalHolidayWorkTime
	 */
	public int getLegalHolidayWorkTime() {
		return legalHolidayWorkTime;
	}
	
	/**
	 * @return lateNightTime
	 */
	public int getLateNightTime() {
		return lateNightTime;
	}
	
	/**
	 * @return lateBreakTime
	 */
	public int getLateBreakTime() {
		return lateBreakTime;
	}
	
	/**
	 * @return overRestTime
	 */
	public int getOverRestTime() {
		return getCheckCalcResults(overRestTime);
	}
	
	/**
	 * @return overtimeBefore
	 */
	public int getOvertimeBefore() {
		return getCheckCalcResults(overtimeBefore);
	}
	
	/**
	 * @return overtimeAfter
	 */
	public int getOvertimeAfter() {
		return getCheckCalcResults(overtimeAfter);
	}
	
	/**
	 * @param startTime セットする startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @param endTime セットする endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @param workTime セットする workTime
	 */
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	/**
	 * @param totalRest セットする totalRest
	 */
	public void setTotaltRest(int totalRest) {
		this.totalRest = totalRest;
	}
	
	/**
	 * @param restStart1 セットする restStart1
	 */
	public void setRestStart1(Date restStart1) {
		this.restStart1 = restStart1;
	}
	
	/**
	 * @param restEnd1 セットする restEnd1
	 */
	public void setRestEnd1(Date restEnd1) {
		this.restEnd1 = restEnd1;
	}
	
	/**
	 * @param restTime1 セットする restTime1
	 */
	public void setRestTime1(int restTime1) {
		this.restTime1 = restTime1;
	}
	
	/**
	 * @param restStart2 セットする restStart2
	 */
	public void setRestStart2(Date restStart2) {
		this.restStart2 = restStart2;
	}
	
	/**
	 * @param restEnd2 セットする restEnd2
	 */
	public void setRestEnd2(Date restEnd2) {
		this.restEnd2 = restEnd2;
	}
	
	/**
	 * @param restTime2 セットする restTime2
	 */
	public void setRestTime2(int restTime2) {
		this.restTime2 = restTime2;
	}
	
	/**
	 * @param restStart3 セットする restStart3
	 */
	public void setRestStart3(Date restStart3) {
		this.restStart3 = restStart3;
	}
	
	/**
	 * @param restEnd3 セットする restEnd3
	 */
	public void setRestEnd3(Date restEnd3) {
		this.restEnd3 = restEnd3;
	}
	
	/**
	 * @param restTime3 セットする restTime3
	 */
	public void setRestTime3(int restTime3) {
		this.restTime3 = restTime3;
	}
	
	/**
	 * @param restStart4 セットする restStart4
	 */
	public void setRestStart4(Date restStart4) {
		this.restStart4 = restStart4;
	}
	
	/**
	 * @param restEnd4 セットする restEnd4
	 */
	public void setRestEnd4(Date restEnd4) {
		this.restEnd4 = restEnd4;
	}
	
	/**
	 * @param restTime4 セットする restTime4
	 */
	public void setRestTime4(int restTime4) {
		this.restTime4 = restTime4;
	}
	
	/**
	 * @param restStart5 セットする restStart5
	 */
	public void setRestStart5(Date restStart5) {
		this.restStart5 = restStart5;
	}
	
	/**
	 * @param restEnd5 セットする restEnd5
	 */
	public void setRestEnd5(Date restEnd5) {
		this.restEnd5 = restEnd5;
	}
	
	/**
	 * @param restTime5 セットする restTime5
	 */
	public void setRestTime5(int restTime5) {
		this.restTime5 = restTime5;
	}
	
	/**
	 * @param restStart6 セットする restStart6
	 */
	public void setRestStart6(Date restStart6) {
		this.restStart6 = restStart6;
	}
	
	/**
	 * @param restEnd6 セットする restEnd6
	 */
	public void setRestEnd6(Date restEnd6) {
		this.restEnd6 = restEnd6;
	}
	
	/**
	 * @param restTime6 セットする restTime6
	 */
	public void setRestTime6(int restTime6) {
		this.restTime6 = restTime6;
	}
	
	/**
	 * @param totalPublic セットする totalPublic
	 */
	public void setTotalPublic(int totalPublic) {
		this.totalPublic = totalPublic;
	}
	
	/**
	 * @param publicStart1 セットする publicStart1
	 */
	public void setPublicStart1(Date publicStart1) {
		this.publicStart1 = publicStart1;
	}
	
	/**
	 * @param publicEnd1 セットする publicEnd1
	 */
	public void setPublicEnd1(Date publicEnd1) {
		this.publicEnd1 = publicEnd1;
	}
	
	/**
	 * @param publicStart2 セットする publicStart2
	 */
	public void setPublicStart2(Date publicStart2) {
		this.publicStart2 = publicStart2;
	}
	
	/**
	 * @param publicEnd2 セットする publicEnd2
	 */
	public void setPublicEnd2(Date publicEnd2) {
		this.publicEnd2 = publicEnd2;
	}
	
	/**
	 * @param lateTime セットする lateTime
	 */
	public void setLateTime(int lateTime) {
		this.lateTime = lateTime;
	}
	
	/**
	 * @param leaveEarlyTime セットする leaveEarlyTime
	 */
	public void setLeaveEarlyTime(int leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	/**
	 * @param totalPrivate セットする totalPrivate
	 */
	public void setTotalPrivate(int totalPrivate) {
		this.totalPrivate = totalPrivate;
	}
	
	/**
	 * @param privateStart1 セットする privateStart1
	 */
	public void setPrivateStart1(Date privateStart1) {
		this.privateStart1 = privateStart1;
	}
	
	/**
	 * @param privateEnd1 セットする privateEnd1
	 */
	public void setPrivateEnd1(Date privateEnd1) {
		this.privateEnd1 = privateEnd1;
	}
	
	/**
	 * @param privateStart2 セットする privateStart2
	 */
	public void setPrivateStart2(Date privateStart2) {
		this.privateStart2 = privateStart2;
	}
	
	/**
	 * @param privateEnd2 セットする privateEnd2
	 */
	public void setPrivateEnd2(Date privateEnd2) {
		this.privateEnd2 = privateEnd2;
	}
	
	/**
	 * @param decreaseTime セットする decreaseTime
	 */
	public void setDecreaseTime(int decreaseTime) {
		this.decreaseTime = decreaseTime;
	}
	
	/**
	 * @param overTimeOut セットする overTimeOut
	 */
	public void setOverTimeOut(int overTimeOut) {
		this.overTimeOut = overTimeOut;
	}
	
	/**
	 * @param overRestTime セットする overRestTime
	 */
	public void setOverRestTime(int overRestTime) {
		this.overRestTime = overRestTime;
	}
	
	/**
	 * @param overtimeTime セットする overtimeTime
	 */
	public void setOvertimeTime(int overtimeTime) {
		this.overtimeTime = overtimeTime;
	}
	
	/**
	 * @param lateNightTime セットする lateNightTime
	 */
	public void setLateNightTime(int lateNightTime) {
		this.lateNightTime = lateNightTime;
	}
	
	/**
	 * @param lateBreakTime セットする lateBreakTime
	 */
	public void setLateBreakTime(int lateBreakTime) {
		this.lateBreakTime = lateBreakTime;
	}
	
	/**
	 * @param generalWorkTime セットする generalWorkTime
	 */
	public void setGeneralWorkTime(int generalWorkTime) {
		this.generalWorkTime = generalWorkTime;
	}
	
	/**
	 * @param predeterminedHolidayWorkTime セットする predeterminedHolidayWorkTime
	 */
	public void setPredeterminedHolidayWorkTime(int predeterminedHolidayWorkTime) {
		this.predeterminedHolidayWorkTime = predeterminedHolidayWorkTime;
	}
	
	/**
	 * @param legalHolidayWorkTime セットする legalHolidayWorkTime
	 */
	public void setLegalHolidayWorkTime(int legalHolidayWorkTime) {
		this.legalHolidayWorkTime = legalHolidayWorkTime;
	}
	
	/**
	 * @param withinStatutoryOvertime セットする withinStatutoryOvertime
	 */
	public void setWithinStatutoryOvertime(int withinStatutoryOvertime) {
		this.withinStatutoryOvertime = withinStatutoryOvertime;
	}
	
	/**
	 * @param overtimeBefore セットする overtimeBefore
	 */
	public void setOvertimeBefore(int overtimeBefore) {
		this.overtimeBefore = overtimeBefore;
	}
	
	/**
	 * @param overtimeAfter セットする overtimeAfter
	 */
	public void setOvertimeAfter(int overtimeAfter) {
		this.overtimeAfter = overtimeAfter;
	}
	
	/*自動計算 */
	/**
	 * 勤務時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setWorkTimeCalc() throws MospException {
		// 残業予定時間
		int planBeforeOvertime = 0;
		// 出勤時刻 
		int perfStartTime = getHourMinuteInt(getStartTime());
		// 勤務形態の始業時刻
		int planStartTime = workstart;
		// 計算出勤時刻
		int startTimeCalc = workstart;
		// 残業申請のチェック
		if (beforeOvertimeDto != null) {
			// 申請状況のチェック
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
					beforeOvertimeDto.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 残業予定時間の設定
				planBeforeOvertime = beforeOvertimeDto.getRequestTime();
			}
		}
		// 前残業処理
		// 出勤時刻、始業時刻の差分を取得
		if (perfStartTime < planStartTime) {
			// 低い差分時間の取得
			int delta = planStartTime - perfStartTime;
			// 差分時間が残業予定時間よりも少ない
			if (delta < planBeforeOvertime) {
				// 追加時間内に差分時間を移す
				startTimeCalc = startTimeCalc - delta;
			} else {
				// 追加時間内に残業予定を移す
				startTimeCalc = startTimeCalc - planBeforeOvertime;
			}
		}
		//（退勤時刻 - 出勤時刻）- 休憩時間 - (公用外出 + 私用外出）
		setWorkTime(getCheckEndTime() - startTimeCalc - getTotalRest() - (getTotalPublic() - getTotalPrivate()));
	}
	
	/**
	 * 休憩時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setBreakTimeCalc() throws MospException {
		//（休憩1～休憩6の合計時間）+　残業休憩時間(勤務携帯)+ 残前休憩(勤務携帯)
		// 手動休憩の合計を求める
		// 休憩1の合計値
		int rest1 = getHourMinuteInt(getRestEnd1()) - getHourMinuteInt(getRestStart1());
		// 休憩2の合計値		
		int rest2 = getHourMinuteInt(getRestEnd2()) - getHourMinuteInt(getRestStart2());
		// 休憩3の合計値
		int rest3 = getHourMinuteInt(getRestEnd3()) - getHourMinuteInt(getRestStart3());
		// 休憩4の合計値
		int rest4 = getHourMinuteInt(getRestEnd4()) - getHourMinuteInt(getRestStart4());
		// 休憩5の合計値
		int rest5 = getHourMinuteInt(getRestEnd5()) - getHourMinuteInt(getRestStart5());
		// 休憩6の合計値
		int rest6 = getHourMinuteInt(getRestEnd6()) - getHourMinuteInt(getRestStart6());
		// 休憩の合計値
		int rest = rest1 + rest2 + rest3 + rest4 + rest5 + rest6;
		// 残前休憩
		int before = 0;
		// 退勤時間の時分
		int endTimeHourMinute = getCheckEndTime();
		// 残業休憩時間の合計
		int totalOverRest = 0;
		// 退勤時間が終業時間を超えている場合
		if ((overbefore + workend) <= endTimeHourMinute) {
			// 残前休憩の設定
			before = overbefore;
			//  残業休憩時間が登録されている場合
			if (overper != 0) {
				// 退勤時間 - 終業時間 + 残前休憩 / 残業休憩時間(毎)
				if (0 < (endTimeHourMinute - (overbefore + workend)) / overper) {
					// 残業休憩時間の合計を求める
					totalOverRest = ((endTimeHourMinute - (overbefore + workend)) / overper) * overrest;
				}
			}
		}
		// 手動休憩の合計と残前休憩と残業休憩時間を加算
		setTotaltRest(rest + before + totalOverRest);
	}
	
	/**
	 * 法定外時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setLegalOutTimeCalc() throws MospException {
		// 
		int workTime = 0;
		// 
		int workStart = getHourMinuteInt(getStartTime());
		// 現在は使用しないが今後使う可能性あり 
//		int restCount = 0;
		// 現在は使用しないが今後使う可能性あり 
//		int overTime = 0;
		// 
		int restTime = 0;
		// 法定外勤務時間設定
		int overTimeOut = 0;
		// 法定外休憩時間設定
		int overRestTime = 0;
		// 手動休憩開始、終了時間のリスト用意
		List<Integer> restStart = new ArrayList<Integer>();
		List<Integer> restEnd = new ArrayList<Integer>();
		// スケジュール内の勤務形態コードが所定、法定で無い場合
		if (!scheduleDateDto.getWorkTypeCode().equals("prescribed_holiday")
				|| !scheduleDateDto.getWorkTypeCode().equals("legal_holiday")) {
			// 法定内勤務時間を設定
			// 休憩時間の設定
			if (getRestStart1() != null) {
				restStart.add(getHourMinuteInt(getRestStart1()));
				restEnd.add(getHourMinuteInt(getRestEnd1()));
			}
			if (getRestStart2() != null) {
				restStart.add(getHourMinuteInt(getRestStart2()));
				restEnd.add(getHourMinuteInt(getRestEnd2()));
			}
			if (getRestStart3() != null) {
				restStart.add(getHourMinuteInt(getRestStart3()));
				restEnd.add(getHourMinuteInt(getRestEnd3()));
			}
			if (getRestStart4() != null) {
				restStart.add(getHourMinuteInt(getRestStart4()));
				restEnd.add(getHourMinuteInt(getRestEnd4()));
			}
			if (getRestStart5() != null) {
				restStart.add(getHourMinuteInt(getRestStart5()));
				restEnd.add(getHourMinuteInt(getRestEnd5()));
			}
			if (getRestStart6() != null) {
				restStart.add(getHourMinuteInt(getRestStart6()));
				restEnd.add(getHourMinuteInt(getRestEnd6()));
			}
			// 必要な休憩時間のソート(時間が若い順)
			Collections.sort(restStart, new AttendanceTotalComparator());
			Collections.sort(restEnd, new AttendanceTotalComparator());
			
			// 出勤時刻から一番若い時間帯を引く
			for (int i = 0; i < restStart.size(); i++) {
				if ((8 * TimeConst.CODE_DEFINITION_HOUR) < workTime) {
//					overTime = (8 * 60) - workTime;
					continue;
				}
				workTime = workTime + restStart.get(i) - workStart;
				workStart = restEnd.get(i);
				restTime = restTime + restEnd.get(i) - restStart.get(i);
//				restCount = i;
			}
			// 法定外勤務時間を求める
			// 法定外勤務時間設定の設定
			overTimeOut = getWorkTime() - (8 * TimeConst.CODE_DEFINITION_HOUR);
			// 法定外休憩時間設定の設定
			overRestTime = getTotalRest() + restTime;
		}
		// 法定外勤務時間設定		
		setOverTimeOut(overTimeOut);
		// 法定外休憩時間設定
		setOverRestTime(overRestTime);
	}
	
	/**
	 * 公用外出時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setPublicGoingOutTimeCalc() throws MospException {
		// 公用外出1～公用外出2の合計時間
		setTotalPublic((getHourMinuteInt(getPublicEnd1()) - getHourMinuteInt(getPublicStart1()))
				+ (getHourMinuteInt(getPublicEnd2()) - getHourMinuteInt(getPublicStart2())));
	}
	
	/**
	 * 私用外出時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setPrivateGoingOutTimeCalc() throws MospException {
		// 私用外出1～私用外出2の合計時間
		setTotalPrivate((getHourMinuteInt(getPrivateEnd1()) - getHourMinuteInt(getPrivateStart1()))
				+ (getHourMinuteInt(getPrivateEnd2()) - getHourMinuteInt(getPrivateStart2())));
	}
	
	/**
	 * 遅刻時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setTardinessTimeCalc() throws MospException {
		// 限度時間を越えている場合は申請時の確認処理でチェックする
		
		// 初期化
		int lateTimeCale = 0;
		// 休日出勤申請が存在している場合、休日出勤を行う。
		if (workOnHolidayDto != null) {
			// ワークフローDTO
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
					workOnHolidayDto.getWorkflow());
			// 申請状態が完了チェック
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
					// 振替休日を申請している場合
					List<SubstituteDtoInterface> list = timeReference().substitute().getSubstituteList(
							workOnHolidayDto.getWorkflow());
					boolean isSubstituteAll = false;
					boolean isSubstituteAm = false;
					boolean isSubstitutePm = false;
					for (SubstituteDtoInterface substituteDto : list) {
						int substituteRange = substituteDto.getSubstituteRange();
						if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
							// 全休の場合
							isSubstituteAll = true;
						} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
							// 午前休の場合
							isSubstituteAm = true;
						} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							// 午後休の場合
							isSubstitutePm = true;
						}
					}
					if (isSubstituteAll) {
						boolean isHolidayAm = false;
						for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestListDto) {
							WorkflowDtoInterface holidayRequestWorkflowDto = reference().workflow()
								.getLatestWorkflowInfo(holidayRequestDto.getWorkflow());
							if (PlatformConst.CODE_STATUS_COMPLETE
								.equals(holidayRequestWorkflowDto.getWorkflowStatus())) {
								if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
									// 午前半休の場合
									isHolidayAm = true;
									break;
								}
							}
						}
						// 出勤時刻 - 勤務形態の開始時間
						lateTimeCale = getHourMinuteInt(getStartTime()) - workstart;
						if (isHolidayAm) {
							// 出勤時間 - 後半休
							lateTimeCale = getHourMinuteInt(getStartTime()) - backStart;
						}
					} else if (isSubstituteAm) {
						// 出勤時間 - 前半休
						lateTimeCale = getHourMinuteInt(getStartTime()) - frontStart;
					} else if (isSubstitutePm) {
						// 出勤時間 - 後半休
						lateTimeCale = getHourMinuteInt(getStartTime()) - backStart;
					}
				}
			}
		} else if (holidayRequestListDto != null) {
			// 休暇申請
			for (int i = 0; i < holidayRequestListDto.size(); i++) {
				WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						holidayRequestListDto.get(i).getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				// 下書状態の場合
				if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					// 午前休の場合
					if (holidayRequestListDto.get(i).getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
						// 勤務形態項目が存在する場合
						if (timeReference().workTypeItem().getWorkTypeItemInfo(code, targetDate,
								TimeConst.CODE_BACKSTART) != null) {
							workstart = setHourMinute(timeReference().workTypeItem()
								.getWorkTypeItemInfo(code, targetDate, TimeConst.CODE_BACKSTART).getWorkTypeItemValue());
							lateTimeCale = getHourMinuteInt(getStartTime()) - workstart;
						}
					}
				}
			}
			
		} else {
			// 出勤時刻 - 勤務形態の開始時間
			lateTimeCale = getHourMinuteInt(getStartTime()) - workstart;
		}
		setLateTime(lateTimeCale);
	}
	
	/**
	 * 早退時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setLeaveEarlyTimeCalc() throws MospException {
		// 限度時間を越えている場合は申請時の確認処理でチェックする
		
		// 初期化
		int leaveEarlyTimeCale = 0;
		// 休日出勤の場合
		if (workOnHolidayDto != null) {
			// ワークフローDTO
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
					workOnHolidayDto.getWorkflow());
			// 申請状態が完了チェック
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
					// 振替休日を申請している場合
					List<SubstituteDtoInterface> list = timeReference().substitute().getSubstituteList(
							workOnHolidayDto.getWorkflow());
					boolean isSubstituteAll = false;
					boolean isSubstituteAm = false;
					boolean isSubstitutePm = false;
					for (SubstituteDtoInterface substituteDto : list) {
						int substituteRange = substituteDto.getSubstituteRange();
						if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
							// 全休の場合
							isSubstituteAll = true;
						} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
							// 午前休の場合
							isSubstituteAm = true;
						} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							// 午後休の場合
							isSubstitutePm = true;
						}
					}
					if (isSubstituteAll) {
						boolean isHolidayPm = false;
						for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestListDto) {
							WorkflowDtoInterface holidayRequestWorkflowDto = reference().workflow()
								.getLatestWorkflowInfo(holidayRequestDto.getWorkflow());
							if (PlatformConst.CODE_STATUS_COMPLETE
								.equals(holidayRequestWorkflowDto.getWorkflowStatus())) {
								if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
									// 午後半休の場合
									isHolidayPm = true;
									break;
								}
							}
						}
						// 勤務形態の終了時間 - 退勤時刻
						leaveEarlyTimeCale = workend - getCheckEndTime();
						if (isHolidayPm) {
							// 前半休 - 退勤時刻
							leaveEarlyTimeCale = frontEnd - getHourMinuteInt(getStartTime());
						}
					} else if (isSubstituteAm) {
						// 前半休 - 退勤時刻
						leaveEarlyTimeCale = frontEnd - getHourMinuteInt(getStartTime());
					} else if (isSubstitutePm) {
						// 後半休 - 退勤時刻
						leaveEarlyTimeCale = backEnd - getHourMinuteInt(getStartTime());
					}
				}
			}
		} else if (holidayRequestListDto != null) {
			// 休暇申請
			for (int i = 0; i < holidayRequestListDto.size(); i++) {
				WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						holidayRequestListDto.get(i).getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					if (holidayRequestListDto.get(i).getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						if (timeReference().workTypeItem().getWorkTypeItemInfo(code, targetDate,
								TimeConst.CODE_FRONTEND) != null) {
							workend = setHourMinute(timeReference().workTypeItem()
								.getWorkTypeItemInfo(code, targetDate, TimeConst.CODE_FRONTEND).getWorkTypeItemValue());
							leaveEarlyTimeCale = workend - getCheckEndTime();
						}
					}
				}
			}
		} else {
			// 勤務形態の終了時間 - 退勤時刻
			leaveEarlyTimeCale = workend - getCheckEndTime();
		}
		setLeaveEarlyTime(leaveEarlyTimeCale);
	}
	
	/**
	 * 残業時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setOvertimeTimeCalc() throws MospException {
		// 初期化
		// 残業時間合計
		int overtimeAfterCalc = 0;
		// 前残業時間
		int overtimeBeforeCalc = 0;
		// 終業時刻に残前休憩を足した時間 - 退勤時刻
		// 残業開始時間の取得
		// 前残業時間（残前休憩 + 終業時刻また時差出勤で申請のある退勤時間）
		int overtimeStart = overbefore + workend;
		
		int previousOvertime = 0;
		// 合計残業時間
		int totalOvertime = 0;
		// 合計手動休憩時間
		int totalManualRest = 0;
		// 残業休憩繰越回数
		int overtimeRestNum = 0;
		// 余り時間の初期化
		prescribedCalendarDayTime = 0;
		legalCalendarDayTime = 0;
		// 休日出勤申請
		if (workOnHolidayDto != null) {
			// 休日出勤申請がある場合
			// ワークフローDTO
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
					workOnHolidayDto.getWorkflow());
			// 申請状態が完了チェック
			if (workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
				// KOIKE 休日出勤申請の仕様が変わるので対応不可 
				// 振替申請するしないのチェック
				if (workOnHolidayDto.getSubstitute() == 0) {
					// する場合
					// 休日出勤時の午前休のみの計算
					// 振替範囲1が前半休、振替範囲2が空欄
//					if (workOnHolidayDto.getSubstituteRange1() == 2 || workOnHolidayDto.getSubstituteRange2() == 0) {
					
//					}
					// TODO 休日出勤時の午後休のみの計算
					// TODO 休日出勤時の午前休と午後休のみの計算
				} else {
					// しない場合
					// 対象日の勤務形態を取得する
					if (scheduleDateDto.getWorkTypeCode().equals("prescribed_holiday")) {
						// 所定休日の場合、勤務時間を残業時間に設定
						overtimeAfterCalc = getWorkTime();
					} else if (scheduleDateDto.getWorkTypeCode().equals("legal_holiday")) {
						// 法定休日の場合、残業時間は0に設定
						overtimeAfterCalc = 0;
					}
				}
			}
		} else {
			// 通常の勤怠
			// 残業開始時間よりも退勤時間が上回っていた場合
			if (overtimeStart < getCheckEndTime()) {
				// 勤務形態の始業時刻と今日の出勤時間を比べて合計残業時間に設定
				
				// 残業時間越えの手動休憩時間を設定
				// 残業開始時間越えの休憩時間の取得
				// 休憩1
				if (overtimeStart < getHourMinuteInt(getRestStart1())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd1())
							- getHourMinuteInt(getRestStart1());
				}
				// 休憩2
				if (overtimeStart < getHourMinuteInt(getRestStart2())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd2())
							- getHourMinuteInt(getRestStart2());
				}
				// 休憩3
				if (overtimeStart < getHourMinuteInt(getRestStart3())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd3())
							- getHourMinuteInt(getRestStart3());
				}
				// 休憩4
				if (overtimeStart < getHourMinuteInt(getRestStart4())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd4())
							- getHourMinuteInt(getRestStart4());
				}
				// 休憩5
				if (overtimeStart < getHourMinuteInt(getRestStart5())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd5())
							- getHourMinuteInt(getRestStart5());
				}
				// 休憩6
				if (overtimeStart < getHourMinuteInt(getRestStart6())) {
					totalManualRest = totalManualRest + getHourMinuteInt(getRestEnd6())
							- getHourMinuteInt(getRestStart6());
				}
				// 合計残業時間の設定
				totalOvertime = (getCheckEndTime() + totalManualRest) - overtimeStart;
				// 残業休憩繰越回数の設定
				if (overper > 1) {
					overtimeRestNum = totalOvertime / overper;
				}
				// 残業時間設定
				overtimeAfterCalc = (totalOvertime + previousOvertime) - (overtimeRestNum * overrest);
				// 暦日の対応
				// (残業開始時間＋残業時間)が24時を超えている場合、勤怠設定画面のフラグを確認する
				if (overtimeStart + overtimeAfterCalc < (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR)) {
					// 所定休日取扱が暦日の場合（1：通常、2：暦日）
					if (timeSettingDto.getSpecificHolidayHandling() == 2) {
						// 暦日の場合、対象日の次の日のカレンダを取得
						ScheduleDateDtoInterface nextScheduleDateDto = timeReference().scheduleDate()
							.getScheduleDateInfo(scheduleDto.getScheduleCode(), DateUtility.addDay(targetDate, 1),
									DateUtility.addDay(targetDate, 1));
						if (nextScheduleDateDto != null) {
							// 次の日のカレンダを確認し、カレンダが休日（所定、法定）の場合
							if (nextScheduleDateDto.getWorkTypeCode().equals("prescribed_holiday")) {
								// 余り時間の取得
								prescribedCalendarDayTime = totalOvertime + previousOvertime
										- (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR);
							} else if (nextScheduleDateDto.getWorkTypeCode().equals("legal_holiday")) {
								// 余り時間の取得
								legalCalendarDayTime = totalOvertime + previousOvertime
										- (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR);
							}
							// 24時間から残業開始時間を引いた値を残業時間とする
							overtimeAfterCalc = (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR)
									- overtimeStart;
						}
					}
				}
			}
		}
		// 前残業時間の取得
		// 勤怠設定の勤務前残業をチェック
		if (timeSettingDto.getBeforeOvertimeFlag() == TimeConst.CODE_BEFORE_OVERTIME_VALID) {
			// 勤務前残業が有効の場合、対象の残業申請をチェック
			if (beforeOvertimeDto != null) {
				// 承認状況の取得
				WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						beforeOvertimeDto.getWorkflow());
				// 承認状況が完了の場合
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 実績の取得
					int before = 0;
					if (getHourMinuteInt(getStartTime()) < workstart) {
						before = workstart - getHourMinuteInt(getStartTime());
					}
					// 残業申請と比べて前残業時間の取得
					if (before < beforeOvertimeDto.getRequestTime()) {
						overtimeBeforeCalc = before;
					} else {
						overtimeBeforeCalc = beforeOvertimeDto.getRequestTime();
					}
				}
			}
		}
		// 残業時間の設定
		setOvertimeTime(overtimeAfterCalc + overtimeBeforeCalc);
		// 前残業時間の設定
		setOvertimeBefore(overtimeBeforeCalc);
		// 後残業時間の設定
		setOvertimeAfter(overtimeAfterCalc);
	}
	
	/**
	 * 深夜勤務時間の計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setLateNightWorkCalc() throws MospException {
		// 繰越カウント
		int repeatCount = 0;
		// 合計残業時間
		int totalOvertime = 0;
		// 深夜時間
		int lateNight = 0;
		// 深夜休憩
		int lateNightRest = 0;
		// 深夜開始時間
		int lateNightStart = TimeConst.TIME_NIGHT_WORK_START * TimeConst.CODE_DEFINITION_HOUR;
		// 深夜終了時間
		int lateNightEnd = TimeConst.TIME_NIGHT_WORK_END * TimeConst.CODE_DEFINITION_HOUR;
		// 深夜開始時間と退勤時間を比べる
		if (lateNightStart < getCheckEndTime()) {
			// 退勤時間の方が大きい場合
			
			// 繰越カウントの設定
			if (overper > 1) {
				repeatCount = (getCheckEndTime() - (overbefore + workend)) / overper;
			}
			// 加算値
			// 加算値の初期値として終業時刻と残前休憩の加算値を設定
			totalOvertime = workend + overbefore;
			// 
			for (int i = 0; i < repeatCount; i++) {
				// 合計残業時間に残業休憩時間(毎)を設定
				totalOvertime = totalOvertime + overper;
				// 合計残業時間と深夜終了時間を比べる
				if (totalOvertime < lateNightEnd) {
					// 深夜開始時間と合計時間を比べる
					if (lateNightStart < totalOvertime) {
						// 合計残業時間と22時間を比べる
						// 合計残業時間が大きい場合
						// 合計残業時間と退勤時間を比べる
						// 合計時間が大きい場合
						// 深夜時間設定
						lateNight = lateNight + (totalOvertime - lateNightStart);
						// 深夜休憩設定
						lateNightRest++;
					}
				} else {
					lateNight = totalOvertime - lateNightEnd;
				}
			}
			lateNight = getCheckEndTime() - lateNightStart;
		}
		// 深夜勤務時間設定
		setLateNightTime(lateNight - (lateNightRest * overrest));
		// 深夜休憩時間設定
		setLateBreakTime(lateNightRest * overrest);
	}
	
	/**
	 * 所定休日勤務時間の計算
	 * @throws MospException MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setPredeterminedHolidayWorkTimeCalc() throws MospException {
		// 初期化
		int predeterminedHolidayWorkTimeCalc = 0;
		// 対象日が所定休日かチェック
		if (scheduleDateDto.getWorkTypeCode().equals("prescribed_holiday")) {
			// 休日出勤申請
			if (workOnHolidayDto != null) {
				// ワークフローDTO
				WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						workOnHolidayDto.getWorkflow());
				// 申請状態が完了チェック
				if (workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
					// 休日出勤を申請しない
					if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
						// 勤務時間と所定労働時間を比べる
						if (getWorkTime() < getHourMinuteInt(timeSettingDto.getGeneralWorkTime())) {
							// 勤務時間が小さい場合
							predeterminedHolidayWorkTimeCalc = getHourMinuteInt(timeSettingDto.getGeneralWorkTime());
						} else {
							// 勤務時間が大きい場合
							predeterminedHolidayWorkTimeCalc = getWorkTime();
						}
					}
				}
			}
		}
		setPredeterminedHolidayWorkTime(predeterminedHolidayWorkTimeCalc + prescribedCalendarDayTime);
	}
	
	/**
	 * 法定休日勤務時間の計算
	 * @throws MospException MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void setLegalHolidayWorkTimeCalc() throws MospException {
		// 初期化
		int legalHolidayWorkTime = 0;
		// 対象日が法定休日かチェック
		if (scheduleDateDto.getWorkTypeCode().equals("legal_holiday")) {
			if (workOnHolidayDto != null) {
				// ワークフローDTO
				WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(
						workOnHolidayDto.getWorkflow());
				// 申請状態が完了チェック
				if (workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
					// 申請しない
					if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
						// 勤務時間と所定労働時間を比べる
						if (getWorkTime() < getHourMinuteInt(timeSettingDto.getGeneralWorkTime())) {
							// 勤務時間が小さい場合
							legalHolidayWorkTime = getHourMinuteInt(timeSettingDto.getGeneralWorkTime());
						} else {
							// 勤務時間が大きい場合
							legalHolidayWorkTime = getWorkTime();
						}
					}
				}
			}
		}
		setLegalHolidayWorkTime(legalHolidayWorkTime + legalCalendarDayTime);
	}
	
	/**
	 * 減額対象時間の計算
	 */
	private void setReducedTargetTimeCalc() {
		// 遅刻時間 + 早退時間  + 私用外出時間
		setDecreaseTime(getLateTime() + getLeaveEarlyTime() + getTotalPrivate());
	}
	
	/**
	 * 所定労働時間内労働の計算
	 */
	private void setGeneralWorkTimeCalc() {
		// 勤務時間 - 残業時間
		setGeneralWorkTime(getWorkTime() - getOvertimeTime());
	}
	
	/**
	 * 法内残業の計算
	 */
	private void setWithinStatutoryOvertimeCalc() {
		// 残業時間  - (法定外残業時間 + 所定休日勤務時間 + 法定休日勤務時間)
		setWithinStatutoryOvertime(getOvertimeTime()
				- (getOverTimeOut() + getPredeterminedHolidayWorkTime() + getLegalHolidayWorkTime()));
	}
	
	private void halfHolidayInfo() {
		// 半休時の初期値
	}
	
	/**
	 * 丸め処理
	 * @param time 対象時間
	 * @param unit 対象丸め単位
	 * @return 丸め計算
	 */
	public long roundingTreatmentCalc(long time, int unit) {
		if (unit == 0) {
			return time;
		} else {
			return time - (time % unit);
		}
	}
	
	/**
	 * 丸め処理
	 * @param time 対象時間
	 * @param unit 対象丸め単位
	 * @param roundType 対象丸め単位
	 * @return 丸め計算
	 */
	public int roundingTreatmentCalc(int time, int unit, int roundType) {
		if (time == 0) {
			return 0;
		}
		// 丸め値
		int rounding = 0;
		if (unit != 0) {
			rounding = time % unit;
		}
		// 切り上げ、切り捨て処理
		if (roundType == 0) {
			// 丸め無し
			return time;
		} else if (roundType == 1) {
			// 切り捨て
			return time - rounding;
		} else if (roundType == 2) {
			if (0 < rounding) {
				// 切り上げ
				return time - rounding + unit;
			} else {
				return time;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * @param date 変換対象の日付データ
	 * @return 時分をInt型に変換した結果を返す
	 */
	public int getHourMinuteInt(Date date) {
		return DateUtility.getHour(new Date(date.getTime())) * 60 + DateUtility.getMinute(new Date(date.getTime()));
	}
	
	/**
	 * @param time int型の分
	 * @return 引数で取得した分を時間と分に変換したDate型
	 * @throws MospException 例外処理発生時
	 */
	public Date getHourMinuteDate(int time) throws MospException {
		if (time != 0) {
			int hour = time / TimeConst.CODE_DEFINITION_HOUR;
			int minute = time % TimeConst.CODE_DEFINITION_HOUR;
			// 丸め単位 
			return DateUtility.getTime(hour, minute);
		} else {
			return DateUtility.getTime(0, 0);
		}
	}
	
	/**
	 * 
	 * @return 退勤時間
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public int getCheckEndTime() throws MospException {
		int endTimeInt = getHourMinuteInt(getEndTime());
		// 日がまたがるかチェック
		// 等しくない
		if (DateUtility.getDay(getStartTime()) != DateUtility.getDay(getEndTime())) {
			endTimeInt = endTimeInt + (TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR);
		}
		return endTimeInt;
	}
	
	/**
	 * 
	 * @param i 計算結果
	 * @return 計算結果が0未満なら0を返す。それ以外はそのまま受け取った引数を返す。
	 */
	public int getCheckCalcResults(int i) {
		if (i > 0) {
			return i;
		}
		return 0;
	}
	
	/**
	 * 自動計算
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void setAutoCalc() throws MospException {
		// 半休の初期化処理
		halfHolidayInfo();
		// 遅刻時間の計算
		setTardinessTimeCalc();
		// 早退時間の計算
		setLeaveEarlyTimeCalc();
		// 公用外出時間の計算
		setPublicGoingOutTimeCalc();
		// 私用外出時間の計算
		setPrivateGoingOutTimeCalc();
		// 深夜勤務時間の計算
		setLateNightWorkCalc();
		// 休憩時間の計算
		setBreakTimeCalc();
		// 勤務時間の計算
		setWorkTimeCalc();
		// 残業時間の計算
		setOvertimeTimeCalc();
		// 所定労働時間
		setGeneralWorkTimeCalc();
		// 所定休日勤務時間の計算
		setPredeterminedHolidayWorkTimeCalc();
		// 法定休日勤務時間の計算
		setLegalHolidayWorkTimeCalc();
		// 減額対象時間の計算
		setReducedTargetTimeCalc();
		// 法内残業の計算
		setWithinStatutoryOvertimeCalc();
		// 法定外残業時間、法定外休憩時間の計算
		setLegalOutTimeCalc();
	}
}
