package com.techinsiderpro.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameClient extends MulticastBroadcaster
{
    private Socket server;

    private GameClient(String ip, int port)
    {
        super(ip, port);

        try
        {
            send("GameJoinRequest".getBytes());

            server = new ServerSocket(port).accept();

            System.out.println("Connected to " + server.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        GameClient gameClient = new GameClient("230.1.1.1", 12345);
    }
}
