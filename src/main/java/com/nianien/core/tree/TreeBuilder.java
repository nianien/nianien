package com.nianien.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 构造树的工具类
 *
 * @author skyfalling
 */
public class TreeBuilder {

    /**
     * 根据Map对象的映射关系构建树形结构<br/>
     * 其中,Key值表示子节点数据,Value值表示父节点数据<br/>
     * 树结构采用孩子兄弟链表表示法<br/>
     *
     * @param relation   父子关系图,key表示当前节点数据,value表示父节点数据
     * @param identifier 获取节点数据标识以确定节点在树形结构的位置.如未实现,请调用{@link #buildTree(java.util.Map)}方法
     * @param <T>
     * @return 返回一个虚拟根节点作为实际数据的根节点的根节点
     */
    public static <T> TreeNode buildTree(Map<T, T> relation, Identifier identifier) {
        // 虚拟根节点
        TreeNode root = new TreeNode(null);
        // 全部节点的映射表
        Map<Object, TreeNode> trees = new HashMap<Object, TreeNode>();
        for (Entry<T, T> entry : relation.entrySet()) {
            T p = entry.getValue();
            T c = entry.getKey();
            TreeNode parent = putIfAbsent(trees, p, identifier);
            TreeNode child = putIfAbsent(trees, c, identifier);
            parent.addChild(child);
        }
        // 父节点为空的节点均为根节点的孩子节点,即实际的一级节点
        for (TreeNode node : trees.values()) {
            if (node.parent() == null) {
                root.addChild(node);
            }
        }
        return root;
    }

    /**
     * 根据Map对象的映射关系构建树形结构<br/>
     * 其中,Key值表示子节点数据,Value值表示父节点数据<br/>
     * 树结构采用孩子兄弟链表表示法<br/>
     * 注意:这里将根据节点对象的hashCode确定其在树形结构中的位置,如有必要,请重写{@link #hashCode()}方法
     *
     * @param relation 父子关系图,key表示当前节点数据,value表示父节点数据
     * @param <T>
     * @return 返回一个虚拟根节点作为实际数据的根节点的根节点
     */
    public static <T> TreeNode buildTree(Map<T, T> relation) {
        return buildTree(relation, new Identifier() {
            @Override
            public Object id(Object node) {
                return node == null ? null : node.hashCode();
            }
        });
    }


    /**
     * 最小化树结构,只保留符合条件的节点及其父节点<br>
     * 如果某个节点满足选择条件,那么从该节点通往根节点的路径将被保留,到叶节点的路径被裁剪
     *
     * @param tree
     * @param selector
     * @return
     */
    public static boolean minimize(TreeNode tree, TreeNodeSelector selector) {
        //采取后根顺序从底自上进行最小化裁剪
        if (tree == null)
            return false;
        TreeNode child = tree.firstChild();
        boolean flag = false;
        while (child != null) {
            //裁剪当前孩子节点
            flag |= minimize(child, selector);
            //裁剪下一个孩子节点
            child = child.nextBrother();
        }

        //判断当前节点是否需要裁剪
        flag |= selector.select(tree);
        if (!flag && tree.parent() != null) {
            //当前节点需要被裁剪
            TreeNode parent = tree.parent();
            if (parent.firstChild() == tree) {
                //如果当前节点是第一个孩子节点,则只需将当前节点的下一个兄弟节点前移即完成裁剪
                parent.firstChild(tree.nextBrother());
            } else {
                //如果不是第一个孩子节点,则需要从第一个孩子节点进行遍历
                // 将当前节点的前一个兄弟节点连接后一个兄弟节点完成裁剪
                child = tree.parent().firstChild();
                while (child != null && child.nextBrother() != tree) {
                    child = child.nextBrother();
                }
                if (child != null) {
                    child.nextBrother(tree.nextBrother());
                }
            }
        }
        return flag;
    }


    /**
     * 层次遍历树
     *
     * @param tree
     * @param handler
     */
    public static void traversal(TreeNode tree, TreeNodeHandler handler) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(tree);
        while (!list.isEmpty()) {
            TreeNode node = list.remove(0);
            handler.handle(node);
            if (node.firstChild() != null) {
                list.add(node.firstChild());
            }
            TreeNode brother = node.nextBrother();
            while (brother != null) {
                handler.handle(brother);
                if (brother.firstChild() != null) {
                    list.add(brother.firstChild());
                }
                brother = brother.nextBrother();
            }
        }
    }

    /**
     * 将指定数据的节点加入映射表中,如果已经存在,则不再加入
     *
     * @param trees
     * @param value
     * @param identifier
     * @param <T>
     * @return
     */
    private static <T> TreeNode putIfAbsent(Map<Object, TreeNode> trees, T value, Identifier identifier) {
        Object key = identifier.id(value);
        TreeNode node = trees.get(key);
        if (node == null) {
            // 如果父节点不存在,创建父节点
            node = new TreeNode(value);
            // 将该节点放入映射表中
            trees.put(key, node);
        } else if (value != null) {
            node.value(value);
        }
        return node;
    }


}
