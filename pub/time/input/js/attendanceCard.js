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
 * 未入力時のメッセージコード。
 */
var MSG_REQUIRED = "PFW0102"

/**
 * 時刻（時）チェックエラーメッセージ。
 */
var MSG_TIME_CHECK = "TMW0233";

/**
 * 休憩/外出チェックエラーメッセージ。
 */
var MSG_REST_CHECK = "TMW0247";

/**
 * 始業時刻終業時刻チェックエラーメッセージ。
 */
var MSG_TIME_EMPTY_CHECK = "TMW0268";

/**
 * 終了時刻チェックエラーメッセージ。
 */
var MSG_END_TIME_CHECK = "TMW0280";

/**
 * 申請モード【新規】。
 */
var	MODE_APPLICATION_NEW	= "new";

/**
 * 申請モード【下書編集】。
 */
var MODE_APPLICATION_DRAFT	= "draft";

/**
 * 申請モード【差戻編集】（1次戻）。
 */
var MODE_APPLICATION_REVERT = "revert";

/**
 * 申請モード【未承認】。
 */
var MODE_APPLICATION_APPLY = "apply";

/**
 * 申請モード【申請済】。
 */
var MODE_APPLICATION_APPLIED = "applied";

/**
 * 申請モード【休日承認済】。
 */
var MODE_APPLICATION_COMPLETED_HOLIDAY = "completedHoliday";

/**
 * 勤務形態【所定休日出勤】。
 */
var CODE_WORK_ON_LEGAL = "work_on_legal";

/**
 * 勤務形態【法定休日出勤】。
 */
var CODE_WORK_ON_PRESCRIBED = "work_on_prescribed"

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 申請モード(新規)
	if (modeCardEdit == MODE_APPLICATION_NEW) {
		// 申請/下書ボタン押下不可
		setReadOnly("btnRegist", false);
		setReadOnly("btnDraft", false);
		setReadOnly("btnCalc", false);
		setReadOnly("btnDelete", true);
	}
	// 申請モード(下書)
	if (modeCardEdit == MODE_APPLICATION_DRAFT) {
		// 申請/下書ボタン押下可
		setReadOnly("btnRegist", false);
		setReadOnly("btnDraft", false);
		setReadOnly("btnCalc", false);
		setReadOnly("btnDelete", false);
	}
	// 申請モード(差戻)
	if (modeCardEdit == MODE_APPLICATION_REVERT) {
		// 申請/下書ボタン押下可
		setReadOnly("btnRegist", false);
		setReadOnly("btnDraft", true);
		setReadOnly("btnCalc", false);
		setReadOnly("btnDelete", false);
	}
	// 申請モード(未承認)
	if (modeCardEdit == MODE_APPLICATION_APPLY) {
		// チェックボックス編集不可
		setDisabled("ckbDirectStart", true);
		setDisabled("ckbDirectEnd", true);
		// 申請/下書ボタン押下不可
		setReadOnly("btnSelect", true);
		setReadOnly("btnRegist", true);
		setReadOnly("btnCalc", true);
		setReadOnly("btnDraft", true);
	}
	// 申請モード(申請済或いは休日承認済)
	if(modeCardEdit == MODE_APPLICATION_APPLIED || modeCardEdit == MODE_APPLICATION_COMPLETED_HOLIDAY) {
		// チェックボックス編集不可
		setDisabled("ckbDirectStart", true);
		setDisabled("ckbDirectEnd", true);
		// 申請/下書ボタン押下不可
		setReadOnly("btnSelect", true);
		setReadOnly("btnRegist", true);
		setReadOnly("btnDraft", true);
		setReadOnly("btnCalc", true);
		setReadOnly("btnDelete", true);
	}
	// 休日出勤設定
	setWorkOnDayOff();
}

/**
 * テキストエリア桁数設定。
 */
function isMaxlength(obj) {
	var mlength = obj.getAttribute ? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length > mlength) {
		obj.value = obj.value.substring(0, mlength);
	}
}

/**
 * 休日出勤設定。
 */
