/**
 *
 */
package jp.mosp.framework.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yoshida
 *
 */
public class FileFinder implements FileFinderInterface {
	
	/**
	 * MosP設定情報ファイル格納ディレクトリパス。
	 */
	private static final String	PATH_PROPERTY			= "/WEB-INF/xml";
	
	/**
	 * MosPアドオン設定情報ファイル格納ディレクトリパス。
	 */
	private static final String	PATH_ADDON_PROPERTY		= "/addon/";
	
	/**
	 * MosPユーザ作成設定情報ファイル格納ディレクトリパス。
	 */
	private static final String	PATH_USER_PROPERTY		= "/user/";
	
	/**
	 * MosP設定情報ファイル名。
	 */
	private static final String	FILE_PROPERTY			= "mosp.xml";
	
	/**
	 * MosP設定情報ファイル接尾辞。
	 */
	private static final String	SUFFIX_PROPERTY_FILE	= ".xml";
	
	
	@Override
	public List<String> getPathList(String docBase) {
		// MosP設定ファイルパスリスト準備
		List<String> list = new ArrayList<String>();
		// XMLディレクトリパス取得
		String xmlDirPath = docBase + PATH_PROPERTY;
		// XMLディレクトリパス直下のXMLファイルパスリストを取得
		List<String> propertyList = getFilePathList(xmlDirPath, false);
		Collections.sort(propertyList);
		// MosP設定ファイルパス設定
		File mospXml = new File(xmlDirPath, FILE_PROPERTY);
		if (mospXml.exists()) {
			// 有ったら追加する。
			list.add(mospXml.getAbsolutePath());
		}
		list.addAll(propertyList);
		// アドオン設定ファイルパス設定
		list.addAll(getFilePathList(xmlDirPath + PATH_ADDON_PROPERTY, true));
		// ユーザ作成設定ファイルパス設定
		list.addAll(getFilePathList(xmlDirPath + PATH_USER_PROPERTY, false));
		return list;
	}
	
	@Override
	public List<String> getFilePathList(String dirPath, boolean containDir) {
		// ファイルパスリスト準備
		List<String> propertyList = new ArrayList<String>();
		// ファイルリスト取得
		File[] files = new File(dirPath).listFiles();
		// ファイルリスト確認
		if (files == null) {
			return propertyList;
		}
		List<String> xmlFileList = new ArrayList<String>();
		// ファイル毎に処理
		for (File file : files) {
			// ディレクトリ確認
			if (file.isDirectory() && containDir) {
				// 下位ディレクトリ参照(ファイルパスリストに追加)
				propertyList.addAll(getFilePathList(file.getPath(), containDir));
			}
			// ファイル確認
			if (file.isFile() == false) {
				continue;
			}
			// ファイル名取得及び確認
			String fileName = file.getName();
			if (fileName.endsWith(SUFFIX_PROPERTY_FILE) == false || fileName.contains(FILE_PROPERTY)) {
				continue;
			}
			// ファイルパスリストに追加
			xmlFileList.add(file.getPath());
		}
		// ソート
		Collections.sort(propertyList);
		Collections.sort(xmlFileList);
		// ファイルを後で追加。
		propertyList.addAll(xmlFileList);
		return propertyList;
	}
	
}
