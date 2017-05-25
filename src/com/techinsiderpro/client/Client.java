package com.techinsiderpro.client;

import com.techinsiderpro.common.game.entity.container.EntityContainer;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastBroadcaster;
import com.techinsiderpro.common.ui.EntityContainerPanel;
import com.techinsiderpro.common.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client
{
	private Window window;

	private Client()
	{
		window = new Window("Client", 720, 720);

		window.setLayout(new BorderLayout());

		window.add(new JPanel()
		{
			{
				final JTextField ipTextField = new JTextField("230.1.1.1");
				JButton joinGameButton = new JButton("Join Game")
				{
					{
						addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								connect(ipTextField.getText(), 12345);
							}
						});
					}
				};

				setLayout(new BorderLayout());

				add(ipTextField, BorderLayout.NORTH);
				add(joinGameButton, BorderLayout.SOUTH);
			}
		}, BorderLayout.CENTER);
	}

	private void connect(String ip, int port)
	{
		Connection connection = waitForConnectionToServer(ip, port);

		EntityContainerPanel entityContainerPanel = new EntityContainerPanel();

		window.add(entityContainerPanel, BorderLayout.CENTER);

		while (connection.isConnected())
		{
			updateEntityContainerPanel(entityContainerPanel, connection);
			window.repaint();
		}
	}

	private void sendJoinGameRequest(String ip, int port)
	{
		MulticastBroadcaster multicastBroadcaster = new MulticastBroadcaster(ip, port);

		multicastBroadcaster.send("JoinGameRequest".getBytes());
		System.out.println("Sent JoinGameRequest...");
	}

	private Connection waitForConnection(int port)
	{
		System.out.println("Waiting for connection to server...");

		Connection connection = null;

		try
		{
			ServerSocket serverSocket = new ServerSocket(port);

			Socket socket = serverSocket.accept();
			connection = new Connection(socket);

			System.out.println("Connected...");
		}
		catch (SocketTimeoutException e)
		{
			System.out.println("No response...");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return connection;
	}

	private Connection waitForConnectionToServer(String ip, int port)
	{
		Connection connection = null;

		while (connection == null)
		{
			try
			{
				Thread.sleep(1000);

				sendJoinGameRequest(ip, port);
				connection = waitForConnection(port + 1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		return connection;
	}

	private Window setupWindow()
	{
		return new Window(720, 720);
	}

	private EntityContainer waitForEntityContainerFromServer(Connection connection)
	{
		Object object = null;

		while (!(object instanceof EntityContainer))
		{
			object = connection.read();
		}

		return (EntityContainer) object;
	}

	private void updateEntityContainerPanel(EntityContainerPanel entityContainerPanel, Connection connection)
	{
		entityContainerPanel.setEntityContainer(waitForEntityContainerFromServer(connection));
	}

	public static void main(String[] args)
	{
		new Client();
	}
}
