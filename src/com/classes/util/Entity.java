package com.classes.util;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.Vector;

/**
 * A base entity class for the game
 * <p/>
 * Created by Chris on 2/5/2015.
 */
public class Entity implements Drawable {

    public Vector2f dimensions;

    public Vector2f velocity;

    public Vector2f acceleration;

    public Sprite sprite;

    public Texture spriteTexture;

    public Vector2f gamePosition;

    public Vector2f maxVelocity;

    public RectangleShape collisionBox;

    public Entity(Resource loader, String textureName, Vector2f gamePosition) {

        this.gamePosition = getBottomCenterPosition(gamePosition);

        velocity = new Vector2f(0, 0);
        acceleration = new Vector2f(0, 0);
        maxVelocity = new Vector2f(5, 5);

        spriteTexture = loader.getTexture(textureName);
        sprite = new Sprite(spriteTexture);
        sprite.setPosition(this.gamePosition);
        collisionBox = new RectangleShape();
        setCollisionBox();
    }

    private Vector2f getBottomCenterPosition(Vector2f imageDimensions) {

        float xCoords = imageDimensions.x / 2;
        float yCoords = imageDimensions.y;

        return new Vector2f(xCoords, yCoords);
    }

    public void setAcceleration(Direction direction, Vector2f change) {

        int sign = getSign(direction);

        acceleration = Vector2f.mul(change, sign);

    }

    public void applyFriction() {

        //If entity is accelerating on either axis
        if(FloatFunctions.isEqual(acceleration.x, 0) || FloatFunctions.isEqual(acceleration.y, 0)) {

            //Take the absolute value of acceleration
            Vector2f absVelocity= VectorFunctions.abs(velocity);

            //System.out.println(absAcceleration);

            //If the absolute value of x acceleration is not equal to zero, decrease by one
            if(!FloatFunctions.isEqual(absVelocity.x, 0, 0.01))
                absVelocity = Vector2f.sub(absVelocity, new Vector2f(.1f, 0));

            //System.out.println(absAcceleration);

            //If the absolute value of y acceleration is not equal to zero, decrease by one
            if(!FloatFunctions.isEqual(absVelocity.y,0, 0.01))
                absVelocity = Vector2f.sub(absVelocity, new Vector2f(0, .1f));


            //System.out.println(absAcceleration);

            //Set velocity to the absolute velocity times the original velocity directions
            velocity = Vector2f.componentwiseMul(absVelocity, VectorFunctions.getSign(acceleration));
        }

        if(FloatFunctions.isEqual(velocity.x, 0f, 0.01))
            velocity = new Vector2f(0, velocity.y);

        if(FloatFunctions.isEqual(velocity.y, 0f, 0.01))
            velocity = new Vector2f(velocity.x, 0);
    }

    public void setCollisionBox(){

        collisionBox.setSize(new Vector2f(sprite.getTexture().getSize()));
        collisionBox.setPosition(gamePosition);
        collisionBox.setFillColor(new Color(Color.BLUE, 144));
    }

    public void update() {

        //Check if new velocity is over max velocity and if not then add it
        if(Math.abs(velocity.x + acceleration.x) <= maxVelocity.x)
            velocity = new Vector2f(velocity.x + acceleration.x, velocity.y);

        if(Math.abs(velocity.y + acceleration.y) <= maxVelocity.y)
            velocity = new Vector2f(velocity.x , velocity.y + acceleration.y);

        gamePosition = Vector2f.add(gamePosition, velocity);
        sprite.setPosition(gamePosition);

        setCollisionBox();

        applyFriction();
    }

    public int getSign(Direction direction) {

        int sign = 1;

        switch (direction) {
            case LEFT:
                sign = -1;
                break;
            case RIGHT:
                sign = +1;
                break;
            case UP:
                sign = -1;
                break;
            case DOWN:
                sign = +1;
                break;
        }
        return sign;
    }

    public void stopMovement(){

        velocity = new Vector2f(0,0);
        acceleration = new Vector2f(0,0);
    }

    public void stopMovementHorizontally(Direction dir) {

        //If the velocity's x value is the same sign as the direction then stop movement
        if(VectorFunctions.getSign(velocity).x == getSign(dir)) {
            acceleration = new Vector2f(0, velocity.y);
        }
    }

    public void stopMovementVertically(Direction dir) {

        //If the velocity's y value is the same sign as the direction then stop movement
        if(VectorFunctions.getSign(velocity).y == getSign(dir)) {
            acceleration = new Vector2f(velocity.x, 0);
        }
    }

    public void interpolate(Float deltaTime) {

        Vector2f endingPosition = Vector2f.add(gamePosition, velocity);

        /**
         * Linearly interpolates between point at the start of the update and point
         * for the next update
         */
        float gamePositionX = ((1 - deltaTime) * gamePosition.x) + (deltaTime * endingPosition.x);
        float gamePositionY = ((1 - deltaTime) * gamePosition.y) + (deltaTime * endingPosition.y);

        gamePosition = new Vector2f(gamePositionX, gamePositionY);
        sprite.setPosition(gamePosition);

    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        sprite.draw(renderTarget, renderStates);
        //collisionBox.draw(renderTarget, renderStates);
    }

    public String toString() {
        return "Player : " +
                "Game position - " + gamePosition.x + "," + gamePosition.y +
                "/ Acceleration:" + acceleration.x +"," + acceleration.y +
                "/ Velocity:" + velocity.x + "," + velocity.y;
    }
}
