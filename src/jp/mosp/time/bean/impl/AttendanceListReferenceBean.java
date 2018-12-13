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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.portal.HolidayBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceBean;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧参照クラス。<br>
 * <br>
 * 締期間における勤怠一覧情報を作成する。<br>
 * 勤怠一覧画面、予定確認画面、出勤簿、予定簿等で用いられる。<br>
 * <br>
 * 勤怠一覧情報作成後に作成情報を取得するためのアクセサメソッドを備える。
 * <br>
 * <br>
 * 勤怠一覧情報を対象日指定、或いは年月指定で取得することができる。<br>
 * 勤怠一覧情報を取得する手順は、以下の通り。<br>
 * <br>
 * 個人ID及び対象日(年月指定の場合は年月の最終日)で、設定適用情報及び締日を取得する。<br>
 * 対象日及び締日から、対象年月を算出する(対象日指定の場合のみ)。<br>
 * 対象年月及び締日から、締期間を算出する。<br>
 * 個人ID及び締期間で、勤怠一覧情報を取得する。<br>
 * <br>
 * 週単位で勤怠一覧情報を取得する場合は、締期間の算出方法が変わる。<br>
 */
public class AttendanceListReferenceBean extends AttendanceBean implements AttendanceListReferenceBeanInterface {
	
	/**
	 * 時間表示時の小数点以下の桁数。<br>
	 */
	public static final int											HOURS_DIGITS				= 2;
	
	/**
	 * 時間表示時の区切文字。<br>
	 */
	public static final String										SEPARATOR_HOURS				= ".";
	
	/**
	 * スタイル文字列(赤)。
	 */
	public static final String										STYLE_RED					= "style=\"color: red\"";
	
	/**
	 * スタイル文字列(青)。
	 */
	public static final String										STYLE_BLUE					= "style=\"color: blue\"";
	
	/**
	 * スタイル文字列(黄)。
	 */
	public static final String										STYLE_YELLOW				= "style=\"color: darkorange\"";
	
	/**
	 * 区切文字(勤怠備考)。
	 */
	public static final String										SEPARATOR_ATTENDANCE_REMARK	= " ";
	
	/**
	 * 限度基準期間(1ヶ月)。
	 */
	public static final String										LIMIT_STANDARD_TERM_MONTH1	= "month1";
	
	/**
	 * 有給休暇設定マスタ参照クラス。
	 */
	protected PaidHolidayReferenceBean								paidHoliday;
	
	/**
	 * 勤怠設定限度基準情報参照クラス。
	 */
	protected LimitStandardReferenceBeanInterface					limitStandard;
	
	/**
	 * 締日ユーティリティクラス。
	 */
	protected CutoffUtilBeanInterface								cutoffUtil;
	
	/**
	 * 勤怠修正情報参照クラス。
	 */
	protected AttendanceCorrectionReferenceBeanInterface			correction;
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface					holidayRequest;
	
	/**
	 * 残業申請参照クラス。
	 */
	protected OvertimeRequestReferenceBeanInterface					overtime;
	
	/**
	 * 代休情報参照クラス。
	 */
	protected SubHolidayReferenceBeanInterface						subHolidayRefer;
	
	/**
	 * 代休申請参照クラス。
	 */
	protected SubHolidayRequestReferenceBeanInterface				subHoliday;
	
	/**
	 * 勤務形態変更申請参照クラス。
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface			workTypeChange;
	
	/**
	 * 時差出勤申請参照クラス。
	 */
	protected DifferenceRequestReferenceBeanInterface				difference;
	
	/**
	 * 所属マスタ参照クラス。
	 */
	protected SectionReferenceBeanInterface							section;
	
	/**
	 * 休暇マスタ参照クラス。
	 */
	protected HolidayReferenceBeanInterface							holiday;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface						retirement;
	
	/**
	 * 人事休職情報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface						suspension;
	
	/**
	 * 祝日クラス。
	 */
	protected HolidayBeanInterface									holidayBean;
	
	/**
	 * 設定適用情報。
	 */
	protected ApplicationDtoInterface								applicationDto;
	
	/**
	 * 勤怠設定情報。
	 */
	protected TimeSettingDtoInterface								timeSettingDto;
	
	/**
	 * 締日情報。
	 */
	protected CutoffDtoInterface									cutoffDto;
	
	/**
	 * カレンダ情報。
	 */
	protected ScheduleDtoInterface									scheduleDto;
	
	/**
	 * カレンダ日情報リスト。
	 */
	protected List<ScheduleDateDtoInterface>						scheduleDateList;
	
	/**
	 * 社員勤怠集計管理参照クラス。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployee;
	
	/**
	 * 取得対象個人ID。<br>
	 * インターフェースに定義されたメソッドの最初で設定される。<br>
	 */
	protected String												personalId;
	
	/**
	 * 対象年。<br>
	 */
	protected int													targetYear;
	
	/**
	 * 対象月。<br>
	 */
	protected int													targetMonth;
	
	/**
	 * 締期間初日。<br>
	 * {@link #setCutoffTerm()}により設定される。<br>
	 */
	protected Date													firstDate;
	
	/**
	 * 締期間最終日。<br>
	 * {@link #setCutoffTerm()}により設定される。<br>
	 */
	protected Date													lastDate;
	
	/**
	 * 対象年月における対象個人IDの締状態。<br>
	 * true：仮締又は確定、false：未締。<br>
	 */
	protected boolean												isTightened;
	
	/**
	 * 勤怠情報リスト。<br>
	 */
	protected List<AttendanceDtoInterface>							attendanceDtoList;
	
	/**
	 * 休暇申請情報リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>						holidayRequestList;
	
	/**
	 * 残業申請情報リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>						overtimeRequestList;
	
	/**
	 * 振替休日情報リスト。<br>
	 */
	protected List<SubstituteDtoInterface>							substituteList;
	
	/**
	 * 代休申請情報リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>					subHolidayRequestList;
	
	/**
	 * 休出申請情報リスト
	 */
	protected List<WorkOnHolidayRequestDtoInterface>				workOnHolidayRequestList;
	
	/**
	 * 勤務形態変更申請情報リスト
	 */
	protected List<WorkTypeChangeRequestDtoInterface>				workTypeChangeRequestList;
	
	/**
	 * 時差出勤申請情報リスト
	 */
	protected List<DifferenceRequestDtoInterface>					differenceRequestList;
	
	/**
	 * 勤怠一覧情報リスト。<br>
	 * 各メソッドは、ここに勤怠一覧情報を追加していく。<br>
	 */
	protected List<AttendanceListDto>								attendanceList;
	
	/**
	 * 申請ユーティリティ。
	 */
	private RequestUtilBeanInterface								requestUtil;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	private Map<Long, WorkflowDtoInterface>							workflowMap;
	
