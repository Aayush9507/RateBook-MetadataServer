package com.subsystem;

import java.io.IOException;

public class Dispatcher implements Runnable {
    UDPComm udpComm;
    ConversationFactory cf;
    private boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
    public void run() {
        try{
            Envelope env = udpComm.getEnvelope();
            if(env!=null){
                System.out.println("Message Type in Dispatcher "+env.getMessage().getMessageType());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            //                Conversation c = ConversationDictionary.getConversation(env.getMessage().getConversationId());
//                if(c!=null){
//                    c.process(env);
//                }else{
//                    cf.CreateFromEnvelope(env);
        }
    }
    
