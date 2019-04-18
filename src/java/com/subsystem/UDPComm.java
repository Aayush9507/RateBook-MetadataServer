package com.subsystem;

import com.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UDPComm implements Runnable{
    private DatagramChannel datagramChannel;

    // TODO: define a queue
    Queue<Envelope> receiveEnvelopeQueue = new ConcurrentLinkedQueue<>();
    public UDPComm(DatagramChannel datagramChannel, InetSocketAddress address) throws IOException{
        super();
        this.datagramChannel = datagramChannel;
        if (address.getPort() != 0)
            this.datagramChannel.bind(address);
        else
            this.datagramChannel.bind(null);
    }

    public void start() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
    }

    public boolean send(Envelope outgoingEnvelope) throws IOException {
        byte [] messageBytes = outgoingEnvelope.getMessage().encode();
        datagramChannel.send(ByteBuffer.wrap(messageBytes), outgoingEnvelope.inetSocketAddress);
        return true;
    }

    public Envelope receive() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4098);
        buffer.clear();
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) datagramChannel.receive(buffer);

        byte[] messageBytes = Arrays.copyOf(buffer.array(), buffer.position());
        return new Envelope(Message.decode(messageBytes), sourceSocketAddress);
    }

    public void stop() throws IOException {
        datagramChannel.close();
    }

    @Override
    public void run() {
        try {
            Envelope e = receive();
            // TODO: put into a queue
            receiveEnvelopeQueue.add(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Envelope getEnvelope() {
        // return message from queue with timeout
        Envelope env = null;
        return env;
    }
}
