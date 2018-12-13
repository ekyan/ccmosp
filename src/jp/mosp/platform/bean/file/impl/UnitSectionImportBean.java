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
package jp.mosp.platform.bean.file.impl;

import java.sql.Connection;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.impl.PfmApprovalUnitDao;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * ユニット情報(所属)インポートクラス。<br>
 */
public class UnitSectionImportBean extends UnitImportBean {
	
	/**
	 * {@link UnitImportBean#UnitImportBean()}を実行する。<br>
	 */
	public UnitSectionImportBean() {
		super();
	}
	
	/**
	 * {@link UnitImportBean#UnitImportBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public UnitSectionImportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	/**
	 * ユニット区分は所属で設定する。<br>
	 */
	@Override
	protected ApprovalUnitDtoInterface getUnitDto(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		// 継承基のメソッドを実行
		ApprovalUnitDtoInterface dto = super.getUnitDto(fieldList, data);
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String approverSectionCode = getFieldValue(PfmApprovalUnitDao.COL_APPROVER_SECTION_CODE, fieldList, data);
		String approverPositionCode = getFieldValue(PfmApprovalUnitDao.COL_APPROVER_POSITION_CODE, fieldList, data);
		// ユニット区分設定
		dto.setUnitType(PlatformConst.UNIT_TYPE_SECTION);
		// 承認者所属及び職位設定
		dto.setApproverSectionCode(approverSectionCode);
		dto.setApproverPositionCode(approverPositionCode);
		// 承認者個人ID設定(空白)
		dto.setApproverPersonalId("");
		return dto;
	}
	
}
