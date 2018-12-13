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
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.base.WorkPlaceCodeComparator"
import = "jp.mosp.platform.comparator.system.WorkPlaceMasterPhoneNumberComparator"
import = "jp.mosp.platform.comparator.system.WorkPlaceMasterPostalCodeComparator"
import = "jp.mosp.platform.comparator.system.WorkPlaceMasterWorkPlaceAbbrComparator"
import = "jp.mosp.platform.comparator.system.WorkPlaceMasterWorkPlaceNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.system.action.WorkPlaceMasterAction"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.system.vo.WorkPlaceMasterVo"
import = "jp.mosp.platform.utils.PlatformUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkPlaceMasterVo vo = (WorkPlaceMasterVo)params.getVo();
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
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />&nbsp;<label for="txtEditActivateYear"><%= HtmlUtility.escapeHTML(params.getName("Year")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />&nbsp;<label for="txtEditActivateMonth"><%= HtmlUtility.escapeHTML(params.getName("Month")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />&nbsp;<label for="txtEditActivateDay"><%= HtmlUtility.escapeHTML(params.getName("Day")) %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditWorkPlaceCode"><%= params.getName("WorkPlace","Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEditWorkPlaceCode" name="txtEditWorkPlaceCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditWorkPlaceCode()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditWorkPlaceName"><%= params.getName("WorkPlace","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15RequiredTextBox" id="txtEditWorkPlaceName" name="txtEditWorkPlaceName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditWorkPlaceName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditWorkPlaceAbbr"><%= params.getName("WorkPlace","Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6RequiredTextBox" id="txtEditWorkPlaceAbbr" name="txtEditWorkPlaceAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditWorkPlaceAbbr()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtEditWorkPlaceKana"><%= params.getName("WorkPlace","Name","FrontParentheses","Kana","BackParentheses") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Kana15TextBox" id="txtEditWorkPlaceKana" name="txtEditWorkPlaceKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditWorkPlaceKana()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtEditPostalCode"><%= params.getName("WorkPlace","PostalCode") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtEditPostalCode1" name="txtEditPostalCode1" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPostalCode1()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtEditPostalCode2" name="txtEditPostalCode2" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPostalCode2()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtEditWorkPlaceName"><%= params.getName("WorkPlace","Prefecture") %></label></span></td>
			<td class="InputTd">
				<select class="PullDown" id="pltEditPrefecture" name="pltEditPrefecture">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPrefecture(), vo.getPltEditPrefecture()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="txtEditAddress1"><%= params.getName("WorkPlace","City") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name10-50TextBox" id="txtEditAddress1" name="txtEditAddress1" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditAddress1()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtEditAddress2"><%= params.getName("WorkPlace","StreetAddress") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtEditAddress2" name="txtEditAddress2" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditAddress2()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtEditAddress3"><%= params.getName("WorkPlace","Building","Information") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name10-50TextBox" id="txtEditAddress3" name="txtEditAddress3" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditAddress3()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtEditPhoneNumber"><%= params.getName("WorkPlace","Phone","Number") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number5TextBox" id="txtEditPhoneNumber1" name="txtEditPhoneNumber1" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPhoneNumber1()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtEditPhoneNumber2" name="txtEditPhoneNumber2" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPhoneNumber2()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtEditPhoneNumber3" name="txtEditPhoneNumber3" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditPhoneNumber3()) %>" />
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
				<button type="button" class="Name2Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', null, '<%= WorkPlaceMasterAction.CMD_REGIST %>')"><%=params.getName("Insert")%></button>
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
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />&nbsp;<label for="txtSearchActivateYear"><%= HtmlUtility.escapeHTML(params.getName("Year")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />&nbsp;<label for="txtSearchActivateMonth"><%= HtmlUtility.escapeHTML(params.getName("Month")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />&nbsp;<label for="txtSearchActivateDay"><%= HtmlUtility.escapeHTML(params.getName("Day")) %></label>
			</td>
			<td class="TitleTd"><span><label for="txtSearchWorkPlaceCode"><%= params.getName("WorkPlace","Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchWorkPlaceCode" name="txtSearchWorkPlaceCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchWorkPlaceCode()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchWorkPlaceName"><%= params.getName("WorkPlace","Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchWorkPlaceName" name="txtSearchWorkPlaceName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchWorkPlaceName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtSearchWorkPlaceKana"><%= params.getName("WorkPlace","Name","FrontParentheses","Kana","BackParentheses") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Kana15TextBox" id="txtSearchWorkPlaceKana" name="txtSearchWorkPlaceKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchWorkPlaceKana()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchWorkPlaceAbbr"><%= params.getName("WorkPlace","Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6TextBox" id="txtSearchWorkPlaceAbbr" name="txtSearchWorkPlaceAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchWorkPlaceAbbr()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchPostalCode"><%= params.getName("WorkPlace","PostalCode") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number3TextBox" id="txtSearchPostalCode1" name="txtSearchPostalCode1" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPostalCode1()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtSearchPostalCode2" name="txtSearchPostalCode2" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPostalCode2()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtSearchWorkPlaceName"><%= params.getName("WorkPlace","Prefecture") %></label></span></td>
			<td class="InputTd">
				<select class="PullDown" id="pltSearchPrefecture" name="pltSearchPrefecture">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchPrefecture(), vo.getPltSearchPrefecture()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="txtSearchAddress1"><%= params.getName("WorkPlace","City") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name10-50TextBox" id="txtSearchAddress1" name="txtSearchAddress1" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchAddress1()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchAddress2"><%= params.getName("WorkPlace","StreetAddress") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchAddress2" name="txtSearchAddress2" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchAddress2()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtSearchAddress3"><%= params.getName("WorkPlace","Building","Information") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name10-50TextBox" id="txtSearchAddress3" name="txtSearchAddress3" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchAddress3()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchPhoneNumber"><%= params.getName("WorkPlace","Phone","Number") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Number5TextBox" id="txtSearchPhoneNumber1" name="txtSearchPhoneNumber1" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPhoneNumber1()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtSearchPhoneNumber2" name="txtSearchPhoneNumber2" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPhoneNumber2()) %>" />&nbsp;<%= params.getName("Hyphen") %>
				<input type="text" class="Number4TextBox" id="txtSearchPhoneNumber3" name="txtSearchPhoneNumber3" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPhoneNumber3()) %>" />
			</td>
			<td class="TitleTd"><span><label for="pltEditInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= WorkPlaceMasterAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkPlaceCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceCodeComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("WorkPlace") %><%= params.getName("Code") %><%= PlatformUtility.getSortMark(WorkPlaceCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkPlaceName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceMasterWorkPlaceNameComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("WorkPlace","Name") %><%= PlatformUtility.getSortMark(WorkPlaceMasterWorkPlaceNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkPlaceAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceMasterWorkPlaceAbbrComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("WorkPlace","Abbreviation") %><%= PlatformUtility.getSortMark(WorkPlaceMasterWorkPlaceAbbrComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPostalCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceMasterPostalCodeComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("PostalCode") %><%= PlatformUtility.getSortMark(WorkPlaceMasterPostalCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPhoneNumber" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkPlaceMasterPhoneNumberComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("Phone","Number") %><%= PlatformUtility.getSortMark(WorkPlaceMasterPhoneNumberComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= WorkPlaceMasterAction.CMD_SORT %>');"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
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
<%
for (int i = 0; i < vo.getAryLblWorkPlaceCode().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i)) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= vo.getAryLblWorkPlaceCode()[i] %>'), '<%= WorkPlaceMasterAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
			</td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i)) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkPlaceCode(i)) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkPlaceName(i)) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkPlaceAbbr(i)) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPostalCode(i)) %></td>
			<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPhoneNumber(i)) %></td>
			<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate(i)) %></td>
			<td class="ListSelectTd"><input type="checkbox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
		</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
		<tr>
			<th class=UnderTd colspan="9">
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, 'divList', checkExtra, '<%= WorkPlaceMasterAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
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
if (vo.getAryLblWorkPlaceCode().length > 0) {
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
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />&nbsp;<label for="txtUpdateActivateYear"><%= HtmlUtility.escapeHTML(params.getName("Year")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />&nbsp;<label for="txtUpdateActivateMonth"><%= HtmlUtility.escapeHTML(params.getName("Month")) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />&nbsp;<label for="txtUpdateActivateDay"><%= HtmlUtility.escapeHTML(params.getName("Day")) %></label>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnEditUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= WorkPlaceMasterAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
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
