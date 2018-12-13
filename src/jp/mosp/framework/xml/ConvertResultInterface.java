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
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.AddonProperty;
import jp.mosp.framework.property.ApplicationProperty;
import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.CodeProperty;
import jp.mosp.framework.property.CommandProperty;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MessageProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.property.NamingProperty;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.framework.property.ViewConfigProperty;

/**
 * MosP設定情報変換結果インターフェース。<br>
 */
interface ConvertResultInterface {
	
	public <T extends BaseProperty> Map<String, T> get(String key);
	
	public Map<String, ConventionProperty> getConvention();
	
	public Map<String, RoleProperty> getRole();
	
	public Map<String, MainMenuProperty> getMainMenu();
	
	public Map<String, AddonProperty> getAddon();
	
	public Map<String, CodeProperty> getCode();
	
	public Map<String, NamingProperty> getNaming();
	
	public Map<String, MessageProperty> getMessage();
	
	public Map<String, CommandProperty> getController();
	
	public Map<String, ApplicationProperty> getApplication();
	
	public Map<String, ViewConfigProperty> getViewConfig();
	
	public Map<String, ModelProperty> getModel();
	
}
