package com.classes.util;

import com.classes.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Tile implements Drawable {

    private int tileTypeIndex;

    private Vector2i tileDimensions;

    private IntRect tileArea;

    private Texture spriteTexture;

    private Sprite tileSprite;

    private Vector2i positionInRoom;

    private Tile[][] roomTiles;

    private Vector2i gamePosition;

    private float tileFriction;

    private float tileDamage;

    public Tile(int tileTypeIndex, Vector2i tileDimensions, Vector2i gamePosition, Vector2i positionInRoom, Tile[][] roomTiles) {

        this.tileDimensions = tileDimensions;
        this.tileTypeIndex = tileTypeIndex;
        this.positionInRoom = positionInRoom;
        this.roomTiles = roomTiles;
        this.gamePosition = gamePosition;

        //Load the texture from com.classes.resources based on theme type
        spriteTexture = Game.getLoader().getTexture(TileType.getTileTextureName(tileTypeIndex));


        //Mark the tile collision box as the pixel area of the tile
        tileArea = new IntRect(gamePosition, tileDimensions);

        tileSprite = new Sprite(spriteTexture);
        tileSprite.setPosition(new Vector2f(gamePosition));

        Vector2f tileScale = Vector2f.componentwiseDiv(new Vector2f(tileDimensions), new Vector2f(spriteTexture.getSize()));
        tileSprite.setScale(tileScale);

        tileFriction = 0.4f;

    }

    public Vector2i getTileDimensions() {
        return tileDimensions;
    }

    public IntRect getTileArea() {
        return tileArea;
    }

    public TileType getTileTypeIndex() {

        return TileType.findTileTypeByIndex(tileTypeIndex);
    }

    public Vector2i getGamePosition() {
        return gamePosition;
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        tileSprite.draw(renderTarget, renderStates);
    }

    public String toString(){

        return gamePosition.x + "," + gamePosition.y;
    }

}
