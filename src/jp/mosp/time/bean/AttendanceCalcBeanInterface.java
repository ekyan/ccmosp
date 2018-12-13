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
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 自動計算インターフェース。
 */
public interface AttendanceCalcBeanInterface {
	
	/**
	 * 日々の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void attendanceCalc(AttendanceDtoInterface attendanceDto) throws MospException;
	
	/**
	 * 日々の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @param restList 休憩リスト
	 * @param publicGoOutList 公用外出リスト
	 * @param privateGoOutList 私用外出リスト
	 * @param minutelyHolidayAList 分単位休暇Aリスト
	 * @param minutelyHolidayBList 分単位休暇Bリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void attendanceCalc(AttendanceDtoInterface attendanceDto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> publicGoOutList, List<GoOutDtoInterface> privateGoOutList,
			List<GoOutDtoInterface> minutelyHolidayAList, List<GoOutDtoInterface> minutelyHolidayBList)
			throws MospException;
	
	/**
	 * 始業時刻及び終業時刻の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @param useBetweenTime 前半休と後半休の間の時間を使う場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void calcStartEndTime(AttendanceDtoInterface attendanceDto, boolean useBetweenTime) throws MospException;
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	Date getRoundMinute(Date time, int type, int unit);
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	int getRoundMinute(int time, int type, int unit);
	
	/**
	 * 日付オブジェクトの差を時間(分)で取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 日付オブジェクトの差(分)
	 */
	int getDefferenceMinutes(Date startTime, Date endTime);
	
	/**
	 * 勤怠時刻文字列を日付オブジェクトに変換し取得する。<br>
	 * 基準日は、変換の際に基準として用いる。<br>
	 * @param date 基準日
	 * @param hour 時間文字列
	 * @param minute 分文字列
	 * @return 日付オブジェクト
	 */
	Date getAttendanceTime(Date date, String hour, String minute);
	
}
