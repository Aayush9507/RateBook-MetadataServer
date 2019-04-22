package com.message;

import java.io.IOException;
import java.util.UUID;

public class RateProdMessage extends Message {

    private String prodId;
    private Float rating;
    private String review;
//    review string will be the feedback of item by user

    public RateProdMessage(UUID uuid, String prodId, Float rating, String review) {
        super(MessageType.RateProd, uuid);
        this.prodId = prodId;
        this.rating = rating;
        this.review = review;

    }

    public static RateProdMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.RateProd) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        String prodId = decoder.decodeString();
        String review = decoder.decodeString();
        Float rating = decoder.decodeFloat();

        return new RateProdMessage(uuid, prodId, rating, review);
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(prodId)
                .encodeFloat(rating)
                .encodeString(review)
                .toByteArray();
    }
}
