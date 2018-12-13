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
package jp.mosp.platform.bean.human.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.dao.human.HumanBinaryHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;

/**
 * 人事汎用バイナリ履歴情報参照クラス。
 */
public class HumanBinaryHistoryReferenceBean extends HumanGeneralBean implements
		HumanBinaryHistoryReferenceBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAO。
	 */
	protected HumanBinaryHistoryDaoInterface	dao;
	
	/**
	 * 人事汎用管理機能クラス。
	 */
	protected HumanGeneralBeanInterface			humanGeneral;
	
	/**
	 * 人事汎用項目区分設定情報。
	 */
	protected ConventionProperty				conventionProperty;
	
	/**
	 * 人事有効日履歴情報汎用マップ。
	 */
	LinkedHashMap<String, Map<String, String>>	historyHumanInfoMap;
	
	/**
	 * 人事履歴情報汎用マップ準備。
	 */
	Map<String, String>							historyMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanBinaryHistoryReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected HumanBinaryHistoryReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		dao = (HumanBinaryHistoryDaoInterface)createDao(HumanBinaryHistoryDaoInterface.class);
		humanGeneral = (HumanGeneralBeanInterface)createBean(HumanGeneralBeanInterface.class);
	}
	
	@Override
	public List<HumanBinaryHistoryDtoInterface> findForHistory(String personalId, String humanItemType)
			throws MospException {
		return dao.findForHistory(personalId, humanItemType);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate)
			throws MospException {
		return dao.findForInfo(personalId, humanItemType, targetDate);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException {
		return dao.findForKey(personalId, humanItemType, activateDate);
	}
	
}
