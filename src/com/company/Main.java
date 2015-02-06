package com.company;

import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class Main {

    private final RenderWindow renderWindow;

    private final ConstView defaultView;

    private final View gameView;

    private final float viewZoom = 0.5f;

    private Main() {

        renderWindow = new RenderWindow();
        renderWindow.create(new VideoMode(640, 480), "NetworkGame");

        defaultView = renderWindow.getDefaultView();

        gameView = new View(defaultView.getCenter(), defaultView.getSize());
        gameView.zoom(viewZoom);

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

                        case W:
                            System.out.println("W");
                            break;
                        case S:
                            System.out.println("S");
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
