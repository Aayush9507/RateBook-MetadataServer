package com.subsystem;

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
        while (keepRunning()) {
            Envelope env = udpComm.getEnvelope();
            if(env!=null){
                Conversation c = ConversationDictionary.getConversation(env.getMessage().getConversationId());
                if(c!=null){
                    c.process(env);
                }else{
                    cf.CreateFromEnvelope(env);
                }
            }
        }
    }
}
