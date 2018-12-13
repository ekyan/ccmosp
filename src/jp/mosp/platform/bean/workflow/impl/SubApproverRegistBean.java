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
package jp.mosp.platform.bean.workflow.impl;

import java.sql.Connection;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftSubApproverDto;

/**
 * 代理承認者テーブル登録クラス。
 */
public class SubApproverRegistBean extends PlatformHumanBean implements SubApproverRegistBeanInterface {
	
	/**
	 * 代理承認者テーブルDAO。
	 */
	protected SubApproverDaoInterface	dao;
	
	/**
	 * メッセージNoフォーマット。
	 */
	protected static final String		FORMAT_MESSAGE_NO	= "0000000000";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubApproverRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected SubApproverRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		dao = (SubApproverDaoInterface)createDao(SubApproverDaoInterface.class);
	}
	
	@Override
	public SubApproverDtoInterface getInitDto() {
		return new PftSubApproverDto();
	}
	
	@Override
	public void insert(SubApproverDtoInterface dto) throws MospException {
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
		// 代理承認者登録No発行
		dto.setSubApproverNo(issueSequenceNo(dao.getMaxMessageNo(), FORMAT_MESSAGE_NO));
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftSubApproverId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(SubApproverDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPftSubApproverId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftSubApproverId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(SubApproverDtoInterface dto) throws MospException {
		// 代理設定重複確認
		checkSubApproverDuplicate(dto);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkUpdate(SubApproverDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPftSubApproverId());
		// 代理設定重複確認
		checkSubApproverDuplicate(dto);
	}
	
	/**
	 * 代理設定重複確認を行う。<br>
	 * 個人IDで同一期間内に同じワークフロー区分のレコードが2つあってはならない。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubApproverDuplicate(SubApproverDtoInterface dto) throws MospException {
		// 期間内における代理設定情報リストを取得
		List<SubApproverDtoInterface> list = dao.findForTerm(dto.getPersonalId(), dto.getWorkflowType(),
				dto.getStartDate(), dto.getEndDate());
		// 情報の存在を確認
		if (list.size() == 0) {
			return;
		}
		// 自情報確認(履歴更新時の自情報は重複対象外)
		if (list.size() == 1 && list.get(0).getSubApproverNo().equals(dto.getSubApproverNo())) {
			return;
		}
		// エラーメッセージ設定
		addDuplicateTermMessage(getNameSubstitution());
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void validate(SubApproverDtoInterface dto) throws MospException {
		// 開始日及び終了日の前後確認
		if (checkDateOrder(dto.getStartDate(), dto.getEndDate(), true) == false) {
			// 順序が異なる場合のメッセージを追加
			addInvalidOrderMessage(getNameSubstitutionStartDate(), getNameSubstitutionEndDate());
		}
		// THINK 代理人在籍確認
	}
	
	/**
	 * 代理開始日名称を取得する。<br>
	 * @return 代理開始日名称
	 */
	protected String getNameSubstitutionStartDate() {
		return mospParams.getName("Substitution") + mospParams.getName("Start") + mospParams.getName("Day");
	}
	
	/**
	 * 代理終了日名称を取得する。<br>
	 * @return 代理終了日名称
	 */
	protected String getNameSubstitutionEndDate() {
		return mospParams.getName("Substitution") + mospParams.getName("End") + mospParams.getName("Day");
	}
	
	/**
	 * 代理名称を取得する。<br>
	 * @return 代理名称
	 */
	protected String getNameSubstitution() {
		return mospParams.getName("Substitution");
	}
	
}
