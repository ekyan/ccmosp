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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;

/**
 * 打刻データ参照インターフェース。
 */
public interface TimeRecordReferenceBeanInterface {
	
	/**
	 * 打刻データを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @param recordType 打刻区分
	 * @return 打刻データDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	TimeRecordDtoInterface findForKey(String personalId, Date workDate, int timesWork, String recordType)
			throws MospException;
	
	/**
	 * 開始日から終了日までの期間の打刻データを取得する。
	 * @param personalId 個人ID
	 * @param startDate 開始日 
	 * @param endDate 	終了日
	 * @return 打刻データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public List<TimeRecordDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException;
}
