package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class Entity {

    protected String tag = "Entity " + count;
    private static int count = 0;
    protected EntityHolder parent;
    protected Vector2 location = Vector2.Zero;

    protected boolean created;

    public Entity() {
        count++;
    }

    public Entity(String tag) {
        this();
        this.tag = tag;
    }

    public void create() {
        if (created) return;
        created = true;
    }

    public boolean wasCreated() {
        return created;
    }

    public String getTag() {
        return tag;
    }

    public void setParent(EntityHolder parent) {
        this.parent = parent;
    }

    public EntityHolder getParent() {
        return parent;
    }
}
