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
import = "jp.mosp.time.calculation.action.TotalTimeCardAction"
import = "jp.mosp.time.calculation.action.TotalTimeListAction"
import = "jp.mosp.time.calculation.vo.TotalTimeCardVo"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TotalTimeCardVo vo = (TotalTimeCardVo)params.getVo();
%>
<jsp:include page="<%= TimeConst.PATH_TIME_TOTAL_JSP %>" flush="false" />
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<div class="List">
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%= params.getName("Correction","Information") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" id="btnSelect" name="btnSelect" onclick="submitTransfer(event, '', null, null, '<%= TotalTimeCardAction.CMD_UPDATE %>');"><%= vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT) ? params.getName("Correction") : params.getName("CorrectionRelease") %></button>
				</span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblCorrection">
		<tr>
			<td class="TitleTd" id="tdCorrectionTitle"><%= params.getName("Correction","History") %></td>
			<td class="InputTd">
				<%= HtmlUtility.escapeHTML(vo.getLblCorrectionHistory()) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtCorrectionReason"><%= params.getName("Correction","Reason") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Name40RequiredTextBox", "txtCorrectionReason", "txtCorrectionReason", vo.getTxtCorrectionReason(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Attendance","Item") %></span></th>
		</tr>
	</table>
	<table class="FixTable" id="tblAttendance">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Work","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkTimeHour", "txtWorkTimeHour", vo.getTxtWorkTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkTimeMinute", "txtWorkTimeMinute", vo.getTxtWorkTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("GoingWork","Days") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesWorkDate", "txtTimesWorkDate", vo.getTxtTimesWorkDate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesWorkDate"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("GoingWork","Frequency") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesWork", "txtTimesWork", vo.getTxtTimesWork(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesWork"><%= params.getName("CountNum") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Legal","Holiday","GoingWork","Days") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLegalWorkOnHoliday", "txtLegalWorkOnHoliday", vo.getTxtLegalWorkOnHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLegalWorkOnHoliday"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed") %><%= params.getName("Holiday","GoingWork","Days") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtSpecificWorkOnHoliday", "txtSpecificWorkOnHoliday", vo.getTxtSpecificWorkOnHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtSpecificWorkOnHoliday"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("GoingWork","Performance","Days") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesAchievement", "txtTimesAchievement", vo.getTxtTimesAchievement(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesAchievement"><%= params.getName("Day") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("GoingWork","Target","Days") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesTotalWorkDate", "txtTimesTotalWorkDate", vo.getTxtTimesTotalWorkDate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesTotalWorkDate"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesNonstop"><%= params.getName("DirectStart","Frequency") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesNonstop", "txtTimesNonstop", vo.getTxtTimesNonstop(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesNoreturn"><%= params.getName("DirectEnd","Frequency") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesNoreturn", "txtTimesNoreturn", vo.getTxtTimesNoreturn(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("CountNum") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed","Work","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtSpecificWorkTimeHour", "txtSpecificWorkTimeHour", vo.getTxtSpecificWorkTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtSpecificWorkTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtSpecificWorkTimeMinute", "txtSpecificWorkTimeMinute", vo.getTxtSpecificWorkTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtSpecificWorkTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>			
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("UnpaidShortTime") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtUnpaidShortTimeHour", "txtUnpaidShortTimeHour", vo.getTxtUnpaidShortTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtUnpaidShortTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtUnpaidShortTimeMinute", "txtUnpaidShortTimeMinute", vo.getTxtUnpaidShortTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtUnpaidShortTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="2"></td>
<%
}
%>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%= params.getName("RestTime","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblRest">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("RestTime","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestTimeHour", "txtRestTimeHour", vo.getTxtRestTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestTimeMinute", "txtRestTimeMinute", vo.getTxtRestTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Midnight","RestTime") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestLateNightHour", "txtRestLateNightHour", vo.getTxtRestLateNightHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestLateNightHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestLateNightMinute", "txtRestLateNightMinute", vo.getTxtRestLateNightMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestLateNightMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRestTitle"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed","WorkingHoliday","RestTime") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestWorkOnSpecificHour", "txtRestWorkOnSpecificHour", vo.getTxtRestWorkOnSpecificHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnSpecificHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestWorkOnSpecificMinute", "txtRestWorkOnSpecificMinute", vo.getTxtRestWorkOnSpecificMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnSpecificMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Legal","WorkingHoliday","RestTime") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestWorkOnLegalHour", "txtRestWorkOnLegalHour", vo.getTxtRestWorkOnLegalHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnLegalHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestWorkOnLegalMinute", "txtRestWorkOnLegalMinute", vo.getTxtRestWorkOnLegalMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnLegalMinute"><%= params.getName("Minutes") %></label>
			</td><!--
			<td class="TitleTd" id="tdBorderTop"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed","Holiday","Legal","Outside","RestTime") %></td>
			<td class="InputTd" id="tdBorderTop">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestWorkOnOverHour", "txtRestWorkOnOverHour", vo.getTxtRestWorkOnOverHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnOverHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestWorkOnOverMinute", "txtRestWorkOnOverMinute", vo.getTxtRestWorkOnOverMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtRestWorkOnOverMinute"><%= params.getName("Minutes") %></label>
			</td> -->
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("PrivateGoingOut") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtPrivateHour", "txtPrivateHour", vo.getTxtPrivateHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtPrivateHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtPrivateMinute", "txtPrivateMinute", vo.getTxtPrivateMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtPrivateMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Official","GoingOut") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtPublicHour", "txtPublicHour", vo.getTxtPublicHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtPublicHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtPublicMinute", "txtPublicMinute", vo.getTxtPublicMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtPublicMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
// 分単位休暇有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_MINUTELY_HOLIDAY)){
%>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("MinutelyHolidayA") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtMinutelyHolidayAHour", "txtMinutelyHolidayAHour", vo.getTxtMinutelyHolidayAHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtMinutelyHolidayAHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtMinutelyHolidayAMinute", "txtMinutelyHolidayAMinute", vo.getTxtMinutelyHolidayAMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtMinutelyHolidayAMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("MinutelyHolidayB") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtMinutelyHolidayBHour", "txtMinutelyHolidayBHour", vo.getTxtMinutelyHolidayBHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtMinutelyHolidayBHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtMinutelyHolidayBMinute", "txtMinutelyHolidayBMinute", vo.getTxtMinutelyHolidayBMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtMinutelyHolidayBMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
}
%>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("OvertimeWork","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblOverTime">
		<tr>
			<td class="TitleTd" id="tdOverTimeTitle"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("OvertimeWork","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeHour", "txtOverTimeHour", vo.getTxtOverTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeMinute", "txtOverTimeMinute", vo.getTxtOverTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Legal","Inside","OvertimeWork") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeInHour", "txtOverTimeInHour", vo.getTxtOverTimeInHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeInHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeInMinute", "txtOverTimeInMinute", vo.getTxtOverTimeInMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeInMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Legal","Outside","OvertimeWork") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeOutHour", "txtOverTimeOutHour", vo.getTxtOverTimeOutHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeOutHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeOutMinute", "txtOverTimeOutMinute", vo.getTxtOverTimeOutMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtOverTimeOutMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("OvertimeWork","Frequency") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesOvertime", "txtTimesOvertime", vo.getTxtTimesOvertime(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesOvertime"><%= params.getName("CountNum") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("No45","Time","Exceed","No60","Time","AndLess","OvertimeWork") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txt45HourOverTimeHour", "txt45HourOverTimeHour", vo.getTxt45HourOverTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txt45HourOverTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txt45HourOverTimeMinute", "txt45HourOverTimeMinute", vo.getTxt45HourOverTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txt45HourOverTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("No60","Time","Exceed","OvertimeWork") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txt60HourOverTimeHour", "txt60HourOverTimeHour", vo.getTxt60HourOverTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txt60HourOverTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txt60HourOverTimeMinute", "txt60HourOverTimeMinute", vo.getTxt60HourOverTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txt60HourOverTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Legal","WorkingHoliday","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkOnHolidayHour", "txtWorkOnHolidayHour", vo.getTxtWorkOnHolidayHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkOnHolidayHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkOnHolidayMinute", "txtWorkOnHolidayMinute", vo.getTxtWorkOnHolidayMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkOnHolidayMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed","WorkingHoliday","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkSpecificOnHolidayHour", "txtWorkSpecificOnHolidayHour", vo.getTxtWorkSpecificOnHolidayHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkSpecificOnHolidayHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkSpecificOnHolidayMinute", "txtWorkSpecificOnHolidayMinute", vo.getTxtWorkSpecificOnHolidayMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkSpecificOnHolidayMinute"><%= params.getName("Minutes") %></label>
			</td><!--
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed","Holiday","Legal","Outside","Work","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkOnOverHour", "txtWorkOnOverHour", vo.getTxtWorkOnOverHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkOnOverHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkOnOverMinute", "txtWorkOnOverMinute", vo.getTxtWorkOnOverMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWorkOnOverMinute"><%= params.getName("Minutes") %></label>
			</td> -->
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("WorkingHoliday","Frequency") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesWorkingHoliday", "txtTimesWorkingHoliday", vo.getTxtTimesWorkingHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtTimesWorkingHoliday"><%= params.getName("CountNum") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Midnight","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateNightHour", "txtLateNightHour", vo.getTxtLateNightHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateNightHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateNightMinute", "txtLateNightMinute", vo.getTxtLateNightMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for=txtLateNightMinute><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd" id="tdDecreaseTimeTitle"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Reduced","Target","Time") %></td>
			<td class="InputTd" id="tdDecreaseTimeInput">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtDecreaseTimeHour", "txtDecreaseTimeHour", vo.getTxtDecreaseTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtDecreaseTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtDecreaseTimeMinute", "txtDecreaseTimeMinute", vo.getTxtDecreaseTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtDecreaseTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%= params.getName("Tardiness","LeaveEarly","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblLateLeaveEarly">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLateDays"><%= params.getName("Tardiness","SumTotal","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateDays", "txtLateDays", vo.getTxtLateDays(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLateDeduction"><%= params.getName("Tardiness","Thirty","Minutes","Over","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateThirtyMinutesOrMore", "txtLateThirtyMinutesOrMore", vo.getTxtLateThirtyMinutesOrMore(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLateNoDeduction"><%= params.getName("Tardiness","Thirty","Minutes","LessThan","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateLessThanThirtyMinutes", "txtLateLessThanThirtyMinutes", vo.getTxtLateLessThanThirtyMinutes(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Tardiness","SumTotal","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateTimeHour", "txtLateTimeHour", vo.getTxtLateTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateTimeMinute", "txtLateTimeMinute", vo.getTxtLateTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Tardiness","Thirty","Minutes","Over","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateThirtyMinutesOrMoreTimeHour", "txtLateThirtyMinutesOrMoreTimeHour", vo.getTxtLateThirtyMinutesOrMoreTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateDeductionHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateThirtyMinutesOrMoreTimeMinute", "txtLateThirtyMinutesOrMoreTimeMinute", vo.getTxtLateThirtyMinutesOrMoreTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateDeductionMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Tardiness","Thirty","Minutes","LessThan","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateLessThanThirtyMinutesTimeHour", "txtLateLessThanThirtyMinutesTimeHour", vo.getTxtLateLessThanThirtyMinutesTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateNoDeductionHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateLessThanThirtyMinutesTimeMinute", "txtLateLessThanThirtyMinutesTimeMinute", vo.getTxtLateLessThanThirtyMinutesTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLateNoDeductionMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr><!--
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLate"><%= params.getName("Tardiness","Frequency") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesLate", "txtTimesLate", vo.getTxtTimesLate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="Blank" id="tdLateBlank" colspan="6"></td>
		</tr> -->
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLeaveEarlyDays"><%= params.getName("LeaveEarly","SumTotal","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyDays", "txtLeaveEarlyDays", vo.getTxtLeaveEarlyDays(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLeaveEarlyDeduction"><%= params.getName("LeaveEarly","Thirty","Minutes","Over","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMore", "txtLeaveEarlyThirtyMinutesOrMore", vo.getTxtLeaveEarlyThirtyMinutesOrMore(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLeaveEarlyNoDeduction"><%= params.getName("LeaveEarly","Thirty","Minutes","LessThan","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutes", "txtLeaveEarlyLessThanThirtyMinutes", vo.getTxtLeaveEarlyLessThanThirtyMinutes(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("LeaveEarly","SumTotal","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyTimeHour", "txtLeaveEarlyTimeHour", vo.getTxtLeaveEarlyTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyTimeMinute", "txtLeaveEarlyTimeMinute", vo.getTxtLeaveEarlyTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("LeaveEarly","Thirty","Minutes","Over","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMoreTimeHour", "txtLeaveEarlyThirtyMinutesOrMoreTimeHour", vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyDeductionHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMoreTimeMinute", "txtLeaveEarlyThirtyMinutesOrMoreTimeMinute", vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyDeductionMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("LeaveEarly","Thirty","Minutes","LessThan","Time") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutesTimeHour", "txtLeaveEarlyLessThanThirtyMinutesTimeHour", vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyNoDeductionHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutesTimeMinute", "txtLeaveEarlyLessThanThirtyMinutesTimeMinute", vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtLeaveEarlyNoDeductionMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr><!--
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLeaveEarly"><%= params.getName("LeaveEarly","Frequency") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesLeaveEarly", "txtTimesLeaveEarly", vo.getTxtTimesLeaveEarly(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="Blank" colspan="6"></td>
		</tr> -->
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Holiday","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblHoliday">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesHoliday"><%= params.getName("SumTotal","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesHoliday", "txtTimesHoliday", vo.getTxtTimesHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLegalHoliday"><%= params.getName("Legal","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesLegalHoliday", "txtTimesLegalHoliday", vo.getTxtTimesLegalHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesSpecificHoliday"><%= params.getName("Prescribed","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesSpecificHoliday", "txtTimesSpecificHoliday", vo.getTxtTimesSpecificHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesSubstitute"><%= params.getName("SumTotal","Transfer","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSubstitute", "txtTimesSubstitute", vo.getTxtTimesSubstitute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLegalHolidaySubstitute"><%= params.getName("Legal","Transfer","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLegalHolidaySubstitute", "txtTimesLegalHolidaySubstitute", vo.getTxtTimesLegalHolidaySubstitute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesSpecificHolidaySubstitute"><%= params.getName("Prescribed","Transfer","Holiday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecificHolidaySubstitute", "txtTimesSpecificHolidaySubstitute", vo.getTxtTimesSpecificHolidaySubstitute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%= params.getName("Vacation","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblVacation">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesPaidHoliday"><%= params.getName("Salaried","Vacation","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesPaidHoliday", "txtTimesPaidHoliday", vo.getTxtTimesPaidHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPaidholidayHour"><%= params.getName("Salaried","Vacation","Time") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtPaidholidayHour", "txtPaidholidayHour", vo.getTxtPaidholidayHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Time") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesStockHoliday"><%= params.getName("Stock","Vacation") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesStockHoliday", "txtTimesStockHoliday", vo.getTxtTimesStockHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
if (TimeUtility.isSubHolidayRequestValid(params)) {
%>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesCompensation"><%= params.getName("SumTotal","CompensatoryHoliday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesCompensation", "txtTimesCompensation", vo.getTxtTimesCompensation(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLegalCompensation"><%= params.getName("Legal","CompensatoryHoliday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLegalCompensation", "txtTimesLegalCompensation", vo.getTxtTimesLegalCompensation(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesSpecificCompensation"><%= params.getName("Prescribed","CompensatoryHoliday","Days") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecificCompensation", "txtTimesSpecificCompensation", vo.getTxtTimesSpecificCompensation(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd" id="tdLateNightTitle"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesLatelCompensation"><%= params.getName("Midnight","CompensatoryHoliday","Days") %></label></td>
			<td class="InputTd" id="tdLateNightInput">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLateCompensation", "txtTimesLateCompensation", vo.getTxtTimesLateCompensation(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLegalCompensationUnused"><%= params.getName("Legal","CompensatoryHoliday","Ram","Acquisition") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLegalCompensationUnused", "txtLegalCompensationUnused", vo.getTxtLegalCompensationUnused(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtSpecificCompensationUnused"><%= params.getName("Prescribed","CompensatoryHoliday","Ram","Acquisition") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtSpecificCompensationUnused", "txtSpecificCompensationUnused", vo.getTxtSpecificCompensationUnused(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtLateCompensationUnused"><%= params.getName("Midnight","CompensatoryHoliday","Ram","Acquisition") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLateCompensationUnused", "txtLateCompensationUnused", vo.getTxtLateCompensationUnused(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
}
%>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh"><%= params.getName("Specially","Vacation","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblSpecialHoliday">
<% for (int i = 0; i < vo.getAryTxtTimesSpecialLeave().length; i = i + 5) { %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesSpecialLeaveTitle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryTxtTimesSpecialLeaveTitle()[i]) %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecialLeave" + (i + 1), "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave()[i], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% if (i + 1 < vo.getAryTxtTimesSpecialLeave().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 1] %>><%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 1] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecialLeave" + (i + 2), "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave()[i + 1], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 2 < vo.getAryTxtTimesSpecialLeave().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 2] %>><%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 2] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecialLeave" + (i + 3), "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave()[i + 2], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 3 < vo.getAryTxtTimesSpecialLeave().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 3] %>><%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 3] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecialLeave" + (i + 4), "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave()[i + 3], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 4 < vo.getAryTxtTimesSpecialLeave().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 4] %>><%= vo.getAryTxtTimesSpecialLeaveTitle()[i + 4] %></label></td>
			<td class="LastTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecialLeave" + (i + 5), "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave()[i + 4], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
<%
if (vo.getAryTxtTimesSpecialLeave().length % 5 != 0) {
%>
			<td class="Blank" colspan="<%= (5 - vo.getAryTxtTimesSpecialLeave().length % 5) * 2 %>"></td>
<%
}
%>
		</tr>
<% } %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTotalSpecialHoliday"><%= params.getName("Specially","Vacation","SumTotal") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalSpecialHoliday", "txtTotalSpecialHoliday", vo.getTxtTotalSpecialHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="Blank" colspan="8"></td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh"><%= params.getName("Others","Vacation","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblOtherHoliday">
<% for (int i = 0; i < vo.getAryTxtTimesOtherVacation().length; i = i + 5) { %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesOtherVacationTitle()[i] %>><%= vo.getAryTxtTimesOtherVacationTitle()[i] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesOtherVacation" + (i + 1), "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation()[i], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% if (i + 1 < vo.getAryTxtTimesOtherVacation().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesOtherVacationTitle()[i+1] %>><%= vo.getAryTxtTimesOtherVacationTitle()[i+1] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesOtherVacation" + (i + 2), "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation()[i+1], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 2 < vo.getAryTxtTimesOtherVacation().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesOtherVacationTitle()[i+2] %>><%= vo.getAryTxtTimesOtherVacationTitle()[i+2] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesOtherVacation" + (i + 3), "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation()[i+2], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 3 < vo.getAryTxtTimesOtherVacation().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesOtherVacationTitle()[i+3] %>><%= vo.getAryTxtTimesOtherVacationTitle()[i+3] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesOtherVacation" + (i + 4), "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation()[i+3], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 4 < vo.getAryTxtTimesOtherVacation().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtTimesOtherVacationTitle()[i+4] %>><%= vo.getAryTxtTimesOtherVacationTitle()[i+4] %></label></td>
			<td class="LastTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesOtherVacation" + (i + 5), "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation()[i+4], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
<%
if (vo.getAryTxtTimesOtherVacation().length % 5 != 0) {
%>
			<td class="Blank" colspan="<%= (5 - vo.getAryTxtTimesOtherVacation().length % 5) * 2 %>"></td>
<%
}
%>
		</tr>
<% } %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTotalOtherHoliday"><%= params.getName("Others","Vacation","SumTotal") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalOtherHoliday", "txtTotalOtherHoliday", vo.getTxtTotalOtherHoliday(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="Blank" colspan="8"></td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh"><%= params.getName("Absence","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblAbsence">
<% for (int i = 0; i < vo.getAryTxtDeduction().length; i = i + 5) { %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtDeductionTitle()[i] %>><%= vo.getAryTxtDeductionTitle()[i] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtDeduction" + (i + 1), "aryTxtDeduction", vo.getAryTxtDeduction()[i], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% if (i + 1 < vo.getAryTxtDeduction().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtDeductionTitle()[i + 1] %>><%= vo.getAryTxtDeductionTitle()[i + 1] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtDeduction" + (i + 2), "aryTxtDeduction", vo.getAryTxtDeduction()[i+1], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 2 < vo.getAryTxtDeduction().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtDeductionTitle()[i + 2] %>><%= vo.getAryTxtDeductionTitle()[i + 2] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtDeduction" + (i + 3), "aryTxtDeduction", vo.getAryTxtDeduction()[i+2], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 3 < vo.getAryTxtDeduction().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtDeductionTitle()[i + 3] %>><%= vo.getAryTxtDeductionTitle()[i + 3] %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtDeduction" + (i + 4), "aryTxtDeduction", vo.getAryTxtDeduction()[i+3], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
	<% if (i + 4 < vo.getAryTxtDeduction().length) { %>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for=<%= vo.getAryTxtDeductionTitle()[i + 4] %>><%= vo.getAryTxtDeductionTitle()[i + 4] %></label></td>
			<td class="LastTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtDeduction" + (i + 5), "aryTxtDeduction", vo.getAryTxtDeduction()[i+4], vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
	<% } %>
<%
if (vo.getAryTxtDeduction().length % 5 != 0) {
%>
			<td class="Blank" colspan="<%= (5 - vo.getAryTxtDeduction().length % 5) * 2 %>"></td>
<%
}
%>
		</tr>
<% } %>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTotalDeduction"><%= params.getName("Absence","SumTotal") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalDeduction", "txtTotalDeduction", vo.getTxtTotalDeduction(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="Blank" colspan="8"></td>
		</tr>
	</table>
<!--
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh"><=params.getName("Allowance","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblAllowance">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance1"><=params.getName("Allowance","No1")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance1", "txtTimesAllowance1", vo.getTxtTimesAllowance1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance2"><=params.getName("Allowance","No2")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance2", "txtTimesAllowance2", vo.getTxtTimesAllowance2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance3"><=params.getName("Allowance","No3")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance3", "txtTimesAllowance3", vo.getTxtTimesAllowance3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance4"><=params.getName("Allowance","No4")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance4", "txtTimesAllowance4", vo.getTxtTimesAllowance4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance5"><=params.getName("Allowance","No5")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance5", "txtTimesAllowance5", vo.getTxtTimesAllowance5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance6"><=params.getName("Allowance","No6")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance6", "txtTimesAllowance6", vo.getTxtTimesAllowance6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance7"><=params.getName("Allowance","No7")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance7", "txtTimesAllowance7", vo.getTxtTimesAllowance7(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance8"><=params.getName("Allowance","No8")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance8", "txtTimesAllowance8", vo.getTxtTimesAllowance8(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance9"><=params.getName("Allowance","No9")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance9", "txtTimesAllowance9", vo.getTxtTimesAllowance9(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance10"><=params.getName("Allowance","No10")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance10", "txtTimesAllowance10", vo.getTxtTimesAllowance10(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtTimesAllowance"><=params.getName("Allowance","SumTotal")%></label></td>
			<td class="InputTd">
				<= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesAllowance", "txtTimesAllowance", vo.getTxtTimesAllowance1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<=params.getName("Day")%>
			</td>
			<td class="Blank" colspan="8"></td>
		</tr>
	</table>
-->
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Time","Outside","FrontParentheses","No60","Time","BackParentheses","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tbl">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Weekday","Time","Outside") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWeekDayOverTimeHour", "txtWeekDayOverTimeHour", vo.getTxtWeekDayOverTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWeekDayOverTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWeekDayOverTimeMinute", "txtWeekDayOverTimeMinute", vo.getTxtWeekDayOverTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtWeekDayOverTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed") %><%= params.getName("Holiday","Time","Outside")%></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtSpecificOverTimeHour", "txtSpecificOverTimeHour", vo.getTxtSpecificOverTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtSpecificOverTimeHour"><%= params.getName("Time") %></label>
				<%= HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtSpecificOverTimeMiunte", "txtSpecificOverTimeMiunte", vo.getTxtSpecificOverTimeMiunte(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<label for="txtSpecificOverTimeMiunte"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Substitute","Vacation") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesAlternative", "txtTimesAlternative", vo.getTxtTimesAlternative(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, '', null, '<%= TotalTimeCardAction.CMD_INSERT %>')"><%= params.getName("Insert") %></button>
	<button type="button" id="btnReset" class="Name4Button" onclick="submitRegist(event, '', null, '<%= TotalTimeCardAction.CMD_RE_SHOW %>')"><%= params.getName("Refresh") %></button>
	<button type="button" id="btnCutOffResultList" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('null', 'null', 'null' , 'null'), '<%= TotalTimeListAction.CMD_RE_SHOW %>');" ><%= params.getName("Total","List") %></button>
</div>
