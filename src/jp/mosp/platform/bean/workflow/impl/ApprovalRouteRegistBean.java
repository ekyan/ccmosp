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
package jp.mosp.platform.bean.workflow.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalRouteDto;

/**
 * 承認ルートマスタ登録クラス。
 */
public class ApprovalRouteRegistBean extends PlatformBean implements ApprovalRouteRegistBeanInterface {
	
	/**
	 * 承認ルートマスタDAO
	 */
	private ApprovalRouteDaoInterface	dao;
	
	/**
	 *  承認ルートユニットマスタDAO
	 */
	RouteApplicationDaoInterface		routeAppDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalRouteRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public ApprovalRouteRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (ApprovalRouteDaoInterface)createDao(ApprovalRouteDaoInterface.class);
		routeAppDao = (RouteApplicationDaoInterface)createDao(RouteApplicationDaoInterface.class);
	}
	
	@Override
	public ApprovalRouteDtoInterface getInitDto() {
		return new PfmApprovalRouteDto();
		
	}
	
	@Override
	public void add(ApprovalRouteDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmApprovalRouteId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(ApprovalRouteDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmApprovalRouteId(getRecordID(dto));
		// 削除対象ユニット情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmApprovalRouteId());
	}
	
	@Override
	public void insert(ApprovalRouteDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmApprovalRouteId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(ApprovalRouteDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmApprovalRouteId(getRecordID(dto));
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmApprovalRouteId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmApprovalRouteId(dao.nextRecordId());
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
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			// 対象ユニット情報における有効日の情報を取得
			ApprovalRouteDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象ユニット情報における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象ユニット情報確認
				if (dto == null) {
					// 有効日以前に情報が存在しない場合
					addNoCodeBeforeActivateDateMessage(code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmApprovalRouteId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmApprovalRouteId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmApprovalRouteId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	/**
	 * ルートコードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return ルートコードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			ApprovalRouteDtoInterface dto = (ApprovalRouteDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getRouteCode());
		}
		return list;
		
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkAdd(ApprovalRouteDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getRouteCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ApprovalRouteDtoInterface> list = dao.findForHistory(dto.getRouteCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき承認ルート適用マスタリストを取得
		List<RouteApplicationDtoInterface> applicationList = getRouteApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getRouteCode(), applicationList);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	private void validate(ApprovalRouteDtoInterface dto) {
		// TODO 妥当性確認
	}
	
	/**
	 * レコード識別IDを取得する。<br>
	 * @param dto 	対象DTO
	 * @return レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private long getRecordID(ApprovalRouteDtoInterface dto) throws MospException {
		// ユニット情報を取得する。
		ApprovalRouteDtoInterface subDto = dao.findForKey(dto.getRouteCode(), dto.getActivateDate());
		// レコード識別IDを返す。
		return subDto.getPfmApprovalRouteId();
		
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象ルート情報を設定しているルート適用管理情報がないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkDelete(ApprovalRouteDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmApprovalRouteId());
		// 対象DTOの無効フラグ確認
		// 画面上の無効フラグは変更可能であるため確認しない。
		if (isDtoActivate(dao.findForKey(dto.getPfmApprovalRouteId(), true)) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<ApprovalRouteDtoInterface> list = dao.findForHistory(dto.getRouteCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき承認ルート適用マスタリストを取得
		List<RouteApplicationDtoInterface> routeApplicationList = getRouteApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getRouteCode(), routeApplicationList);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkInsert(ApprovalRouteDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getRouteCode()));
		
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkUpdate(ApprovalRouteDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmApprovalRouteId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmApprovalRouteId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ApprovalRouteDtoInterface> list = dao.findForHistory(dto.getRouteCode());
		// 確認するべき承認ルート適用マスタリストを取得
		List<RouteApplicationDtoInterface> routeApplicationList = getRouteApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getRouteCode(), routeApplicationList);
	}
	
	/**
	 * ルート適用マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list ルート適用マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<RouteApplicationDtoInterface> list) {
		// 同一のルート適用コードのメッセージは出力しない。
		String codeAdded = "";
		// 承認ルートユニットマスタリストの中身を確認
		for (RouteApplicationDtoInterface dto : list) {
			// 対象コード確認
			if ((code.equals(dto.getRouteCode())) && (isDtoActivate(dto))) {
				// 同一のルートコードのメッセージは出力しない。
				if (!codeAdded.equals(dto.getRouteApplicationCode())) {
					// メッセージ設定
					addRouteCodeIsUsedMessage(code, dto);
					// メッセージに設定したルートコードを保持
					codeAdded = dto.getRouteApplicationCode();
				}
			}
		}
	}
	
	/**
	 * 確認すべきルート適用マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新のルート適用マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * ルート適用マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto 	対象DTO
	 * @param list ルート適用リスト
	 * @return ルート適用リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<RouteApplicationDtoInterface> getRouteApplicationListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 削除対象の有効日以前で最新の承認ルートユニットマスタ情報を取得
		List<RouteApplicationDtoInterface> applicationList = routeAppDao.findForActivateDate(dto.getActivateDate());
		// 無効期間で承認ルートユニットマスタ履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		applicationList
			.addAll(routeAppDao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return applicationList;
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  ルート適用マスタDTO
	 */
	protected void addRouteCodeIsUsedMessage(String code, RouteApplicationDtoInterface dto) {
		mospParams.addErrorMessage(PlatformMessageConst.MSG_ROUTE_CODE_IS_USED, code, dto.getRouteApplicationCode());
	}
	
}
