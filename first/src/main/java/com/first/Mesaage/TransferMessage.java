package com.ericsson.first.Mesaage;

/**
 * Created by esanhdh on 2/9/2017.
 */
import akka.actor.ActorRef;

import java.io.Serializable;

public class TransferMessage implements Serializable {

    private String to;
    private String  from;
    private int amount;

    public TransferMessage(String from,String to, int amount) {
        this.to = to;
        this.from = from;
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }


    public String getFrom() {
        return from;
    }


    public int getAmount() {
        return amount;
    }




}