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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 勤怠集計結果一覧DTOインターフェース。
 */
public interface TotalTimeListDtoInterface extends EmployeeCodeDtoInterface, EmployeeNameDtoInterface,
		SectionCodeDtoInterface {
	
	/**
	 * @return 出勤日数。
	 */
	String getWorkDate();
	
	/**
	 * @param workDate セットする 出勤日数。
	 */
	void setWorkDate(String workDate);
	
	/**
	 * @return 勤務時間。
	 */
	String getWorkTime();
	
	/**
	 * @param workTime セットする 勤務時間。
	 */
	void setWorkTime(String workTime);
	
	/**
	 * @return 休憩時間。
	 */
	String getRestTime();
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(String restTime);
	
	/**
	 * @return 遅刻時間。
	 */
	String getLateTime();
	
	/**
	 * @param lateTime セットする 遅刻時間。
	 */
	void setLateTime(String lateTime);
	
	/**
	 * @return 早退時間。
	 */
	String getLeaveEarlyTime();
	
	/**
	 * @param leaveEarlyTime セットする 早退時間。
	 */
	void setLeaveEarlyTime(String leaveEarlyTime);
	
	/**
	 * @return 残業時間。
	 */
	String getOverTimeIn();
	
	/**
	 * @param overTimeIn セットする 残業時間。
	 */
	void setOverTimeIn(String overTimeIn);
	
	/**
	 * @return 法定外残業時間(外残)。
	 */
	String getOverTimeOut();
	
	/**
	 * @param overTimeOut セットする 法定外残業時間(外残)。
	 */
	void setOverTimeOut(String overTimeOut);
	
	/**
	 * @return 休出時間。
	 */
	String getWorkOnHolidayTime();
	
	/**
	 * @param workOnHolidayTime セットする 休出時間。
	 */
	void setWorkOnHolidayTime(String workOnHolidayTime);
	
	/**
	 * @return 深夜時間。
	 */
	String getLateNightTime();
	
	/**
	 * @param lateNightTime セットする 深夜時間。
	 */
	void setLateNightTime(String lateNightTime);
	
	/**
	 * @return 有休日数。
	 */
	String getPaidHoliday();
	
	/**
	 * @param paidHoliday セットする 有休日数。
	 */
	void setPaidHoliday(String paidHoliday);
	
	/**
	 * @return 休暇日数。
	 */
	String getAllHoliday();
	
	/**
	 * @param allHoliday セットする 休暇日数。
	 */
	void setAllHoliday(String allHoliday);
	
	/**
	 * @return 欠勤日数。
	 */
	String getAbsence();
	
	/**
	 * @param absence セットする 欠勤日数。
	 */
	void setAbsence(String absence);
	
	/**
	 * @return 未承認有無。
	 */
	String getApploval();
	
	/**
	 * @param apploval セットする 未承認有無。
	 */
	void setApploval(String apploval);
	
	/**
	 * @return 未集計有無。
	 */
	String getCalc();
	
	/**
	 * @param calc セットする 未集計有無。
	 */
	void setCalc(String calc);
	
	/**
	 * @return 修正履歴。
	 */
	String getCorrection();
	
	/**
	 * @param correction セットする 修正履歴。
	 */
	void setCorrection(String correction);
	
}
