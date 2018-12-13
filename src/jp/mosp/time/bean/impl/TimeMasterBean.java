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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠関連マスタ参照処理。<br>
 * <br>
 * 取得したマスタの情報をフィールドに保持しておき、
 * DBにアクセスすることなく再取得できるようにする。<br>
 * <br>
 * 集計や一覧表示で一件ずつ処理する場合等、一度取得した
 * マスタの情報を使い回すことで、パフォーマンスの向上を図る。<br>
 * <br>
 * 一覧表示で件数が少ない場合にパフォーマンスが落ちないように、
 * マスタ全件を一度に取得するような処理は行わない。<br>
 * <br>
 * DBにアクセスする回数が減る分メモリを使うことになるため、
 * 保持する情報の量に応じてメモリを調整する必要がある。<br>
 * <br>
 */
public class TimeMasterBean extends PlatformBean implements TimeMasterBeanInterface {
	
	/**
	 * 設定適用マスタDAOクラス。<br>
	 */
	protected ApplicationDaoInterface							applicationDao;
	
	/**
	 * 勤怠設定管理DAOクラス。<br>
	 */
	protected TimeSettingDaoInterface							timeSettingDao;
	
	/**
	 * 有給休暇設定DAOクラス。<br>
	 */
	protected PaidHolidayDaoInterface							paidHolidayDao;
	
	/**
	 * 締日管理DAOクラス。<br>
	 */
	protected CutoffDaoInterface								cutoffDao;
	
	/**
	 * カレンダ日情報DAOクラス。<br>
	 */
	protected ScheduleDateDaoInterface							scheduleDateDao;
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface								holidayDao;
	
	/**
	 * 勤務形態参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface					workTypeRefer;
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface						platformMaster;
	
	/**
	 * From日付(設定適用情報有効日群取得範囲)。<br>
	 */
	protected Date												applicationFromDate;
	
	/**
	 * To日付(設定適用情報有効日群取得範囲)。<br>
	 */
	protected Date												applicationToDate;
	
	/**
	 * 設定適用情報有効日群。<br>
	 * <br>
	 * 設定適用情報有効日群取得範囲に有効日を持つ設定適用情報の有効日群。<br>
	 * <br>
	 */
	protected Set<Date>											applicationDateSet;
	
	/**
	 * 設定適用情報(個人)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<ApplicationDtoInterface>>			applicationPersonMap;
	
	/**
	 * 設定適用情報(マスタ)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<ApplicationDtoInterface>>			applicationMasterMap;
	
	/**
	 * 勤怠設定情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の勤怠設定情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<TimeSettingDtoInterface>>			timeSettingMap;
	
	/**
	 * 有給休暇設定情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の有給休暇設定情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<PaidHolidayDtoInterface>>			paidHolidayMap;
	
	/**
	 * 締日管理情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の締日管理情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<CutoffDtoInterface>>				cutoffMap;
	
	/**
	 * カレンダ日情報群(キー：カレンダコード)。<br>
	 */
	protected Map<String, Map<Date, ScheduleDateDtoInterface>>	scheduleMap;
	
	/**
	 * 勤務形態エンティティ群(キー：勤務形態コード)。<br>
	 * <br>
	 * 値は勤務形態エンティティ履歴(有効日昇順)。<br>
	 * <br>
	 */
	protected Map<String, List<WorkTypeEntity>>					workTypeMap;
	
