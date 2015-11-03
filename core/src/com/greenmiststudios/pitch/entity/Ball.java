package com.greenmiststudios.pitch.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

/**
 * Created by geoffpowell on 9/27/15.
 */
public class Ball extends PhysicsEntity implements IRenderable {

    private float radius = 8f;

    public Ball() {
        super("Ball");
    }

    @Override
    public void create() {
        super.create();
        body = Box2DFactory.createBall(world, radius);
    }

    @Override
    public void render() {
        ShapeRenderer renderer = getParent().getShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.circle(location.x, location.y, radius * Box2DFactory.BOX_TO_WORLD);
        renderer.end();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

    }

    @Override
    public int getPrimaryOrdering() {
        return 2;
    }

    @Override
    public int getSecondaryOrdering() {
        return 1;
    }
}
