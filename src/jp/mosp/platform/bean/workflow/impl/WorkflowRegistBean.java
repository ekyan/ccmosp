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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftWorkflowDto;
import jp.mosp.platform.utils.PlatformMessageUtility;

/**
 * ワークフロー登録クラス。
 */
public class WorkflowRegistBean extends PlatformBean implements WorkflowRegistBeanInterface {
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface						dao;
	
	/**
	 * 承認ルート参照クラス。
	 */
	protected ApprovalRouteReferenceBeanInterface		routeReference;
	
	/**
	 * 承認ルート適用参照クラス。
	 */
	protected RouteApplicationReferenceBeanInterface	routeAppReference;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface		workflowCommentRegist;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public WorkflowRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// クラス準備
		dao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		routeReference = (ApprovalRouteReferenceBeanInterface)createBean(ApprovalRouteReferenceBeanInterface.class);
		routeAppReference = (RouteApplicationReferenceBeanInterface)createBean(RouteApplicationReferenceBeanInterface.class);
		workflowCommentRegist = (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
	}
	
	@Override
	public WorkflowDtoInterface getInitDto() {
		// DTO準備
		WorkflowDtoInterface dto = new PftWorkflowDto();
		// 初期化
		dto.setWorkflowStatus("");
		dto.setRouteCode("");
		dto.setApproverId("");
		dto.setFunctionCode("");
		return dto;
	}
	
	@Override
	public WorkflowDtoInterface draft(WorkflowDtoInterface dto, String personalId, Date targetDate, int workflowType)
			throws MospException {
		// 個人ID及び対象日設定
		dto.setPersonalId(personalId);
		dto.setWorkflowDate(targetDate);
		// 登録情報妥当性確認
		validate(dto);
		// 下書可否確認
		checkDraft(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号確認
		if (dto.getWorkflow() > 0) {
			// ワークフロー番号が割り当てられている場合(変更無し)
			return dto;
		}
		// 下書状態に変更(段階は0)
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_DRAFT);
		dto.setWorkflowStage(PlatformConst.WORKFLOW_STAGE_ZERO);
		// ワークフロー情報新規登録
		return insert(dto);
	}
	
