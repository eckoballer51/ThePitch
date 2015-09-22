package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class PhysicsEntity extends Entity {
    protected Body body;
    protected World world;

    public PhysicsEntity(String tag) {
        super(tag);
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
