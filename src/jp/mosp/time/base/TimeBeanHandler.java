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
package jp.mosp.time.base;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.time.bean.AfterApplyAttendancesExecuteBeanInterface;
import jp.mosp.time.bean.AllowanceRegistBeanInterface;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.HolidayRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.ScheduleDateRegistBeanInterface;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeSettingRegistBeanInterface;
import jp.mosp.time.bean.TotalAbsenceRegistBeanInterface;
import jp.mosp.time.bean.TotalAllowanceRegistBeanInterface;
import jp.mosp.time.bean.TotalLeaveRegistBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;
import jp.mosp.time.bean.impl.HolidayRequestImportBean;
import jp.mosp.time.bean.impl.WorkOnHolidayRequestImportBean;

/**
 * MosPプラットフォーム用BeanHandler。<br>
 */
public class TimeBeanHandler extends BaseBeanHandler implements TimeBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public TimeBeanHandler() {
		super();
	}
	
	@Override
	public AttendanceRegistBeanInterface attendanceRegist() throws MospException {
		return (AttendanceRegistBeanInterface)createBean(AttendanceRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceCorrectionRegistBeanInterface attendanceCorrectionRegist() throws MospException {
		return (AttendanceCorrectionRegistBeanInterface)createBean(AttendanceCorrectionRegistBeanInterface.class);
	}
	
	@Override
	public RestRegistBeanInterface restRegist() throws MospException {
		return (RestRegistBeanInterface)createBean(RestRegistBeanInterface.class);
	}
	
	@Override
	public GoOutRegistBeanInterface goOutRegist() throws MospException {
		return (GoOutRegistBeanInterface)createBean(GoOutRegistBeanInterface.class);
	}
	
	@Override
	public AllowanceRegistBeanInterface allowanceRegist() throws MospException {
		return (AllowanceRegistBeanInterface)createBean(AllowanceRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceTransactionRegistBeanInterface attendanceTransactionRegist() throws MospException {
		return (AttendanceTransactionRegistBeanInterface)createBean(AttendanceTransactionRegistBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestRegistBeanInterface overtimeRequestRegist() throws MospException {
		return (OvertimeRequestRegistBeanInterface)createBean(OvertimeRequestRegistBeanInterface.class);
	}
	
	@Override
	public HolidayRequestRegistBeanInterface holidayRequestRegist() throws MospException {
		return (HolidayRequestRegistBeanInterface)createBean(HolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestRegistBeanInterface workOnHolidayRequestRegist() throws MospException {
		return (WorkOnHolidayRequestRegistBeanInterface)createBean(WorkOnHolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestRegistBeanInterface subHolidayRequestRegist() throws MospException {
		return (SubHolidayRequestRegistBeanInterface)createBean(SubHolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public SubHolidayRegistBeanInterface subHolidayRegist() throws MospException {
		return (SubHolidayRegistBeanInterface)createBean(SubHolidayRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestRegistBeanInterface workTypeChangeRequestRegist() throws MospException {
		return (WorkTypeChangeRequestRegistBeanInterface)createBean(WorkTypeChangeRequestRegistBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestRegistBeanInterface differenceRequestRegist() throws MospException {
		return (DifferenceRequestRegistBeanInterface)createBean(DifferenceRequestRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionRegistBeanInterface totalTimeTransactionRegist() throws MospException {
		return (TotalTimeTransactionRegistBeanInterface)createBean(TotalTimeTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeTransactionRegistBeanInterface totalTimeEmployeeTransactionRegist() throws MospException {
		return (TotalTimeEmployeeTransactionRegistBeanInterface)createBean(
				TotalTimeEmployeeTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeRegistBeanInterface totalTimeRegist() throws MospException {
		return (TotalTimeRegistBeanInterface)createBean(TotalTimeRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionRegistBeanInterface totalTimeCorrectionRegist() throws MospException {
		return (TotalTimeCorrectionRegistBeanInterface)createBean(TotalTimeCorrectionRegistBeanInterface.class);
	}
	
	@Override
	public TotalLeaveRegistBeanInterface totalLeaveRegist() throws MospException {
		return (TotalLeaveRegistBeanInterface)createBean(TotalLeaveRegistBeanInterface.class);
	}
	
	@Override
	public TotalOtherVacationRegistBeanInterface totalOtherVacationRegist() throws MospException {
		return (TotalOtherVacationRegistBeanInterface)createBean(TotalOtherVacationRegistBeanInterface.class);
	}
	
	@Override
	public TotalAbsenceRegistBeanInterface totalAbsenceRegist() throws MospException {
		return (TotalAbsenceRegistBeanInterface)createBean(TotalAbsenceRegistBeanInterface.class);
	}
	
	@Override
	public TotalAllowanceRegistBeanInterface totalAllowanceRegist() throws MospException {
		return (TotalAllowanceRegistBeanInterface)createBean(TotalAllowanceRegistBeanInterface.class);
	}
	
	@Override
	public HolidayRegistBeanInterface holidayRegist() throws MospException {
		return (HolidayRegistBeanInterface)createBean(HolidayRegistBeanInterface.class);
	}
	
	@Override
	public HolidayDataRegistBeanInterface holidayDataRegist() throws MospException {
		return (HolidayDataRegistBeanInterface)createBean(HolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataRegistBeanInterface paidHolidayDataRegist() throws MospException {
		return (PaidHolidayDataRegistBeanInterface)createBean(PaidHolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayTransactionRegistBeanInterface paidHolidayTransactionRegist() throws MospException {
		return (PaidHolidayTransactionRegistBeanInterface)createBean(PaidHolidayTransactionRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataRegistBeanInterface stockHolidayDataRegist() throws MospException {
		return (StockHolidayDataRegistBeanInterface)createBean(StockHolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayTransactionRegistBeanInterface stockHolidayTransactionRegist() throws MospException {
		return (StockHolidayTransactionRegistBeanInterface)createBean(StockHolidayTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TimeSettingRegistBeanInterface timeSettingRegist() throws MospException {
		return (TimeSettingRegistBeanInterface)createBean(TimeSettingRegistBeanInterface.class);
	}
	
	@Override
	public LimitStandardRegistBeanInterface limitStandardRegist() throws MospException {
		return (LimitStandardRegistBeanInterface)createBean(LimitStandardRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeRegistBeanInterface workTypeRegist() throws MospException {
		return (WorkTypeRegistBeanInterface)createBean(WorkTypeRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeItemRegistBeanInterface workTypeItemRegist() throws MospException {
		return (WorkTypeItemRegistBeanInterface)createBean(WorkTypeItemRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternRegistBeanInterface workTypePatternRegist() throws MospException {
		return (WorkTypePatternRegistBeanInterface)createBean(WorkTypePatternRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternItemRegistBeanInterface workTypePatternItemRegist() throws MospException {
		return (WorkTypePatternItemRegistBeanInterface)createBean(WorkTypePatternItemRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayRegistBeanInterface paidHolidayRegist() throws MospException {
		return (PaidHolidayRegistBeanInterface)createBean(PaidHolidayRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayProportionallyRegistBeanInterface paidHolidayProportionallyRegist() throws MospException {
		return (PaidHolidayProportionallyRegistBeanInterface)createBean(
				PaidHolidayProportionallyRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearRegistBeanInterface paidHolidayFirstYearRegist() throws MospException {
		return (PaidHolidayFirstYearRegistBeanInterface)createBean(PaidHolidayFirstYearRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateRegistBeanInterface paidHolidayPointDateRegist() throws MospException {
		return (PaidHolidayPointDateRegistBeanInterface)createBean(PaidHolidayPointDateRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateRegistBeanInterface paidHolidayEntranceDateRegist() throws MospException {
		return (PaidHolidayEntranceDateRegistBeanInterface)createBean(PaidHolidayEntranceDateRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayRegistBeanInterface stockHolidayRegist() throws MospException {
		return (StockHolidayRegistBeanInterface)createBean(StockHolidayRegistBeanInterface.class);
	}
	
	@Override
	public ScheduleRegistBeanInterface scheduleRegist() throws MospException {
		return (ScheduleRegistBeanInterface)createBean(ScheduleRegistBeanInterface.class);
	}
	
	@Override
	public ScheduleDateRegistBeanInterface scheduleDateRegist() throws MospException {
		return (ScheduleDateRegistBeanInterface)createBean(ScheduleDateRegistBeanInterface.class);
	}
	
	@Override
	public CutoffRegistBeanInterface cutoffRegist() throws MospException {
		return (CutoffRegistBeanInterface)createBean(CutoffRegistBeanInterface.class);
	}
	
	@Override
	public ApplicationRegistBeanInterface applicationRegist() throws MospException {
		return (ApplicationRegistBeanInterface)createBean(ApplicationRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeCalcBeanInterface totalTimeCalc() throws MospException {
		return (TotalTimeCalcBeanInterface)createBean(TotalTimeCalcBeanInterface.class);
	}
	
	@Override
	public SubstituteRegistBeanInterface substituteRegist() throws MospException {
		return (SubstituteRegistBeanInterface)createBean(SubstituteRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceListRegistBeanInterface attendanceListRegist() throws MospException {
		return (AttendanceListRegistBeanInterface)createBean(AttendanceListRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceListRegistBeanInterface attendanceListRegist(Date targetDate) throws MospException {
		return (AttendanceListRegistBeanInterface)createBean(AttendanceListRegistBeanInterface.class, targetDate);
	}
	
	@Override
	public AttendanceCalcBeanInterface attendanceCalc() throws MospException {
		return (AttendanceCalcBeanInterface)createBean(AttendanceCalcBeanInterface.class);
	}
	
	@Override
	public AttendanceCalcBeanInterface attendanceCalc(Date targetDate) throws MospException {
		return (AttendanceCalcBeanInterface)createBean(AttendanceCalcBeanInterface.class, targetDate);
	}
	
	@Override
	public TimeApprovalBeanInterface timeApproval() throws MospException {
		return (TimeApprovalBeanInterface)createBean(TimeApprovalBeanInterface.class);
	}
	
	@Override
	public PaidHolidayGrantRegistBeanInterface paidHolidayGrantRegist() throws MospException {
		return (PaidHolidayGrantRegistBeanInterface)createBean(PaidHolidayGrantRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataGrantBeanInterface paidHolidayDataGrant() throws MospException {
		return (PaidHolidayDataGrantBeanInterface)createBean(PaidHolidayDataGrantBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataGrantBeanInterface stockHolidayDataGrant() throws MospException {
		return (StockHolidayDataGrantBeanInterface)createBean(StockHolidayDataGrantBeanInterface.class);
	}
	
	@Override
	public ImportBeanInterface holidayRequestImport() throws MospException {
		return (HolidayRequestImportBean)createBean(HolidayRequestImportBean.class);
	}
	
	@Override
	public ImportBeanInterface workOnHolidayRequestImport() throws MospException {
		return (WorkOnHolidayRequestImportBean)createBean(WorkOnHolidayRequestImportBean.class);
	}
	
	@Override
	public AfterApplyAttendancesExecuteBeanInterface afterApplyAttendancesExecute() throws MospException {
		return (AfterApplyAttendancesExecuteBeanInterface)createBean(AfterApplyAttendancesExecuteBeanInterface.class);
	}
	
	@Override
	public TimeRecordBeanInterface timeRecord() throws MospException {
		return (TimeRecordBeanInterface)createBean(TimeRecordBeanInterface.class);
	}
	
}
