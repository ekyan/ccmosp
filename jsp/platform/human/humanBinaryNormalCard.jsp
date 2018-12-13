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
language="java"
pageEncoding="UTF-8"
buffer="256kb"
autoFlush="false"
errorPage="/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.constant.PlatformMessageConst"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.HumanBinaryNormalCardVo"
import = "jp.mosp.platform.human.action.HumanBinaryNormalCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputImageAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputFileAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)params.getVo();
String fileType = vo.getPltFileType();
%>
<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_COMMON_INFO_JSP %>" flush="false" />
<%
//履歴編集の場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
	// その他のファイルの場合
	if (fileType.equals(PlatformHumanConst.CODE_HUMAN_BINARY_FILE_TYPE_FILE)) {
%>
<div class="List">
	<table class="ListTable" id="tblActivateDate">
		<tr>
			<td class="TitleTd">登録済バイナリファイル</td>
			<td>	
				<button type="button" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, null, '<%= HumanBinaryOutputFileAction.CMD_NORAML_FILE %>');"><%= params.getName("Output") %></button>
			</td>
		</tr>
	</table>
</div>
<%
	} else {
%>
<img src="../srv/?cmd=<%= HumanBinaryOutputImageAction.CMD_NORAML_IMAGE %>" />
<%
	}
}
%>
<div class="List" id="divEdit">
	<table class="InputTable">
	<tr>
		<td class="TitleTd" ><span><%=params.getName("File", "Remarks") %></span></td>
		<td class="InputTd" id="txtFileRemark">
			<textarea maxlength="100" class="Name100TextArea" onkeyup="return isMaxlength(this)" rows="3" cols="50" id="txtFileRemark" name="txtFileRemark" ><%= HtmlUtility.escapeHTML(vo.getTxtFileRemark()) %></textarea>
		</td>
	</tr>
<%
// 履歴編集以外の場合
if (!vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)){
%>	
	<tr>		
		<td class="TitleTd" ><span class="RequiredLabel">*&nbsp;</span><span><%= params.getName("Target", "File") %></span></td>
		<td class="InputTd" id="inputFileName" colspan="6">
			<label for="<%= HumanBinaryNormalCardAction.PRM_FILE_BINARY_NORMAL %>"></label><input type="file" class="FileTextBox" id="<%= HumanBinaryNormalCardAction.PRM_FILE_BINARY_NORMAL %>" name="<%= HumanBinaryNormalCardAction.PRM_FILE_BINARY_NORMAL %>" size="90" />
			<br /><br />
			<label><%= params.getMessage(PlatformMessageConst.MSG_HUMAN_GENERAL_PICTURE_EXTENSION) %></label>
		</td>
	</tr>
<%
}
%>	
	</table>
</div>
<div class="Button">
<%
// 新規登録の場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
%>
	<button type="button" class="Name4Button" id="btnRegist" onclick="submitFormMulti(event, null, checkExtra, '<%= HumanBinaryNormalCardAction.CMD_INSERT %>')"><%=params.getName("Insert")%></button>
<%
} else {
%>
	<button type="button" class="Name4Button" id="btnRegist" onclick="submitRegist(event, null, null, '<%= HumanBinaryNormalCardAction.CMD_UPDATE %>')"><%= params.getName("Update") %></button>
	<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, null, null, '<%= HumanBinaryNormalCardAction.CMD_DELETE %>')"><%= params.getName("Delete") %></button>
<%
}
%>
</div>