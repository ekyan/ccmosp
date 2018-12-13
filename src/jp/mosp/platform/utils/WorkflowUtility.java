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
package jp.mosp.platform.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフローに関する有用なメソッドを提供する。<br>
 */
public class WorkflowUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private WorkflowUtility() {
		// 処理無し
	}
	
	/**
	 * 対象ワークフローの承認状況が、申請済であるかを確認する。<br>
	 * 下書、取下、一次戻以外の場合は、申請済であると判断する。<br>
	 * <br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：申請済である、false：そうでない)
	 */
	public static boolean isApplied(WorkflowDtoInterface dto) {
		if (isDraft(dto) || isWithDrawn(dto) || isFirstReverted(dto)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 対象ワークフローが承認可能であるかを確認する。<br>
	 * 次の何れかに当てはまるワークフローが、承認可能と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が差戻で、ワークフローが一次戻でない
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li></ul>
	 * <br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認可能である、false：承認可能でない)
	 */
	public static boolean isApprovable(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		// ワークフロー状況確認(未承認)
		if (isApply(dto)) {
			return true;
		}
		// ワークフロー状況確認(承認)
		if (isApproved(dto)) {
			return true;
		}
		// ワークフロー状況確認(承認解除)
		if (isCancel(dto)) {
			return true;
		}
		// ワークフロー状況確認(差戻で、一次戻でない)
		if (isReverted(dto)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認済であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認済である、false：そうでない)
	 */
	public static boolean isCompleted(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBean.isCompletedと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_COMPLETE);
	}
	
	/**
	 * 対象ワークフローの承認状況が、解除申であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：解除申である、false：そうでない)
	 */
	public static boolean isCancelApply(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_CANCEL_APPLY);
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認解除であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認解除である、false：そうでない)
	 */
	public static boolean isCancel(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_CANCEL);
	}
	
	/**
	 * 対象ワークフローが、差戻(一次戻以外)であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：差戻(一次戻以外)である、false：そうでない)
	 */
	public static boolean isReverted(WorkflowDtoInterface dto) {
		// ワークフロー情報の承認状況(差戻)を確認
		if (isTheStatus(dto, PlatformConst.CODE_STATUS_REVERT) == false) {
			return false;
		}
		// 一次戻であるかを確認
		return isFirstReverted(dto) == false;
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認である、false：そうでない)
	 */
	public static boolean isApproved(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_APPROVED);
	}
	
	/**
	 * 対象ワークフローの承認状況が、未承認であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：未承認である、false：そうでない)
	 */
	public static boolean isApply(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_APPLY);
	}
	
	/**
	 * 対象ワークフローの承認状況が、取下であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：取下である、false：そうでない)
	 */
	public static boolean isWithDrawn(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_WITHDRAWN);
	}
	
	/**
	 * 対象ワークフローの承認状況が、下書であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：下書である、false：そうでない)
	 */
	public static boolean isDraft(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_DRAFT);
	}
	
	/**
	 * 対象ワークフローが、一次戻であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：一次戻である、false：そうでない)
	 */
	public static boolean isFirstReverted(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBean.isFirstRevertedと同じ
		// ワークフロー情報の承認状況(差戻)を確認
		if (isTheStatus(dto, PlatformConst.CODE_STATUS_REVERT) == false) {
			return false;
		}
		// ワークフロー情報の段階(0)を確認
		return dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO;
	}
	
	/**
	 * 対象ワークフローの承認状況が、その承認状況であるかを確認する。<br>
	 * @param dto    対象ワークフロー情報
	 * @param status その承認状況
	 * @return 確認結果(true：その承認状況である、false：そうでない)
	 */
	protected static boolean isTheStatus(WorkflowDtoInterface dto, String status) {
		if (dto == null || dto.getWorkflowStatus() == null) {
			return false;
		}
		return dto.getWorkflowStatus().equals(status);
	}
	
	/**
	 * ワークフローの状態と段階からワークフロー状態(表示用)を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param status     ワークフロー状態
	 * @param stage      ワークフロー段階
	 * @return ワークフロー状態(表示用)
	 */
	public static String getWorkflowStatus(MospParams mospParams, String status, int stage) {
		// TODO WorkflowIntegrateBean.isFirstRevertedと同じ
		// ワークフロー状態確認
		if (PlatformConst.CODE_STATUS_DRAFT.equals(status)) {
			// 下書
			return getNameDraft(mospParams);
		}
		if (PlatformConst.CODE_STATUS_APPLY.equals(status)) {
			// 未承認
			return getNameNotApproved(mospParams);
		}
		if (PlatformConst.CODE_STATUS_CANCEL.equals(status)) {
			// 承解除
			return getNameRelease(mospParams);
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(status)) {
			// 取下
			return getNameWithdraw(mospParams);
		}
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(status)) {
			// 承認済
			return getNameFinish(mospParams);
		}
		if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(status)) {
			// 解除申
			return getNameCancelAppli(mospParams);
		}
		if (PlatformConst.CODE_STATUS_APPROVED.equals(status)) {
			// n次済
			return getNameStageApprove(mospParams, stage - 1);
		}
		if (PlatformConst.CODE_STATUS_REVERT.equals(status)) {
			// n次戻
			return getNameStageRemand(mospParams, stage + 1);
		}
		return status;
	}
	
	/**
	 * 下書名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 下書名称
	 */
	public static String getNameDraft(MospParams mospParams) {
		return mospParams.getName("WorkPaper");
	}
	
	/**
	 * 申請名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 申請名称
	 */
	public static String getNameApply(MospParams mospParams) {
		return mospParams.getName("Application");
	}
	
	/**
	 * 承認名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 承認名称
	 */
	public static String getNameApprove(MospParams mospParams) {
		return mospParams.getName("Approval");
	}
	
	/**
	 * 差戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 差戻名称
	 */
	public static String getNameRemand(MospParams mospParams) {
		return mospParams.getName("SendingBack");
	}
	
	/**
	 * 取下名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 取下名称
	 */
	public static String getNameWithdraw(MospParams mospParams) {
		return mospParams.getName("TakeDown");
	}
	
	/**
	 * 承認解除名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 承認解除名称
	 */
	public static String getNameRelease(MospParams mospParams) {
		return mospParams.getName("ApprovalRelease");
	}
	
	/**
	 * 未承認名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 未承認名称
	 */
	public static String getNameNotApproved(MospParams mospParams) {
		return mospParams.getName("Ram", "Approval");
	}
	
	/**
	 * n次済名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param stage 段階
	 * @return n次済名称
	 */
	public static String getNameStageApprove(MospParams mospParams, int stage) {
		return stage + mospParams.getName("Following", "Finish");
	}
	
	/**
	 * n次戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param stage 段階
	 * @return n次戻名称
	 */
	public static String getNameStageRemand(MospParams mospParams, int stage) {
		return stage + mospParams.getName("Following", "Back");
	}
	
	/**
	 * 承認済名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 承認済名称
	 */
	public static String getNameFinish(MospParams mospParams) {
		return mospParams.getName("Approval", "Finish");
	}
	
	/**
	 * 解除申名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 解除申名称
	 */
	public static String getNameCancelAppli(MospParams mospParams) {
		return mospParams.getName("Release", "Register");
	}
	
}
