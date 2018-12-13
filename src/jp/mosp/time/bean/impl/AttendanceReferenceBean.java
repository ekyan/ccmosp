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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠データ参照クラス。
 */
public class AttendanceReferenceBean extends TimeBean implements AttendanceReferenceBeanInterface {
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	AttendanceDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendanceReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public AttendanceReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
	}
	
	@Override
	public List<AttendanceDtoInterface> getAttendanceList(String personalId, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(personalId, startDate, endDate);
	}
	
	@Override
	public AttendanceDtoInterface findForKey(String personalId, Date workDate, int timesWork) throws MospException {
		return dao.findForKey(personalId, workDate, timesWork);
	}
	
	@Override
	public AttendanceDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public AttendanceDtoInterface findForId(long id) throws MospException {
		BaseDtoInterface baseDto = dao.findForKey(id, false);
		if (baseDto != null && baseDto.getDeleteFlag() == MospConst.DELETE_FLAG_OFF) {
			return (AttendanceDtoInterface)baseDto;
		}
		return null;
	}
	
	@Override
	public List<AttendanceDtoInterface> getListForWorkflowStatus(String personalId, int workflowStage,
			String workflowStatus, String routeCode) throws MospException {
		return dao.findForWorkflowStatus(personalId, workflowStage, workflowStatus, routeCode);
	}
	
	@Override
	public List<AttendanceDtoInterface> getListForNotApproved(String personalId, int approvalStage)
			throws MospException {
		List<AttendanceDtoInterface> list = new ArrayList<AttendanceDtoInterface>();
		if (approvalStage > PlatformConst.WORKFLOW_STAGE_ZERO) {
			if (approvalStage == PlatformConst.WORKFLOW_STAGE_FIRST) {
				// 未承認
				list.addAll(getListForWorkflowStatus(personalId, approvalStage, PlatformConst.CODE_STATUS_APPLY, ""));
			} else {
				// (stage - 1)次承認済
				list.addAll(getListForWorkflowStatus(personalId, approvalStage - 1, PlatformConst.CODE_STATUS_APPROVED,
						""));
			}
			// (stage + 1)次差戻
			list.addAll(getListForWorkflowStatus(personalId, approvalStage + 1, PlatformConst.CODE_STATUS_REVERT, ""));
		}
		return list;
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		initial(personalId, targetDate);
	}
}
