package com.greenmiststudios.pitch.entity.car;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.box2d.VectorMath;
import com.greenmiststudios.pitch.enums.Steering;
import com.greenmiststudios.pitch.enums.Team;
import com.greenmiststudios.pitch.enums.Throttle;
import com.greenmiststudios.pitch.enums.WheelPosition;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

import java.util.EnumSet;
import java.util.List;

import static com.greenmiststudios.pitch.box2d.CollisionFlag.*;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class Car extends PhysicsEntity implements IRenderable {

    protected List<Wheel> wheels;
    EnumSet<Throttle> throttle;
    EnumSet<Steering> steering;

    private int count = 0;
    private final short carFlag;
    private float lockAngle = (float) Math.toRadians(30);
    private float turnSpeedPerSec = (float) Math.toRadians(160);//from lock to lock in 0.5 sec
    private float turnPerTimeStep = turnSpeedPerSec / 60.0f;
    protected static float maxForwardSpeed = 75; //m/s
    protected static float maxBackwardSpeed = -65;
    protected static float frontTireForce = 500;
    protected static float backTireForce = 300;
    protected static float backTireMaxLateralImpulse = 7.5f;
    protected static float frontTireMaxLateralImpulse = 7.5f;

    private PolygonSprite sprite;
    private Team team;

    public Car(Team team) {
        this(team, "Car");
    }

    public Car(Team team, String tag) {
        this(tag);
        this.team = team;
    }

    public Car(String tag) {
        super(tag);
        throttle = EnumSet.noneOf(Throttle.class);
        steering = EnumSet.noneOf(Steering.class);
        carFlag = CARS.get(count);
        count++;
    }

    public void attachWheels() {
        if (wheels == null || wheels.isEmpty()) return;
        for (Wheel wheel : wheels) {
            wheel.setCar(this);
        }
    }

    private void createPolygon() {
        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFFFFFFFF);
        pix.fill();
        Texture textureSolid = new Texture(pix);
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        float[] verts = VectorMath.convertVerts(Box2DFactory.carBody, Box2DFactory.BOX_TO_WORLD);
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                verts,
                triangulator.computeTriangles(verts).toArray());
        sprite = new PolygonSprite(polyReg);
        sprite.setOrigin(0, 0);
    }

    @Override
    public void create() {
        Body body = Box2DFactory.createCar(world, 0, -100, carFlag);
        wheels = Box2DFactory.createWheels(world, carFlag);
        Box2DFactory.attachCarJoints(world, body, wheels);
        createBody(body);
        attachWheels();
        createPolygon();
        super.create();
    }

    @Override
    public void render() {
        if (getParent() == null || sprite == null) return;
        PolygonSpriteBatch polygonSpriteBatch = getParent().getPolySpriteBatch();
        ShapeRenderer renderer = getParent().getShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        for (Wheel wheel : wheels) {
            Vector2 wheelPos = wheel.getBody().getPosition().cpy();
            renderer.rect(wheelPos.x * Box2DFactory.BOX_TO_WORLD - 6, wheelPos.y * Box2DFactory.BOX_TO_WORLD - 10, 6, 10, 12, 20, 1, 1, wheel.getBody().getAngle() * MathUtils.radDeg);
        }
        renderer.end();

        polygonSpriteBatch.begin();
        sprite.setColor(team.primaryColor);
        sprite.setPosition(location.x, location.y);
        sprite.setRotation(rotation * MathUtils.radDeg);
        sprite.draw(polygonSpriteBatch);
        polygonSpriteBatch.end();
    }

    @Override
    public int getPrimaryOrdering() {
        return 1;
    }

    @Override
    public int getSecondaryOrdering() {
        return 1;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (Wheel wheel : wheels) {
            wheel.updateFriction();
        }

        for (Wheel wheel : wheels) {
            wheel.drive(getThrottle());
        }

        Steering steering = getSteering();
        float desiredAngle = 0;
        if (steering == Steering.RIGHT) {
            desiredAngle = -lockAngle;
        } else if (steering == Steering.LEFT) {
            desiredAngle = lockAngle;
        }
        float angleNow = wheels.get(0).getWheelJoint().getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = MathUtils.clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
        float newAngle = angleNow + angleToTurn;
        wheels.get(0).getWheelJoint().setLimits(newAngle, newAngle);
        wheels.get(1).getWheelJoint().setLimits(newAngle, newAngle);
        wheels.get(2).getWheelJoint().setLimits(0, 0);
        wheels.get(3).getWheelJoint().setLimits(0, 0);
    }

    Throttle getThrottle() {
        if (throttle.size() == 1) return (Throttle) throttle.toArray()[0];
        return Throttle.NONE;
    }

    Steering getSteering() {
        if (steering.size() == 1) return (Steering) steering.toArray()[0];
        return Steering.NONE;
    }

    public Wheel getWheel(final WheelPosition position) {
        for (Wheel w : wheels) {
            if (position == w.wheelPosition) return w;
        }
        return null;
    }
}
