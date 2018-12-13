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
import java.util.Map.Entry;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAttendanceDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 勤怠データ登録クラス。
 */
public class AttendanceRegistBean extends TimeApplicationBean implements AttendanceRegistBeanInterface {
	
	/**
	 * 勤怠データマスタDAOクラス。<br>
	 */
	private AttendanceDaoInterface					dao;
	/**
	 * 勤怠データ参照インターフェース。<br>
	 */
	private AttendanceReferenceBeanInterface		attendanceReference;
	/**
	 * 休暇申請参照インターフェース。<br>
	 */
	private HolidayRequestReferenceBeanInterface	holidayRequestReference;
	/**
	 * 勤務形態マスタ参照インターフェース。<br>
	 */
	protected WorkTypeReferenceBeanInterface		workTypeReference;
	/**
	 * 勤務形態項目DAOインターフェース。<br>
	 */
	protected WorkTypeItemDaoInterface				workTypeItemDao;
	/**
	 * ワークフロー参照インターフェース。<br>
	 */
	protected WorkflowReferenceBeanInterface		workflowReference;
	/**
	 * 残業申請DAOインターフェース。<br>
	 */
	private OvertimeRequestDaoInterface				overtimeDao;
	/**
	 * 時差出勤申請参照インターフェース。<br>
	 */
	private DifferenceRequestReferenceBeanInterface	differenceReference;
	/**
	 * 休暇申請DAOインターフェース。<br>
	 */
	private HolidayRequestDaoInterface				holidayDao;
	/**
	 * 休日出勤申請DAOインターフェース。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface		workOnHolidayRequestDao;
	/**
	 * 振替休日データDAOインターフェース。
	 */
	private SubstituteDaoInterface					substituteDao;
	/**
	 * 代休申請DAOインターフェース。
	 */
	private SubHolidayRequestDaoInterface			subHolidayDao;
	/**
	 * 人事入社情報参照インターフェース
	 */
	private EntranceReferenceBeanInterface			entranceReference;
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	private RetirementReferenceBeanInterface		retirementReference;
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	private WorkflowCommentReferenceBeanInterface	workflowCommentReference;
	/**
	 * 勤怠データ修正情報登録インターフェース。<br>
	 */
	private AttendanceCorrectionRegistBeanInterface	attendanceCorrectionRegist;
	/**
	 * 勤怠データ休憩情報登録インターフェース。	<br>
	 */
	private RestRegistBeanInterface					restRegist;
	/**
	 * 勤怠データ外出情報登録インターフェース。	<br>
	 */
	private GoOutRegistBeanInterface				goOutRegist;
	/**
	 * 代休データ登録インターフェース。<br>
	 */
	private SubHolidayRegistBeanInterface			subHolidayRegist;
	/**
	 * ワークフロー登録インターフェース。<br>
	 */
	private WorkflowRegistBeanInterface				workflowRegist;
	/**
	 * ワークフローコメント登録インターフェース。<br>
	 */
	private WorkflowCommentRegistBeanInterface		workflowCommentRegist;
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	/**
	 * 締日ユーティリティ。<br>
	 */
	private CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface				requestUtil;
	
	/**
	 * 休暇範囲(午前休かつ午後休)。
	 */
	protected int									CODE_HOLIDAY_RANGE_AM_PM			= 5;
	
