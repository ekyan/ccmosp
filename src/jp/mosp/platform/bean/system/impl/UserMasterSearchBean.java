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
package jp.mosp.platform.bean.system.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.AccountInfoDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.impl.AccountInfoDto;

/**
 * ユーザマスタ検索クラス。
 */
public class UserMasterSearchBean extends PlatformBean implements UserMasterSearchBeanInterface {
	
	/**
	 * ユーザマスタ検索DAO
	 */
	private UserMasterDaoInterface	userMasterDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * ユーザID。
	 */
	private String					userId;
	
	/**
	 * 社員コード。
	 */
	private String					employeeCode;
	
	/**
	 * 社員名。
	 */
	private String					employeeName;
	
	/**
	 * ロールコード。
	 */
	private String					roleCode;
	
	/**
	 * 有効無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public UserMasterSearchBean() {
		// 処理無し
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション。
	 */
	public UserMasterSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		userMasterDao = (UserMasterDaoInterface)createDao(UserMasterDaoInterface.class);
	}
	
	@Override
	public List<AccountInfoDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = userMasterDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("userId", userId);
		param.put("employeeCode", employeeCode);
		param.put("employeeName", employeeName);
		param.put("roleCode", roleCode);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		List<UserMasterDtoInterface> userList = userMasterDao.findForSearch(param);
		// アカウント情報リスト準備
		List<AccountInfoDtoInterface> accountList = new ArrayList<AccountInfoDtoInterface>();
		// 検索結果確認
		if (userList.size() == 0) {
			return accountList;
		}
		// 人事マスタDAO準備
		HumanDaoInterface humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		// ロール参照準備
		RoleReferenceBeanInterface roleReference = (RoleReferenceBeanInterface)createBean(RoleReferenceBeanInterface.class);
		// ロール配列取得
		String[][] roleArray = roleReference.getSelectArray(activateDate, false);
		// アカウント情報リスト作成
		for (UserMasterDtoInterface userDto : userList) {
			// 社員コード、有効日から社員情報を取得する
			HumanDtoInterface humanDto = humanDao.findForInfo(userDto.getPersonalId(), activateDate);
			// アカウント情報DTO生成
			AccountInfoDtoInterface dto = new AccountInfoDto();
			dto.setActivateDate(userDto.getActivateDate());
			dto.setPersonalId(userDto.getPersonalId());
			dto.setUserId(userDto.getUserId());
			dto.setInactivateFlag(userDto.getInactivateFlag());
			dto.setPfmUserId(userDto.getPfmUserId());
			dto.setRoleCode(userDto.getRoleCode());
			dto.setRoleName(getCodeName(userDto.getRoleCode(), roleArray));
			dto.setEmployeeCode("");
			dto.setLastName("");
			dto.setFirstName("");
			// 人事情報設定
			if (humanDto != null) {
				dto.setEmployeeCode(humanDto.getEmployeeCode());
				dto.setLastName(humanDto.getLastName());
				dto.setFirstName(humanDto.getFirstName());
			}
			// リスト追加
			accountList.add(dto);
		}
		return accountList;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
