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
 * サーブレットURL
 */
var URL_SRV = "../srv/";

/**
 * 画面背景色。
 */
var COLOR_TABLE_BACKGROUND = "#eeeeee";
var COLOR_MAINMENU = "lightpink";
var COLOR_TABLE_ROW1   = "#ffffff";
var COLOR_TABLE_ROW2   = "#eeeeee";
var COLOR_TABLE_SELECT = "pink";
var COLOR_FIELD_NORMAL = "#f8f8f8";

/**
 * 文字色。
 */
var COLOR_FONT_NORMAL  = "black";
var COLOR_FONT_DISABLE = "#8b8b7a";

/**
 * フィールド色。
 */
var COLOR_FIELD_NORMAL = "#ffffff";
var COLOR_FIELD_ERROR  = "pink";
var COLOR_FIELD_CHANGE = "orange";

/**
 * 問合中フラグ。
 */
var inquiring = true;

/**
 * メッセージ表示件数。
 */
var messageCount = 10;

/**
 * 入力チェックエラーメッセージ。
 */
var MSG_REQUIRED = "PFW0102";

/**
 * クライアントエラーメッセージ。
 */
var MSG_CLIENT_ERROR = "PFW0108";

/**
 * チェックボックス必須エラーメッセージ。
 */
var MSG_CHECKBOX_ERROR = "PFW0103";

/**
 * 入力エラーメッセージ。
 */
var MSG_INPUT_ERROR = "PFW0109";

/**
 * 半角英数字チェックエラーメッセージ。
 */
var MSG_ALP_NUM_CHECK_ERROR = "PFW0110";

/**
 * 半角英数字チェックエラーメッセージ。
 */
var MSG_ALP_NUM_CHECK_ERROR_AMP = "PFW0111";

/**
 * 数字チェックエラーメッセージ。
 */
var MSG_NUMBER_CHECK_ERROR = "PFW0112";

/**
 * 数字チェックエラーメッセージ。
 */
var MSG_NUMBER_CHECK_ERROR_AMP = "PFW0113";

/**
 * 入力形式エラーメッセージ。
 */
var MSG_INPUT_FORM_ERROR = "PFW0114";

/**
 * 入力形式エラーメッセージ。
 */
var MSG_INPUT_FORM_ERROR_AMP = "PFW0115";

/**
 * 日付チェックエラーメッセージ。
 */
var MSG_DATE_CHECK_ERROR = "PFW0116";

/**
 * 日付（年）チェックエラーメッセージ。
 */
var MSG_YEAR_CHECK_ERROR = "PFW0117";

/**
 * 日付（月）チェックエラーメッセージ。
 */
var MSG_MONTH_CHECK_ERROR = "PFW0118";

/**
 * 日付（日）チェックエラーメッセージ。
 */
var MSG_DAY_CHECK_OUTRANGE = "PFW0119";

/**
 * 日付（日）チェックエラーメッセージ。
 */
var MSG_DAY_CHECK_ERROR = "PFW0120";

/**
 * 最低文字数の妥当性が確認できなかった場合のメッセージコード。
 */
var MSG_MIN_LENGTH_ERR = "PFW0122";

/**
 * 最大文字数の妥当性が確認できなかった場合のメッセージコード。
 */
var MSG_MAX_LENGTH_ERR = "PFW0123";

/**
 * バイト数(表示上)の妥当性が確認できなかった場合のメッセージコード。
 */
var MSG_BYTE_LENGTH_ERR = "PFW0126";

/**
 * タグ名(INPUT)。
 */
var TAG_INPUT = "INPUT";

/**
 * タグ名(SELECT)。
 */
var TAG_SELECT = "SELECT";

/**
 * タグ名(TEXTAREA)。
 */
var TAG_TEXTAREA = "TEXTAREA";

/**
 * タグ名(LABEL)。
 */
var TAG_LABEL = "LABEL";

/**
 * タグ名(TR)。
 */
var TAG_TR = "TR";

/**
 * タグ名(TD)。
 */
var TAG_TD = "TD";

/**
 * タグ名(A)。
 */
var TAG_A = "A";

/**
 * 入力タイプ(text)。
 */
var INPUT_TYPE_TEXT = "text";

/**
 * 入力タイプ(checkbox)。
 */
var INPUT_TYPE_CHECK_BOX = "checkbox";

/**
 * 入力タイプ(hidden)。
 */
var INPUT_TYPE_HIDDEN = "hidden";

/**
 * 属性(for)。
 */
var ATT_FOR = "for";

/**
 * リクエスト送信準備をする。<br>
 * @param objForm フォームオブジェクト
 * @param cmd     コマンド
 * @return リクエスト送信可否(true：送信可、false：送信不可)
 */
function prepareSubmit(objForm, cmd) {
	// 問合中フラグ確認
	if (inquiring) {
		return false;
	}
	// パラメータ追加
	addParameter(objForm, "cmd"     , cmd    );
	addParameter(objForm, "procSeq" , procSeq);
	// 送信準備
	objForm.target = "_self";
	objForm.method = "Post";
	objForm.action = URL_SRV;
	// 問合中フラグ更新
	inquiring = true;
	return true;
}


/**
 * リクエストを送信する。<br>
 * @param objForm フォームオブジェクト
 * @param cmd     コマンド
 * @return 無し
 * @throws 実行時例外
 */
function doSubmit(objForm, cmd) {
	// リクエスト送信準備
	if (prepareSubmit(objForm, cmd)) {
		// submit
		objForm.submit();
	}
}

/**
 * リクエストを送信する。<br>
 * 問合中フラグを更新しない。<br>
 * ファイル出力リクエストを送信する際に用いる。<br>
 * @param objForm フォームオブジェクト
 * @param cmd     コマンド
 */
function doSubmitForFile(objForm, cmd) {
	// リクエスト送信準備
	if (prepareSubmit(objForm, cmd)) {
		// 問合中フラグ更新
		inquiring = false;
		// submit
		objForm.submit();
	}
}

/**
 * リクエストを送信する。<br>
 * ファイルアップロード時等に用いる。<br>
 * @param objForm フォームオブジェクト
 * @param cmd     コマンド
 * @return 無し
 * @throws 実行時例外
 */
