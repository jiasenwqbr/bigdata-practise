package com.jason.functional.methodref;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月01日 15:49
 */
public class MethodRef2 {
    static class Student {
        private final String name;
        private final Integer age;

        public Student() {
            this.name = "某人";
            this.age = 18;
        }

        public Student(String name) {
            this.name = name;
            this.age = 18;
        }

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    public static void main(String[] args) {
        Supplier<Student> s1= Student::new;
        Function<String,Student> s2 = Student::new;
        BiFunction<String,Integer,Student> s3 = Student::new;

        System.out.println(s1.get());
        System.out.println(s2.apply("zhangsan"));
        System.out.println(s3.apply("lisi",12));
    }
}
