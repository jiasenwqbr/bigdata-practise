package com.jason.functional.highorder;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 09:13
 */
public class C01InnerLoop {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        // 需求：逆序遍历集合，只想负责元素处理，不改变集合
        hiOrder(list, (value) -> System.out.println(value));
    }

    private static <T> void hiOrder(List<T> list, Consumer<T> consumer)  {
        ListIterator<T> iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()){
            T previous = iterator.previous();
            consumer.accept(previous);
        }
    }
}
