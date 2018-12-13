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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 振替休日データ参照クラス。
 */
public class SubstituteReferenceBean extends TimeBean implements SubstituteReferenceBeanInterface {
	
	/**
	 * 振替休日データDAO。
	 */
	protected SubstituteDaoInterface			dao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface	workflow;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubstituteReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public SubstituteReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		workflow = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public SubstituteDtoInterface getSubstituteDto(String personalId, Date workDate) throws MospException {
		// 振出申請リスト取得
		List<SubstituteDtoInterface> list = dao.findForWorkDate(personalId, workDate);
		// 振出申請リスト毎に処理
		for (SubstituteDtoInterface dto : list) {
			// 取下の場合
			if (workflow.isWithDrawn(dto.getWorkflow())) {
				continue;
			}
			return dto;
		}
		return null;
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(String personalId, Date substituteDate) throws MospException {
		return dao.findForList(personalId, substituteDate);
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
	}
	
	@Override
	public Date getSubstituteDate(long workflow) throws MospException {
		// 休日出勤情報リストを取得
		List<SubstituteDtoInterface> list = getSubstituteList(workflow);
		// 休日出勤情報リスト確認
		if (list.isEmpty()) {
			return null;
		}
		// 休日出勤の日を取得
		return list.get(0).getSubstituteDate();
	}
}
