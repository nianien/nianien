package com.nianien.test.tree;

import com.nianien.core.tree.TreeNode;
import com.nianien.core.tree.TreeNodeHandler;
import com.nianien.core.tree.TrieTree;
import com.nianien.core.util.TimeCounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestTrieTree {

    public static void main(String[] args) throws Exception {

        List<String> alist = getData("all200000.txt");
        testSuggest("155", alist);
        List<String> slist = getData("search20000.txt");
        testSearch(alist, slist);
        testDisplay();
    }

    public static void testDisplay() {
        TreeNodeHandler h = new TreeNodeHandler() {

            @Override
            public void handle(TreeNode node) {
                System.out.println(node);

            }
        };
        TrieTree tree = new TrieTree();
        tree.insert("a");
        tree.insert("b");
        tree.insert("c");
        tree.insert("ab");
        tree.insert("bd");
        tree.insert("abc");
        tree.insert("abcd");
        tree.delete("a");
        tree.delete("ab");
        tree.delete("abc");
        tree.delete("abcd");
        System.out.println(tree.display());
        System.out.println("=======================");
        tree.visit(h);
        System.out.println("=======================");
        tree.optimize();
        System.out.println(tree.display());
        System.out.println("=======================");
        tree.visit(h);
    }

    public static List<String> getData(String file) throws Exception {
        file = TestTrieTree.class.getClassLoader().getResource(file).getFile();
        System.out.println(file);
        List<String> list = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        return list;
    }

    public static void testSuggest(String data, List<String> list)
            throws Exception {
        TrieTree tree = new TrieTree();
        for (String str : list) {
            tree.insert(str);
        }
        TimeCounter tc = new TimeCounter();
        tc.start();
        int i = 0;
        for (String str : list) {
            if (!data.equals(str) && str.startsWith(data))
                i++;
        }
        tc.stop();
        System.out.println("list used: " + tc.timePassed() + " ms, suggest: "
                + i);

        tc.start();
        List<String> suggest = tree.suggest(data);
        tc.stop();
        System.out.println("trie tree used: " + tc.timePassed()
                + " ms, suggest: " + suggest.size());

        tree.optimize();
        tc.start();
        suggest = tree.suggest(data);
        tc.stop();
        System.out.println("optimized tree used: " + tc.timePassed()
                + " ms, suggest: " + suggest.size());

    }

    public static void testSearch(List<String> alist, List<String> slist)
            throws Exception {

        TrieTree tree = new TrieTree();
        HashMap<String, String> map = new HashMap<String, String>();
        for (String s : alist) {
            tree.insert(s);
            map.put(s, s);
        }

        TimeCounter tc = new TimeCounter();
        // map搜索
        System.out.println("==>search by map...");
        int i = 0;
        tc.start();
        for (String s : slist) {
            if (map.containsKey(s))
                i++;
        }
        tc.stop();
        System.out.println("map used :" + tc.timePassed() + " ms, find: " + i);


        // list搜索
        System.out.println("==>search by list...");
        i = 0;
        tc.start();
        for (String s : slist) {
            if (alist.contains(s))
                i++;
        }
        tc.stop();
        System.out.println("list used: " + tc.timePassed() + " ms, find: " + i);


        // trie tree搜索
        System.out.println("==>search by tree...");
        i = 0;
        tc.start();
        for (String s : slist) {
            if (tree.find(s))
                i++;
        }
        tc.stop();
        System.out.println("trie tree used: " + tc.timePassed() + " ms, find: "
                + i);

        // 优化后的树
        tree.optimize();
        System.out.println("==>search by optimized tree...");
        i = 0;
        tc.start();
        for (String s : slist) {
            if (tree.find(s))
                i++;
        }
        tc.stop();
        System.out.println("optimized tree used: " + tc.timePassed()
                + " ms, find: " + i);


        // 再次优化，应无变化
        tree.optimize();
        System.out.println("==>search by optimized tree...");
        i = 0;
        tc.start();
        for (String s : slist) {
            if (tree.find(s))
                i++;
        }
        tc.stop();
        System.out.println("optimized again  used: " + tc.timePassed()
                + " ms, find: " + i);

    }
}
