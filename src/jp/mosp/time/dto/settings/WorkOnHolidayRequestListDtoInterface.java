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
package jp.mosp.time.dto.settings;

import java.util.Date;

/**
 * 休日出勤一覧DTOインターフェース
 */
public interface WorkOnHolidayRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdWorkOnHolidayRequestId();
	
	/**
	 * @return 申請日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 出勤予定時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 退勤予定時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @return 振替日1。
	 */
	Date getSubstituteDate1();
	
	/**
	 * @return 振替範囲1。
	 */
	int getSubstituteRange1();
	
	/**
	 * @return 振替日2。
	 */
	Date getSubstituteDate2();
	
	/**
	 * @return 振替範囲2。
	 */
	int getSubstituteRange2();
	
	/**
	 * @param tmdWorkOnHolidayRequestId セットする レコード識別ID。
	 */
	void setTmdWorkOnHolidayRequestId(long tmdWorkOnHolidayRequestId);
	
	/**
	 * @param requestDate セットする 申請日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param startTime セットする 出勤予定時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param endTime セットする 退勤予定時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
	/**
	 * @param substituteDate1 セットする 振替日1。
	 */
	void setSubstituteDate1(Date substituteDate1);
	
	/**
	 * @param substituteRange1 セットする 振替範囲1。
	 */
	void setSubstituteRange1(int substituteRange1);
	
	/**
	 * @param substituteDate2 セットする 振替日2。
	 */
	void setSubstituteDate2(Date substituteDate2);
	
	/**
	 * @param substituteRange2 セットする 振替範囲2。
	 */
	void setSubstituteRange2(int substituteRange2);
	
}
