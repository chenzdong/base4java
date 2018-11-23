package com.czd.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream初体验
 *
 * @author: czd
 * @create: 2018/11/22 15:28
 */
public class Base {
    public static void main(String[] args) {
        // 构造方法
        // i 静态方法构造 Stream.of(T... values)/(T)
        Stream<Integer> integerStream = Stream.of(1,2,3,5);
        Stream<String> stringStream = Stream.of("hello");
        // Stream.generate()生成无限长度的Stream
        Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });
        Stream.generate(() -> Math.random());
        Stream.generate(Math::random);
        // ii Collection.stream
        List<Integer> nums = Lists.newArrayList(1,2,3,null,5,7,8,9,1,2,3);
        int result = nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::println).skip(2).
                limit(4).sum();
        System.out.println("sum is "+result);
        // 基本方法
        List<String> wordList = Arrays.asList("Test", "ad", "masteR");
        List<String> upperWord = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());

        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1),
                Arrays.asList(2,3),
                Arrays.asList(4,5,6));
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
    }
}
