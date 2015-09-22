package com.greenmiststudios.pitch.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.enums.Direction;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.interfaces.IUpdateable;
import com.greenmiststudios.pitch.model.entity.Entity;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

import java.awt.image.DirectColorModel;
import java.util.List;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class Car extends PhysicsEntity implements IRenderable, IUpdateable {

    private List<Wheel> wheels;
    private float lockAngle = (float) Math.toRadians(35);
    float turnSpeedPerSec = (float) Math.toRadians(160);//from lock to lock in 0.5 sec
    float turnPerTimeStep = turnSpeedPerSec / 60.0f;

    protected int direction;

    public Car() {
        super("Car");
    }

    @Override
    public void create() {
        body = Box2DFactory.createCar(world, 0, 0);
        wheels = Box2DFactory.createWheels(world);
        Box2DFactory.attachCarJoints(world, body, wheels);
        super.create();
    }

    @Override
    public void render() {
        if (getParent() == null) return;
        ShapeRenderer renderer = getParent().getShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.CHARTREUSE);
        renderer.circle(location.x, location.y, 30);
        renderer.end();
    }

    @Override
    public void update(float dt) {
        for (Wheel wheel : wheels) {
            wheel.updateFriction();
        }
        for (Wheel wheel : wheels) {
            wheel.updateDrive(direction);
        }
    }
}
