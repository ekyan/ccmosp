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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeRecordRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.input.action.AttendanceCardAction;
import jp.mosp.time.portal.bean.impl.PortalTimeCardBean;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 打刻クラス。
 */
public class TimeRecordBean extends AttendanceListRegistBean implements TimeRecordBeanInterface {
	
	/**
	 * 最大休憩回数。<br>
	 */
	protected static final int					MAX_REST_TIMES	= 6;
	
	/**
	 * 打刻データ参照クラス。
	 */
	protected TimeRecordReferenceBeanInterface	timeRecordReference;
	
	/**
	 * 打刻データ登録クラス。
	 */
	protected TimeRecordRegistBeanInterface		timeRecordRegist;
	
	/**
	 * 申請ユーティリティクラス。
	 */
	protected RequestUtilBeanInterface			requestUtil;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TimeRecordBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元クラスのメソッドを実行
		super.initBean();
		timeRecordReference = (TimeRecordReferenceBeanInterface)createBean(TimeRecordReferenceBeanInterface.class);
		timeRecordRegist = (TimeRecordRegistBeanInterface)createBean(TimeRecordRegistBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
	}
	
	@Override
	public Date recordStartWork(String personalId, Date recordTime) throws MospException {
		// ポータル時刻を打刻
		recordPortalTime(personalId, recordTime, recordTime, PortalTimeCardBean.RECODE_START_WORK);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定(打刻時刻の日付)
		targetDate = DateUtility.getDate(recordTime);
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 勤怠情報確認
		if (dto != null) {
			// エラーメッセージ設定
			mospParams.addErrorMessage(TimeMessageUtility.MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
					mospParams.getName("WorkManage"), mospParams.getName("RecordTime"));
			return null;
		}
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 申請エンティティを取得
		RequestEntity requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤務形態エンティティを取得(申請エンティティから勤務形態コードを取得)
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(requestEntity.getWorkType(), targetDate);
		// 勤務対象勤務形態であるかを確認
		if (workTypeEntity.isWorkTypeForWork() == false) {
			// エラーメッセージ設定
			addNotWorkDateErrorMessage(targetDate);
			return null;
		}
		// 始業時刻取得
		Date startTime = AttendanceUtility.getStartTime(applicationEntity, requestEntity, workTypeEntity, recordTime);
		// 勤怠データ作成
		// TODO getAttendanceDtoも作り直し、AttendanceListRegistBeanとは切り離すことを検討
		dto = getAttendanceDto(startTime, startTime, null, false, false, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkDraft(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定
		draft(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態に登録されている休憩情報のリストを取得
		List<RestDtoInterface> restList = registRest(startTime, workTypeEntity.getEndTime(requestEntity));
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : restList) {
			// 休憩時間登録
			restRegist.regist(restDto);
		}
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		return recordTime;
	}
	
	@Override
	public Date recordEndWork(String personalId, Date recordTime) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(TimeMessageUtility.getNameEndWork(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 休憩戻確認
		checkRestEnd();
		// ワークフロー番号設定
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		apply(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 申請エンティティを取得
		RequestEntity requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), targetDate);
		// 終業時刻取得
		Date endTime = getEndTime(applicationEntity, requestEntity, workTypeEntity, recordTime);
		// 登録用勤怠データを取得(各種自動計算実施)
		dto = getAttendanceDto(dto.getStartTime(), dto.getActualStartTime(), endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		// 勤怠申請後処理群を実行
		afterApplyAttendance(dto);
		return recordTime;
	}
	
	/**
	 * 終業時刻を取得する。<br>
	 * <br>
	 * 1.直帰の場合：終業予定時刻<br>
	 * 2.早退の場合：<br>
	 *   勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 *   勤務予定時間表示設定が無効である場合：勤怠設定で丸めた実打刻時刻<br>
	 * 3.勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 * 4.その他の場合：勤怠設定で丸めた実打刻時刻<br>
	 * <br>
	 * @param applicationEntity 設定適用エンティティ
	 * @param requestEntity     申請エンティティ
	 * @param workTypeEntity    勤務形態エンティティ
	 * @param recordTime        打刻時刻
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getEndTime(ApplicationEntity applicationEntity, RequestEntity requestEntity,
			WorkTypeEntity workTypeEntity, Date recordTime) throws MospException {
		// 終業予定時刻を取得(勤務形態エンティティ及び申請エンティティから)
		Date scheduledTime = workTypeEntity.getEndTime(requestEntity);
		// 1.直帰の場合(勤務形態の直帰設定を確認)
		if (workTypeEntity.isDirectEnd()) {
			// 終業予定時刻を取得
			return scheduledTime;
		}
		// 2.早退の場合
		if (recordTime.before(scheduledTime)) {
			// 勤務予定時間表示設定が有効である場合
			if (applicationEntity.useScheduledTime()) {
				// 勤怠設定で丸めた打刻時刻を取得
				return applicationEntity.getRoundedEndTime(recordTime);
			}
			// 勤怠設定で丸めた実打刻時刻を取得
			return applicationEntity.getRoundedActualEndTime(recordTime);
		}
		// 3.勤務予定時間表示設定が有効である場合(勤怠設定の勤務予定時間表示設定を確認)
		if (applicationEntity.useScheduledTime()) {
			// 勤怠設定で丸めた打刻時刻を取得
			return applicationEntity.getRoundedEndTime(recordTime);
		}
		// 4.その他(勤怠設定で丸めた実打刻時刻を取得)
		return applicationEntity.getRoundedActualEndTime(recordTime);
	}
	
	@Override
	public Date recordStartRest(String personalId, Date recordTime) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		setTargetDate(TimeMessageUtility.getNameStartRest(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報取得
		List<RestDtoInterface> list = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出取得
		List<GoOutDtoInterface> publicList = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出取得
		List<GoOutDtoInterface> privateList = goOutReference.getPrivateGoOutList(personalId, targetDate);
		for (GoOutDtoInterface goOutDto : publicList) {
			if (checkRestDuplication(recordTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
				// 公用外出と重複している場合
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Official", "GoingOut"));
				sb.append(goOutDto.getTimesGoOut());
				addStartRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime), sb.toString());
				return null;
			}
		}
		for (GoOutDtoInterface goOutDto : privateList) {
			if (checkRestDuplication(recordTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
				// 私用外出と重複している場合
				addStartRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime),
						mospParams.getName("PrivateGoingOut" + goOutDto.getTimesGoOut()));
				return null;
			}
		}
		// 休憩デフォルト日時取得
		Date restDefaultTime = targetDate;
		// 処理対象情報準備
		RestDtoInterface dto = null;
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : list) {
			if (checkRestDuplication(recordTime, restDto.getRestStart(), restDto.getRestEnd())) {
				// 休憩と重複している場合
				addStartRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime),
						mospParams.getName("Rest" + restDto.getRest()));
				return null;
			}
			// 休憩入日時確認
			if (restDefaultTime.equals(restDto.getRestStart())) {
				// 処理対象情報設定
				dto = restDto;
				dto.setRestStart(applicationEntity.getRoundedRestStartTime(recordTime));
				break;
			}
			// 休憩戻り確認
			if (restDefaultTime.equals(restDto.getRestEnd())) {
				// エラーメッセージ追加
				TimeMessageUtility.addErrorStartRestAlreadyRecorded(mospParams, targetDate);
				return null;
			}
		}
		// 処理対象情報確認
		if (dto == null && list.size() < MAX_REST_TIMES) {
			// 処理対象情報作成
			dto = restRegist.getInitDto();
			dto.setPersonalId(this.personalId);
			dto.setWorkDate(targetDate);
			dto.setTimesWork(TIMES_WORK_DEFAULT);
			dto.setRest(list.size() + 1);
			dto.setRestStart(applicationEntity.getRoundedRestStartTime(recordTime));
			dto.setRestEnd(restDefaultTime);
			dto.setRestTime(0);
		}
		// 処理対象情報確認
		if (dto == null) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorRestOverLimit(mospParams, targetDate);
			return null;
		}
		// 休憩情報登録
		restRegist.regist(dto);
		return recordTime;
	}
	
	@Override
	public Date recordEndRest(String personalId, Date recordTime) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		setTargetDate(TimeMessageUtility.getNameEndRest(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠関連各種情報取得
		setTimeDtos(false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報取得
		List<RestDtoInterface> list = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出取得
		List<GoOutDtoInterface> publicList = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出取得
		List<GoOutDtoInterface> privateList = goOutReference.getPrivateGoOutList(personalId, targetDate);
		for (GoOutDtoInterface goOutDto : publicList) {
			if (checkRestDuplication(recordTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
				// 公用外出と重複している場合
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Official", "GoingOut"));
				sb.append(goOutDto.getTimesGoOut());
				addEndRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime), sb.toString());
				return null;
			}
		}
		for (GoOutDtoInterface goOutDto : privateList) {
			if (checkRestDuplication(recordTime, goOutDto.getGoOutStart(), goOutDto.getGoOutEnd())) {
				// 私用外出と重複している場合
				addEndRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime),
						mospParams.getName("PrivateGoingOut" + goOutDto.getTimesGoOut()));
				return null;
			}
		}
		// 休憩デフォルト日時取得
		Date restDefaultTime = targetDate;
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 処理対象情報準備
		RestDtoInterface dto = null;
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : list) {
			if (checkRestDuplication(recordTime, restDto.getRestStart(), restDto.getRestEnd())) {
				// 休憩と重複している場合
				addEndRestDuplicationErrorMessage(DateUtility.getStringDateAndDay(recordTime),
						mospParams.getName("Rest" + restDto.getRest()));
				return null;
			}
			// 休憩入日時確認
			if (restDefaultTime.equals(restDto.getRestStart()) == false
					&& restDefaultTime.equals(restDto.getRestEnd())) {
				// 処理対象情報設定
				dto = restDto;
				dto.setRestEnd(applicationEntity.getRoundedRestEndTime(recordTime));
				// 休憩時間計算
				dto.setRestTime(restRegist.getCalcRestTime(dto.getRestStart(), dto.getRestEnd(), timeSettingDto));
				break;
			}
		}
		// 処理対象情報確認
		if (dto == null) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorStartRestNotRecorded(mospParams, targetDate);
			return null;
		}
		// 休憩情報登録
		restRegist.regist(dto);
		return recordTime;
	}
	
	@Override
	public Date recordRegularEnd(String personalId, Date recordTime) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(TimeMessageUtility.getNameRegularEnd(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 休憩戻確認
		checkRestEnd();
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定(自己承認)
		applyAndApprove(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 申請エンティティを取得
		RequestEntity requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), targetDate);
		// 遅刻及び早退を確認
		if (isLate(requestEntity, workTypeEntity, dto.getStartTime())
				|| isLeaveEarly(requestEntity, workTypeEntity, recordTime)) {
			// 設定適用エンティティを取得
			ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
			// 終業時刻(丸め)を取得
			Date targetTime = applicationEntity.getRoundedEndTime(recordTime);
			// MosP処理情報に対象個人IDを設定
			mospParams.addGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID, personalId);
			// MosP処理情報に対象日を設定
			mospParams.addGeneralParam(PlatformConst.PRM_TARGET_DATE, targetDate);
			// MosP処理情報に終業時刻(丸め)を設定
			mospParams.addGeneralParam(TimeConst.PRM_TARGET_TIME, targetTime);
			// 勤怠詳細画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(AttendanceCardAction.CMD_SELECT_SHOW_FROM_PORTAL);
			// エラーメッセージ設定
			TimeMessageUtility.addErrorSelfApproveFailed(mospParams);
			return null;
		}
		// 勤務形態の終業時刻を取得
		Date endTime = workTypeEntity.getEndTime(requestEntity);
		// 振出・休出申請(休出申請)がある場合
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			// 設定適用エンティティ取得
			ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
			// 打刻時刻から終業時刻を取得
			endTime = getEndTime(applicationEntity, requestEntity, workTypeEntity, recordTime);
		}
		// 登録用勤怠データを取得(各種自動計算実施)
		dto = getAttendanceDto(dto.getStartTime(), dto.getActualStartTime(), endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		// 勤怠申請後処理群を実行
		afterApplyAttendance(dto);
		return recordTime;
	}
	
	/**
	 * 遅刻であるかを確認する。<br>
	 * @param requestEntity  申請エンティティ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @param startTime      始業時刻
	 * @return 確認結果(true：遅刻である、false：そうでない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isLate(RequestEntity requestEntity, WorkTypeEntity workTypeEntity, Date startTime)
			throws MospException {
		// 勤怠情報及び各種申請から始業時刻を取得
		Date scheduledStartTime = workTypeEntity.getStartTime(requestEntity);
		// 遅刻確認
		boolean isLate = scheduledStartTime.before(startTime);
		// 直行(勤怠データ)を確認
		if (requestEntity.isAttendanceDirectStart()) {
			isLate = false;
		}
		// 直行(勤務形態)を確認
		if (workTypeEntity.isDirectStart()) {
			isLate = false;
		}
		// 休日出勤申請を確認
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			isLate = false;
		}
		// 時短時間1(有給)が設定されている場合
		if (workTypeEntity.isShort1TimeSet() && workTypeEntity.isShort1TypePay()) {
			// 時短時間1(有給)終了時刻を取得(勤務日時刻に調整)
			Date short1EndTime = TimeUtility.getDateTime(targetDate, workTypeEntity.getShort1EndTime());
			// 終業時刻が時短時間1終了時刻以前の場合
			if (startTime.after(short1EndTime) == false) {
				isLate = false;
			}
		}
		return isLate;
	}
	
	/**
	 * 早退であるかを確認する。<br>
	 * @param requestEntity  申請エンティティ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @param endTime        終業時刻
	 * @return 確認結果(true：早退である、false：そうでない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isLeaveEarly(RequestEntity requestEntity, WorkTypeEntity workTypeEntity, Date endTime)
			throws MospException {
		// 勤怠情報及び各種申請から終業時刻を取得
		Date scheduledEndTime = workTypeEntity.getEndTime(requestEntity);
		// 早退確認
		boolean isLeaveEarly = scheduledEndTime.after(endTime);
		// 直帰(勤怠データ)を確認
		if (requestEntity.isAttendanceDirectEnd()) {
			isLeaveEarly = false;
		}
		// 直帰(勤務形態)を確認
		if (workTypeEntity.isDirectEnd()) {
			isLeaveEarly = false;
		}
		// 休日出勤申請を確認
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			isLeaveEarly = false;
		}
		// 時短時間2(有給)が設定されている場合
		if (workTypeEntity.isShort2TimeSet() && workTypeEntity.isShort2TypePay()) {
			// 時短時間2(有給)開始時刻を取得(勤務日時刻に調整)
			Date short2StartTime = TimeUtility.getDateTime(targetDate, workTypeEntity.getShort2StartTime());
			// 終業時刻が時短時間2開始時刻以後の場合
			if (endTime.before(short2StartTime) == false) {
				isLeaveEarly = false;
			}
		}
		return isLeaveEarly;
	}
	
	@Override
	public void recordOverEnd(String personalId, Date recordTime) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(TimeMessageUtility.getNameOverEnd(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 勤怠データが有り終業が設定されている場合等
			return;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 終業時刻(丸め)を取得
		Date targetTime = applicationEntity.getRoundedEndTime(recordTime);
		// MosP処理情報に対象個人IDを設定
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID, personalId);
		// MosP処理情報に対象日を設定
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_DATE, targetDate);
		// MosP処理情報に終業時刻(丸め)を設定
		mospParams.addGeneralParam(TimeConst.PRM_TARGET_TIME, targetTime);
		// 勤怠情報がある場合
		if (dto != null && dto.getWorkTypeCode() != null) {
			// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
			WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), targetDate);
			// MosP処理情報に前出を設定
			if (workTypeEntity.isDirectStart()) {
				// 前出フラグを設定
				mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_START, MospConst.CHECKBOX_ON);
			}
			// 勤務形態の後出設定を確認
			if (workTypeEntity.isDirectEnd()) {
				// 後出フラグを設定
				mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_END, MospConst.CHECKBOX_ON);
			}
		}
		// 勤怠詳細画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(AttendanceCardAction.CMD_SELECT_SHOW_FROM_PORTAL);
	}
	
	@Override
	public Date recordRegularWork(String personalId, Date recordTime) throws MospException {
		// ポータル時刻を打刻
		recordPortalTime(personalId, recordTime, recordTime, PortalTimeCardBean.RECODE_START_WORK);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定(打刻した日を対象日とする)
		targetDate = DateUtility.getDate(recordTime);
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 勤怠情報確認
		if (dto != null) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorStartWorkAlreadyRecorded(mospParams, targetDate);
			return null;
		}
		// 申請エンティティを取得
		RequestEntity requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤務形態エンティティを取得(申請エンティティから勤務形態コードを取得)
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(requestEntity.getWorkType(), targetDate);
		// 勤務対象勤務形態であるかを確認
		if (workTypeEntity.isWorkTypeForWork() == false) {
			// エラーメッセージ設定
			addNotWorkDateErrorMessage(targetDate);
			return null;
		}
		// 始業時刻取得
		Date startTime = workTypeEntity.getStartTime(requestEntity);
		// 終業時刻取得
		Date endTime = workTypeEntity.getEndTime(requestEntity);
		// 勤怠データ作成
		dto = getAttendanceDto(startTime, startTime, endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定(自己承認)
		applyAndApprove(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態に登録されている休憩情報のリストを取得
		List<RestDtoInterface> restList = registRest(startTime, endTime);
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : restList) {
			// 休憩時間登録
			restRegist.regist(restDto);
		}
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		return recordTime;
	}
	
	@Override
	public Date recordStartWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordStartWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordEndWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordEndWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordStartRest() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordStartRest(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordEndRest() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordEndRest(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordRegularEnd() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordRegularEnd(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public void recordOverEnd() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		recordOverEnd(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordRegularWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordRegularWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	/**
	 * 打刻対象日を設定する。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 打刻した日が打刻対象日となる。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在しない場合、前日の勤怠情報を確認する。<br>
	 * 前日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 前日が打刻対象日となる。<br>
	 * <br>
	 * 上記の場合以外は、打刻対象日を設定しない。<br>
	 * <br>
	 * @param process 打刻対象処理
	 * @return 打刻対象日の勤怠情報
	 * @throws MospException 勤怠情報の取得に失敗した場合
	 */
	protected AttendanceDtoInterface setTargetDate(String process) throws MospException {
		// 対象日設定(システム日付)
		Date recordDate = getSystemDate();
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(personalId, recordDate, TIMES_WORK_DEFAULT);
		// 打刻した日の勤怠情報が存在しない場合
		if (dto == null) {
			// 前日の勤怠を取得
			Date beforeDate = addDay(recordDate, -1);
			dto = attendanceReference.findForKey(personalId, beforeDate, TIMES_WORK_DEFAULT);
			// 前日の勤怠を確認
			if (dto != null && dto.getEndTime() == null) {
				// 対象日として前日を設定(深夜に日付を超えて打刻した場合等)
				targetDate = beforeDate;
			} else {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorStratWorkNotRecorded(mospParams, recordDate, process);
			}
		} else if (dto.getEndTime() != null) {
			// エラーメッセージ設定(終業時刻が既に登録されている場合)
			TimeMessageUtility.addErrorEndWorkAlreadyRecorded(mospParams, recordDate);
		} else {
			targetDate = recordDate;
		}
		return dto;
	}
	
	/**
	 * 勤務形態情報が保持している直行直帰を勤怠データに設定する。<br>
	 * @param dto            勤怠データ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setDirectStartEnd(AttendanceDtoInterface dto, WorkTypeEntity workTypeEntity) throws MospException {
		// 勤務形態の直行設定を確認
		if (workTypeEntity.isDirectStart()) {
			// 勤怠データに直行フラグを設定
			dto.setDirectStart(getInteger(MospConst.CHECKBOX_ON));
		}
		// 勤務形態の直帰設定を確認
		if (workTypeEntity.isDirectEnd()) {
			// 勤怠データに直帰フラグを設定
			dto.setDirectEnd(getInteger(MospConst.CHECKBOX_ON));
		}
	}
	
	/**
	 * 休憩時刻重複チェック。<br>
	 * @param recordTime 打刻時刻
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 重複している場合true、そうでない場合false
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected boolean checkRestDuplication(Date recordTime, Date startTime, Date endTime) throws MospException {
		Date defaultTime = targetDate;
		// 打刻時刻が休憩開始時刻以降且つ休憩終了時刻より前の場合
		return !defaultTime.equals(startTime) && !defaultTime.equals(endTime) && !recordTime.before(startTime)
				&& recordTime.before(endTime);
	}
	
	/**
	 * 休憩開始時刻重複エラーメッセージを追加する。<br>
	 * @param stringTagetDate 表示用対象日付
	 * @param name 置換文字列
	 */
	protected void addStartRestDuplicationErrorMessage(String stringTagetDate, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("RestTime"));
		sb.append(mospParams.getName("Into"));
		String[] rep = { stringTagetDate, sb.toString(), name };
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
	}
	
	/**
	 * 休憩終了時刻重複エラーメッセージを追加する。<br>
	 * @param stringTagetDate 表示用対象日付
	 * @param name 置換文字列
	 */
	protected void addEndRestDuplicationErrorMessage(String stringTagetDate, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("RestTime"));
		sb.append(mospParams.getName("Return"));
		String[] rep = { stringTagetDate, sb.toString(), name };
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
	}
	
	/**
	 * ポータル時刻を打刻する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param recordType 打刻区分
	 * @param recordTime 打刻時刻
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordPortalTime(String personalId, Date workDate, Date recordTime, String recordType)
			throws MospException {
		if (timeRecordReference.findForKey(personalId, workDate, TIMES_WORK_DEFAULT, recordType) != null) {
			return;
		}
		TimeRecordDtoInterface dto = timeRecordRegist.getInitDto();
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		dto.setTimesWork(TIMES_WORK_DEFAULT);
		dto.setRecordType(recordType);
		dto.setRecordTime(recordTime);
		// 新規登録
		timeRecordRegist.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// コミット
		timeRecordRegist.commit();
	}
	
	/**
	 * 休憩の確認を行う。<br>
	 * <br>
	 * 休憩入のみが登録されていて休憩戻が登録されていない場合、
	 * エラーメッセージを設定する。
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRestEnd() throws MospException {
		// 休憩情報取得(0も登録されている)
		List<RestDtoInterface> list = restDao.findForList(personalId, targetDate, TIMES_WORK_DEFAULT);
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : list) {
			// 休憩戻り確認
			if (targetDate.equals(restDto.getRestStart()) == false && targetDate.equals(restDto.getRestEnd())) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorEndRestNotRecorded(mospParams, targetDate);
				return;
			}
		}
	}
	
	/**
	 * 対象勤怠データ情報に対し、
	 * ワークフロー(自己承認)を作成して、ワークフロー番号を設定する。<br>
	 * @param dto 対象勤怠データ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void applyAndApprove(AttendanceDtoInterface dto) throws MospException {
		// ワークフロー情報準備
		WorkflowDtoInterface workflowDto = workflowRegist.getInitDto();
		// ワークフロー情報確認
		if (dto.getWorkflow() != 0) {
			// ワークフロー情報取得(新規でない場合)
			workflowDto = workflow.getLatestWorkflowInfo(dto.getWorkflow());
		}
		// 承認者を設定
		workflowDto.setApproverId(PlatformConst.APPROVAL_ROUTE_SELF);
		// ワークフロー情報機能コード設定
		workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_MANGE);
		// ワークフロー申請
		workflowDto = workflowRegist.appli(workflowDto, personalId, targetDate, PlatformConst.WORKFLOW_TYPE_TIME, null);
		// ワークフロー申請確認
		if (workflowDto == null) {
			return;
		}
		// ワークフロー番号を勤怠データに設定
		dto.setWorkflow(workflowDto.getWorkflow());
	}
	
}
