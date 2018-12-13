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
import jp.mosp.framework.base.MospUser;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.portal.MospUserBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * MosPユーザ設定クラス。<br>
 */
public class MospUserBean extends PlatformBean implements MospUserBeanInterface {
	
	/**
	 * ユーザ確認処理。<br>
	 */
	protected UserCheckBeanInterface				userCheck;
	
	/**
	 * ユーザマスタ参照処理。<br>
	 */
	protected UserMasterReferenceBeanInterface		userRefer;
	
	/**
	 * ユーザマスタ参照処理。<br>
	 */
	protected HumanReferenceBeanInterface			humanRefer;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface			roleRefer;
	
	/**
	 * ユーザ追加ロール情報参照処理。<br>
	 */
	protected UserExtraRoleReferenceBeanInterface	userExtraRoleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MospUserBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param actionInfo 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	public MospUserBean(MospParams actionInfo, Connection connection) {
		super(actionInfo, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		userCheck = (UserCheckBeanInterface)createBean(UserCheckBeanInterface.class);
		userRefer = (UserMasterReferenceBeanInterface)createBean(UserMasterReferenceBeanInterface.class);
		humanRefer = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		roleRefer = (RoleReferenceBeanInterface)createBean(RoleReferenceBeanInterface.class);
		userExtraRoleRefer = (UserExtraRoleReferenceBeanInterface)createBean(UserExtraRoleReferenceBeanInterface.class);
	}
	
	@Override
	public void setMospUser(String userId) throws MospException {
		// システム日付を取得
		Date systemDate = DateUtility.getSystemDate();
		// ユーザ確認(対象日はシステム日付)
		userCheck.checkUserEmployeeForUserId(userId, systemDate);
		// ロール確認(対象日はシステム日付)
		userCheck.checkUserRole(userId, systemDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザ情報取得
		UserMasterDtoInterface userDto = userRefer.getUserInfo(userId, systemDate);
		// 個人IDを取得
		String personalId = userDto.getPersonalId();
		// 人事情報取得
		HumanDtoInterface humanDto = humanRefer.getHumanInfo(personalId, systemDate);
		// MosPユーザ情報を取得
		MospUser user = mospParams.getUser();
		// MosPユーザ情報が取得できなかった場合
		if (user == null) {
			// MosPユーザ情報を新規に作成
			user = new MospUser();
		}
		// MosPユーザ情報に値を設定
		user.setUserId(userId);
		user.setPersonalId(personalId);
		user.setRole(userDto.getRoleCode());
		user.setUserName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		// 追加ロールが利用可能である場合
		if (roleRefer.isExtraRolesAvailable(systemDate)) {
			// 追加ロール群を設定
			user.setExtraRoles(userExtraRoleRefer.getUserExtraRoles(userId, userDto.getActivateDate()));
		}
		// MosPユーザ情報をMosP処理情報に設定
		mospParams.setUser(user);
	}
	
}
