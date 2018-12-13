/**
 *
 */
package jp.mosp.framework.property;

/**
 * @author yoshida
 *
 */
public class NamingProperty implements BaseProperty {
	
	private String	key;
	
	private String	value;
	
	
	/**
	 * @param key
	 * @param value
	 */
	public NamingProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
}
