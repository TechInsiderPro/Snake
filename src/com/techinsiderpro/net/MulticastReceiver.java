package com.techinsiderpro.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver
{
    private MulticastSocket multicastSocket;
    private InetAddress inetAddress;

    public MulticastReceiver(String ip, int port)
    {
        try
        {
            multicastSocket = new MulticastSocket(port);
            inetAddress = InetAddress.getByName(ip);

            joinGroup();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void joinGroup()
    {
        try
        {
            multicastSocket.joinGroup(inetAddress);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void leaveGroup()
    {
        try
        {
            multicastSocket.leaveGroup(inetAddress);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void receive()
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

            System.out.println("Waiting for a  multicast message...");
            multicastSocket.receive(packet);

            String msg = new String(packet.getData(), packet.getOffset(),
                    packet.getLength());
            System.out.println("[Multicast  Receiver] Received:" + msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
