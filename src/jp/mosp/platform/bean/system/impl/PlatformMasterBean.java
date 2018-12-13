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
package jp.mosp.platform.bean.system.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.system.AppPropertyDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.AppPropertyDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * プラットフォームマスタ参照処理。<br>
 * <br>
 * 取得したマスタの情報をフィールドに保持しておき、
 * DBにアクセスすることなく再取得できるようにする。<br>
 * <br>
 * 一覧表示で一件ずつ処理する場合等、一度取得した
 * マスタの情報を使い回すことで、パフォーマンスの向上を図る。<br>
 * <br>
 * 一覧表示で件数が少ない場合にパフォーマンスが落ちないように、
 * マスタ全件を一度に取得するような処理は行わない。<br>
 * <br>
 * DBにアクセスする回数が減る分メモリを使うことになるため、
 * 保持する情報の量に応じてメモリを調整する必要がある。<br>
 * <br>
 */
public class PlatformMasterBean extends PlatformBean implements PlatformMasterBeanInterface {
	
	/**
	 * ルート適用マスタDAOクラス。<br>
	 */
	protected RouteApplicationDaoInterface					routeApplicationDao;
	
	/**
	 * 人事マスタDAOクラス。<br>
	 */
	protected HumanDaoInterface								humanDao;
	
	/**
	 * 所属マスタDAO。<br>
	 */
	protected SectionDaoInterface							sectionDao;
	
	/**
	 * アプリケーション設定マスタDAO。<br>
	 */
	protected AppPropertyDaoInterface						appPropertyDao;
	
	/**
	 * ルート適用情報(個人)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<RouteApplicationDtoInterface>>	applicationPersonMap;
	
	/**
	 * ルート適用情報(マスタ)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<RouteApplicationDtoInterface>>	applicationMasterMap;
	
	/**
	 * 人事情報履歴群(キー：個人ID)。<br>
	 * 一度DBから取得した情報を保持しておき、再利用する。<br>
	 * <br>
	 */
	protected Map<String, List<HumanDtoInterface>>			humanMap;
	
	/**
	 * 所属情報群(キー：所属コード、対象日)。<br>
	 * 一度DBから取得した情報を保持しておき、再利用する。<br>
	 * <br>
	 */
	protected Map<String, Map<Date, SectionDtoInterface>>	sectionMap;
	
