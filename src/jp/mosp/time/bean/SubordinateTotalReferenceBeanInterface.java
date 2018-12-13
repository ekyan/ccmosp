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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Map;

import jp.mosp.framework.base.MospException;

/**
 * 部下データ合計参照インターフェース。
 */
public interface SubordinateTotalReferenceBeanInterface {
	
	/**
	 * 部下データ合計を取得する。<br><br>
	 * @param personalIdArray 個人ID配列
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 部下データ合計Map
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getSubordinateTotalInfo(String[] personalIdArray, int calculationYear, int calculationMonth)
			throws MospException;
	
}
