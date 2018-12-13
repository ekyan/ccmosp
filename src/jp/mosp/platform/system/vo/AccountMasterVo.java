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
package jp.mosp.platform.system.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * アカウントマスタ画面の情報を格納する。
 */
public class AccountMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -6874451723281652141L;
	
	private String				pltEditRoleCode;
	private String				txtEditUserId;
	private String				txtEditEmployeeCode;
	
	/**
	 * 選択社員個人ID。
	 */
	private String				personalId;
	
	/**
	 * 選択社員名。
	 */
	private String				lblEditEmployeeName;
	
	/**
	 * ユーザロール情報レコードID。<br>
	 * 履歴編集対象のレコードIDを保持しておく。<br>
	 */
	private long				userRoleRecordId;
	
	private String				txtSearchUserId;
	private String				pltSearchEmployeeCode;
	private String				pltSearchEmployeeName;
	
	/**
	 * 一括更新ロールコード。<br>
	 */
	private String				pltUpdateRoleCode;
	
	/**
	 * 一括更新区分(ロール or 有効/無効)。<br>
	 */
	private String				radBatchUpdateType;
	
	private String[]			aryCkbSelect;
	private String[]			aryLblActivateDate;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblUserId;
	private String[]			aryLblRoleCode;
	
	/**
	 * 編集の上位所属プルダウンリスト
	 */
	private String[][]			aryPltEditRoleCode;
	
	private String[][]			aryPltUpdateRoleCode;
	
	private String				radUpdateSelectRadio;
	
	/**
	 * 有効日(一括更新)モード。<br>
	 */
	private String				modeUpdateActivateDate;
	
	/**
	 * 社員編集モード。<br>
	 * 設定値は、有効日モードを用いる。<br>
	 */
	private String				modeEditEmployee;
	
	
	/**
	 * @param pltEditRoleCode セットする pltEditRoleCode
	 */
	public void setPltEditRoleCode(String pltEditRoleCode) {
		this.pltEditRoleCode = pltEditRoleCode;
	}
	
	/**
	 * @return pltEditRoleCode
	 */
	public String getPltEditRoleCode() {
		return pltEditRoleCode;
	}
	
	/**
	 * @param txtEditUserId セットする txtEditUserId
	 */
	public void setTxtEditUserId(String txtEditUserId) {
		this.txtEditUserId = txtEditUserId;
	}
	
	/**
	 * @return txtEditUserId
	 */
	public String getTxtEditUserId() {
		return txtEditUserId;
	}
	
	/**
	 * @param txtEditEmployeeCode セットする txtEditEmployeeCode
	 */
	public void setTxtEditEmployeeCode(String txtEditEmployeeCode) {
		this.txtEditEmployeeCode = txtEditEmployeeCode;
	}
	
	/**
	 * @return txtEditEmployeeCode
	 */
	public String getTxtEditEmployeeCode() {
		return txtEditEmployeeCode;
	}
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return lblEditEmployeeName
	 */
	public String getLblEditEmployeeName() {
		return lblEditEmployeeName;
	}
	
	/**
	 * @param lblEditEmployeeName セットする lblEditEmployeeName
	 */
	public void setLblEditEmployeeName(String lblEditEmployeeName) {
		this.lblEditEmployeeName = lblEditEmployeeName;
	}
	
	/**
	 * @return userRoleRecordId
	 */
	public long getUserRoleRecordId() {
		return userRoleRecordId;
	}
	
	/**
	 * @param userRoleRecordId セットする userRoleRecordId
	 */
	public void setUserRoleRecordId(long userRoleRecordId) {
		this.userRoleRecordId = userRoleRecordId;
	}
	
	/**
	 * @param txtSearchUserId セットする txtSearchUserId
	 */
	public void setTxtSearchUserId(String txtSearchUserId) {
		this.txtSearchUserId = txtSearchUserId;
	}
	
	/**
	 * @return txtSearchUserId
	 */
	public String getTxtSearchUserId() {
		return txtSearchUserId;
	}
	
	/**
	 * @param pltSearchEmployeeCode セットする pltSearchEmployeeCode
	 */
	public void setPltSearchEmployeeCode(String pltSearchEmployeeCode) {
		this.pltSearchEmployeeCode = pltSearchEmployeeCode;
	}
	
	/**
	 * @return pltSearchEmployeeCode
	 */
	public String getPltSearchEmployeeCode() {
		return pltSearchEmployeeCode;
	}
	
	/**
	 * @param pltSearchEmployeeName セットする pltSearchEmployeeName
	 */
	public void setPltSearchEmployeeName(String pltSearchEmployeeName) {
		this.pltSearchEmployeeName = pltSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchEmployeeName
	 */
	public String getPltSearchEmployeeName() {
		return pltSearchEmployeeName;
	}
	
	/**
	 * @param pltUpdateRoleCode セットする pltUpdateRoleCode
	 */
	public void setPltUpdateRoleCode(String pltUpdateRoleCode) {
		this.pltUpdateRoleCode = pltUpdateRoleCode;
	}
	
	/**
	 * @return pltUpdateRoleCode
	 */
	public String getPltUpdateRoleCode() {
		return pltUpdateRoleCode;
	}
	
	/**
	 * @return radBatchUpdateType
	 */
	public String getRadBatchUpdateType() {
		return radBatchUpdateType;
	}
	
	/**
	 * @param radBatchUpdateType セットする radBatchUpdateType
	 */
	public void setRadBatchUpdateType(String radBatchUpdateType) {
		this.radBatchUpdateType = radBatchUpdateType;
	}
	
	/**
	 * @param aryCkbSelect セットする aryCkbSelect
	 */
	public void setAryCkbSelect(String[] aryCkbSelect) {
		this.aryCkbSelect = getStringArrayClone(aryCkbSelect);
	}
	
	/**
	 * @return aryCkbSelect
	 */
	public String[] getAryCkbSelect() {
		return getStringArrayClone(aryCkbSelect);
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeCode
	 */
	public String getAryLblEmployeeCode(int idx) {
		return aryLblEmployeeCode[idx];
	}
	
	/**
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblUserId セットする aryLblUserId
	 */
	public void setAryLblUserId(String[] aryLblUserId) {
		this.aryLblUserId = getStringArrayClone(aryLblUserId);
	}
	
	/**
	 * @return aryLblUserId
	 */
	public String[] getAryLblUserId() {
		return getStringArrayClone(aryLblUserId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblUserId
	 */
	public String getAryLblUserId(int idx) {
		return aryLblUserId[idx];
	}
	
	/**
	 * @param aryLblRoleCode セットする aryLblRoleCode
	 */
	public void setAryLblRoleCode(String[] aryLblRoleCode) {
		this.aryLblRoleCode = getStringArrayClone(aryLblRoleCode);
	}
	
	/**
	 * @return aryLblRoleCode
	 */
	public String[] getAryLblRoleCode() {
		return getStringArrayClone(aryLblRoleCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRoleCode
	 */
	public String getAryLblRoleCode(int idx) {
		return aryLblRoleCode[idx];
	}
	
	/**
	 * @param aryPltEditRoleCode セットする aryPltEditRoleCode
	 */
	public void setAryPltEditRoleCode(String[][] aryPltEditRoleCode) {
		this.aryPltEditRoleCode = getStringArrayClone(aryPltEditRoleCode);
	}
	
	/**
	 * @return aryPltEditRoleCode
	 */
	public String[][] getAryPltEditRoleCode() {
		return getStringArrayClone(aryPltEditRoleCode);
	}
	
	/**
	 * @param aryPltUpdateRoleCode セットする aryPltUpdateRoleCode
	 */
	public void setAryPltUpdateRoleCode(String[][] aryPltUpdateRoleCode) {
		this.aryPltUpdateRoleCode = getStringArrayClone(aryPltUpdateRoleCode);
	}
	
	/**
	 * @return aryPltUpdateRoleCode
	 */
	public String[][] getAryPltUpdateRoleCode() {
		return getStringArrayClone(aryPltUpdateRoleCode);
	}
	
	/**
	 * @param radUpdateSelectRadio セットする radUpdateSelectRadio
	 */
	public void setRadUpdateSelectRadio(String radUpdateSelectRadio) {
		this.radUpdateSelectRadio = radUpdateSelectRadio;
	}
	
	/**
	 * @return radUpdateSelectRadio
	 */
	public String getRadUpdateSelectRadio() {
		return radUpdateSelectRadio;
	}
	
	/**
	 * @return modeUpdateActivateDate
	 */
	public String getModeUpdateActivateDate() {
		return modeUpdateActivateDate;
	}
	
	/**
	 * @param modeUpdateActivateDate セットする modeUpdateActivateDate
	 */
	public void setModeUpdateActivateDate(String modeUpdateActivateDate) {
		this.modeUpdateActivateDate = modeUpdateActivateDate;
	}
	
	/**
	 * @return modeEditEmployee
	 */
	public String getModeEditEmployee() {
		return modeEditEmployee;
	}
	
	/**
	 * @param modeEditEmployee セットする modeEditEmployee
	 */
	public void setModeEditEmployee(String modeEditEmployee) {
		this.modeEditEmployee = modeEditEmployee;
	}
	
	/**
	 * ソートマーク出力。<br>
	 * @param key ソートキー
	 * @return HTMLソートマークの文字列
	 */
	public String getSortMark(String key) {
		String sortMark = "";
		if (key.equals(getComparatorName())) {
			if (isAscending()) {
				sortMark = "▲";
			} else {
				sortMark = "▼";
			}
		}
		return sortMark;
	}
}
