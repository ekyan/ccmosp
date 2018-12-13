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
package jp.mosp.platform.bean.human.impl;

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.AccountRegistBeanInterface;
import jp.mosp.platform.dao.human.AccountDaoInterface;
import jp.mosp.platform.dto.human.AccountDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaAccountDto;
import jp.mosp.platform.utils.PlatformNamingUtility;

/**
 * 口座情報登録クラス。<br>
 */
public class AccountRegistBean extends PlatformBean implements AccountRegistBeanInterface {
	
	/**
	 * 項目長(保持者ID)。<br>
	 */
	protected static final int		LEN_HOLDER_ID		= 20;
	
	/**
	 * 項目長(口座区分)。<br>
	 */
	protected static final int		LEN_ACCOUNT_TYPE	= 10;
	
	/**
	 * 項目長(銀行コード)。<br>
	 */
	protected static final int		LEN_BANK_CODE		= 4;
	
	/**
	 * 項目長(支店コード)。<br>
	 */
	protected static final int		LEN_BRANCH_CODE		= 3;
	
	/**
	 * 項目長(口座種別(普通/当座))。<br>
	 */
	protected static final int		LEN_ACCOUNT_CLASS	= 1;
	
	/**
	 * 項目長(口座番号)。<br>
	 */
	protected static final int		LEN_ACCOUNT_NUMBER	= 7;
	
	/**
	 * 項目長(口座名義)。<br>
	 */
	protected static final int		LEN_ACCOUNT_HOLDER	= 30;
	
	/**
	 * 口座情報DAOクラス。<br>
	 */
	protected AccountDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AccountRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public AccountRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOの準備
		dao = (AccountDaoInterface)createDao(AccountDaoInterface.class);
	}
	
	@Override
	public AccountDtoInterface getInitDto() {
		return new PfaAccountDto();
	}
	
	@Override
	public void regist(AccountDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複DTO取得
		AccountDtoInterface duplicateDto = dao.findForKey(dto.getHolderId(), dto.getAccountType(),
				dto.getActivateDate());
		// 重複DTOが存在する場合
		if (duplicateDto != null) {
			// 論理削除
			logicalDelete(dao, duplicateDto.getPfaAccountId());
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaAccountId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void deletePayMainAccount(String holderId, Date activateDate) throws MospException {
		// 口座情報を削除
		deleteAccount(holderId, AccountReferenceBean.TYPE_PAY_MAIN, activateDate);
	}
	
	@Override
	public void deletePaySubAccount(String holderId, Date activateDate) throws MospException {
		// 口座情報を削除
		deleteAccount(holderId, AccountReferenceBean.TYPE_PAY_SUB, activateDate);
	}
	
	/**
	 * 口座情報の削除を行う。<br>
	 * <br>
	 * 保持者ID、有効日及び口座区分が合致する情報を削除する。<br>
	 * 合致する情報が存在しない場合は、何もしない。<br>
	 * <br>
	 * @param holderId     保持者ID
	 * @param accountType  口座区分
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void deleteAccount(String holderId, String accountType, Date activateDate) throws MospException {
		// 口座情報を取得
		AccountDtoInterface dto = dao.findForKey(holderId, accountType, activateDate);
		// 口座情報が存在する場合
		if (dto != null) {
			// 論理削除
			logicalDelete(dao, dto.getPfaAccountId());
		}
	}
	
	@Override
	public void validate(AccountDtoInterface dto, Integer row) {
		// 必須確認(保持者ID)
		checkRequired(dto.getHolderId(), mospParams.getName("Id"), row);
		// 必須確認(口座区分)
		checkRequired(dto.getAccountType(), mospParams.getName("Type"), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PlatformNamingUtility.activateDate(mospParams), row);
		// 桁数確認(保持者ID)
		checkLength(dto.getHolderId(), LEN_HOLDER_ID, mospParams.getName("Id"), row);
		// 桁数確認(銀行コード)
		checkLength(dto.getBankCode(), LEN_BANK_CODE, mospParams.getName("Code"), row);
		// 桁数確認(支店コード)
		checkLength(dto.getBranchCode(), LEN_BRANCH_CODE, mospParams.getName("Code"), row);
		// 桁数確認(口座種別(普通/当座))
		checkLength(dto.getAccountClass(), LEN_ACCOUNT_CLASS, mospParams.getName("Type"), row);
		// 桁数確認(口座番号)
		checkLength(dto.getAccountNumber(), LEN_ACCOUNT_NUMBER, mospParams.getName("Number"), row);
		// 桁数確認(口座名義)
		checkLength(dto.getAccountHolder(), LEN_ACCOUNT_HOLDER, mospParams.getName("Name"), row);
		// 型確認(口座名義)
		checkTypeKana(dto.getAccountHolder(), mospParams.getName("Name"), row);
		// 削除フラグ確認
		checkDeleteFlag(dto.getDeleteFlag(), row);
	}
}
