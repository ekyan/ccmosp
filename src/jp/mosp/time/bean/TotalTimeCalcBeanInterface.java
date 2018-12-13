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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計インターフェース。
 */
public interface TotalTimeCalcBeanInterface {
	
	/**
	 * 計算情報を設定する。<br>
	 * 勤怠集計処理を行う前に、計算年月及び締日コードから必要な情報を設定する。<br>
	 * @param calculationYear  計算年
	 * @param calculationMonth 計算月
	 * @param cutoffCode       締日コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void setCalculationInfo(int calculationYear, int calculationMonth, String cutoffCode) throws MospException;
	
	/**
	 * 勤怠集計処理を行う。<br>
	 * {@link TotalTimeCalcBeanInterface#setCalculationInfo(int, int, String)}
	 * で設定された計算情報を基に、対象個人IDの勤怠集計処理を行う。<br>
	 * 対象個人IDの集計時にエラーを検知しなければ、勤怠集計データの登録を行う。<br>
	 * 対象個人IDの集計時に検知したエラー内容のリスト(無ければ空のリスト)を返す。<br>
	 * 但し、マスタの不備等で計算できなかった場合は、
	 * {@link MospParams}にエラーメッセージを追加し、nullを返す。
	 * @param personalId 対象個人ID
	 * @return 集計エラー内容リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<CutoffErrorListDtoInterface> calc(String personalId) throws MospException;
	
	/**
	 * 勤怠集計処理をした勤怠集計データを取得する。<br>
	 * {@link TotalTimeCalcBeanInterface#setCalculationInfo(int, int, String)}
	 * で設定された計算情報を基に、対象個人IDの勤怠集計処理を行う。<br>
	 * 但し、マスタの不備等で計算できなかった場合は、nullを返す。
	 * @param personalId 対象個人ID
	 * @return 勤怠集計処理をした勤怠集計データ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	TotalTimeDataDtoInterface getTotaledTimeData(String personalId) throws MospException;
	
	/**
	 * 承認状況取得。
	 * @param personalId 個人ID
	 * @param year 年
	 * @param month 月
	 * @return 未承認がない場合は0、未承認がある場合は1
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	int getApprovalStatus(String personalId, int year, int month) throws MospException;
	
}
