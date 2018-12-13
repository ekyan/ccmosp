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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇データ参照インターフェース。
 */
public interface StockHolidayDataReferenceBeanInterface {
	
	/**
	 * ストック休暇データ取得。
	 * <p>
	 * 個人IDと対象年月日と取得日からストック休暇データを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayDataDtoInterface getStockHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇データからレコードを取得する。<br>
	 * 個人ID、有効日、取得日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇データリストを取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return ストック休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> findForInfoList(String personalId, Date targetDate) throws MospException;
	
}
