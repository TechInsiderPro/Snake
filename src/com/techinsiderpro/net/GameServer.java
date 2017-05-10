package com.techinsiderpro.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends MulticastReceiver
{
	private GameServer(String ip, int port)
	{
		super("230.1.1.1", port);

		try
		{
			ServerSocket serverSocket = new ServerSocket(port + 1);

			List<Socket> clients = new ArrayList<>();

			while (clients.size() < 5)
			{
				DatagramPacket packet = receive();

				String packetData = new String(packet.getData(), packet.getOffset(), packet.getLength());

				System.out.println("Received " + packetData);

				if (packetData.equals("GameJoinRequest"))
				{
					try
					{
						clients.add(serverSocket.accept());

						System.out.println("Added client " + clients.get(clients.size() - 1).toString());
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new GameServer("10.84.8.90", 12345);
	}
}
