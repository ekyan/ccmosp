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
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.workflow.action.UnitCardAction"
import = "jp.mosp.platform.workflow.action.UnitListAction"
import = "jp.mosp.platform.workflow.vo.UnitCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
UnitCardVo vo = (UnitCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<div id="divSearchSectionCode">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
					<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
					<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
					<label for="txtEditActivateDay"><%= params.getName("Day") %></label>
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'divSearchSectionCode', null, '<%= UnitCardAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</div>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtUnitCode"><%= params.getName("WorkflowUnit","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtUnitCode" name="txtUnitCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtUnitCode()) %>"/></td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtUnitName"><%= params.getName("WorkflowUnit","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtUnitName" name="txtUnitName" value="<%= HtmlUtility.escapeHTML(vo.getTxtUnitName()) %>"/></td>	
		</tr>
		<tr>
<%--		
			<td class="TitleTd"><span><label for="pltRouteStage"><%= params.getName("Multiple") %><%= params.getName("Settlement") %></label></span></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltRouteStage" name="pltRouteStage">
					<option value="1">有効</option>
					<option value="2" selected>無効</option>
				</select>
			</td>
--%>
			<td class="TitleTd"><span><label for="pltInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="5">
				<span class="TitleTh"><%= params.getName("Approver","Set") %></span><a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio">
				<input type="radio" class="RadioButton" name="radUnitType" id="radioSectionPosition" value="<%= PlatformConst.UNIT_TYPE_SECTION %>" <%= HtmlUtility.getChecked(vo.getRadUnitType().equals(PlatformConst.UNIT_TYPE_SECTION)) %> />
			</td>
			<td class="TitleTd" id="tdMasterTitle1"><label for="pltSectionMaster"><%= params.getName("Section","Name") %></label></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSectionMaster" name="pltSectionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSectionMaster(), vo.getPltSectionMaster()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><label for="pltPositionMaster"><%= params.getName("Position","Name") %></label></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPositionMaster" name="pltPositionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPositionMaster(), vo.getPltPositionMaster()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radUnitType" id="radioEmployeeCode" value="<%= PlatformConst.UNIT_TYPE_PERSON %>" <%= HtmlUtility.getChecked(vo.getRadUnitType().equals(PlatformConst.UNIT_TYPE_PERSON)) %> />
			</td>
			<td class="TitleTd"><label for="txtEmployeeCode"><%= params.getName("Personal","FrontParentheses","InputCsv","BackParentheses") %></label></td>
			<td class="InputTd" colspan="3">
			<input type="text" class="CodeCsvTextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" /></td>
		</tr>
		<tr>
			<td class="TitleTd" id="lblApprover"><%= params.getName("Approver","FullName") %></td>
			<td class="TitleInputTd" colspan="3" id="lblEmployeeName"><%= HtmlUtility.escapeHTML(vo.getLblEmployeeCode()) %></td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= UnitCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" class="Name6Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= UnitCardAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
<%
}
%>
	<button type="button" class="Name6Button" id="btnUnitList" name="btnUnitList" onclick="submitTransfer(event, null, null, null, '<%= UnitListAction.CMD_RE_SEARCH %>')"><%= params.getName("WorkflowUnit","List") %></button>
</div>
