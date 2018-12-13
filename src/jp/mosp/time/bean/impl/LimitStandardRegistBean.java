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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmLimitStandardDto;

/**
 * 限度基準登録インターフェース。<br>
 */
public class LimitStandardRegistBean extends PlatformBean implements LimitStandardRegistBeanInterface {
	
	/**
	 * 限度基準管理DAO
	 */
	private LimitStandardDaoInterface	dao;
	/**
	 * 勤怠設定管理DAO
	 */
	private TimeSettingDaoInterface		timeSettingdao;
	
	/**
	 * 期間【1週】。<br>
	 */
	public static final String			ONE_WEEK	= "week1";
	
	/**
	 * 期間【2週】。<br>
	 */
	public static final String			TWO_WEEK	= "week2";
	
	/**
	 * 期間【4週】。<br>
	 */
	public static final String			FOUR_WEEK	= "week4";
	
	/**
	 * 期間【1ヶ月】。<br>
	 */
	public static final String			ONE_MONTH	= "month1";
	
	/**
	 * 期間【2ヶ月】。<br>
	 */
	public static final String			TWO_MONTH	= "month2";
	
	/**
	 * 期間【3ヶ月】。<br>
	 */
	public static final String			THREE_MONTH	= "month3";
	
	/**
	 * 期間【1年】。<br>
	 */
	public static final String			ONE_YEAR	= "year1";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public LimitStandardRegistBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション
	 */
	public LimitStandardRegistBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (LimitStandardDaoInterface)createDao(LimitStandardDaoInterface.class);
		timeSettingdao = (TimeSettingDaoInterface)createDao(TimeSettingDaoInterface.class);
	}
	
	@Override
	public LimitStandardDtoInterface getInitDto() {
		return new TmmLimitStandardDto();
	}
	
	@Override
	public void insert(LimitStandardDtoInterface dto) throws MospException {
		// 限度基準欄の入力確認
		/*
		if (!checkInput(dto)) {
			//　限度基準欄が入力されていなければ登録しない。
			return;
		}*/
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
		dto.setTmmLimitStandardId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(LimitStandardDtoInterface dto) throws MospException {
		// 更新前データが登録されているかを確認
		LimitStandardDtoInterface preDto = dao.findForInfo(dto.getWorkSettingCode(), dto.getActivateDate(),
				dto.getTerm());
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新前データが登録されている場合のみ実行
		if (preDto != null) {
			// レコード識別IDを取得。
			dto.setTmmLimitStandardId(getRecordID(dto));
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmLimitStandardId());
		}
		// 限度基準欄の入力確認
		//if (checkInput(dto)) {
		//　限度基準欄が入力されていれば登録。
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmLimitStandardId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		//}
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// 限度基準データ 
		String[] termArray = { ONE_WEEK, TWO_WEEK, FOUR_WEEK, ONE_MONTH, TWO_MONTH, THREE_MONTH, ONE_YEAR };
		// 対象となる
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			// 限度基準データ 期間分処理を行う。
			for (String element : termArray) {
				// 対象勤怠設定情報における有効日の情報を取得
				LimitStandardDtoInterface dto = dao.findForKey(code, activateDate, element);
				// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
				if (dto == null) {
					// 対象勤怠設定情報における有効日以前で最新の情報を取得
					dto = dao.findForInfo(code, activateDate, element);
					if (dto == null) {
						// 有効日以前で最新の情報も存在しなければ履歴追加処理を行わない
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
					dto.setTmmLimitStandardId(dao.nextRecordId());
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
					logicalDelete(dao, dto.getTmmLimitStandardId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmLimitStandardId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				}
			}
		}
	}
	
	@Override
	public void delete(LimitStandardDtoInterface dto) throws MospException {
		// 対象レコードが登録されているかを確認
		if (dao.findForKey(dto.getWorkSettingCode(), dto.getActivateDate(), dto.getTerm()) == null) {
			// 対象レコード未登録であれば何もしない
			return;
		}
		// レコード識別IDを取得。
		dto.setTmmLimitStandardId(getRecordID(dto));
		// 削除対象勤怠設定情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmmLimitStandardId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(LimitStandardDtoInterface dto) throws MospException {
		// TODO 対象レコードの有効日が重複していないかを確認
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(LimitStandardDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkSettingCode(), dto.getActivateDate(), dto.getTerm()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(LimitStandardDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmLimitStandardId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getTmmLimitStandardId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象勤怠設定情報を設定している設定適用管理情報がないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(LimitStandardDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmLimitStandardId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(LimitStandardDtoInterface dto) {
		// TODO 妥当性確認
	}
	
	/**
	 * 勤怠設定情報リストを取得する。<br>
	 * 一括処理時に勤怠設定情報をまず更新するため、排他確認は行わない。<br>
	 * @param idArray レコード識別ID配列
	 * @return 勤怠設定情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			TimeSettingDtoInterface dto = (TimeSettingDtoInterface)timeSettingdao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getWorkSettingCode());
		}
		return list;
	}
	
	/**
	 * 限度基準が入力されているか(登録の対象となるか)を確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：入力あり、false：入力なし)
	 */
	protected boolean checkInput(LimitStandardDtoInterface dto) {
		if (dto.getLimitTime() != 0 || dto.getAttentionTime() != 0 || dto.getWarningTime() != 0) {
			// 時間外限度時間、時間外注意時間、時間外警告時間のいずれかが
			// 入力されていれば登録対象とする。
			return true;
		}
		return false;
	}
	
	/**
	 * レコード識別IDを取得する。<br>
	 * @param dto 	対象DTO
	 * @return レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected long getRecordID(LimitStandardDtoInterface dto) throws MospException {
		// 勤怠設定情報を取得する。
		LimitStandardDtoInterface subDto = dao.findForKey(dto.getWorkSettingCode(), dto.getActivateDate(),
				dto.getTerm());
		// レコード識別IDを返す。
		return subDto.getTmmLimitStandardId();
	}
	
}