function setWorkOnDayOff() {
	// 勤務形態コードが所定休出又は法定休出か確認
	var isWorkOnHolidayTime = getFormValue("pltWorkType") == CODE_WORK_ON_LEGAL || getFormValue("pltWorkType") == CODE_WORK_ON_PRESCRIBED;
	// 休日出勤申請の場合
	if(getFormValue("lblWorkOnHolidayDate") != "" && isWorkOnHolidayTime) {
		// 直行直帰チェックボックス編集不可
		setDisabled("ckbDirectStart", true);
		setDisabled("ckbDirectEnd", true);
		setDisabled("ckbAllMinutelyHolidayA",true);
		setDisabled("ckbAllMinutelyHolidayB",true);
	}
}

function checkDraftTimes(aryMessage, event) {
	var aryTimeHour = new Array("txtStartTimeHour", "txtEndTimeHour");
	var aryTimeMinute = new Array("txtStartTimeMinute", "txtEndTimeMinute");
//	checkTimeEmpty(aryMessage);
	// 時刻チェック
	checkDraftTime(aryTimeHour[0], aryTimeMinute[0], aryMessage);
	// エラーが発生したら処理を終了する
//	if (aryMessage.length != 0) {
//		return;
//	}
	// 時刻チェック
	checkDraftTime(aryTimeHour[1], aryTimeMinute[1], aryMessage);
	// エラーが発生したら処理を終了する
	if (aryMessage.length != 0) {
		return;
	}
	checkTimes(aryMessage, event);
	checkDirect(aryMessage);
	checkAllMinutelyHoliday(aryMessage);
}

function checkRegistTimes(aryMessage, event) {
	checkRegistTime("txtStartTimeHour", "txtStartTimeMinute", aryMessage);
	checkRegistTime("txtEndTimeHour", "txtEndTimeMinute", aryMessage);
	checkTimes(aryMessage, event);
	checkDirect(aryMessage);
}

