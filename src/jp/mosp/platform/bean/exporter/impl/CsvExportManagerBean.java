/**
 *
 */
package jp.mosp.platform.bean.exporter.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.exporter.CsvCompilerInterface;
import jp.mosp.platform.bean.exporter.CsvExportManagerBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.exporter.CsvExportIntermediate;

/**
 * CSV出力処理管理クラス。
 */
public abstract class CsvExportManagerBean extends PlatformBean implements CsvExportManagerBeanInterface {
	
	@Override
	public void delivery(CsvCompilerInterface compiler) throws MospException {
		CsvExportIntermediate intermediate = compiler.compile();
		if (intermediate == null) {
			// 検索結果無しメッセージ設定
			mospParams.addMessage(PlatformMessageConst.MSG_NO_DATA);
			return;
		}
		if (mospParams.hasErrorMessage()) {
			// エラーメッセージが存在する場合、出力しない。
			return;
		}
		// ファイル名
		mospParams.setFileName(intermediate.getFilePrefix() + ".csv");
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(intermediate.getOrangeSignalParams());
	}
	
}
