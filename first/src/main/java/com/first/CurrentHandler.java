package com.ericsson.first;

/**
 * Created by esanhdh on 2/24/2017.
 */
import akka.actor.ScalaActorRef;
import com.ericsson.first.Actor.TransactionProcessorActor;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import java.util.Calendar;

import static akka.actor.TypedActor.context;

/**
 * Created by esanhdh on 2/24/2017.
 */
public class CurrentHandler implements EventHandler {

    BankServiceImpl bank = new BankServiceImpl();



    @Override
    public void handleEvent(Event event) {


        String topic=event.getTopic();
        Object from= event.getProperty("From");
        Object to= event.getProperty("To");
        Object amount= event.getProperty("Amount");
        Object name= event.getProperty("Name");
        Object prefix= event.getProperty("Prefix");
        Object count= event.getProperty("Count");

        int num = Integer.valueOf((String) amount);


       if(topic.equalsIgnoreCase("com/example/bank/transfer")) {

           if(from==(null) || amount==null || to==null)
               System.out.println("Invalid input");

           else {
               System.out.println("Processing transfer ");
               bank.transferMoneyImpl((String) from, (String) to, num);
           }
        }

        else


            if(topic.equalsIgnoreCase("com/example/bank/create")){


                if(name==(null) || amount==null)
                    System.out.println("Invalid input");
                else {
                    System.out.println("Creating new actor");
                    bank.newActor((String) name, num);
                }
            }

            else


            if(topic.equalsIgnoreCase("com/example/bank/createBulk")){

                if(prefix==(null) || amount==null || count==null)
                    System.out.println("Invalid input");

                else {
                    System.out.println("Creating bulk actors");
                    bank.bulkActors((String) prefix, num, Integer.valueOf((String) count));
                }

            }

            else


            if(topic.equalsIgnoreCase("com/example/bank/transferBulk")){

                if(amount==null || count==null)
                    System.out.println("Invalid input");

                else {
                    System.out.println("Transfering bulk actors");
                    bank.bulkTransfer(num, Integer.valueOf((String) count));
                }

            }


            else
                System.out.println("Invalid Input");
    }
}