package com.subsystem;

import com.message.Message;
import com.message.RegisterUserMessage;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UDPComm implements Runnable {

    private DatagramChannel datagramChannel = null;
    Queue receiveEnvelopeQueue = new ConcurrentLinkedQueue();
    private InetAddress IPAddress;

    public UDPComm(DatagramChannel datagramChannel, InetSocketAddress address) throws IOException {
        super();
        this.datagramChannel = datagramChannel;
        if (address.getPort() != 0) {
            this.datagramChannel.bind(address);
        } else {
            this.datagramChannel.bind(null);
        }
    }

    public UDPComm() {
    }

    public void start() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
    }

    public boolean send(Envelope outgoingEnvelope) throws IOException {
        byte[] messageBytes = outgoingEnvelope.getMessage().encode();
        datagramChannel.send(ByteBuffer.wrap(messageBytes), outgoingEnvelope.inetSocketAddress);
        return true;
    }

    public Envelope receive() throws IOException {
        System.out.println("receiving.....");
        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 8087);
        DatagramChannel datagramChannel = DatagramChannel.open();
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(address);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true) {
            InetSocketAddress sourceSocketAddress = (InetSocketAddress) datagramChannel.receive(buffer);
            byte[] messageBytes = Arrays.copyOf(buffer.array(), buffer.position());
            System.out.print("\nData...: "+messageBytes.length);
            buffer.clear();
//            datagramChannel.bind(null);
            System.out.println("Decoding received message");
            return new Envelope(Message.decode(messageBytes), sourceSocketAddress);
        }
        
    }

    public void stop() throws IOException {
        datagramChannel.close();
    }

    @Override
    public void run() {
        try {
            System.out.println("Inside run method");
            Envelope e = receive();
            System.out.println("envelope's Conv ID " + e.getMessage().getConversationId());
            receiveEnvelopeQueue.add(e);
            System.out.println("Added in Receive Envelope Queue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Envelope getEnvelope() {
        // return message from queue with timeout
        System.out.println("Dispatcher Thread Started");
        Envelope env = null;
        if(receiveEnvelopeQueue!=null){
            env = (Envelope) receiveEnvelopeQueue.peek();
            return env;
        }
        return env;
    }
}
