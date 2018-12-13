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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.StockHolidayDaoInterface;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.StockHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * ストック休暇情報参照クラス。
 */
public class StockHolidayInfoReferenceBean extends PlatformBean implements StockHolidayInfoReferenceBeanInterface {
	
	/**
	 * 設定適用管理参照。
	 */
	private ApplicationReferenceBeanInterface		application;
	
	/**
	 * ストック休暇DAO。
	 */
	private StockHolidayDaoInterface				stockHolidayDao;
	
	/**
	 * ストック休暇データDAO。
	 */
	private StockHolidayDataDaoInterface			stockHolidayDataDao;
	
	/**
	 * ストック休暇トランザクションDAO。
	 */
	private StockHolidayTransactionDaoInterface		stockHolidayTransactionDao;
	
	/**
	 * ストック休暇データ登録。
	 */
	private StockHolidayDataRegistBeanInterface		stockHolidayDataRegist;
	
	/**
	 * 休暇申請DAO。
	 */
	private HolidayRequestDaoInterface				holidayRequestDao;
	
	/**
	 * 休暇申請参照。
	 */
	private HolidayRequestReferenceBeanInterface	holidayRequest;
	
	/**
	 * ワークフロー統括
	 */
	private WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayInfoReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public StockHolidayInfoReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 設定適用管理DAO取得
		application = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		// ストック休暇DAO取得
		stockHolidayDao = (StockHolidayDaoInterface)createDao(StockHolidayDaoInterface.class);
		// ストック休暇データDAO取得
		stockHolidayDataDao = (StockHolidayDataDaoInterface)createDao(StockHolidayDataDaoInterface.class);
		// ストック休暇トランザクションDAO取得
		stockHolidayTransactionDao = (StockHolidayTransactionDaoInterface)createDao(StockHolidayTransactionDaoInterface.class);
		// ストック休暇データ登録クラス取得
		stockHolidayDataRegist = (StockHolidayDataRegistBeanInterface)createBean(StockHolidayDataRegistBeanInterface.class);
		// 休暇申請DAO取得
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		// 休暇申請参照クラス取得
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		// ワークフロー統括クラス取得
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayCalcInfo(String personalId, Date targetDate)
			throws MospException {
		List<StockHolidayDataDtoInterface> list = new ArrayList<StockHolidayDataDtoInterface>();
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				targetDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			Date activateDate = stockHolidayDataDto.getActivateDate();
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, activateDate, targetDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				// 手動付与日数設定
				stockHolidayDataDto.setGivingDay(stockHolidayDataDto.getGivingDay()
						+ stockHolidayTransactionDto.getGivingDay());
				// 手動廃棄日数設定
				stockHolidayDataDto.setCancelDay(stockHolidayDataDto.getCancelDay()
						+ stockHolidayTransactionDto.getCancelDay());
			}
			// 承認完了
			Map<String, Object> map = holidayRequest
				.getApprovedDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
					.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]),
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[1][0],
						activateDate, targetDate);
			stockHolidayDataDto.setUseDay(stockHolidayDataDto.getUseDay()
					+ ((Double)map.get(TimeConst.CODE_APPROVED_DAY)).doubleValue());
			stockHolidayDataDto.setActivateDate(targetDate);
			list.add(stockHolidayDataDto);
		}
		return list;
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayPossibleRequestForRequest(String personalId,
			Date targetDate) throws MospException {
		// 返却リストの初期化
		List<StockHolidayDataDtoInterface> list = new ArrayList<StockHolidayDataDtoInterface>();
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				targetDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			Date activateDate = stockHolidayDataDto.getActivateDate();
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, activateDate, targetDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				stockHolidayDataDto.setGivingDay(stockHolidayDataDto.getGivingDay()
						+ stockHolidayTransactionDto.getGivingDay());
				stockHolidayDataDto.setCancelDay(stockHolidayDataDto.getCancelDay()
						+ stockHolidayTransactionDto.getCancelDay());
			}
			double requestDay = 0;
//			int requestHour = 0;
			// 申請
			List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForRequestList(personalId,
					acquisitionDate, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
					Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK), activateDate,
					stockHolidayDataDto.getLimitDate());
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if (workflowIntegrate.isFirstReverted(holidayRequestDto.getWorkflow())) {
					// 1次戻の場合
					continue;
				}
				requestDay += holidayRequestDto.getUseDay();
