/**
 *
 */
package jp.mosp.framework.xml;

import java.util.List;

/**
 * @author yoshida
 *
 */
public interface FileFinderInterface {
	
	/**
	 * MosP設定ファイルパスリストを取得する。<br>
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定ファイルパスリスト
	 */
	public List<String> getPathList(String docBase);
	
	/**
	 * MosP設定情報ファイルパスのリストを取得する。<br>
	 * 下位ディレクトリ参照要の場合、一つ下のディレクトリのみを参照する。<br>
	 * @param dirPath MosP設定情報ファイルが配置されている実際のパス
	 * @param containDir 下位ディレクトリ参照要否(true：要、false：不要)
	 * @return MosP設定情報ファイルパスリスト
	 */
	public List<String> getFilePathList(String dirPath, boolean containDir);
	
}
