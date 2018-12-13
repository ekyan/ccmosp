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
import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠設定エンティティ。<br>
 */
public class TimeSettingEntity implements TimeSettingEntityInterface {
	
	/**
	 * 勤怠設定情報。<br>
	 */
	protected TimeSettingDtoInterface					dto;
	
	/**
	 * 限度基準情報群(キー：期間)。<br>
	 */
	protected Map<String, LimitStandardDtoInterface>	limits;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public TimeSettingEntity() {
		// 限度基準情報群(キー：期間)を初期化
		limits = new HashMap<String, LimitStandardDtoInterface>();
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSettingDto() {
		return dto;
	}
	
	@Override
	public LimitStandardDtoInterface getLimitStandardDto(String term) {
		return limits.get(term);
	}
	
	@Override
	public void setTimeSettingDto(TimeSettingDtoInterface dto) {
		this.dto = dto;
		
	}
	
	@Override
	public void setLimitStandardDtos(Map<String, LimitStandardDtoInterface> limits) {
		this.limits = limits;
	}
	
	@Override
	public String getWorkSettingCode() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤怠設定コードを取得
		return dto.getWorkSettingCode();
	}
	
	@Override
	public Date getActivateDate() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// nullを取得
			return null;
		}
		// 有効日を取得
		return dto.getActivateDate();
	}
	
	@Override
	public boolean isExist() {
		return dto != null;
	}
	
	@Override
	public boolean isLimitStandardExist(String term) {
		return getLimitStandardDto(term) != null;
	}
	
	@Override
	public int getStartWeek() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 0を取得
			return 0;
		}
		// 週の起算曜日を取得
		return dto.getStartWeek();
	}
	
	@Override
	public int getStartMonthOfYear() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 1を取得
			return 1;
		}
		// 年の起算月を取得
		int startMonth = dto.getStartYear();
		// 年の起算月が0である場合
		if (startMonth == 0) {
			// 1を取得
			return 1;
		}
		// 年の起算月を取得
		return startMonth;
	}
	
	@Override
	public int roundDailyLeaveEarly(int leaveEarly) {
		// TODO 未実装 getRoundMinute(value, round, unit);
		return 0;
	}
	
	@Override
	public boolean isOneWeekExist() {
		// 1週間(week1)限度基準情報が存在するかを確認
		return isLimitStandardExist(TERM_ONE_WEEK);
	}
	
	@Override
	public boolean isOneMonthExist() {
		// 1ヶ月(month1)限度基準情報が存在するかを確認
		return isLimitStandardExist(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneWeekLimit() {
		// 1週間(week1)時間外限度時間(分)を取得
		return getLimit(TERM_ONE_WEEK);
	}
	
	@Override
	public int getOneMonthLimit() {
		// 1ヶ月(month1)時間外限度時間(分)を取得
		return getLimit(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneMonthAttention() {
		// 1ヶ月(month1)時間外注意時間(分)を取得
		return getAttention(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneMonthWarning() {
		// 1ヶ月(month1)時間外警告時間(分)を取得
		return getWarning(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneWeekLimitHours() {
		// 1週間(week1)時間外限度時間(時間文字列)を取得
		return getLimitHours(TERM_ONE_WEEK);
	}
	
	@Override
	public String getOneWeekLimitMinutes() {
		// 1週間(week1)時間外限度時間(分文字列)を取得
		return getLimitMinutes(TERM_ONE_WEEK);
	}
	
	@Override
	public String getOneMonthLimitHours() {
		// 1ヶ月(month1)時間外限度時間(時間文字列)を取得
		return getLimitHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthLimitMinutes() {
		// 1ヶ月(month1)時間外限度時間(分文字列)を取得
		return getLimitMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthAttentionHours() {
		// 1ヶ月(month1)時間外注意時間(時間文字列)を取得
		return getAttentionHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthAttentionMinutes() {
		// 1ヶ月(month1)時間外注意時間(分文字列)を取得
		return getAttentionMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthWarningHours() {
		// 1ヶ月(month1)時間外警告時間(時間文字列)を取得
		return getWarningHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthWarningMinutes() {
		// 1ヶ月(month1)時間外警告時間(分文字列)を取得
		return getWarningMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public int getLimit(String term) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 時間外限度時間(分)を取得
		return dto.getLimitTime();
	}
	
	@Override
	public String getLimitHours(String term) {
		// 時間外限度時間(時間文字列)を取得
		return getHours(getLimit(term));
	}
	
	@Override
	public String getLimitMinutes(String term) {
		// 時間外限度時間(分文字列)を取得
		return getMinutes(getLimit(term));
	}
	
	@Override
	public int getAttention(String term) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 時間外注意時間(分)を取得
		return dto.getAttentionTime();
	}
	
	@Override
	public String getAttentionHours(String term) {
		// 時間外注意時間(時間文字列)を取得
		return getHours(getAttention(term));
	}
	
	@Override
	public String getAttentionMinutes(String term) {
		// 時間外注意時間(分文字列)を取得
		return getMinutes(getAttention(term));
	}
	
	@Override
	public int getWarning(String term) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 時間外警告時間(分)を取得
		return dto.getWarningTime();
	}
	
	@Override
	public String getWarningHours(String term) {
		// 時間外警告時間(時間文字列)を取得
		return getHours(getWarning(term));
	}
	
	@Override
	public String getWarningMinutes(String term) {
		// 時間外警告時間(分文字列)を取得
		return getMinutes(getWarning(term));
	}
	
	@Override
	public int getOverLimit(String term, int value) {
		// 時間外限度時間(分)を取得
		int limit = getLimit(term);
		// 時間外限度時間(分)が0である場合
		if (limit == 0) {
			// 0を取得
			return 0;
		}
		// 限度超過量を取得
		int overLimit = value - limit;
		// 限度を超過していない場合は0を取得
		return overLimit < 0 ? 0 : overLimit;
	}
	
	@Override
	public boolean isOverLimit(String term, int value) {
		// 限度超過量が0より大きいかを確認
		return getOverLimit(term, value) > 0;
	}
	
	@Override
	public int getOverAttention(String term, int value) {
		// 時間外注意時間(分)を取得を取得
		int attention = getAttention(term);
		// 時間外注意時間(分)が0である場合
		if (attention == 0) {
			// 0を取得
			return 0;
		}
		// 注意超過量を取得
		int overAttention = value - attention;
		// 注意を超過していない場合は0を取得
		return overAttention < 0 ? 0 : overAttention;
	}
	
	@Override
	public boolean isOverAttention(String term, int value) {
		// 注意超過量が0より大きいかを確認
		return getOverAttention(term, value) > 0;
	}
	
	@Override
	public int getOverWarning(String term, int value) {
		// 時間外警告時間(分)を取得を取得
		int warning = getWarning(term);
		// 時間外警告時間(分)が0である場合
		if (warning == 0) {
			// 0を取得
			return 0;
		}
		// 警告超過量を取得
		int overWarning = value - warning;
		// 警告を超過していない場合は0を取得
		return overWarning < 0 ? 0 : overWarning;
	}
	
	@Override
	public boolean isOverWarning(String term, int value) {
		// 警告超過量が0より大きいかを確認
		return getOverWarning(term, value) > 0;
	}
	
	@Override
	public String getStyle(String term, int value) {
		// 限度基準情報が存在しない場合
		if (isLimitStandardExist(term) == false) {
			// 空を取得
			return MospConst.STR_DB_SPACE;
		}
		// 値が警告を超過している場合
		if (isOverWarning(term, value)) {
			// 警告用のスタイル文字列を取得
			return getWarningStyle();
		}
		// 値が注意を超過している場合
		if (isOverAttention(term, value)) {
			// 注意用のスタイル文字列を取得
			return getAttentionStyle();
		}
		// 空を取得
		return MospConst.STR_DB_SPACE;
	}
	
	@Override
	public String getOneMonthStyle(int value) {
		// 1ヶ月(month1)スタイル文字列を取得
		return getStyle(TERM_ONE_MONTH, value);
	}
	
	/**
	 * 時間文字列を取得する。<br>
	 * @param time 分
	 * @return 時間文字列
	 */
	protected String getHours(int time) {
		// 分が0である場合
		if (time == 0) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// 時間文字列を取得
		return String.valueOf(TimeUtility.getHours(time));
	}
	
	/**
	 * 分文字列を取得する。<br>
	 * @param time 分
	 * @return 分文字列
	 */
	protected String getMinutes(int time) {
		// 分が0である場合
		if (time == 0) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// 分文字列を取得
		return String.valueOf(TimeUtility.getMinutes(time));
	}
	
	/**
	 * 注意用のスタイル文字列を取得する。<br>
	 * @return 注意用のスタイル文字列
	 */
	protected String getAttentionStyle() {
		// スタイル文字列(黄)を取得
		return PlatformConst.STYLE_YELLOW;
	}
	
	/**
	 * 警告用のスタイル文字列を取得する。<br>
	 * @return 警告用のスタイル文字列
	 */
	protected String getWarningStyle() {
		// スタイル文字列(赤)を取得
		return PlatformConst.STYLE_RED;
	}
	
}
