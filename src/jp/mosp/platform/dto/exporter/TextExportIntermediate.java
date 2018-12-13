/**
 * 
 */
package jp.mosp.platform.dto.exporter;

import java.io.Serializable;

import jp.mosp.framework.exporter.TextContents;

/**
 * テキストで出力する中間パラメータクラス。
 */
public class TextExportIntermediate implements Serializable {
	
	private static final long	serialVersionUID	= -3885731566395941206L;
	
	private final String		filePrefix;
	
	private TextContents		textContents;
	
	
	/**
	 * コンストラクタ。
	 * @param filePrefix .txtの前の文字列。
	 * @param encoding 文字コード
	 * @param contents テキスト出力の内容
	 */
	public TextExportIntermediate(String filePrefix, String encoding, String contents) {
		this.filePrefix = filePrefix;
		textContents = new TextContents(encoding, contents);
	}
	
	/**
	 * @return filePrefix .txtの前の文字列。
	 */
	public String getFilePrefix() {
		return filePrefix;
	}
	
	/**
	 * @return contents テキスト出力の内容オブジェクト
	 */
	public TextContents getTextContents() {
		return textContents;
	}
	
}
