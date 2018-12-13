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
 * チェックボックス選択エラーメッセージ。
 */
var MSG_CHECKBOX_SELECT_ERROR = "PFW0233";

/**
 * 振替日重複チェックエラーメッセージ。
 */
var MSG_TRANSFER_REPETITION_CHECK	= "TMW0216";

/**
 * 登録/削除確認メッセージ。
 */
var MSG_DIFFERENCE_REQUEST_CHECK	= "TMW0230";

/**
 * 終業時刻チェックエラーメッセージ。
 */
var MSG_TIME_CHECK					= "TMW0236";


/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	setOnChangeHandler("pltEditSubstitute", changeSubstitute);
	changeSubstitute();
	setOnChangeHandler("pltEditSubstituteWorkRange", changeSubstitute);
	changeSubstitute();
	// 承認状況がNULLの場合は全項目を読取専用にする
	if (modeActivateDate == "") {
		setDisabled("pltEditRequestYear", false);
		setDisabled("pltEditRequestMonth", false);
		setDisabled("pltEditRequestDay", true);
		setReadOnly("btnRequestDate", true);
		setDisabled("pltEditSubstitute", true);
		setDisabled("pltEditStartHour", true);
		setDisabled("pltEditStartMinute", true);
		setDisabled("pltEditEndHour", true);
		setDisabled("pltEditEndMinute", true);
		setDisabled("pltEditSubstitute1Year", true);
		setDisabled("pltEditSubstitute1Month", true);
		setDisabled("pltEditSubstitute1Day", true);
		setDisabled("pltEditSubstitute1Range", true);
		setDisabled("txtEditRequestReason", true);
		setReadOnly("btnRegist", true);
		setReadOnly("btnDraft", true);
		return;
	}
	// 出勤日が無効の場合は出勤年/月以外は編集不可にする
	var editRequestDay = getFormValue("pltEditRequestDay");
	if( editRequestDay == "") {
		setDisabled("pltEditSubstitute", true);
		setDisabled("pltEditRequestDay", true);
		setReadOnly("btnRequestDate", true);
		return;
	} else{
		setDisabled("pltEditSubstitute", false);
		setDisabled("pltEditRequestDay", false);
		setReadOnly("btnRequestDate", false);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setDisabled("pltEditRequestYear", true);
		setDisabled("pltEditRequestMonth", true);
		setDisabled("pltEditRequestDay", true);
		setDisabled("pltEditSubstituteWorkRange", false);
		var substitute = getFormValue("pltEditSubstitute");
		// 申請日が法定/所定休日で読取領域の変更(法定/所定休日判定方法は未実装)
		if(substitute == 1 || substitute == 5) {
			// 法定休日で振替休暇申請
			setDisabled("pltEditStartHour", false);
			setDisabled("pltEditStartMinute", false);
			setDisabled("pltEditEndHour", false);
			setDisabled("pltEditEndMinute", false);
			setDisabled("pltEditSubstitute1Year", false);
			setDisabled("pltEditSubstitute1Month", false);
			setDisabled("pltEditSubstitute1Day", false);
			setDisabled("pltEditSubstitute1Range", true);
			// 所定休日で振替休暇申請
			setDisabled("pltEditStartHour", false);
			setDisabled("pltEditStartMinute", false);
			setDisabled("pltEditEndHour", false);
			setDisabled("pltEditEndMinute", false);
			setDisabled("pltEditSubstitute1Year", false);
			setDisabled("pltEditSubstitute1Month", false);
			setDisabled("pltEditSubstitute1Day", false);
			setDisabled("pltEditSubstitute1Range", false);
			var substitute1Range = getFormValue("pltEditSubstitute1Range");
		} else {
			// 法定休日で振替休暇非申請
			setDisabled("pltEditStartHour", false);
			setDisabled("pltEditStartMinute", false);
			setDisabled("pltEditEndHour", false);
			setDisabled("pltEditEndMinute", false);
			setDisabled("pltEditSubstitute1Year", false);
			setDisabled("pltEditSubstitute1Month", false);
			setDisabled("pltEditSubstitute1Day", false);
			setDisabled("pltEditSubstitute1Range", false);
		}
		// 所定、法定休日のチェック
		if(jsModeLegalHoliday == "on") {
			// 法定
			setDisabled("pltEditSubstitute1Range", true);
		}
	} else {
		// 有効日編集可
		setDisabled("pltEditRequestYear", false);
		setDisabled("pltEditRequestMonth", false);
		setDisabled("pltEditRequestDay", false);
		setDisabled("pltEditSubstitute", false);
		setDisabled("pltEditSubstituteWorkRange", true);
		// 編集項目の利用不可
		setDisabled("pltEditSubstitute", true);
		setDisabled("pltEditStartHour", false);
		setDisabled("pltEditStartMinute", false);
		setDisabled("pltEditEndHour", false);
		setDisabled("pltEditEndMinute", false);
		setDisabled("pltEditSubstitute1Year", true);
		setDisabled("pltEditSubstitute1Month", true);
		setDisabled("pltEditSubstitute1Day", true);
		setDisabled("pltEditSubstitute1Range", true);
		setDisplayNone("spanWorkType");
	}
	// 振替出勤範囲を設定
	setSubstitute();
	// 振替休日範囲を設定
	setSubstitute1Range();
}

