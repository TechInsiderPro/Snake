package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;
import com.techinsiderpro.events.Event;
import com.techinsiderpro.net.MulticastReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class Server {

    private Collection<Socket> connections;

    private Server(int clientCount, String ip, int port) {
        connections = new ArrayList<>();

        //Before Setup
        waitForClients(clientCount, ip, port);

        //Setup
        Game game = new Game(25, 25);

        //After Setup
        listenForEventsFromConnections(game.getDispatcher());
    }

    private void waitForClients(int clientCount, String ip, int port) {
        try {
            MulticastReceiver multicastReceiver = new MulticastReceiver(ip, port);

            while (connections.size() < clientCount) {
                DatagramPacket packet = multicastReceiver.receive();

                Thread.sleep(100);

                connections.add(new Socket(packet.getAddress(), port));

                System.out.println("Added new connection");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void listenForEventsFromConnections(Dispatcher dispatcher) {
        for (final Socket connection : connections) {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());

                        while (this.isAlive()) {
                            Object object = objectInputStream.readObject();

                            System.out.println("Received event " + object.toString());

                            if (object instanceof Event) {
                                dispatcher.dispatch(((Event) object));
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void send(Object object) {
        for (Socket connection : connections) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

                objectOutputStream.writeObject(object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server(1, "230.1.1.1", 12345);
    }
}
