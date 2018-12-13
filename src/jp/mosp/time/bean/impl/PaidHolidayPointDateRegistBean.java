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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayPointDateRegistBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayPointDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayPointDateDto;

/**
 * 有給休暇自動付与(基準日)登録クラス。
 */
public class PaidHolidayPointDateRegistBean extends PlatformBean implements PaidHolidayPointDateRegistBeanInterface {
	
	/**
	 * 有給休暇自動付与(基準日)DAOクラス。<br>
	 */
	PaidHolidayPointDateDaoInterface	dao;
	
	/**
	 * 有給休暇DAOクラス。<br>
	 */
	PaidHolidayDaoInterface				paidHolidayDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayPointDateRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public PaidHolidayPointDateRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (PaidHolidayPointDateDaoInterface)createDao(PaidHolidayPointDateDaoInterface.class);
		paidHolidayDao = (PaidHolidayDaoInterface)createDao(PaidHolidayDaoInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateDtoInterface getInitDto() {
		return new TmmPaidHolidayPointDateDto();
	}
	
	@Override
	public void insert(PaidHolidayPointDateDtoInterface dto) throws MospException {
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
		dto.setTmmPaidHolidayPointDateId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(PaidHolidayPointDateDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmPaidHolidayPointDateId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PaidHolidayPointDateDtoInterface dto) throws MospException {
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
//		logicalDelete(dao, dto.getTmmPaidHolidayPointDateId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmPaidHolidayPointDateId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) {
		// 処理なし
	}
	
	@Override
	public void delete(String paidHolidayCode, Date activateDate) throws MospException {
		List<PaidHolidayPointDateDtoInterface> list = dao.findForList(paidHolidayCode, activateDate);
		for (PaidHolidayPointDateDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 削除確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmPaidHolidayPointDateId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayPointDateDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPaidHolidayCode(), dto.getTimesPointDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(PaidHolidayPointDateDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPaidHolidayCode(), dto.getActivateDate(), dto.getTimesPointDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(PaidHolidayPointDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
//		checkExclusive(dao, dto.getTmmPaidHolidayPointDateId());
		// 無効フラグ確認
//		if (isDtoActivate(dto)) {
//			return;
//		}
		// 更新元データの無効フラグ確認
//		if (!isDtoActivate(dao.findForKey(dto.getTmmPaidHolidayPointDateId(), true))) {
		// 更新元データが更新前から無効であれば無効期間は発生しない
//			return;
//		}
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPaidHolidayCode(), dto.getActivateDate(), dto.getTimesPointDate()));
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PaidHolidayPointDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmPaidHolidayPointDateId());
	}
	
	/**
	 * 有休コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 有休コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)paidHolidayDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getPaidHolidayCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayPointDateDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
