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
package jp.mosp.time.calculation.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TotalTimeBaseAction;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionSearchBeanInterface;
import jp.mosp.time.calculation.vo.TotalTimeVo;
import jp.mosp.time.comparator.settings.TotalTimeCutoffListCutoffCodeComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCutoffListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠集計の実行や結果の参照を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TEMP_TIGHTEN}
 * </li><li>
 * {@link #CMD_DECIDE}
 * </li><li>
 * {@link #CMD_REMOVE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_TOTAL_MONTH}
 * </li></ul>
 */
public class TotalTimeAction extends TotalTimeBaseAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "TM3110";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った締日情報の一覧表示を行う。<br>
	 * 一覧表示の際には締日コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM3112";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示してからパンくずリスト等で再度遷移する場合は保持していた検索条件で検索を行って再表示する。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM3113";
	
	/**
	 * 仮締コマンド。<br>
	 * <br>
	 * その月の勤怠情報が編集できないようにロックする。対象月に未承認・未申請のレコードの有無をチェックし、<br>
	 * 発見された場合はエラー内容の参照画面へ遷移する。<br>
	 */
	public static final String	CMD_TEMP_TIGHTEN	= "TM3114";
	
	/**
	 * 確定コマンド。<br>
	 * <br>
	 * その月の勤怠集計を確定し、対象月を翌月へ進める。仮締と同様に未申請・未承認レコードの有無を確認し、<br>
	 * 発見されたらエラー内容表示画面を表示する。<br>
	 */
	public static final String	CMD_DECIDE			= "TM3115";
	
	/**
	 * 解除コマンド。<br>
	 * <br>
	 * 仮締、確定の実行されたレコードを元の未集計状態へと戻す。該当データのレコードは修正が可能となる。<br>
	 */
	public static final String	CMD_REMOVE			= "TM3117";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "TM3118";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "TM3119";
	
	/**
	 * 集計月決定コマンド。<br>
	 * <br>
	 * 集計月欄に入力されている年月を基に締日情報を検索し、締日名称と締日日付の各プルダウンにその年月時点で有効な情報を入れて表示する。<br>
	 */
	public static final String	CMD_SET_TOTAL_MONTH	= "TM3180";
	
	/**
	 * 未締状態ステータス。<br>
	 * <br>
	 * 検索結果の締状態に未締があればtrueを設定する。<br>
	 */
	public static boolean		unwellsetState;
	
	/**
	 * 仮締状態ステータス。<br>
	 * <br>
	 * 検索結果の締状態に仮締があればtrueを設定する。<br>
	 */
	public static boolean		draftState;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TotalTimeAction() {
		super();
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TotalTimeVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_TEMP_TIGHTEN)) {
			// 仮締
			prepareVo();
			tmpTighten();
		} else if (mospParams.getCommand().equals(CMD_DECIDE)) {
			// 確定
			prepareVo();
			decide();
		} else if (mospParams.getCommand().equals(CMD_REMOVE)) {
			// 解除
			prepareVo();
			remove();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_TOTAL_MONTH)) {
			// 集計月決定コマンド
			prepareVo();
			setActivationMonth();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(TotalTimeCutoffListCutoffCodeComparator.class.getName());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void search() throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 検索クラス取得
		TotalTimeTransactionSearchBeanInterface search = timeReference().totalTimeTransactionSearch();
		// VOの値を検索クラスへ設定
		search.setRequestYear(getInt(vo.getPltEditRequestYear()));
		search.setRequestMonth(getInt(vo.getPltEditRequestMonth()));
		search.setCutoffDate(vo.getPltEditCutoffDate());
		search.setCutoffCode(vo.getTxtEditCutoffCode());
		search.setCutoffName(vo.getTxtEditCutoffName());
		search.setCutoffState(vo.getPltEditCutoffState());
		List<TotalTimeCutoffListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(TotalTimeCutoffListCutoffCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// 検索結果無しメッセージ設定
			addNoSearchResultMessage();
		}
		// 検索した時点の年月を保存する。
		vo.setTotalTimeRequestYear(vo.getPltEditRequestYear());
		vo.setTotalTimeRequestMonth(vo.getPltEditRequestMonth());
	}
	
	/**
	 * 仮締処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void tmpTighten() throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		String cutoffCode = getTransferredCode();
		// VOから年月を取得
		int calculationYear = Integer.parseInt(vo.getPltEditRequestYear());
		int calculationMonth = Integer.parseInt(vo.getPltEditRequestMonth());
		// 登録クラス取得
		TotalTimeTransactionRegistBeanInterface regist = time().totalTimeTransactionRegist();
		TotalTimeEmployeeTransactionRegistBeanInterface employeeRegist = time().totalTimeEmployeeTransactionRegist();
		// DTOの準備
		TotalTimeDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 仮締をセット
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		// 集計クラス取得
		TotalTimeCalcBeanInterface calc = time().totalTimeCalc();
		// 集計クラスに計算情報を設定
		calc.setCalculationInfo(calculationYear, calculationMonth, cutoffCode);
		// 処理結果確認(集計クラス計算情報設定に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 締日ユーティリティを取得
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = cutoffUtil.getCutoffPersonalIdSet(cutoffCode, calculationYear, calculationMonth);
		if (personalIdSet.isEmpty()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			PlatformMessageUtility.addErrorEmployeeNotExist(mospParams);
			return;
		}
		// エラーリスト準備
		List<CutoffErrorListDtoInterface> errorList = new ArrayList<CutoffErrorListDtoInterface>();
		int employeeCount = 0;
		// 仮締対象個人IDリスト準備
		List<String> registerdPersonalId = new ArrayList<String>();
		// 締日コード適用個人ID毎に処理
		for (String personalId : personalIdSet) {
			// 勤怠集計処理
			List<CutoffErrorListDtoInterface> list = calc.calc(personalId);
			// 処理結果確認
			if (mospParams.hasErrorMessage()) {
				// 計算失敗メッセージ設定
				addCalculateFailedMessage();
				return;
			}
			employeeCount++;
			if (!list.isEmpty()) {
				errorList.addAll(list);
				continue;
			}
			registerdPersonalId.add(personalId);
		}
		// 仮締対象社員につき仮締
		employeeRegist.draft(registerdPersonalId, Integer.parseInt(vo.getTotalTimeRequestYear()),
				Integer.parseInt(vo.getTotalTimeRequestMonth()), cutoffCode);
		// 仮締対象社員全員を仮締したか確認
		if (personalIdSet.size() == employeeCount) {
			// 仮締処理
			regist.draft(dto);
		}
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// エラーリストがあるか取得する
		if (!errorList.isEmpty()) {
			// 仮締処理からlistが帰ってきたら集計時エラー内容参照画面へ遷移する
			mospParams.addGeneralParam(TimeConst.PRM_TOTALTIME_ERROR, errorList);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_GENERIC_CODE, cutoffCode);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_YEAR, calculationYear);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_MONTH, calculationMonth);
			mospParams.setNextCommand(CutoffErrorListAction.CMD_SHOW);
			return;
		}
		// コミット
		commit();
		// 仮締成功メッセージ設定
		addTighteningMessage();
		// 検索
		search();
	}
	
	/**
	 * 確定処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void decide() throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 登録クラス取得
		TotalTimeTransactionRegistBeanInterface regist = time().totalTimeTransactionRegist();
		// 締日コード及び対象年月を取得
		String cutoffCode = getTransferredCode();
		int calculationYear = Integer.parseInt(vo.getPltEditRequestYear());
		int calculationMonth = Integer.parseInt(vo.getPltEditRequestMonth());
		// 締日ユーティリティを取得
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		// 締期間の基準日取得
		Date cutoffTermTargetDate = cutoffUtil.getCutoffTermTargetDate(cutoffCode, calculationYear, calculationMonth);
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = cutoffUtil.getCutoffPersonalIdSet(cutoffCode, calculationYear, calculationMonth);
		for (String personalId : personalIdSet) {
			Integer state = timeReference().totalTimeEmployeeTransaction().getCutoffState(personalId, calculationYear,
					calculationMonth);
			if (state == null || state.intValue() != TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
				// 仮締でない場合
				String employeeCode = reference().human().getEmployeeCode(personalId, cutoffTermTargetDate);
				// 登録失敗メッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_TIGHTENING, employeeCode);
				continue;
			}
		}
		if (mospParams.hasErrorMessage()) {
			addInsertFailedMessage();
			return;
		}
		// DTOの準備
		TotalTimeDtoInterface dto = timeReference().totalTimeTransaction().findForKey(calculationYear,
				calculationMonth, cutoffCode);
		// DTOに値を設定
		setDtoFields(dto);
		// 確定をセット
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TIGHTENED);
		// 確定処理
		regist.regist(dto);
		// 確定結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 有給休暇更新
//		updatePaidHoliday(personalIdSet);
		// ストック休暇付与
		grantStockHoliday(personalIdSet);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 確定成功メッセージ設定
		addDecideMessage();
		// 検索
		search();
	}
	
	/**
	 * 解除処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void remove() throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 登録クラス取得
		TotalTimeTransactionRegistBeanInterface regist = time().totalTimeTransactionRegist();
		TotalTimeEmployeeTransactionRegistBeanInterface employeeRegist = time().totalTimeEmployeeTransactionRegist();
		TotalTimeEmployeeTransactionReferenceBeanInterface employeeTransactionReference = timeReference()
			.totalTimeEmployeeTransaction();
		String cutoffCode = getTransferredCode();
		int calculationYear = Integer.parseInt(vo.getPltEditRequestYear());
		int calculationMonth = Integer.parseInt(vo.getPltEditRequestMonth());
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = timeReference().cutoffUtil().getCutoffPersonalIdSet(cutoffCode, calculationYear,
				calculationMonth);
		if (personalIdSet.isEmpty()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			PlatformMessageUtility.addErrorEmployeeNotExist(mospParams);
			return;
		}
		TotalTimeDtoInterface totalTimeDto = timeReference().totalTimeTransaction().findForKey(calculationYear,
				calculationMonth, cutoffCode);
		if (totalTimeDto.getCutoffState() == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
			// 仮締解除(仮締→未締)
			List<String> removePersonalIdList = new ArrayList<String>();
			for (String personalId : personalIdSet) {
				Integer state = employeeTransactionReference.getCutoffState(personalId, calculationYear,
						calculationMonth);
				if (state == null || state.intValue() == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
					// 仮締でない場合
					continue;
				}
				removePersonalIdList.add(personalId);
			}
			// 特別休暇データを削除する
			time().totalLeaveRegist().delete(removePersonalIdList, calculationYear, calculationMonth);
			// その他休暇データを削除する
			time().totalOtherVacationRegist().delete(removePersonalIdList, calculationYear, calculationMonth);
			// 欠勤データを削除する
			time().totalAbsenceRegist().delete(removePersonalIdList, calculationYear, calculationMonth);
			// 勤怠集計データを削除する
			time().totalTimeRegist().delete(removePersonalIdList, calculationYear, calculationMonth);
			// 勤怠集計修正情報を削除する
			time().totalTimeCorrectionRegist().delete(removePersonalIdList, calculationYear, calculationMonth);
			// 仮締解除
			employeeRegist.draftRelease(removePersonalIdList, calculationYear, calculationMonth, cutoffCode);
			// DTOに値を設定
			setDtoFields(totalTimeDto);
			// 未締をセット
			totalTimeDto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT);
			// 仮締解除
			regist.draftRelease(totalTimeDto);
		} else if (totalTimeDto.getCutoffState() == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
			// 確定解除(確定→仮締)
			Set<String> removePersonalIdSet = new HashSet<String>();
			for (String personalId : personalIdSet) {
				Integer state = employeeTransactionReference.getCutoffState(personalId, calculationYear,
						calculationMonth);
				if (state == null || state.intValue() == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
					// 仮締でない場合
					continue;
				}
				removePersonalIdSet.add(personalId);
			}
			// DTOに値を設定
			setDtoFields(totalTimeDto);
			// 仮締をセット
			totalTimeDto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
			// 確定解除
			regist.registRelease(totalTimeDto);
//			employeeRegist.registRelease(personalIdList, calculationYear, calculationMonth, cutoffCode);
			// 有給休暇削除
//			deletePaidHoliday(removePersonalIdSet);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// 解除結果確認
		if (mospParams.hasErrorMessage()) {
			// 仮締解除失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 仮締解除成功メッセージ設定
		addReleaseMessage();
		// 検索
		search();
	}
	
	/**
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 集計月設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationMonth() throws MospException {
		// VO取得
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 検索結果消去
		// 空のリスト準備
		List<BaseDtoInterface> list = new ArrayList<BaseDtoInterface>();
		// VOにセット
		vo.setList(list);
		// データ配列初期化
		setVoList(list);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 年月日指定時(システム日付)の基準月を取得
		Date targetYearMonth = MonthUtility.getTargetYearMonth(date, mospParams);
		// 検索項目設定
		vo.setAryPltEditRequestYear(getYearArray(DateUtility.getYear(targetYearMonth)));
		vo.setPltEditRequestYear(DateUtility.getStringYear(targetYearMonth));
		vo.setAryPltEditRequestMonth(getMonthArray());
		vo.setPltEditRequestMonth(DateUtility.getStringMonthM(targetYearMonth));
		vo.setTxtEditCutoffCode("");
		vo.setTxtEditCutoffName("");
		vo.setPltEditCutoffDate("");
		vo.setTxtEditCutoffCode("");
		vo.setPltEditCutoffState("");
		// 集計対象年月ラベルの初期化
		vo.setTxtLblRequestYear("");
		vo.setTxtLblRequestMonth("");
		unwellsetState = false;
		draftState = false;
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblCutoffCode = new String[list.size()];
		String[] aryLblCutoffName = new String[list.size()];
		String[] aryLblCutoffDate = new String[list.size()];
		String[] aryLblCutoffState = new String[list.size()];
		int[] aryCutoffState = new int[list.size()];
		unwellsetState = false;
		draftState = false;
		// 締日情報取得(プルダウンの中身)
		String[][] cutoffDateArray = mospParams.getProperties().getCodeArray(TimeConst.CODE_KEY_CUTOFF_DATE, false);
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			TotalTimeCutoffListDtoInterface dto = (TotalTimeCutoffListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmmCutoffId());
			aryLblCutoffCode[i] = dto.getCutoffCode();
			aryLblCutoffName[i] = dto.getCutoffName();
			aryLblCutoffDate[i] = MospUtility.getCodeName(String.valueOf(dto.getCutoffDate()), cutoffDateArray);
			aryLblCutoffState[i] = getStringState(Integer.valueOf(dto.getCutoffState()));
			aryCutoffState[i] = dto.getCutoffState();
		}
		// データをVOに設定
		vo.setAryCkbTotalTimeListId(aryCkbRecordId);
		vo.setAryLblCutoffCode(aryLblCutoffCode);
		vo.setAryLblCutoffName(aryLblCutoffName);
		vo.setAryLblCutoffDate(aryLblCutoffDate);
		vo.setAryLblCutoffState(aryLblCutoffState);
		vo.setAryCutoffState(aryCutoffState);
	}
	
	/**
	 * 締め状態をINT型からString型の文字列に変換する。<br>
	 * @param state INT型の締め状態
	 * @return 文字列に変換された締め状態
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected String getStringState(int state) throws MospException {
		if (state == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
			// 未締
			unwellsetState = true;
			return mospParams.getName("Ram") + mospParams.getName("Cutoff");
		} else if (state == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
			// 仮締
			draftState = true;
			return mospParams.getName("Provisional") + mospParams.getName("Cutoff");
		} else {
			// 確定
			return mospParams.getName("Definition");
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(TotalTimeDtoInterface dto) throws MospException {
		// VO取得
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 参照クラス取得
		CutoffReferenceBeanInterface cutoff = timeReference().cutoff();
		int year = Integer.parseInt(vo.getTotalTimeRequestYear());
		int month = Integer.parseInt(vo.getTotalTimeRequestMonth());
		String cutoffCode = getTransferredCode();
		// 年月指定時の基準日取得
		Date date = MonthUtility.getYearMonthTargetDate(year, month, mospParams);
		CutoffDtoInterface cutoffDto = cutoff.getCutoffInfo(cutoffCode, date);
		cutoff.chkExistCutoff(cutoffDto, date);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 集計日取得
		Date calculationDate = TimeUtility.getCutoffCalculationDate(cutoffDto.getCutoffDate(), year, month);
		// VOの値をDTOに設定
		dto.setCalculationYear(year);
		dto.setCalculationMonth(month);
		dto.setCutoffCode(cutoffCode);
		dto.setCalculationDate(calculationDate);
		// 未締に設定
		dto.setCutoffState(0);
	}
	
	/**
	 * 有給休暇データを更新する。<br>
	 * @param idSet 個人IDセット
	 * @throws MospException 例外発生時
	 */
	protected void updatePaidHoliday(Set<String> idSet) throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		int year = Integer.parseInt(vo.getPltEditRequestYear());
		int month = Integer.parseInt(vo.getPltEditRequestMonth());
		// 呼出Bean取得
		PaidHolidayDataRegistBeanInterface regist = time().paidHolidayDataRegist();
		PaidHolidayInfoReferenceBeanInterface infoReference = timeReference().paidHolidayInfo();
		StockHolidayDataRegistBeanInterface stockRegist = time().stockHolidayDataRegist();
		StockHolidayInfoReferenceBeanInterface stockInfoReference = timeReference().stockHolidayInfo();
		CutoffUtilBeanInterface cutoffUtilBean = timeReference().cutoffUtil();
		
		// 締日コード取得
		String cutoffCode = getTransferredCode();
		// 締日コード確認
		if (cutoffCode == null || cutoffCode.isEmpty()) {
			return;
		}
		
		// 締期間最終日取得
		Date cutoffDate = cutoffUtilBean.getCutoffLastDate(cutoffCode, year, month);
		if (cutoffDate == null) {
			return;
		}
		// 締期間 + 1日
		Date nextDate = DateUtility.addDay(cutoffDate, 1);
		Date nextMonth = DateUtility.addMonth(cutoffDate, 1);
		
		for (String personalId : idSet) {
			// ストック休暇
			// 締期間最終日
			stockRegist.delete(personalId, cutoffDate);
			List<StockHolidayDataDtoInterface> stockHolidayDataList = stockInfoReference.getStockHolidayCalcInfo(
					personalId, cutoffDate);
			for (StockHolidayDataDtoInterface dto : stockHolidayDataList) {
				stockRegist.insert(dto);
			}
			// 締期間最終日 + 1
			stockRegist.delete(personalId, nextDate);
			List<StockHolidayDataDtoInterface> nextStockHolidayDataList = stockInfoReference
				.getStockHolidayNextMonthInfo(personalId, nextDate, stockHolidayDataList);
			for (StockHolidayDataDtoInterface dto : nextStockHolidayDataList) {
				stockRegist.insert(dto);
			}
			// 有給休暇
			// 締期間最終日
			regist.delete(personalId, cutoffDate);
			List<PaidHolidayDataDtoInterface> paidHolidayDataList = infoReference.getPaidHolidayCalcInfo(personalId,
					cutoffDate);
			for (PaidHolidayDataDtoInterface dto : paidHolidayDataList) {
				regist.insert(dto);
			}
			List<PaidHolidayDataDtoInterface> addList = new ArrayList<PaidHolidayDataDtoInterface>();
			List<PaidHolidayDataDtoInterface> currentNextPaidHolidayDataList = timeReference().paidHolidayData()
				.getPaidHolidayDataInfoList(personalId, nextDate, nextMonth);
			for (PaidHolidayDataDtoInterface currentDto : currentNextPaidHolidayDataList) {
				boolean exists = false;
				for (PaidHolidayDataDtoInterface dto : paidHolidayDataList) {
					if (currentDto.getAcquisitionDate().equals(dto.getAcquisitionDate())) {
						// 取得日が同じである場合
						exists = true;
						break;
					}
				}
				if (!exists) {
					addList.add(currentDto);
				}
			}
			// リストに追加
			paidHolidayDataList.addAll(addList);
			
			for (PaidHolidayDataDtoInterface dto : currentNextPaidHolidayDataList) {
				// 削除
				regist.delete(dto);
			}
			List<PaidHolidayDataDtoInterface> nextPaidHolidayDataList = infoReference.getPaidHolidayNextMonthInfo(
					personalId, cutoffDate, year, month, paidHolidayDataList);
			for (PaidHolidayDataDtoInterface dto : nextPaidHolidayDataList) {
				regist.insert(dto);
			}
			// ストック休暇新規データ
			Double expirationDay = infoReference.getExpirationDay(paidHolidayDataList, cutoffDate);
			if (expirationDay != null) {
				StockHolidayDataDtoInterface stockHolidayDataDto = stockInfoReference.getNewStockHolidayInfo(
						personalId, nextDate, expirationDay.doubleValue());
				if (stockHolidayDataDto != null) {
					stockRegist.insert(stockHolidayDataDto);
				}
			}
			// 有給休暇新規データ
			PaidHolidayDataDtoInterface paidHolidayDataDto = infoReference.getNewPaidHolidayInfo(personalId,
					cutoffDate, year, month);
			if (paidHolidayDataDto != null) {
				regist.insert(paidHolidayDataDto);
			}
		}
	}
	
	/**
	 * 有給休暇データを削除する。<br>
	 * @param idSet 個人IDセット
	 * @throws MospException 例外発生時
	 */
	protected void deletePaidHoliday(Set<String> idSet) throws MospException {
		// VO準備
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		// 呼出Bean取得
		PaidHolidayDataRegistBeanInterface regist = time().paidHolidayDataRegist();
		PaidHolidayInfoReferenceBeanInterface infoReference = timeReference().paidHolidayInfo();
		StockHolidayDataRegistBeanInterface stockRegist = time().stockHolidayDataRegist();
//		StockHolidayInfoReferenceBeanInterface stockInfoReference = timeReference().stockHolidayInfo();
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		int year = Integer.parseInt(vo.getPltEditRequestYear());
		int month = Integer.parseInt(vo.getPltEditRequestMonth());
		// 締日コード取得
		String cutoffCode = getTransferredCode();
		// 締日コード確認
		if (cutoffCode == null || cutoffCode.isEmpty()) {
			return;
		}
		// 締期間最終日取得
		Date cutoffDate = cutoffUtil.getCutoffLastDate(cutoffCode, year, month);
		if (cutoffDate == null) {
			return;
		}
		int previousMonthYear = year;
		int previousMonthMonth = month - 1;
		if (previousMonthMonth == 0) {
			previousMonthYear--;
			previousMonthMonth = 12;
		}
		// 締期間最終日 + 1日
		Date nextDate = DateUtility.addDay(cutoffDate, 1);
		Date nextMonth = DateUtility.addMonth(cutoffDate, 1);
		for (String personalId : idSet) {
			// ストック休暇
			// 締期間最終日
			stockRegist.delete(personalId, cutoffDate);
//			List<StockHolidayDataDtoInterface> stockHolidayDataList = stockInfoReference.getStockHolidayCalcInfo(
//					personalId, cutoffDate);
//			for (StockHolidayDataDtoInterface dto : stockHolidayDataList) {
//				stockRegist.delete(dto.getPersonalId(), dto.getActivateDate());
//			}
			// 締期間最終日 + 1
			stockRegist.delete(personalId, nextDate);
//			List<StockHolidayDataDtoInterface> nextStockHolidayDataList = stockInfoReference
//				.getStockHolidayNextMonthInfo(personalId, nextDate, stockHolidayDataList);
//			for (StockHolidayDataDtoInterface dto : nextStockHolidayDataList) {
//				stockRegist.delete(dto.getPersonalId(), dto.getActivateDate());
//			}
			// 有給休暇
			List<PaidHolidayDataDtoInterface> paidHolidayDataList = infoReference.getPaidHolidayCalcInfo(personalId,
					cutoffDate);
//			for (PaidHolidayDataDtoInterface dto : paidHolidayDataList) {
//				regist.delete(dto.getPersonalId(), dto.getActivateDate());
//			}
			regist.delete(personalId, cutoffDate);
//			List<PaidHolidayDataDtoInterface> addList = new ArrayList<PaidHolidayDataDtoInterface>();
			List<PaidHolidayDataDtoInterface> currentNextPaidHolidayDataList = timeReference().paidHolidayData()
				.getPaidHolidayDataInfoList(personalId, nextDate, nextMonth);
			for (PaidHolidayDataDtoInterface dto : currentNextPaidHolidayDataList) {
//				boolean exists = false;
				for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidHolidayDataList) {
					if (dto.getAcquisitionDate().equals(paidHolidayDataDto.getAcquisitionDate())) {
						// 取得日が同じである場合
						regist.delete(dto);
//						exists = true;
//						break;
					}
				}
//				if (!exists) {
//					addList.add(dto);
//				}
			}
//			// リストに追加
//			paidHolidayDataList.addAll(addList);
//			for (PaidHolidayDataDtoInterface dto : currentNextPaidHolidayDataList) {
//				// 削除
//				regist.delete(dto);
//			}
//			List<PaidHolidayDataDtoInterface> nextPaidHolidayDataList = infoReference.getPaidHolidayNextMonthInfo(
//					personalId, cutoffDate, year, month, paidHolidayDataList);
//			for (PaidHolidayDataDtoInterface dto : nextPaidHolidayDataList) {
//				regist.delete(dto.getPersonalId(), dto.getActivateDate());
//			}
			// ストック休暇新規データ
//			StockHolidayDataDtoInterface stockHolidayDataDto = stockInfoReference.getNewStockHolidayInfo(personalId,
//					nextDate, infoReference.getExpirationDay(paidHolidayDataList, cutoffDate));
//			if (stockHolidayDataDto != null) {
//				stockRegist.delete(stockHolidayDataDto.getPersonalId(), stockHolidayDataDto.getActivateDate());
//			}
			// 有給休暇新規データ
			PaidHolidayDataDtoInterface paidHolidayDataDto = infoReference.getNewPaidHolidayInfo(personalId,
					cutoffDate, year, month);
			if (paidHolidayDataDto != null) {
				regist.delete(paidHolidayDataDto.getPersonalId(), paidHolidayDataDto.getActivateDate());
			}
			PaidHolidayDataDtoInterface previousMonthPaidHolidayDataDto = infoReference.getNewPaidHolidayInfo(
					personalId, previousMonthYear, previousMonthMonth);
			if (previousMonthPaidHolidayDataDto == null) {
				continue;
			}
			regist.insert(previousMonthPaidHolidayDataDto);
		}
	}
	
	/**
	 * ストック休暇を付与する。
	 * @param personalIdSet 個人IDセット
	 * @throws MospException 例外発生時
	 */
	protected void grantStockHoliday(Set<String> personalIdSet) throws MospException {
		// VO取得
		TotalTimeVo vo = (TotalTimeVo)mospParams.getVo();
		StockHolidayDataGrantBeanInterface stockHolidayDataGrant = time().stockHolidayDataGrant();
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		int year = Integer.parseInt(vo.getPltEditRequestYear());
		int month = Integer.parseInt(vo.getPltEditRequestMonth());
		// 締日コード取得
		String cutoffCode = getTransferredCode();
		// 締期間最終日取得
		Date cutoffDate = cutoffUtil.getCutoffLastDate(cutoffCode, year, month);
		// 対象日取得
		Date targetDate = DateUtility.addDay(cutoffDate, 1);
		for (String personalId : personalIdSet) {
			// 付与(既にデータがある場合は更新しない)
			stockHolidayDataGrant.grant(personalId, targetDate, false);
		}
	}
	
}