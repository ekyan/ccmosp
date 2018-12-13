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
package jp.mosp.platform.dto.workflow;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 承認ルートマスタ検索リストDTOインターフェース。
 */
public interface ApprovalRouteListDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmApprovalRouteId();
	
	/**
	 * @param pfmApprovalRouteId セットする レコード識別ID。
	 */
	void setPfmApprovalRouteId(long pfmApprovalRouteId);
	
	/**
	 * @return ルートコード。
	 */
	String getRouteCode();
	
	/**
	 * @param routeCode セットする ルートコード。
	 */
	void setRouteCode(String routeCode);
	
	/**
	 * @return ルート名称。
	 */
	String getRouteName();
	
	/**
	 * @param routeName セットする ルート名称。
	 */
	void setRouteName(String routeName);
	
	/**
	 * @return 階層数。
	 */
	int getApprovalCount();
	
	/**
	 * @param approvalCount セットする 階層数。
	 */
	void setApprovalCount(int approvalCount);
	
	/**
	 * @return 一次ユニットコード。
	 */
	String getFirstUnitCode();
	
	/**
	 * @param firstUnitCode セットする 一次ユニットコード。
	 */
	void setFirstUnitCode(String firstUnitCode);
	
	/**
	 * @return 最終ユニットコード。
	 */
	String getLastUnitCode();
	
	/**
	 * @param lastUnitCode セットする 最終ユニットコード。
	 */
	void setLastUnitCode(String lastUnitCode);
	
}
