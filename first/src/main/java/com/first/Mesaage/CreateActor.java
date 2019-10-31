package com.ericsson.first.Mesaage;

import java.io.Serializable;

/**
 * Created by esanhdh on 2/28/2017.
 */
public class CreateActor implements Serializable{

    String name;
    int amount;

    public CreateActor(String Name,int amt){
        name=Name;
        amount=amt;
    }

    public String getName(){
        return  name;
    }

    public int getAmount() {
        return amount;
    }
}
