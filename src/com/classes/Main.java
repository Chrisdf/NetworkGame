package com.classes;

import com.classes.ui.FPS;
import com.classes.util.*;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private final RenderWindow renderWindow;

    private final ConstView defaultView;

    private final View gameView;

    private final float viewZoom = 0.5f;

    private FPS fps;

    private final Resource loader;

    private ArrayList<Entity> entityList;

    private ArrayList<UIElement> uiElements;

    private final Player player;

    private Main() {

        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(1280, 720), "NetworkGame");
        renderWindow.setFramerateLimit(60);

        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());
        gameView.zoom(viewZoom);

        loader = new Resource();
        loader.addTexture("player");
        loader.addTexture("back");
        loader.addFont("heav");

        fps = new FPS(loader);
        player = new Player(loader, "player", new Vector2f(0,0), gameView);

        entityList = new ArrayList<Entity>();
        entityList.add(new NPC(loader, "back", new Vector2f(0,0)));

        uiElements = new ArrayList<UIElement>();
        uiElements.add(fps);

        runGame();

    }

    public static void main(String[] args) {

        new Main();
    }

    private void runGame() {

        Clock gameClock = new Clock();

        final float seconds_per_tick = 1/20f;
        float lag = 0;
        int framesDrawn = 0;
        float frameTime = 0;


        while (renderWindow.isOpen()) {

            float elapsedTime = gameClock.restart().asSeconds();
            lag += elapsedTime;
            frameTime += elapsedTime;


            handleInput();

            while(lag >= seconds_per_tick) {

                update();
                lag -= seconds_per_tick;
            }


            draw(lag / seconds_per_tick);

            if(frameTime >= 1.0f) {
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
                            player.setAcceleration(Direction.RIGHT, new Vector2f(0.5f, player.acceleration.y));
                            break;
                        case A:
                            player.setAcceleration(Direction.LEFT, new Vector2f(0.5f, player.acceleration.y));
                            break;
                        case S:
                            player.setAcceleration(Direction.UP, new Vector2f(player.acceleration.x, 0.5f));
                            break;
                        case W:
                            player.setAcceleration(Direction.DOWN, new Vector2f(player.acceleration.x, 0.5f));
                            break;
                    }
                    break;

                case KEY_RELEASED:
                    switch (event.asKeyEvent().key) {

                        case D:
                            player.stopMovementHorizontally(Direction.RIGHT);
                            break;
                        case A:
                            player.stopMovementHorizontally(Direction.LEFT);
                            break;
                        case S:
                            player.stopMovementVertically(Direction.DOWN);
                            break;
                        case W:
                            player.stopMovementVertically(Direction.UP);
                            break;
                    }
                    break;
            }
        }

    }

    private void update() {

        player.update();

        System.out.println(player);

        for(Entity entity: entityList) {
            entity.update();
        }
    }

    private void draw(float deltaTime) {


        renderWindow.clear();


        renderWindow.setView(defaultView);


        for(UIElement ui: uiElements)
            renderWindow.draw(ui);

        renderWindow.setView(gameView);

        for(Entity entity: entityList) {
            entity.interpolate(deltaTime);
            renderWindow.draw(entity);
        }

        renderWindow.draw(player);

        renderWindow.display();
    }
}
