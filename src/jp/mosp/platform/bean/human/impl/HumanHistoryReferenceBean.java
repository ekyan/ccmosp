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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用履歴情報参照クラス。
 */
public class HumanHistoryReferenceBean extends HumanGeneralBean implements HumanHistoryReferenceBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAO。
	 */
	protected HumanHistoryDaoInterface			dao;
	
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
	 * 人事汎用項目情報リスト
	 */
	protected List<TableItemProperty>			tableItemList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanHistoryReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected HumanHistoryReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		dao = (HumanHistoryDaoInterface)createDao(HumanHistoryDaoInterface.class);
		humanGeneral = (HumanGeneralBeanInterface)createBean(HumanGeneralBeanInterface.class);
	}
	
	@Override
	public void setCommounInfo(String division, String viewKey) {
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 人事汎用項目情報リストを取得
		tableItemList = getTableItemList(division, viewKey);
		// 人事有効日履歴情報汎用マップ初期化
		historyHumanInfoMap = new LinkedHashMap<String, Map<String, String>>();
		// 人事履歴情報汎用マップ準備
		historyMap = new HashMap<String, String>();
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHistoryMapInfo(String division, String viewKey,
			String personalId, Date activeDate) throws MospException {
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
				HumanHistoryDtoInterface dto = findForKey(personalId, itemName, activeDate);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					historyMap.put(itemName, "");
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						false);
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					// 項目が終りの場合
					if (i == itemNames.length - 1) {
						historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
					}
					continue;
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
				historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
			}
		}
		return historyHumanInfoMap;
	}
	
	@Override
	public Map<String, String> getBeforeHumanHistoryMapInfo(String division, String viewKey, String personalId,
			Date activeDate, Date targetDate) throws MospException {
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
				HumanHistoryDtoInterface dto = findForInfo(personalId, itemName, activeDate);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					List<HumanHistoryDtoInterface> humanList = findForHistory(personalId, itemName);
					if (humanList.isEmpty()) {
						historyMap.put(itemName, "");
						continue;
					}
					dto = humanList.get(0);
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						false);
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					continue;
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
			}
		}
		return historyMap;
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHumanHistoryMapInfo(String division, String viewKey,
			String personalId, Date activeDate, Date targetDate) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目ラベルキーを取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目ラベルキーを取得する
				String labelKey = labelKeys[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// フォーマットに合わせ合体させたデータを取得
				String dateTypeValue = getSeparateTxtItemHistoryValue(personalId, itemName, itemProperty, activeDate,
						targetDate, labelKey);
				
				if (dateTypeValue.isEmpty() == false) {
					// 設定
					historyMap.put(itemName, dateTypeValue);
					
					// 日付有効日取得
					HumanHistoryDtoInterface dateDto = findForInfo(personalId, itemName + "Year", activeDate);
					if (dateDto != null) {
						historyHumanInfoMap.put(DateUtility.getStringDate(dateDto.getActivateDate()), historyMap);
						continue;
					}
					
					// 電話有効日取得
					HumanHistoryDtoInterface phoneDto = findForInfo(personalId, itemName + "Area", activeDate);
					if (phoneDto != null) {
						historyHumanInfoMap.put(DateUtility.getStringDate(phoneDto.getActivateDate()), historyMap);
						continue;
					}
					
					// カンマ区切り
					String[] aryItemName = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
					
					if (aryItemName.length != 0) {
						
						// 上記以外のフォーマットの場合
						HumanHistoryDtoInterface formatTxtDto = findForInfo(personalId, aryItemName[0], activeDate);
						if (formatTxtDto != null) {
							historyHumanInfoMap.put(DateUtility.getStringDate(formatTxtDto.getActivateDate()),
									historyMap);
							continue;
							
						}
						
					}
					
					continue;
				}
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface dto = findForInfo(personalId, itemName, activeDate);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					continue;
				}
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						true);
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					// 項目が終りの場合
					if (i == itemNames.length - 1) {
						historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
					}
					continue;
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
				historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
			}
		}
		return historyHumanInfoMap;
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getActiveDateHistoryMapInfo(String division, String viewKey,
			String personalId, Date targetDate) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// 有効日セット取得
		HashSet<Date> activeSet = getActiveDateList(tableItemList, personalId);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目ラベルキーを取得する
			String[] labelKeys = tableItem.getLabelKeys();
			// 有効日セット毎に処理
			for (Date activeDate : activeSet) {
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemNames.length; i++) {
					// 有効日取得
					String date = DateUtility.getStringDate(activeDate);
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					// フォーマットに合わせ合体させたデータを取得
					String dateTypeValue = getSeparateTxtItemHistoryValue(personalId, itemNames[i], itemProperty,
							activeDate, targetDate, labelKeys[i]);
					if (dateTypeValue.isEmpty() == false) {
						// プルダウンのコードから名称を取得し設定
						Map<String, String> historyMap = historyHumanInfoMap.get(date);
						if (historyMap == null) {
							historyMap = new HashMap<String, String>();
							// 有効日人事汎用履歴情報マップに詰める
							historyHumanInfoMap.put(date, historyMap);
						}
						historyMap.put(itemNames[i], dateTypeValue);
						continue;
					}
					// 人事汎用履歴情報取得
					HumanHistoryDtoInterface dto = findForInfo(personalId, itemNames[i], activeDate);
					if (dto == null) {
						historyMap.put(itemNames[i], "");
						continue;
					}
					// プルダウン名称を取得する
					String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(),
							dto.getHumanItemValue(), itemNames[i], true);
					if (pulldownValue.isEmpty() == false) {
						// プルダウンのコードから名称を取得し設定
						Map<String, String> historyMap = historyHumanInfoMap.get(date);
						if (historyMap == null) {
							historyMap = new HashMap<String, String>();
							// 有効日人事汎用履歴情報マップに詰める
							historyHumanInfoMap.put(date, historyMap);
						}
						historyMap.put(itemNames[i], pulldownValue);
						continue;
					}
					// 人事情報汎用マップ準備
					Map<String, String> historyMap = historyHumanInfoMap.get(date);
					if (historyMap == null) {
						historyMap = new HashMap<String, String>();
						// 有効日人事汎用履歴情報マップに詰める
						historyHumanInfoMap.put(date, historyMap);
					}
					// 人事情報汎用マップに詰める
					historyMap.put(dto.getHumanItemType(), dto.getHumanItemValue());
				}
			}
		}
		return historyHumanInfoMap;
	}
	
	@Override
	public String getHistoryItemValue(String division, String viewKey, String personalId, Date targetDate,
			String tableItemKey, boolean isPulldownName) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// 有効日セット取得
		HashSet<Date> activeSet = getActiveDateList(tableItemList, personalId);
		// 人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーが違う場合
			if (tableItem.getKey().equals(tableItemKey) == false) {
				continue;
			}
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目ラベルキー取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 有効日セット毎に処理
			for (Date activeDate : activeSet) {
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemKeys.length;) {
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					// 人事汎用項目キーが有効日の場合
					if (itemKeys[i].equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE)) {
						// 人事汎用履歴情報取得
						HumanHistoryDtoInterface dto = findForInfo(personalId, itemNames[i], activeDate);
						if (dto == null) {
							return "";
						}
						// 有効日取得
						return getStringDate(dto.getActivateDate());
					}
					// フォーマットに合わせ合体させたデータを取得
					String dateTypeValue = getSeparateTxtItemHistoryValue(personalId, itemNames[i], itemProperty,
							activeDate, targetDate, labelKeys[i]);
					if (dateTypeValue.isEmpty() == false) {
						return dateTypeValue;
					}
					// 人事汎用履歴情報取得
					HumanHistoryDtoInterface dto = findForInfo(personalId, itemNames[i], activeDate);
					if (dto == null) {
						return "";
					}
					// プルダウン名称を取得する
					String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(),
							dto.getHumanItemValue(), itemNames[i], isPulldownName);
					if (pulldownValue.isEmpty() == false) {
						return pulldownValue;
					}
					return dto.getHumanItemValue();
				}
			}
		}
		return "";
		
	}
	
	@Override
	public List<HumanHistoryDtoInterface> findForHistory(String personalId, String humanItemType) throws MospException {
		return dao.findForHistory(personalId, humanItemType);
	}
	
	@Override
	public HumanHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate)
			throws MospException {
		return dao.findForInfo(personalId, humanItemType, targetDate);
	}
	
	@Override
	public HumanHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException {
		return dao.findForKey(personalId, humanItemType, activateDate);
	}
	
	/**
	 * 対象項目の有効日セットを取得する。
	 * @param tableItemList 人事汎用表示テーブル項目設定情報リスト
	 * @param personalId 個人ID
	 * @return 有効日セット
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public HashSet<Date> getActiveDateList(List<TableItemProperty> tableItemList, String personalId)
			throws MospException {
		// 有効日リスト準備
		List<Date> activeDateList = new ArrayList<Date>();
		for (TableItemProperty tableItem : tableItemList) {
			String[] itemNames = tableItem.getItemNames();
			// 項目毎に処理
			for (String itemName : itemNames) {
				// 配列に変換
				String[] itemNameSplit = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
				// 項目名配列毎に処理
				for (String item : itemNameSplit) {
					// 項目値リスト取得
					List<HumanHistoryDtoInterface> valueList = findForHistory(personalId, item);
					// 項目値リスト毎に処理
					for (HumanHistoryDtoInterface dto : valueList) {
						// 有効日を詰める
						activeDateList.add(dto.getActivateDate());
					}
				}
			}
		}
		// 有効日セット準備
		HashSet<Date> activeDateSet = new HashSet<Date>();
		// 重複削除
		activeDateSet.addAll(activeDateList);
		
		return activeDateSet;
		
	}
	
	@Override
	public String[] getArrayActiveDate(LinkedHashMap<String, Map<String, String>> activeDateHistoryMapInfo) {
		// 有効日リスト取得
		List<String> activeList = new ArrayList<String>(activeDateHistoryMapInfo.keySet());
		String[] arrayActiveDate = new String[activeList.size()];
		if (activeList.isEmpty()) {
			return arrayActiveDate;
		}
		// ソート
		Collections.sort(activeList);
		for (int i = 0; i < activeList.size(); i++) {
			arrayActiveDate[i] = activeList.get(activeList.size() - i - 1);
		}
		
		return arrayActiveDate;
	}
	
}
