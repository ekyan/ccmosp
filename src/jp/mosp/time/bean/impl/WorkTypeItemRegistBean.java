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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeItemDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態項目登録クラス。
 */
public class WorkTypeItemRegistBean extends PlatformBean implements WorkTypeItemRegistBeanInterface {
	
	/**
	 * 勤務形態項目マスタDAOクラス。<br>
	 */
	WorkTypeItemDaoInterface	dao;
	
	/**
	 * 勤務形態マスタDAOクラス。<br>
	 */
	WorkTypeDaoInterface		workTypeDao;
	
	/**
	 * 日々勤怠計算クラス。<br>
	 */
	AttendanceCalcBeanInterface	calc;
	
	/**
	 * 勤務形態項目配列。<br>
	 */
	protected String[]			codesWorkTypeItem	= { TimeConst.CODE_WORKSTART, TimeConst.CODE_WORKEND,
		TimeConst.CODE_WORKTIME, TimeConst.CODE_RESTTIME, TimeConst.CODE_RESTSTART1, TimeConst.CODE_RESTEND1,
		TimeConst.CODE_RESTSTART2, TimeConst.CODE_RESTEND2, TimeConst.CODE_RESTSTART3, TimeConst.CODE_RESTEND3,
		TimeConst.CODE_RESTSTART4, TimeConst.CODE_RESTEND4, TimeConst.CODE_FRONTSTART, TimeConst.CODE_FRONTEND,
		TimeConst.CODE_BACKSTART, TimeConst.CODE_BACKEND, TimeConst.CODE_OVERBEFORE, TimeConst.CODE_OVERPER,
		TimeConst.CODE_OVERREST, TimeConst.CODE_HALFREST, TimeConst.CODE_HALFRESTSTART, TimeConst.CODE_HALFRESTEND,
		TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START, TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END,
		TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END, TimeConst.CODE_AUTO_BEFORE_OVERWORK };
	
