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
package jp.mosp.platform.bean.system.impl;

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.GeneralRegistBeanInterface;
import jp.mosp.platform.dao.system.GeneralDaoInterface;
import jp.mosp.platform.dto.system.GeneralDtoInterface;
import jp.mosp.platform.dto.system.impl.GeneralDto;

/**
 * 汎用マスタ登録クラス。
 */
public class GeneralRegistBean extends PlatformBean implements GeneralRegistBeanInterface {
	
	/**
	 * 汎用マスタDAOクラス。<br>
	 */
	private GeneralDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public GeneralRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public GeneralRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (GeneralDaoInterface)createDao(GeneralDaoInterface.class);
	}
	
	@Override
	public GeneralDtoInterface getInitDto() {
		return new GeneralDto();
	}
	
	@Override
	public void regist(GeneralDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getGeneralType(), dto.getGeneralCode(), dto.getGeneralDate()) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	@Override
	public void insert(GeneralDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(GeneralDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(GeneralDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfgGeneralId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(GeneralDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getGeneralType(), dto.getGeneralCode(), dto.getGeneralDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(GeneralDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getGeneralType(), dto.getGeneralCode(), dto.getGeneralDate()));
	}
	
	@Override
	public void validate(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void checkDraft(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void checkAppli(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void delete(GeneralDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPfgGeneralId());
	}
	
	@Override
	public void checkApproval(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void checkCancel(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void checkTemporaryClosingFinal(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void delete(String personalId, Date workDate) {
		// 処理なし
	}
	
}
