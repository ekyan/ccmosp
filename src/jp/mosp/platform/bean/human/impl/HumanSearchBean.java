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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.HumanSearchDaoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanListDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanListDto;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 人事マスタ検索クラス。
 */
public class HumanSearchBean extends PlatformHumanBean implements HumanSearchBeanInterface {
	
	/**
	 * フリーワード区切文字。
	 */
	public static final String					FREE_WORD_SEPARATOR		= " ";
	
	/**
	 * 人事情報検索DAO。
	 */
	HumanSearchDaoInterface						dao;
	
	/**
	 * 名称区分情報参照クラス。
	 */
	NamingReferenceBeanInterface				namingReference;
	
	/**
	 * 人事兼務情報参照クラス。
	 */
	ConcurrentReferenceBeanInterface			concurrentReference;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	EntranceReferenceBeanInterface				entranceReference;
	
	/**
	 * 人事退社情報参照クラス。
	 */
	RetirementReferenceBeanInterface			retirementReference;
	
	/**
	 * 人事休職情報参照クラス。
	 */
	SuspensionReferenceBeanInterface			suspensionReference;
	
	/**
	 * 人事汎用通常情報参照クラス。
	 */
	HumanNormalReferenceBeanInterface			humanNormalReference;
	
	/**
	 * 人事汎用履歴参照クラス。
	 */
	HumanHistoryReferenceBeanInterface			humanHistoryReference;
	
	/**
	 * 人事汎用一覧情報参照クラス。
	 */
	HumanArrayReferenceBeanInterface			humanArrayReference;
	
	/**
	 * 人事汎用管理機能クラス。
	 */
	HumanGeneralBeanInterface					humanGeneral;
	
	/**
	 * 人事汎用バイナリ通常情報参照クラス。
	 */
	HumanBinaryNormalReferenceBeanInterface		humanBinaryNormal;
	
	/**
	 * 人事汎用バイナリ履歴情報参照クラス。
	 */
	HumanBinaryHistoryReferenceBeanInterface	humanBinaryHistory;
	
	/**
	 * 人事汎用バイナリ一覧情報参照クラス。
	 */
	HumanBinaryArrayReferenceBeanInterface		humanBinaryArray;
	
	/**
	 * 職位マスタ参照。
	 */
	PositionReferenceBeanInterface				position;
	
	/**
	 * 雇用契約マスタ参照。
	 */
	EmploymentContractReferenceBeanInterface	employmentContract;
	
	/**
	 * 勤務地マスタ参照。
	 */
	WorkPlaceReferenceBeanInterface				workPlace;
	
	/**
	 * 勤務地マスタ参照。
	 */
	SectionReferenceBeanInterface				section;
	
	/**
	 * 対象日。
	 */
	protected Date								targetDate;
	
	/**
	 * 社員コード。
	 */
	protected String							employeeCode;
	
	/**
	 * 社員コード(from)。
	 */
	protected String							fromEmployeeCode;
	
	/**
	 * 社員コード(to)。
	 */
	protected String							toEmployeeCode;
	
	/**
	 * 社員名。
	 */
	protected String							employeeName;
	
	/**
	 * 姓。
	 */
	protected String							lastName;
	
	/**
	 * 名。
	 */
	protected String							firstName;
	
	/**
	 * 姓（カナ）。
	 */
	protected String							lastKana;
	
	/**
	 * 名（カナ）。
	 */
	protected String							firstKana;
	
	/**
	 * 勤務地コード。
	 */
	protected String							workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	protected String							employmentContractCode;
	
	/**
	 * 所属コード。
	 */
	protected String							sectionCode;
	
	/**
	 * 下位所属要否。
	 */
	protected Boolean							needLowerSection;
	
	/**
	 * 職位コード。
	 */
	protected String							positionCode;
	
	/**
	 * 兼務要否。
	 */
	protected Boolean							needConcurrent;
	
	/**
	 * 情報区分。
	 */
	protected String							informationType;
	
	/**
	 * 検索ワード。
	 */
	protected String							searchWord;
	
	/**
	 * 休退職区分。
	 */
	protected String							stateType;
	
	/**
	 * 条件区分（社員コード）。
	 */
	protected String							employeeCodeType;
	
	/**
	 * 条件区分（姓）。
	 */
	protected String							lastNameType;
	
	/**
	 * 条件区分（名）。
	 */
	protected String							firstNameType;
	
	/**
	 * 条件区分（姓（カナ））。
	 */
	protected String							lastKanaType;
	
	/**
	 * 条件区分（名（カナ））。
	 */
	protected String							firstKanaType;
	
	/**
	 * 不要個人ID。
	 */
	protected String							unnecessaryPersonalId;
	
	/**
	 * 承認ロール要否。
	 */
	protected Boolean							needApproverRole;
	
	/**
	 * 操作区分。
	 */
	protected String							operationType;
	
	/**
	 * 期間開始日。
	 */
	protected Date								startDate;
	
	/**
	 * 期間終了日。
	 */
	protected Date								endDate;
	
