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

import java.util.Date;

/**
 * カプセル化の際に用いる機能を提供する。<br><br>
 */
public final class CapsuleUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private CapsuleUtility() {
		// 処理無し
	}
	
	/**
	 * 日付オブジェクトの複製を取得する。
	 * @param date 対象日付オブジェクト
	 * @return 複製日付オブジェクト
	 */
	public static Date getDateClone(Date date) {
		if (date == null) {
			return null;
		}
		return (Date)date.clone();
	}
	
	/**
	 * int配列の複製を取得する。
	 * @param array 対象int配列
	 * @return 複製int配列
	 */
	public static int[] getIntArrayClone(int[] array) {
		if (array == null) {
			return new int[0];
		}
		return array.clone();
	}
	
	/**
	 * long配列の複製を取得する。
	 * @param array 対象long配列
	 * @return 複製long配列
	 */
	public static long[] getLongArrayClone(long[] array) {
		if (array == null) {
			return new long[0];
		}
		return array.clone();
	}
	
	/**
	 * byte配列の複製を取得する。
	 * @param array 対象byte配列
	 * @return 複製byte配列
	 */
	public static byte[] getByteArrayClone(byte[] array) {
		if (array == null) {
			return new byte[0];
		}
		return array.clone();
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	public static String[] getStringArrayClone(String[] array) {
		if (array == null) {
			return new String[0];
		}
		return array.clone();
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	public static String[][] getStringArrayClone(String[][] array) {
		if (array == null) {
			return new String[0][0];
		}
		return array.clone();
	}
	
}
