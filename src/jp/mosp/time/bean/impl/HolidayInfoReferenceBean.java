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
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇数参照処理。<br>
 */
public class HolidayInfoReferenceBean extends TimeApplicationBean implements HolidayInfoReferenceBeanInterface {
	
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
	 * 休暇付与情報参照処理。<br>
	 */
	protected HolidayDataReferenceBeanInterface		refer;
	
	/**
	 * 休暇申請参照。
	 */
	private HolidayRequestReferenceBeanInterface	holidayRequest;
	
	/**
	 * ワークフロー統括
	 */
	private WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	
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
		// 継承元のBean初期化処理を実施
		super.initBean();
		// DAOを準備
		dao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		// Beanを準備
		refer = (HolidayDataReferenceBeanInterface)createBean(HolidayDataReferenceBeanInterface.class);
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		timeMaster = (TimeMasterBeanInterface)createBean(TimeMasterBeanInterface.class);
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidayPossibleRequestListForRequest(String personalId, Date targetDate,
			int holidayType) throws MospException {
		// リスト準備
		List<HolidayDataDtoInterface> list = new ArrayList<HolidayDataDtoInterface>();
		// 休暇データリスト取得
		List<HolidayDataDtoInterface> holidayDataList = dao.findForInfoList(personalId, targetDate,
				String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
		// 有休設定限度時間準備
		int generalWorkHour = 0;
		// 設定有給休暇情報を取得し、設定
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 有休設定時休限度時間取得
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
		// 休暇データ毎に処理
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
			int requestHour = 0;
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
				requestHour += holidayRequestDto.getUseHour();
			}
			
			// 日数の計算
			double cancelDay = dto.getCancelDay() + requestDay;
			int cancelHour = dto.getCancelHour() + requestHour;
			if (generalWorkHour > 0) {
				while (cancelHour < 0 && cancelDay >= 1) {
					cancelDay--;
					cancelHour += generalWorkHour;
				}
			}
			// 設定
			dto.setCancelDay(cancelDay);
			dto.setCancelHour(cancelHour);
			// リストに追加
			list.add(dto);
			
		}
		return list;
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidayPossibleRequestList(String personalId, Date targetDate,
			int holidayType) throws MospException {
		// 休暇データリスト準備
		List<HolidayDataDtoInterface> list = new ArrayList<HolidayDataDtoInterface>();
		// 休暇データリスト取得
		List<HolidayDataDtoInterface> holidayDataList = dao.findForInfoList(personalId, targetDate,
				String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
		int generalWorkHour = 0;
		// 設定有給休暇情報を取得し、設定
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 有休設定時休限度時間取得
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
		// 休暇データ情報毎に処理
		for (HolidayDataDtoInterface dto : holidayDataList) {
			// 休暇種別マスタ取得
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			// 休暇種別マスタに存在しない又は無効の場合
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 含まない
				continue;
			}
			// 付与日数が無制限の場合
			if (holidayDto.getNoLimit() == 1) {
				// リストに追加
				list.add(dto);
				continue;
			}
			// 休暇申請マップ取得
			Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, dto.getActivateDate(), holidayType,
					dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayLimitDate());
			// 休暇申請日数・時間数取得
			double requestDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
			int requestHour = ((Integer)map.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
			
			double remainDay = dto.getGivingDay() - dto.getCancelDay() - requestDay;
			int remainHour = dto.getGivingHour() - dto.getCancelHour() - requestHour;
			if (generalWorkHour > 0) {
				// 0より大きい場合
				while (remainHour < 0 && remainDay >= 1) {
					remainDay--;
					remainHour += generalWorkHour;
				}
			}
			if (remainDay > 0 || remainHour > 0) {
				dto.setGivingDay(remainDay);
				dto.setGivingHour(remainHour);
				dto.setCancelDay(dto.getCancelDay() + requestDay);
				dto.setCancelHour(dto.getCancelHour() + requestHour);
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
		// 有休設定限度時間準備
		int generalWorkHour = 0;
		// 設定有給休暇情報を取得し、設定
		if (hasPaidHolidaySettings(personalId, targetDate)) {
			// 有休設定時休限度時間取得
			generalWorkHour = paidHolidayDto.getTimeAcquisitionLimitTimes();
		}
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
			int requestHour = 0;
			// 申請
			List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForRequestList(personalId,
					dto.getActivateDate(), holidayType, holidayCode, dto.getActivateDate(), dto.getHolidayLimitDate());
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if (workflowIntegrate.isFirstReverted(holidayRequestDto.getWorkflow())) {
					// 1次戻の場合
					continue;
				}
				requestDay += holidayRequestDto.getUseDay();
				requestHour += holidayRequestDto.getUseHour();
			}
			// 日数・時間の計算
			double cancelDay = dto.getCancelDay() + requestDay;
			int cancelHour = dto.getCancelHour() + requestHour;
			if (generalWorkHour > 0) {
				while (cancelHour < 0 && cancelDay >= 1) {
					cancelDay--;
					cancelHour += generalWorkHour;
				}
			}
			// 破棄日数をセット
			dto.setCancelDay(cancelDay);
			dto.setCancelHour(cancelHour);
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
	
	@Override
	public Map<HolidayDataDtoInterface, SimpleEntry<Double, Integer>> getHolidayRemains(HumanDtoInterface humanDto,
			Date targetDate, int holidayType) throws MospException {
		// 休暇の残日数及び残時間数群を準備
		Map<HolidayDataDtoInterface, SimpleEntry<Double, Integer>> remains = new HashMap<HolidayDataDtoInterface, SimpleEntry<Double, Integer>>();
		// 人事情報から個人IDを取得
		String personalId = humanDto.getPersonalId();
		// 有休時間取得限度時間(1日の時間数)を取得
		int hoursPerDay = timeMaster.getPaidHolidayHoursPerDay(humanDto, targetDate);
		// 対象日時点で有効な休暇付与情報リストを取得
		List<HolidayDataDtoInterface> list = refer.getActiveList(personalId, targetDate, holidayType);
		// 休暇付与情報毎に処理
		for (HolidayDataDtoInterface dto : list) {
			// 休暇の残日数及び残時間数群を準備
			remains.put(dto, getHolidayRemains(dto, targetDate, hoursPerDay));
		}
		// 休暇の残日数及び残時間数群を取得
		return remains;
	}
	
	/**
	 * 休暇付与情報に対する休暇の残日数及び残時間数を取得する。<br>
	 * @param dto         休暇付与情報
	 * @param targetDate  対象日(申請取得時に申請終了日として利用)
	 * @param hoursPerDay 有休時間取得限度時間(1日の時間数)
	 * @return 休暇の残日数(キー)及び残時間数(値)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected SimpleEntry<Double, Integer> getHolidayRemains(HolidayDataDtoInterface dto, Date targetDate,
			int hoursPerDay) throws MospException {
		// 対象休暇付与情報が無期限(無制限)である場合
		if (TimeUtility.isUnlimited(dto)) {
			// 0日0時間を取得(残数は不要)
			return new SimpleEntry<Double, Integer>(0D, 0);
		}
		// 休暇付与情報から値を取得
		String personalId = dto.getPersonalId();
		Date acquisitionDate = dto.getActivateDate();
		int holidayType = dto.getHolidayType();
		String holidayCode = dto.getHolidayCode();
		// 休暇付与情報に対する利用日数及び利用時間数を取得
		Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, acquisitionDate, holidayType,
				holidayCode, acquisitionDate, targetDate);
		double days = (Double)map.get(TimeConst.CODE_REQUEST_DAY);
		int hours = (Integer)map.get(TimeConst.CODE_REQUEST_HOUR);
		// 残日数及び残時間数を取得
		return TimeUtility.getHolidayRemains(dto, days, hours, hoursPerDay);
	}
	
}
