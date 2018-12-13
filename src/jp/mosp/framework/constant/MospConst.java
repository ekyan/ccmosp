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
package jp.mosp.framework.constant;

/**
 * MosPフレームワークで用いる定数を宣言する。<br><br>
 */
public class MospConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospConst() {
		// 処理無し
	}
	
	
	/**
	 * 無効フラグ(OFF)。<br>
	 */
	public static final int		INACTIVATE_FLAG_OFF		= 0;
	
	/**
	 * 無効フラグ(ON)。<br>
	 */
	public static final int		INACTIVATE_FLAG_ON		= 1;
	
	/**
	 * 削除フラグ(OFF)。<br>
	 */
	public static final int		DELETE_FLAG_OFF			= 0;
	
	/**
	 * 削除フラグ(ON)。<br>
	 */
	public static final int		DELETE_FLAG_ON			= 1;
	
	/**
	 * 表示フラグ(表示)。<br>
	 */
	public static final int		VIEW_ON					= 0;
	
	/**
	 * 表示フラグ(非表示)。
	 */
	public static final int		VIEW_OFF				= 1;
	
	/**
	 * 汎用フラグ(OFF)。<br>
	 */
	public static final int		FLAG_OFF				= 0;
	
	/**
	 * 汎用フラグ(ON)。<br>
	 */
	public static final int		FLAG_ON					= 1;
	
	/**
	 * チェックボックス値(ON)。<br>
	 * value属性に設定しておくと、チェックされていた場合にこの値が送信される。<br>
	 */
	public static final String	CHECKBOX_ON				= "1";
	
	/**
	 * チェックボックス値(OFF)。<br>
	 * 値が送信されなかった場合に、この値が付加される。<br>
	 */
	public static final String	CHECKBOX_OFF			= "0";
	
	// パラメーター名(request.getParameter)
	/**
	 * パラメータID(コマンド)。<br>
	 * 処理Actionを判断させる。<br>
	 */
	public static final String	PRM_CMD					= "cmd";
	
	/**
	 * パラメータID(ページ選択インデックス)。<br>
	 */
	public static final String	PRM_SELECT_INDEX		= "selectIndex";
	
	/**
	 *  MosP属性名(MosPパラメータ)。<br>
	 */
	public static final String	ATT_MOSP_PARAMS			= "mospParams";
	
	/**
	 *  MosP属性名(マルチパートデータのリスト)。<br>
	 */
	public static final String	ATT_MULTIPART_LIST		= "multipartList";
	
	// 属性名(request.getHeader)
	/**
	 *  MosP属性名(USER-AGENT)。<br>
	 */
	public static final String	ATT_USER_AGENT			= "USER-AGENT";
	
	/**
	 *  MosP属性名(REMOTE-ADDR)。<br>
	 */
	public static final String	ATT_REMOTE_ADDR			= "REMOTE-ADDR";
	
	// プロパティ名(public)
	/**
	 *  MosPアプリケーション設定キー(アプリケーションルートの絶対パス)。<br>
	 */
	public static final String	APP_DOCBASE				= "Docbase";
	
	/**
	 * MosPアプリケーション設定キー(文字コード)
	 */
	public static final String	APP_CHARACTER_ENCODING	= "CharacterEncoding";
	
	/**
	 * MosPアプリケーション設定キー(追加メタ情報)
	 */
	public static final String	APP_EXTRA_HTML_META		= "ExtraHtmlMeta";
	
	/**
	 * MosPアプリケーション設定セパレータ。
	 */
	public static final String	APP_PROPERTY_SEPARATOR	= ",";
	
	/**
	 *  MosPアプリケーション設定キー(アプリケーションのタイトル)。<br>
	 */
	public static final String	APP_TITLE				= "Title";
	
	/**
	 *  MosPアプリケーション設定キー(アプリケーションのバージョン)。<br>
	 */
	public static final String	APP_VERSION				= "Version";
	
	/**
	 * 操作区分(参照)。
	 */
	public static final String	OPERATION_TYPE_REFER	= "1";
	
	/**
	 * 範囲設定の範囲(自身)。<br>
	 */
	public static final String	RANGE_MYSELF			= "RangeMyself";
	
	// URL
	/**
	 *  サーブレットのパス。<br>
	 */
	public static final String	URL_SRV					= "/srv/";
	
	/**
	 *  公開ディレクトリのパス。<br>
	 */
	public static final String	URL_PUB					= "../pub/";
	
	// 入力制限
	/**
	 * 入力制限用定数(電話番号)。<br>
	 */
	public static final String	REG_PHONE				= "[0-9-]*";
	
	/**
	 * 入力制限用定数(数値1)。<br>
	 */
	public static final String	REG_DECIMAL_PRE			= "^(([1-9]\\d{0,";
	
	/**
	 * 入力制限用定数(数値2)。<br>
	 */
	public static final String	REG_DECIMAL_MID			= "})|0)(\\.\\d{1,";
	
	/**
	 * 入力制限用定数(数値3)。<br>
	 */
	public static final String	REG_DECIMAL_AFT			= "})?$";
	
	// その他
	/**
	 * デフォルト年。<br>
	 * Date型で月日や時間だけを扱いたい場合に用いる。<br>
	 */
	public static final int		DEFAULT_YEAR			= 1970;
	
	/**
	 * ラインセパレート用の定数。<br>
	 */
	public static final String	LINE_SEPARATOR			= System.getProperty("line.separator");
	
	/**
	 * エラーメッセージ用括弧。<br>
	 * エラーメッセージの後ろに「(エラーメッセージコード)」が付く。<br>
	 */
	public static final String	MESSAGE_L_PARENTHSIS	= "(";
	
	/**
	 * エラーメッセージ用括弧。<br>
	 * エラーメッセージの後ろに「(エラーメッセージコード)」が付く。<br>
	 */
	public static final String	MESSAGE_R_PARENTHSIS	= ")";
	
	/**
	 * 処理バイト数。<br>
	 */
	public static final int		PROCESS_BYTES			= 1024;
	
}
