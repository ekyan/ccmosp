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
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.impl.HumanSearchBean;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.SubordinateListDto;

/**
 * 部下一覧検索クラス。
 */
public class SubordinateSearchBean extends HumanSearchBean implements SubordinateSearchBeanInterface {
	
	/**
	 * 限度基準期間(1ヶ月)。
	 */
	public static final String									LIMIT_STANDARD_TERM_MONTH1	= "month1";
	
	/**
	 * 勤怠集計データDAO。
	 */
	private TotalTimeDataDaoInterface							totalTimeDataDao;
	
	/**
	 * 勤怠集計修正情報参照。
	 */
	private TotalTimeCorrectionReferenceBeanInterface			totalTimeCorrection;
	
	/**
	 * 社員勤怠集計管理参照。
	 */
	private TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeTransaction;
	
	/**
	 * 勤怠集計。
	 */
	TotalTimeCalcBeanInterface									totalTimeCalc;
	
	/**
	 * 承認ルート設定適用参照クラス。
	 */
	protected RouteApplicationReferenceBeanInterface			routeApplication;
	
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface					workflowIntegrate;
	
	/**
	 * 勤怠集計管理参照クラス。
	 */
	protected TotalTimeTransactionReferenceBeanInterface		totalTimeTransactionRefer;
	
	/**
	 * 勤怠一覧参照クラス。
	 */
	protected AttendanceListReferenceBeanInterface				attendanceList;
	
	/**
	 * 設定適用管理参照クラス。
	 */
	protected ApplicationReferenceBeanInterface					applicationReference;
	
	/**
	 * 限度基準参照クラス。
	 */
	protected LimitStandardReferenceBeanInterface				limitStandardReference;
	
	/**
	 * 締日ユーティリティクラス。
	 */
	protected CutoffUtilBeanInterface							cutoffUtill;
	
	/**
	 * 対象年。
	 */
	private int													targetYear;
	
	/**
	 * 対象月。
	 */
	private int													targetMonth;
	
	/**
	 * 未承認。
	 */
	private String												approval;
	
	/**
	 * 締状態。
	 */
	private String												calc;
	
	/**
	 * 勤怠未承認
	 */
	private String												CODE_NOT_ATTENDANCE			= "2";
	
	
	/**
	 * コンストラクタ。
	 */
	public SubordinateSearchBean() {
		// 処理無し
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SubordinateSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// 勤怠集計データDAO取得
		totalTimeDataDao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
		// 勤怠集計修正情報参照クラス取得
		totalTimeCorrection = (TotalTimeCorrectionReferenceBeanInterface)createBean(TotalTimeCorrectionReferenceBeanInterface.class);
		// 謝金勤怠集計管理参照クラス取得
		totalTimeEmployeeTransaction = (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		// 勤怠集計クラス取得
		totalTimeCalc = (TotalTimeCalcBeanInterface)createBean(TotalTimeCalcBeanInterface.class);
		// 承認ルート設定適用参照クラス取得
		routeApplication = (RouteApplicationReferenceBeanInterface)createBean(RouteApplicationReferenceBeanInterface.class);
		// ワークフロー統合クラス取得
		workflowIntegrate = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		// 勤怠集計管理参照クラス取得
		totalTimeTransactionRefer = (TotalTimeTransactionReferenceBeanInterface)createBean(TotalTimeTransactionReferenceBeanInterface.class);
		// 勤怠一覧参照クラス取得
		attendanceList = (AttendanceListReferenceBeanInterface)createBean(AttendanceListReferenceBeanInterface.class);
		// 設定適用管理参照クラス取得
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		// 限度基準参照クラス取得
		limitStandardReference = (LimitStandardReferenceBeanInterface)createBean(LimitStandardReferenceBeanInterface.class);
		// 締日ユーティリティクラス取得
		cutoffUtill = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
	}
	
	@Override
	public List<SubordinateListDtoInterface> getSubordinateList() throws MospException {
		// 検索条件(部下)設定
		setSubordinateParams();
		// 検索条件設定(検索区分(社員コード))
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 部下一覧情報リスト取得
		return getSubordinateList(search());
	}
	
	@Override
	public Set<String> getSubordinateIdSet() throws MospException {
		// 検索条件(部下)設定
		setSubordinateParams();
		// 検索条件設定(検索区分(社員コード))
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 部下個人IDセット取得
		return getPersonalIdSet();
	}
	
	/**
	 * 検索条件(部下)を設定する。<br>
	 */
	protected void setSubordinateParams() {
		// 検索条件設定(休退職区分)
		setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否)
		setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		setOperationType(MospConst.OPERATION_TYPE_REFER);
	}
	
