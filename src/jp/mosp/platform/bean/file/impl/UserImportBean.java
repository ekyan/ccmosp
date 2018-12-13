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

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.bean.file.UserImportBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.impl.HumanRegistBean;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.bean.system.impl.UserMasterRegistBean;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザマスタインポートクラス。
 */
public class UserImportBean extends UserMasterRegistBean implements UserImportBeanInterface {
	
	/**
	 * ユーザ情報参照クラス。<br>
	 */
	protected UserMasterReferenceBeanInterface	userMasterRefer;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface		humanRefer;
	
	/**
	 * ユーザパスワード情報登録クラス。<br>
	 */
	protected UserPasswordRegistBeanInterface	userPasswordRegist;
	
	/**
	 * ユーザ情報リスト。<br>
	 */
	protected List<UserMasterDtoInterface>		userList;
	
	
	/**
	 * {@link HumanRegistBean#HumanRegistBean()}を実行する。<br>
	 */
	public UserImportBean() {
		super();
	}
	
	/**
	 * {@link HumanRegistBean#HumanRegistBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public UserImportBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// Bean準備
		userMasterRefer = (UserMasterReferenceBeanInterface)createBean(UserMasterReferenceBeanInterface.class);
		humanRefer = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		userPasswordRegist = (UserPasswordRegistBeanInterface)createBean(UserPasswordRegistBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// 登録情報リストから人事マスタ情報に変換
		getTargetLists(importDto, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 人事マスタ情報毎に登録
		for (int i = 0; i < userList.size(); i++) {
			// ユーザ情報登録
			registUserMasterDto(userList.get(i));
		}
		// 登録件数取得
		return userList.size();
	}
	
	/**
	 * 登録対象リスト群を取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストからユーザマスタ情報リストに変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList  登録情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getTargetLists(ImportDtoInterface importDto, List<String[]> dataList) throws MospException {
		// ユーザ情報リスト準備
		userList = new ArrayList<UserMasterDtoInterface>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// ユーザ情報取得及び確認
			UserMasterDtoInterface userMasterDto = getUserMasterDto(fieldList, data, i);
			if (userMasterDto != null) {
				// ユーザ情報リストに追加
				userList.add(userMasterDto);
			} else {
				// ユーザ情報が取得できなかった場合
				continue;
			}
		}
	}
	
	/**
	 * ユーザ情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストからユーザ情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return ユーザ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected UserMasterDtoInterface getUserMasterDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String userId = getFieldValue(PlatformFileConst.FIELD_USER_ID, fieldList, data);
		Date activateDate = getDateFieldValue(PlatformFileConst.FIELD_ACTIVATE_DATE, fieldList, data);
		String employeeCode = getFieldValue(PlatformFileConst.FIELD_EMPLOYEE_CODE, fieldList, data);
		String roleCode = getFieldValue(PlatformFileConst.FIELD_USER_ROLE_CODE, fieldList, data);
		// ユーザID確認
		if (userId == null || userId.isEmpty()) {
			// エラーメッセージ追加
			addRequiredErrorMessage(getNameUserId(), Integer.valueOf(row));
			return null;
		}
		// 有効日確認
		if (activateDate == null) {
			// エラーメッセージ追加
			addRequiredErrorMessage(getNameActivateDate(), Integer.valueOf(row));
			return null;
		}
		// ユーザ情報準備
		UserMasterDtoInterface userMaster = userMasterRefer.getUserInfo(userId, activateDate);
		// ユーザ情報確認
		if (userMaster == null) {
			userMaster = getInitDto();
		}
		// ユーザ情報に登録情報の内容を設定
		userMaster.setUserId(userId);
		userMaster.setActivateDate(activateDate);
		if (roleCode != null) {
			userMaster.setRoleCode(roleCode);
		} else if (userMaster.getRoleCode() == null) {
			userMaster.setRoleCode("");
		}
		// 社員コード確認
		if (employeeCode != null) {
			// 個人ID取得
			String personalId = humanRefer.getPersonalId(employeeCode, activateDate);
			// 個人ID確認
			if (userMaster.getPersonalId() != null && userMaster.getPersonalId().equals(personalId) == false) {
				// エラーメッセージ追加
				addUserIdDuplicateMessage(userId);
				return null;
			}
			// 個人ID設定
			userMaster.setPersonalId(personalId);
		}
		// 入力チェック
		validate(userMaster, Integer.valueOf(row));
		return userMaster;
	}
	
	/**
	 * ユーザ情報を登録する。<br>
	 * @param userMaster ユーザ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registUserMasterDto(UserMasterDtoInterface userMaster) throws MospException {
		// ユーザ情報確認
		if (userMaster == null) {
			return;
		}
		// ユーザ情報取得及び確認
		List<UserMasterDtoInterface> list = userMasterRefer.getUserHistory(userMaster.getUserId());
		if (list.isEmpty()) {
			// 新規登録
			insert(userMaster);
			// ユーザパスワード情報作成
			UserPasswordDtoInterface userPasswordDto = userPasswordRegist.getInitDto();
			// DTOの値をVOに設定
			userPasswordDto.setUserId(userMaster.getUserId());
			userPasswordDto.setPassword(SeUtility.encrypt(SeUtility.encrypt(userMaster.getUserId())));
			userPasswordDto.setChangeDate(userMaster.getActivateDate());
			// ユーザパスワード情報登録
			userPasswordRegist.regist(userPasswordDto);
			return;
		}
		// ユーザ情報取得及び確認
		UserMasterDtoInterface dto = userMasterRefer.findForKey(userMaster.getUserId(), userMaster.getActivateDate());
		if (dto == null) {
			// 履歴追加
			add(userMaster);
			return;
		}
		// 履歴更新
		update(userMaster);
	}
	
	/**
	 * ユーザIDが重複している場合のメッセージを設定する。<br>
	 * @param userId ユーザID
	 */
	protected void addUserIdDuplicateMessage(String userId) {
		String[] rep = { getNameUserId(), userId };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_EXIST, rep);
	}
	
}