function doSubmitForMulti(objForm, cmd) {
	// リクエスト送信準備
	if (prepareSubmit(objForm, cmd)) {
		// ファイルアップロード対応
		objForm.encoding = "multipart/form-data"
		// submit
		objForm.submit();
	}
}

/**
 * 操作対象オブジェクトを取得する。
 * @param target 取得対象(StringあるいはObject)
 * @return 操作対象オブジェクト
 */
function getObject(target) {
	var objTarget = target;
	if (typeof target == "string") {
		objTarget = document.getElementById(target);
	}
	return objTarget;
}

/**
 * クラス名で要素群を取得する。<br>
 * @param className 取得対象クラス名
 * @return 要素群
 */
function getElementsByClass(className) {
	// Firefoxの場合
	if (document.getElementsByClassName) {
		return document.getElementsByClassName(className);
	}
	// Internet Explorerの場合
	if (document.all) {
		var classElements = new Array();
		var allElements = document.all;
		var elementsLength = allElements.length;
		for (var i = 0; i < elementsLength; i++) {
			if (allElements[i].className == className) {
				classElements.push(allElements[i]);
			}
		}
		return classElements;
	}
	// 上記の両ブラウザに該当しない場合
	return new Array();
}

/**
 * 検索対象からタグ名で要素群を取得する。<br>
 * @param target  検索対象(StringあるいはObject)
 * @param tagName 取得対象タグ名
 * @return 要素群
 */
function getElementsByTagName(target, tagName) {
	// 検索対象オブジェクト取得
	var objTarget = getObject(target);
	if (objTarget == null) {
		return new Array();
	}
	// 検索対象オブジェクト内要素群取得
	return objTarget.getElementsByTagName(tagName);
}

/**
 * 検索対象からタグ名及び要素名で要素を取得する。<br>
 * 検索対象から取得対象タグ名の要素を抜き出し、
 * 最初に見つかった取得対象要素名の要素を返す。<br>
 * 対象が存在しない場合は、nullを返す。<br>
 * @param target  検索対象(StringあるいはObject)
 * @param tagName 取得対象タグ
 * @param name    取得対象名
 * @return 要素
 */
function getElementByName(target, tagName, name) {
	// 検索対象内にある取得対象タグ名の要素群を取得
	var nodeList = getElementsByTagName(target, tagName);
	if (nodeList == null) {
		return null;
	}
	var nodeListLength = nodeList.length;
	// 取得対象名の要素を確認
	for (var i = 0; i < nodeListLength; i++) {
		if (nodeList.item(i).name == name) {
			return nodeList.item(i);
		}
	}
	return null;
}

/**
 * 背景色を設定する。
 * @param target 設定対象(StringあるいはObject)
 * @param color  設定色
 */
function setBgColor(target, color) {
	var objTarget = getObject(target);
	if (objTarget == null || objTarget.style == null) {
		return;
	}
	if (objTarget.type == null) {
		objTarget.style.backgroundColor = color;
		return;
	}
	switch (objTarget.type) {
		case "text":
			objTarget.style.backgroundColor = color;
			break;
		case "select-one":
			objTarget.style.backgroundColor = color;
			var optionsLength = objTarget.options.length;
			for (var i = 0; i < optionsLength; i++) {
				objTarget.options[i].style.backgroundColor = color;
			}
			break;
		default:
			objTarget.style.backgroundColor = color;
	}
}

/**
 * 文字色を設定する。
 * @param target   設定対象(StringあるいはObject)
 * @param strColor 設定色
 */
function setColor(target, strColor) {
	var objTarget = getObject(target);
	if (objTarget == null || objTarget.style == null) {
		return;
	}
	if (objTarget.type == null) {
		objTarget.style.color = strColor;
		return;
	}
	switch (objTarget.type) {
		case "text":
			objTarget.style.color = strColor;
			break;
		case "select-one":
			objTarget.style.color = strColor;
			var objTargetLength = objTarget.options.length;
			for (var i = 0; i < objTargetLength; i++) {
				objTarget.options[i].style.color = strColor;
			}
			break;
		default:
			objTarget.style.color = strColor;
	}
}

/**
 * 一覧背景色設定
 * @param strTargetTable 設定対象テーブルオブジェクトID
 * @return 無し
 * @throws 実行時例外
 */
function setTableColor(strTargetTable) {
	var objTable = getObject(strTargetTable);
	if (objTable == null) {
		return;
	}
	var objTarget = null;
	var numHeader = 0;
	var numFooter = 0;
	if (objTable.tHead) {
		numHeader = objTable.tHead.rows.length;
	}
	if (objTable.tFoot) {
		numFooter = objTable.tFoot.rows.length;
	}
	var rowslength = objTable.rows.length - numFooter;
	for (var i = numHeader; i < rowslength; i ++) {
		objTable.rows[i].bgColor = i % 2 == 0 ? COLOR_TABLE_ROW2 : COLOR_TABLE_ROW1;
		objTable.rows[i].onmouseover = function() {this.bgColor = COLOR_TABLE_SELECT;};
		objTable.rows[i].onmouseout  = function() {this.bgColor = this.rowIndex % 2 == 0 ? COLOR_TABLE_ROW2 : COLOR_TABLE_ROW1;};
	}
}

/**
 * フォーカス設定
 * @param target 設定対象(StringあるいはObject)
 * @return 無し
 * @throws 実行時例外
 */
function setFocus(target) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	switch (objTarget.type) {
		case "text":
			objTarget.focus();
			if (objTarget.value != "") {
				objTarget.select();
			}
			break;
		case "select-one":
			objTarget.focus();
			break;
		default:
			objTarget.focus();
	}
}

/**
 * 内容を設定する。
 * @param target   設定対象(StringあるいはObject)
 * @param strInner 設定内容
 */
function setInnerHtml(target, strInner) {
	var objTarget = getObject(target);
	if (objTarget != null) {
		objTarget.innerHTML = strInner;
	}
}

/**
 * 内容を取得する。
 * @param target 設定対象(StringあるいはObject)
 * @return 設定されている内容
 */
function getInnerHtml(target) {
	var objTarget = getObject(target);
	if (objTarget != null) {
		return objTarget.innerHTML;
	}
}

/**
 * フォーム入力値設定
 * @param target   取得対象(StringあるいはObject)
 * @param strValue 設定値
 * @return 無し
 * @throws 実行時例外
 */
