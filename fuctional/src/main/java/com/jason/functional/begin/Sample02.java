package com.jason.functional.begin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月31日 23:12
 */
public class Sample02 {
    interface Lambda extends Serializable {
        int calculate(int a, int b);
    }
    static class Server  {
        public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("server start...");
            final ExecutorService executorService = Executors.newCachedThreadPool();
            while(true){
                Socket s = ss.accept();
                executorService.submit(()->{
                    try {
                        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                        Lambda lambda = (Lambda)  is.readObject();
                        int a = ThreadLocalRandom.current().nextInt(10);
                        int b = ThreadLocalRandom.current().nextInt(10);
                        System.out.printf("%s %d op %d = %d%n", s.getRemoteSocketAddress().toString(), a, b, lambda.calculate(a, b));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
    // Server 虚拟机端必须有 Client0 这个类，相当于把实现绑定在了服务器端
    static class Client0 {
        int add(int a,int b){
            return a+b;
        }
    }
    // Server 虚拟机端只需有 Lambda 接口定义，实现与服务器无关
    static class Client1 {
        public static void main(String[] args) {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                Lambda lambda = (a,b) -> a+b;
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(lambda);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class Client2 {
        public static void main(String[] args) {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                Lambda lambda = (a,b) -> a-b;
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(lambda);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Client3 {
        public static void main(String[] args) {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                Lambda lambda = (a,b) -> a*b;
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(lambda);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
