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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.file.action.ExportCardAction"
import = "jp.mosp.platform.file.vo.ExportCardVo"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ExportCardVo vo = (ExportCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd" id=""><%= params.getName("Data") %><%= params.getName("Type") %></td>
				<td class="InputTd" id="tdTableInput">
					<select class="Name15PullDown" id="pltEditTable" name="pltEditTable">
						<%= HtmlUtility.getSelectOption(vo.getAryPltTableType(), vo.getPltEditTable()) %>
					</select>&nbsp;
					<button type="button" id="btnSelectTable" name="btnSelectTable" class="Name2Button" onclick="submitForm(event, 'pltEditTable', null, '<%= ExportCardAction.CMD_SET_TABLE_TYPE %>')">
						<%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %>
					</button>
				</td>
				<td class="TitleTd"><%= params.getName("DataType") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditType" name="pltEditType" disabled>
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_FILE_TYPE, vo.getPltEditType(), false) %>
					</select>
				</td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditCode"><%= params.getName("Export") %><%= params.getName("Code") %></label></td>
				<td class="InputTd">
					<input type="text" class="Code10RequiredTextBox" id="txtEditCode" name="txtEditCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCode()) %>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditName"><%= params.getName("Export") %><%= params.getName("Name") %></label></td>
				<td class="InputTd">
					<input type="text" class="Name10RequiredTextBox" id="txtEditName" name="txtEditName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditName()) %>" />
				</td>
				<td class="TitleTd"><%= params.getName("Header") %><%= params.getName("EffectivenessExistence") %><%= params.getName("InactivateExistence") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditHeader" name="pltEditHeader">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_HEADER_TYPE, vo.getPltEditHeader(), false) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Effectiveness") %><%= params.getName("Slash") %><%= params.getName("Inactivate") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="Select">
	<div class="SelectLeft">
		<div class="SelectLabel">
			<span><%= vo.getLblTableName() %></span>
		</div>
		<div class="SelectMultiple" id="divColumns">
			<select id="pltSelectTable" name="pltSelectTable" multiple="multiple">
			</select>
		</div>
	</div>
	<div class="SelectButton">
		<button type="button" class="Name6Button" id="btnAllSelect" onclick="selectAllItems('pltSelectTable', 'jsPltSelectSelected'                  );"><%= params.getName("All") %><%= params.getName("Select") %>&nbsp;&gt;&gt;</button>
		<button type="button" class="Name6Button" id="btnSelect"    onclick="selectItems   ('pltSelectTable', 'jsPltSelectSelected'                  );"><%= params.getName("Select") %>&nbsp;&gt;</button>
		<button type="button" class="Name6Button" id="btnCancel"    onclick="removeItems   ('pltSelectTable', 'jsPltSelectSelected', jsPltSelectTable);">&lt;&nbsp;<%= params.getName("Release") %></button>
		<button type="button" class="Name6Button" id="btnAllCancel" onclick="removeAllItems('pltSelectTable', 'jsPltSelectSelected', jsPltSelectTable);">&lt;&lt;&nbsp;<%= params.getName("All") %><%= params.getName("Release") %></button>
	</div>
	<div class="SelectRight">
		<div class="SelectLabel">
			<label for="jsPltSelectSelected"><%= params.getName("Select") %><%= params.getName("Item") %></label>
		</div>
		<div class="SelectMultiple">
			<select id="jsPltSelectSelected" name="jsPltSelectSelected" multiple="multiple">
			</select>
		</div>
	</div>
	<div class="Button">
		<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= ExportCardAction.CMD_REGIST %>');"><%= params.getName("Insert") %></button>
		<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= ExportCardAction.CMD_DELETE %>');"><%= params.getName("Delete") %></button>
		<button type="button" id="btnToList" class="Name4Button" onclick="submitTransfer(event, 'divEdit', null, null, '<%= HtmlUtility.escapeHTML(vo.getShowListCommand()) %>');"><%= params.getName("Information") %><%= params.getName("List") %></button>
	</div>
</div>
