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
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 代休データ参照クラス。
 */
public class SubHolidayReferenceBean extends TimeBean implements SubHolidayReferenceBeanInterface {
	
	/**
	 * 代休データDAO。
	 */
	protected SubHolidayDaoInterface			dao;
	
	/**
	 * 代休申請データDAO。
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * 勤怠データDAO。
	 */
	protected AttendanceDaoInterface			attendanceDao;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	/**
	 * 設定適用管理参照。
	 */
	protected ApplicationReferenceBeanInterface	applicationReference;
	
	/**
	 * 勤怠設定参照。
	 */
	protected TimeSettingReferenceBeanInterface	timeSettingReference;
	
	
	/**
	 * 深夜代休を利用する際の利用可能日数。<br>
	 */
//	public static final int						SUB_HOLIDAY_AVAILABLE_DAY	= 10;
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SubHolidayReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (SubHolidayDaoInterface)createDao(SubHolidayDaoInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
	}
	
	@Override
	public String[][] getSelectArray(String personalId, Date targetDate, String holidayRange,
			SubHolidayRequestDtoInterface dto) throws MospException {
		String frontWithCornerParentheses = mospParams.getName("FrontWithCornerParentheses");
		String backWithCornerParentheses = mospParams.getName("BackWithCornerParentheses");
		double subHolidayDays = 0;
		if ("1".equals(holidayRange)) {
			// 全休の場合
			subHolidayDays = 1;
		} else if ("2".equals(holidayRange) || "3".equals(holidayRange)) {
			// 午前休又は午後休の場合
			subHolidayDays = TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		applicationReference.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		TimeSettingDtoInterface timeSettingDto = timeSettingReference.getTimeSettingInfo(
				applicationDto.getWorkSettingCode(), targetDate);
		timeSettingReference.chkExistTimeSetting(timeSettingDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		Map<String, SubHolidayDtoInterface> map = new TreeMap<String, SubHolidayDtoInterface>();
		// 一覧取得
		List<SubHolidayDtoInterface> subHolidayList = dao.getSubHolidayList(personalId, DateUtility.addDay(
				DateUtility.addMonth(targetDate, -timeSettingDto.getSubHolidayLimitMonth()),
				-timeSettingDto.getSubHolidayLimitDate()), targetDate, subHolidayDays);
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(subHolidayDto.getPersonalId(),
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork());
			if (attendanceDto == null) {
				continue;
			}
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (workflowDto == null || workflowIntegrate.isCompleted(workflowDto) == false) {
				continue;
			}
			ApplicationDtoInterface subHolidayApplicationDto = applicationReference.findForPerson(personalId,
					subHolidayDto.getWorkDate());
			TimeSettingDtoInterface subHolidayTimeSettingDto = timeSettingDto;
			if (subHolidayApplicationDto != null) {
				TimeSettingDtoInterface tempTimeSettingDto = timeSettingReference.getTimeSettingInfo(
						subHolidayApplicationDto.getWorkSettingCode(), subHolidayDto.getWorkDate());
				if (tempTimeSettingDto != null) {
					subHolidayTimeSettingDto = tempTimeSettingDto;
				}
			}
			Date limitDate = DateUtility.addDay(
					DateUtility.addMonth(subHolidayDto.getWorkDate(),
							subHolidayTimeSettingDto.getSubHolidayLimitMonth()),
					subHolidayTimeSettingDto.getSubHolidayLimitDate());
//			if (subHolidayDto.getSubHolidayType() == 3) {
//				// 深夜代休
//				limitDate = DateUtility.addDay(subHolidayDto.getWorkDate(), SUB_HOLIDAY_AVAILABLE_DAY);
//				// 深夜代休は勤務日から10日以内であれば利用可能
//				if (limitDate.before(targetDate)) {
//					continue;
//				}
//			}
			StringBuffer sb = new StringBuffer();
			sb.append(DateUtility.getStringYear(limitDate));
			sb.append(DateUtility.getStringMonth(limitDate));
			sb.append(DateUtility.getStringDay(limitDate));
			sb.append(DateUtility.getStringYear(subHolidayDto.getWorkDate()));
			sb.append(DateUtility.getStringMonth(subHolidayDto.getWorkDate()));
			sb.append(DateUtility.getStringDay(subHolidayDto.getWorkDate()));
			sb.append(subHolidayDto.getSubHolidayType());
			map.put(sb.toString(), subHolidayDto);
		}
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		List<String> holidayRangeList = new ArrayList<String>();
		for (Entry<String, SubHolidayDtoInterface> entry : map.entrySet()) {
			SubHolidayDtoInterface subHolidayDto = entry.getValue();
			double count = 0;
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 下書または取下の場合
				if (subHolidayRequestWorkflowDto == null || workflowIntegrate.isDraft(subHolidayRequestWorkflowDto)
						|| workflowIntegrate.isWithDrawn(subHolidayRequestWorkflowDto)) {
					continue;
				}
				if (dto != null && subHolidayRequestDto.getTmdSubHolidayRequestId() == dto.getTmdSubHolidayRequestId()) {
					continue;
				}
				int subHolidayRange = subHolidayRequestDto.getHolidayRange();
				if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					count++;
				} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休又は午後休の場合
					count += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
			double notUseDay = subHolidayDto.getSubHolidayDays() - count;
			if (notUseDay < subHolidayDays) {
				continue;
			}
			list.add(subHolidayDto);
			if (notUseDay >= TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 残りが全休の場合
				holidayRangeList.add(frontWithCornerParentheses + mospParams.getName("AllTime")
						+ backWithCornerParentheses);
			} else if (notUseDay >= TimeConst.HOLIDAY_TIMES_HALF) {
				// 残りが半休の場合
				holidayRangeList.add(frontWithCornerParentheses + mospParams.getName("HalfTime")
						+ backWithCornerParentheses);
			} else {
				// 対象データ無し
				return getNoObjectDataPulldown();
			}
		}
		if (list.size() != holidayRangeList.size()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		String[][] array = prepareSelectArray(list.size(), false);
		int i = 0;
		for (SubHolidayDtoInterface subHolidayDto : list) {
			array[i][0] = DateUtility.getStringDate(subHolidayDto.getWorkDate()) + ","
					+ subHolidayDto.getSubHolidayType();
			StringBuffer sb = new StringBuffer();
			sb.append(frontWithCornerParentheses);
			int subHolidayType = subHolidayDto.getSubHolidayType();
			if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				// 所定
				sb.append(mospParams.getName("Prescribed"));
			} else if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				// 法定
				sb.append(mospParams.getName("Legal"));
			} else if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				// 深夜
				sb.append(mospParams.getName("Midnight"));
			} else {
				continue;
			}
			sb.append(backWithCornerParentheses);
			sb.append(getStringDate(subHolidayDto.getWorkDate()));
			sb.append(holidayRangeList.get(i));
			array[i][1] = sb.toString();
			i++;
		}
		return array;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date startDate, Date endDate)
			throws MospException {
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		for (SubHolidayDtoInterface dto : dao.getSubHolidayList(personalId, startDate, endDate,
				TimeConst.HOLIDAY_TIMES_HALF)) {
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), dto.getWorkDate(),
					dto.getTimesWork());
			if (attendanceDto == null) {
				continue;
			}
			if (!workflowIntegrate.isCompleted(attendanceDto.getWorkflow())) {
				// 承認済でない場合
				continue;
			}
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public double getPossibleRequestDays(String personalId, Date targetDate) throws MospException {
		double days = 0;
		List<SubHolidayDtoInterface> list = getSubHolidayList(personalId, targetDate);
		for (SubHolidayDtoInterface dto : list) {
			double requestDays = 0;
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					dto.getWorkDate(), dto.getTimesWork(), dto.getSubHolidayType());
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(subHolidayRequestDto
					.getWorkflow());
				// 下書または取下の場合
				if (workflowDto == null || workflowIntegrate.isDraft(workflowDto)
						|| workflowIntegrate.isWithDrawn(workflowDto)) {
					continue;
				}
				int subHolidayRange = subHolidayRequestDto.getHolidayRange();
				if (subHolidayRange == 1) {
					// 全休の場合
					requestDays++;
				} else if (subHolidayRange == 2 || subHolidayRange == 3) {
					// 午前休又は午後休の場合
					requestDays += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
			double remainDays = dto.getSubHolidayDays() - requestDays;
			if (remainDays < TimeConst.HOLIDAY_TIMES_HALF) {
				continue;
			}
			days += remainDays;
		}
		return days;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date targetDate) throws MospException {
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		if (applicationDto == null) {
			// 対象データ無し
			return list;
		}
		TimeSettingDtoInterface timeSettingDto = timeSettingReference.getTimeSettingInfo(
				applicationDto.getWorkSettingCode(), targetDate);
		if (timeSettingDto == null) {
			// 対象データ無し
			return list;
		}
		List<SubHolidayDtoInterface> subHolidayList = dao.getSubHolidayList(personalId, DateUtility.addDay(
				DateUtility.addMonth(targetDate, -timeSettingDto.getSubHolidayLimitMonth()),
				-timeSettingDto.getSubHolidayLimitDate()), targetDate, TimeConst.HOLIDAY_TIMES_HALF);
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(subHolidayDto.getPersonalId(),
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork());
			if (attendanceDto == null) {
				continue;
			}
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (workflowDto == null || workflowIntegrate.isCompleted(workflowDto) == false) {
				continue;
			}
			list.add(subHolidayDto);
		}
		return list;
	}
	
	@Override
	public Float[] getBirthSubHolidayTimes(String personalId, Date startDate, Date endDate) throws MospException {
		// 代休発生回数準備
		Float[] days = { null, null, null };
		double prescribed = 0.0;
		double legal = 0.0;
		double midnight = 0.0;
		
		// 代休データリストを取得
		List<SubHolidayDtoInterface> list = dao.findSubHolidayList(personalId, startDate, endDate);
		// 代休データリスト毎に処理
		for (SubHolidayDtoInterface dto : list) {
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(personalId, dto.getWorkDate(),
					TimeBean.TIMES_WORK_DEFAULT);
			if (attendanceDto == null) {
				continue;
			}
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			// 承認済でない場合
			if (workflowDto == null || workflowIntegrate.isCompleted(workflowDto) == false) {
				continue;
			}
			// 所定代休
			if (dto.getSubHolidayType() == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				prescribed += dto.getSubHolidayDays();
				continue;
			}
			// 法定代休
			if (dto.getSubHolidayType() == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				legal += dto.getSubHolidayDays();
				continue;
			}
			// 深夜代休の場合
			if (dto.getSubHolidayType() == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				midnight += dto.getSubHolidayDays();
				continue;
			}
		}
		days[0] = (float)prescribed;
		days[1] = (float)legal;
		days[2] = (float)midnight;
		return days;
	}
	
}
