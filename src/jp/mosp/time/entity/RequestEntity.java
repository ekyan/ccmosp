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

import jp.mosp.framework.base.MospException;
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
import jp.mosp.time.utils.TimeUtility;

/**
 * 申請エンティティクラス。<br>
 * <br>
 * 対象個人IDの対象日における申請情報を保持するクラス。<br>
 * <br>
 */
public class RequestEntity implements RequestEntityInterface {
	
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
	 * <br>
	 * 振休・休出申請(振替出勤)、勤務形態変更申請等は考慮せず、
	 * カレンダに登録されている勤務形態を保持する。<br>
	 * <br>
	 */
	protected String								scheduledWorkTypeCode;
	
	/**
	 * 振出・休出勤務形態コード。<br>
	 * 振出・休出申請により出勤する日の予定勤務形態コード。<br>
	 */
	protected String								substitutedWorkTypeCode;
	
	
	/**
	 * コンストラクタ。<br>
	 * <br>
	 * 各種申請情報は、setterで設定する。
	 */
	public RequestEntity() {
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTargetDate(Date targetDate) {
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
	@Override
	public boolean hasAttendance() {
		return attendanceDto != null;
	}
	
	/**
	 * 勤怠申請がされているかを確認する。<br>
	 * <br>
	 * {@link WorkflowUtility#isApplied(WorkflowDtoInterface)}
	 * で確認する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠申請がされていいる、false：勤怠申請がされていない)
	 */
	@Override
	public boolean isAttendanceApplied() {
		// 勤怠データが存在しない場合
		if (hasAttendance() == false) {
			// 勤怠申請がされていないと判断
			return false;
		}
		// 勤怠申請がされているかを確認
		return WorkflowUtility.isApplied(workflowMap.get(attendanceDto.getWorkflow()));
	}
	
	/**
	 * 勤怠データに直行が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直行が設定されている、false：されていない)
	 */
	@Override
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
	@Override
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
	@Override
	public String getWorkType(boolean isAttendanceConsidered) {
		// 1.勤怠申請が存在する場合
		if (attendanceDto != null && isAttendanceConsidered) {
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
		// 3.勤務形態変更申請がある場合
		if (getWorkTypeChangeRequestDto(isCompleted) != null) {
			// 変更勤務形態を取得
			return getChangeWorkType(isCompleted);
		}
		// 4.振出・休出申請がある場合
		if (getWorkOnHolidayRequestDto(isCompleted) != null) {
			// 振出・休出申請の勤務形態を取得
			return getWorkOnHolidayWorkType(isCompleted);
		}
		// 5.それ以外の場合
		// 予定勤務形態コード(カレンダで登録されている勤務形態)を取得
		return scheduledWorkTypeCode;
	}
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * {@link RequestEntity#getWorkType(boolean) }参照。<br>
	 * <br>
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	@Override
	public String getWorkType() {
		return getWorkType(true);
	}
	
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
	@Override
	public boolean isWorkDay() {
		// 承認済フラグ準備(false：申請済申請を考慮)
		boolean isCompleted = false;
		// 1.全休の場合
		if (isAllHoliday(isCompleted)) {
			// 勤務日でないと判断
			return false;
		}
		// 2.振出・休出申請がある場合(承認済申請のみ)
		if (getWorkOnHolidayRequestDto(true) != null) {
			// 勤務日であると判断
			return true;
		}
		// 3.予定勤務形態コードが法定休日か所定休日である場合
		if (TimeUtility.isHoliday(scheduledWorkTypeCode)) {
			// 勤務日でないと判断
			return false;
		}
		// 4.それ以外の場合(勤務日であると判断)
		return true;
	}
	
	/**
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、空文字を返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請の勤務形態
	 */
	@Override
	public String getWorkOnHolidayWorkType(boolean isCompleted) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請情報確認
		if (dto == null) {
			return "";
		}
		// 振出・休出勤務形態コードを取得
		return substitutedWorkTypeCode;
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：全休である、false：そうでない)
	 */
	@Override
	public boolean isAllHoliday(boolean isCompleted) {
		// 全休があるかを確認
		boolean hasAllHoliday = hasAllHoliday(isCompleted);
		// 前半休を確認
		boolean hasAmHoliday = hasAmHoliday(isCompleted);
		// 後半休があるかを確認
		boolean hasPmHoliday = hasPmHoliday(isCompleted);
		// 全休であるかを確認
		return hasAllHoliday || (hasAmHoliday && hasPmHoliday);
	}
	
	/**
	 * 対象日が前半休であるかを確認する。<br>
	 * 全休がなく、後半休がなく、前半休があれば、前半休と判断する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：前半休である、false：そうでない)
	 */
	@Override
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
	@Override
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
	 * 但し、振替休日情報に全日があったとしても、振出・休出申請情報があった場合は
	 * 振替の振替とみなし、全休でないとする。<br>
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
		// 振替休日情報(全日)があり振出・休出申請情報が無い場合
		if (hasAllHoliday(getSubstituteList(isCompleted)) && hasWorkOnHoliday(isCompleted) == false) {
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
			// 振替出勤申請が午前でない場合(半日振替の振替でない場合)
			if (hasAmWorkOnHoliday(isCompleted) == false) {
				return true;
			}
		}
		// 振出・休出申請情報確認
		if (hasPmWorkOnHoliday(isCompleted)) {
			// 振替休日が午後でない場合(半日振替の振替でない場合)
			if (hasPmHoliday(getSubstituteList(isCompleted)) == false) {
				return true;
			}
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
			// 振替出勤申請が午後の場合(半日振替の振替でない場合)
			if (hasPmWorkOnHoliday(isCompleted) == false) {
				return true;
			}
		}
		// 振出・休出申請情報確認
		if (hasAmWorkOnHoliday(isCompleted)) {
			// 振替休日が午前の場合(半日振替の振替でない場合)
			if (hasAmHoliday(getSubstituteList(isCompleted)) == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 振替出勤(午前)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	protected boolean hasAmWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振替出勤(午前)を確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
	}
	
	/**
	 * 振替出勤(午後)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	protected boolean hasPmWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振替出勤(午前)を確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
	}
	
	/**
	 * 振出・休出申請情報が有るかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振出・休出申請情報が有る、false：無い)
	 */
	protected boolean hasWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請情報が有るかを確認
		return workOnHolidayRequestDto != null;
	}
	
	/**
	 * 法定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：法定休日出勤である、false：そうでない)
	 */
	@Override
	public boolean isWorkOnLegal(boolean isCompleted) {
		// 休日出勤(振替申請しない)でない場合
		if (isWorkOnHolidayNotSubstitute(isCompleted) == false) {
			// 法定休日出勤でないと判断
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 法定休日出勤であるかを確認
		return dto.getWorkOnHolidayType().equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY);
	}
	
	/**
	 * 所定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：所定休日出勤である、false：そうでない)
	 */
	@Override
	public boolean isWorkOnPrescribed(boolean isCompleted) {
		// 休日出勤(振替申請しない)でない場合
		if (isWorkOnHolidayNotSubstitute(isCompleted) == false) {
			// 所定休日出勤でないと判断
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 所定休日出勤であるかを確認
		return dto.getWorkOnHolidayType().equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY);
	}
	
	/**
	 * 振替出勤であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤である、false：そうでない)
	 */
	@Override
	public boolean isWorkOnHolidaySubstitute(boolean isCompleted) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 休日出勤(振替申請しない)でないかを確認
		return isWorkOnHolidayNotSubstitute(isCompleted) == false;
	}
	
	/**
	 * 休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：休日出勤(振替申請しない)である、false：そうでない)
	 */
	@Override
	public boolean isWorkOnHolidayNotSubstitute(boolean isCompleted) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振替申請しない場合
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤(振替申請しない)であると判断
			return true;
		}
		// 休日出勤(振替申請しない)でないと判断
		return false;
	}
	
