package com.classes;

import com.classes.util.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 2/12/2015.
 */
public class Map implements Drawable {

    private Vector2i mapDimensions;

    private java.util.Map<String, PlayerMP> playerList;

    private ArrayList<Entity> entityList;

    private Tile[][] tileList;

    private Vector2i tileDimensions;

    private ArrayList<Room> roomList;

    private int numOfDungeons;

    public Map(Vector2i mapDimensions) {

        this.tileDimensions = new Vector2i(32, 32);
        this.mapDimensions = mapDimensions;

        playerList = new HashMap<String, PlayerMP>();
        entityList = new ArrayList<Entity>();
        roomList = new ArrayList<Room>();
        tileList = new Tile[mapDimensions.x][mapDimensions.y];

        int minNumOfDungeons = 4;
        int varianceInNumOfDungeons = 13;
        numOfDungeons = (int) (Math.random() * varianceInNumOfDungeons) + minNumOfDungeons;

        addRooms();
        addHallways();

    }

    public Map(Vector2i mapDimensions, int numOfDungeons) {

        new Map(mapDimensions);
        this.numOfDungeons = numOfDungeons;
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

                                tileList[i][j] = new Tile(currentRoom.getTileType(), tileDimensions, gamePosition, new Vector2i(i, j), tileList);
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

                    tileList[(a * directions.x) + firstCenter.x][firstCenter.y] = new Tile(roomList.get(i).getTileType(), tileDimensions, gamePosition, boardPos, tileList);
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

                    System.out.println(boardPos);

                    tileList[secondCenter.x][(a * directions.y) + secondCenter.y] = new Tile(roomList.get(i).getTileType(), tileDimensions, gamePosition, boardPos, tileList);
                }
            }

        }
    }

}
