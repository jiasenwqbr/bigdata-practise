package com.jason.functional.highorder;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 10:06
 */
public class C04SimpleStream<T> {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 1, 2, 3);
        HashMap<Integer, Integer> collect = C04SimpleStream.of(list).collect(HashMap::new, (map, t) -> {
            if (!map.containsKey(t)) {
                map.put(t, 1);
            } else {
                Integer v = map.get(t);
                map.put(t, v + 1);
            }
        });
        System.out.println(collect);
        System.out.println("-----------------------------");
        /*
            如果 key 在 map 中不存在，将 key 连同新生成的 value 值存入 map, 并返回 value
            如果 key 在 map 中存在，会返回此 key 上次的 value 值

            1, 2, 3, 4, 5, 1, 2, 3

            key     value
            1       AtomicInteger(2)
            2       AtomicInteger(2)
            3       AtomicInteger(2)
            4       AtomicInteger(1)
            5       AtomicInteger(1)
         */
        HashMap<Integer, AtomicInteger> collect2 = C04SimpleStream.of(list)
                .collect(HashMap::new, (map, t) -> map.computeIfAbsent(t, k -> new AtomicInteger()).getAndIncrement());
        System.out.println(collect2);
        System.out.println("-----------------------------");
        Integer reduce = C04SimpleStream.of(list).reduce(0, (a, b) -> Integer.sum(a, b));
        System.out.println(reduce);
        System.out.println("-----------------------------");
        HashSet<Integer> collect1 = C04SimpleStream.of(list).collect(HashSet::new, HashSet::add);
        System.out.println(collect1);
        System.out.println("-----------------------------");
        C04SimpleStream.of(list).filter((v) -> v > 4).forEach(System.out::println);


    }
    // C 代表容器类型, supplier 用来创建容器
    public <C> C collect(Supplier<C> supplier, BiConsumer<C,T> consumer){
        C c = supplier.get(); // 创建了容器
        for (T t : collection){
            consumer.accept(c,t); // 向容器中添加元素
        }
        return c;
    }

    public C04SimpleStream<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : collection) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return new C04SimpleStream<>(result);
    }

    // o 代表 p 的初始值
    public T reduce(T o, BinaryOperator<T> operator){
        T p = o;
        for (T t : collection){
            p = operator.apply(p,t);
        }
        return p;
    }
    public void forEach(Consumer<T> consumer) {
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    public static <T> C04SimpleStream<T> of(Collection<T> collection) {
        return new C04SimpleStream<>(collection);
    }
    private Collection<T> collection;
    private C04SimpleStream(Collection<T> collection) {
        this.collection = collection;
    }

}
