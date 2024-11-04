package com.jason.functional.stream;

import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 15:12
 */
public class C02MapTest {
    public static void main(String[] args) {
        Stream.of(
                new Fruit("草莓", "Strawberry", "浆果", "红色"),
                new Fruit("桑葚", "Mulberry", "浆果", "紫色"),
                new Fruit("杨梅", "Waxberry", "浆果", "红色"),
                new Fruit("核桃", "Walnut", "坚果", "棕色"),
                new Fruit("草莓", "Peanut", "坚果", "棕色"),
                new Fruit("蓝莓", "Blueberry", "浆果", "蓝色")
        ).map(f->f.cname()+"酱").forEach(System.out::println);
    }
    record Fruit(String cname, String name, String category, String color) {
    }
}
