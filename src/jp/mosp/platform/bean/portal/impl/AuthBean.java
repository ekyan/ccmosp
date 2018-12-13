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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * 認証クラス。<br>
 * <br>
 * 詳細は、{@link AuthBeanInterface}を参照。
 */
public class AuthBean extends PlatformBean implements AuthBeanInterface {
	
	/**
	 * ユーザパスワード情報DAO。<br>
	 */
	private UserPasswordDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AuthBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param actionInfo 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	public AuthBean(MospParams actionInfo, Connection connection) {
		super(actionInfo, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (UserPasswordDaoInterface)createDao(UserPasswordDaoInterface.class);
	}
	
	@Override
	public void authenticate(String userId, String password) throws MospException {
		// ユーザIDでユーザパスワード情報を取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// ユーザパスワード情報存在確認
		if (dto == null) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_AUTH_FAILED);
			return;
		}
		// パスワード妥当性確認
		if (SeUtility.encrypt(password).equals(dto.getPassword()) == false) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(PlatformMessageConst.MSG_AUTH_FAILED);
		}
		return;
	}
	
}
