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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態エンティティクラス。<br>
 */
public class WorkTypeEntity implements WorkTypeEntityInterface {
	
	/**
	 * 給与区分(有給)。<br>
	 */
	public static final String					CODE_PAY_TYPE_PAY	= "0";
	
	/**
	 * 勤務形態情報。<br>
	 */
	protected WorkTypeDtoInterface				workTypeDto;
	
	/**
	 * 勤務形態項目情報リスト。<br>
	 */
	protected List<WorkTypeItemDtoInterface>	itemDtoList;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public WorkTypeEntity() {
	}
	
//	/**
//	 * コンストラクタ。<br>
//	 * @param workTypeDto 勤務形態情報
//	 * @param itemDtoList 勤務形態項目情報リスト
//	 */
//	public WorkTypeEntity(WorkTypeDtoInterface workTypeDto, List<WorkTypeItemDtoInterface> itemDtoList) {
//		// フィールドに設定
//		this.workTypeDto = workTypeDto;
//		this.itemDtoList = itemDtoList;
//	}
//	
	@Override
	public void setWorkTypeDto(WorkTypeDtoInterface workTypeDto) {
		this.workTypeDto = workTypeDto;
	}
	
	@Override
	public void setWorkTypeItemList(List<WorkTypeItemDtoInterface> itemDtoList) {
		this.itemDtoList = itemDtoList;
	}

	
	/**
	 * 勤務形態略称を取得する。<br>
	 * 勤務形態略称が未設定の場合は、空文字を返す。<br>
	 * @return 勤務形態略称
	 */
	@Override
	public String getWorkTypeAbbr() {
		// 勤務形態情報確認
		if (workTypeDto == null || workTypeDto.getWorkTypeAbbr() == null) {
			return "";
		}
		// 勤務形態略称取得
		return workTypeDto.getWorkTypeAbbr();
	}
	
	/**
	 * 始業時刻を取得する。<br>
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getStartWorkTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORKSTART);
	}
	
	/**
	 * 終業時刻を取得する。<br>
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getEndWorkTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORKEND);
	}
	
	/**
	 * 勤務時間(分)を取得する。<br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public int getWorkTime() throws MospException {
		return getItemMinutes(TimeConst.CODE_WORKTIME);
	}
	
	/**
	 * 休憩時間(分)を取得する。<br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public int getRestTime() throws MospException {
		return getItemMinutes(TimeConst.CODE_RESTTIME);
	}
	
	/**
	 * 休憩1開始時刻を取得する。<br>
	 * @return 休憩1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getRest1StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_RESTSTART1);
	}
	
	/**
	 * 休憩1終了時刻を取得する。<br>
	 * @return 休憩1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getRest1EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_RESTEND1);
	}
	
	/**
	 * 前半休開始時刻を取得する。<br>
	 * <br>
	 * 後半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 前半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getFrontStartTime() throws MospException {
		return getItemValue(TimeConst.CODE_FRONTSTART);
	}
	
	/**
	 * 前半休終了時刻を取得する。<br>
	 * <br>
	 * 後半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 前半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getFrontEndTime() throws MospException {
		return getItemValue(TimeConst.CODE_FRONTEND);
	}
	
	/**
	 * 後半休開始時刻を取得する。<br>
	 * <br>
	 * 前半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 後半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getBackStartTime() throws MospException {
		return getItemValue(TimeConst.CODE_BACKSTART);
	}
	
	/**
	 * 後半休終了時刻を取得する。<br>
	 * <br>
	 * 前半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 後半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getBackEndTime() throws MospException {
		return getItemValue(TimeConst.CODE_BACKEND);
	}
	
	/**
	 * 直行かどうかを確認する。<br>
	 * @return 確認結果(true：直行、false：直行でない)
	 */
	@Override
	public boolean isDirectStart() {
		// 勤務形態項目値(予備)がチェックされているかを確認
		return isChecked(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START);
	}
	
	/**
	 * 直帰かどうかを確認する。<br>
	 * @return 確認結果(true：直帰、false：直帰でない)
	 */
	@Override
	public boolean isDirectEnd() {
		// 勤務形態項目値(予備)がチェックされているかを確認
		return isChecked(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END);
	}
	
	/**
	 * 割増休憩除外が有効であるかを確認する。<br>
	 * @return 確認結果(true：割増休憩除外が有効である、false：有効でない)
	 */
	@Override
	public boolean isNightRestExclude() {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST,
				String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
	}
	
