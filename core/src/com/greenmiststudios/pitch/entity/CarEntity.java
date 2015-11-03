package com.greenmiststudios.pitch.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.greenmiststudios.pitch.PitchGame;
import com.greenmiststudios.pitch.box2d.Box2DFactory;
import com.greenmiststudios.pitch.box2d.VectorMath;
import com.greenmiststudios.pitch.components.*;
import com.greenmiststudios.pitch.enums.Player;
import com.greenmiststudios.pitch.enums.Team;
import com.greenmiststudios.pitch.systems.PhysicsSystem;

/**
 * Created by geoffpowell on 10/29/15.
 */
public class CarEntity extends Entity {

    public CarEntity(Player player) {
        super();
        TextureRenderComponent renderComponent = new TextureRenderComponent();
        Texture texture = PitchGame.ASSETMANAGER.get("arrow.png");
        renderComponent.texture = new TextureRegion(texture);

        TeamComponent teamComponent = new TeamComponent();
        teamComponent.team = Team.RED;
        teamComponent.player = player;

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position = new Vector2(30,30);

        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.body = Box2DFactory.createCar(PhysicsSystem.getWorld(), 0, 0, (short) 0);

        PolygonRenderComponent polygonComponent = new PolygonRenderComponent();
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
        polygonComponent.sprite  = new PolygonSprite(polyReg);
        polygonComponent.sprite .setOrigin(0, 0);
        polygonComponent.sprite.setColor(teamComponent.team.primaryColor);

        add(renderComponent);
        add(transformComponent);
        add(polygonComponent);
        add(teamComponent);
    }

}