	/**
	 * アプリケーション設定情報群(キー：アプリケーション設定キー)。<br>
	 */
	protected Map<String, AppPropertyDtoInterface>			appPropertyMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PlatformMasterBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PlatformMasterBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOクラス準備
		routeApplicationDao = (RouteApplicationDaoInterface)createDao(RouteApplicationDaoInterface.class);
		humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		sectionDao = (SectionDaoInterface)createDao(SectionDaoInterface.class);
		appPropertyDao = (AppPropertyDaoInterface)createDao(AppPropertyDaoInterface.class);
		// フィールドの初期化
		applicationPersonMap = new HashMap<Date, Set<RouteApplicationDtoInterface>>();
		applicationMasterMap = new HashMap<Date, Set<RouteApplicationDtoInterface>>();
		humanMap = new HashMap<String, List<HumanDtoInterface>>();
		sectionMap = new HashMap<String, Map<Date, SectionDtoInterface>>();
		appPropertyMap = new HashMap<String, AppPropertyDtoInterface>();
	}
	
	@Override
	public RouteApplicationDtoInterface getRouteApplication(HumanDtoInterface humanDto, Date targetDate,
			int workflowType) throws MospException {
		// ルート適用情報群取得
		Set<RouteApplicationDtoInterface> personSet = getApplicationPersonSet(targetDate, workflowType);
		Set<RouteApplicationDtoInterface> masterSet = getApplicationMasterSet(targetDate, workflowType);
		// 適用情報群から対象人事情報が適用される適用情報を取得
		return (RouteApplicationDtoInterface)PlatformUtility.getApplicationMaster(humanDto, personSet, masterSet);
	}
	
	@Override
	public RouteApplicationDtoInterface getRouteApplication(String personalId, Date targetDate, int workflowType)
			throws MospException {
		// 人事情報を取得
		HumanDtoInterface humanDto = getHuman(personalId, targetDate);
		// ルート適用情報を取得
		return getRouteApplication(humanDto, targetDate, workflowType);
	}
	
	/**
	 * ルート適用情報(個人)群(キー：対象日)を取得する。<br>
	 * <br>
	 * 対象フロー区分の情報のみを抽出する。<br>
	 * <br>
	 * フィールドからルート適用情報(個人)群を取得できなかった場合は、
	 * ルート適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドからルート適用情報(個人)群を再取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param workflowType フロー区分
	 * @return ルート適用情報(個人)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<RouteApplicationDtoInterface> getApplicationPersonSet(Date targetDate, int workflowType)
			throws MospException {
		// フィールドからルート適用情報(個人)群を取得できなかった場合
		if (applicationPersonMap.get(targetDate) == null) {
			// ルート適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドからルート適用情報(個人)群を再取得
		Set<RouteApplicationDtoInterface> set = applicationPersonMap.get(targetDate);
		// ルート適用情報群から対象フロー区分のルート適用情報を抽出
		return getApplicationSet(set, workflowType);
		
	}
	
	/**
	 * ルート適用情報(マスタ)群(キー：対象日)を取得する。<br>
	 * <br>
	 * 対象フロー区分の情報のみを抽出する。<br>
	 * <br>
	 * フィールドからルート適用情報(マスタ)群を取得できなかった場合は、
	 * ルート適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドからルート適用情報(マスタ)群を再取得する。<br>
	 * <br>
	 * @param targetDate   対象日
	 * @param workflowType フロー区分
	 * @return ルート適用情報(マスタ)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<RouteApplicationDtoInterface> getApplicationMasterSet(Date targetDate, int workflowType)
			throws MospException {
		// フィールドからルート適用情報(マスタ)群を取得できなかった場合
		if (applicationMasterMap.get(targetDate) == null) {
			// ルート適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドからルート適用情報(マスタ)群を再取得
		Set<RouteApplicationDtoInterface> set = applicationMasterMap.get(targetDate);
		// ルート適用情報群から対象フロー区分のルート適用情報を抽出
		return getApplicationSet(set, workflowType);
	}
	
	/**
	 * 適用情報群をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApplicationSet(Date targetDate) throws MospException {
		// 適用情報群を準備
		Set<RouteApplicationDtoInterface> personSet = new HashSet<RouteApplicationDtoInterface>();
		Set<RouteApplicationDtoInterface> masterSet = new HashSet<RouteApplicationDtoInterface>();
		// フィールドに設定
		applicationPersonMap.put(targetDate, personSet);
		applicationMasterMap.put(targetDate, masterSet);
		// 適用範囲区分(比較用)を準備
		int person = Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON);
		int master = Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER);
		// 適用情報リストをDBから取得
		List<RouteApplicationDtoInterface> list = routeApplicationDao.findForActivateDate(targetDate);
		// 適用情報毎に処理
		for (RouteApplicationDtoInterface dto : list) {
			// 適用範囲区分が個人指定の場合
			if (dto.getRouteApplicationType() == person) {
				// 適用情報(個人)群に追加
				personSet.add(dto);
			}
			// 適用範囲区分がマスタ指定の場合
			if (dto.getRouteApplicationType() == master) {
				// 適用情報(マスタ)群に追加
				masterSet.add(dto);
			}
		}
	}
	
	/**
	 * ルート適用情報群から対象フロー区分のルート適用情報を抽出する。<br>
	 * @param set          ルート適用情報群
	 * @param workflowType フロー区分
	 * @return ルート適用情報群
	 */
	protected Set<RouteApplicationDtoInterface> getApplicationSet(Set<RouteApplicationDtoInterface> set,
			int workflowType) {
		// 抽出用ルート適用情報群を準備
		Set<RouteApplicationDtoInterface> applicationSet = new HashSet<RouteApplicationDtoInterface>();
		// ルート適用情報毎に処理
		for (RouteApplicationDtoInterface dto : set) {
			// フロー区分が合致する場合
			if (dto.getWorkflowType() == workflowType) {
				// 抽出
				applicationSet.add(dto);
			}
		}
		// 抽出用ルート適用情報群を取得
		return applicationSet;
	}
	
	@Override
	public HumanDtoInterface getHuman(String personalId, Date targetDate) throws MospException {
		// 人事情報履歴群から取得
		List<HumanDtoInterface> list = getHumanHistory(personalId);
		// 対象日以前で最新の情報を取得
		return (HumanDtoInterface)PlatformUtility.getLatestDto(list, targetDate);
	}
	
	@Override
	public HumanDtoInterface getHuman(String personalId, int targetYear, int targetMonth) throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 人事情報を取得
		return getHuman(personalId, targetDate);
	}
	
	@Override
	public List<HumanDtoInterface> getHumanHistory(String personalId) throws MospException {
		// 人事情報履歴群から取得
		List<HumanDtoInterface> list = humanMap.get(personalId);
		// 人事情報履歴群から取得できなかった場合
		if (list == null) {
			// DBから取得し勤怠集計管理情報群に設定
			list = humanDao.findForHistory(personalId);
			humanMap.put(personalId, list);
		}
		// 人事情報履歴を取得
		return list;
	}
	
	@Override
	public String getSectionName(String sectionCode, Date targetDate) throws MospException {
		// 所属情報を取得
		SectionDtoInterface dto = getSection(sectionCode, targetDate);
		// 所属情報を取得できなかった場合
		if (dto == null) {
			// 所属コードを取得
			return sectionCode;
		}
		// 所属名称を取得
		return dto.getSectionName();
	}
	
	@Override
	public String getSectionAbbr(String sectionCode, Date targetDate) throws MospException {
		// 所属情報を取得
		SectionDtoInterface dto = getSection(sectionCode, targetDate);
		// 所属情報を取得できなかった場合
		if (dto == null) {
			// 所属コードを取得
			return sectionCode;
		}
		// 所属略称を取得
		return dto.getSectionAbbr();
	}
	
	/**
	 * 所属情報を取得する。<br>
	 * 所属情報群(キー：所属コード、対象日)に情報が存在する場合は、
	 * 所属情報群から取得し、DBからは取得しない。<br>
	 * <br>
	 * @param sectionCode 所属コード
	 * @param targetDate  対象日
	 * @return 所属情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected SectionDtoInterface getSection(String sectionCode, Date targetDate) throws MospException {
		// 所属情報群(キー：対象日)を取得
		Map<Date, SectionDtoInterface> map = sectionMap.get(sectionCode);
		// 所属情報群(キー：対象日)が取得できなかった場合
		if (map == null) {
			// 所属情報群(キー：対象日)を作成
			map = new HashMap<Date, SectionDtoInterface>();
			// 所属情報群(キー：対象日)を所属情報群(キー：所属コード、対象日)に設定
			sectionMap.put(sectionCode, map);
		}
		// 所属情報を取得
		SectionDtoInterface dto = map.get(targetDate);
		// 所属情報が取得できなかった場合
		if (dto == null) {
			// 所属情報をDBから取得
			dto = sectionDao.findForInfo(sectionCode, targetDate);
			// 所属情報を所属情報群(キー：対象日)に設定
			map.put(targetDate, dto);
		}
		// 所属情報を取得
		return dto;
	}
	
	@Override
	public String getAppProperty(String appKey) throws MospException {
		// MosPアプリケーション設定値を設定ファイル(XML)から取得
		String value = mospParams.getApplicationProperty(appKey);
		// アプリケーション設定情報(DB)を取得
		AppPropertyDtoInterface dto = getAppPropertyDto(appKey);
		// 対象となるアプリケーション設定情報(DB)が存在する場合
		if (dto != null) {
			// アプリケーション設定値を取得
			value = dto.getAppValue();
		}
		// 値が取得できなかった場合
		if (MospUtility.isEmpty(value)) {
			// 空文字を設定
			value = MospConst.STR_EMPTY;
		}
		// アプリケーション設定値を取得
		return value;
	}
	
	@Override
	public int getAppPropertyInt(String appKey) throws MospException {
		// MosPアプリケーション設定値から数値を取得(取得できなかった場合は0)
		return MospUtility.getInt(getAppProperty(appKey));
	}
	
	/**
	 * アプリケーション設定情報(DB)を取得する。<br>
	 * アプリケーション設定情報群(キー：アプリケーション設定キー)に情報が存在する場合は、
	 * アプリケーション設定情報群から取得し、DBからは取得しない。<br>
	 * <br>
	 * @param appKey アプリケーション設定キー
	 * @return アプリケーション設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AppPropertyDtoInterface getAppPropertyDto(String appKey) throws MospException {
		// アプリケーション設定情報群(キー：アプリケーション設定キー)から取得
		AppPropertyDtoInterface dto = appPropertyMap.get(appKey);
		// アプリケーション設定情報が取得できなかった場合
		if (dto == null) {
			// アプリケーション設定情報をDBから取得
			dto = appPropertyDao.findForKey(appKey);
			// システム管理情報をシステム管理情報群(キー：システム管理コード)に設定
			appPropertyMap.put(appKey, dto);
		}
		// システム管理情報を取得
		return dto;
	}
	
}
