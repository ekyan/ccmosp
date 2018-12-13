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
 * パスワード文字種の妥当性が確認できなかった場合のメッセージコード。
 */
var MSG_CHAR_PASSWORD_ERR = "PFW9116";

/**
 * 画面読込時追加処理を行う。
 */
function onLoadExtra(){
	// フォーカス設定
	setFocus("txtOldPassword");
	// 値初期化(パスワードを記憶している場合に設定される値を初期化)
	setFormValue("txtOldPassword", "");
}

/**
 * パスワード確認を行う。
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkPassword(aryMessage, event) {
	// 最低文字数確認
	if (typeof(jsMinPassword) != "undefined" && jsMinPassword != "") {
		checkMinLength("txtNewPassword", jsMinPassword, aryMessage);
	}
	// 文字種確認
	if (typeof(jsCharPassword) != "undefined" && jsCharPassword != "") {
		checkCharPassword("txtNewPassword", jsCharPassword, aryMessage);
	}
	// パスワード設定
	setPassword();
}

/**
 * 文字形式確認を行う。
 * @param target     確認対象(StringあるいはObject)
 * @param reg        正規表現
 * @param aryMessage メッセージ配列
 */
function checkCharPassword(target, reg, aryMessage) {
	if (!checkRegexNoMsg(target, reg)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_CHAR_PASSWORD_ERR , ""));
	}
}

/**
 * パスワードを設定する。
 */
function setPassword() {
	setFormValue("hdnOldPassword"    , encrypt(getFormValue("txtOldPassword"    )));
	setFormValue("hdnNewPassword"    , encrypt(getFormValue("txtNewPassword"    )));
	setFormValue("hdnConfirmPassword", encrypt(getFormValue("txtConfirmPassword")));
}

