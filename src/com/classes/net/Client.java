package com.classes.net;

import com.classes.Game;

import java.io.IOException;
import java.net.*;

/**
 * Created by Chris on 2/16/2015.
 */
public class Client extends Thread {

    private Game game;

    private InetAddress ipAddress;

    private DatagramSocket socket;

    private int portNumber;

    public Client(Game game, String ipAddress) {

        this.game = game;

        portNumber = 2015;

        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
            socket = new DatagramSocket();

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (SocketException e) {

            e.printStackTrace();
        }

        System.out.println("Client created");
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

            //System.out.println("SERVER RETURNED: " + new String(packet.getData()));

        }
    }


    public void sendData(byte[] data) {

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, portNumber);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
