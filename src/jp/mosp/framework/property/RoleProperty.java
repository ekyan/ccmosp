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
package jp.mosp.framework.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.IndexedDtoInterface;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;

/**
 * MosPロール設定情報を扱う。
 */
public class RoleProperty implements IndexedDtoInterface, BaseProperty {
	
	/**
	 * ロール追加情報(初期ロール)。
	 */
	protected static final String			ROLE_EXTRA_DEFAULT		= "default";
	
	/**
	 * ロール追加情報(必須ロール)。
	 */
	protected static final String			ROLE_EXTRA_NEEDED		= "needed";
	
	/**
	 * ロール追加情報(特権ロール)。
	 */
	protected static final String			ROLE_EXTRA_SUPER		= "super";
	
	/**
	 * ロール追加情報(承認ロール)。
	 */
	protected static final String			ROLE_EXTRA_APPROVER		= "approver";
	
	/**
	 * ロール追加情報(計算ロール)。
	 */
	protected static final String			ROLE_EXTRA_CALCULATOR	= "calculator";
	
	/**
	 * キー。
	 */
	private String							key;
	
	/**
	 * ロール名称。
	 */
	private String							roleName;
	
	/**
	 * ロール追加情報。
	 */
	private String							roleExtra;
	
	/**
	 * ロールメニュー設定情報群。
	 */
	private Map<String, RoleMenuProperty>	roleMenuMap;
	
	/**
	 * ロール実行可能コマンドリスト。<br>
	 */
	private List<String>					acceptCmdList;
	
	/**
	 * ロール実行不能コマンドリスト。
	 */
	private List<String>					rejectCmdList;
	
	/**
	 * ロール表示順。<br>
	 * アカウントマスタ画面等のプルダウン表示順として用いられる。<br>
	 */
	private int								viewIndex;
	
	
	/**
	 * MosPロール情報を生成する。
	 * @param key      キー
	 */
	public RoleProperty(String key) {
		this.key = key;
		roleName = "";
		roleExtra = "";
		roleMenuMap = new HashMap<String, RoleMenuProperty>();
		acceptCmdList = new ArrayList<String>();
		rejectCmdList = new ArrayList<String>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * ロール名称を取得する。
	 * @return ロール名称
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * ロール名称を設定する。
	 * @param roleName ロール名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/**
	 * ロール追加情報を取得する。
	 * @return ロール追加情報
	 */
	public String getRoleExtra() {
		return roleExtra;
	}
	
	/**
	 * ロール追加情報を設定する。
	 * @param roleExtra ロール追加情報
	 */
	public void setRoleExtra(String roleExtra) {
		this.roleExtra = roleExtra;
	}
	
	/**
	 * ロールメニュー設定情報群を取得する。
	 * @return ロールメニュー設定情報群
	 */
	public Map<String, RoleMenuProperty> getRoleMenuMap() {
		return roleMenuMap;
	}
	
	/**
	 * ロール実行可能コマンドリストを取得する。
	 * @return ロール実行可能コマンドリスト
	 */
	public List<String> getAcceptCmdList() {
		return acceptCmdList;
	}
	
	/**
	 * ロール実行不能コマンドリストを取得する。
	 * @return ロール実行不能コマンドリスト
	 */
	public List<String> getRejectCmdList() {
		return rejectCmdList;
	}
	
	/**
	 * ロール表示順を取得する。
	 * @return ロール表示順
	 */
	@Override
	public int getIndex() {
		return viewIndex;
	}
	
	/**
	 * ロール表示順を設定する。
	 * @param viewIndex ロール表示順
	 */
	public void setViewIndex(int viewIndex) {
		this.viewIndex = viewIndex;
	}
	
	/**
	 * 初期ロール確認を行う。<br>
	 * @return 初期ロール確認結果(true：初期ロール、false：初期ロールでない)
	 */
	public boolean isDefault() {
		// ロール追加情報取得
		for (String extra : getRoleExtraArray()) {
			// 必須ロール設定確認
			if (extra.equals(ROLE_EXTRA_DEFAULT)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 必須ロール確認を行う。<br>
	 * @return 必須ロール確認結果(true：必須ロール、false：必須ロールでない)
	 */
	public boolean isNeeded() {
		// ロール追加情報取得
		for (String extra : getRoleExtraArray()) {
			// 必須ロール設定確認
			if (extra.equals(ROLE_EXTRA_NEEDED)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 特権ロール確認を行う。<br>
	 * @return 特権ロール確認結果(true：特権ロール、false：特権ロールでない)
	 */
	public boolean isSuper() {
		// ロール追加情報取得
		for (String extra : getRoleExtraArray()) {
			// 特権ロール設定確認
			if (extra.equals(ROLE_EXTRA_SUPER)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 承認ロール確認を行う。<br>
	 * @return 承認ロール確認結果(true：承認ロール、false：承認ロールでない)
	 */
	public boolean isApprover() {
		// ロール追加情報取得
		for (String extra : getRoleExtraArray()) {
			// 承認ロール設定確認
			if (extra.equals(ROLE_EXTRA_APPROVER)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 計算ロール確認を行う。<br>
	 * @return 計算ロール確認結果(true：計算ロール、false：計算ロールでない)
	 */
	public boolean isCalculator() {
		// ロール追加情報取得
		for (String extra : getRoleExtraArray()) {
			// 計算ロール設定確認
			if (extra.equals(ROLE_EXTRA_CALCULATOR)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ロール追加情報配列を取得する。<br>
	 * @return ロール追加情報配列
	 */
	protected String[] getRoleExtraArray() {
		if (roleExtra == null) {
			roleExtra = "";
		}
		return MospUtility.split(roleExtra, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * ロール実行可能コマンドの確認を行う。<br>
	 * 実行不可コマンドは、コマンドでのみ、実行不可コマンドリストから検索する。<br>
	 * 実行不可コマンドリスト内にコマンドが含まれている場合、falseを返す。<br>
	 * 実行可能コマンドは、見つかるまでワイルドカードで置換して、
	 * 実行可能コマンドリストから検索する。<br>
	 * 実行可能コマンドリストに含まれていない場合、falseを返す。<br>
	 * @param command コマンド
	 * @return ロール実行可能コマンド確認結果(true：実行可能、false：実行不能)
	 */
	public boolean hasAuthority(String command) {
		// コマンド確認
		if (command == null || command.isEmpty()) {
			return false;
		}
		// 実行不可コマンドリスト確認
		if (rejectCmdList.contains(command)) {
			return false;
		}
		// 実行可能コマンドリスト確認
		if (acceptCmdList.contains(command)) {
			return true;
		}
		// コマンド末尾をワイルドカード化
		String wildCardCommand = MospUtility.getWildCardCommand(command);
		// 実行可能コマンドリスト確認
		while (wildCardCommand.isEmpty() == false) {
			// 実行可能コマンドリスト確認
			if (acceptCmdList.contains(wildCardCommand)) {
				return true;
			}
			// コマンド末尾をワイルドカード化
			wildCardCommand = MospUtility.getWildCardCommand(wildCardCommand);
		}
		return false;
	}
	
}
