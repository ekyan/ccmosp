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

import java.io.StringWriter;
import java.util.Properties;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformMailConst;
import jp.mosp.platform.dto.mail.impl.MailTemplateDto;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.AvalonLogSystem;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

/**
 * メールテンプレートユーティリティ
 * <p>
 * {@link MailTemplateUtility}を利用したい場合、ビルドパスに、
 * velocity-1.6.4.jar
 * commons-collections-3.2.1.jar
 * commons-lang-2.5.jar
 * jakarta-oro-2.0.8.jar
 * を追加してください。
 * </p>
 */
public class MailTemplateUtility {
	
	private Template		template;
	private VelocityContext	context;
	private VelocityEngine	engine;
	private Properties		vp;
	
	private String			text;
	
	private boolean			loadercache	= true;
	
	
	/**
	 * コンストラクタ
	 */
	public MailTemplateUtility() {
		context = new VelocityContext();
		engine = new VelocityEngine();
		vp = new Properties();
		text = "";
	}
	
	/**
	 * VelocityEngineの初期化
	 * @param mospParams MosP処理情報
	 * @param templatePath テンプレートパス
	 * @param templateFileName テンプレートファイル名
	 */
	public void init(MospParams mospParams, String templatePath, String templateFileName) {
		vp = getProperties(mospParams.getApplicationProperty(MospConst.APP_DOCBASE) + templatePath);
		// エンジンの初期化
		engine.init(vp);
		// テンプレートの取得
		template = engine.getTemplate(templateFileName, PlatformMailConst.ENCODING);
	}
	
	/**
	 * コンテキスト取得
	 * @return context
	 */
	public VelocityContext getContext() {
		return context;
	}
	
	/**
	 * メール項目情報設定
	 * @param dto メールテンプレートDTO
	 */
	public void setMailItem(MailTemplateDto dto) {
		// 項目を設定する
		context.put(PlatformMailConst.KEY_DTO, dto);
	}
	
	/**
	 * メール項目情報設定
	 * @param key   テンプレート項目キー
	 * @param value 表示内容
	 */
	public void setMailItem(String key, String value) {
		// 項目を設定する
		context.put(key, value);
	}
	
	/**
	 * 内容取得
	 * @return	自動送信内容
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public String getText() throws MospException {
		// マージ
		merge();
		return text;
	}
	
	/**
	 * マージ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void merge() throws MospException {
		StringWriter sw = new StringWriter();
		// マージ
		template.merge(context, sw);
		text = sw.toString();
		sw.flush();
		try {
			sw.close();
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * contextを初期化する。
	 */
	public void freshConText() {
		context = new VelocityContext();
	}
	
	/**
	 * VelocityEngineのプロパティ設定
	 * @param templatePath テンプレートディレクトリのファイルパス
	 * @return properties
	 */
	private Properties getProperties(String templatePath) {
		Properties props = new Properties();
		props.setProperty(PlatformMailConst.RESOURCE_LOADER, PlatformMailConst.FILE);
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_DESCRIPTION, "Velocity File Resource Loader");
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_CLASS, FileResourceLoader.class.getCanonicalName());
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_PATH, templatePath);
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_CACHE, Boolean.toString(loadercache));
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_INTERVAL, "0");
		props.setProperty(PlatformMailConst.INPUT_ENCODING, PlatformMailConst.ENCODING);
		props.setProperty(PlatformMailConst.OUTPUT_ENCODING, PlatformMailConst.ENCODING);
		props.setProperty(PlatformMailConst.RUNTIME_LOG_LOGSYSTEM_CLASS, AvalonLogSystem.class.getCanonicalName());
		return props;
	}
	
}
