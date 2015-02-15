package com.classes.util;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Room implements Drawable {

    private Tile[][] roomTiles;

    private Vector2i tileDimensions;

    private Vector2i tileScale;

    private FloatRect cornerCoords;

    private Vector2f topLeftCoords;

    private Vector2i roomDimensions;

    private Vector2f centerPoint;

    private Theme theme;

    public Room(Vector2i roomDimensionsInTiles, Vector2f topLeftCoords, Theme theme) {

        /**
         * tileDimensions must be the same size as the tile texture size
         *
         * tileScale is the ratio to divide the texture size to make it uniform with all other tiles,
         * preferred scale is whatever it takes to get the tile size to 100x100
         */
        tileDimensions = new Vector2i(400, 400);
        tileScale = new Vector2i(8, 8);
        tileDimensions = Vector2i.componentwiseDiv(tileDimensions, tileScale);

        /**
         * Get pixel room dimensions by multiplying the number of tiles in the room
         * by the tile size of each tile
         */
        roomDimensions = Vector2i.componentwiseMul(roomDimensionsInTiles, tileDimensions);

        /**
         * Set position of the room outline rectangle as the top left point and the
         * bottom right point
         */
        this.topLeftCoords = topLeftCoords;
        cornerCoords = new FloatRect(topLeftCoords, new Vector2f(roomDimensions));

        centerPoint = getRoomCenter();

        roomTiles = new Tile[roomDimensionsInTiles.x][roomDimensionsInTiles.y];

        for (int i = 0; i < roomTiles.length; i++)
            for (int d = 0; d < roomTiles[i].length; d++) {

                //Position of the top left tile in the room
                Vector2f startingPosition = topLeftCoords;

                /**
                 * Set each following tile's position relative to the ones
                 * before it in the array
                 */
                float spritePositionX = startingPosition.x + (i * tileDimensions.x);
                float spritePositionY = startingPosition.y + (d * tileDimensions.y);
                Vector2f spritePosition = new Vector2f(spritePositionX, spritePositionY);
                spritePosition = VectorFunctions.round(spritePosition);

                //Mark the position of the tile in the tile matrix
                Vector2i positionInRoom = new Vector2i(i, d);

                roomTiles[i][d] = new Tile(theme, tileDimensions, spritePosition, positionInRoom, roomTiles, tileScale);

            }
    }

    private Vector2f getRoomCenter() {

        float centerX = topLeftCoords.x + roomDimensions.x / 2;
        float centerY = topLeftCoords.y + roomDimensions.y / 2;

        return new Vector2f(centerX, centerY);
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for (Tile[] horizontal : roomTiles)
            for (Tile current : horizontal)
                current.draw(renderTarget, renderStates);
    }

    public FloatRect getCornerCoords() {

        return cornerCoords;
    }

    public Theme getTheme() {

        return theme;
    }


}
