package com.message;

import java.io.IOException;

public class RateFeedMessage extends Message{

    private String prodId;
    private short vote;
    private String reviewId;
    private short userId;

    public RateFeedMessage(String prodId,Short vote,String reviewId,Short userId){
        this.prodId=prodId;
        this.vote=vote;
        this.reviewId=reviewId;
        this.userId=userId;
    }

    public static RateFeedMessage decode(byte[] messageBytes) {
        Message.Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.RateFeed) {
            throw new IllegalArgumentException();
        }

        String prodId = decoder.decodeString();
        String reviewId = decoder.decodeString();
        Short userId = decoder.decodeShort();
        Short vote = decoder.decodeShort();

        return new RateFeedMessage(prodId,vote,reviewId,userId);
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeString(prodId)
                .encodeShort(vote)
                .encodeString(reviewId)
                .encodeShort(userId)
                .toByteArray();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Short getVote() {
        return vote;
    }

    public void setVote(Short vote) {
        this.vote = vote;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }
}
