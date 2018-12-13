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
package jp.mosp.time.input.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.input.vo.AttendanceCardVo;
import jp.mosp.time.portal.bean.impl.PortalTimeCardBean;
import jp.mosp.time.utils.HolidayUtility;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 日々勤怠の詳細情報確認とその編集、更新を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_SELECT_SHOW_FROM_PORTAL}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_CALC}
 * </li></ul>
 */
public class AttendanceCardAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの日々勤怠の詳細情報表示画面へ遷移する。<br>
	 */
	public static final String	CMD_SHOW							= "TM1200";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 日々勤怠の詳細情報表示画面へ遷移する。<br>
	 */
	public static final String	CMD_SELECT_SHOW						= "TM1201";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 操作者の指定にしたがって表示する対象年月を変更する。
	 * 前月・翌月ボタンといったものは現在表示している年月を基に画面遷移を行う。<br>
	 */
	public static final String	CMD_SEARCH							= "TM1202";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示してからパンくずリスト等で再度遷移する場合は保持していた検索条件で検索を行って再表示する。<br>
	 */
	public static final String	CMD_RE_SEARCH						= "TM1203";
	
	/**
	 * 下書きコマンド。<br>
	 * <br>
	 * 編集した入力欄の内容を勤怠情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String	CMD_DRAFT							= "TM1204";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 編集した入力欄の内容を勤怠情報テーブルに登録し、勤怠申請を行う。<br>
	 */
	public static final String	CMD_APPLI							= "TM1205";
	
	/**
	 * 選択表示コマンド(ポータル画面から遷移)。<br>
	 * <br>
	 * ポータル画面から日々勤怠の詳細情報表示画面へ遷移する。<br>
	 */
	public static final String	CMD_SELECT_SHOW_FROM_PORTAL			= "TM1206";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの削除を行う。<br>
	 * 対象日全ての勤怠情報と勤怠修正情報を削除する。<br>
	 */
	public static final String	CMD_DELETE							= "TM1207";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、対象個人ID、対象日等をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER						= "TM1208";
	
	/**
	 * 勤怠計算コマンド。<br>
	 * <br>
	 * 実行時点で勤怠詳細に入っている情報を元に勤怠を計算を行う。<br>
	 * 計算結果を勤怠詳細に表示するが、データベースには反映されない。<br>
	 */
	public static final String	CMD_CALC							= "TM1226";
	
	/**
	 * 勤怠詳細画面メニューキー。<br>
	 * ポータル画面から遷移した場合に、範囲設定をするために用いる。<br>
	 */
	public static final String	MENU_ATTENDANCE_CARD				= "AttendanceCard";
	
	/**
	 * パラメータID(勤怠詳細追加JSP)。
	 */
	public static final String	PRM_ATTENDANCE_EXTRA_JSP			= "prmAttendanceExtraJsp";
	
	/**
	 * 申請モード(休日承認済)。
	 */
	public static final String	MODE_APPLICATION_COMPLETED_HOLIDAY	= "completedHoliday";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public AttendanceCardAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new AttendanceCardVo();
	}
	
	@Override
	public void action() throws MospException {
		// 追加JSP設定
		setAttendanceExtraJsp();
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW_FROM_PORTAL)) {
			// 選択表示コマンド(ポータル画面から遷移)
			prepareVo(false, false);
			selectFromPortal();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_DRAFT)) {
			// 下書き
			prepareVo();
			draft();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli(true);
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_CALC)) {
			// 計算
			prepareVo();
			appli(false);
		}
	}
	
	/**
	 * 勤怠詳細追加JSPを設定する。
	 */
	protected void setAttendanceExtraJsp() {
		// アドオン機能などで処理
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// ログインユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付を取得
		Date targetDate = getSystemDate();
		// 勤怠詳細情報設定
		setAttendaneCardInfo(personalId, targetDate);
		// プルダウンの設定
		setPulldown();
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		Date targetDate = getTargetDate();
		// 個人ID確認
		if (personalId == null || personalId.isEmpty()) {
			// ログインユーザの個人IDを取得
			personalId = mospParams.getUser().getPersonalId();
		}
		// 対象日確認
		if (targetDate == null) {
			// 譲渡有効日を取得
			targetDate = getDate(getTransferredActivateDate());
		}
		// プルダウンの設定
		setPulldown();
		// 勤怠詳細情報設定
		setAttendaneCardInfo(personalId, targetDate);
	}
	
	/**
	 * 選択表示処理(ポータルから遷移)を行う。<br>
	 * <br>
	 * 範囲設定確認をする。<br>
	 * 選択表示処理に影響がないように、エラーメッセージの退避及び戻しを行う。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void selectFromPortal() throws MospException {
		// 範囲設定確認(ポータル画面から遷移してくる場合に必要)
		checkRangeMap(MENU_ATTENDANCE_CARD);
		// エラーメッセージ取得
		List<String> errorMessageList = mospParams.getErrorMessageList();
		// エラーメッセージ退避
		List<String> back = new ArrayList<String>();
		back.addAll(errorMessageList);
		// エラーメッセージ初期化
		errorMessageList.clear();
		// 選択表示処理
		select();
		// エラーメッセージ戻し
		errorMessageList = mospParams.getErrorMessageList();
		errorMessageList.addAll(0, back);
	}
	
	/**
	 * 日付の更新後、該当日付の勤怠詳細を取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// VOから個人ID取得
		String personalId = vo.getPersonalId();
		// VOから対象日取得
		Date targetDate = vo.getTargetDate();
		// 日付操作確認
		if (getTransferredGenericCode() == null) {
			// 処理なし
		} else if (getTransferredGenericCode().equals(TimeConst.CODE_DATE_DECREMENT)) {
			// 対象日 - 1
			targetDate = DateUtility.addDay(targetDate, -1);
		} else if (getTransferredGenericCode().equals(TimeConst.CODE_DATE_INCREMENT)) {
			// 対象日 + 1
			targetDate = DateUtility.addDay(targetDate, 1);
		} else if (getTransferredGenericCode().equals(TimeConst.CODE_DATE_RESET)) {
			// システム日付
			targetDate = DateUtility.getSystemDate();
		} else if (getTransferredGenericCode().equals(TimeConst.CODE_DATE_CALENDAR)) {
			// カレンダ日付
			targetDate = getDate(getTransferredDay());
		}
		// 勤怠詳細情報設定
		setAttendaneCardInfo(personalId, targetDate);
	}
	
	/**
	 * 下書処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void draft() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		AttendanceCalcBeanInterface calc = time().attendanceCalc(vo.getTargetDate());
		AttendanceRegistBeanInterface regist = time().attendanceRegist();
		GoOutRegistBeanInterface goOutRegist = time().goOutRegist();
		// 個人ID、対象日、勤務回数取得
		String personalId = vo.getPersonalId();
		Date date = vo.getTargetDate();
		int timesWork = TimeBean.TIMES_WORK_DEFAULT;
		// DTOの準備
		// 勤怠データ設定
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(personalId, date, timesWork);
		if (dto == null) {
			dto = time().attendanceRegist().getInitDto();
		}
		// VO(編集項目)の値をDTOに設定
		setAttendanceDtoFields(dto);
		// 分単位休暇1
		List<GoOutDtoInterface> minutelyHolidayAList = new ArrayList<GoOutDtoInterface>();
		// 分単位休暇2
		List<GoOutDtoInterface> minutelyHolidayBList = new ArrayList<GoOutDtoInterface>();
		// 分単位休暇設定
		setMinutelyHolidayDtoFields(minutelyHolidayAList, minutelyHolidayBList);
		// 分単位休暇削除
		goOutRegist.delete(vo.getPersonalId(), vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		// 分単位休暇登録
		for (GoOutDtoInterface goOutDto : minutelyHolidayAList) {
			goOutRegist.regist(goOutDto);
		}
		for (GoOutDtoInterface goOutDto : minutelyHolidayBList) {
			goOutRegist.regist(goOutDto);
		}
		// 始業終業時刻自動計算
		calc.calcStartEndTime(dto, true);
		// 申請エンティティ取得
		RequestEntity requestEntity = timeReference().requestUtil().getRequestEntity(personalId, date);
		// 休憩データ設定
		List<RestDtoInterface> restList = new ArrayList<RestDtoInterface>();
		setRestFields(restList, dto);
		// 公用
		List<GoOutDtoInterface> goOutPublicList = new ArrayList<GoOutDtoInterface>();
		// 私用
		List<GoOutDtoInterface> goOutPrivateList = new ArrayList<GoOutDtoInterface>();
		// 外出データ設定
		setGoOutDtoFields(goOutPublicList, goOutPrivateList, dto.getStartTime(), dto.getEndTime());
		// 私用外出チェック
		chkPrivateGoOut(dto, requestEntity);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 分単位時間重複チェック
		checkMinutelyHolidayInWorkType(dto, requestEntity);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 時間帯の重複チェック処理
		chkDuplRest(dto);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 追加処理
		setGeneralDtoFields();
		// 自動計算処理
		calc.attendanceCalc(dto, restList, goOutPublicList, goOutPrivateList, minutelyHolidayAList,
				minutelyHolidayBList);
		// 妥当性チェック
		regist.checkValidate(dto);
		// 申請の相関チェック
		regist.checkDraft(dto);
		regist.checkAttendanceCardDraft(dto);
		// 下書き処理の実行
		draft(dto, restList, goOutPublicList, goOutPrivateList, minutelyHolidayAList, minutelyHolidayBList);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 下書成功メッセージ設定
		addDraftMessage();
		// 勤怠詳細情報設定
		setAttendaneCardInfo(vo.getPersonalId(), vo.getTargetDate());
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 下書き処理以降
	 * @param dto 勤怠DTO
	 * @param restList 休憩リスト
	 * @param goOutPublicList 公用外出リスト
	 * @param goOutPrivateList 私用外出リスト
	 * @param minutelyHolidayAList 分単位休暇Aリスト
	 * @param minutelyHolidayBList 分単位休暇Bリスト
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void draft(AttendanceDtoInterface dto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> goOutPublicList, List<GoOutDtoInterface> goOutPrivateList,
			List<GoOutDtoInterface> minutelyHolidayAList, List<GoOutDtoInterface> minutelyHolidayBList)
			throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		
		GoOutRegistBeanInterface goOutRegist = time().goOutRegist();
		AttendanceRegistBeanInterface regist = time().attendanceRegist();
		// 登録クラスの取得
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_MANGE);
		}
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.draft(workflowDto, dto.getPersonalId(), dto.getWorkDate(),
				PlatformConst.WORKFLOW_TYPE_TIME);
		if (workflowDto != null) {
			// ワークフローコメント登録
			platform().workflowCommentRegist().addComment(workflowDto, mospParams.getUser().getPersonalId(),
					mospParams.getProperties().getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED,
							new String[]{ mospParams.getName("WorkPaper") }));
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 休憩データ登録
			for (int i = 0; i < restList.size(); i++) {
				time().restRegist().regist(restList.get(i));
			}
			// 外出データ削除
			goOutRegist.delete(vo.getPersonalId(), vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
			// 外出データ登録
			for (GoOutDtoInterface goOutDto : goOutPublicList) {
				goOutRegist.regist(goOutDto);
			}
			for (GoOutDtoInterface goOutDto : goOutPrivateList) {
				goOutRegist.regist(goOutDto);
			}
			for (GoOutDtoInterface goOutDto : minutelyHolidayAList) {
				goOutRegist.regist(goOutDto);
			}
			for (GoOutDtoInterface goOutDto : minutelyHolidayBList) {
				goOutRegist.regist(goOutDto);
			}
			// 勤怠データ登録
			regist.regist(dto);
		}
		
	}
	
	/**
	 * 申請処理、計算処理を行う。<br>
	 * @param checkCommand 申請コマンドの場合true、計算コマンドの場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli(boolean checkCommand) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 登録クラスの取得
		AttendanceRegistBeanInterface regist = time().attendanceRegist();
		AttendanceListRegistBeanInterface listRegist = time().attendanceListRegist(vo.getTargetDate());
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// 個人ID、対象日、勤務回数取得
		String personalId = vo.getPersonalId();
		Date date = vo.getTargetDate();
		// デフォルト勤務回数
		int timesWork = TimeBean.TIMES_WORK_DEFAULT;
		// 勤怠データ設定
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(personalId, date, timesWork);
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// 申請処理
		appli(dto, checkCommand);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (checkCommand) {
			// コミット
			commit();
			// 申請成功メッセージ設定
			addAppliMessage();
			// 残業申請督促確認
			listRegist.checkOvertime(dto);
			// 勤怠詳細情報設定
			setAttendaneCardInfo(vo.getPersonalId(), vo.getTargetDate());
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			if (PlatformConst.CODE_STATUS_APPLY.equals(workflowDto.getWorkflowStatus())) {
				// 未承認の場合
				// 編集モード設定
				vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLY);
				return;
			}
			// 編集モード設定
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLIED);
		}
	}
	
	/**
	 * 申請処理、計算処理を行う。<br>
	 * @param dto 対象DTO
	 * @param checkCommand 申請コマンドの場合true、計算コマンドの場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli(AttendanceDtoInterface dto, boolean checkCommand) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		AttendanceCalcBeanInterface calc = time().attendanceCalc(vo.getTargetDate());
		// 登録クラスの取得
		AttendanceRegistBeanInterface regist = time().attendanceRegist();
		AttendanceListRegistBeanInterface listRegist = time().attendanceListRegist(vo.getTargetDate());
		GoOutRegistBeanInterface goOutRegist = time().goOutRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		GoOutReferenceBeanInterface goOut = timeReference().goOut();
		AttendanceTransactionRegistBeanInterface attendanceTransactionRegist = time().attendanceTransactionRegist();
		// 個人ID、対象日、勤務回数取得
		String personalId = vo.getPersonalId();
		Date date = vo.getTargetDate();
		// デフォルト勤務回数
		int timesWork = TimeBean.TIMES_WORK_DEFAULT;
		// 変更前データ取得
		// 勤怠データ
		AttendanceDtoInterface oldDto = timeReference().attendance().findForKey(personalId, date, timesWork);
		if (oldDto == null) {
			oldDto = regist.getInitDto();
		}
		// 休暇データ
		List<RestDtoInterface> oldRestList = timeReference().rest().getRestList(personalId, date, timesWork);
		if (oldRestList == null) {
			oldRestList = new ArrayList<RestDtoInterface>();
		}
		// 外出データ
		List<GoOutDtoInterface> oldGoOutPublicList = goOut.getPublicGoOutList(personalId, date);
		if (oldGoOutPublicList == null) {
			oldGoOutPublicList = new ArrayList<GoOutDtoInterface>();
		}
		// 外出データ
		List<GoOutDtoInterface> oldGoOutPrivateList = goOut.getPrivateGoOutList(personalId, date);
		if (oldGoOutPrivateList == null) {
			oldGoOutPrivateList = new ArrayList<GoOutDtoInterface>();
		}
		// 分単位休暇Aデータ
		List<GoOutDtoInterface> oldGoOutMinutelyHolidayAList = goOut.getMinutelyHolidayAList(personalId, date);
		if (oldGoOutMinutelyHolidayAList == null) {
			oldGoOutMinutelyHolidayAList = new ArrayList<GoOutDtoInterface>();
		}
		// 分単位休暇Bデータ
		List<GoOutDtoInterface> oldGoOutMinutelyHolidayBList = goOut.getMinutelyHolidayBList(personalId, date);
		if (oldGoOutMinutelyHolidayBList == null) {
			oldGoOutMinutelyHolidayBList = new ArrayList<GoOutDtoInterface>();
		}
		// VO(編集項目)の値をDTOに設定
		setAttendanceDtoFields(dto);
		// 分単位休暇A
		List<GoOutDtoInterface> minutelyHolidayAList = new ArrayList<GoOutDtoInterface>();
		// 分単位休暇B
		List<GoOutDtoInterface> minutelyHolidayBList = new ArrayList<GoOutDtoInterface>();
		// 分単位休暇設定
		setMinutelyHolidayDtoFields(minutelyHolidayAList, minutelyHolidayBList);
		// 分単位休暇削除
		goOutRegist.delete(vo.getPersonalId(), vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		// 分単位休暇登録
		for (GoOutDtoInterface goOutDto : minutelyHolidayAList) {
			goOutRegist.regist(goOutDto);
		}
		for (GoOutDtoInterface goOutDto : minutelyHolidayBList) {
			goOutRegist.regist(goOutDto);
		}
		// 始業終業時刻自動計算
		calc.calcStartEndTime(dto, true);
		// 申請エンティティ取得
		RequestEntity requestEntity = timeReference().requestUtil().getRequestEntity(personalId, date);
		// 休憩データ設定
		List<RestDtoInterface> restList = new ArrayList<RestDtoInterface>();
		setRestFields(restList, dto);
		// 外出データ設定
		// 公用
		List<GoOutDtoInterface> goOutPublicList = new ArrayList<GoOutDtoInterface>();
		// 私用
		List<GoOutDtoInterface> goOutPrivateList = new ArrayList<GoOutDtoInterface>();
		// 外出情報設定
		setGoOutDtoFields(goOutPublicList, goOutPrivateList, dto.getStartTime(), dto.getEndTime());
		// 私用外出チェック
		chkPrivateGoOut(dto, requestEntity);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 分単位時間重複チェック
		checkMinutelyHolidayInWorkType(dto, requestEntity);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 時間帯の重複チェック処理
		chkDuplRest(dto);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 追加処理
		setGeneralDtoFields();
		// 自動計算処理
		calc.attendanceCalc(dto, restList, goOutPublicList, goOutPrivateList, minutelyHolidayAList,
				minutelyHolidayBList);
		// 始業・終業必須チェック
		regist.checkTimeExist(dto);
		// 妥当性チェック
		regist.checkValidate(dto);
		// 申請の相関チェック
		regist.checkAppli(dto);
		regist.checkAttendanceCardAppli(dto);
		// 私用外出時間チェック
		regist.checkPrivateGoOut(dto, restList, goOutPrivateList);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 休暇申請(時間休)重複チェック
		regist.checkHolidayTime(personalId, date, restList, goOutPublicList, goOutPrivateList, minutelyHolidayAList,
				minutelyHolidayBList);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		if (checkCommand) {
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				workflowDto = workflowRegist.getInitDto();
				workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_MANGE);
				oldDto = regist.getInitDto();
				oldRestList = new ArrayList<RestDtoInterface>();
				oldGoOutPublicList = new ArrayList<GoOutDtoInterface>();
				oldGoOutPrivateList = new ArrayList<GoOutDtoInterface>();
				oldGoOutMinutelyHolidayAList = new ArrayList<GoOutDtoInterface>();
				oldGoOutMinutelyHolidayBList = new ArrayList<GoOutDtoInterface>();
			} else {
				if (reference().workflowIntegrate().isDraft(workflowDto)) {
					oldDto = regist.getInitDto();
					oldRestList = new ArrayList<RestDtoInterface>();
					oldGoOutPublicList = new ArrayList<GoOutDtoInterface>();
					oldGoOutPrivateList = new ArrayList<GoOutDtoInterface>();
					oldGoOutMinutelyHolidayAList = new ArrayList<GoOutDtoInterface>();
					oldGoOutMinutelyHolidayBList = new ArrayList<GoOutDtoInterface>();
				}
			}
			// 承認者個人IDを設定
			workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
			// 登録後ワークフローの取得
			workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getWorkDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			if (workflowDto != null) {
				// 休憩データ登録
				for (RestDtoInterface restDto : restList) {
					time().restRegist().regist(restDto);
				}
				// 外出データ削除
				goOutRegist.delete(vo.getPersonalId(), vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
				// 外出データ登録
				for (GoOutDtoInterface goOutDto : goOutPublicList) {
					goOutRegist.regist(goOutDto);
				}
				for (GoOutDtoInterface goOutDto : goOutPrivateList) {
					goOutRegist.regist(goOutDto);
				}
				// 分単位休暇登録
				for (GoOutDtoInterface goOutDto : minutelyHolidayAList) {
					goOutRegist.regist(goOutDto);
				}
				for (GoOutDtoInterface goOutDto : minutelyHolidayBList) {
					goOutRegist.regist(goOutDto);
				}
				// 修正履歴を登録
				listRegist.registCorrection(dto, oldDto, oldRestList, oldGoOutPublicList, oldGoOutPrivateList,
						oldGoOutMinutelyHolidayAList, oldGoOutMinutelyHolidayBList);
				// 申請の相関チェック
				regist.checkApprover(dto, workflowDto);
				// ワークフロー番号セット
				dto.setWorkflow(workflowDto.getWorkflow());
				// 始業・終業必須チェック
				regist.checkTimeExist(dto);
				// 勤怠データ登録
				regist.regist(dto);
				// 代休データの設定
				listRegist.registSubHoliday(dto);
				// 勤怠トランザクション登録
				attendanceTransactionRegist.regist(dto);
			}
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 勤怠申請後処理群を実行
			time().afterApplyAttendancesExecute().execute(dto);
		} else {
			// 計算成功メッセージ設定
			addCalcMessage();
			addUnregisteredNoticeMessage();
			// 勤怠詳細情報設定
			setAttendaneCardInfo(dto, restList, goOutPublicList, goOutPrivateList);
		}
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 登録クラス取得
		AttendanceRegistBeanInterface regist = time().attendanceRegist();
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// 個人ID、対象日、勤務回数取得
		String personalId = vo.getPersonalId();
		Date date = vo.getTargetDate();
		// デフォルト勤務回数
		int timesWork = TimeBean.TIMES_WORK_DEFAULT;
		// 削除対象勤怠データ取得
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(personalId, date, timesWork);
		// 存在確認
		checkSelectedDataExist(dto);
		// 取下の相関チェック
		regist.checkDelete(dto);
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		// 未承認・下書・差戻でない場合
		if (!PlatformConst.CODE_STATUS_APPLY.equals(workflowDto.getWorkflowStatus())
				&& workflowIntegrate.isDraft(workflowDto) == false
				&& workflowIntegrate.isFirstReverted(workflowDto) == false) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// 未承認・下書・取下の場合は削除する
		regist.delete(personalId, date);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// 追加処理
		setGeneralDtoFields();
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージ設定
		addDeleteMessage();
		// 検索
		search();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void transfer() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(OvertimeRequestAction.class.getName())) {
			if (getTransferredActivateDate() == null || getTransferredType() == null) {
				// 残業申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(OvertimeRequestAction.CMD_SELECT_SHOW);
			} else {
				// 残業申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(OvertimeRequestAction.CMD_EDIT_MODE);
			}
		} else if (actionName.equals(HolidayRequestAction.class.getName())) {
			if (getTransferredActivateDate() == null || getTransferredHolidayType1() == null
					|| getTransferredHolidayType2() == null || getTransferredHolidayRange() == null
					|| getTransferredStartTime() == null) {
				// 休暇申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(HolidayRequestAction.CMD_SELECT_SHOW);
			} else {
				// 休暇申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(HolidayRequestAction.CMD_EDIT_MODE);
			}
		} else if (actionName.equals(WorkOnHolidayRequestAction.class.getName())) {
			if (getTransferredActivateDate() == null) {
				// 振出・休出申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(WorkOnHolidayRequestAction.CMD_SELECT_SHOW);
			} else {
				// 振出・休出申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(WorkOnHolidayRequestAction.CMD_EDIT_MODE);
			}
		} else if (actionName.equals(SubHolidayRequestAction.class.getName())) {
			if (getTransferredActivateDate() == null || getTransferredType() == null) {
				// 代休申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(SubHolidayRequestAction.CMD_SELECT_SHOW);
			} else {
				// 代休申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(SubHolidayRequestAction.CMD_EDIT_MODE);
			}
		} else if (actionName.equals(WorkTypeChangeRequestAction.class.getName())) {
			// 勤務形態変更申請
			WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = timeReference().workTypeChangeRequest()
				.findForKeyOnWorkflow(vo.getPersonalId(), vo.getTargetDate());
			if (workTypeChangeRequestDto == null) {
				// 勤務形態変更申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(WorkTypeChangeRequestAction.CMD_SELECT_SHOW);
				return;
			}
			long workflow = workTypeChangeRequestDto.getWorkflow();
			if (workflowIntegrate.isDraft(workflow) || workflowIntegrate.isFirstReverted(workflow)) {
				// 下書又は1次戻の場合
				// 勤務形態変更申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(WorkTypeChangeRequestAction.CMD_EDIT_MODE);
				return;
			}
			// 勤務形態変更申請画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(WorkTypeChangeRequestAction.CMD_SELECT_SHOW);
		} else if (actionName.equals(DifferenceRequestAction.class.getName())) {
			if (getTransferredActivateDate() == null) {
				// 時差出勤申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(DifferenceRequestAction.CMD_SELECT_SHOW);
			} else {
				// 時差出勤申請画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(DifferenceRequestAction.CMD_EDIT_MODE);
			}
		}
	}
	
	/**
	 * 勤怠詳細情報を取得し、VOに設定する。
	 * 対象個人ID及び対象日で、人事情報、勤怠情報、承認者情報を取得し設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendaneCardInfo(String personalId, Date targetDate) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// 各領域の初期化
		setDefaultValues();
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		// 各種申請情報取得
		requestUtil.setRequests(personalId, targetDate);
		// 申請状況を設定する
		setApplicationStatus(requestUtil);
		// 退職済確認
		if (reference().retirement().isRetired(personalId, targetDate)) {
			addEmployeeRetiredMessage();
		}
		// 休職期間確認
		if (reference().suspension().isSuspended(personalId, targetDate)) {
			addEmployeeSuspendedMessage();
		}
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 申請モード設定
			vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
			return;
		}
		// 申請エンティティ取得
		RequestEntity requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 対象個人ID及び対象日の設定適用エンティティを取得
		ApplicationEntity applicationEntity = timeReference().application().getApplicationEntity(personalId,
				targetDate);
		// 出退勤情報
		attendanceInfo();
		// 打刻始業時刻
		setRecordStartTime();
		// 打刻終業時刻
		setRecordEndTime();
		// リクエストされた値を設定する
		setTransferredValues();
		// 遷移用パラメータで譲渡された値を設定(終業時刻)
		setTargetTime(targetDate);
		// 勤務形態、勤務形態プルダウン、申請モードを設定
		setWorkType(requestUtil);
		// 勤務予定時間設定
		setScheduledTime(applicationEntity, requestEntity);
		// 無給時短時間設定
		setUnpaidShortTime(requestEntity, requestUtil);
		// 出退勤修正データ
		attendanceCorrectInfo();
		// 休憩情報設定
		setRest(requestUtil);
		// 外出情報設定
		setGoOut();
		// 遅刻早退情報
		tardinessLeaveearlyInfo();
		// 割増情報
		premiumInfo();
		// 手当情報
		allowanceInfo();
		// 勤怠情報承認状況
		attendanceInfoApprovalStatus();
		// 承認者用プルダウン設定
		setApproverPullDown(personalId, targetDate, PlatformConst.WORKFLOW_TYPE_TIME);
		if (mospParams.hasErrorMessage()) {
			vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
		}
	}
	
	/**
	 * 勤怠詳細情報を取得し、VOに設定する。
	 * 勤怠データDTOからVOに設定する。<br>
	 * @param dto 勤怠データ
	 * @param restList 休憩リスト
	 * @param publicList 公用外出リスト
	 * @param privateList 私用外出リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendaneCardInfo(AttendanceDtoInterface dto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> publicList, List<GoOutDtoInterface> privateList) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		
		vo.setPltWorkType(dto.getWorkTypeCode());
		vo.setTxtStartTimeHour(DateUtility.getStringHour(dto.getStartTime(), dto.getWorkDate()));
		vo.setTxtStartTimeMinute(DateUtility.getStringMinute(dto.getStartTime()));
		vo.setTxtEndTimeHour(DateUtility.getStringHour(dto.getEndTime(), dto.getWorkDate()));
		vo.setTxtEndTimeMinute(DateUtility.getStringMinute(dto.getEndTime()));
		vo.setLblWorkTime(getTimeTimeFormat(dto.getWorkTime()));
		vo.setLblGeneralWorkTime(getTimeTimeFormat(dto.getGeneralWorkTime()));
		vo.setCkbDirectStart(String.valueOf(dto.getDirectStart()));
		vo.setCkbDirectEnd(String.valueOf(dto.getDirectEnd()));
		vo.setCkbForgotRecordWorkStart(String.valueOf(dto.getForgotRecordWorkStart()));
		vo.setCkbNotRecordWorkStart(String.valueOf(dto.getNotRecordWorkStart()));
		vo.setLblUnpaidShortTime(getTimeTimeFormat(dto.getShortUnpaid()));
		vo.setTxtTimeComment(dto.getTimeComment());
		if (MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getDirectStart()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getDirectEnd()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getForgotRecordWorkStart()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getNotRecordWorkStart()))) {
			vo.setTxtRemarks(dto.getRemarks());
		}
		vo.setTmdAttendanceId(String.valueOf(dto.getTmdAttendanceId()));
		vo.setPltLateReason(dto.getLateReason());
		vo.setPltLateCertificate(String.valueOf(dto.getLateCertificate()));
		vo.setTxtLateComment(dto.getLateComment());
		vo.setPltLeaveEarlyReason(dto.getLeaveEarlyReason());
		vo.setPltLeaveEarlyCertificate(String.valueOf(dto.getLeaveEarlyCertificate()));
		vo.setTxtLeaveEarlyComment(dto.getLeaveEarlyComment());
		vo.setLblLateTime(getTimeTimeFormat(dto.getLateTime()));
		vo.setLblLeaveEarlyTime(getTimeTimeFormat(dto.getLeaveEarlyTime()));
		vo.setCkbAllMinutelyHolidayA(String.valueOf(dto.getMinutelyHolidayA()));
		vo.setCkbAllMinutelyHolidayB(String.valueOf(dto.getMinutelyHolidayB()));
		vo.setLblOvertime(getTimeTimeFormat(dto.getOvertime()));
		vo.setLblOvertimeIn(getTimeTimeFormat(dto.getOvertimeIn()));
		vo.setLblOvertimeOut(getTimeTimeFormat(dto.getOvertimeOut()));
		vo.setLblLateNightTime(getTimeTimeFormat(dto.getLateNightTime()));
		vo.setLblSpecificWorkTimeIn(getTimeTimeFormat(dto.getSpecificWorkTime()));
		vo.setLblLegalWorkTime(getTimeTimeFormat(dto.getLegalWorkTime()));
		vo.setLblHolidayWorkTime(getTimeTimeFormat(dto.getSpecificWorkTime() + dto.getLegalWorkTime()));
		vo.setLblDecreaseTime(getTimeTimeFormat(dto.getDecreaseTime()));
		
		vo.setLblRestTime(getTimeTimeFormat(dto.getRestTime()));
		vo.setLblOverRestTime(getTimeTimeFormat(dto.getOverRestTime()));
		vo.setLblNightRestTime(getTimeTimeFormat(dto.getNightRestTime()));
		
		vo.setLblPublicTime(getTimeTimeFormat(dto.getPublicTime()));
		vo.setLblPrivateTime(getTimeTimeFormat(dto.getPrivateTime()));
		vo.setLblMinutelyHolidayAInput(getTimeTimeFormat(dto.getMinutelyHolidayATime()));
		vo.setLblMinutelyHolidayBInput(getTimeTimeFormat(dto.getMinutelyHolidayBTime()));
		
		for (RestDtoInterface restDto : restList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(restDto.getRestStart(), vo.getTargetDate());
			String startMinute = DateUtility.getStringMinute(restDto.getRestStart());
			String endHour = DateUtility.getStringHour(restDto.getRestEnd(), vo.getTargetDate());
			String endMinute = DateUtility.getStringMinute(restDto.getRestEnd());
			if (restDto.getRest() == 1) {
				vo.setTxtRestStartHour1(startHour);
				vo.setTxtRestStartMinute1(startMinute);
				vo.setTxtRestEndHour1(endHour);
				vo.setTxtRestEndMinute1(endMinute);
			}
			if (restDto.getRest() == 2) {
				vo.setTxtRestStartHour2(startHour);
				vo.setTxtRestStartMinute2(startMinute);
				vo.setTxtRestEndHour2(endHour);
				vo.setTxtRestEndMinute2(endMinute);
			}
			if (restDto.getRest() == 3) {
				vo.setTxtRestStartHour3(startHour);
				vo.setTxtRestStartMinute3(startMinute);
				vo.setTxtRestEndHour3(endHour);
				vo.setTxtRestEndMinute3(endMinute);
			}
			if (restDto.getRest() == 4) {
				vo.setTxtRestStartHour4(startHour);
				vo.setTxtRestStartMinute4(startMinute);
				vo.setTxtRestEndHour4(endHour);
				vo.setTxtRestEndMinute4(endMinute);
			}
			if (restDto.getRest() == 5) {
				vo.setTxtRestStartHour5(startHour);
				vo.setTxtRestStartMinute5(startMinute);
				vo.setTxtRestEndHour5(endHour);
				vo.setTxtRestEndMinute5(endMinute);
			}
			if (restDto.getRest() == 6) {
				vo.setTxtRestStartHour6(startHour);
				vo.setTxtRestStartMinute6(startMinute);
				vo.setTxtRestEndHour6(endHour);
				vo.setTxtRestEndMinute6(endMinute);
			}
		}
		
		Date date = dto.getWorkDate();
		for (GoOutDtoInterface publicDto : publicList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(publicDto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(publicDto.getGoOutStart());
			String endHour = DateUtility.getStringHour(publicDto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(publicDto.getGoOutEnd());
			if (publicDto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtPublicStartHour1(startHour);
				vo.setTxtPublicStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtPublicEndHour1(endHour);
				vo.setTxtPublicEndMinute1(endMinute);
			}
			if (publicDto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtPublicStartHour2(DateUtility.getStringHour(publicDto.getGoOutStart(), dto.getWorkDate()));
				vo.setTxtPublicStartMinute2(DateUtility.getStringMinute(publicDto.getGoOutStart()));
				// 外出終了時刻。
				vo.setTxtPublicEndHour2(DateUtility.getStringHour(publicDto.getGoOutEnd(), dto.getWorkDate()));
				vo.setTxtPublicEndMinute2(DateUtility.getStringMinute(publicDto.getGoOutEnd()));
			}
		}
		
		// 私用外出情報リスト毎に処理
		for (GoOutDtoInterface privateDto : privateList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(privateDto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(privateDto.getGoOutStart());
			String endHour = DateUtility.getStringHour(privateDto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(privateDto.getGoOutEnd());
			if (privateDto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtPrivateStartHour1(startHour);
				vo.setTxtPrivateStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtPrivateEndHour1(endHour);
				vo.setTxtPrivateEndMinute1(endMinute);
			}
			if (privateDto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtPrivateStartHour2(startHour);
				vo.setTxtPrivateStartMinute2(startMinute);
				// 外出終了時刻。
				vo.setTxtPrivateEndHour2(endHour);
				vo.setTxtPrivateEndMinute2(endMinute);
			}
		}
	}
	
	/**
	 * 半日振休＋半日振休があるか判断する。<br>
	 * 但し、半日振替の振替である場合は半日振休＋半日振休でないと判断する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 確認結果(true：半日振休＋半日振休、false：そうでない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isAmPmHalfSubstitute(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 申請エンティティ取得
		RequestEntity entity = requestUtil.getRequestEntity(vo.getPersonalId(), vo.getTargetDate());
		// 半日振休+半日振休でない場合
		if (entity.isAmPmHalfSubstitute(true) == false) {
			return false;
		}
		// 半日振替の振替かどうか判断
		return entity.isHalfPostpone(true) == false;
	}
	
	/**
	 * 勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkType(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		// 勤怠情報取得(取下、下書、1次戻以外)
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		// 時差出勤申請取得(承認済)
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		// 申請された勤怠情報が存在する場合
		if (attendanceDto != null) {
			// 申請済勤怠申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
			setWorkTypeForApplicatedAttendance(attendanceDto, requestUtil, differenceRequestDto != null);
			// 処理終了
			return;
		}
		// 休暇申請取得(承認済、全休)及び確認
		// カレンダに登録されている勤務形態コードを取得(振替休日、振出・休出申請、勤務形態変更申請、時差出勤申請を考慮)
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(vo.getPersonalId(), vo.getTargetDate(),
				requestUtil);
		// 法定休日
		boolean isLegalDaysOff = TimeUtility.isLegalHoliday(workTypeCode);
		// 所定休日
		boolean isPrescribedDaysOff = TimeUtility.isPrescribedHoliday(workTypeCode);
		// 法定休日労働
		boolean isWorkOnLegalDaysOff = TimeUtility.isWorkOnLegalHoliday(workTypeCode);
		// 所定休日労働
		boolean isWorkOnPrescribedDaysOff = TimeUtility.isWorkOnPrescribedHoliday(workTypeCode);
		// 休暇申請(承認済み、全休)および確認
		if (setWorkTypePulldownHoliday(requestUtil, isLegalDaysOff, isPrescribedDaysOff, isWorkOnLegalDaysOff,
				isWorkOnPrescribedDaysOff)) {
			
			// 処理終了
			return;
		}
		// 代休申請取得(承認済、全休)及び確認
		SubHolidayRequestDtoInterface subHolidayRequestDto = requestUtil.getCompletedSubHolidayRangeAll();
		if (subHolidayRequestDto != null) {
			// 承認済代休申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
			setWorkTypeForSubHolidayRequest(subHolidayRequestDto);
			// 処理終了
			return;
		}
		// 時差出勤申請確認(承認済)
		if (differenceRequestDto != null) {
			// 承認済時差出勤申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
			setWorkTypeForDifferenceRequest(differenceRequestDto, requestUtil);
			// 処理終了
			return;
		}
		// 勤務形態変更申請取得(承認済)
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = requestUtil.getWorkTypeChangeDto(true);
		if (workTypeChangeRequestDto != null) {
			// 承認済勤務形態変更申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
			setWorkTypeForWorkTypeChangeRequest(workTypeChangeRequestDto, requestUtil);
			// 処理終了
			return;
		}
		//  振替休日取得(承認済、全休)及び確認
		if (setWorkTypePulldownSubstitute(requestUtil, isLegalDaysOff, isPrescribedDaysOff, isWorkOnLegalDaysOff,
				isWorkOnPrescribedDaysOff)) {
			// 処理終了
			return;
		}
		// 振出休出申請確認
		if (setWorkTypeForWorkOnHolidayRequest(requestUtil)) {
			// 処理終了
			return;
		}
		// カレンダに対して、勤務形態、勤務形態プルダウン、申請モードを設定
		setWorkTypeForSchedule(requestUtil);
	}
	
	/**
	 * 勤務形態、勤務形態プルダウン、申請モード設定(振替休日)<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param isLegalDaysOff 法定休日判定
	 * @param isPrescribedDaysOff 所定休日判定
	 * @param isWorkOnLegalDaysOff 法定休日労働判定
	 * @param isWorkOnPrescribedDaysOff 所定休日労働判定
	 * @return 処理終了判定(true:処理終了、false:処理継続)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean setWorkTypePulldownSubstitute(RequestUtilBeanInterface requestUtil, boolean isLegalDaysOff,
			boolean isPrescribedDaysOff, boolean isWorkOnLegalDaysOff, boolean isWorkOnPrescribedDaysOff)
			throws MospException {
		// 振替休日取得(承認済、全休)及び確認
		SubstituteDtoInterface subsutituteDto = requestUtil.getCompletedSubstituteRangeAll();
		if (subsutituteDto != null) {
			// 承認済振替休日情報に対して、勤務形態、勤務形態プルダウン、申請モードを設定
			setWorkTypeForSubstitute(subsutituteDto, requestUtil);
			// 処理終了
			return true;
		}
		// 全休(半休 + 半休)確認
		if (!isLegalDaysOff && !isPrescribedDaysOff && !isWorkOnLegalDaysOff && !isWorkOnPrescribedDaysOff) {
			// 半日振休＋半日振休がある場合
			if (isAmPmHalfSubstitute(requestUtil)) {
				// 承認済全休(半休 + 半休)に対して、勤務形態、勤務形態プルダウン、申請モードを設定
				setWorkTypeForHolidayAllDay();
				// 処理終了
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * 勤務形態、勤務形態プルダウン、申請モード設定(休暇申請)<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param isLegalDaysOff 法定休日判定
	 * @param isPrescribedDaysOff 所定休日判定
	 * @param isWorkOnLegalDaysOff 法定休日労働判定
	 * @param isWorkOnPrescribedDaysOff 所定休日労働判定
	 * @return 処理終了判定(true:処理終了、false:処理継続)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean setWorkTypePulldownHoliday(RequestUtilBeanInterface requestUtil, boolean isLegalDaysOff,
			boolean isPrescribedDaysOff, boolean isWorkOnLegalDaysOff, boolean isWorkOnPrescribedDaysOff)
			throws MospException {
		// 法定休日・所定休日・法定休日労働・所定休日労働でない場合
		if (!isLegalDaysOff && !isPrescribedDaysOff && !isWorkOnLegalDaysOff && !isWorkOnPrescribedDaysOff) {
			// 全日の承認済休暇申請取得
			HolidayRequestDtoInterface holidayRequestDto = requestUtil.getCompletedHolidayRangeAll();
			if (holidayRequestDto != null) {
				// 承認済休暇申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
				setWorkTypeForHolidayRequest(holidayRequestDto);
				// 処理終了
				return true;
			}
			// 半休＋半休の場合
			if (requestUtil.checkHolidayRangeHoliday(
					requestUtil.getHolidayList(true)) == TimeConst.CODE_HOLIDAY_RANGE_HALF_AND_HALF) {
				setWorkTypeForHolidayAllDay();
				return true;
				
			}
		}
		
		return false;
	}
	
	/**
	 * 遷移用パラメータで譲渡された値を設定する。<br>
	 * <br>
	 * 遷移用パラメータ(対象時刻)が存在する場合(ポータル画面から残業有終業ボタン
	 * を押下して遷移してきた場合)、対象時刻をVOの終業時刻に設定する。<br>
	 * <br>
	 * @param targetDate 対象日(勤務日)
	 */
	protected void setTargetTime(Date targetDate) {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 遷移用パラメータ(対象時刻)取得
		Date targetTime = getTargetTime();
		// 対象時刻確認
		if (targetTime == null) {
			return;
		}
		// 対象時刻を時分に設定
		vo.setTxtEndTimeHour(DateUtility.getStringHour(targetTime, targetDate));
		vo.setTxtEndTimeMinute(DateUtility.getStringMinute(targetTime));
	}
	
	/**
	 * 始業時刻及び終業時刻に勤務予定時間を設定する。<br>
	 * <br>
	 * 勤怠設定の勤務予定時間表示が有効であり、対象日の勤怠情報が無い場合のみ、
	 * 勤務予定時間を設定する。<br>
	 * <br>
	 * また、勤務形態に設定された直行/直帰を設定する。<br>
	 * <br>
	 * @param applicationEntity 設定適用エンティティ
	 * @param requestEntity     申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setScheduledTime(ApplicationEntity applicationEntity, RequestEntity requestEntity)
			throws MospException {
		// 勤怠データ確認
		if (requestEntity.hasAttendance()) {
			// 既に勤怠データが存在する場合は設定不要
			return;
		}
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// VOから対象日を取得
		Date targetDate = vo.getTargetDate();
		// VOに設定されている勤務形態を取得
		String workTypeCode = vo.getPltWorkType();
		// 設定された勤務形態のエンティティを取得
		WorkTypeEntity workTypeEntity = timeReference().workType().getWorkTypeEntity(workTypeCode, targetDate);
		// 勤務予定時間表示設定確認
		if (applicationEntity.useScheduledTime()) {
			// 始業及び終業時刻を取得
			Date startWorkTime = workTypeEntity.getStartTime(requestEntity);
			Date endWorkTime = workTypeEntity.getEndTime(requestEntity);
			// 始業及び終業時刻をVOに設定
			vo.setTxtStartTimeHour(DateUtility.getStringHour(startWorkTime, targetDate));
			vo.setTxtStartTimeMinute(DateUtility.getStringMinute(startWorkTime));
			vo.setTxtEndTimeHour(DateUtility.getStringHour(endWorkTime, targetDate));
			vo.setTxtEndTimeMinute(DateUtility.getStringMinute(endWorkTime));
		}
		// 直行確認
		if (workTypeEntity.isDirectStart()) {
			// 直行チェックボックスON
			vo.setCkbDirectStart(MospConst.CHECKBOX_ON);
		}
		// 直帰確認
		if (workTypeEntity.isDirectEnd()) {
			// 直帰チェックボックスON
			vo.setCkbDirectEnd(MospConst.CHECKBOX_ON);
		}
	}
	
	/**
	 * 無給時短時間を設定する。<br>
	 * @param requestEntity 申請エンティティ
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setUnpaidShortTime(RequestEntity requestEntity, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		WorkTypeReferenceBeanInterface workTypeReference = timeReference().workType();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		if (!requestEntity.isAllHoliday(false)) {
			// 全休でない場合
			return;
		}
		// 全休の場合
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(vo.getPersonalId(), vo.getTargetDate(),
				requestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			return;
		}
		WorkTypeEntity workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, vo.getTargetDate());
		if (workTypeEntity == null) {
			return;
		}
		int shortTime = 0;
		if (workTypeEntity.isShort1TimeSet() && !workTypeEntity.isShort1TypePay()) {
			// 時短時間1が無給の場合
			shortTime += TimeUtility.getMinutes(workTypeEntity.getShort1EndTime())
					- TimeUtility.getMinutes(workTypeEntity.getShort1StartTime());
		}
		if (workTypeEntity.isShort2TimeSet() && !workTypeEntity.isShort2TypePay()) {
			// 時短時間2が無給の場合
			shortTime += TimeUtility.getMinutes(workTypeEntity.getShort2EndTime())
					- TimeUtility.getMinutes(workTypeEntity.getShort2StartTime());
		}
		vo.setLblUnpaidShortTime(getTimeTimeFormat(shortTime));
	}
	
	/**
	 * 申請済勤怠申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @param attendanceDto 勤怠申請情報
	 * @param requestUtil 申請ユーティリティ
	 * @param isDifference 時差出勤の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForApplicatedAttendance(AttendanceDtoInterface attendanceDto,
			RequestUtilBeanInterface requestUtil, boolean isDifference) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 個人ID・対象日取得
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		// 勤務形態参照クラス準備
		WorkTypeReferenceBeanInterface workTypeRefer = timeReference().workType();
		// 勤務形態取得
		String workTypeCode = attendanceDto.getWorkTypeCode();
		// 特殊な勤務形態の名称を取得
		String workTypeName = workTypeRefer.getParticularWorkTypeName(workTypeCode);
		// 特殊な勤務形態でない場合
		if (workTypeName == null) {
			// 申請エンティティ取得
			RequestEntity entity = requestUtil.getRequestEntity(personalId, targetDate);
			// 勤務形態プルダウン用配列取得(勤務形態略称【出勤時刻～退勤時刻】)
			String[][] aryWorkType = workTypeRefer.getTimeSelectArray(targetDate, entity.isAmHoliday(true),
					entity.isPmHoliday(true));
			// 勤務形態プルダウン用配列から名称を取得
			workTypeName = MospUtility.getCodeName(workTypeCode, aryWorkType);
		}
		// 勤務形態及び勤務形態プルダウン用配列を設定
		setPltWorkType(workTypeCode, workTypeName);
		// 時差出勤の場合
		if (isDifference) {
			// 時差出勤区分取得
			String differenceType = workTypeCode;
			// 勤務形態設定(時差出勤区分)
			vo.setPltWorkType(differenceType);
			// 勤務形態プルダウン設定(時差出勤のプルダウン)
			vo.setAryPltWorkType(
					timeReference().differenceRequest(vo.getTargetDate()).getDifferenceSelectArray(differenceType));
		}
		// 最新のワークフロー情報を取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(attendanceDto.getWorkflow());
		// 最新のワークフロー情報が取得できないか未承認である場合
		if (workflowDto == null || PlatformConst.CODE_STATUS_APPLY.equals(workflowDto.getWorkflowStatus())) {
			// 編集モード(申請モード【未承認】)を設定
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLY);
			return;
		}
		// 編集モード(申請モード【申請済】)を設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLIED);
	}
	
	/**
	 * 承認済休暇申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @param holidayRequestDto 休暇申請情報
	 */
	protected void setWorkTypeForHolidayRequest(HolidayRequestDtoInterface holidayRequestDto) {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤務形態及び勤務形態プルダウン用配列を設定(休暇種別確認)
		switch (holidayRequestDto.getHolidayType1()) {
			// 有給休暇の場合
			case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
				setPltWorkType(mospParams.getName("PaidVacation"));
				break;
			// 特別休暇の場合
			case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
				setPltWorkType(mospParams.getName("Specially", "Vacation"));
				break;
			// その他休暇の場合
			case TimeConst.CODE_HOLIDAYTYPE_OTHER:
				setPltWorkType(mospParams.getName("Others", "Vacation"));
				break;
			// 欠勤の場合
			case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
				setPltWorkType(mospParams.getName("Absence"));
				break;
			default:
				setPltWorkType("");
		}
		// 申請モード設定(休日承認済)
		vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
	}
	
	/**
	 * 承認済代休申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @param subHolidayRequestDto 代休申請情報
	 */
	protected void setWorkTypeForSubHolidayRequest(SubHolidayRequestDtoInterface subHolidayRequestDto) {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤務形態及び勤務形態プルダウン用配列を設定(代休種別確認)
		switch (subHolidayRequestDto.getWorkDateSubHolidayType()) {
			// 所定代休の場合
			case TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE:
				setPltWorkType(mospParams.getName("Prescribed", "Generation", "Rest"));
				break;
			// 法定代休の場合
			case TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE:
				setPltWorkType(mospParams.getName("Legal", "Generation", "Rest"));
				break;
			// 深夜代休の場合
			case TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE:
				setPltWorkType(mospParams.getName("Midnight", "Generation", "Rest"));
				break;
			default:
				setPltWorkType("");
		}
		// 申請モード設定(休日承認済)
		vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
	}
	
	/**
	 * 承認済全休(半休 + 半休)に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForHolidayAllDay() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤務形態及び勤務形態プルダウン用配列を設定
		setPltWorkType(mospParams.getName("Application", "In", "From", "Vacation", "Finish"));
		// 申請モード設定(休日承認済)
		vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
	}
	
	/**
	 * 振出休出申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)する。
	 * @param requestUtil 申請ユーティリティ
	 * @return 結果可否
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setWorkTypeForWorkOnHolidayRequest(RequestUtilBeanInterface requestUtil) throws MospException {
		// 振出休出申請取得(承認済、休日出勤)及び確認
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		// 承認済振出休出申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定
		setWorkTypeForWorkOnHolidayRequest(workOnHolidayRequestDto, requestUtil);
		return true;
	}
	
	/**
	 * 振出休出申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)する。
	 * @param workOnHolidayRequestDto 振出休出情報
	 * @param requestUtil             申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForWorkOnHolidayRequest(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// 振替申請区分取得
		int substitute = workOnHolidayRequestDto.getSubstitute();
		// 振替申請区分確認(休日出勤の場合)
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 勤務形態準備
			String workTypeCode = "";
			// 休出種別取得
			String workOnHolidayType = workOnHolidayRequestDto.getWorkOnHolidayType();
			// 法定休日の場合
			if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayType)) {
				workTypeCode = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
			}
			// 所定休日の場合
			if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workOnHolidayType)) {
				workTypeCode = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
			}
			// 勤務形態及び勤務形態プルダウン用配列を設定
			setPltWorkType(workTypeCode, timeReference().workType().getParticularWorkTypeName(workTypeCode));
			// 申請モード設定(新規)
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
			// 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)
			setWorkTypeForNotApplicatedAttendance(requestUtil);
			return;
		}
		// 振替申請区分確認(振替出勤の場合)
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// TODO 対象日(休日出勤の日)を取得
		Date targetDate = vo.getTargetDate();
		// 設定適用情報取得
		ApplicationDtoInterface applicationDto = application.findForPerson(personalId, targetDate);
		application.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// カレンダ情報取得
		ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(applicationDto.getScheduleCode(), targetDate);
		schedule.chkExistSchedule(scheduleDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請エンティティを取得
		RequestEntity entity = requestUtil.getRequestEntity(personalId, targetDate);
		// 対象日が前半休・後半休であるかを確認
		boolean holidayAm = entity.isAmHoliday(true);
		boolean holidayPm = entity.isPmHoliday(true);
		// 対象日で勤務形態プルダウン用配列を取得(勤務形態略称【出勤時刻～退勤時刻】)
		String[][] aryWorkType = getWorkTypeArray(scheduleDto.getPatternCode(), holidayAm, holidayPm);
		String workTypeCode = workOnHolidayRequestDto.getWorkTypeCode();
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 勤務形態変更ありでない場合
			// 振替休日の日を取得
			Date substituteDate = timeReference().substitute().getSubstituteDate(workOnHolidayRequestDto.getWorkflow());
			// カレンダの勤務形態コード(振替休日の日)を取得
			workTypeCode = timeReference().scheduleUtil().getScheduledWorkTypeCode(personalId, substituteDate);
		}
		if (mospParams.hasErrorMessage()) {
			// 申請モード設定
			vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
			return;
		}
		int workTypeChangeFlag = scheduleDto.getWorkTypeChangeFlag();
		if (workTypeChangeFlag == MospConst.DELETE_FLAG_OFF) {
			// 勤務形態変更可の場合
			// VOに勤務形態コードを設定
			vo.setPltWorkType(workTypeCode);
			// 勤務形態プルダウン設定(勤務形態略称【出勤時刻～退勤時刻】)
			vo.setAryPltWorkType(aryWorkType);
		} else if (workTypeChangeFlag == MospConst.DELETE_FLAG_ON) {
			// 勤務形態変更不可の場合
			setPltWorkType(workTypeCode, MospUtility.getCodeName(workTypeCode, aryWorkType));
		}
		// 申請モード設定(新規)
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)
		setWorkTypeForNotApplicatedAttendance(requestUtil);
	}
	
	/**
	 * 承認済振替休日情報に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 振替の振替があった場合には、
	 * 承認済振出休出申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * @param subsutituteDto 振替休日情報
	 * @param requestUtil    申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForSubstitute(SubstituteDtoInterface subsutituteDto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 振替種別取得
		String substituteType = subsutituteDto.getSubstituteType();
		// 所定振替休日の場合
		if (substituteType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
			setPltWorkType(TimeNamingUtility.prescribedTransferHoliday(mospParams));
		}
		// 法定振替休日の場合
		if (substituteType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			setPltWorkType(TimeNamingUtility.legalTransferHoliday(mospParams));
		}
		// 申請モード設定(休日承認済)
		vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
		// 振替の振替を確認
		setWorkTypeForWorkOnHolidayRequest(requestUtil);
	}
	
	/**
	 * 承認済勤務形態変更申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)する。
	 * @param workTypeChangeRequestDto 勤務形態変更申請情報
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForWorkTypeChangeRequest(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// クラス準備
		ApplicationReferenceBeanInterface application = timeReference().application();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// 適用情報取得
		ApplicationDtoInterface applicationDto = application.findForPerson(vo.getPersonalId(), vo.getTargetDate());
		application.chkExistApplication(applicationDto, vo.getTargetDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// カレンダマスタ取得
		ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(applicationDto.getScheduleCode(),
				vo.getTargetDate());
		schedule.chkExistSchedule(scheduleDto, vo.getTargetDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 午前休・午後休フラグ準備
		boolean holidayAm = false;
		boolean holidayPm = false;
		// 承認済休日出勤申請情報取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto != null) {
			// 休暇範囲取得
			int substitute = workOnHolidayRequestDto.getSubstitute();
			holidayAm = substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
			holidayPm = substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
		}
		// 承認済休暇申請情報の休暇範囲
		int rangeHoliday = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(true));
		// 承認済代休申請情報の休暇範囲
		int rangeSubHoliday = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true));
		// 承認済振替休日申請情報の休暇範囲
		int rangeSubstitute = requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true));
		// 午前休の場合
		if (rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM || rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			holidayAm = true;
		}
		// 午後休の場合
		if (rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM || rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM
				|| rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			holidayPm = true;
		}
		// 勤務形態プルダウン用配列取得(勤務形態略称【出勤時刻～退勤時刻】)
		String[][] aryWorkType = getWorkTypeArray(scheduleDto.getPatternCode(), holidayAm, holidayPm);
		int workTypeChangeFlag = scheduleDto.getWorkTypeChangeFlag();
		if (workTypeChangeFlag == MospConst.DELETE_FLAG_OFF) {
			// 勤務形態変更可の場合
			// VOに勤務形態コードを設定
			vo.setPltWorkType(workTypeChangeRequestDto.getWorkTypeCode());
			// 勤務形態プルダウン設定(勤務形態略称【出勤時刻～退勤時刻】)
			vo.setAryPltWorkType(aryWorkType);
		} else if (workTypeChangeFlag == MospConst.DELETE_FLAG_ON) {
			// 勤務形態変更不可の場合
			setPltWorkType(workTypeChangeRequestDto.getWorkTypeCode(),
					MospUtility.getCodeName(workTypeChangeRequestDto.getWorkTypeCode(), aryWorkType));
		}
		// 変更済勤務形態エンティティを取得取得
		WorkTypeEntity workTypeEntity = timeReference().workType()
			.getWorkTypeEntity(workTypeChangeRequestDto.getWorkTypeCode(), vo.getTargetDate());
		if (workTypeEntity != null) {
			// 勤務形態の直行設定を確認
			if (workTypeEntity.isDirectStart()) {
				// 勤怠データに直行フラグを設定
				vo.setCkbDirectStart(MospConst.CHECKBOX_ON);
			}
			// 勤務形態の直帰設定を確認
			if (workTypeEntity.isDirectEnd()) {
				// 勤怠データに直帰フラグを設定
				vo.setCkbDirectEnd(MospConst.CHECKBOX_ON);
			}
		}
		// 申請モード設定(新規)
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)
		setWorkTypeForNotApplicatedAttendance(requestUtil);
	}
	
	/**
	 * 承認済時差出勤申請に対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)する。
	 * @param differenceRequestDto 時差出勤申請情報
	 * @param requestUtil          申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForDifferenceRequest(DifferenceRequestDtoInterface differenceRequestDto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 時差出勤区分取得
		String differenceType = differenceRequestDto.getDifferenceType();
		// 勤務形態設定(時差出勤区分)
		vo.setPltWorkType(differenceType);
		// 勤務形態プルダウン設定(時差出勤のプルダウン)
		vo.setAryPltWorkType(
				timeReference().differenceRequest(vo.getTargetDate()).getDifferenceSelectArray(differenceType));
		// 申請モード設定(新規)
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)
		setWorkTypeForNotApplicatedAttendance(requestUtil);
	}
	
	/**
	 * カレンダに対して、勤務形態、勤務形態プルダウン、申請モードを設定する。<br>
	 * 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)する。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForSchedule(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// 個人ID及び対象日を取得
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		// カレンダの勤務形態コードを取得
		String workTypeCode = timeReference().scheduleUtil().getScheduledWorkTypeCode(personalId, targetDate);
		if (mospParams.hasErrorMessage()) {
			// 申請モード設定
			vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
		}
		// カレンダの勤務形態コードを確認(法定休日或いは所定休日の場合)
		if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)) {
			// 勤務形態及び勤務形態プルダウン用配列を設定
			setPltWorkType(workTypeCode, timeReference().workType().getParticularWorkTypeName(workTypeCode));
			// 申請モード設定(休日承認済)
			vo.setModeCardEdit(MODE_APPLICATION_COMPLETED_HOLIDAY);
		} else {
			ApplicationDtoInterface applicationDto = application.findForPerson(personalId, targetDate);
			application.chkExistApplication(applicationDto, targetDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(applicationDto.getScheduleCode(), targetDate);
			schedule.chkExistSchedule(scheduleDto, targetDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			int rangeHoliday = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(true));
			int rangeSubHoliday = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true));
			int rangeSubstitute = requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true));
			boolean holidayAm = rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_AM;
			boolean holidayPm = rangeHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM
					|| rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM
					|| rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_PM;
			// 全休の場合
			if (rangeHoliday == 5 || rangeSubHoliday == 5 || rangeSubstitute == 5 || (holidayAm && holidayPm)) {
				// 全休に設定
				setWorkTypeForHolidayAllDay();
				return;
			}
			// 勤務形態プルダウン用配列取得(勤務形態略称【出勤時刻～退勤時刻】)
			String[][] aryWorkType = getWorkTypeArray(scheduleDto.getPatternCode(), holidayAm, holidayPm);
			int workTypeChangeFlag = scheduleDto.getWorkTypeChangeFlag();
			if (workTypeChangeFlag == MospConst.DELETE_FLAG_OFF) {
				// 勤務形態変更可の場合
				// VOに勤務形態コードを設定
				vo.setPltWorkType(workTypeCode);
				// 勤務形態プルダウン設定(勤務形態略称【出勤時刻～退勤時刻】)
				vo.setAryPltWorkType(aryWorkType);
			} else if (workTypeChangeFlag == MospConst.DELETE_FLAG_ON) {
				// 勤務形態変更不可の場合
				setPltWorkType(workTypeCode, MospUtility.getCodeName(workTypeCode, aryWorkType));
			}
			// 申請モード設定(新規)
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		}
		// 勤怠申請(下書或いは1次戻)が存在する場合、勤務形態、申請モードを設定(上書)
		setWorkTypeForNotApplicatedAttendance(requestUtil);
	}
	
	/**
	 * 勤怠申請情報(下書或いは1次戻)に対して、勤務形態、申請モードを設定する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeForNotApplicatedAttendance(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠申請情報(下書)取得
		AttendanceDtoInterface attendanceDto = requestUtil.getDraftAttendance();
		// 下書の勤怠申請が存在する場合
		if (attendanceDto != null) {
			// 勤務形態をVOに設定
			vo.setPltWorkType(attendanceDto.getWorkTypeCode());
			// 申請モード設定(下書編集)
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
		}
		// 勤怠申請情報(1次戻)取得
		attendanceDto = requestUtil.getFirstRevertedAttendance();
		// 1次戻の勤怠申請が存在する場合
		if (attendanceDto != null) {
			// 勤務形態をVOに設定
			vo.setPltWorkType(attendanceDto.getWorkTypeCode());
			// 申請モード設定(差戻編集)
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_REVERT);
		}
		// 対象個人IDにつき対象日付が未締であるかを確認(未締でない場合)
		if (timeReference().cutoffUtil().isNotTighten(vo.getPersonalId(), vo.getTargetDate()) == false) {
			// 申請モード設定(申請済)
			vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLIED);
		}
	}
	
	/**
	 * 勤怠情報をVOに設定する。
	 * @throws MospException 例外発生時
	 */
	protected void attendanceInfo() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠情報取得
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT);
		// 勤怠情報確認
		if (dto == null) {
			return;
		}
		// 勤怠情報設定(勤務形態コードは別途設定)
