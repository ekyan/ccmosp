/**
 * 
 */
package jp.mosp.platform.dto.exporter;

import java.io.Serializable;

import jp.mosp.orangesignal.OrangeSignalParams;

/**
 * CSVで出力する中間パラメータクラス。
 */
public class CsvExportIntermediate implements Serializable {
	
	private static final long			serialVersionUID	= -857194997397651382L;
	
	private final String				filePrefix;
	
	private final OrangeSignalParams	orangeSignalParams;
	
	
	/**
	 * コンストラクタ。
	 * @param filePrefix .csvの前の文字列。
	 * @param orangeSignalParams OrangeSignal処理情報
	 */
	public CsvExportIntermediate(String filePrefix, OrangeSignalParams orangeSignalParams) {
		this.filePrefix = filePrefix;
		this.orangeSignalParams = orangeSignalParams;
	}
	
	/**
	 * @return filePrefix .csvの前の文字列。
	 */
	public String getFilePrefix() {
		return filePrefix;
	}
	
	/**
	 * @return orangeSignalParams OrangeSignal処理情報
	 */
	public OrangeSignalParams getOrangeSignalParams() {
		return orangeSignalParams;
	}
	
}
