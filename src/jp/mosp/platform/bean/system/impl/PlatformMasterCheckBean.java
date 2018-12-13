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

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 所属・雇用契約・職位・勤務地・名称区分マスタに関連する整合性確認をする。
 */
public class PlatformMasterCheckBean extends PlatformBean implements PlatformMasterCheckBeanInterface {
	
	SectionReferenceBeanInterface				sectionReference;
	PositionReferenceBeanInterface				positionReference;
	EmploymentContractReferenceBeanInterface	employmentContractReference;
	HumanReferenceBeanInterface					humanReference;
	WorkPlaceReferenceBeanInterface				workPlaceReference;
	NamingReferenceBeanInterface				namingReference;
	
	
	/**
	 * {@link PlatformMasterCheckBean#PlatformMasterCheckBean()}を実行する。<br>
	 */
	public PlatformMasterCheckBean() {
		super();
	}
	
	/**
	 * {@link PlatformMasterCheckBean#PlatformMasterCheckBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public PlatformMasterCheckBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		sectionReference = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
		positionReference = (PositionReferenceBeanInterface)createBean(PositionReferenceBeanInterface.class);
		employmentContractReference = (EmploymentContractReferenceBeanInterface)createBean(EmploymentContractReferenceBeanInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		workPlaceReference = (WorkPlaceReferenceBeanInterface)createBean(WorkPlaceReferenceBeanInterface.class);
		namingReference = (NamingReferenceBeanInterface)createBean(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermEmploymentContract(employmentContractCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckPosition(String positionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 期間内に無効がある場合
		if (isTermPosition(positionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckSection(String sectionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 期間内に無効がある場合
		if (isTermSection(sectionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermWorkPlace(workPlaceCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckNaming(String namingType, String namingItemCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermNaming(namingType, namingItemCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermEmploymentContract(employmentContractCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckPosition(String positionCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermPosition(positionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckSection(String sectionCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermSection(sectionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermWorkPlace(workPlaceCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckNaming(String namingType, String namingItemCode, Date startDate, Date endDate)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermNaming(namingType, namingItemCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	/**
	 * 雇用契約を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermEmploymentContract(String employmentContractCode, Date startDate, Date endDate)
			throws MospException {
		// 履歴一覧後に処理
		// 雇用契約：現在の有効日の履歴一覧取得
		List<EmploymentContractDtoInterface> employList = employmentContractReference
			.getContractHistory(employmentContractCode);
		// 履歴一覧後に処理
		for (EmploymentContractDtoInterface employDto : employList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(employDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (employDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				addEmploymentContractInactiveMessage(employmentContractCode, null, employDto.getActivateDate());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 職位を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermPosition(String positionCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 職位：現在の有効日の履歴一覧取得
		List<PositionDtoInterface> positionList = positionReference.getPositionHistory(positionCode);
		// 履歴一覧後に処理
		for (PositionDtoInterface positionDto : positionList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(positionDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (positionDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				addPositionInactiveMessage(positionCode, null, positionDto.getActivateDate());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 所属を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param sectionCode 所属コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermSection(String sectionCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 所属：現在の有効日の履歴一覧取得
		List<SectionDtoInterface> sectionList = sectionReference.getSectionHistory(sectionCode);
		// 履歴一覧後に処理
		for (SectionDtoInterface sectionDto : sectionList) {
			// 期間に対象日が含まれていない場合
			if (DateUtility.isTermContain(sectionDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (sectionDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				addSectionInactiveMessage(sectionCode, null, sectionDto.getActivateDate());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 勤務地を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermWorkPlace(String workPlaceCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 勤務地：現在の有効日の履歴一覧取得
		List<WorkPlaceDtoInterface> workPlaceList = workPlaceReference.getHistory(workPlaceCode);
		// 履歴一覧後に処理
		for (WorkPlaceDtoInterface workPlaceDto : workPlaceList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(workPlaceDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (workPlaceDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				addWorkPlaceInactiveMessage(workPlaceCode, null, workPlaceDto.getActivateDate());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 名称区分を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermNaming(String namingType, String namingItemCode, Date startDate, Date endDate)
			throws MospException {
		// 履歴一覧後に処理
		// 名称区分：現在の有効日の履歴一覧取得
		List<NamingDtoInterface> namingList = namingReference.getNamingItemHistory(namingType, namingItemCode);
		// 履歴一覧後に処理
		for (NamingDtoInterface namingDto : namingList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(namingDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (namingDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				addNamingInactiveMessage(namingType, namingItemCode, null, namingDto.getActivateDate());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 勤務地が無効である場合のメッセージを設定する。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param row          行インデックス
	 * @param inactivateDate 無効になる日付
	 */
	protected void addWorkPlaceInactiveMessage(String workPlaceCode, Integer row, Date inactivateDate) {
		String targetDate = DateUtility.getStringDate(inactivateDate);
		String[] aryRep = { getRowedFieldName(mospParams.getName("WorkPlace"), row), workPlaceCode, targetDate };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK, aryRep);
	}
	
	/**
	 * 職位が無効である場合のメッセージを設定する。<br>
	 * @param positionCode 職位コード
	 * @param row          行インデックス
	 * @param inactivateDate 無効になる日付
	 */
	protected void addPositionInactiveMessage(String positionCode, Integer row, Date inactivateDate) {
		String targetDate = DateUtility.getStringDate(inactivateDate);
		String[] aryRep = { getRowedFieldName(getNamePosition(), row), positionCode, targetDate };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK, aryRep);
	}
	
	/**
	 * 所属が無効である場合のメッセージを設定する。<br>
	 * @param sectionCode 所属コード
	 * @param row          行インデックス
	 * @param inactivateDate 無効になる日付
	 */
	protected void addSectionInactiveMessage(String sectionCode, Integer row, Date inactivateDate) {
		String targetDate = DateUtility.getStringDate(inactivateDate);
		String[] aryRep = { getRowedFieldName(getNameSection(), row), sectionCode, targetDate };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK, aryRep);
	}
	
	/**
	 * 雇用契約が無効である場合のメッセージを設定する。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param row          行インデックス
	 * @param inactivateDate 無効になる日付
	 */
	protected void addEmploymentContractInactiveMessage(String employmentContractCode, Integer row, Date inactivateDate) {
		String targetDate = DateUtility.getStringDate(inactivateDate);
		String[] aryRep = { getRowedFieldName(mospParams.getName("EmploymentContract"), row), employmentContractCode,
			targetDate };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK, aryRep);
	}
	
	/**
	 * 名称区分が無効である場合のメッセージを設定する。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param row          行インデックス
	 * @param inactivateDate 無効になる日付
	 */
	protected void addNamingInactiveMessage(String namingType, String namingItemCode, Integer row, Date inactivateDate) {
		String targetDate = DateUtility.getStringDate(inactivateDate);
		// 名称区分の名称を取得する
		String namingTypeItemName = mospParams.getProperties().getCodeItemName(PlatformConst.CODE_KEY_NAMING_TYPE,
				namingType);
		String[] aryRep = { getRowedFieldName(namingTypeItemName, row), namingItemCode, targetDate };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_INACTIVE_DAY_CHECK, aryRep);
	}
	
}
