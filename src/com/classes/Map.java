package com.classes;

import com.classes.net.packets.Packet04EntityPosition;
import com.classes.util.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 2/12/2015.
 */
public class Map implements Drawable {

    private boolean hasLoaded;

    private Vector2i mapDimensions;

    private java.util.Map<String, PlayerMP> playerList;

    private ArrayList<Entity> entityList;

    private Tile[][] tileList;

    private Vector2i tileDimensions;

    private ArrayList<Room> roomList;

    private int numOfDungeons;

    public Map() {

        playerList = new HashMap<String, PlayerMP>();
        entityList = new ArrayList<Entity>();
        hasLoaded = false;
    }

    public Map(Vector2i mapDimensions, int minNumOfDungeons, int varianceInNumOfDungeons) {

        this.tileDimensions = new Vector2i(32, 32);
        this.mapDimensions = mapDimensions;

        playerList = new HashMap<String, PlayerMP>();
        entityList = new ArrayList<Entity>();
        roomList = new ArrayList<Room>();
        tileList = new Tile[mapDimensions.x][mapDimensions.y];

        numOfDungeons = (int) (Math.random() * varianceInNumOfDungeons) + minNumOfDungeons;

        addRooms();
        addHallways();

        instantiateEntities();
        //instantiateNPCs();

        hasLoaded = true;
    }

    private boolean checkForIntersections(Room currentRoom) {

        boolean intersects = false;

        for (Room checkFor : roomList)
            if (currentRoom.getCornerCoords().intersection(checkFor.getCornerCoords()) != null)
                intersects = true;

        return intersects;
    }

    public java.util.Map<String, PlayerMP> getPlayerList() {

        return playerList;
    }

    public void addPlayer(String name, PlayerMP playerToAdd) {

        playerList.put(name, playerToAdd);
    }

    public void removePlayer(String name) {

        playerList.remove(name);
    }

    public void update() {

        for (String key : playerList.keySet()) {
            if (playerList.get(key) != null)
                playerList.get(key).update();
        }

        for (Entity entity : entityList) {
            entity.update();
        }


    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for (Tile[] horizontalTiles : tileList) {
            for (Tile current : horizontalTiles) {
                if (current != null)
                    current.draw(renderTarget, renderStates);
            }
        }

        for (Entity entity : entityList) {
            entity.draw(renderTarget, renderStates);
        }

        for (String key : playerList.keySet()) {
            playerList.get(key).draw(renderTarget, renderStates);
        }
    }

    private void instantiateNPCs() {

        int numOfNPCs = 5;

        for(int i = 0; i < numOfNPCs; i++) {

            Vector2i randomGamePos = VectorFunctions.randomNum(new Vector2i(-100, 100), new Vector2i(-100, 100));

            NPC current = new NPC("yoda", new Vector2f(randomGamePos));
            entityList.add(current);
        }
    }

    private void instantiateEntities() {

        int numOfEntities = 10;

        for(int i = 0; i < numOfEntities; i++) {

            Vector2i randomGamePos = VectorFunctions.randomNum(new Vector2i(100, 300), new Vector2i(0, 200));

            Entity current = new Entity("Red_Rock", new Vector2f(randomGamePos));
            entityList.add(current);
        }
    }

    private void addRooms() {

        int currentNumOfDungeons = 0;

        while (currentNumOfDungeons < numOfDungeons) {

            Vector2i roomCoords = VectorFunctions.randomNum(new Vector2i(0, mapDimensions.x), new Vector2i(0, mapDimensions.y));

            Room currentRoom = new Room(roomCoords, TileType.getRandomTileType());

            if (!checkForIntersections(currentRoom)) {

                int farRightXCoords = currentRoom.getCornerCoords().left + currentRoom.getCornerCoords().width;
                int farBottomYCoords = currentRoom.getCornerCoords().top + currentRoom.getCornerCoords().height;

                if (farRightXCoords < mapDimensions.x)
                    if (farBottomYCoords < mapDimensions.y) {

                        for (int i = roomCoords.x; i < roomCoords.x + currentRoom.getRoomDimensions().x; i++) {
                            for (int j = roomCoords.y; j < roomCoords.y + currentRoom.getRoomDimensions().y; j++) {

                                Vector2i gamePosition = Vector2i.componentwiseMul(new Vector2i(i, j), tileDimensions);
                                tileList[i][j] = new Tile(currentRoom.getTileTypeIndex(), tileDimensions, gamePosition, new Vector2i(i, j), tileList);
                            }
                        }

                        currentNumOfDungeons++;
                        roomList.add(currentRoom);
                    }
            }
        }
    }

