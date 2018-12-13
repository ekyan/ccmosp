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
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;

/**
 * 設定適用管理参照インターフェース。<br>
 */
public interface ApplicationReferenceBeanInterface {
	
	/**
	 * 設定適用マスタ取得。
	 * <p>
	 * 設定適用コードと対象日から設定適用マスタを取得。
	 * </p>
	 * @param applicationCode 設定適用コード
	 * @param targetDate 対象年月日
	 * @return 設定適用マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface getApplicationInfo(String applicationCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 設定適用コードから設定適用マスタリストを取得。
	 * @param applicationCode 設定適用コード
	 * @return 設定適用マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ApplicationDtoInterface> getApplicationHistory(String applicationCode) throws MospException;
	
	/**
	 * 設定適用略称を取得する。<br><br>
	 * 対象となる設定適用管理情報が存在しない場合は、設定適用コードを返す。<br>
	 * @param applicationCode 設定適用コード
	 * @param targetDate 対象年月日
	 * @return 設定適用略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getApplicationAbbr(String applicationCode, Date targetDate) throws MospException;
	
	/**
	 * 設定適用マスタからレコードを取得する。<br>
	 * 設定適用コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param applicationCode 設定適用コード
	 * @param targetDate 有効日
	 * @return 設定適用マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findForKey(String applicationCode, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び対象日から、適用されている設定を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 設定適用マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findForPerson(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び期間から、適用されている設定を日毎に取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return 設定適用マスタDTO群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, ApplicationDtoInterface> findForTerm(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 設定適用マスタの存在チェック。<br>
	 * @param dto 対象設定適用
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistApplication(ApplicationDtoInterface dto, Date targetDate);
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象年月日
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * <br>
	 * 対象年月の基準日で、情報を取得する。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(String personalId, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 個人ID及び対象日から、適用されている設定を取得する。<br>
	 * (設定適用情報を事前取得している)<br>
	 * @param humanDto       人事マスタDTO
	 * @param applicationMap 設定適用情報MAP
	 * @return 設定適用マスタDTO 
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findForPerson(HumanDtoInterface humanDto,
			Map<Integer, Set<ApplicationDtoInterface>> applicationMap) throws MospException;
	
	/**
	 * 対象日からみて最新の設定適用情報群を取得する。<br>
	 * @param targetDate 対象年月日
	 * @return 設定適用情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Integer, Set<ApplicationDtoInterface>> getApplicationInfoForTargetDate(Date targetDate) throws MospException;
	
}
