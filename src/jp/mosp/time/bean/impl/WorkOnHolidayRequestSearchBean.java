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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.impl.WorkOnHolidayRequestListDto;

/**
 * 休日出勤申請検索クラス。
 */
public class WorkOnHolidayRequestSearchBean extends PlatformBean implements WorkOnHolidayRequestSearchBeanInterface {
	
	/**
	 * 休日出勤申請DAO。
	 */
	private WorkOnHolidayRequestDaoInterface	workOnHolidayRequestDao;
	
	/**
	 * 振替休日申請DAO。
	 */
	private SubstituteDaoInterface				substituteDao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	private WorkflowReferenceBeanInterface		workflowReference;
	
	private ApprovalInfoReferenceBeanInterface	approvalInfoReference;
	
	/**
	 * 個人ID。
	 */
	String										personalId;
	
	/**
	 * 振替休日
	 */
	private String								substitute;
	
	/**
	 * 振替休日範囲。
	 */
	private String								substituteRange;
	
	/**
	 * 状態。
	 */
	private String								workflowStatus;
	
	/**
	 * 表示開始日。
	 */
	private Date								requestStartDate;
	
	/**
	 * 表示終了日。
	 */
	private Date								requestEndDate;
	
	
	/**
	 * コンストラクタ。
	 */
	public WorkOnHolidayRequestSearchBean() {
		// 処理無し
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション。
	 */
	public WorkOnHolidayRequestSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 休日出勤申請
		workOnHolidayRequestDao = (WorkOnHolidayRequestDaoInterface)createDao(WorkOnHolidayRequestDaoInterface.class);
		substituteDao = (SubstituteDaoInterface)createDao(SubstituteDaoInterface.class);
		approvalInfoReference = (ApprovalInfoReferenceBeanInterface)createBean(ApprovalInfoReferenceBeanInterface.class);
		workflowReference = (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
	}
	
	@Override
	public List<WorkOnHolidayRequestListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = workOnHolidayRequestDao.getParamsMap();
		param.put("personalId", personalId);
		param.put("substitute", substitute);
		param.put("substituteRange", substituteRange);
		param.put("workflowStatus", workflowStatus);
		param.put("requestStartDate", requestStartDate);
		param.put("requestEndDate", requestEndDate);
		List<WorkOnHolidayRequestDtoInterface> requestList = workOnHolidayRequestDao.findForSearch(param);
		List<WorkOnHolidayRequestListDtoInterface> list = new ArrayList<WorkOnHolidayRequestListDtoInterface>();
		// addLsitに設定するためのフラグ
		boolean flag = false;
		// 検索ListのDTOの作成
		for (WorkOnHolidayRequestDtoInterface requestDto : requestList) {
			WorkOnHolidayRequestListDtoInterface dto = new WorkOnHolidayRequestListDto();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			List<SubstituteDtoInterface> substituteList = substituteDao.findForWorkflow(requestDto.getWorkflow());
			if (!substituteRange.isEmpty()) {
				boolean rangeFlag = false;
				for (SubstituteDtoInterface substituteDto : substituteList) {
					if (substituteRange.equals(String.valueOf(substituteDto.getSubstituteRange()))) {
						rangeFlag = true;
						break;
					}
				}
				if (!rangeFlag) {
					continue;
				}
			}
			// レコード識別ID
			dto.setTmdWorkOnHolidayRequestId(requestDto.getTmdWorkOnHolidayRequestId());
			dto.setRequestDate(requestDto.getRequestDate());
			dto.setStartTime(requestDto.getStartTime());
			dto.setEndTime(requestDto.getEndTime());
			dto.setRequestReason(requestDto.getRequestReason());
			dto.setWorkflow(requestDto.getWorkflow());
			int i = 0;
			for (SubstituteDtoInterface substituteDto : substituteList) {
				Date substituteDate = substituteDto.getSubstituteDate();
				int substituteRange = substituteDto.getSubstituteRange();
				if (i == 0) {
					dto.setSubstituteDate1(substituteDate);
					dto.setSubstituteRange1(substituteRange);
				} else if (i == 1) {
					dto.setSubstituteDate2(substituteDate);
					dto.setSubstituteRange2(substituteRange);
				}
				i++;
			}
			// 承認情報
			approvalInfoReference.setWorkflowInfo(dto, workflowDto);
			// フラグの初期化
			flag = false;
			// 検索状態の処理によって取得する内容を変更
			if (TimeConst.CODE_SEARCH_STATUS_DRAFT.equals(workflowStatus)) {
				// 下書き
				if (PlatformConst.CODE_STATUS_DRAFT.equals(dto.getState())) {
					// ステータスが下書の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_APPLY.equals(workflowStatus)) {
				// 未承認
				if (PlatformConst.CODE_STATUS_APPLY.equals(dto.getState())
						|| PlatformConst.CODE_STATUS_APPROVED.equals(dto.getState())) {
					// ステータスが未承認、1～9次済の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_COMPLETE.equals(workflowStatus)) {
				// 承認済
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(dto.getState())) {
					// ステータスが承認済の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_REVERT.equals(workflowStatus)) {
				// 差戻
				if (PlatformConst.CODE_STATUS_REVERT.equals(dto.getState())
						|| PlatformConst.CODE_STATUS_CANCEL.equals(dto.getState())) {
					// ステータスが2～9次戻、差戻済、承認解除の場合、フラグを立てる
					flag = true;
				}
			} else {
				flag = true;
			}
			if (flag) {
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public void setSubstitute(String substitute) {
		this.substitute = substitute;
	}
	
	@Override
	public void setSubstituteRange(String substituteRange) {
		this.substituteRange = substituteRange;
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Override
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = getDateClone(requestStartDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = getDateClone(requestEndDate);
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
