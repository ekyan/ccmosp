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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TimelyPaidHolidayRegistBeanInterface;
import jp.mosp.time.dao.settings.TimelyPaidHolidayDaoInterface;
import jp.mosp.time.dto.settings.TimelyPaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTimelyPaidHolidayDto;

/**
 * 時間単位有給休暇データ登録クラス。
 */
public class TimelyPaidHolidayRegistBean extends PlatformBean implements TimelyPaidHolidayRegistBeanInterface {
	
	/**
	 * 時間単位有給休暇データDAOクラス。<br>
	 */
	protected TimelyPaidHolidayDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TimelyPaidHolidayRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public TimelyPaidHolidayRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (TimelyPaidHolidayDaoInterface)createDao(TimelyPaidHolidayDaoInterface.class);
	}
	
	@Override
	public TimelyPaidHolidayDtoInterface getInitDto() {
		return new TmdTimelyPaidHolidayDto();
	}
	
	@Override
	public void insert(TimelyPaidHolidayDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTimelyPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TimelyPaidHolidayDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TimelyPaidHolidayDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
