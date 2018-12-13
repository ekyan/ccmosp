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

import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.AttendanceTotalReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠データ合計参照クラス。
 */
public class AttendanceTotalReferenceBean extends PlatformBean implements AttendanceTotalReferenceBeanInterface {
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendanceTotalReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public AttendanceTotalReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() {
	}
	
	@Override
	public Map<String, Integer> getAttendanceTotalInfo(List<AttendanceDtoInterface> list) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		int totalWork = 0;
		int totalRest = 0;
		int totalLate = 0;
		int totalLeaveEarly = 0;
		int totalOverTimeIn = 0;
		int totalOverTimeOut = 0;
		int totalWorkOnHoliday = 0;
		int totalLateNight = 0;
		int timesWork = 0;
		int timesLate = 0;
		int overtimeWork = 0;
		int timesLeaveEarly = 0;
		int timesWorkOnHoliday = 0;
		
		for (int i = 0; i < list.size(); i++) {
			// 合計時間
			totalWork = totalWork + list.get(i).getWorkTime();
			totalRest = totalRest + list.get(i).getRestTime();
			totalLate = totalLate + list.get(i).getLateTime();
			totalLeaveEarly = totalLeaveEarly + list.get(i).getLeaveEarlyTime();
			totalOverTimeIn = totalOverTimeIn + list.get(i).getOvertime();
			totalOverTimeOut = totalOverTimeOut + list.get(i).getOvertimeOut();
			totalWorkOnHoliday = totalWorkOnHoliday + list.get(i).getSpecificWorkTime()
					+ list.get(i).getLegalWorkTime();
			totalLateNight = totalLateNight + list.get(i).getLateNightTime();
			// 回数
			if (list.get(i).getWorkTime() != 0) {
				timesWork++;
			}
			if (list.get(i).getLateTime() != 0) {
				timesLate++;
			}
			if (list.get(i).getOvertime() != 0) {
				overtimeWork++;
			}
			if (list.get(i).getLeaveEarlyTime() != 0) {
				timesLeaveEarly++;
			}
			if (list.get(i).getSpecificWorkTime() != 0 || list.get(i).getLegalWorkTime() != 0) {
				timesWorkOnHoliday++;
			}
		}
		map.put(TimeConst.CODE_TOTAL_WORK_TIME, totalWork);
		map.put(TimeConst.CODE_TOTAL_REST_TIME, totalRest);
		map.put(TimeConst.CODE_TOTAL_LATE_TIME, totalLate);
		map.put(TimeConst.CODE_TOTAL_LEAVE_EARLY, totalLeaveEarly);
		map.put(TimeConst.CODE_TOTAL_OVER_TIMEIN, totalOverTimeIn);
		map.put(TimeConst.CODE_TOTAL_OVER_TIMEOUT, totalOverTimeOut);
		map.put(TimeConst.CODE_TOTAL_WORK_ON_HOLIDAY, totalWorkOnHoliday);
		map.put(TimeConst.CODE_TOTAL_LATE_NIGHT, totalLateNight);
		map.put(TimeConst.CODE_TOTAL_TIMES_WORK, timesWork);
		map.put(TimeConst.CODE_TOTAL_TIMES_LATE, timesLate);
		map.put(TimeConst.CODE_TOTAL_TIMES_OVERTIMEWORK, overtimeWork);
		map.put(TimeConst.CODE_TOTAL_TIMES_LEAVE_EARLY, timesLeaveEarly);
		map.put(TimeConst.CODE_TOTAL_TIMES_WORKONHOLIDAY, timesWorkOnHoliday);
		return map;
	}
}
