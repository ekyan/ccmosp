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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MessageProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.property.MospProperties;

/**
 * MosPアプリケーションを開発する上で有用なメソッドを提供する。<br><br>
 */
public final class MospUtility {
	
	/**
	 * Bean及びDAO接尾辞リスト。<br>
	 */
	public static final String[]	SUFFIX_ARRAY		= { "BeanInterface", "Bean", "Interface" };
	
	/**
	 * 区切文字(半角スペース)。
	 */
	public static final char		CHR_SEPARATOR_SPACE	= ' ';
	
	/**
	 * コマンド末尾のワイルドカード。
	 */
	public static final String		WILD_CARD_COMMAND	= "*";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospUtility() {
		// 処理無し
	}
	
	/**
	 * モデルクラス名を取得する。<br>
	 * <br>
	 * モデルインターフェースからモデルキーを取得し、MosP設定情報からモデル設定情報を取得する。<br>
	 * <br>
	 * 対象日がnullの場合、モデル設定情報からモデルクラス名(モデル有効日指定無し)を取得する。<br>
	 * <br>
	 * 対象日がnullでない場合、モデルクラス名群から対象日以前で最新のモデルクラス名を取得する。<br>
	 * 但し、対象日以前のキー(モデル有効日)が無い場合は、モデルクラス名(モデル有効日指定無し)を
	 * 取得する。<br>
	 * <br>
	 * @param cls            対象モデルインターフェース
	 * @param mospProperties MosP設定情報
	 * @param targetDate     対象日
	 * @return モデルクラス名
	 * @throws MospException モデルクラス名の取得に失敗した場合
	 */
	public static String getModelClass(Class<?> cls, MospProperties mospProperties, Date targetDate)
			throws MospException {
		// モデルインターフェースからモデルキーを取得
		String modelKey = getModelKey(cls);
		// MosP設定情報からモデル設定情報群を取得
		Map<String, ModelProperty> modelProperties = mospProperties.getModelProperties();
		// モデル設定情報群からモデル設定情報を取得
		ModelProperty modelProperty = modelProperties.get(modelKey);
		// モデル設定情報確認
		if (modelProperty == null) {
			throw new MospException(new Exception(), ExceptionConst.EX_FAIL_CLASS_NAME, modelKey);
		}
		// 対象日がnullの場合
		if (targetDate == null) {
			// モデル設定情報からモデルクラス名を取得
			return modelProperty.getModelClass();
		}
		// モデル設定情報からモデルクラス名群を取得
		Map<Date, String> modelClassMap = modelProperty.getModelClassMap();
		// モデルクラス名群のキーをリストに変換
		List<Date> keyList = new ArrayList<Date>(modelClassMap.keySet());
		// キーリストをソート(降順)
		Collections.sort(keyList);
		Collections.reverse(keyList);
		// キー毎に処理
		for (Date key : keyList) {
			if (key.after(targetDate)) {
				// 対象外
				continue;
			}
			return modelClassMap.get(key);
		}
		// モデルクラス名を取得(対象日以前で最も新しいキーが無い場合)
		return modelProperty.getModelClass();
	}
	
	/**
	 * モデルインターフェースからモデルキーを取得する。<br>
	 * @param cls 対象モデルインターフェース
	 * @return モデルキー
	 */
	protected static String getModelKey(Class<?> cls) {
		// クラス名取得
		String key = cls.getSimpleName();
		// モデルキー取得
		for (String suffix : SUFFIX_ARRAY) {
			if (key.indexOf(suffix) == key.length() - suffix.length()) {
				key = key.replace(suffix, "");
				break;
			}
		}
		return key;
	}
	
	/**
	 * メッセージ設定情報をJavaScriptファイルに変換する。<br>
	 * @param mospProperties MosP設定情報
	 * @throws MospException ファイル出力に失敗した場合 
	 */
	public static void outputMessageJs(MospProperties mospProperties) throws MospException {
		// JavaScriptファイルパス及びJavaScript変数名宣言
		final String path = mospProperties.getApplicationProperty(MospConst.APP_DOCBASE) + "/pub/common/js/message.js";
		final String name = "messages";
		// メッセージ設定情報群取得
		Map<String, MessageProperty> messageProperties = mospProperties.getMessageProperties();
		// 出力文字列作成
		StringBuffer sb = new StringBuffer();
		sb.append("var ");
		sb.append(name);
		sb.append(" = new Object();");
		sb.append(MospConst.LINE_SEPARATOR);
		for (Entry<String, MessageProperty> entry : messageProperties.entrySet()) {
			// メッセージ設定情報取得
			MessageProperty messageProperty = entry.getValue();
			// クライアント利用可否確認
			if (messageProperty.getClientAvailable() == false) {
				continue;
			}
			// 要素追加
			sb.append(name);
			sb.append("[\"");
			sb.append(messageProperty.getKey());
			sb.append("\"] = \"");
			sb.append(messageProperty.getMessageBody());
			sb.append("\";");
			sb.append(MospConst.LINE_SEPARATOR);
		}
		// ファイル出力
		outputFile(mospProperties, path, sb.toString());
	}
	
