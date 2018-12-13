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
package jp.mosp.time.bean.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayManagementListDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayManagementListDto;

/**
 * 休暇確認検索クラス。
 */
public class HolidayManagementSearchBean extends PlatformBean implements HolidayManagementSearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	private HumanSearchBeanInterface				humanSearch;
	
	/**
	 * 休暇データDAO。
	 */
	private HolidayDataDaoInterface					holidayDataDao;
	
	/**
	 * 休暇申請参照。
	 */
	private HolidayRequestReferenceBeanInterface	holidayRequest;
	
	/**
	 * 有効日。
	 */
	private Date									activateDate;
	
	/**
	 * 社員コード。
	 */
	private String									employeeCode;
	
	/**
	 * 社員名。
	 */
	private String									employeeName;
	
	/**
	 * 勤務地コード。
	 */
	private String									workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String									employmentCode;
	
	/**
	 * 所属コード。
	 */
	private String									sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String									positionCode;
	
	
	/**
	 * コンストラクタ。
	 */
	public HolidayManagementSearchBean() {
		// 処理無し
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション。
	 */
	public HolidayManagementSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 人事情報検索クラス取得
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		// 休暇データDAO取得
		holidayDataDao = (HolidayDataDaoInterface)createDao(HolidayDataDaoInterface.class);
		holidayRequest = (HolidayRequestReferenceBeanInterface)createBean(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public List<HolidayManagementListDtoInterface> getSearchList(int holidayType) throws MospException {
		// 人事情報検索クラスに検索条件を設定
		humanSearch.setTargetDate(activateDate);
		humanSearch.setEmployeeCode(employeeCode);
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		humanSearch.setEmployeeName(employeeName);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentCode);
		humanSearch.setPositionCode(positionCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 人事情報検索
		List<HumanDtoInterface> list = humanSearch.search();
		// 休暇確認リスト準備
		List<HolidayManagementListDtoInterface> holidayManagementList = new ArrayList<HolidayManagementListDtoInterface>();
		for (HumanDtoInterface dto : list) {
			String personalId = dto.getPersonalId();
			List<HolidayDataDtoInterface> holidayDataList = holidayDataDao.findForInfoList(personalId, activateDate,
					String.valueOf(MospConst.DELETE_FLAG_OFF), holidayType);
			for (HolidayDataDtoInterface holidayDataDto : holidayDataList) {
				// 初期化
				HolidayManagementListDtoInterface holidayManagementListDto = new HolidayManagementListDto();
				holidayManagementListDto.setEmployeeCode(dto.getEmployeeCode());
				holidayManagementListDto.setActivateDate(holidayDataDto.getActivateDate());
				holidayManagementListDto.setLastName(dto.getLastName());
				holidayManagementListDto.setFirstName(dto.getFirstName());
				holidayManagementListDto.setSectionCode(dto.getSectionCode());
				holidayManagementListDto.setHolidayCode(holidayDataDto.getHolidayCode());
				Map<String, Object> map = holidayRequest.getRequestDayHour(personalId,
						holidayDataDto.getActivateDate(), holidayType, holidayDataDto.getHolidayCode(),
						holidayDataDto.getActivateDate(), activateDate);
				holidayManagementListDto.setHolidayRemainder(holidayDataDto.getGivingDay()
						- holidayDataDto.getCancelDay() - ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue());
				holidayManagementListDto.setHolidayLimit(holidayDataDto.getHolidayLimitDate());
				holidayManagementList.add(holidayManagementListDto);
			}
		}
		return holidayManagementList;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
}
