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
import java.util.Set;

import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤怠集計情報インターフェース
 */
public interface TotalTimeEntityInterface {
	
	/**
	 * 勤怠集計を行う。<br>
	 * <br>
	 * 設定された情報を基に勤怠集計を行う。<br>
	 * 集計値はフィールドに設定される。<br>
	 * <br>
	 */
	void total();
	
	/**
	 * @return 計算対象年
	 */
	int getCalculationYear();
	
	/**
	 * @param calculationYear 計算対象年
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @return 計算対象月
	 */
	int getCalculationMonth();
	
	/**
	 * @param calculationMonth 計算対象月
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @param holidaySet 休暇種別情報群
	 */
	void setHolidaySet(Set<HolidayDtoInterface> holidaySet);
	
	/**
	 * @return 締日コード
	 */
	String getCutoffCode();
	
	/**
	 * @param cutoffCode 締日コード
	 */
	void setCutoffCode(String cutoffCode);
	
	/**------------- 個別情報 -------------**/
	
	/**
	 * @return personalId
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする personalId
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return 締期間初日
	 */
	Date getCutoffFirstDate();
	
	/**
	 * @param cutoffFirstDate 締期間初日
	 */
	void setCutoffFirstDate(Date cutoffFirstDate);
	
	/**
	 * @return 締期間最終日
	 */
	Date getCutoffLastDate();
	
	/**
	 * @param cutoffLastDate 締期間最終日
	 */
	void setCutoffLastDate(Date cutoffLastDate);
	
	/**
	 * @return 締期間(個人)対象日リスト
	 */
	List<Date> getTargetDateList();
	
	/**
	 * @param targetDateList 締期間(個人)対象日リスト
	 */
	void setTargetDateList(List<Date> targetDateList);
	
	/**
	 * @return 休職情報リスト
	 */
	List<SuspensionDtoInterface> getSuspensionList();
	
	/**
	 * @param suspensionList 休職情報リスト
	 */
	void setSuspensionList(List<SuspensionDtoInterface> suspensionList);
	
	/**
	 * @return 設定適用情報群
	 */
	Map<Date, ApplicationDtoInterface> getApplicationMap();
	
	/**
	 * @param applicationMap 設定適用情報群
	 */
	void setApplicationMap(Map<Date, ApplicationDtoInterface> applicationMap);
	
	/**
	 * @return 勤怠設定情報群
	 */
	Map<Date, TimeSettingDtoInterface> getTimeSettingMap();
	
	/**
	 * @param timeSettingMap 勤怠設定情報群
	 */
	void setTimeSettingMap(Map<Date, TimeSettingDtoInterface> timeSettingMap);
	
	/**
	 * @return カレンダ日情報群
	 */
	Map<Date, String> getScheduleMap();
	
	/**
	 * @param scheduleMap カレンダ日情報群
	 */
	void setScheduleMap(Map<Date, String> scheduleMap);
	
	/**
	 * @return 振出・休出勤務形態コード群
	 */
	Map<Date, String> getSubstitutedMap();
	
	/**
	 * @param substitutedMap 振出・休出勤務形態コード群
	 */
	void setSubstitutedMap(Map<Date, String> substitutedMap);
	
	/**
	 * @param attendanceList 勤怠申請リスト
	 */
	void setAttendanceList(List<AttendanceDtoInterface> attendanceList);
	
	/**
	 * @return 勤怠申請リスト
	 */
	List<AttendanceDtoInterface> getAttendanceList();
	
	/**
	 * @return 休日出勤申請リスト
	 */
	List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList();
	
	/**
	 * @param workOnHolidayRequestList 休日出勤申請リスト
	 */
	void setWorkOnHolidayRequestList(List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList);
	
	/***
	 * @return 休暇申請リスト
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList();
	
	/**
	 * @param holidayRequestList 休暇申請リスト
	 */
	void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * @return 代休申請リスト
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList();
	
