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

/**
 * 勤怠設定DTOインターフェース
 */
public interface TimeSettingListDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmTimeSettingId();
	
	/**
	 * @return 有効日。
	 */
	Date getActivateDate();
	
	/**
	 * @return 勤怠設定コード。
	 */
	String getSettingCode();
	
	/**
	 * @return 勤怠設定名称。
	 */
	String getSettingName();
	
	/**
	 * @return 勤怠設定略称。
	 */
	String getSettingAbbr();
	
	/**
	 * @return 締日略称。
	 */
	String getCutoffAbbr();
	
	/**
	 * @return 有効/無効
	 */
	int getInactivate();
	
	/**
	 * @param tmmTimeSettingId セットする レコード識別ID。
	 */
	void setTmmTimeSettingId(long tmmTimeSettingId);
	
	/**
	 * @param activateDate セットする 有効日。 
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param settingCode セットする 勤怠設定コード。
	 */
	void setSettingCode(String settingCode);
	
	/**
	 * @param settingName セットする 勤怠設定名称。
	 */
	void setSettingName(String settingName);
	
	/**
	 * @param settingAbbr セットする 勤怠設定略称。
	 */
	void setSettingAbbr(String settingAbbr);
	
	/**
	 * @param cutoffAbbr セットする 締日略称。
	 */
	void setCutoffAbbr(String cutoffAbbr);
	
	/**
	 * @param inactivate セットする 有効/無効。
	 */
	void setInactivate(int inactivate);
}
