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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 休日出勤申請登録インターフェース。<br>
 */
public interface WorkOnHolidayRequestRegistBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	WorkOnHolidayRequestDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @return ワークフローリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> update(long[] idArray) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括取下処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void withdrawn(long[] idArray) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請日設定時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkSetRequestDate(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkDraft(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkCancelAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkWithdrawn(WorkOnHolidayRequestDtoInterface dto);
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkApproval(WorkOnHolidayRequestDtoInterface dto);
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkCancelApproval(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取消時の確認処理を行う。<br>
	 * 振替出勤申請を更に振替出勤申請した際、元の振替出勤申請を承認解除できないよう確認。
	 * 更に振替した承認状態が取下、下書、差戻以外の場合、
	 * エラーメッセージを出力し、承認解除しない。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkCancel(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。休日出勤申請の重複チェック。<br>
	 * <p>
	 * 同日に休日出勤申請がされている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkWorkOnHolidayOverlap(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 休日チェック。<br>
	 * <p>
	 * 法定休日でなく且つ所定休日でない場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkHolidayDate(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。休日出勤申請の項目の必須チェック。<br>
	 * <p>
	 * 必須の項目が入力されていない場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 */
	void checkRequired(WorkOnHolidayRequestDtoInterface dto);
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * <p>
	 * 仮締されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkTemporaryClosingFinal(WorkOnHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * カレンダ勤務形態コードを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 出勤日の入力チェック。<br>
	 * @param dto 対象DTO
	 */
	public void checkValidate(WorkOnHolidayRequestDtoInterface dto);
	
}
