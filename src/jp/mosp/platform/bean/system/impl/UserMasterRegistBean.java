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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmUserDto;

/**
 * ユーザマスタ登録クラス。
 */
public class UserMasterRegistBean extends PlatformFileBean implements UserMasterRegistBeanInterface {
	
	/**
	 * ユーザID項目長。<br>
	 */
	protected static final int				LEN_USER_ID	= 50;
	
	/**
	 * ユーザマスタDAOクラス。<br>
	 */
	protected UserMasterDaoInterface		dao;
	
	/**
	 * ユーザパスワード情報DAO。<br>
	 */
	protected UserPasswordDaoInterface		userPasswordDao;
	
	/**
	 * ロール参照クラス。<br>
	 */
	protected RoleReferenceBeanInterface	roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserMasterRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public UserMasterRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (UserMasterDaoInterface)createDao(UserMasterDaoInterface.class);
		userPasswordDao = (UserPasswordDaoInterface)createDao(UserPasswordDaoInterface.class);
		// Bean準備
		roleRefer = (RoleReferenceBeanInterface)createBean(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public UserMasterDtoInterface getInitDto() {
		return new PfmUserDto();
	}
	
	@Override
	public void insert(UserMasterDtoInterface dto) throws MospException {
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
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(UserMasterDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		// 有効ユーザ存在確認
		checkUserExist();
	}
	
	@Override
	public void update(UserMasterDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getPfmUserId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		// 有効ユーザ存在確認
		checkUserExist();
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String code : getUserIdList(idArray)) {
			// 対象ユーザにおける有効日の情報を取得
			UserMasterDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象ユーザにおける有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象ユーザ情報確認
				if (dto == null) {
					// 有効日以前に情報が存在しない場合
					addNoCodeBeforeActivateDateMessage(code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmUserId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmUserId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmUserId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
		// 有効ユーザ存在確認
		checkUserExist();
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, String roleCode) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String code : getUserIdList(idArray)) {
			// 対象ユーザにおける有効日の情報を取得
			UserMasterDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象ユーザにおける有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 有効日以前で最新の情報がない場合
				if (dto == null) {
					// エラーメッセージ追加
					addNoCodeBeforeActivateDateMessage(code);
					continue;
				}
				// DTOに有効日、ロールコードを設定
				dto.setActivateDate(activateDate);
				dto.setRoleCode(roleCode);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmUserId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに有効日、ロールコードを設定
				dto.setRoleCode(roleCode);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmUserId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmUserId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
		// 有効ユーザ存在確認
		checkUserExist();
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID毎に削除
		for (long id : idArray) {
			// 削除対象DTO取得
			UserMasterDtoInterface dto = (UserMasterDtoInterface)dao.findForKey(id, true);
			// 削除情報の検証
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
			// ユーザIDでユーザパスワード情報を取得
			UserPasswordDtoInterface userPasswordDto = userPasswordDao.findForInfo(dto.getUserId());
			if (userPasswordDto != null) {
				// ユーザマスタ履歴一覧を取得
				List<UserMasterDtoInterface> list = dao.findForHistory(dto.getUserId());
				// 履歴がない場合ユーザパスワード削除
				if (list.isEmpty()) {
					// 論理削除
					logicalDelete(userPasswordDao, userPasswordDto.getPfaUserPasswordId());
				}
			}
		}
		// 有効ユーザ存在確認
		checkUserExist();
	}
	
	@Override
	public List<String> getUserIdList(long[] recordIdArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long recordId : recordIdArray) {
			// レコード識別IDから対象DTOを取得
			UserMasterDtoInterface dto = (UserMasterDtoInterface)dao.findForKey(recordId, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getUserId());
		}
		return list;
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(UserMasterDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getUserId()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(UserMasterDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getUserId(), dto.getActivateDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(UserMasterDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmUserId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象ユーザを設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(UserMasterDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmUserId());
	}
	
	/**
	 * ユーザ妥当性確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUser(UserMasterDtoInterface dto) throws MospException {
		// ユーザ確認クラス取得
		UserCheckBeanInterface userCheck = (UserCheckBeanInterface)createBean(UserCheckBeanInterface.class);
		// ロール存在確認
		userCheck.checkRoleExist(dto.getRoleCode(), dto.getActivateDate());
		// ユーザ社員確認
		userCheck.checkUserEmployee(dto.getPersonalId(), dto.getActivateDate());
	}
	
	/**
	 * 有効ユーザ存在確認を行う。<br>
	 * システム日付で確認し、ユーザが存在せず
	 * ログインできない状態にならないかを確認する。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUserExist() throws MospException {
		// システム日付で必須ロールコードを有する有効なユーザを取得
		List<UserMasterDtoInterface> list = dao.findForRole(getNeededRole(), getSystemDate());
		// 結果確認
		if (list.isEmpty()) {
			// 必須ロールコードを設定してるユーザが存在しなくなる場合のメッセージを設定
			addDisappearNeededRoleMessage();
		}
	}
	
	/**
	 * 必須ロールコードを取得する。<br>
	 * @return 必須ロールコード
	 */
	protected String getNeededRole() {
		// ロール情報取得
		for (Entry<String, RoleProperty> entry : mospParams.getProperties().getRoleProperties().entrySet()) {
			// ロール情報取得
			RoleProperty roleProperty = entry.getValue();
			// 必須ロール設定確認
			if (roleProperty.isNeeded()) {
				return roleProperty.getKey();
			}
		}
		return "";
	}
	
	/**
	 * 必須ロールコードを設定してるユーザが存在しなくなる場合のメッセージを設定する。<br>
	 */
	protected void addDisappearNeededRoleMessage() {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_DISAPPEAR_NEEDED_ROLE, getNeededRole());
	}
	
	@Override
	public void validate(UserMasterDtoInterface dto, Integer row) throws MospException {
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			// ユーザ妥当性確認
			checkUser(dto);
		}
		// 必須確認(ユーザID)
		checkRequired(dto.getUserId(), getNameUserId(), row);
		// コード + 記号確認
		checkUserId(dto.getUserId(), getNameUserId(), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), getNameActivateDate(), row);
		// 桁数確認(ユーザID)
		checkLength(dto.getUserId(), LEN_USER_ID, getNameUserId(), row);
		// ロール存在確認
		checkRole(dto.getRoleCode(), dto.getActivateDate(), row);
	}
	
	/**
	 * ロール存在確認を行う。<br>
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRole(String roleCode, Date targetDate, Integer row) throws MospException {
		// ロール配列取得
		String[][] aryRole = roleRefer.getSelectArray(targetDate, false);
		// ロール存在確認
		for (String[] role : aryRole) {
			if (role[0].equals(roleCode)) {
				return;
			}
		}
		// ロールが存在しない場合のメッセージを追加
		addRoleNotExistMessage(roleCode, row);
	}
	
	/**
	 * ロールが存在しない場合のメッセージを設定する。<br>
	 * @param roleCode ロール
	 * @param row      行インデックス
	 */
	protected void addRoleNotExistMessage(String roleCode, Integer row) {
		String[] rep = { getRowedFieldName(getNameRole(), row), roleCode };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, rep);
	}
	
	/**
	 * ユーザID名称を取得する。<br>
	 * @return ユーザID名称
	 */
	protected String getNameUserId() {
		return mospParams.getName("User") + mospParams.getName("Id");
	}
	
	/**
	 * ロール名称を取得する。<br>
	 * @return ロール名称
	 */
	protected String getNameRole() {
		return mospParams.getName("Role");
	}
	
}
