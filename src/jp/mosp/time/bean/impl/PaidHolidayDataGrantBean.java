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
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;

/**
 * 有給休暇データ生成クラス。
 */
public class PaidHolidayDataGrantBean extends TimeApplicationBean implements PaidHolidayDataGrantBeanInterface {
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayDataRefer;
	
	/**
	 * 有給休暇データ登録クラス。
	 */
	protected PaidHolidayDataRegistBeanInterface			paidHolidayDataRegist;
	
	/**
	 * 有給休暇初年度付与参照クラス。
	 */
	protected PaidHolidayFirstYearReferenceBeanInterface	paidHolidayFirstYearRefer;
	
	/**
	 * 有給休暇自動付与(基準日)参照クラス。
	 */
	protected PaidHolidayPointDateReferenceBeanInterface	paidHolidayPointDateRefer;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface				entranceRefer;
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public PaidHolidayDataGrantBean() {
		super();
	}
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection データベースコネクション
	 */
	public PaidHolidayDataGrantBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		paidHolidayDataRefer = (PaidHolidayDataReferenceBeanInterface)createBean(PaidHolidayDataReferenceBeanInterface.class);
		paidHolidayDataRegist = (PaidHolidayDataRegistBeanInterface)createBean(PaidHolidayDataRegistBeanInterface.class);
		paidHolidayFirstYearRefer = (PaidHolidayFirstYearReferenceBeanInterface)createBean(PaidHolidayFirstYearReferenceBeanInterface.class);
		paidHolidayPointDateRefer = (PaidHolidayPointDateReferenceBeanInterface)createBean(PaidHolidayPointDateReferenceBeanInterface.class);
		entranceRefer = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
	}
	
	@Override
	public void grant(String personalId, Date targetDate) throws MospException {
		grant(create(personalId, targetDate));
	}
	
	/**
	 * 有給休暇データ付与を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void grant(PaidHolidayDataDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataRefer.findForKey(dto.getPersonalId(),
				dto.getActivateDate(), dto.getAcquisitionDate());
		if (paidHolidayDataDto != null) {
			// 削除
			paidHolidayDataRegist.delete(paidHolidayDataDto);
		}
		// 新規登録
		paidHolidayDataRegist.insert(dto);
	}
	
	/**
	 * 有給休暇データを生成する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayDataDtoInterface create(String personalId, Date targetDate) throws MospException {
		return create(personalId, targetDate, true);
	}
	
	@Override
	public PaidHolidayDataDtoInterface create(String personalId, Date targetDate, boolean accomplish)
			throws MospException {
		return create(personalId, getGrantTimes(personalId, targetDate), accomplish);
	}
	
	/**
	 * 有給休暇データを生成する。
	 * @param personalId 個人ID
	 * @param grantTimes 有給休暇付与回数
	 * @param accomplish 達成率基準
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayDataDtoInterface create(String personalId, int grantTimes, boolean accomplish)
			throws MospException {
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			Date grantDate = getGrantDate(personalId, grantTimes);
			PaidHolidayDataDtoInterface dto = paidHolidayDataRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setHoldDay(0);
			dto.setHoldHour(0);
			dto.setGivingDay(0);
			dto.setGivingHour(0);
			dto.setCancelDay(0);
			dto.setCancelHour(0);
			dto.setUseDay(0);
			dto.setUseHour(0);
			dto.setDenominatorDayHour(paidHolidayDto.getTimeAcquisitionLimitTimes());
			dto.setTemporaryFlag(1);
			dto.setActivateDate(grantDate);
			dto.setAcquisitionDate(grantDate);
			dto.setLimitDate(getExpirationDate(personalId, grantDate, grantTimes));
			dto.setHoldDay(getGrantDays(personalId, grantTimes, accomplish));
			dto.setHoldHour(0);
			return dto;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		}
		return null;
	}
	
	@Override
	public int getGrantTimes(String personalId, Date targetDate) throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return 0;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			// 初年度付与日
			Date grantDateOfFirstFiscalYear = getGrantDateOfFirstFiscalYear(personalId);
			if (grantDateOfFirstFiscalYear != null && targetDate.before(grantDateOfFirstFiscalYear)) {
				// 初年度付与日より前の場合
				return 0;
			}
			// 初年度付与日以後の場合
			// 付与回数
			int count = 0;
			Date entranceDate = entranceRefer.getEntranceDate(personalId);
			if (entranceDate == null) {
				return 0;
			}
			int grantYear = DateUtility.getYear(entranceDate);
			int grantMonth = paidHolidayDto.getPointDateMonth();
			int grantDay = paidHolidayDto.getPointDateDay();
			// 付与日
			Date grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
			while (!grantDate.after(entranceDate)) {
				// 付与日が入社日より後でない場合
				grantYear++;
				grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
			}
			if (grantDateOfFirstFiscalYear != null) {
				while (!grantDate.after(grantDateOfFirstFiscalYear)) {
					// 付与日が初年度付与日より後でない場合
					grantYear++;
					grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
				}
				if (!targetDate.before(grantDateOfFirstFiscalYear) && targetDate.before(grantDate)) {
					// 初年度付与日より前でなく且つ次年度付与日より前の場合は初年度とする
					count = 1;
					return 1;
				}
			}
			if (targetDate.before(grantDate)) {
				// 付与日より前の場合
				return 0;
			}
			// 次年度以後の場合
			count = 2;
			// 次年度付与日
			Date grantDateOfNextFiscalYear = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth,
					grantDay);
			while (!targetDate.before(grantDateOfNextFiscalYear)) {
				// 次年度付与日より前でない場合
				grantDate = grantDateOfNextFiscalYear;
				grantDateOfNextFiscalYear = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth,
						grantDay);
				count++;
			}
			return count;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return 0;
		}
		return 0;
	}
	
	@Override
	public Date getGrantDate(String personalId, Date targetDate, int grantTimes) throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return null;
		}
		return getGrantDate(personalId, grantTimes);
	}
	
	/**
	 * 有給休暇付与日を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 有給休暇付与回数
	 * @return 有給休暇付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDate(String personalId, int grantTimes) throws MospException {
		return getGrantDate(personalId, grantTimes, entranceRefer.getEntranceDate(personalId));
	}
	
	@Override
	public Date getGrantDate(String personalId, Date targetDate, int grantTimes, Date entranceDate)
			throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return null;
		}
		return getGrantDate(personalId, grantTimes, entranceDate);
	}
	
	/**
	 * 有給休暇付与日を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 付与回数
	 * @param entranceDate 入社日
	 * @return 有給休暇付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDate(String personalId, int grantTimes, Date entranceDate) throws MospException {
		if (entranceDate == null) {
			return null;
		}
		Date grantDateOfFirstFiscalYear = getGrantDateOfFirstFiscalYear(personalId);
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			} else if (grantTimes == 1) {
				// 1の場合
				return grantDateOfFirstFiscalYear;
			}
			// 1より大きい場合
			int grantMonth = paidHolidayDto.getPointDateMonth();
			int grantDay = paidHolidayDto.getPointDateDay();
			Date grantDate = DateUtility.getDate(DateUtility.getYear(entranceDate), grantMonth, grantDay);
			while (!grantDate.after(entranceDate)) {
				// 付与日が入社日より後でない場合
				grantDate = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth, grantDay);
			}
			if (grantDateOfFirstFiscalYear != null) {
				while (!grantDate.after(grantDateOfFirstFiscalYear)) {
					// 付与日が初年度付与日より後でない場合
					grantDate = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth, grantDay);
				}
			}
			if (grantTimes == 2) {
				// 2の場合
				return grantDate;
			}
			// 2より大きい場合
			return DateUtility.getDate(DateUtility.getYear(grantDate) + grantTimes - 2, grantMonth, grantDay);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		}
		return null;
	}
	
	/**
	 * 有給休暇初年度付与日を取得する。
	 * @param personalId 個人ID
	 * @return 有給休暇初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDateOfFirstFiscalYear(String personalId) throws MospException {
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return null;
		}
		PaidHolidayFirstYearDtoInterface dto = paidHolidayFirstYearRefer.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		if (dto == null) {
			return null;
		}
		if (dto.getGivingAmount() <= 0) {
			// 付与日数が0以下の場合
			return null;
		}
		return addDay(
				DateUtility.addMonth(MonthUtility.getTargetYearMonth(entranceDate, mospParams), dto.getGivingMonth()),
				paidHolidayDto.getPointDateDay() - 1);
	}
	
	/**
	 * 有給休暇期限日を取得する。
	 * @param personalId 個人ID
	 * @param grantDate 有給休暇付与日
	 * @param grantTimes 有給休暇付与回数
	 * @return 有給休暇期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getExpirationDate(String personalId, Date grantDate, int grantTimes) throws MospException {
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			} else if (grantTimes == 1) {
				// 1の場合
				return getExpirationDateOfFirstFiscalYear(personalId, grantDate);
			}
			// 1より大きい場合
			int addYear = 1;
			if (paidHolidayDto.getMaxCarryOverYear() == 0) {
				// 有休繰越が有効の場合は2年とする
				addYear = 2;
			}
			return addDay(DateUtility.addYear(grantDate, addYear), -1);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		}
		return null;
	}
	
	/**
	 * 有給休暇初年度期限日を取得する。
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @return 有給休暇初年度期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getExpirationDateOfFirstFiscalYear(String personalId, Date grantDate) throws MospException {
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return null;
		}
		return getExpirationDateOfFirstFiscalYear(
				paidHolidayFirstYearRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
						paidHolidayDto.getActivateDate(), DateUtility.getMonth(entranceDate)), grantDate);
	}
	
	/**
	 * 有給休暇初年度期限日を取得する。
	 * @param dto 有給休暇初年度DTO
	 * @param grantDate 付与日
	 * @return 有給休暇初年度期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getExpirationDateOfFirstFiscalYear(PaidHolidayFirstYearDtoInterface dto, Date grantDate)
			throws MospException {
		if (dto == null) {
			return null;
		}
		return addDay(DateUtility.addMonth(grantDate, dto.getGivingLimit()), -1);
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 付与回数
	 * @param accomplish 出勤率基準
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDays(String personalId, int grantTimes, boolean accomplish) throws MospException {
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			if (!accomplish) {
				// 未達成の場合
				return 0;
			}
			// 達成の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return 0;
			} else if (grantTimes == 1) {
				// 1の場合
				return getGrantDaysOfFirstFiscalYear(personalId, accomplish);
			}
			// 1より大きい場合
			PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDateRefer.findForKey(
					paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), grantTimes);
			if (paidHolidayPointDateDto == null) {
				// 登録情報最大まで経過後の場合
				return paidHolidayDto.getGeneralPointAmount();
			}
			// 登録情報最大まで経過していない場合
			return paidHolidayPointDateDto.getPointDateAmount();
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return 0;
		}
		return 0;
	}
	
	/**
	 * 有給休暇初年度付与日数を取得する。
	 * @param personalId 個人ID
	 * @param accomplish 出勤率基準
	 * @return 有給休暇初年度付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysOfFirstFiscalYear(String personalId, boolean accomplish) throws MospException {
		if (!accomplish) {
			// 未達成の場合
			return 0;
		}
		// 達成の場合
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return 0;
		}
		PaidHolidayFirstYearDtoInterface dto = paidHolidayFirstYearRefer.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		if (dto == null) {
			return 0;
		}
		return dto.getGivingAmount();
	}
	
}
