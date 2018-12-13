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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;

import jp.mosp.framework.base.MospException;

/**
 * 予定確認データ計算インターフェース。
 */
public interface ScheduleReferenceTotalBeanInterface {
	
	/**
	 * 勤務時間の合計取得。
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 勤務時間合計値
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTotalWorkTime(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 休憩時間の合計取得。
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 休憩時間合計
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTotalRestTime(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 合計出勤日数の取得。
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 合計出勤日数の合計
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTimesWorkViews(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 合計法定休日日数の合計取得。
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 合計法定休日日数の合計
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTimesLegalHolidayViews(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 合計所定休日日数の合計取得。
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 合計所定休日日数の合計
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTimesSpecificHolidayViews(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
}
