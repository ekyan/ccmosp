/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.exporter.TextExportIntermediate;

/**
 * テキスト出力用編集処理インターフェース。
 */
public interface TextCompilerInterface {
	
	/**
	 * @return テキスト出力データ
	 * @throws MospException 例外処理発生時
	 */
	public TextExportIntermediate compile() throws MospException;
	
}
