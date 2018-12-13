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

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請登録インターフェース。<br>
 */
public interface HolidayRequestRegistBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	HolidayRequestDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @return 午前休又は午後休が含まれる場合はtrue、そうでない場合はfalse
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	@Deprecated
	boolean update(long[] idArray) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括取下処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void withdrawn(long[] idArray) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請日設定時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkSetRequestDate(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	void checkDraft(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkAppli(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkCancelAppli(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkWithdrawn(HolidayRequestDtoInterface dto);
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkApproval(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkCancelApproval(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取消時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkCancel(HolidayRequestDtoInterface dto);
	
	/**
	 * 申請時の入力チェック。休暇申請の重複チェック。<br>
	 * <p>
	 * 同日中に同じ残業区分が申請されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Deprecated
	void checkHolidayOverlap(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。他の申請チェック。<br>
	 * <p>
	 * 申請年月日に他の申請日が行われている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Deprecated
	void checkRequest(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 有効日設定時の入力チェック。他の申請チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Deprecated
	void checkRequestForSetActivationDate(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。勤怠の申請チェック。<br>
	 * <p>
	 * 勤怠が申請されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Deprecated
	void checkAttendance(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。取得期限チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkLimitDate(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 勤務形態チェック。
	 * @param dto 対象DTO
	 * @param targetDate 対象日
	 * @param workTypeCode 勤務形態コード
	 */
	void checkWorkType(HolidayRequestDtoInterface dto, Date targetDate, String workTypeCode);
	
	/**
	 * 勤務形態チェック。
	 * @param startDate 休暇開始日
	 * @param endDate 休暇終了日
	 * @param targetDate 対象日
	 * @param workTypeCode 勤務形態コード
	 */
	void checkWorkType(Date startDate, Date endDate, Date targetDate, String workTypeCode);
	
	/**
	 * 法定休日かどうか確認。
	 * @param workTypeCode 勤務形態コード
	 * @return 法定休日であればtrue、法定休日でなければfalse
	 */
	boolean isLegalDaysOff(String workTypeCode);
	
	/**
	 * 所定休日かどうか確認。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 所定休日であればtrue、所定休日でなければfalse
	 */
	boolean isPrescribedDaysOff(String workTypeCode);
	
	/**
	 * 法定休日労働かどうか確認。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 法定休日労働であればtrue、法定休日労働でなければfalse
	 */
	boolean isWorkOnLegalDaysOff(String workTypeCode);
	
	/**
	 * 所定休日労働かどうか確認。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 所定休日労働であればtrue、所定休日労働でなければfalse
	 */
	boolean isWorkOnPrescribedDaysOff(String workTypeCode);
	
	/**
	 * カレンダ勤務形態コードを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 休暇申請日数を取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate 休暇開始日
	 * @param endDate 休暇終了日
	 * @param holidayRange 休暇範囲
	 * @param holidayDto 休暇マスタDTO
	 * @param holidayDataDto 休暇データDTO
	 * @return 休暇申請日数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	double getHolidayRequestDays(String personalId, Date startDate, Date endDate, int holidayRange,
			HolidayDtoInterface holidayDto, HolidayDataDtoInterface holidayDataDto) throws MospException;
	
	/**
	 * 勤怠データを削除する。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void deleteAttendance(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠下書を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void draftAttendance(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * <p>
	 * 仮締されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkTemporaryClosingFinal(HolidayRequestDtoInterface dto) throws MospException;
	
}
