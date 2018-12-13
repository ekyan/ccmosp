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
import java.util.Map;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
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
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayRequestDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請登録クラス。
 */
public class HolidayRequestRegistBean extends TimeApplicationBean implements HolidayRequestRegistBeanInterface {
	
	/**
	 * 休暇申請DAOクラス。<br>
	 */
	HolidayRequestDaoInterface						dao;
	
	/**
	 * 休暇申請参照インターフェース。<br>
	 */
	HolidayRequestReferenceBeanInterface			holidayRequestReference;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	private WorkflowDaoInterface					workflowDao;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	private WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface	workflowCommentRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	private AttendanceRegistBeanInterface			attendanceRegist;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface				timeApproval;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface		approvalInfoReference;
	
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
	 * 振替休日データ参照クラス。<br>
	 */
	protected SubstituteReferenceBeanInterface		substituteReference;
	
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
	protected RequestUtilBeanInterface				requestUtil;
	
	/**
	 * 勤怠データDAOクラス。
	 */
	private AttendanceDaoInterface					attendanceDao;
	
	/**
	 * 有給休暇情報参照クラス。
	 */
	private PaidHolidayInfoReferenceBeanInterface	paidHolidayInfoReference;
	
	/**
	 * ストック休暇情報参照クラス。
	 */
	private StockHolidayInfoReferenceBeanInterface	stockHolidayInfoReference;
	
	/**
	 * 休暇種別管理DAOクラス。
	 */
	private HolidayDaoInterface						holidayDao;
	
	/**
	 * 休暇データDAOクラス。
	 */
	private HolidayDataDaoInterface					holidayDataDao;
	
	/**
	 * 休暇種別管理参照クラス。
	 */
	protected HolidayReferenceBeanInterface			holidayReference;
	
	/**
	 * 休暇データ参照クラス。
	 */
	private HolidayInfoReferenceBeanInterface		holidayInfoReference;
	
	/**
	 * 勤務形態項目管理DAOクラス。
	 */
	private WorkTypeItemDaoInterface				workTypeItemDao;
	
