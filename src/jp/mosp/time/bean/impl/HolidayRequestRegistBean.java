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
import java.util.Map;

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
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayRequestDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請登録クラス。
 */
public class HolidayRequestRegistBean extends TimeApplicationBean implements HolidayRequestRegistBeanInterface {
	
	/**
	 * 休暇申請DAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface					dao;
	
	/**
	 * 休暇申請参照インターフェース。<br>
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestReference;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	protected AttendanceRegistBeanInterface					attendanceRegist;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface						timeApproval;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface				approvalInfoReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface						requestUtil;
	
	/**
	 * 有給休暇情報参照クラス。
	 */
	protected PaidHolidayInfoReferenceBeanInterface			paidHolidayInfoReference;
	
	/**
	 * ストック休暇情報参照クラス。
	 */
	protected StockHolidayInfoReferenceBeanInterface		stockHolidayInfoReference;
	
	/**
	 * 休暇データDAOクラス。
	 */
	protected HolidayDataDaoInterface						holidayDataDao;
	
	/**
	 * 休暇種別管理参照クラス。
	 */
	protected HolidayReferenceBeanInterface					holidayReference;
	
	/**
	 * 休暇データ参照クラス。
	 */
	protected HolidayInfoReferenceBeanInterface				holidayInfoReference;
	
	/**
	 * 勤務形態項目管理DAOクラス。
	 */
	protected WorkTypeItemDaoInterface						workTypeItemDao;
	
	/**
	 * 振替休日データDAO
	 */
	protected SubstituteDaoInterface						substituteDao;
	
	/**
	 * 勤怠情報参照インターフェース。<br>
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 勤務形態項目参照用インターフェース。<br>
	 */
	protected WorkTypeItemReferenceBeanInterface			workTypeItemReference;
	
