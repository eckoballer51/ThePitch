package com.greenmiststudios.pitch.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.enums.Direction;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class Wheel {
    private Body body;
    public final WheelPosition wheelPosition;
    private Joint wheelJoint;


    private float maxForwardSpeed = 250;
    private float maxBackwardSpeed = -40;
    private float backTireMaxDriveForce = 300;
    private float frontTireMaxDriveForce = 500;
    private float backTireMaxLateralImpulse = 8.5f;
    private float frontTireMaxLateralImpulse = 7.5f;
    private float traction = 0.1f;

    public enum WheelPosition { LEFTFRONT, RIGHTFRONT, LEFTREAR, RIGHTREAR }

    public Wheel(Body body, WheelPosition position) {
        wheelPosition = position;
        this.body = body;
    }

    public void setMaxForwardSpeed(float maxForwardSpeed) {
        this.maxForwardSpeed = maxForwardSpeed;
    }

    public void setMaxBackwardSpeed(float maxBackwardSpeed) {
        this.maxBackwardSpeed = maxBackwardSpeed;
    }

    public void setBackTireMaxDriveForce(float backTireMaxDriveForce) {
        this.backTireMaxDriveForce = backTireMaxDriveForce;
    }

    public void setFrontTireMaxDriveForce(float frontTireMaxDriveForce) {
        this.frontTireMaxDriveForce = frontTireMaxDriveForce;
    }

    public void setBackTireMaxLateralImpulse(float backTireMaxLateralImpulse) {
        this.backTireMaxLateralImpulse = backTireMaxLateralImpulse;
    }

    public void setFrontTireMaxLateralImpulse(float frontTireMaxLateralImpulse) {
        this.frontTireMaxLateralImpulse = frontTireMaxLateralImpulse;
    }

    public Body getBody() {
        return body;
    }

    public void setWheelJoint(Joint wheelJoint) {
        this.wheelJoint = wheelJoint;
    }

    Vector2 getForwardVelocity() {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        return currentForwardNormal.scl(currentForwardNormal.dot(body.getLinearVelocity()));
    }

    private Vector2 getLateralVelocity() {
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1, 0));
        return currentRightNormal.scl(currentRightNormal.dot(body.getLinearVelocity()));
    }

    void updateTurn(int direction) {
        float desiredTorque = 0;
        switch ( direction & (Direction.LEFT|Direction.RIGHT) ) {
            case Direction.LEFT:  desiredTorque = 15;  break;
            case Direction.RIGHT: desiredTorque = -15; break;
            default: ;//nothing
        }
        body.applyTorque( desiredTorque, true );
    }

    void updateFriction() {
        //lateral linear velocity
        float maxLateralImpulse = wheelPosition.toString().contains("REAR") ? backTireMaxLateralImpulse : frontTireMaxLateralImpulse;
        Vector2 impulse = getLateralVelocity().scl(-body.getMass());
        if ( impulse.len() > maxLateralImpulse )
            impulse.scl(maxLateralImpulse / impulse.len());
        body.applyLinearImpulse(impulse.scl(traction), body.getWorldCenter(), true);

        //angular velocity
        body.applyAngularImpulse( traction * 0.1f * body.getInertia() * -body.getAngularVelocity(), true );

        //forward linear velocity
        Vector2 currentForwardNormal = getForwardVelocity();
        float currentForwardSpeed = currentForwardNormal.nor().len();
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce(currentForwardNormal.scl(traction * dragForceMagnitude), body.getWorldCenter(), true);
    }

    void updateDrive(int direction) {
        //find desired speed
        float desiredSpeed = 0;
        if ( (direction & (Direction.FORWARD | Direction.BACKWARD)) > 0) {
            if ((direction & Direction.FORWARD) > 0) {
                desiredSpeed = maxForwardSpeed;
            } else {
                desiredSpeed = maxBackwardSpeed;
            }
        }
        float maxDriveForce = wheelPosition.toString().contains("REAR") ? backTireMaxDriveForce : frontTireMaxDriveForce;
        //find current speed in forward direction
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        float currentSpeed = currentForwardNormal.dot(getForwardVelocity());

        //apply necessary force
        float force = 0;
        if ( desiredSpeed > currentSpeed )
            force = maxDriveForce;
        else if ( desiredSpeed < currentSpeed )
            force = -maxDriveForce;
        else
            return;
        body.applyForce(currentForwardNormal.scl(force), body.getWorldCenter(), true);
    }

}
