package com.techinsiderpro.server;

import com.techinsiderpro.common.events.Dispatcher;
import com.techinsiderpro.common.events.Event;
import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.game.GridObject;
import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastReceiver;
import com.techinsiderpro.common.ui.MainWindow;
import com.techinsiderpro.server.events.MovementHandler;
import com.techinsiderpro.server.game.Game;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class Server
{

	private List<Connection> connections;
	private Game game;
	private MainWindow mainWindow;

	private Server(int clientCount, String ip, int port)
	{
		connections = new ArrayList<>();

		//Before Setup
		setupWindow();
		waitForClients(clientCount, ip, port);

		//Setup
		game = new Game(25, 25);
		game.getGridObjectContainer().add(new GridObject(new Position(1, 1), Direction.DOWN));
		new MovementHandler(game.getDispatcher());

		//After Setup
		listenForEventsFromConnections(game.getDispatcher());

		System.out.println(game.getGridObjectContainer().get(0));

		while (true)
		{
			try
			{
				Thread.sleep(250);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			send(game.getGridObjectContainer().get(0));
			System.out.println("Current direction : " + game.getGridObjectContainer().get(0).getDirection());
		}
	}

	private void setupWindow()
	{
		mainWindow = new MainWindow(720, 480);
		mainWindow.setContentPane(new JPanel()
		{
			{
				//setFont(new Font("ariel", Font.PLAIN, getHeight() / 10));
			}

			@Override
			public void paint(Graphics g)
			{
				super.paint(g);

				for (int i = 0; i < connections.size(); i++)
				{
					g.drawString(connections.get(i).getInetAddress().toString(), 0, getHeight() / connections.size() * (i + 1));
				}
			}
		});
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

				Connection connection = new Connection(packet.getAddress(), port + 1);
				connections.add(connection);

				System.out.println("Added new connection : " + connection.getInetAddress().toString());

				mainWindow.repaint();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void listenForEventsFromConnections(final Dispatcher dispatcher)
	{
		for (final Connection connection : connections)
		{
			new Thread()
			{
				@Override
				public void run()
				{
					super.run();

					while (this.isAlive())
					{
						Object object = connection.read();

						if (object instanceof Event)
						{
							System.out.println("Received event " + object.toString());

							dispatcher.dispatch(convertToLocalEvent((Event) object));
						}
					}
				}
			}.start();
		}
	}

	private Event convertToLocalEvent(Event event)
	{
		try
		{
			for (Method method : event.getClass().getMethods())
			{
				if (method.getReturnType().equals(GridObject.class))
				{
					event.getClass()
							.getMethod("set" + method.getName().substring(3, method.getName().length()), GridObject.class)
							.invoke(event, getLocalGridObject((GridObject) method.invoke(event)));
				}
			}
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return event;
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
		for (Connection connection : connections)
		{
			connection.write(object);
		}
	}

	public static void main(String[] args)
	{
		new Server(1, "230.1.1.1", 12345);
	}
}
