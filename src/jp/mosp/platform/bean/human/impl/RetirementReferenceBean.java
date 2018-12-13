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
import java.util.Date;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;

/**
 * 人事退職情報参照クラス。<br>
 */
public class RetirementReferenceBean extends BaseBean implements RetirementReferenceBeanInterface {
	
	/**
	 * 人事退職情報DAO
	 */
	private RetirementDaoInterface retirementDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public RetirementReferenceBean() {
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	protected RetirementReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		retirementDao = (RetirementDaoInterface)createDao(RetirementDaoInterface.class);
	}
	
	@Override
	public RetirementDtoInterface getRetireInfo(String personalId, Date targetDate) throws MospException {
		return retirementDao.findForInfo(personalId, targetDate);
	}
	
	@Override
	public RetirementDtoInterface getRetireInfo(String personalId) throws MospException {
		return retirementDao.findForInfo(personalId);
	}
	
	@Override
	public boolean isRetired(String personalId, Date targetDate) throws MospException {
		RetirementDtoInterface dto = getRetireInfo(personalId, targetDate);
		if (dto != null) {
			return targetDate.compareTo(dto.getRetirementDate()) > 0;
		}
		return false;
	}
	
	@Override
	public RetirementDtoInterface findForKey(long id) throws MospException {
		BaseDtoInterface dto = findForKey(retirementDao, id, false);
		if (dto != null) {
			return (RetirementDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public Date getRetireDate(String personalId) throws MospException {
		RetirementDtoInterface dto = getRetireInfo(personalId);
		if (dto != null) {
			return dto.getRetirementDate();
		}
		return null;
	}
	
}
