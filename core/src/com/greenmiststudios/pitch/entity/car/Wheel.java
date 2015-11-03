package com.greenmiststudios.pitch.entity.car;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.greenmiststudios.pitch.box2d.VectorMath;
import com.greenmiststudios.pitch.enums.Throttle;
import com.greenmiststudios.pitch.enums.WheelPosition;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class Wheel {
    private Body body;
    public final WheelPosition wheelPosition;
    private RevoluteJoint wheelJoint;
    private Car car;

    private float traction = 0.8f;

    public Wheel(Body body, WheelPosition position) {
        wheelPosition = position;
        this.body = body;
        body.setUserData(this);
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Body getBody() {
        return body;
    }

    public void setTraction(float traction) {
        this.traction = traction;
    }

    public WheelPosition getWheelPosition() {
        return wheelPosition;
    }

    public void setWheelJoint(RevoluteJoint wheelJoint) {
        this.wheelJoint = wheelJoint;
    }

    public RevoluteJoint getWheelJoint() {
        return wheelJoint;
    }

    Vector2 getForwardVelocity() {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        return VectorMath.multiply(currentForwardNormal.dot(body.getLinearVelocity()), currentForwardNormal);
    }

    private Vector2 getLateralVelocity() {
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1, 0));
        return VectorMath.multiply(currentRightNormal.dot(body.getLinearVelocity()), currentRightNormal);
    }

    void updateFriction() {
        if (car == null) return;
        //lateral linear velocity
        float maxLateralImpulse = wheelPosition.isRear() ? car.backTireMaxLateralImpulse : car.frontTireMaxLateralImpulse;
        Vector2 lat = VectorMath.minus(getLateralVelocity());
        Vector2 impulse = VectorMath.multiply(body.getMass(), lat);

        if ( impulse.len() > maxLateralImpulse) impulse.scl(maxLateralImpulse / impulse.len());
        body.applyLinearImpulse(VectorMath.multiply(traction, impulse), body.getWorldCenter(), true);

        //angular velocity
        body.applyAngularImpulse(traction * 0.1f * body.getInertia() * -body.getAngularVelocity(), true );

        //forward linear velocity
        Vector2 currentForwardNormal = getForwardVelocity();
        float currentForwardSpeed = VectorMath.normalize(currentForwardNormal);
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce(VectorMath.multiply(traction * dragForceMagnitude, currentForwardNormal), body.getWorldCenter(), true);
    }

    void drive(Throttle throttle) {
        if (car == null) return;

        float desiredSpeed = 0;
        if (throttle == Throttle.FORWARD) {
            desiredSpeed = car.maxForwardSpeed;
        } else if (throttle == Throttle.BACKWARD) {
            desiredSpeed = car.maxBackwardSpeed;
        } else {
            return;
        }
        //find current speed in forward direction
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        float currentSpeed = getForwardVelocity().dot(currentForwardNormal);

        float maxDriveForce = wheelPosition.isRear() ? car.backTireForce : car.frontTireForce;
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
