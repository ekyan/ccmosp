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
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterApprovalCountComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterRouteCodeComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterRouteNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.workflow.action.RouteCardAction"
import = "jp.mosp.platform.workflow.action.RouteListAction"
import = "jp.mosp.platform.workflow.vo.RouteListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RouteListVo vo = (RouteListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Search") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
				<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><label for="pltSearchRouteStage"><%= params.getName("Hierarchy","Num") %></label></td>
			<td class="InputTd">
				<select class="Number2PullDown" id="pltSearchRouteStage" name="pltSearchRouteStage">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_APPROVAL_COUNT, vo.getPltSearchRouteStage(), true) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchRouteCode"><%= params.getName("Route","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteCode" name="txtSearchRouteCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchRouteName"><%= params.getName("Route","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchRouteName" name="txtSearchRouteName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteName()) %>"/></td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchUnitCode"><%= params.getName("WorkflowUnit","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchUnitCode" name="txtSearchUnitCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchUnitName"><%= params.getName("WorkflowUnit","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchUnitName" name="txtSearchUnitName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitName()) %>"/></td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= RouteListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"   id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator                    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= params.getName("ActivateDate")        %><%= PlatformUtility.getSortMark(ActivateDateComparator                    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteCode"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterRouteCodeComparator    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= params.getName("Route","Code")        %><%= PlatformUtility.getSortMark(ApprovalRouteMasterRouteCodeComparator    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteName"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterRouteNameComparator    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= params.getName("Route","Name")        %><%= PlatformUtility.getSortMark(ApprovalRouteMasterRouteNameComparator    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteStage"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterApprovalCountComparator.class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= params.getName("Approval","Hierarchy")%><%= PlatformUtility.getSortMark(ApprovalRouteMasterApprovalCountComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thFirstUnit"><%= params.getName("No1","Following","WorkflowUnit") %></th>
				<th class="ListSelectTh" id="thEndUnit"><%= params.getName("Finality","WorkflowUnit") %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator                      .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (vo.getList().size() > 0) {
%>
					<input type="checkbox" onclick="doAllBoxChecked(this);" />
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblRouteCode()[i]) %>'), '<%= RouteCardAction.CMD_SELECT_SHOW %>');" ><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteCode()[i])    %></td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteName()[i])    %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteStage()[i])   %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFirstUnit()[i])    %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEndUnit()[i])      %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i])   %></td>
				<td class="ListSelectTd"><input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbRouteListId()[i]) %>"></td>
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
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Bulk","Update") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />
				<label for="txtUpdateActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />
				<label for="txtUpdateActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />
				<label for="txtUpdateActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><%= params.getName("Effectiveness","Slash","Inactivate") %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= RouteListAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= RouteCardAction.CMD_SHOW %>')"><%= params.getName("New","Insert") %></button>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
