/**
 * 
 */
package jp.mosp.setup.bean.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.system.UserMasterRegistBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.constant.PlatformMessageConst;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.setup.bean.InitialAccountCreateBeanInterface;
import jp.mosp.setup.dto.InitialAccountParameterInterface;
import jp.mosp.setup.dto.impl.InitialAccountParameter;

/**
 * 初期アカウント登録クラス。
 */
public class InitialAccountCreateBean extends PlatformBean implements InitialAccountCreateBeanInterface {
	
	@Override
	public void initBean() {
	}
	
	public InitialAccountParameterInterface getInitParameter() {
		return new InitialAccountParameter();
	}
	
	public void execute(InitialAccountParameterInterface parameter) throws MospException {
		// 人事マスタ登録
		insertHuman(parameter);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 社員コードから個人IDを取得して設定
		String personalId = getPersonalId(parameter);
		if (personalId.isEmpty()) {
			return;
		}
		// 入社情報登録
		insertEntrance(parameter, personalId);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// ユーザマスタ登録
		insertUser(parameter, personalId);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 人事マスタ登録
	 * @param parameter パラメータ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insertHuman(InitialAccountParameterInterface parameter) throws MospException {
		HumanRegistBeanInterface register = (HumanRegistBeanInterface)createBean(HumanRegistBeanInterface.class);
		// DTOに設定
		HumanDtoInterface dto = register.getInitDto();
		dto.setActivateDate(getActivateDate(parameter));
		dto.setEmployeeCode(parameter.getEmployeeCode());
		dto.setLastName(parameter.getLastName());
		dto.setFirstName(parameter.getFirstName());
		dto.setLastKana(parameter.getLastKana());
		dto.setFirstKana(parameter.getFirstKana());
		dto.setWorkPlaceCode("");
		dto.setEmploymentContractCode("");
		dto.setSectionCode("");
		dto.setPositionCode("");
		// メールアドレスは未使用
		dto.setMail("");
		// 登録
		register.insert(dto);
	}
	
	/**
	 * @param parameter パラメータ
	 * @return 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getPersonalId(InitialAccountParameterInterface parameter) throws MospException {
		HumanReferenceBeanInterface human = (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
		return human.getPersonalId(parameter.getEmployeeCode(), getActivateDate(parameter));
	}
	
	/**
	 * 入社情報登録
	 * @param parameter パラメータ
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insertEntrance(InitialAccountParameterInterface parameter, String personalId) throws MospException {
		EntranceRegistBeanInterface register = (EntranceRegistBeanInterface)createBean(EntranceRegistBeanInterface.class);
		EntranceDtoInterface dto = register.getInitDto();
		// DTOに値をセットする。
		dto.setEntranceDate(parameter.getEntranceDate());
		dto.setPersonalId(personalId);
		// 登録
		register.insert(dto);
	}
	
	/**
	 * ユーザマスタ登録
	 * @param parameter パラメータ
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insertUser(InitialAccountParameterInterface parameter, String personalId) throws MospException {
		UserMasterRegistBeanInterface userMasterRegist = (UserMasterRegistBeanInterface)createBean(UserMasterRegistBeanInterface.class);
		// DTOに値をセットする。
		UserMasterDtoInterface dto = userMasterRegist.getInitDto();
		// ユーザID
		String userId = parameter.getUserId();
		dto.setUserId(userId);
		dto.setActivateDate(getActivateDate(parameter));
		// 社員コードから個人IDを取得して設定
		dto.setPersonalId(personalId);
		// デフォルトロールコード設定
		dto.setRoleCode(parameter.getRoleCode());
		// 無効フラグ設定(有効)
		dto.setInactivateFlag(MospConst.DELETE_FLAG_OFF);
		// 登録
		userMasterRegist.insert(dto);
		// ユーザパスワード情報
		initPassword(parameter);
	}
	
	/**
	 * パスワード登録
	 * @param parameter パラメータ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void initPassword(InitialAccountParameterInterface parameter) throws MospException {
		// ユーザパスワード情報
		UserPasswordRegistBeanInterface userPasswordRegist = (UserPasswordRegistBeanInterface)createBean(UserPasswordRegistBeanInterface.class);
		UserPasswordDtoInterface dto = userPasswordRegist.getInitDto();
		// VOの値をDTOに設定
		dto.setChangeDate(getActivateDate(parameter));
		String userId = parameter.getUserId();
		// ユーザID設定
		dto.setUserId(userId);
		PasswordCheckBeanInterface passwordCheck = (PasswordCheckBeanInterface)createBean(PasswordCheckBeanInterface.class);
		// 初期パスワード取得
		String initialPassword = passwordCheck.getInitialPassword(userId);
		// パスワード設定
		dto.setPassword(SeUtility.encrypt(SeUtility.encrypt(initialPassword)));
		// 登録
		userPasswordRegist.regist(dto);
	}
	
	/**
	 * 有効日取得
	 * @param parameter パラメータ
	 * @return 有効日が入力されている場合、その日付。<br>
	 * nullの場合、入社日が未来の場合、本日の日付を返す。<br>
	 * 其れ以外は入社日を返す。
	 */
	protected Date getActivateDate(InitialAccountParameterInterface parameter) {
		Date entranceDate = parameter.getEntranceDate();
		Date systemDate = DateUtility.getSystemDate();
		Date activateDate = parameter.getActivateDate();
		if (activateDate != null) {
			return activateDate;
		}
		// 入社日が未来の場合、本日にする。
		if (systemDate.compareTo(entranceDate) < 0) {
			return systemDate;
		}
		return entranceDate;
	}
	
	/**
	 * 登録失敗メッセージの設定。
	 */
	protected void addInsertFailedMessage() {
		String[] aryMeassage = { mospParams.getName("Insert") };
		mospParams.addMessage(PlatformMessageConst.MSG_PROCESS_FAILED, aryMeassage);
	}
	
}
