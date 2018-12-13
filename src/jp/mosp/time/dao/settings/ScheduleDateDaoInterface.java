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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日マスタDAOインターフェース
 */
public interface ScheduleDateDaoInterface extends BaseDaoInterface {
	
	/**
	 * カレンダコードと有効日と日からカレンダ日情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @param scheduleDate 日
	 * @return カレンダ日マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDateDtoInterface findForKey(String scheduleCode, Date activateDate, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダコードと対象日からカレンダ日情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param scheduleDate 対象日
	 * @return カレンダ日マスタDTO
	 * @throws MospException MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDateDtoInterface findForKey(String scheduleCode, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダコードと有効日からカレンダ日マスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * カレンダコードと有効日と開始日と終了日からカレンダ日マスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * カレンダコードと開始日と終了日からカレンダ日マスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * カレンダ日マスタ取得。
	 * <p>
	 * カレンダコードと有効日と日からカレンダ日マスタを取得する。
	 * </p>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @param scheduleDate 日
	 * @return カレンダ日管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDateDtoInterface findForInfo(String scheduleCode, Date activateDate, Date scheduleDate)
			throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * カレンダコードと日からカレンダ日マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param scheduleCode カレンダコード
	 * @param scheduleDate 日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForHistory(String scheduleCode, Date scheduleDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日と勤務形態コードからカレンダ日マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @param targetCode 勤務形態コード
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForActivateDate(Date activateDate, String targetCode) throws MospException;
	
	/**
	 * カレンダ日マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @param targetCode 勤務形態コード
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate, String targetCode)
			throws MospException;
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * @return カレンダコードに紐づく有効日が最大であるレコード取得クエリ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StringBuffer getQueryForMaxActivateDate() throws MospException;
	
}
