package com.ericsson.first.Mesaage;

/**
 * Created by esanhdh on 2/9/2017.
 */
import java.io.Serializable;

public class DepositMessage implements Serializable {
    private int amount;

    public DepositMessage(int amt) {
        amount = amt;

    }

    public int getAmount() {
        return amount;
    }



}