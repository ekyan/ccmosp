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
package jp.mosp.platform.file.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.constant.PlatformConst;

/**
 * ファイルのエクスポートを行う。<br>
 * <br>
 * リクエストパラメータで指定されたファイルパスでファイルを取得し、出力する。<br>
 * リクエストパラメータは{@link PlatformConst#PRM_TRANSFERRED_TYPE}を用いる。<br>
 * <br>
 * ファイルパスの例：<br>
 * /template/test.pdf<br>
 * <br>
 * ボタンの例：<br>
 * <button type="button" onclick="submitFile(event, null, 
 * new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '/template/test.pdf'),
 * '<%= FileExportAction.CMD_OUTPUT %>');">出力</button>
 * <br>
 */
public class FileExportAction extends PlatformAction {
	
	/**
	 * 出力コマンド。<br>
	 * <br>
	 */
	public static final String CMD_OUTPUT = "PF9120";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public FileExportAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// ファイルパス取得
		String path = mospParams.getApplicationProperty(MospConst.APP_DOCBASE) + getTransferredType();
		try {
			// ファイル取得
			File file = new File(path);
			// ファイル名取得
			String fileName = file.getName();
			// 入力ストリーム取得
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			// MosP処理情報に設定
			mospParams.setFile(in);
			mospParams.setFileName(fileName);
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
}
