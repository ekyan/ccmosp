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
package jp.mosp.framework.utils;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;

/**
 * HTML作成に有用なメソッドを提供する。<br><br>
 * HTMLのヘッダーやフッター等、標準化されたHTMLを作成するのに役立つ。<br>
 */
public class HtmlUtility {
	
	/**
	 * 一覧頁繰りボタン数。<br>
	 */
	public static final int		COUNT_PAGE_BUTTON	= 10;
	
	/**
	 * エラーメッセージ上限。<br>
	 */
	public static final int		COUNT_ERROR_MESSAGE	= 10;
	
	/**
	 * JavaScriptメニュー配列定数名。<br>
	 */
	public static final String	JS_ARY_MENU			= "ARY_MENU";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HtmlUtility() {
		// 処理無し
	}
	
	/**
	 * セレクトオプション出力。<br>
	 * 任意のセレクトオプションを出力する。<br>
	 * @param array セレクトオプション内容
	 * @param value 初期選択値
	 * @return HTMLセレクトオプション文字列
	 */
	public static String getSelectOption(String[][] array, String value) {
		StringBuffer sb = new StringBuffer();
		for (String[] element : array) {
			String selected = "";
			if (element[0].equals(value)) {
				selected = " selected=\"selected\"";
			}
			sb.append("<option value=\"");
			sb.append(escapeHTML(element[0]));
			sb.append("\"");
			sb.append(selected);
			sb.append(">");
			sb.append(escapeHTML(element[1]));
			sb.append("</option>");
		}
		return sb.toString();
	}
	
	/**
	 * セレクトオプションを出力する。<br>
	 * MosPコード情報から対象コードキーの情報を取得して、
	 * セレクトオプションを作成する。<br>
	 * @param mospParams MosPパラメータ
	 * @param codeKey    対象コードキー
	 * @param value      初期選択値
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return HTMLセレクトオプション文字列
	 */
	public static String getSelectOption(MospParams mospParams, String codeKey, String value, boolean needBlank) {
		// MosP設定情報からコード配列を取得
		String[][] array = mospParams.getProperties().getCodeArray(codeKey, needBlank);
		// セレクトオプション出力
		return getSelectOption(array, value);
	}
	
	/**
	 * 必須マーク出力。<br>
	 * MosPフレームワーク標準の必須マークを出力する。<br>
	 * @return HTML必須マーク文字列
	 */
	public static String getRequiredMark() {
		return "<span class=\"RequiredLabel\">*&nbsp;</span>";
	}
	
	/**
	 * HTMLエスケープ。<br>
	 * @param aStr エスケープ対象文字列
	 * @return エスケープ後文字列
	 */
	public static String escapeHTML(String aStr) {
		char c;
		String strTarget = aStr != null ? aStr : "";
		StringBuffer returnStr = new StringBuffer();
		int length = strTarget.length();
		for (int i = 0; i < length; i++) {
			c = strTarget.charAt(i);
			if (c == '<') {
				returnStr = returnStr.append("&lt;");
			} else if (c == '>') {
				returnStr = returnStr.append("&gt;");
			} else if (c == '&') {
				returnStr = returnStr.append("&amp;");
			} else if (c == '"') {
				returnStr = returnStr.append("&quot;");
			} else if (c == '\'') {
				returnStr = returnStr.append("&#39;");
			} else {
				returnStr = returnStr.append(c);
			}
		}
		return new String(returnStr);
	}
	
	/**
	 * ボタンタグ取得。<br>
	 * @param id   id
	 * @param cmd  コマンドNo.
	 * @param name ボタン名称
	 * @return ボタンタグHTML文字列
	 */
	public static String getButtonTag(String id, String cmd, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("<button type=\"button\" ");
		if (id.length() != 0) {
			sb.append("id=\"");
			sb.append(id);
			sb.append("\"");
		}
		sb.append(" onclick=\"doSubmit(document.form, '");
		sb.append(cmd);
		sb.append("')\">");
		sb.append(escapeHTML(name));
		sb.append("</button>");
		return sb.toString();
	}
	
