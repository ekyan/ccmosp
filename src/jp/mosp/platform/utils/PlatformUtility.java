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
package jp.mosp.platform.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MenuProperty;
import jp.mosp.framework.property.RoleMenuProperty;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.platform.base.PlatformVo;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * プラットフォームにおける有用なメソッドを提供する。<br><br>
 */
public class PlatformUtility {
	
	/**
	 * 文言キー(上三角)。<br>
	 */
	public static final String	NAM_UPPER_TRIANGULAR	= "UpperTriangular";
	
	/**
	 * 文言キー(下三角)。<br>
	 */
	public static final String	NAM_LOWER_TRIANGULAR	= "LowerTriangular";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformUtility() {
		// 処理無し
	}
	
	/**
	 * ソートマークを取得する。<br>
	 * ソートキーが{@link PlatformVo#getComparatorName()}と異なる場合、何も表示しない。
	 * @param sortKey    ソートキー
	 * @param mospParams MosP処理情報
	 * @return ソートマーク
	 */
	public static String getSortMark(String sortKey, MospParams mospParams) {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// ソートキー比較
		if (sortKey.equals(vo.getComparatorName()) == false) {
			return "";
		}
		// 昇順降順フラグ確認
		if (vo.isAscending()) {
			return mospParams.getName(NAM_UPPER_TRIANGULAR);
		}
		return mospParams.getName(NAM_LOWER_TRIANGULAR);
	}
	
	/**
	 * 承認ロールセットを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 承認ロールセット
	 */
	public static Set<String> getApproverRoleSet(MospParams mospParams) {
		// 承認ロールセット準備
		Set<String> approverRoleSet = new HashSet<String>();
		// MosP処理情報からロール情報を取得
		Map<String, RoleProperty> map = mospParams.getProperties().getRoleProperties();
		// ロール情報から承認ロールを抽出
		for (Entry<String, RoleProperty> entry : map.entrySet()) {
			// 承認ロール確認
			if (entry.getValue().isApprover()) {
				// 承認ロールリストに追加
				approverRoleSet.add(entry.getKey());
			}
		}
		return approverRoleSet;
	}
	
	/**
	 * 対象リストから個人IDセットを取得する。<br>
	 * @param list 対象リスト
	 * @return 個人IDセット
	 */
	public static Set<String> getPersonalIdSet(List<? extends PersonalIdDtoInterface> list) {
		// 個人IDセット準備
		Set<String> set = new HashSet<String>();
		// 情報毎に処理
		for (PersonalIdDtoInterface dto : list) {
			set.add(dto.getPersonalId());
		}
		return set;
	}
	
	/**
	 * 対象メニューが有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams  MosP処理情報
	 * @param mainMenuKey メインメニューキー
	 * @param menuKey     メニューキー
	 * @return 確認結果(true：有効、false：無効)
	 */
	public static boolean isTheMenuValid(MospParams mospParams, String mainMenuKey, String menuKey) {
		// メインメニュー取得及び確認
		MainMenuProperty mainMenu = mospParams.getProperties().getMainMenuProperties().get(mainMenuKey);
		if (mainMenu == null) {
			return false;
		}
		// メニュー取得及び確認
		MenuProperty menu = mainMenu.getMenuMap().get(menuKey);
		if (menu == null) {
			return false;
		}
		return menu.isMenuValid();
	}
	
	/**
	 * 対象メニューが利用可能であるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams  MosP処理情報
	 * @param mainMenuKey メインメニューキー
	 * @param menuKey     メニューキー
	 * @return 確認結果(true：利用可能、false：利用不可)
	 */
	public static boolean isTheMenuAvailable(MospParams mospParams, String mainMenuKey, String menuKey) {
		// 対象メニュー有効確認
		if (isTheMenuValid(mospParams, mainMenuKey, menuKey) == false) {
			return false;
		}
		// ユーザロール取得及び確認
		RoleProperty role = mospParams.getUserRole();
		if (role == null) {
			return false;
		}
		// ロールメニュー取得
		RoleMenuProperty roleMenu = role.getRoleMenuMap().get(menuKey);
		return roleMenu != null;
	}
	
	/**
	 * 汎用フラグがOFFであるかを確認する。<br>
	 * @param flag 汎用フラグ
	 * @return 確認結果(true：汎用フラグがOFFである、false：OFFでない)
	 */
	public static boolean isFlagOff(int flag) {
		return flag == MospConst.FLAG_OFF;
	}
	
	/**
	 * 汎用フラグがONであるかを確認する。<br>
	 * @param flag 汎用フラグ
	 * @return 確認結果(true：汎用フラグがONである、false：ONでない)
	 */
	public static boolean isFlagOn(int flag) {
		return flag == MospConst.FLAG_ON;
	}
	
	/**
	 * 検索（前方一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：前方一致、false：前方一致でない)
	 */
	public static boolean isForwardMatch(String condition, String value) {
		return value.startsWith(condition);
	}
	
	/**
	 * 検索（後方一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：前方一致、false：前方一致でない)
	 */
	public static boolean isBackwardMatch(String condition, String value) {
		return value.endsWith(condition);
	}
	
	/**
	 * 検索（部分一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：部分一致、false：部分一致でない)
	 */
	public static boolean isBroadMatch(String condition, String value) {
		return value.indexOf(condition) >= 0;
	}
	
	/**
	 * 検索（完全一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：完全一致、false：完全一致でない)
	 */
	public static boolean isExactMatch(String condition, String value) {
		return value.equals(condition);
	}
	
}
