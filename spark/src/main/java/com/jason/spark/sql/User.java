package com.jason.spark.sql;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 14:58
 */

@Data
public class User implements Serializable {
    public Long age;
    public String name;

    public User() {
    }

    public User(Long age, String name) {
        this.age = age;
        this.name = name;
    }
}