package com.greenmiststudios.pitch.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.greenmiststudios.pitch.PitchGame;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.box2d.CollisionFlag;
import com.greenmiststudios.pitch.interfaces.IRenderable;
import com.greenmiststudios.pitch.model.entity.PhysicsEntity;

/**
 * Created by geoffpowell on 9/30/15.
 */
public class Field extends PhysicsEntity implements IRenderable {

    private Texture texture;
    private float width = 600;
    private float height = 350;

    public Field() {
        super("Field");
    }

    @Override
    public void create() {
        super.create();
        texture = PitchGame.ASSETMANAGER.get("field.jpg");

        ChainShape sd = new ChainShape();
        sd.createLoop(new Vector2[] { new Vector2(width/2,-height/2), new Vector2(-width/2,-height/2), new Vector2(-width/2,height/2), new Vector2(width/2,height/2)});

        FixtureDef def = new FixtureDef();
        def.shape = sd;
        def.filter.categoryBits = CollisionFlag.WALL;

        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);

        Body body = world.createBody(bd);
        body.createFixture(def);
        body.setType(BodyDef.BodyType.StaticBody);
    }

    @Override
    public void render() {
        if (getParent() == null) return;
        SpriteBatch spriteBatch = getParent().getSpriteBatch();
        spriteBatch.begin();
        spriteBatch.draw(texture, location.x - (Box2DFactory.BOX_TO_WORLD * width)/2, location.y - (Box2DFactory.BOX_TO_WORLD * height)/2, (Box2DFactory.BOX_TO_WORLD * width), (Box2DFactory.BOX_TO_WORLD * height));
        spriteBatch.end();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public int getPrimaryOrdering() {
        return 0;
    }

    @Override
    public int getSecondaryOrdering() {
        return 0;
    }
}
