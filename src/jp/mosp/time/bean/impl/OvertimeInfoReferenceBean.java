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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 残業情報参照クラス。
 */
public class OvertimeInfoReferenceBean extends TimeApplicationBean implements OvertimeInfoReferenceBeanInterface {
	
	/**
	 * 週の計算の比較基準日数。<br>
	 */
	public static final int					WEEK_CALCULATE_DAY	= -7;
	
	/**
	 * 限度基準マスタDAO。
	 */
	protected LimitStandardDaoInterface		limitStandardDao;
	
	/**
	 * 勤怠データDAO。
	 */
	protected AttendanceDaoInterface		attendanceDao;
	
	/**
	 * 残業申請データDAO。
	 */
	protected OvertimeRequestDaoInterface	overtimeRequestDao;
	
	/**
	 * ワークフローDAO。
	 */
	protected WorkflowDaoInterface			workflowDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public OvertimeInfoReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public OvertimeInfoReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のメソッド実施
		super.initBean();
		limitStandardDao = (LimitStandardDaoInterface)createDao(LimitStandardDaoInterface.class);
		attendanceDao = (AttendanceDaoInterface)createDao(AttendanceDaoInterface.class);
		overtimeRequestDao = (OvertimeRequestDaoInterface)createDao(OvertimeRequestDaoInterface.class);
		workflowDao = (WorkflowDaoInterface)createDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public int getPossibleTime1Week(String personalId) throws MospException {
		// システム日付取得
		Date targrtDate = DateUtility.getSystemDate();
		// 対象個人ID及び対象日付で勤怠設定情報群を取得し設定
		setCutoffSettings(personalId, targrtDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 残業限度基準
		LimitStandardDtoInterface limitStandardDto = limitStandardDao.findForKey(timeSettingDto.getWorkSettingCode(),
				timeSettingDto.getActivateDate(), "week1");
		if (limitStandardDto == null) {
			addSettingApplicationDefectLimitStandardErrorMessage(timeSettingDto.getActivateDate());
			return 0;
		}
		// 起算曜日準備
		Date startDate = getDateClone(targrtDate);
		// 曜日取得-週の起算曜日
		int difference = DateUtility.getDayOfWeek(startDate) - timeSettingDto.getStartWeek();
		if (difference >= 0) {
			while (difference != 0) {
				startDate = DateUtility.addDay(startDate, -1);
				difference--;
			}
		} else {
			while (difference != 0) {
				startDate = DateUtility.addDay(startDate, 1);
				difference++;
			}
			startDate = DateUtility.addDay(startDate, WEEK_CALCULATE_DAY);
		}
		Date endDate = DateUtility.addDay(startDate, 6);
		List<AttendanceDtoInterface> attendanceList = attendanceDao.findForList(personalId, startDate, endDate);
		boolean requestFlag = true;
		int overTimeOut = 0;
		int requestTime = 0;
		Date targetDate = startDate;
		while (!targetDate.after(endDate)) {
			for (AttendanceDtoInterface attendanceDto : attendanceList) {
				if (targetDate.equals(attendanceDto.getWorkDate())) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					overTimeOut += attendanceDto.getOvertimeOut();
					requestFlag = false;
					break;
				}
			}
			if (requestFlag) {
				List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, targetDate);
				for (OvertimeRequestDtoInterface requestDto : list) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					requestTime += requestDto.getRequestTime();
				}
			}
			requestFlag = true;
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		return limitStandardDto.getLimitTime() - overTimeOut - requestTime;
	}
	
	@Override
	public int getPossibleTime1Month(String personalId) throws MospException {
		// システム日付取得
		Date systemDate = DateUtility.getSystemDate();
		// 対象個人ID及び対象日付で勤怠設定情報群を取得し設定
		setCutoffSettings(personalId, systemDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 限度基準
		LimitStandardDtoInterface limitStandardDto = limitStandardDao.findForKey(timeSettingDto.getWorkSettingCode(),
				timeSettingDto.getActivateDate(), "month1");
		if (limitStandardDto == null) {
			addSettingApplicationDefectLimitStandardErrorMessage(timeSettingDto.getActivateDate());
			return 0;
		}
		// 対象日付及び締日から対象年月日が含まれる締月を取得
		Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), systemDate);
		int year = DateUtility.getYear(cutoffMonth);
		int month = DateUtility.getMonth(cutoffMonth);
		// 締期間の初日及び最終日取得
		Date firstDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), year, month);
		Date lastDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), year, month);
		// 締期間の勤怠データリストを取得
		List<AttendanceDtoInterface> attendanceList = attendanceDao.findForList(personalId, firstDate, lastDate);
		boolean requestFlag = true;
		int overTimeOut = 0;
		int requestTime = 0;
		Date targetDate = firstDate;
		while (!targetDate.after(lastDate)) {
			for (AttendanceDtoInterface attendanceDto : attendanceList) {
				if (targetDate.equals(attendanceDto.getWorkDate())) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					overTimeOut += attendanceDto.getOvertimeOut();
					requestFlag = false;
					break;
				}
			}
			if (requestFlag) {
				List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, targetDate);
				for (OvertimeRequestDtoInterface requestDto : list) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					requestTime += requestDto.getRequestTime();
				}
			}
			requestFlag = true;
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		return limitStandardDto.getLimitTime() - overTimeOut - requestTime;
	}
	
}
