package com.classes.net;

import com.classes.Game;

import java.io.IOException;
import java.net.*;

/**
 * Created by Chris on 2/16/2015.
 */
public class Server extends Thread {

    private Game game;

    private DatagramSocket socket;

    private int portNumber;

    public Server(Game game) {

        this.game = game;

        portNumber = 2015;

        try {
            socket = new DatagramSocket(portNumber);

        } catch (ExceptionInInitializerError e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();
        }

        System.out.println("Server created");

    }


    public void run() {

        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String message = new String(packet.getData());
            System.out.println("CLIENT SENT: " + message);

            if(message.trim().equals("ping")) {
                System.out.println("Returning pong");
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }


    public void sendData(byte[] data, InetAddress ipAddress, int portNumber) {

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, portNumber);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
