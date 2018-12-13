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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkTypeChangeRequestDto;

/**
 * 勤務形態変更申請登録クラス。
 */
public class WorkTypeChangeRequestRegistBean extends TimeBean implements WorkTypeChangeRequestRegistBeanInterface {
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface		dao;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface		workTypeReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface		suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface		retirementReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface			workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface	workflowCommentRegist;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface				timeApproval;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface				cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface				scheduleUtil;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public WorkTypeChangeRequestRegistBean() {
		super();
	}
	
	/**
	 * {@link TimeBean#TimeBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkTypeChangeRequestRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (WorkTypeChangeRequestDaoInterface)createDao(WorkTypeChangeRequestDaoInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(
				WorkflowCommentRegistBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface getInitDto() {
		return new TmdWorkTypeChangeRequestDto();
	}
	
	@Override
	public void regist(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdWorkTypeChangeRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 更新
			update(dto);
		}
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insert(WorkTypeChangeRequestDtoInterface dto) throws MospException {
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
		dto.setTmdWorkTypeChangeRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdWorkTypeChangeRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdWorkTypeChangeRequestId(dao.nextRecordId());
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
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			WorkTypeChangeRequestDtoInterface dto = (WorkTypeChangeRequestDtoInterface)baseDto;
			// 妥当性チェック
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				// 勤怠下書
				draftAttendance(dto);
			}
		}
	}
	
	@Override
	public void delete(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdWorkTypeChangeRequestId());
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
			WorkTypeChangeRequestDtoInterface dto = (WorkTypeChangeRequestDtoInterface)baseDto;
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
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate()));
	}
	
	/**
	 * 更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdWorkTypeChangeRequestId());
	}
	
	@Override
	public void validate(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 勤怠基本情報確認
		initial(dto.getPersonalId(), dto.getRequestDate(), TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE);
		// カレンダ勤務形態コードを取得
		String scheduledWorkTypeCode = getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate());
		// カレンダ勤務形態コードと選択した勤務形態コードが同じ場合
		if (scheduledWorkTypeCode.equals(dto.getWorkTypeCode())) {
			// エラーメッセージ追加
			addDuplicateWorkType();
		}
	}
	
	@Override
	public void checkSetActivationDate(WorkTypeChangeRequestDtoInterface dto) throws MospException {
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
		checkTighten(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 重複チェック
		checkDuplicate(dto, requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// カレンダチェック
		checkSchedule(dto, requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(dto, requestUtil);
	}
	
	@Override
	public void checkDraft(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 有効日設定時と同様の処理を行う。
		checkSetActivationDate(dto);
	}
	
	@Override
	public void checkAppli(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 下書時と同様の処理を行う。
		checkDraft(dto);
		// 必須項目チェック。
		checkRequired(dto);
	}
	
	@Override
	public void checkCancelAppli(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 仮締チェック
		checkTighten(dto);
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		if (checkAttendanceNoMsg(requestUtil)) {
			// 勤怠が申請されている場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
		// 休暇申請リストを取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(false);
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayList) {
			if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休が存在する場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
				break;
			}
		}
	}
	
	@Override
	public void checkWithdrawn(WorkTypeChangeRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkDelete(WorkTypeChangeRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う。
		checkAppli(dto);
	}
	
	@Override
	public void checkCancelApproval(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	/**
	 * 申請時の入力チェック。必須項目チェック。<br>
	 * @param dto 対象DTO
	 */
	protected void checkRequired(WorkTypeChangeRequestDtoInterface dto) {
		if (!checkRequiredNoMsg(dto)) {
			// 必須項目に入力されていない場合
			addReasonErrorMessage();
		}
	}
	
	/**
	 * 申請時の入力チェック。必須項目チェック。<br>
	 * @param dto 対象DTO
	 * @return 必須項目に入力されている場合true、そうでない場合false
	 */
	protected boolean checkRequiredNoMsg(WorkTypeChangeRequestDtoInterface dto) {
		return !dto.getRequestReason().isEmpty();
	}
	
