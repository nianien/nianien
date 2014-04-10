package com.nianien.core.tree;

/**
 * 处理树节点对象的接口声明
 *
 * @author skyfalling
 */
public interface TreeNodeHandler<T> {

    /**
     * 处理树节点
     *
     * @param node
     */
    void handle(TreeNode<T> node);
}
