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
package jp.mosp.platform.bean.human.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.human.HumanNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanNormalDto;

/**
 * 人事汎用通常情報登録クラス。
 */
public class HumanNormalRegistBean extends PlatformHumanBean implements HumanNormalRegistBeanInterface {
	
	/**
	 * 人事汎用情報DAOクラス。<br>
	 */
	HumanNormalDaoInterface		dao;
	
	/**
	 * 人事汎用管理機能クラス。<br>
	 */
	HumanGeneralBeanInterface	humanGeneral;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	HumanReferenceBeanInterface	humanReference;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanNormalRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HumanNormalRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// DAO準備
		dao = (HumanNormalDaoInterface)createDao(HumanNormalDaoInterface.class);
		humanGeneral = (HumanGeneralBeanInterface)createBean(HumanGeneralBeanInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public HumanNormalDtoInterface getInitDto() {
		return new PfaHumanNormalDto();
	}
	
	@Override
	public void regist(String division, String viewKey, String personalId) throws MospException {
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目名毎に処理
			for (String itemName : itemNames) {
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface dto = dao.findForInfo(itemName, personalId);
				// MosP処理情報から値取得
				String value = mospParams.getRequestParam(itemName);
				// 値がない場合
				if (value == null) {
					value = "";
				}
				// レコード識別ID確認
				if (dto == null) {
					// DTOに設定
					dto = getInitDto();
					dto.setPersonalId(personalId);
					dto.setHumanItemType(itemName);
					dto.setHumanItemValue(value);
					// 新規登録
					insert(dto);
				} else {
					// 値を設定
					dto.setHumanItemValue(value);
					// 更新
					update(dto);
				}
			}
		}
		
	}
	
	@Override
	public void regist(HumanNormalDtoInterface dto) throws MospException {
		HumanNormalDtoInterface normalDto = dao.findForInfo(dto.getHumanItemType(), dto.getPersonalId());
		if (normalDto != null) {
			normalDto.setHumanItemValue(dto.getHumanItemValue());
			update(normalDto);
		} else {
			insert(dto);
		}
	}
	
	/**
	 * 新規登録する。
	 * 役職などで登録する場合。
	 * @param dto 人事汎用履歴情報
	 * @throws MospException インスタンスの取得、或いはSQLの作成に失敗した場合
	 */
	@Override
	public void insert(HumanNormalDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanNormalId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(HumanNormalDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanNormalId());
	}
	
	@Override
	public void delete(String division, String viewKey, String personalId) throws MospException {
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目名毎に処理
			for (String itemName : itemNames) {
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface dto = dao.findForInfo(itemName, personalId);
				// レコード識別ID確認
				if (dto == null) {
					// 処理なし
					continue;
				} else {
					// 削除
					delete(dto);
				}
			}
		}
	}
	
	@Override
	public void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException {
		// 項目取得用
		List<String> list = new ArrayList<String>();
		
		// 人事汎用項目情報リストを取得
		for (String division : divisions) {
			List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
			
			//人事汎用項目毎に処理
			for (TableItemProperty tableItem : tableItemList) {
				// 人事汎用項目名を取得
				String[] itemNames = tableItem.getItemNames();
				for (String itemName : itemNames) {
					list.add(itemName);
				}
			}
			
		}
		if (list.isEmpty()) {
			return;
		}
		
		// 取得した項目名以外のデータを取得
		List<HumanNormalDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanNormalDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	@Override
	public void validate(HumanNormalDtoInterface dto, Integer row) throws MospException {
		// 必須確認(人事汎用項目)
		checkRequired(dto.getHumanItemType(), dto.getHumanItemType(), row);
		// 人事入社情報取得
		Date entranceDate = getEntranceDate(dto.getPersonalId());
		// 人事入社情報確認
		if (entranceDate == null) {
			// 社員が入社していない場合のメッセージを追加
			addEmployeeNotEnteredMessage();
			return;
		}
	}
	
	/**
	 * 更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	@Override
	public void update(HumanNormalDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanNormalId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanNormalId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(HumanNormalDtoInterface dto) throws MospException {
		// 人事マスタ取得
		List<HumanDtoInterface> humanList = humanReference.getHistory(dto.getPersonalId());
		// 人事マスタがない場合
		if (humanList.isEmpty()) {
			// エラーメッセージ設定
			humanGeneral.addNotHumanErrorMessage();
		}
	}
	
	/**
	 * 更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HumanNormalDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanNormalId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(HumanNormalDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanNormalId());
	}
	
	/**
	 * 退職情報が存在しているため入社情報が削除できない場合のメッセージを設定する。<br>
	 */
	protected void addDeleteEntranceFailedMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_CANT_DELETE_ENTRANCE);
	}
	
}
