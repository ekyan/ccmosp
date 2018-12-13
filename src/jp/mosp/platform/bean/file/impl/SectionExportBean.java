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
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.SectionExportBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.system.impl.PfmSectionDao;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.system.constant.PlatformSystemConst;

/**
 * 所属マスタエクスポートクラス。
 */
public class SectionExportBean extends PlatformBean implements SectionExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface			exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface		exportFieldDao;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface	sectionReference;
	
	/**
	 * 所属マスタ検索クラス。<br>
	 */
	protected SectionSearchBeanInterface	sectionSearch;
	
	/**
	 * 所属マスタ情報リスト。<br>
	 */
	protected List<SectionDtoInterface>		sectionList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SectionExportBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SectionExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanの準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
		sectionSearch = (SectionSearchBeanInterface)createBean(SectionSearchBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, Date targetDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを作成
		List<String[]> csvDataList = getCsvDataList(exportCode, targetDate, sectionCode);
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
	 * @param exportCode  エクスポートコード
	 * @param targetDate  対象日
	 * @param sectionCode 所属コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(String exportCode, Date targetDate, String sectionCode)
			throws MospException {
		// CSVデータリスト準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// エクスポートマスタ情報取得
		ExportDtoInterface exportDto = exportDao.findForKey(exportCode);
		// エクスポートフィールド情報取得
		List<ExportFieldDtoInterface> fieldList = exportFieldDao.findForList(exportCode);
		// 所属情報検索
		searchSectionMasterData(csvDataList, fieldList, targetDate, sectionCode);
		// 所属情報付加
		addSectionMasterData(csvDataList, fieldList, targetDate);
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
			header[i++] = getCodeName(field.getFieldName(), PlatformFileConst.CODE_KEY_TABLE_TYPE_SECTION);
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, header);
	}
	
	/**
	 * 検索条件に基づき所属情報を検索する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param targetDate  対象日
	 * @param sectionCode 所属コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void searchSectionMasterData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date targetDate, String sectionCode) throws MospException {
		// 所属情報検索条件設定
		sectionSearch.setActivateDate(targetDate);
		sectionSearch.setSectionCode(sectionCode);
		sectionSearch.setSectionName("");
		sectionSearch.setSectionAbbr("");
		sectionSearch.setSectionType(PlatformSystemConst.SEARCH_SECTION_ROUTE);
		sectionSearch.setCloseFlag(Integer.toString(MospConst.DELETE_FLAG_OFF));
		sectionList = sectionSearch.getExportList();
	}
	
	/**
	 * CSVデータリストに所属情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param targetDate  対象日
	 */
	protected void addSectionMasterData(List<String[]> csvDataList, List<ExportFieldDtoInterface> fieldList,
			Date targetDate) {
		// 所属情報フィールド情報準備
		Integer sectionCodeIndex = null;
		Integer activateDateIndex = null;
		Integer sectionNameIndex = null;
		Integer sectionAbbrIndex = null;
		Integer sectionDisplayIndex = null;
		Integer upperSectionCodeIndex = null;
		Integer closeFlagIndex = null;
		// フィールドリスト確認
		for (ExportFieldDtoInterface field : fieldList) {
			Integer index = Integer.valueOf(field.getFieldOrder() - 1);
			if (field.getFieldName().equals(PfmSectionDao.COL_SECTION_CODE)) {
				sectionCodeIndex = index;
			}
			if (field.getFieldName().equals(PfmSectionDao.COL_ACTIVATE_DATE)) {
				activateDateIndex = index;
			}
			if (field.getFieldName().equals(PfmSectionDao.COL_SECTION_NAME)) {
				sectionNameIndex = index;
			}
			if (field.getFieldName().equals(PfmSectionDao.COL_SECTION_ABBR)) {
				sectionAbbrIndex = index;
			}
			if (field.getFieldName().equals(PfmSectionDao.COL_SECTION_DISPLAY)) {
				sectionDisplayIndex = index;
			}
			if (field.getFieldName().equals(PlatformFileConst.FIELD_UPPER_SECTION_CODE)) {
				upperSectionCodeIndex = index;
			}
			if (field.getFieldName().equals(PfmSectionDao.COL_CLOSE_FLAG)) {
				closeFlagIndex = index;
			}
		}
		// CSVデータリスト毎に情報を付加
		for (SectionDtoInterface dto : sectionList) {
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			// 所属情報設定
			if (sectionCodeIndex != null) {
				csvData[sectionCodeIndex.intValue()] = dto.getSectionCode();
			}
			if (activateDateIndex != null) {
				csvData[activateDateIndex.intValue()] = getStringDate(dto.getActivateDate());
			}
			if (sectionNameIndex != null) {
				csvData[sectionNameIndex.intValue()] = dto.getSectionName();
			}
			if (sectionAbbrIndex != null) {
				csvData[sectionAbbrIndex.intValue()] = dto.getSectionAbbr();
			}
			if (sectionDisplayIndex != null) {
				csvData[sectionDisplayIndex.intValue()] = dto.getSectionDisplay();
			}
			if (upperSectionCodeIndex != null) {
				csvData[upperSectionCodeIndex.intValue()] = getUpperSectionCode(dto);
			}
			if (closeFlagIndex != null) {
				csvData[closeFlagIndex.intValue()] = Integer.toString(dto.getCloseFlag());
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
	}
	
	/**
	 * 上位所属コードを取得する。
	 * @param dto 対象DTO
	 * @return 上位所属コード
	 */
	protected String getUpperSectionCode(SectionDtoInterface dto) {
		String[] sectionArray = sectionReference.getClassRouteArray(dto);
		int length = sectionArray.length;
		if (length == 0) {
			return "";
		}
		return sectionArray[--length];
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
