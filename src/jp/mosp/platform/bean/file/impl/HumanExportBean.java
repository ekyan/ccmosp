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
package jp.mosp.platform.bean.file.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.HumanExportBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.file.action.ExportCardAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事マスタエクスポートクラス。
 */
public class HumanExportBean extends PlatformBean implements HumanExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface					exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface				exportFieldDao;
	
	/**
	 * 人事入社情報DAO。<br>
	 */
	protected EntranceDaoInterface					entranceDao;
	
	/**
	 * 人事退職情報DAO。<br>
	 */
	protected RetirementDaoInterface				retirementDao;
	
	/**
	 * 人事汎用通常情報参照クラス。<br>
	 */
	protected HumanNormalReferenceBeanInterface		humanNoraml;
	
	/**
	 * 人事汎用履歴情報参照クラス。<br>
	 */
	protected HumanHistoryReferenceBeanInterface	humanHistory;
	
	/**
	 * 人事汎用一覧情報参照クラス。<br>
	 */
	protected HumanArrayReferenceBeanInterface		humanArray;
	
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
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanExportBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected HumanExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanの準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		entranceDao = (EntranceDaoInterface)createDao(EntranceDaoInterface.class);
		retirementDao = (RetirementDaoInterface)createDao(RetirementDaoInterface.class);
		humanNoraml = (HumanNormalReferenceBeanInterface)createBean(HumanNormalReferenceBeanInterface.class);
		humanHistory = (HumanHistoryReferenceBeanInterface)createBean(HumanHistoryReferenceBeanInterface.class);
		humanArray = (HumanArrayReferenceBeanInterface)createBean(HumanArrayReferenceBeanInterface.class);
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, Date targetDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを作成
		List<String[]> csvDataList = getCsvDataList(exportCode, targetDate, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// CSVデータリスト確認
		if (csvDataList.isEmpty()) {
			// 該当するエクスポート情報が存在しない場合
			addNoExportDataMessage();
			return;
		}
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
		// 送出ファイル名をMosP処理情報に設定
		setFileName(exportCode, targetDate);
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * @param exportCode             エクスポートコード
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(String exportCode, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// CSVデータリスト準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// エクスポートマスタ情報取得
		ExportDtoInterface exportDto = exportDao.findForKey(exportCode);
		// エクスポートフィールド情報取得
		List<ExportFieldDtoInterface> fieldList = exportFieldDao.findForList(exportCode);
		// 人事情報付加
		addHumanData(csvDataList, fieldList, targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// 人事入社情報付加
		addEntranceData(csvDataList, fieldList);
		// 人事退職情報付加
		addRetirementData(csvDataList, fieldList);
		//TODO 人事汎用情報
		addHumanGeneralData(csvDataList, fieldList, targetDate);
		// ヘッダ情報付加
		if (exportDto.getHeader() != PlatformFileConst.HEADER_TYPE_NONE) {
			addHeader(csvDataList, fieldList);
		}
		return csvDataList;
	}
	
	/**
	 * CSVデータリストにヘッダを付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 */
	protected void addHeader(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList) {
		// ヘッダ準備
		String[] header = new String[fieldList.size()];
		// インデックス準備
		int i = 0;
		// フィールド毎にヘッダを付加
		for (ExportFieldDtoInterface field : fieldList) {
			// ヘッダ名取得
			String headerName = mospParams.getName(field.getFieldName());
			// ヘッダ名が存在する場合
			if (headerName != null && headerName.isEmpty() == false) {
				header[i++] = headerName;
				continue;
			}
			// 項目コードをdivisionと項目キーに分割
			String[] fieldNames = MospUtility.split(field.getFieldName(), MospConst.APP_PROPERTY_SEPARATOR);
			// 配列2番目がある場合
			if (fieldNames.length > 1) {
				header[i++] = mospParams.getName(fieldNames[1]) + mospParams.getName("FrontParentheses")
						+ mospParams.getName(fieldNames[0]) + mospParams.getName("BackParentheses");
				continue;
			}
			// コードからヘッダ名取得
			header[i++] = getCodeName(field.getFieldName(), PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN);
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, header);
	}
	
	/**
	 * 検索条件に基づき人事情報を検索し、CSVデータリストに付加する。<br>
	 * @param csvDataList            CSVデータリスト
	 * @param fieldList              エクスポートフィールド情報リスト
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHumanData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList, Date targetDate,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode)
			throws MospException {
		// 人事情報検索条件設定
		humanSearch.setTargetDate(targetDate);
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
		humanList = humanSearch.search();
		// 人事情報毎にCSVデータリストに付加
		for (HumanDtoInterface human : humanList) {
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			// インデックス準備
			int i = 0;
			// フィールド情報毎にCSVデータに人事情報を付加
			for (ExportFieldDtoInterface field : fieldList) {
				csvData[i++] = getHumanData(human, field.getFieldName(), targetDate);
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
	}
	
	/**
	 * CSVデータリストに入社情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addEntranceData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList)
			throws MospException {
		// 入社情報フィールド情報準備
		Integer entranceDateIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface field : fieldList) {
			if (field.getFieldName().equals(PlatformFileConst.FIELD_ENTRANCE_DATE)) {
				entranceDateIndex = Integer.valueOf(field.getFieldOrder() - 1);
				break;
			}
		}
		// 入社情報出力要否確認
		if (entranceDateIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humanList.get(i);
			// 入社情報取得及び確認
			EntranceDtoInterface entrance = entranceDao.findForInfo(human.getPersonalId());
			if (entrance == null) {
				continue;
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 入社情報設定
			csvData[entranceDateIndex.intValue()] = getStringDate(entrance.getEntranceDate());
		}
	}
	
	/**
	 * CSVデータリストに退職情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addRetirementData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList)
			throws MospException {
		// 退職情報フィールド情報準備
		Integer retirementDateIndex = null;
		Integer retirementReasonIndex = null;
		Integer retirementDetailIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface field : fieldList) {
			if (field.getFieldName().equals(PlatformFileConst.FIELD_RETIREMENT_DATE)) {
				retirementDateIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_RETIREMENT_REASON)) {
				retirementReasonIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_RETIREMENT_DETAIL)) {
				retirementDetailIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
		}
		// 退職情報出力要否確認
		if (retirementDateIndex == null && retirementReasonIndex == null && retirementDetailIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humanList.get(i);
			// 退職情報取得及び確認
			RetirementDtoInterface retirement = retirementDao.findForInfo(human.getPersonalId());
			if (retirement == null) {
				continue;
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 退職情報設定
			if (retirementDateIndex != null) {
				csvData[retirementDateIndex.intValue()] = getStringDate(retirement.getRetirementDate());
			}
			if (retirementReasonIndex != null) {
				csvData[retirementReasonIndex.intValue()] = retirement.getRetirementReason();
			}
			if (retirementDetailIndex != null) {
				csvData[retirementDetailIndex.intValue()] = retirement.getRetirementDetail();
			}
		}
	}
	
	/**
	 * CSVデータリストに人事汎用通常情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHumanGeneralData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date targetDate) throws MospException {
		// エクスポートフィールドマスタ情報毎に処理
		for (ExportFieldDtoInterface field : fieldList) {
			// 項目コードをdivisionと項目キーに分割
			String[] fieldName = MospUtility.split(field.getFieldName(), MospConst.APP_PROPERTY_SEPARATOR);
			// 人事管理表示設定情報を取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(fieldName[0]);
			// 人事汎用区分でない場合
			if (viewConfig == null) {
				continue;
			}
			// 項目インデックス取得
			Integer index = Integer.valueOf(field.getFieldOrder() - 1);
			if (index == null) {
				return;
			}
			// CSVデータリスト毎に情報を付加
			for (int i = 0; i < humanList.size(); i++) {
				// 人事情報取得
				HumanDtoInterface human = humanList.get(i);
				// 値準備
				String value = "";
				// 通常の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
					// 人事汎用通常情報をマップ取得
					value = humanNoraml.getNormalItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, targetDate, fieldName[1], true);
				}
				// 履歴の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
					value = humanHistory.getHistoryItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, fieldName[1], true);
				}
				// 一覧の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
					value = humanArray.getArrayItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, fieldName[1], true);
				}
				// CSVデータ取得
				String[] csvData = csvDataList.get(i);
				csvData[index] = value;
			}
		}
	}
	
	/**
	 * 社員一覧情報からフィールド名に対応する情報を取得する。<br>
	 * @param human     社員一覧情報
	 * @param fieldName フィールド名
	 * @param targetDate 対象日
	 * @return フィールド名に対応する情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getHumanData(HumanDtoInterface human, String fieldName, Date targetDate) throws MospException {
		// フィールド名に対応する情報を準備
		String data = "";
		// フィールド名で取得情報を判断
		if (fieldName.equals(PlatformFileConst.FIELD_EMPLOYEE_CODE)) {
			data = human.getEmployeeCode();
		} else if (fieldName.equals(PlatformFileConst.FIELD_ACTIVATE_DATE)) {
			data = getStringDate(human.getActivateDate());
		} else if (fieldName.equals(PlatformFileConst.FIELD_LAST_NAME)) {
			data = human.getLastName();
		} else if (fieldName.equals(PlatformFileConst.FIELD_FIRST_NAME)) {
			data = human.getFirstName();
		} else if (fieldName.equals(PlatformFileConst.FIELD_LAST_KANA)) {
			data = human.getLastKana();
		} else if (fieldName.equals(PlatformFileConst.FIELD_FIRST_KANA)) {
			data = human.getFirstKana();
		} else if (fieldName.equals(PlatformFileConst.FIELD_WORK_PLACE_CODE)) {
			data = human.getWorkPlaceCode();
		} else if (fieldName.equals(PlatformFileConst.FIELD_EMPLOYMENT_CONTRACT_CODE)) {
			data = human.getEmploymentContractCode();
		} else if (fieldName.equals(PlatformFileConst.FIELD_SECTION_CODE)) {
			data = human.getSectionCode();
		} else if (fieldName.equals(PlatformFileConst.FIELD_POSITION_CODE)) {
			data = human.getPositionCode();
		} else if (fieldName.equals(PlatformFileConst.FIELD_SECTION_NAME)) {
			data = sectionReference.getSectionName(human.getSectionCode(), targetDate);
		} else if (fieldName.equals(PlatformFileConst.FIELD_SECTION_DISPLAY)) {
			data = sectionReference.getSectionDisplay(human.getSectionCode(), targetDate);
		}
		return data;
	}
	
	@Override
	public boolean isExistLikeFieldName(String exportCode, String[] aryFieldName) throws MospException {
		List<ExportFieldDtoInterface> list = exportFieldDao.findLikeStartNameList(exportCode, aryFieldName);
		list = list == null ? new ArrayList<ExportFieldDtoInterface>() : list;
		
		return list.isEmpty() ? false : true;
		
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param exportCode エクスポートコード
	 * @param targetDate 対象日
	 */
	protected void setFileName(String exportCode, Date targetDate) {
		// CSVファイル名作成
		String fileName = exportCode + mospParams.getName("Hyphen") + DateUtility.getStringYear(targetDate)
				+ DateUtility.getStringMonth(targetDate) + DateUtility.getStringDay(targetDate) + ".csv";
		// 送出ファイル名設定
		mospParams.setFileName(fileName);
	}
	
	/**
	 * エクスポートデータが存在しない場合のメッセージを設定する。<br>
	 */
	protected void addNoExportDataMessage() {
		String rep = mospParams.getName("Export", "Information");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
	
}
