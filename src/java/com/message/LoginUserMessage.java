package com.message;

import java.io.IOException;

public class LoginUserMessage extends Message{

    private String email;
    private String password;


    public LoginUserMessage(String email, String password){
        this.email=email;
        this.password=password;
    }

    public static LoginUserMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.LoginUser) {
            throw new IllegalArgumentException();
        }

        String email = decoder.decodeString();
        String password = decoder.decodeString();

        return new LoginUserMessage(email, password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                .encodeString(email)
                .encodeString(password)
                .toByteArray();
    }
}
