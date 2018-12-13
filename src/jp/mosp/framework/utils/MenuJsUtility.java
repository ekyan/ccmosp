/**
 * 
 */
package jp.mosp.framework.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MenuProperty;
import jp.mosp.framework.property.RoleMenuProperty;
import net.arnx.jsonic.JSON;

/**
 * メニュー用JS文字列を取得するユーティリティクラス。
 */
public class MenuJsUtility {
	
	/**
	 * メニュー用JS文字列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return メニュー用JS文字列
	 */
	public static String getMenuJs(MospParams mospParams) {
		// ロールメニューとして設定されているメインメニュー設定情報リストを取得
		List<MainMenuProperty> mainMenuList = MenuUtility.getRoleMainMenuList(mospParams);
		
		Map<String, MenuItem> map = new LinkedHashMap<String, MenuItem>();
		
		// メインメニュー毎に処理
		for (MainMenuProperty mainMenu : mainMenuList) {
			String key = mainMenu.getKey();
			map.put(key, new MenuItem(key, mospParams.getName(mainMenu.getKey())));
		}
		
		// ロールメニュー設定情報群取得
		List<RoleMenuProperty> menuList = MenuUtility.getRoleMenuList(mospParams);
		// ロールメニュー毎に配列に追加
		for (RoleMenuProperty roleMenu : menuList) {
			// メニュー設定情報取得
			MenuProperty menu = MenuUtility.getMenuProperty(mospParams, roleMenu.getKey());
			// メニュー有効フラグ確認
			if (menu.isMenuValid() == false) {
				continue;
			}
			// メニューからメインメニューを取得
			map.get(MenuUtility.getMainMenuKey(mospParams, roleMenu.getKey())).add(menu.getKey(), menu.getCommand(),
					mospParams.getName(menu.getVoClass()));
		}
		
		List<List<Object>> list = new ArrayList<List<Object>>();
		for (MenuItem item : map.values()) {
			List<String[]> value = item.getValue();
			List<Object> element = new ArrayList<Object>();
			element.add(item.getKey());
			element.add(item.getName());
			element.add(value.toArray(new String[value.size()][3]));
			list.add(element);
		}
		return "ARY_MENU = " + JSON.escapeScript(list) + ";";
	}
	
	/**
	 * 選択メニュー(大項目)を取得する。<br>
	 * 画面遷移時に選択しているメニュー(大項目)の配列名を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 選択メニュー
	 */
	public static String getSelectMenu(MospParams mospParams) {
		// メインメニューキー取得
		String mainMenuKey = TopicPathUtility.getMainMenuKey(mospParams);
		// メインメニューキー確認
		if (mainMenuKey != null && mainMenuKey.isEmpty() == false) {
			// 選択メニュー(大項目)取得
			return mainMenuKey;
		}
		// メインメニュー設定情報リストの先頭を取得
		List<MainMenuProperty> list = MenuUtility.getRoleMainMenuList(mospParams);
		return list.get(0).getKey();
	}
	
	
	/**
	 * メニュー要素
	 */
	static class MenuItem {
		
		private String			key;
		
		private String			name;
		
		private List<String[]>	value;
		
		
		public MenuItem(String key, String name) {
			this.key = key;
			this.name = name;
			value = new ArrayList<String[]>();
		}
		
		/**
		 * @return key
		 */
		public String getKey() {
			return key;
		}
		
		/**
		 * @return name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * @return value
		 */
		public List<String[]> getValue() {
			return value;
		}
		
		/**
		 * @param key セットする key
		 */
		public void setKey(String key) {
			this.key = key;
		}
		
		/**
		 * @param name セットする name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @param value セットする value
		 */
		public void setValue(List<String[]> value) {
			this.value = value;
		}
		
		/**
		 * @param key キー
		 * @param command コマンド
		 * @param name 名称
		 */
		public void add(String key, String command, String name) {
			value.add(new String[]{ key, command, name });
		}
		
	}
}
