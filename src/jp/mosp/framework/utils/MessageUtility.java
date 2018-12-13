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

import java.util.Date;

import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.constant.PlatformMessageConst;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * フレームワークにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class MessageUtility {
	
	/**
	 * メッセージコード(フラグ値不正)。<br>
	 * %1%には、0か1を入力してください。<br>
	 */
	protected static final String	MSG_FLAG_INVALID	= "PFW0127";
	
	/**
	 * メッセージコード(限界値超)。<br>
	 * %1%には、%2%以上を入力してください。<br>
	 */
	protected static final String	MSG_UNDER_LIMIT		= "PFW0128";
	
	/**
	 * メッセージコード(限界値超)。<br>
	 * %1%には、%2%以下を入力してください。<br>
	 */
	protected static final String	MSG_OVER_LIMIT		= "PFW0129";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MessageUtility() {
		// 処理無し
	}
	
	/**
	 * 有効/無効には、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorMessageActivateOrInactivateInvalid(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_FLAG_INVALID,
				getRowedFieldName(mospParams, getNameActivateOrInactivate(mospParams), row));
	}
	
	/**
	 * 削除フラグには、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorMessageDeleteFlagInvalid(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_FLAG_INVALID, getRowedFieldName(mospParams, getNameDeleteFlag(mospParams), row));
	}
	
	/**
	 * %1%には、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorMessageFlagInvalid(MospParams mospParams, String fieldName, Integer row) {
		mospParams.addErrorMessage(MSG_FLAG_INVALID, getRowedFieldName(mospParams, fieldName, row));
	}
	
	/**
	 * %1%には、%2%以上を入力してください。(PFW0128)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 * @param row        対象行インデックス
	 */
	public static void addErrorMessageUnderLimit(MospParams mospParams, String fieldName, int limit, Integer row) {
		mospParams.addErrorMessage(MSG_UNDER_LIMIT, getRowedFieldName(mospParams, fieldName, row),
				String.valueOf(limit));
	}
	
	/**
	 * %1%には、%2%以下を入力してください。(PFW0129)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 * @param row        対象行インデックス
	 */
	public static void addErrorMessageOverLimit(MospParams mospParams, String fieldName, int limit, Integer row) {
		mospParams
			.addErrorMessage(MSG_OVER_LIMIT, getRowedFieldName(mospParams, fieldName, row), String.valueOf(limit));
	}
	
	/**
	 * %1%：%2%は%3%時点で無効となります。(PFW0213)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 */
	public static void addErrorMessageInactive(MospParams mospParams, String fieldName, String targetCode,
			Date targetDate, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK,
				getRowedFieldName(mospParams, fieldName, row), targetCode, DateUtility.getStringDate(targetDate));
	}
	
	/**
	 * %1%：%2%は存在しないため、登録できません。(PFW0214)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 * @param row        行インデックス
	 */
	public static void addErrorMessageNotExist(MospParams mospParams, String fieldName, String targetCode, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST,
				getRowedFieldName(mospParams, fieldName, row), targetCode);
	}
	
	/**
	 * 行番号が付加されたフィールド名を取得する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	public static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 対象行インデックス確認
		if (row == null) {
			return fieldName;
		}
		// メッセージ置換文字列追加
		return getRowColonName(mospParams, row.intValue()) + fieldName;
	}
	
	/**
	 * 名称(行row+1：)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param row 対象行インデックス
	 * @return 名称(行row+1：)
	 */
	protected static String getRowColonName(MospParams mospParams, int row) {
		return mospParams.getName("Row") + String.valueOf(row + 1) + mospParams.getName("Colon");
	}
	
	/**
	 * 列名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 列名称
	 */
	protected static String getNameRow(MospParams mospParams) {
		return mospParams.getName("Row");
	}
	
	/**
	 * ：名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：名称
	 */
	protected static String getNameColon(MospParams mospParams) {
		return mospParams.getName("Colon");
	}
	
	/**
	 * 有効/無効名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 有効/無効
	 */
	protected static String getNameActivateOrInactivate(MospParams mospParams) {
		return mospParams.getName("Effectiveness", "Slash", "Inactivate");
	}
	
	/**
	 * 削除フラグ名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 削除フラグ
	 */
	protected static String getNameDeleteFlag(MospParams mospParams) {
		return mospParams.getName("Delete", "Flag");
	}
	
}
