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
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * カレンダユーティリティクラス。<br>
 */
public class ScheduleUtilBean extends TimeApplicationBean implements ScheduleUtilBeanInterface {
	
	/**
	 * カレンダ日参照クラス。<br>
	 */
	protected ScheduleDateReferenceBean			scheduleDateReference;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBean				scheduleReference;
	
	/**
	 * 振替休日データ参照クラス。<br>
	 */
	protected SubstituteReferenceBeanInterface	substituteReference;
	
	
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
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
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
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate, boolean useRequest)
			throws MospException {
		if (!useRequest) {
			return getScheduledWorkTypeCode(personalId, targetDate);
		}
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, targetDate);
		return getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認済である場合
			return differenceRequestDto.getDifferenceType();
		}
		
		// 時差出勤申請が承認済でない場合
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = requestUtil.getWorkTypeChangeDto(true);
		if (workTypeChangeRequestDto != null) {
			// 勤務形態変更申請が承認済である場合
			return workTypeChangeRequestDto.getWorkTypeCode();
		}
		
		// 勤務形態変更申請が承認済でない場合
		Date date = targetDate;
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			// 振出・休出申請が承認済でない場合
			List<SubstituteDtoInterface> list = requestUtil.getSubstituteList(true);
			for (SubstituteDtoInterface dto : list) {
				if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 振替休日が全休の場合
					return dto.getSubstituteType();
				}
			}
		} else {
			// 振出・休出申請が承認済である場合
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 振替出勤(勤務形態変更なし)・
				// 振替出勤(午前)・
				// 振替出勤(午後)の場合
				List<SubstituteDtoInterface> list = substituteReference
					.getSubstituteList(workOnHolidayRequestDto.getWorkflow());
				if (list.isEmpty()) {
					return "";
				}
				date = list.get(0).getSubstituteDate();
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 振替出勤(勤務形態変更あり)の場合
				return workOnHolidayRequestDto.getWorkTypeCode();
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 法定休日出勤の場合
					return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
				} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY
					.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 所定休日出勤の場合
					return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
				}
				return "";
			} else {
				return "";
			}
		}
		if (!hasApplicationSettings(personalId, date)) {
			return "";
		}
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(), date);
		if (scheduleDto == null) {
			return "";
		}
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference
			.getScheduleDateInfo(scheduleDto.getScheduleCode(), date);
		if (scheduleDateDto == null || scheduleDateDto.getWorkTypeCode() == null) {
			return "";
		}
		return scheduleDateDto.getWorkTypeCode();
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
				targetDate);
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