	/**
	 * 項目長(勤務形態コード)。<br>
	 */
	protected static final int	LEN_WORK_TYPE_CODE	= 10;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeItemRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public WorkTypeItemRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
		workTypeDao = (WorkTypeDaoInterface)createDao(WorkTypeDaoInterface.class);
		calc = (AttendanceCalcBeanInterface)createBean(AttendanceCalcBeanInterface.class);
	}
	
	@Override
	public WorkTypeItemDtoInterface getInitDto() {
		return new TmmWorkTypeItemDto();
	}
	
	@Override
	public void insert(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 新規登録情報の検証
			checkInsert(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void add(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴追加情報の検証
			checkAdd(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 新規追加項目の場合
			if (dto.getTmmWorkTypeItemId() == 0L) {
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmWorkTypeItemId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
				return;
			}
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			for (String workTypeItemCode : getCodesWorkTypeItem()) {
				// 対象勤務形態項目における有効日の情報を取得
				WorkTypeItemDtoInterface dto = dao.findForKey(code, activateDate, workTypeItemCode);
				// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
				if (dto == null) {
					// 対象勤務形態項目における有効日以前で最新の情報を取得
					dto = dao.findForInfo(code, activateDate, workTypeItemCode);
					// 対象勤務形態項目情報確認
					if (dto == null) {
						// 新規追加項目の場合
						continue;
					}
					// DTOに有効日、無効フラグを設定
					dto.setActivateDate(activateDate);
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴追加情報の検証
					checkAdd(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴追加処理をしない
						continue;
					}
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				} else {
					// DTOに無効フラグを設定
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴更新情報の検証
					checkUpdate(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴更新処理をしない
						continue;
					}
					// 論理削除
					logicalDelete(dao, dto.getTmmWorkTypeItemId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				}
			}
		}
	}
	
	@Override
	public void delete(String workTypeCode, Date activateDate) throws MospException {
		for (String workTypeItemCode : getCodesWorkTypeItem()) {
			WorkTypeItemDtoInterface dto = dao.findForKey(workTypeCode, activateDate, workTypeItemCode);
			if (dto == null) {
				continue;
			}
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
		}
	}
	
	@Override
	public String[] getCodesWorkTypeItem() {
		return CapsuleUtility.getStringArrayClone(codesWorkTypeItem);
	}
	
	/**
	 * 勤務形態項目情報の妥当性確認を行う。<br>
	 * 時間チェック等。<br>
	 * @param itemList 勤務形態項目リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkItemTimeValidate(List<WorkTypeItemDtoInterface> itemList) throws MospException {
		// マップ準備
		Map<String, Date> map = new HashMap<String, Date>();
		// デフォルト時刻取得
		Date defaultTime = DateUtility.getDefaultTime();
		// 区分
		boolean isAutoBeforeOverwork = false;
		boolean isShort1TypePay = false;
		boolean isShort2TypePay = false;
		// マップ作製
		for (WorkTypeItemDtoInterface dto : itemList) {
			// 項目値（時間）取得
			Date targetTime = dto.getWorkTypeItemValue();
			// map追加
			map.put(dto.getWorkTypeItemCode(), targetTime);
			// 勤務前残業自動申請の場合
			if (dto.getWorkTypeItemCode().equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
				if (dto.getPreliminary().equals(String.valueOf(MospConst.INACTIVATE_FLAG_ON))) {
					isAutoBeforeOverwork = true;
				}
			}
			// 割増休憩除外の場合
			if (dto.getWorkTypeItemCode().equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
				// 値が空の時、無効を設定する
				if (dto.getPreliminary().isEmpty()) {
					dto.setPreliminary(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
				}
			}
			// 時短時間1給与区分の場合
			if (dto.getWorkTypeItemCode().equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
				if (dto.getPreliminary().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY)) {
					isShort1TypePay = true;
				}
			}
			// 時短時間1給与区分の場合
			if (dto.getWorkTypeItemCode().equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
				if (dto.getPreliminary().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY)) {
					isShort2TypePay = true;
				}
			}
			// 設定なしの場合
			if (targetTime.compareTo(defaultTime) == 0) {
				continue;
			}
			// 時間と分取得
			int time = DateUtility.getHour(targetTime, defaultTime);
			int minute = DateUtility.getMinute(targetTime);
			// 時は47まで
			if (time > 47) {
				mospParams.addErrorMessage("PFW0129", getWorkTypeItemName(dto.getWorkTypeItemCode()), "47");
			}
			// 分は59まで
			if (minute > 59) {
				mospParams.addErrorMessage("PFW0129", getWorkTypeItemName(dto.getWorkTypeItemCode()), "59");
			}
		}
		
		// 始業・終業・休憩時間取得
		Date startTime = map.get(TimeConst.CODE_WORKSTART);
		Date endTime = map.get(TimeConst.CODE_WORKEND);
		Date rest1Start = map.get(TimeConst.CODE_RESTSTART1);
		Date rest1End = map.get(TimeConst.CODE_RESTEND1);
		Date rest2Start = map.get(TimeConst.CODE_RESTSTART2);
		Date rest2End = map.get(TimeConst.CODE_RESTEND2);
		Date rest3Start = map.get(TimeConst.CODE_RESTSTART3);
		Date rest3End = map.get(TimeConst.CODE_RESTEND3);
		Date rest4Start = map.get(TimeConst.CODE_RESTSTART4);
		Date rest4End = map.get(TimeConst.CODE_RESTEND4);
		// 休憩始業配列
		Date[] aryRestStart = { rest1Start, rest2Start, rest3Start, rest4Start };
		Date[] aryRestEnd = { rest1End, rest2End, rest3End, rest4End };
		// 始業時刻が24時以降の場合
		if (startTime.compareTo(DateUtility.addDay(defaultTime, 1)) >= 0) {
			// エラーメッセージ追加
			String rep[] = { mospParams.getName("StartWork", "Moment"),
				TimeConst.TIME_DAY_ALL_HOUR + mospParams.getName("Hour", "From", "Ahead", "Of", "Time") };
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, rep);
			return;
		}
		// 始業と終業時刻チェック
		if (endTime.compareTo(startTime) < 0) {
			// エラーメッセージを設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_START_END_TIME_CHECK);
			return;
		}
		// 休憩時間開始、休憩終了時刻準備
		Date restStartTime = startTime;
		Date restEndTime = startTime;
		// 休憩時刻確認
		for (int i = 0; i < aryRestStart.length; i++) {
			// 開始・終了時刻が無い場合
			if (aryRestStart[i].compareTo(defaultTime) == 0 && aryRestEnd[i].compareTo(defaultTime) == 0) {
				continue;
			}
			// 始業が休憩開始時間より後の場合
			if (aryRestStart[i].compareTo(startTime) < 0) {
				// エラーメッセージ追加
				addInvalidOrderMessage("始業時刻", mospParams.getName("RestTime", "Time") + (i + 1));
			}
			// 終業が休憩終了時間より前の場合
			if (aryRestEnd[i].compareTo(endTime) > 0) {
				// エラーメッセージ追加
				addInvalidOrderMessage("終業時刻", mospParams.getName("RestTime", "Time") + (i + 1));
			}
			// 休憩時間開始と終了を確認
			if (aryRestStart[i].compareTo(aryRestEnd[i]) > 0) {
				String[] errMes = { mospParams.getName("RestTime", "Time") + (i + 1),
					mospParams.getName("RestTime", "Time") + (i + 1) };
				mospParams.addErrorMessage(TimeMessageConst.MSG_W_END_BEFORE_START, errMes);
				return;
			}
			// 休憩終了と次の開始時刻を確認（次の開始時刻が0:00出ない場合）
			if (i != aryRestStart.length - 1 && aryRestStart[i + 1].compareTo(defaultTime) != 0
					&& aryRestEnd[i].compareTo(aryRestStart[i + 1]) > 0) {
				String[] errMes = { mospParams.getName("RestTime", "Time") + (i + 2),
					mospParams.getName("RestTime", "Time") + (i + 2) };
				mospParams.addErrorMessage(TimeMessageConst.MSG_W_END_BEFORE_START, errMes);
				return;
			}
			// 休憩開始時刻が前休憩時刻に含まれている場合
			if (DateUtility.isTermContain(aryRestStart[i], restStartTime, restEndTime)) {
				String errMes = mospParams.getName("RestTime", "Time") + (i + 1);
				mospParams.addErrorMessage(TimeMessageConst.MSG_REST_GOING_OUT_CHECK, errMes);
				return;
			}
			// 休憩開始・終了時刻設定
			restStartTime = aryRestStart[i];
			restEndTime = aryRestEnd[i];
		}
		// 午前休・午後休
		Date frontStart = map.get(TimeConst.CODE_FRONTSTART);
		Date frontEnd = map.get(TimeConst.CODE_FRONTEND);
		Date backStart = map.get(TimeConst.CODE_BACKSTART);
		Date backEnd = map.get(TimeConst.CODE_BACKEND);
		// 午前休・午後休入力済の場合
		if (frontStart.compareTo(defaultTime) != 0 && backStart.compareTo(defaultTime) != 0) {
			// 午前休期間中に午後休開始×
			if (DateUtility.isTermContain(backStart, frontStart, frontEnd)
					&& !DateUtility.isSame(frontEnd, backStart)) {
				// エラー
				mospParams.addErrorMessage(TimeMessageConst.MSG_REST_GOING_OUT_CHECK, "午後休");
				return;
			}
			// 午後休期間中に午前休終了
			if (DateUtility.isTermContain(frontEnd, backStart, backEnd) && !DateUtility.isSame(frontEnd, backStart)) {
				// エラー
				mospParams.addErrorMessage(TimeMessageConst.MSG_REST_GOING_OUT_CHECK, "午後休");
				return;
			}
		}
		// 午前休入力済の場合
		if (frontStart.compareTo(defaultTime) != 0) {
			//午前休始→午前休終
			if (frontEnd.compareTo(frontStart) < 0) {
				// エラー
				addInvalidOrderMessage("午前休開始時刻", "午前休終了時刻");
				return;
			}
			// 始業時刻→＝午前休始
			if (frontStart.compareTo(startTime) < 0) {
				// エラー
				addInvalidOrderMessage("始業時刻", "午前休開始時刻");
				return;
			}
			// 午前休終→＝終業時刻
			if (endTime.compareTo(frontEnd) < 0) {
				// エラー
				addInvalidOrderMessage("午前休終了時刻", "終業時刻");
				return;
			}
		}
		// 午後休入力済の場合
		if (backStart.compareTo(defaultTime) != 0) {
			// 午後休始→午後休終
			if (backEnd.compareTo(backStart) < 0) {
				// エラー
				addInvalidOrderMessage("午後休開始時刻", "午後休終了時刻");
				return;
			}
			// 午後休終→＝終業時刻
			if (backEnd.compareTo(endTime) < 0) {
				// エラー
				addInvalidOrderMessage("午後休終了時刻", "終業時刻");
				return;
			}
		}
		// 半休取得時休憩取得
		Date halfRestStart = map.get(TimeConst.CODE_HALFRESTSTART);
		Date halfRestEnd = map.get(TimeConst.CODE_HALFRESTEND);
		// 半休取得時休憩の休憩2、休憩3が入力済の場合
		if (halfRestStart.compareTo(defaultTime) != 0 && halfRestEnd.compareTo(defaultTime) != 0) {
			//休憩2→休憩3
			if (halfRestEnd.compareTo(halfRestStart) < 0) {
				// エラー
				String[] rep = { "半休取得時休憩開始時刻", "半休取得時休憩終了時刻" };
				mospParams.addErrorMessage(PlatformMessageConst.MSG_INVALID_ORDER, rep);
				return;
			}
		}
		// 残業休憩取得時休憩取得
		int overPerMinute = TimeUtility.getMinutes(map.get(TimeConst.CODE_OVERPER));
		int overRestMinute = TimeUtility.getMinutes(map.get(TimeConst.CODE_OVERREST));
		// 時間(毎)より残業時間が同じ又は長い場合
		if (overPerMinute == 0 && overRestMinute > 0) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, "休憩時間", "残業休憩");
			return;
		}
		if (overPerMinute > 0 && overPerMinute <= overRestMinute) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, "休憩時間", "残業休憩");
			return;
		}
		// 時短確認
		Date short1StartTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
		Date short1EndTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
		Date short2StartTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
		Date short2EndTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
		boolean isShort1Set = short1StartTime != null && short1StartTime.compareTo(defaultTime) != 0
				|| short1EndTime != null && short1EndTime.compareTo(defaultTime) != 0;
		boolean isShort2Set = short2StartTime != null && short2StartTime.compareTo(defaultTime) != 0
				|| short2EndTime != null && short2EndTime.compareTo(defaultTime) != 0;
		// 時短時間1確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort1Set) {
			// 時短勤務1開始時刻と始業時刻を比較
			if (short1StartTime.compareTo(startTime) != 0) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1TimeBoundary(mospParams);
			}
			// 時短勤務1終了時刻と終業時刻の前後を確認
			if (short1EndTime.after(endTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1OutOfWorkTime(mospParams);
			}
			// 時短時間1開始終了前後確認
			if (short1EndTime.after(short1StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1EndBeforeStart(mospParams);
			}
		}
		// 時短時間2確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort2Set) {
			// 時短勤務2終了時刻と終業時刻を比較
			if (short2EndTime.compareTo(endTime) != 0) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2TimeBoundary(mospParams);
			}
			// 時短勤務2開始時刻と始業時刻の前後を確認
			if (short2StartTime.before(startTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2OutOfWorkTime(mospParams);
			}
			// 時短時間2開始終了前後確認
			if (short2EndTime.after(short2StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2EndBeforeStart(mospParams);
			}
		}
		// 時短勤務区分の組合せを確認
		if (isShort1Set && isShort2Set && !isShort1TypePay && isShort2TypePay) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortTypePair(mospParams);
		}
		// 午前/午後休重複チェック
		// 今後必要になる可能性あり
		/*
		int i = aryStartTimeHour.length - 2;
		if (aryEndTimeHour[i] > aryStartTimeHour[i + 1]) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_HARF_REST_CHECK, null, null);
			return;
		} else if (aryEndTimeHour[i] == aryStartTimeHour[i + 1]) {
			if (aryEndTimeMinute[i] > aryStartTimeMinute[i + 1]) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_HARF_REST_CHECK, null, null);
				return;
			}
		}
		*/
		// 勤務前残業自動申請有効可否確認
		checkAutoBeforeOverWork(isAutoBeforeOverwork, isShort1TypePay, short1StartTime, short1EndTime);
		return;
	}
	
	/**
	 * 勤務前残業自動申請有効可否チェック
	 * @param isAutoBeforeOverwork 勤務前残業自動申請（true：有効、false：無効）
	 * @param isShort1TypePay 時短1有給区分（true：有給、false：無給）
	 * @param short1Start 時短1開始時刻
	 * @param short1End 時短1終了時刻
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	protected void checkAutoBeforeOverWork(boolean isAutoBeforeOverwork, boolean isShort1TypePay, Date short1Start,
			Date short1End) throws MospException {
		
		// 勤務前残業自動申請が無効の場合
		if (isAutoBeforeOverwork) {
			// チェックなし
			return;
		}
		// 時短時間1が未設定の場合、チェックなし
		if (short1Start.compareTo(DateUtility.getDefaultTime()) == 0
				&& short1End.compareTo(DateUtility.getDefaultTime()) == 0) {
			return;
		}
		// 時短時間1有給確認
		if (isShort1TypePay) {
			// 有給の場合、チェックなし
			return;
		}
		// 時短時間1が設定されており且つ無給の場合はエラー
		TimeMessageUtility.addErrorAnotherItemInvalid(mospParams);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(), dto.getWorkTypeItemCode()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (!needCheckTermForAdd(dto, list)) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmWorkTypeItemId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 *  削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 勤務形態コードを取得する。
	 * @param idArray レコード識別ID配列
	 * @return 勤務形態コード
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)workTypeDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getWorkTypeCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(WorkTypeItemDtoInterface dto) {
		// 勤務形態コード
		checkLength(dto.getWorkTypeCode(), LEN_WORK_TYPE_CODE, mospParams.getName("Work", "Form", "Code"), null);
		checkTypeCode(dto.getWorkTypeCode(), mospParams.getName("Work", "Form", "Code"), null);
		
	}
	
	/**
	 * 項目名称を取得する。<br>
	 * @param worktypeItemCode 勤務形態項目コード
	 * @return 項目名称
	 */
	protected String getWorkTypeItemName(String worktypeItemCode) {
		
		String name = "";
		if (worktypeItemCode.equals(TimeConst.CODE_WORKSTART)) {
			return mospParams.getName("StartWork");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORKEND)) {
			return mospParams.getName("EndWork");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORKTIME)) {
			return mospParams.getName("Work", "Time");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTTIME)) {
			return mospParams.getName("RestTime", "Time");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART1)) {
			return mospParams.getName("RestTime", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND1)) {
			return mospParams.getName("RestTime", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART2)) {
			return mospParams.getName("RestTime", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND2)) {
			return mospParams.getName("RestTime", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART3)) {
			return mospParams.getName("RestTime", "No3");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND3)) {
			return mospParams.getName("RestTime", "No3");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART4)) {
			return mospParams.getName("RestTime", "No4");
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND4)) {
			return mospParams.getName("RestTime", "No4");
		} else if (worktypeItemCode.equals(TimeConst.CODE_FRONTSTART)) {
			return mospParams.getName("AmRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_FRONTEND)) {
			return mospParams.getName("AmRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_BACKSTART)) {
			return mospParams.getName("PmRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_BACKEND)) {
			return mospParams.getName("PmRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERBEFORE)) {
			return mospParams.getName("RestBeforeOvertimeWork");
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERPER)) {
			return mospParams.getName("RestInOvertime");
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERREST)) {
			return mospParams.getName("RestInOvertime");
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFREST)) {
			return mospParams.getName("Work", "Ahead", "OvertimeWork", "Performance", "Insert");
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFRESTSTART)) {
			return mospParams.getName("Work", "Ahead", "OvertimeWork", "Performance", "Insert");
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFRESTEND)) {
			return mospParams.getName("Work", "Ahead", "OvertimeWork", "Performance", "Insert");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			return mospParams.getName("DirectStart");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			return mospParams.getName("DirectEnd");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			return mospParams.getName("ExcludeNightRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			return mospParams.getName("ShortTime", "Time", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			return mospParams.getName("ShortTime", "Time", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			return mospParams.getName("ShortTime", "Time", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			return mospParams.getName("ShortTime", "Time", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
			return mospParams.getName("Work", "Ahead", "OvertimeWork", "Performance", "Insert");
		}
		return name;
	}
	
	@Override
	public Date getDefaultTime(String hour, String minute) throws MospException {
		// 基準日取得
		Date defaultDate = DateUtility.getDefaultTime();
		return calc.getAttendanceTime(defaultDate, hour, minute);
	}
	
}
