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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠データDAOインターフェース
 */
public interface AttendanceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと勤務日と勤務回数から勤怠情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @return 勤怠データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceDtoInterface findForKey(String personalId, Date workDate, int timesWork) throws MospException;
	
	/**
	 * ワークフロー番号から勤怠情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 勤怠データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 個人IDと開始年月日と終了年月日から勤怠情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param startDate 開始年月日
	 * @param endDate 終了年月日
	 * @return 勤怠情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceDtoInterface> findForList(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDと勤務日と勤務回数から勤怠情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @return 勤怠情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceDtoInterface> findForHistory(String personalId, Date workDate, int timesWork) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 勤怠情報検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 承認段階、承認状況から勤怠情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workflowStage 承認段階
	 * @param workflowStatus 承認状況
	 * @param routeCode ルートコード
	 * @return 勤怠情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceDtoInterface> findForWorkflowStatus(String personalId, int workflowStage, String workflowStatus,
			String routeCode) throws MospException;
	
}
