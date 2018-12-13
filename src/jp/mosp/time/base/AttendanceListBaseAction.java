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
package jp.mosp.time.base;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧におけるActionの基本機能を提供する。<br>
 * <br>
 */
public abstract class AttendanceListBaseAction extends TimeAction {
	
	/**
	 * 勤怠一覧情報参照クラスから情報を取得し、VOのフィールドを設定する。<br>
	 * @param attendanceListReference 勤怠一覧情報参照クラス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setVoFields(AttendanceListReferenceBeanInterface attendanceListReference) throws MospException {
		// 個人ID取得(勤怠一覧情報参照クラスから)
		String personalId = attendanceListReference.getPersonalId();
		// 対象締期間最終日取得(勤怠一覧情報参照クラスから)
		Date lastDate = attendanceListReference.getLastDate();
		// 対象年月取得(勤怠一覧情報参照クラスから)
		int targetYear = attendanceListReference.getTargetYear();
		int targetMonth = attendanceListReference.getTargetMonth();
		// VOの年月フィールドを設定
		setVoYearMonthFields(targetYear, targetMonth);
		// VOの今月フィールドを設定
		setVoThisMonthFields(personalId);
		// VOの人事情報フィールドを設定
		setVoHumanFields(personalId, lastDate);
	}
	
	/**
	 * VOの年月フィールドを設定する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setVoYearMonthFields(int targetYear, int targetMonth) throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 対象年月をVOに設定
		vo.setPltSelectYear(String.valueOf(targetYear));
		vo.setPltSelectMonth(String.valueOf(targetMonth));
		// 年月プルダウン設定
		vo.setAryPltSelectYear(getYearArray(targetYear));
		vo.setAryPltSelectMonth(getMonthArray());
		// 表示対象年月取得(前月、次月用)
		Date current = MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams);
		// 前月、次月取得
		Date next = DateUtility.addMonth(current, 1);
		Date previous = DateUtility.addMonth(current, -1);
		// 前月、次月ボタンの年月を設定
		vo.setNextMonthYear(DateUtility.getYear(next));
		vo.setNextMonthMonth(DateUtility.getMonth(next));
		vo.setPrevMonthYear(DateUtility.getYear(previous));
		vo.setPrevMonthMonth(DateUtility.getMonth(previous));
	}
	
	/**
	 * VOの今月フィールドを設定する。<br>
	 * @param personalId 個人ID
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setVoThisMonthFields(String personalId) throws MospException {
		// VO取得
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		CutoffUtilBeanInterface cutoffUtil = timeReference().cutoffUtil();
		// 今月取得
		Date thisMonth = getSystemDate();
		// 今月ボタンの年月を設定
		vo.setThisMonthYear(DateUtility.getYear(thisMonth));
		vo.setThisMonthMonth(DateUtility.getMonth(thisMonth));
		TimeSettingDtoInterface timeSettingDto = cutoffUtil.getTimeSetting(personalId, thisMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		CutoffDtoInterface cutoffDto = cutoffUtil.getCutoff(timeSettingDto.getCutoffCode(), thisMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), thisMonth);
		vo.setThisMonthYear(DateUtility.getYear(cutoffMonth));
		vo.setThisMonthMonth(DateUtility.getMonth(cutoffMonth));
	}
	
	/**
	 * VOの人事情報フィールドを設定する。<br>
	 * @param personalId 対象年
	 * @param targetDate 対象月
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void setVoHumanFields(String personalId, Date targetDate) throws MospException {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 人事マスタ情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, targetDate);
		// 人事マスタ情報確認
		if (humanDto == null) {
			return;
		}
		// VOに個人IDを設定
		vo.setPersonalId(humanDto.getPersonalId());
		// VOに社員コードを設定
		vo.setLblEmployeeCode(humanDto.getEmployeeCode());
		// VOに氏名を設定
		vo.setLblEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
		// 所属情報取得及び設定
		vo.setLblSectionName(reference().section().getSectionName(humanDto.getSectionCode(), targetDate));
		if (reference().section().useDisplayName()) {
			// 所属表示名称を設定
			vo.setLblSectionName(reference().section().getSectionDisplay(humanDto.getSectionCode(), targetDate));
		}
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<AttendanceListDto> list) {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 設定項目準備
		String[] aryLblDate = new String[list.size()];
		String[] aryLblWeek = new String[list.size()];
		String[] aryWorkDayOfWeekStyle = new String[list.size()];
		String[] aryLblWorkType = new String[list.size()];
		String[] aryLblStartTime = new String[list.size()];
		String[] aryLblEndTime = new String[list.size()];
		String[] aryLblWorkTime = new String[list.size()];
		String[] aryLblRestTime = new String[list.size()];
		String[] aryLblRemark = new String[list.size()];
		// 設定項目作成
		for (int i = 0; i < list.size(); i++) {
			AttendanceListDto dto = list.get(i);
			aryLblDate[i] = dto.getWorkDateString();
			aryLblWeek[i] = dto.getWorkDayOfWeek();
			aryWorkDayOfWeekStyle[i] = dto.getWorkDayOfWeekStyle();
			aryLblWorkType[i] = dto.getWorkTypeAbbr();
			aryLblStartTime[i] = dto.getStartTimeString();
			aryLblEndTime[i] = dto.getEndTimeString();
			aryLblWorkTime[i] = dto.getWorkTimeString();
			aryLblRestTime[i] = dto.getRestTimeString();
			aryLblRemark[i] = MospUtility.concat(dto.getRemark(), dto.getTimeComment());
		}
		// VOに項目を設定
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblWeek(aryLblWeek);
		vo.setAryWorkDayOfWeekStyle(aryWorkDayOfWeekStyle);
		vo.setAryLblWorkType(aryLblWorkType);
		vo.setAryLblStartTime(aryLblStartTime);
		vo.setAryLblEndTime(aryLblEndTime);
		vo.setAryLblWorkTime(aryLblWorkTime);
		vo.setAryLblRestTime(aryLblRestTime);
		vo.setAryLblRemark(aryLblRemark);
		// 合計値取得確認
		if (list.isEmpty()) {
			return;
		}
		// 勤怠一覧情報リスト最終レコード取得
		AttendanceListDto dto = list.get(list.size() - 1);
		// 合計値設定
		vo.setLblTotalWork(dto.getWorkTimeTotalString());
		vo.setLblTotalRest(dto.getRestTimeTotalString());
		vo.setLblTimesWork(dto.getWorkDaysString());
		vo.setLblTimesPrescribedHoliday(dto.getPrescribedHolidaysString());
		vo.setLblTimesLegalHoliday(dto.getLegalHolidaysString());
		// チェックボックスリセット
		vo.setCkbSelect(new String[0]);
	}
	
}
