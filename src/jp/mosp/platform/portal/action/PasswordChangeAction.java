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
package jp.mosp.platform.portal.action;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.portal.vo.PasswordChangeVo;

/**
 * 初回ログイン時や有効期限切れ、または任意のタイミングでログインパスワードの変更を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li></ul>
 */
public class PasswordChangeAction extends PlatformAction {
	
	/**
	 * MosPアプリケーション設定キー(パスワード最低文字数)。
	 */
	protected static final String	APP_MIN_PASSWORD	= "MinPassword";
	
	/**
	 * MosPアプリケーション設定キー(パスワード文字種)。
	 */
	protected static final String	APP_CHAR_PASSWORD	= "CharPassword";
	
	/**
	 * 自動表示コマンド。<br>
	 * <br>
	 * ログイン時にユーザＩＤとログインパスワードが同一のものである初回ログイン時、
	 * またはログインパスワードの有効期限が切れていると判断された際に自動で画面遷移する。<br>
	 * ログインしようとしたユーザの個人ＩＤを元に該当ユーザのパスワード変更画面を表示する。<br>
	 */
	public static final String		CMD_SHOW			= "PF9110";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 現在ログイン中の個人IDを基にパスワード変更画面を表示する。<br>
	 */
	public static final String		CMD_SELECT			= "PF9116";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄に入力された新しいパスワードを基にユーザパスワーソ情報テーブルの更新を行う。<br>
	 * 現在のパスワード、新しいパスワード、パスワードの入力確認の入力欄が全て入力されて
	 * いない場合はエラーメッセージにて通知。<br>
	 * また、現在パスワードが誤っている場合や新しいパスワードとパスワードの入力確認の各欄の
	 * 入力内容が異なっている場合も同様にエラーメッセージにて通知。<br>
	 * 更新コマンド実行後はメニューバー、パンくずリストを通常通り表示する。<br>
	 */
	public static final String		CMD_UPDATE			= "PF9118";
	
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PasswordChangeVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			update();
		}
	}
	
	/**
	 * 表示処理を行う。
	 * @throws MospException 注意書きリストの設定に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ設定
		vo.setForced(true);
		// メニューバー及びパンくずリスト表示設定
		setNaviUrl();
		// パスワード最低文字数設定
		vo.setJsMinPassword(getMinPassword());
		// パスワード文字種設定
		vo.setJsCharPassword(getCharPassword());
		// 注意書きリスト設定
		setAttentionList();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException 注意書きリストの設定に失敗した場合
	 */
	protected void select() throws MospException {
		// VO準備
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ設定
		vo.setForced(false);
		// パスワード最低文字数設定
		vo.setJsMinPassword(getMinPassword());
		// パスワード文字種設定
		vo.setJsCharPassword(getCharPassword());
		// 注意書きリスト設定
		setAttentionList();
	}
	
	/**
	 * 更新処理を行う。
	 * @throws MospException 暗号化に失敗した場合、或いはパスワード変更に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// パスワード暗号化
		String newPass = SeUtility.encrypt(vo.getHdnNewPassword());
		String oldPass = SeUtility.encrypt(vo.getHdnOldPassword());
		String confirmPass = SeUtility.encrypt(vo.getHdnConfirmPassword());
		// パスワード検証クラス取得
		PasswordCheckBeanInterface check = platform().passwordCheck();
		// パスワード変更検証
		check.checkPasswordChange(mospParams.getUser().getUserId(), oldPass, newPass, confirmPass);
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			// メニューバー及びパンくずリスト表示設定
			setNaviUrl();
			return;
		}
		// 登録クラス取得
		UserPasswordRegistBeanInterface regist = platform().userPasswordRegist();
		// DTOの準備
		UserPasswordDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		dto.setUserId(mospParams.getUser().getUserId());
		dto.setPassword(newPass);
		dto.setChangeDate(getSystemDate());
		// 更新処理
		regist.regist(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			// メニューバー及びパンくずリスト表示設定
			setNaviUrl();
			return;
		}
		// パスワード堅牢性確認
		check.checkPasswordStrength(dto.getUserId());
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			// メニューバー及びパンくずリスト表示設定
			setNaviUrl();
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージ設定
		addUpdateMessage();
		// 強制変更フラグ確認
		if (vo.isForced()) {
			// 連続実行コマンド設定(ポータル画面へ)
			mospParams.setNextCommand(PortalAction.CMD_SHOW);
		}
	}
	
	/**
	 * メニューバー及びパンくずリスト表示設定を行う。
	 */
	protected void setNaviUrl() {
		// VO取得
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ確認
		if (vo.isForced()) {
			// メニューバー及びパンくずリスト非表示
			mospParams.setNaviUrl(null);
		}
	}
	
	/**
	 * 注意書きリストを設定する。<br>
	 * @throws MospException 初期パスワード可否の取得に失敗した場合
	 */
	protected void setAttentionList() throws MospException {
		// VO取得
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 注意書きリスト準備
		List<String> attentionList = new ArrayList<String>();
		vo.setAttentionList(attentionList);
		// 初期パスワード可否確認
		if (platform().passwordCheck().isInitinalPasswordValid() == false) {
			// 注意書き設定
			attentionList.add(mospParams.getProperties().getMessage(PlatformMessageConst.MSG_INIT_PASSWORD_INVALID,
					null));
		}
		// パスワード最低文字数取得
		String minPassword = getMinPassword();
		// パスワード最低文字数確認
		if (minPassword != null && minPassword.isEmpty() == false) {
			// 注意書き設定
			String[] rep = { mospParams.getName("Password"), minPassword };
			attentionList.add(mospParams.getProperties().getMessage(PlatformMessageConst.MSG_MIN_LENGTH_ERR, rep));
		}
		// パスワード最低文字種取得
		String charPassword = getCharPassword();
		if (charPassword != null && charPassword.isEmpty() == false) {
			// 注意書き設定
			attentionList.add(mospParams.getProperties().getMessage(PlatformMessageConst.MSG_CHAR_PASSWORD_ERR, null));
		}
	}
	
	/**
	 * パスワード最低文字数を取得する。<br>
	 * @return パスワード最低文字数
	 */
	protected String getMinPassword() {
		return mospParams.getApplicationProperty(APP_MIN_PASSWORD);
	}
	
	/**
	 * パスワード文字種を取得する。<br>
	 * @return パスワード文字種
	 */
	protected String getCharPassword() {
		return mospParams.getApplicationProperty(APP_CHAR_PASSWORD);
	}
	
}
