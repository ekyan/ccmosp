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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TimelyPaidHolidayDaoInterface;
import jp.mosp.time.dto.settings.TimelyPaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTimelyPaidHolidayDto;

/**
 * 時間単位有給休暇データDAOクラス。
 */
public class TmdTimelyPaidHolidayDao extends PlatformDao implements TimelyPaidHolidayDaoInterface {
	
	/**
	 * 時間単位有給休暇データ。
	 */
	public static final String	TABLE							= "tmd_timely_paid_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TIMELY_PAID_HOLIDAY_ID	= "tmd_timely_paid_holiday_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 取得日。
	 */
	public static final String	COL_ACQUISITION_DATE			= "acquisition_date";
	
	/**
	 * 利用可能時間数。
	 */
	public static final String	COL_POSSIBLE_HOUR				= "possible_hour";
	
	/**
	 * 付与時間数。
	 */
	public static final String	COL_GIVING_HOUR					= "giving_hour";
	
	/**
	 * 廃棄時間数。
	 */
	public static final String	COL_CANCEL_HOUR					= "cancel_hour";
	
	/**
	 * 使用時間数。
	 */
	public static final String	COL_USE_HOUR					= "use_hour";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_TMD_TIMELY_PAID_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTimelyPaidHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTimelyPaidHolidayDto dto = new TmdTimelyPaidHolidayDto();
		dto.setTmdTimelyPaidHolidayId(getLong(COL_TMD_TIMELY_PAID_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setAcquisitionDate(getDate(COL_ACQUISITION_DATE));
		dto.setPossibleHour(getInt(COL_POSSIBLE_HOUR));
		dto.setGivingHour(getInt(COL_GIVING_HOUR));
		dto.setCancelHour(getInt(COL_CANCEL_HOUR));
		dto.setUseHour(getInt(COL_USE_HOUR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TimelyPaidHolidayDtoInterface> mappingAll() throws MospException {
		List<TimelyPaidHolidayDtoInterface> all = new ArrayList<TimelyPaidHolidayDtoInterface>();
		while (next()) {
			all.add((TimelyPaidHolidayDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public TimelyPaidHolidayDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
			setParam(index++, acquisitionDate);
			executeQuery();
			TimelyPaidHolidayDtoInterface dto = null;
			if (next()) {
				dto = (TimelyPaidHolidayDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public TimelyPaidHolidayDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACQUISITION_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			setParam(index++, activateDate);
			executeQuery();
			TimelyPaidHolidayDtoInterface dto = null;
			if (next()) {
				dto = (TimelyPaidHolidayDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		TimelyPaidHolidayDtoInterface dto = (TimelyPaidHolidayDtoInterface)baseDto;
		setParam(index++, dto.getTmdTimelyPaidHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getAcquisitionDate());
		setParam(index++, dto.getPossibleHour());
		setParam(index++, dto.getGivingHour());
		setParam(index++, dto.getCancelHour());
		setParam(index++, dto.getUseHour());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
