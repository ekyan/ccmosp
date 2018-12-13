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

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.impl.TmdHolidayRequestDao;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請インポートクラス。<br>
 */
public class HolidayRequestImportBean extends PlatformFileBean implements ImportBeanInterface {
	
	/**
	 * 休暇申請登録クラス。<br>
	 */
	protected HolidayRequestRegistBeanInterface			holidayRequestRegist;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface				workflowRegist;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public HolidayRequestImportBean() {
		super();
	}
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public HolidayRequestImportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		holidayRequestRegist = (HolidayRequestRegistBeanInterface)createBean(HolidayRequestRegistBeanInterface.class);
		attendanceTransactionRegist = (AttendanceTransactionRegistBeanInterface)createBean(
				AttendanceTransactionRegistBeanInterface.class);
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポートフィールド情報リストを取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 社員コードを個人IDに変換
		convertEmployeeCodeIntoPersonalId(fieldList, dataList, TmdHolidayRequestDao.COL_REQUEST_START_DATE,
				getNameActivateDate());
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポート処理
		return importFile(fieldList, dataList);
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * 休暇申請情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList 登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException {
		int count = 0;
		for (String[] data : dataList) {
			// インポート
			count += importData(fieldList, data);
		}
		// 登録件数取得
		return count;
	}
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importData(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		checkDate(fieldList, data);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		int holidayRange = getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_RANGE, fieldList, data);
		int holidayType1 = getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE1, fieldList, data);
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 有給休暇・ストック休暇の場合
			String holidayType2 = getFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE2, fieldList, data);
			
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holidayType2)) {
				// 有給休暇の場合
				
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 全休・午前休・午後休の場合
					return paidLeaveImport(fieldList, data);
				} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					// 時間休の場合
					return paidLeaveByTheHourImport(fieldList, data);
				}
				return 0;
			} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(holidayType2)) {
				// ストック休暇の場合
				return stockLeaveImport(fieldList, data);
			}
			return 0;
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇・その他休暇・欠勤の場合
			// 全休・午前休・午後休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 全休・午前休・午後休の場合
				return leaveImport(fieldList, data);
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				return leaveByTheHourImport(fieldList, data);
			}
			return 0;
		}
		return 0;
	}
	
	/**
	 * インポート処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void importData(HolidayRequestDtoInterface dto) throws MospException {
		// 申請の相関チェック
		holidayRequestRegist.checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_VACATION);
		}
		// 自己承認設定
		workflowRegist.setSelfApproval(workflowDto);
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestStartDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 休暇申請
			holidayRequestRegist.regist(dto);
			// 勤怠データ削除
			holidayRequestRegist.deleteAttendance(dto);
			// 勤怠データ下書
			holidayRequestRegist.draftAttendance(dto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(dto);
		}
	}
	
	/**
	 * 有給休暇申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int paidLeaveImport(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		String personalId = getFieldValue(TmdHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		if (requestEndDate == null) {
			requestEndDate = requestStartDate;
		}
		int count = 0;
		List<Date> dateList = TimeUtility.getDateList(requestStartDate, requestEndDate);
		for (Date date : dateList) {
			String workTypeCode = holidayRequestRegist.getScheduledWorkTypeCode(personalId, date);
			holidayRequestRegist.checkWorkType(requestStartDate, requestEndDate, date, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				return count;
			}
			if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
					|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			HolidayRequestDtoInterface dto = getHolidayRequestDto(fieldList, data, date, date, date, date);
			holidayRequestRegist.setHolidayRequest(dto);
			importData(dto);
			if (mospParams.hasErrorMessage()) {
				return count;
			}
			count++;
		}
		return count;
	}
	
	/**
	 * 有給休暇(時間休)申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int paidLeaveByTheHourImport(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		String personalId = getFieldValue(TmdHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date startTime = getTimestampFieldValue(TmdHolidayRequestDao.COL_START_TIME, fieldList, data);
		if (startTime == null) {
			// エラーメッセージ追加
			addRequiredErrorMessage(mospParams.getProperties().getCodeItemName(
					TimeFileConst.CODE_IMPORT_TYPE_TMD_HOLIDAY_REQUEST, TmdHolidayRequestDao.COL_START_TIME), null);
			return 0;
		}
		Date endTime = DateUtility.addHour(startTime, 1);
		Date date = DateUtility.getDate(DateUtility.getYear(startTime), DateUtility.getMonth(startTime),
				DateUtility.getDay(startTime));
		String workTypeCode = holidayRequestRegist.getScheduledWorkTypeCode(personalId, date);
		holidayRequestRegist.checkWorkType(date, date, date, workTypeCode);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return 0;
		}
		HolidayRequestDtoInterface dto = getHolidayRequestDto(fieldList, data, date, date, startTime, endTime);
		holidayRequestRegist.setHolidayRequest(dto);
		importData(dto);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * ストック休暇申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int stockLeaveImport(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		String personalId = getFieldValue(TmdHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		if (requestEndDate == null) {
			requestEndDate = requestStartDate;
		}
		int count = 0;
		List<Date> dateList = TimeUtility.getDateList(requestStartDate, requestEndDate);
		for (Date date : dateList) {
			// 勤務形態コード取得
			String workTypeCode = holidayRequestRegist.getScheduledWorkTypeCode(personalId, date);
			holidayRequestRegist.checkWorkType(requestStartDate, requestEndDate, date, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				return count;
			}
			if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
					|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			HolidayRequestDtoInterface dto = getHolidayRequestDto(fieldList, data, date, date, date, date);
			holidayRequestRegist.setHolidayRequest(dto);
			importData(dto);
			if (mospParams.hasErrorMessage()) {
				return count;
			}
			count++;
		}
		return count;
	}
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int leaveImport(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		int holidayRange = getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_RANGE, fieldList, data);
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		if (requestEndDate == null) {
			requestEndDate = requestStartDate;
		}
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			requestEndDate = requestStartDate;
		}
		HolidayRequestDtoInterface dto = getHolidayRequestDto(fieldList, data, requestStartDate, requestEndDate,
				requestStartDate, requestStartDate);
		holidayRequestRegist.setHolidayRequest(dto);
		importData(dto);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 休暇(時間休)申請情報リストを取得する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int leaveByTheHourImport(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		Date startTime = getTimestampFieldValue(TmdHolidayRequestDao.COL_START_TIME, fieldList, data);
		if (startTime == null) {
			// エラーメッセージ追加
			addRequiredErrorMessage(mospParams.getProperties().getCodeItemName(
					TimeFileConst.CODE_IMPORT_TYPE_TMD_HOLIDAY_REQUEST, TmdHolidayRequestDao.COL_START_TIME), null);
			return 0;
		}
		Date endTime = DateUtility.addHour(startTime, 1);
		Date date = DateUtility.getDate(DateUtility.getYear(startTime), DateUtility.getMonth(startTime),
				DateUtility.getDay(startTime));
		HolidayRequestDtoInterface dto = getHolidayRequestDto(fieldList, data, date, date, startTime, endTime);
		holidayRequestRegist.setHolidayRequest(dto);
		importData(dto);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * インポートフィールド情報リストに従い、
	 * 登録情報の内容を休暇申請情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate 申請終了日
	 * @param startTime 時休開始時刻
	 * @param endTime 時休終了時刻
	 * @return 休暇申請情報(DTO)
	 */
	protected HolidayRequestDtoInterface getHolidayRequestDto(List<ImportFieldDtoInterface> fieldList, String[] data,
			Date requestStartDate, Date requestEndDate, Date startTime, Date endTime) {
		HolidayRequestDtoInterface dto = holidayRequestRegist.getInitDto();
		dto.setPersonalId(getFieldValue(TmdHolidayRequestDao.COL_PERSONAL_ID, fieldList, data));
		dto.setRequestStartDate(requestStartDate);
		dto.setRequestEndDate(requestEndDate);
		dto.setHolidayType1(getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE1, fieldList, data));
		dto.setHolidayType2(getFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE2, fieldList, data));
		dto.setHolidayRange(getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_RANGE, fieldList, data));
		dto.setStartTime(startTime);
		dto.setEndTime(endTime);
		dto.setRequestReason(getFieldValue(TmdHolidayRequestDao.COL_REQUEST_REASON, fieldList, data));
		return dto;
	}
	
	/**
	 * 登録情報からフィールド(日付)に対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 値(日付)
	 */
	protected Date getTimestampFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から日付文字列を取得
		String date = getFieldValue(fieldName, fieldList, data);
		// 日付文字列確認
		if (date == null || date.isEmpty()) {
			return null;
		}
		// 日付取得
		return getTimestamp(date);
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。
	 * @param date 日付文字列(yyyy/MM/dd HH:mm)
	 * @return 日付
	 */
	protected Date getTimestamp(String date) {
		return DateUtility.getDate(date, "yyyy/MM/dd HH:mm");
	}
	
	/**
	 * 日付チェック。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 */
	protected void checkDate(List<ImportFieldDtoInterface> fieldList, String[] data) {
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		if (requestStartDate == null) {
			addDateErrorMessage();
			return;
		}
		if (checkDateOrder(requestStartDate, requestEndDate, true)) {
			return;
		}
		addInvalidOrderMessage(mospParams.getName("Vacation", "Start", "Day"),
				mospParams.getName("Vacation", "End", "Day"));
	}
	
	/**
	 * 日付エラーメッセージを追加する。<br>
	 */
	protected void addDateErrorMessage() {
		mospParams.addMessage(PlatformMessageConst.MSG_INPUT, mospParams.getName("Period"));
	}
	
	/**
	 * 休暇種別エラーメッセージを追加する。<br>
	 */
	protected void addHolidayTypeErrorMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Vacation", "Classification"));
	}
	
	/**
	 * 休暇が付与されていない場合のエラーメッセージを追加する。<br>
	 * @param holidayName 休暇名称
	 */
	protected void addHolidayNotGrantErrorMessage(String holidayName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NOT_GIVE, holidayName);
	}
	
}
