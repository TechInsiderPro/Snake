package com.techinsiderpro.client;

import com.techinsiderpro.common.event.DirectionChangeRequestEvent;
import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.container.EntityContainer;
import com.techinsiderpro.common.net.Connection;
import com.techinsiderpro.common.net.MulticastBroadcaster;
import com.techinsiderpro.common.ui.EntityContainerPanel;
import com.techinsiderpro.common.ui.Window;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Client
{
	public Client()
	{
		Window window = new Window(720, 720);
		EntityContainerPanel entityContainerPanel = new EntityContainerPanel();

		window.setContentPane(entityContainerPanel);

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
				}
				catch (SocketTimeoutException e)
				{
					System.out.println("No response");
				}
			}

			System.out.println("Connected");

			while (true)
			{
				Thread.sleep(16);

				EntityContainer entityContainer = (EntityContainer) connection.read();

				entityContainerPanel.setEntityContainer(entityContainer);
				window.repaint();

//				for (Entity entity : entityContainer)
//				{
				Entity entity = (Entity) entityContainer.toArray()[0];

				System.out.println(entity.getComponent(PositionComponent.class).getX());
				DirectionComponent directionComponent = DirectionComponent.values()[new Random().nextInt(
						DirectionComponent.values().length)];
				connection.write(new DirectionChangeRequestEvent(directionComponent, entity));
//				}
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new Client();
	}
}
