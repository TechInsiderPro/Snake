package com.techinsiderpro.client;

import com.techinsiderpro.common.events.DirectionChangeRequestEvent;
import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.game.objects.GridObject;
import com.techinsiderpro.common.game.objects.containers.GridObjectContainer;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastBroadcaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Client
{
	public static void main(String[] args)
	{
		try
		{
			MulticastBroadcaster multicastBroadcaster = new MulticastBroadcaster("230.1.1.1", 12345);
			ServerSocket serverSocket = new ServerSocket(12346);
			Connection connection = null;

			serverSocket.setSoTimeout(1000);

			while (connection == null)
			{
				multicastBroadcaster.send("Sup".getBytes());
				System.out.println("Sent sup");

				try
				{
					Socket socket = serverSocket.accept();
					connection = new Connection(socket);
				} catch (SocketTimeoutException e)
				{
					System.out.println("No response");
				}
			}

			System.out.println("Connected");

			while (connection.isConnected())
			{
				Thread.sleep(300);

				GridObjectContainer gridObjectContainer = (GridObjectContainer) connection.read();

				for (GridObject gridObject : gridObjectContainer)
				{
					Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
					if (connection.isConnected())
						connection.write(new DirectionChangeRequestEvent(direction, gridObject));
				}
			}
		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
