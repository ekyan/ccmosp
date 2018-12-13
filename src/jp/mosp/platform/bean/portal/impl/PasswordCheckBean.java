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
package jp.mosp.platform.bean.portal.impl;

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * パスワードの確認を行う。<br>
 * <br>
 * 認証時、パスワード変更時等で、用いる。<br>
 */
public class PasswordCheckBean extends PlatformBean implements PasswordCheckBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(パスワード有効期間(日))。
	 */
	protected static final String		APP_PASSWORD_PERIOD		= "PasswordPeriod";
	
	/**
	 * MosPアプリケーション設定キー(パスワード確認)。
	 */
	protected static final String		APP_CHECK_PASSWORD		= "CheckPassword";
	
	/**
	 * MosPアプリケーション設定キー(初期パスワード)。
	 */
	protected static final String		APP_INITIAL_PASSWORD	= "InitialPassword";
	
	/**
	 * 初期パスワード設定(ユーザID)。
	 */
	protected static final String		INITIAL_PASS_USER_ID	= "UserId";
	
	/**
	 * パスワード確認設定(初期パスワード不可)
	 */
	protected static final String		PASS_CHECK_INIT_INVALID	= "initPasswordInvalid";
	
	/**
	 * ユーザパスワード情報DAO。<br>
	 */
	private UserPasswordDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PasswordCheckBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PasswordCheckBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (UserPasswordDaoInterface)createDao(UserPasswordDaoInterface.class);
	}
	
	@Override
	public void checkPasswordPeriod(String userId, Date targetDate) throws MospException {
		// 期限日の取得
		int passwordPeriod = mospParams.getApplicationProperty(APP_PASSWORD_PERIOD, Integer.MAX_VALUE);
		// ユーザパスワード情報の取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// 有効期限取得
		Date periodDate = addDay(dto.getChangeDate(), passwordPeriod);
		// 有効期限確認
		if (targetDate.compareTo(periodDate) > 0) {
			// メッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_PASSWORD_LIMIT);
		}
	}
	
	@Override
	public void checkPasswordStrength(String userId) throws MospException {
		// パスワード確認プロパティ値毎に確認を実施
		for (String passwordCheck : getPasswordCheckProperty()) {
			if (passwordCheck.equals(PASS_CHECK_INIT_INVALID)) {
				// 初期パスワード不可確認
				checkInitInvalid(userId);
			}
		}
	}
	
	@Override
	public void checkPasswordChange(String userId, String oldPass, String newPass, String confirmPass)
			throws MospException {
		// 新しいパスワードとパスワード入力確認を比較
		if (confirmPass.equals(newPass) == false) {
			// 新しいパスワードとパスワード入力確認が異なればメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_PASSWORD_NOT_CONFIRMED);
			return;
		}
		// 現在のパスワードと新しいパスワード比較
		if (oldPass.equals(newPass)) {
			// パスワードが変更されていなければメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_PASSWORD_CONFIRMATION);
			return;
		}
		// 現在のパスワードを取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// 現在のパスワードを確認
		if (dto == null || dto.getPassword().equals(oldPass) == false) {
			// 現在のパスワードが異なっていればメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_OLD_PASSWORD);
			return;
		}
	}
	
	@Override
	public String getInitialPassword(String userId) {
		// アプリケーション設定取得
		String initialPassword = mospParams.getApplicationProperty(APP_INITIAL_PASSWORD);
		// 初期パスワード設定確認
		if (initialPassword == null || initialPassword.isEmpty() || initialPassword.equals(INITIAL_PASS_USER_ID)) {
			return userId;
		}
		return initialPassword;
	}
	
	@Override
	public boolean isInitinalPasswordValid() {
		// パスワード確認プロパティ値を確認
		for (String passwordCheck : getPasswordCheckProperty()) {
			if (passwordCheck.equals(PASS_CHECK_INIT_INVALID)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 初期パスワード不可確認を行う。<br>
	 * @param userId     ユーザID
	 * @throws MospException SQL実行、或いは暗号化に失敗した場合
	 */
	protected void checkInitInvalid(String userId) throws MospException {
		// ユーザパスワード情報の取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// 初期パスワード取得
		String initialPassword = SeUtility.encrypt(SeUtility.encrypt(getInitialPassword(userId)));
		// ユーザパスワード情報と初期パスワードを比較
		if (dto.getPassword().equals(initialPassword)) {
			// メッセージ追加()
			mospParams.addErrorMessage(PlatformMessageConst.MSG_INIT_PASSWORD_INVALID);
		}
	}
	
	/**
	 * パスワード確認プロパティ値を取得する。<br>
	 * {@link MospProperties#getApplicationProperty(String)}から取得する。<br>
	 * @return パスワード確認プロパティ値
	 */
	protected String[] getPasswordCheckProperty() {
		// プロパティ取得
		String checkPassword = mospParams.getApplicationProperty(APP_CHECK_PASSWORD);
		// プロパティ確認
		if (checkPassword == null) {
			return new String[0];
		}
		// カンマで分割
		return checkPassword.split(MospConst.APP_PROPERTY_SEPARATOR);
	}
	
}
