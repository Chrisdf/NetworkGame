package com.classes;

import com.classes.util.Direction;
import com.classes.util.Entity;
import com.classes.util.Resource;
import com.classes.util.VectorFunctions;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;


/**
 * Created by Chris on 2/5/2015.
 */
public class Player extends Entity {

    View gameView;


    public Player(Resource loader, String textureName, Vector2f gamePosition, View gameView) {

        super(loader, textureName, gamePosition);
        this.gameView = gameView;
        super.maxVelocity = new Vector2f(10, 10);


    }

    @Override
    public void setCollisionBox(){

        collisionBox.setSize(Vector2f.div(new Vector2f(spriteTexture.getSize()), 1.25f));
        collisionBox.setPosition(Vector2f.add(gamePosition, Vector2f.div(new Vector2f(sprite.getTexture().getSize()), 9)));
        collisionBox.setFillColor(new Color(Color.BLUE, 144));
    }


    @Override
    public void update() {

        super.update();
        gameView.setCenter(gamePosition);
    }

}
