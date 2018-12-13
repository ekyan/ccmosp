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
package jp.mosp.platform.bean.workflow.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタ参照クラス。
 */
public class RouteApplicationReferenceBean extends PlatformBean implements RouteApplicationReferenceBeanInterface {
	
	/**
	 * ルート適用マスタDAO。
	 */
	private RouteApplicationDaoInterface	dao;
	private HumanDaoInterface				humanDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public RouteApplicationReferenceBean() {
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public RouteApplicationReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (RouteApplicationDaoInterface)createDao(RouteApplicationDaoInterface.class);
	}
	
	@Override
	public RouteApplicationDtoInterface findForKey(String routeApplicationCode, Date activateDate) throws MospException {
		return dao.findForKey(routeApplicationCode, activateDate);
	}
	
	@Override
	public RouteApplicationDtoInterface getRouteApplicationInfo(String routeApplicationCode, Date targetDate)
			throws MospException {
		return dao.findForInfo(routeApplicationCode, targetDate);
	}
	
	@Override
	public List<RouteApplicationDtoInterface> getRouteApplicationHistory(String routeApplicationCode)
			throws MospException {
		return dao.findForHistory(routeApplicationCode);
	}
	
	/**
	 * 対象日時点における最新の有効な情報から、以下の方法で順番に
	 * 適用されている情報を探していき、最初に見つかった設定適用情報を返す。<br>
	 *  1.個人ID<br>
	 *  2.職位、所属、雇用契約、勤務地<br>
	 *  3.職位、所属、雇用契約<br>
	 *  4.職位、所属<br>
	 *  5.職位<br>
	 *  6.所属、雇用契約、勤務地<br>
	 *  7.所属、雇用契約<br>
	 *  8.所属<br>
	 *  9.雇用契約、勤務地<br>
	 * 10.雇用契約<br>
	 * 11.勤務地<br>
	 * 12.指定無し<br>
	 */
	@Override
	public RouteApplicationDtoInterface findForPerson(String personalId, Date targetDate, int workflowType)
			throws MospException {
		// DTO準備
		// 1.個人ID
		RouteApplicationDtoInterface dto = dao.findForPersonalId(personalId, targetDate, workflowType);
		if (dto != null) {
			return dto;
		}
		// 人事情報取得準備
		if (humanDao == null) {
			humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		}
		// 人事情報取得
		HumanDtoInterface humanDto = humanDao.findForInfo(personalId, targetDate);
		if (humanDto == null) {
			return dto;
		}
		// 検索条件準備
		String positionCode = humanDto.getPositionCode();
		String sectionCode = humanDto.getSectionCode();
		String employmentContractCode = humanDto.getEmploymentContractCode();
		String workPlaceCode = humanDto.getWorkPlaceCode();
		String blank = "";
		// 2.職位、所属、雇用契約、勤務地
		String[] condition2 = { workPlaceCode, employmentContractCode, sectionCode, positionCode };
		// 3.職位、所属、雇用契約
		String[] condition3 = { blank, employmentContractCode, sectionCode, positionCode };
		// 4.職位、所属
		String[] condition4 = { blank, blank, sectionCode, positionCode };
		// 5.職位
		String[] condition5 = { blank, blank, blank, positionCode };
		// 6.所属、雇用契約、勤務地
		String[] condition6 = { workPlaceCode, employmentContractCode, sectionCode, blank };
		// 7.所属、雇用契約
		String[] condition7 = { blank, employmentContractCode, sectionCode, blank };
		// 8.所属
		String[] condition8 = { blank, blank, sectionCode, blank };
		// 9.雇用契約、勤務地
		String[] condition9 = { workPlaceCode, employmentContractCode, blank, blank };
		// 10.雇用契約
		String[] condition10 = { blank, employmentContractCode, blank, blank };
		// 11.勤務地
		String[] condition11 = { workPlaceCode, blank, blank, blank };
		// 12.指定無し
		String[] condition12 = { blank, blank, blank, blank };
		// 検索条件リスト準備
		List<String[]> conditionList = new ArrayList<String[]>();
		conditionList.add(condition2);
		conditionList.add(condition3);
		conditionList.add(condition4);
		conditionList.add(condition5);
		conditionList.add(condition6);
		conditionList.add(condition7);
		conditionList.add(condition8);
		conditionList.add(condition9);
		conditionList.add(condition10);
		conditionList.add(condition11);
		conditionList.add(condition12);
		// 検索条件毎に承認ルート適用情報を確認
		for (String[] condition : conditionList) {
			// ルート適用情報取得
			dto = dao.findForMaster(targetDate, workflowType, condition[0], condition[1], condition[2], condition[3]);
			if (dto != null) {
				break;
			}
		}
		return dto;
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<RouteApplicationDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
}
