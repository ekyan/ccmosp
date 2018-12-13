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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;

/**
 * 休日出勤一覧DTO
 */
public class WorkOnHolidayRequestListDto extends BaseDto implements WorkOnHolidayRequestListDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1542854401511065150L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdWorkOnHolidayRequestId;
	/**
	 * 出勤日。
	 */
	private Date				requestDate;
	/**
	 * 出勤予定時刻。
	 */
	private Date				startTime;
	/**
	 * 退勤予定時刻。
	 */
	private Date				endTime;
	/**
	 * 理由。
	 */
	private String				requestReason;
	/**
	 * 振替日1。
	 */
	private Date				substituteDate1;
	/**
	 * 振替範囲1。
	 */
	private int					substituteRange1;
	/**
	 * 振替日2。
	 */
	private Date				substituteDate2;
	/**
	 * 振替範囲2。
	 */
	private int					substituteRange2;
	/**
	 * 承認段階。
	 */
	private int					stage;
	/**
	 * 承認状況。
	 */
	private String				state;
	/**
	 * 承認者。
	 */
	private String				approverName;
	
	/**
	 * ワークフロー。
	 */
	private long				workflow;
	
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public String getRequestReason() {
		return requestReason;
	}
	
	@Override
	public Date getSubstituteDate1() {
		return getDateClone(substituteDate1);
	}
	
	@Override
	public Date getSubstituteDate2() {
		return getDateClone(substituteDate2);
	}
	
	@Override
	public int getSubstituteRange1() {
		return substituteRange1;
	}
	
	@Override
	public int getSubstituteRange2() {
		return substituteRange2;
	}
	
	@Override
	public long getTmdWorkOnHolidayRequestId() {
		return tmdWorkOnHolidayRequestId;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setSubstituteDate1(Date substituteDate1) {
		this.substituteDate1 = getDateClone(substituteDate1);
	}
	
	@Override
	public void setSubstituteDate2(Date substituteDate2) {
		this.substituteDate2 = getDateClone(substituteDate2);
	}
	
	@Override
	public void setSubstituteRange1(int substituteRange1) {
		this.substituteRange1 = substituteRange1;
	}
	
	@Override
	public void setSubstituteRange2(int substituteRange2) {
		this.substituteRange2 = substituteRange2;
	}
	
	@Override
	public void setTmdWorkOnHolidayRequestId(long tmdWorkOnHolidayRequestId) {
		this.tmdWorkOnHolidayRequestId = tmdWorkOnHolidayRequestId;
	}
	
	@Override
	public String getApproverName() {
		return approverName;
	}
	
	@Override
	public int getStage() {
		return stage;
	}
	
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@Override
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public Date getEndTime() {
		return getDateClone(endTime);
	}
	
	@Override
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
}
