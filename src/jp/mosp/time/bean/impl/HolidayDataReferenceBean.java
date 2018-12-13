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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;

/**
 * 休暇付与情報参照処理。<br>
 */
public class HolidayDataReferenceBean extends PlatformBean implements HolidayDataReferenceBeanInterface {
	
	/**
	 * 休暇データDAO。
	 */
	private HolidayDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayDataReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HolidayDataReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
	}
	
	@Override
	public HolidayDataDtoInterface findForInfo(String personalId, Date activateDate, String holidayCode,
			int holidayType) throws MospException {
		return dao.findForInfo(personalId, activateDate, holidayCode, holidayType);
	}
	
	@Override
	public HolidayDataDtoInterface findForKey(String personalId, Date activateDate, String holidayCode, int holidayType)
			throws MospException {
		return dao.findForKey(personalId, activateDate, holidayCode, holidayType);
	}
	
	@Override
	public List<HolidayDataDtoInterface> findForInfoList(String personalId, Date targetDate, String inactivateFlag,
			int holidayType) throws MospException {
		return dao.findForInfoList(personalId, targetDate, inactivateFlag, holidayType);
	}
	
	@Override
	public List<HolidayDataDtoInterface> getActiveList(String personalId, Date targetDate, int holidayType)
			throws MospException {
		return findForInfoList(personalId, targetDate, String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
	}
	
	@Override
	public List<HolidayDataDtoInterface> findPersonTerm(String personalId, Date firstDate, Date lastDate,
			int holidayType) throws MospException {
		return dao.findPersonTerm(personalId, firstDate, lastDate, holidayType);
	}
	
	@Override
	public List<HolidayDataDtoInterface> getActiveListForTerm(String personalId, Date firstDate, Date lastDate,
			int holidayType, String holidayCode) throws MospException {
		// 休暇付与情報リストを準備
		List<HolidayDataDtoInterface> list = new ArrayList<HolidayDataDtoInterface>();
		// 対象期間内に付与された休暇付与情報毎に処理
		for (HolidayDataDtoInterface dto : findPersonTerm(personalId, firstDate, lastDate, holidayType)) {
			// 無効でない場合
			if (PlatformUtility.isDtoActivate(dto) == false) {
				// 次の休暇付与情報へ
				continue;
			}
			// 休暇コードが同じである場合
			if (MospUtility.isEqual(holidayCode, dto.getHolidayCode())) {
				// 休暇付与情報リストに追加
				list.add(dto);
			}
		}
		// 休暇付与情報リストを取得
		return list;
	}
	
}