function setFormValue(target, strValue) {
	var objTarget = getObject(target);
	if (objTarget == null || objTarget.type == null) {
		return;
	}
	switch (objTarget.type) {
		case "select-one":
			if (objTarget.length > 0) {
				objTarget.value = objTarget.options[0].value;
			}
			objTarget.value = strValue;
			break;
		default:
			objTarget.value = strValue;
	}
}

/**
 * フォーム入力値取得
 * @param target 取得対象(StringあるいはObject)
 * @return 入力値
 * @throws 実行時例外
 */
function getFormValue(target) {
	var objTarget = getObject(target);
	if (objTarget != null) {
		return objTarget.value;
	}
	return "";
}

/**
 * フィールド長設定
 * @param target    設定対象(StringあるいはObject)
 * @param numLength フィールド長
 * @return 無し
 * @throws 実行時例外
 */
function setMaxLengthNumber(target, numLength) {
	var	objTarget = getObject(target);
	if (objTarget == null || objTarget.maxLength == null) {
		return;
	}
	objTarget.maxLength = numLength;
}

/**
 * 枠線(下)の幅を設定する。
 * @param target   設定対象(StringあるいはObject)
 * @param strWidth 設定幅(単位含：10px等)
 */
function setBorderBottomWidth(target, strWidth) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	objTarget.style.borderBottomWidth = strWidth;
}

/**
 * 枠線(下)の幅を取得する。
 * @param target 取得対象(StringあるいはObject)
 * @return 枠線(下)の幅
 */
function getBorderBottomWidth(target) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return null;
	}
	return objTarget.style.borderBottomWidth;
}

/**
 * 有効/無効設定
 * @param target   設定対象(StringあるいはObject)
 * @param disabled 有効/無効(true：無効、false：有効)
 * @return 無し
 * @throws 実行時例外
 */
function setDisabled(target, disabled) {
	var objTarget = getObject(target);
	if (objTarget != null && objTarget.disabled != null) {
		objTarget.disabled = disabled;
	}
}

/**
 * 読取専用設定
 * @param target     設定対象(StringあるいはObject)
 * @param isReadOnly 読取専用フラグ(true：読取専用、false：入力可能)
 * @return 無し
 * @throws 実行時例外
 */
function setReadOnly(target, isReadOnly) {
	var objTarget = getObject(target);
	if (isReadOnly) {
		objTarget.blur();
		window.focus();
	}
	switch (objTarget.type) {
		case "text":
			if (isReadOnly) {
				objTarget.readOnly = "readonly";
			} else {
				objTarget.readOnly = "";
			}
			break;
		case "select-one":
		case "checkbox":
		case "button":
		case "radio":
			objTarget.disabled = isReadOnly;
			break;
		default:
	}
	if (isReadOnly) {
		setColor(target, COLOR_FONT_DISABLE);
	} else {
		setColor(target, COLOR_FONT_NORMAL);
	}
}

/**
 * 可視設定
 * @param target    設定対象(StringあるいはObject)
 * @param isVisible 設定内容
 * @return 無し
 * @throws 実行時例外
 */
function setVisibility(target, isVisible) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	if (isVisible) {
		objTarget.style.visibility = "visible";
	} else {
		objTarget.style.visibility = "hidden";
	}
}

/**
 * 非表示(style.display = "none")にする。
 * @param target 設定対象(StringあるいはObject)
 * @return 無し
 * @throws 実行時例外
 */
function setDisplayNone(target) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	objTarget.style.display = "none";
}

/**
 * パラメータを追加する。
 * @param objForm 追加先オブジェクト
 * @param id      追加パラメータID
 * @param value   追加パラメータ値
 */
function addParameter(objForm, id, value) {
	// エレメント存在確認
	if (document.getElementById(id) != null) {
		document.getElementById(id).value = value;
		return;
	}
	// エレメント生成
	var element = document.createElement(TAG_INPUT);
	element.type = INPUT_TYPE_HIDDEN;
	element.id = id;
	element.name = id;
	element.value = value;
	// エレメント追加
	objForm.appendChild(element);
}

/**
 * 下位オブジェクトを全て削除する。
 * @param target  削除対象オブジェクト(StringあるいはObject)
 * @param tagName 削除対象下位オブジェクトタグ
 */
function deleteLowerObjects(target, tagName) {
	// 作成対象上位オブジェクト取得
	var objNode = getObject(target);
	// 対象ノードリスト取得
	var objNodeList = getElementsByTagName(target, tagName);
	if (objNodeList == null) {
		return;
	}
	// 削除
	var listSize = objNodeList.length;
	for (var i = 0; i < listSize; i++) {
		objNode.removeChild(objNodeList.item(listSize - i - 1));
	}
}

/**
 * 下位オブジェクトを作成する。
 * @param target  作成対象上位オブジェクト(StringあるいはObject)
 * @param tagName 作成対象下位オブジェクトタグ
 * @return 作成下位オブジェクト
 */
function createLowerObjects(target, tagName) {
	// 作成対象上位オブジェクト取得
	var objNode = getObject(target);
	// 作成対象上位オブジェクト確認
	if (objNode == null) {
		return;
	}
	// 作成対象下位オブジェクト作成
	var objNew = document.createElement(tagName);
	// 作成オブジェクト追加
	objNode.insertBefore(objNew, null);
	return objNew;
}

/**
 * セレクトボックスのオプションを取得する。<br>
 * @param target 取得対象セレクトボックス(StringあるいはObject)
 * @return セレクトボックスのオプション
 */
function getSelectOptions(target) {
	// プルダウンオブジェクト取得
	var objSelect = getObject(target);
	// プルダウンオプション取得
	return objSelect.options;
}

/**
 * セレクトボックスのオプション長を取得する。<br>
 * @param target 取得対象セレクトボックス(StringあるいはObject)
 * @return セレクトボックスのオプション長
 */
function getSelectOptionLength(target) {
	// プルダウンオプション長取得
	return getSelectOptions(target).length;
}

/**
 * セレクトボックスを選択する。<br>
 * @param target 選択対象セレクトボックス(StringあるいはObject)
 * @param value  選択値
 */
