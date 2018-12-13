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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ScheduleTotalReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * 予定合計参照クラス。
 */
public class ScheduleTotalReferenceBean extends PlatformBean implements ScheduleTotalReferenceBeanInterface {
	
	/**
	 * 勤務形態項目参照。
	 */
	WorkTypeItemReferenceBeanInterface	workTypeItemReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleTotalReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public ScheduleTotalReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		workTypeItemReference = (WorkTypeItemReferenceBeanInterface)createBean(WorkTypeItemReferenceBeanInterface.class);
	}
	
	@Override
	public Map<String, Integer> getScheduleTotalInfo(List<ScheduleDateDtoInterface> list) throws MospException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		int totalWork = 0;
		int totalRest = 0;
		int timesWork = 0;
		int timesSpecialLeave = 0;
		int timesLegalHoliday = 0;
		Date workTimeDate;
		Date restTimeDate;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getWorkTypeCode().equals("prescribed_holiday")) {
				// 勤務形態が所定の場合カウントを増やす
				timesSpecialLeave++;
			} else if (list.get(i).getWorkTypeCode().equals("legal_holiday")) {
				// 勤務形態が法定の場合カウントを増やす
				timesLegalHoliday++;
			} else if (list.get(i).getWorkTypeCode().equals("")) {
				// 勤務形態が登録されていない場合、何もしない
				continue;
			} else {
				// 勤務時間の合計
				workTimeDate = workTypeItemReference.getWorkTypeItemInfo(list.get(i).getWorkTypeCode(),
						list.get(i).getScheduleDate(), TimeConst.CODE_WORKTIME).getWorkTypeItemValue();
				totalWork = totalWork + (DateUtility.getHour(workTimeDate) * 60) + DateUtility.getMinute(workTimeDate);
				// 休憩時間の合計
				restTimeDate = workTypeItemReference.getWorkTypeItemInfo(list.get(i).getWorkTypeCode(),
						list.get(i).getScheduleDate(), TimeConst.CODE_RESTTIME).getWorkTypeItemValue();
				totalRest = totalRest + (DateUtility.getHour(restTimeDate) * 60) + DateUtility.getMinute(restTimeDate);
				// 出勤回数のカウント
				timesWork++;
			}
		}
		map.put(TimeConst.CODE_TOTAL_WORK_TIME, totalWork);
		map.put(TimeConst.CODE_TOTAL_REST_TIME, totalRest);
		map.put(TimeConst.CODE_TOTAL_TIMES_WORK, timesWork);
		map.put(TimeConst.CODE_TOTAL_TIMES_SPECIFICHOLIDAY, timesSpecialLeave);
		map.put(TimeConst.CODE_TOTAL_TIMES_LEGALHOLIDAY, timesLegalHoliday);
		
		return map;
	}
}
