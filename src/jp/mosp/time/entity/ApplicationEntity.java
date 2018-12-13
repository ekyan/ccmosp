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

import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 設定適用エンティティクラス。
 */
public class ApplicationEntity {
	
	/**
	 * ポータル出退勤ボタン表示コード(非表示)。
	 */
	public static final int				CODE_TIME_BUTTON_NONE	= 9;
	
	/**
	 * ポータル休憩ボタン表示コード(非表示)。
	 */
	public static final int				CODE_REST_BUTTON_NONE	= 2;
	
	/**
	 * 丸め区分(無し)。
	 */
	public static final int				CODE_ROUND_TYPE_NONE	= 0;
	
	/**
	 * 丸め区分(切捨て)。
	 */
	public static final int				CODE_ROUND_TYPE_DOWN	= 1;
	
	/**
	 * 丸め区分(切上げ)。
	 */
	public static final int				CODE_ROUND_TYPE_UP		= 2;
	
	/**
	 * 設定適用情報。<br>
	 */
	protected ApplicationDtoInterface	applicationDto;
	
	/**
	 * 勤怠設定情報。<br>
	 */
	protected TimeSettingDtoInterface	timeSettingDto;
	
	/**
	 * 締日情報。<br>
	 */
	protected CutoffDtoInterface		cutoffDto;
	
	/**
	 * 有給休暇設定情報。<br>
	 */
	protected PaidHolidayDtoInterface	paidHolidayDto;
	
	
	/**
	 * コンストラクタ。<br>
	 * @param applicationDto 設定適用情報
	 */
	public ApplicationEntity(ApplicationDtoInterface applicationDto) {
		// フィールドに設定
		this.applicationDto = applicationDto;
	}
	
	/**
	 * 設定適用エンティティが有効であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：有効である、false：無効である)
	 */
	public boolean isValid() {
		// 設定適用情報が存在しない場合
		if (applicationDto == null) {
			// 無効であると判断
			return false;
		}
		// 勤怠設定情報が存在しない場合
		if (timeSettingDto == null) {
			// 無効であると判断
			return false;
		}
		// 締日情報が存在しない場合
		if (cutoffDto == null) {
			// 無効であると判断
			return false;
		}
		// 有給休暇情報が存在しない場合
		if (paidHolidayDto == null) {
			// 無効であると判断
			return false;
		}
		// 必要な情報が揃っている場合
		return true;
	}
	
