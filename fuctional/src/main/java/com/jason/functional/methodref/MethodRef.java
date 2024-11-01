package com.jason.functional.methodref;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月01日 13:22
 */
public class MethodRef {
    public static void main(String[] args) {
        highOrder(Student::hello);
    }

    interface Type3 {
        String tranfer(Student stu,String message);
    }
    static void highOrder (Type3 lambda){
        System.out.println(lambda.tranfer(new Student("zhangsan"),"hello"));
    }
    static class Student {
        String name;

        public Student(String name) {
            this.name = name;
        }

        public String hello(String message) {
            return this.name + " say: " + message;
        }
    }
}
