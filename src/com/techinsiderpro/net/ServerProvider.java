package com.techinsiderpro.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ServerProvider extends Thread
{
    public static final String serverName = "192.168.0.14";
    public static final int port = 6060;

    private final List<InetAddress> availableServers = new ArrayList<>();
    private ServerSocket serverSocket;

    public ServerProvider()
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while (true)
            try
            {
                System.out.println("Waiting for connections on port " + port);
                Socket server = serverSocket.accept();
                System.out.println("Connected to " + server.getRemoteSocketAddress().toString());
                ObjectOutputStream objectOut = new ObjectOutputStream(server.getOutputStream());
                objectOut.writeObject(getAvailableServerAddress());
                server.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    private InetAddress getAvailableServerAddress()
    {
        if (availableServers.size() > 0)
            return availableServers.get(0);
        else
            return null;
    }

    public static void main(String[] args)
    {
        try
        {
            ServerProvider serverProvider = new ServerProvider();
            serverProvider.availableServers.add(InetAddress.getByName(serverName));
            serverProvider.start();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
