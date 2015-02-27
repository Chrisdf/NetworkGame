package com.classes.util;

import com.classes.Main;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/12/2015.
 */
public class Tile implements Drawable {

    private Vector2i tileDimensions;

    private IntRect tileArea;

    private Texture spriteTexture;

    private Sprite tileSprite;

    private float tileFriction;

    private float tileDamage;

    public Tile(String textureName, Vector2i tileDimensions, Vector2f gamePosition) {

        this.tileDimensions = tileDimensions;
        spriteTexture = Main.getLoader().getTexture(textureName);
        tileArea = new IntRect(new Vector2i(gamePosition), tileDimensions);
        tileSprite = new Sprite(spriteTexture);
        tileSprite.setPosition(gamePosition);
        tileFriction = 0.4f;

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
