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
package jp.mosp.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.constant.MospConst;

/**
 * 妥当性確認に有用なメソッドを提供する。<br><br>
 */
public final class ValidateUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private ValidateUtility() {
		// 処理無し
	}
	
	/**
	 * 必須確認。<br>
	 * @param value 確認対象
	 * @return true/false
	 */
	public static boolean chkRequired(Object value) {
		if (value instanceof String) {
			return !value.equals("");
		}
		return value != null;
	}
	
	/**
	 * 正規表現による文字列パターン確認。<br>
	 * @param regex 正規表現
	 * @param value 確認対象
	 * @return true/false
	 */
	public static boolean chkRegex(String regex, String value) {
		if (value.matches(regex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 数値(double)確認。<br>
	 * @param value 確認対象
	 * @return true/false
	 */
	public static boolean chkNumeric(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 日付妥当性確認。<br>
	 * @param year   年
	 * @param month  月(0～11)
	 * @param day    日
	 * @return true/false
	 */
	public static boolean chkDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(year, month, day);
		try {
			calendar.getTime();
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 時間妥当性確認。<br>
	 * @param hour   時
	 * @param minute 分
	 * @param second 秒
	 * @return true/false
	 */
	public static boolean chkTime(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(MospConst.DEFAULT_YEAR, 0, 1, hour, minute, second);
		try {
			calendar.getTime();
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 期間妥当性確認。<br>
	 * @param date      確認対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @return true/false
	 */
	public static boolean chkTerm(Date date, Date startDate, Date endDate) {
		if (startDate.compareTo(date) > 0 || endDate.compareTo(date) < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 文字列長確認(最大文字数)。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @return true/false
	 */
	public static boolean chkLength(String value, int maxLength) {
		String regex = "(?s).{0," + String.valueOf(maxLength) + "}";
		if (!ValidateUtility.chkRegex(regex, value)) {
			return false;
		}
		return true;
	}
	
	/**
	 * バイト数(表示上)確認。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大バイト数(表示上)
	 * @return true/false
	 */
	public static boolean chkByteLength(String value, int maxLength) {
		// 値確認
		if (value == null || value.isEmpty()) {
			return true;
		}
		// バイト数確認(日本語を2バイトとして計算するためShift-JISでバイト数を取得)
		try {
			return value.getBytes("Shift-JIS").length <= maxLength;
		} catch (UnsupportedEncodingException e) {
			return value.getBytes().length <= maxLength;
		}
	}
	
}
