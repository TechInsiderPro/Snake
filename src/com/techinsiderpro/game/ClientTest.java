package com.techinsiderpro.game;

import com.techinsiderpro.net.MulticastBroadcaster;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTest
{
	public static void main(String[] args)
	{
		try
		{
			MulticastBroadcaster multicastBroadcaster = new MulticastBroadcaster("230.1.1.1", 12345);
			multicastBroadcaster.send("Sup".getBytes());

			Socket connection = new ServerSocket(12345).accept();

			System.out.println("Connected");

			ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());

			GridObject gridObject = (GridObject) objectInputStream.readObject();

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

			objectOutputStream.writeObject(new DirectionChangeRequestEvent(Direction.LEFT, gridObject));
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
