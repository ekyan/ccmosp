/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;
import jp.mosp.jasperreport.JasperReportIntermediate;

/**
 * PDF帳票編集処理インターフェース。
 */
public interface JasperReportCompilerBeanInterface {
	
	/**
	 * 編集処理
	 * @return 帳票出力用中間パラメータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public JasperReportIntermediate compile() throws MospException;
	
}