	@Override
	public List<SubordinateListDtoInterface> getApprovableList() throws MospException {
		/* THINK 承認者から被承認社員を取得する処理について
		 * 
		 * 承認者から被承認社員を取得する処理は、承認ルート設定適用の仕様上、コストがかかる。
		 * 
		 * 承認ルート設定適用は、被承認者が設定されている承認ルートを特定するためのものであり、
		 * 承認適用設定情報から被承認者を取得することは困難である。
		 * 
		 * 承認ルート設定適用は、個人或いはマスタ(勤務地、雇用契約、所属、職位)の組合せで、
		 * 被承認者の承認ルートを特定するものである。
		 * 承認ルートを特定するにあたり、優先順位が存在する。
		 * その優先順位に沿って確認が行われ、被承認者の人事基本情報に最初に合致した
		 * 承認ルート設定適用が用いられることになる。
		 * 
		 * そのため、承認ルート設定適用から被承認者を取得しようとした場合、
		 * その承認ルート設定適用に合致する社員を取得するだけでなく、その承認ルート設定適用
		 * より優先順位の高い承認ルート設定適用に合致する社員を除かなくてはならない。
		 * 例えば、マスタの組合せで全て空白の承認ルート設定適用があった場合、登録されている
		 * 全ての承認ルート設定適用に対して合致する社員を取得しそれらを除かなくてはならない。
		 * 
		 * 当メソッドでは、検索条件に合致する社員一人一人に対して、承認ルート設定適用情報を取得
		 * し、承認者がユニットとして登録されているルートが適用される承認ルート設定適用情報と
		 * 合致するかで判断することとする。
		 * この方法でもコストがかかることに変わりは無い。
		 * 検索条件を指定しない場合などは、全社員に対して承認ルート設定適用情報を確認することに
		 * なり、相応のコストが想定される。
		 * 
		 * 利用に堪えないようであれば、承認ルート設定適用から被承認者を取得する方法を試してみる。
		 * それでも改善されない場合は、仕様自体を再検討する必要がある。
		 */
		// 検索条件(部下)設定
		setSubordinateParams();
		// 検索条件設定(検索区分(社員コード))
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 人事情報検索(部下一覧)
		List<HumanDtoInterface> approvableList = search();
		// 検索条件再設定(操作区分)(承認対象者全員が検索対象となるためnullを設定)
		setOperationType(null);
		// 人事情報検索
		List<HumanDtoInterface> humanList = search();
		// 承認者がユニットとして登録されているルートのリストを取得
		Set<String> routeSet = workflowIntegrate.getApproverRouteSet(mospParams.getUser().getPersonalId(), targetDate);
		// 検索人事情報毎に被承認者かどうかを確認して人事情報リストに追加
		for (HumanDtoInterface humanDto : humanList) {
			addApprovable(humanDto, routeSet, approvableList);
		}
		// 部下一覧情報リスト取得
		return getSubordinateList(approvableList);
	}
	
	/**
	 * 人事情報リストを基に、部下一覧情報リストを取得する。<br>
	 * @param humanList 人事情報リスト
	 * @return 部下一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<SubordinateListDtoInterface> getSubordinateList(List<HumanDtoInterface> humanList)
			throws MospException {
		// 部下一覧リスト準備
		List<SubordinateListDtoInterface> subordinateList = new ArrayList<SubordinateListDtoInterface>();
		// 検索結果から部下一覧リストを作成
		for (HumanDtoInterface dto : humanList) {
			TotalTimeDataDtoInterface totalTimeDto = totalTimeDataDao.findForKey(dto.getPersonalId(), targetYear,
					targetMonth);
			SubordinateListDtoInterface subordinateListDto = getSubordinateListDto(dto, targetYear, targetMonth,
					totalTimeDto, approval, calc);
			if (subordinateListDto != null) {
				subordinateList.add(subordinateListDto);
			}
		}
		return subordinateList;
	}
	
	/**
	 * 承認状態表示クラスを取得する。
	 * @param approvalState 承認状態
	 * @return 承認状態表示クラス
	 */
	protected String getApprovalStateClass(int approvalState) {
		// 承認状態確認
		switch (approvalState) {
			case TimeConst.CODE_NOT_APPROVED_NONE:
				// 未承認無しの場合(青文字)
				return TimeConst.CSS_BLUE_LABEL;
			case TimeConst.CODE_NOT_APPROVED_EXIST:
				// 未承認有りの場合(赤文字)
				return TimeConst.CSS_RED_LABEL;
			default:
				return "";
		}
	}
	
