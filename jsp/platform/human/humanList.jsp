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
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.EmploymentContractCodeComparator"
import = "jp.mosp.platform.comparator.base.PositionCodeComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.comparator.base.WorkPlaceCodeComparator"
import = "jp.mosp.platform.comparator.human.HumanListEmployeeKanaComparator"
import = "jp.mosp.platform.comparator.human.HumanListRetireStateComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.human.action.HumanListAction"
import = "jp.mosp.platform.human.vo.HumanListVo"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PlatformNamingUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanListVo vo = (HumanListVo)params.getVo();
%>
<div class="List" id="humanListSearch">
	<table class="InputTable" id="tblGeneral">
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd" id="inputActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear" name="txtActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />&nbsp;<label for="txtActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth())%>" />&nbsp;<label for="txtActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay" name="txtActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>"/>&nbsp;<label for="txtActivateDay"><%= params.getName("Day") %></label>
				<button type="button" class="Name2Button" onclick="submitForm(event, 'inputActivateDate', null, '<%= HumanListAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
			</td>
			<td class="TitleTd"><span><label for="txtEmployeeCode"><%= PlatformNamingUtility.employeeCode(params) %></label></span></td>
			<td class="InputTd" id="inputEmployeeCode">
				<input type="text" class="Code10TextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" />
				<select class="Name4PullDown" id="pltEmployeeCode" name="pltEmployeeCode">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_TYPE, vo.getPltEmployeeCode(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="titleLastName"><span><label for="txtLastName"><%= params.getName("LastName") %></label></span></td>
			<td class="InputTd" id="inputLastName">
				<input type="text" class="Name10TextBox" id="txtLastName" name="txtLastName" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastName()) %>" />
				<select class="Name4PullDown" id="pltLastName" name="pltLastName">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_TYPE, vo.getPltLastName(), false) %>
				</select>
			</td>
			<td class="TitleTd"><span><%= params.getName("WorkPlace","Abbreviation") %></span></td>
			<td class="InputTd" id="inputWorkPlaceAbbr">
				<select class="Name15PullDown" id="pltWorkPlaceAbbr" name="pltWorkPlaceAbbr" >
					<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltWorkPlaceAbbr()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><%= params.getName("EmploymentContract","Abbreviation") %></span></td>
			<td class="InputTd" id="inputEmploymentName">
				<select class="Name15PullDown" id="pltEmploymentName" name="pltEmploymentName" >
					<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmploymentName()) %>
				</select>
			</td>
			<td class="TitleTd"><span><%= params.getName("Section") %></span></td>
			<td class="InputTd" id="inputSectionAbbr">
				<select class="SectionNamePullDown" id="pltSectionAbbr" name="pltSectionAbbr" >
					<%= HtmlUtility.getSelectOption(vo.getAryPltSectionAbbr(), vo.getPltSectionAbbr()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><%= params.getName("Position") %></span></td>
			<td class="InputTd" id="inputPositionName">
				<select class="Name15PullDown" id="pltPositionName" name="pltPositionName" >
					<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPositionName()) %>
				</select>
			</td>
			<td class="TitleTd"><span><%= params.getName("Retirement","Type") %></span></td>
			<td class="InputTd">
				<select class="StatePullDown" id="pltState" name="pltState">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_EMPLOYEE_STATE, vo.getPltState(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<div id="hidden" style="display: none;">
		<table class="InputTable">
			<tr>
				<td class="TitleTd"><span><label for="txtFirstName"><%= params.getName("FirstName") %></label></span></td>
				<td class="InputTd" id="inputFirstName">
					<input type="text" class="Name10TextBox" id="txtFirstName" name="txtFirstName" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstName()) %>" />
					<select class="Name4PullDown" id="pltFirstName" name="pltFirstName">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_TYPE, vo.getPltFirstName(), false) %>
					</select>
				</td>
				<td class="TitleTd"><span><label for="txtLastKana"><%= params.getName("LastName","FrontParentheses","Kana","BackParentheses") %></label></span></td>
				<td class="InputTd" id="inputLastKana">
					<input type="text" class="Kana10TextBox" id="txtLastKana" name="txtLastKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastKana()) %>" />
					<select class="ConditionPullDown" id="pltLastKana" name="pltLastKana">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_TYPE, vo.getPltLastKana(), false) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd" id="titleFirstKana"><span><label for="txtFirstKana"><%= params.getName("FirstName","FrontParentheses","Kana","BackParentheses") %></label></span></td>
				<td class="InputTd" id="inputFirstKana">
					<input type="text" class="Kana10TextBox" id="txtFirstKana" name="txtFirstKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstKana()) %>" />
					<select id="ConditionPullDown" id="pltFirstKana" name="pltFirstKana">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_TYPE, vo.getPltFirstKana(), false) %>
					</select>
				</td>
				<td class="Blank" colspan="2"></td>
			</tr>
			<tr>
				<td class="TitleTd"><span><%= params.getName("Information") %></span></td>
				<td class="InputTd" id="tdInformation" colspan="3">
					<select class="InfoPullDown" id="pltInfoType" name="pltInfoType">
					<%= HtmlUtility.getSelectOption(vo.getAryPltFreeWordTypes(), vo.getPltInfoType()) %>
					</select>&nbsp;
					<span><%= params.getName("Search","Word") %></span>&nbsp;
					<input type="text" class="Name25TextBox" id="txtSearchWord" name="txtSearchWord" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchWord()) %>" />
				</td>
			</tr>
		</table>
	</div>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="SearchButton" onclick="submitForm(event, 'humanListSearch', checkExtra, '<%= HumanListAction.CMD_SEARCH %>');"><%= params.getName("Search") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<a id="HumanSearchChangeLower" onclick="show('hidden', 'HumanSearchChangeLower', 'HumanSearchChangeUpper')"><%= params.getName("Detail","Search") %></a>
				<a id="HumanSearchChangeUpper" onclick="hide('hidden', 'HumanSearchChangeLower', 'HumanSearchChangeUpper')" style="display:none"><%= params.getName("UpperTriangular") %></a>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="humanList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= PlatformNamingUtility.employeeCode(params) %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("FullName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeKana"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HumanListEmployeeKanaComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("FullName","FrontParentheses","Kana","BackParentheses") %><%= PlatformUtility.getSortMark(HumanListEmployeeKanaComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkPlaceAbbr"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceCodeComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("WorkPlace","Abbreviation") %><%= PlatformUtility.getSortMark(WorkPlaceCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmploymentName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmploymentContractCodeComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("EmploymentContractAbbr","Abbreviation") %><%= PlatformUtility.getSortMark(EmploymentContractCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSectionAbbr"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPositionName"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PositionCodeComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("Position","Abbreviation") %><%= PlatformUtility.getSortMark(PositionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState"          onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HumanListRetireStateComparator.class.getName() %>'), '<%= HumanListAction.CMD_SORT %>')"><%= params.getName("Retirement") %><%= PlatformUtility.getSortMark(HumanListRetireStateComparator.class.getName(), params) %></th> 
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd" class="ButtonTd">
					<button type="button" id="btnHumenInfo" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= HumanListAction.CMD_TRANSFER %>');">
						<%= params.getName("Select") %>
					</button>
				</td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryEmployeeCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryEmployeeName(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryEmployeeKana(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryWorkPlaceAbbr(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryEmploymentAbbr(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getArySection(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryPositionAbbr(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryState(i)) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
	<input type="hidden" name="modeSearchExpansion" id="modeSearchExpansion" value="<%= HtmlUtility.escapeHTML(vo.getModeSearchExpansion()) %>" />
</div>
<%
if (vo.getList().size() == 0) {
	return;
}
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
