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
buffer       = "16kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.base.PlatformVo"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.portal.action.LogoutAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PlatformVo vo = (PlatformVo)params.getVo();
%>
<div class="MenuButtonBar">
<%
if (params.getUser() != null) {
%>
	<button onclick="doSubmit(document.form, '<%= LogoutAction.CMD_LOGOUT %>')" class="LogoutButton" type="button"><%= params.getName("Logout") %></button>
<%
}
%>
</div>
<div class="LogoBar">
	<img class="Logo" src="<%= params.getApplicationProperty("LogoImage") %>" />
</div>
<div class="TitleBar">
	<span id="lblTitle"><%= params.getApplicationProperty(MospConst.APP_TITLE) %>&nbsp;</span>
	<span id="lblVersion"><%= params.getApplicationProperty(MospConst.APP_VERSION) %></span>
<%
if (params.getUser() != null) {
%>
	<span id="lblUserName"><%= HtmlUtility.escapeHTML(params.getUser().getUserName()) %></span>
<%
}
if (params.getApplicationPropertyBool(PlatformConst.APP_FORUM_LINK_DISABLED) == false) {
%>
	<span class="Forum"><a href="http://www.mospuser.com/" target="_blank"><%= HtmlUtility.escapeHTML(params.getName("QuestionAndAnswer")) %></a></span>
<%
}
%>
</div>
