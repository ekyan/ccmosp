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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.input.action.DifferenceRequestAction"
import = "jp.mosp.time.input.action.HolidayRequestAction"
import = "jp.mosp.time.input.action.OvertimeRequestAction"
import = "jp.mosp.time.input.action.SubHolidayRequestAction"
import = "jp.mosp.time.input.action.WorkOnHolidayRequestAction"
import = "jp.mosp.time.input.action.WorkTypeChangeRequestAction"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
	<table class="DateChangeTable">
		<tr>
			<td><a class="RollLink" id="eventFormer" name="eventFormer" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_DECREMENT %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');">&lt;&lt;</a></td>
			<td>
				&nbsp; 
				<%= HtmlUtility.escapeHTML(vo.getLblYear()) %><%= params.getName("Year") %>
				<%= HtmlUtility.escapeHTML(vo.getLblMonth()) %><%= params.getName("Month") %>
				<%= HtmlUtility.escapeHTML(vo.getLblDay()) %><%= params.getName("Day") %>
			</td>
			<td><a class="RollLink" id="eventNext" name="eventNext" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_INCREMENT %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');">&gt;&gt;</a></td>
			<td class="BetweenTd"></td>
			<td>
				<button type="button" class="Name2Button" id="btnReset" name="btnReset" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_RESET %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');"><%= params.getName("Now","Day") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List">
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%= params.getName("Approver","Set") %></span>
			</th>
		</tr>
	</table>
	<jsp:include page="<%= TimeConst.PATH_TIME_APPROVER_PULLDOWN_JSP %>" flush="false" />
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%= params.getName("CorrectionInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblCorrection">
		<tr>
			<td class="TitleTd"><%= params.getName("CorrectionSummary") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblCorrectionHistory()) %></td>
		</tr>
	</table>
	<table class="HeaderInputTable" id="tblAttendanceHeader">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("AttendanceInformation") %></span></th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblAttendance">
		<tr>
			<td class="TitleTd"><%= params.getName("Work","Form") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("WorkTypePullDown", "pltWorkType", "pltWorkType", vo.getPltWorkType(), vo.getAryPltWorkType(), vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_APPLY) || vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_APPLIED) || vo.getModeCardEdit().equals(AttendanceCardAction.MODE_APPLICATION_COMPLETED_HOLIDAY)) %>
			</td>
			<td class="TitleTd"><label for="txtStartTimeHour"><%= params.getName("StartWork","Moment") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtStartTimeHour", "txtStartTimeHour", vo.getTxtStartTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtStartTimeMinute", "txtStartTimeMinute", vo.getTxtStartTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
				<%= vo.getLblStartTime() %>
			</td>
			<td class="TitleTd"><label for="txtEndTimeHour"><%= params.getName("EndWork","Moment") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtEndTimeHour", "txtEndTimeHour", vo.getTxtEndTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtEndTimeMinute", "txtEndTimeMinute", vo.getTxtEndTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
				<%= vo.getLblEndTime() %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Work","Time") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblWorkTime()) %></td>
			<td class="TitleTd"><%= params.getName("Prescribed","Labor","Time") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblGeneralWorkTime()) %></td>
			<td class="TitleTd"><%= params.getName("DirectStart","Slash","DirectEnd") %></td>
			<td class="InputTd">
				<input type="checkbox" class="CheckBox" id="ckbDirectStart" name="ckbDirectStart" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectStart()) %>>&nbsp;<%= params.getName("DirectStart") %>&nbsp;
				<input type="checkbox" class="CheckBox" id="ckbDirectEnd" name="ckbDirectEnd" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectEnd()) %>>&nbsp;<%= params.getName("DirectEnd") %>
			</td>
		</tr>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
		<tr>
			<td class="TitleTd" id="tdUnpaidShortTime" ><%= params.getName("UnpaidShortTime") %></td>
			<td class="InputTd" id="tdLblUnpaidShortTime" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblUnpaidShortTime()) %></td>
		</tr>
