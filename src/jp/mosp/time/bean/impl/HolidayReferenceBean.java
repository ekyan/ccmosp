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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 * 休暇種別管理参照クラス。
 */
public class HolidayReferenceBean extends PlatformBean implements HolidayReferenceBeanInterface {
	
	/**
	 * 休暇種別管理DAO。
	 */
	private HolidayDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HolidayReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
	}
	
	@Override
	public HolidayDtoInterface getHolidayInfo(String holidayCode, Date targetDate, int holidayType)
			throws MospException {
		return dao.findForInfo(holidayCode, targetDate, holidayType);
	}
	
	@Override
	public List<HolidayDtoInterface> getHolidayHistory(String holidayCode, int holidayType) throws MospException {
		return dao.findForHistory(holidayCode, holidayType);
	}
	
	@Override
	public List<HolidayDtoInterface> getHolidayList(Date targetDate, int holidayType) throws MospException {
		return dao.findForActivateDate(targetDate, holidayType);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, int holidayType, boolean needBlank) throws MospException {
		// 一覧取得
		List<HolidayDtoInterface> list = dao.findForActivateDate(targetDate, holidayType);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// 配列作成
		for (HolidayDtoInterface dto : list) {
			array[idx][0] = dto.getHolidayCode();
			array[idx][1] = dto.getHolidayAbbr();
			idx++;
		}
		return array;
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, int holidayType, boolean needBlank, Set<String> set)
			throws MospException {
		List<HolidayDtoInterface> holidayList = new ArrayList<HolidayDtoInterface>();
		// 一覧取得
		List<HolidayDtoInterface> list = dao.findForActivateDate(targetDate, holidayType);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		for (String string : set) {
			for (HolidayDtoInterface holidayDto : list) {
				if (string.equals(holidayDto.getHolidayCode())) {
					holidayList.add(holidayDto);
					continue;
				}
			}
		}
		if (holidayList.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(holidayList.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// 配列作成
		for (HolidayDtoInterface dto : holidayList) {
			array[idx][0] = dto.getHolidayCode();
			array[idx][1] = dto.getHolidayAbbr();
			idx++;
		}
		return array;
	}
	
	@Override
	public String[][] getExportArray(Date targetDate) throws MospException {
		// 一覧取得
		List<HolidayDtoInterface> specialList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		List<HolidayDtoInterface> otherList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_OTHER);
		List<HolidayDtoInterface> absenceList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
		// 一覧件数確認
		if (specialList.isEmpty() && otherList.isEmpty() && absenceList.isEmpty()) {
			// 対象データ無し
			return prepareSelectArray(0, false);
		}
		String comma = mospParams.getName("Comma");
		String frontParentheses = mospParams.getName("FrontParentheses");
		String backParentheses = mospParams.getName("BackParentheses");
		String allHoliday = frontParentheses + mospParams.getName("AllTime") + backParentheses;
		String halfHoliday = frontParentheses + mospParams.getName("HalfTime") + backParentheses;
		String all = "all";
		String half = "half";
		// 配列準備
		String[][] array = prepareSelectArray((specialList.size() + otherList.size() + absenceList.size()) * 2, false);
		// 配列作成
		for (int i = 0; i < array.length; i += 2) {
			if (i / 2 < specialList.size()) {
				HolidayDtoInterface dto = specialList.get(i / 2);
				StringBuffer sb = new StringBuffer();
				sb.append(TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
				sb.append(comma);
				sb.append(dto.getHolidayCode());
				sb.append(comma);
				array[i][0] = sb.toString() + all;
				array[i][1] = dto.getHolidayAbbr() + allHoliday;
				array[i + 1][0] = sb.toString() + half;
				array[i + 1][1] = dto.getHolidayAbbr() + halfHoliday;
			} else if (i / 2 < specialList.size() + otherList.size()) {
				HolidayDtoInterface dto = otherList.get((i / 2) - specialList.size());
				StringBuffer sb = new StringBuffer();
				sb.append(TimeConst.CODE_HOLIDAYTYPE_OTHER);
				sb.append(comma);
				sb.append(dto.getHolidayCode());
				sb.append(comma);
				array[i][0] = sb.toString() + all;
				array[i][1] = dto.getHolidayAbbr() + allHoliday;
				array[i + 1][0] = sb.toString() + half;
				array[i + 1][1] = dto.getHolidayAbbr() + halfHoliday;
			} else if (i / 2 < specialList.size() + otherList.size() + absenceList.size()) {
				HolidayDtoInterface dto = absenceList.get((i / 2) - specialList.size() - otherList.size());
				StringBuffer sb = new StringBuffer();
				sb.append(TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
				sb.append(comma);
				sb.append(dto.getHolidayCode());
				sb.append(comma);
				array[i][0] = sb.toString() + all;
				array[i][1] = dto.getHolidayAbbr() + allHoliday;
				array[i + 1][0] = sb.toString() + half;
				array[i + 1][1] = dto.getHolidayAbbr() + halfHoliday;
			}
		}
		return array;
	}
	
	@Override
	public HolidayDtoInterface findForKey(String holidayCode, Date activateDate, int holidayType) throws MospException {
		return dao.findForKey(holidayCode, activateDate, holidayType);
	}
	
	@Override
	public String getHolidayAbbr(String holidayCode, Date targetDate, int holidayType) throws MospException {
		HolidayDtoInterface dto = dao.findForInfo(holidayCode, targetDate, holidayType);
		if (dto == null) {
			return holidayCode;
		}
		return dto.getHolidayAbbr();
	}
	
	@Override
	public String getHolidayType1NameForHolidayRequest(int type1, String type2) {
		if (type1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(type2)) {
				// 有給休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Salaried"));
				sb.append(mospParams.getName("Vacation"));
				return sb.toString();
			} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(type2)) {
				// ストック休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Stock"));
				sb.append(mospParams.getName("Vacation"));
				return sb.toString();
			}
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			// 特別休暇
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Specially"));
			return sb.toString();
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// その他休暇
			return mospParams.getName("Others");
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 欠勤
			return mospParams.getName("Absence");
		}
		return "";
	}
	
}
