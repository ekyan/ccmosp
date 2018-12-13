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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmLimitStandardDto;

/**
 * 限度基準管理DAOクラス
 */
public class TmmLimitStandardDao extends PlatformDao implements LimitStandardDaoInterface {
	
	/**
	 * 限度基準管理マスタ。
	 */
	public static final String	TABLE						= "tmm_limit_standard";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_LIMIT_STANDARD_ID	= "tmm_limit_standard_id";
	
	/**
	 * 勤怠設定コード。
	 */
	public static final String	COL_WORK_SETTING_CODE		= "work_setting_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 期間。
	 */
	public static final String	COL_TERM					= "term";
	
	/**
	 * 時間外限度時間。
	 */
	public static final String	COL_LIMIT_TIME				= "limit_time";
	
	/**
	 * 時間外注意時間。
	 */
	public static final String	COL_ATTENTION_TIME			= "attention_time";
	
	/**
	 * 時間外警告時間。
	 */
	public static final String	COL_WARNING_TIME			= "warning_time";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_LIMIT_STANDARD_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmLimitStandardDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmLimitStandardDto dto = new TmmLimitStandardDto();
		dto.setTmmLimitStandardId(getLong(COL_TMM_LIMIT_STANDARD_ID));
		dto.setWorkSettingCode(getString(COL_WORK_SETTING_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setTerm(getString(COL_TERM));
		dto.setLimitTime(getInt(COL_LIMIT_TIME));
		dto.setAttentionTime(getInt(COL_ATTENTION_TIME));
		dto.setWarningTime(getInt(COL_WARNING_TIME));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<LimitStandardDtoInterface> mappingAll() throws MospException {
		List<LimitStandardDtoInterface> all = new ArrayList<LimitStandardDtoInterface>();
		while (next()) {
			all.add((LimitStandardDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public LimitStandardDtoInterface findForKey(String workSettingCode, Date activateDate, String term)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_TERM));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			setParam(index++, activateDate);
			setParam(index++, term);
			executeQuery();
			LimitStandardDtoInterface dto = null;
			if (next()) {
				dto = (LimitStandardDtoInterface)mapping();
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
	public LimitStandardDtoInterface findForInfo(String workSettingCode, Date activateDate, String term)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_TERM));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			setParam(index++, activateDate);
			setParam(index++, term);
			executeQuery();
			LimitStandardDtoInterface dto = null;
			if (next()) {
				dto = (LimitStandardDtoInterface)mapping();
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
	public List<LimitStandardDtoInterface> findForSearch(String workSettingCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			setParam(index++, activateDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
		
	}
	
	@Override
	public List<LimitStandardDtoInterface> findForHistory(String workSettingCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
		
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			LimitStandardDtoInterface dto = (LimitStandardDtoInterface)baseDto;
			setParam(index++, dto.getTmmLimitStandardId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			LimitStandardDtoInterface dto = (LimitStandardDtoInterface)baseDto;
			setParam(index++, dto.getTmmLimitStandardId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		LimitStandardDtoInterface dto = (LimitStandardDtoInterface)baseDto;
		setParam(index++, dto.getTmmLimitStandardId());
		setParam(index++, dto.getWorkSettingCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getTerm());
		setParam(index++, dto.getLimitTime());
		setParam(index++, dto.getAttentionTime());
		setParam(index++, dto.getWarningTime());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	/**
	 * パラメータ設定(Date)。<br>
	 * java.sql.Timeとして設定する。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setTime(int index, Date param) throws MospException {
		try {
			if (ps != null) {
				if (param == null) {
					ps.setTime(index, null);
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(param);
					ps.setTime(index, new java.sql.Time(cal.getTimeInMillis()));
					
				}
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
}
