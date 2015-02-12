package com.classes;

import com.classes.util.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;


/**
 * Created by Chris on 2/5/2015.
 */
public class Player extends AnimatedEntity {

    View gameView;


    public Player(Resource loader, String textureName, Vector2f gamePosition, View gameView, int framesPerAnimation) {

        super(loader, textureName, gamePosition, framesPerAnimation);
        this.gameView = gameView;
        super.maxVelocity = new Vector2f(10, 10);


    }

    @Override
    public void setCollisionBox(){

        int xTextureWidth = sprite.getTextureRect().left + sprite.getTextureRect().width;
        int yTextureWidth = sprite.getTextureRect().top + sprite.getTextureRect().height;
        Vector2f textureSize = new Vector2f(xTextureWidth, yTextureWidth);

        collisionBox.setSize(Vector2f.div(textureSize, 1.25f));
        collisionBox.setPosition(Vector2f.add(gamePosition, Vector2f.div(new Vector2f(sprite.getTexture().getSize()), 9)));
        collisionBox.setFillColor(new Color(Color.BLUE, 144));
    }


    @Override
    public void update() {

        super.update();
        gameView.setCenter(gamePosition);
    }

}
