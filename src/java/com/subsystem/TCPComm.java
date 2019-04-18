package com.subsystem;
import com.message.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
public class TCPComm implements Runnable{

    private Socket socket;

    public TCPComm(Socket socket) {
        if(socket == null || !socket.isConnected()) {
            throw new IllegalArgumentException();
        }

        this.socket = socket;
    }

    public void send(Message messageToSend) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(messageToSend.encode());
    }


    public Envelope receive(Envelope e) throws IOException {
        return new Envelope(e.getMessage(), e.getSrcInetSocketAddress());
    }

    @Override
    public void run() {

    }
}
