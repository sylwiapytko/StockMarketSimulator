package com.example.sylwia.myapplication.MyPurchases;

/**
 * Created by Sylwia on 6/1/2018.
 */

public class MyBalance {

    Integer id;

    Double balance;

    public MyBalance(Integer id, Double balance) {
       this.id=id;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
