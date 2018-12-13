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
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblRestHeader">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("RestTimeInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblRest">
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
			<td class="TitleTd" id="tdPublicGoingOutTitle"><%= params.getName("PublicGoingOut","Time") %></td>
			<td class="InputTd" id="tdPublicGoingOutInput"><%= vo.getLblPublicTime() %></td>
			<td class="TitleTd" id="tdPublicStartHour1Title" ><label for="txtPublicStartHour1"><%= params.getName("PublicGoingOut1") %></label></td>
			<td class="InputTd" id="tdPublicStartHour1Input">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour1", "txtPublicStartHour1", vo.getTxtPublicStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute1", "txtPublicStartMinute1", vo.getTxtPublicStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour1", "txtPublicEndHour1", vo.getTxtPublicEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute1", "txtPublicEndMinute1", vo.getTxtPublicEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdPublicStartHour2Title" ><label for="txtPublicStartHour2"><%= params.getName("PublicGoingOut2") %></label></td>
			<td class="InputTd" id="tdPublicStartHour2Input">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour2", "txtPublicStartHour2", vo.getTxtPublicStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute2", "txtPublicStartMinute2", vo.getTxtPublicStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour2", "txtPublicEndHour2", vo.getTxtPublicEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute2", "txtPublicEndMinute2", vo.getTxtPublicEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdPrivateGoingOutTitle" ><%= params.getName("PrivateGoingOut","Time") %></td>
			<td class="InputTd" id="tdPrivateGoingOutInput" ><%= vo.getLblPrivateTime() %></td>
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
	<table class="HeaderInputTable" id="attendanceCard_tblMinutelyHolidayABox">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("MinutelyHolidayAInformation") %></span>
				<span class="TableLabelSpan">
					<input type="checkbox" class="CheckBox" id="ckbAllMinutelyHolidayA" name="ckbAllMinutelyHolidayA" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayA()) %>>&nbsp;<%= params.getName("AllTime") %>&nbsp;
				</span>
			</th>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblMinutelyHolidayA">
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
	<table class="HeaderInputTable" id="attendanceCard_tblMinutelyHolidayBBox">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("MinutelyHolidayBInformation") %></span>
				<span class="TableLabelSpan">
					<input type="checkbox" class="CheckBox" id="ckbAllMinutelyHolidayB" name="ckbAllMinutelyHolidayB" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbAllMinutelyHolidayB()) %>>&nbsp;<%= params.getName("AllTime") %>&nbsp;
				</span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblMinutelyHolidayB">
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
