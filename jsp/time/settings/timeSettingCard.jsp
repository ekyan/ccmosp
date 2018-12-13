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
import = "jp.mosp.time.settings.action.TimeSettingCardAction"
import = "jp.mosp.time.settings.action.TimeSettingListAction"
import = "jp.mosp.time.settings.vo.TimeSettingCardVo"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeSettingCardVo vo = (TimeSettingCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<jsp:include page="<%= TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdActivateDate"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd" id="tdNoBorderBottom">
				<div id="divSearchCutoffDate">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
					<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
					<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
					<label for="txtEditActivateDay"><%= params.getName("Day") %></label>&nbsp;
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'divSearchCutoffDate', null, '<%= TimeSettingCardAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</div>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtSettingCode"><%= params.getName("WorkManage", "Set", "Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtSettingCode" name="txtSettingCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSettingCode()) %>" /></td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtSettingName"><%= params.getName("WorkManage", "Set", "Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtSettingName" name="txtSettingName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSettingName()) %>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CutoffDate", "Abbreviation") %></td>
			<td class="InputTd" id="tdNoBorderTop">
				<select class="Name13PullDown" id="pltCutoffDate" name="pltCutoffDate">
					<%= HtmlUtility.getSelectOption(vo.getAryPltCutoffDate(), vo.getPltCutoffDate()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtSettingAbbr"><%= params.getName("WorkManage", "Set", "Abbreviation") %></label></td>
			<td class="InputTd"><input type="text" class="Byte6RequiredTextBox" id="txtSettingAbbr" name="txtSettingAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSettingAbbr()) %>"/></td>
			<td class="TitleTd"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></td>
			<td class="InputTd">
				<select id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("WorkManage", "Set") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("WorkManage", "Management", "Target") %></td>
			<td class="InputTd">
				<select id="pltTimeManagement" name="pltTimeManagement">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltTimeManagement(), false) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Prescribed", "Labor", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtGeneralWorkTimeHour" name="txtGeneralWorkTimeHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtGeneralWorkTimeHour()) %>" />&nbsp;<label for="txtGeneralWorkTimeHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtGeneralWorkTimeMinute" name="txtGeneralWorkTimeMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtGeneralWorkTimeMinute()) %>" />&nbsp;<label for="txtGeneralWorkTimeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("StartDayTime") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtStartDayHour"name="txtStartDayHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtStartDayHour()) %>" />&nbsp;<label for="txtStartDayHour"><%= params.getName("Hour") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtStartDayMinute" name="txtStartDayMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtStartDayMinute()) %>" />&nbsp;<label for="txtStartDayMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Tardiness", "LeaveEarly", "Limit", "Time", "FrontParentheses", "HalfDay", "BackParentheses") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyHalfHour" name="txtLateEarlyHalfHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLateEarlyHalfHour()) %>" />&nbsp;<label for="txtLateEarlyHalfHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyHalfMinute" name="txtLateEarlyHalfMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLateEarlyHalfMinute()) %>" />&nbsp;<label for="txtLateEarlyHalfMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><%= params.getName("Work", "Ahead", "OvertimeWork") %></td>
			<td class="InputTd">
				<select id="pltBeforeOverTime" name="pltBeforeOverTime">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltBeforeOverTime(), false) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Prescribed", "Holiday", "Handling") %></td>
			<td class="InputTd">
				<select id="pltSpecificHoliday" name="pltSpecificHoliday">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_SPECIFIC_HOLIDAY, vo.getPltSpecificHoliday(), false) %>
				</select>
			</td>
<%--
			<td class="TitleTd" id=""><%= params.getName("StartYear") %></td>
			<td class="InputTd" id="">
				<select id="pltStartYear" name="pltStartYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltStartYear(), vo.getPltStartYear()) %>
				</select>
				<%= params.getName("Month") %>
			</td>
			<td class="TitleTd" id=""><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Tardiness") %><%= params.getName("LeaveEarly") %><%= params.getName("Limit") %><%= params.getName("Time") %><%= params.getName("FrontParentheses") %><%= params.getName("AllDays") %><%= params.getName("BackParentheses") %></td>
			<td class="InputTd" id="">
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyFullHour" name="txtLateEarlyFullHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLateEarlyFullHour()) %>" />&nbsp;<label for="txtLateEarlyFullHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyFullMinute" name="txtLateEarlyFullMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLateEarlyFullMinute()) %>" />&nbsp;<label for="txtLateEarlyFullMinute"><%= params.getName("Minutes") %></label>
			</td>
