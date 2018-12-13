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
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayDataDto;

/**
 * 休暇データ登録クラス。
 */
public class HolidayDataRegistBean extends PlatformBean implements HolidayDataRegistBeanInterface {
	
	/**
	 * 休暇データDAOクラス。<br>
	 */
	HolidayDataDaoInterface					dao;
	
	/**
	 * 休暇申請参照。
	 */
	HolidayRequestReferenceBeanInterface	holidayRequest;
	
	/**
	 * 休暇種別管理参照。
	 */
	HolidayReferenceBeanInterface			holiday;
	
	/**
	 * 人事情報参照インターフェース
	 */
	HumanReferenceBeanInterface				humanReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayDataRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HolidayDataRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		// 休暇申請参照クラス取得
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
		
		holiday = (HolidayReferenceBeanInterface)createBean(HolidayReferenceBeanInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayDataDtoInterface getInitDto() {
		return new TmdHolidayDataDto();
	}
	
	@Override
	public void insert(HolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		checkRegister(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		checkRegister(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(HolidayDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		HolidayDataDtoInterface holidayDataDto = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getHolidayCode(), dto.getHolidayType());
		if (holidayDataDto != null) {
			// 対象DTOが存在する場合
			// 対象社員情報取得
			HumanDtoInterface humanDto = humanReference.getHumanInfo(dto.getPersonalId(), dto.getActivateDate());
			// 対象休暇情報取得
			HolidayDtoInterface holidayDto = holiday.getHolidayInfo(dto.getHolidayCode(), dto.getActivateDate(),
					dto.getHolidayType());
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_DUPLICATE, holidayDto.getHolidayName(),
					humanDto.getEmployeeCode());
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmdHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 残日数のチェック
		Map<String, Object> map = holidayRequest.getRequestDayHour(dto.getPersonalId(), dto.getActivateDate(),
				dto.getHolidayType(), dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayLimitDate());
		if (dto.getGivingDay() - dto.getCancelDay() < ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue()) {
			String[] aryMeassage = { mospParams.getName("Giving", "Days") };
			mospParams.addMessage(TimeMessageConst.MSG_GRANT_PERIOD_LESS, aryMeassage);
		}
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(HolidayDataDtoInterface dto) {
		// 現在処理無し
	}
	
	/**
	 * @param dto 	対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkRegister(HolidayDataDtoInterface dto) throws MospException {
		HolidayDtoInterface holidayDto = holiday.getHolidayInfo(dto.getHolidayCode(), dto.getActivateDate(),
				dto.getHolidayType());
		if (null != holidayDto) {
			// 付与日数
//			checkGivingDay(dto, holidayDto.getNoLimit());
			// 取得期限
			checkHolidayLimit(dto, holidayDto.getNoLimit());
		}
	}
	
	/**
	 * 付与日数の年月に0が設定されている場合
	 * @param dto 	対象DTO
	 * @param noLinit 付与日数無制限
	 */
	protected void checkGivingDay(HolidayDataDtoInterface dto, int noLinit) {
		// 付与日数無制限にチェックを入れていない場合
		if (0 == noLinit && 0 == Double.compare(dto.getGivingDay(), 0.0)) {
			String mes1 = mospParams.getName("Giving", "Days");
			String mes2 = mes1 + mospParams.getName("Is", "No0", "Dot", "No5", "Over");
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_ITEM_ZERO, mes1, mes2);
		}
	}
	
	/**
	 * 取得期限の年月に0が設定されている場合
	 * @param dto 	対象DTO
	 * @param noLinit 付与日数無制限
	 */
	protected void checkHolidayLimit(HolidayDataDtoInterface dto, int noLinit) {
		// 付与日数無制限にチェックを入れていない場合
		if (0 == noLinit && 0 == dto.getHolidayLimitDay() && 0 == dto.getHolidayLimitMonth()) {
			String mes1 = mospParams.getName("Acquisition", "TimeLimit");
			String mes2 = mes1 + mospParams.getName("Is", "No1", "Day", "Over");
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_ITEM_ZERO, mes1, mes2);
		}
	}
	
	@Override
	public void delete(HolidayDataDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayId());
	}
	
}
