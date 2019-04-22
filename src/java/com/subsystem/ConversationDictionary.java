package com.subsystem;

import java.util.HashMap;
import java.util.UUID;

public class ConversationDictionary {
private static HashMap<UUID, Conversation> _activeConversation = new HashMap();
    public static Conversation getConversation(UUID conversationId)
    {
        Conversation conv = null;
        conv = _activeConversation.get(conversationId);
        return conv;
    }
    public static void delConversation(UUID conversationId)
    {
        _activeConversation.remove(conversationId);
    }
    public static void addConversation(UUID conversationId, Conversation conv)
    {
        if (conv == null) return;
        Conversation existingConversation = getConversation(conversationId);
        if (existingConversation == null)
            _activeConversation.put(conversationId, conv);
    }
    public static void clearAllConversation(UUID conversationId)
    {
        _activeConversation.clear();
    }
}

