<%--
MosP - Mind Open Source Project    http://www.mosp.jp/
Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/

This program is free software: you can redistribute it and/or
modify it under the terms of the GNU Affero General Public License
as published by the Free Software Foundation, either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page
language     = "java"
pageEncoding = "UTF-8"
buffer       = "256kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.SubordinateAbsenceComparator"
import = "jp.mosp.time.comparator.settings.SubordinateAllHolidayComparator"
import = "jp.mosp.time.comparator.settings.SubordinateApprovalComparator"
import = "jp.mosp.time.comparator.settings.SubordinateCalcComparator"
import = "jp.mosp.time.comparator.settings.SubordinateCorrectionComparator"
import = "jp.mosp.time.comparator.settings.SubordinateLateLeaveEarlyTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateLateNightTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateOverTimeInComparator"
import = "jp.mosp.time.comparator.settings.SubordinateOverTimeOutComparator"
import = "jp.mosp.time.comparator.settings.SubordinatePaidHolidayComparator"
import = "jp.mosp.time.comparator.settings.SubordinatePrivateTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateRestTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkDateComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkOnHolidayTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkTimeComaparator"
import = "jp.mosp.time.input.action.AttendanceListAction"
import = "jp.mosp.time.input.action.ScheduleReferenceAction"
import = "jp.mosp.time.management.action.SubordinateListAction"
import = "jp.mosp.time.management.vo.SubordinateListVo"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubordinateListVo vo = (SubordinateListVo)params.getVo();
boolean isSubHolidayRequestValid = TimeUtility.isSubHolidayRequestValid(params);
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("Employee","Search") %></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%= params.getName("Display","Period") %></td>
				<td class="InputTd">
					<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
						<%= HtmlUtility.getSelectOption(vo.getAryPltRequestYear(), vo.getPltSearchRequestYear()) %>
					</select>
					<%= params.getName("Year") %>
					<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
						<%= HtmlUtility.getSelectOption(vo.getAryPltRequestMonth(), vo.getPltSearchRequestMonth()) %>
					</select>
					<%= params.getName("Month") %>&nbsp;
					<button type="button" class="Name2Button" id="btnRequestDate" onclick="submitForm(event, 'divSearchClassRoute', null, '<%= SubordinateListAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</td>
				<td class="TitleTd"><%= params.getName("Employee","Code") %></td>
				<td class="InputTd">
					<Input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>" />
				</td>
				<td class="TitleTd"><%= params.getName("Employee","FirstName") %></td>
				<td class="InputTd">
					<Input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
						<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltSearchWorkPlace()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
						<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltSearchEmployment()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Ram","Approval") %></td>
				<td class="InputTd">
					<select class="Name7PullDown" id="pltSearchApproval" name="pltSearchApproval">
						<%= HtmlUtility.getSelectOption(vo.getAryPltApproval(), vo.getPltSearchApproval()) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Section") %></td>
				<td class="InputTd">
					<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
						<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSearchSection()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Position") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
						<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltSearchPosition()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Cutoff","State") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchCalc" name="pltSearchCalc">
						<%= HtmlUtility.getSelectOption(vo.getAryPltCalc(), vo.getPltSearchCalc()) %>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkSearch, '<%= SubordinateListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="20">
					<span class="TitleTh"><%= params.getName("Employee","List") %></span>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
					<span class="TableButtonSpan">
<% if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW)) { %>
						<button type="button" class="Name5Button" id="btnExport" onclick="submitFile(event, checkExtraForFile, null, '<%= SubordinateListAction.CMD_OUTPUT %>');"><%= params.getName("AttendanceBook", "Output") %></button>&nbsp;
						<button type="button" class="Name5Button" id="btnScheduleExport" onclick="submitFile(event, checkExtraForFile, null, '<%= SubordinateListAction.CMD_SCHEDULE %>');"><%= params.getName("ScheduleBook", "Output") %></button>&nbsp;
<% } %>
						<button type="button" class="Name5Button" id="btnCalc" onclick="submitForm(event, null, checkExtra, '<%= SubordinateListAction.CMD_CALC %>');"><%= params.getName("WorkManage","Total") %></button>
					</span>
