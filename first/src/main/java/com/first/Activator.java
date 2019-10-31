/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericsson.first;

import akka.actor.ActorSystem;
import akka.osgi.ActorSystemActivator;
import kamon.Kamon;
import kamon.metric.instrument.Counter;
import kamon.metric.instrument.Histogram;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator extends ActorSystemActivator implements BundleActivator {

    public void configure(BundleContext bundleContext, ActorSystem actorSystem) {

        //Old code
        Kamon.startWithinOsgi(bundleContext);
        System.out.println("Starting bundle");

       /* for (StackTraceElement ste : Thread.currentThread().getStackTrace()){
            System.out.println(ste.toString());
        }
        */

        System.out.println("kamon started");

        final Histogram someHistogram = Kamon.metrics().histogram("some-histogram");
        final Counter someCounter = Kamon.metrics().counter("some-counter");

        // This application wont terminate unless you shutdown Kamon.

        BankServiceImpl bank1=new BankServiceImpl();

        bank1.InitialiseBank(bundleContext,actorSystem);

        someHistogram.record(42);
        someHistogram.record(50);
        someCounter.increment();

        Kamon.shutdown();

        System.out.println("stopped");
    }

    /*   public void starting(){

        System.out.println("In kamon");
        Kamon.start();

        final Histogram someHistogram = Kamon.metrics().histogram("some-histogram");
        final Counter someCounter = Kamon.metrics().counter("some-counter");

        someHistogram.record(42);
        someHistogram.record(50);
        someCounter.increment();

        // This application wont terminate unless you shutdown Kamon.
        Kamon.shutdown();
    }
*/
    
}