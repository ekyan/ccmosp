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
package jp.mosp.platform.human.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.SuspensionCardVo;
import jp.mosp.platform.utils.InputCheckUtility;

/**
 * 社員一覧画面で選択した社員の休職情報の登録・更新・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class SuspensionCardAction extends PlatformHumanAction {
	
	/**
	 * 社員検索コマンド。<br>
	 * <br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH		= "PF1162";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 選択表示コマンド。社員一覧画面で選択された社員の個人IDを基に休職情報を表示する。<br>
	 */
	public static final String	CMD_SELECT		= "PF1166";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 登録済みの休職情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE		= "PF1167";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄の入力内容を基に休職情報テーブルの更新を行う。<br>
	 * レコードが存在しなければ新規登録し、既存のものがあるなら更新の処理を行う。<br>
	 * 入力チェックを行った際に入力必須項目が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_UPDATE		= "PF1168";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER	= "PF1169";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public SuspensionCardAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SuspensionCardVo();
	}
	
	/**
	 * 検索処理を行う。
	 * @throws MospException VO、或いは社員情報の取得に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 休職情報を取得しVOに設定
		setSuspensionInfo();
	}
	
	/**
	 * 編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 休職情報を取得しVOに設定
		setSuspensionInfo();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 入力値確認
		validate();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 登録クラス取得
		SuspensionRegistBeanInterface regist = platform().suspensionRegist();
		// 登録DTOリスト準備
		List<SuspensionDtoInterface> list = regist.getInitDtoList(vo.getAryHidPfaHumanSuspension().length);
		// DTOに値を設定
		setDtoFields(list);
		// 登録処理
		regist.regist(list);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージ設定
		addInsertMessage();
		// 休職情報再設定
		setSuspensionInfo();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	protected void delete() throws MospException {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(getIndexArray(vo.getCkbSelect()), vo.getAryHidPfaHumanSuspension());
		// 削除対象確認(DB未登録行のみチェックを付けるとidArrayは長さ0)
		if (idArray.length == 0) {
			// VOから削除行を除去
			removeDeletedRow();
			// 削除成功メッセージ設定
			addDeleteMessage();
			return;
		}
		// 削除処理
		platform().suspensionRegist().delete(idArray);
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
		// VOから削除行を除去
		removeDeletedRow();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 休職情報を取得し、VOに設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setSuspensionInfo() throws MospException {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 休職情報を取得しVOに設定
		setVoFields(reference().suspension().getSuspentionList(vo.getPersonalId()));
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 初期値準備
		String[] aryBlank = { "" };
		// 初期値設定
		vo.setAryHidPfaHumanSuspension(aryBlank);
		vo.setAryTxtSuspensionEndYear(aryBlank);
		vo.setAryTxtSuspensionEndMonth(aryBlank);
		vo.setAryTxtSuspensionEndDay(aryBlank);
		vo.setAryTxtSuspensionReason(aryBlank);
		vo.setAryTxtSuspensionScheduleEndDay(aryBlank);
		vo.setAryTxtSuspensionScheduleEndMonth(aryBlank);
		vo.setAryTxtSuspensionScheduleEndYear(aryBlank);
		vo.setAryTxtSuspensionStartDay(aryBlank);
		vo.setAryTxtSuspensionStartMonth(aryBlank);
		vo.setAryTxtSuspensionStartYear(aryBlank);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTOリスト
	 */
	protected void setVoFields(List<SuspensionDtoInterface> list) {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 休職情報存在確認
		if (list.isEmpty()) {
			// 初期化
			setDefaultValues();
			return;
		}
		// 設定配列準備
		String[] aryHidPfaHumanSuspension = new String[list.size()];
		String[] aryTxtSuspensionStartYear = new String[list.size()];
		String[] aryTxtSuspensionStartMonth = new String[list.size()];
		String[] aryTxtSuspensionStartDay = new String[list.size()];
		String[] aryTxtSuspensionScheduleEndDay = new String[list.size()];
		String[] aryTxtSuspensionScheduleEndMonth = new String[list.size()];
		String[] aryTxtSuspensionScheduleEndYear = new String[list.size()];
		String[] aryTxtSuspensionEndYear = new String[list.size()];
		String[] aryTxtSuspensionEndMonth = new String[list.size()];
		String[] aryTxtSuspensionEndDay = new String[list.size()];
		String[] aryTxtSuspensionReason = new String[list.size()];
		// 配列に値を設定
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			SuspensionDtoInterface dto = list.get(i);
			// 開始日
			aryTxtSuspensionStartYear[i] = getStringYear(dto.getStartDate());
			aryTxtSuspensionStartMonth[i] = getStringMonth(dto.getStartDate());
			aryTxtSuspensionStartDay[i] = getStringDay(dto.getStartDate());
			// 終了予定日
			aryTxtSuspensionScheduleEndYear[i] = getStringYear(dto.getScheduleEndDate());
			aryTxtSuspensionScheduleEndMonth[i] = getStringMonth(dto.getScheduleEndDate());
			aryTxtSuspensionScheduleEndDay[i] = getStringDay(dto.getScheduleEndDate());
			// 終了日
			aryTxtSuspensionEndYear[i] = getStringYear(dto.getEndDate());
			aryTxtSuspensionEndMonth[i] = getStringMonth(dto.getEndDate());
			aryTxtSuspensionEndDay[i] = getStringDay(dto.getEndDate());
			// 理由
			aryTxtSuspensionReason[i] = dto.getSuspensionReason();
			// 識別ID
			aryHidPfaHumanSuspension[i] = String.valueOf(dto.getPfaHumanSuspensionId());
		}
		// 配列をVOに設定
		vo.setAryTxtSuspensionStartYear(aryTxtSuspensionStartYear);
		vo.setAryTxtSuspensionStartMonth(aryTxtSuspensionStartMonth);
		vo.setAryTxtSuspensionStartDay(aryTxtSuspensionStartDay);
		vo.setAryTxtSuspensionScheduleEndYear(aryTxtSuspensionScheduleEndYear);
		vo.setAryTxtSuspensionScheduleEndMonth(aryTxtSuspensionScheduleEndMonth);
		vo.setAryTxtSuspensionScheduleEndDay(aryTxtSuspensionScheduleEndDay);
		vo.setAryTxtSuspensionEndYear(aryTxtSuspensionEndYear);
		vo.setAryTxtSuspensionEndMonth(aryTxtSuspensionEndMonth);
		vo.setAryTxtSuspensionEndDay(aryTxtSuspensionEndDay);
		vo.setAryTxtSuspensionReason(aryTxtSuspensionReason);
		vo.setAryHidPfaHumanSuspension(aryHidPfaHumanSuspension);
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事休職情報)に設定する。<br>
	 * @param list 休職情報DTOリスト
	 */
	protected void setDtoFields(List<SuspensionDtoInterface> list) {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// リスト内DTOに値を設定
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			SuspensionDtoInterface dto = list.get(i);
			// レコードID設定(追加行はレコードIDが空白)
			if (vo.getAryHidPfaHumanSuspension()[i].isEmpty()) {
				// 空白なら0を設定
				dto.setPfaHumanSuspensionId(0L);
			} else {
				dto.setPfaHumanSuspensionId(getLong(vo.getAryHidPfaHumanSuspension()[i]));
			}
			// 値設定
			dto.setPersonalId(vo.getPersonalId());
			dto.setStartDate(getSuspensionStartDate(i));
			dto.setEndDate(getSuspensionEndDate(i));
			dto.setScheduleEndDate(getSuspensionScheduleEndDate(i));
			dto.setSuspensionReason(vo.getAryTxtSuspensionReason()[i]);
			// 給与区分(空白)
			dto.setAllowanceType("");
		}
	}
	
	/**
	 * VOから削除行を除去する。<br>
	 */
	protected void removeDeletedRow() {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 削除対象インデックス配列取得
		int[] deletedRows = getIndexArray(vo.getCkbSelect());
		// 配列長取得
		int length = vo.getAryHidPfaHumanSuspension().length - deletedRows.length;
		// 配列長確認
		if (length <= 0) {
			// 初期化
			setDefaultValues();
			return;
		}
		// 設定配列準備
		String[] aryHidPfaHumanSuspension = new String[length];
		String[] aryTxtSuspensionStartYear = new String[length];
		String[] aryTxtSuspensionStartMonth = new String[length];
		String[] aryTxtSuspensionStartDay = new String[length];
		String[] aryTxtSuspensionScheduleEndDay = new String[length];
		String[] aryTxtSuspensionScheduleEndMonth = new String[length];
		String[] aryTxtSuspensionScheduleEndYear = new String[length];
		String[] aryTxtSuspensionEndYear = new String[length];
		String[] aryTxtSuspensionEndMonth = new String[length];
		String[] aryTxtSuspensionEndDay = new String[length];
		String[] aryTxtSuspensionReason = new String[length];
		// 配列に値を設定
		int idx = 0;
		for (int i = 0; i < vo.getAryHidPfaHumanSuspension().length; i++) {
			// 削除行確認
			if (isIndexed(i, deletedRows)) {
				continue;
			}
			// 開始日
			aryTxtSuspensionStartYear[idx] = vo.getAryTxtSuspensionStartYear()[i];
			aryTxtSuspensionStartMonth[idx] = vo.getAryTxtSuspensionStartMonth()[i];
			aryTxtSuspensionStartDay[idx] = vo.getAryTxtSuspensionStartDay()[i];
			// 終了予定日
			aryTxtSuspensionScheduleEndYear[idx] = vo.getAryTxtSuspensionScheduleEndYear()[i];
			aryTxtSuspensionScheduleEndMonth[idx] = vo.getAryTxtSuspensionScheduleEndMonth()[i];
			aryTxtSuspensionScheduleEndDay[idx] = vo.getAryTxtSuspensionScheduleEndDay()[i];
			// 終了日
			aryTxtSuspensionEndYear[idx] = vo.getAryTxtSuspensionEndYear()[i];
			aryTxtSuspensionEndMonth[idx] = vo.getAryTxtSuspensionEndMonth()[i];
			aryTxtSuspensionEndDay[idx] = vo.getAryTxtSuspensionEndDay()[i];
			// 理由
			aryTxtSuspensionReason[idx] = vo.getAryTxtSuspensionReason()[i];
			// 識別ID
			aryHidPfaHumanSuspension[idx++] = vo.getAryHidPfaHumanSuspension()[i];
		}
		// 配列をVOに設定
		vo.setAryTxtSuspensionStartYear(aryTxtSuspensionStartYear);
		vo.setAryTxtSuspensionStartMonth(aryTxtSuspensionStartMonth);
		vo.setAryTxtSuspensionStartDay(aryTxtSuspensionStartDay);
		vo.setAryTxtSuspensionScheduleEndYear(aryTxtSuspensionScheduleEndYear);
		vo.setAryTxtSuspensionScheduleEndMonth(aryTxtSuspensionScheduleEndMonth);
		vo.setAryTxtSuspensionScheduleEndDay(aryTxtSuspensionScheduleEndDay);
		vo.setAryTxtSuspensionEndYear(aryTxtSuspensionEndYear);
		vo.setAryTxtSuspensionEndMonth(aryTxtSuspensionEndMonth);
		vo.setAryTxtSuspensionEndDay(aryTxtSuspensionEndDay);
		vo.setAryTxtSuspensionReason(aryTxtSuspensionReason);
		vo.setAryHidPfaHumanSuspension(aryHidPfaHumanSuspension);
	}
	
	/**
	 * 入力値妥当性確認
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void validate() throws MospException {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 「休職開始日」名称取得
		String leaveStartDay = mospParams.getName("RetirementLeave", "Start", "Day");
		String[] aryLeaveStartDay = new String[]{ leaveStartDay };
		// 「休職終了予定日」名称取得
		String leaveEndScheduleDay = mospParams.getName("RetirementLeave", "End", "Schedule", "Day");
		String[] aryLeaveEndScheduleDay = new String[]{ leaveEndScheduleDay };
		// 「休職終了日」名称取得
		String leaveEndDay = mospParams.getName("RetirementLeave", "End", "Day");
		String[] aryLeaveEndDay = new String[]{ leaveEndDay };
		
		// 期間妥当性エラーメッセージ配列
		String[] aryErrDateMessage;
		for (int i = 0; i < vo.getAryTxtSuspensionStartYear().length; i++) {
			// 休職開始日項目確認
			// 必須項目チェック
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionStartYear()[i], aryLeaveStartDay);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionStartMonth()[i], aryLeaveStartDay);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionStartDay()[i], aryLeaveStartDay);
			// 数字型チェック
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionStartYear()[i], aryLeaveStartDay);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionStartMonth()[i], aryLeaveStartDay);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionStartDay()[i], aryLeaveStartDay);
			// 項目長チェック
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionStartYear()[i], 4, leaveStartDay);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionStartMonth()[i], 2, leaveStartDay);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionStartDay()[i], 2, leaveStartDay);
			// 休職終了予定日項目確認
			// 必須項目チェック
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionScheduleEndYear()[i],
					aryLeaveEndScheduleDay);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionScheduleEndYear()[i],
					aryLeaveEndScheduleDay);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionScheduleEndDay()[i],
					aryLeaveEndScheduleDay);
			// 数字型チェック
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionScheduleEndYear()[i],
					aryLeaveEndScheduleDay);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionScheduleEndYear()[i],
					aryLeaveEndScheduleDay);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionScheduleEndDay()[i],
					aryLeaveEndScheduleDay);
			// 項目長チェック
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionScheduleEndYear()[i], 4,
					leaveEndScheduleDay);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionScheduleEndMonth()[i], 2,
					leaveEndScheduleDay);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionScheduleEndDay()[i], 2,
					leaveEndScheduleDay);
			
			// 終了予定日
			Date endScheduleDate = getDate(vo.getAryTxtSuspensionScheduleEndYear()[i],
					vo.getAryTxtSuspensionScheduleEndMonth()[i], vo.getAryTxtSuspensionScheduleEndDay()[i]);
			// 開始日
			Date startDate = getDate(vo.getAryTxtSuspensionStartYear()[i], vo.getAryTxtSuspensionStartMonth()[i],
					vo.getAryTxtSuspensionStartDay()[i]);
			
			aryErrDateMessage = new String[]{ leaveEndScheduleDay, leaveStartDay + mospParams.getName("Since") };
			// 終了予定日妥当性チェック
			InputCheckUtility.checkTerm(mospParams, endScheduleDate, startDate, endScheduleDate, aryErrDateMessage);
			
			// 終了日が入っていた場合
			if (vo.getAryTxtSuspensionEndYear()[i] != null && !vo.getAryTxtSuspensionEndYear()[i].isEmpty()) {
				// 休職終了日項目確認
				// 必須項目チェック
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionEndYear()[i], aryLeaveEndDay);
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionEndMonth()[i], aryLeaveEndDay);
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtSuspensionEndDay()[i], aryLeaveEndDay);
				
				// 数字型チェック
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionEndYear()[i], aryLeaveEndDay);
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionEndMonth()[i], aryLeaveEndDay);
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtSuspensionEndDay()[i], aryLeaveEndDay);
				// 項目長チェック
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionEndYear()[i], 4, leaveEndDay);
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionEndMonth()[i], 2, leaveEndDay);
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtSuspensionEndDay()[i], 2, leaveEndDay);
				Date endDate = getDate(vo.getAryTxtSuspensionEndYear()[i], vo.getAryTxtSuspensionEndMonth()[i],
						vo.getAryTxtSuspensionEndDay()[i]);
				aryErrDateMessage = new String[]{ leaveEndDay, leaveStartDay + mospParams.getName("Since") };
				// 終了日期間妥当性チェック
				InputCheckUtility.checkTerm(mospParams, endDate, startDate, endDate, aryErrDateMessage);
			}
		}
	}
	
	/**
	 * VOから休職開始日を取得する。<br>
	 * @param idx インデックス
	 * @return 休職開始日
	 */
	protected Date getSuspensionStartDate(int idx) {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 休職開始日取得
		return getDate(vo.getAryTxtSuspensionStartYear()[idx], vo.getAryTxtSuspensionStartMonth()[idx],
				vo.getAryTxtSuspensionStartDay()[idx]);
	}
	
	/**
	 * VOから休職終了予定日を取得する。<br>
	 * @param idx インデックス
	 * @return 休職終了予定日
	 */
	protected Date getSuspensionScheduleEndDate(int idx) {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 休職終了予定日取得
		return getDate(vo.getAryTxtSuspensionScheduleEndYear()[idx], vo.getAryTxtSuspensionScheduleEndMonth()[idx],
				vo.getAryTxtSuspensionScheduleEndDay()[idx]);
	}
	
	/**
	 * VOから休職終了日を取得する。<br>
	 * @param idx インデックス
	 * @return 休職終了日
	 */
	protected Date getSuspensionEndDate(int idx) {
		// VO取得
		SuspensionCardVo vo = (SuspensionCardVo)mospParams.getVo();
		// 休職終了日取得
		return getDate(vo.getAryTxtSuspensionEndYear()[idx], vo.getAryTxtSuspensionEndMonth()[idx],
				vo.getAryTxtSuspensionEndDay()[idx]);
	}
	
}
