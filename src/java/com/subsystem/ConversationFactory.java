package com.subsystem;
import com.message.Message;
import com.subsystem.Conversation.PossibleState;

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
}
