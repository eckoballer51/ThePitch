package com.greenmiststudios.pitch.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.greenmiststudios.pitch.PitchGame;
import com.greenmiststudios.pitch.model.entity.Entity;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.interfaces.IUpdateable;
import com.greenmiststudios.pitch.model.GameWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class MainGameState extends GameState {

    private Map<String, IRenderable> renderables;
    private Map<String, IUpdateable> updatables;
    private Map<String, Entity> entities;
    private GameWorld gameWorld;

    public MainGameState() {
        super("GAME");
        renderables = new HashMap<String, IRenderable>();
        updatables = new HashMap<String, IUpdateable>();
        entities = new HashMap<String, Entity>();
        gameWorld = new GameWorld();
        addEntity(gameWorld);
    }

    public void addEntity(Entity entity) {
        if (entities.containsKey(entity.getTag())) return;
        entities.put(entity.getTag(), entity);
        if (entity instanceof IRenderable && !renderables.containsKey(entity.getTag())) renderables.put(entity.getTag(), (IRenderable) entity);
        if (entity instanceof IUpdateable && !updatables.containsKey(entity.getTag())) updatables.put(entity.getTag(), (IUpdateable) entity);
    }

    @Override
    public void create() {
        super.create();
        for (Entity entity: entities.values()) {
            if (!entity.wasCreated()) entity.create();
        }
    }

    @Override
    public void render() {
        super.render();
        for (IRenderable renderable : renderables.values()) {
            renderable.render();
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (IUpdateable updateable : updatables.values()) {
            updateable.update(dt);
        }
    }
}
