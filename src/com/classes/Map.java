package com.classes;

import com.classes.util.Room;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

/**
 * Created by Chris on 2/12/2015.
 */
public class Map implements Drawable{

    ArrayList<Room> roomList;

    public Map(IntRect mapDimensions) {

        roomList = new ArrayList<Room>();
        roomList.add(new Room(new Vector2i(30,30), new Vector2f(0,0), "stone"));
    }

    public Map(IntRect mapDimensions, int numOfDunegeons) {

        new Map(mapDimensions);
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for(Room current: roomList)
            current.draw(renderTarget, renderStates);
    }
}
