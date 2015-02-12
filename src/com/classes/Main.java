package com.classes;

import com.classes.ui.FPS;
import com.classes.util.Entity;
import com.classes.util.Resource;
import com.classes.util.UIElement;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class Main {

    private final RenderWindow renderWindow;

    private final ConstView defaultView;

    private final View gameView;

    private final float viewZoom = 5f;

    private FPS fps;

    private static Resource loader;

    private ArrayList<Entity> entityList;

    private ArrayList<UIElement> uiElements;

    private Map currentMap;

    private final Player player;

    private Main() {

        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(1280, 720), "NetworkGame");
        renderWindow.setFramerateLimit(60);

        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());
        gameView.zoom(viewZoom);

        loader = new Resource();

        currentMap = new Map(new IntRect(-400, -400, 800, 800));

        fps = new FPS();
        player = new Player("yoda", new Vector2f(0, 0), gameView, 5);

        entityList = new ArrayList<Entity>();

        uiElements = new ArrayList<UIElement>();
        uiElements.add(fps);

        runGame();

    }

    public static void main(String[] args) {

        new Main();
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
                            player.addInput("D");
                            break;
                        case A:
                            player.addInput("A");
                            break;
                        case S:
                            player.addInput("S");
                            break;
                        case W:
                            player.addInput("W");
                            break;
                        case LSHIFT:
                            player.addInput("LSHIFT");
                    }
                    break;

                case KEY_RELEASED:
                    switch (event.asKeyEvent().key) {

                        case D:
                            player.addInput("D_RELEASED");
                            break;
                        case A:
                            player.addInput("A_RELEASED");
                            break;
                        case S:
                            player.addInput("S_RELEASED");
                            break;
                        case W:
                            player.addInput("W_RELEASED");
                            break;
                        case LSHIFT:
                            player.addInput("LSHIFT_RELEASED");
                    }
                    break;
            }
        }

    }

    private void update() {

        player.update();

        for (Entity entity : entityList) {
            entity.update();
        }
    }

    private void draw(float deltaTime) {


        renderWindow.clear();

        renderWindow.setView(gameView);

        renderWindow.draw(currentMap);

        for (Entity entity : entityList) {
            entity.interpolate(deltaTime);
            renderWindow.draw(entity);
        }

        renderWindow.draw(player);


        renderWindow.setView(defaultView);

        for (UIElement ui : uiElements)
            renderWindow.draw(ui);

        renderWindow.display();
    }

    public static Resource getLoader() {
        return loader;
    }
}
