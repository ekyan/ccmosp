/**
 * 
 */
package jp.mosp.platform.bean.exporter.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.jasperreport.JasperReportIntermediate;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.exporter.JasperReportCompilerBeanInterface;
import jp.mosp.platform.bean.exporter.ReportManagerBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;

/**
 * 帳票出力処理管理クラス。
 */
public abstract class ReportManagerBean extends PlatformBean implements ReportManagerBeanInterface {
	
	@Override
	public void delivery(JasperReportCompilerBeanInterface compiler) throws MospException {
		delivery(compiler, PlatformMessageConst.MSG_NO_DATA);
	}
	
	@Override
	public void delivery(JasperReportCompilerBeanInterface compiler, String messageKey, String... replace)
			throws MospException {
		delivery(compiler.compile(), messageKey, replace);
	}
	
	@Override
	public void delivery(JasperReportIntermediate intermediate, String messageKey, String... replace) {
		if (intermediate == null) {
			// 検索結果無しメッセージ設定
			mospParams.addMessage(messageKey, replace);
			return;
		}
		if (mospParams.hasErrorMessage()) {
			// エラーメッセージが存在する場合、出力しない。
			return;
		}
		// ファイル名
		mospParams.setFileName(intermediate.getFilePrefix() + ".pdf");
		// 中間パラメータのセット
		mospParams.setFile(intermediate);
	}
	
}
