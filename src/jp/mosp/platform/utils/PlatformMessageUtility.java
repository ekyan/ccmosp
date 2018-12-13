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

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.platform.constant.PlatformMessageConst;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * プラットフォームにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class PlatformMessageUtility {
	
	/**
	 * メッセージコード(妥当性確認(数値))。<br>
	 * %1%には数字を入力してください。<br>
	 */
	public static final String	MSG_W_CHECK_NUMERIC				= "PFW0113";
	
	/**
	 * メッセージコード(社員の状態確認(肯定)時)。<br>
	 * 該当する社員は%1%しています。<br>
	 */
	public static final String	MSG_W_EMPLOYEE_IS				= "PFW0202";
	
	/**
	 * メッセージコード(ワークフロー処理失敗)。<br>
	 * ワークフロー処理に失敗しました。ワークフローを確認してください。<br>
	 */
	public static final String	MSG_W_WORKFLOW_PROCESS_FAILED	= "PFW0229";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformMessageUtility() {
		// 処理無し
	}
	
	/**
	 * %1%には数字を入力してください。(PFW0113)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckNumeric(MospParams mospParams, String fieldName, Integer row) {
		mospParams.addErrorMessage(MSG_W_CHECK_NUMERIC, getRowedFieldName(mospParams, fieldName, row));
	}
	
	/**
	 * 該当する社員が存在しません。(PFW0201)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeNotExist(MospParams mospParams) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, getNameEmployee(mospParams));
	}
	
	/**
	 * ユーザID：userIdは存在しないため、登録できません。(PFW0214)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param userId ユーザID
	 * @param row    行インデックス
	 */
	public static void addErrorSelectedUserIdNotExist(MospParams mospParams, String userId, Integer row) {
		String rep = getRowedFieldName(mospParams, getNameUserId(mospParams), row);
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, rep, userId);
	}
	
	/**
	 * ワークフロー処理に失敗しました。ワークフローを確認してください。(PFW0229)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkflowProcessFailed(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_WORKFLOW_PROCESS_FAILED);
	}
	
	/**
	 * 該当する社員は退職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeRetired(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_W_EMPLOYEE_IS, getRowedFieldName(mospParams, "", row),
				getNameRetirement(mospParams));
	}
	
	/**
	 * 該当する社員は休職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeSuspended(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_W_EMPLOYEE_IS, getRowedFieldName(mospParams, "", row),
				getNameSuspension(mospParams));
	}
	
	/**
	 * {@link MessageUtility#getRowedFieldName(MospParams, String, Integer)}
	 * を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	protected static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 行番号が付加されたフィールド名を取得
		return MessageUtility.getRowedFieldName(mospParams, fieldName, row);
	}
	
	/**
	 * 下書しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getDraftSucceed(MospParams mospParams) {
		return mospParams.getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED, getNameDraft(mospParams));
	}
	
	/**
	 * 社員コード名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 社員コード名称
	 */
	public static String getNameEmployeeCode(MospParams mospParams) {
		return mospParams.getName("Employee", "Code");
	}
	
	/**
	 * 有効日名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 有効日名称
	 */
	public static String getNameActivateDate(MospParams mospParams) {
		return mospParams.getName("ActivateDate");
	}
	
	/**
	 * 社員名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 社員名称
	 */
	public static String getNameEmployee(MospParams mospParams) {
		return mospParams.getName("Employee");
	}
	
	/**
	 * ユーザID名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ユーザID名称
	 */
	public static String getNameUserId(MospParams mospParams) {
		return mospParams.getName("User", "Id");
	}
	
	/**
	 * 下書名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 下書名称
	 */
	public static String getNameDraft(MospParams mospParams) {
		return mospParams.getName("WorkPaper");
	}
	
	/**
	 * 退職名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 退職名称
	 */
	public static String getNameRetirement(MospParams mospParams) {
		return mospParams.getName("RetirementOn");
	}
	
	/**
	 * 休職名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休職名称
	 */
	public static String getNameSuspension(MospParams mospParams) {
		return mospParams.getName("RetirementLeave");
	}
	
}
