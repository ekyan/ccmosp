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

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.orangesignal.OrangeSignalParams;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;

/**
 * プラットフォームにおけるファイルインポートBeanの基本機能を提供する。<br>
 * <br>
 */
public abstract class PlatformFileBean extends PlatformBean {
	
	/**
	 * {@link PlatformHumanBean}を生成する。<br>
	 */
	public PlatformFileBean() {
		// 処理無し
	}
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected PlatformFileBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	/**
	 * 登録情報リストを取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストを取得する。<br>
	 * @param importDto     インポートマスタ情報
	 * @param requestedFile リクエストされたファイル
	 * @return 登録情報リスト
	 * @throws MospException 登録情報リストの取得に失敗した場合
	 */
	protected List<String[]> getDataList(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// 登録情報リスト準備
		List<String[]> dataList = new ArrayList<String[]>();
		// インポートマスタ情報(ファイル区分)確認
		if (importDto.getType().equals(PlatformFileConst.FILE_TYPE_CSV)) {
			// アップロードファイルを登録情報リストに変換(CSVの場合)
			dataList = parse(requestedFile);
		} else {
			// エラーメッセージ設定
			addFileTypeNotExistMessage();
			return dataList;
		}
		// インポートマスタ情報(ヘッダ有無)確認
		if (importDto.getHeader() != PlatformFileConst.HEADER_TYPE_NONE) {
			// ヘッダ除去(登録情報リストから1行目を除く)
			if (dataList.size() > 0) {
				dataList.remove(0);
			}
		}
		// 登録情報件数確認
		if (dataList.size() == 0) {
			// エラーメッセージ設定
			addFileDataNotExistMessage();
		}
		return dataList;
	}
	
	/**
	 * 登録情報リストの社員コードを個人IDに変換する。<br>
	 * <br>
	 * インポートマスタ情報に従い、フィールド名称が
	 * {@link PfmHumanDao#COL_PERSONAL_ID}と{@link PfmHumanDao#COL_ACTIVATE_DATE}の
	 * ものを登録情報から取得し、それらを用いて個人IDを取得する。<br>
	 * <br>
	 * 個人IDが取得できなかった場合は、空文字を返す。<br>
	 * <br>
	 * @param fieldList         インポートフィールド情報リスト
	 * @param dataList          登録情報リスト
	 * @param activateDateField 有効日フィールド名
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void convertEmployeeCodeIntoPersonalId(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList,
			String activateDateField) throws MospException {
		// 個人ID(社員コード)及び有効日のフィールドインデックスを取得
		Integer personalIdIndex = getFieldIndex(PfmHumanDao.COL_PERSONAL_ID, fieldList);
		Integer activateDateIndex = getFieldIndex(activateDateField, fieldList);
		// フィールドインデックスを確認
		if (personalIdIndex == null || activateDateIndex == null) {
			return;
		}
		// 人事情報参照クラス準備
		HumanReferenceBeanInterface refer = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// 個人ID(社員コード)及び有効日を取得
			String employeeCode = getFieldValue(PfmHumanDao.COL_PERSONAL_ID, fieldList, data);
			Date activateDate = getDateFieldValue(activateDateField, fieldList, data);
			// 処理行数準備
			int row = i + 1;
			// 個人ID(社員コード)及び有効日を確認
			// 必須確認(ユニットコード)
			checkRequired(employeeCode, getNameEmployeeCode(), row);
			// 必須確認(有効日)
			checkRequired(activateDate, getNameActivateDate(), row);
			// 個人ID及び有効日を確認
			if (employeeCode == null || employeeCode.isEmpty() || activateDate == null) {
				continue;
			}
			// 有効日を対象日として個人IDを取得
			String personalId = refer.getPersonalId(employeeCode, activateDate);
			// 社員コードを個人IDに置換
			data[personalIdIndex] = personalId;
		}
	}
	
	/**
	 * 登録情報リストの社員コードを個人IDに変換する。<br>
	 * <br>
	 * 有効日フィールド名は{@link PfmHumanDao#COL_PERSONAL_ID}とする。<br>
	 * <br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList  登録情報リスト
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void convertEmployeeCodeIntoPersonalId(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList)
			throws MospException {
		// 登録情報リストの社員コードを個人IDに変換
		convertEmployeeCodeIntoPersonalId(fieldList, dataList, PfmHumanDao.COL_ACTIVATE_DATE);
	}
	
	/**
	 * リクエストされたファイルをCSVとして解析する。
	 * @param stream リクエストされたファイル
	 * @return 解析結果(文字列配列のリスト)
	 * @throws MospException 入出力例外が発生した場合
	 */
	protected List<String[]> parse(InputStream stream) throws MospException {
		return parse(stream, createOrangeSignalParams());
	}
	
	/**
	 * リクエストされたファイルをCSVとして解析する。
	 * @param stream リクエストされたファイル
	 * @param orangeParams OrangeSignal処理情報
	 * @return 解析結果(文字列配列のリスト)
	 * @throws MospException 入出力例外が発生した場合
	 */
	protected List<String[]> parse(InputStream stream, OrangeSignalParams orangeParams) throws MospException {
		List<String[]> list = OrangeSignalUtility.parse(stream, orangeParams);
		// TODO インポートの際の空白除去(コードのみ除去したい)
		/*
		for (String[] array : list) {
			if (array == null) {
				continue;
			}
			for (int i = 0; i < array.length; i++) {
				// 空白抜く
				array[i] = array[i] == null ? "" : array[i].trim();
			}
		}
		*/
		return list;
	}
	
	/**
	 * @return OrangeSignal処理情報
	 */
	protected OrangeSignalParams createOrangeSignalParams() {
		return new OrangeSignalParams();
	}
	