	/**
	 * 締日状態クラスを取得する。
	 * @param cutoffState 締日状態
	 * @return 締日状態クラス
	 */
	protected String getCutoffStateClass(int cutoffState) {
		// 締状態確認
		switch (cutoffState) {
			case TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT:
				// 未締の場合(赤文字)
				return TimeConst.CSS_RED_LABEL;
			case TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT:
				// 仮締の場合(青文字)
				return TimeConst.CSS_BLUE_LABEL;
			case TimeConst.CODE_CUTOFF_STATE_TIGHTENED:
				// 確定の場合(黒文字)
				return TimeConst.CSS_BLACK_LABEL;
			default:
				return "";
		}
	}
	
	/**
	 * 対象人事情報の社員が被承認者である場合、被承認者リストに追加する。<br>
	 * 対象人事情報から承認ルート適用設定情報を取得し、そのルートコードが
	 * 承認対象ルートリストに含まれるかで、被承認者であるかを確認する。<br>
	 * @param humanDto       対象人事情報
	 * @param routeSet       承認対象ルートコード群
	 * @param approvableList 被承認者リストルート設定適用情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApprovable(HumanDtoInterface humanDto, Set<String> routeSet,
			List<HumanDtoInterface> approvableList) throws MospException {
		// 被承認者リスト確認
		for (HumanDtoInterface approvable : approvableList) {
			// 既に被承認者リストに存在している場合は処理無し
			if (approvable.getPersonalId().equals(humanDto.getPersonalId())) {
				return;
			}
		}
		// 対象人事情報の社員が適用されている承認ルート適用設定情報を取得
		RouteApplicationDtoInterface targetRouteApplication = workflowIntegrate.findForPerson(humanDto.getPersonalId(),
				targetDate, PlatformConst.WORKFLOW_TYPE_TIME);
		// 承認ルート適用設定情報を確認
		if (isApprovable(targetRouteApplication, routeSet)) {
			// 人事情報リスト(部下一覧)に追加
			approvableList.add(humanDto);
		}
	}
	
	/**
	 * 対象ルート設定適用情報に設定されたルートコードが、
	 * ルート設定適用情報リストの中に含まれているかを確認する。<br>
	 * @param routeApplicationDto 対象ルート設定適用リスト
	 * @param routeSet            ルートコード群
	 * @return 確認結果(true：対象がリストの中に含まれる、false：対象がリストの中に含まれない)
	 */
	protected boolean isApprovable(RouteApplicationDtoInterface routeApplicationDto, Set<String> routeSet) {
		// 対象ルート設定適用情報確認
		if (routeApplicationDto == null) {
			return false;
		}
		return routeSet.contains(routeApplicationDto.getRouteCode());
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	@Override
	public void setCalc(String calc) {
		this.calc = calc;
	}
	
	@Override
	public SubordinateListDtoInterface getSubordinateListDto(HumanDtoInterface humanDto, int year, int month,
			TotalTimeDataDtoInterface totalTimeDataDto, String searchApprovalState, String searchCutoffState)
			throws MospException {
		// 人事情報確認
		if (humanDto == null) {
			return null;
		}
		// 部下一覧情報準備
		SubordinateListDtoInterface dto = new SubordinateListDto();
		// 人事情報設定
		setHuman(dto, humanDto);
		// 承認状態設定
		setApprovalState(dto, year, month);
		// 勤怠未申請の場合
		if (searchApprovalState.equals(CODE_NOT_ATTENDANCE)) {
			// 対象社員の勤怠が未申請でない場合
			if (isAllAttendanceApplied(humanDto.getPersonalId(), year, month)) {
				mospParams.getErrorMessageList().clear();
				return null;
			}
		} else if (!searchApprovalState.isEmpty()
				&& !searchApprovalState.equals(String.valueOf(dto.getApprovalState()))) {
			// 検索条件：承認状態確認
			return null;
		}
		// 締状態設定
		setCutoffState(dto, year, month);
		// 検索条件：締状態確認
		if (!searchCutoffState.isEmpty() && !searchCutoffState.equals(String.valueOf(dto.getCutoffState()))) {
			return null;
		}
		// 勤怠修正情報設定
		setCorrection(dto, year, month);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			mospParams.getErrorMessageList().clear();
			return null;
		}
		// 勤怠集計データ設定
		setTotalTimeData(dto, totalTimeDataDto);
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(humanDto.getPersonalId(),
				targetDate);
		if (applicationDto == null) {
			return dto;
		}
		LimitStandardDtoInterface limitStandardDto = limitStandardReference.getLimitStandardInfo(
				applicationDto.getWorkSettingCode(), targetDate, LIMIT_STANDARD_TERM_MONTH1);
		if (limitStandardDto == null) {
			return dto;
		}
		setLimitStandard(dto, limitStandardDto);
		return dto;
	}
	
