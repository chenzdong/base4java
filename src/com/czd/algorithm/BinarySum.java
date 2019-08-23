package com.czd.algorithm;

/**
 * 二进制求和
 * Input: a = "11", b = "1"
 * Output: "100"
 * @author: czd
 * @create: 2019/4/8 16:21
 */
public class BinarySum {
    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        if (a.length() == 0 || b.length()==0) {
            return "";
        }
        int al = a.length()-1;
        int bl = b.length()-1;
        int carry = 0;
        while (al >= 0 || bl >= 0) {
            int sum = carry;
            if (al >= 0) {
                sum += (a.charAt(al--) - '0');
            }
            if (bl >= 0) {
                sum += (b.charAt(bl--) - '0');
            }
            result.append(sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) {
            result.append(1);
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addBinary("","1"));
    }
}
