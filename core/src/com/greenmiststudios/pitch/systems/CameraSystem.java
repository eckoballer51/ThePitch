package com.greenmiststudios.pitch.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.greenmiststudios.pitch.components.CameraComponent;
import com.greenmiststudios.pitch.components.TransformComponent;

/**
 * Created by geoffpowell on 10/29/15.
 */
public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<CameraComponent> cameraMapper;
    private final ComponentMapper<TransformComponent> transformMapper;

    public CameraSystem() {
        super(Family.all(CameraComponent.class).get());
        cameraMapper = ComponentMapper.getFor(CameraComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SimpleRenderingSystem system = getEngine().getSystem(SimpleRenderingSystem.class);

        CameraComponent component = cameraMapper.get(entity);
        system.setCamera(component.camera);

        TransformComponent transformComponent = transformMapper.get(entity);
        if (transformComponent == null) return;
        component.camera.position.x = transformComponent.position.x;
        component.camera.position.y = transformComponent.position.y;
        component.camera.setLerpAngle(transformComponent.angle, deltaTime);
    }
}
