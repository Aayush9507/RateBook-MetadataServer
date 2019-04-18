package com.subsystem;
import java.io.IOException;
import java.util.HashMap;

public class CommSubSystem {
//    private InetSocketAddress bestAddress;
     private ConversationFactory convFact;
    private ConversationDictionary convDict;
    public UDPComm UdpComm;
    private int _minPort;
    private int _maxPort;

    private static HashMap<Integer, Boolean> usedPort = new HashMap<Integer, Boolean>();
    private static int currentPort = 8080;

    public ConversationDictionary getConvDict() {
        return convDict;
    }

    public static int nextAvailablePort()
    {
        while (usedPort.containsKey(currentPort))
            currentPort++;


        usedPort.put(currentPort, true);

        return currentPort++;
    }

    private static void markPortUsed(int port)
    {
        if (!usedPort.containsKey(port))
            usedPort.put(port, true);
    }

    public UDPComm getUdpComm() {
        return UdpComm;
    }

    public void setUdpComm(UDPComm udpComm) {
        UdpComm = udpComm;
    }


    public CommSubSystem(ConversationFactory factory, int minPort, int maxPort)
    {
        convFact = factory;
        _minPort = minPort;
        _maxPort = maxPort;
        convFact.ManagingSubsystem = this;
        convDict = new ConversationDictionary();
    }

    public void Initialize() throws IOException {
        convFact.Initialize();
        UdpComm.start();

    }

    public void stop(int flag) throws IOException {
        if (UdpComm != null)
        {
            UdpComm.stop();
            UdpComm = null;
        }
    }
}
