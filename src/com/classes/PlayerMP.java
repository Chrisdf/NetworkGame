package com.classes;

import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.net.InetAddress;

/**
 * Created by chris on 2/18/15.
 */
public class PlayerMP extends Player{

    private InetAddress ipAddress;

    private int port;

    public PlayerMP(String playerName, String textureName, Vector2f gamePosition, int framesPerAnimation, InetAddress ipAddress, int port) {

        super(playerName, textureName, gamePosition, framesPerAnimation);
        this.ipAddress = ipAddress;
        this.port = port;

    }

    public PlayerMP(View gameView, String playerName, String textureName, Vector2f gamePosition, int framesPerAnimation, InetAddress ipAddress, int port) {

        super(playerName, textureName, gamePosition, gameView, framesPerAnimation);
        this.ipAddress = ipAddress;
        this.port = port;

    }


    @Override
    public void update() {

        super.update();
    }

    public InetAddress getIPAddress() {

        return ipAddress;
    }

    public int getPort() {

        return port;
    }

}
