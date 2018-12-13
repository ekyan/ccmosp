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
package jp.mosp.time.settings.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.TimeSettingCardVo;

/**
 * 勤怠設定情報の個別詳細情報の確認、編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li><li>
 * {@link #CMD_REPLICATION_MODE}
 * </li></ul>
 */
public class TimeSettingCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで画面の初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW				= "TM5120";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤怠設定一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM5121";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤怠設定情報テーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、または勤怠設定コードが
	 * 登録済みのレコードのものと同一であった場合、エラーメッセージを表示する。<br>
	 */
	public static final String		CMD_REGIST				= "TM5125";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String		CMD_DELETE				= "TM5127";
	
	/**
	 * 有効日決定コマンド。<br>
	 * 入力した有効日時点で有効な締日の略称を締日プルダウンで選択できるよう入れる。<br>
	 * 有効日決定後は有効日の入力欄が読取専用となる。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM5170";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE			= "TM5171";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日入力欄を編集可能にした上で内容を空欄にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String		CMD_ADD_MODE			= "TM5173";
	
	/**
	 * 複製モード切替コマンド。<br>
	 * <br>
	 * 編集モードで編集不可だった有効日、コードが編集可能となり、
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * モード切替前に現在編集中のレコードのコードを変更することで新たなレコードとして
	 * 登録できる旨を伝える確認ダイアログを表示して利用者が承認をしてからモードを切り替える。<br>
	 */
	public static final String		CMD_REPLICATION_MODE	= "TM5176";
	
	// 限度基準データ 期間の定数
	private static final String[]	TERM_ARRAY				= { "week1", "week2", "week4", "month1", "month2",
		"month3", "year1"									};
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TimeSettingCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TimeSettingCardVo();
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
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_REPLICATION_MODE)) {
			// 複製モード切替
			prepareVo();
			replicationMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			// 新規登録
			insert();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			add();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		TimeSettingRegistBeanInterface regist = time().timeSettingRegist();
		// DTOの準備
		TimeSettingDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規登録処理
		regist.insert(dto);
		// 新規登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 限度基準管理情報
		// 登録クラス取得
		LimitStandardRegistBeanInterface limitRegist = time().limitStandardRegist();
		// DTOの準備
		LimitStandardDtoInterface limitDto = limitRegist.getInitDto();
		// 限度基準データ 期間分処理を行う。
		for (String element : TERM_ARRAY) {
			// DTOに値を設定
			setLimitDtoFields(limitDto, element);
			// 新規登録処理
			limitRegist.insert(limitDto);
			// 新規登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		}
		// コミット
		commit();
		// 登録成功メッセージ設定
		addInsertNewMessage();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getWorkSettingCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		TimeSettingRegistBeanInterface regist = time().timeSettingRegist();
		// DTOの準備
		TimeSettingDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 限度基準管理情報
		// 登録クラス取得
		LimitStandardRegistBeanInterface limitRegist = time().limitStandardRegist();
		// DTOの準備
		LimitStandardDtoInterface limitDto = limitRegist.getInitDto();
		// 限度基準データ 期間分処理を行う。
		for (String element : TERM_ARRAY) {
			// DTOに値を設定
			setLimitDtoFields(limitDto, element);
			// 履歴追加処理
			limitRegist.insert(limitDto);
			// 履歴追加結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		}
		// コミット
		commit();
		// 履歴追加成功メッセージ設定
		addInsertHistoryMessage();
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setEditUpdateMode(dto.getWorkSettingCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		TimeSettingRegistBeanInterface regist = time().timeSettingRegist();
		// DTOの準備
		TimeSettingDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			return;
		}
		// 限度基準管理情報
		// 登録クラス取得
		LimitStandardRegistBeanInterface limitRegist = time().limitStandardRegist();
		// DTOの準備
		LimitStandardDtoInterface limitDto = limitRegist.getInitDto();
		// 限度基準データ 期間分処理を行う。
		for (String element : TERM_ARRAY) {
			// DTOに値を設定
			setLimitDtoFields(limitDto, element);
			// 更新処理
			limitRegist.update(limitDto);
			// 更新結果確認
			if (mospParams.hasErrorMessage()) {
				// 更新失敗メッセージ設定
				addUpdateFailedMessage();
				return;
			}
		}
		// コミット
		commit();
		// 更新成功メッセージ設定
		addUpdateMessage();
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setEditUpdateMode(dto.getWorkSettingCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		TimeSettingRegistBeanInterface regist = time().timeSettingRegist();
		LimitStandardRegistBeanInterface limitRegist = time().limitStandardRegist();
		// DTOの準備
		TimeSettingDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 削除処理
		regist.delete(dto);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// 限度基準管理情報
		// DTOの準備
		LimitStandardDtoInterface limitDto = limitRegist.getInitDto();
		// 限度基準データ 期間分処理を行う。
		for (String element : TERM_ARRAY) {
			// DTOに値を設定
			setLimitDtoFields(limitDto, element);
			// 削除処理
			limitRegist.delete(limitDto);
			// 更新結果確認
			if (mospParams.hasErrorMessage()) {
				// 削除失敗メッセージ設定
				addDeleteHistoryFailedMessage();
				return;
			}
		}
		// コミット
		commit();
		// 削除成功メッセージ設定
		addDeleteMessage();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 勤務形態コードと有効日で編集対象情報を取得する。<br>
	 * @param settingCode 勤務形態コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String settingCode, Date activateDate) throws MospException {
		// VO準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 初期値設定(限度基準管理情報が存在しない場合でも初期値を設定する)
		setDefaultValues();
		// 勤怠設定情報取得準備
		TimeSettingReferenceBeanInterface timeSetting = timeReference().timeSetting();
		// 履歴編集対象取得
		TimeSettingDtoInterface dto = timeSetting.findForKey(settingCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 限度基準管理情報取得準備
		LimitStandardReferenceBeanInterface limit = timeReference().limitStandard();
		// 限度基準管理情報を取得しVOに設定(期間毎に処理を行う)
		for (String element : TERM_ARRAY) {
			// 履歴編集対象取得
			LimitStandardDtoInterface limitDto = limit.findForKey(settingCode, activateDate, element);
			if (limitDto != null) {
				// VOにセット
				setLimitVoFields(limitDto);
			}
		}
		// 編集モード設定
		setEditUpdateMode(timeSetting.getTimeSettingHistory(settingCode));
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 締日プルダウン設定
		setCutoffPulldouwn();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 締日プルダウン設定
		setCutoffPulldouwn();
	}
	
	/**
	 * 新規登録モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 新規登録モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
		// 締日プルダウン設定
		setCutoffPulldouwn();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 締日プルダウン設定
		setCutoffPulldouwn();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 複製モードで画面を表示する。<br>
	 */
	protected void replicationMode() {
		// 複製モード設定
		setEditReplicationMode();
		// VO準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// コードを空白に設定
		vo.setTxtSettingCode("");
	}
	
	/**
	 * プルダウンを設定する。<br>
	 */
	private void setPulldown() {
		// VO準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 起算月
		vo.setAryPltStartYear(getMonthArray());
		// 丸め
		vo.setAryPltRoundingItems(mospParams.getProperties().getCodeArray(TimeConst.CODE_ROUNDING_ITEMS, false));
	}
	
	/**
	 * 締日プルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffPulldouwn() throws MospException {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 有効日変更状態の場合
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltCutoffDate(getInputActivateDatePulldown());
			return;
		}
		// 締日略称のプルダウン取得
		String[][] aryPltCutoffDate = timeReference().cutoff().getSelectArray(getEditActivateDate());
		// データが存在しない場合
		if (aryPltCutoffDate[0][0].isEmpty()) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン設定
			vo.setAryPltCutoffDate(getInputActivateDatePulldown());
			// メッセージ設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORKFORM_EXISTENCE, mospParams.getName("CutoffDate"));
			return;
		}
		// プルダウン設定
		vo.setAryPltCutoffDate(aryPltCutoffDate);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 基本設定
		// 勤怠設定コード
		vo.setTxtSettingCode("");
		// 勤怠設定名称
		vo.setTxtSettingName("");
		// 勤怠設定略称
		vo.setTxtSettingAbbr("");
		// 締日
		vo.setPltCutoffDate("");
		
		// 勤怠設定
		// 勤怠管理対象
		vo.setPltTimeManagement("0");
		// 日々申請対象
		vo.setPltDailyApproval("0");
		// 勤務前残業
		vo.setPltBeforeOverTime("0");
		// 所定休日取扱
		vo.setPltSpecificHoliday("1");
		// 週の起算曜日
		vo.setPltStartWeek("0");
		// 月の起算日
		vo.setPltStartMonth("0");
		// 年の起算月
		vo.setPltStartYear("0");
		// 所定労働時間（時間）
		vo.setTxtGeneralWorkTimeHour("00");
		// 所定労働時間（分）
		vo.setTxtGeneralWorkTimeMinute("00");
		// 一日の起算時（時間）
		vo.setTxtStartDayHour("09");
		// 一日の起算時（分）
		vo.setTxtStartDayMinute("00");
		// 遅刻早退限度時間（全日）
		vo.setTxtLateEarlyFullHour("00");
		// 遅刻早退限度分（全日）
		vo.setTxtLateEarlyFullMinute("00");
		// 遅刻早退限度時間（半日）
		vo.setTxtLateEarlyHalfHour("00");
		// 遅刻早退限度分（半日）
		vo.setTxtLateEarlyHalfMinute("00");
		// 振休取得期限月(休出前)
		vo.setTxtTransferAheadLimitMonth("0");
		// 振休取得期限日(休出前)
		vo.setTxtTransferAheadLimitDate("0");
		// 振休取得期限月(休出後)
		vo.setTxtTransferLaterLimitMonth("0");
		// 振休取得期限日(休出後)
		vo.setTxtTransferLaterLimitDate("0");
		// 代休取得期限
		vo.setTxtSubHolidayLimitMonth("0");
		// 代休取得期限
		vo.setTxtSubHolidayLimitDate("0");
		// 半休入替取得（振休）
		vo.setPltTransferExchange("0");
		// 半休入替取得（代休）
		vo.setPltSubHolidayExchange("0");
		// 代休基準時間(全休)
		vo.setTxtSubHolidayAllNormHour("00");
		// 代休基準分(全休)
		vo.setTxtSubHolidayAllNormMinute("00");
		// 代休基準時間(半休)
		vo.setTxtSubHolidayHalfNormHour("00");
		// 代休基準分(半休)
		vo.setTxtSubHolidayHalfNormMinute("00");
		// ポータル出退勤ボタン表示(始業/終業)
		vo.setPltPortalTimeButtons("1");
		// ポータル休憩ボタン表示(表示)
		vo.setPltPortalRestButtons("1");
		// 勤務予定時間表示(無効)
		vo.setPltUseScheduledTime(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		
		// 時間丸め設定
		// 日出勤丸め単位
		vo.setTxtRoundDailyStart("0");
		// 日出勤丸め
		vo.setPltRoundDailyStart("0");
		// 日退勤丸め単位
		vo.setTxtRoundDailyEnd("0");
		// 日退勤丸め
		vo.setPltRoundDailyEnd("0");
		// 日勤務時間丸め単位
		vo.setTxtRoundDailyWork("0");
		// 日勤務時間丸め
		vo.setPltRoundDailyWork("0");
		// 日休憩入丸め単位
		vo.setTxtRoundDailyRestStart("0");
		// 日休憩入丸め
		vo.setPltRoundDailyRestStart("0");
		// 日休憩戻丸め単位
		vo.setTxtRoundDailyRestEnd("0");
		// 日休憩戻丸め
		vo.setPltRoundDailyRestEnd("0");
		// 日休憩時間丸め単位
		vo.setTxtRoundDailyRestTime("0");
		// 日休憩時間丸め
		vo.setPltRoundDailyRestTime("0");
		// 日遅刻丸め単位
		vo.setTxtRoundDailyLate("0");
		// 日遅刻丸め
		vo.setPltRoundDailyLate("0");
		// 日早退丸め単位
		vo.setTxtRoundDailyLeaveEarly("0");
		// 日早退丸め
		vo.setPltRoundDailyLeaveEaly("0");
		// 日私用外出入丸め単位
		vo.setTxtRoundDailyPrivateIn("0");
		// 日私用外出入り丸め
		vo.setPltRoundDailyPrivateIn("0");
		// 日私用外出戻丸め単位
		vo.setTxtRoundDailyPrivateOut("0");
		// 日私用外出戻り丸め
		vo.setPltRoundDailyPrivateOut("0");
		// 日公用外出入丸め単位
		vo.setTxtRoundDailyPublicIn("0");
		// 日公用外出入り丸め
		vo.setPltRoundDailyPublicIn("0");
		// 日公用外出戻丸め単位
		vo.setTxtRoundDailyPublicOut("0");
		// 日公用外出戻り丸め
		vo.setPltRoundDailyPublicOut("0");
		// 日減額対象丸め単位
		vo.setTxtRoundDailyDecreaseTime("0");
		// 日減額対象時間丸め
		vo.setPltRoundDailyDecreaseTime("0");
		// 日無給時短時間丸め単位
		vo.setTxtRoundDailyShortUnpaid("0");
		// 日無給時短時間丸め
		vo.setPltRoundDailyShortUnpaid("0");
		// 月勤務時間丸め単位
		vo.setTxtRoundMonthlyWork("0");
		// 月勤務時間丸め
		vo.setPltRoundMonthlyWork("0");
		// 月休憩時間丸め単位
		vo.setTxtRoundMonthlyRest("0");
		// 月休憩時間丸め
		vo.setPltRoundMonthlyRest("0");
		// 月遅刻丸め単位
		vo.setTxtRoundMonthlyLate("0");
		// 月遅刻時間丸め
		vo.setPltRoundMonthlyLate("0");
		// 月早退丸め単位
		vo.setTxtRoundMonthlyLeaveEarly("0");
		// 月早退時間丸め
		vo.setPltRoundMonthlyLeaveEarly("0");
		// 月私用外出丸め単位
		vo.setTxtRoundMonthlyPrivate("0");
		// 月私用外出時間丸め
		vo.setPltRoundMonthlyPrivate("0");
		// 月公用外出丸め単位
		vo.setTxtRoundMonthlyPublic("0");
		// 月公用外出時間丸め
		vo.setPltRoundMonthlyPublic("0");
		// 月減額対象丸め単位
		vo.setTxtRoundMonthlyDecreaseTime("0");
		// 月減額対象時間丸め
		vo.setPltRoundMonthlyDecreaseTime("0");
		// 月無給時短時間丸め単位
		vo.setTxtRoundMonthlyShortUnpaid("0");
		// 月無給時短時間丸め
		vo.setPltRoundMonthlyShortUnpaid("0");
		
		// 限度基準
		// 1週間限度時間（時間）
		vo.setTxtLimit1WeekHour("00");
		// 1週間限度時間（分）
		vo.setTxtLimit1WeekMinute("00");
		// 2週間限度時間（時間）
		vo.setTxtLimit2WeekHour("");
		//2週間限度時間（分）
		vo.setTxtLimit2WeekMinute("");
		// 4週間限度時間（時間）
		vo.setTxtLimit4WeekHour("");
		// 4週間限度時間（分）
		vo.setTxtLimit4WeekMinute("");
		// 1ヶ月限度時間（時間）
		vo.setTxtLimit1MonthHour("00");
		// 1ヶ月限度時間（分）
		vo.setTxtLimit1MonthMinute("00");
		// 2ヶ月限度時間（時間）
		vo.setTxtLimit2MonthHour("");
		// 2ヶ月限度時間（分）
		vo.setTxtLimit2MonthMinute("");
		// 3ヶ月限度時間（時間）
		vo.setTxtLimit3MonthHour("");
		// 3ヶ月限度時間（分）
		vo.setTxtLimit3MonthMinute("");
		// 1年間限度時間（時間）
		vo.setTxtLimit1YearHour("");
		// 1年間限度時間（分）
		vo.setTxtLimit1YearMinute("");
		// 1週間注意時間（時間）
		vo.setTxtAttention1WeekHour("");
		// 1週間注意時間（分）
		vo.setTxtAttention1WeekMinute("");
		// 2週間注意時間（時間）
		vo.setTxtAttention2WeekHour("");
		// 2週間注意時間（分）
		vo.setTxtAttention2WeekMinute("");
		// 4週間注意時間（時間）
		vo.setTxtAttention4WeekHour("");
		// 4週間注意時間（分）
		vo.setTxtAttention4WeekMinute("");
		// 1ヶ月注意時間（時間）
		vo.setTxtAttention1MonthHour("00");
		// 1ヶ月注意時間（分）
		vo.setTxtAttention1MonthMinute("00");
		// 2ヶ月注意時間（時間）
		vo.setTxtAttention2MonthHour("");
		// 2ヶ月注意時間（分）
		vo.setTxtAttention2MonthMinute("");
		// 3ヶ月注意時間（時間）
		vo.setTxtAttention3MonthHour("");
		// 3ヶ月注意時間（分）
		vo.setTxtAttention3MonthMinute("");
		// 1年間注意時間（時間）
		vo.setTxtAttention1YearHour("");
		// 1年間注意時間（分）
		vo.setTxtAttention1YearMinute("");
		// 1週間警告時間（時間）
		vo.setTxtWarning1WeekHour("");
		// 1週間警告時間（分）
		vo.setTxtWarning1WeekMinute("");
		// 2週間警告時間（時間）
		vo.setTxtWarning2WeekHour("");
		// 2週間警告時間（分）
		vo.setTxtWarning2WeekMinute("");
		// 4週間警告時間（時間）
		vo.setTxtWarning4WeekHour("");
		// 4週間警告時間（分）
		vo.setTxtWarning4WeekMinute("");
		// 1ヶ月警告時間（時間）
		vo.setTxtWarning1MonthHour("00");
		// 1ヶ月警告時間（分）
		vo.setTxtWarning1MonthMinute("00");
		// 2ヶ月警告時間（時間）
		vo.setTxtWarning2MonthHour("");
		// 2ヶ月警告時間（分）
		vo.setTxtWarning2MonthMinute("");
		// 3ヶ月警告時間（時間）
		vo.setTxtWarning3MonthHour("");
		// 3ヶ月警告時間（分）
		vo.setTxtWarning3MonthMinute("");
		// 1年間警告時間（時間）
		vo.setTxtWarning1YearHour("");
		// 1年間警告時間（分）
		vo.setTxtWarning1YearMinute("");
		
		// 非表示項目
		// 60時間超割増機能
		vo.setPlt60HourFunction("0");
		// 60時間超代替休暇
		vo.setPlt60HourAlternative("0");
		// 月60時間超割増
		vo.setTxtMonth60HourSurcharge("0");
		// 平日残業割増
		vo.setTxtWeekdayOver("0");
		// 代替休暇平日
		vo.setTxtWeekdayAlternative("0");
		// 代替休暇放棄
		vo.setTxtAltnativeCancel("0");
		// 代替休暇所定休日
		vo.setTxtAltnativeSpecific("0");
		// 代替休暇法定休日
		vo.setTxtAltnativeLegal("0");
		// 所定休日割増率
		vo.setTxtSpecificHoliday("0");
		// 法定休日割増率
		vo.setTxtLegalHoliday("0");
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setDtoFields(TimeSettingDtoInterface dto) throws MospException {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		// －基本情報－
		// 有効日
		dto.setActivateDate(getEditActivateDate());
		// 勤怠設定コード
		dto.setWorkSettingCode(vo.getTxtSettingCode());
		// 勤怠設定名称
		dto.setWorkSettingName(vo.getTxtSettingName());
		// 勤怠設定略称
		dto.setWorkSettingAbbr(vo.getTxtSettingAbbr());
		// 締日
		dto.setCutoffCode(vo.getPltCutoffDate());
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		
		// －勤怠設定－
		// 勤怠管理対象
		dto.setTimeManagementFlag(getInt(vo.getPltTimeManagement()));
		// 日々申請対象
		dto.setDailyApprovalFlag(getInt(vo.getPltDailyApproval()));
		// 勤務前残業
		dto.setBeforeOvertimeFlag(getInt(vo.getPltBeforeOverTime()));
		// 所定休日取扱
		dto.setSpecificHolidayHandling(getInt(vo.getPltSpecificHoliday()));
		// 週の起算曜日
		dto.setStartWeek(getInt(vo.getPltStartWeek()));
		// 月の起算曜日
		dto.setStartMonth(getInt(vo.getPltStartMonth()));
		// 年の起算月
		dto.setStartYear(getInt(vo.getPltStartYear()));
		// 所定労働時間
		dto.setGeneralWorkTime(DateUtility.getTime(vo.getTxtGeneralWorkTimeHour(), vo.getTxtGeneralWorkTimeMinute()));
		// 一日の起算時
		dto.setStartDayTime(DateUtility.getTime(vo.getTxtStartDayHour(), vo.getTxtStartDayMinute()));
		// 遅刻早退限度時間(全日)
		dto.setLateEarlyFull(DateUtility.getTime(vo.getTxtLateEarlyFullHour(), vo.getTxtLateEarlyFullMinute()));
		// 遅刻早退限度時間(半日)
		dto.setLateEarlyHalf(DateUtility.getTime(vo.getTxtLateEarlyHalfHour(), vo.getTxtLateEarlyHalfMinute()));
		// 振休取得期限月(休出前)
		dto.setTransferAheadLimitMonth(getInt(vo.getTxtTransferAheadLimitMonth()));
		// 振休取得期限日(休出前)
		dto.setTransferAheadLimitDate(getInt(vo.getTxtTransferAheadLimitDate()));
		// 振休取得期限月(休出後)
		dto.setTransferLaterLimitMonth(getInt(vo.getTxtTransferLaterLimitMonth()));
		// 振休取得期限日(休出後)
		dto.setTransferLaterLimitDate(getInt(vo.getTxtTransferLaterLimitDate()));
		// 代休取得期限
		dto.setSubHolidayLimitMonth(getInt(vo.getTxtSubHolidayLimitMonth()));
		// 代休取得期限
		dto.setSubHolidayLimitDate(getInt(vo.getTxtSubHolidayLimitDate()));
		// 半休入替取得（振休）
		dto.setTransferExchange(getInt(vo.getPltTransferExchange()));
		// 半休入替取得（代休）
		dto.setSubHolidayExchange(getInt(vo.getPltSubHolidayExchange()));
		// 代休基準時間(全休)
		dto.setSubHolidayAllNorm(DateUtility.getTime(vo.getTxtSubHolidayAllNormHour(),
				vo.getTxtSubHolidayAllNormMinute()));
		// 代休基準時間(半休)
		dto.setSubHolidayHalfNorm(DateUtility.getTime(vo.getTxtSubHolidayHalfNormHour(),
				vo.getTxtSubHolidayHalfNormMinute()));
		// ポータル出退勤ボタン表示
		dto.setPortalTimeButtons(getInt(vo.getPltPortalTimeButtons()));
		// ポータル休憩ボタン表示
		dto.setPortalRestButtons(getInt(vo.getPltPortalRestButtons()));
		// 勤務予定時間表示
		dto.setUseScheduledTime(getInt(vo.getPltUseScheduledTime()));
		
		// －時間丸め設定－
		// 日出勤丸め単位
		dto.setRoundDailyStartUnit(getInt(vo.getTxtRoundDailyStart()));
		// 日出勤丸め
		dto.setRoundDailyStart(getInt(vo.getPltRoundDailyStart()));
		// 日退勤丸め単位	
		dto.setRoundDailyEndUnit(getInt(vo.getTxtRoundDailyEnd()));
		// 日退勤丸め
		dto.setRoundDailyEnd(getInt(vo.getPltRoundDailyEnd()));
		// 日勤務時間丸め単位
		dto.setRoundDailyTimeWork(getInt(vo.getTxtRoundDailyWork()));
		// 日勤務時間丸め
		dto.setRoundDailyWork(getInt(vo.getPltRoundDailyWork()));
		// 日休憩入丸め単位
		dto.setRoundDailyRestStartUnit(getInt(vo.getTxtRoundDailyRestStart()));
		// 日休憩入丸め
		dto.setRoundDailyRestStart(getInt(vo.getPltRoundDailyRestStart()));
		// 日休憩戻丸め単位	
		dto.setRoundDailyRestEndUnit(getInt(vo.getTxtRoundDailyRestEnd()));
		// 日休憩戻丸め
		dto.setRoundDailyRestEnd(getInt(vo.getPltRoundDailyRestEnd()));
		// 日休憩時間丸め単位
		dto.setRoundDailyRestTimeUnit(getInt(vo.getTxtRoundDailyRestTime()));
		// 日休憩時間丸め
		dto.setRoundDailyRestTime(getInt(vo.getPltRoundDailyRestTime()));
		// 日遅刻丸め単位
		dto.setRoundDailyLateUnit(getInt(vo.getTxtRoundDailyLate()));
		// 日遅刻丸め
		dto.setRoundDailyLate(getInt(vo.getPltRoundDailyLate()));
		// 日早退丸め単位
		dto.setRoundDailyLeaveEarlyUnit(getInt(vo.getTxtRoundDailyLeaveEarly()));
		// 日早退丸め
		dto.setRoundDailyLeaveEarly(getInt(vo.getPltRoundDailyLeaveEaly()));
		// 日私用外出入丸め単位
		dto.setRoundDailyPrivateStartUnit(getInt(vo.getTxtRoundDailyPrivateIn()));
		// 日私用外出入り丸め
		dto.setRoundDailyPrivateStart(getInt(vo.getPltRoundDailyPrivateIn()));
		// 日私用外出戻丸め単位	
		dto.setRoundDailyPrivateEndUnit(getInt(vo.getTxtRoundDailyPrivateOut()));
		// 日私用外出戻り丸め
		dto.setRoundDailyPrivateEnd(getInt(vo.getPltRoundDailyPrivateOut()));
		// 日公用外出入丸め単位
		dto.setRoundDailyPublicStartUnit(getInt(vo.getTxtRoundDailyPublicIn()));
		// 日公用外出入り丸め
		dto.setRoundDailyPublicStart(getInt(vo.getPltRoundDailyPublicIn()));
		// 日公用外出戻丸め単位	
		dto.setRoundDailyPublicEndUnit(getInt(vo.getTxtRoundDailyPublicOut()));
		// 日公用外出戻り丸め
		dto.setRoundDailyPublicEnd(getInt(vo.getPltRoundDailyPublicOut()));
		// 日減額対象丸め単位
		dto.setRoundDailyDecreaseTimeUnit(getInt(vo.getTxtRoundDailyDecreaseTime()));
		// 日減額対象時間丸め
		dto.setRoundDailyDecreaseTime(getInt(vo.getPltRoundDailyDecreaseTime()));
		// 日無給時短時間丸め単位
		dto.setRoundDailyShortUnpaidUnit(getInt(vo.getTxtRoundDailyShortUnpaid()));
		// 日無給時短時間丸め
		dto.setRoundDailyShortUnpaid(getInt(vo.getPltRoundDailyShortUnpaid()));
		// 月勤務時間丸め単位
		dto.setRoundMonthlyWorkUnit(getInt(vo.getTxtRoundMonthlyWork()));
		// 月勤務時間丸め
		dto.setRoundMonthlyWork(getInt(vo.getPltRoundMonthlyWork()));
		// 月休憩時間丸め単位
		dto.setRoundMonthlyRestUnit(getInt(vo.getTxtRoundMonthlyRest()));
		// 月休憩時間丸め
		dto.setRoundMonthlyRest(getInt(vo.getPltRoundMonthlyRest()));
		// 月遅刻丸め単位
		dto.setRoundMonthlyLateUnit(getInt(vo.getTxtRoundMonthlyLate()));
		// 月遅刻時間丸め
		dto.setRoundMonthlyLate(getInt(vo.getPltRoundMonthlyLate()));
		// 月早退丸め単位	
		dto.setRoundMonthlyEarlyUnit(getInt(vo.getTxtRoundMonthlyLeaveEarly()));
		// 月早退時間丸め	
		dto.setRoundMonthlyEarly(getInt(vo.getPltRoundMonthlyLeaveEarly()));
		// 月私用外出丸め単位
		dto.setRoundMonthlyPrivateTime(getInt(vo.getTxtRoundMonthlyPrivate()));
		// 月私用外出時間丸め
		dto.setRoundMonthlyPrivate(getInt(vo.getPltRoundMonthlyPrivate()));
		// 月公用外出丸め単位
		dto.setRoundMonthlyPublicTime(getInt(vo.getTxtRoundMonthlyPublic()));
		// 月公用外出時間丸め
		dto.setRoundMonthlyPublic(getInt(vo.getPltRoundMonthlyPublic()));
		// 月減額対象丸め単位
		dto.setRoundMonthlyDecreaseTime(getInt(vo.getTxtRoundMonthlyDecreaseTime()));
		// 月減額対象時間丸め
		dto.setRoundMonthlyDecrease(getInt(vo.getPltRoundMonthlyDecreaseTime()));
		// 月無給時短時間丸め単位
		dto.setRoundMonthlyShortUnpaidUnit(getInt(vo.getTxtRoundMonthlyShortUnpaid()));
		// 月無給時短時間丸め
		dto.setRoundMonthlyShortUnpaid(getInt(vo.getPltRoundMonthlyShortUnpaid()));
		
		// －60時間超設定項目－
		// 60時間超割増機能
		dto.setSixtyHourFunctionFlag(getInt(vo.getPlt60HourFunction()));
		// 60時間超代替休暇
		dto.setSixtyHourAlternativeFlag(getInt(vo.getPlt60HourAlternative()));
		// 月60時間超割増
		dto.setMonthSixtyHourSurcharge(getInt(vo.getTxtMonth60HourSurcharge()));
		// 平日残業割増
		dto.setWeekdayOver(getInt(vo.getTxtWeekdayOver()));
		// 代替休暇平日
		dto.setWeekdayAlternative(getInt(vo.getTxtWeekdayAlternative()));
		// 代替休暇放棄
		dto.setAlternativeCancel(getInt(vo.getTxtAltnativeCancel()));
		// 代替休暇所定休日
		dto.setAlternativeSpecific(getInt(vo.getTxtAltnativeSpecific()));
		// 代替休暇法定休日
		dto.setAlternativeLegal(getInt(vo.getTxtAltnativeLegal()));
		// 所定休日割増率
		dto.setSpecificHoliday(getInt(vo.getTxtSpecificHoliday()));
		// 法定休日割増率
		dto.setLegalHoliday(getInt(vo.getTxtLegalHoliday()));
	}
	
	/**
	 * VO(編集項目)の値を限度基準DTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param term 期間
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setLimitDtoFields(LimitStandardDtoInterface dto, String term) throws MospException {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setActivateDate(getEditActivateDate());
		dto.setWorkSettingCode(vo.getTxtSettingCode());
		dto.setTerm(term);
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		if (term.equals("week1")) {
			// 1週間
			dto.setLimitTime(getTimeValue(vo.getTxtLimit1WeekHour(), vo.getTxtLimit1WeekMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention1WeekHour(), vo.getTxtAttention1WeekMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning1WeekHour(), vo.getTxtWarning1WeekMinute()));
		} else if (term.equals("week2")) {
			// 2週間
			dto.setLimitTime(getTimeValue(vo.getTxtLimit2WeekHour(), vo.getTxtLimit2WeekMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention2WeekHour(), vo.getTxtAttention2WeekMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning2WeekHour(), vo.getTxtWarning2WeekMinute()));
		} else if (term.equals("week4")) {
			// 4週間
			dto.setLimitTime(getTimeValue(vo.getTxtLimit4WeekHour(), vo.getTxtLimit4WeekMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention4WeekHour(), vo.getTxtAttention4WeekMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning4WeekHour(), vo.getTxtWarning4WeekMinute()));
		} else if (term.equals("month1")) {
			// 1ヶ月
			dto.setLimitTime(getTimeValue(vo.getTxtLimit1MonthHour(), vo.getTxtLimit1MonthMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention1MonthHour(), vo.getTxtAttention1MonthMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning1MonthHour(), vo.getTxtWarning1MonthMinute()));
		} else if (term.equals("month2")) {
			// 2ヶ月
			dto.setLimitTime(getTimeValue(vo.getTxtLimit2MonthHour(), vo.getTxtLimit2MonthMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention2MonthHour(), vo.getTxtAttention2MonthMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning2MonthHour(), vo.getTxtWarning2MonthMinute()));
		} else if (term.equals("month3")) {
			// 3ヶ月
			dto.setLimitTime(getTimeValue(vo.getTxtLimit3MonthHour(), vo.getTxtLimit3MonthMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention3MonthHour(), vo.getTxtAttention3MonthMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning3MonthHour(), vo.getTxtWarning3MonthMinute()));
		} else if (term.equals("year1")) {
			// 1年間
			dto.setLimitTime(getTimeValue(vo.getTxtLimit1YearHour(), vo.getTxtLimit1YearMinute()));
			dto.setAttentionTime(getTimeValue(vo.getTxtAttention1YearHour(), vo.getTxtAttention1YearMinute()));
			dto.setWarningTime(getTimeValue(vo.getTxtWarning1YearHour(), vo.getTxtWarning1YearMinute()));
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(TimeSettingDtoInterface dto) {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		// 基本情報
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtSettingCode(dto.getWorkSettingCode());
		vo.setTxtSettingName(dto.getWorkSettingName());
		vo.setTxtSettingAbbr(dto.getWorkSettingAbbr());
		vo.setPltCutoffDate(dto.getCutoffCode());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		// 勤怠設定
		// 勤怠管理対象
		vo.setPltTimeManagement(String.valueOf(dto.getTimeManagementFlag()));
		// 日々申請対象
		vo.setPltDailyApproval(String.valueOf(dto.getDailyApprovalFlag()));
		// 勤務前残業
		vo.setPltBeforeOverTime(String.valueOf(dto.getBeforeOvertimeFlag()));
		// 所定休日取扱
		vo.setPltSpecificHoliday(String.valueOf(dto.getSpecificHolidayHandling()));
		// 週の起算曜日
		vo.setPltStartWeek(String.valueOf(dto.getStartWeek()));
		// 月の起算日（非表示）
		vo.setPltStartMonth(String.valueOf(dto.getStartMonth()));
		// 年の起算月
		vo.setPltStartYear(String.valueOf(dto.getStartYear()));
		// 所定労働時間（時間）
		vo.setTxtGeneralWorkTimeHour(DateUtility.getStringHour(dto.getGeneralWorkTime()));
		// 所定労働時間（分）
		vo.setTxtGeneralWorkTimeMinute(DateUtility.getStringMinute(dto.getGeneralWorkTime()));
		// 一日の起算時（時間）
		vo.setTxtStartDayHour(DateUtility.getStringHour(dto.getStartDayTime()));
		// 一日の起算時（分）
		vo.setTxtStartDayMinute(DateUtility.getStringMinute(dto.getStartDayTime()));
		// 遅刻早退限度時間（全日）
		vo.setTxtLateEarlyFullHour(DateUtility.getStringHour(dto.getLateEarlyFull()));
		// 遅刻早退限度分（全日）
		vo.setTxtLateEarlyFullMinute(DateUtility.getStringMinute(dto.getLateEarlyFull()));
		// 遅刻早退限度時間（半日）
		vo.setTxtLateEarlyHalfHour(DateUtility.getStringHour(dto.getLateEarlyHalf()));
		// 遅刻早退限度分（半日）
		vo.setTxtLateEarlyHalfMinute(DateUtility.getStringMinute(dto.getLateEarlyHalf()));
		// 振休取得期限月(休出前)
		vo.setTxtTransferAheadLimitMonth(String.valueOf(dto.getTransferAheadLimitMonth()));
		// 振休取得期限日(休出前)
		vo.setTxtTransferAheadLimitDate(String.valueOf(dto.getTransferAheadLimitDate()));
		// 振休取得期限月(休出後)
		vo.setTxtTransferLaterLimitMonth(String.valueOf(dto.getTransferLaterLimitMonth()));
		// 振休取得期限日(休出後)
		vo.setTxtTransferLaterLimitDate(String.valueOf(dto.getTransferLaterLimitDate()));
		// 代休取得期限
		vo.setTxtSubHolidayLimitMonth(String.valueOf(dto.getSubHolidayLimitMonth()));
		// 代休取得期限
		vo.setTxtSubHolidayLimitDate(String.valueOf(dto.getSubHolidayLimitDate()));
		// 半休入替取得（振休）
		vo.setPltTransferExchange(String.valueOf(dto.getTransferExchange()));
		// 半休入替取得（代休）
		vo.setPltSubHolidayExchange(String.valueOf(dto.getSubHolidayExchange()));
		// 代休基準時間(全休)
		vo.setTxtSubHolidayAllNormHour(DateUtility.getStringHour(dto.getSubHolidayAllNorm()));
		// 代休基準分(全休)
		vo.setTxtSubHolidayAllNormMinute(DateUtility.getStringMinute(dto.getSubHolidayAllNorm()));
		// 代休基準時間(半休)
		vo.setTxtSubHolidayHalfNormHour(DateUtility.getStringHour(dto.getSubHolidayHalfNorm()));
		// 代休基準分(半休)
		vo.setTxtSubHolidayHalfNormMinute(DateUtility.getStringMinute(dto.getSubHolidayHalfNorm()));
		// ポータル出退勤ボタン表示
		vo.setPltPortalTimeButtons(String.valueOf(dto.getPortalTimeButtons()));
		// ポータル休憩ボタン表示
		vo.setPltPortalRestButtons(String.valueOf(dto.getPortalRestButtons()));
		// 勤務予定時間表示
		vo.setPltUseScheduledTime(String.valueOf(dto.getUseScheduledTime()));
		// 時間丸め設定
		// 日出勤丸め単位
		vo.setTxtRoundDailyStart(String.valueOf(dto.getRoundDailyStartUnit()));
		// 日出勤丸め
		vo.setPltRoundDailyStart(String.valueOf(dto.getRoundDailyStart()));
		// 日退勤丸め単位	
		vo.setTxtRoundDailyEnd(String.valueOf(dto.getRoundDailyEndUnit()));
		// 日退勤丸め
		vo.setPltRoundDailyEnd(String.valueOf(dto.getRoundDailyEnd()));
		// 日勤務時間丸め単位
		vo.setTxtRoundDailyWork(String.valueOf(dto.getRoundDailyWorkUnit()));
		// 日勤務時間丸め
		vo.setPltRoundDailyWork(String.valueOf(dto.getRoundDailyWork()));
		// 日休憩入丸め単位
		vo.setTxtRoundDailyRestStart(String.valueOf(dto.getRoundDailyRestStartUnit()));
		// 日休憩入丸め
		vo.setPltRoundDailyRestStart(String.valueOf(dto.getRoundDailyRestStart()));
		// 日休憩戻丸め単位	
		vo.setTxtRoundDailyRestEnd(String.valueOf(dto.getRoundDailyRestEndUnit()));
		// 日休憩戻丸め
		vo.setPltRoundDailyRestEnd(String.valueOf(dto.getRoundDailyRestEnd()));
		// 日休憩時間丸め単位
		vo.setTxtRoundDailyRestTime(String.valueOf(dto.getRoundDailyRestTimeUnit()));
		// 日休憩時間丸め
		vo.setPltRoundDailyRestTime(String.valueOf(dto.getRoundDailyRestTime()));
		// 日遅刻丸め単位
		vo.setTxtRoundDailyLate(String.valueOf(dto.getRoundDailyLateUnit()));
		// 日遅刻丸め
		vo.setPltRoundDailyLate(String.valueOf(dto.getRoundDailyLate()));
		// 日早退丸め単位
		vo.setTxtRoundDailyLeaveEarly(String.valueOf(dto.getRoundDailyLeaveEarlyUnit()));
		// 日早退丸め
		vo.setPltRoundDailyLeaveEaly(String.valueOf(dto.getRoundDailyLeaveEarly()));
		// 日私用外出入丸め単位
		vo.setTxtRoundDailyPrivateIn(String.valueOf(dto.getRoundDailyPrivateStartUnit()));
		// 日私用外出入り丸め
		vo.setPltRoundDailyPrivateIn(String.valueOf(dto.getRoundDailyPrivateStart()));
		// 日私用外出戻丸め単位	
		vo.setTxtRoundDailyPrivateOut(String.valueOf(dto.getRoundDailyPrivateEndUnit()));
		// 日私用外出戻り丸め
		vo.setPltRoundDailyPrivateOut(String.valueOf(dto.getRoundDailyPrivateEnd()));
		// 日公用外出入丸め単位
		vo.setTxtRoundDailyPublicIn(String.valueOf(dto.getRoundDailyPublicStartUnit()));
		// 日公用外出入り丸め
		vo.setPltRoundDailyPublicIn(String.valueOf(dto.getRoundDailyPublicStart()));
		// 日公用外出戻丸め単位	
		vo.setTxtRoundDailyPublicOut(String.valueOf(dto.getRoundDailyPublicEndUnit()));
		// 日公用外出戻り丸め
		vo.setPltRoundDailyPublicOut(String.valueOf(dto.getRoundDailyPublicEnd()));
		// 日減額対象丸め単位
		vo.setTxtRoundDailyDecreaseTime(String.valueOf(dto.getRoundDailyDecreaseTimeUnit()));
		// 日減額対象時間丸め
		vo.setPltRoundDailyDecreaseTime(String.valueOf(dto.getRoundDailyDecreaseTime()));
		// 日無給時短時間丸め単位
		vo.setTxtRoundDailyShortUnpaid(String.valueOf(dto.getRoundDailyShortUnpaidUnit()));
		// 日無給時短時間丸め
		vo.setPltRoundDailyShortUnpaid(String.valueOf(dto.getRoundDailyShortUnpaid()));
		// 月勤務時間丸め単位
		vo.setTxtRoundMonthlyWork(String.valueOf(dto.getRoundMonthlyWorkUnit()));
		// 月勤務時間丸め
		vo.setPltRoundMonthlyWork(String.valueOf(dto.getRoundMonthlyWork()));
		// 月休憩時間丸め単位
		vo.setTxtRoundMonthlyRest(String.valueOf(dto.getRoundMonthlyRestUnit()));
		// 月休憩時間丸め
		vo.setPltRoundMonthlyRest(String.valueOf(dto.getRoundMonthlyRest()));
		// 月遅刻丸め単位
		vo.setTxtRoundMonthlyLate(String.valueOf(dto.getRoundMonthlyLateUnit()));
		// 月遅刻時間丸め
		vo.setPltRoundMonthlyLate(String.valueOf(dto.getRoundMonthlyLate()));
		// 月早退丸め単位
		vo.setTxtRoundMonthlyLeaveEarly(String.valueOf(dto.getRoundMonthlyEarlyUnit()));
		// 月早退時間丸め
		vo.setPltRoundMonthlyLeaveEarly(String.valueOf(dto.getRoundMonthlyEarly()));
		// 月私用外出丸め単位
		vo.setTxtRoundMonthlyPrivate(String.valueOf(dto.getRoundMonthlyPrivateTime()));
		// 月私用外出時間丸め
		vo.setPltRoundMonthlyPrivate(String.valueOf(dto.getRoundMonthlyPrivate()));
		// 月公用外出丸め単位
		vo.setTxtRoundMonthlyPublic(String.valueOf(dto.getRoundMonthlyPublicTime()));
		// 月公用外出時間丸め
		vo.setPltRoundMonthlyPublic(String.valueOf(dto.getRoundMonthlyPublic()));
		// 月減額対象丸め単位
		vo.setTxtRoundMonthlyDecreaseTime(String.valueOf(dto.getRoundMonthlyDecreaseTime()));
		// 月減額対象時間丸め
		vo.setPltRoundMonthlyDecreaseTime(String.valueOf(dto.getRoundMonthlyDecrease()));
		// 月無給時短時間丸め単位
		vo.setTxtRoundMonthlyShortUnpaid(String.valueOf(dto.getRoundMonthlyShortUnpaidUnit()));
		// 月無給時短時間丸め
		vo.setPltRoundMonthlyShortUnpaid(String.valueOf(dto.getRoundMonthlyShortUnpaid()));
		// 非表示項目
		// 60時間超割増機能
		vo.setPlt60HourFunction(String.valueOf(dto.getSixtyHourFunctionFlag()));
		// 60時間超代替休暇
		vo.setPlt60HourAlternative(String.valueOf(dto.getSixtyHourAlternativeFlag()));
		// 月60時間超割増
		vo.setTxtMonth60HourSurcharge(String.valueOf(dto.getMonthSixtyHourSurcharge()));
		// 平日残業割増
		vo.setTxtWeekdayOver(String.valueOf(dto.getWeekdayOver()));
		// 代替休暇平日
		vo.setTxtWeekdayAlternative(String.valueOf(dto.getWeekdayAlternative()));
		// 代替休暇放棄
		vo.setTxtAltnativeCancel(String.valueOf(dto.getAlternativeCancel()));
		// 代替休暇所定休日
		vo.setTxtAltnativeSpecific(String.valueOf(dto.getAlternativeSpecific()));
		// 代替休暇法定休日
		vo.setTxtAltnativeLegal(String.valueOf(dto.getAlternativeLegal()));
		// 所定休日割増率
		vo.setTxtSpecificHoliday(String.valueOf(dto.getSpecificHoliday()));
		// 法定休日割増率
		vo.setTxtLegalHoliday(String.valueOf(dto.getLegalHoliday()));
	}
	
	/**
	 * 限度基準DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setLimitVoFields(LimitStandardDtoInterface dto) {
		// VO取得
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		String term = dto.getTerm();
		// DTOの値をVOに設定
		if (term.equals("week1")) {
			// 1週間
			vo.setTxtLimit1WeekHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit1WeekMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention1WeekHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention1WeekMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning1WeekHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning1WeekMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("week2")) {
			// 2週間
			vo.setTxtLimit2WeekHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit2WeekMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention2WeekHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention2WeekMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning2WeekHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning2WeekMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("week4")) {
			// 4週間
			vo.setTxtLimit4WeekHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit4WeekMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention4WeekHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention4WeekMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning4WeekHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning4WeekMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("month1")) {
			// 1ヶ月
			vo.setTxtLimit1MonthHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit1MonthMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention1MonthHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention1MonthMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning1MonthHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning1MonthMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("month2")) {
			// 2ヶ月
			vo.setTxtLimit2MonthHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit2MonthMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention2MonthHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention2MonthMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning2MonthHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning2MonthMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("month3")) {
			// 3ヶ月
			vo.setTxtLimit3MonthHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit3MonthMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention3MonthHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention3MonthMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning3MonthHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning3MonthMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		} else if (term.equals("year1")) {
			// 1年間
			vo.setTxtLimit1YearHour(convIntegerTimeToStringHour(dto.getLimitTime()));
			vo.setTxtLimit1YearMinute(convIntegerTimeToStringMinutes(dto.getLimitTime()));
			vo.setTxtAttention1YearHour(convIntegerTimeToStringHour(dto.getAttentionTime()));
			vo.setTxtAttention1YearMinute(convIntegerTimeToStringMinutes(dto.getAttentionTime()));
			vo.setTxtWarning1YearHour(convIntegerTimeToStringHour(dto.getWarningTime()));
			vo.setTxtWarning1YearMinute(convIntegerTimeToStringMinutes(dto.getWarningTime()));
		}
	}
	
}
