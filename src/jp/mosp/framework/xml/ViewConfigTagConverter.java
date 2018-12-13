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
package jp.mosp.framework.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.ViewConfigProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author yoshida
 *
 */
public class ViewConfigTagConverter implements TagConverterInterface {
	
	String	path;
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// ViewConfig
		Node node = wrapper.getNode();
		int index = wrapper.index;
		path = wrapper.path;
		// キー情報取得
		String key = TagUtility.getKey(node);
		// キー情報確認
		if (key.isEmpty()) {
			// エラーログ出力
			TagUtility.noElementKeyMessage(path, node, index);
			return;
		}
		BaseProperty baseProperty = properties.get(key);
		if (baseProperty == null) {
			baseProperty = new ViewConfigProperty(key);
		}
		ViewConfigProperty property = (ViewConfigProperty)baseProperty;
		// 属性(type)を取得し設定
		property.setType(TagUtility.getString("@type", node));
		// 属性(isHumanExist)を取得し設定
		property.setHumanExist(TagUtility.getBoolean("@isHumanExist", node));
		NodeList list = TagUtility.getElements("./*", node);
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			BaseProperty element = null;
			
			switch (ViewConfig.get(item.getNodeName())) {
				case VIEW:
					element = toView(item);
					break;
				case TABLE:
					element = toViewTable(item);
					break;
				default:
					break;
			}
			if (element == null) {
				TagUtility.invalidMassage(path, item);
			} else {
				property.put(element);
			}
			itemIndex++;
		}
		// 表示設定情報追加
		properties.put(key, property);
	}
	
	ViewProperty toView(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		// 人事汎用管理表示テーブルリスト及び人事汎用管理表示テーブルタイトルリストの準備
		ArrayList<String> tableKeyList = new ArrayList<String>();
		ArrayList<String> tableTitleList = new ArrayList<String>();
		// 人事汎用管理表示テーブル情報取得
		NodeList list = TagUtility.getElements("./ViewTable", item);
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node tableItem = list.item(itemIndex);
			// 人事汎用管理表示テーブルキー取得
			String tableKey = TagUtility.getKey(tableItem);
			// 人事汎用管理表示テーブルタイトル取得
			String tableTitle = TagUtility.getString("@title", tableItem);
			if (tableKey.isEmpty()) {
				return null;
			}
			// 人事汎用管理表示テーブルリスト及び人事汎用管理表示テーブルタイトルリストへ追加
			tableKeyList.add(tableKey);
			tableTitleList.add(tableTitle);
			itemIndex++;
		}
		return new ViewProperty(key, tableKeyList.toArray(new String[0]), tableTitleList.toArray(new String[0]));
	}
	
	ViewTableProperty toViewTable(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		// 人事汎用表示テーブル形式取得
		String type = TagUtility.getString("@type", item);
		// 1行に表示する項目数を取得
		int pair = 1;
		try {
			String strPair = TagUtility.getString("@pair", item);
			if (strPair.isEmpty() == false) {
				pair = Integer.parseInt(strPair);
			}
		} catch (NumberFormatException e) {
			return null;
		}
		if (type.isEmpty()) {
			return null;
		}
		// 有効日名称取得
		String dateName = TagUtility.getString("@dateName", item);
		// TableItemの項目リストを取得
		List<TableItemProperty> tableItemList = new ArrayList<TableItemProperty>();
		NodeList list = TagUtility.getElements("./TableItem", item);
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node tableItem = list.item(itemIndex);
			TableItemProperty property = toTableItemProperty(tableItem);
			if (property == null) {
				// エラーメッセージ
				TagUtility.invalidItemMassage(path, item, tableItem.getNodeName(), itemIndex);
			}
			tableItemList.add(property);
			itemIndex++;
		}
		return new ViewTableProperty(key, type, pair, dateName, tableItemList);
	}
	
	TableItemProperty toTableItemProperty(Node item) {
		// 人事汎用表示テーブル項目キーを取得
		String tableKey = TagUtility.getKey(item);
		if (tableKey.isEmpty()) {
			return null;
		}
		// 人事汎用表示テーブル項目設定情報生成
		TableItemProperty tableItem = new TableItemProperty(tableKey);
		
		List<String> itemKeys = new ArrayList<String>();
		List<String> itemNames = new ArrayList<String>();
		List<String> labelKeys = new ArrayList<String>();
		int colspan = 0;
		int rowspan = 0;
		boolean isRequired = false;
		
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		while (index < length) {
			Node child = list.item(index);
			if (TagUtility.isTag(child, "Item")) {
				String itemKey = TagUtility.getKey(child);
				String itemName = TagUtility.getString("@name", child);
				String labelKey = TagUtility.getString("@labelKey", child);
				if (itemKey.isEmpty()) {
					return null;
				}
				itemKeys.add(itemKey);
				itemNames.add(itemName);
				labelKeys.add(labelKey);
			}
			if (TagUtility.isTag(child, "Colspan")) {
				try {
					colspan = Integer.parseInt(TagUtility.trimText(child));
				} catch (NumberFormatException e) {
					return null;
				}
			}
			if (TagUtility.isTag(child, "Rowspan")) {
				try {
					rowspan = Integer.parseInt(TagUtility.trimText(child));
				} catch (NumberFormatException e) {
					return null;
				}
			}
			if (TagUtility.isTag(child, "IsRequired")) {
				isRequired = Boolean.parseBoolean(TagUtility.trimText(child));
			}
			index++;
		}
		tableItem.setItemKeys(itemKeys.toArray(new String[0]));
		tableItem.setItemNames(itemNames.toArray(new String[0]));
		tableItem.setLabelKeys(labelKeys.toArray(new String[0]));
		tableItem.setColspan(colspan);
		tableItem.setRowspan(rowspan);
		tableItem.setRequired(isRequired);
		return tableItem;
	}
	
	
	enum ViewConfig {
		
		VIEW {
			
			@Override
			public String getName() {
				return "View";
			}
		},
		
		TABLE {
			
			@Override
			public String getName() {
				return "ViewTable";
			}
		},
		
		UNKNOWN {
			
			@Override
			public String getName() {
				return "";
			}
		}
		
		;
		
		public abstract String getName();
		
		public static ViewConfig get(String tagName) {
			for (ViewConfig convention : ViewConfig.values()) {
				if (convention.getName().equals(tagName)) {
					return convention;
				}
			}
			return UNKNOWN;
		}
	}
	
}
