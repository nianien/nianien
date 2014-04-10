package com.nianien.core.xml;

/**
 * XMLNode节点选择器接口
 * @author skyfalling
 *
 */
public interface NodeSelector {

	/**
	 * 选择节点Node
	 * @param node 
	 * @return 如果符合条件返回true,否则返回false
	 */
	boolean select(XMLNode node);
}
