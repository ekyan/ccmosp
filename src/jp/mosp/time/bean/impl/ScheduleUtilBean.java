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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダユーティリティクラス。<br>
 */
public class ScheduleUtilBean extends TimeApplicationBean implements ScheduleUtilBeanInterface {
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	private ScheduleDateReferenceBean	scheduleDateReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	private ScheduleReferenceBean		scheduleReference;
	
	
	/**
	 * {@link ScheduleUtilBean}を生成する。<br>
	 */
	public ScheduleUtilBean() {
		// 処理無し
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected ScheduleUtilBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のメソッドを実施
		super.initBean();
		// 各種クラスの取得
		scheduleDateReference = (ScheduleDateReferenceBean)createBean(ScheduleDateReferenceBean.class);
		scheduleReference = (ScheduleReferenceBean)createBean(ScheduleReferenceBean.class);
		
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		// 設定適用
		setApplicationSettings(personalId, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// 勤務形態コードを取得
		return getScheduledWorkTypeCode(targetDate);
	}
	
	@Override
	public String getScheduledWorkTypeCode(ApplicationDtoInterface dto, Date targetDate) throws MospException {
		// 設定適用情報を設定
		applicationDto = dto;
		// 設定適用情報確認
		applicationRefer.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// 勤務形態コードを取得
		return getScheduledWorkTypeCode(targetDate);
	}
	
	/**
	 * 対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードの取得に失敗した場合、エラーメッセージを設定し、空文字を返す。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(Date targetDate) throws MospException {
		// 検索用年月日の作成
		int targetYear = MonthUtility.getFiscalYear(targetDate, mospParams);
		// 年を表す日付(年度の初日)を取得
		Date targetYearDate = MonthUtility.getYearDate(targetYear, mospParams);
		// カレンダマスタの取得
		ScheduleDtoInterface scheduleDto = scheduleReference.findForKey(applicationDto.getScheduleCode(),
				targetYearDate);
		scheduleReference.chkExistSchedule(scheduleDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		// カレンダ日付の取得
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.findForKey(scheduleDto.getScheduleCode(),
				targetYearDate, targetDate);
		scheduleDateReference.chkExistScheduleDate(scheduleDateDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return "";
		}
		if (scheduleDateDto.getWorkTypeCode() == null || scheduleDateDto.getWorkTypeCode().isEmpty()) {
			// 勤務形態がない場合
			addWorkTypeNotExistErrorMessage(targetDate);
			return "";
		}
		return scheduleDateDto.getWorkTypeCode();
	}
	
}
