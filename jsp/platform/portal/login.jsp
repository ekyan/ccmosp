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
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.platform.portal.action.AuthAction"
import = "jp.mosp.platform.portal.vo.LoginVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
LoginVo vo = (LoginVo)params.getVo();
boolean needEncrypt = true;
if (params.getProperties().getAddonProperties().get("Ldap") != null && params.getProperties().getAddonProperties().get("Ldap").isAddonValid()) {
	needEncrypt = false;
}
String loginImagePath = params.getApplicationProperty(PlatformConst.APP_LOGIN_IMAGE_PATH);
%>
<%
if (loginImagePath != null && loginImagePath.isEmpty() == false) {
%>
<div style="margin: auto; width: 980px; height: 250px;">
	<img src="<%= loginImagePath %>" alt="MosPV4" />
</div>
<%
}
%>
<div>
	<table class="LoginTable">
		<tr>
			<td class="LoginTitle">
				<label for="txtUserId"><%= params.getName("User") %><%= params.getName("Id") %></label><%= params.getName("Colon") %>
			</td>
			<td class="LoginInput">
				<input type="text" class="LoginIdTextBox" id="txtUserId" name="txtUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtUserId()) %>" />
			</td>
		<tr>
			<td class="LoginTitle">
				<label for="txtPassWord"><%= params.getName("Password") %></label><%= params.getName("Colon") %>
			</td>
			<td class="LoginInput">
				<input type="password" class="LoginPassTextBox" id="txtPassWord" value="" />
			</td>
		</tr>
	</table>
</div>
<div class="Button" id="divLoginButton">
	<button type="submit" class="LoginButton" onclick="return login('<%= AuthAction.CMD_AUTHENTICATE %>')"><%= params.getName("Login") %></button>
</div>
<div>
	<table>
		<tr>
			<td>
				<div class="CopyLabel">
					<%= params.getName("CopyRight") %>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="hdnPass" name="txtPassWord" value="" />
	<input type="hidden" id="needEncrypt" value="<%= needEncrypt %>" />
</div>
