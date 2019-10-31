package com.ericsson.first.Mesaage;

/**
 * Created by esanhdh on 2/9/2017.
 */
import java.io.Serializable;

public class ResultMessage implements Serializable {
    private String name;
    private int balance;

    public ResultMessage(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

}
