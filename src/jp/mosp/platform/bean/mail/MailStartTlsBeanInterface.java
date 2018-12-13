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

import java.util.List;

import jp.mosp.platform.dto.mail.impl.MailTemplateDto;

/**
 * メール送信(STARTTLS)処理インターフェース。<br>
 */
public interface MailStartTlsBeanInterface {
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * @param recipients  受信者メールアドレスリスト
	 * @param subject     メール題目
	 * @param appTemplate MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @param dto         メールテンプレート情報
	 */
	void send(List<String> recipients, String subject, String appTemplate, MailTemplateDto dto);
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * @param recipient   受信者メールアドレス
	 * @param subject     メール題目
	 * @param appTemplate MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @param dto         メールテンプレート情報
	 */
	void send(String recipient, String subject, String appTemplate, MailTemplateDto dto);
	
}
