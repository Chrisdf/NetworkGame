package com.classes.util;

import org.jsfml.system.Vector2f;

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
