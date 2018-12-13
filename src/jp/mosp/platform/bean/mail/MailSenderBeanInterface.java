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
package jp.mosp.platform.bean.mail;

import java.io.File;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.mail.impl.MailSenderBean;

/**
 * メール送信インターフェース。
 */
public interface MailSenderBeanInterface {
	
	/**
	 * 初期化。
	 */
	void init();
	
	/**
	 * メッセージ設定。
	 * @param receiver 受信者配列
	 * @param title    タイトル
	 * @param text     本文
	 * @param file     添付ファイル
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setMessage(String[][] receiver, String title, String text, File file) throws MospException;
	
	/**
	 * メール送信。
	 * @return 送信できた場合true、そうでない場合false
	 */
	boolean isSend();
	
	/**
	 * 接続テスト。
	 * @return メールサーバの利用可否
	 */
	boolean checkConnection();
	
	/**
	 * デバッグモードの利用設定。
	 * @param useDebug デバッグモードの有効無効
	 * <p>
	 * デバッグモードを利用する場合、{@link MailSenderBean#init()}の前に設定してください。
	 * </p>
	 */
	void setUseDebug(boolean useDebug);
	
}