//				requestHour += holidayRequestDto.getUseHour();
			}
			// 使用日数設定
			stockHolidayDataDto.setUseDay(stockHolidayDataDto.getUseDay() + requestDay);
			list.add(stockHolidayDataDto);
		}
		return list;
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayPossibleRequest(String personalId, Date targetDate)
			throws MospException {
		// 返却リストの初期化
		List<StockHolidayDataDtoInterface> list = new ArrayList<StockHolidayDataDtoInterface>();
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				targetDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			Date activateDate = stockHolidayDataDto.getActivateDate();
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, activateDate, targetDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				stockHolidayDataDto.setGivingDay(stockHolidayDataDto.getGivingDay()
						+ stockHolidayTransactionDto.getGivingDay());
				stockHolidayDataDto.setCancelDay(stockHolidayDataDto.getCancelDay()
						+ stockHolidayTransactionDto.getCancelDay());
			}
			// 申請
			Map<String, Object> map = holidayRequest
				.getRequestDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
					.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]),
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[1][0],
						activateDate, stockHolidayDataDto.getLimitDate());
			// 使用日数設定
			stockHolidayDataDto.setUseDay(stockHolidayDataDto.getUseDay()
					+ ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue());
			list.add(stockHolidayDataDto);
		}
		return list;
	}
	
	@Override
	public Double getRemainDay(String personalId, Date targetDate) throws MospException {
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				targetDate);
		if (stockHolidayDataDtoList.isEmpty()) {
			return null;
		}
		double stockDate = 0;
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			Date activateDate = stockHolidayDataDto.getActivateDate();
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			stockDate += stockHolidayDataDto.getHoldDay();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, activateDate, targetDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				stockDate += stockHolidayTransactionDto.getGivingDay();
				stockDate -= stockHolidayTransactionDto.getCancelDay();
			}
			// 申請・承認・使用
			Map<String, Object> map = holidayRequest
				.getRequestDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
					.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]),
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[1][0],
						activateDate, stockHolidayDataDto.getLimitDate());
			// 全休
			stockDate -= ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
		}
		return Double.valueOf(stockDate);
	}
	
	@Override
	public double getRemainDay(String personalId, Date startDate, Date endDate) throws MospException {
		double stockDate = 0;
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				startDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			Date activateDate = stockHolidayDataDto.getActivateDate();
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			stockDate += stockHolidayDataDto.getHoldDay();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, startDate, endDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				stockDate += stockHolidayTransactionDto.getGivingDay();
				stockDate -= stockHolidayTransactionDto.getCancelDay();
			}
			// 申請・承認・使用
			Map<String, Object> map = holidayRequest
				.getRequestDayHour(personalId, acquisitionDate, Integer.parseInt(mospParams.getProperties()
					.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]),
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[1][0],
						activateDate, stockHolidayDataDto.getLimitDate());
			// 全休
			stockDate -= ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
		}
		return stockDate;
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayNextMonthInfo(String personalId, Date activateDate,
			List<StockHolidayDataDtoInterface> list) {
		List<StockHolidayDataDtoInterface> stockHolidayDataList = new ArrayList<StockHolidayDataDtoInterface>();
		for (StockHolidayDataDtoInterface dto : list) {
			if (dto.getLimitDate().compareTo(activateDate) >= 0) {
				dto.setActivateDate(activateDate);
				dto.setHoldDay(dto.getHoldDay() + dto.getGivingDay() - dto.getCancelDay() - dto.getUseDay());
				dto.setGivingDay(0);
				dto.setCancelDay(0);
				dto.setUseDay(0);
				stockHolidayDataList.add(dto);
			}
		}
		return stockHolidayDataList;
	}
	
	@Override
	public StockHolidayDataDtoInterface getNewStockHolidayInfo(String personalId, Date activateDate, double day)
			throws MospException {
		ApplicationDtoInterface applicationDto = application.findForPerson(personalId, activateDate);
		if (applicationDto == null) {
			return null;
		}
		StockHolidayDtoInterface stockHolidayDto = stockHolidayDao.findForInfo(applicationDto.getPaidHolidayCode(),
				applicationDto.getActivateDate());
		if (stockHolidayDto == null) {
			return null;
		}
		StockHolidayDataDtoInterface dto = stockHolidayDataRegist.getInitDto();
		dto.setPersonalId(personalId);
		dto.setActivateDate(activateDate);
		dto.setAcquisitionDate(activateDate);
		dto.setLimitDate(DateUtility.addMonth(activateDate, stockHolidayDto.getStockLimitDate()));
		int stockYearAmount = stockHolidayDto.getStockYearAmount();
		if (day <= stockYearAmount) {
			// 最大年間積立日数以下の場合は日数をセットする
			dto.setHoldDay(day);
		} else {
			// 最大年間積立日数を超える場合は最大年間積立日数をセットする
			dto.setHoldDay(stockYearAmount);
		}
		dto.setGivingDay(0);
		dto.setCancelDay(0);
		dto.setUseDay(0);
		double totalAmount = 0;
		List<StockHolidayDataDtoInterface> list = stockHolidayDataDao.findForList(personalId, activateDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
			totalAmount += stockHolidayDataDto.getHoldDay();
		}
		if (totalAmount >= stockHolidayDto.getStockTotalAmount()) {
			// 前年度分までの合計積立日数が最大合計積立日数以上の場合は今年度分の積立日数に0をセットする
			dto.setHoldDay(0);
		} else if (totalAmount + dto.getHoldDay() > stockHolidayDto.getStockTotalAmount()) {
			// 前年度分までの合計積立日数 + 今年度分の積立日数が最大合計積立日数を超える場合は
			// 最大合計積立日数 - 前年度分までの合計積立日数を今年度分の積立日数にセットする
			dto.setHoldDay(stockHolidayDto.getStockTotalAmount() - totalAmount);
		}
		return dto;
	}
	
}
