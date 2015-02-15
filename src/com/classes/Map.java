package com.classes;

import com.classes.util.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

/**
 * Created by Chris on 2/12/2015.
 */
public class Map implements Drawable {

    private ArrayList<Player> playerList;

    private ArrayList<Entity> entityList;

    private ArrayList<Room> roomList;

    private int varianceInNumOfDungeons;

    private int minNumOfDungeons;

    private int numOfDungeons;

    public Map(IntRect mapDimensions) {

        playerList = new ArrayList<Player>();
        entityList = new ArrayList<Entity>();
        roomList = new ArrayList<Room>();

        minNumOfDungeons = 4;
        varianceInNumOfDungeons = 3;
        numOfDungeons = (int) (Math.random() * varianceInNumOfDungeons) + minNumOfDungeons;

        int currentNumOfDungeons = 0;

        while (currentNumOfDungeons < numOfDungeons) {

            Vector2i roomDimensionsInTiles = VectorFunctions.randomNum(new Vector2i(5, 20), new Vector2i(5, 20));

            Vector2i mapBoundsX = new Vector2i(mapDimensions.left, mapDimensions.width);
            Vector2i mapBoundsY = new Vector2i(mapDimensions.top, mapDimensions.height);
            Vector2f roomCoords = new Vector2f(VectorFunctions.randomNum(mapBoundsX, mapBoundsY));

            Room currentRoom = new Room(roomDimensionsInTiles, roomCoords, Theme.getRandomTheme());

            if (checkForIntersections(currentRoom) == true)
                currentRoom = null;

            else {
                currentNumOfDungeons++;
                roomList.add(currentRoom);
            }
        }

    }

    public Map(IntRect mapDimensions, int numOfDunegeons) {

        new Map(mapDimensions);
        this.numOfDungeons = numOfDunegeons;
    }

    private boolean checkForIntersections(Room currentRoom) {

        boolean intersects = false;

        for (Room room : roomList) {
            if(room != null && currentRoom != null)
            if (room.getCornerCoords().intersection(currentRoom.getCornerCoords()) != null)
                intersects = true;
        }

        return intersects;
    }

    public void addPlayer(Player playerToAdd) {

        playerList.add(playerToAdd);
    }

    public void update() {

        for (Player currentPlayer : playerList) {
            currentPlayer.update();
        }

        for (Entity entity : entityList) {
            entity.update();
        }


    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for (Room current : roomList) {
            current.draw(renderTarget, renderStates);
        }

        for (Entity entity : entityList) {
            entity.draw(renderTarget, renderStates);
        }

        for (Player currentPlayer : playerList) {
            currentPlayer.draw(renderTarget, renderStates);
        }
    }
}