	/**
	 * インポートフィールド情報リストを取得する。<br>
	 * @param importCode インポートコード
	 * @return インポートフィールド情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<ImportFieldDtoInterface> getImportFieldList(String importCode) throws MospException {
		// インポートフィールド情報取得準備
		ImportFieldReferenceBeanInterface refer = (ImportFieldReferenceBeanInterface)createBean(ImportFieldReferenceBeanInterface.class);
		// インポートフィールド情報取得
		return refer.getImportFieldList(importCode);
	}
	
	/**
	 * フィールドインデックスを取得する。<br>
	 * <br>
	 * フィールドインデックスは0から始まる。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * <br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @return フィールドインデックス
	 */
	protected Integer getFieldIndex(String fieldName, List<ImportFieldDtoInterface> fieldList) {
		// フィールドリスト確認
		for (ImportFieldDtoInterface field : fieldList) {
			// フィールド確認
			if (field.getFieldName().equals(fieldName)) {
				return new Integer(field.getFieldOrder() - 1);
			}
		}
		return null;
	}
	
	/**
	 * 登録情報からフィールドに対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値
	 */
	protected String getFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// フィールドインデックスを取得
		Integer fieldIndex = getFieldIndex(fieldName, fieldList);
		// フィールドインデックスを確認
		if (fieldIndex == null) {
			return null;
		}
		// フィールドに対応する値を取得
		return data[fieldIndex];
	}
	
	/**
	 * 登録情報からフィールド(日付)に対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値(日付)
	 */
	protected Date getDateFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から日付文字列を取得
		String date = getFieldValue(fieldName, fieldList, data);
		// 日付文字列確認
		if (date == null || date.isEmpty()) {
			return null;
		}
		// 日付取得
		return getDate(date);
	}
	
	/**
	 * 登録情報からフィールド(分)に対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * <br>
	 * 基準時刻からの経過時刻として、取得する。<br>
	 * <br>
	 * @param fieldName フィールド名
	 * @param baseTime  基準時刻
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値(時刻)
	 */
	protected Date getMinutesFieldValue(String fieldName, Date baseTime, List<ImportFieldDtoInterface> fieldList,
			String[] data) {
		// 登録情報から時刻(分)を取得
		Integer minutes = getInteger(getFieldValue(fieldName, fieldList, data));
		// 時刻文字列確認
		if (minutes == null || baseTime == null) {
			return null;
		}
		// 時刻取得
		return DateUtility.addMinute(baseTime, minutes);
	}
	
	/**
	 * 登録情報からフィールド(数値)に対応する値を取得する。<br>
	 * 取得できなかった場合、0を返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値(数値)
	 */
	protected int getIntegerFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から数値文字列を取得
		String strInteger = getFieldValue(fieldName, fieldList, data);
		// 数値文字列確認
		if (strInteger == null || strInteger.isEmpty()) {
			return 0;
		}
		// 数値取得
		Integer objInteger = getInteger(strInteger);
		// 数値取得
		return objInteger == null ? 0 : objInteger.intValue();
	}
	
	/**
	 * 登録情報からフィールド(数値)に対応する値を取得する。<br>
	 * 取得できなかった場合、0を返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値(数値)
	 */
	protected double getDoubleFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から数値文字列を取得
		String strDouble = getFieldValue(fieldName, fieldList, data);
		// 数値文字列確認
		if (strDouble == null || strDouble.isEmpty()) {
			return 0;
		}
		// 数値取得
		Double objDouble = getDouble(strDouble);
		// 数値取得
		return objDouble == null ? 0D : objDouble.doubleValue();
	}
	
	/**
	 * 登録情報リスト内の各登録情報長を確認する。<br>
	 * インポートフィールド情報リスト長と異なる場合、エラーメッセージを設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList   登録情報リスト
	 */
	protected void checkCsvLength(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) {
		// 登録情報リスト確認
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報長確認
			if (dataList.get(i).length == fieldList.size()) {
				continue;
			}
			// エラーメッセージ追加
			addDataLengthErrorMessage(i);
			break;
		}
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。
	 * @param date 日付文字列(yyyyMMdd 又は yyyy/MM/dd)
	 * @return 日付
	 */
	protected Date getDate(String date) {
		if (date.indexOf("/") == -1) {
			return DateUtility.getDate(date, "yyyyMMdd");
		}
		return DateUtility.getDate(date);
	}
	
	/**
	 * ファイル区分が存在しない場合のメッセージを設定する。<br>
	 */
	protected void addFileTypeNotExistMessage() {
		String[] rep = { mospParams.getName("Data", "Type") };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
	
	/**
	 * ファイルに登録情報が存在しない場合のメッセージを設定する。<br>
	 */
	protected void addFileDataNotExistMessage() {
		String[] rep = { mospParams.getName("Insert", "Data") };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, rep);
	}
	
	/**
	 * 更新元人事マスタ情報が取得できなかった場合のエラーメッセージを追加する。<br>
	 * @param row          対象行インデックス
	 * @param employeeCode 社員コード
	 * @param activateDate 有効日
	 */
	protected void addEmployeeHistoryNotExistMessage(int row, String employeeCode, Date activateDate) {
		String rep = getRowColonName(row) + getStringDate(activateDate);
		mospParams.addErrorMessage(PlatformMessageConst.MSG_EMP_HISTORY_NOT_EXIST, rep, employeeCode);
	}
	
	/**
	 * 登録情報長が不正な場合のエラーメッセージを追加する。<br>
	 * @param row 対象行インデックス
	 */
	protected void addDataLengthErrorMessage(int row) {
		String rep = getRowColonName(row) + mospParams.getName("Insert", "Data");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INPUT_FORM_AMP, rep, null);
	}
	
}
