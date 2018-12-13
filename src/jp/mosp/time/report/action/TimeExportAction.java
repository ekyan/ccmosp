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
package jp.mosp.time.report.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.file.vo.TimeExportListVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * CSV出力を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_EXECUTION}
 * </li></ul>
 */
public class TimeExportAction extends TimeAction {
	
	/**
	 * 実行コマンド。<br>
	 * <br>
	 * エクスポートを実行する。<br>
	 * 実行時にエクスポートマスタが決定していない、対象ファイルが選択されていない場合は
	 * エラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String	CMD_EXECUTION	= "TM3315";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TimeExportAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TimeExportListVo();
	}
	
	@Override
	public void action() throws MospException {
		if (mospParams.getCommand().equals(CMD_EXECUTION)) {
			// 実行
			prepareVo();
			execution();
		}
	}
	
	/**
	 * エクスポートの実行を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void execution() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		ExportDtoInterface dto = reference().export().findForKey(vo.getRadSelect());
		if (dto == null) {
			// 検索結果無しメッセージ設定
			addNoSearchResultMessage();
			return;
		}
		int startYear = getInt(vo.getTxtStartYear());
		int startMonth = getInt(vo.getTxtStartMonth());
		int endYear = getInt(vo.getTxtEndYear());
		int endMonth = getInt(vo.getTxtEndMonth());
		if (TimeFileConst.CODE_EXPORT_TYPE_TMD_SUB_HOLIDAY.equals(dto.getExportTable())) {
			// 代休データ
			timeReference().subHolidayExport().export(vo.getRadSelect(), startYear, startMonth, endYear, endMonth,
					vo.getPltCutoff(), vo.getPltWorkPlace(), vo.getPltEmployment(), vo.getPltSection(),
					vo.getPltPosition());
			return;
		} else if (TimeFileConst.CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA.equals(dto.getExportTable())) {
			// 休暇取得データ
			timeReference().holidayRequestDataExport().export(vo.getRadSelect(), getInt(vo.getTxtStartYear()),
					getInt(vo.getTxtStartMonth()), getInt(vo.getTxtEndYear()), getInt(vo.getTxtEndMonth()),
					vo.getPltCutoff(), vo.getPltWorkPlace(), vo.getPltEmployment(), vo.getPltSection(),
					vo.getPltPosition());
			return;
		}
		// 検索クラス取得
		ExportTableReferenceBeanInterface exportTable = timeReference().exportTable();
		exportTable.setExportCode(vo.getRadSelect());
		exportTable.setStartYear(startYear);
		exportTable.setStartMonth(startMonth);
		exportTable.setEndYear(endYear);
		exportTable.setEndMonth(endMonth);
		exportTable.setCutoffCode(vo.getPltCutoff());
		exportTable.setWorkPlaceCode(vo.getPltWorkPlace());
		exportTable.setEmploymentCode(vo.getPltEmployment());
		exportTable.setSectionCode(vo.getPltSection());
		exportTable.setPositionCode(vo.getPltPosition());
		// CSVデータリスト取得
		List<String[]> csvDataList = exportTable.export();
		if (mospParams.hasErrorMessage()) {
			// エラー発生時はチェックボタンの選択状態を初期化する
			vo.setRadSelect("");
			return;
		}
		if (csvDataList.isEmpty()) {
			// 該当するエクスポート情報が存在しない
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Export"));
			sb.append(mospParams.getName("Information"));
			mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, sb.toString());
			// エラー発生時はチェックボタンの選択状態を初期化する
			vo.setRadSelect("");
			return;
		}
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
		// 送出ファイル名をMosP処理情報に設定
		setFileName();
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setFileName() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		ExportDtoInterface dto = reference().export().findForKey(vo.getRadSelect());
		String exportCode = "";
		String fileExtension = "";
		if (dto != null) {
			if (dto.getExportCode() != null) {
				exportCode = dto.getExportCode();
			}
			if (PlatformFileConst.FILE_TYPE_CSV.equals(dto.getType())) {
				// CSV
				fileExtension = ".csv";
			}
		}
		String hyphen = mospParams.getName("Hyphen");
		int startYear = getInt(vo.getTxtStartYear());
		int startMonth = getInt(vo.getTxtStartMonth());
		Date targetDate = MonthUtility.getYearMonthTargetDate(startYear, startMonth, mospParams);
		
		CutoffDtoInterface cutoffDto = timeReference().cutoff().getCutoffInfo(vo.getPltCutoff(), targetDate);
		timeReference().cutoff().chkExistCutoff(cutoffDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		Date startDate = TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), startYear, startMonth);
		Date endDate = TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), getInt(vo.getTxtEndYear()),
				getInt(vo.getTxtEndMonth()));
		// CSVファイル名作成
		StringBuffer sb = new StringBuffer();
		// エクスポートコード
		sb.append(exportCode);
		// ハイフン
		sb.append(hyphen);
		// 開始年
		sb.append(getStringYear(startDate));
		// 開始月
		sb.append(getStringMonth(startDate));
		// 開始日
		sb.append(getStringDay(startDate));
		// ハイフン
		sb.append(hyphen);
		// 終了年
		sb.append(getStringYear(endDate));
		// 終了月
		sb.append(getStringMonth(endDate));
		// 終了日
		sb.append(getStringDay(endDate));
		// 拡張子
		sb.append(fileExtension);
		// 送出ファイル名設定
		mospParams.setFileName(sb.toString());
	}
	
}
