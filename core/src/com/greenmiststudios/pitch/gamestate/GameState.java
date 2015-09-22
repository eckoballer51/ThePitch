package com.greenmiststudios.pitch.gamestate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class GameState extends ApplicationAdapter {
    protected GameStateManager manager;
    private final String tag;
    private boolean created;
    protected boolean needsUpdate = true;
    protected int width;
    protected int height;


    public GameState(String tag) {
        this.tag = tag;
    }

    public void setManager(GameStateManager manager) {
        this.manager = manager;
    }

    public String getTag() {
        return tag;
    }

    public boolean isAttached() {
        return manager != null;
    }


    public boolean wasCreated() {
        return created;
    }

    @Override
    public void create() {
        super.create();
        created = true;
    }

    public void update(float dt) {
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.width = width;
        this.height = height;
    }
}
