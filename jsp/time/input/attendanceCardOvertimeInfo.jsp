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
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblOvertimeHeader">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("OvertimeWorkInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblOvertime">
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertime"><%= HtmlUtility.escapeHTML(vo.getLblOvertime()) %></td>
			<td class="TitleTd" id="tdOvertimeIn"><%= params.getName("Legal","Inside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeIn"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeIn()) %></td>
			<td class="TitleTd" id="tdOvertimeOut"><%= params.getName("Legal","Outside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeOut"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeOut()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Midnight","Work","Time") %></td>
			<td class="InputTd" id="lblLateNightTime"><%= HtmlUtility.escapeHTML(vo.getLblLateNightTime()) %></td>
			<td class="TitleTd"><%= params.getName("Prescribed","Holiday","Work","Time") %></td>
			<td class="InputTd" id="lblSpecificWorkTime"><%=HtmlUtility.escapeHTML( vo.getLblSpecificWorkTimeIn()) %></td>
			<td class="TitleTd"><%= params.getName("Legal","Holiday","Work","Time") %></td>
			<td class="InputTd" id="lblLegalWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblLegalWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Reduced","Target","Time") %></td>
			<td class="InputTd" id="lblDecreaseTime"><%= HtmlUtility.escapeHTML(vo.getLblDecreaseTime()) %></td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	
	<!--
	<table class="HeaderInputTable">
		<tr>
			<th class="ListTableTh" colspan="10">
				<span class="TitleTh"><= params.getName("Allowance","Information") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="tblAllowance">
		<tr>
			<td class="TitleTd"><= params.getName("Allowance","No1") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance1", "pltAllowance1", vo.getPltAllowance1(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No2") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance2", "pltAllowance2", vo.getPltAllowance2(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No3") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance3", "pltAllowance3", vo.getPltAllowance3(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No4") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance4", "pltAllowance4", vo.getPltAllowance4(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No5") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance5", "pltAllowance5", vo.getPltAllowance5(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><= params.getName("Allowance","No6") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance6", "pltAllowance6", vo.getPltAllowance6(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No7") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance7", "pltAllowance7", vo.getPltAllowance7(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No8") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance8", "pltAllowance8", vo.getPltAllowance8(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No9") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance9", "pltAllowance9", vo.getPltAllowance9(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><= params.getName("Allowance","No10") %></td>
			<td class="InputTd">
				<= HtmlUtility.getSelectTag("Name2PullDown", "pltAllowance10", "pltAllowance10", vo.getPltAllowance10(),vo.getAryPltAllowance(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
-->
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, '', checkRegistTimes, '<%= AttendanceCardAction.CMD_APPLI %>')"><%= params.getName("Application") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnDraft" name="btnDraft" onclick="submitRegist(event, '', checkDraftTimes, '<%= AttendanceCardAction.CMD_DRAFT %>')"><%= params.getName("WorkPaper") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnDelete" onclick="submitRegist(event, null, null, '<%= AttendanceCardAction.CMD_DELETE %>')"><%= params.getName("Delete") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnCalc" name="btnCalc" onclick="submitRegist(event, '', checkRegistTimes, '<%= AttendanceCardAction.CMD_CALC %>')"><%= params.getName("TrialCalc") %></button>
			</td>
		</tr>
	</table>
