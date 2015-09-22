package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.interfaces.IUpdateable;
import com.greenmiststudios.pitch.model.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class EntityHolder extends Entity implements IRenderable, IUpdateable {
    private Map<String, IRenderable> renderables;
    private Map<String, IUpdateable> updatables;
    private Map<String, Entity> entities;
    private ShapeRenderer shapeRenderer;

    public EntityHolder() {
        setup();
    }

    public EntityHolder(String tag) {
        super(tag);
        setup();
    }

    private void setup() {
        renderables = new HashMap<String, IRenderable>();
        updatables = new HashMap<String, IUpdateable>();
        entities = new HashMap<String, Entity>();
        shapeRenderer = new ShapeRenderer(1000);
    }

    @Override
    public void create() {
        for (Entity entity: entities.values()) {
            if (!entity.wasCreated()) entity.create();
        }
        super.create();
    }

    public boolean addEntity(Entity entity) {
        if (entities.containsKey(entity.getTag())) return false;
        entity.setParent(this);
        if (!entity.wasCreated()) entity.create();
        entities.put(entity.getTag(), entity);
        if (entity instanceof IRenderable && !renderables.containsKey(entity.getTag())) renderables.put(entity.getTag(), (IRenderable) entity);
        if (entity instanceof IUpdateable && !updatables.containsKey(entity.getTag())) updatables.put(entity.getTag(), (IUpdateable) entity);
        return true;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void render() {
        for (IRenderable renderable : renderables.values()) {
            renderable.render();
        }
    }

    public void update(float dt) {
        for (IUpdateable updateable : updatables.values()) {
            updateable.update(dt);
        }
    }


}
