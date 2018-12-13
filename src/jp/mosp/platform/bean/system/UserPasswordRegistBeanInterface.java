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
package jp.mosp.platform.bean.system;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザパスワード情報登録インターフェース
 */
public interface UserPasswordRegistBeanInterface {
	
	/**
	 * 登録用DTO取得
	 * @return 初期DTO
	 */
	UserPasswordDtoInterface getInitDto();
	
	/**
	 * 論理削除。
	 * <p>
	 * ユーザパスワード情報論理削除
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(UserPasswordDtoInterface dto) throws MospException;
	
	/**
	 * ユーザパスワード登録処理を行う。<br>
	 * 同一ユーザID、変更日のレコードがあった場合は、論理削除の上、登録する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(UserPasswordDtoInterface dto) throws MospException;
	
	/**
	 * パスワードの初期化を行う。<br>
	 * @param userIdList ユーザIDリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void initPassword(List<String> userIdList) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(UserPasswordDtoInterface dto, Integer row) throws MospException;
	
}
