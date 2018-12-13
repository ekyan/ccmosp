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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayEntranceDateDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayFirstYearDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayPointDateDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇情報参照クラス。
 */
public class PaidHolidayInfoReferenceBean extends TimeApplicationBean implements PaidHolidayInfoReferenceBeanInterface {
	
	/**
	 * 有給休暇データDAO。
	 */
	private PaidHolidayDataDaoInterface						paidHolidayDataDao;
	
	/**
	 * 有給休暇トランザクションDAO。
	 */
	private PaidHolidayTransactionDaoInterface				paidHolidayTransactionDao;
	
	/**
	 * 休暇申請DAO。
	 */
	private HolidayRequestDaoInterface						holidayRequestDao;
	
	/**
	 * 休暇申請参照。
	 */
	private HolidayRequestReferenceBeanInterface			holidayRequest;
	
	/**
	 * 有給休暇自動付与(基準日)参照クラス。
	 */
	protected PaidHolidayPointDateReferenceBeanInterface	paidHolidayPointDateReference;
	
	/**
	 * 有給休暇基準日管理DAO。
	 */
	private PaidHolidayPointDateDaoInterface				paidHolidayPointDao;
	
	/**
	 * 有給休暇入社日管理DAO。
	 */
	private PaidHolidayEntranceDateDaoInterface				paidHolidayEntranceDateDao;
	
	/**
	 * 勤怠集計データDAO。
	 */
	private TotalTimeDataDaoInterface						totalTimeDataDao;
	
	/**
	 * 有給休暇初年度DAO。
	 */
	private PaidHolidayFirstYearDaoInterface				firstYearDao;
	
	/**
	 * 有給休暇初年度付与参照クラス。
	 */
	protected PaidHolidayFirstYearReferenceBeanInterface	paidHolidayFirstYearReference;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface				entranceRefer;
	
	/**
	 * 有給休暇データ登録クラス。
	 */
	protected PaidHolidayDataRegistBeanInterface			paidHolidayDataRegist;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * カレンダ管理参照クラス。
	 */
	protected ScheduleReferenceBeanInterface				scheduleReference;
	
	/**
	 * カレンダ日参照クラス。
	 */
	protected ScheduleDateReferenceBeanInterface			scheduleDateReference;
	
	/**
	 * 締日ユーティリティ。
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * 申請ユーティリティクラス。
	 */
	protected RequestUtilBeanInterface						requestUtil;
	
	/**
	 * 入社日
	 */
	protected Date											entranceDate;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayInfoReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PaidHolidayInfoReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のBean初期化処理を実施
		super.initBean();
		// 有給休暇データDAO取得
		paidHolidayDataDao = (PaidHolidayDataDaoInterface)createDao(PaidHolidayDataDaoInterface.class);
		// 有給休暇トランザクションDAO取得
		paidHolidayTransactionDao = (PaidHolidayTransactionDaoInterface)createDao(PaidHolidayTransactionDaoInterface.class);
		// 休暇申請DAO
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		// 休暇申請参照クラス取得
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		// 有給休暇基準日管理DAO
		paidHolidayPointDao = (PaidHolidayPointDateDaoInterface)createDao(PaidHolidayPointDateDaoInterface.class);
		// 有給休暇入社日管理DAO
		paidHolidayEntranceDateDao = (PaidHolidayEntranceDateDaoInterface)createDao(PaidHolidayEntranceDateDaoInterface.class);
		// 勤怠集計データDAO
		totalTimeDataDao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
		// 有給休暇初年度DAO
		firstYearDao = (PaidHolidayFirstYearDaoInterface)createDao(PaidHolidayFirstYearDaoInterface.class);
		// 各種Bean準備
		paidHolidayPointDateReference = (PaidHolidayPointDateReferenceBeanInterface)createBean(PaidHolidayPointDateReferenceBeanInterface.class);
		paidHolidayFirstYearReference = (PaidHolidayFirstYearReferenceBeanInterface)createBean(PaidHolidayFirstYearReferenceBeanInterface.class);
		entranceRefer = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		paidHolidayDataRegist = (PaidHolidayDataRegistBeanInterface)createBean(PaidHolidayDataRegistBeanInterface.class);
		// ワークフロー統括
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		// カレンダ管理参照
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		// カレンダ日参照
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		// 締日ユーティリティ
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		// 申請ユーティリティ
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
	}
	
	@Override
	@Deprecated
	public List<PaidHolidayDataDtoInterface> getPaidHolidayCalcInfo(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇情報リスト準備
		List<PaidHolidayDataDtoInterface> list = new ArrayList<PaidHolidayDataDtoInterface>();
		boolean hasPaidHolidaySettings = hasPaidHolidaySettings(personalId, targetDate);
//		Date oneYearBeforeActivateDate = DateUtility.addYear(targetDate, -1);
		// 有給休暇データリスト取得
		List<PaidHolidayDataDtoInterface> paidHolidayDataDtoList = paidHolidayDataDao.findForInfoList(personalId,
				targetDate);
		// 有給休暇データリスト毎に処理
		for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataDtoList) {
			// 取得日
			Date acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
//			if (acquisitionDate.compareTo(oneYearBeforeActivateDate) >= 0) {
			// 有給休暇トランザクション情報リスト(手動付与・破棄)を取得
			List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
				.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
			// 有給休暇トランザクション情報リスト毎に処理
			for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
				// 有休休暇日数+付与日数
				paidHolidayDataDto.setGivingDay(paidHolidayDataDto.getGivingDay()
						+ paidHolidayTransactionDto.getGivingDay());
				// 有休休暇時間数+付与時間数
				paidHolidayDataDto.setGivingHour(paidHolidayDataDto.getGivingHour()
						+ paidHolidayTransactionDto.getGivingHour());
				// 有休廃棄日数+廃棄日数
				paidHolidayDataDto.setCancelDay(paidHolidayDataDto.getCancelDay()
						+ paidHolidayTransactionDto.getCancelDay());
				// 有休廃棄時間数+廃棄時間数
				paidHolidayDataDto.setCancelHour(paidHolidayDataDto.getCancelHour()
						+ paidHolidayTransactionDto.getCancelHour());
			}
			// 有休休暇承認済申請マップを取得
			Map<String, Object> approvedMap = holidayRequest
				.getApprovedDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
					.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]),
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[0][0],
						paidHolidayDataDto.getActivateDate(), targetDate);
			// 承認済みの合計日数
			paidHolidayDataDto.setUseDay(((Double)approvedMap.get(TimeConst.CODE_APPROVED_DAY)).doubleValue());
			// 承認済みの合計時間
			paidHolidayDataDto.setUseHour(((Integer)approvedMap.get(TimeConst.CODE_APPROVED_HOUR)).intValue());
			// 有休休暇設定
			if (hasPaidHolidaySettings) {
				// 分母設定
				paidHolidayDataDto.setDenominatorDayHour(paidHolidayDto.getTimeAcquisitionLimitTimes());
			}
			// 有効日
			paidHolidayDataDto.setActivateDate(targetDate);
			// リストに追加
			list.add(paidHolidayDataDto);
//			}
//			if (acquisitionDate.before(oneYearBeforeActivateDate)
//					&& acquisitionDate.compareTo(DateUtility.addYear(targetDate, -2)) >= 0) {
			// 手動付与・破棄
//				List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
//					.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
//				for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
//					paidHolidayDataDto.setGivingDay(paidHolidayDataDto.getGivingDay()
//							+ paidHolidayTransactionDto.getGivingDay());
//					paidHolidayDataDto.setGivingHour(paidHolidayDataDto.getGivingHour()
//							+ paidHolidayTransactionDto.getGivingHour());
//					paidHolidayDataDto.setCancelDay(paidHolidayDataDto.getCancelDay()
//							+ paidHolidayTransactionDto.getCancelDay());
//					paidHolidayDataDto.setCancelHour(paidHolidayDataDto.getCancelHour()
//							+ paidHolidayTransactionDto.getCancelHour());
//				}
			// 申請