/**
 * 始業/終業時刻、休憩1～6、公用外出1～2、私用外出1～2、分単位休暇A1～6、分単位休暇B1～6の時刻チェックを行う。<br>
 * 全て存在しており、一つでも入力されていたら時刻の妥当性確認を行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkTimes(aryMessage, event) {
	var rep= "";
	var aryTimeHour = new Array("txtStartTimeHour", "txtEndTimeHour");
	var aryTimeMinute = new Array("txtStartTimeMinute", "txtEndTimeMinute");
	var aryStartTimeHour = new Array("txtRestStartHour1", "txtRestStartHour2", "txtRestStartHour3", 
			"txtRestStartHour4", "txtRestStartHour5", "txtRestStartHour6", "txtPublicStartHour1", 
			"txtPublicStartHour2", "txtPrivateStartHour1", "txtPrivateStartHour2"
//			,"txtMinutelyHolidayAStartHour1","txtMinutelyHolidayAStartHour2","txtMinutelyHolidayAStartHour3",
//			"txtMinutelyHolidayAStartHour4","txtMinutelyHolidayBStartHour1","txtMinutelyHolidayBStartHour2",
//			"txtMinutelyHolidayBStartHour3","txtMinutelyHolidayBStartHour4"
			);
	var aryStartTimeMinute = new Array("txtRestStartMinute1", "txtRestStartMinute2", "txtRestStartMinute3", 
			"txtRestStartMinute4", "txtRestStartMinute5", "txtRestStartMinute6", "txtPublicStartMinute1", 
			"txtPublicStartMinute2", "txtPrivateStartMinute1", "txtPrivateStartMinute2"
//			,"txtMinutelyHolidayAStartMinute1","txtMinutelyHolidayAStartMinute2","txtMinutelyHolidayAStartMinute3",
//			"txtMinutelyHolidayAStartMinute4","txtMinutelyHolidayBStartMinute1","txtMinutelyHolidayBStartMinute2",
//			"txtMinutelyHolidayBStartMinute3","txtMinutelyHolidayBStartMinute4"
			);
	var aryEndTimeHour = new Array("txtRestEndHour1", "txtRestEndHour2", "txtRestEndHour3", "txtRestEndHour4", 
			"txtRestEndHour5", "txtRestEndHour6", "txtPublicEndHour1", "txtPublicEndHour2", "txtPrivateEndHour1", 
			"txtPrivateEndHour2"
//			, "txtMinutelyHolidayAEndHour1","txtMinutelyHolidayAEndHour2","txtMinutelyHolidayAEndHour3",
//			"txtMinutelyHolidayAEndHour4","txtMinutelyHolidayBEndHour1","txtMinutelyHolidayBEndHour2",
//			"txtMinutelyHolidayBEndHour3","txtMinutelyHolidayBEndHour4"
			);
	var aryEndTimeMinute = new Array("txtRestEndMinute1", "txtRestEndMinute2", "txtRestEndMinute3", 
			"txtRestEndMinute4", "txtRestEndMinute5", "txtRestEndMinute6", "txtPublicEndMinute1", 
			"txtPublicEndMinute2", "txtPrivateEndMinute1", "txtPrivateEndMinute2"
//			,"txtMinutelyHolidayAEndMinute1","txtMinutelyHolidayAEndMinute2","txtMinutelyHolidayAEndMinute3",
//			"txtMinutelyHolidayAEndMinute4","txtMinutelyHolidayBEndMinute1","txtMinutelyHolidayBEndMinute2",
//			"txtMinutelyHolidayBEndMinute3","txtMinutelyHolidayBEndMinute4"
			);
	var startTimeHour = "";
	var startTimeMinute = "";
	var endTimeHour = "";
	var endTimeMinute = "";
	var aryStartTimeHourLength = aryStartTimeHour.length;
	// 休憩1～6、公用外出1～2、私用外出1～2、分単位休暇A1～6、分単位休暇B1～6の時刻チェックと相関チェック
	for (var i = 0; i < aryStartTimeHourLength; i++) {
		rep = getLabel(aryStartTimeHour[i]);
		startTimeHour = getFormValue(aryStartTimeHour[i]);
		startTimeMinute = getFormValue(aryStartTimeMinute[i]);
		endTimeHour = getFormValue(aryEndTimeHour[i]);
		endTimeMinute = getFormValue(aryEndTimeMinute[i]);
		// 項目のどれか一つでも入力されていたら相関チェックを行う
		if(startTimeHour != "" || startTimeMinute != "" || endTimeHour != "" || endTimeMinute != "") {
			if(startTimeHour == "" || startTimeMinute == "" || endTimeHour == "" || endTimeMinute == "") {
				if (aryMessage.length == 0) {
					setFocus(aryStartTimeHour[i]);
				}
				setBgColor(aryStartTimeHour[i], COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_REQUIRED, rep));
				return;
			}
			// 時刻チェック
			rep = getLabel(aryStartTimeHour[i]);
			checkRegistTime(aryStartTimeHour[i], aryStartTimeMinute[i], aryMessage);
			// エラーが発生したら処理を終了する
			if (aryMessage.length != 0) {
				return;
			}
			rep = getLabel(aryEndTimeHour[i]);
			checkRegistTime(aryEndTimeHour[i], aryEndTimeMinute[i], aryMessage);
			// エラーが発生したら処理を終了する
			if (aryMessage.length != 0) {
				return;
			}
			// 各時刻始業時間、終業時間取得
			var startTime = startTimeHour * 60 + parseIntDecimal(startTimeMinute);
			var endTime = endTimeHour * 60 + parseIntDecimal(endTimeMinute);
			// 直行・直帰がない場合
			if(!isCheckableChecked("ckbDirectStart") && !isCheckableChecked("ckbDirectEnd")){
				// 各時刻始業時間が0でない又は終業時間が0でない場合
				if (startTime != 0 || endTime != 0) {
					// 各時刻始業時間が始業時刻より早い場合
					if (startTime < getFormValue(aryTimeHour[0]) * 60 + parseIntDecimal(getFormValue(aryTimeMinute[0]))) {
						// チェック設定
						setBgColor(aryStartTimeHour[i], COLOR_FIELD_ERROR);
						setBgColor(aryStartTimeMinute[i], COLOR_FIELD_ERROR);
						aryMessage.push(getMessage(MSG_REST_CHECK, getLabel(aryStartTimeHour[i])));
						return;
					}
					// 各時刻終業時間が終業時間より早い場合
					if (endTime > getFormValue(aryTimeHour[1]) * 60 + parseIntDecimal(getFormValue(aryTimeMinute[1]))) {
						// チェック設定
						setBgColor(aryEndTimeHour[i], COLOR_FIELD_ERROR);
						setBgColor(aryEndTimeMinute[i], COLOR_FIELD_ERROR);
						aryMessage.push(getMessage(MSG_REST_CHECK, getLabel(aryStartTimeHour[i])));
						return;
					}
					// 各時刻開始時間より終業時刻が早い場合
					if (startTime > endTime) {
						setBgColor(aryEndTimeHour[i], COLOR_FIELD_ERROR);
						setBgColor(aryEndTimeMinute[i], COLOR_FIELD_ERROR);
						var repArray = [getLabel(aryStartTimeHour[i]), getLabel(aryStartTimeHour[i])];
						aryMessage.push(getMessage(MSG_END_TIME_CHECK, repArray));
						return;
					}
				}
			}
		}
	}
	// 分単位の時刻の妥当性確認
	checkMinutelyHolidayTime(aryMessage, event);
}

/**
 * 分単位の時刻の妥当性確認を行う。
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkMinutelyHolidayTime(aryMessage, event) {
	var aryStartTimeHour = new Array("txtMinutelyHolidayAStartHour1","txtMinutelyHolidayAStartHour2","txtMinutelyHolidayAStartHour3",
			"txtMinutelyHolidayAStartHour4","txtMinutelyHolidayBStartHour1","txtMinutelyHolidayBStartHour2",
			"txtMinutelyHolidayBStartHour3","txtMinutelyHolidayBStartHour4");
	var aryStartTimeMinute = new Array("txtMinutelyHolidayAStartMinute1","txtMinutelyHolidayAStartMinute2","txtMinutelyHolidayAStartMinute3",
			"txtMinutelyHolidayAStartMinute4","txtMinutelyHolidayBStartMinute1","txtMinutelyHolidayBStartMinute2",
			"txtMinutelyHolidayBStartMinute3","txtMinutelyHolidayBStartMinute4");
	var aryEndTimeHour = new Array("txtMinutelyHolidayAEndHour1","txtMinutelyHolidayAEndHour2","txtMinutelyHolidayAEndHour3",
			"txtMinutelyHolidayAEndHour4","txtMinutelyHolidayBEndHour1","txtMinutelyHolidayBEndHour2",
			"txtMinutelyHolidayBEndHour3","txtMinutelyHolidayBEndHour4");
	var aryEndTimeMinute = new Array("txtMinutelyHolidayAEndMinute1","txtMinutelyHolidayAEndMinute2","txtMinutelyHolidayAEndMinute3",
			"txtMinutelyHolidayAEndMinute4","txtMinutelyHolidayBEndMinute1","txtMinutelyHolidayBEndMinute2",
			"txtMinutelyHolidayBEndMinute3","txtMinutelyHolidayBEndMinute4");
	var startTimeHour = "";
	var startTimeMinute = "";
	var endTimeHour = "";
	var endTimeMinute = "";
	var aryStartTimeHourLength = aryStartTimeHour.length;
	// 休憩1～6、公用外出1～2、私用外出1～2、分単位休暇A1～6、分単位休暇B1～6の時刻チェックと相関チェック
	for (var i = 0; i < aryStartTimeHourLength; i++) {
		rep = getLabel(aryStartTimeHour[i]);
		startTimeHour = getFormValue(aryStartTimeHour[i]);
		startTimeMinute = getFormValue(aryStartTimeMinute[i]);
		endTimeHour = getFormValue(aryEndTimeHour[i]);
		endTimeMinute = getFormValue(aryEndTimeMinute[i]);
		// 項目のどれか一つでも入力されていたら相関チェックを行う
		if(startTimeHour != "" || startTimeMinute != "" || endTimeHour != "" || endTimeMinute != "") {
			if(startTimeHour == "" || startTimeMinute == "" || endTimeHour == "" || endTimeMinute == "") {
				if (aryMessage.length == 0) {
					setFocus(aryStartTimeHour[i]);
				}
				setBgColor(aryStartTimeHour[i], COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_REQUIRED, rep));
				return;
			}
			// 時刻チェック
			rep = getLabel(aryStartTimeHour[i]);
			checkRegistTime(aryStartTimeHour[i], aryStartTimeMinute[i], aryMessage);
			// エラーが発生したら処理を終了する
			if (aryMessage.length != 0) {
				return;
			}
			rep = getLabel(aryEndTimeHour[i]);
			checkRegistTime(aryEndTimeHour[i], aryEndTimeMinute[i], aryMessage);
			// エラーが発生したら処理を終了する
			if (aryMessage.length != 0) {
				return;
			}
			var startTime = startTimeHour * 60 + parseIntDecimal(startTimeMinute);
			var endTime = endTimeHour * 60 + parseIntDecimal(endTimeMinute);
			if (startTime != 0 || endTime != 0) {
				if (startTime > endTime) {
					setBgColor(aryEndTimeHour[i], COLOR_FIELD_ERROR);
					setBgColor(aryEndTimeMinute[i], COLOR_FIELD_ERROR);
					var repArray = [getLabel(aryStartTimeHour[i]), getLabel(aryStartTimeHour[i])];
					aryMessage.push(getMessage(MSG_END_TIME_CHECK, repArray));
					return;
				}
			}
		}
	}
}

/**
 * 
 * @param targetHour
 * @param targetMinute
 * @param aryMessage
 */
