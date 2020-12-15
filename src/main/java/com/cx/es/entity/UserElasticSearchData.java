package com.cx.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 〈〉
 *
 * @author chenxin
 * @date 2020/12/11
 */

@Document(indexName = "chenxin")
public class UserElasticSearchData {

    @Id
    private String userId;

    @Field(type = FieldType.Text)
    private String userName;

    @Field(type = FieldType.Double)
    private Double balance;

    public UserElasticSearchData() {}

    public UserElasticSearchData(String userName, Double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override public String toString() {
        return "UserElasticSearchData{" + "userId='" + userId + '\'' + ", userName='" + userName + '\'' + ", balance="
                + balance + '}';
    }
}