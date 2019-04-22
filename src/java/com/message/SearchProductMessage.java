package com.message;

import java.io.IOException;

public class SearchProductMessage extends Message {

    private String prodName;

    public SearchProductMessage(String prodName){
        this.prodName=prodName;
    }

    public static SearchProductMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.SearchProd) {
            throw new IllegalArgumentException();
        }

        String prodName = decoder.decodeString();

        return new SearchProductMessage(prodName);
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
                .encodeString(prodName)
                .toByteArray();
    }
}