//				Map<String, Object> approvedMap = holidayRequest
//					.getApprovedDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
//						.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]), mospParams.getProperties()
//						.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[0][0], paidHolidayDataDto
//						.getActivateDate(), targetDate);
//				paidHolidayDataDto.setUseDay(((Double)approvedMap.get(TimeConst.CODE_APPROVED_DAY)).doubleValue());
//				paidHolidayDataDto.setUseHour(((Integer)approvedMap.get(TimeConst.CODE_APPROVED_HOUR)).intValue());
//				list.add(paidHolidayDataDto);
//			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate) throws MospException {
		return getPaidHolidayInfo(personalId, targetDate, true);
	}
	
	@Override
	public Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate, boolean containNotApproved)
			throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TimeConst.CODE_ACTIVATE_DATE, targetDate);
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_CURRENT_TIME, 0);
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, 0);
		map.put(TimeConst.CODE_GIVING_DAY, 0.0);
		map.put(TimeConst.CODE_GIVING_TIME, 0);
		map.put(TimeConst.CODE_CANCEL_DAY, 0.0);
		map.put(TimeConst.CODE_CANCEL_TIME, 0);
		map.put(TimeConst.CODE_USE_DAY, 0.0);
		map.put(TimeConst.CODE_USE_TIME, 0);
		// 今年度残日数
		double currentDay = 0;
		// 今年度残時間
		int currentTime = 0;
		// 前年度残日数
		double formerDay = 0;
		// 前年度残時間
		int formerTime = 0;
		// 今年度保有日数
		double currentHoldDay = 0;
		// 今年度保有時間
		int currentHoldTime = 0;
		// 前年度保有日数
		double formerHoldDay = 0;
		// 前年度保有時間
		int formerHoldTime = 0;
		// 今年度支給日数
		double currentGivingDay = 0;
		// 今年度支給時間
		int currentGivingTime = 0;
		// 前年度支給日数
		double formerGivingDay = 0;
		// 前年度支給時間
		int formerGivingTime = 0;
		// 今年度廃棄日数
		double currentCancelDay = 0;
		// 今年度廃棄時間
		int currentCancelTime = 0;
		// 前年度廃棄日数
		double formerCancelDay = 0;
		// 前年度廃棄時間
		int formerCancelTime = 0;
		// 今年度利用日数
		double currentUseDay = 0;
		// 今年度利用時間
		int currentUseTime = 0;
		// 前年度利用日数
		double formerUseDay = 0;
		// 前年度利用時間
		int formerUseTime = 0;
		// 今年度付与日準備
		Date currentAcquisitionDate = null;
		// 所定労働時間を取得
		int generalWorkHour = 0;
		// 設定有給休暇情報を取得し、設定
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 今年度付与日に今年度+基準日設定
			currentAcquisitionDate = DateUtility.getDate(DateUtility.getYear(targetDate),
					paidHolidayDto.getPointDateMonth(), paidHolidayDto.getPointDateDay());
			// 今年度付与日がまだ先の場合
			if (targetDate.before(currentAcquisitionDate)) {
				// 年度-1
				currentAcquisitionDate = DateUtility.getDate(DateUtility.getYear(targetDate) - 1,
						paidHolidayDto.getPointDateMonth(), paidHolidayDto.getPointDateDay());
			}
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
		if (currentAcquisitionDate == null) {
			return map;
		}
		// 有給休暇データリスト取得
		List<PaidHolidayDataDtoInterface> paidHolidayDataDtoList = paidHolidayDataDao.findForInfoList(personalId,
				targetDate);
		// 有給休暇データリスト毎に処理
		for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataDtoList) {
			// 有効日設定
			map.put(TimeConst.CODE_ACTIVATE_DATE, paidHolidayDataDto.getActivateDate());
			// 取得日取得
			Date acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
			// 取得日が今年度付与日の前でない場合
			if (!acquisitionDate.before(currentAcquisitionDate)) {
				// 今年度
				currentHoldDay += paidHolidayDataDto.getHoldDay();
				currentHoldTime += paidHolidayDataDto.getHoldHour();
				// 手動付与・破棄
				List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
					.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
				for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
					// 付与日数・付与時間・廃棄日数・廃棄時間数
					currentGivingDay += paidHolidayTransactionDto.getGivingDay();
					currentGivingTime += paidHolidayTransactionDto.getGivingHour();
					currentCancelDay += paidHolidayTransactionDto.getCancelDay();
					currentCancelTime += paidHolidayTransactionDto.getCancelHour();
				}
				// 申請
				if (containNotApproved) {
					// 未承認申請を含む場合
					Map<String, Object> requestMap = holidayRequest.getRequestDayHour(personalId, acquisitionDate, 1,
							Integer.toString(1), paidHolidayDataDto.getActivateDate(), targetDate);
					currentUseDay += ((Double)requestMap.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
					currentUseTime += ((Integer)requestMap.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
				} else {
					// 承認済のみの場合
					Map<String, Object> approvedMap = holidayRequest.getApprovedDayHour(personalId, acquisitionDate, 1,
							Integer.toString(1), paidHolidayDataDto.getActivateDate(), targetDate);
					// 承認済の合計日数
					currentUseDay += ((Double)approvedMap.get(TimeConst.CODE_APPROVED_DAY)).doubleValue();
					// 承認済の合計時間数
					currentUseTime += ((Integer)approvedMap.get(TimeConst.CODE_APPROVED_HOUR)).intValue();
				}
				currentDay = currentHoldDay + currentGivingDay - currentCancelDay - currentUseDay;
				currentTime = currentHoldTime + currentGivingTime - currentCancelTime - currentUseTime;
				if (generalWorkHour > 0) {
					while (currentTime < 0 && currentDay >= 1) {
						currentDay--;
						currentTime += generalWorkHour;
					}
				}
			} else if (acquisitionDate.before(currentAcquisitionDate)) {
				// 前年度以前
				formerHoldDay += paidHolidayDataDto.getHoldDay();
				formerHoldTime += paidHolidayDataDto.getHoldHour();
				// 手動付与・破棄
				List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
					.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
				for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
					// 付与日数・付与時間・廃棄日数・廃棄時間数
					formerGivingDay += paidHolidayTransactionDto.getGivingDay();
					formerGivingTime += paidHolidayTransactionDto.getGivingHour();
					formerCancelDay += paidHolidayTransactionDto.getCancelDay();
					formerCancelTime += paidHolidayTransactionDto.getCancelHour();
				}
				// 申請
				if (containNotApproved) {
					// 未承認申請を含む場合
					Map<String, Object> requestMap = holidayRequest.getRequestDayHour(personalId, acquisitionDate, 1,
							Integer.toString(1), paidHolidayDataDto.getActivateDate(), targetDate);
					formerUseDay += ((Double)requestMap.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
					formerUseTime += ((Integer)requestMap.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
				} else {
					// 承認済のみの場合
					Map<String, Object> approvedMap = holidayRequest.getApprovedDayHour(personalId, acquisitionDate, 1,
							Integer.toString(1), paidHolidayDataDto.getActivateDate(), targetDate);
					// 承認済の合計日数
					formerUseDay += ((Double)approvedMap.get(TimeConst.CODE_APPROVED_DAY)).doubleValue();
					// 承認済の合計時間数
					formerUseTime += ((Integer)approvedMap.get(TimeConst.CODE_APPROVED_HOUR)).intValue();
				}
				formerDay = formerHoldDay + formerGivingDay - formerCancelDay - formerUseDay;
				formerTime = formerHoldTime + formerGivingTime - formerCancelTime - formerUseTime;
				if (generalWorkHour > 0) {
					while (formerTime < 0 && formerDay >= 1) {
						formerDay--;
						formerTime += generalWorkHour;
					}
				}
			}
		}
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, currentDay);
		map.put(TimeConst.CODE_CURRENT_TIME, currentTime);
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, formerDay);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, formerTime);
		map.put(TimeConst.CODE_GIVING_DAY, currentGivingDay + formerGivingDay);
		map.put(TimeConst.CODE_GIVING_TIME, currentGivingTime + formerGivingTime);
		map.put(TimeConst.CODE_CANCEL_DAY, currentCancelDay + formerCancelDay);
		map.put(TimeConst.CODE_CANCEL_TIME, currentCancelTime + formerCancelTime);
		map.put(TimeConst.CODE_USE_DAY, currentUseDay + formerUseDay);
		map.put(TimeConst.CODE_USE_TIME, currentUseTime + formerUseTime);
		return map;
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayPossibleRequestForRequestList(String personalId,
			Date targetDate) throws MospException {
		// リスト準備
		List<PaidHolidayDataDtoInterface> list = new ArrayList<PaidHolidayDataDtoInterface>();
		// 有休休暇データリスト取得
		List<PaidHolidayDataDtoInterface> paidHolidayDataDtoList = paidHolidayDataDao.findForInfoList(personalId,
				targetDate);
		// 有休設定限度時間準備
		int generalWorkHour = 0;
		// 設定有給休暇情報を取得し、設定
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 有休設定時休限度時間取得
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
		for (PaidHolidayDataDtoInterface dto : paidHolidayDataDtoList) {
			// 今年度支給日数
			double currentGivingDate = dto.getGivingDay();
			// 今年度支給時間
			int currentGivingTime = dto.getGivingHour();
			// 今年度廃棄日数
			double currentCancelDate = dto.getCancelDay();
			// 今年度廃棄時間
			int currentCancelTime = dto.getCancelHour();
			// 手動付与・破棄
			List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
				.findForList(personalId, dto.getAcquisitionDate(), dto.getActivateDate(), targetDate);
			for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
				currentGivingDate += paidHolidayTransactionDto.getGivingDay();
				currentGivingTime += paidHolidayTransactionDto.getGivingHour();
				currentCancelDate += paidHolidayTransactionDto.getCancelDay();
				currentCancelTime += paidHolidayTransactionDto.getCancelHour();
			}
			// 今年度利用日数
			double currentUseDate = dto.getUseDay();
			// 今年度利用時間
			int currentUseTime = dto.getUseHour();
			// 申請
			List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForRequestList(personalId,
					dto.getAcquisitionDate(), TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
					Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY), dto.getActivateDate(), dto.getLimitDate());
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if (workflowIntegrate.isFirstReverted(holidayRequestDto.getWorkflow())) {
					// 1次戻の場合
					continue;
				}
				currentUseDate += holidayRequestDto.getUseDay();
				currentUseTime += holidayRequestDto.getUseHour();
			}
			// 今年度残日数
			double currentDate = dto.getHoldDay() + currentGivingDate - currentCancelDate - currentUseDate;
			// 今年度残時間
			int currentTime = dto.getHoldHour() + currentGivingTime - currentCancelTime - currentUseTime;
			if (generalWorkHour > 0) {
				while (currentTime < 0 && currentDate >= 1) {
					currentDate--;
					currentTime += generalWorkHour;
				}
			}
			dto.setHoldDay(currentDate);
			dto.setHoldHour(currentTime);
			dto.setGivingDay(currentGivingDate);
			dto.setGivingHour(currentGivingTime);
			dto.setCancelDay(currentCancelDate);
			dto.setCancelHour(currentCancelTime);
			dto.setUseDay(currentUseDate);
			dto.setUseHour(currentUseTime);
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getPaidHolidayPossibleRequest(String personalId, Date targetDate) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TimeConst.CODE_CURRENT_ACTIVATE_DATE, null);
		map.put(TimeConst.CODE_CURRENT_GIVING_DATE, null);
		map.put(TimeConst.CODE_CURRENT_GRANT_DAY, 0);
		map.put(TimeConst.CODE_CURRENT_GRANT_HOUR, 0);
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_CURRENT_TIME, 0);
		map.put(TimeConst.CODE_CURRENT_LIMIT_DATE, null);
		map.put(TimeConst.CODE_FORMER_ACTIVATE_DATE, null);
		map.put(TimeConst.CODE_FORMER_GIVING_DATE, null);
		map.put(TimeConst.CODE_FORMER_GRANT_DAY, 0);
		map.put(TimeConst.CODE_FORMER_GRANT_HOUR, 0);
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, 0);
		map.put(TimeConst.CODE_FORMER_LIMIT_DATE, null);
		map.put(TimeConst.CODE_GIVING_DAY, 0.0);
		map.put(TimeConst.CODE_GIVING_TIME, 0);
		map.put(TimeConst.CODE_CANCEL_DAY, 0.0);
		map.put(TimeConst.CODE_CANCEL_TIME, 0);
