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
import jp.mosp.framework.constant.MospConst;
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
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
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
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkOnHolidayRequestDto;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 休日出勤申請登録クラス。
 */
public class WorkOnHolidayRequestRegistBean extends TimeBean implements WorkOnHolidayRequestRegistBeanInterface {
	
	/**
	 * 休日出勤申請DAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface				dao;
	
	/**
	 * 休日出勤申請参照インターフェース。<br>
	 */
	WorkOnHolidayRequestReferenceBeanInterface				workOnHolidayReference;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface							workflowDao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 振替休日データDAOインターフェース。
	 */
	private SubstituteDaoInterface							substituteDao;
	/**
	 * 振替休日データ参照インターフェース。
	 */
	private SubstituteReferenceBeanInterface				substituteReference;
	/**
	 * 振替休日データ登録インターフェース。
	 */
	private SubstituteRegistBeanInterface					substituteRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	protected AttendanceRegistBeanInterface					attendanceRegist;
	
	/**
	 * 勤怠データ参照インターフェース。
	 */
	private AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface				approvalInfoReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface				applicationReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBeanInterface				scheduleReference;
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	protected ScheduleDateReferenceBeanInterface			scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	private CutoffUtilBeanInterface							cutoffUtil;
	
	/**
	 * 勤怠トランザクション
	 */
	protected AttendanceTransactionRegistBeanInterface		attendanceTransactionRegist;
	
	/**
	 * 代休申請情報参照インターフェース。
	 */
	protected SubHolidayRequestReferenceBeanInterface		subholidayRequestReference;
	
	/**
	 * 勤務形態変更申請情報参照インターフェース。
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeRequestReference;
	
	/**
	 * 残業申請情報参照インターフェース。
	 */
	protected OvertimeRequestReferenceBeanInterface			overtimeRequestReference;
	
