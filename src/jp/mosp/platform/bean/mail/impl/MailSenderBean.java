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

import java.io.File;
import java.sql.Connection;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.mail.MailSenderBeanInterface;
import jp.mosp.platform.constant.PlatformMailConst;

/**
 * メール送信クラス。
 */
public class MailSenderBean extends PlatformBean implements MailSenderBeanInterface {
	
	private String		mailServer	= "";
	private String		transmitter	= "";
	private String		userName	= "";
	
	private String[][]	receiver	= new String[0][0];
	private Address[]	addresses	= new Address[0];
	private String		title		= "";
	private String		text		= "";
	
	private String		userid		= "";
	private String		password	= "";
	
	private boolean		useMailAuth	= false;
	private boolean		useSSL		= false;
	private boolean		useDebug	= false;
	
	private Session		session;
	private MimeMessage	mimeMessage;
	private Transport	transport;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MailSenderBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public MailSenderBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() {
	}
	
	@Override
	public void init() {
		setMailServer(mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_HOST));
		setUserid(mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_USER_NAME));
		setPassword(mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PASSWORD));
		setUseMailAuth(mospParams.getApplicationPropertyBool(PlatformMailConst.APP_MAIL_SMTP_AUTH));
		setUseSSL(mospParams.getApplicationPropertyBool(PlatformMailConst.APP_MAIL_SSL));
		setTransmitter(mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_ADDRESS));
		setUserName(mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PERSONAL));
		setSession();
		setMimeMessage();
	}
	
	@Override
	public void setMessage(String[][] receiver, String title, String text, File file) throws MospException {
		setReceiver(receiver);
		setAddresses();
		setTitle(title);
		StringBuffer sb = new StringBuffer();
		sb.append(text);
		sb.append("\n");
		setText(sb.toString());
		setMessageSetting();
	}
	
	/**
	 * メール送信
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void send() throws MospException {
		createConnection();
		try {
			transport.sendMessage(mimeMessage, addresses);
		} catch (Throwable e) {
			throw new MospException(e);
		}
		closeConnection();
	}
	
	@Override
	public boolean isSend() {
		boolean isSend = true;
		try {
			send();
		} catch (Throwable e) {
			isSend = false;
			e.printStackTrace();
		}
		return isSend;
	}
	
	/**
	 * セッション取得
	 */
	protected void setSession() {
		Properties props = new Properties();
		// SMTPサーバーのアドレスを指定
		props.put(PlatformMailConst.SMTP_HOST, mailServer);
		props.put(PlatformMailConst.MAIL_HOST, mailServer);
		props.put(PlatformMailConst.MAIL_PROTOCOL, PlatformMailConst.SMTP);
		props.put(PlatformMailConst.SMTP_AUTH, String.valueOf(useMailAuth));
		props.put(PlatformMailConst.SMTP_PORT, changeSMTPPort());
		props.put(PlatformMailConst.CONNETCTION_TIME_OUT, PlatformMailConst.TIME_OUT);
		props.put(PlatformMailConst.SMTP_TIME_OUT, PlatformMailConst.TIME_OUT);
		if (useSSL) {
			props.setProperty(PlatformMailConst.SF_CLASS, SSLSocketFactory.class.getCanonicalName());
			props.setProperty(PlatformMailConst.SF_FALLBACK, String.valueOf(false));
			props.setProperty(PlatformMailConst.SF_PORT, PlatformMailConst.SSL_PORT_465);
		}
		session = Session.getDefaultInstance(props, new MailAuth());
	}
	
	/**
	 * mimeMessageオブジェクト生成
	 */
	protected void setMimeMessage() {
		if (checkSession()) {
			session.setDebug(useDebug);
			mimeMessage = new MimeMessage(session);
		}
	}
	
	/**
	 * メール情報の設定
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setMessageSetting() throws MospException {
		if (mimeMessage == null) {
			return;
		}
		try {
			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress(transmitter, userName, PlatformMailConst.ISO_2022_JP));
			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
			// メールのタイトルを指定
			// mimeMessage.setSubject(title, MailConst.ISO_2022_JP);
			mimeMessage.setSubject(title, PlatformMailConst.UTF_8);
			// メールの内容を指定
			// mimeMessage.setText(text, MailConst.ISO_2022_JP);
			mimeMessage.setText(text, PlatformMailConst.UTF_8);
			// 送信日付を指定
			mimeMessage.setSentDate(getSystemTime());
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * SMTPポート変更
	 * @return ポート番号
	 */
	protected String changeSMTPPort() {
		String port = PlatformMailConst.PORT_25;
		if (useSSL) {
			port = PlatformMailConst.SSL_PORT_465;
		}
		return port;
	}
	
	@Override
	public boolean checkConnection() {
		boolean canUseServrer = false;
		if (checkSession()) {
			canUseServrer = createConnection();
			closeConnection();
		}
		return canUseServrer;
	}
	
	/**
	 * sessionからTransportオブジェクトを生成。
	 */
	protected void setTransport() {
		if (checkSession()) {
			try {
				transport = session.getTransport(PlatformMailConst.SMTP);
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * メールサーバーとのConnectionを生成
	 * @return 接続できればtrue、そうでない場合false。
	 */
	protected boolean createConnection() {
		setTransport();
		try {
			if (checkTransport()) {
				if (useMailAuth) {
					transport.connect(mailServer, userid, password);
					return true;
				} else {
					transport.connect();
					return true;
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * メールサーバーとのConnectionを解除
	 */
	protected void closeConnection() {
		try {
			if (checkTransport()) {
				transport.close();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sessionのチェック
	 * @return sessionがnullの場合false、そうでない場合true
	 */
	protected boolean checkSession() {
		return session != null;
	}
	
	/**
	 * Transportオブジェクトのチェック
	 * @return transportがnullの場合false、そうでない場合true
	 */
	private boolean checkTransport() {
		return transport != null;
	}
	
	/**
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAddresses() throws MospException {
		Address[] addresses = new Address[receiver.length];
		for (int i = 0; i < addresses.length; i++) {
			try {
				addresses[i] = new InternetAddress(receiver[i][1], receiver[i][0], PlatformMailConst.ISO_2022_JP);
			} catch (Throwable e) {
				throw new MospException(e);
			}
		}
		setAddresses(addresses);
	}
	
	/**
	 * @param mailServer セットする mailServer
	 */
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
	
	/**
	 * @param transmitter セットする transmitter
	 */
	public void setTransmitter(String transmitter) {
		this.transmitter = transmitter;
	}
	
	/**
	 * @param userName セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @param receiver セットする receiver
	 */
	public void setReceiver(String[][] receiver) {
		this.receiver = getStringArrayClone(receiver);
	}
	
	/**
	 * @param addresses セットする addresses
	 */
	public void setAddresses(Address[] addresses) {
		this.addresses = getAddressArrayClone(addresses);
	}
	
	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @param text セットする text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @param userid セットする userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @param useMailAuth セットする useMailAuth
	 */
	public void setUseMailAuth(boolean useMailAuth) {
		this.useMailAuth = useMailAuth;
	}
	
	/**
	 * @param useSSL セットする useSSL
	 */
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	@Override
	public void setUseDebug(boolean useDebug) {
		this.useDebug = useDebug;
	}
	
	/**
	 * アドレス配列の複製を取得する。
	 * @param addresses 対象アドレス配列
	 * @return 複製アドレス配列
	 */
	protected Address[] getAddressArrayClone(Address[] addresses) {
		if (addresses == null) {
			return new Address[0];
		}
		return addresses.clone();
	}
	
	
	/**
	 * SMTP認証用
	 */
	private class MailAuth extends Authenticator {
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userid, password);
		}
		
	}
	
}
