package com.techinsiderpro.common.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver
{
    protected MulticastSocket multicastSocket;
    public InetAddress inetAddress;

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

    public void joinGroup()
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

    public void leaveGroup()
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

    public DatagramPacket receive()
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

            multicastSocket.receive(packet);

            return packet;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
