package com.greenmiststudios.pitch.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.greenmiststudios.pitch.components.PolygonRenderComponent;
import com.greenmiststudios.pitch.components.RenderOrderComponent;
import com.greenmiststudios.pitch.components.TextureRenderComponent;
import com.greenmiststudios.pitch.components.TransformComponent;

import java.util.Comparator;

/**
 * Created by geoffpowell on 10/29/15.
 */
public class SimpleRenderingSystem extends SortedIteratingSystem {

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureRenderComponent> renderMapper;
    private ComponentMapper<PolygonRenderComponent> polygonMapper;

    private SpriteBatch spriteBatch;
    private PolygonSpriteBatch polygonSpriteBatch;
    private Camera camera;

    public SimpleRenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(TransformComponent.class).one(TextureRenderComponent.class, PolygonRenderComponent.class).get(), new RenderComparator());
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        renderMapper = ComponentMapper.getFor(TextureRenderComponent.class);
        polygonMapper = ComponentMapper.getFor(PolygonRenderComponent.class);
        this.spriteBatch = spriteBatch;
        polygonSpriteBatch = new PolygonSpriteBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        TextureRenderComponent renderComponent = renderMapper.get(entity);
        PolygonRenderComponent polygonRenderComponent = polygonMapper.get(entity);


        if (camera != null) {
            spriteBatch.setTransformMatrix(camera.combined);
            polygonSpriteBatch.setTransformMatrix(camera.combined);
        }

        if (renderComponent != null) {
            spriteBatch.begin();
            spriteBatch.draw(renderComponent.texture,
                    transformComponent.position.x,
                    transformComponent.position.y,
                    renderComponent.texture.getRegionWidth() / 2,
                    renderComponent.texture.getRegionHeight() / 2,
                    renderComponent.texture.getRegionWidth(),
                    renderComponent.texture.getRegionHeight(),
                    transformComponent.scale.x,
                    transformComponent.scale.y,
                    transformComponent.angle);
            spriteBatch.end();
        }

        if (polygonRenderComponent != null) {
            polygonSpriteBatch.begin();
            polygonRenderComponent.sprite.setPosition(transformComponent.position.x, transformComponent.position.y);
            polygonRenderComponent.sprite.setRotation(transformComponent.angle * MathUtils.radDeg);
            polygonRenderComponent.sprite.draw(polygonSpriteBatch);
            polygonSpriteBatch.end();
        }
    }

    static class RenderComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity o1, Entity o2) {
            RenderOrderComponent renderComponentO1 = o1.getComponent(RenderOrderComponent.class);
            RenderOrderComponent renderComponentO2 = o2.getComponent(RenderOrderComponent.class);

            if (renderComponentO1 == null || renderComponentO2 == null) return 0;

            if (renderComponentO1.primaryOrder == renderComponentO2.primaryOrder) {
                return renderComponentO1.secondaryOrder - renderComponentO2.secondaryOrder;
            }
            return renderComponentO1.primaryOrder - renderComponentO2.primaryOrder;
        }
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