	/**
	 * テキストボックスのHTMLタグを取得する。
	 * @param cls     class
	 * @param id      id
	 * @param name    name
	 * @param value   value
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：テキストボックス)
	 * @return テキストボックスHTMLタグ
	 */
	public static String getTextboxTag(String cls, String id, String name, String value, boolean isLabel) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (id != null && !id.isEmpty()) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(value));
			sb.append("</span>");
			return sb.toString();
		}
		// テキストボックスの場合
		sb.append("<input type=\"text\" ");
		if (cls != null && !cls.isEmpty()) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (id != null && !id.isEmpty()) {
			sb.append("id=\"" + id + "\" ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append("value=\"" + escapeHTML(value) + "\" />");
		return sb.toString();
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。
	 * @param cls     class
	 * @param id      id
	 * @param name    name
	 * @param value   value
	 * @param array   プルダウンオプション配列
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String id, String name, String value, String[][] array,
			boolean isLabel) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (id != null && !id.isEmpty()) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(MospUtility.getCodeName(value, array)));
			sb.append("</span>");
			return sb.toString();
		}
		// プルダウンの場合
		sb.append("<select ");
		if (cls != null && !cls.isEmpty()) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (id != null && !id.isEmpty()) {
			sb.append("id=\"" + id + "\" ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append(">");
		sb.append(getSelectOption(array, value));
		sb.append("</select>");
		return sb.toString();
	}
	
	/**
	 * ロゴイメージタグ取得。<br>
	 * @param logoPath ロゴファイルパス
	 * @param namLogo  ロゴファイルタイトル
	 * @return ロゴイメージタグHTML文字列
	 */
	public static String getTagLogoImage(String logoPath, String namLogo) {
		StringBuffer sb = new StringBuffer();
		if (logoPath != null) {
			sb.append("<img class=\"Logo\" id=\"logo\" src=\"");
			sb.append(MospConst.URL_PUB + logoPath);
			sb.append("\" alt=\"" + namLogo + "\">");
		}
		return sb.toString();
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param value         チェックボックス値
	 * @param selectedArray チェックボックス選択値配列
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(long value, String[] selectedArray) {
		return getChecked(String.valueOf(value), selectedArray);
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param value         チェックボックス値
	 * @param selectedArray チェックボックス選択値配列
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(String value, String... selectedArray) {
		if (selectedArray == null) {
			return getChecked(false);
		}
		for (String selected : selectedArray) {
			if (selected.equals(value)) {
				return getChecked(true);
			}
		}
		return getChecked(false);
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * チェックボックス値と、{@link MospConst#CHECKBOX_ON}を比較する。<br>
	 * @param value チェックボックス値
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(String value) {
		if (value != null && value.equals(MospConst.CHECKBOX_ON)) {
			return getChecked(true);
		}
		return getChecked(false);
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param isChecked チェック判定
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(boolean isChecked) {
		if (isChecked) {
			return "checked=\"checked\"";
		}
		return "";
	}
	
	/**
	 * メッセージ領域を取得する。<br>
	 * {@link MospParams#getMessageList()}、{@link MospParams#getErrorMessageList()}
	 * で得られるメッセージを表示する。
	 * @param params パラメータ
	 * @return HTMLメッセージ領域
	 */
	public static String getMessageDiv(MospParams params) {
		// HTML文字列作成準備
		StringBuffer sb = new StringBuffer();
		// メッセージ取得
		List<String> messageList = params.getMessageList();
		List<String> errorMessageList = params.getErrorMessageList();
		// メッセージ確認
		if (messageList.size() == 0 && errorMessageList.size() == 0) {
			return sb.toString();
		}
		// メッセージ領域作成
		sb.append("<div class=\"Message\">");
		// メッセージ追加
		for (String message : messageList) {
			sb.append("<span class=\"MessageSpan\">");
			sb.append(escapeHTML(message));
			sb.append("</span><br />");
		}
		// エラーメッセージ追加
		int count = 0;
		for (String errorMessage : errorMessageList) {
			// エラーメッセージ上限確認
			if (count >= COUNT_ERROR_MESSAGE) {
				sb.append("<span class=\"ErrorMessageSpan\">");
				sb.append(params.getName("Other"));
				sb.append(errorMessageList.size() - count);
				sb.append(params.getName("Count"));
				sb.append("</span><br />");
				break;
			}
			sb.append("<span class=\"ErrorMessageSpan\">");
			sb.append(escapeHTML(errorMessage));
			sb.append("</span><br />");
			count++;
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * リスト情報出力。<br>
	 * 一覧の件数及び前頁、次頁ボタンを出力する。<br>
	 * 任意の頁に遷移できる機能を持つ。<br>
	 * @param mospParams  MosP処理情報
	 * @param list        リスト
	 * @param command     頁繰りコマンド
	 * @param dataPerPage 1頁あたりのデータ数
	 * @param selectIndex 選択インデックス
	 * @return HTMLリスト情報文字列
	 */
	public static String getListInfoFlex(MospParams mospParams, List<? extends BaseDtoInterface> list, String command,
			int dataPerPage, String selectIndex) {
		StringBuffer sb = new StringBuffer();
		int countAllData = list == null ? 0 : list.size();
		int select = Integer.parseInt(selectIndex);
		int offset = (select - 1) * dataPerPage;
		int full = countAllData / dataPerPage;
		int end = offset + dataPerPage;
		if (countAllData % dataPerPage != 0) {
			full++;
		}
		end = end > countAllData ? countAllData : end;
		sb.append("<div class=\"ListInfo\">");
		sb.append("<table class=\"ListInfoTopTable\">");
		sb.append("<tr>");
		sb.append("<td class=\"RollLinkTd\">");
		if (select - 1 > 0) {
			sb.append("<a ");
			sb.append("onclick=\"submitTransfer(event, null, null, new Array('");
			sb.append(MospConst.PRM_SELECT_INDEX);
			sb.append("', '");
			sb.append(select - 1);
			sb.append("'), '");
			sb.append(command);
			sb.append("')\">");
			sb.append("&lt;&lt;");
			sb.append("</a>");
		}
		sb.append("</td>");
		sb.append("<td>");
		sb.append(countAllData == 0 ? 0 : offset + 1);
		sb.append("&nbsp;");
		sb.append(mospParams.getName("Wave"));
		sb.append("&nbsp;");
		sb.append(end);
		sb.append("&nbsp;/&nbsp;");
		sb.append(countAllData);
		sb.append("&nbsp;");
		sb.append(mospParams.getName("Count"));
		sb.append("</td>");
		sb.append("<td class=\"RollLinkTd\">");
		if (select + 1 <= full) {
			sb.append("<a ");
			sb.append("onclick=\"submitTransfer(event, null, null, new Array('");
			sb.append(MospConst.PRM_SELECT_INDEX);
			sb.append("', '");
			sb.append(select + 1);
			sb.append("'), '");
			sb.append(command);
			sb.append("')\">");
			sb.append("&gt;&gt;");
			sb.append("</a>");
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		if (countAllData <= dataPerPage) {
			sb.append("</div>");
			return sb.toString();
		}
		sb.append("<table class=\"ListInfoBottomTable\">");
		sb.append("<tr>");
		sb.append("<td class=\"ListInfoButton\">");
		if (full != 0) {
			int pageNum = select / COUNT_PAGE_BUTTON * COUNT_PAGE_BUTTON;
			for (int i = -1; i < COUNT_PAGE_BUTTON + 1; i++) {
				if (pageNum + i > 0 && pageNum + i <= full) {
					if (select != pageNum + i) {
						sb.append("<a onclick=\"submitTransfer(event, null, null, new Array('");
						sb.append(MospConst.PRM_SELECT_INDEX);
						sb.append("', '");
						sb.append(pageNum + i);
						sb.append("'), '");
						sb.append(command);
						sb.append("')\">");
						sb.append(mospParams.getName("FrontWithCornerParentheses"));
						sb.append(pageNum + i);
						sb.append(mospParams.getName("BackWithCornerParentheses"));
						sb.append("</a>");
					} else if (select == pageNum + i) {
						sb.append(mospParams.getName("FrontWithCornerParentheses"));
						sb.append(pageNum + i);
						sb.append(mospParams.getName("BackWithCornerParentheses"));
					}
				}
			}
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * JavaScript配列(文字列)を取得する。<br>
	 * @param variableName 変数名
	 * @param array        配列
	 * @return JavaScript配列(文字列)
	 */
	public static String getJsArray(String variableName, String[] array) {
		// JavaScript配列(文字列)準備
		StringBuffer sb = new StringBuffer();
		// JavaScript配列宣言
		sb.append(getJsArrayDeclaration(variableName));
		// JavaScript配列に値を追加
		for (String value : array) {
			sb.append(getJsArrayPush(variableName, "\"" + value + "\""));
		}
		return sb.toString();
	}
	
	/**
	 * JavaScript配列(文字列)を取得する。<br>
	 * @param variableName 変数名
	 * @param array        配列
	 * @return JavaScript配列(文字列)
	 */
	public static String getJsArray(String variableName, String[][] array) {
		// JavaScript配列(文字列)準備
		StringBuffer sb = new StringBuffer();
		// JavaScript配列宣言
		sb.append(getJsArrayDeclaration(variableName));
		// JavaScript配列に値を追加
		for (int i = 0; i < array.length; i++) {
			// 一次元配列作成
			String name = variableName + i;
			sb.append(getJsArray(name, array[i]));
			// JavaScript配列に値を追加
			sb.append(getJsArrayPush(variableName, name));
		}
		return sb.toString();
	}
	
	/**
	 * 選択メニュー(大項目)を取得する。<br>
	 * 画面遷移時に選択しているメニュー(大項目)の配列名を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 選択メニュー
	 */
	@Deprecated
	public static String getSelectMenu(MospParams mospParams) {
		return MenuJsUtility.getSelectMenu(mospParams);
	}
	
	/**
	 * メニュー用JS文字列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return メニュー用JS文字列
	 */
	@Deprecated
	public static String getMenuJs(MospParams mospParams) {
		// 出力文字列準備
		StringBuffer sb = new StringBuffer();
		sb.append("ARY_MENU = ");
		sb.append(MenuJsUtility.getMenuJs(mospParams));
		sb.append(";");
		return sb.toString();
	}
	
	/**
	 * JS配列宣言取得。<br>
	 * @param variableName 変数名
	 * @return JS配列宣言
	 */
	private static String getJsArrayDeclaration(String variableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("var ");
		sb.append(variableName);
		sb.append(" = ");
		sb.append(getJsNewArray(""));
		sb.append(";");
		return sb.toString();
	}
	
	/**
	 * JS配列コンストラクタ取得。<br>
	 * @param value 値
	 * @return JS配列コンストラクタ
	 */
	private static String getJsNewArray(String value) {
		StringBuffer sb = new StringBuffer();
		sb.append("new Array(");
		sb.append(value);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * JS配列push文字列取得。<br>
	 * @param array 配列
	 * @param value 値
	 * @return JS配列push文字列
	 */
	private static String getJsArrayPush(String array, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(array);
		sb.append(".push(");
		sb.append(value);
		sb.append(");");
		return sb.toString();
	}
	
	/**
	 * disabled属性文字列を取得する。<br>
	 * @param disabled disabled設定(true；disabled、false：disabledでない)
	 * @return disabled属性文字列
	 */
	public static String getDisabled(boolean disabled) {
		if (disabled) {
			return " disabled=\"disabled\" ";
		}
		return "";
	}
	
}
