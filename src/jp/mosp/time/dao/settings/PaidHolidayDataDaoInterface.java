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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇データDAOインターフェース
 */
public interface PaidHolidayDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と取得日から有給休暇データ情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 有効日から有給休暇データリストを取得する。<br>
	 * @param targetDate 対象日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForList(Date targetDate) throws MospException;
	
	/**
	 * 個人IDと有効日から有給休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForList(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForHistory(String personalId, Date acquisitionDate) throws MospException;
	
	/**
	 * 有給休暇データ取得。
	 * <p>
	 * 個人IDと有効日と取得日から有給休暇データを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDataDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 有給休暇データリスト取得。<br>
	 * 個人IDと対象日から有給休暇データリストを取得する。<br>
	 * 付与日が対象日以前であり、期限日が対象日以降である、
	 * 有給休暇データを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForInfoList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データリストを取得する。<br>
	 * 個人IDと対象日から全ての有給休暇データリストを取得する。<br>
	 * <br>
	 * 但し、期限日が対象日より前の情報は、取得対象外とする。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForInfoAllList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データリスト取得。<br>
	 * 次年度以降の有給休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データ取得。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDataDtoInterface findForExpirationDateInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データリスト取得。
	 * @param personalId 個人ID
	 * @param expirationDate 期限日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForExpirationDateList(String personalId, Date expirationDate)
			throws MospException;
	
	/**
	 * 個人IDと期間から期限が切れる有給休暇情報リストを取得する。<br>
	 * 対象期間内に期限日がある有給休暇情報リストを取得する。
	 * @param personalId 個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 期限が切れる有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public List<PaidHolidayDataDtoInterface> getExpiredList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 個人IDと期間から有給休暇情報リストを取得する。<br>
	 * 付与日が対象期間内の有給休暇情報リストを取得する。
	 * @param personalId 個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 期間内に付与された有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public List<PaidHolidayDataDtoInterface> findForAcquisitionList(String personalId, Date startDate, Date endDate)
			throws MospException;
}
