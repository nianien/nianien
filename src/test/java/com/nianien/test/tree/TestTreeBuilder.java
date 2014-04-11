package com.nianien.test.tree;

import com.nianien.core.collection.map.KeyValue;
import com.nianien.core.function.Selector;
import com.nianien.core.tree.TreeBuilder;
import com.nianien.core.tree.TreeNode;
import com.nianien.core.tree.TreeNodeHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author skyfalling
 */
public class TestTreeBuilder {

    @Test
    public void test() {


        Map map = new HashMap();
        map.put(new KeyValue(101, "娱乐综合"), new KeyValue(1, "音乐影视"));
        map.put(new KeyValue(102, "在线音乐"), new KeyValue(1, "音乐影视"));
        map.put(new KeyValue(103, "宽带电影"), new KeyValue(1, "音乐影视"));
        map.put(new KeyValue(104, "视频短片"), new KeyValue(1, "音乐影视"));
        map.put(new KeyValue(105, "网络电视"), new KeyValue(1, "音乐影视"));


        map.put(new KeyValue(201, "笑话"), new KeyValue(2, "休闲娱乐"));
        map.put(new KeyValue(202, "图片"), new KeyValue(2, "休闲娱乐"));
        map.put(new KeyValue(203, "动漫"), new KeyValue(2, "休闲娱乐"));
        map.put(new KeyValue(204, "flash"), new KeyValue(2, "休闲娱乐"));

        map.put(new KeyValue(20211, "笑话"), new KeyValue(202, "休闲娱乐"));
        map.put(new KeyValue(20301, "图片"), new KeyValue(203, "休闲娱乐"));
        map.put(new KeyValue(20415, "动漫"), new KeyValue(204, "休闲娱乐"));
        map.put(new KeyValue(206, "flash"), new KeyValue(2, "休闲娱乐"));

        TreeNode<KeyValue> tree = TreeBuilder.buildTree(map/*, new Identifier<Integer, KeyValue<Integer, String>>() {
            @Override
            public Integer id(KeyValue<Integer, String> node) {
                return node.getKey();
            }

        }*/);
        TreeBuilder.traversal(tree, new TreeNodeHandler() {
            @Override
            public void handle(TreeNode node) {
                System.out.println(node);
            }
        });
        TreeBuilder.minimize(tree, new Selector<TreeNode<KeyValue>>() {
            @Override
            public boolean select(TreeNode<KeyValue> node) {
                if (node.value() != null) {
                    KeyValue keyValue = node.value();
                    Object key = keyValue.getKey();
                    return key.equals(20415) || key.equals(105);
                }
                return false;
            }
        });
       final List<TreeNode> treeNodes=new ArrayList<TreeNode>();
        TreeBuilder.traversal(tree, new TreeNodeHandler() {
            @Override
            public void handle(TreeNode node) {
                treeNodes.add(node);
                System.out.println(node);
            }
        });

        assertTrue(treeNodes.size()==6);
    }
}