	/**
	 * 初回連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	@Override
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
	 * 初回連続時間休時刻(開始時刻(分)及び終了時刻(分))を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public List<Integer> getHourlyHolidayFirstSequenceMinutes() throws MospException {
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = getHourlyHolidayFirstSequenceTimes();
		// 対象日からの分数として取得
		return getHourlyHolidaySequenceMinutes(holidayTimeList);
	}
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	@Override
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
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public List<Integer> getHourlyHolidayLastSequenceMinutes() throws MospException {
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = getHourlyHolidayLastSequenceTimes();
		// 対象日からの分数として取得
		return getHourlyHolidaySequenceMinutes(holidayTimeList);
	}
	
	/**
	 * 連続時間休時刻(開始時刻及び終了時刻)を対象日からの分数として取得する。<br>
	 * <br>
	 * @param holidayTimeList 連続時間休時刻(開始時刻及び終了時刻)
	 * @return 連続時間休時刻(開始時刻及び終了時刻)(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public List<Integer> getHourlyHolidaySequenceMinutes(List<Date> holidayTimeList) throws MospException {
		// 連続時間休時刻(開始時刻及び終了時刻)(分)を準備
		List<Integer> holidayMinuteList = new ArrayList<Integer>();
		// 時間単位有給休暇が存在しない場合
		if (holidayTimeList.isEmpty()) {
			// 空のリストを取得
			return holidayMinuteList;
		}
		// 最終連続時間休時刻の開始時刻及び終了時刻(分)を取得
		holidayMinuteList.add(TimeUtility.getMinutes(holidayTimeList.get(0), targetDate));
		holidayMinuteList.add(TimeUtility.getMinutes(holidayTimeList.get(1), targetDate));
		// 連続時間休時刻(開始時刻及び終了時刻)(分)を取得
		return holidayMinuteList;
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
	 * 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)を取得する。<br>
	 * <br>
	 * 振替休日情報(全休or前半休+後半休)が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * 半日の振替は所定休日のみが認められている。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)
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
		// 前半休+後半休の場合
		if (hasAmHoliday(list) && hasPmHoliday(list)) {
			// 所定休日を取得
			return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY;
		}
		// 振替休日情報(全休or前半休+後半休)が存在しない場合
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
	 * 半日振休＋半日振休があるか判断する。
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振休＋半日振休、false：そうでない)
	 */
	@Override
	public boolean isAmPmHalfSubstitute(boolean isCompleted) {
		// 振替休日リスト取得
		List<SubstituteDtoInterface> list = getSubstituteList(isCompleted);
		// 半日振休＋半日振休があるか判断
		return hasAmHoliday(list) && hasPmHoliday(list);
	}
	
