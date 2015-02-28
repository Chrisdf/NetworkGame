package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;
import com.classes.util.Tile;
import com.classes.util.TileType;
import org.jsfml.system.Vector2i;

import java.util.Arrays;

/**
 * Created by chris on 2/18/15.
 */
public class Packet03MapData extends Packet {

    private String username;

    private Vector2i tileDimensions;

    private int[][] tileList;

    public Packet03MapData(byte[] data) {

        /**
         * The game map is a tile matrix represented by an integer matrix for data transfer.
         * Each tile is represented by an int, separated from another tile by the '_' delimiter.
         * A new line on the matrix is represented by the '|' delimiter
         */

        super(03);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        tileDimensions = new Vector2i(Integer.parseInt(dataArray[1]), 0);
        tileDimensions = new Vector2i(tileDimensions.x, Integer.parseInt(dataArray[2]));


        String[] mapRowArray = dataArray[3].split("|");
        System.out.println(Arrays.toString(mapRowArray));

        this.tileList = new int[mapRowArray.length][mapRowArray.length];
        for (int i = 0; i < mapRowArray.length; i++) {

            String[] mapColumnArray = mapRowArray[i].split("_");

            System.out.println(Arrays.toString(mapColumnArray));

            for (int d = 0; d < mapColumnArray.length; d++)
                tileList[i][d] = Integer.parseInt(mapColumnArray[d]);
        }

        printArray();

    }

    public Packet03MapData(String username, Tile[][] tileList, Vector2i tileDimensions) {

        super(03);
        this.tileList = new int[tileList.length][tileList[0].length];
        this.username = username;
        this.tileDimensions = tileDimensions;

        for (int i = 0; i < tileList.length; i++)
            for (int d = 0; d < tileList[i].length; d++) {

                if (tileList[i][d] == null)
                    this.tileList[i][d] = 99;

                else {
                    int tileValue = TileType.findIndexByTileType(tileList[i][d].getTileTypeIndex());
                    this.tileList[i][d] = tileValue;
                }
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

        /**
         * Turns the tileList int matrix into a string matrix with each tile
         * separated by a '_' delimiter and each tile line separated by a
         * '|' delimiter
         */

        String mapData = "";

        for (int i = 0; i < tileList.length - 1; i++) {
            for (int d = 0; d < tileList[i].length; d++) {

                mapData += tileList[i][d] + "_";
            }
            mapData += "|";
        }

        mapData += tileList[tileList.length - 1];


        return ("03" + this.username + "," + tileDimensions.x + "," + tileDimensions.y + "," + mapData).getBytes();
    }

    public String getUsername() {

        return username;
    }

    public Vector2i getTileDimensions() {

        return tileDimensions;
    }

    public int[][] getTileList() {

        return tileList;
    }

    private void printArray() {

        for (int i = 0; i < tileList.length; i++)
            for (int d = 0; d < tileList[i].length; d++)
                System.out.println(tileList[i][d]);
    }

}
