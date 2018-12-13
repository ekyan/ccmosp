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
package jp.mosp.platform.bean.system.impl;

import org.apache.commons.codec.binary.Base64;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.system.CipherBeanInterface;

/**
 * 暗号化処理クラス。
 */
public class CipherBean extends BaseBean implements CipherBeanInterface {
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	/**
	 * Base64でエンコードしているだけで、暗号化はしていない。
	 */
	@Override
	public String encrypt(String source) throws MospException {
		// 暗号化対象文字列をバイト配列に変換
		byte[] sourceBytes = MospUtility.getBytes(source);
		// Base64でエンコーディングした文字列を取得
		return encode(sourceBytes);
	}
	
	/**
	 * Base64でデコードしているだけで、復号化はしていない。
	 */
	@Override
	public String decrypt(String source) throws MospException {
		// Base64でデコードした文字列を取得
		byte[] sourceBytes = decode(source);
		// 復号化した文字列を取得
		return MospUtility.newString(sourceBytes);
	}
	
	/**
	 * Base64でエンコードされた文字列を取得する。
	 * @param source 対象バイト配列
	 * @return Base64でエンコードされた文字列
	 * @throws MospException エンコードに失敗した場合
	 */
	protected String encode(byte[] source) throws MospException {
		try {
			// Base64でエンコード
			return Base64.encodeBase64String(source);
		} catch (Throwable t) {
			// エンコードに失敗した場合
			throw new MospException(t);
		}
	}
	
	/**
	 * Base64でデコードされたバイト配列を取得する。
	 * @param source 対象文字列
	 * @return Base64でデコードされたバイト配列
	 * @throws MospException デコードに失敗した場合
	 */
	protected byte[] decode(String source) throws MospException {
		try {
			// Base64でデコード
			return Base64.decodeBase64(source);
		} catch (Throwable t) {
			// デコードに失敗した場合
			throw new MospException(t);
		}
	}
	
}