//		vo.setTxtStartTimeHour(DateUtility.getStringHour(dto.getStartTime()));
//		vo.setTxtStartTimeMinute(DateUtility.getStringMinute(dto.getStartTime()));
		vo.setTxtStartTimeHour(DateUtility.getStringHour(dto.getActualStartTime()));
		vo.setTxtStartTimeMinute(DateUtility.getStringMinute(dto.getActualStartTime()));
//		vo.setTxtEndTimeHour(DateUtility.getStringHour(dto.getEndTime(), dto.getWorkDate()));
//		vo.setTxtEndTimeMinute(DateUtility.getStringMinute(dto.getEndTime()));
		vo.setTxtEndTimeHour(DateUtility.getStringHour(dto.getActualEndTime(), dto.getWorkDate()));
		vo.setTxtEndTimeMinute(DateUtility.getStringMinute(dto.getActualEndTime()));
		vo.setLblWorkTime(getTimeTimeFormat(dto.getWorkTime()));
		vo.setLblGeneralWorkTime(getTimeTimeFormat(dto.getGeneralWorkTime()));
		vo.setCkbDirectStart(String.valueOf(dto.getDirectStart()));
		vo.setCkbDirectEnd(String.valueOf(dto.getDirectEnd()));
		vo.setCkbForgotRecordWorkStart(String.valueOf(dto.getForgotRecordWorkStart()));
		vo.setCkbNotRecordWorkStart(String.valueOf(dto.getNotRecordWorkStart()));
		vo.setLblUnpaidShortTime(getTimeTimeFormat(dto.getShortUnpaid()));
		vo.setTxtTimeComment(dto.getTimeComment());
		if (MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getDirectStart()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getDirectEnd()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getForgotRecordWorkStart()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(dto.getNotRecordWorkStart()))) {
			vo.setTxtRemarks(dto.getRemarks());
		}
		vo.setTmdAttendanceId(String.valueOf(dto.getTmdAttendanceId()));
		vo.setPltLateReason(dto.getLateReason());
		vo.setPltLateCertificate(String.valueOf(dto.getLateCertificate()));
		vo.setTxtLateComment(dto.getLateComment());
		vo.setPltLeaveEarlyReason(dto.getLeaveEarlyReason());
		vo.setPltLeaveEarlyCertificate(String.valueOf(dto.getLeaveEarlyCertificate()));
		vo.setTxtLeaveEarlyComment(dto.getLeaveEarlyComment());
		vo.setLblLateTime(getTimeTimeFormat(dto.getLateTime()));
		vo.setLblLeaveEarlyTime(getTimeTimeFormat(dto.getLeaveEarlyTime()));
		vo.setCkbAllMinutelyHolidayA(String.valueOf(dto.getMinutelyHolidayA()));
		vo.setCkbAllMinutelyHolidayB(String.valueOf(dto.getMinutelyHolidayB()));
		vo.setLblOvertime(getTimeTimeFormat(dto.getOvertime()));
		vo.setLblOvertimeIn(getTimeTimeFormat(dto.getOvertimeIn()));
		vo.setLblOvertimeOut(getTimeTimeFormat(dto.getOvertimeOut()));
		vo.setLblLateNightTime(getTimeTimeFormat(dto.getLateNightTime()));
		vo.setLblSpecificWorkTimeIn(getTimeTimeFormat(dto.getSpecificWorkTime()));
		vo.setLblLegalWorkTime(getTimeTimeFormat(dto.getLegalWorkTime()));
		vo.setLblHolidayWorkTime(getTimeTimeFormat(dto.getSpecificWorkTime() + dto.getLegalWorkTime()));
		vo.setLblDecreaseTime(getTimeTimeFormat(dto.getDecreaseTime()));
	}
	
	/**
	 * 打刻始業時刻をVOに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setRecordStartTime() throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 始業打刻データ取得
		TimeRecordDtoInterface dto = timeReference().timeRecord().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, PortalTimeCardBean.RECODE_START_WORK);
		if (dto == null) {
			// 始業打刻データがない場合
			return;
		}
		// 【】内に打刻始業時刻を設定
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(DateUtility.getStringTimeAndSecond(dto.getRecordTime(), dto.getWorkDate()));
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		vo.setLblStartTime(sb.toString());
	}
	
	/**
	 * 打刻終業時刻をVOに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setRecordEndTime() throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 終業打刻データ取得
		TimeRecordDtoInterface dto = timeReference().timeRecord().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, PortalTimeCardBean.RECODE_END_WORK);
		if (dto == null) {
			// 終業打刻データがない場合
			return;
		}
		// 【】内に打刻終業時刻を設定
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(DateUtility.getStringTimeAndSecond(dto.getRecordTime(), dto.getWorkDate()));
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		vo.setLblEndTime(sb.toString());
	}
	
	/**
	 * 出退勤修正データ
	 * @throws MospException 例外発生時
	 */
	protected void attendanceCorrectInfo() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// DTO準備
		AttendanceCorrectionDtoInterface dto = timeReference().attendanceCorrection()
			.getLatestAttendanceCorrectionInfo(vo.getPersonalId(), vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		if (dto == null) {
			return;
		}
		// 修正理由
		vo.setTxtCorrectionReason(dto.getCorrectionReason());
		// 修正情報
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("Corrector"));
		sb.append(mospParams.getName("Colon"));
		sb.append(reference().human().getHumanName(dto.getCorrectionPersonalId(), dto.getCorrectionDate()));
		sb.append(" ");
		sb.append(mospParams.getName("Day"));
		sb.append(mospParams.getName("Hour"));
		sb.append(mospParams.getName("Colon"));
		sb.append(DateUtility.getStringDateAndDayAndTime(dto.getCorrectionDate()));
		vo.setLblCorrectionHistory(sb.toString());
	}
	
	/**
	 * 休憩情報を設定する。<br>
	 * 勤怠情報が無い場合は、カレンダに登録されている勤務形態の休憩時間を設定する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setRest(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// DTO準備
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT);
		// 勤怠情報が無い場合
		if (dto == null) {
			// カレンダに登録されている勤務形態の休憩時間を設定
			setScheduledRest(requestUtil);
			return;
		}
		vo.setLblRestTime(getTimeTimeFormat(dto.getRestTime()));
		vo.setLblOverRestTime(getTimeTimeFormat(dto.getOverRestTime()));
		vo.setLblNightRestTime(getTimeTimeFormat(dto.getNightRestTime()));
		if (dto.getStartTime() == null || dto.getEndTime() == null) {
			// 始業時刻又は終業時刻がない場合
			long standardTime = DateUtility.getDateTime(DateUtility.getYear(vo.getTargetDate()),
					DateUtility.getMonth(vo.getTargetDate()), DateUtility.getDay(vo.getTargetDate()), 0, 0)
				.getTime();
			List<RestDtoInterface> list = timeReference().rest().getRestList(vo.getPersonalId(), vo.getTargetDate(), 1);
			for (RestDtoInterface restDto : list) {
				if (restDto.getRestStart().getTime() != standardTime
						|| restDto.getRestEnd().getTime() != standardTime) {
					// 登録されている休憩時間をセット
					setRegisteredRest();
					return;
				}
			}
			// カレンダに登録されている勤務形態の休憩時間を設定
			setScheduledRest(requestUtil);
			return;
		}
		// 登録されている休憩時間をセット
		setRegisteredRest();
	}
	
	/**
	 * カレンダに登録されている休憩情報を設定する。<br>
	 * 但し、設定されている申請モードが休日承認済の場合は、休憩情報を設定しない。<br>
	 * 承認済の振出休出申請が存在する場合は、
	 * 振替申請区分が振替出勤の場合は振替休日の勤務形態に依り、
	 * それ以外の場合は休憩情報を設定しない。<br>
	 * 承認済の時差出勤申請が存在する場合は、時差出勤区分に依る。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setScheduledRest(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 対象個人ID及び対象日付準備
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		// 申請モード確認(休日承認済の場合)
		if (vo.getModeCardEdit().equals(MODE_APPLICATION_COMPLETED_HOLIDAY)) {
			// 処理無し(休日承認済の場合)
			return;
		}
		// 申請エンティティ取得
		RequestEntity entity = requestUtil.getRequestEntity(personalId, targetDate);
		// 全休または前休または後休の場合
		if (entity.isAllHoliday(true) || entity.isAmHoliday(true) || entity.isPmHoliday(true)) {
			// 処理なし
			return;
		}
		// 時差出勤申請情報取得(承認済)及び確認
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			DifferenceRequestReferenceBeanInterface differenceRequest = timeReference()
				.differenceRequest(vo.getTargetDate());
			Date restStartTime = null;
			Date restEndTime = null;
			Date startTime = null;
			Date endTime = null;
			boolean isHalfHoliday = false;
			if (differenceRequest.isDifferenceTypeA(differenceRequestDto)) {
				// 時差出勤区分A
				restStartTime = differenceRequest.getDifferenceRestStartTimeTypeA(vo.getTargetDate(), startTime,
						endTime, isHalfHoliday);
				restEndTime = differenceRequest.getDifferenceRestEndTimeTypeA(vo.getTargetDate(), startTime, endTime,
						isHalfHoliday);
			} else if (differenceRequest.isDifferenceTypeB(differenceRequestDto)) {
				// 時差出勤区分B
				restStartTime = differenceRequest.getDifferenceRestStartTimeTypeB(vo.getTargetDate(), startTime,
						endTime, isHalfHoliday);
				restEndTime = differenceRequest.getDifferenceRestEndTimeTypeB(vo.getTargetDate(), startTime, endTime,
						isHalfHoliday);
			} else if (differenceRequest.isDifferenceTypeC(differenceRequestDto)) {
				// 時差出勤区分C
				restStartTime = differenceRequest.getDifferenceRestStartTimeTypeC(vo.getTargetDate(), startTime,
						endTime, isHalfHoliday);
				restEndTime = differenceRequest.getDifferenceRestEndTimeTypeC(vo.getTargetDate(), startTime, endTime,
						isHalfHoliday);
			} else if (differenceRequest.isDifferenceTypeD(differenceRequestDto)) {
				// 時差出勤区分D
				restStartTime = differenceRequest.getDifferenceRestStartTimeTypeD(vo.getTargetDate(), startTime,
						endTime, isHalfHoliday);
				restEndTime = differenceRequest.getDifferenceRestEndTimeTypeD(vo.getTargetDate(), startTime, endTime,
						isHalfHoliday);
			} else if (differenceRequest.isDifferenceTypeS(differenceRequestDto)) {
				// 時差出勤区分S
				restStartTime = differenceRequest.getDifferenceRestStartTimeTypeS(startTime, endTime,
						differenceRequestDto.getRequestStart(), isHalfHoliday);
				restEndTime = differenceRequest.getDifferenceRestEndTimeTypeS(startTime, endTime,
						differenceRequestDto.getRequestStart(), isHalfHoliday);
			}
			vo.setTxtRestStartHour1(DateUtility.getStringHour(restStartTime, vo.getTargetDate()));
			vo.setTxtRestStartMinute1(DateUtility.getStringMinute(restStartTime));
			vo.setTxtRestEndHour1(DateUtility.getStringHour(restEndTime, vo.getTargetDate()));
			vo.setTxtRestEndMinute1(DateUtility.getStringMinute(restEndTime));
			return;
		}
		// 勤務形態準備
		String workTypeCode = entity.getWorkType(false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		Date defaultDate = DateUtility.getDate(MospConst.DEFAULT_YEAR, Calendar.JANUARY + 1, 1);
		// 休憩1
		WorkTypeItemDtoInterface restStart1Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTSTART1);
		WorkTypeItemDtoInterface restEnd1Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTEND1);
		if (restStart1Dto != null && restEnd1Dto != null) {
			vo.setTxtRestStartHour1(DateUtility.getStringHour(restStart1Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestStartMinute1(DateUtility.getStringMinute(restStart1Dto.getWorkTypeItemValue()));
			vo.setTxtRestEndHour1(DateUtility.getStringHour(restEnd1Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestEndMinute1(DateUtility.getStringMinute(restEnd1Dto.getWorkTypeItemValue()));
		}
		// 休憩2
		WorkTypeItemDtoInterface restStart2Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTSTART2);
		WorkTypeItemDtoInterface restEnd2Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTEND2);
		if (restStart2Dto != null && restEnd2Dto != null) {
			vo.setTxtRestStartHour2(DateUtility.getStringHour(restStart2Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestStartMinute2(DateUtility.getStringMinute(restStart2Dto.getWorkTypeItemValue()));
			vo.setTxtRestEndHour2(DateUtility.getStringHour(restEnd2Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestEndMinute2(DateUtility.getStringMinute(restEnd2Dto.getWorkTypeItemValue()));
		}
		// 休憩3
		WorkTypeItemDtoInterface restStart3Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTSTART3);
		WorkTypeItemDtoInterface restEnd3Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTEND3);
		if (restStart3Dto != null && restEnd3Dto != null) {
			vo.setTxtRestStartHour3(DateUtility.getStringHour(restStart3Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestStartMinute3(DateUtility.getStringMinute(restStart3Dto.getWorkTypeItemValue()));
			vo.setTxtRestEndHour3(DateUtility.getStringHour(restEnd3Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestEndMinute3(DateUtility.getStringMinute(restEnd3Dto.getWorkTypeItemValue()));
		}
		// 休憩4
		WorkTypeItemDtoInterface restStart4Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTSTART4);
		WorkTypeItemDtoInterface restEnd4Dto = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeCode,
				targetDate, TimeConst.CODE_RESTEND4);
		if (restStart4Dto != null && restEnd4Dto != null) {
			vo.setTxtRestStartHour4(DateUtility.getStringHour(restStart4Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestStartMinute4(DateUtility.getStringMinute(restStart4Dto.getWorkTypeItemValue()));
			vo.setTxtRestEndHour4(DateUtility.getStringHour(restEnd4Dto.getWorkTypeItemValue(), defaultDate));
			vo.setTxtRestEndMinute4(DateUtility.getStringMinute(restEnd4Dto.getWorkTypeItemValue()));
		}
	}
	
	/**
	 * 登録されている休憩情報を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setRegisteredRest() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠データ休憩情報リスト取得
		List<RestDtoInterface> list = timeReference().rest().getRestList(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT);
		for (RestDtoInterface dto : list) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(dto.getRestStart(), vo.getTargetDate());
			String startMinute = DateUtility.getStringMinute(dto.getRestStart());
			String endHour = DateUtility.getStringHour(dto.getRestEnd(), vo.getTargetDate());
			String endMinute = DateUtility.getStringMinute(dto.getRestEnd());
			if (dto.getRest() == 1) {
				vo.setTxtRestStartHour1(startHour);
				vo.setTxtRestStartMinute1(startMinute);
				vo.setTxtRestEndHour1(endHour);
				vo.setTxtRestEndMinute1(endMinute);
			}
			if (dto.getRest() == 2) {
				vo.setTxtRestStartHour2(startHour);
				vo.setTxtRestStartMinute2(startMinute);
				vo.setTxtRestEndHour2(endHour);
				vo.setTxtRestEndMinute2(endMinute);
			}
			if (dto.getRest() == 3) {
				vo.setTxtRestStartHour3(startHour);
				vo.setTxtRestStartMinute3(startMinute);
				vo.setTxtRestEndHour3(endHour);
				vo.setTxtRestEndMinute3(endMinute);
			}
			if (dto.getRest() == 4) {
				vo.setTxtRestStartHour4(startHour);
				vo.setTxtRestStartMinute4(startMinute);
				vo.setTxtRestEndHour4(endHour);
				vo.setTxtRestEndMinute4(endMinute);
			}
			if (dto.getRest() == 5) {
				vo.setTxtRestStartHour5(startHour);
				vo.setTxtRestStartMinute5(startMinute);
				vo.setTxtRestEndHour5(endHour);
				vo.setTxtRestEndMinute5(endMinute);
			}
			if (dto.getRest() == 6) {
				vo.setTxtRestStartHour6(startHour);
				vo.setTxtRestStartMinute6(startMinute);
				vo.setTxtRestEndHour6(endHour);
				vo.setTxtRestEndMinute6(endMinute);
			}
		}
	}
	
	/**
	 * 外出情報を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setGoOut() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠データ外出情報参照クラス取得
		GoOutReferenceBeanInterface goOut = timeReference().goOut();
		// 勤怠情報取得
		AttendanceDtoInterface attendanceDto = timeReference().attendance().findForKey(vo.getPersonalId(),
				vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return;
		}
		// 個人ID、勤務日取得
		String personalId = vo.getPersonalId();
		Date date = attendanceDto.getWorkDate();
		// 合計時間設定
		vo.setLblPublicTime(getTimeTimeFormat(attendanceDto.getPublicTime()));
		vo.setLblPrivateTime(getTimeTimeFormat(attendanceDto.getPrivateTime()));
		vo.setLblMinutelyHolidayAInput(getTimeTimeFormat(attendanceDto.getMinutelyHolidayATime()));
		vo.setLblMinutelyHolidayBInput(getTimeTimeFormat(attendanceDto.getMinutelyHolidayBTime()));
		// 公用外出情報リストを取得
		List<GoOutDtoInterface> publicList = goOut.getPublicGoOutList(personalId, date);
		for (GoOutDtoInterface publicDto : publicList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(publicDto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(publicDto.getGoOutStart());
			String endHour = DateUtility.getStringHour(publicDto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(publicDto.getGoOutEnd());
			if (publicDto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtPublicStartHour1(startHour);
				vo.setTxtPublicStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtPublicEndHour1(endHour);
				vo.setTxtPublicEndMinute1(endMinute);
			}
			if (publicDto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtPublicStartHour2(
						DateUtility.getStringHour(publicDto.getGoOutStart(), attendanceDto.getWorkDate()));
				vo.setTxtPublicStartMinute2(DateUtility.getStringMinute(publicDto.getGoOutStart()));
				// 外出終了時刻。
				vo.setTxtPublicEndHour2(
						DateUtility.getStringHour(publicDto.getGoOutEnd(), attendanceDto.getWorkDate()));
				vo.setTxtPublicEndMinute2(DateUtility.getStringMinute(publicDto.getGoOutEnd()));
			}
		}
		// 私用外出情報リストを取得
		List<GoOutDtoInterface> privateList = goOut.getPrivateGoOutList(personalId, date);
		// 私用外出情報リスト毎に処理
		for (GoOutDtoInterface privateDto : privateList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(privateDto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(privateDto.getGoOutStart());
			String endHour = DateUtility.getStringHour(privateDto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(privateDto.getGoOutEnd());
			if (privateDto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtPrivateStartHour1(startHour);
				vo.setTxtPrivateStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtPrivateEndHour1(endHour);
				vo.setTxtPrivateEndMinute1(endMinute);
			}
			if (privateDto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtPrivateStartHour2(startHour);
				vo.setTxtPrivateStartMinute2(startMinute);
				// 外出終了時刻。
				vo.setTxtPrivateEndHour2(endHour);
				vo.setTxtPrivateEndMinute2(endMinute);
			}
		}
		// 分単位休暇Aリストを取得
		List<GoOutDtoInterface> minutelyHolidayAList = goOut.getMinutelyHolidayAList(personalId, date);
		// 分単位休暇Aリスト毎に処理
		for (GoOutDtoInterface minutelyHolidayADto : minutelyHolidayAList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(minutelyHolidayADto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(minutelyHolidayADto.getGoOutStart());
			String endHour = DateUtility.getStringHour(minutelyHolidayADto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(minutelyHolidayADto.getGoOutEnd());
			if (minutelyHolidayADto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayAStartHour1(startHour);
				vo.setTxtMinutelyHolidayAStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayAEndHour1(endHour);
				vo.setTxtMinutelyHolidayAEndMinute1(endMinute);
			}
			if (minutelyHolidayADto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayAStartHour2(startHour);
				vo.setTxtMinutelyHolidayAStartMinute2(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayAEndHour2(endHour);
				vo.setTxtMinutelyHolidayAEndMinute2(endMinute);
			}
			if (minutelyHolidayADto.getTimesGoOut() == 3) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayAStartHour3(startHour);
				vo.setTxtMinutelyHolidayAStartMinute3(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayAEndHour3(endHour);
				vo.setTxtMinutelyHolidayAEndMinute3(endMinute);
			}
			if (minutelyHolidayADto.getTimesGoOut() == 4) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayAStartHour4(startHour);
				vo.setTxtMinutelyHolidayAStartMinute4(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayAEndHour4(endHour);
				vo.setTxtMinutelyHolidayAEndMinute4(endMinute);
			}
		}
		// 分単位休暇Bリストを取得
		List<GoOutDtoInterface> minutelyHolidayBList = goOut.getMinutelyHolidayBList(personalId, date);
		// 分単位休暇Bリスト毎に処理
		for (GoOutDtoInterface minutelyHolidayBDto : minutelyHolidayBList) {
			// 始業終業時刻取得
			String startHour = DateUtility.getStringHour(minutelyHolidayBDto.getGoOutStart(), date);
			String startMinute = DateUtility.getStringMinute(minutelyHolidayBDto.getGoOutStart());
			String endHour = DateUtility.getStringHour(minutelyHolidayBDto.getGoOutEnd(), date);
			String endMinute = DateUtility.getStringMinute(minutelyHolidayBDto.getGoOutEnd());
			if (minutelyHolidayBDto.getTimesGoOut() == 1) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayBStartHour1(startHour);
				vo.setTxtMinutelyHolidayBStartMinute1(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayBEndHour1(endHour);
				vo.setTxtMinutelyHolidayBEndMinute1(endMinute);
			}
			if (minutelyHolidayBDto.getTimesGoOut() == 2) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayBStartHour2(startHour);
				vo.setTxtMinutelyHolidayBStartMinute2(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayBEndHour2(endHour);
				vo.setTxtMinutelyHolidayBEndMinute2(endMinute);
			}
			if (minutelyHolidayBDto.getTimesGoOut() == 3) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayBStartHour3(startHour);
				vo.setTxtMinutelyHolidayBStartMinute3(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayBEndHour3(endHour);
				vo.setTxtMinutelyHolidayBEndMinute3(endMinute);
			}
			if (minutelyHolidayBDto.getTimesGoOut() == 4) {
				// 外出開始時刻。
				vo.setTxtMinutelyHolidayBStartHour4(startHour);
				vo.setTxtMinutelyHolidayBStartMinute4(startMinute);
				// 外出終了時刻。
				vo.setTxtMinutelyHolidayBEndHour4(endHour);
				vo.setTxtMinutelyHolidayBEndMinute4(endMinute);
			}
		}
	}
	
	/**
	 * 遅刻早退情報
	 * @throws MospException 例外発生時
	 */
	protected void tardinessLeaveearlyInfo() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// DTO準備
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getLblEmployeeCode(),
				vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		// 遅刻早退情報
		if (dto != null) {
			vo.setLblLateTime(getTimeTimeFormat(dto.getLateTime()));
			vo.setPltLateReason(dto.getLateReason());
			vo.setPltLateCertificate(String.valueOf(dto.getLateCertificate()));
			vo.setTxtLateComment(dto.getLateComment());
			vo.setLblLeaveEarlyTime(getTimeTimeFormat(dto.getLeaveEarlyTime()));
			vo.setPltLeaveEarlyReason(dto.getLeaveEarlyReason());
			vo.setPltLeaveEarlyCertificate(String.valueOf(dto.getLeaveEarlyCertificate()));
			vo.setTxtLeaveEarlyComment(dto.getLeaveEarlyComment());
		}
	}
	
	/**
	 * 割増情報
	 * @throws MospException 例外発生時
	 */
	protected void premiumInfo() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// DTO準備
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getLblEmployeeCode(),
				vo.getTargetDate(), TimeBean.TIMES_WORK_DEFAULT);
		// 割増情報
		if (dto != null) {
			vo.setLblOvertime(getTimeTimeFormat(dto.getOvertime()));
			vo.setLblOvertimeIn(getTimeTimeFormat(dto.getOvertimeIn()));
			vo.setLblOvertimeOut(getTimeTimeFormat(dto.getOvertimeOut()));
			vo.setLblLateNightTime(getTimeTimeFormat(dto.getLateNightTime()));
			vo.setLblSpecificWorkTimeIn(getTimeTimeFormat(dto.getSpecificWorkTime()));
			vo.setLblSpecificWorkTimeOver("");
			vo.setLblLegalWorkTime(getTimeTimeFormat(dto.getLegalWorkTime()));
			vo.setLblDecreaseTime(getTimeTimeFormat(dto.getDecreaseTime()));
		}
	}
	
	/**
	 * 手当情報
	 * @throws MospException 例外発生時
	 */
	protected void allowanceInfo() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 手当情報
		// 勤務回数は固定で「1」
		AllowanceDtoInterface dto = null;
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO1);
		if (dto != null) {
			vo.setPltAllowance1(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO2);
		if (dto != null) {
			vo.setPltAllowance2(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO3);
		if (dto != null) {
			vo.setPltAllowance3(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO4);
		if (dto != null) {
			vo.setPltAllowance4(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO5);
		if (dto != null) {
			vo.setPltAllowance5(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO6);
		if (dto != null) {
			vo.setPltAllowance6(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO7);
		if (dto != null) {
			vo.setPltAllowance7(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO8);
		if (dto != null) {
			vo.setPltAllowance8(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INFO9);
		if (dto != null) {
			vo.setPltAllowance9(String.valueOf(dto.getAllowance()));
		}
		dto = timeReference().allowance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT, TimeConst.CODE_ALLOWANCE_CODE_INF10);
		if (dto != null) {
			vo.setPltAllowance10(String.valueOf(dto.getAllowance()));
		}
	}
	
	/**
	 * ワークフロー関連情報を設定する。<br>
	 * 承認状況・ワークフローコメント・申請モードを設定する。<br>
	 * @throws MospException ワークフロー情報の取得に失敗した場合
	 */
	protected void attendanceInfoApprovalStatus() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠情報取得及び確認
		AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getPersonalId(), vo.getTargetDate(),
				TimeBean.TIMES_WORK_DEFAULT);
		if (dto == null) {
			return;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		WorkflowCommentDtoInterface commentDto = reference().workflowComment()
			.getLatestWorkflowCommentInfo(dto.getWorkflow());
		// ワークフロー情報確認
		if (workflowDto == null) {
			return;
		}
		// 勤怠情報承認状況設定
		vo.setLblAttendanceState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		// ワークフローコメント情報設定がない場合
		if (commentDto == null) {
			// 申請者名前
			vo.setLblAttendanceApprover(
					reference().human().getHumanName(workflowDto.getPersonalId(), workflowDto.getWorkflowDate()));
			return;
		}
		vo.setLblAttendanceComment(getWorkflowCommentDtoComment(workflowDto, commentDto));
		vo.setLblAttendanceApprover(getWorkflowCommentDtoLastFirstName(workflowDto, commentDto));
	}
	
	/**
	 * 残業申請状況
	 * @throws MospException 例外発生時
	 */
	protected void overtimeApplicationStatus() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		StringBuffer transferParams = new StringBuffer();
		transferParams.append("'");
		transferParams.append(PlatformConst.PRM_TRANSFERRED_ACTION);
		transferParams.append("', '");
		transferParams.append(OvertimeRequestAction.class.getName());
		transferParams.append("'");
		vo.setLblOvertimeTransferParams(transferParams.toString());
		vo.setLblOvertimeCmd(CMD_TRANSFER);
		// DTO準備
		AttendanceDtoInterface attendanceDto = timeReference().attendance().findForKey(vo.getPersonalId(),
				vo.getTargetDate(), 1);
		List<OvertimeRequestDtoInterface> list = timeReference().overtimeRequest()
			.getOvertimeRequestList(vo.getPersonalId(), vo.getTargetDate(), vo.getTargetDate());
		List<OvertimeRequestDtoInterface> overRequestList = new ArrayList<OvertimeRequestDtoInterface>();
		for (OvertimeRequestDtoInterface dto : list) {
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 取下の場合
				continue;
			}
			overRequestList.add(dto);
		}
		int size = overRequestList.size();
		String[] aryLblOvertimeType = new String[size];
		String[] aryLblOvertimeSchedule = new String[size];
		String[] aryLblOvertimeResult = new String[size];
		String[] aryLblOvertimeReason = new String[size];
		String[] aryLblOvertimeState = new String[size];
		String[] aryLblOvertimeComment = new String[size];
		String[] aryLblOvertimeApprover = new String[size];
		for (int i = 0; i < overRequestList.size(); i++) {
			OvertimeRequestDtoInterface dto = overRequestList.get(i);
			long workflow = dto.getWorkflow();
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
			WorkflowCommentDtoInterface commentDto = reference().workflowComment()
				.getLatestWorkflowCommentInfo(workflow);
			int overtimeType = dto.getOvertimeType();
			aryLblOvertimeType[i] = getOvertimeTypeName(overtimeType);
			aryLblOvertimeSchedule[i] = getTimeTimeFormat(dto.getRequestTime());
			aryLblOvertimeResult[i] = mospParams.getName("Hyphen");
			if (attendanceDto != null) {
				int result = 0;
				if (overtimeType == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
					// 勤務前残業
					result = attendanceDto.getOvertimeBefore();
				} else if (overtimeType == TimeConst.CODE_OVERTIME_WORK_AFTER) {
					// 勤務後残業
					result = attendanceDto.getOvertimeAfter();
				}
				aryLblOvertimeResult[i] = getTimeTimeFormat(result);
			}
			aryLblOvertimeReason[i] = dto.getRequestReason();
			aryLblOvertimeState[i] = getStatusStageValueView(workflowDto.getWorkflowStatus(),
					workflowDto.getWorkflowStage());
			aryLblOvertimeComment[i] = getWorkflowCommentDtoComment(workflowDto, commentDto);
			aryLblOvertimeApprover[i] = getWorkflowCommentDtoLastFirstName(workflowDto, commentDto);
			if (i == 0 && (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus()))) {
				// 下書又は差戻の場合
				StringBuffer params = new StringBuffer();
				params.append("'");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTION);
				params.append("', '");
				params.append(OvertimeRequestAction.class.getName());
				params.append("', '");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
				params.append("', '");
				params.append(DateUtility.getStringDate(dto.getRequestDate()));
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_TYPE);
				params.append("', '");
				params.append(dto.getOvertimeType());
				params.append("'");
				vo.setLblOvertimeTransferParams(params.toString());
				vo.setLblOvertimeCmd(CMD_TRANSFER);
			}
		}
		// VOに項目を設定
		vo.setLblOvertimeType(aryLblOvertimeType);
		vo.setLblOvertimeSchedule(aryLblOvertimeSchedule);
		vo.setLblOvertimeResult(aryLblOvertimeResult);
		vo.setLblOvertimeReason(aryLblOvertimeReason);
		vo.setLblOvertimeState(aryLblOvertimeState);
		vo.setLblOvertimeComment(aryLblOvertimeComment);
		vo.setLblOvertimeApprover(aryLblOvertimeApprover);
	}
	
	/**
	 * 休暇申請状況
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException 例外発生時
	 */
	protected void vacationApplicationStatus(RequestUtilBeanInterface requestUtil) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		StringBuffer transferParams = new StringBuffer();
		transferParams.append("'");
		transferParams.append(PlatformConst.PRM_TRANSFERRED_ACTION);
		transferParams.append("', '");
		transferParams.append(HolidayRequestAction.class.getName());
		transferParams.append("'");
		vo.setLblHolidayTransferParams(transferParams.toString());
		vo.setLblHolidayCmd(CMD_TRANSFER);
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(vo.getPersonalId(), vo.getTargetDate(),
				requestUtil);
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		String workTypeAbbr = MospUtility.getCodeName(workTypeCode, getWorkTypeArray("", false, false));
		// DTO準備
		List<HolidayRequestDtoInterface> list = timeReference().holidayRequest()
			.getHolidayRequestList(vo.getPersonalId(), vo.getTargetDate());
		List<HolidayRequestDtoInterface> holidayRequestList = new ArrayList<HolidayRequestDtoInterface>();
		for (HolidayRequestDtoInterface dto : list) {
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			// ワークフロー情報がないか取下の場合
			if (workflowDto == null || PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				continue;
			}
			holidayRequestList.add(dto);
		}
		int size = holidayRequestList.size();
		String[] aryLblHolidayType = new String[size];
		String[] aryLblHolidayLength = new String[size];
		String[] aryLblHolidayTime = new String[size];
		String[] aryLblHolidayReason = new String[size];
		String[] aryLblHolidayWorkType = new String[size];
		String[] aryLblHolidayState = new String[size];
		String[] aryLblHolidayApprover = new String[size];
		String[] aryLblHolidayComment = new String[size];
		for (int i = 0; i < holidayRequestList.size(); i++) {
			HolidayRequestDtoInterface dto = holidayRequestList.get(i);
			long workflow = dto.getWorkflow();
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
			WorkflowCommentDtoInterface commentDto = reference().workflowComment()
				.getLatestWorkflowCommentInfo(workflow);
			aryLblHolidayType[i] = getHolidayTypeName(dto.getHolidayType1(), dto.getHolidayType2(),
					dto.getRequestStartDate());
			aryLblHolidayLength[i] = getHolidayRange(dto.getHolidayRange());
			aryLblHolidayTime[i] = getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate());
			aryLblHolidayReason[i] = dto.getRequestReason();
			aryLblHolidayWorkType[i] = workTypeAbbr;
			aryLblHolidayState[i] = getStatusStageValueView(workflowDto.getWorkflowStatus(),
					workflowDto.getWorkflowStage());
			aryLblHolidayApprover[i] = getWorkflowCommentDtoLastFirstName(workflowDto, commentDto);
			aryLblHolidayComment[i] = getWorkflowCommentDtoComment(workflowDto, commentDto);
			if (i == 0 && (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus()))) {
				// 下書又は差戻の場合
				StringBuffer params = new StringBuffer();
				params.append("'");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTION);
				params.append("', '");
				params.append(HolidayRequestAction.class.getName());
				params.append("', '");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
				params.append("', '");
				params.append(DateUtility.getStringDate(dto.getRequestStartDate()));
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE1);
				params.append("', '");
				params.append(dto.getHolidayType1());
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE2);
				params.append("', '");
				params.append(dto.getHolidayType2());
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_HOLIDAY_RANGE);
				params.append("', '");
				params.append(dto.getHolidayRange());
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_START_TIME);
				params.append("', '");
				params.append(DateUtility.getStringTime(dto.getStartTime(), dto.getRequestStartDate()));
				params.append("'");
				vo.setLblHolidayTransferParams(params.toString());
				vo.setLblHolidayCmd(CMD_TRANSFER);
			}
		}
		// VOに項目を設定
		vo.setLblHolidayType(aryLblHolidayType);
		vo.setLblHolidayLength(aryLblHolidayLength);
		vo.setLblHolidayTime(aryLblHolidayTime);
		vo.setLblHolidayReason(aryLblHolidayReason);
		vo.setLblHolidayWorkType(aryLblHolidayWorkType);
		vo.setLblHolidayState(aryLblHolidayState);
		vo.setLblHolidayComment(aryLblHolidayComment);
		vo.setLblHolidayApprover(aryLblHolidayApprover);
	}
	
	/**
	 * 休日出勤申請状況
	 * @throws MospException 例外発生時
	 */
	protected void holidayWorkApplicationStatus() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		StringBuffer transferParams = new StringBuffer();
		transferParams.append("'");
		transferParams.append(PlatformConst.PRM_TRANSFERRED_ACTION);
		transferParams.append("', '");
		transferParams.append(WorkOnHolidayRequestAction.class.getName());
		transferParams.append("'");
		vo.setLblWorkOnHolidayTransferParams(transferParams.toString());
		vo.setLblWorkOnHolidayCmd(CMD_TRANSFER);
		// 休日出勤申請からレコードを取得
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest()
			.findForKeyOnWorkflow(vo.getPersonalId(), vo.getTargetDate());
		if (dto == null) {
			return;
		}
		long workflow = dto.getWorkflow();
		// ワークフロー情報の取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		// 最新のワークフローコメント取得
		WorkflowCommentDtoInterface commentDto = reference().workflowComment()
			.getLatestWorkflowCommentInfo(dto.getWorkflow());
		// 振替休日情報の取得
		List<SubstituteDtoInterface> list = timeReference().substitute().getSubstituteList(workflow);
		// 休日出勤申請状況
		String workRange = "";
		int substitute = dto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			// 午前の場合
			workRange = mospParams.getName("AnteMeridiem");
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 午後の場合
			workRange = mospParams.getName("PostMeridiem");
		}
		vo.setLblWorkOnHolidayDate(getStringDateAndDay(dto.getRequestDate()) + workRange);
		vo.setLblWorkOnHolidayTime(getWorkOnHolidaySchedule(dto));
		vo.setLblSubStituteDate("");
		for (SubstituteDtoInterface substituteDto : list) {
			StringBuffer sb = new StringBuffer();
			sb.append(getStringDateAndDay(substituteDto.getSubstituteDate()));
			int substituteRange = substituteDto.getSubstituteRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休の場合
				sb.append(mospParams.getName("AnteMeridiem"));
			} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午後休の場合
				sb.append(mospParams.getName("PostMeridiem"));
			}
			vo.setLblSubStituteDate(sb.toString());
			break;
		}
		vo.setLblWorkOnHolidayReason(dto.getRequestReason());
		// 状態
		vo.setLblWorkOnHolidayState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblWorkOnHolidayComment(getWorkflowCommentDtoComment(workflowDto, commentDto));
		vo.setLblWorkOnHolidayApprover(getWorkflowCommentDtoLastFirstName(workflowDto, commentDto));
		if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 下書又は差戻の場合
			StringBuffer params = new StringBuffer();
			params.append("'");
			params.append(PlatformConst.PRM_TRANSFERRED_ACTION);
			params.append("', '");
			params.append(WorkOnHolidayRequestAction.class.getName());
			params.append("', '");
			params.append(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
			params.append("', '");
			params.append(DateUtility.getStringDate(dto.getRequestDate()));
			params.append("'");
			vo.setLblWorkOnHolidayTransferParams(params.toString());
			vo.setLblWorkOnHolidayCmd(CMD_TRANSFER);
		}
	}
	
	/**
	 * 代休申請状況
	 * @throws MospException 例外発生時
	 */
	protected void compensatoryleaveApplicationStatus() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		StringBuffer transferParams = new StringBuffer();
		transferParams.append("'");
		transferParams.append(PlatformConst.PRM_TRANSFERRED_ACTION);
		transferParams.append("', '");
		transferParams.append(SubHolidayRequestAction.class.getName());
		transferParams.append("'");
		vo.setLblSubHolidayTransferParams(transferParams.toString());
		vo.setLblSubHolidayCmd(CMD_TRANSFER);
		// DTO準備
		// 年月日は締め日
		List<SubHolidayRequestDtoInterface> list = timeReference().subHolidayRequest()
			.getSubHolidayRequestList(vo.getPersonalId(), vo.getTargetDate());
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = new ArrayList<SubHolidayRequestDtoInterface>();
		for (SubHolidayRequestDtoInterface dto : list) {
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 取下の場合
				continue;
			}
			subHolidayRequestList.add(dto);
		}
		int size = subHolidayRequestList.size();
		String[] aryLblSubHolidayDate = new String[size];
		String[] aryLblSubHolidayWorkDate = new String[size];
		String[] aryLblSubHolidayState = new String[size];
		String[] aryLblSubHolidayLength = new String[size];
		String[] aryLblSubHolidayComment = new String[size];
		String[] aryLblSubHolidayApprover = new String[size];
		for (int i = 0; i < subHolidayRequestList.size(); i++) {
			SubHolidayRequestDtoInterface dto = subHolidayRequestList.get(i);
			long workflow = dto.getWorkflow();
			// ワークフロー情報の取得
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
			WorkflowCommentDtoInterface commentDto = reference().workflowComment()
				.getLatestWorkflowCommentInfo(workflow);
			aryLblSubHolidayDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryLblSubHolidayLength[i] = getHolidayRange(dto.getHolidayRange());
			aryLblSubHolidayWorkDate[i] = DateUtility.getStringDateAndDay(dto.getWorkDate());
			aryLblSubHolidayState[i] = getStatusStageValueView(workflowDto.getWorkflowStatus(),
					workflowDto.getWorkflowStage());
			aryLblSubHolidayComment[i] = getWorkflowCommentDtoComment(workflowDto, commentDto);
			aryLblSubHolidayApprover[i] = getWorkflowCommentDtoLastFirstName(workflowDto, commentDto);
			if (i == 0 && (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus()))) {
				// 下書又は差戻の場合
				StringBuffer params = new StringBuffer();
				params.append("'");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTION);
				params.append("', '");
				params.append(SubHolidayRequestAction.class.getName());
				params.append("', '");
				params.append(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
				params.append("', '");
				params.append(DateUtility.getStringDate(dto.getRequestDate()));
				params.append("', '");
				params.append(TimeConst.PRM_TRANSFERRED_TYPE);
				params.append("', '");
				params.append(dto.getHolidayRange());
				params.append("'");
				vo.setLblSubHolidayTransferParams(params.toString());
				vo.setLblSubHolidayCmd(CMD_TRANSFER);
			}
		}
		// VOに項目を設定
		vo.setLblSubHolidayDate(aryLblSubHolidayDate);
		vo.setLblSubHolidayLength(aryLblSubHolidayLength);
		vo.setLblSubHolidayWorkDate(aryLblSubHolidayWorkDate);
		vo.setLblSubHolidayState(aryLblSubHolidayState);
		vo.setLblSubHolidayComment(aryLblSubHolidayComment);
		vo.setLblSubHolidayApprover(aryLblSubHolidayApprover);
	}
	
	/**
	 * 勤務形態変更申請状況
	 * @throws MospException 例外発生時
	 */
	protected void workTypeChangeApplicationStatus() throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		WorkTypeChangeRequestReferenceBeanInterface workTypeChangeRequest = timeReference().workTypeChangeRequest();
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = workTypeChangeRequest
			.findForKeyOnWorkflow(vo.getPersonalId(), vo.getTargetDate());
		if (workTypeChangeRequestDto == null) {
			return;
		}
		long workflow = workTypeChangeRequestDto.getWorkflow();
		// ワークフロー情報の取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		if (workflowDto == null) {
			return;
		}
		if (reference().workflowIntegrate().isWithDrawn(workflowDto)) {
			// 取下の場合
			return;
		}
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		// 勤務形態変更申請状況
		vo.setLblWorkTypeChangeDate(getStringDateAndDay(workTypeChangeRequestDto.getRequestDate()));
		vo.setLblWorkTypeChangeBeforeWorkType(
				time().workTypeChangeRequestRegist().getScheduledWorkTypeName(workTypeChangeRequestDto));
		vo.setLblWorkTypeChangeAfterWorkType(timeReference().workType().getWorkTypeAbbrAndTime(
				workTypeChangeRequestDto.getWorkTypeCode(), workTypeChangeRequestDto.getRequestDate()));
		vo.setLblWorkTypeChangeReason(workTypeChangeRequestDto.getRequestReason());
		vo.setLblWorkTypeChangeState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblWorkTypeChangeComment(getWorkflowCommentDtoComment(workflowDto, commentDto));
		vo.setLblWorkTypeChangeApprover(getWorkflowCommentDtoLastFirstName(workflowDto, commentDto));
	}
	
	/**
	 * 時差出勤申請状況
	 * @throws MospException 例外発生時
	 */
	protected void timedifferenceWorkApplicationStatus() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference().differenceRequest();
		StringBuffer transferParams = new StringBuffer();
		transferParams.append("'");
		transferParams.append(PlatformConst.PRM_TRANSFERRED_ACTION);
		transferParams.append("', '");
		transferParams.append(DifferenceRequestAction.class.getName());
		transferParams.append("'");
		vo.setLblDifferenceTransferParams(transferParams.toString());
		vo.setLblDifferenceCmd(CMD_TRANSFER);
		// DTO準備
		// 年月日は締め日
		DifferenceRequestDtoInterface dto = differenceRequest.findForKeyOnWorkflow(vo.getPersonalId(),
				vo.getTargetDate());
		if (dto == null) {
			return;
		}
		long workflow = dto.getWorkflow();
		// ワークフロー情報の取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		// 時差出勤申請状況
		vo.setLblDifferenceDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		vo.setLblDifferenceWorkType(getBeforeDifferenceRequestWorkTypeAbbr());
		vo.setLblDifferenceWorkTime(differenceRequest.getDifferenceTime(dto));
		vo.setLblDifferenceReason(dto.getRequestReason());
		// 状態
		vo.setLblDifferenceState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblDifferenceComment(getWorkflowCommentDtoComment(workflowDto, commentDto));
		vo.setLblDifferenceApprover(getWorkflowCommentDtoLastFirstName(workflowDto, commentDto));
		if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 下書又は差戻の場合
			StringBuffer params = new StringBuffer();
			params.append("'");
			params.append(PlatformConst.PRM_TRANSFERRED_ACTION);
			params.append("', '");
			params.append(DifferenceRequestAction.class.getName());
			params.append("', '");
			params.append(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
			params.append("', '");
			params.append(DateUtility.getStringDate(dto.getRequestDate()));
			params.append("'");
			vo.setLblDifferenceTransferParams(params.toString());
			vo.setLblDifferenceCmd(CMD_TRANSFER);
		}
	}
	
	/**
	 * 勤怠詳細画面の初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setDefaultValues() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_APPLIED);
		// 対象日取得
		Date targetDate = vo.getTargetDate();
		vo.setLblYear(String.valueOf(DateUtility.getYear(targetDate)));
		vo.setLblMonth(String.valueOf(DateUtility.getMonth(targetDate)));
		vo.setLblDay(String.valueOf(DateUtility.getDay(targetDate)));
		vo.setLblDayOfTheWeek(DateUtility.getStringDayOfWeek(targetDate));
		vo.setLblWorkDayOfWeekStyle(HolidayUtility.getWorkDayOfWeekStyle(targetDate, mospParams));
		vo.setCkbDirectStart("0");
		vo.setCkbDirectEnd("0");
		vo.setCkbForgotRecordWorkStart("0");
		vo.setCkbNotRecordWorkStart("0");
		vo.setTmdAttendanceId("0");
		vo.setTxtCorrectionReason("");
		vo.setLblCorrectionHistory("");
		vo.setTxtStartTimeHour("");
		vo.setTxtStartTimeMinute("");
		vo.setLblStartTime("");
		vo.setTxtEndTimeHour("");
		vo.setTxtEndTimeMinute("");
		vo.setLblEndTime("");
		vo.setLblWorkTime("");
		vo.setLblUnpaidShortTime("");
		vo.setLblRestTime("");
		vo.setLblOverRestTime("");
		vo.setLblNightRestTime("");
		vo.setLblPublicTime("");
		vo.setLblPrivateTime("");
		vo.setTxtRestStartHour1("");
		vo.setTxtRestStartMinute1("");
		vo.setTxtRestEndHour1("");
		vo.setTxtRestEndMinute1("");
		vo.setTxtRestStartHour2("");
		vo.setTxtRestStartMinute2("");
		vo.setTxtRestEndHour2("");
		vo.setTxtRestEndMinute2("");
		vo.setTxtRestStartHour3("");
		vo.setTxtRestStartMinute3("");
		vo.setTxtRestEndHour3("");
		vo.setTxtRestEndMinute3("");
		vo.setTxtRestStartHour4("");
		vo.setTxtRestStartMinute4("");
		vo.setTxtRestEndHour4("");
		vo.setTxtRestEndMinute4("");
		vo.setTxtRestStartHour5("");
		vo.setTxtRestStartMinute5("");
		vo.setTxtRestEndHour5("");
		vo.setTxtRestEndMinute5("");
		vo.setTxtRestStartHour6("");
		vo.setTxtRestStartMinute6("");
		vo.setTxtRestEndHour6("");
		vo.setTxtRestEndMinute6("");
		vo.setTxtPublicStartHour1("");
		vo.setTxtPublicStartMinute1("");
		vo.setTxtPublicEndHour1("");
		vo.setTxtPublicEndMinute1("");
		vo.setTxtPublicStartHour2("");
		vo.setTxtPublicStartMinute2("");
		vo.setTxtPublicEndHour2("");
		vo.setTxtPublicEndMinute2("");
		vo.setTxtPrivateStartHour1("");
		vo.setTxtPrivateStartMinute1("");
		vo.setTxtPrivateEndHour1("");
		vo.setTxtPrivateEndMinute1("");
		vo.setTxtPrivateStartHour2("");
		vo.setTxtPrivateStartMinute2("");
		vo.setTxtPrivateEndHour2("");
		vo.setTxtPrivateEndMinute2("");
		vo.setLblLateTime("");
		vo.setPltLateReason("");
		vo.setPltLateCertificate("");
		vo.setTxtLateComment("");
		vo.setLblLeaveEarlyTime("");
		vo.setPltLeaveEarlyReason("");
		vo.setPltLeaveEarlyCertificate("");
		vo.setTxtLeaveEarlyComment("");
		// 分単位休暇A
		vo.setLblMinutelyHolidayAInput("");
		vo.setTxtMinutelyHolidayAStartHour1("");
		vo.setTxtMinutelyHolidayAStartMinute1("");
		vo.setTxtMinutelyHolidayAEndHour1("");
		vo.setTxtMinutelyHolidayAEndMinute1("");
		vo.setTxtMinutelyHolidayAStartHour2("");
		vo.setTxtMinutelyHolidayAStartMinute2("");
		vo.setTxtMinutelyHolidayAEndHour2("");
		vo.setTxtMinutelyHolidayAEndMinute2("");
		vo.setTxtMinutelyHolidayAStartHour3("");
		vo.setTxtMinutelyHolidayAStartMinute3("");
		vo.setTxtMinutelyHolidayAEndHour3("");
		vo.setTxtMinutelyHolidayAEndMinute3("");
		vo.setTxtMinutelyHolidayAStartHour4("");
		vo.setTxtMinutelyHolidayAStartMinute4("");
		vo.setTxtMinutelyHolidayAEndHour4("");
		vo.setTxtMinutelyHolidayAEndMinute4("");
		vo.setCkbAllMinutelyHolidayA(MospConst.CHECKBOX_OFF);
		// 分単位休暇B
		vo.setLblMinutelyHolidayBInput("");
		vo.setTxtMinutelyHolidayBStartHour1("");
		vo.setTxtMinutelyHolidayBStartMinute1("");
		vo.setTxtMinutelyHolidayBEndHour1("");
		vo.setTxtMinutelyHolidayBEndMinute1("");
		vo.setTxtMinutelyHolidayBStartHour2("");
		vo.setTxtMinutelyHolidayBStartMinute2("");
		vo.setTxtMinutelyHolidayBEndHour2("");
		vo.setTxtMinutelyHolidayBEndMinute2("");
		vo.setTxtMinutelyHolidayBStartHour3("");
		vo.setTxtMinutelyHolidayBStartMinute3("");
		vo.setTxtMinutelyHolidayBEndHour3("");
		vo.setTxtMinutelyHolidayBEndMinute3("");
		vo.setTxtMinutelyHolidayBStartHour4("");
		vo.setTxtMinutelyHolidayBStartMinute4("");
		vo.setTxtMinutelyHolidayBEndHour4("");
		vo.setTxtMinutelyHolidayBEndMinute4("");
		vo.setCkbAllMinutelyHolidayB(MospConst.CHECKBOX_OFF);
		vo.setLblOvertime("");
		vo.setLblOvertimeIn("");
		vo.setLblOvertimeOut("");
		vo.setLblLateNightTime("");
		vo.setLblSpecificWorkTimeIn("");
		vo.setLblSpecificWorkTimeOver("");
		vo.setLblLegalWorkTime("");
		vo.setLblHolidayWorkTime("");
		vo.setLblDecreaseTime("");
		vo.setLblAttendanceComment("");
		vo.setLblAttendanceState("");
		vo.setLblAttendanceApprover("");
		vo.setLblOvertimeType(new String[0]);
		vo.setLblOvertimeSchedule(new String[0]);
		vo.setLblOvertimeResult(new String[0]);
		vo.setLblOvertimeReason(new String[0]);
		vo.setLblOvertimeState(new String[0]);
		vo.setLblOvertimeComment(new String[0]);
		vo.setLblOvertimeApprover(new String[0]);
		vo.setLblHolidayType(new String[0]);
		vo.setLblHolidayLength(new String[0]);
		vo.setLblHolidayTime(new String[0]);
		vo.setLblHolidayReason(new String[0]);
		vo.setLblHolidayWorkType(new String[0]);
		vo.setLblHolidayState(new String[0]);
		vo.setLblHolidayComment(new String[0]);
		vo.setLblHolidayApprover(new String[0]);
		vo.setLblWorkOnHolidayDate("");
		vo.setLblWorkOnHolidayTime("");
		vo.setLblSubStituteDate("");
		vo.setLblWorkOnHolidayReason("");
		vo.setLblWorkOnHolidayState("");
		vo.setLblWorkOnHolidayComment("");
		vo.setLblWorkOnHolidayApprover("");
		vo.setLblSubHolidayDate(new String[0]);
		vo.setLblSubHolidayLength(new String[0]);
		vo.setLblSubHolidayWorkDate(new String[0]);
		vo.setLblSubHolidayState(new String[0]);
		vo.setLblSubHolidayComment(new String[0]);
		vo.setLblSubHolidayApprover(new String[0]);
		// 勤務形態変更申請
		vo.setLblWorkTypeChangeDate("");
		vo.setLblWorkTypeChangeBeforeWorkType("");
		vo.setLblWorkTypeChangeAfterWorkType("");
		vo.setLblWorkTypeChangeReason("");
		vo.setLblWorkTypeChangeState("");
		vo.setLblWorkTypeChangeComment("");
		vo.setLblWorkTypeChangeApprover("");
		vo.setLblDifferenceDate("");
		vo.setLblDifferenceWorkType("");
		vo.setLblDifferenceWorkTime("");
		vo.setLblDifferenceReason("");
		vo.setLblDifferenceState("");
		vo.setLblDifferenceComment("");
		vo.setLblDifferenceApprover("");
		vo.setLblGeneralWorkTime("");
		vo.setTxtTimeComment("");
		vo.setTxtRemarks("");
		vo.setPltAllowance1("0");
		vo.setPltAllowance2("0");
		vo.setPltAllowance3("0");
		vo.setPltAllowance4("0");
		vo.setPltAllowance5("0");
		vo.setPltAllowance6("0");
		vo.setPltAllowance7("0");
		vo.setPltAllowance8("0");
		vo.setPltAllowance9("0");
		vo.setPltAllowance10("0");
		vo.setPltWorkType("");
		// 申請モードの初期化
		vo.setModeCardEdit("");
		// 承認者欄の初期化
		vo.setAryPltLblApproverSetting(new String[0]);
		vo.setPltApproverSetting1("");
		vo.setPltApproverSetting2("");
		vo.setPltApproverSetting3("");
		vo.setPltApproverSetting4("");
		vo.setPltApproverSetting5("");
		vo.setPltApproverSetting6("");
		vo.setPltApproverSetting7("");
		vo.setPltApproverSetting8("");
		vo.setPltApproverSetting9("");
		vo.setPltApproverSetting10("");
	}
	
	/**
	 * 申請状況を設定する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException 例外発生時
	 */
	protected void setApplicationStatus(RequestUtilBeanInterface requestUtil) throws MospException {
		// 残業申請状況
		overtimeApplicationStatus();
		// 休暇申請状況
		vacationApplicationStatus(requestUtil);
		// 休日出勤申請状況
		holidayWorkApplicationStatus();
		// 代休申請状況
		compensatoryleaveApplicationStatus();
		// 勤務形態変更申請状況
		workTypeChangeApplicationStatus();
		// 時差出勤申請状況
		timedifferenceWorkApplicationStatus();
	}
	
	/**
	 * リクエストされた値を設定する。<br>
	 */
	protected void setTransferredValues() {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		if (getTransferredStartHour() != null) {
			// 始業時刻
			vo.setTxtStartTimeHour(getTransferredStartHour());
			vo.setTxtStartTimeMinute(getTransferredStartMinute());
		}
		if (getTransferredEndHour() != null) {
			// 終業時刻
			vo.setTxtEndTimeHour(getTransferredEndHour());
			vo.setTxtEndTimeMinute(getTransferredEndMinute());
		}
		if (getTransferredDirectStart() != null) {
			// 直行
			vo.setCkbDirectStart(getTransferredDirectStart());
		} else if (getTimeRecordDirectStart() != null) {
			// 打刻から遷移した場合の直行
			vo.setCkbDirectStart(getTimeRecordDirectStart());
		}
		if (getTransferredDirectEnd() != null) {
			// 直帰
			vo.setCkbDirectEnd(getTransferredDirectEnd());
		} else if (getTimeRecordDirectEnd() != null) {
			// 打刻から遷移した場合の直帰
			vo.setCkbDirectEnd(getTimeRecordDirectEnd());
		}
		if (getTransferredTimeComment() != null) {
			// 勤怠コメント
			vo.setTxtTimeComment(getTransferredTimeComment());
		}
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// プルダウン設定(勤務形態プルダウンは別途設定
		vo.setAryPltAllowance(mospParams.getProperties().getCodeArray(TimeConst.CODE_ALLOWANCE, false));
		vo.setAryPltLateReason(mospParams.getProperties().getCodeArray(TimeConst.CODE_REASON_OF_LATE, true));
		vo.setAryPltLeaveEarlyReason(
				mospParams.getProperties().getCodeArray(TimeConst.CODE_REASON_OF_LEAVE_EARLY, true));
		vo.setAryPltLateCertificate(mospParams.getProperties().getCodeArray(TimeConst.CODE_ALLOWANCE, true));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setAttendanceDtoFields(AttendanceDtoInterface dto) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// レコード識別ID
		dto.setTmdAttendanceId(getLong(vo.getTmdAttendanceId()));
		// 出退勤情報
		dto.setPersonalId(vo.getPersonalId());
		// 勤務形態
		dto.setWorkTypeCode(vo.getPltWorkType());
		// 勤務日
		dto.setWorkDate(vo.getTargetDate());
		// 勤務回数
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		// 始業・終業取得
		Date startTime = getAttendanceTime(vo.getTxtStartTimeHour(), vo.getTxtStartTimeMinute());
		Date endTime = getAttendanceTime(vo.getTxtEndTimeHour(), vo.getTxtEndTimeMinute());
		// 分単位休暇フラグ
		boolean allMinutelyHolidayA = vo.getCkbAllMinutelyHolidayA().equals(MospConst.CHECKBOX_ON);
		boolean allMinutelyHolidayB = vo.getCkbAllMinutelyHolidayB().equals(MospConst.CHECKBOX_ON);
		if (allMinutelyHolidayA || allMinutelyHolidayB) {
			// 申請の場合
			if (mospParams.getCommand().equals(CMD_APPLI)) {
				// 始業・終業を0に設定
				startTime = getAttendanceTime("0", "0");
				endTime = getAttendanceTime("0", "0");
			}
		}
		// 実始業時刻
		dto.setActualStartTime(startTime);
		// 始業時刻
		dto.setStartTime(startTime);
		
		// 実終業時刻
		dto.setActualEndTime(endTime);
		// 終業時刻
		dto.setEndTime(endTime);
		// 直行
		dto.setDirectStart(getInt(vo.getCkbDirectStart()));
		// 直帰
		dto.setDirectEnd(getInt(vo.getCkbDirectEnd()));
		// 始業忘れ
		dto.setForgotRecordWorkStart(getInt(vo.getCkbForgotRecordWorkStart()));
		// その他
		dto.setNotRecordWorkStart(getInt(vo.getCkbNotRecordWorkStart()));
		// 勤怠コメント
		dto.setTimeComment(vo.getTxtTimeComment());
		// 備考
		dto.setRemarks("");
		if (MospConst.CHECKBOX_ON.equals(vo.getCkbDirectStart()) || MospConst.CHECKBOX_ON.equals(vo.getCkbDirectEnd())
				|| MospConst.CHECKBOX_ON.equals(vo.getCkbForgotRecordWorkStart())
				|| MospConst.CHECKBOX_ON.equals(vo.getCkbNotRecordWorkStart())) {
			dto.setRemarks(vo.getTxtRemarks());
		}
		// 遅刻理由
		dto.setLateReason(vo.getPltLateReason());
		// 遅刻証明書
		dto.setLateCertificate(vo.getPltLateCertificate());
		// 遅刻コメント
		dto.setLateComment(vo.getTxtLateComment());
		// 早退理由
		dto.setLeaveEarlyReason(vo.getPltLeaveEarlyReason());
		// 早退証明書
		dto.setLeaveEarlyCertificate(vo.getPltLeaveEarlyCertificate());
		// 早退コメント
		dto.setLeaveEarlyComment(vo.getTxtLeaveEarlyComment());
		// 分単位1全休
		dto.setMinutelyHolidayA(getInt(vo.getCkbAllMinutelyHolidayA()));
		// 分単位2全休
		dto.setMinutelyHolidayB(getInt(vo.getCkbAllMinutelyHolidayB()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param num 手当てコード
	 * @throws MospException 例外発生時
	 */
	protected void setAllowanceDtoFields(AllowanceDtoInterface dto, int num) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// ■手当情報入力欄
		// 個人ID
		dto.setPersonalId(vo.getPersonalId());
		// 勤務日。
		dto.setWorkDate(vo.getTargetDate());
		// 勤務回数
		dto.setWorks(TimeBean.TIMES_WORK_DEFAULT);
		// 手当コード
		dto.setAllowanceCode(String.valueOf(num));
		// 手当
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO1)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance1()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO2)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance2()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO3)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance3()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO4)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance4()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO5)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance5()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO6)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance6()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO7)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance7()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO8)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance8()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INFO9)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance9()));
		}
		if (num == getInt(TimeConst.CODE_ALLOWANCE_CODE_INF10)) {
			dto.setAllowance(Integer.parseInt(vo.getPltAllowance10()));
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param restList 休憩DTOのLIST
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setRestFields(List<RestDtoInterface> restList, AttendanceDtoInterface dto) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 休憩登録クラス取得
		RestRegistBeanInterface regist = time().restRegist();
		
		// 勤怠マスタ取得
		TimeSettingDtoInterface timeSettingDto = timeReference().cutoffUtil().getTimeSetting(vo.getPersonalId(),
				vo.getTargetDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// デフォルト時間取得
		Date defaultTime = DateUtility.getDateTime(DateUtility.getYear(vo.getTargetDate()),
				DateUtility.getMonth(vo.getTargetDate()), DateUtility.getDay(vo.getTargetDate()), 0, 0);
		// 休憩開始時刻配列取得
		Date[] startArray = new Date[]{ getRest1Start(), getRest2Start(), getRest3Start(), getRest4Start(),
			getRest5Start(), getRest6Start() };
		// 休憩終了時刻配列取得
		Date[] endArray = new Date[]{ getRest1End(), getRest2End(), getRest3End(), getRest4End(), getRest5End(),
			getRest6End() };
		
		for (int i = 0; i < startArray.length; i++) {
			// 休憩DTO準備
			RestDtoInterface restDto = regist.getInitDto();
			// 登録済休憩情報取得
			RestDtoInterface oldRestDto = timeReference().rest().findForKey(vo.getPersonalId(), vo.getTargetDate(),
					TimeBean.TIMES_WORK_DEFAULT, i + 1);
			if (oldRestDto != null) {
				// レコード識別ID設定
				restDto.setTmdRestId(oldRestDto.getTmdRestId());
			}
			// 個人ID
			restDto.setPersonalId(vo.getPersonalId());
			// 勤務日
			restDto.setWorkDate(vo.getTargetDate());
			// 勤務回数
			restDto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
			// 休憩回数
			restDto.setRest(i + 1);
			// 休憩開始時刻
			restDto.setRestStart(defaultTime);
			// 休憩終了時刻
			restDto.setRestEnd(defaultTime);
			Date restStartTime = startArray[i];
			Date restEndTime = endArray[i];
			// 休憩開始があり休憩終了がある場合
			if (restStartTime != null && restEndTime != null) {
				// 始業時刻が休憩開始時刻より後の場合
				if (dto.getStartTime() != null && dto.getStartTime().after(restStartTime)) {
					// 始業時刻を休憩開始時刻とする
					restStartTime = dto.getStartTime();
				}
				// 終業時刻が休憩終了時刻より前の場合
				if (dto.getEndTime() != null && dto.getEndTime().before(restEndTime)) {
					// 終業時刻を休憩終了時刻とする
					restEndTime = dto.getEndTime();
				}
				// 休憩終了時刻が休憩開始時刻より前の場合
				if (restEndTime.before(restStartTime)) {
					// 休憩開始時刻を休憩終了時刻とする
					restEndTime = restStartTime;
				}
				if (!restStartTime.equals(restEndTime)) {
					// 休憩開始時刻
					restDto.setRestStart(restStartTime);
					// 休憩終了時刻
					restDto.setRestEnd(restEndTime);
				}
			}
			// 休憩時間
			restDto.setRestTime(regist.getCalcRestTime(restDto.getRestStart(), restDto.getRestEnd(), timeSettingDto));
			restList.add(restDto);
		}
	}
	
	/**
	 * 分単位休暇を設定する。
	 * @param minutelyHolidayAList 分単位休暇ADTOのリスト
	 * @param minutelyHolidayBList 分単位休暇BDTOのリスト
	 * @throws MospException 例外発生時
	 */
	protected void setMinutelyHolidayDtoFields(List<GoOutDtoInterface> minutelyHolidayAList,
			List<GoOutDtoInterface> minutelyHolidayBList) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠データ外出情報登録クラス取得
		GoOutRegistBeanInterface regist = time().goOutRegist();
		// 分単位休暇A
		Date[] MinutelyHolidayAStartArray = new Date[]{ getMinutelyHolidayA1Start(), getMinutelyHolidayA2Start(),
			getMinutelyHolidayA3Start(), getMinutelyHolidayA4Start() };
		Date[] MinutelyHolidayAEndArray = new Date[]{ getMinutelyHolidayA1End(), getMinutelyHolidayA2End(),
			getMinutelyHolidayA3End(), getMinutelyHolidayA4End() };
		Date defaultTime = DateUtility.getDateTime(DateUtility.getYear(vo.getTargetDate()),
				DateUtility.getMonth(vo.getTargetDate()), DateUtility.getDay(vo.getTargetDate()), 0, 0);
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = timeReference().cutoffUtil().getTimeSetting(vo.getPersonalId(),
				vo.getTargetDate());
		for (int i = 0; i < MinutelyHolidayAStartArray.length; i++) {
			GoOutDtoInterface dto = regist.getInitDto();
			// 個人ID
			dto.setPersonalId(vo.getPersonalId());
			// 勤務日
			dto.setWorkDate(vo.getTargetDate());
			// 勤務回数
			dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
			// 外出区分
			dto.setGoOutType(TimeConst.CODE_GO_OUT_MINUTELY_HOLIDAY_A);
			// 外出回数
			dto.setTimesGoOut(i + 1);
			// 外出開始時刻
			dto.setGoOutStart(defaultTime);
			// 外出終了時刻
			dto.setGoOutEnd(defaultTime);
			// 時刻設定
			Date goOutStartTime = MinutelyHolidayAStartArray[i];
			Date goOutEndTime = MinutelyHolidayAEndArray[i];
			if (goOutStartTime != null && goOutEndTime != null) {
				if (goOutEndTime.before(goOutStartTime)) {
					// 外出終了時刻が外出開始時刻より前の場合は外出開始時刻を外出終了時刻とする
					goOutEndTime = goOutStartTime;
				}
				if (!goOutStartTime.equals(goOutEndTime)) {
					// 外出開始時刻
					dto.setGoOutStart(goOutStartTime);
					// 外出終了時刻
					dto.setGoOutEnd(goOutEndTime);
				}
			}
			// 外出時間
			dto.setGoOutTime(
					regist.getCalcMinutelyHolidayAGoOutTime(dto.getGoOutStart(), dto.getGoOutEnd(), timeSettingDto));
			minutelyHolidayAList.add(dto);
		}
		// 分単位休暇B
		Date[] MinutelyHolidayBStartArray = new Date[]{ getMinutelyHolidayB1Start(), getMinutelyHolidayB2Start(),
			getMinutelyHolidayB3Start(), getMinutelyHolidayB4Start() };
		Date[] MinutelyHolidayBEndArray = new Date[]{ getMinutelyHolidayB1End(), getMinutelyHolidayB2End(),
			getMinutelyHolidayB3End(), getMinutelyHolidayB4End() };
		for (int i = 0; i < MinutelyHolidayBStartArray.length; i++) {
			GoOutDtoInterface dto = regist.getInitDto();
			// 個人ID
			dto.setPersonalId(vo.getPersonalId());
			// 勤務日
			dto.setWorkDate(vo.getTargetDate());
			// 勤務回数
			dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
			// 外出区分
			dto.setGoOutType(TimeConst.CODE_GO_OUT_MINUTELY_HOLIDAY_B);
			// 外出回数
			dto.setTimesGoOut(i + 1);
			// 外出開始時刻
			dto.setGoOutStart(defaultTime);
			// 外出終了時刻
			dto.setGoOutEnd(defaultTime);
			// 時刻設定
			Date goOutStartTime = MinutelyHolidayBStartArray[i];
			Date goOutEndTime = MinutelyHolidayBEndArray[i];
			if (goOutStartTime != null && goOutEndTime != null) {
				if (goOutEndTime.before(goOutStartTime)) {
					// 外出終了時刻が外出開始時刻より前の場合は外出開始時刻を外出終了時刻とする
					goOutEndTime = goOutStartTime;
				}
				if (!goOutStartTime.equals(goOutEndTime)) {
					// 外出開始時刻
					dto.setGoOutStart(goOutStartTime);
					// 外出終了時刻
					dto.setGoOutEnd(goOutEndTime);
				}
			}
			// 外出時間
			dto.setGoOutTime(
					regist.getCalcMinutelyHolidayBGoOutTime(dto.getGoOutStart(), dto.getGoOutEnd(), timeSettingDto));
			minutelyHolidayBList.add(dto);
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param publicList 公用DTOのリスト
	 * @param privateList 外用DTOのリスト
	 * @param workStartTime 始業時刻
	 * @param workEndTime 終業時刻
	 * @throws MospException 例外発生時
	 */
	protected void setGoOutDtoFields(List<GoOutDtoInterface> publicList, List<GoOutDtoInterface> privateList,
			Date workStartTime, Date workEndTime) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤怠データ外出情報登録クラス取得
		GoOutRegistBeanInterface regist = time().goOutRegist();
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = timeReference().cutoffUtil().getTimeSetting(vo.getPersonalId(),
				vo.getTargetDate());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		Date defaultTime = DateUtility.getDateTime(DateUtility.getYear(vo.getTargetDate()),
				DateUtility.getMonth(vo.getTargetDate()), DateUtility.getDay(vo.getTargetDate()), 0, 0);
		// 公用外出
		Date[] publicStartArray = new Date[]{ getPublic1Start(), getPublic2Start() };
		Date[] publicEndArray = new Date[]{ getPublic1End(), getPublic2End() };
		for (int i = 0; i < publicStartArray.length; i++) {
			GoOutDtoInterface dto = regist.getInitDto();
			// 個人ID
			dto.setPersonalId(vo.getPersonalId());
			// 勤務日
			dto.setWorkDate(vo.getTargetDate());
			// 勤務回数
			dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
			// 外出区分
			dto.setGoOutType(TimeConst.CODE_GO_OUT_PUBLIC);
			// 外出回数
			dto.setTimesGoOut(i + 1);
			// 外出開始時刻
			dto.setGoOutStart(defaultTime);
			// 外出終了時刻
			dto.setGoOutEnd(defaultTime);
			// 時刻設定
			Date goOutStartTime = publicStartArray[i];
			Date goOutEndTime = publicEndArray[i];
			if (goOutStartTime != null && goOutEndTime != null) {
				if (workStartTime != null && workStartTime.after(goOutStartTime)) {
					// 始業時刻が外出開始時刻より後の場合は始業時刻を外出開始時刻とする
					goOutStartTime = workStartTime;
				}
				if (workEndTime != null && workEndTime.before(goOutEndTime)) {
					// 終業時刻が外出終了時刻より前の場合は終業時刻を外出終了時刻とする
					goOutEndTime = workEndTime;
				}
				if (goOutEndTime.before(goOutStartTime)) {
					// 外出終了時刻が外出開始時刻より前の場合は外出開始時刻を外出終了時刻とする
					goOutEndTime = goOutStartTime;
				}
				if (!goOutStartTime.equals(goOutEndTime)) {
					// 外出開始時刻
					dto.setGoOutStart(goOutStartTime);
					// 外出終了時刻
					dto.setGoOutEnd(goOutEndTime);
				}
			}
			// 外出時間
			dto.setGoOutTime(regist.getCalcPublicGoOutTime(dto.getGoOutStart(), dto.getGoOutEnd(), timeSettingDto));
			publicList.add(dto);
		}
		// 私用外出
		Date[] privateStartArray = new Date[]{ getPrivate1Start(), getPrivate2Start() };
		Date[] privateEndArray = new Date[]{ getPrivate1End(), getPrivate2End() };
		for (int i = 0; i < privateStartArray.length; i++) {
			GoOutDtoInterface dto = regist.getInitDto();
			// 個人ID
			dto.setPersonalId(vo.getPersonalId());
			// 勤務日
			dto.setWorkDate(vo.getTargetDate());
			// 勤務回数
			dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
			// 外出区分
			dto.setGoOutType(TimeConst.CODE_GO_OUT_PRIVATE);
			// 外出回数
			dto.setTimesGoOut(i + 1);
			// 外出開始時刻
			dto.setGoOutStart(defaultTime);
			// 外出終了時刻
			dto.setGoOutEnd(defaultTime);
			// 時刻設定
			Date goOutStartTime = privateStartArray[i];
			Date goOutEndTime = privateEndArray[i];
			if (goOutStartTime != null && goOutEndTime != null) {
				if (workStartTime != null && workStartTime.after(goOutStartTime)) {
					// 始業時刻が外出開始時刻より後の場合は始業時刻を外出開始時刻とする
					goOutStartTime = workStartTime;
				}
				if (workEndTime != null && workEndTime.before(goOutEndTime)) {
					// 終業時刻が外出終了時刻より前の場合は終業時刻を外出終了時刻とする
					goOutEndTime = workEndTime;
				}
				if (goOutEndTime.before(goOutStartTime)) {
					// 外出終了時刻が外出開始時刻より前の場合は外出開始時刻を外出終了時刻とする
					goOutEndTime = goOutStartTime;
				}
				if (!goOutStartTime.equals(goOutEndTime)) {
					// 外出開始時刻
					dto.setGoOutStart(goOutStartTime);
					// 外出終了時刻
					dto.setGoOutEnd(goOutEndTime);
				}
			}
			// 外出時間
			dto.setGoOutTime(regist.getCalcPrivateGoOutTime(dto.getGoOutStart(), dto.getGoOutEnd(), timeSettingDto));
			privateList.add(dto);
		}
	}
	
	/**
	 * 私用外出チェック。
	 * @param dto           勤怠データ情報
	 * @param requestEntity 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void chkPrivateGoOut(AttendanceDtoInterface dto, RequestEntity requestEntity) throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference()
			.differenceRequest(vo.getTargetDate());
		// 前半休或いは後半休であるかを取得
		boolean isHolidayAm = requestEntity.isAmHoliday(true);
		boolean isHolidayPm = requestEntity.isPmHoliday(true);
		// 勤怠申請の勤務形態を確認
		if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
			return;
		}
		// 私用外出名取得
		String goingOut = mospParams.getName("PrivateGoingOut");
		StringBuffer prescribedWorkTime = new StringBuffer();
		prescribedWorkTime.append(mospParams.getName("Prescribed"));
		prescribedWorkTime.append(mospParams.getName("Labor"));
		prescribedWorkTime.append(mospParams.getName("Time"));
		// 勤務日取得
		Date defaultTime = DateUtility.getDateTime(DateUtility.getYear(dto.getWorkDate()),
				DateUtility.getMonth(dto.getWorkDate()), DateUtility.getDay(dto.getWorkDate()), 0, 0);
		// 私用外出1開始時刻を取得
		Date private1Start = getPrivate1Start();
		if (private1Start == null) {
			private1Start = defaultTime;
		}
		// 私用外出1終了時刻を取得
		Date private1End = getPrivate1End();
		if (private1End == null) {
			private1End = defaultTime;
		}
		// 私用外出2開始時刻を取得
		Date private2Start = getPrivate2Start();
		if (private2Start == null) {
			private2Start = defaultTime;
		}
		// 私用外出2終了時刻を取得
		Date private2End = getPrivate2End();
		if (private2End == null) {
			private2End = defaultTime;
		}
		// 法定休日労働又は所定休日労働の場合
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 私用外出1時刻、 私用外出2時刻が勤務日と違う場合
			if (!defaultTime.equals(private1Start) || !defaultTime.equals(private1End)
					|| !defaultTime.equals(private2Start) || !defaultTime.equals(private2End)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, goingOut,
						prescribedWorkTime.toString());
				return;
			}
		}
		Date workStartTime = null;
		Date workEndTime = null;
		if (differenceRequest.isDifferenceTypeA(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeB(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeC(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeD(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeS(dto.getWorkTypeCode())) {
			// 勤務形態が時差出勤の場合
			DifferenceRequestDtoInterface differenceRequestDto = requestEntity.getDifferenceRequestDto(true);
			if (differenceRequestDto != null) {
				workStartTime = differenceRequestDto.getRequestStart();
				workEndTime = differenceRequestDto.getRequestEnd();
				if (isHolidayAm) {
					// 午前休の場合
					workStartTime = differenceRequest
						.getDifferenceStartTimeMorningOff(differenceRequestDto.getRequestStart());
				}
				if (isHolidayPm) {
					// 午後休の場合
					workEndTime = differenceRequest
						.getDifferenceEndTimeAfternoonOff(differenceRequestDto.getRequestEnd());
				}
			}
		}
		if (workStartTime == null || workEndTime == null) {
			// 時差出勤でない場合
			WorkTypeDtoInterface workTypeDto = timeReference().workType().findForInfo(dto.getWorkTypeCode(),
					dto.getWorkDate());
			if (workTypeDto == null) {
				return;
			}
			// 勤務形態項目取得
			WorkTypeItemDtoInterface workStart = timeReference().workTypeItem().getWorkTypeItemInfo(
					workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
			WorkTypeItemDtoInterface workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(
					workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
			if (isHolidayAm) {
				// 午前休の場合
				workStart = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_BACKSTART);
				workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_BACKEND);
			}
			if (isHolidayPm) {
				// 午後休の場合
				workStart = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_FRONTSTART);
				workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_FRONTEND);
			}
			Date standardDate = DateUtility.getTime(0, 0);
			workStartTime = getAttendanceTime(DateUtility.getHour(workStart.getWorkTypeItemValue(), standardDate),
					DateUtility.getMinute(workStart.getWorkTypeItemValue()));
			workEndTime = getAttendanceTime(DateUtility.getHour(workEnd.getWorkTypeItemValue(), standardDate),
					DateUtility.getMinute(workEnd.getWorkTypeItemValue()));
		}
		Date[][] privateTimeArray = new Date[][]{ { private1Start, private1End }, { private2Start, private2End } };
		for (Date[] timeArray : privateTimeArray) {
			Date start = timeArray[0];
			Date end = timeArray[1];
			if ((start == null || end == null) || (defaultTime.equals(start) && defaultTime.equals(end))) {
				continue;
			} else if (start.before(workStartTime) || end.after(workEndTime)) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, goingOut,
						prescribedWorkTime.toString());
				return;
			}
		}
	}
	
	/**
	 * @param hour 時
	 * @param minute 分
	 * @return 日付
	 * @throws MospException 例外発生時
	 */
	protected Date getAttendanceTime(String hour, String minute) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		if (hour.isEmpty() || minute.isEmpty()) {
			return null;
		}
		return time().attendanceCalc().getAttendanceTime(vo.getTargetDate(), hour, minute);
	}
	
	/**
	 * @param hour 時
	 * @param minute 分
	 * @return 日付
	 * @throws MospException 例外発生時
	 */
	protected Date getAttendanceTime(int hour, int minute) throws MospException {
		return getAttendanceTime(Integer.toString(hour), Integer.toString(minute));
	}
	
	/**
	 * 休憩1開始時刻を取得する。<br>
	 * @return 休憩1開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest1Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour1(), vo.getTxtRestStartMinute1());
	}
	
	/**
	 * 休憩1終了時刻を取得する。<br>
	 * @return 休憩1終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest1End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour1(), vo.getTxtRestEndMinute1());
	}
	
	/**
	 * 休憩2開始時刻を取得する。<br>
	 * @return 休憩2開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest2Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour2(), vo.getTxtRestStartMinute2());
	}
	
	/**
	 * 休憩2終了時刻を取得する。<br>
	 * @return 休憩2終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest2End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour2(), vo.getTxtRestEndMinute2());
	}
	
	/**
	 * 休憩3開始時刻を取得する。<br>
	 * @return 休憩3開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest3Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour3(), vo.getTxtRestStartMinute3());
	}
	
	/**
	 * 休憩3終了時刻を取得する。<br>
	 * @return 休憩3終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest3End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour3(), vo.getTxtRestEndMinute3());
	}
	
	/**
	 * 休憩4開始時刻を取得する。<br>
	 * @return 休憩4開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest4Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour4(), vo.getTxtRestStartMinute4());
	}
	
	/**
	 * 休憩4終了時刻を取得する。<br>
	 * @return 休憩4終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest4End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour4(), vo.getTxtRestEndMinute4());
	}
	
	/**
	 * 休憩5開始時刻を取得する。<br>
	 * @return 休憩5開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest5Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour5(), vo.getTxtRestStartMinute5());
	}
	
	/**
	 * 休憩5終了時刻を取得する。<br>
	 * @return 休憩5終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest5End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour5(), vo.getTxtRestEndMinute5());
	}
	
	/**
	 * 休憩6開始時刻を取得する。<br>
	 * @return 休憩6開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest6Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestStartHour6(), vo.getTxtRestStartMinute6());
	}
	
	/**
	 * 休憩6終了時刻を取得する。<br>
	 * @return 休憩6終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getRest6End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtRestEndHour6(), vo.getTxtRestEndMinute6());
	}
	
	/**
	 * 私用外出1開始時刻を取得する。<br>
	 * @return 私用外出1開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPrivate1Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPrivateStartHour1(), vo.getTxtPrivateStartMinute1());
	}
	
	/**
	 * 私用外出1終了時刻を取得する。<br>
	 * @return 私用外出1終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPrivate1End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPrivateEndHour1(), vo.getTxtPrivateEndMinute1());
	}
	
	/**
	 * 私用外出2開始時刻を取得する。<br>
	 * @return 私用外出2開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPrivate2Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPrivateStartHour2(), vo.getTxtPrivateStartMinute2());
	}
	
	/**
	 * 私用外出2終了時刻を取得する。<br>
	 * @return 私用外出2終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPrivate2End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPrivateEndHour2(), vo.getTxtPrivateEndMinute2());
	}
	
	/**
	 * 公用外出1開始時刻を取得する。<br>
	 * @return 公用外出1開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPublic1Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPublicStartHour1(), vo.getTxtPublicStartMinute1());
	}
	
	/**
	 * 公用外出1終了時刻を取得する。<br>
	 * @return 公用外出1終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPublic1End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPublicEndHour1(), vo.getTxtPublicEndMinute1());
	}
	
	/**
	 * 公用外出2開始時刻を取得する。<br>
	 * @return 公用外出2開始時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPublic2Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPublicStartHour2(), vo.getTxtPublicStartMinute2());
	}
	
	/**
	 * 公用外出2終了時刻を取得する。<br>
	 * @return 公用外出2終了時刻
	 * @throws MospException 例外発生時
	 */
	protected Date getPublic2End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtPublicEndHour2(), vo.getTxtPublicEndMinute2());
	}
	
	/**
	 * 分単位休暇A開始時刻1を取得する。<br>
	 * @return 分単位休暇A開始時刻1
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA1Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAStartHour1(), vo.getTxtMinutelyHolidayAStartMinute1());
	}
	
	/**
	 * 分単位休暇A終了時刻1を取得する。<br>
	 * @return 分単位休暇A終了時刻1
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA1End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAEndHour1(), vo.getTxtMinutelyHolidayAEndMinute1());
	}
	
	/**
	 * 分単位休暇A開始時刻2を取得する。<br>
	 * @return 分単位休暇A開始時刻2
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA2Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAStartHour2(), vo.getTxtMinutelyHolidayAStartMinute2());
	}
	
	/**
	 * 分単位休暇A終了時刻2を取得する。<br>
	 * @return 分単位休暇A終了時刻2
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA2End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAEndHour2(), vo.getTxtMinutelyHolidayAEndMinute2());
	}
	
	/**
	 * 分単位休暇A開始時刻3を取得する。<br>
	 * @return 分単位休暇A開始時刻3
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA3Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAStartHour3(), vo.getTxtMinutelyHolidayAStartMinute3());
	}
	
	/**
	 * 分単位休暇A終了時刻3を取得する。<br>
	 * @return 分単位休暇A終了時刻3
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA3End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAEndHour3(), vo.getTxtMinutelyHolidayAEndMinute3());
	}
	
	/**
	 * 分単位休暇A開始時刻4を取得する。<br>
	 * @return 分単位休暇A開始時刻4
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA4Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAStartHour4(), vo.getTxtMinutelyHolidayAStartMinute4());
	}
	
	/**
	 * 分単位休暇A終了時刻4を取得する。<br>
	 * @return 分単位休暇A終了時刻4
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayA4End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayAEndHour4(), vo.getTxtMinutelyHolidayAEndMinute4());
	}
	
	/**
	 * 分単位休暇B開始時刻1を取得する。<br>
	 * @return 分単位休暇B開始時刻1
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB1Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBStartHour1(), vo.getTxtMinutelyHolidayBStartMinute1());
	}
	
	/**
	 * 分単位休暇B終了時刻1を取得する。<br>
	 * @return 分単位休暇B終了時刻1
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB1End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBEndHour1(), vo.getTxtMinutelyHolidayBEndMinute1());
	}
	
	/**
	 * 分単位休暇B開始時刻2を取得する。<br>
	 * @return 分単位休暇B開始時刻2
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB2Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBStartHour2(), vo.getTxtMinutelyHolidayBStartMinute2());
	}
	
	/**
	 * 分単位休暇B終了時刻2を取得する。<br>
	 * @return 分単位休暇B終了時刻2
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB2End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBEndHour2(), vo.getTxtMinutelyHolidayBEndMinute2());
	}
	
	/**
	 * 分単位休暇B開始時刻3を取得する。<br>
	 * @return 分単位休暇B開始時刻3
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB3Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBStartHour3(), vo.getTxtMinutelyHolidayBStartMinute3());
	}
	
	/**
	 * 分単位休暇B終了時刻3を取得する。<br>
	 * @return 分単位休暇B終了時刻3
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB3End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBEndHour3(), vo.getTxtMinutelyHolidayBEndMinute3());
	}
	
	/**
	 * 分単位休暇B開始時刻4を取得する。<br>
	 * @return 分単位休暇B開始時刻4
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB4Start() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBStartHour4(), vo.getTxtMinutelyHolidayBStartMinute4());
	}
	
	/**
	 * 分単位休暇B終了時刻4を取得する。<br>
	 * @return 分単位休暇B終了時刻4
	 * @throws MospException 例外発生時
	 */
	protected Date getMinutelyHolidayB4End() throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getAttendanceTime(vo.getTxtMinutelyHolidayBEndHour4(), vo.getTxtMinutelyHolidayBEndMinute4());
	}
	
	/**
	 * 勤務形態及び勤務形態プルダウン用配列を設定する。<br>
	 * プルダウンには、長さ1の配列が設定される。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param workTypeName 勤務形態名称
	 */
	protected void setPltWorkType(String workTypeCode, String workTypeName) {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤務形態設定
		vo.setPltWorkType(workTypeCode);
		// 配列準備
		String[][] aryPltWorkType = { { workTypeCode, workTypeName } };
		// プルダウン設定
		vo.setAryPltWorkType(aryPltWorkType);
	}
	
	/**
	 * 勤務形態及び勤務形態プルダウン用配列を設定する。<br>
	 * プルダウンには、長さ1の配列が設定される。<br>
	 * 勤務形態は空白とする。<br>
	 * 有給休暇等、存在しない勤務形態を設定する場合に用いる。<br>
	 * @param workTypeName 勤務形態名称
	 */
	protected void setPltWorkType(String workTypeName) {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 勤務形態設定
		vo.setPltWorkType("");
		// 配列準備
		String[][] aryPltWorkType = { { "", workTypeName } };
		// プルダウン設定
		vo.setAryPltWorkType(aryPltWorkType);
	}
	
	/**
	 * 勤務形態プルダウン用配列を取得する。<br>
	 * @param patternCode パターンコード
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException 例外発生時
	 */
	protected String[][] getWorkTypeArray(String patternCode, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		return getWorkTypeArray(patternCode, vo.getTargetDate(), false, true, amHoliday, pmHoliday);
	}
	
	/**
	 * 追加処理を行う。
	 * VOの値を汎用マスタ情報に詰める。
	 * @throws MospException 例外発生時
	 */
	protected void setGeneralDtoFields() throws MospException {
		// アドオン機能などで処理
	}
	
	/**
	 * 休暇時間の重複チェック
	 * @param dto 勤怠情報
	 * @throws MospException 例外発生時
	 */
	protected void chkDuplRest(AttendanceDtoInterface dto) throws MospException {
		Date baseDateStart = null;
		Date baseDateEnd = null;
		// VO取得
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 直行・直帰確認
		boolean isDirect = vo.getCkbDirectStart().equals(MospConst.CHECKBOX_ON)
				|| vo.getCkbDirectEnd().equals(MospConst.CHECKBOX_ON);
		// 始業、終業、勤務日取得
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		// 名称取得
		String rest1 = mospParams.getName("Rest1");
		String rest2 = mospParams.getName("Rest2");
		String rest3 = mospParams.getName("Rest3");
		String rest4 = mospParams.getName("Rest4");
		String rest5 = mospParams.getName("Rest5");
		String rest6 = mospParams.getName("Rest6");
		String public1 = mospParams.getName("Official", "GoingOut", "No1");
		String public2 = mospParams.getName("Official", "GoingOut", "No2");
		String private1 = mospParams.getName("PrivateGoingOut1");
		String private2 = mospParams.getName("PrivateGoingOut2");
		String minutelyA1 = mospParams.getName("MinutelyHolidayAAbbr", "No1");
		String minutelyA2 = mospParams.getName("MinutelyHolidayAAbbr", "No2");
		String minutelyA3 = mospParams.getName("MinutelyHolidayAAbbr", "No3");
		String minutelyA4 = mospParams.getName("MinutelyHolidayAAbbr", "No4");
		String minutelyB1 = mospParams.getName("MinutelyHolidayBAbbr", "No1");
		String minutelyB2 = mospParams.getName("MinutelyHolidayBAbbr", "No2");
		String minutelyB3 = mospParams.getName("MinutelyHolidayBAbbr", "No3");
		String minutelyB4 = mospParams.getName("MinutelyHolidayBAbbr", "No4");
		// 昼休憩
		baseDateStart = getRest1Start();
		baseDateEnd = getRest1End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 時間外休憩2
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest2Start(), getRest2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, rest2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩3
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest3Start(), getRest3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, rest3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩4
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest4Start(), getRest4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, rest4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩5
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest5Start(), getRest5End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, rest5 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩6
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest6Start(), getRest6End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, rest6 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest1, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour1(), vo.getTxtRestStartMinute1(),
					vo.getTxtRestEndHour1(), vo.getTxtRestEndMinute1(), rest1)) {
				return;
			}
		}
		// 時間外休憩2
		baseDateStart = getRest2Start();
		baseDateEnd = getRest2End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 時間外休憩3
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest3Start(), getRest3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, rest3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩4
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest4Start(), getRest4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, rest4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩5
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest5Start(), getRest5End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, rest5 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩6
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest6Start(), getRest6End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, rest6 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest2, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour2(), vo.getTxtRestStartMinute2(),
					vo.getTxtRestEndHour2(), vo.getTxtRestEndMinute2(), rest2)) {
				return;
			}
		}
		
		// 時間外休憩3
		baseDateStart = getRest3Start();
		baseDateEnd = getRest3End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 時間外休憩4
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest4Start(), getRest4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, rest4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩5
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest5Start(), getRest5End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, rest5 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩6
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest6Start(), getRest6End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, rest6 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest3, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour3(), vo.getTxtRestStartMinute3(),
					vo.getTxtRestEndHour3(), vo.getTxtRestEndMinute3(), rest3)) {
				return;
			}
		}
		
		// 時間外休憩4
		baseDateStart = getRest4Start();
		baseDateEnd = getRest4End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 時間外休憩5
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest5Start(), getRest5End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, rest5 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 時間外休憩6
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest6Start(), getRest6End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, rest6 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest4, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour4(), vo.getTxtRestStartMinute4(),
					vo.getTxtRestEndHour4(), vo.getTxtRestEndMinute4(), rest4)) {
				return;
			}
		}
		// 時間外休憩5
		baseDateStart = getRest5Start();
		baseDateEnd = getRest5End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 時間外休憩6
			if (chkDuplTime(baseDateStart, baseDateEnd, getRest6Start(), getRest6End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, rest6 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest5, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour5(), vo.getTxtRestStartMinute5(),
					vo.getTxtRestEndHour5(), vo.getTxtRestEndMinute5(), rest5)) {
				return;
			}
		}
		
		//	時間外休憩6
		baseDateStart = getRest6Start();
		baseDateEnd = getRest6End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 公用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic1Start(), getPublic1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, public1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), rest6, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtRestStartHour6(), vo.getTxtRestStartMinute6(),
					vo.getTxtRestEndHour6(), vo.getTxtRestEndMinute6(), rest6)) {
				return;
			}
		}
		
		// 公用外出1
		baseDateStart = getPublic1Start();
		baseDateEnd = getPublic1End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 公用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPublic2Start(), getPublic2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, public2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public1, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtPublicStartHour1(),
					vo.getTxtPublicStartMinute1(), vo.getTxtPublicEndHour1(), vo.getTxtPublicEndMinute1(), public1)) {
				return;
			}
		}
		
		// 公用外出2
		baseDateStart = getPublic2Start();
		baseDateEnd = getPublic2End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 私用外出1
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate1Start(), getPrivate1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, private1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), public2, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtPublicStartHour2(),
					vo.getTxtPublicStartMinute2(), vo.getTxtPublicEndHour2(), vo.getTxtPublicEndMinute2(), public2)) {
				return;
			}
		}
		
		// 私用外出1
		baseDateStart = getPrivate1Start();
		baseDateEnd = getPrivate1End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 私用外出2
			if (chkDuplTime(baseDateStart, baseDateEnd, getPrivate2Start(), getPrivate2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, private2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private1, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtPrivateStartHour1(),
					vo.getTxtPrivateStartMinute1(), vo.getTxtPrivateEndHour1(), vo.getTxtPrivateEndMinute1(),
					private1)) {
				return;
			}
		}
		// 私用外出2
		baseDateStart = getPrivate2Start();
		baseDateEnd = getPrivate2End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇A-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA1Start(), getMinutelyHolidayA1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyA1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), private2, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtPrivateStartHour2(),
					vo.getTxtPrivateStartMinute2(), vo.getTxtPrivateEndHour2(), vo.getTxtPrivateEndMinute2(),
					private2)) {
				return;
			}
		}
		// 分単位休暇A-1
		baseDateStart = getMinutelyHolidayA1Start();
		baseDateEnd = getMinutelyHolidayA1End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇A-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA2Start(), getMinutelyHolidayA2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyA2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA1, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayAStartHour1(),
					vo.getTxtMinutelyHolidayAStartMinute1(), vo.getTxtMinutelyHolidayAEndHour1(),
					vo.getTxtMinutelyHolidayAEndMinute1(), minutelyA1)) {
				return;
			}
		}
		// 分単位休暇A-2
		baseDateStart = getMinutelyHolidayA2Start();
		baseDateEnd = getMinutelyHolidayA2End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇A-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA3Start(), getMinutelyHolidayA3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyA3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA2, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayAStartHour2(),
					vo.getTxtMinutelyHolidayAStartMinute2(), vo.getTxtMinutelyHolidayAEndHour2(),
					vo.getTxtMinutelyHolidayAEndMinute2(), minutelyA2)) {
				return;
			}
		}
		// 分単位休暇A-3
		baseDateStart = getMinutelyHolidayA3Start();
		baseDateEnd = getMinutelyHolidayA3End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇A-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayA4Start(), getMinutelyHolidayA4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA3, minutelyA4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA3, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA3, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA3, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA3, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayAStartHour3(),
					vo.getTxtMinutelyHolidayAStartMinute3(), vo.getTxtMinutelyHolidayAEndHour3(),
					vo.getTxtMinutelyHolidayAEndMinute3(), minutelyA3)) {
				return;
			}
		}
		// 分単位休暇A-4
		baseDateStart = getMinutelyHolidayA4Start();
		baseDateEnd = getMinutelyHolidayA4End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇B-1
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB1Start(), getMinutelyHolidayB1End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA4, minutelyB1 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA4, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA4, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyA4, minutelyB4 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayAStartHour4(),
					vo.getTxtMinutelyHolidayAStartMinute4(), vo.getTxtMinutelyHolidayAEndHour4(),
					vo.getTxtMinutelyHolidayAEndMinute4(), minutelyA4)) {
				return;
			}
		}
		// 分単位休暇B-1
		baseDateStart = getMinutelyHolidayB1Start();
		baseDateEnd = getMinutelyHolidayB1End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇B-2
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB2Start(), getMinutelyHolidayB2End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB1, minutelyB2 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB1, minutelyB3 };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB1,
					mospParams.getName("MinutelyHolidayB", "No4") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayBStartHour1(),
					vo.getTxtMinutelyHolidayBStartMinute1(), vo.getTxtMinutelyHolidayBEndHour1(),
					vo.getTxtMinutelyHolidayBEndMinute1(), minutelyB1)) {
				return;
			}
		}
		// 分単位休暇B-2
		baseDateStart = getMinutelyHolidayB2Start();
		baseDateEnd = getMinutelyHolidayB2End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇B-3
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB3Start(), getMinutelyHolidayB3End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB2,
					mospParams.getName("MinutelyHolidayB", "No3") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB2,
					mospParams.getName("MinutelyHolidayB", "No4") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayBStartHour2(),
					vo.getTxtMinutelyHolidayBStartMinute2(), vo.getTxtMinutelyHolidayBEndHour2(),
					vo.getTxtMinutelyHolidayBEndMinute2(), minutelyB1)) {
				return;
			}
		}
		// 分単位休暇B-3
		baseDateStart = getMinutelyHolidayB3Start();
		baseDateEnd = getMinutelyHolidayB3End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 分単位休暇B-4
			if (chkDuplTime(baseDateStart, baseDateEnd, getMinutelyHolidayB4Start(), getMinutelyHolidayB4End())) {
				String[] rep = { DateUtility.getStringDateAndDay(baseDateStart), minutelyB3,
					mospParams.getName("MinutelyHolidayB", "No4") };
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
				return;
			}
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayBStartHour3(),
					vo.getTxtMinutelyHolidayBStartMinute3(), vo.getTxtMinutelyHolidayBEndHour3(),
					vo.getTxtMinutelyHolidayBEndMinute3(), minutelyB1)) {
				return;
			}
		}
		// 分単位休暇B-4
		baseDateStart = getMinutelyHolidayB4Start();
		baseDateEnd = getMinutelyHolidayB4End();
		if (baseDateStart != null && baseDateEnd != null) {
			// 直行・直帰かつ整合性がとれない場合
			if (isDirect && !chkTimeValidate(startTime, endTime, vo.getTxtMinutelyHolidayBStartHour4(),
					vo.getTxtMinutelyHolidayBStartMinute4(), vo.getTxtMinutelyHolidayBEndHour4(),
					vo.getTxtMinutelyHolidayBEndMinute4(), minutelyB1)) {
				return;
			}
		}
	}
	
	/**
	 * 分単位休暇、勤務形態時間内チェック。
	 * @param dto           勤怠情報
	 * @param requestEntity 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkMinutelyHolidayInWorkType(AttendanceDtoInterface dto, RequestEntity requestEntity)
			throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference()
			.differenceRequest(vo.getTargetDate());
		// 前半休或いは後半休であるかを取得
		boolean isHolidayAm = requestEntity.isAmHoliday(true);
		boolean isHolidayPm = requestEntity.isPmHoliday(true);
		// 勤怠申請の勤務形態を確認
		if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
			return;
		}
		// 勤務日取得
		Date defaultTime = DateUtility.getDateTime(DateUtility.getYear(dto.getWorkDate()),
				DateUtility.getMonth(dto.getWorkDate()), DateUtility.getDay(dto.getWorkDate()), 0, 0);
		// 名称取得
		String prescribedWorkTime = mospParams.getName("Prescribed", "Holiday", "GoingWork");
		String legalWorkTime = mospParams.getName("Legal", "Holiday", "GoingWork");
		// 分単位休暇時刻を取得
		Date minutelyHolidayA1Start = getMinutelyHolidayA1Start();
		if (minutelyHolidayA1Start == null) {
			minutelyHolidayA1Start = defaultTime;
		}
		Date minutelyHolidayA1End = getMinutelyHolidayA1End();
		if (minutelyHolidayA1End == null) {
			minutelyHolidayA1End = defaultTime;
		}
		Date minutelyHolidayA2Start = getMinutelyHolidayA2Start();
		if (minutelyHolidayA2Start == null) {
			minutelyHolidayA2Start = defaultTime;
		}
		Date minutelyHolidayA2End = getMinutelyHolidayA2End();
		if (minutelyHolidayA2End == null) {
			minutelyHolidayA2End = defaultTime;
		}
		Date minutelyHolidayA3Start = getMinutelyHolidayA3Start();
		if (minutelyHolidayA3Start == null) {
			minutelyHolidayA3Start = defaultTime;
		}
		Date minutelyHolidayA3End = getMinutelyHolidayA3End();
		if (minutelyHolidayA3End == null) {
			minutelyHolidayA3End = defaultTime;
		}
		Date minutelyHolidayA4Start = getMinutelyHolidayA4Start();
		if (minutelyHolidayA4Start == null) {
			minutelyHolidayA4Start = defaultTime;
		}
		Date minutelyHolidayA4End = getMinutelyHolidayA4End();
		if (minutelyHolidayA4End == null) {
			minutelyHolidayA4End = defaultTime;
		}
		Date minutelyHolidayB1Start = getMinutelyHolidayB1Start();
		if (minutelyHolidayB1Start == null) {
			minutelyHolidayB1Start = defaultTime;
		}
		Date minutelyHolidayB1End = getMinutelyHolidayB1End();
		if (minutelyHolidayB1End == null) {
			minutelyHolidayB1End = defaultTime;
		}
		Date minutelyHolidayB2Start = getMinutelyHolidayB2Start();
		if (minutelyHolidayB2Start == null) {
			minutelyHolidayB2Start = defaultTime;
		}
		Date minutelyHolidayB2End = getMinutelyHolidayB2End();
		if (minutelyHolidayB2End == null) {
			minutelyHolidayB2End = defaultTime;
		}
		Date minutelyHolidayB3Start = getMinutelyHolidayB3Start();
		if (minutelyHolidayB3Start == null) {
			minutelyHolidayB3Start = defaultTime;
		}
		Date minutelyHolidayB3End = getMinutelyHolidayB3End();
		if (minutelyHolidayB3End == null) {
			minutelyHolidayB3End = defaultTime;
		}
		Date minutelyHolidayB4Start = getMinutelyHolidayB4Start();
		if (minutelyHolidayB4Start == null) {
			minutelyHolidayB4Start = defaultTime;
		}
		Date minutelyHolidayB4End = getMinutelyHolidayB4End();
		if (minutelyHolidayB4End == null) {
			minutelyHolidayB4End = defaultTime;
		}
		// 法定休日労働の場合
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 各時刻が勤務日と違う場合
			if (!defaultTime.equals(minutelyHolidayA1Start) || !defaultTime.equals(minutelyHolidayA1End)
					|| !defaultTime.equals(minutelyHolidayA2Start) || !defaultTime.equals(minutelyHolidayA2End)
					|| !defaultTime.equals(minutelyHolidayA3Start) || !defaultTime.equals(minutelyHolidayA3End)
					|| !defaultTime.equals(minutelyHolidayA4Start) || !defaultTime.equals(minutelyHolidayA4End)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, legalWorkTime,
						mospParams.getName("MinutelyHolidayA"));
				return;
			}
			if (!defaultTime.equals(minutelyHolidayB1Start) || !defaultTime.equals(minutelyHolidayB1End)
					|| !defaultTime.equals(minutelyHolidayB2Start) || !defaultTime.equals(minutelyHolidayB2End)
					|| !defaultTime.equals(minutelyHolidayB3Start) || !defaultTime.equals(minutelyHolidayB3End)
					|| !defaultTime.equals(minutelyHolidayB4Start) || !defaultTime.equals(minutelyHolidayB4End)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, legalWorkTime,
						mospParams.getName("MinutelyHolidayB"));
				return;
			}
		}
		// 所定休日労働の場合
		if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 各時刻が勤務日と違う場合
			if (!defaultTime.equals(minutelyHolidayA1Start) || !defaultTime.equals(minutelyHolidayA1End)
					|| !defaultTime.equals(minutelyHolidayA2Start) || !defaultTime.equals(minutelyHolidayA2End)
					|| !defaultTime.equals(minutelyHolidayA3Start) || !defaultTime.equals(minutelyHolidayA3End)
					|| !defaultTime.equals(minutelyHolidayA4Start) || !defaultTime.equals(minutelyHolidayA4End)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, prescribedWorkTime,
						mospParams.getName("MinutelyHolidayA"));
				return;
			}
			if (!defaultTime.equals(minutelyHolidayB1Start) || !defaultTime.equals(minutelyHolidayB1End)
					|| !defaultTime.equals(minutelyHolidayB2Start) || !defaultTime.equals(minutelyHolidayB2End)
					|| !defaultTime.equals(minutelyHolidayB3Start) || !defaultTime.equals(minutelyHolidayB3End)
					|| !defaultTime.equals(minutelyHolidayB4Start) || !defaultTime.equals(minutelyHolidayB4End)) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_GENERAL_ERROR, prescribedWorkTime,
						mospParams.getName("MinutelyHolidayB"));
				return;
			}
		}
		Date workStartTime = null;
		Date workEndTime = null;
		if (differenceRequest.isDifferenceTypeA(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeB(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeC(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeD(dto.getWorkTypeCode())
				|| differenceRequest.isDifferenceTypeS(dto.getWorkTypeCode())) {
			// 勤務形態が時差出勤の場合
			DifferenceRequestDtoInterface differenceRequestDto = requestEntity.getDifferenceRequestDto(true);
			if (differenceRequestDto != null) {
				workStartTime = differenceRequestDto.getRequestStart();
				workEndTime = differenceRequestDto.getRequestEnd();
				if (isHolidayAm) {
					// 午前休の場合
					workStartTime = differenceRequest
						.getDifferenceStartTimeMorningOff(differenceRequestDto.getRequestStart());
				}
				if (isHolidayPm) {
					// 午後休の場合
					workEndTime = differenceRequest
						.getDifferenceEndTimeAfternoonOff(differenceRequestDto.getRequestEnd());
				}
			}
		}
		if (workStartTime == null || workEndTime == null) {
			// 時差出勤でない場合
			WorkTypeDtoInterface workTypeDto = timeReference().workType().findForInfo(dto.getWorkTypeCode(),
					dto.getWorkDate());
			if (workTypeDto == null) {
				return;
			}
			// 勤務形態項目取得
			WorkTypeItemDtoInterface workStart = timeReference().workTypeItem().getWorkTypeItemInfo(
					workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKSTART);
			WorkTypeItemDtoInterface workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(
					workTypeDto.getWorkTypeCode(), workTypeDto.getActivateDate(), TimeConst.CODE_WORKEND);
			if (isHolidayAm) {
				// 午前休の場合
				workStart = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_BACKSTART);
				workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_BACKEND);
			}
			if (isHolidayPm) {
				// 午後休の場合
				workStart = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_FRONTSTART);
				workEnd = timeReference().workTypeItem().getWorkTypeItemInfo(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), TimeConst.CODE_FRONTEND);
			}
			Date standardDate = DateUtility.getTime(0, 0);
			workStartTime = getAttendanceTime(DateUtility.getHour(workStart.getWorkTypeItemValue(), standardDate),
					DateUtility.getMinute(workStart.getWorkTypeItemValue()));
			workEndTime = getAttendanceTime(DateUtility.getHour(workEnd.getWorkTypeItemValue(), standardDate),
					DateUtility.getMinute(workEnd.getWorkTypeItemValue()));
		}
		// 分単位休暇A時刻
		Date[][] minutelyHolidayATimeArray = new Date[][]{ { minutelyHolidayA1Start, minutelyHolidayA1End },
			{ minutelyHolidayA2Start, minutelyHolidayA2End }, { minutelyHolidayA3Start, minutelyHolidayA3End },
			{ minutelyHolidayA4Start, minutelyHolidayA4End } };
		for (Date[] timeArray : minutelyHolidayATimeArray) {
			Date start = timeArray[0];
			Date end = timeArray[1];
			if ((start == null || end == null) || (defaultTime.equals(start) && defaultTime.equals(end))) {
				continue;
			} else if (start.before(workStartTime) || end.after(workEndTime)) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorMinutelyAOutOfWorkTime(mospParams);
				return;
			}
		}
		// 分単位休暇B時刻
		Date[][] minutelyHolidayBTimeArray = new Date[][]{ { minutelyHolidayB1Start, minutelyHolidayB1End },
			{ minutelyHolidayB2Start, minutelyHolidayB2End }, { minutelyHolidayB3Start, minutelyHolidayB3End },
			{ minutelyHolidayB4Start, minutelyHolidayB4End } };
		for (Date[] timeArray : minutelyHolidayBTimeArray) {
			Date start = timeArray[0];
			Date end = timeArray[1];
			if ((start == null || end == null) || (defaultTime.equals(start) && defaultTime.equals(end))) {
				continue;
			} else if (start.before(workStartTime) || end.after(workEndTime)) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorMinutelyBOutOfWorkTime(mospParams);
				return;
			}
		}
	}
	
	/**
	 * 時間の重複チェック
	 * @param bStart 対象の開始時間
	 * @param bEnd 対象の終了時間
	 * @param aStart 比べる先の開始時間
	 * @param aEnd 比べる先の終了時間
	 * @return true 時間の重複あり：false 時間の重複無し
	 */
	protected boolean chkDuplTime(Date bStart, Date bEnd, Date aStart, Date aEnd) {
		if (aStart != null && aEnd != null) {
			return bEnd.after(aStart) && bStart.before(aEnd);
		}
		return false;
	}
	
	/**
	 * 時刻のチェックを行う。
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param startHour 項目開始時
	 * @param startMinute 項目開始分
	 * @param endHour 項目終了時
	 * @param endMinute 項目終了分
	 * @param itemName 項目名
	 * @return 確認結果(true：整合性がある、false：整合性がない)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected boolean chkTimeValidate(Date startTime, Date endTime, String startHour, String startMinute,
			String endHour, String endMinute, String itemName) throws MospException {
		// VO準備
		AttendanceCardVo vo = (AttendanceCardVo)mospParams.getVo();
		// 始業時間、終業時間取得
		if (startTime == null || endTime == null) {
			return false;
		}
		int startTimeHour = DateUtility.getHour(startTime, vo.getTargetDate());
		int startTimeMinute = DateUtility.getMinute(startTime);
		int endTimeHour = DateUtility.getHour(endTime, vo.getTargetDate());
		int endTimeMinute = DateUtility.getMinute(endTime);
		// 始業時間時間、終業時間時間取得
		int startTimeTime = startTimeHour * 60 + startTimeMinute;
		int endTimeTime = endTimeHour * 60 + endTimeMinute;
		// 始業時間時間、終業時間時間取得
		int startTimeTime2 = getInt(startHour) * 60 + getInt(startMinute);
		int endTimeTime2 = getInt(endHour) * 60 + getInt(endMinute);
		// 始業時間が0でない又は終業時間が0でない場合
		if (startTimeTime2 != 0 || endTimeTime2 != 0) {
			// 始業時間が各時刻より早い場合
			if (startTimeTime2 < startTimeTime) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_OUT_EXCEPT, itemName);
				return false;
			}
			// 終業時間が各時刻より早い場合
			if (endTimeTime2 > endTimeTime) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_OUT_EXCEPT, itemName);
				return false;
			}
			// 始業が終業より早い場合
			if (startTimeTime2 > endTimeTime2) {
				// エラーメッセージ追加
				String[] rep = { itemName, itemName };
				mospParams.addErrorMessage(TimeMessageConst.MSG_W_END_BEFORE_START, rep);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * リクエストされた直行を取得する。<br>
	 * 定時終業打刻から遷移した場合に用いる。<br>
	 * @return 直行
	 */
	protected String getTimeRecordDirectStart() {
		return (String)mospParams.getGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_START);
	}
	
	/**
	 * リクエストされた直帰を取得する。<br>
	 * 定時終業打刻から遷移した場合に用いる。<br>
	 * @return 直帰
	 */
	protected String getTimeRecordDirectEnd() {
		return (String)mospParams.getGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_END);
	}
	
	/**
	 * 試算成功後お知らせメッセージ設定。
	 */
	protected void addUnregisteredNoticeMessage() {
		mospParams.addMessage(TimeMessageConst.MSG_UNREGISTERED_NOTIS);
	}
}
