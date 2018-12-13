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
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇データ参照インターフェース。
 */
public interface PaidHolidayDataReferenceBeanInterface {
	
	/**
	 * 有給休暇データ取得。
	 * @param id レコード識別ID
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 有給休暇データ取得。
	 * <p>
	 * 個人IDと対象年月日と取得日から有給休暇データを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface getPaidHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 有給休暇データリスト取得。
	 * <p>
	 * 個人ID、対象年月日から有給休暇データリストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayDataInfoList(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 有給休暇データリストを取得する。<br>
	 * 個人IDと対象日から全ての有給休暇データリストを取得する。<br>
	 * <br>
	 * 但し、期限日が対象日より前の情報は、取得対象外とする。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayDataInfoAllList(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 有給休暇データからレコードを取得する。<br>
	 * 個人ID、有効日、取得日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 有給休暇データリスト取得。<br>
	 * 次年度以降の有給休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データ取得。
	 * @param personalId 個人ID
	 * @param expirationDate 期限日
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface findForExpirationDateInfo(String personalId, Date expirationDate) throws MospException;
	
	/**
	 * 有給休暇データリスト取得。
	 * @param personalId 個人ID
	 * @param expirationDate 期限日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> findForExpirationDateList(String personalId, Date expirationDate)
			throws MospException;
	
	/**
	 * 有給休暇データリスト取得。
	 * 期間開始日～期間終了日に付与された有給休暇情報リストを取得する。
	 * @param personalId 個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> findForAcquisitionList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
}
