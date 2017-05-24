package com.techinsiderpro.server;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.server.event.UpdateEvent;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.server.event.handler.CollisionHandler;
import com.techinsiderpro.server.event.handler.MovementHandler;
import com.techinsiderpro.server.event.handler.UpdateHandler;
import com.techinsiderpro.common.game.Game;
import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.Player;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastReceiver;
import com.techinsiderpro.common.ui.EntityContainerPanel;
import com.techinsiderpro.common.ui.Window;

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
	private Window window;

	private Server(int clientCount, String ip, int port)
	{
		connections = new ArrayList<>();

		//Before Setup
		setupWindow();
		waitForClients(clientCount, ip, port);

		int gameSize = 25;

		//Setup
		game = new Game(gameSize, gameSize);

		for (int i = 0; i < connections.size(); i++)
		{
			game.getEntityContainer().add(new Player(new PositionComponent(gameSize / 2, gameSize / 2 + i % 2),
			                                         (i % 2 == 0) ? DirectionComponent.DOWN : DirectionComponent.UP));
		}

		game.getDispatcher().registerHandler(new MovementHandler(game.getEntityContainer(), game.getDispatcher()));
		game.getDispatcher().registerHandler(new UpdateHandler(game.getEntityContainer(), game.getDispatcher()));
		game.getDispatcher().registerHandler(new CollisionHandler(game.getDispatcher()));

		window.setContentPane(new EntityContainerPanel(game.getEntityContainer()));

		//After Setup
		listenForEventsFromConnections(game.getDispatcher());

		while (true)
		{
			try
			{
				game.getDispatcher().dispatch(new UpdateEvent());

				Thread.sleep(200);

				send(game.getEntityContainer());

				window.repaint();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void setupWindow()
	{
		window = new Window(720, 720);
		window.setContentPane(new JPanel()
		{
			{
				setFont(new Font("ariel", Font.PLAIN, 96));
			}

			@Override
			public void paint(Graphics g)
			{
				super.paint(g);

				for (int i = 0; i < connections.size(); i++)
				{
					g.drawString(connections.get(i).getInetAddress().toString(), 0,
					             getHeight() / connections.size() * (i + 1));
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

				window.repaint();
			}
		}
		catch (InterruptedException e)
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

					while (this.isAlive() && connection.isConnected())
					{
						Object object = connection.read();

						if (object instanceof Event)
						{
							dispatcher.dispatch(convertToLocalEvent((Event) object));
						}
					}

					connections.remove(connection);

					this.stop();
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
				if (method.getReturnType().equals(Entity.class))
				{
					Entity localEntity = getLocalGridObject((Entity) method.invoke(event));

					if (localEntity == null)
					{
						System.out.println("Error converting to local gridObject");
					}
					else
					{
						event.getClass()
						     .getMethod("set" + method.getName().substring(3, method.getName().length()),
						                Entity.class)
						     .invoke(event, localEntity);
					}
				}
			}
		}
		catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return event;
	}

	private Entity getLocalGridObject(Entity remoteEntity)
	{
		for (Entity entity : game.getEntityContainer())
		{
			if (entity.equals(remoteEntity))
			{
				return entity;
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
