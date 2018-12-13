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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計データ参照クラス。
 */
public class TotalTimeReferenceBean extends PlatformBean implements TotalTimeReferenceBeanInterface {
	
	/**
	 * 勤怠集計データDAO。
	 */
	protected TotalTimeDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TotalTimeReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
	}
	
	@Override
	public TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		return dao.findForKey(personalId, calculationYear, calculationMonth);
	}
	
	@Override
	public Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 値取得
		int startYear = DateUtility.getYear(firstDate);
		int startMonth = DateUtility.getMonth(firstDate);
		int endYear = DateUtility.getYear(lastDate);
		int endMonth = DateUtility.getMonth(lastDate);
		return findFiscalMap(personalId, startYear, startMonth, endYear, endMonth);
	}
	
	@Override
	public Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, int startYear, int startMonth,
			int endYear, int endMonth) throws MospException {
		// 値取得
		return dao.findFiscalMap(personalId, startYear, startMonth, endYear, endMonth);
	}
	
	@Override
	public int getMinYear() throws MospException {
		// 勤怠集計情報が存在する最小の年を取得
		return dao.getMinYear();
	}
	
}
