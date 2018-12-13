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
import jp.mosp.time.bean.ScheduleReferenceTotalBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeItemDto;

/**
 * 勤怠データ手当情報参照クラス。
 */
public class ScheduleReferenceTotalBean extends PlatformBean implements ScheduleReferenceTotalBeanInterface {
	
	/**
	 * カレンダ日マスタDAOクラス。<br>
	 */
	ScheduleDateDaoInterface	scheduleDateDao;
	
	/**
	 * 勤務形態項目管理DAOクラス。<br>
	 */
	WorkTypeItemDaoInterface	workTypeItemDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleReferenceTotalBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public ScheduleReferenceTotalBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// カレンダー日付マスタの取得
		scheduleDateDao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
		// 勤務形態マスタの取得
		workTypeItemDao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		
	}
	
	@Override
	public int getTimesLegalHolidayViews(String scheduleCode, Date startDate, Date endDate) throws MospException {
		
		List<ScheduleDateDtoInterface> scheduleDateDto = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		
		int timesLegalHolidayViews = 0;
		
		for (int i = 0; i < scheduleDateDto.size(); i++) {
			// 勤務形態コードが法定の場合
			if (scheduleDateDto.get(i).getWorkTypeCode().equals("legal_holiday")) {
				timesLegalHolidayViews++;
			}
		}
		return timesLegalHolidayViews;
	}
	
	@Override
	public int getTimesSpecificHolidayViews(String scheduleCode, Date startDate, Date endDate) throws MospException {
		
		List<ScheduleDateDtoInterface> scheduleDateDto = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		
		int timesSpecificHolidayViews = 0;
		
		for (int i = 0; i < scheduleDateDto.size(); i++) {
			// 勤務形態コードが所定の場合
			if (scheduleDateDto.get(i).getWorkTypeCode().equals("prescribed_holiday")) {
				timesSpecificHolidayViews++;
			}
		}
		return timesSpecificHolidayViews;
	}
	
	@Override
	public int getTimesWorkViews(String scheduleCode, Date startDate, Date endDate) throws MospException {
		
		List<ScheduleDateDtoInterface> scheduleDateDto = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		
		int timesWorkViews = 0;
		
		for (int i = 0; i < scheduleDateDto.size(); i++) {
			// 勤務形態コードが所定,法定では無い場合
			if (!scheduleDateDto.get(i).getWorkTypeCode().equals("legal_holiday")
					&& !scheduleDateDto.get(i).getWorkTypeCode().equals("prescribed_holiday")) {
				timesWorkViews++;
			}
		}
		return timesWorkViews;
	}
	
	@Override
	public int getTotalRestTime(String scheduleCode, Date startDate, Date endDate) throws MospException {
		
		List<ScheduleDateDtoInterface> scheduleDateDto = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		
		WorkTypeItemDtoInterface workTypeItemDto = new TmmWorkTypeItemDto();
		
		int totalRestTime = 0;
		
		for (int i = 0; i < scheduleDateDto.size(); i++) {
			// 対象の休憩時間を検索
			workTypeItemDto = workTypeItemDao.findForKey(scheduleDateDto.get(i).getWorkTypeCode(),
					scheduleDateDto.get(i).getActivateDate(), TimeConst.CODE_RESTTIME);
			
			if (workTypeItemDto.getWorkTypeItemValue() != null) {
				totalRestTime = totalRestTime + (DateUtility.getHour(workTypeItemDto.getWorkTypeItemValue()) * 60)
						+ DateUtility.getMinute(workTypeItemDto.getWorkTypeItemValue());
			}
		}
		return totalRestTime;
	}
	
	@Override
	public int getTotalWorkTime(String scheduleCode, Date startDate, Date endDate) throws MospException {
		
		List<ScheduleDateDtoInterface> scheduleDateDto = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		
		WorkTypeItemDtoInterface workTypeItemDto = new TmmWorkTypeItemDto();
		
		int totalRestTime = 0;
		
		for (int i = 0; i < scheduleDateDto.size(); i++) {
			// 対象の勤務時間を検索
			workTypeItemDto = workTypeItemDao.findForKey(scheduleDateDto.get(i).getWorkTypeCode(),
					scheduleDateDto.get(i).getActivateDate(), TimeConst.CODE_WORKTIME);
			
			if (workTypeItemDto.getWorkTypeItemValue() != null) {
				totalRestTime = totalRestTime + (DateUtility.getHour(workTypeItemDto.getWorkTypeItemValue()) * 60)
						+ DateUtility.getMinute(workTypeItemDto.getWorkTypeItemValue());
			}
		}
		return totalRestTime;
	}
	
}
