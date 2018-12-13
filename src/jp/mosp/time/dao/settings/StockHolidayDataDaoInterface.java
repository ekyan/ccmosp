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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇データDAOインターフェース
 */
public interface StockHolidayDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と取得日からストック休暇情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇データ取得。
	 * <p>
	 * 個人IDと有効日と取得日からストック休暇データを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayDataDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 個人IDと有効日からストック休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return ストック休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<StockHolidayDataDtoInterface> findForList(String personalId, Date activateDate) throws MospException;
	
	/**
	 * ストック休暇データ取得。
	 * <p>
	 * 個人IDと有効日からストック休暇データリストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return ストック休暇データDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<StockHolidayDataDtoInterface> findForInfoList(String personalId, Date activateDate) throws MospException;
	
}
