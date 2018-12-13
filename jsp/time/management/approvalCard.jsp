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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.management.vo.ApprovalCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalCardVo vo = (ApprovalCardVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
	<table class="DateTable">
		<tr>
			<td>&nbsp;
				<%= HtmlUtility.escapeHTML(vo.getLblYear()) %><%= params.getName("Year") %>
				<%= HtmlUtility.escapeHTML(vo.getLblMonth()) %><%= params.getName("Month") %>
				<%= HtmlUtility.escapeHTML(vo.getLblDay()) %><%= params.getName("Day") %>
			</td>
		</tr>
	</table>
</div>
<div class="FixList">
<%
if (vo.getLblWorkType() != null) {
%>
	<div id="divAttendance">
		<table class="InputTable" id="tblCorrection">
			<tr>
				<th class="ListTableTh" colspan="2">
					<span class="TitleTh"><%= params.getName("CorrectionInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("CorrectionSummary") %></td>
				<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblCorrectionHistory()) %></td>
			</tr>
		</table>
		<table class="InputTable" id="tblAttendance">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("AttendanceInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Work","Form") %></td>
				<td class="InputTd" id="lblWorkType">
					<%= HtmlUtility.escapeHTML(vo.getLblWorkType()) %>
				</td>
				<td class="TitleTd"><%= params.getName("StartWork","Moment") %></td>
				<td class="InputTd" id="lblStartTime">
					<%= HtmlUtility.escapeHTML(vo.getLblStartTime()) %>
				</td>
				<td class="TitleTd"><%= params.getName("EndWork","Moment") %></td>
				<td class="InputTd" id="lblEndTime">
					<%= HtmlUtility.escapeHTML(vo.getLblEndTime()) %>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Work","Time") %></td>
				<td class="InputTd" id="lblWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTime()) %></td>
				<td class="TitleTd"><%= params.getName("DirectStart","Slash","DirectEnd") %></td>
				<td class="InputTd" id="lblApprovalState"><%= HtmlUtility.escapeHTML(vo.getLblDirectWorkManage()) %></td>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
				<td class="TitleTd" id="tdUnpaidShortTime" ><%= params.getName("UnpaidShortTime") %></td>
				<td class="InputTd" id="tdLblUnpaidShortTime" ><%= HtmlUtility.escapeHTML(vo.getLblUnpaidShortTime()) %></td>
<%
} else {
%>
				<td class="Blank" colspan="2"></td>
<%
}
%>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("WorkManage","Comment") %></td>
				<td class="InputTd" id="tdAttendanceComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblTimeComment()) %></td>
			</tr>
		</table>
<%
if (params.getGeneralParam(ApprovalCardAction.PRM_APPROVAL_EXTRA_JSP) != null) {
%>
	<jsp:include page="<%= (String)params.getGeneralParam(ApprovalCardAction.PRM_APPROVAL_EXTRA_JSP) %>" flush="false" />
<%
}
%>
		<table class="InputTable" id="tblRest">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("RestTimeInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdRestTitle"><%= params.getName("RestTime","Time") %></td>
				<td class="InputTd" id="lblRestTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblRestTime()) %></td>
				<td class="TitleTd" id="tdRestTitle1"><%= params.getName("Rest1") %></td>
				<td class="InputTd" id="lblRestTime1"><%= HtmlUtility.escapeHTML(vo.getLblRestTime1()) %></td>
				<td class="TitleTd" id="tdRestTitle2"><%= params.getName("Rest2") %></td>
				<td class="InputTd" id="lblRestTime2"><%= HtmlUtility.escapeHTML(vo.getLblRestTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMidnightRestTitle"><%= params.getName("Midnight","RestTime","Time") %></td>
				<td class="InputTd" id="lblLateRestTime"><%= HtmlUtility.escapeHTML(vo.getLblNightRestTime()) %></td>
				<td class="TitleTd" id="tdRestTitle3"><%= params.getName("Rest3") %></td>
				<td class="InputTd" id="lblRestTime3"><%= HtmlUtility.escapeHTML(vo.getLblRestTime3()) %></td>
				<td class="TitleTd" id="tdRestTitle4"><%= params.getName("Rest4") %></td>
				<td class="InputTd" id="lblRestTime4"><%= HtmlUtility.escapeHTML(vo.getLblRestTime4()) %></td>
			</tr>
			<tr>
				<td class="Blank" colspan="2"  id="blankRest"></td>
				<td class="TitleTd" id="tdRestTitle5"><%= params.getName("Rest5") %></td>
				<td class="InputTd" id="lblRestTime5"><%= HtmlUtility.escapeHTML(vo.getLblRestTime5()) %></td>
				<td class="TitleTd" id="tdRestTitle6"><%= params.getName("Rest6") %></td>
				<td class="InputTd" id="lblRestTime6"><%= HtmlUtility.escapeHTML(vo.getLblRestTime6()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Official","GoingOut","Time") %></td>
				<td class="InputTd" id="lblPublicTime"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime()) %></td>
				<td class="TitleTd"><%= params.getName("Official","GoingOut","No1") %></td>
				<td class="InputTd" id="lblPublicTime1"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime1()) %></td>
				<td class="TitleTd"><%= params.getName("Official","GoingOut","No2") %></td>
				<td class="InputTd" id="lblPublicTime2"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut","Time") %></td>
				<td class="InputTd" id="lblPrivateTime"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime()) %></td>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut1") %></td>
				<td class="InputTd" id="lblPrivateTime1"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime1()) %></td>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut2") %></td>
				<td class="InputTd" id="lblPrivateTime2"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime2()) %></td>
			</tr>
		</table>
		
