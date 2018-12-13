/**
 *
 */
package jp.mosp.framework.xml;

/**
 * @author yoshida
 *
 */
public enum PropertyTag {
	
	/**
	 * アプリケーション要素
	 */
	APPLICATION {
		
		@Override
		public String getName() {
			return "Application";
		}
		
	},
	
	/**
	 * コントローラー要素
	 */
	CONTROLLER {
		
		@Override
		public String getName() {
			return "Controller";
		}
		
	},
	
	/**
	 * モデル要素
	 */
	MODEL {
		
		@Override
		public String getName() {
			return "Model";
		}
		
	},
	
	/**
	 * メッセージ要素
	 */
	MESSAGE {
		
		@Override
		public String getName() {
			return "Message";
		}
		
	},
	
	/**
	 * 文言要素
	 */
	NAMING {
		
		@Override
		public String getName() {
			return "Naming";
		}
		
	},
	
	/**
	 * コード要素
	 */
	CODE {
		
		@Override
		public String getName() {
			return "Code";
		}
		
	},
	
	/**
	 * ロール要素
	 */
	ROLE {
		
		@Override
		public String getName() {
			return "Role";
		}
		
	},
	
	/**
	 * メインメニュー要素
	 */
	MAIN_MENU {
		
		@Override
		public String getName() {
			return "MainMenu";
		}
		
	},
	
	/**
	 * アドオン要素
	 */
	ADD_ON {
		
		@Override
		public String getName() {
			return "Addon";
		}
		
	},
	
	/**
	 * 規約要素
	 */
	CONVENTION {
		
		@Override
		public String getName() {
			return "Convention";
		}
		
	},
	
	/**
	 * 表示設定要素
	 */
	VIEW_CONFIG {
		
		@Override
		public String getName() {
			return "ViewConfig";
		}
	},
	
	/**
	 * 未定義要素
	 */
	UNKNOWN {
		
		@Override
		public String getName() {
			return "";
		}
		
	},
	
	;
	
	/**
	 * @return tagName 要素名
	 */
	public abstract String getName();
	
	/**
	 * @param tagName 要素名
	 * @return
	 */
	public static PropertyTag get(String tagName) {
		for (PropertyTag tag : PropertyTag.values()) {
			if (tag.getName().equals(tagName)) {
				return tag;
			}
		}
		return UNKNOWN;
	}
	
	
	/**
	 * ドキュメントルート要素の要素名。
	 */
	static final String	TAG_DOCUMENT	= "MosP";
	
}
