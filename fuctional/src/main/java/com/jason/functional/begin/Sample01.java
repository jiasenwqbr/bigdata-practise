package com.jason.functional.begin;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月31日 22:26
 */
public class Sample01 {
    static int add (int a,int b){
        return a+b;
    }
    interface Lambda {
        int calculate(int a,int b);
    }
    // 函数化对象
    static Lambda sum = (a,b) -> a+b;

    /*
     * 前者是纯粹的一条两数加法规则，它的位置是固定的，要使用它，需要通过 Sample4.add 找到它，然后执行
     * 而后者（add 对象）就像长了腿，它的位置是可以变化的，想去哪里就去哪里，哪里要用到这条加法规则，把它传递过去
     * 接口的目的是为了将来用它来执行函数对象，此接口中只能有一个方法定义
     */

    public static void main(String[] args) {
        System.out.println(Sample01.add(12,13));
        System.out.println(sum.calculate(12,13));
        doSomeThing(12,13,sum);
        doSomeThing(12,13,(a,b)->a*b);
    }

    static void doSomeThing(int a,int b,Lambda l){
        //do many things
        // ...
        int result = l.calculate(a, b);
        System.out.println(result);
    }

}