--%>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ClosedVibration", "Acquisition", "TimeLimit", "FrontParentheses", "WorkingHoliday", "Ahead", "BackParentheses") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtTransferAheadLimitMonth" name="txtTransferAheadLimitMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtTransferAheadLimitMonth()) %>" />&nbsp;<label for="txtTransferAheadLimitMonth"><%= params.getName("Months") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtTransferAheadLimitDate" name="txtTransferAheadLimitDate" value="<%= HtmlUtility.escapeHTML(vo.getTxtTransferAheadLimitDate()) %>" />&nbsp;<label for="txtTransferAheadLimitDate"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ClosedVibration", "Acquisition", "TimeLimit", "FrontParentheses", "WorkingHoliday", "Later", "BackParentheses") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtTransferLaterLimitMonth" name="txtTransferLaterLimitMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtTransferLaterLimitMonth()) %>" />&nbsp;<label for="txtTransferLaterLimitMonth"><%= params.getName("Months") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtTransferLaterLimitDate" name="txtTransferLaterLimitDate" value="<%= HtmlUtility.escapeHTML(vo.getTxtTransferLaterLimitDate()) %>" />&nbsp;<label for="txtTransferLaterLimitDate"><%= params.getName("Day") %></label>
			</td>
<%
if (TimeUtility.isSubHolidayRequestValid(params)) {
%>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("CompensatoryHoliday", "Acquisition", "TimeLimit") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayLimitMonth" name="txtSubHolidayLimitMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayLimitMonth()) %>" />&nbsp;<label for="txtSubHolidayLimitMonth"><%= params.getName("Months") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayLimitDate" name="txtSubHolidayLimitDate" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayLimitDate()) %>" />&nbsp;<label for="txtSubHolidayLimitDate"><%= params.getName("Day") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("CompensatoryHoliday", "Acquisition", "Norm", "Time", "FrontParentheses", "HalfTime", "BackParentheses") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayHalfNormHour" name="txtSubHolidayHalfNormHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayHalfNormHour()) %>" />&nbsp;<label for="txtSubHolidayHalfNormHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayHalfNormMinute" name="txtSubHolidayHalfNormMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayHalfNormMinute()) %>" />&nbsp;<label for="txtSubHolidayHalfNormMinute"><%= params.getName("Minutes", "Over") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("CompensatoryHoliday", "Acquisition", "Norm", "Time", "FrontParentheses", "AllTime", "BackParentheses") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayAllNormHour" name="txtSubHolidayAllNormHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayAllNormHour()) %>" />&nbsp;<label for="txtSubHolidayAllNormHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayAllNormMinute" name="txtSubHolidayAllNormMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtSubHolidayAllNormMinute()) %>" />&nbsp;<label for="txtSubHolidayAllNormMinute"><%= params.getName("Minutes", "Over") %></label>
			</td>
<%
}
%>
			<td class="Blank" id="tdTimeStttingBlank" colspan="2"></td>
<%--
			<td class="TitleTd" id=""><%= params.getName("HalfTime") %><%= params.getName("Exchange") %><%= params.getName("Acquisition") %><%= params.getName("FrontParentheses") %><%= params.getName("ClosedVibration") %><%= params.getName("BackParentheses") %></td>
			<td class="InputTd" id="tdExchange" colspan="3">
				<select class="Name25PullDown" id="pltTransferExchange" name="pltTransferExchange">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_HALF_TIME_EXCHANGE, vo.getPltTransferExchange(), false) %>
				</select>
			</td>
--%>
<%--
			<td class="TitleTd" id=""><%= params.getName("HalfTime") %><%= params.getName("Exchange") %><%= params.getName("Acquisition") %><%= params.getName("FrontParentheses") %><%= params.getName("CompensatoryHoliday") %><%= params.getName("BackParentheses") %></td>
			<td class="InputTd" id="tdExchange" colspan="3">
				<select class="Name25PullDown" id="pltSubHolidayExchange" name="pltSubHolidayExchange">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_HALF_TIME_EXCHANGE, vo.getPltSubHolidayExchange(), false) %>
				</select>
			</td>