<%
}
%>
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Code") %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkDateComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("GoingWork") %><%= PlatformUtility.getSortMark(SubordinateWorkDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkTimeComaparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Work") %><%= PlatformUtility.getSortMark(SubordinateWorkTimeComaparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRestTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateRestTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("RestTime") %><%= PlatformUtility.getSortMark(SubordinateRestTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPrivateTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePrivateTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("GoingOut") %><%= PlatformUtility.getSortMark(SubordinatePrivateTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateLeaveEarlyTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateLeaveEarlyTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("LateLeaveEarly") %><%= PlatformUtility.getSortMark(SubordinateLateLeaveEarlyTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeIn" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeInComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Inside","OvertimeAbbr") %><%= PlatformUtility.getSortMark(SubordinateOverTimeInComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeOut" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeOutComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("LeftOut") %><%= PlatformUtility.getSortMark(SubordinateOverTimeOutComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkOnHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkOnHolidayTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("WorkingHoliday") %><%= PlatformUtility.getSortMark(SubordinateWorkOnHolidayTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateNight" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateNightTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Midnight") %><%= PlatformUtility.getSortMark(SubordinateLateNightTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPaidHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePaidHolidayComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("PaidHoliday") %><%= PlatformUtility.getSortMark(SubordinatePaidHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAllHoliday"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAllHolidayComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Vacation") %><%= PlatformUtility.getSortMark(SubordinateAllHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAbsence"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAbsenceComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Absence") %><%= PlatformUtility.getSortMark(SubordinateAbsenceComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApproval" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateApprovalComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("UnApproval") %><%= PlatformUtility.getSortMark(SubordinateApprovalComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCalc" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCalcComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(SubordinateCalcComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCorrection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCorrectionComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("AttendanceCorrection") %><%= PlatformUtility.getSortMark(SubordinateCorrectionComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
					<input type="checkbox" class="CheckBox" id="ckbSelect" onclick="doAllBoxChecked(this);">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW)) { %>
					<button type="button" class="Name1Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceListAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("Fact") %></button>
					<button type="button" class="Name1Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ScheduleReferenceAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("Plan") %></button>
<% } else if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW_APPROVED)) { %>
					<button type="button" class="Name2Button" id="btnAttendance" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceListAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("WorkManage") %></button>
<% } %>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRestTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPrivateTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateLeaveEarlyTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeIn(i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryOvertimeOutStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeOut(i)) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkOnHolidayTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateNightTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAllHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAbsence(i)) %></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaApploval(i) %>"><%= vo.getAryLblApploval(i) %></span></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaCalc(i) %>"><%= vo.getAryLblCalc(i) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCorrection(i)) %></td>
				<td class="ListSelectTd"><input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= vo.getAryPersonalId(i) %>" <%= HtmlUtility.getChecked(vo.getAryPersonalId(i), vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>

<%
if (false) {
%>
<div class="List">
	<table class="LeftListTable" id="tblTotal">
		<tr>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2">
				<div><%= params.getName("SumTotal") %></div>
				<div><%= params.getName("Time") %></div>
			</th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Work") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("RestTime") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("GoingOut") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("LateLeaveEarly") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Inside","Remainder") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("LeftOut") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("WorkingHoliday") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Midnight") %></th>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><%= params.getName("Frequency") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("GoingWork") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Tardiness") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("LeaveEarly") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("OvertimeWork") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("WorkingHoliday") %></th>
		</tr>
		<tr>
			<td class="SelectTd" id="lblTotalWork"><%= vo.getLblTotalWork() %></td>
			<td class="SelectTd" id="lblTotalRest"><%= vo.getLblTotalRest() %></td>
			<td class="SelectTd" id="lblTotalPrivate"><%= vo.getLblTotalPrivate() %></td>
			<td class="SelectTd" id="lblTotalLateLeaveEarly"><%= vo.getLblTotalLateLeaveEarly() %></td>
			<td class="SelectTd" id="lblTotalOverTimeIn"><%= vo.getLblTotalOverTimeIn() %></td>
			<td class="SelectTd" id="lblTotalOverTimeOut"><%= vo.getLblTotalOverTimeOut() %></td>
			<td class="SelectTd" id="lblTotalWorkOnHoliday"><%= vo.getLblTotalWorkOnHoliday() %></td>
			<td class="SelectTd" id="lblTotalLateNight"><%= vo.getLblTotalLateNight() %></td>
			<td class="SelectTd" id="lblTimesWork"><%= vo.getLblTimesWork() %></td>
			<td class="SelectTd" id="lblTimesLate"><%= vo.getLblTimesLate() %></td>
			<td class="SelectTd" id="lblTimesLeaveEarly"><%= vo.getLblTimesLeaveEarly() %></td>
			<td class="SelectTd" id="lblTimesOverTimeWork"><%= vo.getLblTimesOverTimeWork() %></td>
			<td class="SelectTd" id="lblTimesWorkOnHoliday"><%= vo.getLblTimesWorkOnHoliday() %></td>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><%= params.getName("Holiday") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Legal") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Prescribed") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("ClosedVibration") %></th>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><%= params.getName("Vacation") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("PaidHoliday") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("SalaryTime") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("SpecialLeave") %></th>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Others") %></th>
