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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayInfoReferenceBeanInterface;
import jp.mosp.time.comparator.settings.HolidayRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.input.vo.HolidayRequestVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請情報の確認と編集を行う。<br>
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
 * {@link #CMD_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_BATCH_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SET_VIEW_PERIOD}
 * </li><li>
 * {@link #CMD_SET_TRANSFER_HOLIDAY}
 * </li><li>
 * {@link #CMD_SELECT_ACTIVATION_DATE}
 * </li></ul>
 */
public class HolidayRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの休暇申請画面を表示する。<br>
	 */
	public static final String	CMD_SHOW					= "TM1500";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 休暇申請画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW				= "TM1501";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に休暇申請情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH					= "TM1502";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな下書情報作成や申請を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW					= "TM1503";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休暇情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String	CMD_DRAFT					= "TM1504";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休暇情報テーブルに登録し、休暇申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 休暇の申請可能時間を超過している、申請年月日で申請不可な日付が選択されている、<br>
	 * 申請時間が0時間0分のまま、休暇理由の項目が入力されていない、<br>
	 * といった状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String	CMD_APPLI					= "TM1505";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER				= "TM1506";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。<br>
	 * 取下後、対象の休暇申請情報は未申請状態へ戻る。<br>
	 */
	public static final String	CMD_WITHDRAWN				= "TM1507";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT					= "TM1508";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE					= "TM1509";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String	CMD_BATCH_WITHDRAWN			= "TM1536";
	
	/**
	 * 休暇年月日決定コマンド。<br>
	 * <br>
	 * 休暇年月日入力欄に入力されている日付を元にログイン中のユーザが取得可能な休暇種別を検索、各プルダウンに表示させる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE		= "TM1590";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE				= "TM1591";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 申請テーブルヘッダに新規登録モード切替リンクを表示させる。
	 */
	public static final String	CMD_EDIT_MODE				= "TM1592";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String	CMD_BATCH_UPDATE			= "TM1595";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力した締期間で検索を行い、残日数のある休暇区分と休暇種別を取得し、プルダウンに表示する。<br>
	 */
	public static final String	CMD_SET_VIEW_PERIOD			= "TM1597";
	
	/**
	 * 休暇種別決定コマンド。<br>
	 * <br>
	 * 休暇情報の連続取得判定を行うために休暇種別プルダウン切替時にその休暇の連続取得区分が"必須/警告/不要"の内どれであるかを判断する。<br>
	 */
	public static final String	CMD_SET_TRANSFER_HOLIDAY	= "TM1598";
	
	/**
	 * 休暇年月日決定コマンド。<br>
	 * <br>
	 * 休暇年月日入力欄に入力されている日付を元にログイン中のユーザが取得可能な休暇種別を検索、各プルダウンに表示させる。<br>
	 */
	public static final String	CMD_SELECT_ACTIVATION_DATE	= "TM1599";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public HolidayRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HolidayRequestVo();
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
		} else if (mospParams.getCommand().equals(CMD_WITHDRAWN)) {
			// 取下
			prepareVo();
			withdrawn();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_BATCH_WITHDRAWN)) {
			// 一括取下
			prepareVo();
			batchWithdrawn();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 休暇年月日
			prepareVo();
			setEditActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SET_VIEW_PERIOD)) {
			// 表示期間
			prepareVo();
			setSearchActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SET_TRANSFER_HOLIDAY)) {
			//
			prepareVo();
			setHolidayContinue();
		} else if (mospParams.getCommand().equals(CMD_SELECT_ACTIVATION_DATE)) {
			// 休暇年月日
			prepareVo(false, false);
			selectActivationDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// 個人ID取得(ログインユーザ情報から)
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// 検索
		search();
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 個人ID確認
		if (personalId == null || personalId.isEmpty()) {
			// ログインユーザの個人IDを取得
			personalId = mospParams.getUser().getPersonalId();
		}
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// 検索
		search();
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 検索クラス取得
		HolidayRequestSearchBeanInterface search = timeReference().holidayRequestSearch();
		// VOの値を検索クラスへ設定
		search.setPersonalId(vo.getPersonalId());
		String holidayType1 = vo.getPltSearchHolidayType();
		search.setHolidayType1(holidayType1);
		search.setHolidayType2("");
		search.setHolidayLength("");
		if (holidayType1 != null && !holidayType1.isEmpty()) {
			// 休暇種別の種類を判断
			int holidayType = Integer.parseInt(holidayType1);
			String holidayType2 = "";
			String holidayLength = "";
			if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == holidayType) {
				// 有給休暇
				holidayType2 = vo.getPltSearchStatusWithPay();
				holidayLength = vo.getPltSearchHolidayRange1();
			} else if (TimeConst.CODE_HOLIDAYTYPE_SPECIAL == holidayType) {
				// 特別休暇
				holidayType2 = vo.getPltSearchStatusSpecial();
				holidayLength = vo.getPltSearchHolidayRange2();
			} else if (TimeConst.CODE_HOLIDAYTYPE_OTHER == holidayType) {
				// その他
				holidayType2 = vo.getPltSearchSpecialOther();
				holidayLength = vo.getPltSearchHolidayRange2();
			} else if (TimeConst.CODE_HOLIDAYTYPE_ABSENCE == holidayType) {
				// 欠勤
				holidayType2 = vo.getPltSearchSpecialAbsence();
				holidayLength = vo.getPltSearchHolidayRange2();
			}
			search.setHolidayType2(holidayType2);
			search.setHolidayLength(holidayLength);
		}
		// 変数準備
		int year = Integer.parseInt(vo.getPltSearchYear());
		int startMonth = getInt(TimeConst.CODE_DISPLAY_JANUARY);
		int endMonth = getInt(TimeConst.CODE_DISPLAY_DECEMBER);
		if (!vo.getPltSearchMonth().isEmpty()) {
			// 月検索ならば
			startMonth = getInt(vo.getPltSearchMonth());
			endMonth = startMonth;
		}
		// 締日ユーティリティー取得
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		// 締日情報取得
		CutoffDtoInterface startMonthCutoffDto = cutoffUtil
			.getCutoffForPersonalId(vo.getPersonalId(), year, startMonth);
		if (mospParams.hasErrorMessage()) {
			// 検索結果無しメッセージ設定
			addNoSearchResultMessage();
			return;
		}
		CutoffDtoInterface endMonthCutoffDto = cutoffUtil.getCutoffForPersonalId(vo.getPersonalId(), year, endMonth);
		String startMonthCutoffCode = startMonthCutoffDto == null ? null : startMonthCutoffDto.getCutoffCode();
		String endMonthCutoffCode = endMonthCutoffDto == null ? null : endMonthCutoffDto.getCutoffCode();
		// 締期間の開始及び最終日
		Date firstDate = null;
		if (startMonthCutoffCode != null) {
			firstDate = cutoffUtil.getCutoffFirstDate(startMonthCutoffCode, year, startMonth);
		}
		// 対象年月及び締日から締期間最終日を取得
		Date lastDate = null;
		if (endMonthCutoffCode != null) {
			lastDate = cutoffUtil.getCutoffLastDate(endMonthCutoffCode, year, endMonth);
		}
		// 締期間を検索範囲に設定
		search.setRequestStartDate(firstDate);
		search.setRequestEndDate(lastDate);
		search.setWorkflowStatus(vo.getPltSearchState());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(HolidayRequestRequestDateComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// 検索結果無しメッセージ設定
			addNoSearchResultMessage();
		}
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		final String nameNoLimit = mospParams.getName("NoLimit");
		// 有給休暇情報取得
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		// 休暇情報取得
		HolidayInfoReferenceBeanInterface holidayInfo = timeReference().holidayInfo();
		// ストック休暇情報取得
		StockHolidayInfoReferenceBeanInterface stockHolidayInfo = timeReference().stockHolidayInfo();
		// 特別/その他休暇
		List<HolidayDataDtoInterface> specialList = holidayInfo.getHolidayPossibleRequestList(personalId, date,
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		List<HolidayDataDtoInterface> otherList = holidayInfo.getHolidayPossibleRequestList(personalId, date,
				TimeConst.CODE_HOLIDAYTYPE_OTHER);
		String[] aryGivingDate = new String[specialList.size() + otherList.size()];
		String[] arySpecialHolidayType = new String[specialList.size() + otherList.size()];
		String[] arySpecialHolidayName = new String[specialList.size() + otherList.size()];
		String[] aryRemainder = new String[specialList.size() + otherList.size()];
		String[] aryLimit = new String[specialList.size() + otherList.size()];
		int cnt = 0;
		// 特別休暇情報リスト毎に処理
		for (cnt = 0; cnt < specialList.size(); cnt++) {
			// リストから情報を取得
			HolidayDataDtoInterface dto = specialList.get(cnt);
			aryGivingDate[cnt] = DateUtility.getStringDateAndDay(dto.getActivateDate());
			arySpecialHolidayType[cnt] = mospParams.getName("Specially", "Vacation");
			HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(dto.getHolidayCode(),
					dto.getActivateDate(), dto.getHolidayType());
			if (holidayDto != null) {
				arySpecialHolidayName[cnt] = holidayDto.getHolidayName();
			} else {
				arySpecialHolidayName[cnt] = dto.getHolidayCode();
			}
			if (TimeUtility.getUnlimitedDate().compareTo(dto.getHolidayLimitDate()) == 0) {
				aryRemainder[cnt] = nameNoLimit;
				aryLimit[cnt] = nameNoLimit;
			} else {
				aryRemainder[cnt] = String.valueOf(dto.getGivingDay() - dto.getCancelDay()) + mospParams.getName("Day");
				aryLimit[cnt] = DateUtility.getStringDateAndDay(dto.getHolidayLimitDate());
			}
		}
		// その他休暇情報リスト毎に処理
		for (int i = 0; i < otherList.size(); i++) {
			// リストから情報を取得
			HolidayDataDtoInterface dto = otherList.get(i);
			aryGivingDate[cnt] = DateUtility.getStringDateAndDay(dto.getActivateDate());
			arySpecialHolidayType[cnt] = mospParams.getName("Others", "Vacation");
			HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(dto.getHolidayCode(),
					dto.getActivateDate(), dto.getHolidayType());
			if (holidayDto != null) {
				arySpecialHolidayName[cnt] = holidayDto.getHolidayName();
			} else {
				arySpecialHolidayName[cnt] = dto.getHolidayCode();
			}
			if (TimeUtility.getUnlimitedDate().compareTo(dto.getHolidayLimitDate()) == 0) {
				aryRemainder[cnt] = nameNoLimit;
				aryLimit[cnt] = nameNoLimit;
			} else {
				aryRemainder[cnt] = String.valueOf(dto.getGivingDay() - dto.getCancelDay()) + mospParams.getName("Day");
				aryLimit[cnt] = DateUtility.getStringDateAndDay(dto.getHolidayLimitDate());
			}
			cnt++;
		}
		vo.setRecordId(0);
		vo.setAryLblGivingDate(aryGivingDate);
		vo.setAryLblSpecialHolidayType(arySpecialHolidayType);
		vo.setAryLblSpecialHolidayName(arySpecialHolidayName);
		vo.setAryLblRemainder(aryRemainder);
		vo.setAryLblLimit(aryLimit);
		// 対象日がある場合
		if (getTargetDate() == null) {
			vo.setPltEditStartYear(DateUtility.getStringYear(date));
			vo.setPltEditStartMonth(DateUtility.getStringMonthM(date));
			vo.setPltEditStartDay(DateUtility.getStringDayD(date));
			vo.setPltEditEndYear(DateUtility.getStringYear(date));
			vo.setPltEditEndMonth(DateUtility.getStringMonthM(date));
			vo.setPltEditEndDay(DateUtility.getStringDayD(date));
		} else {
			vo.setPltEditStartYear(DateUtility.getStringYear(getTargetDate()));
			vo.setPltEditStartMonth(DateUtility.getStringMonthM(getTargetDate()));
			vo.setPltEditStartDay(DateUtility.getStringDayD(getTargetDate()));
			vo.setPltEditEndYear(DateUtility.getStringYear(getTargetDate()));
			vo.setPltEditEndMonth(DateUtility.getStringMonthM(getTargetDate()));
			vo.setPltEditEndDay(DateUtility.getStringDayD(getTargetDate()));
		}
		// 時間単位限度設定
		int[] timeUnitLimit = paidHolidayInfo.getHolidayTimeUnitLimit(personalId, date, false, null);
		vo.setLblHolidayTimeUnitLimit(getNumberOfDayAndHour(timeUnitLimit[0], timeUnitLimit[1]));
		// VOを初期化
		initVo();
		// 時間単位設定
		setPaidLeaveByHour();
		// ストック休暇
		double stock = 0;
		Double remainDay = stockHolidayInfo.getRemainDay(personalId, date);
		if (remainDay != null) {
			stock = remainDay.doubleValue();
		}
		vo.setLblPaidHolidayStock(String.valueOf(stock));
		// 有給休暇情報欄表示
		Map<String, Object> map = paidHolidayInfo.getPaidHolidayPossibleRequest(personalId, date);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		double formerGrantDay = 0;
		if (map.get(TimeConst.CODE_FORMER_GRANT_DAY) != null) {
			formerGrantDay = ((Double)map.get(TimeConst.CODE_FORMER_GRANT_DAY)).doubleValue();
		}
		int formerGrantHour = 0;
		if (map.get(TimeConst.CODE_FORMER_GRANT_HOUR) != null) {
			formerGrantHour = ((Integer)map.get(TimeConst.CODE_FORMER_GRANT_HOUR)).intValue();
		}
		double formerYearDay = map.get(TimeConst.CODE_FORMER_YEAR_DAY) == null ? 0 : ((Double)map
			.get(TimeConst.CODE_FORMER_YEAR_DAY)).doubleValue();
		int formerYearTime = map.get(TimeConst.CODE_FORMER_YEAR_TIME) == null ? 0 : ((Integer)map
			.get(TimeConst.CODE_FORMER_YEAR_TIME)).intValue();
		double currentGrantDay = 0;
		if (map.get(TimeConst.CODE_CURRENT_GRANT_DAY) != null) {
			currentGrantDay = ((Double)map.get(TimeConst.CODE_CURRENT_GRANT_DAY)).doubleValue();
		}
		int currentGrantHour = 0;
		if (map.get(TimeConst.CODE_CURRENT_GRANT_HOUR) != null) {
			currentGrantHour = ((Integer)map.get(TimeConst.CODE_CURRENT_GRANT_HOUR)).intValue();
		}
		double currentYearDay = map.get(TimeConst.CODE_CURRENT_YEAR_DAY) == null ? 0 : ((Double)map
			.get(TimeConst.CODE_CURRENT_YEAR_DAY)).doubleValue();
		int currentYearTime = map.get(TimeConst.CODE_CURRENT_TIME) == null ? 0 : ((Integer)map
			.get(TimeConst.CODE_CURRENT_TIME)).intValue();
		Date currentGivingDate = ((Date)map.get(TimeConst.CODE_CURRENT_GIVING_DATE));
		Date formerGivingDate = ((Date)map.get(TimeConst.CODE_FORMER_GIVING_DATE));
		Date currentLimitDate = ((Date)map.get(TimeConst.CODE_CURRENT_LIMIT_DATE));
		Date formerLimitDate = ((Date)map.get(TimeConst.CODE_FORMER_LIMIT_DATE));
		// 前年度付与日数
		vo.setLblFormerGrantDay(getNumberOfDayAndHour(formerGrantDay, formerGrantHour));
		// 前年度残日数
		vo.setLblFormerDay(getNumberOfDayAndHour(formerYearDay, formerYearTime));
		// 前年度付与日
		vo.setLblFormerGivingDay(DateUtility.getStringDateAndDay(formerGivingDate));
		// 前年度期限日
		vo.setLblFormerLimitDay(DateUtility.getStringDateAndDay(formerLimitDate));
		// 今年度付与日数
		vo.setLblCurrentGrantDay(getNumberOfDayAndHour(currentGrantDay, currentGrantHour));
		// 今年度残日数
		vo.setLblCurrentDay(getNumberOfDayAndHour(currentYearDay, currentYearTime));
		// 今年度付与日
		vo.setLblCurrentGivingDay(DateUtility.getStringDateAndDay(currentGivingDate));
		// 今年度期限日
		vo.setLblCurrentLimitDay(DateUtility.getStringDateAndDay(currentLimitDate));
		// 有給休暇付与日数
		vo.setLblTotalGrantDay(getNumberOfDayAndHour(formerGrantDay + currentGrantDay, formerGrantHour
				+ currentGrantHour));
		// 有給休暇申請可能数
		vo.setLblTotalDayAndHour(getNumberOfDayAndHour(formerYearDay + currentYearDay, formerYearTime + currentYearTime));
		// 有休申請可能日数
		vo.setLblTotalDay(Double.toString(formerYearDay + currentYearDay));
		// 有休申請可能時間
		vo.setLblTotalTime(Integer.toString(formerYearTime + currentYearTime));
		// 有給休暇情報欄表示
		Map<String, Object> nextYearMap = paidHolidayInfo.getNextGivingInfo(personalId);
		if (nextYearMap == null) {
			return;
		}
		double nextYearDay = ((Double)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_YEAR_DAY)).doubleValue();
		if (nextYearDay == -1) {
			nextYearDay = 0.0;
		}
		int nextYearTime = ((Integer)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_TIME)).intValue();
		Date nextYearGivingDate = ((Date)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_GIVING_DATE));
		Date nextYearrLimitDate = ((Date)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE));
		// 有給休暇次回付与予定日
		vo.setLblNextGivingDate(DateUtility.getStringDateAndDay(nextYearGivingDate));
		// 有給休暇次回付与予定日数
		vo.setLblNextGivingAmount(getNumberOfDayAndHour(nextYearDay, nextYearTime));
		// 有給休暇次回付与期限日
		vo.setLblNextLimitDate(DateUtility.getStringDateAndDay(nextYearrLimitDate));
		// 次回付与予定日(手動)取得及び確認
		Date nextManualGivingDate = paidHolidayInfo.getNextManualGivingDate(personalId);
		if (null == nextManualGivingDate) {
			return;
		}
		// 次回付与予定日(手動)
		vo.setLblNextManualGivingDate(DateUtility.getStringDateAndDay(nextManualGivingDate));
		// 次回付与予定日数(手動)
		vo.setLblNextManualGivingAmount(paidHolidayInfo.getNextManualGivingDaysAndHours(vo.getPersonalId()));
	}
	
	/**
	 * 有給休暇時間単位利用可否設定。
	 * @throws MospException 例外発生時
	 */
	protected void setPaidLeaveByHour() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		ApplicationReferenceBeanInterface applicationReference = timeReference().application();
		PaidHolidayReferenceBeanInterface paidHolidayReference = timeReference().paidHoliday();
		vo.setPaidLeaveByHour(false);
		Date date = getSystemDate();
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(vo.getPersonalId(), date);
		if (applicationDto == null) {
			return;
		}
		PaidHolidayDtoInterface paidHolidayDto = paidHolidayReference.getPaidHolidayInfo(
				applicationDto.getPaidHolidayCode(), date);
		if (paidHolidayDto == null) {
			return;
		}
		vo.setPaidLeaveByHour(paidHolidayDto.getTimelyPaidHolidayFlag() == 0);
	}
	
	/**
	 * 残数を取得する。<br>
	 * @param day 日数
	 * @param hour 時間数
	 * @return 残数
	 */
	protected String getNumberOfDayAndHour(int day, int hour) {
		StringBuffer sb = new StringBuffer();
		sb.append(day);
		sb.append(mospParams.getName("Day"));
		if (hour > 0) {
			// 0より大きい場合
			sb.append(hour);
			sb.append(mospParams.getName("Time"));
		}
		return sb.toString();
	}
	
	/**
	 * 残数を取得する。<br>
	 * @param day 日数
	 * @param hour 時間数
	 * @return 残数
	 */
	protected String getNumberOfDayAndHour(double day, int hour) {
		StringBuffer sb = new StringBuffer();
		sb.append(day);
		sb.append(mospParams.getName("Day"));
		if (hour > 0) {
			// 0より大きい場合
			sb.append(hour);
			sb.append(mospParams.getName("Time"));
		}
		return sb.toString();
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * @param year 年
	 * @param month 月
	 */
	protected void setSearchRequestDate(String year, String month) {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		vo.setPltSearchYear(year);
		vo.setPltSearchMonth(month);
	}
	
	/**
	 * VOの初期化を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initVo() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		vo.setPltEditHolidayType1(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY));
		vo.setPltEditStatusWithPay("");
		vo.setPltEditStatusSpecial("");
		vo.setPltEditSpecialOther("");
		vo.setPltEditSpecialAbsence("");
		vo.setPltEditHolidayRange("");
		vo.setPltEditHolidayRangePaidHoliday("");
		vo.setPltEditStartHour("0");
		vo.setPltEditStartMinute("0");
		vo.setPltEditEndTime("1");
		vo.setTxtEditRequestReason("");
		vo.setPltSearchHolidayType("");
		vo.setPltSearchHolidayType1("");
		vo.setPltSearchHolidayType2("");
		vo.setPltSearchHolidayType3("");
		vo.setPltSearchState("");
		// 申請残業年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), date);
		// 検索プルダウン年月設定
		vo.setPltSearchYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchMonth(DateUtility.getStringMonthM(searchDate));
		// 承認者欄の初期化
		String[] aryPltLblApproverSetting = new String[0];
		vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
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
		// 編集項目設定
		vo.setTxtEditRequestReason("");
		// 有給休暇情報の初期設定
		vo.setLblFormerDay("0");
		vo.setLblCurrentDay("0");
		vo.setLblTotalDay("0");
		vo.setLblTotalTime("0");
		vo.setLblNextGivingDate("0");
		vo.setLblNextGivingAmount("0");
		final String hyphen = mospParams.getName("Hyphen");
		vo.setLblNextManualGivingDate(hyphen);
		vo.setLblNextManualGivingAmount(hyphen);
	}
	
	/**
	* 下書処理を行う。<br>
	* @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	*/
	protected void draft() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		HolidayReferenceBeanInterface holiday = timeReference().holiday();
		HolidayInfoReferenceBeanInterface holidayInfo = timeReference().holidayInfo();
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		boolean isPaidHoliday = TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1());
		boolean isSpecialHoliday = TimeConst.CODE_HOLIDAYTYPE_SPECIAL == getInt(vo.getPltEditHolidayType1());
		boolean isOtherHoliday = TimeConst.CODE_HOLIDAYTYPE_OTHER == getInt(vo.getPltEditHolidayType1());
		boolean isAbsence = TimeConst.CODE_HOLIDAYTYPE_ABSENCE == getInt(vo.getPltEditHolidayType1());
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		if (isPaidHoliday) {
			// 休暇種別が有給休暇又はストック休暇の場合
			if (vo.getPltEditStatusWithPay().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
				// 休暇種別が有給休暇の場合
				for (Date targetDate : dateList) {
					// 勤務形態コード取得
					String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
					regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
					if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
							|| regist.isWorkOnLegalDaysOff(workTypeCode)
							|| regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
						// 法定休日・所定休日・法定休日労働・所定休日労働の場合
						continue;
					}
					double useDay = 0;
					int useHour = 0;
					if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 全休
						// 申請用有給休暇申請可能数リストを取得
						List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
							.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
						if (list.size() >= 2) {
							// 前年度分及び今年度分がある場合
							boolean addErrorMessage = false;
							boolean isHalfPaidLeave = false;
							for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
								double holdDay = paidHolidayDataDto.getHoldDay();
								if (holdDay >= 1 && isHalfPaidLeave) {
									addErrorMessage = true;
									break;
								}
								isHalfPaidLeave = holdDay == TimeConst.HOLIDAY_TIMES_HALF ? true : isHalfPaidLeave;
							}
							if (addErrorMessage) {
								// 前年度分が0.5日且つ今年度分が1日以上の場合
								// 登録失敗メッセージ設定
								addInsertFailedMessage();
								addPaidLeaveForPreviousFiscalYearErrorMessage();
								return;
							}
						}
						// 取得日準備
						Date acquisitionDate = null;
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							if (paidHolidayDataDto.getHoldDay() >= 1) {
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						if (acquisitionDate == null) {
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						useDay = 1;
						// 下書
						draft(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
						// 登録結果確認
						if (mospParams.hasErrorMessage()) {
							// 登録失敗メッセージ設定
							addInsertFailedMessage();
							return;
						}
					} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRangePaidHoliday())
							|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 半休
						// 取得日準備
						Date acquisitionDate = null;
						// 申請用有給休暇申請可能数リストを取得
						List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
							.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							if (paidHolidayDataDto.getHoldDay() >= TimeConst.HOLIDAY_TIMES_HALF) {
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						if (acquisitionDate == null) {
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						useDay = TimeConst.HOLIDAY_TIMES_HALF;
						// 下書
						draft(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
						// 登録結果確認
						if (mospParams.hasErrorMessage()) {
							// 登録失敗メッセージ設定
							addInsertFailedMessage();
							return;
						}
					} else if (TimeConst.CODE_HOLIDAY_RANGE_TIME == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 時休
						// 時間取得
						int hour = getInt(vo.getPltEditEndTime());
						// 開始時刻
						Date startTime = DateUtility.addMinute(
								DateUtility.addHour(targetDate, Integer.parseInt(vo.getPltEditStartHour())),
								Integer.parseInt(vo.getPltEditStartMinute()));
						// 時休の分だけまわして登録
						for (int i = 0; i < hour; i++) {
							// 終了時刻
							Date endTime = DateUtility.addHour(startTime, 1);
							// 取得日準備
							Date acquisitionDate = null;
							// 申請用有給休暇申請可能数リストを取得
							List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
								.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
							for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
								if (paidHolidayDataDto.getHoldHour() >= 1) {
									// 1時間以上の場合
									acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
									break;
								} else if (paidHolidayDataDto.getHoldDay() >= 1) {
									// 1日以上の場合
									acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
									break;
								}
							}
							if (acquisitionDate == null) {
								addHolidayNumUnitExcessErrorMessage(mospParams.getName("Time"));
								return;
							}
							useHour = 1;
							// 下書
							draft(targetDate, targetDate, startTime, endTime, acquisitionDate, targetDate, 0, useHour);
							// 登録結果確認
							if (mospParams.hasErrorMessage()) {
								// 登録失敗メッセージ設定
								addInsertFailedMessage();
								return;
							}
							startTime = endTime;
						}
					} else {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						// 例外メッセージ
						mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
						return;
					}
				}
			} else if (vo.getPltEditStatusWithPay().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_STOCK))) {
				// 休暇種別がストック休暇の場合
				for (Date targetDate : dateList) {
					// 勤務形態コード取得
					String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
					regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
					if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
							|| regist.isWorkOnLegalDaysOff(workTypeCode)
							|| regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
						// 法定休日・所定休日・法定休日労働・所定休日労働の場合
						continue;
					}
					double useDay = 0;
					if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRange())) {
						// 全休
						useDay = 1;
					} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRange())
							|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRange())) {
						// 半休
						useDay = TimeConst.HOLIDAY_TIMES_HALF;
					} else {
						addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
						return;
					}
					// 取得日
					Date acquisitionDate = getStockHolidayAcquisitionDate(targetDate);
					if (acquisitionDate == null) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						addHolidayNumDaysExcessErrorMessage(mospParams.getName("Stock")
								+ mospParams.getName("Vacation"));
						return;
					}
					// 下書
					draft(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
					// 登録結果確認
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
				}
			} else {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isSpecialHoliday) {
			// 休暇種別が特別休暇の場合
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditStatusSpecial(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				String errorMes = mospParams.getName("Vacation", "Classification");
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, errorMes);
				return;
			}
			HolidayDataDtoInterface holidayDataDto = holidayInfo.getHolidayPossibleRequestForRequest(
					vo.getPersonalId(), startDate, vo.getPltEditStatusSpecial(),
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDataDto == null) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			double useDay = getHolidayDays(holidayDto, holidayDataDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 下書
			draft(startDate, endDate, startDate, startDate, holidayDataDto.getActivateDate(), null, useDay, 0);
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isOtherHoliday) {
			// 休暇種別がその他休暇の場合
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditSpecialOther(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				String errorMes = mospParams.getName("Vacation", "Classification");
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, errorMes);
				return;
			}
			HolidayDataDtoInterface holidayDataDto = holidayInfo.getHolidayPossibleRequestForRequest(
					vo.getPersonalId(), startDate, vo.getPltEditSpecialOther(),
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDataDto == null) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			double useDay = getHolidayDays(holidayDto, holidayDataDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 下書
			draft(startDate, endDate, startDate, startDate, holidayDataDto.getActivateDate(), null, useDay, 0);
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isAbsence) {
			// 休暇種別が欠勤の場合
			int count = 0;
			for (Date targetDate : dateList) {
				// 勤務形態コード取得
				String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
				regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
				if (mospParams.hasErrorMessage()) {
					// 登録失敗メッセージ設定
					addInsertFailedMessage();
					return;
				}
				if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
						|| regist.isWorkOnLegalDaysOff(workTypeCode) || regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
					// 法定休日・所定休日・法定休日労働・所定休日労働の場合
					continue;
				}
				count++;
			}
			double useDay = 0;
			if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRange())) {
				useDay = count;
			} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRange())
					|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRange())) {
				useDay = count * TimeConst.HOLIDAY_TIMES_HALF;
			}
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditSpecialAbsence(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM,
						mospParams.getName("Vacation", "Classification"));
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 下書
			draft(startDate, endDate, startDate, startDate, startDate, null, useDay, 0);
			// 登録結果確認
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
		// コミット
		commit();
		// 下書成功メッセージ設定
		addDraftMessage();
		vo.setPltSearchYear(vo.getPltEditStartYear());
		vo.setPltSearchMonth(vo.getPltEditStartMonth());
		setSearchPulldown();
		// 検索
		search();
		String holidayType2 = "";
		if (isPaidHoliday) {
			// 有給休暇の場合
			holidayType2 = vo.getPltEditStatusWithPay();
		} else if (isSpecialHoliday) {
			// 特別休暇の場合
			holidayType2 = vo.getPltEditStatusSpecial();
		} else if (isOtherHoliday) {
			// その他休暇の場合
			holidayType2 = vo.getPltEditSpecialOther();
		} else if (isAbsence) {
			// 欠勤の場合
			holidayType2 = vo.getPltEditSpecialAbsence();
		}
		String range = vo.getPltEditHolidayRange();
		if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1())
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditStatusWithPay())) {
			// 有給休暇の場合
			range = vo.getPltEditHolidayRangePaidHoliday();
		}
		// 履歴編集対象を取得
		setEditUpdateMode(startDate, vo.getPltEditHolidayType1(), holidayType2, range,
				DateUtility.getStringTime(getEditStartTime(), startDate));
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 下書き処理をする。
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param acquisitionDate 取得日
	 * @param targetDate 対象日
	 * @param useDay 使用日数
	 * @param useHour 使用時間数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void draft(Date startDate, Date endDate, Date startTime, Date endTime, Date acquisitionDate,
			Date targetDate, double useDay, int useHour) throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// 休暇申請データ取得
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		if (useHour > 0 && !startTime.equals(getEditStartTime())) {
			// 時間休で且つ開始時刻が異なる場合
			dto = regist.getInitDto();
		}
		if (targetDate != null && targetDate.compareTo(DateUtility.getDate(getEditStartDate())) != 0) {
			dto = regist.getInitDto();
		}
		// DTOに値をセット
		setDtoFields(dto, startDate, endDate, startTime, endTime, acquisitionDate, useDay, useHour);
		if (useHour > 0 && !startTime.equals(getEditStartTime())) {
			// 時間休で且つ開始時刻が異なる場合
			dto.setTmdHolidayRequestId(0);
		}
		if (targetDate != null && targetDate.compareTo(DateUtility.getDate(getEditStartDate())) != 0) {
			dto.setTmdHolidayRequestId(0);
		}
		// 妥当性チェック
		regist.validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
