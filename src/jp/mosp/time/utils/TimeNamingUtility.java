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
package jp.mosp.time.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.utils.PlatformNamingUtility;
import jp.mosp.time.input.vo.AttendanceListVo;
import jp.mosp.time.input.vo.OvertimeRequestVo;

/**
 * 名称に関するユーティリティクラス。<br>
 * <br>
 * 勤怠管理システムにおいて作成される名称は、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class TimeNamingUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeNamingUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠一覧画面
	 */
	public static String attendanceListScreen(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName(AttendanceListVo.class.getName()));
		sb.append(PlatformNamingUtility.screen(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業申請画面
	 */
	public static String overtimeRequestScreen(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName(OvertimeRequestVo.class.getName()));
		sb.append(PlatformNamingUtility.screen(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return .
	 */
	public static String halfPeriod(MospParams mospParams) {
		return mospParams.getName("HalfPeriod");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 日数
	 */
	public static String days(MospParams mospParams) {
		return mospParams.getName("Days");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定休日
	 */
	public static String prescribedHoliday(MospParams mospParams) {
		return mospParams.getName("PrescribedHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定休日
	 */
	public static String legalHoliday(MospParams mospParams) {
		return mospParams.getName("LegalHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定振替休日
	 */
	public static String legalTransferHoliday(MospParams mospParams) {
		return mospParams.getName("LegalTransferHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定振替休日
	 */
	public static String prescribedTransferHoliday(MospParams mospParams) {
		return mospParams.getName("PrescribedTransferHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示年度
	 */
	public static String displayFiscalYear(MospParams mospParams) {
		return mospParams.getName("Display", "FiscalYear");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 限度
	 */
	public static String limit(MospParams mospParams) {
		return mospParams.getName("Limit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 注意
	 */
	public static String caution(MospParams mospParams) {
		return mospParams.getName("Caution");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 警告
	 */
	public static String warning(MospParams mospParams) {
		return mospParams.getName("Warning");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間外
	 */
	public static String outOfTime(MospParams mospParams) {
		return mospParams.getName("OutOfTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 外残
	 */
	public static String overtimeOutAbbr(MospParams mospParams) {
		return mospParams.getName("LeftOut");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠設定
	 */
	public static String timeSetting(MospParams mospParams) {
		return mospParams.getName("WorkManage", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠設定コード
	 */
	public static String timeSettingCode(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(timeSetting(mospParams));
		sb.append(PlatformNamingUtility.code(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 週の起算曜日
	 */
	public static String startDayOfTheWeek(MospParams mospParams) {
		return mospParams.getName("StartDayOfTheWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 曜日
	 */
	public static String dayOfTheWeek(MospParams mospParams) {
		return mospParams.getName("DayOfTheWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1週間限度時間
	 */
	public static String oneWeekLimitTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneWeek(mospParams));
		sb.append(limit(mospParams));
		sb.append(PlatformNamingUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月限度時間
	 */
	public static String oneMonthLimitTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(limit(mospParams));
		sb.append(PlatformNamingUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月注意時間
	 */
	public static String oneMonthAttentionTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(caution(mospParams));
		sb.append(PlatformNamingUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月警告時間
	 */
	public static String oneMonthWarningTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(warning(mospParams));
		sb.append(PlatformNamingUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1週間
	 */
	public static String oneWeek(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(mospParams.getName("No1"));
		sb.append(PlatformNamingUtility.amountWeek(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月
	 */
	public static String oneMonth(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(mospParams.getName("No1"));
		sb.append(PlatformNamingUtility.amountMonth(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請可能時間
	 */
	public static String applicableTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("Application", "Possible"));
		sb.append(PlatformNamingUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇種別
	 */
	public static String holidayType(MospParams mospParams) {
		return mospParams.getName("Vacation", "Classification");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇年月日
	 */
	public static String holidayDate(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(getVacation(mospParams));
		sb.append(PlatformNamingUtility.yearMonthDay(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇範囲
	 */
	public static String holidayRange(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(getVacation(mospParams));
		sb.append(PlatformNamingUtility.range(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半休
	 */
	public static String halfHoliday(MospParams mospParams) {
		return mospParams.getName("HalfTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間休
	 */
	public static String hourlyHoliday(MospParams mospParams) {
		return mospParams.getName("HolidayTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 同
	 */
	public static String same(MospParams mospParams) {
		return mospParams.getName("Same");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return に
	 */
	public static String in(MospParams mospParams) {
		return mospParams.getName("In");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return と
	 */
	public static String and(MospParams mospParams) {
		return mospParams.getName("And");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return または
	 */
	public static String or(MospParams mospParams) {
		return mospParams.getName("Or");
	}
	
	/**
	 * 遅名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 遅
	 */
	public static String getLateAbbrNaming(MospParams mospParams) {
		return mospParams.getName("LateAbbr");
	}
	
	/**
	 * 早名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 早
	 */
	public static String getEarlyAbbrNaming(MospParams mospParams) {
		return mospParams.getName("EarlyAbbr");
	}
	
	/**
	 * 未申請名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 未申請
	 */
	public static String getNotApplied(MospParams mospParams) {
		return mospParams.getName("Ram", "Application");
	}
	
	/**
	 * 未承認名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 未承認
	 */
	public static String getNotApproved(MospParams mospParams) {
		return mospParams.getName("Ram", "Approval");
	}
	
	/**
	 * 差戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 差戻
	 */
	public static String getReverted(MospParams mospParams) {
		return mospParams.getName("SendingBack");
	}
	
	/**
	 * 1次戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 1次戻
	 */
	public static String getFirstReverted(MospParams mospParams) {
		return mospParams.getName("No1", "Following", "Back");
	}
	
	/**
	 * 勤怠名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 勤怠
	 */
	public static String getWorkManage(MospParams mospParams) {
		return mospParams.getName("WorkManage");
	}
	
	/**
	 * 残業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 残業
	 */
	public static String getOvertimeWork(MospParams mospParams) {
		return mospParams.getName("OvertimeWork");
	}
	
	/**
	 * 休暇名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休暇
	 */
	public static String getVacation(MospParams mospParams) {
		return mospParams.getName("Vacation");
	}
	
	/**
	 * 振出休出名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 振出休出
	 */
	public static String getWorkOnHoliday(MospParams mospParams) {
		return mospParams.getName("SubstituteWorkAbbr", "WorkingHoliday");
	}
	
	/**
	 * 代休名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 代休
	 */
	public static String getSubHoliday(MospParams mospParams) {
		return mospParams.getName("CompensatoryHoliday");
	}
	
	/**
	 * 勤務形態名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 勤務形態
	 */
	public static String getWorkType(MospParams mospParams) {
		return mospParams.getName("Work", "Form");
	}
	
	/**
	 * 時差名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時差
	 */
	public static String getTimeDifference(MospParams mospParams) {
		return mospParams.getName("TimeDifference");
	}
	
}
