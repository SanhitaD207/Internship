package com.ericsson.first.Actor;

/**
 * Created by esanhdh on 2/9/2017.
 */
import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ericsson.first.BankServiceImpl;
import com.ericsson.first.Mesaage.*;

public class AcountActor extends UntypedActor {

    private LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);

    private String owner;

    private int balance;

   // private ActorRef transactionProcessor= BankServiceImpl.getProcessor();

     private AcountActor(String name, int bal) {
        this.owner = name;
        this.balance = bal;
    }



    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof WithdrawMessage){
            LOG.info("withdraw");
            balance =balance-((((WithdrawMessage) message).getAmount()));
            getSender().tell(new WithdrawDoneMessage(owner,balance,((WithdrawMessage)message).getToken(),((WithdrawMessage)message).getAmount()), getSelf());
        } else if(message instanceof DepositMessage){
            LOG.info("deposit");
            balance += ((DepositMessage) message).getAmount();
            getSender().tell(new ResultMessage(owner,balance), getSelf());
        } else {
            unhandled(message);
        }
    }
}