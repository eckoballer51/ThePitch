package com.greenmiststudios.pitch.entity;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.greenmiststudios.pitch.PitchGame;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.entity.car.PlayerCar;
import com.greenmiststudios.pitch.enums.Team;
import com.greenmiststudios.pitch.model.entity.CustomCamera;
import com.greenmiststudios.pitch.model.entity.Entity;
import com.greenmiststudios.pitch.model.entity.EntityHolder;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class GameWorld extends EntityHolder implements ApplicationListener {

    private Texture texture;
    private World world;
    private Box2DDebugRenderer debug;
    private CustomCamera camera;
    private BitmapFont font;
    private SpriteBatch fontBatch = new SpriteBatch();

    public GameWorld() {
        super("GameWorld");
        world = new World(Vector2.Zero, true);
        camera = new CustomCamera();
        camera.setToOrtho(false);
        debug = new Box2DDebugRenderer(true, true, false, true, true, true);
        font = PitchGame.ASSETMANAGER.get("walkway_bold.ttf");
        texture = PitchGame.ASSETMANAGER.get("arrow.png");
        addEntity(new Field());
        addEntity(new PlayerCar(Team.RED));
        addEntity(new Ball());

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                handleContact(contact);
            }

            @Override
            public void endContact(Contact contact) {
                handleContact(contact);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
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

    public void resize(int width, int height) {}

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
        polySpriteBatch.setProjectionMatrix(camera.combined);

        super.render();
        fontBatch.begin();
        if (PitchGame.DEBUG) {
            Matrix4 debugMatrix = new Matrix4(camera.combined);
            debugMatrix.scale(Box2DFactory.BOX_TO_WORLD, Box2DFactory.BOX_TO_WORLD, 1f);
            debug.render(world, debugMatrix);
            if (findByTag("Player") != null) {
                PhysicsEntity player = findByTag("Player");
                font.draw(fontBatch, "Velocity: " + player.getBody().getLinearVelocity().len(), 40,  40);
            }
        }
        if (findByTag("Player") != null) {
            PhysicsEntity player = findByTag("Player");
            font.draw(fontBatch, "Forward Normal: " + player.getBody().getWorldVector(new Vector2(0,1)), 40,  60);
        }
        font.draw(fontBatch, "Camera up: " + camera.up.toString() + " down: " + camera.direction.toString() , 40,  100);
        font.draw(fontBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() , Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 20);
        fontBatch.end();
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
        Entity player = findByTag(PlayerCar.TAG);
        float playerAngle = player.getRotation();
            //camera.rotate(rotationDif);
            //camera.up.traMul()
        camera.position.set(player.getLocation(), 0);
        camera.setLerpAngle(playerAngle, dt);
        camera.update();
        world.step(1f / 60f, 8, 3);
        super.update(dt);
    }

    private void handleContact(Contact contact) {
    }

}
