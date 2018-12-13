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
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;

/**
 * 休暇データ参照インターフェース。
 */
public interface HolidayDataReferenceBeanInterface {
	
	/**
	 * 休暇データからレコードを取得する。<br>
	 * 社員コード、有効日、休暇コード、休暇区分で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDataDtoInterface findForInfo(String personalId, Date activateDate, String holidayCode, int holidayType)
			throws MospException;
	
	/**
	 * 休暇データからレコードを取得する。<br>
	 * 社員コード、有効日、休暇コード、休暇区分で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDataDtoInterface findForKey(String personalId, Date activateDate, String holidayCode, int holidayType)
			throws MospException;
	
}
