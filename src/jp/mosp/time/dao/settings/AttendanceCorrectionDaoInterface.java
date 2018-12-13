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
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;

/**
 * 勤怠データ修正情報参照DAOインターフェース
 */
public interface AttendanceCorrectionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと勤務日と勤務回数から最新の勤怠データ修正情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @return 勤怠修正データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceCorrectionDtoInterface findForLatestInfo(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDと申請日と残業区分から残業申請マスタリストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @return 勤怠詳細修正情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceCorrectionDtoInterface> findForHistory(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 設定適用検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
