package com.greenmiststudios.pitch.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.greenmiststudios.pitch.PitchGame;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.model.entity.Entity;
import com.greenmiststudios.pitch.model.entity.EntityHolder;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class GameWorld extends EntityHolder implements ApplicationListener {

    private World world;
    private Box2DDebugRenderer debug;
    private OrthographicCamera camera;

    public GameWorld() {
        super("GameWorld");
        world = new World(Vector2.Zero, true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debug = new Box2DDebugRenderer(true,true, false,true,false,true);
        addEntity(new PlayerCar());
    }

    @Override
    public boolean addEntity(Entity entity) {
        if (entity instanceof PhysicsEntity) ((PhysicsEntity) entity).setWorld(world);
        return super.addEntity(entity);
    }

    @Override
    public void create() {
        super.create();
    }

    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.render();
        getShapeRenderer().setProjectionMatrix(camera.combined);
        if (PitchGame.DEBUG) {
            Matrix4 debugMatrix = new Matrix4(camera.combined);
            debugMatrix.scale(Box2DFactory.BOX_TO_WORLD, Box2DFactory.BOX_TO_WORLD, 1f);
            debug.render(world, debugMatrix);
        }
    }

    private void tick(long msTick, int iter) {
        world.step(msTick, iter, iter);
    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        world.dispose();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        camera.update();
        tick(((long) dt * 3000), 4);
    }
}