//		// 申請日チェック
//		regist.checkRequest(dto);
//		if (mospParams.hasErrorMessage()) {
//			mospParams.getErrorMessageList().clear();
//			continue;
//		}
		// 申請の相関チェック
		regist.checkDraft(dto);
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_VACATION);
		}
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.draft(workflowDto, dto.getPersonalId(), dto.getRequestStartDate(),
				PlatformConst.WORKFLOW_TYPE_TIME);
		if (workflowDto != null) {
			// ワークフローコメント登録
			platform().workflowCommentRegist().addComment(
					workflowDto,
					mospParams.getUser().getPersonalId(),
					mospParams.getProperties().getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED,
							new String[]{ mospParams.getName("WorkPaper") }));
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 登録
			regist.regist(dto);
		}
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		HolidayReferenceBeanInterface holiday = timeReference().holiday();
		HolidayInfoReferenceBeanInterface holidayInfo = timeReference().holidayInfo();
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		// 申請開始日終了日取得
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		// 申請期間リスト取得
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		boolean isPaidHoliday = TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1());
		boolean isSpecialHoliday = TimeConst.CODE_HOLIDAYTYPE_SPECIAL == getInt(vo.getPltEditHolidayType1());
		boolean isOtherHoliday = TimeConst.CODE_HOLIDAYTYPE_OTHER == getInt(vo.getPltEditHolidayType1());
		boolean isAbsence = TimeConst.CODE_HOLIDAYTYPE_ABSENCE == getInt(vo.getPltEditHolidayType1());
		// 有給休暇の場合
		if (isPaidHoliday) {
			// 休暇種別が有給休暇の場合
			if (vo.getPltEditStatusWithPay().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
				// 申請期間リスト毎に処置
				for (Date targetDate : dateList) {
					// 勤務形態コード取得
					String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
					regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
					if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
							|| regist.isWorkOnLegalDaysOff(workTypeCode)
							|| regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
						// 法定休日・所定休日・法定休日労働・所定休日労働の場合
						continue;
					}
					double useDay = 0;
					int useHour = 0;
					// 全休
					if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 申請用有給休暇申請可能数リストを取得
						List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
							.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
						if (list.size() >= 2) {
							// 前年度分及び今年度分がある場合
							boolean addErrorMessage = false;
							boolean isHalfPaidLeave = false;
							for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
								double holdDay = paidHolidayDataDto.getHoldDay();
								if (holdDay >= 1 && isHalfPaidLeave) {
									addErrorMessage = true;
									break;
								}
								isHalfPaidLeave = holdDay == TimeConst.HOLIDAY_TIMES_HALF ? true : isHalfPaidLeave;
							}
							if (addErrorMessage) {
								// 前年度分が0.5日且つ今年度分が1日以上の場合
								// 登録失敗メッセージ設定
								addInsertFailedMessage();
								addPaidLeaveForPreviousFiscalYearErrorMessage();
								return;
							}
						}
						// 取得日準備
						Date acquisitionDate = null;
						// 申請用有給休暇申請可能数リスト毎に処理
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							// 保有日数が1以上の場合
							if (paidHolidayDataDto.getHoldDay() >= 1) {
								// 取得日設定
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						// 取得日がない場合
						if (acquisitionDate == null) {
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						useDay = 1;
						// 登録
						regist(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
						// 登録結果確認
						if (mospParams.hasErrorMessage()) {
							// 登録失敗メッセージ設定
							addInsertFailedMessage();
							return;
						}
					} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRangePaidHoliday())
							|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 半休
						// 取得日準備
						Date acquisitionDate = null;
						// 申請用有給休暇申請可能数リストを取得
						List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
							.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							if (paidHolidayDataDto.getHoldDay() >= TimeConst.HOLIDAY_TIMES_HALF) {
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						// 取得日がない場合
						if (acquisitionDate == null) {
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						// 半休設定
						useDay = TimeConst.HOLIDAY_TIMES_HALF;
						// 登録
						regist(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
						// 登録結果確認
						if (mospParams.hasErrorMessage()) {
							// 登録失敗メッセージ設定
							addInsertFailedMessage();
							return;
						}
					} else if (TimeConst.CODE_HOLIDAY_RANGE_TIME == getInt(vo.getPltEditHolidayRangePaidHoliday())) {
						// 時休
						int hour = getInt(vo.getPltEditEndTime());
						// 時休が0の場合
						if (hour == 0) {
							// 登録失敗メッセージ設定
							addInsertFailedMessage();
							mospParams.addErrorMessage(PlatformMessageConst.MSG_REQUIRED,
									mospParams.getName("Time", "Rest", "Application", "Time"));
							return;
						}
						// 開始時刻
						Date startTime = DateUtility.addMinute(
								DateUtility.addHour(targetDate, Integer.parseInt(vo.getPltEditStartHour())),
								Integer.parseInt(vo.getPltEditStartMinute()));
						// 時休の分だけまわして登録
						for (int i = 0; i < hour; i++) {
							// 終了時刻
							Date endTime = DateUtility.addHour(startTime, 1);
							// 取得日準備
							Date acquisitionDate = null;
							// 申請用有給休暇申請可能数リストを取得
							List<PaidHolidayDataDtoInterface> list = paidHolidayInfo
								.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), targetDate);
							// 有給休暇データ情報毎に処理
							for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
								if (paidHolidayDataDto.getHoldHour() >= 1) {
									// 1時間以上の場合
									acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
									break;
								} else if (paidHolidayDataDto.getHoldDay() >= 1) {
									// 1日以上の場合
									acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
									break;
								}
							}
							if (acquisitionDate == null) {
								addHolidayNumUnitExcessErrorMessage(mospParams.getName("Time"));
								return;
							}
							useHour = 1;
							// 登録
							regist(targetDate, targetDate, startTime, endTime, acquisitionDate, targetDate, 0, useHour);
							// 登録結果確認
							if (mospParams.hasErrorMessage()) {
								// 登録失敗メッセージ設定
								addInsertFailedMessage();
								return;
							}
							startTime = endTime;
						}
					} else {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						// 例外メッセージ
						mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
						return;
					}
				}
			} else if (vo.getPltEditStatusWithPay().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_STOCK))) {
				// 休暇種別がストック休暇の場合
				for (Date targetDate : dateList) {
					// 勤務形態コード取得
					String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
					regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
					if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
							|| regist.isWorkOnLegalDaysOff(workTypeCode)
							|| regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
						// 法定休日・所定休日・法定休日労働・所定休日労働の場合
						continue;
					}
					// 休暇日数(全休・半休)
					double useDay = 0;
					if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRange())) {
						// 全休
						useDay = TimeConst.HOLIDAY_TIMES_ALL;
					} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRange())
							|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRange())) {
						// 半休
						useDay = TimeConst.HOLIDAY_TIMES_HALF;
					} else {
						addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
						return;
					}
					// 取得日
					Date acquisitionDate = getStockHolidayAcquisitionDate(targetDate);
					if (acquisitionDate == null) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						addHolidayNumDaysExcessErrorMessage(mospParams.getName("Stock")
								+ mospParams.getName("Vacation"));
						return;
					}
					// 登録する
					regist(targetDate, targetDate, targetDate, targetDate, acquisitionDate, targetDate, useDay, 0);
					// 登録結果確認
					if (mospParams.hasErrorMessage()) {
						// 登録失敗メッセージ設定
						addInsertFailedMessage();
						return;
					}
				}
			} else {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isSpecialHoliday) {
			// 休暇種別が特別休暇の場合
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditStatusSpecial(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				// 休暇種別が存在しません。
				String errorMes = mospParams.getName("Vacation", "Classification");
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, errorMes);
				return;
			}
			// 申請用休暇データ取得
			HolidayDataDtoInterface holidayDataDto = holidayInfo.getHolidayPossibleRequestForRequest(
					vo.getPersonalId(), startDate, vo.getPltEditStatusSpecial(),
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDataDto == null) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			double useDay = getHolidayDays(holidayDto, holidayDataDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 登録処理をする
			regist(startDate, endDate, startDate, startDate, holidayDataDto.getActivateDate(), null, useDay, 0);
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isOtherHoliday) {
			// 休暇種別がその他休暇の場合
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditSpecialOther(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				String errorMes = mospParams.getName("Vacation", "Classification");
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, errorMes);
				return;
			}
			// 申請用休暇データ取得
			HolidayDataDtoInterface holidayDataDto = holidayInfo.getHolidayPossibleRequestForRequest(
					vo.getPersonalId(), startDate, vo.getPltEditSpecialOther(),
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDataDto == null) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
				return;
			}
			double useDay = getHolidayDays(holidayDto, holidayDataDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 登録処理をする。
			regist(startDate, endDate, startDate, startDate, holidayDataDto.getActivateDate(), null, useDay, 0);
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
		} else if (isAbsence) {
			// 休暇種別が欠勤の場合
			int count = 0;
			for (Date targetDate : dateList) {
				// 勤務形態コード取得
				String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
				regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
				if (mospParams.hasErrorMessage()) {
					// 登録失敗メッセージ設定
					addInsertFailedMessage();
					return;
				}
				if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
						|| regist.isWorkOnLegalDaysOff(workTypeCode) || regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
					// 法定休日・所定休日・法定休日労働・所定休日労働の場合
					continue;
				}
				count++;
			}
			double useDay = 0;
			if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRange())) {
				useDay = count;
			} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRange())
					|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRange())) {
				useDay = count * TimeConst.HOLIDAY_TIMES_HALF;
			}
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(vo.getPltEditSpecialAbsence(), startDate,
					Integer.parseInt(vo.getPltEditHolidayType1()));
			if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				// 例外メッセージ
				mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM,
						mospParams.getName("Vacation", "Classification"));
				return;
			}
			// 半休申請チェック
			checkHalfHolidayRequest(holidayDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			// 登録処理
			regist(startDate, endDate, startDate, startDate, startDate, null, useDay, 0);
			// 登録結果確認
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
		// コミット
		commit();
		// 申請成功メッセージ設定
		addAppliMessage();
		if (Integer.parseInt(vo.getPltEditHolidayRangePaidHoliday()) == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| Integer.parseInt(vo.getPltEditHolidayRangePaidHoliday()) == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			// 半休申請メッセージ設定
			addHalfHolidayRequestMessage();
		}
		// 登録結果確認
		if (!mospParams.hasErrorMessage()) {
			// 登録が成功した場合、初期状態に戻す。
			String startYear = vo.getPltEditStartYear();
			String startMonth = vo.getPltEditStartMonth();
			insertMode();
			vo.setPltSearchYear(startYear);
			vo.setPltSearchMonth(startMonth);
			setSearchPulldown();
			// 検索
			search();
		}
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli(HolidayRequestDtoInterface dto) throws MospException {
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		AttendanceTransactionRegistBeanInterface attendanceTransactionRegist = time().attendanceTransactionRegist();
		// 申請の相関チェック
		regist.checkAppli(dto);
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_VACATION);
		}
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestStartDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 登録
			regist.regist(dto);
			// 勤怠データ削除
			regist.deleteAttendance(dto);
			// 勤怠データ下書
			regist.draftAttendance(dto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(dto);
		}
	}
	
	/**
	 * 登録処理をする。
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param acquisitionDate 取得日
	 * @param targetDate 対象日
	 * @param useDay 使用日数
	 * @param useHour 使用時間数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(Date startDate, Date endDate, Date startTime, Date endTime, Date acquisitionDate,
			Date targetDate, double useDay, int useHour) throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		AttendanceTransactionRegistBeanInterface attendanceTransactionRegist = time().attendanceTransactionRegist();
		// DTOの準備
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// 時間休
		if (useHour > 0 && !startTime.equals(getEditStartTime())) {
			// 時間休で且つ開始時刻が異なる場合
			dto = regist.getInitDto();
		}
		// 特別休暇/その他
		if (targetDate != null && targetDate.compareTo(DateUtility.getDate(getEditStartDate())) != 0) {
			dto = regist.getInitDto();
		}
		// DTOに値をセット
		setDtoFields(dto, startDate, endDate, startTime, endTime, acquisitionDate, useDay, useHour);
		// 時間休
		if (useHour > 0 && !startTime.equals(getEditStartTime())) {
			// 時間休で且つ開始時刻が異なる場合
			dto.setTmdHolidayRequestId(0);
		}
		// 特別休暇/その他
		if (targetDate != null && targetDate.compareTo(DateUtility.getDate(getEditStartDate())) != 0) {
			dto.setTmdHolidayRequestId(0);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
//		// 申請日チェック
//		regist.checkRequest(dto);
//		if (mospParams.hasErrorMessage()) {
//			mospParams.getErrorMessageList().clear();
//			continue;
//		}
		// 申請の相関チェック
		regist.checkAppli(dto);
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_VACATION);
		}
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestStartDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 登録
			regist.regist(dto);
			// 勤怠データ削除
			regist.deleteAttendance(dto);
			// 勤怠データ下書
			regist.draftAttendance(dto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(dto);
		}
	}
	
	/**
	* 取下処理を行う。<br>
	* @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	*/
	protected void withdrawn() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// DTOの準備
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
		// 存在確認
		checkSelectedDataExist(dto);
		// 取下の相関チェック
		regist.checkWithdrawn(dto);
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		boolean isDraft = PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus());
		if (isDraft) {
			// 下書の場合は削除する
			workflowRegist.delete(workflowDto);
			workflowCommentRegist.deleteList(reference().workflowComment().getWorkflowCommentList(
					workflowDto.getWorkflow()));
			regist.delete(dto);
		} else {
			// 下書でない場合は取下する
			// ワークフロー登録
			workflowDto = workflowRegist.withdrawn(workflowDto);
			if (workflowDto != null) {
				// ワークフローコメント登録
				workflowCommentRegist.addComment(
						workflowDto,
						mospParams.getUser().getPersonalId(),
						mospParams.getProperties().getMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED,
								new String[]{ mospParams.getName("TakeDown") }));
			}
		}
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// コミット
		commit();
		if (isDraft) {
			// 削除成功メッセージ設定
			addDeleteMessage();
		} else {
			// 取下成功メッセージ設定
			addTakeDownMessage();
		}
		String searchYear = vo.getPltEditStartYear();
		String searchMonth = vo.getPltEditStartMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		vo.setPltSearchYear(searchYear);
		vo.setPltSearchMonth(searchMonth);
		setSearchPulldown();
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 一括更新処理
		time().holidayRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addBatchUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 取下成功メッセージ設定
		addTakeDownMessage();
		String searchYear = vo.getPltSearchYear();
		String searchMonth = vo.getPltSearchMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間設定
		setSearchRequestDate(searchYear, searchMonth);
		// 検索
		search();
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 有効日取得
		Date date = getEditStartDate();
		int editRequestYear = DateUtility.getYear(date);
		// 編集項目設定
		vo.setAryPltEditStartYear(getYearArray(editRequestYear));
		vo.setAryPltEditStartMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditStartDay(getDayArray());
		vo.setAryPltEditEndYear(getYearArray(editRequestYear));
		vo.setAryPltEditEndMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditEndDay(getDayArray());
		vo.setAryPltEditHolidayType1(mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false));
		// 休暇種別2(有給休暇)プルダウン設定
		vo.setAryPltEditHolidayType2WithPay(mospParams.getProperties().getCodeArray(
				TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false));
		vo.setAryPltEditHolidayRangePaidHoliday(mospParams.getProperties().getCodeArray(
				TimeConst.CODE_HOLIDAY_TYPE3_RANGE2, false));
		vo.setAryPltEditStartHour(getHourArray());
		ApplicationDtoInterface appDto = timeReference().application().findForPerson(vo.getPersonalId(), date);
		if (appDto != null) {
			TimeSettingDtoInterface timeSettingDto = timeReference().timeSetting().getTimeSettingInfo(
					appDto.getWorkSettingCode(), date);
			if (timeSettingDto != null) {
				// 一日の起算時の23時間後まで
				vo.setAryPltEditStartHour(getHourArray(DateUtility.getHour(timeSettingDto.getStartDayTime()) + 23, true));
				// 終了時刻は勤怠設定の所定労働時間まで
				vo.setAryPltEditEndTime(getHourArray(DateUtility.getHour(timeSettingDto.getGeneralWorkTime()), false));
			}
			PaidHolidayDtoInterface paidHolidayDto = timeReference().paidHoliday().getPaidHolidayInfo(
					appDto.getPaidHolidayCode(), date);
			if (paidHolidayDto != null && paidHolidayDto.getTimelyPaidHolidayFlag() == 0) {
				// 時休が有効の場合
				vo.setAryPltEditHolidayRangePaidHoliday(mospParams.getProperties().getCodeArray(
						TimeConst.CODE_HOLIDAY_TYPE3_RANGE1, false));
				// 分は1/15/30単位
				vo.setAryPltEditStartMinute(getMinuteArray(paidHolidayDto.getAppliTimeInterval()));
			}
		}
		vo.setAryPltEditHolidayRange(mospParams.getProperties()
			.getCodeArray(TimeConst.CODE_HOLIDAY_TYPE3_RANGE2, false));
		// 検索項目設定
		vo.setAryPltSearchYear(getYearArray(editRequestYear));
		vo.setAryPltSearchMonth(getMonthArray(true));
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setEditActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		// プルダウン設定
		setPulldown();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 開始年月日と終了年月日の比較
			if (chkActivationDateValidate()) {
				return;
			}
			if (setApproverPullDown(vo.getPersonalId(), getEditStartDate(), PlatformConst.WORKFLOW_TYPE_TIME) == false) {
				return;
			}
			HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestStartDate(getEditStartDate());
			dto.setRequestEndDate(getEditEndDate());
