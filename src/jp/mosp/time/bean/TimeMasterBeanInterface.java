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
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.WorkTypeEntity;

/**
 * 勤怠関連マスタ参照処理インターフェース。<br>
 */
public interface TimeMasterBeanInterface {
	
	/**
	 * 対象日時点における最新の有効な情報から、設定適用情報を取得する。<br>
	 * <br>
	 * {@link PlatformUtility#getApplicationMaster(HumanDtoInterface, Set, Set)}
	 * 参照。<br>
	 * <br>
	 * @param humanDto   人事情報
	 * @param targetDate 対象日
	 * @return 設定適用情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface getApplication(HumanDtoInterface humanDto, Date targetDate) throws MospException;
	
	/**
	 * 対象日時点における最新の情報から、有給休暇設定情報を取得する。<br>
	 * <br>
	 * {@link PlatformUtility#getApplicationMaster(HumanDtoInterface, Set, Set)}
	 * 参照。<br>
	 * <br>
	 * @param humanDto   人事情報
	 * @param targetDate 対象日
	 * @return 有給休暇設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDtoInterface getPaidHoliday(HumanDtoInterface humanDto, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び期間から、適用されている設定を日毎に取得する。<br>
	 * <br>
	 * 期間開始日で設定適用情報を取得し、対象日毎に追加していく。<br>
	 * 設定適用情報及び人事履歴情報に対象日を有効日とする情報が存在した場合、
	 * 対象日で設定適用情報を再取得することで、日毎の設定適用を作成する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param firstDate  期間開始日
	 * @param lastDate   期間終了日
	 * @return 設定適用情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, ApplicationDtoInterface> getApplicationMap(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * <br>
	 * 対象年月の基準日で、情報を取得する。<br>
	 * <br>
	 * @param humanDto    対象人事情報
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, int targetYear, int targetMonth)
			throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * <br>
	 * @param humanDto   対象人事情報
	 * @param targetDate 対象日
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, Date targetDate) throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 勤怠設定情報群を取得する。<br>
	 * <br>
	 * 設定適用情報群と同じキー群を持つ。<br>
	 * {@link TimeMasterBeanInterface#getApplicationMap(String, Date, Date)}
	 * と併せて用いることを想定する。<br>
	 * <br>
	 * @param applicationMap 設定適用情報群
	 * @return 勤怠設定情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, TimeSettingDtoInterface> getTimeSettingMap(Map<Date, ApplicationDtoInterface> applicationMap)
			throws MospException;
	
	/**
	 * 締日情報を取得する。<br>
	 * <br>
	 * 対象年月の基準日で、情報を取得する。<br>
	 * {@link TimeMasterBeanInterface#getCutoff(String, Date)}
	 * 参照。<br>
	 * <br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	CutoffDtoInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 締日情報を取得する。<br>
	 * <br>
	 * フィールドから締日管理情報を取得できなかった場合は、
	 * 締日管理情報をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param cutoffCode 締日コード
	 * @param targetDate 対象日
	 * @return 締日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	CutoffDtoInterface getCutoff(String cutoffCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ日情報を取得する。<br>
	 * <br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象日
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDate(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ日情報群をフィールドに設定する。<br>
	 * <br>
	 * 但し、フィールドに対象カレンダコードの情報が一つでも設定されている場合は、
	 * 処理をしない。<br>
	 * <br>
	 * 月単位で情報が必要な場合等、DBからまとめて一度に取得することで、
	 * パフォーマンスの向上を図る。<br>
	 * <br>
	 * @param scheduleCode カレンダコード
	 * @param firstDate    対象期間初日
	 * @param lastDate     対象期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void addScheduleDateMap(String scheduleCode, Date firstDate, Date lastDate) throws MospException;
	
	/**
	 * 対象日における最新の休暇種別情報群を取得する。<br>
	 * <br>
	 * 但し、対象日における最新の休暇種別情報が無効となっているものは、取得しない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 休暇種別情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayDtoInterface> getHolidaySet(Date targetDate) throws MospException;
	
	/**
	 * 勤怠設定情報を取得する。<br>
	 * <br>
	 * フィールドから勤怠設定情報を取得できなかった場合は、
	 * 勤怠設定情報をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate      対象日
	 * @return 勤怠設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface getTimeSetting(String workSettingCode, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇設定情報を取得する。<br>
	 * <br>
	 * フィールドから有給休暇設定情報を取得できなかった場合は、
	 * 有給休暇設定情報をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param paidHolidayCode 有給休暇設定コード
	 * @param targetDate      対象日
	 * @return 有給休暇設定
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDtoInterface getPaidHoliday(String paidHolidayCode, Date targetDate) throws MospException;
	
	/**
	 * 有休時間取得限度時間(有給休暇設定)を取得する。<br>
	 * <br>
	 * 取得できなかった場合は、0を返す。<br>
	 * <br>
	 * @param humanDto   人事情報
	 * @param targetDate 対象日
	 * @return 有休時間取得限度時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getPaidHolidayHoursPerDay(HumanDtoInterface humanDto, Date targetDate) throws MospException;
	
	/**
	 * 勤務形態エンティティ履歴を取得する。<br>
	 * <br>
	 * フィールドから勤務形態エンティティ履歴を取得できなかった場合は、
	 * 勤務形態エンティティ履歴をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * 履歴は、有効日昇順に並んでいる。<br>
	 * <br>
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態エンティティ履歴
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeEntity> getWorkTypeEntityHistory(String workTypeCode) throws MospException;
	
}
