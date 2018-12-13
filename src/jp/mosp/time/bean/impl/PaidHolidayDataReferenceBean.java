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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇データ参照クラス。
 */
public class PaidHolidayDataReferenceBean extends PlatformBean implements PaidHolidayDataReferenceBeanInterface {
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	private PaidHolidayDataDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayDataReferenceBean() {
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PaidHolidayDataReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (PaidHolidayDataDaoInterface)createDao(PaidHolidayDataDaoInterface.class);
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto == null) {
			return null;
		}
		return (PaidHolidayDataDtoInterface)dto;
	}
	
	@Override
	public PaidHolidayDataDtoInterface getPaidHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException {
		return dao.findForInfo(personalId, targetDate, acquisitionDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDataInfoList(String personalId, Date targetDate)
			throws MospException {
		return dao.findForInfoList(personalId, targetDate);
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		return dao.findForKey(personalId, activateDate, acquisitionDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDataInfoList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForInfoList(personalId, firstDate, lastDate);
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForExpirationDateInfo(String personalId, Date expirationDate)
			throws MospException {
		return dao.findForExpirationDateInfo(personalId, expirationDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> findForExpirationDateList(String personalId, Date expirationDate)
			throws MospException {
		return dao.findForExpirationDateList(personalId, expirationDate);
	}
	
}
