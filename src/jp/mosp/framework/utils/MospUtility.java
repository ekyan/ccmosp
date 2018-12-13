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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.TopicPath;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.AddonProperty;
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
	private static final String[]	SUFFIX_ARRAY		= { "BeanInterface", "Bean", "Interface" };
	
	/**
	 * 区切文字(半角スペース)。
	 */
	public static final char		CHR_SEPARATOR_SPACE	= ' ';
	
	/**
	 * コマンド末尾のワイルドカード。
	 */
	public static final String		WILD_CARD_COMMAND	= "*";
	
	/**
	 * 文字コード（UTF-8）。
	 */
	public static final String		CHARACTER_ENCODING	= "UTF-8";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospUtility() {
		// 処理無し
	}
	
	/**
	 * モデルキーからモデルクラス名を取得する。<br>
	 * <br>
	 * 対象日がnullの場合、モデル設定情報からモデルクラス名(モデル有効日指定無し)を取得する。<br>
	 * <br>
	 * 対象日がnullでない場合、モデルクラス名群から対象日以前で最新のモデルクラス名を取得する。<br>
	 * 但し、対象日以前のキー(モデル有効日)が無い場合は、モデルクラス名(モデル有効日指定無し)を
	 * 取得する。<br>
	 * <br>
	 * @param modelKey       モデルキー
	 * @param mospProperties MosP設定情報
	 * @param targetDate     対象日
	 * @return モデルクラス名
	 * @throws MospException モデルクラス名の取得に失敗した場合
	 */
	public static String getModelClass(String modelKey, MospProperties mospProperties, Date targetDate)
			throws MospException {
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
	 * モデルクラス名を取得する。<br>
	 * <br>
	 * モデルインターフェースからモデルキーを取得し、MosP設定情報からモデル設定情報を取得する。<br>
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
		// モデルキーからモデルクラス名を取得
		return getModelClass(modelKey, mospProperties, targetDate);
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
	 * コードキーに対応するプルダウン用配列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 */
	public static String[][] getCodeArray(MospParams mospParams, String codeKey, boolean needBlank) {
		// プルダウン用配列を取得
		return mospParams.getProperties().getCodeArray(codeKey, needBlank);
	}
	
	/**
	 * コードに対応する名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param code       対象コード
	 * @param codeKey    コードキー
	 * @return コード名称
	 */
	public static String getCodeName(MospParams mospParams, String code, String codeKey) {
		return getCodeName(code, getCodeArray(mospParams, codeKey, false));
	}
	
	/**
	 * コードに対応する名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param code       対象コード
	 * @param codeKey    コードキー
	 * @return コード名称
	 */
	public static String getCodeName(MospParams mospParams, int code, String codeKey) {
		// コード名称を取得
		return getCodeName(mospParams, String.valueOf(code), codeKey);
	}
	
	/**
	 * プルダウン用配列を用いて、コードが存在するかを確認する。<br>
	 * <br>
	 * 対象コードがnullである場合、falseを返す。<br>
	 * <br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	public static boolean isCodeExist(String code, String[][] array) {
		// 対象コードがnullである場合
		if (code == null) {
			return false;
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * コードキーに対応する値のリストを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return 値のリスト
	 */
	public static List<String> getCodeList(MospParams mospParams, String codeKey, boolean needBlank) {
		// 値のリストを準備
		List<String> list = new ArrayList<String>();
		// コードキーに対応するプルダウン用配列を取得
		String[][] codeArray = getCodeArray(mospParams, codeKey, needBlank);
		// プルダウン用配列毎に処理
		for (String[] array : codeArray) {
			// 値をリストに設定
			list.add(array[0]);
		}
		// 値のリストを取得
		return list;
	}
	
	/**
	 * コードキーに対応するマップ(キー、値)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @return マップ(キー、値)
	 */
	public static Map<String, String> getCodeMap(MospParams mospParams, String codeKey) {
		// コードキーに対応するマップ(キー、値)を取得
		return asMap(getCodeArray(mospParams, codeKey, false));
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
	 * ログインユーザの個人IDを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ログインユーザの個人ID
	 */
	public static String getLoginPersonalId(MospParams mospParams) {
		// ログインユーザ情報が存在しない場合
		if (mospParams.getUser() == null) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// ログインユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// ログインユーザの個人IDが取得できなかった場合
		if (isEmpty(personalId)) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// ログインユーザの個人IDを取得
		return personalId;
	}
	
	/**
	 * 最新のパンくず名称(表示している画面の名称)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ログインユーザの個人ID
	 */
	public static String getTopicPathName(MospParams mospParams) {
		// 最新のパンくず情報を取得
		TopicPath topicPath = getLastValue(mospParams.getTopicPathList());
		// 最新のパンくず情報が取得できなかった場合
		if (topicPath == null) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// パンくず名称を取得
		String name = topicPath.getName();
		// パンくず名称が取得できなかった場合
		if (isEmpty(name)) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// パンくず名称を取得
		return name;
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
	 * アドオンが有効であるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @param addonKey   アドオンキー。
	 * @return 確認結果(true：アドオンが有効である、false：有効でない)
	 */
	public static boolean isAddonValid(MospParams mospParams, String addonKey) {
		// MosP設定情報(アドオン)を取得
		AddonProperty addon = mospParams.getProperties().getAddonProperties().get(addonKey);
		// MosP設定情報(アドオン)が取得できなかった場合
		if (addon == null) {
			// 有効でないと判断
			return false;
		}
		// アドオンが有効であるかを取得
		return addon.isAddonValid();
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
	public static String concat(Object separator, String... strs) {
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
		if (target == null || target.trim().isEmpty()) {
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
	 * 対象文字列を正規表現の区切りで分割しリストを取得する。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return 文字列のリスト
	 */
	public static List<String> asList(String target, String regex) {
		return asList(split(target, regex));
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
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * 文字列(String)を配列(String)に変換する。<br>
	 * @param strs 文字列の配列
	 * @return 配列(String)
	 */
	public static String[] toArray(String... strs) {
		return strs;
	}
	
	/**
	 * リスト(String)を配列(String)に変換する。<br>
	 * @param list 対象リスト(String)
	 * @return 配列(String)
	 */
	public static Integer[] toArrayInt(List<Integer> list) {
		return list.toArray(new Integer[list.size()]);
	}
	
	/**
	 * 二次元配列(String)をマップ(String)に変換する。<br>
	 * 各配列には、2つの要素(キーと値)があるものとする。<br>
	 * @param arrays 対象二次元配列(String)
	 * @return マップ(String)
	 */
	public static Map<String, String> asMap(String[][] arrays) {
		// マップを準備
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 配列毎に処理
		for (String[] array : arrays) {
			// キーと値を設定
			map.put(array[0], array[1]);
		}
		// マップを取得
		return map;
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
	 * コレクションを区切文字で区切った文字列を取得する。<br>
	 * @param collection コレクション
	 * @param separator  区切文字
	 * @return リスト(String)
	 */
	public static String toSeparatedString(Collection<String> collection, String separator) {
		// コレクションを配列に変換
		String[] array = MospUtility.toArray(new ArrayList<String>(collection));
		// 配列(String)を区切文字で区切った文字列を取得
		return toSeparatedString(array, separator);
	}
	
	/**
	 * 対象インデックス文字を削除した文字列を取得する。<br>
	 * @param value 対象文字列
	 * @param index 削除対象インデックス
	 * @return 対象インデックス文字を削除した文字列
	 */
	public static String partDelete(String value, int index) {
		// 対象文字列が空である場合
		if (value == null || value.isEmpty() || index < 0) {
			return "";
		}
		// 対象文字列の文字数が制限文字数より小さい場合
		if (value.length() - 1 < index) {
			return value;
		}
		// 削除
		StringBuilder sb1 = new StringBuilder(value);
		sb1.deleteCharAt(index);
		// 対象文字を削除した文字列を取得
		return sb1.toString();
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
	 * 文字列の配列にnullか空白が含まれるかを確認する。<br>
	 * @param strs 文字列の配列
	 * @return 確認結果(true：nullか空白が含まれる、false：nullも空白も含まれない)
	 */
	public static boolean isEmpty(String... strs) {
		// 文字列毎に処理
		for (String str : strs) {
			// nullか空白である場合
			if (str == null || str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * オブジェクトが同じ(equals)であるかを確認する。<br>
	 * ただし、両方かいずれかのオブジェクトがnullである場合は、同じでないと判断する。<br>
	 * <br>
	 * @param obj1 オブジェクト1
	 * @param obj2 オブジェクト2
	 * @return 確認結果(true：オブジェクトが同じである、false：オブジェクトが同じでない)
	 */
	public static boolean isEqual(Object obj1, Object obj2) {
		// 両方かいずれかのオブジェクトがnullである場合
		if (obj1 == null || obj2 == null) {
			// 同じでないと判断
			return false;
		}
		// オブジェクトが同じ(equals)であるかを確認
		return obj1.equals(obj2);
	}
	
	/**
	 * 文字列を取得する。<br>
	 * nullである場合は、空文字を取得する。<br>
	 * @param obj オブジェクト
	 * @return 文字列
	 */
	public static String getString(Object obj) {
		// nullである場合
		if (obj == null) {
			return MospConst.STR_EMPTY;
		}
		// 文字列を取得
		return obj.toString();
	}
	
	/**
	 * コレクションの最初の要素を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の要素
	 */
	public static <T> T getFirstValue(Collection<T> collection) {
		// コレクションが空である場合
		if (collection == null || collection.isEmpty()) {
			// nullを取得
			return null;
		}
		// コレクションの最初の要素を取得
		return collection.iterator().next();
	}
	
	/**
	 * コレクションの最後の要素を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の要素
	 */
	public static <T> T getLastValue(Collection<T> collection) {
		// コレクションが空である場合
		if (collection == null || collection.isEmpty()) {
			// nullを取得
			return null;
		}
		// 要素を準備
		T value = null;
		// 要素毎に処理
		for (Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {
			// 要素を取得
			value = iterator.next();
		}
		// コレクションの最後の要素を取得
		return value;
	}
	
	/**
	 * コレクションの最初の文字列を取得する。<br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の文字列
	 */
	public static String getFirstString(Collection<String> collection) {
		// コレクションが空である場合
		if (collection == null || collection.isEmpty()) {
			// 空文字を取得
			return "";
		}
		// コレクションの最初の要素を取得
		return collection.iterator().next();
	}
	
	/**
	 * リストからインデックスに対応する値を取得する。<br>
	 * リストのサイズがインデックスよりも小さい場合は、空文字を返す。<br>
	 * @param list リスト
	 * @param idx  インデックス
	 * @return インデックスに対応する値
	 */
	public static String getListValue(List<String> list, int idx) {
		// リストのサイズがインデックスよりも小さい場合
		if (list == null || list.size() <= idx) {
			// 空文字を取得
			return "";
		}
		// リストからインデックスに対応する値を取得
		return list.get(idx);
	}
	
	/**
	 * 文字列から数値を取得する。<br>
	 * 取得できなかった場合は、エラー等とせず0を返す。<br>
	 * @param value 文字列
	 * @return 数値
	 */
	public static int getInt(Object value) {
		try {
			// 文字列である場合
			if (value instanceof String) {
				// 文字列から数値を取得
				return Integer.parseInt((String)value);
			}
			// Integerである場合
			if (value instanceof Integer) {
				// 数値を取得
				return ((Integer)value).intValue();
			}
			// それ以外の場合は0を取得
			return 0;
		} catch (Throwable e) {
			// 取得できなかった場合は0を取得
			return 0;
		}
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
	
	/**
	 * 値からbyteの配列を取得する。<br>
	 * @param value 値
	 * @return 値のbyte配列
	 */
	public static byte[] getBytes(String value) {
		// 値が空の場合
		if (value == null || value.isEmpty()) {
			return new byte[0];
		}
		try {
			// 値からbyteの配列を取得
			return value.getBytes(CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return new byte[0];
		}
	}
	
	/**
	 * byteの配列から文字列を取得する。<br>
	 * @param value 値
	 * @return byte配列の文字列
	 */
	public static String newString(byte[] value) {
		// 値が空の場合
		if (value == null || value.length == 0) {
			return "";
		}
		try {
			//
			return new String(value, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
}
