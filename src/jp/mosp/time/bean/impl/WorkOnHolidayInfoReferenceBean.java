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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayInfoReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 休日出勤情報参照クラス。
 */
public class WorkOnHolidayInfoReferenceBean extends PlatformBean implements WorkOnHolidayInfoReferenceBeanInterface {
	
	/**
	 * 設定適用管理参照クラス。
	 */
	protected ApplicationReferenceBeanInterface	application;
	
	/**
	 * カレンダマスタDAO。
	 */
	protected ScheduleDaoInterface				scheduleDao;
	
	/**
	 * カレンダ日マスタDAO。
	 */
	protected ScheduleDateDaoInterface			scheduleDateDao;
	
	/**
	 * 休日出勤申請DAO。
	 */
	protected WorkOnHolidayRequestDaoInterface	workOnHolidayRequestDao;
	
	/**
	 * ワークフローDAO。
	 */
	protected WorkflowDaoInterface				workflowDao;
	
	/**
	 * 振替休日データDAO。
	 */
	protected SubstituteDaoInterface			substituteDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkOnHolidayInfoReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public WorkOnHolidayInfoReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		application = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleDao = (ScheduleDaoInterface)createDao(ScheduleDaoInterface.class);
		scheduleDateDao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
	}
	
	@Override
	public String[][] getSelectArray(String personalId, Date startDate, Date endDate) throws MospException {
		List<String[]> list = new ArrayList<String[]>();
		Map<Date, ApplicationDtoInterface> map = application.findForTerm(personalId, startDate, endDate);
		Date targetDate = startDate;
		while (!targetDate.after(endDate)) {
			// DateUtility.getStringDay は1桁の場合に頭に0が入るので不可
			String day = String.valueOf(DateUtility.getDay(targetDate));
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao.findForKeyOnWorkflow(
					personalId, targetDate);
			if (workOnHolidayRequestDto != null) {
				// 休日出勤申請されている場合
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(workOnHolidayRequestDto.getWorkflow());
				if (workflowDto != null) {
					if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
						// 休日出勤申請が承認されている場合
						targetDate = DateUtility.addDay(targetDate, 1);
						continue;
					}
				}
			}
			boolean isSubstituteDate = false;
			List<SubstituteDtoInterface> substituteList = substituteDao.findForList(personalId, targetDate);
			for (SubstituteDtoInterface substituteDto : substituteList) {
				// ワークフローの取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
					// 振替休日の場合
					isSubstituteDate = true;
					break;
				}
			}
			if (isSubstituteDate) {
				// 振替休日の場合はリストに追加
				list.add(new String[]{ day, day });
				targetDate = DateUtility.addDay(targetDate, 1);
				continue;
			}
			ApplicationDtoInterface applicationDto = map.get(targetDate);
			if (applicationDto == null) {
				targetDate = DateUtility.addDay(targetDate, 1);
				continue;
			}
			ScheduleDtoInterface scheduleDto = scheduleDao.findForInfo(applicationDto.getScheduleCode(), targetDate);
			if (scheduleDto == null) {
				targetDate = DateUtility.addDay(targetDate, 1);
				continue;
			}
			ScheduleDateDtoInterface scheduleDateDto = scheduleDateDao.findForInfo(scheduleDto.getScheduleCode(),
					scheduleDto.getActivateDate(), targetDate);
			if (scheduleDateDto != null) {
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
					// 法定休日又は所定休日の場合はリストに追加
					list.add(new String[]{ day, day });
				}
			}
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		return list.toArray(new String[0][0]);
	}
	
}