//			dto.setHolidayRange(1);
			// 有効日設定時のチェック
			regist.checkSetRequestDate(dto);
			if (mospParams.hasErrorMessage()) {
				// 決定失敗メッセージ設定
				addFixFailedMessage();
				return;
			}
			if (!getEditStartDate().equals(getEditEndDate())) {
				// 休暇開始日と休暇終了日が異なる場合は全休をセット
				vo.setPltEditHolidayRange(String.valueOf(TimeConst.CODE_HOLIDAY_RANGE_ALL));
				vo.setPltEditHolidayRangePaidHoliday(String.valueOf(TimeConst.CODE_HOLIDAY_RANGE_ALL));
			}
			// 連続取得設定
			setHolidayContinue();
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			String[] aryPltLblApproverSetting = new String[0];
			vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		//setHourPulldown();
		setEditPulldown();
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditPulldown() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayReferenceBeanInterface holidayReference = timeReference().holiday();
		HolidayInfoReferenceBeanInterface holidayInfoReference = timeReference().holidayInfo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			Date startDate = getEditStartDate();
			boolean paidLeave = false;
			List<PaidHolidayDataDtoInterface> paidList = timeReference().paidHolidayInfo()
				.getPaidHolidayPossibleRequestForRequestList(vo.getPersonalId(), startDate);
			for (PaidHolidayDataDtoInterface paidHolidayDataDto : paidList) {
				if (paidHolidayDataDto.getHoldDay() > 0 || paidHolidayDataDto.getHoldHour() > 0) {
					paidLeave = true;
					break;
				}
			}
			boolean stockLeave = false;
			List<StockHolidayDataDtoInterface> stockList = timeReference().stockHolidayInfo()
				.getStockHolidayPossibleRequestForRequest(vo.getPersonalId(), startDate);
			for (StockHolidayDataDtoInterface stockHolidayDataDto : stockList) {
				if (stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
						- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() > 0) {
					stockLeave = true;
					break;
				}
			}
			List<HolidayDataDtoInterface> specialList = holidayInfoReference.getHolidayPossibleRequestListForRequest(
					vo.getPersonalId(), getEditStartDate(), TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
			List<HolidayDataDtoInterface> otherList = holidayInfoReference.getHolidayPossibleRequestListForRequest(
					vo.getPersonalId(), getEditStartDate(), TimeConst.CODE_HOLIDAYTYPE_OTHER);
			Set<String> specialSet = new HashSet<String>();
			for (HolidayDataDtoInterface holidayDataDto : specialList) {
				specialSet.add(holidayDataDto.getHolidayCode());
			}
			Set<String> otherSet = new HashSet<String>();
			for (HolidayDataDtoInterface holidayDataDto : otherList) {
				otherSet.add(holidayDataDto.getHolidayCode());
			}
			// プルダウンデータ取得
			String[][] paidArray = getAryPltEditHolidayType2Paid(paidLeave, stockLeave);
			String[][] specialArray = holidayReference.getSelectArray(startDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL,
					false, specialSet);
			String[][] otherArray = holidayReference.getSelectArray(startDate, TimeConst.CODE_HOLIDAYTYPE_OTHER, false,
					otherSet);
			String[][] absenceArray = holidayReference.getSelectArray(getEditStartDate(),
					TimeConst.CODE_HOLIDAYTYPE_ABSENCE, false);
			// プルダウンデータの存在チェック
			String noObjectData = mospParams.getName("NoObjectData");
			boolean paidStockDeleteFlag = !paidLeave && !stockLeave;
			boolean specialDeleteFlag = noObjectData.equals(specialArray[0][1]);
			boolean otherDeleteFlag = noObjectData.equals(otherArray[0][1]);
			boolean absenceDeleteFlag = noObjectData.equals(absenceArray[0][1]);
			String[][] holidayArray = mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false);
			String[][] newHolidayArray = holidayArray;
			int minus = 0;
			if (paidStockDeleteFlag) {
				minus++;
			}
			if (specialDeleteFlag) {
				minus++;
			}
			if (otherDeleteFlag) {
				minus++;
			}
			if (absenceDeleteFlag) {
				minus++;
			}
			if (minus > 0) {
				newHolidayArray = new String[holidayArray.length - minus][2];
				int i = 0;
				for (String[] holiday : holidayArray) {
					if (paidStockDeleteFlag) {
						if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holiday[0])) {
							continue;
						}
					}
					if (specialDeleteFlag) {
						if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_SPECIAL).equals(holiday[0])) {
							continue;
						}
					}
					if (otherDeleteFlag) {
						if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_OTHER).equals(holiday[0])) {
							continue;
						}
					}
					if (absenceDeleteFlag) {
						if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_ABSENCE).equals(holiday[0])) {
							continue;
						}
					}
					newHolidayArray[i][0] = holiday[0];
					newHolidayArray[i][1] = holiday[1];
					i++;
				}
			}
			if (newHolidayArray.length == 0) {
				vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				// 決定失敗メッセージ設定
				addFixFailedMessage();
				mospParams.addErrorMessage(PlatformMessageConst.MSG_WORKFORM_EXISTENCE, mospParams.getName("Vacation"));
				return;
			}
			// プルダウン設定
			vo.setAryPltEditHolidayType1(newHolidayArray);
			vo.setAryPltEditHolidayType2WithPay(paidArray);
			vo.setAryPltEditHolidayType2Special(specialArray);
			vo.setAryPltEditHolidayType2Other(otherArray);
			vo.setAryPltEditHolidayType2Absence(absenceArray);
			Date endDate = getEditEndDate();
			if (startDate.compareTo(endDate) == 0) {
				// 同日付に時差出勤が申請されているか確認する。
				getDifferenceRequest1(vo.getPersonalId(), startDate);
			}
		}
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setSearchActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getJsSearchModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setSearchPulldown();
	}
	
	/**
	 * 休暇連続取得設定を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setHolidayContinue() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		// 初期化
		vo.setJsHolidayTerm(Integer.toString(1));
		vo.setJsHolidayContinue(Integer.toString(2));
		vo.setJsHolidayRemainDay("");
		// 有給或いは欠勤の場合
		if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1())
				|| TimeConst.CODE_HOLIDAYTYPE_ABSENCE == getInt(vo.getPltEditHolidayType1())) {
			return;
		}
		// 休暇申請の期間取得
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		// 休暇の期間を取得
		int count = 0;
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		for (Date targetDate : dateList) {
			// 勤務形態コード取得
			String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
			regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				addInsertFailedMessage();
				return;
			}
			if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
					|| regist.isWorkOnLegalDaysOff(workTypeCode) || regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			count++;
		}
		// 休暇期間を設定
		vo.setJsHolidayTerm(Integer.toString(count));
		// 休暇情報取得及び設定
		String holidayCode = "";
		HolidayDataDtoInterface holidayDataDto = null;
		if (TimeConst.CODE_HOLIDAYTYPE_SPECIAL == getInt(vo.getPltEditHolidayType1())) {
			holidayCode = vo.getPltEditStatusSpecial();
			holidayDataDto = timeReference().holidayInfo().getHolidayPossibleRequestForRequest(vo.getPersonalId(),
					getEditStartDate(), vo.getPltEditStatusSpecial(), Integer.parseInt(vo.getPltEditHolidayType1()));
		} else if (TimeConst.CODE_HOLIDAYTYPE_OTHER == getInt(vo.getPltEditHolidayType1())) {
			holidayCode = vo.getPltEditSpecialOther();
			holidayDataDto = timeReference().holidayInfo().getHolidayPossibleRequestForRequest(vo.getPersonalId(),
					getEditStartDate(), vo.getPltEditSpecialOther(), Integer.parseInt(vo.getPltEditHolidayType1()));
		}
		if (holidayDataDto == null || holidayDataDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON
				|| holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay() <= 0) {
			vo.setJsHolidayRemainDay("0");
			return;
		}
		HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(holidayCode, getEditStartDate(),
				Integer.parseInt(vo.getPltEditHolidayType1()));
		if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			vo.setJsHolidayRemainDay("0");
			return;
		}
		vo.setJsHolidayContinue(Integer.toString(holidayDto.getContinuousAcquisition()));
		vo.setJsHolidayRemainDay(Double.toString(holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()));
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void selectActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		select();
		String transferredHolidayType1 = getTransferredHolidayType1();
		if (transferredHolidayType1 != null) {
			vo.setPltEditHolidayType1(transferredHolidayType1);
		}
		boolean isPaid = TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1());
		boolean isSpecial = TimeConst.CODE_HOLIDAYTYPE_SPECIAL == getInt(vo.getPltEditHolidayType1());
		boolean isOther = TimeConst.CODE_HOLIDAYTYPE_OTHER == getInt(vo.getPltEditHolidayType1());
		boolean isAbsence = TimeConst.CODE_HOLIDAYTYPE_ABSENCE == getInt(vo.getPltEditHolidayType1());
		String transferredHolidayType2 = getTransferredHolidayType2();
		if (transferredHolidayType2 != null) {
			if (isPaid) {
				// 有給休暇
				vo.setPltEditStatusWithPay(transferredHolidayType2);
			} else if (isSpecial) {
				// 特別休暇
				vo.setPltEditStatusSpecial(transferredHolidayType2);
			} else if (isOther) {
				// その他休暇
				vo.setPltEditSpecialOther(transferredHolidayType2);
			} else if (isAbsence) {
				// 欠勤
				vo.setPltEditSpecialAbsence(transferredHolidayType2);
			}
		}
		String transferredHolidayRange = getTransferredHolidayRange();
		if (transferredHolidayRange != null) {
			if (isPaid) {
				// 有給休暇
				vo.setPltEditHolidayRangePaidHoliday(transferredHolidayRange);
			} else if (isSpecial || isOther || isAbsence) {
				// 特別休暇・その他休暇・欠勤
				vo.setPltEditHolidayRange(transferredHolidayRange);
			}
		}
		setEditActivationDate();
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setSearchPulldown() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getJsSearchModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定
			Date date;
			if (vo.getPltSearchMonth().isEmpty()) {
				date = MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchYear()), 1, mospParams);
			} else {
				date = MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchYear()),
						getInt(vo.getPltSearchMonth()), mospParams);
			}
			vo.setAryPltSearchHolidayType2Special(timeReference().holiday().getSelectArray(date,
					TimeConst.CODE_HOLIDAYTYPE_SPECIAL, true));
			vo.setAryPltSearchHolidayType2Other(timeReference().holiday().getSelectArray(date,
					TimeConst.CODE_HOLIDAYTYPE_OTHER, true));
			vo.setAryPltSearchHolidayType2Absence(timeReference().holiday().getSelectArray(date,
					TimeConst.CODE_HOLIDAYTYPE_ABSENCE, true));
			vo.setAryPltSearchHolidayRangePaidHoliday(mospParams.getProperties().getCodeArray(
					TimeConst.CODE_HOLIDAY_TYPE3_RANGE2, true));
			ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(vo.getPersonalId(),
					date);
			if (applicationDto == null) {
				return;
			}
			PaidHolidayDtoInterface paidHolidayDto = timeReference().paidHoliday().getPaidHolidayInfo(
					applicationDto.getPaidHolidayCode(), date);
			if (paidHolidayDto == null) {
				return;
			}
			if (paidHolidayDto.getTimelyPaidHolidayFlag() == 0) {
				// 時休が有効の場合
				vo.setAryPltSearchHolidayRangePaidHoliday(mospParams.getProperties().getCodeArray(
						TimeConst.CODE_HOLIDAY_TYPE3_RANGE1, true));
			}
		}
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// プルダウン設定
		setPulldown();
		// 状態
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日(検索)モード設定
		vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(HolidayRequestRequestDateComparator.class.getName());
		// 
		vo.setJsModeDifferenceRequest1("");
		//
		setEditPulldown();
		setSearchPulldown();
		// 基本情報チェック
		timeReference().holidayRequest().chkBasicInfo(vo.getPersonalId(), getEditStartDate());
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		if (personalId != null) {
			// 人事情報をVOに設定
			setEmployeeInfo(personalId, targetDate);
			// ページ繰り設定
			setPageInfo(CMD_PAGE, getListLength());
			// 新規登録モード設定
			insertMode();
			// 検索
			search();
		}
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getDate(getTransferredActivateDate()), getTransferredHolidayType1(),
				getTransferredHolidayType2(), getTransferredHolidayRange(), getTransferredStartTime());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		setEditActivationDate();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 申請開始日と休暇種別1と休暇種別2と休暇範囲と時休開始時刻で編集対象情報を取得する。<br>
	 * @param requestStartDate 申請開始日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @param paidTime 休暇時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(Date requestStartDate, String holidayType1, String holidayType2,
			String holidayRange, String paidTime) throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// DTO準備
		HolidayRequestDtoInterface dto = null;
		// 時間休場合
		if (getInt(holidayRange) == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 開始時刻配列取得
			String[] startTime = MospUtility.split(paidTime, mospParams.getName("SingleColon"));
			// 開始時刻と合わせて日付
			Date startDateTime = DateUtility.addMinute(
					DateUtility.addHour(requestStartDate, Integer.parseInt(startTime[0])),
					Integer.parseInt(startTime[1]));
			// 履歴編集対象取得
			dto = timeReference().holidayRequest().findForKeyOnWorkflow(vo.getPersonalId(), requestStartDate,
					Integer.parseInt(holidayType1), holidayType2, Integer.parseInt(holidayRange), startDateTime);
		} else {
			// 履歴編集対象取得
			dto = timeReference().holidayRequest().findForKeyOnWorkflow(vo.getPersonalId(), requestStartDate,
					Integer.parseInt(holidayType1), holidayType2, Integer.parseInt(holidayRange), requestStartDate);
		}
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		boolean containsHalfHoliday = false;
		long[] idArray = getIdArray(vo.getCkbSelect());
		if (idArray == null || idArray.length == 0) {
			// 更新失敗メッセージ設定
			addBatchUpdateFailedMessage();
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHECK);
			return;
		}
		for (long id : idArray) {
			HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(id);
			if (dto == null || dto.getDeleteFlag() == MospConst.DELETE_FLAG_ON) {
				// 更新失敗メッセージ設定
				addBatchUpdateFailedMessage();
				mospParams.addErrorMessage(PlatformMessageConst.MSG_UPDATE_OTHER_USER);
				return;
			}
			int count = 0;
			List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
			for (Date date : dateList) {
				// 勤務形態コード取得
				String workTypeCode = regist.getScheduledWorkTypeCode(dto.getPersonalId(), date);
				// 勤務形態チェック
				regist.checkWorkType(dto, date, workTypeCode);
				if (mospParams.hasErrorMessage()) {
					// 更新失敗メッセージ設定
					addBatchUpdateFailedMessage();
					return;
				}
				if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
						|| regist.isWorkOnLegalDaysOff(workTypeCode) || regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
					// 法定休日・所定休日・法定休日労働・所定休日労働の場合
					continue;
				}
				count++;
			}
			boolean isSpecial = dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL;
			boolean isOther = dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER;
			boolean isAbsence = dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE;
			boolean isHalf = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM;
			if (isHalf) {
				// 半休の場合
				containsHalfHoliday = true;
			}
			if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
					// 有給休暇の場合
					boolean isAll = dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
					// 申請用有給休暇申請可能数リストを取得
					List<PaidHolidayDataDtoInterface> list = timeReference().paidHolidayInfo()
						.getPaidHolidayPossibleRequestForRequestList(dto.getPersonalId(), dto.getRequestStartDate());
					// 取得日準備
					Date acquisitionDate = null;
					if (isAll || isHalf) {
						// 全休・午前休・午後休の場合
						double days = count;
						if (isHalf) {
							// 半休の場合
							days = count * TimeConst.HOLIDAY_TIMES_HALF;
						}
						if (Double.compare(dto.getUseDay(), days) != 0) {
							// 日数が異なる場合
							// 更新失敗メッセージ設定
							addBatchUpdateFailedMessage();
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						if (isAll && list.size() >= 2) {
							// 全休且つ前年度分及び今年度分がある場合
							boolean isHalfPaidLeave = false;
							for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
								double holdDay = paidHolidayDataDto.getHoldDay();
								if (holdDay >= days && isHalfPaidLeave) {
									// 前年度分が0.5日且つ今年度分が1日以上の場合
									// 更新失敗メッセージ設定
									addBatchUpdateFailedMessage();
									addPaidLeaveForPreviousFiscalYearErrorMessage();
									return;
								}
								isHalfPaidLeave = holdDay == TimeConst.HOLIDAY_TIMES_HALF ? true : isHalfPaidLeave;
							}
						}
						// 申請用有給休暇申請可能数リスト毎に処理
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							if (paidHolidayDataDto.getHoldDay() >= days) {
								// 取得日設定
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						if (acquisitionDate == null) {
							// 取得日がない場合
							// 更新失敗メッセージ設定
							addBatchUpdateFailedMessage();
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
					} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
						// 時休の場合
						if (Double.compare(dto.getUseDay(), 0) != 0) {
							// 日数が異なる場合
							// 更新失敗メッセージ設定
							addBatchUpdateFailedMessage();
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
							return;
						}
						// 申請用有給休暇申請可能数リスト毎に処理
						for (PaidHolidayDataDtoInterface paidHolidayDataDto : list) {
							if (paidHolidayDataDto.getHoldHour() >= 1) {
								// 取得日設定
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							} else if (paidHolidayDataDto.getHoldDay() >= 1) {
								// 取得日設定
								acquisitionDate = paidHolidayDataDto.getAcquisitionDate();
								break;
							}
						}
						if (acquisitionDate == null) {
							// 取得日がない場合
							// 更新失敗メッセージ設定
							addBatchUpdateFailedMessage();
							addHolidayNumUnitExcessErrorMessage(mospParams.getName("Time"));
							return;
						}
					} else {
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
						return;
					}
					// DTOに値をセット
					setDtoFields(dto, acquisitionDate);
					// 申請
					appli(dto);
					// 登録結果確認
					if (mospParams.hasErrorMessage()) {
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						return;
					}
				} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(dto.getHolidayType2())) {
					// ストック休暇の場合
					double days = count;
					if (isHalf) {
						// 半休の場合
						days = count * TimeConst.HOLIDAY_TIMES_HALF;
					}
					if (Double.compare(dto.getUseDay(), days) != 0) {
						// 日数が異なる場合
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
						return;
					}
					// 取得日準備
					Date acquisitionDate = null;
					// 申請用ストック休暇申請可能数リストを取得
					List<StockHolidayDataDtoInterface> list = timeReference().stockHolidayInfo()
						.getStockHolidayPossibleRequestForRequest(dto.getPersonalId(), dto.getRequestStartDate());
					// 申請用ストック休暇申請可能数リスト毎に処理
					for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
						if (stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
								- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() >= days) {
							// 取得日設定
							acquisitionDate = stockHolidayDataDto.getAcquisitionDate();
							break;
						}
					}
					if (acquisitionDate == null) {
						// 取得日がない場合
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						addHolidayNumDaysExcessErrorMessage(mospParams.getName("Stock")
								+ mospParams.getName("Vacation"));
						return;
					}
					// DTOに値をセット
					setDtoFields(dto, acquisitionDate);
					// 申請
					appli(dto);
					// 登録結果確認
					if (mospParams.hasErrorMessage()) {
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						return;
					}
				}
			} else if (isSpecial || isOther || isAbsence) {
				// 特別休暇・その他休暇・欠勤の場合
				double days = count;
				if (isHalf) {
					// 半休の場合
					days = count * TimeConst.HOLIDAY_TIMES_HALF;
				}
				HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(dto.getHolidayType2(),
						dto.getRequestStartDate(), dto.getHolidayType1());
				if (holidayDto == null || holidayDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
					// 更新失敗メッセージ設定
					addBatchUpdateFailedMessage();
					mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM,
							mospParams.getName("Vacation", "Classification"));
					return;
				}
				Date acquisitionDate = dto.getRequestStartDate();
				double useDay = days;
				if (isSpecial || isOther) {
					// 特別休暇・その他休暇の場合
					// 申請用休暇データ取得
					HolidayDataDtoInterface holidayDataDto = null;
					holidayDataDto = timeReference().holidayInfo().getHolidayPossibleRequestForRequest(
							dto.getPersonalId(), dto.getRequestStartDate(), dto.getHolidayType2(),
							dto.getHolidayType1());
					if (holidayDataDto == null) {
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						addHolidayNotGiveErrorMessage(holidayDto.getHolidayName());
						return;
					}
					acquisitionDate = holidayDataDto.getActivateDate();
					useDay = getHolidayDays(days, holidayDto, holidayDataDto);
					if (mospParams.hasErrorMessage()) {
						// 更新失敗メッセージ設定
						addBatchUpdateFailedMessage();
						return;
					}
				}
				if (Double.compare(dto.getUseDay(), useDay) != 0) {
					// 日数が異なる場合
					// 更新失敗メッセージ設定
					addBatchUpdateFailedMessage();
					addHolidayNumUnitExcessErrorMessage(mospParams.getName("Day"));
					return;
				}
				// 半休申請チェック
				checkHalfHolidayRequest(holidayDto, dto.getHolidayRange());
				if (mospParams.hasErrorMessage()) {
					// 更新失敗メッセージ設定
					addBatchUpdateFailedMessage();
					return;
				}
				// DTOに値をセット
				setDtoFields(dto, acquisitionDate);
				// 申請
				appli(dto);
				// 登録結果確認
				if (mospParams.hasErrorMessage()) {
					// 更新失敗メッセージ設定
					addBatchUpdateFailedMessage();
					return;
				}
			} else {
				// 更新失敗メッセージ設定
				addBatchUpdateFailedMessage();
				return;
			}
		}
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addBatchUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージ設定
		addUpdateMessage();
		if (containsHalfHoliday) {
			// 半休が含まれる場合
			addHalfHolidayRequestMessage();
		}
		String searchYear = vo.getPltSearchYear();
		String searchMonth = vo.getPltSearchMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		vo.setPltSearchYear(searchYear);
		vo.setPltSearchMonth(searchMonth);
		setSearchPulldown();
		// 検索
		search();
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblHolidayType1 = new String[list.size()];
		String[] aryLblHolidayType2 = new String[list.size()];
		String[] aryLblHolidayRange = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryLblWorkflow = new String[list.size()];
		String[] aryStatusStyle = new String[list.size()];
		String[] aryHolidayType1 = new String[list.size()];
		String[] aryHolidayType2 = new String[list.size()];
		String[] aryHolidayRange = new String[list.size()];
		String[] aryStartTime = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			HolidayRequestListDtoInterface dto = (HolidayRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdHolidayRequestId());
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestStartDate())
					+ mospParams.getName("Wave") + DateUtility.getStringDateAndDay(dto.getRequestEndDate());
			aryLblHolidayType1[i] = timeReference().holiday().getHolidayType1NameForHolidayRequest(
					dto.getHolidayType1(), dto.getHolidayType2());
			aryLblHolidayType2[i] = getHolidayType2Abbr(dto.getHolidayType1(), dto.getHolidayType2(),
					dto.getRequestStartDate());
			aryLblHolidayRange[i] = getHolidayRange(dto);
			aryHolidayType1[i] = String.valueOf(dto.getHolidayType1());
			aryHolidayType2[i] = dto.getHolidayType2();
			aryHolidayRange[i] = String.valueOf(dto.getHolidayRange());
			aryStartTime[i] = getStringTime(dto.getStartTime());
			aryLblRequestReason[i] = dto.getRequestReason();
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStatusStyle[i] = getStatusColor(dto.getState());
			aryLblApproverName[i] = dto.getApproverName();
			aryLblWorkflow[i] = String.valueOf(dto.getWorkflow());
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbHolidayRequestListId(aryCkbRecordId);
		vo.setAryLblDate(aryLblRequestDate);
		vo.setAryLblHolidayType1(aryLblHolidayType1);
		vo.setAryLblHolidayType2(aryLblHolidayType2);
		vo.setAryLblHolidayType3(aryLblHolidayRange);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryStateStyle(aryStatusStyle);
		vo.setAryLblApprover(aryLblApproverName);
		vo.setAryLblWorkflow(aryLblWorkflow);
		vo.setAryHolidayType1(aryHolidayType1);
		vo.setAryHolidayType2(aryHolidayType2);
		vo.setAryHolidayType3(aryHolidayRange);
		vo.setAryStartTime(aryStartTime);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	* 休暇範囲を取得する。<br>
	 * @param dto 対象DTO
	 * @return 休暇範囲
	*/
	protected String getHolidayRange(HolidayRequestListDtoInterface dto) {
		int holidayRange = dto.getHolidayRange();
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			return mospParams.getName("AllTime");
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前休の場合
			return mospParams.getName("FrontTime");
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後休の場合
			return mospParams.getName("BackTime");
		} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時休の場合
			return getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate());
		}
		return "";
	}
	
	/**
	 * @return 休暇開始日を返す
	 */
	protected Date getEditStartDate() {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditStartYear(), vo.getPltEditStartMonth(), vo.getPltEditStartDay());
	}
	
	/**
	 * @return 休暇終了日を返す
	 */
	protected Date getEditEndDate() {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditEndYear(), vo.getPltEditEndMonth(), vo.getPltEditEndDay());
	}
	
	/**
	 * 休暇開始時刻を取得する。<br>
	 * @return 休暇開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getEditStartTime() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		return DateUtility.addMinute(
				DateUtility.addHour(getEditStartDate(), Integer.parseInt(vo.getPltEditStartHour())),
				Integer.parseInt(vo.getPltEditStartMinute()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param acquisitionDate 取得日
	 * @param useDay 使用日数
	 * @param useHour 使用時間数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(HolidayRequestDtoInterface dto, Date startDate, Date endDate, Date startTime,
			Date endTime, Date acquisitionDate, double useDay, int useHour) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmdHolidayRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestStartDate(startDate);
		dto.setRequestEndDate(endDate);
		dto.setHolidayType1(Integer.parseInt(vo.getPltEditHolidayType1()));
		if (vo.getPltEditHolidayType1().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
			dto.setHolidayType2(vo.getPltEditStatusWithPay());
		} else if (vo.getPltEditHolidayType1().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_SPECIAL))) {
			dto.setHolidayType2(vo.getPltEditStatusSpecial());
		} else if (vo.getPltEditHolidayType1().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_OTHER))) {
			dto.setHolidayType2(vo.getPltEditSpecialOther());
		} else {
			dto.setHolidayType2(vo.getPltEditSpecialAbsence());
		}
		if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1())
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditStatusWithPay())) {
			// 有給休暇の場合
			dto.setHolidayRange(Integer.parseInt(vo.getPltEditHolidayRangePaidHoliday()));
		} else {
			// その他の場合
			dto.setHolidayRange(Integer.parseInt(vo.getPltEditHolidayRange()));
		}
		dto.setStartTime(startTime);
		dto.setEndTime(endTime);
		dto.setHolidayAcquisitionDate(acquisitionDate);
		dto.setUseDay(useDay);
		dto.setUseHour(useHour);
		dto.setRequestReason(vo.getTxtEditRequestReason());
	}
	
	/**
	 * 値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param acquisitionDate 取得日
	 */
	protected void setDtoFields(HolidayRequestDtoInterface dto, Date acquisitionDate) {
		dto.setHolidayAcquisitionDate(acquisitionDate);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFields(HolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdHolidayRequestId());
		vo.setPltEditStartYear(DateUtility.getStringYear(dto.getRequestStartDate()));
		vo.setPltEditStartMonth(DateUtility.getStringMonthM(dto.getRequestStartDate()));
		vo.setPltEditStartDay(DateUtility.getStringDayD(dto.getRequestStartDate()));
		vo.setPltEditEndYear(DateUtility.getStringYear(dto.getRequestEndDate()));
		vo.setPltEditEndMonth(DateUtility.getStringMonthM(dto.getRequestEndDate()));
		vo.setPltEditEndDay(DateUtility.getStringDayD(dto.getRequestEndDate()));
		vo.setPltEditHolidayType1(String.valueOf(dto.getHolidayType1()));
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			vo.setPltEditStatusWithPay(dto.getHolidayType2());
		} else if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			vo.setPltEditStatusSpecial(dto.getHolidayType2());
		} else if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			vo.setPltEditSpecialOther(dto.getHolidayType2());
		} else {
			vo.setPltEditSpecialAbsence(dto.getHolidayType2());
		}
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(dto.getHolidayType2())) {
			// 有給休暇の場合
			vo.setPltEditHolidayRangePaidHoliday(String.valueOf(dto.getHolidayRange()));
		} else {
			// その他の場合
			vo.setPltEditHolidayRange(String.valueOf(dto.getHolidayRange()));
		}
		vo.setPltEditStartHour(String.valueOf(DateUtility.getHour(dto.getStartTime(), dto.getRequestStartDate())));
		vo.setPltEditStartMinute(DateUtility.getStringMinuteM(dto.getStartTime()));
		vo.setPltEditEndTime(String.valueOf(dto.getUseHour()));
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * 開始年月日と終了年月日の比較
	 * @return trueは年月日が超えている。falseの年月日が超えていない。
	 * @throws MospException 例外発生時
	 */
	protected boolean chkActivationDateValidate() throws MospException {
		if (getEditStartDate().after(getEditEndDate())) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_ACTIVATIONDATE);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 休暇日数を取得する。<br>
	 * @param holidayDto 休暇マスタDTO
	 * @param holidayDataDto 休暇データDTO
	 * @return 休暇日数
	 * @throws MospException 例外発生時
	 */
	protected double getHolidayDays(HolidayDtoInterface holidayDto, HolidayDataDtoInterface holidayDataDto)
			throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		int count = 0;
		List<Date> dateList = TimeUtility.getDateList(startDate, endDate);
		for (Date targetDate : dateList) {
			// 勤務形態コード取得
			String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), targetDate);
			regist.checkWorkType(startDate, endDate, targetDate, workTypeCode);
			if (mospParams.hasErrorMessage()) {
				return 0;
			}
			if (regist.isLegalDaysOff(workTypeCode) || regist.isPrescribedDaysOff(workTypeCode)
					|| regist.isWorkOnLegalDaysOff(workTypeCode) || regist.isWorkOnPrescribedDaysOff(workTypeCode)) {
				// 法定休日・所定休日・法定休日労働・所定休日労働の場合
				continue;
			}
			count++;
		}
		double holidayDays = 0;
		if (TimeConst.CODE_HOLIDAY_RANGE_ALL == getInt(vo.getPltEditHolidayRange())) {
			// 全休の場合
			holidayDays = count;
		} else if (TimeConst.CODE_HOLIDAY_RANGE_AM == getInt(vo.getPltEditHolidayRange())
				|| TimeConst.CODE_HOLIDAY_RANGE_PM == getInt(vo.getPltEditHolidayRange())) {
			// 午前休又は午後休の場合
			holidayDays = count * TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			mospParams.addErrorMessage(TimeMessageConst.MSG_RANGE_SELECT);
			return 0;
		}
		if (holidayDto.getContinuousAcquisition() == 0) {
			// 連続取得が必須の場合
			if (holidayDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay();
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 1) {
			// 連続取得が警告の場合
			if (holidayDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayDays;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 2) {
			// 連続取得が不要の場合
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				return holidayDays;
			}
			// 付与日数が無制限でない場合
			if (holidayDays <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayDays;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		}
		return 0;
	}
	
	/**
	 * 休暇日数を取得する。<br>
	 * @param days 日数
	 * @param holidayDto 休暇マスタDTO
	 * @param holidayDataDto 休暇データDTO
	 * @return 休暇日数
	 */
	protected double getHolidayDays(double days, HolidayDtoInterface holidayDto, HolidayDataDtoInterface holidayDataDto) {
		if (holidayDto.getContinuousAcquisition() == 0) {
			// 連続取得が必須の場合
			if (days <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay();
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 1) {
			// 連続取得が警告の場合
			if (days <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return days;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		} else if (holidayDto.getContinuousAcquisition() == 2) {
			// 連続取得が不要の場合
			if (holidayDto.getNoLimit() == 1) {
				// 付与日数が無制限の場合
				return days;
			}
			// 付与日数が無制限でない場合
			if (days <= holidayDataDto.getGivingDay() - holidayDataDto.getCancelDay()) {
				return days;
			}
			addHolidayNumDaysExcessErrorMessage(holidayDto.getHolidayName());
			return 0;
		}
		return 0;
	}
	
	/**
	 * 半休申請チェック。
	 * @param dto 対象DTO
	 */
	protected void checkHalfHolidayRequest(HolidayDtoInterface dto) {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		if (dto == null || dto.getHalfHolidayRequest() == MospConst.INACTIVATE_FLAG_OFF
				|| Integer.toString(TimeConst.CODE_HOLIDAY_RANGE_ALL).equals(vo.getPltEditHolidayRange())) {
			return;
		}
		addHalfHolidayRequestErrorMessage(dto.getHolidayName());
	}
	
	/**
	 * 半休申請チェック。
	 * @param dto 対象DTO
	 * @param holidayRange 休暇範囲
	 */
	protected void checkHalfHolidayRequest(HolidayDtoInterface dto, int holidayRange) {
		if (dto == null || dto.getHalfHolidayRequest() == MospConst.INACTIVATE_FLAG_OFF
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			return;
		}
		addHalfHolidayRequestErrorMessage(dto.getHolidayName());
	}
	
	/**
	 * ストック休暇取得日を取得する。<br>
	 * @param targetDate 対象年月日
	 * @return ストック休暇取得日
	 * @throws MospException 例外発生時
	 */
	protected Date getStockHolidayAcquisitionDate(Date targetDate) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		if (!Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(vo.getPltEditHolidayType1())) {
			return null;
		}
		if (!Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(vo.getPltEditStatusWithPay())) {
			// ストック休暇でない場合
			return null;
		}
		List<StockHolidayDataDtoInterface> list = timeReference().stockHolidayInfo()
			.getStockHolidayPossibleRequestForRequest(vo.getPersonalId(), targetDate);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (Integer.toString(TimeConst.CODE_HOLIDAY_RANGE_ALL).equals(vo.getPltEditHolidayRange())) {
			// 全休
			for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
				if (stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
						- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() >= 1) {
					return stockHolidayDataDto.getAcquisitionDate();
				}
			}
		} else if (Integer.toString(TimeConst.CODE_HOLIDAY_RANGE_AM).equals(vo.getPltEditHolidayRange())
				|| Integer.toString(TimeConst.CODE_HOLIDAY_RANGE_PM).equals(vo.getPltEditHolidayRange())) {
			// 午前休又は午後休
			for (StockHolidayDataDtoInterface stockHolidayDataDto : list) {
				if (stockHolidayDataDto.getHoldDay() + stockHolidayDataDto.getGivingDay()
						- stockHolidayDataDto.getCancelDay() - stockHolidayDataDto.getUseDay() >= TimeConst.HOLIDAY_TIMES_HALF) {
					return stockHolidayDataDto.getAcquisitionDate();
				}
			}
		}
		return null;
	}
	
	/**
	 * 有給休暇プルダウンを取得する。<br>
	 * @param paidLeave 有給休暇有無
	 * @param stockLeave ストック休暇有無
	 * @return 有給休暇プルダウン
	 */
	protected String[][] getAryPltEditHolidayType2Paid(boolean paidLeave, boolean stockLeave) {
		String[][] array = mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false);
		int minus = 0;
		if (!paidLeave) {
			minus++;
		}
		if (!stockLeave) {
			minus++;
		}
		if (minus == 0) {
			return array;
		}
		String[][] paidArray = new String[array.length - minus][2];
		int i = 0;
		for (String[] holiday : array) {
			if (!paidLeave) {
				// 有給休暇の場合
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holiday[0])) {
					continue;
				}
			}
			if (!stockLeave) {
				// ストック休暇の場合
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(holiday[0])) {
					continue;
				}
			}
			paidArray[i][0] = holiday[0];
			paidArray[i][1] = holiday[1];
			i++;
		}
		return paidArray;
	}
	
	/**
	 * 有効な有給休暇付与情報を取得できなかった場合のエラーメッセージを設定する。<br>
	 */
	protected void addNotExistHolidayInfoErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_EXIST_HOLIDAY_INFO);
	}
	
	/**
	 * 休暇申請する際に休暇日数を超過していた場合のエラーメッセージを設定する。<br>
	 * @param unit 単位
	 */
	protected void addHolidayNumUnitExcessErrorMessage(String unit) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NUM_DAYS_EXCESS,
				mospParams.getName("Salaried", "Vacation"), unit);
	}
	
	/**
	 * 休暇申請する際に休暇日数を超過していた場合のエラーメッセージを設定する。<br>
	 * @param name 表示する文字
	 */
	protected void addHolidayNumDaysExcessErrorMessage(String name) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NUM_DAYS_EXCESS, name, mospParams.getName("Day"));
	}
	
	/**
	 * 休暇が付与されていない場合のエラーメッセージを設定する。<br>
	 * @param holidayName 休暇名称
	 */
	protected void addHolidayNotGiveErrorMessage(String holidayName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NOT_GIVE, holidayName);
	}
	
	/**
	 * 半休申請が無効の場合のエラーメッセージを設定する。<br>
	 * @param holidayName 休暇名称
	 */
	protected void addHalfHolidayRequestErrorMessage(String holidayName) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("HalfTime"));
		sb.append(mospParams.getName("Application"));
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_6, holidayName, sb.toString(),
				mospParams.getName("Vacation", "Classification"));
	}
	
	/**
	 * 前年度分の有給休暇が0.5日残っている場合のエラーメッセージを設定する。<br>
	 */
	protected void addPaidLeaveForPreviousFiscalYearErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_PAID_HOLIDAY_REQUEST_CHECK);
	}
	
}
