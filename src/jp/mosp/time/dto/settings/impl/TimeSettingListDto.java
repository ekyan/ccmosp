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
import jp.mosp.time.dto.settings.TimeSettingListDtoInterface;

/**
 * 勤怠設定DTO
 */
public class TimeSettingListDto extends BaseDto implements TimeSettingListDtoInterface {
	
	private static final long	serialVersionUID	= 3440957341102795291L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmTimeSettingId;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 勤怠設定コード。
	 */
	private String				settingCode;
	
	/**
	 * 勤怠設定名称。
	 */
	private String				settingName;
	
	/**
	 * 勤怠設定略称。
	 */
	private String				settingAbbr;
	
	/**
	 * 締日略称。
	 */
	private String				cutoffAbbr;
	
	/**
	 * 有効/無効
	 */
	private int					inactivate;
	
	
	@Override
	public Date getActivateDate() {
		return activateDate;
	}
	
	@Override
	public String getCutoffAbbr() {
		return cutoffAbbr;
	}
	
	@Override
	public int getInactivate() {
		return inactivate;
	}
	
	@Override
	public String getSettingAbbr() {
		return settingAbbr;
	}
	
	@Override
	public String getSettingCode() {
		return settingCode;
	}
	
	@Override
	public String getSettingName() {
		return settingName;
	}
	
	@Override
	public long getTmmTimeSettingId() {
		return tmmTimeSettingId;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}
	
	@Override
	public void setCutoffAbbr(String cutoffAbbr) {
		this.cutoffAbbr = cutoffAbbr;
	}
	
	@Override
	public void setInactivate(int inactivate) {
		this.inactivate = inactivate;
	}
	
	@Override
	public void setSettingAbbr(String settingAbbr) {
		this.settingAbbr = settingAbbr;
	}
	
	@Override
	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}
	
	@Override
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	
	@Override
	public void setTmmTimeSettingId(long tmmTimeSettingId) {
		this.tmmTimeSettingId = tmmTimeSettingId;
	}
	
}
