package com.jason.functional.carrying;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月03日 20:14
 */
public class Carrying1Test {
    @FunctionalInterface
    interface Fa {
        Fb op(List<Integer> a);
    }

    @FunctionalInterface
    interface Fb {
        Fc op(List<Integer> b);
    }

    @FunctionalInterface
    interface Fc {
        List<Integer> op(List<Integer> c);
    }

    /*
        目标：把三份数据合在一起，逻辑既定，但数据不能一次得到
        a -> 函数对象
               b -> 函数对象
                    c -> 完成合并
     */
    static Fb step1() {
        List<Integer> x = List.of(1, 2, 3);
        Fa fa = a -> b -> c -> {
            List<Integer> list = new ArrayList<>();
            list.addAll(a);
            list.addAll(b);
            list.addAll(c);
            return list;
        };
        return fa.op(x);
    }

    static Fc step2(Fb fb) {
        List<Integer> y = List.of(4, 5, 6);
        return fb.op(y);
    }

    static void step3(Fc fc) {
        List<Integer> z = List.of(7, 8, 9);
        List<Integer> result = fc.op(z);
        System.out.println(result);
    }

    public static void main(String[] args) {
        step3(step2(step1()));
    }
}
