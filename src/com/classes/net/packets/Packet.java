package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;

/**
 * Created by chris on 2/18/15.
 */
public abstract class Packet {

    /**
     * Enum with different packet types
     */
    public static enum PacketTypes {
        INVALID(-1),
        LOGIN(00),
        DISCONNECT(02);

        private int packetID;

        private PacketTypes(int packetID) {

            this.packetID = packetID;
        }

        public int getID(){

            return packetID;
        }
    }


    /**
     * Creates a packet using the packetID
     */
    public byte packetID;

    public Packet(int packetID) {

        this.packetID = (byte) packetID;
    }



    public abstract void writeData(Client client);

    public abstract void writeData(Server server);

    public abstract byte[] getData();


    public String readData(byte[] data) {

        String message = new String(data).trim();

        return message.substring(2);
    }

    public static PacketTypes lookUpPacket(String message) {

        try{

            int id = Integer.parseInt(message);
            return lookUpPacket(id);

        }catch (NumberFormatException e) {

            e.printStackTrace();
            System.out.println("Packet id is not valid integer");
            return PacketTypes.INVALID;
        }

    }


    public static PacketTypes lookUpPacket(int id) {

        for(PacketTypes packet: PacketTypes.values()){

            if(packet.getID() == id)
                return packet;
        }

        return PacketTypes.INVALID;

    }

}
