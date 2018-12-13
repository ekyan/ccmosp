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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.base.PositionCodeComparator"
import = "jp.mosp.platform.comparator.system.PositionMasterGradeLevelComparator"
import = "jp.mosp.platform.comparator.system.PositionMasterPositionAbbrComparator"
import = "jp.mosp.platform.comparator.system.PositionMasterPositionNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.system.action.PositionMasterAction"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.system.vo.PositionMasterVo"
import = "jp.mosp.platform.utils.PlatformUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PositionMasterVo vo = (PositionMasterVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>"/>&nbsp;<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>"/>&nbsp;<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />&nbsp;<label for="txtEditActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditPositionCode"><%= params.getName("Position","Code") %></label></span></td>
			<td class="InputTd" id="inputCode">
				<input type="text" class="Code10RequiredTextBox" id="txtEditPositionCode" name="txtEditPositionCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPositionCode()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditPositionName"><%= params.getName("Position","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15-30RequiredTextBox" id="txtEditPositionName" name="txtEditPositionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPositionName())%>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditPositionAbbr"><%= params.getName("Position","Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6RequiredTextBox" id="txtEditPositionAbbr" name="txtEditPositionAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPositionAbbr()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditPositionGrade,txtEditPositionLevel"><%= params.getName("PositionGradeLevel") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtEditPositionGrade" name="txtEditPositionGrade" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPositionGrade()) %>" />
<%
if (params.getApplicationPropertyBool("PositionLevelAvailable")) {
%>
				<span><%= params.getName("Grade") %></span>
				<input type="text" class="Number2RequiredTextBox" id="txtEditPositionLevel" name="txtEditPositionLevel" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPositionLevel()) %>" />
				<span><%= params.getName("Issue") %></span>
<%
}
%>
			</td>
			<td class="TitleTd"><span><label for="pltEditInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', null, '<%= PositionMasterAction.CMD_REGIST %>')"><%=params.getName("Insert")%></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSearch">
	<table class="InputTable" id="tblBaseSettingSearch">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Search") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd" id="titleEmployDate"><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd" id="inputDate">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />&nbsp;<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />&nbsp;<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />&nbsp;<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span><label for="txtSearchPositionCode"><%= params.getName("Position","Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchPositionCode" name="txtSearchPositionCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositionCode()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchPositiontName"><%= params.getName("Position","Name") %></label></span></td>
			<td class="InputTd"">
				<input type="text" class="Name15-30TextBox" id="txtSearchPositiontName" name="txtSearchPositionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositionName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd" ><span><label for="txtSearchPositionAbbr"><%= params.getName("Position","Abbreviation") %></label></span></td>
			<td class="InputTd" >
				<input type="text" class="Byte6TextBox" id="txtSearchPositionAbbr" name="txtSearchPositionAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositionAbbr()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchPositionGrade,txtSearchPositionLevel"><%= params.getName("PositionGradeLevel") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtSearchPositionGrade" name="txtSearchPositionGrade" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositionGrade()) %>" />
<%
if (params.getApplicationPropertyBool("PositionLevelAvailable")) {
%>
				<span><%= params.getName("Grade") %></span>
				<input type="text" class="Number2TextBox" id="txtSearchPositionLevel" name="txtSearchPositionLevel" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositionLevel()) %>" />
				<span><%= params.getName("Issue") %></span>
<%
}
%>
			</td>
			<td class="TitleTd"><span><label for="pltSearchInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivatePullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= PositionMasterAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPositionCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PositionCodeComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');"><%= params.getName("Position","Code") %><%= PlatformUtility.getSortMark(PositionCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPositionName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PositionMasterPositionNameComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');"><%= params.getName("Position","Name") %><%= PlatformUtility.getSortMark(PositionMasterPositionNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPositionAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PositionMasterPositionAbbrComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');"><%= params.getName("Position","Abbreviation") %><%= PlatformUtility.getSortMark(PositionMasterPositionAbbrComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPositionGradeLevel"
<%
if (params.getApplicationPropertyBool("PositionLevelAvailable")) {
%>
				colspan="2"
<%
}
%>
				onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PositionMasterGradeLevelComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');">
					<%= params.getName("PositionGradeLevel") %><%= PlatformUtility.getSortMark(PositionMasterGradeLevelComparator.class.getName(), params) %>
			</th>
			<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= PositionMasterAction.CMD_SORT %>');"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
			<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
				<input type="checkbox" onclick="doAllBoxChecked(this);" />
<%
}
%>
			</th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblPositionCode().length; i++) {
%>
		<tr>
			<td class="ListSelectTd" id="tdButton">
				<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblPositionCode()[i]) %>'), '<%= PositionMasterAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
			</td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPositionCode()[i]) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPositionName()[i]) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPositionAbbr()[i]) %></td>
			<td class="ListNumberTd"><%= HtmlUtility.escapeHTML(vo.getAryLblGrade()[i]) %></td>
<% if (params.getApplicationPropertyBool("PositionLevelAvailable")) { %>
			<td class="ListNumberTd"><%= vo.getAryLblLevel()[i] %></td>
<% } %>
			<td class="ListSelectTd" id="tdState"><%= vo.getAryLblInactivate()[i] %></td>
			<td class="ListSelectTd" id=""><input type="checkbox" class="SelectCheckBox" name="ckbSelect" value="<%=vo.getAryCkbRecordId()[i]%>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
		</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
		<tr>
			<th class=UnderTd colspan="<%= params.getApplicationPropertyBool("PositionLevelAvailable") ? 9 : 8 %>">
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, 'divList', checkExtra, '<%= PositionMasterAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
				</span>
			</th>
		</tr>
<%
}
%>
	</table>
</div>
<%
if (vo.getList().isEmpty()) {
	return;
}
if (vo.getAryLblPositionCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4"><span class="TitleTh"><%= params.getName("Bulk","Update") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />&nbsp;<label for="txtUpdateActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />&nbsp;<label for="txtUpdateActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />&nbsp;<label for="txtUpdateActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnEditUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= PositionMasterAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
