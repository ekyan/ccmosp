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
import = "jp.mosp.platform.human.action.BasicCardAction"
import = "jp.mosp.platform.human.action.BasicListAction"
import = "jp.mosp.platform.human.action.ConcurrentCardAction"
import = "jp.mosp.platform.human.action.EntranceCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.RetirementCardAction"
import = "jp.mosp.platform.human.action.SuspensionCardAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.HumanInfoVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanInfoVo vo = (HumanInfoVo)params.getVo();
%>
<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_COMMON_INFO_JSP %>" flush="false" />
<div class="List">
	<table class="OverTable" id="tblBasicInfomation">
		<tr>
			<th colspan="4" class="ListTableTh" id="">
				<span class="TitleTh"><%= params.getName("PersonalBasisInformation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= BasicCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("History","Add") %>
					</button>&nbsp;
					<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= BasicListAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("History","List") %>
					</button>
				</span>
			</th>
		</tr>
	</table>
	<table class="UnderTable" id="tblBasicInfomation">
		<tr>
			<td rowspan="2" class="TitleTd" id="titleEmployeeName">
				<div><%= params.getName("FrontParentheses","Kana","BackParentheses") %></div><div><%= params.getName("FullName") %></div>
			</td>
			<td rowspan="2" class="InputTd" id="lblEmployeeName">
				<div><%= HtmlUtility.escapeHTML(vo.getLblEmployeeKana()) %></div><div><%= HtmlUtility.escapeHTML(vo.getLblEmployeeName()) %></div>
			</td>
			<td class="TitleTd" id="tdTitleWorkPlace"><%= params.getName("WorkPlace") %></td>
			<td class="InputTd" id="lblWorkPlaceName"><%= HtmlUtility.escapeHTML(vo.getLblWorkPlace()) %></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdTitleListBasic"><%= params.getName("EmploymentContract") %></td>
			<td class="InputTd" id="tdEmploymentName"><%= HtmlUtility.escapeHTML(vo.getLblEmployment()) %></td>
		</tr>
		<tr>
			<td class="TitleTd" id="titleSectionName"><%= params.getName("Section") %></td>
			<td class="InputTd" id="lblSectionName"><%= HtmlUtility.escapeHTML(vo.getLblSection()) %></td>
			<td class="TitleTd" id="titlePositionName"><%= params.getName("Position") %></td>
			<td class="InputTd" id="lblPositionName"><%= HtmlUtility.escapeHTML(vo.getLblPosition()) %></td>
		</tr>
<%
if (vo.getNeedPost()) {
%>			
		<tr>
			<td class="TitleTd" id="titlePostName"><%= params.getName("Post") %></td>
			<td class="InputTd" id="lblPostName"><%= HtmlUtility.escapeHTML(vo.getLblPost()) %></td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
}
%>	
	</table>
</div>
<div class="List">
	<table class="OverTable">
		<tr>
			<th colspan="2" class="ListTableTh" id=""><span class="TitleTh"><%= params.getName("JoinedInformation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= EntranceCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("Edit") %>
					</button>
				</span>
			</th>
		</tr>
	</table>
<%
if (vo.getLblEntranceDate().isEmpty() == false) {
%>
	<table class="UnderTable" id="tblEntrance">
		<tr>
			<td class="TitleTd" ><%= params.getName("Joined","Day") %></td>
			<td class="InputTd" id="tdEntranceDate"><%= HtmlUtility.escapeHTML(vo.getLblEntranceDate()) %></td>
			<td class="TitleTd" ><%= params.getName("WorkContinue","Year","Num") %></td>
			<td class="InputTd" id="tdYearsOfServiceDate"><%= HtmlUtility.escapeHTML(vo.getLblYearsOfService()) %></td>
		</tr>
<%
}
%>
	</table>
</div>
<div class="List">
	<table class="OverTable">
		<tr>
			<th colspan="6" class="ListTableTh" id=""><span class="TitleTh"><%= params.getName("ConcurrentInformation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ConcurrentCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("History") %>
					</button>
				</span>
			</th>
		</tr>
	</table>
<%
if (vo.getAryConcurrentStartDate().length > 0) {
%>
	<table class="UnderTable">
		<tr>
			<td class="TitleTd" id="thListNumber"></td>
			<td class="TitleTd" id="thListDate"><%= params.getName("Start","Day") %></td>
			<td class="TitleTd" id="thListDate"><%= params.getName("End","Day") %></td>
			<td class="TitleTd" id="thSectionAbbr"><%= params.getName("Section","Abbreviation") %></td>
			<td class="TitleTd" id="thPositionAbbr"><%= params.getName("Position","Abbreviation") %></td>
			<td class="TitleTd" id="thRemark"><%= params.getName("Remarks") %></td>
		</tr>
<%
}
for (int i = 0; i < vo.getAryConcurrentStartDate().length; i++) {
%>
		<tr id="addLeaveTr1">
			<td class="NumberTd" id=""><%= HtmlUtility.escapeHTML(String.valueOf(i + 1)) %></td>
			<td class="InputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryConcurrentStartDate(i)) %></td>
			<td class="InputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryConcurrentEndDate(i)) %></td>
			<td class="InputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryConcurrentSectionAbbr(i)) %></td>
			<td class="InputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryConcurrentPositionAbbr(i)) %></td>
			<td class="InputTd" id="tdConcurrentRemark"><%= HtmlUtility.escapeHTML(vo.getAryConcurrentRemark(i)) %></td>
		</tr>
<%
}
%>
	</table>
</div>
<div class="List">
	<table class="OverTable">
		<tr>
			<th colspan="5" class="ListTableTh" id=""><span class="TitleTh"><%= params.getName("RetirementLeaveInformation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= SuspensionCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("History") %>
					</button>
				</span>
			</th>
		</tr>
	</table>
<%
if (vo.getArySuspensionStartDate().length > 0) {
%>
	<table class="UnderTable">
		<tr>
			<td class="TitleTd" id="thListNumber"></td>
			<td class="TitleTd" id="thListDate"><%= params.getName("RetirementLeave","Start","Day") %></td>
			<td class="TitleTd" id="thListDate"><%= params.getName("RetirementLeave","End","Day") %></td>
			<td class="TitleTd" id="thListDate"><%= params.getName("RetirementLeave","End","Schedule","Day") %></td>
			<td class="TitleTd" id="thSuspensionReason"><%= params.getName("RetirementLeave","Reason") %></td>
		</tr>
<%
}
for (int i = 0; i < vo.getArySuspensionStartDate().length; i++) {
%>
		<tr>
			<td class="NumberTd"><%= HtmlUtility.escapeHTML(String.valueOf(i + 1)) %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getArySuspensionStartDate(i)) %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getArySuspensionEndDate(i)) %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getArySuspensionsScheduleEndDate(i)) %></td>
			<td class="InputTd" id="tdSuspensionReason"><%= HtmlUtility.escapeHTML(vo.getArySuspensionReason(i)) %></td>
		</tr>
<%
}
%>
	</table>
</div>
<div class="List">
	<table class="OverTable">
		<tr>
			<th colspan="3" class="ListTableTh" id="hRetire"><span class="TitleTh"><%= params.getName("RetirementOnInformation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= RetirementCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= params.getName("Edit") %>
					</button>
				</span>
			</th>
		</tr>
	</table>
<%
if (vo.getLblRetirementDate().isEmpty() == false) {
%>
	<table class="UnderTable">
		<tr>
			<td class="TitleTd" id="thListDate"><%= params.getName("RetirementOn","Day") %></td>
			<td class="TitleTd" id="thRetirementReason"><%= params.getName("RetirementOn","Reason") %></td>
			<td class="TitleTd" id="thRetirementDetail"><%= params.getName("Reason","Detail") %></td>
		</tr>
		<tr>
			<td class="InputTd" id="lblRetirementDate"><%= HtmlUtility.escapeHTML(vo.getLblRetirementDate()) %></td>
			<td class="InputTd" id="tdRetirementReason"><%= HtmlUtility.escapeHTML(vo.getLblRetirementReason()) %></td>
			<td class="InputTd" id="lblRetirementDetail"><%= HtmlUtility.escapeHTML(vo.getLblRetirementDetail()) %></td>
		</tr>
<%
}
%>
	</table>
</div>

<%
// 人事汎用管理区分毎に処理
for (String division : vo.getAryDivision()) {
	// 履歴の場合有効日取得
	String activeDate = vo.getDivisionItem(division);
	// 人事汎用履歴情報確認
	if(activeDate != null){
		// 履歴の場合有効日遷移
		params.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, activeDate);
	}
	// リクエストに人事汎用管理区分を設定(view.jspで用いる)
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION, division);
	// リクエストに表示区分(呼出元JSP)を設定(view.jspで用いる)
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW, "HumanInfo");
	// リクエストに人事汎用管理区分配列を設定
	params.addGeneralParam("humanDivisions",vo.getAryDivision());
	// 人事汎用管理区分毎にview.jspを読込
%>
	<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_VIEW_CONFIG_JSP %>" flush="false" />
<%
}
%>

<div class="MoveUpLink">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
