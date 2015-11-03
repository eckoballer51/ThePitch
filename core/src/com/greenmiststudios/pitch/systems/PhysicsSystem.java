package com.greenmiststudios.pitch.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.components.PhysicsComponent;
import com.greenmiststudios.pitch.components.TransformComponent;

/**
 * Created by geoffpowell on 10/29/15.
 */
public class PhysicsSystem extends IteratingSystem {

    private ComponentMapper<PhysicsComponent> physicsMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    private static World world;

    public PhysicsSystem(World w) {
        super(Family.all(PhysicsComponent.class, TransformComponent.class).get());
        physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        world = w;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(1f / 60f, 8, 3);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physicsComponent = physicsMapper.get(entity);
        TransformComponent transformComponent = transformMapper.get(entity);
        Vector2 physicsPosition = physicsComponent.body.getPosition();

        transformComponent.position = new Vector2(physicsPosition.x * Box2DFactory.BOX_TO_WORLD, physicsPosition.y * Box2DFactory.BOX_TO_WORLD);
        transformComponent.angle = wrapAngle(physicsComponent.body.getAngle());
    }

    public float wrapAngle( float angle) {
        while (angle > MathUtils.PI) angle -= MathUtils.PI2;
        while (angle < 0) angle +=  MathUtils.PI2;
        return angle;
    }

    public static World getWorld() {
        return world;
    }
}