	@Override
	public WorkflowDtoInterface withdrawn(WorkflowDtoInterface dto) throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 取下の確認
		checkWithdrawn(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 取下へ変更
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_WITHDRAWN);
		return add(dto);
	}
	
	@Override
	public WorkflowDtoInterface appli(WorkflowDtoInterface dto, String personalId, Date targetDate, int workflowType,
			String workflowComment) throws MospException {
		// 個人ID及び対象日設定
		dto.setPersonalId(personalId);
		dto.setWorkflowDate(targetDate);
		// 登録情報妥当性確認
		validate(dto);
		// 申請可否確認
		checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 承認ルート設定
		setApprovalRoute(dto, personalId, targetDate, workflowType);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 自己承認確認
		if (isSelfApproval(dto)) {
			// 完了状態に変更(段階は0)(自己承認の場合)
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_COMPLETE);
			dto.setWorkflowStage(PlatformConst.WORKFLOW_STAGE_ZERO);
		} else {
			// 申請状態(未承認)に変更(段階は1)
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_APPLY);
			dto.setWorkflowStage(PlatformConst.WORKFLOW_STAGE_FIRST);
		}
		// 戻値用ワークフロー準備
		WorkflowDtoInterface workflowDto = null;
		// ワークフロー番号確認
		if (dto.getWorkflow() == 0) {
			// 新規申請
			workflowDto = insert(dto);
		} else {
			// 履歴追加(ワークフロー番号が割り当てられている場合)
			workflowDto = add(dto);
		}
		// ワークフロー情報確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 登録ワークフローコメント準備
		String comment = workflowComment;
		// ワークフローコメント確認
		if (workflowComment == null || workflowComment.isEmpty()) {
			// 自己承認確認
			if (isSelfApproval(dto)) {
				// デフォルト承認ワークフローコメント取得
				comment = getDefaultApproveComment();
			} else {
				// デフォルト申請ワークフローコメント取得
				comment = getDefaultApplyComment();
			}
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, comment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface cancelAppli(WorkflowDtoInterface dto, String workflowComment) throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 申請可否確認
		checkCancelAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 承認解除申請へ変更
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_CANCEL_APPLY);
		// 自己承認確認
		if (isSelfApproval(dto)) {
			// 差戻へ変更
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_REVERT);
		}
		// 戻値用ワークフロー準備
		WorkflowDtoInterface workflowDto = add(dto);
		// ワークフロー情報確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, workflowComment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface approve(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException {
		// 承認処理
		return approveWorkflow(dto, workflowType, workflowComment);
	}
	
	/**
	 * 承認を行う。<br>
	 * @param dto             対象DTO
	 * @param workflowType    フロー区分
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected WorkflowDtoInterface approveWorkflow(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 承認の確認
		checkApproval(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象ワークフローの段階を取得
		int workflowStage = dto.getWorkflowStage();
		// 最終承認確認
		if (workflowStage == getWorkflowApprovalCount(dto)) {
			// 完了(最終承認済)
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_COMPLETE);
		} else {
			// 対象ワークフローの段階をインクリメントし状態を更新(承認済)
			dto.setWorkflowStage(++workflowStage);
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_APPROVED);
		}
		// 承認処理
		WorkflowDtoInterface workflowDto = add(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 登録ワークフローコメント準備
		String comment = workflowComment;
		// ワークフローコメント確認
		if (workflowComment == null || workflowComment.isEmpty()) {
			// デフォルト承認ワークフローコメント取得
			comment = getDefaultApproveComment();
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, comment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface revert(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 差戻の確認
		checkRevert(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象ワークフローの段階を取得
		int workflowStage = dto.getWorkflowStage();
		// 対象ワークフローの段階をデクリメントし状態を更新(差戻)
		dto.setWorkflowStage(--workflowStage);
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_REVERT);
		// 差戻処理
		WorkflowDtoInterface workflowDto = add(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, workflowComment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface cancelRevert(WorkflowDtoInterface dto, String workflowComment) throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 解除承認差戻の確認
		checkCancelRevert(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象ワークフローの段階を取得
		int workflowStage = dto.getWorkflowStage();
		// 最終承認確認
		if (workflowStage == getWorkflowApprovalCount(dto)) {
			// 完了(最終承認済)
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_COMPLETE);
		} else {
			// 対象ワークフローの段階をインクリメントし状態を更新(承認済)
			dto.setWorkflowStage(++workflowStage);
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_APPROVED);
		}
		// 解除承認差戻処理
		WorkflowDtoInterface workflowDto = add(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 登録ワークフローコメント準備
		String comment = workflowComment;
		// ワークフローコメント確認
		if (workflowComment == null || workflowComment.isEmpty()) {
			// デフォルト承認ワークフローコメント取得
			comment = getDefaultApproveComment();
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, comment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface cancel(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 承認解除の確認
		checkCancel(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー段階取得
		int workflowStage = dto.getWorkflowStage();
		dto.setWorkflowStage(workflowStage);
		// 承認解除へ変更
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_CANCEL);
		// 自己承認確認
		if (dto.getApproverId().equals(PlatformConst.APPROVAL_ROUTE_SELF)
				|| dto.getRouteCode().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			// 差戻へ変更
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_REVERT);
		}
		// 承認解除処理
		WorkflowDtoInterface workflowDto = add(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフローコメント設定
		// 登録ワークフローコメント準備
		String comment = workflowComment;
		// ワークフローコメント確認
		if (workflowComment == null || workflowComment.isEmpty()) {
			// デフォルト承認解除ワークフローコメント取得
			comment = getDefaultCancelComment();
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, comment);
		return workflowDto;
	}
	
	@Override
	public WorkflowDtoInterface cancelApprove(WorkflowDtoInterface dto, String workflowComment) throws MospException {
		// 登録情報妥当性確認
		validate(dto);
		// 承認解除の確認
		checkCancelApproval(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 承認解除へ変更
		dto.setWorkflowStatus(PlatformConst.CODE_STATUS_CANCEL);
		// 自己承認確認
		if (isSelfApproval(dto)) {
			// 差戻へ変更
			dto.setWorkflowStatus(PlatformConst.CODE_STATUS_REVERT);
		}
		// 承認解除処理
		WorkflowDtoInterface workflowDto = add(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフローコメント設定
		// 登録ワークフローコメント準備
		String comment = workflowComment;
		// ワークフローコメント確認
		if (workflowComment == null || workflowComment.isEmpty()) {
			// デフォルト承認解除ワークフローコメント取得
			comment = getDefaultCancelComment();
		}
		// ワークフローコメント設定
		addWorkflowComment(workflowDto, comment);
		return workflowDto;
	}
	
	/**
	 * 新規登録を行う。<br>
	 * 引数のDTOにワークフロー番号を設定し、かえす。<br>
	 * ただし、レコード識別IDはセットされていない。<br>
	 * ワークフロー操作に失敗した場合nullをかえす。<br>
	 * @param dto 対象DTO
	 * @return 登録後ワークフロー番号
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected WorkflowDtoInterface insert(WorkflowDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// テーブルの中で一番新しいワークフロー番号を取得
		long workflow = dao.nextWorkflow();
		// DTOにセットする。
		dto.setWorkflow(workflow);
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftWorkflowId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		// ワークフロー番号を設定したDTOを取得
		return dto;
	}
	
	/**
	 * 履歴追加を行う。<br>
	 * 引数のDTOにワークフロー番号を設定し、かえす。<br>
	 * ただし、レコード識別IDはセットされていない。<br>
	 * ワークフロー操作に失敗した場合nullをかえす。<br>
	 * @param dto      対象DTO
	 * @return 更新後ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected WorkflowDtoInterface add(WorkflowDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 履歴更新情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 論理削除
		logicalDelete(dao, dto.getPftWorkflowId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftWorkflowId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		// ワークフロー番号を設定したDTOを取得
		return dto;
	}
	
	/**
	 * 登録情報の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 */
	protected void validate(WorkflowDtoInterface dto) {
		// 機能コード必須確認
		if (dto.getFunctionCode() == null || dto.getFunctionCode().isEmpty()) {
			addInputDataInvalidErrorMessage();
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkflowDtoInterface dto) throws MospException {
		// 新規登録ワークフロー番号の履歴が存在しないことを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkflow()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkflowDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPftWorkflowId());
	}
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkDraft(WorkflowDtoInterface dto) {
		// ワークフロー状態が設定されていない場合
		if (dto.getWorkflowStatus().isEmpty()) {
			return;
		}
		// ワークフロー状態が下書の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)) {
			return;
		}
//		// ワークフロー状態が差戻で段階が0の場合
//		if (dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO
//				&& dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
//			return;
//		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkWithdrawn(WorkflowDtoInterface dto) {
		// ワークフロー状態が未承認の場合
		if (PlatformConst.CODE_STATUS_APPLY.equals(dto.getWorkflowStatus())) {
			return;
		}
//		// ワークフロー状態が下書の場合
//		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)) {
//			return;
//		}
		// ワークフロー状態が差戻で段階が0の場合
		if (dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO
				&& dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkAppli(WorkflowDtoInterface dto) {
		// ワークフロー状態が設定されていない場合
		if (dto.getWorkflowStatus().isEmpty()) {
			return;
		}
		// ワークフロー状態が下書の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)) {
			return;
		}
		// ワークフロー状態が差戻で段階が0の場合
		if (dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO
				&& dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkApproval(WorkflowDtoInterface dto) {
		// ワークフロー段階が0の場合
		if (dto.getWorkflowStage() == 0) {
			// エラーメッセージ設定
			addWorkflowProcessFailedErrorMessage();
			return;
		}
		// ワークフロー状態が申請(未承認)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPLY)) {
			return;
		}
		// ワークフロー状態が承認済(未完了)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPROVED)) {
			return;
		}
		// ワークフロー状態が差戻の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			return;
		}
		// ワークフロー状態が取消の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkCancelApproval(WorkflowDtoInterface dto) {
		// ワークフローが解除申の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_APPLY)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 差戻時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkRevert(WorkflowDtoInterface dto) {
		// ワークフロー段階が0の場合
		if (dto.getWorkflowStage() == 0) {
			// エラーメッセージ設定
			addWorkflowProcessFailedErrorMessage();
			return;
		}
		// ワークフロー状態が申請(未承認)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPLY)) {
			return;
		}
		// ワークフロー状態が承認済(未完了)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPROVED)) {
			return;
		}
		// ワークフロー状態が承認解除の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL)) {
			return;
		}
		// ワークフロー状態が差戻の場合(段階0でない)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 解除申請差戻時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkCancelRevert(WorkflowDtoInterface dto) {
		// ワークフロー状態が解除申の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_APPLY)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 取消時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkCancel(WorkflowDtoInterface dto) throws MospException {
		// ワークフロー状態が完了(最終承認済)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 承認解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkCancelAppli(WorkflowDtoInterface dto) throws MospException {
		// ワークフロー状態が完了(最終承認済)の場合
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			return;
		}
		// エラーメッセージ設定
		addWorkflowProcessFailedErrorMessage();
	}
	
	/**
	 * 承認者ID配列をカンマ区切の文字列に変換し、DTOに設定する。<br>
	 */
	@Override
	public void setDtoApproverIds(WorkflowDtoInterface dto, String[] aryApproverId) {
		// 承認者ID確認
		if (aryApproverId == null) {
			dto.setApproverId("");
			return;
		}
		// 承認者ID設定(カンマ区切)
		dto.setApproverId(MospUtility.toSeparatedString(aryApproverId, SEPARATOR_DATA));
		// ルートコード設定(空白)
		dto.setRouteCode("");
	}
	
	/**
	 * ワークフローの承認階層を取得する。<br>
	 * ルートコード或いは承認者IDからワークフローの承認階層を取得する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return ワークフローの承認階層
	 * @throws MospException ルート情報の取得に失敗した場合
	 */
	protected int getWorkflowApprovalCount(WorkflowDtoInterface dto) throws MospException {
		// 承認者ID確認
		if (dto.getApproverId().isEmpty() == false) {
			// 承認者IDを配列で取得
			String[] approvers = split(dto.getApproverId(), SEPARATOR_DATA);
			return approvers.length;
		}
		// ルート情報取得
		ApprovalRouteDtoInterface route = routeReference
			.getApprovalRouteInfo(dto.getRouteCode(), dto.getWorkflowDate());
		if (route == null) {
			return 0;
		}
		return route.getApprovalCount();
	}
	
	/**
	 * ワークフローが自己承認であるかを確認する。<br>
	 * @param dto 確認対象ワークフロー情報
	 * @return 自己承認確認結果(true：自己承認、false：自己承認でない)
	 */
	protected boolean isSelfApproval(WorkflowDtoInterface dto) {
		// 承認者ID確認
		if (dto.getApproverId().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			return true;
		}
		// ルートコード確認
		if (dto.getRouteCode().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			return true;
		}
		return false;
	}
	
	/**
	 * ルート適用情報から承認ルートを取得し、ワークフロー情報に設定する。<br>
	 * 設定対象ワークフローに承認者個人IDが設定されている場合は、
	 * ルートコードを設定しない(承認者個人IDが承認ルートとなる)。<br>
	 * 承認ルートが既に設定されている場合は、何もしない。<br>
	 * @param dto          設定対象ワークフロー情報
	 * @param personalId   申請者個人ID
	 * @param targetDate   申請対象日
	 * @param workflowType フロー区分
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void setApprovalRoute(WorkflowDtoInterface dto, String personalId, Date targetDate, int workflowType)
			throws MospException {
		// ワークフロー承認者ID確認
		if (dto.getApproverId().isEmpty() == false) {
			return;
		}
		// ルート適用情報取得及び確認
		RouteApplicationDtoInterface routeApp = routeAppReference.findForPerson(personalId, targetDate, workflowType);
		if (routeApp == null) {
			// メッセージ設定
			addNoApprovalRouteErrorMessage();
			return;
		}
		// ルートコード設定
		dto.setRouteCode(routeApp.getRouteCode());
	}
	
	@Override
	public void delete(WorkflowDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPftWorkflowId());
	}
	
	/**
	 * ワークフローコメントを追加する。<br>
	 * @param dto             設定対象ワークフロー情報
	 * @param workflowComment ワークフローコメント
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void addWorkflowComment(WorkflowDtoInterface dto, String workflowComment) throws MospException {
		// ワークフローコメント登録
		workflowCommentRegist.addComment(dto, mospParams.getUser().getPersonalId(), workflowComment);
	}
	
	/**
	 * デフォルト申請ワークフローコメントを取得する。<br>
	 * @return デフォルト申請ワークフローコメント
	 */
	protected String getDefaultApplyComment() {
		return mospParams.getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED, mospParams.getName("Application"));
	}
	
	/**
	 * デフォルト承認ワークフローコメントを取得する。<br>
	 * @return デフォルト承認ワークフローコメント
	 */
	protected String getDefaultApproveComment() {
		return mospParams.getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED, mospParams.getName("Approval"));
	}
	
	/**
	 * デフォルト承認解除ワークフローコメントを取得する。<br>
	 * @return デフォルト承認解除ワークフローコメント
	 */
	protected String getDefaultCancelComment() {
		return mospParams.getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED,
				mospParams.getName("Approval", "Release"));
	}
	
	/**
	 * 承認ルートの取得に失敗した場合のエラーメッセージを設定する。<br>
	 */
	protected void addNoApprovalRouteErrorMessage() {
		String message = mospParams.getName("Approval", "Route");
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, message);
	}
	
	/**
	 * ワークフロー処理に失敗した場合のエラーメッセージを設定する。<br>
	 */
	protected void addWorkflowProcessFailedErrorMessage() {
		PlatformMessageUtility.addErrorWorkflowProcessFailed(mospParams);
	}
	
	@Override
	public void approval(long[] aryId, String[] aryPersonalId, Date[] aryRequestDate, int workflowType,
			String workflowComment) throws MospException {
		// 処理ワークフロー情報リスト準備
		List<WorkflowDtoInterface> workflowList = new ArrayList<WorkflowDtoInterface>();
		// ワークフロー毎に承認
		for (long element : aryId) {
			// DTOの準備
			WorkflowDtoInterface dto = (WorkflowDtoInterface)findForKey(dao, element, false);
			// 承認処理(当メソッドで取得するためロックは不要)
			approveWorkflow(dto, workflowType, workflowComment);
			// 処理ワークフロー情報リストへ追加
			if (dto != null) {
				workflowList.add(dto);
			}
		}
	}
}
