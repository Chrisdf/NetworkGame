package com.classes.util;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.Texture;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps in loading all sound, texture and misc. files
 * <p/>
 * Created by Chris on 2/5/2015.
 */
public class Resource {

    private Map<String, Texture> textureMap;

    private Map<String, Font> fontMap;

    public Resource() {

        textureMap = new HashMap<String, Texture>();
        fontMap = new HashMap<String, Font>();

        addResources();
    }

    public void addResources() {
        addTexture("yoda", "png");
        addFont("heav", "ttf");
        addTexture("Glass", "png");
        addTexture("Stone_Dark", "png");
        addTexture("Stone_Light", "png");
        addTexture("Stone_Darkest", "png");
        addTexture("Red_Rock", "png");

    }

    public void addTexture(String name, String extension) {

        Texture oneToAdd = new Texture();

        try {

            oneToAdd.loadFromStream(loadResource("/images/" + name + "." + extension));

        } catch (Exception e) {
            System.out.println("Texture " + name + " failed to load as extension " + extension);
            e.printStackTrace();
        }

        textureMap.put(name, oneToAdd);
    }

    public Texture getTexture(String textureName) {

        return textureMap.get(textureName);
    }

    public void addFont(String name, String extension) {

        Font oneToAdd = new Font();

        try {

            oneToAdd.loadFromStream(loadResource("/fonts/" + name + "." + extension));

        } catch (Exception e) {
            System.out.println("Font " + name + "did not load properly" + "as extension " + extension);
            e.printStackTrace();
        }

        fontMap.put(name, oneToAdd);
    }

    public Font getFont(String fontName) {

        return fontMap.get(fontName);
    }

    public static InputStream loadResource(String name) {

        return Resource.class.getResourceAsStream(name);
    }


}
