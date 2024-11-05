package com.jason.functional.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 23:04
 */
public class C16Parallel {
    public static void main(String[] args) {
        List<Integer> collect = Stream.of(1, 2, 3, 4).collect(
                Collector.of(
                        // Supplier：提供一个新的空集合（ArrayList<Integer>）
                        () -> {
                            System.out.printf("%-12s %s%n", simple(), "create");
                            return new ArrayList<Integer>();
                        },

                        // Accumulator：如何将元素添加到集合中
                        (list, x) -> {
                            List<Integer> old = new ArrayList<>(list);
                            list.add(x);
                            System.out.printf("%-12s %s.add(%d)=>%s%n",simple(), old, x, list);
                        },

                        // Combiner：并行流中如何合并两个部分
                        (list1, list2) -> {
                            List<Integer> old = new ArrayList<>(list1);
                            list1.addAll(list2);
                            System.out.printf("%-12s %s.add(%s)=>%s%n", simple(),old, list2, list1);
                            return list1;
                        },

                        // Finisher：最终阶段，通常用于转换结果
                        list -> null, // 此处没有实际操作，返回 null

                        // Characteristics：指定该收集器的属性
                        Collector.Characteristics.IDENTITY_FINISH

                )
        );
    }
    private static String simple() {
        String name = Thread.currentThread().getName();
        int idx = name.indexOf("worker");
        if (idx > 0) {
            return name.substring(idx);
        }
        return name;
    }
}
/*
    main         create
    main         [].add(1)=>[1]
    main         [1].add(2)=>[1, 2]
    main         [1, 2].add(3)=>[1, 2, 3]
    main         [1, 2, 3].add(4)=>[1, 2, 3, 4]
*/

