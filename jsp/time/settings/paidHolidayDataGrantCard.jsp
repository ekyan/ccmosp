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
import = "jp.mosp.time.settings.action.PaidHolidayDataGrantCardAction"
import = "jp.mosp.time.settings.action.PaidHolidayDataGrantListAction"
import = "jp.mosp.time.settings.vo.PaidHolidayDataGrantCardVo"
import = "jp.mosp.time.settings.vo.PaidHolidayDataGrantListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayDataGrantCardVo vo = (PaidHolidayDataGrantCardVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<%--
<div class="FixList">
	<table class="ListTable">
		<tbody>
			<tr>
				<th class="ListTableTh" colspan="6">
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
%>
					<span class="TitleTh"><%= params.getName("Giving", "Days", "Correction", "FrontWithCornerParentheses", "New", "Insert", "BackWithCornerParentheses") %></span>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
					<span class="TitleTh"><%= params.getName("Giving", "Days", "Correction", "FrontWithCornerParentheses", "History", "Edit", "BackWithCornerParentheses") %></span>
<%
}
%>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Giving", "Day") %></td>
				<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblGrantDate()) %></td>
				<td class="TitleTd"><%= params.getName("TimeLimit", "Day") %></td>
				<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblExpirationDate()) %></td>
				<td class="TitleTd">
					<span class="RequiredLabel">*&nbsp;</span><label for="txtGrantDays"><%= params.getName("Giving", "Days") %></label>
				</td>
				<td class="InputTd">
					<input type="text" name="txtGrantDays" id="txtGrantDays" class="Number2RequiredTextBox" value="<%= HtmlUtility.escapeHTML(vo.getTxtGrantDays()) %>">
					<%= params.getName("Day", "FrontParentheses", "Correction", "Ahead", "Colon") %><%= HtmlUtility.escapeHTML(vo.getLblGrantDays()) %><%= params.getName("BackParentheses") %>
				</td>
			</tr>
		</tbody>
	</table>
</div>
--%>
<div class="FixList">
	<table class="ListTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="5">
					<span class="TitleTh"><%= params.getName("PaidVacation", "Giving", "Days", "Correction") %></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="TitleTd"><%= params.getName("Giving", "Day") %></th>
				<th class="TitleTd"><%= params.getName("ActivateDate") %></th>
				<th class="TitleTd"><%= params.getName("TimeLimit", "Day") %></th>
				<th class="TitleTd"><%= params.getName("Giving", "Days", "FrontParentheses", "Correction", "Ahead", "BackParentheses") %></th>
				<th class="TitleTd">
					<span class="RequiredLabel">*&nbsp;</span><label for="aryTxtGrantDays<%= vo.getAryLblGrantDate().length - 1 %>"><%= params.getName("Giving", "Days") %></label>
				</th>
			</tr>
<%
for (int i = 0; i < vo.getAryLblGrantDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblGrantDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblExpirationDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblGrantDays()[i]) %></td>
				<td class="ListSelectTd">
					<input type="text" name="aryTxtGrantDays" id="aryTxtGrantDays<%= i %>" class="Number2RequiredTextBox" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtGrantDays()[i]) %>">
					<%= params.getName("Day") %>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" onclick="submitRegist(event, '', null, '<%= PaidHolidayDataGrantCardAction.CMD_REGIST %>');"><%= params.getName("Insert") %></button>
	<button type="button" class="Name7Button" onclick="submitTransfer(event, null, null, null, '<%= PaidHolidayDataGrantListAction.CMD_RE_SHOW %>');"><%= params.getName(PaidHolidayDataGrantListVo.class.getName(),"To") %></button>
</div>
