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
package jp.mosp.platform.bean.portal;

import java.util.Date;

import jp.mosp.framework.base.MospException;

/**
 * パスワード確認インターフェース。
 */
public interface PasswordCheckBeanInterface {
	
	/**
	 * パスワード有効期間の確認を行う。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkPasswordPeriod(String userId, Date targetDate) throws MospException;
	
	/**
	 * パスワード堅牢性の確認を行う。<br>
	 * @param userId ユーザID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkPasswordStrength(String userId) throws MospException;
	
	/**
	 * パスワード変更可否の確認を行う。<br>
	 * パスワード堅牢性の確認も、併せて行う。<br>
	 * 各パスワードは既に暗号化されているものとする。<br>
	 * @param userId      ユーザID
	 * @param oldPass     旧パスワード
	 * @param newPass     新パスワード
	 * @param confirmPass 確認パスワード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkPasswordChange(String userId, String oldPass, String newPass, String confirmPass) throws MospException;
	
	/**
	 * 初期パスワードを取得する。<br>
	 * @param userId ユーザID
	 * @return 初期パスワード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getInitialPassword(String userId) throws MospException;
	
	/**
	 * 初期パスワード可否を取得する。<br>
	 * @return 初期パスワード可否(true：可、false：不可)
	 */
	boolean isInitinalPasswordValid();
	
}
