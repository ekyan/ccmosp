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
package jp.mosp.time.file.action;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.file.base.ImportListAction;
import jp.mosp.platform.file.vo.ImportListVo;
import jp.mosp.time.base.TimeBeanHandlerInterface;
import jp.mosp.time.base.TimeReferenceBeanHandlerInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TimelyPaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠情報インポートの実行。インポートマスタの管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_EXECUTION}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_TEMP_OUTPUT}
 * </li></ul>
 */
public class TimeImportListAction extends ImportListAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String					CMD_SHOW		= "TM3210";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿ったインポートマスタ情報の一覧表示を行う。<br>
	 * 一覧表示の際にはインポートコードでソートを行う。<br>
	 */
	public static final String					CMD_SEARCH		= "TM3212";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている
	 * 情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String					CMD_RE_SHOW		= "TM3213";
	
	/**
	 * 実行コマンド。<br>
	 * <br>
	 * インポートを実行する。<br>
	 * 実行時にインポートマスタが決定していない、対象ファイルが選択されていない場合は
	 * エラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String					CMD_EXECUTION	= "TM3215";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String					CMD_SORT		= "TM3218";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String					CMD_PAGE		= "TM3219";
	
	/**
	 * テンプレート出力コマンド。<br>
	 * <br>
	 * ラジオボタンで選択されているインポートマスタの情報を取得し、
	 * マスタ内の項目を用いて参照ファイルのテンプレートを表計算ファイルで出力する。<br>
	 */
	public static final String					CMD_TEMP_OUTPUT	= "TM3186";
	
	/**
	 * MosP勤怠管理用BeanHandler。
	 */
	protected TimeBeanHandlerInterface			time;
	
	/**
	 * MosP勤怠管理参照用BeanHandler。
	 */
	protected TimeReferenceBeanHandlerInterface	timeReference;
	
	
	/**
	 * {@link ImportListAction#ImportListAction()}を実行する。<br>
	 */
	public TimeImportListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
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
		} else if (mospParams.getCommand().equals(CMD_EXECUTION)) {
			// 実行
			prepareVo();
			execution();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_TEMP_OUTPUT)) {
			// TODO テンプレート出力
		}
	}
	
	/**
	 * インポート一覧JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 */
	protected void setImportListInfo() {
		// VO取得
		ImportListVo vo = (ImportListVo)mospParams.getVo();
		// データ区分コードキー設定(勤怠情報データ区分)
		vo.setTableTypeCodeKey(TimeConst.CODE_KEY_TIME_IMPORT_TABLE_TYPE);
		// 再表示コマンド設定
		vo.setReShowCommand(CMD_RE_SHOW);
		// 検索コマンド設定
		vo.setSearchCommand(CMD_SEARCH);
		// 並び替えコマンド設定
		vo.setSortCommand(CMD_SORT);
		// 実行コマンド設定
		vo.setExecuteCommand(CMD_EXECUTION);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// エクスポート一覧共通JSP用コマンド及びデータ区分をVOに設定
		setImportListInfo();
		// エクスポート一覧共通VO初期値設定
		initImportListVoFields();
	}
	
	/**
	 * インポートの実行を行う。<br>
	 * @throws MospException インポートに失敗した場合
	 */
	protected void execution() throws MospException {
		// 勤怠管理参照用BeanHandler取得(ExportListActionでは扱わないためクラスを指定して取得)
		time = (TimeBeanHandlerInterface)createHandler(TimeBeanHandlerInterface.class);
		timeReference = (TimeReferenceBeanHandlerInterface)createHandler(TimeReferenceBeanHandlerInterface.class);
		// アップロードファイルの取得(アップロードファイルはinputStreamReaderで提供される)
		List<String[]> csvList = OrangeSignalUtility.parse(mospParams.getRequestFile(PRM_FIL_IMPORT));
		// VO準備
		ImportListVo vo = (ImportListVo)mospParams.getVo();
		// インポートマスタ取得及び確認
		ImportDtoInterface importDto = reference().importRefer().findForKey(vo.getRadSelect());
		if (importDto == null) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// データ区分確認
		if (TimeFileConst.CODE_IMPORT_TYPE_TMD_ATTENDANCE.equals(importDto.getImportTable())) {
			// 勤怠データ
			importAttendance(importDto, csvList);
		} else if (TimeFileConst.CODE_IMPORT_TYPE_TMD_TOTAL_TIME.equals(importDto.getImportTable())) {
			// 勤怠集計データ
			importTotalTime(importDto, csvList);
		} else if (TimeFileConst.CODE_IMPORT_TYPE_TMD_PAID_HOLIDAY.equals(importDto.getImportTable())) {
			// 有給休暇データ
			importPaidHoliday(importDto, csvList);
		} else if (TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY.equals(importDto.getImportTable())) {
			// ストック休暇データ
			importStockHoliday(importDto, csvList);
		} else if (TimeFileConst.CODE_IMPORT_TYPE_TIMELY_PAID_HOLIDAY.equals(importDto.getImportTable())) {
			// 時間単位有給休暇データ
			importTimelyHoliday(importDto, csvList);
		} else if (TimeFileConst.CODE_IMPORT_TYPE_TMD_HOLIDAY.equals(importDto.getImportTable())) {
			// 休暇データ
			importHoliday(importDto, csvList);
		}
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージ設定
		addInsertMessage();
	}
	
	/**
	 * 勤怠データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList   インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importAttendance(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		// 勤怠データ
		List<AttendanceDtoInterface> list = timeReference.importTable().getAttendanceList(importDto.getImportCode(),
				csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		AttendanceRegistBeanInterface regist = time.attendanceRegist();
		for (AttendanceDtoInterface dto : list) {
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 始業・終業必須チェック
			regist.checkTimeExist(dto);
			// 妥当性チェック
			regist.checkValidate(dto);
			// 申請の相関チェック
			regist.checkDraft(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフロー番号設定
			time.attendanceListRegist(dto.getWorkDate()).draft(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 勤怠データ登録
			regist.regist(dto);
		}
	}
	
	/**
	 * 勤怠集計データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList   インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importTotalTime(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		ApplicationReferenceBeanInterface application = timeReference.application();
		TimeSettingReferenceBeanInterface timeSetting = timeReference.timeSetting();
		CutoffReferenceBeanInterface cutoff = timeReference.cutoff();
		// 勤怠集計データ
		List<TotalTimeDataDtoInterface> list = timeReference.importTable().getTotalTimeList(importDto.getImportCode(),
				csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		for (TotalTimeDataDtoInterface dto : list) {
			// 勤怠集計データ登録
			time.totalTimeRegist().regist(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			Integer state = timeReference.totalTimeEmployeeTransaction().getCutoffState(dto.getPersonalId(),
					dto.getCalculationYear(), dto.getCalculationMonth());
			if (state != null && state.intValue() == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
				// 仮締の場合
				continue;
			}
			ApplicationDtoInterface applicationDto = application.findForPerson(dto.getPersonalId(),
					dto.getCalculationDate());
			application.chkExistApplication(applicationDto, dto.getCalculationDate());
			if (mospParams.hasErrorMessage()) {
				return;
			}
			TimeSettingDtoInterface timeSettingDto = timeSetting.getTimeSettingInfo(
					applicationDto.getWorkSettingCode(), dto.getCalculationDate());
			timeSetting.chkExistTimeSetting(timeSettingDto, dto.getCalculationDate());
			if (mospParams.hasErrorMessage()) {
				return;
			}
			CutoffDtoInterface cutoffDto = cutoff.getCutoffInfo(timeSettingDto.getCutoffCode(),
					dto.getCalculationDate());
			cutoff.chkExistCutoff(cutoffDto, dto.getCalculationDate());
			if (mospParams.hasErrorMessage()) {
				return;
			}
			List<String> personalIdlist = new ArrayList<String>();
			personalIdlist.add(dto.getPersonalId());
			time.totalTimeEmployeeTransactionRegist().draft(personalIdlist, dto.getCalculationYear(),
					dto.getCalculationMonth(), cutoffDto.getCutoffCode());
		}
	}
	
	/**
	 * 有給休暇データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList   インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importPaidHoliday(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		PaidHolidayDataRegistBeanInterface regist = time.paidHolidayDataRegist();
		// 有給休暇データ登録
		List<PaidHolidayDataDtoInterface> list = timeReference.importTable().getPaidHolidayList(
				importDto.getImportCode(), csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		for (PaidHolidayDataDtoInterface dto : list) {
			PaidHolidayDataDtoInterface paidHolidayDataDto = timeReference.paidHolidayData().findForKey(
					dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate());
			if (paidHolidayDataDto == null) {
				// 有給休暇データ登録
				regist.insert(dto);
			} else {
				// 有給休暇データ更新
				regist.update(dto);
			}
		}
	}
	
	/**
	 * ストック休暇データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList   インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importStockHoliday(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		StockHolidayDataRegistBeanInterface regist = time.stockHolidayDataRegist();
		// ストック休暇データ登録
		List<StockHolidayDataDtoInterface> list = timeReference.importTable().getStockHolidayList(
				importDto.getImportCode(), csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		for (StockHolidayDataDtoInterface dto : list) {
			StockHolidayDataDtoInterface stockHolidayDataDto = timeReference.stockHolidayData().findForKey(
					dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate());
			if (stockHolidayDataDto == null) {
				// ストック休暇データ登録
				regist.insert(dto);
			} else {
				// ストック休暇データ更新
				regist.update(dto);
			}
		}
	}
	
	/**
	 * 時間単位有給休暇データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList   インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importTimelyHoliday(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		// 時間単位有給休暇データ登録
		List<TimelyPaidHolidayDtoInterface> list = timeReference.importTable().getTimelyPaidHolidayList(
				importDto.getImportCode(), csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		for (TimelyPaidHolidayDtoInterface dto : list) {
			// 時間単位有給休暇データ登録
			time.timelyPaidHolidayRegist().insert(dto);
		}
	}
	
	/**
	 * 休暇データをインポートする。<br>
	 * @param importDto インポート情報
	 * @param csvList インポート対象データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void importHoliday(ImportDtoInterface importDto, List<String[]> csvList) throws MospException {
		HolidayDataRegistBeanInterface regist = time.holidayDataRegist();
		// 休暇データ登録
		List<HolidayDataDtoInterface> list = timeReference.importTable().getHolidayDataList(importDto.getImportCode(),
				csvList);
		if (mospParams.hasErrorMessage() || list.isEmpty()) {
			return;
		}
		for (HolidayDataDtoInterface dto : list) {
			HolidayDataDtoInterface holidayDataDto = timeReference.holidayData().findForKey(dto.getPersonalId(),
					dto.getActivateDate(), dto.getHolidayCode(), dto.getHolidayType());
			if (holidayDataDto == null) {
				// 休暇データ登録
				regist.insert(dto);
			} else {
				// 休暇データ更新
				regist.update(dto);
			}
		}
	}
	
}
