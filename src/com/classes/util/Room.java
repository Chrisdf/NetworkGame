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

    private Vector2i roomdDimensions;

    private Vector2f centerPoint;

    public Room(Vector2i roomDimensions, Vector2f topLeftCoords, String tileTexture) {

        tileDimensions = new Vector2i(129,129);
        tileScale = new Vector2i(2, 2);
        tileDimensions = Vector2i.componentwiseDiv(tileDimensions, tileScale);
        this.roomdDimensions = roomDimensions;
        this.topLeftCoords = topLeftCoords;
        cornerCoords = new FloatRect(topLeftCoords, new Vector2f(roomdDimensions));
        centerPoint = getRoomCenter();
        roomTiles = new Tile[roomDimensions.x][roomDimensions.y];

        for(int i = 0; i<roomTiles.length; i++)
            for(int d = 0; d<roomTiles[i].length; d++) {

                Vector2f startingPosition = topLeftCoords;

                float spritePositionX = startingPosition.x + (i * tileDimensions.x);
                float spritePositionY = startingPosition.y + (d * tileDimensions.y);
                Vector2f spritePosition = new Vector2f(spritePositionX, spritePositionY);
                spritePosition = VectorFunctions.round(spritePosition);

                roomTiles[i][d] = new Tile(tileTexture, tileDimensions, spritePosition);

                if(i == 0 || i == roomTiles.length -1 || d == 0 || d == roomTiles[i].length-1)
                    roomTiles[i][d] = new Tile("grass", tileDimensions, spritePosition);
            }
    }

    private Vector2f getRoomCenter() {

        float centerX = topLeftCoords.x + roomdDimensions.x / 2;
        float centerY = topLeftCoords.y + roomdDimensions.y / 2;

        return new Vector2f(centerX, centerY);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for(Tile[] horizontal: roomTiles)
            for(Tile current: horizontal)
                current.draw(renderTarget, renderStates);
    }

    public FloatRect getCornerCoords(){

        return cornerCoords;
    }
}
