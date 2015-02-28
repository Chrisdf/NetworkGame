package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;
import com.classes.util.Tile;
import com.classes.util.TileType;

/**
 * Created by chris on 2/18/15.
 */
public class Packet03MapData extends Packet {

    private String username;

    private int[][] tileList;

    public Packet03MapData(byte[] data) {

        super(03);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
    }

    public Packet03MapData(String username, Tile[][] tileList) {

        super(03);
        this.username = username;

        for (int i = 0; i < tileList.length; i++)
            for (int d = 0; d < tileList[i].length; d++) {

                int tileValue = TileType.findIndexByTileType(tileList[i][d].getTileTypeIndex());
                this.tileList[i][d] = tileValue;
            }
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

        return ("03" + this.username).getBytes();
    }

    public String getUsername() {

        return username;
    }

}
