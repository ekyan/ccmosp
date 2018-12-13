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
package jp.mosp.platform.bean.human.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.impl.PfmHumanDto;

/**
 * 人事マスタ登録クラス。
 */
public class HumanRegistBean extends PlatformHumanBean implements HumanRegistBeanInterface {
	
	/**
	 * 社員コード項目長。<br>
	 */
	protected static final int				LEN_EMPLOYEE_CODE	= 10;
	
	/**
	 * 社員名(姓、名、姓(カナ)、名(カナ))項目長。<br>
	 */
	protected static final int				LEN_EMPLOYEE_NAME	= 10;
	
	/**
	 * 人事マスタDAOクラス。<br>
	 */
	protected HumanDaoInterface				dao;
	
	/**
	 * 個人IDフォーマット。
	 */
	protected static final String			FORMAT_PERSONAL_ID	= "P000000000";
	
	/**
	 * 
	 */
	protected HumanReferenceBeanInterface	humanReference;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public HumanRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// DAO準備
		dao = (HumanDaoInterface)createDao(HumanDaoInterface.class);
		humanReference = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public HumanDtoInterface getInitDto() {
		return new PfmHumanDto();
	}
	
	@Override
	public void insert(HumanDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 個人IDを生成してDTOに設定
		dto.setPersonalId(getPersonalId());
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmHumanId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(HumanDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmHumanId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HumanDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmHumanId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmHumanId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(HumanDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPfmHumanId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(HumanDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPersonalId()));
		// 社員コード重複確認
		checkEmployeeCodeDuplicate(dto.getPersonalId(), dto.getEmployeeCode());
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(HumanDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPersonalId(), dto.getActivateDate()));
		// 社員コード重複確認
		checkEmployeeCodeDuplicate(dto.getPersonalId(), dto.getEmployeeCode());
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HumanDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmHumanId());
		// 社員コード重複確認
		checkEmployeeCodeDuplicate(dto.getPersonalId(), dto.getEmployeeCode());
	}
	
	/**
	 * 社員コードの重複確認を行う。<br>
	 * 異なる個人IDで同一社員コードが設定されている場合に、メッセージを追加する。<br>
	 * @param personalId   個人ID
	 * @param employeeCode 社員コード
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkEmployeeCodeDuplicate(String personalId, String employeeCode) throws MospException {
		// 対象社員コードが設定されている情報を取得
		List<HumanDtoInterface> list = dao.findForEmployeeCode(employeeCode);
		// 異なる個人IDで同一社員コードが設定されていないかを確認
		for (HumanDtoInterface dto : list) {
			if (dto.getPersonalId().equals(personalId)) {
				continue;
			}
			// メッセージ追加
			addEmployeeCodeDuplicateMessage(employeeCode);
			return;
		}
	}
	
	/**
	 * 新規登録用の個人IDを取得する。<br>
	 * @return 新規登録用個人ID
	 * @throws MospException 最大個人IDの取得に失敗した場合
	 */
	protected String getPersonalId() throws MospException {
		// 個人ID最大値取得
		long seq = dao.nextPersonalId();
		// 個人ID発行
		return issueSequenceNo(seq, FORMAT_PERSONAL_ID);
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void validate(HumanDtoInterface dto, Integer row) throws MospException {
		// 履歴一覧取得
		List<HumanDtoInterface> list = humanReference.getHistory(dto.getPersonalId());
		// 影響を及ぼす期間取得
		Date startDate = dto.getActivateDate();
		Date endDate = getLastDate(dto.getActivateDate(), list);
		// 必須確認(社員コード)
		checkRequired(dto.getEmployeeCode(), getNameEmployeeCode(), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), getNameActivateDate(), row);
		// 必須確認(姓)
		checkRequired(dto.getLastName(), getNameLastName(), row);
		// 桁数確認(社員コード)
		checkLength(dto.getEmployeeCode(), LEN_EMPLOYEE_CODE, getNameEmployeeCode(), row);
		// 桁数確認(姓)
		checkLength(dto.getLastName(), LEN_EMPLOYEE_NAME, getNameLastName(), row);
		// 桁数確認(名)
		checkLength(dto.getFirstName(), LEN_EMPLOYEE_NAME, getNameFirstName(), row);
		// 桁数確認(姓(カナ))
		checkLength(dto.getLastKana(), LEN_EMPLOYEE_NAME, getNameLastKana(), row);
		// 桁数確認(名(カナ))
		checkLength(dto.getFirstKana(), LEN_EMPLOYEE_NAME, getNameFirstKana(), row);
		// 型確認(社員コード)
		checkTypeCode(dto.getEmployeeCode(), getNameEmployeeCode(), row);
		// 型確認(姓(カナ))
		checkTypeKana(dto.getLastKana(), getNameLastKana(), row);
		// 型確認(名(カナ))
		checkTypeKana(dto.getFirstKana(), getNameFirstKana(), row);
		// 勤務地存在確認
		checkWorkPlace(dto.getWorkPlaceCode(), startDate, endDate, row);
		// 雇用契約存在確認
		checkEmploymentContract(dto.getEmploymentContractCode(), startDate, endDate, row);
		// 所属存在確認
		checkSection(dto.getSectionCode(), startDate, endDate, row);
		// 職位存在確認
		checkPosition(dto.getPositionCode(), startDate, endDate, row);
	}
	
	/**
	 * 情報が影響を及ぼす期間の最終日を取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 対象日から見て次の履歴の有効日の前日を取得する。<br>
	 * 次の履歴が存在しない場合は、nullを返す。<br>
	 * 人事情報登録時マスタ整合性確認等に、用いる。<br>
	 * @param targetDate 対象日
	 * @param list 履歴一覧
	 * @return 一つ最新の履歴の有効日の前日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getLastDate(Date targetDate, List<HumanDtoInterface> list) throws MospException {
		// 最終日宣言
		Date lastDate = null;
		// 履歴リスト確認
		for (HumanDtoInterface dto : list) {
			// 有効日確認
			if (targetDate.compareTo(dto.getActivateDate()) >= 0) {
				// 対象日以前であればcontinue
				continue;
			}
			// 対象日より後で直後の履歴がある場合
			if (dto.getActivateDate() != null) {
				// 一つ最新の履歴の有効日前日を取得
				lastDate = DateUtility.addDay(dto.getActivateDate(), -1);
				break;
			}
		}
		return lastDate;
	}
	
	/**
	 * 社員コードが重複している場合のメッセージを設定する。<br>
	 * @param employeeCode 社員コード
	 */
	protected void addEmployeeCodeDuplicateMessage(String employeeCode) {
		String[] rep = { getNameEmployeeCode(), employeeCode };
		mospParams.addErrorMessage(PlatformMessageConst.MSG_SELECTED_CODE_EXIST, rep);
	}
	
	/**
	 * 姓名称を取得する。<br>
	 * @return 姓名称
	 */
	protected String getNameLastName() {
		return mospParams.getName("LastName");
	}
	
	/**
	 * 名名称を取得する。<br>
	 * @return 名名称
	 */
	protected String getNameFirstName() {
		return mospParams.getName("FirstName");
	}
	
	/**
	 * 姓(カナ)名称を取得する。<br>
	 * @return 姓名称
	 */
	protected String getNameLastKana() {
		return mospParams.getName("LastName") + mospParams.getName("FrontParentheses") + mospParams.getName("Kana")
				+ mospParams.getName("BackParentheses");
	}
	
	/**
	 * 名(カナ)名称を取得する。<br>
	 * @return 姓名称
	 */
	protected String getNameFirstKana() {
		return mospParams.getName("FirstName") + mospParams.getName("FrontParentheses") + mospParams.getName("Kana")
				+ mospParams.getName("BackParentheses");
	}
	
}
