package com.techinsiderpro.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class GreetingClient
{

    public static void main(String[] args)
    {
        try
        {
            Socket client = new Socket(getGameServer(), 6061);
            System.out.println("Connecting to game server " + client.getRemoteSocketAddress().toString());

            new DataOutputStream(client.getOutputStream()).writeUTF("Hello from " + client.getInetAddress().toString());
            System.out.println(new DataInputStream(client.getInputStream()).readUTF());

            client.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static InetAddress getGameServer()
    {
        try
        {
            System.out.println("Connecting to server provider " + ServerProvider.serverName + " on port " + ServerProvider.port);

            Socket client = new Socket(ServerProvider.serverName, ServerProvider.port);

            InetAddress address = (InetAddress) new ObjectInputStream(client.getInputStream()).readObject();

            client.close();

            return address;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
