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
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 締日ユーティリティインターフェース。<br>
 *
 */
public interface CutoffUtilBeanInterface {
	
	/**
	 * 対象年月において対象締日コードが適用されている個人IDのセットを取得する。<br>
	 * @param cutoffCode 対象締日コード
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 個人IDのセット
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getCutoffPersonalIdSet(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 締日コードと年月を指定して締日情報を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	CutoffDtoInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 締日コードと対象日から締日情報を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetDate 対象日
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	CutoffDtoInterface getCutoff(String cutoffCode, Date targetDate) throws MospException;
	
	/**
	 * 年月を指定して締日情報を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	CutoffDtoInterface getCutoffForPersonalId(String personalId, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象個人IDと対象日から締日情報を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	CutoffDtoInterface getCutoffForPersonalId(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用情報と対象日から締日情報を取得する。<br>
	 * @param dto        設定適用情報
	 * @param targetDate 対象日
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	CutoffDtoInterface getCutoffForApplication(ApplicationDtoInterface dto, Date targetDate) throws MospException;
	
	/**
	 * 締期間における基準日を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締期間における基準日
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	Date getCutoffTermTargetDate(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象年月における締期間の集計日を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締期間集計日
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	Date getCutoffCalculationDate(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象個人IDにつき対象日付が未締であるかの確認をする。<br>
	 * 未締でない場合、エラーメッセージを設定する。<br>
	 * また対象日付における対象個人IDの締日情報が取得できなかった場合、エラーメッセージを設定する。<br> 
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @param targetName 対象日付名称
	 * @return 確認結果(true：未締、false：仮締、確定、締日情報取得不能)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean checkTighten(String personalId, Date targetDate, String targetName) throws MospException;
	
	/**
	 * 対象個人IDにつき対象年月が未締であるかの確認をする。<br>
	 * 未締でない場合、エラーメッセージを設定する。<br>
	 * また対象年月における対象個人IDの締日情報が取得できなかった場合、エラーメッセージを設定する。<br>
	 * @param personalId 個人ID
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 確認結果(true：未締、false：仮締、確定、締日情報取得不能)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean checkTighten(String personalId, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象個人IDにつき対象日付が未締であるかの確認をする。<br>
	 * 対象日付における対象個人IDの締日情報が取得できなかった場合、falseを返す。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @return 確認結果(true：未締、false：未締でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isNotTighten(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDにつき対象年月が未締であるかの確認をする。<br>
	 * @param personalId 個人ID
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 確認結果(true：未締、false：未締でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isNotTighten(String personalId, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象年月及び締日から締期間初日を取得する。<br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffFirstDate(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象年月及び締日から締期間最終日を取得する。<br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffLastDate(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 対象個人ID及び対象日付から、対象年月日が含まれる締月を取得する。<br>
	 * 対象日付で対象個人IDに設定されている締日情報を取得し、締月を算出する。<br>
	 * 締日情報が取得できない場合は、対象日付の年月の初日を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @return 締月(締月初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffMonth(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人ID及び対象日付から勤怠設定情報を取得する。<br>
	 * 取得に失敗した場合は、MosP処理情報にエラーメッセージを設定し、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @return 勤怠設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface getTimeSetting(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用情報及び対象日付から勤怠設定情報を取得する。<br>
	 * 取得に失敗した場合は、MosP処理情報にエラーメッセージを設定し、nullを返す。<br>
	 * @param dto        設定適用情報
	 * @param targetDate 対象日付
	 * @return 勤怠設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface getTimeSetting(ApplicationDtoInterface dto, Date targetDate) throws MospException;
	
	/**
	 * 対象個人ID及び対象日付から勤怠設定情報を取得する。<br>
	 * 取得に失敗した場合は、nullを返す(エラーメッセージは設定しない)。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @return 勤怠設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface getTimeSettingNoMessage(String personalId, Date targetDate) throws MospException;
	
}
