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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TimelyPaidHolidayDtoInterface;

/**
 * 時間単位有給休暇データDTO
 */
public class TmdTimelyPaidHolidayDto extends BaseDto implements TimelyPaidHolidayDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3433600996573332700L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTimelyPaidHolidayId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 取得日。
	 */
	private Date				acquisitionDate;
	/**
	 * 利用可能時間数。
	 */
	private int					possibleHour;
	/**
	 * 付与時間数。
	 */
	private int					givingHour;
	/**
	 * 廃棄時間数。
	 */
	private int					cancelHour;
	/**
	 * 使用時間数。
	 */
	private int					useHour;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public Date getAcquisitionDate() {
		return getDateClone(acquisitionDate);
	}
	
	@Override
	public int getCancelHour() {
		return cancelHour;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public int getGivingHour() {
		return givingHour;
	}
	
	@Override
	public int getPossibleHour() {
		return possibleHour;
	}
	
	@Override
	public long getTmdTimelyPaidHolidayId() {
		return tmdTimelyPaidHolidayId;
	}
	
	@Override
	public int getUseHour() {
		return useHour;
	}
	
	@Override
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = getDateClone(acquisitionDate);
	}
	
	@Override
	public void setCancelHour(int cancelHour) {
		this.cancelHour = cancelHour;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setGivingHour(int givingHour) {
		this.givingHour = givingHour;
	}
	
	@Override
	public void setPossibleHour(int possibleHour) {
		this.possibleHour = possibleHour;
	}
	
	@Override
	public void setTmdTimelyPaidHolidayId(long tmdTimelyPaidHolidayId) {
		this.tmdTimelyPaidHolidayId = tmdTimelyPaidHolidayId;
	}
	
	@Override
	public void setUseHour(int useHour) {
		this.useHour = useHour;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
