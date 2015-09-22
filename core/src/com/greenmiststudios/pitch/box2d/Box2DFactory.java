package com.greenmiststudios.pitch.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.greenmiststudios.pitch.model.Wheel;
import com.greenmiststudios.pitch.model.Wheel.WheelPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class Box2DFactory {

    public static final float BOX_TO_WORLD = 10f;
    public static final float WORLD_TO_BOX = 0.1f;

    private static Vector2[] carBody = new Vector2[] {
            new Vector2(1.5f,0),
            new Vector2(3f, 2.5f),
            new Vector2(2.8f, 5.5f),
            new Vector2(1f,10),
            new Vector2(-1f,10),
            new Vector2(-2.8f, 5.5f),
            new Vector2(-3f, 2.5f),
            new Vector2(-1.5f,0),
    };

    private static Body createWheelBody(World world, FixtureDef def, Vector2 pos) {
        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(pos);
        Body body = world.createBody(bd);
        body.createFixture(def);
        body.setType(BodyDef.BodyType.DynamicBody);
        return body;
    }

    public static List<Wheel> createWheels(World world) {
        List<Wheel> wheels = new ArrayList<Wheel>();

        PolygonShape sd = new PolygonShape();
        sd.setAsBox(0.6f, 1);

        FixtureDef wheel = new FixtureDef();
        wheel.shape = sd;
        wheel.density = 0.4f;

        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(3, 8)), WheelPosition.LEFTFRONT));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(-3, 8)), WheelPosition.RIGHTFRONT));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(3, 0.5f)), WheelPosition.LEFTREAR));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(-3, 0.5f)), WheelPosition.RIGHTREAR));

        return wheels;
    }

    public static void attachCarJoints(World world, Body car, List<Wheel> wheels) {
        for (Wheel wheel : wheels) {
            RevoluteJointDef jointDef = new RevoluteJointDef();
            jointDef.bodyA = car;
            jointDef.enableLimit = true;
            jointDef.lowerAngle = 0;//with both these at zero...
            jointDef.upperAngle = 0;//...the joint will not move
            jointDef.localAnchorB.setZero();//joint anchor in tire is always center

            jointDef.bodyB = wheel.getBody();
            jointDef.localAnchorA.set(wheel.getBody().getLocalCenter());
            RevoluteJoint joint = (RevoluteJoint) world.createJoint(jointDef);
            wheel.setWheelJoint(joint);
        }
    }

    public static Body createCar(World world, float x, float y) {
        PolygonShape sd = new PolygonShape();
        sd.set(carBody);

        FixtureDef carFixture = new FixtureDef();
        carFixture.shape = sd;
        carFixture.density = 0.1f;

        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(x, y);
        Body body = world.createBody(bd);
        body.createFixture(carFixture);
        body.setType(BodyDef.BodyType.DynamicBody);
        return body;
    }

}
