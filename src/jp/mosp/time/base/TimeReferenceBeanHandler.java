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
import jp.mosp.time.bean.AllowanceReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceSearchBeanInterface;
import jp.mosp.time.bean.ApplicationSearchBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTotalReferenceBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.CutoffSearchBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestSearchBeanInterface;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayExportBeanInterface;
import jp.mosp.time.bean.HolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.HolidaySearchBeanInterface;
import jp.mosp.time.bean.ImportTableReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidaySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleSearchBeanInterface;
import jp.mosp.time.bean.ScheduleTotalReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayExportBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.SubordinateTotalReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingSearchBeanInterface;
import jp.mosp.time.bean.TotalAbsenceReferenceBeanInterface;
import jp.mosp.time.bean.TotalAllowanceReferenceBeanInterface;
import jp.mosp.time.bean.TotalLeaveReferenceBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionSearchBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeSearchBeanInterface;
import jp.mosp.time.report.bean.AttendanceBookBeanInterface;
import jp.mosp.time.report.bean.ScheduleBookBeanInterface;

/**
 * MosP勤怠管理参照用BeanHandlerクラス。
 */
public class TimeReferenceBeanHandler extends BaseBeanHandler implements TimeReferenceBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public TimeReferenceBeanHandler() {
		super();
	}
	
	@Override
	public AttendanceReferenceBeanInterface attendance() throws MospException {
		return (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceCorrectionReferenceBeanInterface attendanceCorrection() throws MospException {
		return (AttendanceCorrectionReferenceBeanInterface)createBean(AttendanceCorrectionReferenceBeanInterface.class);
	}
	
	@Override
	public RestReferenceBeanInterface rest() throws MospException {
		return (RestReferenceBeanInterface)createBean(RestReferenceBeanInterface.class);
	}
	
	@Override
	public GoOutReferenceBeanInterface goOut() throws MospException {
		return (GoOutReferenceBeanInterface)createBean(GoOutReferenceBeanInterface.class);
	}
	
	@Override
	public AllowanceReferenceBeanInterface allowance() throws MospException {
		return (AllowanceReferenceBeanInterface)createBean(AllowanceReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceTotalReferenceBeanInterface attendanceTotal() throws MospException {
		return (AttendanceTotalReferenceBeanInterface)createBean(AttendanceTotalReferenceBeanInterface.class);
	}
	
	@Override
	public ScheduleTotalReferenceBeanInterface scheduleTotal() throws MospException {
		return (ScheduleTotalReferenceBeanInterface)createBean(ScheduleTotalReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestReferenceBeanInterface overtimeRequest() throws MospException {
		return (OvertimeRequestReferenceBeanInterface)createBean(OvertimeRequestReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestSearchBeanInterface overtimeRequestSearch() throws MospException {
		return (OvertimeRequestSearchBeanInterface)createBean(OvertimeRequestSearchBeanInterface.class);
	}
	
	@Override
	public HolidayRequestReferenceBeanInterface holidayRequest() throws MospException {
		return (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayRequestSearchBeanInterface holidayRequestSearch() throws MospException {
		return (HolidayRequestSearchBeanInterface)createBean(HolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestReferenceBeanInterface workOnHolidayRequest() throws MospException {
		return (WorkOnHolidayRequestReferenceBeanInterface)createBean(WorkOnHolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestSearchBeanInterface workOnHolidayRequestSearch() throws MospException {
		return (WorkOnHolidayRequestSearchBeanInterface)createBean(WorkOnHolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public SubHolidayReferenceBeanInterface subHoliday() throws MospException {
		return (SubHolidayReferenceBeanInterface)createBean(SubHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestReferenceBeanInterface subHolidayRequest() throws MospException {
		return (SubHolidayRequestReferenceBeanInterface)createBean(SubHolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestSearchBeanInterface subHolidayRequestSearch() throws MospException {
		return (SubHolidayRequestSearchBeanInterface)createBean(SubHolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestReferenceBeanInterface workTypeChangeRequest() throws MospException {
		return (WorkTypeChangeRequestReferenceBeanInterface)createBean(WorkTypeChangeRequestReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestSearchBeanInterface workTypeChangeRequestSearch() throws MospException {
		return (WorkTypeChangeRequestSearchBeanInterface)createBean(WorkTypeChangeRequestSearchBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestReferenceBeanInterface differenceRequest() throws MospException {
		return (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestReferenceBeanInterface differenceRequest(Date targetDate) throws MospException {
		return (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class,
				targetDate);
	}
	
	@Override
	public DifferenceRequestSearchBeanInterface differenceRequestSearch() throws MospException {
		return (DifferenceRequestSearchBeanInterface)createBean(DifferenceRequestSearchBeanInterface.class);
	}
	
	@Override
	public SubordinateSearchBeanInterface subordinateSearch() throws MospException {
		return (SubordinateSearchBeanInterface)createBean(SubordinateSearchBeanInterface.class);
	}
	
	@Override
	public SubordinateTotalReferenceBeanInterface subordinateTotal() throws MospException {
		return (SubordinateTotalReferenceBeanInterface)createBean(SubordinateTotalReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionReferenceBeanInterface totalTimeTransaction() throws MospException {
		return (TotalTimeTransactionReferenceBeanInterface)createBean(TotalTimeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeTransactionReferenceBeanInterface totalTimeEmployeeTransaction() throws MospException {
		return (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionSearchBeanInterface totalTimeTransactionSearch() throws MospException {
		return (TotalTimeTransactionSearchBeanInterface)createBean(TotalTimeTransactionSearchBeanInterface.class);
	}
	
	@Override
	public TotalTimeSearchBeanInterface totalTimeSearch() throws MospException {
		return (TotalTimeSearchBeanInterface)createBean(TotalTimeSearchBeanInterface.class);
	}
	
	@Override
	public TotalTimeReferenceBeanInterface totalTime() throws MospException {
		return (TotalTimeReferenceBeanInterface)createBean(TotalTimeReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionReferenceBeanInterface totalTimeCorrection() throws MospException {
		return (TotalTimeCorrectionReferenceBeanInterface)createBean(TotalTimeCorrectionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalLeaveReferenceBeanInterface totalLeave() throws MospException {
		return (TotalLeaveReferenceBeanInterface)createBean(TotalLeaveReferenceBeanInterface.class);
	}
	
	@Override
	public TotalOtherVacationReferenceBeanInterface totalOtherVacation() throws MospException {
		return (TotalOtherVacationReferenceBeanInterface)createBean(TotalOtherVacationReferenceBeanInterface.class);
	}
	
	@Override
	public TotalAbsenceReferenceBeanInterface totalAbsence() throws MospException {
		return (TotalAbsenceReferenceBeanInterface)createBean(TotalAbsenceReferenceBeanInterface.class);
	}
	
	@Override
	public TotalAllowanceReferenceBeanInterface totalAllowance() throws MospException {
		return (TotalAllowanceReferenceBeanInterface)createBean(TotalAllowanceReferenceBeanInterface.class);
	}
	
	@Override
	public ImportTableReferenceBeanInterface importTable() throws MospException {
		return (ImportTableReferenceBeanInterface)createBean(ImportTableReferenceBeanInterface.class);
	}
	
	@Override
	public ExportTableReferenceBeanInterface exportTable() throws MospException {
		return (ExportTableReferenceBeanInterface)createBean(ExportTableReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayReferenceBeanInterface holiday() throws MospException {
		return (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
	}
	
	@Override
	public HolidaySearchBeanInterface holidaySearch() throws MospException {
		return (HolidaySearchBeanInterface)createBean(HolidaySearchBeanInterface.class);
	}
	
	@Override
	public HolidayManagementSearchBeanInterface holidayManagementSearch() throws MospException {
		return (HolidayManagementSearchBeanInterface)createBean(HolidayManagementSearchBeanInterface.class);
	}
	
	@Override
	public HolidayDataReferenceBeanInterface holidayData() throws MospException {
		return (HolidayDataReferenceBeanInterface)createBean(HolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayHistorySearchBeanInterface holidayHistorySearch() throws MospException {
		return (HolidayHistorySearchBeanInterface)createBean(HolidayHistorySearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayManagementSearchBeanInterface paidHolidayManagementSearch() throws MospException {
		return (PaidHolidayManagementSearchBeanInterface)createBean(PaidHolidayManagementSearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayHistorySearchBeanInterface paidHolidayHistorySearch() throws MospException {
		return (PaidHolidayHistorySearchBeanInterface)createBean(PaidHolidayHistorySearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayTransactionReferenceBeanInterface paidHolidayTransaction() throws MospException {
		return (PaidHolidayTransactionReferenceBeanInterface)createBean(PaidHolidayTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataReferenceBeanInterface paidHolidayData() throws MospException {
		return (PaidHolidayDataReferenceBeanInterface)createBean(PaidHolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataSearchBeanInterface paidHolidayDataSearch() throws MospException {
		return (PaidHolidayDataSearchBeanInterface)createBean(PaidHolidayDataSearchBeanInterface.class);
	}
	
	@Override
	public StockHolidayTransactionReferenceBeanInterface stockHolidayTransaction() throws MospException {
		return (StockHolidayTransactionReferenceBeanInterface)createBean(StockHolidayTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataReferenceBeanInterface stockHolidayData() throws MospException {
		return (StockHolidayDataReferenceBeanInterface)createBean(StockHolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public TimeSettingReferenceBeanInterface timeSetting() throws MospException {
		return (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
	}
	
	@Override
	public LimitStandardReferenceBeanInterface limitStandard() throws MospException {
		return (LimitStandardReferenceBeanInterface)createBean(LimitStandardReferenceBeanInterface.class);
	}
	
	@Override
	public TimeSettingSearchBeanInterface timeSettingSearch() throws MospException {
		return (TimeSettingSearchBeanInterface)createBean(TimeSettingSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeReferenceBeanInterface workType() throws MospException {
		return (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypeSearchBeanInterface workTypeSearch() throws MospException {
		return (WorkTypeSearchBeanInterface)createBean(WorkTypeSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeItemReferenceBeanInterface workTypeItem() throws MospException {
		return (WorkTypeItemReferenceBeanInterface)createBean(WorkTypeItemReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternReferenceBeanInterface workTypePattern() throws MospException {
		return (WorkTypePatternReferenceBeanInterface)createBean(WorkTypePatternReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternSearchBeanInterface workTypePatternSearch() throws MospException {
		return (WorkTypePatternSearchBeanInterface)createBean(WorkTypePatternSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternItemReferenceBeanInterface workTypePatternItem() throws MospException {
		return (WorkTypePatternItemReferenceBeanInterface)createBean(WorkTypePatternItemReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayReferenceBeanInterface paidHoliday() throws MospException {
		return (PaidHolidayReferenceBeanInterface)createBean(PaidHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearReferenceBeanInterface paidHolidayFirstYear() throws MospException {
		return (PaidHolidayFirstYearReferenceBeanInterface)createBean(PaidHolidayFirstYearReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateReferenceBeanInterface paidHolidayPointDate() throws MospException {
		return (PaidHolidayPointDateReferenceBeanInterface)createBean(PaidHolidayPointDateReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateReferenceBeanInterface paidHolidayEntranceDate() throws MospException {
		return (PaidHolidayEntranceDateReferenceBeanInterface)createBean(PaidHolidayEntranceDateReferenceBeanInterface.class);
	}
	
	@Override
	public StockHolidayReferenceBeanInterface stockHoliday() throws MospException {
		return (StockHolidayReferenceBeanInterface)createBean(StockHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidaySearchBeanInterface paidHolidaySearch() throws MospException {
		return (PaidHolidaySearchBeanInterface)createBean(PaidHolidaySearchBeanInterface.class);
	}
	
	@Override
	public ScheduleReferenceBeanInterface schedule() throws MospException {
		return (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
	}
	
	@Override
	public ScheduleDateReferenceBeanInterface scheduleDate() throws MospException {
		return (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
	}
	
	@Override
	public ScheduleSearchBeanInterface scheduleSearch() throws MospException {
		return (ScheduleSearchBeanInterface)createBean(ScheduleSearchBeanInterface.class);
	}
	
	@Override
	public CutoffReferenceBeanInterface cutoff() throws MospException {
		return (CutoffReferenceBeanInterface)createBean(CutoffReferenceBeanInterface.class);
	}
	
	@Override
	public CutoffSearchBeanInterface cutoffSearch() throws MospException {
		return (CutoffSearchBeanInterface)createBean(CutoffSearchBeanInterface.class);
	}
	
	@Override
	public ApplicationReferenceBeanInterface application() throws MospException {
		return (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
	}
	
	@Override
	public ApplicationSearchBeanInterface applicationSearch() throws MospException {
		return (ApplicationSearchBeanInterface)createBean(ApplicationSearchBeanInterface.class);
	}
	
	@Override
	public ApplicationReferenceSearchBeanInterface applicationReferenceSearch() throws MospException {
		return (ApplicationReferenceSearchBeanInterface)createBean(ApplicationReferenceSearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayInfoReferenceBeanInterface paidHolidayInfo() throws MospException {
		return (PaidHolidayInfoReferenceBeanInterface)createBean(PaidHolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public StockHolidayInfoReferenceBeanInterface stockHolidayInfo() throws MospException {
		return (StockHolidayInfoReferenceBeanInterface)createBean(StockHolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayInfoReferenceBeanInterface holidayInfo() throws MospException {
		return (HolidayInfoReferenceBeanInterface)createBean(HolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeInfoReferenceBeanInterface overtimeInfo() throws MospException {
		return (OvertimeInfoReferenceBeanInterface)createBean(OvertimeInfoReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalInfoReferenceBeanInterface approvalInfo() throws MospException {
		return (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
	}
	
	@Override
	public SubstituteReferenceBeanInterface substitute() throws MospException {
		return (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayInfoReferenceBeanInterface workOnHolidayInfo() throws MospException {
		return (WorkOnHolidayInfoReferenceBeanInterface)createBean(WorkOnHolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceListReferenceBeanInterface attendanceList() throws MospException {
		return (AttendanceListReferenceBeanInterface)createBean(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	public SubHolidayExportBeanInterface subHolidayExport() throws MospException {
		return (SubHolidayExportBeanInterface)createBean(SubHolidayExportBeanInterface.class);
	}
	
	@Override
	public HolidayExportBeanInterface holidayRequestDataExport() throws MospException {
		return (HolidayExportBeanInterface)createBean(HolidayExportBeanInterface.class);
	}
	
	@Override
	public ScheduleBookBeanInterface scheduleBook() throws MospException {
		return (ScheduleBookBeanInterface)createBean(ScheduleBookBeanInterface.class);
	}
	
	@Override
	public AttendanceBookBeanInterface attendanceBook() throws MospException {
		return (AttendanceBookBeanInterface)createBean(AttendanceBookBeanInterface.class);
	}
	
	@Override
	public CutoffUtilBeanInterface cutoffUtil() throws MospException {
		return (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
	}
	
	@Override
	public RequestUtilBeanInterface requestUtil() throws MospException {
		return (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
	}
	
	@Override
	public ScheduleUtilBeanInterface scheduleUtil() throws MospException {
		return (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public TimeRecordReferenceBeanInterface timeRecord() throws MospException {
		return (TimeRecordReferenceBeanInterface)createBean(TimeRecordReferenceBeanInterface.class);
	}
	
}
