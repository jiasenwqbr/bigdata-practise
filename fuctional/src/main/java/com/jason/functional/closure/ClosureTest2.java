package com.jason.functional.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月03日 18:38
 */
public class ClosureTest2 {
    public static void main(String[] args) {
        List<Runnable> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            int k = i+1;
            Runnable task = () -> System.out.println(Thread.currentThread()+":执行任务" + k);
            list.add(task);
        }

        final ExecutorService executorService = Executors.newCachedThreadPool();
        for (Runnable run : list){
            executorService.submit(run);
        }

    }
}
