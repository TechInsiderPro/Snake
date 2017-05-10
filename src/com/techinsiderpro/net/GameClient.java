package com.techinsiderpro.net;

import java.io.IOException;
import java.net.Socket;

public class GameClient extends MulticastBroadcaster
{
	private GameClient(String ip, int port)
	{
		super("230.1.1.1", port);

		try
		{
			Socket socket = new Socket(ip, port + 1);

			send("GameJoinRequest".getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		GameClient gameClient = new GameClient("10.84.8.90", 12345);
	}
}
