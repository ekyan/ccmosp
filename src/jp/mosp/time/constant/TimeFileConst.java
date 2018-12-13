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
 * インポート、エクスポートで用いる定数を宣言する。<br>
 */
public class TimeFileConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeFileConst() {
		// 処理無し
	}
	
	
	/**
	 * インポート区分(勤怠データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_ATTENDANCE			= "import_tmd_attendance";
	
	/**
	 * インポート区分(勤怠集計データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_TOTAL_TIME			= "import_tmd_total_time";
	
	/**
	 * インポート区分(有給休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_PAID_HOLIDAY		= "import_tmd_paid_holiday";
	
	/**
	 * インポート区分(ストック休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY		= "import_tmd_stock_holiday";
	
	/**
	 * インポート区分(時間単位有給休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TIMELY_PAID_HOLIDAY	= "import_tmd_timely_paid_holiday";
	
	/**
	 * インポート区分(休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_HOLIDAY			= "import_tmd_holiday";
	
	/**
	 * エクスポート区分(勤怠データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_ATTENDANCE			= "export_tmd_attendance";
	
	/**
	 * 勤怠データフィールド(打刻始業時間)。
	 */
	public static final String	FIELD_TIME_ROCODE_START_TIME			= "time_recode_start_time";
	
	/**
	 * 勤怠データフィールド(打刻終業時間)。
	 */
	public static final String	FIELD_TIME_ROCODE_END_TIME				= "time_recode_end_time";
	
	/**
	 * エクスポート区分(勤怠集計データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_TOTAL_TIME			= "export_tmd_total_time";
	
	/**
	 * エクスポート区分(有給休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_PAID_HOLIDAY		= "export_tmd_paid_holiday";
	
	/**
	 * エクスポート区分(ストック休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_STOCK_HOLIDAY		= "export_tmd_stock_holiday";
	
	/**
	 * エクスポート区分(休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_HOLIDAY			= "export_tmd_holiday";
	
	/**
	 * エクスポート区分(代休データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_SUB_HOLIDAY		= "export_tmd_sub_holiday";
	
	/**
	 * 勤怠エクスポート(休暇取得データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA	= "export_holiday_request_data";
	
	/**
	 * エクスポート区分(出勤簿)。
	 */
	public static final String	CODE_EXPORT_TYPE_ATTENDANCE_BOOK		= "export_tmd_attendance_book";
	
	/**
	 * フィールド(代休日1)。
	 */
	public static final String	FIELD_REQUEST_DATE1						= "request_date1";
	
	/**
	 * フィールド(代休日2)。
	 */
	public static final String	FIELD_REQUEST_DATE2						= "request_date2";
	
	/**
	 * 出勤簿フィールド(カレンダ日付)。
	 */
	public static final String	FIELD_SHEDULE_DAY						= "schedule_Day";
	
	/**
	 * 出勤簿フィールド(氏名)。
	 */
	public static final String	FIELD_FULL_NAME							= "full_name";
	
	/**
	 * 出勤簿フィールド(勤務形態コード)。
	 */
	public static final String	FIELD_WORK_TYPE_CODE					= "work_type_code";
	
	/**
	 * 出勤簿フィールド(勤務形態略称)。
	 */
	public static final String	FIELD_WORK_TYPE_ABBR					= "work_type_abbr";
	
	/**
	 * 出勤簿フィールド(始業時刻)。
	 */
	public static final String	FIELD_START_TIME						= "start_time";
	
	/**
	 * 出勤簿フィールド(終業時刻)。
	 */
	public static final String	FIELD_END_TIME							= "end_time";
	
	/**
	 * 出勤簿フィールド(勤務時間)。
	 */
	public static final String	FIELD_WORK_TIME							= "work_time";
	
	/**
	 * 出勤簿フィールド(休憩時間)。
	 */
	public static final String	FIELD_REST_TIME							= "rest_time";
	
	/**
	 * 出勤簿フィールド(私用外出時間)。
	 */
	public static final String	FIELD_PRIVATE_TIME						= "private_time";
	
	/**
	 * 出勤簿フィールド(遅刻早退時間)。
	 */
	public static final String	FIELD_LATE_EARLY_TIME					= "late_leave_early_time";
	
	/**
	 * 出勤簿フィールド(法定内残業時間)。
	 */
	public static final String	FIELD_OVER_TIME_IN						= "overtime_in";
	
	/**
	 * 出勤簿フィールド(法定外残業時間)。
	 */
	public static final String	FIELD_OVER_TIME_OUT						= "overtime_out";
	
	/**
	 * 出勤簿フィールド(休日出勤時間)。
	 */
	public static final String	FIELD_WORK_ON_HOLIDAY					= "work_on_holiday";
	
	/**
	 * 出勤簿フィールド(深夜時間)。
	 */
	public static final String	FIELD_LAST_NIGHT						= "late_night";
	
	/**
	 * 出勤簿フィールド(備考)。
	 */
	public static final String	FIELD_TIME_REMARKS						= "time_remarks";
	
	/**
	 * フィールド(有給休暇(全休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_ALL					= "paid_holiday_all";
	
	/**
	 * フィールド(有給休暇(半休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_HALF					= "paid_holiday_half";
	
	/**
	 * フィールド(有給休暇(時休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_TIME					= "paid_holiday_time";
	
	/**
	 * フィールド(ストック休暇(全休))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_ALL					= "stock_holiday_all";
	
	/**
	 * フィールド(ストック休暇(半休))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_HALF				= "stock_holiday_half";
	
	/**
	 * フィールド(代休(全休))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_ALL					= "sub_holiday_all";
	
	/**
	 * フィールド(代休(半休))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_HALF					= "sub_holiday_half";
	
	/**
	 * フィールド(振替休日(全休))。
	 */
	public static final String	FIELD_SUBSTITUTE_HOLIDAY_ALL			= "substitute_holiday_all";
	
	/**
	 * フィールド(全休)。
	 */
	public static final String	FIELD_ALL								= "all";
	
	/**
	 * フィールド(半休)。
	 */
	public static final String	FIELD_HALF								= "half";
	
	/**
	 * エクスポート時間フォーマット(分)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_MINUTES			= 0;
	
	/**
	 * エクスポート時間フォーマット(時間)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_HOURS			= 1;
	
	/**
	 * エクスポート時間フォーマット(HH:MM)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_COLON_SEPARATED	= 2;
	
	/**
	 * エクスポート時間フォーマット(HH.MM)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_DOT_SEPARATED	= 3;
	
}