	/**
	 * 対象社員が勤怠を申請しているか確認する。
	 * @param personalId 個人ID
	 * @param year 対象年
	 * @param month 対象月
	 * @return 確認結果(true：勤怠が申請済、false：勤怠が未申請のものがある)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isAllAttendanceApplied(String personalId, int year, int month) throws MospException {
		// 対象社員締情報取得
		CutoffDtoInterface cutoffDto = cutoffUtill.getCutoffForPersonalId(personalId, year, month);
		if (mospParams.hasErrorMessage()) {
			return true;
		}
		// 締期間初日取得
		Date firstDate = cutoffUtill.getCutoffFirstDate(cutoffDto.getCutoffCode(), year, month);
		// 締期間最終日取得
		Date lastDate = cutoffUtill.getCutoffLastDate(cutoffDto.getCutoffCode(), year, month);
		if (mospParams.hasErrorMessage()) {
			return true;
		}
		// 勤怠未入力(勤怠入力用チェックボックス有)の日付リストを取得
		List<Date> dateList = attendanceList.getNotAttendanceAppliList(personalId, firstDate, lastDate);
		if (mospParams.hasErrorMessage()) {
			return true;
		}
		// 日付リストがある場合
		if (dateList != null && !dateList.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void setHuman(SubordinateListDtoInterface dto, HumanDtoInterface humanDto) {
		if (humanDto == null) {
			return;
		}
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setSectionCode(humanDto.getSectionCode());
	}
	
	/**
	 * 承認状態を設定する。<br>
	 * @param dto 対象DTO
	 * @param year 年
	 * @param month 月
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApprovalState(SubordinateListDtoInterface dto, int year, int month) throws MospException {
		int approvalStatus = totalTimeCalc.getApprovalStatus(dto.getPersonalId(), year, month);
		// 承認状態取得
		dto.setApprovalState(approvalStatus);
		// 承認状態表示名称設定
		dto.setApproval(getCodeName(approvalStatus, TimeConst.CODE_NOT_APPROVED));
		// 承認状態表示クラス設定
		dto.setApprovalStateClass(getApprovalStateClass(approvalStatus));
	}
	
	/**
	 * 締状態を設定する。<br>
	 * @param dto 対象DTO
	 * @param year 年
	 * @param month 月
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffState(SubordinateListDtoInterface dto, int year, int month) throws MospException {
		// 締状態取得
		int cutoffState = getCutoffState(dto.getPersonalId(), year, month);
		// 締状態設定
		dto.setCutoffState(cutoffState);
		// 締状態表示名称設定
		dto.setCalc(getCodeName(cutoffState, TimeConst.CODE_CUTOFFSTATE));
		// 締状態表示クラス設定
		dto.setCutoffStateClass(getCutoffStateClass(cutoffState));
	}
	
	/**
	 * 勤怠修正情報を設定する。<br>
	 * @param dto 対象DTO
	 * @param year 年
	 * @param month 月
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCorrection(SubordinateListDtoInterface dto, int year, int month) throws MospException {
		// 勤怠集計修正情報取得
		TotalTimeCorrectionDtoInterface totalTimeCorrectionDto = totalTimeCorrection.getLatestTotalTimeCorrectionInfo(
				dto.getPersonalId(), year, month);
		if (totalTimeCorrectionDto == null) {
			dto.setCorrection("");
			return;
		}
		if (totalTimeCorrectionDto.getCorrectionPersonalId().equals(dto.getPersonalId())) {
			dto.setCorrection(mospParams.getName("CorrectionHistory"));
			return;
		}
		dto.setCorrection(mospParams.getName("Other"));
	}
	
	@Override
	public void setTotalTimeData(SubordinateListDtoInterface dto, TotalTimeDataDtoInterface totalTimeDataDto) {
		if (totalTimeDataDto == null) {
			return;
		}
		dto.setWorkDate(totalTimeDataDto.getTimesWorkDate());
		dto.setWorkTime(totalTimeDataDto.getWorkTime());
		dto.setRestTime(totalTimeDataDto.getRestTime());
		dto.setPrivateTime(totalTimeDataDto.getPrivateTime());
		dto.setLateTime(totalTimeDataDto.getLateTime());
		dto.setLeaveEarlyTime(totalTimeDataDto.getLeaveEarlyTime());
		dto.setLateLeaveEarlyTime(totalTimeDataDto.getLateTime() + totalTimeDataDto.getLeaveEarlyTime());
		dto.setOverTimeIn(totalTimeDataDto.getOvertimeIn());
		dto.setOverTimeOut(totalTimeDataDto.getOvertimeOut());
		dto.setWorkOnHolidayTime(totalTimeDataDto.getWorkOnHoliday());
		dto.setLateNightTime(totalTimeDataDto.getLateNight());
		dto.setPaidHoliday(totalTimeDataDto.getTimesPaidHoliday());
		dto.setPaidHolidayHour(totalTimeDataDto.getPaidHolidayHour());
		dto.setTotalSpecialHoliday(totalTimeDataDto.getTotalSpecialHoliday());
		dto.setTotalOtherHoliday(totalTimeDataDto.getTotalOtherHoliday());
		dto.setTimesCompensation(totalTimeDataDto.getTimesCompensation());
		dto.setAllHoliday(totalTimeDataDto.getTotalSpecialHoliday() + totalTimeDataDto.getTotalOtherHoliday()
				+ totalTimeDataDto.getTimesCompensation());
		dto.setAbsence(totalTimeDataDto.getTotalAbsence());
		dto.setTimesWork(totalTimeDataDto.getTimesWork());
		dto.setTimesLate(totalTimeDataDto.getTimesLate());
		dto.setTimesLeaveEarly(totalTimeDataDto.getTimesLeaveEarly());
		dto.setTimesOvertime(totalTimeDataDto.getTimesOvertime());
		dto.setTimesWorkingHoliday(totalTimeDataDto.getTimesWorkingHoliday());
		dto.setTimesLegalHoliday(totalTimeDataDto.getTimesLegalHoliday());
		dto.setTimesSpecificHoliday(totalTimeDataDto.getTimesSpecificHoliday());
		dto.setTimesHolidaySubstitute(totalTimeDataDto.getTimesHolidaySubstitute());
	}
	
	/**
	 * 限度基準情報を設定する。<br>
	 * @param dto 対象DTO
	 * @param limitStandardDto 限度基準マスタDTO
	 */
	protected void setLimitStandard(SubordinateListDtoInterface dto, LimitStandardDtoInterface limitStandardDto) {
		// 処理なし
	}
	
	/**
	 * 締状態を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締状態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getCutoffState(String personalId, int targetYear, int targetMonth) throws MospException {
		// 社員勤怠集計管理情報取得
		TotalTimeEmployeeDtoInterface totalTimeEmployeeDto = totalTimeEmployeeTransaction.findForKey(personalId,
				targetYear, targetMonth);
		// 社員勤怠集計管理情報確認
		if (totalTimeEmployeeDto == null) {
			// 未締
			return TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
		}
		// 勤怠集計管理情報取得
		TotalTimeDtoInterface totalTimeDto = totalTimeTransactionRefer.findForKey(targetYear, targetMonth,
				totalTimeEmployeeDto.getCutoffCode());
		// 勤怠集計管理情報確認
		if (totalTimeDto != null && totalTimeDto.getCutoffState() == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
			return TimeConst.CODE_CUTOFF_STATE_TIGHTENED;
		}
		// 締状態取得
		return totalTimeEmployeeDto.getCutoffState();
	}
}
