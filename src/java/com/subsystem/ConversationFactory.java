package com.subsystem;
import com.message.Message;

import java.util.HashMap;
import java.util.UUID;

public class ConversationFactory {
    private HashMap<Message.MessageType, UUID> _typeMappings = new HashMap<>(); // Doubt

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

    protected void Add(Message.MessageType msgType, UUID convId)
    {
        if (!_typeMappings.containsKey(msgType))
            _typeMappings.put(msgType, convId);
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

    public boolean canIncomingMessageStartConversation(String messageType) {
        return _typeMappings.containsKey(messageType);
    }

    public  Conversation CreateFromEnvelope(Envelope envelope) {
        Conversation conversation = null;
        Message.MessageType messageType = envelope.getMessage().getMessageType();
//        if (messageType != null && _typeMappings.get(messageType))
//            conversation = CreateResponderConversation(_typeMappings[messageType], envelope);

        return conversation;
    }

    public Conversation CreateResponderConversation(String s, Envelope envelope){
    return new Conversation();
    }


}