	/**
	 * プルダウン用配列から、コードに対応するコードアイテムキーを取得する。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コードアイテムキー
	 */
	public static String getCodeItemCode(String code, String[][] array) {
		// コード確認
		if (code == null) {
			return "";
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return element[0];
			}
		}
		return code;
	}
	
	/**
	 * プルダウン用配列から、コードに対応する名称を取得する。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コード名称
	 */
	public static String getCodeName(String code, String[][] array) {
		// コード確認
		if (code == null) {
			return "";
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return element[1];
			}
		}
		return code;
	}
	
	/**
	 * ファイルを出力する。
	 * @param mospProperties MosP設定情報
	 * @param path 出力ファイルパス
	 * @param body 出力内容
	 * @throws MospException ファイル出力に失敗した場合
	 */
	public static void outputFile(MospProperties mospProperties, String path, String body) throws MospException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(path, false);
			osw = new OutputStreamWriter(fos, mospProperties.getApplicationProperty(MospConst.APP_CHARACTER_ENCODING));
			osw.write(body);
		} catch (FileNotFoundException e) {
			throw new MospException(e);
		} catch (UnsupportedEncodingException e) {
			throw new MospException(e);
		} catch (IOException e) {
			throw new MospException(e);
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				throw new MospException(e);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					throw new MospException(e);
				}
			}
		}
	}
	
	/**
	 * 人名を取得する。<br>
	 * @param firstName 名
	 * @param lastName  姓
	 * @return 人名
	 */
	public static String getHumansName(String firstName, String lastName) {
		return concat(lastName, firstName);
	}
	
	/**
	 * 連結文字配列を半角スペースで連結する。<br>
	 * @param strs      被連結文字配列
	 * @return 連結された文字列
	 */
	public static String concat(String... strs) {
		return concat(CHR_SEPARATOR_SPACE, strs);
	}
	
	/**
	 * 連結文字配列を区切り文字で連結する。<br>
	 * @param separator 区切文字
	 * @param strs      被連結文字配列
	 * @return 連結された文字列
	 */
	public static String concat(char separator, String... strs) {
		// 連結文字列準備
		StringBuffer sb = new StringBuffer();
		// 被連結文字配列確認
		if (strs == null) {
			return sb.toString();
		}
		// 被連結文字毎に処理
		for (String str : strs) {
			// 被連結文字確認
			if (str == null || str.isEmpty()) {
				continue;
			}
			// 連結文字列確認
			if (sb.length() != 0) {
				// 区切文字追加
				sb.append(separator);
			}
			// 被連結文字追加
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * 対象文字列を正規表現の区切りで分割する。<br>
	 * 前後に空白が存在した場合は、その空白を除く。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return 文字列の配列
	 */
	public static String[] split(String target, String regex) {
		// 対象文字列が空の場合
		if (target.trim().isEmpty()) {
			return new String[0];
		}
		// 対象文字列を正規表現の区切りで分割
		String[] array = target.split(regex);
		// 空白除去
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	
	/**
	 * コマンドの末尾をワイルドカード化した文字列を取得する。<br>
	 * 既に末尾がワイルドカード化されている場合、
	 * ワイルドカードを除いてから処理を行う。<br>
	 * @param command コマンド
	 * @return コマンドの末尾をワイルドカード化した文字列
	 */
	public static String getWildCardCommand(String command) {
		// コマンドからワイルドカードを除去
		String wildCardCommand = command.replaceAll("\\*", "");
		// コマンド確認
		if (wildCardCommand.isEmpty()) {
			return wildCardCommand;
		}
		// コマンド末尾をワイルドカード化
		return wildCardCommand.substring(0, wildCardCommand.length() - 1) + WILD_CARD_COMMAND;
	}
	
	/**
	 * 配列(String)をリスト(String)に変換する。<br>
	 * @param array 対象配列(String)
	 * @return リスト(String)
	 */
	public static List<String> asList(String[] array) {
		return Arrays.asList(array);
	}
	
	/**
	 * リスト(String)を配列(String)に変換する。<br>
	 * @param list 対象リスト(String)
	 * @return 配列(String)
	 */
	public static String[] toArray(List<String> list) {
		return list.toArray(new String[1]);
	}
	
	/**
	 * 配列(String)を区切文字で区切った文字列にする。<br>
	 * @param array     対象配列(String)
	 * @param separator 区切文字
	 * @return リスト(String)
	 */
	public static String toSeparatedString(String[] array, String separator) {
		// 字列準備
		StringBuffer sb = new StringBuffer();
		// 個人ID毎に処理
		for (String str : array) {
			// 文字列追加
			sb.append(str);
			sb.append(separator);
		}
		// 最終区切文字除去
		if (sb.length() > 0) {
			sb.delete(sb.lastIndexOf(separator), sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * 対象文字列を制限文字列で切った文字列を取得する。<br>
	 * @param value  対象文字列
	 * @param length 制限文字数
	 * @return 対象文字列を制限文字列で切った文字列
	 */
	public static String substring(String value, int length) {
		// 対象文字列が空である場合
		if (value == null || value.isEmpty() || length < 0) {
			return "";
		}
		// 対象文字列の文字数が制限文字数より小さい場合
		if (value.length() < length) {
			return value;
		}
		// 対象文字列を制限文字列で切った文字列を取得
		return value.substring(0, length);
	}
	
	/**
	 * 例外スタックトレースを取得する。<br>
	 * @param ex 例外オブジェクト
	 * @return 例外スタックトレース文字列
	 */
	public static String getStackTrace(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String msg = sw.toString();
		msg = msg.replaceAll(MospConst.LINE_SEPARATOR, "");
		return msg;
	}
	
}