<% if (isSubHolidayRequestValid) { %>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("CompensatoryHoliday") %></th>
<% } %>
			<th class="ListSelectTh" id="thTotalTime"><%= params.getName("Absence") %></th>
			<td class="Blank" colspan="4" rowspan="4"></td>
		</tr>
		<tr>
			<td class="SelectTd" id="lblTimesLegalHoliday"><%= vo.getLblTimesLegalHoliday() %></td>
			<td class="SelectTd" id="lblTimesSpecificHoliday"><%= vo.getLblTimesSpecificHoliday() %></td>
			<td class="SelectTd" id="lblTimesSubstitute"><%= vo.getLblTimesSubstitute() %></td>
			<td class="SelectTd" id="lblTimesPaidHoliday"><%= vo.getLblTimesPaidHoliday() %></td>
			<td class="SelectTd" id="lblTimesPaidHolidayTime"><%= vo.getLblTimesPaidHoloidayTime() %></td>
			<td class="SelectTd" id="lblTimesSpecialHoloiday"><%= vo.getLblTimesSpecialHoloiday() %></td>
			<td class="SelectTd" id="lblTimesOtherHoliday"><%= vo.getLblTimesOtherHoloiday() %></td>
<% if (isSubHolidayRequestValid) { %>
			<td class="SelectTd" id="lblTimesSubHoliday"><%= vo.getLblTimesSubHoliday() %></td>
<% } %>
			<td class="SelectTd" id="lblTimesAbsence"><%= vo.getLblTimesAbsence() %></td>
		</tr>
<!--
		<tr>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><= params.getName("Allowance") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No1") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No2") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No3") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No4") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No5") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No6") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No7") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No8") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No9") %></th>
			<th class="ListSelectTh" id="thTotalTime"><= params.getName("Allowance","No10") %></th>
		</tr>
		<tr>
			<td class="SelectTd" id="lblTimesAllowance1"><= vo.getLblTimesAllowance1() %></td>
			<td class="SelectTd" id="lblTimesAllowance2"><= vo.getLblTimesAllowance2() %></td>
			<td class="SelectTd" id="lblTimesAllowance3"><= vo.getLblTimesAllowance3() %></td>
			<td class="SelectTd" id="lblTimesAllowance4"><= vo.getLblTimesAllowance4() %></td>
			<td class="SelectTd" id="lblTimesAllowance5"><= vo.getLblTimesAllowance5() %></td>
			<td class="SelectTd" id="lblTimesAllowance6"><= vo.getLblTimesAllowance6() %></td>
			<td class="SelectTd" id="lblTimesAllowance7"><= vo.getLblTimesAllowance7() %></td>
			<td class="SelectTd" id="lblTimesAllowance8"><= vo.getLblTimesAllowance8() %></td>
			<td class="SelectTd" id="lblTimesAllowance9"><= vo.getLblTimesAllowance9() %></td>
			<td class="SelectTd" id="lblTimesAllowance10"><= vo.getLblTimesAllowance10() %></td>
		</tr>
-->
	</table>
</div>
<%
}
%>
