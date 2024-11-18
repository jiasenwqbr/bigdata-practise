package com.jason.functional.methodref;

import java.util.stream.Stream;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月01日 15:31
 */
public class MethodRef1 {
    public static void main(String[] args) {
        Util util = new Util();
        Stream.of(
                        new Student("张无忌", "男"),
                        new Student("周芷若", "女"),
                        new Student("宋青书", "男")
                )
                .filter(util::isMale)
                //.filter((stu) -> util.isMale(stu))
                //.map(stu->stu.name)
                .map(Student::name)
                .forEach(
                        System.out::println
                        // name -> System.out.println(name)
                );
    }

    record Student(String name, String sex) {
        public String name() {
            return this.name;
        }
        public String sex() {
            return  this.sex;
        }
    }
    static class Util {
        public boolean isMale(Student stu){
            return stu.sex.equals("男");
        }
        public String getName(Student stu){
            return stu.name;
        }
    }
}
