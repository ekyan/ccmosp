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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.management.vo.ApprovalCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalCardVo vo = (ApprovalCardVo)params.getVo();
%>

		<table class="InputTable" id="approvalCard_tblRest">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("RestTimeInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdRestTitle"><%= params.getName("RestTime","Time") %></td>
				<td class="InputTd" id="lblRestTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblRestTime()) %></td>
				<td class="TitleTd" id="tdRestTitle1"><%= params.getName("Rest1") %></td>
				<td class="InputTd" id="lblRestTime1"><%= HtmlUtility.escapeHTML(vo.getLblRestTime1()) %></td>
				<td class="TitleTd" id="tdRestTitle2"><%= params.getName("Rest2") %></td>
				<td class="InputTd" id="lblRestTime2"><%= HtmlUtility.escapeHTML(vo.getLblRestTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMidnightRestTitle"><%= params.getName("Midnight","RestTime","Time") %></td>
				<td class="InputTd" id="lblLateRestTime"><%= HtmlUtility.escapeHTML(vo.getLblNightRestTime()) %></td>
				<td class="TitleTd" id="tdRestTitle3"><%= params.getName("Rest3") %></td>
				<td class="InputTd" id="lblRestTime3"><%= HtmlUtility.escapeHTML(vo.getLblRestTime3()) %></td>
				<td class="TitleTd" id="tdRestTitle4"><%= params.getName("Rest4") %></td>
				<td class="InputTd" id="lblRestTime4"><%= HtmlUtility.escapeHTML(vo.getLblRestTime4()) %></td>
			</tr>
			<tr>
				<td class="Blank" colspan="2"  id="blankRest"></td>
				<td class="TitleTd" id="tdRestTitle5"><%= params.getName("Rest5") %></td>
				<td class="InputTd" id="lblRestTime5"><%= HtmlUtility.escapeHTML(vo.getLblRestTime5()) %></td>
				<td class="TitleTd" id="tdRestTitle6"><%= params.getName("Rest6") %></td>
				<td class="InputTd" id="lblRestTime6"><%= HtmlUtility.escapeHTML(vo.getLblRestTime6()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdPublicTime"><%= params.getName("Official","GoingOut","Time") %></td>
				<td class="InputTd" id="lblPublicTime"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime()) %></td>
				<td class="TitleTd" id="tdPublicTime1"><%= params.getName("Official","GoingOut","No1") %></td>
				<td class="InputTd" id="lblPublicTime1"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime1()) %></td>
				<td class="TitleTd" id="tbPublicTime2"><%= params.getName("Official","GoingOut","No2") %></td>
				<td class="InputTd" id="lblPublicTime2"><%= HtmlUtility.escapeHTML(vo.getLblPublicTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut","Time") %></td>
				<td class="InputTd" id="lblPrivateTime"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime()) %></td>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut1") %></td>
				<td class="InputTd" id="lblPrivateTime1"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime1()) %></td>
				<td class="TitleTd"><%= params.getName("PrivateGoingOut2") %></td>
				<td class="InputTd" id="lblPrivateTime2"><%= HtmlUtility.escapeHTML(vo.getLblPrivateTime2()) %></td>
			</tr>
		</table>
		
<%
// 分単位休暇有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_MINUTELY_HOLIDAY)){
%>
		
		<table class="InputTable" id="approvalCard_tblMinutelyHolidayA">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("MinutelyHolidayAInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMinutelyHolidayATitle"><%= params.getName("MinutelyHolidayA","Time") %>   </td>
				<td class="InputTd" id="tdMinutelyHolidayAInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA1Title"><%= params.getName("MinutelyHolidayAAbbr","No1") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA1Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime1()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA2Title"><%= params.getName("MinutelyHolidayAAbbr","No2") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA2Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdAllMinutelyHolidayATitle"><%= params.getName("MinutelyHolidayA","AllTime") %>   </td>
				<td class="InputTd" id="tdAllMinutelyHolidayAInput"><%= HtmlUtility.escapeHTML(vo.getLblAllMinutelyHolidayA()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA3Title"><%= params.getName("MinutelyHolidayAAbbr","No3") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA3Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime3()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayA4Title"><%= params.getName("MinutelyHolidayAAbbr","No4") %></td>
				<td class="InputTd" id="tdMinutelyHolidayA4Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayATime4()) %></td>
			</tr>
		</table>
		<table class="InputTable" id="approvalCard_tblMinutelyHolidayB">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("MinutelyHolidayBInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd" id="tdMinutelyHolidayBTitle"><%= params.getName("MinutelyHolidayB","Time") %>   </td>
				<td class="InputTd" id="tdMinutelyHolidayBInput"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB1Title"><%= params.getName("MinutelyHolidayBAbbr","No1") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB1Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime1()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB2Title"><%= params.getName("MinutelyHolidayBAbbr","No2") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB2Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime2()) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdAllMinutelyHolidayBTitle"><%= params.getName("MinutelyHolidayB","AllTime") %>   </td>
				<td class="InputTd" id="tdAllMinutelyHolidayBInput"><%= HtmlUtility.escapeHTML(vo.getLblAllMinutelyHolidayB()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB3Title"><%= params.getName("MinutelyHolidayBAbbr","No3") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB3Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime3()) %></td>
				<td class="TitleTd" id="tdMinutelyHolidayB4Title"><%= params.getName("MinutelyHolidayBAbbr","No4") %></td>
				<td class="InputTd" id="tdMinutelyHolidayB4Time"><%= HtmlUtility.escapeHTML(vo.getLblMinutelyHolidayBTime4()) %></td>
			</tr>
		</table>
	
<%
}
%>		
