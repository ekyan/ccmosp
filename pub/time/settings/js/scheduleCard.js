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
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 一覧背景色設定
	setTableColor("list2");
	// 無効フラグ編集可
	setDisabled("pltEditInactivate", false);
	// コード編集可
	setReadOnly("txtScheduleCode", false);
	// 年度編集可
	setReadOnly("pltFiscalYear", false);
	// 決定ボタン押下可
	setDisabled("btnActivateDateSet", false);
	// 新規登録
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		// 無効フラグ編集不可
		setDisabled("pltEditInactivate", true);
	// 履歴追加
	} else if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		// コード編集不可
		setReadOnly("txtScheduleCode", true);
	// 履歴編集
	} else if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		// コード編集不可
		setReadOnly("txtScheduleCode", true);
		// 年度編集不可
		setReadOnly("pltFiscalYear", true);
		// 決定ボタン押下不可
		setDisabled("btnActivateDateSet", true);
		setDisabled("btnPatternSet", true);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 年度編集不可
		setReadOnly("pltFiscalYear", true);
		// 登録ボタン利用可
		setDisabled("btnRegist", false);
		// 月ボタン利用可
		setDisabled("btnApril", false);
		setDisabled("btnMay", false);
		setDisabled("btnJune", false);
		setDisabled("btnJuly", false);
		setDisabled("btnAugust", false);
		setDisabled("btnSeptember", false);
		setDisabled("btnOctorber", false);
		setDisabled("btnNovember", false);
		setDisabled("btnDecember", false);
		setDisabled("btnJanuary", false);
		setDisabled("btnFebruary", false);
		setDisabled("btnMarch", false);
	} else {
		// 年度編集可
		setReadOnly("pltFiscalYear", false);
		// パターン決定ボタン利用不可
		setDisabled("btnPatternSet", true);
		// 登録ボタン利用不可
		setDisabled("btnRegist", true);
		// 月ボタン利用可
		setDisabled("btnApril", true);
		setDisabled("btnMay", true);
		setDisabled("btnJune", true);
		setDisabled("btnJuly", true);
		setDisabled("btnAugust", true);
		setDisabled("btnSeptember", true);
		setDisabled("btnOctorber", true);
		setDisabled("btnNovember", true);
		setDisabled("btnDecember", true);
		setDisabled("btnJanuary", true);
		setDisabled("btnFebruary", true);
		setDisabled("btnMarch", true);
	}
	// パターンモード確認
	if (modePattern == MODE_ACTIVATE_DATE_FIXED) {
		// 編集不可
		// 有効日決定ボタン利用不可
		setDisabled("btnActivateDateSet", true);
		// パターン編集不可
		setReadOnly("pltPattern", true);
	}
	// コピーモード確認
	if (jsCopyModeEdit == "TM5474") {
		// 決定ボタン押下不可
		setDisabled("btnActivateDateSet", true);
		setDisabled("btnPatternSet", true);
	}
	// 勤務形態指定ラジオボタンイベント設定
	setOnClickHandler("radioWeek", onClickRadioSelect);
	setOnClickHandler("radioPeriod", onClickRadioSelect);
	setOnClickHandler("radioCheck", onClickRadioSelect);
	// 勤務形態指定確認
	onClickRadioSelect(null);
}

/**
 * 勤務形態指定ラジオボタンクリック時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onClickRadioSelect(event) {
	// チェック確認
	if(isCheckableChecked("radioWeek")) {
		setDisabled("ckbMonday", false);
		setDisabled("ckbTuesday", false);
		setDisabled("ckbWednesday", false);
		setDisabled("ckbThursday", false);
		setDisabled("ckbFriday", false);
		setDisabled("ckbSatureday", false);
		setDisabled("ckbSunday", false);
		setDisabled("ckbNationalHoliday", false);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", true);
	} else if(isCheckableChecked("radioPeriod")) {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", false);
		setDisabled("pltScheduleEndDay", false);
		doAllChecked("ckbSelect", true);
	} else if(isCheckableChecked("radioCheck")) {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", false);
	} else {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", true);
	}
}

/**
 * 指定されたname領域の一括読取・解除を行う。<br>
 * @param obj 一括選択・解除チェックボックス(Object)
 */
function doAllChecked(name, flg) {
	// チェックボックスエレメント取得
	var elements = document.getElementsByName(name);
	var elementsLength = elements.length;
	// チェック操作
	for (i = 0; i < elementsLength; i++) {
		var objTarget = getObject(elements[i]);
		objTarget.disabled = flg;
	}
}
