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

    public enum Theme {STONE, GRASS}

    public Room(Vector2i roomDimensions, Vector2f topLeftCoords, String tileTexture) {

        tileDimensions = new Vector2i(129,129);
        tileScale = new Vector2i(2, 2);
        tileDimensions = Vector2i.componentwiseDiv(tileDimensions, tileScale);
        this.roomDimensions = roomDimensions;
        this.topLeftCoords = topLeftCoords;
        cornerCoords = new FloatRect(topLeftCoords, new Vector2f(this.roomDimensions));
        centerPoint = getRoomCenter();
        roomTiles = new Tile[roomDimensions.x][roomDimensions.y];

        for(int i = 0; i<roomTiles.length; i++)
            for(int d = 0; d<roomTiles[i].length; d++) {

                Vector2f startingPosition = topLeftCoords;

                float spritePositionX = startingPosition.x + (i * tileDimensions.x);
                float spritePositionY = startingPosition.y + (d * tileDimensions.y);
                Vector2f spritePosition = new Vector2f(spritePositionX, spritePositionY);
                spritePosition = VectorFunctions.round(spritePosition);
                Vector2i positionInRoom = new Vector2i(i, d);

                roomTiles[i][d] = new Tile(getRandomTheme(), tileDimensions, spritePosition, positionInRoom, roomTiles);

            }
    }

    private Vector2f getRoomCenter() {

        float centerX = topLeftCoords.x + roomDimensions.x / 2;
        float centerY = topLeftCoords.y + roomDimensions.y / 2;

        return new Vector2f(centerX, centerY);
    }

    public Theme getRandomTheme() {

        int themePick = (int)(Math.random() * 2) + 1;

        switch (themePick){

            case 1:
                return Theme.STONE;
            case 2:
                return Theme.GRASS;
            default:
                return Theme.STONE;
        }
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
