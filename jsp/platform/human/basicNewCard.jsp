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
language="java"
pageEncoding="UTF-8"
buffer="256kb"
autoFlush="false"
errorPage="/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformNamingUtility"
import = "jp.mosp.platform.human.action.BasicNewCardAction"
import = "jp.mosp.platform.human.vo.BasicNewCardVo"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.constant.PlatformConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BasicNewCardVo vo = (BasicNewCardVo)params.getVo();
%>
<div class="List">
<table class="ListTable" id="tblNewCard">
	<tr>
		<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("ActivateDate") %></span>
		</td>
		<td class="InputTd" id="tdActivateDate">
			<input type="text" class="Number4RequiredTextBox" id="txtActivateYear" name="txtActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />&nbsp;<label for="txtActivateYear"><%= params.getName("Year") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />&nbsp;<label for="txtActivateMonth"><%= params.getName("Month") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtActivateDay" name="txtActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>" />&nbsp;<label for="txtActivateDay"><%= params.getName("Day") %></label>
			<button type="button" class="Name2Button" onclick="submitForm(event, 'tdActivateDate', null, '<%= BasicNewCardAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
		</td>
		<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEmployeeCode"><%= PlatformNamingUtility.employeeCode(params) %></label></span></td>
		<td class="InputTd" id="tdEmployeeCode">
			<input type="text" class="Code10RequiredTextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" />
<%
if (vo.isNeedNumberingButton()) {
%>
			<button type="button" class="Name4Button" onclick="submitTransfer(event, 'tdEmployeeCode', null, null, '<%= BasicNewCardAction.CMD_AUTO_NUMBERING %>')"><%= PlatformNamingUtility.autoNumbering(params) %></button>
<%
}
%>
		</td>
	</tr>
	<tr>
		<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtLastName"><%= params.getName("LastName") %></label><%= params.getName("Slash") %><label for="txtFirstName"><%= params.getName("FirstName") %></label></span></td>
		<td class="InputTd">
			<input type="text" class="Name10RequiredTextBox" id="txtLastName" name="txtLastName" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastName()) %>" />
			<input type="text" class="Name10TextBox" id="txtFirstName" name="txtFirstName" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstName()) %>" />
		</td>
		<td class="TitleTd"><span><label for="txtLastKana"><%= params.getName("LastName") %><%= params.getName("FrontParentheses") %><%= params.getName("Kana") %><%= params.getName("BackParentheses") %></label><%= params.getName("Slash") %><label for="txtFirstKana"><%= params.getName("FirstName") %><%= params.getName("FrontParentheses") %><%= params.getName("Kana") %><%= params.getName("BackParentheses") %></label></span></td>
		<td class="InputTd">
			<input type="text" class="Kana10TextBox" id="txtLastKana" name="txtLastKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastKana()) %>" />
			<input type="text" class="Kana10TextBox" id="txtFirstKana" name="txtFirstKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstKana()) %>" />
		</td>
	</tr>
	<tr>
		<td class="TitleTd"><span><label for="pltSectionName"><%= params.getName("Section") %></label></span></td>
		<td class="InputTd"><select class="Name15PullDown" id="pltSectionName" name="pltSectionName">
			<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSectionName()) %>
		</select></td>
		<td class="TitleTd"><span><label for="pltPositionName"><%= params.getName("Position") %></label></span></td>
		<td class="InputTd"><select class="Name15PullDown" id="pltPositionName" name="pltPositionName">
			<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPositionName()) %>
		</select></td>
	</tr>
	<tr>
		<td class="TitleTd"><span><label for="pltEmploymentName"><%= params.getName("EmploymentContract") %></label></span></td>
		<td class="InputTd"><select class="Name15PullDown" id="pltEmploymentName" name="pltEmploymentName">
			<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmploymentName()) %>
		</select></td>
		<td class="TitleTd"><span><label for="pltWorkPlaceName"><%= params.getName("WorkPlace") %></label></span></td>
		<td class="InputTd"><select class="Name15PullDown" id="pltWorkPlaceName" name="pltWorkPlaceName">
			<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlaceName(), vo.getPltWorkPlaceName()) %>
		</select></td>
	</tr>
<%
if (vo.getNeedPost()) {
%>		
	<tr>
		<td class="TitleTd"><span><label for="pltPostName"><%= params.getName("Post") %></label></span></td>
		<td class="InputTd">
			<select class="Name15PullDown" id="pltPostName" name="pltPostName">
				<%= HtmlUtility.getSelectOption(vo.getAryPltPostName(), vo.getPltPostName()) %>
			</select>
		</td>
		<td class="Blank" colspan="2"></td>
	</tr>
<%
}
for (String extraJsp : vo.getExtraJspList()) {
%>
	<jsp:include page="<%= extraJsp %>" flush="false" />
<%
}
%>
	<tr>
		<td class="TitleTd"><span><label for="txtUserId"><%= params.getName("User") %><%= params.getName("Id") %></label></span></td>
		<td class="InputTd" id="tdMessage" colspan="3">
			<input type="text" class="UserId50TextBox" id="txtUserId" name="txtUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtUserId()) %>" />
			<%= params.getName("SignStar") %><%= params.getName("Account") %><%= params.getName("InputMaking") %>
		</td>
	</tr>
	<tr>
		<td class="TitleTd"><span><%= params.getName("Joined") %><%= params.getName("Day") %></span></td>
		<td class="InputTd" id="tdMessage" colspan="3">
			<input type="text" class="Number4TextBox" id="txtEntranceYear" name="txtEntranceYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceYear()) %>" />&nbsp;<label for="txtEntranceYear"><%= params.getName("Year") %></label>
			<input type="text" class="Number2TextBox" id="txtEntranceMonth" name="txtEntranceMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceMonth()) %>" />&nbsp;<label for="txtEntranceMonth"><%= params.getName("Month") %></label>
			<input type="text" class="Number2TextBox" id="txtEntranceDay" name="txtEntranceDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceDay()) %>" />&nbsp;<label for="txtEntranceDay"><%= params.getName("Day") %></label>
			<%= params.getName("SignStar") %><%= params.getName("Joined") %><%= params.getName("Information") %><%= params.getName("InputMaking") %>
		</td>
	</tr>
</table>
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblNewCard', null, '<%= BasicNewCardAction.CMD_INSERT %>')"><%= params.getName("Insert") %></button>
	<button type="button" id="btnHumenInfo" class="Name4Button"
			onclick="submitTransfer(event, 'tblNewCard', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%=HumanInfoAction.class.getName() %>'), '<%= BasicNewCardAction.CMD_TRANSFER %>');"
			>
			<%= params.getName("Information") + params.getName("List") %>
	</button>
</div>
