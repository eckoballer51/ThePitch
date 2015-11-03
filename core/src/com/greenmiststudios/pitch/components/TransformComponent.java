package com.greenmiststudios.pitch.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by geoffpowell on 10/29/15.
 */
public class TransformComponent implements Component {

    public Vector2 position = new Vector2();
    public Vector2 scale = new Vector2(1,1);
    public float angle = 0.0f;

}
