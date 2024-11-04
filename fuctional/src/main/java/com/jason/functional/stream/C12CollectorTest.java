package com.jason.functional.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 20:39
 */
public class C12CollectorTest {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("令狐冲", "风清扬", "独孤求败", "方证",
                "东方不败", "冲虚", "向问天", "任我行", "不戒");
        // 1) 收集到 List
        /*List<String> result =  stream.collect(ArrayList::new, ArrayList::add,(a,b)->{});
        System.out.println(result);*/

        // 2) 收集到 Set
        /*Set<String> result = stream.collect(HashSet::new,HashSet::add,(a, b)->{});
        System.out.println(result);*/

        // 3)收集到 StringBuilder
        /*StringBuilder sb = stream.collect(StringBuilder::new, StringBuilder::append, (a,b)->{});
        System.out.println(sb);*/

        // 4)收集到 StringJoiner
//        StringJoiner sb = stream.collect(()->new StringJoiner(","), StringJoiner::add, (a,b)->{});

        // 3)收集到 Map
        /*Map<String, Integer> result = stream.collect(HashMap::new, (map,x)->map.put(x, 1), (a, b) -> { });

        Map<String, Integer> map = stream.collect(Collectors.toMap(x -> x, x -> 1));*/


        Map<Integer, String> result = stream.collect(Collectors.groupingBy(x -> x.length(), Collectors.joining(",")));
        for (Map.Entry<Integer, String> e : result.entrySet()) {
            System.out.println(e);
        }




    }
}
