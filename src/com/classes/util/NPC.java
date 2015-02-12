package com.classes.util;

import org.jsfml.system.Vector2f;

/**
 * Created by chris on 2/10/15.
 */
public class NPC extends Entity{

    public NPC(Resource loader, String textureName, Vector2f gamePosition) {

        super(loader, textureName, gamePosition);
    }

    @Override
    public void update() {

        super.update();
        //randomlyMove();
    }

    public void randomlyMove() {

        int randomNum = (int) (Math.random() * 10);

        switch(randomNum) {

            case 1:
                super.setAcceleration( new Vector2f(-0.5f, 0f));
                break;
            case 2:
                super.setAcceleration(new Vector2f(0.5f, 0f));
                break;
            case 3:
                super.stopMovement();
                break;
            default:
                break;
        }
    }
}
