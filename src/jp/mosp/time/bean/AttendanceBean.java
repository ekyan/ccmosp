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
package jp.mosp.time.bean;

import java.sql.Connection;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.impl.WorkOnHolidayRequestReferenceBean;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 勤怠管理における勤怠データ関連の基本機能を提供する。<br>
 */
public abstract class AttendanceBean extends TimeBean {
	
	/**
	 * 勤怠データ参照クラス。
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 設定適用マスタ参照クラス。
	 */
	protected ApplicationReferenceBeanInterface				applicationReference;
	
	/**
	 * 勤怠設定マスタ参照クラス。
	 */
	protected TimeSettingReferenceBeanInterface				timeSettingReference;
	
	/**
	 * カレンダマスタ参照クラス。
	 */
	protected ScheduleReferenceBeanInterface				scheduleReference;
	
	/**
	 * カレンダ日情報参照クラス。
	 */
	protected ScheduleDateReferenceBeanInterface			scheduleDateReference;
	
	/**
	 * 勤務形態マスタ参照クラス。
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 勤務形態マスタ項目情報参照クラス。
	 */
	protected WorkTypeItemReferenceBeanInterface			workTypeItemReference;
	
	/**
	 * 休日出勤申請参照クラス。
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHoliday;
	
	/**
	 * 振替休日参照クラス。
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface				workflow;
	
	
	/**
	 * コンストラクタ。
	 */
	public AttendanceBean() {
		// 処理無し
	}
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected AttendanceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 参照クラス準備
		attendanceReference = (AttendanceReferenceBeanInterface)createBean(AttendanceReferenceBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		workTypeReference = (WorkTypeReferenceBeanInterface)createBean(WorkTypeReferenceBeanInterface.class);
		workTypeItemReference = (WorkTypeItemReferenceBeanInterface)createBean(WorkTypeItemReferenceBeanInterface.class);
		workOnHoliday = (WorkOnHolidayRequestReferenceBean)createBean(WorkOnHolidayRequestReferenceBean.class);
		substituteReference = (SubstituteReferenceBeanInterface)createBean(SubstituteReferenceBeanInterface.class);
		workflow = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	/**
	 * 休日出勤申請情報から休日出勤時の予定勤務形態を取得する。<br>
	 * @param workOnHolidayRequestDto 休日出勤申請情報
	 * @return 休日出勤時の予定勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkOnHolidayWorkType(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		// 振替申請取得
		int substitute = workOnHolidayRequestDto.getSubstitute();
		// 振替出勤の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替休出日の勤務形態を取得
			return getSubstituteWorkType(workOnHolidayRequestDto);
		}
		// 休日出勤の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休出種別に対応する勤務形態(休出)を取得
			return getWorkOnHolidayWorkType(workOnHolidayRequestDto.getWorkOnHolidayType());
		}
		return "";
	}
	
	/**
	 * 振替休出日の勤務形態を取得する。
	 * @param workOnHolidayRequestDto 休日出勤申請情報
	 * @return 勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getSubstituteWorkType(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		String workTypeCode = "";
		// 振替申請確認
		if (workOnHolidayRequestDto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				&& workOnHolidayRequestDto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& workOnHolidayRequestDto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替申請しない場合
			return workTypeCode;
		}
		// 予定として表示する振替休日情報を取得
		SubstituteDtoInterface substituteDto = getSubstituteForSchedule(workOnHolidayRequestDto);
		// 振替休日情報確認
		if (substituteDto == null) {
			return workTypeCode;
		}
		// 振替日における取得対象個人IDの設定適用情報を取得
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(substituteDto.getPersonalId(),
				substituteDto.getSubstituteDate());
		// 設定適用情報確認
		if (applicationDto == null) {
			return workTypeCode;
		}
		// 振替日におけるカレンダ情報を取得
		ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
				substituteDto.getSubstituteDate());
		// カレンダ情報確認
		if (scheduleDto == null) {
			return workTypeCode;
		}
		// カレンダ日情報取得及び確認
		ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.findForKey(scheduleDto.getScheduleCode(),
				scheduleDto.getActivateDate(), substituteDto.getSubstituteDate());
		if (scheduleDateDto == null) {
			return workTypeCode;
		}
		// カレンダ日情報の勤務形態を取得
		return scheduleDateDto.getWorkTypeCode();
		
	}
	
	/**
	 * 休出種別に対応する勤務形態(休出)を取得する。
	 * @param workOnHolidayType 休出種別
	 * @return 勤務形態(休出)
	 */
	protected String getWorkOnHolidayWorkType(String workOnHolidayType) {
		// 勤務形態準備(所定休日出勤)
		String workTypeCode = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
		// 休出種別確認
		if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 法定休日出勤設定
			workTypeCode = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
		}
		return workTypeCode;
	}
	
	/**
	 * 休日申請情報から、予定として表示する振替休日情報を取得する。
	 * @param workOnHolidayRequestDto 休日申請情報
	 * @return 振替休日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected SubstituteDtoInterface getSubstituteForSchedule(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		// 振替休日情報取得及び確認
		List<SubstituteDtoInterface> substituteList = substituteReference.getSubstituteList(
				workOnHolidayRequestDto.getPersonalId(), workOnHolidayRequestDto.getRequestDate(),
				workOnHolidayRequestDto.getTimesWork());
		if (substituteList.isEmpty()) {
			return null;
		}
		// 午前休確認
		for (SubstituteDtoInterface substituteDto : substituteList) {
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				return substituteDto;
			}
		}
		// 最初の振替休日情報を取得
		return substituteList.get(0);
	}
}
