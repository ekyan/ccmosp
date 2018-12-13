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
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.HolidayExportBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇取得データエクスポートクラス。
 */
public class HolidayExportBean extends PlatformBean implements HolidayExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface					exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface				exportFieldDao;
	
	/**
	 * 休暇種別管理DAO。<br>
	 */
	protected HolidayDaoInterface					holidayDao;
	
	/**
	 * 休暇申請DAO。<br>
	 */
	protected HolidayRequestDaoInterface			holidayRequestDao;
	
	/**
	 * 代休申請DAO。<br>
	 */
	protected SubHolidayRequestDaoInterface			subHolidayRequestDao;
	
	/**
	 * 休日出勤申請DAO。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface		workOnHolidayRequestDao;
	
	/**
	 * 振替休日DAO。<br>
	 */
	protected SubstituteDaoInterface				substituteDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface		applicationReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBeanInterface		scheduleReference;
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	protected ScheduleDateReferenceBeanInterface	scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface		workTypeReference;
	
	/**
	 * 勤怠設定参照クラス。<br>
	 */
	protected TimeSettingReferenceBeanInterface		timeSettingReference;
	
	/**
	 * 締日ユーティリティクラス。<br>
	 */
	protected CutoffUtilBeanInterface				cutoffUtil;
	
	/**
	 * 人事マスタ検索クラス。<br>
	 */
	protected HumanSearchBeanInterface				humanSearch;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface			sectionReference;
	
	/**
	 * 人事マスタ情報リスト。<br>
	 */
	protected List<HumanDtoInterface>				humanList;
	
	/**
	 * 下位所属含むチェックボックス。
	 */
	private int										ckbNeedLowerSection	= 0;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayExportBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public HolidayExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanの準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(
				ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, int startYear, int startMonth, int endYear, int endMonth, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, int ckbNeedLowerSection,
			String positionCode) throws MospException {
		// 下位所属含むチェックボックスの設定
		this.ckbNeedLowerSection = ckbNeedLowerSection;
		ExportDtoInterface dto = exportDao.findForKey(exportCode);
		if (dto == null) {
			// 該当するエクスポート情報が存在しない場合
			addNoExportDataMessage();
			return;
		}
		// 締日マスタ取得
		CutoffDtoInterface cutoffDto = cutoffUtil.getCutoff(cutoffCode, startYear, startMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 締期間初日・最終日取得
		Date startDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), startYear, startMonth);
		Date endDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), endYear, endMonth);
		// CSVデータリストを作成
		List<String[]> list = getCsvDataList(dto, startDate, endDate, cutoffCode, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// CSVデータリスト確認
		if (list.isEmpty()) {
			// 該当するエクスポート情報が存在しない場合
			addNoExportDataMessage();
			return;
		}
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(list));
		// 送出ファイル名をMosP処理情報に設定
		setFileName(dto, startDate, endDate);
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * @param dto 対象DTO
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(ExportDtoInterface dto, Date startDate, Date endDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode)
			throws MospException {
		// CSVデータリスト準備
		List<String[]> list = new ArrayList<String[]>();
		// エクスポートフィールド情報取得
		List<ExportFieldDtoInterface> fieldList = exportFieldDao.findForList(dto.getExportCode());
		// 人事情報付加
		addHumanData(list, fieldList, startDate, endDate, cutoffCode, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// 有給休暇情報付加
		addPaidHolidayData(list, fieldList, startDate, endDate);
		// ストック休暇情報付加
		addStockHolidayData(list, fieldList, startDate, endDate);
		// 代休情報付加
		addSubHolidayData(list, fieldList, startDate, endDate);
		// 振替休日情報付加
		addSubstituteHolidayData(list, fieldList, startDate, endDate);
		// 休暇情報付加
		addHolidayData(list, fieldList, startDate, endDate);
		// ヘッダ情報付加
		if (dto.getHeader() != PlatformFileConst.HEADER_TYPE_NONE) {
			addHeader(list, fieldList, endDate);
		}
		return list;
	}
	
	/**
	 * CSVデータリストにヘッダを付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHeader(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date targetDate)
			throws MospException {
		// ヘッダ準備
		String[] header = new String[fieldList.size()];
		// フィールド毎にヘッダを付加
		for (int i = 0; i < header.length; i++) {
			header[i] = getHeader(fieldList.get(i).getFieldName(), targetDate);
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, header);
	}
	
	/**
	 * 検索条件に基づき人事情報を検索し、CSVデータリストに付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHumanData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date startDate,
			Date endDate, String cutoffCode, String workPlaceCode, String employmentContractCode, String sectionCode,
			String positionCode) throws MospException {
		// 人事情報検索条件設定(在職)
		humanSearch.setStartDate(startDate);
		humanSearch.setEndDate(endDate);
		humanSearch.setTargetDate(endDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否) 下位所属含むチェックボックスで判定
		if (ckbNeedLowerSection == 1) {
			humanSearch.setNeedLowerSection(true);
		} else {
			humanSearch.setNeedLowerSection(false);
		}
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 人事情報検索(在職)
		List<HumanDtoInterface> presenceHumanList = humanSearch.search();
		
		// 人事情報検索条件設定(休職)
		// 検索条件設定(状態)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_SUSPEND);
		// 人事情報検索(休職)
		List<HumanDtoInterface> suspendHumanList = humanSearch.search();
		
		// 人事情報検索(在職+休職)
		List<HumanDtoInterface> allHumanList = new ArrayList<HumanDtoInterface>();
		allHumanList.addAll(presenceHumanList);
		allHumanList.addAll(suspendHumanList);
		
		humanList = new ArrayList<HumanDtoInterface>();
		if (cutoffCode.isEmpty()) {
			humanList = allHumanList;
		} else {
			for (HumanDtoInterface humanDto : allHumanList) {
				ApplicationDtoInterface applicationDto = applicationReference.findForPerson(humanDto.getPersonalId(),
						endDate);
				if (applicationDto == null) {
					continue;
				}
				TimeSettingDtoInterface timeSettingDto = timeSettingReference
					.getTimeSettingInfo(applicationDto.getWorkSettingCode(), endDate);
				if (timeSettingDto == null) {
					continue;
				}
				if (!cutoffCode.equals(timeSettingDto.getCutoffCode())) {
					continue;
				}
				humanList.add(humanDto);
			}
		}
		// 人事情報毎にCSVデータリストに付加
		for (HumanDtoInterface humanDto : humanList) {
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			// フィールド情報毎にCSVデータに人事情報を付加
			for (int i = 0; i < csvData.length; i++) {
				csvData[i] = getHumanData(humanDto, fieldList.get(i).getFieldName(), endDate);
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
	}
	
	/**
	 * CSVデータリストに有給休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addPaidHolidayData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date startDate, Date endDate) throws MospException {
		// 有給休暇情報フィールド情報準備
		Integer paidHolidayAllIndex = null;
		Integer paidHolidayHalfIndex = null;
		Integer paidHolidayTimeIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			int index = dto.getFieldOrder() - 1;
			if (TimeFileConst.FIELD_PAID_HOLIDAY_ALL.equals(dto.getFieldName())) {
				// 有給休暇(全休)
				paidHolidayAllIndex = index;
			} else if (TimeFileConst.FIELD_PAID_HOLIDAY_HALF.equals(dto.getFieldName())) {
				// 有給休暇(半休)
				paidHolidayHalfIndex = index;
			} else if (TimeFileConst.FIELD_PAID_HOLIDAY_TIME.equals(dto.getFieldName())) {
				// 有給休暇(時休)
				paidHolidayTimeIndex = index;
			}
		}
		// 有給休暇情報出力要否確認
		if (paidHolidayAllIndex == null && paidHolidayHalfIndex == null && paidHolidayTimeIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			int paidHolidayAll = 0;
			int paidHolidayHalf = 0;
			int paidHolidayTime = 0;
			List<HolidayRequestDtoInterface> list = holidayRequestDao.findForTerm(humanList.get(i).getPersonalId(),
					startDate, endDate, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
					Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY));
			for (HolidayRequestDtoInterface dto : list) {
				if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
					continue;
				}
				int range = dto.getHolidayRange();
				if (paidHolidayAllIndex != null && range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					paidHolidayAll++;
				} else if (paidHolidayHalfIndex != null
						&& (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM)) {
					// 午前休又は午後休の場合
					paidHolidayHalf++;
				} else if (paidHolidayTimeIndex != null && range == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					// 時間休の場合
					paidHolidayTime++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 代休情報設定
			if (paidHolidayAllIndex != null) {
				// 全休
				csvData[paidHolidayAllIndex.intValue()] = Integer.toString(paidHolidayAll);
			}
			if (paidHolidayHalfIndex != null) {
				// 半休
				csvData[paidHolidayHalfIndex.intValue()] = Integer.toString(paidHolidayHalf);
			}
			if (paidHolidayTimeIndex != null) {
				// 時休
				csvData[paidHolidayTimeIndex.intValue()] = Integer.toString(paidHolidayTime);
			}
		}
	}
	
	/**
	 * CSVデータリストにストック休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addStockHolidayData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date startDate, Date endDate) throws MospException {
		// ストック休暇情報フィールド情報準備
		Integer stockHolidayAllIndex = null;
		Integer stockHolidayHalfIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			int index = dto.getFieldOrder() - 1;
			if (TimeFileConst.FIELD_STOCK_HOLIDAY_ALL.equals(dto.getFieldName())) {
				// ストック休暇(全休)
				stockHolidayAllIndex = index;
			} else if (TimeFileConst.FIELD_STOCK_HOLIDAY_HALF.equals(dto.getFieldName())) {
				// ストック休暇(半休)
				stockHolidayHalfIndex = index;
			}
		}
		// ストック休暇情報出力要否確認
		if (stockHolidayAllIndex == null && stockHolidayHalfIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			int stockHolidayAll = 0;
			int stockHolidayHalf = 0;
			List<HolidayRequestDtoInterface> list = holidayRequestDao.findForTerm(humanList.get(i).getPersonalId(),
					startDate, endDate, 1, Integer.toString(2));
			for (HolidayRequestDtoInterface dto : list) {
				if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
					continue;
				}
				int range = dto.getHolidayRange();
				if (stockHolidayAllIndex != null && range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					stockHolidayAll++;
				} else if (stockHolidayHalfIndex != null
						&& (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM)) {
					stockHolidayHalf++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 代休情報設定
			if (stockHolidayAllIndex != null) {
				csvData[stockHolidayAllIndex.intValue()] = Integer.toString(stockHolidayAll);
			}
			if (stockHolidayHalfIndex != null) {
				csvData[stockHolidayHalfIndex.intValue()] = Integer.toString(stockHolidayHalf);
			}
		}
	}
	
	/**
	 * CSVデータリストに代休情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addSubHolidayData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date startDate, Date endDate) throws MospException {
		// 代休情報フィールド情報準備
		Integer subHolidayAllIndex = null;
		Integer subHolidayHalfIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			int index = dto.getFieldOrder() - 1;
			if (TimeFileConst.FIELD_SUB_HOLIDAY_ALL.equals(dto.getFieldName())) {
				// 代休(全休)
				subHolidayAllIndex = index;
			} else if (TimeFileConst.FIELD_SUB_HOLIDAY_HALF.equals(dto.getFieldName())) {
				// 代休(半休)
				subHolidayHalfIndex = index;
			}
		}
		// 代休情報出力要否確認
		if (subHolidayAllIndex == null && subHolidayHalfIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			int subHolidayAll = 0;
			int subHolidayHalf = 0;
			List<SubHolidayRequestDtoInterface> list = subHolidayRequestDao
				.findForTerm(humanList.get(i).getPersonalId(), startDate, endDate);
			for (SubHolidayRequestDtoInterface dto : list) {
				if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
					continue;
				}
				int range = dto.getHolidayRange();
				if (subHolidayAllIndex != null && range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					subHolidayAll++;
				} else if (subHolidayHalfIndex != null
						&& (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM)) {
					subHolidayHalf++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 代休情報設定
			if (subHolidayAllIndex != null) {
				csvData[subHolidayAllIndex.intValue()] = Integer.toString(subHolidayAll);
			}
			if (subHolidayHalfIndex != null) {
				csvData[subHolidayHalfIndex.intValue()] = Integer.toString(subHolidayHalf);
			}
		}
	}
	
	/**
	 * CSVデータリストに振替休日情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addSubstituteHolidayData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date startDate, Date endDate) throws MospException {
		// 振替休日情報フィールド情報準備
		Integer substituteHolidayAllIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			if (TimeFileConst.FIELD_SUBSTITUTE_HOLIDAY_ALL.equals(dto.getFieldName())) {
				// 振替休日(全休)
				substituteHolidayAllIndex = dto.getFieldOrder() - 1;
				break;
			}
		}
		// 振替休日情報出力要否確認
		if (substituteHolidayAllIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			int substituteHolidayAll = 0;
			List<SubstituteDtoInterface> list = substituteDao.findForTerm(humanList.get(i).getPersonalId(), startDate,
					endDate);
			for (SubstituteDtoInterface dto : list) {
				if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
					continue;
				}
				WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao
					.findForKeyOnWorkflow(dto.getPersonalId(), dto.getSubstituteDate());
				if (workOnHolidayRequestDto == null) {
					// 振替休日を加算
					substituteHolidayAll++;
					continue;
				}
				if (!workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
					// 振替休日を加算
					substituteHolidayAll++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 振替休日情報設定
			csvData[substituteHolidayAllIndex.intValue()] = Integer.toString(substituteHolidayAll);
		}
	}
	
	/**
	 * CSVデータリストに休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHolidayData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date startDate,
			Date endDate) throws MospException {
		String comma = mospParams.getName("Comma");
		StringBuffer special = new StringBuffer();
		special.append(TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		special.append(comma);
		StringBuffer other = new StringBuffer();
		other.append(TimeConst.CODE_HOLIDAYTYPE_OTHER);
		other.append(comma);
		StringBuffer absence = new StringBuffer();
		absence.append(TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
		absence.append(comma);
		// 休暇情報フィールド情報リスト準備
		Map<String, Integer> holidayIndexMap = new HashMap<String, Integer>();
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			if (dto.getFieldName().startsWith(special.toString()) || dto.getFieldName().startsWith(other.toString())
					|| dto.getFieldName().startsWith(absence.toString())) {
				// 特別休暇・その他休暇・欠勤の場合
				holidayIndexMap.put(dto.getFieldName(), dto.getFieldOrder() - 1);
			}
		}
		// 休暇情報出力要否確認
		if (holidayIndexMap.isEmpty()) {
			return;
		}
		
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			for (Entry<String, Integer> entry : holidayIndexMap.entrySet()) {
				int count = 0;
				String[] array = entry.getKey().split(comma);
				// 休暇申請情報リストを取得
				List<HolidayRequestDtoInterface> list = holidayRequestDao.findForTerm(humanList.get(i).getPersonalId(),
						startDate, endDate, getInteger(array[0]), array[1]);
				// 休暇申請毎に処理
				for (HolidayRequestDtoInterface dto : list) {
					// 承認済みでない場合
					if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
						// 処理なし
						continue;
					}
					int range = dto.getHolidayRange();
					// フィールドが全休で且つ申請の範囲が全休の場合又はフィールドが半休で且つ申請の範囲が午前休・午後休の場合
					if ((TimeFileConst.FIELD_ALL.equals(array[2]) && range == TimeConst.CODE_HOLIDAY_RANGE_ALL)
							|| (TimeFileConst.FIELD_HALF.equals(array[2]) && (range == TimeConst.CODE_HOLIDAY_RANGE_AM
									|| range == TimeConst.CODE_HOLIDAY_RANGE_PM))) {
						// 申請開始日取得
						Date targetDate = dto.getRequestStartDate();
						// 申請開始日が開始日より前の場合
						if (dto.getRequestStartDate().before(startDate)) {
							// 開始日に設定
							targetDate = startDate;
						}
						// 申請終了日取得
						Date targetEndDate = dto.getRequestEndDate();
						// 申請終了日が終了日より後の場合
						if (dto.getRequestEndDate().after(endDate)) {
							// 終了日に設定
							targetEndDate = endDate;
						}
						while (!targetDate.after(targetEndDate)) {
							// 休暇申請可能の場合
							if (canHolidayRequest(dto.getPersonalId(), targetDate)) {
								count++;
							}
							// 1日加算
							targetDate = addDay(targetDate, 1);
						}
					} else if (TimeFileConst.FIELD_HOUR.equals(array[2])
							&& range == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
						// 時間休の場合
						count++;
					}
				}
				// CSVデータ取得
				String[] csvData = csvDataList.get(i);
				// 特別休暇情報設定
				csvData[entry.getValue().intValue()] = Integer.toString(count);
			}
		}
	}
	
	/**
	 * 社員一覧情報からフィールド名に対応する情報を取得する。<br>
	 * @param dto 対象DTO
	 * @param fieldName フィールド名
	 * @param targetDate 対象日
	 * @return フィールド名に対応する情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getHumanData(HumanDtoInterface dto, String fieldName, Date targetDate) throws MospException {
		// フィールド名で取得情報を判断
		if (PfmHumanDao.COL_EMPLOYEE_CODE.equals(fieldName)) {
			return dto.getEmployeeCode();
		} else if (TimeFileConst.FIELD_FULL_NAME.equals(fieldName)) {
			return MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
		} else if (PlatformFileConst.FIELD_SECTION_NAME.equals(fieldName)) {
			return sectionReference.getSectionName(dto.getSectionCode(), targetDate);
		} else if (PlatformFileConst.FIELD_SECTION_DISPLAY.equals(fieldName)) {
			return sectionReference.getSectionDisplay(dto.getSectionCode(), targetDate);
		}
		return "";
	}
	
	/**
	 * ヘッダを取得する。<br>
	 * @param fieldName フィールド名称
	 * @param targetDate 対象日
	 * @return ヘッダ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getHeader(String fieldName, Date targetDate) throws MospException {
		String frontParentheses = mospParams.getName("FrontParentheses");
		String backParentheses = mospParams.getName("BackParentheses");
		String frontWithCornerParentheses = mospParams.getName("FrontWithCornerParentheses");
		String backWithCornerParentheses = mospParams.getName("BackWithCornerParentheses");
		String header = getCodeName(fieldName, TimeFileConst.CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA);
		if (!fieldName.equals(header)) {
			return header;
		}
		String[] array = fieldName.split(mospParams.getName("Comma"));
		HolidayDtoInterface holidayDto = holidayDao.findForInfo(array[1], targetDate, Integer.parseInt(array[0]));
		if (holidayDto == null) {
			StringBuffer sb = new StringBuffer();
			sb.append(frontWithCornerParentheses);
			sb.append(array[1]);
			sb.append(backWithCornerParentheses);
			return sb.toString();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(holidayDto.getHolidayName());
		if (TimeFileConst.FIELD_ALL.equals(array[2]) || TimeFileConst.FIELD_HALF.equals(array[2])
				|| TimeFileConst.FIELD_HOUR.equals(array[2])) {
			// 全休又は半休の場合
			sb.append(frontParentheses);
			if (TimeFileConst.FIELD_ALL.equals(array[2])) {
				// 全休の場合
				sb.append(mospParams.getName("AllTime"));
			} else if (TimeFileConst.FIELD_HALF.equals(array[2])) {
				// 半休の場合
				sb.append(mospParams.getName("HalfTime"));
			} else if (TimeFileConst.FIELD_HOUR.equals(array[2])) {
				// 時間休の場合
				sb.append(mospParams.getName("HourTime"));
			}
			sb.append(backParentheses);
		}
		sb.append(frontWithCornerParentheses);
		sb.append(holidayDto.getHolidayCode());
		sb.append(backWithCornerParentheses);
		return sb.toString();
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param dto 対象DTO
	 * @param startDate 開始日
	 * @param endDate 終了日
	 */
	protected void setFileName(ExportDtoInterface dto, Date startDate, Date endDate) {
		String hyphen = mospParams.getName("Hyphen");
		String exportCode = "";
		String fileExtension = "";
		if (dto != null) {
			if (dto.getExportCode() != null) {
				exportCode = dto.getExportCode();
			}
			if (PlatformFileConst.FILE_TYPE_CSV.equals(dto.getType())) {
				// CSV
				fileExtension = ".csv";
			}
		}
		// CSVファイル名作成
		StringBuffer sb = new StringBuffer();
		// エクスポートコード
		sb.append(exportCode);
		// ハイフン
		sb.append(hyphen);
		// 開始年
		sb.append(DateUtility.getStringYear(startDate));
		// 開始月
		sb.append(DateUtility.getStringMonth(startDate));
		// 開始日
		sb.append(DateUtility.getStringDay(startDate));
		// ハイフン
		sb.append(hyphen);
		// 終了年
		sb.append(DateUtility.getStringYear(endDate));
		// 終了月
		sb.append(DateUtility.getStringMonth(endDate));
		// 終了日
		sb.append(DateUtility.getStringDay(endDate));
		// 拡張子
		sb.append(fileExtension);
		// 送出ファイル名設定
		mospParams.setFileName(sb.toString());
	}
	
	/**
	 * 休暇申請可能かどうか確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇申請可能の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean canHolidayRequest(String personalId, Date targetDate) throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao
			.findForKeyOnWorkflow(personalId, targetDate);
		if (workOnHolidayRequestDto != null) {
			if (workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
				int substitute = workOnHolidayRequestDto.getSubstitute();
				if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
					// 振替出勤の場合
					return true;
				} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 休日出勤の場合
					return false;
				}
			}
		}
		// 振替休日の場合
		SubstituteDtoInterface substituteDto = substituteDao.findForDate(personalId, targetDate);
		if (substituteDto != null) {
			if (targetDate.compareTo(substituteDto.getSubstituteDate()) == 0) {
				return false;
			}
		}
		// 設定適用情報取得
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		applicationReference.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// カレンダ情報取得
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
				targetDate);
		scheduleReference.chkExistSchedule(scheduleDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// カレンダ日情報取得
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference
			.getScheduleDateInfo(scheduleDto.getScheduleCode(), targetDate);
		scheduleDateReference.chkExistScheduleDate(scheduleDateDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// 法定休日又は所定休日の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
			return false;
		}
		// 勤務形態情報取得
		if (workTypeReference.findForInfo(scheduleDateDto.getWorkTypeCode(), targetDate) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * エクスポートデータが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addNoExportDataMessage() {
		String rep = mospParams.getName("Export", "Information");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
	
}