//		map.put(TimeConst.CODE_USE_DAY, 0.0);
//		map.put(TimeConst.CODE_USE_TIME, 0);
		// 今年度有効日
		Date currentActivateDate = null;
		// 今年度付与日
		Date currentGivingDate = null;
		// 今年度付与日数
		double currentGrantDay = 0;
		// 今年度付与時間数
		int currentGrantHour = 0;
		// 今年度残日数
		double currentDay = 0;
		// 今年度残時間
		int currentTime = 0;
		// 今年度期限日
		Date currentLimitDate = null;
		// 前年度有効日
		Date formerActivateDate = null;
		// 前年度付与日
		Date formerGivingDate = null;
		// 前年度付与日数
		double formerGrantDay = 0;
		// 前年度付与時間数
		int formerGrantHour = 0;
		// 前年度残日数
		double formerDay = 0;
		// 前年度残時間
		int formerTime = 0;
		// 前年度期限日
		Date formerLimitDate = null;
		// 今年度保有日数
		double currentHoldDay = 0;
		// 今年度保有時間
		int currentHoldTime = 0;
		// 前年度保有日数
		double formerHoldDay = 0;
		// 前年度保有時間
		int formerHoldTime = 0;
		// 今年度支給日数
		double currentGivingDay = 0;
		// 今年度支給時間
		int currentGivingTime = 0;
		// 前年度支給日数
		double formerGivingDay = 0;
		// 前年度支給時間
		int formerGivingTime = 0;
		// 今年度廃棄日数
		double currentCancelDay = 0;
		// 今年度廃棄時間
		int currentCancelTime = 0;
		// 前年度廃棄日数
		double formerCancelDay = 0;
		// 前年度廃棄時間
		int formerCancelTime = 0;
		// 今年度利用日数
		double currentUseDay = 0;
		// 今年度利用時間
		int currentUseTime = 0;
		// 前年度利用日数
		double formerUseDay = 0;
		// 前年度利用時間
		int formerUseTime = 0;
		Date currentAcquisitionDate = null;
		// 有休時間取得限度時間
		int generalWorkHour = 0;
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 付与日取得(今年+基準日)
			currentAcquisitionDate = DateUtility.getDate(DateUtility.getYear(targetDate),
					paidHolidayDto.getPointDateMonth(), paidHolidayDto.getPointDateDay());
			if (targetDate.before(currentAcquisitionDate)) {
				currentAcquisitionDate = DateUtility.addYear(currentAcquisitionDate, -1);
			}
			// 有休時間取得限度時間取得
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
		if (currentAcquisitionDate == null) {
			return map;
		}
		// 有休情報リスト取得
		List<PaidHolidayDataDtoInterface> paidHolidayDataDtoList = paidHolidayDataDao.findForInfoList(personalId,
				targetDate);
		// 有休情報リスト毎に処理
		for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataDtoList) {
			// 取得日取得
			Date acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
			// 取得日より付与日前でない場合
			if (!acquisitionDate.before(currentAcquisitionDate)) {
				// 今年度
				currentHoldDay += paidHolidayDataDto.getHoldDay();
				currentHoldTime += paidHolidayDataDto.getHoldHour();
				currentGivingDay += paidHolidayDataDto.getGivingDay();
				currentGivingTime += paidHolidayDataDto.getGivingHour();
				currentCancelDay += paidHolidayDataDto.getCancelDay();
				currentCancelTime += paidHolidayDataDto.getCancelHour();
				currentUseDay += paidHolidayDataDto.getUseDay();
				currentUseTime += paidHolidayDataDto.getUseHour();
				// 今年度付与日又は今年度期限日がない場合
				if (currentGivingDate == null || currentLimitDate == null) {
					// 設定
					currentActivateDate = paidHolidayDataDto.getActivateDate();
					currentLimitDate = paidHolidayDataDto.getLimitDate();
					currentGivingDate = paidHolidayDataDto.getAcquisitionDate();
				}
				// 既に設定されている日付より後日付の場合
				if (currentGivingDate.after(paidHolidayDataDto.getAcquisitionDate())
						|| currentLimitDate.after(paidHolidayDataDto.getLimitDate())) {
					// 設定
					currentActivateDate = paidHolidayDataDto.getActivateDate();
					currentLimitDate = paidHolidayDataDto.getLimitDate();
					currentGivingDate = paidHolidayDataDto.getAcquisitionDate();
				}
				// 手動付与・破棄
				List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
					.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
				for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
					currentGivingDay += paidHolidayTransactionDto.getGivingDay();
					currentGivingTime += paidHolidayTransactionDto.getGivingHour();
					currentCancelDay += paidHolidayTransactionDto.getCancelDay();
					currentCancelTime += paidHolidayTransactionDto.getCancelHour();
				}
				currentGrantDay = currentHoldDay + currentGivingDay - currentCancelDay;
				currentGrantHour = currentHoldTime + currentGivingTime - currentCancelTime;
				if (generalWorkHour > 0) {
					// 0より大きい場合
					while (currentGrantHour < 0 && currentGrantDay >= 1) {
						// 今年度付与時間数が0より小さく且つ今年度付与日数が1以上の場合
						currentGrantDay--;
						currentGrantHour += generalWorkHour;
					}
				}
				// 申請
				Map<String, Object> requestMap = holidayRequest.getRequestDayHour(personalId, acquisitionDate, 1,
						Integer.toString(1), paidHolidayDataDto.getActivateDate(), paidHolidayDataDto.getLimitDate());
				currentUseDay += ((Double)requestMap.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
				currentUseTime += ((Integer)requestMap.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
				currentDay = currentHoldDay + currentGivingDay - currentCancelDay - currentUseDay;
				currentTime = currentHoldTime + currentGivingTime - currentCancelTime - currentUseTime;
				if (generalWorkHour > 0) {
					while (currentTime < 0 && currentDay >= 1) {
						currentDay--;
						currentTime += generalWorkHour;
					}
				}
				map.put(TimeConst.CODE_CURRENT_ACQUISITION_DATE, acquisitionDate);
			} else if (acquisitionDate.before(currentAcquisitionDate)) {
				// 前年度以前
				formerHoldDay += paidHolidayDataDto.getHoldDay();
				formerHoldTime += paidHolidayDataDto.getHoldHour();
				formerGivingDay += paidHolidayDataDto.getGivingDay();
				formerGivingTime += paidHolidayDataDto.getGivingHour();
				formerCancelDay += paidHolidayDataDto.getCancelDay();
				formerCancelTime += paidHolidayDataDto.getCancelHour();
				formerUseDay += paidHolidayDataDto.getUseDay();
				formerUseTime += paidHolidayDataDto.getUseHour();
				// 今年度付与日又は今年度期限日がない場合
				if (formerGivingDate == null || formerLimitDate == null) {
					// 設定
					formerActivateDate = paidHolidayDataDto.getActivateDate();
					formerLimitDate = paidHolidayDataDto.getLimitDate();
					formerGivingDate = paidHolidayDataDto.getAcquisitionDate();
				}
				// 既に設定されている日付より後日付の場合
				if (formerGivingDate.after(paidHolidayDataDto.getAcquisitionDate())
						|| formerLimitDate.after(paidHolidayDataDto.getLimitDate())) {
					// 設定
					formerActivateDate = paidHolidayDataDto.getActivateDate();
					formerLimitDate = paidHolidayDataDto.getLimitDate();
					formerGivingDate = paidHolidayDataDto.getAcquisitionDate();
				}
				// 手動付与・破棄情報取得
				List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
					.findForList(personalId, acquisitionDate, paidHolidayDataDto.getActivateDate(), targetDate);
				// 手動付与・破棄情報取得毎に処理
				for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
					formerGivingDay += paidHolidayTransactionDto.getGivingDay();
					formerGivingTime += paidHolidayTransactionDto.getGivingHour();
					formerCancelDay += paidHolidayTransactionDto.getCancelDay();
					formerCancelTime += paidHolidayTransactionDto.getCancelHour();
				}
				formerGrantDay = formerHoldDay + formerGivingDay - formerCancelDay;
				formerGrantHour = formerHoldTime + formerGivingTime - formerCancelTime;
				if (generalWorkHour > 0) {
					// 0より大きい場合
					while (formerGrantHour < 0 && formerGrantDay >= 1) {
						// 前年度付与時間数が0より小さく且つ前年度付与日数が1以上の場合
						formerGrantDay--;
						formerGrantHour += generalWorkHour;
					}
				}
				// 申請
				Map<String, Object> requestMap = holidayRequest.getRequestDayHour(personalId, acquisitionDate, 1,
						Integer.toString(1), paidHolidayDataDto.getActivateDate(), paidHolidayDataDto.getLimitDate());
				formerUseDay += ((Double)requestMap.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
				formerUseTime += ((Integer)requestMap.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
				formerDay = formerHoldDay + formerGivingDay - formerCancelDay - formerUseDay;
				formerTime = formerHoldTime + formerGivingTime - formerCancelTime - formerUseTime;
				if (generalWorkHour > 0) {
					while (formerTime < 0 && formerDay >= 1) {
						formerDay--;
						formerTime += generalWorkHour;
					}
				}
				map.put(TimeConst.CODE_FORMER_ACQUISITION_DATE_DATE, acquisitionDate);
			}
		}
		map.put(TimeConst.CODE_CURRENT_ACTIVATE_DATE, currentActivateDate);
		map.put(TimeConst.CODE_CURRENT_GIVING_DATE, currentGivingDate);
		map.put(TimeConst.CODE_CURRENT_GRANT_DAY, currentGrantDay);
		map.put(TimeConst.CODE_CURRENT_GRANT_HOUR, currentGrantHour);
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, currentDay);
		map.put(TimeConst.CODE_CURRENT_TIME, currentTime);
		if (map.get(TimeConst.CODE_CURRENT_ACQUISITION_DATE) == null) {
			if (currentDay == 0) {
				map.put(TimeConst.CODE_CURRENT_YEAR_DAY, null);
			}
			if (currentTime == 0) {
				map.put(TimeConst.CODE_CURRENT_TIME, null);
			}
		}
		map.put(TimeConst.CODE_CURRENT_LIMIT_DATE, currentLimitDate);
		map.put(TimeConst.CODE_FORMER_ACTIVATE_DATE, formerActivateDate);
		map.put(TimeConst.CODE_FORMER_GIVING_DATE, formerGivingDate);
		map.put(TimeConst.CODE_FORMER_GRANT_DAY, formerGrantDay);
		map.put(TimeConst.CODE_FORMER_GRANT_HOUR, formerGrantHour);
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, formerDay);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, formerTime);
		if (map.get(TimeConst.CODE_FORMER_ACQUISITION_DATE_DATE) == null) {
			if (formerDay == 0) {
				map.put(TimeConst.CODE_FORMER_YEAR_DAY, null);
			}
			if (formerTime == 0) {
				map.put(TimeConst.CODE_FORMER_YEAR_TIME, null);
			}
		}
		map.put(TimeConst.CODE_FORMER_LIMIT_DATE, formerLimitDate);
		map.put(TimeConst.CODE_GIVING_DAY, currentGivingDay + formerGivingDay);
		map.put(TimeConst.CODE_GIVING_TIME, currentGivingTime + formerGivingTime);
		map.put(TimeConst.CODE_CANCEL_DAY, currentCancelDay + formerCancelDay);
		map.put(TimeConst.CODE_CANCEL_TIME, currentCancelTime + formerCancelTime);
