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

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 時間単位有給休暇トランザクションDTOインターフェース
 */
public interface TimelyPaidHolidayTransactionDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmtTimelyPaidHolidayId();
	
	/**
	 * @return 社員コード。
	 */
	String getEmployeeCode();
	
	/**
	 * @return 取得日。
	 */
	Date getAcquisitionDate();
	
	/**
	 * @return 付与時間数。
	 */
	int getGivingHour();
	
	/**
	 * @return 廃棄時間数。
	 */
	int getCancelHour();
	
	/**
	 * @param tmtTimelyPaidHolidayId セットする レコード識別ID。
	 */
	void setTmtTimelyPaidHolidayId(long tmtTimelyPaidHolidayId);
	
	/**
	 * @param employeeCode セットする 社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param acquisitionDate セットする 取得日。
	 */
	void setAcquisitionDate(Date acquisitionDate);
	
	/**
	 * @param givingHour セットする 付与時間数。
	 */
	void setGivingHour(int givingHour);
	
	/**
	 * @param cancelHour セットする 廃棄時間数。
	 */
	void setCancelHour(int cancelHour);
}