function checkDraftTime(targetHour, targetMinute, aryMessage) {
	// 範囲宣言
	var MIN_HOUR = 0;
	var MAX_HOUR = 47;
	var MIN_MINUTE = 0;
	var MAX_MINUTE = 59;
	var rep = getLabel(targetHour);
	// 時分を取得
	var hour = getFormValue(targetHour);
	var minute = getFormValue(targetMinute);
	var isHourEmpty = hour == "";
	var isMinuteEmpty = minute == "";
	if (isHourEmpty && isMinuteEmpty) {
		return;
	}
	// 時間確認
	if (isHourEmpty) {
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		return;
	} else {
		if (hour < MIN_HOUR || hour > MAX_HOUR) {
			if (aryMessage.length == 0) {
				setFocus(targetHour);
			}
			setBgColor(targetHour, COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
			return;
		}
	}
	// 分確認
	if (isMinuteEmpty) {
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		return;
	} else {
		if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
			if (aryMessage.length == 0) {
				setFocus(targetMinute);
			}
			setBgColor(targetMinute, COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		}
	}
}

function checkRegistTime(targetHour, targetMinute, aryMessage) {
	// 範囲宣言
	var MIN_HOUR = 0;
	var MAX_HOUR = 47;
	var MIN_MINUTE = 0;
	var MAX_MINUTE = 59;
	var rep = getLabel(targetHour);
	var hour = getFormValue(targetHour);
	var minute = getFormValue(targetMinute);
	if (hour == "") {
		// 分単位休暇全休の場合
		if (isCheckableChecked("ckbAllMinutelyHolidayA") || isCheckableChecked("ckbAllMinutelyHolidayB")) {
			return;
		}
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		return;
	}
	if (hour < MIN_HOUR || hour > MAX_HOUR) {
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		return;
	}
	if (minute == "") {
		// 分単位休暇全休の場合
		if (isCheckableChecked("ckbAllMinutelyHolidayA") || isCheckableChecked("ckbAllMinutelyHolidayB")) {
			return;
		}
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
		return;
	}
	if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_CHECK, rep));
	}
}

