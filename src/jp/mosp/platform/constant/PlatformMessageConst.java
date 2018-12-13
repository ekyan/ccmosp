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
package jp.mosp.platform.constant;

/**
 * MosPプラットフォームで用いるメッセージキーの定数を宣言する。<br><br>
 */
public class PlatformMessageConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformMessageConst() {
		// 処理無し
	}
	
	
	/**
	 * パスワード初期化時のメッセージコード。<br>
	 */
	public static final String	MSG_PASSWORD_INIT								= "PFQ9111";
	// クライアントメッセージコード
	/**
	 * 入力内容エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_INPUT										= "PFW0101";
	/**
	 * 未入力時のメッセージコード。<br>
	 */
	public static final String	MSG_REQUIRED									= "PFW0102";
	/**
	 * 必須確認(チェックボックス)エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_CHECK										= "PFW0103";
	/**
	 * 日付入力値チェックエラー時のメッセージコード。<br>
	 */
	public static final String	MSG_INPUT_DATE									= "PFW0104";
	/**
	 * 文字種別チェックエラー時のメッセージコード。<br>
	 */
	public static final String	MSG_CHR_TYPE									= "PFW0105";
	/**
	 * 桁数エラーのメッセージコード。<br>
	 */
	public static final String	MSG_DIGIT_NUMBER								= "PFW0106";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_EFFECTIVE_DAY								= "PFW0107";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_CLIENT										= "PFW0108";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_INPUT_ERROR									= "PFW0109";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_ALP_NUM_CHECK								= "PFW0110";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_ALP_NUM_CHECK_AMP							= "PFW0111";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_NUMBER_CHECK								= "PFW0112";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_NUMBER_CHECK_AMP							= "PFW0113";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_INPUT_FORM									= "PFW0114";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_INPUT_FORM_AMP								= "PFW0115";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_DATE_CHECK									= "PFW0116";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_YEAR_CHECK									= "PFW0117";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_MONTH_CHECK									= "PFW0118";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_DAY_CHECK_OUTRANGE							= "PFW0119";
	/**
	 * 有効日設定エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_DAY_CHECK									= "PFW0120";
	/**
	 * 時間不正時のメッセージコード。<br>
	 */
	public static final String	MSG_TIME_CHECK									= "PFW0121";
	/**
	 * 最低文字数の妥当性が確認できなかった場合のメッセージコード。<br>
	 */
	public static final String	MSG_MIN_LENGTH_ERR								= "PFW0122";
	/**
	 * 最大文字数の妥当性が確認できなかった場合のメッセージコード。<br>
	 */
	public static final String	MSG_MAX_LENGTH_ERR								= "PFW0123";
	/**
	 * 英数字、一部記号("_"、"."、"-"、"@")の妥当性が確認できなかった場合のメッセージコード。<br>
	 */
	public static final String	MSG_ALP_SIGN_NUM_CHECK_AMP						= "PFW0125";
	
	/**
	 * バイト数(表示上)の妥当性が確認できなかった場合のメッセージコード。<br>
	 */
	public static final String	MSG_BYTE_LENGTH_ERR								= "PFW0126";
	
	/**
	 * 該当項目が存在しない時のメッセージコード。<br>
	 */
	public static final String	MSG_NO_ITEM										= "PFW0201";
	
	/**
	 * 既に登録している情報の二重登録エラーメッセージコード。<br>
	 */
	public static final String	MSG_REG_DUPLICATE								= "PFW0204";
	/**
	 * 履歴追加時の該当有効日における重複エラーメッセージ。<br>
	 */
	public static final String	MSG_HIST_ALREADY_EXISTED						= "PFW0205";
	/**
	 * 該当データが既に更新されていた場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_UPDATE_OTHER_USER							= "PFW0206";
	/**
	 * 該当コードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_CODE_IS_USED								= "PFW0207";
	/**
	 * 該当コードが経路として使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_CODE_IS_USED_AS_ROUTE						= "PFW0208";
	/**
	 * 自らを経路に含めている場合の警告メッセージ。<br>
	 */
	public static final String	MSG_ROUTE_CONTAINS_CODE							= "PFW0209";
	/**
	 * 有効日以前の情報が存在しない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_NO_CODE_BEFORE_DATE							= "PFW0210";
	
	/**
	 * 対象日時点で無効の場合のマスタ確認エラーメッセージ。<br>
	 */
	public static final String	MSG_INACTIVE_DAY_CHECK							= "PFW0213";
	
	/**
	 * 選択したコードが無効なため登録ができない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_SELECTED_CODE_NOT_EXIST						= "PFW0214";
	/**
	 * 選択したコードが既に存在しているため登録ができない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_SELECTED_CODE_EXIST							= "PFW0215";
	/**
	 * 退職情報が存在しているため入社情報が削除できない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_CANT_DELETE_ENTRANCE						= "PFW0216";
	/**
	 * 順序が異なる(日付等)ため処理できない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_INVALID_ORDER								= "PFW0217";
	/**
	 * 期間が重複するため処理できない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_TERM_DUPLICATE								= "PFW0218";
	/**
	 * 社員の履歴が存在しない場合の警告メッセージ。<br>
	 */
	public static final String	MSG_EMP_HISTORY_NOT_EXIST						= "PFW0219";
	/**
	 * プルダウン存在チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_WORKFORM_EXISTENCE							= "PFW0220";
	
	/**
	 * 選択したコードが重複するため処理できない場合の警告メッセージ。<br>
	 * ルート詳細画面のJavaScriptで、用いられる。<br>
	 */
	public static final String	MSG_SELECTED_CODE_DUPLICATE						= "PFW0221";
	
	/**
	 * 該当ユニットコードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_UNIT_CODE_IS_USED							= "PFW0222";
	/**
	 * 該当ルートコードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_ROUTE_CODE_IS_USED							= "PFW0223";
	/**
	 * 個人設定が重複する際のエラーメッセージ。<br>
	 */
	public static final String	MSG_APPLICATION_PERSON_DUPLICATE				= "PFW0224";
	/**
	 * マスタ組合せによる適用範囲が重複する際のエラーメッセージ。<br>
	 */
	public static final String	MSG_APPLICATION_MASTER_DUPLICATE				= "PFW0225";
	/**
	 * 権限が無いため対象社員の操作ができない場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_HAS_AUTHORITY							= "PFW0226";
	/**
	 * 存在しないため変更する必要がある場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_EXIST_AND_CHANGE_SOMETHING				= "PFW0227";
	/**
	 * ユニットに承認者が存在しない場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_UNIT_NOT_HAVE_APPROVER						= "PFW0228";
	/**
	 * 決定した日付時点で入社日を過ぎていない社員の社員コードが入力されている場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_JOINED_EMPLOYEES						= "PFW0230";
	/**
	 * 決定した日付時点で退職日を過ぎている社員の社員はコードが入力されている場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_RETIREMENT_EMPLOYEES					= "PFW0231";
	
	/**
	 * 検索条件が指定されていない場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_SEARCH_CONDITION							= "PFW0234";
	/**
	 * 操作中にログアウトしてしまった場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_LOGOUT_NOT_EXIST_USER						= "PFW0235";
	
	/**
	 * 認証不正時のメッセージコード。<br>
	 */
	public static final String	MSG_AUTH_FAILED									= "PFW9111";
	/**
	 * 個人基本情報削除時のメッセージコード。<br>
	 */
	public static final String	MSG_PERSONAL_DELETE								= "PFW9118";
	
	/**
	 * 処理成功時メッセージコード。<br>
	 */
	public static final String	MSG_PROCESS_SUCCEED								= "PFI0001";
	/**
	 * 処理失敗時メッセージコード。<br>
	 */
	public static final String	MSG_PROCESS_FAILED								= "PFI0002";
	/**
	 * 履歴削除時メッセージコード。<br>
	 */
	public static final String	MSG_DELETE_HISTORY								= "PFI0003";
	/**
	 * インポート時メッセージコード。<br>
	 */
	public static final String	MSG_IMPORT_SUCCEED								= "PFI0004";
	/**
	 * 処理失敗時メッセージコード。<br>
	 */
	public static final String	MSG_PROCESS_FAILED_2							= "PFI0005";
	/**
	 * 処理失敗時メッセージコード。<br>
	 */
	public static final String	MSG_PROCESS_FAILED_3							= "PFI0006";
	/**
	 * データ情報が存在しない際のメッセージコード。<br>
	 */
	public static final String	MSG_NO_DATA										= "PFI0102";
	/**
	 * データ更新確認時のメッセージコード。<br>
	 */
	public static final String	MSG_UPDATE_CONFIRMATION							= "PFQ1001";
	/**
	 * 削除確認時のメッセージコード。<br>
	 */
	public static final String	MSG_DELETE_CONFIRMATION							= "PFQ1002";
	/**
	 * 編集内容破棄確認時のメッセージコード。<br>
	 */
	public static final String	MSG_EDIT_ANNULMENT								= "PFQ1003";
	
	/**
	 * 人事汎用管理：インポートに有効日を選択しない際のメッセージコード
	 */
	public static final String	MSG_HUMAN_GENERAL_NOT_ACTIVATE_DATE				= "HG0001";
	
	/**
	 * 人事汎用情報をインポート際、有効日を対象社員有効日前にした場合のエラーメッセージコード
	 */
	public static final String	MSG_HUMAN_GENERAL_IMPORT_BEFORE_ACTIVATE_DATE	= "HG0002";
	
	/**
	 * 人事汎用情報をインポート際、入力値が正しくない場合のエラーメッセージコード
	 */
	public static final String	MSG_HUMAN_GENERAL_IMPORT_NOT_INPUT				= "HG0003";
	
	/**
	 * 人事汎用管理：バイナリ情報の画像用拡張子メッセージコード。<br>
	 */
	public static final String	MSG_HUMAN_GENERAL_PICTURE_EXTENSION				= "HGI0001";
	
}
