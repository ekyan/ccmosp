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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.human.action.ConcurrentCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.ConcurrentCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ConcurrentCardVo vo = (ConcurrentCardVo)params.getVo();
%>
<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_COMMON_INFO_JSP %>" flush="false" />
<div class="List">
	<table class="ListTable" id="tblNowPosition">
		<tr>
			<td class="TitleTd" id="tdNowPosition">
				<%= params.getName("PresentTime","Of","Mainly","Charge","Colon") %>
<%
for (String sectionName : vo.getAryLblClassRoute()) {
%>
				<%= sectionName %>&nbsp;
<%
}
%>
				<%= HtmlUtility.escapeHTML(vo.getLblPositionName()) %>
			</td>
		</tr>
	</table>
</div>
<div class="FixList">
	<table class="ListTable" id="tblAddList">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thListNumber"></th>
				<th class="ListSelectTh" id="thCardButtonDate"><span class="RequiredLabel"><%= params.getName("RequireMark") %>&nbsp;</span><%= params.getName("Start","Day") %></th>
				<th class="ListSelectTh" id="thCardDate"><%= params.getName("End","Day") %></th>
				<th class="ListSelectTh" id="thSectionAbbr"><%= params.getName("Section") %></th>
				<th class="ListSelectTh" id="thPositionAbbr"><%= params.getName("Position") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("ConcurrentRemarks") %></th>
				<th class="ListSelectTh" id="thListSelect"><input type="checkbox" name="ckbAllSelect" onclick="doAllBoxChecked(this);setDeleteButtonDisabled();" /></th>
			</tr>
		</thead>
		<tbody id="addLeaveBody">
<%
for (int i = 0; i < vo.getAryTxtConcurrentStartYear().length; i++) {
%>
			<tr>
				<td class="NumberTd"><span class="RowIndex"></span></td>
				<td class="SelectTd" id="inputActivateDate<%= i %>">
					<input type="text" class="Number4RequiredTextBox" id="aryTxtConcurrentStartYear<%= i %>" name="aryTxtConcurrentStartYear" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartYear()[i]) %>" />&nbsp;<label for="aryTxtConcurrentStartYear<%= i %>"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtConcurrentStartMonth<%= i %>" name="aryTxtConcurrentStartMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartMonth()[i]) %>" />&nbsp;<label for="aryTxtConcurrentStartMonth<%= i %>"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtConcurrentStartDay<%= i %>" name="aryTxtConcurrentStartDay" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartDay()[i]) %>" />&nbsp;<label for="aryTxtConcurrentStartDay<%= i %>"><%= params.getName("Day") %></label>
					<button type="button" class="Name2Button" name="btnActivateDate"
						onclick="submitForm(event, null, checkExtraForActivate, '<%= ConcurrentCardAction.CMD_SET_ACTIVATION_DATE %>')">
						<%= vo.getModeActivateDateArray()[i].equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %>
					</button>
					<input type="hidden" name="modeActivateDateArray" value="<%= HtmlUtility.escapeHTML(vo.getModeActivateDateArray()[i]) %>" />
				</td>
				<td class="SelectTd">
					<input type="text" class="Number4TextBox" id="aryTxtConcurrentEndYear<%= i %>" name="aryTxtConcurrentEndYear" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndYear()[i]) %>" />&nbsp;<label for="aryTxtConcurrentEndYear<%= i %>"><%= params.getName("Year") %></label>
					<input type="text" class="Number2TextBox" id="aryTxtConcurrentEndMonth<%= i %>" name="aryTxtConcurrentEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndMonth()[i]) %>" />&nbsp;<label for="aryTxtConcurrentEndMonth<%= i %>"><%= params.getName("Month") %></label>
					<input type="text" class="Number2TextBox" id="aryTxtConcurrentEndDay<%= i %>" name="aryTxtConcurrentEndDay" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndDay()[i]) %>" />&nbsp;<label for="aryTxtConcurrentEndDay<%= i %>"><%= params.getName("Day") %></label>
				</td>
				<td class="SelectTd">
					<select class="Name12PullDown" id="aryPltSectionAbbr<%= i %>" name="arySectionAbbr">
						<%= HtmlUtility.getSelectOption(vo.getListAryPltSectionAbbr().get(i), vo.getArySectionAbbr()[i]) %>
					</select>
				</td>
				<td class="SelectTd">
					<select class="Name12PullDown" id="aryPltPosition<%= i %>" name="aryPosition">
						<%= HtmlUtility.getSelectOption(vo.getListAryPltPosition().get(i), vo.getAryPosition()[i]) %>
					</select>
				</td>
				<td class="SelectTd">
					<input type="text" class="Name10-50TextBox" id="aryTxtRemark<%= i %>" name="aryTxtRemark" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtRemark()[i]) %>" />
				</td>
				<td class="SelectTd">
					<input type="checkbox" name="ckbSelect" onclick="setDeleteButtonDisabled();" />
					<input type="hidden" name="aryHidPfaHumanConcurrentId" value="<%= HtmlUtility.escapeHTML(vo.getAryHidPfaHumanConcurrentId()[i]) %>" />
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" id="btnAddRow" class="Name4Button" onclick="addConcurrentRow();"><%= params.getName("Row","Add") %></button>
	<button type="button" id="btnRegist" class="Name4Button"
		onclick="removeBlankRows();submitRegist(event, 'tblAddList', checkExtraForRegist, '<%= ConcurrentCardAction.CMD_UPDATE %>');"><%= params.getName("Insert") %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= ConcurrentCardAction.CMD_DELETE %>')"><%= params.getName("Delete") %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=HumanInfoAction.class.getName() %>'), '<%= ConcurrentCardAction.CMD_TRANSFER %>');">
		<%= params.getName("Information","List") %>
	</button>
</div>
<div class="MoveUpLink">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular") %><%= params.getName("TopOfPage") %></a>
</div>
