package com.message;

import java.io.IOException;
import java.util.UUID;

public class ACKMessage extends Message {

    public ACKMessage(UUID uuid) {
        super(MessageType.ACKMessage, uuid);
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(this.messageType)
                .encodeUUID(this.conversationId)
                .toByteArray();
    }

    public static ACKMessage decode(byte[] messageBytes) {
        Decoder decoder = new Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.ACKMessage) {
            throw new IllegalArgumentException();
        }
        UUID uuid = decoder.decodeUUID();
        return new ACKMessage(uuid);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
