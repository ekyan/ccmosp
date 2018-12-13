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
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TimelyPaidHolidayTransactionDtoInterface;

/**
 * 時間単位有給休暇トランザクションDAOインターフェース
 */
public interface TimelyPaidHolidayTransactionDaoInterface {
	
	/**
	 * 時間単位有給休暇トランザクション取得。
	 * <p>
	 * 社員コードと有効日と取得日から時間単位有給休暇トランザクションを取得する。
	 * </p>
	 * @param employeeCode 社員コード
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 時間単位有給休暇トランザクションDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TimelyPaidHolidayTransactionDtoInterface findForInfo(String employeeCode, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から時間単位有給休暇トランザクションリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 時間単位有給休暇トランザクションリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimelyPaidHolidayTransactionDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 時間単位有給休暇トランザクション検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
