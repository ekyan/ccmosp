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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
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
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdOvertimeRequestDto;

/**
 * 残業申請登録クラス。
 */
public class OvertimeRequestRegistBean extends TimeBean implements OvertimeRequestRegistBeanInterface {
	
	/**
	 * 残業申請マスタDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface				dao;
	
	/**
	 * 残業申請マスタ参照クラス。<br>
	 */
	protected OvertimeRequestReferenceBeanInterface	overtimeReference;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface			workflowReference;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface	workflowCommentRegist;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface		approvalInfoReference;
	
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
	protected CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface				scheduleUtil;
	
	
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
		overtimeReference = (OvertimeRequestReferenceBeanInterface)createBean(
				OvertimeRequestReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowReference = (WorkflowReferenceBean)createBean(WorkflowReferenceBean.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(
				WorkflowCommentRegistBeanInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(
				ApprovalInfoReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
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
		// 残業申請情報レコード識別ID毎に処理
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
				workflowCommentRegist.addComment(workflowDto, mospParams.getUser().getPersonalId(),
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
		checkDuplicateInsert(
				dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate(), dto.getOvertimeType()));
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
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
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
	public void checkDraft(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 申請日決定時と同様の処理を行う。
		checkSetRequestDate(dto, localRequestUtil);
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
	 * 未承認仮締：無効(残業事前申請のみ)で<br>
	 * 且つ勤怠申請(未承認以上)が存在した場合にエラー。<br>
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
		
		// 締日情報を取得
		CutoffDtoInterface cutDto = cutoffUtil.getCutoffForPersonalId(dto.getPersonalId(), dto.getRequestDate());
		// 仮締確認後のチェックであるため、エラーは返さない
		if (cutDto == null) {
			return;
		}
		
		// 未承認仮締：無効(残業事前申請のみ)以外は、勤怠申請の事前申請を許可する為、処理中断
		if (cutDto.getNoApproval() != TimeConst.CODE_NO_APPROVAL_BEFORE_OVER_REQ) {
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
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate(),
				localRequestUtil);
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
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(OvertimeRequestDtoInterface dto) throws MospException {
		if (!isEntered(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で入社していない場合
			PlatformMessageUtility.addErrorEmployeeNotJoin(mospParams);
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
