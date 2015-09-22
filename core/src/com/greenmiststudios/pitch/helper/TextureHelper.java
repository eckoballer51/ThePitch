package com.greenmiststudios.pitch.helper;

import com.badlogic.gdx.graphics.Texture;

import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class TextureHelper {
    private static Map<String, Texture> textureMap = new HashMap<String, Texture>();

    public static void addTexture(String tag, String path) {
        textureMap.put(tag, new Texture(path));
    }

    public static void addTexture(String tag, Texture texture) {
        textureMap.put(tag, texture);
    }

    public static Texture getTexture(String tag) {
        return textureMap.get(tag);
    }
}
