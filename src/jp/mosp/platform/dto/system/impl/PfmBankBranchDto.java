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
package jp.mosp.platform.dto.system.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.BankBranchDtoInterface;

/**
 * 銀行支店マスタDTOクラス。<br>
 */
public class PfmBankBranchDto extends BaseDto implements BankBranchDtoInterface {
	
	private static final long	serialVersionUID	= -6653217282843114583L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmBankBranchId;
	
	/**
	 * 郵便番号。
	 */
	private String				bankCode;
	
	/**
	 * 郵便番号。
	 */
	private String				branchCode;
	
	/**
	 * 郵便番号。
	 */
	private String				branchName;
	
	/**
	 * 郵便番号。
	 */
	private String				branchKana;
	
	/**
	 * 郵便番号。
	 */
	private String				branchAddressName;
	
	/**
	 * 郵便番号。
	 */
	private String				branchAddressKana;
	
	/**
	 * 郵便番号。
	 */
	private String				branchPhone;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmBankBranchId() {
		return pfmBankBranchId;
	}
	
	@Override
	public String getBankCode() {
		return bankCode;
	}
	
	@Override
	public String getBranchCode() {
		return branchCode;
	}
	
	@Override
	public String getBranchName() {
		return branchName;
	}
	
	@Override
	public String getBranchKana() {
		return branchKana;
	}
	
	@Override
	public String getBranchAddressName() {
		return branchAddressName;
	}
	
	@Override
	public String getBranchAddressKana() {
		return branchAddressKana;
	}
	
	@Override
	public String getBranchPhone() {
		return branchPhone;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmBankBranchId(long pfmBankBranchId) {
		this.pfmBankBranchId = pfmBankBranchId;
	}
	
	@Override
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Override
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	@Override
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	@Override
	public void setBranchKana(String branchKana) {
		this.branchKana = branchKana;
	}
	
	@Override
	public void setBranchAddressName(String branchAddressName) {
		this.branchAddressName = branchAddressName;
	}
	
	@Override
	public void setBranchAddressKana(String branchAddressKana) {
		this.branchAddressKana = branchAddressKana;
	}
	
	@Override
	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
