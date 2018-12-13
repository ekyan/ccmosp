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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.TotalAbsenceRegistBeanInterface;
import jp.mosp.time.bean.TotalLeaveRegistBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AllowanceDaoInterface;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dao.settings.TotalAbsenceDaoInterface;
import jp.mosp.time.dao.settings.TotalAllowanceDaoInterface;
import jp.mosp.time.dao.settings.TotalLeaveDaoInterface;
import jp.mosp.time.dao.settings.TotalOtherVacationDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.CutoffErrorListDto;
import jp.mosp.time.dto.settings.impl.TmdTotalAllowanceDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠集計クラス。
 */
public class TotalTimeCalcBean extends TimeApplicationBean implements TotalTimeCalcBeanInterface {
	
	/**
	 * 深夜代休を利用する際、勤務日から数えた未使用日数。<br>
	 */
//	public static final int								SUB_HOLIDAY_UNUSED_DAY		= -11;
	
	/**
	 * 週単位の計算を行う際、カウント数。<br>
	 */
	public static final int								WEEK_CALCULATE_COUNT		= -6;
	
	/**
	 * 所定労働時間及び法内残業時間の基準時間。<br>
	 */
	public static final int								PREDETERMINED_OVERTIME_WORK	= 40;
	
	/**
	 * 法定労働時間及び法定残業時間の基準時間。<br>
	 */
	public static final int								LEGAL_OVERTIME_WORK			= 45;
	
	/**
	 * 人事マスタDAOクラス。<br>
	 */
	protected HumanDaoInterface							humanDao;
	
	/**
	 * 入社情報DAOクラス。<br>
	 */
	protected EntranceDaoInterface						entranceDao;
	
	/**
	 * 退社情報DAOクラス。<br>
	 */
	protected RetirementDaoInterface					retirementDao;
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface						holidayDao;
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface					attendanceDao;
	
	/**
	 * カレンダマスタDAOクラス。<br>
	 */
	protected ScheduleDaoInterface						scheduleDao;
	
	/**
	 * カレンダ日マスタDAOクラス。<br>
	 */
	protected ScheduleDateDaoInterface					scheduleDateDao;
	
	/**
	 * 残業申請データDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface				overtimeRequestDao;
	
	/**
	 * 休暇申請データDAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface				holidayRequestDao;
	
	/**
	 * 休日出勤申請データDAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface			workOnHolidayRequestDao;
	
	/**
	 * 代休申請データDAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface				subHolidayRequestDao;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface				differenceRequestDao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface						workflowDao;
	
	/**
	 * 特別休暇集計データDAOクラス。<br>
	 */
	protected TotalLeaveDaoInterface					totalLeaveDao;
	
	/**
	 * その他休暇集計データDAOクラス。<br>
	 */
	protected TotalOtherVacationDaoInterface			totalOtherVacationDao;
	
	/**
	 * 欠勤集計データDAOクラス。<br>
	 */
	protected TotalAbsenceDaoInterface					totalAbsenceDao;
	
	/**
	 * 手当集計データDAOクラス。<br>
	 */
	protected TotalAllowanceDaoInterface				totalAllowanceDao;
	
	/**
	 * 手当データDAOクラス。<br>
	 */
	protected AllowanceDaoInterface						allowanceDao;
	
	/**
	 * 勤怠設定管理DAOクラス。<br>
	 */
	protected TimeSettingDaoInterface					timeSettingDao;
	
	/**
	 * 振替休日データDAOクラス。<br>
	 */
	protected SubstituteDaoInterface					substituteDao;
	
	/**
	 * カレンダ管理参照。<br>
	 */
	protected ScheduleReferenceBeanInterface			schedule;
	
	/**
	 * カレンダ日管理参照。<br>
	 */
	protected ScheduleDateReferenceBeanInterface		scheduleDate;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface			workTypeReference;
	
	/**
	 * 締日ユーティリティインターフェース参照。
	 */
	protected CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * 代休データDAOクラス。<br>
	 */
	protected SubHolidayDaoInterface					subHolidayDao;
	
	/**
	 * 特別休暇集計データ登録クラス。<br>
	 */
	protected TotalLeaveRegistBeanInterface				totalLeaveRegist;
	
	/**
	 * その他休暇集計データ登録クラス。<br>
	 */
	protected TotalOtherVacationRegistBeanInterface		totalOtherVacationRegist;
	
	/**
	 * 欠勤集計データ登録クラス。<br>
	 */
	protected TotalAbsenceRegistBeanInterface			totalAbsenceRegist;
	
	/**
	 * 勤怠集計データ登録クラス。<br>
	 */
	protected TotalTimeReferenceBeanInterface			totalTimeRefer;
	
	/**
	 * 勤怠集計データ登録クラス。<br>
	 */
	protected TotalTimeRegistBeanInterface				totalTimeRegist;
	
	/**
	 * 社員勤怠集計管理参照クラス。<br>
	 */
	TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployee;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	/**
	 * 計算対象年。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected int										calculationYear;
	
	/**
	 * 計算対象月。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected int										calculationMonth;
	
	/**
	 * 締期間基準日。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected Date										cutoffTargetDate;
	
	/**
	 * 締期間初日。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected Date										cutoffFirstDate;
	
	/**
	 * 締期間最終日。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected Date										cutoffLastDate;
	
	/**
	 * 締期間集計日。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}で設定される。<br>
	 */
	protected Date										cutoffCalculationDate;
	
	/**
	 * 休暇種別マスタリスト(特別休暇)。<br>
	 */
	protected List<HolidayDtoInterface>					specialHolidayList;
	
	/**
	 * 休暇種別マスタリスト(その他休暇)。<br>
	 */
	protected List<HolidayDtoInterface>					otherHolidayList;
	
	/**
	 * 休暇種別マスタリスト(欠勤)。<br>
	 */
	protected List<HolidayDtoInterface>					absenceList;
	
	/**
	 * 締期間初日(個人)。<br>
	 * {@link TotalTimeCalcBean#calc(String)}等で設定される。<br>
	 * 締期間初日及び入社日から求める。<br>
	 */
	protected Date										personalFirstDate;
	
	/**
	 * 締期間最終日(個人)。<br>
	 * {@link TotalTimeCalcBean#calc(String)}等で設定される。<br>
	 * 締期間最終日及び退職日から求める。<br>
	 */
	protected Date										personalLastDate;
	
	/**
	 * 締期間(個人)対象日リスト。<br>
	 * {@link TotalTimeCalcBean#calc(String)}等で設定される。<br>
	 * 締期間初日(個人)及び締期間最終日(個人)から求める。<br>
	 */
	protected List<Date>								targetDateList;
	
	/**
	 * 設定適用情報群。<br>
	 * 締期間(個人)における日毎の設定適用情報を格納する。<br>
	 * {@link TotalTimeCalcBean#setPersonalInfo(String)}で設定される。<br>
	 */
	protected Map<Date, ApplicationDtoInterface>		applicationMap;
	
	/**
	 * カレンダ日情報群。<br>
	 * 締期間(個人)における日毎のカレンダ日情報を格納する。<br>
	 * {@link TotalTimeCalcBean#calc(String)}等で設定される。<br>
	 */
	protected Map<Date, ScheduleDateDtoInterface>		scheduleMap;
	
	/**
	 * 振替休日情報群。<br>
	 * 締期間(個人)における日毎の振替休日情報を格納する。<br>
	 * {@link TotalTimeCalcBean#calc(String)}等で設定される。<br>
	 */
	protected Map<Date, SubstituteDtoInterface>			substituteMap;
	
	/**
	 * 勤怠情報リスト。<br>
	 */
	protected List<AttendanceDtoInterface>				attendanceDtoList;
	
	/**
	 * 休日出勤申請リスト。<br>
	 */
	protected List<WorkOnHolidayRequestDtoInterface>	workOnHolidayRequestDtoList;
	