/**
 * @param aryMessage メッセージ配列
 * @return 無し
 */
function checkDirect(aryMessage) {
	if (isCheckableChecked("ckbDirectStart") || isCheckableChecked("ckbDirectEnd")) {
		// 直行又は直帰にチェックされている場合は勤怠コメントを必須とする
		checkRequired("txtTimeComment", aryMessage);
	}
}

/**
 * 分単位休暇全休の場合の入力チェック
 */
function checkAllMinutelyHoliday(aryMessage) {
	var startTimeHour = "";
	var startTimeMinute = "";
	var endTimeHour = "";
	var endTimeMinute = "";
	var isEmpty = true;
	// 分単位休暇Aに全休チェックの場合
	if (isCheckableChecked("ckbAllMinutelyHolidayA")){
		var aryStartTimeHour = new Array("txtMinutelyHolidayAStartHour1","txtMinutelyHolidayAStartHour2","txtMinutelyHolidayAStartHour3",
				"txtMinutelyHolidayAStartHour4");
		var aryStartTimeHourLength = aryStartTimeHour.length;
		// 分単位休暇A1～4の時刻チェックと相関チェック
		for (var i = 0; i < aryStartTimeHourLength; i++) {
			rep = getLabel(aryStartTimeHour[i]);
			startTimeHour = getFormValue(aryStartTimeHour[i]);
			// 項目のどれか一つでも入力されていたら相関チェックを行う
			if(startTimeHour != "" && startTimeHour != "00") {
				isEmpty = false;
			}	
		}
		// 一つも入っていない場合
		if (aryMessage.length == 0 && isEmpty) {
			setFocus("txtMinutelyHolidayAStartHour1");
			setBgColor("txtMinutelyHolidayAStartHour1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayAStartMinute1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayAEndHour1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayAEndMinute1", COLOR_FIELD_ERROR);
			checkRequired("txtMinutelyHolidayAStartHour1", aryMessage);
		}
	}
	// 分単位休暇Bに全休チェックの場合
	if (isCheckableChecked("ckbAllMinutelyHolidayB")){
		var aryStartTimeHour = new Array("txtMinutelyHolidayBStartHour1","txtMinutelyHolidayBStartHour2",
				"txtMinutelyHolidayBStartHour3","txtMinutelyHolidayBStartHour4"
				);
		var aryStartTimeHourLength = aryStartTimeHour.length;
		// 分単位休暇AB1～4の時刻チェックと相関チェック
		for (var i = 0; i < aryStartTimeHourLength; i++) {
			rep = getLabel(aryStartTimeHour[i]);
			startTimeHour = getFormValue(aryStartTimeHour[i]);
			// 項目のどれか一つでも入力されていたら相関チェックを行う
			if(startTimeHour != "" && startTimeHour != "00") {
				isEmpty = false;
			}	
		}
		// 一つも入っていない場合
		if (aryMessage.length == 0 && isEmpty) {
			setFocus("txtMinutelyHolidayBStartHour1");
			setBgColor("txtMinutelyHolidayBStartHour1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayBStartMinute1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayBEndHour1", COLOR_FIELD_ERROR);
			setBgColor("txtMinutelyHolidayBEndMinute1", COLOR_FIELD_ERROR);
			checkRequired("txtMinutelyHolidayBStartHour1", aryMessage);
		}
	}
}

