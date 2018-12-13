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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日参照インターフェース。
 */
public interface ScheduleDateReferenceBeanInterface {
	
	/**
	 * カレンダ日マスタ取得。
	 * <p>
	 * カレンダコードと対象日と日からカレンダ日マスタを取得。
	 * </p>
	 * @param scheduleCode カレンダコード
	 * @param targetDate 対象年月日
	 * @param scheduleDate 日
	 * @return カレンダ日マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDateInfo(String scheduleCode, Date targetDate, Date scheduleDate)
			throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * カレンダコード、有効日、日で合致するレコードが無い場合、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @param scheduleDate 日
	 * @return カレンダ日マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface findForKey(String scheduleCode, Date activateDate, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * カレンダコード、日で合致するレコードが無い場合、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param scheduleDate 日
	 * @return カレンダ日マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface findForKey(String scheduleCode, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダ日マスタDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * 年度(activateDate)ありで取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * 年度(activateDate)なしで取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * カレンダーコードにおいて対象日が、所定又は法定休日であるか確認する。
	 * @param scheduleCode カレンダコード
	 * @param targetDate 対象日
	 * @return 確認結果(true：所定又は法定休日の場合、false：所定又は法定休日以外の場合)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isHoliday(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ日の存在チェック。<br>
	 * @param dto 対象カレンダ日
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistScheduleDate(ScheduleDateDtoInterface dto, Date targetDate);
}
