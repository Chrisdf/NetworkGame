package com.classes;

import com.classes.util.Resource;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class Main {

    private final RenderWindow renderWindow;

    private final ConstView defaultView;

    private final View gameView;

    private final float viewZoom = 0.5f;

    private final Resource loader;

    private final Player player;

    private Main() {

        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(640, 480), "NetworkGame");

        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());
        gameView.zoom(viewZoom);

        loader = new Resource();
        player = new Player(loader, "player", new Vector2f(0,0), gameView);

        runGame();

    }

    public static void main(String[] args) {

        new Main();
    }

    private void runGame() {

        while (renderWindow.isOpen()) {

            handleInput();

            update();

            draw();
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
                            player.addAcceleration(new Vector2f(1, 0));
                            break;
                        case A:
                            player.addAcceleration(new Vector2f(-1, 0));
                            break;
                    }
                    break;
            }
        }

    }

    private void update() {

    }

    private void draw() {


    }
}
