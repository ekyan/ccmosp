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
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 休日出勤申請参照インターフェース。
 */
public interface WorkOnHolidayRequestReferenceBeanInterface {
	
	/**
	 * 休日出勤申請からレコードを取得する。<br>
	 * 取下状態は除く。<br>
	 * 個人ID、申請日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID	
	 * @param requestDate 申請日
	 * @return 休日出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 個人IDと出勤日及び出勤回数から休日出勤申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId  個人ID
	 * @param requestDate 出勤日
	 * @param timesWork   出勤回数
	 * @return 休日出勤申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface findForKey(String personalId, Date requestDate, int timesWork)
			throws MospException;
	
	/**
	 * 休日出勤申請取得。
	 * <p>
	 * レコード識別IDから休日出勤申請を取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 休日出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * ワークフロー番号からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 休日出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 休日出勤申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休日出勤申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人IDと出勤日及び出勤回数から休日出勤申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * 但し、取下られた申請は除く。<br>
	 * @param personalId  個人ID
	 * @param requestDate 出勤日
	 * @param timesWork   出勤回数
	 * @return 休日出勤申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface findForSubstitute(String personalId, Date requestDate, int timesWork)
			throws MospException;
	
	/**
	 * 半日振替出勤を利用するかどうかを取得する。<br>
	 * @return 半日振替出勤利用設定(true：利用、false：利用しない)
	 */
	boolean useHalfSubstitute();
	
}
