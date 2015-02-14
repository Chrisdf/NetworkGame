package com.classes.util;

import org.jsfml.graphics.*;
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

        tileDimensions = new Vector2i(100, 100);
        tileScale = new Vector2i(2, 2);
        tileDimensions = Vector2i.componentwiseDiv(tileDimensions, tileScale);

        roomDimensions = Vector2i.componentwiseMul(roomDimensionsInTiles, tileDimensions);
        this.topLeftCoords = topLeftCoords;
        cornerCoords = new FloatRect(topLeftCoords, new Vector2f(roomDimensions));

        centerPoint = getRoomCenter();

        roomTiles = new Tile[roomDimensionsInTiles.x][roomDimensionsInTiles.y];

        for (int i = 0; i < roomTiles.length; i++)
            for (int d = 0; d < roomTiles[i].length; d++) {

                Vector2f startingPosition = topLeftCoords;

                float spritePositionX = startingPosition.x + (i * tileDimensions.x);
                float spritePositionY = startingPosition.y + (d * tileDimensions.y);
                Vector2f spritePosition = new Vector2f(spritePositionX, spritePositionY);
                spritePosition = VectorFunctions.round(spritePosition);
                Vector2i positionInRoom = new Vector2i(i, d);

                roomTiles[i][d] = new Tile(theme, tileDimensions, spritePosition, positionInRoom, roomTiles);

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

        RectangleShape outline = new RectangleShape();
        outline.setPosition(cornerCoords.left, cornerCoords.top);
        outline.setSize(new Vector2f(cornerCoords.width, cornerCoords.height));
        outline.setFillColor(Color.CYAN);
        outline.draw(renderTarget, renderStates);

        RectangleShape centerOutline = new RectangleShape();
        centerOutline.setPosition(getRoomCenter());
        centerOutline.setSize(new Vector2f(5, 5));
        centerOutline.setFillColor(Color.GREEN);
        centerOutline.draw(renderTarget, renderStates);
    }

    public FloatRect getCornerCoords() {

        return cornerCoords;
    }

    public Theme getTheme() {

        return theme;
    }


}
