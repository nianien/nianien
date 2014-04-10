package com.nianien.core.tree;

/**
 * 树节点的选择接口
 *
 * @author skyfalling
 */
public interface TreeNodeSelector<T> {

    /**
     * 判断当前节点是否满足选择条件
     *
     * @param node
     * @return
     */
    boolean select(TreeNode<T> node);
}