<%
// 分単位休暇有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_MINUTELY_HOLIDAY)){
%>
		
		<table class="InputTable" id="tblMinutelyHolidayA">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("MinutelyHolidayAInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMinutelyHolidayATitle"><%= params.getName("MinutelyHolidayA","Time") %>   </td>
				<td class="InputTd" id="tdMinutelyHolidayAInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA1Title"><%= params.getName("MinutelyHolidayAAbbr","No1") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA1Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime1()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA2Title"><%= params.getName("MinutelyHolidayAAbbr","No2") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA2Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdAllMinutelyHolidayATitle"><%= params.getName("MinutelyHolidayA","AllTime") %>   </td>
				<td class="InputTd" id="tdAllMinutelyHolidayAInput"><%= HtmlUtility.escapeHTML(vo.getLblAllMinutelyHolidayA()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA3Title"><%= params.getName("MinutelyHolidayAAbbr","No3") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA3Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime3()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA4Title"><%= params.getName("MinutelyHolidayAAbbr","No4") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA4Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime4()) %></td>
			</tr>
		</table>
		<table class="InputTable" id="tblMinutelyHolidayB">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("MinutelyHolidayBInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMinutelyHolidayBTitle"><%= params.getName("MinutelyHolidayB","Time") %>   </td>
				<td class="InputTd" id="tdMinutelyHolidayBInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB1Title"><%= params.getName("MinutelyHolidayBAbbr","No1") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB1Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime1()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB2Title"><%= params.getName("MinutelyHolidayBAbbr","No2") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB2Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdAllMinutelyHolidayBTitle"><%= params.getName("MinutelyHolidayB","AllTime") %>   </td>
				<td class="InputTd" id="tdAllMinutelyHolidayBInput"><%= HtmlUtility.escapeHTML(vo.getLblAllMinutelyHolidayB()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB3Title"><%= params.getName("MinutelyHolidayBAbbr","No3") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB3Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime3()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB4Title"><%= params.getName("MinutelyHolidayBAbbr","No4") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB4Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime4()) %></td>
			</tr>
		</table>
	
<%
}
%>		
		
		<table class="InputTable" id="tblLateEarly">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("TardinessLeaveEarlyInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Tardiness","Time") %></td>
				<td class="InputTd" id="lblLateTime"><%= HtmlUtility.escapeHTML(vo.getLblLateTime()) %></td>
				<td class="TitleTd"><%= params.getName("Tardiness","Reason") %></td>
				<td class="InputTd" id="lblLateReason"><%= HtmlUtility.escapeHTML(vo.getLblLateReason()) %></td>
				<td class="TitleTd"><%= params.getName("Certificates") %></td>
				<td class="InputTd" id="lblLateCertificate"><%= HtmlUtility.escapeHTML(vo.getLblLateCertificate()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Comment") %></td>
				<td class="InputTd" id="lblLateComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblLateComment()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("LeaveEarly","Time") %></td>
				<td class="InputTd" id="lblLeaveEarlyTime"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyTime()) %></td>
				<td class="TitleTd"><%= params.getName("LeaveEarly","Reason") %></td>
				<td class="InputTd" id="lblLeaveEarlyReason"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyReason()) %></td>
				<td class="TitleTd"><%= params.getName("Certificates") %></td>
				<td class="InputTd" id="lblLeaveEarlyCertificate"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyCertificate()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Comment") %></td>
				<td class="InputTd" id="lblLeaveEarlyComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyComment()) %></td>
			</tr>
		</table>
		<table class="InputTable" id="tblOverTime">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("OvertimeWorkInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdOvertimeIn"><%= params.getName("OvertimeWork","Time") %></td>
				<td class="InputTd" id="lblOverTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeIn()) %></td>
				<td class="TitleTd" id="tdOvertimeOut"><%= params.getName("Legal","Outside","OvertimeWork","Time") %></td>
				<td class="InputTd" id="lblOverTimeOut"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeOut()) %></td>
				<td class="TitleTd"><%= params.getName("Midnight","Time") %></td>
				<td class="InputTd" id="lblLateNightTime"><%= HtmlUtility.escapeHTML(vo.getLblLateNightTime()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Prescribed","Holiday","Work","Time") %></td>
				<td class="InputTd" id="lblSpecificWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblSpecificWorkTimeIn()) %></td>
				<td class="TitleTd"><%= params.getName("Legal","Holiday","Work","Time") %></td>
				<td class="InputTd" id="lblLegalWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblLegalWorkTime()) %></td>
				<td class="TitleTd"><%= params.getName("Reduced","Target","Time") %></td>
				<td class="InputTd" id="lblDecreaseTime"><%= HtmlUtility.escapeHTML(vo.getLblDecreaseTime()) %></td>
			</tr>
		</table>
	</div>
	<table class="LeftListTable">
		<tr>
<% if (vo.isAttendance()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkApprovalSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtAttendanceComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isAttendance() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtAttendanceComment" name="txtAttendanceComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblAttendanceComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblAttendanceState"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceState()) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblAttendanceApprover"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblOvertimeType().length > 0) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isOvertime()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("OvertimeRequestSituation") %></span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblOvertimeType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Type") %></td>
			<td class="InputTd" id="lblOverTimeType"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application","Time") %></td>
			<td class="InputTd" id="lblOverTimeSchedule"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeSchedule()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Performance","Time") %></td>
			<td class="InputTd" id="lblOverTimeResult"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeResult()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Reason") %></td>
			<td class="InputTd" id="lblOverTimeReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblOverTimeState"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtOverTimeComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<%	if (vo.isOvertime() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtOverTimeComment" name="txtOverTimeComment" />
<%	} else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblOvertimeComment()[i]) %>
<%	} %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblOverTimeApprover"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (vo.getLblHolidayType().length > 0) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("HolidayRequestSituation") %></span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblHolidayType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("Date") %></td>
			<td class="InputTd" id="lblHolidayDate" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblHolidayDate()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Vacation","Classification") %></td>
			<td class="InputTd" id="lblHolidayType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Vacation","Content") %></td>
			<td class="InputTd" id="lblHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblHolidayLength()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Vacation","Time") %></td>
			<td class="InputTd" id="lblHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblHolidayTime()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Vacation","Reason") %></td>
			<td class="InputTd" id="lblHolidayReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtHolidayComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<%	if (vo.isHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtHolidayComment" name="txtHolidayComment" />
