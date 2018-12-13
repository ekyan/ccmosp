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
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.AttendanceReapplicationExportBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdSubHolidayDao;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠再申請対象者エクスポートクラス。
 */
public class AttendanceReapplicationExportBean extends TimeApplicationBean
		implements AttendanceReapplicationExportBeanInterface {
	
	/**
	 * 拡張子(.csv)
	 */
	public static final String				FILENAME_EXTENSION_CSV	= ".csv";
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface			exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface		exportFieldDao;
	
	/**
	 * 勤怠データDAO。<br>
	 */
	protected AttendanceDaoInterface		attendanceDao;
	
	/**
	 * 締日管理参照クラス。<br>
	 */
	protected CutoffUtilBeanInterface		cutoffUtil;
	
	/**
	 * カレンダユーティリティ
	 */
	protected ScheduleUtilBeanInterface		scheduleUtil;
	
	/**
	 * 人事マスタ参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface	humanReference;
	
	/**
	 * 人事マスタ検索クラス。<br>
	 */
	protected HumanSearchBeanInterface		humanSearch;
	
	/**
	 * 下位所属含むチェックボックス。
	 */
	private int								ckbNeedLowerSection		= 0;
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public AttendanceReapplicationExportBean() {
		super();
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public AttendanceReapplicationExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		scheduleUtil = (ScheduleUtilBeanInterface)createBean(ScheduleUtilBeanInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
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
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(ExportDtoInterface dto, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode)
			throws MospException {
		// エクスポートフィールド情報取得
		List<ExportFieldDtoInterface> fieldList = exportFieldDao.findForList(dto.getExportCode());
		List<String[]> list = getCsvDataList(fieldList, firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, positionCode);
		if (dto.getHeader() != PlatformFileConst.HEADER_TYPE_NONE) {
			// ヘッダ情報付加
			addHeader(list, getHeader(dto, fieldList));
		}
		return list;
	}
	
	/**
	 * CSVデータリストを取得する。<br>
	 * @param fieldList フィールドDTOリスト
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(List<ExportFieldDtoInterface> fieldList, Date firstDate, Date lastDate,
			String cutoffCode, String workPlaceCode, String employmentContractCode, String sectionCode,
			String positionCode) throws MospException {
		// フィールド情報準備
		Integer employeeCodeIndex = null;
		Integer fullNameIndex = null;
		Integer workDateIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface dto : fieldList) {
			int index = dto.getFieldOrder() - 1;
			if (PfmHumanDao.COL_EMPLOYEE_CODE.equals(dto.getFieldName())) {
				// 社員コード
				employeeCodeIndex = index;
			} else if (TimeFileConst.FIELD_FULL_NAME.equals(dto.getFieldName())) {
				// 指名
				fullNameIndex = index;
			} else if (TmdSubHolidayDao.COL_WORK_DATE.equals(dto.getFieldName())) {
				// 出勤日
				workDateIndex = index;
			}
		}
		List<String[]> csvDataList = new ArrayList<String[]>();
		for (AttendanceDtoInterface dto : findAttendanceList(
				findHumanList(lastDate, cutoffCode, workPlaceCode, employmentContractCode, sectionCode, positionCode),
				firstDate, lastDate)) {
			// 人事マスタ取得
			HumanDtoInterface humanDto = humanReference.getHumanInfo(dto.getPersonalId(), dto.getWorkDate());
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			if (employeeCodeIndex != null && humanDto != null) {
				// 社員コード
				csvData[employeeCodeIndex.intValue()] = humanDto.getEmployeeCode();
			}
			if (fullNameIndex != null && humanDto != null) {
				// 氏名
				csvData[fullNameIndex.intValue()] = MospUtility.getHumansName(humanDto.getFirstName(),
						humanDto.getLastName());
			}
			if (workDateIndex != null) {
				// 勤務日
				csvData[workDateIndex.intValue()] = getStringDate(dto.getWorkDate());
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
		return csvDataList;
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
	 * @param targetDate 対象日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> findHumanList(Date targetDate, String cutoffCode, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// 人事情報検索条件設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態)
		humanSearch.setStateType("");
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
		// 人事情報検索
		List<HumanDtoInterface> humanList = humanSearch.search();
		if (cutoffCode.isEmpty()) {
			return humanList;
		}
		List<HumanDtoInterface> list = new ArrayList<HumanDtoInterface>();
		for (HumanDtoInterface dto : humanList) {
			if (!hasCutoffSettings(dto.getPersonalId(), targetDate)) {
				continue;
			}
			if (!cutoffDto.getCutoffCode().equals(cutoffCode)) {
				continue;
			}
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 勤怠データリストを取得する。<br>
	 * @param list 人事マスタリスト
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合s
	 */
	protected List<AttendanceDtoInterface> findAttendanceList(List<HumanDtoInterface> list, Date firstDate,
			Date lastDate) throws MospException {
		List<AttendanceDtoInterface> attendanceList = new ArrayList<AttendanceDtoInterface>();
		for (HumanDtoInterface dto : list) {
			List<AttendanceDtoInterface> attendanceDtoList = findAttendanceList(dto.getPersonalId(), firstDate,
					lastDate);
			if (!attendanceDtoList.isEmpty()) {
				attendanceList.addAll(attendanceDtoList);
			}
		}
		return attendanceList;
	}
	
	/**
	 * 勤怠データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceDtoInterface> findAttendanceList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		List<AttendanceDtoInterface> list = new ArrayList<AttendanceDtoInterface>();
		for (AttendanceDtoInterface dto : attendanceDao.findForReapplicationExport(personalId, firstDate, lastDate)) {
			if (isReapplicationTarget(dto)) {
				// 再申請対象の場合
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 翌日の勤務形態コードを取得する。<br>
	 * @param dto 対象DTO
	 * @return 翌日の勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getTomorrowWorkTypeCode(AttendanceDtoInterface dto) throws MospException {
		return getWorkTypeCode(dto.getPersonalId(), addDay(dto.getWorkDate(), 1));
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkTypeCode(String personalId, Date targetDate) throws MospException {
		return scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
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
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param fileName 送出ファイル名
	 */
	protected void setFileName(String fileName) {
		// 送出ファイル名設定
		mospParams.setFileName(fileName);
	}
	
	/**
	 * エクスポートデータが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addNoExportDataMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Export", "Information"));
	}
	
	/**
	 * 再申請対象判断。<br>
	 * @param dto 対象DTO
	 * @return 再申請対象の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isReapplicationTarget(AttendanceDtoInterface dto) throws MospException {
		return isReapplicationTarget(dto, getTomorrowWorkTypeCode(dto));
	}
	
	/**
	 * 再申請対象判断。<br>
	 * @param dto 対象DTO
	 * @param tomorrowWorkTypeCode 翌日勤務形態コード
	 * @return 再申請対象の場合true、そうでない場合false
	 */
	protected boolean isReapplicationTarget(AttendanceDtoInterface dto, String tomorrowWorkTypeCode) {
		return (dto.getLegalWorkTime() > 0 && !TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())
				&& !TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)
				&& !TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode))
				|| (addDay(dto.getWorkDate(), 1).before(dto.getEndTime()) && dto.getLegalWorkTime() == 0
						&& (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)
								|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)));
	}
	
}
