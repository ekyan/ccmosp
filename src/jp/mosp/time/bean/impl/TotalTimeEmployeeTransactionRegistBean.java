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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeEmployeeDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtTotalTimeEmployeeDto;

/**
 * 社員勤怠集計管理登録登録クラス。
 */
public class TotalTimeEmployeeTransactionRegistBean extends PlatformBean
		implements TotalTimeEmployeeTransactionRegistBeanInterface {
	
	/**
	 * 社員勤怠集計管理トランザクションDAOクラス。<br>
	 */
	TotalTimeEmployeeDaoInterface	dao;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	CutoffUtilBeanInterface			cutoffUtil;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeEmployeeTransactionRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public TotalTimeEmployeeTransactionRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (TotalTimeEmployeeDaoInterface)createDao(TotalTimeEmployeeDaoInterface.class);
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeDtoInterface getInitDto() {
		return new TmtTotalTimeEmployeeDto();
	}
	
	@Override
	public void draft(String personalId, int calculationYear, int calculationMonth, String cutoffCode,
			Date calculationDate) throws MospException {
		// 社員勤怠集計管理情報をDBから取得
		TotalTimeEmployeeDtoInterface dto = dao.findForKey(personalId, calculationYear, calculationMonth);
		// 社員勤怠集計管理情報が存在しないかを確認
		if (dto == null) {
			// 社員勤怠集計管理情報を準備(社員勤怠集計管理情報が存在しない場合)
			dto = getInitDto();
		} else {
			// 論理削除(社員勤怠集計管理情報が存在する場合)
			logicalDelete(dao, dto.getTmtTotalTimeEmployeeId());
		}
		// 社員勤怠集計管理情報に値を設定
		dto.setPersonalId(personalId);
		dto.setCalculationYear(calculationYear);
		dto.setCalculationMonth(calculationMonth);
		dto.setCutoffCode(cutoffCode);
		dto.setCalculationDate(calculationDate);
		// 締状態設定(仮締)
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtTotalTimeEmployeeId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void draft(List<String> personalIdList, int calculationYear, int calculationMonth, String cutoffCode)
			throws MospException {
		TotalTimeEmployeeDtoInterface dto = getInitDto();
		dto.setCalculationYear(calculationYear);
		dto.setCalculationMonth(calculationMonth);
		dto.setCutoffCode(cutoffCode);
		// 集計日設定
		dto.setCalculationDate(cutoffUtil.getCutoffCalculationDate(cutoffCode, calculationYear, calculationMonth));
		// 締状態設定(仮締)
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		for (String personalId : personalIdList) {
			dto.setPersonalId(personalId);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			TotalTimeEmployeeDtoInterface totalTimeEmployeeDto = dao.findForKey(dto.getPersonalId(),
					dto.getCalculationYear(), dto.getCalculationMonth());
			if (totalTimeEmployeeDto == null) {
				// 新規登録情報の検証
				checkInsert(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
			} else {
				// 履歴更新情報の検証
				checkUpdate(totalTimeEmployeeDto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// 論理削除
				logicalDelete(dao, totalTimeEmployeeDto.getTmtTotalTimeEmployeeId());
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmtTotalTimeEmployeeId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void draftRelease(List<String> personalIdList, int calculationYear, int calculationMonth, String cutoffCode)
			throws MospException {
		for (String personalId : personalIdList) {
			TotalTimeEmployeeDtoInterface dto = dao.findForKey(personalId, calculationYear, calculationMonth);
			dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT);
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
			logicalDelete(dao, dto.getTmtTotalTimeEmployeeId());
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmtTotalTimeEmployeeId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TotalTimeEmployeeDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getCalculationYear(), dto.getCalculationMonth()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(TotalTimeEmployeeDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtTotalTimeEmployeeId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	public void validate(TotalTimeEmployeeDtoInterface dto) {
		// TODO 妥当性確認
	}
}