//		map.put(TimeConst.CODE_USE_DAY, currentUseDate + formerUseDate);
//		map.put(TimeConst.CODE_USE_TIME, currentUseTime + formerUseTime);
		return map;
	}
	
	/**
	 * 入社日や有給休暇初年度情報を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇初年度情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayFirstYearDtoInterface getPaidHolidayFirstYearDto(String personalId, Date targetDate)
			throws MospException {
		// 入社日取得及び確認
		entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			// 該当する入社日が存在しない
			String errorMes = mospParams.getName("Joined");
			mospParams.addErrorMessage(PlatformMessageConst.MSG_EMPLOYEE_IS_NOT, errorMes);
			return null;
		}
		// 対象個人ID及び対象日付で有給休暇設定情報を取得し設定
		if (hasPaidHolidaySettings(personalId, targetDate) == false) {
			// 有給休暇情報が取得できない場合
			return null;
		}
		// 初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = firstYearDao.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		return paidHolidayFirstYearDto;
	}
	
	@Override
	public Map<String, Object> getNextGivingInfo(String personalId) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, null);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, null);
		// 
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = getPaidHolidayFirstYearDto(personalId,
				getSystemDate());
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			return getStandardDay(map, paidHolidayFirstYearDto, personalId, entranceDate);
		}
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			return getEntranceMonth(map, paidHolidayFirstYearDto, personalId, entranceDate);
		}
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			return getEntranceDay(map, paidHolidayFirstYearDto, personalId);
		}
		return map;
	}
	
	@Override
	public Map<String, Object> getNextGivingInfo(String personalId, Date targetDate) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, null);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, null);
		// 対象個人ID及び対象日付で有給休暇設定情報を取得し設定
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			// 有給休暇情報が取得できない場合
			return map;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			getStandardDayNextGivingInfo(map, personalId, targetDate);
		}
		return map;
	}
	
	/**
	 * 初年度付与日の取得
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getFirstYearGivingDate(String personalId, Date targetDate) throws MospException {
		// 有給休暇初年度情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = getPaidHolidayFirstYearDto(personalId, targetDate);
		// 有給休暇初年度情報がない場合
		if (paidHolidayFirstYearDto == null) {
			return null;
		}
		if (paidHolidayFirstYearDto.getGivingAmount() <= 0) {
			// 付与日数が0以下の場合
			return null;
		}
		// 基準日(日)取得
		int pointDay = paidHolidayDto.getPointDateDay();
		// 初年度付与日取得(入社月の基準日から付与月を加算)
		return addDay(
				DateUtility.addMonth(MonthUtility.getTargetYearMonth(entranceDate, mospParams),
						paidHolidayFirstYearDto.getGivingMonth()), pointDay - 1);
	}
	
	/**
	 * 初年度付与日を取得する。<br>
	 * @param dto 有給休暇初年度DTO
	 * @param date 入社日
	 * @return 初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getFirstYearGivingDate(PaidHolidayFirstYearDtoInterface dto, Date date) throws MospException {
		// 有給休暇初年度情報がない場合
		if (dto == null) {
			return null;
		}
		if (dto.getGivingAmount() <= 0) {
			// 付与日数が0以下の場合
			return null;
		}
		// 初年度付与日取得(入社月の基準日から付与月を加算)
		return addDay(DateUtility.addMonth(MonthUtility.getTargetYearMonth(date, mospParams), dto.getGivingMonth()),
				paidHolidayDto.getPointDateDay() - 1);
	}
	
	/**
	 * 前年度付与日を取得する。<br>
	 * @param personalId 個人ID
	 * @param cutoffDate 締日
	 * @return 前年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDateOfPreviousFiscalYear(String personalId, Date cutoffDate) throws MospException {
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			Date firstYearGivingDate = getFirstYearGivingDate(personalId, cutoffDate);
			if (firstYearGivingDate != null && cutoffDate.before(firstYearGivingDate)) {
				// 締日が初年度付与日より前の場合
				return null;
			}
			// 次年度以降の場合
			// 基準日
			int pointMonth = paidHolidayDto.getPointDateMonth();
			int pointDay = paidHolidayDto.getPointDateDay();
			// 基準日準備
			Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), pointMonth, pointDay);
			while (!entranceDate.before(pointDate)) {
				// 入社日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 初年度付与日確認
			while (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
				// 初年度付与日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 基準日経過回数準備
			int count = 2;
			List<Date> list = new ArrayList<Date>();
			list.add(pointDate);
			// 基準日経過回数設定
			while (!cutoffDate.before(pointDate)) {
				// 締日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
				count++;
				list.add(pointDate);
			}
			if (count == 2) {
				// 次年度の場合
				return firstYearGivingDate;
			}
			// 次々年度以降の場合
			return list.get(count - 3);
		}
		return null;
	}
	
	/**
	 * 初年度付与日かどうか判断。
	 * @param personalId 個人ID
	 * @param cutoffDate 締日
	 * @param acquisitionDate 取得日
	 * @return 初年度付与日の場合はtrue、そうでない場合はfalse
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isGrantDateOfFirstFiscalYear(String personalId, Date cutoffDate, Date acquisitionDate)
			throws MospException {
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			Date firstYearGivingDate = getFirstYearGivingDate(personalId, cutoffDate);
			if (firstYearGivingDate != null && !acquisitionDate.after(firstYearGivingDate)) {
				// 取得日が初年度付与日より後でない場合は初年度とする
				return true;
			}
			// 次年度以降の場合
			return false;
		}
		return false;
	}
	
	/**
	 * 基準日の取得
	 * @param map マップ
	 * @param paidHolidayFirstYearDto 有給休暇初年度情報
	 * @param personalId 個人ID
	 * @param entranceDate 入社日
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getStandardDay(Map<String, Object> map,
			PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto, String personalId, Date entranceDate)
			throws MospException {
		// 初年度付与日準備
		Date firstYearGivingDate = getFirstYearGivingDate(personalId, getSystemDate());
		// 初年度付与日確認
		if (firstYearGivingDate != null && getSystemDate().before(firstYearGivingDate)) {
			// 初年度の場合
			map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, firstYearGivingDate);
			map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, (double)paidHolidayFirstYearDto.getGivingAmount());
			map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
			map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE,
					addDay(DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
			return map;
		}
		// 次年度付与日
		Date nextYearGivingDate = null;
		// 次年度付与数
		double nextYearDay = -1;
		// 次年度期限日
		Date nextYearLimitDate = null;
		// 基準日
		int pointMonth = paidHolidayDto.getPointDateMonth();
		int pointDay = paidHolidayDto.getPointDateDay();
		// 基準日準備
		Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), pointMonth, pointDay);
		// 入社日が基準日より前でない場合
		if (!entranceDate.before(pointDate)) {
			// 基準日に1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		// 初年度付与日確認
		if (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
			// 初年度付与日が基準日より前でない場合は1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
//		--------------------
		// 基準日経過回数準備
		int count = 2;
//		--------------------
		// 基準日経過回数設定
		while (!pointDate.after(getSystemDate())) {
			// 基準日が締日より後でない場合は1年加算
			pointDate = DateUtility.addYear(pointDate, 1);
//			--------------------
			count++;
//			--------------------
		}
		nextYearGivingDate = pointDate;
		// 有休繰越取得
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
		}
		//	--------------------
		// 基準日経過回数から有給休暇基準日管理情報を取得
		PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDao.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), count);
		if (paidHolidayPointDateDto == null) {
			// 登録情報超過後
			nextYearDay = paidHolidayDto.getGeneralPointAmount();
		} else {
			nextYearDay = paidHolidayPointDateDto.getPointDateAmount();
		}
//		--------------------
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	/**
	 * 基準日が入社月の場合の取得
	 * @param map マップ
	 * @param paidHolidayFirstYearDto 有休初年度情報
	 * @param personalId 個人ID
	 * @param entranceDate 入社日
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getEntranceMonth(Map<String, Object> map,
			PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto, String personalId, Date entranceDate)
			throws MospException {
		// 入社年・入社月取得
		int entranceYear = DateUtility.getYear(entranceDate);
		int entranceMonth = DateUtility.getMonth(entranceDate);
		// 次年度付与日
		Date nextYearGivingDate = null;
		// 次年度付与数
		double nextYearDay = -1;
		// 次年度付与時間
		int nextYearTime = 0;
		// 次年度期限日
		Date nextYearLimitDate = null;
		// 入社月
		// 対象個人ID及び対象日付で締日情報を取得し設定
		if (!hasCutoffSettings(personalId, getSystemDate())) {
			// 締日情報が取得できない場合
			return null;
		}
		Date pointDate = addDay(MonthUtility.getYearMonthTermLastDate(entranceYear, entranceMonth, mospParams), 1);
		if (cutoffDto.getCutoffDate() != 0) {
			// 締日が末日でない場合
			pointDate = addDay(DateUtility.getDate(entranceYear, entranceMonth, cutoffDto.getCutoffDate()), 1);
		}
		// 初年度付与日準備
		Date firstYearGivingDate = null;
		if (paidHolidayFirstYearDto != null) {
			// 初年度付与マスタが存在する場合
			int amount = paidHolidayFirstYearDto.getGivingAmount();
			if (amount > 0) {
				// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
				// 初年度付与日取得(入社月の締日の翌日から付与月を加算)
				firstYearGivingDate = DateUtility.addMonth(pointDate, paidHolidayFirstYearDto.getGivingMonth());
				// 初年度付与日確認
				if (getSystemDate().before(firstYearGivingDate)) {
					nextYearGivingDate = firstYearGivingDate;
					// 初年度の利用期限
					nextYearLimitDate = addDay(
							DateUtility.addMonth(nextYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
					nextYearDay = amount;
				}
			}
		}
		Date givingDate = null;
		Date maxDate = pointDate;
		int amount = 0;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDateDao.findForList(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(pointDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (firstYearGivingDate != null && !firstYearGivingDate.before(workDate)) {
				continue;
			}
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			if (workDate.after(getSystemDate()) && (givingDate == null || givingDate.after(workDate))) {
				givingDate = workDate;
				amount = paidHolidayEntranceDateDto.getJoiningDateAmount();
			}
		}
		if (givingDate == null) {
			// 登録情報最大まで経過後
			int generalJoiningMonth = paidHolidayDto.getGeneralJoiningMonth();
			if (generalJoiningMonth == 0) {
				return null;
			}
			if (nextYearDay == -1) {
				nextYearDay = paidHolidayDto.getGeneralJoiningAmount();
			}
			while (!maxDate.after(getSystemDate())) {
				maxDate = DateUtility.addMonth(maxDate, generalJoiningMonth);
			}
			if (nextYearGivingDate == null) {
				nextYearGivingDate = maxDate;
			}
			if (nextYearLimitDate == null) {
				if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
					// 有休繰越が有効の場合は期限は2年
					nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
				} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
					// 有休繰越が無効の場合は期限は1年
					nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
				}
			}
		}
		if (nextYearDay == -1) {
			nextYearDay = amount;
		}
		if (nextYearGivingDate == null) {
			nextYearGivingDate = givingDate;
		}
		if (nextYearLimitDate == null) {
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				// 有休繰越が有効の場合は期限は2年
				nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
			} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
				// 有休繰越が無効の場合は期限は1年
				nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
			}
		}
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	/**
	 * 入社日の時
	 * @param map マップ
	 * @param paidHolidayFirstYearDto 有給休暇初年度情報
	 * @param personalId 個人ID
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getEntranceDay(Map<String, Object> map,
			PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto, String personalId) throws MospException {
		// 次年度付与日
		Date nextYearGivingDate = null;
		// 次年度付与数
		double nextYearDay = -1;
		// 次年度付与時間
		int nextYearTime = 0;
		// 次年度期限日
		Date nextYearLimitDate = null;
		// 入社日
		// 初年度付与日準備
		Date firstYearGivingDate = null;
		if (paidHolidayFirstYearDto != null) {
			// 初年度付与マスタが存在する場合
			int amount = paidHolidayFirstYearDto.getGivingAmount();
			if (amount > 0) {
				// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
				// 初年度付与日取得(入社日から付与月を加算)
				firstYearGivingDate = DateUtility.addMonth(entranceDate, paidHolidayFirstYearDto.getGivingMonth());
				// 初年度付与日確認
				if (getSystemDate().before(firstYearGivingDate)) {
					nextYearGivingDate = firstYearGivingDate;
					// 初年度の利用期限
					nextYearLimitDate = addDay(
							DateUtility.addMonth(nextYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
					nextYearDay = amount;
				}
			}
		}
		Date givingDate = null;
		int amount = 0;
		Date maxDate = entranceDate;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDateDao.findForList(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (firstYearGivingDate != null && !firstYearGivingDate.before(workDate)) {
				continue;
			}
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			if (workDate.after(getSystemDate()) && (givingDate == null || givingDate.after(workDate))) {
				givingDate = workDate;
				amount = paidHolidayEntranceDateDto.getJoiningDateAmount();
			}
		}
		if (givingDate == null) {
			// 登録情報最大まで経過後
			int generalJoiningMonth = paidHolidayDto.getGeneralJoiningMonth();
			if (generalJoiningMonth == 0) {
				return null;
			}
			if (nextYearDay == -1) {
				nextYearDay = paidHolidayDto.getGeneralJoiningAmount();
			}
			while (!maxDate.after(getSystemDate())) {
				maxDate = DateUtility.addMonth(maxDate, generalJoiningMonth);
			}
			if (nextYearGivingDate == null) {
				nextYearGivingDate = maxDate;
			}
			if (nextYearLimitDate == null) {
				if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
					// 有休繰越が有効の場合は期限は2年
					nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
				} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
					// 有休繰越が無効の場合は期限は1年
					nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
				}
			}
		}
		if (nextYearDay == -1) {
			nextYearDay = amount;
		}
		if (nextYearGivingDate == null) {
			nextYearGivingDate = givingDate;
		}
		if (nextYearLimitDate == null) {
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				// 有休繰越が有効の場合は期限は2年
				nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
			} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
				// 有休繰越が無効の場合は期限は1年
				nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
			}
		}
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	/**
	 * 有給休暇次回付与予定を取得する(基準日)。
	 * @param map 対象Map
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void getStandardDayNextGivingInfo(Map<String, Object> map, String personalId, Date targetDate)
			throws MospException {
		// 入社日取得
		Date userEntranceDate = entranceRefer.getEntranceDate(personalId);
		if (userEntranceDate == null) {
			return;
		}
		// 初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = paidHolidayFirstYearReference.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(userEntranceDate));
		
		// 初年度付与日準備
		Date firstYearGivingDate = getFirstYearGivingDate(paidHolidayFirstYearDto, userEntranceDate);
		// 初年度付与日確認
		if (firstYearGivingDate != null && targetDate.before(firstYearGivingDate)) {
			// 初年度の場合
			map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, firstYearGivingDate);
			map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, (double)paidHolidayFirstYearDto.getGivingAmount());
			map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
			map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE,
					addDay(DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
			return;
		}
		// 基準年
		int pointYear = DateUtility.getYear(userEntranceDate);
		// 基準月
		int pointMonth = paidHolidayDto.getPointDateMonth();
		// 基準日
		int pointDay = paidHolidayDto.getPointDateDay();
		// 基準日付
		Date pointDate = DateUtility.getDate(pointYear, pointMonth, pointDay);
		// 入社日が基準日付より前でない場合
		while (!userEntranceDate.before(pointDate)) {
			// 基準日に1年を加算する
			pointYear++;
			pointDate = DateUtility.getDate(pointYear, pointMonth, pointDay);
		}
		// 初年度付与日確認
		while (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
			// 初年度付与日が基準日より前でない場合は1年を加算する
			pointYear++;
			pointDate = DateUtility.getDate(pointYear, pointMonth, pointDay);
		}
		// 基準日経過回数準備
		int count = 2;
		// 基準日経過回数設定
		while (!pointDate.after(targetDate)) {
			// 基準日付が対象日より後でない場合は1年加算
			pointYear++;
			pointDate = DateUtility.getDate(pointYear, pointMonth, pointDay);
			count++;
		}
		// 次年度付与日
		Date nextYearGivingDate = pointDate;
		int addLimitYear = 0;
		// 有休繰越取得
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			addLimitYear = 2;
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			addLimitYear = 1;
		} else {
			return;
		}
		// 次年度期限日
		Date nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, addLimitYear), -1);
		// 次年度付与数
		double nextYearDay = paidHolidayDto.getGeneralPointAmount();
		//	--------------------
		// 基準日経過回数から有給休暇基準日管理情報を取得
		PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDateReference.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), count);
		if (paidHolidayPointDateDto != null) {
			nextYearDay = paidHolidayPointDateDto.getPointDateAmount();
		}
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
	}
	
	@Override
	public Date getNextManualGivingDate(String personalId) throws MospException {
		// 個人ID及びシステム日付で有給休暇トランザクション情報リストを取得
		List<PaidHolidayTransactionDtoInterface> list = paidHolidayTransactionDao.findForNextGiving(personalId,
				getSystemDate());
		// リスト確認
		if (list.isEmpty()) {
			return null;
		}
		// 直近の有給休暇トランザクション情報から付与日を取得
		return list.get(0).getActivateDate();
	}
	
	@Override
	public String getNextManualGivingDaysAndHours(String personalId) throws MospException {
		// 次回付与予定日を取得
		Date activateDate = getNextManualGivingDate(personalId);
		if (activateDate == null) {
			return null;
		}
		// 直近の有給休暇トランザクション情報を取得
		List<PaidHolidayTransactionDtoInterface> list = paidHolidayTransactionDao.findForList(personalId, activateDate);
		// 日数及び時間を準備
		double givingDays = 0D;
		int givingHours = 0;
		// 日数及び時間を加算
		for (PaidHolidayTransactionDtoInterface dto : list) {
			givingDays += dto.getGivingDay();
			givingDays -= dto.getCancelDay();
			givingHours += dto.getGivingHour();
			givingHours -= dto.getCancelHour();
		}
		// 日数及び時間を文字列に変換
		StringBuffer sb = new StringBuffer();
		sb.append(getStringDays(givingDays));
		sb.append(mospParams.getName("Day"));
		if (givingHours != 0) {
			sb.append(givingHours);
			sb.append(mospParams.getName("Time"));
		}
		return sb.toString();
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayNextMonthInfo(String personalId, Date cutoffDate,
			int calculationYear, int calculationMonth, List<PaidHolidayDataDtoInterface> list) throws MospException {
		// 次月リスト準備
		List<PaidHolidayDataDtoInterface> paidHolidayDataList = new ArrayList<PaidHolidayDataDtoInterface>();
		// 有給休暇データリスト毎に処理
		for (PaidHolidayDataDtoInterface dto : list) {
			// 期限日が締日後の場合
			if (dto.getLimitDate().after(cutoffDate)) {
				// 取得日
				Date acquisitionDate = dto.getAcquisitionDate();
				// 仮付与フラグが有効かつ締日が取得日の前かつ締日次の日が取得日の前の場合
				if (dto.getTemporaryFlag() == 0 && cutoffDate.before(acquisitionDate)
						&& !DateUtility.addMonth(cutoffDate, 1).before(acquisitionDate)) {
					// 出勤率が下回っていた場合
					if (!isOverRatioForBaseDate(personalId, cutoffDate, calculationYear, calculationMonth,
							isGrantDateOfFirstFiscalYear(personalId, cutoffDate, dto.getAcquisitionDate()) ? 1 : 2)) {
						// 保有日数0設定
						dto.setHoldDay(0);
					}
					// 仮付与フラグを無効とする
					dto.setTemporaryFlag(1);
				}
				// 有効日が締日より後でない場合
				if (!dto.getActivateDate().after(cutoffDate)) {
					// 有効日に締日の翌日をセットする
					dto.setActivateDate(DateUtility.addDay(cutoffDate, 1));
				}
				// 分母準備
				int denominator = 0;
				if (hasPaidHolidaySettings(personalId, dto.getActivateDate())) {
					// 分母取得
					denominator = paidHolidayDto.getTimeAcquisitionLimitTimes();
				}
				if (mospParams.hasErrorMessage()) {
					return null;
				}
				// 全保有日数
				double day = dto.getHoldDay() + dto.getGivingDay() - dto.getCancelDay() - dto.getUseDay();
				// 整数の日数取得
//				int intDay = (int)day;
				// 日数の小数点以下を取得
//				double doubleDay = day - intDay;
				// (保有日数 * 時間変換日分母) + 保有時間-破棄・使用時間
//				int time = (intDay * denominator) + dto.getHoldHour() + dto.getGivingHour() - dto.getCancelHour()
//						- dto.getUseHour();
				int time = dto.getHoldHour() + dto.getGivingHour() - dto.getCancelHour() - dto.getUseHour();
				if (denominator > 0) {
					while (time < 0 && day >= 1) {
						day--;
						time += denominator;
					}
				}
				// 保有日数
//				intDay = time / denominator;
				// 整数の日数+小数点以下取得
//				day = intDay + doubleDay;
				// 保有時間
//				time = time % denominator;
				// 設定
				dto.setHoldDay(day);
				dto.setHoldHour(time);
				dto.setGivingDay(0);
				dto.setGivingHour(0);
				dto.setCancelDay(0);
				dto.setCancelHour(0);
				dto.setDenominatorDayHour(denominator);
				dto.setUseDay(0);
				dto.setUseHour(0);
				paidHolidayDataList.add(dto);
			}
		}
		return paidHolidayDataList;
	}
	
	@Override
	public Double getExpirationDay(List<PaidHolidayDataDtoInterface> list, Date cutoffDate) {
		for (PaidHolidayDataDtoInterface dto : list) {
			if (dto.getLimitDate().compareTo(cutoffDate) <= 0) {
				return dto.getHoldDay() + dto.getGivingDay() - dto.getCancelDay() - dto.getUseDay();
			}
		}
		return null;
	}
	
	@Override
	public PaidHolidayDataDtoInterface getNewPaidHolidayInfo(String personalId, Date cutoffLastDate,
			int calculationYear, int calculationMonth) throws MospException {
		// 初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = getPaidHolidayFirstYearDto(personalId,
				cutoffLastDate);
		// 有給休暇データ準備
		PaidHolidayDataDtoInterface dto = paidHolidayDataRegist.getInitDto();
		// 有給休暇データ初期設定
		dto.setPersonalId(personalId);
		dto.setHoldDay(0);
		dto.setHoldHour(0);
		dto.setGivingDay(0);
		dto.setGivingHour(0);
		dto.setCancelDay(0);
		dto.setCancelHour(0);
		dto.setUseDay(0);
		dto.setUseHour(0);
		dto.setDenominatorDayHour(paidHolidayDto.getTimeAcquisitionLimitTimes());
		dto.setTemporaryFlag(1);
		// 仮付与日確認
		if (paidHolidayDto.getScheduleGiving() != 0) {
			// 仮付与日が0でない場合は仮付与フラグを有効にする
			dto.setTemporaryFlag(0);
		}
		int limit = 1;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			limit = 1;
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			limit = 2;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日
			Date firstYearGivingDate = getFirstYearGivingDate(personalId, cutoffLastDate);
			Integer grant = getNumberOfYearsFromEntranceForPaidHolidayGrant(personalId, cutoffLastDate);
			if (grant == null) {
				return null;
			} else if (grant.intValue() <= 0) {
				return null;
			} else if (grant.intValue() == 1) {
				// 初年度の場合
				// 締期間最終日が仮付与日より前の場合
				dto.setActivateDate(firstYearGivingDate);
				dto.setAcquisitionDate(firstYearGivingDate);
				dto.setLimitDate(addDay(
						DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
				dto.setHoldDay(paidHolidayFirstYearDto.getGivingAmount());
				dto.setHoldHour(0);
				if (dto.getTemporaryFlag() == 1
						&& !isOverRatioForBaseDate(personalId, cutoffLastDate, calculationYear, calculationMonth, 1)) {
					// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
					dto.setHoldDay(0);
					dto.setHoldHour(0);
				}
				return dto;
			}
			// 入社年・入社月取得
			int entranceYear = DateUtility.getYear(entranceDate);
			int entranceMonth = DateUtility.getMonth(entranceDate);
			// 次年度以降の場合
			// 基準日準備
			Date pointDate = DateUtility.getDate(entranceYear, paidHolidayDto.getPointDateMonth(),
					paidHolidayDto.getPointDateDay());
			if (!entranceDate.before(pointDate)) {
				// 入社日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 初年度付与日確認
			if (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
				// 初年度付与日が基準日より前でない場合は1年加算
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 基準日経過回数準備
			int count = 2;
			// 基準日経過回数設定
			while (!pointDate.after(cutoffLastDate)) {
				// 基準日が締日より後でない場合は1年加算
				pointDate = DateUtility.addYear(pointDate, 1);
				count++;
			}
			dto.setActivateDate(pointDate);
			dto.setAcquisitionDate(pointDate);
			dto.setLimitDate(addDay(DateUtility.addYear(pointDate, limit), -1));
			dto.setHoldDay(paidHolidayDto.getGeneralPointAmount());
			// 基準日経過回数から有給休暇基準日管理情報を取得
			PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDao.findForKey(
					paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), grant);
			if (paidHolidayPointDateDto != null) {
				dto.setHoldDay(paidHolidayPointDateDto.getPointDateAmount());
			}
			dto.setHoldHour(0);
			if (dto.getTemporaryFlag() == 1
					&& !isOverRatioForBaseDate(personalId, cutoffLastDate, calculationYear, calculationMonth, grant)) {
				// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
				dto.setHoldDay(0);
				dto.setHoldHour(0);
			}
			return dto;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月
			// 対象個人ID及び対象日付で締日情報を取得し設定
			if (!hasCutoffSettings(personalId, cutoffLastDate)) {
				// 締日情報が取得できない場合
				return null;
			}
			// 入社年・入社月取得
			int entranceYear = DateUtility.getYear(entranceDate);
			int entranceMonth = DateUtility.getMonth(entranceDate);
			Date pointDate = addDay(MonthUtility.getYearMonthTermLastDate(entranceYear, entranceMonth, mospParams), 1);
			int cutoffDate = cutoffDto.getCutoffDate();
			if (cutoffDate != 0) {
				// 締日が末日でない場合
				pointDate = addDay(DateUtility.getDate(entranceYear, entranceMonth, cutoffDate), 1);
			}
			// 初年度付与日準備
			Date firstYearGivingDate = null;
			if (paidHolidayFirstYearDto != null) {
				// 初年度付与マスタが存在する場合
				int amount = paidHolidayFirstYearDto.getGivingAmount();
				if (amount > 0) {
					// 付与日数が0より大きい場合
					// 初年度付与日取得(入社月の締日の翌日から付与月を加算)
					firstYearGivingDate = DateUtility.addMonth(pointDate, paidHolidayFirstYearDto.getGivingMonth());
					// 仮付与日準備
					Date temporaryDate = DateUtility.addMonth(firstYearGivingDate, -paidHolidayDto.getScheduleGiving());
					// 締期間最終日及び仮付与日確認
					if (DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
						return null;
					} else if (cutoffLastDate.before(temporaryDate)) {
						dto.setActivateDate(firstYearGivingDate);
						dto.setAcquisitionDate(firstYearGivingDate);
						dto.setLimitDate(addDay(
								DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
						dto.setHoldDay(amount);
						dto.setHoldHour(0);
						if (dto.getTemporaryFlag() == 1
								&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
							// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
							dto.setHoldDay(0);
							dto.setHoldHour(0);
						}
						return dto;
					}
				}
			}
			Date givingDate = null;
			Date maxDate = pointDate;
			Date maxTemporaryDate = null;
			int amount = 0;
			List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDateDao.findForList(
					paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				Date workDate = DateUtility.addMonth(pointDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (firstYearGivingDate != null && !firstYearGivingDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				// 仮付与日準備
				Date temporaryDate = DateUtility.addMonth(workDate, -paidHolidayDto.getScheduleGiving());
				if (maxTemporaryDate == null || maxTemporaryDate.before(temporaryDate)) {
					maxTemporaryDate = temporaryDate;
				}
				// 締期間最終日及び仮付与日確認
				if (cutoffLastDate.before(temporaryDate)
						&& !DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
					if (givingDate == null || givingDate.after(workDate)) {
						givingDate = workDate;
						amount = paidHolidayEntranceDateDto.getJoiningDateAmount();
					}
				}
			}
			if (givingDate == null) {
				if (maxTemporaryDate != null && !DateUtility.addMonth(cutoffLastDate, 1).before(maxTemporaryDate)) {
					return null;
				}
				// 登録情報最大まで経過後
				int generalJoiningMonth = paidHolidayDto.getGeneralJoiningMonth();
				if (generalJoiningMonth == 0) {
					return null;
				}
				// 仮付与日準備
				Date temporaryDate = DateUtility.addMonth(maxDate, -paidHolidayDto.getScheduleGiving());
				// 締期間最終日及び仮付与日確認
				if (!temporaryDate.before(DateUtility.addMonth(cutoffLastDate, 1))) {
					return null;
				}
				while (!cutoffLastDate.before(temporaryDate)
						|| DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
					maxDate = DateUtility.addMonth(maxDate, generalJoiningMonth);
					temporaryDate = DateUtility.addMonth(maxDate, -paidHolidayDto.getScheduleGiving());
					if (!temporaryDate.before(DateUtility.addMonth(cutoffLastDate, 1))) {
						return null;
					}
				}
				dto.setActivateDate(maxDate);
				dto.setAcquisitionDate(maxDate);
				dto.setLimitDate(addDay(DateUtility.addYear(maxDate, limit), -1));
				dto.setHoldDay(paidHolidayDto.getGeneralJoiningAmount());
				dto.setHoldHour(0);
				if (dto.getTemporaryFlag() == 1
						&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
					// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
					dto.setHoldDay(0);
					dto.setHoldHour(0);
				}
				return dto;
			}
			dto.setActivateDate(givingDate);
			dto.setAcquisitionDate(givingDate);
			dto.setLimitDate(addDay(DateUtility.addYear(givingDate, limit), -1));
			dto.setHoldDay(amount);
			dto.setHoldHour(0);
			if (dto.getTemporaryFlag() == 1
					&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
				// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
				dto.setHoldDay(0);
				dto.setHoldHour(0);
			}
			return dto;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日
			// 初年度付与日準備
			Date firstYearGivingDate = null;
			// 初年度付与マスタが存在する場合
			if (paidHolidayFirstYearDto != null) {
				// 付与日数取得
				int amount = paidHolidayFirstYearDto.getGivingAmount();
				// 付与日数が0より大きい場合
				if (amount > 0) {
					// 初年度付与日取得(入社日から付与月を加算)
					firstYearGivingDate = DateUtility.addMonth(entranceDate, paidHolidayFirstYearDto.getGivingMonth());
					// 仮付与日準備
					Date temporaryDate = DateUtility.addMonth(firstYearGivingDate, -paidHolidayDto.getScheduleGiving());
					// 締期間最終日及び仮付与日確認
					if (DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
						return null;
					} else if (cutoffLastDate.before(temporaryDate)) {
						dto.setActivateDate(firstYearGivingDate);
						dto.setAcquisitionDate(firstYearGivingDate);
						dto.setLimitDate(addDay(
								DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
						dto.setHoldDay(amount);
						dto.setHoldHour(0);
						if (dto.getTemporaryFlag() == 1
								&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
							// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
							dto.setHoldDay(0);
							dto.setHoldHour(0);
						}
						return dto;
					}
				}
			}
			
			Date givingDate = null;
			Date maxDate = entranceDate;
			Date maxTemporaryDate = null;
			int amount = 0;
			// 有給休暇入社日管理情報リストを取得
			List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDateDao.findForList(
					paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
			// 有給休暇入社日管理情報リスト毎に処理
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				// 
				Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (firstYearGivingDate != null && !firstYearGivingDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				// 仮付与日準備
				Date temporaryDate = DateUtility.addMonth(workDate, -paidHolidayDto.getScheduleGiving());
				if (maxTemporaryDate == null || maxTemporaryDate.before(temporaryDate)) {
					maxTemporaryDate = temporaryDate;
				}
				// 締期間最終日及び仮付与日確認
				if (cutoffLastDate.before(temporaryDate)
						&& !DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
					if (givingDate == null || givingDate.after(workDate)) {
						givingDate = workDate;
						amount = paidHolidayEntranceDateDto.getJoiningDateAmount();
					}
				}
			}
			if (givingDate == null) {
				if (maxTemporaryDate != null && !DateUtility.addMonth(cutoffLastDate, 1).before(maxTemporaryDate)) {
					return null;
				}
				// 登録情報最大まで経過後
				int generalJoiningMonth = paidHolidayDto.getGeneralJoiningMonth();
				if (generalJoiningMonth == 0) {
					return null;
				}
				// 仮付与日準備
				Date temporaryDate = DateUtility.addMonth(maxDate, -paidHolidayDto.getScheduleGiving());
				// 締期間最終日及び仮付与日確認
				if (!temporaryDate.before(DateUtility.addMonth(cutoffLastDate, 1))) {
					return null;
				}
				while (!cutoffLastDate.before(temporaryDate)
						|| DateUtility.addMonth(cutoffLastDate, 1).before(temporaryDate)) {
					maxDate = DateUtility.addMonth(maxDate, generalJoiningMonth);
					temporaryDate = DateUtility.addMonth(maxDate, -paidHolidayDto.getScheduleGiving());
					if (!temporaryDate.before(DateUtility.addMonth(cutoffLastDate, 1))) {
						return null;
					}
				}
				dto.setActivateDate(maxDate);
				dto.setAcquisitionDate(maxDate);
				dto.setLimitDate(addDay(DateUtility.addYear(maxDate, limit), -1));
				dto.setHoldDay(paidHolidayDto.getGeneralJoiningAmount());
				dto.setHoldHour(0);
				if (dto.getTemporaryFlag() == 1
						&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
					// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
					dto.setHoldDay(0);
					dto.setHoldHour(0);
				}
				return dto;
			}
			dto.setActivateDate(givingDate);
			dto.setAcquisitionDate(givingDate);
			dto.setLimitDate(addDay(DateUtility.addYear(givingDate, limit), -1));
			dto.setHoldDay(amount);
			dto.setHoldHour(0);
			if (dto.getTemporaryFlag() == 1
					&& !isOverRatio(personalId, cutoffLastDate, calculationYear, calculationMonth)) {
				// 仮付与フラグが無効で且つ出勤率が下回っている場合は保有数に0をセットする
				dto.setHoldDay(0);
				dto.setHoldHour(0);
			}
			return dto;
		}
		return null;
	}
	
	@Override
	public PaidHolidayDataDtoInterface getNewPaidHolidayInfo(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
		if (hasCutoffSettings(personalId,
				MonthUtility.getYearMonthTargetDate(calculationYear, calculationMonth, mospParams))) {
			return getNewPaidHolidayInfo(personalId, cutoffUtil.getCutoffLastDate(
					cutoffUtil.getCutoff(cutoffDto.getCutoffCode(), calculationYear, calculationMonth).getCutoffCode(),
					calculationYear, calculationMonth), calculationYear, calculationMonth);
		}
		return null;
	}
	
	@Override
	public Integer getNumberOfYearsFromEntranceForPaidHolidayGrant(String personalId, Date cutoffDate)
			throws MospException {
		if (!hasPaidHolidaySettings(personalId, cutoffDate)) {
			// 有給休暇情報が取得できない場合
			return null;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 初年度付与日準備
			Date firstYearGivingDate = getFirstYearGivingDate(personalId, cutoffDate);
			if (firstYearGivingDate != null) {
				// 仮付与日準備
				Date temporaryDate = DateUtility.addMonth(firstYearGivingDate, -paidHolidayDto.getScheduleGiving());
				// 締期間最終日及び仮付与日確認
				if (DateUtility.addMonth(cutoffDate, 1).before(temporaryDate)) {
					// 締期間最終日の1ヶ月後が仮付与日より前の場合
					return null;
				} else if (cutoffDate.before(temporaryDate)) {
					// 締期間最終日が仮付与日より前の場合
					return 1;
				}
			}
			// 次年度以降の場合
			// 基準日
			int pointMonth = paidHolidayDto.getPointDateMonth();
			int pointDay = paidHolidayDto.getPointDateDay();
			// 基準日準備
			Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), pointMonth, pointDay);
			while (!entranceDate.before(pointDate)) {
				// 入社日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 初年度付与日確認
			while (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
				// 初年度付与日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
			}
			// 基準日経過回数準備
			int count = 2;
			// 基準日経過回数設定
			while (!cutoffDate.before(pointDate)) {
				// 締日が基準日より前でない場合は基準日に1年を加算する
				pointDate = DateUtility.addYear(pointDate, 1);
				count++;
			}
			// 仮付与日準備
			Date temporaryDate = DateUtility.addMonth(pointDate, -paidHolidayDto.getScheduleGiving());
			// 締期間最終日及び仮付与日確認
			if (cutoffDate.before(temporaryDate) && !DateUtility.addMonth(cutoffDate, 1).before(temporaryDate)) {
				// 締期間最終日が仮付与日より前且つ締期間最終日の1ヶ月後が仮付与日より前でない場合
				return count;
			}
			return null;
		}
		return null;
	}
	
	@Override
	public int[] getHolidayTimeUnitLimit(String personalId, Date targetDate, boolean isStatus,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException {
		// int配列準備
		int[] limitDayTime = { 0, 0, 0 };
		// 設定有給休暇情報を取得、設定
		if (hasPaidHolidaySettings(personalId, targetDate) == false) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORKFORM_EXISTENCE,
					mospParams.getName("PaidVacation", "Set"));
			return limitDayTime;
		}
		// 今年度基準日取得
		Date firstDate = DateUtility.getDate(DateUtility.getYear(targetDate), paidHolidayDto.getPointDateMonth(),
				paidHolidayDto.getPointDateDay());
		// 対象日前の場合
		if (firstDate.after(targetDate)) {
			// 基準日-1年
			firstDate = DateUtility.addYear(firstDate, -1);
		}
		// 入社日取得及び確認
//		Date entranceDate = entranceRefer.getEntranceDate(personalId);
//		if (entranceDate == null) {
//			// 該当する入社日が存在しない
//			String errorMes = mospParams.getName("Joined");
//			mospParams.addErrorMessage(PlatformMessageConst.MSG_EMPLOYEE_IS_NOT, errorMes, null);
//			return limitDayTime;
//		}
		// 入社日が今年度基準日後の場合
//		if (entranceDate.after(firstDate)) {
//			firstDate = entranceDate;
//		}
		// 翌年基準日1日前を取得
		Date lastDate = addDay(DateUtility.addYear(firstDate, 1), -1);
		// 有休時間取得限度取得
		int limitDay = paidHolidayDto.getTimeAcquisitionLimitDays();
		int limitTime = paidHolidayDto.getTimeAcquisitionLimitTimes();
		// 時間休が有効でない場合
		if (paidHolidayDto.getTimelyPaidHolidayFlag() != 0) {
			return limitDayTime;
		}
		// 時間休限度準備
		int time = limitDay * limitTime;
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> holidayList = holidayRequest.getHolidayRequestListOnWorkflow(personalId,
				firstDate, lastDate);
		for (HolidayRequestDtoInterface dto : holidayList) {
			// 時間休でない場合
			if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				continue;
			}
			if (holidayRequestDto != null && holidayRequestDto.getTmdHolidayRequestId() == dto.getTmdHolidayRequestId()) {
				// レコード識別IDが同じ場合
				continue;
			}
			// 承認済の場合
			if (isStatus && workflowIntegrate.isCompleted(dto.getWorkflow())) {
				time--;
			}
			if (!isStatus && workflowIntegrate.isWithDrawn(dto.getWorkflow()) == false
					&& workflowIntegrate.isDraft(dto.getWorkflow()) == false) {
				// 取下・下書でない場合
				time--;
			}
		}
		limitDayTime[0] = time / limitTime;
		limitDayTime[1] = time % limitTime;
		limitDayTime[2] = limitTime;
		// 残時間取得
		return limitDayTime;
	}
	
	/**
	 * 設定した出勤率以上かどうか判断(基準日)。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param grant 付与回数
	 * @return 出勤率以上の場合はtrue、そうでない場合はfalse
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isOverRatioForBaseDate(String personalId, Date activateDate, int calculationYear,
			int calculationMonth, int grant) throws MospException {
		if (paidHolidayDto.getWorkRatio() == 0) {
			// 出勤率が0%の場合
			return true;
		}
		// 出勤率が0%でなく且つ全労働日が0日でない場合
		double percentage = getAttendanceRatio(personalId, activateDate, calculationYear, calculationMonth, grant);
		if (percentage <= 0) {
			return false;
		}
		return percentage >= paidHolidayDto.getWorkRatio();
	}
	
	/**
	 * 設定した出勤率以上かどうか判断。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @return 出勤率以上の場合はtrue、そうでない場合はfalse
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isOverRatio(String personalId, Date activateDate, int calculationYear, int calculationMonth)
			throws MospException {
		// 対象個人ID及び対象日付で有給休暇設定情報を取得し設定
		if (hasPaidHolidaySettings(personalId, activateDate) == false) {
			// 有給休暇情報が取得できない場合
			return false;
		}
		if (paidHolidayDto.getWorkRatio() == 0) {
			// 出勤率が0%の場合
			return true;
		}
		int achievement = 0;
		// 全労働日
		int totalWorkDays = 0;
		// 1年前の年月を取得
		int year = calculationYear - 1;
		int month = calculationMonth + 1;
		if (month == 13) {
			month = 1;
		}
		while (year * TimeConst.CODE_DEFINITION_YEAR + month <= calculationYear * TimeConst.CODE_DEFINITION_YEAR
				+ calculationMonth) {
			TotalTimeDataDtoInterface dto = totalTimeDataDao.findForKey(personalId, year, month);
			if (dto != null) {
				// 出勤実績日数を加算
				achievement += dto.getTimesAchievement();
				// 出勤対象日数を加算
				totalWorkDays += dto.getTimesTotalWorkDate();
			}
			month++;
			if (month == 13) {
				year++;
				month = 1;
			}
		}
		if (totalWorkDays == 0) {
			// 全労働日が0の場合
			return false;
		}
		// 出勤率が0%でなく且つ全労働日が0日でない場合
		double percentage = (achievement * 100) / (double)totalWorkDays;
		return percentage >= paidHolidayDto.getWorkRatio();
	}
	
	@Override
	public double getAttendanceRatio(String personalId, Date activateDate, int calculationYear, int calculationMonth,
			int grant) throws MospException {
		if (!hasCutoffSettings(personalId, activateDate)) {
			// 締日情報が取得できない場合
			return 0;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			// 対象個人ID及び対象日付で有給休暇設定情報を取得し設定
			if (!hasPaidHolidaySettings(personalId, activateDate)) {
				// 有給休暇情報が取得できない場合
				return 0;
			}
			int achievement = 0;
			// 全労働日
			int totalWorkDays = 0;
			// 付与年度の確認
			if (grant == 1) {
				// 初年度の場合
				Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), entranceDate);
				int year = DateUtility.getYear(cutoffMonth);
				int month = DateUtility.getMonth(cutoffMonth);
				while (year * TimeConst.CODE_DEFINITION_YEAR + month <= calculationYear
						* TimeConst.CODE_DEFINITION_YEAR + calculationMonth) {
					TotalTimeDataDtoInterface dto = totalTimeDataDao.findForKey(personalId, year, month);
					if (dto != null) {
						// 出勤実績日数を加算
						achievement += dto.getTimesAchievement();
						// 出勤対象日数を加算
						totalWorkDays += dto.getTimesTotalWorkDate();
					}
					month++;
					if (month == 13) {
						year++;
						month = 1;
					}
				}
				int difference = (calculationYear * TimeConst.CODE_DEFINITION_YEAR + calculationMonth)
						- (DateUtility.getYear(cutoffMonth) * TimeConst.CODE_DEFINITION_YEAR + DateUtility
							.getMonth(cutoffMonth));
				if (difference < 5) {
					List<Date> list = TimeUtility.getDateList(addDay(activateDate, 1),
							addDay(DateUtility.addMonth(entranceDate, 6), -1));
					for (Date targetDate : list) {
						if (!hasApplicationSettings(personalId, targetDate)) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(
								applicationDto.getScheduleCode(), targetDate);
						if (scheduleDto == null) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
								scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), targetDate);
						if (scheduleDateDto == null) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
								&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
							// 法定休日でなく且つ所定休日でない場合
							achievement++;
							totalWorkDays++;
						}
					}
				}
			} else if (grant >= 2) {
				// 次年度以降の場合
				Date grantDateOfPreviousFiscalYear = getGrantDateOfPreviousFiscalYear(personalId, activateDate);
				if (grantDateOfPreviousFiscalYear == null) {
					// 入社日とする
					grantDateOfPreviousFiscalYear = entranceDate;
				}
				Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), grantDateOfPreviousFiscalYear);
				int year = DateUtility.getYear(cutoffMonth);
				int month = DateUtility.getMonth(cutoffMonth);
				while (year * TimeConst.CODE_DEFINITION_YEAR + month <= calculationYear
						* TimeConst.CODE_DEFINITION_YEAR + calculationMonth) {
					TotalTimeDataDtoInterface dto = totalTimeDataDao.findForKey(personalId, year, month);
					if (dto != null) {
						// 出勤実績日数を加算
						achievement += dto.getTimesAchievement();
						// 出勤対象日数を加算
						totalWorkDays += dto.getTimesTotalWorkDate();
					}
					month++;
					if (month == 13) {
						year++;
						month = 1;
					}
				}
				int difference = (calculationYear * TimeConst.CODE_DEFINITION_YEAR + calculationMonth)
						- (DateUtility.getYear(cutoffMonth) * TimeConst.CODE_DEFINITION_YEAR + DateUtility
							.getMonth(cutoffMonth));
				if (difference < 11) {
					List<Date> list = TimeUtility.getDateList(addDay(activateDate, 1),
							addDay(DateUtility.addMonth(grantDateOfPreviousFiscalYear, 12), -1));
					for (Date targetDate : list) {
						if (!hasApplicationSettings(personalId, targetDate)) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(
								applicationDto.getScheduleCode(), targetDate);
						if (scheduleDto == null) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
								scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), targetDate);
						if (scheduleDateDto == null) {
							achievement++;
							totalWorkDays++;
							continue;
						}
						if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
								&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
							// 法定休日でなく且つ所定休日でない場合
							achievement++;
							totalWorkDays++;
						}
					}
				}
			} else {
				return 0;
			}
			if (totalWorkDays == 0) {
				// 全労働日が0の場合
				return 0;
			}
			// 全労働日が0日でない場合
			double percentage = (achievement * 100) / (double)totalWorkDays;
			return percentage;
		}
		return 0;
	}
	
	/**
	 * 日数文字列を取得する。<br>
	 * 日数を文字列(小数点以下1桁)で表す。<br>
	 * @param days 対象日数
	 * @return 日数文字列
	 */
	protected String getStringDays(double days) {
		DecimalFormat df = new DecimalFormat("0.#");
		// 日付文字列取得
		return df.format(days);
	}
	
	@Override
	public Date getOldestGrantDate(String personalId, Date targetDate, double requestDay, int requestHour)
			throws MospException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	@Override
	public boolean canPaidHolidayRequest(String personalId, Date targetDate, double requestDay, int requestHour)
			throws MospException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	
	@Override
	public void lockTables() throws MospException {
		super.lockTables();
	}
	
	@Override
	public void unlockTable() throws MospException {
		super.unlockTable();
	}
	
}
