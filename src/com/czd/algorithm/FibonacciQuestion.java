package com.czd.algorithm;

/**
 * Fibonacci问题
 * 问题描述：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少
 * 思路：1:2 2:2 3:4 4:6 5:10 6:16 n:n+n-1
 * @author: czd
 * @create: 2018/11/20 16:32
 */
public class FibonacciQuestion {
    public static void main(String[] args) {
        //n:第几个月
        int n = 10;
        System.out.println(getRabbit(n));
    }
    private static int getRabbit(int n) {
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return 2;
        }
        return getRabbit(n-1) + getRabbit(n-2);
    }
}
