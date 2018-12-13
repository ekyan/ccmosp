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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠データ参照インターフェース。
 */
public interface AttendanceReferenceBeanInterface {
	
	/**
	 * 勤怠データリスト取得。
	 * <p>
	 * 個人IDと開始年月日と終了年月日から勤怠データリストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param startDate 開始年月日
	 * @param endDate 終了年月日
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceDtoInterface> getAttendanceList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 勤怠データからレコードを取得する。<br>
	 * 個人ID、勤務日、勤務回数で合致するレコードが無い場合、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @return 勤怠詳細データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceDtoInterface findForKey(String personalId, Date workDate, int timesWork) throws MospException;
	
	/**
	 * ワークフロー番号からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 勤怠詳細データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * @param id レコード識別ID
	 * @return 勤怠詳細データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceDtoInterface findForId(long id) throws MospException;
	
	/**
	 * 承認段階、承認状況から勤怠一覧を取得する。<br>
	 * @param personalId 個人ID
	 * @param workflowStage 承認段階
	 * @param workflowStatus 承認状況
	 * @param routeCode ルートコード
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceDtoInterface> getListForWorkflowStatus(String personalId, int workflowStage, String workflowStatus,
			String routeCode) throws MospException;
	
	/**
	 * 未承認勤怠データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param approvalStage 承認段階
	 * @return 未承認勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceDtoInterface> getListForNotApproved(String personalId, int approvalStage) throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
}
