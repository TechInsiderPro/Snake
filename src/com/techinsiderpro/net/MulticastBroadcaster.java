package com.techinsiderpro.net;

import java.io.IOException;
import java.net.*;

public class MulticastBroadcaster
{
    protected DatagramSocket datagramSocket;
    protected InetAddress inetAddress;
    protected int port;

    public MulticastBroadcaster(String ip, int port)
    {
        try
        {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName(ip);
        }
        catch (SocketException | UnknownHostException e)
        {
            e.printStackTrace();
        }

        this.port = port;
    }

    public void send(byte[] msg)
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            packet.setAddress(inetAddress);
            packet.setPort(port);
            System.out.println(packet.toString());
            datagramSocket.send(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
