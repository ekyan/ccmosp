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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.bean.workflow.impl.WorkflowReferenceBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdOvertimeRequestDto;

/**
 * 残業申請登録クラス。
 */
public class OvertimeRequestRegistBean extends TimeBean implements OvertimeRequestRegistBeanInterface {
	
	/**
	 * 残業申請マスタDAOクラス。<br>
	 */
	private OvertimeRequestDaoInterface				dao;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface		applicationReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBeanInterface		scheduleReference;
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	protected ScheduleDateReferenceBeanInterface	scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface		workTypeReference;
	
	/**
	 * 残業申請マスタ参照クラス。<br>
	 */
	private OvertimeRequestReferenceBeanInterface	overtimeReference;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	private WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	private WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	private WorkflowReferenceBeanInterface			workflowReference;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface	workflowCommentRegist;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface		approvalInfoReference;
	
	/**
	 * 振替休日データ参照クラス。<br>
	 */
	protected SubstituteReferenceBeanInterface		substituteReference;
	
	/**
	 * 人事入社情報参照クラス。<br>
	 */
	protected EntranceReferenceBeanInterface		entranceReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface		suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface		retirementReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	private CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	private ScheduleUtilBeanInterface				scheduleUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	private RequestUtilBeanInterface				requestUtil;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public OvertimeRequestRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public OvertimeRequestRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		overtimeReference = (OvertimeRequestReferenceBeanInterface)createBean(OvertimeRequestReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowReference = (WorkflowReferenceBean)createBean(WorkflowReferenceBean.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestDtoInterface getInitDto() {
		return new TmdOvertimeRequestDto();
	}
	
	@Override
	public void insert(OvertimeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setTmdOvertimeRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// Bean初期化
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		// 処理ワークフロー情報リスト準備
		List<WorkflowDtoInterface> workflowList = new ArrayList<WorkflowDtoInterface>();
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				workflowList.add(workflowDto);
			}
		}
	}
	
	@Override
	public void regist(OvertimeRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdOvertimeRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void add(OvertimeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdOvertimeRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdOvertimeRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(OvertimeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdOvertimeRequestId());
	}
	
	@Override
	public void withdrawn(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
//			// 妥当性チェック
//			validate(dto);
//			if (mospParams.hasErrorMessage()) {
//				continue;
//			}
			// 取下時の確認
			checkWithdrawn(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 取下
			workflowDto = workflowRegist.withdrawn(workflowDto);
			if (workflowDto != null) {
				// ワークフローコメント登録
				workflowCommentRegist.addComment(
						workflowDto,
						mospParams.getUser().getPersonalId(),
						mospParams.getProperties().getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED,
								new String[]{ mospParams.getName("TakeDown") }));
			}
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate(), dto.getOvertimeType()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdOvertimeRequestId());
	}
	
