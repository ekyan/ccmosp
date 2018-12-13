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
import java.util.List;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
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
	 * メッセージコード(処理成功)。<br>
	 * %1%しました。<br>
	 */
	public static final String	MSG_I_PROCESS_SUCCEED			= "PFI0001";
	
	/**
	 * メッセージコード(妥当性確認(数値))。<br>
	 * %1%には数字を入力してください。<br>
	 */
	public static final String	MSG_W_CHECK_NUMERIC				= "PFW0113";
	
	/**
	 * メッセージコード(妥当性確認(形式))。<br>
	 * %1%には正しい形式の値を入力してください。<br>
	 */
	public static final String	MSG_W_CHECK_FORM				= "PFW0115";
	
	/**
	 * メッセージコード(妥当性確認(日付))。<br>
	 * 正しい日付を入力してください。<br>
	 */
	public static final String	MSG_W_CHECK_DATE				= "PFW0116";
	
	/**
	 * メッセージコード(妥当性確認(小数))。<br>
	 * %1%は、整数部%2%桁以内、小数部%3%桁以内で入力してください。<br>
	 */
	public static final String	MSG_W_CHECK_DECIMAL				= "PFW0130";
	
	/**
	 * メッセージコード(日付不正)。<br>
	 * %1%には存在する日付を正しい形式(例：2017/04/01)で入力してください。<br>
	 */
	public static final String	MSG_W_DATE_INVALID				= "PFW0132";
	
	/**
	 * メッセージコード(項目不在)。<br>
	 * 該当する%1%が存在しません。<br>
	 */
	public static final String	MSG_W_NO_ITEM					= "PFW0201";
	
	/**
	 * メッセージコード(社員の状態確認(肯定)時)。<br>
	 * 該当する社員は%1%しています。<br>
	 */
	public static final String	MSG_W_EMPLOYEE_IS				= "PFW0202";
	
	/**
	 * メッセージコード(社員の状態確認(否定)時)。<br>
	 * 該当する社員は%1%していません。<br>
	 */
	public static final String	MSG_W_EMPLOYEE_IS_NOT			= "PFW0203";
	
	/**
	 * メッセージコード(必須ロールユーザ不在)。<br>
	 * 本日時点で、必須ロールコード：%1%を設定しているユーザが、存在しなくなってしまいます。<br>
	 */
	public static final String	MSG_W_NO_NEEDED_ROLE			= "PFW0211";
	
	/**
	 * メッセージコード(入社していないためユーザ作成不可)。<br>
	 * 社員コード：%1%は入社日が登録されていないため、アカウントを作成できません。<br>
	 */
	public static final String	MSG_W_NOT_JOIN_FOR_ACCOUNT		= "PFW0212";
	
	// TODO PFW0219 PlatformHumanBean.addEmployeeHistoryNotExistMessageを変更
	
	/**
	 * メッセージコード(ワークフロー処理失敗)。<br>
	 * ワークフロー処理に失敗しました。ワークフローを確認してください。<br>
	 */
	public static final String	MSG_W_WORKFLOW_PROCESS_FAILED	= "PFW0229";
	
	/**
	 * メッセージコード(履歴情報不在)。<br>
	 * %1%における%2%の履歴情報は存在しません。<br>
	 */
	public static final String	MSG_W_HISTORY_NOT_EXIST			= "PFW0232";
	
	/**
	 * メッセージコード(異なる申請によるデータが存在)。<br>
	 * %1%は異なる申請により登録された%2%が存在するため、操作できません。<br>
	 */
	public static final String	MSG_W_OTHER_DATA_EXIST			= "PFW0236";
	
	/**
	 * メッセージコード(未登録)。<br>
	 * 有効日：%1%時点で社員コード：%2%の%3%は登録されていません。<br>
	 */
	public static final String	MSG_W_UNREGISTERED				= "PFW0237";
	
	/**
	 * メッセージコード(組み合わせ不正)。<br>
	 * %1%と%2%の組み合わせが、正しくありません。<br>
	 */
	public static final String	MSG_W_INVALID_PAIR				= "PFW0238";
	
	/**
	 * メッセージコード(ユーザ数超過)。<br>
	 * %1%のロールを付加されたユーザ数が上限値(%2%)を超えないようにしてください。<br>
	 */
	public static final String	MSG_W_EXCEED_USERS				= "PFW0239";
	
	/**
	 * メッセージコード(パスワード不変)。<br>
	 * パスワードは前回入力値と違うものを入力してください。<br>
	 */
	public static final String	MSG_W_PASSWORD_UNCHANGED		= "PFW9112";
	
	/**
	 * メッセージコード(パスワード有効期限切れ)。<br>
	 * パスワードの有効期限が切れています。パスワードを更新してください。<br>
	 */
	public static final String	MSG_W_PASSWORD_EXPIRE			= "PFW9113";
	
	/**
	 * メッセージコード(旧パスワード不正)。<br>
	 * 現在のパスワードに誤りがあります。<br>
	 */
	public static final String	MSG_W_OLD_PASSWORD				= "PFW9114";
	
	/**
	 * メッセージコード(新パスワード不正)。<br>
	 * 新しいパスワードとパスワード入力確認が異なります。<br>
	 */
	public static final String	MSG_W_NEW_PASSWORD				= "PFW9115";
	
	/**
	 * メッセージコード(パスワード文字種)。<br>
	 * パスワードは英字と数字を組み合わせたものを設定してください。<br>
	 */
	public static final String	MSG_W_PASSWORD_CHAR				= "PFW9116";
	
	/**
	 * メッセージコード(初期パスワード)。<br>
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 */
	public static final String	MSG_W_PASSWORD_INIT				= "PFW9117";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformMessageUtility() {
		// 処理無し
	}
	
	/**
	 * 新規登録しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addInsertNewSucceed(MospParams mospParams) {
		mospParams.addMessage(MSG_I_PROCESS_SUCCEED, PlatformNamingUtility.newInsert(mospParams));
	}
	
	/**
	 * 必ず一件はチェックボックスを選択してください。(PFW0103)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorRequireCheck(MospParams mospParams) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_CHECK);
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
	 * %1%には正しい形式の値を入力してください。(PFW0116)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckForm(MospParams mospParams, String fieldName, Integer row) {
		mospParams.addErrorMessage(MSG_W_CHECK_FORM, getRowedFieldName(mospParams, fieldName, row));
	}
	
	/**
	 * 正しい日付を入力してください。(PFW0116)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorCheckDate(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_CHECK_DATE);
	}
	
	/**
	 * %1%は、整数部%2%桁以内、小数部%3%桁以内で入力してください。(PFW0130)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param fieldName    対象フィールド名
	 * @param integerDigit 整数部桁数
	 * @param decimalDigit 小数部桁数
	 * @param row          対象行インデックス
	 */
	public static void addErrorCheckDecimal(MospParams mospParams, String fieldName, int integerDigit, int decimalDigit,
			Integer row) {
		mospParams.addErrorMessage(MSG_W_CHECK_DECIMAL, getRowedFieldName(mospParams, fieldName, row),
				String.valueOf(integerDigit), String.valueOf(decimalDigit));
	}
	
	/**
	 * 該当する社員が存在しません。(PFW0201)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeNotExist(MospParams mospParams) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, PlatformNamingUtility.employee(mospParams));
	}
	
	/**
	 * コード：codeは、有効日以前の情報が存在しないため、一括更新できません。個別に履歴追加をしてください。(PFW0210)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param code       コード
	 */
	public static void addErrorCodeNotExistBeforeDate(MospParams mospParams, String code) {
		MessageUtility.addErrorMessage(mospParams, PlatformMessageConst.MSG_NO_CODE_BEFORE_DATE, code);
	}
	
	/**
	 * ユーザID：userIdは存在しないため、登録できません。(PFW0214)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param userId ユーザID
	 * @param row    行インデックス
	 */
	public static void addErrorSelectedUserIdNotExist(MospParams mospParams, String userId, Integer row) {
		String rep = getRowedFieldName(mospParams, PlatformNamingUtility.userId(mospParams), row);
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, rep, userId);
	}
	
	/**
	 * ロールコード：roleCodeは存在しないため、登録できません。(PFW0214)<br>
	 * @param mospParams MosP処理情報
	 * @param roleCode   ロールコード
	 * @param row        行インデックス
	 */
	public static void addErrorRoleCodeNotExist(MospParams mospParams, String roleCode, Integer row) {
		MessageUtility.addErrorMessage(mospParams, PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, row,
				PlatformNamingUtility.roleCode(mospParams), roleCode);
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
	 * 該当する%1%が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param name       項目名
	 * @param row        行インデックス
	 */
	public static void addErrorNoItem(MospParams mospParams, String name, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, row, name);
	}
	
	/**
	 * 該当するユーザが存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNoUser(MospParams mospParams) {
		addErrorNoItem(mospParams, PlatformNamingUtility.user(mospParams), null);
	}
	
	/**
	 * 該当するロールが存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorNoRole(MospParams mospParams, Integer row) {
		addErrorNoItem(mospParams, PlatformNamingUtility.role(mospParams), row);
	}
	
	/**
	 * 該当するロール区分が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorNoRoleType(MospParams mospParams, Integer row) {
		addErrorNoItem(mospParams, PlatformNamingUtility.roleType(mospParams), row);
	}
	
	/**
	 * 該当するエクスポート情報が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNoExportInfo(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_NO_ITEM, PlatformNamingUtility.exportInfo(mospParams));
	}
	
	/**
	 * 該当する社員は退職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeRetired(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_W_EMPLOYEE_IS, getRowedFieldName(mospParams, "", row),
				PlatformNamingUtility.retire(mospParams));
	}
	
	/**
	 * 該当する社員は休職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeSuspended(MospParams mospParams, Integer row) {
		mospParams.addErrorMessage(MSG_W_EMPLOYEE_IS, getRowedFieldName(mospParams, "", row),
				PlatformNamingUtility.suspension(mospParams));
	}
	
	/**
	 * 該当する社員は入社していません。(PFW0203)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeNotJoin(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_EMPLOYEE_IS_NOT, PlatformNamingUtility.join(mospParams));
	}
	
	/**
	 * targetDate時点で、必須ロールコード：%2%を設定しているユーザが、存在しなくなってしまいます。(PFW0211)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorNoNeededRole(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_NEEDED_ROLE, DateUtility.getStringDate(targetDate),
				RoleUtility.getNeededRole(mospParams));
	}
	
	/**
	 * 社員コード：%1%は入社日が登録されていないため、アカウントを作成できません。(PFW0212)<br>
	 * @param mospParams   MosP処理情報
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeNotJoinForAccount(MospParams mospParams, String employeeCode) {
		mospParams.addErrorMessage(MSG_W_NOT_JOIN_FOR_ACCOUNT, employeeCode);
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
	public static void addErrorMessageNotExist(MospParams mospParams, String fieldName, String targetCode,
			Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST,
				getRowedFieldName(mospParams, fieldName, row), targetCode);
	}
	
	/**
	 * YYYY/MM/DDにおける対象ユーザの履歴情報は存在しません。(PFW0232)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorUserHistoryNotExist(MospParams mospParams, Date activateDate, Integer row) {
		mospParams.addErrorMessage(MSG_W_HISTORY_NOT_EXIST,
				getRowedFieldName(mospParams, DateUtility.getStringDate(activateDate), row),
				PlatformNamingUtility.targetUser(mospParams));
	}
	
	/**
	 * YYYY/MM/DDにおける対象社員の履歴情報は存在しません。(PFW0232)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorEmployeeHistoryNotExist(MospParams mospParams, Date activateDate, Integer row) {
		mospParams.addErrorMessage(MSG_W_HISTORY_NOT_EXIST,
				getRowedFieldName(mospParams, DateUtility.getStringDate(activateDate), row),
				PlatformNamingUtility.targetEmployee(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは異なる申請により登録された住所情報が存在するため、操作できません。(PFW0236)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorOtherAddressExist(MospParams mospParams, Date activateDate, Integer row) {
		mospParams.addErrorMessage(MSG_W_OTHER_DATA_EXIST,
				getRowedFieldName(mospParams, DateUtility.getStringDate(activateDate), row),
				PlatformNamingUtility.addressInfo(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは異なる申請により登録された電話情報が存在するため、操作できません。(PFW0236)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorOtherPhoneExist(MospParams mospParams, Date activateDate, Integer row) {
		mospParams.addErrorMessage(MSG_W_OTHER_DATA_EXIST,
				getRowedFieldName(mospParams, DateUtility.getStringDate(activateDate), row),
				PlatformNamingUtility.phoneInfo(mospParams));
	}
	
	/**
	 * 有効日：%1%時点で社員コード：%2%の%3%は登録されていません。(PFW0237)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param employeeCode 社員コード
	 */
	public static void addErrorUnregisteredMailAddress(MospParams mospParams, Date activateDate, String employeeCode) {
		mospParams.addErrorMessage(MSG_W_UNREGISTERED, DateUtility.getStringDate(activateDate), employeeCode,
				PlatformNamingUtility.mailAddress(mospParams));
	}
	
	/**
	 * ユーザIDとメールアドレスの組み合わせが、正しくありません。(PFW0238)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorInvalidUserAndAddress(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_INVALID_PAIR, PlatformNamingUtility.userId(mospParams),
				PlatformNamingUtility.mailAddress(mospParams));
	}
	
	/**
	 * %1%のロールを付加されたユーザ数が上限値(%2%)を超えないようにしてください。(PFW0239)<br>
	 * @param mospParams MosP処理情報
	 * @param roleName   ロール名称
	 * @param limit      上限値
	 */
	public static void addErrorExceedUsers(MospParams mospParams, String roleName, int limit) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EXCEED_USERS, roleName, String.valueOf(limit));
	}
	
	/**
	 * %1%には%2%を入力してください。(PFW0237)<br>
	 * <br>
	 * 利用可能文字列を%2%に入れる。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param availables 利用可能文字列リスト
	 * @param row        行インデックス
	 */
	public static void addErrorAvailableChars(MospParams mospParams, String fieldName, List<String> availables,
			Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, getRowedFieldName(mospParams, fieldName, row),
				getAvailableCharString(mospParams, availables));
	}
	
	/**
	 * 下書しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getDraftSucceed(MospParams mospParams) {
		return mospParams.getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED, PlatformNamingUtility.draft(mospParams));
	}
	
	/**
	 * 検索できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams  MosP処理情報
	 */
	public static void addMessageSearchFailed(MospParams mospParams) {
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_FAILED, PlatformNamingUtility.search(mospParams));
	}
	
	/**
	 * そのコードは既に存在しているため、別のコードを入力してください。(PFW0204)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorDuplicate(MospParams mospParams) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_REG_DUPLICATE);
	}
	
	/**
	 * ユーザID：userIdは既に存在しているため、登録できません。(PFW0215)<br>
	 * @param mospParams MosP処理情報
	 * @param userId     ユーザID
	 * @param row        行インデックス
	 */
	public static void addErrorUserIdExist(MospParams mospParams, String userId, Integer row) {
		MessageUtility.addErrorMessage(mospParams, PlatformMessageConst.MSG_SELECTED_CODE_EXIST, row,
				PlatformNamingUtility.userId(mospParams), userId);
	}
	
	/**
	 * %1%には存在する日付を正しい形式(例：2017/04/01)で入力してください。(PFW0132)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名称
	 * @param row        行インデックス
	 */
	public static void addErrorDateInvalid(MospParams mospParams, String fieldName, Integer row) {
		mospParams.addErrorMessage(MSG_W_DATE_INVALID, getRowedFieldName(mospParams, fieldName, row));
	}
	
	/**
	 * パスワードは前回入力値と違うものを入力してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordUnchanged(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_UNCHANGED);
	}
	
	/**
	 * パスワードの有効期限が切れています。パスワードを更新してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordExpire(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_EXPIRE);
	}
	
	/**
	 * 現在のパスワードに誤りがあります。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorOldPassword(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OLD_PASSWORD);
	}
	
	/**
	 * 新しいパスワードとパスワード入力確認が異なります。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNewPassword(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NEW_PASSWORD);
	}
	
	/**
	 * パスワードは英字と数字を組み合わせたものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWarningPasswordChar(MospParams mospParams) {
		return mospParams.getMessage(MSG_W_PASSWORD_CHAR);
	}
	
	/**
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordInit(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_INIT);
	}
	
	/**
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWarningPasswordInit(MospParams mospParams) {
		return mospParams.getMessage(MSG_W_PASSWORD_INIT);
	}
	
	/**
	 * パスワードは%2%文字以上を入力してください。<br>
	 * @param mospParams  MosP処理情報
	 * @param minPassword パスワード最低文字数
	 * @return メッセージ
	 */
	public static String getWarningPasswordMin(MospParams mospParams, String minPassword) {
		return mospParams.getMessage(PlatformMessageConst.MSG_MIN_LENGTH_ERR,
				PlatformNamingUtility.password(mospParams), minPassword);
	}
	
	/**
	 * 社員コード名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 社員コード名称
	 */
	public static String getNameEmployeeCode(MospParams mospParams) {
		return PlatformNamingUtility.employeeCode(mospParams);
	}
	
	/**
	 * 利用可能文字列リストをメッセージ用文字列に変換する。<br>
	 * <br>
	 * ""の場合は、空白と出力する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param availables 利用可能文字列リスト
	 * @return メッセージ用文字列
	 */
	public static String getAvailableCharString(MospParams mospParams, List<String> availables) {
		// 配列を準備
		String[] array = new String[availables.size()];
		// インデックス準備
		int i = 0;
		// 利用可能文字毎に処理
		for (String available : availables) {
			// ""の場合は空白に変換
			array[i++] = available.equals("") ? PlatformNamingUtility.blank(mospParams) : available;
		}
		return MospUtility.toSeparatedString(array, PlatformNamingUtility.touten(mospParams));
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
	
}
