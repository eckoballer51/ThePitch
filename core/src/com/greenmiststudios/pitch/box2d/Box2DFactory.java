package com.greenmiststudios.pitch.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.greenmiststudios.pitch.Utils;
import com.greenmiststudios.pitch.enums.WheelPosition;
import com.greenmiststudios.pitch.entity.car.Wheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class Box2DFactory {

    public static final float BOX_TO_WORLD = 10f;
    public static final float WORLD_TO_BOX = 0.1f;

    private static Vector2[] rightHalf = new Vector2[] {
            new Vector2(0,0),
            new Vector2(1.5f,0),
            new Vector2(3f, 2.5f),
            new Vector2(2.8f, 5.5f),
            new Vector2(1f,10)
    };

    private static Vector2[] leftHalf = new Vector2[] {
            new Vector2(-1f,10),
            new Vector2(-2.8f, 5.5f),
            new Vector2(-3f, 2.5f),
            new Vector2(-1.5f,0),
            new Vector2(0,0)
    };

    public static Vector2[] carBody = Utils.concat(rightHalf, leftHalf);


    private static Body createWheelBody(World world, FixtureDef def, Vector2 pos) {
        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(pos);
        bd.type = BodyDef.BodyType.DynamicBody;

        Body body = createTopDownBody(bd, world);
        body.createFixture(def);
        return body;
    }

    public static List<Wheel> createWheels(World world, short carFlag) {
        List<Wheel> wheels = new ArrayList<Wheel>();

        PolygonShape sd = new PolygonShape();
        sd.setAsBox(0.6f, 1);

        FixtureDef wheel = new FixtureDef();
        wheel.shape = sd;
        wheel.density = 0.1f;
        wheel.restitution = 0.3f;
        wheel.filter.groupIndex = carFlag;

        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(3, 8)), WheelPosition.LEFTFRONT));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(-3, 8)), WheelPosition.RIGHTFRONT));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(3, 0.5f)), WheelPosition.LEFTREAR));
        wheels.add(new Wheel(createWheelBody(world, wheel, new Vector2(-3, 0.5f)), WheelPosition.RIGHTREAR));

        return wheels;
    }

    public static void attachCarJoints(World world, Body car, List<Wheel> wheels) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = car;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        for (Wheel wheel : wheels) {
            if (wheel.wheelPosition.isRear()) {
                jointDef.maxMotorTorque = 0;
                jointDef.enableMotor = false;
            }
            jointDef.bodyB = wheel.getBody();
            jointDef.localAnchorA.set(wheel.getBody().getWorldCenter());
            RevoluteJoint joint = (RevoluteJoint) world.createJoint(jointDef);
            wheel.setWheelJoint(joint);
        }
    }

    public static Body createCar(World world, float x, float y, short carFlag) {
        PolygonShape leftShape = new PolygonShape();
        leftShape.set(leftHalf);
        PolygonShape rightShape = new PolygonShape();
        rightShape.set(rightHalf);

        FixtureDef leftFixture = new FixtureDef();
        leftFixture.shape = leftShape;
        leftFixture.density = 0.1f;
        leftFixture.restitution = 0.3f;
        leftFixture.filter.groupIndex = carFlag;

        FixtureDef rightFixture = new FixtureDef();
        rightFixture.shape = rightShape;
        rightFixture.density = 0.1f;
        rightFixture.restitution = 0.3f;
        rightFixture.filter.groupIndex = carFlag;

        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.DynamicBody;

        Body body = createTopDownBody(bd, world);
        body.createFixture(leftFixture);
        body.createFixture(rightFixture);
        return body;
    }

    private static Body createTopDownBody(BodyDef bodyDef, World world) {
        bodyDef.linearDamping = 0.4f;
        bodyDef.angularDamping = 0.3f;
        return world.createBody(bodyDef);
    }

    public static Body createBall(World world, float radius) {
        CircleShape sd = new CircleShape();
        sd.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sd;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.8f;

        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.type = BodyDef.BodyType.DynamicBody;

        Body body = createTopDownBody(bd, world);
        body.createFixture(fixtureDef);
        body.setMassData(new MassData());
        return body;
    }

}
