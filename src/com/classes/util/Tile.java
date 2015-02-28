package com.classes.util;

import com.classes.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Tile implements Drawable {

    private Theme theme;

    private Vector2i tileDimensions;

    private IntRect tileArea;

    private Texture spriteTexture;

    private Sprite tileSprite;

    private Vector2i positionInRoom;

    private Tile[][] roomTiles;

    private Vector2i gamePosition;

    private float tileFriction;

    private float tileDamage;

    public Tile(Theme theme, Vector2i tileDimensions, Vector2i gamePosition, Vector2i positionInRoom, Tile[][] roomTiles) {

        this.tileDimensions = tileDimensions;
        this.theme = theme;
        this.positionInRoom = positionInRoom;
        this.roomTiles = roomTiles;
        this.gamePosition = gamePosition;

        //Load the texture from com.classes.resources based on theme type
        spriteTexture = Game.getLoader().getTexture(getTileTextureName());


        //Mark the tile collision box as the pixel area of the tile
        tileArea = new IntRect(gamePosition, tileDimensions);

        tileSprite = new Sprite(spriteTexture);
        tileSprite.setPosition(new Vector2f(gamePosition));

        Vector2f tileScale = Vector2f.componentwiseDiv(new Vector2f(tileDimensions), new Vector2f(spriteTexture.getSize()));
        tileSprite.setScale(tileScale);

        tileFriction = 0.4f;

    }

    private String getTileTextureName() {

        switch (theme) {

            case GLASS:
                return "Glass";
            case STONELIGHT:
                return "Stone_Light";
            case STONEDARK:
                return "Stone_Dark";
            case STONEDARKEST:
                return "Stone_Darkest";
        }

        return "Stone_Dark";
    }

    public Vector2i getTileDimensions() {
        return tileDimensions;
    }

    public IntRect getTileArea() {
        return tileArea;
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        tileSprite.draw(renderTarget, renderStates);
    }

    public String toString(){

        return gamePosition.x + "," + gamePosition.y;
    }

}
