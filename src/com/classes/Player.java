package com.classes;

import com.classes.util.Entity;
import com.classes.util.Resource;
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

    }

    @Override
    public void update() {

        gameView.setCenter(getGamePosition());
    }

}
