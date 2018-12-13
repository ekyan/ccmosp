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
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 部下一覧検索インターフェース。
 */
public interface SubordinateSearchBeanInterface extends HumanSearchBeanInterface {
	
	/**
	 * 部下リストを取得する。<br><br>
	 * 設定された条件で、検索を行う。
	 * @return 部下リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SubordinateListDtoInterface> getSubordinateList() throws MospException;
	
	/**
	 * 部下個人IDセットを取得する。<br><br>
	 * 設定された条件で、検索を行う。<br>
	 * @return 部下個人IDセット
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	Set<String> getSubordinateIdSet() throws MospException;
	
	/**
	 * 承認対象社員リストを取得する。<br><br>
	 * 設定された条件で、検索を行う。<br>
	 * @return 承認対象社員リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SubordinateListDtoInterface> getApprovableList() throws MospException;
	
	/**
	 * 部下一覧情報DTOを取得する。<br><br>
	 * @param humanDto 人事情報
	 * @param year 対象年
	 * @param month 対象月
	 * @param totalTimeDataDto 勤怠集計情報DTO
	 * @param searchApprovalState 承認状態(検索条件)
	 * @param searchCutoffState 締状態(検索条件)
	 * @return 部下一覧情報DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubordinateListDtoInterface getSubordinateListDto(HumanDtoInterface humanDto, int year, int month,
			TotalTimeDataDtoInterface totalTimeDataDto, String searchApprovalState, String searchCutoffState)
			throws MospException;
	
	/**
	 * 人事情報を設定する。<br>
	 * @param dto 対象DTO
	 * @param humanDto 人事情報DTO
	 */
	void setHuman(SubordinateListDtoInterface dto, HumanDtoInterface humanDto);
	
	/**
	 * 勤怠集計データを設定する。<br>
	 * @param dto 対象DTO
	 * @param totalTimeDataDto 勤怠集計データDTO
	 */
	void setTotalTimeData(SubordinateListDtoInterface dto, TotalTimeDataDtoInterface totalTimeDataDto);
	
	/**
	 * @param targetYear セットする 対象年。
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth セットする 対象月。
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param approval セットする 未承認。
	 */
	void setApproval(String approval);
	
	/**
	 * @param calc セットする 締状態。
	 */
	void setCalc(String calc);
	
}
