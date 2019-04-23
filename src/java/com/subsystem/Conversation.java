package com.subsystem;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
public class Conversation {
    Queue<Envelope> IncomingEnvelopes = new ConcurrentLinkedQueue<Envelope>(); // Queue for Incoming Envelopes
    String ConvId;
    // For maintaining the state of a conversation
    public enum PossibleState {
        NotInitialized,
        Working,
        Failed,
        Succeed
    }

    public PossibleState State  = PossibleState.NotInitialized;

    public CommSubSystem CommSubsystem;
    public InetSocketAddress inetSocketAddress;
    public int Timeout = 3000;
    public int MaxRetries = 3;
    public String Error;
    public boolean Done;

    public Queue<Envelope> getIncomingEnvelopes() {
        return IncomingEnvelopes;
    }

    public PossibleState getState() {
        return State;
    }

    public void setState(PossibleState state) {
        State = state;
    }

    public CommSubSystem getCommSubsystem() {
        return CommSubsystem;
    }

    public void setCommSubsystem(CommSubSystem commSubsystem) {
        CommSubsystem = commSubsystem;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    public int getMaxRetries() {
        return MaxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        MaxRetries = maxRetries;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public void setIncomingEnvelopes(Queue<Envelope> incomingEnvelopes) {

        IncomingEnvelopes = incomingEnvelopes;
    }

    public String getConvId() {
        return ConvId;
    }

    public void setConvId(String convId) {
        ConvId = convId;
    }

    protected boolean Initialize()
    {
        State = PossibleState.Working;
        return true;
    }

    public void process(Envelope env)
    {
        if (env.getMessage() == null || env.getSrcInetSocketAddress() == null) return;
        IncomingEnvelopes.add(env);
    }
    protected boolean IsEnvelopeValid(Envelope env)
    {
        if (env.getMessage() == null)
            return false;
        else
            return true;
    }

    protected Envelope doReliableRequestReply(Envelope outgoingEnv) throws Exception
    {
        Envelope incomingEnvelope = null;
        int remainingSends = MaxRetries;
        while (remainingSends > 0 && incomingEnvelope == null)
        {
            remainingSends--;
            if (CommSubsystem.UdpComm.send(outgoingEnv)){
        try {
//            incomingEnvelope = CommSubsystem.UdpComm.receive();
        } catch (Exception e) {
            e.printStackTrace();
        }}
        }

        return incomingEnvelope;
    }
}
