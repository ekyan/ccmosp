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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.dao.settings.RestDaoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠データ休憩情報参照クラス。
 */
public class RestReferenceBean extends PlatformBean implements RestReferenceBeanInterface {
	
	/**
	 *  勤怠データ休憩情報マスタDAOクラス。<br>
	 */
	RestDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RestReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public RestReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (RestDaoInterface)createDao(RestDaoInterface.class);
	}
	
	@Override
	public RestDtoInterface findForKey(String personalId, Date workDate, int timesWork, int times)
			throws MospException {
		return dao.findForKey(personalId, workDate, timesWork, times);
	}
	
	@Override
	public List<RestDtoInterface> getRestList(String personalId, Date workDate, int works) throws MospException {
		return dao.findForList(personalId, workDate, works);
	}
}
