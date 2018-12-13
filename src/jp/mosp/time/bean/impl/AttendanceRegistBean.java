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
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
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
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠データ登録クラス。
 */
public class AttendanceRegistBean extends TimeApplicationBean implements AttendanceRegistBeanInterface {
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface				dao;
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
	protected CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface				requestUtil;
	
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
		holidayRequestReference = (HolidayRequestReferenceBeanInterface)createBean(
				HolidayRequestReferenceBeanInterface.class);
		workflowReference = (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
		overtimeDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		differenceReference = (DifferenceRequestReferenceBeanInterface)createBean(
				DifferenceRequestReferenceBeanInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		subHolidayDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		workflowCommentReference = (WorkflowCommentReferenceBeanInterface)createBean(
				WorkflowCommentReferenceBeanInterface.class);
		attendanceCorrectionRegist = (AttendanceCorrectionRegistBeanInterface)createBean(
				AttendanceCorrectionRegistBeanInterface.class);
		restRegist = (RestRegistBeanInterface)createBean(RestRegistBeanInterface.class);
		goOutRegist = (GoOutRegistBeanInterface)createBean(GoOutRegistBeanInterface.class);
		subHolidayRegist = (SubHolidayRegistBeanInterface)createBean(SubHolidayRegistBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(
				WorkflowCommentRegistBeanInterface.class);
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
	public void validate(AttendanceDtoInterface dto) {
		// 妥当性確認
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
		// 分単位休暇が有効の場合
		if (mospParams.getApplicationPropertyBool("UseMinutelyHoliday")) {
			// 分単位休暇全休チェック
			checkMinutelyHoliday(dto);
		}
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
		if (dto.getMinutelyHolidayA() != TimeConst.CODE_HOLIDAY_RANGE_ALL
				&& dto.getMinutelyHolidayB() != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 遅刻早退の限度チェック
			checkLateEarlyTime(dto);
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
		// 仮締め確認
		checkTemporaryClosingFinal(dto);
		// 代休チェック
		checkSubHoliday(dto, mospParams.getName("Release"));
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
	public void checkLateEarlyTime(AttendanceDtoInterface dto) throws MospException {
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date workDate = dto.getWorkDate();
		// 勤怠設定情報の取得
		setTimeSettings(personalId, workDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 各種申請を取得
		requestUtil.setRequests(personalId, workDate);
		// 休暇申請情報取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(false);
		// 代休申請情報取得
		List<SubHolidayRequestDtoInterface> subHolidayList = requestUtil.getSubHolidayList(false);
		// 遅刻限度時間を超過している場合
		if (checkLateLimit(dto, timeSettingDto)) {
			// 午前休取得確認
			checkLateTime(workDate, holidayList, subHolidayList);
		}
		// 早退限度時間を超えている場合
		if (checkLeaveEarlyLimit(dto, timeSettingDto)) {
			// 午後休取得確認
			checkLeaveEarlyTime(workDate, holidayList, subHolidayList);
		}
	}
	
	/**
	 * 遅刻限度時間を超えている時に午前休を取得しているか確認する。<br>
	 * 午前休を取得していない場合、取得を促すメッセージを追加する。<br>
	 * @param workDate 勤務日
	 * @param holidayList 下書、取下以外の休暇申請情報リスト
	 * @param subHolidayList 下書、取下以外の代休申請情報リスト
	 */
	public void checkLateTime(Date workDate, List<HolidayRequestDtoInterface> holidayList,
			List<SubHolidayRequestDtoInterface> subHolidayList) {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayList) {
			// 午前休の場合
			if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				return;
			}
		}
		// 代休申請情報毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayList) {
			// 午前休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				return;
			}
		}
		// メッセージ設定
		getArdinessTardinessLimitOver(workDate);
	}
	
	/**
	 * 早退限度時間を超えている時に午後休を取得しているか確認する。<br>
	 * 午後休を取得していない場合、取得を促すメッセージを追加する。<br>
	 * @param workDate 勤務日
	 * @param holidayList 下書、取下以外の休暇申請情報リスト
	 * @param subHolidayList 下書、取下以外の代休申請情報リスト
	 */
	protected void checkLeaveEarlyTime(Date workDate, List<HolidayRequestDtoInterface> holidayList,
			List<SubHolidayRequestDtoInterface> subHolidayList) {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayList) {
			// 1件でも午後休がある場合
			if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// エラーメッセージ無し
				return;
			}
		}
		// 代休申請情報毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayList) {
			// 1件でも午後休がある場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// エラーメッセージ無し
				return;
			}
		}
		// メッセージ設定
		getLeaveEarlyLimitOver(workDate);
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
	 * @throws MospException アドオンで例外が発生した場合
	 */
	@Override
	public void checkAttendanceCardDraft(AttendanceDtoInterface dto) throws MospException {
		// 処理無し(アドオンで実装)
	}
	
	/**
	 * @throws MospException アドオンで例外が発生した場合
	 */
	@Override
	public void checkAttendanceCardAppli(AttendanceDtoInterface dto) throws MospException {
		// 処理無し(アドオンで実装)
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
			mospParams.addErrorMessage(TimeMessageConst.MSG_START_DAY_TIME_CHECK, workDate,
					DateUtility.getStringTime(
							DateUtility.addHour(DateUtility.getDefaultTime(), TimeConst.TIME_DAY_ALL_HOUR),
							DateUtility.getDefaultTime()),
					getNameStartTimeForWork());
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
		String rep = mospParams.getName("Ahead", "Day", "Kara");
		mospParams.addErrorMessage(TimeMessageConst.MSG_START_TIME_CHECK,
				new String[]{ workDate, rep, DateUtility.getStringTime(timeSettingDto.getStartDayTime()),
					DateUtility.getStringTime(
							DateUtility.addHour(timeSettingDto.getStartDayTime(), TimeConst.TIME_DAY_ALL_HOUR),
							DateUtility.getDefaultTime()) });
	}
	
	/**
	 * 下書時の終業時刻のチェック。<br>
	 * 翌日の起算時刻を取得し整合性の確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkDraftEndTimeForWork(AttendanceDtoInterface dto) throws MospException {
		// 終業時刻が入力されていない場合
		if (dto.getEndTime() == null) {
			// チェックなし
			return;
		}
		String workDate = getStringDate(dto.getWorkDate());
		Date tomorrowDate = addDay(dto.getWorkDate(), 1);
		// 翌日の起算時間取得
		Date afterTwentyFourHoursTime = getTime(timeSettingDto.getStartDayTime(), tomorrowDate);
		String afterTwentyFourHours = DateUtility.getStringTime(afterTwentyFourHoursTime, dto.getWorkDate());
		// 終業時刻が一日の起算時刻の24時間後より後の場合
		if (dto.getEndTime().compareTo(afterTwentyFourHoursTime) > 0) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_START_DAY_TIME_CHECK, workDate, afterTwentyFourHours,
					getNameEndTimeForWork());
		}
		// 翌日の申請済勤怠情報取得
		RequestUtilBeanInterface tomorrowRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		tomorrowRequestUtil.setRequests(dto.getPersonalId(), tomorrowDate);
		AttendanceDtoInterface tomorrowDto = tomorrowRequestUtil.getApplicatedAttendance();
		// 翌日に勤怠情報が存在しない場合
		if (tomorrowDto == null || tomorrowDto.getStartTime() == null) {
			return;
		}
		// 終業時刻が翌日の始業時刻より前の場合
		if (dto.getEndTime().compareTo(tomorrowDto.getStartTime()) < 0) {
			return;
		}
		// 終業時刻が翌日の始業時刻と同じの場合
		if (dto.getEndTime().equals(tomorrowDto.getStartTime())) {
			// 終業時刻が一日の起算時刻の24時間後の場合
			if (afterTwentyFourHoursTime.equals(dto.getEndTime())) {
				return;
			}
		}
		// 終業時刻が翌日の始業時刻以降の場合
		mospParams.addErrorMessage(TimeMessageConst.MSG_END_TIME_CHECK);
	}
	
	/**
	 * 勤怠設定の遅刻限度時間を超えていないか確認する。<br>
	 * @param dto 勤怠データ
	 * @param timeSettingDto 勤怠設定
	 * @return 確認結果(true：限度時間を超えている、false：限度時間を超えていない)
	 */
	public boolean checkLateLimit(AttendanceDtoInterface dto, TimeSettingDtoInterface timeSettingDto) {
		// 勤怠設定遅刻限度時間（分）取得
		int lateEarlyHalf = (DateUtility.getHour(timeSettingDto.getLateEarlyHalf()) * TimeConst.CODE_DEFINITION_HOUR)
				+ DateUtility.getMinute(timeSettingDto.getLateEarlyHalf());
		// 遅刻限度時間を超えている場合
		if (lateEarlyHalf < dto.getLateTime()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 勤怠設定の早退限度時間を超えていないか確認する。<br>
	 * @param dto 勤怠データ
	 * @param timeSettingDto 勤怠設定
	 * @return 確認意結果(true：限度時間を超えている、false：限度時間を超えていない)
	 */
	public boolean checkLeaveEarlyLimit(AttendanceDtoInterface dto, TimeSettingDtoInterface timeSettingDto) {
		// 勤怠設定早退限度時間（分）取得
		int lateEarlyHalf = (DateUtility.getHour(timeSettingDto.getLateEarlyHalf()) * TimeConst.CODE_DEFINITION_HOUR)
				+ DateUtility.getMinute(timeSettingDto.getLateEarlyHalf());
		// 早退限度時間を超えている場合
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
		
		// 未承認仮締取得
		int noApproval = cutoffDto.getNoApproval();
		// 未承認仮締が無効（残業事前申請のみ）でない場合
		if (noApproval != 2) {
			return;
		}
		// 前後残業確認
		boolean overTimeBefore = dto.getOvertimeBefore() > 0;
		boolean overTimeAfter = dto.getOvertimeAfter() > 0;
		// 前残業かつ後残業時間がない場合
		if (!overTimeBefore && !overTimeAfter) {
			return;
		}
		// 承認済振出・休出申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		// 振出・休出申請があり振替申請しない場合
		if (workOnHolidayRequestDto != null
				&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return;
		}
		// 承認済残業申請リスト取得
		List<OvertimeRequestDtoInterface> overtimeRequestList = requestUtil.getOverTimeList(true);
		// 承認済残業申請毎に処理
		for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
			// 残業申請区分取得
			int type = overtimeRequestDto.getOvertimeType();
			// 勤務前残業申請があり承認済の場合
			if (overTimeBefore && type == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
				// 勤務前残業申請がされている
				overTimeBefore = false;
				continue;
			}
			// 勤務後残業申請があり承認済の場合
			if (overTimeAfter && type == TimeConst.CODE_OVERTIME_WORK_AFTER) {
				// 勤務後残業時間
				overTimeAfter = false;
			}
		}
		// 前残と後残が全て申請している場合
		if (!overTimeBefore && !overTimeAfter) {
			return;
		}
		// エラーメッセージ追加(残業申請してください。)
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
		// 残業申請リストを取得
		List<OvertimeRequestDtoInterface> overtimeList = overtimeDao.findForList(personalId, requestDate);
		// 残業申請毎に処置
		for (OvertimeRequestDtoInterface requestDto : overtimeList) {
			// 最新のワークフロー取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			// 承認状況が取下の場合
			if (WorkflowUtility.isWithDrawn(workflowDto)) {
				continue;
			}
			// 承認状況が下書の場合
			if (WorkflowUtility.isDraft(workflowDto)) {
				continue;
			}
			// 承認状況が承認済でない場合
			if (!WorkflowUtility.isCompleted(workflowDto)) {
				// エラーメッセージの表示
				// TODO 残業申請のメッセージ追加
				requestName = mospParams.getName("OvertimeWork", "Application");
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE, getStringDate(requestDate),
						requestName);
			}
		}
		// 時差出勤申請を取得
		DifferenceRequestDtoInterface differenceDto = differenceReference.findForKeyOnWorkflow(personalId, requestDate);
		if (differenceDto != null) {
			// 最新のワークフロー取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceDto.getWorkflow());
			if (workflowDto != null) {
				// 承認状況が取下でなく、下書でなく、承認済でない場合
				if (!WorkflowUtility.isWithDrawn(workflowDto) && !WorkflowUtility.isDraft(workflowDto)
						&& !WorkflowUtility.isCompleted(workflowDto)) {
					// エラーメッセージの表示
					// 時差出勤申請のメッセージ追加
					requestName = mospParams.getName("TimeDifference", "GoingWork", "Application");
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
							getStringDate(requestDate), requestName);
				}
			}
		}
		// 休暇申請（半休）
		List<HolidayRequestDtoInterface> holidayList = holidayRequestReference.getHolidayRequestList(personalId,
				requestDate);
		for (HolidayRequestDtoInterface requestDto : holidayList) {
			// 休暇範囲取得
			int holidayRange = requestDto.getHolidayRange();
			// 休暇が午前休又は午後休又は時間休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
				// 承認状況が取下又は下書の場合
				if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)) {
					continue;
				}
				// 承認状況が承認済でない場合
				if (!WorkflowUtility.isCompleted(workflowDto)) {
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
			// 振替日が午前休又は午後休の場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// ワークフロ情報取得
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
				// 承認状況が取下の場合
				if (WorkflowUtility.isWithDrawn(workflowDto)) {
					continue;
				}
				// 承認状況が下書の場合
				if (WorkflowUtility.isDraft(workflowDto)) {
					continue;
				}
				// 承認状況が承認済でない場合
				if (!WorkflowUtility.isCompleted(workflowDto)) {
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
				// 承認状況が取下の場合
				if (WorkflowUtility.isWithDrawn(workflowDto)) {
					continue;
				}
				// 承認状況が下書の場合
				if (WorkflowUtility.isDraft(workflowDto)) {
					continue;
				}
				// 承認状況が承認済でない場合
				if (!WorkflowUtility.isCompleted(workflowDto)) {
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
		// 法定休日労働又は所定休日労働の場合
		if (TimeUtility.isWorkOnLegalHoliday(dto.getWorkTypeCode())
				|| TimeUtility.isWorkOnPrescribedHoliday(dto.getWorkTypeCode())) {
			return true;
		}
		// 勤務開始時刻・終了時刻取得
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		// 時差出勤申請が承認済の場合
		if (differenceDto != null && workflowIntegrate.isCompleted(differenceDto.getWorkflow())) {
			// 始業・就業時刻設定
			startTime = differenceDto.getRequestStart();
			endTime = differenceDto.getRequestEnd();
		} else {
			// 時差出勤申請がない場合
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
		// 休日出勤申請情報取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getWorkDate());
		// 休日出勤申請がない又は承認済の場合
		if (workOnHolidayRequestDto == null || workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
			return;
		}
		// エラーメッセージ追加
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE_2, getStringDate(dto.getWorkDate()),
				mospParams.getName("jp.mosp.time.input.vo.WorkOnHolidayRequestVo"));
	}
	
	/**
	 * 振出・休出申請及び振替休日情報から出勤日であるか判断する。
	 * @return 確認結果（true：出勤日である、false：出勤日でない）
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean isWorkDayForWorkOnHoliday() throws MospException {
		// 振出休出申請（承認済）がある場合
		if (requestUtil.getWorkOnHolidayDto(true) != null) {
			// 振出又は休出
			return true;
		}
		// 振替休日情報（全休）がある場合
		if (requestUtil
			.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(false)) == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			return false;
		}
		return true;
	}
	
	/**
	 * 休暇申請チェック。<br>
	 * 休暇、振替休日、代休が申請されている場合、エラーメッセージを設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(AttendanceDtoInterface dto) throws MospException {
		// 出勤日でない場合（振出・休出申請及び振替休日情報で判断）
		if (isWorkDayForWorkOnHoliday() == false) {
			// 振出・休出でない場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10, getStringDate(dto.getWorkDate()),
					mospParams.getName("ObservedHoliday"), mospParams.getName("WorkManage", "Application"));
			return;
		}
		// 休暇範囲フラグ
		boolean holidayAm = false;
		boolean holidayPm = false;
		// 承認済休日出勤申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		// 振出・休出申請がない又は全日振替申請の場合
		if (workOnHolidayRequestDto == null
				|| workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 取下、下書以外休暇申請の休暇範囲取得
			int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
			// 全休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// エラーメッセージ設定
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
		// 代休範囲フラグ
		boolean subHolidayAm = false;
		boolean subHolidayPm = false;
		// 取下、下書以外代休申請情報の範囲取得
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		// 全休の場合
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// エラーメッセージ設定
			String[] rep = { getStringDate(dto.getWorkDate()), mospParams.getName("CompensatoryHoliday"),
				mospParams.getName("WorkManage", "Application") };
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10, rep);
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
		// 振替休日範囲フラグ
		boolean substituteAm = false;
		boolean substitutePm = false;
		int substituteRange = getSubstituteRange(dto);
		// 全休の場合
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// エラーメッセージ設定
			String[] rep = { getStringDate(dto.getWorkDate()), mospParams.getName("ObservedHoliday"),
				mospParams.getName("WorkManage", "Application") };
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10, rep);
			return;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 午前休の場合
			substituteAm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午後休の場合
			substitutePm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休且つ午後休の場合
			substituteAm = true;
			substitutePm = true;
		}
		// 午前休且つ午後休の場合
		if ((holidayAm || subHolidayAm || substituteAm) && (holidayPm || subHolidayPm || substitutePm)) {
			// エラーメッセージ設定
			String[] rep = { getStringDate(dto.getWorkDate()), mospParams.getName("WorkManage", "Application") };
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_13, rep);
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
	 * 始業・終業・勤務時刻の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 */
	public void checkTimeValidity(AttendanceDtoInterface dto) {
		// 始業時刻・終業時刻取得
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		// 始業時刻がない又は終業時刻がない場合
		if (startTime == null || endTime == null) {
			return;
		}
		// 勤務時間の妥当性を確認
		checkWorkTime(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 始業時刻の前に終業時刻又は同じ場合
		if (startTime.compareTo(endTime) >= 0) {
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
		// 勤怠情報取得
		AttendanceDtoInterface attendanceDto = dao.findForKey(dto.getPersonalId(), dto.getWorkDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		// 取下の場合
		if (WorkflowUtility.isWithDrawn(workflowDto)) {
			return;
		}
		// 下書の場合
		if (WorkflowUtility.isDraft(workflowDto)) {
			return;
		}
		// 差戻の場合
		if (PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// ワークフロー段階が0の場合
			if (workflowDto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO) {
				return;
			}
		}
		// ワークフロー番号が同じ場合
		if (dto.getWorkflow() == workflowDto.getWorkflow()) {
			return;
		}
		// 重複エラーメッセージ追加
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
		// 勤怠情報がないまたは始業時刻がない場合
		if (dto == null || dto.getStartTime() == null) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, getNameStartTimeForWork());
		}
	}
	
	/**
	 * 申請時の終業時刻チェック。
	 * @param dto 対象DTO
	 */
	protected void checkRegistEndTimeForWork(AttendanceDtoInterface dto) {
		// 勤怠情報がないまたは終業時刻がない場合
		if (dto == null || dto.getEndTime() == null) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, getNameEndTimeForWork());
		}
	}
	
	/**
	 * 申請時の遅刻理由チェック。<br>
	 * 遅刻時間が発生し遅刻理由がない場合、メッセージで入力を促す。<br>
	 * @param dto 対象DTO
	 */
	protected void checkLateReason(AttendanceDtoInterface dto) {
		// 実遅刻時間が0の場合
		if (dto.getActualLateTime() == 0) {
			return;
		}
		// 遅刻理由がない場合
		if (dto.getLateReason().isEmpty()) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUIRED_SELECTION,
					DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Tardiness", "Reason"));
		}
	}
	
	/**
	 * 申請時の早退理由チェック。<br>
	 * 早退時間が発生し早退理由がない場合、メッセージで入力を促す。<br>
	 * @param dto 対象DTO
	 */
	protected void checkLeaveEarlyReason(AttendanceDtoInterface dto) {
		// 実早退時間が0の場合
		if (dto.getActualLeaveEarlyTime() == 0) {
			return;
		}
		// 早退理由がない場合
		if (dto.getLeaveEarlyReason().isEmpty()) {
			// エラーメッセージ追加
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
		// 昨日申請ユーティリティクラス取得
		RequestUtilBeanInterface yesterdayRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		yesterdayRequestUtil.setRequests(dto.getPersonalId(), DateUtility.addDay(dto.getWorkDate(), -1));
		// 明日申請ユーティリティクラス取得
		RequestUtilBeanInterface tomorrowRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		tomorrowRequestUtil.setRequests(dto.getPersonalId(), DateUtility.addDay(dto.getWorkDate(), 1));
		// 昨日勤怠情報取得
		AttendanceDtoInterface yesterdayDto = yesterdayRequestUtil.getApplicatedAttendance();
		// 昨日勤怠情報がない場合
		if (yesterdayDto == null) {
			// 昨日勤怠申請情報(1次戻)を取得
			yesterdayDto = yesterdayRequestUtil.getFirstRevertedAttendance();
		}
		// 昨日勤怠情報がない場合
		if (yesterdayDto == null) {
			// 昨日勤怠申請情報(下書)を取得
			yesterdayDto = yesterdayRequestUtil.getDraftAttendance();
		}
		// 昨日終業時刻準備
		Date yesterdayEndTime = null;
		// 昨日勤怠情報がある場合
		if (yesterdayDto != null) {
			// 昨日終業時刻設定
			yesterdayEndTime = yesterdayDto.getEndTime();
		}
		// 明日勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface tomorrowDto = tomorrowRequestUtil.getApplicatedAttendance();
		// 明日勤怠情報がない場合
		if (tomorrowDto == null) {
			// 明日勤怠申請情報(1次戻)を取得
			tomorrowDto = tomorrowRequestUtil.getFirstRevertedAttendance();
		}
		// 明日勤怠情報がない場合
		if (tomorrowDto == null) {
			// 明日勤怠申請情報(下書)を取得
			tomorrowDto = tomorrowRequestUtil.getDraftAttendance();
		}
		// 明日始業時刻準備
		Date tomorrowStartTime = null;
		// 明日勤怠情報がある場合
		if (tomorrowDto != null) {
			// 明日始業時刻設定
			tomorrowStartTime = tomorrowDto.getStartTime();
		}
		// 昨日終業時刻がある又は明日始業時刻がある場合
		if (yesterdayEndTime != null || tomorrowStartTime != null) {
			// 休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface holidayDto : requestUtil.getHolidayList(true)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				// 前日の終業時刻より前の場合
				if (yesterdayEndTime != null && holidayDto.getStartTime().before(yesterdayEndTime)) {
					// エラーメッセージ追加
					String rep = mospParams.getName("Ahead", "Day", "Of", "Work", "Time");
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("HolidayTime"), rep);
					return;
				}
				// 翌日の始業時刻より後の場合
				if (tomorrowStartTime != null && holidayDto.getEndTime().after(tomorrowStartTime)) {
					// エラーメッセージ追加
					String rep = mospParams.getName("NextDay", "Of", "Work", "Time");
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("HolidayTime"), rep);
					return;
				}
			}
		}
		// 始業時刻がある場合
		if (dto.getStartTime() != null) {
			// 昨日の休暇申請リスト（取下、下書以外）毎に処理
			for (HolidayRequestDtoInterface holidayDto : yesterdayRequestUtil.getHolidayList(false)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				// 始業時刻より後の場合
				if (holidayDto.getEndTime().after(dto.getStartTime())) {
					// エラーメッセージ追加
					String rep = mospParams.getName("Ahead", "Day", "Of", "HolidayTime");
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Work", "Time"), rep);
					return;
				}
			}
		}
		// 終業時刻がある場合
		if (dto.getEndTime() != null) {
			// 明日の休暇申請リスト（取下、下書以外）毎に処理
			for (HolidayRequestDtoInterface holidayDto : tomorrowRequestUtil.getHolidayList(false)) {
				// 時間休でない場合
				if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					continue;
				}
				// 終業時刻より前の場合
				if (holidayDto.getStartTime().before(dto.getEndTime())) {
					// エラーメッセージ追加
					String rep = mospParams.getName("NextDay", "Of", "HolidayTime");
					mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK_3,
							DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("Work", "Time"), rep);
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
		// 時差出勤申請情報で対象承認状況の情報を取得
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		// 時差出勤申請が承認されている場合
		if (differenceRequestDto != null) {
			return;
		}
		// 時差出勤申請が承認されていない場合
		// 勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), dto.getWorkDate());
		if (workTypeEntity == null) {
			return;
		}
		// 時短時間1が設定されているかを確認
		boolean isShort1TimeSet = workTypeEntity.isShort1TimeSet();
		Date short1StartTime = null;
		Date short1EndTime = null;
		// 時短時間1が設定されている場合
		if (isShort1TimeSet) {
			short1StartTime = getTime(workTypeEntity.getShort1StartTime(), dto.getWorkDate());
			short1EndTime = getTime(workTypeEntity.getShort1EndTime(), dto.getWorkDate());
		}
		boolean isShort2TimeSet = workTypeEntity.isShort2TimeSet();
		Date short2StartTime = null;
		Date short2EndTime = null;
		// 時短時間2が設定されている場合
		if (isShort2TimeSet) {
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
			if (isShort1TimeSet && checkDuplicationTimeZone(holidayDto.getStartTime(), holidayDto.getEndTime(),
					short1StartTime, short1EndTime)) {
				// 時短時間1と重複している場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK,
						DateUtility.getStringDate(dto.getWorkDate()), mospParams.getName("ShortTime", "Time", "No1"),
						mospParams.getName("HolidayTime"));
				return;
			}
			if (isShort2TimeSet && checkDuplicationTimeZone(holidayDto.getStartTime(), holidayDto.getEndTime(),
					short2StartTime, short2EndTime)) {
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
		// 始業時刻又は終業時刻がない場合
		if (dto.getStartTime() == null || dto.getEndTime() == null) {
			return;
		}
		// 各種申請取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		// 勤怠設定情報の取得
		setTimeSettings(dto.getPersonalId(), dto.getWorkDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 値準備
		int regWorkStart = 0;
		int regWorkEnd = 0;
		boolean isWorkOnDayOff = false;
		// 休日出勤申請情報(取下・下書以外)を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(false);
		if (workOnHolidayRequestDto != null) {
			// 最新のワークフロー取得
			WorkflowDtoInterface workflowDto = workflowIntegrate
				.getLatestWorkflowInfo(workOnHolidayRequestDto.getWorkflow());
			// 承認可能又は承認済かつ休日出勤申請の場合
			if (workflowDto != null
					&& (workflowIntegrate.isApprovable(workflowDto) || WorkflowUtility.isCompleted(workflowDto))
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 差を取得
				isWorkOnDayOff = true;
				regWorkStart = getDefferenceMinutes(dto.getWorkDate(), dto.getStartTime());
				regWorkEnd = getDefferenceMinutes(dto.getWorkDate(), dto.getEndTime());
			}
		}
		boolean isDifferenceWork = false;
		// 時差出勤申請情報(承認済)を取得
		DifferenceRequestDtoInterface differenceDto = requestUtil.getDifferenceDto(true);
		// 時差出勤が存在する場合
		if (differenceDto != null) {
			isDifferenceWork = true;
			regWorkStart = getDefferenceMinutes(dto.getWorkDate(), differenceDto.getRequestStart());
			regWorkEnd = getDefferenceMinutes(dto.getWorkDate(), differenceDto.getRequestEnd());
		}
		// 休日出勤でなく且つ時差出勤でない場合
		if (!isWorkOnDayOff && !isDifferenceWork) {
			// 勤務形態(出勤時刻)取得
			WorkTypeItemDtoInterface workStartDto = workTypeItemDao.findForInfo(dto.getWorkTypeCode(),
					dto.getWorkDate(), TimeConst.CODE_WORKSTART);
			// 勤務形態(退勤時刻)取得
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
			// 遅刻時間 + 私用外出時間が遅刻早退限度時間を超える場合
			if (privateTime > limitTime) {
				// メッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
						getStringDate(dto.getWorkDate()), mospParams.getName("PrivateGoingOut"),
						mospParams.getName("Tardiness"), mospParams.getName("AmRest"));
				return;
			}
		}
		// 休暇申請が午後休でなく代休が午後休でなく早退時間が限度時間以下の場合
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_PM && subHolidayRange != TimeConst.CODE_HOLIDAY_RANGE_PM
				&& dto.getLeaveEarlyTime() <= limitTime) {
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
			// 早退時間 + 私用外出時間が遅刻早退限度時間を超える場合
			if (privateTime > limitTime) {
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
		// 承認者個人ID取得
		String approverId = workDto.getApproverId();
		// 自己承認以外
		if (PlatformConst.APPROVAL_ROUTE_SELF.equals(approverId)) {
			return;
		}
		// 承認者個人ID配列取得
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
				if (!isEntered(element, dto.getWorkDate())) {
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
		// 明日申請ユーティリティクラス取得
		RequestUtilBeanInterface tomorrowRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		tomorrowRequestUtil.setRequests(dto.getPersonalId(), addDay(dto.getWorkDate(), 1));
		// 明日勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface tomorrowDto = tomorrowRequestUtil.getApplicatedAttendance();
		if (tomorrowDto == null || tomorrowDto.getStartTime() == null) {
			return;
		}
		// 翌日の始業時刻が当日の終業時刻より前の場合
		if (tomorrowDto.getStartTime().before(dto.getEndTime())) {
			// エラーメッセージ追加
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
		// 法定代休申請データリストを取得
		List<SubHolidayRequestDtoInterface> legalList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE);
		// 代休申請存在確認
		if (isAppliSubHoliday(legalList)) {
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
		// 所定代休申請データリストを取得
		List<SubHolidayRequestDtoInterface> prescribedList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE);
		// 代休申請存在確認
		if (isAppliSubHoliday(prescribedList)) {
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
		// 深夜代休申請データリストを取得
		List<SubHolidayRequestDtoInterface> nightList = subHolidayDao.findForList(dto.getPersonalId(),
				dto.getWorkDate(), dto.getTimesWork(), TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE);
		// 代休申請存在確認
		if (isAppliSubHoliday(nightList)) {
			// エラーメッセージ
			addSubHolidayRequestedErrorMessage(operationName);
			return;
		}
	}
	
	/**
	 * 取下、下書、1次戻以外の振替休日申請の休暇範囲取得。<br>
	 * 対象日に申請済の振替出勤申請があった場合は休暇範囲から除外する。
	 * @param dto 対象DTO
	 * @return 振替休日の休暇範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	int getSubstituteRange(AttendanceDtoInterface dto) throws MospException {
		// 対象日における振替休日申請リストを取得
		List<SubstituteDtoInterface> substituteDtoList = requestUtil.getSubstituteList(false);
		// 休暇範囲判定用リストの振替休日申請から休暇範囲を取得
		int substituteRange = requestUtil.checkHolidayRangeSubstitute(substituteDtoList);
		int workOnHolidayRange = 0;
		// 対象日の振替出勤申請の有無確認
		WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHolidayRequestDao
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getWorkDate());
		if (workOnHolidayDto != null) {
			// 再振替出勤申請がある場合
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
			if (WorkflowUtility.isApplied(workflowDto) && !WorkflowUtility.isCompleted(workflowDto)) {
				// 再振替出勤申請が未承認の場合
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
					// 午前振出の場合
					workOnHolidayRange = TimeConst.CODE_HOLIDAY_RANGE_AM;
				} else if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
					// 午後振出の場合
					workOnHolidayRange = TimeConst.CODE_HOLIDAY_RANGE_PM;
				}
			}
		}
		// 休暇範囲から再振替出勤分をひく
		return substituteRange - workOnHolidayRange;
	}
	
	/**
	 * 代休申請が存在するか確認する。
	 * @param list 代休申請リスト
	 * @return 確認結果(true：代休申請あり、false：代休申請なし)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected boolean isAppliSubHoliday(List<SubHolidayRequestDtoInterface> list) throws MospException {
		// 代休申請データ毎に処理
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : list) {
			// 最新のワークフロー取得
			WorkflowDtoInterface workflowDto = workflowIntegrate
				.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
			// 下書又は取下の場合
			if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)) {
				continue;
			}
			return true;
		}
		return false;
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
	
	/**
	 * 遅刻限度時間を超過しているエラーメッセージ。<br>
	 * @param workDate 勤務日
	 */
	protected void getArdinessTardinessLimitOver(Date workDate) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
				getStringDate(workDate), mospParams.getName("Tardiness"), mospParams.getName("Tardiness"),
				mospParams.getName("AmRest"));
	}
	
	/**
	 * 早退限度時間を超過しているエラーメッセージ。<br>
	 * @param workDate 勤務日
	 */
	protected void getLeaveEarlyLimitOver(Date workDate) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER,
				getStringDate(workDate), mospParams.getName("LeaveEarly"), mospParams.getName("LeaveEarly"),
				mospParams.getName("PmRest"));
	}
	
}
