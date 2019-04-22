package com.message;

import java.io.IOException;
import java.util.UUID;

public class CreateProdMessage extends Message {

    private String userId;
    private String name;
    private String prodId;
    private short price;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public short getPrice() {
        return price;
    }

    public void setPrice(short price) {
        this.price = price;
    }

    public CreateProdMessage(UUID uuid, String userId, String name, String prodId, short price) {
        super(MessageType.CreateProd, uuid);
        this.userId = userId;
        this.name = name;
        this.prodId = prodId;
        this.price = price;

    }

    public static CreateProdMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != Message.MessageType.CreateProd) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        String userId = decoder.decodeString();
        String name = decoder.decodeString();
        String prodId = decoder.decodeString();
        short price = decoder.decodeShort();

        return new CreateProdMessage(uuid, userId, name, prodId, price);
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(userId)
                .encodeString(name)
                .encodeString(prodId)
                .encodeShort(price)
                .toByteArray();

    }

}
