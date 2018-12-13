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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeDto;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤務形態マスタ参照クラス。
 */
public class WorkTypeReferenceBean extends PlatformBean implements WorkTypeReferenceBeanInterface {
	
	/**
	 * 勤務形態マスタDAO。
	 */
	protected WorkTypeDaoInterface		dao;
//	private WorkTypeDaoInterface		dao;
	
	/**
	 * 勤務形態項目管理DAO。
	 */
	protected WorkTypeItemDaoInterface	workTypeItemDao;
//	private WorkTypeItemDaoInterface	workTypeItemDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeReferenceBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosPパラメータクラス
	 * @param connection DBコネクション
	 */
	public WorkTypeReferenceBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		dao = (WorkTypeDaoInterface)createDao(WorkTypeDaoInterface.class);
		workTypeItemDao = (WorkTypeItemDaoInterface)createDao(WorkTypeItemDaoInterface.class);
	}
	
	@Override
	public WorkTypeDtoInterface getWorkTypeInfo(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態確認(法定休日出勤)
		if (workTypeCode.equals(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY)) {
			return getWorkOnLegalHolidayWorkType();
		}
		// 勤務形態確認(所定休日出勤)
		if (workTypeCode.equals(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY)) {
			return getWorkOnPrescribedHolidayWorkType();
		}
		// 勤務形態マスタ情報取得
		return findForInfo(workTypeCode, targetDate);
	}
	
	@Override
	public List<WorkTypeDtoInterface> getWorkTypeHistory(String workTypeCode) throws MospException {
		return dao.findForHistory(workTypeCode);
	}
	
	@Override
	public String getWorkTypeAbbr(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態確認(所定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
			return getPrescribedHolidayAbbrNaming();
		}
		// 勤務形態確認(法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			return getLegalHolidayAbbrNaming();
		}
		// 勤務形態マスタ情報取得及び確認
		WorkTypeDtoInterface dto = getWorkTypeInfo(workTypeCode, targetDate);
		if (dto == null) {
			// 勤務形態マスタ情報が存在しない場合
			return workTypeCode;
		}
		// 勤務形態マスタ情報略称取得
		return dto.getWorkTypeAbbr();
	}
	
	@Override
	public String getParticularWorkTypeName(String workTypeCode) {
		// 法定休日出勤の場合
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)) {
			return getWorkOnLegalHolidayNaming();
		}
		// 所定休日出勤の場合
		if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			return getWorkOnPrescribedHolidayNaming();
		}
		// 法定休日の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)) {
			return getLegalHolidayNaming();
		}
		// 所定休日の場合
		if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			return getPrescribedHolidayNaming();
		}
		return null;
	}
	
	@Override
	public String getWorkTypeNameAndTime(String workTypeCode, Date targetDate) throws MospException {
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeName());
		sb.append(getWorkBeginAndWorkEnd(dto));
		return sb.toString();
	}
	
	@Override
	public String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態マスタからレコードを取得
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeAbbr());
		sb.append(getWorkBeginAndWorkEnd(dto));
		return sb.toString();
	}
	
	@Override
	public String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeAbbr());
		sb.append(getWorkBeginAndWorkEnd(dto, amHoliday, pmHoliday));
		return sb.toString();
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, true, false, false, false);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate) throws MospException {
		// 一覧取得
		List<WorkTypeDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列及びインデックス宣言
		String[][] array = new String[list.size()][2];
		int idx = 0;
		// コードの最大文字数確認
		int codeLength = 0;
		for (WorkTypeDtoInterface dto : list) {
			if (dto.getWorkTypeCode().length() > codeLength) {
				codeLength = dto.getWorkTypeCode().length();
			}
		}
		// 配列作成
		for (WorkTypeDtoInterface dto : list) {
			array[idx][0] = dto.getWorkTypeCode();
			array[idx][1] = getCodedName(dto.getWorkTypeCode(), dto.getWorkTypeAbbr(), codeLength);
			idx++;
		}
		return array;
	}
	
	@Override
	public String[][] getTimeSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, false, true, false, false);
	}
	
	@Override
	public String[][] getTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday) throws MospException {
		return getSelectArray(targetDate, false, true, amHoliday, pmHoliday);
	}
	
	@Override
	public String[][] getNameTimeSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, true, true, false, false);
	}
	
	@Override
	public String[][] getNameTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		return getSelectArray(targetDate, true, true, amHoliday, pmHoliday);
	}
	
	@Override
	public WorkTypeDtoInterface findForKey(String workTypeCode, Date activateDate) throws MospException {
		return dao.findForKey(workTypeCode, activateDate);
	}
	
	@Override
	public String[][] getSelectAbbrArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, false, false, false, false);
	}
	
	@Override
	public WorkTypeDtoInterface findForInfo(String workTypeCode, Date activateDate) throws MospException {
		return dao.findForInfo(workTypeCode, activateDate);
	}
	
	@Override
	public List<WorkTypeEntity> getWorkTypeEntityHistory(String workTypeCode) throws MospException {
		// 勤務形態エンティティ履歴(有効日昇順)を準備
		List<WorkTypeEntity> history = new ArrayList<WorkTypeEntity>();
		// 勤務形態情報履歴を取得
		List<WorkTypeDtoInterface> list = getWorkTypeHistory(workTypeCode);
		// 勤務形態情報毎に処理
		for (WorkTypeDtoInterface dto : list) {
			// 有効日を取得
			Date activateDate = dto.getActivateDate();
			// 勤務形態項目情報リストを取得
			List<WorkTypeItemDtoInterface> itemList = workTypeItemDao.findForWorkType(workTypeCode, activateDate);
			// 勤務形態エンティティを取得し設定
			history.add(createWorkTypeEntity(dto, itemList));
		}
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		return history;
	}
	
	@Override
	public WorkTypeEntity getWorkTypeEntity(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態情報取得
		WorkTypeDtoInterface dto = getWorkTypeInfo(workTypeCode, targetDate);
		// 勤務形態情報確認
		if (dto != null) {
			// 勤務形態項目情報リスト取得
			List<WorkTypeItemDtoInterface> list = workTypeItemDao.findForWorkType(workTypeCode, dto.getActivateDate());
			// 勤務形態エンティティを取得
			return createWorkTypeEntity(dto, list);
		}
		// 勤務形態コード確認
		if (workTypeCode == null) {
			// 勤務形態コードが無い場合
			dto = getInitDto();
		} else if (workTypeCode.equals(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY)) {
			// 法定休日出勤の場合
			dto = getWorkOnLegalHolidayWorkType();
		} else if (workTypeCode.equals(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY)) {
			// 所定休日出勤の場合
			dto = getWorkOnPrescribedHolidayWorkType();
		} else if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 法定休日の場合
			dto = getLegalHolidayWorkType();
		} else if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
			// 所定休日の場合
			dto = getPrescribedHolidayWorkType();
		} else {
			// その他の場合
			dto = getInitDto();
			dto.setWorkTypeCode(workTypeCode);
		}
		// 勤務形態エンティティを取得
		return createWorkTypeEntity(dto, new ArrayList<WorkTypeItemDtoInterface>());
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @param viewTime 時刻表示(true：時刻表示、false：時刻非表示)
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean isName, boolean viewTime, boolean amHoliday,
			boolean pmHoliday) throws MospException {
		// 一覧取得
		List<WorkTypeDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列宣言
		String[][] array = prepareSelectArray(list.size(), false);
		// 配列作成
		for (int i = 0; i < list.size(); i++) {
			WorkTypeDtoInterface dto = list.get(i);
			StringBuffer sb = new StringBuffer();
			if (isName) {
				// 名称
				sb.append(dto.getWorkTypeName());
			} else {
				// 略称
				sb.append(dto.getWorkTypeAbbr());
			}
			if (viewTime) {
				// 時刻表示
				sb.append(getWorkBeginAndWorkEnd(dto, amHoliday, pmHoliday));
			}
			array[i][0] = dto.getWorkTypeCode();
			array[i][1] = sb.toString();
		}
		return array;
	}
	
	/**
	 * 【出勤時刻～退勤時刻】を取得する。<br>
	 * @param dto 対象DTO
	 * @return 【出勤時刻～退勤時刻】
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkBeginAndWorkEnd(WorkTypeDtoInterface dto) throws MospException {
		WorkTypeItemDtoInterface workBeginDto = workTypeItemDao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(),
				TimeConst.CODE_WORKSTART);
		WorkTypeItemDtoInterface workEndDto = workTypeItemDao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(),
				TimeConst.CODE_WORKEND);
		Date defaultTime = DateUtility.getDefaultTime();
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(DateUtility.getStringTime(workBeginDto.getWorkTypeItemValue(), defaultTime));
		sb.append(mospParams.getName("Wave"));
		sb.append(DateUtility.getStringTime(workEndDto.getWorkTypeItemValue(), defaultTime));
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		return sb.toString();
	}
	
	/**
	 * 【始業時刻～終業時刻】を取得する。<br>
	 * @param dto 対象DTO
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 【始業時刻～終業時刻】
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkBeginAndWorkEnd(WorkTypeDtoInterface dto, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		Date defaultTime = DateUtility.getDefaultTime();
		WorkTypeEntity workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode(), dto.getActivateDate());
		if (amHoliday) {
			// 午前休である場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("FrontWithCornerParentheses"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getBackStartTime(), defaultTime));
			sb.append(mospParams.getName("Wave"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getBackEndTime(), defaultTime));
			sb.append(mospParams.getName("BackWithCornerParentheses"));
			return sb.toString();
		}
		if (pmHoliday) {
			// 午後休である場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("FrontWithCornerParentheses"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getFrontStartTime(), defaultTime));
			sb.append(mospParams.getName("Wave"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getFrontEndTime(), defaultTime));
			sb.append(mospParams.getName("BackWithCornerParentheses"));
			return sb.toString();
		}
		// 午前休・午後休でない場合
		return getWorkBeginAndWorkEnd(dto);
	}
	
	/**
	 * 法定休日出勤勤務形態情報を取得する。<br>
	 * @return 法定休日出勤勤務形態情報
	 */
	protected WorkTypeDtoInterface getWorkOnLegalHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY);
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkOnLegalHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 所定休日出勤勤務形態情報を取得する。<br>
	 * @return 所定休日出勤勤務形態情報
	 */
	protected WorkTypeDtoInterface getWorkOnPrescribedHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY);
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkOnPrescribedHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 法定休日勤務形態情報を取得する。<br>
	 * @return 法定休日勤務形態情報
	 */
	protected WorkTypeDtoInterface getLegalHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY);
		// 勤務形態名称設定
		dto.setWorkTypeName(getLegalHolidayNaming());
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getLegalHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 所定休日勤務形態情報を取得する。<br>
	 * @return 所定休日勤務形態情報
	 */
	protected WorkTypeDtoInterface getPrescribedHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY);
		// 勤務形態名称設定
		dto.setWorkTypeName(getPrescribedHolidayNaming());
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getPrescribedHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 初期化された勤務形態情報を取得する。<br>
	 * @return 初期化された勤務形態情報
	 */
	protected WorkTypeDtoInterface getInitDto() {
		// DTO準備
		WorkTypeDtoInterface dto = new TmmWorkTypeDto();
		return dto;
	}
	
	/**
	 * 勤務形態エンティティの作成
	 * @param workTypeDto 勤務形態情報
	 * @param itemDtoList 勤務形態項目情報リスト
	 * @return 勤務形態エンティティ
	 */
	protected WorkTypeEntity createWorkTypeEntity(WorkTypeDtoInterface workTypeDto, 
			List<WorkTypeItemDtoInterface> itemDtoList) throws MospException {
		// インターフェースで返すべきだが、修正箇所を減らすため、クラスとして返す
		WorkTypeEntity entity = (WorkTypeEntity)createObject(WorkTypeEntityInterface.class);
		entity.setWorkTypeDto(workTypeDto);
		entity.setWorkTypeItemList(itemDtoList);
		
		return entity;
	}
	
	/**
	 * 所定休日略称を取得する。<br>
	 * @return 所定休日略称
	 */
	protected String getPrescribedHolidayAbbrNaming() {
		return mospParams.getName("PrescribedAbbreviation") + mospParams.getName("Holiday");
	}
	
	/**
	 * 法定休日略称を取得する。<br>
	 * @return 法定休日略称
	 */
	protected String getLegalHolidayAbbrNaming() {
		return mospParams.getName("LegalAbbreviation") + mospParams.getName("Holiday");
	}
	
	/**
	 * 所定休出略称を取得する。<br>
	 * @return 所定代休略称
	 */
	protected String getWorkOnPrescribedHolidayAbbrNaming() {
		return mospParams.getName("PrescribedAbbreviation") + mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 法定休出略称を取得する。<br>
	 * @return 法定代休略称
	 */
	protected String getWorkOnLegalHolidayAbbrNaming() {
		return mospParams.getName("LegalAbbreviation") + mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 所定休日名称を取得する。<br>
	 * @return 所定休日名称
	 */
	protected String getPrescribedHolidayNaming() {
		return TimeNamingUtility.prescribedHoliday(mospParams);
	}
	
	/**
	 * 法定休日名称を取得する。<br>
	 * @return 法定休日名称
	 */
	protected String getLegalHolidayNaming() {
		return TimeNamingUtility.legalHoliday(mospParams);
	}
	
	/**
	 * 所定休日出勤名称を取得する。<br>
	 * @return 所定休日出勤名称
	 */
	protected String getWorkOnPrescribedHolidayNaming() {
		return mospParams.getName("Prescribed") + mospParams.getName("Holiday") + mospParams.getName("GoingWork");
	}
	
	/**
	 * 法定休日出勤名称を取得する。<br>
	 * @return 法定休日出勤名称
	 */
	protected String getWorkOnLegalHolidayNaming() {
		return mospParams.getName("Legal") + mospParams.getName("Holiday") + mospParams.getName("GoingWork");
	}
	
}
