package com.jason.functional.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 16:56
 */
public class C11CollectTest {
    /*
        收集：将元素收集入容器
            .collect(() -> c, (c, x) -> void, ?)
            () -> c             创建容器 c
            (c, x) -> void      将元素 x 加入 容器 c
     */
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("令狐冲", "风清扬", "独孤求败", "方证",
                "东方不败", "冲虚", "向问天", "任我行", "不戒", "不戒", "不戒", "不戒");
        //  1) 收集到 List
        // List<String> result = stream.collect(() -> new ArrayList<>(), (list, x) -> list.add(x), (a, b) -> { });
        ArrayList<String> collect = stream.collect(ArrayList::new, ArrayList::add, (a, b) -> {});
        System.out.println(collect);
         /*
            2) 收集到 Set
            Set<String> result = stream.collect(LinkedHashSet::new, Set::add, (a, b) -> { });
         */

        /*
            3)收集到 Map
            Map<String, Integer> result = stream.collect(HashMap::new, (map,x)->map.put(x, 1), (a, b) -> { });
         */

//        StringBuilder sb = stream.collect(StringBuilder::new, StringBuilder::append, (a,b)->{});
//        System.out.println(sb);

    }
}
