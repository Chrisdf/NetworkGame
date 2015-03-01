package com.classes;

import com.classes.net.Client;
import com.classes.net.Server;
import com.classes.net.packets.Packet03MapData;
import com.classes.ui.FPS;
import com.classes.util.Resource;
import com.classes.util.UIElement;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import javax.swing.*;
import java.util.ArrayList;

public class Game {

    public static Game game;

    private RenderWindow renderWindow;

    private final ConstView defaultView;

    private Client client;

    private Server server;

    private final View gameView;

    private FPS fps;

    private static Resource loader;

    private ArrayList<UIElement> uiElements;

    private String username;

    private String serverIP;

    public Map currentMap;

    public Player mainPlayer;

    private Game() {

        username = JOptionPane.showInputDialog("Enter a username: | If want to start a server, enter 'server'");

        if (!username.equals("server"))
            serverIP = JOptionPane.showInputDialog("Enter an IP to connect to: | If you are the server, enter 'localhost'");
        else
            serverIP = "localhost";

        loader = new Resource();

        client = new Client(this, serverIP);
        client.start();

        RenderWindow window = new RenderWindow();


        if (username.equals("server")) {

            server = new Server(this);
            server.start();

            currentMap = new Map(new Vector2i(10, 10), 1, 0);
        }


        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(1280, 720), "NetworkGame");
        renderWindow.setFramerateLimit(60);

        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());

        fps = new FPS();

        uiElements = new ArrayList<UIElement>();
        uiElements.add(fps);

        client.sendData(("00" + username + "," + 0f + "," + 0f).getBytes());

        game = this;

        runGame();


    }

    public static void main(String[] args) {

        new Game();
    }

    private void runGame() {

        Clock gameClock = new Clock();

        final float seconds_per_tick = 1 / 20f;
        float lag = 0;
        int framesDrawn = 0;
        float frameTime = 0;


        while (renderWindow.isOpen()) {

            if (mainPlayer != null && currentMap.hasLoaded()) {

                float elapsedTime = gameClock.restart().asSeconds();
                lag += elapsedTime;
                frameTime += elapsedTime;


                handleInput();

                while (lag >= seconds_per_tick) {

                    update();
                    lag -= seconds_per_tick;
                }


                draw(lag / seconds_per_tick);

                if (frameTime >= 1.0f) {
                    fps.displayFPS((int) (framesDrawn / frameTime));
                    framesDrawn = 0;
                    frameTime = 0;
                }

                framesDrawn++;
            }
        }
    }


    private void handleInput() {

        for (Event event : renderWindow.pollEvents()) {
            switch (event.type) {

                case CLOSED:

                    //Send a logout packet
                    client.sendData(("01" + username).getBytes());
                    client.closeSocket();

                    if (server != null)
                        server.closeSocket();

                    renderWindow.close();
                    break;

                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {

                        case D:
                            mainPlayer.addInput("D");
                            break;
                        case A:
                            mainPlayer.addInput("A");
                            break;
                        case S:
                            mainPlayer.addInput("S");
                            break;
                        case W:
                            mainPlayer.addInput("W");
                            break;
                        case LSHIFT:
                            mainPlayer.addInput("LSHIFT");
                    }
                    break;

                case KEY_RELEASED:
                    switch (event.asKeyEvent().key) {

                        case D:
                            mainPlayer.addInput("D_RELEASED");
                            break;
                        case A:
                            mainPlayer.addInput("A_RELEASED");
                            break;
                        case S:
                            mainPlayer.addInput("S_RELEASED");
                            break;
                        case W:
                            mainPlayer.addInput("W_RELEASED");
                            break;
                        case LSHIFT:
                            mainPlayer.addInput("LSHIFT_RELEASED");
                            break;
                        case LCONTROL:
                            mainPlayer.addInput("LCONTROL_RELEASED");
                    }
                    break;

            }

        }

    }

    private void update() {

        currentMap.update();
    }

    private void draw(float deltaTime) {

        renderWindow.clear();

        if (gameView != null && mainPlayer != null) {

            gameView.setCenter(mainPlayer.getGamePosition());
            renderWindow.setView(gameView);
        }

        renderWindow.draw(currentMap);

        renderWindow.setView(defaultView);

        for (UIElement ui : uiElements)
            renderWindow.draw(ui);

        renderWindow.display();
    }

    public void setMainPlayer(PlayerMP player) {

        mainPlayer = player;
        mainPlayer.setGameView(gameView);
    }

    public Map getCurrentMap() {

        return currentMap;
    }

    public void setMap(Packet03MapData mapDataPacket) {

        currentMap = new Map();
        currentMap.setMap(mapDataPacket.getTileList(), mapDataPacket.getTileDimensions());
    }

    public View getGameView() {

        return gameView;
    }

    public Server getServer() {

        return server;
    }

    public Client getClient() {

        return client;
    }

    public static Resource getLoader() {
        return loader;
    }


    public String getUsername() {
        return username;
    }

}
