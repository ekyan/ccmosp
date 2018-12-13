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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤務形態エンティティインターフェース。<br>
 */
public interface RequestEntityInterface {
	
	/**
	 * 個人IDの設定。<br>
	 * <br>
	 * @param personalId セットする個人ID
	 */
	public void setPersonalId(String personalId);
	
	/**
	 * 対象日の設定。<br>
	 * <br>
	 * @param targetDate セットする対象日
	 */
	public void setTargetDate(Date targetDate);
	
	/**
	 * 勤怠データが存在するかを確認する。<br>
	 * <br>
	 * 承認状況が下書以上の勤怠データが存在すれば、trueを返す。<br>
	 * (勤怠データには取下状態がない。)
	 * <br>
	 * @return 確認結果(true：勤怠データが存在する、false：存在しない)
	 */
	public boolean hasAttendance();
	
	/**
	 * 勤怠申請がされているかを確認する。<br>
	 * <br>
	 * {@link WorkflowUtility#isApplied(WorkflowDtoInterface)}
	 * で確認する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠申請がされていいる、false：勤怠申請がされていない)
	 */
	public boolean isAttendanceApplied();
	
	/**
	 * 勤怠データに直行が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直行が設定されている、false：されていない)
	 */
	public boolean isAttendanceDirectStart();
	
	/**
	 * 勤怠データに直帰が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直帰が設定されている、false：されていない)
	 */
	public boolean isAttendanceDirectEnd();
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * カレンダで登録されている勤務形態及び各種申請情報から、取るべき勤務形態を割り出す。
	 * <br>
	 * 各種申請につき、ワークフローが下書、1次戻、取下の申請は、考慮に入れない。<br>
	 * 但し、勤怠申請については、下書、1次戻を考慮に入れる。<br>
	 * <br>
	 * 勤務形態は、次の方法で決定する。<br>
	 * <br>
	 * 1.勤怠申請が存在する場合：<br>
	 * 勤怠申請に設定されている勤務形態を返す。<br>
	 * <br>
	 * 2.全休の場合：<br>
	 * 空文字を返す。<br>
	 * 但し、全休の振替休日情報がある場合は、その振替種別を返す。<br>
	 * <br>
	 * 3.勤務形態変更申請がある場合
	 * 変更勤務形態(勤務形態変更申請の勤務形態)を返す。<br>
	 * 振替出勤申請の場合に勤務形態変更申請が可能なため、
	 * 振出・休出申請の勤務形態を取得する前に確認をする。<br>
	 * <br>
	 * 4.振出・休出申請がある場合：<br>
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 5.それ以外の場合
	 * 予定勤務形態コード(カレンダで登録されている勤務形態)を返す。<br>
	 * <br>
	 * @param isAttendanceConsidered 勤怠申請考慮フラグ(true：考慮する、false：しない)
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	public String getWorkType(boolean isAttendanceConsidered);
	
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * {@link RequestEntity#getWorkType(boolean) }参照。<br>
	 * <br>
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	public String getWorkType();
	
	/**
	 * 勤務日であるかを確認する。<br>
	 * <br>
	 * カレンダで登録されている勤務形態及び各種申請情報から、確認する。
	 * <br>
	 * 各種申請につき、ワークフローが下書、1次戻、取下の申請は、考慮に入れない。<br>
	 * <br>
	 * 勤務日であるかは、次の方法で確認する。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * 勤務日でないと判断。<br>
	 * <br>
	 * 2.振出・休出申請がある場合：<br>
	 * 勤務日であると判断。<br>
	 * <br>
	 * 3.予定勤務形態コードが法定休日か所定休日である場合：<br>
	 * 勤務日でないと判断。<br>
	 * <br>
	 * 4.それ以外の場合：<br>
	 * 勤務日であると判断。<br>
	 * <br>
	 * @return 確認結果(true：勤務日である、false：勤務日でない)
	 */
	public boolean isWorkDay();
	
	/**
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、空文字を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請の勤務形態
	 */
	public String getWorkOnHolidayWorkType(boolean isCompleted);
	
	/**
	 * 振出・休出申請(休日出勤)の出勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休日出勤)の出勤予定時刻
	 */
	public Date getWorkOnHolidayStartTime(boolean isCompleted);
	
	/**
	 * 振出・休出申請(休日出勤)の退勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の退勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休日出勤)の退勤予定時刻
	 */
	public Date getWorkOnHolidayEndTime(boolean isCompleted);
	
	/**
	 * 残業申請(勤務前残業)の申請時間(分)を取得する。
	 * <br>
	 * 残業申請(勤務前残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務前残業)の申請時間(分)
	 */
	public int getOvertimeMinutesBeforeWork(boolean isCompleted);
	
	/**
	 * 残業申請(勤務後残業)の申請時間(分)を取得する。
	 * <br>
	 * 残業申請(勤務後残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務後残業)の申請時間(分)
	 */
	public int getOvertimeMinutesAfterWork(boolean isCompleted);
	
	/**
	 * 対象日が全休であるかを確認する。<br>
	 * 全休、或いは前半休+後半休であれば、全休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：全休である、false：そうでない)
	 */
	public boolean isAllHoliday(boolean isCompleted);
	
	/**
	 * 対象日が前半休であるかを確認する。<br>
	 * 全休がなく、後半休がなく、前半休があれば、前半休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：前半休である、false：そうでない)
	 */
	public boolean isAmHoliday(boolean isCompleted);
	
	/**
	 * 対象日が後半休であるかを確認する。<br>
	 * 全休がなく、前半休がなく、後半休があれば、後半休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：後半休である、false：そうでない)
	 */
	public boolean isPmHoliday(boolean isCompleted);
	
	/**
	 * 法定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：法定休日出勤である、false：そうでない)
	 */
	public boolean isWorkOnLegal(boolean isCompleted);
	
	/**
	 * 所定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：所定休日出勤である、false：そうでない)
	 */
	public boolean isWorkOnPrescribed(boolean isCompleted);
	
	/**
	 * 振替出勤であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤である、false：そうでない)
	 */
	public boolean isWorkOnHolidaySubstitute(boolean isCompleted);
	
	/**
	 * 休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：休日出勤(振替申請しない)である、false：そうでない)
	 */
	public boolean isWorkOnHolidayNotSubstitute(boolean isCompleted);
	
	/**
	 * 初回連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	public List<Date> getHourlyHolidayFirstSequenceTimes();
	
	/**
	 * 初回連続時間休時刻(開始時刻(分)及び終了時刻(分))を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public List<Integer> getHourlyHolidayFirstSequenceMinutes() throws MospException;
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	public List<Date> getHourlyHolidayLastSequenceTimes();
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public List<Integer> getHourlyHolidayLastSequenceMinutes() throws MospException;
	
	/**
	 * 連続時間休時刻(開始時刻及び終了時刻)を対象日からの分数として取得する。<br>
	 * <br>
	 * @param holidayTimeList 連続時間休時刻(開始時刻及び終了時刻)
	 * @return 連続時間休時刻(開始時刻及び終了時刻)(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public List<Integer> getHourlyHolidaySequenceMinutes(List<Date> holidayTimeList) throws MospException;
	
	/**
	 * 半日振休＋半日振休があるか判断する。
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振休＋半日振休、false：そうでない)
	 */
	public boolean isAmPmHalfSubstitute(boolean isCompleted);
	
	/**
	 * 半日振替の振替かどうか判断する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振替の振替、false：そうでない)
	 */
	public boolean isHalfPostpone(boolean isCompleted);
	
	/**
	 * 勤怠の申請が可能か確認する。<br>
	 * 次の条件を満たす時、勤怠の申請が可能と判断する。<br>
	 * ・勤怠が未申請である。<br>
	 * ・申請済であり未承認の残業申請がない。<br>
	 * ・
	 * @return 確認結果(true：勤怠申請可能、false：勤怠申請不可)
	 */
	public boolean isAttendanceAppliable();
	
	/**
	 * 残業申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isOvertimeApplied(boolean isContainCompleted);
	
	/**
	 * 休暇申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 代休申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isSubHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 休日出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isWorkOnHolidayHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 振替休日申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isSubstituteApplied(boolean isContainCompleted);
	
	/**
	 * 時差出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isDifferenceApplied(boolean isContainCompleted);
	
	/**
	 * 勤務形態変更申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	public boolean isWorkTypeChangeApplied(boolean isContainCompleted);
	
	/**
	 * 残業申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休暇申請情報リスト
	 */
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList(boolean isCompleted);
	
	/**
	 * @return personalId
	 */
	public String getPersonalId();
	
	/**
	 * @return targetDate
	 */
	public Date getTargetDate();
	
	/**
	 * @param holidayRequestList セットする holidayRequestList
	 */
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * @param overtimeRequestList セットする overTimeRequestList
	 */
	public void setOverTimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList);
	
