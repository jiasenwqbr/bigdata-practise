package com.jason.functional.stream;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 16:36
 */
public class C10ReduceTest {
    record Hero(String name, int strength) {
    }
    public static void main(String[] args) {
        Stream<Hero> stream = Stream.of(
                new Hero("令狐冲", 90),
                new Hero("风清扬", 98),
                new Hero("独孤求败", 100),
                new Hero("方证", 92),
                new Hero("东方不败", 98),
                new Hero("冲虚", 90),
                new Hero("向问天", 88),
                new Hero("任我行", 92),
                new Hero("不戒", 88)
        );
        // 1) 求武力最高的 hero
//        Optional<Hero> result = stream.reduce((h1, h2) -> h1.strength() > h2.strength() ? h1 : h2);
//        System.out.println(result.get());

//        Hero result = stream.reduce(new Hero("-", -1), (h1, h2) -> h1.strength() > h2.strength() ? h1 : h2);
//        System.out.println(result);
        // 2) 求高手总数
//        System.out.println(stream.map(h -> 1).reduce(0, (a, b) -> a + b));
//        System.out.println(stream.max(Comparator.comparingInt(Hero::strength)));
        //System.out.println(stream.min(Comparator.comparingInt(Hero::strength)));
//        System.out.println(stream.mapToInt(Hero::strength).sum());
        System.out.println(stream.mapToInt(Hero::strength).average());



    }
}