	/**
	 * 勤務形態エンティティ群。<br>
	 */
	private Map<String, WorkTypeEntity>								workTypeEntityMap;
	
	
	@Override
	public void initBean() throws MospException {
		// 継承元クラスのメソッドを実行
		super.initBean();
		// 参照クラス準備
		paidHoliday = (PaidHolidayReferenceBean)createBean(PaidHolidayReferenceBean.class);
		limitStandard = (LimitStandardReferenceBeanInterface)createBean(LimitStandardReferenceBeanInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		correction = (AttendanceCorrectionReferenceBeanInterface)createBean(AttendanceCorrectionReferenceBeanInterface.class);
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		overtime = (OvertimeRequestReferenceBean)createBean(OvertimeRequestReferenceBean.class);
		workOnHoliday = (WorkOnHolidayRequestReferenceBeanInterface)createBean(WorkOnHolidayRequestReferenceBeanInterface.class);
		subHolidayRefer = (SubHolidayReferenceBeanInterface)createBean(SubHolidayReferenceBeanInterface.class);
		subHoliday = (SubHolidayRequestReferenceBeanInterface)createBean(SubHolidayRequestReferenceBeanInterface.class);
		workTypeChange = (WorkTypeChangeRequestReferenceBeanInterface)createBean(WorkTypeChangeRequestReferenceBeanInterface.class);
		difference = (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class);
		section = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
		holiday = (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
		retirement = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		suspension = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		totalTimeEmployee = (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		// 祝日クラス準備
		holidayBean = (HolidayBeanInterface)createBean(HolidayBeanInterface.class);
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日及び締日から対象年月を算出し設定
		setTargetYearMonth(targetDate);
		// 締期間設定
		setCutoffTerm();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間含む)
		addScheduleList(true, true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addApplicationList(false, false, true);
		// 申請(承認済)によって予定勤務時間表示無し
		setApprovalTime();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 予定一覧追加処理
		setScheduleExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間含む)
		addScheduleList(true, true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addApplicationList(false, false, true);
		// 申請(承認済)によって予定勤務時間表示無し
		setApprovalTime();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 予定一覧追加処理
		setScheduleExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getActualList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日及び締日から対象年月を算出し設定
		setTargetYearMonth(targetDate);
		// 締期間設定
		setCutoffTerm();
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addActualList(false);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addApplicationList(false, true, false);
		// カレンダ情報を取得し勤怠一覧情報リストに設定(休日のみ)
		addScheduleList(false, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 実績一覧追加処理
		setActualExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getActualList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addActualList(false);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addApplicationList(false, true, false);
		// カレンダ情報を取得し勤怠一覧情報リストに設定(休日のみ)
		addScheduleList(false, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 実績一覧追加処理
		setActualExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日及び締日から対象年月を算出し設定
		setTargetYearMonth(targetDate);
		// 締期間設定
		setCutoffTerm();
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(useScheduledTime());
		// チェックボックス設定
		setNeedCheckbox();
		// 勤怠一覧追加処理
		setAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(useScheduledTime());
		// チェックボックス設定
		setNeedCheckbox();
		// 勤怠一覧追加処理
		setAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getWeeklyAttendanceList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日から期間(週)を設定
		setWeekTerm(targetDate);
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧(週)追加処理
		setWeeklyAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getApprovalAttendanceList(String personalId, int year, int month)
			throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, true, false);
		// カレンダ情報を取得し勤怠一覧情報リストに設定(休日のみ)
		addScheduleList(false, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(false);
		// チェックボックス設定
		setNeedApprovalCheckbox();
		// 勤怠承認一覧追加処理
		setApprovalAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public AttendanceListDto getAttendanceListDto(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象日を設定
		firstDate = targetDate;
		lastDate = targetDate;
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧データ追加処理
		setAttendanceListDtoExtraInfo();
		if (attendanceList.size() == 1) {
			return attendanceList.get(0);
		}
		return null;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getLastDate() {
		return getDateClone(lastDate);
	}
	
	@Override
	public int getTargetYear() {
		return targetYear;
	}
	
	@Override
	public int getTargetMonth() {
		return targetMonth;
	}
	
	@Override
	public String getScheduleName() {
		return scheduleDto == null ? "" : scheduleDto.getScheduleName();
	}
	
	@Override
	public List<Date> getNotAttendanceAppliList(String personalId, Date startDate, Date endDate) throws MospException {
		// 勤怠未入力確認用に勤怠一覧情報リストを取得
		List<AttendanceListDto> attendanceList = getListForAttendanceCheck(personalId, startDate, endDate);
		// 日付リスト準備
		List<Date> errorList = new ArrayList<Date>();
		// 勤怠毎に処理
		for (AttendanceListDto dto : attendanceList) {
			// 勤怠入力用チェックボックス無しの場合
			if (dto.isNeedCheckbox() == false) {
				continue;
			}
			// 日付リスト取得
			errorList.add(dto.getWorkDate());
		}
		return errorList;
	}
	
	/**
	 * 勤怠未入力確認用に勤怠一覧情報リストを取得する。
	 * @param personalId 個人ID
	 * @param firstDate 期間開始日
	 * @param lastDate 期間終了日
	 * @return 勤怠未入力確認用勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getListForAttendanceCheck(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId);
		// 期間を設定
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		return attendanceList;
	}
	
	/**
	 * フィールド初期化及び対象個人ID設定を行う。<br>
	 * @param personalId 対象個人ID
	 */
	protected void initFields(String personalId) {
		// 設定適用情報初期化
		applicationDto = null;
		// 勤怠設定情報初期化
		timeSettingDto = null;
		// 締日情報初期化
		cutoffDto = null;
		// カレンダ情報初期化
		scheduleDto = null;
		// カレンダ日情報リスト初期化
		scheduleDateList = null;
		// 締期間初日初期化
		firstDate = null;
		// 締期間最終日初期化
		lastDate = null;
		// 勤怠一覧情報リスト初期化
		attendanceList = new ArrayList<AttendanceListDto>();
		// 個人ID設定
		this.personalId = personalId;
	}
	
	/**
	 * 対象日で対象年月及び締期間最終日を仮設定する。<br>
	 * @param targetDate 対象日
	 */
	protected void initDateFields(Date targetDate) {
		// 対象年月設定(対象日の年月を仮設定)
		// TODO 変更予定
		targetYear = DateUtility.getYear(targetDate);
		targetMonth = DateUtility.getMonth(targetDate);
		// 初期表示の場合システム日付設定(対象日を仮設定)
		lastDate = targetDate;
	}
	
	/**
	 * 対象年月で対象年月を設定、締期間最終日を仮設定する。<br>
	 * @param year  対象年
	 * @param month 対象月
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void initDateFields(int year, int month) throws MospException {
		// 対象年月設定
		targetYear = year;
		targetMonth = month;
		// 締期間最終日設定(年月の最終日を仮設定)
		lastDate = MonthUtility.getYearMonthTargetDate(year, month, mospParams);
	}
	
	/**
	 * 帳票用ヘッダー項目を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHeaderFields() throws MospException {
		// 最初の勤怠一覧レコードを取得
		AttendanceListDto dto = attendanceList.get(0);
		// 人事情報取得
		HumanDtoInterface humanDto = getHumanInfo(personalId, lastDate);
		// 年月設定
		dto.setTargetDate(MonthUtility.getYearMonthDate(targetYear, targetMonth));
		// 社員コード設定
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		// 社員氏名設定
		dto.setEmployeeName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		// 所属名設定
		dto.setSectionName(section.getSectionName(humanDto.getSectionCode(), lastDate));
		if (section.useDisplayName()) {
			// 所属表示名称を設定
			dto.setSectionName(section.getSectionDisplay(humanDto.getSectionCode(), lastDate));
		}
		// 帳票承認印欄表示設定
		dto.setSealBoxAvailable(true);
		// 最後の勤怠一覧レコードを取得
		AttendanceListDto lastDto = attendanceList.get(attendanceList.size() - 1);
		// 帳票代休申請不要設定
		lastDto.setSubHolidayRequestValid(TimeUtility.isSubHolidayRequestValid(mospParams));
	}
	
	// TODO TimeApplicationBeanに作成
	/**
	 * 対象個人ID及び締期間最終日で勤怠設定情報群を取得し、設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApplicationSettings() throws MospException {
		// 勤怠一覧情報リスト初期化
		attendanceList = new ArrayList<AttendanceListDto>();
		// 基準日における取得対象個人IDの設定適用情報を取得
		applicationDto = applicationReference.findForPerson(personalId, lastDate);
		// 設定適用情報確認
		applicationReference.chkExistApplication(applicationDto, lastDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 基準日における勤怠設定情報を取得
		timeSettingDto = timeSettingReference.getTimeSettingInfo(applicationDto.getWorkSettingCode(), lastDate);
		// 勤怠設定情報確認
		timeSettingReference.chkExistTimeSetting(timeSettingDto, lastDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 基準日における締日情報を取得
		cutoffDto = cutoffUtil.getCutoff(timeSettingDto.getCutoffCode(), lastDate);
		// 締日情報確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 基準日におけるカレンダ情報を取得
		scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(), lastDate);
		// カレンダ情報確認
		scheduleReference.chkExistSchedule(scheduleDto, lastDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 対象日及び締日から対象年月を算出し、設定する。<br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTargetYearMonth(Date targetDate) throws MospException {
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象日及び締日から対象年月を算出
		Date yearMonth = TimeUtility.getCutoffMonth(cutoffDate, targetDate);
		// 対象年月設定
		targetYear = DateUtility.getYear(yearMonth);
		targetMonth = DateUtility.getMonth(yearMonth);
	}
	
	/**
	 * 対象年月及び締日から締期間を算出し、設定する。<br>
	 * 勤怠一覧画面等の期間で勤怠情報を取得する場合は、この締期間を利用する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffTerm() throws MospException {
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 締期間初日設定
		firstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth);
		// 締期間最終日設定
		lastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth);
	}
	
	/**
	 * 締状態を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTightened() throws MospException {
		// 締状態取得
		Integer state = totalTimeEmployee.getCutoffState(personalId, targetYear, targetMonth);
		// 締状態設定
		isTightened = state != null && state != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
	}
	
	/**
	 * 勤怠一覧情報リストを初期化する。<br>
	 * 締期間初日～締期間最終日の勤怠一覧情報を作成し、初期化する。<br>
	 * <br>
	 * また、締期間初日～締期間最終日の
	 * カレンダ日情報リスト、各種申請情報を取得する。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initAttendanceList() throws MospException {
		// 初期勤怠一覧情報リスト準備
		attendanceList = new ArrayList<AttendanceListDto>();
		// 勤怠日準備(締期間初日)
		Date workDate = getDateClone(firstDate);
		// 締期間初日から締期間最終日までの初期勤怠一覧情報を作成
		while (workDate.after(lastDate) == false) {
			// 勤怠一覧情報準備
			AttendanceListDto dto = new AttendanceListDto();
			// 初期情報設定
			dto.setWorkDate(workDate);
			// 個人ID(頁単位項目)設定
			dto.setPersonalId(personalId);
			// 初期勤怠一覧情報リストに追加
			attendanceList.add(dto);
			// 勤怠日加算
			workDate = addDay(workDate, 1);
		}
		// ワークフロー情報群の初期化
		workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報群の初期化
		workTypeEntityMap = new HashMap<String, WorkTypeEntity>();
		// カレンダ日情報取得及び確認
		scheduleDateList = scheduleDateReference.findForList(scheduleDto.getScheduleCode(), firstDate, lastDate);
		// 勤怠情報取得
		attendanceDtoList = attendanceReference.getAttendanceList(personalId, firstDate, lastDate);
		// 休暇申請情報取得
		holidayRequestList = holidayRequest.getHolidayRequestList(personalId, firstDate, lastDate);
		// 残業申請情報取得
		overtimeRequestList = overtime.getOvertimeRequestList(personalId, firstDate, lastDate);
		// 代休申請情報取得
		subHolidayRequestList = subHoliday.getSubHolidayRequestList(personalId, firstDate, lastDate);
		// 振替休日情報取得
		substituteList = substituteReference.getSubstituteList(personalId, firstDate, lastDate);
		// 休日出勤申請情報取得
		workOnHolidayRequestList = workOnHoliday.getWorkOnHolidayRequestList(personalId, firstDate, lastDate);
		// 勤務形態変更申請情報取得
		workTypeChangeRequestList = workTypeChange.getWorkTypeChangeRequestList(personalId, firstDate, lastDate);
		// 時差出勤申請情報リスト取得
		differenceRequestList = difference.getDifferenceRequestList(personalId, firstDate, lastDate);
	}
	
	/**
	 * 申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 同時に、申請情報の集計を行う。<br>
	 * 実績一覧(出勤簿等)でない場合、申請によって、
	 * 申請日の勤怠が下書き状態であれば下書き情報をリストに加える。<br>
	 * @param containNotApproved 未承認要否フラグ(true：下書等未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @param isSchedule 予定確認フラグ(true：予定確認、false：予定確認ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApplicationList(boolean containNotApproved, boolean isActualList, boolean isSchedule)
			throws MospException {
		// 勤務形態変更申請情報設定
		addWorkTypeChangeRequestList(containNotApproved, isActualList, isSchedule);
		// 休暇申請情報設定
		addHolidayRequestList(containNotApproved);
		// 休日出勤申請情報設定
		addWorkOnHolidayRequestList(containNotApproved, isActualList);
		// 振替休日申請情報設定
		addSubstituteList(containNotApproved);
		// 代休申請情報設定
		addSubHolidayRequestList(containNotApproved);
		// 残業申請情報設定
		addOvertimeRequestList(containNotApproved);
		// 時差出勤申請情報設定
		addDifferenceRequestList(containNotApproved, isActualList);
		// 時間単位年休利用確認
		confirmTimelyPaidHoliday();
		// 同日複数申請確認
		checkPluralRequest(containNotApproved);
	}
	
	/**
	 * 休暇申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 同時に、休暇申請情報の集計を行う。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHolidayRequestList(boolean containNotApproved) throws MospException {
		// 集計値準備
		// 有給休暇回数
		float paidHolidays = 0F;
		// 有給時間
		float paidHolidayTime = 0F;
		// 特別休暇回数
		float specialHolidays = 0F;
		// その他休暇回数
		float otherHolidays = 0F;
		// 欠勤回数
		float absenceDays = 0F;
		// 休暇申請情報取得
		holidayRequestList = holidayRequest.getHolidayRequestList(personalId, firstDate, lastDate);
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(holidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					// 対象日(申請開始日)取得
					Date targetDate = holidayRequestDto.getRequestStartDate();
					// 申請終了日取得
					Date endDate = holidayRequestDto.getRequestEndDate();
					// 開始日から終了日まで日毎に処理
					while (!endDate.before(targetDate)) {
						// 設定適用情報を取得
						ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId,
								targetDate);
						// 設定適用情報確認
						applicationReference.chkExistApplication(applicationDto, targetDate);
						if (mospParams.hasErrorMessage()) {
							return;
						}
						// カレンダ情報を取得
						ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(
								applicationDto.getScheduleCode(), targetDate);
						// カレンダ情報確認
						scheduleReference.chkExistSchedule(scheduleDto, targetDate);
						if (mospParams.hasErrorMessage()) {
							return;
						}
						// カレンダ日情報取得及び確認
						ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
								scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), targetDate);
						scheduleDateReference.chkExistScheduleDate(scheduleDateDto, targetDate);
						if (mospParams.hasErrorMessage()) {
							return;
						}
						// 勤務形態コード取得
						String workTypeCode = scheduleDateDto.getWorkTypeCode();
						// 各種申請情報取得
						requestUtil.setRequests(personalId, targetDate);
						// 休日出勤申請がある場合
						WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil
							.getWorkOnHolidayDto(true);
						if (workOnHolidayRequestDto == null) {
							for (SubstituteDtoInterface substituteDto : requestUtil.getSubstituteList(true)) {
								if (isAll(substituteDto)) {
									// 全休の場合
									workTypeCode = substituteDto.getSubstituteType();
									break;
								}
							}
						} else {
							workTypeCode = getWorkOnHolidayWorkType(workOnHolidayRequestDto);
						}
						// 法定休日・所定休日・法定休日出勤・所定休日出勤の場合
						if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
								|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)
								|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
								|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
							// 対象日加算
							targetDate = addDay(targetDate, 1);
							continue;
						}
						// 対象日における勤怠一覧情報を取得
						AttendanceListDto dto = getAttendanceListDto(targetDate);
						// 対象日加算
						targetDate = addDay(targetDate, 1);
						// 対象日における勤怠一覧情報の存在を確認
						if (dto == null) {
							continue;
						}
						// 備考
						String remark = getHolidayNaming() + getDraftAbbrNaming();
						// 有給休暇時間承認状態別休数回数マップ取得
						Map<String, Integer> timeHoliday = holidayRequest.getTimeHolidayStatusTimesMap(personalId,
								dto.getWorkDate(), null);
						// 時休を取得している場合
						if (timeHoliday.get(getDraftAbbrNaming()) > 0) {
							// 備考追加
							remark = remark + PlatformBean.STR_SB_SAPCE + mospParams.getName("Hour")
									+ timeHoliday.get(getDraftAbbrNaming());
						}
						// 備考設定
						addRemark(dto, remark);
					}
				}
				continue;
			}
			// 休暇回数取得
			float times = getHolidayTimes(holidayRequestDto);
			// 対象日(申請開始日)取得
			Date targetDate = holidayRequestDto.getRequestStartDate();
			// 申請終了日取得
			Date endDate = holidayRequestDto.getRequestEndDate();
			// 開始日から終了日まで日毎に処理
			while (!endDate.before(targetDate)) {
				// 設定適用情報を取得
				ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
				// 設定適用情報確認
				applicationReference.chkExistApplication(applicationDto, targetDate);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// カレンダ情報を取得
				ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
						targetDate);
				// カレンダ情報確認
				scheduleReference.chkExistSchedule(scheduleDto, targetDate);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// カレンダ日情報取得及び確認
				ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
						scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), targetDate);
				scheduleDateReference.chkExistScheduleDate(scheduleDateDto, targetDate);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				String workTypeCode = scheduleDateDto.getWorkTypeCode();
				// 各種申請情報取得
				requestUtil.setRequests(personalId, targetDate);
				WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
				if (workOnHolidayRequestDto == null) {
					for (SubstituteDtoInterface substituteDto : requestUtil.getSubstituteList(true)) {
						if (isAll(substituteDto)) {
							// 全休の場合
							workTypeCode = substituteDto.getSubstituteType();
							break;
						}
					}
				} else {
					workTypeCode = getWorkOnHolidayWorkType(workOnHolidayRequestDto);
				}
				// 法定休日・所定休日・法定休日出勤・所定休日出勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)
						|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
						|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
					// 対象日加算
					targetDate = addDay(targetDate, 1);
					continue;
				}
				// 対象日における勤怠一覧情報を取得
				AttendanceListDto dto = getAttendanceListDto(targetDate);
				// 対象日加算
				targetDate = addDay(targetDate, 1);
				// 対象日における勤怠一覧情報の存在を確認
				if (dto == null) {
					continue;
				}
				// 休暇申請情報を勤怠一覧情報に設定
				setDtoFields(dto, holidayRequestDto, workflowDto);
				// 休暇区分確認
				switch (holidayRequestDto.getHolidayType1()) {
					case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
						// 有給休暇の場合
						// 休暇範囲確認
						if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// 有給時間加算
							paidHolidayTime += times;
						} else {
							// 有給休暇回数加算
							paidHolidays += times;
						}
						break;
					case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
						// 特別休暇の場合
						// 特別休暇回数加算
						specialHolidays += times;
						break;
					case TimeConst.CODE_HOLIDAYTYPE_OTHER:
						// その他休暇の場合
						// その他休暇回数加算
						otherHolidays += times;
						break;
					case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
						// 欠勤の場合
						// 欠勤回数加算
						absenceDays += times;
						break;
					default:
						// 処理無し
				}
			}
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 有給休暇回数
		dto.setPaidHolidays(paidHolidays);
		// 有給時間
		dto.setPaidHolidayTime(paidHolidayTime);
		// 特別休暇回数
		dto.setSpecialHolidays(specialHolidays);
		// その他休暇回数
		dto.setOtherHolidays(otherHolidays);
		// 欠勤回数
		dto.setAbsenceDays(absenceDays);
	}
	
	/**
	 * ワークフロー情報から申請情報の要否を確認する。<br>
	 * ワークフロー状況及び未承認要否フラグで判断する。<br>
	 * @param workflowDto        ワークフロー情報
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @return 申請情報の要否(true：必要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isRequestNeeded(WorkflowDtoInterface workflowDto, boolean containNotApproved)
			throws MospException {
		// ワークフロー状況取得
		String workflowStatus = workflowDto.getWorkflowStatus();
		// ワークフロー状況確認(下書の場合)
		if (workflow.isDraft(workflowDto)) {
			// 不要
			return false;
		}
		// ワークフロー状況確認(取下の場合)
		if (workflowStatus.equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
			// 不要
			return false;
		}
		// 未承認要否フラグ及びワークフロー状況確認(承認済でない場合)
		if (containNotApproved == false && workflowStatus.equals(PlatformConst.CODE_STATUS_COMPLETE) == false) {
			// 不要
			return false;
		}
		return true;
	}
	
	/**
	 * ワークフロー情報から差戻かどうかを判断する。<br>
	 * @param dto ワークフロー情報
	 * @return true：差戻、false：差戻でない
	 */
	protected boolean isRevert(WorkflowDtoInterface dto) {
		return PlatformConst.CODE_STATUS_REVERT.equals(dto.getWorkflowStatus());
	}
	
	/**
	 * 休暇回数を取得する。<br>
	 * 休暇申請情報の休暇範囲から、休暇回数を取得する。<br>
	 * 休暇申請の場合は、時間休も考慮する。<br>
	 * @param holidayRequestDto 休暇申請情報
	 * @return 休暇回数
	 */
	protected float getHolidayTimes(HolidayRequestDtoInterface holidayRequestDto) {
		// 休暇範囲確認
		switch (holidayRequestDto.getHolidayRange()) {
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休の場合
				return TimeConst.HOLIDAY_TIMES_ALL;
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 半休の場合
				return TimeConst.HOLIDAY_TIMES_HALF;
			case TimeConst.CODE_HOLIDAY_RANGE_TIME:
				// 時間休の場合
				// 開始時刻と終了時刻の差を取得
				return holidayRequestDto.getUseHour();
			default:
				return 0F;
		}
	}
	
	/**
	 * 休暇回数を取得する。<br>
	 * 休暇申請情報の休暇範囲から、休暇回数を取得する。<br>
	 * @param holidayRange 休暇範囲
	 * @return 休暇回数
	 */
	protected float getHolidayTimes(int holidayRange) {
		// 休暇範囲確認
		switch (holidayRange) {
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休の場合
				return TimeConst.HOLIDAY_TIMES_ALL;
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 半休の場合
				return TimeConst.HOLIDAY_TIMES_HALF;
			default:
				return 0F;
		}
	}
	
	/**
	 * 休暇申請情報を勤怠一覧情報に設定する。<br>
	 * ワークフロー情報は、休暇申請情報から取得したものとする。<br>
	 * @param dto               設定対象勤怠一覧情報
	 * @param holidayRequestDto 休暇申請情報
	 * @param workflowDto       ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, HolidayRequestDtoInterface holidayRequestDto,
			WorkflowDtoInterface workflowDto) throws MospException {
		// 備考
		String remark = getHolidayNaming() + getWorkflowStatusAbbr(workflowDto);
		// 有給休暇時間承認状態別休数回数マップ取得
		Map<String, Integer> timeHoliday = holidayRequest.getTimeHolidayStatusTimesMap(personalId,
				holidayRequestDto.getRequestStartDate(), null);
		// 時休を取得している場合
		if (timeHoliday.get(getWorkflowStatusAbbr(workflowDto)) > 0) {
			// 備考追加
			remark = remark + PlatformBean.STR_SB_SAPCE + mospParams.getName("Hour")
					+ timeHoliday.get(getWorkflowStatusAbbr(workflowDto));
		}
		// 備考設定
		addRemark(dto, remark);
		// 休暇範囲確認
		if (holidayRequestDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休以外の場合は設定不要
			return;
		}
		// 勤務形態設定(空白)(予定による上書を防ぐため)
		dto.setWorkTypeCode("");
		// チェックボックス要否設定(不要)
		dto.setNeedCheckbox(false);
		// 状態設定
		dto.setApplicationInfo(workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(),
				workflowDto.getWorkflowStage()));
		// 状態リンクコマンド設定
		dto.setNeedStatusLink(false);
		// 休暇申請のレコード識別IDを設定
		dto.setAttendanceRecordId(holidayRequestDto.getTmdHolidayRequestId());
		// 休暇区分毎に勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkTypeAbbr(holidayRequestDto));
	}
	
	/**
	 * ワークフロー状況略称を取得する。<br>
	 * ワークフロー状況によって申、済を返す。<br>
	 * @param workflowDto ワークフロー情報
	 * @return ワークフロー状況略称
	 */
	protected String getWorkflowStatusAbbr(WorkflowDtoInterface workflowDto) {
		if (workflowDto == null) {
			return "";
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return "";
		}
		// ワークフロー状況確認(下書の場合)
		if (workflow.isDraft(workflowDto)) {
			return getDraftAbbrNaming();
		}
		// ワークフロー状況確認(承認済の場合)
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return getCompleteApprovalAbbrNaming();
		}
		// ワークフロー状況確認(1次戻の場合)
		if (isRevert(workflowDto) && workflowDto.getWorkflowStage() == 0) {
			// 1次戻の場合
			return getRevertAbbrNaming();
		}
		return getApprovalAbbrNaming();
	}
	
	/**
	 * 休日出勤申請の略称(備考用)を取得する。
	 * @param dto 休日出勤申請情報
	 * @return 休日出勤の略称(備考用)
	 */
	protected String getWorkOnHolidayAbbr(WorkOnHolidayRequestDtoInterface dto) {
		// 振替申請を取得
		int substitute = dto.getSubstitute();
		// 振替申請の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤略称を設定
			return getSubstituteWorkAbbrNaming();
		}
		// 休日出勤申請の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤略称を設定
			return getWorkOnHolidayAbbrNaming();
		}
		return "";
	}
	
	/**
	 * 休日出勤申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkOnHolidayRequestList(boolean containNotApproved, boolean isActualList) throws MospException {
		// 休日出勤申請毎に処理
		for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(workOnHolidayRequestDto.getRequestDate());
			// 休日出勤略称を設定
			String requestAbbr = getWorkOnHolidayAbbr(workOnHolidayRequestDto);
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, requestAbbr + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, requestAbbr + getWorkflowStatusAbbr(workflowDto));
			// 勤務形態確認
			if (dto.getWorkTypeCode() != null) {
				// 勤務形態が設定されている場合(実績が存在する場合)
				continue;
			}
			// チェックボックス要否設定
			setNeedCheckbox(dto, workOnHolidayRequestDto);
			// 実績一覧の場合
			if (isActualList) {
				dto.setWorkTypeCode("");
				continue;
			}
			// 休日出勤申請情報から休日出勤時の予定勤務形態を取得して設定
			dto.setWorkTypeCode(getWorkOnHolidayWorkType(workOnHolidayRequestDto));
			// 振替申請確認
			if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 振替申請しない場合
				// 始業予定時間設定
				dto.setStartTime(getWorkTypeStartTime(dto));
				// 終業予定時間設定
				dto.setEndTime(getWorkTypeEndTime(dto));
				// 状態設定(予定)
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// 勤怠一覧情報に勤務形態の内容を設定
				setDtoFields(dto, dto.getWorkTypeCode(), true, false,
						workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM,
						workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM);
				// チェックボックス要否設定
				setNeedCheckbox(dto, workOnHolidayRequestDto);
			}
			// 勤怠データ情報取得(下書確認)
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			if (attendanceDto != null) {
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
				// チェックボックス要否設定
				setNeedCheckbox(dto, workOnHolidayRequestDto);
			}
		}
	}
	
	/**
	 * 振替休日情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubstituteList(boolean containNotApproved) throws MospException {
		// 振替休日回数
		float substituteHolidays = 0F;
		// 振替休日毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 振替休日の元である休日出勤申請情報を取得
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHoliday.findForWorkflow(substituteDto
				.getWorkflow());
			// 休日出勤申請情報確認
			if (workOnHolidayRequestDto == null) {
				continue;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				continue;
			}
			// 振替休日と同日の休日出勤申請情報を取得
			WorkOnHolidayRequestDtoInterface sameDayWorkOnHolidayRequestDto = workOnHoliday.findForKeyOnWorkflow(
					personalId, substituteDto.getSubstituteDate());
			// 振替休日と同日の休日出勤申請情報確認
			if (sameDayWorkOnHolidayRequestDto != null) {
				// 振替休日と同日の休日出勤申請情報のワークフロー情報取得
				WorkflowDtoInterface sameDayWorkflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (isRequestNeeded(sameDayWorkflowDto, containNotApproved)) {
					continue;
				}
			}
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(substituteDto.getSubstituteDate());
			// 備考設定
			addRemark(dto, getSubstituteAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// 振替休日回数加算
			substituteHolidays += getHolidayTimes(substituteDto.getSubstituteRange());
			// 休暇範囲確認
			if (substituteDto.getSubstituteRange() != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休以外の場合は設定不要
				continue;
			}
			// 勤務形態コード設定
			dto.setWorkTypeCode("");
			// 振替休日種別確認
			if (substituteDto.getSubstituteType().equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
				// 勤務形態略称設定
				dto.setWorkTypeAbbr(getLegalSubstituteAbbrNaming());
			} else {
				// 勤務形態略称設定
				dto.setWorkTypeAbbr(getPrescribedSubstituteAbbrNaming());
			}
			// 状態設定
			dto.setApplicationInfo(workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(),
					workflowDto.getWorkflowStage()));
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 休日出勤回数
		dto.setSubstituteHolidays(substituteHolidays);
	}
	
	/**
	 * 代休申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubHolidayRequestList(boolean containNotApproved) throws MospException {
		// 代休回数
		float subHolidays = 0F;
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(subHolidayRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(subHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getSubHolidayAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getSubHolidayAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// 代休回数加算
			subHolidays += getHolidayTimes(subHolidayRequestDto.getHolidayRange());
			// 休暇範囲確認
			if (subHolidayRequestDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休以外の場合は設定不要
				continue;
			}
			// 勤務形態コード設定
			dto.setWorkTypeCode("");
			// 勤務形態略称設定
			dto.setWorkTypeAbbr(getWorkTypeAbbr(subHolidayRequestDto));
			// 状態設定
			dto.setApplicationInfo(workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(),
					workflowDto.getWorkflowStage()));
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 代休回数
		dto.setSubHolidays(subHolidays);
	}
	
	/**
	 * 残業申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addOvertimeRequestList(boolean containNotApproved) throws MospException {
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(overtimeRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(overtimeRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getOvertimeAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getOvertimeAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
		}
	}
	
	/**
	 * 勤務形態変更申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @param isSchedule 予定確認フラグ(true：予定確認、false：予定確認ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkTypeChangeRequestList(boolean containNotApproved, boolean isActualList, boolean isSchedule)
			throws MospException {
		// 勤務形態変更申請毎に処理
		for (WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto : workTypeChangeRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(workTypeChangeRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workTypeChangeRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getWorkTypeChangeAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getWorkTypeChangeAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// ワークフロー状況確認
			boolean isComplete = workflow.isCompleted(workflowDto);
			if (isComplete && dto.getWorkTypeCode() == null) {
				// 勤務形態設定
				dto.setWorkTypeCode(workTypeChangeRequestDto.getWorkTypeCode());
			}
			// 実績一覧の場合
			if (isActualList) {
				continue;
			}
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(workTypeChangeRequestDto.getWorkTypeCode());
				// 始業予定時間設定
				dto.setStartTime(getWorkTypeStartTime(dto));
				// 終業予定時間設定
				dto.setEndTime(getWorkTypeEndTime(dto));
			}
			// 勤怠データ情報取得
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			// 勤怠データ確認
			if (attendanceDto == null) {
				// 勤怠データが存在しない場合
				dto.setNeedCheckbox(true);
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// 予定確認の場合
				if (isSchedule) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
			}
		}
	}
	
	/**
	 * 時差出勤申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addDifferenceRequestList(boolean containNotApproved, boolean isActualList) throws MospException {
		// 時差出勤申請情報取得
		List<DifferenceRequestDtoInterface> list = difference.getDifferenceRequestList(personalId, firstDate, lastDate);
		// 時差出勤申請毎に処理
		for (DifferenceRequestDtoInterface differenceRequestDto : list) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(differenceRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(differenceRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getTimeDefferenceAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getTimeDefferenceAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// ワークフロー状況確認
			boolean isComplete = workflow.isCompleted(workflowDto);
			boolean isApprovable = workflow.isApprovable(workflowDto);
			boolean isFirstReverted = workflow.isFirstReverted(workflowDto);
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
			}
			// 実績一覧の場合
			if (isActualList) {
				continue;
			}
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
				// 始業予定時間設定
				dto.setStartTime(differenceRequestDto.getRequestStart());
				// 終業予定時間設定
				dto.setEndTime(differenceRequestDto.getRequestEnd());
			} else if (isApprovable || isFirstReverted) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
			}
			// 勤怠データ情報取得
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			// 勤怠データ確認
			if (attendanceDto == null) {
				// 勤怠データが存在しない場合
				dto.setNeedCheckbox(true);
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
				if (isComplete || isApprovable || isFirstReverted) {
					// 勤務形態設定
					dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
				}
			}
		}
	}
	
	/**
	 * 時間単位年休の利用確認を行う。<br>
	 * 時間単位年休を利用しない場合は、時間単位年休の合計値をnullにする。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void confirmTimelyPaidHoliday() throws MospException {
		// 有給休暇コード取得
		String paidHolidayCode = applicationDto.getPaidHolidayCode();
		// 有給休暇設定取得
		PaidHolidayDtoInterface paidHolidayDto = paidHoliday.getPaidHolidayInfo(paidHolidayCode, lastDate);
		// 時間単位年休利用フラグ確認
		if (paidHolidayDto != null && paidHolidayDto.getTimelyPaidHolidayFlag() == MospConst.INACTIVATE_FLAG_OFF) {
			return;
		}
		// 時間単位年休無効の場合
		// 勤怠一覧情報リスト最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 有給時間
		dto.setPaidHolidayTime(null);
	}
	
	/**
	 * 同日複数申請を確認する。<br>
	 * 同日に複数の休暇申請、振替休日、代休申請があり、合わせて終日休暇となる場合に、
	 * 状態及びチェックボックス要否を再設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkPluralRequest(boolean containNotApproved) throws MospException {
		// 勤怠一覧情報毎に処理
		attendanceList: for (AttendanceListDto dto : attendanceList) {
			// 休日日数準備
			float holidayTimes = 0F;
			// 午前休ワークフロー
			WorkflowDtoInterface anteWorkflowDto = null;
			// 午後休ワークフロー
			WorkflowDtoInterface postWorkflowDto = null;
			// 休暇申請情報確認
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				// 休暇日確認
				if (DateUtility.isTermContain(dto.getWorkDate(), holidayRequestDto.getRequestStartDate(),
						holidayRequestDto.getRequestEndDate()) == false) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(holidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 休暇範囲確認
				if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合は処理不要
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(holidayRequestDto.getHolidayRange());
				String workTypeAbbrInitial = getWorkTypeAbbr(holidayRequestDto).substring(0, 1);
				if (dto.getWorkTypeAnteAbbr() == null
						&& holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(workTypeAbbrInitial);
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(workTypeAbbrInitial);
					postWorkflowDto = workflowDto;
				}
			}
			// 代休申請情報確認
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休日確認
				if (subHolidayRequestDto.getRequestDate().compareTo(dto.getWorkDate()) != 0) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(subHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 休暇範囲確認
				if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合は処理不要
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(subHolidayRequestDto.getHolidayRange());
				if (dto.getWorkTypeAnteAbbr() == null
						&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(getSubHolidayAbbrNaming());
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(getSubHolidayAbbrNaming());
					postWorkflowDto = workflowDto;
				}
			}
			// 振出・休出申請情報確認
			for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
				// 出勤日確認
				if (!workOnHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflow
					.getLatestWorkflowInfo(workOnHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 振替申請確認
				if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
						|| workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 振替出勤(全日)又は休日出勤の場合は処理不要
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += TimeConst.HOLIDAY_TIMES_HALF;
				if (dto.getWorkTypePostAbbr() == null
						&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
					// 振替出勤(午前)の場合
					dto.setWorkTypePostAbbr(getHalfSubstituteWorkAbbrNaming());
					postWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypeAnteAbbr() == null
						&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
					// 振替出勤(午後)の場合
					dto.setWorkTypeAnteAbbr(getHalfSubstituteWorkAbbrNaming());
					anteWorkflowDto = workflowDto;
				}
			}
			// 振替休日情報確認
			for (SubstituteDtoInterface substituteDto : substituteList) {
				// 振替日確認
				if (!substituteDto.getSubstituteDate().equals(dto.getWorkDate())) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(substituteDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 振替休日確認
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合は処理不要
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(substituteDto.getSubstituteRange());
				if (dto.getWorkTypeAnteAbbr() == null
						&& substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(getSubstituteHolidayAbbrNaming());
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(getSubstituteHolidayAbbrNaming());
					postWorkflowDto = workflowDto;
				}
			}
			// 休日日数確認
			if (holidayTimes >= TimeConst.HOLIDAY_TIMES_ALL) {
				// 勤務形態コード設定
				dto.setWorkTypeCode("");
				// 勤務形態略称設定
				dto.setWorkTypeAbbr(dto.getWorkTypeAnteAbbr() + getSlashNaming() + dto.getWorkTypePostAbbr());
				if (getSubstituteHolidayAbbrNaming().equals(dto.getWorkTypeAnteAbbr())) {
					// 午前振休である場合
					if (getSubstituteHolidayAbbrNaming().equals(dto.getWorkTypePostAbbr())) {
						// 午後振休である場合
						dto.setWorkTypeAbbr(getSubstituteHolidayAbbrNaming());
					} else {
						// 午後振休でない場合
						dto.setWorkTypeAbbr(getAnteMeridiemSubstituteHolidayAbbrNaming() + getSlashNaming()
								+ dto.getWorkTypePostAbbr());
					}
				} else {
					// 午前振休でない場合
					if (getSubstituteHolidayAbbrNaming().equals(dto.getWorkTypePostAbbr())) {
						// 午後振休である場合
						dto.setWorkTypeAbbr(dto.getWorkTypeAnteAbbr() + getSlashNaming()
								+ getPostMeridiemSubstituteHolidayAbbrNaming());
					}
				}
				// チェックボックス設定
				dto.setNeedCheckbox(false);
				// 状態設定
				setApplicationInfo(dto, anteWorkflowDto, postWorkflowDto);
			}
		}
	}
	
	/**
	 * 勤怠情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：下書き等未承認勤怠も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addActualList(boolean containNotApproved) throws MospException {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface attendanceDto : attendanceDtoList) {
			// 勤怠情報の勤務日から勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(attendanceDto.getWorkDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(attendanceDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				continue;
			}
			// 勤怠一覧情報に値を設定
			setDtoFields(dto, attendanceDto, workflowDto);
		}
	}
	
	/**
	 * 勤怠情報(下書)を勤怠一覧情報リストに設定する。<br>
	 * 但し、既に対象日の勤怠一覧情報に勤務形態が設定されている場合は設定しない。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addDraftList() throws MospException {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface attendanceDto : attendanceDtoList) {
			// 勤怠情報の勤務日から勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(attendanceDto.getWorkDate());
			// 勤怠情報(下書)要否確認
			if (dto.getWorkTypeCode() != null) {
				// 勤務形態が既に設定されている場合
				continue;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(attendanceDto.getWorkflow());
			// ワークフロー状況確認
			if (workflow.isDraft(workflowDto) == false) {
				// 下書でない場合
				continue;
			}
			// 勤怠一覧情報に値を設定
			setDtoFields(dto, attendanceDto, workflowDto);
		}
	}
	
	/**
	 * カレンダ情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 但し、既に対象日の勤怠一覧情報に勤務形態が設定されている場合は設定しない。<br>
	 * 出勤簿では、出勤予定日が不要となる。<br>
	 * 勤怠一覧では、勤務時間及び休憩時間が不要となる。<br>
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間及び休憩時間要否(true：要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addScheduleList(boolean needWorkDay, boolean needWorkTime) throws MospException {
		// 人事退職情報から退職日を取得
		Date retireDate = retirement.getRetireDate(personalId);
		// 設定適用マスタ(対象期間)情報取得
		Map<Date, ApplicationDtoInterface> mapApplication = applicationReference.findForTerm(personalId, firstDate,
				lastDate);
		// 勤怠一覧情報にカレンダ日情報を設定
		for (AttendanceListDto dto : attendanceList) {
			// カレンダ日情報要否確認
			if (dto.getWorkTypeCode() != null) {
				// 勤務形態が既に設定されている場合
				continue;
			}
			// カレンダ日情報取得(勤怠一覧情報の勤務日で取得)及び確認
			ScheduleDateDtoInterface scheduleDateDto = getScheduleDateDto(dto.getWorkDate());
			if (scheduleDateDto == null || scheduleDateDto.getWorkTypeCode().isEmpty()) {
				// カレンダ日情報が存在しない場合
				continue;
			}
			// 設定適用マスタ状況確認
			if (mapApplication.get(dto.getWorkDate()) == null) {
				// 対象日に設定適用情報が存在しない場合
				continue;
			}
			// 退職日確認
			if (retireDate != null && retireDate.before(dto.getWorkDate())) {
				// 既に退職している場合
				continue;
			}
			// 休職期間確認
			SuspensionDtoInterface suspensionDto = suspension.getSuspentionInfo(personalId, dto.getWorkDate());
			if (suspensionDto != null) {
				// 休職している場合
				StringBuffer sb = new StringBuffer();
				sb.append(getAdministrativeLeaveNaming());
				if (suspensionDto.getSuspensionReason() != null && !suspensionDto.getSuspensionReason().isEmpty()) {
					// 休職理由がある場合
					sb.append(mospParams.getName("SingleColon"));
					sb.append(suspensionDto.getSuspensionReason());
				}
				addRemark(dto, sb.toString());
				continue;
			}
			// 勤怠一覧情報に勤務形態の内容を設定
			setDtoFields(dto, scheduleDateDto.getWorkTypeCode(), needWorkDay, needWorkTime);
		}
	}
	
	/**
	 * 休暇申請情報から勤務形態略称を取得する。<br>
	 * @param dto 休暇申請情報
	 * @return 勤務形態略称
	 * @throws MospException 特別休暇或いはその他休暇の略称取得に失敗した場合
	 */
	protected String getWorkTypeAbbr(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇区分毎に勤務形態略称を取得
		switch (dto.getHolidayType1()) {
			case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
				// 有給休暇の場合
				if (dto.getHolidayType2().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
					return getPaidHolidayAbbrNaming();
				} else {
					return getStockHolidayAbbrNaming();
				}
			case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
			case TimeConst.CODE_HOLIDAYTYPE_OTHER:
			case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
				// 特別休暇・その他休暇・欠勤の場合
				return holiday.getHolidayAbbr(dto.getHolidayType2(), dto.getRequestEndDate(), dto.getHolidayType1());
			default:
				return null;
		}
	}
	
	/**
	 * 代休申請情報から勤務形態略称を取得する。<br>
	 * @param dto 代休申請情報
	 * @return 勤務形態略称
	 */
	protected String getWorkTypeAbbr(SubHolidayRequestDtoInterface dto) {
		// 代休種別確認
		switch (dto.getWorkDateSubHolidayType()) {
			case TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE:
			case TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE:
				// 所定代休或いは深夜代休
				return getPrescribedSubHolidayAbbrNaming();
			case TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE:
				// 法定代休
				return getLegalSubHolidayAbbrNaming();
			default:
				return null;
		}
	}
	
	/**
	 * 未承認確認を行う。<br>
	 * ワークフロー状況及び段階から、承認可能であるかを確認する。<br>
	 * @param dto ワークフロー情報
	 * @return 未承認確認結果(true：未承認、false：未承認でない)
	 */
	protected boolean isApprovable(WorkflowDtoInterface dto) {
		// ワークフロー状況取得
		String status = dto.getWorkflowStatus();
		// 未承認、承認済(完了でない)の場合
		if (status.equals(PlatformConst.CODE_STATUS_APPLY) || status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 未承認
			return true;
		}
		// 差戻、取消(承認解除)の場合
		if (status.equals(PlatformConst.CODE_STATUS_CANCEL) || status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 段階が0より大きい場合(申請者が操作者でない場合)
			if (dto.getWorkflowStage() > 0) {
				return true;
			}
		}
		// それ以外の場合
		return false;
	}
	
	/**
	 * チェックボックス要否(勤怠一覧用)を取得する。<br>
	 * @param dto ワークフロー情報
	 * @return チェックボックス要否(勤怠一覧用)
	 */
	protected boolean isNeedCheckBox(WorkflowDtoInterface dto) {
		// 下書の場合
		if (workflow.isDraft(dto)) {
			return true;
		}
		// 差戻の場合
		if (isRevert(dto)) {
			// 段階が0(申請者)の場合
			if (dto.getWorkflowStage() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 状態リンク要否(勤怠一覧用)を取得する。<br>
	 * @param dto ワークフロー情報
	 * @return 状態リンク要否(勤怠一覧用)
	 */
	protected boolean getNeedStatusLink(WorkflowDtoInterface dto) {
		return !workflow.isDraft(dto);
	}
	
	/**
	 * カレンダ日情報リストから対象日のカレンダ日情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ScheduleDateDtoInterface getScheduleDateDto(Date targetDate) throws MospException {
		// カレンダ日情報リストから対象日のカレンダ日情報を取得
		for (ScheduleDateDtoInterface dto : scheduleDateList) {
			// 勤務日確認
			if (targetDate.equals(dto.getScheduleDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 勤怠データ情報リストから対象日の勤怠一覧情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 勤怠データ情報
	 */
	protected AttendanceDtoInterface getAttendanceDtoListDto(Date targetDate) {
		// 勤怠一覧情報確認
		if (attendanceDtoList == null) {
			return null;
		}
		// 勤怠一覧情報リストから対象日の勤怠一覧情報を取得
		for (AttendanceDtoInterface dto : attendanceDtoList) {
			// 勤務日確認
			if (targetDate.equals(dto.getWorkDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 勤怠一覧情報リストから対象日の勤怠一覧情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 勤怠一覧情報
	 */
	protected AttendanceListDto getAttendanceListDto(Date targetDate) {
		// 勤怠一覧情報リストから対象日の勤怠一覧情報を取得
		for (AttendanceListDto dto : attendanceList) {
			// 勤務日確認
			if (targetDate.equals(dto.getWorkDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 限度基準情報を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setLimitStandard() throws MospException {
		// 限度基準取得
		LimitStandardDtoInterface limitStandardDto = limitStandard.findForKey(timeSettingDto.getWorkSettingCode(),
				timeSettingDto.getActivateDate(), LIMIT_STANDARD_TERM_MONTH1);
		if (limitStandardDto == null) {
			return;
		}
		// 注意時間取得
		int attention = limitStandardDto.getAttentionTime();
		// 警告時間取得
		int warning = limitStandardDto.getWarningTime();
		// 法定外残業時間合計値準備
		int overtimeTotal = 0;
		// 勤怠一覧情報毎に残業時間を確認
		for (AttendanceListDto dto : attendanceList) {
			// 法定外残業時間確認
			if (dto.getOvertimeOut() == null || dto.getOvertimeOut().intValue() == 0F) {
				continue;
			}
			// 法定外残業時間加算
			overtimeTotal += dto.getOvertimeOut().intValue();
			// 注意時間確認
			if (overtimeTotal > attention) {
				dto.setOvertimeStyle(STYLE_YELLOW);
			}
			// 警告時間確認
			if (overtimeTotal > warning) {
				dto.setOvertimeStyle(STYLE_RED);
			}
		}
	}
	
	/**
	 * 勤怠一覧情報リストを集計する。<br>
	 * 集計結果は、リストの最終レコードに設定される。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void totalAttendanceList() throws MospException {
		// 集計値準備
		// 勤務時間(分)
		int workTimeTotal = 0;
		// 休憩時間(分)
		int restTimeTotal = 0;
		// 私用外出時間(分)
		int privateTimeTotal = 0;
		// 公用外出時間(分)
		int publicTimeTotal = 0;
		// 分単位休暇A時間(分)
		int minutelyHolidayATimeTotal = 0;
		// 分単位休暇B時間(分)
		int minutelyHolidayBTimeTotal = 0;
		// 遅刻時間(分)
		int lateTimeTotal = 0;
		// 早退時間(分)
		int leaveEarlyTimeTotal = 0;
		// 遅刻早退時間(分)
		int lateLeaveEarlyTimeTotal = 0;
		// 残業時間(分)
		int overtimeTotal = 0;
		// 内残時間(分)
		int overtimeInTotal = 0;
		// 外残時間(分)
		int overtimeOutTotal = 0;
		// 休出時間(分)
		int holidayWorkTimeTotal = 0;
		// 深夜時間(分)
		int lateNightTimeTotal = 0;
		// 出勤回数
		int workDays = 0;
		// 遅刻回数
		int lateDays = 0;
		// 早退回数
		int leaveEarlyDays = 0;
		// 残業回数
		int overtimeDays = 0;
		// 休出回数
		int holidayWorkDays = 0;
		// 深夜回数
		int lateNightDays = 0;
		// 所定休日回数
		int prescribedHolidays = 0;
		// 法定休日回数
		int legalHolidays = 0;
		// 勤怠一覧情報毎に集計
		for (AttendanceListDto dto : attendanceList) {
			// 勤務時間
			workTimeTotal += dto.getWorkTime() == null ? 0 : dto.getWorkTime().intValue();
			// 休憩時間
			restTimeTotal += dto.getRestTime() == null ? 0 : dto.getRestTime().intValue();
			// 私用外出時間
			privateTimeTotal += dto.getPrivateTime() == null ? 0 : dto.getPrivateTime().intValue();
			// 公用外出時間
			publicTimeTotal += dto.getPublicTime() == null ? 0 : dto.getPublicTime().intValue();
			// 分単位休暇A時間
			minutelyHolidayATimeTotal += dto.getMinutelyHolidayATime() == null ? 0 : dto.getMinutelyHolidayATime()
				.intValue();
			// 分単位休暇B時間
			minutelyHolidayBTimeTotal += dto.getMinutelyHolidayBTime() == null ? 0 : dto.getMinutelyHolidayBTime()
				.intValue();
			// 遅刻時間
			lateTimeTotal += dto.getLateTime() == null ? 0 : dto.getLateTime().intValue();
			// 早退時間
			leaveEarlyTimeTotal += dto.getLeaveEarlyTime() == null ? 0 : dto.getLeaveEarlyTime().intValue();
			// 遅刻早退時間
			lateLeaveEarlyTimeTotal += dto.getLateLeaveEarlyTime() == null ? 0 : dto.getLateLeaveEarlyTime().intValue();
			// 残業時間
			overtimeTotal += dto.getOvertime() == null ? 0 : dto.getOvertime().intValue();
			// 内残時間
			overtimeInTotal += dto.getOvertimeIn() == null ? 0 : dto.getOvertimeIn().intValue();
			// 外残時間
			overtimeOutTotal += dto.getOvertimeOut() == null ? 0 : dto.getOvertimeOut().intValue();
			// 休出時間
			holidayWorkTimeTotal += dto.getHolidayWorkTime() == null ? 0 : dto.getHolidayWorkTime().intValue();
			// 深夜時間
			lateNightTimeTotal += dto.getLateNightTime() == null ? 0 : dto.getLateNightTime().intValue();
			// 出勤回数
			workDays += dto.getGoingWork();
			// 遅刻回数
			lateDays += countHours(dto.getLateTime());
			// 早退回数
			leaveEarlyDays += countHours(dto.getLeaveEarlyTime());
			// 残業回数
			overtimeDays += countHours(dto.getOvertime());
			// 深夜回数
			lateNightDays += countHours(dto.getLateNightTime());
			// 勤務形態確認
			if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
				continue;
			}
			// 休出回数
			holidayWorkDays += TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())
					|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode()) ? 1 : 0;
			// 所定休日回数
			prescribedHolidays += dto.getWorkTypeCode().equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY) ? 1 : 0;
			// 法定休日回数
			legalHolidays += dto.getWorkTypeCode().equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY) ? 1 : 0;
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		dto.setWorkTimeTotal(workTimeTotal);
		dto.setRestTimeTotal(restTimeTotal);
		dto.setPrivateTimeTotal(privateTimeTotal);
		dto.setPublicTimeTotal(publicTimeTotal);
		dto.setMinutelyHolidayATimeTotal(minutelyHolidayATimeTotal);
		dto.setMinutelyHolidayBTimeTotal(minutelyHolidayBTimeTotal);
		dto.setLateTimeTotal(lateTimeTotal);
		dto.setLeaveEarlyTimeTotal(leaveEarlyTimeTotal);
		dto.setLateLeaveEarlyTimeTotal(lateLeaveEarlyTimeTotal);
		dto.setOvertimeTotal(overtimeTotal);
		dto.setOvertimeInTotal(overtimeInTotal);
		dto.setOvertimeOutTotal(overtimeOutTotal);
		dto.setHolidayWorkTimeTotal(holidayWorkTimeTotal);
		dto.setLateNightTimeTotal(lateNightTimeTotal);
		dto.setWorkDays(workDays);
		dto.setLateDays(lateDays);
		dto.setLeaveEarlyDays(leaveEarlyDays);
		dto.setOvertimeDays(overtimeDays);
		dto.setHolidayWorkDays(holidayWorkDays);
		dto.setLateNightDays(lateNightDays);
		dto.setPrescribedHolidays(prescribedHolidays);
		dto.setLegalHolidays(legalHolidays);
		// 代休発生日数取得
		Float[] subHolidayDays = subHolidayRefer.getBirthSubHolidayTimes(personalId, attendanceList.get(0)
			.getWorkDate(), attendanceList.get(attendanceList.size() - 1).getWorkDate());
		dto.setBirthPrescribedSubHoliday(subHolidayDays[0]);
		dto.setBirthLegalSubHoliday(subHolidayDays[1]);
		dto.setBirthMidnightSubHolidaydays(subHolidayDays[2]);
	}
	
	/**
	 * 勤怠一覧情報に勤怠情報の内容を設定する。<br>
	 * @param dto           設定対象勤怠一覧情報
	 * @param attendanceDto 勤怠情報
	 * @param workflowDto   ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, AttendanceDtoInterface attendanceDto,
			WorkflowDtoInterface workflowDto) throws MospException {
		// 出勤回数設定
		dto.setGoingWork(TimeBean.TIMES_WORK_DEFAULT);
		// 勤務形態
		dto.setWorkTypeCode(attendanceDto.getWorkTypeCode());
		// 始業時間
//		dto.setStartTime(attendanceDto.getStartTime());
		dto.setStartTime(attendanceDto.getActualStartTime());
		// 終業時間
//		dto.setEndTime(attendanceDto.getEndTime());
		dto.setEndTime(attendanceDto.getActualEndTime());
		// 勤務時間
		dto.setWorkTime(attendanceDto.getWorkTime());
		// 休憩時間
		dto.setRestTime(attendanceDto.getRestTime());
		// 私用外出時間
		dto.setPrivateTime(attendanceDto.getPrivateTime());
		// 公用外出時間
		dto.setPublicTime(attendanceDto.getPublicTime());
		// 分単位休暇A時間
		dto.setMinutelyHolidayATime(attendanceDto.getMinutelyHolidayATime());
		// 分単位休暇A全休
		dto.setMinutelyHolidayA(attendanceDto.getMinutelyHolidayA());
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTime(attendanceDto.getMinutelyHolidayBTime());
		// 分単位休暇B全休
		dto.setMinutelyHolidayB(attendanceDto.getMinutelyHolidayB());
		// 遅刻時間
		dto.setLateTime(attendanceDto.getLateTime());
		// 早退時間
		dto.setLeaveEarlyTime(attendanceDto.getLeaveEarlyTime());
		// 遅刻早退時間
		dto.setLateLeaveEarlyTime(attendanceDto.getLateTime() + attendanceDto.getLeaveEarlyTime());
		// 残業時間
		dto.setOvertime(attendanceDto.getOvertime());
		// 内残時間
		dto.setOvertimeIn(attendanceDto.getOvertimeIn());
		// 外残時間
		dto.setOvertimeOut(attendanceDto.getOvertimeOut());
		// 休出時間
		dto.setHolidayWorkTime(attendanceDto.getLegalWorkTime());
		// 深夜時間
		dto.setLateNightTime(attendanceDto.getLateNightTime());
		// 勤怠コメント
		dto.setTimeComment(attendanceDto.getTimeComment());
		// 備考
		addAttendanceRemark(dto, attendanceDto);
		// 申請情報(ワークフロー情報から取得)
		dto.setApplicationInfo(workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(),
				workflowDto.getWorkflowStage()));
		// チェックボックス要否(勤怠一覧用)
		dto.setNeedCheckbox(isNeedCheckBox(workflowDto));
		// 状態リンク要否(勤怠一覧用)
		dto.setNeedStatusLink(getNeedStatusLink(workflowDto));
		// 勤怠データレコード識別ID
		dto.setAttendanceRecordId(attendanceDto.getTmdAttendanceId());
		// ワークフローレコード識別ID
		dto.setWorkflowRecordId(workflowDto.getPftWorkflowId());
		// ワークフロー番号
		dto.setWorkflow(workflowDto.getWorkflow());
		// ワークフロー状況
		dto.setWorkflowStatus(workflowDto.getWorkflowStatus());
		// ワークフロー段階
		dto.setWorkflowStage(workflowDto.getWorkflowStage());
		// 修正情報
		dto.setCorrectionInfo(getCorrectionInfo(attendanceDto));
	}
	
	/**
	 * 修正情報を取得する。<br>
	 * @param attendanceDto 勤怠情報
	 * @return 修正情報（本/他）
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getCorrectionInfo(AttendanceDtoInterface attendanceDto) throws MospException {
		// 勤怠修正情報取得
		AttendanceCorrectionDtoInterface attendanceCorrectionDto = correction.getLatestAttendanceCorrectionInfo(
				personalId, attendanceDto.getWorkDate(), TIMES_WORK_DEFAULT);
		// 勤怠修正情報確認
		if (attendanceCorrectionDto == null) {
			return "";
		}
		// 勤怠修正者確認
		if (attendanceCorrectionDto.getPersonalId().equals(attendanceCorrectionDto.getCorrectionPersonalId())) {
			return getSelfCorrectAbbrNaming();
		}
		return getOtherCorrectAbbrNaming();
	}
	
	/**
	 * 勤怠一覧情報に勤怠情報の備考を追加する。<br>
	 * @param dto           追加対象勤怠一覧情報
	 * @param attendanceDto 勤怠情報
	 */
	protected void addAttendanceRemark(AttendanceListDto dto, AttendanceDtoInterface attendanceDto) {
		// 直行確認
		if (attendanceDto.getDirectStart() == Integer.parseInt(MospConst.CHECKBOX_ON)) {
			addRemark(dto, getDirectStartNaming());
		}
		// 直帰確認
		if (attendanceDto.getDirectEnd() == Integer.parseInt(MospConst.CHECKBOX_ON)) {
			addRemark(dto, getDirectEndNaming());
		}
	}
	
	/**
	 * 勤怠一覧情報に勤務形態情報の内容を設定する。<br>
	 * @param dto          設定対象勤怠一覧情報
	 * @param workTypeCode 勤務形態コード
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間、休憩時間要否(true：要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, String workTypeCode, boolean needWorkDay, boolean needWorkTime)
			throws MospException {
		// 勤務形態確認(所定休日或いは法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)
				|| workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 勤務形態設定
			dto.setWorkTypeCode(workTypeCode);
			// 状態設定(承認済)
			dto.setApplicationInfo(getApplovedNaming());
			// チェックボックス要否設定(不要)
			dto.setNeedCheckbox(false);
			return;
		}
		// 出勤予定日不要確認
		if (needWorkDay == false) {
			return;
		}
		// 勤務形態設定
		dto.setWorkTypeCode(workTypeCode);
		// 状態設定(予定)
		dto.setApplicationInfo(getScheduleNaming());
		// チェックボックス要否設定(要)
		dto.setNeedCheckbox(true);
		// 始業時間設定
		if (dto.getStartTime() == null) {
			dto.setStartTime(getWorkTypeStartTime(dto));
		}
		// 終業時間設定
		if (dto.getEndTime() == null) {
			dto.setEndTime(getWorkTypeEndTime(dto));
		}
		// 勤務時間要不要確認
		if (needWorkTime == false) {
			return;
		}
		// 勤務時間
		dto.setWorkTime(getWorkTypeEntity(dto).getWorkTime());
		// 休憩時間
		dto.setRestTime(getWorkTypeEntity(dto).getRestTime());
	}
	
	/**
	 * 勤怠一覧情報に勤務形態情報の内容を設定する。<br>
	 * @param dto          設定対象勤怠一覧情報
	 * @param workTypeCode 勤務形態コード
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間、休憩時間要否(true：要、false：不要)
	 * @param holidayRangeAm 午前休判断(true：午前休、false：午前休でない)
	 * @param holidayRangePm 午後休判断(true：午後休、false：午後休でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, String workTypeCode, boolean needWorkDay, boolean needWorkTime,
			boolean holidayRangeAm, boolean holidayRangePm) throws MospException {
		if (!holidayRangeAm && !holidayRangePm) {
			setDtoFields(dto, workTypeCode, needWorkDay, needWorkTime);
			return;
		}
		// 勤務形態確認(所定休日或いは法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)
				|| workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 勤務形態設定
			dto.setWorkTypeCode(workTypeCode);
			// 状態設定(承認済)
			dto.setApplicationInfo(getApplovedNaming());
			// チェックボックス要否設定(不要)
			dto.setNeedCheckbox(false);
			return;
		}
		// 出勤予定日不要確認
		if (needWorkDay == false) {
			return;
		}
		// 勤務形態設定
		dto.setWorkTypeCode(workTypeCode);
		// 状態設定(予定)
		dto.setApplicationInfo(getScheduleNaming());
		// チェックボックス要否設定(要)
		dto.setNeedCheckbox(true);
		// 予定始業時刻及び予定終業時刻を取得
		Date startTime = getWorkTypeStartTime(dto);
		Date endTime = getWorkTypeEndTime(dto);
		// 始業時間設定
		if (dto.getStartTime() == null) {
			dto.setStartTime(startTime);
		}
		// 終業時間設定
		if (dto.getEndTime() == null) {
			dto.setEndTime(endTime);
		}
		// 勤務時間要不要確認
		if (needWorkTime == false) {
			return;
		}
		// 勤務時間
		dto.setWorkTime(getWorkTypeEntity(dto).getWorkTime());
		// 休憩時間
		dto.setRestTime(getWorkTypeEntity(dto).getRestTime());
		// 午前休又は午後休の場合
		if (holidayRangeAm || holidayRangePm) {
			dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
			dto.setRestTime(null);
		}
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoStringFields() throws MospException {
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			setDtoStringFields(dto);
		}
	}
	
	/**
	 * 勤怠一覧情報の特定の項目にハイフンを設定する。<br>
	 * @param useScheduledTime 勤務予定時間表示要否(true：要、false：否)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoHyphenFields(boolean useScheduledTime) throws MospException {
		// 勤務予定時間表示要否確認
		if (useScheduledTime) {
			return;
		}
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			if (dto.getCorrectionInfo() == null) {
				// 始業時刻
				dto.setStartTimeString(getHyphenNaming());
				// 終業時刻
				dto.setEndTimeString(getHyphenNaming());
			}
		}
	}
	
	/**
	 * 勤怠一覧情報にチェックボックス要否設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedCheckbox() throws MospException {
		// 締状態確認
		if (isTightened == false) {
			// 未締の場合
			return;
		}
		// チェックボックスを不要に設定
		for (AttendanceListDto dto : attendanceList) {
			dto.setNeedCheckbox(false);
		}
	}
	
	/**
	 * 勤怠一覧情報にチェックボックス要否設定する。<br>
	 * @param dto 対象DTO
	 * @param workOnHolidayRequestDto 休日出勤申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedCheckbox(AttendanceListDto dto, WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		// チェックボックス要否設定(不要)
		dto.setNeedCheckbox(false);
		// ワークフロー状況確認(承認済の場合)
		if (workflow.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
			// チェックボックス要否設定(要)
			dto.setNeedCheckbox(true);
		}
	}
	
	/**
	 * 勤怠一覧情報(勤怠承認一覧)にチェックボックス要否設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedApprovalCheckbox() throws MospException {
		// 締状態確認
		if (isTightened) {
			// 未締でない場合
			// チェックボックスを不要に設定
			for (AttendanceListDto dto : attendanceList) {
				dto.setNeedCheckbox(false);
			}
			return;
		}
		// 承認可能ワークフロー情報マップ(勤怠)取得
		Map<Long, WorkflowDtoInterface> approvableMap = getApprovableMap();
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			// チェックボックス要否設定
			dto.setNeedCheckbox(false);
			// ワークフロー確認
			if (dto.getWorkflow() == 0) {
				// ワークフロー番号が設定されていない場合
				continue;
			}
			// 承認可能ワークフロー情報に含まれているか確認
			if (approvableMap.containsKey(dto.getWorkflow())) {
				// チェックボックス要否設定
				dto.setNeedCheckbox(true);
			}
		}
	}
	
	/**
	 * 承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * ログインユーザが承認可能な情報を取得する。<br>
	 * @return 承認可能ワークフロー情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Long, WorkflowDtoInterface> getApprovableMap() throws MospException {
		// ログインユーザ個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// 承認情報参照クラス取得
		ApprovalInfoReferenceBeanInterface approvalReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		// 承認可能ワークフローリスト(勤怠)取得
		return approvalReference.getApprovableMap(personalId).get(TimeConst.CODE_FUNCTION_WORK_MANGE);
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @param dto 設定対象勤怠一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoStringFields(AttendanceListDto dto) throws MospException {
		// 基準日付取得(勤務日)
		Date standardDate = dto.getWorkDate();
		// 対象日取得(勤務日)
		Date targetDate = dto.getWorkDate();
		// 勤務日
		dto.setWorkDateString(getStringMonthDay(targetDate));
		// 勤務曜日
		dto.setWorkDayOfWeek(getStringDayOfWeek(targetDate));
		// 曜日配色設定
		dto.setWorkDayOfWeekStyle(getWorkDayOfWeekStyle(targetDate));
		// カレンダ備考設定
		addRemark(dto, getScheduleDateRemark(dto.getWorkDate()));
		// 勤務形態略称
		setWorkTypeAbbr(dto);
		// 始業時間
		dto.setStartTimeString(getStringTime(dto.getStartTime(), standardDate));
		// 終業時間
		dto.setEndTimeString(getStringTime(dto.getEndTime(), standardDate));
		// 勤務時間
		dto.setWorkTimeString(getStringHours(dto.getWorkTime(), true));
		// 休憩時間
		dto.setRestTimeString(getStringHours(dto.getRestTime(), true));
		// 私用外出時間
		dto.setPrivateTimeString(getStringHours(dto.getPrivateTime(), true));
		// 公用外出時間
		dto.setPublicTimeString(getStringHours(dto.getPublicTime(), true));
		// 遅刻時間
		dto.setLateTimeString(getStringHours(dto.getLateTime(), true));
		// 早退時間
		dto.setLeaveEarlyTimeString(getStringHours(dto.getLeaveEarlyTime(), true));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeString(getStringHours(dto.getLateLeaveEarlyTime(), true));
		// 残業時間
		dto.setOvertimeString(getStringHours(dto.getOvertime(), true));
		// 内残時間
		dto.setOvertimeInString(getStringHours(dto.getOvertimeIn(), true));
		// 外残時間
		dto.setOvertimeOutString(getStringHours(dto.getOvertimeOut(), true));
		// 休出時間
		dto.setHolidayWorkTimeString(getStringHours(dto.getHolidayWorkTime(), true));
		// 深夜時間
		dto.setLateNightTimeString(getStringHours(dto.getLateNightTime(), true));
		// 勤務時間合計
		dto.setWorkTimeTotalString(getStringHours(dto.getWorkTimeTotal(), false));
		// 休憩時間合計
		dto.setRestTimeTotalString(getStringHours(dto.getRestTimeTotal(), false));
		// 私用外出時間合計
		dto.setPrivateTimeTotalString(getStringHours(dto.getPrivateTimeTotal(), false));
		// 公用外出時間合計
		dto.setPublicTimeTotalString(getStringHours(dto.getPublicTimeTotal(), false));
		// 遅刻時間
		dto.setLateTimeTotalString(getStringHours(dto.getLateTimeTotal(), false));
		// 早退時間
		dto.setLeaveEarlyTimeTotalString(getStringHours(dto.getLeaveEarlyTimeTotal(), false));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeTotalString(getStringHours(dto.getLateLeaveEarlyTimeTotal(), false));
		// 残業時間
		dto.setOvertimeTotalString(getStringHours(dto.getOvertimeTotal(), false));
		// 内残時間
		dto.setOvertimeInTotalString(getStringHours(dto.getOvertimeInTotal(), false));
		// 外残時間
		dto.setOvertimeOutTotalString(getStringHours(dto.getOvertimeOutTotal(), false));
		// 休出時間
		dto.setHolidayWorkTimeTotalString(getStringHours(dto.getHolidayWorkTimeTotal(), false));
		// 深夜時間
		dto.setLateNightTimeTotalString(getStringHours(dto.getLateNightTimeTotal(), false));
		// 出勤回数
		dto.setWorkDaysString(getStringTimes(dto.getWorkDays()));
		// 遅刻回数
		dto.setLateDaysString(getStringTimes(dto.getLateDays()));
		// 早退回数
		dto.setLeaveEarlyDaysString(getStringTimes(dto.getLeaveEarlyDays()));
		// 残業回数
		dto.setOvertimeDaysString(getStringTimes(dto.getOvertimeDays()));
		// 休出回数
		dto.setHolidayWorkDaysString(getStringTimes(dto.getHolidayWorkDays()));
		// 代休発生回数設定
		dto.setBirthPrescribedSubHolidayString(getStringFloat(dto.getBirthPrescribedSubHoliday()));
		dto.setBirthLegalSubHolidayString(getStringFloat(dto.getBirthLegalSubHoliday()));
		dto.setBirthMidnightSubHolidayString(getStringFloat(dto.getBirthMidnightSubHoliday()));
		if (getStringFloat(dto.getBirthMidnightSubHoliday()).equals(getHyphenNaming())) {
			dto.setBirthMidnightSubHolidayString(null);
		}
		// 深夜回数
		dto.setLateNightDaysString(getStringTimes(dto.getLateNightDays()));
		// 所定休日回数
		dto.setPrescribedHolidaysString(getStringTimes(dto.getPrescribedHolidays()));
		// 法定休日回数
		dto.setLegalHolidaysString(getStringTimes(dto.getLegalHolidays()));
		// 振替休日回数
		dto.setSubstituteHolidaysString(getStringTimes(dto.getSubstituteHolidays()));
		// 有給休暇回数
		dto.setPaidHolidaysString(getStringTimes(dto.getPaidHolidays()));
		// 有給時間
		dto.setPaidHolidayTimeString(getStringTimes(dto.getPaidHolidayTime()));
		// 特別休暇回数
		dto.setSpecialHolidaysString(getStringTimes(dto.getSpecialHolidays()));
		// その他休暇回数
		dto.setOtherHolidaysString(getStringTimes(dto.getOtherHolidays()));
		// 代休回数
		dto.setSubHolidaysString(getStringTimes(dto.getSubHolidays()));
		// 欠勤回数
		dto.setAbsenceDaysString(getStringTimes(dto.getAbsenceDays()));
		// 分単位休暇A時間
		dto.setMinutelyHolidayATimeString(getStringHours(dto.getMinutelyHolidayATimeTotal(), false));
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTimeString(getStringHours(dto.getMinutelyHolidayBTimeTotal(), false));
		// 帳票残業項目タイトル設定
		dto.setOvertimeTitle(mospParams.getName("LeftOut"));
		// 分単位休暇を利用する場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_ADD_USE_MINUTELY_HOLIDAY)) {
			// 分単位休暇A項目タイトル設定
			dto.setMinutelyHolidayATitle(mospParams.getName("MinutelyHolidayAAbbr"));
			// 分単位休暇B項目タイトル設定
			dto.setMinutelyHolidayBTitle(mospParams.getName("MinutelyHolidayBAbbr"));
		}
		// 時間休項目有無設定
		dto.setHourlyPaidHolidayValid(true);
		// 特別休暇項目タイトル設定
		dto.setSpecialHolidaysTitle(mospParams.getName("TotalSpecialLeave"));
		// 分単位休暇全休の場合
		if (dto.getMinutelyHolidayA() == 1 || dto.getMinutelyHolidayB() == 1) {
			// 始業時間
			dto.setStartTimeString(getHyphenNaming());
			// 終業時間
			dto.setEndTimeString(getHyphenNaming());
			// 勤務時間
			dto.setWorkTimeString(getHyphenNaming());
			// 休憩時間
			dto.setRestTimeString(getHyphenNaming());
			// 私用外出時間
			dto.setPrivateTimeString(getHyphenNaming());
			// 公用外出時間
			dto.setPublicTimeString(getHyphenNaming());
			// 遅刻時間
			dto.setLateTimeString(getHyphenNaming());
			// 早退時間
			dto.setLeaveEarlyTimeString(getHyphenNaming());
			// 遅刻早退時間
			dto.setLateLeaveEarlyTimeString(getHyphenNaming());
			// 残業時間
			dto.setOvertimeString(getHyphenNaming());
			// 内残時間
			dto.setOvertimeInString(getHyphenNaming());
			// 外残時間
			dto.setOvertimeOutString(getHyphenNaming());
			// 休出時間
			dto.setHolidayWorkTimeString(getHyphenNaming());
			// 深夜時間
			dto.setLateNightTimeString(getHyphenNaming());
		}
	}
	
	/**
	 * 対象日勤務曜日のスタイル文字列を取得する。<br>
	 * @param targetDate 対象日
	 * @return 対象日勤務曜日のスタイル文字列
	 */
	protected String getWorkDayOfWeekStyle(Date targetDate) {
		// 祝日判定
		if (holidayBean.isHoliday(targetDate)) {
			return STYLE_RED;
		}
		// 土曜日判定
		if (DateUtility.isSaturday(targetDate)) {
			return STYLE_BLUE;
		}
		// 日曜日判定
		if (DateUtility.isSunday(targetDate)) {
			return STYLE_RED;
		}
		return "";
	}
	
	/**
	 * 勤務形態略称を設定する。<br>
	 * @param dto 勤怠一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeAbbr(AttendanceListDto dto) throws MospException {
		// 勤務形態確認
		if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
			// 何もしない
			return;
		}
		// 略称取得
		String workTypeAbbr = difference.getDifferenceAbbr(dto.getWorkTypeCode());
		String workTypeAbbrInitial = workTypeAbbr.substring(workTypeAbbr.length() - 1);
		if (dto.getWorkTypeCode().equals(workTypeAbbr)) {
			// 時差出勤でない場合
			workTypeAbbr = getWorkTypeEntity(dto.getWorkTypeCode()).getWorkTypeAbbr();
			if (workTypeAbbr.isEmpty() == false) {
				workTypeAbbrInitial = workTypeAbbr.substring(0, 1);
			}
		}
		if (dto.getWorkTypeAnteAbbr() != null && !dto.getWorkTypeAnteAbbr().isEmpty()) {
			if (getHalfSubstituteWorkAbbrNaming().equals(dto.getWorkTypeAnteAbbr())) {
				// 振替出勤の場合
				dto.setWorkTypeAbbr(getHalfSubstituteWorkAbbrNaming());
				return;
			} else if (getSubstituteHolidayAbbrNaming().equals(dto.getWorkTypeAnteAbbr())) {
				// 振替休日の場合
				dto.setWorkTypeAbbr(getHalfSubstituteHolidayAbbrNaming());
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(dto.getWorkTypeAnteAbbr().substring(0, 1));
			sb.append(getSlashNaming());
			sb.append(workTypeAbbrInitial);
			dto.setWorkTypeAbbr(sb.toString());
			return;
		}
		if (dto.getWorkTypePostAbbr() != null && !dto.getWorkTypePostAbbr().isEmpty()) {
			if (getHalfSubstituteWorkAbbrNaming().equals(dto.getWorkTypePostAbbr())) {
				// 振替出勤の場合
				dto.setWorkTypeAbbr(getHalfSubstituteWorkAbbrNaming());
				return;
			} else if (getSubstituteHolidayAbbrNaming().equals(dto.getWorkTypePostAbbr())) {
				// 振替休日の場合
				dto.setWorkTypeAbbr(getHalfSubstituteHolidayAbbrNaming());
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(workTypeAbbrInitial);
			sb.append(getSlashNaming());
			sb.append(dto.getWorkTypePostAbbr().substring(0, 1));
			dto.setWorkTypeAbbr(sb.toString());
			return;
		}
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(workTypeAbbr);
	}
	
	/**
	 * 対象日のカレンダ日情報の備考を取得する。<br>
	 * @param targetDate 対象日
	 * @return カレンダ日情報の備考
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduleDateRemark(Date targetDate) throws MospException {
		// 対象日のカレンダ日情報を取得
		ScheduleDateDtoInterface dto = getScheduleDateDto(targetDate);
		// 対象日のカレンダ日情報を確認
		if (dto == null) {
			return "";
		}
		return dto.getRemark();
	}
	
	/**
	 * 予定始業時刻を取得する。<br>
	 * <br>
	 * 勤務形態エンティティ及び申請エンティティを用いて、始業時刻を取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getWorkTypeStartTime(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode());
		// 申請エンティティを取得
		RequestEntity requestEntity = getRequestEntity(personalId, dto.getWorkDate());
		// 始業時刻を取得
		return workTypeEntity.getStartTime(requestEntity);
	}
	
	/**
	 * 予定終業時刻を取得する。<br>
	 * <br>
	 * 勤務形態エンティティ及び申請エンティティを用いて、終業時刻を取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getWorkTypeEndTime(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode());
		// 申請エンティティを取得
		RequestEntity requestEntity = getRequestEntity(personalId, dto.getWorkDate());
		// 終業時刻を取得
		return workTypeEntity.getEndTime(requestEntity);
	}
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 有効日は、勤怠一覧表示期間の最終日として取得する。<br>
	 * 新規に取得した勤務形態エンティティは、勤務形態エンティティ群に設定する。<br>
	 * <br>
	 * @param workTypeCode 対象勤務形態コード
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkTypeEntity getWorkTypeEntity(String workTypeCode) throws MospException {
		// 勤務形態エンティティ群から勤務形態エンティティを取得
		WorkTypeEntity workTypeEntity = workTypeEntityMap.get(workTypeCode);
		// 勤務形態エンティティ確認
		if (workTypeEntity != null) {
			return workTypeEntity;
		}
		// 勤務形態エンティティ再取得
		workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, lastDate);
		// 勤務形態エンティティ群に設定
		workTypeEntityMap.put(workTypeCode, workTypeEntity);
		return workTypeEntity;
	}
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 勤怠一覧情報に設定されている勤務形態コードから、勤務形態エンティティを取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkTypeEntity getWorkTypeEntity(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		return getWorkTypeEntity(dto.getWorkTypeCode());
	}
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * 取得した各種申請を基に、対象日の申請を抽出する。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected RequestEntity getRequestEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティ準備
		RequestEntity requestEntity = new RequestEntity(personalId, targetDate);
		// 対象申請情報リスト準備
		AttendanceDtoInterface targetAttendanceDto = null;
		List<HolidayRequestDtoInterface> targetHolidayRequestList = new ArrayList<HolidayRequestDtoInterface>();
		List<SubHolidayRequestDtoInterface> targetSubHolidayRequestList = new ArrayList<SubHolidayRequestDtoInterface>();
		List<SubstituteDtoInterface> targetSubstituteList = new ArrayList<SubstituteDtoInterface>();
		List<OvertimeRequestDtoInterface> targetOvertimeRequestList = new ArrayList<OvertimeRequestDtoInterface>();
		WorkOnHolidayRequestDtoInterface targetWorkOnHolidayRequestDto = null;
		DifferenceRequestDtoInterface targetDifferenceRequestDto = null;
		WorkTypeChangeRequestDtoInterface targetWorkTypeChangeRequestDto = null;
		// 勤怠申請情報選別
		targetAttendanceDto = getAttendanceDtoListDto(targetDate);
		// 休暇申請情報選別
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// 対象日が休暇申請開始日～終了日に含まれる場合
			if (DateUtility.isTermContain(targetDate, dto.getRequestStartDate(), dto.getRequestEndDate())) {
				// 対象休暇申請情報リストに追加
				targetHolidayRequestList.add(dto);
			}
		}
		// 代休申請情報選別
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// 対象日が代休日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象代休申請情報リストに追加
				targetSubHolidayRequestList.add(dto);
			}
		}
		// 振替休日情報選別
		for (SubstituteDtoInterface dto : substituteList) {
			// 対象日が振替日である場合
			if (targetDate.compareTo(dto.getSubstituteDate()) == 0) {
				// 対象振替休日情報リストに追加
				targetSubstituteList.add(dto);
			}
		}
		// 残業申請情報選別
		for (OvertimeRequestDtoInterface dto : targetOvertimeRequestList) {
			// 対象日が残業年月日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象残業申請情報リストに追加
				targetOvertimeRequestList.add(dto);
			}
		}
		// 振出・休出申請情報選別
		for (WorkOnHolidayRequestDtoInterface dto : workOnHolidayRequestList) {
			// 対象日が出勤日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象振替休日情報リストに追加
				targetWorkOnHolidayRequestDto = dto;
				break;
			}
		}
		// 時差出勤申請情報選別
		for (DifferenceRequestDtoInterface dto : differenceRequestList) {
			// 対象日が申請日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象時差出勤申請情報を取得
				targetDifferenceRequestDto = dto;
				break;
			}
		}
		// 勤務形態変更申請情報選別
		for (WorkTypeChangeRequestDtoInterface dto : workTypeChangeRequestList) {
			// 対象日が出勤日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象勤務形態変更申請情報を取得
				targetWorkTypeChangeRequestDto = dto;
				break;
			}
		}
		// 対象申請情報リスト設定
		requestEntity.setAttendanceDto(targetAttendanceDto);
		requestEntity.setHolidayRequestList(targetHolidayRequestList);
		requestEntity.setSubHolidayRequestList(targetSubHolidayRequestList);
		requestEntity.setSubstituteList(targetSubstituteList);
		requestEntity.setOverTimeRequestList(targetOvertimeRequestList);
		requestEntity.setWorkOnHolidayRequestDto(targetWorkOnHolidayRequestDto);
		requestEntity.setDifferenceRequestDto(targetDifferenceRequestDto);
		requestEntity.setWorkTypeChangeRequestDto(targetWorkTypeChangeRequestDto);
		// ワークフロー情報群取得
		if (targetAttendanceDto != null) {
			getWorkflow(targetAttendanceDto.getWorkflow());
		}
		for (HolidayRequestDtoInterface dto : targetHolidayRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		for (SubHolidayRequestDtoInterface dto : targetSubHolidayRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		for (SubstituteDtoInterface dto : targetSubstituteList) {
			getWorkflow(dto.getWorkflow());
		}
		for (OvertimeRequestDtoInterface dto : targetOvertimeRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		if (targetDifferenceRequestDto != null) {
			getWorkflow(targetDifferenceRequestDto.getWorkflow());
		}
		if (targetWorkTypeChangeRequestDto != null) {
			getWorkflow(targetWorkTypeChangeRequestDto.getWorkflow());
		}
		// ワークフロー情報群設定
		requestEntity.setWorkflowMap(workflowMap);
		return requestEntity;
	}
	
	/**
	 * 勤怠一覧情報に備考を追加する。<br>
	 * @param dto    追加対象勤怠一覧情報
	 * @param remark 追加備考
	 */
	protected void addRemark(AttendanceListDto dto, String remark) {
		// 追加備考確認
		if (remark == null || remark.isEmpty()) {
			return;
		}
		// 追加備考重複確認
		if (dto.getRemark() != null && dto.getRemark().indexOf(remark) >= 0) {
			// 追加する備考が既に設定されている場合
			return;
		}
		// 勤怠一覧情報の備考に追加
		dto.setRemark(MospUtility.concat(dto.getRemark(), remark));
	}
	
	/**
	 * 回数を取得する。<br>
	 * 時間がnull、或いは0の場合は0を返す。<br>
	 * それ以外の場合は1を返す。<br>
	 * @param minutes 時間(分)
	 * @return 回数
	 */
	protected int countHours(Integer minutes) {
		// 時間確認
		if (minutes == null || minutes.intValue() == 0) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 時間文字列を取得する(Integer→String)。<br>
	 * 時間を文字列(小数点以下2桁)で表す。<br>
	 * 小数点以下2桁は、分を表す。<br>
	 * @param minutes    対象時間(分)
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(0.00)
	 */
	protected String getStringHours(Integer minutes, boolean needHyphen) {
		// 時間確認
		if (minutes == null) {
			// 時間がnullならハイフン
			return getHyphenNaming();
		}
		if (needHyphen && minutes.intValue() == 0) {
			// 時間が0ならハイフン
			return getHyphenNaming();
		}
		// 時間文字列準備
		StringBuffer sb = new StringBuffer();
		// 時間
		sb.append(minutes.intValue() / TimeConst.CODE_DEFINITION_HOUR);
		// 区切文字
		sb.append(SEPARATOR_HOURS);
		// 分
		int remainder = minutes.intValue() % TimeConst.CODE_DEFINITION_HOUR;
		// 数値フォーマットクラス準備
		NumberFormat nf = NumberFormat.getNumberInstance();
		// 丸め方法指定(切捨)
		nf.setRoundingMode(RoundingMode.DOWN);
		// 桁数指定
		nf.setMinimumIntegerDigits(HOURS_DIGITS);
		sb.append(nf.format(remainder));
		// 時間文字列取得
		return sb.toString();
	}
	
	/**
	 * Float→String変換。
	 * @param value 値
	 * @return 文字列
	 */
	protected String getStringFloat(Float value) {
		// 値がない場合
		if (value == null) {
			// ハイフン
			return getHyphenNaming();
		}
		return String.valueOf(value);
	}
	
	/**
	 * 回数文字列を取得する(Integer→String)。<br>
	 * 回数を文字列で表す。<br>
	 * @param times 対象回数
	 * @return 回数文字列
	 */
	protected String getStringTimes(Integer times) {
		// 回数確認
		if (times == null) {
			// 回数がnullならハイフン
			return getHyphenNaming();
		}
		// 回数文字列取得
		return times.toString();
	}
	
	/**
	 * 回数文字列を取得する(Float→String)。<br>
	 * 回数を文字列(小数点以下1桁)で表す。<br>
	 * @param times 対象回数
	 * @return 回数文字列
	 */
	protected String getStringTimes(Float times) {
		// 回数確認
		if (times == null) {
			// 回数がnullならハイフン
			return getHyphenNaming();
		}
		DecimalFormat df = new DecimalFormat("#.#");
		// 日付文字列取得
		return df.format(times);
	}
	
	/**
	 * 勤務予定時間表示要否を取得する。<br>
	 * 勤怠設定情報から取得し、勤怠一覧画面等で利用される。<br>
	 * @return 勤務予定時間表示要否(true：要、false：否)
	 */
	protected boolean useScheduledTime() {
		return timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * 週の起算曜日(勤怠設定)から週期間を算出し、設定する。<br>
	 * ポータル画面等の期間で勤怠情報を取得する場合は、この週期間を利用する。<br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWeekTerm(Date targetDate) throws MospException {
		// 週の起算曜日を取得
		int startWeek = timeSettingDto.getStartWeek();
		// 曜日確認
		while (!DateUtility.isDayOfWeek(targetDate, startWeek)) {
			// 対象日を減算
			targetDate = addDay(targetDate, -1);
		}
		// 期間初日設定
		firstDate = getDateClone(targetDate);
		// 対象日を加算
		targetDate = addDay(targetDate, 1);
		// 曜日確認
		while (!DateUtility.isDayOfWeek(targetDate, startWeek)) {
			// 対象日を加算
			targetDate = addDay(targetDate, 1);
		}
		// 期間最終日設定
		lastDate = addDay(targetDate, -1);
	}
	
	/**
	 * ワークフロー情報を取得する。<br>
	 * ワークフロー情報群から取得し、無い場合はDBから取得する。<br>
	 * @param workflowNo ワークフロー番号
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkflowDtoInterface getWorkflow(long workflowNo) throws MospException {
		// ワークフロー情報群からワークフロー情報を取得
		WorkflowDtoInterface dto = workflowMap.get(workflowNo);
		// ワークフロー情報が有る場合
		if (dto != null) {
			return dto;
		}
		// DBからワークフロー情報を取得
		dto = workflow.getLatestWorkflowInfo(workflowNo);
		if (dto != null) {
			workflowMap.put(workflowNo, dto);
		}
		return dto;
	}
	
	/**
	 * 月日を取得する。<br>
	 * @param date 日付
	 * @return 月日
	 */
	protected String getStringMonthDay(Date date) {
		return DateUtility.getStringDate(date, "MM/dd");
	}
	
	/**
	 * 曜日を取得する。<br>
	 * @param date 日付
	 * @return 曜日
	 */
	protected String getStringDayOfWeek(Date date) {
		return DateUtility.getStringDayOfWeek(date);
	}
	
	/**
	 * 所定代休略称を取得する。<br>
	 * @return 所定代休略称
	 */
	protected String getPrescribedSubHolidayAbbrNaming() {
		return mospParams.getName("PrescribedAbbreviation", "CompensatoryHoliday");
	}
	
	/**
	 * 法定代休略称を取得する。<br>
	 * @return 法定代休略称
	 */
	protected String getLegalSubHolidayAbbrNaming() {
		return mospParams.getName("LegalAbbreviation", "CompensatoryHoliday");
	}
	
	/**
	 * 所定振替休日略称を取得する。<br>
	 * @return 所定振替休日略称
	 */
	protected String getPrescribedSubstituteAbbrNaming() {
		return mospParams.getName("PrescribedAbbreviation", "ClosedVibration");
	}
	
	/**
	 * 法定振替休日略称を取得する。<br>
	 * @return 法定振替休日略称
	 */
	protected String getLegalSubstituteAbbrNaming() {
		return mospParams.getName("LegalAbbreviation", "ClosedVibration");
	}
	
	/**
	 * 予定名称を取得する。<br>
	 * @return 予定名称
	 */
	protected String getScheduleNaming() {
		return mospParams.getName("Schedule");
	}
	
	/**
	 * 承認済名称を取得する。<br>
	 * @return 承認済名称
	 */
	protected String getApplovedNaming() {
		return mospParams.getName("Approval", "Finish");
	}
	
	/**
	 * 直行名称を取得する。<br>
	 * @return 直行名称
	 */
	protected String getDirectStartNaming() {
		return mospParams.getName("DirectStart");
	}
	
	/**
	 * 直帰名称を取得する。<br>
	 * @return 直帰名称
	 */
	protected String getDirectEndNaming() {
		return mospParams.getName("DirectEnd");
	}
	
	/**
	 * 有給休暇略称を取得する。<br>
	 * @return 有給休暇略称
	 */
	protected String getPaidHolidayAbbrNaming() {
		return mospParams.getName("PaidHolidayAbbr");
	}
	
	/**
	 * ストック休暇略称を取得する。<br>
	 * @return ストック休暇略称
	 */
	protected String getStockHolidayAbbrNaming() {
		return mospParams.getName("StockHolidayAbbr");
	}
	
	/**
	 * 休暇名称を取得する。<br>
	 * @return 休暇名称
	 */
	protected String getHolidayNaming() {
		return mospParams.getName("Vacation");
	}
	
	/**
	 * 休日出勤略称を取得する。<br>
	 * @return 休日出勤略称
	 */
	protected String getWorkOnHolidayAbbrNaming() {
		return mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 振替出勤略称を取得する。<br>
	 * @return 振替出勤略称
	 */
	protected String getSubstituteWorkAbbrNaming() {
		return mospParams.getName("SubstituteAbbr", "GoingWorkAbbr");
	}
	
	/**
	 * 振替休日略称を取得する。<br>
	 * @return 振替休日略称
	 */
	protected String getSubstituteAbbrNaming() {
		return mospParams.getName("SubstituteAbbr");
	}
	
	/**
	 * 振替休日略称を取得する。<br>
	 * @return 振替休日略称
	 */
	protected String getSubstituteHolidayAbbrNaming() {
		return mospParams.getName("ClosedVibration");
	}
	
	/**
	 * 半日振替休日略称を取得する。<br>
	 * @return 半日振替休日略称
	 */
	protected String getHalfSubstituteHolidayAbbrNaming() {
		return mospParams.getName("HalfSubstituteHolidayAbbr");
	}
	
	/**
	 * 午前振替休日略称を取得する。<br>
	 * @return 午前振替休日略称
	 */
	protected String getAnteMeridiemSubstituteHolidayAbbrNaming() {
		return mospParams.getName("AnteMeridiemSubstituteHolidayAbbr");
	}
	
	/**
	 * 午後振替休日略称を取得する。<br>
	 * @return 午後振替休日略称
	 */
	protected String getPostMeridiemSubstituteHolidayAbbrNaming() {
		return mospParams.getName("PostMeridiemSubstituteHolidayAbbr");
	}
	
	/**
	 * 半日振替出勤略称を取得する。<br>
	 * @return 半日振替出勤略称
	 */
	protected String getHalfSubstituteWorkAbbrNaming() {
		return mospParams.getName("HalfSubstituteWorkAbbr");
	}
	
	/**
	 * 代休略称を取得する。<br>
	 * @return 代休略称
	 */
	protected String getSubHolidayAbbrNaming() {
		return mospParams.getName("Generation");
	}
	
	/**
	 * 残業略称を取得する。<br>
	 * @return 残業略称
	 */
	protected String getOvertimeAbbrNaming() {
		return mospParams.getName("OvertimeAbbr");
	}
	
	/**
	 * 勤務形態変更略称を取得する。<br>
	 * @return 勤務形態略称
	 */
	protected String getWorkTypeChangeAbbrNaming() {
		return mospParams.getName("WorkTypeChangeAbbr");
	}
	
	/**
	 * 時差出勤略称を取得する。<br>
	 * @return 時差出勤略称
	 */
	protected String getTimeDefferenceAbbrNaming() {
		return mospParams.getName("TimeDefferenceAbbr");
	}
	
	/**
	 * 休職名称を取得する。<br>
	 * @return 休職名称
	 */
	protected String getAdministrativeLeaveNaming() {
		return mospParams.getName("RetirementLeave");
	}
	
	/**
	 * 承認済略称を取得する。<br>
	 * @return 承認済略称
	 */
	protected String getCompleteApprovalAbbrNaming() {
		return mospParams.getName("Finish");
	}
	
	/**
	 * 差戻略称を取得する。<br>
	 * @return 差戻略称
	 */
	protected String getRevertAbbrNaming() {
		return mospParams.getName("Back");
	}
	
	/**
	 * 申請略称を取得する。<br>
	 * @return 申請略称
	 */
	protected String getApprovalAbbrNaming() {
		return mospParams.getName("Register");
	}
	
	/**
	 * 下書略称を取得する。<br>
	 * @return 下書略称
	 */
	protected String getDraftAbbrNaming() {
		return mospParams.getName("Under");
	}
	
	/**
	 * 本人修正略称を取得する。<br>
	 * @return 本人修正略称
	 */
	protected String getSelfCorrectAbbrNaming() {
		return mospParams.getName("CorrectionHistory");
	}
	
	/**
	 * 他人修正略称を取得する。<br>
	 * @return 他人修正略称
	 */
	protected String getOtherCorrectAbbrNaming() {
		return mospParams.getName("Other");
	}
	
	/**
	 * スラッシュを取得する。<br>
	 * @return スラッシュ
	 */
	protected String getSlashNaming() {
		return mospParams.getName("Slash");
	}
	
	/**
	 * 予定確認や予定簿の表示について、<br>
	 * 申請(承認済)によって勤務時間の表示を決める。<br>
	 * @throws MospException 例外が発生した時
	 */
	protected void setApprovalTime() throws MospException {
		// 勤怠一覧情報毎に処理
		attendanceList: for (AttendanceListDto dto : attendanceList) {
			boolean amHoliday = false;
			boolean pmHoliday = false;
			WorkTypeChangeRequestDtoInterface workTypeChangeDto = null;
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = null;
			// 休暇申請情報確認
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				// 休暇日確認
				if (!DateUtility.isTermContain(dto.getWorkDate(), holidayRequestDto.getRequestStartDate(),
						holidayRequestDto.getRequestEndDate())) {
					continue;
				}
				if (!workflow.isCompleted(holidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休暇範囲確認
				if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 代休申請情報確認
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休日確認
				if (!subHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(subHolidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休暇範囲確認
				if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 振替休日情報確認
			for (SubstituteDtoInterface substituteDto : substituteList) {
				// 振替日確認
				if (!substituteDto.getSubstituteDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(substituteDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休日範囲確認
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 振出・休出申請情報確認
			for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
				// 出勤日確認
				if (!workOnHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 振替申請確認
				if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
					// 振替出勤(午前)の場合
					pmHoliday = true;
				} else if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
					// 振替出勤(午後)の場合
					amHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
				workOnHolidayDto = workOnHolidayRequestDto;
			}
			// 勤務形態変更申請情報確認
			for (WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto : workTypeChangeRequestList) {
				// 出勤日確認
				if (!workTypeChangeRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(workTypeChangeRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				workTypeChangeDto = workTypeChangeRequestDto;
			}
			if (workTypeChangeDto != null) {
				// 勤務形態変更申請が承認済の場合
				dto.setStartTime(null);
				dto.setEndTime(null);
				setDtoFields(dto, workTypeChangeDto.getWorkTypeCode(), true, true, amHoliday, pmHoliday);
				continue;
			}
			if (workOnHolidayDto != null) {
				// 振出・休出申請が承認済の場合
				setDtoFields(dto, getWorkOnHolidayWorkType(workOnHolidayDto), true, true, amHoliday, pmHoliday);
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 休日出勤の場合
					// 始業時間
					Date startTime = getWorkTypeStartTime(dto);
					// 終業時間
					Date endTime = getWorkTypeEndTime(dto);
					// 勤務時間
					dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
				}
				continue;
			}
			if (amHoliday || pmHoliday) {
				// 半休の場合
				Date startTime = getWorkTypeStartTime(dto);
				Date endTime = getWorkTypeEndTime(dto);
				// 半休時間確認
				if (startTime == null || endTime == null) {
					continue;
				}
				// 始業時間
				dto.setStartTime(startTime);
				// 終業時間
				dto.setEndTime(endTime);
				// 休憩時間を消去
				dto.setRestTime(null);
				// 勤務時間
				dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
			}
		}
	}
	
	/**
	 * 状態設定。<br>
	 * @param dto 設定対象勤怠一覧情報
	 * @param anteDto 前半休ワークフロー情報
	 * @param postDto 後半休ワークフロー情報
	 */
	protected void setApplicationInfo(AttendanceListDto dto, WorkflowDtoInterface anteDto, WorkflowDtoInterface postDto) {
		if (anteDto == null || postDto == null) {
			return;
		}
		int anteStatus = Integer.parseInt(anteDto.getWorkflowStatus());
		int postStatus = Integer.parseInt(postDto.getWorkflowStatus());
		if (anteStatus > postStatus) {
			dto.setApplicationInfo(workflow.getWorkflowStatus(postDto.getWorkflowStatus(), postDto.getWorkflowStage()));
			return;
		} else if (anteStatus < postStatus) {
			dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
			return;
		}
		if (PlatformConst.CODE_STATUS_DRAFT.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_APPLY.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_CANCEL.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_COMPLETE.equals(anteDto.getWorkflowStatus())) {
			// 下書・未承認・承解除・承認済の場合
			dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
		} else if (PlatformConst.CODE_STATUS_APPROVED.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_REVERT.equals(anteDto.getWorkflowStatus())) {
			// 承認・差戻の場合
			if (anteDto.getWorkflowStage() <= postDto.getWorkflowStage()) {
				dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(),
						anteDto.getWorkflowStage()));
				return;
			}
			dto.setApplicationInfo(workflow.getWorkflowStatus(postDto.getWorkflowStatus(), postDto.getWorkflowStage()));
		}
	}
	
	/**
	 * 全休判断。<br>
	 * @param dto 対象DTO
	 * @return 全休の場合true、そうでない場合false
	 */
	protected boolean isAll(SubstituteDtoInterface dto) {
		return isAll(dto.getHolidayRange());
	}
	
	/**
	 * 全休判断。<br>
	 * @param range 休暇範囲
	 * @return 全休の場合true、そうでない場合false
	 */
	protected boolean isAll(int range) {
		return range == TimeConst.CODE_HOLIDAY_RANGE_ALL;
	}
	
	/**
	 * 予定一覧用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setScheduleExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 実績一覧用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setActualExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧(週)用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setWeeklyAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠承認一覧用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setApprovalAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧データ用の追加処理を行う。<br>
	 * アドオンでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setAttendanceListDtoExtraInfo() throws MospException {
		// 処理無し
	}
	
}
