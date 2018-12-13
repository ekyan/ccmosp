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
package jp.mosp.time.report.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.jasperreport.JasperReportUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.report.bean.AttendanceBookBeanInterface;

/**
 * 出勤簿作成クラス。
 */
public class AttendanceBookBean extends PlatformBean implements AttendanceBookBeanInterface {
	
	/**
	 * 勤怠一覧参照クラス。
	 */
	protected AttendanceListReferenceBeanInterface	attendanceListRefer;
	
	
	@Override
	public void initBean() throws MospException {
		// 勤怠一覧参照クラス取得
		attendanceListRefer = (AttendanceListReferenceBeanInterface)createBean(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	public void makeAttendanceBook(String personalId, Date targetDate) throws MospException {
		// 勤怠一覧(出勤簿)情報取得
		List<AttendanceListDto> attendanceList = attendanceListRefer.getActualList(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 出勤簿作成
		makeAttendanceBook(attendanceList);
	}
	
	@Override
	public void makeAttendanceBook(String personalId, int year, int month) throws MospException {
		// 勤怠一覧(出勤簿)情報取得
		List<AttendanceListDto> attendanceList = attendanceListRefer.getActualList(personalId, year, month);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 出勤簿作成
		makeAttendanceBook(attendanceList);
	}
	
	@Override
	public void makeAttendanceBooks(String[] personalIds, int year, int month) throws MospException {
		// 勤怠一覧情報リスト準備
		List<AttendanceListDto> list = new ArrayList<AttendanceListDto>();
		// 個人ID毎に出勤簿を作成
		for (String personalId : personalIds) {
			// 勤怠一覧(出勤簿)情報取得
			List<AttendanceListDto> attendanceList = attendanceListRefer.getActualList(personalId, year, month);
			// 処理結果確認
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 勤怠一覧情報リストに追加
			list.addAll(attendanceList);
		}
		// 出勤簿作成
		makeAttendanceBook(list);
	}
	
	/**
	 * 出勤簿を作成する。<br>
	 * @param attendanceList 勤怠一覧情報リスト
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void makeAttendanceBook(List<AttendanceListDto> attendanceList) throws MospException {
		// ヘッダ情報(帳票タイトル)付加
		for (AttendanceListDto dto : attendanceList) {
			dto.setTitle(getReportTitle());
		}
		// 帳票を作成し送出ファイルとして設定
		mospParams.setFile(JasperReportUtility.createJasperPrint(getTemplatePath(), attendanceList));
		// 送出ファイル名設定
		mospParams.setFileName(APP_REPORT_ATTENDANCE_BOOK);
	}
	
	/**
	 * 帳票のテンプレートパスを取得する。<br>
	 * @return 帳票のテンプレートパス
	 */
	protected String getTemplatePath() {
		return mospParams.getApplicationProperty(MospConst.APP_DOCBASE)
				+ mospParams.getApplicationProperty(APP_REPORT_ATTENDANCE_BOOK);
	}
	
	/**
	 * 帳票のタイトルを取得する。<br>
	 * @return 帳票のタイトル
	 */
	protected String getReportTitle() {
		return mospParams.getName("AttendanceBook");
	}
	
}
