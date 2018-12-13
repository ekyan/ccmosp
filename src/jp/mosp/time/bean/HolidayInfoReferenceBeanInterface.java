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

import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;

/**
 * 休暇数参照処理インターフェース。<br>
 */
public interface HolidayInfoReferenceBeanInterface {
	
	/**
	 * 申請用休暇データリスト取得。
	 * <p>
	 * 個人IDと対象年月日と休暇区分から休暇データを取得。
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は廃棄日数に加算する。
	 * 但し、1次戻は廃棄日数に加算しない。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param holidayType 休暇区分
	 * @return 休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayDataDtoInterface> getHolidayPossibleRequestListForRequest(String personalId, Date targetDate,
			int holidayType) throws MospException;
	
	/**
	 * 休暇データリスト取得。
	 * <p>
	 * 個人IDと対象年月日と休暇区分から休暇データを取得。
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は廃棄日数に加算する。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param holidayType 休暇区分
	 * @return 休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayDataDtoInterface> getHolidayPossibleRequestList(String personalId, Date targetDate, int holidayType)
			throws MospException;
	
	/**
	 * 申請用休暇データ取得。
	 * <p>
	 * 個人IDと対象年月日と休暇コードと休暇区分から休暇データを取得。
	 * 休暇申請の承認状況が未承認・承認・差戻・承認解除・承認済は廃棄日数に加算する。
	 * 但し、1次戻は廃棄日数に加算しない。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDataDtoInterface getHolidayPossibleRequestForRequest(String personalId, Date targetDate, String holidayCode,
			int holidayType) throws MospException;
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @param holidayType 休暇区分
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate, int holidayType)
			throws MospException;
	
	/**
	 * 休暇の残日数及び残時間数群を取得する。<br>
	 * <br>
	 * 対象日時点で有効な休暇付与情報リストを取得し、
	 * それぞれの休暇付与情報に対する休暇申請情報と合わせて、
	 * 残日数及び残時間数を計算する。<br>
	 * <br>
	 * @param humanDto    人事情報
	 * @param targetDate  対象日
	 * @param holidayType 休暇区分(休暇種別1：特別休暇orその他休暇)
	 * @return 休暇の残日数(キー)及び残時間数(値)群(キー：休暇付与情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<HolidayDataDtoInterface, SimpleEntry<Double, Integer>> getHolidayRemains(HumanDtoInterface humanDto,
			Date targetDate, int holidayType) throws MospException;
	
}
