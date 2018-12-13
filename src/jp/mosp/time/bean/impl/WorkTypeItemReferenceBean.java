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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目参照クラス。
 */
public class WorkTypeItemReferenceBean extends PlatformBean implements WorkTypeItemReferenceBeanInterface {
	
	/**
	 * 勤務形態項目マスタDAO。
	 */
	private WorkTypeItemDaoInterface dao;
	
	
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
	
	@Override
	public int getWorkTime(Date startWorkTime, Date endWorkTime, int restTime) {
		if (startWorkTime == null || endWorkTime == null) {
			return 0;
		}
		
		long startTime = startWorkTime.getTime();
		long endTime = endWorkTime.getTime();
		int difference = (int)(endTime - startTime) / TimeConst.CODE_DEFINITION_MINUTE_MILLI_SEC;
		
		return difference - restTime;
	}
	
	@Override
	public int getRestTime(int rest1, int rest2, int rest3, int rest4) {
		return rest1 + rest2 + rest3 + rest4;
	}
	
	@Override
	public int getDifferenceTime(String startTimeHour, String startTimeMinute, String endTimeHour,
			String endTimeMinute) {
		if (startTimeHour.isEmpty() || startTimeMinute.isEmpty() || endTimeHour.isEmpty() || endTimeMinute.isEmpty()) {
			return 0;
		}
		
		int differencetime = 0;
		try {
			long start = DateUtility.getTime(startTimeHour, startTimeMinute).getTime();
			long end = DateUtility.getTime(endTimeHour, endTimeMinute).getTime();
			differencetime = (int)Math.abs((end - start) / TimeConst.CODE_DEFINITION_MINUTE_MILLI_SEC);
		} catch (MospException e) {
			e.printStackTrace();
		}
		
		return differencetime;
	}
}
