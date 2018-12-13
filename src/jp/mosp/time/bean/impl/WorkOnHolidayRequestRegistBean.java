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
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkOnHolidayRequestDto;

/**
 * 休日出勤申請登録クラス。
 */
public class WorkOnHolidayRequestRegistBean extends TimeBean implements WorkOnHolidayRequestRegistBeanInterface {
	
	/**
	 * 休日出勤申請DAOクラス。<br>
	 */
	WorkOnHolidayRequestDaoInterface					dao;
	
	/**
	 * 休日出勤申請参照インターフェース。<br>
	 */
	WorkOnHolidayRequestReferenceBeanInterface			workOnHolidayReference;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	private WorkflowDaoInterface						workflowDao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	private WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	private WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	private WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	private WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 振替休日データDAOインターフェース。
	 */
	private SubstituteDaoInterface						substituteDao;
	/**
	 * 振替休日データ参照インターフェース。
	 */
	private SubstituteReferenceBeanInterface			substituteReference;
	/**
	 * 振替休日データ登録インターフェース。
	 */
	private SubstituteRegistBeanInterface				substituteRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	private AttendanceRegistBeanInterface				attendanceRegist;
	
	/**
	 * 勤怠データ参照インターフェース。
	 */
	private AttendanceReferenceBeanInterface			attendanceReference;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface			approvalInfoReference;
	
