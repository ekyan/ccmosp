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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospUser;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.portal.vo.LoginVo;

/**
 * 認証処理を行う。<br>
 */
public class AuthAction extends PlatformAction {
	
	/**
	 * 認証処理を行う。
	 */
	public static final String	CMD_AUTHENTICATE	= "PF0020";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public AuthAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_AUTHENTICATE)) {
			// 認証
			prepareVo();
			auth();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new LoginVo();
	}
	
	/**
	 * 認証処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void auth() throws MospException {
		// VO取得
		LoginVo vo = (LoginVo)mospParams.getVo();
		// ユーザID及びパスワードによる認証
		platform().auth().authenticate(vo.getTxtUserId(), vo.getTxtPassWord());
		// 認証結果確認
		if (mospParams.hasErrorMessage()) {
			// 認証失敗メッセージ設定及びMosPセッション保持情報初期化
			addAuthFailedMessage();
			return;
		}
		// ユーザ確認(対象日はシステム日付)
		platform().userCheck().checkUserEmployeeForUserId(vo.getTxtUserId(), getSystemDate());
		// ユーザ確認結果確認
		if (mospParams.hasErrorMessage()) {
			// 認証失敗メッセージ設定及びMosPセッション保持情報初期化
			addAuthFailedMessage();
			return;
		}
		// ロール確認(対象日はシステム日付)
		platform().userCheck().checkUserRole(vo.getTxtUserId(), getSystemDate());
		// ロール確認結果確認
		if (mospParams.hasErrorMessage()) {
			// 認証失敗メッセージ設定及びMosPセッション保持情報初期化
			addAuthFailedMessage();
			return;
		}
		// ユーザマスタ取得
		UserMasterDtoInterface userMasterDto = reference().user().getUserInfo(vo.getTxtUserId(), getSystemDate());
		// 人事マスタ取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(userMasterDto.getPersonalId(), getSystemDate());
		// MosPユーザ情報作成
		MospUser user = mospParams.getUser();
		if (user == null) {
			user = new MospUser();
		}
		user.setUserId(userMasterDto.getUserId());
		user.setPersonalId(userMasterDto.getPersonalId());
		user.setRole(userMasterDto.getRoleCode());
		user.setUserName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		// MosPユーザ情報設定
		mospParams.setUser(user);
		// パスワード確認(堅牢性)
		platform().passwordCheck().checkPasswordStrength(vo.getTxtUserId());
		// パスワード確認結果確認
		if (mospParams.hasErrorMessage()) {
			// パスワード変更画面へ
			mospParams.setNextCommand(PasswordChangeAction.CMD_SHOW);
			return;
		}
		// パスワード確認(有効期間)
		platform().passwordCheck().checkPasswordPeriod(vo.getTxtUserId(), getSystemDate());
		// パスワード確認結果確認
		if (mospParams.hasErrorMessage()) {
			// パスワード変更画面へ
			mospParams.setNextCommand(PasswordChangeAction.CMD_SHOW);
			return;
		}
		// 認証成功
		mospParams.setNextCommand(PortalAction.CMD_AFTER_AUTH);
		// ログ出力
		LogUtility.application(mospParams, mospParams.getName("Login"));
	}
	
	/**
	 * 認証失敗時のメッセージを追加する。<br>
	 * MosPセッション保持情報の初期化も、併せて行う。<br>
	 */
	protected void addAuthFailedMessage() {
		// 認証失敗メッセージ設定
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_FAILED, mospParams.getName("Login"));
		// MosPセッション保持情報初期化
		mospParams.getStoredInfo().initStoredInfo();
	}
	
}