function selectOption(target, value) {
	// オプション取得
	var options = getSelectOptions(target);
	var optionsLength = options.length;
	// 選択値のオプションを選択
	for (var i = 0; i < optionsLength; i++) {
		if (options[i].value == value) {
			options[i].selected = true;
			break;
		}
	}
}

/**
 * セレクトボックスを全選択する。
 * @param target 処理対象セレクトボックス(StringあるいはObject)
 */
function selectAllOptions(target) {
	var options = getSelectOptions(target);
	var optionsLength = options.length;
	for (var i = 0; i < optionsLength; i++) {
		options[i].selected = true;
	}
}

/**
 * セレクトボックスから選択されたオプションを除く。<br>
 * @param target 処理対象セレクトボックス(StringあるいはObject)
 */
function removeSelectedOption(target) {
	var options = getSelectOptions(target);
	for (var i = options.length - 1; i >= 0; i--) {
		if (options[i].selected) {
			options[i] = null;
		}
	}
}

/**
 * セレクトボックスのオプションを設定する。
 * @param target      設定対象セレクトボックス(StringあるいはObject)
 * @param optionArray 設定オプション配列(1列目：値、2列目：表示内容)
 */
function setSelectOptions(target, optionArray) {
	// オプション取得
	var options = getSelectOptions(target);
	// オプション初期化
	for (var i = options.length - 1; i >= 0; i--) {
		options[i] = null;
	}
	// 設定オプション配列確認
	if (optionArray == null) {
		return;
	}
	var optionArrayLength = optionArray.length;
	// オプション設定
	for (var i = 0; i < optionArrayLength; i++) {
		options[i] = new Option(optionArray[i][1], optionArray[i][0]);
	}
}

/**
 * セレクトボックスのオプションを移動させる。<br>
 * @param targetFrom 移動元セレクトボックス(StringあるいはObject)
 * @param targetTo   移動先セレクトボックス(StringあるいはObject)
 */
function moveSelectOptions(targetFrom, targetTo) {
	// オプション取得
	var optionFrom = getSelectOptions(targetFrom);
	var optionTo = getSelectOptions(targetTo);
	var optionFromLength = optionFrom.length;
	// 移動先セレクトボックスにオプションを追加
	for (var i = 0; i < optionFromLength; i++) {
		if (optionFrom[i].selected) {
			optionTo[optionTo.length] = new Option(optionFrom[i].text, optionFrom[i].value);
		}
	}
	// 移動元セレクトボックスのオプションを除去
	removeSelectedOption(targetFrom);
}

/**
 * チェックボックス等のチェックを操作する。<br>
 * @param target  設定対象チェックボックス
 * @param checked チェック要否(true：チェック、false：チェック外し)
 */
function checkableCheck(target, checked) {
	var objTarget = getObject(target);
	if (objTarget != null && objTarget.checked != null) {
		objTarget.checked = checked;
	}
}

/**
 * チェックボックス等がチェックされているかを確認する。<br>
 * @param target  設定対象チェックボックス
 * @return チェックされているか(true：チェックされている、false：チェックされていない)
 */
function isCheckableChecked(target) {
	var objTarget = getObject(target);
	if (objTarget != null && objTarget.checked != null && objTarget.checked == true) {
		return true;
	}
	return false;
}

/**
 * ラジオボタンの選択値を取得する。<br>
 * 選択されていない場合は、空白文字列を返す。<br>
 * @param targetName ラジオボタン名
 * @return ラジオボタン選択値
 */
function getRadioSelectedValue(targetName) {
	// ラジオボタン要素リスト取得
	var elements = document.getElementsByName(targetName);
	var elementsLength = elements.length;
	// 選択されている要素を確認
	for (var i = 0; i < elementsLength; i++) {
		if (elements[i].checked) {
			return elements[i].value;
		}
	}
	return "";
}

/**
 * 前後の空白を除去する。<br>
 * @return 空白除去後の文字列
 */
function trim(value) {
	return new String(value).replace(/^[ 　\t]+|[ 　\t]+$/g, "");
}

/**
 * 前後の空白、タブ、改行を除去する。<br>
 * @return 空白、タブ、改行除去後の文字列
 */
function trimAll(value) {
	return new String(value).replace(/^[ 　\t\r\n]+|[ 　\t\r\n]+$/g, "");
}


/**
 * イベント発生元オブジェクトを取得する。
 * @param event イベントオブジェクト
 * @return イベント発生オブジェクト
 */
function getSrcElement(event) {
	if (window.event != null) {
		return window.event.srcElement;
	} else if (event.target != null) {
		return event.target;
	}
	return null;
}

/**
 * クリックイベントハンドラを設定する。
 * @param target 設定対象(StringあるいはObject)
 * @param func   設定ファンクション又はスクリプト
 */
function setOnClickHandler(target, func) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return null;
	}
	objTarget.onclick = func;
}

/**
 * マウスオーバーイベントハンドラを設定する。
 * @param target 設定対象(StringあるいはObject)
 * @param func   設定ファンクション又はスクリプト
 */
function setOnMouseOverHandler(target, func) {
	getObject(target).onmouseover = func;
}

/**
 * マウスアウトイベントハンドラを設定する。
 * @param target 設定対象(StringあるいはObject)
 * @param func   設定ファンクション又はスクリプト
 */
function setOnMouseOutHandler(target, func) {
	getObject(target).onmouseout = func;
}

/**
 * オンチェンジイベントハンドラを設定する。
 * @param target 設定対象(StringあるいはObject)
 * @param func   設定ファンクション又はスクリプト
 */
function setOnChangeHandler(target, func) {
	getObject(target).onchange = func;
}

/**
 * メッセージ表示(alert)
 * @param aryMessage メッセージ配列
 * @return 無し
 * @throws 実行時例外
 */
function showMessage(aryMsg) {
	strMsg = "";
	var aryMsgLength = aryMsg.length;
	for (var i = 0; i < aryMsgLength; i++) {
		// メッセージ表示件数確認
		if (i >= messageCount) {
			break;
		}
		// メッセージ追加
		strMsg += aryMsg[i];
		if (i != aryMsg.length - 1) {
			strMsg += "\n";
		}
	}
	// メッセージ表示
	alert(strMsg);
}

/**
 * メッセージを取得する。
 * @param key メッセージID
 * @param rep 変換文字列
 */
