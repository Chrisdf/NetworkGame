package com.classes.util;

/**
 * Created by chris on 2/13/15.
 */
public enum Theme {

    STONELIGHT,
    STONEDARK,
    GRASS;


    public static Theme getRandomTheme() {

        int themePick = (int) (Math.random() * 2) + 1;

        switch (themePick) {

            case 1:
                return Theme.GRASS;
            case 2:
                return Theme.STONEDARK;
            default:
                return Theme.STONELIGHT;
        }
    }
}
