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
import java.sql.SQLException;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * ポータル用タイムカード処理クラス。<br>
 */
public class PortalTimeLoginBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パラメータキー(押されたボタンの値)。
	 */
	public static final String	PRM_RECODE_TYPE		= "RecodeType";
	
	/**
	 * ポータルパラメータキー(出勤)。
	 */
	public static final String	RECODE_START_WORK	= "StartWork";
	
	/**
	 * 勤怠打刻クラス。
	 */
	TimeRecordBeanInterface		timeRecord;
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalTimeLoginBean() {
		super();
	}
	
	/**
	 * {@link PortalBean#PortalBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	protected PortalTimeLoginBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// Bean取得
		timeRecord = (TimeRecordBeanInterface)createBean(TimeRecordBeanInterface.class);
	}
	
	@Override
	public void show() throws MospException {
		// VOから値を受け取り変数に詰める
		String recodeType = mospParams.getRequestParam(PortalTimeCardBean.PRM_RECODE_TYPE);
		// 通常ログインの場合
		if (recodeType == null || recodeType.isEmpty()) {
			return;
		}
		// コマンド毎の処理
		if (recodeType.equals(PortalTimeCardBean.RECODE_START_WORK)) {
			// 出勤	
			recordStartWork();
			// 処理結果確認
			if (mospParams.hasErrorMessage() == false) {
				// コミット
				commit();
			}
		}
	}
	
	/**
	 * 始業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordStartWork() throws MospException {
		// 始業打刻
		String recordTime = DateUtility.getStringTimeAndSecond(timeRecord.recordStartWork());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordTimeFailed(mospParams);
			return;
		}
		
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordStartWork(mospParams, recordTime);
	}
	
	@Override
	public void regist() {
		// 処理なし
	}
	
	/**
	 * コミットする。<br>
	 * @throws MospException SQL例外が発生した場合
	 */
	public void commit() throws MospException {
		if (connection == null) {
			return;
		}
		try {
			if (connection.isClosed()) {
				return;
			}
			// コミット
			connection.commit();
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
}
