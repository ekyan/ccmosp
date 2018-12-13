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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 締日ユーティリティクラス。<br>
 *
 */
public class CutoffUtilBean extends TimeApplicationBean implements CutoffUtilBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	HumanSearchBeanInterface							humanSearch;
	
	/**
	 * 社員勤怠集計管理参照クラス。
	 */
	TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeRefer;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public CutoffUtilBean() {
		super();
	}
	
	/**
	 * {@link TimeBean#TimeBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected CutoffUtilBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のメソッドを実施
		super.initBean();
		// 各種クラスの取得
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		totalTimeEmployeeRefer = (TotalTimeEmployeeTransactionReferenceBeanInterface)createBean(
				TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		timeMaster = (TimeMasterBeanInterface)createBean(TimeMasterBeanInterface.class);
	}
	
	@Override
	public Set<String> getCutoffPersonalIdSet(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 個人IDセットの準備
		Set<String> idSet = new HashSet<String>();
		// 年月を指定して締日情報を取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return idSet;
		}
		// 締日を取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象年月における締期間の基準日及び初日、最終日取得
		Date cutoffTermTargetDate = TimeUtility.getCutoffTermTargetDate(cutoffDate, targetYear, targetMonth);
		Date firstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth);
		Date lastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth);
		// 有効な個人IDセット取得(対象日は締期間の基準日)
		Map<String, HumanDtoInterface> activateMap = getActivatePersonalIdMap(cutoffTermTargetDate, firstDate,
				lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : activateMap.values()) {
			// 設定適用エンティティを取得
			ApplicationEntity entity = timeMaster.getApplicationEntity(humanDto, cutoffTermTargetDate);
			// 締日が同じ場合
			if (entity.getCutoffCode().equals(cutoffCode)) {
				// 有効な個人IDセットに
				idSet.add(humanDto.getPersonalId());
			}
		}
		// 個人IDセット確認
		if (idSet.isEmpty()) {
			// 登録失敗メッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Employee"));
		}
		return idSet;
	}
	
	@Override
	public List<HumanDtoInterface> getCutoffHumanDto(String cutoffCode, int targetYear, int targetMonth)
			throws MospException {
		// 人事情報リスト準備
		List<HumanDtoInterface> humanList = new ArrayList<HumanDtoInterface>();
		
		// 年月を指定して締日情報を取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return humanList;
		}
		// 締日を取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象年月における締期間の基準日及び初日、最終日取得
		Date cutoffTermTargetDate = TimeUtility.getCutoffTermTargetDate(cutoffDate, targetYear, targetMonth);
		Date firstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth);
		Date lastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth);
		// 有効な個人IDセット取得(対象日は締期間の基準日)
		Map<String, HumanDtoInterface> activateMap = getActivatePersonalIdMap(cutoffTermTargetDate, firstDate,
				lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : activateMap.values()) {
			// 設定適用エンティティを取得
			ApplicationEntity entity = timeMaster.getApplicationEntity(humanDto, cutoffTermTargetDate);
			// 締日が同じ場合
			if (entity.getCutoffCode().equals(cutoffCode)) {
				// 有効な人事情報追加
				humanList.add(humanDto);
			}
		}
		// 人事情報リスト確認
		if (humanList.isEmpty()) {
			// 登録失敗メッセージ設定
			mospParams.addErrorMessage(PlatformMessageConst.MSG_NO_ITEM, mospParams.getName("Employee"));
		}
		return humanList;
	}
	
	@Override
	public CutoffDtoInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 年月指定時の基準日を取得
		Date yearMonthTargetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 締日コードと対象日から締日マスタを取得 
		CutoffDtoInterface cutoffDto = timeMaster.getCutoff(cutoffCode, yearMonthTargetDate);
		// 確認
		cutoffRefer.chkExistCutoff(cutoffDto, yearMonthTargetDate);
		return cutoffDto;
	}
	
	@Override
	public CutoffDtoInterface getCutoff(String cutoffCode, Date targetDate) throws MospException {
		// 締日コードと対象日から締日マスタを取得 
		CutoffDtoInterface cutoffDto = timeMaster.getCutoff(cutoffCode, targetDate);
		// 確認
		cutoffRefer.chkExistCutoff(cutoffDto, targetDate);
		return cutoffDto;
	}
	
	@Override
	public CutoffDtoInterface getCutoffForPersonalId(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 対象個人ID及び対象日付(年月指定時の基準日)で勤怠設定情報群を取得し設定
		setCutoffSettings(personalId, MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		return cutoffDto;
	}
	
	@Override
	public CutoffDtoInterface getCutoffForPersonalId(String personalId, Date targetDate) throws MospException {
		// 対象個人ID及び対象日付(年月指定時の基準日)で勤怠設定情報群を取得し設定
		setCutoffSettings(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		return cutoffDto;
	}
	
	@Override
	public CutoffDtoInterface getCutoffForApplication(ApplicationDtoInterface dto, Date targetDate)
			throws MospException {
		// 対象日付における勤怠設定情報を取得
		getTimeSetting(dto, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象日付における締日情報を取得
		cutoffDto = cutoffRefer.getCutoffInfo(timeSettingDto.getCutoffCode(), targetDate);
		// 締日情報確認
		cutoffRefer.chkExistCutoff(cutoffDto, targetDate);
		return cutoffDto;
	}
	
	@Override
	public Date getCutoffCalculationDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日コードと対象日から締日マスタを取得 
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象日付及び締日から集計日を取得
		return TimeUtility.getCutoffCalculationDate(cutoffDate, targetYear, targetMonth);
	}
	
	@Override
	public Date getCutoffTermTargetDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日コードと対象日から締日マスタを取得 
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象日付及び締日から基準日を取得
		return TimeUtility.getCutoffTermTargetDate(cutoffDate, targetYear, targetMonth);
	}
	
	@Override
	public boolean checkTighten(String personalId, Date targetDate, String targetName) throws MospException {
		// 対象個人ID及び対象日付から対象年月日が含まれる締月を取得
		Date cutoffDate = getCutoffMonth(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// 年月取得
		int year = DateUtility.getYear(cutoffDate);
		int month = DateUtility.getMonth(cutoffDate);
		// 締状態確認
		boolean isNotTighten = isNotTighten(personalId, year, month);
		// 未締でない場合
		if (isNotTighten == false) {
			// メッセージ追加
			addMonthlyTreatmentErrorMessage(year, month, targetName);
		}
		return isNotTighten;
	}
	
	@Override
	public boolean checkTighten(String personalId, int targetYear, int targetMonth) throws MospException {
		// 締状態確認
		boolean isNotTighten = isNotTighten(personalId, targetYear, targetMonth);
		// 未締でない場合
		if (isNotTighten == false) {
			// メッセージ追加
			TimeMessageUtility.addErrorTheMonthIsTighten(mospParams, targetYear, targetMonth);
		}
		return isNotTighten;
	}
	
	@Override
	public boolean isNotTighten(String personalId, Date targetDate) throws MospException {
		// 対象個人ID及び対象日付で勤怠設定情報群を取得し設定
		if (hasCutoffSettings(personalId, targetDate) == false) {
			return false;
		}
		// 対象日付及び締日から対象年月日が含まれる締月を取得
		Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), targetDate);
		// 締状態確認
		return isNotTighten(personalId, DateUtility.getYear(cutoffMonth), DateUtility.getMonth(cutoffMonth));
	}
	
	@Override
	public boolean isNotTighten(String personalId, int targetYear, int targetMonth) throws MospException {
		// 社員勤怠集計管理から締状態を取得
		Integer state = totalTimeEmployeeRefer.getCutoffState(personalId, targetYear, targetMonth);
		// 締状態確認
		if (state == null || state == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
			return true;
		}
		// 未締でない場合
		return false;
	}
	
	@Override
	public Date getCutoffFirstDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		return TimeUtility.getCutoffFirstDate(cutoffDto.getCutoffDate(), targetYear, targetMonth);
	}
	
	@Override
	public Date getCutoffLastDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		return TimeUtility.getCutoffLastDate(cutoffDto.getCutoffDate(), targetYear, targetMonth);
	}
	
	@Override
	public Date getCutoffMonth(String personalId, Date targetDate) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoffDto = getCutoffForPersonalId(personalId, targetDate);
		// 締日情報が存在しない場合
		if (cutoffDto == null) {
			// 日付指定時の基準年月を取得
			return MonthUtility.getTargetYearMonth(targetDate, mospParams);
		}
		// 対象日付が含まれる締月を取得
		return TimeUtility.getCutoffMonth(cutoffDto.getCutoffDate(), targetDate);
	}
	
	/**
	 * 有効な個人IDセットを取得する。<br>
	 * @param targetDate 対象日付
	 * @param staDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 有効な個人IDセット
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected Map<String, HumanDtoInterface> getActivatePersonalIdMap(Date targetDate, Date staDate, Date endDate)
			throws MospException {
		// 条件の設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setStartDate(staDate);
		humanSearch.setEndDate(endDate);
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 個人IDセット取得
		return humanSearch.getHumanDtoMap();
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSetting(String personalId, Date targetDate) throws MospException {
		// 対象個人ID及び対象日付で勤怠設定情報を取得
		setTimeSettings(personalId, targetDate);
		return timeSettingDto;
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSetting(ApplicationDtoInterface dto, Date targetDate) throws MospException {
		// 設定適用情報を設定
		applicationDto = dto;
		// 設定適用情報確認
		applicationRefer.chkExistApplication(applicationDto, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象日付における勤怠設定情報を取得
		timeSettingDto = timeSettingRefer.getTimeSettingInfo(applicationDto.getWorkSettingCode(), targetDate);
		// 勤怠設定情報確認
		timeSettingRefer.chkExistTimeSetting(timeSettingDto, targetDate);
		return timeSettingDto;
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSettingNoMessage(String personalId, Date targetDate) throws MospException {
		// 設定適用取得及び確認
		if (hasApplicationSettings(personalId, targetDate) == false) {
			return null;
		}
		// 勤怠設定取得
		if (hasTimeSettings(personalId, targetDate) == false) {
			return null;
		}
		// 勤怠設定取得
		return timeSettingDto;
	}
	
	@Override
	public int getNoApproval(String cutoffCode, Date targetDate) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetDate);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 未承認仮締取得
		return cutoffDto.getNoApproval();
	}
	
	@Override
	public int getNoApproval(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoffDto = getCutoff(cutoffCode, targetYear, targetMonth);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 未承認仮締取得
		return cutoffDto.getNoApproval();
	}
	
}
