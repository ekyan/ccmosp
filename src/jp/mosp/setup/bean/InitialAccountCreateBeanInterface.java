/**
 * 
 */
package jp.mosp.setup.bean;

import jp.mosp.framework.base.MospException;
import jp.mosp.setup.dto.InitialAccountParameterInterface;

/**
 * 初期アカウント登録インターフェース。
 */
public interface InitialAccountCreateBeanInterface {
	
	/**
	 * @return インスタンス
	 */
	public InitialAccountParameterInterface getInitParameter();
	
	/**
	 * @param parameter パラメータ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void execute(InitialAccountParameterInterface parameter) throws MospException;
	
}
