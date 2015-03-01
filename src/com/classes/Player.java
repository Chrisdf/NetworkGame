package com.classes;

import com.classes.net.packets.Packet02Move;
import com.classes.util.AnimatedEntity;
import com.classes.util.Direction;
import com.classes.util.FloatFunctions;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Chris on 2/5/2015.
 */
public class Player extends AnimatedEntity {

    protected String playerName;

    protected Text displayedName;

    protected View gameView;

    protected float gameViewZoom;

    protected Set<String> inputList;


    public Player(String playerName, String textureName, Vector2f gamePosition, View gameView, int framesPerAnimation) {

        this(playerName, textureName, gamePosition, framesPerAnimation);

        this.gameView = gameView;
        gameViewZoom = 1f;
    }

    public Player(String playerName, String textureName, Vector2f gamePosition, int framesPerAnimation) {

        super(textureName, gamePosition, framesPerAnimation);
        this.playerName = playerName;
        super.maxVelocity = new Vector2f(10, 10);
        inputList = new HashSet<String>();

        Font heav = Game.getLoader().getFont("heav");
        this.displayedName = new Text(playerName, heav, 19);
        displayedName.setOrigin(displayedName.getLocalBounds().width / 2, displayedName.getLocalBounds().height / 2);
        displayedName.setPosition(Vector2f.add(this.sprite.getPosition(), new Vector2f(0, 0)));
        displayedName.setColor(new Color(Color.CYAN, 150));
    }

    @Override
    public void setCollisionBox() {

        int xTextureWidth = sprite.getTextureRect().width;
        int yTextureWidth = sprite.getTextureRect().height;
        Vector2f textureSize = new Vector2f(xTextureWidth, yTextureWidth);

        collisionBox.setSize(Vector2f.div(textureSize, 1.5f));
        collisionBox.setPosition(Vector2f.add(gamePosition, Vector2f.div(new Vector2f(sprite.getTexture().getSize()), 18)));
        collisionBox.setFillColor(new Color(Color.BLUE, 144));
    }


    @Override
    public void update() {

        respondToInput();

        super.update();

        inputList.clear();

        displayedName.setPosition(Vector2f.add(this.sprite.getPosition(), new Vector2f(0, 0)));
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        super.draw(renderTarget, renderStates);
        displayedName.draw(renderTarget, renderStates);
    }

    public void respondToInput() {


        if (inputList.contains("D")) {

            isAnimating = true;
            setAcceleration(new Vector2f(1f, getAcceleration().y));
        }

        if (inputList.contains("A")) {

            isAnimating = true;
            setAcceleration(new Vector2f(-1f, getAcceleration().y));
        }

        if (inputList.contains("S")) {

            isAnimating = true;
            setAcceleration(new Vector2f(getAcceleration().x, 1f));
        }

        if (inputList.contains("W")) {

            isAnimating = true;
            setAcceleration(new Vector2f(getAcceleration().x, -1f));
        }

        if (inputList.contains("LSHIFT")) {

            setFramesPerAnimation(2);
            maxVelocity = Vector2f.mul(maxVelocity, 2f);
        }

        if (inputList.contains("D_RELEASED")) {

            stopMovementHorizontally(Direction.EAST);
        }

        if (inputList.contains("A_RELEASED")) {

            stopMovementHorizontally(Direction.WEST);
        }

        if (inputList.contains("S_RELEASED")) {

            stopMovementVertically(Direction.SOUTH);
        }

        if (inputList.contains("W_RELEASED")) {

            stopMovementVertically(Direction.NORTH);
        }

        if (inputList.contains("LSHIFT_RELEASED")) {

            setFramesPerAnimation(5);
            maxVelocity = new Vector2f(10, 10);
        }

        if (inputList.contains("LCONTROL_RELEASED"))
            toggleViewZoom();

    }

    public void addInput(String input) {

        inputList.add(input);
    }

    public void setGameView(View gameView) {

        this.gameView = gameView;
    }

    private void toggleViewZoom() {

        gameViewZoom = gameViewZoom + 0.5f;

        if (gameView != null)
            gameView.zoom(gameViewZoom);
    }

    public String getUsername() {
        return playerName;
    }

    public String toString() {

        return playerName;
    }
}