	/**
	 * @param substituteList セットする substituteList
	 */
	public void setSubstituteList(List<SubstituteDtoInterface> substituteList);
	
	/**
	 * @param subHolidayRequestList セットする subHolidayRequestList
	 */
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList);
	
	/**
	 * @param workOnHolidayRequestDto セットする workOnHolidayRequestDto
	 */
	public void setWorkOnHolidayRequestDto(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto);
	
	/**
	 * 時差出勤申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 時差出勤申請情報
	 */
	public DifferenceRequestDtoInterface getDifferenceRequestDto(boolean isCompleted);
	
	/**
	 * @param differenceRequestDto セットする differenceRequestDto
	 */
	public void setDifferenceRequestDto(DifferenceRequestDtoInterface differenceRequestDto);
	
	/**
	 * @param workTypeChangeRequestDto セットする workTypeChangeRequestDto
	 */
	public void setWorkTypeChangeRequestDto(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto);
	
	/**
	 * @return attendanceDto
	 */
	public AttendanceDtoInterface getAttendanceDto();
	
	/**
	 * @param attendanceDto セットする attendanceDto
	 */
	public void setAttendanceDto(AttendanceDtoInterface attendanceDto);
	
	/**
	 * @param workflowMap セットする workflowMap
	 */
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap);
	
	/**
	 * @param scheduledWorkTypeCode セットする scheduledWorkTypeCode
	 */
	public void setScheduledWorkTypeCode(String scheduledWorkTypeCode);
	
	/**
	 * @param substitutedWorkTypeCode セットする substitutedWorkTypeCode
	 */
	public void setSubstitutedWorkTypeCode(String substitutedWorkTypeCode);
	
}
