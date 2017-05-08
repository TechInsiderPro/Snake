package com.techinsiderpro.net;

import java.io.IOException;
import java.net.*;

class MulticastBroadcaster
{
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private int port;

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

    protected void send(byte[] msg)
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            packet.setAddress(inetAddress);
            packet.setPort(port);
            datagramSocket.send(packet);
            System.out.println("Sent a  multicast message");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
