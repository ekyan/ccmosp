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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.platform.dto.base.RequestDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.base.HolidayRangeDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 申請エンティティクラス。<br>
 * <br>
 * 対象個人IDの対象日における申請情報を保持するクラス。<br>
 * <br>
 */
public class RequestEntity {
	
	// TODO RequestUtilBeanの機能を置き換える。
	
	/**
	 * 対象個人ID。<br>
	 */
	protected String								personalId;
	
	/**
	 * 対象日。<br>
	 */
	protected Date									targetDate;
	
	/**
	 * 休暇申請情報リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>		holidayRequestList;
	
	/**
	 * 残業申請情報リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>		overtimeRequestList;
	
	/**
	 * 振替休日情報リスト。<br>
	 */
	protected List<SubstituteDtoInterface>			substituteList;
	
	/**
	 * 代休申請情報リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>	subHolidayRequestList;
	
	/**
	 * 休日出勤申請情報。<br>
	 */
	protected WorkOnHolidayRequestDtoInterface		workOnHolidayRequestDto;
	
	/**
	 * 時差出勤申請情報。<br>
	 */
	protected DifferenceRequestDtoInterface			differenceRequestDto;
	
	/**
	 * 勤務形態変更申請情報。<br>
	 */
	protected WorkTypeChangeRequestDtoInterface		workTypeChangeRequestDto;
	
	/**
	 * 勤怠情報。<br>
	 */
	protected AttendanceDtoInterface				attendanceDto;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>		workflowMap;
	
	/**
	 * 予定勤務形態コード。<br>
	 * 対象個人IDの対象日における予定勤務形態コード。<br>
	 */
	protected String								scheduledWorkTypeCode;
	
	/**
	 * 振替勤務形態コード。<br>
	 * 振休・休出申請(振替出勤)により振り替えた出勤日の予定勤務形態コード。<br>
	 */
	protected String								substitutedWorkTypeCode;
	
	
	/**
	 * コンストラクタ。<br>
	 * <br>
	 * 各種申請情報は、setterで設定する。
	 * <br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 */
	public RequestEntity(String personalId, Date targetDate) {
		this.personalId = personalId;
		this.targetDate = CapsuleUtility.getDateClone(targetDate);
	}
	
	/**
	 * 勤怠データが存在するかを確認する。<br>
	 * <br>
	 * 承認状況が下書以上の勤怠データが存在すれば、trueを返す。<br>
	 * (勤怠データには取下状態がない。)
	 * <br>
	 * @return 確認結果(true：勤怠データが存在する、false：存在しない)
	 */
	public boolean hasAttendance() {
		return attendanceDto != null;
	}
	
	/**
	 * 勤怠データに直行が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直行が設定されている、false：されていない)
	 */
	public boolean isAttendanceDirectStart() {
		// 勤怠データが存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直行が設定されているかを確認
		return isChecked(attendanceDto.getDirectStart());
	}
	
	/**
	 * 勤怠データに直帰が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直帰が設定されている、false：されていない)
	 */
	public boolean isAttendanceDirectEnd() {
		// 勤怠データが存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直帰が設定されているかを確認
		return isChecked(attendanceDto.getDirectEnd());
	}
	
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
	 * 3.振出・休出申請がある場合：<br>
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 4.勤務形態変更申請がある場合
	 * 変更勤務形態(勤務形態変更申請の勤務形態)を返す。<br>
	 * 
	 * 5.それ以外の場合
	 * 予定勤務形態コード(カレンダで登録されている勤務形態)を返す。<br>
	 * <br>
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	public String getWorkType() {
		// 1.勤怠申請が存在する場合
		if (attendanceDto != null) {
			// 勤怠申請に設定されている勤務形態
			return attendanceDto.getWorkTypeCode();
		}
		// 承認済フラグ準備(false：申請済申請を考慮)
		boolean isCompleted = false;
		// 2.全休の場合
		if (isAllHoliday(isCompleted)) {
			// 空文字(全休の振替休日情報がある場合はその振替種別)を取得
			return getSubstituteType(isCompleted);
		}
		// 3.振出・休出申請がある場合
		if (getWorkOnHolidayRequestDto(isCompleted) != null) {
			// 振出・休出申請の勤務形態を取得
			return getWorkOnHolidayWorkType(isCompleted);
		}
		// 4.勤務形態変更申請がある場合
		if (getWorkTypeChangeRequestDto(isCompleted) != null) {
			// 変更勤務形態を取得
			return getChangeWorkType(isCompleted);
		}
		// 4.それ以外の場合
		// 予定勤務形態コード(カレンダで登録されている勤務形態)を取得
		return scheduledWorkTypeCode;
	}
	
