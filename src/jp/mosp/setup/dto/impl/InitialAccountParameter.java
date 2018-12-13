/**
 * 
 */
package jp.mosp.setup.dto.impl;

import java.io.Serializable;
import java.util.Date;

import jp.mosp.setup.dto.InitialAccountParameterInterface;

/**
 * 初期アカウント登録パラメータクラス。
 */
public class InitialAccountParameter implements Serializable, InitialAccountParameterInterface {
	
	private static final long	serialVersionUID	= 4452984768286447824L;
	
	private String				userId;
	
	private String				employeeCode;
	
	private String				lastName;
	
	private String				firstName;
	
	private String				lastKana;
	
	private String				firstKana;
	
	private Date				entranceDate;
	
	private Date				activateDate;
	
	private String				roleCode;
	
	
	public String getUserId() {
		return userId;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastKana() {
		return lastKana;
	}
	
	public String getFirstKana() {
		return firstKana;
	}
	
	public Date getEntranceDate() {
		return entranceDate;
	}
	
	public Date getActivateDate() {
		return activateDate;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = entranceDate;
	}
	
	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}
	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
