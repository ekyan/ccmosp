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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日参照クラス。
 */
public class ScheduleDateReferenceBean extends PlatformBean implements ScheduleDateReferenceBeanInterface {
	
	/**
	 * カレンダ日マスタDAO。
	 */
	private ScheduleDateDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleDateReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public ScheduleDateReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDateInfo(String scheduleCode, Date targetDate, Date scheduleDate)
			throws MospException {
		return dao.findForInfo(scheduleCode, targetDate, scheduleDate);
	}
	
	@Override
	public ScheduleDateDtoInterface findForKey(String scheduleCode, Date activateDate, Date scheduleDate)
			throws MospException {
		return dao.findForKey(scheduleCode, activateDate, scheduleDate);
	}
	
	@Override
	public ScheduleDateDtoInterface findForKey(String scheduleCode, Date scheduleDate) throws MospException {
		return dao.findForKey(scheduleCode, scheduleDate);
	}
	
	@Override
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate) throws MospException {
		return dao.findForList(scheduleCode, activateDate);
	}
	
	@Override
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate, Date startDate,
			Date endDate) throws MospException {
		return dao.findForList(scheduleCode, activateDate, startDate, endDate);
	}
	
	@Override
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(scheduleCode, startDate, endDate);
	}
	
	@Override
	public boolean isHoliday(String scheduleCode, Date targetDate) throws MospException {
		// カレンダーコードにおける対象日の情報取得
		ScheduleDateDtoInterface scheduleDateDto = dao.findForInfo(scheduleCode, targetDate, targetDate);
		// カレンダー日情報確認
		if (scheduleDateDto == null) {
			return false;
		}
		// 法定休日確認
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
			return true;
		}
		// 所定休日確認
		if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
			return true;
		}
		return false;
	}
	
	@Override
	public void chkExistScheduleDate(ScheduleDateDtoInterface dto, Date targetDate) {
		if (dto == null) {
			String errorMes1 = "";
			if (targetDate == null) {
				errorMes1 = DateUtility.getStringDate(DateUtility.getSystemDate());
			} else {
				errorMes1 = DateUtility.getStringDate(targetDate);
			}
			String errorMes2 = mospParams.getName("Calendar", "Set", "Information");
			mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, errorMes1, errorMes2);
		}
	}
	
}
