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
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 休日出勤申請DAOインターフェース
 */
public interface WorkOnHolidayRequestDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと出勤日及び出勤回数から休日出勤申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId  個人ID
	 * @param requestDate 出勤日
	 * @param timesWork   出勤回数
	 * @return 休日出勤申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkOnHolidayRequestDtoInterface findForKey(String personalId, Date requestDate, int timesWork)
			throws MospException;
	
	/**
	 * ワークフロー番号から勤怠情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 休日出勤申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkOnHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDから休日出勤申請リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 休日出勤申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForHistory(String personalId) throws MospException;
	
	/**
	 * 休日出勤申請取得。
	 * <p>
	 * 個人IDと有効日から休日出勤申請を取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 休日出勤申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkOnHolidayRequestDtoInterface findForInfo(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 休日出勤申請取得。
	 * <p>
	 * 個人IDと有効日から休日出勤申請を取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 休日出勤申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 個人IDと対象期間から休日出勤申請情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休日出勤申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から休日出勤申請リストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 休日出勤申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 休日出勤申請検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 個人IDと申請日から休日出勤申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休日出勤申請情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkOnHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 承認段階、承認状況から休日出勤申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workflowStage 承認段階
	 * @param workflowStatus 承認状況
	 * @param routeCode ルートコード
	 * @return 休日出勤申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForWorkflowStatus(String personalId, int workflowStage,
			String workflowStatus, String routeCode) throws MospException;
	
	/**
	 * 個人ID、出勤日、勤務回数から休日出勤申請リストを取得する。<br>
	 * 振替休日データから、休日出勤申請を取得する際に用いる。<br>
	 * @param personalId 個人ID
	 * @param requestDate 出勤日
	 * @param timesWork 勤務回数
	 * @return 休日出勤申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> findForSubstitute(String personalId, Date requestDate, int timesWork)
			throws MospException;
	
}
