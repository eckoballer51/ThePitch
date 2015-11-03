package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.OrderedMap;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.interfaces.IUpdateable;
import com.greenmiststudios.pitch.model.entity.Entity;
import javafx.collections.transformation.SortedList;

import java.util.*;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class EntityHolder extends Entity implements  IRenderable, IUpdateable {

    private SortedSet<IRenderable> renderables;
    private List<IUpdateable> updatables;
    private Map<String, Entity> entities;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;
    protected PolygonSpriteBatch polySpriteBatch;

    public EntityHolder() {
        setup();
    }

    public EntityHolder(String tag) {
        super(tag);
        setup();
    }

    private void setup() {
        renderables = new TreeSet<IRenderable>(renderableComparator);
        updatables = new ArrayList<IUpdateable>();
        entities = new HashMap<String, Entity>();
        shapeRenderer = new ShapeRenderer(1000);
        spriteBatch = new SpriteBatch();
        polySpriteBatch = new PolygonSpriteBatch();
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
        if (entity instanceof IRenderable) {
            renderables.add((IRenderable) entity);
        }
        if (entity instanceof IUpdateable) updatables.add((IUpdateable) entity);
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T findByTag(String tag) {
        return (T) entities.get(tag);
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    @Override
    public int getPrimaryOrdering() {
        return 0;
    }

    @Override
    public int getSecondaryOrdering() {
        return 0;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public PolygonSpriteBatch getPolySpriteBatch() {
        return polySpriteBatch;
    }

    public void render() {
        for (IRenderable renderable : renderables) {
            renderable.render();
        }
    }

    public void update(float dt) {
        for (IUpdateable updateable : updatables) {
            updateable.update(dt);
        }
    }

    @Override
    public void dispose() {
        for (Entity entity: entities.values()) {
            entity.dispose();
        }
        entities.clear();
        renderables.clear();
        updatables.clear();
    }

    private final Comparator<IRenderable> renderableComparator = new Comparator<IRenderable>() {
        @Override
        public int compare(IRenderable o1, IRenderable o2) {
            if (o1 == null || o2 == null) return 0;

            if (o1.getPrimaryOrdering() == o2.getPrimaryOrdering()) {
                return o1.getSecondaryOrdering() - o2.getSecondaryOrdering();
            } else {
                return o1.getPrimaryOrdering() - o2.getPrimaryOrdering();
            }
        }
    };
}
