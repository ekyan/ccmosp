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
language = "java"
pageEncoding = "UTF-8"
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.human.base.PlatformHumanVo"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PlatformHumanVo vo = (PlatformHumanVo)params.getVo();
String modeHumanLayout = vo.getModeHumanLayout();
if (modeHumanLayout == null) {
	return;
}
%>
<div class="Search" id="divHumanSearch">
<%
if (modeHumanLayout.equals(PlatformHumanConst.MODE_HUMAN_SHOW_ALL) || modeHumanLayout.equals(PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE)) {
%>
	<table class="EmployeeCodeRollTable">
		<tr>
			<td class="RollTd" id="tdFormerCode"><%= HtmlUtility.escapeHTML(vo.getLblBackEmployeeCode()) %></td>
			<td class="RollTd" id="tdFormerButton">
<%
if (vo.getLblBackEmployeeCode() != null && vo.getLblBackEmployeeCode().isEmpty() == false) {
%>
				<a onclick="submitTransfer(event, null, null, new Array('<%= PlatformHumanConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= PlatformHumanConst.SEARCH_BACK %>'), '<%= HtmlUtility.escapeHTML(vo.getCmdSaerch()) %>');">&lt;&lt;</a>
<%
}
%>
			</td>
			<td class="RollTd">&nbsp;<%= params.getName("Employee") %><%= params.getName("Code") %>&nbsp;</td>
			<td class="RollTd" id="tdNextButton">
<%
if (vo.getLblNextEmployeeCode() != null && vo.getLblNextEmployeeCode().isEmpty() == false) {
%>
				<a onclick="submitTransfer(event, null, null, new Array('<%= PlatformHumanConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= PlatformHumanConst.SEARCH_NEXT%>'), '<%= HtmlUtility.escapeHTML(vo.getCmdSaerch()) %>');">&gt;&gt;</a>
<%
}
%>
			</td>
			<td class="RollTd" id="tdNextCode"><%= HtmlUtility.escapeHTML(vo.getLblNextEmployeeCode()) %></td>
		</tr>
	</table>
	<table class="EmployeeCodeSearchTable">
		<tr>
			<td class="EmployeeCodeLabelTd" id = "EmployeeCodeLabelTd">
				<label for="txtSearchEmployeeCode"><%= params.getName("Employee") %><%= params.getName("Code") %></label><%= params.getName("Colon") %>
				<input type="text" class="Code10RequiredTextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>" />&nbsp;
				<button type="button" id="btnHumenInfo" class="Name2Button"
					onclick="submitTransfer(event, null, checkCommonSearchEmployee, new Array('<%= PlatformHumanConst.PRM_TRANSFER_SEARCH_MODE %>','<%= PlatformHumanConst.SEARCH_EMPLOYEE_CODE %>'), '<%= HtmlUtility.escapeHTML(vo.getCmdSaerch()) %>');">
					<%= params.getName("Search") %>
				</button>
			</td>
		</tr>
	</table>
<%
}
%>
</div>
<div class="Search" id="divHumanLabel">
	<table class="EmployeeCodeLabelTable" id="tblEmployeeCodeLabel">
		<tr>
			<td class="EmployeeCodeTd">
				<%= params.getName("Employee") %><%= params.getName("Code") %><%= params.getName("Colon") %><%= HtmlUtility.escapeHTML(vo.getEmployeeCode()) %>
			</td>
			<td class="EmployeeNameTd">
				<%= params.getName("Employee") %><%= params.getName("FirstName") %><%= params.getName("Colon") %><%= HtmlUtility.escapeHTML(vo.getLblEmployeeName()) %>
			</td>
<%
if (modeHumanLayout.equals(PlatformHumanConst.MODE_HUMAN_SHOW_ALL)) {
%>
			<td class="NowDateTd" id="tdNowDate">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />&nbsp;<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />&nbsp;<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />&nbsp;<label for="txtSearchActivateDay"><%= params.getName("Day") %></label><%= params.getName("Of") %><%= params.getName("Information") %>&nbsp;
				<button type="button" id="btnHumenInfo" class="Name3Button"
					onclick="submitTransfer(event, null, checkCommonSearchActivateDate, new Array('<%= PlatformHumanConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= PlatformHumanConst.SEARCH_TARGET_DATE %>'),'<%= HtmlUtility.escapeHTML(vo.getCmdSaerch()) %>');">
					<%= params.getName("Refresh") %>
				</button>
			</td>
<%
}
%>
		</tr>
	</table>
</div>
