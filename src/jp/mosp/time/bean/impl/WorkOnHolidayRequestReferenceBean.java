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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 休日出勤申請参照クラス。
 */
public class WorkOnHolidayRequestReferenceBean extends TimeBean implements WorkOnHolidayRequestReferenceBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(半日振替出勤利用設定)。
	 */
	protected static final String		APP_USE_HALF_SUBSTITUTE	= "UseHalfSubstitute";
	
	/**
	 * 休日出勤申請DAOクラス。<br>
	 */
	WorkOnHolidayRequestDaoInterface	dao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	WorkflowDaoInterface				workflowDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkOnHolidayRequestReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkOnHolidayRequestReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (WorkOnHolidayRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKey(String personalId, Date requestDate, int timesWork)
			throws MospException {
		return dao.findForKey(personalId, requestDate, timesWork);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForSubstitute(String personalId, Date requestDate, int timesWork)
			throws MospException {
		// 勤務日で休日出勤申請情報取得
		List<WorkOnHolidayRequestDtoInterface> list = dao.findForSubstitute(personalId, requestDate, timesWork);
		// 取下されていない申請を取得
		for (WorkOnHolidayRequestDtoInterface dto : list) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
			// ワークフロー情報確認
			if (workflowDto != null
					&& workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN) == false) {
				// 取下でない場合
				return dto;
			}
		}
		return null;
	}
	
	@Override
	public boolean useHalfSubstitute() {
		// 半日振替出勤利用設定取得
		return mospParams.getApplicationPropertyBool(APP_USE_HALF_SUBSTITUTE);
	}
}
