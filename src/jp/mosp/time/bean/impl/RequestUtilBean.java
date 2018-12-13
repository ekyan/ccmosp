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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.RequestEntity;

/**
 * 申請ユーティリティクラス。<br>
 */
public class RequestUtilBean extends TimeBean implements RequestUtilBeanInterface {
	
	/**
	 * 休暇申請参照クラス。
	 */
	HolidayRequestReferenceBeanInterface			holidayRequestRefer;
	
	/**
	 * 残業申請参照クラス。
	 */
	OvertimeRequestReferenceBeanInterface			overtimeRequestRefer;
	
	/**
	 * 休日出勤申請参照クラス。
	 */
	WorkOnHolidayRequestReferenceBeanInterface		workOnHolidayRefer;
	
	/**
	 * 振替休日申請参照クラス。
	 */
	SubstituteReferenceBeanInterface				substituteRefer;
	
	/**
	 * 代休申請参照クラス。
	 */
	SubHolidayRequestReferenceBeanInterface			subHolidayRequestRefer;
	
	/**
	 * 時差出勤申請参照クラス。
	 */
	DifferenceRequestReferenceBeanInterface			differenceRequestRefer;
	
	/**
	 * 勤務形態変更申請参照クラス。
	 */
	WorkTypeChangeRequestReferenceBeanInterface		workTypeChangeRequestRefer;
	
	/**
	 * 勤怠データ参照クラス。<br>
	 */
	AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * ワークフロー情報参照クラス。
	 */
	WorkflowReferenceBeanInterface					workflowReference;
	
	/**
	 * ワークフロー統合クラス。
	 */
	WorkflowIntegrateBeanInterface					workflowIntegrate;
	
	/**
	 * カレンダユーティリティクラス。
	 */
	ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 休暇申請情報リスト。
	 */
	protected List<HolidayRequestDtoInterface>		holidayRequestList;
	
	/**
	 * 残業申請情報リスト。
	 */
	protected List<OvertimeRequestDtoInterface>		overTimeRequestList;
	
	/**
	 * 振替休日情報リスト。
	 */
	protected List<SubstituteDtoInterface>			substituteList;
	
	/**
	 * 代休申請情報リスト。
	 */
	protected List<SubHolidayRequestDtoInterface>	subHolidayRequestList;
	
	/**
	 * 休日出勤情報。
	 */
	protected WorkOnHolidayRequestDtoInterface		workOnHolidayDto;
	
	/**
	 * 時差出勤情報。
	 */
	protected DifferenceRequestDtoInterface			differenceDto;
	
	/**
	 * 勤務形態変更情報。
	 */
	protected WorkTypeChangeRequestDtoInterface		workTypeChangeDto;
	
	/**
	 * 勤怠情報。
	 */
	protected AttendanceDtoInterface				attendanceDto;
	
	/**
	 * 休暇範囲(午前休かつ午後休)。
	 */
	protected int									CODE_HOLIDAY_RANGE_AM_PM	= 5;
	
	
	/**
	 * {@link RequestUtilBean}を生成する。<br>
	 */
	public RequestUtilBean() {
		// 処理無し
	}
	
