package com.ericsson.first.Mesaage;

/**
 * Created by esanhdh on 2/23/2017.
 */
import akka.actor.ActorRef;

import java.io.Serializable;

public class WithdrawDoneMessage implements Serializable {
    private String name;
    private int balance;
    private int token;
    private int reducedAmount;

    public WithdrawDoneMessage(String name, int balance,int token,int reducedAmount) {
        this.name = name;
        this.balance = balance;
        this.token=token;
        this.reducedAmount=reducedAmount;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getToken(){return token;}

    public int getReducedAmount() {
        return reducedAmount;
    }
}