	/**
	 * 時差出勤申請情報参照インターフェース。
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceReference;
	
	
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
		workOnHolidayReference = (WorkOnHolidayRequestReferenceBeanInterface)createBean(
				WorkOnHolidayRequestReferenceBeanInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		workflowReference = (WorkflowReferenceBean)createBean(WorkflowReferenceBean.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(
				WorkflowCommentRegistBeanInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		substituteRegist = (SubstituteRegistBeanInterface)createBean(SubstituteRegistBeanInterface.class);
		attendanceRegist = (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(
				ApprovalInfoReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(
				ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		attendanceTransactionRegist = (AttendanceTransactionRegistBeanInterface)createBean(
				AttendanceTransactionRegistBeanInterface.class);
		subholidayRequestReference = (SubHolidayRequestReferenceBeanInterface)createBean(
				SubHolidayRequestReferenceBeanInterface.class);
		workTypeChangeRequestReference = (WorkTypeChangeRequestReferenceBeanInterface)createBean(
				WorkTypeChangeRequestReferenceBeanInterface.class);
		overtimeRequestReference = (OvertimeRequestReferenceBeanInterface)createBean(
				OvertimeRequestReferenceBeanInterface.class);
		differenceReference = (DifferenceRequestReferenceBeanInterface)createBean(
				DifferenceRequestReferenceBeanInterface.class);
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
	public List<WorkflowDtoInterface> update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return null;
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
				substituteRegist.checkRegist(substituteDto);
				if (mospParams.hasErrorMessage()) {
					continue;
				}
				// 勤怠を削除
				attendanceRegist.delete(substituteDto.getPersonalId(), substituteDto.getSubstituteDate());
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
		return workflowList;
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
	public void checkValidate(WorkOnHolidayRequestDtoInterface dto) {
		// 必須確認(休日出勤日)
		checkRequired(dto.getRequestDate(), mospParams.getName("Work", "Day"), null);
		return;
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
		// 勤怠チェック
		checkAttendance(dto);
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
		// 振替出勤チェック
		checkLegalHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態チェック
		checkWorkTypeCode(dto);
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
		// 半日振替時のチェック
		checkHalfSubstitute(dto);
		// 勤怠の下書きチェック
		checkAttendanceDraft(dto);
	}
	
	/**
	 * 半日振替時のチェックを行う。<br>
	 * ・半日振替→振替申請(全日)、休出申請禁止<br>
	 * ・半日/半日→振替申請(全日)、休出申請禁止<br>
	 * ・出勤日の範囲(午前/午後)確認<br>
	 * @param dto 休出申請
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHalfSubstitute(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 半日振替でない場合
		if (!workOnHolidayReference.useHalfSubstitute()) {
			return;
		}
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date workDate = dto.getRequestDate();
		int substitute = dto.getSubstitute();
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		// 勤務日が振替休日の振替申請情報承認済リストを取得
		List<SubstituteDtoInterface> beforeList = requestUtil.getSubstituteList(true);
		if (beforeList.isEmpty()) {
			return;
		}
		// 振替の振替の場合
		// 出勤日（振替休日）が半振休フラグ準備
		boolean isHalfSubstitute = false;
		// 出勤日（振替休日）振替申請休暇範囲取得
		int beforeHolidayRange = beforeList.get(0).getHolidayRange();
		// 出勤日（振替休日）が半振休か確認
		isHalfSubstitute = beforeHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| beforeHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM;
		// 申請日が半振休の振替であり、午前、午後でない場合
		if (isHalfSubstitute && substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// [勤務日]は全日の振替休日ではないため、全日の振替及び休日出勤は出来ません。申請区分または範囲を選択し直してください。
			mospParams.addErrorMessage(TimeMessageConst.MSG_ALL_DAYS_APPLICATION_IN_HALF_HOLIDAY,
					getStringDate(workDate));
			return;
		}
		// 半振時の休暇範囲チェック
		halfHolidayRangeCheck(dto, beforeList);
	}
	
	/**
	 * 半日振替出勤日に勤怠申請があるか確認する。
	 * 勤怠の下書きがある場合は下書きの削除を行う。
	 * @param dto 休出申請
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkAttendanceDraft(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 半日振替申請でない場合
		int substitute = dto.getSubstitute();
		// 振替出勤(午前)でなく且つ振替出勤(午後)でない場合
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 処理なし
			return;
		}
		String personalId = dto.getPersonalId();
		Date workDate = dto.getRequestDate();
		// 振替出勤日に勤怠申請がされているか確認
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(personalId, workDate,
				TimeBean.TIMES_WORK_DEFAULT);
		if (attendanceDto == null) {
			//勤怠申請がない場合
			return;
		}
		// 勤怠申請がある場合
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)
				|| workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			// 振替出勤日の下書きを削除する
			attendanceRegist.delete(personalId, workDate);
		}
	}
	
	/**
	 * 半振時の休暇範囲確認チェック
	 * @param dto 対象休出申請情報
	 * @param beforeList 申請日が振替休日になる振替申請情報(承認済)
	 */
	protected void halfHolidayRangeCheck(WorkOnHolidayRequestDtoInterface dto,
			List<SubstituteDtoInterface> beforeList) {
		// 休暇範囲準備
		int range = dto.getSubstitute();
		// 休暇範囲設定
		if (range == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			range = TimeConst.CODE_HOLIDAY_RANGE_AM;
		} else if (range == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			range = TimeConst.CODE_HOLIDAY_RANGE_PM;
		} else {
			// 半振休以外処理なし
			return;
		}
		// 振替：休暇範囲取得
		int substituteRange = beforeList.get(0).getSubstituteRange();
		// 全休の場合
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			return;
		}
		// 振替申請が1つかつ休暇範囲が違う場合
		if (beforeList.size() == 1 && range != substituteRange) {
			// エラーメッセージ
			String rep[] = { getStringDate(dto.getRequestDate()), mospParams.getName("Transfer", "Holiday", "Range"),
				mospParams.getName("Application") };
			// [勤務日]は振替休日範囲が異なるため申請できません。
			mospParams.addErrorMessage(TimeMessageConst.MSG_REASON_NOT_ACTION, rep);
			return;
		}
	}
	
	@Override
	public void checkCancelAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 仮締確認
		checkTemporaryClosingFinal(dto);
		// 取消時の確認処理
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
		// 個人ID取得
		String personalId = dto.getPersonalId();
		// 休日出勤日取得
		Date workDate = dto.getRequestDate();
		// 休日出勤申請である場合
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 代休申請確認
			checkSubHolidayRequest(dto, personalId, workDate);
			return;
		}
		// 休日出勤日確認
		checkWorkDate(personalId, workDate);
		// 振替出勤情報準備
		SubstituteDtoInterface substituteDto;
		// 承認解除対象振替出勤情報取得
		substituteDto = substituteReference.getSubstituteDto(personalId, workDate);
		// 承認解除対象の振替日取得
		Date substituteDate = substituteDto.getSubstituteDate();
		// 振替休日確認
		checkSubstituteDate(personalId, substituteDate);
		// 承認解除対象の振替日で更に振替がされている情報リスト取得
		substituteDto = substituteReference.getSubstituteDto(personalId, substituteDate);
		// 再振替情報がある場合
		if (substituteDto != null) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
			// 取下又は下書又は一次戻の場合
			if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)
					|| WorkflowUtility.isFirstReverted(workflowDto)) {
				return;
			}
			// エラーメッセージ（承認解除できない）追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
		} else {
			// 再振替申請されていない場合
			// 休日出勤、残業処理がされている情報取得
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHolidayReference.findForSubstitute(personalId,
					substituteDate, TimeBean.TIMES_WORK_DEFAULT);
			// 情報が存在しない場合
			if (workOnHolidayDto == null) {
				return;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
			// 取下・下書・一次戻の場合
			if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)
					|| WorkflowUtility.isFirstReverted(workflowDto)) {
				// 処理なし
				return;
			}
			// エラーメッセージ（他承認状態の場合は承認解除できない）追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
		}
		return;
	}
	
	/**
	 * 休日出勤日に各種申請が存在するか確認する。<br>
	 * 休日出勤日に勤怠申請、休暇申請、代休申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 * 
	 */
	protected void checkWorkDate(String personalId, Date workDate) throws MospException {
		// 休日出勤日に勤怠申請がある場合
		if (approvalInfoReference.isExistAttendanceTargetDate(personalId, workDate)) {
			// エラーメッセージ
			addOthersRequestErrorMessage(workDate, mospParams.getName("WorkManage"));
		}
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		// 休暇申請チェック
		checkHolidayRequest(requestUtil, workDate);
		// 代休申請リスト取得
		List<SubHolidayRequestDtoInterface> subHolidayList = requestUtil.getSubHolidayList(false);
		// 代休リストが空の場合
		if (!subHolidayList.isEmpty()) {
			// エラーメッセージ追加
			addOthersRequestErrorMessage(workDate, mospParams.getName("CompensatoryHoliday"));
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(personalId, workDate);
		// 残業申請チェック
		checkOverTimeRequest(personalId, workDate);
		// 時差出勤申請チェック
		checkDifferenceRequest(personalId, workDate);
	}
	
	/**
	 * 振替休日に各種申請が存在するか確認する。<br>
	 * 振替休日に勤怠申請、休暇申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替休日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 * 
	 */
	protected void checkSubstituteDate(String personalId, Date substituteDate) throws MospException {
		// 勤怠申請チェック
		checkSubstituteAttendance(personalId, substituteDate);
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, substituteDate);
		// 休暇申請チェック
		checkHolidayRequest(requestUtil, substituteDate);
	}
	
	/**
	 * 休暇申請が存在するか確認する。<br>
	 * 対象日に休暇申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param date 対象日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 * 
	 */
	protected void checkHolidayRequest(RequestUtilBeanInterface requestUtil, Date date) throws MospException {
		// 休暇申請リスト取得
		List<HolidayRequestDtoInterface> list = requestUtil.getHolidayList(false);
		// 休暇申請が存在する場合
		if (!list.isEmpty()) {
			// エラーメッセージ追加
			addOthersRequestErrorMessage(date, mospParams.getName("Vacation"));
		}
	}
	
	/**
	 * 振替休日に勤怠申請が存在するか確認する。<br>
	 * 振替休日に勤怠申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替休日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 * 
	 */
	protected void checkSubstituteAttendance(String personalId, Date substituteDate) throws MospException {
		// 承認解除対象の振替日の勤怠を取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(personalId, substituteDate,
				TIMES_WORK_DEFAULT);
		if (attendanceDto != null) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			// 下書・1次戻以外の場合
			if (workflowDto != null
					&& (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isFirstReverted(workflowDto))) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
						getNameSubstituteDay() + mospParams.getName("Of", "WorkManage", "Application"),
						mospParams.getName("Release"));
				return;
			}
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
		// 取下の場合
		if (WorkflowUtility.isWithDrawn(workflowDto)) {
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
	public void checkHolidayDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dto.getWorkOnHolidayType() == null || dto.getWorkOnHolidayType().isEmpty()) {
			// 勤務形態がない場合
			addWorkTypeNotExistErrorMessage(dto.getRequestDate());
			return;
		} else if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getWorkOnHolidayType())
				&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(dto.getWorkOnHolidayType())) {
			// 法定休日でなく且つ所定休日でない場合
			addWorkOnHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
		}
	}
	
	/**
	 * 勤怠申請チェック。対象日に勤怠申請が行われているか確認する。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public void checkAttendance(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 休日出勤申請取得
		AttendanceDtoInterface requestDto = attendanceReference.findForKey(dto.getPersonalId(), dto.getRequestDate(),
				TimeBean.TIMES_WORK_DEFAULT);
		if (requestDto == null) {
			// 勤怠申請が行われていない場合
			return;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
		// 下書又は取下又は1次戻の場合
		if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)
				|| WorkflowUtility.isFirstReverted(workflowDto)) {
			return;
		}
		// エラーメッセージ出力
		addApplicatedAttendanceErrorMessage(dto.getRequestDate());
	}
	
	/**
	 * 振替出勤チェック。
	 * @param dto 対象DTO
	 */
	protected void checkLegalHolidayDate(WorkOnHolidayRequestDtoInterface dto) {
		int substitute = dto.getSubstitute();
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤(午前)でなく且つ振替出勤(午後)でない場合
			return;
		}
		// 振替出勤(午前)又は振替出勤(午後)の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getWorkOnHolidayType())) {
			// 法定休日の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, TimeNamingUtility.legalHoliday(mospParams),
					mospParams.getName("HalfDay", "Transfer"), getNameGoingWorkDay());
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
	
	/**
	 * 申請時の入力チェック。勤務形態チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeCode(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 振替出勤(勤務形態変更あり)でない場合
			return;
		}
		// 振替出勤(勤務形態変更あり)である場合
		WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(dto.getWorkTypeCode(), dto.getRequestDate());
		if (workTypeDto == null || workTypeDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			addWorkTypeNotExistErrorMessage(dto.getRequestDate());
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
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, targetDate);
		return getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
	}
	
	/**
	 * カレンダ勤務形態コードを取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 振替休日データリストを取得
		List<SubstituteDtoInterface> list = requestUtil.getSubstituteList(true);
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : list) {
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				return substituteDto.getSubstituteType();
			}
		}
		// 適用情報取得
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		// 設定適用マスタの存在チェック
		applicationReference.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// カレンダマスタ取得
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
				targetDate);
		// カレンダ管理の存在チェック
		scheduleReference.chkExistSchedule(scheduleDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// カレンダ日マスタからレコードを取得
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.findForKey(scheduleDto.getScheduleCode(),
				targetDate);
		// カレンダ日の存在チェック
		scheduleDateReference.chkExistScheduleDate(scheduleDateDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// 勤務形態コードを返す
		return scheduleDateDto.getWorkTypeCode();
	}
	
	/**
	 * 代休申請が行われているか確認する。
	 * @param dto 休日出勤申請DTOインターフェース
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @throws MospException MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubHolidayRequest(WorkOnHolidayRequestDtoInterface dto, String personalId, Date workDate)
			throws MospException {
		// 代休申請リストの取得
		List<SubHolidayRequestDtoInterface> subholidayRequestList = subholidayRequestReference
			.findForWorkDate(personalId, workDate);
		// 代休申請が存在するなら
		if (!subholidayRequestList.isEmpty()) {
			for (SubHolidayRequestDtoInterface subholidayRequestDto : subholidayRequestList) {
				// 対象代休申請のワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowReference
					.getLatestWorkflowInfo(subholidayRequestDto.getWorkflow());
				if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)
						|| WorkflowUtility.isFirstReverted(workflowDto)) {
					continue;
				}
				// 代休申請が取り下げられていない場合
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
						DateUtility.getStringDate(subholidayRequestDto.getRequestDate())
								+ mospParams.getName("Of", "CompensatoryHoliday", "Application"),
						mospParams.getName("Release"));
			}
		}
		// 休日出勤日に勤怠申請があるか確認
		if (approvalInfoReference.isExistAttendanceTargetDate(personalId, workDate)) {
			// エラーメッセージ
			mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_DELETE, DateUtility.getStringDate(workDate));
		}
	}
	
	/**
	 * 勤務形態変更申請の確認
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChangeRequest(String personalId, Date workDate) throws MospException {
		// 勤務形態変更申請の確認
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeRequestReference
			.findForKeyOnWorkflow(personalId, workDate);
		if (workTypeChangeDto != null) {
			// 対象勤務形態変更申請のワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
			// 取下、下書、一次戻以外の場合
			if (!WorkflowUtility.isWithDrawn(workflowDto) && !WorkflowUtility.isDraft(workflowDto)
					&& !WorkflowUtility.isFirstReverted(workflowDto)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
						getNameSubstituteDay() + mospParams.getName("Of", "Work", "Form", "Change", "Application"),
						mospParams.getName("Release"));
			}
		}
	}
	
	/**
	 * 代休申請が行われているか確認する。
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @throws MospException MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkOverTimeRequest(String personalId, Date workDate) throws MospException {
		// 残業申請の確認
		List<OvertimeRequestDtoInterface> overtimeRequestList = overtimeRequestReference
			.getOvertimeRequestList(personalId, workDate, workDate);
		if (!overtimeRequestList.isEmpty()) {
			// 残業申請のワークフロー情報取得
			for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
				WorkflowDtoInterface workflowDto = workflowReference
					.getLatestWorkflowInfo(overtimeRequestDto.getWorkflow());
				// 取下、下書、一次戻以外の場合
				if (!WorkflowUtility.isWithDrawn(workflowDto) && !WorkflowUtility.isDraft(workflowDto)
						&& !WorkflowUtility.isFirstReverted(workflowDto)) {
					// エラーメッセージ追加
					mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
							getNameSubstituteDay() + mospParams.getName("Of", "OvertimeWork", "Application"),
							mospParams.getName("Release"));
				}
			}
		}
	}
	
	/**
	 * 時差出勤申請が行われているか確認する。
	 * @param personalId 個人ID
	 * @param workDate 振替出勤日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDifferenceRequest(String personalId, Date workDate) throws MospException {
		// 振替出勤日の時差出勤申請情報取得
		DifferenceRequestDtoInterface differenceDto = differenceReference.findForKeyOnWorkflow(personalId, workDate);
		if (differenceDto == null) {
			// 時差出勤申請が行われていない場合
			return;
		}
		// 時差出勤申請が行われている場合
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceDto.getWorkflow());
		if (workflowDto != null && WorkflowUtility.isDraft(workflowDto)) {
			// 時差出勤申請が下書の場合
			return;
		}
		// 下書・取下以外の時差出勤申請が行われている場合
		mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
				getNameGoingWorkDay() + mospParams.getName("Of", "TimeDifference", "GoingWork", "Application"),
				mospParams.getName("Release"));
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
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, getStringDate(date), getNameGoingWorkDay());
	}
	
	/**
	 * 出勤日名称を取得する。
	 * @return 出勤日名称
	 */
	protected String getNameGoingWorkDay() {
		return mospParams.getName("GoingWork", "Day");
	}
	
	/**
	 * 振替日名称を取得する。
	 * @return 振替日名称
	 */
	protected String getNameSubstituteDay() {
		return mospParams.getName("Transfer", "Day");
	}
	
	/**
	 * 既に勤怠申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 */
	protected void addApplicatedAttendanceErrorMessage(Date date) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, getStringDate(date),
				mospParams.getName("WorkManage"), mospParams.getName("GoingWork", "Day"));
	}
}