<%
}
%>
		<tr>
			<td class="TitleTd"><label for="txtTimeComment"><%= params.getName("WorkManageComment") %></label></td>
			<td class="InputTd" id="tdAttendanceComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtTimeComment", "txtTimeComment", vo.getTxtTimeComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
<%
if (params.getGeneralParam(AttendanceCardAction.PRM_ATTENDANCE_EXTRA_JSP) != null) {
%>
	<jsp:include page="<%= (String)params.getGeneralParam(AttendanceCardAction.PRM_ATTENDANCE_EXTRA_JSP) %>" flush="false" />
<%
}
%>
	<table class="HeaderInputTable" id="tblRestHeader">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("RestTimeInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblRest">
		<tr>
			<td class="TitleTd" id="tdRestTitle"><%= params.getName("RestTime","Time") %></td>
			<td class="InputTd" id="tdRestInput"><%= HtmlUtility.escapeHTML(vo.getLblRestTime()) %></td>
			<td class="TitleTd" id="tdRestTitle1"><label for="txtRestStartHour1"><%= params.getName("Rest1") %></label></td>
			<td class="InputTd" id="tdRestTitle1Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour1", "txtRestStartHour1", vo.getTxtRestStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute1", "txtRestStartMinute1", vo.getTxtRestStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour1", "txtRestEndHour1", vo.getTxtRestEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute1", "txtRestEndMinute1", vo.getTxtRestEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle2"><label for="txtRestStartHour2"><%= params.getName("Rest2") %></label></td>
			<td class="InputTd" id="tdRestTitle2Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour2", "txtRestStartHour2", vo.getTxtRestStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute2", "txtRestStartMinute2", vo.getTxtRestStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour2", "txtRestEndHour2", vo.getTxtRestEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute2", "txtRestEndMinute2", vo.getTxtRestEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Midnight","RestTime","Time") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblNightRestTime()) %></td>
			<td class="TitleTd" id="tdRestTitle3"><label for="txtRestStartHour3"><%= params.getName("Rest3") %></label></td>
			<td class="InputTd" id="tdRestTitle3Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour3", "txtRestStartHour3", vo.getTxtRestStartHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute3", "txtRestStartMinute3", vo.getTxtRestStartMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour3", "txtRestEndHour3", vo.getTxtRestEndHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute3", "txtRestEndMinute3", vo.getTxtRestEndMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle4"><label for="txtRestStartHour4"><%= params.getName("Rest4") %></label></td>
			<td class="InputTd" id="tdRestTitle4Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour4", "txtRestStartHour4", vo.getTxtRestStartHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute4", "txtRestStartMinute4", vo.getTxtRestStartMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour4", "txtRestEndHour4", vo.getTxtRestEndHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute4", "txtRestEndMinute4", vo.getTxtRestEndMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="Blank" colspan="2" id="blankRest"></td>
			<td class="TitleTd" id="tdRestTitle5"><label for="txtRestStartHour5"><%= params.getName("Rest5") %></label></td>
			<td class="InputTd" id="tdRestTitle5Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour5", "txtRestStartHour5", vo.getTxtRestStartHour5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute5", "txtRestStartMinute5", vo.getTxtRestStartMinute5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour5", "txtRestEndHour5", vo.getTxtRestEndHour5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute5", "txtRestEndMinute5", vo.getTxtRestEndMinute5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle6"><label for="txtRestStartHour6"><%= params.getName("Rest6") %></label></td>
			<td class="InputTd" id="tdRestTitle6Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour6", "txtRestStartHour6", vo.getTxtRestStartHour6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute6", "txtRestStartMinute6", vo.getTxtRestStartMinute6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour6", "txtRestEndHour6", vo.getTxtRestEndHour6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute6", "txtRestEndMinute6", vo.getTxtRestEndMinute6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdBorderTop"><%= params.getName("PublicGoingOut","Time") %></td>
			<td class="InputTd" id="tdBorderTop"><%= vo.getLblPublicTime() %></td>
			<td class="TitleTd"><label for="txtPublicStartHour1"><%= params.getName("PublicGoingOut1") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour1", "txtPublicStartHour1", vo.getTxtPublicStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute1", "txtPublicStartMinute1", vo.getTxtPublicStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour1", "txtPublicEndHour1", vo.getTxtPublicEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute1", "txtPublicEndMinute1", vo.getTxtPublicEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd"><label for="txtPublicStartHour2"><%= params.getName("PublicGoingOut2") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour2", "txtPublicStartHour2", vo.getTxtPublicStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute2", "txtPublicStartMinute2", vo.getTxtPublicStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour2", "txtPublicEndHour2", vo.getTxtPublicEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute2", "txtPublicEndMinute2", vo.getTxtPublicEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("PrivateGoingOut","Time") %></td>
			<td class="InputTd"><%= vo.getLblPrivateTime() %></td>
			<td class="TitleTd"><label for="txtPrivateStartHour1"><%= params.getName("PrivateGoingOut1") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartHour1", "txtPrivateStartHour1", vo.getTxtPrivateStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartMinute1", "txtPrivateStartMinute1", vo.getTxtPrivateStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndHour1", "txtPrivateEndHour1", vo.getTxtPrivateEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndMinute1", "txtPrivateEndMinute1", vo.getTxtPrivateEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd"><label for="txtPrivateStartHour2"><%= params.getName("PrivateGoingOut2") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartHour2", "txtPrivateStartHour2", vo.getTxtPrivateStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartMinute2", "txtPrivateStartMinute2", vo.getTxtPrivateStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndHour2", "txtPrivateEndHour2", vo.getTxtPrivateEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndMinute2", "txtPrivateEndMinute2", vo.getTxtPrivateEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
	</table>
