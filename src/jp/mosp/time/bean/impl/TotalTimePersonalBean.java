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

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimePersonalBeanInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 個別仮締クラス。<br>
 */
public class TotalTimePersonalBean extends TimeBean implements TotalTimePersonalBeanInterface {
	
	/**
	 * 締日ユーティリティクラス。
	 */
	protected CutoffUtilBeanInterface							cutoffUtil;
	
	/**
	 * 勤怠集計クラス。<br>
	 */
	protected TotalTimeCalcBeanInterface						calc;
	
	/**
	 * 社員勤怠集計管理登録クラス。<br>
	 */
	protected TotalTimeEmployeeTransactionRegistBeanInterface	regist;
	
	
	@Override
	public void initBean() throws MospException {
		// Bean準備
		cutoffUtil = (CutoffUtilBeanInterface)createBean(CutoffUtilBeanInterface.class);
		calc = (TotalTimeCalcBeanInterface)createBean(TotalTimeCalcBeanInterface.class);
		regist = (TotalTimeEmployeeTransactionRegistBeanInterface)createBean(TotalTimeEmployeeTransactionRegistBeanInterface.class);
	}
	
	@Override
	public void total(String personalId, int calculationYear, int calculationMonth) throws MospException {
		// 締日情報取得
		CutoffDtoInterface cutoff = cutoffUtil.getCutoffForPersonalId(personalId, calculationYear, calculationMonth);
		// 処理結果確認(締日情報の取得に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 集計クラスに計算情報を設定
		calc.setCalculationInfo(calculationYear, calculationMonth, cutoff.getCutoffCode());
		// 処理結果確認(集計クラス計算情報設定に失敗した場合)
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠集計処理
		List<CutoffErrorListDtoInterface> errorList = calc.calc(personalId);
		// エラーリストの内容をMosP処理情報(エラーメッセージ)に設定
		for (CutoffErrorListDtoInterface dto : errorList) {
			TimeMessageUtility.addErrorCutoff(mospParams, dto);
		}
		// 処理結果確認
		if (mospParams.hasErrorMessage() || errorList.isEmpty() == false) {
			return;
		}
		// 未締確認
		cutoffUtil.checkTighten(personalId, calculationYear, calculationMonth);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 社員勤怠集計管理登録準備
		List<String> personalIdList = new ArrayList<String>();
		personalIdList.add(personalId);
		// 社員勤怠集計管理に登録(仮締)
		regist.draft(personalIdList, calculationYear, calculationMonth, cutoff.getCutoffCode());
	}
}
