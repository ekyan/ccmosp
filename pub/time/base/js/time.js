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
 * 時刻（時）チェックエラーメッセージ。
 */
var MSG_HOUR_CHECK	= "TMW0202";

/**
 * 時刻（分）チェックエラーメッセージ。
 */
var MSG_MINUTE_CHECK	= "TMW0203";

/**
 * 日付（年）チェックエラーメッセージ。
 */
var MSG_YEAR_CHECK_ERROR	= "PFW0117";

/**
 * 日付（月）チェックエラーメッセージ。
 */
var MSG_MONTH_CHECK_ERROR	= "PFW0118";

/**
 * 付与日数値チェックエラーメッセージ。
 */
var MSG_GIVINGDAY_VALUE_CHECK = "TMW0219";


function checkTime(targetHour, targetMinute, aryMessage) {
	// 範囲宣言
	var MIN_HOUR  = 0;
	var MAX_HOUR  = 47;
	var MIN_MINUTE = 0;
	var MAX_MINUTE = 59;
	// 時分を取得
	var hour  = getFormValue(targetHour);
	var minute = getFormValue(targetMinute);
	// 未入力チェック
	if( hour == "" ){
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_HOUR_CHECK, 47));
		return;
	}
	if( minute == "" ){
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MINUTE_CHECK, null));
		return;
	}
	// 時間確認
	if (hour < MIN_HOUR || hour > MAX_HOUR) {
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_HOUR_CHECK, 47));
		return;
	}
	// 分確認
	if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MINUTE_CHECK, null));
		return;
	}
}

/**
 * ノード内の時刻チェックを行う。<br>
 * 時刻は、idで判断する。<br>
 * 〇〇Hourが存在した場合、〇〇Minuteを検索する。<br>
 * 全て存在しており、一つでも入力されていたら時刻の妥当性確認を行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkTimes(aryMessage, event) {
	// 検索対象文字列宣言
	var HOUR_ID  = "Hour";
	var MINUTE_ID = "Minute";
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.lastIndexOf(HOUR_ID) != elementId.length - HOUR_ID.length) {
			continue;
		}
		// 分ID作成
		var minuteId = elementId.replace(HOUR_ID, MINUTE_ID);
		// 分要素取得
		var minuteElement = document.getElementById(minuteId);
		// 分要素確認
		if (minuteElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(minuteElement) == "" ) {
			continue;
		}
		// 時刻チェック
		checkTime(element, minuteElement, aryMessage);
	}
}

/**
 * ノード内の日付チェックを行う。<br>
 * 日付は、idで判断する。<br>
 * 〇〇Yearが存在した場合、〇〇Monthを検索する。<br>
 * 全て存在しており、一つでも入力されていたら日付の妥当性確認を行う。<br>
 * @param aryMessage メッセージ配列
 * @param target     確認対象(StringあるいはObject)
 */
function checkDatesYearMonth(aryMessage, event) {
	// 検索対象文字列宣言
	var YEAR_NAME  = "Year";
	var MONTH_NAME = "Month";
	// INPUT要素群取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementName = new String(element.name);
		// 要素name確認
		if (elementName.lastIndexOf(YEAR_NAME) != elementName.length - YEAR_NAME.length) {
			continue;
		}
		// 月name作成
		var monthName = elementName.replace(YEAR_NAME, MONTH_NAME);
		// 月要素準備
		var monthElement = document.getElementById(monthName);
		// 月要素確認
		if (monthElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(monthElement) == "" ) {
			continue;
		}
		// 日付チェック
		return checkDateYearMonth(element, monthElement, aryMessage);
	}
}

/**
 * 年月のチェックを行う。<br>
 * 必須確認及び数値確認は、共通チェックで行うため、ここでは行わない。<br>
 * @param targetYear  確認対象年(StringあるいはObject)
 * @param targetMonth 確認対象月(StringあるいはObject)
 * @param aryMessage  エラーメッセージ格納配列
 * @return 無し
 */
function checkDateYearMonth(targetYear, targetMonth, aryMessage) {
	// 範囲宣言
	var MIN_YEAR  = 1000;
	var MIN_MONTH = 1;
	var MAX_MONTH = 12;
	// 年月を取得
	var year  = getFormValue(targetYear );
	var month = getFormValue(targetMonth);
	// 年確認
	if (year < MIN_YEAR) {
		if (aryMessage.length == 0) {
			setFocus(targetYear);
		}
		setBgColor(targetYear, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_YEAR_CHECK_ERROR, MIN_YEAR));
		return false;
	}
	// 月確認
	if (month < MIN_MONTH || month > MAX_MONTH) {
		if (aryMessage.length == 0) {
			setFocus(targetMonth);
		}
		setBgColor(targetMonth, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MONTH_CHECK_ERROR, null));
		return false;
	}
}

/**
 * 付与日数チェック処理
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 * @return 無し
 */
function checkGiving(aryMessage, event, target) {
	var holidayType = getFormValue(target);
	var DECIMAL_CHECK = 0.5;
	if( holidayType % DECIMAL_CHECK != 0){
		var rep = getLabel(target);
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_GIVINGDAY_VALUE_CHECK, rep));
		return false;
	}
	return true;
}