    private void addHallways() {

        /**
         * Generates hallways between the center point of two rooms in the roomList
         * by using the first room's x value as the hallway x value and the second room's
         * y value as the hallway's y value
         */

        for (int i = 0; i < roomList.size() - 1; i++) {

            Vector2i firstCenter = roomList.get(i).getCenterTilePosition();
            Vector2i secondCenter = roomList.get(i + 1).getCenterTilePosition();

            Vector2i difference = Vector2i.sub(secondCenter, firstCenter);

            //Find which directions are needed to go for the connecting hallways
            Vector2i directions = VectorFunctions.getSign(difference);

            for (int a = 0; a <= Math.abs(difference.x); a++) {

                if (tileList[(a * directions.x) + firstCenter.x][firstCenter.y] == null) {

                    Vector2i gamePosition = Vector2i.componentwiseMul(new Vector2i((a * directions.x) + firstCenter.x, firstCenter.y), tileDimensions);
                    Vector2i boardPos = new Vector2i((a * directions.x) + firstCenter.x, firstCenter.y);

                    tileList[(a * directions.x) + firstCenter.x][firstCenter.y] = new Tile(roomList.get(i).getTileTypeIndex(), tileDimensions, gamePosition, boardPos, tileList);
                }
            }

            /**
             * Although earlier the second room was compared relative to the first for the x-coords,
             * we are now using the y value of the second room relative to the first. Because of that,
             * we need to invert the signs of the directions
             */
            directions = Vector2i.neg(directions);

            for (int a = 0; a <= Math.abs(difference.y); a++) {

                if (tileList[secondCenter.x][(a * directions.y) + secondCenter.y] == null) {

                    Vector2i gamePosition = Vector2i.componentwiseMul(new Vector2i(secondCenter.x, (a * directions.y) + secondCenter.y), tileDimensions);
                    Vector2i boardPos = new Vector2i(secondCenter.x, (a * directions.y) + secondCenter.y);

                    tileList[secondCenter.x][(a * directions.y) + secondCenter.y] = new Tile(roomList.get(i).getTileTypeIndex(), tileDimensions, gamePosition, boardPos, tileList);
                }
            }

        }
    }

    public void setMap(int[][] mapTileData, Vector2i tileDimensions) {

        System.out.println("Setting map with server data");

        this.tileDimensions = tileDimensions;
        tileList = new Tile[mapTileData.length][mapTileData[0].length];
        this.mapDimensions = new Vector2i(mapTileData.length, mapTileData[0].length);

        for (int i = 0; i < mapTileData.length; i++) {
            for (int d = 0; d < mapTileData[i].length; d++) {

                if (mapTileData[i][d] != 0) {

                    Vector2i boardPos = new Vector2i(i, d);
                    Vector2i gamePosition = Vector2i.componentwiseMul(boardPos, tileDimensions);

                    Tile currentTile = new Tile(mapTileData[i][d], tileDimensions, gamePosition, boardPos, tileList);
                    tileList[i][d] = currentTile;
                }
            }
        }

        hasLoaded = true;
    }

    public void addEntity(Packet04EntityPosition entityPos) {

        Entity entity = new Entity(entityPos.getEntityName(), entityPos.getGamePosition());

        entityList.add(entity);

    }


    public boolean hasLoaded() {

        return hasLoaded;
    }

    public Vector2i getTileDimensions() {

        return tileDimensions;
    }

    public ArrayList<Entity> getEntityList() {

        return entityList;
    }

    public Tile[][] getTileList() {

        return tileList;
    }

    public Vector2i getRandomTilePosition() {

        Vector2i xRange = new Vector2i(0, tileList.length - 1);
        Vector2i yRange = new Vector2i(0, tileList[0].length - 1);

        Vector2i randomTile = VectorFunctions.randomNum(xRange, yRange);

        return tileList[randomTile.x][randomTile.y].getGamePosition();
    }
}
