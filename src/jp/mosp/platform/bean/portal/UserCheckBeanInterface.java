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
 * ユーザ確認インターフェース。<br>
 */
public interface UserCheckBeanInterface {
	
	/**
	 * ユーザ社員の確認を行う。<br>
	 * ユーザIDから個人IDを取得し、
	 * {@link #checkUserEmployee(String, Date)}を行う。<br>
	 * 認証時等に用いられる。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkUserEmployeeForUserId(String userId, Date targetDate) throws MospException;
	
	/**
	 * ユーザ妥当性の確認を行う。<br>
	 * <ul><li>
	 * 入社確認
	 * </li><li>
	 * 休職確認
	 * </li><li>
	 * 退職確認
	 * </li></ul>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkUserEmployee(String personalId, Date targetDate) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkRoleExist(String roleCode, Date targetDate) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * ユーザIDからロールコードを取得し、
	 * {@link #checkRoleExist(String, Date)}を行う。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkUserRole(String userId, Date targetDate) throws MospException;
	
}
