package com.message;

import java.io.IOException;
import java.util.UUID;

public class FollowProdMessage extends Message {

    private String prodId;
    private String userId;

    public FollowProdMessage(UUID uuid, String userId, String prodId) {
        super(MessageType.FollowProd, uuid);
        this.userId = userId;
        this.prodId = prodId;
    }

    public static FollowProdMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != Message.MessageType.FollowProd) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        String userId = decoder.decodeString();
        String prodId = decoder.decodeString();

        return new FollowProdMessage(uuid,userId,prodId);
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(prodId)
                .toByteArray();
    }
}
