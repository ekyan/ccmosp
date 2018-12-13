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

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * 勤怠管理モジュールにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class TimeMessageUtility {
	
	/**
	 * メッセージコード(集計時エラー内容)。<br>
	 * %1%の%2%が%3%です。<br>
	 */
	protected static final String	MSG_CUTOFF_ERROR			= "TMW0318";
	
	/**
	 * メッセージコード(打刻成功時)。<br>
	 * %1%を%2%しました。<br>
	 */
	public static final String		MSG_RECORD_TIME				= "TMI0001";
	
	/**
	 * 勤務時間の範囲チェックエラーメッセージ。<br>
	 * メッセージコード(勤務時間外に設定されている場合)。<br>
	 * 勤務時間外に%1%が設定されています。%1%は勤務時間内に含めて設定してください。<br>
	 */
	public static final String		MSG_W_WORK_TIME_OUT_CHECK	= "TMW0237";
	
	/**
	 * メッセージコード(終了が開始よりも前の場合)。<br>
	 * %1%終了は%2%開始より後となるようにしてください。<br>
	 */
	protected static final String	MSG_W_END_BEFORE_START		= "TMW0280";
	
	/**
	 * メッセージコード(既に登録されているため処理できなかった場合)。<br>
	 * %1%は既に%2%が登録されているため、%3%できません。勤怠詳細を確認してください。<br>
	 */
	public static final String		MSG_ALREADY_RECORDED		= "TMW0301";
	
	/**
	 * メッセージコード(開始時刻が登録されていないため処理できなかった場合)。<br>
	 * %1%の%2%は、%3%が登録されていないため%4%できません。勤怠詳細を確認してください。<br>
	 */
	protected static final String	MSG_START_NOT_RECORDED		= "TMW0302";
	
	/**
	 * メッセージコード(限度を超えているため処理できなかった場合)。<br>
	 * %1%の%2%は、限度を超えるため%3%できません。勤怠詳細を確認してください。<br>
	 */
	protected static final String	MSG_OVER_LIMIT				= "TMW0303";
	
	/**
	 * メッセージコード(時短勤務の時刻の境界が不正な場合)。<br>
	 * %1%の%2%には%3%と同一の時刻を入力してください。<br>
	 */
	protected static final String	MSG_W_SHORT_TIME_BOUNDARY	= "TMW0321";
	
	/**
	 * メッセージコード(時短勤務区分の組合せが不正な場合)。<br>
	 * 時短時間1で無給を設定した場合、時短時間2でも必ず無給を設定するようにしてください。<br>
	 */
	protected static final String	MSG_W_SHORT_TYPE_PAIR		= "TMW0322";
	
	/**
	 * メッセージコード(遅刻か早退があるためポータルからの自己承認ができない場合)。<br>
	 * 遅刻か早退があるため、自己承認ができません。勤怠詳細から勤怠を申請してください。<br>
	 */
	protected static final String	MSG_W_SELF_APPROVE_FAILED	= "TMW0323";
	
	/**
	 * メッセージコード(分単位休暇が0でなく勤務時間が0の場合(全休チェックなし))。<br>
	 * 分単位休暇を取得して全休にする場合、全休チェックを付けてください。<br>
	 */
	protected static final String	MSG_W_MINUTELY_ALL_HOLIDAY	= "TMW0328";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeMessageUtility() {
		// 処理無し
	}
	
	/**
	 * 集計しました。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageTotalSucceed(MospParams mospParams) {
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_SUCCEED, getNameTotal(mospParams));
	}
	
	/**
	 * 集計できませんでした。エラー内容を確認の上、再度処理を行ってください。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageTotalFailed(MospParams mospParams) {
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_FAILED, getNameTotal(mospParams));
	}
	
	/**
	 * %1%の%2%が%3%です。(TMW0318)<br>
	 * 例：2013/05/01の勤怠が未申請です。(TMW0318)<br>
	 * @param mospParams MosP処理情報
	 * @param dto        集計時エラー内容情報
	 */
	public static void addErrorCutoff(MospParams mospParams, CutoffErrorListDtoInterface dto) {
		String date = DateUtility.getStringDate(dto.getDate());
		mospParams.addErrorMessage(MSG_CUTOFF_ERROR, date, dto.getType(), dto.getState());
	}
	
	/**
	 * year年month月は既に月次処理が行われています。年月を変更してください。(TMW0272)
	 * @param mospParams MosP処理情報
	 * @param year       年
	 * @param month      月
	 */
	public static void addErrorTheMonthIsTighten(MospParams mospParams, int year, int month) {
		String rep = year + getNameYear(mospParams) + month + getNameMonth(mospParams);
		mospParams.addErrorMessage(TimeMessageConst.MSG_MONTHLY_TREATMENT, rep, getNameYearMonth(mospParams));
	}
	
	/**
	 * 打刻できませんでした。エラー内容を確認の上、再度処理を行ってください。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordTimeFailed(MospParams mospParams) {
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_FAILED, getNameRecordTime(mospParams));
	}
	
	/**
	 * 始業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordStartWork(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 終業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordEndWork(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameEndWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 休憩入りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordStartRest(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 休憩戻りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordEndRest(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameEndRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 定時終業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordRegularEnd(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameRegularEnd(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 出勤を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordRegularWork(MospParams mospParams) {
		mospParams.addMessage(MSG_RECORD_TIME, getNameRegularWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に始業が登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartWorkAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に休憩入りが登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartRestAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に終業が登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorEndWorkAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの休憩戻りは、休憩入りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartRestNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndRest(mospParams), getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの終業は、休憩戻りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorEndRestNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndWork(mospParams), getNameEndRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDのprocessは、始業が登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * 例：2013/05/01の終業は、始業が登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param process    打刻対象処理
	 */
	public static void addErrorStratWorkNotRecorded(MospParams mospParams, Date targetDate, String process) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate), process,
				getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの休憩入りは、限度を超えるため打刻できません。勤怠詳細を確認してください。(TMW0303)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorRestOverLimit(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_OVER_LIMIT, DateUtility.getStringDate(targetDate), getNameStartRest(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * %1%の時間と分をそれぞれ正しく入力してください。
	 * @param mospParams MosP処理情報
	 * @param targetName 確認対象名称
	 */
	public static void addErrorTimeFormat(MospParams mospParams, String targetName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, targetName);
	}
	
	/**
	 * 時短時間1の開始時刻には始業時刻と同一の時刻を入力してください。(TMW0320)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1TimeBoundary(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TIME_BOUNDARY, getNameShort1Time(mospParams),
				getNameStartTime(mospParams), getNameStartWorkTime(mospParams));
	}
	
	/**
	 * 時短時間2の終了時刻には終業時刻と同一の時刻を入力してください。(TMW0321)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2TimeBoundary(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TIME_BOUNDARY, getNameShort2Time(mospParams),
				getNameEndTime(mospParams), getNameEndWorkTime(mospParams));
	}
	
	/**
	 * 時短時間1で無給を設定した場合、時短時間2でも必ず無給を設定するようにしてください。(TMW0322)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShortTypePair(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TYPE_PAIR);
	}
	
	/**
	 * 遅刻か早退があるため、自己承認ができません。勤怠詳細から勤怠を申請してください。(TMW0323)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSelfApproveFailed(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SELF_APPROVE_FAILED);
	}
	
	/**
	 * 時短時間1終了は時短時間1開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1EndBeforeStart(MospParams mospParams) {
		mospParams
			.addErrorMessage(MSG_W_END_BEFORE_START, getNameShort1Time(mospParams), getNameShort1Time(mospParams));
	}
	
	/**
	 * 時短時間2終了は時短時間2開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2EndBeforeStart(MospParams mospParams) {
		mospParams
			.addErrorMessage(MSG_W_END_BEFORE_START, getNameShort2Time(mospParams), getNameShort2Time(mospParams));
	}
	
	/**
	 * 勤務時間外に時短時間1が設定されています。時短時間1は勤務時間内に含めて設定してください。(TMW0237)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1OutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_WORK_TIME_OUT_CHECK, getNameShort1Time(mospParams));
	}
	
	/**
	 * 勤務時間外に時短時間2が設定されています。時短時間2は勤務時間内に含めて設定してください。(TMW0237)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2OutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_WORK_TIME_OUT_CHECK, getNameShort2Time(mospParams));
	}
	
	/**
	 * 分単位休暇Aは所定労働時間内で入力してください。(TMW0310)
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorMinutelyAOutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, getNameMinutelyHolidayA(mospParams),
				getNamePrescribedWorkTime(mospParams));
	}
	
	/**
	 * 分単位休暇Bは所定労働時間内で入力してください。(TMW0310)
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorMinutelyBOutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_INSIDE_CHECK, getNameMinutelyHolidayB(mospParams),
				getNamePrescribedWorkTime(mospParams));
	}
	
	/**
	 * 分単位休暇を取得して全休にする場合、全休チェックを付けてください。(TMW0328)
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorMinutelyAllHoliday(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_MINUTELY_ALL_HOLIDAY);
	}
	
	/**
	 * YYYY/MM/DDは休暇の申請が行われているので勤怠の申請及び下書を行うことはできません。(TMW0279)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorNotApplicableForHoliday(MospParams mospParams, Date targetDate, Integer row) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10,
				getRowedFieldName(mospParams, DateUtility.getStringDate(targetDate), row), getNameVacation(mospParams),
				getNameWorkManage(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に勤怠申請が行われています。勤務日を選択し直してください。(TMW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorAlreadyApplyWork(MospParams mospParams, Date targetDate, Integer row) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1,
				getRowedFieldName(mospParams, DateUtility.getStringDate(targetDate), row),
				getNameWorkManage(mospParams), getNameWorkDate(mospParams));
	}
	
	/**
	 * 終業時刻は始業時刻より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorWorkTimeOrderInvalid(MospParams mospParams, Integer row) {
		addErrorTimeOrderInvalid(mospParams, getNameStartWorkTime(mospParams), getNameEndWorkTime(mospParams), row);
	}
	
	/**
	 * 休憩戻は休憩入より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorRestTimeOrderInvalid(MospParams mospParams, Integer row) {
		addErrorTimeOrderInvalid(mospParams, getNameStartRest(mospParams), getNameEndRest(mospParams), row);
	}
	
	/**
	 * %1%は%2%より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param beforeName 前にあるべき日時の名称
	 * @param afterName  後にあるべき日時の名称
	 * @param row        対象行インデックス
	 */
	public static void addErrorTimeOrderInvalid(MospParams mospParams, String beforeName, String afterName, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INVALID_ORDER,
				getRowedFieldName(mospParams, afterName, row), beforeName);
	}
	
	/**
	 * {@link MessageUtility#getRowedFieldName(MospParams, String, Integer)}
	 * を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	protected static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 行番号が付加されたフィールド名を取得
		return MessageUtility.getRowedFieldName(mospParams, fieldName, row);
	}
	
	/**
	 * 勤怠名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤怠名称
	 */
	public static String getNameWorkManage(MospParams mospParams) {
		return mospParams.getName("WorkManage");
	}
	
	/**
	 * 休暇名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：休暇名称
	 */
	public static String getNameVacation(MospParams mospParams) {
		return mospParams.getName("Vacation");
	}
	
	/**
	 * 勤務日名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤務日名称
	 */
	public static String getNameWorkDate(MospParams mospParams) {
		return mospParams.getName("Work", "Day");
	}
	
	/**
	 * 勤務形態名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤務形態名称
	 */
	public static String getNameWorkType(MospParams mospParams) {
		return mospParams.getName("Work", "Form");
	}
	
	/**
	 * 集計名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：集計名称
	 */
	protected static String getNameTotal(MospParams mospParams) {
		return mospParams.getName("Total");
	}
	
	/**
	 * 年月名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：年月名称
	 */
	protected static String getNameYearMonth(MospParams mospParams) {
		return mospParams.getName("Year", "Month");
	}
	
	/**
	 * 年名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：年名称
	 */
	protected static String getNameYear(MospParams mospParams) {
		return mospParams.getName("Year");
	}
	
	/**
	 * 月名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：月名称
	 */
	protected static String getNameMonth(MospParams mospParams) {
		return mospParams.getName("Month");
	}
	
	/**
	 * 打刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 打刻名称
	 */
	protected static String getNameRecordTime(MospParams mospParams) {
		return mospParams.getName("RecordTime");
	}
	
	/**
	 * 始業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 始業名称
	 */
	protected static String getNameStartWork(MospParams mospParams) {
		return mospParams.getName("StartWork");
	}
	
	/**
	 * 終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終業名称
	 */
	public static String getNameEndWork(MospParams mospParams) {
		return mospParams.getName("EndWork");
	}
	
	/**
	 * 休憩入名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩入名称
	 */
	public static String getNameStartRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Into");
	}
	
	/**
	 * 休憩戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩戻名称
	 */
	public static String getNameEndRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Return");
	}
	
	/**
	 * 定時終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 定時終業名称
	 */
	public static String getNameRegularEnd(MospParams mospParams) {
		return mospParams.getName("RegularTime", "EndWork");
	}
	
	/**
	 * 残業有終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 残業有終業名称
	 */
	public static String getNameOverEnd(MospParams mospParams) {
		return mospParams.getName("OvertimeWork", "EffectivenessExistence", "EndWork");
	}
	
	/**
	 * 出勤名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 出勤名称
	 */
	protected static String getNameRegularWork(MospParams mospParams) {
		return mospParams.getName("GoingWork");
	}
	
	/**
	 * 時短時間1名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時短時間1名称
	 */
	protected static String getNameShort1Time(MospParams mospParams) {
		return mospParams.getName("ShortTime", "Time", "No1");
	}
	
	/**
	 * 時短時間2名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時短時間2名称
	 */
	protected static String getNameShort2Time(MospParams mospParams) {
		return mospParams.getName("ShortTime", "Time", "No2");
	}
	
	/**
	 * 分単位休暇A名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 分単位休暇A名称
	 */
	protected static String getNameMinutelyHolidayA(MospParams mospParams) {
		return mospParams.getName("MinutelyHolidayA");
	}
	
	/**
	 * 分単位休暇B名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 分単位休暇B名称
	 */
	protected static String getNameMinutelyHolidayB(MospParams mospParams) {
		return mospParams.getName("MinutelyHolidayB");
	}
	
	/**
	 * 所定労働時間名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 所定労働時間名称
	 */
	protected static String getNamePrescribedWorkTime(MospParams mospParams) {
		return mospParams.getName("Prescribed", "Labor", "Time");
	}
	
	/**
	 * 開始時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 開始時刻
	 */
	public static String getNameStartTime(MospParams mospParams) {
		return mospParams.getName("Start", "Moment");
	}
	
	/**
	 * 終了時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終了時刻
	 */
	public static String getNameEndTime(MospParams mospParams) {
		return mospParams.getName("End", "Moment");
	}
	
	/**
	 * 始業時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 始業時刻名称
	 */
	public static String getNameStartWorkTime(MospParams mospParams) {
		return mospParams.getName("StartWork", "Moment");
	}
	
	/**
	 * 終業時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終業時刻名称
	 */
	public static String getNameEndWorkTime(MospParams mospParams) {
		return mospParams.getName("EndWork", "Moment");
	}
	
	/**
	 * 休憩名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩名称
	 */
	public static String getNameRest(MospParams mospParams) {
		return mospParams.getName("RestTime");
	}
	
}
