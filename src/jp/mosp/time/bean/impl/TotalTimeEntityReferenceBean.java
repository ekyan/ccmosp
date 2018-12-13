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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEntityReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.RequestDetectEntityInterface;
import jp.mosp.time.entity.TotalTimeEntityInterface;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠集計エンティティクラス。
 */
public class TotalTimeEntityReferenceBean extends TimeBean implements TotalTimeEntityReferenceBeanInterface {
	
	/**
	 * 勤怠情報取得対象となる締期間初日以前の日数。<br>
	 * <br>
	 * 締期間初日が週の最終日だった場合の6日と、その前日。<br>
	 * 前日が法定休日出勤だった場合に、7日前が必要になる。<br>
	 * <br>
	 */
	public static final int							DAYS_FORMER_ATTENDANCE	= -7;
	
	/**
	 * 入社情報DAOクラス。<br>
	 */
	protected EntranceDaoInterface					entranceDao;
	
	/**
	 * 休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface		suspentionReference;
	
	/**
	 * 退社情報DAOクラス。<br>
	 */
	protected RetirementDaoInterface				retirementDao;
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface					holidayDao;
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface				attendanceDao;
	
	/**
	 * 残業申請データDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface			overtimeRequestDao;
	
	/**
	 * 休暇申請データDAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface			holidayRequestDao;
	
	/**
	 * 休日出勤申請データDAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface		workOnHolidayRequestDao;
	
	/**
	 * 代休申請データDAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface			subHolidayRequestDao;
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface		workTypeChangeRequestDao;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface			differenceRequestDao;
	
	/**
	 * 振替休日データDAOクラス。<br>
	 */
	protected SubstituteDaoInterface				substituteDao;
	
	/**
	 * 代休データDAOクラス。<br>
	 */
	protected SubHolidayDaoInterface				subHolidayDao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface					workflowDao;
	
	/**
	 * 勤怠トランザクションDAO。<br>
	 */
	protected AttendanceTransactionDaoInterface		attendanceTransactionDao;
	
	/**
	 * 勤怠設定管理DAOクラス。<br>
	 */
	protected TimeSettingDaoInterface				timeSettingDao;
	