--%>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Portal", "Attendance", "Button", "Display") %></td>
			<td class="InputTd">
				<select id="pltPortalTimeButtons" name="pltPortalTimeButtons">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PORTAL_TIME_BUTTONS, vo.getPltPortalTimeButtons(), false) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Portal", "RestTime", "Button", "Display") %></td>
			<td class="InputTd">
				<select id="pltPortalRestButtons" name="pltPortalRestButtons">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PORTAL_REST_BUTTONS, vo.getPltPortalRestButtons(), false) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Work", "Schedule", "Time", "Display") %></td>
			<td class="InputTd">
				<select id="pltUseScheduledTime" name="pltUseScheduledTime">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUseScheduledTime(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Time", "Rounding", "Set") %></span>
				<a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyStart"><%= params.getName("Day", "GoingWork", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyStart" name="txtRoundDailyStart" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyStart()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyStart" name="pltRoundDailyStart">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyStart()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyEnd"><%= params.getName("Day", "RetireOffice", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyEnd" name="txtRoundDailyEnd" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyEnd()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyEnd" name="pltRoundDailyEnd">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyEnd()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyWork"><%= params.getName("Day", "Work", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyWork" name="txtRoundDailyWork" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyWork()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyWork" name="pltRoundDailyWork">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyWork()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyRestStart"><%= params.getName("Day", "RestTime", "Into", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestStart" name="txtRoundDailyRestStart" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestStart()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyRestStart" name="pltRoundDailyRestStart">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestStart()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyRestEnd"><%= params.getName("Day", "RestTime", "Return", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestEnd" name="txtRoundDailyRestEnd" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestEnd()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyRestEnd" name="pltRoundDailyRestEnd">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestEnd()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyRestTime"><%= params.getName("Day", "RestTime", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestTime" name="txtRoundDailyRestTime" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestTime()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyRestTime" name="pltRoundDailyRestTime">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestTime()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyPublicIn"><%= params.getName("Day", "Official", "GoingOut", "Into", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPublicIn" name="txtRoundDailyPublicIn" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyPublicIn()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyPublicIn" name="pltRoundDailyPublicIn">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPublicIn()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyPublicOut"><%= params.getName("Day", "Official", "GoingOut", "Return", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPublicOut" name="txtRoundDailyPublicOut" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyPublicOut()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyPublicOut" name="pltRoundDailyPublicOut">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPublicOut()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyLate"><%= params.getName("Day", "Tardiness", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyLate" name="txtRoundDailyLate" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyLate()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyLate" name="pltRoundDailyLate">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyLate()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyLeaveEaly"><%= params.getName("Day", "LeaveEarly", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyLeaveEarly" name="txtRoundDailyLeaveEarly" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyLeaveEarly()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyLeaveEaly" name="pltRoundDailyLeaveEaly">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyLeaveEaly()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyPrivateIn"><%= params.getName("Day", "Private", "GoingOut", "Into", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPrivateIn" name="txtRoundDailyPrivateIn" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyPrivateIn()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyPrivateIn" name="pltRoundDailyPrivateIn">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPrivateIn()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyPrivateOut"><%= params.getName("Day", "Private", "GoingOut", "Return", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPrivateOut" name="txtRoundDailyPrivateOut" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyPrivateOut()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyPrivateOut" name="pltRoundDailyPrivateOut">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPrivateOut()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyDecreaseTime"><%= params.getName("Day", "Reduced", "Target", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyDecreaseTime" name="txtRoundDailyDecreaseTime" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyDecreaseTime()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyDecreaseTime" name="pltRoundDailyDecreaseTime">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyDecreaseTime()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundDailyShortUnpaid"><%= params.getName("Day", "UnpaidShortTime", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyShortUnpaid" name="txtRoundDailyShortUnpaid" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundDailyShortUnpaid()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundDailyShortUnpaid" name="pltRoundDailyShortUnpaid">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyShortUnpaid()) %>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyWork"><%= params.getName("Month", "Work", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyWork" name="txtRoundMonthlyWork" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyWork()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyWork" name="pltRoundMonthlyWork">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyWork()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyRest"><%= params.getName("Month", "RestTime", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyRest" name="txtRoundMonthlyRest" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyRest()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyRest" name="pltRoundMonthlyRest">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyRest()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyPublic"><%= params.getName("Month", "Official", "GoingOut", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyPublic" name="txtRoundMonthlyPublic" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyPublic()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyPublic" name="pltRoundMonthlyPublic">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyPublic()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyLate"><%= params.getName("Month", "Tardiness", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyLate" name="txtRoundMonthlyLate" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyLate()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyLate" name="pltRoundMonthlyLate">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyLate()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyLeaveEarly"><%= params.getName("Month", "LeaveEarly", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyLeaveEarly" name="txtRoundMonthlyLeaveEarly" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyLeaveEarly()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyLeaveEarly" name="pltRoundMonthlyLeaveEarly">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyLeaveEarly()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyPrivate"><%= params.getName("Month", "Private", "GoingOut", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyPrivate" name="txtRoundMonthlyPrivate" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyPrivate()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyPrivate" name="pltRoundMonthlyPrivate">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyPrivate()) %>
				</select>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyDecreaseTime"><%= params.getName("Month", "Reduced", "Target", "Time", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyDecreaseTime" name="txtRoundMonthlyDecreaseTime" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyDecreaseTime()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyDecreaseTime" name="pltRoundMonthlyDecreaseTime">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyDecreaseTime()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtRoundMonthlyShortUnpaid"><%= params.getName("Month", "UnpaidShortTime", "Rounding") %></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyShortUnpaid" name="txtRoundMonthlyShortUnpaid" value="<%= HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyShortUnpaid()) %>" />&nbsp;<%= params.getName("Minutes", "Unit") %>&nbsp;
				<select id="pltRoundMonthlyShortUnpaid" name="pltRoundMonthlyShortUnpaid">
					<%= HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyShortUnpaid()) %>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="5">
				<span class="TitleTh"><%= params.getName("Limit", "Norm") %></span>
				<span class="TableLabelSpan"><%= params.getName("SignStar", "TimeSettingCardMessage") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CountingOfTheWeek", "DayOfTheWeek") %></td>
			<td class="InputTd">
				<select id="pltStartWeek" name="pltStartWeek">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_DAY_OF_THE_WEEK, vo.getPltStartWeek(), false) %>
				</select>&nbsp;<%= params.getName("DayOfTheWeek") %>
			</td>
			<td class="Blank" colspan="3">
		</tr>
		<tr>
			<td class="TitleTd"></td><td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("No1", "Week") %></td><td class="TitleTd"><%= params.getName("No2", "Week") %></td>
			<td class="TitleTd"><%= params.getName("No4", "Week") %></td><td class="Blank" rowspan="4"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Limit", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtLimit1WeekHour" name="txtLimit1WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1WeekHour()) %>" />&nbsp;<label for="txtLimit1WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtLimit1WeekMinute" name="txtLimit1WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1WeekMinute()) %>" />&nbsp;<label for="txtLimit1WeekMinute"><%= params.getName("Minutes") %></label>&nbsp;<%= params.getName("SignStar") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtLimit2WeekHour" name="txtLimit2WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit2WeekHour()) %>" disabled />&nbsp;<label for="txtLimit2WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtLimit2WeekMinute" name="txtLimit2WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit2WeekMinute()) %>" disabled />&nbsp;<label for="txtLimit2WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtLimit4WeekHour" name="txtLimit4WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit4WeekHour()) %>" disabled />&nbsp;<label for="txtLimit4WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtLimit4WeekMinute" name="txtLimit4WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit4WeekMinute()) %>" disabled />&nbsp;<label for="txtLimit4WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Caution", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtAttention1WeekHour" name="txtAttention1WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1WeekHour()) %>" disabled />&nbsp;<label for="txtAttention1WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention1WeekMinute" name="txtAttention1WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1WeekMinute()) %>" disabled />&nbsp;<label for="txtAttention1WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtAttention2WeekHour" name="txtAttention2WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention2WeekHour()) %>" disabled />&nbsp;<label for="txtAttention2WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention2WeekMinute" name="txtAttention2WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention2WeekMinute()) %>" disabled />&nbsp;<label for="txtAttention2WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtAttention4WeekHour" name="txtAttention4WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention4WeekHour()) %>" disabled />&nbsp;<label for="txtAttention4WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention4WeekMinute" name="txtAttention4WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention4WeekMinute()) %>" disabled />&nbsp;<label for="txtAttention4WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Warning", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWarning1WeekHour" name="txtWarning1WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1WeekHour()) %>" disabled />&nbsp;<label for="txtWarning1WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning1WeekMinute" name="txtWarning1WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1WeekMinute()) %>" disabled />&nbsp;<label for="txtWarning1WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWarning2WeekHour" name="txtWarning2WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning2WeekHour()) %>" disabled />&nbsp;<label for="txtWarning2WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning2WeekMinute" name="txtWarning2WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning2WeekMinute()) %>" disabled />&nbsp;<label for="txtWarning2WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWarning4WeekHour" name="txtWarning4WeekHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning4WeekHour()) %>" disabled />&nbsp;<label for="txtWarning4WeekHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning4WeekMinute" name="txtWarning4WeekMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning4WeekMinute()) %>" disabled />&nbsp;<label for="txtWarning4WeekMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"></td><td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("No1", "Months") %></td><td class="TitleTd"><%= params.getName("No2", "Months") %></td><td class="TitleTd"><%= params.getName("No3", "Months") %></td><td class="TitleTd"><%= params.getName("No1", "Years") %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Limit", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtLimit1MonthHour" name="txtLimit1MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1MonthHour()) %>" />&nbsp;<label for="txtLimit1MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtLimit1MonthMinute" name="txtLimit1MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1MonthMinute()) %>" />&nbsp;<label for="txtLimit1MonthMinute"><%= params.getName("Minutes") %></label>&nbsp;<%= params.getName("SignStar") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtLimit2MonthHour" name="txtLimit2MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit2MonthHour()) %>" disabled />&nbsp;<label for="txtLimit2MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtLimit2MonthMinute" name="txtLimit2MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit2MonthMinute()) %>" disabled />&nbsp;<label for="txtLimit2MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtLimit3MonthHour" name="txtLimit3MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit3MonthHour()) %>" disabled />&nbsp;<label for="txtLimit3MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtLimit3MonthMinute" name="txtLimit3MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit3MonthMinute()) %>" disabled />&nbsp;<label for="txtLimit3MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtLimit1YearHour" name="txtLimit1YearHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1YearHour()) %>" disabled />&nbsp;<label for="txtLimit1YearHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtLimit1YearMinute" name="txtLimit1YearMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtLimit1YearMinute()) %>" disabled />&nbsp;<label for="txtLimit1YearMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Caution", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtAttention1MonthHour" name="txtAttention1MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1MonthHour()) %>" />&nbsp;<label for="txtAttention1MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtAttention1MonthMinute" name="txtAttention1MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1MonthMinute()) %>"/>&nbsp;<label for="txtAttention1MonthMinute"><%= params.getName("Minutes") %></label>&nbsp;<%= params.getName("SignStar") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtAttention2MonthHour" name="txtAttention2MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention2MonthHour()) %>" disabled />&nbsp;<label for="txtAttention2MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention2MonthMinute" name="txtAttention2MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention2MonthMinute()) %>" disabled />&nbsp;<label for="txtAttention2MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtAttention3MonthHour" name="txtAttention3MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention3MonthHour()) %>" disabled />&nbsp;<label for="txtAttention3MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention3MonthMinute" name="txtAttention3MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention3MonthMinute()) %>" disabled />&nbsp;<label for="txtAttention3MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtAttention1YearHour" name="txtAttention1YearHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1YearHour()) %>" disabled />&nbsp;<label for="txtAttention1YearHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtAttention1YearMinute" name="txtAttention1YearMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtAttention1YearMinute()) %>" disabled />&nbsp;<label for="txtAttention1YearMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Time", "Outside", "Warning", "Time") %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtWarning1MonthHour" name="txtWarning1MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1MonthHour()) %>" />&nbsp;<label for="txtWarning1MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtWarning1MonthMinute" name="txtWarning1MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1MonthMinute()) %>" />&nbsp;<label for="txtWarning1MonthMinute"><%= params.getName("Minutes") %></label>&nbsp;<%= params.getName("SignStar") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWarning2MonthHour" name="txtWarning2MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning2MonthHour()) %>" disabled />&nbsp;<label for="txtWarning2MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning2MonthMinute" name="txtWarning2MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning2MonthMinute()) %>" disabled />&nbsp;<label for="txtWarning2MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtWarning3MonthHour" name="txtWarning3MonthHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning3MonthHour()) %>" disabled />&nbsp;<label for="txtWarning3MonthHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning3MonthMinute" name="txtWarning3MonthMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning3MonthMinute()) %>" disabled />&nbsp;<label for="txtWarning3MonthMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtWarning1YearHour" name="txtWarning1YearHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1YearHour()) %>" disabled />&nbsp;<label for="txtWarning1YearHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2TextBox" id="txtWarning1YearMinute" name="txtWarning1YearMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWarning1YearMinute()) %>" disabled />&nbsp;<label for="txtWarning1YearMinute"><%= params.getName("Minutes") %></label>
			</td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= TimeSettingCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" class="Name6Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= TimeSettingCardAction.CMD_DELETE %>')"><%= params.getName("History", "Delete") %></button>
<%
}
%>
	<button type="button" class="Name6Button" id="btnTimeSettingList" name="btnTimeSettingList" onclick="submitTransfer(event, null, null, null, '<%= TimeSettingListAction.CMD_RE_SHOW %>')"><%= params.getName("WorkManage", "Set", "List") %></button>
</div>
