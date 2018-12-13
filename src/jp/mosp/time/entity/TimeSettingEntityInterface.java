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
import java.util.Map;

import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠設定エンティティインターフェース。<br>
 */
public interface TimeSettingEntityInterface {
	
	/**
	 * 限度基準期間(1週間)。<br>
	 */
	public static final String	TERM_ONE_WEEK	= "week1";
	
	/**
	 * 限度基準期間(1ヶ月)。<br>
	 */
	public static final String	TERM_ONE_MONTH	= "month1";
	
	
	/**
	 * 勤怠設定情報を取得する。<br>
	 * @return 勤怠設定情報
	 */
	TimeSettingDtoInterface getTimeSettingDto();
	
	/**
	 * 限度基準情報を取得する。<br>
	 * @param term 期間
	 * @return 限度基準情報
	 */
	LimitStandardDtoInterface getLimitStandardDto(String term);
	
	/**
	 * 勤怠設定情報を設定する。<br>
	 * @param dto 勤怠設定情報
	 */
	void setTimeSettingDto(TimeSettingDtoInterface dto);
	
	/**
	 * 限度基準情報群を設定する。<br>
	 * @param limits 限度基準情報群(キー：期間)
	 */
	void setLimitStandardDtos(Map<String, LimitStandardDtoInterface> limits);
	
	/**
	 * 勤怠設定コードを取得する。<br>
	 * @return 勤怠設定コード
	 */
	String getWorkSettingCode();
	
	/**
	 * 有効日を取得する。<br>
	 * @return 有効日
	 */
	Date getActivateDate();
	
	/**
	 * 勤怠設定情報が存在するかを確認する。<br>
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isExist();
	
	/**
	 * 限度基準情報が存在するかを確認する。<br>
	 * @param term 期間
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isLimitStandardExist(String term);
	
	/**
	 * 週の起算曜日を取得する。<br>
	 * @return 週の起算曜日
	 */
	int getStartWeek();
	
	/**
	 * 年の起算月を取得する。<br>
	 * @return 年の起算月
	 */
	int getStartMonthOfYear();
	
	/**
	 * 日早退丸めをする。<br>
	 * @param leaveEarly 日早退時間(分)
	 * @return 日早退時間(分)
	 */
	int roundDailyLeaveEarly(int leaveEarly);
	
	/**
	 * 1週間(week1)限度基準情報が存在するかを確認する。<br>
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isOneWeekExist();
	
	/**
	 * 1ヶ月(month1)限度基準情報が存在するかを確認する。<br>
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isOneMonthExist();
	
	/**
	 * 1週間(week1)時間外限度時間(分)を取得する。<br>
	 * @return 1週間(week1)時間外限度時間(分)
	 */
	int getOneWeekLimit();
	
	/**
	 * 1ヶ月(month1)時間外限度時間(分)を取得する。<br>
	 * @return 1ヶ月(month1)時間外限度時間(分)
	 */
	int getOneMonthLimit();
	
	/**
	 * 1ヶ月(month1)時間外注意時間(分)を取得する。<br>
	 * @return 1ヶ月(month1)時間外注意時間(分)
	 */
	int getOneMonthAttention();
	
	/**
	 * 1ヶ月(month1)時間外警告時間(分)を取得する。<br>
	 * @return 1ヶ月(month1)時間外警告時間(分)
	 */
	int getOneMonthWarning();
	
	/**
	 * 1週間(week1)時間外限度時間(時間文字列)を取得する。<br>
	 * @return 1週間(week1)時間外限度時間(時間文字列)
	 */
	String getOneWeekLimitHours();
	
	/**
	 * 1週間(week1)時間外限度時間(分文字列)を取得する。<br>
	 * @return 1週間(week1)時間外限度時間(分文字列)
	 */
	String getOneWeekLimitMinutes();
	
	/**
	 * 1ヶ月(month1)時間外限度時間(時間文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外限度時間(時間文字列)
	 */
	String getOneMonthLimitHours();
	
