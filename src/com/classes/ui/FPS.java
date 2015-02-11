package com.classes.ui;

import com.classes.util.Resource;
import com.classes.util.UIElement;
import org.jsfml.graphics.*;

/**
 * Created by chris on 2/9/15.
 */
public class FPS extends UIElement {

    private Resource loader;

    private Text displayedFPS;

    public FPS(Resource loader) {

        this.loader = loader;

        displayedFPS = new Text("", loader.getFont("heav"), 15);
        displayedFPS.setPosition(0f, 0f);
        displayedFPS.setColor(Color.CYAN);
    }

   public void displayFPS(int fps){

       displayedFPS.setString(fps + "");
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        displayedFPS.draw(renderTarget, renderStates);
    }
}