/**
 * @param aryMessage メッセージ配列
 * @return 無し
 */
function checkTimeEmpty(aryMessage) {
	if (getFormValue("txtStartTimeHour") == ""
		&& getFormValue("txtStartTimeMinute") == ""
		&& getFormValue("txtEndTimeHour") == ""
		&& getFormValue("txtEndTimeMinute") == ""
	) {
		if (aryMessage.length == 0) {
			setFocus("txtStartTimeHour");
		}
		setBgColor("txtStartTimeHour", COLOR_FIELD_ERROR);
		setBgColor("txtStartTimeMinute", COLOR_FIELD_ERROR);
		setBgColor("txtEndTimeHour", COLOR_FIELD_ERROR);
		setBgColor("txtEndTimeMinute", COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_TIME_EMPTY_CHECK, null));
	}
}

/**
 * 文字列を整数に変換する。
 * @param string 解析する文字列
 */
function parseIntDecimal(string) {
	return parseInt(string, 10);
}

/**
 * カレンダ日付要素をクリックした際の追加処理を行う。
 * mospCalendar.jsの内容を上書する。
 */
function onClickCalendarExtra(event) {
	// カレンダ日付を譲渡し検索コマンドを実行する
	submitTransfer(event, null, null, new Array("transferredGenericCode", "Calendar", "transferredDay", getFormValue("hdnTargetDate")), "TM1202");
}
