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
package jp.mosp.platform.bean.workflow.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフロー参照クラス。
 */
public class WorkflowReferenceBean extends PlatformBean implements WorkflowReferenceBeanInterface {
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	private WorkflowDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkflowReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public WorkflowDtoInterface getLatestWorkflowInfo(long workflow) throws MospException {
		return dao.findForKey(workflow);
	}
	
	@Override
	public List<WorkflowDtoInterface> getListForApproverId(String functionCode, String approverId)
			throws MospException {
		return dao.findForApproverId(functionCode, approverId);
	}
	
	@Override
	public List<WorkflowDtoInterface> getListForRoute(int workflowStage, String workflowStatus, String routeCode,
			String functionCode) throws MospException {
		return dao.findForRoute(workflowStage, workflowStatus, routeCode, functionCode);
	}
	
	@Override
	public List<WorkflowDtoInterface> getListForRoute(String workflowStatus, String routeCode, String functionCode)
			throws MospException {
		return dao.findForRoute(workflowStatus, routeCode, functionCode);
	}
	
	@Override
	public WorkflowDtoInterface findForId(long id) throws MospException {
		BaseDtoInterface baseDto = findForKey(dao, id, false);
		if (baseDto != null) {
			return (WorkflowDtoInterface)baseDto;
		}
		return null;
	}
	
	@Override
	public List<WorkflowDtoInterface> getApprovableList(Set<String> functionCodeSet) throws MospException {
		return dao.findApprovable(functionCodeSet);
	}
	
	@Override
	public List<WorkflowDtoInterface> getCancelableList(Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(null, null, functionCodeSet, getCancelAppliedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getEffectiveList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getEffectiveSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCompletedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getCompletedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(personalId, fromDate, toDate, functionCodeSet, getCompletedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getNonApprovedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getNonApprovedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getRevertedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getRevertedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCancelAppliedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getCancelAppliedSet());
	}
	
	/**
	 * 有効ワークフロー状況セットを取得する。<br>
	 * @return 有効ワークフロー状況セット
	 */
	protected Set<String> getEffectiveSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_APPLY);
		set.add(PlatformConst.CODE_STATUS_APPROVED);
		set.add(PlatformConst.CODE_STATUS_REVERT);
		set.add(PlatformConst.CODE_STATUS_CANCEL);
		set.add(PlatformConst.CODE_STATUS_COMPLETE);
		set.add(PlatformConst.CODE_STATUS_CANCEL_APPLY);
		set.add(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
		return set;
	}
	
	/**
	 * 差戻ワークフロー状況セットを取得する。<br>
	 * @return 差戻ワークフロー状況セット
	 */
	protected Set<String> getRevertedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_REVERT);
		return set;
	}
	
	/**
	 * 承認済ワークフロー状況セットを取得する。<br>
	 * @return 承認済ワークフロー状況セット
	 */
	protected Set<String> getCompletedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_COMPLETE);
		return set;
	}
	
	/**
	 * 有効ワークフロー状況セットを取得する。<br>
	 * @return 有効ワークフロー状況セット
	 */
	protected Set<String> getNonApprovedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_APPLY);
		set.add(PlatformConst.CODE_STATUS_APPROVED);
		return set;
	}
	
	/**
	 * 解除申ワークフロー状況セットを取得する。<br>
	 * @return 解除申ワークフロー状況セット
	 */
	protected Set<String> getCancelAppliedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_CANCEL_APPLY);
		set.add(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
		return set;
	}
	
	@Override
	public List<WorkflowDtoInterface> getPersonalList(String personalId, Date startDate, Date endDate,
			Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(personalId, startDate, endDate, functionCodeSet);
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> findForPersonAndDay(String personalId, Date workflowDate,
			Set<String> functionCode) throws MospException {
		// マップ準備
		Map<Long, WorkflowDtoInterface> result = new HashMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報取得
		Map<Long, WorkflowDtoInterface> map = dao.findForPersonAndDay(personalId, workflowDate);
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : map.values()) {
			// 対象機能コードに含まれている場合
			if (functionCode.contains(dto.getFunctionCode())) {
				// マップに詰める
				result.put(dto.getWorkflow(), dto);
			}
		}
		return result;
	}
	
}
