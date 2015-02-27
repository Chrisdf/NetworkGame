package com.classes;

import com.classes.util.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

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

    private int varianceInNumOfDungeons;

    private int minNumOfDungeons;

    private int numOfDungeons;

    public Map(Vector2i mapDimensions) {

        this.tileDimensions = new Vector2i(100,100);
        this.mapDimensions = mapDimensions;

        playerList = new HashMap<String, PlayerMP>();
        entityList = new ArrayList<Entity>();
        roomList = new ArrayList<Room>();
        tileList = new Tile[mapDimensions.x][mapDimensions.y];

        minNumOfDungeons = 4;
        varianceInNumOfDungeons = 13;
        numOfDungeons = (int) (Math.random() * varianceInNumOfDungeons) + minNumOfDungeons;

        addRooms();
        addHallways();

    }

    public Map(Vector2i mapDimensions, int numOfDunegeons) {

        new Map(mapDimensions);
        this.numOfDungeons = numOfDunegeons;
    }

    private boolean checkForIntersections(Room currentRoom) {

        boolean intersects = false;

        for(Room checkFor: roomList)
            if(currentRoom.getCornerCoords().intersection(checkFor.getCornerCoords()) != null)
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

        for (Tile[] horizontalTiles: tileList) {
            for (Tile current : horizontalTiles) {
                if(current != null)
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

            Room currentRoom = new Room(roomCoords, Theme.getRandomTheme());

            if (checkForIntersections(currentRoom) == true)
                currentRoom = null;

            else {

                int farRightXCoords = currentRoom.getCornerCoords().left + currentRoom.getCornerCoords().width;
                int farBottomYCoords = currentRoom.getCornerCoords().top + currentRoom.getCornerCoords().height;

                if (farRightXCoords < mapDimensions.x)
                    if (farBottomYCoords < mapDimensions.y) {

                        for (int i = roomCoords.x; i < roomCoords.x + currentRoom.getRoomDimensions().x; i++) {
                            for (int j = roomCoords.y; j < roomCoords.y + currentRoom.getRoomDimensions().y; j++) {

                                Vector2i gamePosition = Vector2i.componentwiseMul(new Vector2i(i, j), tileDimensions);

                                tileList[i][j] = new Tile(currentRoom.getTheme(), tileDimensions, gamePosition, new Vector2i(i, j), tileList);
                            }
                        }

                        currentNumOfDungeons++;
                        roomList.add(currentRoom);
                    }
            }
        }
    }

    private void addHallways() {

        for(int i = 0; i < roomList.size()-1; i++) {

            Vector2i firstCenter = roomList.get(i).getCenterTilePosition();
            Vector2i secondCenter = roomList.get(i+1).getCenterTilePosition();

            //Find which directions are needed to go for the connecting hallways
            Vector2i directions = VectorFunctions.getSign(Vector2i.sub(firstCenter, secondCenter));

        }
    }

}
