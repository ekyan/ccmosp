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
package jp.mosp.time.constant;

/**
 * MosPシステムで用いるメッセージキーの定数を宣言する。<br><br>
 */
public final class TimeMessageConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeMessageConst() {
		// 処理無し
	}
	
	
	/**
	 * 入力内容エラー時のメッセージコード。<br>
	 */
	public static final String	MSG_NOT_APPROVAL									= "TMW0201";
	
	/**
	 * 時間（分）チェック範囲外エラーメッセージ。<br>
	 */
	public static final String	MSG_MINUTE_CHECK									= "TMW0203";
	
	/**
	 * ラジオチェック不正エラーメッセージ。<br>
	 */
	public static final String	MSG_RADIO_CHECK										= "TMW0205";
	
	/**
	 * 半休取得時休憩相関チェックエラーメッセージ。<br>
	 * 勤務形態管理詳細画面のJavaScriptで、用いられる。<br>
	 */
	public static final String	MSG_HALFDAY_ACQUISITION								= "TMW0212";
	
	/**
	 * 設定情報選択チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_SETUP_INFORMATION								= "TMW0213";
	
	/**
	 * プルダウン存在チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_WORKFORM_EXISTENCE								= "TMW0214";
	
	/**
	 * 削除整合性確認チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_DELETE_RESON_CHECK								= "TMW0217";
	
	/**
	 * 既に登録されているエラーメッセージ。<br>
	 */
	public static final String	MSG_ALREADY_EXIST									= "TMW0222";
	
	/**
	 * 勤怠集計の状態により変更できないエラーメッセージ。<br>
	 */
	public static final String	MSG_ALREADY_CALC_IS_USED							= "TMW0223";
	
	/**
	 * 該当締日コードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_CUTOFF_CODE_IS_USED								= "TMW0224";
	
	/**
	 * 該当勤務形態コードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_WORK_TYPE_CODE_IS_USED							= "TMW0225";
	
	/**
	 * 該当マスタコードが使用されていた場合の警告メッセージ。<br>
	 */
	public static final String	MSG_SELECTED_CODE_IS_USED							= "TMW0226";
	
	/**
	 * 休暇種別MAX件数オーバーエラーメッセージ。<br>
	 */
	public static final String	MSG_HOLIDAY_TYPE_MAX_OVER							= "TMW0227";
	
	/**
	 * カレンダ履歴追加時の該当有効日における重複エラーメッセージ。<br>
	 */
	public static final String	MSG_SCHEDULE_HIST_ALREADY_EXISTED					= "TMW0228";
	
	/**
	 * カレンダ管理詳細画面でのチェックボックスのNULLチェックメッセージコード。<br>
	 */
	public static final String	MSG_SCHEDULE_CHECK									= "TMW0231";
	
	/**
	 * 時刻のフォーマットエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_FORMAT_CHECK								= "TMW0233";
	
	/**
	 * 起算時刻オーバーエラーメッセージ。<br>
	 */
	public static final String	MSG_START_DAY_TIME_CHECK							= "TMW0235";
	
	/**
	 * 始業/終業時刻エラーメッセージ。<br>
	 */
	public static final String	MSG_START_END_TIME_CHECK							= "TMW0236";
	
	/**
	 * 勤務時間の範囲チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_WORK_TIME_OUT_CHECK								= "TMW0237";
	
	/**
	 * 休憩/外出時間重複エラーメッセージ。<br>
	 */
	public static final String	MSG_REST_GOING_OUT_CHECK							= "TMW0238";
	
	/**
	 * 始業時刻エラーメッセージ。<br>
	 */
	public static final String	MSG_START_TIME_CHECK								= "TMW0239";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_1									= "TMW0240";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_2									= "TMW0241";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_3									= "TMW0242";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_4									= "TMW0243";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_5									= "TMW0244";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_6									= "TMW0245";
	
	/**
	 * 設定適用管理情報の適用範囲に含まれていない、または適用する情報に不備の場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_SETTING_APPLICATION_DEFECT						= "TMW0246";
	
	/**
	 * 振替可能な期間を超過している場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_TRANSFER_DAY_EXCESS								= "TMW0248";
	
	/**
	 * 出勤日でない場合のエラーメッセージ。
	 */
	public static final String	MSG_NOT_WORK_DATE									= "TMW0249";
	
	/**
	 * 休暇申請のエラーメッセージ。<br>
	 */
	public static final String	MSG_HOLIDAY_ACTIVATIONDATE							= "TMW0251";
	
	/**
	 * 代休日数のエラーメッセージ。<br>
	 */
	public static final String	MSG_SUBHOLIDAY_DAY_CHECK							= "TMW0252";
	
	/**
	 * 適用する情報に不備の場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_SETTING_APPLICATION_DEFECT_2					= "TMW0253";
	
	/**
	 * 休暇申請する際に休暇日数を超過していた場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_HOLIDAY_NUM_DAYS_EXCESS							= "TMW0254";
	
	/**
	 * 登録できない場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_REGISTER									= "TMW0255";
	
	/**
	 * 1年目にストック休暇が付与できない場合エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_STOCK_VACATION_GRANT						= "TMW0256";
	
	/**
	 * 入社していない社員が選択されている場合エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_JOINED_GRANT								= "TMW0257";
	
	/**
	 * 入社1年目であるため前年度の有給休暇を付与する場合エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_WITH_PAY_VACATION_GRANT						= "TMW0258";
	
	/**
	 * 休暇申請する際に休暇が付与されていなかった場合のエラーメッセージ。<br>
	 */
	public static final String	MSG_HOLIDAY_NOT_GIVE								= "TMW0259";
	
	/**
	 * 入力されている社員コートが正しくない場合エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_EXIST_EMPLOYEES								= "TMW0260";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_8									= "TMW0261";
	
	/**
	 * 未登録時のエラーメッセージ。<br>
	 */
	public static final String	MSG_UNSETTING										= "TMW0262";
	
	/**
	 * 既に手動付与が行われている日付に同じ社員へ手動付与を行う場合エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_EXIST_WITH_PAY_VACATION_GRANT				= "TMW0263";
	
	/**
	 * プルダウン存在チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_WORKFORM_EXISTENCE2								= "TMW0265";
	
	/**
	 * カレンダ管理で使用するエラーメッセージ。<br>
	 */
	public static final String	MSG_CALENDAR_ERROR_MESSAGE							= "TMW0266";
	
	/**
	 * 申請状態が完了で無い場合でその申請状態を変更するように表示するメッセージ。<br>
	 */
	public static final String	MSG_NOT_REQUEST_STATE_COMPLETE						= "TMW0267";
	
	/**
	 * 下書時の始業時刻、終業時刻の未入力メッセージ<br>
	 */
	public static final String	MSG_DRAFT_TIME_NOT_INPUT							= "TMW0268";
	
	/**
	 * 休暇種別管理の削除、無効時に対象コード使われている場合のメッセージ<br>
	 */
	public static final String	MSG_WARNING_TARGET_CODE_VACATION_GRANT_DELETE		= "TMW0269";
	
	/**
	 * 休暇種別管理の対象の項目に0が設定されている場合のメッセージ<br>
	 */
	public static final String	MSG_HOLIDAY_ITEM_ZERO								= "TMW0270";
	
	/**
	 * 休暇種別管理の対象の項目に0が設定されている場合のメッセージ<br>
	 */
	public static final String	MSG_GRANT_PERIOD_LESS								= "TMW0271";
	
	/**
	 * 月次処理が行われている場合のメッセージ<br>
	 */
	public static final String	MSG_MONTHLY_TREATMENT								= "TMW0272";
	
	/**
	 * 勤怠管理対象では無いユーザに対するメッセージ<br>
	 */
	public static final String	MSG_NOT_USER_ATTENDANCE_MANAGEMENT_TARGET			= "TMW0273";
	
	/**
	 * 未締の状態の場合のメッセージ<br>
	 */
	public static final String	MSG_NOT_TIGHTENING									= "TMW0274";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_9									= "TMW0277";
	
	/**
	 * 申請状態が完了してから申請するように表示するメッセージ。<br>
	 */
	public static final String	MSG_NOT_REQUEST_STATE_COMPLETE_2					= "TMW0278";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_10								= "TMW0279";
	
	/**
	 * 遅刻、早退時間が限度時間を超えた場合エラーメッセージ。<br>
	 */
	public static final String	MSG_TARGET_DATE_TARDINESS_LEAVE_EARLY_LIMIT_OVER	= "TMW0282";
	
	/**
	 * 承認解除(振替出勤申請)時のエラーメッセージ。<br>
	 */
	public static final String	MSG_WORK_ON_HOLIDAY_NOT_APPROVER					= "TMW0283";
	
	/**
	 * 承認解除(振替出勤申請)時のエラーメッセージ。<br>
	 */
	public static final String	MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2					= "TMW0284";
	
	/**
	 * 下書時のエラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_DRAFT										= "TMW0285";
	
	/**
	 * 翌日の始業時刻のエラーメッセージ。<br>
	 */
	public static final String	MSG_TOMORROW_WORK_BEGIN								= "TMW0286";
	
	/**
	 * 連続休暇申請重複エラーメッセージ。<br>
	 */
	public static final String	MSG_DUPLICATE_CONSECUTIVE_HOLIDAYS					= "TMW0288";
	
	/**
	 * 申請時のエラーメッセージ。（申請してください。）
	 */
	public static final String	MSG_REQUEST_CHECK_11								= "TMW0304";
	
	/**
	 * 範囲指定のエラーメッセージ（範囲を選択してください。）<br>
	 */
	public static final String	MSG_RANGE_SELECT									= "TMW0305";
	
	/**
	 * 形式不正のエラーメッセージ（の形式が不正です。）。<br>
	 */
	public static final String	MSG_FORM_INJUSTICE									= "TMW0306";
	
	/**
	 * ファイル内重複エラーメッセージ。<br>
	 */
	public static final String	MSG_FILE_REPETITION									= "TMW0307";
	
	/**
	 * 有給休暇付与設定情報取得エラーメッセージ。<br>
	 */
	public static final String	MSG_NOT_EXIST_HOLIDAY_INFO							= "TMW0308";
	
	/**
	 * 申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_REQUEST_CHECK_12								= "TMW0309";
	
	/**
	 * 時間の範囲チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_INSIDE_CHECK								= "TMW0310";
	
	/**
	 * 時間の範囲チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_OUTSIDE_CHECK								= "TMW0311";
	
	/**
	 * 時間の重複チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_DUPLICATION_CHECK							= "TMW0312";
	
	/**
	 * 時間の重複チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_DUPLICATION_CHECK_2						= "TMW0314";
	
	/**
	 * 時間の重複チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_TIME_DUPLICATION_CHECK_3						= "TMW0315";
	
	/**
	 * 勤怠の承認解除時のエラーメッセージ。<br>
	 */
	public static final String	MSG_ATTENDANCE_CANCEL_CHECK							= "TMW0316";
	
	/**
	 * 有給休暇申請時のエラーメッセージ。<br>
	 */
	public static final String	MSG_PAID_HOLIDAY_REQUEST_CHECK						= "TMW0317";
	
	/**
	 * 未選択時のメッセージコード。<br>
	 */
	public static final String	MSG_REQUIRED_SELECTION								= "TMW0319";
	
	/**
	 * 時短時間の重複チェックエラーメッセージ。<br>
	 */
	public static final String	MSG_SHORT_TIME_DUPLICATION_CHECK					= "TMW0320";

	/**
	 * 分単位休暇全休チェックのエラーメッセージ。<br>
	 */
	public static final String	MSG_MINUTELY_HOLIDAY								= "TMW0324";
	
	/**
	 * 勤務時間整合性チェック(マイナス値)のエラーメッセージ。<br>
	 */
	public static final String	MSG_MINUS_WORK_TIME									= "TMW0325";
	
	/**
	 * 分単位休暇(全休)下書時の申請チェックのエラーメッセージ。<br>
	 */
	public static final String	MSG_DRAFT_MINUTELY_HOLIDAY							= "TMW0326";
	
	/**
	 * 汎用エラーメッセージ。
	 */
	public static final String	MSG_GENERAL_ERROR									= "TMW0327";
	
	/**
	 * 打刻成功時のメッセージ。<br>
	 */
	public static final String	MSG_RECORD_TIME										= "TMI0001";
	
	/**
	 * 半休申請時のメッセージ。<br>
	 */
	public static final String	MSG_HALF_HOLIDAY_REQUEST							= "TMI0002";
	
}
