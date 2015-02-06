package com.classes.util;

import org.jsfml.graphics.Texture;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps in loading all sound, texture and misc. files
 *
 * Created by Chris on 2/5/2015.
 */
public class Resource {

    private Map<String, Texture> textureMap;

    public Resource(){

       textureMap = new HashMap<String, Texture>();
    }

    /*
    Loads the texture from an input stream and adds it to the texture map
     */
    public void addTexture(String name) {

        Texture oneToAdd = new Texture();
        InputStream stream = Resource.class.getResourceAsStream("com/classes/resources/images" + name + ".jpg");

        try {
            oneToAdd.loadFromStream(stream);
        } catch (Exception e) {
            System.out.print("Texture " + name + "failed to load as a jpg");
            e.printStackTrace();
        }

        textureMap.put(name, oneToAdd);
    }

    public Texture getTexture(String textureName) {

        return textureMap.get(textureName);
    }


}