	/**
	 * 勤怠設定コードを取得する。<br>
	 * 勤怠設定コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤怠設定コード
	 */
	public String getWorkSettingCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getWorkSettingCode())) {
			return "";
		}
		// 勤怠設定コード取得
		return applicationDto.getWorkSettingCode();
	}
	
	/**
	 * カレンダコードを取得する。<br>
	 * カレンダコードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return カレンダコード
	 */
	public String getScheduleCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getScheduleCode())) {
			return "";
		}
		// カレンダコード取得
		return applicationDto.getScheduleCode();
	}
	
	/**
	 * 有給休暇設定コードを取得する。<br>
	 * 有給休暇設定コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return カレンダコード
	 */
	public String getPaidHolidayCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getPaidHolidayCode())) {
			return "";
		}
		// 有給休暇設定コードを取得
		return applicationDto.getPaidHolidayCode();
	}
	
	/**
	 * 締日コードを取得する。<br>
	 * 締日コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤怠設定コード
	 */
	public String getCutoffCode() {
		// 勤怠設定情報確認
		if (timeSettingDto == null || timeSettingDto.getCutoffCode() == null) {
			return "";
		}
		// 締日コード取得
		return timeSettingDto.getCutoffCode();
	}
	
	/**
	 * 締日を取得する。<br>
	 * <br>
	 * 締日が存在しない場合は、0(月末締)を返す。<br>
	 * <br>
	 * @return 締日
	 */
	public int getCutoffDate() {
		// 締日情報を確認
		if (cutoffDto == null) {
			return TimeConst.CUTOFF_DATE_LAST_DAY;
		}
		// 締日を取得
		return cutoffDto.getCutoffDate();
	}
	
	/**
	 * 勤怠管理対象であるかを確認する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、勤怠管理対象でないと判断する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠管理対象である、false：そうでない)
	 */
	public boolean isTimeManaged() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return false;
		}
		// 勤怠管理対象が有効であるかを確認
		return PlatformUtility.isActivate(timeSettingDto.getTimeManagementFlag());
	}
	
	/**
	 * 週の起算曜日を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、1(日曜)を返す。<br>
	 * <br>
	 * @return 週の起算曜日
	 */
	public int getStartDayOfWeek() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return Calendar.SUNDAY;
		}
		// 週の起算曜日を取得
		return timeSettingDto.getStartWeek();
	}
	
	/**
	 * 未承認仮締を取得する。<br>
	 * <br>
	 * 締日が存在しない場合は、0(有効)を返す。<br>
	 * <br>
	 * @return 未承認仮締
	 */
	public int getNoApproval() {
		// 締日情報を確認
		if (cutoffDto == null) {
			return TimeConst.CODE_NO_APPROVAL_VALID;
		}
		// 未承認仮締を取得
		return cutoffDto.getNoApproval();
	}
	
	/**
	 * 勤務予定時間表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 勤務予定時間表示設定（true：勤務予定時間表示有効、false：無効)
	 */
	public boolean useScheduledTime() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return false;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * 自己月締が有効であるかを確認する。<br>
	 * <br>
	 * 締日情報から自己仮締を取得し、確認する。
	 * 締日情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：自己月締有効、false：無効)
	 */
	public boolean isSelfTightening() {
		// 締日情報を確認
		if (cutoffDto == null) {
			return false;
		}
		// 自己月締設定を取得
		return cutoffDto.getSelfTightening() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * ポータル出退勤ボタン表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、9(非表示)を返す。<br>
	 * <br>
	 * @return ポータル出退勤ボタン表示設定
	 */
	public int getPortalTimeButtons() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return CODE_TIME_BUTTON_NONE;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getPortalTimeButtons();
	}
	
	/**
	 * ポータル休憩ボタン表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、2(非表示)を返す。<br>
	 * <br>
	 * @return ポータル休憩ボタン表示設定
	 */
	public int getPortalRestButtons() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return CODE_TIME_BUTTON_NONE;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getPortalRestButtons();
	}
	
	/**
	 * 始業時刻(丸め)を取得する。<br>
	 * 対象時刻を、日出勤丸め設定で丸める。<br>
	 * @param targetTime 対象時刻
	 * @return 始業時刻(丸め)
	 */
	public Date getRoundedStartTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		// 始業時刻(丸め)を取得
		return getRoundedTime(targetTime, timeSettingDto.getRoundDailyStartUnit(), timeSettingDto.getRoundDailyStart());
	}
	
	/**
	 * 実始業時刻(丸め)を取得する。<br>
	 * 対象時刻を、日出勤丸め設定で丸める。<br>
	 * @param targetTime 対象時刻
	 * @return 実始業時刻(丸め)
	 */
	public Date getRoundedActualStartTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		if (timeSettingDto.getRoundDailyStartUnit() == 1 && timeSettingDto.getRoundDailyStart() == CODE_ROUND_TYPE_UP) {
			// 1分単位切上げの場合
			return getRoundedTime(targetTime, timeSettingDto.getRoundDailyStartUnit(),
					timeSettingDto.getRoundDailyStart());
		}
		// 実始業時刻(丸め)を取得
		return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
	}
	
	/**
	 * 終業時刻(丸め)を取得する。<br>
	 * 対象時刻を、日退勤丸め設定で丸める。<br>
	 * @param targetTime 対象時刻
	 * @return 終業時刻(丸め)
	 */
	public Date getRoundedEndTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		// 終業時刻(丸め)を取得
		return getRoundedTime(targetTime, timeSettingDto.getRoundDailyEndUnit(), timeSettingDto.getRoundDailyEnd());
	}
	
	/**
	 * 実終業時刻(丸め)を取得する。<br>
	 * 対象時刻を、日退勤丸め設定で丸める。<br>
	 * @param targetTime 対象時刻
	 * @return 実終業時刻(丸め)
	 */
	public Date getRoundedActualEndTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		if (timeSettingDto.getRoundDailyEndUnit() == 1 && timeSettingDto.getRoundDailyEnd() == CODE_ROUND_TYPE_UP) {
			// 1分単位切上げの場合
			return getRoundedTime(targetTime, timeSettingDto.getRoundDailyEndUnit(), timeSettingDto.getRoundDailyEnd());
		}
		// 実終業時刻(丸め)を取得
		return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
	}
	
	/**
	 * 休憩入時刻(丸め)を取得する。<br>
	 * @param targetTime 対象時刻
	 * @return 休憩入時刻(丸め)
	 */
	public Date getRoundedRestStartTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		if (timeSettingDto.getRoundDailyRestStartUnit() == 1
				&& timeSettingDto.getRoundDailyRestStart() == CODE_ROUND_TYPE_UP) {
			// 1分単位切上げの場合
			return getRoundedTime(targetTime, timeSettingDto.getRoundDailyRestStartUnit(),
					timeSettingDto.getRoundDailyRestStart());
		}
		// 休憩入時刻(丸め)を取得
		return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
	}
	
	/**
	 * 休憩戻時刻(丸め)を取得する。<br>
	 * @param targetTime 対象時刻
	 * @return 休憩戻時刻(丸め)
	 */
	public Date getRoundedRestEndTime(Date targetTime) {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
		}
		if (timeSettingDto.getRoundDailyRestEndUnit() == 1
				&& timeSettingDto.getRoundDailyRestEnd() == CODE_ROUND_TYPE_UP) {
			// 1分単位切上げの場合
			return getRoundedTime(targetTime, timeSettingDto.getRoundDailyRestEndUnit(),
					timeSettingDto.getRoundDailyRestEnd());
		}
		// 休憩戻時刻(丸め)を取得
		return getRoundedTime(targetTime, 1, CODE_ROUND_TYPE_DOWN);
	}
	
	/**
	 * 丸めた時刻を取得する。<br>
	 * @param targetTime 対象時刻
	 * @param roundUnit  丸め単位
	 * @param roundType  丸め区分
	 * @return 丸めた時刻
	 */
	protected Date getRoundedTime(Date targetTime, int roundUnit, int roundType) {
		if (targetTime == null) {
			return null;
		}
		long milliseconds = targetTime.getTime();
		if (milliseconds <= 0) {
			return targetTime;
		}
		// 0ミリ秒より大きい場合
		int oneMinute = TimeConst.CODE_DEFINITION_HOUR * 1000;
		// 1分未満の端数
		long fraction = milliseconds % oneMinute;
		// 端数を切り捨てる
		milliseconds -= fraction;
		if (fraction > 0 && roundType == CODE_ROUND_TYPE_UP && roundUnit == 1) {
			// 端数が0ミリ秒より大きく且つ1分単位切上げの場合
			milliseconds += oneMinute;
		}
		if (roundType == CODE_ROUND_TYPE_NONE || roundUnit <= 0) {
			// 丸めた時刻を取得
			return new Date(milliseconds);
		}
		// 丸め単位を分単位からミリ秒単位に変換
		int millisecondsUnit = roundUnit * TimeConst.CODE_DEFINITION_HOUR * 1000;
		// 丸めた値(切捨て)を取得
		long rounded = milliseconds - (milliseconds % millisecondsUnit);
		// 切上げの場合
		if (roundType == CODE_ROUND_TYPE_UP && milliseconds % millisecondsUnit > 0) {
			// 切捨て値+丸め単位
			rounded += millisecondsUnit;
		}
		// 丸めた時刻を取得
		return new Date(rounded);
	}
	
	/**
	 * ポータル出退勤ボタン表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、9(非表示)を返す。<br>
	 * <br>
	 * @return ポータル出退勤ボタン表示設定
	 */
	public String getProspectsMonths() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return MospConst.CHECKBOX_OFF;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getProspectsMonths();
	}
	
	/**
	 * @return timeSettingDto
	 */
	public TimeSettingDtoInterface getTimeSettingDto() {
		return timeSettingDto;
	}
	
	/**
	 * @param timeSettingDto セットする timeSettingDto
	 */
	public void setTimeSettingDto(TimeSettingDtoInterface timeSettingDto) {
		this.timeSettingDto = timeSettingDto;
	}
	
	/**
	 * @return cutoffDto
	 */
	public CutoffDtoInterface getCutoffDto() {
		return cutoffDto;
	}
	
	/**
	 * @param cutoffDto セットする cutoffDto
	 */
	public void setCutoffDto(CutoffDtoInterface cutoffDto) {
		this.cutoffDto = cutoffDto;
	}
	
	/**
	 * @return paidHolidayDto
	 */
	public PaidHolidayDtoInterface getPaidHolidayDto() {
		return paidHolidayDto;
	}
	
	/**
	 * @param paidHolidayDto セットする paidHolidayDto
	 */
	public void setPaidHolidayDto(PaidHolidayDtoInterface paidHolidayDto) {
		this.paidHolidayDto = paidHolidayDto;
	}
	
}
