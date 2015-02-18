package com.classes.net;

import com.classes.Game;
import com.classes.Player;
import com.classes.PlayerMP;
import com.classes.net.packets.Packet;
import com.classes.net.packets.Packet00Login;
import org.jsfml.system.Vector2f;

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

        switch(type) {

            case LOGIN:
                Packet00Login loginPacket = new Packet00Login(data);
                System.out.println(loginPacket.getUsername() + " connected at " + ipAddress + ":" + port);

                PlayerMP player = new PlayerMP(loginPacket.getUsername(), "yoda", new Vector2f(0,0), 15, ipAddress, port);


                if(ipAddress.equals("127.0.0.1")) {
                    player = new PlayerMP(game.getGameView(), loginPacket.getUsername(), "yoda", new Vector2f(20,20), 15, ipAddress, port);
                    game.setMainPlayer(player);
                }

                game.getCurrentMap().addPlayer(loginPacket.getUsername(), player);
                connectedPlayers.add(player);

                break;

            case DISCONNECT:
                break;

            case INVALID:
                System.out.println("INVALID PACKET RECIEVED");
                break;


        }

    }

}
