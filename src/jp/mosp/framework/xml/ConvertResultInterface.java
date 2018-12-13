/**
 *
 */
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.AddonProperty;
import jp.mosp.framework.property.ApplicationProperty;
import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.CodeProperty;
import jp.mosp.framework.property.CommandProperty;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MessageProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.property.NamingProperty;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.framework.property.ViewConfigProperty;

/**
 * @author yoshida
 *
 */
interface ConvertResultInterface {
	
	public <T extends BaseProperty>Map<String, T> get(String key);
	
	public Map<String, ConventionProperty> getConvention();
	
	public Map<String, RoleProperty> getRole();
	
	public Map<String, MainMenuProperty> getMainMenu();
	
	public Map<String, AddonProperty> getAddon();
	
	public Map<String, CodeProperty> getCode();
	
	public Map<String, NamingProperty> getNaming();
	
	public Map<String, MessageProperty> getMessage();
	
	public Map<String, CommandProperty> getController();
	
	public Map<String, ApplicationProperty> getApplication();
	
	public Map<String, ViewConfigProperty> getViewConfig();
	
	public Map<String, ModelProperty> getModel();
	
}
