package com.greenmiststudios.pitch.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.greenmiststudios.pitch.box2d.VectorMath;
import com.greenmiststudios.pitch.components.*;
import com.greenmiststudios.pitch.entity.car.Wheel;
import com.greenmiststudios.pitch.enums.Steering;
import com.greenmiststudios.pitch.enums.Throttle;
import com.greenmiststudios.pitch.enums.WheelPosition;

/**
 * Created by geoffpowell on 11/2/15.
 */
public class CarSystem extends IteratingSystem {

    private final ComponentMapper<TeamComponent> teamMapper;
    private final ComponentMapper<PhysicsComponent> physicsMapper;
    private final ComponentMapper<WheelComponent> wheelMapper;

    private float lockAngle = (float) Math.toRadians(30);
    private float turnSpeedPerSec = (float) Math.toRadians(160);//from lock to lock in 0.5 sec
    private float turnPerTimeStep = turnSpeedPerSec / 60.0f;
    private static float maxForwardSpeed = 75; //m/s
    private static float maxBackwardSpeed = -65;
    private static float frontTireForce = 500;
    private static float backTireForce = 300;
    private static float backTireMaxLateralImpulse = 7.5f;
    private static float frontTireMaxLateralImpulse = 7.5f;
    private static float traction = 0.8f;

    public CarSystem() {
        super(Family.all(TeamComponent.class, PhysicsComponent.class, WheelComponent.class).get());
        teamMapper = ComponentMapper.getFor(TeamComponent.class);
        physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
        wheelMapper = ComponentMapper.getFor(WheelComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physicsComponent = physicsMapper.get(entity);
        TeamComponent teamComponent = teamMapper.get(entity);
        WheelComponent wheelComponent = wheelMapper.get(entity);

        updateFriction(physicsComponent.body, wheelComponent.wheelPosition);

        if (Gdx.input.isKeyPressed(teamComponent.player.accelerate)) {
            drive(Throttle.FORWARD, physicsComponent.body, wheelComponent.wheelPosition);
        } else if (Gdx.input.isKeyPressed(teamComponent.player.decelerate)) {

        }

        if (wheelComponent.wheelPosition.isLeft() || wheelComponent.wheelPosition.isRight()) {
            float desiredAngle = 0;
            if (Gdx.input.isKeyPressed(teamComponent.player.left)) {
                desiredAngle = lockAngle;
            } else if (Gdx.input.isKeyPressed(teamComponent.player.right)) {
                desiredAngle = -lockAngle;
            } else {
                wheelComponent.wheelJoint.setLimits(0, 0);
                return;
            }
            float angleNow = wheelComponent.wheelJoint.getJointAngle();
            float angleToTurn = desiredAngle - angleNow;
            angleToTurn = MathUtils.clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
            float newAngle = angleNow + angleToTurn;
            wheelComponent.wheelJoint.setLimits(newAngle, newAngle);
        }

    }

    Vector2 getForwardVelocity(Body body) {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        return VectorMath.multiply(currentForwardNormal.dot(body.getLinearVelocity()), currentForwardNormal);
    }

    private Vector2 getLateralVelocity(Body body) {
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1, 0));
        return VectorMath.multiply(currentRightNormal.dot(body.getLinearVelocity()), currentRightNormal);
    }

    void updateFriction(Body body, WheelPosition wheelPosition) {
        //lateral linear velocity
        float maxLateralImpulse = wheelPosition.isRear() ? backTireMaxLateralImpulse : frontTireMaxLateralImpulse;
        Vector2 lat = VectorMath.minus(getLateralVelocity(body));
        Vector2 impulse = VectorMath.multiply(body.getMass(), lat);

        if (impulse.len() > maxLateralImpulse) impulse.scl(maxLateralImpulse / impulse.len());
        body.applyLinearImpulse(VectorMath.multiply(traction, impulse), body.getWorldCenter(), true);

        //angular velocity
        body.applyAngularImpulse(traction * 0.1f * body.getInertia() * -body.getAngularVelocity(), true);

        //forward linear velocity
        Vector2 currentForwardNormal = getForwardVelocity(body);
        float currentForwardSpeed = VectorMath.normalize(currentForwardNormal);
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce(VectorMath.multiply(traction * dragForceMagnitude, currentForwardNormal), body.getWorldCenter(), true);
    }

    void drive(Throttle throttle, Body body, WheelPosition wheelPosition) {

        float desiredSpeed = 0;
        if (throttle == Throttle.FORWARD) {
            desiredSpeed = maxForwardSpeed;
        } else if (throttle == Throttle.BACKWARD) {
            desiredSpeed = maxBackwardSpeed;
        } else {
            return;
        }
        //find current speed in forward direction
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        float currentSpeed = getForwardVelocity(body).dot(currentForwardNormal);

        float maxDriveForce = wheelPosition.isRear() ? backTireForce : frontTireForce;
        //apply necessary force
        float force = 0;
        if (desiredSpeed > currentSpeed) {
            force = maxDriveForce;
        } else if (desiredSpeed < currentSpeed) {
            force = -maxDriveForce;
        } else {
            return;
        }

        body.applyForce(VectorMath.multiply(traction * force, currentForwardNormal), body.getWorldCenter(), true);
    }

}
