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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
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
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.AppliReasonDataExportBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.comparator.report.AppliReasonDataComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 各種申請理由データエクスポートクラス。<br>
 */
public class AppliReasonDataExportBean extends TimeApplicationBean implements AppliReasonDataExportBeanInterface {
	
	/**
	 * 拡張子(.csv)
	 */
	public static final String							FILENAME_EXTENSION_CSV	= ".csv";
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface						exportDao;
	
	/**
	 * 人事マスタ検索クラス。<br>
	 */
	protected HumanSearchBeanInterface					humanSearch;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface				sectionReference;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface					exportFieldDao;
	
	/**
	 * 締日ユーティリティクラス。<br>
	 */
	protected CutoffUtilBeanInterface					cutoffUtil;
	
	/**
	 * カレンダーユーティリティクラス。<br>
	 */
	protected ScheduleUtilBeanInterface					scheduleUtil;
	
	/**
	 * 残業申請DAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface				overtimeRequestDao;
	
	/**
	 * 休暇申請DAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface				holidayRequestDao;
	
	/**
	 * 休暇DAOクラス。<br>
	 */
	protected HolidayDaoInterface						holidayDao;
	
	/**
	 * 休日出勤申請DAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface			workOnHolidayRequestDao;
	
	/**
	 * 振替休日申請DAOクラス。<br>
	 */
	protected SubstituteDaoInterface					substituteDao;
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface			workTypeChangeRequestDao;
	
	/**
	 * 勤務形態情報参照クラス。<br>
	 */
	WorkTypeReferenceBeanInterface						workTypeRefer;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface				differenceRequestDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * 下位所属含むチェックボックス。
	 */
	private int											ckbNeedLowerSection		= 0;
	
	/**
	 * 人事マスタ情報リスト。<br>
	 */
	protected List<HumanDtoInterface>					humanList;
	
	/**
	 * 残業申請(承認済)リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>			overtimeRequestList;
	
	/**
	 * 休暇申請(承認済)リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>			holidayRequestList;
	
	/**
	 * 振出・休出申請(承認済)リスト。<br>
	 */
	protected List<WorkOnHolidayRequestDtoInterface>	workOnHolidayList;
	
	/**
	 * 時差出勤申請(承認済)リスト。<br>
	 */
	protected List<DifferenceRequestDtoInterface>		differenceRequestList;
	