	@Override
	public void validate(OvertimeRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		overtimeReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public void checkSetRequestDate(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		checkSetRequestDate(dto, localRequestUtil);
	}
	
	/**
	 * 申請日決定時の確認処理を行う。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSetRequestDate(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		// 入社チェック
		checkEntered(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 退職チェック
		checkRetired(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休職チェック
		checkSuspended(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態チェック
		checkWorkType(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(dto, localRequestUtil);
		// 申請日決定時点では勤務前・勤務後の判断ができないので重複チェック・勤怠申請チェックは行わない
	}
	
	@Override
	public void checkDraft(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 申請日決定時と同様の処理を行う。
		checkSetRequestDate(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複チェック
		checkOvertimeOverlap(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(dto, localRequestUtil);
	}
	
	@Override
	public void checkAppli(OvertimeRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		// 残業申請の申請期間チェック。
		checkPeriod(dto);
		// 残業申請の項目の必須チェック。
		checkRequired(dto);
	}
	
	@Override
	public void checkCancelAppli(OvertimeRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
	}
	
	@Override
	public void checkWithdrawn(OvertimeRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
	}
	
	@Override
	public void checkCancelApproval(OvertimeRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(OvertimeRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkRequired(OvertimeRequestDtoInterface dto) {
		if (dto.getRequestTime() == 0) {
			// 表示例 残業申請時間には正しい時間を入力してください。
			addOvertimeRequestTimeErrorMessage();
		}
		if (dto.getRequestReason().isEmpty()) {
			// 表示例 残業理由を正しく入力してください。
			addOvertimeRequestReasonErrorMessage();
		}
	}
	
	/**
	 * 他の申請チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequest(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		if (localRequestUtil.isHolidayAllDay(false)) {
			// 全休の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
					DateUtility.getStringDate(dto.getRequestDate()), getNameApplicationDay());
		}
	}
	
	@Override
	@Deprecated
	public void checkRequest(OvertimeRequestDtoInterface dto) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// カレンダの勤務形態が法定休日又は所定休日の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 下書、取下でない休日出勤申請情報取得
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(false);
			// 休日出勤申請の場合
			if (workOnHolidayRequestDto != null
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// （日付）は休日出勤が申請されています。残業年月日を選択し直してください。
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkingHoliday"));
				return;
			}
			// 承認済かつ振替出勤申請の休日出勤申請情報がない場合
			if (requestUtil.getWorkOnHolidayDto(true) == null) {
				// 法定休日又は所定休日の場合
				addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
				return;
			}
		}
//		if (requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
//			// 下書、取下でない休暇申請情報がある場合
//			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Holiday"));
//			return;
//		}
//		if (requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
//			// 下書、取下でない代休申請情報がある場合
//			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("CompensatoryHoliday"));
//			return;
//		}
//		if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
//			// 下書、取下でない振替日の振替休日申請情報がある場合
//			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Transfer"));
//			return;
//		}
		if (requestUtil.isHolidayAllDay(false)) {
			// 全休の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
					DateUtility.getStringDate(dto.getRequestDate()), getNameApplicationDay());
		}
	}
	
	@Override
	public void checkOvertimeOverlap(OvertimeRequestDtoInterface dto) throws MospException {
		// 残業申請リスト取得
		List<OvertimeRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getRequestDate());
		for (OvertimeRequestDtoInterface requestDto : list) {
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflowDto.getWorkflow()) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			if (dto.getOvertimeType() == requestDto.getOvertimeType()) {
				// 表示例 yyyy/MM/ddは既に残業が申請されています。残業年月日または残業区分を選択し直してください。"
				addOvertimeTargetDateOvertimeErrorMessage(dto.getRequestDate());
				break;
			}
		}
	}
	
	@Override
	public void checkPeriod(OvertimeRequestDtoInterface dto) {
		if (dto.getRequestDate().after(DateUtility.addMonth(getSystemDate(), 1))) {
			addOvertimePeriodErrorMessage();
		}
	}
	
	/**
	 * 勤怠の申請チェック。<br>
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		// 勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface attendanceDto = localRequestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			return;
		}
		if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER && attendanceDto.getOvertimeAfter() > 0) {
			// 勤務後残業の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addOvertimeTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	@Override
	@Deprecated
	public void checkAttendance(OvertimeRequestDtoInterface dto) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			return;
		}
		if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER && attendanceDto.getOvertimeAfter() > 0) {
			// 勤務後残業の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addOvertimeTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	/**
	 * 勤務形態チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		String workTypeCode = getScheduledWorkTypeCode(dto, localRequestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日の場合
			addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日労働・所定休日労働の場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkingHoliday"));
		}
	}
	
	/**
	 * カレンダ勤務形態コードを取得する。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @return カレンダ勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認済である場合
			return differenceRequestDto.getDifferenceType();
		}
		Date date = dto.getRequestDate();
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = localRequestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			// 振出・休出申請が承認済でない場合
			List<SubstituteDtoInterface> list = localRequestUtil.getSubstituteList(true);
			for (SubstituteDtoInterface substituteDto : list) {
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					return substituteDto.getSubstituteType();
				}
			}
		} else {
			// 振出・休出申請が承認済である場合
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 振替出勤の場合
				List<SubstituteDtoInterface> list = substituteReference.getSubstituteList(workOnHolidayRequestDto
					.getWorkflow());
				for (SubstituteDtoInterface substituteDto : list) {
					date = substituteDto.getSubstituteDate();
					break;
				}
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 法定休日出勤の場合
					return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
				} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workOnHolidayRequestDto
					.getWorkOnHolidayType())) {
					// 所定休日出勤の場合
					return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
		String workTypeCode = "";
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = localRequestUtil.getWorkTypeChangeDto(true);
		if (workTypeChangeRequestDto == null) {
			// 勤務形態変更申請が承認済でない場合
			ApplicationDtoInterface applicationDto = applicationReference.findForPerson(dto.getPersonalId(), date);
			if (applicationDto == null) {
				return "";
			}
			ScheduleDtoInterface scheduleDto = scheduleReference
				.getScheduleInfo(applicationDto.getScheduleCode(), date);
			if (scheduleDto == null) {
				return "";
			}
			ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
					scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), date);
			if (scheduleDateDto == null || scheduleDateDto.getWorkTypeCode() == null) {
				return "";
			}
			if (scheduleDateDto.getWorkTypeCode().isEmpty()
					|| TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
					|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
				return scheduleDateDto.getWorkTypeCode();
			}
			workTypeCode = scheduleDateDto.getWorkTypeCode();
		} else {
			// 勤務形態変更申請が承認済である場合
			workTypeCode = workTypeChangeRequestDto.getWorkTypeCode();
		}
		WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(workTypeCode, date);
		if (workTypeDto == null || workTypeDto.getWorkTypeCode() == null) {
			return "";
		}
		return workTypeDto.getWorkTypeCode();
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(OvertimeRequestDtoInterface dto) throws MospException {
		if (!entranceReference.isEntered(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で入社していない場合
			addNotEntranceErrorMessage();
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(OvertimeRequestDtoInterface dto) throws MospException {
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で退職している場合
			addEmployeeRetiredMessage();
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(OvertimeRequestDtoInterface dto) throws MospException {
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で休職している場合
			addEmployeeSuspendedMessage();
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameApplicationDay());
	}
	
	/**
	 * 残業年月日名称を取得する。
	 * @return 残業年月日名称
	 */
	protected String getNameApplicationDay() {
		return mospParams.getName("OvertimeWork") + mospParams.getName("Year") + mospParams.getName("Month")
				+ mospParams.getName("Day");
	}
	
}
