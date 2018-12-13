/**
 *
 */
package jp.mosp.jasperreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JasperPrintで出力する中間パラメータクラス。
 */
public class JasperReportIntermediate implements Serializable {
	
	private static final long							serialVersionUID	= -3719638470694011714L;
	
	private final String								filePrefix;
	
	private final String								templatePath;
	
	private final List<? extends ReportDtoInterface>	list;
	
	
	/**
	 * コンストラクタ。
	 * @param filePrefix .pdfの前の文字列。
	 * @param templatePath 基底ディレクトリ配下のテンプレートファイルパス
	 * @param list 出力対象リスト
	 */
	public JasperReportIntermediate(String filePrefix, String templatePath, List<? extends ReportDtoInterface> list) {
		this.filePrefix = filePrefix;
		this.templatePath = templatePath;
		this.list = list == null ? new ArrayList<ReportDtoInterface>() : list;
	}
	
	/**
	 * @return filePrefix .pdfの前の文字列。
	 */
	public String getFilePrefix() {
		return filePrefix;
	}
	
	/**
	 * @return templatePath 基底ディレクトリ配下のテンプレートファイルパス
	 */
	public String getTemplatePath() {
		return templatePath;
	}
	
	/**
	 * @return list 出力対象リスト
	 */
	public List<? extends ReportDtoInterface> getList() {
		return list;
	}
	
}
