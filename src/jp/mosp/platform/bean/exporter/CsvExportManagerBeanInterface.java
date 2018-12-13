/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;

/**
 * CSV出力処理管理インターフェース。
 */
public interface CsvExportManagerBeanInterface {
	
	/**
	 * CSV出力処理
	 * @param compiler CSVエクスポート用編集処理オブジェクト
	 * @throws MospException 例外処理発生
	 */
	public void delivery(CsvCompilerInterface compiler) throws MospException;
	
}
