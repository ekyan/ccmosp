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
package jp.mosp.platform.bean.system;

import java.util.Date;

import jp.mosp.framework.base.MospException;

/**
 * 	ロールマスタ参照インターフェース
 */
public interface RoleReferenceBeanInterface {
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 対象年月日からプルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException 例外処理が発生した場合。
	 */
	String[][] getSelectArray(Date targetDate, boolean viewCode) throws MospException;
	
	/**
	 * ロール名称を取得する。<br>
	 * @param roleCode   対象ロールコード
	 * @param targetDate 対象年月日
	 * @return ロール名称
	 * @throws MospException ロール名称の取得に失敗した場合
	 */
	String getRoleName(String roleCode, Date targetDate) throws MospException;
	
}
