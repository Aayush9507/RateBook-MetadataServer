package com.subsystem;

import com.message.Message;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Envelope {
    Message message;
    InetSocketAddress inetSocketAddress;

    Envelope(Message message, InetSocketAddress inetSocketAddress) {
        this.message = message;
        this.inetSocketAddress = inetSocketAddress;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public InetSocketAddress getSrcInetSocketAddress() {
        return inetSocketAddress;
    }

    public boolean isValidToSend(Message m, InetSocketAddress isa){
        return m != null && isa != null && !Objects.equals(isa.getAddress().toString(), "0.0.0.0") && isa.getPort() != 0;
    }
}

