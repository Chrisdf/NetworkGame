package com.classes.net;

import com.classes.Game;

import java.net.*;

/**
 * Created by Chris on 2/16/2015.
 */
public class Client extends Thread {

    private Game game;

    private InetAddress ipAddress;

    private DatagramSocket socket;

    public Client(Game game, String ipAddress) {

        this.game = game;

        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
            socket = new DatagramSocket();

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (SocketException e) {

            e.printStackTrace();
        }

    }


    public void run() {

        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        try {
            socket.receive(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
