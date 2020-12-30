package com.czd.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角
 * Input: 5
 * Output:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1],
 * [1,5,10,10,5,1]
 * ]
 * 思路 triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]
 * @author: czd
 * @create: 2019/4/8 14:21
 */
public class PascalTriangle {
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>(numRows);
        if (numRows < 1) {
            return result;
        }
        for (int i = 1; i <= numRows; i++) {
           List<Integer> rowList = new ArrayList<>(i);
            for (int j = 0; j < i ; j++) {
                if (j == 0 || j == i-1) {
                    rowList.add(1);
                } else {
                    rowList.add(result.get(i-2).get(j-1) + result.get(i-2).get(j));
                }
            }
            result.add(rowList);
        }
        return result;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = generate(5);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }
}
