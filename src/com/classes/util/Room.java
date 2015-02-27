package com.classes.util;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Room {

    private Tile[][] roomTiles;

    private IntRect cornerCoords;

    private Vector2i roomDimensions;

    private Vector2i centerTilePosition;

    private Theme theme;

    public Room(Vector2i topLeftCoords, Theme theme) {

        this.theme = theme;

        roomDimensions = VectorFunctions.randomNum(new Vector2i(5,20), new Vector2i(5,20));

        cornerCoords = new IntRect(topLeftCoords, roomDimensions);

        centerTilePosition = new Vector2i(cornerCoords.left + roomDimensions.x / 2, cornerCoords.top + roomDimensions.y / 2);

    }

    public Vector2i getCenterTilePosition() {


        return centerTilePosition;
    }


    public IntRect getCornerCoords() {

        return cornerCoords;
    }

    public Vector2i getRoomDimensions() {

        return roomDimensions;
    }


    public Theme getTheme() {

        return theme;
    }


}
