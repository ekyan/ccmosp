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
 * 休暇区分(欠勤)。<br>
 */
var HOLIDAY_TYPE_ABSENCE = "4";

/**
 * 給与区分(無給)。<br>
 */
var SALARY_PAY_TYPE_NONE = "1";

/**
 * 付与日数値チェックエラーメッセージ。
 */
var MSG_GIVINGDAY_VALUE_CHECK = "TMW0219";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// イベントハンドラ設定
	setOnChangeHandler("pltEditHolidayType", checkHolidayType);
	setOnClickHandler("ckbNoLimit", checkNoLimit);
	// 休暇区分確認
	checkHolidayType();
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		setReadOnly("pltEditInactivate", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setReadOnly("pltEditHolidayType", true);
		setReadOnly("txtEditHolidayCode", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
		setReadOnly("pltEditHolidayType", true);
		setReadOnly("txtEditHolidayCode", true);
	}
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
}

/**
 * 休暇区分変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function checkHolidayType(event) {
	// フィールド変更確認
	if (event != null) {
		onChangeFields(event);
	}
	// 休暇区分確認
	if (getFormValue("pltEditHolidayType") == HOLIDAY_TYPE_ABSENCE) {
		// 欠勤の場合
		checkableCheck("ckbNoLimit", true);
		setReadOnly("ckbNoLimit", true);
		setFormValue("pltEditSalary", SALARY_PAY_TYPE_NONE);
		setReadOnly("pltEditSalary", true);
	} else {
		// 欠勤以外の場合
		setReadOnly("ckbNoLimit", false);
		setReadOnly("pltEditSalary", false);
		setReadOnly("pltEditContinue", false);
	}
	// 無制限確認
	checkNoLimit(null);
}

/**
 * 無制限クリック時の処理を行う。<br>
 * @param イベントオブジェクト
 */
function checkNoLimit(event) {
	// 無制限チェック確認
	if (isCheckableChecked("ckbNoLimit")) {
		// チェックされている場合
		setReadOnly("txtEditHolidayGiving", true);
		setFormValue("txtEditHolidayGiving", "0.0");
		setReadOnly("pltEditContinue", true);
		setFormValue("pltEditContinue", "2");
		setReadOnly("txtEditHolidayLimitMonth", true);
		setFormValue("txtEditHolidayLimitMonth", "0");
		setReadOnly("txtEditHolidayLimitDay", true);
		setFormValue("txtEditHolidayLimitDay", "0");
	} else {
		// チェックされていない場合
		setReadOnly("txtEditHolidayGiving", false);
		setReadOnly("pltEditContinue", false);
		setReadOnly("txtEditHolidayLimitMonth", false);
		setReadOnly("txtEditHolidayLimitDay", false);
	}
}

/**
 * 標準付与日数チェック処理
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 * @return 無し
 */
function checkHolidayGiving(aryMessage, event) {
	var holidayType = getFormValue("txtEditHolidayGiving");
	var DECIMAL_CHECK = 0.5;
	if( holidayType % DECIMAL_CHECK != 0){
		var rep = getLabel("txtEditHolidayGiving");
		if (aryMessage.length == 0) {
			setFocus("txtEditHolidayGiving");
		}
		setBgColor("txtEditHolidayGiving", COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_GIVINGDAY_VALUE_CHECK, rep));
		return;
	}
}

