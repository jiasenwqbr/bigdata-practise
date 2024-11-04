package com.jason.functional.stream;

import java.util.stream.IntStream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 16:30
 */
public class C08MatchTest {
    public static void main(String[] args) {
        IntStream stream = IntStream.of(1, 3, 5);
        //System.out.println(stream.anyMatch(x->(x&1)==0));
        //System.out.println(stream.allMatch(x->(x&1)==0));
        System.out.println(stream.noneMatch(x->(x&1)==0));

    }
}