	/**
	 * 休暇申請リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>			holidayRequestDtoList;
	
	/**
	 * 代休申請リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>		subHolidayRequestDtoList;
	
	/**
	 * 残業申請リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>			overtimeRequestDtoList;
	
	/**
	 * 時差出勤申請リスト。<br>
	 */
	protected List<DifferenceRequestDtoInterface>		differenceRequestDtoList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeCalcBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public TotalTimeCalcBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のメソッドを実施
		super.initBean();
		// Bean及びDAO準備
		humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		entranceDao = (EntranceDaoInterface)createDao(EntranceDaoInterface.class);
		retirementDao = (RetirementDaoInterface)createDao(RetirementDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		scheduleDao = (ScheduleDaoInterface)createDao(ScheduleDaoInterface.class);
		scheduleDateDao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
		overtimeRequestDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		differenceRequestDao = (DifferenceRequestDaoInterface)createDao(DifferenceRequestDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		totalLeaveDao = (TotalLeaveDaoInterface)createDao(TotalLeaveDaoInterface.class);
		totalOtherVacationDao = (TotalOtherVacationDaoInterface)createDao(TotalOtherVacationDaoInterface.class);
		totalAbsenceDao = (TotalAbsenceDaoInterface)createDao(TotalAbsenceDaoInterface.class);
		totalAllowanceDao = (TotalAllowanceDaoInterface)createDao(TotalAllowanceDaoInterface.class);
		allowanceDao = (AllowanceDaoInterface)createDao(AllowanceDaoInterface.class);
		timeSettingDao = (TimeSettingDaoInterface)createDao(TimeSettingDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		schedule = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDate = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		subHolidayDao = (SubHolidayDaoInterface)createDao(SubHolidayDaoInterface.class);
		totalLeaveRegist = (TotalLeaveRegistBeanInterface)createBean(TotalLeaveRegistBeanInterface.class);
		totalOtherVacationRegist = (TotalOtherVacationRegistBeanInterface)createBean(TotalOtherVacationRegistBeanInterface.class);
		totalAbsenceRegist = (TotalAbsenceRegistBeanInterface)createBean(TotalAbsenceRegistBeanInterface.class);
		totalTimeRefer = (TotalTimeReferenceBeanInterface)createBean(TotalTimeReferenceBeanInterface.class);
		totalTimeRegist = (TotalTimeRegistBeanInterface)createBean(TotalTimeRegistBeanInterface.class);
		totalTimeEmployee = (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		attendanceTransactionRegist = (AttendanceTransactionRegistBeanInterface)createBean(AttendanceTransactionRegistBeanInterface.class);
	}
	
	@Override
	public void setCalculationInfo(int calculationYear, int calculationMonth, String cutoffCode) throws MospException {
		// 対象年月設定
		this.calculationYear = calculationYear;
		this.calculationMonth = calculationMonth;
		// 締日情報取得
		cutoffDto = cutoffUtil.getCutoff(cutoffCode, calculationYear, calculationMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 締期間基準日設定
		cutoffTargetDate = TimeUtility.getCutoffTermTargetDate(cutoffDate, calculationYear, calculationMonth);
		// 締期間初日設定
		cutoffFirstDate = TimeUtility.getCutoffFirstDate(cutoffDate, calculationYear, calculationMonth);
		// 締期間最終日設定
		cutoffLastDate = TimeUtility.getCutoffLastDate(cutoffDate, calculationYear, calculationMonth);
		// 締期間集計日設定
		cutoffCalculationDate = TimeUtility.getCutoffCalculationDate(cutoffDate, calculationYear, calculationMonth);
		// 休暇種別設定
		specialHolidayList = holidayDao.findForActivateDate(cutoffTargetDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		otherHolidayList = holidayDao.findForActivateDate(cutoffTargetDate, TimeConst.CODE_HOLIDAYTYPE_OTHER);
		absenceList = holidayDao.findForActivateDate(cutoffTargetDate, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
	}
	
	@Override
	public List<CutoffErrorListDtoInterface> calc(String personalId) throws MospException {
		// 社員勤怠集計管理情報取得
		Integer state = totalTimeEmployee.getCutoffState(personalId, calculationYear, calculationMonth);
		// 社員勤怠集計管理情報確認(未締でない場合)
		if (state != null && state.intValue() != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
			// 計算不要
			return new ArrayList<CutoffErrorListDtoInterface>();
		}
		// 締期間初日(個人)設定
		personalFirstDate = getPersonalFirstDate(personalId);
		// 締期間最終日(個人)設定
		personalLastDate = getPersonalLastDate(personalId);
		// 締期間(個人)確認
		if (personalFirstDate == null || personalLastDate == null) {
			// 計算対象外
			return new ArrayList<CutoffErrorListDtoInterface>();
		}
		// 個人計算情報設定
		setPersonalInfo(personalId);
		// カレンダ日情報群取得
		scheduleMap = getScheduleMap(personalId);
		// 振替休日情報群取得
		substituteMap = getSubstituteMap(personalId);
		// 処理結果確認(個人計算情報設定等に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			// エラーがあるため集計不能
			return null;
		}
		// 特別休暇集計
		double totalSpecialHoliday = calcToalLeave(personalId, true);
		// その他休暇集計
		double totalOtherHoliday = calcTotalOtherVacation(personalId, true);
		// 欠勤集計
		double totalAbsence = calcTotalAbsence(personalId, true);
		// 勤怠集計データ取得
		TotalTimeDataDtoInterface dto = totalTimeRefer.findForKey(personalId, calculationYear, calculationMonth);
		if (dto == null) {
			dto = totalTimeRegist.getInitDto();
		}
		// 勤怠集計データに値を設定
		dto.setPersonalId(personalId);
		dto.setCalculationYear(calculationYear);
		dto.setCalculationMonth(calculationMonth);
		dto.setCalculationDate(cutoffCalculationDate);
		dto.setTotalSpecialHoliday(totalSpecialHoliday);
		dto.setTotalOtherHoliday(totalOtherHoliday);
		dto.setTotalAbsence(totalAbsence);
		// 勤怠集計
		return calcTime(personalId, dto, true);
	}
	
	@Override
	public TotalTimeDataDtoInterface getTotaledTimeData(String personalId) throws MospException {
		// 社員勤怠集計管理情報取得
		Integer state = totalTimeEmployee.getCutoffState(personalId, calculationYear, calculationMonth);
		// 社員勤怠集計管理情報確認(未締でない場合)
		if (state != null && state.intValue() != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
			// 勤怠集計情報取得
			TotalTimeDataDtoInterface totalTimeDataDto = totalTimeRefer.findForKey(personalId, calculationYear,
					calculationMonth);
			// 勤怠集計情報確認
			if (totalTimeDataDto != null) {
				// 計算不要
				return totalTimeDataDto;
			}
		}
		// 締期間初日(個人)設定
		personalFirstDate = getPersonalFirstDate(personalId);
		// 締期間最終日(個人)設定
		personalLastDate = getPersonalLastDate(personalId);
		// 締期間(個人)確認
		if (personalFirstDate == null || personalLastDate == null) {
			// 計算対象外
			return null;
		}
		// 個人計算情報設定
		setPersonalInfo(personalId);
		// カレンダ日情報群取得
		scheduleMap = getScheduleMap(personalId);
		// 振替休日情報群取得
		substituteMap = getSubstituteMap(personalId);
		// 処理結果確認(個人計算情報設定等に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			// エラーがあるため集計不能
			return null;
		}
		// 特別休暇集計
		double totalSpecialHoliday = calcToalLeave(personalId, false);
		// その他休暇集計
		double totalOtherHoliday = calcTotalOtherVacation(personalId, false);
		// 欠勤集計
		double totalAbsence = calcTotalAbsence(personalId, false);
		// 勤怠集計データ取得
		TotalTimeDataDtoInterface dto = totalTimeRegist.getInitDto();
		// 勤怠集計データに値を設定
		dto.setPersonalId(personalId);
		dto.setCalculationYear(calculationYear);
		dto.setCalculationMonth(calculationMonth);
		dto.setCalculationDate(cutoffCalculationDate);
		dto.setTotalSpecialHoliday(totalSpecialHoliday);
		dto.setTotalOtherHoliday(totalOtherHoliday);
		dto.setTotalAbsence(totalAbsence);
		// 勤怠集計
		calcTime(personalId, dto, false);
		return dto;
	}
	
	/**
	 * 個人計算情報を設定する。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}
	 * で設定された計算情報を基に、対象個人IDの個人計算情報を設定する。<br>
	 * @param personalId 対象個人ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPersonalInfo(String personalId) throws MospException {
		// 締期間(個人)対象日リスト設定
		targetDateList = TimeUtility.getDateList(personalFirstDate, personalLastDate);
		// 設定適用情報群設定
		applicationMap = applicationRefer.findForTerm(personalId, personalFirstDate, personalLastDate);
		// 締期間(個人)における各種申請リスト設定
		attendanceDtoList = attendanceDao.findForList(personalId, personalFirstDate, personalLastDate);
		holidayRequestDtoList = holidayRequestDao.findForTerm(personalId, personalFirstDate, personalLastDate);
		workOnHolidayRequestDtoList = workOnHolidayRequestDao.findForList(personalId, personalFirstDate,
				personalLastDate);
		subHolidayRequestDtoList = subHolidayRequestDao.findForList(personalId, personalFirstDate, personalLastDate);
		overtimeRequestDtoList = overtimeRequestDao.findForList(personalId, personalFirstDate, personalLastDate);
		differenceRequestDtoList = differenceRequestDao.findForList(personalId, personalFirstDate, personalLastDate);
	}
	
	/**
	 * 勤怠の集計をする。<br>
	 * 集計結果を勤怠集計データに格納する。<br>
	 * @param personalId 対象個人ID
	 * @param dto        勤怠集計データ
	 * @param needRegist 登録要否(true：登録要、false：登録不要)
	 * @return 集計時エラー内容リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<CutoffErrorListDtoInterface> calcTime(String personalId, TotalTimeDataDtoInterface dto,
			boolean needRegist) throws MospException {
		// 各種変数宣言及び初期化
		int workTime = 0;
//		int specificWorkTime = 0;
		int contractWorkTime = 0;
		double timesWorkDate = 0;
		int timesWork = 0;
		double legalWorkOnHoliday = 0;
		double specificWorkOnHoliday = 0;
		int timesAchievement = 0;
		int timesTotalWorkDate = 0;
		int directStart = 0;
		int directEnd = 0;
		int restTime = 0;
		int restLateNight = 0;
		int restWorkOnSpecificHoliday = 0;
		int restWorkOnHoliday = 0;
		int publicTime = 0;
		int privateTime = 0;
		int minutelyHolidayATime = 0;
		int minutelyHolidayBTime = 0;
		int overtime = 0;
		// 法定内残業時間
		int overtimeIn = 0;
		// 法定外残業時間
		int overtimeOut = 0;
		int overtimeOutForOver45Hours = 0;
		int lateNight = 0;
		int workOnSpecificHoliday = 0;
		int workOnHoliday = 0;
		int decreaseTime = 0;
		int fortyFiveHourOvertime = 0;
		int timesOvertime = 0;
		int timesWorkingHoliday = 0;
		int lateDays = 0;
		int lateThirtyMinutesOrMore = 0;
		int lateLessThanThirtyMinutes = 0;
		int lateTime = 0;
		int lateThirtyMinutesOrMoreTime = 0;
		int lateLessThanThirtyMinutesTime = 0;
		int timesLate = 0;
		int leaveEarlyDays = 0;
		int leaveEarlyThirtyMinutesOrMore = 0;
		int leaveEarlyLessThanThirtyMinutes = 0;
		int leaveEarlyTime = 0;
		int leaveEarlyThirtyMinutesOrMoreTime = 0;
		int leaveEarlyLessThanThirtyMinutesTime = 0;
		int timesLeaveEarly = 0;
		int timesHoliday = 0;
		int timesLegalHoliday = 0;
		int timesSpecificHoliday = 0;
		double timesPaidHoliday = 0;
		int paidHolidayHour = 0;
		double timesStockHoliday = 0;
		double timesCompensation = 0;
		double timesLegalCompensation = 0;
		double timesSpecificCompensation = 0;
		double timesLateCompensation = 0;
		double timesHolidaySubstitute = 0;
		double timesLegalHolidaySubstitute = 0;
		double timesSpecificHolidaySubstitute = 0;
		int totalAllowance = 0;
		int sixtyHourOvertime = 0;
		// 平日時間内時間(平日法定労働時間内残業時間)
		int workdayOvertimeIn = 0;
		// 所定休日時間内時間(所定休日法定労働時間内残業時間)
		int prescribedHolidayOvertimeIn = 0;
		// 平日時間外時間(平日法定労働時間外残業時間)
		int workdayOvertimeOut = 0;
		// 所定休日時間外時間(所定休日法定労働時間外残業時間)
		int prescribedOvertimeOut = 0;
		double timesAlternative = 0;
		double legalCompensationOccurred = 0;
		double specificCompensationOccurred = 0;
		double lateCompensationOccurred = 0;
		double legalCompensationUnused = 0;
		double specificCompensationUnused = 0;
		double lateCompensationUnused = 0;
		int statutoryHolidayWorkTimeIn = 0;
		int statutoryHolidayWorkTimeOut = 0;
		int prescribedHolidayWorkTimeIn = 0;
		int prescribedHolidayWorkTimeOut = 0;
		int shortUnpaid = 0;
		// 週40時間超勤務時間
		int weeklyOverFortyHourWorkTime = 0;
		// 法定内残業時間(週40時間超除く)
		int overtimeInNoWeeklyForty = 0;
		// 法定外残業時間(週40時間超除く)
		int overtimeOutNoWeeklyForty = 0;
		// 平日時間内時間(週40時間超除く)
		int weekDayOvertimeInNoWeeklyForty = 0;
		// 平日時間外時間(週40時間超除く)
		int weekDayOvertimeOutNoWeeklyForty = 0;
		// 集計時エラー情報リスト準備
		List<CutoffErrorListDtoInterface> list = new ArrayList<CutoffErrorListDtoInterface>();
		// 未承認仮締設定取得(true：未承認仮締有効)
		boolean noApproval = cutoffDto.getNoApproval() == 0;
		Map<Date, int[]> weekWorkMap = new HashMap<Date, int[]>();
		// 計算対象日毎に処理
		for (Date date : targetDateList) {
			// 出勤扱いフラグ
			boolean workFlag = false;
			// 出勤対象フラグ
			boolean workDateFlag = false;
			// 全休
			boolean allHoliday = false;
			// 午前休
			boolean amHoliday = false;
			// 午後休
			boolean pmHoliday = false;
			// 振替全休
			boolean allSubstituteHoliday = false;
			// 設定適用情報取得
			ApplicationDtoInterface applicationDto = applicationMap.get(date);
			// 設定適用情報確認
			if (applicationDto == null) {
				// 処理無し(次の日へ)
				continue;
			}
			// 勤怠設定マスタ取得
			TimeSettingDtoInterface timeSettingDto = timeSettingDao.findForInfo(applicationDto.getWorkSettingCode(),
					date);
			// 勤怠マスタの存在チェック
			timeSettingRefer.chkExistTimeSetting(timeSettingDto, date);
			// 処理結果確認
			if (mospParams.hasErrorMessage()) {
				return list;
			}
			HumanDtoInterface humanDto = humanDao.findForInfo(personalId, date);
			if (humanDto == null) {
				return list;
			}
			// カレンダ勤務形態
			String scheduleWorkTypeCode = getScheduleWorkTypeCode(personalId, date, false);
			if (mospParams.hasErrorMessage()) {
				return list;
			}
			// 法定休日
			boolean isLegalHoliday = TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleWorkTypeCode);
			// 所定休日
			boolean isPrescribedHoliday = TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleWorkTypeCode);
			// 期間開始日(代休発生日)を取得
			Date startDate = addDay(DateUtility.addMonth(date, -timeSettingDto.getSubHolidayLimitMonth()),
					-timeSettingDto.getSubHolidayLimitDate());
			if (addDay(DateUtility.addMonth(startDate, timeSettingDto.getSubHolidayLimitMonth()),
					timeSettingDto.getSubHolidayLimitDate()).equals(date)) {
				// 対象期間終了日準備
				Date endDate = startDate;
				// 代休取得期限月と対象月の足りない日にちをまわす
				while (addDay(DateUtility.addMonth(endDate, timeSettingDto.getSubHolidayLimitMonth()),
						timeSettingDto.getSubHolidayLimitDate()).equals(date)) {
					// 期間終了日に1日追加
					endDate = addDay(endDate, 1);
				}
				// 対象期間終了日設定(代休取得期限月と対象月の足りない日にち取得)
				endDate = addDay(endDate, -1);
				// 法定代休未使用日数・所定代休未使用日数
				List<SubHolidayDtoInterface> subHolidayUnusedList = subHolidayDao.findSubHolidayList(personalId,
						startDate, endDate);
				for (SubHolidayDtoInterface subHolidayDto : subHolidayUnusedList) {
					int subHolidayType = subHolidayDto.getSubHolidayType();
					if (subHolidayType != TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE
							&& subHolidayType != TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
						// 所定代休でなく且つ法定代休でない場合
						continue;
					}
					double days = subHolidayDto.getSubHolidayDays();
					double useDays = 0;
					List<SubHolidayRequestDtoInterface> subHolidayRequestDtoList = subHolidayRequestDao.findForList(
							subHolidayDto.getPersonalId(), subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(),
							subHolidayType);
					for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestDtoList) {
						WorkflowDtoInterface workflowDto = workflowDao.findForKey(subHolidayRequestDto.getWorkflow());
						if (workflowDto == null) {
							continue;
						}
						if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
							continue;
						}
						int range = subHolidayRequestDto.getHolidayRange();
						if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
							// 全休
							useDays++;
						} else if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							// 午前休又は午後休
							useDays += TimeConst.HOLIDAY_TIMES_HALF;
						}
					}
					days -= useDays;
					if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
						// 所定
						specificCompensationUnused += days;
					} else if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
						// 法定
						legalCompensationUnused += days;
					}
				}
				// 深夜代休未使用日数
				List<SubHolidayDtoInterface> nightSubHolidayUnusedList = subHolidayDao.findSubHolidayList(personalId,
						startDate, endDate);
				for (SubHolidayDtoInterface subHolidayDto : nightSubHolidayUnusedList) {
					int subHolidayType = subHolidayDto.getSubHolidayType();
					if (subHolidayType != TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
						// 深夜代休でない場合
						continue;
					}
					double days = subHolidayDto.getSubHolidayDays();
					double useDays = 0;
					List<SubHolidayRequestDtoInterface> subHolidayRequestDtoList = subHolidayRequestDao.findForList(
							subHolidayDto.getPersonalId(), subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(),
							subHolidayType);
					for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestDtoList) {
						WorkflowDtoInterface workflowDto = workflowDao.findForKey(subHolidayRequestDto.getWorkflow());
						if (workflowDto == null) {
							continue;
						}
						if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
							continue;
						}
						int range = subHolidayRequestDto.getHolidayRange();
						if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
							// 全休
							useDays++;
						} else if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							// 午前休又は午後休
							useDays += TimeConst.HOLIDAY_TIMES_HALF;
						}
					}
					days -= useDays;
					if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
						// 深夜
						lateCompensationUnused += days;
					}
				}
			}
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(date);
			Date yesterdayDate = addDay(date, -1);
			AttendanceDtoInterface yesterdayAttendanceDto = getAttendanceDtoListDto(yesterdayDate);
			if (yesterdayDate.before(personalFirstDate)) {
				// 先月の場合
				yesterdayAttendanceDto = attendanceDao.findForKey(personalId, yesterdayDate, 1);
			}
			boolean yesterdayWorkOnLegal = false;
			if (yesterdayAttendanceDto != null) {
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(yesterdayAttendanceDto.getWorkflow());
				yesterdayWorkOnLegal = workflowDto != null
						&& PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())
						&& TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(yesterdayAttendanceDto.getWorkTypeCode());
			}
			// 週40時間時間外計算
			weekWorkMap.put(
					date,
					getWeekOvertimeCalculationArray(attendanceDto, yesterdayAttendanceDto, yesterdayWorkOnLegal,
							timeSettingDto));
			// 前日の勤怠データ
			boolean overtimeCountFlag = false;
			if (yesterdayWorkOnLegal) {
				// TODO
				// 前日が法定休日出勤の場合は前日の残業時間を加算
				overtime += yesterdayAttendanceDto.getOvertime();
				// 法定内残業時間加算
				overtimeIn += yesterdayAttendanceDto.getOvertimeIn();
				// 法定外残業時間加算
				overtimeOut += yesterdayAttendanceDto.getOvertimeOut();
				overtimeOutForOver45Hours += yesterdayAttendanceDto.getOvertimeOut();
				boolean workOnLegal = false;
				boolean workOnPrescribed = false;
				if (attendanceDto != null) {
					workOnLegal = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(attendanceDto.getWorkTypeCode());
					workOnPrescribed = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY
						.equals(attendanceDto.getWorkTypeCode());
				}
				// 平日時間内時間(平日法定労働時間内残業時間)加算
				workdayOvertimeIn += getWorkdayOvertimeIn(yesterdayAttendanceDto, workOnLegal, workOnPrescribed);
				// 所定休日時間内時間(所定休日法定労働時間内残業時間)加算
				prescribedHolidayOvertimeIn += getPrescribedHolidayOvertimeIn(yesterdayAttendanceDto, workOnPrescribed);
				// 平日時間外時間(平日法定労働時間外残業時間)加算
				workdayOvertimeOut += getWorkdayOvertimeOut(yesterdayAttendanceDto, workOnLegal, workOnPrescribed);
				// 所定休日時間外時間(所定休日法定労働時間外残業時間)加算
				prescribedOvertimeOut += getPrescribedHolidayOvertimeOut(yesterdayAttendanceDto, workOnPrescribed);
				// 法定内残業時間(週40時間超除く)加算
				overtimeInNoWeeklyForty += yesterdayAttendanceDto.getOvertimeIn();
				// 法定外残業時間(週40時間超除く)加算
				overtimeOutNoWeeklyForty += yesterdayAttendanceDto.getOvertimeOut();
				// 平日時間内時間(週40時間超除く)加算
				weekDayOvertimeInNoWeeklyForty += getWorkdayOvertimeIn(yesterdayAttendanceDto, workOnLegal,
						workOnPrescribed);
				// 平日時間外時間(週40時間超除く)加算
				weekDayOvertimeOutNoWeeklyForty += getWorkdayOvertimeOut(yesterdayAttendanceDto, workOnLegal,
						workOnPrescribed);
				if (yesterdayAttendanceDto.getOvertime() > 0) {
					// 前日の残業時間がある場合は残業回数を加算
					timesOvertime++;
					overtimeCountFlag = true;
				}
			}
			if (DateUtility.isDayOfWeek(date, getEndDayOfWeek(timeSettingDto.getStartWeek()))
					|| date.equals(personalLastDate)) {
				// 曜日がその週の終了曜日と一致した場合、
				// 又はその月の末日の場合は、
				// その週の週単位の計算を行う
				int count = WEEK_CALCULATE_COUNT;
				while (count <= 0) {
					if (weekWorkMap.get(addDay(date, count)) == null) {
						if (addDay(date, count).before(personalFirstDate)) {
							// 先月の場合
							Date lastMonthDate = addDay(date, count);
							// 先月分の勤怠データを取得する
							AttendanceDtoInterface lastMonthAttendanceDto = attendanceDao.findForKey(personalId,
									lastMonthDate, TimeBean.TIMES_WORK_DEFAULT);
							AttendanceDtoInterface yesterdayLastMonthAttendanceDto = attendanceDao.findForKey(
									personalId, addDay(lastMonthDate, -1), TimeBean.TIMES_WORK_DEFAULT);
							boolean yesterdayLastMonthWorkOnLegal = false;
							if (yesterdayLastMonthAttendanceDto != null) {
								WorkflowDtoInterface workflowDto = workflowDao
									.findForKey(yesterdayLastMonthAttendanceDto.getWorkflow());
								yesterdayLastMonthWorkOnLegal = workflowDto != null
										&& PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())
										&& TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(yesterdayLastMonthAttendanceDto
											.getWorkTypeCode());
							}
							weekWorkMap.put(
									lastMonthDate,
									getWeekOvertimeCalculationArray(lastMonthAttendanceDto,
											yesterdayLastMonthAttendanceDto, yesterdayLastMonthWorkOnLegal,
											timeSettingDto));
						}
					}
					count++;
				}
				int weeklyTotalGeneralWorkTime = 0;
				int weeklyTotalOvertimeIn = 0;
				int minusOvertimeIn = 0;
				int minusWorkdayOvertimeIn = 0;
				int minusPrescribedHolidayOvertimeIn = 0;
				count = WEEK_CALCULATE_COUNT;
				// TODO
				// 一週間単位(例：日曜～土曜)で計算
				while (count <= 0) {
					Date calculateDate = addDay(date, count);
					int[] workTimeArray = weekWorkMap.get(calculateDate);
					if (workTimeArray == null) {
						count++;
						continue;
					}
					for (int i = 0; i < workTimeArray.length; i++) {
						boolean isWorkdayWork = i == 0 || i == 1 || i == 4 || i == 5;
						boolean isPrescribedHolidayWork = i == 2 || i == 3 || i == 6 || i == 7;
						boolean isPrescribedWork = i == 0 || i == 2 || i == 4 || i == 6;
						boolean isOvertimeIn = i == 1 || i == 3 || i == 5 || i == 7;
						if (weeklyTotalGeneralWorkTime + weeklyTotalOvertimeIn >= PREDETERMINED_OVERTIME_WORK
								* TimeConst.CODE_DEFINITION_HOUR) {
							// 所定労働時間及び法内残業時間の合計が40時間以上の場合
							if (!calculateDate.before(personalFirstDate)) {
								// 法定労働時間外残業時間
								overtimeOut += workTimeArray[i];
								// 週40時間超勤務時間加算
								weeklyOverFortyHourWorkTime += workTimeArray[i];
								if (isPrescribedHolidayWork) {
									// 所定休日時間外時間
									prescribedOvertimeOut += workTimeArray[i];
								} else if (isWorkdayWork) {
									// 平日時間外時間
									workdayOvertimeOut += workTimeArray[i];
								}
								if (isOvertimeIn) {
									// 法内残業時間
									minusOvertimeIn += workTimeArray[i];
									if (isWorkdayWork) {
										// 平日の場合
										minusWorkdayOvertimeIn += workTimeArray[i];
									} else if (isPrescribedHolidayWork) {
										// 所定休日の場合
										minusPrescribedHolidayOvertimeIn += workTimeArray[i];
									}
								}
							}
						} else if (weeklyTotalGeneralWorkTime + weeklyTotalOvertimeIn + workTimeArray[i] > PREDETERMINED_OVERTIME_WORK
								* TimeConst.CODE_DEFINITION_HOUR) {
							// 所定労働時間及び法内残業時間の合計に加算したら40時間を超える場合
							if (!calculateDate.before(personalFirstDate)) {
								int over = weeklyTotalGeneralWorkTime + weeklyTotalOvertimeIn + workTimeArray[i]
										- PREDETERMINED_OVERTIME_WORK * TimeConst.CODE_DEFINITION_HOUR;
								// 法定労働時間外残業時間
								overtimeOut += over;
								// 週40時間超勤務時間加算
								weeklyOverFortyHourWorkTime += over;
								if (isPrescribedHolidayWork) {
									// 所定休日時間外時間
									prescribedOvertimeOut += over;
								} else if (isWorkdayWork) {
									// 平日時間外時間
									workdayOvertimeOut += over;
								}
								if (isOvertimeIn) {
									// 法内残業時間
									minusOvertimeIn += over;
									if (isWorkdayWork) {
										// 法内残業時間且つ平日の場合
										minusWorkdayOvertimeIn += over;
									} else if (isPrescribedHolidayWork) {
										// 法内残業時間且つ所定休日の場合
										minusPrescribedHolidayOvertimeIn += over;
									}
								}
							}
						}
						if (isPrescribedWork) {
							// 所定労働時間
							weeklyTotalGeneralWorkTime += workTimeArray[i];
						} else if (isOvertimeIn) {
							// 法内残業時間
							weeklyTotalOvertimeIn += workTimeArray[i];
						}
					}
					count++;
				}
				// 法定内残業時間から週40時間超勤務時間(法定内残業時間分)を除去
				overtimeIn -= minusOvertimeIn;
				workdayOvertimeIn -= minusWorkdayOvertimeIn;
				prescribedHolidayOvertimeIn -= minusPrescribedHolidayOvertimeIn;
				// 初期化
				weekWorkMap.clear();
			}
			if (!noApproval && cutoffDto.getNoApproval() != 3) {
				// 残業
				List<OvertimeRequestDtoInterface> overtimeRequestList = getOvertimeRequestDtoList(date);
				for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
					WorkflowDtoInterface overtimeRequestWorkflowDto = workflowDao.findForKey(overtimeRequestDto
						.getWorkflow());
					if (overtimeRequestWorkflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(overtimeRequestWorkflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(overtimeRequestWorkflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (!PlatformConst.CODE_STATUS_COMPLETE.equals(overtimeRequestWorkflowDto.getWorkflowStatus())) {
						// 承認完了でない場合
						CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
						cutoffErrorListDto.setType(mospParams.getName("OvertimeWork"));
						cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
						list.add(cutoffErrorListDto);
					}
				}
			}
			if (!noApproval) {
				// 時差出勤
				DifferenceRequestDtoInterface differenceRequestDto = getDifferenceRequestDtoListDto(date);
				if (differenceRequestDto != null) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(differenceRequestDto.getWorkflow());
					if (workflowDto != null) {
						if (!PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())
								&& !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
								&& !PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
							// 取下でなく且つ下書でなく且つ承認完了でない場合
							CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
							cutoffErrorListDto.setType(mospParams.getName("TimeDifference"));
							cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
							list.add(cutoffErrorListDto);
						}
					}
				}
			}
			// 休日出勤
			boolean isWorkOnHoliday = false;
			// 振替出勤
			boolean isWorkOnHolidaySubstitute = false;
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDtoListDto(date);
			if (workOnHolidayRequestDto != null) {
				// ワークフロー情報を取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(workOnHolidayRequestDto.getWorkflow());
				if (workflowDto != null) {
					if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
						// 承認完了の場合
						int substitute = workOnHolidayRequestDto.getSubstitute();
						if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
							// 振替出勤の場合
							isWorkOnHolidaySubstitute = true;
						} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
							// 振替出勤(午前)の場合
							isWorkOnHolidaySubstitute = true;
							pmHoliday = true;
						} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
							// 振替出勤(午後)の場合
							isWorkOnHolidaySubstitute = true;
							amHoliday = true;
						} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
							// 休日出勤の場合
							isWorkOnHoliday = true;
						}
					} else if (!PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())
							&& !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus()) && !noApproval) {
						// 取下でなく下書でなく未承認仮締が無効の場合
						CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
						cutoffErrorListDto.setType("振出休出");
						cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
						list.add(cutoffErrorListDto);
						continue;
					}
				}
			}
			if (!isWorkOnHoliday && !isWorkOnHolidaySubstitute) {
				// 振出・休出でない場合
				// 振替休日
				List<SubstituteDtoInterface> substituteList = substituteDao.findForList(personalId, date);
				for (SubstituteDtoInterface substituteDto : substituteList) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
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
					if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
						// 承認完了の場合
						int substituteRange = substituteDto.getSubstituteRange();
						String substituteType = substituteDto.getSubstituteType();
						if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
							// 全休の場合
							timesHolidaySubstitute++;
							allHoliday = true;
							allSubstituteHoliday = true;
							if (substituteType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
								// 法定振替休日
								timesLegalHolidaySubstitute++;
							} else if (substituteType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
								// 所定振替休日
								timesSpecificHolidaySubstitute++;
							}
						} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM
								|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							// 午前休又は午後休の場合
							timesHolidaySubstitute += TimeConst.HOLIDAY_TIMES_HALF;
							if (substituteType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
								// 法定振替休日
								timesLegalHolidaySubstitute += TimeConst.HOLIDAY_TIMES_HALF;
							} else if (substituteType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
								// 所定振替休日
								timesSpecificHolidaySubstitute += TimeConst.HOLIDAY_TIMES_HALF;
							}
							if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
								// 午前休の場合
								amHoliday = true;
							} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
								// 午後休の場合
								pmHoliday = true;
							}
						}
					} else {
						// 承認完了でない場合
						if (!noApproval) {
							// 未承認仮締が無効の場合
							CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
							cutoffErrorListDto.setType("振出休出");
							cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
							list.add(cutoffErrorListDto);
						}
					}
				}
			}
			if (!isWorkOnHoliday) {
				// 休日出勤でない場合
				// 休暇
				List<HolidayRequestDtoInterface> holidayRequestList = getHolidayRequestDtoList(date);
				for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
					WorkflowDtoInterface holidayRequestWorkflowDto = workflowDao.findForKey(holidayRequestDto
						.getWorkflow());
					if (holidayRequestWorkflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(holidayRequestWorkflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(holidayRequestWorkflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_COMPLETE.equals(holidayRequestWorkflowDto.getWorkflowStatus())) {
						// 承認完了の場合
						if (holidayRequestDto.getHolidayType1() == 1) {
							if (String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(
									holidayRequestDto.getHolidayType2())) {
								// 有給休暇の場合
								if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
									// 全休の場合
									timesPaidHoliday++;
									workFlag = true;
									workDateFlag = true;
									allHoliday = true;
								} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
										|| holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
									// 半休の場合
									timesPaidHoliday += TimeConst.HOLIDAY_TIMES_HALF;
									workFlag = true;
									workDateFlag = true;
									if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
										amHoliday = true;
									} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
										pmHoliday = true;
									}
								} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
									// 時休の場合
									paidHolidayHour += holidayRequestDto.getUseHour();
								}
							} else if (String.valueOf(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(
									holidayRequestDto.getHolidayType2())) {
								// ストック休暇の場合
								workFlag = true;
								workDateFlag = true;
								if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
									// 全休の場合
									timesStockHoliday++;
									allHoliday = true;
								} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
										|| holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
									// 半休の場合
									timesStockHoliday += TimeConst.HOLIDAY_TIMES_HALF;
									if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
										amHoliday = true;
									} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
										pmHoliday = true;
									}
								}
							}
						} else if (holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
								|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER
								|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
							// 特別休暇・その他休暇・欠勤の場合
							if ((!isLegalHoliday && !isPrescribedHoliday && !allSubstituteHoliday)
									|| ((isLegalHoliday || isPrescribedHoliday) && isWorkOnHolidaySubstitute)) {
								// 法定休日でなく且つ所定休日でなく且つ振替全休でない場合
								// 又は法定休日・所定休日且つ振替出勤の場合
								HolidayDtoInterface holidayDto = holidayDao.findForInfo(
										holidayRequestDto.getHolidayType2(), date, holidayRequestDto.getHolidayType1());
								if (holidayDto.getPaidHolidayCalc() == 1) {
									// 出勤扱いの場合
									workFlag = true;
									workDateFlag = true;
								} else if (holidayDto.getPaidHolidayCalc() == 2) {
									// 欠勤扱いの場合
									workDateFlag = true;
								}
								if (holidayRequestDto.getHolidayRange() == 1) {
									allHoliday = true;
								} else if (holidayRequestDto.getHolidayRange() == 2) {
									amHoliday = true;
								} else if (holidayRequestDto.getHolidayRange() == 3) {
									pmHoliday = true;
								}
							}
						}
					} else {
						// 承認完了でない場合
						if (!noApproval) {
							if (date.equals(holidayRequestDto.getRequestStartDate())) {
								// 未承認仮締が無効の場合
								CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
								cutoffErrorListDto.setType(mospParams.getName("Vacation"));
								cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
								list.add(cutoffErrorListDto);
							}
						}
					}
				}
			}
			// 代休
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = getSubHolidayRequestDtoList(date);
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowDao.findForKey(subHolidayRequestDto
					.getWorkflow());
				if (subHolidayRequestWorkflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(subHolidayRequestWorkflowDto.getWorkflowStatus())) {
					// 取下の場合
					continue;
				}
				if (PlatformConst.CODE_STATUS_DRAFT.equals(subHolidayRequestWorkflowDto.getWorkflowStatus())) {
					// 下書の場合
					continue;
				}
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(subHolidayRequestWorkflowDto.getWorkflowStatus())) {
					// 承認完了の場合
					SubHolidayDtoInterface subHolidayDto = subHolidayDao.findForKey(
							subHolidayRequestDto.getPersonalId(), subHolidayRequestDto.getWorkDate(),
							subHolidayRequestDto.getTimesWork(), subHolidayRequestDto.getWorkDateSubHolidayType());
					if (subHolidayDto == null) {
						continue;
					}
					int workDateSubHolidayType = subHolidayRequestDto.getWorkDateSubHolidayType();
					int holidayRange = subHolidayRequestDto.getHolidayRange();
					if (holidayRange == 1) {
						// 全休の場合
						if (workDateSubHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
							// 法定代休の場合
							timesLegalCompensation++;
						} else if (workDateSubHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
							// 所定代休の場合
							timesSpecificCompensation++;
						} else if (workDateSubHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
							// 深夜代休の場合
							timesLateCompensation++;
						} else {
							continue;
						}
						timesCompensation++;
						allHoliday = true;
					} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						// 半休の場合
						if (workDateSubHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
							// 法定代休の場合
							timesLegalCompensation += TimeConst.HOLIDAY_TIMES_HALF;
						} else if (workDateSubHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
							// 所定代休の場合
							timesSpecificCompensation += TimeConst.HOLIDAY_TIMES_HALF;
						} else if (workDateSubHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
							// 深夜代休の場合
							timesLateCompensation += TimeConst.HOLIDAY_TIMES_HALF;
						} else {
							continue;
						}
						timesCompensation += TimeConst.HOLIDAY_TIMES_HALF;
						if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
							amHoliday = true;
						} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
							pmHoliday = true;
						}
					}
				} else {
					// 承認完了でない場合
					if (!noApproval) {
						// 未承認仮締が無効の場合
						CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
						cutoffErrorListDto.setType("代休");
						cutoffErrorListDto.setState(mospParams.getName("Ram") + mospParams.getName("Approval"));
						list.add(cutoffErrorListDto);
					}
				}
			}
			if (allHoliday || (amHoliday && pmHoliday)) {
				// 無給時短時間
				shortUnpaid += getShortUnpaid(personalId, date);
				if (workFlag) {
					// 出勤扱い加算
					timesAchievement++;
				}
				if (workDateFlag) {
					// 出勤対象加算
					timesTotalWorkDate++;
				}
				continue;
			}
			if (isLegalHoliday || isPrescribedHoliday) {
				// 勤務形態が法定休日又は所定休日の場合
				if (!isWorkOnHoliday && !isWorkOnHolidaySubstitute) {
					// 休日出勤でなく且つ振替出勤でない場合
					if (isLegalHoliday) {
						// 法定休日
						timesLegalHoliday++;
						timesHoliday++;
					}
					if (isPrescribedHoliday) {
						// 所定休日
						timesSpecificHoliday++;
						timesHoliday++;
					}
					// 勤怠トランザクション登録
					attendanceTransactionRegist.regist(personalId, date);
					continue;
				}
			}
			if (attendanceDto == null) {
				if (!noApproval) {
					// 未承認仮締が無効の場合
					CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
					cutoffErrorListDto.setType("勤怠");
					cutoffErrorListDto.setState("未申請");
					list.add(cutoffErrorListDto);
				}
				continue;
			} else {
				boolean workOnLegal = isWorkOnHoliday
						&& TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(attendanceDto.getWorkTypeCode());
				boolean workOnPrescribed = isWorkOnHoliday
						&& TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(attendanceDto.getWorkTypeCode());
				WorkflowDtoInterface attendanceWorkflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
				if (attendanceWorkflowDto == null) {
					continue;
				}
				if (!PlatformConst.CODE_STATUS_COMPLETE.equals(attendanceWorkflowDto.getWorkflowStatus())) {
					// 承認完了でない場合
					if (!noApproval) {
						// 未承認仮締が無効の場合
						CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(date, humanDto);
						cutoffErrorListDto.setType("勤怠");
						cutoffErrorListDto.setState("未承認");
						list.add(cutoffErrorListDto);
					}
					continue;
				}
				// 出勤の場合
				workTime += attendanceDto.getWorkTime();
				// 所定勤務時間
//				specificWorkTime
				// 出勤日数
				timesWorkDate++;
				if (amHoliday || pmHoliday) {
					// 半休がある場合は半日(0.5日)分を引く
					timesWorkDate -= TimeConst.HOLIDAY_TIMES_HALF;
				}
				timesWork++;
				workOnHoliday += attendanceDto.getLegalWorkTime();
				workOnSpecificHoliday += attendanceDto.getSpecificWorkTime();
				restWorkOnHoliday += attendanceDto.getLegalHolidayRestTime();
				restWorkOnSpecificHoliday += attendanceDto.getPrescribedHolidayRestTime();
				if (isWorkOnHoliday) {
					// 休日出勤
					if (workOnLegal) {
						// 法定休日出勤
						legalWorkOnHoliday++;
					}
					if (workOnPrescribed) {
						// 所定休日出勤
						specificWorkOnHoliday++;
					}
					timesWorkingHoliday++;
				} else {
					// 休日出勤でない場合
					workFlag = true;
					workDateFlag = true;
				}
				if (attendanceDto.getDirectStart() == 1) {
					directStart++;
				}
				if (attendanceDto.getDirectEnd() == 1) {
					directEnd++;
				}
				restTime += attendanceDto.getRestTime();
				restLateNight += attendanceDto.getNightRestTime();
				publicTime += attendanceDto.getPublicTime();
				privateTime += attendanceDto.getPrivateTime();
				minutelyHolidayATime += attendanceDto.getMinutelyHolidayATime();
				minutelyHolidayBTime += attendanceDto.getMinutelyHolidayBTime();
				if (!workOnLegal) {
					// TODO
					// 法定休日出勤でない場合は当日の残業時間を加算
					overtime += attendanceDto.getOvertime();
					// 法定内残業時間(週40時間超除く)加算
					overtimeIn += attendanceDto.getOvertimeIn();
					// 法定外残業時間(週40時間超除く)加算
					overtimeOut += attendanceDto.getOvertimeOut();
					overtimeOutForOver45Hours += attendanceDto.getOvertimeOut();
					// 平日時間内時間(平日法定労働時間内残業時間)加算
					workdayOvertimeIn += getWorkdayOvertimeIn(attendanceDto, workOnLegal, workOnPrescribed);
					// 所定休日時間内時間(所定休日法定労働時間内残業時間)加算
					prescribedHolidayOvertimeIn += getPrescribedHolidayOvertimeIn(attendanceDto, workOnPrescribed);
					// 平日時間外時間(平日法定労働時間外残業時間)加算
					workdayOvertimeOut += getWorkdayOvertimeOut(attendanceDto, workOnLegal, workOnPrescribed);
					// 所定休日時間外時間(所定休日法定労働時間外残業時間)加算
					prescribedOvertimeOut += getPrescribedHolidayOvertimeOut(attendanceDto, workOnPrescribed);
					// 法定内残業時間(週40時間超除く)加算
					overtimeInNoWeeklyForty += attendanceDto.getOvertimeIn();
					// 法定外残業時間(週40時間超除く)加算
					overtimeOutNoWeeklyForty += attendanceDto.getOvertimeOut();
					// 平日時間内時間(週40時間超除く)加算
					weekDayOvertimeInNoWeeklyForty += getWorkdayOvertimeIn(attendanceDto, workOnLegal, workOnPrescribed);
					// 平日時間外時間(週40時間超除く)加算
					weekDayOvertimeOutNoWeeklyForty += getWorkdayOvertimeOut(attendanceDto, workOnLegal,
							workOnPrescribed);
				}
				if (!overtimeCountFlag && attendanceDto.getOvertime() > 0) {
					// 残業回数を加算
					timesOvertime++;
				}
				lateNight += attendanceDto.getLateNightTime();
				decreaseTime += attendanceDto.getDecreaseTime();
				if (!noApproval && cutoffDto.getNoApproval() != 3 && !isWorkOnHoliday) {
					// 未承認仮締が有効でなく且つ未承認仮締が無効(残業申請なし)でなく且つ休日出勤でない場合
					// 残業申請チェック
					checkOvertimeRequest(list, attendanceDto, humanDto, amHoliday, pmHoliday);
				}
				if (attendanceDto.getLateTime() > 0) {
					// 遅刻時間が0分を超える場合
					lateDays++;
					lateTime += attendanceDto.getLateTime();
					if (attendanceDto.getLateTime() < TimeConst.TIME_HURF_HOUR_MINUTES) {
						// 遅刻時間が30分未満の場合
						lateLessThanThirtyMinutes++;
						lateLessThanThirtyMinutesTime += attendanceDto.getLateTime();
					} else {
						// 遅刻時間が30分以上の場合
						lateThirtyMinutesOrMore++;
						lateThirtyMinutesOrMoreTime += attendanceDto.getLateTime();
					}
					timesLate++;
				}
				if (attendanceDto.getLeaveEarlyTime() > 0) {
					// 早退時間が0分を超える場合
					leaveEarlyDays++;
					leaveEarlyTime += attendanceDto.getLeaveEarlyTime();
					if (attendanceDto.getLeaveEarlyTime() < TimeConst.TIME_HURF_HOUR_MINUTES) {
						// 遅刻時間が30分未満の場合
						leaveEarlyLessThanThirtyMinutes++;
						leaveEarlyLessThanThirtyMinutesTime += attendanceDto.getLeaveEarlyTime();
					} else {
						// 遅刻時間が30分以上の場合
						leaveEarlyThirtyMinutesOrMore++;
						leaveEarlyThirtyMinutesOrMoreTime += attendanceDto.getLeaveEarlyTime();
					}
					timesLeaveEarly++;
				}
				// 法定代休発生日数・所定代休発生日数・深夜代休発生日数
				List<SubHolidayDtoInterface> subHolidayOccurredList = subHolidayDao.findForList(personalId, date);
				for (SubHolidayDtoInterface subHolidayDto : subHolidayOccurredList) {
					int subHolidayType = subHolidayDto.getSubHolidayType();
					double subHolidayDays = subHolidayDto.getSubHolidayDays();
					if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
						// 法定代休の場合
						legalCompensationOccurred += subHolidayDays;
						continue;
					} else if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
						// 所定代休の場合
						specificCompensationOccurred += subHolidayDays;
						continue;
					} else if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
						// 深夜代休の場合
						lateCompensationOccurred += subHolidayDays;
					}
				}
				statutoryHolidayWorkTimeIn += getStatutoryHolidayWorkTimeIn(attendanceDto);
				statutoryHolidayWorkTimeOut += getStatutoryHolidayWorkTimeOut(attendanceDto);
				prescribedHolidayWorkTimeIn += getPrescribedHolidayWorkTimeIn(attendanceDto);
				prescribedHolidayWorkTimeOut += getPrescribedHolidayWorkTimeOut(attendanceDto);
				// 契約勤務時間
				contractWorkTime += getContractWorkTime(attendanceDto);
				// 無給時短時間
				shortUnpaid += attendanceDto.getShortUnpaid();
			}
			if (workFlag) {
				// 出勤扱い加算
				timesAchievement++;
			}
			if (workDateFlag) {
				// 出勤対象加算
				timesTotalWorkDate++;
			}
		}
		// 残業
		if (overtimeOutForOver45Hours > TimeConst.CODE_DEFINITION_HOUR * TimeConst.CODE_DEFINITION_HOUR) {
			// 法定外残業時間が60時間以上の場合
			sixtyHourOvertime = overtimeOutForOver45Hours - TimeConst.CODE_DEFINITION_HOUR
					* TimeConst.CODE_DEFINITION_HOUR;
			fortyFiveHourOvertime = 15 * TimeConst.CODE_DEFINITION_HOUR;
		} else if (overtimeOutForOver45Hours >= LEGAL_OVERTIME_WORK * TimeConst.CODE_DEFINITION_HOUR) {
			// 法定外残業時間が45時間以上60時間未満の場合
			sixtyHourOvertime = 0;
			fortyFiveHourOvertime = overtimeOutForOver45Hours - LEGAL_OVERTIME_WORK * TimeConst.CODE_DEFINITION_HOUR;
		}
		// 手当合計
		List<TotalAllowanceDtoInterface> totalAllowanceList = totalAllowanceDao.findForList(personalId,
				calculationYear, calculationMonth);
		for (TotalAllowanceDtoInterface totalAllowanceDto : totalAllowanceList) {
			totalAllowance += totalAllowanceDto.getTimes();
		}
		// 勤怠設定情報取得
		ApplicationDtoInterface applicationDto = applicationMap.get(personalLastDate);
		if (applicationDto == null) {
			return list;
		}
		TimeSettingDtoInterface timeSettingDto = timeSettingDao.findForInfo(applicationDto.getWorkSettingCode(),
				personalLastDate);
		if (timeSettingDto == null) {
			return list;
		}
		// 勤怠集計情報に値を設定
		dto.setWorkTime(roundMonthlyWork(workTime, timeSettingDto));
//		dto.setSpecificWorkTime(getRoundMinute(specificWorkTime, timeSettingDto.getRoundMonthlyWork(), timeSettingDto
//			.getRoundMonthlyWorkUnit()));
		dto.setTimesWorkDate(timesWorkDate);
		dto.setTimesWork(timesWork);
		dto.setLegalWorkOnHoliday(legalWorkOnHoliday);
		dto.setSpecificWorkOnHoliday(specificWorkOnHoliday);
		dto.setTimesAchievement(timesAchievement);
		dto.setTimesTotalWorkDate(timesTotalWorkDate);
		dto.setDirectStart(directStart);
		dto.setDirectEnd(directEnd);
		dto.setRestTime(getRoundMinute(restTime, timeSettingDto.getRoundMonthlyRest(),
				timeSettingDto.getRoundMonthlyRestUnit()));
		dto.setRestLateNight(getRoundMinute(restLateNight, timeSettingDto.getRoundMonthlyRest(),
				timeSettingDto.getRoundMonthlyRestUnit()));
		dto.setRestWorkOnSpecificHoliday(getRoundMinute(restWorkOnSpecificHoliday,
				timeSettingDto.getRoundMonthlyRest(), timeSettingDto.getRoundMonthlyRestUnit()));
		dto.setRestWorkOnHoliday(getRoundMinute(restWorkOnHoliday, timeSettingDto.getRoundMonthlyRest(),
				timeSettingDto.getRoundMonthlyRestUnit()));
		dto.setPublicTime(getRoundMinute(publicTime, timeSettingDto.getRoundMonthlyPublic(),
				timeSettingDto.getRoundMonthlyPublicTime()));
		dto.setPrivateTime(getRoundMinute(privateTime, timeSettingDto.getRoundMonthlyPrivate(),
				timeSettingDto.getRoundMonthlyPrivateTime()));
		dto.setMinutelyHolidayATime(minutelyHolidayATime);
		dto.setMinutelyHolidayBTime(minutelyHolidayBTime);
//		dto.setOvertime(getRoundMinute(overtime, timeSettingDto.getRoundMonthlyWork(), timeSettingDto
//			.getRoundMonthlyWorkUnit()));
//		dto.setOvertimeIn(getRoundMinute(overtimeIn, timeSettingDto.getRoundMonthlyWork(), timeSettingDto
//			.getRoundMonthlyWorkUnit()));
		setTotalTimeOvertimeIn(dto, overtimeIn, workdayOvertimeIn, timeSettingDto);
//		dto.setOvertimeOut(getRoundMinute(overtimeOut, timeSettingDto.getRoundMonthlyWork(), timeSettingDto
//			.getRoundMonthlyWorkUnit()));
		setTotalTimeOvertimeOut(dto, overtimeOut, workdayOvertimeOut, timeSettingDto);
		dto.setOvertime(dto.getOvertimeIn() + dto.getOvertimeOut());
		dto.setLateNight(roundMonthlyWork(lateNight, timeSettingDto));
		dto.setWorkOnSpecificHoliday(roundMonthlyWork(workOnSpecificHoliday, timeSettingDto));
		dto.setWorkOnHoliday(roundMonthlyWork(workOnHoliday, timeSettingDto));
		dto.setDecreaseTime(getRoundMinute(decreaseTime, timeSettingDto.getRoundMonthlyDecrease(),
				timeSettingDto.getRoundMonthlyDecreaseTime()));
		dto.setFortyFiveHourOvertime(roundMonthlyWork(fortyFiveHourOvertime, timeSettingDto));
		dto.setTimesOvertime(timesOvertime);
		dto.setTimesWorkingHoliday(timesWorkingHoliday);
		dto.setLateDays(lateDays);
		dto.setLateThirtyMinutesOrMore(lateThirtyMinutesOrMore);
		dto.setLateLessThanThirtyMinutes(lateLessThanThirtyMinutes);
		dto.setLateTime(getRoundMinute(lateTime, timeSettingDto.getRoundMonthlyLate(),
				timeSettingDto.getRoundMonthlyLateUnit()));
		dto.setLateThirtyMinutesOrMoreTime(getRoundMinute(lateThirtyMinutesOrMoreTime,
				timeSettingDto.getRoundMonthlyLate(), timeSettingDto.getRoundMonthlyLateUnit()));
		dto.setLateLessThanThirtyMinutesTime(getRoundMinute(lateLessThanThirtyMinutesTime,
				timeSettingDto.getRoundMonthlyLate(), timeSettingDto.getRoundMonthlyLateUnit()));
		dto.setTimesLate(timesLate);
		dto.setLeaveEarlyDays(leaveEarlyDays);
		dto.setLeaveEarlyThirtyMinutesOrMore(leaveEarlyThirtyMinutesOrMore);
		dto.setLeaveEarlyLessThanThirtyMinutes(leaveEarlyLessThanThirtyMinutes);
		dto.setLeaveEarlyTime(getRoundMinute(leaveEarlyTime, timeSettingDto.getRoundMonthlyEarly(),
				timeSettingDto.getRoundMonthlyEarlyUnit()));
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(getRoundMinute(leaveEarlyThirtyMinutesOrMoreTime,
				timeSettingDto.getRoundMonthlyEarly(), timeSettingDto.getRoundMonthlyEarlyUnit()));
		dto.setLeaveEarlyLessThanThirtyMinutesTime(getRoundMinute(leaveEarlyLessThanThirtyMinutesTime,
				timeSettingDto.getRoundMonthlyEarly(), timeSettingDto.getRoundMonthlyEarlyUnit()));
		dto.setTimesLeaveEarly(timesLeaveEarly);
		dto.setTimesHoliday(timesHoliday);
		dto.setTimesLegalHoliday(timesLegalHoliday);
		dto.setTimesSpecificHoliday(timesSpecificHoliday);
		dto.setTimesPaidHoliday(timesPaidHoliday);
		dto.setPaidHolidayHour(paidHolidayHour);
		dto.setTimesStockHoliday(timesStockHoliday);
		dto.setTimesCompensation(timesCompensation);
		dto.setTimesLegalCompensation(timesLegalCompensation);
		dto.setTimesSpecificCompensation(timesSpecificCompensation);
		dto.setTimesLateCompensation(timesLateCompensation);
		dto.setTimesHolidaySubstitute(timesHolidaySubstitute);
		dto.setTimesLegalHolidaySubstitute(timesLegalHolidaySubstitute);
		dto.setTimesSpecificHolidaySubstitute(timesSpecificHolidaySubstitute);
		dto.setTotalAllowance(totalAllowance);
		dto.setSixtyHourOvertime(roundMonthlyWork(sixtyHourOvertime, timeSettingDto));
		dto.setWeekDayOvertime(roundMonthlyWork(workdayOvertimeOut, timeSettingDto));
		dto.setSpecificOvertime(roundMonthlyWork(prescribedOvertimeOut, timeSettingDto));
		dto.setTimesAlternative(timesAlternative);
		dto.setLegalCompensationOccurred(legalCompensationOccurred);
		dto.setSpecificCompensationOccurred(specificCompensationOccurred);
		dto.setLateCompensationOccurred(lateCompensationOccurred);
		dto.setLegalCompensationUnused(legalCompensationUnused);
		dto.setSpecificCompensationUnused(specificCompensationUnused);
		dto.setLateCompensationUnused(lateCompensationUnused);
		dto.setStatutoryHolidayWorkTimeIn(roundMonthlyWork(statutoryHolidayWorkTimeIn, timeSettingDto));
		dto.setStatutoryHolidayWorkTimeOut(roundMonthlyWork(statutoryHolidayWorkTimeOut, timeSettingDto));
		dto.setPrescribedHolidayWorkTimeIn(roundMonthlyWork(prescribedHolidayWorkTimeIn, timeSettingDto));
		dto.setPrescribedHolidayWorkTimeOut(roundMonthlyWork(prescribedHolidayWorkTimeOut, timeSettingDto));
		dto.setSpecificWorkTime(roundMonthlyWork(workTime - overtimeIn - overtimeOut - workOnHoliday, timeSettingDto));
		dto.setContractWorkTime(roundMonthlyWork(contractWorkTime, timeSettingDto));
		dto.setShortUnpaid(getRoundMinute(shortUnpaid, timeSettingDto.getRoundMonthlyShortUnpaid(),
				timeSettingDto.getRoundMonthlyShortUnpaidUnit()));
		dto.setWeeklyOverFortyHourWorkTime(roundMonthlyWork(weeklyOverFortyHourWorkTime, timeSettingDto));
		dto.setOvertimeInNoWeeklyForty(roundMonthlyWork(overtimeInNoWeeklyForty, timeSettingDto));
		dto.setOvertimeOutNoWeeklyForty(roundMonthlyWork(overtimeOutNoWeeklyForty, timeSettingDto));
		dto.setWeekDayOvertimeTotal(roundMonthlyWork(workdayOvertimeIn + workdayOvertimeOut, timeSettingDto));
		dto.setWeekDayOvertimeInNoWeeklyForty(roundMonthlyWork(weekDayOvertimeInNoWeeklyForty, timeSettingDto));
		dto.setWeekDayOvertimeOutNoWeeklyForty(roundMonthlyWork(weekDayOvertimeOutNoWeeklyForty, timeSettingDto));
		dto.setWeekDayOvertimeIn(roundMonthlyWork(workdayOvertimeIn, timeSettingDto));
		dto.setPrescribedHolidayOvertimeIn(prescribedHolidayOvertimeIn);
		// 集計時エラー内容リスト確認
		if (list.isEmpty() == false) {
			return list;
		}
		// 勤怠集計データを登録
		regist(dto, needRegist);
		return list;
	}
	
	/**
	 * 勤怠集計データを登録する。<br>
	 * 但し、登録不要の場合は、何もしない。<br>
	 * @param dto        勤怠集計データ
	 * @param needRegist 登録要否(true：登録要、false：登録不要)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void regist(TotalTimeDataDtoInterface dto, boolean needRegist) throws MospException {
		// 登録要否確認
		if (needRegist) {
			// 勤怠集計データ登録
			totalTimeRegist.regist(dto);
		}
	}
	
	/**
	 * 所定労働時間内法定休日労働時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間内法定休日労働時間
	 */
	protected int getStatutoryHolidayWorkTimeIn(AttendanceDtoInterface dto) {
		return dto.getStatutoryHolidayWorkTimeIn();
	}
	
	/**
	 * 所定労働時間外法定休日労働時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間外法定休日労働時間
	 */
	protected int getStatutoryHolidayWorkTimeOut(AttendanceDtoInterface dto) {
		return dto.getStatutoryHolidayWorkTimeOut();
	}
	
	/**
	 * 所定労働時間内所定休日労働時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間内所定休日労働時間
	 */
	protected int getPrescribedHolidayWorkTimeIn(AttendanceDtoInterface dto) {
		return dto.getPrescribedHolidayWorkTimeIn();
	}
	
	/**
	 * 所定労働時間外所定休日労働時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間外所定休日労働時間
	 */
	protected int getPrescribedHolidayWorkTimeOut(AttendanceDtoInterface dto) {
		return dto.getPrescribedHolidayWorkTimeOut();
	}
	
	/**
	 * 平日法定労働時間内残業時間を取得する。<br>
	 * @param dto 対象DTO
	 * @param isWorkOnLegal 法定休日労働の場合true、そうでない場合false
	 * @param isWorkOnPrescribed 所定休日労働の場合true、そうでない場合false
	 * @return 平日法定労働時間内残業時間
	 */
	protected int getWorkdayOvertimeIn(AttendanceDtoInterface dto, boolean isWorkOnLegal, boolean isWorkOnPrescribed) {
		if (dto.getWorkdayOvertimeIn() > 0) {
			// 平日法定時間内残業時間が0より大きい場合
			return dto.getWorkdayOvertimeIn();
		}
		if (isWorkOnLegal || isWorkOnPrescribed) {
			// 法定休日労働又は所定休日労働の場合
			return 0;
		}
		if (dto.getOvertimeIn() > 0 && dto.getWorkdayOvertimeIn() == 0 && dto.getPrescribedHolidayOvertimeIn() == 0) {
			// 法定内残業時間が0より大きく
			// 且つ平日法定内残業時間が0
			// 且つ所定休日法定内残業時間が0の場合
			return dto.getOvertimeIn();
		}
		return 0;
	}
	
	/**
	 * 平日法定労働時間外残業時間を取得する。<br>
	 * @param dto 対象DTO
	 * @param isWorkOnLegal 法定休日労働の場合true、そうでない場合false
	 * @param isWorkOnPrescribed 所定休日労働の場合true、そうでない場合false
	 * @return 平日法定労働時間外残業時間
	 */
	protected int getWorkdayOvertimeOut(AttendanceDtoInterface dto, boolean isWorkOnLegal, boolean isWorkOnPrescribed) {
		if (dto.getWorkdayOvertimeOut() > 0) {
			// 平日法定時間外残業時間が0より大きい場合
			return dto.getWorkdayOvertimeOut();
		}
		if (isWorkOnLegal || isWorkOnPrescribed) {
			// 法定休日労働又は所定休日労働の場合
			return 0;
		}
		if (dto.getOvertimeOut() > 0 && dto.getWorkdayOvertimeOut() == 0 && dto.getPrescribedHolidayOvertimeOut() == 0) {
			// 法定外残業時間が0より大きく
			// 且つ平日法定外残業時間が0
			// 且つ所定休日法定外残業時間が0の場合の場合
			return dto.getOvertimeOut();
		}
		return 0;
	}
	
	/**
	 * 所定休日法定労働時間内残業時間を取得する。<br>
	 * @param dto 対象DTO
	 * @param isWorkOnPrescribed 所定休日労働の場合true、そうでない場合false
	 * @return 所定休日法定労働時間内残業時間
	 */
	protected int getPrescribedHolidayOvertimeIn(AttendanceDtoInterface dto, boolean isWorkOnPrescribed) {
		if (dto.getPrescribedHolidayOvertimeIn() > 0) {
			// 所定休日法定時間内残業時間が0より大きい場合
			return dto.getPrescribedHolidayOvertimeIn();
		}
		if (!isWorkOnPrescribed) {
			// 所定休日労働でない場合
			return 0;
		}
		if (dto.getOvertimeIn() > 0 && dto.getWorkdayOvertimeIn() == 0 && dto.getPrescribedHolidayOvertimeIn() == 0) {
			// 法定内残業時間が0より大きく
			// 且つ平日法定内残業時間が0
			// 且つ所定休日法定内残業時間が0の場合
			return dto.getOvertimeIn();
		}
		return 0;
	}
	
	/**
	 * 所定休日法定労働時間外残業時間を取得する。<br>
	 * @param dto 対象DTO
	 * @param isWorkOnPrescribed 所定休日労働の場合true、そうでない場合false
	 * @return 所定休日法定労働時間外残業時間
	 */
	protected int getPrescribedHolidayOvertimeOut(AttendanceDtoInterface dto, boolean isWorkOnPrescribed) {
		if (dto.getPrescribedHolidayOvertimeOut() > 0) {
			// 所定休日法定時間外残業時間が0より大きい場合
			return dto.getPrescribedHolidayOvertimeOut();
		}
		if (!isWorkOnPrescribed) {
			// 所定休日労働でない場合
			return 0;
		}
		if (dto.getOvertimeOut() > 0 && dto.getWorkdayOvertimeOut() == 0 && dto.getPrescribedHolidayOvertimeOut() == 0) {
			// 法定外残業時間が0より大きく
			// 且つ平日法定外残業時間が0
			// 且つ所定休日法定外残業時間が0の場合
			return dto.getOvertimeOut();
		}
		return 0;
	}
	
	/**
	 * 契約勤務時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 契約勤務時間
	 */
	protected int getContractWorkTime(AttendanceDtoInterface dto) {
		int contractWorkTime = dto.getWorkTime() - dto.getOvertime() - dto.getLegalWorkTime();
		if (TimeConst.CODE_TARDINESS_WHY_TRAIN.equals(dto.getLateReason())
				|| TimeConst.CODE_TARDINESS_WHY_COMPANY.equals(dto.getLateReason())) {
			// 遅刻理由が電車遅延又は会社指示の場合は実遅刻時間を加算
			contractWorkTime += dto.getActualLateTime();
		}
		if (TimeConst.CODE_LEAVEEARLY_WHY_COMPANY.equals(dto.getLeaveEarlyReason())) {
			// 早退理由が会社指示の場合は実早退時間を加算
			contractWorkTime += dto.getActualLeaveEarlyTime();
		}
		return contractWorkTime;
	}
	
	/**
	 * 法定内残業時間をセットする。
	 * @param dto 対象DTO
	 * @param overtimeIn 法定内残業時間数
	 * @param workdayOvertimeIn 平日法定内残業時間数
	 * @param timeSettingDto 勤怠設定管理DTO
	 */
	protected void setTotalTimeOvertimeIn(TotalTimeDataDtoInterface dto, int overtimeIn, int workdayOvertimeIn,
			TimeSettingDtoInterface timeSettingDto) {
		dto.setOvertimeIn(roundMonthlyWork(overtimeIn, timeSettingDto));
	}
	
	/**
	 * 法定外残業時間をセットする。
	 * @param dto 対象DTO
	 * @param overtimeOut 法定外残業時間数
	 * @param workdayOvertimeOut 平日法定外残業時間数
	 * @param timeSettingDto 勤怠設定管理DTO
	 */
	protected void setTotalTimeOvertimeOut(TotalTimeDataDtoInterface dto, int overtimeOut, int workdayOvertimeOut,
			TimeSettingDtoInterface timeSettingDto) {
		dto.setOvertimeOut(roundMonthlyWork(overtimeOut, timeSettingDto));
	}
	
	/**
	 * 週残業時間計算用配列取得。<br>
	 * @param dto 勤怠DTO
	 * @param yesterdayDto 前日勤怠DTO
	 * @param yesterdayWorkOnLegal 前日が法定休日労働の場合true、そうでない場合false
	 * @param timeSettingDto 勤怠設定管理DTO
	 * @return 週残業時間計算用配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int[] getWeekOvertimeCalculationArray(AttendanceDtoInterface dto, AttendanceDtoInterface yesterdayDto,
			boolean yesterdayWorkOnLegal, TimeSettingDtoInterface timeSettingDto) throws MospException {
		if (dto == null) {
			int[] yesterdayArray = getIntArray(0, 0, 0, 0, 0, 0, 0, 0);
			if (yesterdayWorkOnLegal) {
				// 前日が法定休日出勤の場合
				yesterdayArray = getIntArray(0, yesterdayDto.getWorkdayOvertimeIn(), 0,
						yesterdayDto.getPrescribedHolidayOvertimeIn(), 0, 0, 0, 0);
				if (yesterdayDto.getOvertimeIn() > 0 && yesterdayDto.getWorkdayOvertimeIn() == 0
						&& yesterdayDto.getPrescribedHolidayOvertimeIn() == 0) {
					// 前日の法定内残業時間が0以上
					// 且つ前日の平日法定内残業時間が0
					// 且つ前日の所定休日法定内残業時間が0の場合
					yesterdayArray = getIntArray(0, yesterdayDto.getOvertimeIn(), 0, 0, 0, 0, 0, 0);
				}
			}
			return yesterdayArray;
		}
		WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
		if (workflowDto == null || !PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			int[] yesterdayArray = getIntArray(0, 0, 0, 0, 0, 0, 0, 0);
			if (yesterdayWorkOnLegal) {
				// 前日が法定休日出勤の場合
				yesterdayArray = getIntArray(0, yesterdayDto.getWorkdayOvertimeIn(), 0,
						yesterdayDto.getPrescribedHolidayOvertimeIn(), 0, 0, 0, 0);
				if (yesterdayDto.getOvertimeIn() > 0 && yesterdayDto.getWorkdayOvertimeIn() == 0
						&& yesterdayDto.getPrescribedHolidayOvertimeIn() == 0) {
					// 前日の法定内残業時間が0以上
					// 且つ前日の平日法定内残業時間が0
					// 且つ前日の所定休日法定内残業時間が0の場合
					yesterdayArray = getIntArray(0, yesterdayDto.getOvertimeIn(), 0, 0, 0, 0, 0, 0);
				}
			}
			return yesterdayArray;
		}
		int todayWorkdayPrescribedWork = 0;
		int todayWorkdayOvertimeIn = 0;
		int todayPrescribedHolidayPrescribedWork = 0;
		int todayPrescribedHolidayOvertimeIn = 0;
		int nextDayWorkdayPrescribedWork = 0;
		int nextDayWorkdayOvertimeIn = 0;
		int nextDayPrescribedHolidayPrescribedWork = 0;
		int nextDayPrescribedHolidayOvertimeIn = 0;
		int yesterdayWorkdayOvertimeIn = 0;
		int yesterdayPrescribedHolidayOvertimeIn = 0;
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 法定休日労働の場合
			todayWorkdayPrescribedWork = 0;
			todayWorkdayOvertimeIn = 0;
			todayPrescribedHolidayPrescribedWork = 0;
			todayPrescribedHolidayOvertimeIn = 0;
			nextDayWorkdayPrescribedWork = 0;
			nextDayWorkdayOvertimeIn = 0;
			nextDayPrescribedHolidayPrescribedWork = 0;
			nextDayPrescribedHolidayOvertimeIn = 0;
		} else if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 所定休日出勤の場合
			todayWorkdayPrescribedWork = 0;
			todayWorkdayOvertimeIn = 0;
			todayPrescribedHolidayPrescribedWork = 0;
			todayPrescribedHolidayOvertimeIn = dto.getPrescribedHolidayOvertimeIn();
			nextDayWorkdayPrescribedWork = 0;
			nextDayWorkdayOvertimeIn = dto.getWorkdayOvertimeIn();
			nextDayPrescribedHolidayPrescribedWork = 0;
			nextDayPrescribedHolidayOvertimeIn = 0;
			if (yesterdayWorkOnLegal) {
				// 前日が法定休日出勤の場合
				yesterdayPrescribedHolidayOvertimeIn = yesterdayDto.getPrescribedHolidayOvertimeIn();
			}
		} else {
			// 平日の場合
			todayWorkdayPrescribedWork = dto.getWorkTime() - dto.getLegalWorkTime() - dto.getSpecificWorkTime()
					- dto.getWorkdayOvertimeIn() - dto.getWorkdayOvertimeOut();
			todayWorkdayOvertimeIn = dto.getWorkdayOvertimeIn();
			todayPrescribedHolidayPrescribedWork = 0;
			todayPrescribedHolidayOvertimeIn = 0;
			nextDayWorkdayPrescribedWork = 0;
			nextDayWorkdayOvertimeIn = 0;
			nextDayPrescribedHolidayPrescribedWork = dto.getSpecificWorkTime() - dto.getPrescribedHolidayOvertimeIn()
					- dto.getPrescribedHolidayOvertimeOut();
			nextDayPrescribedHolidayOvertimeIn = dto.getPrescribedHolidayOvertimeIn();
			if (yesterdayWorkOnLegal) {
				// 前日が法定休日出勤の場合
				yesterdayWorkdayOvertimeIn = yesterdayDto.getWorkdayOvertimeIn();
			}
		}
		if (dto.getOvertime() > 0 && dto.getWorkdayOvertimeIn() == 0 && dto.getWorkdayOvertimeOut() == 0
				&& dto.getPrescribedHolidayOvertimeIn() == 0 && dto.getPrescribedHolidayOvertimeOut() == 0) {
			// 残業時間が0以上
			// 且つ平日法定内残業時間が0
			// 且つ平日法定時間外残業時間が0
			// 且つ所定休日法定内残業時間が0
			// 且つ所定休日法定時間外残業時間が0の場合
			if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())) {
				// 法定休日労働の場合
				todayWorkdayPrescribedWork = 0;
				todayWorkdayOvertimeIn = 0;
				todayPrescribedHolidayPrescribedWork = 0;
				todayPrescribedHolidayOvertimeIn = 0;
				nextDayWorkdayPrescribedWork = 0;
				nextDayWorkdayOvertimeIn = 0;
				nextDayPrescribedHolidayPrescribedWork = 0;
				nextDayPrescribedHolidayOvertimeIn = 0;
			} else if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
				// 所定休日出勤の場合
				todayWorkdayPrescribedWork = 0;
				todayWorkdayOvertimeIn = 0;
				todayPrescribedHolidayPrescribedWork = 0;
				todayPrescribedHolidayOvertimeIn = dto.getOvertimeIn();
				nextDayWorkdayPrescribedWork = 0;
				nextDayWorkdayOvertimeIn = 0;
				nextDayPrescribedHolidayPrescribedWork = 0;
				nextDayPrescribedHolidayOvertimeIn = 0;
				if (yesterdayWorkOnLegal) {
					// 前日が法定休日出勤の場合
					yesterdayPrescribedHolidayOvertimeIn = yesterdayDto.getOvertimeIn();
				}
			} else {
				// 平日の場合
				todayWorkdayPrescribedWork = dto.getWorkTime() - dto.getLegalWorkTime() - dto.getOvertimeIn()
						- dto.getOvertimeOut();
				todayWorkdayOvertimeIn = dto.getOvertimeIn();
				todayPrescribedHolidayPrescribedWork = 0;
				todayPrescribedHolidayOvertimeIn = 0;
				nextDayWorkdayPrescribedWork = 0;
				nextDayWorkdayOvertimeIn = 0;
				nextDayPrescribedHolidayPrescribedWork = 0;
				nextDayPrescribedHolidayOvertimeIn = 0;
				if (yesterdayWorkOnLegal) {
					// 前日が法定休日出勤の場合
					yesterdayWorkdayOvertimeIn = yesterdayDto.getOvertimeIn();
				}
			}
		}
		return getIntArray(todayWorkdayPrescribedWork, yesterdayWorkdayOvertimeIn + todayWorkdayOvertimeIn,
				todayPrescribedHolidayPrescribedWork, yesterdayPrescribedHolidayOvertimeIn
						+ todayPrescribedHolidayOvertimeIn, nextDayWorkdayPrescribedWork, nextDayWorkdayOvertimeIn,
				nextDayPrescribedHolidayPrescribedWork, nextDayPrescribedHolidayOvertimeIn);
	}
	
	/**
	 * 無給時短時間を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 無給時短時間
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int getShortUnpaid(String personalId, Date targetDate) throws MospException {
		String workTypeCode = getScheduleWorkTypeCode(personalId, targetDate, true);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			return 0;
		}
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, targetDate);
		if (workTypeEntity == null) {
			return 0;
		}
		int shortTime = 0;
		if (workTypeEntity.isShort1TimeSet() && !workTypeEntity.isShort1TypePay()) {
			// 時短時間1が無給の場合
			shortTime += TimeUtility.getMinutes(workTypeEntity.getShort1EndTime())
					- TimeUtility.getMinutes(workTypeEntity.getShort1StartTime());
		}
		if (workTypeEntity.isShort2TimeSet() && !workTypeEntity.isShort2TypePay()) {
			// 時短時間2が無給の場合
			shortTime += TimeUtility.getMinutes(workTypeEntity.getShort2EndTime())
					- TimeUtility.getMinutes(workTypeEntity.getShort2StartTime());
		}
		return shortTime;
	}
	
	/**
	 * 特別休暇の集計をする。<br>
	 * @param personalId 対象個人ID
	 * @param needRegist 登録要否(true：登録要、false：登録不要)
	 * @return 特別休暇回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected double calcToalLeave(String personalId, boolean needRegist) throws MospException {
		// 特別休暇回数準備
		double totalLeave = 0D;
		// 特別休暇集計データ削除
		if (needRegist) {
			totalLeaveRegist.delete(personalId, calculationYear, calculationMonth);
		}
		// 休暇種別マスタ毎に処理
		for (HolidayDtoInterface holidayDto : specialHolidayList) {
			// 休暇種別マスタ毎の休暇回数を取得
			double times = calcHoliday(personalId, TimeConst.CODE_HOLIDAYTYPE_SPECIAL, holidayDto.getHolidayCode());
			// 特別休暇回数に加算
			totalLeave += times;
			// 登録要否確認
			if (needRegist == false) {
				continue;
			}
			// 特別休暇集計データ登録
			TotalLeaveDtoInterface dto = totalLeaveRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(calculationYear);
			dto.setCalculationMonth(calculationMonth);
			dto.setHolidayCode(holidayDto.getHolidayCode());
			dto.setTimes(times);
			totalLeaveRegist.insert(dto);
			
		}
		return totalLeave;
	}
	
	/**
	 * その他休暇の集計をする。<br>
	 * @param personalId 対象個人ID
	 * @param needRegist 登録要否(true：登録要、false：登録不要)
	 * @return その他休暇回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected double calcTotalOtherVacation(String personalId, boolean needRegist) throws MospException {
		// その他休暇回数準備
		double totalOtherVacation = 0D;
		// その他休暇集計データ削除
		if (needRegist) {
			totalOtherVacationRegist.delete(personalId, calculationYear, calculationMonth);
		}
		// 休暇種別マスタ毎に処理
		for (HolidayDtoInterface holidayDto : otherHolidayList) {
			// 休暇種別マスタ毎の休暇回数を取得
			double times = calcHoliday(personalId, TimeConst.CODE_HOLIDAYTYPE_OTHER, holidayDto.getHolidayCode());
			// その他休暇回数に加算
			totalOtherVacation += times;
			// 登録要否確認
			if (needRegist == false) {
				continue;
			}
			// その他休暇集計データ登録
			TotalOtherVacationDtoInterface dto = totalOtherVacationRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(calculationYear);
			dto.setCalculationMonth(calculationMonth);
			dto.setHolidayCode(holidayDto.getHolidayCode());
			dto.setTimes(times);
			totalOtherVacationRegist.insert(dto);
		}
		return totalOtherVacation;
	}
	
	/**
	 * 欠勤の集計をする。<br>
	 * @param personalId 対象個人ID
	 * @param needRegist 登録要否(true：登録要、false：登録不要)
	 * @return 欠勤回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected double calcTotalAbsence(String personalId, boolean needRegist) throws MospException {
		// 欠勤回数準備
		double totalAbsence = 0D;
		// 欠勤集計データ削除
		if (needRegist) {
			totalAbsenceRegist.delete(personalId, calculationYear, calculationMonth);
		}
		// 休暇種別マスタ毎に処理
		for (HolidayDtoInterface holidayDto : absenceList) {
			// 休暇種別マスタ毎の休暇回数を取得
			double times = calcHoliday(personalId, TimeConst.CODE_HOLIDAYTYPE_ABSENCE, holidayDto.getHolidayCode());
			// 欠勤回数に加算
			totalAbsence += times;
			// 登録要否確認
			if (needRegist == false) {
				continue;
			}
			// 欠勤集計データ登録
			TotalAbsenceDtoInterface dto = totalAbsenceRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(calculationYear);
			dto.setCalculationMonth(calculationMonth);
			dto.setAbsenceCode(holidayDto.getHolidayCode());
			dto.setTimes(times);
			totalAbsenceRegist.insert(dto);
		}
		return totalAbsence;
	}
	
	/**
	 * 休暇回数を取得する。<br>
	 * {@link TotalTimeCalcBean#holidayRequestDtoList}から対象休暇種別コードの
	 * 休暇申請情報を取得し、承認完了の申請をカウントする。<br>
	 * @param personalId  対象個人ID
	 * @param holidayType 対象休暇区分
	 * @param holidayCode 対象休暇種別コード
	 * @return 休暇回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected double calcHoliday(String personalId, int holidayType, String holidayCode) throws MospException {
		// 回数準備
		double times = 0;
		// 締期間(対象日)内の日毎に処理
		for (Date date : targetDateList) {
			// 振出・休出申請取得
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDtoListDto(date);
			if (workOnHolidayRequestDto != null && workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
				// 振出・休出申請が承認済である場合
				if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 休日出勤の場合
					continue;
				}
			} else {
				// 振出・休出申請が承認済でない場合
				// 勤務形態取得
				String workTypeCode = getScheduleWorkTypeCode(personalId, date, true);
				// 勤務形態確認
				if (workTypeCode == null || workTypeCode.isEmpty()) {
					// 勤務形態が取得できない場合
					continue;
				}
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
					// 法定休日又は所定休日の場合
					continue;
				}
			}
			List<HolidayRequestDtoInterface> list = getHolidayRequestDtoList(date, holidayType, holidayCode);
			// 休暇申請毎に処理
			for (HolidayRequestDtoInterface holidayRequestDto : list) {
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(holidayRequestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 承認完了の場合
					if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
						times++;
					} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
							|| holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						times += TimeConst.HOLIDAY_TIMES_HALF;
					}
				}
			}
		}
		return times;
	}
	
	/**
	 * 手当集計。
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param cutoffCode 締日コード
	 * @param allowanceCode 手当コード
	 * @return 手当集計データ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected TotalAllowanceDtoInterface calcTotalAllowance(String personalId, int calculationYear,
			int calculationMonth, String cutoffCode, String allowanceCode) throws MospException {
		// 回数準備
		int times = 0;
		// 締期間で手当情報リストを取得
		List<AllowanceDtoInterface> list = allowanceDao.findForList(personalId, allowanceCode, personalFirstDate,
				personalLastDate);
		// 手当情報毎に処理
		for (AllowanceDtoInterface allowanceDto : list) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDao.findForKey(personalId,
					allowanceDto.getWorkDate(), allowanceDto.getWorks()).getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認完了の場合
				times++;
			}
		}
		TotalAllowanceDtoInterface dto = new TmdTotalAllowanceDto();
		dto.setPersonalId(personalId);
		dto.setCalculationYear(calculationYear);
		dto.setCalculationMonth(calculationMonth);
		dto.setAllowanceCode(allowanceCode);
		dto.setTimes(times);
		return dto;
	}
	
	@Override
	public int getApprovalStatus(String personalId, int year, int month) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoff = cutoffUtil.getCutoffForPersonalId(personalId, year, month);
		// 処理結果確認(締日が取得できなかった場合)
		if (mospParams.hasErrorMessage()) {
			return TimeConst.CODE_NOT_APPROVED_NONE;
		}
		// 計算情報設定
		setCalculationInfo(year, month, cutoff.getCutoffCode());
		// 処理結果確認(集計クラス計算情報設定に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			return TimeConst.CODE_NOT_APPROVED_NONE;
		}
		// 締期間初日(個人)設定
		personalFirstDate = getPersonalFirstDate(personalId);
		// 締期間最終日(個人)設定
		personalLastDate = getPersonalLastDate(personalId);
		// 締期間(個人)確認
		if (personalFirstDate == null || personalLastDate == null) {
			return TimeConst.CODE_NOT_APPROVED_NONE;
		}
		// 個人計算情報設定
		setPersonalInfo(personalId);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return TimeConst.CODE_NOT_APPROVED_NONE;
		}
		// 勤怠
		for (AttendanceDtoInterface attendanceDto : attendanceDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		// 残業
		for (OvertimeRequestDtoInterface requestDto : overtimeRequestDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		// 休暇
		for (HolidayRequestDtoInterface requestDto : holidayRequestDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		// 休日出勤
		for (WorkOnHolidayRequestDtoInterface requestDto : workOnHolidayRequestDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		// 代休
		for (SubHolidayRequestDtoInterface requestDto : subHolidayRequestDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		// 時差出勤
		for (DifferenceRequestDtoInterface requestDto : differenceRequestDtoList) {
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
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
			if (!PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 未承認有り
				return TimeConst.CODE_NOT_APPROVED_EXIST;
			}
		}
		return TimeConst.CODE_NOT_APPROVED_NONE;
	}
	
	/**
	 * 
	 * @param date 対象日付
	 * @param humanDto 人事マスタDTOクラス
	 * @return エラーリスト
	 */
	protected CutoffErrorListDtoInterface getCutoffErrorListDto(Date date, HumanDtoInterface humanDto) {
		CutoffErrorListDtoInterface cutoffErrorListDto = new CutoffErrorListDto();
		cutoffErrorListDto.setDate(date);
		cutoffErrorListDto.setEmployeeCode(humanDto.getEmployeeCode());
		cutoffErrorListDto.setPersonalId(humanDto.getPersonalId());
		cutoffErrorListDto.setLastName(humanDto.getLastName());
		cutoffErrorListDto.setFirstName(humanDto.getFirstName());
		cutoffErrorListDto.setWorkPlaceCode(humanDto.getWorkPlaceCode());
		cutoffErrorListDto.setEmploymentCode(humanDto.getEmploymentContractCode());
		cutoffErrorListDto.setSectionCode(humanDto.getSectionCode());
		cutoffErrorListDto.setPositionCode(humanDto.getPositionCode());
		return cutoffErrorListDto;
	}
	
	/**
	 * 締期間初日(個人)を取得する。<br>
	 * 対象個人IDの入社日が締期間初日よりも後の場合は、入社日を取得する。<br>
	 * 対象個人IDが入社していない場合は、nullを返す。<br>
	 * @param personalId 対象個人ID
	 * @return 締期間初日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalFirstDate(String personalId) throws MospException {
		// 入社日取得
		EntranceDtoInterface entranceDto = entranceDao.findForInfo(personalId);
		// 入社日確認
		if (entranceDto == null) {
			return null;
		}
		// 締期間初日が入社日よりも前の場合
		if (cutoffFirstDate.before(entranceDto.getEntranceDate())) {
			// 入社日を取得
			return entranceDto.getEntranceDate();
		}
		return cutoffFirstDate;
	}
	
	/**
	 * 締期間最終日(個人)を取得する。<br>
	 * 対象個人IDの退社日が締期間最終日よりも前の場合は、退社日を取得する。<br>
	 * @param personalId 対象個人ID
	 * @return 締期間最終日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalLastDate(String personalId) throws MospException {
		// 退社日取得
		RetirementDtoInterface retirementDto = retirementDao.findForInfo(personalId);
		// 退社日確認
		if (retirementDto == null) {
			return cutoffLastDate;
		}
		// 締期間最終日が退社日よりも後の場合
		if (cutoffLastDate.after(retirementDto.getRetirementDate())) {
			return retirementDto.getRetirementDate();
		}
		return cutoffLastDate;
	}
	
	/**
	 * カレンダ日情報群を取得する。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}及び
	 * {@link TotalTimeCalcBean#setPersonalInfo(String)}で設定された情報を基に、
	 * 対象個人IDのカレンダ日情報群を作成する。<br>
	 * 対象日において設定適用情報が取得できない場合は、
	 * 対象日のカレンダ日情報にnullを設定する。
	 * @param personalId 対象個人ID
	 * @return カレンダ日情報群
	 * @throws MospException カレンダ日情報の取得に失敗した場合
	 */
	protected Map<Date, ScheduleDateDtoInterface> getScheduleMap(String personalId) throws MospException {
		// カレンダ日情報郡準備
		Map<Date, ScheduleDateDtoInterface> map = new HashMap<Date, ScheduleDateDtoInterface>();
		// 締期間(対象日)内の日毎に処理
		for (Date targetDate : targetDateList) {
			// 設定適用情報取得
			ApplicationDtoInterface applicationDto = applicationMap.get(targetDate);
			// 設定適用情報が取得できない場合
			if (applicationDto == null) {
				// カレンダ日情報追加
				map.put(targetDate, null);
				continue;
			}
			// カレンダ日情報取得
			ScheduleDateDtoInterface scheduleDateDto = scheduleDateDao.findForInfo(applicationDto.getScheduleCode(),
					targetDate, targetDate);
			// カレンダ日情報確認
			scheduleDate.chkExistScheduleDate(scheduleDateDto, targetDate);
			if (mospParams.hasErrorMessage()) {
				break;
			}
			// カレンダ日情報追加
			map.put(targetDate, scheduleDateDto);
		}
		return map;
	}
	
	/**
	 * 振替休日情報群を取得する。<br>
	 * {@link TotalTimeCalcBean#setCalculationInfo(int, int, String)}及び
	 * {@link TotalTimeCalcBean#setPersonalInfo(String)}で設定された情報を基に、
	 * 対象個人IDの承認済振替休日情報群を作成する。<br>
	 * 但し、承認済であり、振替範囲が全休のもののみを取得対象とする。<br>
	 * @param personalId 対象個人ID
	 * @return 振替休日情報群
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Map<Date, SubstituteDtoInterface> getSubstituteMap(String personalId) throws MospException {
		// 振替休日情報群準備
		Map<Date, SubstituteDtoInterface> map = new HashMap<Date, SubstituteDtoInterface>();
		// 締期間(対象日)内の日毎に処理
		for (Date targetDate : targetDateList) {
			// 振替休日情報リスト取得
			List<SubstituteDtoInterface> list = substituteDao.findForList(personalId, targetDate);
			// 振替休日毎に処理
			for (SubstituteDtoInterface substituteDto : list) {
				// 振替範囲確認
				if (substituteDto.getSubstituteRange() != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休でなければ対象外
					continue;
				}
				// ワークフロー番号からワークフロー情報を取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				// もし承認済ならば
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 振替休日情報追加
					map.put(targetDate, substituteDto);
				}
			}
		}
		return map;
	}
	
	/**
	 * カレンダの勤務形態コードを取得する。<br>
	 * カレンダが取得できない場合はnullを返す。<br>
	 * @param personalId    個人ID
	 * @param targetDate    対象日
	 * @param useSubstitute 振替休日情報考慮要否
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private String getScheduleWorkTypeCode(String personalId, Date targetDate, boolean useSubstitute)
			throws MospException {
		// 振替休日情報考慮要否確認
		if (useSubstitute) {
			// 振替休日情報取得
			SubstituteDtoInterface substituteDto = substituteMap.get(targetDate);
			// 振替休日情報確認
			if (substituteDto != null && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 振替休日情報の休暇種別を取得
				return substituteDto.getSubstituteType();
			}
		}
		// カレンダ存在確認
		if (scheduleMap.get(targetDate) == null) {
			return null;
		}
		// 勤務形態コード
		return scheduleMap.get(targetDate).getWorkTypeCode();
	}
	
	/**
	 * 勤怠情報リストから対象日の勤怠情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 勤怠情報
	 */
	protected AttendanceDtoInterface getAttendanceDtoListDto(Date targetDate) {
		// 勤怠情報リストから対象日の勤怠情報を取得
		for (AttendanceDtoInterface dto : attendanceDtoList) {
			// 勤務日確認
			if (targetDate.equals(dto.getWorkDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 休日出勤申請リストから対象日の休日出勤申請を取得する。<br>
	 * @param targetDate 対象日
	 * @return 休日出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDtoListDto(Date targetDate) throws MospException {
		for (WorkOnHolidayRequestDtoInterface dto : workOnHolidayRequestDtoList) {
			if (targetDate.equals(dto.getRequestDate())) {
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (!PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 取下でない場合
					return dto;
				}
			}
		}
		return null;
	}
	
	/**
	 * 休暇申請リストから対象日の休暇申請リストを取得する。<br>
	 * @param targetDate 対象日
	 * @return 休暇申請リスト
	 */
	protected List<HolidayRequestDtoInterface> getHolidayRequestDtoList(Date targetDate) {
		List<HolidayRequestDtoInterface> list = new ArrayList<HolidayRequestDtoInterface>();
		for (HolidayRequestDtoInterface dto : holidayRequestDtoList) {
			if (!dto.getRequestStartDate().after(targetDate) && !dto.getRequestEndDate().before(targetDate)) {
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 休暇申請リストから対象日の休暇申請リストを取得する。<br>
	 * @param targetDate 対象日
	 * @param holidayType 休暇種別1
	 * @param holidayCode 休暇コード
	 * @return 休暇申請リスト
	 */
	protected List<HolidayRequestDtoInterface> getHolidayRequestDtoList(Date targetDate, int holidayType,
			String holidayCode) {
		List<HolidayRequestDtoInterface> list = new ArrayList<HolidayRequestDtoInterface>();
		for (HolidayRequestDtoInterface dto : holidayRequestDtoList) {
			if (dto.getRequestStartDate().after(targetDate)) {
				continue;
			}
			if (dto.getRequestEndDate().before(targetDate)) {
				continue;
			}
			if (holidayType != dto.getHolidayType1()) {
				continue;
			}
			if (holidayCode.equals(dto.getHolidayType2())) {
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 代休申請リストから対象日の代休申請リストを取得する。<br>
	 * @param targetDate 対象日
	 * @return 代休申請リスト
	 */
	protected List<SubHolidayRequestDtoInterface> getSubHolidayRequestDtoList(Date targetDate) {
		List<SubHolidayRequestDtoInterface> list = new ArrayList<SubHolidayRequestDtoInterface>();
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestDtoList) {
			if (targetDate.equals(dto.getRequestDate())) {
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 残業申請リストから対象日の残業申請リストを取得する。<br>
	 * @param targetDate 対象日
	 * @return 残業申請リスト
	 */
	protected List<OvertimeRequestDtoInterface> getOvertimeRequestDtoList(Date targetDate) {
		List<OvertimeRequestDtoInterface> list = new ArrayList<OvertimeRequestDtoInterface>();
		for (OvertimeRequestDtoInterface dto : overtimeRequestDtoList) {
			if (targetDate.equals(dto.getRequestDate())) {
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 時差出勤申請リストから対象日の時差出勤申請を取得する。<br>
	 * @param targetDate 対象日
	 * @return 時差出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected DifferenceRequestDtoInterface getDifferenceRequestDtoListDto(Date targetDate) throws MospException {
		for (DifferenceRequestDtoInterface dto : differenceRequestDtoList) {
			if (targetDate.equals(dto.getRequestDate())) {
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (!PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
					// 取下でない場合
					return dto;
				}
			}
		}
		return null;
	}
	
	/**
	 * 残業申請チェック。<br>
	 * @param list リスト
	 * @param attendanceDto 勤怠DTO
	 * @param humanDto 人事マスタDTO
	 * @param amHoliday 午前休フラグ
	 * @param pmHoliday 午後休フラグ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeRequest(List<CutoffErrorListDtoInterface> list, AttendanceDtoInterface attendanceDto,
			HumanDtoInterface humanDto, boolean amHoliday, boolean pmHoliday) throws MospException {
		if (attendanceDto.getOvertimeAfter() > 0) {
			// 後残業がある場合
			OvertimeRequestDtoInterface overtimeRequestDto = overtimeRequestDao.findForKeyOnWorkflow(
					attendanceDto.getPersonalId(), attendanceDto.getWorkDate(), 2);
			if (overtimeRequestDto == null) {
				CutoffErrorListDtoInterface cutoffErrorListDto = getCutoffErrorListDto(attendanceDto.getWorkDate(),
						humanDto);
				cutoffErrorListDto.setType("残業");
				cutoffErrorListDto.setState("未申請");
				list.add(cutoffErrorListDto);
			}
		}
	}
	
	/**
	 * 丸めた(月勤務時間丸め)時間を取得する。<br>
	 * @param time 対象時間
	 * @param dto  勤怠設定情報
	 * @return 丸めた(月勤務時間丸め)時間
	 */
	protected int roundMonthlyWork(int time, TimeSettingDtoInterface dto) {
		return getRoundMinute(time, dto.getRoundMonthlyWork(), dto.getRoundMonthlyWorkUnit());
	}
	
	/**
	 * 週の終了曜日を取得する。<br>
	 * @param startDayOfWeek 週の開始曜日を示すフィールド値
	 * @return 週の終了曜日を示すフィールド値
	 */
	private int getEndDayOfWeek(int startDayOfWeek) {
		if (startDayOfWeek == Calendar.SUNDAY) {
			// 週の開始曜日が日曜日の場合
			return Calendar.SATURDAY;
		}
		return --startDayOfWeek;
	}
	
	/**
	 * 配列取得
	 * @param is 対象
	 * @return 配列
	 */
	protected int[] getIntArray(int... is) {
		return is;
	}
	
}
