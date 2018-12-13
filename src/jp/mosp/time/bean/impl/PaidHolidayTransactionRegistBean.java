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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtPaidHolidayTransactionDto;

/**
 * 有給休暇手動付与登録クラス。
 */
public class PaidHolidayTransactionRegistBean extends PlatformBean implements PaidHolidayTransactionRegistBeanInterface {
	
	/**
	 * 有給休暇トランザクションDAOクラス。
	 */
	private PaidHolidayTransactionDaoInterface	dao;
	
	/**
	 * 有給休暇データDAO。
	 */
	private PaidHolidayDataDaoInterface			paidHolidayDataDao;
	
	/**
	 * 休暇申請参照。
	 */
	HolidayRequestReferenceBeanInterface		holidayRequest;
	
	/**
	 * 設定適用管理参照。
	 */
	private ApplicationReferenceBeanInterface	application;
	
	/**
	 * 勤怠設定参照。
	 */
	private TimeSettingReferenceBeanInterface	timeSetting;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayTransactionRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PaidHolidayTransactionRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (PaidHolidayTransactionDaoInterface)createDao(PaidHolidayTransactionDaoInterface.class);
		// 有給休暇データDAO取得
		paidHolidayDataDao = (PaidHolidayDataDaoInterface)createDao(PaidHolidayDataDaoInterface.class);
		// 休暇申請参照クラス取得
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		// 設定適用参照クラス取得
		application = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		// 勤怠設定参照クラス取得
		timeSetting = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayTransactionDtoInterface getInitDto() {
		return new TmtPaidHolidayTransactionDto();
	}
	
	@Override
	public void insert(PaidHolidayTransactionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		// 重複チェック
		checkOverlapping(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PaidHolidayTransactionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmtPaidHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayTransactionDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
		// 残数の確認
		checkRemain(dto);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(PaidHolidayTransactionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtPaidHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmtPaidHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 残数の確認
		checkRemain(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayTransactionDtoInterface dto) {
		// TODO 妥当性確認
	}
	
	/**
	 * 有給休暇の残数の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkRemain(PaidHolidayTransactionDtoInterface dto) throws MospException {
		int generalWorkHour = 8;
		ApplicationDtoInterface applicationDto = application.findForPerson(dto.getPersonalId(), dto.getActivateDate());
		if (applicationDto != null) {
			generalWorkHour = timeSetting
				.getGeneralWorkHour(applicationDto.getWorkSettingCode(), dto.getActivateDate());
		}
		double holdDay = 0;
		int holdHour = 0;
		double givingDay = 0;
		int givingHour = 0;
		double cancelDay = 0;
		int cancelHour = 0;
		double requestDay = 0;
		int requestHour = 0;
		double day = 0;
		int hour = 0;
		// 有給休暇データ取得
		PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataDao.findForInfo(dto.getPersonalId(),
				dto.getActivateDate(), dto.getAcquisitionDate());
		if (paidHolidayDataDto != null) {
			holdDay = paidHolidayDataDto.getHoldDay();
			holdHour = paidHolidayDataDto.getHoldHour();
		}
		// 有給休暇手動付与・破棄トランザクション取得
		List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = dao.findForList(dto.getPersonalId(),
				dto.getAcquisitionDate(), null, null);
		for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
			if (!paidHolidayTransactionDto.getActivateDate().equals(dto.getActivateDate())) {
				givingDay += paidHolidayTransactionDto.getGivingDay();
				givingHour += paidHolidayTransactionDto.getGivingHour();
				cancelDay += paidHolidayTransactionDto.getCancelDay();
				cancelHour += paidHolidayTransactionDto.getCancelHour();
			}
		}
		if (paidHolidayDataDto != null) {
			// 申請取得
			Map<String, Object> map = holidayRequest
				.getRequestDayHour(dto.getPersonalId(), dto.getAcquisitionDate(), Integer.parseInt(mospParams
					.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false)[0][0]), mospParams
					.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false)[0][0],
						paidHolidayDataDto.getActivateDate(), dto.getAcquisitionDate());
			requestDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
			requestHour = ((Integer)map.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
		}
		day = holdDay + givingDay - cancelDay - requestDay + dto.getGivingDay() - dto.getCancelDay();
		hour = holdHour + givingHour - cancelHour - requestHour + dto.getGivingHour() - dto.getCancelHour();
		while (hour < 0) {
			day--;
			hour += generalWorkHour;
		}
		if (day < 0) {
			String[] aryMeassage = { mospParams.getName("Remainder") + mospParams.getName("Num") };
			mospParams.addMessage(TimeMessageConst.MSG_GRANT_PERIOD_LESS, aryMeassage);
		}
	}
	
	@Override
	public void checkOverlapping(PaidHolidayTransactionDtoInterface dto) throws MospException {
		PaidHolidayTransactionDtoInterface paidHoliday = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getAcquisitionDate());
		if (paidHoliday != null) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_EXIST_WITH_PAY_VACATION_GRANT,
					DateUtility.getStringDate(dto.getActivateDate()),
					getHumanInfo(dto.getPersonalId(), dto.getActivateDate()).getEmployeeCode());
		}
	}
}
