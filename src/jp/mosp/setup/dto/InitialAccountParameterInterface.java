/**
 * 
 */
package jp.mosp.setup.dto;

import java.util.Date;

/**
 * 初期アカウント登録パラメータインターフェース。
 */
public interface InitialAccountParameterInterface {
	
	/**
	 * @return userId
	 */
	public String getUserId();
	
	/**
	 * @return employeeCode
	 */
	public String getEmployeeCode();
	
	/**
	 * @return lastName
	 */
	public String getLastName();
	
	/**
	 * @return firstName
	 */
	public String getFirstName();
	
	/**
	 * @return lastKana
	 */
	public String getLastKana();
	
	/**
	 * @return firstKana
	 */
	public String getFirstKana();
	
	/**
	 * @return entranceDate
	 */
	public Date getEntranceDate();
	
	/**
	 * @return activateDate
	 */
	public Date getActivateDate();
	
	/**
	 * @return roleCode
	 */
	public String getRoleCode();
	
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId);
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	public void setEmployeeCode(String employeeCode);
	
	/**
	 * @param lastName セットする lastName
	 */
	public void setLastName(String lastName);
	
	/**
	 * @param firstName セットする firstName
	 */
	public void setFirstName(String firstName);
	
	/**
	 * @param lastKana セットする lastKana
	 */
	public void setLastKana(String lastKana);
	
	/**
	 * @param firstKana セットする firstKana
	 */
	public void setFirstKana(String firstKana);
	
	/**
	 * @param entranceDate セットする entranceDate
	 */
	public void setEntranceDate(Date entranceDate);
	
	/**
	 * @param activateDate セットする activateDate
	 */
	public void setActivateDate(Date activateDate);
	
	/**
	 * @param roleCode セットする roleCode
	 */
	public void setRoleCode(String roleCode);
	
}
