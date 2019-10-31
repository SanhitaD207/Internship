package com.ericsson.first.Mesaage;

/**
 * Created by esanhdh on 2/9/2017.
 */
import akka.actor.ActorRef;

import java.io.Serializable;

public class WithdrawMessage implements Serializable {
    private int amount;
    private int token;

    public WithdrawMessage(int amt,int token) {
        amount = amt;
        this.token=token;
    }

    public int getAmount() {
        return amount;
    }

    public int getToken() {return token;}

}