package com.classes;

import com.classes.util.Room;
import com.classes.util.VectorFunctions;
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

    int numOfDungeons;

    public Map(IntRect mapDimensions) {

        roomList = new ArrayList<Room>();
        numOfDungeons = (int)(Math.random() * 8) + 1;

        int currentNumOfDungeons = 0;

        while(currentNumOfDungeons < numOfDungeons) {

            Vector2i roomDimensions = VectorFunctions.randomNum(new Vector2i(5,15), new Vector2i(10, 30));

            Vector2i mapBoundsX = new Vector2i(mapDimensions.left, mapDimensions.width);
            Vector2i mapBoundsY = new Vector2i( mapDimensions.top, mapDimensions.height);
            Vector2f roomCoords = new Vector2f(VectorFunctions.randomNum(mapBoundsX, mapBoundsY));

            Room currentRoom = new Room(roomDimensions, roomCoords, "stone");

            if(checkForIntersections(currentRoom) == true)
                currentRoom = null;

            else
                currentNumOfDungeons++;
                roomList.add(currentRoom);
        }

    }

    public Map(IntRect mapDimensions, int numOfDunegeons) {

        new Map(mapDimensions);
        this.numOfDungeons = numOfDunegeons;
    }

    private boolean checkForIntersections(Room currentRoom) {

        boolean intersects = false;

        for(Room room: roomList) {
            if(room.getCornerCoords().intersection(currentRoom.getCornerCoords()) != null)
                intersects = true;
        }

        return intersects;
    }

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

        for(Room current: roomList)
            current.draw(renderTarget, renderStates);
    }
}
