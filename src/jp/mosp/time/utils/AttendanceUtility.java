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
package jp.mosp.time.utils;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.WorkTypeEntity;

/**
 * 勤怠登録における有用なメソッドを提供する。<br><br>
 */
public class AttendanceUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private AttendanceUtility() {
		// 処理無し
	}
	
	/**
	 * 始業打刻から勤怠情報に登録する始業時刻を取得する。<br>
	 * <br>
	 * 1.直行の場合：始業予定時刻<br>
	 * 2.遅刻の場合：<br>
	 *   勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 *   勤務予定時間表示設定が無効である場合：勤怠設定で丸めた実打刻時刻<br>
	 * 3.勤務予定時間表示設定が有効である場合：勤務前残業を考慮した始業予定時刻<br>
	 * 4.その他の場合：勤怠設定で丸めた実打刻時刻<br>
	 * <br>
	 * @param applicationEntity 設定適用エンティティ
	 * @param requestEntity     申請エンティティ
	 * @param workTypeEntity    勤務形態エンティティ
	 * @param recordTime        打刻時刻
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getStartTime(ApplicationEntity applicationEntity, RequestEntity requestEntity,
			WorkTypeEntity workTypeEntity, Date recordTime) throws MospException {
		// 始業予定時刻を取得(勤務形態エンティティ及び申請エンティティから)
		Date scheduledTime = workTypeEntity.getStartTime(requestEntity);
		// 勤務前残業自動申請設定(有効の場合TRUE)
		boolean isAutoBeforeOverwork = false;
		
		WorkTypeItemDtoInterface workTypeItemDto = workTypeEntity.getWorkTypeItem(TimeConst.CODE_AUTO_BEFORE_OVERWORK);
		if (workTypeItemDto != null) {
			isAutoBeforeOverwork = workTypeItemDto.getPreliminary()
				.equals(String.valueOf(String.valueOf(MospConst.INACTIVATE_FLAG_OFF)));
			
		}
		
		// 残業申請(勤務前残業)の申請時間(分)を取得
		int overtimeMinutesBeforeWork = requestEntity.getOvertimeMinutesBeforeWork(false);
		
		// 勤務前残業自動申請が有効である場合
		if (isAutoBeforeOverwork) {
			// 勤怠申請情報で前残業申請情報設定
			// 勤務形態の始業時刻-startTimeIntを引いた時間を設定
			int beforeOvertimeInt = DateUtility.getHour(recordTime) * TimeConst.CODE_DEFINITION_HOUR;
			int scheduledTimeInt = DateUtility.getHour(scheduledTime) * TimeConst.CODE_DEFINITION_HOUR;
			overtimeMinutesBeforeWork = scheduledTimeInt - beforeOvertimeInt;
			
		}
		
		// 勤務前残業を考慮した始業予定時刻を取得
		Date overScheduledTime = DateUtility.addMinute(scheduledTime, -overtimeMinutesBeforeWork);
		// 1.直行の場合(勤務形態の直行設定を確認)
		if (workTypeEntity.isDirectStart()) {
			// 始業予定時刻を取得
			return scheduledTime;
		}
		// 2.遅刻の場合
		if (recordTime.compareTo(overScheduledTime) > 0) {
			// 勤務予定時間表示設定が有効で且つ、勤務前残業自動申請が無効の場合
			if (applicationEntity.useScheduledTime() && !isAutoBeforeOverwork) {
				// 勤怠設定で丸めた打刻時刻を取得
				return applicationEntity.getRoundedStartTime(recordTime);
			}
			// 勤怠設定で丸めた実打刻時刻を取得
			return applicationEntity.getRoundedActualStartTime(recordTime);
		}
		// 3.勤務予定時間表示設定が有効である場合、勤務前残業自動申請が無効の場合(勤怠設定の勤務予定時間表示設定を確認)
		if (applicationEntity.useScheduledTime() && !isAutoBeforeOverwork) {
			// 勤務前残業を考慮した始業予定時刻を取得
			return overScheduledTime;
		}
		// 4.その他(勤怠設定で丸めた実打刻時刻を取得)
		return applicationEntity.getRoundedActualStartTime(recordTime);
	}
}
