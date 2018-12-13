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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態エンティティインターフェース。<br>
 */
public interface WorkTypeEntityInterface {
	
	/**
	 * 勤務形態情報の設定。
	 * @param workTypeDto セットする勤務形態情報
	 */
	public void setWorkTypeDto(WorkTypeDtoInterface workTypeDto);
	
	/**
	 * 勤務形態項目情報リストの設定。
	 * @param itemDtoList セットする勤務形態項目情報リスト
	 */
	public void setWorkTypeItemList(List<WorkTypeItemDtoInterface> itemDtoList);
	
	/**
	 * 勤務形態略称を取得する。<br>
	 * 勤務形態略称が未設定の場合は、空文字を返す。<br>
	 * @return 勤務形態略称
	 */
	public String getWorkTypeAbbr();
	
	/**
	 * 始業時刻を取得する。<br>
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getStartWorkTime() throws MospException;
	
	/**
	 * 終業時刻を取得する。<br>
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getEndWorkTime() throws MospException;
	
	/**
	 * 勤務時間(分)を取得する。<br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public int getWorkTime() throws MospException;
	
	/**
	 * 休憩時間(分)を取得する。<br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public int getRestTime() throws MospException;
	
	/**
	 * 休憩1開始時刻を取得する。<br>
	 * @return 休憩1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getRest1StartTime() throws MospException;
	
	/**
	 * 休憩1終了時刻を取得する。<br>
	 * @return 休憩1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getRest1EndTime() throws MospException;
	
	/**
	 * 前半休開始時刻を取得する。<br>
	 * <br>
	 * 後半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 前半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getFrontStartTime() throws MospException;
	
	/**
	 * 前半休終了時刻を取得する。<br>
	 * <br>
	 * 後半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 前半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getFrontEndTime() throws MospException;
	
	/**
	 * 後半休開始時刻を取得する。<br>
	 * <br>
	 * 前半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 後半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getBackStartTime() throws MospException;
	
	/**
	 * 後半休終了時刻を取得する。<br>
	 * <br>
	 * 前半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 後半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getBackEndTime() throws MospException;
	
	/**
	 * 直行かどうかを確認する。<br>
	 * @return 確認結果(true：直行、false：直行でない)
	 */
	public boolean isDirectStart();
	
	/**
	 * 直帰かどうかを確認する。<br>
	 * @return 確認結果(true：直帰、false：直帰でない)
	 */
	public boolean isDirectEnd();
	
	/**
	 * 割増休憩除外が有効であるかを確認する。<br>
	 * @return 確認結果(true：割増休憩除外が有効である、false：有効でない)
	 */
	public boolean isNightRestExclude();
	
	/**
	 * 時短時間1開始時刻を取得する。<br>
	 * @return 時短時間1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getShort1StartTime() throws MospException;
	
	/**
	 * 時短時間1終了時刻を取得する。<br>
	 * @return 時短時間1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getShort1EndTime() throws MospException;
	
	/**
	 * 時短時間1給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間1給与区分が有給、false：無給)
	 */
	public boolean isShort1TypePay();
	
	/**
	 * 時短時間1が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間1が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public boolean isShort1TimeSet() throws MospException;
	
	/**
	 * 時短時間2開始時刻を取得する。<br>
	 * @return 時短時間2開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getShort2StartTime() throws MospException;
	
	/**
	 * 時短時間2終了時刻を取得する。<br>
	 * @return 時短時間2終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getShort2EndTime() throws MospException;
	
	/**
	 * 時短時間2給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間2給与区分が有給、false：無給)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public boolean isShort2TypePay() throws MospException;
	
	/**
	 * 時短時間2が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間2が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public boolean isShort2TimeSet() throws MospException;
	
	/**
	 * 勤務対象勤務形態であるかを確認する。<br>
	 * 未設定(休暇等)、所定休日、法定休日の場合は、勤務対象勤務形態でないと判断する。<br>
	 * @return 確認結果(true：勤務対象勤務形態である、false：そうでない。)
	 */
	public boolean isWorkTypeForWork();
	
	/**
	 * 始業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、始業時刻を勤務日の時刻として算出する。<br>
	 * 各種申請は、申請済(下書、取下、一次戻以外)を対象とする。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、残業申請及び時差出勤申請は、考慮しない。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.前半休の場合：<br>
	 * 勤務形態の後半休開始時刻を返す。<br>
	 * 但し、残業申請(勤務前残業)がある場合は、そこから残業申請(勤務前残業)の
	 * 申請時間(分)を減算する(前半休の場合は前半休終了時刻が前残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の出勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間1が設定されている場合：<br>
	 * 時短時間1終了時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の終了時刻を返す。<br>
	 * 時短時間1(無給)が設定されている場合は、時短時間1終了時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の始業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の終了時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の始業時刻を返す。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getStartTime(RequestEntity requestEntity) throws MospException;
	
	/**
	 * 終業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、終業時刻を勤務日の時刻として算出する。<br>
	 * 各種申請は、申請済(下書、取下、一次戻以外)を対象とする。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、時差出勤申請は、考慮しない。<br>
	 * 全休の場合は、nullを返す。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.後半休の場合：<br>
	 * 勤務形態の前半休終了時刻を返す。<br>
	 * 但し、残業申請(勤務後残業)がある場合は、そこに残業申請(勤務後残業)の
	 * 申請時間(分)を加算する(後半休の場合は後半休開始時刻が後残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の退勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間2が設定されている場合：<br>
	 * 時短時間2開始時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の開始時刻を返す。<br>
	 * 時短時間2(無給)が設定されている場合は、時短時間2開始時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の終業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の開始時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の終業時刻を返す。<br>
	 * <br>
	 * @param requestEntity  申請エンティティ
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getEndTime(RequestEntity requestEntity) throws MospException;
	
	/**
	 * 有効日を取得する。<br>
	 * 勤務形態情報が未設定の場合は、nullを返す。<br>
	 * <br>
	 * @return 有効日
	 */
	public Date getActivateDate();
	
	/**
	 * 勤務形態項目値を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、デフォルト時刻を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public Date getItemValue(String workTypeItemCode) throws MospException;
	
	/**
	 * 勤務形態項目情報を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、nullを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目情報
	 */
	public WorkTypeItemDtoInterface getWorkTypeItem(String workTypeItemCode);
}
