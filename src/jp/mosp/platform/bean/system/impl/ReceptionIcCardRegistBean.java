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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.ReceptionIcCardRegistBeanInterface;
import jp.mosp.platform.dao.system.ReceptionIcCardDaoInterface;
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;
import jp.mosp.platform.dto.system.impl.PftReceptionIcCardDto;

/**
 * カードID受付登録クラス。
 */
public class ReceptionIcCardRegistBean extends PlatformBean implements ReceptionIcCardRegistBeanInterface {
	
	/**
	 * カードID受付DAO。
	 */
	ReceptionIcCardDaoInterface	dao;
	
	/**
	 * 受付時(ICカード新規登録時)のメッセージコード。<br>
	 */
	public static final String	MSG_REGIST_IC_CARD	= "ADTR001";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ReceptionIcCardRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected ReceptionIcCardRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (ReceptionIcCardDaoInterface)createDao(ReceptionIcCardDaoInterface.class);
	}
	
	@Override
	public ReceptionIcCardDtoInterface getInitDto() {
		return new PftReceptionIcCardDto();
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象雇用契約を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ReceptionIcCardDtoInterface dto) throws MospException {
		// TODO
	}
	
	@Override
	public void insert(ReceptionIcCardDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftReceptionIcCardId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(ReceptionIcCardDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPftReceptionIcCardId());
	}
	
	/**
	 * 新規登録の検証
	 * @param dto 対象DTO
	 */
	private void checkInsert(ReceptionIcCardDtoInterface dto) {
		// TODO
	}
	
	/**
	 * 登録の妥当性確認。
	 * ICカードIDが既に登録されている場合、受付番号を表示する。
	 */
	@Override
	public void validate(ReceptionIcCardDtoInterface dto, Integer row) throws MospException {
		// 受付番号情報を取得
		ReceptionIcCardDtoInterface infoDto = dao.findForIcCardId(dto.getIcCardId());
		if (infoDto == null) {
			return;
		}
		mospParams.addErrorMessage(MSG_REGIST_IC_CARD, String.valueOf(infoDto.getPftReceptionIcCardId()));
	}
	
}
