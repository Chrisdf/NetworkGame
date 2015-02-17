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

public class Game extends Canvas{

    private RenderWindow renderWindow;

    private final ConstView defaultView;

    private Client client;

    private Server server;

    private final View gameView;

    private FPS fps;

    private static Resource loader;

    private ArrayList<UIElement> uiElements;

    private static String username;

    private Map currentMap;

    private Player mainPlayer;

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

        mainPlayer = new Player(username, "yoda", new Vector2f(0, 0), gameView, 5);

        currentMap.addPlayer(username, mainPlayer);


        uiElements = new ArrayList<UIElement>();
        uiElements.add(fps);


        client.sendData("ping".getBytes());

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
                            currentMap.addPlayerInput(username, "D");
                            break;
                        case A:
                            currentMap.addPlayerInput(username, "A");
                            break;
                        case S:
                            currentMap.addPlayerInput(username, "S");
                            break;
                        case W:
                            currentMap.addPlayerInput(username, "W");
                            break;
                        case LSHIFT:
                            currentMap.addPlayerInput(username, "LSHIFT");
                    }
                    break;

                case KEY_RELEASED:
                    switch (event.asKeyEvent().key) {

                        case D:
                            currentMap.addPlayerInput(username, "D_RELEASED");
                            break;
                        case A:
                            currentMap.addPlayerInput(username, "A_RELEASED");
                            break;
                        case S:
                            currentMap.addPlayerInput(username, "S_RELEASED");
                            break;
                        case W:
                            currentMap.addPlayerInput(username, "W_RELEASED");
                            break;
                        case LSHIFT:
                            currentMap.addPlayerInput(username, "LSHIFT_RELEASED");
                            break;
                        case LCONTROL:
                            currentMap.addPlayerInput(username, "LCONTROL_RELEASED");
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

        renderWindow.setView(gameView);

        renderWindow.draw(currentMap);

        renderWindow.setView(defaultView);

        for (UIElement ui : uiElements)
            renderWindow.draw(ui);

        renderWindow.display();
    }

    public static Resource getLoader() {
        return loader;
    }


    public static String getUsername() {
        return username;
    }

}
