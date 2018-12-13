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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalTimeEmployeeDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;

/**
 * 社員勤怠集計管理参照クラス。
 */
public class TotalTimeEmployeeTransactionReferenceBean extends PlatformBean implements
		TotalTimeEmployeeTransactionReferenceBeanInterface {
	
	/**
	 * 社員勤怠集計管理DAO。
	 */
	protected TotalTimeEmployeeDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeEmployeeTransactionReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TotalTimeEmployeeTransactionReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (TotalTimeEmployeeDaoInterface)createDao(TotalTimeEmployeeDaoInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		return dao.findForKey(personalId, calculationYear, calculationMonth);
	}
	
	@Override
	public Integer getCutoffState(String personalId, int calculationYear, int calculationMonth) throws MospException {
		TotalTimeEmployeeDtoInterface dto = dao.findForKey(personalId, calculationYear, calculationMonth);
		if (dto != null) {
			return dto.getCutoffState();
		}
		return null;
	}
	
	@Override
	public boolean getCutoffTermState(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<TotalTimeEmployeeDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
	@Override
	public List<TotalTimeEmployeeDtoInterface> isCutoffTermState(String cutoffCode, Date startDate, Date endDate)
			throws MospException {
		// 対象締日コードで対象期間内の情報を取得
		return dao.findCalcDataTerm(cutoffCode, startDate, endDate);
	}
	
	@Override
	public TotalTimeEmployeeDtoInterface findForPersonalList(String personalId, int cutoffState) throws MospException {
		// 社員勤怠集計管理データリスト取得
		List<TotalTimeEmployeeDtoInterface> personalList = dao.findForPersonalList(personalId, cutoffState);
		// 社員勤怠集計管理データリストがない場合
		if (personalList.isEmpty()) {
			return null;
		}
		// 最新社員勤怠集計管理データ取得
		return personalList.get(personalList.size() - 1);
	}
}
