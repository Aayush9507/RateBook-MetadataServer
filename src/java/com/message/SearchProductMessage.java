package com.message;

import java.io.IOException;
import java.util.UUID;

public class SearchProductMessage extends Message {

    private String prodName;
    private String prodId;

    public SearchProductMessage(UUID uuid, String prodName, String prodId) {
        super(MessageType.SearchProd, uuid);
        this.prodName = prodName;
    }

    public static SearchProductMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.SearchProd) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        String prodName = decoder.decodeString();
        String prodId = decoder.decodeString();

        return new SearchProductMessage(uuid, prodName, prodId);
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(prodName)
                .encodeString(prodId)
                .toByteArray();
    }
}
