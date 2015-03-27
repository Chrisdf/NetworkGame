package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;
import org.jsfml.system.Vector2f;

/**
 * Created by chris on 2/18/15.
 */
public class Packet04EntityPosition extends Packet {

    private String entityName;

    private Vector2f gamePosition;

    public Packet04EntityPosition(byte[] data) {

        super(04);
        String[] dataArray = readData(data).split(",");
        this.entityName = dataArray[0];
        gamePosition = new Vector2f(Float.parseFloat(dataArray[1]), 0);
        gamePosition = new Vector2f(gamePosition.x, Float.parseFloat(dataArray[2]));
    }

    public Packet04EntityPosition(String entityName, Vector2f gamePosition){

        super(04);
        this.entityName = entityName;
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

        return ("04" + this.entityName + "," + gamePosition.x + "," + gamePosition.y).getBytes();
    }

    public String getEntityName(){

        return entityName;
    }

    public Vector2f getGamePosition() {

        return gamePosition;
    }
}