	/**
	 * 人事入社情報参照クラス。<br>
	 */
	protected EntranceReferenceBeanInterface			entranceReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface			suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface			retirementReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	private CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * 勤怠トランザクション
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkOnHolidayRequestRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkOnHolidayRequestRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		workOnHolidayReference = (WorkOnHolidayRequestReferenceBeanInterface)createBean(WorkOnHolidayRequestReferenceBeanInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		workflowReference = (WorkflowReferenceBean)createBean(WorkflowReferenceBean.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		substituteRegist = (SubstituteRegistBeanInterface)createBean(SubstituteRegistBeanInterface.class);
		attendanceRegist = (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		attendanceTransactionRegist = (AttendanceTransactionRegistBeanInterface)createBean(AttendanceTransactionRegistBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getInitDto() {
		return new TmdWorkOnHolidayRequestDto();
	}
	
	@Override
	public void insert(WorkOnHolidayRequestDtoInterface dto) throws MospException {
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
		dto.setTmdWorkOnHolidayRequestId(dao.nextRecordId());
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
			// 排他確認
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフロー番号から振替休日データを取得
			List<SubstituteDtoInterface> substituteList = substituteDao.findForWorkflow(dto.getWorkflow());
			int size = substituteList.size();
			if (size == 1) {
				SubstituteDtoInterface substituteDto = substituteList.get(0);
				substituteRegist.checkRegist(substituteDto, null);
				if (mospParams.hasErrorMessage()) {
					continue;
				}
				// 勤怠を削除
				attendanceRegist.delete(substituteDto.getPersonalId(), substituteDto.getSubstituteDate());
			} else if (size == 2) {
				SubstituteDtoInterface substitute1Dto = substituteList.get(0);
				SubstituteDtoInterface substitute2Dto = substituteList.get(1);
				substituteRegist.checkRegist(substitute1Dto, substitute2Dto);
				if (mospParams.hasErrorMessage()) {
					continue;
				}
				// 勤怠を削除
				attendanceRegist.delete(substitute1Dto.getPersonalId(), substitute1Dto.getSubstituteDate());
				attendanceRegist.delete(substitute2Dto.getPersonalId(), substitute2Dto.getSubstituteDate());
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			if (!substituteList.isEmpty()) {
				// 勤怠トランザクション登録
				attendanceTransactionRegist.regist(substituteList.get(0));
			}
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				workflowList.add(workflowDto);
			}
		}
	}
	
	@Override
	public void regist(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdWorkOnHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void add(WorkOnHolidayRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdWorkOnHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdWorkOnHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdWorkOnHolidayRequestId());
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
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
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
	protected void checkInsert(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
//		checkDuplicateAdd(dao.findForKey(dto.getPersonalId(), dto.getRequestDate()));
		// 代休日と重複していないかチェック
//		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getRequestDate()));
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdWorkOnHolidayRequestId());
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdWorkOnHolidayRequestId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Deprecated
	protected void checkDelete(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 保留
	}
	
	/**
	 * 対象コードを有効日に無効にすることができるかの確認を行う。<br>
	 * <br>
	 * @param code 対象コード
	 * @param activateDate 有効日
	 */
	protected void checkInactivate(String code, Date activateDate) {
		// 保留
	}
	
	@Override
	public void validate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		workOnHolidayReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public void checkSetRequestDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
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
		checkTightenForSetRequestDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複チェック
		checkWorkOnHolidayOverlap(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休日チェック
		checkHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇申請チェック
		checkHolidayRequest(dto);
	}
	
	@Override
	public void checkDraft(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請日設定時と同様の処理を行う。
		checkSetRequestDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		checkTemporaryClosingFinal(dto);
	}
	
	@Override
	public void checkAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休日チェック
//		checkHolidayDate(dto);
		// 休日出勤申請の重複チェック。
//		checkWorkOnHolidayOverlap(dto);
		// 休日出勤申請の項目の必須チェック。
		checkRequired(dto);
	}
	
	@Override
	public void checkCancelAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
		checkCancel(dto);
	}
	
	@Override
	public void checkWithdrawn(WorkOnHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(WorkOnHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkCancelApproval(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 休日出勤申請である場合
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return;
		}
		// 個人ID取得
		String personalId = dto.getPersonalId();
		// 休日出勤日取得
		Date workDate = dto.getRequestDate();
		// 振替出勤情報リスト準備
		List<SubstituteDtoInterface> substituteList;
		// 承認解除対象振替出勤情報取得
		substituteList = substituteReference.getSubstituteList(personalId, workDate, TimeBean.TIMES_WORK_DEFAULT);
		// 承認解除対象の振替日取得
		Date substituteDate = substituteList.get(0).getSubstituteDate();
		// 承認解除対象の振替日の勤怠を取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(personalId, substituteDate,
				TIMES_WORK_DEFAULT);
		if (attendanceDto != null) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (workflowDto != null
					&& (workflowIntegrate.isApprovable(workflowDto) || workflowIntegrate.isFirstReverted(workflowDto) || workflowIntegrate
						.isCompleted(workflowDto))) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
						mospParams.getName("Transfer", "Day", "Of", "WorkManage", "Application"),
						mospParams.getName("Release"));
				return;
			}
		}
		// 承認解除対象の振替日で更に振替がされている情報リスト取得
		substituteList = substituteReference.getSubstituteList(personalId, substituteDate, TimeBean.TIMES_WORK_DEFAULT);
		// 再振替申請されていない場合休日出勤を確認
		if (substituteList.isEmpty()) {
			// 休日出勤、残業処理がされている情報取得
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHolidayReference.findForSubstitute(personalId,
					substituteDate, TimeBean.TIMES_WORK_DEFAULT);
			// 情報が存在しない場合
			if (workOnHolidayDto == null) {
				return;
			}
			// 承認状態情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
			// 取下の場合
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 処理なし
				return;
			}
			// 下書の場合
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
				// 処理なし
				return;
			}
			// 差戻の場合
			if (PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
				// 処理なし
				return;
			}
			// 他承認状態の場合は承認解除できない
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
			return;
		}
		// 情報リスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 承認状態情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
			// 取下の場合
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 次の申請を取得
				continue;
			}
			// 下書の場合
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
				// 次の申請を取得
				continue;
			}
			// 差戻の場合
			if (PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
				// 次の申請を取得
				continue;
			}
			// 承認解除できない
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
			return;
		}
	}
	
	@Override
	public void checkWorkOnHolidayOverlap(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 休日出勤申請取得
		WorkOnHolidayRequestDtoInterface requestDto = dao.findForKeyOnWorkflow(dto.getPersonalId(),
				dto.getRequestDate());
		if (requestDto == null) {
			return;
		}
		// ワークフローの取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		if (dto.getWorkflow() == workflowDto.getWorkflow()) {
			// ワークフロー番号が同じ場合は同じ申請
			return;
		}
		// エラーメッセージ出力
		addWorkOnHolidayTargetDateWorkOnHolidayRequestErrorMessage(dto.getRequestDate());
	}
	
	@Override
	public void checkHolidayDate(WorkOnHolidayRequestDtoInterface dto) {
		if (dto.getWorkOnHolidayType() == null || dto.getWorkOnHolidayType().isEmpty()) {
			// 勤務形態がない場合
			addWorkTypeNotExistErrorMessage(dto.getRequestDate());
			return;
		} else if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getWorkOnHolidayType())
				&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(dto.getWorkOnHolidayType())) {
			// 法定休日でなく且つ所定休日でない場合
			addWorkOnHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
		}
		int substitute = dto.getSubstitute();
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤(午前)でなく且つ振替出勤(午後)でない場合
			return;
		}
		// 振替出勤(午前)又は振替出勤(午後)の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getWorkOnHolidayType())) {
			// 法定休日の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, mospParams.getName("Legal", "Holiday"),
					mospParams.getName("HalfDay", "Transfer"), mospParams.getName("GoingWork", "Day"));
		}
	}
	
	/**
	 * 申請時の入力チェック。休暇申請チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		checkHolidayRequest(dto, requestUtil);
	}
	
	/**
	 * 申請時の入力チェック。休暇申請チェック。
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(WorkOnHolidayRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		List<HolidayRequestDtoInterface> list = requestUtil.getHolidayList(false);
		int substitute = dto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤の場合
			if (list.isEmpty()) {
				// 休暇申請がない場合
				return;
			}
			// 休暇申請がある場合
			addDuplicateConsecutiveHolidaysErrorMessage();
			return;
		}
		// 振替出勤でない場合
		for (HolidayRequestDtoInterface holidayRequestDto : list) {
			if (holidayRequestDto.getRequestStartDate().equals(dto.getRequestDate())
					|| holidayRequestDto.getRequestEndDate().equals(dto.getRequestDate())) {
				// 休暇申請の初日が休日労働日と同じ場合
				// 又は休暇申請の末日が休日労働日と同じ場合
				addApplicatedRequestErrorMessage(dto.getRequestDate());
				return;
			}
		}
	}
	
	@Override
	public void checkRequired(WorkOnHolidayRequestDtoInterface dto) {
		// 申請理由が空の場合
		if (dto.getRequestReason().isEmpty()) {
			// エラーメッセージ
			addWorkOnHolidayRequestReasonErrorMessage();
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(WorkOnHolidayRequestDtoInterface dto) throws MospException {
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
	protected void checkRetired(WorkOnHolidayRequestDtoInterface dto) throws MospException {
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
	protected void checkSuspended(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で休職している場合
			addEmployeeSuspendedMessage();
		}
	}
	
	/**
	 * 申請時の入力チェック。仮締チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkTightenForSetRequestDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameGoingWorkDay());
	}
	
	@Override
	public void checkTemporaryClosingFinal(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameGoingWorkDay());
		// 振替申請区分確認
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤(振替申請しない)の場合
			return;
		}
		// 振替休日情報リスト取得
		List<SubstituteDtoInterface> substituteList = substituteReference.getSubstituteList(dto.getWorkflow());
		// 振替情報毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 振替日が未締であるかの確認
			cutoffUtil.checkTighten(substituteDto.getPersonalId(), substituteDto.getSubstituteDate(),
					getNameSubstituteDay());
		}
	}
	
	/**
	 * 連続休暇申請期間と重複している場合にエラーメッセージを追加する。
	 */
	protected void addDuplicateConsecutiveHolidaysErrorMessage() {
		String substitutionHolidayWorkRequest = mospParams.getName("Transfer", "GoingWork", "Application");
		mospParams.addErrorMessage(TimeMessageConst.MSG_DUPLICATE_CONSECUTIVE_HOLIDAYS, substitutionHolidayWorkRequest,
				substitutionHolidayWorkRequest, mospParams.getName("Administrator"));
	}
	
	/**
	 * 既に他の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 */
	protected void addApplicatedRequestErrorMessage(Date date) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, getStringDate(date),
				mospParams.getName("GoingWork", "Day"));
	}
	
	/**
	 * 出勤日名称を取得する。
	 * @return 出勤日名称
	 */
	protected String getNameGoingWorkDay() {
		return mospParams.getName("GoingWork") + mospParams.getName("Day");
	}
	
	/**
	 * 振替日名称を取得する。
	 * @return 振替日名称
	 */
	protected String getNameSubstituteDay() {
		return mospParams.getName("Transfer") + mospParams.getName("Day");
	}
	
}
