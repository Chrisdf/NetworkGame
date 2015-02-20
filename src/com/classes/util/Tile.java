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

    private float tileFriction;

    private float tileDamage;

    public Tile(Theme theme, Vector2i tileDimensions, Vector2f gamePosition, Vector2i positionInRoom, Tile[][] roomTiles, Vector2i tileScale) {

        this.tileDimensions = tileDimensions;
        this.theme = theme;
        this.positionInRoom = positionInRoom;
        this.roomTiles = roomTiles;

        //Load the texture from com.classes.resources based on theme type
        spriteTexture = Game.getLoader().getTexture(getTileTextureName());

        //Mark the tile collision box as the pixel area of the tile
        tileArea = new IntRect(new Vector2i(gamePosition), tileDimensions);

        tileSprite = new Sprite(spriteTexture);
        tileSprite.setPosition(gamePosition);

        //Take inverse of tile scale to get desired tile size, preferably 100x100 after scaling
        tileSprite.setScale(Vector2f.componentwiseDiv(new Vector2f(1, 1), new Vector2f(tileScale)));

        tileFriction = 0.4f;

    }

    private String getTileTextureName() {

        switch (theme) {

            case GRASS:
                return "Grass";
            case STONELIGHT:
                return "StoneLight";
            case STONEDARK:
                return "StoneDark";
        }

        return "StoneDark";
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

}
