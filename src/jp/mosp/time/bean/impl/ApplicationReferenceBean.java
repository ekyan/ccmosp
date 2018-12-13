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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.utils.TimeUtility;

/**
 * 設定適用管理参照クラス。
 */
public class ApplicationReferenceBean extends TimeBean implements ApplicationReferenceBeanInterface {
	
	/**
	 * 設定適用マスタDAOクラス。<br>
	 */
	protected ApplicationDaoInterface	dao;
	
	/**
	 * 人事マスタDAOクラス。<br>
	 */
	protected HumanDaoInterface			humanDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApplicationReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public ApplicationReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// 設定適用マスタDAOクラス準備
		dao = (ApplicationDaoInterface)createDao(ApplicationDaoInterface.class);
		// 人事マスタDAOクラス準備
		humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
	}
	
	@Override
	public ApplicationDtoInterface getApplicationInfo(String applicationCode, Date targetDate) throws MospException {
		return dao.findForKey(applicationCode, targetDate);
	}
	
	@Override
	public List<ApplicationDtoInterface> getApplicationHistory(String applicationCode) throws MospException {
		return dao.findForHistory(applicationCode);
	}
	
	@Override
	public String getApplicationAbbr(String applicationCode, Date targetDate) throws MospException {
		ApplicationDtoInterface dto = dao.findForKey(applicationCode, targetDate);
		if (dto != null) {
			return dto.getApplicationAbbr();
		}
		return applicationCode;
	}
	
	@Override
	public ApplicationDtoInterface findForKey(String applicationCode, Date targetDate) throws MospException {
		ApplicationDtoInterface dto = dao.findForKey(applicationCode, targetDate);
		if (dto != null) {
			return dto;
		}
		return null;
	}
	
	/**
	 * 対象日時点における最新の有効な情報から、以下の方法で順番に
	 * 適用されている情報を探していき、最初に見つかった設定適用情報を返す。<br>
	 *  1.個人ID<br>
	 *  2.職位、所属、雇用契約、勤務地<br>
	 *  3.職位、所属、雇用契約<br>
	 *  4.職位、所属<br>
	 *  5.職位<br>
	 *  6.所属、雇用契約、勤務地<br>
	 *  7.所属、雇用契約<br>
	 *  8.所属<br>
	 *  9.雇用契約、勤務地<br>
	 * 10.雇用契約<br>
	 * 11.勤務地<br>
	 * 12.指定無し<br>
	 */
	@Override
	public ApplicationDtoInterface findForPerson(String personalId, Date targetDate) throws MospException {
		// DTO準備
		ApplicationDtoInterface dto = null;
		// 1.個人ID
		dto = dao.findForPersonalId(targetDate, personalId);
		if (dto != null) {
			return dto;
		}
		// 人事情報取得
		HumanDtoInterface humanDto = humanDao.findForInfo(personalId, targetDate);
		if (humanDto == null) {
			return dto;
		}
		String positionCode = humanDto.getPositionCode();
		String sectionCode = humanDto.getSectionCode();
		String employmentContractCode = humanDto.getEmploymentContractCode();
		String workPlaceCode = humanDto.getWorkPlaceCode();
		String blank = "";
		// 2.職位、所属、雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 3.職位、所属、雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 4.職位、所属
		dto = dao.findForMaster(targetDate, blank, blank, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 5.職位
		dto = dao.findForMaster(targetDate, blank, blank, blank, positionCode);
		if (dto != null) {
			return dto;
		}
		// 6.所属、雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 7.所属、雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 8.所属
		dto = dao.findForMaster(targetDate, blank, blank, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 9.雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 10.雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 11.勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 12.指定無し
		dto = dao.findForMaster(targetDate, blank, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 該当無し
		return dto;
	}
	
	/**
	 * 期間開始日で設定適用情報を取得し、対象日毎に追加していく。<br>
	 * 但し、人事履歴情報に対象日を有効日とする情報が存在した場合、
	 * 対象日で設定適用情報を再取得する。<br>
	 */
	@Override
	public Map<Date, ApplicationDtoInterface> findForTerm(String personalId, Date startDate, Date endDate)
			throws MospException {
		// 設定適用郡準備
		Map<Date, ApplicationDtoInterface> map = new HashMap<Date, ApplicationDtoInterface>();
		// 設定適用情報取得
		ApplicationDtoInterface dto = findForPerson(personalId, startDate);
		// 人事情報取得準備
		if (humanDao == null) {
			humanDao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		}
		// 人事情報履歴を取得
		List<HumanDtoInterface> humanList = humanDao.findForHistory(personalId);
		// 期間開始日日毎に設定
		for (Date targetDate : TimeUtility.getDateList(startDate, endDate)) {
			// 人事情報リスト確認
			for (HumanDtoInterface humanDto : humanList) {
				// 対象日と有効日が異なる場合は何もしない
				if (targetDate.equals(humanDto.getActivateDate()) == false && dto != null) {
					continue;
				}
				// 設定適用情報再取得
				dto = findForPerson(personalId, targetDate);
				break;
			}
			// 設定適用追加
			map.put(targetDate, dto);
		}
		return map;
	}
	
	@Override
	public void chkExistApplication(ApplicationDtoInterface dto, Date targetDate) {
		if (dto == null) {
			String errorMes1 = "";
			if (targetDate == null) {
				errorMes1 = DateUtility.getStringDate(DateUtility.getSystemDate());
			} else {
				errorMes1 = DateUtility.getStringDate(targetDate);
			}
			String errorMes2 = mospParams.getName("Set", "Apply", "Information");
			mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, errorMes1, errorMes2);
		}
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<ApplicationDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException {
		// 設定適用情報取得
		ApplicationDtoInterface applicationDto = findForPerson(personalId, targetDate);
		// 設定適用エンティティ準備
		ApplicationEntity applicationEntity = new ApplicationEntity(applicationDto);
		// 設定適用情報確認
		if (applicationDto == null) {
			// 勤務形態情報が取得できない場合
			return applicationEntity;
		}
		// 各種参照クラス取得
		TimeSettingReferenceBeanInterface timeSettingRefer = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		CutoffReferenceBeanInterface cutoffRefer = (CutoffReferenceBeanInterface)createBean(CutoffReferenceBeanInterface.class);
		// 勤怠設定コード取得
		String workSettingCode = applicationEntity.getWorkSettingCode();
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = timeSettingRefer.getTimeSettingInfo(workSettingCode, targetDate);
		// 設定適用エンティティに勤怠設定情報を設定
		applicationEntity.setTimeSettingDto(timeSettingDto);
		// 締日コード取得
		String cutoffCode = applicationEntity.getCutoffCode();
		// 締日情報取得
		CutoffDtoInterface cutoffDto = cutoffRefer.getCutoffInfo(cutoffCode, targetDate);
		// 設定適用エンティティに締日情報を設定
		applicationEntity.setCutoffDto(cutoffDto);
		// 設定適用エンティティを取得
		return applicationEntity;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティを取得
		return getApplicationEntity(personalId, targetDate);
	}
	
	/**
	 * 対象日時点における最新の有効な情報から、以下の方法で順番に
	 * 適用されている情報を探していき、最初に見つかった設定適用情報を返す。(DBと接続しない)<br>
	 *  1.個人ID<br>
	 *  2.職位、所属、雇用契約、勤務地<br>
	 *  3.職位、所属、雇用契約<br>
	 *  4.職位、所属<br>
	 *  5.職位<br>
	 *  6.所属、雇用契約、勤務地<br>
	 *  7.所属、雇用契約<br>
	 *  8.所属<br>
	 *  9.雇用契約、勤務地<br>
	 * 10.雇用契約<br>
	 * 11.勤務地<br>
	 * 12.指定無し<br>
	 */
	@Override
	public ApplicationDtoInterface findForPerson(HumanDtoInterface humanDto,
			Map<Integer, Set<ApplicationDtoInterface>> applicationMap) {
		// DTO準備
		ApplicationDtoInterface dto = null;
		Set<ApplicationDtoInterface> personalSet = applicationMap.get(1);
		Set<ApplicationDtoInterface> applicationSet = applicationMap.get(2);
		
		// 変数初期化
		String positionCode = "";
		String sectionCode = "";
		String employmentContractCode = "";
		String workPlaceCode = "";
		String personalId = "";
		String blank = "";
		
		// 人事情報の設定
		positionCode = humanDto.getPositionCode();
		sectionCode = humanDto.getSectionCode();
		employmentContractCode = humanDto.getEmploymentContractCode();
		workPlaceCode = humanDto.getWorkPlaceCode();
		personalId = humanDto.getPersonalId();
		
		// 1.個人
		dto = getPersonalApplicationData(personalSet, personalId, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		if (dto != null) {
			return dto;
		}
		// 2.職位、所属、雇用契約、勤務地
		dto = getPersonalApplicationData(applicationSet, personalId, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		
		// 3.職位、所属、雇用契約
		dto = getPersonalApplicationData(applicationSet, personalId, blank, employmentContractCode, sectionCode,
				positionCode);
		if (dto != null) {
			return dto;
		}
		// 4.職位、所属
		dto = getPersonalApplicationData(applicationSet, personalId, blank, blank, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 5.職位
		dto = getPersonalApplicationData(applicationSet, personalId, blank, blank, blank, positionCode);
		if (dto != null) {
			return dto;
		}
		// 6.所属、雇用契約、勤務地
		dto = getPersonalApplicationData(applicationSet, personalId, workPlaceCode, employmentContractCode,
				sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 7.所属、雇用契約
		dto = getPersonalApplicationData(applicationSet, personalId, blank, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 8.所属
		dto = getPersonalApplicationData(applicationSet, personalId, blank, blank, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 9.雇用契約、勤務地
		dto = getPersonalApplicationData(applicationSet, personalId, workPlaceCode, employmentContractCode, blank,
				blank);
		if (dto != null) {
			return dto;
		}
		// 10.雇用契約
		dto = getPersonalApplicationData(applicationSet, personalId, blank, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 11.勤務地
		dto = getPersonalApplicationData(applicationSet, personalId, workPlaceCode, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 12.指定無し
		dto = getPersonalApplicationData(applicationSet, personalId, blank, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 該当無し
		return dto;
	}
	
	/**
	 * 人事情報に紐づいた設定適用情報取得
	 * @param set 設定適用情報セット
	 * @param personalId 個人ID
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @return 設定適用情報DTO
	 */
	private ApplicationDtoInterface getPersonalApplicationData(Set<ApplicationDtoInterface> set, String personalId,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode) {
		for (ApplicationDtoInterface applicationDto : set) {
			//個人に紐づく場合、比較対象外
			if (!applicationDto.getPersonalId().equals("")) {
				// 個人ID分割
				String[] aryPersonalIds = MospUtility.split(applicationDto.getPersonalId(),
						MospConst.APP_PROPERTY_SEPARATOR);
				for (String applicationPersonalId : aryPersonalIds) {
					if (applicationPersonalId.equals(personalId)) {
						return applicationDto;
						
					}
				}
				continue;
			}
			// 人事情報と合致する設定適用情報を返却
			if (applicationDto.getWorkPlaceCode().equals(workPlaceCode)
					&& applicationDto.getEmploymentContractCode().equals(employmentContractCode)
					&& applicationDto.getSectionCode().equals(sectionCode)
					&& applicationDto.getPositionCode().equals(positionCode)) {
				return applicationDto;
				
			}
		}
		
		return null;
	}
	
	@Override
	public Map<Integer, Set<ApplicationDtoInterface>> getApplicationInfoForTargetDate(Date targetDate)
			throws MospException {
		Map<Integer, Set<ApplicationDtoInterface>> map = new HashMap<Integer, Set<ApplicationDtoInterface>>();
		List<ApplicationDtoInterface> list = new ArrayList<ApplicationDtoInterface>();
		
		// 対象日時点で最新の情報を取得
		list = dao.findForActivateDate(targetDate);
		
		// 個人
		map.put(1, splitApplicationDto(list, true));
		// 個人以外
		map.put(2, splitApplicationDto(list, false));
		
		return map;
		
	}
	
	/**
	 * 設定適用情報を分割しセットに設定する
	 * @param list 設定適用情報リスト
	 * @param isPersonalSet 個人IDが設定されているものみ
	 * @return 設定適用区分に紐づく設定適用情報セット
	 */
	private Set<ApplicationDtoInterface> splitApplicationDto(List<ApplicationDtoInterface> list, boolean isPersonalSet) {
		Set<ApplicationDtoInterface> set = new HashSet<ApplicationDtoInterface>();
		// 設定適用情報毎に処理
		for (ApplicationDtoInterface applicationDto : list) {
			// 個人の場合
			if (isPersonalSet) {
				// 個人区分の場合
				if (applicationDto.getApplicationType() == Integer.valueOf(PlatformConst.APPLICATION_TYPE_PERSON)) {
					// 追加
					set.add(applicationDto);
				}
			} else {
				// 個人でない場合
				if (applicationDto.getApplicationType() == Integer.valueOf(PlatformConst.APPLICATION_TYPE_MASTER)) {
					// 追加
					set.add(applicationDto);
				}
			}
		}
		return set;
	}
	
}