function getMessage(key, rep) {
	var message = messages[key];
	if (rep instanceof Array) {
		var repLength = rep.length;
		for (var i = 0; i < repLength; i++ ) {
			message = message.replace("%" + String(i + 1) + "%", rep[i]);
		}
	} else {
		message = message.replace("%1%", rep);
	}
	return message;
}

/**
 * 必須確認（チェックボックス）
 * @param objForm フォームオブジェクト
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkBoxRequired(target, aryMessage) {
	// checkboxをチェック
	var cnt = 0;
	var check = document.getElementsByName(target);
	var checkLength = check.length;
	for (var i = 0; i < checkLength; i++) {
		if(check[i].checked)
			cnt++;
	}
	if (cnt == 0) {
		aryMessage.push(getMessage(MSG_CHECKBOX_ERROR, null));
	}
}

/**
 * 必須確認
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkRequired(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkRequiredNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_INPUT_ERROR, null));
		} else {
			aryMessage.push(getMessage(MSG_REQUIRED , rep));
		}
	}
}

/**
 * 必須確認(メッセージ無し)
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkRequiredNoMsg(target) {
	if (getFormValue(target) == "") {
		return false;
	} else {
		return true;
	}
}

/**
 * 文字形式確認を行う。
 * @param target 確認対象(StringあるいはObject)
 * @param reg    正規表現
 */
function checkRegexNoMsg(target, reg) {
	return getFormValue(target).match(reg);
}

/**
 * 文字列タイプ確認(半角英数字)
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkCode(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkCodeNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_ALP_NUM_CHECK_ERROR , null));
		} else {
			aryMessage.push(getMessage(MSG_ALP_NUM_CHECK_ERROR_AMP , rep));
		}
	}
}

/**
 * 文字列タイプ確認(半角英数字)(メッセージ無し)
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkCodeNoMsg(target) {
	if (getFormValue(target).match(/[^A-Za-z0-9]+/)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 文字列タイプ確認(半角数字)
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkNumber(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkNumberNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_NUMBER_CHECK_ERROR , null));
		} else {
			aryMessage.push(getMessage(MSG_NUMBER_CHECK_ERROR_AMP , rep));
		}
	}
}

/**
 * 文字列タイプ確認(半角数字)(メッセージ無し)
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkNumberNoMsg(target) {
	if (getFormValue(target).match(/[^0-9]+/)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 文字列タイプ確認(半角カナ+半角英数字記号)
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkKana(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkKanaNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR , null));
		} else {
			aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR_AMP , rep));
		}
	}
}

/**
 * 文字列タイプ確認(半角カナ+半角英数字記号)(メッセージ無し)
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkKanaNoMsg(target) {
	if (getFormValue(target).match(/[^｡-ﾟ -~]+/)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 文字列タイプを確認する(数値)。
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkNumeric(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkNumericNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_NUMBER_CHECK_ERROR , null));
		} else {
			aryMessage.push(getMessage(MSG_NUMBER_CHECK_ERROR_AMP , rep));
		}
	}
}

/**
 * 文字列タイプを確認する(数値)(メッセージ無し)。
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkNumericNoMsg(target) {
	// 数値チェック
	if (isNaN(getFormValue(target))) {
		return false;
	} else {
		// 半角数字(0-9)と小数点(.)以外が入力されていればエラー
		if (getFormValue(target).match(/[^0-9.]+/)) {
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 年月日のチェックを行う。<br>
 * 必須確認及び数値確認は、共通チェックで行うため、ここでは行わない。<br>
 * @param targetYear  確認対象年(StringあるいはObject)
 * @param targetMonth 確認対象月(StringあるいはObject)
 * @param targetDay   確認対象日(StringあるいはObject)
 * @param aryMessage  エラーメッセージ格納配列
 * @return 無し
 */
function checkDate(targetYear, targetMonth, targetDay, aryMessage) {
	// 範囲宣言
	var MIN_DAY   = 1;
	// 各月最終日宣言
	var MAX_DAY = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	// うるう年各月最終日宣言
	var MAX_DAY_LEAP = new Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	// 年月日を取得
	var year  = getFormValue(targetYear );
	var month = getFormValue(targetMonth);
	var day   = getFormValue(targetDay  );
	// 年確認
	if (checkDateYear(targetYear, aryMessage) == false) {
		return;
	}
	// 月確認
	if (checkDateMonth(targetMonth, aryMessage) == false ){
		return;
	}
	// 日確認
	var maxDay = MAX_DAY;
	if (checkLeapYear(year)) {
		maxDay = MAX_DAY_LEAP;
	}
	if (checkNumberNoMsg(targetMonth)
			&& checkNumberNoMsg(targetDay)
			&& (day < MIN_DAY || day > maxDay[month - 1])
	) {
		if (aryMessage.length == 0) {
			setFocus(targetDay);
		}
		setBgColor(targetDay, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_DAY_CHECK_OUTRANGE, maxDay[month - 1]));
	}
}

/**
 * 検索用年月日のチェックを行う。<br>
 * 年月日全て空白の場合はチェックを行わない。<br>
 * 年月日全て入力されている場合はcheckDateを行う。<br>
 * 年月のみ入力されている場合は年月のチェックを行う。<br>
 * 年のみ入力されている場合は年のチェックを行う。<br>
 * それ以外はエラーメッセージを設定する。<br>
 * @param targetYear  確認対象年(StringあるいはObject)
 * @param targetMonth 確認対象月(StringあるいはObject)
 * @param targetDay   確認対象日(StringあるいはObject)
 * @param aryMessage  エラーメッセージ格納配列
 * @return 無し
 */
function checkSearchDate(targetYear, targetMonth, targetDay, aryMessage) {
	// 年月日を取得
	var year  = getFormValue(targetYear );
	var month = getFormValue(targetMonth);
	var day   = getFormValue(targetDay  );
	// 年月日が全て空白の場合
	if(year == "" && month =="" && day ==""){
		return;
	}
	// 年月日全て入力されている場合
	if(year != "" && month !="" && day !=""){
		// 年月日チェック
		checkDate(targetYear, targetMonth, targetDay, aryMessage);
		return;
	}
	// 年月のみ入力されている場合
	if(year != "" && month != "" && day ==""){
		// 年確認
		checkDateYear(targetYear, aryMessage);
		// 月確認
		checkDateMonth(targetMonth, aryMessage);
		return;
	}
	// 年だけの場合
	if(year != "" && month =="" && day ==""){
		// 年確認
		checkDateYear(targetYear, aryMessage);
		return;
	}
	// それ以外の場合
	setBgColor(targetYear, COLOR_FIELD_ERROR);
	setBgColor(targetMonth, COLOR_FIELD_ERROR);
	setBgColor(targetDay, COLOR_FIELD_ERROR);
	aryMessage.push(getMessage(MSG_INPUT_ERROR));
}

