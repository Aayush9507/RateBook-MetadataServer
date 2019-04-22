package com.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.UUID;

public abstract class Message {

    MessageType messageType;
    String msgId;
    String ConvId;

    public static Message decode(byte[] messageBytes) {
        if (messageBytes.length < 2) {
            throw new IllegalArgumentException();
        }

        Decoder decoder = new Decoder(messageBytes);

        MessageType messageType = decoder.decodeMessageType();

        switch (messageType) {
            case RegisterUser:
                return RegisterUserMessage.decode(messageBytes);
            case LoginUser:
                return LoginUserMessage.decode(messageBytes);
            case CreateProd:
                return CreateProdMessage.decode(messageBytes);
            case FollowProd:
                return FollowProdMessage.decode(messageBytes);
            case RateProd:
                return RateProdMessage.decode(messageBytes);
            case PostFeed:
                return PostFeedMessage.decode(messageBytes);
            case RateFeed:
                return RateFeedMessage.decode(messageBytes);
            case SearchProd:
                return SearchProductMessage.decode(messageBytes);
            case NotificationMessage:
                return NotificationMessage.decode(messageBytes);
            case ACKMessage:
                return ACKMessage.decode(messageBytes);
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract byte[] encode() throws IOException;

    public MessageType getMessageType() {
        return messageType;
    }

    public String getConversationId() {
        return ConvId;
    }

    public String getMsgId() {
        return msgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return messageType == message.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageType);
    }

    @Override
    public String toString() {
        return "Message{"
                + "messageType=" + messageType
                + '}';
    }

    public static class Encoder {

        private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        public Encoder encodeUUID(UUID uuid) throws IOException {
            return encodeString(uuid.toString());
        }

        public Encoder encodeShort(short value) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate((Short.SIZE) / 8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putShort(value);
            byteArrayOutputStream.write(buffer.array());
            return this;
        }

        public Encoder encodeFloat(float value) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate((Float.SIZE) / 8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putFloat(value);
            byteArrayOutputStream.write(buffer.array());
            return this;
        }

        public Encoder encodeInt(int value) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate((Integer.SIZE) / 8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putInt(value);
            byteArrayOutputStream.write(buffer.array());
            return this;
        }

        public Encoder encodeByte(byte value) throws IOException {
            byteArrayOutputStream.write(value);
            return this;
        }

        public Encoder encodeLong(long value) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate((Long.SIZE) / 8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putLong(value);
            byteArrayOutputStream.write(buffer.array());
            return this;
        }

        public Encoder encodeString(String value) throws IOException {
            if (value == null) {
                value = "";
            }

            byte[] textBytes = value.getBytes(Charset.forName("UTF-16BE"));
            encodeShort((short) (textBytes.length));
            byteArrayOutputStream.write(textBytes);
            return this;
        }

        public Encoder encodeMessageType(MessageType messageType) throws IOException {
            return encodeShort(messageType.toShort());
        }

        public byte[] toByteArray() {
            return byteArrayOutputStream.toByteArray();
        }
    }

    protected static class Decoder {

        private ByteBuffer byteBuffer;

        public Decoder(byte[] messageBytes) {
            byteBuffer = ByteBuffer.wrap(messageBytes);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public UUID decodeUUID() {
            return UUID.fromString(decodeString());
        }

        public short decodeShort() {
            return byteBuffer.getShort();
        }

        public float decodeFloat() {
            return byteBuffer.getFloat();
        }

        public int decodeInt() {
            return byteBuffer.getInt();
        }

        public long decodeLong() {
            return byteBuffer.getLong();
        }

        public byte decodeByte() {
            return byteBuffer.get();
        }

        public String decodeString() {
            short textLength = decodeShort();

            byte[] textBytes = new byte[textLength];
            byteBuffer.get(textBytes, 0, textLength);
            return new String(textBytes, Charset.forName("UTF-16BE"));
        }

        public MessageType decodeMessageType() {
            short messageTypeShort = decodeShort();
            return MessageType.getTypeFromShort(messageTypeShort);
        }
    }

    public enum MessageType {
        ERROR,
        RegisterUser,
        LoginUser,
        CreateProd,
        FollowProd,
        RateProd,
        PostFeed,
        RateFeed,
        SearchProd,
        NotificationMessage,
        ACKMessage;

        public short toShort() {
            return (short) this.ordinal();
        }

        public static MessageType getTypeFromShort(short ordinal) {
            return MessageType.values()[ordinal];
        }
    }
}
