package com.classes.util;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.Vector;

/**
 * Created by chris on 2/9/15.
 */
public class VectorFunctions {

    private VectorFunctions(){}

    public static Vector2f abs(Vector2f vector){

        float xPos = Math.abs(vector.x);
        float yPos = Math.abs(vector.y);

        return new Vector2f(xPos, yPos);
    }

    public static Vector2f round(Vector2f vector){

        float xPos = Math.round(vector.x);
        float yPos = Math.round(vector.y);


        return new Vector2f(xPos, yPos);
    }

    public static Vector2i randomNum(Vector2i xRange, Vector2i yRange) {

        int xDiff = xRange.y - xRange.x;
        int yDiff = yRange.y - yRange.x;

        int xRand = (int)(Math.random() * xDiff) + xRange.x;
        int yRand = (int)(Math.random() * yDiff) + yRange.x;

        return new Vector2i(xRand, yRand);
    }

    public static Vector2f getSign(Vector2f vector){

        float xSign = vector.x;
        float ySign = vector.y;

        if(xSign >= 0)
            xSign = 1;
        else
            xSign = -1;

        if(ySign >= 0)
            ySign = 1;
        else
            ySign = -1;

        return new Vector2f(xSign, ySign);

    }
}