	/**
	 * 勤務形態変更申請(承認済)リスト。<br>
	 */
	protected List<WorkTypeChangeRequestDtoInterface>	workTypeChangeRequestList;
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public AppliReasonDataExportBean() {
		super();
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public AppliReasonDataExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		overtimeRequestDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		holidayRequestDao = (HolidayRequestDaoInterface)createDao(HolidayRequestDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		workTypeChangeRequestDao = (WorkTypeChangeRequestDaoInterface)createDao(
				WorkTypeChangeRequestDaoInterface.class);
		differenceRequestDao = (DifferenceRequestDaoInterface)createDao(DifferenceRequestDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		workTypeRefer = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
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
		// 締日情報を取得
		CutoffDtoInterface cutoffDto = cutoffUtil.getCutoff(cutoffCode, startYear, startMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 締め期間初日・最終日を取得
		Date firstDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), startYear, startMonth);
		Date lastDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), endYear, endMonth);
		// CSVデータリストを作成
		List<String[]> list = getCsvDataList(dto, firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, positionCode);
		if (list.isEmpty()) {
			// 該当するエクスポート情報が存在しない場合
			addNoExportDataMessage();
			return;
		}
		
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(list));
		// ファイル名設定
		mospParams.setFileName(getFilename(dto, firstDate, lastDate));
		
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * @param dto 対象DTO
	 * @param startDate 初日
	 * @param endDate 末日
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
		// 人事情報リスト取得
		getHumanList(list, fieldList, startDate, endDate, cutoffCode, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// 社員毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 各種申請リスト(承認済)取得
			getAppliedRequestList(humanDto.getPersonalId(), startDate, endDate);
			// 残業申請情報付加
			addOverTimeRequestData(humanDto, list, fieldList, endDate);
			// 休暇申請情報付加
			addHolidayRequestData(humanDto, list, fieldList, startDate, endDate);
			// 休日出勤申請情報付加
			addWorkOnHolidayRequestData(humanDto, list, fieldList, endDate);
			// 時差出勤申請情報付加
			addDifferebceRequestData(humanDto, list, fieldList, endDate);
			// 勤務形態変更申請情報付加
			addWorkTypeChangeRequestData(humanDto, list, fieldList, endDate);
		}
		// ソート
		Collections.sort(list, new AppliReasonDataComparator());
		if (dto.getHeader() != PlatformFileConst.HEADER_TYPE_NONE) {
			// ヘッダ情報付加
			addHeader(list, getHeader(dto, fieldList));
		}
		return list;
	}
	
	/**
	 * 対象期間の承認済み申請リストを取得する。<br>
	 * 残業申請、休暇申請、休日出勤申請、時差出勤申請、勤務形態変更申請。<br>
	 * @param personalId 個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getAppliedRequestList(String personalId, Date startDate, Date endDate) throws MospException {
		// 申請リスト初期化
		overtimeRequestList = new ArrayList<OvertimeRequestDtoInterface>();
		holidayRequestList = new ArrayList<HolidayRequestDtoInterface>();
		workOnHolidayList = new ArrayList<WorkOnHolidayRequestDtoInterface>();
		differenceRequestList = new ArrayList<DifferenceRequestDtoInterface>();
		workTypeChangeRequestList = new ArrayList<WorkTypeChangeRequestDtoInterface>();
		// 残業申請リスト取得
		List<OvertimeRequestDtoInterface> overtimeList = overtimeRequestDao.findForList(personalId, startDate, endDate);
		for (OvertimeRequestDtoInterface dto : overtimeList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 承認済の場合
			if (WorkflowUtility.isCompleted(workflowDto)) {
				// 承認済リスト追加
				overtimeRequestList.add(dto);
			}
		}
		// 休暇申請リスト取得
		List<HolidayRequestDtoInterface> holidayList = holidayRequestDao.findForTerm(personalId, startDate, endDate);
		for (HolidayRequestDtoInterface dto : holidayList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 承認済の場合
			if (WorkflowUtility.isCompleted(workflowDto)) {
				// 承認済リスト追加
				holidayRequestList.add(dto);
			}
		}
		// 休日出勤申請リスト取得
		List<WorkOnHolidayRequestDtoInterface> workList = workOnHolidayRequestDao.findForList(personalId, startDate,
				endDate);
		for (WorkOnHolidayRequestDtoInterface dto : workList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 承認済の場合
			if (WorkflowUtility.isCompleted(workflowDto)) {
				// 承認済リスト追加
				workOnHolidayList.add(dto);
			}
		}
		// 時差出勤申請リスト取得
		List<DifferenceRequestDtoInterface> differenceList = differenceRequestDao.findForList(personalId, startDate,
				endDate);
		for (DifferenceRequestDtoInterface dto : differenceList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 承認済の場合
			if (WorkflowUtility.isCompleted(workflowDto)) {
				// 承認済リスト追加
				differenceRequestList.add(dto);
			}
		}
		// 勤務形態変更申請リスト取得
		List<WorkTypeChangeRequestDtoInterface> worktypeList = workTypeChangeRequestDao.findForTerm(personalId,
				startDate, endDate);
		for (WorkTypeChangeRequestDtoInterface dto : worktypeList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 承認済の場合
			if (WorkflowUtility.isCompleted(workflowDto)) {
				// 承認済リスト追加
				workTypeChangeRequestList.add(dto);
			}
		}
	}
	
	/**
	 * 残業申請理由をCSVデータリストに付加する。<br>
	 * @param humanDto 人事情報
	 * @param list CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param endDate 期間最終日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addOverTimeRequestData(HumanDtoInterface humanDto, List<String[]> list,
			List<ExportFieldDtoInterface> fieldList, Date endDate) throws MospException {
		// 残業申請情報毎に処理
		for (OvertimeRequestDtoInterface overtimeDto : overtimeRequestList) {
			// データ配列準備
			String[] fieldValue = new String[fieldList.size()];
			// フィールド毎に処理
			for (int i = 0; i < fieldList.size(); i++) {
				// 項目名取得
				String fieldName = fieldList.get(i).getFieldName();
				// 社員コード、氏名、所属名、所属表示名称設定
				fieldValue[i] = getHumanData(humanDto, fieldName, endDate);
				// 勤務日
				if (TimeFileConst.FIELD_WORK_DATE.equals(fieldName)) {
					fieldValue[i] = DateUtility.getStringDateAndDay(overtimeDto.getRequestDate());
					continue;
				}
				// 申請区分
				if (TimeFileConst.FIELD_APPLI_TYPE.equals(fieldName)) {
					fieldValue[i] = mospParams.getName("OvertimeWork");
					continue;
				}
				// 詳細1
				if (TimeFileConst.FIELD_APPLI_DETAIL_1.equals(fieldName)) {
					fieldValue[i] = getCodeName(overtimeDto.getOvertimeType(), TimeConst.CODE_OVER_TIME_TYPE);
					continue;
				}
				// 詳細2
				if (TimeFileConst.FIELD_APPLI_DETAIL_2.equals(fieldName)) {
					// 表示例 2時間30分
					StringBuffer sb = new StringBuffer();
					// 予定
					sb.append((overtimeDto.getRequestTime() / TimeConst.CODE_DEFINITION_HOUR)
							+ mospParams.getName("Time"));
					sb.append((overtimeDto.getRequestTime() % TimeConst.CODE_DEFINITION_HOUR)
							+ mospParams.getName("Minutes"));
					
					fieldValue[i] = sb.toString();
					continue;
				}
				// 詳細3
				if (TimeFileConst.FIELD_APPLI_DETAIL_3.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 詳細4
				if (TimeFileConst.FIELD_APPLI_DETAIL_4.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 申請理由
				if (TimeFileConst.FIELD_APPLI_REASON.equals(fieldName)) {
					fieldValue[i] = overtimeDto.getRequestReason();
					continue;
				}
			}
			// CSVリスト追加
			list.add(fieldValue);
		}
	}
	
	/**
	 * 休暇申請理由をCSVデータリストに付加する。<br>
	 * @param humanDto 人事情報
	 * @param list CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHolidayRequestData(HumanDtoInterface humanDto, List<String[]> list,
			List<ExportFieldDtoInterface> fieldList, Date startDate, Date endDate) throws MospException {
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface holidayDto : holidayRequestList) {
			// 申請日付リスト取得
			List<Date> dateList = TimeUtility.getDateList(holidayDto.getRequestStartDate(),
					holidayDto.getRequestEndDate());
			// 申請日付毎に処理
			for (Date requestDate : dateList) {
				// 勤務形態コード取得
				String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(humanDto.getPersonalId(), requestDate,
						true);
				// 休日や休日出勤の場合
				if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)
						|| TimeUtility.isWorkOnLegalHoliday(workTypeCode)) {
					// 処理なし
					continue;
				}
				
				// 期間開始日より前の場合
				if (requestDate.compareTo(startDate) < 0) {
					// 処理なし
					continue;
				}
				// 期間終了日より後の場合
				if (requestDate.compareTo(endDate) > 0) {
					// 処理なし
					continue;
				}
				// データ配列準備
				String[] fieldValue = new String[fieldList.size()];
				// フィールド毎に処理
				for (int i = 0; i < fieldList.size(); i++) {
					// 項目名取得
					String fieldName = fieldList.get(i).getFieldName();
					// 社員コード、氏名、所属名、所属表示名称設定
					fieldValue[i] = getHumanData(humanDto, fieldName, endDate);
					// 社員コード
					if (PlatformFileConst.FIELD_EMPLOYEE_CODE.equals(fieldName)) {
						fieldValue[i] = humanDto.getEmployeeCode();
						continue;
					}
					// 氏名
					if (TimeFileConst.FIELD_FULL_NAME.equals(fieldName)) {
						fieldValue[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
						continue;
					}
					// 勤務日
					if (TimeFileConst.FIELD_WORK_DATE.equals(fieldName)) {
						fieldValue[i] = DateUtility.getStringDateAndDay(requestDate);
						continue;
					}
					// 申請区分
					if (TimeFileConst.FIELD_APPLI_TYPE.equals(fieldName)) {
						fieldValue[i] = mospParams.getName("Vacation");
						continue;
					}
					// 詳細1
					if (TimeFileConst.FIELD_APPLI_DETAIL_1.equals(fieldName)) {
						fieldValue[i] = getCodeName(holidayDto.getHolidayType1(), TimeConst.CODE_HOLIDAY_TYPE);
						continue;
					}
					// 詳細2
					if (TimeFileConst.FIELD_APPLI_DETAIL_2.equals(fieldName)) {
						// 休暇種別コード1を取得
						int holidayType1 = holidayDto.getHolidayType1();
						// 休暇種別2準備
						String holidayType2 = "";
						// 有給休暇の場合
						if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
							holidayType2 = getCodeName(holidayDto.getHolidayType2(),
									TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY);
						} else {
							// 休暇種別取得
							HolidayDtoInterface holiday = holidayDao.findForInfo(holidayDto.getHolidayType2(),
									requestDate, holidayType1);
							if (holiday != null) {
								holidayType2 = holiday.getHolidayAbbr();
							}
						}
						fieldValue[i] = holidayType2;
						continue;
					}
					// 詳細3
					if (TimeFileConst.FIELD_APPLI_DETAIL_3.equals(fieldName)) {
						fieldValue[i] = getCodeName(holidayDto.getHolidayRange(), TimeConst.CODE_HOLIDAY_TYPE3_RANGE1);
						continue;
					}
					// 詳細4
					if (TimeFileConst.FIELD_APPLI_DETAIL_4.equals(fieldName)) {
						String detail4 = "";
						// 時間休の場合
						if (holidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// 時間休時刻設定
							detail4 = getTimeWaveFormat(holidayDto.getStartTime(), holidayDto.getEndTime(),
									holidayDto.getRequestStartDate());
						}
						fieldValue[i] = detail4;
						continue;
					}
					// 申請理由
					if (TimeFileConst.FIELD_APPLI_REASON.equals(fieldName)) {
						fieldValue[i] = holidayDto.getRequestReason();
						continue;
					}
				}
				// CSVリスト追加
				list.add(fieldValue);
			}
		}
	}
	
	/**
	 * 休日出勤申請をCSVデータリストに付加する。<br>
	 * @param humanDto 人事情報
	 * @param list CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addWorkOnHolidayRequestData(HumanDtoInterface humanDto, List<String[]> list,
			List<ExportFieldDtoInterface> fieldList, Date endDate) throws MospException {
		// 休日出勤申請データ毎に処理
		for (WorkOnHolidayRequestDtoInterface workDto : workOnHolidayList) {
			// データ配列準備
			String[] fieldValue = new String[fieldList.size()];
			// フィールド毎に処理
			for (int i = 0; i < fieldList.size(); i++) {
				// 項目名取得
				String fieldName = fieldList.get(i).getFieldName();
				// 社員コード、氏名、所属名、所属表示名称設定
				fieldValue[i] = getHumanData(humanDto, fieldName, endDate);
				// 社員コード
				if (PlatformFileConst.FIELD_EMPLOYEE_CODE.equals(fieldName)) {
					fieldValue[i] = humanDto.getEmployeeCode();
					continue;
				}
				// 氏名
				if (TimeFileConst.FIELD_FULL_NAME.equals(fieldName)) {
					fieldValue[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
					continue;
				}
				// 勤務日
				if (TimeFileConst.FIELD_WORK_DATE.equals(fieldName)) {
					fieldValue[i] = DateUtility.getStringDateAndDay(workDto.getRequestDate());
					continue;
				}
				// 申請区分
				if (TimeFileConst.FIELD_APPLI_TYPE.equals(fieldName)) {
					fieldValue[i] = mospParams.getName("SubstituteWorkAbbr", "MiddlePoint", "WorkingHoliday");
					continue;
				}
				// 詳細1
				if (TimeFileConst.FIELD_APPLI_DETAIL_1.equals(fieldName)) {
					// 振替出勤申請種別準備
					String substitute = "";
					// 午前休の場合
					if (workDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
						substitute = mospParams.getName("AnteMeridiem");
					} else if (workDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
						// 午後休の場合
						substitute = mospParams.getName("PostMeridiem");
					} else {
						// その他の場合
						substitute = getCodeName(workDto.getSubstitute(), TimeConst.CODE_WORKONHOLIDAY_SUBSTITUTE);
					}
					
					fieldValue[i] = substitute;
					continue;
				}
				// 詳細2
				if (TimeFileConst.FIELD_APPLI_DETAIL_2.equals(fieldName)) {
					// 詳細2準備
					String detail2 = "";
					// 休日出勤申請の場合
					if (workDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
						// 勤務時刻設定
						detail2 = getTimeWaveFormat(workDto.getStartTime(), workDto.getEndTime(),
								workDto.getRequestDate());
					} else {
						// 振替休日データ取得
						List<SubstituteDtoInterface> substituteList = substituteDao
							.findForWorkflow(workDto.getWorkflow());
						// 振替休日データ毎に処理
						for (SubstituteDtoInterface substituteDto : substituteList) {
							// 振替休日設定
							detail2 = DateUtility.getStringDateAndDay(substituteDto.getSubstituteDate());
							break;
						}
					}
					fieldValue[i] = detail2;
					continue;
				}
				// 詳細3
				if (TimeFileConst.FIELD_APPLI_DETAIL_3.equals(fieldName)) {
					// 詳細2準備
					String detail3 = "";
					// 振替休日データ取得
					List<SubstituteDtoInterface> substituteList = substituteDao.findForWorkflow(workDto.getWorkflow());
					// 振替休日データ毎に処理
					for (SubstituteDtoInterface substituteDto : substituteList) {
						// 出勤範囲設定
						if (substituteDto.getSubstituteRange() == 1) {
							// 全日
							detail3 = mospParams.getName("AllDays");
						} else {
							// 午前・午後の場合
							detail3 = getCodeName(substituteDto.getSubstituteRange(),
									TimeConst.CODE_SUBSTITUTE_HOLIDAY_RANGE);
							// TODO 1日に午前午後の振替があった場合を未考慮
							break;
						}
					}
					fieldValue[i] = detail3;
					continue;
				}
				// 詳細4
				if (TimeFileConst.FIELD_APPLI_DETAIL_4.equals(fieldName)) {
					// 勤務形態準備
					String workTypeCode = "";
					if (!workDto.getWorkTypeCode().isEmpty()) {
						// 振替出勤申請(勤務形態変更)の際、勤務形態コード設定
						workTypeCode = workDto.getWorkTypeCode();
					}
					fieldValue[i] = workTypeCode;
					continue;
				}
				// 申請理由
				if (TimeFileConst.FIELD_APPLI_REASON.equals(fieldName)) {
					fieldValue[i] = workDto.getRequestReason();
					continue;
				}
			}
			// CSVリスト追加
			list.add(fieldValue);
		}
	}
	
	/**
	 * 時差出勤申請データをCSVデータリストに付加する。<br>
	 * @param humanDto 人事情報
	 * @param list CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addDifferebceRequestData(HumanDtoInterface humanDto, List<String[]> list,
			List<ExportFieldDtoInterface> fieldList, Date endDate) throws MospException {
		// 時差出勤申請データ毎に処理
		for (DifferenceRequestDtoInterface differenceDto : differenceRequestList) {
			// データ配列準備
			String[] fieldValue = new String[fieldList.size()];
			// フィールド毎に処理
			for (int i = 0; i < fieldList.size(); i++) {
				// 項目名取得
				String fieldName = fieldList.get(i).getFieldName();
				// 社員コード、氏名、所属名、所属表示名称設定
				fieldValue[i] = getHumanData(humanDto, fieldName, endDate);
				// 社員コード
				if (PlatformFileConst.FIELD_EMPLOYEE_CODE.equals(fieldName)) {
					fieldValue[i] = humanDto.getEmployeeCode();
					continue;
				}
				// 氏名
				if (TimeFileConst.FIELD_FULL_NAME.equals(fieldName)) {
					fieldValue[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
					continue;
				}
				// 勤務日
				if (TimeFileConst.FIELD_WORK_DATE.equals(fieldName)) {
					fieldValue[i] = DateUtility.getStringDateAndDay(differenceDto.getRequestDate());
					continue;
				}
				// 申請区分
				if (TimeFileConst.FIELD_APPLI_TYPE.equals(fieldName)) {
					fieldValue[i] = mospParams.getName("TimeDifference");
					continue;
				}
				// 詳細1
				if (TimeFileConst.FIELD_APPLI_DETAIL_1.equals(fieldName)) {
					// 時差出勤区分準備
					String detail1 = "";
					if (TimeConst.CODE_DIFFERENCE_TYPE_A.equals(differenceDto.getDifferenceType())) {
						detail1 = mospParams.getName("CharaA");
					} else if (TimeConst.CODE_DIFFERENCE_TYPE_B.equals(differenceDto.getDifferenceType())) {
						detail1 = mospParams.getName("CharaB");
					} else if (TimeConst.CODE_DIFFERENCE_TYPE_C.equals(differenceDto.getDifferenceType())) {
						detail1 = mospParams.getName("CharaC");
					} else if (TimeConst.CODE_DIFFERENCE_TYPE_D.equals(differenceDto.getDifferenceType())) {
						detail1 = mospParams.getName("CharaD");
					} else if (TimeConst.CODE_DIFFERENCE_TYPE_S.equals(differenceDto.getDifferenceType())) {
						detail1 = mospParams.getName("CharaS");
					}
					fieldValue[i] = detail1;
					continue;
				}
				// 詳細2
				if (TimeFileConst.FIELD_APPLI_DETAIL_2.equals(fieldName)) {
					// 勤務時刻取得
					String time = getTimeWaveFormat(differenceDto.getRequestStart(), differenceDto.getRequestEnd(),
							differenceDto.getRequestDate());
					fieldValue[i] = time;
					continue;
				}
				// 詳細3
				if (TimeFileConst.FIELD_APPLI_DETAIL_3.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 詳細4
				if (TimeFileConst.FIELD_APPLI_DETAIL_4.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 申請理由
				if (TimeFileConst.FIELD_APPLI_REASON.equals(fieldName)) {
					fieldValue[i] = differenceDto.getRequestReason();
					continue;
				}
			}
			// CSVリスト追加
			list.add(fieldValue);
		}
	}
	
	/**
	 * 勤務形態変更申請データをCSVデータリストに付加する。<br>
	 * @param humanDto 人事情報
	 * @param list CSVデータリスト
	 * @param fieldList エクスポートフィールド情報リスト
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addWorkTypeChangeRequestData(HumanDtoInterface humanDto, List<String[]> list,
			List<ExportFieldDtoInterface> fieldList, Date endDate) throws MospException {
		// 勤務形態変更申請データ毎に処理
		for (WorkTypeChangeRequestDtoInterface workTypeChangeDto : workTypeChangeRequestList) {
			// データ配列準備
			String[] fieldValue = new String[fieldList.size()];
			// フィールド毎に処理
			for (int i = 0; i < fieldList.size(); i++) {
				// 項目名取得
				String fieldName = fieldList.get(i).getFieldName();
				// 社員コード、氏名、所属名、所属表示名称設定
				fieldValue[i] = getHumanData(humanDto, fieldName, endDate);
				// 社員コード
				if (PlatformFileConst.FIELD_EMPLOYEE_CODE.equals(fieldName)) {
					fieldValue[i] = humanDto.getEmployeeCode();
					continue;
				}
				// 氏名
				if (TimeFileConst.FIELD_FULL_NAME.equals(fieldName)) {
					fieldValue[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
					continue;
				}
				// 勤務日
				if (TimeFileConst.FIELD_WORK_DATE.equals(fieldName)) {
					fieldValue[i] = DateUtility.getStringDateAndDay(workTypeChangeDto.getRequestDate());
					continue;
				}
				// 申請区分
				if (TimeFileConst.FIELD_APPLI_TYPE.equals(fieldName)) {
					fieldValue[i] = mospParams.getName("WorkTypeAbbr", "Change");
					continue;
				}
				// 詳細1
				if (TimeFileConst.FIELD_APPLI_DETAIL_1.equals(fieldName)) {
					fieldValue[i] = workTypeRefer.getWorkTypeAbbrAndTime(workTypeChangeDto.getWorkTypeCode(),
							workTypeChangeDto.getRequestDate());
					continue;
				}
				// 詳細2
				if (TimeFileConst.FIELD_APPLI_DETAIL_2.equals(fieldName)) {
					fieldValue[i] = workTypeChangeDto.getWorkTypeCode();
					continue;
				}
				// 詳細3
				if (TimeFileConst.FIELD_APPLI_DETAIL_3.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 詳細4
				if (TimeFileConst.FIELD_APPLI_DETAIL_4.equals(fieldName)) {
					fieldValue[i] = "";
					continue;
				}
				// 申請理由
				if (TimeFileConst.FIELD_APPLI_REASON.equals(fieldName)) {
					fieldValue[i] = workTypeChangeDto.getRequestReason();
					continue;
				}
			}
			// CSVリスト追加
			list.add(fieldValue);
		}
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
	protected void getHumanList(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date startDate,
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
		
		// 社員リスト準備
		humanList = new ArrayList<HumanDtoInterface>();
		if (cutoffCode.isEmpty()) {
			humanList = allHumanList;
		} else {
			for (HumanDtoInterface humanDto : allHumanList) {
				if (!hasCutoffSettings(humanDto.getPersonalId(), endDate)) {
					continue;
				}
				if (!cutoffDto.getCutoffCode().equals(cutoffCode)) {
					continue;
				}
				humanList.add(humanDto);
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
	 * CSVデータリストにヘッダを付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param header ヘッダ
	 */
	protected void addHeader(List<String[]> csvDataList, String[] header) {
		csvDataList.add(0, header);
	}
	
	/**
	 * ヘッダを取得する。<br>
	 * @param dto 対象DTO
	 * @param list フィールドDTOリスト
	 * @return ヘッダ
	 */
	protected String[] getHeader(ExportDtoInterface dto, List<ExportFieldDtoInterface> list) {
		String[][] array = mospParams.getProperties().getCodeArray(dto.getExportTable(), false);
		String[] header = new String[list.size()];
		for (int i = 0; i < header.length; i++) {
			header[i] = MospUtility.getCodeName(list.get(i).getFieldName(), array);
		}
		return header;
	}
	
	/**
	 * 送出ファイル名を取得する。<br>
	 * @param dto 対象DTO
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 送出ファイル名
	 */
	protected String getFilename(ExportDtoInterface dto, Date firstDate, Date lastDate) {
		StringBuffer sb = new StringBuffer();
		// エクスポートコード
		sb.append(dto.getExportCode());
		// ハイフン
		sb.append(mospParams.getName("Hyphen"));
		// 開始年
		sb.append(DateUtility.getStringYear(firstDate));
		// 開始月
		sb.append(DateUtility.getStringMonth(firstDate));
		// 開始日
		sb.append(DateUtility.getStringDay(firstDate));
		// ハイフン
		sb.append(mospParams.getName("Hyphen"));
		// 終了年
		sb.append(DateUtility.getStringYear(lastDate));
		// 終了月
		sb.append(DateUtility.getStringMonth(lastDate));
		// 終了日
		sb.append(DateUtility.getStringDay(lastDate));
		// 拡張子
		sb.append(getFilenameExtension(dto));
		return sb.toString();
	}
	
	/**
	 * 拡張子を取得する。<br>
	 * @param dto 対象DTO
	 * @return 拡張子
	 */
	protected String getFilenameExtension(ExportDtoInterface dto) {
		if (PlatformFileConst.FILE_TYPE_CSV.equals(dto.getType())) {
			// CSV
			return FILENAME_EXTENSION_CSV;
		}
		return "";
	}
	
	/**
	 * エクスポートデータが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addNoExportDataMessage() {
		String rep = mospParams.getName("Export", "Information");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
	
	/**
	 * 一覧時分形式で出力する。
	 * @param time 分
	 * @return HH.MM
	 */
	protected String getTimeDotFormat(int time) {
		StringBuffer sb = new StringBuffer();
		sb.append(convIntegerTimeToIntegerHour(time));
		sb.append(mospParams.getName("Dot"));
		sb.append(convIntegerTimeToStringMinutes(time));
		return sb.toString();
	}
	
	/**
	 * int型の時間をint型の時間(時)に変換
	 * @param time 時間
	 * @return 時間(時)
	 */
	protected int convIntegerTimeToIntegerHour(int time) {
		return time / TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * int型の時間をString型の時間（分）に変換
	 * @param time 時刻
	 * @return 時間（分）
	 */
	protected String convIntegerTimeToStringMinutes(int time) {
		int minute = convIntegerTimeToIntegerMinute(time);
		StringBuffer sb = new StringBuffer();
		if (minute < 10) {
			sb.append(0);
		}
		sb.append(minute);
		return sb.toString();
	}
	
	/**
	 * int型の時間をint型の時間(分)に変換
	 * @param time 時間
	 * @return 時間(分)
	 */
	protected int convIntegerTimeToIntegerMinute(int time) {
		return Math.abs(time) % TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 時分形式で出力する。
	 * @param date1 前時間
	 * @param date2 後時間
	 * @param standardDate 基準日付
	 * @return hh:mm～hh:mm
	 */
	protected String getTimeWaveFormat(Date date1, Date date2, Date standardDate) {
		if (date1 == null || date2 == null || standardDate == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtility.getStringTime(date1, standardDate));
		sb.append(mospParams.getName("Wave"));
		sb.append(DateUtility.getStringTime(date2, standardDate));
		return sb.toString();
	}
}
