package com.techinsiderpro.game;

import com.techinsiderpro.net.MulticastBroadcaster;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ClientTest {
    public static void main(String[] args)
    {
        try
        {
            MulticastBroadcaster multicastBroadcaster = new MulticastBroadcaster("230.1.1.1", 12345);
            multicastBroadcaster.send("Sup".getBytes());
            System.out.println("Sent sup");

            Socket connection = new ServerSocket(12345).accept();

            System.out.println("Connected");

            ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

            while (true)
            {
                Thread.sleep(1000);

                GridObject gridObject = (GridObject) objectInputStream.readObject();

                Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
                System.out.println(direction);
                objectOutputStream.writeObject(new DirectionChangeRequestEvent(direction, gridObject));
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
