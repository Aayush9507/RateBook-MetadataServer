package com.message;

import java.io.IOException;
import java.util.UUID;

public class LoginUserMessage extends Message {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    private String password;

    public LoginUserMessage(UUID uuid, String userId, String password) {
        super(MessageType.LoginUser, uuid);
        this.userId = userId;
        this.password = password;
    }

    public static LoginUserMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.LoginUser) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        String userId = decoder.decodeString();
        String password = decoder.decodeString();

        return new LoginUserMessage(uuid, userId, password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(userId)
                .encodeString(password)
                .toByteArray();
    }
}