	/**
	 * カレンダ日管理参照。<br>
	 */
	protected ScheduleDateReferenceBeanInterface	scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface		workTypeReference;
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface			platformMaster;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// Bean及びDAO準備
		entranceDao = (EntranceDaoInterface)createDao(EntranceDaoInterface.class);
		suspentionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		retirementDao = (RetirementDaoInterface)createDao(RetirementDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		overtimeRequestDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		workTypeChangeRequestDao = (WorkTypeChangeRequestDaoInterface)createDao(
				WorkTypeChangeRequestDaoInterface.class);
		differenceRequestDao = (DifferenceRequestDaoInterface)createDao(DifferenceRequestDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		timeSettingDao = (TimeSettingDaoInterface)createDao(TimeSettingDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(
				ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		subHolidayDao = (SubHolidayDaoInterface)createDao(SubHolidayDaoInterface.class);
		attendanceTransactionDao = (AttendanceTransactionDaoInterface)createDao(
				AttendanceTransactionDaoInterface.class);
	}
	
	@Override
	public TotalTimeEntityInterface getTotalTimeEntity(String personalId, int targetYear, int targetMonth,
			CutoffDtoInterface cutoffDto) throws MospException {
		// 勤怠集計エンティティを準備
		TotalTimeEntityInterface entity = (TotalTimeEntityInterface)createObject(TotalTimeEntityInterface.class);
		// 個人ID設定
		entity.setPersonalId(personalId);
		// 対象年月設定
		entity.setCalculationYear(targetYear);
		entity.setCalculationMonth(targetMonth);
		// 締日情報が取得できない場合
		if (cutoffDto == null) {
			return entity;
		}
		// 締日コード設定
		entity.setCutoffCode(cutoffDto.getCutoffCode());
		// 締日を取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 締期間基準日を取得
		Date cutoffTermTargetDate = TimeUtility.getCutoffTermTargetDate(cutoffDate, targetYear, targetMonth);
		// 締期間初日を取得
		Date firstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth);
		// 締期間最終日を取得
		Date lastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth);
		// 締期間初日(個人)を取得
		Date personalFirstDate = getPersonalFirstDate(personalId, targetYear, targetMonth, cutoffDate);
		// 締期間最終日(個人)を取得
		Date personalLastDate = getPersonalLastDate(personalId, targetYear, targetMonth, cutoffDate);
		// 締期間(個人)が取得できなかった場合
		if (personalFirstDate == null || personalLastDate == null) {
			// 計算対象外
			return entity;
		}
		// 締期間(個人)対象日リストを取得
		List<Date> targetDateList = TimeUtility.getDateList(personalFirstDate, personalLastDate);
		// 締期間初日の7日前を取得(週40時間計算用に勤怠申請情報を取得するため)
		Date attendanceFirstDate = DateUtility.addDay(firstDate, DAYS_FORMER_ATTENDANCE);
		// 休暇種別情報群取得
		entity.setHolidaySet(timeMaster.getHolidaySet(cutoffTermTargetDate));
		// 締期間初日設定
		entity.setCutoffFirstDate(firstDate);
		// 締期間最終日設定
		entity.setCutoffLastDate(lastDate);
		// 休職情報リスト設定
		entity.setSuspensionList(suspentionReference.getSuspentionList(personalId));
		// 締期間(個人)対象日リスト設定
		entity.setTargetDateList(targetDateList);
		// 設定適用情報群設定(締期間(個人))
		entity.setApplicationMap(timeMaster.getApplicationMap(personalId, attendanceFirstDate, personalLastDate));
		// 勤怠設定情報群設定(締期間(個人))
		entity.setTimeSettingMap(timeMaster.getTimeSettingMap(entity.getApplicationMap()));
		// カレンダ日情報群設定(締期間(個人))
		entity.setScheduleMap(getScheduleMap(personalId, targetDateList));
		// 勤怠申請リスト取得
		entity.setAttendanceList(attendanceDao.findForList(personalId, attendanceFirstDate, lastDate));
		// 休暇申請リスト取得
		entity.setHolidayRequestList(holidayRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 休日出勤申請リスト取得
		entity.setWorkOnHolidayRequestList(workOnHolidayRequestDao.findForList(personalId, firstDate, lastDate));
		// 残業申請リスト取得
		entity.setOvertimeRequestList(overtimeRequestDao.findForList(personalId, firstDate, lastDate));
		// 勤務形態変更申請リスト取得
		entity.setWorkTypeChangeRequestList(workTypeChangeRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 時差出勤申請リスト取得
		entity.setDifferenceRequestList(differenceRequestDao.findForList(personalId, firstDate, lastDate));
		// 振替休日データ取得
		entity.setSubstitubeList(substituteDao.findForTerm(personalId, firstDate, lastDate));
		// 締期間初日(個人)から代休取得期限だけ遡った日付を取得
		Date subHolidayFirstDate = getDateOnTimeSetteingDto(entity);
		// 代休データリスト取得
		entity.setSubHolidayList(subHolidayDao.findSubHolidayList(personalId, subHolidayFirstDate, lastDate));
		// 代休勤怠設定マップ取得
		entity.setSubHolidayTimeSettingMap(getSubHolidayTimeSettingMap(entity.getSubHolidayList()));
		// 代休申請リスト取得
		entity.setSubHolidayRequestList(subHolidayRequestDao.findForList(personalId, subHolidayFirstDate, lastDate));
		// ワークフロー日付範囲を取得
		Date workflowFirstDate = getRequestStartDateForWorkflow(entity);
		Date workflowLastDate = getRequestEndDateForWorkflow(entity);
		// ワークフロー情報群取得
		entity.setWorkflowMap(workflowDao.findForCondition(personalId, workflowFirstDate, workflowLastDate));
		entity.setAttendanceTransactionSet(getAttendanceTransactionSet(personalId, firstDate, lastDate));
		// 振替勤務形態コード群設定
		entity.setSubstitutedMap(getSubstitutedMap(entity));
		// 勤務形態エンティティ群を準備
		Map<String, List<WorkTypeEntity>> workTypeEntityMap = Collections.emptyMap();
		// 時短時間機能利用可の場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)) {
			// 勤務形態エンティティ群を取得
			workTypeEntityMap = getWorkTypeEntityMap(entity);
		}
		// 勤務形態エンティティ群を設定
		entity.setWorkTypeEntityMap(workTypeEntityMap);
		// 勤怠集計エンティティを取得
		return entity;
	}
	
	/**
	 * 締期間初日(個人)を取得する。<br>
	 * 対象個人IDの最も古い人事基本情報の有効日が締期間初日よりも後の場合は、
	 * 最も古い人事基本情報の有効日を取得する。<br>
	 * 対象個人IDの入社日が締期間初日よりも後の場合は、入社日を取得する。<br>
	 * 対象個人IDが入社していない場合
	 * 及び人事基本情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 締期間初日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalFirstDate(String personalId, int targetYear, int targetMonth, int cutoffDate)
			throws MospException {
		// 入社日取得
		EntranceDtoInterface entranceDto = entranceDao.findForInfo(personalId);
		// 入社日確認
		if (entranceDto == null) {
			return null;
		}
		// 人事基本情報履歴を取得
		List<HumanDtoInterface> humanList = platformMaster.getHumanHistory(personalId);
		// 人事基本情報が存在しない場合
		if (humanList.isEmpty()) {
			return null;
		}
		// 締期間初日を取得
		Date cutoffFirstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth);
		// 締期間初日が最も古い人事基本情報の有効日よりも前の場合
		if (cutoffFirstDate.before(humanList.get(0).getActivateDate())) {
			// 最も古い人事基本情報の有効日を取得
			return humanList.get(0).getActivateDate();
		}
		// 締期間初日が入社日よりも前の場合
		if (cutoffFirstDate.before(entranceDto.getEntranceDate())) {
			// 入社日を取得
			return entranceDto.getEntranceDate();
		}
		// 締期間初日を取得
		return cutoffFirstDate;
	}
	
	/**
	 * 締期間最終日(個人)を取得する。<br>
	 * 対象個人IDの退社日が締期間最終日よりも前の場合は、退社日を取得する。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 締期間最終日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalLastDate(String personalId, int targetYear, int targetMonth, int cutoffDate)
			throws MospException {
		// 締期間最終日を取得
		Date cutoffLastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth);
		// 退社日取得
		RetirementDtoInterface retirementDto = retirementDao.findForInfo(personalId);
		// 退社日確認
		if (retirementDto == null) {
			return cutoffLastDate;
		}
		// 締期間最終日が退社日よりも後の場合
		if (cutoffLastDate.after(retirementDto.getRetirementDate())) {
			// 退社日を取得
			return retirementDto.getRetirementDate();
		}
		// 締期間最終日を取得
		return cutoffLastDate;
	}
	
	/**
	 * 締期間初日から代休取得期限だけ遡った日付を取得する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 締期間初日(個人)から代休取得期限だけ遡った日付
	 */
	protected Date getDateOnTimeSetteingDto(TotalTimeEntityInterface entity) {
		// 締期間初日を取得
		Date targetDate = entity.getCutoffFirstDate();
		// 勤怠設定取得
		TimeSettingDtoInterface timeSettingDto = entity.getTimeSettingMap().get(targetDate);
		if (timeSettingDto == null) {
			return entity.getCutoffFirstDate();
		}
		Date date = addDay(DateUtility.addMonth(targetDate, -timeSettingDto.getSubHolidayLimitMonth()),
				-timeSettingDto.getSubHolidayLimitDate());
		return date;
	}
	
	/**
	 * 代休出勤日の勤怠設定情報マップを取得する。<br>
	 * @param subHolidayList 代休リスト
	 * @return 代休出勤日の勤怠設定情報マップ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Map<Date, TimeSettingDtoInterface> getSubHolidayTimeSettingMap(
			List<SubHolidayDtoInterface> subHolidayList) throws MospException {
		// マップ準備
		Map<Date, TimeSettingDtoInterface> map = new HashMap<Date, TimeSettingDtoInterface>();
		// 代休情報毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 休日出勤日取得
			Date workDate = subHolidayDto.getWorkDate();
			// 勤怠設定情報取得
			TimeSettingDtoInterface dto = map.get(workDate);
			if (dto != null) {
				continue;
			}
			// 勤怠設定情報マップ取得
			Map<Date, TimeSettingDtoInterface> tmpMap = timeMaster
				.getTimeSettingMap(timeMaster.getApplicationMap(subHolidayDto.getPersonalId(), workDate, workDate));
			if (tmpMap == null) {
				continue;
			}
			map.putAll(tmpMap);
		}
		return map;
	}
	
	/**
	 * 申請初日(期間内)を取得する。<br>
	 * <br>
	 * ワークフロー情報を取得するのに用いる。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 申請初日(期間内)
	 */
	protected Date getRequestStartDateForWorkflow(TotalTimeEntityInterface entity) {
		// 申請初日を準備(締期間初日)
		Date startDate = entity.getCutoffFirstDate();
		// 勤怠申請リスト
		for (AttendanceDtoInterface dto : entity.getAttendanceList()) {
			if (startDate.compareTo(dto.getWorkDate()) > 0) {
				startDate = dto.getWorkDate();
				continue;
			}
		}
		// 	休暇申請リスト
		for (HolidayRequestDtoInterface dto : entity.getHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestStartDate()) > 0) {
				startDate = dto.getRequestStartDate();
				continue;
			}
		}
		// 休日出勤申請リスト
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 振替休日データリスト
		for (SubstituteDtoInterface dto : entity.getSubstitubeList()) {
			if (startDate.compareTo(dto.getWorkDate()) > 0) {
				startDate = dto.getWorkDate();
				continue;
			}
		}
		// 代休申請リスト
		for (SubHolidayRequestDtoInterface dto : entity.getSubHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 残業申請リスト
		for (OvertimeRequestDtoInterface dto : entity.getOvertimeRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 勤務形態変更申請リスト
		for (WorkTypeChangeRequestDtoInterface dto : entity.getWorkTypeChangeRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 時差出勤申請リスト
		for (DifferenceRequestDtoInterface dto : entity.getDifferenceRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		return startDate;
	}
	
	/**
	 * 申請最終日(期間内)を取得する。<br>
	 * <br>
	 * ワークフロー情報を取得するのに用いる。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 申請最終日(期間内)
	 */
	protected Date getRequestEndDateForWorkflow(TotalTimeEntityInterface entity) {
		// 締期間最終日取得
		Date endDate = entity.getCutoffLastDate();
		// 振替休日データリスト
		for (SubstituteDtoInterface dto : entity.getSubstitubeList()) {
			if (endDate.compareTo(dto.getWorkDate()) < 0) {
				endDate = dto.getWorkDate();
				continue;
			}
		}
		return endDate;
	}
	
	/**
	 * 勤怠トランザクション群を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間末日
	 * @return 勤怠トランザクション群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<AttendanceTransactionDtoInterface> getAttendanceTransactionSet(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		// 勤怠トランザクションマップを取得
		Map<Date, AttendanceTransactionDtoInterface> map = attendanceTransactionDao.findForTerm(personalId, firstDate,
				lastDate);
		// セットに変換
		return new HashSet<AttendanceTransactionDtoInterface>(map.values());
	}
	
	@Override
	public RequestDetectEntityInterface getRequestDetectEntity(TotalTimeEntityInterface totalTimeEntity)
			throws MospException {
		// 申請確認エンティティを準備
		RequestDetectEntityInterface entity = (RequestDetectEntityInterface)createObject(
				RequestDetectEntityInterface.class);
		// 勤怠集計エンティティから必要な情報を取得
		entity.setTargetDateList(totalTimeEntity.getTargetDateList());
		entity.setSuspensionList(totalTimeEntity.getSuspensionList());
		entity.setScheduleMap(totalTimeEntity.getScheduleMap());
		entity.setAttendanceList(totalTimeEntity.getAttendanceList());
		entity.setWorkOnHolidayRequestList(totalTimeEntity.getWorkOnHolidayRequestList());
		entity.setHolidayRequestList(totalTimeEntity.getHolidayRequestList());
		entity.setSubHolidayRequestList(totalTimeEntity.getSubHolidayRequestList());
		entity.setOvertimeRequestList(totalTimeEntity.getOvertimeRequestList());
		entity.setWorkTypeChangeRequestList(totalTimeEntity.getWorkTypeChangeRequestList());
		entity.setDifferenceRequestList(totalTimeEntity.getDifferenceRequestList());
		entity.setSubstituteList(totalTimeEntity.getSubstitubeList());
		entity.setWorkflowMap(totalTimeEntity.getWorkflowMap());
		// 申請確認エンティティを取得
		return entity;
	}
	
	@Override
	public RequestDetectEntityInterface getRequestDetectEntity(String personalId, int targetYear, int targetMonth,
			int cutoffDate) throws MospException {
		// 申請検出エンティティを準備
		RequestDetectEntityInterface entity = (RequestDetectEntityInterface)createObject(
				RequestDetectEntityInterface.class);
		// 締期間初日及び最終日(個人)を取得
		Date firstDate = getPersonalFirstDate(personalId, targetYear, targetMonth, cutoffDate);
		Date lastDate = getPersonalLastDate(personalId, targetYear, targetMonth, cutoffDate);
		// 締期間(個人)対象日リストを取得
		List<Date> targetDateList = TimeUtility.getDateList(firstDate, lastDate);
		// 締期間(個人)予定勤務形態コード群を取得
		Map<Date, String> scheduleMap = getScheduleMap(personalId, targetDateList);
		// 申請検出エンティティに個人IDを設定
		entity.setPersonalId(personalId);
		// 申請検出エンティティに対象日リストを設定
		entity.setTargetDateList(targetDateList);
		// 申請検出エンティティに予定勤務形態コード群を設定
		entity.setScheduleMap(scheduleMap);
		// 休職情報を設定
		entity.setSuspensionList(suspentionReference.getSuspentionList(personalId));
		// 締期間(個人)における勤怠申請リスト取得
		entity.setAttendanceList(attendanceDao.findForList(personalId, DateUtility.addDay(firstDate, -6), lastDate));
		// 休暇申請リスト取得
		entity.setHolidayRequestList(holidayRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 休日出勤申請リスト取得
		entity.setWorkOnHolidayRequestList(workOnHolidayRequestDao.findForList(personalId, firstDate, lastDate));
		// 残業申請リスト取得
		entity.setOvertimeRequestList(overtimeRequestDao.findForList(personalId, firstDate, lastDate));
		// 勤務形態変更申請リスト取得
		entity.setWorkTypeChangeRequestList(workTypeChangeRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 時差出勤申請リスト取得
		entity.setDifferenceRequestList(differenceRequestDao.findForList(personalId, firstDate, lastDate));
		// 代休申請リスト取得
		entity.setSubHolidayRequestList(subHolidayRequestDao.findForList(personalId, firstDate, lastDate));
		// 振替休日リスト取得
		entity.setSubstituteList(substituteDao.findForTerm(personalId, firstDate, lastDate));
		// ワークフロー情報群取得
		entity.setWorkflowMap(workflowDao.findForCondition(personalId, firstDate, lastDate));
		// 申請検出エンティティを取得
		return entity;
	}
	
	/**
	 * カレンダ日情報から予定勤務形態コード群を取得する。<br>
	 * <br>
	 * 対象日をキーとし、値には勤務形態コードを設定する。<br>
	 * カレンダ日情報から対象日の勤務形態コードが取得できない場合は、
	 * 空白を設定する。<br>
	 * <br>
	 * @param personalId     対象個人ID
	 * @param targetDateList 対象日リスト
	 * @return 予定勤務形態コード群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Date, String> getScheduleMap(String personalId, List<Date> targetDateList) throws MospException {
		// 予定勤務形態コード群を準備
		Map<Date, String> scheduleMap = new HashMap<Date, String>();
		// 対象日リストが空である場合
		if (targetDateList == null || targetDateList.isEmpty()) {
			return scheduleMap;
		}
		// 締期間初日及び最終日(個人)を取得
		Date firstDate = targetDateList.get(0);
		Date lastDate = targetDateList.get(targetDateList.size() - 1);
		// 設定適用情報群を取得
		Map<Date, ApplicationDtoInterface> appMap = timeMaster.getApplicationMap(personalId, firstDate, lastDate);
		// 締期間(対象日)内の日毎に処理
		for (Date targetDate : targetDateList) {
			// 設定適用情報を取得
			ApplicationDtoInterface appDto = appMap.get(targetDate);
			// 設定適用情報が取得できなかった場合
			if (appDto == null) {
				// 空白を設定
				scheduleMap.put(targetDate, "");
				continue;
			}
			// カレンダコードを取得
			String scheduleCode = appDto.getScheduleCode();
			// カレンダ日情報を勤怠関連マスタ参照クラスに設定
			timeMaster.addScheduleDateMap(scheduleCode, firstDate, lastDate);
			// カレンダ日情報を取得
			ScheduleDateDtoInterface scheduleDto = timeMaster.getScheduleDate(scheduleCode, targetDate);
			// カレンダ日情報が取得できなかった場合
			if (scheduleDto == null) {
				// 空白を設定
				scheduleMap.put(targetDate, "");
				continue;
			}
			// 予定勤務形態コードを設定
			scheduleMap.put(targetDate, scheduleDto.getWorkTypeCode());
		}
		// 予定勤務形態コード群を取得
		return scheduleMap;
	}
	
	/**
	 * 振出・休出勤務形態コード群を取得する。<br>
	 * <br>
	 * 振出・休出申請により出勤する日の予定勤務形態コード群を取得する。<br>
	 * 但し、承認済でない振出・休出申請は、考慮しない。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 振替勤務形態コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Date, String> getSubstitutedMap(TotalTimeEntityInterface entity) throws MospException {
		// 振替勤務形態コード群を準備
		Map<Date, String> substitutedMap = new HashMap<Date, String>();
		// 対象日リスト内の日毎に処理
		for (Date targetDate : entity.getTargetDateList()) {
			// 振替勤務形態コード群に空白を設定
			substitutedMap.put(targetDate, "");
		}
		// 個人IDを取得
		String personalId = entity.getPersonalId();
		// 振出・休出申請毎に処理
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			// 承認済でない場合
			if (WorkflowUtility.isCompleted(entity.getWorkflowDto(dto.getWorkflow())) == false) {
				// 処理無し
				continue;
			}
			// 申請日(振出・休出日)を取得
			Date requestDate = dto.getRequestDate();
			// 振替申請フラグと休出種別確認を取得
			int substitute = dto.getSubstitute();
			String workOnHolidayType = dto.getWorkOnHolidayType();
			// 休日出勤の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 法定休出の場合
				if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
					// 法定休日出勤を設定
					substitutedMap.put(requestDate, TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY);
				}
				// 所定休出の場合
				if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
					// 所定休日出勤を設定
					substitutedMap.put(requestDate, TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY);
				}
				continue;
			}
			// 振替出勤(勤務形態変更)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 振替勤務形態コードを設定
				substitutedMap.put(requestDate, dto.getWorkTypeCode());
				continue;
			}
			// 振替出勤(全休か午前か午後)の場合
			// 振替休日情報毎に処理
			for (SubstituteDtoInterface substituteDto : entity.getSubstitubeList()) {
				// 振替出勤日が異なる場合
				if (substituteDto.getWorkDate().compareTo(requestDate) != 0) {
					// 処理無し
					continue;
				}
				// 振替日を取得
				Date substituteDate = substituteDto.getSubstituteDate();
				// 人事基本情報を取得
				HumanDtoInterface humanDto = platformMaster.getHuman(personalId, substituteDate);
				// 設定適用情報を取得
				ApplicationDtoInterface aplicationDto = timeMaster.getApplication(humanDto, substituteDate);
				// カレンダコードを取得
				String scheduleCode = aplicationDto.getScheduleCode();
				// カレンダ日情報を取得
				ScheduleDateDtoInterface scheduleDateDto = timeMaster.getScheduleDate(scheduleCode, substituteDate);
				// カレンダ日情報が存在しない場合
				if (scheduleDateDto == null) {
					// 処理無し
					continue;
				}
				// 振替日の予定勤務形態を設定
				substitutedMap.put(requestDate, scheduleDateDto.getWorkTypeCode());
			}
		}
		// 振替勤務形態コード群を取得
		return substitutedMap;
	}
	
	/**
	 * 勤務形態エンティティ群を取得する。<br>
	 * <br>
	 * 勤怠集計エンティティに設定されてる次の勤務形態につき、
	 * 情報を取得する。<br>
	 * ・カレンダ日情報群<br>
	 * ・振替勤務形態コード群<br>
	 * ・振出・休出申請(勤務形態変更)<br>
	 * ・勤務形態変更申請<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 振替勤務形態コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, List<WorkTypeEntity>> getWorkTypeEntityMap(TotalTimeEntityInterface entity)
			throws MospException {
		// 勤務形態エンティティ群を準備
		Map<String, List<WorkTypeEntity>> map = new HashMap<String, List<WorkTypeEntity>>();
		// カレンダ日情報群の勤務形態コード毎に処理
		for (String workTypeCode : entity.getScheduleMap().values()) {
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 振替勤務形態コード群
		for (String workTypeCode : entity.getSubstitutedMap().values()) {
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 振出・休出申請申請
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			// 振替出勤(勤務形態変更)でない場合
			if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 処理無し
				continue;
			}
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 勤務形態変更申請
		for (WorkTypeChangeRequestDtoInterface dto : entity.getWorkTypeChangeRequestList()) {
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 勤務形態エンティティ群を取得
		return map;
	}
	
	@Override
	public void setTimeMasterBean(TimeMasterBeanInterface timeMaster) {
		this.timeMaster = timeMaster;
	}
	
	@Override
	public void setPlatformMasterBean(PlatformMasterBeanInterface platformMaster) {
		this.platformMaster = platformMaster;
	}
	
}