	/**
	 * 時短時間1開始時刻を取得する。<br>
	 * @return 時短時間1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort1StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
	}
	
	/**
	 * 時短時間1終了時刻を取得する。<br>
	 * @return 時短時間1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort1EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
	}
	
	/**
	 * 時短時間1給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間1給与区分が有給、false：無給)
	 */
	@Override
	public boolean isShort1TypePay() {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, CODE_PAY_TYPE_PAY);
	}
	
	/**
	 * 時短時間1が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間1が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort1TimeSet() throws MospException {
		return isItemValueSet(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
	}
	
	/**
	 * 時短時間2開始時刻を取得する。<br>
	 * @return 時短時間2開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort2StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
	}
	
	/**
	 * 時短時間2終了時刻を取得する。<br>
	 * @return 時短時間2終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort2EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
	}
	
	/**
	 * 時短時間2給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間2給与区分が有給、false：無給)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort2TypePay() throws MospException {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, CODE_PAY_TYPE_PAY);
	}
	
	/**
	 * 時短時間2が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間2が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort2TimeSet() throws MospException {
		return isItemValueSet(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
	}
	
	/**
	 * 勤務対象勤務形態であるかを確認する。<br>
	 * 未設定(休暇等)、所定休日、法定休日の場合は、勤務対象勤務形態でないと判断する。<br>
	 * @return 確認結果(true：勤務対象勤務形態である、false：そうでない。)
	 */
	@Override
	public boolean isWorkTypeForWork() {
		// 勤務形態コード確認
		if (getWorkTypeCode().isEmpty() || TimeUtility.isHoliday(getWorkTypeCode())) {
			// 未設定(休暇等)、所定休日、法定休日の場合
			return false;
		}
		return true;
	}
	
	/**
	 * 始業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、始業時刻を勤務日の時刻として算出する。<br>
	 * 各種申請は、申請済(下書、取下、一次戻以外)を対象とする。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、残業申請及び時差出勤申請は、考慮しない。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.前半休の場合：<br>
	 * 勤務形態の後半休開始時刻を返す。<br>
	 * 但し、残業申請(勤務前残業)がある場合は、そこから残業申請(勤務前残業)の
	 * 申請時間(分)を減算する(前半休の場合は前半休終了時刻が前残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の出勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間1が設定されている場合：<br>
	 * 時短時間1終了時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の終了時刻を返す。<br>
	 * 時短時間1(無給)が設定されている場合は、時短時間1終了時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の始業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の終了時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の始業時刻を返す。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getStartTime(RequestEntity requestEntity) throws MospException {
		// 承認済フラグ準備(false：申請済申請を考慮)
		boolean isCompleted = false;
		// 勤務日を取得
		Date targetDate = requestEntity.getTargetDate();
		// 残業申請(勤務前残業)の申請時間(分)を取得
		int overtimeMinutes = requestEntity.getOvertimeMinutesBeforeWork(isCompleted);
		// 振出・休出申請(休出申請)の出勤予定時刻を取得
		Date workOnHolidayStartTime = requestEntity.getWorkOnHolidayStartTime(isCompleted);
		// 1.全休の場合
		if (requestEntity.isAllHoliday(isCompleted)) {
			return null;
		}
		// 2.前半休の場合
		if (requestEntity.isAmHoliday(isCompleted)) {
			// 勤務形態の後半休開始時刻を取得
			Date startTime = getBackStartTime();
			// 始業時刻から残業申請(勤務前残業)の申請時間(分)を減算
			startTime = DateUtility.addMinute(startTime, -overtimeMinutes);
			// 始業時刻と前半休終了時刻を比較
			Date frontEndTime = getFrontEndTime();
			if (startTime.before(frontEndTime)) {
				// 前半休終了時刻を設定(前半休の場合は前半休終了時刻が前残業の限度)
				startTime = frontEndTime;
			}
			// 勤務日時刻に調整
			return TimeUtility.getDateTime(targetDate, startTime);
		}
		// 3.振出・休出申請(休出申請)がある場合
		if (workOnHolidayStartTime != null) {
			// 勤務日時刻に調整(振出・休出申請(休出申請)の出勤予定時刻)
			return workOnHolidayStartTime;
		}
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = requestEntity.getHourlyHolidayFirstSequenceTimes();
		// 4.時短時間1が設定されている場合
		if (isShort1TimeSet()) {
			// 時短時間1終了時刻(勤務日時刻に調整)を取得
			Date short1EndTime = TimeUtility.getDateTime(targetDate, getShort1EndTime());
			// 時短時間1終了時刻と時間単位有給休暇が接する場合
			if (holidayTimeList.isEmpty() == false && holidayTimeList.get(0).compareTo(short1EndTime) == 0) {
				// 時間単位有給休暇の終了時刻を取得
				return holidayTimeList.get(1);
			}
			// 時短時間1(無給)が設定されている場合
			if (isShort1TypePay() == false) {
				// 時短時間1終了時刻を取得
				return short1EndTime;
			}
		}
		// 勤務形態の始業時刻(勤務日時刻に調整)を取得
		Date startTime = TimeUtility.getDateTime(targetDate, getStartWorkTime());
		// 5.勤務形態の始業時刻と時間単位有給休暇が接する場合
		if (holidayTimeList.isEmpty() == false && holidayTimeList.get(0).compareTo(startTime) == 0) {
			// 時間単位有給休暇の終了時刻を取得
			return holidayTimeList.get(1);
		}
		// 6.それ以外の場合
		// 勤務形態の始業時刻を取得
		return startTime;
	}
	
	/**
	 * 終業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、終業時刻を勤務日の時刻として算出する。<br>
	 * 各種申請は、申請済(下書、取下、一次戻以外)を対象とする。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、時差出勤申請は、考慮しない。<br>
	 * 全休の場合は、nullを返す。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.後半休の場合：<br>
	 * 勤務形態の前半休終了時刻を返す。<br>
	 * 但し、残業申請(勤務後残業)がある場合は、そこに残業申請(勤務後残業)の
	 * 申請時間(分)を加算する(後半休の場合は後半休開始時刻が後残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の退勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間2が設定されている場合：<br>
	 * 時短時間2開始時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の開始時刻を返す。<br>
	 * 時短時間2(無給)が設定されている場合は、時短時間2開始時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の終業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の開始時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の終業時刻を返す。<br>
	 * <br>
	 * @param requestEntity  申請エンティティ
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getEndTime(RequestEntity requestEntity) throws MospException {
		// 承認済フラグ準備(false：申請済申請を考慮)
		boolean isCompleted = false;
		// 勤務日を取得
		Date targetDate = requestEntity.getTargetDate();
		// 残業申請(勤務後残業)の申請時間(分)を取得
		int overtimeMinutes = requestEntity.getOvertimeMinutesAfterWork(isCompleted);
		// 振出・休出申請(休出申請)の退勤予定時刻を取得
		Date workOnHolidayEndTime = requestEntity.getWorkOnHolidayEndTime(isCompleted);
		// 1.全休の場合
		if (requestEntity.isAllHoliday(isCompleted)) {
			return null;
		}
		// 2.後半休の場合
		if (requestEntity.isPmHoliday(isCompleted)) {
			// 勤務形態の前半休終了時刻を取得
			Date endTime = getFrontEndTime();
			// 終業時刻に残業申請(勤務後残業)の申請時間(分)を加算
			endTime = DateUtility.addMinute(endTime, overtimeMinutes);
			// 終業時刻と後半休開始時刻を比較
			Date backStartTime = getBackStartTime();
			if (endTime.after(backStartTime)) {
				// 後半休終了時刻を設定(後半休の場合は後半休開始時刻が後残業の限度)
				endTime = backStartTime;
			}
			// 勤務日時刻に調整
			return TimeUtility.getDateTime(targetDate, endTime);
		}
		// 3.振出・休出申請(休出申請)がある場合
		if (workOnHolidayEndTime != null) {
			// 勤務日時刻に調整(振出・休出申請(休出申請)の出勤予定時刻)
			return workOnHolidayEndTime;
		}
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = requestEntity.getHourlyHolidayLastSequenceTimes();
		// 4.時短時間2が設定されている場合
		if (isShort2TimeSet()) {
			// 時短時間2開始時刻(勤務日時刻に調整)を取得
			Date short2StartTime = TimeUtility.getDateTime(targetDate, getShort2StartTime());
			// 時短時間2開始時刻と時間単位有給休暇が接する場合
			if (holidayTimeList.isEmpty() == false && holidayTimeList.get(1).compareTo(short2StartTime) == 0) {
				// 時間単位有給休暇の開始時刻を取得
				return holidayTimeList.get(0);
			}
			// 時短時間2(無給)が設定されている場合
			if (isShort2TypePay() == false) {
				// 時短時間1終了時刻を取得
				return short2StartTime;
			}
		}
		// 勤務形態の終業時刻(勤務日時刻に調整)を取得
		Date endTime = TimeUtility.getDateTime(targetDate, getEndWorkTime());
		// 5.勤務形態の終業時刻と時間単位有給休暇が接する場合
		if (holidayTimeList.isEmpty() == false && holidayTimeList.get(1).compareTo(endTime) == 0) {
			// 時間単位有給休暇の開始時刻を取得
			return holidayTimeList.get(0);
		}
		// 6.それ以外の場合
		// 勤務形態の終業時刻を取得
		return endTime;
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * 勤務形態コードが未設定(休暇等)の場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤務形態コード
	 */
	protected String getWorkTypeCode() {
		// 勤務形態情報確認
		if (workTypeDto == null || workTypeDto.getWorkTypeCode() == null) {
			return "";
		}
		// 勤務形態コード取得
		return workTypeDto.getWorkTypeCode();
	}
	
	/**
	 * 有効日を取得する。<br>
	 * 勤務形態情報が未設定の場合は、nullを返す。<br>
	 * <br>
	 * @return 有効日
	 */
	@Override
	public Date getActivateDate() {
		// 勤務形態情報確認
		if (workTypeDto == null) {
			return null;
		}
		// 勤務形態コード取得
		return workTypeDto.getActivateDate();
	}
	
	/**
	 * 勤務形態項目値を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、デフォルト時刻を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getItemValue(String workTypeItemCode) throws MospException {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return DateUtility.getDefaultTime();
		}
		// 勤務形態項目値を確認
		if (dto.getWorkTypeItemValue() == null) {
			// 勤務形態項目値が取得できなかった場合
			return DateUtility.getDefaultTime();
		}
		// 勤務形態項目値を取得
		return new Date(dto.getWorkTypeItemValue().getTime());
	}
	
	/**
	 * 勤務形態項目値(分)を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、0を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getItemMinutes(String workTypeItemCode) throws MospException {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return 0;
		}
		// 勤務形態項目値を確認
		if (dto.getWorkTypeItemValue() == null) {
			// 勤務形態項目値が取得できなかった場合
			return 0;
		}
		// 勤務形態項目値(分)を取得
		return TimeUtility.getMinutes(dto.getWorkTypeItemValue());
	}
	
	/**
	 * 勤務形態項目値(予備)を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、空文字列を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値(予備)
	 */
	protected String getItemPreliminary(String workTypeItemCode) {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return "";
		}
		// 勤務形態項目値(予備)を取得
		return dto.getPreliminary();
	}
	
	/**
	 * 勤務形態項目情報を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、nullを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目情報
	 */
	@Override
	public WorkTypeItemDtoInterface getWorkTypeItem(String workTypeItemCode) {
		// 勤務形態項目情報毎に確認
		for (WorkTypeItemDtoInterface dto : itemDtoList) {
			// 勤務形態項目コードが合致する場合
			if (dto.getWorkTypeItemCode().equals(workTypeItemCode)) {
				// 勤務形態項目情報を取得
				return dto;
			}
		}
		// 勤務形態項目情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 勤務形態項目値(予備)(CheckBox)がチェックされているかどうかを確認する。<br>
	 * 勤務形態項目値(予備)が取得できない場合は、falseを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	protected boolean isChecked(String workTypeItemCode) {
		// チェックされているかどうかを確認
		return isPreliminaryTheValue(workTypeItemCode, MospConst.CHECKBOX_ON);
	}
	
	/**
	 * 勤務形態項目値(予備)が、その値かどうかを確認する。<br>
	 * 勤務形態項目値(予備)が取得できない場合は、falseを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param theValue         その値
	 * @return 確認結果(true：その値である、false：その値でない)
	 */
	protected boolean isPreliminaryTheValue(String workTypeItemCode, String theValue) {
		// 勤務形態項目値(予備)を取得
		String preliminary = getItemPreliminary(workTypeItemCode);
		// 勤務形態項目値(予備)を確認
		if (preliminary.isEmpty()) {
			return false;
		}
		// 勤務形態項目値(予備)がその値であるかを確認
		return preliminary.equals(theValue);
	}
	
	/**
	 * 勤務形態項目(時刻～時刻)が設定されているかを確認する。<br>
	 * 勤務形態項目コード(From)及び勤務形態項目コード(To)の値が
	 * 共にデフォルト時刻であった場合、設定されていないと判定する。<br>
	 * @param fromItemCode 勤務形態項目コード(From)
	 * @param toItemCode   勤務形態項目コード(To)
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isItemValueSet(String fromItemCode, String toItemCode) throws MospException {
		// デフォルト時刻準備
		Date defaultTime = DateUtility.getDefaultTime();
		// 勤務形態項目確認
		if (getItemValue(fromItemCode).equals(defaultTime) && getItemValue(toItemCode).equals(defaultTime)) {
			// 勤務形態項目コード(From)及び勤務形態項目コード(To)の値が共にデフォルト時刻であった場合
			return false;
		}
		return true;
	}
	
}
