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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤務形態パターンの情報を格納する。
 */
public class WorkTypePatternCardVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 1280472632092676759L;
	private String				txtPatternCode;
	private String				txtPatternName;
	private String				txtPatternAbbr;
	private String[][]			jsPltSelectTable;
	private String[]			jsPltSelectSelected;
	
	
	/**
	 * @return txtPatternCode
	 */
	public String getTxtPatternCode() {
		return txtPatternCode;
	}
	
	/**
	 * @param txtPatternCode セットする txtPatternCode
	 */
	public void setTxtPatternCode(String txtPatternCode) {
		this.txtPatternCode = txtPatternCode;
	}
	
	/**
	 * @return txtPatternName
	 */
	public String getTxtPatternName() {
		return txtPatternName;
	}
	
	/**
	 * @param txtPatternName セットする txtPatternName
	 */
	public void setTxtPatternName(String txtPatternName) {
		this.txtPatternName = txtPatternName;
	}
	
	/**
	 * @return txtPatternAbbr
	 */
	public String getTxtPatternAbbr() {
		return txtPatternAbbr;
	}
	
	/**
	 * @param txtPatternAbbr セットする txtPatternAbbr
	 */
	public void setTxtPatternAbbr(String txtPatternAbbr) {
		this.txtPatternAbbr = txtPatternAbbr;
	}
	
	/**
	 * @return jsPltSelectTable
	 */
	public String[][] getJsPltSelectTable() {
		return getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @param jsPltSelectTable セットする jsPltSelectTable
	 */
	public void setJsPltSelectTable(String[][] jsPltSelectTable) {
		this.jsPltSelectTable = getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @return jsPltSelectSelected
	 */
	public String[] getJsPltSelectSelected() {
		return getStringArrayClone(jsPltSelectSelected);
	}
	
	/**
	 * @param jsPltSelectSelected セットする jsPltSelectSelected
	 */
	public void setJsPltSelectSelected(String[] jsPltSelectSelected) {
		this.jsPltSelectSelected = getStringArrayClone(jsPltSelectSelected);
	}
	
}
