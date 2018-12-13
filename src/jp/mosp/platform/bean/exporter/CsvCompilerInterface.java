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
import jp.mosp.platform.dto.exporter.CsvExportIntermediate;

/**
 * CSVエクスポート用編集処理インターフェース。
 */
public interface CsvCompilerInterface {
	
	/**
	 * @return CSVデータ
	 * @throws MospException 例外処理発生時
	 */
	CsvExportIntermediate compile() throws MospException;
	
}
