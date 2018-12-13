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
package jp.mosp.platform.bean.system.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタ参照クラス。
 */
public class NamingReferenceBean extends PlatformBean implements NamingReferenceBeanInterface {
	
	/**
	 * 名称区分マスタDAO。
	 */
	protected NamingDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public NamingReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected NamingReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (NamingDaoInterface)createDao(NamingDaoInterface.class);
	}
	
	@Override
	public List<NamingDtoInterface> getNamingItemHistory(String namingType, String namingItemCode) throws MospException {
		return dao.findForHistory(namingType, namingItemCode);
	}
	
	@Override
	public NamingDtoInterface getNamingItemInfo(String namingType, String namingItemCode, Date targetDate)
			throws MospException {
		return dao.findForInfo(namingType, namingItemCode, targetDate);
	}
	
	@Override
	public String getNamingItemName(String namingType, String namingItemCode, Date targetDate) throws MospException {
		NamingDtoInterface dto = getNamingItemInfo(namingType, namingItemCode, targetDate);
		if (dto == null) {
			if (namingItemCode != null) {
				return namingItemCode;
			}
			return "";
		}
		return dto.getNamingItemName();
	}
	
	@Override
	public String getNamingItemAbbr(String namingType, String namingItemCode, Date targetDate) throws MospException {
		NamingDtoInterface dto = getNamingItemInfo(namingType, namingItemCode, targetDate);
		if (dto == null) {
			return namingItemCode;
		}
		return dto.getNamingItemAbbr();
	}
	
	@Override
	public NamingDtoInterface findForKey(String namingType, String namingItemCode, Date activateDate)
			throws MospException {
		return dao.findForKey(namingType, namingItemCode, activateDate);
	}
	
	@Override
	public String[][] getCodedSelectArray(String namingType, Date targetDate, boolean needBlank) throws MospException {
		// 一覧取得
		List<NamingDtoInterface> list = dao.findForList(namingType, targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// コードの最大文字数確認
		int codeLength = 0;
		for (NamingDtoInterface dto : list) {
			if (dto.getNamingItemCode().length() > codeLength) {
				codeLength = dto.getNamingItemCode().length();
			}
		}
		// 配列作成
		for (NamingDtoInterface dto : list) {
			array[idx][0] = dto.getNamingItemCode();
			array[idx][1] = getCodedName(dto.getNamingItemCode(), dto.getNamingItemName(), codeLength);
			idx++;
		}
		return array;
	}
	
}
