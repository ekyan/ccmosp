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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.AttendanceListBaseAction;
import jp.mosp.time.base.AttendanceListBaseVo;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.AttendanceListVo;
import jp.mosp.time.management.action.ApprovalCardAction;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 日々勤怠情報の一覧表示と始終業時刻の編集、勤怠申請を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_TOTAL}
 * </li><li>
 * {@link #CMD_OUTPUT}
 * </li><li>
 * {@link #CMD_SHOW_APPROVAL}
 * </li><li>
 * {@link #CMD_APPROVE}
 * </li></ul>
 */
public class AttendanceListAction extends AttendanceListBaseAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの勤怠情報を表示する。<br>
	 * サーバ日付を取得し、画面遷移を行った時点の年月のものをデフォルトで表示する。<br>
	 */
	public static final String	CMD_SHOW			= "TM1100";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 部下一覧画面にて選択したユーザのサーバ日付時点年月の勤怠情報を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW		= "TM1101";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 操作者の指定にしたがって表示する対象年月を変更する。
	 * 前月・翌月ボタンといったものは現在表示している年月を基に画面遷移を行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM1102";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層にあたる勤怠詳細画面で勤怠情報を編集し、
	 * 再びこの画面へ遷移した際に編集内容を反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM1103";
	
	/**
	 * 下書きコマンド。<br>
	 * <br>
	 * 編集チェックボックスにチェックの入ったレコードの始業時刻・終業時刻の
	 * 入力欄に入力されているそれぞれの内容を勤怠情報テーブルに登録し、下書情報として保存する。<br>
	 * この時、始業時刻または終業時刻が編集されていたとしても下書コマンドを実行時に該当レコードのチェックボックスが空になっている場合は更新されない。<br>
	 * 下書処理実行後はこの画面を再表示し、始終業時刻の表示欄に登録内容を反映させる。<br>
	 * 別画面へ遷移するまで実行前に入力欄を編集したにも関わらずチェックボックスのチェックが外れていて更新されなかったレコードの編集内容については保持しておく。<br>
	 * <br>
	 */
	public static final String	CMD_DRAFT			= "TM1104";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 編集チェックボックスにチェックの入ったレコードの始業時刻・終業時刻の入力欄に入力されているそれぞれの内容を勤怠情報テーブルに登録し、
	 * 勤怠申請処理を行う。<br>
	 * この時、始業時刻または終業時刻が編集されていたとしても申請コマンドを実行時に該当レコードのチェックボックスが空になっている場合は更新されない。<br>
	 * 申請処理実行後はこの画面を再表示し、始終業時刻・合計時間・合計回数の表示欄に登録内容を反映させる。<br>
	 * 別画面へ遷移するまで実行前に入力欄を編集したにも関わらずチェックボックスのチェックが外れていて更新されなかったレコードの編集内容については保持しておく。<br>
	 * <br>
	 */
	public static final String	CMD_APPLI			= "TM1105";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、対象個人ID、対象日等をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER		= "TM1106";
	
	/**
	 * 集計コマンド。<br>
	 * <br>
	 * 現在表示している対象個人ID及び対象年月につき、集計(個別仮締)処理を行う。<br>
	 */
	public static final String	CMD_TOTAL			= "TM1107";
	
	/**
	 * 帳票出力コマンド。<br>
	 * <br>
	 * 現在表示している勤怠情報を帳票として出力する。<br>
	 */
	public static final String	CMD_OUTPUT			= "TM1196";
	
	/**
	 * 表示コマンド(勤怠承認一覧画面)。<br>
	 * <br>
	 * 勤怠承認モードで画面を表示する。<br>
	 * 社員別勤怠承認画面にて選択したユーザの指定年月の勤怠情報を表示する。<br>
	 */
	public static final String	CMD_SHOW_APPROVAL	= "TM2421";
	
	/**
	 * 承認コマンド(勤怠承認一覧画面)。<br>
	 * <br>
	 * 勤怠承認モードでのみ実行される。<br>
	 * 編集チェックボックスにチェックの入ったレコードの承認処理を一括で行う。<br>
	 * 申請処理実行後は承認の状態や承認者の欄を更新した上でこの画面を再表示する。<br>
	 */
	public static final String	CMD_APPROVE			= "TM2425";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public AttendanceListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new AttendanceListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SHOW_APPROVAL)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
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
			appli();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_TOTAL)) {
			// 集計
			prepareVo(true, false);
			total();
		} else if (mospParams.getCommand().equals(CMD_APPROVE)) {
			// 承認
			prepareVo();
			approve();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT)) {
			// 帳票出力
			prepareVo(true, false);
			output();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * 対象個人IDは、ログインユーザ情報から取得する。<br>
	 * 対象日は、システム日付から取得する。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void show() throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 表示コマンド設定
		vo.setShowCommand(mospParams.getCommand());
		// 個人ID取得(ログインユーザ情報から)
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface attendanceListReference = timeReference().attendanceList();
		// 勤怠一覧情報リスト(勤怠一覧)を取得しVOに設定
		setVoList(attendanceListReference.getAttendanceList(personalId, targetDate));
		// 勤怠一覧情報参照クラスの情報をVOのフィールドに設定
		setVoFields(attendanceListReference);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// メッセージ設定
			addNoSearchResultMessage();
			return;
		}
		// 締状態チェック
		checkCutoffState();
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * MosP処理情報から対象年月及び個人IDを取得し、検索する。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void select() throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 表示コマンド設定
		vo.setShowCommand(mospParams.getCommand());
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVAL)) {
			// パンくずリスト名称設定(承認モードの場合)
			TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), getNameAttendanceApprovalList());
		}
		// 対象個人ID取得
		String personalId = getTargetPersonalId();
		// 対象年月取得
		int year = getTargetYear();
		int month = getTargetMonth();
		// 年月指定時の基準日取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(year, month, mospParams);
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface attendanceListReference = timeReference().attendanceList();
		// 基本情報チェック
		timeReference().attendance().chkBasicInfo(personalId, targetDate);
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVAL)) {
			// 勤怠承認一覧を取得しVOに設定(勤怠承認モードの場合)
			setVoList(attendanceListReference.getApprovalAttendanceList(personalId, year, month));
		} else {
			// 対象日で勤怠一覧(勤怠一覧)情報を取得しVOに設定
			setVoList(attendanceListReference.getAttendanceList(personalId, year, month));
		}
		// 勤怠一覧情報参照クラスの情報をVOのフィールドに設定
		setVoFields(attendanceListReference);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// メッセージ設定
			addNoSearchResultMessage();
			return;
		}
		// 締状態チェック
		checkCutoffState();
	}
	
	/**
	 * 年月による検索を行う。<br>
	 * 対象個人IDはVOに保持されているものを用いる。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		// パラメータ受取(対象年月)
		String year = getTransferredYear();
		String month = getTransferredMonth();
		// 年月確認
		if (year == null || month == null) {
			// VOから年月を取得
			year = vo.getPltSelectYear();
			month = vo.getPltSelectMonth();
		}
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface attendanceListReference = timeReference().attendanceList();
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVAL)) {
			// 勤怠承認一覧を取得しVOに設定(承認モードの場合)
			setVoList(attendanceListReference.getApprovalAttendanceList(personalId, getInt(year), getInt(month)));
		} else {
			// 勤怠一覧情報リスト(勤怠一覧)を取得しVOに設定
			setVoList(attendanceListReference.getAttendanceList(personalId, getInt(year), getInt(month)));
		}
		// 勤怠一覧情報参照クラスの情報をVOのフィールドに設定
		setVoFields(attendanceListReference);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// メッセージ設定
			addNoSearchResultMessage();
			return;
		}
		// 締状態チェック
		checkCutoffState();
	}
	
	/**
	 * 帳票を作成し、送出ファイルとして設定する。<br>
	 * 対象個人IDはVOに保持されているものを用いる。<br>
	 * 対象年月は、VOに保持されているものを用いる。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void output() throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		// 対象年月取得(VOから)
		int year = getInt(vo.getPltSelectYear());
		int month = getInt(vo.getPltSelectMonth());
		// 年月で出勤簿を作成
		timeReference().attendanceBook().makeAttendanceBook(personalId, year, month);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// メッセージ設定
			addNoSearchResultMessage();
		}
	}
	
	/**
	 * 下書き処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void draft() throws MospException {
		// リクエストされた時刻をVOに設定
		setRequestedTimes();
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// 対象日配列取得
		String[] targetDates = vo.getCkbSelect();
		// 始業時刻配列取得
		String[] startTimes = vo.getTxtStartTime();
		// 終業時刻配列取得
		String[] endTimes = vo.getTxtEndTime();
		// 一括下書
		time().attendanceListRegist(DateUtility.getDate(targetDates[0])).draft(personalId, targetDates, startTimes,
				endTimes, false, false, false);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addBatchUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 下書成功メッセージ設定
		addDraftMessage();
		// 検索
		search();
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException 検索失敗時
	 */
	protected void appli() throws MospException {
		// リクエストされた時刻をVOに設定
		setRequestedTimes();
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// 対象日配列取得
		String[] targetDates = vo.getCkbSelect();
		// 始業時刻配列取得
		String[] startTimes = vo.getTxtStartTime();
		// 終業時刻配列取得
		String[] endTimes = vo.getTxtEndTime();
		AttendanceListRegistBeanInterface attendanceListRegist = time().attendanceListRegist(
				DateUtility.getDate(targetDates[0]));
		// 一括申請
		attendanceListRegist.apply(personalId, targetDates, startTimes, endTimes);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addBatchUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 申請成功メッセージ設定
		addAppliMessage();
		// 残業申請督促確認
		attendanceListRegist.checkOvertime(personalId, targetDates);
		// 検索
		search();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(AttendanceCardAction.class.getName())) {
			// MosP処理情報に対象個人IDを設定
			setTargetPersonalId(vo.getPersonalId());
			// MosP処理情報に対象日を設定
			setTargetDate(getDate(vo.getAryDate(getTransferredIndex())));
			// 勤怠詳細画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(AttendanceCardAction.CMD_SELECT_SHOW);
		} else if (actionName.equals(AttendanceHistoryAction.class.getName())) {
			// MosP処理情報に対象個人IDを設定
			setTargetPersonalId(vo.getPersonalId());
			// MosP処理情報に対象日を設定
			setTargetDate(getDate(vo.getAryDate(getTransferredIndex())));
			// 勤怠修正履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(AttendanceHistoryAction.CMD_SELECT_SHOW);
		} else if (actionName.equals(ApprovalHistoryAction.class.getName())) {
			// MosP処理情報に対象ワークフローを設定
			setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
			// 承認履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(ApprovalHistoryAction.CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW);
		} else if (actionName.equals(ApprovalCardAction.class.getName())) {
			// MosP処理情報に対象ワークフローを設定
			setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
			// 承認履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(ApprovalCardAction.CMD_APPROVAL_ATTENDANCE);
		}
	}
	
	/**
	 * 集計(個別仮締)処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void total() throws MospException {
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		// 計算対象年月取得(VOから)
		int calculationYear = getInt(vo.getPltSelectYear());
		int calculationMonth = getInt(vo.getPltSelectMonth());
		// 個別仮締
		time().totalTimePersonal().total(personalId, calculationYear, calculationMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 集計失敗メッセージ設定
			TimeMessageUtility.addMessageTotalFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 集計(個別仮締)成功メッセージ設定
		TimeMessageUtility.addMessageTotalSucceed(mospParams);
		// 再表示
		search();
	}
	
	/**
	 * 一括承認処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void approve() throws MospException {
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 対象日配列取得
		String[] targetDates = vo.getCkbSelect();
		List<Long> list = new ArrayList<Long>();
		for (String date : targetDates) {
			AttendanceDtoInterface dto = timeReference().attendance().findForKey(vo.getPersonalId(), getDate(date),
					TimeBean.TIMES_WORK_DEFAULT);
			if (dto == null) {
				continue;
			}
			list.add(dto.getWorkflow());
		}
		long[] aryWorkflow = toArray(list);
		// 一括更新処理
		time().timeApproval().approve(aryWorkflow, null);
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージ設定
		addApprovalMessage();
		// 再検索
		search();
	}
	
	/**
	 * リクエストされた時刻をVOに設定する。<br>
	 * 下書、申請処理でエラーがあった場合に、設定しておくことでリクエスト値を再表示する。<br>
	 */
	protected void setRequestedTimes() {
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 対象日配列(リクエストされた値)取得
		String[] targetDates = vo.getCkbSelect();
		// 始業時刻配列(リクエストされた値)取得
		String[] startTimes = vo.getTxtStartTime();
		// 終業時刻配列(リクエストされた値)取得
		String[] endTimes = vo.getTxtEndTime();
		// 対象日配列取得
		String[] lblDates = vo.getAryDate();
		// 始業時刻配列取得
		String[] lblStartTimes = vo.getAryLblStartTime();
		// 終業時刻配列取得
		String[] lblEndTimes = vo.getAryLblEndTime();
		// 対象日配列(リクエストされた値)毎に処理
		for (int i = 0; i < targetDates.length; i++) {
			// 対象日配列確認
			for (int j = 0; j < lblDates.length; j++) {
				// 日付文字列比較
				if (targetDates[i].equals(lblDates[j]) == false) {
					continue;
				}
				// 始業、終業時刻設定
				lblStartTimes[j] = startTimes[i];
				lblEndTimes[j] = endTimes[i];
			}
		}
		// 始業、終業時刻をVOに再設定
		vo.setAryLblStartTime(lblStartTimes);
		vo.setAryLblEndTime(lblEndTimes);
	}
	
	/**
	 * 締状態をチェックする。<br>
	 * <br>
	 * 仮締、確定、締日情報取得不能である場合、エラーメッセージを設定する。<br>
	 * また、勤怠設定の自己月締を確認し集計ボタンの要否をVOに設定する。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkCutoffState() throws MospException {
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 勤怠データ情報を取得
		String personalId = vo.getPersonalId();
		int targetYear = getInt(vo.getPltSelectYear());
		int targetMonth = getInt(vo.getPltSelectMonth());
		
		// 対象個人IDにつき対象日付が未締であるかを確認
		boolean isNotTighten = timeReference().cutoffUtil().isNotTighten(personalId, targetYear, targetMonth);
		// 未締であるかを確認
		if (isNotTighten == false) {
			// 未締でない場合
			vo.setTotalButtonVisible(false);
			// エラーメッセージ設定(集計コマンドでない場合)
			if (mospParams.getCommand().equals(CMD_TOTAL) == false) {
				TimeMessageUtility.addErrorTheMonthIsTighten(mospParams, targetYear, targetMonth);
			}
			return;
		}
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティ取得
		ApplicationEntity applicationEntity = timeReference().application()
			.getApplicationEntity(personalId, targetDate);
		// 自己月締確認及び集計ボタン要否設定
		vo.setTotalButtonVisible(applicationEntity.isSelfTightening());
	}
	
	/**
	 * 継承元のメソッドを実行した後、VOに検索結果リストの内容を設定する。<br>
	 * @param list 対象リスト
	 */
	@Override
	protected void setVoList(List<AttendanceListDto> list) {
		// 継承元のメソッド実行
		super.setVoList(list);
		// VO準備
		AttendanceListVo vo = (AttendanceListVo)mospParams.getVo();
		// 設定項目準備
		String[] aryDate = new String[list.size()];
		String[] aryLblPrivateTime = new String[list.size()];
		String[] aryLblLateTime = new String[list.size()];
		String[] aryLblLeaveEarlyTime = new String[list.size()];
		String[] aryLblLateLeaveEarlyTime = new String[list.size()];
		String[] aryLblOverTimeIn = new String[list.size()];
		String[] aryOvertimeStyle = new String[list.size()];
		String[] aryLblOverTimeOut = new String[list.size()];
		String[] aryLblWorkOnHoliday = new String[list.size()];
		String[] aryLblLateNight = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		String[] aryStateStyle = new String[list.size()];
		boolean[] aryLinkState = new boolean[list.size()];
		boolean[] aryCheckState = new boolean[list.size()];
		String[] aryLblCorrection = new String[list.size()];
		long[] aryAttendanceRecordId = new long[list.size()];
		long[] aryWorkflowRecordId = new long[list.size()];
		long[] aryWorkflow = new long[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		int[] aryWorkflowStage = new int[list.size()];
		// 設定項目作成
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			AttendanceListDto dto = list.get(i);
			// 日付設定
			aryDate[i] = getStringDate(dto.getWorkDate());
			// 私用外出時間
			aryLblPrivateTime[i] = dto.getPrivateTimeString();
			// 遅刻時間
			aryLblLateTime[i] = dto.getLateTimeString();
			// 早退時間
			aryLblLeaveEarlyTime[i] = dto.getLeaveEarlyTimeString();
			// 遅刻早退時間
			aryLblLateLeaveEarlyTime[i] = dto.getLateLeaveEarlyTimeString();
			// 内残時間
			aryLblOverTimeIn[i] = dto.getOvertimeInString();
			// 残業時間スタイル
			aryOvertimeStyle[i] = dto.getOvertimeStyle();
			// 外残時間
			aryLblOverTimeOut[i] = dto.getOvertimeOutString();
			// 休出時間
			aryLblWorkOnHoliday[i] = dto.getHolidayWorkTimeString();
			// 深夜時間
			aryLblLateNight[i] = dto.getLateNightTimeString();
			// 状態
			aryLblState[i] = dto.getApplicationInfo();
			// 状態スタイル
			aryStateStyle[i] = getAttendanceStateColor(dto.getApplicationInfo());
			// 状態リンク要否情報
			aryLinkState[i] = dto.isNeedStatusLink();
			// チェックボックス要否情報
			aryCheckState[i] = dto.isNeedCheckbox();
			// 修正情報
			aryLblCorrection[i] = dto.getCorrectionInfo();
			// 勤怠データレコード識別ID
			aryAttendanceRecordId[i] = dto.getAttendanceRecordId();
			// ワークフローレコード識別ID
			aryWorkflowRecordId[i] = dto.getWorkflowRecordId();
			// ワークフロー番号
			aryWorkflow[i] = dto.getWorkflow();
			// ワークフロー状況
			aryWorkflowStatus[i] = dto.getWorkflowStatus();
			// ワークフロー段階
			aryWorkflowStage[i] = dto.getWorkflowStage();
		}
		// VOに項目を設定
		vo.setAryDate(aryDate);
		vo.setAryLblPrivateTime(aryLblPrivateTime);
		vo.setAryLblLateTime(aryLblLateTime);
		vo.setAryLblLeaveEarlyTime(aryLblLeaveEarlyTime);
		vo.setAryLblLateLeaveEarlyTime(aryLblLateLeaveEarlyTime);
		vo.setAryLblOverTimeIn(aryLblOverTimeIn);
		vo.setAryOvertimeStyle(aryOvertimeStyle);
		vo.setAryLblOverTimeOut(aryLblOverTimeOut);
		vo.setAryLblWorkOnHoliday(aryLblWorkOnHoliday);
		vo.setAryLblLateNight(aryLblLateNight);
		vo.setAryLblState(aryLblState);
		vo.setAryStateStyle(aryStateStyle);
		vo.setAryLinkState(aryLinkState);
		vo.setAryCheckState(aryCheckState);
		vo.setAryLblCorrection(aryLblCorrection);
		vo.setAryAttendanceRecordId(aryAttendanceRecordId);
		vo.setAryWorkflowRecordId(aryWorkflowRecordId);
		vo.setAryWorkflow(aryWorkflow);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflowStage(aryWorkflowStage);
		// 合計値取得確認
		if (list.isEmpty()) {
			return;
		}
		// 勤怠一覧情報リスト最終レコード取得
		AttendanceListDto dto = list.get(list.size() - 1);
		// 合計時間/回数表示項目設定
		vo.setLblTotalPrivate(dto.getPrivateTimeTotalString());
		vo.setLblTotalLate(dto.getLateTimeTotalString());
		vo.setLblTotalLeaveEarly(dto.getLeaveEarlyTimeTotalString());
		vo.setLblTotalLateLeaveEarly(dto.getLateLeaveEarlyTimeTotalString());
		vo.setLblTotalOverTimeIn(dto.getOvertimeInTotalString());
		vo.setLblTotalOverTimeOut(dto.getOvertimeOutTotalString());
		vo.setLblTotalWorkOnHoliday(dto.getHolidayWorkTimeTotalString());
		vo.setLblTotalLateNight(dto.getLateNightTimeTotalString());
		vo.setLblTimesLate(dto.getLateDaysString());
		vo.setLblTimesLeaveEarly(dto.getLeaveEarlyDaysString());
		vo.setLblTimesOverTimeWork(dto.getOvertimeDaysString());
		vo.setLblTimesWorkOnHoliday(dto.getHolidayWorkDaysString());
		// 代休発生回数
		vo.setLblTimesBirthPrescribedSubHolidayday(dto.getBirthPrescribedSubHolidayString());
		vo.setLblTimesBirthLegalSubHolidayday(dto.getBirthLegalSubHolidayString());
		// 深夜代休がない場合
		if (dto.getBirthMidnightSubHolidayString().equals("0.0")) {
			vo.setLblTimesBirthMidnightSubHolidayday(null);
		} else {
			vo.setLblTimesBirthMidnightSubHolidayday(dto.getBirthMidnightSubHolidayString());
		}
		// 休暇表示項目設定
		vo.setLblTimesSubstitute(dto.getSubstituteHolidaysString());
		vo.setLblTimesPaidHoliday(dto.getPaidHolidaysString());
		vo.setLblTimesPaidHolidayTime(dto.getPaidHolidayTimeString());
		vo.setLblTimesSpecialHoloiday(dto.getSpecialHolidaysString());
		vo.setLblTimesOtherHoloiday(dto.getOtherHolidaysString());
		vo.setLblTimesSubHoliday(dto.getSubHolidaysString());
		vo.setLblTimesAbsence(dto.getAbsenceDaysString());
		vo.setLblTimesMinutelyHolidayA(dto.getMinutelyHolidayATimeString());
		vo.setLblTimesMinutelyHolidayB(dto.getMinutelyHolidayBTimeString());
		// 分単位休暇AB非表示
		vo.setLblTimesMinutelyHolidayA(false);
		vo.setLblTimesMinutelyHolidayB(false);
	}
	
	/**
	 * リストを配列に変換する。<br>
	 * @param list 対象リスト
	 * @return 配列
	 */
	protected long[] toArray(List<Long> list) {
		if (list == null) {
			return new long[0];
		}
		long[] array = new long[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i).longValue();
		}
		return array;
	}
	
	/**
	 * 勤怠承認一覧名称を取得する。<br>
	 * @return 勤怠承認一覧名称
	 */
	protected String getNameAttendanceApprovalList() {
		return mospParams.getName("AttendanceApprovalListVo");
	}
	
}
