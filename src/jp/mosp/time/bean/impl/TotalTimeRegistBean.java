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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalTimeDataDto;

/**
 * 勤怠集計データ登録クラス。
 */
public class TotalTimeRegistBean extends PlatformBean implements TotalTimeRegistBeanInterface {
	
	/**
	 * 勤怠集計データDAOクラス。<br>
	 */
	TotalTimeDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TotalTimeRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (TotalTimeDataDaoInterface)createDao(TotalTimeDataDaoInterface.class);
	}
	
	@Override
	public TotalTimeDataDtoInterface getInitDto() {
		return new TmdTotalTimeDataDto();
	}
	
	@Override
	public void insert(TotalTimeDataDtoInterface dto) throws MospException {
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
		dto.setTmdTotalTimeId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(TotalTimeDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdTotalTimeId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTotalTimeId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void regist(TotalTimeDataDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getPersonalId(), dto.getCalculationYear(), dto.getCalculationMonth()) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 更新
			update(dto);
		}
	}
	
	@Override
	public void delete(TotalTimeDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdTotalTimeId());
	}
	
	@Override
	public void delete(List<String> personalIdList, int calculationYear, int calculationMonth) throws MospException {
		for (String personalId : personalIdList) {
			// 勤怠集計データ取得
			TotalTimeDataDtoInterface dto = dao.findForKey(personalId, calculationYear, calculationMonth);
			// 勤怠集計データ確認
			if (dto == null) {
				// 勤怠集計データが無い場合は削除不要
				continue;
			}
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdTotalTimeId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TotalTimeDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getCalculationYear(), dto.getCalculationMonth()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(TotalTimeDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdTotalTimeId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(TotalTimeDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdTotalTimeId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TotalTimeDataDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