	/**
	 * 半日振替の振替かどうか判断する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振替の振替、false：そうでない)
	 */
	@Override
	public boolean isHalfPostpone(boolean isCompleted) {
		// 半日振替出勤申請がない場合
		if (hasAmWorkOnHoliday(isCompleted) == false && hasPmWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 振替休日リスト取得
		List<SubstituteDtoInterface> list = getSubstituteList(isCompleted);
		// 半日振替休日がない場合
		if (hasAmHoliday(list) == false && hasPmHoliday(list) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * 勤怠の申請が可能か確認する。<br>
	 * 次の条件を満たす時、勤怠の申請が可能と判断する。<br>
	 * ・勤怠が未申請である。<br>
	 * ・申請済であり未承認の残業申請がない。<br>
	 * ・
	 * @return 確認結果(true：勤怠申請可能、false：勤怠申請不可)
	 */
	@Override
	public boolean isAttendanceAppliable() {
		// 勤務日でない場合
		if (!isWorkDay()) {
			// 未申請でないと判断
			return false;
		}
		// 勤怠申請がされている場合
		if (isAttendanceApplied()) {
			// 勤怠未申請でない
			return false;
		}
		// 残業申請がされている場合
		if (isOvertimeApplied(false)) {
			return false;
		}
		// 休暇申請がされている場合
		if (isHolidayApplied(false)) {
			return false;
		}
		// 代休申請がされている場合
		if (isSubHolidayApplied(false)) {
			return false;
		}
		// 休日出勤申請がされている場合
		if (isWorkOnHolidayHolidayApplied(false)) {
			return false;
		}
		// 振替休日申請がされている場合
		if (isSubstituteApplied(false)) {
			return false;
		}
		// 時差出勤申請がされている場合
		if (isDifferenceApplied(false)) {
			return false;
		}
		// 勤務形態変更申請がされている場合
		if (isWorkTypeChangeApplied(false)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 残業申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isOvertimeApplied(boolean isContainCompleted) {
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : overtimeRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 休暇申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isHolidayApplied(boolean isContainCompleted) {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 代休申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isSubHolidayApplied(boolean isContainCompleted) {
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 休日出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isWorkOnHolidayHolidayApplied(boolean isContainCompleted) {
		// 休日出勤申請有無
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(workOnHolidayRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 解除申請(取下希望)である場合
		if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 振替休日申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isSubstituteApplied(boolean isContainCompleted) {
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : substituteList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 時差出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isDifferenceApplied(boolean isContainCompleted) {
		// 時差出勤申請有無
		if (differenceRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(differenceRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 解除申請(取下希望)である場合
		if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 勤務形態変更申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	@Override
	public boolean isWorkTypeChangeApplied(boolean isContainCompleted) {
		// 勤務形態変更申請有無
		if (workTypeChangeRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(workTypeChangeRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
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
	@Override
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList(boolean isCompleted) {
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
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @return targetDate
	 */
	@Override
	public Date getTargetDate() {
		return CapsuleUtility.getDateClone(targetDate);
	}
	
	/**
	 * @param holidayRequestList セットする holidayRequestList
	 */
	@Override
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList) {
		this.holidayRequestList = holidayRequestList;
	}
	
	/**
	 * @param overtimeRequestList セットする overTimeRequestList
	 */
	@Override
	public void setOverTimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList) {
		this.overtimeRequestList = overtimeRequestList;
	}
	
	/**
	 * @param substituteList セットする substituteList
	 */
	@Override
	public void setSubstituteList(List<SubstituteDtoInterface> substituteList) {
		this.substituteList = substituteList;
	}
	
	/**
	 * @param subHolidayRequestList セットする subHolidayRequestList
	 */
	@Override
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList) {
		this.subHolidayRequestList = subHolidayRequestList;
	}
	
	/**
	 * @param workOnHolidayRequestDto セットする workOnHolidayRequestDto
	 */
	@Override
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
	@Override
	public DifferenceRequestDtoInterface getDifferenceRequestDto(boolean isCompleted) {
		return (DifferenceRequestDtoInterface)getRequestDto(differenceRequestDto, isCompleted);
	}
	
	/**
	 * @param differenceRequestDto セットする differenceRequestDto
	 */
	@Override
	public void setDifferenceRequestDto(DifferenceRequestDtoInterface differenceRequestDto) {
		this.differenceRequestDto = differenceRequestDto;
	}
	
	/**
	 * @param workTypeChangeRequestDto セットする workTypeChangeRequestDto
	 */
	@Override
	public void setWorkTypeChangeRequestDto(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto) {
		this.workTypeChangeRequestDto = workTypeChangeRequestDto;
	}
	
	/**
	 * @return attendanceDto
	 */
	@Override
	public AttendanceDtoInterface getAttendanceDto() {
		return attendanceDto;
	}
	
	/**
	 * @param attendanceDto セットする attendanceDto
	 */
	@Override
	public void setAttendanceDto(AttendanceDtoInterface attendanceDto) {
		this.attendanceDto = attendanceDto;
	}
	
	/**
	 * @param workflowMap セットする workflowMap
	 */
	@Override
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap) {
		this.workflowMap = workflowMap;
	}
	
	/**
	 * @param scheduledWorkTypeCode セットする scheduledWorkTypeCode
	 */
	@Override
	public void setScheduledWorkTypeCode(String scheduledWorkTypeCode) {
		this.scheduledWorkTypeCode = scheduledWorkTypeCode;
	}
	
	/**
	 * @param substitutedWorkTypeCode セットする substitutedWorkTypeCode
	 */
	@Override
	public void setSubstitutedWorkTypeCode(String substitutedWorkTypeCode) {
		this.substitutedWorkTypeCode = substitutedWorkTypeCode;
	}
	
}
