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
package jp.mosp.platform.bean.file;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.platform.utils.PlatformNamingUtility;

/**
 * プラットフォームにおけるファイルエクスポート処理の基本機能を提供する。<br>
 * <br>
 */
public abstract class PlatformExportBean extends PlatformBean implements ExportBeanInterface {
	
	/**
	 * ファイル拡張子(CSV)。<br>
	 */
	protected static final String			FILENAME_EXTENSION_CSV	= ".csv";
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface			exportDao;
	
	/**
	 * エクスポートフィールド情報DAO。<br>
	 */
	protected ExportFieldDaoInterface		exportFieldDao;
	
	/**
	 * 人事マスタ検索処理。<br>
	 */
	protected HumanSearchBeanInterface		humanSearch;
	
	/**
	 * エクスポートマスタ情報。<br>
	 * {@link PlatformExportBean#setExportInfo(String)}で設定される。<br>
	 */
	protected ExportDtoInterface			exportDto;
	
	/**
	 * エクスポートフィールド情報。<br>
	 * {@link PlatformExportBean#setExportInfo(String)}で設定される。<br>
	 */
	protected List<ExportFieldDtoInterface>	fieldList;
	
	/**
	 * インデックス群(キー：インデックス、値：フィールド名称)(キー自然順序付け)。<br>
	 * {@link PlatformExportBean#setExportInfo(String)}で設定される。<br>
	 */
	protected Map<Integer, String>			indexes;
	
	
	/**
	 * {@link PlatformExportBean}を生成する。<br>
	 */
	public PlatformExportBean() {
		// 処理無し
	}
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected PlatformExportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		exportDao = (ExportDaoInterface)createDao(ExportDaoInterface.class);
		exportFieldDao = (ExportFieldDaoInterface)createDao(ExportFieldDaoInterface.class);
		// Beanを準備
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, Date targetDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException {
		// エクスポート情報を取得しフィールドに設定
		setExportInfo(exportCode);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// CSVデータリストを作成
		List<String[]> csvDataList = getCsvDataList(targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// CSVデータリストが空である場合
		if (csvDataList.isEmpty()) {
			// エラーメッセージを追加
			PlatformMessageUtility.addErrorNoExportInfo(mospParams);
			// 処理終了
			return;
		}
		// CSVデータリストにヘッダを付加
		addHeader(csvDataList);
		// CSVデータリストをMosP処理情報に設定
		setFile(csvDataList);
		// 送出ファイル名をMosP処理情報に設定
		setFileName(exportCode, targetDate);
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected abstract List<String[]> getCsvDataList(Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException;
	
	/**
	 * エクスポート情報を取得しフィールドに設定する。<br>
	 * @param exportCode エクスポートコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setExportInfo(String exportCode) throws MospException {
		// エクスポートマスタ情報を取得
		exportDto = exportDao.findForKey(exportCode);
		// エクスポートフィールド情報を取得
		fieldList = exportFieldDao.findForList(exportCode);
		// エクスポート情報が取得できなかった場合
		if (exportDto == null || fieldList.isEmpty()) {
			// エラーメッセージを追加
			PlatformMessageUtility.addErrorNoExportInfo(mospParams);
		}
		// インデックス群(キー：インデックス、値：フィールド名称)(キー自然順序付け)を準備
		indexes = new TreeMap<Integer, String>();
		// エクスポートフィールド情報毎に処理
		for (ExportFieldDtoInterface field : fieldList) {
			// インデックス群(キー：フィールド名称、値：インデックス)に追加
			indexes.put(field.getFieldOrder(), field.getFieldName());
		}
	}
	
	/**
	 * CSVデータリストにヘッダを付加する。<br>
	 * @param csvDataList CSVデータリスト
	 */
	protected void addHeader(List<String[]> csvDataList) {
		// ヘッダ有無が無である場合
		if (exportDto.getHeader() == PlatformFileConst.HEADER_TYPE_NONE) {
			// 処理無し
			return;
		}
		// データ区分を取得
		String exportTable = exportDto.getExportTable();
		// ヘッダ準備
		List<String> header = new ArrayList<String>();
		// フィールド毎にヘッダを付加
		for (ExportFieldDtoInterface field : fieldList) {
			header.add(getCodeName(field.getFieldName(), exportTable));
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, MospUtility.toArray(header));
	}
	
	/**
	 * CSVデータリストをMosP処理情報に設定する。<br>
	 * @param csvDataList CSVデータリスト
	 */
	protected void setFile(List<String[]> csvDataList) {
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param exportCode エクスポートコード
	 * @param targetDate 対象日
	 */
	protected void setFileName(String exportCode, Date targetDate) {
		// CSVファイル名作成
		StringBuilder sb = new StringBuilder();
		sb.append(exportCode);
		sb.append(PlatformNamingUtility.hyphen(mospParams));
		sb.append(DateUtility.getStringDateNoSeparator(targetDate));
		sb.append(FILENAME_EXTENSION_CSV);
		// 送出ファイル名を取得しMosP処理情報に設定
		mospParams.setFileName(sb.toString());
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchHumanData(Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
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
		return humanSearch.search();
	}
	
}
