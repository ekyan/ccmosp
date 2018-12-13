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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.comparator.IndexComparator;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;

/**
 * ロールマスタ参照クラス。
 */
public class RoleReferenceBean extends PlatformBean implements RoleReferenceBeanInterface {
	
	/**
	 * コンストラクタ。
	 */
	public RoleReferenceBean() {
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	protected RoleReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() {
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean viewCode) {
		// XMLからロール情報群を取得
		List<RoleProperty> list = new ArrayList<RoleProperty>(mospParams.getProperties().getRoleProperties().values());
		// コード項目リストソート
		Collections.sort(list, new IndexComparator());
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// 配列及びインデックス宣言
		String[][] array = new String[list.size()][2];
		// 配列作成
		for (int i = 0; i < list.size(); i++) {
			RoleProperty roleProperty = list.get(i);
			array[i][0] = roleProperty.getKey();
			if (viewCode) {
				// コード + 名称
				array[i][1] = getCodedName(roleProperty.getKey(), roleProperty.getRoleName(), length);
			} else {
				// 名称
				array[i][1] = roleProperty.getRoleName();
			}
		}
		return array;
	}
	
	@Override
	public String getRoleName(String roleCode, Date targetDate) {
		return getCodeName(roleCode, getSelectArray(targetDate, false));
	}
	
	/**
	 * ロール情報リストにおけるコード最大文字数を取得する。<br>
	 * @param list 対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return ロール情報リストにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<RoleProperty> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (RoleProperty roleProperty : list) {
			if (roleProperty.getKey().length() > length) {
				length = roleProperty.getKey().length();
			}
		}
		return length;
	}
	
}
