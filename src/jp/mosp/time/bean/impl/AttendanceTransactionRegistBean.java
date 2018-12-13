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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtAttendanceDto;

/**
 * 勤怠トランザクション登録クラス。
 */
public class AttendanceTransactionRegistBean extends TimeBean implements AttendanceTransactionRegistBeanInterface {
	
	/**
	 * 勤怠トランザクションDAOクラス。
	 */
	protected AttendanceTransactionDaoInterface		dao;
	
	/**
	 * 設定適用管理参照クラス。
	 */
	protected ApplicationReferenceBeanInterface		applicationReference;
	
	/**
	 * カレンダ管理参照クラス。
	 */
	protected ScheduleReferenceBeanInterface		scheduleReference;
	
	/**
	 * カレンダ日参照クラス。
	 */
	protected ScheduleDateReferenceBeanInterface	scheduleDateReference;
	
	/**
	 * 休暇種別管理参照クラス。
	 */
	protected HolidayReferenceBeanInterface			holidayReference;
	
	/**
	 * 休暇申請登録クラス。
	 */
	protected HolidayRequestRegistBeanInterface		holidayRequestRegist;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public AttendanceTransactionRegistBean() {
		super();
	}
	
	/**
	 * {@link TimeBean#TimeBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection データベースコネクション
	 */
	public AttendanceTransactionRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (AttendanceTransactionDaoInterface)createDao(AttendanceTransactionDaoInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		holidayReference = (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
		holidayRequestRegist = (HolidayRequestRegistBeanInterface)createBean(HolidayRequestRegistBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public void regist(String personalId, Date workDate) throws MospException {
		regist(personalId, workDate, true);
	}
	
	@Override
	public void regist(AttendanceDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getWorkDate(), dto.getWorkflow());
	}
	
	@Override
	public void regist(HolidayRequestDtoInterface dto) throws MospException {
		for (Date date = dto.getRequestStartDate(); !date.after(dto.getRequestEndDate()); date = addDay(date, 1)) {
			String workTypeCode = holidayRequestRegist.getScheduledWorkTypeCode(dto.getPersonalId(), date);
			if (holidayRequestRegist.isLegalDaysOff(workTypeCode)
					|| holidayRequestRegist.isPrescribedDaysOff(workTypeCode)
					|| holidayRequestRegist.isWorkOnLegalDaysOff(workTypeCode)
					|| holidayRequestRegist.isWorkOnPrescribedDaysOff(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			regist(dto.getPersonalId(), date, dto.getWorkflow());
		}
	}
	
	@Override
	public void regist(SubHolidayRequestDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getWorkDate(), dto.getWorkflow());
	}
	
	@Override
	public void regist(SubstituteDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getSubstituteDate(), dto.getWorkflow());
	}
	
	/**
	 * 登録を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param workflow ワークフロー番号
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(String personalId, Date workDate, long workflow) throws MospException {
		if (workflowIntegrate.isCompleted(workflow)) {
			// 承認済の場合
			regist(personalId, workDate);
		}
	}
	
	/**
	 * 登録を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param isUpdate 更新する場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(String personalId, Date workDate, boolean isUpdate) throws MospException {
		AttendanceTransactionDtoInterface dto = dao.findForKey(personalId, workDate);
		if (dto == null) {
			dto = new TmtAttendanceDto();
			setDtoFields(dto, personalId, workDate);
			// 新規登録
			insert(dto);
			return;
		}
		if (isUpdate) {
			setDtoFields(dto);
			// 履歴更新
			update(dto);
		}
	}
	
	@Override
	public void regist(String personalId, Date firstDate, Date lastDate, boolean isUpdate) throws MospException {
		for (Date date = firstDate; !date.after(lastDate); date = addDay(date, 1)) {
			regist(personalId, date, isUpdate);
		}
	}
	
	/**
	 * 値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceTransactionDtoInterface dto, String personalId, Date workDate)
			throws MospException {
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		setDtoFields(dto);
	}
	
	/**
	 * 値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceTransactionDtoInterface dto) throws MospException {
		dto.setAttendanceType("");
		dto.setNumerator(0);
		dto.setDenominator(0);
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		if (attendanceDto != null && workflowIntegrate.isCompleted(attendanceDto.getWorkflow())) {
			// 勤怠申請が承認済の場合
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
			if (workOnHolidayRequestDto == null) {
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(1);
				dto.setDenominator(1);
				return;
			}
			// 振出・休出申請が承認済の場合
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				// 振替出勤(全日)の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(1);
				dto.setDenominator(1);
				return;
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 法定休日出勤の場合
					dto.setAttendanceType(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY);
					dto.setNumerator(0);
					dto.setDenominator(0);
					return;
				} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workOnHolidayRequestDto
					.getWorkOnHolidayType())) {
					// 所定休日出勤の場合
					dto.setAttendanceType(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY);
					dto.setNumerator(0);
					dto.setDenominator(0);
					return;
				}
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 振替出勤(半日)の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(0);
				dto.setDenominator(0);
				return;
			}
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
			dto.setNumerator(1);
			dto.setDenominator(1);
			return;
		}
		// 振替休日
		int substituteRange = requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 振替休日が全休又は午前休 + 午後休の場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_SUB_HOLIDAY);
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		// 代休
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 代休の場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_SUB_HOLIDAY);
			dto.setNumerator(1);
			dto.setDenominator(1);
			return;
		}
		// 休暇
		Integer amHoliday = null;
		Integer pmHoliday = null;
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(true);
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayList) {
			// 申請休暇範囲取得
			boolean isAll = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
			boolean isAm = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM;
			boolean isPm = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM;
			if (holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holidayRequestDto.getHolidayType2())) {
					// 有給休暇の場合
					dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
					dto.setNumerator(1);
					dto.setDenominator(1);
					return;
				} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(
						holidayRequestDto.getHolidayType2())) {
					// ストック休暇の場合
					// 出勤率扱い取得
					int stockHolidayAttendance = mospParams.getApplicationProperty(
							TimeConst.APP_STOCK_HOLIDAY_ATTENDANCE, 1);
					// 出勤扱いの場合
					if (stockHolidayAttendance == 1) {
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(1);
						dto.setDenominator(1);
						return;
					}
					// 全休の場合
					if (isAll) {
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						// 欠勤扱いの場合
						if (stockHolidayAttendance == 2) {
							dto.setNumerator(0);
							dto.setDenominator(1);
							return;
						}
						// 対象外の場合
						if (stockHolidayAttendance == 3) {
							dto.setNumerator(0);
							dto.setDenominator(0);
							return;
						}
					} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
						// 午前休の場合
						amHoliday = stockHolidayAttendance;
					} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						// 午後休の場合
						pmHoliday = stockHolidayAttendance;
					}
				}
			} else if (holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
					|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER
					|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
				// 特別休暇・その他休暇・欠勤の場合
				HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(holidayRequestDto.getHolidayType2(),
						holidayRequestDto.getRequestStartDate(), holidayRequestDto.getHolidayType1());
				if (holidayDto == null) {
					continue;
				}
				if (holidayDto.getPaidHolidayCalc() == 1) {
					// 出勤扱いの場合
					dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
					dto.setNumerator(1);
					dto.setDenominator(1);
					return;
				} else if (holidayDto.getPaidHolidayCalc() == 2) {
					// 欠勤扱いの場合
					if (isAll) {
						// 全休の場合
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(0);
						dto.setDenominator(1);
						return;
					}
					if (isAm) {
						// 午前休の場合
						amHoliday = 2;
					}
					if (isPm) {
						// 午後休の場合
						pmHoliday = 2;
					}
				} else if (holidayDto.getPaidHolidayCalc() == 3) {
					// 計算対象外の場合
					if (isAll) {
						// 全休の場合
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(0);
						dto.setDenominator(0);
						return;
					}
					if (isAm && amHoliday == null) {
						// 午前休の場合
						amHoliday = 3;
					}
					if (isPm && pmHoliday == null) {
						// 午後休の場合
						pmHoliday = 3;
					}
				}
			}
		}
		if (requestUtil.isHolidayAllDay(true)) {
			// 全休の場合
			if ((amHoliday != null && amHoliday.intValue() == 2) || (pmHoliday != null && pmHoliday.intValue() == 2)) {
				// 欠勤扱いの場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
				dto.setNumerator(0);
				dto.setDenominator(1);
				return;
			}
			if ((amHoliday != null && amHoliday.intValue() == 3) || (pmHoliday != null && pmHoliday.intValue() == 3)) {
				// 計算対象外の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
				dto.setNumerator(0);
				dto.setDenominator(0);
				return;
			}
		}
		// カレンダ
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(dto.getPersonalId(),
				dto.getWorkDate());
		if (applicationDto == null) {
			dto.setAttendanceType("");
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
				dto.getWorkDate());
		if (scheduleDto == null) {
			dto.setAttendanceType("");
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
				scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), dto.getWorkDate());
		if (scheduleDateDto == null) {
			dto.setAttendanceType("");
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		if (scheduleDateDto.getWorkTypeCode().isEmpty()
				|| TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
			// 勤務形態が未設定・法定休日・所定休日の場合
			dto.setAttendanceType(scheduleDateDto.getWorkTypeCode());
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		// 勤務形態が設定されている場合
		dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
		dto.setNumerator(0);
		dto.setDenominator(1);
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insert(AttendanceTransactionDtoInterface dto) throws MospException {
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
		dto.setTmtAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update(AttendanceTransactionDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmtAttendanceId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(AttendanceTransactionDtoInterface dto) {
		// TODO
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(AttendanceTransactionDtoInterface dto) throws MospException {
		// 重複確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getWorkDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkUpdate(AttendanceTransactionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtAttendanceId());
	}
	
}
