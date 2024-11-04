package com.jason.functional.stream;

import java.util.stream.IntStream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 15:46
 */
public class C07FindTest {
    public static void main(String[] args) {
        // 1. findFirst 找到第一个元素
        IntStream stream = IntStream.of(1, 2, 3, 4, 5, 6);
        System.out.println(stream.filter(x->(x&1)==0).findFirst().orElse(-1));

        stream.filter(x -> (x & 1) == 0).findFirst().ifPresent((x)->System.out.println(x));
    }
}
