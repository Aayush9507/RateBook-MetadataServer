package com.subsystem;

import java.io.IOException;

public class Dispatcher {

    UDPComm udpComm;
    ConversationFactory cf;
    private boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    public void dispatch(Envelope env) {
        try {
            System.out.println("Inside dispatcher's dispatch method");

            System.out.println("Message Type in Dispatcher " + env.getMessage().getMessageType());
            Conversation c = ConversationDictionary.getConversation(env.getMessage().getConversationId());
            System.out.println("Conversation "+c);
            if (c == null) {
                cf = new ConversationFactory();
                c = cf.CreateFromEnvelope(env);
                System.out.println("Conversation created from factory"+c);
                ConversationDictionary.addConversation(env.getMessage().getConversationId(),c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //                
//                
    }
}
