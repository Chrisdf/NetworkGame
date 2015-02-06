package com.classes.util;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * A base entity class for the game
 *
 * Created by Chris on 2/5/2015.
 */
public class Entity implements Drawable {

    Vector2f dimensions;

    Vector2f velocity;

    Vector2f acceleration;

    Sprite sprite;

    Texture spriteTexture;

    Vector2f gamePosition;

    RectangleShape collisionBox;

    public Entity(Resource loader, String textureName, Vector2f gamePosition) {

        this.gamePosition = gamePosition;
        getBottomCenterPosition(gamePosition);

        velocity = new Vector2f(0, 0);
        acceleration = new Vector2f(0, 0);

        spriteTexture = loader.getTexture(textureName);
        sprite = new Sprite(spriteTexture);
    }

    private Vector2f getBottomCenterPosition(Vector2f imageDimensions){

        float xCoords = imageDimensions.x / 2;
        float yCoords = imageDimensions.y;

        return new Vector2f(xCoords, yCoords);
    }

    public void addAcceleration(Vector2f change) {

        Vector2f.add(acceleration, change);
    }

    private void setCollisionBox() {

        collisionBox.setSize(dimensions);
    }

    public Vector2f getGamePosition() {
        return gamePosition;
    }

    public void setGamePosition(Vector2f gamePosition) {
        this.gamePosition = gamePosition;
    }

    public Vector2f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2f acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector2f dimensions) {
        this.dimensions = dimensions;
    }


    public void update() {

        Vector2f.add(acceleration, velocity);
        Vector2f.add(gamePosition, velocity);
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        sprite.draw(renderTarget, renderStates);
    }
}
