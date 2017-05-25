package com.techinsiderpro.server;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.game.Game;
import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.Player;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.OwnedComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastReceiver;
import com.techinsiderpro.common.ui.Window;
import com.techinsiderpro.server.event.UpdateEvent;
import com.techinsiderpro.server.event.handler.CollisionHandler;
import com.techinsiderpro.server.event.handler.EntityRemovalHandler;
import com.techinsiderpro.server.event.handler.MovementHandler;
import com.techinsiderpro.server.event.handler.UpdateHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
	private List<Connection> connections;
	private Game game;
	private boolean close;
	private Thread waitForClientsThread, hostGameThread;

	private Server()
	{
		final Window window = new Window("Server", 250, 150);

		window.setContentPane(new JPanel()
		{
			{
				final int port = 12345;

				final JTextField ipTextField = new JTextField("230.1.1.1");

				final JButton waitForClientsButton = new JButton("Wait for clients")
				{
					{
						addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								if (waitForClientsThread != null && waitForClientsThread.isAlive())
								{
									waitForClientsThread.stop();
								}
								else
								{
									connections = new ArrayList<>();

									waitForClientsThread = new Thread()
									{
										@Override
										public void run()
										{
											super.run();

											try
											{
												MulticastReceiver multicastReceiver =
														new MulticastReceiver(ipTextField.getText(), port);

												System.out.println("Waiting for clients to connect on " +
														multicastReceiver.inetAddress.toString());

												while (isAlive())
												{
													DatagramPacket packet = multicastReceiver.receive();
													System.out.println("Received packet : " + packet.toString());

													Thread.sleep(1000);

													Connection connection =
															new Connection(packet.getAddress(), port + 1);
													connections.add(connection);

													System.out.println("Added new connection : " +
															connection.getRemoteInetAddress().toString());
												}
											} catch (InterruptedException e)
											{
												e.printStackTrace();
											}
										}
									};

									waitForClientsThread.start();
								}
							}
						});
					}
				};

				JLabel clientCountLabel = new JLabel()
				{
					@Override
					public void repaint()
					{
						if (connections != null)
						{
							setText("Clients : " + connections.size());
						}
					}
				};

				final JButton hostGameButton = new JButton("Host Game")
				{
					{
						addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								if (hostGameThread == null)
								{
									if (waitForClientsThread != null && waitForClientsThread.isAlive())
										waitForClientsThread.stop();

									host();
									hostGameThread.start();
								}
								else if (hostGameThread.isAlive())
								{
									hostGameThread.stop();

									if (connections != null)
									{
										for (Connection connection : connections)
										{
											connection.close();
										}

										close = true;
									}

									window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
								}
								else
								{
									hostGameThread.start();
								}
							}
						});
					}
				};

				setLayout(new BorderLayout());

				add(ipTextField, BorderLayout.PAGE_START);
				add(clientCountLabel, BorderLayout.LINE_START);
				add(waitForClientsButton, BorderLayout.CENTER);
				add(hostGameButton, BorderLayout.LINE_END);
			}
		});
	}

	private void host()
	{
		//Setup
		final Game game = setupGame(25);

		//After Setup
		listenForEventsFromConnections(game.getDispatcher());

		hostGameThread = new Thread()
		{
			@Override
			public void run()
			{
				super.run();

				while (isAlive())
				{
					try
					{
						game.getDispatcher().dispatch(new UpdateEvent());

						Thread.sleep(500);

						send(game.getEntityContainer());
					} catch (InterruptedException e)

					{
						e.printStackTrace();
					}
				}
			}
		};
	}

	private Game setupGame(int mapSize)
	{
		game = new Game(mapSize, mapSize);

		System.out.println(connections.size());

		for (int i = 0; i < connections.size(); i++)
		{
			game.getEntityContainer().add(new Player(new PositionComponent(mapSize / connections.size() * i, mapSize / 2), DirectionComponent.DOWN, new OwnedComponent(connections.get(i).getLocalInetAddress())));
		}

		game.getDispatcher().registerHandler(new MovementHandler(game.getEntityContainer(), game.getDispatcher()));
		game.getDispatcher().registerHandler(new UpdateHandler(game.getEntityContainer(), game.getDispatcher()));
		game.getDispatcher().registerHandler(new CollisionHandler(game.getDispatcher()));
		game.getDispatcher().registerHandler(new EntityRemovalHandler(game.getEntityContainer()));

		return game;
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
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
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

	private void send(Object object)
	{
		for (Connection connection : connections)
		{
			connection.write(object);
		}
	}

	public static void main(String[] args)
	{
		new Server();
	}
}
