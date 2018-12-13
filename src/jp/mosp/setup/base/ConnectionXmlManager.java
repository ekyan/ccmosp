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
package jp.mosp.setup.base;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import jp.mosp.framework.base.DBConnBean;
import jp.mosp.framework.base.MospException;

/**
 * ユーザデータベース接続情報管理クラス。
 */
public final class ConnectionXmlManager {
	
	/**
	 * 出力
	 * @param xml 出力ファイル
	 * @param url URL
	 * @param user DBユーザ名
	 * @param password DBパスワード
	 * @throws MospException JAXBに関する例外
	 */
	public static void export(File xml, String url, String user, String password) throws MospException {
		try {
			JAXBContext context = JAXBContext.newInstance(MosP.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE MosP>");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(create(url, user, password), xml);
		} catch (Exception e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 読取
	 * @param xml XMLファイル
	 * @return DB接続情報
	 * @throws MospException JAXBに関する例外
	 */
	public static Map<String, String> load(File xml) throws MospException {
		MosP mosp = null;
		try {
			mosp = JAXB.unmarshal(xml, MosP.class);
		} catch (Exception e) {
			throw new MospException(e);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		List<Application> list = mosp.getApplications();
		for (Application application : list) {
			map.put(application.getKey(), application.getValue());
		}
		return map;
	}
	
	private static MosP create(String url, String user, String password) {
		MosP mosp = new MosP();
		mosp.add(DBConnBean.APP_DB_URL, url);
		mosp.add(DBConnBean.APP_DB_USER, user);
		mosp.add(DBConnBean.APP_DB_PASS, password);
		return mosp;
	}
	
	
	@XmlRootElement(name = "MosP")
	static class MosP implements Serializable {
		
		private static final long	serialVersionUID	= 5206655866494720141L;
		
		private List<Application>	applications;
		
		
		/**
		 * コンストラクタ。
		 */
		public MosP() {
			applications = new ArrayList<Application>();
		}
		
		/**
		 * @return applications
		 */
		@XmlElement(name = "Application")
		public List<Application> getApplications() {
			return applications;
		}
		
		/**
		 * @param applications セットする applications
		 */
		public void setApplications(List<Application> applications) {
			this.applications = applications;
		}
		
		public void add(String key, String value) {
			applications.add(new Application(key, value));
		}
		
	}
	
	static class Application implements Serializable {
		
		private static final long	serialVersionUID	= 7771392188778377897L;
		
		private String				key;
		
		private String				value;
		
		
		/**
		 * コンストラクタ。
		 */
		public Application() {
		}
		
		/**
		 * コンストラクタ。
		 * @param key Applicationキー
		 * @param value 値
		 */
		public Application(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * @return key
		 */
		@XmlAttribute
		public String getKey() {
			return key;
		}
		
		/**
		 * @param key セットする key
		 */
		public void setKey(String key) {
			this.key = key;
		}
		
		/**
		 * @return value
		 */
		@XmlValue
		public String getValue() {
			return value;
		}
		
		/**
		 * @param value セットする value
		 */
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
}
