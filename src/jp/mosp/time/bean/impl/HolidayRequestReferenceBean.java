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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請参照クラス。
 */
public class HolidayRequestReferenceBean extends TimeBean implements HolidayRequestReferenceBeanInterface {
	
	/**
	 * 休暇申請DAO。
	 */
	private HolidayRequestDaoInterface		dao;
	
	/**
	 * ワークフロー参照クラス。
	 */
	private WorkflowIntegrateBeanInterface	workflowIntegerBean;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayRequestReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HolidayRequestReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 休暇申請DAOクラス取得
		dao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		workflowIntegerBean = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date requestDate)
			throws MospException {
		return dao.findForList(personalId, requestDate);
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date requestDate)
			throws MospException {
		// 休暇情報リスト準備
		List<HolidayRequestDtoInterface> newList = new ArrayList<HolidayRequestDtoInterface>();
		// 休暇申請リストを取得
		List<HolidayRequestDtoInterface> list = dao.findForList(personalId, requestDate);
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface dto : list) {
			// 取下の場合
			if (workflowIntegerBean.isWithDrawn(dto.getWorkflow())) {
				continue;
			}
			// リスト追加
			newList.add(dto);
		}
		return newList;
	}
	
	@Override
	public HolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime) throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestStartDate, holidayType1, holidayType2, holidayRange,
				startTime);
	}
	
	@Override
	public HolidayRequestDtoInterface findForEndTimeKeyOnWorkflow(String personalId, Date requestStartDate,
			int holidayType1, String holidayType2, int holidayRange, Date endTime) throws MospException {
		return dao.findForEndTimeKeyOnWorkflow(personalId, requestStartDate, holidayType1, holidayType2, holidayRange,
				endTime);
	}
	
	@Override
	public HolidayRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (HolidayRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public HolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForTermOnWorkflow(personalId, firstDate, lastDate);
	}
	
	@Override
	public Map<String, Object> getApprovedDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException {
		// マップ準備
		Map<String, Object> map = new HashMap<String, Object>();
		// 承認済使用日数と時間数準備
		double approvedDay = 0;
		int approvedHour = 0;
		// 申請開始日取得
		Date countDate = requestStartDate;
		while (!countDate.after(requestEndDate)) {
			// 承認完了休暇申請リストを取得
			List<HolidayRequestDtoInterface> list = dao.findForApprovedList(personalId, acquisitionDate, holidayType1,
					holidayType2, countDate);
			// 承認完了休暇申請リスト毎に処理
			for (HolidayRequestDtoInterface dto : list) {
				// 休暇範囲取得
				int holidayRange = dto.getHolidayRange();
				// 全休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					approvedDay++;
				}
				// 半休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					approvedDay += TimeConst.HOLIDAY_TIMES_HALF;
				}
				// 時間休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					approvedHour += dto.getUseHour();
				}
			}
			countDate = addDay(countDate, 1);
		}
		map.put(TimeConst.CODE_APPROVED_DAY, approvedDay);
		map.put(TimeConst.CODE_APPROVED_HOUR, approvedHour);
		return map;
	}
	
	/*
	public Map<String, Object> getRequestDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		double requestDay = 0;
		int requestHour = 0;
		Date countDate = requestStartDate;
		while (!countDate.after(requestEndDate)) {
			List<HolidayRequestDtoInterface> list = dao.findForRequestList(personalId, acquisitionDate, holidayType1,
					holidayType2, countDate);
			for (HolidayRequestDtoInterface dto : list) {
				int holidayRange = dto.getHolidayRange();
				if (holidayRange == 1) {
					requestDay++;
				} else if (holidayRange == 2 || holidayRange == 3) {
					requestDay += 0.5;
				} else if (holidayRange == 4) {
					requestHour += Integer.parseInt(DateUtility.getStringHour(new Date(dto.getEndTime().getTime()
							- dto.getStartTime().getTime())));
				}
			}
			countDate = addDay(countDate, 1);
		}
		map.put(TimeConst.CODE_REQUEST_DAY, requestDay);
		map.put(TimeConst.CODE_REQUEST_HOUR, requestHour);
		return map;
	}*/
	@Override
	public Map<String, Object> getRequestDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		double requestDay = 0;
		int requestHour = 0;
		List<HolidayRequestDtoInterface> list = dao.findForRequestList(personalId, acquisitionDate, holidayType1,
				holidayType2, requestStartDate, requestEndDate);
		for (HolidayRequestDtoInterface dto : list) {
			requestDay += dto.getUseDay();
			requestHour += dto.getUseHour();
		}
		map.put(TimeConst.CODE_REQUEST_DAY, requestDay);
		map.put(TimeConst.CODE_REQUEST_HOUR, requestHour);
		return map;
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		initial(personalId, targetDate);
	}
	
	@Override
	public Map<String, Integer> getTimeHolidayStatusTimesMap(String personalId, Date acquisitionDate,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException {
		// マップ準備
		Map<String, Integer> timeHolidayMap = new HashMap<String, Integer>();
		// 回数準備
		int draft = 0;
		int approval = 0;
		int complete = 0;
		int revert = 0;
		// 休暇申請情報リスト取得
		List<HolidayRequestDtoInterface> holidayList = getHolidayRequestListOnWorkflow(personalId, acquisitionDate);
		if (holidayList.isEmpty()) {
			return timeHolidayMap;
		}
		// 休暇リスト毎に処理
		for (HolidayRequestDtoInterface dto : holidayList) {
			// 時間休でない又は取下の場合
			if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME
					|| workflowIntegerBean.isWithDrawn(dto.getWorkflow())) {
				continue;
			}
			if (holidayRequestDto != null && holidayRequestDto.getTmdHolidayRequestId() == dto.getTmdHolidayRequestId()) {
				// レコード識別IDが同じ場合
				continue;
			}
			// 下書
			if (workflowIntegerBean.isDraft(dto.getWorkflow())) {
				draft++;
				continue;
			}
			// 承認済
			if (workflowIntegerBean.isCompleted(dto.getWorkflow())) {
				complete++;
				continue;
			}
			// 差戻
			if (workflowIntegerBean.isFirstReverted(dto.getWorkflow())) {
				revert++;
				continue;
			}
			// 申請
			approval++;
		}
		timeHolidayMap.put(mospParams.getName("Under"), draft);
		timeHolidayMap.put(mospParams.getName("Register"), approval);
		timeHolidayMap.put(mospParams.getName("Finish"), complete);
		timeHolidayMap.put(mospParams.getName("Back"), revert);
		return timeHolidayMap;
	}
}
