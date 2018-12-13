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
package jp.mosp.time.portal.bean.impl;

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * ポータル用タイムカード処理クラス。<br>
 */
public class PortalTimeCardBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用打刻機能JSP)。
	 */
	protected static final String	PATH_PORTAL_VIEW	= "/jsp/time/portal/portalTimeCard.jsp";
	
	/**
	 * パス(ポータル用打刻機能JS)。
	 */
	public static final String		JS_TIME				= "jsTime";
	
	/**
	 * ポータルパラメータキー(出勤)。
	 */
	public static final String		RECODE_START_WORK	= "StartWork";
	
	/**
	 * ポータルパラメータキー(退勤)。
	 */
	public static final String		RECODE_END_WORK		= "EndWork";
	
	/**
	 * ポータルパラメータキー(休憩入)。
	 */
	public static final String		RECODE_START_REST	= "StartRest";
	
	/**
	 * ポータルパラメータキー(休憩戻)。
	 */
	public static final String		RECODE_END_REST		= "EndRest";
	
	/**
	 * ポータルパラメータキー(定時終業)。
	 */
	public static final String		RECODE_REGULAR_END	= "RegularEnd";
	
	/**
	 * ポータルパラメータキー(残業有終業)。
	 */
	public static final String		RECODE_OVER_END		= "OverEnd";
	
	/**
	 * ポータルパラメータキー(出勤)。
	 */
	public static final String		RECODE_REGULAR_WORK	= "RegularWork";
	
	/**
	 * パラメータキー(押されたボタンの値)。
	 */
	public static final String		PRM_RECODE_TYPE		= "RecodeType";
	
	/**
	 * パラメータキー(ポータル出退勤ボタン表示)。
	 */
	public static final String		PRM_TIME_BUTTON		= "TimeButton";
	
	/**
	 * パラメータキー(ポータル休憩ボタン表示)。
	 */
	public static final String		PRM_REST_BUTTON		= "RestButton";
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalTimeCardBean() {
		super();
	}
	
	/**
	 * {@link PortalBean#PortalBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected PortalTimeCardBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void show() throws MospException {
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_VIEW);
		// ポータル用JS
		mospParams.addGeneralParam(JS_TIME, String.valueOf(getSystemTimeAndSecond().getTime()));
		// 対象個人ID(ログインユーザの個人ID)取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付取得
		Date targetDate = getSystemDate();
		// 設定適用エンティティ取得
		ApplicationEntity applicationEntity = getApplicationReferenceBean()
			.getApplicationEntity(personalId, targetDate);
		// ポータル出退勤ボタン表示設定取得
		putPortalParameter(PRM_TIME_BUTTON, String.valueOf(applicationEntity.getPortalTimeButtons()));
		// ポータル休憩ボタン表示設定取得
		putPortalParameter(PRM_REST_BUTTON, String.valueOf(applicationEntity.getPortalRestButtons()));
	}
	
	@Override
	public void regist() throws MospException {
		// VOから値を受け取り変数に詰める
		String recodeType = getPortalParameter(PRM_RECODE_TYPE);
		// コマンド毎の処理
		if (recodeType.equals(RECODE_START_WORK)) {
			// 出勤	
			recordStartWork();
		} else if (recodeType.equals(RECODE_END_WORK)) {
			// 退勤	
			recordEndWork();
		} else if (recodeType.equals(RECODE_START_REST)) {
			// 休憩入
			recordStartRest();
		} else if (recodeType.equals(RECODE_END_REST)) {
			// 休憩戻
			recordEndRest();
		} else if (recodeType.equals(RECODE_REGULAR_END)) {
			// 定時終業
			recordRegularEnd();
		} else if (recodeType.equals(RECODE_OVER_END)) {
			// 残業有終業
			recordOverEnd();
		} else if (recodeType.equals(RECODE_REGULAR_WORK)) {
			// 出勤
			recordRegularWork();
		}
	}
	
	/**
	 * 始業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordStartWork() throws MospException {
		// 始業打刻
		getTimeRecordBean().recordStartWork();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordStartWork(mospParams);
	}
	
	/**
	 * 終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordEndWork() throws MospException {
		// 終業打刻
		getTimeRecordBean().recordEndWork();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordEndWork(mospParams);
	}
	
	/**
	 * 休憩入を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordStartRest() throws MospException {
		// 休憩入打刻
		getTimeRecordBean().recordStartRest();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordStartRest(mospParams);
	}
	
	/**
	 * 休憩戻を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordEndRest() throws MospException {
		// 休憩戻打刻
		getTimeRecordBean().recordEndRest();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordEndRest(mospParams);
	}
	
	/**
	 * 定時終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordRegularEnd() throws MospException {
		// 定時終業打刻
		getTimeRecordBean().recordRegularEnd();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordRegularEnd(mospParams);
	}
	
	/**
	 * 残業有終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordOverEnd() throws MospException {
		// 定時終業打刻
		getTimeRecordBean().recordOverEnd();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
		}
	}
	
	/**
	 * 出勤を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordRegularWork() throws MospException {
		// 定時終業打刻
		getTimeRecordBean().recordRegularWork();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordRegularWork(mospParams);
	}
	
	/**
	 * 設定適用参照クラスを取得する。<br>
	 * @return 設定適用参照ユーティリティクラス
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected ApplicationReferenceBeanInterface getApplicationReferenceBean() throws MospException {
		return (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
	}
	
	/**
	 * 打刻クラスを取得する。<br>
	 * @return 打刻クラス
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeRecordBeanInterface getTimeRecordBean() throws MospException {
		return (TimeRecordBeanInterface)createBean(TimeRecordBeanInterface.class);
	}
	
}