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
import jp.mosp.time.bean.SubHolidayExportBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdSubHolidayDao;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 代休データエクスポートクラス。
 */
public class SubHolidayExportBean extends PlatformBean implements SubHolidayExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface				exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface			exportFieldDao;
	
	/**
	 * 代休データ参照クラス。<br>
	 */
	protected SubHolidayReferenceBeanInterface	subHolidayReference;
	
	/**
	 * 代休申請DAO。<br>
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	/**
	 * 設定適用管理参照クラス。<br>
	 */
	protected ApplicationReferenceBeanInterface	applicationReference;
	
	/**
	 * 勤怠設定参照クラス。<br>
	 */
	protected TimeSettingReferenceBeanInterface	timeSettingReference;
	
	/**
	 * 締日管理参照クラス。<br>
	 */
	protected CutoffUtilBeanInterface			cutoffUtil;
	
	/**
	 * 人事マスタ検索クラス。<br>
	 */
	protected HumanSearchBeanInterface			humanSearch;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface		sectionReference;
	
	/**
	 * 人事マスタ情報リスト。<br>
	 */
	protected List<HumanDtoInterface>			humanList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayExportBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SubHolidayExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOの準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		subHolidayReference = (SubHolidayReferenceBeanInterface)createBean(SubHolidayReferenceBeanInterface.class);
		subHolidayRequestDao = (SubHolidayRequestDaoInterface)createDao(SubHolidayRequestDaoInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, int startYear, int startMonth, int endYear, int endMonth, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode)
			throws MospException {
		// エクスポート情報取得
		ExportDtoInterface dto = exportDao.findForKey(exportCode);
		if (dto == null) {
			// 該当するエクスポート情報が存在しない場合
			addNoExportDataMessage();
			return;
		}
		// 締日情報を取得
		CutoffDtoInterface cutoffDto = cutoffUtil.getCutoff(cutoffCode, startYear, startMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 締め期間初日・最終日を取得
		Date startDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), startYear, startMonth);
		Date endDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), endYear, endMonth);
		// CSVデータリストを作成
		List<String[]> list = getCsvDataList(dto, startDate, endDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, positionCode);
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
		// 人事情報検索
		searchHumanData(list, fieldList, startDate, endDate, cutoffCode, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// 代休情報付加
		addSubHolidayData(list, fieldList, startDate, endDate);
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
			header[i] = getCodeName(fieldList.get(i).getFieldName(), TimeFileConst.CODE_EXPORT_TYPE_TMD_SUB_HOLIDAY);
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, header);
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
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
	protected void searchHumanData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date startDate,
			Date endDate, String cutoffCode, String workPlaceCode, String employmentContractCode, String sectionCode,
			String positionCode) throws MospException {
		// 人事情報検索条件設定
		humanSearch.setTargetDate(endDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 人事情報検索
		List<HumanDtoInterface> allHumanList = humanSearch.search();
		if (cutoffCode.isEmpty()) {
			humanList = allHumanList;
			return;
		}
		humanList = new ArrayList<HumanDtoInterface>();
		for (HumanDtoInterface humanDto : allHumanList) {
			ApplicationDtoInterface applicationDto = applicationReference.findForPerson(humanDto.getPersonalId(),
					endDate);
			if (applicationDto == null) {
				continue;
			}
			TimeSettingDtoInterface timeSettingDto = timeSettingReference.getTimeSettingInfo(
					applicationDto.getWorkSettingCode(), endDate);
			if (timeSettingDto == null) {
				continue;
			}
			if (!cutoffCode.equals(timeSettingDto.getCutoffCode())) {
				continue;
			}
			humanList.add(humanDto);
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
		Integer employeeCodeIndex = null;
		Integer fullNameIndex = null;
		Integer sectionNameIndex = null;
		Integer sectionDisplayIndex = null;
		Integer workDateIndex = null;
		Integer subHolidayTypeIndex = null;
		Integer requestDate1Index = null;
		Integer requestDate2Index = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			int index = dto.getFieldOrder() - 1;
			if (PfmHumanDao.COL_EMPLOYEE_CODE.equals(dto.getFieldName())) {
				// 社員コード
				employeeCodeIndex = index;
			} else if (TimeFileConst.FIELD_FULL_NAME.equals(dto.getFieldName())) {
				// 氏名
				fullNameIndex = index;
			} else if (PlatformFileConst.FIELD_SECTION_NAME.equals(dto.getFieldName())) {
				// 所属名称
				sectionNameIndex = index;
			} else if (PlatformFileConst.FIELD_SECTION_DISPLAY.equals(dto.getFieldName())) {
				// 所属表示名称
				sectionDisplayIndex = index;
			} else if (TmdSubHolidayDao.COL_WORK_DATE.equals(dto.getFieldName())) {
				// 出勤日
				workDateIndex = index;
			} else if (TmdSubHolidayDao.COL_SUB_HOLIDAY_TYPE.equals(dto.getFieldName())) {
				// 代休種別
				subHolidayTypeIndex = index;
			} else if (TimeFileConst.FIELD_REQUEST_DATE1.equals(dto.getFieldName())) {
				// 代休日1
				requestDate1Index = index;
			} else if (TimeFileConst.FIELD_REQUEST_DATE2.equals(dto.getFieldName())) {
				// 代休日2
				requestDate2Index = index;
			}
		}
		// CSVデータリスト毎に情報を付加
		for (HumanDtoInterface humanDto : humanList) {
			// 代休情報取得及び確認
			List<SubHolidayDtoInterface> list = subHolidayReference.getSubHolidayList(humanDto.getPersonalId(),
					startDate, endDate);
			for (SubHolidayDtoInterface subHolidayDto : list) {
				Date[] requestDateArray = getRequestDateArray(subHolidayDto.getPersonalId(),
						subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
				// CSVデータ準備
				String[] csvData = new String[fieldList.size()];
				// 代休情報設定
				if (employeeCodeIndex != null) {
					csvData[employeeCodeIndex.intValue()] = humanDto.getEmployeeCode();
				}
				if (fullNameIndex != null) {
					csvData[fullNameIndex.intValue()] = MospUtility.getHumansName(humanDto.getFirstName(),
							humanDto.getLastName());
				}
				if (sectionNameIndex != null) {
					csvData[sectionNameIndex.intValue()] = sectionReference.getSectionName(humanDto.getSectionCode(),
							subHolidayDto.getWorkDate());
				}
				if (sectionDisplayIndex != null) {
					csvData[sectionDisplayIndex.intValue()] = sectionReference.getSectionDisplay(
							humanDto.getSectionCode(), subHolidayDto.getWorkDate());
				}
				if (workDateIndex != null) {
					csvData[workDateIndex.intValue()] = getStringDate(subHolidayDto.getWorkDate());
				}
				if (subHolidayTypeIndex != null) {
					StringBuffer sb = new StringBuffer();
					int subHolidayType = subHolidayDto.getSubHolidayType();
					if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
						// 法定
						sb.append(mospParams.getName("Legal"));
					} else if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
						// 所定
						sb.append(mospParams.getName("Prescribed"));
					} else if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
						// 深夜
						sb.append(mospParams.getName("Midnight"));
					} else {
						continue;
					}
					sb.append(mospParams.getName("FrontParentheses"));
					double subHolidayDays = subHolidayDto.getSubHolidayDays();
					if (Double.compare(subHolidayDays, 1) == 0) {
						// 全休
						sb.append(mospParams.getName("AllTime"));
					} else if (Double.compare(subHolidayDays, TimeConst.HOLIDAY_TIMES_HALF) == 0) {
						// 半休
						sb.append(mospParams.getName("HalfTime"));
					} else {
						continue;
					}
					sb.append(mospParams.getName("BackParentheses"));
					csvData[subHolidayTypeIndex.intValue()] = sb.toString();
				}
				if (requestDate1Index != null) {
					csvData[requestDate1Index.intValue()] = getStringDate(requestDateArray[0]);
				}
				if (requestDate2Index != null) {
					csvData[requestDate2Index.intValue()] = getStringDate(requestDateArray[1]);
				}
				// CSVデータをCSVデータリストに追加
				csvDataList.add(csvData);
			}
		}
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
	 * 代休日配列を取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @param timesWork 出勤回数
	 * @param subHolidayType 代休種別
	 * @return 代休日配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date[] getRequestDateArray(String personalId, Date workDate, int timesWork, int subHolidayType)
			throws MospException {
		Date[] requestDateArray = new Date[2];
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
				workDate, timesWork, subHolidayType);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			if (!workflowIntegrate.isCompleted(subHolidayRequestDto.getWorkflow())) {
				continue;
			}
			int holidayRange = subHolidayRequestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				requestDateArray[0] = subHolidayRequestDto.getRequestDate();
				requestDateArray[1] = subHolidayRequestDto.getRequestDate();
				return requestDateArray;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				if (requestDateArray[0] == null) {
					requestDateArray[0] = subHolidayRequestDto.getRequestDate();
				} else {
					requestDateArray[1] = subHolidayRequestDto.getRequestDate();
				}
			}
		}
		return requestDateArray;
	}
	
	/**
	 * エクスポートデータが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addNoExportDataMessage() {
		String rep = mospParams.getName("Export", "Information");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
}
