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
import = "jp.mosp.platform.file.action.ExportCardAction"
import = "jp.mosp.platform.file.action.HumanExportListAction"
import = "jp.mosp.platform.file.base.ExportListAction"
import = "jp.mosp.platform.file.vo.HumanExportListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanExportListVo vo = (HumanExportListVo)params.getVo();
%>
<jsp:include page="<%= ExportListAction.PATH_EXPORT_LIST_JSP %>" flush="false" />
<%
if (vo.getAryLblInactivate().length > 0) {
%>
<div class="List">
	<table class="LeftListTable" id="list">
		<tr>
			<td class="TitleTd"><%= params.getName("ActivateDate") %></td>
			<td class="InputTd" id="tdActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear" name="txtActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />
				<label for="txtActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />
				<label for="txtActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay" name="txtActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>" />
				<label for="txtActivateDay"><%= params.getName("Day") %></label>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdActivateDate', null, '<%= HumanExportListAction.CMD_SET_ACTIVATION_DATE %>')">
					<%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %>
				</button>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltWorkPlace" name="pltWorkPlace">
					<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltWorkPlace()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEmployment" name="pltEmployment">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmployment()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Section") %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSection" name="pltSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSection()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Position") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPosition" name="pltPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdFooter" colspan="4">
				<span class="FloatLeftSpan"><%= params.getName("ExportListAction") %></span>
				<span class="TableLabelSpan">
					<button type="button" class="Name2Button" id="btnExecute" onclick="submitFile(event, null, null, '<%= HumanExportListAction.CMD_EXPORT %>');"><%= params.getName("Execution") %></button>
				</span>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getTableTypeCodeKey()) %>', '<%= PlatformConst.PRM_TRANSFERRED_COMMAND %>', '<%= HtmlUtility.escapeHTML(vo.getReShowCommand()) %>'), '<%= ExportCardAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (vo.getAryLblInactivate().length == 0) {
	return;
}
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
