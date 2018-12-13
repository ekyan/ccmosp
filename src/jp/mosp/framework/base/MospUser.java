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
package jp.mosp.framework.base;

import java.io.Serializable;

/**
 * MosPユーザー情報を扱う。<br>
 */
public class MospUser implements Serializable {
	
	private static final long	serialVersionUID	= 8544895877748481588L;
	
	/**
	 * ASPユーザーID。
	 */
	private String				aspUserId;
	
	/**
	 * JDBCドライバー名。
	 */
	private String				dbDriver;
	/**
	 * DBURL。
	 */
	private String				dbUrl;
	/**
	 * DBユーザーID。
	 */
	private String				dbUser;
	/**
	 * DBパスワード。
	 */
	private String				dbPass;
	
	/**
	 * ユーザID。
	 */
	private String				userId;
	
	/**
	 * ロール
	 */
	private String				role;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * ユーザ名。
	 */
	private String				userName;
	
	
	/**
	 * ASPユーザーIDを取得する。<br>
	 * @return ASPユーザーID
	 */
	public String getAspUserId() {
		return aspUserId;
	}
	
	/**
	 * ASPユーザーIDを設定する。<br>
	 * @param aspUserId セットするASPユーザーID
	 */
	public void setAspUserId(String aspUserId) {
		this.aspUserId = aspUserId;
	}
	
	/**
	 * ユーザIDを取得する。<br>
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * ユーザIDを設定する。<br>
	 * @param userId セットするユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 個人IDを取得する。<br>
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * 個人IDを設定する。<br>
	 * @param personalId セットする個人ID
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * ユーザ名を取得する。<br>
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * ユーザ名を設定する。<br>
	 * @param userName セットするユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}
	
	/**
	 * @return dbUrl
	 */
	public String getDbUrl() {
		return dbUrl;
	}
	
	/**
	 * @return dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}
	
	/**
	 * @return dbPass
	 */
	public String getDbPass() {
		return dbPass;
	}
	
	/**
	 * @param dbDriver セットする dbDriver
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
	/**
	 * @param dbUrl セットする dbUrl
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	/**
	 * @param dbUser セットする dbUser
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**
	 * @param dbPass セットする dbPass
	 */
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	
	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @param role セットする role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}