	/**
	 * 休暇種別情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の休暇種別情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<HolidayDtoInterface>>				holidayMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TimeMasterBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TimeMasterBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		applicationDao = (ApplicationDaoInterface)createDao(ApplicationDaoInterface.class);
		timeSettingDao = (TimeSettingDaoInterface)createDao(TimeSettingDaoInterface.class);
		paidHolidayDao = (PaidHolidayDaoInterface)createDao(PaidHolidayDaoInterface.class);
		cutoffDao = (CutoffDaoInterface)createDao(CutoffDaoInterface.class);
		scheduleDateDao = (ScheduleDateDaoInterface)createDao(ScheduleDateDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		// Beanを準備
		workTypeRefer = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		platformMaster = (PlatformMasterBeanInterface)createBean(PlatformMasterBeanInterface.class);
		// フィールドの初期化
		applicationDateSet = new HashSet<Date>();
		applicationPersonMap = new HashMap<Date, Set<ApplicationDtoInterface>>();
		applicationMasterMap = new HashMap<Date, Set<ApplicationDtoInterface>>();
		timeSettingMap = new HashMap<Date, Set<TimeSettingDtoInterface>>();
		paidHolidayMap = new HashMap<Date, Set<PaidHolidayDtoInterface>>();
		cutoffMap = new HashMap<Date, Set<CutoffDtoInterface>>();
		scheduleMap = new HashMap<String, Map<Date, ScheduleDateDtoInterface>>();
		workTypeMap = new HashMap<String, List<WorkTypeEntity>>();
		holidayMap = new HashMap<Date, Set<HolidayDtoInterface>>();
	}
	
	@Override
	public ApplicationDtoInterface getApplication(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 設定適用情報群取得
		Set<ApplicationDtoInterface> personSet = getApplicationPersonSet(targetDate);
		Set<ApplicationDtoInterface> masterSet = getApplicationMasterSet(targetDate);
		// 適用情報群から対象人事情報が適用される適用情報を取得
		return (ApplicationDtoInterface)PlatformUtility.getApplicationMaster(humanDto, personSet, masterSet);
	}
	
	@Override
	public PaidHolidayDtoInterface getPaidHoliday(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 対象人事情報が適用される適用情報を取得
		ApplicationDtoInterface application = getApplication(humanDto, targetDate);
		// 適用情報が取得できなかった場合
		if (application == null) {
			// nullを取得
			return null;
		}
		// 有給休暇設定情報を取得
		return getPaidHoliday(application.getPaidHolidayCode(), targetDate);
	}
	
	@Override
	public Map<Date, ApplicationDtoInterface> getApplicationMap(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 設定適用郡準備
		Map<Date, ApplicationDtoInterface> map = new HashMap<Date, ApplicationDtoInterface>();
		// 人事情報履歴(有効日昇順)を取得
		List<HumanDtoInterface> humanList = platformMaster.getHumanHistory(personalId);
		// 有効日リスト取得
		Set<Date> activateDateSet = getApplicationDateSet(firstDate, lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 有効日追加
			activateDateSet.add(humanDto.getActivateDate());
		}
		// 期間開始日を追加
		activateDateSet.add(firstDate);
		// 設定適用準備
		ApplicationDtoInterface applicationDto = null;
		// 期間開始日日毎に設定
		for (Date targetDate : TimeUtility.getDateList(firstDate, lastDate)) {
			// 有効日セットに含まれている場合
			if (activateDateSet.contains(targetDate)) {
				// 対象日以前で最新の人事情報を取得
				HumanDtoInterface humanDto = (HumanDtoInterface)PlatformUtility.getLatestDto(humanList, targetDate);
				// 人事情報が存在する場合
				if (humanDto != null) {
					// 設定適用を再設定
					applicationDto = getApplication(humanDto, targetDate);
				}
			}
			// 設定適用追加
			map.put(targetDate, applicationDto);
		}
		return map;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティを取得
		return getApplicationEntity(humanDto, targetDate);
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 設定適用情報取得
		ApplicationDtoInterface applicationDto = getApplication(humanDto, targetDate);
		// 設定適用エンティティ準備
		ApplicationEntity applicationEntity = new ApplicationEntity(applicationDto);
		// 設定適用情報確認
		if (applicationDto == null) {
			// 設定適用情報が取得できない場合
			return applicationEntity;
		}
		// 勤怠設定コード取得
		String workSettingCode = applicationEntity.getWorkSettingCode();
		// 設定適用エンティティに勤怠設定情報を設定
		applicationEntity.setTimeSettingDto(getTimeSetting(workSettingCode, targetDate));
		// 締日コード取得
		String cutoffCode = applicationEntity.getCutoffCode();
		// 設定適用エンティティに締日情報を設定
		applicationEntity.setCutoffDto(getCutoff(cutoffCode, targetDate));
		// 有給休暇設定コードを取得
		String paidHolidayCode = applicationEntity.getPaidHolidayCode();
		// 設定適用エンティティに有給休暇設定情報を設定
		applicationEntity.setPaidHolidayDto(getPaidHoliday(paidHolidayCode, targetDate));
		// 設定適用エンティティを取得
		return applicationEntity;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException {
		// 設定適用エンティティを取得
		return getApplicationEntity(platformMaster.getHuman(personalId, targetDate), targetDate);
	}
	
	@Override
	public Map<Date, TimeSettingDtoInterface> getTimeSettingMap(Map<Date, ApplicationDtoInterface> applicationMap)
			throws MospException {
		// 勤怠設定情報郡を準備
		Map<Date, TimeSettingDtoInterface> map = new HashMap<Date, TimeSettingDtoInterface>();
		// 設定適用情報毎に処理
		for (Entry<Date, ApplicationDtoInterface> entry : applicationMap.entrySet()) {
			// 対象日を取得
			Date targetDate = entry.getKey();
			// 設定適用情報を取得
			ApplicationDtoInterface applicationDto = entry.getValue();
			// 設定適用情報が取得できない場合
			if (applicationDto == null) {
				// 勤怠設定情報郡にnullを設定
				map.put(targetDate, null);
				continue;
			}
			// 設定適用情報を取得し勤怠設定情報郡に設定
			map.put(targetDate, getTimeSetting(applicationDto.getWorkSettingCode(), targetDate));
		}
		// 勤怠設定情報郡を取得
		return map;
	}
	
	@Override
	public CutoffDtoInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 締日情報を取得
		return getCutoff(cutoffCode, targetDate);
	}
	
	@Override
	public CutoffDtoInterface getCutoff(String cutoffCode, Date targetDate) throws MospException {
		// 締日コードがnullである場合
		if (cutoffCode == null) {
			return null;
		}
		// フィールドから締日管理情報群を取得
		Set<CutoffDtoInterface> set = cutoffMap.get(targetDate);
		// フィールドから締日管理情報群を取得できなかった場合
		if (set == null) {
			// 締日管理情報群を準備しフィールドに設定
			set = new HashSet<CutoffDtoInterface>();
			cutoffMap.put(targetDate, set);
		}
		// 締日管理情報毎に処理
		for (CutoffDtoInterface dto : set) {
			// コードが一致する場合
			if (dto.getCutoffCode().equals(cutoffCode)) {
				// 締日管理情報を取得
				return dto;
			}
		}
		// DBから締日管理情報を取得(フィールドから締日管理情報を取得できなかった場合)
		CutoffDtoInterface dto = cutoffDao.findForInfo(cutoffCode, targetDate);
		// DBから締日管理情報を取得できなかった場合
		if (dto == null) {
			return null;
		}
		// フィールドに設定
		set.add(dto);
		// 締日管理情報を取得
		return dto;
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDate(String scheduleCode, Date targetDate) throws MospException {
		// フィールドからカレンダ日情報群を取得
		Map<Date, ScheduleDateDtoInterface> map = scheduleMap.get(scheduleCode);
		// フィールドからカレンダ日情報群を取得できなかった場合
		if (map == null) {
			// カレンダ日情報群を準備しフィールドに設定
			map = new HashMap<Date, ScheduleDateDtoInterface>();
			scheduleMap.put(scheduleCode, map);
		}
		// 対象日のカレンダ日情報を取得
		ScheduleDateDtoInterface dto = map.get(targetDate);
		// 対象日のカレンダ日情報が取得できた場合
		if (dto != null) {
			// カレンダ日情報を取得
			return dto;
		}
		// DBからカレンダ日情報を取得(フィールドからカレンダ日情報を取得できなかった場合)
		dto = scheduleDateDao.findForKey(scheduleCode, targetDate);
		// フィールドに設定(nullであればnullを設定)
		map.put(targetDate, dto);
		// カレンダ日情報を取得
		return dto;
	}
	
	@Override
	public void addScheduleDateMap(String scheduleCode, Date firstDate, Date lastDate) throws MospException {
		// フィールドからカレンダ日情報群を取得
		Map<Date, ScheduleDateDtoInterface> map = scheduleMap.get(scheduleCode);
		// フィールドに対象カレンダコードの情報が一つでも設定されている場合
		if (map != null && map.isEmpty() == false) {
			// 処理無し
			return;
		}
		// カレンダ日情報群を準備しフィールドに設定
		map = new HashMap<Date, ScheduleDateDtoInterface>();
		scheduleMap.put(scheduleCode, map);
		// DBからカレンダ日情報を取得
		List<ScheduleDateDtoInterface> list = scheduleDateDao.findForList(scheduleCode, firstDate, lastDate);
		// カレンダ日情報毎に処理
		for (ScheduleDateDtoInterface dto : list) {
			// カレンダ日情報群に設定
			map.put(dto.getScheduleDate(), dto);
		}
	}
	
	@Override
	public Set<HolidayDtoInterface> getHolidaySet(Date targetDate) throws MospException {
		// フィールドから休暇種別情報群を取得
		Set<HolidayDtoInterface> set = holidayMap.get(targetDate);
		// フィールドから休暇種別情報群を取得できなかった場合
		if (set == null) {
			// 休暇種別情報群をDBから準備しフィールドに設定
			set = holidayDao.findForActivateDate(targetDate);
			holidayMap.put(targetDate, set);
		}
		// 休暇種別情報群を取得
		return set;
	}
	
	/**
	 * 設定適用情報(個人)群(キー：対象日)を取得する。<br>
	 * <br>
	 * フィールドから設定適用情報(個人)群を取得できなかった場合は、
	 * 設定適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドから設定適用情報(個人)群を再取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 設定適用情報(個人)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<ApplicationDtoInterface> getApplicationPersonSet(Date targetDate) throws MospException {
		// フィールドから設定適用情報(個人)群を取得できなかった場合
		if (applicationPersonMap.get(targetDate) == null) {
			// 設定適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドから設定適用情報(個人)群を再取得
		return applicationPersonMap.get(targetDate);
	}
	
	/**
	 * 設定適用情報(マスタ)群(キー：対象日)を取得する。<br>
	 * <br>
	 * フィールドから設定適用情報(マスタ)群を取得できなかった場合は、
	 * 設定適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドから設定適用情報(マスタ)群を再取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 設定適用情報(マスタ)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<ApplicationDtoInterface> getApplicationMasterSet(Date targetDate) throws MospException {
		// フィールドから設定適用情報(マスタ)群を取得できなかった場合
		if (applicationMasterMap.get(targetDate) == null) {
			// 設定適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドから設定適用情報(マスタ)群を再取得
		return applicationMasterMap.get(targetDate);
	}
	
	/**
	 * 設定適用情報群をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApplicationSet(Date targetDate) throws MospException {
		// 設定適用情報群を準備
		Set<ApplicationDtoInterface> personSet = new HashSet<ApplicationDtoInterface>();
		Set<ApplicationDtoInterface> masterSet = new HashSet<ApplicationDtoInterface>();
		// フィールドに設定
		applicationPersonMap.put(targetDate, personSet);
		applicationMasterMap.put(targetDate, masterSet);
		// 適用範囲区分(比較用)を準備
		int person = Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON);
		int master = Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER);
		// 設定適用情報リストをDBから取得
		List<ApplicationDtoInterface> list = applicationDao.findForActivateDate(targetDate);
		// 設定適用情報毎に処理
		for (ApplicationDtoInterface dto : list) {
			// 適用範囲区分が個人指定の場合
			if (dto.getApplicationType() == person) {
				// 設定適用情報(個人)群に追加
				personSet.add(dto);
			}
			// 適用範囲区分がマスタ指定の場合
			if (dto.getApplicationType() == master) {
				// 設定適用情報(マスタ)群に追加
				masterSet.add(dto);
			}
		}
	}
	
	/**
	 * 設定適用情報有効日群を取得する。<br>
	 * <br>
	 * 期間が設定適用情報有効日群取得範囲外である場合、
	 * 設定適用情報リストを再取得しフィールドに設定する。<br>
	 * <br>
	 * @param firstDate  期間開始日
	 * @param lastDate   期間終了日
	 * @return 設定適用情報有効日群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<Date> getApplicationDateSet(Date firstDate, Date lastDate) throws MospException {
		// 設定適用情報有効日群取得範囲外フラグ準備
		boolean isOutOfTerm = false;
		// 期間開始日がFrom日付(設定適用情報有効日群取得範囲)より前の場合
		if (applicationFromDate == null || firstDate.before(applicationFromDate)) {
			// 設定適用情報有効日群取得範囲外フラグ設定
			isOutOfTerm = true;
			// From日付(設定適用情報有効日群取得範囲)を設定
			applicationFromDate = firstDate;
		}
		// 期間終了日がTo日付(設定適用情報有効日群取得範囲)より前の場合
		if (applicationToDate == null || lastDate.after(applicationToDate)) {
			// 設定適用情報有効日群取得範囲外フラグ設定
			isOutOfTerm = true;
			// To日付(設定適用情報有効日群取得範囲)を設定
			applicationToDate = lastDate;
		}
		// 設定適用情報有効日群取得範囲外フラグ確認
		if (isOutOfTerm) {
			// 設定適用情報有効日群準備
			applicationDateSet = new HashSet<Date>();
			// 設定適用情報取得
			List<ApplicationDtoInterface> list = applicationDao.findForTerm(applicationFromDate, applicationToDate);
			// 設定適用情報リスト毎に処理
			for (ApplicationDtoInterface dto : list) {
				// 有効日リストに追加
				applicationDateSet.add(dto.getActivateDate());
			}
		}
		// 設定適用情報有効日群を取得
		return applicationDateSet;
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSetting(String workSettingCode, Date targetDate) throws MospException {
		// フィールドから勤怠設定情報群を取得
		Set<TimeSettingDtoInterface> set = timeSettingMap.get(targetDate);
		// フィールドから勤怠設定情報群を取得できなかった場合
		if (set == null) {
			// 勤怠設定情報群を準備しフィールドに設定
			set = new HashSet<TimeSettingDtoInterface>();
			timeSettingMap.put(targetDate, set);
		}
		// 勤怠設定情報毎に処理
		for (TimeSettingDtoInterface dto : set) {
			// コードが一致する場合
			if (dto.getWorkSettingCode().equals(workSettingCode)) {
				// 勤怠設定情報を取得
				return dto;
			}
		}
		// DBから勤怠設定情報を取得(フィールドから勤怠設定情報を取得できなかった場合)
		TimeSettingDtoInterface dto = timeSettingDao.findForInfo(workSettingCode, targetDate);
		// フィールドに設定(nullであればnullを設定)
		set.add(dto);
		// 勤怠設定情報を取得
		return dto;
	}
	
	@Override
	public PaidHolidayDtoInterface getPaidHoliday(String paidHolidayCode, Date targetDate) throws MospException {
		// フィールドから有給休暇設定情報群を取得
		Set<PaidHolidayDtoInterface> set = paidHolidayMap.get(targetDate);
		// フィールドから勤怠設定情報群を取得できなかった場合
		if (set == null) {
			// 有給休暇設定情報群を準備しフィールドに設定
			set = new HashSet<PaidHolidayDtoInterface>();
			paidHolidayMap.put(targetDate, set);
		}
		// 有給休暇設定情報毎に処理
		for (PaidHolidayDtoInterface dto : set) {
			// コードが一致する場合
			if (dto.getPaidHolidayCode().equals(paidHolidayCode)) {
				// 有給休暇設定情報を取得
				return dto;
			}
		}
		// DBから勤怠設定情報を取得(フィールドから勤怠設定情報を取得できなかった場合)
		PaidHolidayDtoInterface dto = paidHolidayDao.findForInfo(paidHolidayCode, targetDate);
		// フィールドに設定(nullであればnullを設定)
		set.add(dto);
		// 勤怠設定情報を取得
		return dto;
	}
	
	@Override
	public int getPaidHolidayHoursPerDay(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 有給休暇設定情報を取得
		PaidHolidayDtoInterface dto = getPaidHoliday(humanDto, targetDate);
		// 有給休暇設定情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 有休時間取得限度時間を取得
		return dto.getTimeAcquisitionLimitTimes();
	}
	
	@Override
	public List<WorkTypeEntity> getWorkTypeEntityHistory(String workTypeCode) throws MospException {
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		List<WorkTypeEntity> history = workTypeMap.get(workTypeCode);
		// 勤務形態エンティティ履歴が取得できた場合
		if (history != null) {
			// 勤務形態エンティティ履歴(有効日昇順)を取得
			return history;
		}
		// 勤務形態エンティティ履歴(有効日昇順)を準備
		history = workTypeRefer.getWorkTypeEntityHistory(workTypeCode);
		// 勤務形態エンティティ群に追加
		workTypeMap.put(workTypeCode, history);
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		return history;
	}
	
}
