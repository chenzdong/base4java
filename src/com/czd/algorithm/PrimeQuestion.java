package com.czd.algorithm;

/**
 * 判断是否为素数并输出
 * 问题描述：判断101-200之间有多少个素数，并输出所有素数
 * 思路 ： 素数：用一个数分别去除2到sqrt(这个数)，如果能被整除， 则表明此数不是素数，反之是素数。
 * @author: czd
 * @create: 2018/11/20 16:39
 */
public class PrimeQuestion {
    public static void main(String[] args) {
        int count = 0;
        for (int i = 101; i < 200; i += 2) {
            if (isPrime(i)) {
                System.out.println(i);
                count++;
            }
        }
    }
    private static boolean isPrime(int i) {
        for (int j = 2; j < Math.sqrt(i); j++) {
            if(i%j == 0){
                return false;
            }
        }
        return true;
    }
}
