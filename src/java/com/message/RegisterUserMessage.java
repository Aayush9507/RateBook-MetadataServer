package com.message;

import java.io.IOException;
import java.util.UUID;
public  class RegisterUserMessage extends Message {
    private short userId;
    private String Name;
    private String emailId;
    private String password;
    private String areaOfInterest;

    public RegisterUserMessage(UUID uuid, short userId, String Name, String emailId, String password, String areaOfInterest) {
        super(MessageType.RegisterUser, uuid);
        this.userId = userId;
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
        UUID uuid = decoder.decodeUUID();
        short userId = decoder.decodeShort();
        String Name = decoder.decodeString();
        String emailId = decoder.decodeString();
        String password = decoder.decodeString();
        String areaOfInterest = decoder.decodeString();

        return new RegisterUserMessage(uuid, userId, Name, emailId, password, areaOfInterest);
    }

    public short getUserId() {
        return userId;
    }

    public void setUserId(short userId) {
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
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(convId)
                .encodeShort(userId)
                .encodeString(Name)
                .encodeString(emailId)
                .encodeString(password)
                .encodeString(areaOfInterest)
                .toByteArray();
    }
}
