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
 * MosPシステムで用いるメッセージキーの定数を宣言する。<br><br>
 */
public class MessageConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MessageConst() {
		// 処理無し
	}
	
	
	/**
	 * ログアウト時のメッセージコード。<br>
	 */
	public static final String	MSG_LOGOUT					= "FWI0001";
	
	/**
	 * セッションタイムアウト時のメッセージコード。<br>
	 */
	public static final String	MSG_SESSION_TIMEOUT			= "FWW0001";
	
	/**
	 * インスタンスを生成失敗時（クラス名未入力）のメッセージコード。<br>
	 */
	public static final String	MSG_INSTANCE_NO_INPUT		= "FWE9101";
	
	/**
	 * インスタンスを生成失敗時（クラス名なし）のメッセージコード。<br>
	 */
	public static final String	MSG_INSTANCE_NO_CLASSNAME	= "FWE9102";
	
	/**
	 * インスタンスを生成失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_INSTANCE_ERROR			= "FWE9103";
	
	/**
	 * コマンド未実装時のメッセージコード。<br>
	 */
	public static final String	MSG_UNKNOWN_COMMAND			= "FWE9111";
	
	/**
	 * RDBMS依存依存エラー発生時のメッセージコード。<br>
	 */
	public static final String	MSG_RDBMS					= "FWE9211";
	
	/**
	 * DBへの挿入処理失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_DB_INSERT				= "FWE9221";
	
	/**
	 * DBへの更新処理失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_DB_UPDATE				= "FWE9222";
	
	/**
	 * DBへの削除処理失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_DB_DELETE				= "FWE9223";
	
	/**
	 * 実行時例外発生時のメッセージコード。<br>
	 */
	public static final String	MSG_EXC_GEN					= "FWE9999";
	
}