	/**
	 * 振替休日データDAO
	 */
	private SubstituteDaoInterface					substituteDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayRequestRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HolidayRequestRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// DAO準備
		dao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		holidayRequestReference = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
		attendanceRegist = (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		holidayDataDao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		paidHolidayInfoReference = (PaidHolidayInfoReferenceBeanInterface)createBean(PaidHolidayInfoReferenceBeanInterface.class);
		stockHolidayInfoReference = (StockHolidayInfoReferenceBeanInterface)createBean(StockHolidayInfoReferenceBeanInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		holidayReference = (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
		holidayInfoReference = (HolidayInfoReferenceBeanInterface)createBean(HolidayInfoReferenceBeanInterface.class);
		workTypeItemDao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
	}
	
	@Override
	public HolidayRequestDtoInterface getInitDto() {
		return new TmdHolidayRequestDto();
	}
	
	@Override
	public void insert(HolidayRequestDtoInterface dto) throws MospException {
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
		dto.setTmdHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	@Deprecated
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
			HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
			// 申請時の確認
			checkHolidayType(dto);
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestStartDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				workflowList.add(workflowDto);
				// 勤怠削除
				deleteAttendance(dto);
				// 勤怠下書
				draftAttendance(dto);
				int holidayRange = dto.getHolidayRange();
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休又は午後休の場合
					containsHalfHoliday = true;
				}
			}
		}
		return containsHalfHoliday;
	}
	
	@Override
	public void regist(HolidayRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void add(HolidayRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(HolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayRequestId());
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
			HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
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
	protected void checkInsert(HolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate(),
				dto.getHolidayType1(), dto.getHolidayType2(), dto.getHolidayRange(), dto.getStartTime()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdHolidayRequestId());
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(HolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdHolidayRequestId());
	}
	
	@Override
	public void validate(HolidayRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		holidayRequestReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestStartDate());
	}
	
	@Override
	public void checkSetRequestDate(HolidayRequestDtoInterface dto) throws MospException {
		// 期間チェック
		checkPeriod(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
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
		// 日リスト取得
		List<Date> list = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休職チェック
		checkSuspended(dto, list);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 日毎のチェック
		checkDailyForSetRequestDate(dto, list);
	}
	
	@Override
	public void checkDraft(HolidayRequestDtoInterface dto) throws MospException {
		// 期間チェック
		checkPeriod(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
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
		// 日リスト取得
		List<Date> list = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休職チェック
		checkSuspended(dto, list);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時間休の限度チェック
		checkTimeHolidayLimit(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 特別休暇・その他休暇チェック
		checkLimitDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 日毎のチェック
		checkDailyForDraft(dto, list);
	}
	
	/**
	 * 時間休の限度チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void checkTimeHolidayLimit(HolidayRequestDtoInterface dto) throws MospException {
		// 時間休でない場合
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return;
		}
		// 個人ID・申請日取得
		String personalId = dto.getPersonalId();
		Date requestDate = dto.getRequestStartDate();
		// 有休休暇設定があり時間休の場合
		if (hasPaidHolidaySettings(personalId, requestDate)) {
			// 有休休暇設定が時間単位取得が有効か確認
			if (paidHolidayDto.getTimelyPaidHolidayFlag() != 0) {
				String mes1 = mospParams.getName("Time", "Unit", "Acquisition");
				String mes2 = mospParams.getName("HolidayTime");
				String mes3 = mospParams.getName("PaidVacation", "Set");
				mospParams.addErrorMessage(TimeMessageConst.MSG_UNSETTING, new String[]{ mes1, mes2, mes3 });
				return;
			}
		}
		// 限度時間取得
		int[] limit = paidHolidayInfoReference.getHolidayTimeUnitLimit(personalId, requestDate, false, dto);
		// 0日0時間の場合
		if (limit[0] <= 0 && limit[1] <= 0) {
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("HolidayTime"));
			sb.append(mospParams.getName("Of"));
			sb.append(mospParams.getName("Years"));
			addHolidayNumDaysExcessErrorMessage(sb.toString(), mospParams.getName("Time"));
			return;
		}
		// 有給休暇時間承認状態別休数回数マップ取得
		Map<String, Integer> timeHoliday = holidayRequestReference.getTimeHolidayStatusTimesMap(personalId,
				requestDate, dto);
		// マップが空の場合
		if (timeHoliday.isEmpty()) {
			return;
		}
		// 取下・下書以外合算
		int holidayTimes = timeHoliday.get(mospParams.getName("Finish"))
				+ timeHoliday.get(mospParams.getName("Register")) + timeHoliday.get(mospParams.getName("Back"));
		// マップがあり、時休の1日の限度時間以上取得している場合
		if (timeHoliday.isEmpty() == false && holidayTimes >= getPrescribedWorkHour(dto)) {
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("HolidayTime"));
			sb.append(mospParams.getName("Of"));
			sb.append(mospParams.getName("No1"));
			sb.append(mospParams.getName("Day"));
			addHolidayNumDaysExcessErrorMessage(sb.toString(), mospParams.getName("Time"));
		}
	}
	
	@Override
	public void checkAppli(HolidayRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇申請の重複チェック。
//		checkHolidayOverlap(dto);
		// 勤怠の申請チェック。
//		checkAttendance(dto);
		// 休暇申請の項目の必須チェック。
		checkRequired(dto);
	}
	
	@Override
	public void checkCancelAppli(HolidayRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (dto.getRequestStartDate().equals(dto.getRequestEndDate())
				&& approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestStartDate())) {
			addOthersRequestErrorMessage(dto.getRequestStartDate(), mospParams.getName("WorkManage"));
		}
	}
	
	@Override
	public void checkWithdrawn(HolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(HolidayRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
	}
	
	@Override
	public void checkCancelApproval(HolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(HolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	/**
	 * 申請日設定時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param list リスト 
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForSetRequestDate(HolidayRequestDtoInterface dto, List<Date> list) throws MospException {
		for (Date date : list) {
			checkDailyForSetRequestDate(dto, date);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 申請日設定時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param date 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForSetRequestDate(HolidayRequestDtoInterface dto, Date date) throws MospException {
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), date);
		// 勤務形態コード取得
		String workTypeCode = getScheduledWorkTypeCode(dto, date, localRequestUtil);
		// 勤務形態チェック
		checkWorkType(dto, date, workTypeCode);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (isLegalDaysOff(workTypeCode) || isPrescribedDaysOff(workTypeCode) || isWorkOnLegalDaysOff(workTypeCode)
				|| isWorkOnPrescribedDaysOff(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		// 休暇申請・代休申請・振替休日の重複チェック
		checkDuplicate(dto, date, localRequestUtil, false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(localRequestUtil);
	}
	
	/**
	 * 下書時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param list リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForDraft(HolidayRequestDtoInterface dto, List<Date> list) throws MospException {
		for (Date date : list) {
			checkDailyForDraft(dto, date);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 下書時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param date 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForDraft(HolidayRequestDtoInterface dto, Date date) throws MospException {
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), date);
		// 勤務形態コード取得
		String workTypeCode = getScheduledWorkTypeCode(dto, date, localRequestUtil);
		// 勤務形態チェック
		checkWorkType(dto, date, workTypeCode);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (isLegalDaysOff(workTypeCode) || isPrescribedDaysOff(workTypeCode) || isWorkOnLegalDaysOff(workTypeCode)
				|| isWorkOnPrescribedDaysOff(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		// 休暇申請・代休申請の重複チェック
		checkDuplicate(dto, date, localRequestUtil, true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時短時間との重複チェック
		checkShortTime(dto, localRequestUtil, workTypeCode, date);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(localRequestUtil);
	}
	
	/**
	 * 休暇申請・代休申請・振替休日の重複チェック。
	 * @param dto 対象DTO
	 * @param targetDate 対象日
	 * @param localRequestUtil 申請ユーティリティ
	 * @param isDraft 下書・申請の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(HolidayRequestDtoInterface dto, Date targetDate,
			RequestUtilBeanInterface localRequestUtil, boolean isDraft) throws MospException {
		boolean holidayRangeAm = false;
		boolean holidayRangePm = false;
		boolean holidayRangeTime = false;
		boolean subHolidayRangeAm = false;
		boolean subHolidayRangePm = false;
		boolean substituteRangeAm = false;
		boolean substituteRangePm = false;
		// 休暇申請チェック
		List<HolidayRequestDtoInterface> list = dao.findForTermOnWorkflow(dto.getPersonalId(), targetDate, targetDate);
		for (HolidayRequestDtoInterface holidayRequestDto : list) {
			long workflow = holidayRequestDto.getWorkflow();
			if (workflowIntegrate.isWithDrawn(workflow)) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflow) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int holidayRange = holidayRequestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				addHolidayOverlapRange1ErrorMessage();
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
				if (isDraft) {
					// 下書時・申請時の場合
					if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME
							&& checkDuplicationTimeZone(holidayRequestDto.getStartTime(),
									holidayRequestDto.getEndTime(), dto.getStartTime(), dto.getEndTime())) {
						// 時間休の時間帯が重複している場合
						addOthersRequestErrorMessage(targetDate, mospParams.getName("HolidayTime"));
						return;
					}
				}
			}
		}
		if (holidayRangeAm && holidayRangePm) {
			// 前半休及び後半休の場合
			addHolidayOverlapRange1ErrorMessage();
			return;
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (holidayRangeAm || holidayRangePm || holidayRangeTime) {
					// 前半休・後半休・時間休と重複している場合
					addHolidayOverlapRange1ErrorMessage();
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (holidayRangeAm) {
					// 前半休と重複している場合
					addHolidayOverlapRange2ErrorMessage();
					return;
				} else if (holidayRangeTime) {
					// 時間休と重複している場合
					addDuplicateTimeHolidayRequestErrorMessage(mospParams.getName("HalfTime"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (holidayRangePm) {
					// 後半休と重複している場合
					addHolidayOverlapRange2ErrorMessage();
					return;
				} else if (holidayRangeTime) {
					// 時間休と重複している場合
					addDuplicateTimeHolidayRequestErrorMessage(mospParams.getName("HalfTime"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (holidayRangeAm || holidayRangePm) {
					// 前半休・後半休と重複している場合
					addDuplicateTimeHolidayRequestErrorMessage(mospParams.getName("HalfTime"));
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (holidayRangeAm || holidayRangePm || holidayRangeTime)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休・時間休と重複している場合
				addHolidayOverlapRange1ErrorMessage();
				return;
			}
		}
		// 代休申請チェック
		int subHolidayRange = localRequestUtil.checkHolidayRangeSubHoliday(localRequestUtil.getSubHolidayList(false));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
			return;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			subHolidayRangeAm = true;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			subHolidayRangePm = true;
		}
		// 振替休日チェック
		int substituteRange = localRequestUtil.checkHolidayRangeSubstitute(localRequestUtil.getSubstituteList(false));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addSubstituteErrorMessage(targetDate);
			return;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			substituteRangeAm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			substituteRangePm = true;
		}
		if ((holidayRangeAm || subHolidayRangeAm || substituteRangeAm)
				&& (holidayRangePm || subHolidayRangePm || substituteRangePm)) {
			// 前半休及び後半休を組み合わせて全休となる場合
			addHolidayOverlapRange1ErrorMessage();
			return;
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (subHolidayRangeAm || subHolidayRangePm) {
					// 前半休・後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (subHolidayRangeAm) {
					// 前半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (subHolidayRangePm) {
					// 後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (subHolidayRangeAm || subHolidayRangePm) {
					// 前半休・後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate()) && (subHolidayRangeAm || subHolidayRangePm)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休と重複している場合
				addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
				return;
			}
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (substituteRangeAm || substituteRangePm) {
					// 前半休・後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (substituteRangeAm) {
					// 前半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (substituteRangePm) {
					// 後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (substituteRangeAm || substituteRangePm) {
					// 前半休・後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate()) && (substituteRangeAm || substituteRangePm)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休と重複している場合
				addSubstituteErrorMessage(targetDate);
				return;
			}
		}
		int holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
		if (isDraft) {
			// 下書時・申請時の場合
			holidayRange = dto.getHolidayRange();
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (holidayRangePm || subHolidayRangePm || substituteRangePm) {
					// 後半休と重複している場合
					holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
				}
			} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (holidayRangeAm || subHolidayRangeAm || substituteRangeAm) {
					// 前半休と重複している場合
					holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
				}
			}
		} else {
			// 申請日設定時の場合
			if (dto.getRequestStartDate().equals(dto.getRequestEndDate())) {
				// 休暇開始日が休暇終了日の場合
				return;
			}
		}
		// 残業申請チェック
		checkOvertimeWorkRequest(localRequestUtil, targetDate, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替出勤チェック
		checkSubstituteWorkRequest(localRequestUtil, targetDate, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(localRequestUtil, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時差出勤申請チェック
		checkDifferenceRequest(localRequestUtil, holidayRange);
	}
	
	@Override
	@Deprecated
	public void checkHolidayOverlap(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getRequestStartDate().after(dto.getRequestEndDate())) {
			// 開始日が終了日より後の場合
			// 休暇年月日の入力内容が不正です。終了日は開始日よりも後になるよう入力してください。
			addHolidayRequestDateErrorMessage();
			return;
		}
		// 取得開始日
		Date requestDate = dto.getRequestStartDate();
		while (!requestDate.after(dto.getRequestEndDate())) {
			// 全休フラグ確認
			boolean holidayRangeAll = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
			// 午前休・午後休フラグ確認
			boolean holidayRangeAm = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM;
			boolean holidayRangePm = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM;
			// 時間休フラグ確認
			boolean holidayRangeTime = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME;
			// 休暇申請リスト取得
			List<HolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), requestDate);
			// 休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface requestDto : list) {
				// ワークフローの取得
				WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(requestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				// 取下の場合
				if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					continue;
				}
				// ワークフロー番号が同じ場合は同じ申請
				if (dto.getWorkflow() == workflowDto.getWorkflow()) {
					continue;
				}
				// 全休の場合
				if (holidayRangeAll || requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 表示例 休暇内容が重複しています。休暇年月日を選択し直してください。
					addHolidayOverlapRange1ErrorMessage();
					return;
				}
				// 午前休又は午後休の場合
				if (holidayRangeAm || holidayRangePm) {
					// 休暇範囲が既に登録している場合
					if (dto.getHolidayRange() == requestDto.getHolidayRange()) {
						// 表示例 休暇内容が重複しています。休暇年月日または休暇範囲を選択し直してください。
						addHolidayOverlapRange2ErrorMessage();
						return;
					}
					// 時間休の場合
					if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
						// 半休・時間休重複エラーメッセージ。
						addDuplicateTimeHolidayRequestErrorMessage(mospParams.getName("HalfTime"));
						return;
					}
					// 午前休の場合
					if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
						holidayRangeAm = true;
					}
					// 午後休の場合
					if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						holidayRangePm = true;
					}
				}
				// 時間休の場合
				if (holidayRangeTime) {
					// 午前休・午後休の場合
					if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						// 半休・時間休重複エラーメッセージ。
						addDuplicateTimeHolidayRequestErrorMessage(mospParams.getName("HalfTime"));
						return;
					}
					// 時間休の場合
					if (requestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
						// 時間休時間帯重複確認
						if (checkDuplicationTimeZone(requestDto.getStartTime(), requestDto.getEndTime(),
								dto.getStartTime(), dto.getEndTime())) {
							// エラーメッセージ設定
							addOthersRequestErrorMessage(requestDate, mospParams.getName("HolidayTime"));
							return;
						}
					}
				}
			}
			// 各種申請情報取得
			requestUtil.setRequests(dto.getPersonalId(), requestDate);
			// 代休申請情報リストの休暇範囲を確認
			int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
			// 時間休で代休が有った場合
			if (holidayRangeTime && subHolidayRange > 0) {
				addOthersRequestErrorMessage(requestDate, mospParams.getName("HolidayTime"));
				return;
			}
			// 午前休又は午後休の場合
			if (holidayRangeAm || holidayRangePm) {
				// 全休フラグ確認
				boolean allHoliday = (holidayRangeAm && holidayRangePm)
						|| (holidayRangeAm && subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayRangePm && subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM)
						|| (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM && subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM);
				// 全休+残業申請がある場合
				if (allHoliday && !requestUtil.getOverTimeList(false).isEmpty()) {
					addOthersRequestErrorMessage(requestDate, mospParams.getName("OvertimeWork"));
					return;
				}
//				if (allHoliday && requestUtil.getDifferenceDto(false) != null) {
//					// 時差出勤申請がある場合
//					StringBuffer sb = new StringBuffer();
//					sb.append(mospParams.getName("TimeDifference"));
//					sb.append(mospParams.getName("GoingWork"));
//					addOthersRequestErrorMessage(requestDate, sb.toString());
//					return;
//				}
			}
			// 1日加算
			requestDate = addDay(requestDate, 1);
		}
	}
	
	/**
	 * 時短時間との重複チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @param workTypeCode 勤務形態コード
	 * @param date 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkShortTime(HolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil,
			String workTypeCode, Date date) throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認されている場合
			return;
		}
		// 時差出勤申請が承認されていない場合
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, date);
		if (workTypeEntity == null) {
			return;
		}
		boolean isShort1TimeSet = workTypeEntity.isShort1TimeSet();
		Date short1StartTime = null;
		Date short1EndTime = null;
		if (isShort1TimeSet) {
			// 時短時間1が設定されている場合
			short1StartTime = getTime(workTypeEntity.getShort1StartTime(), date);
			short1EndTime = getTime(workTypeEntity.getShort1EndTime(), date);
		}
		boolean isShort2TimeSet = workTypeEntity.isShort2TimeSet();
		Date short2StartTime = null;
		Date short2EndTime = null;
		if (isShort2TimeSet) {
			// 時短時間2が設定されている場合
			short2StartTime = getTime(workTypeEntity.getShort2StartTime(), date);
			short2EndTime = getTime(workTypeEntity.getShort2EndTime(), date);
		}
		if (!isShort1TimeSet && !isShort2TimeSet) {
			// 時短時間が設定されていない場合
			return;
		}
		// 時短時間が設定されている場合
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休でない場合
			return;
		}
		// 時間休である場合
		if (isShort1TimeSet
				&& checkDuplicationTimeZone(dto.getStartTime(), dto.getEndTime(), short1StartTime, short1EndTime)) {
			// 時短時間1と重複している場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SHORT_TIME_DUPLICATION_CHECK,
					DateUtility.getStringTime(short1StartTime), DateUtility.getStringTime(short1EndTime));
			return;
		}
		if (isShort2TimeSet
				&& checkDuplicationTimeZone(dto.getStartTime(), dto.getEndTime(), short2StartTime, short2EndTime)) {
			// 時短時間2と重複している場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SHORT_TIME_DUPLICATION_CHECK,
					DateUtility.getStringTime(short2StartTime), DateUtility.getStringTime(short2EndTime));
		}
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
	@Deprecated
	public void checkRequest(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getRequestStartDate().after(dto.getRequestEndDate())) {
			// 休暇申請の重複チェックでメッセージを追加するため、ここのメッセージ設定は無し。
			return;
		}
		// 時間休確認フラグ
		boolean isHolidayRangeTime = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME;
		// 個人ID取得
		String personalId = dto.getPersonalId();
		// 申請開始日取得
		Date requestDate = dto.getRequestStartDate();
		while (!requestDate.after(dto.getRequestEndDate())) {
			// 各種申請情報取得
			requestUtil.setRequests(personalId, requestDate);
			// 勤務形態コードを取得
			String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, requestDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// カレンダの勤務形態が法定休日又は所定休日の場合
			if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
					|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
				// 時間休の場合
				if (isHolidayRangeTime) {
					// （日付）は休日出勤が申請されています。休暇年月日を選択し直してください。
					addOthersRequestErrorMessage(requestDate,
							mospParams.getName("SubstituteAbbr") + mospParams.getName("GoingWorkAbbr"));
					return;
				}
				// 下書、取下でない休日出勤申請情報取得
				WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(false);
				// 休日出勤申請の場合
				if (workOnHolidayRequestDto != null
						&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// （日付）は休日出勤が申請されています。休暇年月日を選択し直してください。
					addOthersRequestErrorMessage(requestDate, mospParams.getName("WorkingHoliday"));
					return;
				}
				// 承認済かつ振替出勤申請の休日出勤申請情報がない場合
				if (requestUtil.getWorkOnHolidayDto(true) == null
						&& (requestDate.equals(dto.getRequestStartDate()) || requestDate
							.equals(dto.getRequestEndDate()))) {
					// 法定休日又は所定休日の場合
					// 休暇開始日又は休暇終了日の場合はエラーとする
					addHolidayTargetWorkDateHolidayErrorMessage(requestDate);
					return;
				}
			}
			// 振替休日
			if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 申請対象日に既に別の申請が行われている場合、エラーメッセージを追加
				addOthersRequestErrorMessage(requestDate,
						mospParams.getName("Transfer") + mospParams.getName("GoingWork"));
				return;
			}
			// 代休申請
			int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
			// 時間休で代休、全休、午前休午後休、申請が全休かつ全休、午前休、午後休
			if (isHolidayRangeTime
					&& subHolidayRange != 0
					|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
					|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM
					|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL && (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
							|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM || subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							+ TimeConst.CODE_HOLIDAY_RANGE_PM))
					|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM && subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM)
					|| (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM && subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM)) {
				// 申請対象日に既に別の申請が行われている場合、エラーメッセージを追加
				addOthersRequestErrorMessage(requestDate, mospParams.getName("CompensatoryHoliday"));
				return;
			}
			// 残業申請
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
					&& !requestUtil.getOverTimeList(false).isEmpty()) {
				// 表示例 <日付>は既に他の申請が行われています。休暇年月日を選択し直してください。
				addOthersRequestErrorMessage(requestDate, mospParams.getName("OvertimeWork"));
				return;
			}
			// 勤務形態変更申請
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
					&& requestUtil.getWorkTypeChangeDto(false) != null) {
				// 全休の場合
				addOthersRequestErrorMessage(requestDate, mospParams.getName("Work", "Form", "Change"));
				return;
			}
			// 時差出勤申請
			if ((dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL || dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)
					&& requestUtil.getDifferenceDto(false) != null) {
				// 全休又は午前休の場合
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("TimeDifference"));
				sb.append(mospParams.getName("GoingWork"));
				addOthersRequestErrorMessage(requestDate, sb.toString());
				return;
			}
			// 1日加算
			requestDate = addDay(requestDate, 1);
		}
	}
	
	@Override
	@Deprecated
	public void checkRequestForSetActivationDate(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getRequestStartDate().after(dto.getRequestEndDate())) {
			// 休暇申請の重複チェックでメッセージを追加するため、ここのメッセージ設定は無し。
			return;
		}
		boolean isTerm = !dto.getRequestStartDate().equals(dto.getRequestEndDate());
		List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		for (Date date : dateList) {
			// 各種申請情報取得
			requestUtil.setRequests(dto.getPersonalId(), date);
			// 勤務形態コードを取得
			String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), date);
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
					// （日付）は休日出勤が申請されています。休暇年月日を選択し直してください。
					addOthersRequestErrorMessage(date, mospParams.getName("WorkingHoliday"));
					return;
				}
				// 承認済かつ振替出勤申請の休日出勤申請情報がない場合
				if (requestUtil.getWorkOnHolidayDto(true) == null
						&& (!isTerm || (isTerm && (date.equals(dto.getRequestStartDate()) || date.equals(dto
							.getRequestEndDate()))))) {
					// 承認済の振出・休出申請がなく且つ
					// 休暇開始日と休暇終了日とが同じ場合
					// 又は休暇開始日と休暇申請日とが異なる場合の休暇開始日若しくは休暇終了日の場合
					addHolidayTargetWorkDateHolidayErrorMessage(date);
					return;
				}
			}
			// 振替休日
			if (requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				addOthersRequestErrorMessage(date, mospParams.getName("Transfer", "GoingWork"));
				return;
			}
			// 代休申請
			int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
			if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
					|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM
					|| (isTerm && (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
							|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM || subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							+ TimeConst.CODE_HOLIDAY_RANGE_PM))) {
				// 代休が全休の場合
				// 又は休暇開始日と休暇終了日とが異なる場合において代休申請されている場合
				addOthersRequestErrorMessage(date, mospParams.getName("CompensatoryHoliday"));
				return;
			}
			if (isTerm) {
				// 休暇開始日と休暇終了日とが異なる場合
				// 残業申請
				if (!requestUtil.getOverTimeList(false).isEmpty()) {
					// 残業申請されている場合
					addOthersRequestErrorMessage(date, mospParams.getName("OvertimeWork"));
					return;
				}
				// 時差出勤申請
				if (requestUtil.getDifferenceDto(false) != null) {
					// 時差出勤申請されている場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("TimeDifference"));
					sb.append(mospParams.getName("GoingWork"));
					addOthersRequestErrorMessage(date, sb.toString());
					return;
				}
			}
		}
	}
	
	/**
	 * 残業申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param date 対象日
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeWorkRequest(RequestUtilBeanInterface localRequestUtil, Date date, int holidayRange)
			throws MospException {
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
		addOthersRequestErrorMessage(date, mospParams.getName("OvertimeWork"));
	}
	
	/**
	 * 振替休日申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param date 対象日
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubstituteWorkRequest(RequestUtilBeanInterface localRequestUtil, Date date, int holidayRange)
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
//		addHolidayTargetWorkDateHolidayErrorMessage(date);
		// 半日振替出勤日は休暇申請を不可とする
		addOthersRequestErrorMessage(date, mospParams.getName("HalfDay", "Transfer", "GoingWork"));
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
		addHolidayTargetWorkDateAttendanceRequestErrorMessage(attendanceDto.getWorkDate());
	}
	
	@Override
	@Deprecated
	public void checkAttendance(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getRequestStartDate().after(dto.getRequestEndDate())) {
			// 開始日が終了日より後の場合
			return;
		}
		Date requestDate = dto.getRequestStartDate();
		while (!requestDate.after(dto.getRequestEndDate())) {
			Date targetDate = requestDate;
			// 1日加算
			requestDate = addDay(requestDate, 1);
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), targetDate, 1);
			if (attendanceDto == null) {
				continue;
			}
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 取下の場合
				continue;
			}
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
				// 下書の場合
				continue;
			}
			if (workflowDto.getWorkflowStage() == 0
					&& PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
				// 1次戻の場合
				continue;
			}
			// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
			addHolidayTargetWorkDateAttendanceRequestErrorMessage(targetDate);
			return;
		}
	}
	
	/**
	 * 申請時の入力チェック。休暇申請の項目の必須チェック。<br>
	 * <p>
	 * 必須の項目が入力されていない場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequired(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
				|| dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER
				|| dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇・その他休暇・欠勤の場合
			HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(dto.getHolidayType2(),
					dto.getRequestStartDate(), dto.getHolidayType1());
			if (holidayDto == null) {
				return;
			}
			if (holidayDto.getReasonType() == TimeConst.CODE_REASON_TYPE_NOT_REQUIRED) {
				// 任意の場合
				return;
			}
		}
		// 休暇理由がない場合
		if (dto.getRequestReason().isEmpty()) {
			addHolidayRequestReasonErrorMessage();
		}
	}
	
	@Override
	public void checkLimitDate(HolidayRequestDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 休暇種別取得
		int holidayType = dto.getHolidayType1();
		// 特別休暇でなく且つその他休暇でない場合
		if (holidayType != TimeConst.CODE_HOLIDAYTYPE_SPECIAL && holidayType != TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			return;
		}
		// 休暇情報取得
		HolidayDataDtoInterface holidayDataDto = holidayDataDao.findForKey(dto.getPersonalId(),
				dto.getHolidayAcquisitionDate(), dto.getHolidayType2(), dto.getHolidayType1());
		if (holidayDataDto == null) {
			return;
		}
		// 取得期限が終了日より前の場合
		if (holidayDataDto.getHolidayLimitDate().before(dto.getRequestEndDate())) {
			// 取得期限を過ぎた年月日で申請がされているエラーメッセージ
			addHolidayLimitDateErrorMessage();
		}
	}
	
	@Override
	public void deleteAttendance(HolidayRequestDtoInterface dto) throws MospException {
		int range = dto.getHolidayRange();
		boolean holidayAm = range == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayPm = range == TimeConst.CODE_HOLIDAY_RANGE_PM;
		List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		for (Date date : dateList) {
			RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
			localRequestUtil.setRequests(dto.getPersonalId(), date);
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = localRequestUtil.getWorkOnHolidayDto(false);
			if (workOnHolidayRequestDto != null
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休出である場合
				continue;
			}
			// 休出でない場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				attendanceRegist.delete(dto.getPersonalId(), date);
				continue;
			} else if (holidayAm || holidayPm) {
				// 半休の場合
				// 各種申請情報取得
				requestUtil.setRequests(dto.getPersonalId(), date);
				// 休暇
				List<HolidayRequestDtoInterface> list = requestUtil.getHolidayList(false);
				for (HolidayRequestDtoInterface holidayRequestDto : list) {
					if ((holidayAm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
				// 代休
				List<SubHolidayRequestDtoInterface> subHolidayRequestList = requestUtil.getSubHolidayList(false);
				for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
					if ((holidayAm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void draftAttendance(HolidayRequestDtoInterface dto) throws MospException {
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
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestStartDate(), deleteRest, false, false);
	}
	
	/**
	 * 期間のチェック。<br>
	 * @param dto 対象DTO
	 */
	protected void checkPeriod(HolidayRequestDtoInterface dto) {
		if (dto.getRequestStartDate().after(dto.getRequestEndDate())) {
			// 開始日が終了日より後の場合
			// 休暇年月日の入力内容が不正です。終了日は開始日よりも後になるよう入力してください。
			addHolidayRequestDateErrorMessage();
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(HolidayRequestDtoInterface dto) throws MospException {
		if (!entranceReference.isEntered(dto.getPersonalId(), dto.getRequestStartDate())) {
			// 休暇開始日時点で入社していない場合
			addNotEntranceErrorMessage();
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(HolidayRequestDtoInterface dto) throws MospException {
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestEndDate())) {
			// 休暇終了日時点で退職している場合
			addEmployeeRetiredMessage();
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @param list リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(HolidayRequestDtoInterface dto, List<Date> list) throws MospException {
		checkSuspended(dto.getPersonalId(), list);
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param personalId 個人ID
	 * @param list リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(String personalId, List<Date> list) throws MospException {
		for (Date date : list) {
			checkSuspended(personalId, date);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param personalId 個人ID
	 * @param date 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(String personalId, Date date) throws MospException {
		if (suspensionReference.isSuspended(personalId, date)) {
			// 休職している場合
			addEmployeeSuspendedMessage();
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(HolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestStartDate(), getNameVacationDay());
	}
	
	/**
	 * 申請時の入力チェック。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkHolidayType(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇種別取得
		int holidayType1 = dto.getHolidayType1();
		// 休暇範囲取得
		int holidayRange = dto.getHolidayRange();
		// 欠勤フラグ
		boolean isAbsence = holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE;
		// 有休休暇の場合
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 有休休暇の場合
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
				// 申請用有給休暇申請可能数リストを取得
				List<PaidHolidayDataDtoInterface> list = paidHolidayInfoReference
					.getPaidHolidayPossibleRequestForRequestList(dto.getPersonalId(), dto.getRequestStartDate());
				// 申請用有給休暇申請可能数リスト毎に処理
				for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
					if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
							|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						// 全休・前半休・後半休
						// 申請した日数が保有日数以下の場合
						if (paidHolidayDataDto.getHoldDay() >= dto.getUseDay()
								&& paidHolidayDataDto.getAcquisitionDate().equals(dto.getHolidayAcquisitionDate())) {
							// 有休取得日と休暇取得日が同じ場合
							return;
						}
						if (paidHolidayDataDto.getHoldDay() == 0) {
							continue;
						}
						addPaidLeaveForPreviousFiscalYearErrorMessage();
						return;
					} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
						// 時間休
						if (paidHolidayDataDto.getHoldHour() >= dto.getUseHour()
								&& paidHolidayDataDto.getAcquisitionDate().equals(dto.getHolidayAcquisitionDate())) {
							// 有休取得日と休暇取得日が同じ場合
							return;
						} else if (paidHolidayDataDto.getHoldDay() >= 1
								&& paidHolidayDataDto.getAcquisitionDate().equals(dto.getHolidayAcquisitionDate())) {
							// 有休取得日と休暇取得日が同じ場合
							return;
						}
					}
				}
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					addHolidayNumDaysExcessErrorMessage(mospParams.getName("Salaried", "Vacation"),
							mospParams.getName("Day"));
				} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					addHolidayNumDaysExcessErrorMessage(mospParams.getName("Salaried", "Vacation"),
							mospParams.getName("Time"));
				}
				return;
			}
			// ストック休暇の場合
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(dto.getHolidayType2())) {
				// 申請用ストック休暇申請可能情報リストを取得
				List<StockHolidayDataDtoInterface> list = stockHolidayInfoReference
					.getStockHolidayPossibleRequestForRequest(dto.getPersonalId(), dto.getRequestStartDate());
				// 申請用ストック休暇申請可能情報リスト毎に処理
				for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
					// 休暇取得日=ストック取得日かつ
					//ストック保有日数+ストック付与日数-ストック廃棄日数-ストック使用日数は休暇取得日が同じかより多い
					if (dto.getHolidayAcquisitionDate().equals(stockHolidayDataDto.getAcquisitionDate())
							&& stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
									- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() >= dto
								.getUseDay()) {
						return;
					}
				}
				addHolidayNumDaysExcessErrorMessage(mospParams.getName("Stock", "Vacation"), mospParams.getName("Day"));
				return;
			}
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER || isAbsence) {
			// 特別休暇・その他休暇・欠勤の場合
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayType2(), dto.getRequestStartDate(),
					dto.getHolidayType1());
			checkHolidayMaster(holidayDto, holidayRange);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			if (isAbsence) {
				// 欠勤の場合
				return;
			}
//			HolidayDataDtoInterface holidayDataDto = holidayDataDao.findForKey(dto.getPersonalId(), dto
//				.getHolidayAcquisitionDate(), dto.getHolidayType2(), dto.getHolidayType1());
			HolidayDataDtoInterface holidayDataDto = holidayInfoReference.getHolidayPossibleRequestForRequest(
					dto.getPersonalId(), dto.getRequestStartDate(), dto.getHolidayType2(), dto.getHolidayType1());
			if (holidayDataDto == null || holidayDataDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON
					|| !holidayDataDto.getActivateDate().equals(dto.getHolidayAcquisitionDate())) {
				// 休暇データが存在しない場合
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			double days = getHolidayRequestDays(dto, holidayDto, holidayDataDto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			if (Double.compare(dto.getUseDay(), days) == 0) {
				return;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName(), mospParams.getName("Day"));
		}
	}
	
	/**
	 * 休暇種別チェック。
	 * @param dto 対象DTO
	 * @param holidayRange 休暇範囲
	 */
	protected void checkHolidayMaster(HolidayDtoInterface dto, int holidayRange) {
		if (dto == null || dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// 休暇種別が存在しない場合
			addHolidayNotExistErrorMessage();
			return;
		}
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| dto.getHalfHolidayRequest() == MospConst.INACTIVATE_FLAG_OFF) {
			// 全休の場合又は半休申請が有効の場合
			return;
		}
		// 半休申請が無効の場合
		addHalfHolidayRequestErrorMessage(dto.getHolidayName());
	}
	
	@Override
	public void checkWorkType(HolidayRequestDtoInterface dto, Date targetDate, String workTypeCode) {
		checkWorkType(dto.getRequestStartDate(), dto.getRequestEndDate(), targetDate, workTypeCode);
	}
	
	@Override
	public void checkWorkType(Date startDate, Date endDate, Date targetDate, String workTypeCode) {
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addHolidayTargetWorkDateHolidayErrorMessage(targetDate);
			return;
		} else if (isLegalDaysOff(workTypeCode) || isPrescribedDaysOff(workTypeCode)
				|| isWorkOnLegalDaysOff(workTypeCode) || isWorkOnPrescribedDaysOff(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			if (targetDate.equals(startDate) || targetDate.equals(endDate)) {
				// 休暇開始日又は休暇終了日の場合
				addHolidayTargetWorkDateHolidayErrorMessage(targetDate);
			}
		}
	}
	
	@Override
	public boolean isLegalDaysOff(String workTypeCode) {
		return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	@Override
	public boolean isPrescribedDaysOff(String workTypeCode) {
		return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	@Override
	public boolean isWorkOnLegalDaysOff(String workTypeCode) {
		return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	@Override
	public boolean isWorkOnPrescribedDaysOff(String workTypeCode) {
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(personalId, targetDate);
		return getScheduledWorkTypeCode(personalId, targetDate, localRequestUtil);
	}
	
	/**
	 * カレンダ勤務形態コードを取得する。
	 * @param dto 対象DTO
	 * @param targetDate 対象日
	 * @param localRequestUtil 申請ユーティリティ
	 * @return カレンダ勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(HolidayRequestDtoInterface dto, Date targetDate,
			RequestUtilBeanInterface localRequestUtil) throws MospException {
		return getScheduledWorkTypeCode(dto.getPersonalId(), targetDate, localRequestUtil);
	}
	
	/**
	 * カレンダ勤務形態コードを取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param localRequestUtil 申請ユーティリティ
	 * @return カレンダ勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(String personalId, Date targetDate,
			RequestUtilBeanInterface localRequestUtil) throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認済である場合
			return differenceRequestDto.getDifferenceType();
		}
		Date date = targetDate;
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
			ApplicationDtoInterface applicationDto = applicationRefer.findForPerson(personalId, date);
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
	 * 休暇申請日数を取得する。<br>
	 * @param dto 対象DTO
	 * @param holidayDto 休暇マスタDTO
	 * @param holidayDataDto 休暇データDTO
	 * @return 休暇申請日数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected double getHolidayRequestDays(HolidayRequestDtoInterface dto, HolidayDtoInterface holidayDto,
			HolidayDataDtoInterface holidayDataDto) throws MospException {
		return getHolidayRequestDays(dto.getPersonalId(), dto.getRequestStartDate(), dto.getRequestEndDate(),
				dto.getHolidayRange(), holidayDto, holidayDataDto);
	}
	
	@Override
	public double getHolidayRequestDays(String personalId, Date startDate, Date endDate, int holidayRange,
			HolidayDtoInterface holidayDto, HolidayDataDtoInterface holidayDataDto) throws MospException {
		int count = 0;
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		for (Date date : dateList) {
			// 勤務形態コード取得
			String workTypeCode = getScheduledWorkTypeCode(personalId, date);
			// 勤務形態チェック
			checkWorkType(startDate, endDate, date, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				return 0;
			}
			if (isLegalDaysOff(workTypeCode) || isPrescribedDaysOff(workTypeCode) || isWorkOnLegalDaysOff(workTypeCode)
					|| isWorkOnPrescribedDaysOff(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			count++;
		}
		double holidayRequestDays = 0;
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			holidayRequestDays = count;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			holidayRequestDays = count * TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
			return 0;
		}
		if (holidayDto.getHolidayType() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 欠勤の場合
			return holidayRequestDays;
		}
		if (holidayDto.getContinuousAcquisition() == 0) {
			// 連続取得が必須の場合
			if (holidayRequestDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay();
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName(), mospParams.getName("Day"));
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 1) {
			// 連続取得が警告の場合
			if (holidayRequestDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayRequestDays;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName(), mospParams.getName("Day"));
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 2) {
			// 連続取得が不要の場合
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				return holidayRequestDays;
			}
			// 付与日数が無制限でない場合
			if (holidayRequestDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayRequestDays;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName(), mospParams.getName("Day"));
			return 0;
		}
		return 0;
	}
	
	/**
	 * 所定労働時間を時間単位で取得する。<br>
	 * 時間未満は切り捨てる。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected int getPrescribedWorkHour(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return 0;
		}
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestStartDate());
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤が承認済の場合
			DifferenceRequestReferenceBeanInterface differenceRequestReference = (DifferenceRequestReferenceBeanInterface)createBean(
					DifferenceRequestReferenceBeanInterface.class, dto.getRequestStartDate());
			return differenceRequestReference.getDifferenceWorkTime(false, false) / TimeConst.CODE_DEFINITION_HOUR;
		}
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			attendanceDto = requestUtil.getFirstRevertedAttendance();
		}
		if (attendanceDto == null) {
			attendanceDto = requestUtil.getDraftAttendance();
		}
		String workTypeCode = "";
		if (attendanceDto == null) {
			Date targetDate = dto.getRequestStartDate();
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
			if (workOnHolidayRequestDto != null
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				// 振出・休出申請が承認済且つ振り替える場合
				List<SubstituteDtoInterface> list = substituteDao
					.findForWorkflow(workOnHolidayRequestDto.getWorkflow());
				for (SubstituteDtoInterface substituteDto : list) {
					targetDate = substituteDto.getSubstituteDate();
					break;
				}
			}
			workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), targetDate);
		} else {
			workTypeCode = attendanceDto.getWorkTypeCode();
		}
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			return 0;
		}
		WorkTypeItemDtoInterface workTypeItemDto = workTypeItemDao.findForInfo(workTypeCode, dto.getRequestStartDate(),
				TimeConst.CODE_WORKTIME);
		if (workTypeItemDto == null) {
			return 0;
		}
		return DateUtility.getHour(workTypeItemDto.getWorkTypeItemValue(), DateUtility.getDefaultTime());
	}
	
	/**
	 * 休暇年月日名称を取得する。
	 * @return 休暇年月日名称
	 */
	protected String getNameVacationDay() {
		return mospParams.getName("Vacation", "Year", "Month", "Day");
	}
	
	/**
	 * 半休申請が無効の場合のエラーメッセージを設定する。<br>
	 * @param holidayName 休暇名称
	 */
	protected void addHalfHolidayRequestErrorMessage(String holidayName) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("HalfTime"));
		sb.append(mospParams.getName("Application"));
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, new String[]{ holidayName, sb.toString(),
			mospParams.getName("Vacation", "Classification") });
	}
	
	/**
	 * 時間休と他申請が同じ日に申請される場合のエラーメッセージを設定する。<br>
	 * <休暇名>と時間休は同日に申請出来ません。休暇年月日または休暇範囲を選択し直してください。
	 * @param holidayName <休暇名>
	 */
	protected void addDuplicateTimeHolidayRequestErrorMessage(String holidayName) {
		// <休暇名>と時間休
		String key1 = holidayName + mospParams.getName("And", "HolidayTime");
		// 同日に申請
		String key2 = mospParams.getName("Same", "Day", "In", "Application");
		// 休暇年月日または休暇範囲
		String key3 = mospParams.getName("Vacation", "Year", "Month", "Day", "Or", "Vacation")
				+ mospParams.getName("Range");
		String[] rep = { key1, key2, key3 };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, rep);
	}
	
	/**
	 * 前年度分の有給休暇が0.5日残っている場合のエラーメッセージを設定する。<br>
	 */
	protected void addPaidLeaveForPreviousFiscalYearErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_PAID_HOLIDAY_REQUEST_CHECK);
	}
	
	/**
	 * 振替休日である場合のエラーメッセージを設定する。<br>
	 * @param date 対象日
	 */
	protected void addSubstituteErrorMessage(Date date) {
		addOthersRequestErrorMessage(date, mospParams.getName("Transfer", "Holiday"));
	}
	
}
