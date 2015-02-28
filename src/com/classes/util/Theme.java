package com.classes.util;

/**
 * Created by chris on 2/13/15.
 */
public enum Theme {

    STONELIGHT,
    STONEDARK,
    STONEDARKEST,
    GLASS;


    public static Theme getRandomTheme() {

        int numOfThemes = 4;

        int themePick = (int) (Math.random() * numOfThemes) + 1;

        switch (themePick) {

            case 1:
                return Theme.GLASS;
            case 2:
                return Theme.STONEDARK;
            case 3:
                return Theme.STONEDARKEST;
            case 4:
                return Theme.STONELIGHT;
            default:
                return Theme.STONELIGHT;
        }
    }
}
