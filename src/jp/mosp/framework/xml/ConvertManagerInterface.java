/**
 *
 */
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.BaseProperty;

/**
 * @author yoshida
 *
 */
public interface ConvertManagerInterface {
	
	/**
	 * @return
	 */
	public ConvertResultInterface init();
	
	/**
	 * @param tagName
	 * @return
	 */
	public boolean isUnknown(String tagName);
	
	/**
	 * @param properties
	 * @param wrapper
	 * @return
	 */
	public void convert(Map<String, BaseProperty> properties, NodeWrapper wrapper);
}
