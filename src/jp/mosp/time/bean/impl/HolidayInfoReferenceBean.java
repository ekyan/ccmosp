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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇データ参照クラス。
 */
public class HolidayInfoReferenceBean extends TimeBean implements HolidayInfoReferenceBeanInterface {
	
	/**
	 * 休暇データDAO。
	 */
	private HolidayDataDaoInterface					dao;
	
	/**
	 * 休暇種別マスタDAO。
	 */
	private HolidayDaoInterface						holidayDao;
	
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
	public HolidayInfoReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public HolidayInfoReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidayPossibleRequestListForRequest(String personalId, Date targetDate,
			int holidayType) throws MospException {
		List<HolidayDataDtoInterface> list = new ArrayList<HolidayDataDtoInterface>();
		List<HolidayDataDtoInterface> holidayDataList = dao.findForInfoList(personalId, targetDate,
				String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
		for (HolidayDataDtoInterface dto : holidayDataList) {
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				continue;
			}
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				// リストに追加
				list.add(dto);
				continue;
			}
			double requestDay = 0;
//			int requestHour = 0;
			// 申請
			List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForRequestList(personalId,
					dto.getActivateDate(), holidayType, dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayLimitDate());
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if (workflowIntegrate.isFirstReverted(holidayRequestDto.getWorkflow())) {
					// 1次戻の場合
					continue;
				}
				requestDay += holidayRequestDto.getUseDay();
//				requestHour += holidayRequestDto.getUseHour();
			}
			if (dto.getGivingDay() - dto.getCancelDay() - requestDay > 0) {
				dto.setCancelDay(dto.getCancelDay() + requestDay);
				// リストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidayPossibleRequestList(String personalId, Date targetDate,
			int holidayType) throws MospException {
		List<HolidayDataDtoInterface> list = new ArrayList<HolidayDataDtoInterface>();
		List<HolidayDataDtoInterface> holidayDataList = dao.findForInfoList(personalId, targetDate,
				String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
		for (HolidayDataDtoInterface dto : holidayDataList) {
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				continue;
			}
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				// リストに追加
				list.add(dto);
				continue;
			}
			// 申請
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, dto.getActivateDate(), holidayType,
					dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayLimitDate());
			double requestDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
			if (dto.getGivingDay() - dto.getCancelDay() - requestDay > 0) {
				dto.setCancelDay(dto.getCancelDay() + requestDay);
				// リストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public HolidayDataDtoInterface getHolidayPossibleRequestForRequest(String personalId, Date targetDate,
			String holidayCode, int holidayType) throws MospException {
		HolidayDataDtoInterface holidayDataDto = null;
		List<HolidayDataDtoInterface> list = dao.findForEarliestList(personalId, targetDate, holidayCode, holidayType);
		for (HolidayDataDtoInterface dto : list) {
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				continue;
			}
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				return dto;
			}
			double requestDay = 0;
//			int requestHour = 0;
			// 申請
			List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForRequestList(personalId,
					dto.getActivateDate(), holidayType, holidayCode, dto.getActivateDate(), dto.getHolidayLimitDate());
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if (workflowIntegrate.isFirstReverted(holidayRequestDto.getWorkflow())) {
					// 1次戻の場合
					continue;
				}
				requestDay += holidayRequestDto.getUseDay();
//				requestHour += holidayRequestDto.getUseHour();
			}
			// 破棄日数をセット
			dto.setCancelDay(dto.getCancelDay() + requestDay);
			if (dto.getGivingDay() - dto.getCancelDay() > 0) {
				// 0より大きい場合
				return dto;
			}
			// 0以下の場合
			holidayDataDto = dto;
		}
		return holidayDataDto;
	}
	
	@Override
	public HolidayDataDtoInterface getHolidayPossibleRequest(String personalId, Date targetDate, String holidayCode,
			int holidayType) throws MospException {
		List<HolidayDataDtoInterface> list = dao.findForEarliestList(personalId, targetDate, holidayCode, holidayType);
		for (HolidayDataDtoInterface dto : list) {
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				return null;
			}
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				return dto;
			}
			// 申請
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, dto.getActivateDate(), holidayType,
					holidayCode, dto.getActivateDate(), dto.getHolidayLimitDate());
			double requestDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
			if (dto.getGivingDay() - dto.getCancelDay() - requestDay > 0) {
				dto.setCancelDay(dto.getCancelDay() + requestDay);
				return dto;
			}
		}
		return null;
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate, int holidayType)
			throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<HolidayDataDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate, holidayType);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
}
