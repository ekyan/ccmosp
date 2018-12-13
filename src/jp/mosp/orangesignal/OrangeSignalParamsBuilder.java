/**
 *
 */
package jp.mosp.orangesignal;

import java.util.List;

import jp.sf.orangesignal.csv.CsvConfig;

/**
 * {@link OrangeSignalParams}の生成をサポートするBuilderクラス。
 */
public class OrangeSignalParamsBuilder {
	
	private String	encoding;
	
	private boolean	ignoreEmptyLines;
	
	private char	escape;
	
	private char	quote;
	
	private char	separator;
	
	
	OrangeSignalParamsBuilder() {
		// 区切り文字(,)
		separator = CsvConfig.DEFAULT_SEPARATOR;
		// 囲み文字(")
		quote = CsvConfig.DEFAULT_QUOTE;
		// エスケープ文字(\)
		escape = CsvConfig.DEFAULT_ESCAPE;
		// 文字コード
		encoding = OrangeSignalParams.DEFAULT_ENCODING;
		// 空行無視フラグ(空行無視)
		ignoreEmptyLines = true;
	}
	
	/**
	 * @param separator セットする separator
	 * @return this
	 */
	public OrangeSignalParamsBuilder setSeparator(char separator) {
		this.separator = separator;
		return this;
	}
	
	/**
	 * @param quote セットする quote
	 * @return this
	 */
	public OrangeSignalParamsBuilder setQuote(char quote) {
		this.quote = quote;
		return this;
	}
	
	/**
	 * @param escape セットする escape
	 * @return this
	 */
	public OrangeSignalParamsBuilder setEscape(char escape) {
		this.escape = escape;
		return this;
	}
	
	/**
	 * @param ignoreEmptyLines セットする ignoreEmptyLines
	 * @return this
	 */
	public OrangeSignalParamsBuilder setIgnoreEmptyLines(boolean ignoreEmptyLines) {
		this.ignoreEmptyLines = ignoreEmptyLines;
		return this;
	}
	
	/**
	 * @param encoding セットする encoding
	 * @return this
	 */
	public OrangeSignalParamsBuilder setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * @return インスタンス
	 */
	public static OrangeSignalParamsBuilder newBuilder() {
		return new OrangeSignalParamsBuilder();
	}
	
	/**
	 * @param csvDataList CSVデータ
	 * @return OrangeSignal処理情報
	 */
	public OrangeSignalParams build(List<String[]> csvDataList) {
		if (csvDataList == null) {
			throw new IllegalArgumentException("param:csvDataList is null.");
		}
		OrangeSignalParams params = build();
		params.setCsvDataList(csvDataList);
		return params;
	}
	
	/**
	 * @return OrangeSignal処理情報
	 */
	public OrangeSignalParams build() {
		OrangeSignalParams params = new OrangeSignalParams();
		params.setSeparator(separator);
		params.setQuote(quote);
		params.setEscape(escape);
		params.setIgnoreEmptyLines(ignoreEmptyLines);
		params.setEncoding(encoding);
		return params;
	}
	
	/**
	 * @param csvDataList CSVデータ
	 * @return UTF-8で出力されるOrangeSignal処理情報
	 */
	public static OrangeSignalParams forUTF8(List<String[]> csvDataList) {
		return newBuilder().setEncoding("UTF-8").build(csvDataList);
	}
	
	/**
	 * @param csvDataList CSVデータ
	 * @return MS932で出力されるOrangeSignal処理情報
	 */
	public static OrangeSignalParams forMS932(List<String[]> csvDataList) {
		return newBuilder().setEncoding("MS932").build(csvDataList);
	}
	
}
