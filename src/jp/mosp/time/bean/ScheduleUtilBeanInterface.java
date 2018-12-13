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
package jp.mosp.time.bean;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;

/**
 * カレンダユーティリティインターフェース。<br>
 */
public interface ScheduleUtilBeanInterface {
	
	/**
	 * 対象個人IDの、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードの取得に失敗した場合、エラーメッセージを設定し、空文字を返す。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用情報の、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードの取得に失敗した場合、エラーメッセージを設定し、空文字を返す。<br>
	 * @param dto        設定適用情報
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(ApplicationDtoInterface dto, Date targetDate) throws MospException;
	
}
