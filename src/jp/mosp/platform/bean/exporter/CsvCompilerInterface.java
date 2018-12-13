/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.exporter.CsvExportIntermediate;

/**
 * CSVエクスポート用編集処理インターフェース。
 */
public interface CsvCompilerInterface {
	
	/**
	 * @return CSVデータ
	 * @throws MospException 例外処理発生時
	 */
	CsvExportIntermediate compile() throws MospException;
	
}
