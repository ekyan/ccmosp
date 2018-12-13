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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeApplicationBean;
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
public class SubHolidayReferenceBean extends TimeApplicationBean implements SubHolidayReferenceBeanInterface {
	
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
		// 継承元実行
		super.initBean();
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
		// 【】取得
		String frontWithCornerParentheses = mospParams.getName("FrontWithCornerParentheses");
		String backWithCornerParentheses = mospParams.getName("BackWithCornerParentheses");
		// 代休日数準備
		double appliSubHolidayDays = 0;
		// 休暇範囲から代休日数を取得
		if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInteger(holidayRange)) {
			// 全休の場合
			appliSubHolidayDays = TimeConst.HOLIDAY_TIMES_ALL;
		} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInteger(holidayRange)
				|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInteger(holidayRange)) {
			// 午前休又は午後休の場合
			appliSubHolidayDays = TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 代休申請日時点で有効な代休データリストを取得
		List<SubHolidayDtoInterface> subHolidayList = getfindForList(personalId, targetDate, targetDate,
				appliSubHolidayDays);
		// リスト準備
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		List<String> holidayRangeList = new ArrayList<String>();
		// 期間最終日時点で有効な代休データ毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 代休日数準備
			double count = 0;
			// 代休申請データリスト
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休申請ワークフロ情報取得
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請済でない場合
				if (!WorkflowUtility.isApplied(subHolidayRequestWorkflowDto)) {
					continue;
				}
				// 対象代休申請データが同じ場合
				if (dto != null
						&& subHolidayRequestDto.getTmdSubHolidayRequestId() == dto.getTmdSubHolidayRequestId()) {
					continue;
				}
				// 代休休暇範囲取得
				int subHolidayRange = subHolidayRequestDto.getHolidayRange();
				// 全休の場合
				if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					count += TimeConst.HOLIDAY_TIMES_ALL;
				} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休又は午後休の場合
					count += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
			// 保有代休残日数（代休日数-代休申請(下書又は取下)）取得
			double notUseDay = subHolidayDto.getSubHolidayDays() - count;
			// 利用数より申請数の方が多い場合
			if (notUseDay < appliSubHolidayDays) {
				continue;
			}
			// リスト追加
			list.add(subHolidayDto);
			if (notUseDay >= TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 残りが全休の場合
				holidayRangeList
					.add(frontWithCornerParentheses + mospParams.getName("AllTime") + backWithCornerParentheses);
			} else if (notUseDay >= TimeConst.HOLIDAY_TIMES_HALF) {
				// 残りが半休の場合
				holidayRangeList
					.add(frontWithCornerParentheses + mospParams.getName("HalfTime") + backWithCornerParentheses);
			} else {
				// 対象データ無し
				return getNoObjectDataPulldown();
			}
		}
		// 対象データが無い場合
		if (list.size() != holidayRangeList.size()) {
			return getNoObjectDataPulldown();
		}
		// プルダウン配列準備
		String[][] array = prepareSelectArray(list.size(), false);
		int i = 0;
		// 残代休情報毎に処理
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
	public double getPossibleRequestDays(String personalId, Date targetDate) throws MospException {
		// 残日数準備
		double days = 0;
		// 対象日時点で有効な代休リスト取得
		List<SubHolidayDtoInterface> list = getfindForList(personalId, targetDate, targetDate,
				TimeConst.HOLIDAY_TIMES_HALF);
		// 対象日時点で有効な代休データ毎に処理
		for (SubHolidayDtoInterface dto : list) {
			// 申請日数準備
			double requestDays = 0;
			// 代休申請リスト取得
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					dto.getWorkDate(), dto.getTimesWork(), dto.getSubHolidayType());
			// 代休申請データ毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請済でない場合
				if (!WorkflowUtility.isApplied(workflowDto)) {
					continue;
				}
				// 休暇範囲取得
				int subHolidayRange = subHolidayRequestDto.getHolidayRange();
				if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					requestDays++;
				} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
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
	public List<SubHolidayDtoInterface> getfindForList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException {
		// 期間開始日時点の代休データリスト
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		// 勤怠設定情報取得
		setTimeSettings(personalId, startDate);
		if (timeSettingDto == null) {
			// 対象データ無し
			return list;
		}
		// 代休取得開始日取得
		Date subHolidayStartDate = DateUtility.addDay(
				DateUtility.addMonth(startDate, -timeSettingDto.getSubHolidayLimitMonth()),
				-timeSettingDto.getSubHolidayLimitDate());
		// （対象日-対象日時点の取得期限）～対象日までの代休情報リストを取得
		List<SubHolidayDtoInterface> subHolidayList = getSubHolidayList(personalId, subHolidayStartDate, endDate,
				subHolidayDays);
		// 代休情報毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 勤怠データ
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(subHolidayDto.getPersonalId(),
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork());
			if (attendanceDto == null) {
				continue;
			}
			// ワークフロ情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (!WorkflowUtility.isCompleted(workflowDto)) {
				continue;
			}
			// リスト追加
			list.add(subHolidayDto);
		}
		// 各代休毎の休日出勤日時点での取得期限を確認し、期間最終日より前の場合省く
		List<SubHolidayDtoInterface> list2 = new ArrayList<SubHolidayDtoInterface>();
		// 代休申請日時点で有効な代休データ毎に処理
		for (SubHolidayDtoInterface subHolidayDto : list) {
			// 代休勤務日時点の勤怠設定情報取得
			ApplicationDtoInterface subHolidayApplicationDto = applicationReference.findForPerson(personalId,
					subHolidayDto.getWorkDate());
			if (subHolidayApplicationDto == null) {
				// 代休勤務日時点の設定適用情報なし
				continue;
			}
			TimeSettingDtoInterface tempTimeSettingDto = timeSettingReference
				.getTimeSettingInfo(subHolidayApplicationDto.getWorkSettingCode(), subHolidayDto.getWorkDate());
			if (tempTimeSettingDto == null) {
				// 代休勤務日時点の勤怠設定情報なし
				continue;
			}
			// // 代休勤務日時点の勤怠設定から期限日取得
			Date subHolidayWorklimitDate = DateUtility.addDay(
					DateUtility.addMonth(subHolidayDto.getWorkDate(), tempTimeSettingDto.getSubHolidayLimitMonth()),
					tempTimeSettingDto.getSubHolidayLimitDate());
			// 代休勤務日時点の代休期限日が休日出勤日より前の場合
			if (subHolidayWorklimitDate.compareTo(startDate) < 0) {
				continue;
			}
//			if (subHolidayDto.getSubHolidayType() == 3) {
//				// 深夜代休
//				limitDate = DateUtility.addDay(subHolidayDto.getWorkDate(), SUB_HOLIDAY_AVAILABLE_DAY);
//				// 深夜代休は勤務日から10日以内であれば利用可能
//				if (limitDate.before(targetDate)) {
//					continue;
//					}
//				}
			// リスト追加
			list2.add(subHolidayDto);
		}
		// 残代休リスト準備
		List<SubHolidayDtoInterface> list3 = new ArrayList<SubHolidayDtoInterface>();
		// 代休データ毎に代休申請を確認し、利用している分省く
		for (SubHolidayDtoInterface subHolidayDto : list2) {
			// 代休日数準備
			double count = 0;
			// 代休申請データリスト
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休申請ワークフロ情報取得
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請していない場合
				if (!WorkflowUtility.isApplied(subHolidayRequestWorkflowDto)) {
					continue;
				}
				// 代休休暇範囲取得
				int subHolidayRange = subHolidayRequestDto.getHolidayRange();
				// 全休の場合
				if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					count += TimeConst.HOLIDAY_TIMES_ALL;
				} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休又は午後休の場合
					count += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
			// 保有代休残日数（代休日数-代休申請(申請済)）取得
			double notUseDay = subHolidayDto.getSubHolidayDays() - count;
			// 利用数より申請数の方が多い場合
			if (notUseDay <= 0) {
				continue;
			}
			list3.add(subHolidayDto);
		}
		return list3;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException {
		// リスト準備
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		// 代休データ毎に処理
		for (SubHolidayDtoInterface dto : dao.getSubHolidayList(personalId, startDate, endDate, subHolidayDays)) {
			// 勤怠情報が承認済でない場合
			if (!isCompletedAttendance(dto.getPersonalId(), dto.getWorkDate())) {
				continue;
			}
			// リスト追加
			list.add(dto);
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
			// 承認済勤怠情報がない場合
			if (!isCompletedAttendance(personalId, dto.getWorkDate())) {
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
	
	/**
	 * 対象日の勤怠情報が承認済か確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果（true：承認済、false：承認済でない）
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected boolean isCompletedAttendance(String personalId, Date targetDate) throws MospException {
		// 勤怠データ取得
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(personalId, targetDate,
				TimeBean.TIMES_WORK_DEFAULT);
		if (attendanceDto == null) {
			return false;
		}
		// ワークフロ情報取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		// 承認済でない場合
		if (workflowDto == null || !workflowIntegrate.isCompleted(workflowDto)) {
			return false;
		}
		return true;
	}
	
}
