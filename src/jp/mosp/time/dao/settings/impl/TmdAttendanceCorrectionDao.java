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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.AttendanceCorrectionDaoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAttendanceCorrectionDto;

/**
 * 勤怠データ修正情報参照DAOクラス。
 */
public class TmdAttendanceCorrectionDao extends PlatformDao implements AttendanceCorrectionDaoInterface {
	
	/**
	 * 勤怠データ修正。
	 */
	public static final String	TABLE								= "tmd_attendance_correction";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_ATTENDANCE_CORRECTION_ID	= "tmd_attendance_correction_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID						= "personal_id";
	/**
	 * 勤務日。
	 */
	public static final String	COL_WORK_DATE						= "work_date";
	/**
	 * 勤務回数。
	 */
	public static final String	COL_WORKS							= "works";
	/**
	 * 修正番号。
	 */
	public static final String	COL_CORRECTION_TIMES				= "correction_times";
	/**
	 * 修正箇所。
	 */
	public static final String	COL_CORRECTION_TYPE					= "correction_type";
	/**
	 * 修正日時。
	 */
	public static final String	COL_CORRECTION_DATE					= "correction_date";
	/**
	 * 修正社員コード。
	 */
	public static final String	COL_CORRECTION_PERSONAL_ID			= "correction_personal_id";
	/**
	 * 修正前。
	 */
	public static final String	COL_CORRECTION_BEFORE				= "correction_before";
	/**
	 * 修正後。
	 */
	public static final String	COL_CORRECTION_AFTER				= "correction_after";
	/**
	 * 修正理由。
	 */
	public static final String	COL_CORRECTION_REASON				= "correction_reason";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMD_ATTENDANCE_CORRECTION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdAttendanceCorrectionDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdAttendanceCorrectionDto dto = new TmdAttendanceCorrectionDto();
		dto.setTmdAttendanceCorrectionId(getLong(COL_TMD_ATTENDANCE_CORRECTION_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setWorks(getInt(COL_WORKS));
		dto.setCorrectionTimes(getInt(COL_CORRECTION_TIMES));
		dto.setCorrectionDate(getTimestamp(COL_CORRECTION_DATE));
		dto.setCorrectionPersonalId(getString(COL_CORRECTION_PERSONAL_ID));
		dto.setCorrectionType(getString(COL_CORRECTION_TYPE));
		dto.setCorrectionBefore(getString(COL_CORRECTION_BEFORE));
		dto.setCorrectionAfter(getString(COL_CORRECTION_AFTER));
		dto.setCorrectionReason(getString(COL_CORRECTION_REASON));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<AttendanceCorrectionDtoInterface> mappingAll() throws MospException {
		List<AttendanceCorrectionDtoInterface> all = new ArrayList<AttendanceCorrectionDtoInterface>();
		while (next()) {
			all.add((AttendanceCorrectionDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public AttendanceCorrectionDtoInterface findForLatestInfo(String personalId, Date workDate, int works)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_WORKS));
			sb.append(getOrderByColumnDescLimit1(COL_CORRECTION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, works);
			executeQuery();
			AttendanceCorrectionDtoInterface dto = null;
			if (next()) {
				dto = (AttendanceCorrectionDtoInterface)mapping();
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
	public List<AttendanceCorrectionDtoInterface> findForHistory(String personalId, Date workDate, int works)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_WORKS));
			sb.append(getOrderByColumn(COL_CORRECTION_DATE, COL_TMD_ATTENDANCE_CORRECTION_ID));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, works);
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
			AttendanceCorrectionDtoInterface dto = (AttendanceCorrectionDtoInterface)baseDto;
			setParam(index++, dto.getTmdAttendanceCorrectionId());
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
			AttendanceCorrectionDtoInterface dto = (AttendanceCorrectionDtoInterface)baseDto;
			setParam(index++, dto.getTmdAttendanceCorrectionId());
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
		AttendanceCorrectionDtoInterface dto = (AttendanceCorrectionDtoInterface)baseDto;
		setParam(index++, dto.getTmdAttendanceCorrectionId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getWorks());
		setParam(index++, dto.getCorrectionTimes());
		setParam(index++, dto.getCorrectionType());
		setParam(index++, dto.getCorrectionDate(), true);
		setParam(index++, dto.getCorrectionPersonalId());
		setParam(index++, dto.getCorrectionBefore());
		setParam(index++, dto.getCorrectionAfter());
		setParam(index++, dto.getCorrectionReason());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
