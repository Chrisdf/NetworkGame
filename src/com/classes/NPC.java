package com.classes;

import com.classes.util.AnimatedEntity;
import com.classes.util.Entity;
import com.classes.util.Resource;
import org.jsfml.system.Vector2f;

/**
 * Created by chris on 2/10/15.
 */
public class NPC extends AnimatedEntity {

    public NPC(String textureName, Vector2f gamePosition) {

        super(textureName, gamePosition, 5);
    }

    @Override
    public void update() {

        super.update();
        //randomlyMove();
    }

    public void randomlyMove() {

        int randomNum = (int) (Math.random() * 10);

        switch (randomNum) {

            case 1:
                super.setAcceleration(new Vector2f(-0.5f, 0f));
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
