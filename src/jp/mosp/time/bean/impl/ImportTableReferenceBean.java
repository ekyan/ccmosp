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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.file.ImportDaoInterface;
import jp.mosp.platform.dao.file.ImportFieldDaoInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ImportTableReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dao.settings.TimelyPaidHolidayDaoInterface;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdAttendanceDao;
import jp.mosp.time.dao.settings.impl.TmdHolidayDataDao;
import jp.mosp.time.dao.settings.impl.TmdPaidHolidayDao;
import jp.mosp.time.dao.settings.impl.TmdStockHolidayDao;
import jp.mosp.time.dao.settings.impl.TmdTimelyPaidHolidayDao;
import jp.mosp.time.dao.settings.impl.TmdTotalTimeDao;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TimelyPaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAttendanceDto;
import jp.mosp.time.dto.settings.impl.TmdHolidayDataDto;
import jp.mosp.time.dto.settings.impl.TmdPaidHolidayDataDto;
import jp.mosp.time.dto.settings.impl.TmdStockHolidayDto;
import jp.mosp.time.dto.settings.impl.TmdTimelyPaidHolidayDto;
import jp.mosp.time.dto.settings.impl.TmdTotalTimeDataDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * インポートテーブル参照クラス。
 */
public class ImportTableReferenceBean extends PlatformBean implements ImportTableReferenceBeanInterface {
	
	/**
	 * 勤怠コメント最大文字数。<br>
	 */
	public static final int								MAX_LENGTH_TIME_COMMENT	= 50;
	
	/**
	 * エクスポートマスタDAO。
	 */
	private ImportDaoInterface							importDao;
	
	/**
	 * エクスポートフィールドマスタDAO。
	 */
	private ImportFieldDaoInterface						importFieldDao;
	
	/**
	 * 人事マスタDAO。
	 */
	private HumanDaoInterface							humanDao;
	
	/**
	 * 勤怠データDAO。
	 */
	private AttendanceDaoInterface						attendanceDao;
	
	/**
	 * 勤怠集計データDAO。
	 */
	private TotalTimeDataDaoInterface					totalTimeDataDao;
	
	/**
	 * 休暇種別マスタDAO。
	 */
	private HolidayDaoInterface							holidayDao;
	
	/**
	 * 休暇データDAO。
	 */
	private HolidayDataDaoInterface						holidayDataDao;
	
	/**
	 * 有給休暇データDAO。
	 */
	private PaidHolidayDataDaoInterface					paidHolidayDataDao;
	
	/**
	 * ストック休暇データDAO。
	 */
	private StockHolidayDataDaoInterface				stockHolidayDataDao;
	
	/**
	 * 時間単位有給休暇データDAO。
	 */
	private TimelyPaidHolidayDaoInterface				timelyPaidHolidayDao;
	
	/**
	 * 勤務形態マスタDAO。
	 */
	private WorkTypeDaoInterface						workTypeDao;
	
	/**
	 * 勤怠設定管理DAO。
	 */
	private TimeSettingDaoInterface						timeSettingDao;
	
	/**
	 * 締日管理DAO。
	 */
	private CutoffDaoInterface							cutoffDao;
	
	/**
	 * ワークフローDAO。
	 */
	private WorkflowDaoInterface						workflowDao;
	
	/**
	 * 設定適用参照。
	 */
	private ApplicationReferenceBeanInterface			application;
	
	/**
	 * 勤怠集計管理参照。
	 */
	private TotalTimeTransactionReferenceBeanInterface	totalTimeTransaction;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ImportTableReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public ImportTableReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		importDao = (ImportDaoInterface)createDao(ImportDaoInterface.class);
		importFieldDao = (ImportFieldDaoInterface)createDao(ImportFieldDaoInterface.class);
		humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		totalTimeDataDao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
		holidayDao = (HolidayDaoInterface)createDao(HolidayDaoInterface.class);
		holidayDataDao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		paidHolidayDataDao = (PaidHolidayDataDaoInterface)createDao(PaidHolidayDataDaoInterface.class);
		stockHolidayDataDao = (StockHolidayDataDaoInterface)createDao(StockHolidayDataDaoInterface.class);
		timelyPaidHolidayDao = (TimelyPaidHolidayDaoInterface)createDao(TimelyPaidHolidayDaoInterface.class);
		workTypeDao = (WorkTypeDaoInterface)createDao(WorkTypeDaoInterface.class);
		timeSettingDao = (TimeSettingDaoInterface)createDao(TimeSettingDaoInterface.class);
		cutoffDao = (CutoffDaoInterface)createDao(CutoffDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
		application = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		totalTimeTransaction = (TotalTimeTransactionReferenceBeanInterface)createBean(TotalTimeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public List<String[]> getTemplate(String importCode) throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<String[]> list = new ArrayList<String[]>();
		if (importDto.getHeader() == 1) {
			// ヘッダが有りの場合
			List<String> headerList = new ArrayList<String>();
			for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
				headerList.add(mospParams.getProperties().getCodeItemName(importDto.getImportTable(),
						importFieldDto.getFieldName()));
			}
			list.add(headerList.toArray(new String[0]));
		}
		return list;
	}
	
