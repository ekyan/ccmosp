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
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;

/**
 * 代休申請参照クラス。
 */
public class SubHolidayRequestReferenceBean extends TimeBean implements SubHolidayRequestReferenceBeanInterface {
	
	/**
	 * 代休申請DAOクラス。<br>
	 */
	SubHolidayRequestDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayRequestReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public SubHolidayRequestReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date requestDate)
			throws MospException {
		return dao.findForList(personalId, requestDate);
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int holidayRange)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate, holidayRange);
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (SubHolidayRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date workDate,
			int timesWork, int workDateSubHolidayType) throws MospException {
		return dao.findForList(personalId, workDate, timesWork, workDateSubHolidayType);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		initial(personalId, targetDate);
	}
	
}
