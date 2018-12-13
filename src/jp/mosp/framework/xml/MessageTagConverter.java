/**
 *
 */
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.MessageProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author yoshida
 *
 */
public class MessageTagConverter implements TagConverterInterface {
	
	/**
	 * メッセージ要素の下位要素名(メッセージ本体)。
	 */
	private static final String	TAG_MESSAGE_BODY		= "MessageBody";
	
	/**
	 * メッセージ要素の下位要素名(クライアント利用可否)。
	 */
	private static final String	TAG_CLIENT_AVAILABLE	= "ClientAvailable";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Message
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
		// メッセージ(メッセージ設定情報)準備
		String messageBody = null;
		// クライアント利用可否(メッセージ設定情報)準備(default:false)
		boolean clientAvailable = false;
		
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			// メッセージ(メッセージ設定情報)
			if (TagUtility.isTag(item, TAG_MESSAGE_BODY)) {
				messageBody = TagUtility.trimText(item);
			}
			// クライアント利用可否(メッセージ設定情報)準備
			if (TagUtility.isTag(item, TAG_CLIENT_AVAILABLE)) {
				clientAvailable = Boolean.parseBoolean(TagUtility.trimText(item));
			}
			itemIndex++;
		}
		// メッセージ設定情報追加確認
		if (messageBody == null) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		// メッセージ設定情報追加
		properties.put(key, new MessageProperty(key, messageBody, clientAvailable));
	}
	
}
