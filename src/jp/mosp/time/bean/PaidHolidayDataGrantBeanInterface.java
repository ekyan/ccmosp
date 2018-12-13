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

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇データ付与インターフェース。<br>
 */
public interface PaidHolidayDataGrantBeanInterface {
	
	/**
	 * 有給休暇データ付与を行う。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void grant(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データを生成する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param accomplish 達成率基準
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface create(String personalId, Date targetDate, boolean accomplish) throws MospException;
	
	/**
	 * 有給休暇付与回数を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇付与回数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getGrantTimes(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇付与日を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param grantTimes 有給休暇付与回数
	 * @return 有給休暇付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getGrantDate(String personalId, Date targetDate, int grantTimes) throws MospException;
	
	/**
	 * 有給休暇付与日を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param grantTimes 有給休暇付与回数
	 * @param entranceDate 入社日
	 * @return 有給休暇付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getGrantDate(String personalId, Date targetDate, int grantTimes, Date entranceDate) throws MospException;
	
}
