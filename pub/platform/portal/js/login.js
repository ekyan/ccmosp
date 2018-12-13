/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 画面読込時追加処理。
 */
function onLoadExtra(){
	// フォーカス設定
	if (getFormValue("txtUserId") == "") {
		setFocus("txtUserId");
	} else {
		setFocus("txtPassWord");
	}
}

/**
 * ログインリクエストを送信する。
 * @param コマンド
 * @return リクエスト送信可否(true：可、false：不可)
*/
function login(cmd) {
	// 暗号化要否確認
	if (getFormValue("needEncrypt") == "true") {
		setFormValue("hdnPass", encrypt(getFormValue("txtPassWord")));
	} else {
		setFormValue("hdnPass", getFormValue("txtPassWord"));
	}
	if (validate("", null, null)) {
		return prepareSubmit(document.form, cmd);
	}
	return false;
}
