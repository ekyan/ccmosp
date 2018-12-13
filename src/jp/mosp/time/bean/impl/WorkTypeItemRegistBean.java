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
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeItemDto;

/**
 * 勤務形態項目登録クラス。
 */
public class WorkTypeItemRegistBean extends PlatformBean implements WorkTypeItemRegistBeanInterface {
	
	/**
	 * 勤務形態項目マスタDAOクラス。<br>
	 */
	WorkTypeItemDaoInterface	dao;
	
	/**
	 * 勤務形態マスタDAOクラス。<br>
	 */
	WorkTypeDaoInterface		workTypeDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeItemRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public WorkTypeItemRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		workTypeDao = (WorkTypeDaoInterface)createDao(WorkTypeDaoInterface.class);
	}
	
	@Override
	public WorkTypeItemDtoInterface getInitDto() {
		return new TmmWorkTypeItemDto();
	}
	
	@Override
	public void insert(WorkTypeItemDtoInterface dto) throws MospException {
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
		dto.setTmmWorkTypeItemId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(WorkTypeItemDtoInterface dto) throws MospException {
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
		dto.setTmmWorkTypeItemId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(WorkTypeItemDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規追加項目の場合
		if (dto.getTmmWorkTypeItemId() == 0L) {
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmmWorkTypeItemId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmWorkTypeItemId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			for (String workTypeItemCode : CODES_WORK_TYPE_ITEM) {
				// 対象勤務形態項目における有効日の情報を取得
				WorkTypeItemDtoInterface dto = dao.findForKey(code, activateDate, workTypeItemCode);
				// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
				if (dto == null) {
					// 対象勤務形態項目における有効日以前で最新の情報を取得
					dto = dao.findForInfo(code, activateDate, workTypeItemCode);
					// 対象勤務形態項目情報確認
					if (dto == null) {
						// 新規追加項目の場合
						continue;
					}
					// DTOに有効日、無効フラグを設定
					dto.setActivateDate(activateDate);
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴追加情報の検証
					checkAdd(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴追加処理をしない
						continue;
					}
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				} else {
					// DTOに無効フラグを設定
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴更新情報の検証
					checkUpdate(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴更新処理をしない
						continue;
					}
					// 論理削除
					logicalDelete(dao, dto.getTmmWorkTypeItemId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				}
			}
		}
	}
	
	@Override
	public void delete(String workTypeCode, Date activateDate) throws MospException {
		for (String workTypeItemCode : CODES_WORK_TYPE_ITEM) {
			WorkTypeItemDtoInterface dto = dao.findForKey(workTypeCode, activateDate, workTypeItemCode);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(), dto.getWorkTypeItemCode()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (!needCheckTermForAdd(dto, list)) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmWorkTypeItemId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 *  削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 勤務形態コードを取得する。
	 * @param idArray レコード識別ID配列
	 * @return 勤務形態コード
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)workTypeDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getWorkTypeCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(WorkTypeItemDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
