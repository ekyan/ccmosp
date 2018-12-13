package jp.mosp.setup.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * XMLファイル作成画面のVO。<br>
 */
public class XmlCreateVo extends PlatformVo {
	
	/**
	 * シリアルナンバー。<br>
	 */
	private static final long	serialVersionUID	= -4010891654516370388L;
	
	/**
	 * サーバ名。<br>
	 */
	private String				txtServer;
	
	/**
	 * ポート番号。<br>
	 */
	private String				txtPort;
	
	/**
	 * データベース名。<br>
	 */
	private String				txtDbName;
	
	/**
	 * ロール名。<br>
	 */
	private String				txtRoleName;
	
	/**
	 * ロールパスワード。<br>
	 */
	private String				txtRolePw;
	
	
	/**
	 * @return txtServer
	 */
	public String getTxtServer() {
		return txtServer;
	}
	
	/**
	 * @param txtServer セットする txtServer
	 */
	public void setTxtServer(String txtServer) {
		this.txtServer = txtServer;
	}
	
	/**
	 * @return txtPort
	 */
	public String getTxtPort() {
		return txtPort;
	}
	
	/**
	 * @param txtPort セットする txtPort
	 */
	public void setTxtPort(String txtPort) {
		this.txtPort = txtPort;
	}
	
	/**
	 * @return txtDbName
	 */
	public String getTxtDbName() {
		return txtDbName;
	}
	
	/**
	 * @param txtDbName セットする txtDbName
	 */
	public void setTxtDbName(String txtDbName) {
		this.txtDbName = txtDbName;
	}
	
	/**
	 * @return txtRoleName
	 */
	public String getTxtRoleName() {
		return txtRoleName;
	}
	
	/**
	 * @param txtRoleName セットする txtRoleName
	 */
	public void setTxtRoleName(String txtRoleName) {
		this.txtRoleName = txtRoleName;
	}
	
	/**
	 * @return txtRolePw
	 */
	public String getTxtRolePw() {
		return txtRolePw;
	}
	
	/**
	 * @param txtRolePw セットする txtRolePw
	 */
	public void setTxtRolePw(String txtRolePw) {
		this.txtRolePw = txtRolePw;
	}
	
}
