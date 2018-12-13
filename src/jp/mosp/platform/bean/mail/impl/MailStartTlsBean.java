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
package jp.mosp.platform.bean.mail.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.mail.MailStartTlsBeanInterface;
import jp.mosp.platform.constant.PlatformMailConst;
import jp.mosp.platform.dto.mail.impl.MailTemplateDto;
import jp.mosp.platform.utils.MailTemplateUtility;

/**
 * メール送信(STARTTLS)処理。<br>
 */
public class MailStartTlsBean extends PlatformBean implements MailStartTlsBeanInterface {
	
	/**
	 * SMTP設定(認証)。<br>
	 */
	protected static final String	SMTP_AUTH					= "true";
	
	/**
	 * SMTP設定(ポート番号)。<br>
	 */
	protected static final String	SMTP_PORT					= "587";
	
	/**
	 * SMTP設定(STARTTLS設定)。<br>
	 */
	protected static final String	SMTP_ATT_STARTTLS_ENABLE	= "mail.smtp.starttls.enable";
	
	/**
	 * SMTP設定(STARTTLS)。<br>
	 */
	protected static final String	SMTP_STARTTLS_ENABLE		= "true";
	
	/**
	 * SMTP設定(STARTTLS設定)。<br>
	 */
	protected static final String	SMTP_ATT_STARTTLS_REQUIRED	= "mail.smtp.starttls.required";
	
