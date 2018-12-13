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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdStockHolidayDto;

/**
 * ストック休暇データ登録クラス。
 */
public class StockHolidayDataRegistBean extends PlatformBean implements StockHolidayDataRegistBeanInterface {
	
	/**
	 * ストック休暇データDAOクラス。<br>
	 */
	StockHolidayDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayDataRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public StockHolidayDataRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (StockHolidayDataDaoInterface)createDao(StockHolidayDataDaoInterface.class);
	}
	
	@Override
	public StockHolidayDataDtoInterface getInitDto() {
		return new TmdStockHolidayDto();
	}
	
	@Override
	public void insert(StockHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		StockHolidayDataDtoInterface stockHolidayDataDto = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getAcquisitionDate());
		if (stockHolidayDataDto != null) {
			// DTO妥当性確認
			validate(stockHolidayDataDto);
			// 履歴更新情報の検証
			checkUpdate(stockHolidayDataDto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, stockHolidayDataDto.getTmdStockHolidayId());
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdStockHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(StockHolidayDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdStockHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdStockHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(StockHolidayDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdStockHolidayId());
	}
	
	@Override
	public void delete(String personalId, Date activateDate) throws MospException {
		List<StockHolidayDataDtoInterface> list = dao.findForList(personalId, activateDate);
		for (StockHolidayDataDtoInterface dto : list) {
			checkDelete((StockHolidayDataDtoInterface)dao.findForKey(dto.getTmdStockHolidayId(), true));
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdStockHolidayId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdStockHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmdStockHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdStockHolidayId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(StockHolidayDataDtoInterface dto) {
		// 名称取得
		String employeeCode = MospUtility.getCodeName("employee_code",
				mospParams.getProperties().getCodeArray(TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY, false));
		// 有効日
		String activateDate = MospUtility.getCodeName("activate_date",
				mospParams.getProperties().getCodeArray(TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY, false));
		// 取得日
		String acquisitionDate = MospUtility.getCodeName("acquisition_date",
				mospParams.getProperties().getCodeArray(TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY, false));
		// 期限日
		String limitDate = MospUtility.getCodeName("limit_date",
				mospParams.getProperties().getCodeArray(TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY, false));
		
		// 必須入力チェック
		InputCheckUtility.checkRequired(mospParams, dto.getPersonalId(), employeeCode);
		InputCheckUtility.checkRequired(mospParams, getStringDate(dto.getActivateDate()), activateDate);
		InputCheckUtility.checkRequired(mospParams, getStringDate(dto.getAcquisitionDate()), acquisitionDate);
		InputCheckUtility.checkRequired(mospParams, getStringDate(dto.getAcquisitionDate()), limitDate);
	}
	
}
