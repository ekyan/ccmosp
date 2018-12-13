/**
 * 
 */
package jp.mosp.platform.bean.exporter;

import jp.mosp.framework.base.MospException;

/**
 * テキスト出力処理管理インターフェース。
 */
public interface TextExportManagerBeanInterface {
	
	/**
	 * テキスト出力処理
	 * @param compiler テキスト出力用編集処理オブジェクト
	 * @throws MospException 例外処理発生
	 */
	public void delivery(TextCompilerInterface compiler) throws MospException;
	
}
