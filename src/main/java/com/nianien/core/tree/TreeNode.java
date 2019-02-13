package com.nianien.core.tree;

/**
 * 树形结构节点定义<br/>
 * 采用孩子兄弟链表表示法
 *
 * @param <T>
 */
public class TreeNode<T> {

    /**
     * 父节点
     */
    private TreeNode parent;
    /**
     * 第一个孩子节点
     */
    private TreeNode firstChild;
    /**
     * 布尔值标记
     */
    private boolean exist;
    /**
     * 下一个兄弟节点
     */
    private TreeNode nextBrother;
    /**
     * 节点数据
     */
    private T value;

    /**
     * 构造方法,指定节点数据
     *
     * @param value
     */
    public TreeNode(T value) {
        this(value, true);
    }

    /**
     * 构造方法
     *
     * @param value 节点数据
     * @param exist 是否标记节点数据
     */
    public TreeNode(T value, boolean exist) {
        this.value = value;
        this.exist = exist;
    }

    /**
     * 添加兄弟节点
     *
     * @param node
     */
    public void addBrother(TreeNode node) {
        if (node == null)
            return;
        TreeNode brother = this.nextBrother;
        if (brother != null) {
            while (brother.nextBrother != null) {
                brother = brother.nextBrother;
            }
            brother.nextBrother = node;
        } else {
            this.nextBrother = node;
        }
        node.parent = this.parent;
    }

    /**
     * 添加孩子节点
     *
     * @param node
     */
    public void addChild(TreeNode node) {
        if (node == null)
            return;
        TreeNode child = this.firstChild;
        if (child != null) {
            while (child.nextBrother != null) {
                child = child.nextBrother;
            }
            child.nextBrother = node;
        } else {
            this.firstChild = node;
        }
        node.parent = this;
    }

    /**
     * 如果节点数据相等,则认为是同一节点
     */
    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof TreeNode && this.value != null) {
            this.value.equals(((TreeNode) other).value);
        }
        return false;
    }

    /**
     * 获取父节点
     *
     * @return parent
     */
    public TreeNode parent() {
        return this.parent;
    }

    /**
     * 获取第一个孩子节点
     *
     * @return children
     */
    public TreeNode firstChild() {
        return this.firstChild;
    }

    /**
     * 设置第一个孩子节点
     *
     * @param firstChild
     */
    public void firstChild(TreeNode firstChild) {
        this.firstChild = firstChild;
        if (firstChild != null)
            firstChild.parent = this;
    }

    /**
     * 最后一个兄弟节点
     *
     * @return
     */
    public TreeNode lastBrother() {
        TreeNode lastBrother = this.nextBrother;
        if (lastBrother != null) {
            while (lastBrother.nextBrother != null) {
                lastBrother = lastBrother.nextBrother;
            }
        }
        return lastBrother;
    }

    /**
     * 最后一个孩子节点
     *
     * @return
     */
    public TreeNode lastChild() {
        TreeNode lastChild = this.firstChild;
        if (lastChild != null) {
            while (lastChild.nextBrother != null) {
                lastChild = lastChild.nextBrother;
            }
        }
        return lastChild;
    }

    /**
     * 设置节点是否存在
     *
     * @param value
     */
    public void exist(boolean value) {
        this.exist = value;
    }

    /**
     * 节点是否存在
     *
     * @return
     */
    public boolean exist() {
        return exist;
    }

    /**
     * 获取下一个兄弟节点
     *
     * @return
     */
    public TreeNode nextBrother() {
        return nextBrother;
    }

    /**
     * 设置下一个兄弟节点
     *
     * @param nextBrother
     */
    public void nextBrother(TreeNode nextBrother) {
        this.nextBrother = nextBrother;
        if (nextBrother != null)
            nextBrother.parent = this.parent;
    }

    /**
     * 获取节点数据
     *
     * @return
     */
    public T value() {
        return value;
    }

    /**
     * 设置节点数据
     *
     * @param value
     */
    public void value(T value) {
        this.value = value;
    }

    /**
     * 显示节点数据内容
     */
    public String toString() {
        return value + "[" + exist + "]";
    }

}
