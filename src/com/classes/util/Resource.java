package com.classes.util;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;
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
        addTexture("stone", "png");
        addTexture("grass", "png");
    }

    public void addTexture(String name, String extension) {

        Texture oneToAdd = new Texture();
        //InputStream stream = Resource.class.getResourceAsStream("resources/images/" + name + ".jpg");

        try {
            //oneToAdd.loadFromStream(stream);
            oneToAdd.loadFromFile(Paths.get("src/com/classes/resources/images/" + name + "." + extension));

        } catch (Exception e) {
            System.out.print("Texture " + name + "failed to load as extension " + extension);
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
            oneToAdd.loadFromFile(Paths.get("src/com/classes/resources/fonts/" + name + "." + extension));
        } catch (Exception e) {
            System.out.print("Font " + name + "did not load properly" + "as extension " + extension);
            e.printStackTrace();
        }

        fontMap.put(name, oneToAdd);
    }

    public Font getFont(String fontName) {

        return fontMap.get(fontName);
    }


}
