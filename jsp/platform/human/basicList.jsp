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
buffer       = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.human.vo.BasicListVo"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.action.BasicCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.BasicListAction"
import = "jp.mosp.platform.constant.PlatformConst"

%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BasicListVo vo = (BasicListVo)params.getVo();
%>

<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_COMMON_INFO_JSP %>" flush="false" />
<div class="List">
	
<% for (int i = 0; i < vo.getAryActiveteDate().length; i++) { %>
	<table class="OverTable">
		<tr>
			<th colspan="4" class="ListTableTh" id="">
				<span class="TitleTh"><%= HtmlUtility.escapeHTML(vo.getAryActiveteDate(i)) %><%= params.getName("Wave") %></span>
				<span class="TableButtonSpan">
<%
if (vo.getNeedDeleteBasciHistory()) {
%>
					<button type="button" id="btnHumenInfo" name="btnDelete" class="Name4Button" onclick="submitTransfer(event, null, checkExtra, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= BasicListAction.CMD_DELETE %>');"
					><%= params.getName("History") %><%= params.getName("Delete") %></button>
<%
}
%>
					<button type="button" id="btnHumenInfo" class="Name4Button"
					onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= BasicCardAction.CMD_EDIT_SELECT %>'), '<%= BasicListAction.CMD_TRANSFER %>');"
					><%= params.getName("History") %><%= params.getName("Edit") %></button>
				</span>
			</th>
		</tr>
	</table>
	<table class="UnderTable">
		<tr>
			<td rowspan="2" class="TitleTd" id="titleEmployeeName">
			<div><%= params.getName("FrontParentheses") %><%= params.getName("Kana") %><%= params.getName("BackParentheses") %></div><div><%= params.getName("FullName") %></div></td>
			<td rowspan="2" class="InputTd" id="lblEmployeeName">
			<div><%= HtmlUtility.escapeHTML(vo.getAryEmployeeKana(i)) %></div><div><%= HtmlUtility.escapeHTML(vo.getAryEmployeeName(i)) %></div></td>
			<td class="TitleTd" id="tdTitleWorkPlace"><%= params.getName("WorkPlace") %></td>
			<td class="InputTd" id="lblWorkPlaceName"><%= HtmlUtility.escapeHTML(vo.getAryWorkPlace(i)) %></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdTitleListBasic"><%= params.getName("Employment") %><%= params.getName("EmploymentContractAbbr") %></td>
			<td class="InputTd" id="tdEmploymentName"><%= HtmlUtility.escapeHTML(vo.getAryEmployment(i)) %></td>
		</tr>
		<tr>
			<td class="TitleTd" id="titleSectionName"><%= params.getName("Section") %></td>
			<td class="InputTd" id="lblSectionName"><%= HtmlUtility.escapeHTML(vo.getArySection(i)) %></td>
			<td class="TitleTd" id="titlePositionName"><%= params.getName("Position") %></td>
			<td class="InputTd" id="lblPositionName"><%= HtmlUtility.escapeHTML(vo.getAryPosition(i)) %></td>
		</tr>
<%
if (vo.getNeedPost()) {
%>		
		<tr>
			<td class="TitleTd" id="titlePost"><%= params.getName("Post") %></td>
			<td class="InputTd" id="lblPostName"><%= HtmlUtility.escapeHTML(vo.getAryPost(i)) %></td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
}
%>		
	</table>
<% } %>
	
</div>
<div class="Button">

		<button type="button" id="btnCmd01" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= BasicCardAction.CMD_ADD_SELECT %>'), '<%= BasicListAction.CMD_TRANSFER %>');"
		><%= params.getName("History") %><%= params.getName("Add") %></button>
		<button type="button" id="btnCmd01" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=HumanInfoAction.class.getName() %>'), '<%= BasicListAction.CMD_TRANSFER %>');"
		><%= params.getName("Information") %><%= params.getName("List") %></button>
</div>
