package com.techinsiderpro.common.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection
{
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private Socket socket;

	public Connection(InetAddress host, int port)
	{
		try
		{
			socket = new Socket(host, port);

			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Connection(Socket socket)
	{
		this.socket = socket;

		try
		{
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void write(Object object)
	{
		try
		{
			objectOutputStream.writeObject(object);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Object read()
	{
		try
		{
			return objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public boolean isConnected()
	{
		return socket.isConnected();
	}

	public InetAddress getInetAddress()
	{
		return socket.getInetAddress();
	}
}
