package com.classes.net;

import com.classes.Game;
import com.classes.PlayerMP;
import com.classes.net.packets.Packet;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Chris on 2/16/2015.
 */
public class Server extends Thread {

    private Game game;

    private ArrayList<PlayerMP> connectedPlayers;

    private DatagramSocket socket;

    private int portNumber;

    public Server(Game game) {

        this.game = game;
        connectedPlayers = new ArrayList<PlayerMP>();

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

            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
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

    public void globalSendData(byte[] data) {

        for(PlayerMP currentPlayer: connectedPlayers) {

            sendData(data, currentPlayer.getIPAddress(), currentPlayer.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress ipAddress, int port) {

        String message = new String(data).trim();

        Packet.PacketTypes type = Packet.lookUpPacket(message.substring(0,2));

    }

}
