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
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceEndApproverComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceFirstApproverComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceRouteApplicationCodeComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceRouteApplicationNameComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceRouteNameComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationReferenceRouteStageComparator"
import = "jp.mosp.platform.workflow.action.RouteApplicationReferenceAction"
import = "jp.mosp.platform.workflow.vo.RouteApplicationReferenceVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Search") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%></td>
			<td class="InputTd">
				<div id="divSearchActiveDate">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>" />
					<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>" />
					<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>" />
					<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>
					<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearchActiveDate', null, '<%=RouteApplicationReferenceAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</div>
			</td>
			<td class="TitleTd"><label for="txtSearchEmployeeCode"><%=params.getName("Employee","Code")%></label></td>
			<td class="InputTd" id="EmployeeCodeSearch">
				<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode())%>" />
			</td>
			<td class="TitleTd"><label for="txtSearchEmployeeName"><%=params.getName("FullName")%></label></td>
			<td class="InputTd">
				<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName())%>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%=params.getName("WorkPlace","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchWorkPlaceMaster" name="pltSearchWorkPlace">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlaceMaster(), vo.getPltSearchWorkPlace())%>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%=params.getName("EmploymentContract","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchEmploymentMaster" name="pltSearchEmployment">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchEmploymentMaster(), vo.getPltSearchEmployment())%>
				</select>
			</td>
			<td class="Blank" colspan="2" rowspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%=params.getName("Section","Name")%></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSearchSectionMaster" name="pltSearchSection">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSectionMaster(), vo.getPltSearchSection())%>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%=params.getName("Position","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPositionMaster" name="pltSearchPosition">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPositionMaster(), vo.getPltSearchPosition())%>
				</select>
			</td>
		</tr>
	</table>
	<table class="InputTable">
		<tr>
			<td class="TitleTd"><label for="txtSearchRouteApplicationCode"><%= params.getName("Route","Apply","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteApplicationCode" name="txtSearchRouteApplicationCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteApplicationCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchRouteApplicationName"><%= params.getName("Route","Apply","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchRouteApplicationName" name="txtSearchRouteApplicationName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteApplicationName()) %>"/></td>
			<td class="TitleTd"><label for="pltSearchRouteStage"><%= params.getName("Flow","Type") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchFlowType" name="pltSearchFlowType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_WORKFLOW_TYPE, vo.getPltSearchFlowType(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchRouteCode"><%= params.getName("Route","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteCode" name="txtSearchRouteCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchRouteName"><%= params.getName("Route","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchRouteName" name="txtSearchRouteName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteName()) %>"/></td>
			<td class="Blank" colspan="2" rowspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchApproverCode"><%= params.getName("Approver","Employee","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchApproverCode" name="txtSearchApproverCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApproverCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchApproverName"><%= params.getName("Approver","FullName") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchApproverName" name="txtSearchApproverName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApproverName()) %>"/></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= RouteApplicationReferenceAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=RouteApplicationReferenceAction.CMD_SORT%>')"><%=params.getName("Employee","Code")%><%=PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeNameComparator.class.getName()%>'), '<%=RouteApplicationReferenceAction.CMD_SORT%>')"><%=params.getName("FullName")%><%=PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thRouteApplicationCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceRouteApplicationCodeComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Apply","Code")%><%= PlatformUtility.getSortMark(RouteApplicationReferenceRouteApplicationCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRouteApplicationName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceRouteApplicationNameComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Apply","Name")%><%= PlatformUtility.getSortMark(RouteApplicationReferenceRouteApplicationNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRouteName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceRouteNameComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Route","Name")%><%= PlatformUtility.getSortMark(RouteApplicationReferenceRouteNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRouteStage" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceRouteStageComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Approval","Hierarchy")%><%= PlatformUtility.getSortMark(RouteApplicationReferenceRouteStageComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thFirstApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceFirstApproverComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("No1","Following","Approver") %><%= PlatformUtility.getSortMark(RouteApplicationReferenceFirstApproverComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEndApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationReferenceEndApproverComparator.class.getName() %>'), '<%= RouteApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Finality","Approver") %><%= PlatformUtility.getSortMark(RouteApplicationReferenceEndApproverComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRouteApplicationCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRouteApplicationName(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRouteName(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRouteStage(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblFirstApprovalName(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEndApprovalName(i)) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
