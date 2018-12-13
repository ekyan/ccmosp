/**
 *
 */
package jp.mosp.framework.xml;

import java.io.Serializable;

import org.w3c.dom.Document;

/**
 * @author yoshida
 *
 */
public class DocumentWrapper implements Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 6850067929501863848L;
	
	final String				path;
	
	private final Document		document;
	
	
	/**
	 * コンストラクタ。
	 * @param path xmlのファイルパス。
	 * @param document documentオブジェクト。
	 */
	public DocumentWrapper(String path, Document document) {
		this.path = path;
		this.document = document;
	}
	
	/**
	 * @return document xmlDocumentオブジェクト。
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * @return MosP用のxmlの場合true、そうでない場合false。
	 */
	public boolean isMosPDocument() {
		return PropertyTag.TAG_DOCUMENT.equals(document.getDocumentElement().getTagName());
	}
	
	/**
	 * @return アドオンの場合true、そうでない場合false
	 */
	public boolean isAddon() {
		return TagUtility.getString(PropertyTag.TAG_DOCUMENT + "/Addon", document).isEmpty() == false;
	}
	
	/**
	 * @return アドオン無効の場合true、そうでない場合false
	 */
	public boolean isAddonValid() {
		return Boolean.parseBoolean(TagUtility.getString(PropertyTag.TAG_DOCUMENT + "/Addon/AddonValid[text()]",
				document));
	}
	
}
