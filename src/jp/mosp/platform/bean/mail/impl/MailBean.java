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
import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.mail.MailBeanInterface;
import jp.mosp.platform.bean.mail.MailSenderBeanInterface;
import jp.mosp.platform.constant.PlatformMailConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.dto.mail.impl.MailTemplateDto;
import jp.mosp.platform.utils.MailTemplateUtility;

/**
 * メールクラス。
 */
public class MailBean extends PlatformBean implements MailBeanInterface {
	
	/**
	 * メール送信クラス。
	 */
	protected MailSenderBeanInterface			mailSender;
	
	/**
	 * 人事マスタ参照クラス。
	 */
	protected HumanReferenceBeanInterface		humanReference;
	
	/**
	 * 人事汎用通常情報参照クラス。
	 */
	protected HumanNormalReferenceBeanInterface	humanNormalReference;
	
	/**
	 * テンプレートパス。
	 */
	public static final String					PATH_TEMPLATE	= "/template/";
	
	/**
	 * 人事汎用管理項目。
	 */
	protected String							itemName		= "mailAddress";
	
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 */
	public MailBean() {
		super();
	}
	
	/**
	 * {@link BaseBean#BaseBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public MailBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		mailSender = (MailSenderBeanInterface)createBean(MailSenderBeanInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		humanNormalReference = (HumanNormalReferenceBeanInterface)createBean(HumanNormalReferenceBeanInterface.class);
	}
	
	@Override
	public boolean canUseMailServer() {
		boolean useMail = false;
		// 設定ファイルのプロパティから取得
		if (mospParams.getApplicationProperty(PlatformMailConst.APP_USE_MAIL) == null) {
			mailSender.init();
			// メールサーバーと接続するか確認
			useMail = mailSender.checkConnection();
		} else {
			useMail = mospParams.getApplicationPropertyBool(PlatformMailConst.APP_USE_MAIL);
		}
		return useMail;
	}
	
	@Override
	public boolean sendMail(String personalId, String subject, String templateFileName, MailTemplateDto dto, File file)
			throws MospException {
		String address = getMailAddress(personalId);
		return sendMail(personalId, address, getPersonal(personalId, address), subject,
				getText(PATH_TEMPLATE, templateFileName, dto), file);
	}
	
	/**
	 * メール送信
	 * @param personalId 個人ID
	 * @param personal 個人名
	 * @param subject 件名
	 * @param path テンプレートパス
	 * @param fileName テンプレートファイル名
	 * @param dto テンプレートDTO
	 * @param file 添付ファイル
	 * @return 送信成功の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean sendMail(String personalId, String personal, String subject, String path, String fileName,
			MailTemplateDto dto, File file) throws MospException {
		return sendMail(personalId, getMailAddress(personalId), personal, subject, getText(path, fileName, dto), file);
	}
	
	@Override
	public boolean sendMail(String[][] aryPersonalId, String title, String text, File file) throws MospException {
		List<String[]> list = new ArrayList<String[]>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < aryPersonalId.length; i++) {
			if (!checkMailAddress(aryPersonalId[i][1])) {
				continue;
			}
			sb.append(aryPersonalId[i][0]);
			if (i != aryPersonalId.length - 1) {
				sb.append(PlatformMailConst.STR_UNDER_SEPARATOR);
			}
			String[] receiver = new String[2];
			receiver[0] = aryPersonalId[i][0];
			HumanDtoInterface dto = humanReference.getHumanInfo(aryPersonalId[i][0], getSystemDate());
			if (dto != null) {
				receiver[0] = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
			}
			receiver[1] = aryPersonalId[i][1];
			list.add(receiver);
		}
		// 初期化
		mailSender.init();
		// メッセージと添付ファイル
		mailSender.setMessage(toArray(list), title, text, file);
		boolean isSend = mailSender.isSend();
		// 送信
		if (isSend) {
			sb.append(PlatformMailConst.MSG_MAIL_SEND_SUCCESS);
		} else {
			sb.append(PlatformMailConst.MSG_MAIL_SEND_FAILED);
		}
		// ログ出力
		LogUtility.log(mospParams, 800, sb.toString());
		return isSend;
	}
	
	/**
	 * メール送信
	 * @param personalId 個人ID
	 * @param subject 件名
	 * @param text 本文
	 * @param file 添付ファイル
	 * @return 送信成功の場合true、そうでない場合false。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean sendMail(String personalId, String subject, String text, File file) throws MospException {
		String address = getMailAddress(personalId);
		return sendMail(personalId, address, getPersonal(personalId, address), subject, text, file);
	}
	
	/**
	 * メール送信
	 * @param personalId 個人ID
	 * @param address アドレス
	 * @param personal 個人名
	 * @param subject 件名
	 * @param text 本文
	 * @param file 添付ファイル
	 * @return 送信成功の場合true、そうでない場合false。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean sendMail(String personalId, String address, String personal, String subject, String text,
			File file) throws MospException {
		if (!checkMailAddress(address)) {
			return false;
		}
		// 初期化
		mailSender.init();
		// メッセージと添付ファイル
		mailSender.setMessage(
				new String[][]{ { personal == null || personal.isEmpty() ? address : personal, address } }, subject,
				text, file);
		// 送信
		boolean isSend = mailSender.isSend();
		StringBuffer sb = new StringBuffer();
		sb.append(personalId);
		if (isSend) {
			sb.append(PlatformMailConst.MSG_MAIL_SEND_SUCCESS);
		} else {
			sb.append(PlatformMailConst.MSG_MAIL_SEND_FAILED);
		}
		// ログ出力
		LogUtility.log(mospParams, 800, sb.toString());
		return isSend;
	}
	
	/**
	 * メールアドレスを取得する。
	 * @param personalId 個人ID
	 * @return メールアドレス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getMailAddress(String personalId) throws MospException {
		HumanNormalDtoInterface dto = humanNormalReference.getHumanNormalInfo(itemName, personalId);
		if (dto == null) {
			return "";
		}
		return dto.getHumanItemValue();
	}
	
	/**
	 * 個人名を取得する。
	 * @param personalId 個人ID
	 * @param address 電子メールアドレス
	 * @return 個人名
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getPersonal(String personalId, String address) throws MospException {
		String humansName = getHumansName(personalId);
		if (humansName == null || humansName.isEmpty()) {
			return address;
		}
		return humansName;
	}
	
	/**
	 * 人名を取得する。
	 * @param personalId 個人ID
	 * @return 人名
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getHumansName(String personalId) throws MospException {
		HumanDtoInterface dto = humanReference.getHumanInfo(personalId, getSystemDate());
		if (dto == null) {
			return "";
		}
		return MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
	}
	
	/**
	 * 本文を取得する。
	 * @param path テンプレートパス
	 * @param fileName テンプレートファイル名
	 * @param dto テンプレートDTO
	 * @return 本文
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getText(String path, String fileName, MailTemplateDto dto) throws MospException {
		MailTemplateUtility mailTemplate = new MailTemplateUtility();
		mailTemplate.init(mospParams, path, fileName);
		mailTemplate.setMailItem(dto);
		return mailTemplate.getText();
	}
	
	/**
	 * メールアドレス確認。
	 * @param mailAddress メールアドレス
	 * @return 確認結果(true：有効、false：無効)
	 */
	protected boolean checkMailAddress(String mailAddress) {
		return mailAddress != null && !mailAddress.isEmpty();
	}
	
	/**
	 * リストの配列を返す。
	 * @param list 対象リスト
	 * @param a 配列
	 * @return リストの配列
	 */
	protected <T> T[] toArray(List<T> list, T... a) {
		return list.toArray(a);
	}
	
}
