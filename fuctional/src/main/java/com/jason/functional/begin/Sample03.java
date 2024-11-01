package com.jason.functional.begin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月01日 10:48
 */
public class Sample03 {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("张无忌", 18, "男"),
                new Student("杨不悔", 16, "女"),
                new Student("周芷若", 19, "女"),
                new Student("宋青书", 20, "男")
        );
        System.out.println(filter0(students,(Student stu)->stu.sex.equals("男")));
        System.out.println(filter0(students,(Student stu)->stu.age>17));
    }
    static List<Student> filter0 (List<Student> list, Lambda lambda){
        List<Student> result = new ArrayList<>();
        for (Student stu : list){
            if (lambda.test(stu)){
                result.add(stu);
            }
        }
        return result;
    }
    interface Lambda {
        boolean test(Student student);
    }
    static class Student {
        private String name;
        private int age;
        private String sex;

        public Student(String name, int age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}
