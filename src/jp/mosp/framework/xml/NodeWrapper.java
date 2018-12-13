/**
 *
 */
package jp.mosp.framework.xml;

import java.io.Serializable;

import org.w3c.dom.Node;

/**
 * @author yoshida
 *
 */
public final class NodeWrapper implements Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1518576038024376804L;
	
	final String				path;
	
	final int					index;
	
	private Node				node;
	
	
	/**
	 * @param path
	 * @param node
	 * @param index
	 */
	public NodeWrapper(String path, Node node, int index) {
		this.path = path;
		this.node = node;
		this.index = index;
	}
	
	/**
	 * @return node
	 */
	public Node getNode() {
		return node.cloneNode(true);
	}
	
}
