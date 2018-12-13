package jp.mosp.setup.base;

/**
 * データベース生成・削除時に発生する例外クラス。
 */
public class DbSetUpException extends RuntimeException {
	
	private static final long	serialVersionUID	= 6211450392469001086L;
	
	
	/**
	 * コンストラクタ。
	 * @param throwable 例外
	 */
	public DbSetUpException(Throwable throwable) {
		super(throwable);
	}
	
}
