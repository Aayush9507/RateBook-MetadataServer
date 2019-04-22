package com.message;

import java.io.IOException;
import java.util.UUID;

public class RateFeedMessage extends Message{

    private String prodId;
    private short vote;
    private String reviewId;
    private String userId;

    public RateFeedMessage(UUID uuid,String prodId,Short vote,String reviewId,String userId){
        super(MessageType.RateFeed,uuid);
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

        UUID uuid = decoder.decodeUUID();
        String prodId = decoder.decodeString();
        String reviewId = decoder.decodeString();
        String userId = decoder.decodeString();
        Short vote = decoder.decodeShort();

        return new RateFeedMessage(uuid,prodId,vote,reviewId,userId);
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(messageType)
                .encodeUUID(conversationId)
                .encodeString(prodId)
                .encodeShort(vote)
                .encodeString(reviewId)
                .encodeString(userId)
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