	/**
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、空文字を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請の勤務形態
	 */
	public String getWorkOnHolidayWorkType(boolean isCompleted) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請情報確認
		if (dto == null) {
			return "";
		}
		// 振替出勤の場合
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 振替勤務形態コードを取得
			return substitutedWorkTypeCode;
		}
		// 休日出勤の場合
		// 休出種別確認
		if (dto.getWorkOnHolidayType().equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 法定休出の場合
			return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
		}
		// 所定休出の場合
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
	}
	
	/**
	 * 振出・休出申請(休日出勤)の出勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休日出勤)の出勤予定時刻
	 */
	public Date getWorkOnHolidayStartTime(boolean isCompleted) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請情報確認
		if (dto == null) {
			return null;
		}
		// 振替出勤の場合
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return null;
		}
		// 休日出勤の場合
		return dto.getStartTime();
	}
	
	/**
	 * 振出・休出申請(休日出勤)の退勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の退勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休日出勤)の退勤予定時刻
	 */
	public Date getWorkOnHolidayEndTime(boolean isCompleted) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請情報確認
		if (dto == null) {
			return null;
		}
		// 振替出勤の場合
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return null;
		}
		// 休日出勤の場合
		return dto.getEndTime();
	}
	
	/**
	 * 残業申請(勤務前残業)の申請時間(分)を取得する。
	 * <br>
	 * 残業申請(勤務前残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務前残業)の申請時間(分)
	 */
	public int getOvertimeMinutesBeforeWork(boolean isCompleted) {
		// 残業申請情報取得
		List<OvertimeRequestDtoInterface> list = getOvertimeRequestList(isCompleted);
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 勤務前残業申請確認
			if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
				// 申請時間を取得
				return dto.getRequestTime();
			}
		}
		// 残業申請(勤務前残業)が無い場合
		return 0;
	}
	
	/**
	 * 残業申請(勤務後残業)の申請時間(分)を取得する。
	 * <br>
	 * 残業申請(勤務後残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務後残業)の申請時間(分)
	 */
	public int getOvertimeMinutesAfterWork(boolean isCompleted) {
		// 残業申請情報取得
		List<OvertimeRequestDtoInterface> list = getOvertimeRequestList(isCompleted);
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 勤務前残業申請確認
			if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER) {
				// 申請時間を取得
				return dto.getRequestTime();
			}
		}
		// 残業申請(勤務前残業)が無い場合
		return 0;
	}
	
	/**
	 * 対象日が全休であるかを確認する。<br>
	 * 全休、或いは前半休+後半休であれば、全休と判断する。<br>
	 * <br>
	 * 但し、振出・休出申請情報がある場合は、振替の振替とみなし、全休ではないとする。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：全休である、false：そうでない)
	 */
	public boolean isAllHoliday(boolean isCompleted) {
		// 全休があるかを確認
		boolean hasAllHoliday = hasAllHoliday(isCompleted);
		// 前半休を確認
		boolean hasAmHoliday = hasAmHoliday(isCompleted);
		// 後半休があるかを確認
		boolean hasPmHoliday = hasPmHoliday(isCompleted);
		// 全休であるかを確認
		boolean isAllHoliday = hasAllHoliday || (hasAmHoliday && hasPmHoliday);
		// 振出・休出申請情報確認
		if (getWorkOnHolidayRequestDto(isCompleted) != null) {
			// 振替の振替
			isAllHoliday = false;
		}
		return isAllHoliday;
	}
	
	/**
	 * 対象日が前半休であるかを確認する。<br>
	 * 全休がなく、後半休がなく、前半休があれば、前半休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：前半休である、false：そうでない)
	 */
	public boolean isAmHoliday(boolean isCompleted) {
		// 全休があるかを確認
		if (hasAllHoliday(isCompleted)) {
			return false;
		}
		// 後半休があるかを確認
		if (hasPmHoliday(isCompleted)) {
			return false;
		}
		// 前半休を確認
		return hasAmHoliday(isCompleted);
	}
	
	/**
	 * 対象日が後半休であるかを確認する。<br>
	 * 全休がなく、前半休がなく、後半休があれば、後半休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：後半休である、false：そうでない)
	 */
	public boolean isPmHoliday(boolean isCompleted) {
		// 全休があるかを確認
		if (hasAllHoliday(isCompleted)) {
			return false;
		}
		// 前半休があるかを確認
		if (hasAmHoliday(isCompleted)) {
			return false;
		}
		// 後半休を確認
		return hasPmHoliday(isCompleted);
	}
	
	/**
	 * 全休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、全休があるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：全休が有る、false：全休が無い)
	 */
	protected boolean hasAllHoliday(boolean isCompleted) {
		// 休暇申請情報確認
		if (hasAllHoliday(getHolidayRequestList(isCompleted))) {
			return true;
		}
		// 代休申請情報確認
		if (hasAllHoliday(getSubHolidayRequestList(isCompleted))) {
			return true;
		}
		// 振替休日情報確認
		if (hasAllHoliday(getSubstituteList(isCompleted))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 前半休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、前半休があるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：前半休が有る、false：前半休が無い)
	 */
	protected boolean hasAmHoliday(boolean isCompleted) {
		// 休暇申請情報確認
		if (hasAmHoliday(getHolidayRequestList(isCompleted))) {
			return true;
		}
		// 代休申請情報確認
		if (hasAmHoliday(getSubHolidayRequestList(isCompleted))) {
			return true;
		}
		// 振替休日情報確認
		if (hasAmHoliday(getSubstituteList(isCompleted))) {
			return true;
		}
		//  振出・休出申請情報確認
		if (hasPmWorkOnHoliday(isCompleted)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 後半休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、午後休があるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：後半休が有る、false：後半休が無い)
	 */
	protected boolean hasPmHoliday(boolean isCompleted) {
		// 休暇申請情報確認
		if (hasPmHoliday(getHolidayRequestList(isCompleted))) {
			return true;
		}
		// 代休申請情報確認
		if (hasPmHoliday(getSubHolidayRequestList(isCompleted))) {
			return true;
		}
		// 振替休日情報確認
		if (hasPmHoliday(getSubstituteList(isCompleted))) {
			return true;
		}
		//  振出・休出申請情報確認
		if (hasAmWorkOnHoliday(isCompleted)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 振替出勤(午前)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	protected boolean hasAmWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報を取得及び確認
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDto(isCompleted);
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		// 振替出勤(午前)を確認
		return workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
	}
	
	/**
	 * 振替出勤(午後)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	protected boolean hasPmWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報を取得及び確認
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDto(isCompleted);
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		// 振替出勤(午前)を確認
		return workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
	}
	
	/**
	 * 初回連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	public List<Date> getHourlyHolidayFirstSequenceTimes() {
		// 承認済フラグ設定(false：申請済申請を考慮)
		boolean isCompleted = false;
		// リストの準備
		List<Date> hourlyHolidayFirstSequenceTimes = new ArrayList<Date>();
		// 休暇申請情報(時間休)群を取得
		Map<Date, HolidayRequestDtoInterface> map = getHourlyHolidayMap(isCompleted);
		// 休暇申請情報(時間休)群を確認
		if (map.isEmpty()) {
			return hourlyHolidayFirstSequenceTimes;
		}
		// 時間休開始時刻リスト取得
		List<Date> startTimeList = new ArrayList<Date>(map.keySet());
		// ソート
		Collections.sort(startTimeList);
		// 開始時間をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(startTimeList.get(0).getTime()));
		// 連続終了時刻準備
		Date endTime = null;
		// 休暇申請情報(時間休)毎に処理
		for (Date startTime : startTimeList) {
			// 連続確認
			if (endTime != null && startTime.compareTo(endTime) != 0) {
				break;
			}
			// 休暇申請情報(時間休)情報取得
			HolidayRequestDtoInterface dto = map.get(startTime);
			// 終了時刻取得
			endTime = dto.getEndTime();
		}
		// 連続終了時刻をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(endTime.getTime()));
		return hourlyHolidayFirstSequenceTimes;
	}
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	public List<Date> getHourlyHolidayLastSequenceTimes() {
		// 承認済フラグ設定(false：申請済申請を考慮)
		boolean isCompleted = false;
		// リストの準備
		List<Date> hourlyHolidayFirstSequenceTimes = new ArrayList<Date>();
		// 休暇申請情報(時間休)群を取得
		Map<Date, HolidayRequestDtoInterface> map = getHourlyHolidayMap(isCompleted);
		// 休暇申請情報(時間休)群を確認
		if (map.isEmpty()) {
			return hourlyHolidayFirstSequenceTimes;
		}
		// 時間休開始時刻リスト取得
		List<Date> startTimeList = new ArrayList<Date>(map.keySet());
		// ソート(逆順)
		Collections.sort(startTimeList);
		Collections.reverse(startTimeList);
		// 終了時間をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(map.get(startTimeList.get(0)).getEndTime().getTime()));
		// 連続開始時刻準備
		Date sequenceStartTime = null;
		// 休暇申請情報(時間休)毎に処理
		for (Date startTime : startTimeList) {
			// 休暇申請情報(時間休)情報取得
			HolidayRequestDtoInterface dto = map.get(startTime);
			// 連続確認
			if (sequenceStartTime != null && dto.getEndTime().compareTo(sequenceStartTime) != 0) {
				break;
			}
			// 連続開始時刻設定
			sequenceStartTime = startTime;
		}
		// 連続終了時刻をリストに設定
		hourlyHolidayFirstSequenceTimes.add(0, new Date(sequenceStartTime.getTime()));
		return hourlyHolidayFirstSequenceTimes;
	}
	
	/**
	 * 休暇申請情報(時間休)群を取得する。<br>
	 * 時休開始時刻をキー、休暇申請情報を値とする。<br>
	 * 休暇申請情報(時間休)が存在しない場合は、空のマップを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 休暇申請情報(時間休)群
	 */
	protected Map<Date, HolidayRequestDtoInterface> getHourlyHolidayMap(boolean isCompleted) {
		// 休暇申請情報群を準備
		Map<Date, HolidayRequestDtoInterface> map = new HashMap<Date, HolidayRequestDtoInterface>();
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> list = getHolidayRequestList(isCompleted);
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : list) {
			// 休暇申請情報が時間休であるかを確認
			if (isHourlyHoliday(dto) == false) {
				continue;
			}
			// 休暇申請情報群に設定
			map.put(dto.getStartTime(), dto);
		}
		return map;
	}
	
	/**
	 * 休暇申請情報が時間休であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：時間休である、false：時間休でない)
	 */
	protected boolean isHourlyHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報を確認
		if (dto == null) {
			return false;
		}
		// 時間休であるかを確認
		return dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME;
	}
	
	/**
	 * 振替休日情報(全休)の休暇種別(法定休日、所定休日)を取得する。<br>
	 * <br>
	 * 振替休日情報(全休)が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振替休日情報(全休)の休暇種別(法定休日、所定休日)
	 */
	protected String getSubstituteType(boolean isCompleted) {
		// 振替休日情報リストを取得
		List<SubstituteDtoInterface> list = getSubstituteList(isCompleted);
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : list) {
			// 全休確認
			if (isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_ALL)) {
				return dto.getSubstituteType();
			}
		}
		return "";
	}
	
	/**
	 * 変更勤務形態を取得する。<br>
	 * <br>
	 * 勤務形態変更申請が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 変更勤務形態
	 */
	protected String getChangeWorkType(boolean isCompleted) {
		// 勤務形態変更申請を取得
		WorkTypeChangeRequestDtoInterface dto = getWorkTypeChangeRequestDto(isCompleted);
		// 勤務形態変更申請を確認
		if (dto == null) {
			return "";
		}
		// 変更勤務形態を取得
		return dto.getWorkTypeCode();
	}
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休暇申請情報リスト
	 */
	protected List<HolidayRequestDtoInterface> getHolidayRequestList(boolean isCompleted) {
		return castHolidayRequest(getRequestList(holidayRequestList, isCompleted));
	}
	
	/**
	 * 代休申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 代休申請情報リスト
	 */
	protected List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(boolean isCompleted) {
		return castSubHolidayRequest(getRequestList(subHolidayRequestList, isCompleted));
	}
	
	/**
	 * 振替休日情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 代休申請情報リスト
	 */
	protected List<SubstituteDtoInterface> getSubstituteList(boolean isCompleted) {
		return castSubstitute(getRequestList(substituteList, isCompleted));
	}
	
	/**
	 * 振出・休出申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 振出・休出申請情報
	 */
	protected WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(boolean isCompleted) {
		return (WorkOnHolidayRequestDtoInterface)getRequestDto(workOnHolidayRequestDto, isCompleted);
	}
	
	/**
	 * 残業申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休暇申請情報リスト
	 */
	protected List<OvertimeRequestDtoInterface> getOvertimeRequestList(boolean isCompleted) {
		return castOvertimeRequest(getRequestList(overtimeRequestList, isCompleted));
	}
	
	/**
	 * 勤務形態変更申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 振出・休出申請情報
	 */
	protected WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestDto(boolean isCompleted) {
		return (WorkTypeChangeRequestDtoInterface)getRequestDto(workTypeChangeRequestDto, isCompleted);
	}
	
	/**
	 * 申請情報リストを休暇申請情報リストへキャストする。<br>
	 * @param list 申請情報リスト
	 * @return 休暇申請情報リスト
	 */
	protected List<HolidayRequestDtoInterface> castHolidayRequest(List<? extends RequestDtoInterface> list) {
		// 休暇申請情報リストの準備
		List<HolidayRequestDtoInterface> castedList = new ArrayList<HolidayRequestDtoInterface>();
		// 申請情報毎にキャスト
		for (RequestDtoInterface dto : list) {
			castedList.add((HolidayRequestDtoInterface)dto);
		}
		return castedList;
	}
	
	/**
	 * 申請情報リストを代休申請情報リストへキャストする。<br>
	 * @param list 申請情報リスト
	 * @return 代休申請情報リスト
	 */
	protected List<SubHolidayRequestDtoInterface> castSubHolidayRequest(List<? extends RequestDtoInterface> list) {
		// 代休申請情報リストの準備
		List<SubHolidayRequestDtoInterface> castedList = new ArrayList<SubHolidayRequestDtoInterface>();
		// 申請情報毎にキャスト
		for (RequestDtoInterface dto : list) {
			castedList.add((SubHolidayRequestDtoInterface)dto);
		}
		return castedList;
	}
	
	/**
	 * 申請情報リストを振替休日情報リストへキャストする。<br>
	 * @param list 申請情報リスト
	 * @return 振替休日情報リスト
	 */
	protected List<SubstituteDtoInterface> castSubstitute(List<? extends RequestDtoInterface> list) {
		// 振替休日情報リストの準備
		List<SubstituteDtoInterface> castedList = new ArrayList<SubstituteDtoInterface>();
		// 申請情報毎にキャスト
		for (RequestDtoInterface dto : list) {
			castedList.add((SubstituteDtoInterface)dto);
		}
		return castedList;
	}
	
	/**
	 * 申請情報リストを残業申請情報リストへキャストする。<br>
	 * @param list 申請情報リスト
	 * @return 残業申請情報リスト
	 */
	protected List<OvertimeRequestDtoInterface> castOvertimeRequest(List<? extends RequestDtoInterface> list) {
		// 残業申請情報リストの準備
		List<OvertimeRequestDtoInterface> castedList = new ArrayList<OvertimeRequestDtoInterface>();
		// 申請情報毎にキャスト
		for (RequestDtoInterface dto : list) {
			castedList.add((OvertimeRequestDtoInterface)dto);
		}
		return castedList;
	}
	
	/**
	 * 抽出対象申請情報リストから、申請済申請情報リストを取得する。<br>
	 * @param list        抽出対象申請情報リスト
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 申請済申請情報リスト
	 */
	protected List<? extends RequestDtoInterface> getRequestList(List<? extends RequestDtoInterface> list,
			boolean isCompleted) {
		// 申請済申請リストの準備
		List<RequestDtoInterface> appliedList = new ArrayList<RequestDtoInterface>();
		// 申請済の申請のみを抽出
		for (RequestDtoInterface dto : list) {
			dto = getRequestDto(dto, isCompleted);
			if (dto != null) {
				appliedList.add(dto);
			}
		}
		return appliedList;
	}
	
	/**
	 * 対象申請情報から、申請済申請情報を取得する。<br>
	 * 対象が存在しない場合はnullを返す。<br>
	 * <br>
	 * 下書、取下、一次戻以外の場合は、申請済であると判断する。<br>
	 * 承認済フラグがtrueの場合は、承認済の申請情報のみを返す。<br>
	 * <br>
	 * @param dto         抽出対象申請情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 申請済申請情報
	 */
	protected RequestDtoInterface getRequestDto(RequestDtoInterface dto, boolean isCompleted) {
		// 抽出対象申請情報確認
		if (dto == null) {
			return null;
		}
		// 承認済フラグ確認
		if (isCompleted) {
			// 承認済申請のみ取得
			if (WorkflowUtility.isCompleted(workflowMap.get(dto.getWorkflow()))) {
				return dto;
			}
		} else {
			// 申請済申請を取得
			if (WorkflowUtility.isApplied(workflowMap.get(dto.getWorkflow()))) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 休暇(範囲)情報リストに全休があるかを確認する。<br>
	 * @param list 休暇(範囲)情報リスト
	 * @return 確認結果(true：全休がある、false：ない)
	 */
	protected boolean hasAllHoliday(List<? extends HolidayRangeDtoInterface> list) {
		return hasTheHolidayRange(list, TimeConst.CODE_HOLIDAY_RANGE_ALL);
	}
	
	/**
	 * 休暇(範囲)情報リストに前半休があるかを確認する。<br>
	 * @param list 休暇(範囲)情報リスト
	 * @return 確認結果(true：前半休がある、false：ない)
	 */
	protected boolean hasAmHoliday(List<? extends HolidayRangeDtoInterface> list) {
		return hasTheHolidayRange(list, TimeConst.CODE_HOLIDAY_RANGE_AM);
	}
	
	/**
	 * 休暇(範囲)情報リストに後半休があるかを確認する。<br>
	 * @param list 休暇(範囲)情報リスト
	 * @return 確認結果(true：後半休がある、false：ない)
	 */
	protected boolean hasPmHoliday(List<? extends HolidayRangeDtoInterface> list) {
		return hasTheHolidayRange(list, TimeConst.CODE_HOLIDAY_RANGE_PM);
	}
	
	/**
	 * 休暇(範囲)情報リストにその休暇範囲があるかを確認する。<br>
	 * @param list            休暇(範囲)情報リスト
	 * @param theHolidayRange その休暇範囲
	 * @return 確認結果(true：その休暇範囲がある、false：ない)
	 */
	protected boolean hasTheHolidayRange(List<? extends HolidayRangeDtoInterface> list, int theHolidayRange) {
		// 休暇(範囲)情報毎に確認
		for (HolidayRangeDtoInterface dto : list) {
			// 休暇範囲確認
			if (isTheHolidayRange(dto, theHolidayRange)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 休暇(範囲)情報がその休暇範囲であるかを確認する。<br>
	 * @param dto             休暇(範囲)情報
	 * @param theHolidayRange その休暇範囲
	 * @return 確認結果(true：その休暇範囲である、false：そうでない)
	 */
	protected boolean isTheHolidayRange(HolidayRangeDtoInterface dto, int theHolidayRange) {
		if (dto == null) {
			return false;
		}
		return dto.getHolidayRange() == theHolidayRange;
	}
	
	/**
	 * チェックボックスがチェックされているかを確認する。<br>
	 * @param value 値
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	protected boolean isChecked(int value) {
		return value == Integer.parseInt(MospConst.CHECKBOX_ON);
	}
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return targetDate
	 */
	public Date getTargetDate() {
		return CapsuleUtility.getDateClone(targetDate);
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = CapsuleUtility.getDateClone(targetDate);
	}
	
	/**
	 * @param holidayRequestList セットする holidayRequestList
	 */
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList) {
		this.holidayRequestList = holidayRequestList;
	}
	
	/**
	 * @param overtimeRequestList セットする overTimeRequestList
	 */
	public void setOverTimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList) {
		this.overtimeRequestList = overtimeRequestList;
	}
	
	/**
	 * @param substituteList セットする substituteList
	 */
	public void setSubstituteList(List<SubstituteDtoInterface> substituteList) {
		this.substituteList = substituteList;
	}
	
	/**
	 * @param subHolidayRequestList セットする subHolidayRequestList
	 */
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList) {
		this.subHolidayRequestList = subHolidayRequestList;
	}
	
	/**
	 * @param workOnHolidayRequestDto セットする workOnHolidayRequestDto
	 */
	public void setWorkOnHolidayRequestDto(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto) {
		this.workOnHolidayRequestDto = workOnHolidayRequestDto;
	}
	
	/**
	 * 時差出勤申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 時差出勤申請情報
	 */
	public DifferenceRequestDtoInterface getDifferenceRequestDto(boolean isCompleted) {
		return (DifferenceRequestDtoInterface)getRequestDto(differenceRequestDto, isCompleted);
	}
	
	/**
	 * @param differenceRequestDto セットする differenceRequestDto
	 */
	public void setDifferenceRequestDto(DifferenceRequestDtoInterface differenceRequestDto) {
		this.differenceRequestDto = differenceRequestDto;
	}
	
	/**
	 * @param workTypeChangeRequestDto セットする workTypeChangeRequestDto
	 */
	public void setWorkTypeChangeRequestDto(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto) {
		this.workTypeChangeRequestDto = workTypeChangeRequestDto;
	}
	
	/**
	 * @param attendanceDto セットする attendanceDto
	 */
	public void setAttendanceDto(AttendanceDtoInterface attendanceDto) {
		this.attendanceDto = attendanceDto;
	}
	
	/**
	 * @param workflowMap セットする workflowMap
	 */
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap) {
		this.workflowMap = workflowMap;
	}
	
	/**
	 * @param scheduledWorkTypeCode セットする scheduledWorkTypeCode
	 */
	public void setScheduledWorkTypeCode(String scheduledWorkTypeCode) {
		this.scheduledWorkTypeCode = scheduledWorkTypeCode;
	}
	
	/**
	 * @param substitutedWorkTypeCode セットする substitutedWorkTypeCode
	 */
	public void setSubstitutedWorkTypeCode(String substitutedWorkTypeCode) {
		this.substitutedWorkTypeCode = substitutedWorkTypeCode;
	}
	
}
