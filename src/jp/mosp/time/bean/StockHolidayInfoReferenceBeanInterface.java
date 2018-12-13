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
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇情報参照インターフェース。
 */
public interface StockHolidayInfoReferenceBeanInterface {
	
	/**
	 * ストック休暇データ集計リストを取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return ストック休暇データ集計リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayCalcInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 申請用ストック休暇申請可能数を取得する。<br><br>
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は使用日数に加算する。
	 * 但し、1次戻は使用日数に加算しない。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return ストック休暇申請可能数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayPossibleRequestForRequest(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * ストック休暇申請可能数を取得する。<br><br>
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は使用日数に加算する。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return ストック休暇申請可能数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayPossibleRequest(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 前年度残、今年度付与日数、対象期間利用(休暇申請)日数を取得する。<br>
	 * 統計情報一覧で使用する。<br>
	 * @param humanDto    人事基本情報
	 * @param displayYear 表示年度
	 * @param targetDate  対象日
	 * @return 前年度残、 今年度付与日数、対象期間利用(休暇申請)日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getSubordinateFiscalStockHolidayInfo(HumanDtoInterface humanDto, int displayYear,
			Date targetDate) throws MospException;
	
	/**
	 * ストック休暇残日数を取得する。<br><br>
	 * ストック休暇が付与されていない場合はnullを返す。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return ストック休暇残日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Double getRemainDay(String personalId, Date targetDate) throws MospException;
	
	/**
	 * ストック休暇残日数を取得する。<br><br>
	 * @param personalId 個人ID
	 * @param startDate 対象年月日また開始年月日
	 * @param endDate 終了年月日
	 * @return ストック休暇残日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	double getRemainDay(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * ストック休暇データ次月リストを取得する。<br><br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param list ストック休暇データリスト
	 * @return ストック休暇データ次月リスト
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayNextMonthInfo(String personalId, Date activateDate,
			List<StockHolidayDataDtoInterface> list);
	
	/**
	 * ストック休暇新規データを取得する。<br><br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param day 付与日数
	 * @return ストック休暇新規データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayDataDtoInterface getNewStockHolidayInfo(String personalId, Date activateDate, double day)
			throws MospException;
	
	/**
	 * 対象期間内のストック休暇残日数を取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate 対象期間開始日
	 * @param endDate 対象期間終了日
	 * @return ストック休暇残日数+今年度付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	double getStockPaidInfo(String personalId, Date startDate, Date endDate) throws MospException;
	
}
