package com.subsystem;
import com.message.Message;

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
        conv.ConvId = conv.getConvId();
        conv.MaxRetries = 2;
        conv.State = Conversation.PossibleState.Working;
        conv.MaxRetries = 4;
        ConversationDictionary.addConversation(conv.ConvId,conv);
        return conv;
    }

    public  Conversation CreateFromEnvelope(Envelope envelope) {
        Conversation conversation = null;
        Message.MessageType messageType = envelope.getMessage().getMessageType();
        return conversation;
    }
}
