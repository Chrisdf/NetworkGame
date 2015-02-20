package com.classes.net;

import com.classes.Game;
import com.classes.PlayerMP;
import com.classes.net.packets.Packet;
import com.classes.net.packets.Packet00Login;
import com.classes.net.packets.Packet01Disconnect;
import org.jsfml.system.Vector2f;

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

        portNumber = 12345;

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

            /**
             * Constantly look for new packets, once received extract the contents into a packet
             * for later examination
             */
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


    public void sendData(byte[] data) {

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, portNumber);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePacket(byte[] data, InetAddress ipAddress, int portNumber) {

        //Convert the byte array into strings and remove extra unneeded characters
        String message = new String(data).trim();

        //Find out what type of packet it is based on the first two strings in the data string
        Packet.PacketTypes type = Packet.lookUpPacket(message.substring(0, 2));

        //Based off the first two letters, see what type of packet it is
        switch (type) {

            case LOGIN:

                Packet00Login loginPacket = new Packet00Login(data);

                System.out.println("Client received other client named " + loginPacket.getUsername());

                PlayerMP newPlayer = new PlayerMP(loginPacket.getUsername(), "yoda", new Vector2f(0, 0), 15, ipAddress, portNumber);

                if (loginPacket.getUsername().equals(game.getUsername())) {

                    System.out.println(game.getUsername() + " has had main player set");
                    game.setMainPlayer(newPlayer);
                    game.getCurrentMap().addPlayer(loginPacket.getUsername(), newPlayer);
                }

                break;

            case DISCONNECT:

                Packet01Disconnect disconnectPacket = new Packet01Disconnect(data);

                System.out.println("Client " + disconnectPacket.getUsername() + " disconnected");

                game.getCurrentMap().removePlayer(disconnectPacket.getUsername());

                break;

            case INVALID:
                System.out.println("INVALID PACKET RECEIVED AS " + message);
                break;


        }
    }

}
