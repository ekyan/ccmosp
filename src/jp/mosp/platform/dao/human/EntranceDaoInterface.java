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
package jp.mosp.platform.dao.human;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.EntranceDtoInterface;

/**
 * 人事入社情報DAOインターフェース
 */
public interface EntranceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事入社情報取得。
	 * <p>
	 * 個人IDから人事入社情報を取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事入社情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	EntranceDtoInterface findForInfo(String personalId) throws MospException;
	
	/**
	 * 対象日における退職者の個人IDを抽出するSQLを取得する。<br>
	 * @return 退職者の個人IDを抽出するSQL
	 */
	String getQueryForEntrancedPerson();
}
