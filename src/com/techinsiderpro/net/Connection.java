package com.techinsiderpro.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection extends Socket
{
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public Connection(InetAddress host, int port) throws IOException
	{
		super(host, port);

		objectOutputStream = new ObjectOutputStream(getOutputStream());
		objectInputStream = new ObjectInputStream(getInputStream());
	}

	public void write(Object object)
	{
		try
		{
			objectOutputStream.writeObject(object);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Object read()
	{
		try
		{
			return objectInputStream.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
