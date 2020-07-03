package com.czd.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 螺旋矩阵
 * 输入:
 * [
 *   [1, 2, 3, 4],
 *   [5, 6, 7, 8],
 *   [9,10,11,12]
 * ]
 * 思路： 上 -> 右 -> 下 -> 左
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 * @author: czd
 * @create: 2019/4/8 10:12
 */
public class SpiralOrder {
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix.length == 0) {
            return  result;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int up = 0, down = m-1, left = 0, right = n-1;
        int index = 0;
        while (true) {
            // up
            for (int col = left; col <= right; col++) {
                result.add(index++, matrix[up][col]);
            }
            if (++up > down) {
                break;
            }
            // right
            for (int row = up; row <= down; row++) {
                result.add(index++, matrix[row][right]);
            }
            if (--right < left) {
                break;
            }
            // down
            for (int col = right; col >= left; col--) {
                result.add(index++, matrix[down][col]);
            }
            if (--down < up) {
                break;
            }
            // left
            for (int row = down; row >= up ; row--) {
                result.add(index++, matrix[row][left]);
            }
            if (++left > right) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] origin = {{1, 2, 3, 4},{5, 6, 7, 8},{9,10,11,12}};
        List<Integer> result = spiralOrder(origin);
        for (Integer integer : result) {
            System.out.println(integer);
        }
    }
}
