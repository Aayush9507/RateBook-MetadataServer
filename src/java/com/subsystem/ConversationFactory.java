package com.subsystem;
import com.message.ACKMessage;
import com.message.CreateProdMessage;
import com.message.FollowProdMessage;
import com.message.LoginUserMessage;
import com.message.Message;
import com.message.RateFeedMessage;
import com.message.RateProdMessage;
import com.message.RegisterUserMessage;
import com.message.SearchProductMessage;
import com.subsystem.Conversation.PossibleState;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.UUID;

public class ConversationFactory {
    public CommSubSystem ManagingSubsystem;
    public int DefaultMaxRetries;
    public int DefaultTimeout;
    public void Initialize() {

    }

    public CommSubSystem getManagingSubsystem() {
        return ManagingSubsystem;
    }

    public void setManagingSubsystem(CommSubSystem managingSubsystem) {
        ManagingSubsystem = managingSubsystem;
    }

    public int getDefaultMaxRetries() {
        return DefaultMaxRetries;
    }

    public void setDefaultMaxRetries(int defaultMaxRetries) {
        DefaultMaxRetries = defaultMaxRetries;
    }

    public int getDefaultTimeout() {
        return DefaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        DefaultTimeout = defaultTimeout;
    }

    public Conversation CreateFromConversationType() {
        Conversation conv = new Conversation();
        return conv;
    }

    public  Conversation CreateFromEnvelope(Envelope envelope) {
        Conversation conversation = new Conversation();
        conversation.setInetSocketAddress(envelope.getSrcInetSocketAddress());
        conversation.ConvId = UUID.randomUUID().toString();
        conversation.setState(PossibleState.Working);
        //Message.MessageType messageType = envelope.getMessage().getMessageType();
        return conversation;
    }
    public Envelope CreateEnvelopee(ArrayList<String> msglist) throws IOException {
        System.out.println("Creating Envelope using factory"+msglist.get(0));
        Message msg;

        EnvelopeFactory ef = new EnvelopeFactory();
        msg = ef.EnvelopeFactory(msglist);
        Envelope env = new Envelope(msg, new InetSocketAddress("localhost", 8082));
        return env;
    }
    public class EnvelopeFactory {

        public UUID uuid = UUID.randomUUID();

        public Message EnvelopeFactory(ArrayList<String> mylist) {

            String pos = mylist.get(0);

            if (pos.equals("RegisterUser")) {
                System.out.println("0 "+mylist.get(0));
                System.out.println("1 "+mylist.get(1));
                System.out.println("2 "+mylist.get(2));
                System.out.println("3 "+mylist.get(3));
                System.out.println("4 "+mylist.get(4));
                System.out.println("5 "+mylist.get(5));
                return new RegisterUserMessage(uuid, mylist.get(1), mylist.get(2), mylist.get(3), mylist.get(4), mylist.get(5));
            }

            if (pos.equals("LoginUser")) {
                return new LoginUserMessage(uuid, mylist.get(1), mylist.get(2));
            }

            if (pos.equals("CreateProd")) {
                return new CreateProdMessage(uuid, mylist.get(1), mylist.get(2), mylist.get(3), Short.valueOf(mylist.get(4)));
            }

            if (pos.equals("FollowProd")) {
                return new FollowProdMessage(uuid, mylist.get(1), mylist.get(2));
            }
            if (pos.equals("RateProd")) {
                return new RateProdMessage(uuid, mylist.get(1), Float.valueOf(mylist.get(2)), mylist.get(3));
            }
            if (pos.equals("RateFeed")) {
                return new RateFeedMessage(uuid, mylist.get(1), Short.valueOf(mylist.get(2)), mylist.get(3), mylist.get(4));
            }
            if (pos.equals("SearchProd")) {
                return new SearchProductMessage(uuid, mylist.get(1), mylist.get(2));
            }
            if (pos.equals("ACKMessage")) {
                return new ACKMessage(uuid);
            }
            return null;
        }
    }
}
