package com.cx.es.entity;

import java.io.Serializable;

/**
 * 〈〉
 *
 * @author chenxin
 * @date 2020/12/11
 */
public class User implements Serializable {

    private Long userId;

    private String userName;

    public User(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}