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
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * 有給休暇データ付与クラス。
 */
public class StockHolidayDataGrantBean extends TimeApplicationBean implements StockHolidayDataGrantBeanInterface {
	
	/**
	 * ストック休暇設定参照クラス。
	 */
	protected StockHolidayReferenceBeanInterface			stockHolidayRefer;
	
	/**
	 * ストック休暇データ参照クラス。
	 */
	protected StockHolidayDataReferenceBeanInterface		stockHolidayDataRefer;
	
	/**
	 * ストック休暇データ登録クラス。
	 */
	protected StockHolidayDataRegistBeanInterface			stockHolidayDataRegist;
	
	/**
	 * ストック休暇トランザクション参照クラス。
	 */
	protected StockHolidayTransactionReferenceBeanInterface	stockHolidayTransactionRefer;
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayDataRefer;
	
	/**
	 * 有給休暇トランザクション参照クラス。
	 */
	protected PaidHolidayTransactionReferenceBeanInterface	paidHolidayTransactionRefer;
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestRefer;
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public StockHolidayDataGrantBean() {
		super();
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection データベースコネクション
	 */
	public StockHolidayDataGrantBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		stockHolidayRefer = (StockHolidayReferenceBeanInterface)createBean(StockHolidayReferenceBeanInterface.class);
		stockHolidayDataRefer = (StockHolidayDataReferenceBeanInterface)createBean(StockHolidayDataReferenceBeanInterface.class);
		stockHolidayDataRegist = (StockHolidayDataRegistBeanInterface)createBean(StockHolidayDataRegistBeanInterface.class);
		stockHolidayTransactionRefer = (StockHolidayTransactionReferenceBeanInterface)createBean(StockHolidayTransactionReferenceBeanInterface.class);
		paidHolidayDataRefer = (PaidHolidayDataReferenceBeanInterface)createBean(PaidHolidayDataReferenceBeanInterface.class);
		paidHolidayTransactionRefer = (PaidHolidayTransactionReferenceBeanInterface)createBean(PaidHolidayTransactionReferenceBeanInterface.class);
		holidayRequestRefer = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public void grant(String personalId, Date targetDate) throws MospException {
		grant(personalId, targetDate, true);
	}
	
	@Override
	public void grant(String personalId, Date targetDate, boolean isUpdate) throws MospException {
		grant(create(personalId, targetDate, isUpdate));
	}
	
	/**
	 * ストック休暇データ付与を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void grant(StockHolidayDataDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		StockHolidayDataDtoInterface stockHolidayDataDto = stockHolidayDataRefer.findForKey(dto.getPersonalId(),
				dto.getActivateDate(), dto.getAcquisitionDate());
		if (stockHolidayDataDto != null) {
			// 削除
			stockHolidayDataRegist.delete(stockHolidayDataDto);
		}
		// 新規登録
		stockHolidayDataRegist.insert(dto);
	}
	
	/**
	 * ストック休暇データを生成する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param isUpdate 更新する場合true、しない場合false
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected StockHolidayDataDtoInterface create(String personalId, Date targetDate, boolean isUpdate)
			throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return null;
		}
		StockHolidayDtoInterface stockHolidayDto = stockHolidayRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
				paidHolidayDto.getActivateDate());
		if (stockHolidayDto == null) {
			return null;
		}
		Date paidHolidayExpirationDate = getPaidHolidayExpirationDate(personalId, targetDate);
		if (paidHolidayExpirationDate == null) {
			return null;
		}
		Date grantDate = getGrantDate(paidHolidayExpirationDate);
		if (!isUpdate && stockHolidayDataRefer.findForKey(personalId, grantDate, grantDate) != null) {
			// 更新しない場合
			return null;
		}
		StockHolidayDataDtoInterface dto = stockHolidayDataRegist.getInitDto();
		dto.setPersonalId(personalId);
		dto.setAcquisitionDate(grantDate);
		dto.setActivateDate(grantDate);
		dto.setLimitDate(getExpirationDate(stockHolidayDto, grantDate));
		dto.setHoldDay(getGrantDays(stockHolidayDto, personalId, paidHolidayExpirationDate));
		dto.setGivingDay(0);
		dto.setCancelDay(0);
		dto.setUseDay(0);
		return dto;
	}
	
	/**
	 * 付与日付を取得する。
	 * @param paidHolidayExpirationDate 有給休暇期限日付
	 * @return 付与日付
	 */
	protected Date getGrantDate(Date paidHolidayExpirationDate) {
		return addDay(paidHolidayExpirationDate, 1);
	}
	
	/**
	 * 期限日付を取得する。
	 * @param dto 対象DTO
	 * @param grantDate 付与日付
	 * @return 期限日付
	 */
	protected Date getExpirationDate(StockHolidayDtoInterface dto, Date grantDate) {
		return addDay(DateUtility.addMonth(grantDate, dto.getStockLimitDate()), -1);
	}
	
	/**
	 * 付与日数を取得する。
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param paidHolidayExpirationDate 有給休暇期限日
	 * @return 付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getGrantDays(StockHolidayDtoInterface dto, String personalId, Date paidHolidayExpirationDate)
			throws MospException {
		double remainderDays = getRemainderDay(personalId, paidHolidayExpirationDate);
		double paidHolidayRemainderDays = getPaidHolidayRemainderDay(personalId, paidHolidayExpirationDate);
		double grantDays = paidHolidayRemainderDays;
		if (grantDays > dto.getStockYearAmount()) {
			// 最大年間積立日数を超える場合は最大年間積立日数とする
			grantDays = dto.getStockYearAmount();
		}
		if (remainderDays >= dto.getStockTotalAmount()) {
			// 前年度分までの合計積立日数が最大合計積立日数以上の場合は0とする
			return 0;
		} else if (remainderDays + grantDays > dto.getStockTotalAmount()) {
			// 前年度分までの合計積立日数 + 付与日数が最大合計積立日数を超える場合は
			// 最大合計積立日数 - 前年度分までの合計積立日数とする
			return dto.getStockTotalAmount() - remainderDays;
		}
		return grantDays;
	}
	
	/**
	 * 残数を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return ストック休暇残数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getRemainderDay(String personalId, Date targetDate) throws MospException {
		double totalDay = 0;
		List<StockHolidayDataDtoInterface> stockHolidayDataList = stockHolidayDataRefer.findForInfoList(personalId,
				targetDate);
		for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataList) {
			double givingDay = 0;
			double cancelDay = 0;
			List<StockHolidayTransactionDtoInterface> stockHolidayTransactionList = stockHolidayTransactionRefer
				.findForList(stockHolidayDataDto.getPersonalId(), stockHolidayDataDto.getAcquisitionDate(),
						stockHolidayDataDto.getActivateDate(), targetDate);
			for (StockHolidayTransactionDtoInterface stockHolidayTransactionDto : stockHolidayTransactionList) {
				givingDay += stockHolidayTransactionDto.getGivingDay();
				cancelDay += stockHolidayTransactionDto.getCancelDay();
			}
			Map<String, Object> map = holidayRequestRefer.getApprovedDayHour(stockHolidayDataDto.getPersonalId(),
					stockHolidayDataDto.getAcquisitionDate(), TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
					Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK), stockHolidayDataDto.getActivateDate(),
					targetDate);
			double useDay = ((Double)map.get(TimeConst.CODE_APPROVED_DAY)).doubleValue();
			double day = stockHolidayDataDto.getHoldDay() + givingDay - cancelDay - useDay;
			totalDay += day;
		}
		return totalDay;
	}
	
	/**
	 * 対象日から最も近い有給休暇期限日を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getPaidHolidayExpirationDate(String personalId, Date targetDate) throws MospException {
		PaidHolidayDataDtoInterface dto = paidHolidayDataRefer.findForExpirationDateInfo(personalId, targetDate);
		if (dto == null) {
			return null;
		}
		return dto.getLimitDate();
	}
	
	/**
	 * 有給休暇期限切れ日数を取得する。
	 * @param personalId 個人ID
	 * @param expirationDate 期限日
	 * @return 有給休暇期限切れ日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getPaidHolidayRemainderDay(String personalId, Date expirationDate) throws MospException {
		double totalDay = 0;
		List<PaidHolidayDataDtoInterface> paidHolidayDataList = paidHolidayDataRefer.findForExpirationDateList(
				personalId, expirationDate);
		for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataList) {
			double givingDay = 0;
			int givingHour = 0;
			double cancelDay = 0;
			int cancelHour = 0;
			List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionList = paidHolidayTransactionRefer
				.findForList(paidHolidayDataDto.getPersonalId(), paidHolidayDataDto.getAcquisitionDate(),
						paidHolidayDataDto.getActivateDate(), paidHolidayDataDto.getLimitDate());
			for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionList) {
				givingDay += paidHolidayTransactionDto.getGivingDay();
				givingHour += paidHolidayTransactionDto.getGivingHour();
				cancelDay += paidHolidayTransactionDto.getCancelDay();
				cancelHour += paidHolidayTransactionDto.getCancelHour();
			}
			Map<String, Object> map = holidayRequestRefer.getRequestDayHour(paidHolidayDataDto.getPersonalId(),
					paidHolidayDataDto.getAcquisitionDate(), TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
					Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY), paidHolidayDataDto.getActivateDate(),
					paidHolidayDataDto.getLimitDate());
			double useDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
			int useHour = ((Integer)map.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
			double day = paidHolidayDataDto.getHoldDay() + givingDay - cancelDay - useDay;
			int hour = paidHolidayDataDto.getHoldHour() + givingHour - cancelHour - useHour;
			ApplicationDtoInterface applicationDto = applicationRefer.findForPerson(paidHolidayDataDto.getPersonalId(),
					paidHolidayDataDto.getLimitDate());
			applicationRefer.chkExistApplication(applicationDto, paidHolidayDataDto.getLimitDate());
			if (mospParams.hasErrorMessage()) {
				return 0;
			}
			PaidHolidayDtoInterface dto = paidHolidayRefer.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(),
					paidHolidayDataDto.getLimitDate());
			paidHolidayRefer.chkExistPaidHoliday(dto, paidHolidayDataDto.getLimitDate());
			if (mospParams.hasErrorMessage()) {
				return 0;
			}
			if (dto.getTimeAcquisitionLimitTimes() <= 0) {
				totalDay += day;
				continue;
			}
			while (day >= 1 && hour < 0) {
				day--;
				hour += dto.getTimeAcquisitionLimitTimes();
			}
			totalDay += day;
		}
		return totalDay;
	}
	
}