<%
// 分単位休暇有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_MINUTELY_HOLIDAY)){
%>
	<table class="HeaderInputTable" id="tblMinutelyHolidayABox">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("MinutelyHolidayAInformation") %></span>
				<span class="TableLabelSpan">
					<input type="checkbox" class="CheckBox" id="ckbAllMinutelyHolidayA" name="ckbAllMinutelyHolidayA" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayA()) %>>&nbsp;<%= params.getName("AllTime") %>&nbsp;
				</span>
			</th>
	</table>
	<table class="FixInputTable" id="tblMinutelyHolidayA">
		<tr>
			<td class="TitleTd" id="tdMinutelyHolidayATitle"><%= params.getName("MinutelyHolidayA","Time") %></td>
			<td class="InputTd" id="tdMinutelyHolidayAInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayAInput()) %></td>
			<td class="TitleTd" id="tdMinutelyHolidayA1Title"><label for="txtMinutelyHolidayAStartHour1"><%= params.getName("MinutelyHolidayAAbbr","No1") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayA1Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartHour1", "txtMinutelyHolidayAStartHour1", vo.getTxtMinutelyHolidayAStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartMinute1", "txtMinutelyHolidayAStartMinute1", vo.getTxtMinutelyHolidayAStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndHour1", "txtMinutelyHolidayAEndHour1", vo.getTxtMinutelyHolidayAEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndMinute1", "txtMinutelyHolidayAEndMinute1", vo.getTxtMinutelyHolidayAEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdMinutelyHolidayA2Title"><label for="txtMinutelyHolidayAStartHour2"><%= params.getName("MinutelyHolidayAAbbr","No2") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayA2Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartHour2", "txtMinutelyHolidayAStartHour2", vo.getTxtMinutelyHolidayAStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartMinute2", "txtMinutelyHolidayAStartMinute2", vo.getTxtMinutelyHolidayAStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndHour2", "txtMinutelyHolidayAEndHour2", vo.getTxtMinutelyHolidayAEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndMinute2", "txtMinutelyHolidayAEndMinute2", vo.getTxtMinutelyHolidayAEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="Blank" colspan="2"></td>
			<td class="TitleTd" id="tdMinutelyHolidayA3Title"><label for="txtMinutelyHolidayAStartHour3"><%= params.getName("MinutelyHolidayAAbbr","No3") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayA3Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartHour3", "txtMinutelyHolidayAStartHour3", vo.getTxtMinutelyHolidayAStartHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartMinute3", "txtMinutelyHolidayAStartMinute3", vo.getTxtMinutelyHolidayAStartMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndHour3", "txtMinutelyHolidayAEndHour3", vo.getTxtMinutelyHolidayAEndHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndMinute3", "txtMinutelyHolidayAEndMinute3", vo.getTxtMinutelyHolidayAEndMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdMinutelyHolidayA4Title"><label for="txtMinutelyHolidayAStartHour4"><%= params.getName("MinutelyHolidayAAbbr","No4") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayA4Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartHour4", "txtMinutelyHolidayAStartHour4", vo.getTxtMinutelyHolidayAStartHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAStartMinute4", "txtMinutelyHolidayAStartMinute4", vo.getTxtMinutelyHolidayAStartMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndHour4", "txtMinutelyHolidayAEndHour4", vo.getTxtMinutelyHolidayAEndHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayAEndMinute4", "txtMinutelyHolidayAEndMinute4", vo.getTxtMinutelyHolidayAEndMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
	</table>
	<table class="HeaderInputTable" id="tblMinutelyHolidayBBox">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("MinutelyHolidayBInformation") %></span>
				<span class="TableLabelSpan">
					<input type="checkbox" class="CheckBox" id="ckbAllMinutelyHolidayB" name="ckbAllMinutelyHolidayB" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayB()) %>>&nbsp;<%= params.getName("AllTime") %>&nbsp;
				</span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblMinutelyHolidayB">
		<tr>
			<td class="TitleTd" id="tdMinutelyHolidayBTitle"><%= params.getName("MinutelyHolidayB","Time") %></td>
			<td class="InputTd" id="tdMinutelyHolidayBInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBInput()) %></td>
			<td class="TitleTd" id="tdMinutelyHolidayB1Title"><label for="txtMinutelyHolidayBStartHour1"><%= params.getName("MinutelyHolidayBAbbr","No1") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayB1Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartHour1", "txtMinutelyHolidayBStartHour1", vo.getTxtMinutelyHolidayBStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartMinute1", "txtMinutelyHolidayBStartMinute1", vo.getTxtMinutelyHolidayBStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndHour1", "txtMinutelyHolidayBEndHour1", vo.getTxtMinutelyHolidayBEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndMinute1", "txtMinutelyHolidayBEndMinute1", vo.getTxtMinutelyHolidayBEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdMinutelyHolidayB2Title"><label for="txtMinutelyHolidayBStartHour2"><%= params.getName("MinutelyHolidayBAbbr","No2") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayB2Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartHour2", "txtMinutelyHolidayBStartHour2", vo.getTxtMinutelyHolidayBStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartMinute2", "txtMinutelyHolidayBStartMinute2", vo.getTxtMinutelyHolidayBStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndHour2", "txtMinutelyHolidayBEndHour2", vo.getTxtMinutelyHolidayBEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndMinute2", "txtMinutelyHolidayBEndMinute2", vo.getTxtMinutelyHolidayBEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="Blank" colspan="2"></td>
			<td class="TitleTd" id="tdMinutelyHolidayB3Title"><label for="txtMinutelyHolidayBStartHour3"><%= params.getName("MinutelyHolidayBAbbr","No3") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayB3Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartHour3", "txtMinutelyHolidayBStartHour3", vo.getTxtMinutelyHolidayBStartHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartMinute3", "txtMinutelyHolidayBStartMinute3", vo.getTxtMinutelyHolidayBStartMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndHour3", "txtMinutelyHolidayBEndHour3", vo.getTxtMinutelyHolidayBEndHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndMinute3", "txtMinutelyHolidayBEndMinute3", vo.getTxtMinutelyHolidayBEndMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdMinutelyHolidayB4Title"><label for="txtMinutelyHolidayBStartHour4"><%= params.getName("MinutelyHolidayBAbbr","No4") %></label></td>
			<td class="InputTd" id="tdMinutelyHolidayB4Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartHour4", "txtMinutelyHolidayBStartHour4", vo.getTxtMinutelyHolidayBStartHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBStartMinute4", "txtMinutelyHolidayBStartMinute4", vo.getTxtMinutelyHolidayBStartMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndHour4", "txtMinutelyHolidayBEndHour4", vo.getTxtMinutelyHolidayBEndHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtMinutelyHolidayBEndMinute4", "txtMinutelyHolidayBEndMinute4", vo.getTxtMinutelyHolidayBEndMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
	</table>
