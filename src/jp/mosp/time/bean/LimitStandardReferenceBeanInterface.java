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
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準参照インターフェース。
 */
public interface LimitStandardReferenceBeanInterface {
	
	/**
	 * 限度基準マスタ取得。
	 * <p>
	 * 勤怠設定コードと対象日と期間から限度基準マスタを取得。
	 * </p>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate 対象年月日
	 * @param term 期間
	 * @return 限度基準マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	LimitStandardDtoInterface getLimitStandardInfo(String workSettingCode, Date targetDate, String term)
			throws MospException;
	
	/**
	 * 限度基準マスタからレコードを取得する。<br>
	 * 勤怠設定コード、有効日、期間で合致するレコードが無い場合、nullを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @param term 期間
	 * @return 限度基準マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	LimitStandardDtoInterface findForKey(String workSettingCode, Date activateDate, String term) throws MospException;
	
}
