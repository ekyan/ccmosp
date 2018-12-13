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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalAbsenceReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalAbsenceDaoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;

/**
 * 勤怠集計欠勤データ参照クラス。
 */
public class TotalAbsenceReferenceBean extends PlatformBean implements TotalAbsenceReferenceBeanInterface {
	
	/**
	 * 勤怠集計欠勤データDAOクラス。<br>
	 */
	TotalAbsenceDaoInterface totalAbsenceDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalAbsenceReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TotalAbsenceReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		totalAbsenceDao = (TotalAbsenceDaoInterface)createDao(TotalAbsenceDaoInterface.class);
	}
	
	@Override
	public TotalAbsenceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String absenceCode) throws MospException {
		return totalAbsenceDao.findForKey(personalId, calculationYear, calculationMonth, absenceCode);
	}
	
	@Override
	public List<TotalAbsenceDtoInterface> getTotalAbsenceList(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
		return totalAbsenceDao.findForList(personalId, calculationYear, calculationMonth);
	}
	
}
