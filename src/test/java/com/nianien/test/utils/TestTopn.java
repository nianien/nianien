package com.nianien.test.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.nianien.core.io.Files;
import com.nianien.core.util.PriorityHeap;

public class TestTopn {

    @Test
    public void test() throws IOException {
        List<String> list = Files.readLines(new File("all200000.txt"));
        PriorityHeap<String> heap = new PriorityHeap<String>(list.size(), String.class);

        for (String s : list) {
            heap.add(s);
        }
        String[] arr = heap.popAll();

//		System.out.println(Arrays.toString(arr));
        Collections.sort(list);
//		System.out.println(Arrays.toString(list.toArray(new String[0])));
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(arr.length - i - 1).equals(arr[i])) {
                n++;
            }
        }

        System.out.println(n + "/" + list.size());
    }
}
