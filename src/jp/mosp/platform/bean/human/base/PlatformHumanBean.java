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
package jp.mosp.platform.bean.human.base;

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dao.system.WorkPlaceDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.utils.PlatformNamingUtility;

/**
 * MosPプラットフォーム人事管理におけるBeanの基本機能を提供する。<br>
 */
public abstract class PlatformHumanBean extends PlatformFileBean {
	
	/**
	 * 所属・雇用契約・職位・勤務地・名称区分マスタに関連する整合性確認インターフェース。
	 */
	protected PlatformMasterCheckBeanInterface masterCheck;
	
	
	/**
	 * {@link PlatformHumanBean}を生成する。<br>
	 */
	public PlatformHumanBean() {
		// 処理無し
	}
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	protected PlatformHumanBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		masterCheck = (PlatformMasterCheckBeanInterface)createBean(PlatformMasterCheckBeanInterface.class);
	}
	
	/**
	 * 社員名を取得する。<br>
	 * @param dto 人事マスタDTO。
	 * @return 社員名
	 */
	protected String getHumanName(HumanDtoInterface dto) {
		if (dto == null) {
			return "";
		}
		return MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
	}
	
	/**
	 * 入社日を取得する。<br>
	 * 入社情報が存在しない場合はnullを返す。<br>
	 * @param personalId 個人ID
	 * @return 入社日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getEntranceDate(String personalId) throws MospException {
		// 人事入社情報DAO準備
		EntranceDaoInterface entranceDao = (EntranceDaoInterface)createDao(EntranceDaoInterface.class);
		EntranceDtoInterface dto = entranceDao.findForInfo(personalId);
		if (dto != null) {
			return dto.getEntranceDate();
		}
		return null;
	}
	
	/**
	 * 退職日を取得する。<br>
	 * 退職情報が存在しない場合はnullを返す。<br>
	 * @param personalId 個人ID
	 * @return 退職日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getRetirementDate(String personalId) throws MospException {
		// 人事退職情報DAO準備
		RetirementDaoInterface retirementDao = (RetirementDaoInterface)createDao(RetirementDaoInterface.class);
		RetirementDtoInterface dto = retirementDao.findForInfo(personalId);
		if (dto != null) {
			return dto.getRetirementDate();
		}
		return null;
	}
	
	/**
	 * 勤務地存在確認を行う。<br>
	* @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkPlace(String workPlaceCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 勤務地確認
		if (workPlaceCode.isEmpty()) {
			return;
		}
		// 勤務地マスタDAO取得
		WorkPlaceDaoInterface workPlaceDao = (WorkPlaceDaoInterface)createDao(WorkPlaceDaoInterface.class);
		// 勤務地情報取得
		WorkPlaceDtoInterface workDto = workPlaceDao.findForInfo(workPlaceCode, startDate);
		// 勤務地情報確認
		if (workDto == null) {
			// 勤務地が存在しない場合のメッセージを追加
			addWorkPlaceNotExistMessage(workPlaceCode, row);
			return;
		}
		// 登録する期間無効にならないか確認
		masterCheck.isCheckWorkPlace(workPlaceCode, startDate, endDate, row);
	}
	
	/**
	 * 雇用契約存在確認を行う。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEmploymentContract(String employmentContractCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 雇用契約確認
		if (employmentContractCode.isEmpty()) {
			return;
		}
		// 雇用契約マスタDAO取得
		EmploymentContractDaoInterface employmentContractDao;
		employmentContractDao = (EmploymentContractDaoInterface)createDao(EmploymentContractDaoInterface.class);
		// 雇用契約情報取得
		EmploymentContractDtoInterface employmentDto = employmentContractDao.findForInfo(employmentContractCode,
				startDate);
		// 雇用契約情報確認
		if (employmentDto == null) {
			// 雇用契約が存在しない場合のメッセージを追加
			addEmploymentContractNotExistMessage(employmentContractCode, row);
			return;
		}
		// 登録する期間無効にならないか確認
		masterCheck.isCheckEmploymentContract(employmentContractCode, startDate, endDate, row);
	}
	
	/**
	 * 所属存在確認を行う。<br>
	 * @param sectionCode 所属コード
	 * @param startDate  期間開始日
	 * @param endDate 期間終了日
	 * @param row         行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSection(String sectionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 所属確認
		if (sectionCode.isEmpty()) {
			return;
		}
		// 所属マスタDAO取得
		SectionDaoInterface sectionDao = (SectionDaoInterface)createDao(SectionDaoInterface.class);
		// 所属情報取得
		SectionDtoInterface dto = sectionDao.findForInfo(sectionCode, startDate);
		// 所属情報確認
		if (dto == null) {
			// 所属が存在しない場合のメッセージを追加
			addSectionNotExistMessage(sectionCode, row);
			return;
		}
		// 登録する期間内に無効がないか確認
		if (masterCheck.isCheckSection(sectionCode, startDate, endDate, row) == false) {
			return;
		}
	}
	
	/**
	 * 職位存在確認を行う。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPosition(String positionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 職位確認
		if (positionCode.isEmpty()) {
			return;
		}
		// 職位マスタDAO取得
		PositionDaoInterface positionDao = (PositionDaoInterface)createDao(PositionDaoInterface.class);
		// 職位情報取得
		PositionDtoInterface dto = positionDao.findForInfo(positionCode, startDate);
		// 職位情報確認
		if (dto == null) {
			// 職位が存在しない場合のメッセージを追加
			addPositionNotExistMessage(positionCode, row);
			return;
		}
		// 登録する期間内に無効がないか確認
		if (masterCheck.isCheckPosition(positionCode, startDate, endDate, row)) {
			return;
		}
	}
	
	/**
	 * 名称区分存在確認を行う。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkNaming(String namingType, String namingItemCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 名称項目コード確認
		if (namingItemCode.isEmpty()) {
			return;
		}
		// 名称区分マスタDAO取得
		NamingDaoInterface namingDao = (NamingDaoInterface)createDao(NamingDaoInterface.class);
		// 名称区分情報取得
		NamingDtoInterface dto = namingDao.findForInfo(namingType, namingItemCode, startDate);
		// 名称区分情報確認
		if (dto == null) {
			// 名称区分が存在しない場合のメッセージを追加
			addNamingItemNotExistMessage(namingType, namingItemCode, row);
			return;
		}
		// 登録する期間内に無効がないか確認
		if (masterCheck.isCheckNaming(namingType, namingItemCode, startDate, endDate, row)) {
			return;
		}
	}
	
	/**
	 * 勤務地が存在しない場合のメッセージを設定する。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param row           行インデックス
	 */
	protected void addWorkPlaceNotExistMessage(String workPlaceCode, Integer row) {
		String[] rep = { getRowedFieldName(mospParams.getName("WorkPlace"), row), workPlaceCode };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, rep);
	}
	
	/**
	 * 雇用契約が存在しない場合のメッセージを設定する。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param row                    行インデックス
	 */
	protected void addEmploymentContractNotExistMessage(String employmentContractCode, Integer row) {
		String[] rep = { getRowedFieldName(mospParams.getName("EmploymentContract"), row), employmentContractCode };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST, rep);
	}
	
	/**
	 * 所属が存在しない場合のメッセージを設定する。<br>
	 * @param sectionCode 所属コード
	 * @param row         行インデックス
	 */
	protected void addSectionNotExistMessage(String sectionCode, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST,
				getRowedFieldName(getNameSection(), row), sectionCode);
	}
	
	/**
	 * 職位が存在しない場合のメッセージを設定する。<br>
	 * @param positionCode 職位コード
	 * @param row          行インデックス
	 */
	protected void addPositionNotExistMessage(String positionCode, Integer row) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST,
				getRowedFieldName(getNamePosition(), row), positionCode);
	}
	
	/**
	 * 名称項目が存在しない場合のメッセージを設定する。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param row          行インデックス
	 */
	protected void addNamingItemNotExistMessage(String namingType, String namingItemCode, Integer row) {
		// 名称区分の名称を取得する
		String namingTypeItemName = mospParams.getProperties().getCodeItemName(PlatformConst.CODE_KEY_NAMING_TYPE,
				namingType);
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_NOT_EXIST,
				getRowedFieldName(namingTypeItemName, row), namingItemCode);
	}
	
	/**
	 * 社員の履歴が存在しない場合のメッセージを設定する。<br>
	 * @param employeeCode 社員コード
	 * @param targetDate   対象日
	 */
	protected void addEmployeeHistoryNotExistMessage(String employeeCode, Date targetDate) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_EMP_HISTORY_NOT_EXIST, getStringDate(targetDate),
				employeeCode);
	}
	
	/**
	 * 期間が重複する場合のメッセージを設定する。<br>
	 * @param duplicated 重複対象
	 */
	protected void addDuplicateTermMessage(String duplicated) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_TERM_DUPLICATE, duplicated);
	}
	
	/**
	 * 入社日名称を取得する。<br>
	 * @return 入社日名称
	 */
	protected String getNameEntranceDate() {
		return PlatformNamingUtility.join(mospParams) + PlatformNamingUtility.day(mospParams);
	}
	
	/**
	 * 退職日名称を取得する。<br>
	 * @return 退職日名称
	 */
	protected String getNameRetirementDate() {
		return mospParams.getName("RetirementOn") + PlatformNamingUtility.day(mospParams);
	}
	
	/**
	 * 休職名称を取得する。<br>
	 * @return 休職名称
	 */
	protected String getNameSuspension() {
		return mospParams.getName("RetirementLeave");
	}
	
	/**
	 * 休職開始日名称を取得する。<br>
	 * @return 休職開始日名称
	 */
	protected String getNameSuspensionStartDate() {
		return getNameSuspension() + mospParams.getName("Start") + mospParams.getName("Day");
	}
	
	/**
	 * 休職終了日名称を取得する。<br>
	 * @return 休職終了日名称
	 */
	protected String getNameSuspensionEndDate() {
		return getNameSuspension() + mospParams.getName("End") + mospParams.getName("Day");
	}
	
	/**
	 * 休職終了予定日名称を取得する。<br>
	 * @return 休職終了予定日名称
	 */
	protected String getNameSuspensionScheduledEndDate() {
		return getNameSuspension() + mospParams.getName("End") + mospParams.getName("Schedule")
				+ mospParams.getName("Day");
	}
	
	/**
	 * 兼務名称を取得する。<br>
	 * @return 兼務名称
	 */
	protected String getNameConcurrent() {
		return mospParams.getName("Concurrent");
	}
	
	/**
	 * 兼務開始日名称を取得する。<br>
	 * @return 兼務開始日名称
	 */
	protected String getNameConcurrentStartDate() {
		return getNameConcurrent() + mospParams.getName("Start") + mospParams.getName("Day");
	}
	
	/**
	 * 兼務終了日名称を取得する。<br>
	 * @return 兼務終了日名称
	 */
	protected String getNameConcurrentEndDate() {
		return getNameConcurrent() + mospParams.getName("End") + mospParams.getName("Day");
	}
	
}
