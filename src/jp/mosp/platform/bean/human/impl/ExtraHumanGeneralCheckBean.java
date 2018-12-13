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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.human.ExtraHumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;

/**
 * 拡張用人事汎用管理機能チェッククラス。
 */
public class ExtraHumanGeneralCheckBean extends PlatformHumanBean implements ExtraHumanGeneralCheckBeanInterface {
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public ExtraHumanGeneralCheckBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected ExtraHumanGeneralCheckBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元実行
		super.initBean();
	}
	
	@Override
	public void extraValidate(String division, String viewKey) {
	}
	
	@Override
	public void extraValidate(String division, List<? extends PlatformDtoInterface> listPlatformDto) {
	}
	
}
