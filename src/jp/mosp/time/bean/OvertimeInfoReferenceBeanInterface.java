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

import jp.mosp.framework.base.MospException;

/**
 * 残業情報参照インターフェース。
 */
public interface OvertimeInfoReferenceBeanInterface {
	
	/**
	 * 残業申請可能時間(1週間)を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 残業申請可能時間(1週間)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getPossibleTime1Week(String personalId) throws MospException;
	
	/**
	 * 残業申請可能時間(1ヶ月)を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 残業申請可能時間(1週間)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getPossibleTime1Month(String personalId) throws MospException;
	
}