	/**
	 * SMTP設定(STARTTLS)。<br>
	 */
	protected static final String	SMTP_STARTTLS_REQUIRED		= "true";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：メール送信)。<br>
	 */
	protected static final String	APP_LOG_LEVEL_MAIL			= "LogLevelMail";
	
	/**
	 * 区切文字(メールアドレス)。<br>
	 */
	protected static final String	SEPARATOR_ADDRESS			= "@";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MailStartTlsBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public MailStartTlsBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void send(String recipient, String subject, String appTemplate, MailTemplateDto dto) {
		// 受信者メールアドレスリストを準備
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipient);
		// メールを送信(STARTTLS)
		send(recipients, subject, appTemplate, dto);
	}
	
	@Override
	public void send(List<String> recipients, String subject, String appTemplate, MailTemplateDto dto) {
		// プロパティ群(SMTP用)を作成
		Properties props = makeProperties();
		// セッションを取得
		Session session = Session.getInstance(props);
		// メッセージを準備
		MimeMessage mimeMessage = new MimeMessage(session);
		// トランスポートを準備
		Transport transport = null;
		try {
			// 送信元メールアドレスを設定
			mimeMessage.setFrom(makeAddress(getTransmitter(), getTransmitterName()));
			// 送信先メールアドレスを設定
			mimeMessage.setRecipients(Message.RecipientType.TO, makeAddresses(recipients));
			// メールのタイトルを設定
			mimeMessage.setSubject(subject, PlatformMailConst.UTF_8);
			// メールの内容を指定
			mimeMessage.setText(makeText(appTemplate, dto), PlatformMailConst.UTF_8);
			// 送信日付を指定
			mimeMessage.setSentDate(getSystemTime());
			// トランスポートを取得
			transport = session.getTransport(PlatformMailConst.SMTP);
			// メールサーバと接続
			transport.connect(getSmtpHost(), getSmtpUser(), getSmtpPass());
			// メール送信
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			// ログ出力
			outputSendLog(recipients);
		} catch (Throwable t) {
			// エラーログを出力
			LogUtility.error(mospParams, t);
			// エラーメッセージを設定
			addErrorSendMail(recipients);
		} finally {
			try {
				// トランスポートが存在する場合
				if (transport != null) {
					// トランスポートをクローズ
					transport.close();
				}
			} catch (Throwable t) {
				// エラーログを出力
				LogUtility.error(mospParams, t);
			}
		}
		
	}
	
	/**
	 * プロパティ群(SMTP用)を作成する。<br>
	 * @return プロパティ群(SMTP用)
	 */
	protected Properties makeProperties() {
		// プロパティを準備
		Properties props = new Properties();
		// 値を設定
		props.put(PlatformMailConst.SMTP_PORT, SMTP_PORT);
		props.put(PlatformMailConst.SMTP_AUTH, SMTP_AUTH);
		props.put(PlatformMailConst.CONNETCTION_TIME_OUT, PlatformMailConst.TIME_OUT);
		props.put(PlatformMailConst.SMTP_TIME_OUT, PlatformMailConst.TIME_OUT);
		props.put(SMTP_ATT_STARTTLS_ENABLE, SMTP_STARTTLS_ENABLE);
		props.put(SMTP_ATT_STARTTLS_REQUIRED, SMTP_STARTTLS_REQUIRED);
		// 送信者メールアドレスを@で分割
		String[] address = MospUtility.split(getTransmitter(), SEPARATOR_ADDRESS);
		// 分割できた場合
		if (address.length > 1) {
			// メールユーザとホストを設定
			props.put(PlatformMailConst.MAIL_USER, address[0]);
			props.put(PlatformMailConst.MAIL_HOST, address[1]);
		}
		// プロパティを取得
		return props;
	}
	
	/**
	 * アドレス配列を作成する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 * @return アドレス配列
	 * @throws MospException アドレスの作成に失敗した場合
	 */
	protected Address[] makeAddresses(List<String> recipients) throws MospException {
		// アドレスリストを準備
		List<Address> list = new ArrayList<Address>();
		// 受信者メールアドレス毎に処理
		for (String recipient : recipients) {
			// アドレスリストに追加
			list.add(makeAddress(recipient));
		}
		// アドレス配列を作成
		return list.toArray(new Address[list.size()]);
	}
	
	/**
	 * アドレスを作成する。<br>
	 * @param address  メールアドレス
	 * @return アドレス
	 * @throws MospException アドレスの作成に失敗した場合
	 */
	protected Address makeAddress(String address) throws MospException {
		try {
			// アドレスを作成
			return new InternetAddress(address);
		} catch (AddressException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * アドレスを作成する。<br>
	 * @param address  メールアドレス
	 * @param personal 名前
	 * @return アドレス
	 * @throws MospException アドレスの作成に失敗した場合
	 */
	protected Address makeAddress(String address, String personal) throws MospException {
		try {
			// アドレスを作成
			return new InternetAddress(address, personal);
		} catch (Exception e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * テンプレートからメール本文を作成する。<br>
	 * @param appTemplate MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @param dto         メールテンプレート情報
	 * @return メール本文
	 * @throws MospException メール本文の作成に失敗した場合
	 */
	protected String makeText(String appTemplate, MailTemplateDto dto) throws MospException {
		MailTemplateUtility mailTemplate = new MailTemplateUtility();
		mailTemplate.init(mospParams, "", mospParams.getApplicationProperty(appTemplate));
		mailTemplate.setMailItem(dto);
		return mailTemplate.getText();
	}
	
	/**
	 * SMTPホストを取得する。<br>
	 * @return SMTPホスト
	 */
	protected String getSmtpHost() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_HOST);
	}
	
	/**
	 * SMTPユーザを取得する。<br>
	 * @return SMTPユーザ
	 */
	protected String getSmtpUser() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_USER_NAME);
	}
	
	/**
	 * SMTPユーザパスワードを取得する。<br>
	 * @return SMTPユーザパスワード
	 */
	protected String getSmtpPass() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PASSWORD);
	}
	
	/**
	 * SMTP送信者メールアドレスを取得する。<br>
	 * @return SMTP送信者メールアドレス
	 */
	protected String getTransmitter() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_ADDRESS);
	}
	
	/**
	 * SMTP送信者名を取得する。<br>
	 * @return SMTP送信者名
	 */
	protected String getTransmitterName() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PERSONAL);
	}
	
	/**
	 * メール送信ログを出力する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 */
	protected void outputSendLog(List<String> recipients) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_MAIL, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログを出力
		LogUtility.log(mospParams, level, makeMessage(recipients, PlatformMailConst.MSG_MAIL_SEND_SUCCESS));
	}
	
	/**
	 * エラーメッセージを設定する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 */
	protected void addErrorSendMail(List<String> recipients) {
		// エラーメッセージを設定
		mospParams.getErrorMessageList().add(makeMessage(recipients, PlatformMailConst.MSG_MAIL_SEND_FAILED));
	}
	
	/**
	 * メール送信メッセージを作成する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 * @param message    メッセージ
	 * @return メール送信メッセージ
	 */
	protected String makeMessage(List<String> recipients, String message) {
		// メッセージを作成
		StringBuilder sb = new StringBuilder();
		sb.append(MospUtility.toSeparatedString(recipients, MospConst.APP_PROPERTY_SEPARATOR));
		sb.append(message);
		// メッセージを取得
		return sb.toString();
	}
	
}
