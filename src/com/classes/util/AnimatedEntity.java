package com.classes.util;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by Chris on 2/11/2015.
 */
public class AnimatedEntity extends Entity {

    protected boolean isAnimating;

    protected int currentFrame;

    protected int verticalFramePosition;

    protected int uniqueAnimationFrames;

    protected int framesPerAnimation;

    protected Vector2i spriteSize;

    public AnimatedEntity(String textureName, Vector2f gamePosition, int framesPerAnimation) {

        super(textureName, gamePosition);
        uniqueAnimationFrames = 4;
        spriteSize = Vector2i.div(spriteTexture.getSize(), uniqueAnimationFrames);
        this.framesPerAnimation = framesPerAnimation;
        verticalFramePosition = getVerticalFramePosition();

        IntRect textureRect = new IntRect(currentFrame / framesPerAnimation, verticalFramePosition, spriteSize.x, spriteSize.y);
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
                return 3;
        }

        System.out.print("Something went wrong finding direction");
        return -1;
    }

    public void update() {

        super.update();

        verticalFramePosition = getVerticalFramePosition();

        if (isAnimating)
            currentFrame++;
        else
            currentFrame = 0;

        if (currentFrame >= framesPerAnimation * uniqueAnimationFrames)
            currentFrame = 0;

        int xStartCoords = spriteSize.x * (currentFrame / framesPerAnimation);
        int yStartCoords = spriteSize.y * verticalFramePosition;
        IntRect textureRect = new IntRect(xStartCoords, yStartCoords, spriteSize.x, spriteSize.y);
        sprite.setTextureRect(textureRect);

        //If the player is not moving, do not animate
        if (FloatFunctions.isEqual(velocity.x, 0, 0.5))
            if (FloatFunctions.isEqual(velocity.y, 0, 0.5))
                isAnimating = false;
    }

    protected void setFramesPerAnimation(int framesPerAnimation) {
        this.framesPerAnimation = framesPerAnimation;
    }
}
