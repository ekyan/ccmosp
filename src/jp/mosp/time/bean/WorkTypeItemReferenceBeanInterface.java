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
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目参照インターフェース。
 */
public interface WorkTypeItemReferenceBeanInterface {
	
	/**
	 * 勤務形態項目取得。
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeItemDtoInterface getWorkTypeItemInfo(String workTypeCode, Date targetDate, String workTypeItemCode)
			throws MospException;
	
	/**
	 * 勤務形態項目取得。
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeItemDtoInterface findForKey(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException;
}
