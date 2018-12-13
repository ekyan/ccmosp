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
package jp.mosp.time.portal.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * ログイン直後ポータル画面表示時に勤怠未入力確認クラス。
 */
public class PortalAttendanceCheckBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * 入社日参照クラス。
	 */
	EntranceReferenceBeanInterface						entranceRefer;
	
	/**
	 * 勤怠集計参照クラス。
	 */
	TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeRefer;
	
	/**
	 * 締日ユーティリティクラス。
	 */
	CutoffUtilBeanInterface								cutoffUtill;
	
	/**
	 * 勤怠未入力確認用に勤怠一覧情報リスト
	 */
	AttendanceListReferenceBeanInterface				attendanceList;
	
	/**
	 * パス(ポータル用勤怠未入力確認機能JSP)。
	 */
	protected static final String						PATH_PORTAL_CHECK_TIME_VIEW		= "/jsp/time/portal/portalAttendanceCheck.jsp";
	
	/**
	 * 勤怠未入力時の確認メッセージ。
	 */
	public static final String							MSG_NO_APPLI_TIME_WORK			= "TMW0287";
	
	/**
	 * 確認メッセージ。
	 */
	public static final String							MSG_CONFIRM						= "TMI0003";
	
	/**
	 * ポータルパラメータキー(ログイン直後メッセージ)。
	 */
	public static final String							PRM_ATTENDANCE_CHECK_MESSAGE	= "prmAttendanceCheckMessage";
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalAttendanceCheckBean() {
		super();
	}
	
	/**
	 * {@link PortalBean#PortalBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected PortalAttendanceCheckBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
		entranceRefer = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		cutoffUtill = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		totalTimeEmployeeRefer = (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(
				TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		attendanceList = (AttendanceListReferenceBeanInterface)createBean(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	public void show() throws MospException {
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_CHECK_TIME_VIEW);
		// 個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付前日取得
		Date yesterday = DateUtility.addDay(getSystemDate(), -1);
		// 期間開始日取得
		Date startDate = setStartDate(personalId, yesterday);
		// 期間開始日がないまたは開始日が終了日より後の場合
		if (startDate == null || startDate.after(yesterday)) {
			// エラーメッセージクリア
			mospParams.getErrorMessageList().clear();
			return;
		}
		// 勤怠未入力の日付リスト
		List<Date> errorList = attendanceList.getNotAttendanceAppliList(personalId, startDate, yesterday);
		// メッセージ設定
		addErrorMessageNotTimeAppli(errorList);
		// エラーメッセージクリア
		mospParams.getErrorMessageList().clear();
	}
	
	/**
	 * 期間開始日に締期間初日を設定する。
	 * 勤怠集計データが無く、締め期間初日からシステム日付前日までに
	 * 入社日があった場合、入社日を設定する。
	 * @param personalId 対象個人ID
	 * @param yesterday システム日付前日
	 * @return 期間開始日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date setStartDate(String personalId, Date yesterday) throws MospException {
		// システム日付取得
		Date today = getSystemDate();
		// 入社日取得
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = cutoffUtill.getTimeSetting(personalId, today);
		// 勤怠管理対象でない場合
		if (timeSettingDto == null || timeSettingDto.getTimeManagementFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// 処理なし
			return null;
		}
		// 締日情報取得
		CutoffDtoInterface cutoffDto = cutoffUtill.getCutoff(timeSettingDto.getCutoffCode(), today);
		// 締日情報確認
		if (cutoffDto == null) {
			// 処理なし
			return null;
		}
		// 締め状態の勤怠集計データ取得
		TotalTimeEmployeeDtoInterface totalTimeEmployeeDto = totalTimeEmployeeRefer.findForPersonalList(personalId,
				TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		// 勤怠集計データが存在する場合
		if (totalTimeEmployeeDto != null) {
			// 勤怠集計データ年月取得
			int totalTimeYear = totalTimeEmployeeDto.getCalculationYear();
			int totalTimeMounth = totalTimeEmployeeDto.getCalculationMonth();
			// 締め期間終了日の次の日を期間初日に設定
			Date startDate = DateUtility
				.addDay(TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), totalTimeYear, totalTimeMounth), 1);
			// 入社日が締め期間終了日の次の日を期間初日より遅い場合
			if (entranceDate.after(startDate)) {
				// 入社日を締期間初日に設定
				return entranceDate;
			}
			return startDate;
		}
		// 対象年月日が含まれる締月を取得
		Date cutoffDate = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), today);
		// システム日付年月取得
		int targetYear = DateUtility.getYear(cutoffDate);
		int targetMounth = DateUtility.getMonth(cutoffDate);
		// 締期間初日取得
		Date startDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), targetYear, targetMounth);
		// 締期間初日からシステム日付前日までの期間に入社日が含まれている
		boolean isTermEmploy = DateUtility.isTermContain(entranceDate, startDate, yesterday);
		// 含まれている場合
		if (isTermEmploy) {
			// 期間初日を入社日に設定
			return entranceDate;
		}
		return startDate;
	}
	
	/**
	 * エラーメッセージを作成しポータルパラメータに設定する。
	 * @param errorDateList エラー日付リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addErrorMessageNotTimeAppli(List<Date> errorDateList) throws MospException {
		// エラー日付リストが空の場合
		if (errorDateList.isEmpty()) {
			return;
		}
		// メッセージ準備
		StringBuffer sb = new StringBuffer();
		// エラー日付毎に処理
		for (int i = 0; i < errorDateList.size(); i++) {
			// メッセージ設定
			String[] rep = { DateUtility.getStringDate(errorDateList.get(i)) };
			sb.append(mospParams.getMessage(MSG_NO_APPLI_TIME_WORK, rep));
			sb.append("\\\\n");
			// 10件以上の場合
			if (i == 9 && errorDateList.size() > 10) {
				// 他○件設定
				sb.append(mospParams.getName("Other"));
				sb.append(errorDateList.size() - (i + 1));
				sb.append(mospParams.getName("Count"));
				sb.append("\\\\n");
				break;
			}
		}
		// 置換文字に設定
		String[] rep = { sb.toString() };
		// パラメータに設定
		mospParams.addGeneralParam(PRM_ATTENDANCE_CHECK_MESSAGE, mospParams.getMessage(MSG_CONFIRM, rep));
	}
	
	@Override
	public void regist() {
		// 処理なし		
	}
}