	/**
	 * 人事汎用管理表示区分(社員検索)。<br>
	 */
	public static final String					KEY_VIEW_HUMAN_SEARCH	= "HumanSearch";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanSearchBean() {
		super();
	}
	
	/**
	 * {@link PlatformBean#PlatformBean(MospParams, Connection)}を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	public HumanSearchBean(MospParams mospParams, Connection connection) {
		super(mospParams, connection);
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// 人事情報検索DAO取得
		dao = (HumanSearchDaoInterface)createDao(HumanSearchDaoInterface.class);
		concurrentReference = (ConcurrentReferenceBeanInterface)createBean(ConcurrentReferenceBeanInterface.class);
		entranceReference = (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
		retirementReference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		suspensionReference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		humanNormalReference = (HumanNormalReferenceBeanInterface)createBean(HumanNormalReferenceBeanInterface.class);
		humanHistoryReference = (HumanHistoryReferenceBeanInterface)createBean(HumanHistoryReferenceBeanInterface.class);
		humanArrayReference = (HumanArrayReferenceBeanInterface)createBean(HumanArrayReferenceBeanInterface.class);
		humanGeneral = (HumanGeneralBeanInterface)createBean(HumanGeneralBeanInterface.class);
		humanBinaryNormal = (HumanBinaryNormalReferenceBeanInterface)createBean(HumanBinaryNormalReferenceBeanInterface.class);
		humanBinaryHistory = (HumanBinaryHistoryReferenceBeanInterface)createBean(HumanBinaryHistoryReferenceBeanInterface.class);
		humanBinaryArray = (HumanBinaryArrayReferenceBeanInterface)createBean(HumanBinaryArrayReferenceBeanInterface.class);
		position = (PositionReferenceBeanInterface)createBean(PositionReferenceBeanInterface.class);
		section = (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
		employmentContract = (EmploymentContractReferenceBeanInterface)createBean(EmploymentContractReferenceBeanInterface.class);
		workPlace = (WorkPlaceReferenceBeanInterface)createBean(WorkPlaceReferenceBeanInterface.class);
		namingReference = (NamingReferenceBeanInterface)createBean(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public List<HumanDtoInterface> search() throws MospException {
		// パラメータ準備
		Map<String, Object> param = dao.getParamsMap();
		param.put(HumanSearchDaoInterface.SEARCH_TARGET_DATE, targetDate);
		param.put(HumanSearchDaoInterface.SEARCH_EMPLOYEE_CODE, employeeCode);
		param.put(HumanSearchDaoInterface.SEARCH_FROM_EMPLOYEE_CODE, fromEmployeeCode);
		param.put(HumanSearchDaoInterface.SEARCH_TO_EMPLOYEE_CODE, toEmployeeCode);
		param.put(HumanSearchDaoInterface.SEARCH_EMPLOYEE_CODE_TYPE, employeeCodeType);
		param.put(HumanSearchDaoInterface.SEARCH_EMPLOYEE_NAME, employeeName);
		param.put("lastName", lastName);
		param.put("lastNameType", lastNameType);
		param.put("workPlaceCode", workPlaceCode);
		param.put(HumanSearchDaoInterface.SEARCH_SECTION_CODE, sectionCode);
		param.put(HumanSearchDaoInterface.SEARCH_NEED_LOWER_SECTION, needLowerSection);
		param.put(HumanSearchDaoInterface.SEARCH_POSITION_CODE, positionCode);
		param.put(HumanSearchDaoInterface.SEARCH_NEED_CONCURRENT, needConcurrent);
		param.put("employmentContractCode", employmentContractCode);
		param.put("firstName", firstName);
		param.put("firstNameType", firstNameType);
		param.put("lastKana", lastKana);
		param.put("lastKanaType", lastKanaType);
		param.put("firstKana", firstKana);
		param.put("firstKanaType", firstKanaType);
		param.put(HumanSearchDaoInterface.SEARCH_EMPLOYEE_STATE, stateType);
		// 不要個人IDパラメータ設定
		param.put(HumanSearchDaoInterface.SEARCH_UNNECESSARY_PERSONAL_ID, unnecessaryPersonalId);
		// 承認ロール要否条件パラメータ設定
		param.put(HumanSearchDaoInterface.SEARCH_NEED_APPROVER_ROLE, needApproverRole);
		// 範囲条件パラメータ設定
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_WORK_PLACE, getRangeWorkPlace(operationType, targetDate));
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYMENT_CONTRACT,
				getRangeEmploymentContract(operationType, targetDate));
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_SECTION, getRangeSection(operationType, targetDate));
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_POSITION, getRangePosition(operationType, targetDate));
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYEE, getRangeEmployee(operationType, targetDate));
		// 範囲条件(兼務)パラメータ設定
		param.put(HumanSearchDaoInterface.SEARCH_RANGE_CONCURRENT, getRangeConcurrent(operationType, targetDate));
		// 期間設定
		param.put(HumanSearchDaoInterface.SEARCH_START_DATE, startDate);
		param.put(HumanSearchDaoInterface.SEARCH_END_DATE, endDate);
		// 検索
		List<HumanDtoInterface> humanList = dao.findForSearch(param);
		// フリーワード検索
		return searchForFreeWord(humanList);
	}
	
	/**
	 * 操作範囲(兼務)を取得する。<br>
	 * 各メニューに設定された範囲情報を{@link MospParams}から取得する。<br>
	 * <br>
	 * 兼務情報を取得し、兼務している所属及び職位の配列を返す。<br>
	 * 但し、操作範囲(所属)及び操作範囲(職位)に範囲設定(自身)がなされていない場合、
	 * 空の配列を返す。<br>
	 * <br>
	 * 配列の内容は、次の通り。<br>
	 * ・1次元：兼務の件数
	 * ・2次元：所属と職位の区分(0行目：所属、1行目：職位)
	 * ・3次元：兼務設定されている所属コード或いは職位コード<br>
	 * <br>
	 * @param operationType 操作区分
	 * @param targetDate    対象日
	 * @return 操作範囲(兼務)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][][] getRangeConcurrent(String operationType, Date targetDate) throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return new String[0][][];
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return new String[0][][];
		}
		// 操作範囲(所属)取得及び確認
		String rangeSection = range.getSection();
		if (rangeSection == null || rangeSection.isEmpty()
				|| asList(rangeSection, MospConst.APP_PROPERTY_SEPARATOR).contains(MospConst.RANGE_MYSELF) == false) {
			return new String[0][][];
		}
		// 操作範囲(職位)取得及び確認
		String rangePosition = range.getPosition();
		if (rangePosition == null || rangePosition.isEmpty()
				|| asList(rangePosition, MospConst.APP_PROPERTY_SEPARATOR).contains(MospConst.RANGE_MYSELF) == false) {
			return new String[0][][];
		}
		// 人事兼務情報参照クラス取得
		ConcurrentReferenceBeanInterface concurrentRefer = (ConcurrentReferenceBeanInterface)createBean(ConcurrentReferenceBeanInterface.class);
		// 対象日時点で終了していない人事兼務情報のリストを取得
		List<ConcurrentDtoInterface> concurrentList = concurrentRefer.getConcurrentList(mospParams.getUser()
			.getPersonalId(), targetDate);
		// 操作範囲(兼務)の準備
		String[][][] rangeConcurrent = new String[concurrentList.size()][][];
		// 操作範囲(兼務)の作成
		for (int i = 0; i < rangeConcurrent.length; i++) {
			// 人事兼務情報の取得
			ConcurrentDtoInterface concurrent = concurrentList.get(i);
			// 操作範囲配列作成
			String[][] concurrentArray = { { concurrent.getSectionCode() }, { concurrent.getPositionCode() } };
			// 操作範囲(兼務)に操作範囲配列を設定
			rangeConcurrent[i] = concurrentArray;
		}
		return rangeConcurrent;
	}
	
	/**
	 * フリーワードによる検索を行う。<br>
	 * 対象人事情報リストのうち、フリーワードの条件に合致する人事情報のみを取得する。<br>
	 * @param humanList 対象人事情報リスト
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWord(List<HumanDtoInterface> humanList) throws MospException {
		// 情報区分及び検索ワード確認
		if (informationType == null || informationType.isEmpty() || searchWord == null || searchWord.isEmpty()) {
			// フリーワード検索不要
			return humanList;
		}
		// 検索ワード配列準備
		String[] arySearchWord = MospUtility.split(searchWord, FREE_WORD_SEPARATOR);
		// 人事汎用管理区分型取得
		ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(informationType);
		// 情報区分毎に処理
		if (informationType.equals(PlatformConst.FREE_WORD_HUMAN)) {
			// 個人基本情報でフリーワード検索
			return searchForFreeWordHuman(humanList, arySearchWord);
		} else if (informationType.equals(PlatformConst.FREE_WORD_SUSPEND)) {
			// 休職情報でフリーワード検索
			return searchForFreeWordSuspensionInfo(humanList, arySearchWord);
		} else if (informationType.equals(PlatformConst.FREE_WORD_RETIRE)) {
			// 退職情報でフリーワード検索
			return searchForFreeWordRetirementInfo(humanList, arySearchWord);
		} else if (informationType.equals(PlatformConst.FREE_WORD_CONCUR)) {
			// 兼務情報でフリーワード検索
			return searchForFreeWordConcurrentInfo(humanList, arySearchWord);
		} else if (viewConfig != null && viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
			// 人事汎用通常情報でフリーワード検索
			return searchForFreeWordHumanNormalInfo(informationType, humanList, arySearchWord);
		} else if (viewConfig != null
				&& viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
			// 人事汎用履歴情報でフリーワード検索
			return searchForFreeWordHumanHistoryInfo(informationType, humanList, arySearchWord);
		} else if (viewConfig != null && viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
			// 人事汎用一覧情報でフリーワード検索
			return searchForFreeWordHumanArrayInfo(informationType, humanList, arySearchWord, targetDate);
		} else if (viewConfig != null
				&& viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL)) {
			// 人事バイナリ汎用通常情報でフリーワード検索
			return searchForFreeWordHumanBinaryNormalInfo(informationType, humanList, arySearchWord);
		} else if (viewConfig != null
				&& viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY)) {
			// 人事バイナリ汎用履歴情報でフリーワード検索
			return searchForFreeWordHumanBinaryHistoryInfo(informationType, humanList, arySearchWord);
		} else if (viewConfig != null
				&& viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY)) {
			// 人事バイナリ汎用一覧情報でフリーワード検索
			return searchForFreeWordHumanBinaryArrayInfo(informationType, humanList, arySearchWord);
		}
		// 人事汎用情報でフリーワード検索
		return humanList;
	}
	
	/**
	 * 個人基本情報に対して、フリーワードによる検索を行う。<br>
	 * @param humanList     対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHuman(List<HumanDtoInterface> humanList, String[] arySearchWord)
			throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 役職要否確認
		boolean isPost = false;
		isPost = mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST);
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 対象社員有効日取得
			Date targetDate = humanDto.getActivateDate();
			// フリーワード毎に処理
			for (String searchWord : arySearchWord) {
				// フリーワードがない場合
				if (searchWord.isEmpty()) {
					break;
				}
				// 勤務地コード取得
				String resultWorkPlaceCode = humanDto.getWorkPlaceCode();
				// 勤務地が登録されている場合(必須ではない)
				if (resultWorkPlaceCode.isEmpty() == false) {
					// 勤務地情報取得
					WorkPlaceDtoInterface resultWorkPlaceDto = workPlace.getWorkPlaceInfo(resultWorkPlaceCode,
							targetDate);
					// 勤務地名で検索結果がある場合
					if (isBroadMatch(searchWord, resultWorkPlaceDto.getWorkPlaceName())) {
						resultList.add(humanDto);
						break;
					}
					// 勤務地略称で検索結果がある場合
					if (isBroadMatch(searchWord, resultWorkPlaceDto.getWorkPlaceAbbr())) {
						resultList.add(humanDto);
						break;
					}
					// 勤務地カナで検索結果がある場合
					if (isBroadMatch(searchWord, resultWorkPlaceDto.getWorkPlaceKana())) {
						resultList.add(humanDto);
						break;
					}
				}
				// 職位コード取得(必須ではない)
				String resultPositionCode = humanDto.getPositionCode();
				if (isSearchPosition(resultPositionCode, searchWord, targetDate)) {
					resultList.add(humanDto);
					break;
				}
				// 所属コード取得(必須ではない)
				String resultSectionCode = humanDto.getSectionCode();
				// 所属情報を検索
				if (isSearchSecition(resultSectionCode, searchWord, targetDate)) {
					resultList.add(humanDto);
					break;
				}
				// 雇用契約コード取得(必須ではない)
				String resultEmployementCode = humanDto.getEmploymentContractCode();
				// 雇用契約が登録されている場合
				if (resultEmployementCode.isEmpty() == false) {
					// 雇用契約情報取得
					EmploymentContractDtoInterface resultEmploymentDto = employmentContract.getContractInfo(
							resultEmployementCode, targetDate);
					// 雇用契約名で検索結果がある場合
					if (isBroadMatch(searchWord, resultEmploymentDto.getEmploymentContractName())) {
						resultList.add(humanDto);
						break;
					}
					// 雇用契約略称で検索結果がある場合
					if (isBroadMatch(searchWord, resultEmploymentDto.getEmploymentContractAbbr())) {
						resultList.add(humanDto);
						break;
					}
				}
				// 役職情報有効の場合
				if (isPost) {
					// 役職情報取得
					HumanHistoryDtoInterface humanHistory = humanHistoryReference.findForInfo(humanDto.getPersonalId(),
							PlatformConst.NAMING_TYPE_POST, targetDate);
					// 人事汎用情報がない場合
					if (humanHistory == null) {
						break;
					}
					// 役職コード取得
					String namingItemCode = humanHistory.getHumanItemValue();
					// 役職コードが空の場合
					if (namingItemCode.isEmpty()) {
						break;
					}
					// 役職マスタ情報取得
					NamingDtoInterface namingDto = namingReference.getNamingItemInfo(PlatformConst.NAMING_TYPE_POST,
							namingItemCode, targetDate);
					// 役職名で検索結果がある場合
					if (isBroadMatch(searchWord, namingDto.getNamingItemName())) {
						resultList.add(humanDto);
						break;
					}
					// 役職略称で検索結果がある場合
					if (isBroadMatch(searchWord, namingDto.getNamingItemAbbr())) {
						resultList.add(humanDto);
						break;
					}
				}
			}
		}
		// 検索結果を返す
		return resultList;
	}
	
	/**
	 * 休職情報に対して、フリーワードによる検索を行う。<br>
	 * @param humanList     対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordSuspensionInfo(List<HumanDtoInterface> humanList,
			String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 休職情報履歴一覧所得
			List<SuspensionDtoInterface> suspensionList = suspensionReference.getSuspentionList(humanDto
				.getPersonalId());
			// 休職情報がない場合
			if (suspensionList.isEmpty()) {
				continue;
			}
			// 休職情報リスト毎に処理
			for (SuspensionDtoInterface suspensionDto : suspensionList) {
				// フリーワード毎に処理
				for (String searchWord : arySearchWord) {
					// フリーワードがない場合
					if (searchWord.isEmpty()) {
						break;
					}
					// 開始日で検索結果がある場合
					if (isBroadMatch(searchWord, DateUtility.getStringDate(suspensionDto.getStartDate()))) {
						resultList.add(humanDto);
						break;
					}
					// 終了予定日で検索結果がある場合
					if (isBroadMatch(searchWord, DateUtility.getStringDate(suspensionDto.getScheduleEndDate()))) {
						resultList.add(humanDto);
						break;
					}
					// 給与区分で検索結果がある場合(必須ではない)
					String allwanceType = suspensionDto.getAllowanceType();
					if (allwanceType.isEmpty() == false && isBroadMatch(searchWord, allwanceType)) {
						resultList.add(humanDto);
						break;
					}
					// 終了日で検索結果がある場合(必須ではない)
					String endDate = DateUtility.getStringDate(suspensionDto.getEndDate());
					if (endDate.isEmpty() == false && isBroadMatch(searchWord, endDate)) {
						resultList.add(humanDto);
						break;
					}
					// 休職理由で検索結果がある場合(必須ではない)
					String suspentionReason = suspensionDto.getSuspensionReason();
					if (suspentionReason.isEmpty() == false && isBroadMatch(searchWord, suspentionReason)) {
						resultList.add(humanDto);
						break;
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 退職情報に対して、フリーワードによる検索を行う。<br>
	 * @param humanList     対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordRetirementInfo(List<HumanDtoInterface> humanList,
			String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 退職情報取得
			RetirementDtoInterface retirementDto = retirementReference.getRetireInfo(humanDto.getPersonalId());
			// 退職情報がない場合
			if (retirementDto == null) {
				continue;
			}
			// フリーワード毎に処理
			for (String searchWord : arySearchWord) {
				// フリーワードがない場合
				if (searchWord.isEmpty()) {
					break;
				}
				// 退職日で検索結果がある場合
				if (isBroadMatch(searchWord, DateUtility.getStringDate(retirementDto.getRetirementDate()))) {
					resultList.add(humanDto);
					break;
				}
				// 退職理由コード取得
				String retirementResonCode = retirementDto.getRetirementReason();
				// 退職理由取得
				String retirementReson = getCodeName(retirementResonCode, PlatformConst.CODE_KEY_RETIREMENT);
				// 退職理由で検索結果がある場合
				if (isBroadMatch(searchWord, retirementReson)) {
					resultList.add(humanDto);
					break;
				}
				// 退職詳細で検索結果がある場合(必須ではない)
				String retirementDetail = retirementDto.getRetirementDetail();
				if (retirementDetail.isEmpty() == false && isBroadMatch(searchWord, retirementDetail)) {
					resultList.add(humanDto);
					break;
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 兼務情報に対して、フリーワードによる検索を行う。<br>
	 * @param humanList     対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordConcurrentInfo(List<HumanDtoInterface> humanList,
			String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 兼務情報履歴一覧所得
			List<ConcurrentDtoInterface> concurrentList = concurrentReference.getConcurrentList(
					humanDto.getPersonalId(), targetDate);
			// 兼務情報リストがない場合
			if (concurrentList.isEmpty()) {
				continue;
			}
			// 所属マスタ・職位マスタ情報取得
			// 兼務情報リスト毎に処理
			for (ConcurrentDtoInterface concurrentDto : concurrentList) {
				// フリーワード毎に処理
				for (String searchWord : arySearchWord) {
					// フリーワードがない場合
					if (searchWord.isEmpty()) {
						break;
					}
					// 開始日で検索結果がある場合
					if (isBroadMatch(searchWord, DateUtility.getStringDate(concurrentDto.getStartDate()))) {
						resultList.add(humanDto);
						break;
					}
					// 兼務備考で検索結果がある場合(必須ではない)
					String concurrentRemark = concurrentDto.getConcurrentRemark();
					if (concurrentRemark.isEmpty() == false && isBroadMatch(searchWord, concurrentRemark)) {
						resultList.add(humanDto);
						break;
					}
					// 終了日で検索結果がある場合(必須ではない)
					String endDate = DateUtility.getStringDate(concurrentDto.getEndDate());
					if (endDate.isEmpty() == false && isBroadMatch(searchWord, endDate)) {
						resultList.add(humanDto);
						break;
					}
					// 職位コード取得
					String resultPositionCode = concurrentDto.getPositionCode();
					if (isSearchPosition(resultPositionCode, searchWord, targetDate)) {
						resultList.add(humanDto);
						break;
					}
					// 所属コード取得
					String resultSectionCode = concurrentDto.getSectionCode();
					if (isSearchSecition(resultSectionCode, searchWord, targetDate)) {
						resultList.add(humanDto);
						break;
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事汎用通常情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	  * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanNormalInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 対象社員有効日取得
			Date activeDate = humanDto.getActivateDate();
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			Map<String, String> normalMap = humanNormalReference.getShowHumanNormalMapInfo(division,
					KEY_VIEW_HUMAN_SEARCH, personalId, activeDate, targetDate);
			// 人事汎用通常情報がない場合
			if (normalMap.isEmpty()) {
				continue;
			}
			// 人事汎用情報を検索
			if (isSearchHumanGeneral(arySearchWord, normalMap)) {
				// 検索結果リスト追加
				resultList.add(humanDto);
				continue;
			}
		}
		return resultList;
	}
	
	/**
	 * 人事汎用履歴情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanHistoryInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			LinkedHashMap<String, Map<String, String>> historyMap = humanHistoryReference.getHumanHistoryMapInfo(
					division, KEY_VIEW_HUMAN_SEARCH, personalId, targetDate, targetDate);
			// 人事汎用通常情報がない場合
			if (historyMap.isEmpty()) {
				continue;
			}
			// 有効日のリストを取得
			List<String> historyList = new ArrayList<String>(historyMap.keySet());
			// 履歴情報有効日キー毎に処理
			for (String mapDate : historyList) {
				// 項目名項目値マップ取得
				Map<String, String> historyItemMap = historyMap.get(mapDate);
				// キー取得
				if (isSearchHumanGeneral(arySearchWord, historyItemMap)) {
					// 検索結果リスト追加
					resultList.add(humanDto);
					break;
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事汎用一覧情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @param targetDate 対象日
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanArrayInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord, Date targetDate) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			LinkedHashMap<String, Map<String, String>> arrayMap = humanArrayReference.getRowIdArrayMapInfo(division,
					KEY_VIEW_HUMAN_SEARCH, personalId, targetDate);
			// 人事汎用通常情報がない場合
			if (arrayMap.isEmpty()) {
				continue;
			}
			// 有効日のリストを取得
			List<String> rowIdList = new ArrayList<String>(arrayMap.keySet());
			// 履歴情報有効日キー毎に処理
			for (String rowId : rowIdList) {
				// 項目名項目値マップ取得
				Map<String, String> arrayItemMap = arrayMap.get(rowId);
				// キー取得
				if (isSearchHumanGeneral(arySearchWord, arrayItemMap)) {
					// 検索結果リスト追加
					resultList.add(humanDto);
					break;
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事バイナリ汎用通常情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanBinaryNormalInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			// 人事バイナリ汎用情報取得
			HumanBinaryNormalDtoInterface binarynormal = humanBinaryNormal.findForInfo(personalId, division);
			// 人事バイナリ汎用情報がない場合
			if (binarynormal == null) {
				continue;
			}
			// フリーワード毎に処理
			for (String searchWord : arySearchWord) {
				// フリーワードがない場合
				if (searchWord.isEmpty()) {
					break;
				}
				// 検索
				if (isSearchHumanBinaryGeneral(binarynormal.getFileType(), binarynormal.getFileName(),
						binarynormal.getFileRemark(), searchWord)) {
					// 検索結果リスト追加
					resultList.add(humanDto);
					break;
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事バイナリ汎用履歴情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanBinaryHistoryInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			// 人事バイナリ汎用情報リスト取得
			List<HumanBinaryHistoryDtoInterface> binaryHistoryList = humanBinaryHistory.findForHistory(personalId,
					division);
			// 人事バイナリ汎用情報リストがない場合
			if (binaryHistoryList.isEmpty()) {
				continue;
			}
			// 履歴情報有効日キー毎に処理
			for (HumanBinaryHistoryDtoInterface binaryHistory : binaryHistoryList) {
				// フリーワード毎に処理
				for (String searchWord : arySearchWord) {
					// フリーワードがない場合
					if (searchWord.isEmpty()) {
						break;
					}
					// 検索
					if (isSearchHumanBinaryGeneral(binaryHistory.getFileType(), binaryHistory.getFileName(),
							binaryHistory.getFileRemark(), searchWord)) {
						// 検索結果リスト追加
						resultList.add(humanDto);
						break;
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事バイナリ汎用一覧情報に対して、フリーワードによる検索を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param humanList 対象人事情報リスト
	 * @param arySearchWord フリーワード配列
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchForFreeWordHumanBinaryArrayInfo(String division,
			List<HumanDtoInterface> humanList, String[] arySearchWord) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 対象人事リスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 個人ID取得
			String personalId = humanDto.getPersonalId();
			// 人事バイナリ汎用情報リスト取得
			List<HumanBinaryArrayDtoInterface> binaryArrayList = humanBinaryArray.findForItemType(personalId, division);
			// 人事バイナリ汎用情報リストがない場合
			if (binaryArrayList.isEmpty()) {
				continue;
			}
			// 履歴情報有効日キー毎に処理
			for (HumanBinaryArrayDtoInterface binaryArray : binaryArrayList) {
				// フリーワード毎に処理
				for (String searchWord : arySearchWord) {
					// フリーワードがない場合
					if (searchWord.isEmpty()) {
						break;
					}
					// 検索
					if (isSearchHumanBinaryGeneral(binaryArray.getFileType(), binaryArray.getFileName(),
							binaryArray.getFileRemark(), searchWord)) {
						// 検索結果リスト追加
						resultList.add(humanDto);
						break;
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人事汎用情報(通常・履歴・一覧)を検索する。
	 * @param arySearchWord 検索ワード配列
	 * @param itemMap 項目、項目値のマップ
	 * @return 確認結果(true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean isSearchHumanGeneral(String[] arySearchWord, Map<String, String> itemMap) throws MospException {
		// 項目名のリストを取得
		List<String> itemNameList = new ArrayList<String>(itemMap.keySet());
		// 通常情報キー毎に処理
		for (String itemKey : itemNameList) {
			String value = itemMap.get(itemKey);
			// フリーワード毎に処理
			for (String searchWord : arySearchWord) {
				// フリーワードがない場合
				if (searchWord.isEmpty()) {
					return false;
				}
				// 検索結果がある場合
				if (value.isEmpty() == false && isBroadMatch(searchWord, value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 人事汎用バイナリ情報(通常・履歴・一覧)を検索する。
	 * @param fileType ファイル区分
	 * @param fileName ファイル名
	 * @param remark ファイル備考
	 * @param searchWord 検索ワード
	 * @return 検索結果(true：検索結果有、false：検索結果無)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean isSearchHumanBinaryGeneral(String fileType, String fileName, String remark, String searchWord)
			throws MospException {
		// ファイル区分で検索結果がある場合
		if (fileType.isEmpty() == false) {
			if (isBroadMatch(searchWord, getCodeName(fileType, PlatformConst.CODE_KEY_BINARY_FILE_TYPE))) {
				return true;
			}
		}
		// ファイル名で検索結果がある場合
		if (isBroadMatch(searchWord, fileName)) {
			return true;
		}
		// ファイル備考で検索結果がある場合(必須ではない)
		if (remark.isEmpty() == false && isBroadMatch(searchWord, remark)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 職位を検索する。<br>
	 * 検索ワードに検索された社員情報の職位があてはまるか確認する。<br>
	 * @param resultPositionCode 職位コード
	 * @param searchWord 検索ワード
	 * @param targetDate 対象社員有効日
	 * @return 検索結果(true：検索結果有、false：検索結果無)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean isSearchPosition(String resultPositionCode, String searchWord, Date targetDate)
			throws MospException {
		// 職位が登録されている場合
		if (resultPositionCode.isEmpty() == false) {
			// 職位名称で検索結果がある場合
			if (isBroadMatch(searchWord, position.getPositionName(resultPositionCode, targetDate))) {
				return true;
			}
			// 職位略称で検索結果がある場合
			if (isBroadMatch(searchWord, position.getPositionAbbr(resultPositionCode, targetDate))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 所属を検索する。<br>
	 * 検索ワードに検索された社員情報の所属があてはまるか確認する。<br>
	 * @param resultSectionCode 所属コード
	 * @param searchWord 検索ワード
	 * @param targetDate 対象社員有効日
	 * @return 検索結果(true：検索結果有、false：検索結果無)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean isSearchSecition(String resultSectionCode, String searchWord, Date targetDate)
			throws MospException {
		if (resultSectionCode.isEmpty() == false) {
			// 所属名称で検索結果がある場合
			if (isBroadMatch(searchWord, section.getSectionName(resultSectionCode, targetDate))) {
				return true;
			}
			// 所属略称で検索結果がある場合
			if (isBroadMatch(searchWord, section.getSectionAbbr(resultSectionCode, targetDate))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<HumanListDtoInterface> getHumanList() throws MospException {
		// 人事マスタ検索
		List<HumanDtoInterface> list = search();
		String[][] aryWorkPlace = workPlace.getSelectArray(targetDate, true, null);
		String[][] aryEmploymentContract = employmentContract.getSelectArray(targetDate, true, null);
		String[][] arySection = section.getNameSelectArray(targetDate, true, null);
		String[][] aryPosition = position.getSelectArray(targetDate, true, null);
		// 人事情報リスト準備
		List<HumanListDtoInterface> humanList = new ArrayList<HumanListDtoInterface>();
		// 検索結果から人事情報リストを作成
		for (HumanDtoInterface dto : list) {
			// 初期化
			HumanListDtoInterface humanListDto = new PfaHumanListDto();
			// 人事基本情報設定
			humanListDto.setPfmHumanId(String.valueOf(dto.getPfmHumanId()));
			humanListDto.setPersonalId(dto.getPersonalId());
			humanListDto.setEmployeeCode(dto.getEmployeeCode());
			humanListDto.setLastName(dto.getLastName());
			humanListDto.setFirstName(dto.getFirstName());
			humanListDto.setLastKana(dto.getLastKana());
			humanListDto.setFirstKana(dto.getFirstKana());
			// 勤務地情報設定
			humanListDto.setWorkPlaceCode(dto.getWorkPlaceCode());
			if (dto.getWorkPlaceCode().isEmpty() == false) {
				humanListDto.setWorkPlaceAbbr(getCodeName(dto.getWorkPlaceCode(), aryWorkPlace));
			}
			// 所属情報設定
			humanListDto.setSectionCode(dto.getSectionCode());
			if (dto.getSectionCode().isEmpty() == false) {
				humanListDto.setSectionName(getCodeName(dto.getSectionCode(), arySection));
			}
			// 職位情報設定
			humanListDto.setPositionCode(dto.getPositionCode());
			if (dto.getPositionCode().isEmpty() == false) {
				humanListDto.setPositionAbbr(getCodeName(dto.getPositionCode(), aryPosition));
			}
			// 雇用契約情報設定
			humanListDto.setEmploymentContractCode(dto.getEmploymentContractCode());
			if (dto.getEmploymentContractCode().isEmpty() == false) {
				humanListDto.setEmploymentContractAbbr(getCodeName(dto.getEmploymentContractCode(),
						aryEmploymentContract));
			}
			// 休退職情報設定
			if (retirementReference.isRetired(dto.getPersonalId(), targetDate)) {
				humanListDto.setRetireState(mospParams.getProperties().getName("RetirementOn"));
			} else if (suspensionReference.isSuspended(dto.getPersonalId(), targetDate)) {
				humanListDto.setRetireState(mospParams.getProperties().getName("RetirementLeave"));
			} else if (entranceReference.isEntered(dto.getPersonalId(), targetDate) == false) {
				humanListDto.setRetireState("");
			} else {
				humanListDto.setRetireState(mospParams.getProperties().getName("RetirementOff"));
			}
			// 人事情報リストに追加
			humanList.add(humanListDto);
		}
		return humanList;
	}
	
	@Override
	public Set<String> getPersonalIdSet() throws MospException {
		// 人事マスタ検索及び個人IDセット取得
		return PlatformUtility.getPersonalIdSet(search());
	}
	
	@Override
	public String[][] getCodedSelectArray(boolean needBlank) throws MospException {
		// プルダウン用配列取得(コード)
		return getSelectArray(needBlank, true);
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode  コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(boolean needBlank, boolean viewCode) throws MospException {
		// 人事マスタ検索
		List<HumanDtoInterface> list = search();
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (HumanDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getPersonalId();
			// 表示内容設定
			if (viewCode) {
				// コード表示
				array[idx++][1] = getCodedName(dto.getEmployeeCode(), getHumanName(dto), length);
			} else {
				// コード非表示
				array[idx++][1] = getHumanName(dto);
			}
		}
		return array;
	}
	
	@Override
	public Map<String, HumanDtoInterface> getHumanDtoMap() throws MospException {
		// 個人IDセット準備
		Map<String, HumanDtoInterface> map = new HashMap<String, HumanDtoInterface>();
		List<HumanDtoInterface> list = search();
		// 情報毎に処理
		for (HumanDtoInterface dto : list) {
			map.put(dto.getPersonalId(), dto);
		}
		return map;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<HumanDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (HumanDtoInterface dto : list) {
			if (dto.getEmployeeCode().length() > length) {
				length = dto.getEmployeeCode().length();
			}
		}
		return length;
	}
	
	@Override
	public void setTargetDate(Date targetDate) {
		this.targetDate = getDateClone(targetDate);
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setFromEmployeeCode(String fromEmployeeCode) {
		this.fromEmployeeCode = fromEmployeeCode;
	}
	
	@Override
	public void setToEmployeeCode(String toEmployeeCode) {
		this.toEmployeeCode = toEmployeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	@Override
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setNeedLowerSection(boolean needLowerSection) {
		this.needLowerSection = Boolean.valueOf(needLowerSection);
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setNeedConcurrent(boolean needConcurrent) {
		this.needConcurrent = Boolean.valueOf(needConcurrent);
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}
	
	@Override
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	@Override
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	
	@Override
	public void setEmployeeCodeType(String employeeCodeType) {
		this.employeeCodeType = employeeCodeType;
	}
	
	@Override
	public void setLastNameType(String lastNameType) {
		this.lastNameType = lastNameType;
	}
	
	@Override
	public void setFirstNameType(String firstNameType) {
		this.firstNameType = firstNameType;
	}
	
	@Override
	public void setLastKanaType(String lastKanaType) {
		this.lastKanaType = lastKanaType;
	}
	
	@Override
	public void setFirstKanaType(String firstKanaType) {
		this.firstKanaType = firstKanaType;
	}
	
	@Override
	public void setUnnecessaryPersonalId(String unnecessaryPersonalId) {
		this.unnecessaryPersonalId = unnecessaryPersonalId;
	}
	
	@Override
	public void setNeedApproverRole(boolean needApproverRole) {
		this.needApproverRole = Boolean.valueOf(needApproverRole);
	}
	
	@Override
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@Override
	public void setStartDate(Date startDate) {
		this.startDate = getDateClone(startDate);
	}
	
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = getDateClone(endDate);
	}
	
}
