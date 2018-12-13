/**
 * 
 */
package jp.mosp.setup.dto.impl;

import java.util.Date;

import jp.mosp.setup.constant.Command;
import jp.mosp.setup.dto.DbSetUpParameterInterface;

/**
 * データベースセットアップパラメータクラス。
 */
public class DbSetUpParameter implements DbSetUpParameterInterface {
	
	private static final long	serialVersionUID	= 7949200534431360799L;
	
	private String				userId;
	
	private String				employeeCode;
	
	private String				lastName;
	
	private String				firstName;
	
	private String				lastKana;
	
	private String				firstKana;
	
	private Date				entranceDate;
	
	private Date				activateDate;
	
	private String				roleCode;
	
	private String				serverName;
	
	private int					port;
	
	private String				postgresDb;
	
	private String				superUser;
	
	private String				superPassword;
	
	private String				defaultDbUser;
	
	private String[]			dirs;
	
	private Command				command;
	
	private String				dbName;
	
	private String				userName;
	
	private String				userPassword;
	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastKana() {
		return lastKana;
	}
	
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	public String getFirstKana() {
		return firstKana;
	}
	
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	public Date getEntranceDate() {
		return entranceDate;
	}
	
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = entranceDate;
	}
	
	public Date getActivateDate() {
		return activateDate;
	}
	
	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getPostgresDb() {
		return postgresDb;
	}
	
	/**
	 * @param postgresDb セットする postgresDb
	 */
	public void setPostgresDb(String postgresDb) {
		this.postgresDb = postgresDb;
	}
	
	public String getSuperUser() {
		return superUser;
	}
	
	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}
	
	public String getSuperPassword() {
		return superPassword;
	}
	
	public void setSuperPassword(String superPassword) {
		this.superPassword = superPassword;
	}
	
	public String getDefaultDbUser() {
		return defaultDbUser;
	}
	
	/**
	 * @param defaultDbUser セットする defaultDbUser
	 */
	public void setDefaultDbUser(String defaultDbUser) {
		this.defaultDbUser = defaultDbUser;
	}
	
	public String[] getDirs() {
		return dirs;
	}
	
	public void setDirs(String[] dirs) {
		this.dirs = dirs;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
