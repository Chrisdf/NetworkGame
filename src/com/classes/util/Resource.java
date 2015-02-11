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
    }

    /*
    Loads the texture from an input stream and adds it to the texture map
     */
    public void addTexture(String name) {

        Texture oneToAdd = new Texture();
        //InputStream stream = Resource.class.getResourceAsStream("resources/images/" + name + ".jpg");

        try {
            //oneToAdd.loadFromStream(stream);
            oneToAdd.loadFromFile(Paths.get("src/com/classes/resources/images/" + name + ".jpg"));

        } catch (Exception e) {
            System.out.print("Texture " + name + "failed to load as a jpg");
            e.printStackTrace();
        }

        textureMap.put(name, oneToAdd);
    }

    public Texture getTexture(String textureName) {

        return textureMap.get(textureName);
    }

    public void addFont(String name) {

        Font oneToAdd = new Font();

        try {
            oneToAdd.loadFromFile(Paths.get("src/com/classes/resources/fonts/" + name + ".ttf"));
        } catch (Exception e) {
            System.out.print("Font " + name + "did not load properly");
            e.printStackTrace();
        }

        fontMap.put(name, oneToAdd);
    }

    public Font getFont(String fontName) {

        return fontMap.get(fontName);
    }


}
