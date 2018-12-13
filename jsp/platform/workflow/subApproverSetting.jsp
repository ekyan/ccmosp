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
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EndDateComparator"
import = "jp.mosp.platform.comparator.base.WorkflowTypeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.workflow.action.SubApproverSettingAction"
import = "jp.mosp.platform.workflow.vo.SubApproverSettingVo"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PlatformNamingUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubApproverSettingVo vo = (SubApproverSettingVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable" id="subApproverSetting_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Substitution","Start","Day") %></td>
			<td class="InputTd" id="tdEditActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
				<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
				<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
				<label for="txtEditActivateDay"><%= params.getName("Day") %></label>&nbsp;
				<button type="button" class="Name2Button" id="btnEditActivateDate" onclick="submitForm(event, 'tdEditActivateDate', null, '<%= SubApproverSettingAction.CMD_SET_ACTIVATION_DATE %>')">
					<%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %>
				</button>
			</td>
			<td class="TitleTd"><label for="pltEditSection"><%= params.getName("Agency","Section") %></label></td>
			<td class="InputTd" id="tdNoBorderBottom">
				<select class="SectionNamePullDown" id="pltEditSection" name="pltEditSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditSection(), vo.getPltEditSection()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Substitution","End","Day") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditEndYear" name="txtEditEndYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndYear()) %>" />
				<label for="txtEditEndYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditEndMonth" name="txtEditEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndMonth()) %>" />
				<label for="txtEditEndMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditEndDay" name="txtEditEndDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndDay()) %>" />
				<label for="txtEditEndDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><label for="pltEditPosition"><%= params.getName("Agency","Position") %></label></td>
			<td class="InputTd" id="tdNoBorderVertical">
				<select class="Name15PullDown" id="pltEditPosition" name="pltEditPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPosition(), vo.getPltEditPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltEditWorkflowType"><%= params.getName("Flow","Type") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditWorkflowType" name="pltEditWorkflowType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_WORKFLOW_TYPE, vo.getPltEditWorkflowType(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="txtEditEmployeeCode"><%= params.getName("Agency") + PlatformNamingUtility.employeeCode(params) %></label></td>
			<td class="InputTd" id="tdNoBorderVertical">
				<input type="text" class="Code10TextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode()) %>"/>&nbsp;
				<button type="button" class="Name2Button" id="btnEmployeeSearch" onclick="submitForm(event, null, null, '<%= SubApproverSettingAction.CMD_SET_EMPLOYEE %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltEditInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltEditEmployeeName"><%= params.getName("Agency") %></label></td>
			<td class="InputTd" id="tdNoBorderTop">
				<select class="Name15PullDown" id="pltEditEmployeeName" name="pltEditEmployeeName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditEmployee(), vo.getPltEditEmployeeName()) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divEdit', null, '<%= SubApproverSettingAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSearch">
	<table class="InputTable" id="subApproverSetting_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Search") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Display","Period") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
			</td>
			<td class="TitleTd"><label for="txtSearchSectionName"><%= params.getName("Agency","Section") %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchSectionName" name="txtSearchSectionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchSectionName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchEmployeeName"><%= params.getName("Agency","FullName") %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>" />
			</td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= SubApproverSettingAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"   id="thStartDate"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("Start","Day")                                          %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEndDate"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EndDateComparator     .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("End","Day")                                            %><%= PlatformUtility.getSortMark(EndDateComparator     .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thFlowType"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkflowTypeComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("Type")                                                 %><%= PlatformUtility.getSortMark(WorkflowTypeComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("Agency") + PlatformNamingUtility.employeeCode(params)                             %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("Agency","FullName")                                    %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thSection"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("Agency","Section")                                     %><%= PlatformUtility.getSortMark(SectionCodeComparator .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator  .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator  .class.getName() , params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblSubApproverNo(i)) %>'), '<%= SubApproverSettingAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEndDate     ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkflowName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection     ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate  ()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
