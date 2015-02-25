package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;
import org.jsfml.system.Vector2f;

/**
 * Created by chris on 2/18/15.
 */
public class Packet00Login extends Packet {

    private String username;

    private Vector2f gamePosition;

    public Packet00Login(byte[] data) {

        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        gamePosition = new Vector2f(Float.parseFloat(dataArray[1]), 0);
        gamePosition = new Vector2f(gamePosition.x, Float.parseFloat(dataArray[2]));
    }

    public Packet00Login(String username, Vector2f gamePosition){

        super(00);
        this.username = username;
        this.gamePosition = gamePosition;
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

        return ("00" + this.username + "," + gamePosition.x + "," + gamePosition.y).getBytes();
    }

    public String getUsername(){

        return username;
    }

    public Vector2f getGamePosition() {

        return gamePosition;
    }
}
