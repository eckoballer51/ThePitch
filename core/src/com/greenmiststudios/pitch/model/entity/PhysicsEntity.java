package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.interfaces.IUpdateable;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class PhysicsEntity extends Entity implements IUpdateable {

    protected Body body;
    protected World world;

    public PhysicsEntity(String tag) {
        super(tag);
    }

    public void createBody(Body body) {
        super.create();
        this.body = body;
        body.setUserData(this);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        if (body == null) return;
        body.getWorld().destroyBody(body);
    }

    @Override
    public void update(float dt) {
        if (body != null) {
            Vector2 boxPos = body.getPosition().cpy();
            location = new Vector2(boxPos.x * Box2DFactory.BOX_TO_WORLD, boxPos.y * Box2DFactory.BOX_TO_WORLD);
            rotation = wrapAngle(body.getAngle());
        }
    }

    public float wrapAngle( float angle) {
        while (angle > MathUtils.PI) angle -= MathUtils.PI2;
        while (angle < 0) angle +=  MathUtils.PI2;
        return angle;
    }
}
