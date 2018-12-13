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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目参照クラス。
 */
public class WorkTypeItemReferenceBean extends PlatformBean implements WorkTypeItemReferenceBeanInterface {
	
	/**
	 * 勤務形態項目マスタDAO。
	 */
	private WorkTypeItemDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeItemReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkTypeItemReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
	}
	
	@Override
	public WorkTypeItemDtoInterface getWorkTypeItemInfo(String workTypeCode, Date targetDate, String workTypeItemCode)
			throws MospException {
		return dao.findForInfo(workTypeCode, targetDate, workTypeItemCode);
	}
	
	@Override
	public WorkTypeItemDtoInterface findForKey(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException {
		return dao.findForKey(workTypeCode, activateDate, workTypeItemCode);
	}
	
}
