/**
 *
 */
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ApplicationProperty;
import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.utils.MospUtility;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author yoshida
 *
 */
public class ApplicationTagConverter implements TagConverterInterface {
	
	/**
	 * アプリケーション要素の下位要素名(値)。
	 */
	private static final String	TAG_VALUE		= "Value";
	
	/**
	 * アプリケーション要素の下位要素名(追加要否)。
	 */
	private static final String	TAG_ADD_VALUE	= "AddValue";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Application
		Node node = wrapper.getNode();
		int index = wrapper.index;
		String path = wrapper.path;
		// キー情報取得
		String key = TagUtility.getKey(node);
		// キー情報確認
		if (key.isEmpty()) {
			// エラーログ出力
			TagUtility.noElementKeyMessage(path, node, index);
			return;
		}
		// 値及び追加要否(デフォルト：false)準備
		String value = null;
		boolean addValue = false;
		
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			if (TagUtility.isTag(item, TAG_VALUE)) {
				value = TagUtility.trimText(item);
			}
			if (TagUtility.isTag(item, TAG_ADD_VALUE)) {
				addValue = Boolean.parseBoolean(TagUtility.trimText(item));
			}
			itemIndex++;
		}
		if (value == null) {
			value = TagUtility.trimText(node);
		}
		// 設定情報確認
		if (value == null) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		// 追加要否確認
		if (addValue == false) {
			// 設定情報追加
			properties.put(key, new ApplicationProperty(key, value));
			return;
		}
		// 設定値取得及び確認
		BaseProperty formerValue = properties.get(key);
		if (formerValue == null) {
			// 設定情報追加
			properties.put(key, new ApplicationProperty(key, value));
			return;
		}
		ApplicationProperty property = (ApplicationProperty)formerValue;
		// 設定値分割
		String[] aryFormer = MospUtility.split(property.getValue(), MospConst.APP_PROPERTY_SEPARATOR);
		// 設定値確認
		for (String former : aryFormer) {
			// 設定値に追加要素が含まれている場合
			if (value.equals(former)) {
				return;
			}
		}
		// 設定情報追加
		properties.put(key,
				new ApplicationProperty(key, property.getValue() + MospConst.APP_PROPERTY_SEPARATOR + value));
	}
	
}
