/**
 *
 */
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.CommandProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author yoshida
 *
 */
public class ControllerTagConverter implements TagConverterInterface {
	
	/**
	 * コントローラー要素の下位要素名(アクションクラス)。
	 */
	static final String	ACTION_CLASS		= "ActionClass";
	
	/**
	 * コントローラー要素の下位要素名(HTTPセッション要否)。
	 */
	static final String	TAG_NEED_SESSION	= "NeedSession";
	
	/**
	 * コントローラー要素の下位要素名(処理シーケンス要否)。
	 */
	static final String	TAG_NEED_PROC_SEQ	= "NeedProcSeq";
	
	/**
	 * コントローラー要素の下位要素名(許可HTTPメソッド)。
	 */
	static final String	TAG_ACCEPT_METHOD	= "AcceptMethod";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Controller
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
		// アクションクラス(コントローラー設定情報)準備
		String actionClass = null;
		// HTTPセッション要否(コントローラー設定情報)準備
		String needSession = null;
		// 処理シーケンス要否(コントローラー設定情報)準備
		String needProcSeq = null;
		// 許可HPPTメソッド(コントローラー設定情報)準備
		String acceptMethod = null;
		
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			if (TagUtility.isTag(item, ACTION_CLASS)) {
				// アクションクラス
				actionClass = TagUtility.trimText(item);
			}
			if (TagUtility.isTag(item, TAG_NEED_SESSION)) {
				// HTTPセッション要否
				needSession = TagUtility.trimText(item);
			}
			if (TagUtility.isTag(item, TAG_NEED_PROC_SEQ)) {
				// 処理シーケンス要否
				needProcSeq = TagUtility.trimText(item);
			}
			if (TagUtility.isTag(item, TAG_ACCEPT_METHOD)) {
				// 許可HTTPメソッド
				acceptMethod = TagUtility.trimText(item);
			}
			itemIndex++;
		}
		if (actionClass == null || actionClass.isEmpty()) {
			TagUtility.invalidMassage(path, node);
			return;
		}
		// コントローラー設定情報
		CommandProperty value = new CommandProperty(key, actionClass, needSession, needProcSeq, acceptMethod);
		// コントローラー設定情報追加
		properties.put(key, value);
	}
	
}