	/**
	 * 振替休日データ参照インターフェース。<br>
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * 勤務形態変更申請情報参照インターフェース
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeReference;
	
	/**
	 * 時差出勤申請参照インターフェース
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceRequestReference;
	
	
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
		// DAOを準備
		dao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		// Beanを準備
		holidayRequestReference = (HolidayRequestReferenceBeanInterface)createBean(
				HolidayRequestReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(
				WorkflowCommentRegistBeanInterface.class);
		attendanceRegist = (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
		holidayDataDao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(
				ApprovalInfoReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		paidHolidayInfoReference = (PaidHolidayInfoReferenceBeanInterface)createBean(
				PaidHolidayInfoReferenceBeanInterface.class);
		stockHolidayInfoReference = (StockHolidayInfoReferenceBeanInterface)createBean(
				StockHolidayInfoReferenceBeanInterface.class);
		holidayReference = (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
		holidayInfoReference = (HolidayInfoReferenceBeanInterface)createBean(HolidayInfoReferenceBeanInterface.class);
		workTypeItemDao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		workTypeItemReference = (WorkTypeItemReferenceBeanInterface)createBean(
				WorkTypeItemReferenceBeanInterface.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		workTypeChangeReference = (WorkTypeChangeRequestReferenceBeanInterface)createBean(
				WorkTypeChangeRequestReferenceBeanInterface.class);
		differenceRequestReference = (DifferenceRequestReferenceBeanInterface)createBean(
				DifferenceRequestReferenceBeanInterface.class);
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
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto);
		// 休暇が残っているかを確認(下書)
		checkHolidayRemains(dto, false);
	}
	
	@Override
	public void checkAppli(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇が残っているかを確認(申請)
		checkHolidayRemains(dto, false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇申請の項目の必須チェック。
		checkRequired(dto);
		// 勤務形態変更申請チェック
		checkWorkTypeChange(dto);
		// 時差出勤申請チェック
		checkDifference(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時間休申請範囲チェック
		checkTimeHoliday(dto);
	}
	
	@Override
	public void checkApproval(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇が残っているかを確認(承認処理)
		checkHolidayRemains(dto, true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChange(dto);
		// 時差出勤申請チェック
		checkDifference(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時間休申請範囲チェック
		checkTimeHoliday(dto);
	}
	
	/**
	 * 休暇申請が申請として妥当であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForRequest(HolidayRequestDtoInterface dto) throws MospException {
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
		// 有給休暇設定チェック
		checkPaidHolidayMaster(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時間休の限度チェック
		checkTimeHolidayLimit(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇種別チェック
		checkHolidayMaster(dto);
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
	 * 時間休の限度チェックを行う。<br>
	 * 有給休暇(ストック休暇は時間休は申請できない)の時間休の場合のみ、
	 * 限度時間の確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void checkTimeHolidayLimit(HolidayRequestDtoInterface dto) throws MospException {
		// 有給休暇でない場合
		if (TimeUtility.isPaidHolidayRequest(dto) == false) {
			// 処理終了(時間休の限度を確認する必要がないため)
			return;
		}
		// 時間休でない場合
		if (TimeUtility.isHourlyHoliday(dto) == false) {
			// 処理終了(時間休の限度を確認する必要がないため)
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
		Map<String, Integer> timeHoliday = holidayRequestReference.getTimeHolidayStatusTimesMap(personalId, requestDate,
				dto);
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
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), date);
		// 勤務形態コード取得
		String workTypeCode = getScheduledWorkTypeCode(dto, date, localRequestUtil);
		// 勤務形態チェック
		checkWorkType(dto, date, workTypeCode);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		// 休暇申請・代休申請・振替休日の重複チェック
		checkDuplicate(dto, date, localRequestUtil, false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(dto, date);
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
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), date);
		// 勤務形態コード取得
		String workTypeCode = getScheduledWorkTypeCode(dto, date, localRequestUtil);
		// 勤務形態チェック
		checkWorkType(dto, date, workTypeCode);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
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
		// 勤怠申請チェック
		checkAttendance(dto, date);
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
						// 期間が重複する場合のメッセージを追加
						addDuplicateTermMessage();
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
					TimeMessageUtility.addErrorHalfAndHourlyHoliday(mospParams);
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
					TimeMessageUtility.addErrorHalfAndHourlyHoliday(mospParams);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (holidayRangeAm || holidayRangePm) {
					// 前半休・後半休と重複している場合
					TimeMessageUtility.addErrorHalfAndHourlyHoliday(mospParams);
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
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (subHolidayRangeAm || subHolidayRangePm)) {
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
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (substituteRangeAm || substituteRangePm)) {
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
			// [全日]振替出勤（勤務形態変更なし）の場合
			return;
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// [全日]振替出勤（勤務形態変更あり）の場合
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
	 * @param dto 休暇申請DTO
	 * @param date 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkAttendance(HolidayRequestDtoInterface dto, Date date) throws MospException {
		// 勤怠申請情報取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(dto.getPersonalId(), date, 1);
		if (attendanceDto == null) {
			// 勤怠申請がない場合
			return;
		}
		// 勤怠申請のワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (WorkflowUtility.isWithDrawn(workflowDto)) {
			// 勤怠が取下の場合
			return;
		}
		if (WorkflowUtility.isDraft(workflowDto)) {
			// 勤怠が下書の場合
			return;
		}
		if (WorkflowUtility.isFirstReverted(workflowDto)) {
			// 勤怠が1次戻の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addHolidayTargetWorkDateAttendanceRequestErrorMessage(date);
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
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 有給休暇・ストック休暇の場合
			if (!holidayRequestReference.isPaidHolidayReasonRequired()) {
				// 任意の場合
				return;
			}
		} else if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
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
	
	/**
	 * 休暇が残っているかを確認する。<br>
	 * <br>
	 * 特別休暇及びその他休暇について、確認する。<br>
	 * <br>
	 * @param dto        休暇申請情報
	 * @param isApproval 承認処理フラグ(true：承認処理に対する確認、false：下書及び申請)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRemains(HolidayRequestDtoInterface dto, boolean isApproval) throws MospException {
		// 休暇申請情報及び休暇情報から情報を取得
		String personalId = dto.getPersonalId();
		Date startDate = dto.getRequestStartDate();
		Date endDate = dto.getRequestEndDate();
		int holidayType = dto.getHolidayType1();
		String holidayCode = dto.getHolidayType2();
		int holidayRange = dto.getHolidayRange();
		// 特別休暇・その他休暇でない場合
		if (holidayType != TimeConst.CODE_HOLIDAYTYPE_SPECIAL && holidayType != TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// 処理なし
			return;
		}
		// 休暇種別情報を取得
		HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(holidayCode, startDate, holidayType);
		// 休暇種別情報が存在しないか無制限の場合(ここでは有効/無効は確認しない)
		if (holidayDto == null || holidayDto.getNoLimit() == getInteger(MospConst.CHECKBOX_ON)) {
			// 処理終了(休暇が残っているかを確認する必要がないため)
			return;
		}
		// 対象個人ID及び申請開始日で設定有給休暇情報を取得
		setPaidHolidaySettings(personalId, startDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 1日の時間数を取得
		int hoursPerDay = paidHolidayDto.getTimeAcquisitionLimitTimes();
		// 休暇情報を取得
		HolidayDataDtoInterface holidayData = holidayInfoReference.getHolidayPossibleRequestForRequest(personalId,
				startDate, holidayCode, holidayType);
		// 日数を取得
		double days = getHolidayRequestDays(personalId, startDate, endDate, holidayRange);
		// 時間数を取得
		int hours = TimeUtility.isHourlyHoliday(dto) ? 1 : 0;
		// 承認処理に対する確認である場合
		if (isApproval) {
			// 日数及び時間数を0に設定(休暇情報に申請済の数が含まれるため)
			days = 0D;
			hours = 0;
		}
		// 休暇が残っていない場合
		if (TimeUtility.isHolidayRemain(holidayData, days, hours, hoursPerDay) == false) {
			// エラーメッセージを設定
			StringBuilder sb = new StringBuilder();
			sb.append(holidayDto.getHolidayAbbr());
			sb.append(TimeNamingUtility.getVacation(mospParams));
			addHolidayNumDaysExcessErrorMessage(sb.toString(), mospParams.getName("Time"));
		}
	}
	
	@Override
	public void deleteAttendance(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請の休暇範囲取得
		int range = dto.getHolidayRange();
		boolean holidayAm = range == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayPm = range == TimeConst.CODE_HOLIDAY_RANGE_PM;
		// 休暇申請の対象日付リストを取得
		List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休暇申請対象日毎に処理
		for (Date date : dateList) {
			RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
					RequestUtilBeanInterface.class);
			// 休暇申請対象日の各種申請情報取得
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
				// 半休申請の場合
				// 休暇申請情報リスト取得
				List<HolidayRequestDtoInterface> list = localRequestUtil.getHolidayList(false);
				// 休暇申請情報ごとに処理
				for (HolidayRequestDtoInterface holidayRequestDto : list) {
					if ((holidayAm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後休暇申請となる場合勤怠を削除
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
				// 代休申請情報リスト取得
				List<SubHolidayRequestDtoInterface> subHolidayRequestList = localRequestUtil.getSubHolidayList(false);
				// 代休申請情報毎に処理
				for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
					if ((holidayAm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm
									&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後代休申請、または午前代休申請+午後休暇申請となる場合勤怠を削除
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
				// 振替休日申請情報リスト取得
				List<SubstituteDtoInterface> substituteList = localRequestUtil.getSubstituteList(false);
				// 振替休日申請情報毎に処理
				for (SubstituteDtoInterface substituteDto : substituteList) {
					if ((holidayAm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後振替休日、または午前振替休日+午後休暇申請となる場合勤怠を削除
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
		if (!isEntered(dto.getPersonalId(), dto.getRequestStartDate())) {
			// 休暇開始日時点で入社していない場合
			PlatformMessageUtility.addErrorEmployeeNotJoin(mospParams);
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
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestStartDate(),
				TimeNamingUtility.holidayDate(mospParams));
	}
	
	/**
	 * 有給休暇設定チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPaidHolidayMaster(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getHolidayType1() != TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			return;
		}
		if (!Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
			// 有給休暇でない場合
			return;
		}
		// 有給休暇である場合
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休でない場合
			return;
		}
		// 時間休である場合
		setPaidHolidaySettings(dto.getPersonalId(), dto.getRequestStartDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (paidHolidayDto.getTimelyPaidHolidayFlag() != 0) {
			// 時間単位が有効でない場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_UNSETTING,
					mospParams.getName("Time", "Unit", "Acquisition"), mospParams.getName("HolidayTime"),
					mospParams.getName("PaidVacation", "Set"));
			return;
		}
		// 時間単位が有効である場合
		if (DateUtility.getMinute(dto.getStartTime()) % paidHolidayDto.getAppliTimeInterval() == 0) {
			return;
		}
		// 有給休暇設定の申請時間間隔と異なる場合
		addStartTimeErrorMessage();
	}
	
	/**
	 * 特別休暇・その他休暇・欠勤の休暇種別チェックを行う。<br>
	 * 特別・その他・欠勤の確認を行う。<br>
	 * 時間単位取得の確認を行う。<br>
	 * 半休申請の確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayMaster(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇区分1取得
		int holidayType1 = dto.getHolidayType1();
		// 特別休暇・その他休暇・欠勤でない場合
		if (holidayType1 != TimeConst.CODE_HOLIDAYTYPE_SPECIAL && holidayType1 != TimeConst.CODE_HOLIDAYTYPE_OTHER
				&& holidayType1 != TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 処理なし
			return;
		}
		// 休暇種別情報取得
		HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(dto.getHolidayType2(),
				dto.getRequestStartDate(), holidayType1);
		// 休暇が無効の場合
		if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// エラーメッセージ追加
			addHolidayNotExistErrorMessage();
			return;
		}
		// 時間休の場合
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間単位が無効の場合
			if (PlatformUtility.isInactivate(holidayDto.getTimelyHolidayFlag())) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorHourlyHolidayInvalid(mospParams, holidayDto.getHolidayName());
				// 処理終了
				return;
			}
		}
		// 半休申請チェック
		checkHalfHoliday(dto, holidayDto);
	}
	
	/**
	 * 半休申請チェック。<br>
	 * @param dto 対象DTO
	 * @param holidayDto 休暇種別DTO
	 * @throws MospException インスタンスの生成或いに失敗した場合
	 */
	protected void checkHalfHoliday(HolidayRequestDtoInterface dto, HolidayDtoInterface holidayDto)
			throws MospException {
		int holidayType1 = dto.getHolidayType1();
		if (holidayType1 != TimeConst.CODE_HOLIDAYTYPE_SPECIAL && holidayType1 != TimeConst.CODE_HOLIDAYTYPE_OTHER
				&& holidayType1 != TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇・その他休暇・欠勤でない場合
			return;
		}
		// 特別休暇・その他休暇・欠勤である場合
		int holidayRange = dto.getHolidayRange();
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_AM && holidayRange != TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休・午後休でない場合
			return;
		}
		// 午前休・午後休である場合
		if (holidayDto.getHalfHolidayRequest() == MospConst.INACTIVATE_FLAG_OFF) {
			// 半休申請が有効の場合
			return;
		}
		// 半休申請が無効の場合
		TimeMessageUtility.addErrorHalfHolidayInvalid(mospParams, holidayDto.getHolidayName());
	}
	
	@SuppressWarnings("unused")
	@Override
	public void checkWorkType(HolidayRequestDtoInterface dto, Date targetDate, String workTypeCode)
			throws MospException {
		checkWorkType(dto.getRequestStartDate(), dto.getRequestEndDate(), targetDate, workTypeCode);
	}
	
	@Override
	public void checkWorkType(Date startDate, Date endDate, Date targetDate, String workTypeCode) {
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addHolidayTargetWorkDateHolidayErrorMessage(targetDate);
			return;
		} else if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			if (targetDate.equals(startDate) || targetDate.equals(endDate)) {
				// 休暇開始日又は休暇終了日の場合
				addHolidayTargetWorkDateHolidayErrorMessage(targetDate);
			}
		}
	}
	
	@Override
	public void setHolidayRequest(HolidayRequestDtoInterface dto) throws MospException {
		int holidayType1 = dto.getHolidayType1();
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 有給休暇・ストック休暇の場合
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
				// 有給休暇の場合
				setPaidLeave(dto);
			} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(dto.getHolidayType2())) {
				// ストック休暇の場合
				setStockLeave(dto);
			}
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇・その他休暇・欠勤の場合
			setLeave(dto);
		}
	}
	
	/**
	 * 休暇申請情報をDTOに設定する。
	 * @param dto 対象DTO
	 * @param holidayAcquisitionDate 休暇取得日
	 * @param useDay 使用日数
	 * @param useHour 使用時間数
	 */
	protected void setHolidayRequest(HolidayRequestDtoInterface dto, Date holidayAcquisitionDate, double useDay,
			int useHour) {
		dto.setHolidayAcquisitionDate(holidayAcquisitionDate);
		dto.setUseDay(useDay);
		dto.setUseHour(useHour);
	}
	
	/**
	 * 有給休暇情報をDTOに設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPaidLeave(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getHolidayType1() != TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			return;
		}
		if (!Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
			// 有給休暇でない場合
			return;
		}
		// 有給休暇である場合
		double useDay = 0;
		int useHour = 0;
		int holidayRange = dto.getHolidayRange();
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			useDay = TimeConst.HOLIDAY_TIMES_ALL;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			useDay = TimeConst.HOLIDAY_TIMES_HALF;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休の場合
			useHour = 1;
		} else {
			mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
			return;
		}
		List<PaidHolidayDataDtoInterface> list = paidHolidayInfoReference
			.getPaidHolidayPossibleRequestForRequestList(dto.getPersonalId(), dto.getRequestStartDate());
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			boolean isHalfPaidLeave = false;
			for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
				double holdDay = paidHolidayDataDto.getHoldDay();
				if (holdDay >= TimeConst.HOLIDAY_TIMES_ALL) {
					// 1日以上の場合
					if (isHalfPaidLeave) {
						addPaidLeaveForPreviousFiscalYearErrorMessage();
						return;
					}
					break;
				} else if (TimeUtility.isHolidayTimesHalf(holdDay)) {
					// 0.5日の場合
					isHalfPaidLeave = true;
				}
			}
		}
		Date holidayAcquisitionDate = null;
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・午前休・午後休の場合
			for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
				if (paidHolidayDataDto.getHoldDay() >= useDay) {
					// 取得日設定
					holidayAcquisitionDate = paidHolidayDataDto.getAcquisitionDate();
					break;
				}
			}
			if (holidayAcquisitionDate == null) {
				addHolidayNumDaysExcessErrorMessage(mospParams.getName("Salaried", "Vacation"),
						mospParams.getName("Day"));
				return;
			}
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休の場合
			for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
				if (paidHolidayDataDto.getHoldHour() >= 1
						|| paidHolidayDataDto.getHoldDay() >= TimeConst.HOLIDAY_TIMES_ALL) {
					holidayAcquisitionDate = paidHolidayDataDto.getAcquisitionDate();
					break;
				}
			}
			if (holidayAcquisitionDate == null) {
				addHolidayNumDaysExcessErrorMessage(mospParams.getName("Salaried", "Vacation"),
						mospParams.getName("Time"));
			}
		}
		// 設定
		setHolidayRequest(dto, holidayAcquisitionDate, useDay, useHour);
	}
	
	/**
	 * ストック休暇情報をDTOに設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setStockLeave(HolidayRequestDtoInterface dto) throws MospException {
		if (dto.getHolidayType1() != TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			return;
		}
		if (!Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(dto.getHolidayType2())) {
			// ストック休暇でない場合
			return;
		}
		// ストック休暇である場合
		double useDay = 0;
		int holidayRange = dto.getHolidayRange();
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			useDay = TimeConst.HOLIDAY_TIMES_ALL;
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			useDay = TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
			return;
		}
		List<StockHolidayDataDtoInterface> list = stockHolidayInfoReference
			.getStockHolidayPossibleRequestForRequest(dto.getPersonalId(), dto.getRequestStartDate());
		Date holidayAcquisitionDate = null;
		for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
			if (stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
					- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() >= useDay) {
				holidayAcquisitionDate = stockHolidayDataDto.getAcquisitionDate();
				break;
			}
		}
		if (holidayAcquisitionDate == null) {
			addHolidayNumDaysExcessErrorMessage(mospParams.getName("Stock", "Vacation"), mospParams.getName("Day"));
			return;
		}
		// 設定
		setHolidayRequest(dto, holidayAcquisitionDate, useDay, 0);
	}
	
	/**
	 * 休暇情報をDTOに設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setLeave(HolidayRequestDtoInterface dto) throws MospException {
		int holidayType1 = dto.getHolidayType1();
		if (holidayType1 != TimeConst.CODE_HOLIDAYTYPE_SPECIAL && holidayType1 != TimeConst.CODE_HOLIDAYTYPE_OTHER
				&& holidayType1 != TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇・その他休暇・欠勤でない場合
			return;
		}
		// 特別休暇・その他休暇・欠勤である場合
		HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(dto.getHolidayType2(),
				dto.getRequestStartDate(), holidayType1);
		if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			addHolidayNotExistErrorMessage();
			return;
		}
		Date holidayAcquisitionDate = dto.getRequestStartDate();
		HolidayDataDtoInterface holidayDataDto = null;
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL || holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// 特別休暇又はその他休暇の場合
			holidayDataDto = holidayInfoReference.getHolidayPossibleRequestForRequest(dto.getPersonalId(),
					dto.getRequestStartDate(), dto.getHolidayType2(), holidayType1);
			if (holidayDataDto == null) {
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			holidayAcquisitionDate = holidayDataDto.getActivateDate();
		}
		double useDay = 0;
		int useHour = 0;
		int holidayRange = dto.getHolidayRange();
		// 時間休の場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			useDay = getHolidayRequestDays(dto, holidayDto, holidayDataDto);
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			useHour = 1;
		} else {
			mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
			return;
		}
		
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 設定
		setHolidayRequest(dto, holidayAcquisitionDate, useDay, useHour);
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		return scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
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
		return scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), targetDate, localRequestUtil);
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
		// 休暇申請日数を取得
		double holidayRequestDays = getHolidayRequestDays(personalId, startDate, endDate, holidayRange);
		// 欠勤である場合
		if (holidayDto.getHolidayType() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 休暇申請日数を取得
			return holidayRequestDays;
		}
		// 欠勤でない場合
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
	 * 休暇申請日数を取得する。<br>
	 * @param personalId   個人ID
	 * @param startDate    休暇開始日
	 * @param endDate      休暇終了日
	 * @param holidayRange 休暇範囲
	 * @return 休暇申請日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getHolidayRequestDays(String personalId, Date startDate, Date endDate, int holidayRange)
			throws MospException {
		// 休暇申請日数を準備
		int count = 0;
		// 申請開始日から申請終了日の日リストを取得
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		// 日毎に処理
		for (Date date : dateList) {
			// 勤務形態コード取得
			String workTypeCode = getScheduledWorkTypeCode(personalId, date);
			// 勤務形態チェック
			checkWorkType(startDate, endDate, date, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				return 0D;
			}
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
					|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
				continue;
			}
			count++;
		}
		// 全休の場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 休暇申請日数を取得
			return count;
		}
		// 午前休又は午後休の場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 休暇申請日数×0.5を取得
			return count * TimeConst.HOLIDAY_TIMES_HALF;
		}
		// 日数は0と判断(それ以外(時間休))
		return 0D;
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
			// 勤怠情報が存在しない場合
			Date targetDate = dto.getRequestStartDate();
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
			if (workOnHolidayRequestDto != null
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				// 振出・休出申請が承認済且つ振り替える場合
				List<SubstituteDtoInterface> list = substituteDao
					.findForWorkflow(workOnHolidayRequestDto.getWorkflow());
				for (SubstituteDtoInterface substituteDto : list) {
					// 振替休日取得
					targetDate = substituteDto.getSubstituteDate();
					break;
				}
				// 振替先の勤務形態コード取得
				workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), targetDate);
			} else if (workOnHolidayRequestDto != null && workOnHolidayRequestDto
				.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 振出・休出申請が承認済且つ振り替える(勤務形態変更あり)場合
				// 振替出勤申請時の勤務形態コード取得
				workTypeCode = workOnHolidayRequestDto.getWorkTypeCode();
			} else {
				// それ以外の場合
				workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), targetDate);
			}
		} else {
			// 勤怠情報が存在する場合、勤怠情報から勤務形態を取得
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
	 * 編集時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkTimeHoliday(HolidayRequestDtoInterface dto) throws MospException {
		// 時間休時のみチェック
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return;
		}
		// 始業時刻終業時刻を準備
		Date targetStartTime = null;
		Date targetEndTime = null;
		Date defaultDate = DateUtility.getDefaultTime();
		// 申請情報を取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestStartDate());
		// 勤怠下書情報を取得
		AttendanceDtoInterface attendanceDraftDto = requestUtil.getDraftAttendance();
		// 時差出勤申請情報を取得
		DifferenceRequestDtoInterface differenceDto = requestUtil.getDifferenceDto(true);
		// 勤務形態変更申請情報取得
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = requestUtil.getWorkTypeChangeDto(true);
		// 振替出勤申請情報取得
		WorkOnHolidayRequestDtoInterface workOnHolidayDto = requestUtil.getWorkOnHolidayDto(true);
		// 勤務開始時刻・終了時刻設定
		if (differenceDto != null) {
			// 承認済みの時差出勤がある場合
			// 勤務開始時刻設定
			targetStartTime = TimeUtility.getDefaultTime(
					DateUtility.getStringHour(differenceDto.getRequestStart(), dto.getRequestStartDate()),
					DateUtility.getStringMinute(differenceDto.getRequestStart()));
			// 勤務終了時刻設定
			targetEndTime = TimeUtility.getDefaultTime(
					DateUtility.getStringHour(differenceDto.getRequestEnd(), dto.getRequestStartDate()),
					DateUtility.getStringMinute(differenceDto.getRequestEnd()));
		} else if (workTypeChangeDto != null) {
			// 勤務形態変更申請がされている場合
			// 勤務形態情報取得
			WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(workTypeChangeDto.getWorkTypeCode(),
					dto.getRequestStartDate());
			// 勤務開始時刻情報取得
			WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
					workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
			// 勤務終了時刻情報取得
			WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
					workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
			// 勤務開始時刻設定
			targetStartTime = TimeUtility.getDefaultTime(DateUtility.getStringHour(workStartDto.getWorkTypeItemValue()),
					DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
			// 勤務終了時刻設定
			targetEndTime = TimeUtility.getDefaultTime(
					DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
					DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
			
		} else if (workOnHolidayDto != null) {
			// 振替出勤申請が行われている場合
			if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF
					|| workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 休日出勤、半日振替出勤の場合エラーメッセージを設定する。
				addHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestStartDate());
				return;
			}
			// 振替出勤申請情報から勤務形態を取得する(勤務形態変更ありの場合)
			String workTypeCode = workOnHolidayDto.getWorkTypeCode();
			if (workTypeCode == null || workTypeCode.isEmpty()) {
				// 勤務形態変更なしの場合
				SubstituteDtoInterface substituteDto = substituteReference.getSubstituteDto(dto.getPersonalId(),
						dto.getRequestStartDate());
				if (substituteDto == null) {
					addHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestStartDate());
					return;
				}
				// 振替先の勤務形態コードをカレンダから取得する
				workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(),
						substituteDto.getSubstituteDate());
			}
			// 勤務形態コードから勤務形態情報を取得
			WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(workTypeCode, dto.getRequestStartDate());
			// 勤務開始時刻情報取得
			WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
					workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
			// 勤務終了時刻情報取得
			WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
					workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
			// 勤務開始時刻設定
			targetStartTime = TimeUtility.getDefaultTime(DateUtility.getStringHour(workStartDto.getWorkTypeItemValue()),
					DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
			// 勤務終了時刻設定
			targetEndTime = TimeUtility.getDefaultTime(
					DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
					DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
		} else if (attendanceDraftDto != null) {
			// 時差出勤申請・勤務形態変更申請がされておらず、勤怠の下書が存在する場合
			if (differenceRequestReference.isDifferenceTypeA(attendanceDraftDto.getWorkTypeCode())
					|| differenceRequestReference.isDifferenceTypeB(attendanceDraftDto.getWorkTypeCode())
					|| differenceRequestReference.isDifferenceTypeC(attendanceDraftDto.getWorkTypeCode())
					|| differenceRequestReference.isDifferenceTypeD(attendanceDraftDto.getWorkTypeCode())
					|| differenceRequestReference.isDifferenceTypeS(attendanceDraftDto.getWorkTypeCode())) {
				// 下書の勤務形態コードがa・b・c・d・sの場合
				WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(attendanceDraftDto.getWorkTypeCode(),
						dto.getRequestStartDate());
				if (workTypeDto == null) {
					// 勤務形態マスタに勤務形態コードa・b・c・d・sが登録されていない場合カレンダから勤務形態コードを取得する.
					String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(),
							dto.getRequestStartDate());
					// 勤務形態コードから勤務形態情報を取得
					WorkTypeDtoInterface workType = workTypeReference.findForInfo(workTypeCode,
							dto.getRequestStartDate());
					// 勤務開始時刻情報取得
					WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(workType.getWorkTypeCode(),
							workType.getActivateDate(), TimeConst.CODE_WORKSTART);
					// 勤務終了時刻情報取得
					WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(workType.getWorkTypeCode(),
							workType.getActivateDate(), TimeConst.CODE_WORKEND);
					// 勤務開始時刻設定
					targetStartTime = TimeUtility.getDefaultTime(
							DateUtility.getStringHour(workStartDto.getWorkTypeItemValue()),
							DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
					// 勤務終了時刻設定
					targetEndTime = TimeUtility.getDefaultTime(
							DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
							DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
				} else {
					// 勤務形態マスタに勤務形態コードa・b・c・d・sが登録されている場合
					// 勤務開始時刻情報取得
					WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(
							workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
					// 勤務終了時刻情報取得
					WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(
							workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
					// 勤務開始時刻設定
					targetStartTime = TimeUtility.getDefaultTime(
							DateUtility.getStringHour(workStartDto.getWorkTypeItemValue()),
							DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
					// 勤務終了時刻設定
					targetEndTime = TimeUtility.getDefaultTime(
							DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
							DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
				}
			} else {
				// 勤怠の下書から勤務形態情報を取得
				WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(attendanceDraftDto.getWorkTypeCode(),
						dto.getRequestStartDate());
				// 勤務開始時刻情報取得
				WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
				// 勤務終了時刻情報取得
				WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
				// 勤務開始時刻設定
				targetStartTime = TimeUtility.getDefaultTime(
						DateUtility.getStringHour(workStartDto.getWorkTypeItemValue()),
						DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
				// 勤務終了時刻設定
				targetEndTime = TimeUtility.getDefaultTime(
						DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
						DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
			}
		} else {
			// 各種申請がされていない場合、カレンダから勤務形態コードを取得する。
			checkTimeHolidayCalendar(dto);
			return;
		}
		// 時間休開始時刻を取得
		Date requestStartTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(dto.getStartTime(), dto.getRequestStartDate()),
				DateUtility.getStringMinute(dto.getStartTime()));
		// 時間休終了時刻を取得
		Date requestEndTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(dto.getEndTime(), dto.getRequestStartDate()),
				DateUtility.getStringMinute(dto.getEndTime()));
		// 勤務形態の勤務開始時間と時間休期間の重複確認
		if (!DateUtility.isTermContain(requestStartTime, targetStartTime, targetEndTime)
				|| !DateUtility.isTermContain(requestEndTime, targetStartTime, targetEndTime)) {
			// 重複していない場合エラーメッセージ
			String workStartHour = DateUtility.getStringHourH(targetStartTime, defaultDate);
			String workStartMinute = DateUtility.getStringMinute(targetStartTime);
			String workEndHour = DateUtility.getStringHourH(targetEndTime, defaultDate);
			String workEndMinute = DateUtility.getStringMinute(targetEndTime);
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_HOLIDAY_CHECK, mospParams.getName("HolidayTime"),
					workStartHour, workStartMinute, workEndHour, workEndMinute);
		}
	}
	
	/**
	 * 勤務形態変更申請の有無を確認する。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkWorkTypeChange(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請日の勤務形態変更申請情報を取得する。
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate());
		if (workTypeChangeDto == null) {
			// 勤務形態変更申請がされていない場合
			return;
		}
		// 勤務形態変更申請のワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
		// 承認状況確認
		if (workflowIntegrate.isCompleted(workflowDto.getWorkflow())
				|| workflowIntegrate.isWithDrawn(workflowDto.getWorkflow())
				|| workflowIntegrate.isDraft(workflowDto.getWorkflow())) {
			// 承認状況が承認済・取下・下書の場合
			return;
		}
		// 承認可能な勤務形態変更申請がある場合
		String requestName = mospParams.getName("Work", "Form", "Change");
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
				getStringDate(dto.getRequestStartDate()), requestName);
	}
	
	/**
	 * 時差出勤申請の有無を確認する。
	 * 時間休を申請する場合、時差出勤申請は承認済のみ。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDifference(HolidayRequestDtoInterface dto) throws MospException {
		// 時間休時のみチェック
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return;
		}
		// 時差出勤申請取得（時間休は期間で申請できない）
		DifferenceRequestDtoInterface differenceDto = differenceRequestReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate());
		if (differenceDto == null) {
			// チェックなし
			return;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface differenceWorkflow = workflowIntegrate.getLatestWorkflowInfo(differenceDto.getWorkflow());
		// 承認状況が取下でなく、下書でなく、承認済でない場合
		if (!WorkflowUtility.isWithDrawn(differenceWorkflow) && !WorkflowUtility.isDraft(differenceWorkflow)
				&& !WorkflowUtility.isCompleted(differenceWorkflow)) {
			// 時差出勤申請のメッセージ追加
			String requestName = mospParams.getName("TimeDifference", "GoingWork", "Application");
			mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
					getStringDate(dto.getRequestStartDate()), requestName);
		}
	}
	
	/**
	 * 時間休の範囲確認<br>
	 * カレンダー情報で確認を行う
	 * @param dto 休暇申請DTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void checkTimeHolidayCalendar(HolidayRequestDtoInterface dto) throws MospException {
		// 始業時刻終業時刻を準備
		Date targetStartTime = null;
		Date targetEndTime = null;
		Date defaultDate = DateUtility.getDefaultTime();
		
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestStartDate());
		// 勤務形態コードから勤務形態情報を取得
		WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(workTypeCode, dto.getRequestStartDate());
		// 勤務開始時刻情報取得
		WorkTypeItemDtoInterface workStartDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
				workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
		// 勤務終了時刻情報取得
		WorkTypeItemDtoInterface workEndDto = workTypeItemReference.findForKey(workTypeDto.getWorkTypeCode(),
				workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
		// 勤務開始時刻設定
		targetStartTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(workStartDto.getWorkTypeItemValue(), defaultDate),
				DateUtility.getStringMinute(workStartDto.getWorkTypeItemValue()));
		// 勤務終了時刻設定
		targetEndTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(workEndDto.getWorkTypeItemValue(), defaultDate),
				DateUtility.getStringMinute(workEndDto.getWorkTypeItemValue()));
		
		// 時間休の範囲チェック
		checkTimeHolidayAddMessage(dto, targetStartTime, targetEndTime, defaultDate);
		
	}
	
	/**
	 * 時間休の範囲チェックを行い<br>
	 * エラーの場合はメッセージを設定する<br>
	 * @param dto 休暇申請DTO
	 * @param targetStartTime 対象開始時間
	 * @param targetEndTime 対象終了時間
	 * @param defaultDate 標準時間
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void checkTimeHolidayAddMessage(HolidayRequestDtoInterface dto, Date targetStartTime, Date targetEndTime,
			Date defaultDate) throws MospException {
		// 時間休開始時刻を取得
		Date requestStartTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(dto.getStartTime(), dto.getRequestStartDate()),
				DateUtility.getStringMinute(dto.getStartTime()));
		// 時間休終了時刻を取得
		Date requestEndTime = TimeUtility.getDefaultTime(
				DateUtility.getStringHour(dto.getEndTime(), dto.getRequestStartDate()),
				DateUtility.getStringMinute(dto.getEndTime()));
		
		// 勤務形態の勤務開始時間と時間休期間の重複確認
		if (!DateUtility.isTermContain(requestStartTime, targetStartTime, targetEndTime)
				|| !DateUtility.isTermContain(requestEndTime, targetStartTime, targetEndTime)) {
			// 重複していない場合エラーメッセージ
			String workStartHour = DateUtility.getStringHourH(targetStartTime, defaultDate);
			String workStartMinute = DateUtility.getStringMinute(targetStartTime);
			String workEndHour = DateUtility.getStringHourH(targetEndTime, defaultDate);
			String workEndMinute = DateUtility.getStringMinute(targetEndTime);
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_HOLIDAY_CHECK, mospParams.getName("HolidayTime"),
					workStartHour, workStartMinute, workEndHour, workEndMinute);
		}
		
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
	
	/**
	 * 開始時刻エラーメッセージを設定する。<br>
	 */
	protected void addStartTimeErrorMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT,
				mospParams.getName("Time", "Rest", "Application", "Time"));
	}
	
	/**
	 * 期間が重複する場合のメッセージを設定する。<br>
	 */
	protected void addDuplicateTermMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_TERM_DUPLICATE, mospParams.getName("HolidayTime"));
	}
	
}
