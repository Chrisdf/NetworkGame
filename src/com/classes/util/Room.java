package com.classes.util;

import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Room {

    private Tile[][] roomTiles;

    private Vector2i tileDimensions;

    private FloatRect cornerCoords;

    private Vector2f topLeftCoords;

    private Vector2i roomdDimensions;

    private Vector2f centerPoint;

    public Room(Vector2i roomDimensions, Vector2f topLeftCoords, String tileTexture) {

        tileDimensions = new Vector2i(129,129);
        this.roomdDimensions = roomDimensions;
        this.topLeftCoords = topLeftCoords;
        cornerCoords = new FloatRect(topLeftCoords, new Vector2f(roomdDimensions));
        centerPoint = getRoomCenter();
        roomTiles = new Tile[roomDimensions.x][roomDimensions.y];

        for(int i = 0; i<roomTiles.length; i++)
            for(int d = 0; d<roomTiles[i].length; d++) {

                Vector2f startingPosition = topLeftCoords;

                float spritePositionX = startingPosition.x + (i * tileDimensions.x);
                float spritePositionY = startingPosition.y + (i * tileDimensions.y);
                Vector2f spritePosition = new Vector2f(spritePositionX, spritePositionY);

                roomTiles[i][d] = new Tile("House_Stone(10)", tileDimensions, spritePosition);
            }
    }

    private Vector2f getRoomCenter() {

        float centerX = topLeftCoords.x + roomdDimensions.x / 2;
        float centerY = topLeftCoords.y + roomdDimensions.y / 2;

        return new Vector2f(centerX, centerY);
    }
}
