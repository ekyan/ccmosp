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
import java.util.Map;

import jp.mosp.framework.base.MospException;

/**
 * 有給休暇申請参照インターフェース。
 */
public interface PaidHolidayRequestReferenceBeanInterface {
	
	/**
	 * 有給休暇申請可能数を取得する。<br><br>
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は残日数から減算する。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 有給休暇申請可能数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayPossibleRequest(String personalId, Date targetDate) throws MospException;
	
}