/**
 * 年の妥当性確認
 * 必須確認及び数値確認
 */
function checkDateYear(targetYear, aryMessage) {
	// 範囲宣言
	var MIN_YEAR  = 1000;
	// 年を取得
	var year  = getFormValue(targetYear );
	// 年確認
	if (checkNumberNoMsg(targetYear) && year < MIN_YEAR) {
		if (aryMessage.length == 0) {
			setFocus(targetYear);
		}
		setBgColor(targetYear, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_YEAR_CHECK_ERROR, MIN_YEAR));
		return false;
	}
	return true;
}

/**
 * 月の妥当性確認
 * 必須確認及び数値確認
 */
function checkDateMonth(targetMonth, aryMessage) {
	// 範囲宣言
	var MIN_MONTH = 1;
	var MAX_MONTH = 12;
	// 月を取得
	var month = getFormValue(targetMonth);
	// 月確認
	if (checkNumberNoMsg(targetMonth) && (month < MIN_MONTH || month > MAX_MONTH)) {
		if (aryMessage.length == 0) {
			setFocus(targetMonth);
		}
		setBgColor(targetMonth, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MONTH_CHECK_ERROR, null));
		return false;
	}
	return true;
}

/**
 * うるう年判定を行う。
 * @param year 年(数値)
 * @return 判定結果(true：うるう年、false：うるう年でない)
 */
function checkLeapYear(year) {
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
		return true;
	}
	return false;
}

/**
 * 最低文字数確認を行う。
 * @param target     確認対象(StringあるいはObject)
 * @param minLength  最低文字列長
 * @param aryMessage メッセージ配列
 */
function checkMinLength(target, minLength, aryMessage) {
	var rep = [ getLabel(target), minLength ];
	if (!checkMinLengthNoMsg(target, minLength)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MIN_LENGTH_ERR , rep));
	}
}

/**
 * バイト数(表示上)妥当性確認を行う。<br>
 * @param target    確認対象(StringあるいはObject)
 * @param maxLength 最大バイト数(表示上)
 * @return 無し
 */
function checkByteLength(target, maxLength, aryMessage) {
	var rep = [ getLabel(target), Math.floor(maxLength / 2), maxLength ];
	if (!checkByteLengthNoMsg(target, maxLength)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_BYTE_LENGTH_ERR , rep));
	}
}

/**
 * バイト数(表示上)妥当性確認(メッセージ無し)を行う。<br>
 * @param target    確認対象(StringあるいはObject)
 * @param maxLength 最大バイト数(表示上)
 * @return 確認結果(true：OK、false：NG)
 */
function checkByteLengthNoMsg(target, maxLength) {
	// 対象の値を取得
	var value = getFormValue(target);
	// バイト数(表示上)準備
	var bytes = 0;
	var valueLength = value.length;
	// 値を一文字ずつ確認
	for (var i = 0; i < valueLength; ++i) {
		var c = value.charCodeAt(i);
		if ((c >= 0x0 && c <= 0x7f) || (c >= 0xff61 && c <= 0xff9f)) {
			bytes += 1;
		} else {
			bytes += 2;
		}
	}
	// バイト数(表示上)確認
	return bytes <= maxLength;
}

/**
 * ラベルを取得する。<br>
 * @param target 取得対象(StringあるいはObject)
 * @return ラベル
 */
function getLabel(target) {
	// ラベル区切文字宣言
	var separator = ",";
	// ラベル取得対象ID取得
	var id = getObject(target).id;
	// ラベル要素群取得
	var labelElements = getElementsByTagName(DIV_BODY, TAG_LABEL);
	var labelElementsLength = labelElements.length;
	for (var i = 0; i < labelElementsLength; i++) {
		// for属性確認
		if (labelElements.item(i).getAttribute(ATT_FOR) == null) {
			return "";
		}
		// for属性分割
		var ids = labelElements.item(i).getAttribute(ATT_FOR).split(separator);
		var idsLength = ids.length;
		// for属性とidの比較
		for (var j = 0; j < idsLength; j++) {
			if (ids[j] == id) {
				return new String(trimAll(getInnerHtml(labelElements.item(i))));
			}
		}
	}
	return "";
}

/**
 * 最低文字数確認(メッセージ無し)を行う。
 * @param target    確認対象(StringあるいはObject)
 * @param minLength 最低文字列長
 * @return 確認結果(true：OK、false：NG)
 */
function checkMinLengthNoMsg(target, minLength) {
	return getFormValue(target).length >= minLength;
}

/**
 * 郵便番号チェック
 * @param code1 確認対象郵便番号3桁(StringあるいはObject)
 * @param code2 確認対象郵便番号4桁(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 */
function checkPosta(code1, code2, aryMessage) {
	var posta1 = getFormValue(code1);
	var posta2 = getFormValue(code2);
	if (posta2 != "") {
		// 郵便番号2の入力有り
		if (posta1 != "") {
			// 郵便番号1の入力有り
			if (posta1.length < 3) {
				// 郵便番号1が2文字以内
				if (aryMessage.length == 0) {
					setFocus(code1);
				}
				setBgColor(code1, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR , code1));
			}
			if (posta2.length < 4) {
				// 郵便番号2が3文字以内
				if (aryMessage.length == 0) {
					setFocus(code2);
				}
				setBgColor(code2, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR , code2));
			}
		}
	} else {
		// 郵便番号2入力無し
		if (posta1 != "") {
			// 郵便番号1の入力有り
			if (posta1.length < 3) {
				// 郵便番号1が2文字以内
				if (aryMessage.length == 0) {
					setFocus(code1);
				}
				setBgColor(code1, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR , code1));
			}
		}
	}
}

