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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.system.SectionRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmSectionDto;
import jp.mosp.platform.system.constant.PlatformSystemConst;

/**
 * 所属マスタ登録クラス。
 */
public class SectionRegistBean extends PlatformFileBean implements SectionRegistBeanInterface {
	
	/**
	 * 所属コード項目長。<br>
	 */
	protected static final int		LEN_SECTION_CODE	= 10;
	
	/**
	 * 所属名称項目長。<br>
	 */
	protected static final int		LEN_SECTION_NAME	= 40;
	
	/**
	 * 所属略称項目長(バイト数)。<br>
	 */
	protected static final int		LEN_SECTION_ABBR	= 6;
	
	/**
	 * 所属表示名称項目長。<br>
	 */
	protected static final int		LEN_SECTION_DISPLAY	= 16;
	
	/**
	 * 所属マスタDAOクラス。<br>
	 */
	protected SectionDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SectionRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public SectionRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = (SectionDaoInterface)createDao(SectionDaoInterface.class);
	}
	
	@Override
	public SectionDtoInterface getInitDto() {
		return new PfmSectionDto();
	}
	
	@Override
	public void insert(SectionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmSectionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(SectionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		// 追加履歴情報経路の検証
		checkClassRouteForAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmSectionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(SectionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		// 更新履歴情報経路の検証
		checkClassRouteForUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmSectionId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmSectionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String code : getCodeList(idArray)) {
			// 対象所属における有効日の情報を取得
			SectionDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象所属における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象所属情報確認
				if (dto == null) {
					// 有効日以前に情報が存在しない場合
					addNoCodeBeforeActivateDateMessage(code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				// 追加履歴情報経路の検証
				checkClassRouteForAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmSectionId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				// 更新履歴情報経路の検証
				checkClassRouteForUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmSectionId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmSectionId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, String classRoute) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String code : getCodeList(idArray)) {
			// 対象所属における有効日の情報を取得
			SectionDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象所属における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象所属情報確認
				if (dto == null) {
					// 有効日以前に情報が存在しない場合
					addNoCodeBeforeActivateDateMessage(code);
					continue;
				}
				// DTOに有効日、経路を設定
				dto.setActivateDate(activateDate);
				dto.setClassRoute(classRoute);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				// 追加履歴情報経路の検証
				checkClassRouteForAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmSectionId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに経路を設定
				dto.setClassRoute(classRoute);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				// 更新履歴情報経路の検証
				checkClassRouteForUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmSectionId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmSectionId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID毎に削除
		for (long id : idArray) {
			// 削除対象DTO取得
			SectionDtoInterface dto = (SectionDtoInterface)dao.findForKey(id, true);
			// 削除対象所属を設定している社員がいないかを確認
			checkDelete(dto);
			// 削除履歴情報経路の検証
			checkClassRouteForDelete(dto);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(SectionDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getSectionCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(SectionDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getSectionCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getSectionCode(), humanList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(SectionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmSectionId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmSectionId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 更新対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getSectionCode(), humanList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象所属を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(SectionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmSectionId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getSectionCode(), humanList);
	}
	
	/**
	 * 履歴追加時の経路確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkClassRouteForAdd(SectionDtoInterface dto) throws MospException {
		// 確認対象DTOの経路に自らが含まれていないかを確認
		checkClassRouteContainSelf(dto);
		// 一つ前の履歴を取得
		SectionDtoInterface beforeDto = dao.findForInfo(dto.getSectionCode(), dto.getActivateDate());
		// 一つ前の履歴を確認
		if (beforeDto == null || isDtoActivate(beforeDto) == false) {
			// 一つ前の履歴が存在しない、又は無効であれば確認不要
			return;
		}
		// 無効フラグ確認
		if (isDtoActivate(beforeDto) == false && isDtoActivate(dto) == false) {
			// 無効→無効であれば確認不要
			return;
		}
		// 一つ前の履歴と比較し、経路に変更があるかを確認
		if (isDtoActivate(beforeDto) && isDtoActivate(dto) && beforeDto.getClassRoute().equals(dto.getClassRoute())) {
			// 経路に変更が無ければ確認不要(有効→有効)
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 所属コード使用確認
		checkCodeIsUsedAsClassRoute(dto.getSectionCode(), getSectionListForCheck(dto, list));
	}
	
	/**
	 * 履歴更新時の経路確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkClassRouteForUpdate(SectionDtoInterface dto) throws MospException {
		// 更新前情報取得
		SectionDtoInterface currentDto = dao.findForKey(dto.getSectionCode(), dto.getActivateDate());
		// 無効フラグ確認
		if (isDtoActivate(currentDto) == false && isDtoActivate(dto) == false) {
			// 無効→無効であれば確認不要
			return;
		}
		// 確認対象DTOの経路に自らが含まれていないかを確認
		checkClassRouteContainSelf(dto);
		// 無効フラグ、経路変更確認
		if (isDtoActivate(currentDto) && isDtoActivate(dto) && currentDto.getClassRoute().equals(dto.getClassRoute())) {
			// 経路に変更が無ければ確認不要(有効→有効)
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 所属コード使用確認
		checkCodeIsUsedAsClassRoute(dto.getSectionCode(), getSectionListForCheck(dto, list));
	}
	
	/**
	 * 履歴削除時の経路確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkClassRouteForDelete(SectionDtoInterface dto) throws MospException {
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば確認不要
			return;
		}
		// 一つ前の履歴を取得
		SectionDtoInterface beforeDto = dao.findForInfo(dto.getSectionCode(),
				DateUtility.addDay(dto.getActivateDate(), -1));
		// 一つ前の履歴を確認
		if (beforeDto != null && isDtoActivate(beforeDto) && beforeDto.getClassRoute().equals(dto.getClassRoute())) {
			// 一つ前の履歴が存在し、有効であり、経路に変更が無ければ、確認不要
			return;
		}
		// 履歴削除対象コードの履歴情報を取得
		List<SectionDtoInterface> list = dao.findForHistory(dto.getSectionCode());
		// 確認すべき所属マスタリストに対し、経路としての使用を確認
		checkCodeIsUsedAsClassRoute(dto.getSectionCode(), getSectionListForCheck(dto, list));
	}
	
	/**
	 * 確認すべき所属マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新の所属マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * 所属マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時におけるコード使用確認等で用いられる。<br>
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<SectionDtoInterface> getSectionListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 削除対象の有効日以前で最新の人事情報を取得
		List<SectionDtoInterface> sectionList = dao.findForActivateDate(dto.getActivateDate(), new String[0]);
		// 無効期間で人事履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		sectionList.addAll(dao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return sectionList;
	}
	
	/**
	 * 所属コードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return 所属コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			SectionDtoInterface dto = (SectionDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getSectionCode());
		}
		return list;
	}
	
	/**
	 * 人事マスタリスト内に所属コードが使用されている情報がないかの確認をする。<br>
	 * @param code 所属コード
	 * @param list 人事マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<HumanDtoInterface> list) {
		// 人事マスタリストの中身を確認
		for (HumanDtoInterface dto : list) {
			// 所属コード確認
			if (code.equals(dto.getSectionCode())) {
				// メッセージ設定
				addCodeIsUsedMessage(code, dto);
			}
		}
	}
	
	/**
	 * 所属マスタリスト内に所属コードが経路として使用されている情報がないかの確認をする。<br>
	 * @param code 所属コード
	 * @param list 人事マスタリスト
	 */
	protected void checkCodeIsUsedAsClassRoute(String code, List<?> list) {
		// 対象所属コードに経路区切文字を付加
		String classRoute = PlatformSystemConst.SEPARATOR_CLASS_ROUTE + code
				+ PlatformSystemConst.SEPARATOR_CLASS_ROUTE;
		// 所属マスタリストの中身を確認
		for (int i = 0; i < list.size(); i++) {
			SectionDtoInterface dto = (SectionDtoInterface)list.get(i);
			// 所属コード確認
			if (dto.getClassRoute().indexOf(classRoute) != -1) {
				// メッセージ設定
				mospParams.addErrorMessage(PlatformMessageConst.MSG_CODE_IS_USED_AS_ROUTE, code, dto.getSectionCode());
			}
		}
	}
	
	/**
	 * 確認対象DTOの経路に、自らが含まれているかの確認をする。<br>
	 * 自らを上位所属に設定することはできない。<br>
	 * @param dto 確認対象DTO
	 */
	protected void checkClassRouteContainSelf(SectionDtoInterface dto) {
		// 対象所属コードに経路区切文字を付加
		String classRoute = PlatformSystemConst.SEPARATOR_CLASS_ROUTE + dto.getSectionCode()
				+ PlatformSystemConst.SEPARATOR_CLASS_ROUTE;
		// 対象経路確認
		if (dto.getClassRoute().indexOf(classRoute) != -1) {
			mospParams.addErrorMessage(PlatformMessageConst.MSG_ROUTE_CONTAINS_CODE);
		}
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void validate(SectionDtoInterface dto, Integer row) throws MospException {
		// 必須確認(所属コード)
		checkRequired(dto.getSectionCode(), getNameSectionCode(), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), getNameActivateDate(), row);
		// 桁数確認(所属コード)
		checkLength(dto.getSectionCode(), LEN_SECTION_CODE, getNameSectionCode(), row);
		// 桁数確認(所属名称)
		checkLength(dto.getSectionName(), LEN_SECTION_NAME, getNameSectionName(), row);
		// 桁数確認(所属表示名称)
		checkLength(dto.getSectionDisplay(), LEN_SECTION_DISPLAY, getNameSectionDisplay(), row);
		// バイト数(表示上)確認(所属略称)
		checkByteLength(dto.getSectionAbbr(), LEN_SECTION_ABBR, getNameSectionAbbr(), row);
		// 型確認(所属コード)
		checkTypeCode(dto.getSectionCode(), getNameSectionCode(), row);
		// 型確認(閉鎖フラグ)
		checkInactivateFlag(dto.getCloseFlag(), row);
		// 無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 妥当性確認終了
			return;
		}
		// 必須確認(所属名称)
		checkRequired(dto.getSectionName(), getNameSectionName(), row);
		// 必須確認(所属略称)
		checkRequired(dto.getSectionAbbr(), getNameSectionAbbr(), row);
	}
	
	/**
	 * 所属コード名称を取得する。<br>
	 * @return 所属コード名称
	 */
	protected String getNameSectionCode() {
		return mospParams.getName("Section") + mospParams.getName("Code");
	}
	
	/**
	 * 所属名称名称を取得する。<br>
	 * @return 所属名称名称
	 */
	protected String getNameSectionName() {
		return mospParams.getName("Section") + mospParams.getName("Name");
	}
	
	/**
	 * 所属略称名称を取得する。<br>
	 * @return 所属略称名称
	 */
	protected String getNameSectionAbbr() {
		return mospParams.getName("Section") + mospParams.getName("Abbreviation");
	}
	
	/**
	 * 所属表示名称名称を取得する。<br>
	 * @return 所属表示名称名称
	 */
	protected String getNameSectionDisplay() {
		return mospParams.getName("Section") + mospParams.getName("Display") + mospParams.getName("Name");
	}
	
}
