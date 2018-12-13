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
 * 時間単位有給休暇データDTOインターフェース
 */
public interface TimelyPaidHolidayDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdTimelyPaidHolidayId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 取得日。
	 */
	Date getAcquisitionDate();
	
	/**
	 * @return 利用可能時間数。
	 */
	int getPossibleHour();
	
	/**
	 * @return 付与時間数。
	 */
	int getGivingHour();
	
	/**
	 * @return 廃棄時間数。
	 */
	int getCancelHour();
	
	/**
	 * @return 使用時間数。
	 */
	int getUseHour();
	
	/**
	 * @param tmdTimelyPaidHolidayId セットする レコード識別ID。
	 */
	void setTmdTimelyPaidHolidayId(long tmdTimelyPaidHolidayId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param acquisitionDate セットする 取得日。
	 */
	void setAcquisitionDate(Date acquisitionDate);
	
	/**
	 * @param possibleHour セットする 利用可能時間数。
	 */
	void setPossibleHour(int possibleHour);
	
	/**
	 * @param givingHour セットする 付与時間数。
	 */
	void setGivingHour(int givingHour);
	
	/**
	 * @param cancelHour セットする 廃棄時間数。
	 */
	void setCancelHour(int cancelHour);
	
	/**
	 * @param useHour セットする 使用時間数。
	 */
	void setUseHour(int useHour);
}
