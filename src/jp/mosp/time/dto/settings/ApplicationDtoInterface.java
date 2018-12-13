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

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 設定適用マスタDTOインターフェース。
 */
public interface ApplicationDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmApplicationId();
	
	/**
	 * @return 設定適用コード。
	 */
	String getApplicationCode();
	
	/**
	 * @return 設定適用区分
	 */
	int getApplicationType();
	
	/**
	 * @return 設定適用名称。
	 */
	String getApplicationName();
	
	/**
	 * @return 設定適用略称。
	 */
	String getApplicationAbbr();
	
	/**
	 * @return 勤怠設定コード。
	 */
	String getWorkSettingCode();
	
	/**
	 * @return カレンダコード。
	 */
	String getScheduleCode();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 勤務地コード。
	 */
	String getWorkPlaceCode();
	
	/**
	 * @return 雇用契約コード。
	 */
	String getEmploymentContractCode();
	
	/**
	 * @return 所属コード。
	 */
	String getSectionCode();
	
	/**
	 * @return 職位コード。
	 */
	String getPositionCode();
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @param tmmApplicationId セットする レコード識別ID。
	 */
	void setTmmApplicationId(long tmmApplicationId);
	
	/**
	 * @param applicationCode セットする 設定適用コード。
	 */
	void setApplicationCode(String applicationCode);
	
	/**
	 * @param applicationType セットする 設定適用区分
	 */
	void setApplicationType(int applicationType);
	
	/**
	 * @param applicationName セットする 設定適用名称。
	 */
	void setApplicationName(String applicationName);
	
	/**
	 * @param applicationAbbr セットする 設定適用略称。
	 */
	void setApplicationAbbr(String applicationAbbr);
	
	/**
	 * @param workSettingCode セットする 勤怠設定コード。
	 */
	void setWorkSettingCode(String workSettingCode);
	
	/**
	 * @param scheduleCode セットする カレンダコード。
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード。
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentContractCode セットする 雇用契約コード。
	 */
	void setEmploymentContractCode(String employmentContractCode);
	
	/**
	 * @param sectionCode セットする 所属コード。
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param personalIds セットする 個人ID
	 */
	void setPersonalId(String personalIds);
	
}
