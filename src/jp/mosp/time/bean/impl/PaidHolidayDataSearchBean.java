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

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.impl.PaidHolidayDataGrantListDto;

/**
 * 有給休暇データ検索クラス。
 */
public class PaidHolidayDataSearchBean extends PlatformBean implements PaidHolidayDataSearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	protected HumanSearchBeanInterface						humanSearch;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface				entranceReference;
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayDataReference;
	
	/**
	 * 設定適用参照クラス。
	 */
	protected ApplicationReferenceBeanInterface				applicationReference;
	
	/**
	 * カレンダ管理参照クラス。
	 */
	protected ScheduleReferenceBeanInterface				scheduleReference;
	
	/**
	 * カレンダ日参照クラス。
	 */
	protected ScheduleDateReferenceBeanInterface			scheduleDateReference;
	
	/**
	 * 有給休暇設定参照クラス。
	 */
	protected PaidHolidayReferenceBeanInterface				paidHolidayReference;
	
	/**
	 * 有給休暇データ付与クラス。
	 */
	protected PaidHolidayDataGrantBeanInterface				paidHolidayDataGrant;
	
	/**
	 * 勤怠トランザクション参照クラス。
	 */
	protected AttendanceTransactionReferenceBeanInterface	attendanceTransactionReference;
	
	private Date											activateDate;
	private Date											entranceFromDate;
	private Date											entranceToDate;
	private String											employeeCode;
	private String											employeeName;
	private String											workPlaceCode;
	private String											employmentCode;
	private String											sectionCode;
	private String											positionCode;
	private String											paidHolidayCode;
	private String											grant;
	private boolean											calcAttendanceRate;
	private Set<String>										personalIdSet;
	
	/**
	 * 未付与
	 */
	public static final String								NOT_GRANTED	= "0";
	
	/**
	 * 付与済
	 */
	public static final String								GRANTED		= "1";
	
	
	/**
	 * コンストラクタ。
	 */
	public PaidHolidayDataSearchBean() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param mospParams MosPパラメータクラス。
	 * @param connection DBコネクション。
	 */
	public PaidHolidayDataSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		humanSearch = (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		paidHolidayDataReference = (PaidHolidayDataReferenceBeanInterface)createBean(PaidHolidayDataReferenceBeanInterface.class);
		applicationReference = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		scheduleReference = (ScheduleReferenceBeanInterface)createBean(ScheduleReferenceBeanInterface.class);
		scheduleDateReference = (ScheduleDateReferenceBeanInterface)createBean(ScheduleDateReferenceBeanInterface.class);
		paidHolidayReference = (PaidHolidayReferenceBeanInterface)createBean(PaidHolidayReferenceBeanInterface.class);
		paidHolidayDataGrant = (PaidHolidayDataGrantBeanInterface)createBean(PaidHolidayDataGrantBeanInterface.class);
		attendanceTransactionReference = (AttendanceTransactionReferenceBeanInterface)createBean(AttendanceTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public List<PaidHolidayDataGrantListDtoInterface> getSearchList() throws MospException {
		List<PaidHolidayDataGrantListDtoInterface> list = new ArrayList<PaidHolidayDataGrantListDtoInterface>();
		List<HumanDtoInterface> humanList = getHumanList();
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID
			if (!searchPersonalId(humanDto)) {
				continue;
			}
			// 入社日
			if (!searchEntranceDate(humanDto)) {
				continue;
			}
			ApplicationDtoInterface applicationDto = applicationReference.findForPerson(humanDto.getPersonalId(),
					activateDate);
			if (applicationDto == null) {
				continue;
			}
			PaidHolidayDtoInterface paidHolidayDto = paidHolidayReference.getPaidHolidayInfo(
					applicationDto.getPaidHolidayCode(), activateDate);
			if (paidHolidayDto == null) {
				continue;
			}
			// 有給休暇設定
			if (!searchPaidHoliday(paidHolidayDto)) {
				continue;
			}
			int grantTimes = paidHolidayDataGrant.getGrantTimes(humanDto.getPersonalId(), activateDate);
			Date grantDate = paidHolidayDataGrant.getGrantDate(humanDto.getPersonalId(), activateDate, grantTimes);
			PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataReference.findForKey(
					humanDto.getPersonalId(), grantDate, grantDate);
			// 付与状態
			if (!searchGrant(paidHolidayDataDto)) {
				continue;
			}
			Date startDate = getStartDate(humanDto, grantTimes, grantDate);
			Date endDate = getEndDate(startDate, grantTimes);
			PaidHolidayDataGrantListDtoInterface dto = new PaidHolidayDataGrantListDto();
			setAttendanceRate(dto, humanDto, grantDate, startDate, endDate);
			boolean isAccomplished = isAccomplished(dto, paidHolidayDto);
			setDto(dto, humanDto, paidHolidayDataDto, grantDate, startDate, endDate, isAccomplished);
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 人事マスタリストを取得する。<br>
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanList() throws MospException {
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
		return humanSearch.search();
	}
	
	/**
	 * 個人ID判断。
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPersonalId(HumanDtoInterface dto) {
		return searchPersonalId(dto.getPersonalId());
	}
	
	/**
	 * 個人ID判断。
	 * @param personalId 個人ID
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPersonalId(String personalId) {
		if (personalIdSet == null) {
			return true;
		}
		return personalIdSet.contains(personalId);
	}
	
	/**
	 * 入社日判断。
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 * @throws MospException 例外発生時
	 */
	protected boolean searchEntranceDate(HumanDtoInterface dto) throws MospException {
		return searchEntranceDate(dto.getPersonalId());
	}
	
	/**
	 * 入社日判断。
	 * @param personalId 個人ID
	 * @return 検索条件に一致する場合true、そうでない場合false
	 * @throws MospException 例外発生時
	 */
	protected boolean searchEntranceDate(String personalId) throws MospException {
		// 入社日自がない場合
		if (entranceFromDate == null) {
			return true;
		}
		// 入社日取得
		Date date = entranceReference.getEntranceDate(personalId);
		if (date == null) {
			return false;
		}
		// 検索期間に含まれているか確認
		return DateUtility.isTermContain(date, entranceFromDate, entranceToDate);
	}
	
	/**
	 * 有給休暇設定判断。<br>
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPaidHoliday(PaidHolidayDtoInterface dto) {
		if (paidHolidayCode.isEmpty()) {
			return true;
		}
		return paidHolidayCode.equals(dto.getPaidHolidayCode());
	}
	
	/**
	 * 付与状態判断。
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchGrant(PaidHolidayDataDtoInterface dto) {
		if (grant.isEmpty()) {
			return true;
		}
		if (dto == null) {
			// 未付与の場合
			return NOT_GRANTED.equals(grant);
		}
		// 付与済の場合
		return GRANTED.equals(grant);
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 対象DTO
	 * @param humanDto 人事マスタDTO
	 * @param paidHolidayDataDto 有給休暇データDTO
	 * @param grantDate 付与日付
	 * @param firstDate 開始日付
	 * @param lastDate 終了日付
	 * @param isAccomplished 出勤率基準
	 */
	protected void setDto(PaidHolidayDataGrantListDtoInterface dto, HumanDtoInterface humanDto,
			PaidHolidayDataDtoInterface paidHolidayDataDto, Date grantDate, Date firstDate, Date lastDate,
			boolean isAccomplished) {
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setGrantDate(grantDate);
		dto.setFirstDate(firstDate);
		dto.setLastDate(lastDate);
		dto.setAccomplish(mospParams.getName("Ram", "Accomplish"));
		if (isAccomplished) {
			// 達成の場合
			dto.setAccomplish(mospParams.getName("Accomplish"));
		}
		if (grantDate == null) {
			dto.setAccomplish(mospParams.getName("Hyphen"));
		}
		if (dto.getAttendanceRate() == null) {
			dto.setAccomplish(mospParams.getName("Hyphen"));
		}
		dto.setGrant(mospParams.getName("Ram", "Giving"));
		dto.setActivateDate(null);
		dto.setGrantDays(null);
		if (paidHolidayDataDto != null) {
			// 付与済の場合
			dto.setGrant(mospParams.getName("Giving", "Finish"));
			dto.setActivateDate(paidHolidayDataDto.getActivateDate());
			dto.setGrantDays(paidHolidayDataDto.getHoldDay());
		}
	}
	
	/**
	 * 出勤率を達成しているかどうかを確認する。<br>
	 * @param dto 対象DTO
	 * @param paidHolidayDto 有給休暇管理DTO
	 * @return 達成している場合true、そうでない場合false
	 */
	protected boolean isAccomplished(PaidHolidayDataGrantListDtoInterface dto, PaidHolidayDtoInterface paidHolidayDto) {
		if (paidHolidayDto.getWorkRatio() <= 0) {
			// 有給休暇設定の出勤率が0以下の場合
			return true;
		}
		if (dto.getAttendanceRate() == null) {
			return false;
		}
		// 有給休暇設定の出勤率が0以下でない場合
		return Double.compare(dto.getAttendanceRate() * 100, paidHolidayDto.getWorkRatio()) >= 0;
	}
	
	/**
	 * 開始日を取得する。<br>
	 * @param dto 対象DTO
	 * @param grantTimes 有給休暇付与回数
	 * @param grantDate 付与日
	 * @return 開始日
	 * @throws MospException 例外発生時
	 */
	protected Date getStartDate(HumanDtoInterface dto, int grantTimes, Date grantDate) throws MospException {
		return getStartDate(dto.getPersonalId(), grantTimes, grantDate);
	}
	
	/**
	 * 開始日を取得する。<br>
	 * @param personalId 個人ID
	 * @param grantTimes 有給休暇付与回数
	 * @param grantDate 付与日
	 * @return 開始日
	 * @throws MospException 例外発生時
	 */
	protected Date getStartDate(String personalId, int grantTimes, Date grantDate) throws MospException {
		Date entranceDate = entranceReference.getEntranceDate(personalId);
		if (grantTimes <= 0) {
			// 0以下の場合
			return null;
		} else if (grantTimes == 1) {
			// 1の場合は入社日とする
			return entranceDate;
		}
		// 1より大きい場合
		Date startDate = paidHolidayDataGrant.getGrantDate(personalId, activateDate, grantTimes - 1, entranceDate);
		if (grantTimes >= 3) {
			// 3以上の場合
			return startDate;
		}
		// 3より小さい場合
		if (startDate != null) {
			return startDate;
		}
		Date targetDate = DateUtility.addYear(grantDate, -1);
		if (targetDate.after(entranceDate)) {
			// 入社日より後の場合
			return targetDate;
		}
		// 入社日以前の場合
		return entranceDate;
	}
	
	/**
	 * 終了日を取得する。
	 * @param startDate 開始日
	 * @param grantTimes 有給休暇付与回数
	 * @return 終了日
	 */
	protected Date getEndDate(Date startDate, int grantTimes) {
		if (startDate == null) {
			return null;
		}
		if (grantTimes <= 0) {
			// 0以下の場合
			return null;
		} else if (grantTimes == 1) {
			// 1の場合は6ヶ月後の前日とする
			return addDay(DateUtility.addMonth(startDate, 6), -1);
		}
		// 1より大きい場合は1年後の前日とする
		return addDay(DateUtility.addYear(startDate, 1), -1);
	}
	
	/**
	 * 出勤率を設定する。
	 * @param dto 対象DTO
	 * @param humanDto 人事マスタDTO
	 * @param grantDate 付与日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendanceRate(PaidHolidayDataGrantListDtoInterface dto, HumanDtoInterface humanDto,
			Date grantDate, Date startDate, Date endDate) throws MospException {
		setAttendanceRate(dto, humanDto.getPersonalId(), grantDate, startDate, endDate);
	}
	
	/**
	 * 出勤率を設定する。
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendanceRate(PaidHolidayDataGrantListDtoInterface dto, String personalId, Date grantDate,
			Date startDate, Date endDate) throws MospException {
		dto.setWorkDays(0);
		dto.setTotalWorkDays(0);
		dto.setAttendanceRate(null);
		if (startDate == null) {
			return;
		}
		if (!calcAttendanceRate) {
			return;
		}
		int attendanceDays = 0;
		int totalWorkDays = 0;
		Date firstDate = startDate;
		Date lastDate = addDay(grantDate, -1);
		Set<Long> set = attendanceTransactionReference.findForMilliseconds(personalId, firstDate, lastDate);
		if (getNumberOfDays(firstDate, lastDate) != set.size()) {
			// 日数がセットの数と一致しない場合
			int milliseconds = 1000 * 60 * TimeConst.CODE_DEFINITION_HOUR * TimeConst.TIME_DAY_ALL_HOUR;
			long firstTime = firstDate.getTime();
			long lastTime = lastDate.getTime();
			for (long i = firstTime; i <= lastTime; i += milliseconds) {
				if (set.contains(i)) {
					continue;
				}
				Date date = new Date(i);
				ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, date);
				if (applicationDto == null) {
					continue;
				}
				ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
						date);
				if (scheduleDto == null) {
					continue;
				}
				ScheduleDateDtoInterface scheduleDateDto = scheduleDateReference.getScheduleDateInfo(
						scheduleDto.getScheduleCode(), scheduleDto.getActivateDate(), date);
				if (scheduleDateDto == null) {
					continue;
				}
				if (scheduleDateDto.getWorkTypeCode().isEmpty()
						|| TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(scheduleDateDto.getWorkTypeCode())) {
					// 勤務形態が未設定・法定休日・所定休日の場合
					continue;
				}
				totalWorkDays++;
			}
		}
		AttendanceTransactionDtoInterface attendanceTransactionDto = attendanceTransactionReference.sum(personalId,
				firstDate, lastDate);
		if (attendanceTransactionDto != null) {
			attendanceDays += attendanceTransactionDto.getNumerator();
			totalWorkDays += attendanceTransactionDto.getDenominator();
		}
		// 付与日から算定期間末日までの日数
		int days = getNumberOfDays(grantDate, endDate);
		attendanceDays += days;
		totalWorkDays += days;
		dto.setWorkDays(attendanceDays);
		dto.setTotalWorkDays(totalWorkDays);
		dto.setAttendanceRate(getAttendanceRate(attendanceDays, totalWorkDays));
	}
	
	/**
	 * 出勤率を取得する。
	 * @param workDays 労働日数
	 * @param totalWorkDays 全労働日数
	 * @return 出勤率
	 */
	protected double getAttendanceRate(int workDays, int totalWorkDays) {
		if (totalWorkDays <= 0) {
			// 全労働日数が0以下の場合
			return 0;
		}
		BigDecimal dividend = new BigDecimal(workDays);
		BigDecimal divisor = new BigDecimal(totalWorkDays);
		BigDecimal quotient = dividend.divide(divisor, 3, BigDecimal.ROUND_FLOOR);
		return quotient.doubleValue();
	}
	
	/**
	 * 日数を取得する。<br>
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 日数
	 */
	protected int getNumberOfDays(Date firstDate, Date lastDate) {
		long difference = lastDate.getTime() - firstDate.getTime();
		if (difference < 0) {
			// 0より小さい場合
			return 0;
		}
		long quotient = difference / (1000 * 60 * TimeConst.CODE_DEFINITION_HOUR * TimeConst.TIME_DAY_ALL_HOUR);
		return (int)quotient + 1;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEntranceFromDate(Date entranceFromDate) {
		this.entranceFromDate = getDateClone(entranceFromDate);
	}
	
	@Override
	public void setEntranceToDate(Date entranceToDate) {
		this.entranceToDate = getDateClone(entranceToDate);
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
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setGrant(String grant) {
		this.grant = grant;
	}
	
	@Override
	public void setCalcAttendanceRate(boolean calcAttendanceRate) {
		this.calcAttendanceRate = calcAttendanceRate;
	}
	
	@Override
	public void setPersonalIdSet(Set<String> personalIdSet) {
		this.personalIdSet = personalIdSet;
	}
	
}
