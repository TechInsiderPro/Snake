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

    protected DatagramPacket receive()
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

            multicastSocket.receive(packet);

            System.out.println("Received Packet");

            return packet;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
