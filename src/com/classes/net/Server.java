package com.classes.net;

import com.classes.Game;
import com.classes.Player;
import com.classes.PlayerMP;
import com.classes.net.packets.Packet;
import com.classes.net.packets.Packet00Login;
import com.classes.net.packets.Packet01Disconnect;
import com.classes.net.packets.Packet02Move;
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

        //Port number for the server, all clients must have matching ports to connect
        portNumber = 3015;

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

        while (!socket.isClosed()) {
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

        //Convert the byte array into strings and remove extra unneeded characters
        String message = new String(data).trim();

        //Find out what type of packet it is based on the first two strings in the data string
        Packet.PacketTypes type = Packet.lookUpPacket(message.substring(0,2));

        //Based off the first two letters, see what type of packet it is
        switch(type) {

            case LOGIN:

                //Instantiate the sent data as a login packet
                Packet00Login loginPacket = new Packet00Login(data);

                if(!usernameUsed(loginPacket.getUsername())) {

                    System.out.println(loginPacket.getUsername() + " connected at " + ipAddress + ":" + port);

                    PlayerMP player = new PlayerMP(loginPacket.getUsername(), "yoda", new Vector2f(0, 0), 15, ipAddress, port);

                    if (ipAddress.toString().equals("/127.0.0.1")) {

                        System.out.println("SERVER HOST DETECTED");
                    }

                    game.getCurrentMap().addPlayer(loginPacket.getUsername(), player);


                    /**
                     * Tell the connecting player about all the players currently in the game
                     */
                    for(PlayerMP current: connectedPlayers)
                        sendData(("00" + current.getUsername() + "," + current.getGamePosition().x + "," + current.getGamePosition().y).getBytes(), ipAddress, port);

                    /**
                     * Add the connecting player to the player list
                     */
                    connectedPlayers.add(player);


                    /**
                     * Tell all the other connected players about the new connection
                     */
                    globalSendData(data);
                }
                break;

            case DISCONNECT:

                Packet01Disconnect disconnectPacket = new Packet01Disconnect(data);

                System.out.println("User " + disconnectPacket.getUsername() + " has disconnected from the server");

                for (int i = 0; i < connectedPlayers.size(); i++) {

                    if (connectedPlayers.get(i).equals(disconnectPacket.getUsername())) {
                        connectedPlayers.remove(i);
                        i--;
                    }
                }


                game.getCurrentMap().removePlayer(disconnectPacket.getUsername());

                System.out.println("Player disconnected");

                /**
                 * Tell all connected clients that the client has disconnected
                 */
                globalSendData(data);

                break;

            case MOVE:

                Packet02Move movePacket = new Packet02Move(data);

                for(PlayerMP current: connectedPlayers) {

                    if(current.getUsername().equals(movePacket.getUsername())) {
                        current.setGamePosition(movePacket.getPosition());
                    }
                }

                globalSendData(data);

                break;

            case INVALID:

                System.out.println("INVALID PACKET RECEIVED");
                break;


        }

    }

    private boolean usernameUsed(String username) {

        for(PlayerMP current: connectedPlayers)
            if(username.equals(current.getUsername()))
                return true;

        return false;

    }


    private boolean hasConnected(InetAddress ipAddress) {

        boolean hasConnected = false;

        for(PlayerMP currentPlayer: connectedPlayers)
            if(currentPlayer.getIPAddress().equals(ipAddress))
                hasConnected = true;

        return hasConnected;

    }

    private boolean hostConnected() {

        boolean hostConnected = false;

        for(PlayerMP currentPlayer: connectedPlayers)
            if(currentPlayer.getIPAddress().toString().equals("/127.0.0.1"))
                hostConnected = true;

        return hostConnected;

    }

    public void closeSocket() {

        socket.close();
    }


}