/**
 * 全角スペース→半角スペース変換
 * @param target 変換対象(StringあるいはObject)
 * @return 無し
 * @throws 実行時例外
 */
function convSbSpace(target) {
	var str = getFormValue(target);
	str = str.replace(/　/g, String.fromCharCode(0x20));
	setFormValue(target, str);
}

/**
 * 全角→半角カナ変換
 * @param target 変換対象(StringあるいはObject)
 * @return 無し
 * @throws 実行時例外
 */
function convSbKana(target) {
	var str = getFormValue(target);
	var sbKana = "";
	var strLength = str.length;
	for (var i = 0; i < strLength; i++) {
		sbKana += convCharSbKana(str.charAt(i));
	}
	setFormValue(target, sbKana);
}

/**
 * 全角→半角カナ変換
 * @param char 変換対象
 * @return 変換後文字列
 * @throws 実行時例外
 */
function convCharSbKana(char) {
	var aryMbKana1 = new Array(
		"。","「","」","、","・","ヲ","ァ","ィ","ゥ","ェ","ォ","ャ","ュ","ョ","ッ","ー",
		"ア","イ","ウ","エ","オ","カ","キ","ク","ケ","コ","サ","シ","ス","セ","ソ",
		"タ","チ","ツ","テ","ト","ナ","ニ","ヌ","ネ","ノ","ハ","ヒ","フ","ヘ","ホ",
		"マ","ミ","ム","メ","モ","ヤ","ユ","ヨ","ラ","リ","ル","レ","ロ","ワ","ン",
		"゛","゜"
	);
	var aryMbKana2 = new Array(
		"ガ","ギ","グ","ゲ","ゴ","ザ","ジ","ズ","ゼ","ゾ","ダ","ヂ","ヅ","デ","ド"
	);
	var aryMbKana3 = new Array(
		"バ","ビ","ブ","ベ","ボ"
	);
	var aryMbKana4 = new Array(
		"パ","ピ","プ","ペ","ポ"
	);
	var strMbKana1 = "ヴ";
	var aryMbHira1 = new Array(
		"。","「","」","、","・","を","ぁ","ぃ","ぅ","ぇ","ぉ","ゃ","ゅ","ょ","っ","ー",
		"あ","い","う","え","お","か","き","く","け","こ","さ","し","す","せ","そ",
		"た","ち","つ","て","と","な","に","ぬ","ね","の","は","ひ","ふ","へ","ほ",
		"ま","み","む","め","も","や","ゆ","よ","ら","り","る","れ","ろ","わ","ん",
		"゛","゜"
	);
	var aryMbHira2 = new Array(
		"が","ぎ","ぐ","げ","ご","ざ","じ","ず","ぜ","ぞ","だ","ぢ","づ","で","ど"
	);
	var aryMbHira3 = new Array(
		"ば","び","ぶ","べ","ぼ"
	);
	var aryMbHira4 = new Array(
		"ぱ","ぴ","ぷ","ぺ","ぽ"
	);
	for (var i in aryMbKana1) {
		if (char == aryMbKana1[i]) {
			return String.fromCharCode(i - (-0xFF61));
		}
	}
	for (var i in aryMbKana2) {
		if (char == aryMbKana2[i]) {
			return String.fromCharCode(i - (-0xFF76), 0xFF9E);
		}
	}
	for (var i in aryMbKana3) {
		if (char == aryMbKana3[i]) {
			return String.fromCharCode(i - (-0xFF8A), 0xFF9E);
		}
	}
	for (var i in aryMbKana4) {
		if (char == aryMbKana4[i]) {
			return String.fromCharCode(i - (-0xFF8A), 0xFF9F);
		}
	}
	if (char == strMbKana1) {
		return String.fromCharCode(0xFF73, 0xFF9E);
	}
	for (var i in aryMbHira1) {
		if (char == aryMbHira1[i]) {
			return String.fromCharCode(i - (-0xFF61));
		}
	}
	for (var i in aryMbHira2) {
		if (char == aryMbHira2[i]) {
			return String.fromCharCode(i - (-0xFF76), 0xFF9E);
		}
	}
	for (var i in aryMbHira3) {
		if (char == aryMbHira3[i]) {
			return String.fromCharCode(i - (-0xFF8A), 0xFF9E);
		}
	}
	for (var i in aryMbHira4) {
		if (char == aryMbHira4[i]) {
			return String.fromCharCode(i - (-0xFF8A), 0xFF9F);
		}
	}
	return char;
}

