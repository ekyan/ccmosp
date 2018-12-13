/**
 * 
 */
package jp.mosp.setup.constant;

/**
 * セットアップに関する定数クラス。
 */
public final class SetUpConst {
	
	private SetUpConst() {
	}
	
	
	/**
	 * 接続失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_CONECTION				= "SUE001";
	
	/**
	 * SQL文を流す時のメッセージコード。<br>
	 */
	public static final String	MSG_SQL						= "SUE002";
	
	/**
	 * DB作成失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_CREATEDB				= "SUE003";
	
	/**
	 * ロール名入力チェック。<br>
	 */
	public static final String	MSG_INPUT_ROLE				= "SUE004";
	
	/**
	 * 既に存在時のメッセージコード。<br>
	 */
	public static final String	MSG_YET						= "SUE005";
	
	/**
	 * セッションが取得不可時のメッセージコード。
	 */
	public static final String	MSG_SESSION					= "SUE006";
	
	/**
	 * ファイルのエンコーディング指定。<br>
	 */
	public static final String	ENCODE_UTF_8				= "UTF-8";
	
	/**
	 * SQLファイルディレレクトリーパス。<br>
	 */
	public static final String	SQL_DIR						= "sql";
	
	/**
	 * SQLファイルの拡張子。<br>
	 */
	public static final String	SUFFIX_SQL_FILE				= ".sql";
	
	/**
	 * GRANTSQLファイルに含まれる文字。<br>
	 */
	public static final String	FILE_GRANT					= "grant";
	
	/**
	 * GRANT文に元々あるロール名。<br>
	 */
	public static final String	DEFAULT_ROLL_NAME			= "usermosp";
	
	/**
	 * DB接続設定XMLファイル。<br>
	 */
	public static final String	PATH_XML_FILE				= "WEB-INF/xml/user/user_connection.xml";
	
	/**
	 * PostgresSQL文字列。<br>
	 */
	public static final String	NAME_POSTGRES				= "postgres";
	
	/**
	 * Applicationキー:DefaultServerName
	 */
	public static final String	APP_DEFAULT_SERVER_NAME		= "DefaultServerName";
	
	/**
	 * Applicationキー:DefaultPort
	 */
	public static final String	APP_DEFAULT_PORT			= "DefaultPort";
	
	/**
	 * Applicationキー:DatabaseUrlPattern
	 */
	public static final String	APP_DATABASE_URL_PATTERN	= "DatabaseUrlPattern";
	
	/**
	 * Applicationキー:SetUpDatabase
	 */
	public static final String	APP_SETUP_DATABASE			= "SetUpDatabase";
	
	/**
	 * Applicationキー:InitUserRoleCode
	 */
	public static final String	APP_INIT_USER_ROLE_CODE		= "InitUserRoleCode";
	
}
