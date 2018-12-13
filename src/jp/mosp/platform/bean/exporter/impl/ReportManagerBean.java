/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.platform.bean.exporter.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.jasperreport.JasperReportIntermediate;
import jp.mosp.jasperreport.JasperReportUtility;
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
	public void delivery(JasperReportIntermediate intermediate, String messageKey, String... replace)
			throws MospException {
		if (intermediate == null) {
			// 検索結果無しメッセージ設定
			mospParams.addMessage(messageKey, replace);
			return;
		}
		if (mospParams.hasErrorMessage()) {
			// エラーメッセージが存在する場合、出力しない。
			return;
		}
		// 帳票を作成し送出ファイルとして設定
		mospParams.setFile(JasperReportUtility.createJasperPrint(getTemplatePath(intermediate.getAppReport()),
				intermediate.getList()));
		// 送出ファイル名設定
		mospParams.setFileName(intermediate.getAppReport());
	}
	
	/**
	 * 帳票のテンプレートパスを取得する。<br>
	 * @param appReport MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @return 帳票のテンプレートパス
	 */
	protected String getTemplatePath(String appReport) {
		return mospParams.getApplicationProperty(MospConst.APP_DOCBASE) + mospParams.getApplicationProperty(appReport);
	}
	
}
