package com.ericsson.first.Actor;

/**
 * Created by esanhdh on 2/9/2017.
 */
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ericsson.first.ActorData;
import com.ericsson.first.BankServiceImpl;
import com.ericsson.first.Mesaage.*;
import org.osgi.framework.BundleContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TransactionProcessorActor extends UntypedActor {

    private LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);

    ActorRef to;
    private int tokennumber=1;

   private Map<String,ActorData> actors=new HashMap<String, ActorData>();
   private Map<Integer,String> tokens=new HashMap<Integer, String>();


    private ActorRef getFrom(String from){
        String name=from;

        for(Map.Entry<String,ActorData> entry:actors.entrySet()){
            if(entry.getKey().equalsIgnoreCase(name)){
                return entry.getValue().getRef();
            }
        }
       return to;
    }


    private ActorRef getTo(int token){
        String name=" ";

        for(Map.Entry<Integer,String> entry:tokens.entrySet()){
            if(entry.getKey()==token){
                name=entry.getValue();
            }
        }

        for(Map.Entry<String,ActorData> entry:actors.entrySet()){
            if(entry.getKey().equalsIgnoreCase(name)){
                return entry.getValue().getRef();
            }
        }
        return to;

    }

    private void createActor(String name,int amount){
       ActorRef account = context().actorOf(Props.create(AcountActor.class,name,amount));
       ActorData actorData=new ActorData(account,amount);
       actors.put(name,actorData);

      /*  for(Map.Entry<String,ActorData> entry:actors.entrySet()){

            System.out.println("Name = "+entry.getKey()+" Amount ="+entry.getValue().getAmount()+" "+entry.getValue().getRef());
        }*/
    }


    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof TransferMessage){
            TransferMessage transferMessage=(TransferMessage) message;
            tokens.put(tokennumber,transferMessage.getTo());
            getFrom(transferMessage.getFrom()).tell(new WithdrawMessage(transferMessage.getAmount(),tokennumber), getSelf());
            LOG.info("Processing transfer ");
            tokennumber++;
        }

        else

            if(message instanceof ResultMessage){
            ResultMessage result = (ResultMessage) message;
            LOG.info("Remaining Balance for " + result.getName() + " is " + result.getBalance());
        }

        else

            if(message instanceof WithdrawDoneMessage) {
            WithdrawDoneMessage done = (WithdrawDoneMessage) message;
            LOG.info("Remaining Balance for " + done.getName() + " is " + done.getBalance());
            getTo(done.getToken()).tell(new DepositMessage(done.getReducedAmount()),getSelf());
        }

        else

            if(message instanceof CreateActor){
            CreateActor createActor=(CreateActor) message;
            createActor(createActor.getName(),createActor.getAmount());
            LOG.info("Actor " + createActor.getName() + " has " + createActor.getAmount());
        }

        else{
            unhandled(message);
        }
    }
}