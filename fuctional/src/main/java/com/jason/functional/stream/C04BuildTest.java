package com.jason.functional.stream;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 15:24
 */
public class C04BuildTest {
    public static void main(String[] args) {
        // 1. 从集合构建
        Set.of(1, 2, 3).stream().forEach(System.out::println);
        Map.of("a", 1, "b", 2).entrySet().stream().forEach(System.out::println);

        // 2. 从数组构建
        int[] array = {1, 2, 3};
        Arrays.stream(array).forEach(System.out::println);

        // 3. 从对象构建
        Stream.of(1,2,3,4,5).forEach(System.out::println);
    }
}
