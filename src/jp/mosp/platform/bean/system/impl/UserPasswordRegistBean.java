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
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.dto.system.impl.PfaUserPasswordDto;
import jp.mosp.platform.utils.PlatformMessageUtility;

/**
 * ユーザパスワード情報登録クラス。
 */
public class UserPasswordRegistBean extends PlatformBean implements UserPasswordRegistBeanInterface {
	
	/**
	 * パスワード項目長(DBフィールド)。<br>
	 */
	protected static final int					LEN_PASSWORD	= 50;
	
	/**
	 * ユーザパスワード情報DAOクラス。<br>
	 */
	protected UserPasswordDaoInterface			dao;
	
	/**
	 * ユーザマスタ参照クラス。<br>
	 */
	protected UserMasterReferenceBeanInterface	userRefer;
	
	
	/**
	 * コンストラクタ。
	 */
	public UserPasswordRegistBean() {
		// 処理無し
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public UserPasswordRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (UserPasswordDaoInterface)createDao(UserPasswordDaoInterface.class);
		// Bean準備
		userRefer = (UserMasterReferenceBeanInterface)createBean(UserMasterReferenceBeanInterface.class);
	}
	
	@Override
	public UserPasswordDtoInterface getInitDto() {
		return new PfaUserPasswordDto();
	}
	
	@Override
	public void regist(UserPasswordDtoInterface dto) throws MospException {
		// 現在のユーザパスワード情報取得
		UserPasswordDtoInterface currentDto = dao.findForInfo(dto.getUserId());
		// 存在確認
		if (currentDto != null) {
			// 論理削除
			delete(currentDto);
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaUserPasswordId(dao.nextRecordId());
		// 登録
		dao.insert(dto);
	}
	
	@Override
	public void delete(UserPasswordDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPfaUserPasswordId());
	}
	
	@Override
	public void initPassword(List<String> userIdList) throws MospException {
		// システム日付取得
		Date systemDate = getSystemDate();
		// パスワード確認クラス取得
		PasswordCheckBeanInterface passwordCheck = (PasswordCheckBeanInterface)createBean(PasswordCheckBeanInterface.class);
		// 更新処理
		for (String userId : userIdList) {
			// 初期パスワード取得
			String initialPassword = passwordCheck.getInitialPassword(userId);
			// パスワード作成
			String password = SeUtility.encrypt(SeUtility.encrypt(initialPassword));
			// 対象ユーザにおけるシステム日付の情報を取得
			UserPasswordDtoInterface dto = dao.findForInfo(userId);
			// 存在確認(存在しなければ新規登録、存在すれば更新)
			if (dto != null) {
				// 論理削除
				delete(dto);
			}
			// DTO作成
			dto = getInitDto();
			dto.setUserId(userId);
			dto.setChangeDate(systemDate);
			dto.setPassword(password);
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setPfaUserPasswordId(dao.nextRecordId());
			// 登録
			dao.insert(dto);
		}
	}
	
	@Override
	public void validate(UserPasswordDtoInterface dto, Integer row) throws MospException {
		// 必須確認(ユーザID)
		checkRequired(dto.getUserId(), getNameUserId(), row);
		// 必須確認(変更日)
		checkRequired(dto.getChangeDate(), getNameChangeDate(), row);
		// 必須確認(パスワード)
		checkRequired(dto.getPassword(), getNamePassword(), row);
		// 桁数確認(パスワード)
		checkLength(dto.getPassword(), LEN_PASSWORD, getNamePassword(), row);
		// ユーザ存在確認
		checkUser(dto.getUserId(), row);
	}
	
	/**
	 * ユーザ存在確認を行う。<br>
	 * 有効日及び有効/無効は不問とし、対象ユーザIDが存在するかのみを確認する。<br>
	 * @param userId ユーザID
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkUser(String userId, Integer row) throws MospException {
		// ユーザ履歴一覧を取得し件数を確認
		if (userRefer.getUserHistory(userId).size() == 0) {
			// エラーメッセージ追加
			PlatformMessageUtility.addErrorSelectedUserIdNotExist(mospParams, userId, row);
		}
	}
	
	/**
	 * ユーザID名称を取得する。<br>
	 * @return ユーザID名称
	 */
	protected String getNameUserId() {
		return mospParams.getName("User", "Id");
	}
	
	/**
	 * 変更日名称を取得する。<br>
	 * @return 変更日名称
	 */
	protected String getNameChangeDate() {
		return mospParams.getName("Change", "Day");
	}
	
	/**
	 * パスワード名称を取得する。<br>
	 * @return パスワード名称
	 */
	protected String getNamePassword() {
		return mospParams.getName("Password");
	}
	
}
