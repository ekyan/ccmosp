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
public interface TagConverterInterface {
	
	/**
	 * @param properties
	 * @param wrapper
	 */
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper);
	
}
