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
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザ確認クラス。<br>
 * <br>
 * 認証時、アカウントメンテナンス時等に、ユーザが妥当であるかの確認を行う。<br>
 */
public class UserCheckBean extends PlatformBean implements UserCheckBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(認証時ユーザ確認)。
	 */
	protected static final String	APP_CHECK_USER			= "CheckUser";
	
	/**
	 * 認証時ユーザ確認設定(入社確認)。
	 */
	protected static final String	USER_CHECK_ENTRANCE		= "Entrance";
	
	/**
	 * 認証時ユーザ確認設定(退職確認)。
	 */
	protected static final String	USER_CHECK_RETIREMENT	= "Retirement";
	
	/**
	 * 認証時ユーザ確認設定(休職確認)。
	 */
	protected static final String	USER_CHECK_SUSPENSION	= "Suspension";
	
	/**
	 * ユーザマスタDAO。
	 */
	private UserMasterDaoInterface	userMasterDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserCheckBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected UserCheckBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		userMasterDao = (UserMasterDaoInterface)createDao(UserMasterDaoInterface.class);
	}
	
	@Override
	public void checkUserEmployeeForUserId(String userId, Date targetDate) throws MospException {
		// ユーザ情報取得
		UserMasterDtoInterface dto = userMasterDao.findForInfo(userId, targetDate);
		// ユーザ存在確認
		if (dto == null || dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// ユーザが存在しない場合のメッセージを設定
			addUserNotExistMessage();
			return;
		}
		// ユーザ社員妥当性確認
		checkUserEmployee(dto.getPersonalId(), targetDate);
	}
	
	@Override
	public void checkUserEmployee(String personalId, Date targetDate) throws MospException {
		// ユーザ確認プロパティ値毎に確認を実施
		for (String userCheck : mospParams.getApplicationProperties(APP_CHECK_USER)) {
			if (userCheck.equals(USER_CHECK_ENTRANCE)) {
				// 入社情報確認
				checkEntrance(personalId, targetDate);
			} else if (userCheck.equals(USER_CHECK_RETIREMENT)) {
				// 退職情報確認
				checkRetire(personalId, targetDate);
			} else if (userCheck.equals(USER_CHECK_SUSPENSION)) {
				// 休職情報確認
				checkSuspension(personalId, targetDate);
			}
		}
	}
	
	@Override
	public void checkUserRole(String userId, Date targetDate) throws MospException {
		// ユーザ情報取得
		UserMasterDtoInterface dto = userMasterDao.findForInfo(userId, targetDate);
		// ユーザ存在確認
		if (dto == null || dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// ユーザが存在しない場合のメッセージを設定
			addUserNotExistMessage();
			return;
		}
		// ロール存在確認
		checkRoleExist(dto.getRoleCode(), targetDate);
	}
	
	@Override
	public void checkRoleExist(String roleCode, Date targetDate) throws MospException {
		// ロール参照
		RoleReferenceBeanInterface roleRefer = (RoleReferenceBeanInterface)createBean(RoleReferenceBeanInterface.class);
		// ロール取得
		String[][] roleArray = roleRefer.getSelectArray(targetDate, false);
		// ロール確認
		for (String[] role : roleArray) {
			if (role[0].equals(roleCode)) {
				return;
			}
		}
		// ロールが存在しない場合のメッセージを設定
		addRoleNotExistMessage();
	}
	
	/**
	 * 入社確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntrance(String personalId, Date targetDate) throws MospException {
		// 人事入社情報参照クラス取得
		EntranceReferenceBeanInterface reference;
		reference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		// 入社情報確認
		if (reference.isEntered(personalId, targetDate) == false) {
			HumanReferenceBeanInterface humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
			// 社員が入社していない場合のメッセージを設定
			addEmployeeNotEnteredMessage(humanReference.getEmployeeCode(personalId, targetDate));
		}
	}
	
	/**
	 * 退職確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetire(String personalId, Date targetDate) throws MospException {
		// 人事退職情報参照クラス取得
		RetirementReferenceBeanInterface reference;
		reference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		// 退職情報確認
		if (reference.isRetired(personalId, targetDate)) {
			// 社員が退職している場合のメッセージを設定
			addEmployeeRetiredMessage();
		}
	}
	
	/**
	 * 休職確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspension(String personalId, Date targetDate) throws MospException {
		// 人事休職情報参照クラス取得
		SuspensionReferenceBeanInterface reference;
		reference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		// 休職情報確認
		if (reference.isSuspended(personalId, targetDate)) {
			// 社員が休職している場合のメッセージを設定
			addEmployeeSuspendedMessage();
		}
	}
	
	/**
	 * ユーザが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addUserNotExistMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("User"));
	}
	
	/**
	 * ロールが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addRoleNotExistMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Role"));
	}
	
	/**
	 * 社員が入社していない場合のメッセージを設定する。<br>
	 * @param employeeCode 社員コード
	 */
	protected void addEmployeeNotEnteredMessage(String employeeCode) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NOT_ENTERED_FOR_ACCOUNT, employeeCode);
	}
	
}
