package com.czd.algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 质数分解问题
 *
 * @author: czd
 * @create: 2018/11/22 10:37
 */
public class factorQuetion {
    public static void main(String[] args) {
        int num = 60;
        List<Integer> list = getFactor(num);
        list.stream().forEach(integer -> System.out.println(integer));
    }
    private static List<Integer> getFactor(int num) {
        List<Integer> list = new ArrayList<>();
        int k = 2;
        while (k <= num) {
            if (num % k == 0) {
                list.add(Integer.valueOf(k));
                num /= k;
                if (num == 1) {
                    break;
                }
            } else {
                k++;
            }
        }
        return list;
    }
}