	/**
	 * 申請時の入力チェック。勤務形態変更申請の重複チェック。<br>
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(WorkTypeChangeRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		if (checkDuplicateNoMsg(dto, requestUtil)) {
			// 重複している場合
			addDuplicateErrorMessage(dto.getRequestDate());
		}
	}
	
	/**
	 * 勤務形態変更申請の重複チェック。<br>
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @return 重複している場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean checkDuplicateNoMsg(WorkTypeChangeRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		WorkTypeChangeRequestDtoInterface requestDto = requestUtil.getWorkTypeChangeDto(false);
		if (requestDto == null) {
			return false;
		}
		if (workflowIntegrate.isWithDrawn(requestDto.getWorkflow())) {
			// 取下の場合
			return false;
		}
		// ワークフロー番号が異なる場合は既に申請されている
		return dto.getWorkflow() != requestDto.getWorkflow();
	}
	
	/**
	 * 申請時の入力チェック。勤怠の申請チェック。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAttendance(RequestUtilBeanInterface requestUtil) throws MospException {
		if (checkAttendanceNoMsg(requestUtil)) {
			// 勤怠申請されている場合
			addApplicatedAttendanceErrorMessage(requestUtil.getApplicatedAttendance().getWorkDate());
		}
	}
	
	/**
	 * 勤怠の申請チェック。
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤怠申請されている場合true、そうでない場合false
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected boolean checkAttendanceNoMsg(RequestUtilBeanInterface requestUtil) throws MospException {
		return requestUtil.getApplicatedAttendance() != null;
	}
	
	/**
	 * 申請時の入力チェック。他の申請チェック。<br>
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequest(WorkTypeChangeRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 休暇申請の有無の確認
		checkHolidayRequest(dto, requestUtil);
	}
	
	/**
	 * 休暇申請チェック。
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(WorkTypeChangeRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 申請日の休暇申請リストを取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(false);
		if (!holidayList.isEmpty()) {
			// 未承認・n次承認・n次戻・承認済の休暇申請が存在する場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
		}
		// 代休申請リストを取得
		List<SubHolidayRequestDtoInterface> subHolidayList = requestUtil.getSubHolidayList(false);
		if (!subHolidayList.isEmpty()) {
			// 未承認・n次承認・n次戻・承認済の代休申請が存在する場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("CompensatoryHoliday"));
		}
	}
	
	@Override
	public void checkSchedule(String personalId, Date targetDate) throws MospException {
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, targetDate);
		checkSchedule(personalId, targetDate, requestUtil);
	}
	
	/**
	 * 申請時の入力チェック。カレンダチェック。<br>
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSchedule(WorkTypeChangeRequestDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		checkSchedule(dto.getPersonalId(), dto.getRequestDate(), requestUtil);
	}
	
	/**
	 * 申請時の入力チェック。カレンダチェック。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSchedule(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException {
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty() || TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 出勤日でない場合
			addDayOffErrorMessage(targetDate);
		} else if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 休日出勤の場合
			addApplicatedRequestErrorMessage(targetDate);
		}
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		return scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
	}
	
	@Override
	public String getScheduledWorkTypeName(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		return getScheduledWorkTypeName(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public String getScheduledWorkTypeName(String personalId, Date targetDate) throws MospException {
		String workTypeCode = getScheduledWorkTypeCode(personalId, targetDate);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			return "";
		} else if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return workTypeReference.getParticularWorkTypeName(workTypeCode);
		}
		return workTypeReference.getWorkTypeAbbrAndTime(workTypeCode, targetDate);
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(WorkTypeChangeRequestDtoInterface dto) throws MospException {
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
	protected void checkRetired(WorkTypeChangeRequestDtoInterface dto) throws MospException {
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
	protected void checkSuspended(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で休職している場合
			addEmployeeSuspendedMessage();
		}
	}
	
	/**
	 * 申請時の入力チェック。仮締チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkTighten(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), mospParams.getName("GoingWork", "Day"));
	}
	
	@Override
	public void draftAttendance(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
			// 承認済でない場合
			return;
		}
		// Bean初期化
		timeApproval = (TimeApprovalBeanInterface)createBean(TimeApprovalBeanInterface.class);
		// 勤怠再下書
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestDate(), false, true, false);
	}
	
	/**
	 * 理由が入力されていない場合、エラーメッセージを追加する。<br>
	 */
	protected void addReasonErrorMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED, mospParams.getName("Reason"));
	}
	
	/**
	 * 既に同じ勤務形態が適用されている場合、エラーメッセージを追加する。
	 */
	protected void addDuplicateWorkType() {
		String errorMes1 = mospParams.getName("Work", "Form");
		String errorMes2 = mospParams.getName("Change", "Later", "Work", "Form");
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_4, errorMes1, errorMes2);
	}
	
	/**
	 * 既に勤務形態変更申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 */
	protected void addDuplicateErrorMessage(Date date) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, DateUtility.getStringDate(date),
				mospParams.getName("Work", "Form", "Change"), mospParams.getName("GoingWork", "Day"));
	}
	
	/**
	 * 既に勤怠申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 */
	protected void addApplicatedAttendanceErrorMessage(Date date) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, getStringDate(date),
				mospParams.getName("WorkManage"), mospParams.getName("GoingWork", "Day"));
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
	 * 出勤日でない場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 */
	protected void addDayOffErrorMessage(Date date) {
		String workDay = mospParams.getName("GoingWork", "Day");
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, getStringDate(date), workDay, workDay);
	}
	
	/**
	 * 申請可能期間より先の日付で申請が行われている場合、エラーメッセージを追加する。<br>
	 */
	protected void addRequestPeriodErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_2,
				mospParams.getName("Work", "Form", "Change", "Application"), mospParams.getName("No1", "Months"),
				mospParams.getName("GoingWork", "Day"));
	}
	
}
