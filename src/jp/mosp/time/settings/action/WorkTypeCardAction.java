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
package jp.mosp.time.settings.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.WorkTypeCardVo;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態情報の個別詳細情報の確認、編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li></ul>
 */
public class WorkTypeCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW		= "TM5220";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤務形態一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW	= "TM5221";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤務形態情報テーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、または勤務形態コードが登録済のレコードのものと同一であった場合、
	 * エラーメッセージを表示する。<br>
	 */
	public static final String	CMD_REGIST		= "TM5225";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE		= "TM5227";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE	= "TM5271";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE	= "TM5273";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkTypeCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkTypeCardVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
			prepareVo();
			addMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 */
	protected void show() {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			// 新規登録
			insert();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			add();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// 時間重複チェック
		restCheck();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規追加処理
		regist.insert(dto);
		// 勤務形態項目毎に処理
		for (String item : WorkTypeItemRegistBeanInterface.CODES_WORK_TYPE_ITEM) {
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// 新規追加処理
			itemRegist.insert(itemDto);
		}
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージ設定
		addInsertMessage();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// 時間重複チェック
		restCheck();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 勤務形態項目毎に処理
		for (String item : WorkTypeItemRegistBeanInterface.CODES_WORK_TYPE_ITEM) {
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// 履歴追加処理
			itemRegist.add(itemDto);
		}
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージ設定
		addInsertHistoryMessage();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// 時間重複チェック
		restCheck();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージ設定
			addInsertFailedMessage();
			return;
		}
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// 勤務形態項目毎に処理
		for (String item : WorkTypeItemRegistBeanInterface.CODES_WORK_TYPE_ITEM) {
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// 更新処理
			itemRegist.update(itemDto);
		}
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージ設定
			addUpdateFailedMessage();
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージ設定
		addUpdateMessage();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		Date date = MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth()));
		// 削除処理
		regist.delete(dto);
		time().workTypeItemRegist().delete(vo.getTxtWorkTypeCode(), date);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージ設定
			addDeleteHistoryFailedMessage();
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージ設定
		addDeleteMessage();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
	}
	
	/**
	 * 新規登録モードで画面を表示する。<br>
	 */
	protected void insertMode() {
		// 編集モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 */
	protected void addMode() {
		// 履歴追加モード設定
		setEditAddMode();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 初期値設定
		setDefaultValues();
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 勤務形態コードと有効日で編集対象情報を取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String workTypeCode, Date activateDate) throws MospException {
		// 参照クラス取得
		WorkTypeReferenceBeanInterface reference = timeReference().workType();
		// 履歴編集対象取得
		WorkTypeDtoInterface dto = reference.findForKey(workTypeCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 勤務形態項目コード毎に勤務形態項目を取得し設定
		for (String workTypeItemCode : WorkTypeItemRegistBeanInterface.CODES_WORK_TYPE_ITEM) {
			// 勤務形態項目取得
			WorkTypeItemDtoInterface itemDto = timeReference().workTypeItem().findForKey(workTypeCode, activateDate,
					workTypeItemCode);
			// 勤務形態項目設定
			if (itemDto != null) {
				setVoFieldsItem(itemDto, workTypeItemCode);
			}
		}
		// 編集モード設定
		setEditUpdateMode(reference.getWorkTypeHistory(workTypeCode));
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 編集項目の表示
		vo.setTxtWorkTypeCode("");
		vo.setTxtWorkTypeName("");
		vo.setTxtWorkTypeAbbr("");
		// 登録情報欄の初期値設定
		vo.setTxtWorkStartHour("");
		vo.setTxtWorkStartMinute("");
		vo.setTxtWorkEndHour("");
		vo.setTxtWorkEndMinute("");
		vo.setTxtWorkTimeHour("0");
		vo.setTxtWorkTimeMinute("0");
		vo.setTxtRestTimeHour("0");
		vo.setTxtRestTimeMinute("0");
		vo.setTxtRestStart1Hour("");
		vo.setTxtRestStart1Minute("");
		vo.setTxtRestEnd1Hour("");
		vo.setTxtRestEnd1Minute("");
		vo.setTxtRestStart2Hour("");
		vo.setTxtRestStart2Minute("");
		vo.setTxtRestEnd2Hour("");
		vo.setTxtRestEnd2Minute("");
		vo.setTxtRestStart3Hour("");
		vo.setTxtRestStart3Minute("");
		vo.setTxtRestEnd3Hour("");
		vo.setTxtRestEnd3Minute("");
		vo.setTxtRestStart4Hour("");
		vo.setTxtRestStart4Minute("");
		vo.setTxtRestEnd4Hour("");
		vo.setTxtRestEnd4Minute("");
		vo.setTxtFrontStartHour("");
		vo.setTxtFrontStartMinute("");
		vo.setTxtFrontEndHour("");
		vo.setTxtFrontEndMinute("");
		vo.setTxtBackStartHour("");
		vo.setTxtBackStartMinute("");
		vo.setTxtBackEndHour("");
		vo.setTxtBackEndMinute("");
		vo.setTxtOverBeforeHour("");
		vo.setTxtOverBeforeMinute("");
		vo.setTxtOverPerHour("");
		vo.setTxtOverPerMinute("");
		vo.setTxtOverRestHour("");
		vo.setTxtOverRestMinute("");
		vo.setTxtHalfRestHour("");
		vo.setTxtHalfRestMinute("");
		vo.setTxtHalfRestStartHour("");
		vo.setTxtHalfRestStartMinute("");
		vo.setTxtHalfRestEndHour("");
		vo.setTxtHalfRestEndMinute("");
		vo.setCkbDirectStart(MospConst.CHECKBOX_OFF);
		vo.setCkbDirectEnd(MospConst.CHECKBOX_OFF);
		vo.setPltMidnightRestExclusion(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		vo.setTxtShort1StartHour("");
		vo.setTxtShort1StartMinute("");
		vo.setTxtShort1EndHour("");
		vo.setTxtShort1EndMinute("");
		vo.setPltShort1Type("");
		vo.setTxtShort2StartHour("");
		vo.setTxtShort2StartMinute("");
		vo.setTxtShort2EndHour("");
		vo.setTxtShort2EndMinute("");
		vo.setPltShort2Type("");
		vo.setTmmWorkTypeItemId(new long[WorkTypeItemRegistBeanInterface.CODES_WORK_TYPE_ITEM.length]);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(WorkTypeDtoInterface dto) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmWorkTypeId(vo.getRecordId());
		dto.setActivateDate(MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth())));
		dto.setWorkTypeCode(vo.getTxtWorkTypeCode());
		dto.setWorkTypeName(vo.getTxtWorkTypeName());
		dto.setWorkTypeAbbr(vo.getTxtWorkTypeAbbr());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(WorkTypeDtoInterface dto) {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmWorkTypeId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtWorkTypeCode(dto.getWorkTypeCode());
		vo.setTxtWorkTypeName(dto.getWorkTypeName());
		vo.setTxtWorkTypeAbbr(dto.getWorkTypeAbbr());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param itemType 時間種別
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFieldsItem(WorkTypeItemDtoInterface dto, String itemType) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setActivateDate(MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth())));
		dto.setWorkTypeCode(vo.getTxtWorkTypeCode());
		dto.setPreliminary("");
		if (itemType.equals(TimeConst.CODE_WORKSTART)) {
			// 始業時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(0));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKSTART);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtWorkStartHour(), vo.getTxtWorkStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_WORKEND)) {
			// 終業時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(1));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKEND);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtWorkEndHour(), vo.getTxtWorkEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_WORKTIME)) {
			// 勤務時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(2));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKTIME);
			dto.setWorkTypeItemValue(getTimestamp(getWorkTime()));
		} else if (itemType.equals(TimeConst.CODE_RESTTIME)) {
			// 休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(3));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTTIME);
			dto.setWorkTypeItemValue(getTimestamp(getRestTime()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART1)) {
			// 休憩開始時刻1
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(4));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART1);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestStart1Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestStart1Hour(), vo.getTxtRestStart1Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND1)) {
			// 休憩終了時刻1
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(5));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND1);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestEnd1Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestEnd1Hour(), vo.getTxtRestEnd1Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART2)) {
			// 休憩開始時刻2
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(6));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART2);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestStart2Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestStart2Hour(), vo.getTxtRestStart2Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND2)) {
			// 休憩終了時刻2
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(7));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND2);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestEnd2Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestEnd2Hour(), vo.getTxtRestEnd2Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART3)) {
			// 休憩開始時刻3
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(8));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART3);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestStart3Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestStart3Hour(), vo.getTxtRestStart3Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND3)) {
			// 休憩終了時刻3
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(9));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND3);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestEnd3Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestEnd3Hour(), vo.getTxtRestEnd3Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART4)) {
			// 休憩開始時刻4
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(10));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART4);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestStart4Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestStart4Hour(), vo.getTxtRestStart4Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND4)) {
			// 休憩終了時刻4
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(11));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND4);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtRestEnd4Hour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtRestEnd4Hour(), vo.getTxtRestEnd4Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_FRONTSTART)) {
			// 午前休開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(12));
			dto.setWorkTypeItemCode(TimeConst.CODE_FRONTSTART);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtFrontStartHour(), vo.getTxtFrontStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_FRONTEND)) {
			// 午前休終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(13));
			dto.setWorkTypeItemCode(TimeConst.CODE_FRONTEND);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtFrontEndHour(), vo.getTxtFrontEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_BACKSTART)) {
			// 午後休開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(14));
			dto.setWorkTypeItemCode(TimeConst.CODE_BACKSTART);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtBackStartHour(), vo.getTxtBackStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_BACKEND)) {
			// 午後休終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(15));
			dto.setWorkTypeItemCode(TimeConst.CODE_BACKEND);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtBackEndHour(), vo.getTxtBackEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERBEFORE)) {
			// 残前休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(16));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERBEFORE);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtOverBeforeHour(), vo.getTxtOverBeforeMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERPER)) {
			// 残業休憩時間(毎)
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(17));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERPER);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtOverPerHour(), vo.getTxtOverPerMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERREST)) {
			// 残業休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(18));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERREST);
			dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtOverRestHour(), vo.getTxtOverRestMinute()));
		} else if (itemType.equals(TimeConst.CODE_HALFREST)) {
			// 半休休憩対象時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(19));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFREST);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtHalfRestHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtHalfRestHour(), vo.getTxtHalfRestMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_HALFRESTSTART)) {
			// 半休休憩開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(20));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFRESTSTART);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtHalfRestStartHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtHalfRestStartHour(), vo.getTxtHalfRestStartMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_HALFRESTEND)) {
			// 半休休憩終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(21));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFRESTEND);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtHalfRestEndHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtHalfRestEndHour(), vo.getTxtHalfRestEndMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			// 直行
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(22));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START);
			dto.setPreliminary(vo.getCkbDirectStart());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			// 直帰
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(23));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END);
			dto.setPreliminary(vo.getCkbDirectEnd());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			// 割増休憩除外
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(24));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST);
			dto.setPreliminary(vo.getPltMidnightRestExclusion());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			// 時短時間1開始時刻及び給与区分
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(25));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtShort1StartHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtShort1StartHour(), vo.getTxtShort1StartMinute()));
				dto.setPreliminary(vo.getPltShort1Type());
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			// 時短時間1終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(26));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtShort1EndHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtShort1EndHour(), vo.getTxtShort1EndMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			// 時短時間2開始時刻及び給与区分
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(27));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtShort2StartHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtShort2StartHour(), vo.getTxtShort2StartMinute()));
				dto.setPreliminary(vo.getPltShort2Type());
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			// 時短時間2終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(28));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
			dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
			if (!vo.getTxtShort2EndHour().isEmpty()) {
				dto.setWorkTypeItemValue(getDefaultTime(vo.getTxtShort2EndHour(), vo.getTxtShort2EndMinute()));
			}
		}
		// 画面上にはないが必須項目なので設定する
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @param itemType 時間種別
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsItem(WorkTypeItemDtoInterface dto, String itemType) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		if (itemType.equals(TimeConst.CODE_WORKSTART)) {
			// 始業時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 0);
			vo.setTxtWorkStartHour(DateUtility.getStringHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORKEND)) {
			// 終業時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 1);
			vo.setTxtWorkEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORKTIME)) {
			// 勤務時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 2);
			vo.setTxtWorkTimeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkTimeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTTIME)) {
			// 休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 3);
			vo.setTxtRestTimeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestTimeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART1)) {
			// 休憩開始時刻1
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 4);
			vo.setTxtRestStart1Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart1Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND1)) {
			// 休憩終了時刻1
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 5);
			vo.setTxtRestEnd1Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd1Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART2)) {
			// 休憩開始時刻2
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 6);
			vo.setTxtRestStart2Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart2Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND2)) {
			// 休憩終了時刻2
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 7);
			vo.setTxtRestEnd2Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd2Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART3)) {
			// 休憩開始時刻3
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 8);
			vo.setTxtRestStart3Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart3Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND3)) {
			// 休憩終了時刻3
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 9);
			vo.setTxtRestEnd3Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd3Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART4)) {
			// 休憩開始時刻4
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 10);
			vo.setTxtRestStart4Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart4Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND4)) {
			// 休憩終了時刻4
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 11);
			vo.setTxtRestEnd4Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd4Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_FRONTSTART)) {
			// 午前休開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 12);
			vo.setTxtFrontStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtFrontStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_FRONTEND)) {
			// 午前休終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 13);
			vo.setTxtFrontEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtFrontEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_BACKSTART)) {
			// 午後休開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 14);
			vo.setTxtBackStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtBackStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_BACKEND)) {
			// 午後休終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 15);
			vo.setTxtBackEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtBackEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERBEFORE)) {
			// 残前休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 16);
			vo.setTxtOverBeforeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverBeforeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERPER)) {
			// 残業休憩時間(毎)
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 17);
			vo.setTxtOverPerHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverPerMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERREST)) {
			// 残業休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 18);
			vo.setTxtOverRestHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverRestMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFREST)) {
			// 半休休憩対象時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 19);
			vo.setTxtHalfRestHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFRESTSTART)) {
			// 半休休憩開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 20);
			vo.setTxtHalfRestStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFRESTEND)) {
			// 半休休憩終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 21);
			vo.setTxtHalfRestEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			// 直行
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 22);
			vo.setCkbDirectStart(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			// 直帰
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 23);
			vo.setCkbDirectEnd(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			// 割増休憩除外
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 24);
			vo.setPltMidnightRestExclusion(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			// 時短時間1開始時刻及び給与区分
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 25);
			vo.setTxtShort1StartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort1StartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
			vo.setPltShort1Type(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			// 時短時間1終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 26);
			vo.setTxtShort1EndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort1EndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			// 時短時間2開始時刻及び給与区分
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 27);
			vo.setTxtShort2StartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort2StartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
			vo.setPltShort2Type(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			// 時短時間2終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 28);
			vo.setTxtShort2EndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort2EndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		}
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * 勤務時間取得。<br>
	 * @return 勤務時間(分)
	 */
	protected int getWorkTime() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		int minute = getMinute(vo.getTxtWorkStartHour(), vo.getTxtWorkStartMinute(), vo.getTxtWorkEndHour(),
				vo.getTxtWorkEndMinute())
				- getRestTime();
		if (minute > 0) {
			return minute;
		}
		return 0;
	}
	
	/**
	 * 休憩時間取得。<br>
	 * @return 休憩時間(分)
	 */
	protected int getRestTime() {
		return getRest1Time() + getRest2Time() + getRest3Time() + getRest4Time();
	}
	
	/**
	 * 休憩1時間取得。<br>
	 * @return 休憩1時間(分)
	 */
	protected int getRest1Time() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		if (vo.getTxtRestStart1Hour().isEmpty() || vo.getTxtRestStart1Minute().isEmpty()
				|| vo.getTxtRestEnd1Hour().isEmpty() || vo.getTxtRestEnd1Minute().isEmpty()) {
			return 0;
		}
		return getMinute(vo.getTxtRestStart1Hour(), vo.getTxtRestStart1Minute(), vo.getTxtRestEnd1Hour(),
				vo.getTxtRestEnd1Minute());
	}
	
	/**
	 * 休憩2時間取得。<br>
	 * @return 休憩2時間(分)
	 */
	protected int getRest2Time() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		if (vo.getTxtRestStart2Hour().isEmpty() || vo.getTxtRestStart2Minute().isEmpty()
				|| vo.getTxtRestEnd2Hour().isEmpty() || vo.getTxtRestEnd2Minute().isEmpty()) {
			return 0;
		}
		return getMinute(vo.getTxtRestStart2Hour(), vo.getTxtRestStart2Minute(), vo.getTxtRestEnd2Hour(),
				vo.getTxtRestEnd2Minute());
	}
	
	/**
	 * 休憩3時間取得。<br>
	 * @return 休憩3時間(分)
	 */
	protected int getRest3Time() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		if (vo.getTxtRestStart3Hour().isEmpty() || vo.getTxtRestStart3Minute().isEmpty()
				|| vo.getTxtRestEnd3Hour().isEmpty() || vo.getTxtRestEnd3Minute().isEmpty()) {
			return 0;
		}
		return getMinute(vo.getTxtRestStart3Hour(), vo.getTxtRestStart3Minute(), vo.getTxtRestEnd3Hour(),
				vo.getTxtRestEnd3Minute());
	}
	
	/**
	 * 休憩4時間取得。<br>
	 * @return 休憩4時間(分)
	 */
	protected int getRest4Time() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		if (vo.getTxtRestStart4Hour().isEmpty() || vo.getTxtRestStart4Minute().isEmpty()
				|| vo.getTxtRestEnd4Hour().isEmpty() || vo.getTxtRestEnd4Minute().isEmpty()) {
			return 0;
		}
		return getMinute(vo.getTxtRestStart4Hour(), vo.getTxtRestStart4Minute(), vo.getTxtRestEnd4Hour(),
				vo.getTxtRestEnd4Minute());
	}
	
	/**
	 * 休憩/午前休/午後休重複チェック。<br>
	 * @throws MospException 例外発生時	
	 */
	protected void restCheck() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 始業時刻
		int startTimeHour = getInt(vo.getTxtWorkStartHour());
		int startTimeMinute = getInt(vo.getTxtWorkStartMinute());
		// 終業時刻
		int endTimeHour = getInt(vo.getTxtWorkEndHour());
		int endTimeMinute = getInt(vo.getTxtWorkEndMinute());
		// 休憩1
		int restStartTime1Hour = getInt(vo.getTxtRestStart1Hour());
		int restStartTime1Minute = getInt(vo.getTxtRestStart1Minute());
		int restEndTime1Hour = getInt(vo.getTxtRestEnd1Hour());
		int restEndTime1Minute = getInt(vo.getTxtRestEnd1Minute());
		// 休憩2
		int restStartTime2Hour = getInt(vo.getTxtRestStart2Hour());
		int restStartTime2Minute = getInt(vo.getTxtRestStart2Minute());
		int restEndTime2Hour = getInt(vo.getTxtRestEnd2Hour());
		int restEndTime2Minute = getInt(vo.getTxtRestEnd2Minute());
		// 休憩3
		int restStartTime3Hour = getInt(vo.getTxtRestStart3Hour());
		int restStartTime3Minute = getInt(vo.getTxtRestStart3Minute());
		int restEndTime3Hour = getInt(vo.getTxtRestEnd3Hour());
		int restEndTime3Minute = getInt(vo.getTxtRestEnd3Minute());
		// 休憩4
		int restStartTime4Hour = getInt(vo.getTxtRestStart4Hour());
		int restStartTime4Minute = getInt(vo.getTxtRestStart4Minute());
		int restEndTime4Hour = getInt(vo.getTxtRestEnd4Hour());
		int restEndTime4Minute = getInt(vo.getTxtRestEnd4Minute());
		// 午前休
		int frontStartHour = getInt(vo.getTxtFrontStartHour());
		int frontStartMinute = getInt(vo.getTxtFrontStartMinute());
		int frontEndHour = getInt(vo.getTxtFrontEndHour());
		int frontEndMinute = getInt(vo.getTxtFrontEndMinute());
		// 午後休
		int backStartHour = getInt(vo.getTxtBackStartHour());
		int backStartMinute = getInt(vo.getTxtBackStartMinute());
		int backEndHour = getInt(vo.getTxtBackEndHour());
		int backEndMinute = getInt(vo.getTxtBackEndMinute());
		int[] aryStartTimeHour = { restStartTime1Hour, restStartTime2Hour, restStartTime3Hour, restStartTime4Hour,
			frontStartHour, backStartHour };
		int[] aryStartTimeMinute = { restStartTime1Minute, restStartTime2Minute, restStartTime3Minute,
			restStartTime4Minute, frontStartMinute, backStartMinute };
		int[] aryEndTimeHour = { restEndTime1Hour, restEndTime2Hour, restEndTime3Hour, restEndTime4Hour, frontEndHour,
			backEndHour };
		int[] aryEndTimeMinute = { restEndTime1Minute, restEndTime2Minute, restEndTime3Minute, restEndTime4Minute,
			frontEndMinute, backEndMinute };
		// 始業時刻及び終業時刻を取得
		
		Date startTime = TimeUtility.getDateTime(startTimeHour, startTimeMinute);
		Date endTime = TimeUtility.getDateTime(endTimeHour, endTimeMinute);
		// エラーメッセージの設定
		String errMes = mospParams.getName("RestTime", "Time");
		// 始業終業時刻前後確認
		if (endTime.after(startTime) == false) {
			// エラーメッセージを設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_START_END_TIME_CHECK);
			return;
		}
		// 始業時刻が24時以降の場合
		if (startTimeHour >= TimeConst.TIME_DAY_ALL_HOUR) {
			// エラーメッセージ追加
			String rep[] = { mospParams.getName("StartWork", "Moment"),
				TimeConst.TIME_DAY_ALL_HOUR + mospParams.getName("Hour", "From", "Ahead", "Of", "Time") };
			mospParams.addErrorMessage(PlatformMessageConst.MSG_CHR_TYPE, rep);
			return;
		}
		for (int i = 0; i < aryStartTimeHour.length; i++) {
			// 時分がすべて0(未入力)なのでチェックしない
			if (aryStartTimeHour[i] == 0 && aryStartTimeMinute[i] == 0 && aryEndTimeHour[i] == 0
					&& aryEndTimeMinute[i] == 0) {
				continue;
			}
			if (startTimeHour > aryStartTimeHour[i]) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TIME_OUT_CHECK, errMes);
				return;
			} else if (startTimeHour == aryStartTimeHour[i]) {
				if (startTimeMinute > aryStartTimeMinute[i]) {
					mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TIME_OUT_CHECK, errMes);
					return;
				}
			}
			if (endTimeHour < aryEndTimeHour[i]) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TIME_OUT_CHECK, errMes);
				return;
			} else if (endTimeHour == aryEndTimeHour[i]) {
				if (endTimeMinute < aryEndTimeMinute[i]) {
					mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TIME_OUT_CHECK, errMes);
					return;
				}
			}
		}
		// 休憩時間重複チェック
		for (int i = 0; i < aryStartTimeHour.length - 2; i++) {
			// 時分がすべて0(未入力)なのでチェックしない
			if (aryStartTimeHour[i] == 0 && aryStartTimeMinute[i] == 0 && aryEndTimeHour[i] == 0
					&& aryEndTimeMinute[i] == 0) {
				continue;
			}
			for (int j = i; j < aryStartTimeHour.length - 2; j++) {
				if (i == j) {
					continue;
				}
				// 時分がすべて0(未入力)なのでチェックしない
				if (aryStartTimeHour[j] == 0 && aryStartTimeMinute[j] == 0 && aryEndTimeHour[j] == 0
						&& aryEndTimeMinute[j] == 0) {
					continue;
				}
				if (aryEndTimeHour[i] > aryStartTimeHour[j]) {
					mospParams.addErrorMessage(TimeMessageConst.MSG_REST_GOING_OUT_CHECK, errMes);
					return;
				} else if (aryEndTimeHour[i] == aryStartTimeHour[j]) {
					if (aryEndTimeMinute[i] > aryStartTimeMinute[j]) {
						mospParams.addErrorMessage(TimeMessageConst.MSG_REST_GOING_OUT_CHECK, errMes);
						return;
					}
				}
			}
		}
		// デフォルト時刻取得
		Date defaultTime = DateUtility.getDefaultTime();
		// 時短勤務時刻取得
		int short1StartHour = getInt(vo.getTxtShort1StartHour());
		int short1StartMinute = getInt(vo.getTxtShort1StartMinute());
		int short1EndHour = getInt(vo.getTxtShort1EndHour());
		int short1EndMinute = getInt(vo.getTxtShort1EndMinute());
		int short2StartHour = getInt(vo.getTxtShort2StartHour());
		int short2StartMinute = getInt(vo.getTxtShort2StartMinute());
		int short2EndHour = getInt(vo.getTxtShort2EndHour());
		int short2EndMinute = getInt(vo.getTxtShort2EndMinute());
		Date short1StartTime = TimeUtility.getDateTime(short1StartHour, short1StartMinute);
		Date short1EndTime = TimeUtility.getDateTime(short1EndHour, short1EndMinute);
		Date short2StartTime = TimeUtility.getDateTime(short2StartHour, short2StartMinute);
		Date short2EndTime = TimeUtility.getDateTime(short2EndHour, short2EndMinute);
		boolean isShort1Set = short1StartTime.equals(defaultTime) == false
				|| short1EndTime.equals(defaultTime) == false;
		boolean isShort2Set = short2StartTime.equals(defaultTime) == false
				|| short2EndTime.equals(defaultTime) == false;
		// 時短時間1確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort1Set) {
			// 時短勤務1開始時刻と始業時刻を比較
			if (short1StartTime.equals(startTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1TimeBoundary(mospParams);
			}
			// 時短勤務1終了時刻と終業時刻の前後を確認
			if (short1EndTime.after(endTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1OutOfWorkTime(mospParams);
			}
			// 時短時間1開始終了前後確認
			if (short1EndTime.after(short1StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1EndBeforeStart(mospParams);
			}
		}
		// 時短時間2確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort2Set) {
			// 時短勤務2終了時刻と終業時刻を比較
			if (short2EndTime.equals(endTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2TimeBoundary(mospParams);
			}
			// 時短勤務2開始時刻と始業時刻の前後を確認
			if (short2StartTime.before(startTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2OutOfWorkTime(mospParams);
			}
			// 時短時間2開始終了前後確認
			if (short2EndTime.after(short2StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2EndBeforeStart(mospParams);
			}
		}
		// 時短勤務区分の組合せを確認
		if (isShort1Set && isShort2Set && vo.getPltShort1Type().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY) == false
				&& vo.getPltShort2Type().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortTypePair(mospParams);
		}
		
		// 午前/午後休重複チェック
		// 今後必要になる可能性あり
		/*
		int i = aryStartTimeHour.length - 2;
		if (aryEndTimeHour[i] > aryStartTimeHour[i + 1]) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_HARF_REST_CHECK, null, null);
			return;
		} else if (aryEndTimeHour[i] == aryStartTimeHour[i + 1]) {
			if (aryEndTimeMinute[i] > aryStartTimeMinute[i + 1]) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_HARF_REST_CHECK, null, null);
				return;
			}
		}
		*/
		return;
	}
	
	/**
	 * 時刻を取得する。
	 * 24時を超えた場合、そのまま表示する。
	 * @param hour 時間
	 * @param minute 分
	 * @return フォーマットされた時間
	 * @throws MospException 例外発生時
	 */
	protected Date getDefaultTime(String hour, String minute) throws MospException {
		// 勤怠自動計算クラス
		AttendanceCalcBeanInterface calc = time().attendanceCalc();
		// 基準日取得
		Date defaultDate = DateUtility.getDefaultTime();
		return calc.getAttendanceTime(defaultDate, hour, minute);
	}
	
	private int getMinute(String startHour, String startMinute, String endHour, String endMinute) {
		return getMinute(Integer.parseInt(startHour), Integer.parseInt(startMinute), Integer.parseInt(endHour),
				Integer.parseInt(endMinute));
	}
	
	private int getMinute(int startHour, int startMinute, int endHour, int endMinute) {
		return getMinute(startHour * TimeConst.CODE_DEFINITION_HOUR + startMinute, endHour
				* TimeConst.CODE_DEFINITION_HOUR + endMinute);
	}
	
	private int getMinute(int startMinute, int endMinute) {
		int minute = endMinute - startMinute;
		if (minute >= 0) {
			return minute;
		}
		return 0;
	}
	
	private Date getTimestamp(int minute) throws MospException {
		return DateUtility.addMinute(DateUtility.getDefaultTime(), minute);
	}
	
	private String getWorkTypeItemHour(Date time) throws MospException {
		return DateUtility.getStringHour(time, DateUtility.getDefaultTime());
	}
	
	/**
	 * 数値を取得する(String→int)。<br>
	 * 数値の取得に失敗した場合は、0を返す。<br>
	 * @param value 値(String)
	 * @return 値(int)
	 */
	@Override
	protected int getInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Throwable e) {
			return 0;
		}
	}
	
}
