/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;
import jp.mosp.jasperreport.JasperReportIntermediate;

/**
 * 帳票出力処理管理インターフェース。
 */
public interface ReportManagerBeanInterface {
	
	/**
	 * PDF帳票出力処理
	 * @param compiler 編集処理オブジェクト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void delivery(JasperReportCompilerBeanInterface compiler) throws MospException;
	
	/**
	 * PDF帳票出力処理
	 * @param compiler 編集処理オブジェクト
	 * @param messageKey メッセージキー
	 * @param replace 置換文字列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void delivery(JasperReportCompilerBeanInterface compiler, String messageKey, String... replace)
			throws MospException;
	
	/**
	 * PDF帳票出力処理
	 * @param intermediate 中間パラメータ
	 * @param messageKey メッセージキー
	 * @param replace 置換文字列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void delivery(JasperReportIntermediate intermediate, String messageKey, String... replace)
			throws MospException;
	
}
