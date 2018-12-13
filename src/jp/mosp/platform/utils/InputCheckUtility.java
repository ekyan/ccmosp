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
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;

/**
 * @author mind
 * 入力チェックに有効な共通機能を提供する。<br/><br/>
 */
public class InputCheckUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	/*private InputCheckUtility() {
		// 処理無し
	}*/
	
	/**
	 * 必須入力確認
	 * @param mospParams 共通引数
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkRequired(MospParams mospParams, Object targetValue, String... targetRep) {
		
		if (!ValidateUtility.chkRequired(targetValue)) {
			// 必須メッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED, targetRep);
		}
	}
	
	/**
	 * 必須確認を行う。(インポート共用)<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams	共通設定
	 * @param value     	確認対象
	 * @param fieldName 	確認対象フィールド名
	 * @param row       	行インデックス
	 */
	public static void checkRequiredGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRequired(value) == false) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED,
					getRowedFieldName(mospParams, fieldName, row));
		}
	}
	
	/**
	 * 文字列長(最大文字数)を確認する。(インポート共用)<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkLengthGeneral(MospParams mospParams, String value, int maxLength, String fieldName,
			Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkLength(value, maxLength) == false) {
			String[] rep = { getRowedFieldName(mospParams, fieldName, row), String.valueOf(maxLength) };
			mospParams.addErrorMessage(PlatformMessageConst.MSG_MAX_LENGTH_ERR, rep);
		}
	}
	
	/**
	 * 対象文字列が半角英数字であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkTypeCodeGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[A-Za-z0-9]*", value) == false) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_ALP_NUM_CHECK_AMP,
					getRowedFieldName(mospParams, fieldName, row));
		}
	}
	
	/**
	 * 対象文字列がカナであることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkTypeKanaGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[｡-ﾟ -~]*", value) == false) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP,
					getRowedFieldName(mospParams, fieldName, row));
		}
	}
	
	/**
	 * 行番号が付加されたフィールド名を取得する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param mospParams	共通設定
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	protected static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 対象行インデックス確認
		if (row == null) {
			return fieldName;
		}
		return mospParams.getName("Row") + String.valueOf(row + 1) + mospParams.getName("Colon") + fieldName;
	}
	
	/**
	 * 数値妥当性確認
	 * @param mospParams 共通引数
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkNumber(MospParams mospParams, String targetValue, String... targetRep) {
		String reqValue = targetValue;
		if (reqValue == null) {
			reqValue = "0";
		}
		if (!chkNumber(reqValue)) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, targetRep);
		}
	}
	
	/**
	 * 数値妥当性確認（インポート）
	 * @param mospParams 共通引数
	 * @param targetValue 対象値
	 * @param fieldName 対象名称
	 * @param fieldPropertyName 対象の属性名称（例：数値）
	 * @param row 行
	 */
	public static void checkNumberGeneral(MospParams mospParams, String targetValue, String fieldName,
			String fieldPropertyName, Integer row) {
		String reqValue = targetValue;
		
		if (reqValue == null) {
			reqValue = "0";
		}
		
		// メッセージ作成
		String[] message = new String[]{ getRowedFieldName(mospParams, fieldName, row), fieldPropertyName };
		
		if (!chkNumber(reqValue)) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, message);
		}
	}
	
	/**
	 * コード妥当性確認
	 * @param mospParams 共通引数
	 * @param targetValue 対象値
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkCode(MospParams mospParams, String targetValue, String... targetRep) {
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
	 * @param mospParams 共通引数
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkDate(MospParams mospParams, int year, int month, int day, String... targetRep) {
		if (!ValidateUtility.chkDate(year, month - 1, day)) {
			// 日付の妥当性チェックメッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 日付妥当性確認
	 * @param mospParams 共通引数
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkDate(MospParams mospParams, String year, String month, String day, String... targetRep) {
		try {
			checkDate(mospParams, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), targetRep);
		} catch (NumberFormatException e) {
			// 日付の妥当性チェックメッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 日付妥当性確認（人事汎用機能用）
	 * @param mospParams 共通引数
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param fieldName メッセージ置換文字配列
	 */
	public static void checkDateGeneral(MospParams mospParams, String year, String month, String day,
			String[] fieldName) {
		// 空の場合はチェックしない
		if (year == null || month == null || day == null) {
			return;
		}
		if (year.isEmpty() && month.isEmpty() && day.isEmpty()) {
			return;
		}
		
		try {
			// 勤務形態インポートでは対象(年)、人事汎用では項目名1つのため
			String fieldName1 = fieldName[0];
			String fieldName2 = fieldName[0];
			String fieldName3 = fieldName[0];
			if (fieldName.length == 3) {
				fieldName2 = fieldName[1];
				fieldName3 = fieldName[2];
			}
			// 年妥当性確認
			checkYear(mospParams, year, fieldName1);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 月妥当性確認
			checkMonth(mospParams, month, fieldName2);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			if (!chkNumber(day)) {
				// 形式チェック （数値）
				mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName3);
				return;
			}
			
			// 日付チェック
			if (!ValidateUtility.chkDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day))) {
				// 日付形式チェック
				mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
				return;
				
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
		}
		
	}
	
	/**
	 * 年妥当性確認。<br>
	 * @param mospParams MosP処理情報
	 * @param year 年
	 * @param fieldName 項目名(月)
	 */
	public static void checkYear(MospParams mospParams, String year, String fieldName) {
		try {
			if (!chkNumber(year)) {
				// 形式チェック （数値）
				mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
				return;
			}
			// 年の超過確認
			if (!ValidateUtility.chkLength(year, 4)) {
				// 桁数エラー
				mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, fieldName,
						PlatformConst.TEXT_NUMBER_FOUR);
				return;
			}
			// 1000年未満のチェック
			if (Integer.parseInt(year) < 1000) {
				// 桁数エラー
				mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, fieldName,
						PlatformConst.TEXT_NUMBER_FOUR);
				return;
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
		}
	}
	
	/**
	 * 年妥当性確認。<br>
	 * @param mospParams MosP処理情報
	 * @param month 月
	 * @param fieldName 名称(月)
	 */
	public static void checkMonth(MospParams mospParams, String month, String fieldName) {
		try {
			if (!chkNumber(month)) {
				// 形式チェック （数値）
				mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
				return;
			}
			if (!ValidateUtility.chkLength(month, 2)) {
				// 桁数エラー
				mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, fieldName, "2");
				return;
			}
			// 1以上チェック
			if (Integer.parseInt(month) < 1) {
				// エラーメッセージ追加
				MessageUtility.addErrorMessageUnderLimit(mospParams, fieldName, 1, null);
			}
			// 12以下チェック
			if (Integer.parseInt(month) > 12) {
				// エラーメッセージ追加
				MessageUtility.addErrorMessageOverLimit(mospParams, fieldName, 12, null);
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, fieldName);
		}
	}
	
	/**
	 * 時間妥当性確認。<br>
	 * @param mospParams 共通引数
	 * @param hour   時
	 * @param minute 分
	 * @param second 秒
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkTime(MospParams mospParams, int hour, int minute, int second, String... targetRep) {
		if (!ValidateUtility.chkTime(hour, minute, second)) {
			// 時間妥当性エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 期間妥当性確認。<br>
	 * @param mospParams 共通引数
	 * @param date      確認対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @param targetRep メッセージ置換文字配列
	 */
	public static void checkTerm(MospParams mospParams, Date date, Date startDate, Date endDate, String... targetRep) {
		if (date == null || startDate == null || endDate == null) {
			return;
		}
		if (!ValidateUtility.chkTerm(date, startDate, endDate)) {
			// 期間妥当性エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_DATE, targetRep);
		}
	}
	
	/**
	 * 文字列長確認(最大文字数)。<br>
	 * @param mospParams 共通引数
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @param targetName 対象項目名
	 */
	public static void checkLength(MospParams mospParams, String value, int maxLength, String targetName) {
		if (value == null) {
			return;
		}
		if (!ValidateUtility.chkLength(value, maxLength)) {
			String[] rep = { targetName, String.valueOf(maxLength) };
			// 桁数エラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_DIGIT_NUMBER, rep);
		}
	}
	
	/**
	 * 対象コード存在確認（人事汎用機能用（インポート共用））
	 * @param mospParams 共通引数
	 * @param codeValue チェック対象値
	 * @param codeKey コード区分名称
	 * @param fieldName メッセージ文字
	 */
	public static void checkExistCodeGeneral(MospParams mospParams, String codeValue, String codeKey,
			String fieldName) {
		// 対象値が空の場合はチェックしない
		if (codeValue == null) {
			return;
			
		}
		if (codeValue.isEmpty()) {
			return;
		}
		
		// コードキーが未設定
		if (codeKey == null) {
			// コードキー取得出来ない場合はエラー
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT, codeValue);
		}
		// コード配列を取得
		String[][] codeArrays = MospUtility.getCodeArray(mospParams, codeKey, false);
		// コード毎に処理
		for (String[] codeArray : codeArrays) {
			// 対象コードが存在する場合
			if (codeArray[0].equals(codeValue)) {
				// 処理終了
				return;
			}
		}
		// コード存在エラー(対象コードがコード配列に存在しない場合)
		mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED, fieldName);
	}
	
	/**
	 * 対象文字列がメールフォーマットであることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkTypeMailGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 入力値が空白の場合、チェックを行わない
		if (value.isEmpty()) {
			return;
		}
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[!#-9A-~]+@+[A-Za-z0-9]+.+[^.]$", value) == false) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_ALP_SIGN_NUM_CHECK_AMP,
					getRowedFieldName(mospParams, fieldName, row));
		}
		
	}
	
	/**
	 * 対象文字列が半角英字+半角スペースのみであることを確認する。（人事汎用機能用）<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 */
	public static void checkTypeLowerAlphabetSpace(MospParams mospParams, String value, String fieldName) {
		// 対象値が空の場合はチェックしない
		if (value == null) {
			return;
			
		}
		if (value.isEmpty()) {
			return;
		}
		
		// 半角英字であるか正規表現で確認
		if (ValidateUtility.chkRegex("[a-zA-Z\\s]+", value) == false) {
			// メッセージ作成
			String[] message = new String[]{ fieldName, mospParams.getName("LowerAlphaCharacter")
					+ mospParams.getName("Or") + mospParams.getName("SpaceKana") };
			
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, message);
		}
		
	}
	
	/**
	 * 対象文字列がハイフンと半角英数字であることを確認する<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param fieldPropertyName 確認対象フィールド属性
	 */
	public static void checkTypeHyhonCode(MospParams mospParams, String value, String fieldName,
			String fieldPropertyName) {
		// 対象値が空の場合はチェックしない
		if (value == null) {
			return;
			
		}
		if (value.isEmpty()) {
			return;
		}
		
		// 半角英字であるか正規表現で確認
		if (ValidateUtility.chkRegex("[A-Za-z0-9-]+", value) == false) {
			// メッセージ作成
			String[] message = new String[]{ fieldName, fieldPropertyName };
			
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, message);
		}
		
	}
	
	private static boolean chkCode(String value) {
		return ValidateUtility.chkRegex("\\w*", value);
	}
	
	private static boolean chkNumber(String value) {
		return ValidateUtility.chkRegex("\\d*", value);
	}
	
}
