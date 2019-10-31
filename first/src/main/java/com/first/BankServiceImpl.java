package com.ericsson.first;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.osgi.ActorSystemActivator;
import com.ericsson.first.Actor.AcountActor;
import com.ericsson.first.Actor.TransactionProcessorActor;
import com.ericsson.first.Mesaage.CreateActor;
import com.ericsson.first.Mesaage.TransferMessage;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Hashtable;

import static akka.actor.TypedActor.context;

public class BankServiceImpl implements EventAdmin {

    private static ActorRef proc;
    private static String prefix;



    public void InitialiseBank(BundleContext bundleContext, ActorSystem system) {

     //   System.out.println("In kamon");
     //   Kamon.start();

        ActorRef processor = system.actorOf(Props.create(TransactionProcessorActor.class),"processor");
        proc=processor;

        System.out.println("processor made");

        //Processor created

       // EventAdmin newevent;

        //to register eventadminservice
        ServiceReference<EventAdmin> ref = bundleContext.getServiceReference(EventAdmin.class);
        //newevent = (EventAdmin)context.getService(ref);


       //to initialise handler
        String[] topics = new String[] {"com/example/bank/*"};
        Hashtable ht = new Hashtable();
        ht.put(EventConstants.EVENT_TOPIC, topics);

        //to register handler
        bundleContext.registerService(EventHandler.class.getName(),new CurrentHandler(), ht);

    }


    public void transferMoneyImpl(String from,String to,int amount){

            proc.tell(new TransferMessage(from, to, amount), proc);
    }

    public void newActor(String name,int amount){


        proc.tell(new CreateActor(name,amount),proc);

    }

    public void bulkActors(String name,int amount,int count){

       prefix=name;

        //First actor with high amount
        String actor0 = prefix.concat("0");
        newActor(actor0,50000);

        //Rest of the actors
      for(int n=1;n<=count;n++) {
            String actors = prefix.concat(String.valueOf(n));
            newActor(actors,amount);
        }
    }

    public void bulkTransfer(int amount,int count){

        for(int n=1;n<=count;n++) {
            String actors = prefix.concat(String.valueOf(n));
            transferMoneyImpl(prefix.concat("0"),actors,amount);
        }

    }

    @Override
    public void postEvent(Event event) {

    }

    @Override
    public void sendEvent(Event event) {

    }
}
