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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayEntranceDateReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayEntranceDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;

/**
 * 有給休暇自動付与(入社日)参照クラス。
 */
public class PaidHolidayEntranceDateReferenceBean extends PlatformBean implements
		PaidHolidayEntranceDateReferenceBeanInterface {
	
	/**
	 * 有給休暇自動付与(入社日)DAO。
	 */
	private PaidHolidayEntranceDateDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayEntranceDateReferenceBean() {
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PaidHolidayEntranceDateReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (PaidHolidayEntranceDateDaoInterface)createDao(PaidHolidayEntranceDateDaoInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateDtoInterface getPaidHolidayEntranceDateInfo(String paidHolidayCode, Date targetDate,
			int workMonth) throws MospException {
		return dao.findForInfo(paidHolidayCode, targetDate, workMonth);
	}
	
	@Override
	public PaidHolidayEntranceDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int workMonth)
			throws MospException {
		return dao.findForKey(paidHolidayCode, activateDate, workMonth);
	}
	
	@Override
	public List<PaidHolidayEntranceDateDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException {
		return dao.findForList(paidHolidayCode, activateDate);
	}
	
}
