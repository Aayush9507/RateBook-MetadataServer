package com.message;

public class PostFeedMessage {
    private short msgType;
//    ProductMessage prod;
    RegisterUserMessage user;
    String feed;

    public static Message decode(byte[] messageBytes) {
        return null;
    }
}
