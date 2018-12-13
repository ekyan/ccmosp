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
package jp.mosp.platform.utils;

import java.util.Date;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.constant.PlatformMessageConst;

/**
 * @author mind
 * 入力チェックに有効な共通機能を提供する。<br/><br/>
 */
public class InputCheckUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private InputCheckUtility() {
		// 処理無し
	}
	
	/**
	 * 必須入力確認
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkRequired(String targetValue, String[] targetRep, MospParams mospParams) {
		String reqValue = targetValue;
		if (reqValue == null) {
			reqValue = "";
		}
		if (!ValidateUtility.chkRequired(reqValue)) {
			// 必須メッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED, targetRep);
		}
	}
	
	/**
	 * 数値妥当性確認
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkNumber(String targetValue, String[] targetRep, MospParams mospParams) {
		String reqValue = targetValue;
		if (reqValue == null) {
			reqValue = "0";
		}
		if (!chkNumber(reqValue)) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, targetRep);
		}
	}
	
	/**
	 * コード妥当性確認
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkCode(String targetValue, String[] targetRep, MospParams mospParams) {
		String reqValue = targetValue;
		if (reqValue == null) {
			reqValue = "";
		}
		if (!chkCode(reqValue)) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, targetRep);
		}
	}
	
	/**
	 * 日付妥当性確認
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkDate(int year, int month, int day, String[] targetRep, MospParams mospParams) {
		if (!ValidateUtility.chkDate(year, month - 1, day)) {
			// 日付の妥当性チェックメッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 日付妥当性確認
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkDate(String year, String month, String day, String[] targetRep, MospParams mospParams) {
		try {
			checkDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), targetRep, mospParams);
		} catch (NumberFormatException e) {
			// 日付の妥当性チェックメッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 時間妥当性確認。<br>
	 * @param hour   時
	 * @param minute 分
	 * @param second 秒
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkTime(int hour, int minute, int second, String[] targetRep, MospParams mospParams) {
		if (!ValidateUtility.chkTime(hour, minute, second)) {
			// 時間妥当性エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 期間妥当性確認。<br>
	 * @param date      確認対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @param targetRep メッセージ置換文字配列
	 * @param mospParams 共通引数
	 */
	public static void checkTerm(Date date, Date startDate, Date endDate, String[] targetRep, MospParams mospParams) {
		if (!ValidateUtility.chkTerm(date, startDate, endDate)) {
			// 期間妥当性エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 文字列長確認(最大文字数)。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @param targetName 対象項目名
	 * @param mospParams 共通引数
	 */
	public static void checkLength(String value, int maxLength, String targetName, MospParams mospParams) {
		if (!ValidateUtility.chkLength(value, maxLength)) {
			String[] rep = { targetName, String.valueOf(maxLength) };
			// 桁数エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, rep);
		}
	}
	
	private static boolean chkCode(String value) {
		return ValidateUtility.chkRegex("\\w*", value);
	}
	
	private static boolean chkNumber(String value) {
		return ValidateUtility.chkRegex("\\d*", value);
	}
	
}
