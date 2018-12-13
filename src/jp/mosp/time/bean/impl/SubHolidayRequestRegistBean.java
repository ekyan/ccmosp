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
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubHolidayRequestDto;

/**
 * 代休申請登録クラス。
 */
public class SubHolidayRequestRegistBean extends TimeBean implements SubHolidayRequestRegistBeanInterface {
	
	/**
	 * 代休申請DAOクラス。<br>
	 */
	SubHolidayRequestDaoInterface						dao;
	/**
	 * 代休申請参照インターフェース。<br>
	 */
	SubHolidayRequestReferenceBeanInterface				subHolidayReference;
	/**
	 * ワークフローDAOクラス。<br>
	 */
	private WorkflowDaoInterface						workflowDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	private WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	private WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	private AttendanceRegistBeanInterface				attendanceRegist;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface					timeApproval;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface			approvalInfoReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	private CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	private RequestUtilBeanInterface					requestUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	private ScheduleUtilBeanInterface					scheduleUtil;
	
	private SubHolidayDaoInterface						subHolidayDao;
	
	private AttendanceDaoInterface						attendanceDao;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface			applicationReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBeanInterface			scheduleReference;
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	protected ScheduleDateReferenceBeanInterface		scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface			workTypeReference;
	
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
	 * 振替休日データ参照クラス。<br>
	 */
	protected SubstituteReferenceBeanInterface			substituteReference;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayRequestRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public SubHolidayRequestRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		subHolidayReference = (SubHolidayRequestReferenceBeanInterface)createBean(SubHolidayRequestReferenceBeanInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
		subHolidayDao = (SubHolidayDaoInterface)createDao(SubHolidayDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		attendanceRegist = (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		attendanceTransactionRegist = (AttendanceTransactionRegistBeanInterface)createBean(AttendanceTransactionRegistBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestDtoInterface getInitDto() {
		return new TmdSubHolidayRequestDto();
	}
	
	@Override
	public void insert(SubHolidayRequestDtoInterface dto) throws MospException {
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
		dto.setTmdSubHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public boolean update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		boolean containsHalfHoliday = false;
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
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				workflowList.add(workflowDto);
				// 勤怠削除
				deleteAttendance(dto);
				// 勤怠下書
				draftAttendance(dto);
				// 勤怠トランザクション登録
				attendanceTransactionRegist.regist(dto);
				// 午前休又は午後休の場合
				containsHalfHoliday = true;
			}
		}
		return containsHalfHoliday;
	}
	
	@Override
	public void regist(SubHolidayRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdSubHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void add(SubHolidayRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdSubHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdSubHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(SubHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdSubHolidayRequestId());
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
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
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
	protected void checkInsert(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate(), dto.getHolidayRange()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdSubHolidayRequestId());
	}
	
	@Override
	public void validate(SubHolidayRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		subHolidayReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public void checkSetRequestDate(SubHolidayRequestDtoInterface dto) throws MospException {
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
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 勤務形態チェック
		checkWorkType(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 代休申請・休暇申請・振替休日の重複チェック
		checkDuplicate(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkAttendance(localRequestUtil);
	}
	
	@Override
	public void checkDraft(SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請日決定時と同様の処理を行う。
		checkSetRequestDate(dto);
//		checkTemporaryClosingFinal(dto);
//		// 他の申請チェック。
//		checkRequest(dto);
	}
	
	@Override
	public void checkAppli(SubHolidayRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
//		// 代休申請の重複チェック
//		checkSubHolidayOverlap(dto);
//		// 勤怠の申請チェック
//		checkAttendance(dto);
		// 代休日数チェック。
		checkDay(dto);
	}
	
	@Override
	public void checkCancelAppli(SubHolidayRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
	}
	
	@Override
	public void checkWithdrawn(SubHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
	}
	
	@Override
	public void checkCancelApproval(SubHolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(SubHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	/**
	 * 休暇申請・代休申請・振替休日の重複チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(SubHolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		boolean subHolidayRangeAm = false;
		boolean subHolidayRangePm = false;
		boolean holidayRangeAm = false;
		boolean holidayRangePm = false;
		boolean holidayRangeTime = false;
		boolean substituteRangeAm = false;
		boolean substituteRangePm = false;
		// 代休申請チェック
		List<SubHolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getRequestDate());
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : list) {
			long workflow = subHolidayRequestDto.getWorkflow();
			if (workflowIntegrate.isWithDrawn(workflow)) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflow) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int subHolidayRange = subHolidayRequestDto.getHolidayRange();
			if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				subHolidayRangeAm = true;
			} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				subHolidayRangePm = true;
			}
		}
		if (subHolidayRangeAm && subHolidayRangePm) {
			// 前半休及び後半休の場合
			addSubHolidayTargetDateSubHolidayRequestErrorMessage();
			return;
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (subHolidayRangeAm || subHolidayRangePm) {
				// 前半休・後半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangeAm) {
				// 前半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (subHolidayRangePm) {
				// 後半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		}
		// 休暇申請チェック
		int holidayRange = localRequestUtil.checkHolidayRangeHoliday(localRequestUtil.getHolidayList(false));
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
			return;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			holidayRangeAm = true;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			holidayRangePm = true;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休の場合
			holidayRangeTime = true;
		}
		// 振替休日チェック
		int substituteRange = localRequestUtil.checkHolidayRangeSubstitute(localRequestUtil.getSubstituteList(false));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addSubstituteErrorMessage(dto.getRequestDate());
			return;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			substituteRangeAm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			substituteRangePm = true;
		}
		if ((subHolidayRangeAm || holidayRangeAm || substituteRangeAm)
				&& (subHolidayRangePm || holidayRangePm || substituteRangePm)) {
			// 前半休及び後半休を組み合わせて全休となる場合
			addSubHolidayTargetDateSubHolidayRequestErrorMessage();
			return;
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (holidayRangeAm || holidayRangePm) {
				// 前半休・後半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (holidayRangeAm) {
				// 前半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (holidayRangePm) {
				// 後半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (substituteRangeAm || substituteRangePm) {
				// 前半休・後半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (substituteRangeAm) {
				// 前半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (substituteRangePm) {
				// 後半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		}
		int range = dto.getHolidayRange();
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangePm || holidayRangePm || substituteRangePm) {
				// 後半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (subHolidayRangeAm || holidayRangeAm || substituteRangeAm) {
				// 前半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		}
		// 残業申請チェック
		checkOvertimeWorkRequest(dto, localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替出勤チェック
		checkSubstituteWorkRequest(localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時差出勤申請チェック
		checkDifferenceRequest(localRequestUtil, range);
	}
	
	@Override
	@Deprecated
	public void checkSubHolidayOverlap(SubHolidayRequestDtoInterface dto) throws MospException {
		boolean holidayRangeAm = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayRangePm = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM;
		// 代休申請リスト取得
		List<SubHolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getRequestDate());
		for (SubHolidayRequestDtoInterface requestDto : list) {
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(requestDto.getWorkflow());
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
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
					|| requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				// 休暇内容が重複しています。代休日または休暇範囲を選択し直してください。
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (dto.getHolidayRange() == requestDto.getHolidayRange()) {
					// 休暇内容が重複しています。代休日または休暇範囲を選択し直してください。
					addSubHolidayTargetDateSubHolidayRequestErrorMessage();
					return;
				}
			}
			if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休の場合
				holidayRangeAm = true;
			}
			if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午後休の場合
				holidayRangePm = true;
			}
		}
		
		// 午前休又は午後休の場合
		if (holidayRangeAm || holidayRangePm) {
			// 各種申請情報取得
			requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
			int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
			boolean allHoliday = (holidayRangeAm && holidayRangePm)
					|| (holidayRangeAm && holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM)
					|| (holidayRangePm && holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM)
					|| (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM && holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM);
			// 残業申請がある場合
			if (allHoliday && !requestUtil.getOverTimeList(false).isEmpty()) {
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("OvertimeWork"));
//				return;
//			}
//			if (allHoliday && requestUtil.getDifferenceDto(false) != null) {
//				// 時差出勤申請がある場合
//				StringBuffer sb = new StringBuffer();
//				sb.append(mospParams.getName("TimeDifference"));
//				sb.append(mospParams.getName("GoingWork"));
//				addOthersRequestErrorMessage(dto.getRequestDate(), sb.toString());
			}
		}
	}
	
	/**
	 * 残業申請チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeWorkRequest(SubHolidayRequestDtoInterface dto,
			RequestUtilBeanInterface localRequestUtil, int holidayRange) throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		if (localRequestUtil.getOverTimeList(false).isEmpty()) {
			// 残業申請されていない場合
			return;
		}
		// 残業申請されている場合
		addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("OvertimeWork"));
	}
	
	/**
	 * 振替休日申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubstituteWorkRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = localRequestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return;
		}
		// 振出・休出申請が承認済である場合
		int substitute = workOnHolidayRequestDto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤の場合
			return;
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 振替出勤(全日)の場合
			return;
		}
//		else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
//			// 振替出勤(午前)の場合
//			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
//				return;
//			}
//		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
//			// 振替出勤(午後)の場合
//			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
//				return;
//			}
//		}
//		addSubHolidayTargetWorkDateHolidayErrorMessage(workOnHolidayRequestDto.getRequestDate());
		// 半日振替出勤日は休暇申請を不可とする
		addOthersRequestErrorMessage(workOnHolidayRequestDto.getRequestDate(),
				mospParams.getName("HalfDay", "Transfer", "GoingWork"));
	}
	
	/**
	 * 勤務形態変更申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChangeRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = localRequestUtil.getWorkTypeChangeDto(false);
		if (workTypeChangeRequestDto == null) {
			// 勤務形態変更申請されていない場合
			return;
		}
		// 勤務形態変更申請されている場合
		addOthersRequestErrorMessage(workTypeChangeRequestDto.getRequestDate(),
				mospParams.getName("Work", "Form", "Change"));
	}
	
	/**
	 * 時差出勤申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDifferenceRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL && holidayRange != TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 全休でなく且つ前半休でない場合
			return;
		}
		// 全休又は前半休である場合
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(false);
		if (differenceRequestDto == null) {
			// 時差出勤申請されていない場合
			return;
		}
		// 時差出勤申請されている場合
		addOthersRequestErrorMessage(differenceRequestDto.getRequestDate(),
				mospParams.getName("TimeDifference", "GoingWork"));
	}
	
	/**
	 * 他の申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequest(RequestUtilBeanInterface localRequestUtil) throws MospException {
		// 勤怠申請チェック
		checkAttendance(localRequestUtil);
	}
	
	@Override
	public void checkRequest(SubHolidayRequestDtoInterface dto) throws MospException {
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
				// （日付）は休日出勤が申請されています。代休日を選択し直してください。
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkingHoliday"));
				return;
			}
			// 承認済かつ振替出勤申請の休日出勤申請情報がない場合
			if (requestUtil.getWorkOnHolidayDto(true) == null) {
				// 法定休日又は所定休日の場合
				addSubHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
				return;
			}
		}
		// 振替休日
		if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			addOthersRequestErrorMessage(dto.getRequestDate(),
					mospParams.getName("Transfer") + mospParams.getName("GoingWork"));
			return;
		}
		// 休暇申請
		int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
		// 時間休の場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// エラーメッセージ設定
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
			return;
		}
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM
				|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL && (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						+ TimeConst.CODE_HOLIDAY_RANGE_PM))
				|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM && holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM)
				|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM && holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM)) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Vacation"));
			return;
		}
		// 残業申請
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL && !requestUtil.getOverTimeList(false).isEmpty()) {
			// 表示例 <日付>は既に他の申請が行われています。代休日を選択し直してください。 
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("OvertimeWork"));
			return;
		}
		// 勤務形態変更申請
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
				&& requestUtil.getWorkTypeChangeDto(false) != null) {
			// 全休の場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Work", "Form", "Change"));
			return;
		}
		// 時差出勤申請
		if ((dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL || dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)
				&& requestUtil.getDifferenceDto(false) != null) {
			// 全休又は午前休の場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("TimeDifference"));
			sb.append(mospParams.getName("GoingWork"));
			addOthersRequestErrorMessage(dto.getRequestDate(), sb.toString());
		}
	}
	
	/**
	 * 勤怠申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(RequestUtilBeanInterface localRequestUtil) throws MospException {
		AttendanceDtoInterface attendanceDto = localRequestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			// 勤怠申請されていない場合
			return;
		}
		// 勤怠申請されている場合
		addSubHolidayTargetWorkDateAttendanceRequestErrorMessage(attendanceDto.getWorkDate());
	}
	
	@Override
	public void checkAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), dto.getRequestDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
			// 下書の場合
			return;
		}
		if (workflowDto.getWorkflowStage() == 0
				&& PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 1次戻の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addSubHolidayTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	@Override
	public void checkDay(SubHolidayRequestDtoInterface dto) throws MospException {
		SubHolidayDtoInterface subHolidayDto = subHolidayDao.findForKey(dto.getPersonalId(), dto.getWorkDate(),
				dto.getTimesWork(), dto.getWorkDateSubHolidayType());
		double subHolidayDays = subHolidayDto.getSubHolidayDays();
		List<SubHolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getWorkDate(),
				dto.getTimesWork(), dto.getWorkDateSubHolidayType());
		for (SubHolidayRequestDtoInterface requestDto : list) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書又は取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflowDto.getWorkflow()) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int holidayRange = requestDto.getHolidayRange();
			if (holidayRange == 1) {
				subHolidayDays--;
			} else if (holidayRange == 2 || holidayRange == 3) {
				subHolidayDays -= TimeConst.HOLIDAY_TIMES_HALF;
			}
		}
		int holidayRange = dto.getHolidayRange();
		String errorMes1 = "";
		if (holidayRange == 1) {
			// 全休の場合
			if (subHolidayDays < 1) {
				// 代休日数が1未満の場合
				errorMes1 = "1";
				// 表示例 代休日数が1未満の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_SUBHOLIDAY_DAY_CHECK, errorMes1);
			}
		} else if (holidayRange == 2 || holidayRange == 3) {
			// 午前休又は午後休の場合
			if (subHolidayDays < TimeConst.HOLIDAY_TIMES_HALF) {
				errorMes1 = "0.5";
				// 代休日数が0.5未満の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_SUBHOLIDAY_DAY_CHECK, errorMes1);
			}
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(SubHolidayRequestDtoInterface dto) throws MospException {
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
	protected void checkRetired(SubHolidayRequestDtoInterface dto) throws MospException {
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
	protected void checkSuspended(SubHolidayRequestDtoInterface dto) throws MospException {
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で休職している場合
			addEmployeeSuspendedMessage();
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getCompensatoryHolidayDay());
	}
	
	/**
	 * 勤務形態チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(SubHolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		String workTypeCode = getScheduledWorkTypeCode(dto, localRequestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日の場合
			addSubHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
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
	protected String getScheduledWorkTypeCode(SubHolidayRequestDtoInterface dto,
			RequestUtilBeanInterface localRequestUtil) throws MospException {
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
	
	@Override
	public void deleteAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		int range = dto.getHolidayRange();
		boolean holidayAm = range == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayPm = range == TimeConst.CODE_HOLIDAY_RANGE_PM;
		if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
			return;
		} else if (holidayAm || holidayPm) {
			// 半休の場合
			// 各種申請情報取得
			requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
			// 代休
			List<SubHolidayRequestDtoInterface> list = requestUtil.getSubHolidayList(false);
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : list) {
				if ((holidayAm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayPm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
					attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
					return;
				}
			}
			// 休暇
			List<HolidayRequestDtoInterface> holidayRequestList = requestUtil.getHolidayList(false);
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if ((holidayAm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayPm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
					attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
					break;
				}
			}
		}
	}
	
	@Override
	public void draftAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
			// 承認済でない場合
			return;
		}
		// Bean初期化
		timeApproval = (TimeApprovalBeanInterface)createBean(TimeApprovalBeanInterface.class);
		boolean deleteRest = false;
		int holidayRange = dto.getHolidayRange();
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			deleteRest = true;
		}
		// 勤怠再下書
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestDate(), deleteRest, false, false);
	}
	
	/**
	 * 振替休日である場合のエラーメッセージを設定する。<br>
	 * @param date 対象日
	 */
	protected void addSubstituteErrorMessage(Date date) {
		addOthersRequestErrorMessage(date, mospParams.getName("Transfer", "Holiday"));
	}
	
	/**
	 * 代休日名称を取得する。
	 * @return 代休日名称
	 */
	protected String getCompensatoryHolidayDay() {
		return mospParams.getName("CompensatoryHoliday") + mospParams.getName("Day");
	}
	
}
