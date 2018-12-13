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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.UserExportBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザマスタエクスポートクラス。
 */
public class UserExportBean extends PlatformBean implements UserExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface			exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface		exportFieldDao;
	
	/**
	 * アカウント情報DAO。<br>
	 */
	protected UserMasterDaoInterface		userMasterDao;
	
	/**
	 * 人事マスタ検索クラス。<br>
	 */
	protected HumanSearchBeanInterface		humanSearch;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface	sectionReference;
	
	/**
	 * 人事マスタ情報リスト。<br>
	 */
	protected List<HumanDtoInterface>		humanList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserExportBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected UserExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanの準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		userMasterDao = (UserMasterDaoInterface)createDao(UserMasterDaoInterface.class);
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
		// 人事情報検索
		searchHumanData(csvDataList, fieldList, targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// アカウント情報付加
		addUserMasterData(csvDataList, fieldList, targetDate);
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
			header[i++] = getCodeName(field.getFieldName(), PlatformFileConst.CODE_KEY_TABLE_TYPE_USER);
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, header);
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
	 * @param csvDataList            CSVデータリスト
	 * @param fieldList              エクスポートフィールド情報リスト
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void searchHumanData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date targetDate, String workPlaceCode, String employmentContractCode, String sectionCode,
			String positionCode) throws MospException {
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
	}
	
	/**
	 * CSVデータリストにアカウント情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addUserMasterData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date targetDate) throws MospException {
		// アカウント情報フィールド情報準備
		Integer userIdIndex = null;
		Integer activateDateIndex = null;
		Integer employeeCodeIndex = null;
		Integer employeeNameIndex = null;
		Integer sectionNameIndex = null;
		Integer sectionDisplayIndex = null;
		Integer roleCodeIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface field : fieldList) {
			if (field.getFieldName().equals(PlatformFileConst.FIELD_USER_ID)) {
				userIdIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_ACTIVATE_DATE)) {
				activateDateIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_EMPLOYEE_CODE)) {
				employeeCodeIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_FULL_NAME)) {
				employeeNameIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_USER_ROLE_CODE)) {
				roleCodeIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_SECTION_NAME)) {
				sectionNameIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_SECTION_DISPLAY)) {
				sectionDisplayIndex = Integer.valueOf(field.getFieldOrder() - 1);
			}
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humanList.get(i);
			// アカウント情報取得及び確認
			List<UserMasterDtoInterface> userMasterList = userMasterDao.findForPersonalId(human.getPersonalId(),
					targetDate);
			for (UserMasterDtoInterface userMaster : userMasterList) {
				// CSVデータ準備
				String[] csvData = new String[fieldList.size()];
				// アカウント情報設定
				if (userIdIndex != null) {
					csvData[userIdIndex.intValue()] = userMaster.getUserId();
				}
				if (activateDateIndex != null) {
					csvData[activateDateIndex.intValue()] = getStringDate(userMaster.getActivateDate());
				}
				if (employeeCodeIndex != null) {
					csvData[employeeCodeIndex.intValue()] = human.getEmployeeCode();
				}
				if (employeeNameIndex != null) {
					csvData[employeeNameIndex.intValue()] = MospUtility.getHumansName(human.getFirstName(),
							human.getLastName());
				}
				if (roleCodeIndex != null) {
					csvData[roleCodeIndex.intValue()] = userMaster.getRoleCode();
				}
				if (sectionNameIndex != null) {
					csvData[sectionNameIndex.intValue()] = sectionReference.getSectionName(human.getSectionCode(),
							targetDate);
				}
				if (sectionDisplayIndex != null) {
					csvData[sectionDisplayIndex.intValue()] = sectionReference.getSectionDisplay(
							human.getSectionCode(), targetDate);
				}
				// CSVデータをCSVデータリストに追加
				csvDataList.add(csvData);
			}
		}
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
