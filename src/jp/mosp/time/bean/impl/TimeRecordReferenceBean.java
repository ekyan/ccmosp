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

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.dao.settings.TimeRecordDaoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;

/**
 * 打刻データ参照クラス。
 */
public class TimeRecordReferenceBean extends BaseBean implements TimeRecordReferenceBeanInterface {
	
	/**
	 * 打刻データDAOクラス。<br>
	 */
	TimeRecordDaoInterface dao;
	
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 */
	public TimeRecordReferenceBean() {
		super();
	}
	
	/**
	 * {@link BaseBean#BaseBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public TimeRecordReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (TimeRecordDaoInterface)createDao(TimeRecordDaoInterface.class);
	}
	
	@Override
	public TimeRecordDtoInterface findForKey(String personalId, Date workDate, int timesWork, String recordType)
			throws MospException {
		return dao.findForKey(personalId, workDate, timesWork, recordType);
	}
	
	@Override
	public List<TimeRecordDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(personalId, startDate, endDate);
	}
	
}
