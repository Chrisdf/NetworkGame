package com.classes;

import com.classes.util.Room;
import org.jsfml.graphics.IntRect;

import java.util.ArrayList;

/**
 * Created by Chris on 2/12/2015.
 */
public class Map {

    ArrayList<Room> roomList;

    public Map(IntRect mapDimensions) {

        roomList = new ArrayList<Room>();
    }

    public Map(IntRect mapDimensions, int numOfDunegeons) {

    }
}
