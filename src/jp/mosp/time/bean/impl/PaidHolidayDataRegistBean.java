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
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdPaidHolidayDataDto;
import jp.mosp.time.input.vo.HolidayRequestVo;

/**
 * 有給休暇データ雇用契約マスタ登録クラス。
 */
public class PaidHolidayDataRegistBean extends PlatformBean implements PaidHolidayDataRegistBeanInterface {
	
	/**
	 * 有給休暇データDAOクラス。<br>
	 */
	PaidHolidayDataDaoInterface								dao;
	
	/**
	 * 有給休暇トランザクション参照クラス。
	 */
	protected PaidHolidayTransactionReferenceBeanInterface	paidHolidayTransactionReference;
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayDataRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public PaidHolidayDataRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (PaidHolidayDataDaoInterface)createDao(PaidHolidayDataDaoInterface.class);
		paidHolidayTransactionReference = (PaidHolidayTransactionReferenceBeanInterface)createBean(PaidHolidayTransactionReferenceBeanInterface.class);
		holidayRequestReference = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataDtoInterface getInitDto() {
		return new TmdPaidHolidayDataDto();
	}
	
	@Override
	public void insert(PaidHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		PaidHolidayDataDtoInterface paidHolidayDataDto = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getAcquisitionDate());
		if (paidHolidayDataDto != null) {
			// DTO妥当性確認
			validate(paidHolidayDataDto);
			// 履歴更新情報の検証
			checkUpdate(paidHolidayDataDto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, paidHolidayDataDto.getTmdPaidHolidayId());
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PaidHolidayDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdPaidHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(PaidHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdPaidHolidayId());
	}
	
	@Override
	public void delete(String personalId, Date activateDate) throws MospException {
		List<PaidHolidayDataDtoInterface> list = dao.findForList(personalId, activateDate);
		for (PaidHolidayDataDtoInterface dto : list) {
			checkDelete((PaidHolidayDataDtoInterface)dao.findForKey(dto.getTmdPaidHolidayId(), true));
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdPaidHolidayId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	
	protected void checkUpdate(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdPaidHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmdPaidHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdPaidHolidayId());
	}
	
	@Override
	public void checkModify(PaidHolidayDataDtoInterface dto) throws MospException {
		Date endDate = dto.getLimitDate();
		List<PaidHolidayDataDtoInterface> list = dao.findForHistory(dto.getPersonalId(), dto.getAcquisitionDate());
		for (int i = 0; i < list.size(); i++) {
			PaidHolidayDataDtoInterface paidHolidayDataDto = list.get(i);
			if (!paidHolidayDataDto.getActivateDate().equals(dto.getActivateDate())) {
				// 有効日が異なる場合
				continue;
			}
			if (list.size() > i + 1) {
				endDate = addDay(list.get(i + 1).getActivateDate(), -1);
			}
			break;
		}
		if (dto.getActivateDate().after(endDate)) {
			// 有効日が終了日より後の場合は有効日を終了日とする
			endDate = dto.getActivateDate();
		}
		double givingDay = 0;
		int givingHour = 0;
		double cancelDay = 0;
		int cancelHour = 0;
		List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionList = paidHolidayTransactionReference
			.findForList(dto.getPersonalId(), dto.getAcquisitionDate(), dto.getActivateDate(), endDate);
		for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionList) {
			givingDay += paidHolidayTransactionDto.getGivingDay();
			givingHour += paidHolidayTransactionDto.getGivingHour();
			cancelDay += paidHolidayTransactionDto.getCancelDay();
			cancelHour += paidHolidayTransactionDto.getCancelHour();
		}
		Map<String, Object> map = holidayRequestReference.getRequestDayHour(dto.getPersonalId(),
				dto.getAcquisitionDate(), TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
				Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY), dto.getActivateDate(), endDate);
		double useDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
		int useHour = ((Integer)map.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
		double day = dto.getHoldDay() + givingDay - cancelDay - useDay;
		int hour = dto.getHoldHour() + givingHour - cancelHour - useHour;
		if (dto.getDenominatorDayHour() > 0) {
			// 0より大きい場合
			while (day >= 1 && hour < 0) {
				day--;
				hour += dto.getDenominatorDayHour();
			}
		}
		if (day >= 0 && hour >= 0) {
			return;
		}
		mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_CANCEL_CHECK,
				mospParams.getName(HolidayRequestVo.class.getName()), mospParams.getName("Insert"));
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayDataDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
