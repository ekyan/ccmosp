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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubstituteDto;

/**
 * 振替休日データ登録クラス。
 */
public class SubstituteRegistBean extends PlatformBean implements SubstituteRegistBeanInterface {
	
	/**
	 * 振替休日データDAOクラス。<br>
	 */
	protected SubstituteDaoInterface			dao;
	/**
	 * 振替休日データ参照インターフェース。<br>
	 */
	protected SubstituteReferenceBeanInterface	substituteReference;
	/**
	 * 休出申請データDAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface	workOnHolidayRequestDao;
	
	/**
	 * 休暇申請データDAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface		holidayRequestDao;
	
	/**
	 * 代休申請データDAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * 残業申請データDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface		overtimeRequestDao;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface		differenceRequestDao;
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface			attendanceDao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface				workflowDao;
	
	/**
	 * カレンダマスタDAOクラス。<br>
	 */
	protected ScheduleDaoInterface				scheduleDao;
	
	/**
	 * カレンダ日マスタDAOクラス。<br>
	 */
	protected ScheduleDateDaoInterface			scheduleDateDao;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface	application;
	
	/**
	 * 勤怠設定参照クラス。<br>
	 */
	protected TimeSettingReferenceBeanInterface	timeSettingReference;
	
	/**
	 * 人事求職情報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface	suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface	retirementReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubstituteRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SubstituteRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		overtimeRequestDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		differenceRequestDao = (DifferenceRequestDaoInterface)createDao(DifferenceRequestDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		scheduleDao = (ScheduleDaoInterface)createDao(ScheduleDaoInterface.class);
		scheduleDateDao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
		application = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
	}
	
	@Override
	public SubstituteDtoInterface getInitDto() {
		return new TmdSubstituteDto();
	}
	
	@Override
	public void insert(SubstituteDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdSubstituteId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(long workflow) throws MospException {
		List<SubstituteDtoInterface> list = dao.findForWorkflow(workflow);
		for (SubstituteDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdSubstituteId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(SubstituteDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getSubstituteDate(),
				dto.getSubstituteRange(), dto.getWorkDate(), dto.getTimesWork()));
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(SubstituteDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdSubstituteId());
	}
	
	@Override
	public void validate(SubstituteDtoInterface dto) throws MospException {
		// 基本情報のチェック
		substituteReference.chkBasicInfo(dto.getPersonalId(), dto.getSubstituteDate());
	}
	
	@Override
	public void checkRegist(SubstituteDtoInterface dto1, SubstituteDtoInterface dto2) throws MospException {
		// 振替日1の休職チェック
		checkSuspension(dto1);
		// 振替日2の休職チェック
		checkSuspension(dto2);
		// 振替日1の退職チェック
		checkRetirement(dto1);
		// 振替日2の退職チェック
		checkRetirement(dto2);
		// 振替日1の妥当性チェック
		checkTargetTransferDate(dto1);
		// 振替日2の妥当性チェック
		checkTargetTransferDate(dto2);
		// 振替日1の休日チェック
		checkHolidayDate(dto1);
		// 振替日2の休日チェック
		checkHolidayDate(dto2);
		// TODO 期間チェック
		// 振替日1の範囲チェック
		checkTransferDateRange(dto1);
		// 振替日2の範囲チェック
		checkTransferDateRange(dto2);
		// 振替日1の他の申請チェック
		checkRequest(dto1);
		// 振替日2の他の申請チェック
		checkRequest(dto2);
		// 振替日1の勤怠の申請チェック
		checkAttendance(dto1);
		// 振替日2の勤怠の申請チェック
		checkAttendance(dto2);
	}
	
	@Override
	public void checkTargetTransferDate(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		int substituteRange = dto.getSubstituteRange();
		boolean substituteFlag = false;
		// 個人IDと振替日から振替休日データリストを取得
		List<SubstituteDtoInterface> substituteList = dao.findForList(personalId, substituteDate);
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// ワークフロー番号が同じ場合
			if (dto.getWorkflow() == substituteDto.getWorkflow()) {
				continue;
			}
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (!PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書でなく且つ取下でない場合
				substituteFlag = true;
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(substituteDto.getSubstituteType())
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(substituteDto.getSubstituteType())) {
					// 法定休日か所定休日の場合
					if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
							|| substituteRange == substituteDto.getSubstituteRange()) {
						String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
							mospParams.getName("GoingWork", "Day"), mospParams.getName("Transfer", "Day") };
						mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
						return;
					}
				}
				break;
			}
		}
		if (!substituteFlag) {
			// 適用されている設定を取得
			ApplicationDtoInterface applicationDto = application.findForPerson(personalId, substituteDate);
			if (applicationDto == null) {
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM,
						mospParams.getName("Set") + mospParams.getName("Apply"), null);
				return;
			}
			// カレンダマスタ取得
			ScheduleDtoInterface scheduleDto = scheduleDao
				.findForInfo(applicationDto.getScheduleCode(), substituteDate);
			if (scheduleDto == null) {
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Calendar"), null);
				return;
			}
			// カレンダコードと有効日と日からカレンダ日情報を取得
			ScheduleDateDtoInterface scheduleDateDto = scheduleDateDao.findForKey(scheduleDto.getScheduleCode(),
					scheduleDto.getActivateDate(), substituteDate);
			if (scheduleDateDto == null) {
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Calendar")
						+ mospParams.getName("Data"), null);
				return;
			}
			if (scheduleDateDto.getWorkTypeCode() == null || scheduleDateDto.getWorkTypeCode().isEmpty()
					|| TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
					|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
				// 法定休日、所定休日又は勤務形態が登録されていない場合
				String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
					mospParams.getName("GoingWork", "Day"), mospParams.getName("Transfer", "Day") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
				return;
			}
		}
		// 振替休日のチェック
		for (SubstituteDtoInterface substituteDto : substituteList) {
			if (dto.getWorkflow() == substituteDto.getWorkflow()) {
				continue;
			}
			// ワークフロー情報を取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書又は取下の場合
				continue;
			}
			// 振替範囲取得
			int range = substituteDto.getSubstituteRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				mospParams.addErrorMessage(
						TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()),
							mospParams.getName("Transfer", "Day") });
				return;
			} else if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (range == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
						mospParams.getName("GoingWork", "Day"), mospParams.getName("Transfer", "Day") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			}
		}
		// 休暇申請のチェック
		// 個人IDと申請日から休暇申請マスタリストを取得
		List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForList(personalId, substituteDate);
		// 休暇申請マスタリスト毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
			// ワークフロー番号からワークフロー情報を取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(holidayRequestDto.getWorkflow());
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書又は取下の場合
				continue;
			}
			// 休暇範囲取得
			int holidayRange = holidayRequestDto.getHolidayRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 休暇範囲が全休の場合
				mospParams.addErrorMessage(
						TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()),
							mospParams.getName("Transfer", "Day") });
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (holidayRange == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
						mospParams.getName("GoingWork", "Day"), mospParams.getName("Transfer", "Day") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時休の場合
				mospParams.addErrorMessage(
						TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()),
							mospParams.getName("Transfer", "Day") });
				return;
			}
		}
		// 代休申請のチェック
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
				substituteDate);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(subHolidayRequestDto.getWorkflow());
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書又は取下の場合
				continue;
			}
			int holidayRange = subHolidayRequestDto.getHolidayRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				mospParams.addErrorMessage(
						TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()),
							mospParams.getName("Transfer", "Day") });
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (holidayRange == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
						mospParams.getName("GoingWork", "Day"), mospParams.getName("Transfer", "Day") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			}
		}
	}
	
	@Override
	public void checkTransferDateRange(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		Date workDate = dto.getWorkDate();
		// 設定適用マスタDTOの取得
		ApplicationDtoInterface applicationDto = application.findForPerson(dto.getPersonalId(), workDate);
		application.chkExistApplication(applicationDto, workDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠設定管理DTOの取得
		TimeSettingDtoInterface timeSettingDto = timeSettingReference.getTimeSettingInfo(
				applicationDto.getWorkSettingCode(), workDate);
		timeSettingReference.chkExistTimeSetting(timeSettingDto, workDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 期間開始日および期間終了日取得
		Date beforeDate = DateUtility.addDay(
				DateUtility.addMonth(workDate, -timeSettingDto.getTransferAheadLimitMonth()),
				-timeSettingDto.getTransferAheadLimitDate());
		Date afterDate = DateUtility.addDay(
				DateUtility.addMonth(workDate, timeSettingDto.getTransferLaterLimitMonth()),
				timeSettingDto.getTransferLaterLimitDate());
		// 振替日取得
		Date substituteDate = dto.getSubstituteDate();
		if (!substituteDate.after(beforeDate) || !substituteDate.before(afterDate)) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TRANSFER_DAY_EXCESS,
					getStringDate(dto.getSubstituteDate()), null);
		}
	}
	
	@Override
	public void checkRequest(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		// 振替日の休出申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao.findForKeyOnWorkflow(
				personalId, substituteDate);
		if (workOnHolidayRequestDto != null) {
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(workOnHolidayRequestDto.getWorkflow());
			if (workflowDto != null) {
				if (workflowDto.getWorkflowStage() != 0
						&& !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
						&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
						mospParams.getName("Transfer", "Day") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
				}
			}
		}
		if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
			requestUtil.setRequests(personalId, dto.getSubstituteDate());
			// 振替日の残業申請取得
			List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, substituteDate);
			// 残業申請リスト毎に処理
			for (OvertimeRequestDtoInterface requestDto : list) {
				// ワークフローの取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (!PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
						&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 下書でなく且つ取下でない場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
						mospParams.getName("Transfer", "Day") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
					return;
				}
			}
			// 振替日の勤務形態変更申請
			if (requestUtil.getWorkTypeChangeDto(false) != null) {
				// 下書でなく且つ取下でない場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						DateUtility.getStringDate(dto.getSubstituteDate()), mospParams.getName("Transfer", "Day"));
			}
			
			// 振替日の時差出勤申請取得
			DifferenceRequestDtoInterface differenceRequestDto = differenceRequestDao.findForKeyOnWorkflow(personalId,
					substituteDate);
			if (differenceRequestDto == null) {
				return;
			}
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(differenceRequestDto.getWorkflow());
			if (workflowDto == null) {
				return;
			}
			if (!PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書でなく且つ取下でない場合
				String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()),
					mospParams.getName("Transfer", "Day") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
			}
		}
	}
	
	@Override
	public void checkAttendance(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと勤務日と勤務回数から勤怠情報を取得
		AttendanceDtoInterface attendanceDto = attendanceDao
			.findForKey(dto.getPersonalId(), dto.getSubstituteDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		// ワークフロー番号からワークフロー情報を取得
		WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 下書又は取下の場合
			return;
		}
		if (workflowDto.getWorkflowStage() == 0
				&& PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 1次戻の場合
			return;
		}
		// 申請時のエラーメッセージ
		String[] aryRep = { getStringDate(dto.getSubstituteDate()), mospParams.getName("WorkManage"),
			mospParams.getName("Transfer", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	@Override
	public void checkHolidayDate(SubstituteDtoInterface dto) {
		if (dto == null) {
			return;
		}
		// 法定休日でなく且つ所定休日でない場合
		if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getSubstituteType())
				&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(dto.getSubstituteType())) {
			// 申請時のエラーメッセージ
			String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), mospParams.getName("Holiday"),
				mospParams.getName("GoingWork", "Day") };
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
		}
		if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			return;
		}
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getSubstituteType())) {
			// 法定休日の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, mospParams.getName("Legal", "Holiday"),
					mospParams.getName("HalfDay", "Transfer"), mospParams.getName("Transfer", "Day"));
		}
	}
	
	@Override
	public void checkSuspension(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと対象年月日から休職判断
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getSubstituteDate())) {
			addEmployeeSuspendedMessage();
		}
	}
	
	@Override
	public void checkRetirement(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと対象年月日から退職判断
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getSubstituteDate())) {
			addEmployeeRetiredMessage();
		}
	}
	
}
