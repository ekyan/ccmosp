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
package jp.mosp.platform.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.constant.PlatformConst;

/**
 * 名称に関するユーティリティクラス。<br>
 * 
 * プラットフォームにおいて作成される名称は、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class PlatformNamingUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformNamingUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 人事管理
	 */
	public static String menuHumanManage(MospParams mospParams) {
		return mospParams.getName("menuHumanManage");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユーザ
	 */
	public static String user(MospParams mospParams) {
		return mospParams.getName("User");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象ユーザ
	 */
	public static String targetUser(MospParams mospParams) {
		return mospParams.getName("Target", "User");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユーザID
	 */
	public static String userId(MospParams mospParams) {
		return mospParams.getName("User", "Id");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワード
	 */
	public static String password(MospParams mospParams) {
		return mospParams.getName("Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 変更日
	 */
	public static String changeDate(MospParams mospParams) {
		return mospParams.getName("Change", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 住所情報
	 */
	public static String addressInfo(MospParams mospParams) {
		return mospParams.getName("Address", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 郵便番号
	 */
	public static String postalCode(MospParams mospParams) {
		return mospParams.getName("PostalCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 都道府県
	 */
	public static String prefecture(MospParams mospParams) {
		return mospParams.getName("Prefecture");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 市区町村
	 */
	public static String city(MospParams mospParams) {
		return mospParams.getName("City");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 番地
	 */
	public static String streetAddress(MospParams mospParams) {
		return mospParams.getName("StreetAddress");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 建物
	 */
	public static String building(MospParams mospParams) {
		return mospParams.getName("Building");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 電話情報
	 */
	public static String phoneInfo(MospParams mospParams) {
		return mospParams.getName("Phone", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 電話番号
	 */
	public static String phoneNumber(MospParams mospParams) {
		return mospParams.getName("Phone", "Number");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return コード
	 */
	public static String code(MospParams mospParams) {
		return mospParams.getName("Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員
	 */
	public static String employee(MospParams mospParams) {
		return mospParams.getName("Employee");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象社員
	 */
	public static String targetEmployee(MospParams mospParams) {
		return mospParams.getName("Target", "Employee");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員コード
	 */
	public static String employeeCode(MospParams mospParams) {
		return mospParams.getName("EmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員名
	 */
	public static String employeeName(MospParams mospParams) {
		return mospParams.getName("Employee", "FirstName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者職位等級範囲
	 */
	public static String approverPositionGrade(MospParams mospParams) {
		return mospParams.getName("ApproverPositionGrade");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 範囲
	 */
	public static String range(MospParams mospParams) {
		return mospParams.getName("Range");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 、
	 */
	public static String touten(MospParams mospParams) {
		return mospParams.getName("Touten");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 空白
	 */
	public static String blank(MospParams mospParams) {
		return mospParams.getName("Blank");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 自動設定
	 */
	public static String autoNumbering(MospParams mospParams) {
		return mospParams.getName("AutoNumbering");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有
	 */
	public static String exsistAbbr(MospParams mospParams) {
		return mospParams.getName("EffectivenessExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 無
	 */
	public static String notExsistAbbr(MospParams mospParams) {
		return mospParams.getName("InactivateExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名
	 */
	public static String firstName(MospParams mospParams) {
		return mospParams.getName("FirstName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 姓
	 */
	public static String lastName(MospParams mospParams) {
		return mospParams.getName("LastName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 氏名
	 */
	public static String fullName(MospParams mospParams) {
		return mospParams.getName("FullName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ：
	 */
	public static String colon(MospParams mospParams) {
		return mospParams.getName("Colon");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 期間
	 */
	public static String period(MospParams mospParams) {
		return mospParams.getName("Period");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象期間
	 */
	public static String targetPeriod(MospParams mospParams) {
		return mospParams.getName("Target", "Period");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索年月
	 */
	public static String searchYearMonth(MospParams mospParams) {
		return mospParams.getName("Search", "Year", "Month");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索区分
	 */
	public static String searchType(MospParams mospParams) {
		return mospParams.getName("Search", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索
	 */
	public static String search(MospParams mospParams) {
		return mospParams.getName("Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員検索
	 */
	public static String searchEmployee(MospParams mospParams) {
		return mospParams.getName("Employee", "Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象検索
	 */
	public static String targetSearch(MospParams mospParams) {
		return mospParams.getName("Target", "Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員一覧
	 */
	public static String employeeList(MospParams mospParams) {
		return mospParams.getName("Employee", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ～
	 */
	public static String wave(MospParams mospParams) {
		return mospParams.getName("Wave");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日
	 */
	public static String activateDate(MospParams mospParams) {
		return mospParams.getName("ActivateDate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効/無効
	 */
	public static String inactivate(MospParams mospParams) {
		return mospParams.getName("Effectiveness", "Slash", "Inactivate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有/無
	 */
	public static String inactivateAbbr(MospParams mospParams) {
		return mospParams.getName("EffectivenessExistence", "Slash", "InactivateExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 年
	 */
	public static String year(MospParams mospParams) {
		return mospParams.getName("Year");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 月
	 */
	public static String month(MospParams mospParams) {
		return mospParams.getName("Month");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 日
	 */
	public static String day(MospParams mospParams) {
		return mospParams.getName("Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 年月日
	 */
	public static String yearMonthDay(MospParams mospParams) {
		// 年月日文字列を準備
		StringBuilder sb = new StringBuilder(year(mospParams));
		sb.append(month(mospParams));
		sb.append(day(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時
	 */
	public static String hour(MospParams mospParams) {
		return mospParams.getName("Hour");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 分
	 */
	public static String minutes(MospParams mospParams) {
		return mospParams.getName("Minutes");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 週間
	 */
	public static String amountWeek(MospParams mospParams) {
		return mospParams.getName("AmountWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ヶ月
	 */
	public static String amountMonth(MospParams mospParams) {
		return mospParams.getName("AmountMonth");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 決定
	 */
	public static String decision(MospParams mospParams) {
		return mospParams.getName("Decision");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 変更
	 */
	public static String change(MospParams mospParams) {
		return mospParams.getName("Change");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 選択
	 */
	public static String select(MospParams mospParams) {
		return mospParams.getName("Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 新規登録
	 */
	public static String newInsert(MospParams mospParams) {
		return mospParams.getName("New", "Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 登録
	 */
	public static String insert(MospParams mospParams) {
		return mospParams.getName("Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 更新
	 */
	public static String update(MospParams mospParams) {
		return mospParams.getName("Update");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 一括更新
	 */
	public static String batchUpdate(MospParams mospParams) {
		return mospParams.getName("Bulk", "Update");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 編集
	 */
	public static String edit(MospParams mospParams) {
		return mospParams.getName("Edit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴追加
	 */
	public static String addHistory(MospParams mospParams) {
		return mospParams.getName("History", "Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴編集
	 */
	public static String edtiHistory(MospParams mospParams) {
		return mospParams.getName("History", "Edit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 複製
	 */
	public static String replication(MospParams mospParams) {
		return mospParams.getName("Replication");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 削除
	 */
	public static String delete(MospParams mospParams) {
		return mospParams.getName("Delete");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴削除
	 */
	public static String deleteHistory(MospParams mospParams) {
		return mospParams.getName("History", "Delete");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴
	 */
	public static String history(MospParams mospParams) {
		return mospParams.getName("History");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 確認
	 */
	public static String confirmation(MospParams mospParams) {
		return mospParams.getName("Confirmation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 下書
	 */
	public static String draft(MospParams mospParams) {
		return mospParams.getName("WorkPaper");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 追加
	 */
	public static String add(MospParams mospParams) {
		return mospParams.getName("Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 解除
	 */
	public static String release(MospParams mospParams) {
		return mospParams.getName("Release");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 行
	 */
	public static String row(MospParams mospParams) {
		return mospParams.getName("Row");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ▲ページの先頭へ
	 */
	public static String topOfPage(MospParams mospParams) {
		return mospParams.getName("UpperTriangular", "TopOfPage");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return レ
	 */
	public static String checked(MospParams mospParams) {
		return mospParams.getName("Checked");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ●
	 */
	public static String selected(MospParams mospParams) {
		return mospParams.getName("Selected");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 基本情報
	 */
	public static String basisInfo(MospParams mospParams) {
		return mospParams.getName("Basis", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 最新
	 */
	public static String latest(MospParams mospParams) {
		return mospParams.getName("Latest");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロール
	 */
	public static String role(MospParams mospParams) {
		return mospParams.getName("Role");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロールコード
	 */
	public static String roleCode(MospParams mospParams) {
		return mospParams.getName("Role", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロール区分
	 */
	public static String roleType(MospParams mospParams) {
		return mospParams.getName("Role", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 追加ロール
	 */
	public static String extraRole(MospParams mospParams) {
		return mospParams.getName("Add", "Role");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 人事情報
	 */
	public static String humanInfo(MospParams mospParams) {
		return mospParams.getName("HumanInfo", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 入社
	 */
	public static String join(MospParams mospParams) {
		return mospParams.getName("Joined");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職
	 */
	public static String retire(MospParams mospParams) {
		return mospParams.getName("RetirementOn");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職
	 */
	public static String suspension(MospParams mospParams) {
		return mospParams.getName("RetirementLeave");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地
	 */
	public static String workPlace(MospParams mospParams) {
		return mospParams.getName("WorkPlace");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用契約
	 */
	public static String employmentContract(MospParams mospParams) {
		return mospParams.getName("EmploymentContract");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属
	 */
	public static String section(MospParams mospParams) {
		return mospParams.getName("Section");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属情報
	 */
	public static String sectionInfo(MospParams mospParams) {
		return mospParams.getName("Section", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位
	 */
	public static String position(MospParams mospParams) {
		return mospParams.getName("Position");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位情報
	 */
	public static String positionInfo(MospParams mospParams) {
		return mospParams.getName("Position", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メールアドレス
	 */
	public static String mailAddress(MospParams mospParams) {
		return mospParams.getName("MailAddress");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 現在のパスワード
	 */
	public static String currentPassrword(MospParams mospParams) {
		return mospParams.getName("PresentTime", "Of", "Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 新しいパスワード
	 */
	public static String newPassrword(MospParams mospParams) {
		return mospParams.getName("ItNew", "Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワード入力確認
	 */
	public static String confirmPassrword(MospParams mospParams) {
		return mospParams.getName("Password", "Input", "Confirmation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ログイン
	 */
	public static String login(MospParams mospParams) {
		return mospParams.getName("Login");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ログアウト
	 */
	public static String logout(MospParams mospParams) {
		return mospParams.getName("Logout");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return エクスポート情報
	 */
	public static String exportInfo(MospParams mospParams) {
		return mospParams.getName("Export", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間
	 */
	public static String time(MospParams mospParams) {
		return mospParams.getName("Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 画面
	 */
	public static String screen(MospParams mospParams) {
		return mospParams.getName("Screen");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示設定
	 */
	public static String displaySetting(MospParams mospParams) {
		return mospParams.getName("DisplaySetting");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請
	 */
	public static String application(MospParams mospParams) {
		return mospParams.getName("Application");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return アプリケーション設定キー
	 */
	public static String appKey(MospParams mospParams) {
		return mospParams.getName("AppKey");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return へ
	 */
	public static String to(MospParams mospParams) {
		return mospParams.getName("To");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 赤
	 */
	public static String red(MospParams mospParams) {
		return mospParams.getName("Red");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 黄
	 */
	public static String yellow(MospParams mospParams) {
		return mospParams.getName("Yellow");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return -
	 */
	public static String hyphen(MospParams mospParams) {
		return mospParams.getName("Hyphen");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return /
	 */
	public static String slash(MospParams mospParams) {
		return mospParams.getName("Slash");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ※
	 */
	public static String signStar(MospParams mospParams) {
		return mospParams.getName("SignStar");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メール送信
	 */
	public static String sendMail(MospParams mospParams) {
		return mospParams.getName("SendMail");
	}
	
	/**
	 * @param mospParams       MosP処理情報
	 * @param activateDateMode 有効日モード
	 * @return 決定or変更
	 */
	public static String activeteDateButton(MospParams mospParams, String activateDateMode) {
		return activateDateMode.equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? change(mospParams)
				: decision(mospParams);
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param enclosed   カッコ内の文字列
	 * @return (enclosed)
	 */
	public static String parentheses(MospParams mospParams, String enclosed) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("FrontParentheses"));
		sb.append(enclosed);
		sb.append(mospParams.getName("BackParentheses"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param enclosed   カッコ内の文字列
	 * @return 【enclosed】
	 */
	public static String cornerParentheses(MospParams mospParams, String enclosed) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(enclosed);
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param count 件数
	 * @return 全count件
	 */
	public static String allCount(MospParams mospParams, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("All"));
		sb.append(count);
		sb.append(mospParams.getName("Count"));
		return sb.toString();
	}
	
}