	@Override
	public List<AttendanceDtoInterface> getAttendanceList(String importCode, List<String[]> list) throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<AttendanceDtoInterface> attendanceList = new ArrayList<AttendanceDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有りの場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				// 初期化
				AttendanceDtoInterface dto = new TmdAttendanceDto();
				dto.setTimesWork(1);
				dto.setLateReason("");
				dto.setLateCertificate("");
				dto.setLateComment("");
				dto.setLeaveEarlyReason("");
				dto.setLeaveEarlyCertificate("");
				dto.setLeaveEarlyComment("");
				dto.setTimeComment("");
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (fieldName.equals(PfmHumanDao.COL_EMPLOYEE_CODE)) {
							// 社員コード
							employeeCode = value;
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_DATE)) {
							// 勤務日
							Date workDate = getDate(value);
							if (workDate == null) {
								hasError = true;
								break;
							}
							dto.setWorkDate(workDate);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIMES_WORK)) {
							// 勤務回数
							int timesWork = 0;
							try {
								timesWork = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
//							if (timesWork <= 0) {
							if (timesWork != 1) {
								hasError = true;
								break;
							}
							dto.setTimesWork(timesWork);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_TYPE_CODE)) {
							// 勤務形態コード
							dto.setWorkTypeCode(value);
						} else if (fieldName.equals(TmdAttendanceDao.COL_DIRECT_START)) {
							// 直行
							int directStart = 0;
							try {
								directStart = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (directStart != 0 && directStart != 1) {
								hasError = true;
								break;
							}
							dto.setDirectStart(directStart);
						} else if (fieldName.equals(TmdAttendanceDao.COL_DIRECT_END)) {
							// 直帰
							int directEnd = 0;
							try {
								directEnd = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (directEnd != 0 && directEnd != 1) {
								hasError = true;
								break;
							}
							dto.setDirectEnd(directEnd);
						} else if (fieldName.equals(TmdAttendanceDao.COL_START_TIME)) {
							// 始業時刻
							dto.setStartTime(getTimestamp(value));
						} else if (fieldName.equals(TmdAttendanceDao.COL_ACTUAL_START_TIME)) {
							// 実始業時刻
							dto.setActualStartTime(getTimestamp(value));
						} else if (fieldName.equals(TmdAttendanceDao.COL_END_TIME)) {
							// 終業時刻
							dto.setEndTime(getTimestamp(value));
						} else if (fieldName.equals(TmdAttendanceDao.COL_ACTUAL_END_TIME)) {
							// 実終業時刻
							dto.setActualEndTime(getTimestamp(value));
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_DAYS)) {
							// 遅刻日数
							int lateDays = 0;
							try {
								lateDays = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateDays < 0) {
								hasError = true;
								break;
							}
							dto.setLateDays(lateDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_THIRTY_MINUTES_OR_MORE)) {
							// 遅刻30分以上日数
							int lateThirtyMinutesOrMore = 0;
							try {
								lateThirtyMinutesOrMore = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateThirtyMinutesOrMore < 0) {
								hasError = true;
								break;
							}
							dto.setLateThirtyMinutesOrMore(lateThirtyMinutesOrMore);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_LESS_THAN_THIRTY_MINUTES)) {
							// 遅刻30分未満日数
							int lateLessThanThirtyMinutes = 0;
							try {
								lateLessThanThirtyMinutes = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateLessThanThirtyMinutes < 0) {
								hasError = true;
								break;
							}
							dto.setLateLessThanThirtyMinutes(lateLessThanThirtyMinutes);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_TIME)) {
							// 遅刻時間
							int lateTime = 0;
							try {
								lateTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateTime(lateTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_THIRTY_MINUTES_OR_MORE_TIME)) {
							// 遅刻30分以上時間
							int lateThirtyMinutesOrMoreTime = 0;
							try {
								lateThirtyMinutesOrMoreTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateThirtyMinutesOrMoreTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateThirtyMinutesOrMoreTime(lateThirtyMinutesOrMoreTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME)) {
							// 遅刻30分未満時間
							int lateLessThanThirtyMinutesTime = 0;
							try {
								lateLessThanThirtyMinutesTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateLessThanThirtyMinutesTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateLessThanThirtyMinutesTime(lateLessThanThirtyMinutesTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_REASON)) {
							// 遅刻理由
							if (value.isEmpty()) {
								dto.setLateReason("");
							} else if (TimeConst.CODE_TARDINESS_WHY_INDIVIDU.equals(value)
									|| TimeConst.CODE_TARDINESS_WHY_BAD_HEALTH.equals(value)
									|| TimeConst.CODE_TARDINESS_WHY_OTHERS.equals(value)
									|| TimeConst.CODE_TARDINESS_WHY_TRAIN.equals(value)
									|| TimeConst.CODE_TARDINESS_WHY_COMPANY.equals(value)) {
								// 個人都合、体調不良、その他、電車遅延又は会社指示の場合
								dto.setLateReason(value);
							} else {
								hasError = true;
								break;
							}
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_CERTIFICATE)) {
							// 遅刻証明書
							if (value.isEmpty()) {
								dto.setLateCertificate("");
							} else if ("0".equals(value) || "1".equals(value)) {
								dto.setLateCertificate(value);
							} else {
								hasError = true;
								break;
							}
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_COMMENT)) {
							// 遅刻コメント
							if (!checkMaxLength(value, MAX_LENGTH_TIME_COMMENT)) {
								hasError = true;
								break;
							}
							dto.setLateComment(value);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_DAYS)) {
							// 早退日数
							int leaveEarlyDays = 0;
							try {
								leaveEarlyDays = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyDays < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyDays(leaveEarlyDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE)) {
							// 早退30分以上日数
							int leaveEarlyThirtyMinutesOrMore = 0;
							try {
								leaveEarlyThirtyMinutesOrMore = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyThirtyMinutesOrMore < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyThirtyMinutesOrMore(leaveEarlyThirtyMinutesOrMore);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES)) {
							// 早退30分未満日数
							int leaveEarlyLessThanThirtyMinutes = 0;
							try {
								leaveEarlyLessThanThirtyMinutes = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyLessThanThirtyMinutes < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyLessThanThirtyMinutes(leaveEarlyLessThanThirtyMinutes);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_TIME)) {
							// 早退時間
							int leaveEarlyTime = 0;
							try {
								leaveEarlyTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyTime(leaveEarlyTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME)) {
							// 早退30分以上時間
							int leaveEarlyThirtyMinutesOrMoreTime = 0;
							try {
								leaveEarlyThirtyMinutesOrMoreTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyThirtyMinutesOrMoreTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyThirtyMinutesOrMoreTime(leaveEarlyThirtyMinutesOrMoreTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME)) {
							// 早退30分未満時間
							int leaveEarlyLessThanThirtyMinutesTime = 0;
							try {
								leaveEarlyLessThanThirtyMinutesTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyLessThanThirtyMinutesTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyLessThanThirtyMinutesTime(leaveEarlyLessThanThirtyMinutesTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_REASON)) {
							// 早退理由
							if (value.isEmpty()) {
								dto.setLeaveEarlyReason("");
							} else if (TimeConst.CODE_LEAVEEARLY_WHY_INDIVIDU.equals(value)
									|| TimeConst.CODE_LEAVEEARLY_WHY_BAD_HEALTH.equals(value)
									|| TimeConst.CODE_LEAVEEARLY_WHY_OTHERS.equals(value)
									|| TimeConst.CODE_LEAVEEARLY_WHY_COMPANY.equals(value)) {
								// 個人都合、体調不良、その他又は会社指示の場合
								dto.setLeaveEarlyReason(value);
							} else {
								hasError = true;
								break;
							}
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_CERTIFICATE)) {
							// 早退証明書
							if (value.isEmpty()) {
								dto.setLeaveEarlyCertificate("");
							} else if ("0".equals(value) || "1".equals(value)) {
								dto.setLeaveEarlyCertificate(value);
							} else {
								hasError = true;
								break;
							}
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEAVE_EARLY_COMMENT)) {
							// 早退コメント
							if (!checkMaxLength(value, MAX_LENGTH_TIME_COMMENT)) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyComment(value);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_TIME)) {
							// 勤務時間
							int workTime = 0;
							try {
								workTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workTime < 0) {
								hasError = true;
								break;
							}
							dto.setWorkTime(workTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_GENERAL_WORK_TIME)) {
							// 所定労働時間
							int generalWorkTime = 0;
							try {
								generalWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (generalWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setGeneralWorkTime(generalWorkTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_TIME_WITHIN_PRESCRIBED_WORK_TIME)) {
							// 所定労働時間内労働時間
							int workTimeWithinPrescribedWorkTime = 0;
							try {
								workTimeWithinPrescribedWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workTimeWithinPrescribedWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setWorkTimeWithinPrescribedWorkTime(workTimeWithinPrescribedWorkTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_CONTRACT_WORK_TIME)) {
							// 契約勤務時間
							int contractWorkTime = 0;
							try {
								contractWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (contractWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setContractWorkTime(contractWorkTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_SHORT_UNPAID)) {
							// 無給時短時間
							int shortUnpaid = 0;
							try {
								shortUnpaid = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (shortUnpaid < 0) {
								hasError = true;
								break;
							}
							dto.setShortUnpaid(shortUnpaid);
						} else if (fieldName.equals(TmdAttendanceDao.COL_REST_TIME)) {
							// 休憩時間
							int restTime = 0;
							try {
								restTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (restTime < 0) {
								hasError = true;
								break;
							}
							dto.setRestTime(restTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVER_REST_TIME)) {
							// 法定外休憩時間
							int overRestTime = 0;
							try {
								overRestTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overRestTime < 0) {
								hasError = true;
								break;
							}
							dto.setOverRestTime(overRestTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_NIGHT_REST_TIME)) {
							// 深夜休憩時間
							int nightRestTime = 0;
							try {
								nightRestTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (nightRestTime < 0) {
								hasError = true;
								break;
							}
							dto.setNightRestTime(nightRestTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEGAL_HOLIDAY_REST_TIME)) {
							// 法定休出休憩時間
							int legalHolidayRestTime = 0;
							try {
								legalHolidayRestTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalHolidayRestTime < 0) {
								hasError = true;
								break;
							}
							dto.setLegalHolidayRestTime(legalHolidayRestTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_REST_TIME)) {
							// 所定休出休憩時間
							int prescribedHolidayRestTime = 0;
							try {
								prescribedHolidayRestTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayRestTime < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayRestTime(prescribedHolidayRestTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PUBLIC_TIME)) {
							// 公用外出時間
							int publicTime = 0;
							try {
								publicTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (publicTime < 0) {
								hasError = true;
								break;
							}
							dto.setPublicTime(publicTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRIVATE_TIME)) {
							// 私用外出時間
							int privateTime = 0;
							try {
								privateTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (privateTime < 0) {
								hasError = true;
								break;
							}
							dto.setPrivateTime(privateTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIMES_OVERTIME)) {
							// 残業回数
							int timesOvertime = 0;
							try {
								timesOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setTimesOvertime(timesOvertime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME)) {
							// 残業時間
							int overtime = 0;
							try {
								overtime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtime < 0) {
								hasError = true;
								break;
							}
							dto.setOvertime(overtime);
						} else if (TmdAttendanceDao.COL_OVERTIME_BEFORE.equals(fieldName)) {
							// 前残業時間
							int overtimeBefore = 0;
							try {
								overtimeBefore = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeBefore < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeBefore(overtimeBefore);
						} else if (TmdAttendanceDao.COL_OVERTIME_AFTER.equals(fieldName)) {
							// 後残業時間
							int overtimeAfter = 0;
							try {
								overtimeAfter = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeAfter < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeAfter(overtimeAfter);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_IN)) {
							// 法定内残業時間
							int overtimeIn = 0;
							try {
								overtimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeIn(overtimeIn);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_OUT)) {
							// 法定外残業時間
							int overtimeOut = 0;
							try {
								overtimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeOut(overtimeOut);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORKDAY_OVERTIME_IN)) {
							// 平日法定時間内残業時間
							int workdayOvertimeIn = 0;
							try {
								workdayOvertimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workdayOvertimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setWorkdayOvertimeIn(workdayOvertimeIn);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORKDAY_OVERTIME_OUT)) {
							// 平日法定時間外残業時間
							int workdayOvertimeOut = 0;
							try {
								workdayOvertimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workdayOvertimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setWorkdayOvertimeOut(workdayOvertimeOut);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_OVERTIME_IN)) {
							// 所定休日法定時間内残業時間
							int prescribedHolidayOvertimeIn = 0;
							try {
								prescribedHolidayOvertimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayOvertimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayOvertimeIn(prescribedHolidayOvertimeIn);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_OVERTIME_OUT)) {
							// 所定休日法定時間外残業時間
							int prescribedHolidayOvertimeOut = 0;
							try {
								prescribedHolidayOvertimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayOvertimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayOvertimeOut(prescribedHolidayOvertimeOut);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LATE_NIGHT_TIME)) {
							// 深夜勤務時間
							int lateNightTime = 0;
							try {
								lateNightTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateNightTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateNightTime(lateNightTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_SPECIFIC_WORK_TIME)) {
							// 所定休日勤務時間
							int specificWorkTime = 0;
							try {
								specificWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specificWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setSpecificWorkTime(specificWorkTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEGAL_WORK_TIME)) {
							// 法定休日勤務時間
							int legalWorkTime = 0;
							try {
								legalWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setLegalWorkTime(legalWorkTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_DECREASE_TIME)) {
							// 減額対象時間
							int decreaseTime = 0;
							try {
								decreaseTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (decreaseTime < 0) {
								hasError = true;
								break;
							}
							dto.setDecreaseTime(decreaseTime);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIME_COMMENT)) {
							// 勤怠コメント
							dto.setTimeComment(value);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_DAYS)) {
							// 出勤日数
							double workDays = 0;
							try {
								workDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workDays < 0) {
								hasError = true;
								break;
							}
							dto.setWorkDays(workDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_WORK_DAYS_FOR_PAID_LEAVE)) {
							// 有給休暇用出勤日数
							int workDaysForPaidLeave = 0;
							try {
								workDaysForPaidLeave = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workDaysForPaidLeave < 0) {
								hasError = true;
								break;
							}
							dto.setWorkDaysForPaidLeave(workDaysForPaidLeave);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TOTAL_WORK_DAYS_FOR_PAID_LEAVE)) {
							// 有給休暇用全労働日
							int totalWorkDaysForPaidLeave = 0;
							try {
								totalWorkDaysForPaidLeave = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (totalWorkDaysForPaidLeave < 0) {
								hasError = true;
								break;
							}
							dto.setTotalWorkDaysForPaidLeave(totalWorkDaysForPaidLeave);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIMES_HOLIDAY_WORK)) {
							// 休日出勤回数
							int timesHolidayWork = 0;
							try {
								timesHolidayWork = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesHolidayWork < 0) {
								hasError = true;
								break;
							}
							dto.setTimesHolidayWork(timesHolidayWork);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIMES_LEGAL_HOLIDAY_WORK)) {
							// 法定休日出勤回数
							int timesLegalHolidayWork = 0;
							try {
								timesLegalHolidayWork = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLegalHolidayWork < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLegalHolidayWork(timesLegalHolidayWork);
						} else if (fieldName.equals(TmdAttendanceDao.COL_TIMES_PRESCRIBED_HOLIDAY_WORK)) {
							// 所定休日出勤回数
							int timesPrescribedHolidayWork = 0;
							try {
								timesPrescribedHolidayWork = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesPrescribedHolidayWork < 0) {
								hasError = true;
								break;
							}
							dto.setTimesPrescribedHolidayWork(timesPrescribedHolidayWork);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PAID_LEAVE_DAYS)) {
							// 有給休暇日数
							double paidLeaveDays = 0;
							try {
								paidLeaveDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (paidLeaveDays < 0) {
								hasError = true;
								break;
							}
							dto.setPaidLeaveDays(paidLeaveDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PAID_LEAVE_HOURS)) {
							// 有給休暇時間数
							int paidLeaveHours = 0;
							try {
								paidLeaveHours = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (paidLeaveHours < 0) {
								hasError = true;
								break;
							}
							dto.setPaidLeaveHours(paidLeaveHours);
						} else if (fieldName.equals(TmdAttendanceDao.COL_STOCK_LEAVE_DAYS)) {
							// ストック休暇日数
							double stockLeaveDays = 0;
							try {
								stockLeaveDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (stockLeaveDays < 0) {
								hasError = true;
								break;
							}
							dto.setStockLeaveDays(stockLeaveDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_COMPENSATION_DAYS)) {
							// 代休日数
							double compensationDays = 0;
							try {
								compensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (compensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setCompensationDays(compensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEGAL_COMPENSATION_DAYS)) {
							// 法定代休日数
							double legalCompensationDays = 0;
							try {
								legalCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setLegalCompensationDays(legalCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_COMPENSATION_DAYS)) {
							// 所定代休日数
							double prescribedCompensationDays = 0;
							try {
								prescribedCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedCompensationDays(prescribedCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_NIGHT_COMPENSATION_DAYS)) {
							// 深夜代休日数
							double nightCompensationDays = 0;
							try {
								nightCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (nightCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setNightCompensationDays(nightCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_SPECIAL_LEAVE_DAYS)) {
							// 特別休暇日数
							double specialLeaveDays = 0;
							try {
								specialLeaveDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specialLeaveDays < 0) {
								hasError = true;
								break;
							}
							dto.setSpecialLeaveDays(specialLeaveDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OTHER_LEAVE_DAYS)) {
							// その他休暇日数
							double otherLeaveDays = 0;
							try {
								otherLeaveDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (otherLeaveDays < 0) {
								hasError = true;
								break;
							}
							dto.setOtherLeaveDays(otherLeaveDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_ABSENCE_DAYS)) {
							// 欠勤日数
							double absenceDays = 0;
							try {
								absenceDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (absenceDays < 0) {
								hasError = true;
								break;
							}
							dto.setAbsenceDays(absenceDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_GRANTED_LEGAL_COMPENSATION_DAYS)) {
							// 法定代休発生日数
							double grantedLegalCompensationDays = 0;
							try {
								grantedLegalCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (grantedLegalCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setGrantedLegalCompensationDays(grantedLegalCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_GRANTED_PRESCRIBED_COMPENSATION_DAYS)) {
							// 所定代休発生日数
							double grantedPrescribedCompensationDays = 0;
							try {
								grantedPrescribedCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (grantedPrescribedCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setGrantedPrescribedCompensationDays(grantedPrescribedCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_GRANTED_NIGHT_COMPENSATION_DAYS)) {
							// 深夜代休発生日数
							double grantedNightCompensationDays = 0;
							try {
								grantedNightCompensationDays = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (grantedNightCompensationDays < 0) {
								hasError = true;
								break;
							}
							dto.setGrantedNightCompensationDays(grantedNightCompensationDays);
						} else if (fieldName.equals(TmdAttendanceDao.COL_LEGAL_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY)) {
							// 法定休出時間(代休あり)
							int legalHolidayWorkTimeWithCompensationDay = 0;
							try {
								legalHolidayWorkTimeWithCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalHolidayWorkTimeWithCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setLegalHolidayWorkTimeWithCompensationDay(legalHolidayWorkTimeWithCompensationDay);
						} else if (fieldName
							.equals(TmdAttendanceDao.COL_LEGAL_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY)) {
							// 法定休出時間(代休なし)
							int legalHolidayWorkTimeWithoutCompensationDay = 0;
							try {
								legalHolidayWorkTimeWithoutCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalHolidayWorkTimeWithoutCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setLegalHolidayWorkTimeWithoutCompensationDay(legalHolidayWorkTimeWithoutCompensationDay);
						} else if (fieldName
							.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY)) {
							// 所定休出時間(代休あり)
							int prescribedHolidayWorkTimeWithCompensationDay = 0;
							try {
								prescribedHolidayWorkTimeWithCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeWithCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeWithCompensationDay(prescribedHolidayWorkTimeWithCompensationDay);
						} else if (fieldName
							.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY)) {
							// 所定休出時間(代休なし)
							int prescribedHolidayWorkTimeWithoutCompensationDay = 0;
							try {
								prescribedHolidayWorkTimeWithoutCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeWithoutCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeWithoutCompensationDay(prescribedHolidayWorkTimeWithoutCompensationDay);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_IN_WITH_COMPENSATION_DAY)) {
							// 法定労働時間内残業時間(代休あり)
							int overtimeInWithCompensationDay = 0;
							try {
								overtimeInWithCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeInWithCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeInWithCompensationDay(overtimeInWithCompensationDay);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_IN_WITHOUT_COMPENSATION_DAY)) {
							// 法定労働時間内残業時間(代休なし)
							int overtimeInWithoutCompensationDay = 0;
							try {
								overtimeInWithoutCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeInWithoutCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeInWithoutCompensationDay(overtimeInWithoutCompensationDay);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_OUT_WITH_COMPENSATION_DAY)) {
							// 法定労働時間外残業時間(代休あり)
							int overtimeOutWithCompensationDay = 0;
							try {
								overtimeOutWithCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeOutWithCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeOutWithCompensationDay(overtimeOutWithCompensationDay);
						} else if (fieldName.equals(TmdAttendanceDao.COL_OVERTIME_OUT_WITHOUT_COMPENSATION_DAY)) {
							// 法定労働時間外残業時間(代休なし)
							int overtimeOutWithoutCompensationDay = 0;
							try {
								overtimeOutWithoutCompensationDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeOutWithoutCompensationDay < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeOutWithoutCompensationDay(overtimeOutWithoutCompensationDay);
						} else if (fieldName.equals(TmdAttendanceDao.COL_STATUTORY_HOLIDAY_WORK_TIME_IN)) {
							// 所定労働時間内法定休日労働時間
							int statutoryHolidayWorkTimeIn = 0;
							try {
								statutoryHolidayWorkTimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (statutoryHolidayWorkTimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setStatutoryHolidayWorkTimeIn(statutoryHolidayWorkTimeIn);
						} else if (fieldName.equals(TmdAttendanceDao.COL_STATUTORY_HOLIDAY_WORK_TIME_OUT)) {
							// 所定労働時間外法定休日労働時間
							int statutoryHolidayWorkTimeOut = 0;
							try {
								statutoryHolidayWorkTimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (statutoryHolidayWorkTimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setStatutoryHolidayWorkTimeOut(statutoryHolidayWorkTimeOut);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN)) {
							// 所定労働時間内所定休日労働時間
							int prescribedHolidayWorkTimeIn = 0;
							try {
								prescribedHolidayWorkTimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeIn(prescribedHolidayWorkTimeIn);
						} else if (fieldName.equals(TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT)) {
							// 所定労働時間外所定休日労働時間
							int prescribedHolidayWorkTimeOut = 0;
							try {
								prescribedHolidayWorkTimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeOut(prescribedHolidayWorkTimeOut);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getWorkDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getWorkDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				if (!hasError) {
					if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
						hasError = true;
					} else {
						WorkTypeDtoInterface workTypeDto = workTypeDao.findForInfo(dto.getWorkTypeCode(),
								dto.getWorkDate());
						if (workTypeDto == null) {
							hasError = true;
						}
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					// ファイル内重複チェック
					for (AttendanceDtoInterface attendanceDto : attendanceList) {
						if (attendanceDto.getPersonalId().equals(dto.getPersonalId())
								&& attendanceDto.getWorkDate().equals(dto.getWorkDate())
								&& attendanceDto.getTimesWork() == dto.getTimesWork()) {
							addDuplicateDataErrorMessage(i);
							hasError = true;
							break;
						}
					}
				}
				if (!hasError) {
					AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(),
							dto.getWorkDate(), dto.getTimesWork());
					if (attendanceDto != null) {
						WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
						if (workflowDto != null) {
							if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
								// 下書の場合
								dto.setTmdAttendanceId(attendanceDto.getTmdAttendanceId());
								dto.setWorkflow(attendanceDto.getWorkflow());
							} else if (PlatformConst.CODE_STATUS_APPLY.equals(workflowDto.getWorkflowStatus())
									|| PlatformConst.CODE_STATUS_APPROVED.equals(workflowDto.getWorkflowStatus())
									|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())
									|| PlatformConst.CODE_STATUS_CANCEL.equals(workflowDto.getWorkflowStatus())
									|| PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
								// 未承認・承認・差戻・承認解除・承認済の場合
								addAlreadyRegisteredDataErrorMessage(i);
								hasError = true;
								break;
							}
						}
					}
				}
				if (!hasError) {
					attendanceList.add(dto);
				}
			}
			i++;
		}
		return attendanceList;
	}
	
	@Override
	public List<TotalTimeDataDtoInterface> getTotalTimeList(String importCode, List<String[]> list)
			throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<TotalTimeDataDtoInterface> totaltimeList = new ArrayList<TotalTimeDataDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有り場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				TotalTimeDataDtoInterface dto = new TmdTotalTimeDataDto();
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (fieldName.equals(PfmHumanDao.COL_EMPLOYEE_CODE)) {
							// 社員コード
							employeeCode = value;
						} else if (fieldName.equals(TmdTotalTimeDao.COL_CALCULATION_YEAR)) {
							// 年
							int calculationYear = 1;
							try {
								calculationYear = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (calculationYear < 1) {
								hasError = true;
								break;
							}
							dto.setCalculationYear(calculationYear);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_CALCULATION_MONTH)) {
							// 月
							int calculationMonth = 1;
							try {
								calculationMonth = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (calculationMonth < 1 || calculationMonth > 12) {
								hasError = true;
								break;
							}
							dto.setCalculationMonth(calculationMonth);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_CALCULATION_DATE)) {
							// 集計日
							Date calculationDate = getDate(value);
							if (calculationDate == null) {
								hasError = true;
								break;
							}
							dto.setCalculationDate(calculationDate);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WORK_TIME)) {
							// 勤務時間
							int workTime = 0;
							try {
								workTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workTime < 0) {
								hasError = true;
								break;
							}
							dto.setWorkTime(workTime);
						} else if (TmdTotalTimeDao.COL_SPECIFIC_WORK_TIME.equals(fieldName)) {
							// 所定勤務時間
							int specificWorkTime = 0;
							try {
								specificWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specificWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setSpecificWorkTime(specificWorkTime);
						} else if (TmdTotalTimeDao.COL_SHORT_UNPAID.equals(fieldName)) {
							// 無給時短時間
							int shortUnpaid = 0;
							try {
								shortUnpaid = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (shortUnpaid < 0) {
								hasError = true;
								break;
							}
							dto.setShortUnpaid(shortUnpaid);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_WORK_DATE)) {
							// 出勤日数
							double timesWorkDate = 0;
							try {
								timesWorkDate = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesWorkDate < 0) {
								hasError = true;
								break;
							}
							dto.setTimesWorkDate(timesWorkDate);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_WORK)) {
							// 出勤回数
							int timesWork = 0;
							try {
								timesWork = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesWork < 0) {
								hasError = true;
								break;
							}
							dto.setTimesWork(timesWork);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_LEGAL_WORK_ON_HOLIDAY)) {
							// 法定休日出勤日数
							double legalWorkOnHoliday = 0;
							try {
								legalWorkOnHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalWorkOnHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setLegalWorkOnHoliday(legalWorkOnHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_SPECIFIC_WORK_ON_HOLIDAY)) {
							// 所定休日出勤日数
							double specificWorkOnHoliday = 0;
							try {
								specificWorkOnHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specificWorkOnHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setSpecificWorkOnHoliday(specificWorkOnHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_ACHIEVEMENT)) {
							// 出勤実績日数
							int timesAchievement = 0;
							try {
								timesAchievement = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesAchievement < 0) {
								hasError = true;
								break;
							}
							dto.setTimesAchievement(timesAchievement);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_TOTAL_WORK_DATE)) {
							// 出勤対象日数
							int timesTotalWorkDate = 0;
							try {
								timesTotalWorkDate = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesTotalWorkDate < 0) {
								hasError = true;
								break;
							}
							dto.setTimesTotalWorkDate(timesTotalWorkDate);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_DIRECT_START)) {
							// 直行回数
							int directStart = 0;
							try {
								directStart = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (directStart < 0) {
								hasError = true;
								break;
							}
							dto.setDirectStart(directStart);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_DIRECT_END)) {
							// 直帰回数
							int directEnd = 0;
							try {
								directEnd = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (directEnd < 0) {
								hasError = true;
								break;
							}
							dto.setDirectEnd(directEnd);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_REST_TIME)) {
							// 休憩時間
							int restTime = 0;
							try {
								restTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (restTime < 0) {
								hasError = true;
								break;
							}
							dto.setRestTime(restTime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_REST_LATE_NIGHT)) {
							// 深夜休憩時間
							int restLateNight = 0;
							try {
								restLateNight = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (restLateNight < 0) {
								hasError = true;
								break;
							}
							dto.setRestLateNight(restLateNight);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_REST_WORK_ON_SPECIFIC_HOLIDAY)) {
							// 所定休出休憩時間
							int restWorkOnSpecificHoliday = 0;
							try {
								restWorkOnSpecificHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (restWorkOnSpecificHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setRestWorkOnSpecificHoliday(restWorkOnSpecificHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_REST_WORK_ON_HOLIDAY)) {
							// 法定休出休憩時間
							int restWorkOnHoliday = 0;
							try {
								restWorkOnHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (restWorkOnHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setRestWorkOnHoliday(restWorkOnHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_PUBLIC_TIME)) {
							// 公用外出時間
							int publicTime = 0;
							try {
								publicTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (publicTime < 0) {
								hasError = true;
								break;
							}
							dto.setPublicTime(publicTime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_PRIVATE_TIME)) {
							// 私用外出時間
							int privateTime = 0;
							try {
								privateTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (privateTime < 0) {
								hasError = true;
								break;
							}
							dto.setPrivateTime(privateTime);
						} else if (TmdTotalTimeDao.COL_OVERTIME.equals(fieldName)) {
							// 残業時間
							int overtime = 0;
							try {
								overtime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtime < 0) {
								hasError = true;
								break;
							}
							dto.setOvertime(overtime);
						} else if (TmdTotalTimeDao.COL_OVERTIME_IN.equals(fieldName)) {
							// 法定内残業時間
							int overtimeIn = 0;
							try {
								overtimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeIn(overtimeIn);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_OVERTIME_OUT)) {
							// 法定外残業時間
							int overtimeOut = 0;
							try {
								overtimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeOut(overtimeOut);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_LATE_NIGHT)) {
							// 深夜時間
							int lateNight = 0;
							try {
								lateNight = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateNight < 0) {
								hasError = true;
								break;
							}
							dto.setLateNight(lateNight);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WORK_ON_SPECIFIC_HOLIDAY)) {
							// 所定休出時間
							int workOnSpecificHoliday = 0;
							try {
								workOnSpecificHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workOnSpecificHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setWorkOnSpecificHoliday(workOnSpecificHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WORK_ON_HOLIDAY)) {
							// 法定休出時間
							int workOnHoliday = 0;
							try {
								workOnHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (workOnHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setWorkOnHoliday(workOnHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_DECREASE_TIME)) {
							// 減額対象時間
							int decreaseTime = 0;
							try {
								decreaseTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (decreaseTime < 0) {
								hasError = true;
								break;
							}
							dto.setDecreaseTime(decreaseTime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_FORTY_FIVE_HOUR_OVERTIME)) {
							// 45時間超残業時間
							int fortyFiveHourOvertime = 0;
							try {
								fortyFiveHourOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (fortyFiveHourOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setFortyFiveHourOvertime(fortyFiveHourOvertime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_OVERTIME)) {
							// 残業回数
							int timesOvertime = 0;
							try {
								timesOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setTimesOvertime(timesOvertime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_WORKING_HOLIDAY)) {
							// 休日出勤回数
							int timesWorkingHoliday = 0;
							try {
								timesWorkingHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesWorkingHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesWorkingHoliday(timesWorkingHoliday);
						} else if (TmdTotalTimeDao.COL_LATE_DAYS.equals(fieldName)) {
							// 合計遅刻日数
							int lateDays = 0;
							try {
								lateDays = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateDays < 0) {
								hasError = true;
								break;
							}
							dto.setLateDays(lateDays);
						} else if (TmdTotalTimeDao.COL_LATE_THIRTY_MINUTES_OR_MORE.equals(fieldName)) {
							// 遅刻30分以上日数
							int lateThirtyMinutesOrMore = 0;
							try {
								lateThirtyMinutesOrMore = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateThirtyMinutesOrMore < 0) {
								hasError = true;
								break;
							}
							dto.setLateThirtyMinutesOrMore(lateThirtyMinutesOrMore);
						} else if (TmdTotalTimeDao.COL_LATE_LESS_THAN_THIRTY_MINUTES.equals(fieldName)) {
							// 遅刻30分未満日数
							int lateLessThanThirtyMinutes = 0;
							try {
								lateLessThanThirtyMinutes = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateLessThanThirtyMinutes < 0) {
								hasError = true;
								break;
							}
							dto.setLateLessThanThirtyMinutes(lateLessThanThirtyMinutes);
						} else if (TmdTotalTimeDao.COL_LATE_TIME.equals(fieldName)) {
							// 合計遅刻時間
							int lateTime = 0;
							try {
								lateTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateTime(lateTime);
						} else if (TmdTotalTimeDao.COL_LATE_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)) {
							// 遅刻30分以上時間
							int lateThirtyMinutesOrMoreTime = 0;
							try {
								lateThirtyMinutesOrMoreTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateThirtyMinutesOrMoreTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateThirtyMinutesOrMoreTime(lateThirtyMinutesOrMoreTime);
						} else if (TmdTotalTimeDao.COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)) {
							// 遅刻30分未満時間
							int lateLessThanThirtyMinutesTime = 0;
							try {
								lateLessThanThirtyMinutesTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateLessThanThirtyMinutesTime < 0) {
								hasError = true;
								break;
							}
							dto.setLateLessThanThirtyMinutesTime(lateLessThanThirtyMinutesTime);
						} else if (TmdTotalTimeDao.COL_TIMES_LATE.equals(fieldName)) {
							// 合計遅刻回数
							int timesLate = 0;
							try {
								timesLate = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLate < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLate(timesLate);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_DAYS.equals(fieldName)) {
							// 合計早退日数
							int leaveEarlyDays = 0;
							try {
								leaveEarlyDays = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyDays < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyDays(leaveEarlyDays);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE.equals(fieldName)) {
							//  早退30分以上日数
							int leaveEarlyThirtyMinutesOrMore = 0;
							try {
								leaveEarlyThirtyMinutesOrMore = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyThirtyMinutesOrMore < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyThirtyMinutesOrMore(leaveEarlyThirtyMinutesOrMore);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES.equals(fieldName)) {
							// 早退30分未満日数
							int leaveEarlyLessThanThirtyMinutes = 0;
							try {
								leaveEarlyLessThanThirtyMinutes = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyLessThanThirtyMinutes < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyLessThanThirtyMinutes(leaveEarlyLessThanThirtyMinutes);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_TIME.equals(fieldName)) {
							// 合計早退時間
							int leaveEarlyTime = 0;
							try {
								leaveEarlyTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyTime(leaveEarlyTime);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)) {
							// 早退30分以上時間
							int leaveEarlyThirtyMinutesOrMoreTime = 0;
							try {
								leaveEarlyThirtyMinutesOrMoreTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyThirtyMinutesOrMoreTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyThirtyMinutesOrMoreTime(leaveEarlyThirtyMinutesOrMoreTime);
						} else if (TmdTotalTimeDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)) {
							// 早退30分未満時間
							int leaveEarlyLessThanThirtyMinutesTime = 0;
							try {
								leaveEarlyLessThanThirtyMinutesTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (leaveEarlyLessThanThirtyMinutesTime < 0) {
								hasError = true;
								break;
							}
							dto.setLeaveEarlyLessThanThirtyMinutesTime(leaveEarlyLessThanThirtyMinutesTime);
						} else if (TmdTotalTimeDao.COL_TIMES_LEAVE_EARLY.equals(fieldName)) {
							// 合計早退回数
							int timesLeaveEarly = 0;
							try {
								timesLeaveEarly = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLeaveEarly < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLeaveEarly(timesLeaveEarly);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_HOLIDAY)) {
							// 休日日数
							int timesHoliday = 0;
							try {
								timesHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesHoliday(timesHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_LEGAL_HOLIDAY)) {
							// 法定休日日数
							int timesLegalHoliday = 0;
							try {
								timesLegalHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLegalHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLegalHoliday(timesLegalHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_SPECIFIC_HOLIDAY)) {
							// 所定休日日数
							int timesSpecificHoliday = 0;
							try {
								timesSpecificHoliday = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesSpecificHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesSpecificHoliday(timesSpecificHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_PAID_HOLIDAY)) {
							// 有給休暇日数
							double timesPaidHoliday = 0;
							try {
								timesPaidHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesPaidHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesPaidHoliday(timesPaidHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_PAID_HOLIDAY_HOUR)) {
							// 有給休暇時間
							int paidHolidayHour = 0;
							try {
								paidHolidayHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (paidHolidayHour < 0) {
								hasError = true;
								break;
							}
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_STOCK_HOLIDAY)) {
							// ストック休暇日数
							double timesStockHoliday = 0;
							try {
								timesStockHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesStockHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTimesStockHoliday(timesStockHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_COMPENSATION)) {
							// 代休日数
							double timesCompensation = 0;
							try {
								timesCompensation = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesCompensation < 0) {
								hasError = true;
								break;
							}
							dto.setTimesCompensation(timesCompensation);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_LEGAL_COMPENSATION)) {
							// 法定代休日数
							double timesLegalCompensation = 0;
							try {
								timesLegalCompensation = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLegalCompensation < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLegalCompensation(timesLegalCompensation);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_SPECIFIC_COMPENSATION)) {
							// 所定代休日数
							double timesSpecificCompensation = 0;
							try {
								timesSpecificCompensation = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesSpecificCompensation < 0) {
								hasError = true;
								break;
							}
							dto.setTimesSpecificCompensation(timesSpecificCompensation);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_LATE_COMPENSATION)) {
							// 深夜代休日数
							double timesLateCompensation = 0;
							try {
								timesLateCompensation = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLateCompensation < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLateCompensation(timesLateCompensation);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_HOLIDAY_SUBSTITUTE)) {
							// 振替休日日数
							double timesHolidaySubstitute = 0;
							try {
								timesHolidaySubstitute = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesHolidaySubstitute < 0) {
								hasError = true;
								break;
							}
							dto.setTimesHolidaySubstitute(timesHolidaySubstitute);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_LEGAL_HOLIDAY_SUBSTITUTE)) {
							//  法定振替休日日数
							double timesLegalHolidaySubstitute = 0;
							try {
								timesLegalHolidaySubstitute = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesLegalHolidaySubstitute < 0) {
								hasError = true;
								break;
							}
							dto.setTimesLegalHolidaySubstitute(timesLegalHolidaySubstitute);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_SPECIFIC_HOLIDAY_SUBSTITUTE)) {
							// 所定振替休日日数
							double timesSpecificHolidaySubstitute = 0;
							try {
								timesSpecificHolidaySubstitute = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesSpecificHolidaySubstitute < 0) {
								hasError = true;
								break;
							}
							dto.setTimesSpecificHolidaySubstitute(timesSpecificHolidaySubstitute);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TOTAL_SPECIAL_HOLIDAY)) {
							// 特別休暇合計日数
							double totalSpecialHoliday = 0;
							try {
								totalSpecialHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (totalSpecialHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTotalSpecialHoliday(totalSpecialHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TOTAL_OTHER_HOLIDAY)) {
							// その他休暇合計日数
							double totalOtherHoliday = 0;
							try {
								totalOtherHoliday = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (totalOtherHoliday < 0) {
								hasError = true;
								break;
							}
							dto.setTotalOtherHoliday(totalOtherHoliday);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TOTAL_ABSENCE)) {
							// 欠勤合計日数
							int totalAbsence = 0;
							try {
								totalAbsence = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (totalAbsence < 0) {
								hasError = true;
								break;
							}
							dto.setTotalAbsence(totalAbsence);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TOTAL_ALLOWANCE)) {
							// 手当合計
							int totalAllowance = 0;
							try {
								totalAllowance = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (totalAllowance < 0) {
								hasError = true;
								break;
							}
							dto.setTotalAllowance(totalAllowance);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_SIXTY_HOUR_OVERTIME)) {
							// 60時間超残業時間
							int sixtyHourOvertime = 0;
							try {
								sixtyHourOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (sixtyHourOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setSixtyHourOvertime(sixtyHourOvertime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME)) {
							// 平日時間外時間
							int weekDayOvertime = 0;
							try {
								weekDayOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weekDayOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setWeekDayOvertime(weekDayOvertime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_SPECIFIC_OVERTIME)) {
							// 所定休日時間外時間
							int specificOvertime = 0;
							try {
								specificOvertime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specificOvertime < 0) {
								hasError = true;
								break;
							}
							dto.setSpecificOvertime(specificOvertime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_TIMES_ALTERNATIVE)) {
							// 代替休暇日数
							double timesAlternative = 0;
							try {
								timesAlternative = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (timesAlternative < 0) {
								hasError = true;
								break;
							}
							dto.setTimesAlternative(timesAlternative);
						} else if (TmdTotalTimeDao.COL_LEGAL_COMPENSATION_UNUSED.equals(fieldName)) {
							// 法定代休未使用日数
							double legalCompensationUnused = 0;
							try {
								legalCompensationUnused = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (legalCompensationUnused < 0) {
								hasError = true;
								break;
							}
							dto.setLegalCompensationUnused(legalCompensationUnused);
						} else if (TmdTotalTimeDao.COL_SPECIFIC_COMPENSATION_UNUSED.equals(fieldName)) {
							// 所定代休未使用日数
							double specificCompensationUnused = 0;
							try {
								specificCompensationUnused = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (specificCompensationUnused < 0) {
								hasError = true;
								break;
							}
							dto.setSpecificCompensationUnused(specificCompensationUnused);
						} else if (TmdTotalTimeDao.COL_LATE_COMPENSATION_UNUSED.equals(fieldName)) {
							// 深夜代休未使用日数
							double lateCompensationUnused = 0;
							try {
								lateCompensationUnused = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (lateCompensationUnused < 0) {
								hasError = true;
								break;
							}
							dto.setLateCompensationUnused(lateCompensationUnused);
						} else if (TmdTotalTimeDao.COL_STATUTORY_HOLIDAY_WORK_TIME_IN.equals(fieldName)) {
							// 所定労働時間内法定休日労働時間
							int statutoryHolidayWorkTimeIn = 0;
							try {
								statutoryHolidayWorkTimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (statutoryHolidayWorkTimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setStatutoryHolidayWorkTimeIn(statutoryHolidayWorkTimeIn);
						} else if (TmdTotalTimeDao.COL_STATUTORY_HOLIDAY_WORK_TIME_OUT.equals(fieldName)) {
							// 所定労働時間外法定休日労働時間
							int statutoryHolidayWorkTimeOut = 0;
							try {
								statutoryHolidayWorkTimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (statutoryHolidayWorkTimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setStatutoryHolidayWorkTimeOut(statutoryHolidayWorkTimeOut);
						} else if (TmdTotalTimeDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN.equals(fieldName)) {
							// 所定労働時間内所定休日労働時間
							int prescribedHolidayWorkTimeIn = 0;
							try {
								prescribedHolidayWorkTimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeIn(prescribedHolidayWorkTimeIn);
						} else if (TmdTotalTimeDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT.equals(fieldName)) {
							// 所定労働時間外所定休日労働時間
							int prescribedHolidayWorkTimeOut = 0;
							try {
								prescribedHolidayWorkTimeOut = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (prescribedHolidayWorkTimeOut < 0) {
								hasError = true;
								break;
							}
							dto.setPrescribedHolidayWorkTimeOut(prescribedHolidayWorkTimeOut);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEKLY_OVER_FORTY_HOUR_WORK_TIME)) {
							// 週40時間超勤務時間
							int weeklyOverFortyHourWorkTime = 0;
							try {
								weeklyOverFortyHourWorkTime = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weeklyOverFortyHourWorkTime < 0) {
								hasError = true;
								break;
							}
							dto.setWeeklyOverFortyHourWorkTime(weeklyOverFortyHourWorkTime);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_OVERTIME_IN_NO_WEEKLY_FORTY)) {
							// 法定内残業時間(週40時間超除く)
							int overtimeInNoWeeklyForty = 0;
							try {
								overtimeInNoWeeklyForty = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeInNoWeeklyForty < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeInNoWeeklyForty(overtimeInNoWeeklyForty);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_OVERTIME_OUT_NO_WEEKLY_FORTY)) {
							// 法定外残業時間(週40時間超除く)
							int overtimeOutNoWeeklyForty = 0;
							try {
								overtimeOutNoWeeklyForty = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (overtimeOutNoWeeklyForty < 0) {
								hasError = true;
								break;
							}
							dto.setOvertimeOutNoWeeklyForty(overtimeOutNoWeeklyForty);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_TOTAL)) {
							// 平日残業合計時間
							int weekDayOvertimeTotal = 0;
							try {
								weekDayOvertimeTotal = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weekDayOvertimeTotal < 0) {
								hasError = true;
								break;
							}
							dto.setWeekDayOvertimeTotal(weekDayOvertimeTotal);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_IN_NO_WEEKLY_FORTY)) {
							// 平日時間内時間(週40時間超除く)
							int weekDayOvertimeInNoWeeklyForty = 0;
							try {
								weekDayOvertimeInNoWeeklyForty = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weekDayOvertimeInNoWeeklyForty < 0) {
								hasError = true;
								break;
							}
							dto.setWeekDayOvertimeInNoWeeklyForty(weekDayOvertimeInNoWeeklyForty);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_OUT_NO_WEEKLY_FORTY)) {
							// 平日時間外時間(週40時間超除く)
							int weekDayOvertimeOutNoWeeklyForty = 0;
							try {
								weekDayOvertimeOutNoWeeklyForty = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weekDayOvertimeOutNoWeeklyForty < 0) {
								hasError = true;
								break;
							}
							dto.setWeekDayOvertimeOutNoWeeklyForty(weekDayOvertimeOutNoWeeklyForty);
						} else if (fieldName.equals(TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_IN)) {
							// 平日時間内時間
							int weekDayOvertimeIn = 0;
							try {
								weekDayOvertimeIn = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (weekDayOvertimeIn < 0) {
								hasError = true;
								break;
							}
							dto.setWeekDayOvertimeIn(weekDayOvertimeIn);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getCalculationYear() < 1) {
					hasError = true;
				}
				if (!hasError && (dto.getCalculationMonth() < 1 || dto.getCalculationMonth() > 12)) {
					hasError = true;
				}
				if (!hasError && dto.getCalculationDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getCalculationDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				String cutoffCode = "";
				if (!hasError) {
					ApplicationDtoInterface applicationDto = application.findForPerson(dto.getPersonalId(),
							dto.getCalculationDate());
					if (applicationDto == null) {
						hasError = true;
					} else {
						TimeSettingDtoInterface timeSettingDto = timeSettingDao.findForInfo(
								applicationDto.getWorkSettingCode(), dto.getCalculationDate());
						if (timeSettingDto == null) {
							hasError = true;
						} else {
							CutoffDtoInterface cutoffDto = cutoffDao.findForInfo(timeSettingDto.getCutoffCode(),
									dto.getCalculationDate());
							if (cutoffDto == null) {
								hasError = true;
							} else {
								// 締期間最終日を取得
								Date cutoffDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(),
										dto.getCalculationYear(), dto.getCalculationMonth());
								// 締日が末日でない場合
								if (cutoffDto.getCutoffDate() != 0) {
									// 集計年月と締日で締日付取得
									cutoffDate = DateUtility.getDate(dto.getCalculationYear(),
											dto.getCalculationMonth(), cutoffDto.getCutoffDate());
								}
								if (cutoffDate.equals(dto.getCalculationDate())) {
									cutoffCode = cutoffDto.getCutoffCode();
								} else {
									hasError = true;
								}
							}
						}
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					// ファイル内重複チェック
					for (TotalTimeDataDtoInterface totalTimeDataDto : totaltimeList) {
						if (totalTimeDataDto.getPersonalId().equals(dto.getPersonalId())
								&& totalTimeDataDto.getCalculationYear() == dto.getCalculationYear()
								&& totalTimeDataDto.getCalculationMonth() == dto.getCalculationMonth()) {
							addDuplicateDataErrorMessage(i);
							hasError = true;
							break;
						}
					}
				}
				if (!hasError) {
					TotalTimeDataDtoInterface totalTimeDataDto = totalTimeDataDao.findForKey(dto.getPersonalId(),
							dto.getCalculationYear(), dto.getCalculationMonth());
					if (totalTimeDataDto != null && cutoffCode != null && !cutoffCode.isEmpty()) {
						TotalTimeDtoInterface totalTimeDto = totalTimeTransaction.findForKey(dto.getCalculationYear(),
								dto.getCalculationMonth(), cutoffCode);
						if (totalTimeDto == null) {
							dto.setTmdTotalTimeId(totalTimeDataDto.getTmdTotalTimeId());
						} else {
							int state = totalTimeDto.getCutoffState();
							if (state == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT
									|| state == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
								// 未締・仮締の場合
								dto.setTmdTotalTimeId(totalTimeDataDto.getTmdTotalTimeId());
							} else if (state == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
								// 確定の場合
								addAlreadyRegisteredDataErrorMessage(i);
								hasError = true;
								break;
							}
						}
					}
				}
				if (!hasError) {
					totaltimeList.add(dto);
				}
			}
			i++;
		}
		return totaltimeList;
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayList(String importCode, List<String[]> list)
			throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<PaidHolidayDataDtoInterface> paidHolidayDataList = new ArrayList<PaidHolidayDataDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有り場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				PaidHolidayDataDtoInterface dto = new TmdPaidHolidayDataDto();
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (fieldName.equals(PfmHumanDao.COL_EMPLOYEE_CODE)) {
							// 社員コード
							employeeCode = value;
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_ACTIVATE_DATE)) {
							// 有効日
							Date activateDate = getDate(value);
							if (activateDate == null) {
								hasError = true;
								break;
							}
							dto.setActivateDate(activateDate);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_ACQUISITION_DATE)) {
							// 取得日
							Date acquisitionDate = getDate(value);
							if (acquisitionDate == null) {
								hasError = true;
								break;
							}
							dto.setAcquisitionDate(acquisitionDate);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_LIMIT_DATE)) {
							// 期限日
							Date limitDate = getDate(value);
							if (limitDate == null) {
								hasError = true;
								break;
							}
							dto.setLimitDate(limitDate);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_HOLD_DAY)) {
							// 保有日数
							double holdDay = 0;
							try {
								holdDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holdDay < 0) {
								hasError = true;
								break;
							}
							dto.setHoldDay(holdDay);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_HOLD_HOUR)) {
							// 保有時間数
							int holdHour = 0;
							try {
								holdHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holdHour < 0) {
								hasError = true;
								break;
							}
							dto.setHoldHour(holdHour);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_GIVING_DAY)) {
							// 付与日数
							double givingDay = 0;
							try {
								givingDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (givingDay < 0) {
								hasError = true;
								break;
							}
							dto.setGivingDay(givingDay);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_GIVING_HOUR)) {
							// 付与時間数
							int givingHour = 0;
							try {
								givingHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (givingHour < 0) {
								hasError = true;
								break;
							}
							dto.setGivingHour(givingHour);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_CANCEL_DAY)) {
							// 廃棄日数
							double cancelDay = 0;
							try {
								cancelDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (cancelDay < 0) {
								hasError = true;
								break;
							}
							dto.setCancelDay(cancelDay);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_CANCEL_HOUR)) {
							// 廃棄時間数
							int cancelHour = 0;
							try {
								cancelHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (cancelHour < 0) {
								hasError = true;
								break;
							}
							dto.setCancelHour(cancelHour);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_USE_DAY)) {
							// 使用日数
							double useDay = 0;
							try {
								useDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (useDay < 0) {
								hasError = true;
								break;
							}
							dto.setUseDay(useDay);
						} else if (fieldName.equals(TmdPaidHolidayDao.COL_USE_HOUR)) {
							// 使用時間数
							int useHour = 0;
							try {
								useHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (useHour < 0) {
								hasError = true;
								break;
							}
							dto.setUseHour(useHour);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getActivateDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getActivateDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					// ファイル内重複チェック
					for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataList) {
						if (paidHolidayDataDto.getPersonalId().equals(dto.getPersonalId())
								&& paidHolidayDataDto.getActivateDate().equals(dto.getActivateDate())
								&& paidHolidayDataDto.getAcquisitionDate().equals(dto.getAcquisitionDate())) {
							addDuplicateDataErrorMessage(i);
							hasError = true;
							break;
						}
					}
				}
				if (!hasError) {
					PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataDao.findForKey(dto.getPersonalId(),
							dto.getActivateDate(), dto.getAcquisitionDate());
					if (paidHolidayDataDto != null) {
						dto.setTmdPaidHolidayId(paidHolidayDataDto.getTmdPaidHolidayId());
					}
				}
				if (!hasError) {
					paidHolidayDataList.add(dto);
				}
			}
			i++;
		}
		return paidHolidayDataList;
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayList(String importCode, List<String[]> list)
			throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<StockHolidayDataDtoInterface> stockHolidayDataList = new ArrayList<StockHolidayDataDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有り場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				StockHolidayDataDtoInterface dto = new TmdStockHolidayDto();
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (fieldName.equals(PfmHumanDao.COL_EMPLOYEE_CODE)) {
							// 社員コード
							employeeCode = value;
						} else if (fieldName.equals(TmdStockHolidayDao.COL_ACTIVATE_DATE)) {
							// 有効日
							Date activateDate = getDate(value);
							if (activateDate == null) {
								hasError = true;
								break;
							}
							dto.setActivateDate(activateDate);
						} else if (fieldName.equals(TmdStockHolidayDao.COL_ACQUISITION_DATE)) {
							// 取得日
							Date acquisitionDate = getDate(value);
							if (acquisitionDate == null) {
								hasError = true;
								break;
							}
							dto.setAcquisitionDate(acquisitionDate);
						} else if (fieldName.equals(TmdStockHolidayDao.COL_LIMIT_DATE)) {
							// 期限日
							Date limitDate = getDate(value);
							if (limitDate == null) {
								hasError = true;
								break;
							}
							dto.setLimitDate(getDate(value));
						} else if (fieldName.equals(TmdStockHolidayDao.COL_HOLD_DAY)) {
							// 保有日数
							double holdDay = 0;
							try {
								holdDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holdDay < 0) {
								hasError = true;
								break;
							}
							dto.setHoldDay(holdDay);
						} else if (fieldName.equals(TmdStockHolidayDao.COL_GIVING_DAY)) {
							// 付与日数
							double givingDay = 0;
							try {
								givingDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (givingDay < 0) {
								hasError = true;
								break;
							}
							dto.setGivingDay(givingDay);
						} else if (fieldName.equals(TmdStockHolidayDao.COL_CANCEL_DAY)) {
							// 廃棄日数
							double cancelDay = 0;
							try {
								cancelDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (cancelDay < 0) {
								hasError = true;
								break;
							}
							dto.setCancelDay(cancelDay);
						} else if (fieldName.equals(TmdStockHolidayDao.COL_USE_DAY)) {
							// 使用日数
							double useDay = 0;
							try {
								useDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (useDay < 0) {
								hasError = true;
								break;
							}
							dto.setUseDay(useDay);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getActivateDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getActivateDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					// ファイル内重複チェック
					for (StockHolidayDataDtoInterface stockHolidayDataDto : stockHolidayDataList) {
						if (stockHolidayDataDto.getPersonalId().equals(dto.getPersonalId())
								&& stockHolidayDataDto.getActivateDate().equals(dto.getActivateDate())
								&& stockHolidayDataDto.getAcquisitionDate().equals(dto.getAcquisitionDate())) {
							addDuplicateDataErrorMessage(i);
							hasError = true;
							break;
						}
					}
				}
				if (!hasError) {
					StockHolidayDataDtoInterface stockHolidayDataDto = stockHolidayDataDao.findForKey(
							dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate());
					if (stockHolidayDataDto != null) {
						dto.setTmdStockHolidayId(stockHolidayDataDto.getTmdStockHolidayId());
					}
				}
				if (!hasError) {
					stockHolidayDataList.add(dto);
				}
			}
			i++;
		}
		return stockHolidayDataList;
	}
	
	@Override
	public List<TimelyPaidHolidayDtoInterface> getTimelyPaidHolidayList(String importCode, List<String[]> list)
			throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<TimelyPaidHolidayDtoInterface> timelyPaidHolidayList = new ArrayList<TimelyPaidHolidayDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有り場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				TimelyPaidHolidayDtoInterface dto = new TmdTimelyPaidHolidayDto();
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (fieldName.equals(PfmHumanDao.COL_EMPLOYEE_CODE)) {
							// 社員コード
							employeeCode = value;
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_ACTIVATE_DATE)) {
							// 有効日
							Date activateDate = getDate(value);
							if (activateDate == null) {
								hasError = true;
								break;
							}
							dto.setActivateDate(activateDate);
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_ACQUISITION_DATE)) {
							// 取得日
							Date acquisitionDate = getDate(value);
							if (acquisitionDate == null) {
								hasError = true;
								break;
							}
							dto.setAcquisitionDate(acquisitionDate);
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_POSSIBLE_HOUR)) {
							// 利用可能時間数
							int possibleHour = 0;
							try {
								possibleHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (possibleHour < 0) {
								hasError = true;
								break;
							}
							dto.setPossibleHour(possibleHour);
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_GIVING_HOUR)) {
							// 付与時間数
							int givingHour = 0;
							try {
								givingHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (givingHour < 0) {
								hasError = true;
								break;
							}
							dto.setGivingHour(givingHour);
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_CANCEL_HOUR)) {
							// 廃棄時間数
							int cancelHour = 0;
							try {
								cancelHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (cancelHour < 0) {
								hasError = true;
								break;
							}
							dto.setCancelHour(cancelHour);
						} else if (fieldName.equals(TmdTimelyPaidHolidayDao.COL_USE_HOUR)) {
							// 使用時間数
							int useHour = 0;
							try {
								useHour = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (useHour < 0) {
								hasError = true;
								break;
							}
							dto.setUseHour(useHour);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getActivateDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getActivateDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					if (timelyPaidHolidayDao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
							dto.getAcquisitionDate()) == null) {
						// ファイル内重複チェック
						for (TimelyPaidHolidayDtoInterface timelyPaidHolidayDataDto : timelyPaidHolidayList) {
							if (timelyPaidHolidayDataDto.getPersonalId().equals(dto.getPersonalId())
									&& timelyPaidHolidayDataDto.getActivateDate().equals(dto.getActivateDate())
									&& timelyPaidHolidayDataDto.getAcquisitionDate().equals(dto.getAcquisitionDate())) {
								addDuplicateDataErrorMessage(i);
								hasError = true;
								break;
							}
						}
					} else {
						addAlreadyRegisteredDataErrorMessage(i);
						hasError = true;
					}
				}
				if (!hasError) {
					timelyPaidHolidayList.add(dto);
				}
			}
			i++;
		}
		return timelyPaidHolidayList;
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidayDataList(String importCode, List<String[]> list)
			throws MospException {
		ImportDtoInterface importDto = importDao.findForKey(importCode);
		if (importDto == null) {
			return null;
		}
		List<ImportFieldDtoInterface> importFieldDtoList = importFieldDao.findForList(importCode);
		if (importFieldDtoList == null || importFieldDtoList.isEmpty()) {
			return null;
		}
		List<HolidayDataDtoInterface> holidayDataList = new ArrayList<HolidayDataDtoInterface>();
		int i = 0;
		for (String[] csvArray : list) {
			if (importDto.getHeader() == 1 && i == 0) {
				// ヘッダが有り場合
				if (!checkHeader(importDto, importFieldDtoList, csvArray)) {
					// ヘッダの形式が不正の場合
					addInvalidHeaderErrorMessage();
					return null;
				}
			} else {
				boolean hasError = false;
				String employeeCode = "";
				HolidayDataDtoInterface dto = new TmdHolidayDataDto();
				for (ImportFieldDtoInterface importFieldDto : importFieldDtoList) {
					int fieldOrder = importFieldDto.getFieldOrder();
					if (csvArray.length > fieldOrder - 1) {
						String value = csvArray[fieldOrder - 1];
						String fieldName = importFieldDto.getFieldName();
						if (PfmHumanDao.COL_EMPLOYEE_CODE.equals(fieldName)) {
							// 社員コード
							employeeCode = value;
						} else if (TmdHolidayDataDao.COL_ACTIVATE_DATE.equals(fieldName)) {
							// 有効日
							Date activateDate = getDate(value);
							if (activateDate == null) {
								hasError = true;
								break;
							}
							dto.setActivateDate(activateDate);
						} else if (TmdHolidayDataDao.COL_HOLIDAY_CODE.equals(fieldName)) {
							// 休暇コード
							dto.setHolidayCode(value);
						} else if (TmdHolidayDataDao.COL_HOLIDAY_TYPE.equals(fieldName)) {
							// 休暇区分
							int holidayType = 0;
							try {
								holidayType = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holidayType != 2 && holidayType != 3 && holidayType != 4) {
								hasError = true;
								break;
							}
							dto.setHolidayType(holidayType);
						} else if (TmdHolidayDataDao.COL_GIVING_DAY.equals(fieldName)) {
							// 付与日数
							double givingDay = 0;
							try {
								givingDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (givingDay < 0) {
								hasError = true;
								break;
							}
							dto.setGivingDay(givingDay);
						} else if (TmdHolidayDataDao.COL_CANCEL_DAY.equals(fieldName)) {
							// 廃棄日数
							double cancelDay = 0;
							try {
								cancelDay = Double.parseDouble(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (cancelDay < 0) {
								hasError = true;
								break;
							}
							dto.setCancelDay(cancelDay);
						} else if (TmdHolidayDataDao.COL_HOLIDAY_LIMIT_DATE.equals(fieldName)) {
							// 取得期限
							Date holidayLimitDate = getDate(value);
							if (holidayLimitDate == null) {
								hasError = true;
								break;
							}
							dto.setHolidayLimitDate(holidayLimitDate);
						} else if (TmdHolidayDataDao.COL_HOLIDAY_LIMIT_MONTH.equals(fieldName)) {
							// 取得期限(月)
							int holidayLimitMonth = 0;
							try {
								holidayLimitMonth = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holidayLimitMonth < 0) {
								hasError = true;
								break;
							}
							dto.setHolidayLimitMonth(holidayLimitMonth);
						} else if (TmdHolidayDataDao.COL_HOLIDAY_LIMIT_DAY.equals(fieldName)) {
							// 取得期限(日)
							int holidayLimitDay = 0;
							try {
								holidayLimitDay = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								hasError = true;
								break;
							}
							if (holidayLimitDay < 0) {
								hasError = true;
								break;
							}
							dto.setHolidayLimitDay(holidayLimitDay);
						}
					}
				}
				if (!hasError && employeeCode.isEmpty()) {
					hasError = true;
				}
				if (!hasError && dto.getActivateDate() == null) {
					hasError = true;
				}
				if (!hasError) {
					HumanDtoInterface humanDto = humanDao.findForEmployeeCode(employeeCode, dto.getActivateDate());
					if (humanDto == null || humanDto.getPersonalId() == null || humanDto.getPersonalId().isEmpty()) {
						hasError = true;
					} else {
						// 個人ID
						dto.setPersonalId(humanDto.getPersonalId());
					}
				}
				if (!hasError) {
					if (dto.getHolidayCode() == null || dto.getHolidayCode().isEmpty()) {
						hasError = true;
					} else {
						HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayCode(),
								dto.getActivateDate(), dto.getHolidayType());
						if (holidayDto == null) {
							hasError = true;
						}
					}
				}
				if (!hasError) {
					if (dto.getHolidayLimitMonth() == 0 && dto.getHolidayLimitDay() == 0) {
						// 0月0日の場合は取得期限を5874897年12月31日とする
						dto.setHolidayLimitDate(TimeUtility.getUnlimitedDate());
					} else {
						dto.setHolidayLimitDate(DateUtility.addDay(
								DateUtility.addMonth(dto.getActivateDate(), dto.getHolidayLimitMonth()),
								dto.getHolidayLimitDay() - 1));
					}
				}
				if (hasError) {
					addInvalidDataErrorMessage(i);
				} else {
					// ファイル内重複チェック
					for (HolidayDataDtoInterface holidayDataDto : holidayDataList) {
						if (holidayDataDto.getPersonalId().equals(dto.getPersonalId())
								&& holidayDataDto.getActivateDate().equals(dto.getActivateDate())
								&& holidayDataDto.getHolidayCode().equals(dto.getHolidayCode())
								&& holidayDataDto.getHolidayType() == dto.getHolidayType()) {
							addDuplicateDataErrorMessage(i);
							hasError = true;
							break;
						}
					}
				}
				if (!hasError) {
					HolidayDataDtoInterface holidayDataDto = holidayDataDao.findForKey(dto.getPersonalId(),
							dto.getActivateDate(), dto.getHolidayCode(), dto.getHolidayType());
					if (holidayDataDto != null) {
						dto.setTmdHolidayId(holidayDataDto.getTmdHolidayId());
					}
				}
				if (!hasError) {
					holidayDataList.add(dto);
				}
			}
			i++;
		}
		return holidayDataList;
	}
	
	/**
	 * ヘッダチェック。
	 * @param importDto インポートDTO
	 * @param list インポートフィールドDTO
	 * @param array ヘッダ配列
	 * @return true：ヘッダ正常、false：ヘッダ異常
	 */
	private boolean checkHeader(ImportDtoInterface importDto, List<ImportFieldDtoInterface> list, String[] array) {
		if (importDto == null) {
			return false;
		}
		if (list == null || list.isEmpty()) {
			return false;
		}
		String[] headerArray = new String[list.size()];
		int i = 0;
		for (ImportFieldDtoInterface importFieldDto : list) {
			headerArray[i] = mospParams.getProperties().getCodeItemName(importDto.getImportTable(),
					importFieldDto.getFieldName());
			i++;
		}
		return Arrays.equals(headerArray, array);
	}
	
	/**
	 * 入力された日付のスラッシュの有無に限らずDateに変換する。
	 * @param date 入力された日付
	 * @return データ型日付
	 */
	private Date getDate(String date) {
		if (date.indexOf("/") == -1) {
			return DateUtility.getDate(date, "yyyyMMdd");
		}
		return DateUtility.getDate(date);
	}
	
	/**
	 * 入力された打刻日付のスラッシュの有無に限らずDateに変換する。
	 * @param timestamp 入力された打刻日付
	 * @return データ型日付
	 */
	private Date getTimestamp(String timestamp) {
		if (timestamp.indexOf("/") == -1) {
			return DateUtility.getDate(timestamp, "yyyyMMdd H:m");
		}
		return DateUtility.getDate(timestamp, "y/M/d H:m");
	}
	
	/**
	 * 最大長チェック。
	 * @param target 対象文字列
	 * @param maxLength 最大長
	 * @return true：最大長の範囲内の場合、false：最大長を超える場合
	 */
	private boolean checkMaxLength(String target, int maxLength) {
		return target.length() <= maxLength;
	}
	
	private void addInvalidHeaderErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_FORM_INJUSTICE, mospParams.getName("Header"));
	}
	
	private void addInvalidDataErrorMessage(int i) {
		String rep = ++i + mospParams.getName("TheLine", "Of", "Data");
		mospParams.addErrorMessage(TimeMessageConst.MSG_FORM_INJUSTICE, rep);
	}
	
	private void addDuplicateDataErrorMessage(int i) {
		String rep = ++i + mospParams.getName("TheLine", "Of", "Data");
		mospParams.addErrorMessage(TimeMessageConst.MSG_FILE_REPETITION, rep);
	}
	
	private void addAlreadyRegisteredDataErrorMessage(int i) {
		String rep = ++i + mospParams.getName("TheLine", "Of", "Data");
		mospParams.addErrorMessage(TimeMessageConst.MSG_ALREADY_EXIST, rep);
	}
	
}
