package com.message;
import java.io.IOException;
public class ACKMessage extends Message {
    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(this.messageType)
                .toByteArray();
    }

    public static ACKMessage decode(byte[] messageBytes) {
        Decoder decoder = new Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.ACKMessage) {
            throw new IllegalArgumentException();
        }

        return new ACKMessage();
    }

//    @Override
//    public boolean equals(Object o) {
//        return super.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
