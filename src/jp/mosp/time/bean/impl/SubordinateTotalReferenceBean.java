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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.SubordinateTotalReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 部下データ合計参照クラス。
 */
public class SubordinateTotalReferenceBean extends PlatformBean implements SubordinateTotalReferenceBeanInterface {
	
	/**
	 * 勤怠集計データDAO。
	 */
	private TotalTimeDataDaoInterface	totalTimeDataDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubordinateTotalReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public SubordinateTotalReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 勤怠集計データDAO取得
		totalTimeDataDao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
	}
	
	@Override
	public Map<String, Object> getSubordinateTotalInfo(String[] personalIdArray, int calculationYear,
			int calculationMonth) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		int workTime = 0;
		int restTime = 0;
		int lateTime = 0;
		int leaveEarlyTime = 0;
		int overTimeIn = 0;
		int overTimeOut = 0;
		int workOnHolidayTime = 0;
		int lateNight = 0;
		int timesWork = 0;
		int timesLate = 0;
		int timesLeaveEarly = 0;
		int timesOvertime = 0;
		int workOnHoliday = 0;
		int timesLegalHoliday = 0;
		int timesSpecificHoliday = 0;
		double timesPaidHoliday = 0;
		int paidHolidayHour = 0;
		double totalSpecialHoliday = 0;
		double totalOtherHoliday = 0;
		double timesHolidaySubstitute = 0;
		double timesCompensation = 0;
		int totalAbsence = 0;
		List<TotalTimeDataDtoInterface> list = totalTimeDataDao.findForList(personalIdArray, calculationYear,
				calculationMonth);
		for (TotalTimeDataDtoInterface dto : list) {
			workTime += dto.getWorkTime();
			restTime += dto.getRestTime();
			lateTime += dto.getLateTime();
			leaveEarlyTime += dto.getLeaveEarlyTime();
			overTimeIn += dto.getOvertimeIn();
			overTimeOut += dto.getOvertimeOut();
			workOnHolidayTime += dto.getWorkOnSpecificHoliday();
			workOnHolidayTime += dto.getWorkOnHoliday();
			lateNight += dto.getLateNight();
			timesWork += dto.getTimesWork();
			timesLate += dto.getTimesLate();
			timesLeaveEarly += dto.getTimesLeaveEarly();
			timesOvertime += dto.getTimesOvertime();
			workOnHoliday += dto.getLegalWorkOnHoliday();
			workOnHoliday += dto.getSpecificWorkOnHoliday();
			timesLegalHoliday += dto.getTimesLegalHoliday();
			timesSpecificHoliday += dto.getTimesSpecificHoliday();
			timesPaidHoliday += dto.getTimesPaidHoliday();
			paidHolidayHour += dto.getPaidHolidayHour();
			totalSpecialHoliday += dto.getTotalSpecialHoliday();
			totalOtherHoliday += dto.getTotalOtherHoliday();
			timesHolidaySubstitute += dto.getTimesHolidaySubstitute();
			timesCompensation += dto.getTimesCompensation();
			totalAbsence += dto.getTotalAbsence();
		}
		map.put(TimeConst.CODE_TOTAL_WORK_TIME, workTime);
		map.put(TimeConst.CODE_TOTAL_REST_TIME, restTime);
		map.put(TimeConst.CODE_TOTAL_LATE_TIME, lateTime);
		map.put(TimeConst.CODE_TOTAL_LEAVE_EARLY, leaveEarlyTime);
		map.put(TimeConst.CODE_TOTAL_OVER_TIMEIN, overTimeIn);
		map.put(TimeConst.CODE_TOTAL_OVER_TIMEOUT, overTimeOut);
		map.put(TimeConst.CODE_TOTAL_WORK_ON_HOLIDAY, workOnHolidayTime);
		map.put(TimeConst.CODE_TOTAL_LATE_NIGHT, lateNight);
		map.put(TimeConst.CODE_TOTAL_TIMES_WORK, timesWork);
		map.put(TimeConst.CODE_TOTAL_TIMES_LATE, timesLate);
		map.put(TimeConst.CODE_TOTAL_TIMES_LEAVE_EARLY, timesLeaveEarly);
		map.put(TimeConst.CODE_TOTAL_TIMES_OVERTIMEWORK, timesOvertime);
		map.put(TimeConst.CODE_TOTAL_TIMES_WORKONHOLIDAY, workOnHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_LEGALHOLIDAY, timesLegalHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_SPECIFICHOLIDAY, timesSpecificHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_PAIDHOLIDAY, timesPaidHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_PAIDHOLIDAY_TIME, paidHolidayHour);
		map.put(TimeConst.CODE_TOTAL_TIMES_SPECIALHOLIDAY, totalSpecialHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_OTHERHOLIDAY, totalOtherHoliday);
		map.put(TimeConst.CODE_TOTAL_TIMES_SUBSTITUTE, timesHolidaySubstitute);
		map.put(TimeConst.CODE_TOTAL_TIMES_SUBHOLIDAY, timesCompensation);
		map.put(TimeConst.CODE_TOTAL_TIMES_ABSENCE, totalAbsence);
		return map;
	}
	
}
