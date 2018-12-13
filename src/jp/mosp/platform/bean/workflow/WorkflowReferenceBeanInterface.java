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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフロー参照インターフェース。
 */
public interface WorkflowReferenceBeanInterface {
	
	/**
	 * 最新のワークフロー取得。
	 * <p>
	 * ワークフロー番号から最新のワークフローを取得。
	 * </p>
	 * @param workflow ワークフロー番号
	 * @return ワークフローデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface getLatestWorkflowInfo(long workflow) throws MospException;
	
	/**
	 * 機能コードと承認者個人IDからワークフロー情報一覧を取得する。<br>
	 * @param functionCode 機能コード
	 * @param approverId 承認者個人ID
	 * @return ワークフローリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getListForApproverId(String functionCode, String approverId) throws MospException;
	
	/**
	 * 段階、状況、ルートコード、機能コードからワークフロー情報一覧を取得する。<br>
	 * @param workflowStage 段階
	 * @param workflowStatus 状況
	 * @param routeCode ルートコード
	 * @param functionCode 機能コード
	 * @return ワークフローリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getListForRoute(int workflowStage, String workflowStatus, String routeCode,
			String functionCode) throws MospException;
	
	/**
	 * 状況、ルートコード、機能コードからワークフロー情報一覧を取得する。<br>
	 * @param workflowStatus 状況
	 * @param routeCode ルートコード
	 * @param functionCode 機能コード
	 * @return ワークフローリスト
	 * @throws MospException Sインスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getListForRoute(String workflowStatus, String routeCode, String functionCode)
			throws MospException;
	
	/**
	 * ワークフローデータを取得する。
	 * @param id レコード識別ID
	 * @return ワークフローデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface findForId(long id) throws MospException;
	
	/**
	 * 承認可能ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * @param functionCodeSet 機能コードセット
	 * @return 承認可能ワークフロー情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getApprovableList(Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 解除承認可能ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * @param functionCodeSet 機能コードセット
	 * @return 解除承認可能ワークフロー情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getCancelableList(Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 対象期間における有効ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の何れかに当てはまるワークフローが、有効と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が差戻
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li><li>
	 * ワークフロー状況が承認済
	 * </li></ul>
	 * @param fromDate        対象期間自
	 * @param toDate          対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getEffectiveList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 対象期間における差戻ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の条件に当てはまるワークフローが、差戻と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が差戻
	 * </li></ul>
	 * @param fromDate        対象期間自
	 * @param toDate          対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getRevertedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 対象期間における承認済ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の条件に当てはまるワークフローが、承認済と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が承認済
	 * </li></ul>
	 * @param fromDate        対象期間自
	 * @param toDate          対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 承認済ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getCompletedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 対象期間における承認済ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の条件に当てはまるワークフローが、承認済と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が承認済
	 * </li></ul>
	 * @param personalId 個人ID
	 * @param fromDate 対象期間自
	 * @param toDate 対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 承認済ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 対象期間における未承認ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の何れかに当てはまるワークフローが、未承認と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li></ul>
	 * @param fromDate        対象期間自
	 * @param toDate          対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 未承認ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getNonApprovedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 対象期間における解除申ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の何れかに当てはまるワークフローが、解除申と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が解除申
	 * </li></ul>
	 * @param fromDate 対象期間自
	 * @param toDate 対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 未承認ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getCancelAppliedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 個人IDと期間でワークフロー情報を取得する。
	 * @param personalId 個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param functionCodeSet 機能コードセット
	 * @return ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> getPersonalList(String personalId, Date startDate, Date endDate,
			Set<String> functionCodeSet) throws MospException;
	
}