<%	} else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblHolidayComment()[i]) %>
<%	} %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (vo.getLblWorkOnHolidayDate() != null) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isWorkOnHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkOnHolidayRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Schedule","Moment") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTime()) %></td>
			<td class="TitleTd"><%= params.getName("Transfer") %><%= params.getName("Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTransferDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTransferDate()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblWorkOnHolidayReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkOnHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtWorkOnHolidayComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isWorkOnHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtWorkOnHolidayComment" name="txtWorkOnHolidayComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkOnHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblSubHolidayDate() != null) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isSubHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("SubHolidayRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
			<td class="InputTd" id="lblSubHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblSubHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayWorkDate()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblSubHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtCompensationComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isSubHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtCompensationComment" name="txtCompensationComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblSubHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblWorkTypeChangeDate() != null) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isWorkTypeChange()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkTypeChangeRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork") %><%= params.getName("Day") %></td>
			<td class="InputTd" id="lblWorkTypeChangeDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeDate()) %></td>
			<td class="TitleTd"><%= params.getName("Change") %><%= params.getName("Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeBeforeWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Change") %><%= params.getName("Later","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeAfterWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeAfterWorkType()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblWorkTypeChangeReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkTypeChangeState"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtWorkTypeChangeComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isWorkTypeChange() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtWorkTypeChangeComment" name="txtWorkTypeChangeComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkTypeChangeApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblDifferenceDate() != null) {
%>
	<table class="LeftListTable">
		<tr>
<% if (vo.isDifference()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("jp.mosp.time.input.vo.DifferenceRequestVo","Situation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblDifferenceDate"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceDate()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblDifferenceWorkType"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Later","Work","Time") %></td>
			<td class="InputTd" id="lblDifferenceWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblDifferenceReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblDifferenceState"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtDifferenceComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isDifference() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtDifferenceComment" name="txtDifferenceComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblDifferenceComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblDifferenceApprover"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.isNeedCancelApproveButton()) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="EditTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Approval","Release","Situation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtCancelComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
				<input type="text" class="Name30TextBox" id="txtCancelComment" name="txtCancelComment" />
			</td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblCancelState"><%= HtmlUtility.escapeHTML(vo.getLblCancelState()) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblCancelApprover"><%= HtmlUtility.escapeHTML(vo.getLblCancelApprover()) %></td>
		</tr>
	</table>
<%
}
%>
</div>
<div class="Button">
<%
if (vo.isNeedCancelButton()) {
%>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= ApprovalCardAction.CMD_DELETE %>');"><%= params.getName("Approval","Release") %></button>
<%
}
if (vo.isNeedApproveButton()) {
%>
	<button type="button" class="Name4Button" onclick="submitRegist(event, 'ApprovalUpdate', null, '<%= ApprovalCardAction.CMD_APPROVAL %>');"><%= params.getName("Approval") %></button>
	<button type="button" class="Name4SendingBackButton" onclick="submitRegist(event, 'ApprovalUpdate', checkRevert, '<%= ApprovalCardAction.CMD_REVERTING %>');"><%= params.getName("SendingBack") %></button>
<%
}
if (vo.isNeedCancelApproveButton()) {
%>
	<button type="button" id="btnDelete" class="Name4CancelApproveButton" onclick="submitDelete(event, null, null, '<%= ApprovalCardAction.CMD_DELETE %>');"><%= params.getName("Approval","Release") %></button>
	<button type="button" class="Name4Button" onclick="submitRegist(event, 'ApprovalUpdate', checkRevert, '<%= ApprovalCardAction.CMD_REVERTING %>');"><%= params.getName("Release","TakeDown") %></button>
<%
}
%>
</div>
