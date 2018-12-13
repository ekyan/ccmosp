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
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目登録インターフェース
 */
public interface WorkTypeItemRegistBeanInterface {
	
	/**
	 * 勤務形態項目配列。<br>
	 */
	public static final String[]	CODES_WORK_TYPE_ITEM	= { TimeConst.CODE_WORKSTART, TimeConst.CODE_WORKEND,
		TimeConst.CODE_WORKTIME, TimeConst.CODE_RESTTIME, TimeConst.CODE_RESTSTART1, TimeConst.CODE_RESTEND1,
		TimeConst.CODE_RESTSTART2, TimeConst.CODE_RESTEND2, TimeConst.CODE_RESTSTART3, TimeConst.CODE_RESTEND3,
		TimeConst.CODE_RESTSTART4, TimeConst.CODE_RESTEND4, TimeConst.CODE_FRONTSTART, TimeConst.CODE_FRONTEND,
		TimeConst.CODE_BACKSTART, TimeConst.CODE_BACKEND, TimeConst.CODE_OVERBEFORE, TimeConst.CODE_OVERPER,
		TimeConst.CODE_OVERREST, TimeConst.CODE_HALFREST, TimeConst.CODE_HALFRESTSTART, TimeConst.CODE_HALFRESTEND,
		TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START, TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END,
		TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END			};
	
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	WorkTypeItemDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String workTypeCode, Date activateDate) throws MospException;
	
}
