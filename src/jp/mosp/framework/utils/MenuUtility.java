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
package jp.mosp.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.comparator.IndexComparator;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MenuProperty;
import jp.mosp.framework.property.RoleMenuProperty;

/**
 * メニューの操作に有用なメソッドを提供する。<br><br>
 */
public class MenuUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MenuUtility() {
		// 処理無し
	}
	
	/**
	 * メニューキーからメインメニュー設定情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メインメニュー設定情報
	 */
	public static MainMenuProperty getMainMenu(MospParams mospParams, String menuKey) {
		// メインメニュー情報群を取得
		Map<String, MainMenuProperty> mainMenuProperties = mospParams.getProperties().getMainMenuProperties();
		// メインメニュー情報毎に内容を確認
		for (Entry<String, MainMenuProperty> mainMenu : mainMenuProperties.entrySet()) {
			// メインメニュー情報を取得
			MainMenuProperty mainMenuProperty = mainMenu.getValue();
			// メインメニュー内メニュー情報確認
			for (Entry<String, MenuProperty> menu : mainMenuProperty.getMenuMap().entrySet()) {
				// メニューキー比較
				if (menuKey.equals(menu.getKey())) {
					// メインメニュー設定情報を取得
					return mainMenuProperty;
				}
			}
		}
		return null;
	}
	
	/**
	 * メニュー設定情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メニュー設定情報
	 */
	public static MenuProperty getMenuProperty(MospParams mospParams, String menuKey) {
		// メインメニュー情報群を取得
		Map<String, MainMenuProperty> mainMenuProperties = mospParams.getProperties().getMainMenuProperties();
		// メインメニュー情報毎に内容を確認
		for (Entry<String, MainMenuProperty> mainMenu : mainMenuProperties.entrySet()) {
			// メインメニュー情報を取得
			MainMenuProperty mainMenuProperty = mainMenu.getValue();
			// メインメニュー内メニュー情報確認
			for (Entry<String, MenuProperty> menu : mainMenuProperty.getMenuMap().entrySet()) {
				// メニュー情報を取得
				MenuProperty menuProperty = menu.getValue();
				// メニューキー比較
				if (menuKey.equals(menuProperty.getKey())) {
					// メニュー設定情報を取得
					return menuProperty;
				}
			}
		}
		return null;
	}
	
	/**
	 * メニューキーからメインメニューキーを取得する。<br>
	 * メインメニューからパンくずが進行しているのかを判断するのに用いることができる。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メインメニューキー
	 */
	public static String getMainMenuKey(MospParams mospParams, String menuKey) {
		// メインメニュー設定情報を取得
		MainMenuProperty mainMenuProperty = getMainMenu(mospParams, menuKey);
		// メインメニュー設定情報確認
		if (mainMenuProperty == null) {
			return "";
		}
		// メインメニューキーを取得
		return mainMenuProperty.getKey();
	}
	
	/**
	 * メニューが有効であるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return 確認結果(true：有効、false：無効)
	 */
	public static boolean isMenuValid(MospParams mospParams, String menuKey) {
		// メニュー設定情報取得
		MenuProperty menuProperty = getMenuProperty(mospParams, menuKey);
		// メニュー設定情報確認
		if (menuProperty == null) {
			return false;
		}
		// メニュー有効フラグ取得
		return menuProperty.isMenuValid();
	}
	
	/**
	 * ロールメニューとして設定されているメインメニュー設定情報リストを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ロールメニューとして設定されているメインメニュー設定情報リスト
	 */
	public static List<MainMenuProperty> getRoleMainMenuList(MospParams mospParams) {
		// ロールメニューリスト取得
		List<RoleMenuProperty> menuList = getRoleMenuList(mospParams);
		// メインメニューリスト準備
		List<MainMenuProperty> mainMenuList = new ArrayList<MainMenuProperty>();
		// ロールメニューリストからメインメニューリストを作成
		for (RoleMenuProperty roleMenu : menuList) {
			// メニューが無効ならメインメニュー設定不要
			if (isMenuValid(mospParams, roleMenu.getKey()) == false) {
				continue;
			}
			// メニューキーからメインメニューを取得
			MainMenuProperty mainMenu = getMainMenu(mospParams, roleMenu.getKey());
			// 未設定であればメインメニューを追加
			if (mainMenuList.contains(mainMenu) == false) {
				mainMenuList.add(mainMenu);
			}
		}
		return mainMenuList;
	}
	
	/**
	 * ロールメニュー設定リストを取得する。<br>
	 * ロールメニュー設定のインデックスでソートする。<br>
	 * @param mospParams MosP処理情報
	 * @return ロールメニュー設定リスト
	 */
	public static List<RoleMenuProperty> getRoleMenuList(MospParams mospParams) {
		// ロールメニュー取得
		Map<String, RoleMenuProperty> map = mospParams.getUserRole().getRoleMenuMap();
		// ロールメニューをリストで取得
		List<RoleMenuProperty> list = new ArrayList<RoleMenuProperty>(map.values());
		// ロールメニューのインデックスでソート
		Collections.sort(list, new IndexComparator());
		return list;
	}
	
}