	/**
	 * @param subHolidayRequestList 代休申請リスト
	 */
	void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList);
	
	/**
	 * @return 残業申請リスト
	 */
	List<OvertimeRequestDtoInterface> getOvertimeRequestList();
	
	/**
	 * @param overtimeRequestList 残業申請リスト
	 */
	void setOvertimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList);
	
	/**
	 * @return 勤務形態変更申請リスト
	 */
	List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequestList();
	
	/**
	 * @param workTypeChangeRequestList 勤務形態変更申請リスト
	 */
	void setWorkTypeChangeRequestList(List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList);
	
	/**
	 * @return 時差出勤申請リスト
	 */
	List<DifferenceRequestDtoInterface> getDifferenceRequestList();
	
	/**
	 * @param differenceRequestList 時差出勤申請リスト
	 */
	void setDifferenceRequestList(List<DifferenceRequestDtoInterface> differenceRequestList);
	
	/**
	 * @return 振替休日情報リスト
	 */
	List<SubstituteDtoInterface> getSubstitubeList();
	
	/**
	 * @param substitubeList 振替休日情報リスト
	 */
	void setSubstitubeList(List<SubstituteDtoInterface> substitubeList);
	
	/**
	 * @return 代休情報リスト
	 */
	List<SubHolidayDtoInterface> getSubHolidayList();
	
	/**
	 * @param subHolidayList 代休情報リスト
	 */
	void setSubHolidayList(List<SubHolidayDtoInterface> subHolidayList);
	
	/**
	 * @return 代休休日出勤勤怠設定情報リスト
	 */
	Map<Date, TimeSettingDtoInterface> getSubHolidayTimeSettingMap();
	
	/**
	 * @param subHolidayTimeSettingMap 代休情報リスト
	 */
	void setSubHolidayTimeSettingMap(Map<Date, TimeSettingDtoInterface> subHolidayTimeSettingMap);
	
	/**
	 * @return ワークフロー情報群
	 */
	Map<Long, WorkflowDtoInterface> getWorkflowMap();
	
	/**
	 * @param workflowMap ワークフロー情報群
	 */
	void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap);
	
	/**
	 * @return 勤怠トランザクション情報リスト
	 */
	Set<AttendanceTransactionDtoInterface> getAttendanceTransactionSet();
	
	/**
	 * @param attendanceTransactionSet 勤怠トランザクション情報リスト
	 */
	void setAttendanceTransactionSet(Set<AttendanceTransactionDtoInterface> attendanceTransactionSet);
	
	/**
	 * ワークフロー情報取得
	 * @param workflow ワークフロー番号
	 * @return ワークフローDTO
	 */
	WorkflowDtoInterface getWorkflowDto(long workflow);
	
	/**
	 * @return 勤務形態エンティティ群
	 */
	Map<String, List<WorkTypeEntity>> getWorkTypeEntityMap();
	
	/**
	 * @param workTypeEntityMap 勤務形態エンティティ群
	 */
	void setWorkTypeEntityMap(Map<String, List<WorkTypeEntity>> workTypeEntityMap);
	
	/**
	 * @return 勤怠トランザクション登録判定情報群
	 */
	Map<Date, String> getAttendanceTransactionMap();
	
	/**------------- 変数(勤怠集計後) -------------**/
	
	/**
	 * @return 勤務時間
	 */
	int getWorkTime();
	
	/**
	 * @return 契約勤務時間
	 */
	int getContractWorkTime();
	
	/**
	 * @return 出勤日数
	 */
	double getTimesWorkDate();
	
	/**
	 * @return 出勤回数
	 */
	int getTimesWork();
	
	/**
	 * @return 法定休日出勤日数
	 */
	double getLegalWorkOnHoliday();
	
	/**
	 * @return 所定休日出勤日数
	 */
	double getSpecificWorkOnHoliday();
	
	/**
	 * @return 出勤実績日数
	 */
	int getTimesAchievement();
	
	/**
	 * @return 出勤対象日数
	 */
	int getTimesTotalWorkDate();
	
	/**
	 * @return 直行回数
	 */
	int getDirectStart();
	
	/**
	 * @return 直帰回数
	 */
	int getDirectEnd();
	
	/**
	 * @return 休憩時間
	 */
	int getRestTime();
	
	/**
	 * @return 深夜休憩時間
	 */
	int getRestLateNight();
	
	/**
	 * @return 所定休出休憩時間
	 */
	int getRestWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出休憩時間
	 */
	int getRestWorkOnHoliday();
	
	/**
	 * @return 公用外出時間
	 */
	int getPublicTime();
	
	/**
	 * @return 私用外出時間
	 */
	int getPrivateTime();
	
	/**
	 * @return 分単位休暇A時間
	 */
	int getMinutelyHolidayATime();
	
	/**
	 * @return 分単位休暇B時間
	 */
	int getMinutelyHolidayBTime();
	
	/**
	 * @return 残業時間
	 */
	int getOvertime();
	
	/**
	 * @return 法定内残業時間
	 */
	int getOvertimeIn();
	
	/**
	 * @return 法定外残業時間
	 */
	int getOvertimeOut();
	
	/**
	 * @return 深夜時間
	 */
	int getLateNight();
	
	/**
	 * @return 深夜所定労働時間内時間
	 */
	int getNightWorkWithinPrescribedWork();
	
	/**
	 * @return 深夜時間外時間
	 */
	int getNightOvertimeWork();
	
	/**
	 * @return 深夜休日労働時間
	 */
	int getNightWorkOnHoliday();
	
	/**
	 * @return 所定休出時間
	 */
	int getWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出時間
	 */
	int getWorkOnHoliday();
	
	/**
	 * @return 減額対象時間
	 */
	int getDecreaseTime();
	
	/**
	 * @return 45時間超残業時間
	 */
	int getFortyFiveHourOvertime();
	
	/**
	 * @return 残業回数
	 */
	int getTimesOvertime();
	
	/**
	 * @return 休日出勤回数
	 */
	int getTimesWorkingHoliday();
	
	/**
	 * @return 合計遅刻日数
	 */
	int getLateDays();
	
	/**
	 * @return 遅刻30分以上日数
	 */
	int getLateThirtyMinutesOrMore();
	
	/**
	 * @return 遅刻30分未満日数
	 */
	int getLateLessThanThirtyMinutes();
	
	/**
	 * @return 合計遅刻時間
	 */
	int getLateTime();
	
	/**
	 * @return 遅刻30分以上時間
	 */
	int getLateThirtyMinutesOrMoreTime();
	
	/**
	 * @return 遅刻30分未満時間
	 */
	int getLateLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計遅刻回数
	 */
	int getTimesLate();
	
	/**
	 * @return 合計早退日数
	 */
	int getLeaveEarlyDays();
	
	/**
	 * @return 早退30分以上日数
	 */
	int getLeaveEarlyThirtyMinutesOrMore();
	
	/**
	 * @return 早退30分未満日数
	 */
	int getLeaveEarlyLessThanThirtyMinutes();
	
	/**
	 * @return  合計早退時間
	 */
	int getLeaveEarlyTime();
	
	/**
	 * @return 早退30分以上時間
	 */
	int getLeaveEarlyThirtyMinutesOrMoreTime();
	
	/**
	 * @return 早退30分未満時間
	 */
	int getLeaveEarlyLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計早退回数
	 */
	int getTimesLeaveEarly();
	
	/**
	 * @return 休日日数
	 */
	int getTimesHoliday();
	
	/**
	 * @return 法定休日日数
	 */
	int getTimesLegalHoliday();
	
	/**
	 * @return 所定休日日数
	 */
	int getTimesSpecificHoliday();
	
	/**
	 * @return 有給休暇日数
	 */
	double getTimesPaidHoliday();
	
	/**
	 * @return 有給休暇時間
	 */
	int getPaidHolidayHour();
	
	/**
	 * @return ストック休暇日数
	 */
	double getTimesStockHoliday();
	
	/**
	 * @return 代休日数
	 */
	double getTimesCompensation();
	
	/**
	 * @return 法定代休日数
	 */
	double getTimesLegalCompensation();
	
	/**
	 * @return 所定代休日数
	 */
	double getTimesSpecificCompensation();
	
	/**
	 * @return 深夜代休日数
	 */
	double getTimesLateCompensation();
	
	/**
	 * @return 振替休日日数
	 */
	double getTimesHolidaySubstitute();
	
	/**
	 * @return 法定振替休日日数
	 */
	double getTimesLegalHolidaySubstitute();
	
	/**
	 * @return 所定振替休日日数
	 */
	double getTimesSpecificHolidaySubstitute();
	
	/**
	 * @return 特別休暇合計日数
	 */
	double getTotalSpecialHoliday();
	
	/**
	 * @return 特別休暇時間数
	 */
	int getSpecialHolidayHour();
	
	/**
	 * @return その他休暇合計日数
	 */
	double getTotalOtherHoliday();
	
	/**
	 * @return その他休暇時間数
	 */
	int getOtherHolidayHour();
	
	/**
	 * @return 欠勤合計日数
	 */
	double getTotalAbsence();
	
	/**
	 * @return 欠勤時間数
	 */
	int getAbsenceHour();
	
	/**
	 * @return 手当合計
	 */
	int getTotalAllowance();
	
	/**
	 * @return 60時間超残業時間
	 */
	int getSixtyHourOvertime();
	
	/**
	 * @return 平日時間内時間(平日法定労働時間内残業時間)
	 */
	int getWorkdayOvertimeIn();
	
	/**
	 * @return 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	int getPrescribedHolidayOvertimeIn();
	
	/**
	 * @return 平日時間外時間(平日法定労働時間外残業時間)
	 */
	int getWorkdayOvertimeOut();
	
	/**
	 * @return 所定休日時間外時間(所定休日法定労働時間外残業時間)
	 */
	int getPrescribedOvertimeOut();
	
	/**
	 * @return 代替休暇日数
	 */
	double getTimesAlternative();
	
	/**
	 * @return 法定代休発生日数
	 */
	double getLegalCompensationOccurred();
	
	/**
	 * @return 所定代休発生日数
	 */
	double getSpecificCompensationOccurred();
	
	/**
	 * @return 深夜代休発生日数
	 */
	double getLateCompensationOccurred();
	
	/**
	 * @return 法定代休未使用日数
	 */
	double getLegalCompensationUnused();
	
	/**
	 * @return 所定代休未使用日数
	 */
	double getSpecificCompensationUnused();
	
	/**
	 * @return 深夜代休未使用日数
	 */
	double getLateCompensationUnused();
	
	/**
	 * @return 所定労働時間内法定休日労働時間
	 */
	int getStatutoryHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外法定休日労働時間
	 */
	int getStatutoryHolidayWorkTimeOut();
	
	/**
	 * @return 所定労働時間内所定休日労働時間
	 */
	int getPrescribedHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外所定休日労働時間
	 */
	int getPrescribedHolidayWorkTimeOut();
	
	/**
	 * @return 無給時短時間
	 */
	int getShortUnpaid();
	
	/**
	 * @return 週40時間超勤務時間
	 */
	int getWeeklyOverFortyHourWorkTime();
	
	/**
	 * @return 法定内残業時間(週40時間超除く)
	 */
	int getOvertimeInNoWeeklyForty();
	
	/**
	 * @return 法定外残業時間(週40時間超除く)
	 */
	int getOvertimeOutNoWeeklyForty();
	
	/**
	 * @return 平日時間内時間(週40時間超除く)
	 */
	int getWeekDayOvertimeInNoWeeklyForty();
	
	/**
	 * @return 平日時間外時間(週40時間超除く)
	 */
	int getWeekDayOvertimeOutNoWeeklyForty();
	
	/**
	 * @return 汎用項目1(数値)
	 */
	int getGeneralIntItem1();
	
	/**
	 * @return 汎用項目2(数値)
	 */
	int getGeneralIntItem2();
	
	/**
	 * @return 汎用項目3(数値)
	 */
	int getGeneralIntItem3();
	
	/**
	 * @return 汎用項目4(数値)
	 */
	int getGeneralIntItem4();
	
	/**
	 * @return 汎用項目5(数値)
	 */
	int getGeneralIntItem5();
	
	/**
	 * @return 汎用項目1(浮動小数点数)
	 */
	int getGeneralDoubleItem1();
	
	/**
	 * @return 汎用項目2(浮動小数点数)
	 */
	int getGeneralDoubleItem2();
	
	/**
	 * @return 汎用項目3(浮動小数点数)
	 */
	int getGeneralDoubleItem3();
	
	/**
	 * @return 汎用項目4(浮動小数点数)
	 */
	int getGeneralDoubleItem4();
	
	/**
	 * @return 汎用項目5(浮動小数点数)
	 */
	int getGeneralDoubleItem5();
	
	/**
	 * @return 特別休暇日数群(キー：休暇コード)
	 */
	Map<String, Float> getSpecialHolidayDays();
	
	/**
	 * @return 特別休暇時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getSpecialHolidayHours();
	
	/**
	 * @return その他休暇日数群(キー：休暇コード)
	 */
	Map<String, Float> getOtherHolidayDays();
	
	/**
	 * @return その他休暇時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getOtherHolidayHours();
	
	/**
	 * @return 欠勤日数群(キー：休暇コード)
	 */
	Map<String, Float> getAbsenceDays();
	
	/**
	 * @return 欠勤時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getAbsenceHours();
	
}
