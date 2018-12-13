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
 * 休日出勤情報参照インターフェース。
 */
public interface WorkOnHolidayInfoReferenceBeanInterface {
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 個人IDと開始日と終了日から休日出勤申請可能日プルダウン用配列を取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(String personalId, Date startDate, Date endDate) throws MospException;
	
}