//暗号化 **********************************************************************
var encryptT = new Array(0x00000000, 0xd76aa478, 0xe8c7b756, 0x242070db,
		0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613,
		0xfd469501, 0x698098d8, 0x8b44f7af, 0xffff5bb1,
		0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e,
		0x49b40821, 0xf61e2562, 0xc040b340, 0x265e5a51,
		0xe9b6c7aa, 0xd62f105d, 0x02441453, 0xd8a1e681,
		0xe7d3fbc8, 0x21e1cde6, 0xc33707d6, 0xf4d50d87,
		0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 0x676f02d9,
		0x8d2a4c8a, 0xfffa3942, 0x8771f681, 0x6d9d6122,
		0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60,
		0xbebfbc70, 0x289b7ec6, 0xeaa127fa, 0xd4ef3085,
		0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8,
		0xc4ac5665, 0xf4292244, 0x432aff97, 0xab9423a7,
		0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d,
		0x85845dd1, 0x6fa87e4f, 0xfe2ce6e0, 0xa3014314,
		0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb,
		0xeb86d391
);
var encryptround1 = new Array(new Array( 0, 7, 1), new Array( 1,12, 2),
		new Array( 2,17, 3), new Array( 3,22, 4),
		new Array( 4, 7, 5), new Array( 5,12, 6),
		new Array( 6,17, 7), new Array( 7,22, 8),
		new Array( 8, 7, 9), new Array( 9,12,10),
		new Array(10,17,11), new Array(11,22,12),
		new Array(12, 7,13), new Array(13,12,14),
		new Array(14,17,15), new Array(15,22,16)
);
var encryptround2 = new Array(new Array( 1, 5,17), new Array( 6, 9,18),
		new Array(11,14,19), new Array( 0,20,20),
		new Array( 5, 5,21), new Array(10, 9,22),
		new Array(15,14,23), new Array( 4,20,24),
		new Array( 9, 5,25), new Array(14, 9,26),
		new Array( 3,14,27), new Array( 8,20,28),
		new Array(13, 5,29), new Array( 2, 9,30),
		new Array( 7,14,31), new Array(12,20,32)
);
var encryptround3 = new Array(new Array( 5, 4,33), new Array( 8,11,34),
		new Array(11,16,35), new Array(14,23,36),
		new Array( 1, 4,37), new Array( 4,11,38),
		new Array( 7,16,39), new Array(10,23,40),
		new Array(13, 4,41), new Array( 0,11,42),
		new Array( 3,16,43), new Array( 6,23,44),
		new Array( 9, 4,45), new Array(12,11,46),
		new Array(15,16,47), new Array( 2,23,48)
);
var encryptround4 = new Array(new Array( 0, 6,49), new Array( 7,10,50),
		new Array(14,15,51), new Array( 5,21,52),
		new Array(12, 6,53), new Array( 3,10,54),
		new Array(10,15,55), new Array( 1,21,56),
		new Array( 8, 6,57), new Array(15,10,58),
		new Array( 6,15,59), new Array(13,21,60),
		new Array( 4, 6,61), new Array(11,10,62),
		new Array( 2,15,63), new Array( 9,21,64)
);
var encryptround = new Array(new Array(encryptF, encryptround1),
		new Array(encryptG, encryptround2),
		new Array(encryptH, encryptround3),
		new Array(encryptI, encryptround4)
);
function encryptF(x, y, z) { return (x & y) | (~x & z); }
function encryptG(x, y, z) { return (x & z) | (y & ~z); }
function encryptH(x, y, z) { return x ^ y ^ z;          }
function encryptI(x, y, z) { return y ^ (x | ~z);       }
function encryptpack(n32) {
	return String.fromCharCode(n32 & 0xff) +
			String.fromCharCode((n32 >>> 8) & 0xff) +
			String.fromCharCode((n32 >>> 16) & 0xff) +
			String.fromCharCode((n32 >>> 24) & 0xff);
}
function encryptunpack(s4) {
	return  s4.charCodeAt(0) |
			(s4.charCodeAt(1) <<  8) |
			(s4.charCodeAt(2) << 16) |
			(s4.charCodeAt(3) << 24);
}
function encryptnumber(n) {
	while (n < 0) n += 4294967296;
	while (n > 4294967295) n -= 4294967296;
	return n;
}
function encryptapply_round(x, s, f, abcd, r) {
	var a, b, c, d;
	var kk, ss, ii;
	var t, u;
	a = abcd[0];
	b = abcd[1];
	c = abcd[2];
	d = abcd[3];
	kk = r[0];
	ss = r[1];
	ii = r[2];
	u = f(s[b], s[c], s[d]);
	t = s[a] + u + x[kk] + encryptT[ii];
	t = encryptnumber(t);
	t = ((t<<ss) | (t>>>(32-ss)));
	t += s[b];
	s[a] = encryptnumber(t);
}
function hash(data) {
	var abcd, x, state, s;
	var len, index, padLen, f, r;
	var i, j, k;
	var tmp;
	state = new Array(0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476);
	len = data.length;
	index = len & 0x3f;
	padLen = (index < 56) ? (56 - index) : (120 - index);
	if (padLen > 0) {
		data += "\x80";
	for (i = 0; i < padLen - 1; i++)
		data += "\x00";
	}
	data += encryptpack(len * 8);
	data += encryptpack(0);
	len  += padLen + 8;
	abcd = new Array(0, 1, 2, 3);
	x    = new Array(16);
	s    = new Array(4);
	for (k = 0; k < len; k += 64) {
		for (i = 0, j = k; i < 16; i++, j += 4) {
			x[i] = data.charCodeAt(j) |
					(data.charCodeAt(j + 1) <<  8) |
					(data.charCodeAt(j + 2) << 16) |
					(data.charCodeAt(j + 3) << 24);
		}
		for (i = 0; i < 4; i++) s[i] = state[i];
		for (i = 0; i < 4; i++) {
			f = encryptround[i][0];
			r = encryptround[i][1];
			for (j = 0; j < 16; j++) {
				encryptapply_round(x, s, f, abcd, r[j]);
				tmp = abcd[0];
				abcd[0] = abcd[3];
				abcd[3] = abcd[2];
				abcd[2] = abcd[1];
				abcd[1] = tmp;
			}
		}
		for (i = 0; i < 4; i++) {
			state[i] += s[i];
			state[i] = encryptnumber(state[i]);
		}
	}
	return encryptpack(state[0]) +
			encryptpack(state[1]) +
			encryptpack(state[2]) +
			encryptpack(state[3]);
}
function encrypt(arg) {
	var i;
	var ret;
	var c;
	arg = hash(arg);
	ret = "";
	for (i = 0; i < 16; i++) {
		c = arg.charCodeAt(i);
		ret += "0123456789abcdef".charAt((c >> 4) & 0xf);
		ret += "0123456789abcdef".charAt(c & 0xf);
	}
	return ret;
}

/**
 * 最大文字数確認(メッセージ無し)を行う。
 * @param target    確認対象(StringあるいはObject)
 * @param maxLength 最大文字列長
 * @return 確認結果(true：OK、false：NG)
 */
function checkMaxLengthNoMsg(target, maxLength) {
	// 最大文字数確認
	if (typeof(maxLength) != "undefined" && maxLength != "") {
		return getFormValue(target).length <= maxLength;
	}
}

/**
 * 最大文字数確認を行う。
 * @param target     確認対象(StringあるいはObject)
 * @param maxLength  最大文字列長
 * @param aryMessage メッセージ配列
 */
function checkMaxLength(target, maxLength, aryMessage) {
	var rep = [ getLabel(target), maxLength ];
	if (!checkMaxLengthNoMsg(target, maxLength)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MAX_LENGTH_ERR , rep));
	}
}

//*****************************************************************************

/**
 * 例外処理
 * @param e 例外オブジェクト
 * @return 無し
 * @throws 無し
 */
function handleException(e) {
	alert(getMessage(MSG_CLIENT_ERROR , null));
	if (e.description != null) {
		alert(e.description);
	}
	throw e;
}
