package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;
import org.jsfml.system.Vector2f;

/**
 * Created by chris on 2/18/15.
 */
public class Packet02Move extends Packet {

    private String username;
    private Vector2f position;

    public Packet02Move(byte[] data) {

        super(02);
        String information[] = readData(data).split(",");
        System.out.println(information);

        this.username = information[0];
        this.position = new Vector2f(Float.parseFloat(information[1]), 0f);
        this.position = new Vector2f(position.x, Float.parseFloat(information[2]));
    }

    public Packet02Move(String username, Vector2f position) {

        super(02);
        this.username = username;
        this.position = position;
    }

    @Override
    public void writeData(Client client) {

        client.sendData(getData());
    }

    @Override
    public void writeData(Server server) {

        server.globalSendData(getData());
    }

    @Override
    public byte[] getData() {

        return ("02" + this.username + "," + this.position.x + "," + (int)this.position.y).getBytes();
    }

    public String getUsername() {

        return username;
    }

    public Vector2f getPosition() {

        return position;
    }

}
