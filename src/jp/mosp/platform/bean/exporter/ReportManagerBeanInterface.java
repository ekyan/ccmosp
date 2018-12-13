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
