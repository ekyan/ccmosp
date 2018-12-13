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
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 勤怠一覧参照インターフェース。
 */
public interface AttendanceListReferenceBeanInterface {
	
	/**
	 * 個人ID及び対象日で予定一覧を取得する。<br>
	 * 予定一覧画面、予定簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 予定一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getScheduleList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び年月で予定一覧を取得する。<br>
	 * 予定一覧画面、予定簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 予定一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getScheduleList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人ID及び対象日で実績一覧を取得する。<br>
	 * 出勤簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 実績一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getActualList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び年月で実績一覧を取得する。<br>
	 * 出勤簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 実績一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getActualList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠一覧を取得する。<br>
	 * 勤怠一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getAttendanceList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び年月で勤怠一覧を取得する。<br>
	 * 勤怠一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getAttendanceList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人ID及び対象日で週の勤怠一覧を取得する。<br>
	 * ポータル画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 週の勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getWeeklyAttendanceList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠承認一覧を取得する。<br>
	 * 勤怠承認一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 勤怠承認一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getApprovalAttendanceList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠一覧データを取得する。<br>
	 * 承認履歴参照画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 勤怠一覧データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceListDto getAttendanceListDto(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDを取得する。<br>
	 * @return 対象個人ID
	 */
	String getPersonalId();
	
	/**
	 * 対象締期間最終日を取得する。<br>
	 * @return 対象締期間最終日
	 */
	Date getLastDate();
	
	/**
	 * 対象締期間初日を取得する。<br>
	 * @return 対象締期間初日
	 */
	Date getFirstDate();
	
	/**
	 * 対象年を取得する。<br>
	 * @return 対象年
	 */
	int getTargetYear();
	
	/**
	 * 対象月を取得する。<br>
	 * @return 対象月
	 */
	int getTargetMonth();
	
	/**
	 * 対象カレンダ名称を取得する。<br>
	 * @return カレンダ名称
	 */
	String getScheduleName();
	
	/**
	 * 対象期間の勤怠未入力確認用に勤怠一覧情報から
	 * 勤怠未入力(勤怠入力用チェックボックス有)の日付リストを取得する。
	 * @param personalId 対象個人ID
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 勤怠未入力の日付リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<Date> getNotAttendanceAppliList(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 半日勤務形態略称長を設定する。<br>
	 * @param halfWorkTypeLength 半日勤務形態略称長
	 */
	void setHalfWorkTypeLength(int halfWorkTypeLength);
	
}
