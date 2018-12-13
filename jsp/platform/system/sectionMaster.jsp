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
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.comparator.system.SectionMasterClassRouteComparator"
import = "jp.mosp.platform.comparator.system.SectionMasterCloseFlagComparator"
import = "jp.mosp.platform.comparator.system.SectionMasterSectionNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.system.action.SectionMasterAction"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.system.vo.SectionMasterVo"
import = "jp.mosp.platform.utils.PlatformUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SectionMasterVo vo = (SectionMasterVo)params.getVo();
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
			<td class="InputTd" rowspan="2">
				<div id="divSearchClassRoute">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />&nbsp;<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />&nbsp;<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay"  value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />&nbsp;<label for="txtEditActivateDay"><%= params.getName("Day") %></label>
					<button type="button" class="Name2Button" id="btnEditActivateDate" onclick="submitForm(event, 'divSearchClassRoute', null, '<%= SectionMasterAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</div>
				<div>
					<select class="ClassRouteRequiredPullDown" id="pltEditClassRoute" name="pltEditClassRoute">
						<%= HtmlUtility.getSelectOption(vo.getAryPltEditClassRoute(), vo.getPltEditClassRoute()) %>
					</select>
				</div>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditSectionCode"><%= params.getName("Section","Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEditSectionCode" name="txtEditSectionCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditSectionCode()) %>"/>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditSectionName"><%= params.getName("Section","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="<%= vo.getUseDisplay() == true ? "Name15-40RequiredTextBox" : "Name15-40RequiredTextBox" %>" id="txtEditSectionName" name="txtEditSectionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditSectionName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="pltEditClassRoute"><%= params.getName("HigherOrder","Section") %></label></span></td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditSectionAbbr"><%= params.getName("Section","Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6RequiredTextBox" id="txtEditSectionAbbr" name="txtEditSectionAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditSectionAbbr()) %>"/>
			</td>
			<td class="TitleTd"><span><label for="pltEditInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
		</tr>
<%
if (vo.getUseDisplay()) {
%>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditSectionDisplay"><%= params.getName("Section","Display","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name16RequiredTextBox" id="txtEditSectionDisplay" name="txtEditSectionDisplay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditSectionDisplay()) %>"/>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
<%
}
%>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', null, '<%= SectionMasterAction.CMD_REGIST %>')"><%=params.getName("Insert")%></button>
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
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>"/>&nbsp;<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>"/>&nbsp;<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>"/>&nbsp;<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span><label for=""><%= params.getName("Search","Target") %></label></span></td>
			<td class="InputTd">
				<select class="PullDown" id="pltSectionType" name="pltSectionType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_SEARCH_SECTION_TYPE, vo.getPltSectionType(), false) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="txtSearchSectionCode"><%= params.getName("Section","Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchSectionCode" name="txtSearchSectionCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchSectionCode()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtSearchSectionName"><%= params.getName("Section","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15-30TextBox" id="txtSearchSectionName" name="txtSearchSectionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchSectionName()) %>" />
			</td>
			<td class="TitleTd"><span><label for="pltSearchSectionAbbr"><%= params.getName("Section","Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6TextBox" id="pltSearchSectionAbbr" name="pltSearchSectionAbbr" value="<%= HtmlUtility.escapeHTML(vo.getPltSearchSectionAbbr()) %>" />
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
				<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= SectionMasterAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= SectionMasterAction.CMD_SORT %>');"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thSectionCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= SectionMasterAction.CMD_SORT %>');"><%= params.getName("Section","Code") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thSectionName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionMasterSectionNameComparator.class.getName() %>'), '<%= SectionMasterAction.CMD_SORT %>');"><%= params.getName("Section","Name") %><%= PlatformUtility.getSortMark(SectionMasterSectionNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thSectionClassRoute" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionMasterClassRouteComparator.class.getName() %>'), '<%= SectionMasterAction.CMD_SORT %>');"><%= params.getName("Section","Hierarchy") %><%= PlatformUtility.getSortMark(SectionMasterClassRouteComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionMasterCloseFlagComparator.class.getName() %>'), '<%= SectionMasterAction.CMD_SORT %>');"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(SectionMasterCloseFlagComparator.class.getName(), params) %></th>
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
for (int i = 0; i < vo.getAryLblSectionCode().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i)) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= vo.getAryLblSectionCode()[i] %>'), '<%= SectionMasterAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
			</td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i)) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSectionCode(i)) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSectionName(i)) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblClassRoute(i)) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate(i)) %></td>
			<td class="ListSelectTd" id=""><input type="checkbox" class="SelectCheckBox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
		</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
		<tr>
			<th class="UnderTd" colspan="7">
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, 'divList', checkExtra, '<%= SectionMasterAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
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
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Bulk","Update") %></span>
				<span class="TableLabelSpan">
					<input type="radio" class="RadioButton" id="classRouteSelect" value="<%= SectionMasterAction.TYPE_BATCH_UPDATE_CLASS_ROUTE %>" name="radBatchUpdateType" <%= HtmlUtility.getChecked(vo.getRadBatchUpdateType().equals(SectionMasterAction.TYPE_BATCH_UPDATE_CLASS_ROUTE)) %>
							onclick="submitTransfer(event, null, checkBatchUpdateType, null, '<%= SectionMasterAction.CMD_SET_BATCH_UPDATE_TYPE %>')" /><%= params.getName("HigherOrder","Section","Select") %>&nbsp;
					<input type="radio" class="RadioButton" id="inactivateSelect" value="<%= SectionMasterAction.TYPE_BATCH_UPDATE_INACTIVATE  %>" name="radBatchUpdateType" <%= HtmlUtility.getChecked(vo.getRadBatchUpdateType().equals(SectionMasterAction.TYPE_BATCH_UPDATE_INACTIVATE )) %>
							onclick="submitTransfer(event, null, checkBatchUpdateType, null, '<%= SectionMasterAction.CMD_SET_BATCH_UPDATE_TYPE %>')" /><%= params.getName("Effectiveness","Slash","Inactivate","Switching") %>
				</span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span><%= params.getName("ActivateDate") %></span></td>
			<td class="InputTd" id="tdUpdateActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />&nbsp;<label for="txtUpdateActivateYear"><%= HtmlUtility.escapeHTML(params.getName("Year")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />&nbsp;<label for="txtUpdateActivateMonth"><%= HtmlUtility.escapeHTML(params.getName("Month")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />&nbsp;<label for="txtUpdateActivateDay"><%= HtmlUtility.escapeHTML(params.getName("Day")) %></label>
				<button type="button" class="Name2Button" id="btnUpdateActivateDate" onclick="submitForm(event, 'tdUpdateActivateDate', null, '<%= SectionMasterAction.CMD_SET_BATCH_ACTIVATION_DATE %>')"><%= vo.getModeUpdateActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateClassRoute"><%= params.getName("HigherOrder","Section") %></label></span></td>
			<td class="InputTd">
				<select class="ClassRoutePullDown" id="pltUpdateClassRoute" name="pltUpdateClassRoute">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUpdateClassRoute(), vo.getPltUpdateClassRoute()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltUpdateInactivate"  name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= SectionMasterAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