	/**
	 * {@link TimeBean#TimeBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected RequestUtilBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 各種クラスの取得
		holidayRequestRefer = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		workOnHolidayRefer = (WorkOnHolidayRequestReferenceBeanInterface)createBean(WorkOnHolidayRequestReferenceBeanInterface.class);
		substituteRefer = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		overtimeRequestRefer = (OvertimeRequestReferenceBeanInterface)createBean(OvertimeRequestReferenceBeanInterface.class);
		differenceRequestRefer = (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class);
		workTypeChangeRequestRefer = (WorkTypeChangeRequestReferenceBeanInterface)createBean(WorkTypeChangeRequestReferenceBeanInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		workflowReference = (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		subHolidayRequestRefer = (SubHolidayRequestReferenceBeanInterface)createBean(SubHolidayRequestReferenceBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public void setRequests(String personalId, Date targetDate) throws MospException {
		// 休暇申請リスト取得
		holidayRequestList = holidayRequestRefer.getHolidayRequestList(personalId, targetDate);
		// 休日出勤申請情報取得
		workOnHolidayDto = workOnHolidayRefer.findForSubstitute(personalId, targetDate, TimeBean.TIMES_WORK_DEFAULT);
		// 振替休日データリスト取得
		substituteList = substituteRefer.getSubstituteList(personalId, targetDate);
		// 残業申請情報リスト取得
		overTimeRequestList = overtimeRequestRefer.getOvertimeRequestList(personalId, targetDate, targetDate);
		// 代休申請情報取得
		subHolidayRequestList = subHolidayRequestRefer.getSubHolidayRequestList(personalId, targetDate);
		// 時差出勤申請情報取得
		differenceDto = differenceRequestRefer.findForKeyOnWorkflow(personalId, targetDate);
		// 勤務形態変更申請情報取得
		workTypeChangeDto = workTypeChangeRequestRefer.findForKeyOnWorkflow(personalId, targetDate);
		// 勤怠情報取得
		attendanceDto = attendanceReference.findForKey(personalId, targetDate, TimeBean.TIMES_WORK_DEFAULT);
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(boolean status) throws MospException {
		// リスト準備
		List<SubstituteDtoInterface> completedSubstitute = new ArrayList<SubstituteDtoInterface>();
		// 情報リストがない場合
		if (substituteList.isEmpty()) {
			return completedSubstitute;
		}
		// 振替申請リスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 振替申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedSubstitute.add(substituteDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedSubstitute.add(substituteDto);
			}
		}
		// 承認済の振替申請リスト
		return completedSubstitute;
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOverTimeList(boolean status) throws MospException {
		// リスト準備
		List<OvertimeRequestDtoInterface> completedOvertime = new ArrayList<OvertimeRequestDtoInterface>();
		// 情報リストがない場合
		if (overTimeRequestList.isEmpty()) {
			return completedOvertime;
		}
		// 残業申請リスト毎に処理
		for (OvertimeRequestDtoInterface overtimeDto : overTimeRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(overtimeDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 残業申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedOvertime.add(overtimeDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedOvertime.add(overtimeDto);
			}
		}
		// 承認済の残業申請リスト
		return completedOvertime;
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayList(boolean status) throws MospException {
		// リスト準備
		List<SubHolidayRequestDtoInterface> completedsubHoliday = new ArrayList<SubHolidayRequestDtoInterface>();
		// 情報リストがない場合
		if (subHolidayRequestList.isEmpty()) {
			return completedsubHoliday;
		}
		// 代休申請リスト毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(subHolidayDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 代休申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedsubHoliday.add(subHolidayDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedsubHoliday.add(subHolidayDto);
			}
		}
		// 承認済の代休申請リスト
		return completedsubHoliday;
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayList(boolean status) throws MospException {
		// リスト準備
		List<HolidayRequestDtoInterface> completedHoliday = new ArrayList<HolidayRequestDtoInterface>();
		// 情報リストがない場合
		if (holidayRequestList.isEmpty()) {
			return completedHoliday;
		}
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface holidayDto : holidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(holidayDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 休暇申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedHoliday.add(holidayDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedHoliday.add(holidayDto);
			}
		}
		// 承認済の休暇申請リスト
		return completedHoliday;
	}
	
	@Override
	public DifferenceRequestDtoInterface getDifferenceDto(boolean status) throws MospException {
		// 時差出勤申請情報がない場合
		if (differenceDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return differenceDto;
		}
		// 下書でなく取下でない場合
		if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return differenceDto;
		}
		return null;
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface getWorkTypeChangeDto(boolean status) throws MospException {
		// 勤務形態変更申請情報がない場合
		if (workTypeChangeDto == null) {
			return null;
		}
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && workflowIntegrate.isCompleted(workflowDto)) {
			return workTypeChangeDto;
		}
		// 下書でなく取下でない場合
		if (!status && !workflowIntegrate.isDraft(workflowDto) && !workflowIntegrate.isWithDrawn(workflowDto)) {
			return workTypeChangeDto;
		}
		return null;
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getWorkOnHolidayDto(boolean status) throws MospException {
		// 休日出勤申請情報がない場合
		if (workOnHolidayDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return workOnHolidayDto;
		}
		// 下書でなく取下でない場合
		if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return workOnHolidayDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getDraftAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// 下書の場合
		if (workflowIntegrate.isDraft(attendanceDto.getWorkflow())) {
			return attendanceDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getFirstRevertedAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// 1次戻の場合
		if (workflowIntegrate.isFirstReverted(attendanceDto.getWorkflow())) {
			return attendanceDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getApplicatedAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		// 取下の場合
		if (workflowIntegrate.isWithDrawn(workflowDto)) {
			return null;
		}
		// 下書の場合
		if (workflowIntegrate.isDraft(workflowDto)) {
			return null;
		}
		// 1次戻の場合
		if (workflowIntegrate.isFirstReverted(workflowDto)) {
			return null;
		}
		return attendanceDto;
	}
	
	@Override
	public HolidayRequestDtoInterface getCompletedHolidayRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<HolidayRequestDtoInterface> getCompletedHolidayList = getHolidayList(true);
		// 情報が存在しない場合
		if (getCompletedHolidayList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (HolidayRequestDtoInterface holidayDto : getCompletedHolidayList) {
			// 全休の場合
			if (holidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return holidayDto;
			}
		}
		return null;
	}
	
	@Override
	public SubHolidayRequestDtoInterface getCompletedSubHolidayRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<SubHolidayRequestDtoInterface> getCompletedSubHolidayList = getSubHolidayList(true);
		// 情報が存在しない場合
		if (getCompletedSubHolidayList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : getCompletedSubHolidayList) {
			// 全休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return subHolidayDto;
			}
		}
		return null;
	}
	
	@Override
	public SubstituteDtoInterface getCompletedSubstituteRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<SubstituteDtoInterface> getCompletedSubstituteList = getSubstituteList(true);
		// 情報が存在しない場合
		if (getCompletedSubstituteList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (SubstituteDtoInterface substituteDto : getCompletedSubstituteList) {
			// 承認済休日出勤申請がない場合
			if (getWorkOnHolidayDto(true) == null) {
				// 全休の場合
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					return substituteDto;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isHolidayAllDay(boolean status) throws MospException {
		// 午前休・午後休フラグ準備
		boolean amFlag = false;
		boolean pmFlag = false;
		// 休暇申請休暇範囲取得
		List<HolidayRequestDtoInterface> statusHolidayList = getHolidayList(status);
		int rangeHoliday = checkHolidayRangeHoliday(statusHolidayList);
		// 休暇申請確認
		if (rangeHoliday != 0) {
			// 全休又は午前休午後休の場合
			if (rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_ALL || rangeHoliday == CODE_HOLIDAY_RANGE_AM_PM) {
				// 処理終了
				return true;
			}
			// 午前休の場合
			if (rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				amFlag = true;
			}
			// 午後休の場合
			if (rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				pmFlag = true;
			}
		}
		// 振替申請休暇範囲取得
		List<SubstituteDtoInterface> statusSubstituteList = getSubstituteList(status);
		int rangeSubstitute = checkHolidayRangeSubstitute(statusSubstituteList);
		// 対象日が振替日の場合
		if (rangeSubstitute != 0) {
			// 全休又は午前休午後休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_ALL || rangeSubstitute == CODE_HOLIDAY_RANGE_AM_PM) {
				return true;
			}
			// 午前休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				amFlag = true;
			}
			// 午後休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				pmFlag = true;
			}
		}
		// 代休申請休暇範囲取得
		List<SubHolidayRequestDtoInterface> statusSubHolidayList = getSubHolidayList(status);
		int rangeSubHoliday = checkHolidayRangeSubHoliday(statusSubHolidayList);
		// 全休又は午前休午後休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_ALL || rangeSubHoliday == CODE_HOLIDAY_RANGE_AM_PM) {
			return true;
		}
		// 午前休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			amFlag = true;
		}
		// 午後休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			pmFlag = true;
		}
		// 振替出勤申請休日範囲取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayDto(status);
		if (workOnHolidayRequestDto != null) {
			int substitute = workOnHolidayRequestDto.getSubstitute();
			// 休日出勤の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				return false;
			}
			// 半日振替出勤(午前)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
				pmFlag = true;
			}
			// 半日振替出勤(午後)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				amFlag = true;
			}
		}
		// 全休の場合
		if (amFlag && pmFlag) {
			return true;
		}
		return false;
	}
	
	@Override
	public int checkHolidayRangeHoliday(List<HolidayRequestDtoInterface> holidayRequestList) {
		// 休暇範囲準備
		int range = 0;
		// 情報リストがない場合
		if (holidayRequestList.isEmpty()) {
			return range;
		}
		// 休暇申請情報リスト毎に処理
		for (HolidayRequestDtoInterface HolidayDto : holidayRequestList) {
			// 全休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
			// 午前休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			// 午後休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
			// 時間休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				return TimeConst.CODE_HOLIDAY_RANGE_TIME;
			}
		}
		return range;
	}
	
	@Override
	public int checkHolidayRangeSubstitute(List<SubstituteDtoInterface> substituteList) throws MospException {
		// 休暇範囲準備
		int range = 0;
		// 承認済リストがない場合
		if (substituteList.isEmpty()) {
			return range;
		}
		// 更に承認済の振替又は休日出勤がされている場合
		if (getWorkOnHolidayDto(true) != null) {
			return range;
		}
		// 情報リスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 全休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
			// 午前休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			// 午後休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
		}
		return range;
	}
	
	@Override
	public int checkHolidayRangeSubHoliday(List<SubHolidayRequestDtoInterface> subHolidayList) {
		// 休暇範囲準備
		int range = 0;
		// 承認済リストがない場合
		if (subHolidayList.isEmpty()) {
			return range;
		}
		// 代休申請情報リスト毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayList) {
			// 全休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
			// 午前休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
		}
		return range;
	}
	
	@Override
	public RequestEntity getRequestEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティ準備
		RequestEntity requestEntity = new RequestEntity(personalId, targetDate);
		// 申請情報取得
		setRequests(personalId, targetDate);
		// 申請情報設定
		requestEntity.setHolidayRequestList(holidayRequestList);
		requestEntity.setWorkOnHolidayRequestDto(workOnHolidayDto);
		requestEntity.setSubstituteList(substituteList);
		requestEntity.setOverTimeRequestList(overTimeRequestList);
		requestEntity.setSubHolidayRequestList(subHolidayRequestList);
		requestEntity.setDifferenceRequestDto(differenceDto);
		requestEntity.setWorkTypeChangeRequestDto(workTypeChangeDto);
		requestEntity.setAttendanceDto(attendanceDto);
		// ワークフロー情報取得
		requestEntity.setWorkflowMap(getWorkflowMap());
		// 申請日の予定勤務形態を取得及び設定
		requestEntity.setScheduledWorkTypeCode(scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate));
		// 振替出勤日の予定勤務形態を取得及び設定
		requestEntity.setSubstitutedWorkTypeCode(getSubstitutedWorkTypeCode());
		return requestEntity;
	}
	
	/**
	 * 振替休日の予定勤務形態を取得する。<br>
	 * 振出・休出申請(振替出勤)情報が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * @return 振替出勤日の予定勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getSubstitutedWorkTypeCode() throws MospException {
		// 振出・休出申請(振替出勤)情報確認
		if (workOnHolidayDto == null
				|| workOnHolidayDto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			return "";
		}
		// 個人ID取得
		String personalId = workOnHolidayDto.getPersonalId();
		// 振替出勤日取得
		Date workDate = workOnHolidayDto.getRequestDate();
		// 振替出勤日で振替休日情報取得
		List<SubstituteDtoInterface> list = substituteRefer.getSubstituteList(personalId, workDate,
				TimeBean.TIMES_WORK_DEFAULT);
		// 振替休日情報確認
		for (SubstituteDtoInterface dto : list) {
			// 振替日を取得
			Date substituteDate = dto.getSubstituteDate();
			// 振替日の予定勤務形態を取得
			return scheduleUtil.getScheduledWorkTypeCode(personalId, substituteDate);
		}
		return "";
	}
	
	/**
	 * ワークフロー情報群を取得する。<br>
	 * 設定されている申請情報のワークフロー番号につき、ワークフロー情報を取得する。<br>
	 * <br>
	 * @return ワークフロー情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Long, WorkflowDtoInterface> getWorkflowMap() throws MospException {
		// ワークフロー情報群の準備
		Map<Long, WorkflowDtoInterface> workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// 申請毎にワークフロー情報を取得してワークフロー情報群に設定
		// 休暇申請リスト取得
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 休日出勤申請情報取得
		if (workOnHolidayDto != null) {
			long workflow = workOnHolidayDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 振替休日データリスト取得
		for (SubstituteDtoInterface dto : substituteList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 残業申請情報リスト取得
		for (OvertimeRequestDtoInterface dto : overTimeRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 代休申請情報取得
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 時差出勤申請情報取得
		if (differenceDto != null) {
			long workflow = differenceDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 勤務形態変更申請情報取得
		if (workTypeChangeDto != null) {
			long workflow = workTypeChangeDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 勤怠情報取得
		if (attendanceDto != null) {
			long workflow = attendanceDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		return workflowMap;
	}
}