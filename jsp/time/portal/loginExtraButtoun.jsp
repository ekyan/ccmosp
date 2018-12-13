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
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.time.portal.bean.impl.PortalTimeCardBean"
import = "jp.mosp.platform.portal.action.AuthAction"
%><%
// MosP処理情報及びVOを取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
%>

<button type="submit" class="LoginButton" onclick="return startWorkLogin('<%= AuthAction.CMD_AUTHENTICATE %>')"> <%= params.getName("StartWork","Login") %></button>

<script language="Javascript">
<!--
/**
 * ログインリクエストを送信する。
 * @param コマンド
 * @return リクエスト送信可否(true：可、false：不可)
*/
function startWorkLogin(cmd) {
	// 暗号化要否確認
	if (getFormValue("needEncrypt") == "true") {
		setFormValue("hdnPass", encrypt(getFormValue("txtPassWord")));
	} else {
		setFormValue("hdnPass", getFormValue("txtPassWord"));
	}
	if (validate("", null, null)) {
		addParameter(document.form, "RecodeType" , "StartWork");
		return prepareSubmit(document.form, cmd );
	}
	return false;
	
}
//-->
</script>
