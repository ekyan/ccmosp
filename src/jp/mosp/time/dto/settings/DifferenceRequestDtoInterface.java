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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.RequestDtoInterface;

/**
 * @author a_nakamura
 *
 */
/**
 * 時差出勤申請DTOインターフェース
 */
public interface DifferenceRequestDtoInterface extends BaseDtoInterface, RequestDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdDifferenceRequestId();
	
	/**
	 * @return 時差出勤日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 時差出勤区分。
	 */
	String getDifferenceType();
	
	/**
	 * @return 勤務形態コード。
	 */
	String getWorkTypeCode();
	
	/**
	 * @return 開始日。
	 */
	int getStartDate();
	
	/**
	 * @return 時差出勤開始時刻。
	 */
	Date getRequestStart();
	
	/**
	 * @return 時差出勤終了時刻。
	 */
	Date getRequestEnd();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdDifferenceRequestId セットする レコード識別ID。
	 */
	void setTmdDifferenceRequestId(long tmdDifferenceRequestId);
	
	/**
	 * @param requestDate セットする 時差出勤日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param differenceType セットする 時差出勤区分。
	 */
	void setDifferenceType(String differenceType);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param startDate セットする 開始日。
	 */
	void setStartDate(int startDate);
	
	/**
	 * @param requestStart セットする 時差出勤開始時刻。
	 */
	void setRequestStart(Date requestStart);
	
	/**
	 * @param requestEnd セットする 時差出勤終了時刻。
	 */
	void setRequestEnd(Date requestEnd);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
