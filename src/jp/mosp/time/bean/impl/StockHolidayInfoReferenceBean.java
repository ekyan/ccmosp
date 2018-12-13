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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
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
import jp.mosp.time.utils.TimeUtility;

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
		stockHolidayTransactionDao = (StockHolidayTransactionDaoInterface)createDao(
				StockHolidayTransactionDaoInterface.class);
		// ストック休暇データ登録クラス取得
		stockHolidayDataRegist = (StockHolidayDataRegistBeanInterface)createBean(
				StockHolidayDataRegistBeanInterface.class);
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
				stockHolidayDataDto
					.setGivingDay(stockHolidayDataDto.getGivingDay() + stockHolidayTransactionDto.getGivingDay());
				// 手動廃棄日数設定
				stockHolidayDataDto
					.setCancelDay(stockHolidayDataDto.getCancelDay() + stockHolidayTransactionDto.getCancelDay());
			}
			// 承認完了
			Map<String, Object> map = holidayRequest.getApprovedDayHour(personalId, acquisitionDate,
					TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK),
					activateDate, targetDate);
			stockHolidayDataDto.setUseDay(
					stockHolidayDataDto.getUseDay() + ((Double)map.get(TimeConst.CODE_APPROVED_DAY)).doubleValue());
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
				stockHolidayDataDto
					.setGivingDay(stockHolidayDataDto.getGivingDay() + stockHolidayTransactionDto.getGivingDay());
				stockHolidayDataDto
					.setCancelDay(stockHolidayDataDto.getCancelDay() + stockHolidayTransactionDto.getCancelDay());
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
				stockHolidayDataDto
					.setGivingDay(stockHolidayDataDto.getGivingDay() + stockHolidayTransactionDto.getGivingDay());
				stockHolidayDataDto
					.setCancelDay(stockHolidayDataDto.getCancelDay() + stockHolidayTransactionDto.getCancelDay());
			}
			// 申請
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, acquisitionDate,
					TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK),
					activateDate, stockHolidayDataDto.getLimitDate());
			// 使用日数設定
			stockHolidayDataDto.setUseDay(
					stockHolidayDataDto.getUseDay() + ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue());
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
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, acquisitionDate,
					TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK),
					activateDate, stockHolidayDataDto.getLimitDate());
			// 全休
			stockDate -= ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
		}
		return Double.valueOf(stockDate);
	}
	
	@Override
	public double getStockPaidInfo(String personalId, Date startDate, Date endDate) throws MospException {
		double stockDate = 0;
		// ストック休暇データ取得
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoList(personalId,
				startDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			// 取得日取得
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			stockDate += stockHolidayDataDto.getHoldDay();
			// 手動付与・破棄
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, startDate, endDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				stockDate += stockHolidayTransactionDto.getGivingDay();
				stockDate -= stockHolidayTransactionDto.getCancelDay();
			}
		}
		return stockDate;
		
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
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, acquisitionDate,
					TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK),
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
	
	@Override
	public Map<String, Object> getSubordinateFiscalStockHolidayInfo(HumanDtoInterface humanDto, int displayYear,
			Date targetDate) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentGiveDay", 0.0);
		map.put("formerRestDay", 0.0);
		map.put("termUseHolidayRequestDay", 0.0);
		// 今年度付与日数(返値)
		double currentGiveDay = 0;
		// 前年度残日数(返値)
		double formerRestDay = 0;
		// 今年度利用日数(有給休暇期限切れによる破棄数も含む)(返値)
		double termUseHolidayRequestDay = 0;
		// 今年度付与日数
		double currentHoldDay = 0;
		// 前年度付与日数
		double formerHoldDay = 0;
		// 今年度手動付与日数
		double currentGivingDay = 0;
		// 前年度手動付与日数
		double formerGivingDay = 0;
		// 今年度手動廃棄日数
		double currentCancelDay = 0;
		// 前年度手動廃棄日数
		double formerCancelDay = 0;
		// 前年度利用日数
		double formerUseDay = 0;
		// 個人IDを取得
		String personalId = humanDto.getPersonalId();
		// 年度の開始日及び終了日を取得
		Date fiscalFirstDate = MonthUtility.getFiscalYearFirstDate(displayYear, mospParams);
		Date fiscalLastDate = MonthUtility.getFiscalYearLastDate(displayYear, mospParams);
		// 表示年度開始日の前日を取得(表示年度が2014年の場合は2014年3月31日)
		Date lastDate = DateUtility.addDay(fiscalFirstDate, -1);
		// 期限日が表示年度開始日の前日以降のストック休暇情報リストを取得
		List<StockHolidayDataDtoInterface> stockHolidayDataDtoList = stockHolidayDataDao.findForInfoAllList(personalId,
				lastDate);
		// ストック休暇データリスト毎に処理
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataDtoList) {
			// 取得日取得
			Date acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
			// 取得日が対象日より後である場合
			if (acquisitionDate.compareTo(targetDate) > 0) {
				// 処理無し
				continue;
			}
			// 当該有給休暇情報の残日数及び残時間を準備(破棄時に用いる)
			double restDays = stockHolidayDataDto.getHoldDay();
			// 取得日が表示年度開始日の前日より後（true：今年度、false：前年度）
			boolean isCurrent = acquisitionDate.compareTo(lastDate) > 0;
			// 今年度か前年度かを判断
			if (isCurrent) {
				// 今年度の付与日数及び付与時間を加算
				currentHoldDay += stockHolidayDataDto.getHoldDay();
			} else {
				// 前年度の付与日数及び付与時間を加算
				formerHoldDay += stockHolidayDataDto.getHoldDay();
			}
			// 下書、取下除く休暇申請リストを取得
			List<HolidayRequestDtoInterface> requestList = holidayRequest.getUsePaidHolidayDataList(personalId,
					acquisitionDate);
			// 有給休暇トランザクション情報リスト取得
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionDtoList = stockHolidayTransactionDao
				.findForList(personalId, acquisitionDate, stockHolidayDataDto.getActivateDate(), targetDate);
			// 有給休暇トランザクション情報リスト毎に処理
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionDtoList) {
				if (isCurrent) {
					// 付与日数・付与時間・廃棄日数・廃棄時間数
					currentGivingDay += stockHolidayTransactionDto.getGivingDay();
					currentCancelDay += stockHolidayTransactionDto.getCancelDay();
				} else {
					// 付与日数・付与時間・廃棄日数・廃棄時間数
					formerGivingDay += stockHolidayTransactionDto.getGivingDay();
					formerCancelDay += stockHolidayTransactionDto.getCancelDay();
				}
				// 当該有給休暇情報の残日数及び残時間に加算
				restDays += stockHolidayTransactionDto.getGivingDay();
				// 当該有給休暇情報の残日数及び残時間から減算
				restDays -= stockHolidayTransactionDto.getCancelDay();
			}
			// 休暇申請毎に処理
			for (HolidayRequestDtoInterface requestDto : requestList) {
				// ストック休暇申請でない場合
				if (TimeUtility.isStockHolidayRequest(requestDto) == false) {
					// 処理無し
					continue;
				}
				// 申請日を取得(有給休暇の場合は申請開始日と申請終了日が同じ)
				Date requestDate = requestDto.getRequestStartDate();
				// 申請日が年度の終了日より後(次年度)である場合
				if (requestDate.compareTo(fiscalLastDate) > 0) {
					// 処理無し
					continue;
				}
				// 申請日が表示年度開始日の前日より後(今年度)である場合
				if (requestDate.compareTo(lastDate) > 0) {
					// 今年度利用日数及び今年度利用時間を加算
					termUseHolidayRequestDay += requestDto.getUseDay();
				} else {
					// 申請日が前年度である場合
					formerUseDay += requestDto.getUseDay();
				}
				// 当該有給休暇情報の残日数及び残時間から減算
				restDays -= requestDto.getUseDay();
			}
			// 対象日が期限日よりも後(期限切れ)である場合
			if (targetDate.compareTo(stockHolidayDataDto.getLimitDate()) > 0) {
				// 今年度利用日数及び今年度利用時間に当該有給休暇情報の残を加算
				termUseHolidayRequestDay += restDays;
			}
		}
		// 今年度付与日数
		currentGiveDay = currentHoldDay + currentGivingDay - currentCancelDay;
		// 前年度残数
		formerRestDay = formerHoldDay + formerGivingDay - formerCancelDay - formerUseDay;
		map.put("currentGiveDay", currentGiveDay);
		map.put("formerRestDay", formerRestDay);
		map.put("termUseHolidayRequestDay", termUseHolidayRequestDay);
		return map;
	}
	
}
