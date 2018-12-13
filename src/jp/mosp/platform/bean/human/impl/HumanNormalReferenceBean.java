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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用通常情報参照クラス。
 */
public class HumanNormalReferenceBean extends HumanGeneralBean implements HumanNormalReferenceBeanInterface {
	
	/**
	 * 人事通常情報DAO。
	 */
	private HumanNormalDaoInterface		normalDao;
	
	/**
	 * 人事汎用項目区分設定情報。
	 */
	protected ConventionProperty		conventionProperty;
	
	/**
	 * 人事汎用項目情報リスト
	 */
	protected List<TableItemProperty>	tableItemList;
	
	/**
	 * 人事通常情報汎用マップ
	 */
	protected Map<String, String>		normalMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanNormalReferenceBean() {
		// 処理無し
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	protected HumanNormalReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		normalDao = (HumanNormalDaoInterface)createDao(HumanNormalDaoInterface.class);
	}
	
	@Override
	public HumanNormalDtoInterface getHumanNormalInfo(String itemName, String personalId) throws MospException {
		return normalDao.findForInfo(itemName, personalId);
	}
	
	@Override
	public void setCommounInfo(String division, String viewKey) {
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 人事汎用項目情報リスト取得
		tableItemList = getTableItemList(division, viewKey);
		// 人事通常情報汎用マップ初期化
		normalMap = new HashMap<String, String>();
	}
	
	@Override
	public Map<String, String> getHumanNormalMapInfo(String division, String viewKey, String personalId,
			Date activeDate, boolean isPulldownName) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface dto = normalDao.findForInfo(itemName, personalId);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					normalMap.put(itemName, "");
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウンがあるか確認し、あった場合名称又はコードで取得
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						isPulldownName);
				if (pulldownValue.isEmpty() == false) {
					normalMap.put(itemName, pulldownValue);
					continue;
				}
				// 値を設定
				normalMap.put(itemName, dto.getHumanItemValue());
			}
		}
		return normalMap;
	}
	
	@Override
	public Map<String, String> getShowHumanNormalMapInfo(String division, String viewKey, String personalId,
			Date activeDate, Date targetDate) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目ラベルキー取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目ラベルキー取得
				String labelKey = labelKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// フォーマットに合わせ合体させたデータを取得
				String dateTypeValue = getSeparateTxtItemNormalValue(personalId, itemName, itemProperty, targetDate,
						labelKey);
				if (dateTypeValue.isEmpty() == false) {
					//詰める
					normalMap.put(itemName, dateTypeValue);
					continue;
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface dto = normalDao.findForInfo(itemName, personalId);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					continue;
				}
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						true);
				if (pulldownValue.isEmpty() == false) {
					normalMap.put(itemName, pulldownValue);
					continue;
				}
				// 値を設定
				normalMap.put(itemName, dto.getHumanItemValue());
			}
		}
		return normalMap;
	}
	
	@Override
	public String getNormalItemValue(String division, String viewKey, String personalId, Date targetDate,
			Date activeDate, String tableItemKey, boolean isPulldownName) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 項目キーが同じでない場合
			if (tableItem.getKey().equals(tableItemKey) == false) {
				continue;
			}
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目ラベルキー取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目ラベルキー取得
				String labelKey = labelKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// フォーマットに合わせ合体させたデータを取得
				String dateTypeValue = getSeparateTxtItemNormalValue(personalId, itemName, itemProperty, targetDate,
						labelKey);
				if (dateTypeValue.isEmpty() == false) {
					return dateTypeValue;
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface dto = normalDao.findForInfo(itemName, personalId);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					continue;
				}
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						true);
				if (pulldownValue.isEmpty() == false) {
					return pulldownValue;
				}
				// 値を設定
				return dto.getHumanItemValue();
			}
		}
		return "";
	}
}