/**
 * 半振替プルダウンを設定
 * @param event
 */
function changeSubstitute(event) {
	if (event != null) {
		onChangeFields(event);
	}
	changeWorkOnHolidayType();
	// 振替出勤設定
	setSubstitute();
	// 振替出勤：半日振替設定
	setSubstitute1Range();
}

/**
 * 振出・休出区分設定
 */
function changeWorkOnHolidayType() {
	setInlineVisibility("spanWorkType", false);
	setTableRowVisibility("trSubstitute", false);
	setTableRowVisibility("trWorkOnDayOff", false);
	var editSubstitute = getFormValue("pltEditSubstitute");
	if (editSubstitute == 1) {
		// 振替出勤(勤務形態変更なし)
		setTableRowVisibility("trSubstitute", true);
		return;
	} else if (editSubstitute == 2) {
		// 振替日未定(休日出勤)
		setTableRowVisibility("trWorkOnDayOff", true);
		// 全日に固定する
		setFormValue("pltEditSubstituteWorkRange", 1);
		setDisabled("pltEditSubstituteWorkRange", true);
		return;
	} else if (editSubstitute == 5) {
		// 振替出勤(勤務形態変更あり)
		setTableRowVisibility("trSubstitute", true);
		setInlineVisibility("spanWorkType", true);
		// 振替出勤が全日の場合は表示しない
		setInlineVisibility("pltEditSubstitute1Range", false);
		// 全日に固定する
		setFormValue("pltEditSubstituteWorkRange", 1);
		setDisabled("pltEditSubstituteWorkRange", true);
	}
}

/**
 * 振替出勤設定
 */
function setSubstitute() {
	if (!modeHalfSubstitute) {
		// 半日振替利用不可の場合
		return;
	}
	// 半日振替利用可の場合
	// 振出・休出区分取得
	var editSubstitute = getFormValue("pltEditSubstitute");
	// 振替出勤(勤務形態変更なし)
	if(editSubstitute == 1) {
		setDisabled("pltEditSubstituteWorkRange", false);
	}
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可の場合
		return;
	}
	// 有効日編集可の場合
	var substitute = getFormValue("pltEditSubstitute");
	if (substitute == 1) {
		// 振替出勤の場合
		setDisabled("pltEditSubstituteWorkRange", true);
	} else if (substitute == 2) {
		// 休日出勤の場合
		setFormValue("pltEditSubstituteWorkRange", 1);
		setDisabled("pltEditSubstituteWorkRange", true);
		changeFieldBgColor("pltEditSubstituteWorkRange");
	}
}

/**
 * 振替出勤：半日振替設定
 */
