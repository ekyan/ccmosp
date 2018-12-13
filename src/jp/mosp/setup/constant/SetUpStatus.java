/**
 * 
 */
package jp.mosp.setup.constant;

/**
 * セットアップステータスクラス。
 */
public enum SetUpStatus {
	
	/**
	 * DBが存在しない。
	 */
	NULL,
	
	/**
	 * DBは存在しているが、初期アカウントが存在しない。
	 */
	EMPTY,
	
	/**
	 * エラーと判断された場合。
	 */
	ERROR,
	
	/**
	 * セットアップ完了。
	 */
	ALREADY;
	
}
