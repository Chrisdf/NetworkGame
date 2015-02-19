package com.classes;

import com.classes.net.Client;
import com.classes.net.Server;
import com.classes.ui.FPS;
import com.classes.util.Resource;
import com.classes.util.UIElement;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.*;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {

    private RenderWindow renderWindow;

    private final ConstView defaultView;

    private Client client;

    private Server server;

    private final View gameView;

    private FPS fps;

    private static Resource loader;

    private ArrayList<UIElement> uiElements;

    private String username;

    public Map currentMap;

    public Player mainPlayer;

    private Game() {

        username = JOptionPane.showInputDialog("Enter a username: | If want to start a server, enter 'server'");

        client = new Client(this, "localhost");
        client.start();

        RenderWindow window = new RenderWindow();


        if(username.equals("server")) {

            server = new Server(this);
            server.start();
        }


        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(1280, 720), "NetworkGame");
        renderWindow.setFramerateLimit(60);


        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());

        loader = new Resource();

        currentMap = new Map(new IntRect(-1000, -1000, 1000, 1000));

        fps = new FPS();

        uiElements = new ArrayList<UIElement>();
        uiElements.add(fps);

        client.sendData(("00" + username).getBytes());

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


    private void handleInput() {

        for (Event event : renderWindow.pollEvents()) {
            switch (event.type) {

                case CLOSED:
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

        if(gameView != null && mainPlayer != null) {

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
    }

    public Map getCurrentMap() {

        return currentMap;
    }

    public View getGameView() {

        return gameView;
    }

    public static Resource getLoader() {
        return loader;
    }


    public String getUsername() {
        return username;
    }

}