<%
} else {
%>
	<input type="hidden" class="CheckBox" id="ckbAllMinutelyHolidayA" name="ckbAllMinutelyHolidayA" value="<%= MospConst.CHECKBOX_OFF %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayA()) %>>
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartHour1" name="txtMinutelyHolidayAStartHour1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartHour1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartMinute1" name="txtMinutelyHolidayAStartMinute1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartMinute1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndHour1" name="txtMinutelyHolidayAEndHour1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndHour1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndMinute1" name="txtMinutelyHolidayAEndMinute1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndMinute1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartHour2" name="txtMinutelyHolidayAStartHour2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartHour2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartMinute2" name="txtMinutelyHolidayAStartMinute2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartMinute2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndHour2" name="txtMinutelyHolidayAEndHour2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndHour2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndMinute2" name="txtMinutelyHolidayAEndMinute2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndMinute2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartHour3" name="txtMinutelyHolidayAStartHour3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartHour3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartMinute3" name="txtMinutelyHolidayAStartMinute3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartMinute3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndHour3" name="txtMinutelyHolidayAEndHour3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndHour3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndMinute3" name="txtMinutelyHolidayAEndMinute3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndMinute3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartHour4" name="txtMinutelyHolidayAStartHour4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartHour4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAStartMinute4" name="txtMinutelyHolidayAStartMinute4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAStartMinute4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndHour4" name="txtMinutelyHolidayAEndHour4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndHour4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayAEndMinute4" name="txtMinutelyHolidayAEndMinute4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayAEndMinute4()) %>" />
	<input type="hidden" class="CheckBox" id="ckbAllMinutelyHolidayB" name="ckbAllMinutelyHolidayB" value="<%= MospConst.CHECKBOX_OFF %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayB()) %>>
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartHour1" name="txtMinutelyHolidayBStartHour1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartHour1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartMinute1" name="txtMinutelyHolidayBStartMinute1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartMinute1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndHour1" name="txtMinutelyHolidayBEndHour1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndHour1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndMinute1" name="txtMinutelyHolidayBEndMinute1" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndMinute1()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartHour2" name="txtMinutelyHolidayBStartHour2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartHour2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartMinute2" name="txtMinutelyHolidayBStartMinute2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartMinute2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndHour2" name="txtMinutelyHolidayBEndHour2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndHour2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndMinute2" name="txtMinutelyHolidayBEndMinute2" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndMinute2()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartHour3" name="txtMinutelyHolidayBStartHour3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartHour3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartMinute3" name="txtMinutelyHolidayBStartMinute3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartMinute3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndHour3" name="txtMinutelyHolidayBEndHour3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndHour3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndMinute3" name="txtMinutelyHolidayBEndMinute3" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndMinute3()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartHour4" name="txtMinutelyHolidayBStartHour4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartHour4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBStartMinute4" name="txtMinutelyHolidayBStartMinute4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBStartMinute4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndHour4" name="txtMinutelyHolidayBEndHour4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndHour4()) %>" />
	<input type="hidden" class="Number2TextBox" id="txtMinutelyHolidayBEndMinute4" name="txtMinutelyHolidayBEndMinute4" value="<%= HtmlUtility.escapeHTML(vo.getTxtMinutelyHolidayBEndMinute4()) %>" />
