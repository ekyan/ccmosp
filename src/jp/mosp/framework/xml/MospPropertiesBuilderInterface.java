/**
 *
 */
package jp.mosp.framework.xml;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;

/**
 * @author yoshida
 *
 */
public interface MospPropertiesBuilderInterface {
	
	/**
	 * ログメッセージ(ドキュメントエレメント不正)。
	 */
	static final String	MSG_INVALID_DOC_ELEMENT	= "  MosP設定情報ファイルのドキュメント要素が不正です。";
	
	/**
	 * ログメッセージ(要素値無し)。
	 */
	static final String	MSG_INVALID_VALUE		= "  MosP設定情報ファイルの要素値が不正です。";
	
	/**
	 * ログメッセージ(アドオン無効)。
	 */
	static final String	MSG_ADDON_INVALID		= "  MosP設定情報ファイルのアドオン設定が無効です。";
	
	
	/**
	 * MosP設定情報生成
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定情報
	 * @throws MospException ドキュメントリストの取得に失敗した場合
	 */
	public abstract MospProperties build(String docBase) throws MospException;
	
}
