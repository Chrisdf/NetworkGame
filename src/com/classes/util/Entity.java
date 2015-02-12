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

    public Vector2f velocity;

    public Vector2f acceleration;

    public double angleFromNorth;

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
        angleFromNorth = Math.atan2(velocity.y, velocity.x);

        spriteTexture = loader.getTexture(textureName);
        sprite = new Sprite(spriteTexture);
        sprite.setPosition(this.gamePosition);
        collisionBox = new RectangleShape();
        setCollisionBox();
    }

    private Vector2f getBottomCenterPosition(Vector2f imageDimensions) {

        return new Vector2f(imageDimensions.x / 2, imageDimensions.y);
    }

    public void setAcceleration(Vector2f change) {

        acceleration = change;
    }

    public void applyFriction() {

        float friction = 0.4f;

        //If entity is not accelerating then apply friction
        //TODO: Apply friction based on floor material
        if(FloatFunctions.isEqual(acceleration.x, 0))
            velocity = new Vector2f(velocity.x * friction, velocity.y);

        if(FloatFunctions.isEqual(acceleration.y, 0))
            velocity = new Vector2f(velocity.x, velocity.y * friction);

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
            velocity = new Vector2f(velocity.x, velocity.y + acceleration.y);

        gamePosition = Vector2f.add(gamePosition, velocity);
        sprite.setPosition(gamePosition);

        angleFromNorth = Math.atan2(velocity.y, velocity.x) + (Math.PI*3)/2;

        setCollisionBox();

        applyFriction();
    }


    public void stopMovement(){

        velocity = new Vector2f(0,0);
        acceleration = new Vector2f(0,0);
    }

    public void stopMovementHorizontally(Direction dir) {

        acceleration = new Vector2f(0, acceleration.y);
    }

    public void stopMovementVertically(Direction dir) {

        acceleration = new Vector2f(acceleration.x, 0);
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
        collisionBox.draw(renderTarget, renderStates);
    }

    public String toString() {
        return "Entity : " +
        //        "Game position - " + gamePosition.x + "," + gamePosition.y +
        //        "/ Acceleration:" + acceleration.x +"," + acceleration.y +
                "/ Velocity:" + velocity.x + "," + velocity.y +
                "/ Angle:" + Math.toDegrees(angleFromNorth);
    }
}
