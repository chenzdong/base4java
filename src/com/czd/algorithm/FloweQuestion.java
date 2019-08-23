package com.czd.algorithm;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 水仙花数问题
 * 问题描述:打印出所有的 "水仙花数 "
 * 思路：水仙花数：是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
 * @author: czd
 * @create: 2018/11/20 16:46
 */
public class FloweQuestion {
    public static void main(String[] args) {
        for (int i = 100; i < 1000; i++) {
            if (isFlower(i)) {
                System.out.println(i);
            }
        }
    }
    private static boolean isFlower(int num) {
        int single = num % 10;
        int ten = num/10 % 10;
        int hundred = num / 100;
        int total = single*single*single + ten*ten*ten + hundred*hundred*hundred;
        if (total == num) {
            return true;
        }
        return false;
    }
}
