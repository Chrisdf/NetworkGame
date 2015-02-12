package com.classes.util;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/11/2015.
 */
public class AnimatedEntity extends Entity {

    private int currentFrame;

    private int verticalFramePosition;

    private int uniqueAnimationFrames;

    private int framesPerAnimation;

    public Vector2i spriteSize;

    private Direction direction;

    public AnimatedEntity(Resource loader, String textureName, Vector2f gamePosition, int framesPerAnimation){

        super(loader, textureName, gamePosition);
        uniqueAnimationFrames = 4;
        spriteSize = Vector2i.div(spriteTexture.getSize(), uniqueAnimationFrames);
        this.framesPerAnimation = framesPerAnimation;
        direction = Direction.SOUTH;
        verticalFramePosition = getVerticalFramePosition();

        IntRect textureRect = new IntRect(currentFrame/framesPerAnimation, verticalFramePosition, spriteSize.x, spriteSize.y);
        sprite.setTextureRect(textureRect);
    }

    private int getVerticalFramePosition() {

        switch (direction) {

            case SOUTH:
                return 0;
            case WEST:
                return 1;
            case EAST:
                return 2;
            case NORTH:
                return 4;
        }

        System.out.print("Something went wrong finding direction");
        return -1;
    }

    public void update(){

        super.update();
        currentFrame++;

        if(currentFrame >= framesPerAnimation * uniqueAnimationFrames)
            currentFrame = 0;

        int xStartCoords = spriteSize.x *(currentFrame/framesPerAnimation);
        int yStartCoords = spriteSize.y * verticalFramePosition;
        IntRect textureRect = new IntRect(xStartCoords, yStartCoords, spriteSize.x, spriteSize.y);
        sprite.setTextureRect(textureRect);
    }
}