	/**
	 * 1ヶ月(month1)時間外限度時間(分文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外限度時間(分文字列)
	 */
	String getOneMonthLimitMinutes();
	
	/**
	 * 1ヶ月(month1)時間外注意時間(時間文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外注意時間(時間文字列)
	 */
	String getOneMonthAttentionHours();
	
	/**
	 * 1ヶ月(month1)時間外注意時間(分文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外注意時間(分文字列)
	 */
	String getOneMonthAttentionMinutes();
	
	/**
	 * 1ヶ月(month1)時間外警告時間(時間文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外警告時間(時間文字列)
	 */
	String getOneMonthWarningHours();
	
	/**
	 * 1ヶ月(month1)時間外警告時間(分文字列)を取得する。<br>
	 * @return 1ヶ月(month1)時間外警告時間(分文字列)
	 */
	String getOneMonthWarningMinutes();
	
	/**
	 * 時間外限度時間(分)を取得する。<br>
	 * @param term 期間
	 * @return 時間外限度時間(分)
	 */
	int getLimit(String term);
	
	/**
	 * 時間外限度時間(時間文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外限度時間(時間文字列)
	 */
	String getLimitHours(String term);
	
	/**
	 * 時間外限度時間(分文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外限度時間(分文字列)
	 */
	String getLimitMinutes(String term);
	
	/**
	 * 時間外注意時間(分)を取得する。<br>
	 * @param term 期間
	 * @return 時間外注意時間(分)
	 */
	int getAttention(String term);
	
	/**
	 * 時間外注意時間(時間文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外注意時間(時間文字列)
	 */
	String getAttentionHours(String term);
	
	/**
	 * 時間外注意時間(分文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外注意時間(分文字列)
	 */
	String getAttentionMinutes(String term);
	
	/**
	 * 時間外警告時間(分)を取得する。<br>
	 * @param term 期間
	 * @return 時間外警告時間(分)
	 */
	int getWarning(String term);
	
	/**
	 * 時間外警告時間(時間文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外警告時間(時間文字列)
	 */
	String getWarningHours(String term);
	
	/**
	 * 時間外警告時間(分文字列)を取得する。<br>
	 * @param term 期間
	 * @return 時間外警告時間(分文字列)
	 */
	String getWarningMinutes(String term);
	
	/**
	 * 限度超過量を取得する。<br>
	 * 限度を超過していない場合は0を返す。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 限度超過量
	 */
	int getOverLimit(String term, int value);
	
	/**
	 * 値が限度を超過しているかを確認する。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 確認結果(true：値が限度を超過している、false：していない)
	 */
	boolean isOverLimit(String term, int value);
	
	/**
	 * 注意超過量を取得する。<br>
	 * 注意を超過していない場合は0を返す。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 注意超過量
	 */
	int getOverAttention(String term, int value);
	
	/**
	 * 値が注意を超過しているかを確認する。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 確認結果(true：値が注意を超過している、false：していない)
	 */
	boolean isOverAttention(String term, int value);
	
	/**
	 * 警告超過量を取得する。<br>
	 * 警告を超過していない場合は0を返す。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 警告超過量
	 */
	int getOverWarning(String term, int value);
	
	/**
	 * 値が警告を超過しているかを確認する。<br>
	 * @param term  期間
	 * @param value 値
	 * @return 確認結果(true：値が警告を超過している、false：していない)
	 */
	boolean isOverWarning(String term, int value);
	
	/**
	 * スタイル文字列を取得する。<br>
	 * 値が注意を超えている場合は黄、警告を超えている場合は赤を取得する。<br>
	 * どちらも超えている場合は、警告を優先する。<br>
	 * @param term  期間
	 * @param value 値
	 * @return スタイル文字列
	 */
	String getStyle(String term, int value);
	
	/**
	 * 1ヶ月(month1)スタイル文字列を取得する。<br>
	 * @param value 値
	 * @return 1ヶ月(month1)スタイル文字列
	 */
	String getOneMonthStyle(int value);
	
}
