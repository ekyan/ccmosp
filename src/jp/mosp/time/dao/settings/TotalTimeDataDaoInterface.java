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

import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計データDAOインターフェース。<br>
 */
public interface TotalTimeDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと年と月から勤怠集計データを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 勤怠集計データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
	/**
	 * 個人ID配列と年と月から勤怠集計データリストを取得する。<br>
	 * @param personalIdArray 個人ID配列
	 * @param calculationYear 年
	 * @param calculationMonth 日
	 * @return 勤怠集計データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalTimeDataDtoInterface> findForList(String[] personalIdArray, int calculationYear, int calculationMonth)
			throws MospException;
	
}