<%	
}
%>
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%= params.getName("TardinessLeaveEarlyInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblLateEarly">
		<tr>
			<td class="TitleTd"><%= params.getName("Tardiness","Time") %></td>
			<td class="InputTd" id="tdLateEarlyTime"><%= HtmlUtility.escapeHTML(vo.getLblLateTime()) %></td>
			<td class="TitleTd"><%= params.getName("Tardiness","Reason") %></td>
			<td class="InputTd" id="tdLateEarlyReason">
				<%= HtmlUtility.getSelectTag("Name4PullDown", "pltLateReason", "pltLateReason", vo.getPltLateReason(),vo.getAryPltLateReason(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><%= params.getName("Tardiness","Certificates") %></td>
			<td class="InputTd" id="tdLateEarlyCertificates">
				<%= HtmlUtility.getSelectTag("Name3PullDown", "pltLateCertificate", "pltLateCertificate", vo.getPltLateCertificate(),vo.getAryPltLateCertificate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Tardiness","Comment") %></td>
			<td class="InputTd" id="tdLateEarlyComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtLateComment", "txtLateComment", vo.getTxtLateComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Time") %></td>
			<td class="InputTd"><%= vo.getLblLeaveEarlyTime() %></td>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Reason") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("Name4PullDown", "pltLeaveEarlyReason", "pltLeaveEarlyReason", vo.getPltLeaveEarlyReason(),vo.getAryPltLeaveEarlyReason(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Certificates") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("Name3PullDown", "pltLeaveEarlyCertificate", "pltLeaveEarlyCertificate", vo.getPltLeaveEarlyCertificate(),vo.getAryPltLateCertificate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Comment") %></td>
			<td class="InputTd" id="tdLateEarlyComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtLeaveEarlyComment", "txtLeaveEarlyComment", vo.getTxtLeaveEarlyComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("OvertimeWorkInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblOvertime">
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertime"><%= HtmlUtility.escapeHTML(vo.getLblOvertime()) %></td>
			<td class="TitleTd" id="tdOvertimeIn"><%= params.getName("Legal","Inside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeIn"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeIn()) %></td>
			<td class="TitleTd" id="tdOvertimeOut"><%= params.getName("Legal","Outside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeOut"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeOut()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Midnight","Work","Time") %></td>
			<td class="InputTd" id="lblLateNightTime"><%= HtmlUtility.escapeHTML(vo.getLblLateNightTime()) %></td>
			<td class="TitleTd"><%= params.getName("Prescribed","Holiday","Work","Time") %></td>
			<td class="InputTd" id="lblSpecificWorkTime"><%=HtmlUtility.escapeHTML( vo.getLblSpecificWorkTimeIn()) %></td>
			<td class="TitleTd"><%= params.getName("Legal","Holiday","Work","Time") %></td>
			<td class="InputTd" id="lblLegalWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblLegalWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Reduced","Target","Time") %></td>
			<td class="InputTd" id="lblDecreaseTime"><%= HtmlUtility.escapeHTML(vo.getLblDecreaseTime()) %></td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
<!--
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="10">
				<span class="TitleTh"><= params.getName("Allowance","Information") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblAllowance">
		<tr>
			<td class="TitleTd"><= params.getName("Allowance","No1") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance1", "pltAllowance1", vo.getPltAllowance1(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No2") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance2", "pltAllowance2", vo.getPltAllowance2(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No3") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance3", "pltAllowance3", vo.getPltAllowance3(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No4") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance4", "pltAllowance4", vo.getPltAllowance4(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No5") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance5", "pltAllowance5", vo.getPltAllowance5(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><= params.getName("Allowance","No6") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance6", "pltAllowance6", vo.getPltAllowance6(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No7") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance7", "pltAllowance7", vo.getPltAllowance7(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No8") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance8", "pltAllowance8", vo.getPltAllowance8(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No9") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance9", "pltAllowance9", vo.getPltAllowance9(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No10") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance10", "pltAllowance10", vo.getPltAllowance10(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
-->
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, '', checkRegistTimes, '<%= AttendanceCardAction.CMD_APPLI %>')"><%= params.getName("Application") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnDraft" name="btnDraft" onclick="submitRegist(event, '', checkDraftTimes, '<%= AttendanceCardAction.CMD_DRAFT %>')"><%= params.getName("WorkPaper") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnDelete" onclick="submitRegist(event, null, null, '<%= AttendanceCardAction.CMD_DELETE %>')"><%= params.getName("Delete") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="FixList">
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("WorkApprovalSituation") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceComment()) %></td>
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
if (TimeUtility.isOvertimeRequestAvailable(params)) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("OvertimeRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnOvertimeRequest" name="btnOvertimeRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblOvertimeTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblOvertimeCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.OvertimeRequestVo","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblOvertimeType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Type") %></td>
			<td class="InputTd" id="lblOvertimeType"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application","Time") %></td>
			<td class="InputTd" id="lblOvertimeSchedule"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeSchedule()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Performance","Time") %></td>
			<td class="InputTd" id="lblOvertimeResult"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeResult()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblOvertimeState"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblOvertimeApprover"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("HolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnHolidayRequest" name="btnHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblHolidayTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblHolidayCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.HolidayRequestVo","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblHolidayType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("Vacation","Classification") %></td>
			<td class="InputTd" id="lblHolidayType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Vacation","Content") %></td>
			<td class="InputTd" id="lblHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblHolidayLength()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Vacation","Time") %></td>
			<td class="InputTd" id="lblHolidayTime"><%= vo.getLblHolidayTime()[i] %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Vacation","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application", "Ahead", "Work", "Form") %></td>
			<td class="InputTd" id="lblHolidayWorkType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayWorkType()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isWorkOnHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("WorkOnHolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnWorkOnHolidayRequest" name="btnWorkOnHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTransferParams()) %>), '<%= vo.getLblWorkOnHolidayCmd() %>');"><%= params.getName("SubstituteAbbr","GoingWorkAbbr","WorkingHoliday","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblWorkOnHolidayDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Schedule","Moment") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTime()) %></td>
			<td class="TitleTd"><%= params.getName("Transfer","Day") %></td>
			<td class="InputTd" id="lblSubStituteDate"><%= HtmlUtility.escapeHTML(vo.getLblSubStituteDate()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkOnHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkOnHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isSubHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("SubHolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnSubHolidayRequest" name="btnSubHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.SubHolidayRequestVo") %><%= params.getName("Information") %><%= params.getName("Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblSubHolidayDate().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
			<td class="InputTd" id="lblSubHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayDate()[i]) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblSubHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayWorkDate()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblSubHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblSubHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isWorkTypeChangeRequestAvailable(params)) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("WorkTypeChangeRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnWorkTypeChangeRequest" name="btnWorkTypeChangeRequest"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= WorkTypeChangeRequestAction.class.getName() %>'), '<%= AttendanceCardAction.CMD_TRANSFER %>');"><%= params.getName("WorkTypeAbbr") %><%= params.getName("Change") %><%= params.getName("Information") %><%= params.getName("Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblWorkTypeChangeDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkTypeChangeDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeDate()) %></td>
			<td class="TitleTd"><%= params.getName("Change","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeBeforeWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Change","Later","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeAfterWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeAfterWorkType()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkTypeChangeState"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkTypeChangepprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (params.getProperties().getMainMenuProperties().get("menuTimeInput").getMenuMap().containsKey("DifferenceRequest")
	&& params.getUserRole().getRoleMenuMap().containsKey("DifferenceRequest")) {
%>
	<table class="LeftListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("jp.mosp.time.input.vo.DifferenceRequestVo","Situation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnDifferenceRequest" name="btnDifferenceRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblDifferenceTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblDifferenceCmd()) %>');"><%= params.getName("TimeDifference","GoingWork","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblDifferenceDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblDifferenceDate"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceDate()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Later","Work","Time") %></td>
			<td class="InputTd" id="lblDifferenceWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblDifferenceState"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblDifferenceApprover"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
%>
</div>