	/**
	 * MosPアプリケーション設定キー(勤怠申請期限設定)。
	 */
	protected static final String					APP_KEY_APPLICABLE_LIMIT_ATTENDANCE	= "ApplicableLimitAttendance";
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public AttendanceRegistBean() {
		super();
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public AttendanceRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		dao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		workTypeItemDao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		holidayRequestReference = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		workflowReference = (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
		overtimeDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		differenceReference = (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class);
		holidayDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		subHolidayDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		workflowCommentReference = (WorkflowCommentReferenceBeanInterface)createBean(WorkflowCommentReferenceBeanInterface.class);
		attendanceCorrectionRegist = (AttendanceCorrectionRegistBeanInterface)createBean(AttendanceCorrectionRegistBeanInterface.class);
		restRegist = (RestRegistBeanInterface)createBean(RestRegistBeanInterface.class);
		goOutRegist = (GoOutRegistBeanInterface)createBean(GoOutRegistBeanInterface.class);
		subHolidayRegist = (SubHolidayRegistBeanInterface)createBean(SubHolidayRegistBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
	}
	
	@Override
	public AttendanceDtoInterface getInitDto() {
		return new TmdAttendanceDto();
	}
	
	@Override
	public void regist(AttendanceDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getPersonalId(), dto.getWorkDate(), dto.getTimesWork()) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void insert(AttendanceDtoInterface dto) throws MospException {
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
		dto.setTmdAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(AttendanceDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdAttendanceId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(AttendanceDtoInterface dto) throws MospException {
		// 重複確認
		checkDuplicateInsert(dao.findForHistory(dto.getPersonalId(), dto.getWorkDate(), dto.getTimesWork()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(AttendanceDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdAttendanceId());
	}
	
	@Override
	public void checkValidate(AttendanceDtoInterface dto) throws MospException {
		// 時刻妥当性チェック
		checkTimeValidity(dto);
		// 基本情報のチェック
		attendanceReference.chkBasicInfo(dto.getPersonalId(), dto.getWorkDate());
	}
	
	@Override
	public void validate(AttendanceDtoInterface dto) throws MospException {
		// TODO 妥当性確認
	}
	
	@Override
	public void checkDraft(AttendanceDtoInterface dto) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		checkTemporaryClosingFinal(dto);
		if (dto.getStartTime() != null || dto.getEndTime() != null) {
			// 勤怠設定情報の取得
			setTimeSettings(dto.getPersonalId(), dto.getWorkDate());
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 始業時刻チェック
			checkDraftStartTimeForWork(dto);
			// 終業時刻チェック
			checkDraftEndTimeForWork(dto);
		}
		// 振出・休出申請チェック
		checkWorkOnHolidayRequest(dto);
		// 休暇申請チェック
		checkHolidayRequest(dto);
		// 分単位休暇全休チェック
		checkMinutelyHoliday(dto);
	}
	
	@Override
	public void checkAppli(AttendanceDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 必須チェック
		checkRequired(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請期間チェック
		checkPeriod(dto);
		// 分単位休暇が全休でない場合
		if (dto.getMinutelyHolidayA() != 1 && dto.getMinutelyHolidayB() != 1) {
			// 遅刻の限度チェック
			checkLateTime(dto);
			// 早退の限度チェック
			checkLeaveEarlyTime(dto);
		}
		// 残業のチェック
		checkOvertime(dto);
		// 申請チェック
		checkRequest(dto);
		// 時間休のチェック
		checkPaidLeaveTime(dto);
		checkPaidLeaveTimeForShortTime(dto);
		// 勤怠の申請チェック
		checkAttendance(dto);
	}
	
	@Override
	public void checkCancelAppli(AttendanceDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
	}
	
	@Override
	public void checkDelete(AttendanceDtoInterface dto) throws MospException {
		// 代休チェック
		checkSubHoliday(dto, mospParams.getName("Delete"));
	}
	
	@Override
	public void checkApproval(AttendanceDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
		// 翌日の勤怠チェック
		checkTomorrowAttendance(dto);
	}
	
	@Override
	public void checkCancelApproval(AttendanceDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
		// 代休チェック
		checkSubHoliday(dto, mospParams.getName("Release"));
	}
	
	@Override
	public void checkCancel(AttendanceDtoInterface dto) throws MospException {
		// 代休チェック
		checkSubHoliday(dto, mospParams.getName("Release"));
	}
	
	@Override
	public void checkPeriod(AttendanceDtoInterface dto) {
		// 勤怠申請期限設定情報取得
		int checkPeriodDays = mospParams.getApplicationProperty(APP_KEY_APPLICABLE_LIMIT_ATTENDANCE, 0);
		// 勤怠申請期限設定されていない場合
		if (checkPeriodDays == 0) {
			return;
		}
		// ヵ月後で設定されている場合
		if (checkPeriodDays > 100) {
			// ヵ月取得
			int targetMonth = checkPeriodDays - 100;
			// 勤務日がヵ月後の場合
			if (dto.getWorkDate().after(DateUtility.addMonth(getSystemDate(), targetMonth))
					|| dto.getWorkDate().equals(DateUtility.addMonth(getSystemDate(), targetMonth))) {
				// エラーメッセージ追加
				addAttendancePeriodErrorMessage(targetMonth + mospParams.getName("Months"));
			}
			return;
		}
		// 勤務日が日後の場合
		if (dto.getWorkDate().after(DateUtility.addDay(getSystemDate(), checkPeriodDays))
				|| dto.getWorkDate().equals(DateUtility.addDay(getSystemDate(), checkPeriodDays))) {
			// エラーメッセージ追加
			addAttendancePeriodErrorMessage(checkPeriodDays + mospParams.getName("Day"));
		}
	}
	
	@Override
	public void checkLateTime(AttendanceDtoInterface dto) throws MospException {
		// 勤怠設定情報の取得
		setTimeSettings(dto.getPersonalId(), dto.getWorkDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 限度の超過チェック
		if (checkLateLimit(dto, timeSettingDto)) {
			// 超過していた場合、休暇申請の取得
			List<HolidayRequestDtoInterface> list = holidayRequestReference.getHolidayRequestList(dto.getPersonalId(),
					dto.getWorkDate());
			// 休暇申請チェック
			if (list != null && !list.isEmpty()) {
				// 午前休の申請チェック
				for (HolidayRequestDtoInterface holidayRequestDto : list) {
					if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
						WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(holidayRequestDto
							.getWorkflow());
						// 下書・取下の場合は参照しない
						if (workflowDto != null
								&& !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)
								&& !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
							// 1件でも午前休があればエラーメッセージ無し
							return;
						}
					}
				}
			}
			// メッセージ設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
					getStringDate(dto.getWorkDate()), mospParams.getName("Tardiness"), mospParams.getName("Tardiness"),
					mospParams.getName("AmRest"));
		}
	}
	
	@Override
	public void checkLeaveEarlyTime(AttendanceDtoInterface dto) throws MospException {
		// 勤怠設定情報の取得
		setTimeSettings(dto.getPersonalId(), dto.getWorkDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 限度の超過チェック
		if (checkLeaveEarlyLimit(dto, timeSettingDto)) {
			// 超過していた場合、休暇申請の取得
			List<HolidayRequestDtoInterface> list = holidayRequestReference.getHolidayRequestList(dto.getPersonalId(),
					dto.getWorkDate());
			// 休暇申請チェック
			if (list != null && !list.isEmpty()) {
				// 午後休の申請チェック
				for (HolidayRequestDtoInterface holidayRequestDto : list) {
					if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(holidayRequestDto
							.getWorkflow());
						// 下書・取下の場合は参照しない
						if (workflowDto != null
								&& !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)
								&& !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
							// 1件でも午後休があればエラーメッセージ無し
							return;
						}
					}
				}
			}
			// メッセージ設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
					getStringDate(dto.getWorkDate()), mospParams.getName("LeaveEarly"),
					mospParams.getName("LeaveEarly"), mospParams.getName("PmRest"));
		}
	}
	
	@Override
	public void checkHolidayTime(String personalId, Date workDate, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> goOutPublicList, List<GoOutDtoInterface> goOutPrivateList,
			List<GoOutDtoInterface> goOutMinutely1HolidayList, List<GoOutDtoInterface> goOutMinutely2HolidayList)
			throws MospException {
		// 表示用勤務日取得
		String workDateString = DateUtility.getStringDateAndDay(workDate);
		// 申請情報設定
		requestUtil.setRequests(personalId, workDate);
		// 休暇申請リスト取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(true);
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface holidayDto : holidayList) {
			// 時間休でない場合
			if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				continue;
			}
			// 始業終業取得
			Date startTime = holidayDto.getStartTime();
			Date endTime = holidayDto.getEndTime();
			// 休憩情報リスト毎に処理
			for (RestDtoInterface restDto : restList) {
				if (checkDuplicationTimeZone(startTime, endTime, restDto.getRestStart(), restDto.getRestEnd())) {
					String[] rep = { workDateString, mospParams.getName("HolidayTime"),
						mospParams.getName("Rest" + restDto.getRest()) };
					// 休憩と重複している場合
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
					return;
				}
			}
			// 公用外出情報リスト毎に処理
			for (GoOutDtoInterface goOutDto : goOutPublicList) {
				if (checkDuplicationTimeZone(startTime, endTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
					// 公用外出と重複している場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("Official"));
					sb.append(mospParams.getName("GoingOut"));
					sb.append(goOutDto.getTimesGoOut());
					String[] rep = { workDateString, mospParams.getName("HolidayTime"), sb.toString() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
					return;
				}
			}
			// 私用外出リスト毎に処理
			for (GoOutDtoInterface goOutDto : goOutPrivateList) {
				if (checkDuplicationTimeZone(startTime, endTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
					// 私用外出と重複している場合
					String[] rep = { workDateString, mospParams.getName("HolidayTime"),
						mospParams.getName("PrivateGoingOut" + goOutDto.getTimesGoOut()) };
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
					return;
				}
			}
			// 分単位休暇1リスト毎に処理
			for (GoOutDtoInterface goOutDto : goOutMinutely1HolidayList) {
				if (checkDuplicationTimeZone(startTime, endTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
					// 分単位休暇1と重複している場合
					String[] rep = { workDateString, mospParams.getName("HolidayTime"),
						mospParams.getName("MinutelyHolidayAAbbr") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
					return;
				}
			}
			// 分単位休暇2リスト毎に処理
			for (GoOutDtoInterface goOutDto : goOutMinutely2HolidayList) {
				if (checkDuplicationTimeZone(startTime, endTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
					// 分単位休暇2と重複している場合
					String[] rep = { workDateString, mospParams.getName("HolidayTime"),
						mospParams.getName("MinutelyHolidayBAbbr") };
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
					return;
				}
			}
		}
	}
	
	/**
	 * 下書時の始業時刻のチェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkDraftStartTimeForWork(AttendanceDtoInterface dto) throws MospException {
		if (dto.getStartTime() == null) {
			// 始業時刻が入力されていない場合はチェックを行わない
			return;
		}
		String workDate = getStringDate(dto.getWorkDate());
		if (!dto.getStartTime().before(addDay(dto.getWorkDate(), 1))) {
			// 始業時刻が翌日の場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("StartWork"));
			sb.append(mospParams.getName("Moment"));
			mospParams.addErrorMessage(
					TimeMessageConst.MSG_START_DAY_TIME_CHECK,
					workDate,
					DateUtility.getStringTime(
							DateUtility.addHour(DateUtility.getDefaultTime(), TimeConst.TIME_DAY_ALL_HOUR),
							DateUtility.getDefaultTime()), sb.toString());
		}
		if (!dto.getStartTime().before(getTime(timeSettingDto.getStartDayTime(), dto.getWorkDate()))) {
			// 始業時刻が一日の起算時刻より前でない場合
			return;
		}
		AttendanceDtoInterface yesterdayDto = attendanceReference.findForKey(dto.getPersonalId(),
				addDay(dto.getWorkDate(), -1), TimeBean.TIMES_WORK_DEFAULT);
		if (yesterdayDto == null || yesterdayDto.getEndTime() == null) {
			return;
		}
		if (dto.getStartTime().after(yesterdayDto.getEndTime())) {
			// 始業時刻が前日の終業時刻より後の場合
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("Ahead"));
		sb.append(mospParams.getName("Day"));
		sb.append(mospParams.getName("Kara"));
		mospParams.addErrorMessage(
				TimeMessageConst.MSG_START_TIME_CHECK,
				new String[]{
					workDate,
					sb.toString(),
					DateUtility.getStringTime(timeSettingDto.getStartDayTime()),
					DateUtility.getStringTime(
							DateUtility.addHour(timeSettingDto.getStartDayTime(), TimeConst.TIME_DAY_ALL_HOUR),
							DateUtility.getDefaultTime()) });
	}
	
	/**
	 * 下書時の終業時刻のチェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkDraftEndTimeForWork(AttendanceDtoInterface dto) throws MospException {
		if (dto.getEndTime() == null) {
			// 終業時刻が入力されていない場合はチェックを行わない
			return;
		}
		String workDate = getStringDate(dto.getWorkDate());
		Date tomorrowDate = addDay(dto.getWorkDate(), 1);
		Date afterTwentyFourHoursTime = getTime(timeSettingDto.getStartDayTime(), tomorrowDate);
		String afterTwentyFourHours = DateUtility.getStringTime(afterTwentyFourHoursTime, dto.getWorkDate());
		if (dto.getEndTime().after(afterTwentyFourHoursTime)) {
			// 終業時刻が一日の起算時刻の24時間後より後の場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("EndWork"));
			sb.append(mospParams.getName("Moment"));
			mospParams.addErrorMessage(TimeMessageConst.MSG_START_DAY_TIME_CHECK, workDate, afterTwentyFourHours,
					sb.toString());
		}
		if (afterTwentyFourHoursTime.equals(dto.getEndTime())) {
			// 終業時刻が一日の起算時刻の24時間後の場合
			return;
		}
		AttendanceDtoInterface tomorrowDto = attendanceReference.findForKey(dto.getPersonalId(), tomorrowDate,
				TimeBean.TIMES_WORK_DEFAULT);
		if (tomorrowDto == null || tomorrowDto.getStartTime() == null) {
			return;
		}
		if (dto.getEndTime().before(tomorrowDto.getStartTime())) {
			// 終業時刻が翌日の始業時刻より前の場合
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("NextDay"));
		sb.append(mospParams.getName("To"));
		mospParams.addErrorMessage(TimeMessageConst.MSG_START_TIME_CHECK, new String[]{ workDate, sb.toString(),
			DateUtility.getStringTime(timeSettingDto.getStartDayTime()), afterTwentyFourHours });
	}
	
	/**
	 * @param dto 勤怠データ
	 * @param timeSettingDto 勤怠設定
	 * @return 限度時間を超えた場合true それ以外false
	 */
	public boolean checkLateLimit(AttendanceDtoInterface dto, TimeSettingDtoInterface timeSettingDto) {
		int lateEarlyHalf = (DateUtility.getHour(timeSettingDto.getLateEarlyHalf()) * TimeConst.CODE_DEFINITION_HOUR)
				+ DateUtility.getMinute(timeSettingDto.getLateEarlyHalf());
		if (lateEarlyHalf < dto.getLateTime()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param dto 勤怠データ
	 * @param timeSettingDto 勤怠設定
	 * @return 限度時間を超えた場合true それ以外false
	 */
	public boolean checkLeaveEarlyLimit(AttendanceDtoInterface dto, TimeSettingDtoInterface timeSettingDto) {
		int lateEarlyHalf = (DateUtility.getHour(timeSettingDto.getLateEarlyHalf()) * TimeConst.CODE_DEFINITION_HOUR)
				+ DateUtility.getMinute(timeSettingDto.getLateEarlyHalf());
		if (lateEarlyHalf < dto.getLeaveEarlyTime()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void checkOvertime(AttendanceDtoInterface dto) throws MospException {
		// 設定締日情報の取得
		setCutoffSettings(dto.getPersonalId(), dto.getWorkDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		int noApproval = cutoffDto.getNoApproval();
		if (noApproval == 0 || noApproval == 1 || noApproval == 3) {
			// 未承認仮締が有効・無効(残業事後申請可)・無効(残業申請なし)の場合
			return;
		}
		if (dto.getOvertimeAfter() == 0) {
			return;
		}
		// 振出・休出申請
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto != null
				&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 振り替えない場合
			return;
		}
		// 残業申請
		List<OvertimeRequestDtoInterface> overtimeRequestList = requestUtil.getOverTimeList(true);
		for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
			if (overtimeRequestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER) {
				// 勤務後残業申請が承認済の場合
				return;
			}
		}
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_11, new String[]{
			getStringDate(dto.getWorkDate()), mospParams.getName("OvertimeWork"), mospParams.getName("OvertimeWork") });
	}
	
	@Override
	public void checkRequest(AttendanceDtoInterface dto) throws MospException {
		// 個人ID・申請日取得
		String personalId = dto.getPersonalId();
		Date requestDate = dto.getWorkDate();
		// 申請名称準備
		String requestName = "";
		// 各種申請を取得
		requestUtil.setRequests(personalId, requestDate);
		// 承認済み残業リスト取得
		List<OvertimeRequestDtoInterface> completedOvertimeList = new ArrayList<OvertimeRequestDtoInterface>();
		completedOvertimeList.addAll(requestUtil.getOverTimeList(true));
		// 残業申請
		List<OvertimeRequestDtoInterface> overtimeList = overtimeDao.findForList(personalId, requestDate);
		for (OvertimeRequestDtoInterface requestDto : overtimeList) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 承認状況が取下の場合
				continue;
			}
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
				// 承認状況が下書の場合
				continue;
			}
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認状況が承認済でない場合は、エラーメッセージの表示
				// TODO 残業申請のメッセージ追加
				requestName = mospParams.getName("OvertimeWork", "Application");
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE, getStringDate(requestDate),
						requestName);
			}
		}
		// 時差出勤申請
		DifferenceRequestDtoInterface differenceDto = differenceReference.findForKeyOnWorkflow(personalId, requestDate);
		if (differenceDto != null) {
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceDto.getWorkflow());
			if (workflowDto != null) {
				if (!PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())
						&& !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
						&& !PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が取下でなく、下書でなく、承認済でない場合は、エラーメッセージの表示
					// 時差出勤申請のメッセージ追加
					requestName = mospParams.getName("TimeDifference", "GoingWork", "Application");
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
							getStringDate(requestDate), requestName);
				}
			}
		}
		// 休暇申請（半休）
		List<HolidayRequestDtoInterface> holidayList = holidayDao.findForList(personalId, requestDate);
		for (HolidayRequestDtoInterface requestDto : holidayList) {
			// 休暇範囲取得
			int holidayRange = requestDto.getHolidayRange();
			// 休暇が午前休又は午後休又は時間休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が取下の場合
					continue;
				}
				if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が下書の場合
					continue;
				}
				if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が承認済でない場合は、エラーメッセージの表示
					// 休暇申請のメッセージ追加
					requestName = mospParams.getName("Vacation", "Application");
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
							getStringDate(requestDate), requestName);
					return;
				}
				// 時間休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					// 開始の前に開始時刻で終了が開始の前又は終了の後に終了かつ開始の後に終業
					if (isConfirmValidate(dto, completedOvertimeList, differenceDto, requestDto.getStartTime(),
							requestDto.getEndTime()) == false) {
						// エラーメッセージ
						String[] rep = { mospParams.getName("HolidayTime") };
						mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TIME_OUT_CHECK, rep);
						break;
					}
				}
			}
		}
		// 休日出勤申請に伴う振替申請（半休）
		List<SubstituteDtoInterface> substituteList = substituteDao.findForList(personalId, requestDate);
		for (SubstituteDtoInterface substituteDto : substituteList) {
			int range = substituteDto.getSubstituteRange();
			if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 振替日が午前休又は午後休の場合
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が取下の場合
					continue;
				}
				if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が下書の場合
					continue;
				}
				if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が承認済でない場合は、エラーメッセージの表示
					// 休日出勤申請のメッセージ追加
					requestName = mospParams.getName("Holiday", "GoingWork", "Application");
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
							getStringDate(requestDate), requestName);
				}
			}
		}
		// 代休申請（半休）
		List<SubHolidayRequestDtoInterface> subHolidayList = subHolidayDao.findForList(personalId, requestDate);
		for (SubHolidayRequestDtoInterface requestDto : subHolidayList) {
			int holidayRange = requestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 代休が午前休又は午後休の場合
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が取下の場合
					continue;
				}
				if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が下書の場合
					continue;
				}
				if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認状況が承認済でない場合は、エラーメッセージの表示
					// 代休申請のメッセージ追加
					requestName = mospParams.getName("CompensatoryHoliday", "Application");
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
							getStringDate(requestDate), requestName);
				}
			}
		}
		// 取下・下書以外の勤務形態変更申請取得
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = requestUtil.getWorkTypeChangeDto(false);
		if (workTypeChangeDto != null) {
			// 承認済でない場合
			if (!workflowIntegrate.isCompleted(workTypeChangeDto.getWorkflow())) {
				// 勤務形態変更申請のメッセージ追加
				requestName = mospParams.getName("Work", "Form", "Change", "Application");
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE, getStringDate(requestDate),
						requestName);
			}
		}
	}
	
	/**
	 * 対象の時間休申請が始業より前に申請している際の妥当性を確認する。
	 * 勤務形態の始業時刻・終業時刻を取得し設定する。
	 * @param dto 勤怠情報
	 * @param overtimeList 残業申請情報リスト
	 * @param differenceDto 時差出勤申請情報
	 * @param requestStartTime 申請開始時間
	 * @param requestEndTime 申請終了時間
	 * @return 確認結果(true：整合性がとれている、false：整合性がとれていない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean isConfirmValidate(AttendanceDtoInterface dto, List<OvertimeRequestDtoInterface> overtimeList,
			DifferenceRequestDtoInterface differenceDto, Date requestStartTime, Date requestEndTime)
			throws MospException {
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 法定休日労働又は所定休日労働の場合
			return true;
		}
		// 勤務開始時刻・終了時刻取得
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		if (differenceDto != null && workflowIntegrate.isCompleted(differenceDto.getWorkflow())) {
			// 時差出勤申請が承認済の場合
			startTime = differenceDto.getRequestStart();
			endTime = differenceDto.getRequestEnd();
		} else {
			// 時差出勤でない場合
			// 勤務形態から始業終業時刻を取得
			WorkTypeItemDtoInterface startDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate(),
					TimeConst.CODE_WORKSTART);
			WorkTypeItemDtoInterface endDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate(),
					TimeConst.CODE_WORKEND);
			if (startDto != null && endDto != null) {
				// 勤務形態情報取得
				int startHour = DateUtility.getHour(startDto.getWorkTypeItemValue(), DateUtility.getDefaultTime());
				int startMinute = DateUtility.getMinute(startDto.getWorkTypeItemValue());
				int endHour = DateUtility.getHour(endDto.getWorkTypeItemValue(), DateUtility.getDefaultTime());
				int endMinute = DateUtility.getMinute(endDto.getWorkTypeItemValue());
				// 勤務形態始業・終業時刻取得
				startTime = DateUtility.addMinute(DateUtility.addHour(dto.getWorkDate(), startHour), startMinute);
				endTime = DateUtility.addMinute(DateUtility.addHour(dto.getWorkDate(), endHour), endMinute);
			}
		}
		return !requestStartTime.before(startTime) && !requestEndTime.after(endTime);
	}
	
	/**
	 * 振出・休出申請チェック。<br>
	 * 振出・休出申請が承認済でない場合、エラーメッセージを設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkOnHolidayRequest(AttendanceDtoInterface dto) throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao.findForKeyOnWorkflow(
				dto.getPersonalId(), dto.getWorkDate());
		if (workOnHolidayRequestDto == null || workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
			return;
		}
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE_2, getStringDate(dto.getWorkDate()),
				mospParams.getName("jp.mosp.time.input.vo.WorkOnHolidayRequestVo"));
	}
	
	/**
	 * 休暇申請チェック。<br>
	 * 休暇、振替休日、代休が申請されている場合、エラーメッセージを設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(AttendanceDtoInterface dto) throws MospException {
		// 振替休日
		if (requestUtil.getWorkOnHolidayDto(true) == null
				&& requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 振出・休出でない場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("WorkManage"));
			sb.append(mospParams.getName("Application"));
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10, getStringDate(dto.getWorkDate()),
					mospParams.getName("ObservedHoliday"), sb.toString());
			return;
		}
		// 休暇
		boolean holidayAm = false;
		boolean holidayPm = false;
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null
				|| workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 振出・休出申請されていない
			// 又は振替出勤の場合
			int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合(エラーメッセージを設定)
				TimeMessageUtility.addErrorNotApplicableForHoliday(mospParams, dto.getWorkDate(), null);
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休の場合
				holidayAm = true;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午後休の場合
				holidayPm = true;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休且つ午後休の場合
				holidayAm = true;
				holidayPm = true;
			}
		}
		// 代休
		boolean subHolidayAm = false;
		boolean subHolidayPm = false;
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("WorkManage"));
			sb.append(mospParams.getName("Application"));
			mospParams.addErrorMessage(
					TimeMessageConst.MSG_REQUEST_CHECK_10,
					new String[]{ getStringDate(dto.getWorkDate()), mospParams.getName("CompensatoryHoliday"),
						sb.toString() });
			return;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 午前休の場合
			subHolidayAm = true;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午後休の場合
			subHolidayPm = true;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休且つ午後休の場合
			subHolidayAm = true;
			subHolidayPm = true;
		}
		if ((holidayAm || subHolidayAm) && (holidayPm || subHolidayPm)) {
			// 午前休且つ午後休の場合
			String requestName = "";
			if (holidayAm) {
				// 休暇の場合
				requestName = mospParams.getName("Vacation");
			} else if (subHolidayAm) {
				// 代休の場合
				requestName = mospParams.getName("CompensatoryHoliday");
			}
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("WorkManage"));
			sb.append(mospParams.getName("Application"));
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10,
					new String[]{ getStringDate(dto.getWorkDate()), requestName, sb.toString() });
		}
	}
	
	/**
	 * 分単位休暇チェック。
	 * @param dto 勤怠情報DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkMinutelyHoliday(AttendanceDtoInterface dto) throws MospException {
		// 勤務形態コード取得
		String workTypeCode = dto.getWorkTypeCode();
		// 所定休出出勤日の場合
		if (workTypeCode.equals(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY)) {
			if (dto.getMinutelyHolidayATime() != 0 || dto.getMinutelyHolidayBTime() != 0) {
				// 置換文字作成
				String[] rep = { mospParams.getName("Prescribed", "Holiday", "GoingWork", "Day"),
					mospParams.getName("MinutelyHoliday") };
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, rep);
				return;
			}
		} else if (workTypeCode.equals(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY)) {
			// 法定休日出勤日
			if (dto.getMinutelyHolidayATime() != 0 || dto.getMinutelyHolidayBTime() != 0) {
				// 置換文字作成
				String[] rep = { mospParams.getName("Legal", "Holiday", "GoingWork", "Day"),
					mospParams.getName("MinutelyHoliday") };
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, rep);
				return;
			}
		} else {
			// 勤務形態から始業終業時刻を取得
			WorkTypeItemDtoInterface startDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate(),
					TimeConst.CODE_WORKSTART);
			WorkTypeItemDtoInterface endDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate(),
					TimeConst.CODE_WORKEND);
			if (startDto != null && endDto != null) {
				// 勤務形態情報取得
				int startHour = DateUtility.getHour(startDto.getWorkTypeItemValue(), DateUtility.getDefaultTime());
				int startMinute = DateUtility.getMinute(startDto.getWorkTypeItemValue());
				int endHour = DateUtility.getHour(endDto.getWorkTypeItemValue(), DateUtility.getDefaultTime());
				int endMinute = DateUtility.getMinute(endDto.getWorkTypeItemValue());
				// TODO
				
			}
		}
		// TODO チェックがついているのに入力なしの時
		// 所定労働時間取得
		TimeSettingDtoInterface timeSettingDto = cutoffUtil.getTimeSetting(dto.getPersonalId(), dto.getWorkDate());
		int time = DateUtility.getHour(timeSettingDto.getGeneralWorkTime()) * 60;
		time = time + DateUtility.getMinute(timeSettingDto.getGeneralWorkTime());
		// 分単位休暇時間取得
		int minutelyHolidayTime = dto.getMinutelyHolidayATime() + dto.getMinutelyHolidayBTime();
		
		// 分単位休暇フラグ
		boolean allMinutelyHolidayA = dto.getMinutelyHolidayA() == getInteger(MospConst.CHECKBOX_ON);
		boolean allMinutelyHolidayB = dto.getMinutelyHolidayB() == getInteger(MospConst.CHECKBOX_ON);
		// 所定労働時間より分単位休暇時間が少ない又は同じ場合
		if (time >= minutelyHolidayTime) {
			return;
		}
		// 所定労働時間より分単位休暇時間が多い場合
		// 分単位が全休の場合
		if (allMinutelyHolidayA && allMinutelyHolidayB) {
			// エラーメッセージ
			TimeMessageUtility.addErrorMinutelyAOutOfWorkTime(mospParams);
			TimeMessageUtility.addErrorMinutelyBOutOfWorkTime(mospParams);
			return;
		}
		if (allMinutelyHolidayA) {
			// エラーメッセージ
			TimeMessageUtility.addErrorMinutelyAOutOfWorkTime(mospParams);
			return;
		}
		if (allMinutelyHolidayB) {
			// エラーメッセージ
			TimeMessageUtility.addErrorMinutelyBOutOfWorkTime(mospParams);
			return;
		}
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, mospParams.getName("MinutelyHoliday"),
				mospParams.getName("Prescribed", "Labor", "Time"));
	}
	
	@Override
	public void checkTimeExist(AttendanceDtoInterface dto) {
		// 始業時刻、終了時刻両方共無い場合
		if (dto.getStartTime() == null && dto.getEndTime() == null) {
			// エラーメッセージ設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_DRAFT_TIME_NOT_INPUT);
		}
	}
	
	/**
	 * @param dto 対象DTO
	 */
	public void checkTimeValidity(AttendanceDtoInterface dto) {
		if (dto.getStartTime() == null || dto.getEndTime() == null) {
			return;
		}
		// 勤務時間の妥当性を確認
		checkWorkTime(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (dto.getEndTime().before(dto.getStartTime())) {
			// エラーメッセージ設定
			addInvalidOrderMessage(mospParams.getName("StartWork"), mospParams.getName("EndWork"));
		}
	}
	
	/**
	 * 勤務時間の妥当性を確認する。<br>
	 * <br>
	 * 以下の場合に勤務時間が妥当でないと判断し、MosP処理情報にエラーメッセージを設定する。
	 * <br>
	 * ・勤務時間がマイナスである場合：<br>
	 * 妥当でない。<br>
	 * <br>
	 * ・勤務時間が0である場合：<br>
	 * 分単位休暇が0でなく全休チェックが付いていない場合、妥当でない。<br>
	 * <br>
	 * ・勤務時間が0でない場合：<br>
	 * 全休チェックが付いている場合、妥当でない。<br>
	 * <br>
	 * @param dto 勤怠情報
	 */
	protected void checkWorkTime(AttendanceDtoInterface dto) {
		// 勤務時間がマイナスの場合
		if (dto.getWorkTime() < 0) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_MINUS_WORK_TIME);
		}
		// 全休チェック取得
		boolean allAHoliday = dto.getMinutelyHolidayA() == getInteger(MospConst.CHECKBOX_ON);
		boolean allBHoliday = dto.getMinutelyHolidayB() == getInteger(MospConst.CHECKBOX_ON);
		// 勤務時間が0の場合
		if (dto.getWorkTime() == 0) {
			// 分単位休暇が0でなく全休チェックが付いていない場合
			if (allAHoliday == false && allBHoliday == false
					&& (dto.getMinutelyHolidayATime() > 0 || dto.getMinutelyHolidayBTime() > 0)) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorMinutelyAllHoliday(mospParams);
			}
		}
		// 勤務時間が0でない場合
		if (dto.getWorkTime() > 0) {
			if (allAHoliday || allBHoliday) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_MINUTELY_HOLIDAY);
			}
		}
	}
	
	@Override
	public void checkAttendance(AttendanceDtoInterface dto) throws MospException {
		AttendanceDtoInterface attendanceDto = dao.findForKey(dto.getPersonalId(), dto.getWorkDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
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
		if (PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 差戻の場合
			if (workflowDto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO) {
				// ワークフロー段階が0の場合
				return;
			}
		}
		if (dto.getWorkflow() == workflowDto.getWorkflow()) {
			// ワークフロー番号が同じ場合は同じ申請
			return;
		}
		TimeMessageUtility.addErrorAlreadyApplyWork(mospParams, dto.getWorkDate(), null);
	}
	
	@Override
	public void checkRequired(AttendanceDtoInterface dto) {
		// 始業時刻チェック
		checkRegistStartTimeForWork(dto);
		// 終業時刻チェック
		checkRegistEndTimeForWork(dto);
		// 遅刻理由チェック
		checkLateReason(dto);
		// 早退理由チェック
		checkLeaveEarlyReason(dto);
	}
	
	/**
	 * 申請時の始業時刻チェック。
	 * @param dto 対象DTO
	 */
	protected void checkRegistStartTimeForWork(AttendanceDtoInterface dto) {
		if (dto == null || dto.getStartTime() == null) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, getNameStartTimeForWork());
		}
	}
	
	/**
	 * 申請時の終業時刻チェック。
	 * @param dto 対象DTO
	 */
	protected void checkRegistEndTimeForWork(AttendanceDtoInterface dto) {
		if (dto == null || dto.getEndTime() == null) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, getNameEndTimeForWork());
		}
	}
	
	/**
	 * 申請時の遅刻理由チェック。
	 * @param dto 対象DTO
	 */
	protected void checkLateReason(AttendanceDtoInterface dto) {
		if (dto.getActualLateTime() == 0) {
			// 実遅刻時間が0の場合
			return;
		}
		if (dto.getLateReason().isEmpty()) {
			// 遅刻理由がない場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUIRED_SELECTION,
					DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Tardiness", "Reason"));
		}
	}
	
	/**
	 * 申請時の早退理由チェック。
	 * @param dto 対象DTO
	 */
	protected void checkLeaveEarlyReason(AttendanceDtoInterface dto) {
		if (dto.getActualLeaveEarlyTime() == 0) {
			// 実早退時間が0の場合
			return;
		}
		if (dto.getLeaveEarlyReason().isEmpty()) {
			// 早退理由がない場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUIRED_SELECTION,
					DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("LeaveEarly", "Reason"));
		}
	}
	
	/**
	 * 時間休のチェック。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkPaidLeaveTime(AttendanceDtoInterface dto) throws MospException {
		RequestUtilBeanInterface yesterdayRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		yesterdayRequestUtil.setRequests(dto.getPersonalId(), DateUtility.addDay(dto.getWorkDate(), -1));
		RequestUtilBeanInterface tomorrowRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		tomorrowRequestUtil.setRequests(dto.getPersonalId(), DateUtility.addDay(dto.getWorkDate(), 1));
		AttendanceDtoInterface yesterdayDto = yesterdayRequestUtil.getApplicatedAttendance();
		if (yesterdayDto == null) {
			yesterdayDto = yesterdayRequestUtil.getFirstRevertedAttendance();
		}
		if (yesterdayDto == null) {
			yesterdayDto = yesterdayRequestUtil.getDraftAttendance();
		}
		Date yesterdayEndTime = null;
		if (yesterdayDto != null) {
			yesterdayEndTime = yesterdayDto.getEndTime();
		}
		AttendanceDtoInterface tomorrowDto = tomorrowRequestUtil.getApplicatedAttendance();
		if (tomorrowDto == null) {
			tomorrowDto = tomorrowRequestUtil.getFirstRevertedAttendance();
		}
		if (tomorrowDto == null) {
			tomorrowDto = tomorrowRequestUtil.getDraftAttendance();
		}
		Date tomorrowStartTime = null;
		if (tomorrowDto != null) {
			tomorrowStartTime = tomorrowDto.getStartTime();
		}
		if (yesterdayEndTime != null || tomorrowStartTime != null) {
			// 休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface holidayDto : requestUtil.getHolidayList(true)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				if (yesterdayEndTime != null && holidayDto.getStartTime().before(yesterdayEndTime)) {
					// 前日の終業時刻より前の場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("Ahead"));
					sb.append(mospParams.getName("Day"));
					sb.append(mospParams.getName("Of"));
					sb.append(mospParams.getName("Work"));
					sb.append(mospParams.getName("Time"));
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("HolidayTime"),
							sb.toString());
					return;
				}
				if (tomorrowStartTime != null && holidayDto.getEndTime().after(tomorrowStartTime)) {
					// 翌日の始業時刻より後の場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("NextDay"));
					sb.append(mospParams.getName("Of"));
					sb.append(mospParams.getName("Work"));
					sb.append(mospParams.getName("Time"));
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("HolidayTime"),
							sb.toString());
					return;
				}
			}
		}
		if (dto.getStartTime() != null) {
			// 休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface holidayDto : yesterdayRequestUtil.getHolidayList(false)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				if (holidayDto.getEndTime().after(dto.getStartTime())) {
					// 始業時刻より後の場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("Ahead"));
					sb.append(mospParams.getName("Day"));
					sb.append(mospParams.getName("Of"));
					sb.append(mospParams.getName("HolidayTime"));
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Work", "Time"),
							sb.toString());
					return;
				}
			}
		}
		if (dto.getEndTime() != null) {
			// 休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface holidayDto : tomorrowRequestUtil.getHolidayList(false)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				if (holidayDto.getStartTime().before(dto.getEndTime())) {
					// 終業時刻より前の場合
					StringBuffer sb = new StringBuffer();
					sb.append(mospParams.getName("NextDay"));
					sb.append(mospParams.getName("Of"));
					sb.append(mospParams.getName("HolidayTime"));
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Work", "Time"),
							sb.toString());
					return;
				}
			}
		}
	}
	
	/**
	 * 時間休と時短時間の重複チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPaidLeaveTimeForShortTime(AttendanceDtoInterface dto) throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認されている場合
			return;
		}
		// 時差出勤申請が承認されていない場合
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), dto.getWorkDate());
		if (workTypeEntity == null) {
			return;
		}
		boolean isShort1TimeSet = workTypeEntity.isShort1TimeSet();
		Date short1StartTime = null;
		Date short1EndTime = null;
		if (isShort1TimeSet) {
			// 時短時間1が設定されている場合
			short1StartTime = getTime(workTypeEntity.getShort1StartTime(), dto.getWorkDate());
			short1EndTime = getTime(workTypeEntity.getShort1EndTime(), dto.getWorkDate());
		}
		boolean isShort2TimeSet = workTypeEntity.isShort2TimeSet();
		Date short2StartTime = null;
		Date short2EndTime = null;
		if (isShort2TimeSet) {
			// 時短時間2が設定されている場合
			short2StartTime = getTime(workTypeEntity.getShort2StartTime(), dto.getWorkDate());
			short2EndTime = getTime(workTypeEntity.getShort2EndTime(), dto.getWorkDate());
		}
		if (!isShort1TimeSet && !isShort2TimeSet) {
			// 時短時間が設定されていない場合
			return;
		}
		// 時短時間が設定されている場合
		for (HolidayRequestDtoInterface holidayDto : requestUtil.getHolidayList(true)) {
			if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休でない場合
				continue;
			}
			// 時間休である場合
			if (isShort1TimeSet
					&& checkDuplicationTimeZone(holidayDto.getStartTime(), holidayDto.getEndTime(), short1StartTime,
							short1EndTime)) {
				// 時短時間1と重複している場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK,
						DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("ShortTime", "Time", "No1"),
						mospParams.getName("HolidayTime"));
				return;
			}
			if (isShort2TimeSet
					&& checkDuplicationTimeZone(holidayDto.getStartTime(), holidayDto.getEndTime(), short2StartTime,
							short2EndTime)) {
				// 時短時間2と重複している場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK,
						DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("ShortTime", "Time", "No2"),
						mospParams.getName("HolidayTime"));
				return;
			}
		}
	}
	
	@Override
	public void checkPrivateGoOut(AttendanceDtoInterface dto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> privateList) throws MospException {
		if (dto.getStartTime() == null || dto.getEndTime() == null) {
			return;
		}
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		// 勤怠設定情報の取得
		setTimeSettings(dto.getPersonalId(), dto.getWorkDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		int regWorkStart = 0;
		int regWorkEnd = 0;
		boolean isWorkOnDayOff = false;
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(false);
		if (workOnHolidayRequestDto != null) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(workOnHolidayRequestDto
				.getWorkflow());
			if (workflowDto != null
					&& (workflowIntegrate.isApprovable(workflowDto) || workflowIntegrate.isCompleted(workflowDto))
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				isWorkOnDayOff = true;
				regWorkStart = getDefferenceMinutes(dto.getWorkDate(), dto.getStartTime());
				regWorkEnd = getDefferenceMinutes(dto.getWorkDate(), dto.getEndTime());
			}
		}
		boolean isDifferenceWork = false;
		DifferenceRequestDtoInterface differenceDto = requestUtil.getDifferenceDto(true);
		if (differenceDto != null) {
			// 時差出勤の場合
			isDifferenceWork = true;
			regWorkStart = getDefferenceMinutes(dto.getWorkDate(), differenceDto.getRequestStart());
			regWorkEnd = getDefferenceMinutes(dto.getWorkDate(), differenceDto.getRequestEnd());
		}
		if (!isWorkOnDayOff && !isDifferenceWork) {
			// 休日出勤でなく且つ時差出勤でない場合
			WorkTypeItemDtoInterface workStartDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(),
					dto.getWorkDate(), TimeConst.CODE_WORKSTART);
			WorkTypeItemDtoInterface workEndDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate(),
					TimeConst.CODE_WORKEND);
			if (workStartDto == null || workEndDto == null) {
				// エラーメッセージ設定
				addWorkTypeNotExistErrorMessage(dto.getWorkDate());
				return;
			}
			regWorkStart = getDefferenceMinutes(getDefaultStandardDate(), workStartDto.getWorkTypeItemValue());
			regWorkEnd = getDefferenceMinutes(getDefaultStandardDate(), workEndDto.getWorkTypeItemValue());
		}
		
		int workStart = getDefferenceMinutes(dto.getWorkDate(), dto.getStartTime());
		if (workStart < regWorkStart) {
			workStart = regWorkStart;
		}
		int workEnd = getDefferenceMinutes(dto.getWorkDate(), dto.getEndTime());
		if (workEnd > regWorkEnd) {
			workEnd = regWorkEnd;
		}
		
		// 休憩Map
		Map<Integer, Integer> restMap = new TreeMap<Integer, Integer>();
		for (RestDtoInterface restDto : restList) {
			int start = getDefferenceMinutes(dto.getWorkDate(), restDto.getRestStart());
			int end = getDefferenceMinutes(dto.getWorkDate(), restDto.getRestEnd());
			if (start >= regWorkEnd || end <= regWorkStart) {
				continue;
			}
			if (start < workStart) {
				start = workStart;
			}
			if (end > workEnd) {
				end = workEnd;
			}
			if (!restMap.containsKey(start) || restMap.get(start).intValue() < end) {
				restMap.put(start, end);
			}
		}
		
		// 私用外出Map
		Map<Integer, GoOutDtoInterface> privateMap = new TreeMap<Integer, GoOutDtoInterface>();
		for (GoOutDtoInterface privateDto : privateList) {
			int start = getDefferenceMinutes(dto.getWorkDate(), privateDto.getGoOutStart());
			int end = getDefferenceMinutes(dto.getWorkDate(), privateDto.getGoOutEnd());
			if (start >= regWorkEnd || end <= regWorkStart) {
				continue;
			}
			if (start < workStart) {
				start = workStart;
			}
			if (end > workEnd) {
				end = workEnd;
			}
			GoOutDtoInterface goOutDto = privateMap.get(start);
			if (goOutDto == null) {
				privateMap.put(start, privateDto);
			} else {
				int goOutEnd = getDefferenceMinutes(dto.getWorkDate(), goOutDto.getGoOutEnd());
				if (goOutEnd > workEnd) {
					goOutEnd = workEnd;
				}
				if (goOutEnd < end) {
					privateMap.put(start, privateDto);
				}
			}
		}
		
		// 限度時間
		int limitTime = getDefferenceMinutes(getDefaultStandardDate(), timeSettingDto.getLateEarlyHalf());
		// 休暇範囲
		int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
		// 代休範囲
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_AM && subHolidayRange != TimeConst.CODE_HOLIDAY_RANGE_AM
				&& dto.getLateTime() <= limitTime) {
			// 午前休でなく且つ遅刻時間が限度時間以下の場合
			int time = workStart;
			int privateTime = dto.getLateTime();
			while (privateTime <= limitTime) {
				int count = 0;
				for (Entry<Integer, GoOutDtoInterface> entry : privateMap.entrySet()) {
					GoOutDtoInterface privateDto = entry.getValue();
					int start = entry.getKey().intValue();
					int end = getDefferenceMinutes(dto.getWorkDate(), privateDto.getGoOutEnd());
					if (end > workEnd) {
						end = workEnd;
					}
					if (start == time && end > time) {
						privateTime += privateDto.getGoOutTime();
						time = end;
						count++;
					}
				}
				for (Entry<Integer, Integer> entry : restMap.entrySet()) {
					int start = entry.getKey().intValue();
					int end = entry.getValue().intValue();
					if (start == time && end > time) {
						time = end;
						count++;
					}
				}
				if (count == 0) {
					break;
				}
			}
			if (privateTime > limitTime) {
				// 遅刻時間 + 私用外出時間が遅刻早退限度時間を超える場合
				// メッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
						getStringDate(dto.getWorkDate()), mospParams.getName("PrivateGoingOut"),
						mospParams.getName("Tardiness"), mospParams.getName("AmRest"));
				return;
			}
		}
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_PM && subHolidayRange != TimeConst.CODE_HOLIDAY_RANGE_PM
				&& dto.getLeaveEarlyTime() <= limitTime) {
			// 午後休でなく且つ早退時間が限度時間以下の場合
			int time = workEnd;
			int privateTime = dto.getLeaveEarlyTime();
			while (privateTime <= limitTime) {
				int count = 0;
				for (Entry<Integer, GoOutDtoInterface> entry : privateMap.entrySet()) {
					GoOutDtoInterface privateDto = entry.getValue();
					int start = entry.getKey().intValue();
					int end = getDefferenceMinutes(dto.getWorkDate(), privateDto.getGoOutEnd());
					if (end > workEnd) {
						end = workEnd;
					}
					if (end == time && start < time) {
						privateTime += privateDto.getGoOutTime();
						time = start;
						count++;
					}
				}
				for (Entry<Integer, Integer> entry : restMap.entrySet()) {
					int start = entry.getKey().intValue();
					int end = entry.getValue().intValue();
					if (end == time && start < time) {
						time = start;
						count++;
					}
				}
				if (count == 0) {
					break;
				}
			}
			if (privateTime > limitTime) {
				// 早退時間 + 私用外出時間が遅刻早退限度時間を超える場合
				// メッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
						new String[]{ getStringDate(dto.getWorkDate()), mospParams.getName("PrivateGoingOut"),
							mospParams.getName("LeaveEarly"), mospParams.getName("PmRest") });
			}
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(AttendanceDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getWorkDate(), getNameWorkDate());
	}
	
	@Override
	public void checkApprover(AttendanceDtoInterface dto, WorkflowDtoInterface workDto) throws MospException {
		String approverId = workDto.getApproverId();
		// 自己承認以外
		if (PlatformConst.APPROVAL_ROUTE_SELF.equals(approverId)) {
			return;
		}
		String[] arrayApproverId = approverId.split(",");
		for (String element : arrayApproverId) {
			// 対象データが1件しかない場合のチェック
			if (element.isEmpty()) {
				if (0 >= arrayApproverId.length) {
					mospParams.addErrorMessage(PlatformMessageConst.MSG_NOT_EXIST_AND_CHANGE_SOMETHING,
							mospParams.getName("Approver"), getNameWorkDate());
				}
			} else {
				// 入社チェック
				if (!entranceReference.isEntered(element, dto.getWorkDate())) {
					mospParams.addErrorMessage(PlatformMessageConst.MSG_NOT_EXIST_AND_CHANGE_SOMETHING,
							mospParams.getName("Approver"), getNameWorkDate());
				}
				// 退社チェック
				if (retirementReference.isRetired(element, dto.getWorkDate())) {
					mospParams.addErrorMessage(PlatformMessageConst.MSG_NOT_EXIST_AND_CHANGE_SOMETHING,
							mospParams.getName("Approver"), getNameWorkDate());
				}
			}
		}
	}
	
	/**
	 * 翌日の勤怠チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkTomorrowAttendance(AttendanceDtoInterface dto) throws MospException {
		RequestUtilBeanInterface tomorrowRequestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		tomorrowRequestUtil.setRequests(dto.getPersonalId(), addDay(dto.getWorkDate(), 1));
		AttendanceDtoInterface tomorrowDto = tomorrowRequestUtil.getApplicatedAttendance();
		if (tomorrowDto == null || tomorrowDto.getStartTime() == null) {
			return;
		}
		if (tomorrowDto.getStartTime().before(dto.getEndTime())) {
			// 翌日の始業時刻が当日の終業時刻より前の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_TOMORROW_WORK_BEGIN,
					new String[]{ getStringDate(dto.getWorkDate()) });
		}
	}
	
	/**
	 * 代休申請しているか確認する。<br>
	 * @param dto 対象DTO
	 * @param operationName 操作名称
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubHoliday(AttendanceDtoInterface dto, String operationName) throws MospException {
		// 法定代休
		List<SubHolidayRequestDtoInterface> legalList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : legalList) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(subHolidayRequestDto
				.getWorkflow());
			if (workflowIntegrate.isDraft(workflowDto) || workflowIntegrate.isWithDrawn(workflowDto)) {
				// 下書又は取下の場合
				continue;
			}
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
		// 所定代休
		List<SubHolidayRequestDtoInterface> prescribedList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : prescribedList) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(subHolidayRequestDto
				.getWorkflow());
			if (workflowIntegrate.isDraft(workflowDto) || workflowIntegrate.isWithDrawn(workflowDto)) {
				// 下書又は取下の場合
				continue;
			}
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
		// 深夜代休
		List<SubHolidayRequestDtoInterface> nightList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : nightList) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(subHolidayRequestDto
				.getWorkflow());
			if (workflowIntegrate.isDraft(workflowDto) || workflowIntegrate.isWithDrawn(workflowDto)) {
				// 下書又は取下の場合
				continue;
			}
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
	}
	
	/**
	 * 代休が申請されている場合、エラーメッセージを追加する。<br>
	 * @param operationName 操作名称
	 */
	protected void addSubHolidayRequestedErrorMessage(String operationName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
				mospParams.getName("CompensatoryHoliday"), operationName);
	}
	
	@Override
	public void delete(String personalId, Date workDate) throws MospException {
		// 対象の勤怠日を取得
		AttendanceDtoInterface dto = dao.findForKey(personalId, workDate, TIMES_WORK_DEFAULT);
		if (dto == null) {
			return;
		}
		// ワークフローの削除
		workflowRegist.delete(workflowReference.getLatestWorkflowInfo(dto.getWorkflow()));
		// ワークフローコメントの削除
		workflowCommentRegist.deleteList(workflowCommentReference.getWorkflowCommentList(dto.getWorkflow()));
		// 休憩の削除
		restRegist.delete(personalId, workDate, TIMES_WORK_DEFAULT);
		// 外出の削除
		goOutRegist.delete(personalId, workDate, TIMES_WORK_DEFAULT);
		// 修正履歴の削除
		attendanceCorrectionRegist.delete(personalId, workDate, TIMES_WORK_DEFAULT);
		// 勤怠の削除
		delete(dto);
		// 代休データの削除
		subHolidayRegist.delete(personalId, workDate);
	}
	
	@Override
	public void delete(AttendanceDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getTmdAttendanceId());
	}
	
	/**
	 * 勤務日名称を取得する。
	 * @return 勤務日名称
	 */
	protected String getNameWorkDate() {
		return mospParams.getName("Work", "Day");
	}
	
	/**
	 * 始業時刻名称を取得する。
	 * @return 始業時刻名称
	 */
	protected String getNameStartTimeForWork() {
		return mospParams.getName("StartWork", "Moment");
	}
	
	/**
	 * 終業時刻名称を取得する。
	 * @return 終業時刻名称
	 */
	protected String getNameEndTimeForWork() {
		return mospParams.getName("EndWork", "Moment");
	}
	
}
