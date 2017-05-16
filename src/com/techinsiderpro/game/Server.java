package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;
import com.techinsiderpro.events.Event;
import com.techinsiderpro.net.MulticastReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class Server
{

	private Collection<Socket> connections;
	private Game game;

	private Server(int clientCount, String ip, int port)
	{
		connections = new ArrayList<>();

		//Before Setup
		waitForClients(clientCount, ip, port);

		//Setup
		game = new Game(25, 25);
		game.getGridObjectContainer().add(new GridObject(new Position(1, 1), Direction.DOWN));

		//After Setup
		listenForEventsFromConnections(game.getDispatcher());

		while (true)
		{
			try
			{
				Thread.sleep(200);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			send(game.getGridObjectContainer().getGridObjectAt(new Position(1, 1)));
			System.out.println(game.getGridObjectContainer().getGridObjectAt(new Position(1, 1)).getDirection());
		}
	}

	private void waitForClients(int clientCount, String ip, int port)
	{
		try
		{
			MulticastReceiver multicastReceiver = new MulticastReceiver(ip, port);

			System.out.println("Waiting for clients to connect on " + multicastReceiver.inetAddress.toString());

			while (connections.size() < clientCount)
			{
				DatagramPacket packet = multicastReceiver.receive();
				System.out.println("Received packet : " + packet.toString());

				Thread.sleep(1000);

				connections.add(new Socket(packet.getAddress(), port));

				System.out.println("Added new connection");
			}
		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void listenForEventsFromConnections(final Dispatcher dispatcher)
	{
		for (final Socket connection : connections)
		{
			new Thread()
			{
				@Override
				public void run()
				{
					super.run();

					try
					{
						ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());

						while (this.isAlive())
						{
							Object object = objectInputStream.readObject();

							System.out.println("Received event " + object.toString());

							if (object instanceof Event)
							{
								Event event = (Event) object;

								try
								{
									for (Method method : event.getClass().getMethods())
									{
										if (method.getReturnType().equals(GridObject.class))
										{
											event.getClass().getMethod("set" + method.getName().substring(3, method.getName().length()), GridObject.class).invoke(event, getLocalGridObject((GridObject) method.invoke(event)));
										}
									}
								} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
								{
									e.printStackTrace();
								}

								dispatcher.dispatch(event);
							}
						}
					} catch (IOException | ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	private GridObject getLocalGridObject(GridObject remoteGridObject)
	{
		for (GridObject gridObject : game.getGridObjectContainer())
		{
			if (gridObject.equals(remoteGridObject))
			{
				return gridObject;
			}
		}

		return null;
	}

	public void send(Object object)
	{
		for (Socket connection : connections)
		{
			try
			{
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

				objectOutputStream.writeObject(object);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		new Server(1, "230.1.1.1", 12345);
	}
}
