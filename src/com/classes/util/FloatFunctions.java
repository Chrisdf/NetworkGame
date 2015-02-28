package com.classes.util;

/**
 * Created by Chris on 2/11/2015.
 */
public abstract class FloatFunctions {

    private FloatFunctions() {
    }


    public static boolean isEqual(float f1, float f2) {

        double desiredPrecision = 0.001;

        if (Math.abs(f1 - f2) <= desiredPrecision)
            return true;
        else
            return false;
    }

    public static boolean isEqual(float f1, float f2, double precision) {

        double desiredPrecision = precision;

        if (Math.abs(f1 - f2) <= desiredPrecision)
            return true;
        else
            return false;
    }
}
