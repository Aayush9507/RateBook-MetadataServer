package com.message;

import java.io.IOException;
import java.util.UUID;

public class RegisterUserMessage extends Message {

    private String userId;
    private String Name;
    private String emailId;
    private String password;
    private String areaOfInterest;
//    private String convId;
//    private String msgId;

    public RegisterUserMessage(String userId, String Name, String emailId, String password, String areaOfInterest) {
        super(MessageType.RegisterUser);
//        this.convId = convId;
        this.userId = userId;
//        this.userId = msgId;
        this.Name = Name;
        this.emailId = emailId;
        this.password = password;
        this.areaOfInterest = areaOfInterest;
    }

    public RegisterUserMessage(UUID uuid, String userId, String Name, String emailId, String password, String areaOfInterest) {
        super(MessageType.RegisterUser, uuid);
//        this.convId = convId;
        this.userId = userId;
//        this.userId = msgId;
        this.Name = Name;
        this.emailId = emailId;
        this.password = password;
        this.areaOfInterest = areaOfInterest;
    }

    public static RegisterUserMessage decode(byte[] messageBytes) {
        Decoder decoder = new Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.RegisterUser) {
            throw new IllegalArgumentException();
        }
//        String convId = decoder.decodeString();
//        String msgId = decoder.decodeString();
        UUID uuid = decoder.decodeUUID();
        String userId = decoder.decodeString();
        String Name = decoder.decodeString();
        String emailId = decoder.decodeString();
        String password = decoder.decodeString();
        String areaOfInterest = decoder.decodeString();
        System.out.println(uuid + " " + userId + " " + " " + Name + " " + emailId + " " + password + " " + areaOfInterest);
        return new RegisterUserMessage(uuid, userId, Name, emailId, password, areaOfInterest);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAreaOfInterest() {
        return areaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    @Override
    public byte[] encode() throws IOException {
        System.out.println("Encoding......................Register user Message");
        System.out.println("Message Type" + this.getMessageType());
        System.out.println("Conversation Id" + this.getConversationId());
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(userId)
                .encodeString(Name)
                .encodeString(emailId)
                .encodeString(password)
                .encodeString(areaOfInterest)
                .toByteArray();
    }
}
