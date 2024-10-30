package com.jason.spark;

import java.util.Formatter;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 21:46
 */
public class FormatTest {
    public static void main(String[] args) {
        String stringA = "A";
        String stringB = "B";
        String stringnull = null;
        StringBuilder bufferc = new StringBuilder("C");
        Formatter fmt = new Formatter(bufferc);

        fmt.format("%s%s", stringA, stringB);
        System.out.println("Line 1: "+ fmt);

        fmt.format("%-2s", stringB);
        System.out.println("Line 2: "+ fmt);

        fmt.format("%b", stringnull);
        System.out.println("Line 3: "+ fmt);
    }
}

class BaseLogger {
    private static BaseLogger log = new BaseLogger();
    private BaseLogger() {}
    public  static BaseLogger getInstance() {
        return log;
    }
    private StringBuilder logMessage = new StringBuilder();
    public void addLog(String logMessage) {
        this.logMessage.append(logMessage + "|");
        //Logic to write log into file.
    }
    public void printLog() {
        System.out.println(logMessage.toString());//To print log.
    }
}