function setSubstitute1Range() {
	if (!modeHalfSubstitute) {
		// 半日振替利用不可の場合
		return;
	}
	// 半日振替利用可の場合
	if (getFormValue("pltEditSubstituteWorkRange") == "1") {
		// 振替出勤が全日の場合は表示しない
		setInlineVisibility("pltEditSubstitute1Range", false);
	}else{
		setInlineVisibility("pltEditSubstitute1Range", true);
	}
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDateExtra(aryMessage, event) {
	checkDate("pltEditRequestYear", "pltEditRequestMonth", "pltEditRequestDay", aryMessage); 
}

/**
 * 申請関連のリクエストを送信する。<br>
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * @param event イベントオブジェクト
 * @param cmd   コマンド
 * @return 無し
 */
function submitApplication(event, cmd) {
	submitRegist(event, "divEdit", checkRegistExtra, cmd);
}

/**
 * 下書時の追加チェックを行う。<br>
 * 振替日の入力チェックを行う。<br>
 */
function checkDraftExtra(aryMessage, event) {
	// 振替休日が平日かの確認
	checkRegistExtra(aryMessage, event);
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkRegistExtra(aryMessage, event) {
	checkDate("pltEditSubstitute1Year", "pltEditSubstitute1Month", "pltEditSubstitute1Day", aryMessage);
	if (getFormValue("pltEditSubstitute") == "2") {
		// 休日出勤の場合
		var startTime = getFormValue("pltEditStartHour") * 60 + parseIntDecimal(getFormValue("pltEditStartMinute"));
		var endTime = getFormValue("pltEditEndHour") * 60 + parseIntDecimal(getFormValue("pltEditEndMinute"));
		if (startTime >= endTime) {
			// 始業時刻と終業時刻が同じ場合
			if (aryMessage.length == 0) {
				setFocus("pltEditEndHour");
			}
			setBgColor("pltEditEndHour", COLOR_FIELD_ERROR);
			setBgColor("pltEditEndMinute", COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_TIME_CHECK, null));
		}
	}
}

/**
 * 一括申請時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchUpdateExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "下書");
}

/**
 * 一括取下時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchWithdrawnExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "未承認");
}

/**
 * 状態確認。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @param status 状態
 * @return 無し
 */
function checkStatus(aryMessage, event, status) {
	var checkbox = document.getElementsByName("ckbSelect");
	var checkboxLength = checkbox.length;
	for (var i = 0; i < checkboxLength; i++) {
		if (!isCheckableChecked(checkbox[i])) {
			// チェックボックス確認
			continue;
		}
		// チェックボックスが含まれるTR要素取得
		var trElement = checkbox[i].parentNode.parentNode;
		// TR要素内TD要素群取得
		var tdElements = getElementsByTagName(trElement, TAG_TD);
		// TD要素内A要素群取得
		var aElements = getElementsByTagName(tdElements.item(5), TAG_A);
		// A要素内SPAN要素群取得
		var spanElements = getElementsByTagName(aElements.item(0), "SPAN");
		if (spanElements.item(0).firstChild.nodeValue != status) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), status];
			aryMessage.push(getMessage(MSG_CHECKBOX_SELECT_ERROR, rep));
			return;
		}
	}
}

/**
 * オブジェクトの表示・非表示
 * @param target
 * @param isVisible
 * @return 無し
 * @throws 実行時例外
 */
function setInlineVisibility(target, isVisible) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	if (isVisible) {
		objTarget.style.display = "inline";
		return;
	}
	objTarget.style.display = "none";
}

/**
 * オブジェクトの表示・非表示
 * @param target
 * @param isVisible
 * @return 無し
 * @throws 実行時例外
 */
function setTableRowVisibility(target, isVisible) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	if (isVisible) {
		objTarget.style.display = "table-row";
		return;
	}
	objTarget.style.display = "none";
}

/**
 * 文字列を整数に変換する。
 * @param string 解析する文字列
 */
function parseIntDecimal(string) {
	return parseInt(string, 10);
}
