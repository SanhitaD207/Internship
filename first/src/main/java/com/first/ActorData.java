package com.ericsson.first;

import akka.actor.ActorRef;

/**
 * Created by esanhdh on 2/28/2017.
 */
public class ActorData {

    ActorRef ref;
    int amount;

    public ActorData(ActorRef reference,int amt){
        this.ref=reference;
        this.amount=amt;
    }

    public ActorRef getRef(){
        return  ref;
    }

    public int getAmount(){
        return amount;
    }
}
