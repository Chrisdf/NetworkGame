package com.classes.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chris on 2/13/15.
 */
public enum TileType {

    STONELIGHT(1),
    STONEDARK(2),
    STONEDARKEST(3),
    GLASS(4);

    public static String getTileTextureName(int index) {

        switch (index) {

            case 1:
                return "Stone_Light";
            case 2:
                return "Stone_Dark";
            case 3:
                return "Stone_Darkest";
            case 4:
                return "Glass";
        }

        return "Stone_Dark";
    }

    private final int index;

    TileType(int index) {
        this.index = index;
    }

    private static final Map<Integer, TileType> enumMap;

    static {
        enumMap = new HashMap<Integer, TileType>();
        for (TileType v : TileType.values())
            enumMap.put(v.index, v);

        System.out.println(enumMap);
    }

    public static TileType findTileTypeByIndex(int index) {

        return enumMap.get(index);
    }

    private static final Map<TileType, Integer> indexMap;

    static {
        indexMap = new HashMap<TileType, Integer>();
        for (int i = 1; i < TileType.values().length + 1; i++)
            indexMap.put(TileType.findTileTypeByIndex(i), i);

        System.out.println(indexMap);
    }

    public static int findIndexByTileType(TileType type) {

        System.out.println(type);
        return indexMap.get(type);
    }


    public static TileType getRandomTileType() {

        int numOfTypes = TileType.values().length;

        int typePick = (int) (Math.random() * numOfTypes + 1);

        return enumMap.get(typePick);
    }
}
